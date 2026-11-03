// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/24/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;


/// <summary>
/// 20.1.8.40 innerShdw (Inner Shadow Effect)
/// This element specifies an inner shadow effect. A shadow is applied within the edges of the object according to
/// the parameters given by the attributes.
/// </summary>
class DmlShapeInnerShadowEffect extends DmlShapeEffect implements Cloneable
{
    DmlShapeInnerShadowEffect()
    {
        mColor.setAlpha(new DmlAlpha());
        mColor.getAlpha().setValue(0.5);
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlShapeInnerShadowEffect value = (DmlShapeInnerShadowEffect)obj;

        return msObject.equals(value.getColor(), getColor()) &&
               msObject.equals(value.getDirection(), getDirection()) &&
               MathUtil.areEqual(value.getBlurRadius(), getBlurRadius()) &&
               MathUtil.areEqual(value.getDistance(), getDistance());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        if (getColor() != null)
            hash ^= getColor().hashCode();
        hash ^= msNumber.doubleHashCode(getBlurRadius());
        hash ^= msNumber.doubleHashCode(getDirection().getValue());
        hash ^= msNumber.doubleHashCode(getDistance());
        return hash;
    }

    /// <summary>
    /// Returns true if effect is one of the shadow effects.
    /// </summary>
    /*override*/ boolean isShadowEffect() { return true; }

    /*override*/ DmlShapeEffect deepClone()
    {
        DmlShapeInnerShadowEffect lhs = (DmlShapeInnerShadowEffect)memberwiseClone();

        if (mColor != null)
            lhs.mColor = mColor.deepClone();

        // The DmlAngle field is skipped since the class is immutable.

        return lhs;
    }

    /*override*/ /*DmlShapeEffectType*/int getEffectType() { return DmlShapeEffectType.INNER_SHADOW; }

    /// <summary>
    /// Specifies the color of the shadow.
    /// </summary>
    /*override*/ DmlColor getColor() { return mColor; }
    /*override*/ void setColor(DmlColor value) { mColor = value; }

    /// <summary>
    /// Specifies the blur radius.
    /// </summary>
    double getBlurRadius() { return mBlurRadius; }
    void setBlurRadius(double value) { mBlurRadius = value; }

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
    /// Gets or sets <see cref="Aspose.Words.Drawing.ShadowType"/> corresponding to this inner shadow effect.
    /// </summary>
    /*override*/ /*ShadowType*/int getShadowType()
    {
        if ((((getBlurRadius()) == (63500.0))) &&
            (((getDistance()) == (50800.0))) &&
            (((getDirection().getValue()) == (13500000.0))))
            return ShadowType.SHADOW_30;

        if ((((getBlurRadius()) == (63500.0))) &&
            (((getDistance()) == (50800.0))) &&
            (((getDirection().getValue()) == (16200000.0))))
            return ShadowType.SHADOW_31;

        if ((((getBlurRadius()) == (63500.0))) &&
            (((getDistance()) == (50800.0))) &&
            (((getDirection().getValue()) == (18900000.0))))
            return ShadowType.SHADOW_32;

        if ((((getBlurRadius()) == (63500.0))) &&
            (((getDistance()) == (50800.0))) &&
            (((getDirection().getValue()) == (10800000.0))))
            return ShadowType.SHADOW_33;

        if ((((getBlurRadius()) == (114300.0))) &&
            (((getDistance()) == (0.0))) &&
            (((getDirection().getValue()) == (0.0))))
            return ShadowType.SHADOW_34;

        if ((((getBlurRadius()) == (63500.0))) &&
            (((getDistance()) == (50800.0))) &&
            (((getDirection().getValue()) == (0.0))))
            return ShadowType.SHADOW_35;

        if ((((getBlurRadius()) == (63500.0))) &&
            (((getDistance()) == (50800.0))) &&
            (((getDirection().getValue()) == (8100000.0))))
            return ShadowType.SHADOW_36;

        if ((((getBlurRadius()) == (63500.0))) &&
            (((getDistance()) == (50800.0))) &&
            (((getDirection().getValue()) == (5400000.0))))
            return ShadowType.SHADOW_37;

        if ((((getBlurRadius()) == (63500.0))) &&
            (((getDistance()) == (50800.0))) &&
            (((getDirection().getValue()) == (2700000.0))))
            return ShadowType.SHADOW_38;

        return ShadowType.SHADOW_MIXED;
    }
    /*override*/ void setShadowType(/*ShadowType*/int value)
    {
        trySetShadowType(value);
    }

    /// <summary>
    /// Returns true, if specified shadow type was set successfully.
    /// </summary>
    /*override*/ boolean trySetShadowType(/*ShadowType*/int shadowType)
    {
        switch (shadowType)
        {
            case ShadowType.SHADOW_30:
                setBlurRadius(63500.0);
                setDistance(50800.0);
                setDirection(new DmlAngle(13500000.0));
                break;

            case ShadowType.SHADOW_31:
                setBlurRadius(63500.0);
                setDistance(50800.0);
                setDirection(new DmlAngle(16200000.0));
                break;

            case ShadowType.SHADOW_32:
                setBlurRadius(63500.0);
                setDistance(50800.0);
                setDirection(new DmlAngle(18900000.0));
                break;

            case ShadowType.SHADOW_33:
                setBlurRadius(63500.0);
                setDistance(50800.0);
                setDirection(new DmlAngle(10800000.0));
                break;

            case ShadowType.SHADOW_34:
                setBlurRadius(114300.0);
                getColor().setAlpha(null);
                break;

            case ShadowType.SHADOW_35:
                setBlurRadius(63500.0);
                setDistance(50800.0);
                break;

            case ShadowType.SHADOW_36:
                setBlurRadius(63500.0);
                setDistance(50800.0);
                setDirection(new DmlAngle(8100000.0));
                break;

            case ShadowType.SHADOW_37:
                setBlurRadius(63500.0);
                setDistance(50800.0);
                setDirection(new DmlAngle(5400000.0));
                break;

            case ShadowType.SHADOW_38:
                setBlurRadius(63500.0);
                setDistance(50800.0);
                setDirection(new DmlAngle(2700000.0));
                break;

            default:
                return false;
        }

        return true;
    }

    private DmlColor mColor = new DmlPercentageRgbColor();
    private double mBlurRadius;
    private DmlAngle mDirection = new DmlAngle(0.0);
    private double mDistance;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

