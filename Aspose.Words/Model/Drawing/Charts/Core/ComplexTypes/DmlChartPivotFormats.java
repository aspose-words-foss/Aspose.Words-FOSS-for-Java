// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/07/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntToObjDictionary;


/// <summary>
/// Represents 5.7.2.144 pivotFmts (Pivot Formats) element.
/// This element contains a collection of formatting bands for a surface chart indexed from low to high.
/// </summary>
class DmlChartPivotFormats
{
    void addFmt(DmlChartPivotFormat pivotFormat)
    {
        mFmts.set(pivotFormat.getIndex(), pivotFormat);
    }

    DmlChartPivotFormats deepClone()
    {
        DmlChartPivotFormats lhs = new DmlChartPivotFormats();

        for (DmlChartPivotFormat pivotFormat : mFmts.getValues())
            lhs.addFmt(pivotFormat.deepClone());

        return lhs;
    }

    DmlChartPivotFormat getFmt(int index)
    {
        return mFmts.get(index);
    }

    IntToObjDictionary<DmlChartPivotFormat> getFmts() { return mFmts; }

    /// <summary>
    ///  Dictionary contains formatting. Key is index, value is <see cref="DmlChartPivotFormat"/>.
    /// </summary>
    private /*final*/ IntToObjDictionary<DmlChartPivotFormat> mFmts = new IntToObjDictionary<DmlChartPivotFormat>();
}

