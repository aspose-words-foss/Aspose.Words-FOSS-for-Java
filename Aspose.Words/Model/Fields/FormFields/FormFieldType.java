// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// These values are used in DOC, WordML and RTF. Do not renumber.
/// </summary>
/*enum*/ final class FormFieldType
{
    private FormFieldType(){}
    
    public static final int TEXT_INPUT = 0x00;
    public static final int CHECK_BOX = 0x01;
    public static final int DROP_DOWN = 0x02;
    public static final int UNKNOWN = 0xff;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FormFieldType value.
    /// </summary>
    public static String getName(/*FormFieldType*/int formFieldType)
    {
    	switch (formFieldType)
    	{
    		case TEXT_INPUT: return "TEXT_INPUT";
    		case CHECK_BOX: return "CHECK_BOX";
    		case DROP_DOWN: return "DROP_DOWN";
    		case UNKNOWN: return "UNKNOWN";
    		default: return "Unknown FormFieldType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FormFieldType value.
    /// </summary>
    public static String toString(/*FormFieldType*/int formFieldType)
    {
    	switch (formFieldType)
    	{
    		case TEXT_INPUT: return "TextInput";
    		case CHECK_BOX: return "CheckBox";
    		case DROP_DOWN: return "DropDown";
    		case UNKNOWN: return "Unknown";
    		default: return "Unknown FormFieldType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FormFieldType name.
    /// </summary>
    public static /*FormFieldType*/int fromName(String formFieldTypeName)
    {
    	if ("TEXT_INPUT".equals(formFieldTypeName)) return TEXT_INPUT;
    	if ("CHECK_BOX".equals(formFieldTypeName)) return CHECK_BOX;
    	if ("DROP_DOWN".equals(formFieldTypeName)) return DROP_DOWN;
    	if ("UNKNOWN".equals(formFieldTypeName)) return UNKNOWN;
    	throw new IllegalArgumentException("Unknown FormFieldType name.");
    }
    
    /// <summary>
    /// Returns array of FormFieldType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TEXT_INPUT,
    		CHECK_BOX,
    		DROP_DOWN,
    		UNKNOWN,
    	};
    }

    public static final int length = 4;
}

