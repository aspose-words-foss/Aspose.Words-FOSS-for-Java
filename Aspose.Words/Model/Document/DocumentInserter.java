// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/12/2014 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msChar;
import com.aspose.ms.System.msString;
import com.aspose.IntToIntDictionary;


/// <summary>
/// Class for inserting one document into another.
/// </summary>
class DocumentInserter
{
    /// <summary>
    /// Initializes new instance with a specified destination document.
    /// </summary>
    DocumentInserter(Document dstDoc)
    {
        mDstDoc = dstDoc;
    }

    /// <summary>
    /// Initializes new instance with a specified DocumentBuilder.
    /// </summary>
    DocumentInserter(DocumentBuilder documentBuilder)
    {
    	this(documentBuilder.getDocument());
	
        mDocumentBuilder = documentBuilder;
    }

    /// <summary>
    /// Inserts document at the current position of a document builder.
    /// </summary>
    static Node insertDocument(DocumentBuilder builder, Document srcDoc, /*ImportFormatMode*/int importFormatMode,
        ImportFormatOptions options)
    {
        DocumentInserter documentInserter = new DocumentInserter(builder);
        return documentInserter.insertDocument(srcDoc, importFormatMode, options);
    }

    /// <summary>
    /// Inserts document at the current position of a document builder.
    /// </summary>
    Node insertDocument(Document srcDoc, /*ImportFormatMode*/int importFormatMode, ImportFormatOptions options)
    {
        Debug.assertTrue(options != null);

        if (!canInsertDocument(srcDoc))
            throw new IllegalStateException("You cannot insert document with this content into this location.");

        mNodeImporter = new NodeImporter(srcDoc, getDstDoc(), importFormatMode, options);

        // FOSS

        // Word inserts document into inline level SDT in a very special way.
        return ((getCurrentNode() != null) && (getCurrentNode().getParentNode().getNodeLevel() == NodeLevel.INLINE))
            ? insertIntoInlineLevelSdt()
            : insertIntoBlockLevelNode();
    }

    /// <summary>
    /// Appends source document to the end of a destination document.
    /// </summary>
    void appendDocument(Document srcDoc, /*ImportFormatMode*/int importFormatMode, ImportFormatOptions options)
    {
        ImportFormatOptions importFormatOptions = (options != null) ? options : new ImportFormatOptions();
        boolean isSmartStyleBehavior = importFormatOptions.getSmartStyleBehavior();

        // Some tests (for example, Test19275, TestDefect26460, UnifiedTestImportStyleWithList) fail,
        // if SmartStyleBehavior is disabled and it is not clear which result is correct,
        // as we are not mimic Word behavior in these tests (see analysis of WORDSNET-19275).
        // So, lets for a while always append documents with this option enabled.
        importFormatOptions.setSmartStyleBehavior(true);
        mNodeImporter = new NodeImporter(srcDoc, getDstDoc(), importFormatMode, importFormatOptions);

        mParaBefore = getLastDstParagraph();

        // FOSS

        for (Section srcSection : (Iterable<Section>) getSrcDoc().getSections())
        {
            Section importedSection = (Section)mNodeImporter.importNode(srcSection, true, mInfoCollector);

            // WORDSNET-28243 Word removes section type of the first appended section.
            // WORDSNET-28336 Implemented a new option that allows to control either to remove section type, or not.
            if (srcSection.isFirstSection() && importFormatOptions.getAppendDocumentWithNewPage())
            {
                if (importedSection.getSectPr().getSectionStart() != SectionStart.NEW_PAGE)
                    importedSection.getSectPr().remove(SectAttr.SECTION_START);
            }

            preInsertFormatting();
            getDstDoc().appendChild(importedSection);
        }

        postInsertFormatting();

        // Restore SmartStyleBehavior.
        if (options != null)
            options.setSmartStyleBehavior(isSmartStyleBehavior);

        // WORDSNET-25587 Added importing citations.
        mNodeImporter.importCitationSources();
    }

    /// <summary>
    /// Returns true, if document can be inserted at the current position of a document builder.
    /// </summary>
    private boolean canInsertDocument(Document document)
    {
        if (document.getSections().getCount() == 1)
            return true;

        /*NodeType*/int nodeType = getCurrentParagraph().getParentNode().getNodeType();

        return (nodeType != NodeType.CELL &&
                nodeType != NodeType.SHAPE &&
                nodeType != NodeType.HEADER_FOOTER &&
                nodeType != NodeType.FOOTNOTE &&
                nodeType != NodeType.COMMENT);
    }

    /// <summary>
    /// Inserts nodes from a source document into a block level node of a destination document.
    /// </summary>
    /// <returns>First node of source document inserted into destination document.</returns>
    private Node insertIntoBlockLevelNode()
    {
        Section dstSection = getCurrentParagraph().getParentSection();

        // WORDSNET-14073 Word has special logic when inserting into the last empty section.
        // Note, we must calculate it here while destination document is not yet modified.
        boolean isDstSectionEmptyAndLast = (dstSection.isLastSection() && isEmpty(dstSection));

        StructuredDocumentTag parentSdt = (StructuredDocumentTag)getCurrentParagraph().getAncestor(NodeType.STRUCTURED_DOCUMENT_TAG);
        boolean hasPlainSdtAncestor = (parentSdt != null) &&
                            NodeUtil.hasAncestor(parentSdt, NodeType.STRUCTURED_DOCUMENT_TAG) &&
                            (parentSdt.getSdtType() == SdtType.PLAIN_TEXT);

        // Split current destination paragraph to insert source document between split parts.
        mParaAfter = getCurrentParagraph();
        mParaBefore = splitCurrentParagraph();

        for (Section node : (Iterable<Section>) getSrcDoc().getSections())
        {
            Section importedSection = (Section)mNodeImporter.importNode(node, true, mInfoCollector);
            preInsertFormatting();

            // When inserting into the nested SDT, Word ignores section breaks and inserts all the content
            // the way as if it is entirely resides in a single section (see Test13652NestedSdt()).
            if (mInfoCollector.isLastSection() || hasPlainSdtAncestor)
            {
                // The last inserting section should be merged with destination section, so that there will
                // be only one single merged section produced from those two sections. The way in which
                // the sections will be merged depends on either destination section is last and empty
                // and on the place where the source section will be put (i.e. Body or HeaderFooter).
                if (isDstSectionEmptyAndLast && (mParaAfter.getParentStory().getStoryType() == StoryType.MAIN_TEXT))
                {
                    // In case we insert LAST source section into a Body of LAST EMPTY destination section,
                    // we should insert imported section and merge destination section into it.
                    replaceWithImportedSection(importedSection, dstSection);
                }
                else
                {
                    // Otherwise, we leave destination section and merge content of imported section into it.
                    mParaAfter.insertPrevious(importedSection.getBody().getFirstChild(), null);
                }
            }
            else
            {
                // If we are here, then the source document has multiple sections.
                // In this case all source sections, except of very last section, should be inserted 'as is'.
                getDstDoc().insertBefore(importedSection, dstSection);

                // If this is the first inserting section, then we should merge content before
                // split destination paragraph into the inserted section (up to inclusive 'paraBefore').
                if (mInfoCollector.isFirstSection())
                {
                    // WORDSNET-13652 If we insert document with multiple sections into SDT,
                    // then we need to convert destination SDT to ranged one.
                    if (parentSdt != null)
                        StructuredDocumentTag.convertOuterSdtsToRanges(parentSdt);

                    // WORDSNET-23180 Word preserves SectionBreak of the destination section when importing first section.
                    if (importedSection.getPageSetup().getSectionStart() != dstSection.getPageSetup().getSectionStart())
                        importedSection.getSectPr().setSectionStart(dstSection.getSectPr().getSectionStart());

                    importedSection.getBody().insertBefore(mParaBefore.getParentNode().getFirstChild(), mParaBefore.getNextNode(),
                        importedSection.getBody().getFirstChild());
                }
            }
        }

        Node firstImportedNode = mParaBefore.getNextNonAnnotationSibling();
        postInsertFormatting();

        // WORDSNET-25587 Added importing citations.
        mNodeImporter.importCitationSources();

        return firstImportedNode;
    }

    /// <summary>
    /// Replaces destination section with imported one.
    /// </summary>
    /// <remarks>
    /// Preserves some formatting and content from the destination section.
    /// </remarks>
    private void replaceWithImportedSection(Section importedSection, Section dstSection)
    {
        getDstDoc().appendChild(importedSection);

        // Some formatting should be preserved from the destination section.
        for (int key : G_NON_REPLACING_ATTRS)
        {
            // WORDSNET-23180 We shall not change SectionBreak if there is only one section.
            if ((key == SectAttr.SECTION_START) && (getSrcDoc().getSections().getCount() > 1))
                continue;
            dstSection.getSectPr().moveTo(importedSection.getSectPr(), key);
        }

        // Preserve paraAfter and paraBefore around inserted content (they are actually the paragraph
        // that we are split to insert source content).
        importedSection.getBody().appendChild(mParaAfter);
        if (mInfoCollector.isFirstSection())
            importedSection.getBody().prependChild(mParaBefore);

        dstSection.remove();
    }

    /// <summary>
    /// Returns true, if a specified section can be treated as empty.
    /// </summary>
    private static boolean isEmpty(Section section)
    {
        if (!isEmpty(section.getBody()))
            return false;

        // Word ignores Headers and Footers of the last section of a multi-section documents.
        if (section.isLastSection() && !section.isFirstSection())
            return true;
        for (HeaderFooter node : (Iterable<HeaderFooter>) section.getHeadersFooters())
        {
            if (!isEmpty((Story)node))
                return false;
        }

        return true;
    }

    /// <summary>
    /// Returns true, if a specified story is empty.
    /// </summary>
    private static boolean isEmpty(Story story)
    {
        for (Node node : (Iterable<Node>) story)
        {
            if (node.getNodeType() == NodeType.PARAGRAPH)
            {
                Paragraph para = (Paragraph)node;
                if (!para.isEmptyOrContainsOnlyCrossAnnotation())
                    return false;

                return (story.getStoryType() == StoryType.MAIN_TEXT) ? para.isEndOfSection() : para.isEndOfHeaderFooter();
            }

            if (!NodeUtil.isCrossStructureAnnotation(node))
                return false;
        }

        return true;
    }

    /// <summary>
    /// Inserts specified document into inline level SDT.
    /// </summary>
    /// <remarks>
    /// Insertion into inline SDT has special logic in Word:
    /// 1. Only runs from the source document body are inserted, headers and footers are ignored.
    /// 2. 'Paragraph Break' control char is inserted after each SDT in last cell of row in source document.
    /// 3. 'Tab' control char is inserted before each cell except very first in source document table if previously cell hasn't SDT.
    /// </remarks>
    /// <returns>First inserted run.</returns>
    private Node insertIntoInlineLevelSdt()
    {
        Node nodeBefore = getCurrentNode().getPreviousSibling();

        for (Section section : (Iterable<Section>) getSrcDoc().getSections())
        {
            // Word inserts extra run before very first section if AdjustSpacing option is enabled and always inserts it before all subsequent sections.
            if (!section.isFirstSection() || getOptions().getAdjustSentenceAndWordSpacing())
                insertSpaceBeforeNode(getCurrentNode());

            // Word inserts nodes only from the body. Nodes from the headers and footers should not be taken into account.
            insertNodesWithFormatting(section.getBody(), getCurrentNode().getParentNode(), getCurrentNode());
        }

        if (getOptions().getAdjustSentenceAndWordSpacing())
        {
            // Word inserts extra space after inserted content if source document ends with SDT.
            if (getSrcDoc().getLastSection().getBody().getLastNonAnnotationChild().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
                insertSpaceBeforeNode(getCurrentNode());
        }

        return (nodeBefore == null) ? getCurrentNode().getParentNode().getFirstChild() : nodeBefore.getNextSibling();
    }

    /// <summary>
    /// Splits current paragraph onto two parts.
    /// </summary>
    /// <remarks>
    /// This method splits paragraph at the cursor position and is a wrapper for
    /// <see cref="Aspose.Words.DocumentBuilder.InsertParagraphAsWord"/> method.
    /// </remarks>
    /// <returns>Paragraph, which is the first part of the split paragraph.</returns>
    private Paragraph splitCurrentParagraph()
    {
        // Word moves bookmarks to the left part of the split paragraph.
        while ((getCurrentNode() != null) && (getCurrentNode().getNextSibling() != null))
        {
            if ((getCurrentNode().getNodeType() != NodeType.BOOKMARK_START) && (getCurrentNode().getNodeType() != NodeType.BOOKMARK_END))
                break;

            getDocumentBuilder().moveTo(getCurrentNode().getNextSibling());
        }

        // WORDSNET-19667 Insert paragraph in Word manner.
        return getDocumentBuilder().insertParagraphAsWord();
    }

    /// <summary>
    /// Inserts all nodes from a source container into a destination container before a referencing node, applying
    /// specified formatting to the inserting nodes. Processes also all child composite nodes recursively.
    /// See <see cref="InsertIntoInlineLevelSdt"/> for more details.
    /// </summary>
    private void insertNodesWithFormatting(CompositeNode srcContainer, CompositeNode dstContainer, Node dstRefNode)
    {
        for (Node node : (Iterable<Node>) srcContainer)
        {
            CompositeNode composite = ms.as(node, CompositeNode.class);

            switch (node.getNodeType())
            {
                case NodeType.RUN:
                {
                    processRun((Run)node, dstContainer, dstRefNode);
                    break;
                }
                case NodeType.CELL:
                {
                    // Add 'Tab' into inline SDT before cell if this cell is not first in row
                    // and previous cell is not inside SDT.
                    Cell curCell = (Cell)node;
                    if (!curCell.isFirstCell() && !NodeUtil.hasAncestor(curCell.getPreviousCell(), NodeType.STRUCTURED_DOCUMENT_TAG))
                    {
                        // Word applies formatting from the first found run inside source document,
                        // if it does not start from SDT.
                        RunPr runPr = (!mInfoCollector.isFirstNodeSdt() && (mInfoCollector.getFirstRun() != null))
                            ? AttrCollectionExtension.deepClone(mInfoCollector.getFirstRun().getRunPr())
                            : new RunPr();
                        Run newRun = new Run(getDstDoc(), ControlChar.TAB, runPr);
                        dstContainer.insertBefore(newRun, dstRefNode);
                    }
                    break;
                }
                case NodeType.STRUCTURED_DOCUMENT_TAG:
                {
                    StructuredDocumentTag sdt = (StructuredDocumentTag)mNodeImporter.importNode(node, false);
                    sdt.setLevel(MarkupLevel.INLINE);

                    dstContainer.insertBefore(sdt, dstRefNode);

                    insertNodesWithFormatting(composite, sdt, null);
                    continue;
                }
                // WORDSNET-18731 Word allows shapes inside inline level SDTs, so added the case to process them.
                // WORDSNET-20413 The comments are also allowed inside inline level SDTs.
                case NodeType.SHAPE:
                case NodeType.GROUP_SHAPE:
                case NodeType.COMMENT_RANGE_START:
                case NodeType.COMMENT_RANGE_END:
                case NodeType.COMMENT:
                case NodeType.FIELD_START:
                case NodeType.FIELD_SEPARATOR:
                case NodeType.FIELD_END:
                case NodeType.BOOKMARK_START:
                case NodeType.BOOKMARK_END:
                {
                    Node importedNode = mNodeImporter.importNode(node, true);
                    dstContainer.insertBefore(importedNode, dstRefNode);
                    continue;
                }
                default:
                    // skipped now
                    break;
            }

            if (node.isComposite())
                insertNodesWithFormatting(composite, dstContainer, dstRefNode);
        }
    }

    /// <summary>
    /// Adds a run before a specified node applying an appropriate formatting.
    /// </summary>
    /// <remarks>
    /// The formatting being applied depends on importing mode and concrete location of run inside a document.
    /// To mimic Word behavior it expands style into direct attributes for KeepSourceFormatting importing mode
    /// and preserves it for other importing modes.
    /// </remarks>
    private void processRun(Run run, CompositeNode dstContainer, Node dstRefNode)
    {
        RunPr formatting;
        if (mNodeImporter.getContext().getImportFormatMode() == ImportFormatMode.KEEP_SOURCE_FORMATTING)
        {
            // Word expands character style into direct attributes and removes style.
            RunPr runPr = run.getRunPr();
            Style srcStyle = getSrcDoc().getStyles().fetchByIstd(runPr.getIstd(), StyleIndex.DEFAULT_PARAGRAPH_FONT);
            formatting = (srcStyle.getIstd() != StyleIndex.DEFAULT_PARAGRAPH_FONT)
                ? AttrCollectionExtension.deepClone(srcStyle.getRunPr())
                : new RunPr();

            runPr.expandTo(formatting);

            formatting.remove(FontAttr.ISTD);
        }
        else
        {
            formatting = ((Run)mNodeImporter.importNode(run, false)).getRunPr();
        }

        Run newRun = new Run(getDstDoc(), run.getText(), formatting);
        dstContainer.insertBefore(newRun, dstRefNode);

        // Word inserts paragraph break if this run is last node in last cell of row inside SDT.
        Cell cell = (Cell)run.getAncestor(NodeType.CELL);
        if (cell != null)
        {
            Node lastRun = cell.getChild(NodeType.RUN, -1, true);
            if ((run == lastRun) &&
                cell.isLastCell() && NodeUtil.hasAncestor(cell, NodeType.STRUCTURED_DOCUMENT_TAG))
            {
                newRun = new Run(getDstDoc(), ControlChar.PARAGRAPH_BREAK, formatting);
                dstContainer.insertBefore(newRun, dstRefNode);
            }
        }
    }

    /// <summary>
    /// Applies various formatting to a content before insertion.
    /// </summary>
    private void preInsertFormatting()
    {
        if (getOptions().getMergePastedLists())
            mergePastedLists();

        // By default, NodeImporter imports paragraphs as if SmartStyleBehavior option is enabled.
        // If the option is disabled, then we need to process it additionally.
        // WORDSNET-22709 The option is not applicable if there is list defined in direct attributes of some paragraph
        // with the same ListDefId as in some style of this document.
        if (!getOptions().getSmartStyleBehavior() || mInfoCollector.hasStyleListInDirectAttrs())
        {
            for (Paragraph para : (Iterable<Paragraph>) mInfoCollector.getImportedParagraphs())
                processNoSmartStyle(para);
        }
    }

    /// <summary>
    /// Merges pasted lists.
    /// </summary>
    /// <remarks>
    /// Processes imported paragraphs by merging source and destination lists in accordance with Word behavior.
    /// </remarks>
    private void mergePastedLists()
    {
        if (getOptions().getMergePastedLists())
        {
            if (canMergePastedLists())
            {
                // If a first inserting node is a table,
                // then Word merges lists only with the very first paragraph of that table.
                if (mInfoCollector.isFirstNodeTable())
                {
                    Paragraph para = (Paragraph)mInfoCollector.getImportedParagraphs().get(0);
                    if (para.getParentTable() == mInfoCollector.getFirstNode())
                        applyList(para, mParaBefore);
                }
                else
                {
                    for (Paragraph para : (Iterable<Paragraph>) mInfoCollector.getImportedParagraphs())
                    {
                        // If content has list, then only this list must be merged,
                        // otherwise all imported paragraphs must be merged.
                        if (!para.isInCell() && (!mInfoCollector.hasLists() || para.isListItem()))
                            applyList(para, mParaBefore);
                    }
                }
            }

            // When source content ends with a block level SDT and inserting not at the end of a list item,
            // Word defines new list for the rest of the list items after inserted content.
            if (mInfoCollector.isLastSection() && mInfoCollector.isLastNodeSdt() && !isInsertAtEnd())
                applyListDefCopy(mParaAfter);
        }
    }

    /// <summary>
    /// Applies various formatting to inserted content.
    /// </summary>
    private void postInsertFormatting()
    {
        if (mParaBefore == null)
            return;

        Node firstImportedNode = mParaBefore.getNextNonAnnotationSibling();
        // WORDSNET-22103 If we append document, then there is no ParaAfter.
        // In this case the last imported node is just last node of the destination document.
        Node lastImportedNode = (mParaAfter != null) ? mParaAfter.getPreviousNonAnnotationSibling() : getLastNonAnnotationDstChild();
        if ((lastImportedNode != null) && NodeUtil.isCrossStructureAnnotation(lastImportedNode))
            lastImportedNode = lastImportedNode.getPreviousNonAnnotationSibling();

        if ((firstImportedNode == null) || (lastImportedNode == null))
            return;

        // WORDSNET-24960 Actually, the behavior in Word slightly more complex.
        // It analyses the character before and after inserted content and behaves appropriately.
        // But for a moment (till real customers requests) let's leave it as is.
        if (getOptions().getAdjustSentenceAndWordSpacing())
        {
            // Word inserts run with space character before inserted content if it does not start with a table.
            if (firstImportedNode.getNodeType() != NodeType.TABLE)
                addSpaceRun(mParaBefore, false);

            // Word inserts run with space character after inserted content if it ends with SDT.
            if (lastImportedNode.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
                addSpaceRun(mParaAfter, true);
        }

        Paragraph firstImportedPara = (firstImportedNode.getNodeType() == NodeType.PARAGRAPH)
            ? (Paragraph)firstImportedNode
            : (Paragraph)firstImportedNode.nextPreOrderOfType(firstImportedNode.getDocument(), NodeType.PARAGRAPH);

        // Move annotations between the merged paragraphs.
        if (firstImportedPara != null)
        {
            // Block-level.
            // WORDSNET-26905 Move bookmark nodes only.
            if ((mParaBefore.getNextSibling() != null) && (mParaBefore.getNextSibling() instanceof IBookmarkNode) &&
                (mParaBefore.getParentNode() == firstImportedNode.getParentNode()))
            {
                // WORDSNET-23299 Interrupt the insertion when SDT range node  occur among annotations.
                firstImportedPara.insertBefore(mParaBefore.getNextSibling(), firstImportedNode, firstImportedPara.getFirstChild(), true);
            }
            // Inline-level.
            while ((mParaBefore.getLastChild() != null) && NodeUtil.isCrossStructureAnnotation(mParaBefore.getLastChild()))
                firstImportedPara.prependChild(mParaBefore.getLastChild());
        }

        // Word concatenates first nodes of srcDoc and dstDoc if they are both paragraphs.
        if (firstImportedNode.getNodeType() == NodeType.PARAGRAPH)
        {
            if (mParaBefore.hasChildNodes())
                concatenateParagraphs(mParaBefore, firstImportedPara);
        }
        else if ((firstImportedPara != null) && (firstImportedNode.getNodeType() != NodeType.TABLE))
        {
            // Word applies formatting to the left part of split paragraph from the first found paragraph in srcDoc
            // if first node of srcDoc is not a paragraph or a table.
            mParaBefore.setParaPr(AttrCollectionExtension.deepClone(firstImportedPara.getParaPr()));
        }

        // Delete left part of split paragraph if it is empty.
        if (!mParaBefore.hasChildNodes())
            mParaBefore.remove();

        // WORDSNET-25505 Implemented a new option to insert document as inline for new method InsertDocumentInline().
        if (getOptions().getInlineMode() && (mParaAfter != null))
        {
            Paragraph dstLastPara = ms.as(mParaAfter.getPreviousSibling(), Paragraph.class);
            if (dstLastPara != null)
            {
                concatenateParagraphs(dstLastPara, mParaAfter);
                if (!dstLastPara.hasChildNodes())
                    dstLastPara.remove();
            }
        }
    }

    /// <summary>
    /// Adds run with one space character to a specified paragraph.
    /// </summary>
    /// <param name="paragraph">Paragraph to add run with space character.</param>
    /// <param name="isAtBeginning">Specify where to insert new run.</param>
    private static void addSpaceRun(Paragraph paragraph, boolean isAtBeginning)
    {
        if (paragraph == null)
            return;

        Run refRun = isAtBeginning ? paragraph.getFirstRun() : paragraph.getLastRun(true);

        if ((refRun == null) || (refRun.getText().length() == 0))
            return;

        char edgeChar;
        if (isAtBeginning)
        {
            edgeChar = refRun.getText().charAt(0);
            // Word inserts at the beginning only if first character in the paragraph is a letter.
            if (!StringUtil.isLetter(edgeChar))
                return;
        }
        else
        {
            edgeChar = refRun.getText().charAt(refRun.getText().length() - 1);
        }

        // Don't add space character, if it already exists.
        if (msChar.isWhiteSpace(edgeChar))
            return;

        Run newRun = new Run(refRun.getDocument(), " ", AttrCollectionExtension.deepClone(refRun.getRunPr()));

        if (isAtBeginning)
            paragraph.prependChild(newRun);
        else
            paragraph.appendChild(newRun);
    }

    /// <summary>
    /// Concatenates last paragraph before imported content with the first imported paragraph.
    /// </summary>
    /// <remarks>
    /// When Word inserts into not a very beginning of the paragraph, so that the left split part of this paragraph
    /// will contain some text, it preserves the original style of the imported paragraph and an original formatting
    /// of the both paragraphs being concatenated.
    /// </remarks>
    private void concatenateParagraphs(Paragraph paraBeforeImportedContent, Paragraph firstImportedPara)
    {
        // WORDSNET-19532 A concatenated paragraph should have a style name,
        // that first imported paragraph had in a source document.
        Style styleFirstImportedParaOriginal = getSrcDoc().getFirstSection().getBody().getFirstParagraph().getParagraphStyle();

        // The style that should be set to the concatenated paragraph.
        Style dstStyle = getDstDoc().getStyles().findLocaleIndependentMatch(styleFirstImportedParaOriginal);

        RunPr dstStyleRunPrExp = dstStyle.getExpandedRunPr(RunPrExpandFlags.DOCUMENT_DEFAULTS);
        ParaPr dstStyleParaPrExp = dstStyle.getExpandedParaPr(ParaPrExpandFlags.DOCUMENT_DEFAULTS);

        // The imported paragraph has all properties expanded into direct attributes and collapsed
        // over the source Normal style during the importing process. But now, if we want to apply
        // a some another (not a Normal) style to the imported paragraph, then we need to recalculate
        // its attributes in accordance with the formatting of the style being applied.
        if (dstStyle.getStyleIdentifier() != StyleIdentifier.NORMAL)
        {
            for (Node importedNode : (Iterable<Node>) firstImportedPara)
            {
                Inline importedInline = ms.as(importedNode, Inline.class);
                if (importedInline != null)
                {
                    importedInline.setRunPr(importedInline.getExpandedRunPr(RunPrExpandFlags.DOCUMENT_DEFAULTS));
                    importedInline.getRunPr().collapse(dstStyleRunPrExp);
                }
            }
        }

        // Concatenate paragraphs by moving a content of the last paragraph
        // before imported content into the first imported paragraph.
        while (paraBeforeImportedContent.getLastChild() != null)
        {
            // If style will be changed, then we need to recalculate direct
            // attributes of paragraph content to preserve its formatting.
            if (!msString.equals(paraBeforeImportedContent.getParagraphStyle().getName(), dstStyle.getName()))
            {
                Inline inline = ms.as(paraBeforeImportedContent.getLastChild(), Inline.class);
                if (inline != null)
                {
                    inline.setRunPr(inline.getExpandedRunPr(RunPrExpandFlags.DOCUMENT_DEFAULTS));
                    inline.getRunPr().collapse(dstStyleRunPrExp);
                }
            }

            firstImportedPara.prependChild(paraBeforeImportedContent.getLastChild());
        }

        // Recalculate formatting of the resulted paragraph in accordance
        // with a formatting of the new style being applied.
        RunPr runPrExp = firstImportedPara.getExpandedParagraphBreakRunPr(RunPrExpandFlags.DOCUMENT_DEFAULTS);
        runPrExp.collapse(dstStyleRunPrExp);
        firstImportedPara.setParagraphBreakRunPr(runPrExp);

        ParaPr paraPrExp = firstImportedPara.getExpandedParaPr(ParaPrExpandFlags.DOCUMENT_DEFAULTS);
        paraPrExp.collapse(dstStyleParaPrExp);
        firstImportedPara.setParaPr(paraPrExp);

        // At last, apply the style.
        firstImportedPara.getParaPr().set(ParaAttr.ISTD, dstStyle.getIstd());
    }

    /// <summary>
    /// Inserts new run with space char before specified node.
    /// </summary>
    private static void insertSpaceBeforeNode(Node node)
    {
        if ((node != null) && (node.getParentNode() != null))
            node.getParentNode().insertBefore(new Run(node.getDocument(), " "), node);
    }

    /// <summary>
    /// Returns true if inserted paragraphs can be merged with surrounding lists.
    /// </summary>
    private boolean canMergePastedLists()
    {
        // Exit, if we insert into paragraph that is not a list.
        if ((mParaBefore == null) || !mParaBefore.isListItem())
            return false;

        // When 'AdjustSentenceAndWordSpacing' option is set to true and inserted content ends with SDT,
        // Word does not merge lists.
        // Please see TestInsertDocumentOptions.TestMergeListsLastSdt() for example.
        if (getOptions().getAdjustSentenceAndWordSpacing() && mInfoCollector.isLastNodeSdt())
            return false;

        // Word merges lists only when inserts into the beginning of paragraph.
        // The exception to this rule is when first inserted node is a table.
        // In this case Word merges very first paragraph of that table.
        // Please see TestInsertDocumentOptions.TestMergeListsParagraph() and
        // TestInsertDocumentOptions.TestMergeListsTable() for example.
        if (!isInsertAtStart() && !mInfoCollector.isFirstNodeTable())
            return false;

        // At last, there are three different cases depending on lists existence inside inserting content:
        // 1) If source content has no any lists, then Word applies numbering to the all inserted paragraphs.
        if (!mInfoCollector.hasLists())
            return true;

        // 2) If there is exactly one list inside source content and inserted content either starts or ends with this
        // list, then Word merges lists.
        if (mInfoCollector.hasSingleList())
        {
            Paragraph para = ms.as(mInfoCollector.getFirstNode(), Paragraph.class);
            if ((para != null) && para.isListItem())
                return true;

            para = ms.as(mInfoCollector.getLastNode(), Paragraph.class);
            if ((para != null) && para.isListItem())
                return true;
        }

        // 3) In all other cases Word does not merge lists.
        return false;
    }

    /// <summary>
    /// Applies list to the paragraph from the referred <paramref name="refPara"/>.
    /// </summary>
    private static void applyList(Paragraph para, Paragraph refPara)
    {
        int refListId = (/*int*/Integer)((IParaAttrSource)refPara).fetchParaAttr(ParaAttr.LIST_ID);
        para.getParaPr().set(ParaAttr.LIST_ID, refListId);

        // Also Word resets Normal style to a ListParagraph by default.
        // See TestTableDocStartsWithPara.TestStyleInsertedParagraphs for example.
        if (para.getParagraphStyle().getStyleIdentifier() == StyleIdentifier.NORMAL)
            para.getParaPr().set(ParaAttr.ISTD, refPara.getDocument().getStyles().fetchBySti(StyleIdentifier.LIST_PARAGRAPH).getIstd());
    }

    /// <summary>
    /// Creates a copy of list definition of the specified paragraph and applies it to the all paragraphs that have same
    /// list id as specified one, starting from this paragraph and to the end of a document.
    /// </summary>
    private static void applyListDefCopy(Paragraph fromPara)
    {
        int listId = (/*int*/Integer)((IParaAttrSource)fromPara).fetchParaAttr(ParaAttr.LIST_ID);
        if (listId == 0)
            return;

        DocumentBase doc = fromPara.getDocument();
        List oldList = doc.getLists().getListByListId(listId);
        List newList = doc.getLists().addCopy(oldList);

        Paragraph curPara = fromPara;
        while (curPara != null)
        {
            int curListId = (/*int*/Integer)curPara.getParaPr().fetchAttr(ParaAttr.LIST_ID);

            if (curListId == oldList.getListId())
                curPara.getParaPr().setAttr(ParaAttr.LIST_ID, newList.getListId());

            curPara = NodeUtil.findNextParagraph(curPara);
        }
    }

    /// <summary>
    /// Applies formatting to a specified paragraph as if it were imported
    /// with the <see cref="ImportFormatOptions.SmartStyleBehavior"/> option disabled.
    /// </summary>
    /// <remarks>
    /// When SmartStyleBehavior option is disabled, Word expands source style of the specified
    /// paragraph into direct attributes of this paragraph, if style has numbering.
    /// The existing direct attributes of paragraph are not overridden.
    /// </remarks>
    private void processNoSmartStyle(Paragraph paragraph)
    {
        Style srcStyle = getSourceStyle(paragraph);
        if (srcStyle == null)
            return;

        ParaPr dstParaPr = paragraph.getParaPr();

        int srcStyleListId = (/*int*/Integer)((IParaAttrSource)srcStyle).fetchParaAttr(ParaAttr.LIST_ID);
        // If style is not numbered, then exit.
        // WORDSNET-22149 Also do not expand source style, if paragraph is explicitly not a list item to mimic Word.
        if ((srcStyleListId == 0) || dstParaPr.isExplicitlyNotListItem())
            return;

        // Otherwise, import source style list to apply it to the destination
        // paragraph, if there is no direct list applied yet (i.e. expand listId no-override).
        if (dstParaPr.getListId() == 0)
            dstParaPr.setListId(getDstDoc().getLists().importList(mNodeImporter.getContext(), srcStyleListId));

        int dstListId = (/*int*/Integer)paragraph.fetchParaAttr(ParaAttr.LIST_ID, RevisionsView.ORIGINAL);
        // This is listId, which paragraph has in the source document.
        int srcListId = mNodeImporter.getContext().getImportedListIds().getKey(dstListId);
        if (IntToIntDictionary.isNullSubstitute(srcListId))
            srcListId = srcStyleListId;

        // This is resolved list level, which paragraph has in both: source and destination document.
        int listLevel = (/*int*/Integer)paragraph.fetchParaAttr(ParaAttr.LIST_LEVEL, RevisionsView.ORIGINAL);

        // WORDSNET-27484 Check, if there is no list level set in numbered style of the source document.
        // If so, then mimic Word and set it from the direct (resolved) list level of the paragraph.
        // Note, resolved list level value is the same in source and destination documents.
        boolean hasSrcStyleListLevel = srcStyle.getParaPr().contains(ParaAttr.LIST_LEVEL);
        if (!hasSrcStyleListLevel)
            srcStyle.getParaPr().set(ParaAttr.LIST_LEVEL, listLevel);

        // Expand source style attributes.
        ParaPr srcStyleExpandedParaPr = srcStyle.getExpandedParaPr(ParaPrExpandFlags.DOCUMENT_DEFAULTS);

        // Restore original list level of the source style.
        if (!hasSrcStyleListLevel)
        {
            srcStyle.getParaPr().remove(ParaAttr.LIST_LEVEL);
            // Also remove it from the expanded collection.
            srcStyleExpandedParaPr.remove(ParaAttr.LIST_LEVEL);
        }

        // Now check, if resolved source listId is not the same, as the source style listId of the paragraph,
        // then it means the paragraph has direct listId attribute. Word applies its formatting too.
        if (srcListId != srcStyleListId)
        {
            List srcList = getSrcDoc().getLists().getListByListId(srcListId);
            ListLevel srcDirectListLevel = srcList.getListLevelOverrideAware(listLevel);
            srcDirectListLevel.getParaPr().expandTo(srcStyleExpandedParaPr);
        }

        // Preserve direct paragraph attributes.
        dstParaPr.expandTo(srcStyleExpandedParaPr);

        // WORDSNET-22709 Word also collapses it over inherited attributes in destination document.
        // WORDSNET-23909 Don't collapse direct formatting in KeepDifferentStyles mode (by design).
        int[] keysToIgnore = (mNodeImporter.getContext().getImportFormatMode() == ImportFormatMode.KEEP_DIFFERENT_STYLES)
            ? dstParaPr.getKeys()
            : new int[] { ParaAttr.ISTD };
        ParaPr dstInheritedParaPr = paragraph.getExpandedParaPr(ParaPrExpandFlags.NO_DIRECT_FORMATTING);
        srcStyleExpandedParaPr.collapse(dstInheritedParaPr, keysToIgnore);

        paragraph.setParaPr(srcStyleExpandedParaPr);
    }

    /// <summary>
    /// Returns style of the specified imported paragraph that it had in source document.
    /// </summary>
    private Style getSourceStyle(Paragraph importedParagraph)
    {
        Style dstStyle = importedParagraph.getParagraphStyle();

        StyleCollection srcStyles = getSrcDoc().getStyles();

        int istdSrcStyle = mNodeImporter.getContext().getImportedIstds().tryGetValueIndirect(dstStyle.getIstd());
        Style srcStyle = (istdSrcStyle != Integer.MIN_VALUE)
            ? srcStyles.getByIstd(istdSrcStyle, false)
            : srcStyles.findLocaleIndependentMatch(dstStyle);

        return srcStyle;
    }

    /// <summary>
    /// Gets current paragraph in destination document.
    /// </summary>
    private Paragraph getCurrentParagraph() { return getDocumentBuilder().getCurrentParagraph(); }

    /// <summary>
    /// Gets current node in destination document.
    /// </summary>
    private Node getCurrentNode() { return getDocumentBuilder().getCurrentNode(); }

    /// <summary>
    /// Gets a source document.
    /// </summary>
    private Document getSrcDoc() { return (Document)mNodeImporter.getContext().getSrcDoc(); }

    /// <summary>
    /// Gets a destination document.
    /// </summary>
    private Document getDstDoc() { return mDstDoc; }

    /// <summary>
    /// Gets a DocumentBuilder of the destination document.
    /// </summary>
    private DocumentBuilder getDocumentBuilder()
    {
        if (mDocumentBuilder == null)
            mDocumentBuilder = new DocumentBuilder(mDstDoc);

        return mDocumentBuilder;
    }

    /// <summary>
    /// Gets an importing options.
    /// </summary>
    private ImportFormatOptions getOptions() { return mNodeImporter.getContext().getImportFormatOptions(); }

    /// <summary>
    /// Returns true, if inserting at start of destination paragraph.
    /// </summary>
    private boolean isInsertAtStart() { return (mParaBefore == null) || (mParaAfter == null) || mParaBefore.isEmptyOrContainsOnlyCrossAnnotation(); }

    /// <summary>
    /// Returns true, if inserting at end of destination paragraph.
    /// </summary>
    private boolean isInsertAtEnd() { return (mParaAfter == null) || mParaAfter.isEmptyOrContainsOnlyCrossAnnotation(); }

    /// <summary>
    /// Gets last non-annotation child of destination document.
    /// </summary>
    private Node getLastNonAnnotationDstChild()
    {
        return ((getDstDoc().getLastSection() != null) && (getDstDoc().getLastSection().getBody() != null))
            ? getDstDoc().getLastSection().getBody().getLastNonAnnotationChild()
            : null;
    }

    /// <summary>
    /// Gets last paragraph of destination document.
    /// </summary>
    private Paragraph getLastDstParagraph()
    {
        return ((getDstDoc().getLastSection() != null) && (getDstDoc().getLastSection().getBody() != null))
            ? getDstDoc().getLastSection().getBody().getLastParagraph()
            : null;
    }

    /// <summary>
    /// Document builder of a destination document.
    /// </summary>
    private DocumentBuilder mDocumentBuilder;

    /// <summary>
    /// Destination document.
    /// </summary>
    private /*final*/ Document mDstDoc;

    /// <summary>
    /// The node importer to import nodes from source to destination document.
    /// </summary>
    private NodeImporter mNodeImporter;

    /// <summary>
    /// Clone listener that collects all necessary information about inserted content upon importing its particular nodes.
    /// </summary>
    private /*final*/ ImportInfoCollector mInfoCollector = new ImportInfoCollector();

    /// <summary>
    /// Paragraph before inserted content.
    /// </summary>
    private Paragraph mParaBefore;
    /// <summary>
    /// Paragraph after inserted content.
    /// </summary>
    private Paragraph mParaAfter;

    /// <summary>
    /// The collection of section attributes that should be preserved when replacing last empty section with imported one.
    /// </summary>
    private static /*final*/ int[] G_NON_REPLACING_ATTRS = new int[]
    {
        // See TestJira16127.
        SectAttr.SECTION_START,

        // The following attrs (related to TextColumnCollection) actually should not be preserved.
        // But there is incorrect WORDSNET-19014, so let's leave it at that for now.
        SectAttr.COLUMNS_COUNT, SectAttr.COLUMNS_EVENLY_SPACED, SectAttr.COLUMNS_SPACING, SectAttr.COLUMNS_LINE_BETWEEN, SectAttr.COLUMNS
    };
}

