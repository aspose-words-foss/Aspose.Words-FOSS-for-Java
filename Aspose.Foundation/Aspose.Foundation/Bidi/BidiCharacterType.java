// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Bidi classes of characters, the Bidi_Class property of the Unicode Character Database.
/// </summary>
/// <remarks>
/// The names are the short property value names used by UnicodeData.txt and by the Unicode bidirectional algorithm
/// (https://www.unicode.org/reports/tr9/, Table 4). Only the classes of Unicode 5.1 are listed.
/// </remarks>
public /*enum*/ final class BidiCharacterType
{
    private BidiCharacterType(){}
    
    /// <summary>
    /// Strong left-to-right: most letters of left-to-right scripts, LEFT-TO-RIGHT MARK.
    /// </summary>
    public static final int L = 0;

    /// <summary>
    /// Strong right-to-left: Hebrew and other right-to-left scripts, RIGHT-TO-LEFT MARK.
    /// </summary>
    public static final int R = 1;

    /// <summary>
    /// Strong right-to-left Arabic letter: Arabic, Syriac, Thaana.
    /// </summary>
    public static final int AL = 2;

    /// <summary>
    /// European number: European digits, Eastern Arabic-Indic digits.
    /// </summary>
    public static final int EN = 3;

    /// <summary>
    /// European number separator: plus and minus signs.
    /// </summary>
    public static final int ES = 4;

    /// <summary>
    /// European number terminator: currency symbols, percent and degree signs.
    /// </summary>
    public static final int ET = 5;

    /// <summary>
    /// Arabic number: Arabic-Indic digits, Arabic decimal and thousands separators.
    /// </summary>
    public static final int AN = 6;

    /// <summary>
    /// Common number separator: colon, comma, full stop, no-break space.
    /// </summary>
    public static final int CS = 7;

    /// <summary>
    /// Nonspacing mark: characters of general category Mn and Me.
    /// </summary>
    public static final int NSM = 8;

    /// <summary>
    /// Boundary neutral: default ignorable characters such as SOFT HYPHEN and ZERO WIDTH SPACE, control characters.
    /// </summary>
    public static final int BN = 9;

    /// <summary>
    /// Paragraph separator: line feed, carriage return, PARAGRAPH SEPARATOR.
    /// </summary>
    public static final int B = 10;

    /// <summary>
    /// Segment separator: tab.
    /// </summary>
    public static final int S = 11;

    /// <summary>
    /// Whitespace: space, form feed, LINE SEPARATOR.
    /// </summary>
    public static final int WS = 12;

    /// <summary>
    /// Other neutral: most punctuation and symbols.
    /// </summary>
    public static final int ON = 13;

    /// <summary>
    /// LEFT-TO-RIGHT EMBEDDING.
    /// </summary>
    public static final int LRE = 14;

    /// <summary>
    /// LEFT-TO-RIGHT OVERRIDE.
    /// </summary>
    public static final int LRO = 15;

    /// <summary>
    /// RIGHT-TO-LEFT EMBEDDING.
    /// </summary>
    public static final int RLE = 16;

    /// <summary>
    /// RIGHT-TO-LEFT OVERRIDE.
    /// </summary>
    public static final int RLO = 17;

    /// <summary>
    /// POP DIRECTIONAL FORMATTING.
    /// </summary>
    public static final int PDF = 18;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer BidiCharacterType value.
    /// </summary>
    public static String getName(/*BidiCharacterType*/int bidiCharacterType)
    {
    	switch (bidiCharacterType)
    	{
    		case L: return "L";
    		case R: return "R";
    		case AL: return "AL";
    		case EN: return "EN";
    		case ES: return "ES";
    		case ET: return "ET";
    		case AN: return "AN";
    		case CS: return "CS";
    		case NSM: return "NSM";
    		case BN: return "BN";
    		case B: return "B";
    		case S: return "S";
    		case WS: return "WS";
    		case ON: return "ON";
    		case LRE: return "LRE";
    		case LRO: return "LRO";
    		case RLE: return "RLE";
    		case RLO: return "RLO";
    		case PDF: return "PDF";
    		default: return "Unknown BidiCharacterType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer BidiCharacterType value.
    /// </summary>
    public static String toString(/*BidiCharacterType*/int bidiCharacterType)
    {
    	switch (bidiCharacterType)
    	{
    		case L: return "L";
    		case R: return "R";
    		case AL: return "AL";
    		case EN: return "EN";
    		case ES: return "ES";
    		case ET: return "ET";
    		case AN: return "AN";
    		case CS: return "CS";
    		case NSM: return "NSM";
    		case BN: return "BN";
    		case B: return "B";
    		case S: return "S";
    		case WS: return "WS";
    		case ON: return "ON";
    		case LRE: return "LRE";
    		case LRO: return "LRO";
    		case RLE: return "RLE";
    		case RLO: return "RLO";
    		case PDF: return "PDF";
    		default: return "Unknown BidiCharacterType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by BidiCharacterType name.
    /// </summary>
    public static /*BidiCharacterType*/int fromName(String bidiCharacterTypeName)
    {
    	if ("L".equals(bidiCharacterTypeName)) return L;
    	if ("R".equals(bidiCharacterTypeName)) return R;
    	if ("AL".equals(bidiCharacterTypeName)) return AL;
    	if ("EN".equals(bidiCharacterTypeName)) return EN;
    	if ("ES".equals(bidiCharacterTypeName)) return ES;
    	if ("ET".equals(bidiCharacterTypeName)) return ET;
    	if ("AN".equals(bidiCharacterTypeName)) return AN;
    	if ("CS".equals(bidiCharacterTypeName)) return CS;
    	if ("NSM".equals(bidiCharacterTypeName)) return NSM;
    	if ("BN".equals(bidiCharacterTypeName)) return BN;
    	if ("B".equals(bidiCharacterTypeName)) return B;
    	if ("S".equals(bidiCharacterTypeName)) return S;
    	if ("WS".equals(bidiCharacterTypeName)) return WS;
    	if ("ON".equals(bidiCharacterTypeName)) return ON;
    	if ("LRE".equals(bidiCharacterTypeName)) return LRE;
    	if ("LRO".equals(bidiCharacterTypeName)) return LRO;
    	if ("RLE".equals(bidiCharacterTypeName)) return RLE;
    	if ("RLO".equals(bidiCharacterTypeName)) return RLO;
    	if ("PDF".equals(bidiCharacterTypeName)) return PDF;
    	throw new IllegalArgumentException("Unknown BidiCharacterType name.");
    }
    
    /// <summary>
    /// Returns array of BidiCharacterType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		L,
    		R,
    		AL,
    		EN,
    		ES,
    		ET,
    		AN,
    		CS,
    		NSM,
    		BN,
    		B,
    		S,
    		WS,
    		ON,
    		LRE,
    		LRO,
    		RLE,
    		RLO,
    		PDF,
    	};
    }

    public static final int length = 19;
}

