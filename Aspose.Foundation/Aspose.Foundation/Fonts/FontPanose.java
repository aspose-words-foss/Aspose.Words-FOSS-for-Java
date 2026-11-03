// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/12/2016 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;

/// <summary>
/// Represents the font PANOSE classification number.
/// </summary>
/// <remarks>
/// See https://www.microsoft.com/typography/otspec/os2.htm#pan for more details.
/// </remarks>
public final class FontPanose
{
    public FontPanose(byte[] values)
    {
        if (values == null)
            values = new byte[PANOSE_LENGTH];

        if (values.length != PANOSE_LENGTH)
        {
            Debug.fail("Incorrect PANOSE length.");
            values = new byte[PANOSE_LENGTH];
        }

        mValues = values;
    }

    public byte[] getValues() { return mValues; }

    //>>>>>>>> #region  Equality members

    private boolean equals(FontPanose other)
    {
        return ArrayUtil.isArrayEqual(getValues(), other.getValues());
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (obj.getClass() != getClass())
            return false;
        return equals((FontPanose)obj);
    }

    public /*override*/ int hashCode()
    {
        int hashCode = 0;
        for (byte value : mValues)
            hashCode = (hashCode * 397) ^ (value & 0xFF);

        return hashCode;
    }

    //<<<<<<<< #endregion 

    private /*final*/ byte[] mValues;

    private static final int PANOSE_LENGTH = 10;

    public static final byte VALUE_ANY = (byte) 0;
    public static final byte VALUE_NO_FIT = (byte) 1;
    public static /*final*/ FontPanose EMPTY = new FontPanose(new byte[PANOSE_LENGTH]);
}

