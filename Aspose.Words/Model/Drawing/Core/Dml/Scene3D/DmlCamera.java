// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.MathUtil;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msNumber;


/// <summary>
/// 20.1.5.5 camera (Camera)
/// This element defines the placement and properties of the camera in the 3D scene. The camera position and
/// properties modify the view of the scene.
/// </summary>
class DmlCamera implements Cloneable
{
    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlCamera value = (DmlCamera)obj;

        return (value.getPresetCameraType() == getPresetCameraType()) &&
               MathUtil.areEqual(value.getZoom(), getZoom()) &&
               msObject.equals(value.getFovAngle(), getFovAngle()) &&
               msObject.equals(value.getRotation(), getRotation());
    }

    public /*override*/ int hashCode()
    {
        int hash = getFovAngle().hashCode();
        hash ^= /*enum.hashCode()=(int)value*/getPresetCameraType();
        hash ^= msNumber.doubleHashCode(getZoom());
        if (getRotation() != null)
            hash ^= getRotation().hashCode();
        return hash;
    }

    DmlCamera deepClone()
    {
        DmlCamera lhs = (DmlCamera)memberwiseClone();
        lhs.mFovAngle = mFovAngle.deepClone();
        lhs.mPresetCameraType = mPresetCameraType;
        lhs.mZoom = mZoom;

        if (mRotation != null)
            lhs.mRotation = mRotation.deepClone();

        return lhs;
    }

    /// <summary>
    /// Provides an override for the default field of view for the camera.
    /// Range from [0, 180] degrees.
    /// Default is 180 degrees (not sure if this is correct).
    /// </summary>
    DmlAngle getFovAngle() { return mFovAngle; }
    void setFovAngle(DmlAngle value) { mFovAngle = value; }

    /// <summary>
    /// Defines the preset camera that is being used by the camera element. 
    /// The preset camera defines a starting point for common preset rotations in space.
    /// </summary>
    /*DmlPresetCameraType*/int getPresetCameraType() { return mPresetCameraType; }
    void setPresetCameraType(/*DmlPresetCameraType*/int value) { mPresetCameraType = value; }

    /// <summary>
    /// Defines the zoom factor of a given camera element. 
    /// The zoom modifies the scene as a whole and zooms in or out accordingly.
    /// Default is 100%.
    /// Value is in fraction representation, so 100% equals 1.0.
    /// </summary>
    double getZoom() { return mZoom; }
    void setZoom(double value) { mZoom = value; }

    /// <summary>
    /// Defines a rotation in 3D space.
    /// </summary>
    DmlRotation3D getRotation() { return mRotation; }
    void setRotation(DmlRotation3D value) { mRotation = value; }

    private DmlAngle mFovAngle = DmlAngle.fromDegrees(180.0d);
    private /*DmlPresetCameraType*/int mPresetCameraType;
    private double mZoom = 1.0;
    private DmlRotation3D mRotation;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

