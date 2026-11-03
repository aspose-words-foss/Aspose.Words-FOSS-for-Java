// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// AG_IteratorAttributes
/// </summary>
class DmlIteratorAttributes
{
    /*DmlAxisType*/int[] getAxisType() { return mAxisType; }
    void setAxisType(/*DmlAxisType*/int[] value) { mAxisType = value; }

    /*DmlElementType*/int[] getPointType() { return mPointType; }
    void setPointType(/*DmlElementType*/int[] value) { mPointType = value; }

    boolean[] getHideLastTransition() { return mHideLastTransition; }
    void setHideLastTransition(boolean[] value) { mHideLastTransition = value; }

    int[] getStart() { return mStart; }
    void setStart(int[] value) { mStart = value; }

    int[] getCount() { return mCount; }
    void setCount(int[] value) { mCount = value; }

    int[] getStep() { return mStep; }
    void setStep(int[] value) { mStep = value; }

    private /*DmlAxisType*/int[] mAxisType = new /*DmlAxisType*/int[0];
    private /*DmlElementType*/int[] mPointType = new /*DmlElementType*/int[0];
    private boolean[] mHideLastTransition = new boolean[0];
    private int[] mStart = new int[0];
    private int[] mCount = new int[0];
    private int[] mStep = new int[0];
}

