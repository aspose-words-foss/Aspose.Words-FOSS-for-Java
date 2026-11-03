// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;


/// <summary>
/// Represents 5.7.2.202 strRef (String Reference) or 5.7.2.124 numRef (Number Reference) element.
/// This element specifies a reference to data for a single data label or title with a cache of the last values used.
/// Also it is used to store data of the CT_NumericDimension and CT_StringDimension complex types [MS-ODRAWXML].
/// </summary>
class DmlChartValueRef extends DmlExtensionListSource implements Cloneable
{
    DmlChartValueRef(/*DmlChartValueType*/int refType)
    {
        mValues = new DmlChartValueCollection(refType);
    }

    DmlChartValueRef(DmlChartValueCollection values)
    {
        mValues = values;
    }

    DmlChartValueRef deepClone()
    {
        DmlChartValueRef lhs = (DmlChartValueRef)memberwiseClone();
        lhs.mValues = mValues.deepClone();

        if (mFormula != null)
            lhs.mFormula = mFormula.deepClone();

        if (mNameFormula != null)
            lhs.mNameFormula = mNameFormula.deepClone();

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    /// <summary>
    /// Removes the formula and the extensions so that the instance represents literal data rather than a reference
    /// to XLSX content.
    /// </summary>
    /// <remarks>
    /// The formula becomes invalid after adding/removing chart data values and must be removed, otherwise
    /// MS Word will generate incorrect XLSX after performing Edit Data.
    /// </remarks>
    /// <dev>
    /// Data reference is stored as a 21.2.2.123 numRef (Number Reference) or 21.2.2.201 strRef (String Reference)
    /// element. Literal data is stored as a 21.2.2.122 numLit (Number Literal) or 21.2.2.200 strLit (String Literal)
    /// element.
    /// </dev>
    void markAsLiteralData()
    {
        if (isLiteralData())
            return;

        mFormula = null;

        // Clear extensions because they may be incorrect for a numLit/strLit element.
        if (getExtensions() != null)
            getExtensions().clear();
    }

    DmlChartFormula getFormula()
    {
        if (mFormula == null)
            mFormula = new DmlChartFormula();

        return mFormula;
    }

    /// <summary>
    /// Gets a flag indicating whether this instance is a container for literal data (a 21.2.2.200 strLit (String
    /// Literal) or 21.2.2.122 numLit (Number Literal) element). If value of the property is <c>false</c>, the
    /// instance is a 21.2.2.201 strRef (String Reference) or 21.2.2.123 numRef (Number Reference) element.
    /// </summary>
    boolean isLiteralData()
    {
        return ((mFormula == null) || !StringUtil.hasChars(mFormula.getValue()));
    }

    /// <summary>
    /// Gets or sets a formula that specifies dimension name reference.
    /// </summary>
    /// <remarks>
    /// It is the nf element of the CT_NumericDimension and CT_StringDimension complex types [MS-ODRAWXML]
    /// </remarks>
    DmlChartFormula getNameFormula()
    {
        if (mNameFormula == null)
            mNameFormula = new DmlChartFormula();
        return mNameFormula;
    }

    /// <summary>
    /// Gets series data.
    /// </summary>
    DmlChartValueCollection getValues() { return mValues; }

    /// <summary>
    /// Gets or sets type of this dimension. 
    /// </summary>
    /// <remarks>
    /// Represents the 'type' attribute of the CT_NumericDimension or CT_StringDimension complex
    /// type [MS-ODRAWXML].
    /// </remarks>
    /*DimensionType*/int getDimensionType() { return mDimensionType; }
    void setDimensionType(/*DimensionType*/int value) { mDimensionType = value; }

    private DmlChartFormula mFormula;
    private DmlChartFormula mNameFormula;
    private DmlChartValueCollection mValues;
    private /*DimensionType*/int mDimensionType;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

