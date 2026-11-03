// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/08/2009 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/*enum*/ final class RunPrExpandFlags
{
    private RunPrExpandFlags(){}
    
    /// <summary>
    /// Performs the normal "plain" expansion of attributes.
    /// </summary>
    public static final int NORMAL = 0x0000;

    /// <summary>
    /// Set to expand default run properties stored for this document.
    /// </summary>
    public static final int DOCUMENT_DEFAULTS = 0x0001;

    /// <summary>
    /// In some cases MS Word ignores font size 10 and 12 specified on Normal paragraph style
    /// when text is inside a table.
    /// </summary>
    /// <remarks>This flag is used internally when expanding attribute collection. Do NOT use it anywhere else.</remarks>
    // See https://msdn.microsoft.com/en-us/library/dd944611%28v=office.12%29.aspx?f=255&MSPPError=-2147217396
    public static final int IGNORE_NORMAL_FONT_SIZE = 0x0002;

    /// <summary>
    /// This is requested during layout for runs that are inside a TOC field. They are likely to have the
    /// Hyperlink style applied, but MS Word outputs them as if it was not applied and we have to do the same.
    /// </summary>
    public static final int IGNORE_HYPERLINK_CHAR_STYLE = 0x0004;

    /// <summary>
    /// This is used to access revised properties of attributes. Code shall check if format revision is available
    /// and return corresponding attributes instead.
    /// </summary>
    public static final int REVISED = 0x0008;

    /// <summary>
    /// Returns "raw" i.e not accepted format revision properties. 
    /// Used only during import for PositiveDifference calculation.
    /// </summary>
    public static final int AFTER_CHANGES = 0x0010;

    /// <summary>
    /// When set, all global defaults are expanded.
    /// </summary>
    public static final int GLOBAL_DEFAULTS = 0x0020;
    
    /// <summary>
    /// When set, table style is not expanded.
    /// </summary>
    public static final int NO_TABLE_STYLE = 0x0040;

    /// <summary>
    /// Expand only inherited attributes.
    /// </summary>
    public static final int NO_DIRECT_FORMATTING = 0x0080;

    /// <summary>
    /// When set, directly defined paragraph style is skipped.
    /// </summary>
    public static final int NO_PARA_STYLE = 0x0100;

    /// <summary>
    /// When set, a text size inside Comment will not be changed automatically to a size defined in BalloonText style.
    /// </summary>
    public static final int NO_CHANGE_COMMENT_SIZE = 0x0200;

    /// <summary>
    /// This combination is normally used for layout purposes. It includes document defaults and theme fonts.
    /// </summary>
    public static final int LAYOUT = DOCUMENT_DEFAULTS;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer RunPrExpandFlags value.
    /// </summary>
    public static String getName(/*RunPrExpandFlags*/int runPrExpandFlags)
    {
    	switch (runPrExpandFlags)
    	{
    		case NORMAL: return "NORMAL";
    		case DOCUMENT_DEFAULTS:
    		/*case LAYOUT:*/
    			return "DOCUMENT_DEFAULTS" + " | " + "LAYOUT";
    		case IGNORE_NORMAL_FONT_SIZE: return "IGNORE_NORMAL_FONT_SIZE";
    		case IGNORE_HYPERLINK_CHAR_STYLE: return "IGNORE_HYPERLINK_CHAR_STYLE";
    		case REVISED: return "REVISED";
    		case AFTER_CHANGES: return "AFTER_CHANGES";
    		case GLOBAL_DEFAULTS: return "GLOBAL_DEFAULTS";
    		case NO_TABLE_STYLE: return "NO_TABLE_STYLE";
    		case NO_DIRECT_FORMATTING: return "NO_DIRECT_FORMATTING";
    		case NO_PARA_STYLE: return "NO_PARA_STYLE";
    		case NO_CHANGE_COMMENT_SIZE: return "NO_CHANGE_COMMENT_SIZE";
    		default: return "Unknown RunPrExpandFlags value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer RunPrExpandFlags value.
    /// </summary>
    public static Set<String> getNames(/*RunPrExpandFlags*/int runPrExpandFlags)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NORMAL & runPrExpandFlags) == NORMAL) names.add("NORMAL");
    	if ((DOCUMENT_DEFAULTS & runPrExpandFlags) == DOCUMENT_DEFAULTS) names.add("DOCUMENT_DEFAULTS");
    	if ((IGNORE_NORMAL_FONT_SIZE & runPrExpandFlags) == IGNORE_NORMAL_FONT_SIZE) names.add("IGNORE_NORMAL_FONT_SIZE");
    	if ((IGNORE_HYPERLINK_CHAR_STYLE & runPrExpandFlags) == IGNORE_HYPERLINK_CHAR_STYLE) names.add("IGNORE_HYPERLINK_CHAR_STYLE");
    	if ((REVISED & runPrExpandFlags) == REVISED) names.add("REVISED");
    	if ((AFTER_CHANGES & runPrExpandFlags) == AFTER_CHANGES) names.add("AFTER_CHANGES");
    	if ((GLOBAL_DEFAULTS & runPrExpandFlags) == GLOBAL_DEFAULTS) names.add("GLOBAL_DEFAULTS");
    	if ((NO_TABLE_STYLE & runPrExpandFlags) == NO_TABLE_STYLE) names.add("NO_TABLE_STYLE");
    	if ((NO_DIRECT_FORMATTING & runPrExpandFlags) == NO_DIRECT_FORMATTING) names.add("NO_DIRECT_FORMATTING");
    	if ((NO_PARA_STYLE & runPrExpandFlags) == NO_PARA_STYLE) names.add("NO_PARA_STYLE");
    	if ((NO_CHANGE_COMMENT_SIZE & runPrExpandFlags) == NO_CHANGE_COMMENT_SIZE) names.add("NO_CHANGE_COMMENT_SIZE");
    	if ((LAYOUT & runPrExpandFlags) == LAYOUT) names.add("LAYOUT");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer RunPrExpandFlags value.
    /// </summary>
    public static String toString(/*RunPrExpandFlags*/int runPrExpandFlags)
    {
    	switch (runPrExpandFlags)
    	{
    		case NORMAL: return "Normal";
    		case DOCUMENT_DEFAULTS:
    		/*case LAYOUT:*/
    			return "DocumentDefaults" + " | " + "Layout";
    		case IGNORE_NORMAL_FONT_SIZE: return "IgnoreNormalFontSize";
    		case IGNORE_HYPERLINK_CHAR_STYLE: return "IgnoreHyperlinkCharStyle";
    		case REVISED: return "Revised";
    		case AFTER_CHANGES: return "AfterChanges";
    		case GLOBAL_DEFAULTS: return "GlobalDefaults";
    		case NO_TABLE_STYLE: return "NoTableStyle";
    		case NO_DIRECT_FORMATTING: return "NoDirectFormatting";
    		case NO_PARA_STYLE: return "NoParaStyle";
    		case NO_CHANGE_COMMENT_SIZE: return "NoChangeCommentSize";
    		default: return toStringSet(runPrExpandFlags);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer RunPrExpandFlags value.
    /// </summary>
    public static String toStringSet(int attr)
    {
    	String result = "";
    	String separator = "";
    	for (int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by RunPrExpandFlags name.
    /// </summary>
    public static /*RunPrExpandFlags*/int fromName(String runPrExpandFlagsName)
    {
    	if ("NORMAL".equals(runPrExpandFlagsName)) return NORMAL;
    	if ("DOCUMENT_DEFAULTS".equals(runPrExpandFlagsName)) return DOCUMENT_DEFAULTS;
    	if ("IGNORE_NORMAL_FONT_SIZE".equals(runPrExpandFlagsName)) return IGNORE_NORMAL_FONT_SIZE;
    	if ("IGNORE_HYPERLINK_CHAR_STYLE".equals(runPrExpandFlagsName)) return IGNORE_HYPERLINK_CHAR_STYLE;
    	if ("REVISED".equals(runPrExpandFlagsName)) return REVISED;
    	if ("AFTER_CHANGES".equals(runPrExpandFlagsName)) return AFTER_CHANGES;
    	if ("GLOBAL_DEFAULTS".equals(runPrExpandFlagsName)) return GLOBAL_DEFAULTS;
    	if ("NO_TABLE_STYLE".equals(runPrExpandFlagsName)) return NO_TABLE_STYLE;
    	if ("NO_DIRECT_FORMATTING".equals(runPrExpandFlagsName)) return NO_DIRECT_FORMATTING;
    	if ("NO_PARA_STYLE".equals(runPrExpandFlagsName)) return NO_PARA_STYLE;
    	if ("NO_CHANGE_COMMENT_SIZE".equals(runPrExpandFlagsName)) return NO_CHANGE_COMMENT_SIZE;
    	if ("LAYOUT".equals(runPrExpandFlagsName)) return LAYOUT;
    	throw new IllegalArgumentException("Unknown RunPrExpandFlags name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by RunPrExpandFlags names.
    /// </summary>
    public static /*RunPrExpandFlags*/int fromNames(Set<String> runPrExpandFlagsNames)
    {
    	/*RunPrExpandFlags*/int result = 0;
    	for (String name : runPrExpandFlagsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of RunPrExpandFlags values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NORMAL,
    		DOCUMENT_DEFAULTS,
    		IGNORE_NORMAL_FONT_SIZE,
    		IGNORE_HYPERLINK_CHAR_STYLE,
    		REVISED,
    		AFTER_CHANGES,
    		GLOBAL_DEFAULTS,
    		NO_TABLE_STYLE,
    		NO_DIRECT_FORMATTING,
    		NO_PARA_STYLE,
    		NO_CHANGE_COMMENT_SIZE,
    		LAYOUT,
    	};
    }

    public static final int length = 12;
}

