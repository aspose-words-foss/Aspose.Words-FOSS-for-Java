// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.MathUtil;
import com.aspose.ScRgbUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// 20.1.2.3.18 invGamma (Inverse Gamma)
/// This element specifies that the output color rendered by the 
/// generating application should be the inverse sRGB gamma shift of the input color.
/// </summary>
class DmlInverseGamma extends DmlColorModifier
{
    public /*override*/ IDmlColorModifier deepClone()
    {
        return new DmlInverseGamma();
    }

    public /*override*/ DrColor modify(DrColor color)
    {
        int r = inverseGamma(color.getR());
        int g = inverseGamma(color.getG());
        int b = inverseGamma(color.getB());
        return new DrColor(color.getA(), r, g, b);
    }

    private static int inverseGamma(int comp)
    {
        double normComp = comp / 255.0;
        return MathUtil.doubleToInt(ScRgbUtil.linearize(normComp) * 255.0);
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writer.getBuilder().writeEmptyElement(msString.format("{0}:invGamma", prefix));
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.INVERSE_GAMMA; }
}

