// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/12/2016 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.BitConverter;
import com.aspose.ms.System.msObject;


/// <summary>
/// Represents codepages supported by the font.
/// </summary>
public final class FontCodepageRanges
{
    public FontCodepageRanges(/*uint*/int range1, /*uint*/int range2)
    {
        mRange1 = range1;
        mRange2 = range2;
    }

    public FontCodepageRanges(byte[] bytes, int start)
    {
        Debug.assertTrue(bytes.length >= start + 2 * 4);

        mRange1 = BitConverter.toUInt32(bytes, start);
        mRange2 = BitConverter.toUInt32(bytes, start + 4);
    }

    public /*uint*/int getRange1() { return mRange1; }

    public /*uint*/int getRange2() { return mRange2; }

    public boolean isSymbolCharsetUsed() { return BitUtil.isSetUInt32(mRange1, RANGE_1_SYMBOL); }

    boolean isCjkMetrics() { return BitUtil.isSetUInt32(mRange1, RANGE_1_CJK_METRICS_MASK); }

    public boolean isAnsiCodepageUsed() { return BitUtil.isSetUInt32(mRange1, RANGE_1_ANSI); }

    public boolean isArabicCodepageUsed() { return BitUtil.isSetUInt32(mRange1, RANGE_1_ARABIC); }

    public byte getCharset()
    {
        if (G_RANGE_1_TO_CHARSET.containsKey(mRange1))
            return G_RANGE_1_TO_CHARSET.get(mRange1);

        // Default charset.
        return (byte) 0x01;
    }

    public FontCodepageRanges addCharset(byte charset)
    {
        if (!G_CHARSET_TO_RANGE_1.containsKey(charset))
            return this;

        /*uint*/int charsetBit = G_CHARSET_TO_RANGE_1.get(charset);
        return new FontCodepageRanges((/*uint*/int)((mRange1 & 0xFFFFFFFFL) | (charsetBit & 0xFFFFFFFFL)), mRange2);
    }

    //>>>>>>>> #region  Equality members

    private boolean equals(FontCodepageRanges other)
    {
        return (mRange1 == other.mRange1) && (mRange2 == other.mRange2);
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (obj.getClass() != getClass())
            return false;
        return equals((FontCodepageRanges)obj);
    }

    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            return ((int)(mRange1 & 0xFFFFFFFFL) * 397) ^ (int)(mRange2 & 0xFFFFFFFFL);
        }
    }

    //<<<<<<<< #endregion 

    private /*final*/ /*uint*/int mRange1;
    private /*final*/ /*uint*/int mRange2;

    private static void addCharsetToMap(/*uint*/int codepageBit, byte charset)
    {
        G_RANGE_1_TO_CHARSET.add(codepageBit, charset);
        G_CHARSET_TO_RANGE_1.add(charset, codepageBit);
    }

    /// <summary>
    /// Maps ulCodePageRange1 of OS/2 metrics table to character set.
    /// </summary>
    private static /*final*/ UIntToByteDictionary G_RANGE_1_TO_CHARSET = new UIntToByteDictionary();
    private static /*final*/ ByteToUIntDictionary G_CHARSET_TO_RANGE_1 = new ByteToUIntDictionary();

    public static /*final*/ FontCodepageRanges EMPTY = new FontCodepageRanges(0, 0);
    public static final /*uint*/int RANGE_1_ANSI = 0x00000001;
    public static final /*uint*/int RANGE_1_SYMBOL = (int) 0x80000000L;
    public static final /*uint*/int RANGE_1_ARABIC = 0x00000040;

    /// <summary>
    /// Experiments shows that MW performs CJK metrics adjustment according to the presence of following codepages:
    /// Bit 17 - JIS/Japan
    /// Bit 18 - Chinese: Simplified chars—PRC and Singapore
    /// Bit 19 - Korean Wansung
    /// Bit 20 - Chinese: Traditional chars—Taiwan and Hong Kong
    /// </summary>
    private static final /*uint*/int RANGE_1_CJK_METRICS_MASK = 0x001E0000;

    static/* FontCodepageRanges()*/
    {
        // Charset values are taken from ECMA 376 spec.
        addCharsetToMap(0x00000001, (byte) 0x00); // Default ANSI.
        addCharsetToMap(0x00000002, (byte) 0xEE); // Eastern European
        addCharsetToMap(0x00000004, (byte) 0xCC); // Russian
        addCharsetToMap(0x00000008, (byte) 0xA1); // Greek
        addCharsetToMap(0x00000010, (byte) 0xA2); // Turkish
        addCharsetToMap(0x00000020, (byte) 0xB1); // Hebrew
        addCharsetToMap(0x00000040, (byte) 0xB2); // Arabic
        addCharsetToMap(0x00000080, (byte) 0xBA); // Baltic
        addCharsetToMap(0x00000100, (byte) 0xA3); // Vietnamese
        addCharsetToMap(0x00010000, (byte) 0xDE); // Thai
        addCharsetToMap(0x00020000, (byte) 0x80); // Shift JIS
        addCharsetToMap(0x00040000, (byte) 0x86); // GB2312
        addCharsetToMap(0x00080000, (byte) 0x81); // Hangul
        addCharsetToMap(0x00100000, (byte) 0x88); // Big5
        addCharsetToMap(0x00200000, (byte) 0x82); // Johab
        addCharsetToMap(0x20000000, (byte) 0x4D); // Mac Roman
        addCharsetToMap(0x40000000, (byte) 0xFF); // OEM
        addCharsetToMap((int) 0x80000000L, (byte) 0x02); // Symbol
    }
}

