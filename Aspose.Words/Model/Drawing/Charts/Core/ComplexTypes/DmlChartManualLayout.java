// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;
import com.aspose.ms.System.msMath;


/// <summary>
/// Represents 5.7.2.105 manualLayout (Manual Layout) element.
/// This element specifies the exact position of a chart element.
/// </summary>
class DmlChartManualLayout extends DmlExtensionListSource implements Cloneable
{
    DmlChartManualLayout deepClone()
    {
        DmlChartManualLayout lhs = (DmlChartManualLayout)memberwiseClone();
        lhs.setExtensions(cloneExtensions());
        return lhs;
    }

    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlChartManualLayout value = (DmlChartManualLayout)obj;

        return
            MathUtil.areEqual(getHeight(), value.getHeight()) &&
            (getHeightMode() == value.getHeightMode()) &&
            (getLayoutTarget() == value.getLayoutTarget()) &&
            MathUtil.areEqual(getWidth(), value.getWidth()) &&
            (getWidthMode() == value.getWidthMode()) &&
            MathUtil.areEqual(getLeft(), value.getLeft()) &&
            (getLeftMode() == value.getLeftMode()) &&
            MathUtil.areEqual(getTop(), value.getTop()) &&
            (getTopMode() == value.getTopMode());
    }

    public /*override*/ int hashCode()
    {
        int hash = 0;
        hash ^= getHashOfDouble(getHeight());
        hash ^= /*enum.hashCode()=(int)value*/getHeightMode();
        hash ^= /*enum.hashCode()=(int)value*/getLayoutTarget();
        hash ^= getHashOfDouble(getWidth());
        hash ^= /*enum.hashCode()=(int)value*/getWidthMode();
        hash ^= getHashOfDouble(getLeft());
        hash ^= /*enum.hashCode()=(int)value*/getLeftMode();
        hash ^= getHashOfDouble(getTop());
        hash ^= /*enum.hashCode()=(int)value*/getTopMode();

        return hash;
    }

    /// <summary>
    /// Gets a hash value with support of approximate equality.
    /// </summary>
    private static int getHashOfDouble(double value)
    {
        final int PRECISION = 10;
        return msNumber.doubleHashCode(msMath.round(value, PRECISION));
    }

    double getHeight() { return mHeight; }
    void setHeight(double value) { mHeight = value; }

    /*LayoutMode*/int getHeightMode() { return mHeightMode; }
    void setHeightMode(/*LayoutMode*/int value) { mHeightMode = value; }

    /*LayoutTarget*/int getLayoutTarget() { return mLayoutTarget; }
    void setLayoutTarget(/*LayoutTarget*/int value) { mLayoutTarget = value; }

    double getWidth() { return mWidth; }
    void setWidth(double value) { mWidth = value; }

    /*LayoutMode*/int getWidthMode() { return mWidthMode; }
    void setWidthMode(/*LayoutMode*/int value) { mWidthMode = value; }

    double getLeft() { return mLeft; }
    void setLeft(double value) { mLeft = value; }

    /*LayoutMode*/int getLeftMode() { return mLeftMode; }
    void setLeftMode(/*LayoutMode*/int value) { mLeftMode = value; }

    double getTop() { return mTop; }
    void setTop(double value) { mTop = value; }

    /*LayoutMode*/int getTopMode() { return mTopMode; }
    void setTopMode(/*LayoutMode*/int value) { mTopMode = value; }

    /// <summary>
    /// Returns true if manual layout is empty, i.e. position and size of target rectangle is zero.
    /// MS Word seems to ignore such manual layouts, so we should do the same.
    /// </summary>
    boolean isEmpty() { return (!isXSet() && !isYSet() && !isWidthSet() && !isHeightSet()); }

    /// <summary>
    /// Indicates whether the x-value of the manual layout is set.
    /// </summary>
    boolean isXSet() { return !Double.isNaN(mLeft); }

    /// <summary>
    /// Indicates whether the y-value of the manual layout is set.
    /// </summary>
    boolean isYSet() { return !Double.isNaN(mTop); }

    /// <summary>
    /// Indicates whether the width of the manual layout is set.
    /// </summary>
    boolean isWidthSet() { return !Double.isNaN(mWidth); }

    /// <summary>
    /// Indicates whether the height of the manual layout is set.
    /// </summary>
    boolean isHeightSet() { return !Double.isNaN(mHeight); }

    private double mHeight = Double.NaN;
    private /*LayoutMode*/int mHeightMode = LayoutMode.FACTOR;
    private /*LayoutTarget*/int mLayoutTarget = LayoutTarget.OUTER;
    private double mWidth = Double.NaN;
    private /*LayoutMode*/int mWidthMode = LayoutMode.FACTOR;
    private double mLeft = Double.NaN;
    private /*LayoutMode*/int mLeftMode = LayoutMode.FACTOR;
    private double mTop = Double.NaN;
    private /*LayoutMode*/int mTopMode = LayoutMode.FACTOR;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

