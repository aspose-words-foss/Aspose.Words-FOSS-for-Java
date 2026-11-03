// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/05/2012 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.OpcPackagePart;
import com.aspose.ms.System.IO.MemoryStream;
import java.util.ArrayList;
import java.util.Collections;


/// <summary>
/// Provides static method for reading altChunk element.
/// </summary>
class DocxAltChunkReader
{
    /// <summary>
    /// Reads an altChunk element (17.17.2.1).
    /// </summary>
    static void read(NrxDocumentReaderBase reader) throws Exception
    {
        Debug.assertTrue(reader.isDocx());

        String altChunkId = "";
        /*ImportFormatMode*/int importFormatMode = ImportFormatMode.USE_DESTINATION_STYLES;

        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"id"*/0:
                    altChunkId = xmlReader.getValue();
                    break;
                default:
                    xmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DOCX, xmlReader.getLocalName());
                    break;
            }
        }

        while (xmlReader.readChild("altChunk"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"altChunk"*/1:
                    // WORDSNET-11573 Nested altChunk element.
                    read(reader);
                    return;
                case /*"altChunkPr"*/2:
                    importFormatMode = readAltChunkPr(xmlReader, importFormatMode);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        addAltChunk((DocxDocumentReaderBase)reader, altChunkId, importFormatMode);
    }

    /// <summary>
    /// Read altChunkPr element (17.17.2.2)
    /// Within ISO/IEC 29500, only one property (matchSrc) is specified for altChunkPr element.
    /// </summary>
    private static /*ImportFormatMode*/int readAltChunkPr(NrxXmlReader xmlReader, /*ImportFormatMode*/int importFormatMode)
    {
        while (xmlReader.readChild("altChunkPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                // Read matchSrc - Keep Source Formatting on Import (17.17.2.3)
                case /*"matchSrc"*/3:
                    importFormatMode = xmlReader.readBoolVal()
                                           ? ImportFormatMode.KEEP_SOURCE_FORMATTING
                                           : ImportFormatMode.USE_DESTINATION_STYLES;
                    break;
                default:
                    xmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DOCX, xmlReader.getLocalName());
                    break;
            }
        }
        return importFormatMode;
    }

    /// <summary>
    /// Create and add AltChunk object to AltChunk collection.
    /// </summary>
    private static void addAltChunk(DocxDocumentReaderBase reader, String altChunkId, /*ImportFormatMode*/int importFormatMode) throws Exception
    {
        String target = reader.getRelationshipTarget(altChunkId);

        OpcPackagePart imagePart = reader.getPartByName(target);
        if (imagePart == null)
            return;

        byte[] altChunkData = imagePart.getAsMemoryStream().toArray();

        // Detect load format of altChunk content. If load format unknown just ignore.
        FileFormatDetector detector = new FileFormatDetector();
        FileFormatInfo info = detector.detect(new MemoryStream(altChunkData));
        if (info.getLoadFormat() == LoadFormat.UNKNOWN)
        {
            return;
        }

        // Reset loading callback which used for the main document.
        // Don't want to affect main document progress while "AltChunk" loading.
        LoadOptions lo = reader.getLoadOptions().deepClone();
        lo.setProgressCallback(null);

        // Get AltChunk document.
        Document altChunkDocument = loadAltChunkDocument(altChunkData, info.getLoadFormat(), reader, lo, importFormatMode);

        if (altChunkDocument.getOriginalLoadFormat() == LoadFormat.RTF)
        {
            // WORDSNET-28163 Handle RTF altchunk tables.
            // AM. Don't really know if it is a good place for this code, maybe we need to add internal load options
            // to handle this case directly in RTF reader.
            /*MsWordVersionCore*/int altchunkVersion = altChunkDocument.getCompatibilityOptions().getMswVersion();
            /*MsWordVersionCore*/int targetVersion = reader.getDocument().fetchDocument().getCompatibilityOptions().getMswVersion();

            if ((altchunkVersion == MsWordVersionCore.UNSPECIFIED) && (targetVersion >= MsWordVersionCore.WORD_2013))
                handleRtfTables(altChunkDocument);
        }

        /*NodeType*/int containerNodeType = NodeType.PARAGRAPH;

        Node referenceNode = reader.getCurContainer().getLastChild();

        // WORDSNET-13130 Page break position is changed after re-saving Docx.
        // Flash a run with a page break into the first paragraph of altChunk document. If the first node in body
        // is not a paragraph, create a new paragraph with a page break and insert it into main document.
        flushRun(altChunkDocument, reader);

        // If CurContainer is Paragraph, altChunk is inserted on inline level and must be processed in special way.
        boolean isInline = (reader.getCurContainer().getNodeType() == NodeType.PARAGRAPH);

        // WORDSNET-11945 If CurContainer is StructuredDocumentTag placed on an inline level,
        // i.e. inside a paragraph. This means that it can host only inline content inside.
        if (reader.getCurContainer().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
        {
            containerNodeType = NodeType.STRUCTURED_DOCUMENT_TAG;

            StructuredDocumentTag structuredDocumentTag = (StructuredDocumentTag)reader.getCurContainer();
            isInline = (structuredDocumentTag.getParentNode().getNodeType() == NodeType.PARAGRAPH);
        }

        CompositeNode referenceContainer;

        CompositeNode curContainer = reader.getCurContainer();

        // If it is inline altChunk, we should close current paragraph/SDT and reopen it after altChunk referenceContainer.
        if (isInline)
            reader.popContainer(containerNodeType);

        // Create, add and push paragraph or SDT container to insert an altChunk content before it.
        switch (containerNodeType)
        {
            case NodeType.STRUCTURED_DOCUMENT_TAG:
                referenceContainer = new StructuredDocumentTag(reader.getDocument(), MarkupLevel.INLINE);
                break;
            default:
                referenceContainer = new Paragraph(reader.getDocument());
                break;
        }

        reader.addAndPushContainer(referenceContainer);
        // WORDSNET-16659 Flush SDT to the "altchunk" reference node to preserve SDT content.
        // Note: Reference node will be removed after markup will be resolved.
        if (referenceContainer.getNodeType() == NodeType.PARAGRAPH)
        {
            reader.flushPendingMarkup(referenceContainer);
            // WORDSNET-17050 Update node, which represents end of the SDT markup.
            reader.getXmlReader().setPossibleSdtEnd((Paragraph)referenceContainer);
        }

        // Reopen container if altChunk is on inline level.
        if (isInline)
        {
            reader.popContainer(containerNodeType);
            reader.addAndPushContainer((CompositeNode)curContainer.deepClone(false));
        }

        // Pop altChunk referenceParagraph if altChunk is on block level.
        if (!isInline)
            reader.popContainer(containerNodeType);

        DocxAltChunk altChunk = new DocxAltChunk(referenceContainer, referenceNode, altChunkDocument, importFormatMode, isInline);

        // Add AltChunk object to AltChunk collection.
        reader.addAltChunk(altChunk);
    }

    /// <summary>
    /// Loads an altChunk document.
    /// </summary>
    /// <param name="altChunkData">A byte array to load the altChunk document from.</param>
    /// <param name="altChunkFormat">Detected <see cref="LoadFormat"/> of the altChunk document.</param>
    /// <param name="docxReader">"Docx" document reader.</param>
    /// <param name="targetLoadOptions">The LoadOptions of the target Document.</param>
    /// <param name="importFormatMode">The mode in which the altChunk will be inserted into the document.</param>
    /// <returns>A document loaded from the altChunk data. </returns>
    private static Document loadAltChunkDocument(
        byte[] altChunkData,
        /*LoadFormat*/int altChunkFormat,
        DocxDocumentReaderBase docxReader,
        LoadOptions targetLoadOptions,
        /*ImportFormatMode*/int importFormatMode) throws Exception
    {
        MemoryStream documentStream = new MemoryStream(altChunkData);
        DocumentBase targetDocument = docxReader.getDocument();

        // Import of HTML-based altChunks require special processing in order to make sure the result looks close
        // to what is produced by MS Word.
        if ((altChunkFormat == LoadFormat.HTML) || (altChunkFormat == LoadFormat.MHTML))
        {
            Document altChunkDocument = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);

            // WORDSNET-23604 Update initial value of the list definition identifier to preserve numbering from the altChunk.
            ArrayList<DocxAltChunk> altChunks = docxReader.getAltChunks();
            if (altChunks.size() > 0)
            {
                // WORDSNET-23844 Try to find a previous non-empty list definitions collection from altChunks.
                ArrayList<ListDef> listDefs = null;
                for (int i = altChunks.size() - 1; i >= 0; --i)
                {
                    listDefs = altChunks.get(i).getAltChunkDocument().getLists().getListDefs();
                    if (listDefs.size() > 0)
                        break;
                }

                Collections.sort(listDefs);
                int maxListDefId = listDefs.size() > 0 ? listDefs.get(listDefs.size() - 1).getListDefId() : 0;
                altChunkDocument.getLists().setMaxListDefId(maxListDefId);
            }

            // WORDSNET-23496 Copy all HTML blocks to a new altChunk document to
            // take into account IDs of already created HTML blocks.
            copyHtmlBlocks(targetDocument, altChunkDocument);
            for (DocxAltChunk altChunk : altChunks)
            {
                copyHtmlBlocks(altChunk.getAltChunkDocument(), altChunkDocument);
            }

            if (importFormatMode == ImportFormatMode.USE_DESTINATION_STYLES)
            {
                // Make sure the altChunk document has the same base formatting as the target document.
                // This step is important for correct splitting of imported formatting into style and direct parts.
                targetDocument.getStyles().getDefaultParaPr().copyTo(altChunkDocument.getStyles().getDefaultParaPr());
                targetDocument.getStyles().getDefaultRunPr().copyTo(altChunkDocument.getStyles().getDefaultRunPr());
            }

            // The HTML reader should stick to MS Word behavior when importing HTML altChunks.
            HtmlLoadOptions htmlLoadOptions = new HtmlLoadOptions(targetLoadOptions);
            htmlLoadOptions.setApplyFormattingAsMsWord(true);

            // WORDSNET-20364 Copy the MS Word version to the document imported from HTML or use the MS Word version
            // from load options if the original document's MS Word version is unspecified.
            Document mainDocument = (targetDocument.getNodeType() == NodeType.DOCUMENT)
                ? (Document)targetDocument
                : ((GlossaryDocument)targetDocument).getMainDocument();
            if (mainDocument.getCompatibilityOptions().getMswVersion() != MsWordVersionCore.UNSPECIFIED)
            {
                altChunkDocument.getCompatibilityOptions().setMswVersion(mainDocument.getCompatibilityOptions().getMswVersion());
            }
            else
            {
                altChunkDocument.getCompatibilityOptions().optimizeFor(targetLoadOptions.getMswVersion());
            }

            altChunkDocument.load(documentStream, htmlLoadOptions);
            return altChunkDocument;
        }

        LoadOptions loadOptions = new LoadOptions(targetLoadOptions);

        // All other formats don't require any special processing.
        return new Document(documentStream, loadOptions, false);
    }

    private static void copyHtmlBlocks(
        DocumentBase source,
        DocumentBase destination)
    {
        destination.getHtmlBlockCollection().add(source.getHtmlBlockCollection());
    }

    /// <summary>
    /// Updates document tables to mimic MS Word behaviour.
    /// </summary>
    private static void handleRtfTables(Document doc) throws Exception
    {
        for (Section section : (Iterable<Section>) doc.getSections())
        for (Node story : (Iterable<Node>) section)
        for (Table table : (Iterable<Table>) ((IStory)story).getTables())
        {
            table.setAttrOnAllRows(TableAttr.STYLE_OPTIONS, TableStyleOptions.DEFAULT_2003);
            table.setAttrOnAllRows(TableAttr.LEFT_INDENT, -5);
        }
    }

    /// <summary>
    /// Flush a run with text joined in RunTextBuilder.
    /// </summary>
    /// <param name="altChunkDoc">AltChunk document</param>
    /// <param name="reader">Reader</param>
    private static void flushRun(Document altChunkDoc, NrxDocumentReaderBase reader)
    {
        if (reader.getXmlReader().getRunTextBuilder().length() == 0)
            return;

        Node fChild = altChunkDoc.getFirstSection().getBody().getFirstNonAnnotationChild();
        Paragraph altChunkPar = (fChild.getNodeType() == NodeType.PARAGRAPH) ? (Paragraph)fChild : null;

        if (null != altChunkPar)
        {
            // Flush a run with a page break into the first paragraph of altChunk document.
            String runText = reader.getXmlReader().getRunTextBuilder().toString();
            Run run = new Run(altChunkDoc, runText, new RunPr());
            altChunkPar.prependChild(run);

            // Reset run text builder.
            reader.getXmlReader().getRunTextBuilder().setLength(0);
        }
        else
        {
            // Create a new paragraph with a page break and insert it into main document.
            ((DocxDocumentReaderBase)reader).flushRun(reader.getDocument());
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"id",
		"altChunk",
		"altChunkPr",
		"matchSrc"
	);

}

