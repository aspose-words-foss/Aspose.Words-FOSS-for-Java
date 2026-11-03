// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/24/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msNumber;


class DmlShapePresetShadowEffect extends DmlShapeEffect implements Cloneable
{
    DmlShapePresetShadowEffect()
    {
        mColor.setAlpha(new DmlAlpha());
        mColor.getAlpha().setValue(0.5);
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlShapePresetShadowEffect value = (DmlShapePresetShadowEffect)obj;

        return (value.getPresetShadow() == getPresetShadow()) &&
               MathUtil.areEqual(value.getDistance(), getDistance()) &&
               msObject.equals(value.getColor(), getColor()) &&
               msObject.equals(value.getDirection(), getDirection());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        if (getColor() != null)
            hash ^= getColor().hashCode();
        hash ^= msNumber.doubleHashCode(getDirection().getValue());
        hash ^= msNumber.doubleHashCode(getDistance());
        hash ^= /*enum.hashCode()=(int)value*/getPresetShadow();
        return hash;
    }

    /// <summary>
    /// Returns true if effect is one of the shadow effects.
    /// </summary>
    /*override*/ boolean isShadowEffect() { return true; }

    /*override*/ DmlShapeEffect deepClone()
    {
        DmlShapePresetShadowEffect lhs = (DmlShapePresetShadowEffect)memberwiseClone();

        if (mColor != null)
            lhs.mColor = mColor.deepClone();

        // The DmlAngle field is skipped since the class is immutable.

        return lhs;
    }

    /*override*/ /*DmlShapeEffectType*/int getEffectType() { return DmlShapeEffectType.PRESET_SHADOW; }

    /// <summary>
    /// Specifies the color of the shadow.
    /// </summary>
    /*override*/ DmlColor getColor() { return mColor; }
    /*override*/ void setColor(DmlColor value) { mColor = value; }

    /// <summary>
    /// Specifies the direction to offset the shadow.
    /// </summary>
    DmlAngle getDirection() { return mDirection; }
    void setDirection(DmlAngle value) { mDirection = value; }

    /// <summary>
    /// Specifies how far to offset the shadow.
    /// </summary>
    double getDistance() { return mDistance; }
    void setDistance(double value) { mDistance = value; }

    /// <summary>
    /// Specifies which preset shadow to use.
    /// </summary>
    /*DmlPresetShadow*/int getPresetShadow() { return mPresetShadow; }
    void setPresetShadow(/*DmlPresetShadow*/int value) { mPresetShadow = value; }

    /// <summary>
    /// Gets or sets <see cref="Aspose.Words.Drawing.ShadowType"/> corresponding to this preset shadow effect.
    /// </summary>
    /*override*/ /*ShadowType*/int getShadowType()
    {
        switch (mPresetShadow)
        {
            case DmlPresetShadow.TOP_LEFT_DROP_SHADOW:
                return ShadowType.SHADOW_1;
            case DmlPresetShadow.TOP_LEFT_LARGE_DROP_SHADOW:
                return ShadowType.SHADOW_10;
            case DmlPresetShadow.BACK_LEFT_LONG_PERSPECTIVE_SHADOW:
                return ShadowType.SHADOW_11;
            case DmlPresetShadow.BACK_RIGHT_LONG_PERSPECTIVE_SHADOW:
                return ShadowType.SHADOW_12;
            case DmlPresetShadow.TOP_LEFT_DOUBLE_DROP_SHADOW:
                return ShadowType.SHADOW_13;
            case DmlPresetShadow.BOTTOM_RIGHT_SMALL_DROP_SHADOW:
                return ShadowType.SHADOW_14;
            case DmlPresetShadow.FRONT_LEFT_LONG_PERSPECTIVE_SHADOW:
                return ShadowType.SHADOW_15;
            case DmlPresetShadow.FRONT_RIGHT_LONG_PERSPECTIVE_SHADOW:
                return ShadowType.SHADOW_16;
            case DmlPresetShadow.OUTER_BOX_SHADOW_3_D:
                return ShadowType.SHADOW_17;
            case DmlPresetShadow.INNER_BOX_SHADOW_3_D:
                return ShadowType.SHADOW_18;
            case DmlPresetShadow.BACK_CENTER_PERSPECTIVE_SHADOW:
                return ShadowType.SHADOW_19;
            case DmlPresetShadow.TOP_RIGHT_DROP_SHADOW:
                return ShadowType.SHADOW_2;
            case DmlPresetShadow.FRONT_BOTTOM_SHADOW:
                return ShadowType.SHADOW_20;
            case DmlPresetShadow.BACK_LEFT_PERSPECTIVE_SHADOW:
                return ShadowType.SHADOW_3;
            case DmlPresetShadow.BACK_RIGHT_PERSPECTIVE_SHADOW:
                return ShadowType.SHADOW_4;
            case DmlPresetShadow.BOTTOM_LEFT_DROP_SHADOW:
                return ShadowType.SHADOW_5;
            case DmlPresetShadow.BOTTOM_RIGHT_DROP_SHADOW:
                return ShadowType.SHADOW_6;
            case DmlPresetShadow.FRONT_LEFT_PERSPECTIVE_SHADOW:
                return ShadowType.SHADOW_7;
            case DmlPresetShadow.FRONT_RIGHT_PERSPECTIVE_SHADOW:
                return ShadowType.SHADOW_8;
            case DmlPresetShadow.TOP_LEFT_SMALL_DROP_SHADOW:
                return ShadowType.SHADOW_9;
            default:
                return ShadowType.SHADOW_MIXED;
        }
    }
    /*override*/ void setShadowType(/*ShadowType*/int value)
    {
        trySetShadowType(value);
    }

    /// <summary>
    /// Returns true, if specified shadow type has been set successfully.
    /// </summary>
    /*override*/ boolean trySetShadowType(/*ShadowType*/int shadowType)
    {

        switch (shadowType)
        {
            case ShadowType.SHADOW_1:
                setPresetShadow(DmlPresetShadow.TOP_LEFT_DROP_SHADOW);
                setDirection(new DmlAngle(13500000.0));
                setDistance(107763.0);
                break;

            case ShadowType.SHADOW_2:
                setPresetShadow(DmlPresetShadow.TOP_RIGHT_DROP_SHADOW);
                setDirection(new DmlAngle(18900000.0));
                setDistance(107763.0);
                break;

            case ShadowType.SHADOW_3:
                setPresetShadow(DmlPresetShadow.BACK_LEFT_PERSPECTIVE_SHADOW);
                break;

            case ShadowType.SHADOW_4:
                setPresetShadow(DmlPresetShadow.BACK_RIGHT_PERSPECTIVE_SHADOW);
                break;

            case ShadowType.SHADOW_5:
                setPresetShadow(DmlPresetShadow.BOTTOM_LEFT_DROP_SHADOW);
                setDirection(new DmlAngle(8100000.0));
                setDistance(107763.0);
                break;

            case ShadowType.SHADOW_6:
                setPresetShadow(DmlPresetShadow.BOTTOM_RIGHT_DROP_SHADOW);
                setDirection(new DmlAngle(2700000.0));
                setDistance(107763.0);
                break;

            case ShadowType.SHADOW_7:
                setPresetShadow(DmlPresetShadow.FRONT_LEFT_PERSPECTIVE_SHADOW);
                break;

            case ShadowType.SHADOW_8:
                setPresetShadow(DmlPresetShadow.FRONT_RIGHT_PERSPECTIVE_SHADOW);
                break;

            case ShadowType.SHADOW_9:
                setPresetShadow(DmlPresetShadow.TOP_LEFT_SMALL_DROP_SHADOW);
                setDirection(new DmlAngle(13500000.0));
                setDistance(107763.0);
                break;

            case ShadowType.SHADOW_10:
                setPresetShadow(DmlPresetShadow.TOP_LEFT_LARGE_DROP_SHADOW);
                setDirection(new DmlAngle(13500000.0));
                setDistance(107763.0);
                break;

            case ShadowType.SHADOW_11:
                setPresetShadow(DmlPresetShadow.BACK_LEFT_LONG_PERSPECTIVE_SHADOW);
                break;

            case ShadowType.SHADOW_12:
                setPresetShadow(DmlPresetShadow.BACK_RIGHT_LONG_PERSPECTIVE_SHADOW);
                break;

            case ShadowType.SHADOW_13:
                setPresetShadow(DmlPresetShadow.TOP_LEFT_DOUBLE_DROP_SHADOW);
                setDirection(new DmlAngle(13500000.0));
                setDistance(53882.0);
                break;

            case ShadowType.SHADOW_14:
                setPresetShadow(DmlPresetShadow.BOTTOM_RIGHT_SMALL_DROP_SHADOW);
                setDirection(new DmlAngle(2700000.0));
                setDistance(35921.0);
                break;

            case ShadowType.SHADOW_15:
                setPresetShadow(DmlPresetShadow.FRONT_LEFT_LONG_PERSPECTIVE_SHADOW);
                break;

            case ShadowType.SHADOW_16:
                setPresetShadow(DmlPresetShadow.FRONT_RIGHT_LONG_PERSPECTIVE_SHADOW);
                break;

            case ShadowType.SHADOW_17:
                setPresetShadow(DmlPresetShadow.OUTER_BOX_SHADOW_3_D);
                setDirection(new DmlAngle(2700000.0));
                setDistance(17961.0);
                break;

            case ShadowType.SHADOW_18:
                setPresetShadow(DmlPresetShadow.INNER_BOX_SHADOW_3_D);
                setDirection(new DmlAngle(13500000.0));
                setDistance(17961.0);
                break;

            case ShadowType.SHADOW_19:
                setPresetShadow(DmlPresetShadow.BACK_CENTER_PERSPECTIVE_SHADOW);
                break;

            case ShadowType.SHADOW_20:
                setPresetShadow(DmlPresetShadow.FRONT_BOTTOM_SHADOW);
                break;

            default:
                return false;
        }

        return true;
    }

    private DmlColor mColor = new DmlPercentageRgbColor();
    private double mDistance;
    private /*DmlPresetShadow*/int mPresetShadow;

    // WORDSNET-13508 This property is optional and has default value according to spec,
    // so it is have to be initialized. Otherwise DmlShapeEffectsWriter can failed with NullReferenceException.
    private DmlAngle mDirection = new DmlAngle();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

