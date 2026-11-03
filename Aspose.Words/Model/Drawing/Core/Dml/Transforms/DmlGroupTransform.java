// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.msPointF;
import com.aspose.MathUtil;


class DmlGroupTransform extends DmlTransform
{
    void fitChildrenArea()
    {
        setChildWidth(getWidth());
        setChildHeight(getHeight());

        setChildX(getX());
        setChildY(getY());
    }

    /*override*/ void scale(double xscale, double yscale)
    {
        if ((xscale == 1.0) && (yscale == 1.0))
            return;

        super.scale(xscale, yscale);

        mChildWidth *= xscale;
        mChildHeight *= yscale;
    }

    /*override*/ DmlTransform deepClone()
    {
        DmlGroupTransform result = new DmlGroupTransform();
        copyTo(result);
        return result;
    }

    /*override*/ void offset(float dx, float dy)
    {
        super.offset(dx, dy);
        mChildX += dx;
        mChildY += dy;
    }

    protected void copyTo(DmlGroupTransform target)
    {
        super.copyTo(target);
        target.mChildHeight = mChildHeight;
        target.mChildWidth = mChildWidth;
        target.mChildX = mChildX;
        target.mChildY = mChildY;
    }

    /// <summary>
    /// Specifies the width of the extents rectangle in EMUs. This rectangle shall 
    /// dictate the size of the object as displayed (the result of any 
    /// scaling to the original object).
    /// In Office, this element additionally defines the group scaling factor which 
    /// is the ratio of the extents to child extents when the size of the child extents 
    /// is nonzero. If the size of the child extents is zero or omitted, there is no group scaling.
    /// </summary>
    double getChildWidth() { return mChildWidth; }
    void setChildWidth(double value) { mChildWidth = value; }

    /// <summary>
    /// Specifies the length of the extents rectangle in EMUs. This rectangle shall 
    /// dictate the size of the object as displayed (the result of any 
    /// scaling to the original object).
    /// In Office, this element additionally defines the group scaling factor which 
    /// is the ratio of the extents to child extents when the size of the child extents 
    /// is nonzero. If the size of the child extents is zero or omitted, there is no group scaling.
    /// </summary>
    double getChildHeight() { return mChildHeight; }
    void setChildHeight(double value) { mChildHeight = value; }

    /*PointF*/long getChildTopLeft() { return msPointF.ctor((float)getChildX(), (float)getChildY()); }
    void setChildTopLeft(/*PointF*/long value)
    {
        setChildX(msPointF.getX(value));
        setChildY(msPointF.getY(value));
    }

    /// <summary>
    /// Specifies a coordinate on the x-axis. The origin point for this coordinate shall be specified 
    /// by the parent XML element. In Office, the child extents and offset are used to determine 
    /// the center for scaling, rotation and flipping.
    /// </summary>
    double getChildX() { return mChildX; }
    void setChildX(double value) { mChildX = value; }

    /// <summary>
    /// Specifies a coordinate on the y-axis. The origin point for this coordinate shall be specified 
    /// by the parent XML element.
    /// </summary>
    double getChildY() { return mChildY; }
    void setChildY(double value) { mChildY = value; }

    private float getLengthRatio()
    {
        //If the starting shape has zero width (e.g., it is a vertical line), 
        // then the cx attribute of a:ext is ignored and the horizontal scaling is skipped. 
        // Similarly, if the starting shape has zero height, then the cy attribute 
        // of a:ext is ignored and the vertical scaling is skipped. 
        if (MathUtil.isZero(getHeight()) || MathUtil.isZero(getChildHeight()))
            return 1.0f;
        else
            return (float)(getHeight() / getChildHeight());
    }

    private float getWidthRatio()
    {
        // If the starting shape has zero width (e.g., it is a vertical line), 
        // then the cx attribute of a:ext is ignored and the horizontal scaling is skipped. 
        // Similarly, if the starting shape has zero height, then the cy attribute 
        // of a:ext is ignored and the vertical scaling is skipped. 
        if (MathUtil.isZero(getWidth()) || MathUtil.isZero(getChildWidth()))
            return 1.0f;
        else
            return (float)(getWidth() / getChildWidth());
    }

    /*override*/ double getXOffset() { return getX() - getChildX(); }

    /*override*/ double getYOffset() { return getY() - getChildY(); }

    /*override*/ float getXScale() { return getWidthRatio(); }

    /*override*/ float getYScale() { return getLengthRatio(); }

    private double mChildHeight;
    private double mChildWidth;
    private double mChildX;
    private double mChildY;
}

