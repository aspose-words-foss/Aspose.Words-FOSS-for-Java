// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/01/2018 by Alexey Morozov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FontUtil;
import com.aspose.BitUtil;
import com.aspose.CodePage;
import com.aspose.ms.System.msArray;


/// <summary>
/// Represents FontSignature which stores information about a font's <see cref="FontUnicodeRanges"/> and <see cref="FontCodepageRanges"/>
/// </summary>
class FontSignature
{
    FontSignature(byte[] data)
    {
        mData = (data == null) ? new byte[SIG_LENGTH] : data;
    }

    void addCharset(int charset)
    {            
        if (charset == FontUtil.UNDEFINED_CHARSET)
            return;

        // Seems there is no codepage for this charset.
        if ((charset == FontUtil.DEFAULT_CHARSET))
            return;

        int codePageBit = getCodePageBit(charset);

        if (codePageBit >= 0)
            setBit(16, codePageBit);
    }


    private void setBit(int offset, int index)
    {
        int byteNo = (index / 8) + offset;
        int bitNo = index % 8;

        int curByte = (mData[byteNo] & 0xFF);
        mData[byteNo] = (byte)BitUtil.setBit(curByte, (((byte)(1 << bitNo)) & 0xFF), true);
    }

    byte[] getData() { return mData; }

    /// <summary>
    /// Determines CodePage bit by charset.
    /// </summary>
    private static int getCodePageBit(int charset)
    {
        // There is no such code page in .NET, so let's just return the corresponding bit. 
        if (charset == CodePage.SYMBOL_CHAR_SET)
            return SYMBOL_CODE_PAGE_BIT;

        int codepage = CodePage.charSetToCodePage(charset, CodePage.WINDOWS_LATIN_1_CODE_PAGE);

        return msArray.indexOf((CODE_PAGE_BITS), (codepage), 0, (CODE_PAGE_BITS).length);
    }

    static /*final*/ int[] CODE_PAGE_BITS = new int[]
    {
        // 0
        1252, 1250, 1251, 1253, 1254, 1255, 1256, 1257,
        // 8
        1258, 0, 0, 0, 0, 0, 0, 0,
        // 16
        874, 932, 936, 949, 950, 1361, 0, 0,
        // 24
        0, 0, 0, 0, 0, 0, 0, 2,
        // 32
        0, 0, 0, 0, 0, 0, 0, 0,
        // 40
        0, 0, 0, 0, 0, 0, 0, 0,
        // 48
        869, 866, 865, 864, 863, 862, 861, 860,
        // 56
        857, 855, 852, 775, 737, 708, 850, 437
    };

    private /*final*/ byte[] mData;

    private static final int SIG_LENGTH = 24;

    private static final int SYMBOL_CODE_PAGE_BIT = 31;
}

