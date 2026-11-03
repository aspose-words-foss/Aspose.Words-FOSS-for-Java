// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/24/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msNumber;


/// <summary>
/// 20.1.8.45 outerShdw (Outer Shadow Effect)
/// This element specifies an Outer Shadow Effect.
/// </summary>
class DmlShapeOuterShadowEffect extends DmlShapeEffect implements Cloneable
{
    DmlShapeOuterShadowEffect()
    {
        mColor.setAlpha(new DmlAlpha());
        mColor.getAlpha().setValue(0.4);
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlShapeOuterShadowEffect value = (DmlShapeOuterShadowEffect)obj;

        return (value.getRotateWithShape() == getRotateWithShape()) &&
            (value.getAlignment() == getAlignment()) &&
            MathUtil.areEqual(value.getBlurRadius(), getBlurRadius()) &&
            MathUtil.areEqual(value.getDistance(), getDistance()) &&
            MathUtil.areEqual(value.getHorizontalScale(), getHorizontalScale()) &&
            MathUtil.areEqual(value.getVerticalScale(), getVerticalScale()) &&
            msObject.equals(value.getColor(), getColor()) &&
            msObject.equals(value.getDirection(), getDirection()) &&
            msObject.equals(value.getHorizontalSkew(), getHorizontalSkew()) &&
            msObject.equals(value.getVerticalSkew(), getVerticalSkew());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        if (getColor() != null)
            hash ^= getColor().hashCode();
        hash ^= msNumber.doubleHashCode(getBlurRadius());
        hash ^= msNumber.doubleHashCode(getDirection().getValue());
        hash ^= msNumber.doubleHashCode(getDistance());
        hash ^= /*enum.hashCode()=(int)value*/getAlignment();
        hash ^= msNumber.doubleHashCode(getHorizontalSkew().getValue());
        hash ^= msNumber.doubleHashCode(getVerticalSkew().getValue());
        hash ^= msNumber.doubleHashCode(getHorizontalScale());
        hash ^= msNumber.doubleHashCode(getVerticalScale());
        hash ^= msNumber.booleanHashCode(getRotateWithShape());

        return hash;
    }

    /// <summary>
    /// Returns true if effect is one of the shadow effects.
    /// </summary>
    /*override*/ boolean isShadowEffect() { return true; }

    /*override*/ DmlShapeEffect deepClone()
    {
        DmlShapeOuterShadowEffect lhs = (DmlShapeOuterShadowEffect)memberwiseClone();

        if (mColor != null)
            lhs.mColor = mColor.deepClone();

        // The DmlAngle fields are skipped since the class is immutable.

        return lhs;
    }

    /*override*/ /*DmlShapeEffectType*/int getEffectType() { return DmlShapeEffectType.OUTER_SHADOW; }

    /*override*/ boolean isEmpty()
    {
        return MathUtil.isZero(mBlurRadius, mDirection.getValueInDegrees(), mDistance, mKx.getValueInDegrees(), mKy.getValueInDegrees(),
            mSx, mSy);
    }

    boolean isZeroSize()
    {
        // If vertical or horizontal scale is zero we get zero width.
        // To avoid problems with this consider shadow with zero scale as empty.
        return MathUtil.isZero(mSx) && MathUtil.isZero(mSy);
    }

    /// <summary>
    /// Specifies the color of the shadow.
    /// </summary>
    /*override*/ DmlColor getColor() { return mColor; }
    /*override*/ void setColor(DmlColor value) { mColor = value; }

    /// <summary>
    /// Specifies shadow alignment; alignment happens first, effectively setting the origin for scale, skew, and offset.
    /// </summary>
    /*DmlRectangleAlignment*/int getAlignment() { return mAlignment; }
    void setAlignment(/*DmlRectangleAlignment*/int value) { mAlignment = value; }

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
    /// Specifies the horizontal skew angle.
    /// </summary>
    DmlAngle getHorizontalSkew() { return mKx; }
    void setHorizontalSkew(DmlAngle value) { mKx = value; }

    /// <summary>
    /// Specifies the vertical skew angle.
    /// </summary>
    DmlAngle getVerticalSkew() { return mKy; }
    void setVerticalSkew(DmlAngle value) { mKy = value; }

    /// <summary>
    /// Specifies the horizontal scaling factor; negative scaling causes a flip.
    /// </summary>
    double getHorizontalScale() { return mSx; }
    void setHorizontalScale(double value) { mSx = value; }

    /// <summary>
    /// Specifies the vertical scaling factor; negative scaling causes a flip.
    /// </summary>
    double getVerticalScale() { return mSy; }
    void setVerticalScale(double value) { mSy = value; }

    /// <summary>
    /// Specifies whether the shadow rotates with the shape if the shape is rotated.
    /// </summary>
    boolean getRotateWithShape() { return mRotateWithShape; }
    void setRotateWithShape(boolean value) { mRotateWithShape = value; }

    /// <summary>
    /// Gets or sets <see cref="Aspose.Words.Drawing.ShadowType"/> corresponding to outer shadow effect.
    /// </summary>
    /*override*/ /*ShadowType*/int getShadowType()
    {
        if ((((getBlurRadius()) == (50800.0))) &&
            (((getDistance()) == (38100.0))) &&
            (((getDirection().getValue()) == (2700000.0))) &&
            (((getVerticalScale()) == (1.0))) &&
            (((getHorizontalScale()) == (1.0))) &&
            (getAlignment() == DmlRectangleAlignment.TOP_LEFT))
            return ShadowType.SHADOW_21;

        if ((((getBlurRadius()) == (50800.0))) &&
            (((getDistance()) == (38100.0))) &&
            (((getDirection().getValue()) == (5400000.0))) &&
            (((getVerticalScale()) == (1.0))) &&
            (((getHorizontalScale()) == (1.0))) &&
            (getAlignment() == DmlRectangleAlignment.TOP))
            return ShadowType.SHADOW_22;

        if ((((getBlurRadius()) == (50800.0))) &&
            (((getDistance()) == (38100.0))) &&
            (((getDirection().getValue()) == (8100000.0))) &&
            (((getVerticalScale()) == (1.0))) &&
            (((getHorizontalScale()) == (1.0))) &&
            (getAlignment() == DmlRectangleAlignment.TOP_RIGHT))
            return ShadowType.SHADOW_23;

        if ((((getBlurRadius()) == (50800.0))) &&
            (((getDistance()) == (38100.0))) &&
            (((getDirection().getValue()) == (0.0))) &&
            (((getVerticalScale()) == (1.0))) &&
            (((getHorizontalScale()) == (1.0))) &&
            (getAlignment() == DmlRectangleAlignment.LEFT))
            return ShadowType.SHADOW_24;

        if ((((getBlurRadius()) == (63500.0))) &&
            (((getDistance()) == (0.0))) &&
            (((getDirection().getValue()) == (0.0))) &&
            (((getVerticalScale()) == (1.02))) &&
            (((getHorizontalScale()) == (1.02))) &&
            (getAlignment() == DmlRectangleAlignment.CENTER))
            return ShadowType.SHADOW_25;

        if ((((getBlurRadius()) == (50800.0))) &&
            (((getDistance()) == (38100.0))) &&
            (((getDirection().getValue()) == (10800000.0))) &&
            (((getVerticalScale()) == (1.0))) &&
            (((getHorizontalScale()) == (1.0))) &&
            (getAlignment() == DmlRectangleAlignment.RIGHT))
            return ShadowType.SHADOW_26;

        if ((((getBlurRadius()) == (50800.0))) &&
            (((getDistance()) == (38100.0))) &&
            (((getDirection().getValue()) == (18900000.0))) &&
            (((getVerticalScale()) == (1.0))) &&
            (((getHorizontalScale()) == (1.0))) &&
            (getAlignment() == DmlRectangleAlignment.BOTTOM_LEFT))
            return ShadowType.SHADOW_27;

        if ((((getBlurRadius()) == (50800.0))) &&
            (((getDistance()) == (38100.0))) &&
            (((getDirection().getValue()) == (16200000.0))) &&
            (((getVerticalScale()) == (1.0))) &&
            (((getHorizontalScale()) == (1.0))) &&
            (getAlignment() == DmlRectangleAlignment.BOTTOM))
            return ShadowType.SHADOW_28;

        if ((((getBlurRadius()) == (50800.0))) &&
            (((getDistance()) == (38100.0))) &&
            (((getDirection().getValue()) == (13500000.0))) &&
            (((getVerticalScale()) == (1.0))) &&
            (((getHorizontalScale()) == (1.0))) &&
            (getAlignment() == DmlRectangleAlignment.BOTTOM_RIGHT))
            return ShadowType.SHADOW_29;

        if ((((getBlurRadius()) == (76200.0))) &&
            (((getDistance()) == (0.0))) &&
            (((getDirection().getValue()) == (13500000.0))) &&
            (((getVerticalScale()) == (0.23))) &&
            (((getHorizontalScale()) == (1.0))) &&
            (getAlignment() == DmlRectangleAlignment.BOTTOM_RIGHT))
            return ShadowType.SHADOW_39;

        if ((((getBlurRadius()) == (76200.0))) &&
            (((getDistance()) == (0.0))) &&
            (((getDirection().getValue()) == (18900000.0))) &&
            (((getVerticalScale()) == (0.23))) &&
            (((getHorizontalScale()) == (1.0))) &&
            (getAlignment() == DmlRectangleAlignment.BOTTOM_LEFT))
            return ShadowType.SHADOW_40;

        if ((((getBlurRadius()) == (152400.0))) &&
            (((getDistance()) == (317500.0))) &&
            (((getDirection().getValue()) == (5400000.0))) &&
            (((getVerticalScale()) == (-0.19))) &&
            (((getHorizontalScale()) == (0.9))) &&
            (getAlignment() == DmlRectangleAlignment.BOTTOM))
            return ShadowType.SHADOW_41;

        if ((((getBlurRadius()) == (76200.0))) &&
            (((getDistance()) == (12700.0))) &&
            (((getDirection().getValue()) == (8100000.0))) &&
            (((getVerticalScale()) == (-0.23))) &&
            (((getHorizontalScale()) == (1.0))) &&
            (getAlignment() == DmlRectangleAlignment.BOTTOM_RIGHT))
            return ShadowType.SHADOW_42;

        if ((((getBlurRadius()) == (76200.0))) &&
            (((getDistance()) == (12700.0))) &&
            (((getDirection().getValue()) == (2700000.0))) &&
            (((getVerticalScale()) == (-0.23))) &&
            (((getHorizontalScale()) == (1.0))) &&
            (getAlignment() == DmlRectangleAlignment.BOTTOM_LEFT))
            return ShadowType.SHADOW_43;

        return ShadowType.SHADOW_MIXED;
    }
    /*override*/ void setShadowType(/*ShadowType*/int value)
    {
        trySetShadowType(value);
    }

    /// <summary>
    /// Returns true, if specified shadow type is set successfully.
    /// </summary>
    /*override*/ boolean trySetShadowType(/*ShadowType*/int shadowType)
    {
        switch (shadowType)
        {
            case ShadowType.SHADOW_21:
                setBlurRadius(50800.0);
                setDistance(38100.0);
                setDirection(new DmlAngle(2700000.0));
                setAlignment(DmlRectangleAlignment.TOP_LEFT);
                break;

            case ShadowType.SHADOW_22:
                setBlurRadius(50800.0);
                setDistance(38100.0);
                setDirection(new DmlAngle(5400000.0));
                setAlignment(DmlRectangleAlignment.TOP);
                break;

            case ShadowType.SHADOW_23:
                setBlurRadius(50800.0);
                setDistance(38100.0);
                setDirection(new DmlAngle(8100000.0));
                setAlignment(DmlRectangleAlignment.TOP_RIGHT);
                break;

            case ShadowType.SHADOW_24:
                setBlurRadius(50800.0);
                setDistance(38100.0);
                setAlignment(DmlRectangleAlignment.LEFT);
                break;

            case ShadowType.SHADOW_25:
                setBlurRadius(63500.0);
                setHorizontalScale(1.02);
                setVerticalScale(1.02);
                setAlignment(DmlRectangleAlignment.CENTER);
                break;

            case ShadowType.SHADOW_26:
                setBlurRadius(50800.0);
                setDistance(38100.0);
                setDirection(new DmlAngle(10800000.0));
                setAlignment(DmlRectangleAlignment.RIGHT);
                break;

            case ShadowType.SHADOW_27:
                setBlurRadius(50800.0);
                setDistance(38100.0);
                setDirection(new DmlAngle(18900000.0));
                setAlignment(DmlRectangleAlignment.BOTTOM_LEFT);
                break;

            case ShadowType.SHADOW_28:
                setBlurRadius(50800.0);
                setDistance(38100.0);
                setDirection(new DmlAngle(16200000.0));
                break;

            case ShadowType.SHADOW_29:
                setBlurRadius(50800.0);
                setDistance(38100.0);
                setDirection(new DmlAngle(13500000.0));
                setAlignment(DmlRectangleAlignment.BOTTOM_RIGHT);
                break;

            case ShadowType.SHADOW_39:
                setBlurRadius(76200.0);
                setDirection(new DmlAngle(13500000.0));
                setVerticalScale(0.23);
                setHorizontalSkew(new DmlAngle(1200000.0));
                setAlignment(DmlRectangleAlignment.BOTTOM_RIGHT);
                getColor().getAlpha().setValue(0.2);
                break;

            case ShadowType.SHADOW_40:
                setBlurRadius(76200.0);
                setDirection(new DmlAngle(18900000.0));
                setVerticalScale(0.23);
                setHorizontalSkew(new DmlAngle(-1200000));
                setAlignment(DmlRectangleAlignment.BOTTOM_LEFT);
                getColor().getAlpha().setValue(0.2);
                break;

            case ShadowType.SHADOW_41:
                setBlurRadius(152400.0);
                setDistance(317500.0);
                setDirection(new DmlAngle(5400000.0));
                setVerticalScale(-0.19);
                setHorizontalScale(0.9);
                getColor().getAlpha().setValue(0.15);
                break;

            case ShadowType.SHADOW_42:
                setBlurRadius(76200.0);
                setDistance(12700.0);
                setDirection(new DmlAngle(8100000.0));
                setVerticalScale(-0.23);
                setHorizontalSkew(new DmlAngle(800400.0));
                setAlignment(DmlRectangleAlignment.BOTTOM_RIGHT);
                getColor().getAlpha().setValue(0.2);
                break;

            case ShadowType.SHADOW_43:
                setBlurRadius(76200.0);
                setDistance(12700.0);
                setDirection(new DmlAngle(2700000.0));
                setVerticalScale(-0.23);
                setHorizontalSkew(new DmlAngle(-800400));
                setAlignment(DmlRectangleAlignment.BOTTOM_LEFT);
                getColor().getAlpha().setValue(0.2);
                break;

            default:
                return false;
        }

        return true;
    }

    private DmlColor mColor = new DmlPresetColor("black");
    private double mBlurRadius;
    private DmlAngle mDirection = new DmlAngle(0.0);
    private double mDistance;
    private /*DmlRectangleAlignment*/int mAlignment = DmlRectangleAlignment.BOTTOM;
    private DmlAngle mKx = new DmlAngle(0.0);
    private DmlAngle mKy = new DmlAngle(0.0);
    private double mSx = 1.0;
    private double mSy = 1.0;
    private boolean mRotateWithShape = true;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }

}

