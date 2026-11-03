// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;


/// <summary>
/// Represents a base class of node in dml tree that can contain other nodes.
/// </summary>
abstract class DmlCompositeNode extends DmlFillableNode
{
    protected /*override*/ void initializeTransform()
    {
        mTransform = new DmlGroupTransform();
    }

    public /*override*/ boolean hasTransparency() throws Exception
    {
        if (super.hasTransparency())
            return true;

        for (ShapeBase childNode : (Iterable<ShapeBase>) getDml().getChildNodes(NodeType.ANY, false))
        {
            DmlFillableNode fillable = ms.as(childNode.getDmlNode(), DmlFillableNode.class);

            if (fillable != null && fillable.hasTransparency())
                return true;
        }

        return false;
    }

    /// <summary>
    /// Defines optional scene-level 3D properties to apply to an object.
    /// </summary>
    DmlScene3DProperties getScene3DProperties() { return mScene3DProperties; }
    void setScene3DProperties(DmlScene3DProperties value) { mScene3DProperties = value; }
    
    /// <summary>
    /// Returns original transform set in the document.
    /// Used for writers only. In rendering use <see cref="DmlNode.Transform"/> property.
    /// </summary>
    DmlTransform getTransformInternal() { return mTransform; }

    private DmlScene3DProperties mScene3DProperties;
}
