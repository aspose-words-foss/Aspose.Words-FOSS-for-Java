// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.ms.System.msString;


/// <summary>
/// 20.1.2.3.17 inv (Inverse)
/// This element specifies the inverse of its input color. 
/// </summary>
class DmlInverse extends DmlColorModifier
{
    public /*override*/ DrColor modify(DrColor color)
    {
        return new DrColor(color.getA(), 255 - color.getR(), 255 - color.getG(), 255 - color.getB());
    }

    public /*override*/ IDmlColorModifier deepClone()
    {
        return new DmlInverse();
    }

    public /*override*/ void write(String prefix, IDmlShapeWriterContext writer)
    {
        writer.getBuilder().writeEmptyElement(msString.format("{0}:inv", prefix));
    }

    public /*override*/ /*DmlColorModifierType*/int getModifierType() { return DmlColorModifierType.INVERSE; }
}

