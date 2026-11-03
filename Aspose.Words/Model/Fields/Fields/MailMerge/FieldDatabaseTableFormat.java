// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2022 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies table formats for the <see cref="FieldDatabase"/> \l switch.
/// </summary>
/*enum*/ final class FieldDatabaseTableFormat
{
    private FieldDatabaseTableFormat(){}
    
    public static final int NONE = 0;
    public static final int SIMPLE_1 = 1;
    public static final int SIMPLE_2 = 2;
    public static final int SIMPLE_3 = 3;
    public static final int CLASSIC_1 = 4;
    public static final int CLASSIC_2 = 5;
    public static final int CLASSIC_3 = 6;
    public static final int CLASSIC_4 = 7;
    public static final int COLORFUL_1 = 8;
    public static final int COLORFUL_2 = 9;
    public static final int COLORFUL_3 = 10;
    public static final int COLUMNS_1 = 11;
    public static final int COLUMNS_2 = 12;
    public static final int COLUMNS_3 = 13;
    public static final int COLUMNS_4 = 14;
    public static final int COLUMNS_5 = 15;
    public static final int GRID_1 = 16;
    public static final int GRID_2 = 17;
    public static final int GRID_3 = 18;
    public static final int GRID_4 = 19;
    public static final int GRID_5 = 20;
    public static final int GRID_6 = 21;
    public static final int GRID_7 = 22;
    public static final int GRID_8 = 23;
    public static final int LIST_1 = 24;
    public static final int LIST_2 = 25;
    public static final int LIST_3 = 26;
    public static final int LIST_4 = 27;
    public static final int LIST_5 = 28;
    public static final int LIST_6 = 29;
    public static final int LIST_7 = 30;
    public static final int LIST_8 = 31;
    public static final int THREE_DIMENSIONAL_FX_1 = 32;
    public static final int THREE_DIMENSIONAL_FX_2 = 33;
    public static final int THREE_DIMENSIONAL_FX_3 = 34;
    public static final int CONTEMPORARY = 35;
    public static final int ELEGANT = 36;
    public static final int PROFESSIONAL = 37;
    public static final int SUBTLE_1 = 38;
    public static final int SUBTLE_2 = 39;
    public static final int WEB_1 = 40;
    public static final int WEB_2 = 41;
    public static final int WEB_3 = 42;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldDatabaseTableFormat value.
    /// </summary>
    public static String getName(/*FieldDatabaseTableFormat*/int fieldDatabaseTableFormat)
    {
    	switch (fieldDatabaseTableFormat)
    	{
    		case NONE: return "NONE";
    		case SIMPLE_1: return "SIMPLE_1";
    		case SIMPLE_2: return "SIMPLE_2";
    		case SIMPLE_3: return "SIMPLE_3";
    		case CLASSIC_1: return "CLASSIC_1";
    		case CLASSIC_2: return "CLASSIC_2";
    		case CLASSIC_3: return "CLASSIC_3";
    		case CLASSIC_4: return "CLASSIC_4";
    		case COLORFUL_1: return "COLORFUL_1";
    		case COLORFUL_2: return "COLORFUL_2";
    		case COLORFUL_3: return "COLORFUL_3";
    		case COLUMNS_1: return "COLUMNS_1";
    		case COLUMNS_2: return "COLUMNS_2";
    		case COLUMNS_3: return "COLUMNS_3";
    		case COLUMNS_4: return "COLUMNS_4";
    		case COLUMNS_5: return "COLUMNS_5";
    		case GRID_1: return "GRID_1";
    		case GRID_2: return "GRID_2";
    		case GRID_3: return "GRID_3";
    		case GRID_4: return "GRID_4";
    		case GRID_5: return "GRID_5";
    		case GRID_6: return "GRID_6";
    		case GRID_7: return "GRID_7";
    		case GRID_8: return "GRID_8";
    		case LIST_1: return "LIST_1";
    		case LIST_2: return "LIST_2";
    		case LIST_3: return "LIST_3";
    		case LIST_4: return "LIST_4";
    		case LIST_5: return "LIST_5";
    		case LIST_6: return "LIST_6";
    		case LIST_7: return "LIST_7";
    		case LIST_8: return "LIST_8";
    		case THREE_DIMENSIONAL_FX_1: return "THREE_DIMENSIONAL_FX_1";
    		case THREE_DIMENSIONAL_FX_2: return "THREE_DIMENSIONAL_FX_2";
    		case THREE_DIMENSIONAL_FX_3: return "THREE_DIMENSIONAL_FX_3";
    		case CONTEMPORARY: return "CONTEMPORARY";
    		case ELEGANT: return "ELEGANT";
    		case PROFESSIONAL: return "PROFESSIONAL";
    		case SUBTLE_1: return "SUBTLE_1";
    		case SUBTLE_2: return "SUBTLE_2";
    		case WEB_1: return "WEB_1";
    		case WEB_2: return "WEB_2";
    		case WEB_3: return "WEB_3";
    		default: return "Unknown FieldDatabaseTableFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldDatabaseTableFormat value.
    /// </summary>
    public static String toString(/*FieldDatabaseTableFormat*/int fieldDatabaseTableFormat)
    {
    	switch (fieldDatabaseTableFormat)
    	{
    		case NONE: return "None";
    		case SIMPLE_1: return "Simple1";
    		case SIMPLE_2: return "Simple2";
    		case SIMPLE_3: return "Simple3";
    		case CLASSIC_1: return "Classic1";
    		case CLASSIC_2: return "Classic2";
    		case CLASSIC_3: return "Classic3";
    		case CLASSIC_4: return "Classic4";
    		case COLORFUL_1: return "Colorful1";
    		case COLORFUL_2: return "Colorful2";
    		case COLORFUL_3: return "Colorful3";
    		case COLUMNS_1: return "Columns1";
    		case COLUMNS_2: return "Columns2";
    		case COLUMNS_3: return "Columns3";
    		case COLUMNS_4: return "Columns4";
    		case COLUMNS_5: return "Columns5";
    		case GRID_1: return "Grid1";
    		case GRID_2: return "Grid2";
    		case GRID_3: return "Grid3";
    		case GRID_4: return "Grid4";
    		case GRID_5: return "Grid5";
    		case GRID_6: return "Grid6";
    		case GRID_7: return "Grid7";
    		case GRID_8: return "Grid8";
    		case LIST_1: return "List1";
    		case LIST_2: return "List2";
    		case LIST_3: return "List3";
    		case LIST_4: return "List4";
    		case LIST_5: return "List5";
    		case LIST_6: return "List6";
    		case LIST_7: return "List7";
    		case LIST_8: return "List8";
    		case THREE_DIMENSIONAL_FX_1: return "ThreeDimensionalFx1";
    		case THREE_DIMENSIONAL_FX_2: return "ThreeDimensionalFx2";
    		case THREE_DIMENSIONAL_FX_3: return "ThreeDimensionalFx3";
    		case CONTEMPORARY: return "Contemporary";
    		case ELEGANT: return "Elegant";
    		case PROFESSIONAL: return "Professional";
    		case SUBTLE_1: return "Subtle1";
    		case SUBTLE_2: return "Subtle2";
    		case WEB_1: return "Web1";
    		case WEB_2: return "Web2";
    		case WEB_3: return "Web3";
    		default: return "Unknown FieldDatabaseTableFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FieldDatabaseTableFormat name.
    /// </summary>
    public static /*FieldDatabaseTableFormat*/int fromName(String fieldDatabaseTableFormatName)
    {
    	if ("NONE".equals(fieldDatabaseTableFormatName)) return NONE;
    	if ("SIMPLE_1".equals(fieldDatabaseTableFormatName)) return SIMPLE_1;
    	if ("SIMPLE_2".equals(fieldDatabaseTableFormatName)) return SIMPLE_2;
    	if ("SIMPLE_3".equals(fieldDatabaseTableFormatName)) return SIMPLE_3;
    	if ("CLASSIC_1".equals(fieldDatabaseTableFormatName)) return CLASSIC_1;
    	if ("CLASSIC_2".equals(fieldDatabaseTableFormatName)) return CLASSIC_2;
    	if ("CLASSIC_3".equals(fieldDatabaseTableFormatName)) return CLASSIC_3;
    	if ("CLASSIC_4".equals(fieldDatabaseTableFormatName)) return CLASSIC_4;
    	if ("COLORFUL_1".equals(fieldDatabaseTableFormatName)) return COLORFUL_1;
    	if ("COLORFUL_2".equals(fieldDatabaseTableFormatName)) return COLORFUL_2;
    	if ("COLORFUL_3".equals(fieldDatabaseTableFormatName)) return COLORFUL_3;
    	if ("COLUMNS_1".equals(fieldDatabaseTableFormatName)) return COLUMNS_1;
    	if ("COLUMNS_2".equals(fieldDatabaseTableFormatName)) return COLUMNS_2;
    	if ("COLUMNS_3".equals(fieldDatabaseTableFormatName)) return COLUMNS_3;
    	if ("COLUMNS_4".equals(fieldDatabaseTableFormatName)) return COLUMNS_4;
    	if ("COLUMNS_5".equals(fieldDatabaseTableFormatName)) return COLUMNS_5;
    	if ("GRID_1".equals(fieldDatabaseTableFormatName)) return GRID_1;
    	if ("GRID_2".equals(fieldDatabaseTableFormatName)) return GRID_2;
    	if ("GRID_3".equals(fieldDatabaseTableFormatName)) return GRID_3;
    	if ("GRID_4".equals(fieldDatabaseTableFormatName)) return GRID_4;
    	if ("GRID_5".equals(fieldDatabaseTableFormatName)) return GRID_5;
    	if ("GRID_6".equals(fieldDatabaseTableFormatName)) return GRID_6;
    	if ("GRID_7".equals(fieldDatabaseTableFormatName)) return GRID_7;
    	if ("GRID_8".equals(fieldDatabaseTableFormatName)) return GRID_8;
    	if ("LIST_1".equals(fieldDatabaseTableFormatName)) return LIST_1;
    	if ("LIST_2".equals(fieldDatabaseTableFormatName)) return LIST_2;
    	if ("LIST_3".equals(fieldDatabaseTableFormatName)) return LIST_3;
    	if ("LIST_4".equals(fieldDatabaseTableFormatName)) return LIST_4;
    	if ("LIST_5".equals(fieldDatabaseTableFormatName)) return LIST_5;
    	if ("LIST_6".equals(fieldDatabaseTableFormatName)) return LIST_6;
    	if ("LIST_7".equals(fieldDatabaseTableFormatName)) return LIST_7;
    	if ("LIST_8".equals(fieldDatabaseTableFormatName)) return LIST_8;
    	if ("THREE_DIMENSIONAL_FX_1".equals(fieldDatabaseTableFormatName)) return THREE_DIMENSIONAL_FX_1;
    	if ("THREE_DIMENSIONAL_FX_2".equals(fieldDatabaseTableFormatName)) return THREE_DIMENSIONAL_FX_2;
    	if ("THREE_DIMENSIONAL_FX_3".equals(fieldDatabaseTableFormatName)) return THREE_DIMENSIONAL_FX_3;
    	if ("CONTEMPORARY".equals(fieldDatabaseTableFormatName)) return CONTEMPORARY;
    	if ("ELEGANT".equals(fieldDatabaseTableFormatName)) return ELEGANT;
    	if ("PROFESSIONAL".equals(fieldDatabaseTableFormatName)) return PROFESSIONAL;
    	if ("SUBTLE_1".equals(fieldDatabaseTableFormatName)) return SUBTLE_1;
    	if ("SUBTLE_2".equals(fieldDatabaseTableFormatName)) return SUBTLE_2;
    	if ("WEB_1".equals(fieldDatabaseTableFormatName)) return WEB_1;
    	if ("WEB_2".equals(fieldDatabaseTableFormatName)) return WEB_2;
    	if ("WEB_3".equals(fieldDatabaseTableFormatName)) return WEB_3;
    	throw new IllegalArgumentException("Unknown FieldDatabaseTableFormat name.");
    }
    
    /// <summary>
    /// Returns array of FieldDatabaseTableFormat values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		SIMPLE_1,
    		SIMPLE_2,
    		SIMPLE_3,
    		CLASSIC_1,
    		CLASSIC_2,
    		CLASSIC_3,
    		CLASSIC_4,
    		COLORFUL_1,
    		COLORFUL_2,
    		COLORFUL_3,
    		COLUMNS_1,
    		COLUMNS_2,
    		COLUMNS_3,
    		COLUMNS_4,
    		COLUMNS_5,
    		GRID_1,
    		GRID_2,
    		GRID_3,
    		GRID_4,
    		GRID_5,
    		GRID_6,
    		GRID_7,
    		GRID_8,
    		LIST_1,
    		LIST_2,
    		LIST_3,
    		LIST_4,
    		LIST_5,
    		LIST_6,
    		LIST_7,
    		LIST_8,
    		THREE_DIMENSIONAL_FX_1,
    		THREE_DIMENSIONAL_FX_2,
    		THREE_DIMENSIONAL_FX_3,
    		CONTEMPORARY,
    		ELEGANT,
    		PROFESSIONAL,
    		SUBTLE_1,
    		SUBTLE_2,
    		WEB_1,
    		WEB_2,
    		WEB_3,
    	};
    }

    public static final int length = 43;
}

