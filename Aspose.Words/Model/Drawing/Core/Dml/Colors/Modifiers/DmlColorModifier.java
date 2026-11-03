// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.ArgumentUtil;


/// <summary>
/// Base class for color modifiers.
/// </summary>
abstract class DmlColorModifier implements IDmlColorModifier
{
    /// <summary>
    /// Apply the modifier to the color.
    /// </summary>
    /// <returns>The modified color.</returns>
    public /*virtual*/ DrColor modify(DrColor color)
    {
        return color; // return the original color.
    }

    public abstract IDmlColorModifier deepClone();

    public abstract void write(String prefix, IDmlShapeWriterContext writer);

    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hash code does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlColorModifier value = (DmlColorModifier)obj;

        return (value.getModifierType() == getModifierType());
    }

    public /*override*/ int hashCode()
    {
        return /*enum.hashCode()=(int)value*/getModifierType();
    }

    public abstract /*DmlColorModifierType*/int getModifierType();
}

