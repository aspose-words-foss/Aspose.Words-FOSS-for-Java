package com.aspose.ms.System.Text;

import java.util.HashMap;
import java.util.Map;

class NetEncodingProvider extends BaseEncodingProvider
{
    @Override
    protected Map<Integer, Encoding> initSupportedEncodings()
    {
        Map<Integer, Encoding> encodings = new HashMap<Integer, Encoding>();
        for (BaseEncoding entry : getDotNetEncodings())
            encodings.put(entry.mData.CodePage, entry);

        return encodings;
    }

    private BaseEncoding[] getDotNetEncodings()
    {
        return new BaseEncoding[]{
                newDotNetEncoding(37, "IBM037", "IBM037", "IBM037", "IBM037", 1252), // IBM EBCDIC (US-Canada)
                newDotNetEncoding(437, "IBM437", "IBM437", "IBM437", "IBM437", 1252), // OEM United States
                newDotNetEncoding(500, "IBM500", "IBM500", "IBM500", "IBM500", 1252), // IBM EBCDIC (International)
                newDotNetEncoding(708, "ASMO-708", "ASMO-708", "ASMO-708", "ASMO-708", 1256), // Arabic (ASMO 708)
                newDotNetEncoding(737, "ibm737", "ibm737", "ibm737", "ibm737", 1253), // Greek (DOS)
                newDotNetEncoding(775, "ibm775", "ibm775", "ibm775", "ibm775", 1257), // Baltic (DOS)
                newDotNetEncoding(850, "ibm850", "ibm850", "ibm850", "ibm850", 1252), // Western European (DOS)
                newDotNetEncoding(852, "ibm852", "ibm852", "ibm852", "ibm852", 1250), // Central European (DOS)
                newDotNetEncoding(855, "IBM855", "IBM855", "IBM855", "IBM855", 1252), // OEM Cyrillic
                newDotNetEncoding(857, "ibm857", "ibm857", "ibm857", "ibm857", 1254), // Turkish (DOS)
                newDotNetEncoding(858, "IBM00858", "IBM00858", "IBM00858", "IBM00858", 1252), // OEM Multilingual Latin I
                newDotNetEncoding(860, "IBM860", "IBM860", "IBM860", "IBM860", 1252), // Portuguese (DOS)
                newDotNetEncoding(861, "ibm861", "ibm861", "ibm861", "ibm861", 1252), // Icelandic (DOS)
                newDotNetEncoding(863, "IBM863", "IBM863", "IBM863", "IBM863", 1252), // French Canadian (DOS)
                newDotNetEncoding(864, "IBM864", "IBM864", "IBM864", "IBM864", 1256), // Arabic (864)
                newDotNetEncoding(865, "IBM865", "IBM865", "IBM865", "IBM865", 1252), // Nordic (DOS)
                newDotNetEncoding(869, "ibm869", "ibm869", "ibm869", "ibm869", 1253), // Greek, Modern (DOS)
                newDotNetEncoding(870, "IBM870", "IBM870", "IBM870", "IBM870", 1250), // IBM EBCDIC (Multilingual Latin-2)
                newDotNetEncoding(874, "windows-874", "windows-874", "windows-874", "windows-874", 874), // Thai (Windows)
                newDotNetEncoding(875, "cp875", "cp875", "cp875", "cp875", 1253), // IBM EBCDIC (Greek Modern)
                newDotNetEncoding(932, "shift_jis", "iso-2022-jp", "iso-2022-jp", "shift_jis", 932), // Japanese (Shift-JIS)
                newDotNetEncoding(936, "gb2312", "gb2312", "gb2312", "gb2312", 936), // Chinese Simplified (GB2312)
                newDotNetEncoding(949, "ks_c_5601-1987", "ks_c_5601-1987", "ks_c_5601-1987", "ks_c_5601-1987", 949), // Korean
                newDotNetEncoding(950, "big5", "big5", "big5", "big5", 950), // Chinese Traditional (Big5)
                newDotNetEncoding(1026, "IBM1026", "IBM1026", "IBM1026", "IBM1026", 1254), // IBM EBCDIC (Turkish Latin-5)
                newDotNetEncoding(1047, "IBM01047", "IBM01047", "IBM01047", "IBM01047", 1252), // IBM Latin-1
                newDotNetEncoding(1140, "IBM01140", "IBM01140", "IBM01140", "IBM01140", 1252), // IBM EBCDIC (US-Canada-Euro)
                newDotNetEncoding(1141, "IBM01141", "IBM01141", "IBM01141", "IBM01141", 1252), // IBM EBCDIC (Germany-Euro)
                newDotNetEncoding(1142, "IBM01142", "IBM01142", "IBM01142", "IBM01142", 1252), // IBM EBCDIC (Denmark-Norway-Euro)
                newDotNetEncoding(1143, "IBM01143", "IBM01143", "IBM01143", "IBM01143", 1252), // IBM EBCDIC (Finland-Sweden-Euro)
                newDotNetEncoding(1144, "IBM01144", "IBM01144", "IBM01144", "IBM01144", 1252), // IBM EBCDIC (Italy-Euro)
                newDotNetEncoding(1145, "IBM01145", "IBM01145", "IBM01145", "IBM01145", 1252), // IBM EBCDIC (Spain-Euro)
                newDotNetEncoding(1146, "IBM01146", "IBM01146", "IBM01146", "IBM01146", 1252), // IBM EBCDIC (UK-Euro)
                newDotNetEncoding(1147, "IBM01147", "IBM01147", "IBM01147", "IBM01147", 1252), // IBM EBCDIC (France-Euro)
                newDotNetEncoding(1148, "IBM01148", "IBM01148", "IBM01148", "IBM01148", 1252), // IBM EBCDIC (International-Euro)
                newDotNetEncoding(1149, "IBM01149", "IBM01149", "IBM01149", "IBM01149", 1252), // IBM EBCDIC (Icelandic-Euro)
                newDotNetEncoding(1361, "Johab", "Johab", "Johab", "Johab", 949), // Korean (Johab)
                newDotNetEncoding(10000, "macintosh", "macintosh", "macintosh", "macintosh", 1252), // Western European (Mac)
                newDotNetEncoding(10001, "x-mac-japanese", "x-mac-japanese", "x-mac-japanese", "x-mac-japanese", 932), // Japanese (Mac)
                newDotNetEncoding(10002, "x-mac-chinesetrad", "x-mac-chinesetrad", "x-mac-chinesetrad", "x-mac-chinesetrad", 950), // Chinese Traditional (Mac)
                newDotNetEncoding(10003, "x-mac-korean", "x-mac-korean", "x-mac-korean", "x-mac-korean", 949), // Korean (Mac)
                newDotNetEncoding(10004, "x-mac-arabic", "x-mac-arabic", "x-mac-arabic", "x-mac-arabic", 1256), // Arabic (Mac)
                newDotNetEncoding(10005, "x-mac-hebrew", "x-mac-hebrew", "x-mac-hebrew", "x-mac-hebrew", 1255), // Hebrew (Mac)
                newDotNetEncoding(10006, "x-mac-greek", "x-mac-greek", "x-mac-greek", "x-mac-greek", 1253), // Greek (Mac)
                newDotNetEncoding(10007, "x-mac-cyrillic", "x-mac-cyrillic", "x-mac-cyrillic", "x-mac-cyrillic", 1251), // Cyrillic (Mac)
                newDotNetEncoding(10008, "x-mac-chinesesimp", "x-mac-chinesesimp", "x-mac-chinesesimp", "x-mac-chinesesimp", 936), // Chinese Simplified (Mac)
                newDotNetEncoding(10010, "x-mac-romanian", "x-mac-romanian", "x-mac-romanian", "x-mac-romanian", 1250), // Romanian (Mac)
                newDotNetEncoding(10017, "x-mac-ukrainian", "x-mac-ukrainian", "x-mac-ukrainian", "x-mac-ukrainian", 1251), // Ukrainian (Mac)
                newDotNetEncoding(10021, "x-mac-thai", "x-mac-thai", "x-mac-thai", "x-mac-thai", 874), // Thai (Mac)
                newDotNetEncoding(10029, "x-mac-ce", "x-mac-ce", "x-mac-ce", "x-mac-ce", 1250), // Central European (Mac)
                newDotNetEncoding(10079, "x-mac-icelandic", "x-mac-icelandic", "x-mac-icelandic", "x-mac-icelandic", 1252), // Icelandic (Mac)
                newDotNetEncoding(10081, "x-mac-turkish", "x-mac-turkish", "x-mac-turkish", "x-mac-turkish", 1254), // Turkish (Mac)
                newDotNetEncoding(10082, "x-mac-croatian", "x-mac-croatian", "x-mac-croatian", "x-mac-croatian", 1250), // Croatian (Mac)
                newDotNetEncoding(20000, "x-Chinese-CNS", "x-Chinese-CNS", "x-Chinese-CNS", "x-Chinese-CNS", 950), // Chinese Traditional (CNS)
                newDotNetEncoding(20001, "x-cp20001", "x-cp20001", "x-cp20001", "x-cp20001", 950), // TCA Taiwan
                newDotNetEncoding(20002, "x-Chinese-Eten", "x-Chinese-Eten", "x-Chinese-Eten", "x-Chinese-Eten", 950), // Chinese Traditional (Eten)
                newDotNetEncoding(20003, "x-cp20003", "x-cp20003", "x-cp20003", "x-cp20003", 950), // IBM5550 Taiwan
                newDotNetEncoding(20004, "x-cp20004", "x-cp20004", "x-cp20004", "x-cp20004", 950), // TeleText Taiwan
                newDotNetEncoding(20005, "x-cp20005", "x-cp20005", "x-cp20005", "x-cp20005", 950), // Wang Taiwan
                newDotNetEncoding(20105, "x-IA5", "x-IA5", "x-IA5", "x-IA5", 1252), // Western European (IA5)
                newDotNetEncoding(20106, "x-IA5-German", "x-IA5-German", "x-IA5-German", "x-IA5-German", 1252), // German (IA5)
                newDotNetEncoding(20107, "x-IA5-Swedish", "x-IA5-Swedish", "x-IA5-Swedish", "x-IA5-Swedish", 1252), // Swedish (IA5)
                newDotNetEncoding(20108, "x-IA5-Norwegian", "x-IA5-Norwegian", "x-IA5-Norwegian", "x-IA5-Norwegian", 1252), // Norwegian (IA5)
                newDotNetEncoding(20127, "us-ascii", "us-ascii", "us-ascii", "us-ascii", 1252), // US-ASCII
                newDotNetEncoding(20261, "x-cp20261", "x-cp20261", "x-cp20261", "x-cp20261", 1252), // T.61
                newDotNetEncoding(20269, "x-cp20269", "x-cp20269", "x-cp20269", "x-cp20269", 1252), // ISO-6937
                newDotNetEncoding(20273, "IBM273", "IBM273", "IBM273", "IBM273", 1252), // IBM EBCDIC (Germany)
                newDotNetEncoding(20277, "IBM277", "IBM277", "IBM277", "IBM277", 1252), // IBM EBCDIC (Denmark-Norway)
                newDotNetEncoding(20278, "IBM278", "IBM278", "IBM278", "IBM278", 1252), // IBM EBCDIC (Finland-Sweden)
                newDotNetEncoding(20280, "IBM280", "IBM280", "IBM280", "IBM280", 1252), // IBM EBCDIC (Italy)
                newDotNetEncoding(20284, "IBM284", "IBM284", "IBM284", "IBM284", 1252), // IBM EBCDIC (Spain)
                newDotNetEncoding(20285, "IBM285", "IBM285", "IBM285", "IBM285", 1252), // IBM EBCDIC (UK)
                newDotNetEncoding(20290, "IBM290", "IBM290", "IBM290", "IBM290", 932), // IBM EBCDIC (Japanese katakana)
                newDotNetEncoding(20297, "IBM297", "IBM297", "IBM297", "IBM297", 1252), // IBM EBCDIC (France)
                newDotNetEncoding(20420, "IBM420", "IBM420", "IBM420", "IBM420", 1256), // IBM EBCDIC (Arabic)
                newDotNetEncoding(20423, "IBM423", "IBM423", "IBM423", "IBM423", 1253), // IBM EBCDIC (Greek)
                newDotNetEncoding(20424, "IBM424", "IBM424", "IBM424", "IBM424", 1255), // IBM EBCDIC (Hebrew)
                newDotNetEncoding(20833, "x-EBCDIC-KoreanExtended", "x-EBCDIC-KoreanExtended", "x-EBCDIC-KoreanExtended", "x-EBCDIC-KoreanExtended", 949), // IBM EBCDIC (Korean Extended)
                newDotNetEncoding(20838, "IBM-Thai", "IBM-Thai", "IBM-Thai", "IBM-Thai", 874), // IBM EBCDIC (Thai)
                newDotNetEncoding(20866, "koi8-r", "koi8-r", "koi8-r", "koi8-r", 1251), // Cyrillic (KOI8-R)
                newDotNetEncoding(20871, "IBM871", "IBM871", "IBM871", "IBM871", 1252), // IBM EBCDIC (Icelandic)
                newDotNetEncoding(20880, "IBM880", "IBM880", "IBM880", "IBM880", 1251), // IBM EBCDIC (Cyrillic Russian)
                newDotNetEncoding(20905, "IBM905", "IBM905", "IBM905", "IBM905", 1254), // IBM EBCDIC (Turkish)
                newDotNetEncoding(20924, "IBM00924", "IBM00924", "IBM00924", "IBM00924", 1252), // IBM Latin-1
                newDotNetEncoding(20932, "EUC-JP", "EUC-JP", "EUC-JP", "EUC-JP", 932), // Japanese (JIS 0208-1990 and 0212-1990)
                newDotNetEncoding(20936, "x-cp20936", "x-cp20936", "x-cp20936", "x-cp20936", 936), // Chinese Simplified (GB2312-80)
                newDotNetEncoding(20949, "x-cp20949", "x-cp20949", "x-cp20949", "x-cp20949", 949), // Korean Wansung
                newDotNetEncoding(21025, "cp1025", "cp1025", "cp1025", "cp1025", 1251), // IBM EBCDIC (Cyrillic Serbian-Bulgarian)
                newDotNetEncoding(21866, "koi8-u", "koi8-u", "koi8-u", "koi8-u", 1251), // Cyrillic (KOI8-U)
                newDotNetEncoding(28591, "iso-8859-1", "iso-8859-1", "iso-8859-1", "iso-8859-1", 1252), // Western European (ISO)
                newDotNetEncoding(28592, "iso-8859-2", "iso-8859-2", "iso-8859-2", "iso-8859-2", 1250), // Central European (ISO)
                newDotNetEncoding(28593, "iso-8859-3", "iso-8859-3", "iso-8859-3", "iso-8859-3", 1254), // Latin 3 (ISO)
                newDotNetEncoding(28594, "iso-8859-4", "iso-8859-4", "iso-8859-4", "iso-8859-4", 1257), // Baltic (ISO)
                newDotNetEncoding(28595, "iso-8859-5", "iso-8859-5", "iso-8859-5", "iso-8859-5", 1251), // Cyrillic (ISO)
                newDotNetEncoding(28596, "iso-8859-6", "iso-8859-6", "iso-8859-6", "iso-8859-6", 1256), // Arabic (ISO)
                newDotNetEncoding(28597, "iso-8859-7", "iso-8859-7", "iso-8859-7", "iso-8859-7", 1253), // Greek (ISO)
                newDotNetEncoding(28598, "iso-8859-8", "iso-8859-8", "iso-8859-8", "iso-8859-8", 1255), // Hebrew (ISO-Visual)
                newDotNetEncoding(28599, "iso-8859-9", "iso-8859-9", "iso-8859-9", "iso-8859-9", 1254), // Turkish (ISO)
                newDotNetEncoding(28603, "iso-8859-13", "iso-8859-13", "iso-8859-13", "iso-8859-13", 1257), // Estonian (ISO)
                newDotNetEncoding(28605, "iso-8859-15", "iso-8859-15", "iso-8859-15", "iso-8859-15", 1252), // Latin 9 (ISO)
                newDotNetEncoding(29001, "x-Europa", "x-Europa", "x-Europa", "x-Europa", 1252), // Europa
                newDotNetEncoding(38598, "iso-8859-8-i", "iso-8859-8-i", "iso-8859-8-i", "iso-8859-8-i", 1255), // Hebrew (ISO-Logical)
                newDotNetEncoding(50220, "iso-2022-jp", "iso-2022-jp", "iso-2022-jp", "iso-2022-jp", 932), // Japanese (JIS)
                newDotNetEncoding(50221, "csISO2022JP", "iso-2022-jp", "iso-2022-jp", "csISO2022JP", 932), // Japanese (JIS-Allow 1 byte Kana)
                newDotNetEncoding(50222, "iso-2022-jp", "iso-2022-jp", "iso-2022-jp", "iso-2022-jp", 932), // Japanese (JIS-Allow 1 byte Kana - SO/SI)
                newDotNetEncoding(50225, "iso-2022-kr", "iso-2022-kr", "euc-kr", "iso-2022-kr", 949), // Korean (ISO)
                newDotNetEncoding(50227, "x-cp50227", "x-cp50227", "x-cp50227", "x-cp50227", 936), // Chinese Simplified (ISO-2022)
                newDotNetEncoding(51932, "euc-jp", "euc-jp", "euc-jp", "euc-jp", 932), // Japanese (EUC)
                newDotNetEncoding(51936, "EUC-CN", "EUC-CN", "EUC-CN", "EUC-CN", 936), // Chinese Simplified (EUC)
                newDotNetEncoding(51949, "euc-kr", "euc-kr", "euc-kr", "euc-kr", 949), // Korean (EUC)
                newDotNetEncoding(52936, "hz-gb-2312", "hz-gb-2312", "hz-gb-2312", "hz-gb-2312", 936), // Chinese Simplified (HZ)
                newDotNetEncoding(54936, "GB18030", "GB18030", "GB18030", "GB18030", 936), // Chinese Simplified (GB18030)
                newDotNetEncoding(57002, "x-iscii-de", "x-iscii-de", "x-iscii-de", "x-iscii-de", 57002), // ISCII Devanagari
                newDotNetEncoding(57003, "x-iscii-be", "x-iscii-be", "x-iscii-be", "x-iscii-be", 57003), // ISCII Bengali
                newDotNetEncoding(57004, "x-iscii-ta", "x-iscii-ta", "x-iscii-ta", "x-iscii-ta", 57004), // ISCII Tamil
                newDotNetEncoding(57005, "x-iscii-te", "x-iscii-te", "x-iscii-te", "x-iscii-te", 57005), // ISCII Telugu
                newDotNetEncoding(57006, "x-iscii-as", "x-iscii-as", "x-iscii-as", "x-iscii-as", 57006), // ISCII Assamese
                newDotNetEncoding(57007, "x-iscii-or", "x-iscii-or", "x-iscii-or", "x-iscii-or", 57007), // ISCII Oriya
                newDotNetEncoding(57008, "x-iscii-ka", "x-iscii-ka", "x-iscii-ka", "x-iscii-ka", 57008), // ISCII Kannada
                newDotNetEncoding(57009, "x-iscii-ma", "x-iscii-ma", "x-iscii-ma", "x-iscii-ma", 57009), // ISCII Malayalam
                newDotNetEncoding(57010, "x-iscii-gu", "x-iscii-gu", "x-iscii-gu", "x-iscii-gu", 57010), // ISCII Gujarati
                newDotNetEncoding(57011, "x-iscii-pa", "x-iscii-pa", "x-iscii-pa", "x-iscii-pa", 57011), // ISCII Punjabi

                /*
                // These encodings are overridden in CustomEncodingProvider
                newDotNetEncoding(1250, "windows-1250", "iso-8859-2", "windows-1250", "windows-1250", 1250), // Central European (Windows)
                newDotNetEncoding(1251, "windows-1251", "koi8-r", "windows-1251", "windows-1251", 1251), // Cyrillic (Windows)
                newDotNetEncoding(1252, "Windows-1252", "iso-8859-1", "Windows-1252", "Windows-1252", 1252), // Western European (Windows)
                newDotNetEncoding(1253, "windows-1253", "iso-8859-7", "windows-1253", "windows-1253", 1253), // Greek (Windows)
                newDotNetEncoding(1254, "windows-1254", "iso-8859-9", "windows-1254", "windows-1254", 1254), // Turkish (Windows)
                newDotNetEncoding(1255, "windows-1255", "windows-1255", "windows-1255", "windows-1255", 1255), // Hebrew (Windows)
                newDotNetEncoding(1256, "windows-1256", "windows-1256", "windows-1256", "windows-1256", 1256), // Arabic (Windows)
                newDotNetEncoding(1257, "windows-1257", "windows-1257", "windows-1257", "windows-1257", 1257), // Baltic (Windows)
                newDotNetEncoding(1258, "windows-1258", "windows-1258", "windows-1258", "windows-1258", 1258), // Vietnamese (Windows)
                newDotNetEncoding(720, "DOS-720", "DOS-720", "DOS-720", "DOS-720", 1256), // Arabic (DOS)
                newDotNetEncoding(862, "DOS-862", "DOS-862", "DOS-862", "DOS-862", 1255, "IBM862"), // Hebrew (DOS)
                newDotNetEncoding(866, "cp866", "cp866", "cp866", "cp866", 1251), // Cyrillic (DOS)
                newDotNetEncoding(1200, "utf-16", "utf-16", "utf-16", "utf-16", 1200, "UTF-16LE"), // Unicode
                newDotNetEncoding(1201, "utf-16BE", "utf-16BE", "utf-16BE", "utf-16BE", 1200), // Unicode (Big-Endian)
                newDotNetEncoding(12000, "utf-32", "utf-32", "utf-32", "utf-32", 1200), // Unicode (UTF-32)
                newDotNetEncoding(12001, "utf-32BE", "utf-32BE", "utf-32BE", "utf-32BE", 1200), // Unicode (UTF-32 Big-Endian)
                newDotNetEncoding(65000, "utf-7", "utf-7", "utf-7", "utf-7", 1200), // Unicode (UTF-7)
                newDotNetEncoding(65001, "utf-8", "utf-8", "utf-8", "utf-8", 1200), // Unicode (UTF-8)
                */
        };
    }

    private BaseEncoding newDotNetEncoding(int codePage, String encodingInfoName, String bodyName, String headerName, String webName, int windowsCodePage)
    {
        EncodingDataItem data = new EncodingDataItem(codePage, encodingInfoName, bodyName, headerName, webName, windowsCodePage, gNetCodePageToJavaCharsetName.get(codePage));

        if (isGB(data))
            return new BaseGBEncoding(data);

        else if (!data.isSupported())
            return new BaseUnicodeEncoding(data, false);

        return new BaseEncoding(data);
    }

    private static boolean isGB(EncodingDataItem data)
    {
        switch (data.CodePage)
        {
            case 54936: //GB18030
            case 936:// GB2312
            case 10008: // MAC Simplified Chinese (GB 2312); Chinese Simplified (Mac)
                return true;
        }
        return false;
    }

    //https://docs.microsoft.com/en-us/windows/win32/intl/code-page-identifiers
    //https://docs.oracle.com/javase/8/docs/technotes/guides/intl/encoding.doc.html
    private static final Map<Integer, String> gNetCodePageToJavaCharsetName = new HashMap<Integer, String>()
    {{
        put(874, "x-windows-874");
        put(932, "windows-31j");
        put(936, "x-mswin-936");
        put(949, "x-windows-949");
        put(950, "x-windows-950");
        put(1361, "x-Johab");
        put(10000, "MacRoman");
        put(10001, "windows-31j");
        put(10002, "Big5");
        put(10005, "x-MacHebrew");
        put(10006, "x-MacGreek");
        put(10007, "x-MacCyrillic");
        put(10008, "GB2312"); // x-mac-chinesesimp	MAC Simplified Chinese (GB 2312); Chinese Simplified (Mac)
        put(10010, "x-MacRomania");
        put(10017, "x-MacUkraine");
        put(10021, "x-MacThai");
        put(10029, "x-MacCentralEurope");
        put(10079, "x-MacIceland");
        put(10081, "x-MacTurkish");
        put(38598, "ISO-8859-8");
        put(50220, "x-windows-50220");
        put(50221, "x-windows-50221");
        put(50222, "ISO-2022-JP");
        put(50225, "ISO-2022-KR");
    }};
}
