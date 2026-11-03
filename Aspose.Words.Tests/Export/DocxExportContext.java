// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/01/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.XmlUtilPal;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.Map;
import java.util.HashMap;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ZipReaderPal;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.StringUtil;


/// <summary>
/// Saves document as Docx or XLSX and allows searching for XML nodes in necessary part of document internal structure.
/// </summary>
class DocxExportContext implements IXmlExportContext
{
    /// <summary>
    /// Ctor that allows specifying output DOCX part and save options.
    /// </summary>
    DocxExportContext(Document doc, String documentPart, OoxmlSaveOptions saveOptions) throws Exception
    {
        Debug.assertTrue(saveOptions.getSaveFormat() == SaveFormat.DOCX);
        mCompliance = OoxmlComplianceInfo.getCompliance(doc.getComplianceInfo(), saveOptions);
        mXmlDocument = saveAndGetXmlDocument(doc, saveOptions, documentPart);
        mMappings = getNamespaceMappings();
    }

    /// <summary>
    /// Ctor that allows specifying output DOCX part and compliance.
    /// </summary>
    DocxExportContext(Document doc, String documentPart, /*OoxmlComplianceCore*/int compliance) throws Exception
    {
    	this(doc, documentPart, OoxmlSaveOptions.docxWithCompliance(compliance));
	
    }

    /// <summary>
    /// Ctor that generates output DOCX with default OOXML compliance.
    /// </summary>
    DocxExportContext(Document doc, String documentPart) throws Exception
    {
    	this(doc, documentPart, new OoxmlSaveOptions());
	
    }

    /// <summary>
    /// Ctor that allows specifying document and compliance. The document.xml part is extracted.
    /// </summary>
    DocxExportContext(Document doc, /*OoxmlComplianceCore*/int compliance) throws Exception
    {
    	this(doc, "word/document.xml", compliance);
	
    }

    /// <summary>
    /// Ctor that generates output DOCX and extracts document.xml part.
    /// </summary>
    DocxExportContext(Document doc) throws Exception
    {
    	this(doc, "word/document.xml");
	
    }

    /// <inheritdoc />
    public org.w3c.dom.Node getXmlNode(org.w3c.dom.Element parentNode, String xpath)
    {
        return XmlUtilPal.selectSingleNode(parentNode, xpath, mMappings);
    }

    /// <summary>
    /// Searches for a XML node from document root with using specified XPath query. First found node is returned.
    /// </summary>
    org.w3c.dom.Node getXmlNode(String xpath)
    {
        return getXmlNode(getXmlDocument().getDocumentElement(), xpath);
    }

    /// <inheritdoc />
    public ArrayList<org.w3c.dom.Node> getXmlNodes(org.w3c.dom.Element element, String xpath)
    {
        return XmlUtilPal.selectNodes(element, xpath, mMappings);
    }

    /// <summary>
    /// Searches for XML nodes from document root with using specified XPath query. Returns all found nodes.
    /// </summary>
    ArrayList<org.w3c.dom.Node> getXmlNodes(String xpath)
    {
        return getXmlNodes(getXmlDocument().getDocumentElement(), xpath);
    }

    /// <summary>
    /// Add custom namespace mapping
    /// </summary>
    void addCustomNamespaceMapping(String key, String space)
    {
        msDictionary.add(mMappings, key, space);
    }

    /// <summary>
    /// Returns necessary namespace mappings to parse output XML document.
    /// </summary>
    private Map<String, String> getNamespaceMappings()
    {
        boolean isIsoStrict = mCompliance == OoxmlComplianceCore.ISO_STRICT;
        Map<String, String> mappings = new HashMap<String, String>();
        msDictionary.add(mappings, "w", DocxNamespaces.getNamespace(DocxNamespace.MAIN, isIsoStrict));
        msDictionary.add(mappings, "a", DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN, isIsoStrict));
        msDictionary.add(mappings, "w14", DocxNamespaces.getNamespace(DocxNamespace.W1_4_MARKUP, isIsoStrict));
        msDictionary.add(mappings, "wp", DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML, isIsoStrict));
        msDictionary.add(mappings, "wps", DocxNamespaces.getNamespace(DocxNamespace.WORDROCESSING_SHAPE, isIsoStrict));
        msDictionary.add(mappings, "mc", DocxNamespaces.getNamespace(DocxNamespace.MARKUP_COMPATIBILITY, isIsoStrict));
        msDictionary.add(mappings, "wp14", DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_ISO_29500, isIsoStrict));
        msDictionary.add(mappings, "dsp", DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_DIAGRAM_2008, isIsoStrict));
        msDictionary.add(mappings, "cx", DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_EX, isIsoStrict));
        msDictionary.add(mappings, "dgm", DmlNamespaceUtil.getNamespace(DmlNodeType.DIAGRAM, isIsoStrict));
        msDictionary.add(mappings, "cs", DmlExtensionsNamespace.CHART_STYLE);
        msDictionary.add(mappings, "v", NrxNamespaces.VML);
        msDictionary.add(mappings, "lc", DmlNamespaceUtil.getNamespace(DmlNodeType.LOCKED_CANVAS, isIsoStrict));
        msDictionary.add(mappings, "w16cid", DocxNamespaces.getNamespace(DocxNamespace.W1_6_CID, isIsoStrict));
        msDictionary.add(mappings, "w16cex", DocxNamespaces.getNamespace(DocxNamespace.W1_6_CEX, isIsoStrict));
        msDictionary.add(mappings, "c", DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART, isIsoStrict));
        return mappings;
    }

    /// <summary>
    /// Saves the document using the specified save options, extracts the specified part and returns it as
    /// <see cref="System.Xml.XmlDocument"/> object.
    /// </summary>
    static org.w3c.dom.Document saveAndGetXmlDocument(Document doc, SaveOptions saveOptions,
        String returningPart) throws Exception
    {
        org.w3c.dom.Document[] xmlDocs = saveAndGetXmlDocuments(doc, saveOptions, new String[] {returningPart});

        return xmlDocs[0];
    }

    /// <summary>
    /// Saves the document using the specified save options, extracts the specified parts and returns it as
    /// <see cref="System.Xml.XmlDocument"/> object array.
    /// </summary>
    static org.w3c.dom.Document[] saveAndGetXmlDocuments(Document doc, SaveOptions saveOptions,
        String[] returningParts) throws Exception
    {
        MemoryStream[] requestedParts = saveAndGetParts(doc, saveOptions, returningParts);
        org.w3c.dom.Document[] xmlDocs = new org.w3c.dom.Document[returningParts.length];

        for(int i = 0; i < requestedParts.length; ++i)
            xmlDocs[i] = XmlUtilPal.loadXml(requestedParts[i], false);

        return xmlDocs;
    }

    /// <summary>
    /// Saves the document using the specified save options, extracts the specified parts and returns it
    /// as memory stream.
    /// </summary>
    static MemoryStream[] saveAndGetParts(Document doc, SaveOptions saveOptions,
        String[] returningParts) throws Exception
    {
        Debug.assertTrue((returningParts != null) && (returningParts.length > 0));

        MemoryStream docxStream = new MemoryStream();
        doc.save(docxStream, saveOptions);
        docxStream.setPosition(0);

        MemoryStream[] docParts = new MemoryStream[returningParts.length];
        MemoryStream docPartStream;
        int extractedXmlCount = 0;
        int partNameIndex;

        ZipReaderPal zipReader = new ZipReaderPal(docxStream);
        try /*JAVA: was using*/
        {
            while (zipReader.moveToNextEntry())
            {
                partNameIndex = getPartNameIndex(zipReader.getEntryFileName(), returningParts);

                // Indexes starts from 0.
                if (partNameIndex > -1)
                {
                    docPartStream = new MemoryStream();
                    zipReader.extractEntryToStream(docPartStream);

                    if (docPartStream.getLength() == 0)
                        Assert.fail("Cannot extract document part.");

                    docPartStream.setPosition(0);
                    docParts[partNameIndex] = docPartStream;
                    extractedXmlCount++;
                }
            }
        }
        finally { if (zipReader != null) zipReader.dispose(); }

        msAssert.areEqual(extractedXmlCount, returningParts.length, "Some of document parts were not found.");

        return docParts;
    }

    /// <summary>
    /// Searches index of the specified part in the collection of parts names.
    /// </summary>
    /// <param name="currentPart">Name of the part for search.</param>
    /// <param name="returningPart">Collection with names of parts.</param>
    /// <returns>Index of the part, otherwise -1.</returns>
    private static int getPartNameIndex(String currentPart, String[] returningPart)
    {
        int partNameIndex = -1;

        for (int i = 0; i < returningPart.length; ++i)
        {
            if (StringUtil.equalsOrdinalIgnoreCase(currentPart, returningPart[i]))
            {
                partNameIndex = i;
                break;
            }
        }

        return partNameIndex;
    }

    /// <summary>
    /// Returns XML document of the Docx document part.
    /// </summary>
    public org.w3c.dom.Document getXmlDocument() { return mXmlDocument; };

    private org.w3c.dom.Document mXmlDocument;

    private /*final*/ Map<String, String> mMappings;
    private /*final*/ /*OoxmlComplianceCore*/int mCompliance;
}

