// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msNumber;

/// <summary>
/// Represents an angle in 60000ths of a degree
/// </summary>
class DmlAngle implements Cloneable
{
    DmlAngle()
    {
    	this (0.0);
	
    }

    DmlAngle(double value)
    {
        mValue = value;
        mRawValue = mValue;
    }

    DmlAngle deepClone()
    {
        return (DmlAngle)memberwiseClone();
    }

    static DmlAngle createWithNormalization(double value)
    {
        DmlAngle angle = new DmlAngle(value);
        angle.normalizeAngle();
        return angle;
    }

    static DmlAngle fromDegrees(double degrees)
    {
        return new DmlAngle(ConvertUtilCore.degreesToDmlAngles(degrees));
    }
    
    static DmlAngle fromRadians(double radians)
    {
        return new DmlAngle(ConvertUtilCore.radiansToDmlAngles(radians));
    }

    public boolean equals(DmlAngle other)
    {
        if (msObject.referenceEquals(null, other))
            return false;
        if (msObject.referenceEquals(this, other))
            return true;
        return ((other.mValue) == (mValue));
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (obj.getClass() != DmlAngle.class)
            return false;
        return equals((DmlAngle)obj);
    }

    public /*override*/ int hashCode()
    {
        return msNumber.doubleHashCode(mValue);
    }

    /// <summary>
    /// Normalizes specified angle so it becomes greater or equal to zero and less then 360 degrees.
    /// </summary>
    private void normalizeAngle()
    {
        double angle360 = ConvertUtilCore.degreesToDmlAngles(360.0f);
        if (Math.abs(mValue) > angle360)
            mValue %= angle360;

        while (mValue < 0)
            mValue += angle360;
    }

    /// <summary>
    /// An angle in 60,000ths of a degree. Positive angles are clockwise 
    /// (i.e., towards the positive y axis); negative angles are 
    /// counter-clockwise (i.e., towards the negative y axis).
    /// </summary>
    double getValue() { return mValue; }

    double getValueInRadians() { return ConvertUtilCore.dmlAnglesToRadians(getValue()); }

    double getValueInDegrees() { return ConvertUtilCore.dmlAnglesToDegrees(getValue()); }

    /// <summary>
    /// Returns original value read from document in degrees (without normalization)
    /// </summary>
    double getRawValueInDegrees() { return ConvertUtilCore.dmlAnglesToDegrees(mRawValue); }

    private double mValue;
    private /*final*/ double mRawValue;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

