// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/24/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;


/// <summary>
/// 20.1.8.32 glow (Glow Effect)
/// This element specifies a glow effect, in which a color blurred outline is added outside the edges of the object.
/// </summary>
class DmlShapeGlowEffect extends DmlShapeEffect implements Cloneable
{
    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlShapeGlowEffect value = (DmlShapeGlowEffect)obj;

        return msObject.equals(value.getColor(), getColor()) && MathUtil.areEqual(value.getRadius(), getRadius());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        if (getColor() != null)
            hash ^= getColor().hashCode();
        hash ^= msNumber.doubleHashCode(getRadius());
        return hash;
    }

    /*override*/ DmlShapeEffect deepClone()
    {
        DmlShapeGlowEffect lhs = (DmlShapeGlowEffect)memberwiseClone();

        if (mColor != null)
            lhs.mColor = mColor.deepClone();

        return lhs;
    }

    /*override*/ /*DmlShapeEffectType*/int getEffectType() { return DmlShapeEffectType.GLOW; }

    /*override*/ boolean isEmpty() { return MathUtil.isZero(mRaduis); }

    /// <summary>
    /// Specifies the color of the glow.
    /// </summary>
    /*override*/ DmlColor getColor() { return mColor; }
    /*override*/ void setColor(DmlColor value) { mColor = value; }

    /// <summary>
    /// Specifies the radius of the glow.
    /// </summary>
    double getRadius() { return mRaduis; }
    void setRadius(double value) { mRaduis = value; }

    private DmlColor mColor;
    private double mRaduis;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

