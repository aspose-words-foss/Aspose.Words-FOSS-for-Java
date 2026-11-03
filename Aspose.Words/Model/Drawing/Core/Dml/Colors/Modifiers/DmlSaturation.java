// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.HSLColor;


/// <summary>
/// 20.1.2.3.26 sat (Saturation)
/// This element specifies the input color with the specified 
/// saturation, but with its hue and luminance unchanged. Typically 
/// saturation values fall in the range [0%, 100%].
/// </summary>
class DmlSaturation extends DmlPercentageBasedColorModifier
{
    public /*override*/ DrColor modify(DrColor color)
    {
        HSLColor hsl = new HSLColor(color);
        hsl.setSat(getValue());
        return new DrColor(color.getA(), hsl.toDrColor());
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.SATURATION; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlSaturation();
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("sat", prefix, getValue(), writer);
    }
}

