// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of a text form field.
/// </summary>
public /*enum*/ final class TextFormFieldType
{
    private TextFormFieldType(){}
    
    /// <summary>
    /// The text form field can contain any text.
    /// </summary>
    public static final int REGULAR = 0;
    /// <summary>
    /// The text form field can contain only numbers.
    /// </summary>
    public static final int NUMBER = 1;
    /// <summary>
    /// The text form field can contain only a valid date value.
    /// </summary>
    public static final int DATE = 2;
    /// <summary>
    /// The text form field value is the current date when the field is updated.
    /// </summary>
    public static final int CURRENT_DATE = 3;
    /// <summary>
    /// The text form field value is the current time when the field is updated.
    /// </summary>
    public static final int CURRENT_TIME = 4;
    /// <summary>
    /// The text form field value is calculated from the expression specified in
    /// the <see cref="FormField.TextInputDefault"/> property.
    /// </summary>
    public static final int CALCULATED = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TextFormFieldType value.
    /// </summary>
    public static String getName(/*TextFormFieldType*/int textFormFieldType)
    {
    	switch (textFormFieldType)
    	{
    		case REGULAR: return "REGULAR";
    		case NUMBER: return "NUMBER";
    		case DATE: return "DATE";
    		case CURRENT_DATE: return "CURRENT_DATE";
    		case CURRENT_TIME: return "CURRENT_TIME";
    		case CALCULATED: return "CALCULATED";
    		default: return "Unknown TextFormFieldType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TextFormFieldType value.
    /// </summary>
    public static String toString(/*TextFormFieldType*/int textFormFieldType)
    {
    	switch (textFormFieldType)
    	{
    		case REGULAR: return "Regular";
    		case NUMBER: return "Number";
    		case DATE: return "Date";
    		case CURRENT_DATE: return "CurrentDate";
    		case CURRENT_TIME: return "CurrentTime";
    		case CALCULATED: return "Calculated";
    		default: return "Unknown TextFormFieldType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TextFormFieldType name.
    /// </summary>
    public static /*TextFormFieldType*/int fromName(String textFormFieldTypeName)
    {
    	if ("REGULAR".equals(textFormFieldTypeName)) return REGULAR;
    	if ("NUMBER".equals(textFormFieldTypeName)) return NUMBER;
    	if ("DATE".equals(textFormFieldTypeName)) return DATE;
    	if ("CURRENT_DATE".equals(textFormFieldTypeName)) return CURRENT_DATE;
    	if ("CURRENT_TIME".equals(textFormFieldTypeName)) return CURRENT_TIME;
    	if ("CALCULATED".equals(textFormFieldTypeName)) return CALCULATED;
    	throw new IllegalArgumentException("Unknown TextFormFieldType name.");
    }
    
    /// <summary>
    /// Returns array of TextFormFieldType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		REGULAR,
    		NUMBER,
    		DATE,
    		CURRENT_DATE,
    		CURRENT_TIME,
    		CALCULATED,
    	};
    }

    public static final int length = 6;
}

