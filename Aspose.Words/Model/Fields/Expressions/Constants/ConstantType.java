// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a constant type.
/// </summary>
/*enum*/ final class ConstantType
{
    private ConstantType(){}
    
    /// <summary>
    /// The constant has no value.
    /// </summary>
    public static final int NULL = 0;
    /// <summary>
    /// The constant has an integer numeric value.
    /// </summary>
    public static final int INT_32 = 1;
    /// <summary>
    /// The constant has a floating point numeric value.
    /// </summary>
    public static final int DOUBLE = 2;
    /// <summary>
    /// The constant has a date/time value.
    /// </summary>
    public static final int DATE_TIME = 3;
    /// <summary>
    /// The constant has a boolean value.
    /// </summary>
    public static final int BOOLEAN = 4;
    /// <summary>
    /// The constant has a text value.
    /// </summary>
    public static final int STRING = 5;
    /// <summary>
    /// The constant has a value that consists of other constants.
    /// </summary>
    public static final int AGGREGATE = 6;
    /// <summary>
    /// The constant represents an erroneous evaluation result.
    /// </summary>
    public static final int ERROR = 7;
    /// <summary>
    /// The constant represents a reference to cell, which does not exist.
    /// </summary>
    public static final int NULL_CELL_REFERENCE = 8;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ConstantType value.
    /// </summary>
    public static String getName(/*ConstantType*/int constantType)
    {
    	switch (constantType)
    	{
    		case NULL: return "NULL";
    		case INT_32: return "INT_32";
    		case DOUBLE: return "DOUBLE";
    		case DATE_TIME: return "DATE_TIME";
    		case BOOLEAN: return "BOOLEAN";
    		case STRING: return "STRING";
    		case AGGREGATE: return "AGGREGATE";
    		case ERROR: return "ERROR";
    		case NULL_CELL_REFERENCE: return "NULL_CELL_REFERENCE";
    		default: return "Unknown ConstantType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ConstantType value.
    /// </summary>
    public static String toString(/*ConstantType*/int constantType)
    {
    	switch (constantType)
    	{
    		case NULL: return "Null";
    		case INT_32: return "Int32";
    		case DOUBLE: return "Double";
    		case DATE_TIME: return "DateTime";
    		case BOOLEAN: return "Boolean";
    		case STRING: return "String";
    		case AGGREGATE: return "Aggregate";
    		case ERROR: return "Error";
    		case NULL_CELL_REFERENCE: return "NullCellReference";
    		default: return "Unknown ConstantType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ConstantType name.
    /// </summary>
    public static /*ConstantType*/int fromName(String constantTypeName)
    {
    	if ("NULL".equals(constantTypeName)) return NULL;
    	if ("INT_32".equals(constantTypeName)) return INT_32;
    	if ("DOUBLE".equals(constantTypeName)) return DOUBLE;
    	if ("DATE_TIME".equals(constantTypeName)) return DATE_TIME;
    	if ("BOOLEAN".equals(constantTypeName)) return BOOLEAN;
    	if ("STRING".equals(constantTypeName)) return STRING;
    	if ("AGGREGATE".equals(constantTypeName)) return AGGREGATE;
    	if ("ERROR".equals(constantTypeName)) return ERROR;
    	if ("NULL_CELL_REFERENCE".equals(constantTypeName)) return NULL_CELL_REFERENCE;
    	throw new IllegalArgumentException("Unknown ConstantType name.");
    }
    
    /// <summary>
    /// Returns array of ConstantType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NULL,
    		INT_32,
    		DOUBLE,
    		DATE_TIME,
    		BOOLEAN,
    		STRING,
    		AGGREGATE,
    		ERROR,
    		NULL_CELL_REFERENCE,
    	};
    }

    public static final int length = 9;
}

