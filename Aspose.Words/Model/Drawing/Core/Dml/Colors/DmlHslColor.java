// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;
import com.aspose.DrColor;
import com.aspose.HSLColor;


/// <summary>
/// This element specifies a color using the HSL color model. 
/// A perceptual gamma of 2.2 is assumed.
/// Hue refers to the dominant wavelength of color, saturation 
/// refers to the purity of its hue, and luminance refers to its lightness or darkness.
/// </summary>
class DmlHslColor extends DmlColor
{
    public /*override*/ DmlColor deepClone()
    {
        DmlHslColor result = new DmlHslColor();
        result.setSaturation(getSaturation());
        result.setLuminance(getLuminance());
        result.setHue(getHue());
        copyColorModifiersTo(result);
        return result;
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlHslColor value = (DmlHslColor)obj;

        return msObject.equals(value.getHue(), getHue()) &&
               MathUtil.areEqual(value.getLuminance(), getLuminance()) &&
               MathUtil.areEqual(value.getSaturation(), getSaturation());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= msNumber.doubleHashCode(getHue().getValue());
        hash ^= msNumber.doubleHashCode(getLuminance());
        hash ^= msNumber.doubleHashCode(getSaturation());
        return hash;
    }

    public /*override*/ /*DmlColorType*/int getColorType() { return DmlColorType.HSL_COLOR; }

    protected /*override*/ DrColor createUnmodifiedColor(IThemeProvider themeProvider)
    {
        HSLColor hsl = new HSLColor(getHue().getValueInDegrees() / 360.0, getSaturation(), getLuminance());
        return hsl.toDrColor();
    }

    /// <summary>
    /// Specifies the saturation referring to the purity of the hue. 
    /// Expressed as a percentage with 0% referring to grey, 100% referring to the purest form of the hue.
    /// Value is in fraction representation.
    /// </summary>
    double getSaturation() { return mSaturation; }
    void setSaturation(double value) { mSaturation = value; }

    /// <summary>
    /// Specifies the luminance referring to the lightness or darkness of the color. 
    /// Expressed as a percentage with 0% referring to maximal dark (black) 
    /// and 100% referring to maximal white.
    /// Value is in fraction representation.
    /// </summary>
    double getLuminance() { return mLuminance; }
    void setLuminance(double value) { mLuminance = value; }

    /// <summary>
    /// Specifies the angular value describing the wavelength. Expressed in 1/60000ths of a degree.
    /// </summary>
    DmlAngle getHue() { return mHue; }
    void setHue(DmlAngle value) { mHue = value; }

    private DmlAngle mHue = new DmlAngle(); // For java
    private double mLuminance;
    private double mSaturation;
}

