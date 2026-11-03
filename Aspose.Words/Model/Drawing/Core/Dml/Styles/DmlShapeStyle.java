// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.2.2.37 style (Shape Style)
/// This element specifies the style information for a shape.
/// </summary>
class DmlShapeStyle implements Cloneable
{
    /// <summary>
    /// Creates the effects style.
    /// </summary>
    EffectStyle getEffectStyle(IThemeProvider theme)
    {
        return getEffectReference().getEffectStyle(theme);
    }

    DmlShapeStyle deepClone()
    {
        if (isEmpty())
            return new DmlShapeStyle();

        DmlShapeStyle lhs = (DmlShapeStyle)memberwiseClone();
        if (mEffectReference != null)
            lhs.setEffectReference((DmlEffectReference)mEffectReference.deepClone());
        if (mFillReference != null)
            lhs.setFillReference((DmlFillReference)mFillReference.deepClone());
        if (mFontReference != null)
            lhs.mFontReference = (DmlFontReference)mFontReference.deepClone();
        if (mLineReference != null)
            lhs.setLineReference((DmlLineReference)mLineReference.deepClone());
        return lhs;
    }

    DmlEffectReference getEffectReference()
    {
        if (mEffectReference == null)
            mEffectReference = new DmlEffectReference();
        return mEffectReference;
    }
    void setEffectReference(DmlEffectReference value) { mEffectReference = value; }

    DmlFillReference getFillReference()
    {
        if (mFillReference == null)
            mFillReference = new DmlFillReference();
        return mFillReference;
    }
    void setFillReference(DmlFillReference value) { mFillReference = value; }

    DmlFontReference getFontReference()
    {
        if (mFontReference == null)
            mFontReference = new DmlFontReference();
        return mFontReference;
    }
    void setFontReference(DmlFontReference value) { mFontReference = value; }

    DmlLineReference getLineReference()
    {
        if (mLineReference == null)
            mLineReference = new DmlLineReference();
        return mLineReference;
    }
    void setLineReference(DmlLineReference value) { mLineReference = value; }

    boolean isEmpty() { return (((mEffectReference == null) || mEffectReference.isEmpty()) &&
            ((mFillReference == null) || mFillReference.isEmpty()) &&
            ((mFontReference == null) || mFontReference.isEmpty()) &&
            ((mLineReference == null) || mLineReference.isEmpty()));
    }

    private DmlEffectReference mEffectReference;
    private DmlFillReference mFillReference;
    private DmlFontReference mFontReference;
    private DmlLineReference mLineReference;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

