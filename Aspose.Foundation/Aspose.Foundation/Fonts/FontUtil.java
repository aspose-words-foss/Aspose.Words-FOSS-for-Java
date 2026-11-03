// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/12/2008 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.Drawing.FontStyle;
import com.aspose.ms.System.msArray;
import com.aspose.ms.System.IO.Stream;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;


/// <summary>
/// Basic font utility methods.
/// </summary>
public /*static*/ class FontUtil
{
	/* Simulation of static class by using private constructor */
	private FontUtil()
	{}

    /// <summary>
    /// Default FontSize for Rtf format, measured in half points.
    /// </summary>
    public static final int RTF_DEFAULT_FONT_SIZE = 24;

    /// <summary>
    /// Default character set of a font.
    /// </summary>
    public static final int DEFAULT_CHARSET = 1;

    /// <summary>
    /// ANSI character set of a font.
    /// </summary>
    public static final int ANSI_CHARSET = 0;

    /// <summary>
    /// Undefined character set of a font.
    /// </summary>
    public static final int UNDEFINED_CHARSET = -1;

    /// <summary>
    /// Returns True if specified font name belongs to known symbolic fonts.
    /// It is of course better to look at the font char set, but it is not always accurate.
    /// </summary>
    public static boolean isSymbolic(String fontName)
    {
        // WORDSNET-20983 Despite 'Segoe UI Symbol' font contains 'Symbol' in its name,
        // Word does not consider it as a symbolic font.
        // WORDSNET-26687 Similar issue with 'Noto Sans Symbols' font.
        if (msString.equals(fontName, "Segoe UI Symbol", StringComparison.ORDINAL_IGNORE_CASE))
            return false;
        if (msString.equals(fontName, "Noto Sans Symbols", StringComparison.ORDINAL_IGNORE_CASE))
            return false;

        return msString.indexOf(fontName, "Symbol", 0, StringComparison.ORDINAL) >= 0 ||
               msString.indexOf(fontName, "Webdings", 0, StringComparison.ORDINAL) >= 0 ||
               msString.indexOf(fontName, "Wingdings", 0, StringComparison.ORDINAL) >= 0 ||
               msString.indexOf(fontName, "Dingbats", 0, StringComparison.ORDINAL) >= 0;
    }

    public static String validatePostscriptName(String postscriptName)
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < postscriptName.length(); i++)
        {
            char c = postscriptName.charAt(i);
            if (isPostscriptCharAllowed(c))
                builder.append(c);
        }
        return builder.toString();
    }

    private static boolean isPostscriptCharAllowed(char c)
    {
        return
            c >= 33 && c <= 126 &&
            c != '[' && c != ']' && c != '(' && c != ')' && c != '{' &&
            c != '}' && c != '<' && c != '>' && c != '/' && c != '%';
    }

    /// <summary>
    /// If the Unicode character is in the Private Use Area 0xF020-0xF0FF, converts it into 0x0020 - 0x00FF.
    /// This is needed when converting from Unicode characters formatted using the Symbol font into Symbol encoding.
    /// There is no code page for Symbol encoding in .NET, therefore one needs to use this method.
    /// </summary>
    public static char unicodeToSymbol(char c)
    {
        return isPrivateUnicode(c) ? (char)(c - 0xF000) : c;
    }

    /// <summary>
    /// Converts a character encoded using the Symbol encoding into a Unicode character.
    /// See comments above.
    /// </summary>
    public static char symbolToUnicode(char c)
    {
        return isSymbol(c) ? (char)(c + 0xF000) : c;
    }

    /// <summary>
    /// Converts a character encoded using Wingding font in Private Use Area
    /// to analogue Unicode character from some other Unicode area.
    /// </summary>
    public static char wingdingToUnicode(char c)
    {
        switch (c)
        {
            case '':
                return '✓';

            default:
                return c;
        }
    }

    /// <summary>
    /// Returns True if specified character is in the Private Use Area (0xF020-0xF0FF).
    /// </summary>
    public static boolean isPrivateUnicode(char c)
    {
        return ((c >= 0xF020) && (c <= 0xF0FF));
    }

    public static boolean isSymbol(char c)
    {
        return ((c >= 0x0020) && (c <= 0x00FF));
    }


    /// <summary>
    /// Gets charset from fontName for some specific font names.
    /// </summary>
    /// <param name="fontName">Font Name</param>
    /// <returns>Charset value. Zero if fontName is not on a list.</returns>
    public static int charsetFromFontName(String fontName)
    {
        // Charset suffixes comparison is case sensitive. For example, MSW doesn't consider
        // CYR or TUR suffixes as a charset suffixes.
        String suffix = fontName.substring(msString.lastIndexOf(fontName, " ", StringComparison.ORDINAL) + 1);

        if (G_SUFFIX_CHARSET_MAP.containsKey(suffix))
        {
            return G_SUFFIX_CHARSET_MAP.get(suffix);
        }
        else
        {
            switch (gStringSwitchMap.of(suffix.toUpperCase()))
            {
                case /*"BATANG"*/0:
                    return 129;
                case /*"明朝"*/1:
                    return 128;
                default:
                    return UNDEFINED_CHARSET;
            }
        }
    }

    /// <summary>
    /// Gets character set resolved by either IANA or Font name.
    /// </summary>
    /// <returns><see cref="UndefinedCharset"/> if cannot be resolved.</returns>
    public static int resolveCharset(String ianaName, String fontName)
    {
        // Try to resolve by IANA.
        int charset = charsetByIanaName(ianaName);
        if (charset != UNDEFINED_CHARSET)
            return charset;

        // Try to resolve by font name.
        return charsetFromFontName(fontName);
    }

    /// <summary>
    /// Gets character set by IANA name/aliases.
    /// </summary>
    /// <remarks>
    /// The Internet Assigned Numbers Authority (IANA)
    /// is responsible for the global coordination of the DNS Root, IP addressing,
    /// and other Internet protocol resources.
    /// http://www.iana.org/assignments/character-sets/character-sets.xml
    /// </remarks>
    private static int charsetByIanaName(String ianaName)
    {
        if (StringUtil.hasChars(ianaName))
        {
            int charset = G_CHARSET_IANA_NAME_MAP.tryGetValue(ianaName);
            if (!StringToIntBidirectionalMap.isNullSubstitute(charset))
                return charset;

            // Leave this just in case. For example, Hangul character in MS Word is ks_c-5601-1987, but
            // in the Encoding class and at the site above it is ks_c_5601-1987. MS Word can read the both values.
            // Maybe other office applications use different values.
            // Also, it may be needed when IANA name will be public.
            Encoding encoding = tryGetEncoding(ianaName);
            if (encoding != null)
                return charsetFromCodePage(encoding.getWindowsCodePage());
        }

        return UNDEFINED_CHARSET;
    }

    /// <summary>
    /// Gets IANA name by charset code.
    /// </summary>
    public static String ianaNameByCharset(int charset)
    {
        return G_CHARSET_IANA_NAME_MAP.getValue(charset, "");
    }

    /// <summary>
    /// Get charset decimal value by code page.
    /// </summary>
    /// <returns>Charset value. Zero if code page is not on a list.</returns>
    public static int charsetFromCodePage(int codePage)
    {
        int val = G_CHARSET_FROM_CODE_PAGE.get(codePage);
        if (!IntToIntDictionary.isNullSubstitute(val))
            return val;

        // Zero it is ANSI charset. MS Word 2013 writes it by default upon writing strict OOXML.
        return 0;
    }

    /// <summary>
    /// Detects if a fontName is an Asian font name or not.
    /// </summary>
    public static boolean isAsianFont(IFontProvider fontProvider, String fontName) throws Exception
    {
        // WORDSNET-26956 First check if this is well-known EastAsian font.
        if (ArrayUtil.contains(G_EAST_ASIAN_FONTS, fontName))
            return true;

        TTFont font = fontProvider.fetchTTFont(fontName, FontStyle.REGULAR);
        return font.isCjkMetrics();
    }

    /// <summary>
    /// Detects if a fontName is a Common System font name or not.
    /// </summary>
    public static boolean isCommonSystemFont(String fontName)
    {
        return (msArray.indexOf((G_COMMON_SYSTEM_FONTS), (fontName), 0, (G_COMMON_SYSTEM_FONTS).length) != -1);
    }

    /// <summary>
    /// Detects if the specified fontName is a monospaced font's name.
    /// </summary>
    public static boolean isMonospacedFont(String fontName)
    {
        return (msArray.indexOf((G_MONOSPACED_FONTS), (fontName), 0, (G_MONOSPACED_FONTS).length) != -1);
    }

    /// <summary>
    /// Reads the last resort font 'Fanwood' that is embedded into assembly resources.
    /// </summary>
    public static TTFont loadLastResortFontFromResources()
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	Stream stream = SystemPal.fetchResourceStream("Aspose.Resources.Fanwood.ttf");
        	try /*JAVA: was using*/
        	{
            	byte[] data = StreamUtil.copyStreamToByteArray(stream);
            	return TTFontBuilder.read(new PhysicalFontData(new MemoryFontData(data)));
        	}
        	finally { if (stream != null) stream.close(); }
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    private static Encoding tryGetEncoding(String ianaName)
    {
        try
        {
            return Encoding.getEncoding(ianaName);
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }
    }

    /// <summary>
    /// Fills dictionary that allows getting charset code from code page.
    /// </summary>
    private static void fillCharsetFromCodePageMap()
    {
        //ANSI charset.
        G_CHARSET_FROM_CODE_PAGE.add(1252, 0);

        //SHIFTJIS charset.
        G_CHARSET_FROM_CODE_PAGE.add(932, 128);

        //HANGUL charset.
        G_CHARSET_FROM_CODE_PAGE.add(949, 129);

        //GB2312 charset.
        G_CHARSET_FROM_CODE_PAGE.add(936, 134);

        //CHINESEBIG5 charset.
        G_CHARSET_FROM_CODE_PAGE.add(950, 136);

        //GREEK charset.
        G_CHARSET_FROM_CODE_PAGE.add(1253, 161);

        //TURKISH charset.
        G_CHARSET_FROM_CODE_PAGE.add(1254, 162);

        //Vietnamese charset.
        G_CHARSET_FROM_CODE_PAGE.add(1258, 163);

        //HEBREW charset.
        G_CHARSET_FROM_CODE_PAGE.add(1255, 177);

        //ARABIC charset.
        G_CHARSET_FROM_CODE_PAGE.add(1256, 178);

        //BALTIC charset.
        G_CHARSET_FROM_CODE_PAGE.add(1257, 186);

        //RUSSIAN charset.
        G_CHARSET_FROM_CODE_PAGE.add(1251, 204);

        //THAI charset.
        G_CHARSET_FROM_CODE_PAGE.add(874, 222);

        //EE charset.
        G_CHARSET_FROM_CODE_PAGE.add(1250, 238);
    }

    /// <summary>
    /// Fills map between IANA name and charset code.
    /// </summary>
    private static void fillCharsetIanaNameMap()
    {
        //ANSI charset.
        G_CHARSET_IANA_NAME_MAP.addEntry(0, "iso-8859-1");

        //Default charset (1) is skipped now. MS Word converts it to system default charset.

        //Symbol charset (2) is skipped. MS Word does not write charset for such font.

        //Macintosh (Standard Roman) charset.
        G_CHARSET_IANA_NAME_MAP.addEntry(0x4D, "macintosh");

        //SHIFTJIS charset.
        G_CHARSET_IANA_NAME_MAP.addEntry(0x80, "shift_jis");

        //HANGUL charset.
        G_CHARSET_IANA_NAME_MAP.addEntry(0x81, "ks_c-5601-1987");

        //Johab charset. IANA name is taken from §21.1.2.3.1 of ISO/IEC 29500:1.
        G_CHARSET_IANA_NAME_MAP.addEntry(0x82, "KS C-5601-1992");

        //GB2312 charset.
        G_CHARSET_IANA_NAME_MAP.addEntry(0x86, "GBK");

        //CHINESEBIG5 charset.
        G_CHARSET_IANA_NAME_MAP.addEntry(0x88, "Big5");

        //GREEK charset.
        G_CHARSET_IANA_NAME_MAP.addEntry(0xA1, "windows-1253");

        //TURKISH charset.
        G_CHARSET_IANA_NAME_MAP.addEntry(0xA2, "iso-8859-9");

        //Vietnamese charset.
        G_CHARSET_IANA_NAME_MAP.addEntry(0xA3, "windows-1258");

        //HEBREW charset.
        G_CHARSET_IANA_NAME_MAP.addEntry(0xB1, "windows-1255");

        //ARABIC charset.
        G_CHARSET_IANA_NAME_MAP.addEntry(0xB2, "windows-1256");

        //BALTIC charset.
        G_CHARSET_IANA_NAME_MAP.addEntry(0xBA, "windows-1257");

        //RUSSIAN charset.
        G_CHARSET_IANA_NAME_MAP.addEntry(0xCC, "windows-1251");

        //THAI charset.
        G_CHARSET_IANA_NAME_MAP.addEntry(0xDE, "windows-874");

        //EE charset.
        G_CHARSET_IANA_NAME_MAP.addEntry(0xEE, "windows-1250");

        //OEM charset (0xFF) is skipped.
    }

    /// <summary>
    /// Fills map between font name script suffix and charset code.
    /// </summary>
    private static void fillSuffixCharsetMap()
    {
        G_SUFFIX_CHARSET_MAP.add("(Arabic)", 178);
        G_SUFFIX_CHARSET_MAP.add("CE", 238);
        G_SUFFIX_CHARSET_MAP.add("Cyr", 204);
        G_SUFFIX_CHARSET_MAP.add("(Vietnamese)", 163);
        G_SUFFIX_CHARSET_MAP.add("Western", 0);
        G_SUFFIX_CHARSET_MAP.add("(Hebrew)", 177);
        G_SUFFIX_CHARSET_MAP.add("Tur", 162);
        G_SUFFIX_CHARSET_MAP.add("Greek", 161);
        G_SUFFIX_CHARSET_MAP.add("Baltic", 186);
        G_SUFFIX_CHARSET_MAP.add("(Thai)", 222);
        // WORDSNET-28233 Added suffix for Japanese fonts.
        G_SUFFIX_CHARSET_MAP.add("JP", 128);
    }

    /// <summary>
    /// Minimal allowed by MSWord Font Size measured in half points.
    /// </summary>
    private static final int MIN_ALLOWED_FONT_SIZE = 2;

    /// <summary>
    /// MS Word treats these fonts as common system fonts.
    /// </summary>
    /// <remarks>
    /// For example, MS Word does not embed these fonts if corresponded option is specified in its GUI.
    /// </remarks>
    private static /*final*/ String[] G_COMMON_SYSTEM_FONTS = new String[] { "Arial", "Times New Roman", "Courier New", "Symbol", "Wingdings" };

    /// <summary>
    /// List of monospaced fonts' names.
    /// </summary>
    private static /*final*/ String[] G_MONOSPACED_FONTS = new String[] { "Consolas", "Courier New", "Lucida Console", "DotumChe" };

    /// <summary>
    /// The list of fonts that allow kerning to disable the Allow Hanging Punctuation rule.
    /// </summary>
    private static /*final*/ String[] G_FONTS_ALLOWING_KERNING_TO_DISABLE_HANGING_PUNCTUATION = new String[] { "Times New Roman",
        "Verdana", "Calibri", "Calibri Light", "Arial", "Arabic Typesetting", "Cambria" };

    /// <summary>
    /// The fonts' offset factors used for position of subscript glyphs.
    /// </summary>
    private static HashMap<String, Float> gFontSubscriptOffsetFactors;

    /// <summary>
    /// Default offset factor used to position subscript glyphs.
    /// </summary>
    private static final float DEFAULT_FONT_SUBSCRIPT_OFFSET_FACTOR = 0.15f;

    /// <summary>
    /// Default offset factor used to position superscript glyphs.
    /// </summary>
    private static final float DEFAULT_FONT_SUPERSCRIPT_OFFSET_FACTOR = -0.303f;

    private static /*final*/ Object G_LOCK_SUBSCRIPT_FACTOR = new Object();

    private static /*final*/ IntToIntDictionary G_CHARSET_FROM_CODE_PAGE = new IntToIntDictionary();
    private static /*final*/ StringToIntBidirectionalMap G_CHARSET_IANA_NAME_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntDictionary G_SUFFIX_CHARSET_MAP = new StringToIntDictionary();


    /// <summary>
    /// Returns trimmed family name which suits GDI+ family name parsing behavior.
    /// </summary>
    public static String trimFamilyNameForGdi(String familyName)
    {
        final int MAX_GDI_FAMILY_NAME_LENGTH = 31;
        return familyName.length() <= MAX_GDI_FAMILY_NAME_LENGTH
            ? familyName
            : familyName.substring((0), (0) + (MAX_GDI_FAMILY_NAME_LENGTH));
    }

    /// <summary>
    /// Initializes a dictionary that contains the offset factors for positioning subscript glyphs.
    /// </summary>
    private static void initializeSubscriptFactors()
    {
        if (gFontSubscriptOffsetFactors == null)
        {
            synchronized (G_LOCK_SUBSCRIPT_FACTOR)
            {
                if (gFontSubscriptOffsetFactors == null)
                {
                    gFontSubscriptOffsetFactors = new HashMap<String, Float>();
                    msDictionary.add(gFontSubscriptOffsetFactors, "Arial", 0.07f);
                    msDictionary.add(gFontSubscriptOffsetFactors, "Courier New", 0.105f);
                    msDictionary.add(gFontSubscriptOffsetFactors, "SimSun", 0.065f);
                    msDictionary.add(gFontSubscriptOffsetFactors, "Calibri", 0.1f);
                }
            }
        }
    }

    public static StringToStringDictionary getFontSubstitutesCache() { return gFontSubstitutesCache; }

    private static void fillFontSubstitutesCache()
    {
        gFontSubstitutesCache = new StringToStringDictionary(false);

        // Check the well known font substitutes here. Actually MW uses registry keys for this purpose but
        // predefined table seems to be OK.
        // HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows NT\CurrentVersion\FontSubstitutes
        // Also some of the substitutes from registry are set by default in TableSubstitutionRule.
        // The difference is that substitutes from here are checked before embedded font and AltName
        // and substitute from TableSubstitutionRule are checked after.
        gFontSubstitutesCache.add("Courier", "Courier New");
        gFontSubstitutesCache.add("David Transparent", "David");
        gFontSubstitutesCache.add("Fixed Miriam Transparent", "Miriam Fixed");
        gFontSubstitutesCache.add("Miriam Transparent", "Miriam");
        gFontSubstitutesCache.add("Rod Transparent", "Rod");
        gFontSubstitutesCache.add("Times", "Times New Roman");
    }

    private static StringToStringDictionary gFontSubstitutesCache;

    /// <summary>
    /// Limits size of data that should be encoded with MTX algorithm (<see cref="MtxCoder.Encode"/>).
    /// </summary>
    /// <remarks>
    /// IN. MTX encoding algorithm is quite slow due to the LZCOMP encoding (<see cref="LzCompEncoder"/>).
    /// And it looks like MS Word limits the size of data that is encoded with MTX algorithm to 4 Mb (found experimentally).
    /// If size is greater than 4 Mb, MS Word performs XOR encoding (<see cref="EotFontFiler.XorFontData"/>) only.
    /// </remarks>
    public static final int MTX_ENCODE_SIZE_LIMIT = 4 * (1024 * 1024); // 4 Mb.

    /// <summary>
    /// Limits size of font that can be collected without subsetting.
    /// </summary>
    /// <remarks>
    /// IN. It seems, MS Word limits the font size for full embedding. If font size exceeds this value,
    /// it embeds only used characters, despite a save option is set to 'FullEmbedding'.
    /// This value is found experimentally, and it is something like <see cref="MtxEncodeSizeLimit"/>.
    /// </remarks>
    public static final int FULL_EMBED_SIZE_LIMIT = 4 * (1024 * 1024); // 4 Mb.

    /// <summary>
    /// The collection of well-known EastAsian fonts.
    /// </summary>
    private static /*final*/ String[] G_EAST_ASIAN_FONTS = new String[]
        { "ＭＳ ゴシック", "맑은 고딕", "宋体", "新細明體", "ＭＳ 明朝", "SimSun" };

    static/* FontUtil()*/
    {
        fillCharsetFromCodePageMap();
        fillCharsetIanaNameMap();
        fillSuffixCharsetMap();
        fillFontSubstitutesCache();
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"BATANG",
		"明朝"
	);


}

