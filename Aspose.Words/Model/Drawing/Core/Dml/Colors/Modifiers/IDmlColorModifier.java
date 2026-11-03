// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;


/// <summary>
/// Public interface of color modifiers.
/// </summary>
interface IDmlColorModifier
{
    /// <summary>
    /// Applies the modifiers to the color.
    /// </summary>
    /// <returns>The modified color.</returns>
    public DrColor modify(DrColor color);

    public IDmlColorModifier deepClone();

    public /*DmlColorModifierType*/int getModifierType();

    public void write(String prefix, IDmlShapeWriterContext writer);
}

