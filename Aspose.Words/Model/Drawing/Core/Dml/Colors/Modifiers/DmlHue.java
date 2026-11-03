// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.DrColor;
import com.aspose.HSLColor;
import com.aspose.ms.System.msNumber;
import com.aspose.ms.System.msString;


/// <summary>
/// 20.1.2.3.14 hue (Hue)
/// This element specifies the input color with the specified hue, 
/// but with its saturation and luminance unchanged.
/// </summary>
class DmlHue extends DmlColorModifier
{
    public /*override*/ DrColor modify(DrColor color)
    {
        HSLColor hsl = new HSLColor(color);
        hsl.setHue(getValue().getValueInDegrees()/360.0);
        return new DrColor(color.getA(), hsl.toDrColor());
    }

    public /*override*/ IDmlColorModifier deepClone()
    {
        DmlHue result = new DmlHue();
        result.setValue(getValue());
        return result;
    }

    public /*override*/ int hashCode()
    {
        int hash = /*enum.hashCode()=(int)value*/getModifierType();
        if (getValue() != null)
            hash ^= msNumber.doubleHashCode(getValue().getValue());
        return hash;
    }

    public /*override*/ boolean equals(Object obj)
    {
        DmlHue hue = ms.as(obj, DmlHue.class);
        return (hue != null) && (((mValue == null) && (hue.getValue() == null)) ||
                                 ((mValue != null) && mValue.equals(hue.getValue()))) &&
               super.equals(obj);
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writer.getBuilder().writeElementWithAttributes(
            msString.format("{0}:hue", prefix),
            DmlNamespaceUtil.getAttrName(prefix, "val"),
            getValue().getValue());
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.HUE; }

    /// <summary>
    /// Specifies the angular value describing the wavelength. Expressed in 1/60000ths of a degree.
    /// </summary>
    public DmlAngle getValue() { return mValue; }
    public void setValue(DmlAngle value) { mValue = value; }

    private DmlAngle mValue;
}

