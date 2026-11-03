// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.HSLColor;


/// <summary>
/// 20.1.2.3.27 satMod (Saturation Modulation)
/// This element specifies the input color with its saturation 
/// modulated by the given percentage. A 50% saturation modulate 
/// reduces the saturation by half. A 200% saturation modulate 
/// doubles the saturation.
/// </summary>
class DmlSaturationModulation extends DmlPercentageBasedColorModifier
{
    public /*override*/ DrColor modify(DrColor color)
    {
        HSLColor hsl = new HSLColor(color);

        // WORDSNET-20638 MS Word does not limit the product of the saturation modulation value and the saturation.
        double satWithSatMod = hsl.getSat() * getValue();

        return new DrColor(color.getA(), HSLColor.toDrColor(hsl.getHue(), satWithSatMod, hsl.getLum()));
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.SATURATION_MODULATION; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlSaturationModulation();
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("satMod", prefix, getValue(), writer);
    }
}

