// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// All Dml paragraph level text properties enum.
/// </summary>
/*enum*/ final class DmlParagraphPropertiesIds
{
    private DmlParagraphPropertiesIds(){}
    
    public static final int LEVEL = 0;
    public static final int ALIGNMENT = 1;
    public static final int DEFAULT_TAB_SIZE = 2;
    public static final int IS_EAST_ASIAN_LINE_BREAK_ALLOWED = 3;
    public static final int FONT_ALIGNMENT = 4;
    public static final int IS_HANGING_PUNCTUATION_ALLOWED = 5;
    public static final int TEXT_IDENTATION = 6;
    public static final int IS_LATIN_LINE_BREAK_ALLOWED = 7;
    public static final int LEFT_MARGIN = 8;
    public static final int RIGHT_MARGIN = 9;
    public static final int RIGHT_TO_LEFT_FLOW_DIRECTION = 10;
    public static final int DEFAULT_RUN_PROPERTIES = 11;
    public static final int LINE_SPACING = 12;
    public static final int SPACE_AFTER = 13;
    public static final int SPACE_BEFORE = 14;
    public static final int BULLET = 15;
    public static final int BULLET_SIZE = 16;
    public static final int BULLET_COLOR = 17;
    public static final int BULLET_FONT = 18;
    public static final int TAB_LIST = 19;
    public static final int EXTENSIONS = 20;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlParagraphPropertiesIds value.
    /// </summary>
    public static String getName(/*DmlParagraphPropertiesIds*/int dmlParagraphPropertiesIds)
    {
    	switch (dmlParagraphPropertiesIds)
    	{
    		case LEVEL: return "LEVEL";
    		case ALIGNMENT: return "ALIGNMENT";
    		case DEFAULT_TAB_SIZE: return "DEFAULT_TAB_SIZE";
    		case IS_EAST_ASIAN_LINE_BREAK_ALLOWED: return "IS_EAST_ASIAN_LINE_BREAK_ALLOWED";
    		case FONT_ALIGNMENT: return "FONT_ALIGNMENT";
    		case IS_HANGING_PUNCTUATION_ALLOWED: return "IS_HANGING_PUNCTUATION_ALLOWED";
    		case TEXT_IDENTATION: return "TEXT_IDENTATION";
    		case IS_LATIN_LINE_BREAK_ALLOWED: return "IS_LATIN_LINE_BREAK_ALLOWED";
    		case LEFT_MARGIN: return "LEFT_MARGIN";
    		case RIGHT_MARGIN: return "RIGHT_MARGIN";
    		case RIGHT_TO_LEFT_FLOW_DIRECTION: return "RIGHT_TO_LEFT_FLOW_DIRECTION";
    		case DEFAULT_RUN_PROPERTIES: return "DEFAULT_RUN_PROPERTIES";
    		case LINE_SPACING: return "LINE_SPACING";
    		case SPACE_AFTER: return "SPACE_AFTER";
    		case SPACE_BEFORE: return "SPACE_BEFORE";
    		case BULLET: return "BULLET";
    		case BULLET_SIZE: return "BULLET_SIZE";
    		case BULLET_COLOR: return "BULLET_COLOR";
    		case BULLET_FONT: return "BULLET_FONT";
    		case TAB_LIST: return "TAB_LIST";
    		case EXTENSIONS: return "EXTENSIONS";
    		default: return "Unknown DmlParagraphPropertiesIds value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlParagraphPropertiesIds value.
    /// </summary>
    public static String toString(/*DmlParagraphPropertiesIds*/int dmlParagraphPropertiesIds)
    {
    	switch (dmlParagraphPropertiesIds)
    	{
    		case LEVEL: return "Level";
    		case ALIGNMENT: return "Alignment";
    		case DEFAULT_TAB_SIZE: return "DefaultTabSize";
    		case IS_EAST_ASIAN_LINE_BREAK_ALLOWED: return "IsEastAsianLineBreakAllowed";
    		case FONT_ALIGNMENT: return "FontAlignment";
    		case IS_HANGING_PUNCTUATION_ALLOWED: return "IsHangingPunctuationAllowed";
    		case TEXT_IDENTATION: return "TextIdentation";
    		case IS_LATIN_LINE_BREAK_ALLOWED: return "IsLatinLineBreakAllowed";
    		case LEFT_MARGIN: return "LeftMargin";
    		case RIGHT_MARGIN: return "RightMargin";
    		case RIGHT_TO_LEFT_FLOW_DIRECTION: return "RightToLeftFlowDirection";
    		case DEFAULT_RUN_PROPERTIES: return "DefaultRunProperties";
    		case LINE_SPACING: return "LineSpacing";
    		case SPACE_AFTER: return "SpaceAfter";
    		case SPACE_BEFORE: return "SpaceBefore";
    		case BULLET: return "Bullet";
    		case BULLET_SIZE: return "BulletSize";
    		case BULLET_COLOR: return "BulletColor";
    		case BULLET_FONT: return "BulletFont";
    		case TAB_LIST: return "TabList";
    		case EXTENSIONS: return "Extensions";
    		default: return "Unknown DmlParagraphPropertiesIds value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlParagraphPropertiesIds name.
    /// </summary>
    public static /*DmlParagraphPropertiesIds*/int fromName(String dmlParagraphPropertiesIdsName)
    {
    	if ("LEVEL".equals(dmlParagraphPropertiesIdsName)) return LEVEL;
    	if ("ALIGNMENT".equals(dmlParagraphPropertiesIdsName)) return ALIGNMENT;
    	if ("DEFAULT_TAB_SIZE".equals(dmlParagraphPropertiesIdsName)) return DEFAULT_TAB_SIZE;
    	if ("IS_EAST_ASIAN_LINE_BREAK_ALLOWED".equals(dmlParagraphPropertiesIdsName)) return IS_EAST_ASIAN_LINE_BREAK_ALLOWED;
    	if ("FONT_ALIGNMENT".equals(dmlParagraphPropertiesIdsName)) return FONT_ALIGNMENT;
    	if ("IS_HANGING_PUNCTUATION_ALLOWED".equals(dmlParagraphPropertiesIdsName)) return IS_HANGING_PUNCTUATION_ALLOWED;
    	if ("TEXT_IDENTATION".equals(dmlParagraphPropertiesIdsName)) return TEXT_IDENTATION;
    	if ("IS_LATIN_LINE_BREAK_ALLOWED".equals(dmlParagraphPropertiesIdsName)) return IS_LATIN_LINE_BREAK_ALLOWED;
    	if ("LEFT_MARGIN".equals(dmlParagraphPropertiesIdsName)) return LEFT_MARGIN;
    	if ("RIGHT_MARGIN".equals(dmlParagraphPropertiesIdsName)) return RIGHT_MARGIN;
    	if ("RIGHT_TO_LEFT_FLOW_DIRECTION".equals(dmlParagraphPropertiesIdsName)) return RIGHT_TO_LEFT_FLOW_DIRECTION;
    	if ("DEFAULT_RUN_PROPERTIES".equals(dmlParagraphPropertiesIdsName)) return DEFAULT_RUN_PROPERTIES;
    	if ("LINE_SPACING".equals(dmlParagraphPropertiesIdsName)) return LINE_SPACING;
    	if ("SPACE_AFTER".equals(dmlParagraphPropertiesIdsName)) return SPACE_AFTER;
    	if ("SPACE_BEFORE".equals(dmlParagraphPropertiesIdsName)) return SPACE_BEFORE;
    	if ("BULLET".equals(dmlParagraphPropertiesIdsName)) return BULLET;
    	if ("BULLET_SIZE".equals(dmlParagraphPropertiesIdsName)) return BULLET_SIZE;
    	if ("BULLET_COLOR".equals(dmlParagraphPropertiesIdsName)) return BULLET_COLOR;
    	if ("BULLET_FONT".equals(dmlParagraphPropertiesIdsName)) return BULLET_FONT;
    	if ("TAB_LIST".equals(dmlParagraphPropertiesIdsName)) return TAB_LIST;
    	if ("EXTENSIONS".equals(dmlParagraphPropertiesIdsName)) return EXTENSIONS;
    	throw new IllegalArgumentException("Unknown DmlParagraphPropertiesIds name.");
    }
    
    /// <summary>
    /// Returns array of DmlParagraphPropertiesIds values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEVEL,
    		ALIGNMENT,
    		DEFAULT_TAB_SIZE,
    		IS_EAST_ASIAN_LINE_BREAK_ALLOWED,
    		FONT_ALIGNMENT,
    		IS_HANGING_PUNCTUATION_ALLOWED,
    		TEXT_IDENTATION,
    		IS_LATIN_LINE_BREAK_ALLOWED,
    		LEFT_MARGIN,
    		RIGHT_MARGIN,
    		RIGHT_TO_LEFT_FLOW_DIRECTION,
    		DEFAULT_RUN_PROPERTIES,
    		LINE_SPACING,
    		SPACE_AFTER,
    		SPACE_BEFORE,
    		BULLET,
    		BULLET_SIZE,
    		BULLET_COLOR,
    		BULLET_FONT,
    		TAB_LIST,
    		EXTENSIONS,
    	};
    }

    public static final int length = 21;
}
