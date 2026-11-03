// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;

/// <summary>
/// Base class for blip fill effects.
/// </summary>
abstract class DmlEffect implements Cloneable
{
    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlEffect value = (DmlEffect)obj;

        return (value.getType() == getType());
    }

    public /*override*/ int hashCode()
    {
        return /*enum.hashCode()=(int)value*/getType();
    }

    /// <summary>
    /// Gets the type of effects.
    /// </summary>
    abstract /*DmlEffectType*/int getType();

    /*virtual*/ DmlEffect deepClone()
    {
        return (DmlEffect)memberwiseClone();
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

