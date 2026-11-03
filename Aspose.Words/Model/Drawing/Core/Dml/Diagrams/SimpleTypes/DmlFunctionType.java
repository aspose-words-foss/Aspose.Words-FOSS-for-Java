// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.33 ST_FunctionType (Function Type)
/// </summary>
/*enum*/ final class DmlFunctionType
{
    private DmlFunctionType(){}
    
    public static final int COUNT = 0;
    public static final int DEPTH = 1;
    public static final int MAX_DEPTH = 2;
    public static final int POSITION = 3;
    public static final int POSITION_EVEN = 4;
    public static final int POSITION_ODD = 5;
    public static final int REVERSE_POSITION = 6;
    public static final int VARIABLE = 7;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlFunctionType value.
    /// </summary>
    public static String getName(/*DmlFunctionType*/int dmlFunctionType)
    {
    	switch (dmlFunctionType)
    	{
    		case COUNT: return "COUNT";
    		case DEPTH: return "DEPTH";
    		case MAX_DEPTH: return "MAX_DEPTH";
    		case POSITION: return "POSITION";
    		case POSITION_EVEN: return "POSITION_EVEN";
    		case POSITION_ODD: return "POSITION_ODD";
    		case REVERSE_POSITION: return "REVERSE_POSITION";
    		case VARIABLE: return "VARIABLE";
    		default: return "Unknown DmlFunctionType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlFunctionType value.
    /// </summary>
    public static String toString(/*DmlFunctionType*/int dmlFunctionType)
    {
    	switch (dmlFunctionType)
    	{
    		case COUNT: return "Count";
    		case DEPTH: return "Depth";
    		case MAX_DEPTH: return "MaxDepth";
    		case POSITION: return "Position";
    		case POSITION_EVEN: return "PositionEven";
    		case POSITION_ODD: return "PositionOdd";
    		case REVERSE_POSITION: return "ReversePosition";
    		case VARIABLE: return "Variable";
    		default: return "Unknown DmlFunctionType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlFunctionType name.
    /// </summary>
    public static /*DmlFunctionType*/int fromName(String dmlFunctionTypeName)
    {
    	if ("COUNT".equals(dmlFunctionTypeName)) return COUNT;
    	if ("DEPTH".equals(dmlFunctionTypeName)) return DEPTH;
    	if ("MAX_DEPTH".equals(dmlFunctionTypeName)) return MAX_DEPTH;
    	if ("POSITION".equals(dmlFunctionTypeName)) return POSITION;
    	if ("POSITION_EVEN".equals(dmlFunctionTypeName)) return POSITION_EVEN;
    	if ("POSITION_ODD".equals(dmlFunctionTypeName)) return POSITION_ODD;
    	if ("REVERSE_POSITION".equals(dmlFunctionTypeName)) return REVERSE_POSITION;
    	if ("VARIABLE".equals(dmlFunctionTypeName)) return VARIABLE;
    	throw new IllegalArgumentException("Unknown DmlFunctionType name.");
    }
    
    /// <summary>
    /// Returns array of DmlFunctionType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		COUNT,
    		DEPTH,
    		MAX_DEPTH,
    		POSITION,
    		POSITION_EVEN,
    		POSITION_ODD,
    		REVERSE_POSITION,
    		VARIABLE,
    	};
    }

    public static final int length = 8;
}

