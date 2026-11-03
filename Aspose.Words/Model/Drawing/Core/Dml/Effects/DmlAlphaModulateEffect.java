// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ListUtil;
import java.util.ArrayList;


/// <summary>
/// 20.1.8.5 alphaMod (Alpha Modulate Effect)
/// This element represents an alpha modulate effect.
/// Effect alpha (opacity) values are multiplied by a fixed percentage. 
/// The effect container specifies an effect containing alpha values to modulate.
/// </summary>
class DmlAlphaModulateEffect extends DmlEffect
{
    /// <summary>
    /// Gets the type of effects.
    /// </summary>
    /*override*/ /*DmlEffectType*/int getType() { return DmlEffectType.ALPHA_MODULATE; }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlAlphaModulateEffect value = (DmlAlphaModulateEffect)obj;

        return ListUtil.checkAreEquals(value.getEffectsContainer(), getEffectsContainer());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        for (DmlEffect effect : getEffectsContainer())
            hash ^= effect.hashCode();
        return hash;
    }

    ArrayList<DmlEffect> getEffectsContainer() { return mEffectsContainer; }

    private /*final*/ ArrayList<DmlEffect> mEffectsContainer = new ArrayList<DmlEffect>();
}
