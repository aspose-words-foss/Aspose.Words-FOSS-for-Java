// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;

/// <summary>
/// This element holds the properties associated with defining a bevel.
/// </summary>
class DmlBevel implements Cloneable
{
    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlBevel value = (DmlBevel)obj;

        return (value.getBevelPresetType() == getBevelPresetType()) &&
               MathUtil.areEqual(value.getHeight(), getHeight()) &&
               MathUtil.areEqual(value.getWidth(), getWidth());
    }

    public /*override*/ int hashCode()
    {
        int hash = 0;
        hash ^= msNumber.doubleHashCode(getHeight());
        hash ^= msNumber.doubleHashCode(getWidth());
        hash ^= /*enum.hashCode()=(int)value*/getBevelPresetType();
        return hash;
    }

    DmlBevel deepClone()
    {
        return (DmlBevel)memberwiseClone();
    }

    /// <summary>
    /// Specifies the height of the bevel, or how far above the shape it is applied.
    /// </summary>
    double getHeight() { return mHeight; }
    void setHeight(double value) { mHeight = value; }

    /// <summary>
    /// Specifies the width of the bevel, or how far into the shape it is applied.
    /// </summary>
    double getWidth() { return mWidth; }
    void setWidth(double value) { mWidth = value; }

    /// <summary>
    /// Specifies the preset bevel type which defines the look of the bevel.
    /// Default <see cref="DmlBevelPresetType.Circle"/>.
    /// </summary>
    /*DmlBevelPresetType*/int getBevelPresetType() { return mBevelPresetType; }
    void setBevelPresetType(/*DmlBevelPresetType*/int value) { mBevelPresetType = value; }


    private double mHeight;
    private double mWidth;
    private /*DmlBevelPresetType*/int mBevelPresetType = DmlBevelPresetType.CIRCLE;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

