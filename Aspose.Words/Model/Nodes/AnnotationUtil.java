// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/09/2018 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msString;
import com.aspose.Debug;


/*static*/ class AnnotationUtil
{
	/* Simulation of static class by using private constructor */
	private AnnotationUtil()
	{}

    /// <summary>
    /// Gets real range bound for the specified annotation node. Result depends on value of the DisplacedBy property.
    /// </summary>
    /// <remarks>
    /// It is mainly used for updating referencing fields.
    /// </remarks>
    static RangeBound getAnnotationRangeBound(Node annotation, boolean forRangeStart)
    {
        IDisplaceableByCustomXml displaceableAnnotation = ms.as(annotation, IDisplaceableByCustomXml.class);

        if (displaceableAnnotation == null)
            return new RangeBound(annotation, false);

        switch (displaceableAnnotation.getDisplacedByCustomXml())
        {
            case DisplacedByType.NEXT:
                return getBoundByNextSdt(annotation, forRangeStart);
            case DisplacedByType.PREV:
                return getBoundByPreviousSdt(annotation, forRangeStart);
            default:
                return new RangeBound(annotation, false);
        }
    }

    /// <summary>
    /// Gets range bound of a annotation node that is displaced by next SDT.
    /// </summary>
    private static RangeBound getBoundByNextSdt(Node annotation, boolean forRangeStart)
    {
        // The code reproduces behaviour of MS Word to some extent.
        // if displacedByCustomXml = "next", looks like it searches for real position of a annotation node in this way:
        // 1. If a annotation node is located before an SDT, this is a searching displaced SDT.
        // 2. If a annotation node is a descending of SDT (not only a direct child), this is a searching displaced SDT.
        // (If the both #1 and #2 are present, looks like it is an error and the attribute is just removed/ignored.)
        // 3. If node is outside of SDT, it searches for the next SDT as far as necessary. If there is no SDT,
        // the annotation is treated as invalid and deleted.
        // If a displaced SDT has been found, annotation real position is at inline level inside it.

        boolean isStartOfSdt = true; // i.e. before SDT
        StructuredDocumentTag sdt = ms.as(annotation.getNextNonAnnotationSibling(), StructuredDocumentTag.class);

        if (sdt == null)
        {
            Node sdtRange = getSdtRangeNode(annotation.getNextSibling(), true);
            if (sdtRange != null)
            {
                return getBound(sdtRange, forRangeStart, sdtRange.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START,
                    annotation, sdtRange.getNextSibling());
            }
        }

        if (sdt == null)
        {
            sdt = (StructuredDocumentTag)annotation.getAncestor(NodeType.STRUCTURED_DOCUMENT_TAG);
            isStartOfSdt = sdt == null;
        }

        if (sdt == null)
        {
            DocumentPosition dp = new DocumentPosition(annotation);
            while (dp.move(annotation.getDocument(), true, true, false, false, false))
            {
                if (dp.getNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
                {
                    sdt = (StructuredDocumentTag)dp.getNode();
                    break;
                }
            }
        }

        if (sdt != null)
            return getBoundBySdt(sdt, forRangeStart, isStartOfSdt, annotation);
        else
            return null;
    }

    /// <summary>
    /// Gets range bound of a annotation node that is displaced by previous SDT.
    /// </summary>
    private static RangeBound getBoundByPreviousSdt(Node annotation, boolean forRangeStart)
    {
        // The code reproduces behaviour of MS Word to some extent.
        // if displacedByCustomXml = "prev", looks like it searches for real position of a annotation node in this way:
        // 1. If a annotation node is located after an SDT, this is a searching displaced SDT.
        // 2. If a annotation node is a direct child of an SDT, this is a searching displaced SDT.
        // (If the both #1 and #2 are present, looks like it is an error and the attribute is just removed/ignored.)
        // 3. If a annotation node is not a direct child of an SDT, Word goes back in node hierarchy till the first SDT
        // or text node (or paragraph/cell/row end) is reached. If it is an SDT, this is a displaced SDT. At the other
        // case the real position is through one char of the reached node (paragraph break is treated as one char too):
        // run is split for it, or the annotation position is last child of paragraph/cell/row; displacedByCustomXml
        // attribute (of this node only) is reset.
        // If a displaced SDT has been found, annotation real position is at inline level inside it.

        boolean isStartOfSdt = false; // i.e. first child of SDT
        StructuredDocumentTag sdt = ms.as(annotation.getPreviousNonAnnotationSibling(), StructuredDocumentTag.class);

        if (sdt == null)
        {
            Node sdtRange = getSdtRangeNode(annotation.getPreviousSibling(), false);
            if (sdtRange != null)
            {
                return getBound(sdtRange, forRangeStart, sdtRange.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START,
                    annotation, sdtRange.getPreviousSibling());
            }
        }

        if ((sdt == null) &&
            (annotation.getParentNode() != null) &&
            (annotation.getParentNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG))
        {
            sdt = (StructuredDocumentTag)annotation.getParentNode();
            isStartOfSdt = true;
        }

        if (sdt == null)
        {
            DocumentPosition dp = new DocumentPosition(annotation);
            while (dp.move(annotation.getDocument(), false, true, false, false, false))
            {
                Node node = dp.getNode();
                if (node.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
                {
                    sdt = (StructuredDocumentTag)node;
                    break;
                }

                CompositeNode composite = ms.as(node, CompositeNode.class);
                if ((composite != null) && (!msString.equals(composite.getEndText(), "")))
                {
                    if (composite.getLastChild() != null)
                        return new RangeBound(composite.getLastChild(), !forRangeStart);
                    else
                        return new RangeBound(composite, forRangeStart);
                }
                else if (node instanceof Inline)
                {
                    boolean isBeforeNode = node.getText().length() == 1;
                    return new RangeBound(node, forRangeStart ? isBeforeNode : !isBeforeNode);
                }
            }
        }

        if (sdt != null)
            return getBoundBySdt(sdt, forRangeStart, isStartOfSdt, annotation);
        else
            return null;
    }

    /// <summary>
    /// Searches for a <see cref="StructuredDocumentTagRangeStart"/> or <see cref="StructuredDocumentTagRangeEnd"/>
    /// node near the specified node in the specified direction. The search is performed up to the first
    /// non-annotation node.
    /// </summary>
    private static Node getSdtRangeNode(Node node, boolean inForwardDirection)
    {
        Node sdtRange = null;

        while ((node != null) && NodeUtil.isCrossStructureAnnotation(node))
        {
            if ((node.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START) ||
                (node.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_END))
            {
                sdtRange = node;
            }

            node = inForwardDirection ? node.getNextSibling() : node.getPreviousSibling();
        }

        return sdtRange;
    }

    /// <summary>
    /// Gets range bound by the displacing SDT.
    /// </summary>
    private static RangeBound getBoundBySdt(StructuredDocumentTag sdt, boolean forRangeStart, boolean forSdtStart,
        Node annotation)
    {
        Node node = getDeepestChild(sdt, forSdtStart);
        return getBound(sdt, forRangeStart, forSdtStart, annotation, node);
    }

    private static RangeBound getBound(Node sdtNode, boolean forRangeStart, boolean forSdtStart, Node annotation, Node node)
    {
        if ((node == null) ||
            (NodeUtil.isCrossStructureAnnotation(node) &&
             (node.getParentNode() == annotation.getParentNode()) &&
             (forRangeStart
                 ? (node.getNextNonAnnotationSibling() == annotation.getNextNonAnnotationSibling())
                 : (node.getPreviousNonAnnotationSibling() == annotation.getPreviousNonAnnotationSibling()))))
            node = annotation;

        return new RangeBound(
            node,
            (node != annotation) &&
                // Displaced annotation is intended at inline level. Composite is treated as a paragraph break char.
                // Depending on it, include or not composite node.
                ((forRangeStart)
                    ? (forSdtStart || node.isComposite())
                    : (!forSdtStart && (!node.isComposite() || ((CompositeNode)node).hasChildNodes()))),
            sdtNode,
            annotation);
    }

    /// <summary>
    /// Gets the deepest child node (non-annotation) of the specified node without going into inline stories.
    /// </summary>
    static Node getDeepestChild(CompositeNode node, boolean isGettingFirst)
    {
        while (true)
        {
            Node childNonAnnotation = isGettingFirst ? node.getFirstNonAnnotationChild() : node.getLastNonAnnotationChild();
            if (childNonAnnotation == null)
                return node.hasChildNodes() ? (isGettingFirst ? node.getFirstChild() : node.getLastChild()) : node;

            if (childNonAnnotation.isComposite() && (childNonAnnotation.getNodeLevel() != NodeLevel.INLINE))
                node = (CompositeNode)childNonAnnotation;
            else
                return isGettingFirst ? node.getFirstChild() : node.getLastChild();
        }
    }
}

/// <summary>
/// Represents necessary information for bound of a range: node and a flag indicating whether to include
/// the node into the range.
/// </summary>
/// <remarks>
/// For ranges specified by a cross-structure annotation node with defined DisplacedBy property, the
/// <see cref="Node"/> property contains node that is effective start/end of a range. This is a child of
/// a displacing SDT (if a displacing SDT is found).
/// </remarks>
class RangeBound
{
    /// <summary>
    /// Ctor if range bound is not by displacing annotation node.
    /// </summary>
    RangeBound(Node node, boolean includeNode)
    {
    	this(node, includeNode, null, null);
	
    }

    /// <summary>
    /// Ctor if range bound is by displacing annotation node.
    /// </summary>
    RangeBound(Node node, boolean includeNode, Node displacingNode, Node annotation)
    {
        mNode = node;
        mPreviousSibling = node.getPreviousSibling();
        mNextSibling = node.getNextSibling();
        mParent = node.getParentNode();
        mIsNodeIncluded = includeNode;
        Debug.assertTrue((displacingNode == null) || NodeUtil.isStructuredDocumentTagNode(displacingNode));
        mDisplacingNode = displacingNode;
        mDisplacedAnnotation = annotation;
        // Store mIsAnnotationAChildOfSdt to be able to remove the annotation during processing.
        mIsAnnotationAChildOfSdt = (annotation != null) && annotation.isAncestorNode(displacingNode);
    }

    Node getNode() { return mNode; };

    private Node mNode;

    boolean isNodeIncluded() { return mIsNodeIncluded; };

    private boolean mIsNodeIncluded;

    /// <summary>
    /// Range bound is formed by this displacing structured document tag or structured document tag range start/end.
    /// </summary>
    Node getDisplacingNode() { return mDisplacingNode; };

    private Node mDisplacingNode;

    /// <summary>
    /// If this property contains a value, this range bound is specified by a cross-structure annotation node with
    /// defined DisplacedBy property. Effective start/end of a range is stored in the <see cref="Node"/> property.
    /// </summary>
    Node getDisplacedAnnotation() { return mDisplacedAnnotation; };

    private Node mDisplacedAnnotation;

    /*DisplacedByType*/int getDisplacedByType()
    {
        return (getDisplacedAnnotation() != null)
            ? ((IDisplaceableByCustomXml)getDisplacedAnnotation()).getDisplacedByCustomXml()
            : DisplacedByType.UNSPECIFIED;
    }

    boolean isAnnotationAChildOfSdt() { return mIsAnnotationAChildOfSdt; };

    private boolean mIsAnnotationAChildOfSdt;

    // The following properties can help to get position after removing Node.

    Node getPreviousSibling() { return mPreviousSibling; };

    private Node mPreviousSibling;

    Node getNextSibling() { return mNextSibling; };

    private Node mNextSibling;

    CompositeNode getParent() { return mParent; };

    private CompositeNode mParent;
}

