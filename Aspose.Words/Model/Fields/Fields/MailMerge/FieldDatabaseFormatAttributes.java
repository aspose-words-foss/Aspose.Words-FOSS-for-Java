// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2022 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies format attributes for the <see cref="FieldDatabase"/> \b switch.
/// </summary>
/*enum*/ final class FieldDatabaseFormatAttributes
{
    private FieldDatabaseFormatAttributes(){}
    
    public static final int NONE = 0;
    public static final int BORDERS = 1;
    public static final int SHADING = 2;
    public static final int FONT = 4;
    public static final int COLOR = 8;
    public static final int AUTO_FIT = 16;
    public static final int FIRST_ROW = 32;
    public static final int LAST_ROW = 64;
    public static final int FIRST_COLUMN = 128;
    public static final int LAST_COLUMN = 256;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldDatabaseFormatAttributes value.
    /// </summary>
    public static String getName(/*FieldDatabaseFormatAttributes*/int fieldDatabaseFormatAttributes)
    {
    	switch (fieldDatabaseFormatAttributes)
    	{
    		case NONE: return "NONE";
    		case BORDERS: return "BORDERS";
    		case SHADING: return "SHADING";
    		case FONT: return "FONT";
    		case COLOR: return "COLOR";
    		case AUTO_FIT: return "AUTO_FIT";
    		case FIRST_ROW: return "FIRST_ROW";
    		case LAST_ROW: return "LAST_ROW";
    		case FIRST_COLUMN: return "FIRST_COLUMN";
    		case LAST_COLUMN: return "LAST_COLUMN";
    		default: return "Unknown FieldDatabaseFormatAttributes value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer FieldDatabaseFormatAttributes value.
    /// </summary>
    public static Set<String> getNames(/*FieldDatabaseFormatAttributes*/int fieldDatabaseFormatAttributes)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & fieldDatabaseFormatAttributes) == NONE) names.add("NONE");
    	if ((BORDERS & fieldDatabaseFormatAttributes) == BORDERS) names.add("BORDERS");
    	if ((SHADING & fieldDatabaseFormatAttributes) == SHADING) names.add("SHADING");
    	if ((FONT & fieldDatabaseFormatAttributes) == FONT) names.add("FONT");
    	if ((COLOR & fieldDatabaseFormatAttributes) == COLOR) names.add("COLOR");
    	if ((AUTO_FIT & fieldDatabaseFormatAttributes) == AUTO_FIT) names.add("AUTO_FIT");
    	if ((FIRST_ROW & fieldDatabaseFormatAttributes) == FIRST_ROW) names.add("FIRST_ROW");
    	if ((LAST_ROW & fieldDatabaseFormatAttributes) == LAST_ROW) names.add("LAST_ROW");
    	if ((FIRST_COLUMN & fieldDatabaseFormatAttributes) == FIRST_COLUMN) names.add("FIRST_COLUMN");
    	if ((LAST_COLUMN & fieldDatabaseFormatAttributes) == LAST_COLUMN) names.add("LAST_COLUMN");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldDatabaseFormatAttributes value.
    /// </summary>
    public static String toString(/*FieldDatabaseFormatAttributes*/int fieldDatabaseFormatAttributes)
    {
    	switch (fieldDatabaseFormatAttributes)
    	{
    		case NONE: return "None";
    		case BORDERS: return "Borders";
    		case SHADING: return "Shading";
    		case FONT: return "Font";
    		case COLOR: return "Color";
    		case AUTO_FIT: return "AutoFit";
    		case FIRST_ROW: return "FirstRow";
    		case LAST_ROW: return "LastRow";
    		case FIRST_COLUMN: return "FirstColumn";
    		case LAST_COLUMN: return "LastColumn";
    		default: return toStringSet(fieldDatabaseFormatAttributes);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldDatabaseFormatAttributes value.
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
    /// Returns integer representation by FieldDatabaseFormatAttributes name.
    /// </summary>
    public static /*FieldDatabaseFormatAttributes*/int fromName(String fieldDatabaseFormatAttributesName)
    {
    	if ("NONE".equals(fieldDatabaseFormatAttributesName)) return NONE;
    	if ("BORDERS".equals(fieldDatabaseFormatAttributesName)) return BORDERS;
    	if ("SHADING".equals(fieldDatabaseFormatAttributesName)) return SHADING;
    	if ("FONT".equals(fieldDatabaseFormatAttributesName)) return FONT;
    	if ("COLOR".equals(fieldDatabaseFormatAttributesName)) return COLOR;
    	if ("AUTO_FIT".equals(fieldDatabaseFormatAttributesName)) return AUTO_FIT;
    	if ("FIRST_ROW".equals(fieldDatabaseFormatAttributesName)) return FIRST_ROW;
    	if ("LAST_ROW".equals(fieldDatabaseFormatAttributesName)) return LAST_ROW;
    	if ("FIRST_COLUMN".equals(fieldDatabaseFormatAttributesName)) return FIRST_COLUMN;
    	if ("LAST_COLUMN".equals(fieldDatabaseFormatAttributesName)) return LAST_COLUMN;
    	throw new IllegalArgumentException("Unknown FieldDatabaseFormatAttributes name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by FieldDatabaseFormatAttributes names.
    /// </summary>
    public static /*FieldDatabaseFormatAttributes*/int fromNames(Set<String> fieldDatabaseFormatAttributesNames)
    {
    	/*FieldDatabaseFormatAttributes*/int result = 0;
    	for (String name : fieldDatabaseFormatAttributesNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of FieldDatabaseFormatAttributes values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		BORDERS,
    		SHADING,
    		FONT,
    		COLOR,
    		AUTO_FIT,
    		FIRST_ROW,
    		LAST_ROW,
    		FIRST_COLUMN,
    		LAST_COLUMN,
    	};
    }

    public static final int length = 10;
}

