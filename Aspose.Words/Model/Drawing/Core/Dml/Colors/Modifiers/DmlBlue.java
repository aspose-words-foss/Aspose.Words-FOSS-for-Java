// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.2.3.4 blue (Blue)
/// This element specifies the input color with the specific blue 
/// component, but with the red and green color components unchanged.
/// </summary>
class DmlBlue extends DmlScRgbColorModifier
{
    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.BLUE; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlBlue();
    }

    protected /*override*/ double modifyComponent(double normComp, int compIndex)
    {
        // Change only the blue component.
        return (compIndex == 2) ? getValue() : normComp;
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("blue", prefix, getValue(), writer);
    }
}

