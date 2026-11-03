// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msObject;


/// <summary>
/// 20.1.5.11 rot (Rotation)
/// This element defines a rotation in 3D space. A rotation in DrawingML is defined through the use of a latitude
/// coordinate, a longitude coordinate, and a revolution about the axis as the latitude and longitude coordinates.
/// </summary>
class DmlRotation3D implements Cloneable
{
    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlRotation3D value = (DmlRotation3D)obj;

        return msObject.equals(value.getLatitude(), getLatitude()) &&
               msObject.equals(value.getLongitude(), getLongitude()) &&
               msObject.equals(value.getRevolution(), getRevolution());
    }

    public /*override*/ int hashCode()
    {
        int hash = 0;
        if (getLatitude() != null)
            hash^=getLatitude().hashCode();
        if (getLongitude() != null)
            hash ^= getLongitude().hashCode();
        if (getRevolution() != null)
            hash ^= getRevolution().hashCode();
        return hash;
    }

    DmlRotation3D deepClone()
    {
        DmlRotation3D lhs = (DmlRotation3D)memberwiseClone();
        if (mLatitude != null)
            lhs.mLatitude = mLatitude.deepClone();
        if (mLongitude != null)
            lhs.mLongitude = mLongitude.deepClone();
        if (mRevolution != null)
            lhs.mRevolution = mRevolution.deepClone();
        return lhs;
    }

    /// <summary>
    /// Defines the latitude value of the rotation.
    /// </summary>
    DmlAngle getLatitude() { return mLatitude; }
    void setLatitude(DmlAngle value) { mLatitude = value; }

    /// <summary>
    /// Defines the longitude value of the rotation.
    /// </summary>
    DmlAngle getLongitude() { return mLongitude; }
    void setLongitude(DmlAngle value) { mLongitude = value; }

    /// <summary>
    /// This attributes defines the revolution around the central axis in the rotation.
    /// </summary>
    DmlAngle getRevolution() { return mRevolution; }
    void setRevolution(DmlAngle value) { mRevolution = value; }

    private DmlAngle mLatitude;
    private DmlAngle mLongitude;
    private DmlAngle mRevolution;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

