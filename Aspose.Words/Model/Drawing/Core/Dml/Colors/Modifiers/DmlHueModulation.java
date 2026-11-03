// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.HSLColor;


/// <summary>
/// 20.1.2.3.15 hueMod (Hue Modulate)
/// This element specifies the input color with its hue modulated 
/// by the given percentage. A 50% hue modulate decreases the angular 
/// hue value by half. A 200% hue modulate doubles the angular hue value.
/// </summary>
class DmlHueModulation extends DmlPercentageBasedColorModifier
{
    public /*override*/ DrColor modify(DrColor color)
    {
        HSLColor hsl = new HSLColor(color);
        hsl.setHue(hsl.getHue() * getValue());
        return new DrColor(color.getA(), hsl.toDrColor());
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.HUE_MODULATION; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlHueModulation();
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("hueMod", prefix, getValue(), writer);
    }
}

