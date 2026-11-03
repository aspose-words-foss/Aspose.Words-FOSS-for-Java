// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;


/// <summary>
/// This element specifies a color using the red, green, blue RGB color model. 
/// Each component, red, green, and blue is expressed 
/// as a percentage from 0% to 100%. A linear gamma of 1.0 is assumed.
/// </summary>
class DmlPercentageRgbColor extends DmlColor
{
    protected /*override*/ DrColor createUnmodifiedColor(IThemeProvider themeProvider)
    {
        int r = MathUtil.doubleToInt(getR()*255.0);
        int g = MathUtil.doubleToInt(getG()*255.0);
        int b = MathUtil.doubleToInt(getB()*255.0);
        return new DrColor(r, g, b);
    }

    public /*override*/ DmlColor deepClone()
    {
        DmlPercentageRgbColor result = new DmlPercentageRgbColor();
        result.setR(getR());
        result.setG(getG());
        result.setB(getB());
        copyColorModifiersTo(result);
        return result;
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlPercentageRgbColor value = (DmlPercentageRgbColor)obj;

        return MathUtil.areEqual(value.getR(), getR()) &&
               MathUtil.areEqual(value.getG(), getG()) &&
               MathUtil.areEqual(value.getB(), getB());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= msNumber.doubleHashCode(getR());
        hash ^= msNumber.doubleHashCode(getG());
        hash ^= msNumber.doubleHashCode(getB());
        return hash;
    }

    public /*override*/ /*DmlColorType*/int getColorType() { return DmlColorType.PERCENTAGE_RGB_COLOR; }

    /// <summary>
    /// Specifies the percentage of red.
    /// </summary>
    double getR() { return mR; }
    void setR(double value) { mR = value; }

    /// <summary>
    /// Specifies the percentage of green.
    /// </summary>
    double getG() { return mG; }
    void setG(double value) { mG = value; }

    /// <summary>
    /// Specifies the percentage of blue.
    /// </summary>
    double getB() { return mB; }
    void setB(double value) { mB = value; }

    private double mB;
    private double mG;
    private double mR;
}

