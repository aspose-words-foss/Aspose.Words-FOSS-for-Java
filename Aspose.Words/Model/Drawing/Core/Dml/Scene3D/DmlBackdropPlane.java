// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;

/// <summary>
/// 20.1.5.2 backdrop (Backdrop Plane)
/// This element defines a plane in which effects, such as glow and shadow, are applied in relation to the shape they
/// are being applied to. The points and vectors contained within the backdrop define a plane in 3D space.
/// </summary>
class DmlBackdropPlane extends DmlExtensionListSource implements Cloneable
{
    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlBackdropPlane value = (DmlBackdropPlane)obj;

        return getAnchor().equals(value.getAnchor()) &&
               getNormalVector().equals(value.getNormalVector()) &&
               getUpVector().equals(value.getUpVector());
    }

    public /*override*/ int hashCode()
    {
        int hash = getAnchor().hashCode();
        hash ^= getNormalVector().hashCode();
        hash ^= getUpVector().hashCode();
        return hash;
    }

    DmlBackdropPlane deepClone()
    {
        DmlBackdropPlane lhs = (DmlBackdropPlane)memberwiseClone();
        lhs.setExtensions(cloneExtensions());
        return lhs;
    }

    /// <summary>
    /// This point is the point in space that anchors the backdrop plane.
    /// </summary>
    DmlPoint3D getAnchor() { return mAnchor; }
    void setAnchor(DmlPoint3D value) { mAnchor = value; }

    /// <summary>
    /// This element defines a normal vector.
    /// X, Y and Z coordinates of the point should be considered as a distance along the appropriate axis.
    /// </summary>
    DmlPoint3D getNormalVector() { return mNormalVector; }
    void setNormalVector(DmlPoint3D value) { mNormalVector = value; }

    /// <summary>
    /// This element defines a vector representing up.
    /// X, Y and Z coordinates of the point should be considered as a distance along the appropriate axis.
    /// </summary>
    DmlPoint3D getUpVector() { return mUpVector; }
    void setUpVector(DmlPoint3D value) { mUpVector = value; }

    private DmlPoint3D mAnchor;
    private DmlPoint3D mNormalVector;
    private DmlPoint3D mUpVector;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

