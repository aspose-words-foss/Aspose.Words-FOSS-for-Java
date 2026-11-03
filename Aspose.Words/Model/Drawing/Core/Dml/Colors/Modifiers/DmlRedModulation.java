// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.2.3.24 redMod (Red Modulation)
/// This element specifies the input color with its red component 
/// modulated by the given percentage. A 50% red modulate reduces the 
/// red component by half. A 200% red modulate doubles the red component.
/// </summary>
class DmlRedModulation extends DmlScRgbColorModifier
{
    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.RED_MODULATION; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlRedModulation();
    }

    protected /*override*/ double modifyComponent(double normComp, int compIndex)
    {
        // Change only the red component.
        return (compIndex == 0) ? normComp * getValue() : normComp;
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("redMod", prefix, getValue(), writer);
    }
}

