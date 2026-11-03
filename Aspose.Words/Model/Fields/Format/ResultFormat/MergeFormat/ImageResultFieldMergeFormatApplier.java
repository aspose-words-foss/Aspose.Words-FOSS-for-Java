// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/03/2015 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Applies MERGEFORMAT to a field with image result.
/// </summary>
class ImageResultFieldMergeFormatApplier implements IFieldResultFormatApplier
{
    ImageResultFieldMergeFormatApplier(Iterable<Node> oldResultNodes)
    {
        mOldResultNodes = oldResultNodes;
    }

    public void /*IFieldResultFormatApplier.*/applyFormat(NodeRange result)
    {
        Shape oldResultShape = getShapeNode(mOldResultNodes);
        Shape newResultShape = getShapeNode(result);

        if (oldResultShape != null && newResultShape != null)
        {
            newResultShape.setWidthSafe(oldResultShape.getWidth());
            newResultShape.setHeightSafe(oldResultShape.getHeight());
        }
    }

    private static Shape getShapeNode(Iterable<Node> range)
    {
        for (Node node : range)
        {
            if (node.getNodeType() == NodeType.SHAPE)
            {
                return (Shape)node;
            }
        }

        return null;
    }

    private /*final*/ Iterable<Node> mOldResultNodes;
}

