// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msString;
import com.aspose.Debug;
import com.aspose.ms.System.BitConverter;
import com.aspose.ConvertUtilCore;


/// <summary>
/// Specifies size of control measured in HIMETRIC units.
/// </summary>
class OleSize
{
    /// <summary>
    /// Ctor.
    /// </summary>
    OleSize(int width, int height)
    {
        mWidth = width;
        mHeight = height;
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

        return equals((OleSize)obj);
    }

    /// <summary>
    /// Gets hash code for this instance.
    /// </summary>
    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            return (getWidth() * 397) ^ getHeight();
        }
    }

    /// <summary>
    /// Converts the OleSize object to a string.
    /// </summary>
    public /*override*/ String toString()
    {
        long raw = (long)getWidth() | ((long)getHeight() << 32);
        return msString.format("Raw:0x{0:X8}, (W:{1}hm, H:{2}hm)", raw, getWidth(), getHeight());
    }

    /// <summary>
    /// Creates a OleSize object from raw.
    /// </summary>
    static OleSize fromRaw(byte[] raw)
    {
        Debug.assertTrue(raw.length == 8);
        return new OleSize(BitConverter.toInt32(raw, 0), BitConverter.toInt32(raw, 4));
    }

    /// <summary>
    /// Gets raw value of this OleSize object.
    /// </summary>
    byte[] toRaw()
    {
        long raw = (long)getWidth() | ((long)getHeight() << 32);
        return BitConverter.getBytes(raw);
    }

    /// <summary>
    /// Creates a OleSize object from Width and Height measured in points.
    /// </summary>
    static OleSize fromPoints(double widthInPoints, double heightInPoints)
    {
        return new OleSize(ConvertUtilCore.pointToHimetricInt(widthInPoints),
            ConvertUtilCore.pointToHimetricInt(heightInPoints));
    }

    /// <summary>
    /// Determines whether two OleSize objects are equal.
    /// </summary>
    private boolean equals(OleSize other)
    {
        Debug.assertTrue(other != null);

        return (getWidth() == other.getWidth()) &&
               (getHeight() == other.getHeight());
    }

    /// <summary>
    /// Gets width of this OleSize, measured in HIMETRIC.
    /// </summary>
    int getWidth() { return mWidth; };

    private int mWidth;

    /// <summary>
    /// Gets height of this OleSize, measured in HIMETRIC.
    /// </summary>
    int getHeight() { return mHeight; };

    private int mHeight;
}

