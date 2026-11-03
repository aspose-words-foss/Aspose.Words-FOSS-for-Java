// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the meaning of display range required by layout engine.
/// </summary>
/*enum*/ final class FieldDisplayRangeType
{
    private FieldDisplayRangeType(){}
    
    /// <summary>
    /// The display range designates field result.
    /// </summary>
    public static final int FIELD_RESULT = 0;
    /// <summary>
    /// The display range designates the whole field.
    /// </summary>
    public static final int WHOLE_FIELD = 1;
    /// <summary>
    /// The display range was created artificially.
    /// </summary>
    public static final int FAKE_RESULT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldDisplayRangeType value.
    /// </summary>
    public static String getName(/*FieldDisplayRangeType*/int fieldDisplayRangeType)
    {
    	switch (fieldDisplayRangeType)
    	{
    		case FIELD_RESULT: return "FIELD_RESULT";
    		case WHOLE_FIELD: return "WHOLE_FIELD";
    		case FAKE_RESULT: return "FAKE_RESULT";
    		default: return "Unknown FieldDisplayRangeType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldDisplayRangeType value.
    /// </summary>
    public static String toString(/*FieldDisplayRangeType*/int fieldDisplayRangeType)
    {
    	switch (fieldDisplayRangeType)
    	{
    		case FIELD_RESULT: return "FieldResult";
    		case WHOLE_FIELD: return "WholeField";
    		case FAKE_RESULT: return "FakeResult";
    		default: return "Unknown FieldDisplayRangeType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FieldDisplayRangeType name.
    /// </summary>
    public static /*FieldDisplayRangeType*/int fromName(String fieldDisplayRangeTypeName)
    {
    	if ("FIELD_RESULT".equals(fieldDisplayRangeTypeName)) return FIELD_RESULT;
    	if ("WHOLE_FIELD".equals(fieldDisplayRangeTypeName)) return WHOLE_FIELD;
    	if ("FAKE_RESULT".equals(fieldDisplayRangeTypeName)) return FAKE_RESULT;
    	throw new IllegalArgumentException("Unknown FieldDisplayRangeType name.");
    }
    
    /// <summary>
    /// Returns array of FieldDisplayRangeType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FIELD_RESULT,
    		WHOLE_FIELD,
    		FAKE_RESULT,
    	};
    }

    public static final int length = 3;
}

