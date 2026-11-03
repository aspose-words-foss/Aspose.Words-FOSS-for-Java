// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.HSLColor;


/// <summary>
/// 20.1.2.3.19 lum (Luminance)
/// This element specifies the input color with the specified luminance, 
/// but with its hue and saturation unchanged. Typically luminance values 
/// fall in the range [0%, 100%].
/// </summary>
class DmlLuminance extends DmlPercentageBasedColorModifier
{
    public /*override*/ DrColor modify(DrColor color)
    {
        HSLColor hsl = new HSLColor(color);
        hsl.setLum(getValue());
        return new DrColor(color.getA(), hsl.toDrColor());
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.LUMINANCE; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlLuminance();
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("lum", prefix, getValue(), writer);
    }
}

