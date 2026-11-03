// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.2.3.5 blueMod (Blue Modification)
/// This element specifies the input color with its blue 
/// component modulated by the given percentage. A 50% blue 
/// modulate reduces the blue component by half. A 200% blue 
/// modulate doubles the blue component.
/// </summary>
class DmlBlueModulation extends DmlScRgbColorModifier
{
    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.BLUE_MODULATION; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlBlueModulation();
    }

    protected /*override*/ double modifyComponent(double normComp, int compIndex)
    {
        // Change only the blue component.
        return (compIndex == 2) ? normComp * getValue() : normComp;
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("blueMod", prefix, getValue(), writer);
    }
}

