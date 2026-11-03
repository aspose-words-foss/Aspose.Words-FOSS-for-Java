// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/03/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;
import com.aspose.ms.System.Drawing.RectangleF;


/// <summary>
/// Represents a rectangle defined by percentage offsets of its sides.
/// </summary>
class DmlPercentageOffsetRectangle
{
    /// <summary>
    /// Initializes a new instance of the <see cref="DmlPercentageOffsetRectangle" /> class.
    /// </summary>
    DmlPercentageOffsetRectangle()
    {
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="DmlPercentageOffsetRectangle" /> class.
    /// </summary>
    DmlPercentageOffsetRectangle(double leftOffset, double topOffset, double rightOffset, double bottomOffset)
    {
        mLeftOffset = leftOffset;
        mTopOffset = topOffset;
        mRightOffset = rightOffset;
        mBottomOffset = bottomOffset;
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="DmlPercentageOffsetRectangle" /> class.
    /// </summary>
    DmlPercentageOffsetRectangle(DmlPercentageOffsetRectangle other)
    {
    	this(other.getLeftOffset(), other.getTopOffset(), other.getRightOffset(), other.getBottomOffset());
	
    }

    public boolean equals(DmlPercentageOffsetRectangle other)
    {
        if (msObject.referenceEquals(this, other))
            return true;

        return MathUtil.areEqual(mLeftOffset, other.getLeftOffset()) &&
               MathUtil.areEqual(mTopOffset, other.getTopOffset()) &&
               MathUtil.areEqual(mRightOffset, other.getRightOffset()) &&
               MathUtil.areEqual(mBottomOffset, other.getBottomOffset());
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (obj.getClass() != DmlPercentageOffsetRectangle.class)
            return false;
        return equals((DmlPercentageOffsetRectangle)obj);
    }

    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            int result = msNumber.doubleHashCode(mBottomOffset);
            result = (result*397) ^ msNumber.doubleHashCode(mLeftOffset);
            result = (result*397) ^ msNumber.doubleHashCode(mRightOffset);
            result = (result*397) ^ msNumber.doubleHashCode(mTopOffset);
            return result;
        }
    }

    public DmlPercentageOffsetRectangle deepClone()
    {
        DmlPercentageOffsetRectangle result = new DmlPercentageOffsetRectangle();
        result.setTopOffset(getTopOffset());
        result.setBottomOffset(getBottomOffset());
        result.setLeftOffset(getLeftOffset());
        result.setRightOffset(getRightOffset());
        return result;
    }

    RectangleF adjust(RectangleF rect)
    {
        if (isEmpty())
            return rect;

        float w = rect.getWidth();
        float h = rect.getHeight();
        float l = (float)(rect.getLeft() + w * getLeftOffset());
        float r = (float)(rect.getRight() - w * getRightOffset());
        float t = (float)(rect.getTop() + h * getTopOffset());
        float b = (float)(rect.getBottom() - h * getBottomOffset());
        return RectangleF.fromLTRB(l, t, r, b);
    }

    /// <summary>
    /// Specifies the bottom edge of the rectangle.
    /// </summary>
    double getBottomOffset() { return mBottomOffset; }
    void setBottomOffset(double value) { mBottomOffset = value; }

    /// <summary>
    /// Specifies the top edge of the rectangle.
    /// </summary>
    double getTopOffset() { return mTopOffset; }
    void setTopOffset(double value) { mTopOffset = value; }

    /// <summary>
    /// Specifies the left edge of the rectangle.
    /// </summary>
    double getLeftOffset() { return mLeftOffset; }
    void setLeftOffset(double value) { mLeftOffset = value; }

    /// <summary>
    /// Specifies the right edge of the rectangle.
    /// </summary>
    double getRightOffset() { return mRightOffset; }
    void setRightOffset(double value) { mRightOffset = value; }

    boolean isEmpty()
    {
        return MathUtil.isZero(getBottomOffset())
               && MathUtil.isZero(getTopOffset())
               && MathUtil.isZero(getLeftOffset())
               && MathUtil.isZero(getRightOffset());
    }

    private double mBottomOffset;
    private double mLeftOffset;
    private double mRightOffset;
    private double mTopOffset;
}

