// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;


/// <summary>
/// 20.1.8.39 hsl (Hue Saturation Luminance Effect)
/// This element specifies a hue/saturation/luminance effect. 
/// The hue, saturation, and luminance can each be adjusted relative to its current value.
/// </summary>
class DmlHueSaturationLuminanceEffect extends DmlEffect
{
    /// <summary>
    /// Gets the type of effects.
    /// </summary>
    /*override*/ /*DmlEffectType*/int getType() { return DmlEffectType.HUE_SATURATION_LUMINANCE; }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlHueSaturationLuminanceEffect value = (DmlHueSaturationLuminanceEffect)obj;

        return msObject.equals(value.getHue(), getHue()) &&
               MathUtil.areEqual(value.getLuminance(), getLuminance()) &&
               MathUtil.areEqual(value.getSaturation(), getSaturation());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= getHue().hashCode();
        hash ^= msNumber.doubleHashCode(getLuminance());
        hash ^= msNumber.doubleHashCode(getSaturation());
        return hash;
    }

    /// <summary>
    /// Specifies the number of degrees by which the hue is adjusted.
    /// </summary>
    DmlAngle getHue() { return mHue; }
    void setHue(DmlAngle value) { mHue = value; }

    /// <summary>
    /// Specifies the percentage by which the luminance is adjusted.
    /// </summary>
    double getLuminance() { return mLuminance; }
    void setLuminance(double value) { mLuminance = value; }

    /// <summary>
    /// Specifies the percentage by which the saturation is adjusted.
    /// </summary>
    double getSaturation() { return mSaturation; }
    void setSaturation(double value) { mSaturation = value; }

    private DmlAngle mHue = new DmlAngle(); // For Java
    private double mLuminance;
    private double mSaturation;
}

