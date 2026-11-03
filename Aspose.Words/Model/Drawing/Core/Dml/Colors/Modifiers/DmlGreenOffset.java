// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.2.3.12 greenOff (Green Offset)
/// This element specifies the input color with its green component 
/// shifted, but with its red and blue color components unchanged.
/// </summary>
class DmlGreenOffset extends DmlScRgbColorModifier
{
    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.GREEN_OFFSET; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlGreenOffset();
    }

    protected /*override*/ double modifyComponent(double normComp, int compIndex)
    {
        // Change only the green component.
        return (compIndex == 1) ? normComp + getValue() : normComp;
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("greenOff", prefix, getValue(), writer);
    }
}

