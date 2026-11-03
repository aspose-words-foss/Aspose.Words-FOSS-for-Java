// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/03/2015 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



class DmlTextBulletSize implements Cloneable
{
    public DmlTextBulletSize(double value, /*DmlTextBulletSizeType*/int sizeType)
    {
        mValue = value;
        mSizeType = sizeType;
    }

    /// <summary>
    /// Clones this instance of bullet size.
    /// </summary>
    DmlTextBulletSize deepClone()
    {
        return (DmlTextBulletSize)memberwiseClone();
    }

    /// <summary>
    /// Returns bullet font size in half-points.
    /// </summary>
    public int getBulletSize(RunPr runPr)
    {
        switch (mSizeType)
        {
            case DmlTextBulletSizeType.POINTS:
            {
                DmlTextPoints points = new DmlTextPoints((int)getValue());
                return (int)(points.getValueInPoints() * 2.0);
            }
            case DmlTextBulletSizeType.PERCENTAGE:
            {
                double fraction = DmlPercentageUtil.fromDmlPercent(getValue());
                return (int)(fraction * runPr.getSize());
            }
            case DmlTextBulletSizeType.FOLOW_TEXT:
            default:
            {
                return runPr.getSize();
            }
        }
    }

    public /*DmlTextBulletSizeType*/int getSizeType() { return mSizeType; }

    /// <summary>
    /// Flag indicates that bullet size must follow text size.
    /// </summary>
    public boolean getFollowText() { return (mSizeType == DmlTextBulletSizeType.FOLOW_TEXT); }

    /// <summary>
    /// Size value.
    /// </summary>
    public double getValue() { return mValue; }

    private /*final*/ /*DmlTextBulletSizeType*/int mSizeType;
    private /*final*/ double mValue;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

