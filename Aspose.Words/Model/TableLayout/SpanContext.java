// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Stores several values to pass between the methods that deal with spanned cell calculations.
/// </summary>
class SpanContext
{
    SpanContext()
    {
    }

    int getSpanIndex() { return mSpanIndex; }
    void setSpanIndex(int value) { mSpanIndex = value; }

    int getLastColumnIndex() { return mLastColumnIndex; }
    void setLastColumnIndex(int value) { mLastColumnIndex = value; }

    int getSpan() { return mSpan; }
    void setSpan(int value) { mSpan = value; }

    PreferredWidth getCellPreferredWidth() { return mCellPreferredWidth; }
    void setCellPreferredWidth(PreferredWidth value) { mCellPreferredWidth = value; }

    boolean getAllColumnsAreFixed() { return mAllColumnsAreFixed; }
    void setAllColumnsAreFixed(boolean value) { mAllColumnsAreFixed = value; }

    boolean getAllColumnsArePercent() { return mAllColumnsArePercent; }
    void setAllColumnsArePercent(boolean value) { mAllColumnsArePercent = value; }

    boolean hasAuto() { return mHasAuto; }
    void hasAuto(boolean value) { mHasAuto = value; }

    double getTotalPercent() { return mTotalPercent; }
    void setTotalPercent(double value) { mTotalPercent = value; }

    double getTotalFixed() { return mTotalFixed; }
    void setTotalFixed(double value) { mTotalFixed = value; }

    LayoutWidth getCellLayoutWidth() { return mCellLayoutWidth; }
    void setCellLayoutWidth(LayoutWidth value) { mCellLayoutWidth = value; }

    LayoutWidth getSpanLayoutWidth() { return mSpanLayoutWidth; }
    void setSpanLayoutWidth(LayoutWidth value) { mSpanLayoutWidth = value; }

    private int mSpan;
    private int mSpanIndex;
    private int mLastColumnIndex;
    private PreferredWidth mCellPreferredWidth;
    private boolean mAllColumnsAreFixed = true;
    private boolean mAllColumnsArePercent = true;
    private boolean mHasAuto = false;
    private double mTotalPercent = 0.0;
    private double mTotalFixed = 0.0;
    private LayoutWidth mCellLayoutWidth;
    private LayoutWidth mSpanLayoutWidth;
}

