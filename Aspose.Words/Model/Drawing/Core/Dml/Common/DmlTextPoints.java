// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.msObject;

/// <summary>
/// Represents a size in increments of point.
/// Whole points are specified in increments of 100 starting with 100 being a point size of 1. 
/// For instance a font point size of 12 would be 1200 and a font point size of 12.5 would be 1250. 
/// </summary>
class DmlTextPoints implements Cloneable
{
    DmlTextPoints()
    {
    }

    public DmlTextPoints deepClone()
    {
        return (DmlTextPoints)memberwiseClone();
    }

    DmlTextPoints(int value)
    {
        mValue = value;
    }

    static DmlTextPoints fromPoints(double points)
    {
        return new DmlTextPoints((int) (points*VALUES_IN_POINT));
    }

    static DmlTextPoints fromEmus(double emus)
    {
        return fromPoints(ConvertUtilCore.emuToPoint(emus));
    }

    public boolean equals(DmlTextPoints other)
    {
        if (msObject.referenceEquals(null, other))
            return false;
        if (msObject.referenceEquals(this, other))
            return true;
        return other.mValue == mValue;
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (obj.getClass() != DmlTextPoints.class)
            return false;
        return equals((DmlTextPoints)obj);
    }

    public /*override*/ int hashCode()
    {
        return mValue;
    }

    int getValue() { return mValue; }

    double getValueInPoints() { return getValue() / VALUES_IN_POINT; }

    double getValueInEmus() { return ConvertUtilCore.pointToEmu(getValueInPoints()); }

    private /*final*/ int mValue;
    private static final double VALUES_IN_POINT = 100.0;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

