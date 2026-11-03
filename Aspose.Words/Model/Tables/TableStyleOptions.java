// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/06/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies how table style is applied to a table.
/// </summary>
/// <seealso cref="Table.StyleOptions"/>
/// <dev>
/// 2.4.51 tblLook (Table Style Conditional Formatting Settings)
/// The DOC spec defines more values here, but it says they are ignored by Word 2007 so we ignore them too.
/// </dev>
public /*enum*/ final class TableStyleOptions
{
    private TableStyleOptions(){}
    
    /// <summary>
    /// No table style formatting is applied.
    /// </summary>
    public static final int NONE = 0x0000;

    // Borders - they only from auto-format
    // Shading - only from auto-format
    // Font - only from auto-format
    // Color - only from auto-format
    // BestFit - only from auto-format

    /// <summary>
    /// Apply first row conditional formatting.
    /// </summary>
    public static final int FIRST_ROW = 0x0020;
    /// <summary>
    /// Apply last row conditional formatting.
    /// </summary>
    public static final int LAST_ROW = 0x0040;
    /// <summary>
    /// Apply 1 first column conditional formatting.
    /// </summary>
    public static final int FIRST_COLUMN = 0x0080;
    /// <summary>
    /// Apply last column conditional formatting.
    /// </summary>
    public static final int LAST_COLUMN = 0x0100;
    /// <summary>
    /// Apply row banding conditional formatting.
    /// </summary>
    public static final int ROW_BANDS = 0x0200;
    /// <summary>
    /// Apply column banding conditional formatting.
    /// </summary>
    public static final int COLUMN_BANDS = 0x0400;

    //JAVA: moved here to remove illegal forward reference error.
    /// <summary>
    /// Row and column banding is applied. This is Microsoft Word default for old formats such as DOC, WML and RTF.
    /// </summary>
    public static final int DEFAULT_2003 = ROW_BANDS | COLUMN_BANDS;

    /// <summary>
    /// This is Microsoft Word defaults.
    /// </summary>
    public static final int DEFAULT = FIRST_ROW | FIRST_COLUMN | ROW_BANDS;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TableStyleOptions value.
    /// </summary>
    public static String getName(/*TableStyleOptions*/int tableStyleOptions)
    {
    	switch (tableStyleOptions)
    	{
    		case NONE: return "NONE";
    		case FIRST_ROW: return "FIRST_ROW";
    		case LAST_ROW: return "LAST_ROW";
    		case FIRST_COLUMN: return "FIRST_COLUMN";
    		case LAST_COLUMN: return "LAST_COLUMN";
    		case ROW_BANDS: return "ROW_BANDS";
    		case COLUMN_BANDS: return "COLUMN_BANDS";
    		case DEFAULT_2003: return "DEFAULT_2003";
    		case DEFAULT: return "DEFAULT";
    		default: return "Unknown TableStyleOptions value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer TableStyleOptions value.
    /// </summary>
    public static Set<String> getNames(/*TableStyleOptions*/int tableStyleOptions)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & tableStyleOptions) == NONE) names.add("NONE");
    	if ((FIRST_ROW & tableStyleOptions) == FIRST_ROW) names.add("FIRST_ROW");
    	if ((LAST_ROW & tableStyleOptions) == LAST_ROW) names.add("LAST_ROW");
    	if ((FIRST_COLUMN & tableStyleOptions) == FIRST_COLUMN) names.add("FIRST_COLUMN");
    	if ((LAST_COLUMN & tableStyleOptions) == LAST_COLUMN) names.add("LAST_COLUMN");
    	if ((ROW_BANDS & tableStyleOptions) == ROW_BANDS) names.add("ROW_BANDS");
    	if ((COLUMN_BANDS & tableStyleOptions) == COLUMN_BANDS) names.add("COLUMN_BANDS");
    	if ((DEFAULT_2003 & tableStyleOptions) == DEFAULT_2003) names.add("DEFAULT_2003");
    	if ((DEFAULT & tableStyleOptions) == DEFAULT) names.add("DEFAULT");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TableStyleOptions value.
    /// </summary>
    public static String toString(/*TableStyleOptions*/int tableStyleOptions)
    {
    	switch (tableStyleOptions)
    	{
    		case NONE: return "None";
    		case FIRST_ROW: return "FirstRow";
    		case LAST_ROW: return "LastRow";
    		case FIRST_COLUMN: return "FirstColumn";
    		case LAST_COLUMN: return "LastColumn";
    		case ROW_BANDS: return "RowBands";
    		case COLUMN_BANDS: return "ColumnBands";
    		case DEFAULT_2003: return "Default2003";
    		case DEFAULT: return "Default";
    		default: return toStringSet(tableStyleOptions);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TableStyleOptions value.
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
    /// Returns integer representation by TableStyleOptions name.
    /// </summary>
    public static /*TableStyleOptions*/int fromName(String tableStyleOptionsName)
    {
    	if ("NONE".equals(tableStyleOptionsName)) return NONE;
    	if ("FIRST_ROW".equals(tableStyleOptionsName)) return FIRST_ROW;
    	if ("LAST_ROW".equals(tableStyleOptionsName)) return LAST_ROW;
    	if ("FIRST_COLUMN".equals(tableStyleOptionsName)) return FIRST_COLUMN;
    	if ("LAST_COLUMN".equals(tableStyleOptionsName)) return LAST_COLUMN;
    	if ("ROW_BANDS".equals(tableStyleOptionsName)) return ROW_BANDS;
    	if ("COLUMN_BANDS".equals(tableStyleOptionsName)) return COLUMN_BANDS;
    	if ("DEFAULT_2003".equals(tableStyleOptionsName)) return DEFAULT_2003;
    	if ("DEFAULT".equals(tableStyleOptionsName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown TableStyleOptions name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by TableStyleOptions names.
    /// </summary>
    public static /*TableStyleOptions*/int fromNames(Set<String> tableStyleOptionsNames)
    {
    	/*TableStyleOptions*/int result = 0;
    	for (String name : tableStyleOptionsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of TableStyleOptions values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		FIRST_ROW,
    		LAST_ROW,
    		FIRST_COLUMN,
    		LAST_COLUMN,
    		ROW_BANDS,
    		COLUMN_BANDS,
    		DEFAULT_2003,
    		DEFAULT,
    	};
    }

    public static final int length = 9;
}

