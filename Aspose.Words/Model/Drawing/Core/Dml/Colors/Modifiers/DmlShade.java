// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;


/// <summary>
/// 20.1.2.3.31 shade (Shade)
/// This element specifies a darker version of its input color. 
/// A 10% shade is 10% of the input color combined with 90% black.
/// Value is in fraction representation.
/// </summary>
class DmlShade extends DmlScRgbColorModifier
{
    DmlShade()
    {
    }

    DmlShade(double value)
    {
    	super(value, false);
	
    }

    DmlShade(double value, boolean gammaShift)
    {
    	super(value, gammaShift);
	
    }

    protected /*override*/ double modifyComponent(double normComp, int compIndex)
    {
        return MathUtil.fitToRange(normComp * getValue(), 0.0, 1.0);
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.SHADE; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlShade();
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("shade", prefix, getValue(), writer);
    }
}

