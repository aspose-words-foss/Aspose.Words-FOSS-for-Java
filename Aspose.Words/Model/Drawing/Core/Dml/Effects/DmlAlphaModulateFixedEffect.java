// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;

/// <summary>
/// 20.1.8.6 alphaModFix (Alpha Modulate Fixed Effect)
/// This element represents an alpha modulate fixed effect.
/// Effect alpha (opacity) values are multiplied by a fixed percentage.
/// </summary>
class DmlAlphaModulateFixedEffect extends DmlEffect
{
    /// <summary>
    /// Gets the type of effects.
    /// </summary>
    /*override*/ /*DmlEffectType*/int getType() { return DmlEffectType.ALPHA_MODULATE_FIXED; }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlAlphaModulateFixedEffect value = (DmlAlphaModulateFixedEffect)obj;

        return MathUtil.areEqual(value.getAmount(), getAmount());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= msNumber.doubleHashCode(getAmount());
        return hash;
    }

    /// <summary>
    /// Specifies the percentage amount to scale the alpha.
    /// Value is in fraction representation, so 100% equals 1.0.
    /// </summary>
    double getAmount() { return mAmount; }
    void setAmount(double value) { mAmount = value; }

    private double mAmount;
}
