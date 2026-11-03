// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/10/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ISetGeneric;
import com.aspose.HashSetGeneric;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.StreamReader;


/// <summary>
/// Represents a class to write a document's fragment to <see cref="SaveFormat.FlatOpc"/>.
/// </summary>
class OpcDocumentFragmentWriter implements IDocumentFragmentWriter
{
    OpcDocumentFragmentWriter()
    {
    	this(false);
	
    }

    /// <summary>
    /// Special constructor to write minimal document.
    /// </summary>
    /// <remarks>
    /// See WORDSNET-24602 for details.
    /// </remarks>
    OpcDocumentFragmentWriter(boolean writeMinimalDocument)
    {
    	this(new OoxmlSaveOptions(SaveFormat.FLAT_OPC));
	
        mWriteMinimalDocument = writeMinimalDocument;
    }

    OpcDocumentFragmentWriter(OoxmlSaveOptions saveOptions)
    {
        mSaveOptions = saveOptions;

        if (gTestMode)
            mSaveOptions.setTestMode();
    }

    //>>>>>>>> #region  IDocumentFragmentWriter

    /// <summary>
    /// Saves just an individual node (document fragment) into the <see cref="SaveFormat.FlatOpc"/> format.
    /// </summary>
    /// <param name="node">A node to save.</param>
    /// <returns>String that represents node content in the <see cref="SaveFormat.FlatOpc"/> format.</returns>
    /// <remarks>Supported nodes:<br/>
    /// <see cref="NodeType.StructuredDocumentTag"/><br/>
    /// <see cref="NodeType.StructuredDocumentTagRangeStart"/>
    /// </remarks>
    public String saveToString(Node node) throws Exception
    {
        Debug.assertTrue(node != null);

        Document srcDoc = ms.as(node.getDocument(), Document.class);
        // Skip nodes from glossary for a while.
        // It is necessary to take in attention 3 documents in some way and prepare the result.
        boolean insertContent = srcDoc != null;

        // Attempt use the main document for nodes in glossary causes heavy cloning operation in some
        // cases, for example "TestJira7611.docx". For the "TestJira7611.docx" iterations through all SDT
        // in the glossary may take few hours. And some attempts failed with out of memory exception.
        // So, decided to use just a blank document for a while.
        Document dstDoc = insertContent ? (Document)srcDoc.getDocument().deepClone(false) : new Document();

        if (insertContent)
            importNodeContent(node, dstDoc);

        dstDoc.setWarningCallback(null);
        dstDoc.getBuiltInDocumentProperties().clear();
        // WORDSNET-23347 At this point all SDTs are updated
        // and we can remove corresponding CustomXmlParts to mimic Word.
        dstDoc.getCustomXmlParts().clear();

        if (mWriteMinimalDocument)
        {
            CleanupOptions options = new CleanupOptions(); { options.setUnusedBuiltinStyles(true); }
            dstDoc.cleanup(options);
            dstDoc.getDocPr().Rsids.clear();
            // WORDSNET-28539 Remove the document protection in WriteMinimalDocument mode.
            dstDoc.getDocPr().DocumentProtection.unprotect();
            dstDoc.setThemeInternal(null);

            RunPrValidator runPrCollector = new RunPrValidator();
            ISetGeneric<String> usedFonts = new HashSetGeneric<String>();
            runPrCollector.execute(dstDoc, usedFonts);
            dstDoc.getFontInfos().updateToUsedFonts(usedFonts);

            dstDoc.getVariables().clear();
            dstDoc.joinRunsWithSameFormatting();
            removeRsids(dstDoc);

            mSaveOptions.setSetBuiltInThemeIfNull(false);
            mSaveOptions.setWriteLatentStyles(false);
        }

        // WORDSNET-27447 Remove macros when save WordOpenXML content.
        dstDoc.removeMacros();

        return documentToString(dstDoc);
    }

    //<<<<<<<< #endregion  IDocumentFragmentWriter

    /// <summary>
    /// Sets Test flag. This flag is used to make behavior of the class deterministic for test purposes.
    /// Call this method before running any tests which can be connected to methods of this class.
    /// </summary>
    public static void setTestMode()
    {
        gTestMode = true;
    }

    /// <summary>
    /// Imports node content depending from the node type.
    /// </summary>
    private void importNodeContent(Node node, Document dstDoc) throws Exception
    {
        Document srcDoc = ms.as(node.getDocument(), Document.class);
        NodeImporter importer = new NodeImporter(srcDoc, dstDoc, ImportFormatMode.USE_DESTINATION_STYLES);

        switch (node.getNodeType())
        {
            case NodeType.STRUCTURED_DOCUMENT_TAG:
                importSdt((StructuredDocumentTag)node, importer);
                break;
            case NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START:
                importSdtRange((StructuredDocumentTagRangeStart)node, importer);
                break;
            default:
                // Skip other nodes for a while and return just empty document.
                break;
        }

        // WORDSNET-23887 The Word inserts start of bookmarks to the result when bookmarks in the destination are inconsistent.
        fixMissingBookmarksStarts(node, dstDoc, importer);
    }

    private static void fixMissingBookmarksStarts(Node node, Document dstDoc, NodeImporter importer) throws Exception
    {
        // Skip for a while cases for row and cell level SDTs. For example, currently logic does not insert bookmarks
        // from cell level. See Test23887CellLevel for details.
        if (!node.isComposite() || ((node.getNodeLevel() != NodeLevel.BLOCK) && (node.getNodeLevel() != NodeLevel.INLINE)))
            return;

        BookmarkCache dstBookmarkCache = null;
        BookmarkCache srcBookmarkCache = null;

        try
        {
            dstBookmarkCache = new BookmarkCache(dstDoc);
            srcBookmarkCache = new BookmarkCache((Document)node.getDocument());
            // Looks like the Word flushes "starts" to the begging of the first paragraph.
            Paragraph dstBookmarkParent = (Paragraph)dstDoc.getChild(NodeType.PARAGRAPH, 0, true);
            if (dstBookmarkParent == null)
                return;

            for (BookmarkEnd end : (Iterable<BookmarkEnd>) ((CompositeNode)node).getChildNodes(NodeType.BOOKMARK_END, true))
            {
                Bookmark srcBookmark = srcBookmarkCache.get(end.getName());
                if (srcBookmark == null) // i.e. "start" not found in the source.
                    continue;

                Bookmark dstBookmark = dstBookmarkCache.get(srcBookmark.getName());
                if (dstBookmark != null) // i.e. current bookmark has "start" and "end".
                    continue;

                // "end" should be imported from the source and there is not a "start" in the destination.
                // At the same time the source contains "start". So, import and append "start" to the
                // destination as the Word does.

                BookmarkStart dstStart = (BookmarkStart)importer.importNode(srcBookmark.getBookmarkStart(), false);
                dstBookmarkParent.prependChild(dstStart);

                // I.e. start + end here must exist.
                dstBookmark = dstBookmarkCache.get(dstStart.getName());
                Debug.assertTrue(dstBookmark != null);

                // Looks like the Word resets this bookmark property.
                dstBookmark.getBookmarkEnd().setDisplacedBy(DisplacedByType.UNSPECIFIED);
                dstBookmark.getBookmarkStart().setDisplacedBy(DisplacedByType.UNSPECIFIED);
            }
        }
        finally
        {
            // Release resources and unsubscribe from events.
            if (dstBookmarkCache != null)
                dstBookmarkCache.clear();

            if (srcBookmarkCache != null)
                srcBookmarkCache.clear();
        }
    }

    /// <summary>
    /// Imports SDT node.
    /// </summary>
    private void importSdt(StructuredDocumentTag sdt, NodeImporter importer) throws Exception
    {
        if (!sdt.hasChildNodes())
            return;

        Document dstDoc = ms.as(importer.getContext().getDstDoc(), Document.class);
        Debug.assertTrue(dstDoc != null);

        StructuredDocumentTag dstSdt = (StructuredDocumentTag)importer.importNode(sdt, true);
        Section srcSect = (Section)sdt.getAncestor(NodeType.SECTION);
        if (srcSect != null)
        {
            Section dstSect = (Section)importer.importNode(srcSect, false);
            dstDoc.getSections().add(dstSect);
        }

        DocumentBuilder builder = new DocumentBuilder(dstDoc);
        builder.moveToDocumentEnd();

        switch (dstSdt.getLevel())
        {
            case MarkupLevel.BLOCK:
                insertBlockLevelSdt(builder, dstSdt);
                break;
            case MarkupLevel.INLINE:
                insertInlineLevelSdt(builder, dstSdt);
                break;
            case MarkupLevel.CELL:
                insertCellLevelSdt(builder, importer, sdt, dstSdt);
                break;
            case MarkupLevel.ROW:
                insertRowLevelSdt(builder, importer, sdt, dstSdt);
                break;
            default:
                throw new IllegalStateException("Unexpected SDT level.");
        }
    }

    /// <summary>
    /// Imports SDT range.
    /// </summary>
    private void importSdtRange(StructuredDocumentTagRangeStart rangeStart, NodeImporter importer)
    {
        Debug.assertTrue(rangeStart != null);

        if (rangeStart.getParentNode() == null)
            throw new IllegalStateException("Structured document tag range start is not descendant of a document.");

        StructuredDocumentTagRangeEnd rangeEnd = rangeStart.getRangeEnd();
        if (rangeEnd == null)
            throw new IllegalStateException("Structured document tag range end was not found in the document.");

        Document dstDoc = ms.as(importer.getContext().getDstDoc(), Document.class);
        Debug.assertTrue(dstDoc != null);

        Section endSection = (rangeEnd.getParentNode() != null) ? ((Body)rangeEnd.getParentNode()).getParentSection() : null;
        Section startSection = (rangeStart.getParentNode() != null) ? ((Body)rangeStart.getParentNode()).getParentSection() : null;

        for (Section curSect = startSection; curSect != endSection.getNextSibling() && curSect != null; curSect = (Section)curSect.getNextSibling())
        {
            if (msObject.referenceEquals(curSect, startSection))
            {
                dstDoc.ensureMinimum();
                dstDoc.getFirstSection().getBody().remove();
            }

            copySection(curSect, importer, rangeStart);
        }
    }

    private static void copySection(
        Section srcSect,
        NodeImporter importer,
        StructuredDocumentTagRangeStart rangeStart)
    {
        Section importedSect;
        Document dstDoc = (Document)importer.getContext().getDstDoc();

        boolean isFirstSection = msObject.referenceEquals(((Body)rangeStart.getParentNode()).getParentSection(), srcSect);
        boolean isLastSection = msObject.referenceEquals(((Body)rangeStart.getRangeEnd().getParentNode()).getParentSection(), srcSect);

        if (isLastSection)
        {
            importedSect = dstDoc.getLastSection();
        }
        else
        {
            importedSect = (Section)importer.importNode(srcSect, !isFirstSection);
            dstDoc.insertBefore(importedSect, dstDoc.getLastSection());
        }

        if (!isLastSection && !isFirstSection)
            return;

        // Copy headers/footers.
        for(HeaderFooter headerFooter : (Iterable<HeaderFooter>) srcSect.getChildNodes(NodeType.HEADER_FOOTER, false))
        {
            HeaderFooter importedHeaderFooter = (HeaderFooter)importer.importNode(headerFooter, true);
            importedSect.appendChild(importedHeaderFooter);
        }

        // Copy body content.
        Node importedNode = importer.importNode(srcSect.getBody(), false);
        importedSect.appendChild(importedNode);

        copyBodyChildren(
            isFirstSection ? rangeStart.getNextSibling() : srcSect.getBody().getFirstChild(),
            rangeStart.getRangeEnd(),
            importer,
            importedSect);
    }

    private static void copyBodyChildren(Node from, Node to, NodeImporter importer, Section dstSect)
    {
        for (Node nodeToImport = from; nodeToImport != null && nodeToImport != to; nodeToImport = nodeToImport.getNextSibling())
        {
            Node importedNode = importer.importNode(nodeToImport, true);
            dstSect.getBody().appendChild(importedNode);
        }
    }

    private String documentToString(Document doc) throws Exception
    {
        MemoryStream ms = new MemoryStream();
        try /*JAVA: was using*/
        {
            StreamReader sr = new StreamReader(ms);
            try /*JAVA: was using*/
            {
                doc.save(ms, mSaveOptions);

                ms.setPosition(0);
                return sr.readToEnd();
            }
            finally { if (sr != null) sr.close(); }
        }
        finally { if (ms != null) ms.close(); }
    }

    private void insertInlineLevelSdt(DocumentBuilder builder, StructuredDocumentTag dstSdt) throws Exception
    {
        builder.insertNode(dstSdt);

        boolean isUpdateXmlMapping =
            mSaveOptions.getUpdateXmlMapping() && dstSdt.getXmlMapping().checksumNotEqual(new XmlMappingContext());

        dstSdt.removeSelfOnly(isUpdateXmlMapping);
    }

    private void insertBlockLevelSdt(DocumentBuilder builder, StructuredDocumentTag dstSdt) throws Exception
    {
        builder.getCurrentStory().appendChild(dstSdt);
        builder.getCurrentParagraph().remove();

        boolean isUpdateXmlMapping =
            mSaveOptions.getUpdateXmlMapping() && dstSdt.getXmlMapping().checksumNotEqual(new XmlMappingContext());

        dstSdt.removeSelfOnly(isUpdateXmlMapping);
    }

    /// <summary>
    /// Inserts cell level SDT content to the destination.
    /// </summary>
    private static void insertCellLevelSdt(
        DocumentBuilder builder,
        NodeImporter importer,
        StructuredDocumentTag sourceSdt,
        StructuredDocumentTag dstSdt)
    {
        // Skip SDT updating for this level. Looks like cell level SDT can not be mapped to the XML.
        // Skip updating of nested SDT's for a while.

        // Word moves cell content to the story when SDT contains only one cell.
        Cell firstCell = (Cell)dstSdt.getChild(NodeType.CELL, 0, true);
        if (msObject.referenceEquals(firstCell, firstCell.getParentNode().getLastChild()))
        {
            // Actually between cell and imported SDT may be other SDT's. And we should to import
            // these SDT's, change SDT's levels and remove cell. Skip this logic for a while.
            // WORDSNET-23220 Iterate through nodes in reverse order.
            for (Node cellChild = firstCell.getLastChild(); cellChild != null; cellChild = firstCell.getLastChild())
                builder.getCurrentStory().prependChild(cellChild);

            builder.getCurrentParagraph().remove();
        }
        else
        {
            // Import parent row and table.
            Row srcRow = (Row)sourceSdt.getParentNode();
            Row dstRow = (Row)importer.importNode(sourceSdt.getParentNode(), false);

            Table dstTbl = (Table)importer.importNode(srcRow.getParentTable(), false);
            dstTbl.appendChild(dstRow);

            // Import SDT cells and append these cells to the destination table.
            for (Node childCell = dstSdt.getLastChild(); childCell != null; childCell = dstSdt.getLastChild())
                dstRow.prependChild(childCell);

            builder.getCurrentStory().prependChild(dstTbl);
        }
    }

    private static void insertRowLevelSdt(
        DocumentBuilder builder,
        NodeImporter importer,
        StructuredDocumentTag sourceSdt,
        StructuredDocumentTag dstSdt)
    {
        // Skip SDT updating for this level. Looks like row level SDT can not be mapped to the XML.
        Table srcTbl = (Table)sourceSdt.getAncestor(NodeType.TABLE);
        Table dstTbl = (Table)importer.importNode(srcTbl, false);

        for (Node childRow = dstSdt.getLastChild(); childRow != null; childRow = dstSdt.getLastChild())
            dstTbl.prependChild(childRow);

        // Already there is paragraph in the end of the document. Insert content before the paragraph.
        builder.getCurrentStory().prependChild(dstTbl);
    }

    /// <summary>
    /// Removes all rsid related attributes.
    /// </summary>
    private static void removeRsids(Document doc)
    {
        for (Section section : (Iterable<Section>) doc.getSections())
        {
            for (Paragraph para : (Iterable<Paragraph>) section.getBody().getParagraphs())
            {
                for (Run run : (Iterable<Run>) para.getRuns())
                {
                    run.getRunPr().remove(FontAttr.RSID_R);
                    run.getRunPr().remove(FontAttr.RSID_R_PR);
                }

                para.getParaPr().remove(ParaAttr.RSID_P);

                para.getParagraphBreakRunPr().remove(FontAttr.RSID_R);
                para.getParagraphBreakRunPr().remove(FontAttr.RSID_R_PR);
            }

            section.getSectPr().remove(SectAttr.RSID);
        }
    }

    private /*final*/ OoxmlSaveOptions mSaveOptions;

    /// <summary>
    /// Controls whether we write stripped-down document that excludes any non-content-related parts.
    /// </summary>
    private /*final*/ boolean mWriteMinimalDocument;

    private static boolean gTestMode;
}

