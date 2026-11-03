// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/06/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ArgumentUtil;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;


/// <summary>
/// Represents a single bookmark.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-bookmarks/">Working with Bookmarks</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p><see cref="Bookmark"/> is a "facade" object that encapsulates two nodes <see cref="BookmarkStart"/>
/// and <see cref="BookmarkEnd"/> in a document tree and allows to work with a bookmark as a single object.</p>
/// </remarks>
public class Bookmark
{
    /// <summary>
    /// Creates a <see cref="Bookmark"/> instance with defined start and lazily initialized end.
    /// </summary>
    Bookmark(BookmarkStart bookmarkStart)
    {
        ArgumentUtil.checkNotNull(bookmarkStart, "bookmarkStart");
        mBookmarkStart = bookmarkStart;
    }

    /// <summary>
    /// Creates a <see cref="Bookmark"/> instance with defined start and end.
    /// </summary>
    Bookmark(BookmarkStart bookmarkStart, BookmarkEnd bookmarkEnd)
    {
    	this(bookmarkStart);
	
        ArgumentUtil.checkNotNull(bookmarkEnd, "bookmarkEnd");
        mBookmarkEnd = bookmarkEnd;
    }

    /// <summary>
    /// Gets or sets the name of the bookmark.
    /// </summary>
    /// <remarks>
    /// Note that if you change the name of a bookmark to a name that already exists in the document,
    /// no error will be given and only the first bookmark will be stored when you save the document.
    /// </remarks>
    public String getName() { return getBookmarkStart().getName(); }
    public void setName(String value) throws Exception
    {
        ArgumentUtil.checkNotNull(value, "value");

        //Need to change end name before start name since bookmark end is looked up using start name.
        getBookmarkEnd().setNameInternal(value);
        getBookmarkStart().setNameInternal(value);
    }

    /// <summary>
    /// Gets or sets the text enclosed in the bookmark.
    /// </summary>
    public String getText() throws Exception
    {
        return getText(false);
    }
    public void setText(String value) throws Exception
    {
        ArgumentUtil.checkNotNull(value, "value");

        if (isColumn())
            setColumnBookmarkText(value);
        else
            setTextInternal(value);
    }

    private void setTextInternal(String text) throws Exception
    {
        untangleStart();
        untangleEnd();

        RunPr sourceRunPr = getSourceRunPr();

        RangeBound startBound = AnnotationUtil.getAnnotationRangeBound(getBookmarkStart(), true);
        // If DisplacedBy has incorrect value, just reset it for now.
        if (startBound == null)
        {
            getBookmarkStart().setDisplacedBy(DisplacedByType.UNSPECIFIED);
            startBound = AnnotationUtil.getAnnotationRangeBound(getBookmarkStart(), true);
        }

        RangeBound endBound = AnnotationUtil.getAnnotationRangeBound(getBookmarkEnd(), false);
        // If DisplacedBy has incorrect value, just reset it for now.
        if (endBound == null)
        {
            getBookmarkEnd().setDisplacedBy(DisplacedByType.UNSPECIFIED);
            endBound = AnnotationUtil.getAnnotationRangeBound(getBookmarkEnd(), false);
        }

        Node insertPosition = getBookmarkEnd();
        NodeRange nodeRange = createNodeRange(startBound, endBound, null);

        if ((startBound != null) && (endBound != null))
        {
            // Position before paragraph equals position at first child of paragraph, so move end into nearest
            // paragraph for correct paragraph removal and merging like in inline bookmarks mode.
            // (Bookmark end is expected to be at inline level at this case in result.)
            // Do the same with all other annotations at this position to prevent them from being removed.
            if ((endBound.getNode() == getBookmarkEnd()) &&
                (getBookmarkEnd().getDisplacedBy() == DisplacedByType.UNSPECIFIED) &&
                (getBookmarkEnd().getNodeLevel() != NodeLevel.INLINE) &&
                (endBound.getNode().getNextNonAnnotationSibling() != startBound.getNode().getNextNonAnnotationSibling()))
            {
                moveAnnotationsToNextParagraph(getBookmarkEnd());
            }

            BookmarkSafeNodeRemover remover =
                new BookmarkSafeNodeRemover(nodeRange, NodeJoinMode.JOIN_TO_NEXT_SIBLING, true);
            remover.removeCore();
            restoreRemovedStartEnd(startBound, endBound);
            insertPosition = prepareTextInsertPosition();
        }

        // Insert the new text.
        // todo 0 rk document builder does not work with glossary yet
        DocumentBuilder builder = new DocumentBuilder(getBookmarkStart().fetchDocument());
        builder.moveTo(insertPosition);

        sourceRunPr = AttrCollectionExtension.deepClone(sourceRunPr);
        RevisionUtil.removeEditRevisions(sourceRunPr);
        builder.setFont(sourceRunPr, false);

        insertText(builder, text);
    }

    private static void insertText(DocumentBuilder builder, String text)
    {
        // DocumentBuilder.Write(String) is not used for the entire text as it inserts new paragraphs after the current one,
        // but to mimic MS Word, new paragraphs should be inserted before the current one. Also special processing
        // for revision tracking is needed.

        // Convert CrLf and Lf into Crs.
        String normalizedText = WordUtil.normalizeToWord(text);

        // If text had any Crs insert paragraphs as appropriate.
        int paragraphStart = 0;
        while (paragraphStart <= normalizedText.length())
        {
            int paragraphEnd = normalizedText.indexOf(ControlChar.PARAGRAPH_BREAK_CHAR, paragraphStart);
            boolean isParagraphBreakCharFound = (paragraphEnd >= 0);
            if (!isParagraphBreakCharFound)
                paragraphEnd = normalizedText.length();

            int length = paragraphEnd - paragraphStart;
            if (length > 0)
                builder.write(normalizedText.substring((paragraphStart), (paragraphStart) + (length)));

            if (isParagraphBreakCharFound)
                insertParagraph(builder);

            paragraphStart = paragraphEnd + 1;
        }
    }

    private static void insertParagraph(DocumentBuilder builder)
    {
        Document document = builder.getDocument();
        Paragraph currentParagraph = builder.getCurrentParagraph();

        // New paragraph gets a copy of the current paragraph attributes.
        Paragraph newPara = new Paragraph(document, builder.getParaPrCopy(), builder.getRunPrCopy());
        RevisionUtil.removeEditRevisions(newPara.getParagraphBreakRunPr());

        // Add new paragraph node before the current paragraph.
        currentParagraph.insertPrevious(newPara);

        // Move nodes before the insertion point to the new paragraph.
        SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(document);
        try /*JAVA: was using*/
        {
            newPara.insertBefore(currentParagraph.getFirstChild(), builder.getCurrentNode(), null);
        }
        finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }
    }

    private void setColumnBookmarkText(String text)
    {
        Debug.assertTrue(text != null);

        // Word ignores end of cell character.
        text = text.replace(ControlChar.CELL, "");

        // WORDSNET-24087 Take sibling Row as a parent in case when
        // bookmark is placed just inside a Table to mimic Word.
        Row row = (getBookmarkStart().getParentNode().getNodeType() == NodeType.TABLE)
            ? (Row)getBookmarkStart().nextSiblingOfType(NodeType.ROW)
            : ms.as(getBookmarkStart().getAncestor(NodeType.ROW), Row.class);

        if (row != null)
        {
            // Word throws an exception here. Maybe we should throw an exception too.
            if ((getFirstColumn() >= row.getCells().getCount()) && (getLastColumn() >= row.getCells().getCount()))
                return;

            int column = getLastColumn() > getFirstColumn() ? getFirstColumn() : getLastColumn();

            Cell cell = row.getCells().get(column);
            Paragraph para = cell.getFirstParagraph();
            RunPr runPr = para.getRuns().getCount() > 0 ? para.getFirstRun().getRunPr() : new RunPr();

            prepareCell(cell);

            if (!msString.isNullOrEmpty(text))
            {
                Run run = new Run(getBookmarkStart().getDocument(), text, runPr);
                para.appendChild(run);
            }

            cell.insertAfter(para, null);
        }
    }

    private void prepareCell(Cell cell)
    {
        Paragraph firstPara = cell.getFirstParagraph();

        // Word moves bookmarkStarts to the first paragraph from other paragraphs.
        for (int i = 1; i < cell.getParagraphs().getCount(); i++)
        {
            for (Node child : (Iterable<Node>) cell.getParagraphs().get(i))
            {
                if (child.getNodeType() == NodeType.BOOKMARK_START)
                    firstPara.appendChild(child);
            }
        }

        cell.getChildNodes(NodeType.ANY, false).clear();

        // Prepare first paragraph.
        for (Node child : (Iterable<Node>) firstPara.getChildNodes(NodeType.ANY, false))
            if (!NodeUtil.isBookmarkNode(child))
                child.remove();
    }

    private RangeBound getRangeBound(boolean forRangeStart) throws Exception
    {
        Node bookmarkBound = forRangeStart ? (Node)getBookmarkStart() : getBookmarkEnd();

        return isColumn()
            ? getColumnRangeBound(forRangeStart)
            : AnnotationUtil.getAnnotationRangeBound(bookmarkBound, forRangeStart);
    }

    private RangeBound getColumnRangeBound(boolean forRangeStart) throws Exception
    {
        Debug.assertTrue(isColumn());

        Cell cellBound = forRangeStart ? getBookmarkStartCellBound() : getBookmarkEndCellBound();

        return cellBound == null
            ? null
            : new RangeBound(cellBound, true, null, null);
    }

    private Cell getBookmarkEndCellBound() throws Exception
    {
        Cell cellBound = null;
        Row row = (Row)getBookmarkEnd().getAncestor(NodeType.ROW);
        Table table = (Table)getBookmarkStart().getAncestor(NodeType.TABLE);

        // Make sure ancestor row does not belong to outer table.
        if ((row != null) && (row.getParentTable() != table))
            row = null;

        // BookmarkEnd can be located between the rows, after the last row and in the paragraph after the table.
        if (row == null)
        {
            row = ms.as(getBookmarkEnd().getPreviousNonAnnotationSibling(), Row.class);

            if ((row == null) && (table != null))
                row = table.getLastRow();
        }

        if (row != null)
        {
            if ((getFirstColumn() >= row.getCells().getCount()) && (getLastColumn() >= row.getCells().getCount()))
                return null;

            boolean lastColumnOutOfRange = row.getCells().getCount() <= getLastColumn();
            cellBound = lastColumnOutOfRange ? row.getLastCell() : row.getCells().get(getLastColumn());

            Row bookmarkStartRow = ms.as(getBookmarkStart().getAncestor(NodeType.ROW), Row.class);
            if ((bookmarkStartRow != null) && bookmarkStartRow.isAbove(row) && getBookmarkEnd().isAbove(cellBound))
            {
                row = row.getPreviousRow();
                cellBound = lastColumnOutOfRange ? row.getLastCell() : row.getCells().get(getLastColumn());
            }
        }

        Debug.assertTrue(cellBound != null);
        return cellBound;
    }

    private Cell getBookmarkStartCellBound()
    {
        Cell cellBound = null;
        Row row = ms.as(getBookmarkStart().getAncestor(NodeType.ROW), Row.class);

        if (row != null)
        {
            if ((getFirstColumn() >= row.getCells().getCount()) && (getLastColumn() >= row.getCells().getCount()))
                return null;

            cellBound = getLastColumn() < getFirstColumn() ? row.getCells().get(getLastColumn()) : row.getCells().get(getFirstColumn());
        }

        Debug.assertTrue(cellBound != null);
        return cellBound;
    }

    /// <summary>
    /// Returns text enclosed in the bookmark.
    /// </summary>
    /// <param name="isFieldResultMode">
    /// <c>true</c> to include field results only, <c>false</c> to include all field parts that includes field code, field result
    /// and field control characters.
    /// </param>
    /// <returns></returns>
    String getText(boolean isFieldResultMode) throws Exception
    {
        RangeBound startBound = getRangeBound(true);
        RangeBound endBound = getRangeBound(false);
        if ((startBound == null) || (endBound == null))
            return "";

        return NodeTextCollector.getText(startBound.getNode(), startBound.isNodeIncluded(),
            endBound.getNode(), endBound.isNodeIncluded(), isFieldResultMode);
    }

    /// <summary>
    /// It is possible to for many bookmark starts and ends in any order to occur at one position in a document.
    /// It means that our bookmark can "entangle" some of those starts and ends both at the start and end.
    /// When we are setting (or deleting) the bookmark text, we don't want to delete starts or ends of such
    /// heighbouring bookmarks. This method tries to "untangle" bookmark starts and ends at the same position.
    ///
    /// Untangling start means moving our bookmark start to be after all bookmark starts and ends at the given position.
    /// Untangling end means moving our bookmark end to be before all bookmark starts and ends at the given position.
    /// </summary>
    private void untangleStart()
    {
        if (getBookmarkStart() == null)
            return;

        // Loop forward until we reach end of a parent or a non bookmark node.
        Node curNode = getBookmarkStart();
        do
        {
            curNode = curNode.getNextSibling();
        }
        while ((curNode != null) && isBookmarkNodeToSkip(curNode));

        // CurNode is now pointing to a first non-bookmark node or end of parent.
        // Move the bookmark start to be just before that node unless it is already there.
        if (getBookmarkStart().getNextSibling() != curNode)
            getBookmarkStart().getParentNode().insertBefore(getBookmarkStart(), curNode);
    }

    /// <summary>
    /// This is a complete reverse of <see cref="UntangleStart"/>
    /// </summary>
    private void untangleEnd() throws Exception
    {
        Node curNode = getBookmarkEnd();
        do
        {
            curNode = curNode.getPreviousSibling();
        }
        while ((curNode != null) && isBookmarkNodeToSkip(curNode));

        if (getBookmarkEnd().getPreviousSibling() != curNode)
            getBookmarkEnd().getParentNode().insertAfter(getBookmarkEnd(), curNode);
    }

    /// <summary>
    /// We skip bookmark nodes that belong to different bookmarks.
    /// </summary>
    private boolean isBookmarkNodeToSkip(Node node)
    {
        IBookmarkNode otherBookmark = ms.as(node, IBookmarkNode.class);
        return (otherBookmark != null) && !StringUtil.equalsIgnoreCase(getName(), otherBookmark.getName());
    }

    /// <summary>
    /// Gets source run properties for bookmark text.
    /// </summary>
    /// <returns></returns>
    private RunPr getSourceRunPr()
    {
        Node node;
        Paragraph parentParagraph;
        if (mBookmarkStart.getNodeLevel() != NodeLevel.INLINE)
        {
            parentParagraph = NodeUtil.findNextParagraph(mBookmarkStart);
            if (parentParagraph == null)
                return new RunPr();

            node = parentParagraph.getFirstNonMarkupDescendant();
        }
        else
        {
            parentParagraph = (Paragraph)mBookmarkStart.getAncestor(NodeType.PARAGRAPH);
            node = mBookmarkStart.getNextNonMarkupNodeLimited();
        }

        while ((node != null) && !(node instanceof Inline))
            node = node.getNextNonMarkupNodeLimited();

        if (node != null)
            return ((Inline)node).getRunPr();

        return parentParagraph.getParagraphBreakRunPr();
    }

    /// <summary>
    /// Gets the node that represents the start of the bookmark.
    /// </summary>
    public BookmarkStart getBookmarkStart() { return mBookmarkStart; }

    /// <summary>
    /// Gets the node that represents the end of the bookmark.
    /// </summary>
    public BookmarkEnd getBookmarkEnd() throws Exception
    {
        if (mBookmarkEnd == null)
            mBookmarkEnd = BookmarkFinder.fetchBookmarkEnd(mBookmarkStart.getTopmostAncestor(), getName(), mBookmarkStart);

        return mBookmarkEnd;
    }

    /// <summary>
    /// Returns <c>true</c> if this bookmark is a table column bookmark.
    /// </summary>
    public boolean isColumn() { return mBookmarkStart.isColumn(); }

    /// <summary>
    /// Gets the zero-based index of the first column of the table column range associated with the bookmark.
    /// </summary>
    /// <remarks>
    /// Returns <b>-1</b> if this bookmark is not a table column bookmark.
    /// </remarks>
    public int getFirstColumn() { return mBookmarkStart.getFirstColumn(); }

    /// <summary>
    /// Gets the zero-based index of the last column of the table column range associated with the bookmark.
    /// </summary>
    /// <remarks>
    /// Returns <b>-1</b> if this bookmark is not a table column bookmark.
    /// </remarks>
    public int getLastColumn() { return mBookmarkStart.getLastColumn(); }

    /// <summary>
    /// Removes the bookmark from the document. Does not remove text inside the bookmark.
    /// </summary>
    public void remove() throws Exception
    {
        getBookmarkEnd().remove();
        getBookmarkStart().remove();
    }

    /// <summary>
    /// Gets a node range starting at <see cref="BookmarkStart"/> and ending at <see cref="BookmarkEnd"/>.
    /// Range start and end points are excluded.
    /// </summary>
    NodeRange getNodeRange() throws Exception
    {
        return getNodeRange(getBookmarkStart(), getBookmarkEnd());
    }

    /// <summary>
    /// Gets a node range between <see cref="BookmarkStart"/> and <see cref="BookmarkEnd"/> and includes sibling bookmark nodes.
    /// </summary>
    NodeRange getMostWideNodeRange() throws Exception
    {
        NodeRange range = getNodeRange();

        Node start = range.getStart().getNode();
        if ((start.getNodeType() == NodeType.BOOKMARK_START) && !((BookmarkStart)start).isColumn())
        {
            while ((start.getPreviousSibling() != null) &&
                (start.getPreviousSibling().getNodeType() == NodeType.BOOKMARK_START) &&
                !((BookmarkStart)start.getPreviousSibling()).isColumn())
            {
                start = start.getPreviousSibling();
            }
        }

        Node end = range.getEnd().getNode();
        if (end.getNodeType() == NodeType.BOOKMARK_END)
            while (end.getNextSibling() != null && end.getNextSibling().getNodeType() == NodeType.BOOKMARK_END)
                end = end.getNextSibling();

        return new NodeRange(start, start.getNodeType() == NodeType.BOOKMARK_START, end, end.getNodeType() == NodeType.BOOKMARK_END, ms.as(start, BookmarkStart.class));
    }

    static NodeRange getNodeRange(BookmarkStart bookmarkStart, BookmarkEnd bookmarkEnd)
    {
        RangeBound startBound = AnnotationUtil.getAnnotationRangeBound(bookmarkStart, true);
        RangeBound endBound = bookmarkEnd.getDisplacedBy() != DisplacedByType.UNSPECIFIED
            ? AnnotationUtil.getAnnotationRangeBound(bookmarkEnd, false)
            : new RangeBound(getNodeRangeEndNode(bookmarkStart, bookmarkEnd), false);
        if ((startBound == null) || (endBound == null))
            return NodeRange.VOID;

        NodeRange range = createNodeRange(startBound, endBound, bookmarkStart);

        int fieldCharCount = 0;
        for (Node node : range)
        {
            boolean isFieldCodeEnd = false;
            switch (node.getNodeType())
            {
                case NodeType.FIELD_START:
                    fieldCharCount++;
                    break;
                case NodeType.FIELD_SEPARATOR:
                    isFieldCodeEnd = true;
                    break;
                case NodeType.FIELD_END:
                    isFieldCodeEnd = !((FieldEnd)node).hasSeparator();
                    break;
                default:
                    // Do nothing.
                    break;
            }

            if (isFieldCodeEnd)
            {
                if (fieldCharCount == 0)
                {
                    // The bookmark starts within a field code. Let's complete or truncate the range from start.
                    Node startNode = getNodeRangeStartNode(startBound, node);
                    return new NodeRange(startNode, true, endBound.getNode(), endBound.isNodeIncluded(), bookmarkStart);
                }

                fieldCharCount--;
            }
        }

        return range;
    }

    /// <summary>
    /// Creates node range for the specified bounds.
    /// </summary>
    private static NodeRange createNodeRange(RangeBound startBound, RangeBound endBound, BookmarkStart bookmarkStart)
    {
        return new NodeRange(startBound.getNode(), startBound.isNodeIncluded(),
            endBound.getNode(), endBound.isNodeIncluded(), bookmarkStart);
    }

    private static Node getNodeRangeEndNode(BookmarkStart bookmarkStart, BookmarkEnd bookmarkEnd)
    {
        // If a bookmark end is at beginning of the first row or cell of a table, it is equivalent to
        // a bookmark before the table: the table should not be included in the range.
        if (((bookmarkEnd.getParentNode().getNodeType() != NodeType.PARAGRAPH) ||
                !NodeUtil.isCellLevelNode(bookmarkEnd.getParentNode().getParentNode())) &&
            !NodeUtil.isCellLevelNode(bookmarkEnd.getParentNode()) &&
            !NodeUtil.isRowLevelNode(bookmarkEnd.getParentNode()) &&
            (bookmarkEnd.getNodeLevel() != NodeLevel.ROW))
        {
            return bookmarkEnd;
        }

        // Move upward until:
        // - a parent which is not the first child (excluding other bookmark and comment starts/ends) itself is met or
        // - a parent which contains the specified bookmark start is met.
        for (Node endNode = bookmarkEnd; ; endNode = endNode.getParentNode()) // return inside.
        {
            for (Node node = endNode.getParentNode().getFirstChild(); node != endNode; node = node.getNextSibling())
            {
                if ((node == bookmarkStart) || !NodeUtil.isCrossStructureAnnotation(node))
                    return endNode;
            }
        }
    }

    /// <summary>
    /// Gets correct start node of bookmark range if the bookmark is in a field.
    /// </summary>
    private static Node getNodeRangeStartNode(RangeBound startBound, Node fieldCodeEnd)
    {
        // If the first item of a bookmark is a field, then the bookmark's start is sometimes placed after the field's
        // start by MS Word. But we need to consider such fields either. On the other hand, if the bookmark starts
        // inside a field code, it may not occupy the whole field, so we should not consider the field in this case.
        // So let's move backward from the bookmark's start until any inline node and if this node is the field's start,
        // then consider the whole field, otherwise consider its result only. Note, that other cross-structure annotation
        // nodes should be ignored while searching.
        //
        // This can make the bookmark start fall within the result node range. However, it seems like there is nothing
        // bad in this.
        DocumentPosition position = startBound.isNodeIncluded()
            ? DocumentPosition.createPositionBefore(startBound.getNode())
            : DocumentPosition.createPositionAfter(startBound.getNode());
        while (position.move(null, false, true, true, false, false))
        {
            Node node = position.getNode();
            if (NodeUtil.isInlineLevelNode(node) && !NodeUtil.isCrossStructureAnnotation(node))
                return (node.getNodeType() == NodeType.FIELD_START) ? node : fieldCodeEnd;
        }

        return fieldCodeEnd;
    }

    /// <summary>
    /// If bookmark start or end are removed during removal of bookmark range, this method restores them
    /// at correct positions.
    /// </summary>
    /// <dev>
    /// The bookmark nodes may be removed because of the following. Real range of bookmarks with defined
    /// displacedByCustomXml attribute differ from range driven by bookmark position. So, bookmark nodes may
    /// be included in real bookmark range and may be removed during removing nodes of bookmark range.
    /// For example, real range of this bookmark:
    /// <sdt>
    /// <sdtContent>
    ///   <p>
    ///     <r>
    ///     </r>
    ///   </p>
    /// </sdtContent>
    /// </sdt>
    /// <bookmarkStart displacedByCustomXml = "prev" />
    /// is started just after the run. The bookmark will be removed on removing nodes of the bookmark range.
    /// We need to restore it after removing the range nodes.
    /// </dev>
    private void restoreRemovedStartEnd(RangeBound startOfRemovedRange, RangeBound endOfRemovedRange) throws Exception
    {
        if (isNodeRemoved(getBookmarkStart()))
        {
            Node sdtNode = startOfRemovedRange.getDisplacingNode();
            if ((sdtNode != null) && !isNodeRemoved(sdtNode))
            {
                if (sdtNode.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
                {
                    // StructuredDocumentTag.
                    CompositeNode sdt = (CompositeNode)sdtNode;
                    insertAtDisplacedSdt(getBookmarkStart(), sdt, startOfRemovedRange.isAnnotationAChildOfSdt());
                }
                else
                {
                    // StructuredDocumentTagRangeStart or StructuredDocumentTagRangeEnd.
                    sdtNode.getParentNode().insert(getBookmarkStart(), sdtNode,
                        getBookmarkStart().getDisplacedBy() == DisplacedByType.PREV);
                }
            }
            else
            {
                startOfRemovedRange.getParent().insert(getBookmarkStart(), startOfRemovedRange.isNodeIncluded() ?
                    startOfRemovedRange.getPreviousSibling() : startOfRemovedRange.getNode(), true);

                getBookmarkStart().setDisplacedBy(DisplacedByType.UNSPECIFIED);
            }
        }

        if (isNodeRemoved(getBookmarkEnd()))
        {
            Node sdtNode = endOfRemovedRange.getDisplacingNode();
            if ((sdtNode != null) && !isNodeRemoved(sdtNode))
            {
                if (sdtNode.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
                {
                    // StructuredDocumentTag.
                    CompositeNode sdt = (CompositeNode)sdtNode;
                    insertAtDisplacedSdt(getBookmarkEnd(), sdt, endOfRemovedRange.isAnnotationAChildOfSdt());
                }
                else
                {
                    // StructuredDocumentTagRangeStart or StructuredDocumentTagRangeEnd.
                    sdtNode.getParentNode().insert(getBookmarkEnd(), sdtNode, getBookmarkEnd().getDisplacedBy() == DisplacedByType.PREV);
                }
            }
            else
            {
                endOfRemovedRange.getParent().insert(getBookmarkEnd(), endOfRemovedRange.isNodeIncluded() ?
                    endOfRemovedRange.getNextSibling() : endOfRemovedRange.getNode(), false);

                getBookmarkEnd().setDisplacedBy(DisplacedByType.UNSPECIFIED);
            }
        }
    }

    /// <summary>
    /// Returns <c>true</c> if the specified node has been removed from a document.
    /// </summary>
    private static boolean isNodeRemoved(Node node)
    {
        return node.getAncestor(NodeType.DOCUMENT) == null;
    }

    /// <summary>
    /// Moves the specified annotation and everyone else in the same position at block level to the nearest paragraph.
    /// </summary>
    private static void moveAnnotationsToNextParagraph(Node annotation)
    {
        Debug.assertTrue(NodeUtil.isCrossStructureAnnotation(annotation));
        Debug.assertTrue(annotation.getNodeLevel() != NodeLevel.INLINE);

        // Get interval of annotation nodes in this position.
        Node previousNonAnnotation = annotation.getPreviousNonAnnotationSibling();
        Node startAnnotation = (previousNonAnnotation == null)
            ? annotation.getParentNode().getFirstChild()
            : previousNonAnnotation.getNextSibling();
        Node nextNonAnnotation = annotation.getNextNonAnnotationSibling();

        // Find destination node.
        CompositeNode nextNode = annotation.getNextNonMarkupCompositeLimited();
        Node newPositionNode = (nextNode != null) ? AnnotationUtil.getDeepestChild(nextNode, true) : null;
        if (newPositionNode != null)
        {
            // Insert into a composite node (e.g. a paragraph or inline SDT), but not into a shape or comment.
            if (newPositionNode.isComposite() && !NodeUtil.isStoryNodeType(newPositionNode))
                ((CompositeNode)newPositionNode).insertAfter(startAnnotation, nextNonAnnotation, null);
            else
                newPositionNode.insertPrevious(startAnnotation, nextNonAnnotation);
        }
    }

    /// <summary>
    /// Gets a node to insert text before it on setting the <see cref="Text"/> property.
    /// Also the bookmark start or end may be moved for new text to be placed inside bookmark range
    /// (for new text to be included in result on getting from the <see cref="Text"/> property).
    /// </summary>
    private Node prepareTextInsertPosition() throws Exception
    {
        Node insertPosition = getBookmarkEnd();

        // Move in accordance with DisplacedBy.
        if (getBookmarkEnd().getDisplacedBy() != DisplacedByType.UNSPECIFIED)
        {
            RangeBound endBound = AnnotationUtil.getAnnotationRangeBound(getBookmarkEnd(), false);
            if (endBound != null)
            {
                Node endNode = endBound.getNode();
                boolean isAfterEndNode = endBound.isNodeIncluded();

                if (endNode != getBookmarkEnd())
                {
                    boolean needMoveEnd =
                        ((getBookmarkEnd().getDisplacedBy() == DisplacedByType.NEXT) &&
                            (endBound.getDisplacingNode() != null) &&
                            !getBookmarkEnd().isAncestorNode(endBound.getDisplacingNode())) ||
                        ((getBookmarkEnd().getDisplacedBy() == DisplacedByType.PREV) &&
                            (endBound.getDisplacingNode() != null) &&
                            getBookmarkEnd().isAncestorNode(endBound.getDisplacingNode()));
                    if (needMoveEnd)
                    {
                        endNode.getParentNode().insert(getBookmarkEnd(), endNode, isAfterEndNode);
                        getBookmarkEnd().setDisplacedBy(DisplacedByType.UNSPECIFIED);
                    }
                    else
                    {
                        insertPosition = endBound.getNode();
                        if (isAfterEndNode)
                        {
                            insertPosition = insertPosition.getNextOrParent();
                            //if (insertPosition == BookmarkStart)
                            //    insertPosition = insertPosition.NextOrParent;
                        }
                    }
                }
            }
        }

        // Move bookmark start for inserting text to be after it.
        RangeBound startBound = AnnotationUtil.getAnnotationRangeBound(getBookmarkStart(), true);
        if (startBound != null)
        {
            Node startNode = startBound.isNodeIncluded() ? startBound.getNode() : startBound.getNode().getNextOrParent();

            boolean needMoveStart = insertPosition.isAbove(startNode) && !startNode.isAncestorNode(insertPosition);
            if (!needMoveStart && (insertPosition == startNode))
            {
                needMoveStart =
                    ((getBookmarkStart().getDisplacedBy() == DisplacedByType.PREV) &&
                            (startBound.getDisplacingNode() != null) &&
                            !getBookmarkStart().isAncestorNode(startBound.getDisplacingNode())) ||
                    ((getBookmarkStart().getDisplacedBy() == DisplacedByType.NEXT) &&
                        (startBound.getDisplacingNode() != null) &&
                        getBookmarkStart().isAncestorNode(startBound.getDisplacingNode()));
            }

            if (needMoveStart)
            {
                if (insertPosition.isComposite())
                    ((CompositeNode)insertPosition).appendChild(getBookmarkStart());
                else
                    insertPosition.getParentNode().insert(getBookmarkStart(), insertPosition, false);

                getBookmarkStart().setDisplacedBy(DisplacedByType.UNSPECIFIED);
            }
        }

        // Move to inline level.
        if ((insertPosition.getNodeType() != NodeType.PARAGRAPH) && (insertPosition.getNodeLevel() != NodeLevel.INLINE))
        {
            Paragraph paragraph = NodeUtil.findNextParagraph(insertPosition);
            if (paragraph == null)
            {
                Node story = insertPosition.getStoryAncestor(NodeType.BODY);
                if (story == null)
                    story = insertPosition.getStoryAncestor(NodeType.ANY);

                if ((story != null) && story.isComposite())
                {
                    CompositeNode compositeStory = (CompositeNode)story;
                    paragraph = new Paragraph(getBookmarkEnd().getDocument());
                    if (compositeStory.canInsert(paragraph))
                        compositeStory.appendChild(paragraph);
                    else
                        paragraph = null;
                }
            }
            if (paragraph != null)
            {
                paragraph.insert(getBookmarkEnd(), null, true);
                getBookmarkEnd().setDisplacedBy(DisplacedByType.UNSPECIFIED);
            }
        }

        return insertPosition;
    }

    /// <summary>
    /// Inserts the specified bookmark node at correct position relatively to its displaced SDT.
    /// </summary>
    private static void insertAtDisplacedSdt(Node bookmarkNode, CompositeNode sdt, boolean asChild)
    {
        /*DisplacedByType*/int displacedBy = ((IDisplaceableByCustomXml)bookmarkNode).getDisplacedByCustomXml();
        switch (displacedBy)
        {
            case DisplacedByType.NEXT:
            case DisplacedByType.PREV:
            {
                if (asChild)
                    sdt.insert(bookmarkNode, null, displacedBy == DisplacedByType.PREV);
                else
                    sdt.getParentNode().insert(bookmarkNode, sdt, displacedBy == DisplacedByType.PREV);
                break;
            }
            default:
                Debug.assertTrue(false);
                break;
        }
    }

    private /*final*/ BookmarkStart mBookmarkStart;
    /// <summary>
    /// This is not really nice as bookmark end can be deleted from the document and this will not get updated.
    /// </summary>
    private BookmarkEnd mBookmarkEnd;

    static final String ERROR_BOOKMARK_NOT_DEFINED = "Error! Bookmark not defined.";

    /// <summary>
    /// Maximum allowed bookmark name length.
    /// </summary>
    static final int MAX_NAME_LENGTH = 40;

    static final String GO_BACK_BOOKMARK_NAME = "_GoBack";
}

