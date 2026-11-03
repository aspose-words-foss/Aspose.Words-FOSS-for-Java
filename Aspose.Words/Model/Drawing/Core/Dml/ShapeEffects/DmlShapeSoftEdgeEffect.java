// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/24/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;

/// <summary>
/// 20.1.8.53 softEdge (Soft Edge Effect)
/// This element specifies a soft edge effect. The edges of the shape are blurred, while the fill is not affected.
/// </summary>
class DmlShapeSoftEdgeEffect extends DmlShapeEffect implements Cloneable
{
    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlShapeSoftEdgeEffect value = (DmlShapeSoftEdgeEffect)obj;

        return MathUtil.areEqual(value.getRadius(), getRadius());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= msNumber.doubleHashCode(getRadius());
        return hash;
    }

    /*override*/ DmlShapeEffect deepClone()
    {
        return (DmlShapeSoftEdgeEffect)memberwiseClone();
    }

    /*override*/ /*DmlShapeEffectType*/int getEffectType() { return DmlShapeEffectType.SOFT_EDGES; }

    /// <summary>
    /// Specifies the radius of blur to apply to the edges.
    /// </summary>
    double getRadius() { return mRadius; }
    void setRadius(double value) { mRadius = value; }

    private double mRadius;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

