// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.2.3.10 green (Green)
/// This elements specifies the input color with the specified 
/// green component, but with its red and blue color components unchanged.
/// </summary>
class DmlGreen extends DmlScRgbColorModifier
{
    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.GREEN; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlGreen();
    }

    protected /*override*/ double modifyComponent(double normComp, int compIndex)
    {
        // Change only the green component.
        return (compIndex == 1) ? getValue() : normComp;
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("green", prefix, getValue(), writer);
    }
}

