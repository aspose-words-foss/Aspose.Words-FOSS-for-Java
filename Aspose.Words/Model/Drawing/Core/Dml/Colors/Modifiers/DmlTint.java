// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.2.3.34 tint (Tint)
/// This element specifies a lighter version of its input color. 
/// A 10% tint is 10% of the input color combined with 90% white.
/// Value is in fraction representation.
/// </summary>
class DmlTint extends DmlScRgbColorModifier
{
    public DmlTint()
    {
    }

    DmlTint(double value)
    {
    	super(value, false);
	
    }

    DmlTint(double value, boolean gammaShift)
    {
    	super(value, gammaShift);
	
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.TINT; }

    protected /*override*/ double modifyComponent(double normComp, int compIndex)
    {
        return (getValue() >= 0.0)
            ? normComp * getValue() + (1.0 - getValue())
            : normComp * (1.0 + getValue());
    }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlTint();
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("tint", prefix, getValue(), writer);
    }
}

