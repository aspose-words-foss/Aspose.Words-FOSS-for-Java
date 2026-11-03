// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/24/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;


/// <summary>
/// 20.1.8.29 fillOverlay (Fill Overlay Effect)
/// This element specifies a fill overlay effect. A fill overlay can be used to specify an additional fill for an object and
/// blend the two fills together.
/// 
/// Class is wrapper for <see cref="DmlFillOverlayEffect"/>. This effect can be applied to blip as well to whole shape.
/// </summary>
class DmlShapeFillOverlayEffect extends DmlShapeEffect implements Cloneable
{
    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlShapeFillOverlayEffect value = (DmlShapeFillOverlayEffect)obj;

        return msObject.equals(value.getFillOverlay(), getFillOverlay());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= getFillOverlay().hashCode();
        return hash;
    }

    /*override*/ DmlShapeEffect deepClone()
    {
        DmlShapeFillOverlayEffect lhs = (DmlShapeFillOverlayEffect)memberwiseClone();

        lhs.mFillOverlay = (DmlFillOverlayEffect)mFillOverlay.deepClone();

        return lhs;
    }

    /*override*/ /*DmlShapeEffectType*/int getEffectType() { return DmlShapeEffectType.FILL_OVERLAY; }

    /// <summary>
    /// Specifies how to blend the fill with the base effect.
    /// </summary>
    /*DmlEffectBlendMode*/int getBlend() { return mFillOverlay.getBlend(); }
    void setBlend(/*DmlEffectBlendMode*/int value) { mFillOverlay.setBlend(value); }

    DmlFill getFill() { return mFillOverlay.getFill(); }
    void setFill(DmlFill value) { mFillOverlay.setFill(value); }

    DmlFillOverlayEffect getFillOverlay() { return mFillOverlay; }

    private DmlFillOverlayEffect mFillOverlay = new DmlFillOverlayEffect();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

