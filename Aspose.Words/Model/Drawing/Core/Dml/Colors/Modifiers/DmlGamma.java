// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.MathUtil;
import com.aspose.ScRgbUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// 20.1.2.3.8 gamma (Gamma)
/// This element specifies that the output color rendered by 
/// the generating application should be the sRGB gamma shift of the input color.
/// </summary>
class DmlGamma extends DmlColorModifier
{
    public /*override*/ IDmlColorModifier deepClone()
    {
        return new DmlGamma();
    }

    public /*override*/ DrColor modify(DrColor color)
    {
        int r = gamma(color.getR());
        int g = gamma(color.getG());
        int b = gamma(color.getB());
        return new DrColor(color.getA(), r, g, b);
    }

    private static int gamma(int comp)
    {
        double normComp = comp / 255.0;
        return MathUtil.doubleToInt(ScRgbUtil.deLinearize(normComp) * 255.0);
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writer.getBuilder().writeEmptyElement(msString.format("{0}:gamma", prefix));
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.GAMMA; }
}

