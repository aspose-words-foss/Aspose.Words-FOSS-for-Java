// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.HSLColor;


/// <summary>
/// 20.1.2.3.21 lumOff (Luminance Offset)
/// This element specifies the input color with its luminance shifted, 
/// but with its hue and saturation unchanged.
/// </summary>
class DmlLuminanceOffset extends DmlPercentageBasedColorModifier
{
    /// <summary>
    /// Default ctor.
    /// </summary>
    DmlLuminanceOffset()
    {
    }

    /// <summary>
    /// Ctor with specifying a luminance offset.
    /// </summary>
    DmlLuminanceOffset(double value)
    {
        setValue(value);
    }

    public /*override*/ DrColor modify(DrColor color)
    {
        HSLColor hsl = new HSLColor(color);
        hsl.setLum(hsl.getLum() + getValue());
        return new DrColor(color.getA(), hsl.toDrColor());
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.LUMINANCE_OFFSET; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlLuminanceOffset();
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("lumOff", prefix, getValue(), writer);
    }
}

