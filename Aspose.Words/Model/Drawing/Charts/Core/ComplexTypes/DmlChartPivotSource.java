// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/07/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents 5.7.2.145 pivotSource (Pivot Source) element.
/// This element specifies the source pivot table for a pivot chart.
/// </summary>
class DmlChartPivotSource extends DmlExtensionListSource implements Cloneable
{
    DmlChartPivotSource deepClone()
    {
        DmlChartPivotSource lhs = (DmlChartPivotSource)memberwiseClone();
        lhs.setExtensions(cloneExtensions());
        return lhs;
    }

    int getFmtId() { return mFmtId; }
    void setFmtId(int value) { mFmtId = value; }

    String getName() { return mName; }
    void setName(String value) { mName = value; }

    private int mFmtId;
    private String mName;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

