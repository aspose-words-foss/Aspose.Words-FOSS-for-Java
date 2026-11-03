// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.2.2.20 grpSp (Group shape)
/// This element specifies a group shape that represents many shapes grouped together.
/// This shape is to be treated just as if it were a regular shape but instead of
/// being described by a single geometry it is made up of all the shape geometries
/// encompassed within it. Within a group shape each of the shapes that make up the
/// group are specified just as they normally would. The idea behind grouping elements
/// however is that a single transform can apply to many shapes at the same time.
/// </summary>
class DmlGroupShape extends DmlCompositeNode implements IDmlCommonShapePrSource
{
    DmlGroupShape(/*DmlNodeType*/int groupShapeType)
    {
        if (groupShapeType != DmlNodeType.GROUP_SHAPE
            && groupShapeType != DmlNodeType.WORDPROCESSING_GROUP_SHAPE
            && groupShapeType != DmlNodeType.SP_TREE
            && groupShapeType != DmlNodeType.GRAPHIC_FRAME)
            throw new IllegalArgumentException("Unexpected group shape type.");

        mGroupShapeType = groupShapeType;
    }

    /*override*/ DmlNode deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        DmlGroupShape lhs = (DmlGroupShape)super.deepClone(isCloneChildren, cloningListener);

        if (mTextShape != null)
            lhs.mTextShape = mTextShape.deepClone();

        return lhs;
    }

    /*override*/ /*DmlNodeType*/int getDmlNodeType() { return mGroupShapeType; }

    DmlTextShape getTextShape() { return mTextShape; }
    void setTextShape(DmlTextShape value) { mTextShape = value; }

    /*BWMode*/int getBWMode() { return mDmlBWMode; }
    void setBWMode(/*BWMode*/int value) { mDmlBWMode = value; }

    boolean hasParentLockedCanvas(DmlGroupShape node)
    {
        if ((node.getParent() == null) || (node.getParent().getNodeType() != NodeType.GROUP_SHAPE))
            return false;

        DmlNode parentDmlNode = ((GroupShape)node.getParent()).getDmlNode();
        if (parentDmlNode == null)
            return false;

        if (parentDmlNode.getDmlNodeType() == DmlNodeType.LOCKED_CANVAS)
            return true;

        if (parentDmlNode.getDmlNodeType() == DmlNodeType.GROUP_SHAPE)
            return hasParentLockedCanvas((DmlGroupShape)parentDmlNode);

        return false;
    }

    public DmlOutline getOutline() { return mOutline; }
    public void setOutline(DmlOutline value) { mOutline = value; }

    public DmlShapeStyle /*IDmlCommonShapePrSource.*/getStyle() { return mStyle; }
    public void /*IDmlCommonShapePrSource.*/setStyle(DmlShapeStyle value) { mStyle = value; }

    boolean isParentLockedCanvas() { return hasParentLockedCanvas(this); }

    private DmlOutline mOutline;
    private DmlShapeStyle mStyle;
    private DmlTextShape mTextShape;
    private /*final*/ /*DmlNodeType*/int mGroupShapeType;
    private /*BWMode*/int mDmlBWMode = (/*BWMode*/int)255; // Set special value to avoid BWMode writing.
}

