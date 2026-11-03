// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.2.3.23 red (Red)
/// This element specifies the input color with the specified red component, 
/// but with its green and blue color components unchanged.
/// </summary>
class DmlRed extends DmlScRgbColorModifier
{
    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.RED; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlRed();
    }

    protected /*override*/ double modifyComponent(double normComp, int compIndex)
    {
        // Change only the red component.
        return (compIndex == 0) ? getValue() : normComp;
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("red", prefix, getValue(), writer);
    }
}

