// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/03/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Modifies nodes being copied in the way to produce MS-Word-like results for REF fields.
/// </summary>
class FieldRefNodeModifier implements INodeModifier
{
    FieldRefNodeModifier(boolean includeNoteOrComment)
    {
        mIncludeNoteOrComment = includeNoteOrComment;
    }

    public Node /*INodeModifier.*/modify(Node referenceNode, Node nodeToModify, boolean modifyChildren, INodeCloningListener cloningListener)
    {
        return modify(nodeToModify, modifyChildren);
    }

    /// <summary>
    /// Performs actual modifying.
    /// </summary>
    /// <param name="node"></param>
    /// <param name="modifyChildren"></param>
    /// <returns></returns>
    private Node modify(Node node, boolean modifyChildren)
    {
        if (needRemoveNode(node))
            return null;

        if (modifyChildren && node.isComposite())
        {
            CompositeNode parentNode = (CompositeNode)node;
            Node childNode = parentNode.getFirstChild();
            while (childNode != null)
            {
                // Remember the child node.
                Node childNodeToModify = childNode;
                // The child node can be removed below so advance to the next one here.
                childNode = childNode.getNextSibling();

                // If the child node should not appear in a REF field result remove it from a parent node.
                if (modify(childNodeToModify, true) == null)
                    childNodeToModify.remove();
            }
        }

        return node;
    }

    /// <summary>
    /// Returns a value indicating whether the specified node should not appear in a REF field result.
    /// </summary>
    /// <param name="node"></param>
    /// <returns></returns>
    private boolean needRemoveNode(Node node)
    {
        // WORDSNET-11235 Skip comment ranges, comments and footnote nodes.
        if (needRemoveNodeType(node.getNodeType()))
            return true;

        // Nodes other than drawing objects should not be removed.
        if (!NodeUtil.isDrawingObject(node))
            return false;

        // Get the topmost drawing object for the given one.
        Node topmostDrawingObject = node;
        while ((topmostDrawingObject.getParentNode() != null) && NodeUtil.isDrawingObject(topmostDrawingObject.getParentNode()))
            topmostDrawingObject = topmostDrawingObject.getParentNode();

        // Determine whether the drawing object is inlined with text.
        // Note, that it makes sense only for the topmost drawing object.
        boolean isInline = ((ShapeBase)topmostDrawingObject).isInline();

        // If the drawing object is not inlined with text it should be removed.
        return !isInline;
    }

    private boolean needRemoveNodeType(/*NodeType*/int nodeType)
    {
        switch (nodeType)
        {
            case NodeType.COMMENT_RANGE_START:
            case NodeType.COMMENT_RANGE_END:
            case NodeType.COMMENT:
            case NodeType.FOOTNOTE:
                return !mIncludeNoteOrComment;
            default:
                return NodeUtil.isCrossStructureAnnotation(nodeType);
        }
    }

    private /*final*/ boolean mIncludeNoteOrComment;
}

