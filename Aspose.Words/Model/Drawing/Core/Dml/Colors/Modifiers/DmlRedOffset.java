// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.2.3.25 redOff (Red Offset)
/// This element specifies the input color with its red component 
/// shifted, but with its green and blue color components unchanged.
/// </summary>
class DmlRedOffset extends DmlScRgbColorModifier
{
    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.RED_OFFSET; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlRedOffset();
    }

    protected /*override*/ double modifyComponent(double normComp, int compIndex)
    {
        // Change only the red component.
        return (compIndex == 0) ? normComp + getValue() : normComp;
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("redOff", prefix, getValue(), writer);
    }
}

