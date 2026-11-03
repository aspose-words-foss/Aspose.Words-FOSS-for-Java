// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.HSLColor;


/// <summary>
/// 20.1.2.3.20 lumMod (Luminance Modulation)
/// This element specifies the input color with its luminance modulated 
/// by the given percentage. A 50% luminance modulate reduces the luminance 
/// by half. A 200% luminance modulate doubles the luminance.
/// </summary>
class DmlLuminanceModulation extends DmlPercentageBasedColorModifier
{
    /// <summary>
    /// Default ctor.
    /// </summary>
    DmlLuminanceModulation()
    {
    }

    /// <summary>
    /// Ctor with specifying a luminance modulation.
    /// </summary>
    DmlLuminanceModulation(double value)
    {
        setValue(value);
    }

    public /*override*/ DrColor modify(DrColor color)
    {
        HSLColor hsl = new HSLColor(color);
        hsl.setLum(hsl.getLum() * getValue());
        return new DrColor(color.getA(), hsl.toDrColor());
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.LUMINANCE_MODULATION; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlLuminanceModulation();
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("lumMod", prefix, getValue(), writer);
    }
}

