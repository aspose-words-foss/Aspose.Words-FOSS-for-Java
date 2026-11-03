// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msObject;

/// <summary>
/// 20.1.5.9 lightRig (Light Rig)
/// This element defines the light rig associated with the table. The light rig comes into play when there is a 3D
/// bevel applied to a cell. When 3D is used, the light rig defines the lighting properties associated with the scene.
/// </summary>
class DmlLightRig implements Cloneable
{
    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlLightRig value = (DmlLightRig)obj;

        return (value.getLightRigType() == getLightRigType()) &&
               (value.getDirection() == getDirection()) &&
               msObject.equals(value.getRotation(), getRotation());
    }

    public /*override*/ int hashCode()
    {
        int hash = 0;
        if (getRotation() != null)
            hash ^= getRotation().hashCode();

        hash ^= /*enum.hashCode()=(int)value*/getDirection();
        hash ^= /*enum.hashCode()=(int)value*/getLightRigType();

        return hash;
    }

    DmlLightRig deepClone()
    {
        DmlLightRig lhs = (DmlLightRig)memberwiseClone();
        if (mRotation != null)
            lhs.mRotation = mRotation.deepClone();

        lhs.mDirection = mDirection;
        lhs.mLightRigType = mLightRigType;
        return lhs;
    }

    /// <summary>
    /// Defines a rotation in 3D space.
    /// </summary>
    DmlRotation3D getRotation() { return mRotation; }
    void setRotation(DmlRotation3D value) { mRotation = value; }

    /// <summary>
    /// Defines the direction from which the light rig is oriented in relation to the scene.
    /// </summary>
    /*DmlLightRigDirection*/int getDirection() { return mDirection; }
    void setDirection(/*DmlLightRigDirection*/int value) { mDirection = value; }

    /// <summary>
    /// Defines the preset type of light rig which is to be applied to the scene.
    /// </summary>
    /*DmlLightRigType*/int getLightRigType() { return mLightRigType; }
    void setLightRigType(/*DmlLightRigType*/int value) { mLightRigType = value; }

    private DmlRotation3D mRotation;
    private /*DmlLightRigDirection*/int mDirection;
    private /*DmlLightRigType*/int mLightRigType;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

