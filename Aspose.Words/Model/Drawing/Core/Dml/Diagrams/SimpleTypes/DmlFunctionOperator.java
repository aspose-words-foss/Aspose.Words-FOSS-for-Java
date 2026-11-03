// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.32 ST_FunctionOperator (Function Operator)
/// </summary>
/*enum*/ final class DmlFunctionOperator
{
    private DmlFunctionOperator(){}
    
    public static final int EQUAL = 0;
    public static final int GREATER = 1;
    public static final int GREATER_OR_EQUAL = 2;
    public static final int LESS = 3;
    public static final int LESS_OR_EQUAL = 4;
    public static final int NOT_EQUAL = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlFunctionOperator value.
    /// </summary>
    public static String getName(/*DmlFunctionOperator*/int dmlFunctionOperator)
    {
    	switch (dmlFunctionOperator)
    	{
    		case EQUAL: return "EQUAL";
    		case GREATER: return "GREATER";
    		case GREATER_OR_EQUAL: return "GREATER_OR_EQUAL";
    		case LESS: return "LESS";
    		case LESS_OR_EQUAL: return "LESS_OR_EQUAL";
    		case NOT_EQUAL: return "NOT_EQUAL";
    		default: return "Unknown DmlFunctionOperator value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlFunctionOperator value.
    /// </summary>
    public static String toString(/*DmlFunctionOperator*/int dmlFunctionOperator)
    {
    	switch (dmlFunctionOperator)
    	{
    		case EQUAL: return "Equal";
    		case GREATER: return "Greater";
    		case GREATER_OR_EQUAL: return "GreaterOrEqual";
    		case LESS: return "Less";
    		case LESS_OR_EQUAL: return "LessOrEqual";
    		case NOT_EQUAL: return "NotEqual";
    		default: return "Unknown DmlFunctionOperator value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlFunctionOperator name.
    /// </summary>
    public static /*DmlFunctionOperator*/int fromName(String dmlFunctionOperatorName)
    {
    	if ("EQUAL".equals(dmlFunctionOperatorName)) return EQUAL;
    	if ("GREATER".equals(dmlFunctionOperatorName)) return GREATER;
    	if ("GREATER_OR_EQUAL".equals(dmlFunctionOperatorName)) return GREATER_OR_EQUAL;
    	if ("LESS".equals(dmlFunctionOperatorName)) return LESS;
    	if ("LESS_OR_EQUAL".equals(dmlFunctionOperatorName)) return LESS_OR_EQUAL;
    	if ("NOT_EQUAL".equals(dmlFunctionOperatorName)) return NOT_EQUAL;
    	throw new IllegalArgumentException("Unknown DmlFunctionOperator name.");
    }
    
    /// <summary>
    /// Returns array of DmlFunctionOperator values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		EQUAL,
    		GREATER,
    		GREATER_OR_EQUAL,
    		LESS,
    		LESS_OR_EQUAL,
    		NOT_EQUAL,
    	};
    }

    public static final int length = 6;
}

