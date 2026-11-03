// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.10 ST_BoolOperator (Boolean Constraint)
/// </summary>
/*enum*/ final class DmlBooleanOperator
{
    private DmlBooleanOperator(){}
    
    public static final int NONE = 0;
    public static final int EQUAL = 1;
    public static final int GREATER_OR_EQUAL = 2;
    public static final int LESS_OR_EQUAL = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlBooleanOperator value.
    /// </summary>
    public static String getName(/*DmlBooleanOperator*/int dmlBooleanOperator)
    {
    	switch (dmlBooleanOperator)
    	{
    		case NONE: return "NONE";
    		case EQUAL: return "EQUAL";
    		case GREATER_OR_EQUAL: return "GREATER_OR_EQUAL";
    		case LESS_OR_EQUAL: return "LESS_OR_EQUAL";
    		default: return "Unknown DmlBooleanOperator value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlBooleanOperator value.
    /// </summary>
    public static String toString(/*DmlBooleanOperator*/int dmlBooleanOperator)
    {
    	switch (dmlBooleanOperator)
    	{
    		case NONE: return "None";
    		case EQUAL: return "Equal";
    		case GREATER_OR_EQUAL: return "GreaterOrEqual";
    		case LESS_OR_EQUAL: return "LessOrEqual";
    		default: return "Unknown DmlBooleanOperator value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlBooleanOperator name.
    /// </summary>
    public static /*DmlBooleanOperator*/int fromName(String dmlBooleanOperatorName)
    {
    	if ("NONE".equals(dmlBooleanOperatorName)) return NONE;
    	if ("EQUAL".equals(dmlBooleanOperatorName)) return EQUAL;
    	if ("GREATER_OR_EQUAL".equals(dmlBooleanOperatorName)) return GREATER_OR_EQUAL;
    	if ("LESS_OR_EQUAL".equals(dmlBooleanOperatorName)) return LESS_OR_EQUAL;
    	throw new IllegalArgumentException("Unknown DmlBooleanOperator name.");
    }
    
    /// <summary>
    /// Returns array of DmlBooleanOperator values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		EQUAL,
    		GREATER_OR_EQUAL,
    		LESS_OR_EQUAL,
    	};
    }

    public static final int length = 4;
}

