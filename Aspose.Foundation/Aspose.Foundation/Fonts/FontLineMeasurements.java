// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/07/2016 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Contains font line measurements like line spacing, ascent, descent.
/// </summary>
public class FontLineMeasurements
{
    public FontLineMeasurements(int ascent, int descent, int lineSpacing)
    {
        mAscent = ascent;
        mDescent = descent;
        mLineSpacing = lineSpacing;
    }

    public int getAscent() { return mAscent; }

    public int getDescent() { return mDescent; }

    public int getLineSpacing() { return mLineSpacing; }

    public int getCellHeight() { return getAscent() + getDescent(); }

    private /*final*/ int mAscent;
    private /*final*/ int mDescent;
    private /*final*/ int mLineSpacing;
}

