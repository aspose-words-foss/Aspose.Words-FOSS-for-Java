// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/14/2013 by Alexey Noskov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


public class Comparables
{
    public Comparables(String[] comparableOuts, String[] comparableGolds, String[] comparableOriginalGolds)
    {
        mComparableOuts = comparableOuts;
        mComparableGolds = comparableGolds;
        mComparableOriginalGolds = comparableOriginalGolds;
    }

    public String[] getComparableOuts() { return mComparableOuts; }

    public String[] getComparableGolds() { return mComparableGolds; }

    public String[] getComparableOriginalGolds() { return mComparableOriginalGolds; }

    private /*final*/ String[] mComparableOuts;
    private /*final*/ String[] mComparableGolds;
    private /*final*/ String[] mComparableOriginalGolds;
}
