// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;

/// <summary>
/// 20.1.8.8 alphaRepl (Alpha Replace Effect)
/// This element specifies an alpha replace effect.
/// Effect alpha (opacity) values are replaced by a fixed alpha.
/// </summary>
class DmlAlphaReplaceEffect extends DmlEffect
{
    /// <summary>
    /// Gets the type of effects.
    /// </summary>
    /*override*/ /*DmlEffectType*/int getType() { return DmlEffectType.ALPHA_REPLACE; }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlAlphaReplaceEffect value = (DmlAlphaReplaceEffect)obj;

        return MathUtil.areEqual(value.getAlpha(), getAlpha());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= msNumber.doubleHashCode(getAlpha());
        return hash;
    }

    /// <summary>
    /// Specifies the new opacity value.
    /// </summary>
    double getAlpha() { return mAlpha; }
    void setAlpha(double value) { mAlpha = value; }

    private double mAlpha;
}
