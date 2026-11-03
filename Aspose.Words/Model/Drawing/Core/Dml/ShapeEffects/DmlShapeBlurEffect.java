// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/24/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.DrColor;


/// <summary>
/// Represents 20.1.8.15 blur (Blur Effect)
/// This element specifies a blur effect that is applied to the entire shape, including its fill. All color channels,
/// including alpha, are affected.
/// 
/// Class is wrapper for <see cref="DmlBlurEffect"/>. This effect can be applied to blip as well to whole shape.
/// </summary>
class DmlShapeBlurEffect extends DmlShapeEffect implements Cloneable
{
    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlShapeBlurEffect value = (DmlShapeBlurEffect)obj;

        return msObject.equals(value.getBlur(), getBlur());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= getBlur().hashCode();
        return hash;
    }

    DmlShapeBlurEffect()
    {
        mBlur = new DmlBlurEffect();
        mBlur.setCropToOriginalSize(false);
    }

    /*override*/ DmlShapeEffect deepClone()
    {
        DmlShapeBlurEffect lhs = (DmlShapeBlurEffect)memberwiseClone();

        if (mBlur != null)
            lhs.mBlur = (DmlBlurEffect)mBlur.deepClone();

        return lhs;
    }

    /*override*/ /*DmlShapeEffectType*/int getEffectType() { return DmlShapeEffectType.BLUR; }

    /// <summary>
    /// Specifies whether the bounds of the object should be grown as a result of the blurring.
    /// Default value is true.
    /// </summary>
    boolean getGrow() { return mBlur.getGrow(); }
    void setGrow(boolean value) { mBlur.setGrow(value); }

    /// <summary>
    /// Specifies the radius of blur.
    /// </summary>
    double getRadius() { return mBlur.getRadius(); }
    void setRadius(double value) { mBlur.setRadius(value); }

    DrColor getBackgroundColor() { return mBlur.getBackgroundColor(); }
    void setBackgroundColor(DrColor value) { mBlur.setBackgroundColor(value); }

    DmlBlurEffect getBlur() { return mBlur; }

    private DmlBlurEffect mBlur;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

