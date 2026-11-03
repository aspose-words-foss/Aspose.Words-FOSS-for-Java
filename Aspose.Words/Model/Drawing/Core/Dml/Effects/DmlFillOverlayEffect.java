// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;


/// <summary>
/// 20.1.8.29 fillOverlay (Fill Overlay Effect)
/// This element specifies a fill overlay effect. 
/// A fill overlay can be used to specify an additional 
/// fill for an object and blend the two fills together.
/// </summary>
class DmlFillOverlayEffect extends DmlEffect implements Cloneable
{
    /// <summary>
    /// Gets the type of effects.
    /// </summary>
    /*override*/ /*DmlEffectType*/int getType() { return DmlEffectType.FILL_OVERLAY; }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlFillOverlayEffect value = (DmlFillOverlayEffect)obj;

        return msObject.equals(value.getFill(), getFill()) && (value.getBlend() == getBlend());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= getFill().hashCode();
        hash ^= /*enum.hashCode()=(int)value*/getBlend();
        return hash;
    }

    /*override*/ DmlEffect deepClone()
    {
        DmlFillOverlayEffect lhs = (DmlFillOverlayEffect)memberwiseClone();

        if (mFill != null)
            lhs.mFill = mFill.deepClone();
        
        return lhs;
    }

    DmlFill getFill() { return mFill; }
    void setFill(DmlFill value) { mFill = value; }

    /*DmlEffectBlendMode*/int getBlend() { return mBlend; }
    void setBlend(/*DmlEffectBlendMode*/int value) { mBlend = value; }

    private DmlFill mFill;
    private /*DmlEffectBlendMode*/int mBlend;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

