// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/09/2017 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Helper methods to work with TrueType encodings.
/// </summary>
abstract class TTEncoding
{
    public static TTEncoding getEncoding(int platformId, int encodingId)
    {
        // The task here is to use a proper encoding to read the string.
        // At the moment we support only a few most common encoding.
        if (platformId == Cmap.PLATFORM_ID_UNICODE)
            return getBigEndianUnicode();

        if (platformId == Cmap.PLATFORM_ID_MACINTOSH &&
            ((encodingId == Cmap.MACINTOSH_ENCODING_ID_ROMAN) ||
             (encodingId == Cmap.MACINTOSH_ENCODING_ID_JAPANESE) ||
             (encodingId == Cmap.MACINTOSH_ENCODING_ID_KOREAN) ||
             (encodingId == Cmap.MACINTOSH_ENCODING_ID_SIMPLIFIED_CHINESE)))
            return getCp1252();

        if (platformId == Cmap.PLATFORM_ID_MACINTOSH &&
            encodingId == Cmap.MACINTOSH_ENCODING_ID_TRADITIONAL_CHINESE)
            return getMacTradChinese();

        if (platformId == Cmap.PLATFORM_ID_MICROSOFT &&
            ((encodingId == Cmap.MICROSOFT_ENCODING_ID_UNICODE_UCS_2) ||
             (encodingId == Cmap.MICROSOFT_ENCODING_ID_UNICODE_UCS_4) ||
             (encodingId == Cmap.MICROSOFT_ENCODING_ID_SYMBOL)))
            return getBigEndianUnicode();

        if (platformId == Cmap.PLATFORM_ID_MICROSOFT &&
            encodingId == Cmap.MICROSOFT_ENCODING_ID_PRC)
            return getPrc();

        if (platformId == Cmap.PLATFORM_ID_MICROSOFT &&
            encodingId == Cmap.MICROSOFT_ENCODING_ID_BIG_5)
            return getBig5();

        return null;
    }

    public abstract String getString(byte[] bytes);

    private static TTEncoding getBigEndianUnicode()
    {
        if (gBigEndianUnicode == null)
            synchronized (G_SYNC_ROOT)
            {
                if (gBigEndianUnicode == null)
                    gBigEndianUnicode = new TTEncodingRegular(Encoding.getBigEndianUnicode());
            }

        return gBigEndianUnicode;
    }

    private static TTEncoding getCp1252()
    {
        if (gCp1252 == null)
            synchronized (G_SYNC_ROOT)
            {
                if (gCp1252 == null)
                    gCp1252 = new TTEncodingRegular(Encoding.getEncoding(1252));
            }

        return gCp1252;
    }

    private static TTEncoding getPrc()
    {
        if (gPrc == null)
            synchronized (G_SYNC_ROOT)
            {
                if (gPrc == null)
                    gPrc = new TTEncodingLegacyChinese(Encoding.getEncoding("gb2312"));
            }

        return gPrc;
    }

    private static TTEncoding getBig5()
    {
        if (gBig5 == null)
            synchronized (G_SYNC_ROOT)
            {
                if (gBig5 == null)
                    gBig5 = new TTEncodingLegacyChinese(Encoding.getEncoding(950));
            }

        return gBig5;
    }

    private static TTEncoding getMacTradChinese()
    {
        if (gMacTradChinese == null)
            synchronized (G_SYNC_ROOT)
            {
                if (gMacTradChinese == null)
                    gMacTradChinese = new TTEncodingLegacyChinese(Encoding.getEncoding(10002));
            }

        return gMacTradChinese;
    }

    private static TTEncoding gBigEndianUnicode;
    private static TTEncoding gCp1252;
    private static TTEncoding gPrc;
    private static TTEncoding gBig5;
    private static TTEncoding gMacTradChinese;
    private static /*final*/ Object G_SYNC_ROOT = new Object();
}

