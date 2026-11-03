// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/08/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/*enum*/ final class ParaPrExpandFlags
{
    private ParaPrExpandFlags(){}
    
    /// <summary>
    /// Performs the normal "plain" expansion of attributes.
    /// </summary>
    public static final int NORMAL = 0x0000;

    /// <summary>
    /// Set to expand default run properties stored for this document.
    /// I do not have a good explanation why we sometimes need to expand and sometimes do not.
    /// </summary>
    public static final int DOCUMENT_DEFAULTS = 0x0001;

    /// <summary>
    /// When set, the "clear" tab stops are not included in the result.
    /// </summary>
    public static final int REMOVE_CLEAR_TAB_STOPS = 0x0002;

    /// <summary>
    /// Set to expand table style properties.
    /// It seems we do not need to expand table style for RTF.
    /// </summary>
    public static final int EXPAND_TABLE_STYLE = 0x0004;

    /// <summary>
    /// Set to expand HTML related information <see cref="HtmlBlock" /> for paragraphs and rows.
    /// It seems we do not need to expand this for paragraphs in RTF.
    /// </summary>
    public static final int EXPAND_HTML_BLOCKS = 0x0008;

    /// <summary>
    /// This is used to access revised properties of attributes. Code shall check if format revision is available
    /// and return corresponding attributes instead.
    /// </summary>
    public static final int REVISED = 0x0010;

    /// <summary>
    /// Returns "raw" i.e not accepted format revision properties.
    /// Used only during import for PositiveDifference calculation.
    /// </summary>
    public static final int AFTER_CHANGES = 0x0020;

    /// <summary>
    /// When set, all global defaults are expanded.
    /// </summary>
    public static final int GLOBAL_DEFAULTS = 0x0040;

    /// <summary>
    /// Expand only inherited attributes.
    /// </summary>
    public static final int NO_DIRECT_FORMATTING = 0x0080;

    /// <summary>
    /// Expand legacy list formatting into legacy tab.
    /// </summary>
    public static final int EXPAND_LEGACY_TAB = 0x0100;

    /// <summary>
    /// This combination is normally used for layout purposes. It includes document defaults and paragraph group properties.
    /// </summary>
    public static final int LAYOUT = DOCUMENT_DEFAULTS | EXPAND_HTML_BLOCKS | EXPAND_LEGACY_TAB;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ParaPrExpandFlags value.
    /// </summary>
    public static String getName(/*ParaPrExpandFlags*/int paraPrExpandFlags)
    {
    	switch (paraPrExpandFlags)
    	{
    		case NORMAL: return "NORMAL";
    		case DOCUMENT_DEFAULTS: return "DOCUMENT_DEFAULTS";
    		case REMOVE_CLEAR_TAB_STOPS: return "REMOVE_CLEAR_TAB_STOPS";
    		case EXPAND_TABLE_STYLE: return "EXPAND_TABLE_STYLE";
    		case EXPAND_HTML_BLOCKS: return "EXPAND_HTML_BLOCKS";
    		case REVISED: return "REVISED";
    		case AFTER_CHANGES: return "AFTER_CHANGES";
    		case GLOBAL_DEFAULTS: return "GLOBAL_DEFAULTS";
    		case NO_DIRECT_FORMATTING: return "NO_DIRECT_FORMATTING";
    		case EXPAND_LEGACY_TAB: return "EXPAND_LEGACY_TAB";
    		case LAYOUT: return "LAYOUT";
    		default: return "Unknown ParaPrExpandFlags value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer ParaPrExpandFlags value.
    /// </summary>
    public static Set<String> getNames(/*ParaPrExpandFlags*/int paraPrExpandFlags)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NORMAL & paraPrExpandFlags) == NORMAL) names.add("NORMAL");
    	if ((DOCUMENT_DEFAULTS & paraPrExpandFlags) == DOCUMENT_DEFAULTS) names.add("DOCUMENT_DEFAULTS");
    	if ((REMOVE_CLEAR_TAB_STOPS & paraPrExpandFlags) == REMOVE_CLEAR_TAB_STOPS) names.add("REMOVE_CLEAR_TAB_STOPS");
    	if ((EXPAND_TABLE_STYLE & paraPrExpandFlags) == EXPAND_TABLE_STYLE) names.add("EXPAND_TABLE_STYLE");
    	if ((EXPAND_HTML_BLOCKS & paraPrExpandFlags) == EXPAND_HTML_BLOCKS) names.add("EXPAND_HTML_BLOCKS");
    	if ((REVISED & paraPrExpandFlags) == REVISED) names.add("REVISED");
    	if ((AFTER_CHANGES & paraPrExpandFlags) == AFTER_CHANGES) names.add("AFTER_CHANGES");
    	if ((GLOBAL_DEFAULTS & paraPrExpandFlags) == GLOBAL_DEFAULTS) names.add("GLOBAL_DEFAULTS");
    	if ((NO_DIRECT_FORMATTING & paraPrExpandFlags) == NO_DIRECT_FORMATTING) names.add("NO_DIRECT_FORMATTING");
    	if ((EXPAND_LEGACY_TAB & paraPrExpandFlags) == EXPAND_LEGACY_TAB) names.add("EXPAND_LEGACY_TAB");
    	if ((LAYOUT & paraPrExpandFlags) == LAYOUT) names.add("LAYOUT");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ParaPrExpandFlags value.
    /// </summary>
    public static String toString(/*ParaPrExpandFlags*/int paraPrExpandFlags)
    {
    	switch (paraPrExpandFlags)
    	{
    		case NORMAL: return "Normal";
    		case DOCUMENT_DEFAULTS: return "DocumentDefaults";
    		case REMOVE_CLEAR_TAB_STOPS: return "RemoveClearTabStops";
    		case EXPAND_TABLE_STYLE: return "ExpandTableStyle";
    		case EXPAND_HTML_BLOCKS: return "ExpandHtmlBlocks";
    		case REVISED: return "Revised";
    		case AFTER_CHANGES: return "AfterChanges";
    		case GLOBAL_DEFAULTS: return "GlobalDefaults";
    		case NO_DIRECT_FORMATTING: return "NoDirectFormatting";
    		case EXPAND_LEGACY_TAB: return "ExpandLegacyTab";
    		case LAYOUT: return "Layout";
    		default: return toStringSet(paraPrExpandFlags);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ParaPrExpandFlags value.
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
    /// Returns integer representation by ParaPrExpandFlags name.
    /// </summary>
    public static /*ParaPrExpandFlags*/int fromName(String paraPrExpandFlagsName)
    {
    	if ("NORMAL".equals(paraPrExpandFlagsName)) return NORMAL;
    	if ("DOCUMENT_DEFAULTS".equals(paraPrExpandFlagsName)) return DOCUMENT_DEFAULTS;
    	if ("REMOVE_CLEAR_TAB_STOPS".equals(paraPrExpandFlagsName)) return REMOVE_CLEAR_TAB_STOPS;
    	if ("EXPAND_TABLE_STYLE".equals(paraPrExpandFlagsName)) return EXPAND_TABLE_STYLE;
    	if ("EXPAND_HTML_BLOCKS".equals(paraPrExpandFlagsName)) return EXPAND_HTML_BLOCKS;
    	if ("REVISED".equals(paraPrExpandFlagsName)) return REVISED;
    	if ("AFTER_CHANGES".equals(paraPrExpandFlagsName)) return AFTER_CHANGES;
    	if ("GLOBAL_DEFAULTS".equals(paraPrExpandFlagsName)) return GLOBAL_DEFAULTS;
    	if ("NO_DIRECT_FORMATTING".equals(paraPrExpandFlagsName)) return NO_DIRECT_FORMATTING;
    	if ("EXPAND_LEGACY_TAB".equals(paraPrExpandFlagsName)) return EXPAND_LEGACY_TAB;
    	if ("LAYOUT".equals(paraPrExpandFlagsName)) return LAYOUT;
    	throw new IllegalArgumentException("Unknown ParaPrExpandFlags name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by ParaPrExpandFlags names.
    /// </summary>
    public static /*ParaPrExpandFlags*/int fromNames(Set<String> paraPrExpandFlagsNames)
    {
    	/*ParaPrExpandFlags*/int result = 0;
    	for (String name : paraPrExpandFlagsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of ParaPrExpandFlags values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NORMAL,
    		DOCUMENT_DEFAULTS,
    		REMOVE_CLEAR_TAB_STOPS,
    		EXPAND_TABLE_STYLE,
    		EXPAND_HTML_BLOCKS,
    		REVISED,
    		AFTER_CHANGES,
    		GLOBAL_DEFAULTS,
    		NO_DIRECT_FORMATTING,
    		EXPAND_LEGACY_TAB,
    		LAYOUT,
    	};
    }

    public static final int length = 11;
}

