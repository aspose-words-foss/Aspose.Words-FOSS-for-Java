// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/08/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies data type of a document property.
/// </summary>
/// <seealso cref="DocumentProperty"/>
/// <seealso cref="DocumentProperty.Type"/>
public /*enum*/ final class PropertyType
{
    private PropertyType(){}
    
    /// <summary>
    /// The property is a boolean value.
    /// </summary>
    public static final int BOOLEAN = 0;

    /// <summary>
    /// The property is a date time value.
    /// </summary>
    public static final int DATE_TIME = 1;

    /// <summary>
    /// The property is a floating number.
    /// </summary>
    public static final int DOUBLE = 2;

    /// <summary>
    /// The property is an integer number.
    /// </summary>
    public static final int NUMBER = 3;

    /// <summary>
    /// The property is a string value.
    /// </summary>
    public static final int STRING = 4;

    /// <summary>
    /// The property is an array of strings.
    /// </summary>
    public static final int STRING_ARRAY = 5;

    /// <summary>
    /// The property is an array of objects.
    /// </summary>
    public static final int OBJECT_ARRAY = 6;

    /// <summary>
    /// The property is an array of bytes.
    /// </summary>
    public static final int BYTE_ARRAY = 7;

    /// <summary>
    /// The property is some other type.
    /// </summary>
    public static final int OTHER = 8;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PropertyType value.
    /// </summary>
    public static String getName(/*PropertyType*/int propertyType)
    {
    	switch (propertyType)
    	{
    		case BOOLEAN: return "BOOLEAN";
    		case DATE_TIME: return "DATE_TIME";
    		case DOUBLE: return "DOUBLE";
    		case NUMBER: return "NUMBER";
    		case STRING: return "STRING";
    		case STRING_ARRAY: return "STRING_ARRAY";
    		case OBJECT_ARRAY: return "OBJECT_ARRAY";
    		case BYTE_ARRAY: return "BYTE_ARRAY";
    		case OTHER: return "OTHER";
    		default: return "Unknown PropertyType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PropertyType value.
    /// </summary>
    public static String toString(/*PropertyType*/int propertyType)
    {
    	switch (propertyType)
    	{
    		case BOOLEAN: return "Boolean";
    		case DATE_TIME: return "DateTime";
    		case DOUBLE: return "Double";
    		case NUMBER: return "Number";
    		case STRING: return "String";
    		case STRING_ARRAY: return "StringArray";
    		case OBJECT_ARRAY: return "ObjectArray";
    		case BYTE_ARRAY: return "ByteArray";
    		case OTHER: return "Other";
    		default: return "Unknown PropertyType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PropertyType name.
    /// </summary>
    public static /*PropertyType*/int fromName(String propertyTypeName)
    {
    	if ("BOOLEAN".equals(propertyTypeName)) return BOOLEAN;
    	if ("DATE_TIME".equals(propertyTypeName)) return DATE_TIME;
    	if ("DOUBLE".equals(propertyTypeName)) return DOUBLE;
    	if ("NUMBER".equals(propertyTypeName)) return NUMBER;
    	if ("STRING".equals(propertyTypeName)) return STRING;
    	if ("STRING_ARRAY".equals(propertyTypeName)) return STRING_ARRAY;
    	if ("OBJECT_ARRAY".equals(propertyTypeName)) return OBJECT_ARRAY;
    	if ("BYTE_ARRAY".equals(propertyTypeName)) return BYTE_ARRAY;
    	if ("OTHER".equals(propertyTypeName)) return OTHER;
    	throw new IllegalArgumentException("Unknown PropertyType name.");
    }
    
    /// <summary>
    /// Returns array of PropertyType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BOOLEAN,
    		DATE_TIME,
    		DOUBLE,
    		NUMBER,
    		STRING,
    		STRING_ARRAY,
    		OBJECT_ARRAY,
    		BYTE_ARRAY,
    		OTHER,
    	};
    }

    public static final int length = 9;
}

