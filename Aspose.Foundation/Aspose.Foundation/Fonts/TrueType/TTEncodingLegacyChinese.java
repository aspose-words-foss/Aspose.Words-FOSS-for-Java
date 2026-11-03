// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/02/2020 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Legacy Chinese encodings specific for TrueType fonts.
/// </summary>
class TTEncodingLegacyChinese extends TTEncoding
{
    public TTEncodingLegacyChinese(Encoding encoding)
    {
        mEncoding = encoding;
    }

    public /*override*/ String getString(byte[] bytes)
    {
        // Generally PRC encoding seems to be handled well by system GB2312 encoding.
        // But in case of WORDSNET-20026 there are additional zero byte for each encoded byte for some reason.
        byte[] actualBytes = bytes;
        if (checkExtraBytes(bytes))
            actualBytes = removeExtraBytes(bytes);

        // fix Java problem with empty string
        if (mEncoding.getString(actualBytes).length() == 0)
            return Encoding.getEncoding("gb2312").getString(actualBytes);
        else
            return mEncoding.getString(actualBytes);
    }

    private static boolean checkExtraBytes(byte[] bytes)
    {
        if (bytes.length % 2 == 1)
            return false;

        for (int i = 0; i < bytes.length; i++)
            if (i % 2 == 0 && bytes[i] != 0)
                return false;

        return true;
    }

    private static byte[] removeExtraBytes(byte[] bytes)
    {
        byte[] result = new byte[bytes.length / 2];
        for (int i = 0; i < result.length; i++)
            result[i] = bytes[i * 2 + 1];

        return result;
    }

    private /*final*/ Encoding mEncoding;
}

