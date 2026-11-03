// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/03/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import java.util.ArrayList;
import com.aspose.ms.System.msObject;
import com.aspose.StringUtil;
import java.util.HashMap;


/// <summary>
/// A collection of <see cref="RevisionGroup"/> objects that represent revision groups in the document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/track-changes-in-a-document/">Track Changes in a Document</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>You do not create instances of this class directly. Use the <see cref="RevisionCollection.Groups"/>
/// property to get revision groups present in a document.</para>
/// </remarks>
public final class RevisionGroupCollection implements Iterable<RevisionGroup>
{
    RevisionGroupCollection(DocumentBase doc)
    {
        // Collect style definition revisions.
        for (Style style : doc.getStyles())
        {
            if (style.hasRevisions())
            {
                RevisionGroup revisionGroup = new RevisionGroup(style);
                msArrayList.add(mItems, revisionGroup);
            }
        }

        collectEditRevisions(doc, EditRevisionType.DELETION, true);
        collectEditRevisions(doc, EditRevisionType.INSERTION, true);
        collectMoveRevisions(doc);
        collectFormatRevisions(doc, NodeType.RUN);
        collectFormatRevisions(doc, NodeType.PARAGRAPH);
        collectFormatRevisions(doc, NodeType.SECTION);
        collectTableRevisions(doc);

        collectExtraEditRevisions(doc);
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<RevisionGroup> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Returns a revision group at the specified index.
    /// </summary>
    public RevisionGroup get(int index) { return mItems.get(index); }

    /// <summary>
    /// Returns the number of revision groups in the collection.
    /// </summary>
    public int getCount() { return mItems.size(); }

    /// <summary>
    /// Returns a revision group by the specified revision.
    /// </summary>
    RevisionGroup get(RevisionBase revisionBase)
    {
        return mRevisionBaseToGroupMap.containsKey(revisionBase)
            ? mRevisionBaseToGroupMap.get(revisionBase)
            : null;
    }

    /// <summary>
    /// Collects edit revision groups.
    /// </summary>
    private void collectEditRevisions(Node doc, /*EditRevisionType*/int type, boolean ignoreGroupShapeContent)
    {
        Node node = deepestNode(doc, ignoreGroupShapeContent);
        while (node != null && node != doc)
        {
            ITrackableNode trackable = ms.as(node, ITrackableNode.class);
            if ((trackable != null))
            {
                EditRevision revision = getEditRevision(trackable, type);

                node = (revision != null)
                    ? extractRevisionGroup(node, doc, revision, ignoreGroupShapeContent)
                    : nextNode(node, ignoreGroupShapeContent);
            }
            else
            {
                node = nextNode(node, ignoreGroupShapeContent);
            }
        }
    }

    /// <summary>
    /// Collects move revision groups.
    /// </summary>
    private void collectMoveRevisions(DocumentBase doc)
    {
        // In contrast with edit revision groups, a move group may contain an edit revision group inside, nodes of
        // which have no move revisions. Also, row and cell nodes in a move range may have no revisions. But a move
        // range has a start and end nodes: moveFromRangeStart/moveToRangeStart and moveFromRangeEnd/moveToRangeEnd.
        // Let's use them to get move group boundary.

        // Order of move groups is important for DocumentSpanConverter.

        Node node = deepestNode(doc, false);
        while (node != null)
        {
            if (node.getNodeType() == NodeType.MOVE_FROM_RANGE_START || node.getNodeType() == NodeType.MOVE_TO_RANGE_START)
                node = extractMoveRevisionGroup((MoveRangeStart)node);
            else
                node = nextNode(node);
        }
    }

    /// <summary>
    /// Collects extra edit revisions that MS Word collects in group shapes in textboxes.
    /// </summary>
    /// <remarks>
    /// AM. Actually these revision groups looks like MS Word bug, but we have ES customer wanting exact match.
    /// </remarks>
    private void collectExtraEditRevisions(DocumentBase doc)
    {
        // Collect extra revisions from group shapes.
        ArrayList<GroupShape> allGroupShapes = doc.getChildNodes(NodeType.GROUP_SHAPE, true).<GroupShape>toList();
        ArrayList<GroupShape> topGroupShapes = new ArrayList<GroupShape>();

        // Filter nested group shapes.
        for(GroupShape groupShape : allGroupShapes)
            if(getShapeContainer(groupShape) == groupShape)
                msArrayList.add(topGroupShapes, groupShape);

        for (GroupShape groupShape : topGroupShapes)
        {
            collectEditRevisions(groupShape, EditRevisionType.INSERTION, false);
            collectEditRevisions(groupShape, EditRevisionType.DELETION, false);
        }

        // Collect extra revisions from textboxes.
        ArrayList<Shape> allShapes = doc.getChildNodes(NodeType.SHAPE, true).<Shape>toList();
        ArrayList<Shape> topTextboxes = new ArrayList<Shape>();

        // Filter out nested textboxes.
        for(Shape shape : allShapes)
            if(shape.hasTextbox() && getShapeContainer(shape) == shape)
                msArrayList.add(topTextboxes, shape);

        for (Shape textbox : topTextboxes)
        {
            if(textbox.getRunPr().hasInsertRevision())
                collectEditRevisions(textbox, EditRevisionType.INSERTION, false);
            if(textbox.getRunPr().hasDeleteRevision())
                collectEditRevisions(textbox, EditRevisionType.DELETION, false);
        }
    }

    /// <summary>
    /// Collects format revision groups.
    /// </summary>
    private void collectFormatRevisions(DocumentBase doc, /*NodeType*/int nodeType)
    {
        Node node = deepestNode(doc, false);

        while (node != null)
        {
            WordAttrCollection pr = getPr(node, nodeType);

            node = (pr != null) && pr.hasFormatRevision()
                ? extractRevisionGroup(node, pr.getFormatRevision(), nodeType)
                : nextNode(node);
        }
    }

    /// <summary>
    /// Collects format revision for tables.
    /// </summary>
    private void collectTableRevisions(DocumentBase doc)
    {
        for (Table table : (Iterable<Table>) doc.getChildNodes(NodeType.TABLE, true))
        {
            ArrayList<RevisionBase> revList = getFormatRevisions(table);
            if (revList.size() > 0)
            {
                ArrayList<Node> nodeList = new ArrayList<Node>();
                msArrayList.add(nodeList, table);

                // Create group with first occurred revision.
                RevisionGroup revisionGroup = new RevisionGroup(nodeList, (FormatRevision)revList.get(0));
                msArrayList.add(mItems, revisionGroup);

                mapRevisions(revList, revisionGroup);
            }
        }
    }

    /// <summary>
    /// Returns first revision from table rows/cells or null if not found.
    /// </summary>
    private static ArrayList<RevisionBase> getFormatRevisions(Table table)
    {
        ArrayList<RevisionBase> revList = new ArrayList<RevisionBase>();

        for (Row row : (Iterable<Row>) table.getRows())
        {
            // WORDSNET-23305 Ignore empty format revisions.
            if ((row.getTablePr().getFormatRevision() != null) && (row.getTablePr().getFormatRevision().getRevPr().getCount() > 0))
                msArrayList.add(revList, row.getTablePr().getFormatRevision());

            for(Cell cell : (Iterable<Cell>) row.getCells())
                if (cell.getCellPr().getFormatRevision() != null)
                    msArrayList.add(revList, cell.getCellPr().getFormatRevision());
        }

        return revList;
    }

    /// <summary>
    /// Gets properties of specified type from given node.
    /// </summary>
    private static WordAttrCollection getPr(Node node, /*NodeType*/int desiredNodeType)
    {
        switch (desiredNodeType)
        {
            case NodeType.PARAGRAPH:
                return (node.getNodeType() == NodeType.PARAGRAPH) ? ((Paragraph)node).getParaPr() : null;

            case NodeType.SECTION:
                return (node.getNodeType() == NodeType.SECTION) ? ((Section)node).getSectPr() : null;

            case NodeType.RUN:
            {
                IInline inline = ms.as(node, IInline.class);

                if (inline != null)
                    return inline.getRunPr_IInline();

                if (node.getNodeType() == NodeType.PARAGRAPH)
                    return ((Paragraph)node).getParagraphBreakRunPr();

                return null;
            }

            default:
                // Unexpected desired node type.
                throw new IllegalStateException();
        }
    }

    /// <summary>
    /// Extracts one edit revision group started from given node. Returns node next to group.
    /// </summary>
    private Node extractRevisionGroup(Node node, Node root, EditRevision revision, boolean ignoreGroupShapeContent)
    {
        ArrayList<Node> nodeList = new ArrayList<Node>();
        boolean isStartOfRow = false;
        boolean takeEntireRow = false;

        CompositeNode lastStory = null;

        ArrayList<RevisionBase> revList = new ArrayList<RevisionBase>();

        while (node != null && node != root)
        {
            ITrackableNode trackable = ms.as(node, ITrackableNode.class);
            if (trackable != null)
            {
                EditRevision nodeRevision = getEditRevision(trackable, revision.getType());
                msArrayList.add(revList, nodeRevision);

                boolean isEqualRevision = (nodeRevision != null) && areEqual(nodeRevision, revision);
                if (!(isEqualRevision || takeEntireRow))
                    break;

                if (node.getNodeType() == NodeType.ROW)
                {
                    isStartOfRow = true; // next row is being started
                    takeEntireRow = false;
                }
                else if (isStartOfRow)
                {
                    isStartOfRow = false;
                    // If the first run of a row next to a deleted/inserted row is deleted/inserted,
                    // the entire row is included into the same revision group.
                    takeEntireRow = node instanceof IInline;
                }

                msArrayList.add(nodeList, node);
                lastStory = node.getAncestor(Story.class);
            }

            node = nextNode(node, ignoreGroupShapeContent);
            CompositeNode currStory = node.getAncestor(Story.class);
            // WORDSNET-28956 Break group when story type is changed.
            if ((currStory != null) && (lastStory != null) && (currStory.getNodeType() != lastStory.getNodeType()))
                break;
        }

        RevisionGroup revisionGroup = new RevisionGroup(nodeList, revision);
        msArrayList.add(mItems, revisionGroup);

        mapRevisions(revList, revisionGroup);

        return node;
    }


    /// <summary>
    /// Returns shape itself or topmost shape container in case shape is a part of group shape.
    /// </summary>
    private static ShapeBase getShapeContainer(ShapeBase shape)
    {
        while (true)
        {
            ShapeBase parentShape = ms.as(shape.getParentNode(), ShapeBase.class);

            if (parentShape == null)
                return shape;

            shape = parentShape;
        }
    }

    /// <summary>
    /// Extracts one format revision group started from given node. Returns node next to group.
    /// </summary>
    private Node extractRevisionGroup(Node node, FormatRevision revision, /*NodeType*/int desiredNodeType)
    {
        ArrayList<Node> nodeList = new ArrayList<Node>();
        ArrayList<RevisionBase> revList = new ArrayList<RevisionBase>();

        while (node != null)
        {
            WordAttrCollection pr = getPr(node, desiredNodeType);

            if (pr != null)
            {
                if (pr.hasFormatRevision() && areEqual(revision, pr.getFormatRevision()))
                {
                    msArrayList.add(nodeList, node);
                    msArrayList.add(revList, pr.getFormatRevision());
                }
                else
                    break;
            }

            Node prevNode = node;
            node = nextNode(node);

            // Looks like the Word splits revision group when a content related to different rows.
            if (isRowChanged(prevNode, node))
                break;
        }

        // Process list revision in special way.
        if ((desiredNodeType == NodeType.PARAGRAPH) && revision.getRevPr().contains(ParaAttr.LIST_ID))
            revision = new FormatRevision(calculateListRevision((Paragraph)nodeList.get(0)), revision.getAuthor(), revision.getDateTime());

        RevisionGroup revisionGroup = new RevisionGroup(nodeList, revision);
        msArrayList.add(mItems, revisionGroup);

        mapRevisions(revList, revisionGroup);

        return (node);
    }

    /// <summary>
    /// Returns true when node and next node related to different rows.
    /// </summary>
    private static boolean isRowChanged(Node node, Node nextNode)
    {
        if ((node == null) || (nextNode == null))
            return false;

        Node nodeParentRow = (node.getNodeType() == NodeType.ROW) ? node : node.getAncestor(NodeType.ROW);
        if (nodeParentRow == null) // I.e. there is not a row among parents and the node is not a row.
            return false;

        // Next node is a row i.e. specified nodes can not be related with as one row.
        if (nextNode.getNodeType() == NodeType.ROW)
            return false;

        Node nextNodeParentRow = nextNode.getAncestor(NodeType.ROW);
        // Parent row for the next node was not found or parent rows are different.
        if ((nextNodeParentRow == null) || msObject.referenceEquals(nodeParentRow, nextNodeParentRow))
            return false;

        return true;
    }

    /// <summary>
    /// Extracts one move revision group started from the specified start node of a move range.
    /// Returns a node next to the group.
    /// </summary>
    private Node extractMoveRevisionGroup(MoveRangeStart moveStartNode)
    {
        ArrayList<Node> nodeList = new ArrayList<Node>();
        boolean isMoveFrom = moveStartNode.getNodeType() == NodeType.MOVE_FROM_RANGE_START;
        /*NodeType*/int endNodeType = isMoveFrom ? NodeType.MOVE_FROM_RANGE_END : NodeType.MOVE_TO_RANGE_END;
        Node node = nextNode(moveStartNode);

        while (node != null && ((node.getNodeType() != endNodeType) || (((MoveRangeEnd)node).getId() != moveStartNode.getId())))
        {
            boolean hasRevision = false;
            if (node instanceof IMoveTrackableNode)
            {
                IMoveTrackableNode moveTrackableNode = ms.as(node, IMoveTrackableNode.class);
                hasRevision = (moveTrackableNode.getMoveFromRevision() != null) || (moveTrackableNode.getMoveToRevision() != null) || (node instanceof Paragraph);
            }

            if (!hasRevision && (node instanceof ITrackableNode))
            {
                ITrackableNode trackableNode = ms.as(node, ITrackableNode.class);
                hasRevision = (trackableNode.getDeleteRevision() != null) || (trackableNode.getInsertRevision() != null);
            }

            if (hasRevision)
                msArrayList.add(nodeList, node);

            node = nextNode(node);
        }

        // If the extraction is successful: range end node is found, create and add a revision group.
        if ((node != null) && (nodeList.size() > 0))
        {
            MoveRevision revision = new MoveRevision(isMoveFrom ? MoveRevisionType.MOVE_FROM : MoveRevisionType.MOVE_TO,
                moveStartNode.getAuthor(), moveStartNode.getDate());
            msArrayList.add(mItems, new RevisionGroup(nodeList, revision, moveStartNode.getName()));
            return nextNode(node);
        }
        else
        {
            return nextNode(moveStartNode);
        }
    }

    private static EditRevision getEditRevision(ITrackableNode trackable, /*EditRevisionType*/int type)
    {
        EditRevision revision = (type == EditRevisionType.DELETION)
                                    ? trackable.getDeleteRevision()
                                    : trackable.getInsertRevision();

        // Special case for cells. It seems that edit revisions might be inherited from parent row.
        if ((revision == null) && (trackable instanceof Cell))
            revision = getEditRevision(((Cell)trackable).getParentRow(), type);

        return revision;
    }

    /// <summary>
    /// Calculates revision for paragraph with list revision.
    /// </summary>
    /// <remarks>
    /// AM. When paragraph has list revision Word saves some formatting inherited from list definition.
    /// To get correct formatting changes we need to strip this inherited formatting first.
    /// </remarks>
    private static ParaPr calculateListRevision(Paragraph p)
    {
        final /*ParaPrExpandFlags*/int ORIGINAL_FLAGS = ParaPrExpandFlags.NORMAL;
        final /*ParaPrExpandFlags*/int FINAL_FLAGS = ParaPrExpandFlags.REVISED | ParaPrExpandFlags.REMOVE_CLEAR_TAB_STOPS;

        ParaPr original = p.getExpandedParaPr(ORIGINAL_FLAGS);
        ParaPr originalInherited = p.getExpandedParaPr(ORIGINAL_FLAGS | ParaPrExpandFlags.NO_DIRECT_FORMATTING);
        original.removeEquals(originalInherited);

        ParaPr finalRevision = p.getExpandedParaPr(FINAL_FLAGS);
        ParaPr finalInherited = p.getExpandedParaPr(FINAL_FLAGS | ParaPrExpandFlags.NO_DIRECT_FORMATTING);
        finalRevision.removeEquals(finalInherited);

        finalRevision.removeEquals(original);

        if (finalRevision.isExplicitlyNotListItem())
        {
            // When paragraph is set to explicitly not list we expand zero left indent and zero first line indent.
            // Remove this to get bare revision changes.
            if (finalRevision.getLeftIndent() == 0)
                finalRevision.remove(ParaAttr.LEFT_INDENT);

            if (finalRevision.getFirstLineIndent() == 0)
                finalRevision.remove(ParaAttr.FIRST_LINE_INDENT);
        }

        return finalRevision;
    }

    /// <summary>
    /// Compares two format revisions.
    /// </summary>
    private static boolean areEqual(FormatRevision formatRevision1, FormatRevision formatRevision2)
    {
        if(!StringUtil.equalsIgnoreCase(formatRevision1.getAuthor(), formatRevision2.getAuthor()))
            return false;

        WordAttrCollection revPr1 = themeCollapseHack(formatRevision1.getRevPr());
        WordAttrCollection revPr2 = themeCollapseHack(formatRevision2.getRevPr());

        if (!revPr1.equals(revPr2, G_IGNORE_KEYS))
            return false;

        // There should equality operator for effect attribute values.
        // Postpone for a while and compare presence of these attributes only.
        for (int effectKey : G_EFFECT_KEYS)
        {
            boolean contains1 = revPr1.contains(effectKey);
            boolean contains2 = revPr2.contains(effectKey);
            if (contains1 != contains2)
                return false;
        }

        return true;
    }

    /// <summary>
    /// Fixes theme color collapse issue
    /// </summary>
    /// <remarks>
    /// AM. We store simple color and theme color as separate attribute and this may cause the issue
    /// after collapse procedure during PositiveDifference calculation.
    ///
    /// This method removes default theme color value, which is actually empty string, to properly collect revision group.
    /// </remarks>
    private static WordAttrCollection themeCollapseHack(WordAttrCollection revPr)
    {
        if (revPr.containsKey(FontAttr.COLOR) && revPr.containsKey(FontAttr.THEME_COLOR) &&
            "".equals((String)revPr.get(FontAttr.THEME_COLOR)))
        {
            revPr = AttrCollectionExtension.deepClone(revPr);
            revPr.remove(FontAttr.THEME_COLOR);
        }

        return revPr;
    }

    /// <summary>
    /// Compares two edit revisions.
    /// </summary>
    private static boolean areEqual(EditRevision editRevision1, EditRevision editRevision2)
    {
        return
            (editRevision1.getType() == editRevision2.getType()) &&
            StringUtil.equalsIgnoreCase(editRevision1.getAuthor(), editRevision2.getAuthor());
    }

    private static Node nextNode(Node node)
    {
        return nextNode(node, false);
    }

    /// <summary>
    /// Returns next node in textual order.
    /// </summary>
    private static Node nextNode(Node node, boolean ignoreGroupShapeContent)
    {
        Node nextSibling = node.getNextSibling();

        // Look for the first not ignorable next sibling.
        while (nextSibling != null && isIgnorableNode(nextSibling))
            nextSibling = nextSibling.getNextSibling();

        // If no sibling go to parent node.
        if(nextSibling == null)
            return node.getParentNode();


        return nextSibling.isComposite()
            ? deepestNode(nextSibling, ignoreGroupShapeContent)
            : nextSibling;
    }

    /// <summary>
    /// Returns true if node should be ignored while revision group is collected.
    /// </summary>
    private static boolean isIgnorableNode(Node node)
    {
        // Skip annotations.
        switch (node.getNodeType())
    {
            case NodeType.BOOKMARK_START:
            case NodeType.BOOKMARK_END:
            case NodeType.COMMENT_RANGE_START:
            case NodeType.COMMENT_RANGE_END:

            // We cannot have revisions in comments so skip it entirely to do not break revision chain.
            case NodeType.COMMENT:
                return true;

            default:
                return false;
        }
    }

    /// <summary>
    /// Returns first deepest child node.
    /// </summary>
    private static Node deepestNode(Node node, boolean ignoreGroupShapeContent)
    {
        // AM. MS Word has strange logic for group shapes revision groups.
        // It seems it is collected separately but for top level group shapes only.
        if ((node.getNodeType() == NodeType.GROUP_SHAPE) && ignoreGroupShapeContent)
            return node;

        if (node.isComposite() && ((CompositeNode)node).hasChildNodes())
            return deepestNode(((CompositeNode)node).getFirstChild(), ignoreGroupShapeContent);

        return node;
    }

    private void mapRevisions(ArrayList<RevisionBase> revisions, RevisionGroup revisionGroup)
    {
        for (RevisionBase revision : revisions)
            if(revision != null)
                mRevisionBaseToGroupMap.put(revision, revisionGroup);
    }

    private static /*final*/ int[] G_EFFECT_KEYS =
    {
        FontAttr.EFFECT_GLOW         /* 810 */,
        FontAttr.EFFECT_SHADOW       /* 815 */,
        FontAttr.EFFECT_REFLECTION   /* 820 */,
        FontAttr.EFFECT_OUTLINE      /* 825 */,
        FontAttr.EFFECT_FILL         /* 830 */,
        FontAttr.EFFECT_SCENE_3_D      /* 835 */,
        FontAttr.EFFECT_PROPS_3_D      /* 840 */,
    };

    private static /*final*/ int[] G_IGNORE_KEYS =
    {
        FontAttr.RSID_R_PR    /* 30 */,
        FontAttr.RSID_R      /* 40 */,

        // WORDSNET-28093 Seems MS Word ignores RTl related attributes, at least for paragraph break mark.
        FontAttr.BOLD_BI,    /* 250 */
        FontAttr.ITALIC_BI,  /* 260 */
        FontAttr.NAME_BI,    /* 270 */
        FontAttr.SIZE_BI,    /* 350 */

        FontAttr.EFFECT_GLOW         /* 810 */,
        FontAttr.EFFECT_SHADOW       /* 815 */,
        FontAttr.EFFECT_REFLECTION   /* 820 */,
        FontAttr.EFFECT_OUTLINE      /* 825 */,
        FontAttr.EFFECT_FILL         /* 830 */,
        FontAttr.EFFECT_SCENE_3_D      /* 835 */,
        FontAttr.EFFECT_PROPS_3_D      /* 840 */,

        ParaAttr.RSID_P              /* 1580 */
    };

    private /*final*/ HashMap<RevisionBase, RevisionGroup> mRevisionBaseToGroupMap = new HashMap<RevisionBase, RevisionGroup>();

    private /*final*/ ArrayList<RevisionGroup> mItems = new ArrayList<RevisionGroup>();
}

