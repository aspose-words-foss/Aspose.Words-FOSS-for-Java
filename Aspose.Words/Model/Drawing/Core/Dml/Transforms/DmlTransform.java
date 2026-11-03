// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.msSizeF;
import com.aspose.MathUtil;
import com.aspose.ms.System.Drawing.msPointF;
import com.aspose.ms.System.Drawing.RectangleF;


class DmlTransform
{
    /*SizeF*/long getExtents()
    {
        return msSizeF.ctor((float)getWidth(), (float)getHeight());
    }

    /*virtual*/ void scale(double xscale, double yscale)
    {
        mWidth *= xscale;
        mHeight *= yscale;
    }

    /*virtual*/ void offset(float dx, float dy)
    {
        mX += dx;
        mY += dy;
    }

    /*virtual*/ DmlTransform deepClone()
    {
        DmlTransform result = new DmlTransform();
        copyTo(result);
        return result;
    }

    /// <summary>
    /// Returns true if the specified transformation equals this transformation.
    /// </summary>
    public boolean equals(DmlTransform transform)
    {
        // This condition exits the allowed number of conditional operators, 
        // but we have to check all the properties one by one here. So leave it as is.
        return (transform.mFlip == mFlip) &&
               (transform.mVerticalFlip == mVerticalFlip) &&
               MathUtil.areEqual(transform.mHeight, mHeight) &&
               MathUtil.areEqual(transform.mRotation.getValue(), mRotation.getValue()) &&
               MathUtil.areEqual(transform.mWidth, mWidth) &&
               MathUtil.areEqual(transform.mX, mX) &&
               MathUtil.areEqual(transform.mY, mY);
    }

    /// <summary>
    /// Returns true if the transformation is empty.
    /// </summary>
    boolean isEmpty() { return this.equals(G_EMPTY); }

    protected void copyTo(DmlTransform target)
    {
        target.mFlip = mFlip;
        target.mHeight = mHeight;
        target.mRotation = mRotation;
        target.mVerticalFlip = mVerticalFlip;
        target.mWidth = mWidth;
        target.mX = mX;
        target.mY = mY;
    }

    /// <summary>
    /// Orientation of a shape.
    /// </summary>
    /*FlipOrientation*/int getFlipOrientation() { return mFlip; }
    void setFlipOrientation(/*FlipOrientation*/int value) { mFlip = value; }

    /// <summary>
    /// Specifies the clockwise rotation of a group in 1/60000 of a degree.
    /// </summary>
    DmlAngle getRotation() { return mRotation; }
    void setRotation(DmlAngle value) { mRotation = value; }

    /// <summary>
    /// Specifies the width of the extents rectangle in EMUs. This rectangle shall 
    /// dictate the size of the object as displayed (the result of any 
    /// scaling to the original object).
    /// In Office, the extents also determine the size and center of the object for most 
    /// geometry definitions. If the geometry data is independent of these extents, they are 
    /// only used to determine the center for rotation and flipping.
    /// </summary>
    double getWidth() { return mWidth; }
    void setWidth(double value) { mWidth = value; }

    /// <summary>
    /// Specifies the height of the extents rectangle in EMUs. This rectangle shall 
    /// dictate the size of the object as displayed (the result of any 
    /// scaling to the original object).
    /// In Office, the extents also determine the size and center of the object for most 
    /// geometry definitions. If the geometry data is independent of these extents, they are 
    /// only used to determine the center for rotation and flipping.
    /// </summary>
    double getHeight() { return mHeight; }
    void setHeight(double value) { mHeight = value; }

    /// <summary>
    /// Specifies a coordinate on the x-axis. The origin point for this coordinate 
    /// shall be specified by the parent XML element.
    /// In Office, the offset element specifies the location of a bounding box of 
    /// an object as defined by the ext element.
    /// </summary>
    double getX() { return mX; }
    void setX(double value) { mX = value; }

    /// <summary>
    /// Specifies a coordinate on the y-axis. The origin point for this coordinate 
    /// shall be specified by the parent XML element.
    /// In Office, the offset element specifies the location of a bounding box of 
    /// an object as defined by the ext element.
    /// </summary>
    double getY() { return mY; }
    void setY(double value) { mY = value; }

    /*PointF*/long getTopLeft() { return msPointF.ctor((float)getX(), (float)getY()); }
    void setTopLeft(/*PointF*/long value)
    {
        setX(msPointF.getX(value));
        setY(msPointF.getY(value));
    }

    RectangleF getBoundingBox() { return new RectangleF((float)getX(), (float)getY(), (float)getWidth(), (float)getHeight()); }

    /// <summary>
    /// Gets the center point without offset defined by X and Y.
    /// </summary>
    /*virtual*/ /*PointF*/long getOriginalCenterPoint() { return msPointF.isEmpty(mOriginalCenterPoint) ? msPointF.ctor((float)(getWidth() / 2.0), (float)(getHeight() / 2.0)) : mOriginalCenterPoint; }
    /*virtual*/ void setOriginalCenterPoint(/*PointF*/long value) { mOriginalCenterPoint = value; }

    /*virtual*/ float getXScale() { return 1.0f; }

    /*virtual*/ float getYScale() { return 1.0f; }

    /*virtual*/ double getXOffset() { return getX(); }

    /*virtual*/ double getYOffset() { return getY(); }

    private /*PointF*/long mOriginalCenterPoint = msPointF.Empty;
    private /*FlipOrientation*/int mFlip = FlipOrientation.NONE;
    private DmlAngle mRotation = new DmlAngle(); // For Java
    private boolean mVerticalFlip;
    private double mWidth;
    private double mHeight;
    private double mX;
    private double mY;

    private static /*final*/ DmlTransform G_EMPTY = new DmlGroupTransform();
}

