// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/03/2011 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msObject;
import java.util.ArrayList;
import com.aspose.DictionaryExtension;
import com.aspose.StackExtension;
import com.aspose.ms.System.Collections.msStack;
import java.util.HashMap;


/// <summary>
/// Resolves block-level markup issues for DOC, RTF and DOCX formats.
/// </summary>
class MarkupResolver
{
    MarkupResolver(LoadOptions loadOptions, /*WarningSource*/int warningSource)
    {
        mLoadOptions = loadOptions;
        mWarningSource = warningSource;
        mInlineResolver = new InlineMarkupResolver(mMarkupStartsTable);
    }

    /// <summary>
    /// Collects markup nodes to be started on flush operation.
    /// </summary>
    void pendingStart(StructuredDocumentTag sdt)
    {
        if (sdt.getLevel() == MarkupLevel.INLINE)
            mInlineResolver.addToSequence(sdt);
        else
            msArrayList.add(mPendingStarts, sdt);
    }

    /// <summary>
    /// Process start for pending markup nodes.
    /// </summary>
    /// <param name="node"></param>
    void flushPendingStarts(Node node)
    {
        for(IMarkupNode markup : mPendingStarts)
            processStart(markup, node);

        mPendingStarts.clear();
    }

    /// <summary>
    /// Processes IMarkupNode end event.
    /// </summary>
    void processEnd(StructuredDocumentTag sdt, Node node)
    {
        if (sdt.getLevel() == MarkupLevel.INLINE)
        {
            mInlineResolver.processInline(sdt);
        }
        else
        {
            if (mPendingStarts.size() > 0)
            {
                for (IMarkupNode markup : mPendingStarts)
                    msArrayList.add(mEmptySdtList, markup);

                // This means that mark does not contain any paragraph inside. Flush all pending starts.
                flushPendingStarts(node);
            }

            // Pop custom XML node from markup start stack.
            IMarkupNode customXml = mMarkupStartStack.pop();

            if (customXml.getLevel_IMarkupNode() != MarkupLevel.INLINE)
            {
                // Push not inline nodes to "ready" stack to resolve it later.
                mMarkupReadyStack.push(customXml);
                mMarkupEndsTable.put(customXml, node);
            }
        }
    }

    /// <summary>
    /// Resolves all stored markup nodes and places it into document.
    /// </summary>
    void resolve() throws Exception
    {
        StructuredDocumentTag prevRepeatingSection = null;

        while (mMarkupReadyStack.getCount() > 0)
        {
            IMarkupNode cxm = mMarkupReadyStack.pop();

            Node firstNode = mMarkupStartsTable.get(cxm);
            Node lastNode = mMarkupEndsTable.get(cxm);

            // WORDSNET-23544 Special case for empty content block SDT. Word inserts it in very peculiar way.
            if ((firstNode != null) && msObject.referenceEquals(firstNode, lastNode) && mEmptySdtList.contains(cxm))
            {
                if (handleEmptyContent((StructuredDocumentTag)cxm, firstNode))
                    continue;
            }

            // Build common parent range from markup start and end.
            ArrayList<Node> range = getReadMarkupRange(cxm);

            if (range == null)
            {
                // Cannot find a place to insert markup.
                warn(WarningStrings.UNABLE_TO_GET_PARENT);
                continue;
            }

            // WORDSNET-25872 Adjust repeating section/item markup level.
            StructuredDocumentTag sdt = (StructuredDocumentTag)cxm;

            if (sdt.getSdtType() == SdtType.REPEATING_SECTION)
                prevRepeatingSection = sdt;

            if ((sdt.getLevel() == MarkupLevel.CELL) && hasMappedDataRepeatingSdt(sdt, prevRepeatingSection) &&
              ((sdt.getSdtType() == SdtType.REPEATING_SECTION) || (sdt.getSdtType() == SdtType.REPEATING_SECTION_ITEM)))
            {
                sdt.setLevel(MarkupLevel.BLOCK);
            }

            CompositeNode rangeParent = range.get(0).getParentNode();

            if (((rangeParent.getNodeType() == NodeType.TABLE) && (cxm.getLevel_IMarkupNode() == MarkupLevel.BLOCK)) ||
                ((rangeParent.getNodeType() == NodeType.ROW) && (cxm.getLevel_IMarkupNode() == MarkupLevel.ROW)) ||
                ((rangeParent.getNodeType() == NodeType.CELL) && (cxm.getLevel_IMarkupNode() == MarkupLevel.CELL)))
            {
                // Additional processing for tables - Custom XML will be applied for parent node.
                range.clear();
                msArrayList.add(range, rangeParent);
                rangeParent = rangeParent.getParentNode();
            }
            else if ((rangeParent.getNodeType() == NodeType.CELL) && (cxm.getLevel_IMarkupNode() == MarkupLevel.ROW) )
            {
                // Special case for row level markup with single cell in row.
                // Custom XML will be applied for parent row.
                Cell parentCell = (Cell)rangeParent;

                range.clear();
                msArrayList.add(range, parentCell.getParentRow());
                rangeParent = parentCell.getParentTable();
            }
            else if ((rangeParent.getNodeType() == NodeType.ROW) && (cxm.getLevel_IMarkupNode() == MarkupLevel.BLOCK))
            {
                Row parentRow = (Row)rangeParent;

                // Special case for block level markup with single row in table.
                // Custom XML will be applied for parent table.
                range.clear();
                msArrayList.add(range, parentRow.getParentTable());
                rangeParent = parentRow.getParentTable().getParentNode();
            }

            if (rangeParent.canInsert((Node)cxm) && canInsertRange((CompositeNode)cxm, range))
            {
                // Custom XML is supported by Model at this level.
                insertAsParent((CompositeNode)cxm, range);
            }
            else
            {
                // WORDSNET-22616 Update markup end if needed.
                lastNode = getActualRangeEndNode(lastNode);

                // Try to insert structured document tag as ranged SDT.
                if (StructuredDocumentTag.isRangeValid(firstNode, lastNode))
                    insertAsRangedSdt(cxm, firstNode, lastNode);
                else
                    warn(WarningStrings.UNACCEPTABLE_MARKUP);
            }
        }
    }

    /// <summary>
    /// Returns true if either RepeatingSectionItem itself or its parent RepeatingSection is mapped.
    /// </summary>
    private boolean hasMappedDataRepeatingSdt(StructuredDocumentTag tag, StructuredDocumentTag repeatingSection)
    {
        if (!tag.getXmlMapping().isEmpty())
            return true;

        if ((tag.getSdtType() != SdtType.REPEATING_SECTION_ITEM) || (repeatingSection == null))
            return false;

        // Here we check that the current RepeatingSectionItem is a potential
        // child sdt for the previous RepeatingSection.
        return (mMarkupStartsTable.get((IMarkupNode)tag) == mMarkupStartsTable.get(repeatingSection)) &&
               (mMarkupEndsTable.get((IMarkupNode)tag) == mMarkupEndsTable.get(repeatingSection)) &&
               !repeatingSection.getXmlMapping().isEmpty();
    }

    /// <summary>
    /// Checks that all nodes in range can be inserted into target composite node.
    /// </summary>
    private static boolean canInsertRange(CompositeNode parentNode, ArrayList<Node> range)
    {
        for(Node node : range)
            if (!parentNode.canInsert(node))
                return false;

        return true;
    }

    /// <summary>
    /// Handles SDT with empty content.
    /// </summary>
    /// <remarks>
    /// Actually I think this logic is explained by flushing operation occurred while Word read SDT content,
    /// seems AW does flushing in other than Word locations.
    /// Lets postpone to find proper logic and move SDT location manually.
    /// </remarks>
    /// <returns>
    /// Returns true if SDT fully resolved and no further actions needed.
    /// </returns>
    private boolean handleEmptyContent(StructuredDocumentTag sdt, Node firstNode) throws Exception
    {
        boolean isMapped = sdt.getXmlMapping().isMapped();

        if (mLoadOptions.getMswVersion() <= MsWordVersion.WORD_2013)
        {
            Paragraph para = (Paragraph)firstNode.nextPreOrderOfType(firstNode.getDocument(), NodeType.PARAGRAPH);

            // Logic is different depending on SDT is mapped. See Test23828_1 for example.
            if ((para != null) && isMapped)
            {
                // Very strange behavior in Word 2013. Probably this is a bug fixed in latest MS Word versions.
                // Move SDT as inline into following appropriate paragraph.
                sdt.setLevel(MarkupLevel.INLINE);

                para.insertAfter(sdt, null);
                return true;
            }

            // Unable to find following appropriate paragraph.
            if (para == null)
            {
                // Word inserts extra paragraph and uses it as SDT content.
                para = new Paragraph(firstNode.getDocument());
                firstNode.insertNext(para);
            }

            // Resolve SDT content with following appropriate (maybe newly created) paragraph.
            mMarkupStartsTable.put(sdt, para);
            mMarkupEndsTable.put(sdt, para);
        }
        else
        {
            // MS Word 2019 logic is much simpler.
            Node node = !isMapped
                ? ms.as(firstNode.getNextNonAnnotationSibling(), Paragraph.class)
                : null;

            // WORDSNET-26378 MS Word moves the SDT with empty sdtContent located at the row level inside the
            // first cell encountered. This cell's Paragraph becomes the SDT content. If there is no paragraph
            // inside the cell, then a new empty paragraph is created.
            if ((sdt.getLevel() == MarkupLevel.ROW) && (firstNode.getNodeType() == NodeType.TABLE))
            {
                Table table = (Table)firstNode;
                if ((table.getFirstRow() != null) && (table.getFirstRow().getFirstCell() != null))
                {
                    Cell cell = table.getFirstRow().getFirstCell();
                    if (cell.getFirstParagraph() == null)
                    {
                        node = new Paragraph(firstNode.getDocument());
                        cell.getParagraphs().add(node);
                    }

                    node = cell.getFirstParagraph();
                }
                else
                {
                    if (table.getNextNonAnnotationSibling() instanceof CompositeNode)
                    {
                        node = table.getNextNonAnnotationSibling();
                    }
                    else
                    {
                        node = new Paragraph(firstNode.getDocument());
                        table.insertNext(node);
                    }
                }

                // The SDT becomes Block level after these updates.
                sdt.setLevel(MarkupLevel.BLOCK);
            }

            if (node == null)
                node = addResiliencyParagraph(firstNode);

            // Resolve SDT content with newly created paragraph.
            mMarkupStartsTable.put(sdt, node);
            mMarkupEndsTable.put(sdt, node);
        }

        return false;
    }

    /// <summary>
    /// Adds paragraph and places it in proper location depending on surrounding nodes.
    /// </summary>
    private static Paragraph addResiliencyParagraph(Node firstNode)
    {
        Paragraph para = new Paragraph(firstNode.getDocument());

        // If next sibling is table, markup is moved into first table cell.
        // It seems Word does not flush before tables.
        Table table = ms.as(firstNode.getNextNonAnnotationSibling(), Table.class);
        if ((table != null) && (table.getFirstRow() != null) && (table.getFirstRow().getFirstCell() != null))
        {
            table.getFirstRow().getFirstCell().insertAfter(para, null);
        }
        else if (firstNode.getNodeType() == NodeType.CELL)
        {
            ((Cell)firstNode).getParagraphs().add(para);
        }
        else if (firstNode instanceof Story)
        {
            // WORDSNET-27397 Insert empty paragraph as first child of header/footer.
            //
            // AM. This case reveals interesting problem. What is we have few empty SDT here?
            // The only information we have is that header/footer is a parent of SDTs, can we place such SDTs
            // properly?
            ((Story)firstNode).insertAfter(para, null);
        }
        else
            firstNode.insertNext(para);

        return para;
    }

    /// <summary>
    /// Processes IMarkupNode start event.
    /// </summary>
    private void processStart(IMarkupNode markup, Node node)
    {
        mMarkupStartStack.push(markup);

        if (markup.getLevel_IMarkupNode() != MarkupLevel.INLINE)
            mMarkupStartsTable.put(markup, node);
    }

    private static void insertAsRangedSdt(IMarkupNode cxm, Node firstNode, Node lastNode)
    {
        DocumentBase doc = firstNode.getDocument();

        ((StructuredDocumentTag)cxm).updateId();
        int sdtId = ((StructuredDocumentTag)cxm).getId();

        StructuredDocumentTagRangeStart rangeStart =
            new StructuredDocumentTagRangeStart(doc, (StructuredDocumentTag)cxm);

        StructuredDocumentTagRangeEnd rangeEnd = new StructuredDocumentTagRangeEnd(doc, sdtId);

        firstNode.insertPrevious(rangeStart);
        lastNode.insertNext(rangeEnd);
    }

    /// <summary>
    /// Gets an appropriate node for the end of a structured document tag range. If a position after the specified
    /// node is not correct for <see cref="StructuredDocumentTagRangeEnd"/>, finds and returns its ancestor, which
    /// can be the last node in the range.
    /// </summary>
    private static Node getActualRangeEndNode(Node node)
    {
        Node currentNode = node;
        while (currentNode.getParentNode() != null)
        {
            if (currentNode.getParentNode().getNodeType() == NodeType.BODY)
                return currentNode;

            currentNode = currentNode.getParentNode();
        }

        return node;
    }

    /// <summary>
    /// Returns list of nodes (one-level) that are located inside the specified mark-up node in the reading document
    /// file. Final contents of the mark-up node may be different than the read node list (<see cref="Resolve"/>).
    /// </summary>
    ArrayList<Node> getReadMarkupRange(IMarkupNode markupNode)
    {
        Node node1 = DictionaryExtension.getValueOrNull(mMarkupStartsTable, markupNode);
        Node node2 = DictionaryExtension.getValueOrNull(mMarkupEndsTable, markupNode);

        return commonParentRange(node1, node2);
    }

    /// <summary>
    /// Content resolver for inline SDT.
    /// </summary>
    InlineMarkupResolver getInlineResolver() { return mInlineResolver; }

    /// <summary>
    /// Gets the last ended markup.
    /// </summary>
    IMarkupNode getLastMarkup() { return StackExtension.top(mMarkupReadyStack); }

    /// <summary>
    /// Gets end node of the last ended markup.
    /// </summary>
    Node getEndNodeOfLastMarkup()
    {
        if (mMarkupReadyStack.getCount() == 0)
            return null;

        IMarkupNode lastMarkup = mMarkupReadyStack.peek();
        return mMarkupEndsTable.get(lastMarkup);
    }

    /// <summary>
    /// Gets a flag indicating that the first child of a new markup node is being read.
    /// </summary>
    boolean isInStartingMarkup() { return mPendingStarts.size() > 0; }

    /// <summary>
    /// Returns common parent node range.
    /// </summary>
    /// <remarks>
    /// AM. In most cases nodes with custom XML markup have the same depth in our model.
    /// But I could made test file where custom XML markup nodes have different depth, see TestCustomXmlDiffLevel.doc.
    /// In this file markup starts in paragraph before table and ends in last cell of table.
    /// This is the reason I use slightly more complex algorithm to find common parent range.
    /// </remarks>
    /// <returns>List of nodes or null if range couldn't be found.</returns>
    private static ArrayList<Node> commonParentRange(Node node1, Node node2)
    {
        int nodeDepth1 = NodeUtil.getDepth(node1);
        int nodeDepth2 = NodeUtil.getDepth(node2);

        int nodeDepth = Math.min(nodeDepth1, nodeDepth2);

        // Sync node levels
        node1 = NodeUtil.getNthParent(node1, nodeDepth1 - nodeDepth);
        node2 = NodeUtil.getNthParent(node2, nodeDepth2 - nodeDepth);

        // Node levels are the same so we can climb while common parent for nodes not found.
        while((node1 != null) && (node2 != null) && (node1.getParentNode() != node2.getParentNode()))
        {
            node1 = node1.getParentNode();
            node2 = node2.getParentNode();
        }

        if ((node1 != null) && (node2 != null))
        {
            // Found not null nodes with common parent, build range
            ArrayList<Node> range = new ArrayList<Node>();

            // andrnosk: WORDSNET-5422 Check if node is not null.
            while (node1 != null && node1 != node2)
            {
                msArrayList.add(range, node1);
                node1 = node1.getNextSibling();
            }

            msArrayList.add(range, node2);
            return range;
        }
        else
            return null;
    }

    /// <summary>
    /// Insert a custom XML node as parent of nodes range which already is in the Model tree.
    /// This method removes all nodes within given range from their parent (all nodes in range have the same parent node),
    /// insert IMarkupNode node and load all range as child nodes of custom XML.
    /// </summary>
    private static void insertAsParent(CompositeNode markup, ArrayList<Node> range)
    {
        Node firstInRange = range.get(0);

        // Insert custom XML node before first node in range.
        firstInRange.insertPrevious(markup);

        // Move all nodes from range to custom XML.
        for (Node n : range)
            markup.appendChild(n);
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    private void warn(String description)
    {
        if (mLoadOptions.getWarningCallback() != null)
            mLoadOptions.getWarningCallback().warning(new WarningInfo(WarningType.DATA_LOSS, mWarningSource, description));
    }

    /// <summary>
    /// Content resolver for inline SDT's.
    /// </summary>
    private /*final*/ InlineMarkupResolver mInlineResolver;

    /// <summary>
    /// All markup nodes are pushed into this stack on their starts. Main purpose of this stack to get markup start/end synced.
    /// </summary>
    private /*final*/ msStack<IMarkupNode> mMarkupStartStack = new msStack<IMarkupNode>();

    /// <summary>
    /// All markup nodes except Inline are pushed into this stack on their ends and will be resolved later
    /// i.e it's ready to be applied markups.
    /// </summary>
    private /*final*/ msStack<IMarkupNode> mMarkupReadyStack = new msStack<IMarkupNode>();

    /// <summary>
    /// Stores nodes where IMarkupNode of block level is started. Keys are IMarkupNode nodes and values are Node where
    /// markups were started.
    /// </summary>
    private /*final*/ HashMap<IMarkupNode, Node> mMarkupStartsTable = new HashMap<IMarkupNode, Node>();
    private /*final*/ HashMap<IMarkupNode, Node> mMarkupEndsTable = new HashMap<IMarkupNode, Node>();
    private /*final*/ ArrayList<IMarkupNode> mPendingStarts = new ArrayList<IMarkupNode>();

    /// <summary>
    /// List of structured document tag with empty content.
    /// </summary>
    private /*final*/ ArrayList<IMarkupNode> mEmptySdtList = new ArrayList<IMarkupNode>();

    private /*final*/ /*WarningSource*/int mWarningSource;
    private /*final*/ LoadOptions mLoadOptions;
}

