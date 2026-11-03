// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.HSLColor;
import com.aspose.MathUtil;


/// <summary>
/// 20.1.2.3.28 satOff (Saturation Offset)
/// This element specifies the input color with its saturation 
/// shifted, but with its hue and luminance unchanged. A 10% 
/// offset to 20% saturation yields 30% saturation.
/// </summary>
class DmlSaturationOffset extends DmlPercentageBasedColorModifier
{
    public /*override*/ DrColor modify(DrColor color)
    {
        HSLColor hsl = new HSLColor(color);
        DrColor result = new DrColor(color.getA(), HSLColor.toDrColor(hsl.getHue(), hsl.getSat() + getValue(), hsl.getLum()));

        if (!MathUtil.isZero(hsl.getSat()))
            return result;

        // WORDSNET-20719 Strange behavior of MS Word. If saturation is zero MS Word uses the saturation 
        // offset value multiplied by 5 when calculating the blue component. Received by an experimental way.
        final double BLUE_COMPONENT_FACTOR = 5.0;
        DrColor colorZeroSat = HSLColor.toDrColor(hsl.getHue(), (getValue() * BLUE_COMPONENT_FACTOR), hsl.getLum());

        return new DrColor(result.getA(), result.getR(), result.getG(), colorZeroSat.getB());
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.SATURATION_OFFSET; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlSaturationOffset();
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("satOff", prefix, getValue(), writer);
    }
}

