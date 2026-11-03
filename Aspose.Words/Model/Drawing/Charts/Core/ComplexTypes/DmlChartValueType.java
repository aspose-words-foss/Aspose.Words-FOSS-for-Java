// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class DmlChartValueType
{
    private DmlChartValueType(){}
    
    /// <summary>
    /// This is special type of values that should not be rendered. It is added for rendering Axis. 
    /// </summary>
    public static final int NONE = 0;
    public static final int STRING = 1;
    public static final int NUMERIC = 2;
    public static final int MULTI_LVL_STRING = 3;
    /// <summary>
    /// Multi-level numeric values of dimension data. 
    /// Such data is stored in elements of the 2.24.3.57 CT_NumericDimension complex type [MS-ODRAWXML].
    /// </summary>
    public static final int MULTI_LVL_NUMERIC = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlChartValueType value.
    /// </summary>
    public static String getName(/*DmlChartValueType*/int dmlChartValueType)
    {
    	switch (dmlChartValueType)
    	{
    		case NONE: return "NONE";
    		case STRING: return "STRING";
    		case NUMERIC: return "NUMERIC";
    		case MULTI_LVL_STRING: return "MULTI_LVL_STRING";
    		case MULTI_LVL_NUMERIC: return "MULTI_LVL_NUMERIC";
    		default: return "Unknown DmlChartValueType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlChartValueType value.
    /// </summary>
    public static String toString(/*DmlChartValueType*/int dmlChartValueType)
    {
    	switch (dmlChartValueType)
    	{
    		case NONE: return "None";
    		case STRING: return "String";
    		case NUMERIC: return "Numeric";
    		case MULTI_LVL_STRING: return "MultiLvlString";
    		case MULTI_LVL_NUMERIC: return "MultiLvlNumeric";
    		default: return "Unknown DmlChartValueType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlChartValueType name.
    /// </summary>
    public static /*DmlChartValueType*/int fromName(String dmlChartValueTypeName)
    {
    	if ("NONE".equals(dmlChartValueTypeName)) return NONE;
    	if ("STRING".equals(dmlChartValueTypeName)) return STRING;
    	if ("NUMERIC".equals(dmlChartValueTypeName)) return NUMERIC;
    	if ("MULTI_LVL_STRING".equals(dmlChartValueTypeName)) return MULTI_LVL_STRING;
    	if ("MULTI_LVL_NUMERIC".equals(dmlChartValueTypeName)) return MULTI_LVL_NUMERIC;
    	throw new IllegalArgumentException("Unknown DmlChartValueType name.");
    }
    
    /// <summary>
    /// Returns array of DmlChartValueType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		STRING,
    		NUMERIC,
    		MULTI_LVL_STRING,
    		MULTI_LVL_NUMERIC,
    	};
    }

    public static final int length = 5;
}

