// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/07/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents 5.7.2.150 protection (Protection) element.
/// This element specifies protection for the chart.
/// </summary>
class DmlChartProtection implements Cloneable
{
    DmlChartProtection deepClone()
    {
        return (DmlChartProtection)memberwiseClone();
    }

    boolean getChartObject() { return mChartObject; }
    void setChartObject(boolean value) { mChartObject = value; }

    boolean getData() { return mData; }
    void setData(boolean value) { mData = value; }

    boolean getFormatting() { return mFormatting; }
    void setFormatting(boolean value) { mFormatting = value; }

    boolean getSelection() { return mSelection; }
    void setSelection(boolean value) { mSelection = value; }

    boolean getUserInterface() { return mUserInterface; }
    void setUserInterface(boolean value) { mUserInterface = value; }

    boolean hasProtection() { return getChartObject() || getData() || getFormatting() || getSelection() || getUserInterface(); }

    private boolean mChartObject;
    private boolean mData;
    private boolean mFormatting;
    private boolean mSelection;
    private boolean mUserInterface;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

