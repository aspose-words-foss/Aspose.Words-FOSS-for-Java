// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;
import com.aspose.DrColor;


/// <summary>
/// 20.1.8.15 blur (Blur Effect)
/// This element specifies a blur effect that is applied to the 
/// entire shape, including its fill. All color channels, 
/// including alpha, are affected.
/// </summary>
class DmlBlurEffect extends DmlEffect
{
    /// <summary>
    /// Gets the type of effects.
    /// </summary>
    /*override*/ /*DmlEffectType*/int getType() { return DmlEffectType.BLUR; }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlBlurEffect value = (DmlBlurEffect)obj;

        return (value.getGrow() == getGrow()) && MathUtil.areEqual(value.getRadius(), getRadius());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= msNumber.doubleHashCode(getRadius());
        hash ^= msNumber.booleanHashCode(getGrow());
        return hash;
    }

    /// <summary>
    /// Specifies whether the bounds of the object should be grown as a 
    /// result of the blurring. True indicates the bounds are grown while 
    /// false indicates that they are not.
    /// Default value is true.
    /// </summary>
    boolean getGrow() { return mGrow; }
    void setGrow(boolean value) { mGrow = value; }

    /// <summary>
    /// Specifies the radius of blur.
    /// </summary>
    double getRadius() { return mRadius; }
    void setRadius(double value) { mRadius = value; }

    /// <summary>
    /// Specifies that the resulting bitmap must be cropped to the original size after applying blur.
    /// </summary>
    boolean getCropToOriginalSize() { return mCropToOriginalSize; }
    void setCropToOriginalSize(boolean value) { mCropToOriginalSize = value; }

    DrColor getBackgroundColor() { return mBackgroundColor; }
    void setBackgroundColor(DrColor value) { mBackgroundColor = value; }

    private boolean mCropToOriginalSize = true;
    private boolean mGrow = true;
    private double mRadius;

    // Use transparent white as a default background.
    private DrColor mBackgroundColor = new DrColor(1, 255, 255, 255);
}
