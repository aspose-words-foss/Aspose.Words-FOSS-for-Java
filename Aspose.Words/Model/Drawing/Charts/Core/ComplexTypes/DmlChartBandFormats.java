// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/07/2012 by Alexey Noskov


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntToObjDictionary;


/// <summary>
/// Represents 5.7.2.14 bandFmts (Band Formats) element.
/// This element contains a collection of formatting bands for a surface chart indexed from low to high.
/// </summary>
class DmlChartBandFormats
{
    DmlChartBandFormats deepClone()
    {
        DmlChartBandFormats lhs = new DmlChartBandFormats();

        IntToObjDictionary<DmlChartBandFormat>.Enumerator enumerator = mFmts.iterator();
        while (enumerator.moveNext())
        {
            DmlChartBandFormat format = enumerator.getCurrentValue();
            lhs.mFmts.set(enumerator.getCurrentKey(), format.deepClone());
        }

        return lhs;
    }

    void addFmt(int index, DmlChartSpPr spPr)
    {
        mFmts.set(index, new DmlChartBandFormat(index, spPr));
    }

    DmlChartSpPr getFmt(int index)
    {
        DmlChartBandFormat pr = mFmts.get(index);
        return (pr != null) ? pr.getSpPr() : new DmlChartSpPr();
    }

    Iterable<DmlChartBandFormat> getFormats() { return mFmts.getValues(); }

    int getCount() { return mFmts.getCount(); }

    /// <summary>
    /// Dictionary contains formatting. Key is index, value is SpPr.
    /// </summary>
    private /*final*/ IntToObjDictionary<DmlChartBandFormat> mFmts = new IntToObjDictionary<DmlChartBandFormat>();
}

