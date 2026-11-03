// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;


/// <summary>
/// 20.1.8.42 lum (Luminance Effect)
/// This element specifies a luminance effect. 
/// Brightness linearly shifts all colors closer to white or black. 
/// Contrast scales all colors to be either closer or further apart.
/// </summary>
class DmlLuminanceEffect extends DmlEffect
{
    /// <summary>
    /// Gets the type of effects.
    /// </summary>
    /*override*/ /*DmlEffectType*/int getType() { return DmlEffectType.LUMINANCE; }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlLuminanceEffect value = (DmlLuminanceEffect)obj;

        return MathUtil.areEqual(value.getBrightness(), getBrightness()) &&
               MathUtil.areEqual(value.getContrast(), getContrast());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= msNumber.doubleHashCode(getBrightness());
        hash ^= msNumber.doubleHashCode(getContrast());
        return hash;
    }

    /// <summary>
    /// Specifies the percent to change the brightness.
    /// </summary>
    /// <remarks>
    /// Despite the various specs and according to MS Word behavior 
    /// this value is specified in range [-100%; 100%]. This should be forced for public API later.
    /// Keep it in range [0; 1].
    /// </remarks>
    double getBrightness() { return mBrightness; }
    void setBrightness(double value)
    {
        if ((value < 0.0) || (value > 1.0))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        mBrightness = value;
    }

    /// <summary>
    /// Specifies the percent to change the contrast.
    /// </summary>
    /// <remarks>
    /// Despite the various specs and according to MS Word behavior 
    /// this value is specified in range [-100%; 100%]. This should be forced for public API later.
    /// Keep it in range [0; 1].
    /// </remarks>
    double getContrast() { return mContrast; }
    void setContrast(double value)
    {
        if ((value < 0.0) || (value > 1.0))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        mContrast = value;
    }

    private double mBrightness;
    private double mContrast;
}
