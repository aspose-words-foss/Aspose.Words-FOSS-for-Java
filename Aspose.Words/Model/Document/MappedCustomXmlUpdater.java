// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/10/2022 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.ArrayList;
import com.aspose.StringUtil;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// Handles document node change and updates mapped CustomXml document accordingly.
/// </summary>
/// <remarks>
/// AM. This is just very basic implementation of runtime mapped XML update.
/// We need to handle Run.Text change, formatting change and so on but lets wait till actual requests.
/// </remarks>
class MappedCustomXmlUpdater implements INodeChangingCallback
{
    MappedCustomXmlUpdater(Document doc)
    {
        mDoc = doc;
    }

    public void /*INodeChangingCallback.*/nodeInserting(NodeChangingArgs args)
    {
        // Nothing to do.
    }

    public void /*INodeChangingCallback.*/nodeRemoving(NodeChangingArgs args)
    {
        // Nothing to do.
    }

    public void /*INodeChangingCallback.*/nodeInserted(NodeChangingArgs args) throws Exception
    {
        if (!mDoc.isMappedCustomXmlUpdateSuspended())
            updateMappedCustomXml(args.getNewParent());
    }

    public void /*INodeChangingCallback.*/nodeRemoved(NodeChangingArgs args) throws Exception
    {
        if (!mDoc.isMappedCustomXmlUpdateSuspended())
            updateMappedCustomXml(args.getOldParent());
    }

    private void updateMappedCustomXml(Node parentNode) throws Exception
    {
        // Look for nearest outer SDT.
        StructuredDocumentTag outerSdt = (parentNode.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
            ? (StructuredDocumentTag)parentNode
            : ms.as(parentNode.getAncestor(NodeType.STRUCTURED_DOCUMENT_TAG), StructuredDocumentTag.class);

        // Update found and all ancestor markup.
        while (outerSdt != null)
        {
            updateMappedCustomXmlCore(outerSdt);

            // Look for next outer SDT.
            outerSdt = ms.as(outerSdt.getAncestor(NodeType.STRUCTURED_DOCUMENT_TAG), StructuredDocumentTag.class);
        }
    }

    private void updateMappedCustomXmlCore(StructuredDocumentTag sdt) throws Exception
    {
        // SDT is not mapped, nothing to update.
        if (!sdt.getXmlMapping().isMapped())
            return;

        ArrayList<org.w3c.dom.Node> xmlNodes = sdt.getXmlMapping().getBoundXmlNodes(new XmlMappingContext());
        String mappedContent = CustomXmlUtil.getOverallInnerText(xmlNodes);

        // Resiliently update mapped content.
        try
        {
            // Update content either Document or simple content.
            if (isMappedAsDocument(sdt))
            {
                SuspendMappedCustomXmlUpdateDocument suspendMappedCustomXmlUpdateDocumentResource = new SuspendMappedCustomXmlUpdateDocument(mDoc);
                try /*JAVA: was using*/
                {
                    OoxmlSaveOptions so = new OoxmlSaveOptions(SaveFormat.FLAT_OPC);
                    so.setUpdateXmlMapping(false);
                    OpcDocumentFragmentWriter opcWriter = new OpcDocumentFragmentWriter(so);

                    String wordOpenXML = opcWriter.saveToString(sdt);
                    sdt.getXmlMapping().setValue(wordOpenXML);
                }
                finally { if (suspendMappedCustomXmlUpdateDocumentResource != null) suspendMappedCustomXmlUpdateDocumentResource.dispose(); }
            }
            else
            {
                String newContent = sdt.getText();

                // WORDSNET-26381 Remove the end-of-cell character if the SDT is at the cell level, otherwise
                // MS Word will throw an error when opening the document.
                if (StringUtil.hasChars(newContent) && (sdt.getLevel() == MarkupLevel.CELL))
                {
                    Debug.assertTrue(msString.endsWith(newContent, ControlChar.CELL, StringComparison.INVARIANT_CULTURE));
                    newContent = newContent.substring((0), (0) + (newContent.length() - 1));

                    // Fix Java Error with "\x0007" whitespace
                    newContent = newContent.replace(ControlChar.CELL, " ");
                }

                // Section end character is not allowed in mapped content.
                newContent = msString.trimEnd(newContent, '\f');

                sdt.getXmlMapping().setValue(newContent);
            }
        }
        catch (Exception e)
        {
            Debug.assertTrue(false, e.getMessage());
        }
    }

    /// <summary>
    /// Check if XML data should be mapped as a document.
    /// </summary>
    private static boolean isMappedAsDocument(StructuredDocumentTag sdt)
    {
        // AM. Check for MarkupLevel.Cell to pass Test26381.
        // It seems some rework is needed here.
        return (sdt.getSdtType() != SdtType.PLAIN_TEXT) && (sdt.getLevel() != MarkupLevel.CELL) &&
               (sdt.getSdtType() != SdtType.DATE);
    }

    private /*final*/ Document mDoc;
}

