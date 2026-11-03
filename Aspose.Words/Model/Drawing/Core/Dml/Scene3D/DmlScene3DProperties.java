// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msNumber;

/// <summary>
/// 20.1.4.1.26 scene3d (3D Scene Properties)
/// This element defines optional scene-level 3D properties to apply to an object.
/// </summary>
class DmlScene3DProperties extends DmlExtensionListSource implements Cloneable
{
    DmlScene3DProperties()
    {
    	this(false);
	
    }

    DmlScene3DProperties(boolean isTheme)
    {
        mIsTheme = isTheme;
    }

    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlScene3DProperties value = (DmlScene3DProperties)obj;

        return (value.isTheme() == isTheme()) &&
               msObject.equals(value.getCamera(), getCamera()) &&
               msObject.equals(value.getLightRig(), getLightRig()) &&
               msObject.equals(value.getBackdropPlane(), getBackdropPlane());
    }

    public /*override*/ int hashCode()
    {
        int hash = 0;
        hash ^= getCamera().hashCode();
        hash ^= getLightRig().hashCode();
        if (getBackdropPlane() != null)
            hash ^= getBackdropPlane().hashCode();
        hash ^= msNumber.booleanHashCode(isTheme());

        return hash;
    }

    DmlScene3DProperties deepClone()
    {
        DmlScene3DProperties lhs = (DmlScene3DProperties)memberwiseClone();
        if (mCamera != null)
            lhs.mCamera = mCamera.deepClone();

        if (mLightRig != null)
            lhs.mLightRig = mLightRig.deepClone();

        if (mBackdropPlane != null)
            lhs.mBackdropPlane = mBackdropPlane.deepClone();

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    /// <summary>
    /// Defines placement and properties of the camera in the 3D scene.
    /// </summary>
    DmlCamera getCamera()
    {
        if (mCamera == null)
            mCamera = new DmlCamera();

        return mCamera;
    }
    void setCamera(DmlCamera value)
    {
        mCamera = value;
    }

    /// <summary>
    /// Defines the lighting properties associated with the scene.
    /// </summary>
    DmlLightRig getLightRig()
    {
        if (mLightRig == null)
            mLightRig = new DmlLightRig();

        return mLightRig;
    }
    void setLightRig(DmlLightRig value)
    {
        mLightRig = value;
    }

    /// <summary>
    /// Defines a plane in which effects, such as glow and shadow, are applied.
    /// Can be null.
    /// </summary>
    DmlBackdropPlane getBackdropPlane() { return mBackdropPlane; }
    void setBackdropPlane(DmlBackdropPlane value) { mBackdropPlane = value; }

    /// <summary>
    /// Flag indicates that 3D properties represented by this class are applied thorough document theme.
    /// </summary>
    boolean isTheme() { return mIsTheme; }

    private DmlCamera mCamera;
    private DmlLightRig mLightRig;
    private DmlBackdropPlane mBackdropPlane;
    private /*final*/ boolean mIsTheme;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

