// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/06/2025 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


class SimpleGlyfPoint
{
    SimpleGlyfPoint(short dx, short dy, boolean isOnCurve, boolean isEndOfContour)
    {
    	this(dx, dy, isOnCurve, isEndOfContour, false);
	
    }

    SimpleGlyfPoint(short dx, short dy, boolean isOnCurve, boolean isEndOfContour, boolean isOverlap)
    {
        setDX(dx);
        setDY(dy);
        mIsOnCurve = isOnCurve;
        mIsEndOfContour = isEndOfContour;
        mIsOverlap = isOverlap;
    }

    public short getDX() { return mDX; }; public void setDX(short value) { mDX = value; };

    private short mDX;

    public short getDY() { return mDY; }; public void setDY(short value) { mDY = value; };

    private short mDY;

    public boolean isOnCurve() { return mIsOnCurve; };

    private  boolean mIsOnCurve;

    public boolean isEndOfContour() { return mIsEndOfContour; };

    private  boolean mIsEndOfContour;

    public boolean isOverlap() { return mIsOverlap; };

    private  boolean mIsOverlap;
}

