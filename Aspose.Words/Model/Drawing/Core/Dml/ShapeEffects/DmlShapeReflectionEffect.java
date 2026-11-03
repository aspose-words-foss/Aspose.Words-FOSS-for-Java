// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/24/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msNumber;


/// <summary>
/// 20.1.8.50 reflection (Reflection Effect)
/// This element specifies a reflection effect.
/// </summary>
class DmlShapeReflectionEffect extends DmlShapeEffect implements Cloneable
{
    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlShapeReflectionEffect value = (DmlShapeReflectionEffect)obj;

        return (value.getRotateWithShape() == getRotateWithShape()) &&
               (value.getAlignment() == getAlignment()) &&
               MathUtil.areEqual(value.getBlurRadius(), getBlurRadius()) &&
               MathUtil.areEqual(value.getDistance(), getDistance()) &&
               MathUtil.areEqual(value.getHorizontalScale(), getHorizontalScale()) &&
               MathUtil.areEqual(value.getVerticalScale(), getVerticalScale()) &&
               MathUtil.areEqual(value.getEndAlpha(), getEndAlpha()) &&
               MathUtil.areEqual(value.getStartAlpha(), getStartAlpha()) &&
               MathUtil.areEqual(value.getEndPosition(), getEndPosition()) &&
               MathUtil.areEqual(value.getStartPosition(), getStartPosition()) &&
               msObject.equals(value.getDirection(), getDirection()) &&
               msObject.equals(value.getFadeDirection(), getFadeDirection()) &&
               msObject.equals(value.getHorizontalSkew(), getHorizontalSkew()) &&
               msObject.equals(value.getVerticalSkew(), getVerticalSkew());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= msNumber.doubleHashCode(getBlurRadius());
        hash ^= msNumber.doubleHashCode(getDirection().getValue());
        hash ^= msNumber.doubleHashCode(getDistance());
        hash ^= msNumber.doubleHashCode(getHorizontalSkew().getValue());
        hash ^= msNumber.doubleHashCode(getVerticalSkew().getValue());
        hash ^= msNumber.doubleHashCode(getHorizontalScale());
        hash ^= msNumber.doubleHashCode(getVerticalScale());
        hash ^= msNumber.doubleHashCode(getEndAlpha());
        hash ^= msNumber.doubleHashCode(getStartAlpha());
        hash ^= msNumber.doubleHashCode(getEndPosition());
        hash ^= msNumber.doubleHashCode(getStartPosition());
        hash ^= msNumber.doubleHashCode(getFadeDirection().getValue());
        return hash;
    }

    /*override*/ DmlShapeEffect deepClone()
    {
        // The DmlAngle fields are not cloned since the class is immutable.
        return (DmlShapeReflectionEffect)memberwiseClone();
    }

    /*override*/ /*DmlShapeEffectType*/int getEffectType() { return DmlShapeEffectType.REFLECTION; }

    /*override*/ boolean isEmpty()
    {
        return MathUtil.isZero(mBlurRadius, 
            mDirection.getValueInDegrees(),
            mDistance,
            mKx.getValueInDegrees(),
            mKy.getValueInDegrees(),
            mSx,
            mSy,
            mEndAlpha,
            mStartAlpha,
            mEndPosition,
            mStartPosition,
            mFadeDirection.getValueInDegrees());
    }
    
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
    /// Specifies the direction of the alpha gradient ramp relative to the shape itself.
    /// </summary>
    DmlAngle getDirection() { return mDirection; }
    void setDirection(DmlAngle value) { mDirection = value; }

    /// <summary>
    /// Specifies how far to distance the shadow.
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
    /// Specifies the ending reflection opacity.
    /// </summary>
    double getEndAlpha() { return mEndAlpha; }
    void setEndAlpha(double value) { mEndAlpha = value; }

    /// <summary>
    /// Specifies the starting reflection opacity.
    /// </summary>
    double getStartAlpha() { return mStartAlpha; }
    void setStartAlpha(double value) { mStartAlpha = value; }

    /// <summary>
    /// Specifies the end position (along the alpha gradient ramp) of the end alpha value.
    /// </summary>
    double getEndPosition() { return mEndPosition; }
    void setEndPosition(double value) { mEndPosition = value; }

    /// <summary>
    /// Specifies the start position (along the alpha gradient ramp) of the start alpha value.
    /// </summary>
    double getStartPosition() { return mStartPosition; }
    void setStartPosition(double value) { mStartPosition = value; }

    /// <summary>
    /// Specifies the direction to offset the reflection.
    /// </summary>
    DmlAngle getFadeDirection() { return mFadeDirection; }
    void setFadeDirection(DmlAngle value) { mFadeDirection = value; }

    private double mBlurRadius;
    private DmlAngle mDirection = new DmlAngle();
    private double mDistance;
    private /*DmlRectangleAlignment*/int mAlignment = DmlRectangleAlignment.BOTTOM;
    private DmlAngle mKx = new DmlAngle();
    private DmlAngle mKy = new DmlAngle();
    private double mSx = 1.0;
    private double mSy = 1.0;
    private boolean mRotateWithShape = true;
    private double mEndAlpha;
    private double mStartAlpha = 1.0;
    private double mEndPosition = 1.0;
    private double mStartPosition;
    private DmlAngle mFadeDirection = new DmlAngle(5400000.0);

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

