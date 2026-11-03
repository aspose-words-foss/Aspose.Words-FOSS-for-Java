// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;

/// <summary>
/// 20.1.8.1 alphaBiLevel (Alpha Bi-Level Effect)
/// This element represents an Alpha Bi-Level Effect.
/// Alpha (Opacity) values less than the threshold are changed 
/// to 0 (fully transparent) and alpha values greater than or equal 
/// to the threshold are changed to 100% (fully opaque).
/// </summary>
class DmlAlphaBiLevelEffect extends DmlEffect
{
    /// <summary>
    /// Gets the type of effects.
    /// </summary>
    /*override*/ /*DmlEffectType*/int getType() { return DmlEffectType.ALPHA_BI_LEVEL; }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlAlphaBiLevelEffect value = (DmlAlphaBiLevelEffect)obj;

        return MathUtil.areEqual(value.getThreshold(), getThreshold());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= msNumber.doubleHashCode(getThreshold());
        return hash;
    }

    /// <summary>
    /// Specifies the threshold value for the alpha bi-level effect.
    /// Value is in fraction representation, so 100% equals 1.0.
    /// </summary>
    double getThreshold() { return mThreshold; }
    void setThreshold(double value) { mThreshold = value; }

    private double mThreshold;
}
