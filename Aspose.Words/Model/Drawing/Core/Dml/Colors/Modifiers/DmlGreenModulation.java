// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.2.3.11 greenMod (Green Modification)
/// This element specifies the input color with its green 
/// component modulated by the given percentage. A 50% green 
/// modulate reduces the green component by half. A 200% 
/// green modulate doubles the green component.
/// </summary>
class DmlGreenModulation extends DmlScRgbColorModifier
{
    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.GREEN_MODULATION; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlGreenModulation();
    }

    protected /*override*/ double modifyComponent(double normComp, int compIndex)
    {
        // Change only the green component.
        return (compIndex == 1) ? normComp * getValue() : normComp;
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("greenMod", prefix, getValue(), writer);
    }
}

