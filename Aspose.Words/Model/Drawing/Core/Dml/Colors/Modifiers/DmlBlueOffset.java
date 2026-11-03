// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.2.3.6 blueOff (Blue Offset)
/// This element specifies the input color with its blue component 
/// shifted, but with its red and green color components unchanged.
/// </summary>
class DmlBlueOffset extends DmlScRgbColorModifier
{
    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.BLUE_OFFSET; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlBlueOffset();
    }

    protected /*override*/ double modifyComponent(double normComp, int compIndex)
    {
        // Change only the blue component.
        return (compIndex == 2) ? normComp + getValue() : normComp;
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("blueOff", prefix, getValue(), writer);
    }
}

