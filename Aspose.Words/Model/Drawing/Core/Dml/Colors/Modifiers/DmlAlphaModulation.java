// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.MathUtil;


/// <summary>
/// 20.1.2.3.2 alphaMod (Alpha Modulation)
/// This element specifies a more or less opaque version of 
/// its input color. An alpha modulate never increases the alpha 
/// beyond 100%. A 200% alpha modulate makes a input color twice 
/// as opaque as before. A 50% alpha modulate makes a input color 
/// half as opaque as before.
/// </summary>
class DmlAlphaModulation extends DmlPercentageBasedColorModifier
{
    public /*override*/ DrColor modify(DrColor color)
    {
        return new DrColor(MathUtil.doubleToInt(color.getA()*getValue()), color);
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.ALPHA_MODULATION; }

    protected /*override*/ DmlPercentageBasedColorModifier createEmptyObject()
    {
        return new DmlAlphaModulation();
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writeCore("alphaMod", prefix, getValue(), writer);
    }
}

