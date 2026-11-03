// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/09/2008 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.Encoding;
import java.util.Map;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.HashMap;


/// <summary>
/// Constants and utility methods for code pages and font character sets.
/// </summary>
public /*static*/ class CodePage
{
	/* Simulation of static class by using private constructor */
	private CodePage()
	{}


    public static final int SYMBOL_CHAR_SET = 2;

    public static final int HEBREW_CHAR_SET = 177;
    public static final int ARABIC_CHAR_SET = 178;

    /// <summary>
    /// 437 MS-DOS Latin US
    /// </summary>
    public static final int DOS_LATIN_US_CODE_PAGE = 437;
    /// <summary>
    /// 850 MS-DOS Latin 1
    /// </summary>
    public static final int DOS_LATIN_1_CODE_PAGE = 850;
    /// <summary>
    /// 1200
    /// </summary>
    public static final int CODE_PAGE_UTF_16 = 1200;
    /// <summary>
    /// 1201
    /// </summary>
    public static final int CODE_PAGE_UTF_16_BE = 1201;
    /// <summary>
    /// 1252 Windows ANSI code page.
    /// </summary>
    public static final int WINDOWS_LATIN_1_CODE_PAGE = 1252;
    /// <summary>
    /// 1255 Windows Hebrew code page.
    /// </summary>
    public static final int HEBREW_CODE_PAGE = 1255;
    /// <summary>
    /// 1256 Windows Arabic code page. 
    /// </summary>
    public static final int ARABIC_CODE_PAGE = 1256;
    /// <summary>
    /// 10000
    /// </summary>
    public static final int MACINTOSH_ROMAN_CODE_PAGE = 10000;
    /// <summary>
    /// 12000
    /// </summary>
    public static final int CODE_PAGE_UTF_32 = 12000;
    /// <summary>
    /// 12001
    /// </summary>
    public static final int CODE_PAGE_UTF_32_BE = 12001;
    /// <summary>
    /// 65000
    /// </summary>
    public static final int CODE_PAGE_UTF_7 = 65000;
    /// <summary>
    /// 65001
    /// </summary>
    public static final int CODE_PAGE_UTF_8 = 65001;

    // Few national encodings used in auto detection of files in unknown encoding.
    // Japanese
    public static final int WINDOWS_JAPANESE_SHIFT_JIS = 932;
    // Chinese
    public static final int OFFICIAL_CHINESE_GB_18030 = 54936;
    public static final int WINDOWS_CHINESE_GB_2312 = 936;
    // Korean
    public static final int WINDOWS_KOREAN_KS_C_5601_1987 = 949;
    // Russian
    public static final int WINDOWS_CYRILLIC_1251 = 1251;
    public static final int OLD_CYRILLIC_KOI_8_R = 20866;

    public static final int UNKNOWN = Integer.MIN_VALUE;

    /// <summary>
    /// Returns the default value if cannot find a mapping.
    /// </summary>
    public static int charSetToCodePage(int charSet, int defaultCodePage)
    {
        return (G_CHAR_SET_MAP.containsKey(charSet))
            ? G_CHAR_SET_MAP.get(charSet)
            : defaultCodePage;
    }

    /// <summary>
    /// Returns true if specified encoding is a known single-byte encoding.
    /// </summary>
    public static boolean isKnownSingleByteEncoding(Encoding encoding)
    {
        return G_SINGLE_BYTE_ENCODINGS.contains(encoding.getCodePage());
    }

    /// <summary>
    /// Returns <c>true</c> if the specified code page is a code page of a right-to-left language.
    /// </summary>
    public static boolean isRightToLeft(int codePage)
    {
        return (codePage == HEBREW_CODE_PAGE) || (codePage == ARABIC_CODE_PAGE);
    }

    /// <summary>
    /// Returns <c>true</c> if the specified charset is a charset of a right-to-left language.
    /// </summary>
    public static boolean isRightToLeftCharset(int charset)
    {
        return (charset == HEBREW_CHAR_SET) || (charset == ARABIC_CHAR_SET);
    }

    /// <summary>
    /// Maps Language to ANSI code page.
    /// </summary>
    /// <remarks>
    /// See https://www.microsoft.com/resources/msdn/goglobal/default.mspx?OS=Windows%20Vista
    /// </remarks>
    public static int lidToCodePage(int lid)
    {
        switch ((/*Language*/int)lid)
        {
            case Language.HEBREW:
            case Language.HEBREW_ISRAEL:
                return 1255;

            case Language.RUSSIAN_RUSSIA:
                return WINDOWS_CYRILLIC_1251;

            case Language.GREEK_GREECE:
                return 1253;

            default:
                // Default code page we used to read non Unicode text.
                return 1252;
        }
    }

    /// <summary>
    /// .NET doesn't define UTF-7 BOM (signature) and doesn't cut it from stream.
    /// We have to skip it manually.
    /// Seen here: http://blogs.msdn.com/oldnewthing/archive/2004/03/24/95235.aspx
    /// See also: http://en.wikipedia.org/wiki/Byte-order_mark
    /// UTF-7 can have 5 different BOMs. And 4 of them depend on the first data (non-BOM) byte.
    /// I've never seen samples of 4-byte BOMS so supported just this one.
    /// UTF-7 itself is pretty rare and archaic.
    /// </summary>
    public static /*final*/ byte[] UTF_7_BOM = new byte[] { (byte) 0x2B, (byte) 0x2F, (byte) 0x76, (byte) 0x38, (byte) 0x2D };
    /// <summary>
    /// UTF-8
    /// </summary>
    public static /*final*/ byte[] UTF_8_BOM = new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
    /// <summary>
    /// UTF-16, Little Endian
    /// </summary>
    public static /*final*/ byte[] UTF_16_LE_BOM = new byte[] { (byte) 0xFF, (byte) 0xFE };
    /// <summary>
    /// UTF-16, Big Endian
    /// </summary>
    public static /*final*/ byte[] UTF_16_BE_BOM = new byte[] { (byte) 0xFE, (byte) 0xFF };

    /// <summary>
    /// UTF-32, Little Endian
    /// </summary>
    public static /*final*/ byte[] UTF_32_LE_BOM = new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x00, (byte) 0x00 };
    /// <summary>
    /// UTF-32, Big Endian
    /// </summary>
    public static /*final*/ byte[] UTF_32_BE_BOM = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0xFE, (byte) 0xFF };

    /// <summary>
    /// Returns CodePage by BOM, or <see cref="Unknown"/> code page if a specified BOM is unknown. 
    /// </summary>
    public static int getByBom(byte[] bom)
    {
        if (ArrayUtil.hasData(bom))
        {
            for (Map.Entry<byte[], Integer> bomToCodePage : G_BOM_TO_CODE_PAGE.entrySet())
            {
                byte[] knownBom = bomToCodePage.getKey();
                if (ArrayUtil.compareBytes(bom, knownBom, knownBom.length))
                    return bomToCodePage.getValue();
            }
        }

        return UNKNOWN;
    }

    /// <summary>
    /// Returns BOM from a specified buffer.
    /// </summary>
    public static byte[] getBom(byte[] buffer)
    {
        Debug.assertTrue(buffer != null);

        for (byte[] knownBom : (Iterable<byte[]>) G_BOM_TO_CODE_PAGE.keySet())
        {
            if (ArrayUtil.compareBytes(knownBom, buffer, knownBom.length))
                return knownBom;
        }

        return ArrayUtil.EMPTY_BYTE_ARRAY;
    }

    /// <summary>
    /// A map of font character sets to the corresponding code pages.
    /// Key is integer font character set. Value is integer code page.
    /// </summary>
    private static /*final*/ SortedIntegerListGeneric<Integer> G_CHAR_SET_MAP = new SortedIntegerListGeneric<Integer>();

    /// <summary>
    /// List of known single-byte encodings.
    /// </summary>
    private static /*final*/ HashSetGeneric<Integer> G_SINGLE_BYTE_ENCODINGS = new HashSetGeneric<Integer>();

    /// <summary>
    /// Maps known BOMs to CodePages.
    /// </summary>
    private static /*final*/ HashMap<byte[], Integer> G_BOM_TO_CODE_PAGE = new HashMap<byte[], Integer>(6);


    static/* CodePage()*/
    {
        // Source: Word2007RTFSpec1.9.1.docx
        G_CHAR_SET_MAP.add(0, WINDOWS_LATIN_1_CODE_PAGE);   // ANSI
        // There is no such code page in .NET. Use UnicodeToSymbol and SymbolToUnicode instead.
        // gCharsetMap.Add(SymbolCharSet, 42);     // Symbol
        G_CHAR_SET_MAP.add(77, MACINTOSH_ROMAN_CODE_PAGE); // Mac Roman
        G_CHAR_SET_MAP.add(78, 10001); // Mac Shift Jis
        G_CHAR_SET_MAP.add(79, 10003); // Mac Hangul
        G_CHAR_SET_MAP.add(80, 10008); // Mac GB2312
        G_CHAR_SET_MAP.add(81, 10002); // Mac Big5
        G_CHAR_SET_MAP.add(83, 10005); // Mac Hebrew
        G_CHAR_SET_MAP.add(84, 10004); // Mac Arabic
        G_CHAR_SET_MAP.add(85, 10006); // Mac Greek
        G_CHAR_SET_MAP.add(86, 10081); // Mac Turkish
        G_CHAR_SET_MAP.add(87, 10021); // Mac Thai
        G_CHAR_SET_MAP.add(88, 10029); // Mac East Europe
        G_CHAR_SET_MAP.add(89, 10007); // Mac Russian
        G_CHAR_SET_MAP.add(128, WINDOWS_JAPANESE_SHIFT_JIS);  // Shift JIS
        G_CHAR_SET_MAP.add(129, 949);  // Hangul
        G_CHAR_SET_MAP.add(130, 1361); // Johab
        G_CHAR_SET_MAP.add(134, 936);  // GB2312
        G_CHAR_SET_MAP.add(136, 950);  // Big5
        G_CHAR_SET_MAP.add(161, 1253); // Greek
        G_CHAR_SET_MAP.add(162, 1254); // Turkish
        G_CHAR_SET_MAP.add(163, 1258); // Vietnamese
        G_CHAR_SET_MAP.add(177, HEBREW_CODE_PAGE); // Hebrew
        G_CHAR_SET_MAP.add(178, ARABIC_CODE_PAGE); // Arabic 
        G_CHAR_SET_MAP.add(186, 1257); // Baltic
        G_CHAR_SET_MAP.add(204, 1251); // Russian
        G_CHAR_SET_MAP.add(222, 874);  // Thai
        G_CHAR_SET_MAP.add(238, 1250); // Eastern European
        G_CHAR_SET_MAP.add(254, DOS_LATIN_US_CODE_PAGE);  // PC 437
        G_CHAR_SET_MAP.add(255, DOS_LATIN_1_CODE_PAGE);  // OEM
        G_CHAR_SET_MAP.add(65535, CODE_PAGE_UTF_16);

        // The following list is obtained using Encoding.GetEncodings() method.
        // Also the list of encodings with IsSinleByte flag available on MSDN 
        // http://msdn.microsoft.com/en-us/library/system.text.encoding.issinglebyte.aspx 
        G_SINGLE_BYTE_ENCODINGS.add(37);
        G_SINGLE_BYTE_ENCODINGS.add(437);
        G_SINGLE_BYTE_ENCODINGS.add(500);
        G_SINGLE_BYTE_ENCODINGS.add(708);
        G_SINGLE_BYTE_ENCODINGS.add(720);
        G_SINGLE_BYTE_ENCODINGS.add(737);
        G_SINGLE_BYTE_ENCODINGS.add(775);
        G_SINGLE_BYTE_ENCODINGS.add(850);
        G_SINGLE_BYTE_ENCODINGS.add(852);
        G_SINGLE_BYTE_ENCODINGS.add(855);
        G_SINGLE_BYTE_ENCODINGS.add(857);
        G_SINGLE_BYTE_ENCODINGS.add(858);
        G_SINGLE_BYTE_ENCODINGS.add(860);
        G_SINGLE_BYTE_ENCODINGS.add(861);
        G_SINGLE_BYTE_ENCODINGS.add(862);
        G_SINGLE_BYTE_ENCODINGS.add(863);
        G_SINGLE_BYTE_ENCODINGS.add(864);
        G_SINGLE_BYTE_ENCODINGS.add(865);
        G_SINGLE_BYTE_ENCODINGS.add(866);
        G_SINGLE_BYTE_ENCODINGS.add(869);
        G_SINGLE_BYTE_ENCODINGS.add(870);
        G_SINGLE_BYTE_ENCODINGS.add(874);
        G_SINGLE_BYTE_ENCODINGS.add(875);
        G_SINGLE_BYTE_ENCODINGS.add(1026);
        G_SINGLE_BYTE_ENCODINGS.add(1047);
        G_SINGLE_BYTE_ENCODINGS.add(1140);
        G_SINGLE_BYTE_ENCODINGS.add(1141);
        G_SINGLE_BYTE_ENCODINGS.add(1142);
        G_SINGLE_BYTE_ENCODINGS.add(1143);
        G_SINGLE_BYTE_ENCODINGS.add(1144);
        G_SINGLE_BYTE_ENCODINGS.add(1145);
        G_SINGLE_BYTE_ENCODINGS.add(1146);
        G_SINGLE_BYTE_ENCODINGS.add(1147);
        G_SINGLE_BYTE_ENCODINGS.add(1148);
        G_SINGLE_BYTE_ENCODINGS.add(1149);
        G_SINGLE_BYTE_ENCODINGS.add(1250);
        G_SINGLE_BYTE_ENCODINGS.add(1251);
        G_SINGLE_BYTE_ENCODINGS.add(1252);
        G_SINGLE_BYTE_ENCODINGS.add(1253);
        G_SINGLE_BYTE_ENCODINGS.add(1254);
        G_SINGLE_BYTE_ENCODINGS.add(1255);
        G_SINGLE_BYTE_ENCODINGS.add(1256);
        G_SINGLE_BYTE_ENCODINGS.add(1257);
        G_SINGLE_BYTE_ENCODINGS.add(1258);
        G_SINGLE_BYTE_ENCODINGS.add(10000);
        G_SINGLE_BYTE_ENCODINGS.add(10004);
        G_SINGLE_BYTE_ENCODINGS.add(10005);
        G_SINGLE_BYTE_ENCODINGS.add(10006);
        G_SINGLE_BYTE_ENCODINGS.add(10007);
        G_SINGLE_BYTE_ENCODINGS.add(10010);
        G_SINGLE_BYTE_ENCODINGS.add(10017);
        G_SINGLE_BYTE_ENCODINGS.add(10021);
        G_SINGLE_BYTE_ENCODINGS.add(10029);
        G_SINGLE_BYTE_ENCODINGS.add(10079);
        G_SINGLE_BYTE_ENCODINGS.add(10081);
        G_SINGLE_BYTE_ENCODINGS.add(10082);
        G_SINGLE_BYTE_ENCODINGS.add(20105);
        G_SINGLE_BYTE_ENCODINGS.add(20106);
        G_SINGLE_BYTE_ENCODINGS.add(20107);
        G_SINGLE_BYTE_ENCODINGS.add(20108);
        G_SINGLE_BYTE_ENCODINGS.add(20127);
        G_SINGLE_BYTE_ENCODINGS.add(20269);
        G_SINGLE_BYTE_ENCODINGS.add(20273);
        G_SINGLE_BYTE_ENCODINGS.add(20277);
        G_SINGLE_BYTE_ENCODINGS.add(20278);
        G_SINGLE_BYTE_ENCODINGS.add(20280);
        G_SINGLE_BYTE_ENCODINGS.add(20284);
        G_SINGLE_BYTE_ENCODINGS.add(20285);
        G_SINGLE_BYTE_ENCODINGS.add(20290);
        G_SINGLE_BYTE_ENCODINGS.add(20297);
        G_SINGLE_BYTE_ENCODINGS.add(20420);
        G_SINGLE_BYTE_ENCODINGS.add(20423);
        G_SINGLE_BYTE_ENCODINGS.add(20424);
        G_SINGLE_BYTE_ENCODINGS.add(20833);
        G_SINGLE_BYTE_ENCODINGS.add(20838);
        G_SINGLE_BYTE_ENCODINGS.add(20866);
        G_SINGLE_BYTE_ENCODINGS.add(20871);
        G_SINGLE_BYTE_ENCODINGS.add(20880);
        G_SINGLE_BYTE_ENCODINGS.add(20905);
        G_SINGLE_BYTE_ENCODINGS.add(20924);
        G_SINGLE_BYTE_ENCODINGS.add(21025);
        G_SINGLE_BYTE_ENCODINGS.add(21866);
        G_SINGLE_BYTE_ENCODINGS.add(28591);
        G_SINGLE_BYTE_ENCODINGS.add(28592);
        G_SINGLE_BYTE_ENCODINGS.add(28593);
        G_SINGLE_BYTE_ENCODINGS.add(28594);
        G_SINGLE_BYTE_ENCODINGS.add(28595);
        G_SINGLE_BYTE_ENCODINGS.add(28596);
        G_SINGLE_BYTE_ENCODINGS.add(28597);
        G_SINGLE_BYTE_ENCODINGS.add(28598);
        G_SINGLE_BYTE_ENCODINGS.add(28599);
        G_SINGLE_BYTE_ENCODINGS.add(28603);
        G_SINGLE_BYTE_ENCODINGS.add(28605);
        G_SINGLE_BYTE_ENCODINGS.add(29001);
        G_SINGLE_BYTE_ENCODINGS.add(38598);

        // The order is important for correct BOM detection.
        msDictionary.add(G_BOM_TO_CODE_PAGE, UTF_8_BOM, CODE_PAGE_UTF_8);
        msDictionary.add(G_BOM_TO_CODE_PAGE, UTF_7_BOM, CODE_PAGE_UTF_7);
        msDictionary.add(G_BOM_TO_CODE_PAGE, UTF_32_LE_BOM, CODE_PAGE_UTF_32);
        msDictionary.add(G_BOM_TO_CODE_PAGE, UTF_32_BE_BOM, CODE_PAGE_UTF_32_BE);
        msDictionary.add(G_BOM_TO_CODE_PAGE, UTF_16_LE_BOM, CODE_PAGE_UTF_16);
        msDictionary.add(G_BOM_TO_CODE_PAGE, UTF_16_BE_BOM, CODE_PAGE_UTF_16_BE);
    }
}

