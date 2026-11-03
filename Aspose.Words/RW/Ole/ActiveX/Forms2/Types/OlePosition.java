// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.ArrayUtil;
import com.aspose.MathUtil;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.msString;
import com.aspose.Debug;
import com.aspose.ms.System.BitConverter;


/// <summary>
/// Specify a position relative to a reference point measured in HIMETRIC units.
/// </summary>
class OlePosition
{
    /// <summary>
    /// Ctor.
    /// </summary>
    OlePosition(int top, int left)
    {
        mTop = top;
        mLeft = left;
    }

    /// <summary>
    /// Determines whether two object instances are equal.
    /// </summary>
    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;

        if (msObject.referenceEquals(this, obj))
            return true;

        if (obj.getClass() != this.getClass())
            return false;

        return equals((OlePosition)obj);
    }

    /// <summary>
    /// Gets hash code for this instance.
    /// </summary>
    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            return (getTop() * 397) ^ getLeft();
        }
    }

    /// <summary>
    /// Converts the OlePosition object to a string.
    /// </summary>
    public /*override*/ String toString()
    {
        String raw = ArrayUtil.dumpArray(toRaw());
        int topPt = MathUtil.doubleToInt(ConvertUtilCore.himetricToPoint(getTop()));
        int leftPt = MathUtil.doubleToInt(ConvertUtilCore.himetricToPoint(getLeft()));
        return msString.format("Raw: {0}, (T:{1}hm ({3}pt), L:{2}hm ({4}pt))", raw, getTop(), getLeft(), topPt, leftPt);
    }

    /// <summary>
    /// Creates a OlePosition object from a raw value.
    /// </summary>
    static OlePosition fromRaw(byte[] raw)
    {
        Debug.assertTrue(raw.length == 8);
        return new OlePosition(BitConverter.toInt32(raw, 4), BitConverter.toInt32(raw, 0));
    }

    /// <summary>
    /// Converts this OlePosition object to a raw value.
    /// </summary>
    byte[] toRaw()
    {
        // long raw = ((long)mTop << 32 | (long)mLeft);

        byte[] raw = new byte[8];
        System.arraycopy(BitConverter.getBytes(getTop()), 0, raw, 4, BitConverter.getBytes(getTop()).length);
        System.arraycopy(BitConverter.getBytes(getLeft()), 0, raw, 0, BitConverter.getBytes(getLeft()).length);

        return raw;
    }

    /// <summary>
    /// Creates a OlePosition object from Top and Left measured in points.
    /// </summary>
    static OlePosition fromPoints(double topInPoints, double leftInPoints)
    {
        return new OlePosition(ConvertUtilCore.pointToHimetricInt(topInPoints),
            ConvertUtilCore.pointToHimetricInt(leftInPoints));
    }

    /// <summary>
    /// Determines whether two OlePosition objects are equal.
    /// </summary>
    private boolean equals(OlePosition other)
    {
        return ((getTop() == other.getTop()) && (getLeft() == other.getLeft()));
    }

    /// <summary>
    /// Gets top-coordinate of this OlePosition, measured in HIMETRIC.
    /// </summary>
    int getTop() { return mTop; };

    private int mTop;

    /// <summary>
    /// Gets left-coordinate of this OlePosition, measured in HIMETRIC.
    /// </summary>
    int getLeft() { return mLeft; };

    private int mLeft;
}

