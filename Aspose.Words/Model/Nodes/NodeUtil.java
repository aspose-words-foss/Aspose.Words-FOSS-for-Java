// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Michael Morozoff

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.StringUtil;


/// <summary>
/// Common methods used on nodes.
/// </summary>
/*static*/ class NodeUtil
{
	/* Simulation of static class by using private constructor */
	private NodeUtil()
	{}

    /// <summary>
    /// Returns logical length of the node. This is not cumulative length of child nodes, but length of node
    /// itself. The returned length depends solely on the node type and not it's parent story.<para/>
    /// Value is 0 for Zlns, text length for runs and 1 for all other nodes.
    /// </summary>
    static int getLength(Node node)
    {
        switch (node.getNodeType())
        {
            case NodeType.RUN:
            {
                Debug.assertTrue(0 < node.getText().length());
                return node.getText().length();
            }
            default:
            {
                if (isZln(node))
                    return 0;
                return 1;
            }
        }
    }

    /// <summary>
    /// Returns true if one node is an ancestor of the other node or they are the same.
    /// </summary>
    /// <param name="node"></param>
    /// <param name="nodeToCheck"></param>
    /// <returns></returns>
    static boolean isAncestorOrSelf(Node node, Node nodeToCheck)
    {
        return ((node == nodeToCheck) || (node.isAncestorNode(nodeToCheck)));
    }

    /// <summary>
    /// Returns an ancestor of the node with the specified type or the node itself if the ancestor is null.
    /// </summary>
    /// <param name="node"></param>
    /// <param name="ancestorType"></param>
    /// <returns></returns>
    static Node getAncestorOrSelf(Node node, /*NodeType*/int ancestorType)
    {
        Node ancestor = node.getAncestor(ancestorType);
        return (ancestor != null ? ancestor : node);
    }

    /// <summary>
    /// Returns true if the specified node has an ancestor of the specified type.
    /// </summary>
    static boolean hasAncestor(Node node, /*NodeType*/int ancestorType)
    {
        return node != getAncestorOrSelf(node, ancestorType);
    }

    /// <summary>
    /// Returns nesting level of the node. Doesn't ascend inline stories.
    /// </summary>
    static int getNestingLevel(Node node)
    {
        int result = 0;
        while (null != node)
        {
            if (NodeType.TABLE == node.getNodeType())
                result++;

            node = node.getParentNode();
            if (null != node)
            {
                switch (node.getNodeType())
                {
                    case NodeType.COMMENT:
                    case NodeType.FOOTNOTE:
                    case NodeType.GROUP_SHAPE:
                    case NodeType.SHAPE:
                        node = null;
                        break;
                    default:
                        // Do nothing.
                        break;
                }
            }
        }
        return result;
    }

    /// <summary>
    /// Returns True if node has zero length.
    /// </summary>
    static boolean isZln(Node node)
    {
        Debug.assertTrue(null != node);
        switch (node.getNodeType())
        {
            case NodeType.BOOKMARK_START:
            case NodeType.BOOKMARK_END:
            case NodeType.COMMENT_RANGE_START:
            case NodeType.COMMENT_RANGE_END:
            case NodeType.MOVE_FROM_RANGE_START:
            case NodeType.MOVE_FROM_RANGE_END:
            case NodeType.MOVE_TO_RANGE_START:
            case NodeType.MOVE_TO_RANGE_END:
            case NodeType.EDITABLE_RANGE_START:
            case NodeType.EDITABLE_RANGE_END:
            case NodeType.SMART_TAG:
            case NodeType.STRUCTURED_DOCUMENT_TAG:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns a value indicating whether the specified node is a run with a non-whitespace text
    /// or a node of a non-zero length type.
    /// </summary>
    static boolean isNonWhitespace(Node node)
    {
        Debug.assertTrue(node != null);

        return (node.getNodeType() == NodeType.RUN)
            ? !StringUtil.containsOnlyWhitespaces(node.getText())
            : !isZln(node);
    }

    /// <summary>
    /// Returns a value indicating whether the specified node is a run with a whitespace text
    /// or a node of a zero length type.
    /// </summary>
    static boolean isWhitespace(Node node)
    {
        return !isNonWhitespace(node);
    }

    /// <summary>
    /// Indicates that given node is either BookmarkStart or BookmarkEnd node.
    /// </summary>
    static boolean isBookmarkNode(Node node)
    {
        return (node.getNodeType() == NodeType.BOOKMARK_START) || (node.getNodeType() == NodeType.BOOKMARK_END);
    }

    /// <summary>
    /// Returns a value indicating whether the specified node is a cross-structure annotation (i.e. bookmark
    /// or comment range start/end).
    /// </summary>
    static boolean isCrossStructureAnnotation(Node node)
    {
        return isCrossStructureAnnotation(node.getNodeType());
    }

    /// <summary>
    /// Returns a value indicating whether the specified node type is a cross-structure annotation (i.e. bookmark
    /// or comment range start/end).
    /// </summary>
    static boolean isCrossStructureAnnotation(/*NodeType*/int nodeType)
    {
        switch (nodeType)
        {
            case NodeType.BOOKMARK_START:
            case NodeType.BOOKMARK_END:
            case NodeType.COMMENT_RANGE_START:
            case NodeType.COMMENT_RANGE_END:
            case NodeType.MOVE_FROM_RANGE_START:
            case NodeType.MOVE_FROM_RANGE_END:
            case NodeType.MOVE_TO_RANGE_START:
            case NodeType.MOVE_TO_RANGE_END:
            case NodeType.EDITABLE_RANGE_START:
            case NodeType.EDITABLE_RANGE_END:
            case NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START:
            case NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_END:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns True if specified node type is story master type.
    /// </summary>
    static boolean isStoryNodeType(Node node)
    {
        switch (node.getNodeType())
        {
            case NodeType.DOCUMENT:         // RK This is MF's code. I think it should probably be NodeType.Body.
                                            // MF No it should be Document because we need to skip over bodies to continue movements in main text.
            case NodeType.GLOSSARY_DOCUMENT:
            case NodeType.HEADER_FOOTER:
            case NodeType.FOOTNOTE:
            case NodeType.COMMENT:
            case NodeType.SHAPE:
            case NodeType.GROUP_SHAPE:
                return true;
            case NodeType.SYSTEM:       // We lived without it for years. Maybe we do not need that, but it is correct to have it.
                return node instanceof FootnoteSeparator;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns true if element is block-level type.
    /// </summary>
    static boolean isBlockLevelNode(Node node)
    {
        switch (node.getNodeType())
        {
            case NodeType.PARAGRAPH:
            case NodeType.TABLE:
            case NodeType.BOOKMARK_START:
            case NodeType.BOOKMARK_END:
            case NodeType.COMMENT_RANGE_START:
            case NodeType.COMMENT_RANGE_END:
            case NodeType.MOVE_FROM_RANGE_START:
            case NodeType.MOVE_FROM_RANGE_END:
            case NodeType.MOVE_TO_RANGE_START:
            case NodeType.MOVE_TO_RANGE_END:
            case NodeType.EDITABLE_RANGE_START:
            case NodeType.EDITABLE_RANGE_END:
                return true;
            case NodeType.STRUCTURED_DOCUMENT_TAG:
                return (((StructuredDocumentTag)node).getLevel() == MarkupLevel.BLOCK);
            case NodeType.SYSTEM:
                return (node instanceof SdtMarkerStart) || (node instanceof SdtMarkerEnd);
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns true if element is row-level type. That means can be inserted inside a table on row-level.
    /// </summary>
    static boolean isRowLevelNode(Node node)
    {
        switch (node.getNodeType())
        {
            case NodeType.ROW:
            case NodeType.BOOKMARK_START:
            case NodeType.BOOKMARK_END:
            case NodeType.COMMENT_RANGE_START:
            case NodeType.COMMENT_RANGE_END:
            case NodeType.MOVE_FROM_RANGE_START:
            case NodeType.MOVE_FROM_RANGE_END:
            case NodeType.MOVE_TO_RANGE_START:
            case NodeType.MOVE_TO_RANGE_END:
            case NodeType.EDITABLE_RANGE_START:
            case NodeType.EDITABLE_RANGE_END:
                return true;
            case NodeType.STRUCTURED_DOCUMENT_TAG:
                return (((StructuredDocumentTag)node).getLevel() == MarkupLevel.ROW);
            default:
                return false;
        }
    }

    /// <summary>
    /// returns true if element is cell-level type. That means can be inserted inside a row at a cell-level.
    /// </summary>
    static boolean isCellLevelNode(Node node)
    {
        switch (node.getNodeType())
        {
            case NodeType.CELL:
            case NodeType.BOOKMARK_START:
            case NodeType.BOOKMARK_END:
            case NodeType.COMMENT_RANGE_START:
            case NodeType.COMMENT_RANGE_END:
            case NodeType.MOVE_FROM_RANGE_START:
            case NodeType.MOVE_FROM_RANGE_END:
            case NodeType.MOVE_TO_RANGE_START:
            case NodeType.MOVE_TO_RANGE_END:
            case NodeType.EDITABLE_RANGE_START:
            case NodeType.EDITABLE_RANGE_END:
                return true;
            case NodeType.STRUCTURED_DOCUMENT_TAG:
                return (((StructuredDocumentTag)node).getLevel() == MarkupLevel.CELL);
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns true for inline-level nodes that can occur inside a paragraph, smart tag, sdt.
    /// </summary>
    static boolean isInlineLevelNode(Node node)
    {
        switch (node.getNodeType())
        {
            case NodeType.BOOKMARK_START:
            case NodeType.BOOKMARK_END:

            case NodeType.EDITABLE_RANGE_START:
            case NodeType.EDITABLE_RANGE_END:

            case NodeType.MOVE_FROM_RANGE_START:
            case NodeType.MOVE_FROM_RANGE_END:
            case NodeType.MOVE_TO_RANGE_START:
            case NodeType.MOVE_TO_RANGE_END:

            case NodeType.FIELD_START:
            case NodeType.FIELD_SEPARATOR:
            case NodeType.FIELD_END:
            case NodeType.FORM_FIELD:

            case NodeType.COMMENT:
            case NodeType.COMMENT_RANGE_START:
            case NodeType.COMMENT_RANGE_END:
            case NodeType.FOOTNOTE:

            case NodeType.RUN:
            case NodeType.SPECIAL_CHAR:

            case NodeType.GROUP_SHAPE:
            case NodeType.SHAPE:

            case NodeType.SMART_TAG:
            case NodeType.OFFICE_MATH:
            case NodeType.SUB_DOCUMENT:
                return true;

            case NodeType.SYSTEM:
                return node instanceof SdtMarkerStart || node instanceof SdtMarkerEnd;

            case NodeType.STRUCTURED_DOCUMENT_TAG:
                return (((StructuredDocumentTag)node).getLevel() == MarkupLevel.INLINE);
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns a value indicating whether the specified node is a drawing object, i.e.
    /// <see cref="ShapeBase"/> descendant.
    /// </summary>
    static boolean isDrawingObject(Node node)
    {
        switch (node.getNodeType())
        {
            case NodeType.SHAPE:
            case NodeType.GROUP_SHAPE:
                return true;
            default:
                return false;
        }
    }

    static boolean canInsertIntoMarkupNode(IMarkupNode parentMarkupNode, Node newChild)
    {
        // AM. Think we should not allow to insert range start/end into another SDT.
        if ((newChild.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START) ||
            (newChild.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_END))
            return false;

        if (newChild instanceof IMarkupNode)
        {
            return parentMarkupNode.getLevel_IMarkupNode() == ((IMarkupNode)newChild).getLevel_IMarkupNode();
        }
        else
        {
            switch (parentMarkupNode.getLevel_IMarkupNode())
            {
                case MarkupLevel.BLOCK:
                    return isBlockLevelNode(newChild);
                case MarkupLevel.CELL:
                    return isCellLevelNode(newChild);
                case MarkupLevel.INLINE:
                    return isInlineLevelNode(newChild);
                case MarkupLevel.ROW:
                    return isRowLevelNode(newChild);
                default:
                    return false;
            }
        }
    }

    /// <summary>
    /// Returns false if node type is one of the following:
    /// - <see cref="NodeType.Any"/>  - means that collection must return Sdt nodes along with others
    /// - <see cref="NodeType.StructuredDocumentTag"/> - means collection must return only sdt nodes
    /// - <see cref="NodeType.SmartTag"/> - means collection must return only smartTag nodes
    /// </summary>
    static boolean isSkipMarkupNodesInFlatEnumeration(/*NodeType*/int nodeType)
    {
        return !((nodeType == NodeType.ANY) || isMarkupNode(nodeType));
    }

    /// <summary>
    /// Returns true if the given <see cref="NodeType"/> represents nodes used for Markup in the document tree.
    /// </summary>
    static boolean isMarkupNode(/*NodeType*/int nodeType)
    {
        return (nodeType == NodeType.STRUCTURED_DOCUMENT_TAG) || (nodeType == NodeType.SMART_TAG);
    }

    /// <summary>
    /// Returns true if the node is not null and is a markup node:
    /// <see cref="StructuredDocumentTag"/> or
    /// <see cref="SmartTag"/>.
    /// </summary>
    static boolean isMarkupNode(Node node)
    {
        return (node != null) && isMarkupNode(node.getNodeType());
    }

    /// <summary>
    /// Returns <c>true</c> if the node is not null and is a structured document tag node:
    /// <see cref="StructuredDocumentTag"/> or
    /// <see cref="StructuredDocumentTagRangeStart"/> or
    /// <see cref="StructuredDocumentTagRangeEnd"/>.
    /// </summary>
    static boolean isStructuredDocumentTagNode(Node node)
    {
        return
            (node != null) &&
            ((node.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG) ||
             (node.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START) ||
             (node.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_END));
    }

    /// <summary>
    /// Returns true if the node is not null and is an Office Math node.
    /// </summary>
    static boolean isOfficeMath(Node node)
    {
        return (node != null) && (node.getNodeType() == NodeType.OFFICE_MATH);
    }

    /// <summary>
    /// Gets the first non-markup node in depth-first traverse of the parent node, starting from the given node.
    /// </summary>
    /// <param name="startNode">
    /// The node to start the traverse with.
    /// If this is a non-markup node, it is returned.
    /// </param>
    /// <param name="compositeNodeOnly">
    /// Flag indicating whether it searches for a composite node.
    /// </param>
    /// <returns>
    /// If all traversed nodes are markups, null is returned.
    /// </returns>
    static Node getFirstNonMarkupNodeInDepthFirstTraverse(Node startNode, boolean compositeNodeOnly)
    {
        Node nonMarkup = null;
        for (Node current = startNode; (current != null) && (nonMarkup == null); current = current.getNextSibling())
        {
            if (isMarkupNode(current))
            {
                CompositeNode compositeCurrent = (CompositeNode)current;
                nonMarkup = compositeNodeOnly
                    ? compositeCurrent.getFirstNonMarkupCompositeDescendant()
                    : compositeCurrent.getFirstNonMarkupDescendant();
            }
            else
            {
                nonMarkup = (!compositeNodeOnly || current.isComposite()) ? current : null;
            }
        }

        return nonMarkup;
    }

    /// <summary>
    /// Gets the last non-markup node in a depth-first traverse of the parent node, which goes before or equals the given node.
    /// </summary>
    /// <param name="endNode">
    /// The node to end the traverse with.
    /// If this is a non-markup node, it is returned.
    /// </param>
    /// <param name="compositeNodeOnly">
    /// Flag indicating whether it searches for a composite node.
    /// </param>
    /// <returns>
    /// If all traversed nodes are markups, null is returned.
    /// </returns>
    static Node getLastNonMarkupNodeInDepthFirstTraverse(Node endNode, boolean compositeNodeOnly)
    {
        Node nonMarkup = null;
        for (Node current = endNode; (current != null) && (nonMarkup == null); current = current.getPreviousSibling())
        {
            if (isMarkupNode(current))
            {
                CompositeNode compositeCurrent = (CompositeNode)current;
                nonMarkup = compositeNodeOnly
                    ? compositeCurrent.getLastNonMarkupCompositeDescendant()
                    : compositeCurrent.getLastNonMarkupDescendant();
            }
            else
            {
                nonMarkup = (!compositeNodeOnly || current.isComposite()) ? current : null;
            }
        }

        return nonMarkup;
    }

    /// <summary>
    /// If node is table returns it, otherwise returns first grand parent which is table.
    /// This method doesn't ascend inline stories.
    /// </summary>
    static Table getTable(Node node)
    {
        Debug.assertTrue(null != node);
        return (Table)(node.getNodeType() == NodeType.TABLE ? node :
            (Table)node.getStoryAncestor(NodeType.TABLE));
    }

    /// <summary>
    /// Returns index of node in parent.
    /// </summary>
    static int getNodeIndex(Node node)
    {
        Debug.assertTrue(node != null);
        if (node.getParentNode() == null)
            return 0;

        int result = 0;
        for (Node n = node.getParentNode().getFirstChild(); n != node; n = n.getNextSibling())
            result++;
        return result;
    }

    /// <summary>
    /// Returns node depth level.
    /// </summary>
    /// <param name="node"></param>
    /// <returns></returns>
    static int getDepth(Node node)
    {
        int depth = 0;

        while (node != null)
        {
            node = node.getParentNode();
            depth++;
        }

        return depth;
    }

    /// <summary>
    /// Gets node N-th parent.
    /// </summary>
    /// <param name="node"></param>
    /// <param name="n"></param>
    /// <returns></returns>
    static Node getNthParent(Node node, int n)
    {
        while ((n > 0) && (node != null))
        {
            n--;
            node = node.getParentNode();
        }

        return node;
    }

    /// <summary>
    /// Returns a value indicating whether the specified nodes belong to the same story.
    /// </summary>
    static boolean areNodesInSameStory(Node node1, Node node2)
    {
        return (node1.getStoryAncestor(NodeType.ANY) == node2.getStoryAncestor(NodeType.ANY));
    }

    /// <summary>
    /// Returns rotation of text in node. Value is in degrees.
    /// </summary>
    static float getTextRotation(Node node)
    {
        // Text frames inside of a text box are ignored. So we first need to find parent text box
        // and only if it's not present check frame attributes.

        // Word doesn't let you have shapes nested inside a text box, but only inline images.
        // So we really need to get first shape above and check if it's a text box.
        // node<-paragraph<-shape
        Shape textBox = (Shape)node.getAncestor(NodeType.SHAPE);
        // FOSS
        if (textBox != null)
            return 0f;

        // Now check that there is no frame.
        // node<-frame paragraph
        Paragraph textFrame = (Paragraph)node.getAncestor(NodeType.PARAGRAPH);

        // Shape is inline thus it must have parent paragraph. However it may not have parent when rendered alone.
        if (textFrame == null)
            return 0f;

        /*TextOrientation*/int textOrientation = textFrame.getParaPr().getFrameTextOrientation();
        if (!textFrame.getParaPr().isFloating())
        {
            // Check if node is inside cell.
            Cell cell = (Cell)node.getAncestor(NodeType.CELL);
            if (cell == null)
                return 0f;
            textOrientation = cell.getCellPr().getOrientation();
        }

        switch (textOrientation)
        {
            case TextOrientation.DOWNWARD:
                return 90f;
            case TextOrientation.UPWARD:
                return 270f;
            default:
                return 0f;
        }
    }

    /// <summary>
    /// If cannot insert a shape into the parent of refNode, method inserts the shape into the first ancestor
    /// of refNode where the shape can be inserted.
    /// </summary>
    static void insertShapeAtCompatibleTreeLevel(Node shapeNode, Node refNode)
    {
        // Search for node where we can insert newNode.
        while (refNode.getParentNode() != null)
        {
            if (refNode.getParentNode().canInsert(shapeNode))
                break;

            refNode = refNode.getParentNode();
        }

        if (refNode.getParentNode() == null)
            throw new IllegalStateException("Parent cannot be null.");

        refNode.insertNext(shapeNode);
    }

    /// <summary>
    /// Searches through the specified node and its descendants with using the specified matcher.
    /// </summary>
    static Node findChildOrSelf(Node node, NodeMatcher matcher) throws Exception
    {
        if (matcher.isMatch(node))
            return node;

        if (node.isComposite())
        {
            for (Node childNode : (Iterable<Node>) ((CompositeNode)node).getChildNodes(NodeType.ANY, false))
            {
                Node foundNode = findChildOrSelf(childNode, matcher);
                if (foundNode != null)
                    return foundNode;
            }
        }

        return null;
    }

    /// <summary>
    /// Searches for a paragraph next to the specified node. Traverses up to the node tree within
    /// body or story if necessary.
    /// </summary>
    /// <param name="node">Node to start search from.</param>
    /// <returns>Found paragraph or <c>null</c> if it is not found.</returns>
    static Paragraph findNextParagraph(Node node)
    {
        return findNextParagraph(node, true);
    }

    /// <summary>
    /// Searches for a paragraph prior to the specified node. Traverses up to the node tree within
    /// body or story if necessary.
    /// </summary>
    /// <param name="node">Node to start search from.</param>
    /// <returns>Found paragraph or <c>null</c> if it is not found.</returns>
    static Paragraph findPreviousParagraph(Node node)
    {
        return findNextParagraph(node, false);
    }

    private static Paragraph findNextParagraph(Node node, boolean forward)
    {
        int getChildIndex = forward ? 0 : -1;

        Node paragraph = null;
        Node currentLevel = node;
        do
        {
            Node sibling = forward
                ? currentLevel.getNextNonMarkupCompositeLimited()
                : currentLevel.getPreviousNonMarkupCompositeLimited();

            while ((paragraph == null) && (sibling != null))
            {
                if (sibling.getNodeType() == NodeType.PARAGRAPH)
                    paragraph = sibling;
                else if (sibling.isComposite())
                    paragraph = ((CompositeNode)sibling).getChild(NodeType.PARAGRAPH, getChildIndex, true);

                sibling = forward
                    ? sibling.getNextNonMarkupCompositeLimited()
                    : sibling.getPreviousNonMarkupCompositeLimited();
            }

            currentLevel = currentLevel.getFirstNonMarkupParentNode();
        }
        while (tryFindNextParagraph(paragraph, currentLevel));

        return (Paragraph)paragraph;
    }

    private static boolean tryFindNextParagraph(Node paragraph, Node currentLevel)
    {
        if (paragraph != null)
            return false;

        if (currentLevel == null)
            return false;

        return isBlockLevelNode(currentLevel) || isCellLevelNode(currentLevel) || isRowLevelNode(currentLevel);
    }

    /// <summary>
    /// Returns <c>true</c> if the specified paragraph is inside a cell and it is the only paragraph of it.
    /// </summary>
    static boolean isSingleParagraphInCell(Paragraph paragraph)
    {
        Cell cell = ms.as(paragraph.getFirstNonMarkupParentNode(), Cell.class);

        return
            (cell != null) &&
            (cell.getFirstParagraph() == paragraph) &&
            (cell.getLastParagraph() == paragraph);
    }

    /// <summary>
    /// Resets values of the <see cref="IDisplaceableByCustomXml.DisplacedByCustomXml"/> property of annotations
    /// that reference the specified structured document tag/structured document tag range node.
    /// </summary>
    static void resetDisplacedAnnotationReferences(Node sdtNode, boolean areChildNodesPreserved)
    {
        Debug.assertTrue(isStructuredDocumentTagNode(sdtNode));
        CompositeNode sdt = ms.as(sdtNode, CompositeNode.class);

        // If there is another SDT after/before the specified sdtNode in DisplacedByCustomXml direction,
        // DisplacedByCustomXml should be preserved. For example, if a bookmark start has DisplacedByType.Next,
        // an after it contains another SDT as the first child, and the SDT is removed using RemoveSelfOnly,
        // the bookmark start will reference the child SDT, and its DisplacedByCustomXml should be preserved.

        // Reset annotations before the SDT, DisplacedByCustomXml of which is DisplacedByType.Next.
        // Skip if there is a child SDT or another SDT after the processing one.
        Node nextToStart = ((sdt != null) && areChildNodesPreserved) ? sdt.getFirstChild() : sdtNode.getNextSibling();
        if (!hasSdtNodeIgnoringAnnotations(nextToStart, true))
            resetAnnotationDisplacement(sdtNode.getPreviousSibling(), DisplacedByType.NEXT, false);

        // Reset annotations after the SDT, DisplacedByCustomXml of which is DisplacedByType.Prev.
        // Skip if there is a child SDT or another SDT before the processing one.
        Node prevFromEnd = ((sdt != null) && areChildNodesPreserved) ? sdt.getLastChild() : sdtNode.getPreviousSibling();
        if (!hasSdtNodeIgnoringAnnotations(prevFromEnd, false))
            resetAnnotationDisplacement(sdtNode.getNextSibling(), DisplacedByType.PREV, true);

        if (sdt != null)
        {
            // Reset first-child annotations, DisplacedByCustomXml of which is DisplacedByType.Prev.
            // Skip if there is a parent SDT or another SDT before the processing one.
            if (!hasSdtNodeIgnoringAnnotations(sdt.getPreviousSibling(), false) &&
                ((sdt.getPreviousNonAnnotationSibling() != null) ||
                 (sdt.getParentNode().getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG)))
            {
                resetAnnotationDisplacement(sdt.getFirstChild(), DisplacedByType.PREV, true);
            }

            // Reset last-child annotations, DisplacedByCustomXml of which is DisplacedByType.Next.
            // Skip if there is a parent SDT or another SDT after the processing one.
            if (!hasSdtNodeIgnoringAnnotations(sdt.getNextSibling(), true) &&
                ((sdt.getNextNonAnnotationSibling() != null) ||
                 (sdt.getParentNode().getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG)))
            {
                resetAnnotationDisplacement(sdt.getLastChild(), DisplacedByType.NEXT, false);
            }
        }
    }

    /// <summary>
    /// Returns <c>true</c> if there is a structured document tag/structured document tag range node in the specified
    /// direction starting with the specified node.
    /// </summary>
    private static boolean hasSdtNodeIgnoringAnnotations(Node node, boolean inForwardDirection)
    {
        while (node != null)
        {
            if (isStructuredDocumentTagNode(node))
                return true;

            if (!isCrossStructureAnnotation(node))
                break;

            node = inForwardDirection ? node.getNextSibling() : node.getPreviousSibling();
        }

        return false;
    }

    /// <summary>
    /// Resets value of <see cref="IDisplaceableByCustomXml.DisplacedByCustomXml"/> property of annotations
    /// starting with the node in the specified direction if the property has the specified value.
    /// </summary>
    private static void resetAnnotationDisplacement(Node node, /*DisplacedByType*/int displacedByType, boolean inForwardDirection)
    {
        while ((node != null) && isCrossStructureAnnotation(node))
        {
            IDisplaceableByCustomXml displaceableByCustomXml = ms.as(node, IDisplaceableByCustomXml.class);
            if ((displaceableByCustomXml != null) && (displaceableByCustomXml.getDisplacedByCustomXml() == displacedByType))
                displaceableByCustomXml.setDisplacedByCustomXml(DisplacedByType.UNSPECIFIED);

            node = inForwardDirection ? node.getNextSibling() : node.getPreviousSibling();
        }
    }
}

