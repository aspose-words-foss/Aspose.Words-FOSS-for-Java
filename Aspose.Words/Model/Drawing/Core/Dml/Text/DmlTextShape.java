// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.RectangleF;


/// <summary>
/// 20.1.2.2.41 txSp (Text Shape)
/// This element specifies the existence of a text shape within a parent shape. 
/// This text shape is specifically used for displaying text as it has only text related child elements.
/// </summary>
class DmlTextShape implements Cloneable
{
    DmlTextShape deepClone()
    {
        DmlTextShape lhs = (DmlTextShape)memberwiseClone();

        if (mTransform != null)
            lhs.mTransform = mTransform.deepClone();

        if (mTextBody != null)
            lhs.mTextBody = mTextBody.deepClone();

        return lhs;
    }

    DmlTransform getTransform()
    {
        if (mTransform == null)
            mTransform = new DmlTransform();
        return mTransform;
    }
    void setTransform(DmlTransform value) { mTransform = value; }

    /// <summary>
    /// 20.1.2.2.42 useSpRect (Use Shape Text Rectangle)
    /// This element specifies that the text rectangle from the parent shape should be used for 
    /// this text shape. If this attribute is specified then the text rectangle, or text bounding 
    /// box as it is also called should have the same dimensions as the text bounding box of the 
    /// parent shape within which this text shape resides.
    /// </summary>
    boolean getUseShapeTextRectangle() { return mUseShapeTextRectangle; }
    void setUseShapeTextRectangle(boolean value) { mUseShapeTextRectangle = value; }

    DmlTextBody getTextBody()
    {
        if (mTextBody == null)
            mTextBody = new DmlTextBody();
        return mTextBody;
    }
    void setTextBody(DmlTextBody value) { mTextBody = value; }

    /// <summary>
    /// Returns the text shape bounding size without borders and effects.
    /// </summary>
    RectangleF getBounds() { return mBounds; }
    void setBounds(RectangleF value) { mBounds = value; }

    private RectangleF mBounds = RectangleF.Empty;
    private DmlTextBody mTextBody;
    private DmlTransform mTransform;
    private boolean mUseShapeTextRectangle;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

