// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// These are major categories of CSS value types.
/// </summary>
/*enum*/ final class CssValueType
{
    private CssValueType(){}
    
    /// <summary>
    /// The property value is an identifier such as 'xx-large' (no quotes). Stored as a string.
    /// This is a default value used for any property value that we don't recognize.
    /// </summary>
    public static final int IDENTIFIER = 0;
    /// <summary>
    /// The property value is a text string. 
    /// </summary>
    public static final int STRING = 1;
    /// <summary>
    /// The property value is a hash string (a continuous text prefixed by the hash symbol '#').
    /// </summary>
    /// <remarks>
    /// The string is not necessarily a hex number. It's up to the calling code to correctly interpret the value.
    /// </remarks>
    public static final int HASH = 2;
    /// <summary>
    /// The property value is a number without units. Stored as double.
    /// </summary>
    public static final int NUMBER = 3;
    /// <summary>
    /// The property value is an absolute length with units. Stored as double. 
    /// Examine unit type to find out what units the value is in.
    /// </summary>
    public static final int LENGTH = 4;
    /// <summary>
    /// The property value is a number of percent. Stored as double.
    /// </summary>
    public static final int PERCENTAGE = 5;
    /// <summary>
    /// The property value is a URI value (Uniform Resource Identifiers, see [RFC3986], which includes URLs, URNs, etc).
    /// </summary>
    public static final int URI = 6;
    /// <summary>
    /// , char
    /// </summary>
    public static final int COMMA = 7;
    /// <summary>
    /// U+002F SOLIDUS (/)
    /// </summary>
    public static final int SOLIDUS = 8;
    /// <summary>
    /// The property value is a function such as counter(cnt1).
    /// </summary>
    public static final int FUNCTION = 9;
    /// <summary>
    /// The property value is a degree such as 90deg. Stored as double.
    /// </summary>
    public static final int DEGREE = 10;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssValueType value.
    /// </summary>
    public static String getName(/*CssValueType*/int cssValueType)
    {
    	switch (cssValueType)
    	{
    		case IDENTIFIER: return "IDENTIFIER";
    		case STRING: return "STRING";
    		case HASH: return "HASH";
    		case NUMBER: return "NUMBER";
    		case LENGTH: return "LENGTH";
    		case PERCENTAGE: return "PERCENTAGE";
    		case URI: return "URI";
    		case COMMA: return "COMMA";
    		case SOLIDUS: return "SOLIDUS";
    		case FUNCTION: return "FUNCTION";
    		case DEGREE: return "DEGREE";
    		default: return "Unknown CssValueType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssValueType value.
    /// </summary>
    public static String toString(/*CssValueType*/int cssValueType)
    {
    	switch (cssValueType)
    	{
    		case IDENTIFIER: return "Identifier";
    		case STRING: return "String";
    		case HASH: return "Hash";
    		case NUMBER: return "Number";
    		case LENGTH: return "Length";
    		case PERCENTAGE: return "Percentage";
    		case URI: return "Uri";
    		case COMMA: return "Comma";
    		case SOLIDUS: return "Solidus";
    		case FUNCTION: return "Function";
    		case DEGREE: return "Degree";
    		default: return "Unknown CssValueType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CssValueType name.
    /// </summary>
    public static /*CssValueType*/int fromName(String cssValueTypeName)
    {
    	if ("IDENTIFIER".equals(cssValueTypeName)) return IDENTIFIER;
    	if ("STRING".equals(cssValueTypeName)) return STRING;
    	if ("HASH".equals(cssValueTypeName)) return HASH;
    	if ("NUMBER".equals(cssValueTypeName)) return NUMBER;
    	if ("LENGTH".equals(cssValueTypeName)) return LENGTH;
    	if ("PERCENTAGE".equals(cssValueTypeName)) return PERCENTAGE;
    	if ("URI".equals(cssValueTypeName)) return URI;
    	if ("COMMA".equals(cssValueTypeName)) return COMMA;
    	if ("SOLIDUS".equals(cssValueTypeName)) return SOLIDUS;
    	if ("FUNCTION".equals(cssValueTypeName)) return FUNCTION;
    	if ("DEGREE".equals(cssValueTypeName)) return DEGREE;
    	throw new IllegalArgumentException("Unknown CssValueType name.");
    }
    
    /// <summary>
    /// Returns array of CssValueType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		IDENTIFIER,
    		STRING,
    		HASH,
    		NUMBER,
    		LENGTH,
    		PERCENTAGE,
    		URI,
    		COMMA,
    		SOLIDUS,
    		FUNCTION,
    		DEGREE,
    	};
    }

    public static final int length = 11;
}

