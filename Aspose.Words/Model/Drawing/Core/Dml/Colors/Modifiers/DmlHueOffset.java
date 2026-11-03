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
/// 20.1.2.3.16 hueOff (Hue Offset)
/// This element specifies the input color with its hue shifted, 
/// but with its saturation and luminance unchanged.
/// </summary>
class DmlHueOffset extends DmlColorModifier
{
    public /*override*/ DrColor modify(DrColor color)
    {
        HSLColor hsl = new HSLColor(color);
        hsl.setHue(hsl.getHue() + (getValue().getValueInDegrees()/360.0));
        return new DrColor(color.getA(), hsl.toDrColor());
    }

    public /*override*/ IDmlColorModifier deepClone()
    {
        DmlHueOffset result = new DmlHueOffset();
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
        DmlHueOffset hueOffset = ms.as(obj, DmlHueOffset.class);
        return (hueOffset != null) && (((mValue == null) && (hueOffset.getValue() == null)) ||
                                       ((mValue != null) && mValue.equals(hueOffset.getValue()))) &&
               super.equals(obj);
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writer.getBuilder().writeElementWithAttributes(
            msString.format("{0}:hueOff", prefix),
            DmlNamespaceUtil.getAttrName(prefix, "val"),
            getValue().getValue());
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.HUE_OFFSET; }

    public DmlAngle getValue() { return mValue; }
    public void setValue(DmlAngle value) { mValue = value; }

    private DmlAngle mValue = new DmlAngle();
}

