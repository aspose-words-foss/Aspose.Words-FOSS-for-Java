// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



class DmlLockedCanvas extends DmlCompositeNode implements IDmlCommonShapePrSource, Cloneable
{
    DmlLockedCanvas(/*DmlNodeType*/int canvasType)
    {
        if (canvasType != DmlNodeType.LOCKED_CANVAS && canvasType != DmlNodeType.WORDPROCESSING_CANVAS)
            throw new IllegalArgumentException("Unexpected canvas type");

        mCanvasType = canvasType;
    }

    /*override*/ DmlNode deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        return (DmlNode)memberwiseClone();
    }

    /*override*/ /*DmlNodeType*/int getDmlNodeType() { return mCanvasType; }

    /// <summary>
    /// Outline specified via 'whole' formatting of word-processing canvas.
    /// </summary>
    public DmlOutline getOutline() { return mOutline; }
    public void setOutline(DmlOutline value) { mOutline = value; }

    public DmlShapeStyle /*IDmlCommonShapePrSource.*/getStyle() { return mStyle; }
    public void /*IDmlCommonShapePrSource.*/setStyle(DmlShapeStyle value) { mStyle = value; }

    private /*final*/ /*DmlNodeType*/int mCanvasType;
    private DmlOutline mOutline;
    private DmlShapeStyle mStyle;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

