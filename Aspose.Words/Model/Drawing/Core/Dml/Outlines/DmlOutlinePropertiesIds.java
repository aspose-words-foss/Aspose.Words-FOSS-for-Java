// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/01/2015 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// All Dml outline level text properties enum.
/// </summary>
/*enum*/ final class DmlOutlinePropertiesIds
{
    private DmlOutlinePropertiesIds(){}
    
    public static final int WIDTH_IN_EMUS = 0;
    public static final int LINE_MITER_LIMIT = 1;
    public static final int COMPOUND_LINE_TYPE = 2;
    public static final int LINE_ENDING_CAP_TYPE = 3;
    public static final int STROKE_ALIGNMENT = 4;
    public static final int DASH = 5;
    public static final int LINE_JOIN_STYLE = 6;
    public static final int HEAD_LINE_END_STYLE = 7;
    public static final int TAIL_LINE_END_STYLE = 8;
    public static final int FILL = 9;
    public static final int EXTENSIONS = 10;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlOutlinePropertiesIds value.
    /// </summary>
    public static String getName(/*DmlOutlinePropertiesIds*/int dmlOutlinePropertiesIds)
    {
    	switch (dmlOutlinePropertiesIds)
    	{
    		case WIDTH_IN_EMUS: return "WIDTH_IN_EMUS";
    		case LINE_MITER_LIMIT: return "LINE_MITER_LIMIT";
    		case COMPOUND_LINE_TYPE: return "COMPOUND_LINE_TYPE";
    		case LINE_ENDING_CAP_TYPE: return "LINE_ENDING_CAP_TYPE";
    		case STROKE_ALIGNMENT: return "STROKE_ALIGNMENT";
    		case DASH: return "DASH";
    		case LINE_JOIN_STYLE: return "LINE_JOIN_STYLE";
    		case HEAD_LINE_END_STYLE: return "HEAD_LINE_END_STYLE";
    		case TAIL_LINE_END_STYLE: return "TAIL_LINE_END_STYLE";
    		case FILL: return "FILL";
    		case EXTENSIONS: return "EXTENSIONS";
    		default: return "Unknown DmlOutlinePropertiesIds value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlOutlinePropertiesIds value.
    /// </summary>
    public static String toString(/*DmlOutlinePropertiesIds*/int dmlOutlinePropertiesIds)
    {
    	switch (dmlOutlinePropertiesIds)
    	{
    		case WIDTH_IN_EMUS: return "WidthInEmus";
    		case LINE_MITER_LIMIT: return "LineMiterLimit";
    		case COMPOUND_LINE_TYPE: return "CompoundLineType";
    		case LINE_ENDING_CAP_TYPE: return "LineEndingCapType";
    		case STROKE_ALIGNMENT: return "StrokeAlignment";
    		case DASH: return "Dash";
    		case LINE_JOIN_STYLE: return "LineJoinStyle";
    		case HEAD_LINE_END_STYLE: return "HeadLineEndStyle";
    		case TAIL_LINE_END_STYLE: return "TailLineEndStyle";
    		case FILL: return "Fill";
    		case EXTENSIONS: return "Extensions";
    		default: return "Unknown DmlOutlinePropertiesIds value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlOutlinePropertiesIds name.
    /// </summary>
    public static /*DmlOutlinePropertiesIds*/int fromName(String dmlOutlinePropertiesIdsName)
    {
    	if ("WIDTH_IN_EMUS".equals(dmlOutlinePropertiesIdsName)) return WIDTH_IN_EMUS;
    	if ("LINE_MITER_LIMIT".equals(dmlOutlinePropertiesIdsName)) return LINE_MITER_LIMIT;
    	if ("COMPOUND_LINE_TYPE".equals(dmlOutlinePropertiesIdsName)) return COMPOUND_LINE_TYPE;
    	if ("LINE_ENDING_CAP_TYPE".equals(dmlOutlinePropertiesIdsName)) return LINE_ENDING_CAP_TYPE;
    	if ("STROKE_ALIGNMENT".equals(dmlOutlinePropertiesIdsName)) return STROKE_ALIGNMENT;
    	if ("DASH".equals(dmlOutlinePropertiesIdsName)) return DASH;
    	if ("LINE_JOIN_STYLE".equals(dmlOutlinePropertiesIdsName)) return LINE_JOIN_STYLE;
    	if ("HEAD_LINE_END_STYLE".equals(dmlOutlinePropertiesIdsName)) return HEAD_LINE_END_STYLE;
    	if ("TAIL_LINE_END_STYLE".equals(dmlOutlinePropertiesIdsName)) return TAIL_LINE_END_STYLE;
    	if ("FILL".equals(dmlOutlinePropertiesIdsName)) return FILL;
    	if ("EXTENSIONS".equals(dmlOutlinePropertiesIdsName)) return EXTENSIONS;
    	throw new IllegalArgumentException("Unknown DmlOutlinePropertiesIds name.");
    }
    
    /// <summary>
    /// Returns array of DmlOutlinePropertiesIds values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		WIDTH_IN_EMUS,
    		LINE_MITER_LIMIT,
    		COMPOUND_LINE_TYPE,
    		LINE_ENDING_CAP_TYPE,
    		STROKE_ALIGNMENT,
    		DASH,
    		LINE_JOIN_STYLE,
    		HEAD_LINE_END_STYLE,
    		TAIL_LINE_END_STYLE,
    		FILL,
    		EXTENSIONS,
    	};
    }

    public static final int length = 11;
}

