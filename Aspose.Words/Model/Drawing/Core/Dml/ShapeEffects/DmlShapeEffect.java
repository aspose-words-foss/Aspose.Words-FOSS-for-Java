// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/24/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;


abstract class DmlShapeEffect
{
    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlShapeEffect value = (DmlShapeEffect)obj;

        return (value.getEffectType() == getEffectType());
    }

    public /*override*/ int hashCode()
    {
        return /*enum.hashCode()=(int)value*/getEffectType();
    }

    /// <summary>
    /// Returns type of the effect.
    /// </summary>
    abstract /*DmlShapeEffectType*/int getEffectType();

    /// <summary>
    /// Returns true if effect's properties are empty, in this case effect must not be rendered.
    /// Used for text effects.
    /// </summary>
    /*virtual*/ boolean isEmpty() { return false; }

    /// <summary>
    /// Returns true if effect is applied to text.
    /// </summary>
    boolean isTextEffect() { return mIsTextEffect; }
    void isTextEffect(boolean value) { mIsTextEffect = value; }

    /// <summary>
    /// Creates effect of a specified type.
    /// </summary>
    static DmlShapeEffect createEffect(/*DmlShapeEffectType*/int type)
    {
        DmlShapeEffect effect;
        switch (type)
        {
            case DmlShapeEffectType.FILL_OVERLAY:
                effect = new DmlShapeFillOverlayEffect();
                break;

            case DmlShapeEffectType.INNER_SHADOW:
                effect = new DmlShapeInnerShadowEffect();
                break;

            case DmlShapeEffectType.PRESET_SHADOW:
                effect = new DmlShapePresetShadowEffect();
                break;

            case DmlShapeEffectType.SOFT_EDGES:
                effect = new DmlShapeSoftEdgeEffect();
                break;

            case DmlShapeEffectType.BLUR:
                effect = new DmlShapeBlurEffect();
                break;

            case DmlShapeEffectType.GLOW:
                effect = new DmlShapeGlowEffect();
                break;

            case DmlShapeEffectType.OUTER_SHADOW:
                effect = new DmlShapeOuterShadowEffect();
                break;

            case DmlShapeEffectType.REFLECTION:
                effect = new DmlShapeReflectionEffect();
                break;

            default:
                throw new IllegalArgumentException();
        }

        return effect;
    }

    /// <summary>
    /// Returns true, if specified shadow type has been set successfully.
    /// </summary>
    /*virtual*/ boolean trySetShadowType(/*ShadowType*/int shadowType)
    {
        return false;
    }

    /// <summary>
    /// Gets <see cref="Aspose.Words.Drawing.ShadowType"/> of the effect.
    /// </summary>
    /*virtual*/ /*ShadowType*/int getShadowType() { return ShadowType.SHADOW_MIXED; }
    /*virtual*/ void setShadowType(/*ShadowType*/int value) { /* No default setter. */ }

    /// <summary>
    /// Gets or sets <see cref="DmlColor"/> object for effect.
    /// </summary>
     /*virtual*/ DmlColor getColor() { return mColor; }; /*virtual*/ void setColor(DmlColor value) { mColor = value; };

    private DmlColor mColor;

    /// <summary>
    /// Returns true if effect is one of the shadow effects.
    /// </summary>
    /*virtual*/ boolean isShadowEffect() { return false; }

    abstract DmlShapeEffect deepClone();

    private boolean mIsTextEffect;
}

