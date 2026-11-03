// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.Debug;


/// <summary>
/// This class represents CT_NumDataSource or CT_StrDataSource complex types.
/// It contains either embedded data or referenced data.
/// </summary>
class DmlChartDataSource implements Cloneable
{
    DmlChartDataSource deepClone()
    {
        DmlChartDataSource lhs = (DmlChartDataSource)memberwiseClone();

        if (mValueRef != null)
            lhs.mValueRef = mValueRef.deepClone();

        if (isFallbackValueRefActual())
            lhs.setFallbackValueRef(mFallbackValueRef.deepClone());
        else
            lhs.mFallbackValueRef = null;

        return lhs;
    }

    /// <summary>
    /// Gets or generates a fallback value collection that is needed for a multi-level string category in
    /// a pre-Word 2016 chart. Values of a such category are written as an AlternateContent XML element.
    /// </summary>
    /// <returns></returns>
    DmlChartValueCollection getOrGenerateFallbackValues()
    {
        // Fallback values are needed only for multi-level string values.
        if ((getValues() == null) || (getValues().getValueType() != DmlChartValueType.MULTI_LVL_STRING))
            return null;

        // Check for existing fallback values.
        if (isFallbackValueRefActual())
            return mFallbackValueRef.getValues();

        DmlChartValueCollection fallbackValues = new DmlChartValueCollection(DmlChartValueType.STRING);

        for (DmlChartValue val : getValues())
        {
            DmlChartMultiLvlStrValue value = (DmlChartMultiLvlStrValue)val;
            StringBuilder sb = new StringBuilder();

            for (Object level : value.getLevels())
            {
                String levelValue = (String)level;
                if (sb.length() > 0)
                    msStringBuilder.append(sb, "\r\n");

                msStringBuilder.append(sb, levelValue);
            }

            fallbackValues.add(new DmlChartStrValue(value.getIndex(), sb.toString()));
        }

        setFallbackValueRef(new DmlChartValueRef(fallbackValues));
        return fallbackValues;
    }

    /// <summary>
    /// Gets a flag indicating whether <see cref="FallbackValueRef"/> is not null and contains actual fallback data
    /// for the data stored in the <see cref="Values"/> property.
    /// </summary>
    private boolean isFallbackValueRefActual()
    {
        return
            (mFallbackValueRef != null) &&
            (getValues() != null) &&
            (mFallbackValueChangeCount == getValues().getValueChangeCount());
    }

    /// <summary>
    /// Gets or sets series data.
    /// </summary>
    DmlChartValueCollection getValues() { return (mValueRef != null) ? mValueRef.getValues() : null; }
    void setValues(DmlChartValueCollection value) { setValueRef((value != null) ? new DmlChartValueRef(value) : null); }

    /// <summary>
    /// Gets or sets reference to values.
    /// </summary>
    DmlChartValueRef getValueRef() { return mValueRef; }
    void setValueRef(DmlChartValueRef value)
    {
        mValueRef = value;
        mFallbackValueRef = null;
    }

    /// <summary>
    /// Gets or sets a reference to fallback values.
    /// </summary>
    DmlChartValueRef getFallbackValueRef() { return mFallbackValueRef; }
    void setFallbackValueRef(DmlChartValueRef value)
    {
        // A Choice part of an alternate content is always before a fallback part: expect that Values already
        // exists.
        Debug.assertTrue(getValues() != null);
        mFallbackValueChangeCount = getValues().getValueChangeCount();
        mFallbackValueRef = value;
    }

    private DmlChartValueRef mValueRef;
    private DmlChartValueRef mFallbackValueRef;
    private int mFallbackValueChangeCount;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

