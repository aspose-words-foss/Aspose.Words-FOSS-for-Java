// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.ms.System.msString;


/// <summary>
/// 20.1.2.3.9 gray (Gray)
/// This element specifies a grayscale of its input color, 
/// taking into relative intensities of the red, green, and blue primaries.
/// </summary>
class DmlGray extends DmlColorModifier
{
    public /*override*/ DrColor modify(DrColor color)
    {
        // Rec. ITU-R BT.601-4 http://inst.eecs.berkeley.edu/~cs150/Documents/ITU601.PDF
        // 2.1 Construction of luminance (EY¢ ) and colour-difference (ER¢ – EY¢ ) and (EB¢ – EY¢ ) signals 
        // The construction of luminance and colour-difference signals is as follows: 
        // EY ¢ = 0.299 ER ¢ + 0.587 EG ¢ + 0.114 EB ¢ 
        // whence: 
        // (ER ¢ – EY ¢ ) = ER ¢ – 0.299 ER ¢ – 0.587 EG ¢ – 0.114 EB ¢ 
        // = 0.701 ER ¢ – 0.587 EG ¢ – 0.114 EB ¢ 
        // and: 
        // (EB ¢ – EY ¢ ) = EB ¢ – 0.299 ER ¢ – 0.587 EG ¢ – 0.114 EB ¢ 
        // = – 0.299 ER ¢ – 0.587 EG ¢ + 0.886 E 
        double ey = 0.299*color.getR() + 0.587*color.getG() + 0.114*color.getB();
        return new DrColor(color.getA(), (int)ey, (int)ey, (int)ey);
    }

    public /*override*/ IDmlColorModifier deepClone()
    {
        return new DmlGray();
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writer.getBuilder().writeEmptyElement(msString.format("{0}:gray", prefix));
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.GRAY; }
}

