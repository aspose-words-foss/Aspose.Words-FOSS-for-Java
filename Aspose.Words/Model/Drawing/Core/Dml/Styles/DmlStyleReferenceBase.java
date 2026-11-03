// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents the base class for style references.
/// </summary>
abstract class DmlStyleReferenceBase implements Cloneable
{
    /// <summary>
    /// Color.
    /// </summary>
    DmlColor getColor() { return mColor; }
    void setColor(DmlColor value) { mColor = value; }

    /// <summary>
    /// Clones this style reference instance.
    /// </summary>
    /*virtual*/ DmlStyleReferenceBase deepClone()
    {
        DmlStyleReferenceBase lhs = (DmlStyleReferenceBase)memberwiseClone();

        if (mColor != null)
            lhs.mColor = mColor.deepClone();

        return lhs;
    }

    /// <summary>
    /// Returns true, when this style reference is empty.
    /// </summary>
    boolean isEmpty() { return (mColor == null); }
    
    private DmlColor mColor;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

