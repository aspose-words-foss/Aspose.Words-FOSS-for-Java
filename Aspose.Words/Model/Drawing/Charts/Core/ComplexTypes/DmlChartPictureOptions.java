// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents 5.7.2.139 pictureOptions (Picture Options) element.
/// This element specifies the picture to be used on the data point, series, wall, or floor.
/// </summary>
class DmlChartPictureOptions implements Cloneable
{
    DmlChartPictureOptions deepClone()
    {
       return (DmlChartPictureOptions)memberwiseClone();
    }

    boolean getApplyToFront() { return mApplyToFront; }
    void setApplyToFront(boolean value) { mApplyToFront = value; }

    boolean getApplyToSides() { return mApplyToSides; }
    void setApplyToSides(boolean value) { mApplyToSides = value; }

    boolean getApplyToEnd() { return mApplyToEnd; }
    void setApplyToEnd(boolean value) { mApplyToEnd = value; }

    /*PictureFormat*/int getPictureFormat() { return mPictureFormat; }
    void setPictureFormat(/*PictureFormat*/int value) { mPictureFormat = value; }

    /// <summary>
    /// Zero means that property is not set.
    /// </summary>
    double getPictureStackUnit() { return mPictureStackUnit; }
    void setPictureStackUnit(double value)
    {
        // Value must be greater than zero.
        if (mPictureStackUnit > 0)
            mPictureStackUnit = value;
    }

    private boolean mApplyToFront;
    private boolean mApplyToSides;
    private boolean mApplyToEnd;
    private /*PictureFormat*/int mPictureFormat;
    private double mPictureStackUnit;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

