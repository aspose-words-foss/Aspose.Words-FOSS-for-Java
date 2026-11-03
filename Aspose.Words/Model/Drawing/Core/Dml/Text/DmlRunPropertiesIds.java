// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class DmlRunPropertiesIds
{
    private DmlRunPropertiesIds(){}
    
    public static final int ALTERNATIVE_LANGUAGE = 0;
    public static final int BOLD = 1;
    public static final int BASELINE = 2;
    public static final int BOOKMARK_LINK_TARGET = 3;
    public static final int CAPITALIZATION = 4;
    public static final int IS_DIRTY = 5;
    public static final int HAS_SPELLING_ERROR = 6;
    public static final int ITALICS = 7;
    public static final int KERNING = 8;
    public static final int KUMIMOJI = 9;
    public static final int LANGUAGE = 10;
    public static final int NO_PROOFING = 11;
    public static final int NORMALIZE_HEIGHTS = 12;
    public static final int SMART_TAGS_CLEAN = 13;
    public static final int SMART_TAG_ID = 14;
    public static final int SPACING = 15;
    public static final int STRIKETHROUGH = 16;
    public static final int FONT_SIZE = 17;
    public static final int UNDERLINE = 18;
    public static final int FILL = 19;
    public static final int OUTLINE = 20;
    public static final int HIGHLIGHT_COLOR = 21;
    public static final int LATIN_FONT = 22;
    public static final int EAST_ASIAN_FONT = 23;
    public static final int SYMBOL_FONT = 24;
    public static final int COMPLEX_SCRIPT_FONT = 25;
    public static final int HLINK_CLICK = 26;
    public static final int HLINK_HOVER = 27;
    public static final int RTL = 28;
    public static final int UNDERLINE_FILL = 29;
    public static final int UNDERLINE_FILL_TX = 30;
    public static final int UNDERLINE_STROKE = 31;
    public static final int UNDERLINE_STROKE_TX = 32;
    public static final int RIGHT_TO_LEFT_FLOW_DIRECTION = 33;
    public static final int EXTENSIONS = 34;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlRunPropertiesIds value.
    /// </summary>
    public static String getName(/*DmlRunPropertiesIds*/int dmlRunPropertiesIds)
    {
    	switch (dmlRunPropertiesIds)
    	{
    		case ALTERNATIVE_LANGUAGE: return "ALTERNATIVE_LANGUAGE";
    		case BOLD: return "BOLD";
    		case BASELINE: return "BASELINE";
    		case BOOKMARK_LINK_TARGET: return "BOOKMARK_LINK_TARGET";
    		case CAPITALIZATION: return "CAPITALIZATION";
    		case IS_DIRTY: return "IS_DIRTY";
    		case HAS_SPELLING_ERROR: return "HAS_SPELLING_ERROR";
    		case ITALICS: return "ITALICS";
    		case KERNING: return "KERNING";
    		case KUMIMOJI: return "KUMIMOJI";
    		case LANGUAGE: return "LANGUAGE";
    		case NO_PROOFING: return "NO_PROOFING";
    		case NORMALIZE_HEIGHTS: return "NORMALIZE_HEIGHTS";
    		case SMART_TAGS_CLEAN: return "SMART_TAGS_CLEAN";
    		case SMART_TAG_ID: return "SMART_TAG_ID";
    		case SPACING: return "SPACING";
    		case STRIKETHROUGH: return "STRIKETHROUGH";
    		case FONT_SIZE: return "FONT_SIZE";
    		case UNDERLINE: return "UNDERLINE";
    		case FILL: return "FILL";
    		case OUTLINE: return "OUTLINE";
    		case HIGHLIGHT_COLOR: return "HIGHLIGHT_COLOR";
    		case LATIN_FONT: return "LATIN_FONT";
    		case EAST_ASIAN_FONT: return "EAST_ASIAN_FONT";
    		case SYMBOL_FONT: return "SYMBOL_FONT";
    		case COMPLEX_SCRIPT_FONT: return "COMPLEX_SCRIPT_FONT";
    		case HLINK_CLICK: return "HLINK_CLICK";
    		case HLINK_HOVER: return "HLINK_HOVER";
    		case RTL: return "RTL";
    		case UNDERLINE_FILL: return "UNDERLINE_FILL";
    		case UNDERLINE_FILL_TX: return "UNDERLINE_FILL_TX";
    		case UNDERLINE_STROKE: return "UNDERLINE_STROKE";
    		case UNDERLINE_STROKE_TX: return "UNDERLINE_STROKE_TX";
    		case RIGHT_TO_LEFT_FLOW_DIRECTION: return "RIGHT_TO_LEFT_FLOW_DIRECTION";
    		case EXTENSIONS: return "EXTENSIONS";
    		default: return "Unknown DmlRunPropertiesIds value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlRunPropertiesIds value.
    /// </summary>
    public static String toString(/*DmlRunPropertiesIds*/int dmlRunPropertiesIds)
    {
    	switch (dmlRunPropertiesIds)
    	{
    		case ALTERNATIVE_LANGUAGE: return "AlternativeLanguage";
    		case BOLD: return "Bold";
    		case BASELINE: return "Baseline";
    		case BOOKMARK_LINK_TARGET: return "BookmarkLinkTarget";
    		case CAPITALIZATION: return "Capitalization";
    		case IS_DIRTY: return "IsDirty";
    		case HAS_SPELLING_ERROR: return "HasSpellingError";
    		case ITALICS: return "Italics";
    		case KERNING: return "Kerning";
    		case KUMIMOJI: return "Kumimoji";
    		case LANGUAGE: return "Language";
    		case NO_PROOFING: return "NoProofing";
    		case NORMALIZE_HEIGHTS: return "NormalizeHeights";
    		case SMART_TAGS_CLEAN: return "SmartTagsClean";
    		case SMART_TAG_ID: return "SmartTagID";
    		case SPACING: return "Spacing";
    		case STRIKETHROUGH: return "Strikethrough";
    		case FONT_SIZE: return "FontSize";
    		case UNDERLINE: return "Underline";
    		case FILL: return "Fill";
    		case OUTLINE: return "Outline";
    		case HIGHLIGHT_COLOR: return "HighlightColor";
    		case LATIN_FONT: return "LatinFont";
    		case EAST_ASIAN_FONT: return "EastAsianFont";
    		case SYMBOL_FONT: return "SymbolFont";
    		case COMPLEX_SCRIPT_FONT: return "ComplexScriptFont";
    		case HLINK_CLICK: return "HlinkClick";
    		case HLINK_HOVER: return "HlinkHover";
    		case RTL: return "Rtl";
    		case UNDERLINE_FILL: return "UnderlineFill";
    		case UNDERLINE_FILL_TX: return "UnderlineFillTx";
    		case UNDERLINE_STROKE: return "UnderlineStroke";
    		case UNDERLINE_STROKE_TX: return "UnderlineStrokeTx";
    		case RIGHT_TO_LEFT_FLOW_DIRECTION: return "RightToLeftFlowDirection";
    		case EXTENSIONS: return "Extensions";
    		default: return "Unknown DmlRunPropertiesIds value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlRunPropertiesIds name.
    /// </summary>
    public static /*DmlRunPropertiesIds*/int fromName(String dmlRunPropertiesIdsName)
    {
    	if ("ALTERNATIVE_LANGUAGE".equals(dmlRunPropertiesIdsName)) return ALTERNATIVE_LANGUAGE;
    	if ("BOLD".equals(dmlRunPropertiesIdsName)) return BOLD;
    	if ("BASELINE".equals(dmlRunPropertiesIdsName)) return BASELINE;
    	if ("BOOKMARK_LINK_TARGET".equals(dmlRunPropertiesIdsName)) return BOOKMARK_LINK_TARGET;
    	if ("CAPITALIZATION".equals(dmlRunPropertiesIdsName)) return CAPITALIZATION;
    	if ("IS_DIRTY".equals(dmlRunPropertiesIdsName)) return IS_DIRTY;
    	if ("HAS_SPELLING_ERROR".equals(dmlRunPropertiesIdsName)) return HAS_SPELLING_ERROR;
    	if ("ITALICS".equals(dmlRunPropertiesIdsName)) return ITALICS;
    	if ("KERNING".equals(dmlRunPropertiesIdsName)) return KERNING;
    	if ("KUMIMOJI".equals(dmlRunPropertiesIdsName)) return KUMIMOJI;
    	if ("LANGUAGE".equals(dmlRunPropertiesIdsName)) return LANGUAGE;
    	if ("NO_PROOFING".equals(dmlRunPropertiesIdsName)) return NO_PROOFING;
    	if ("NORMALIZE_HEIGHTS".equals(dmlRunPropertiesIdsName)) return NORMALIZE_HEIGHTS;
    	if ("SMART_TAGS_CLEAN".equals(dmlRunPropertiesIdsName)) return SMART_TAGS_CLEAN;
    	if ("SMART_TAG_ID".equals(dmlRunPropertiesIdsName)) return SMART_TAG_ID;
    	if ("SPACING".equals(dmlRunPropertiesIdsName)) return SPACING;
    	if ("STRIKETHROUGH".equals(dmlRunPropertiesIdsName)) return STRIKETHROUGH;
    	if ("FONT_SIZE".equals(dmlRunPropertiesIdsName)) return FONT_SIZE;
    	if ("UNDERLINE".equals(dmlRunPropertiesIdsName)) return UNDERLINE;
    	if ("FILL".equals(dmlRunPropertiesIdsName)) return FILL;
    	if ("OUTLINE".equals(dmlRunPropertiesIdsName)) return OUTLINE;
    	if ("HIGHLIGHT_COLOR".equals(dmlRunPropertiesIdsName)) return HIGHLIGHT_COLOR;
    	if ("LATIN_FONT".equals(dmlRunPropertiesIdsName)) return LATIN_FONT;
    	if ("EAST_ASIAN_FONT".equals(dmlRunPropertiesIdsName)) return EAST_ASIAN_FONT;
    	if ("SYMBOL_FONT".equals(dmlRunPropertiesIdsName)) return SYMBOL_FONT;
    	if ("COMPLEX_SCRIPT_FONT".equals(dmlRunPropertiesIdsName)) return COMPLEX_SCRIPT_FONT;
    	if ("HLINK_CLICK".equals(dmlRunPropertiesIdsName)) return HLINK_CLICK;
    	if ("HLINK_HOVER".equals(dmlRunPropertiesIdsName)) return HLINK_HOVER;
    	if ("RTL".equals(dmlRunPropertiesIdsName)) return RTL;
    	if ("UNDERLINE_FILL".equals(dmlRunPropertiesIdsName)) return UNDERLINE_FILL;
    	if ("UNDERLINE_FILL_TX".equals(dmlRunPropertiesIdsName)) return UNDERLINE_FILL_TX;
    	if ("UNDERLINE_STROKE".equals(dmlRunPropertiesIdsName)) return UNDERLINE_STROKE;
    	if ("UNDERLINE_STROKE_TX".equals(dmlRunPropertiesIdsName)) return UNDERLINE_STROKE_TX;
    	if ("RIGHT_TO_LEFT_FLOW_DIRECTION".equals(dmlRunPropertiesIdsName)) return RIGHT_TO_LEFT_FLOW_DIRECTION;
    	if ("EXTENSIONS".equals(dmlRunPropertiesIdsName)) return EXTENSIONS;
    	throw new IllegalArgumentException("Unknown DmlRunPropertiesIds name.");
    }
    
    /// <summary>
    /// Returns array of DmlRunPropertiesIds values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ALTERNATIVE_LANGUAGE,
    		BOLD,
    		BASELINE,
    		BOOKMARK_LINK_TARGET,
    		CAPITALIZATION,
    		IS_DIRTY,
    		HAS_SPELLING_ERROR,
    		ITALICS,
    		KERNING,
    		KUMIMOJI,
    		LANGUAGE,
    		NO_PROOFING,
    		NORMALIZE_HEIGHTS,
    		SMART_TAGS_CLEAN,
    		SMART_TAG_ID,
    		SPACING,
    		STRIKETHROUGH,
    		FONT_SIZE,
    		UNDERLINE,
    		FILL,
    		OUTLINE,
    		HIGHLIGHT_COLOR,
    		LATIN_FONT,
    		EAST_ASIAN_FONT,
    		SYMBOL_FONT,
    		COMPLEX_SCRIPT_FONT,
    		HLINK_CLICK,
    		HLINK_HOVER,
    		RTL,
    		UNDERLINE_FILL,
    		UNDERLINE_FILL_TX,
    		UNDERLINE_STROKE,
    		UNDERLINE_STROKE_TX,
    		RIGHT_TO_LEFT_FLOW_DIRECTION,
    		EXTENSIONS,
    	};
    }

    public static final int length = 35;
}
