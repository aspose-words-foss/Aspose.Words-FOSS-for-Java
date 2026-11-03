// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;

/// <summary>
/// 20.1.8.22 ds (Dash Stop)
/// This element specifies a dash stop primitive. 
/// Dashing schemes are built by specifying an ordered 
/// list of dash stop primitive. A dash stop primitive 
/// consists of a dash and a space.
/// </summary>
class DmlDashStop
{
    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlDashStop value = (DmlDashStop)obj;

        return MathUtil.areEqual(value.getDashLength(), getDashLength()) &&
               MathUtil.areEqual(value.getSpaceLength(), getSpaceLength());
    }

    public /*override*/ int hashCode()
    {
        int hash = 0;
        hash ^= msNumber.doubleHashCode(getDashLength());
        hash ^= msNumber.doubleHashCode(getSpaceLength());
        return hash;
    }

    /// <summary>
    /// Specifies the length of the dash relative to the line width.
    /// </summary>
    double getDashLength() { return mDashLength; }
    void setDashLength(double value) { mDashLength = value; }

    /// <summary>
    /// Specifies the length of the space relative to the line width.
    /// </summary>
    double getSpaceLength() { return mSpaceLength; }
    void setSpaceLength(double value) { mSpaceLength = value; }

    private double mDashLength;
    private double mSpaceLength;

    public DmlDashStop deepClone()
    {
        DmlDashStop result = new DmlDashStop();
        result.setSpaceLength(getSpaceLength());
        result.setDashLength(getDashLength());
        return result;
    }
}
