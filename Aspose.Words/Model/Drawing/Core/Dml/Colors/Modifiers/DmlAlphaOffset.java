// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.MathUtil;


/// <summary>
/// 20.1.2.3.3 alphaOff (Alpha Offset)
/// This element specifies a more or less opaque version of its input 
/// color. Increases or decreases the input alpha percentage by the 
/// specified percentage offset. A 10% alpha offset increases a 50% 
/// opacity to 60%. A -10% alpha offset decreases a 50% opacity to 40%. 
/// The transformed alpha values are limited to a range of 0 to 100%. 
/// A 10% alpha offset increase to a 100% opaque object still results 
/// in 100% opacity.
/// </summary>
class DmlAlphaOffset extends DmlPercentageBasedColorModifier
{
    public /*override*/ DrColor modify(DrColor color)
    {
        return new DrColor(color.getA() + MathUtil.doubleToInt(255.0*getValue()), color);
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.ALPHA_OFFSET; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlAlphaOffset();
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("alphaOff", prefix, getValue(), writer);
    }
}

