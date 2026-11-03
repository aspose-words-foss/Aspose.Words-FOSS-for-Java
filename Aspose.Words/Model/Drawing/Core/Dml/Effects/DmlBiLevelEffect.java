// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/09/2011 by Alexey Kachalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;

/// <summary>
/// 20.1.8.11 biLevel (Bi-Level (Black/White) Effect)
/// This element specifies a bi-level (black/white) effect. 
/// Input colors whose luminance is less than the specified
/// threshold value are changed to black. Input colors whose
/// luminance are greater than or equal the specified value
/// are set to white. The alpha effect values are unaffected
/// by this effect.
/// </summary>
class DmlBiLevelEffect extends DmlEffect
{
    /// <summary>
    /// Gets the type of effects.
    /// </summary>
    /*override*/ /*DmlEffectType*/int getType() { return DmlEffectType.BI_LEVEL; }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlBiLevelEffect value = (DmlBiLevelEffect)obj;

        return MathUtil.areEqual(value.getThreshold(), getThreshold());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= msNumber.doubleHashCode(getThreshold());
        return hash;
    }

    /// <summary>
    /// Specifies the threshold value for the bi-level effect.
    /// </summary>
    double getThreshold() { return mThreshold; }
    void setThreshold(double value) { mThreshold = value; }

    private double mThreshold;
}

