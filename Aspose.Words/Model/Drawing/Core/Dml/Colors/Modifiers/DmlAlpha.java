// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.MathUtil;


/// <summary>
/// 20.1.2.3.1 alpha (Alpha)
/// This element specifies its input color with 
/// the specific opacity, but with its color unchanged.
/// </summary>
class DmlAlpha extends DmlPercentageBasedColorModifier implements IDmlColorIvertableModifier
{
    /// <summary>
    /// Apply the modifier to the color.
    /// </summary>
    /// <returns>The modified color.</returns>
    public /*override*/ DrColor modify(DrColor color)
    {
        return new DrColor(MathUtil.doubleToInt(255.0*getValue()), color);
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.ALPHA; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlAlpha();
    }

    /// <summary>
    /// Inverts Alpha value.
    /// </summary>
    public void /*IDmlColorIvertableModifier.*/invert()
    {
        setValue(1.0 - getValue());
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        double alphaVal = DmlPercentageUtil.toDmlPercentPrecision(getValue());
        writeCore("alpha", prefix, alphaVal, writer);
    }
}

