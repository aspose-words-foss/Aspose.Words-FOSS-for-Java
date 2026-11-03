// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/05/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents 2.8.3.7 CT_StyleEntry [MS-ODRAWXML]
/// This complex type specifies the default formatting for a single type of element on a chart. This element allows
/// for properties to be explicitly specified or hold references to the document's theme.
/// </summary>
class DmlChartStyleEntry extends DmlExtensionListSource implements Cloneable
{
    /// <summary>
    /// Clones this <see cref="DmlChartStyleEntry"/> object.
    /// </summary>
    DmlChartStyleEntry deepClone()
    {
        DmlChartStyleEntry lhs = (DmlChartStyleEntry)memberwiseClone();

        lhs.mLineReference = cloneReference(mLineReference);
        lhs.mFillReference = cloneReference(mFillReference);
        lhs.mEffectReference = cloneReference(mEffectReference);

        if (mFontReference != null)
            lhs.mFontReference = (DmlChartFontReference)mFontReference.deepClone();
        if (mShapePr != null)
            lhs.mShapePr = mShapePr.deepClone();
        if (mDefaultRunPr != null)
            lhs.mDefaultRunPr = mDefaultRunPr.deepClone();
        if (mTextBodyPr != null)
            lhs.mTextBodyPr = mTextBodyPr.deepClone();

        if (mModifiers != null)
        {
            lhs.mModifiers = new String[mModifiers.length];
            for (int i = 0; i < mModifiers.length; i++)
                lhs.mModifiers[i] = mModifiers[i];
        }

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    /// <summary>
    /// Clones the specified style reference object if it is not null.
    /// </summary>
    private static DmlChartStyleReference cloneReference(DmlChartStyleReference reference)
    {
        return (reference != null) ? (DmlChartStyleReference)reference.deepClone() : null;
    }

    /// <summary>
    /// Represents lnRef: a CT_StyleReference element that specifies a reference to a line style within the style 
    /// matrix.
    /// </summary>
    DmlChartStyleReference getLineReference()
    {
        if (mLineReference == null)
            mLineReference = new DmlChartStyleReference();
        return mLineReference;
    }
    void setLineReference(DmlChartStyleReference value) { mLineReference = value; }

    /// <summary>
    /// Represents lineWidthScale: a double element that specifies a multiplier to apply to the line width.
    /// </summary>
    double getLineWidthScale() { return mLineWidthScale; }
    void setLineWidthScale(double value) { mLineWidthScale = value; }

    /// <summary>
    /// Represents fillRef: a CT_StyleReference element that specifies a reference to a fill style within 
    /// the style matrix.
    /// </summary>
    DmlChartStyleReference getFillReference()
    {
        if (mFillReference == null)
            mFillReference = new DmlChartStyleReference();
        return mFillReference;
    }
    void setFillReference(DmlChartStyleReference value) { mFillReference = value; }

    /// <summary>
    /// Represents effectRef: a CT_StyleReference element that specifies a reference to an effect style within 
    /// the style matrix.
    /// </summary>
    DmlChartStyleReference getEffectReference()
    {
        if (mEffectReference == null)
            mEffectReference = new DmlChartStyleReference();
        return mEffectReference;
    }
    void setEffectReference(DmlChartStyleReference value) { mEffectReference = value; }

    /// <summary>
    /// Represents fontRef: a CT_FontReference element that specifies a reference to a themed font.
    /// </summary>
    DmlChartFontReference getFontReference()
    {
        if (mFontReference == null)
            mFontReference = new DmlChartFontReference();
        return mFontReference;
    }
    void setFontReference(DmlChartFontReference value) { mFontReference = value; }

    /// <summary>
    /// Represents spPr: a CT_ShapeProperties ([ISO/IEC29500-1:2012] section A.4.1) element that specifies visual
    /// shape properties of the part of the chart associated with this CT_StyleEntry. These properties override
    /// properties that are specified by fillRef, lnRef and effectRef.
    /// </summary>
    DefaultShapeProperties getShapePr() { return mShapePr; }
    void setShapePr(DefaultShapeProperties value) { mShapePr = value; }

    /// <summary>
    /// Represents defRPr: a CT_TextCharacterProperties ([ISO/IEC29500-1:2012] section A.4.1) element that 
    /// specifies the default text character properties for a text body on a chart which is associated with this 
    /// CT_StyleEntry. If a CT_SchemeColor ([ISO/IEC29500-1:2012] section A.4.1) element within this element has 
    /// a value of phClr, then the color is resolved by replacing it with the color specified by fontRef.
    /// </summary>
    DmlRunProperties getDefaultRunPr() { return mDefaultRunPr; }
    void setDefaultRunPr(DmlRunProperties value) { mDefaultRunPr = value; }

    /// <summary>
    /// Represents bodyPr: a CT_TextBodyProperties ([ISO/IEC29500-1:2012] section A.4.1) element that specifies 
    /// the body properties for a text body on a chart that is associated with this CT_StyleEntry.
    /// </summary>
    DmlTextBodyProperties getTextBodyPr() { return mTextBodyPr; }
    void setTextBodyPr(DmlTextBodyProperties value) { mTextBodyPr = value; }

    /// <summary>
    /// Represents mods: an ST_StyleEntryModifierList attribute that specifies modifiers for this style entry.
    /// </summary>
    String[] getModifiers() { return mModifiers; }
    void setModifiers(String[] value) { mModifiers = value; }

    private DmlChartStyleReference mLineReference;
    private double mLineWidthScale = LINE_WIDTH_SCALE_DEFAULT;
    private DmlChartStyleReference mFillReference;
    private DmlChartStyleReference mEffectReference;
    private DmlChartFontReference mFontReference;
    private DefaultShapeProperties mShapePr;
    private DmlRunProperties mDefaultRunPr;
    private DmlTextBodyProperties mTextBodyPr;
    private String[] mModifiers;

    static final double LINE_WIDTH_SCALE_DEFAULT = 1d;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

