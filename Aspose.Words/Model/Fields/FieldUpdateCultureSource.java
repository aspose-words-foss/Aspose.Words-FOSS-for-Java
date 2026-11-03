// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/07/2011 by Dmitry Matveenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Indicates what culture to use during field update.
/// </summary>
public /*enum*/ final class FieldUpdateCultureSource
{
    private FieldUpdateCultureSource(){}
    
    /// <summary>
    /// The culture of the current execution thread is used to update fields.
    /// </summary>
    public static final int CURRENT_THREAD = 0;

    /// <summary>
    /// The culture specified in the field formatting properties via language setting is used.
    /// </summary>
    /// <remarks>
    /// To be exact, Aspose.Words mimics MS Word by using the language set for the first character of the field code.
    /// </remarks>
    /// <seealso cref="Field.LocaleId"/>
    public static final int FIELD_CODE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldUpdateCultureSource value.
    /// </summary>
    public static String getName(/*FieldUpdateCultureSource*/int fieldUpdateCultureSource)
    {
    	switch (fieldUpdateCultureSource)
    	{
    		case CURRENT_THREAD: return "CURRENT_THREAD";
    		case FIELD_CODE: return "FIELD_CODE";
    		default: return "Unknown FieldUpdateCultureSource value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldUpdateCultureSource value.
    /// </summary>
    public static String toString(/*FieldUpdateCultureSource*/int fieldUpdateCultureSource)
    {
    	switch (fieldUpdateCultureSource)
    	{
    		case CURRENT_THREAD: return "CurrentThread";
    		case FIELD_CODE: return "FieldCode";
    		default: return "Unknown FieldUpdateCultureSource value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FieldUpdateCultureSource name.
    /// </summary>
    public static /*FieldUpdateCultureSource*/int fromName(String fieldUpdateCultureSourceName)
    {
    	if ("CURRENT_THREAD".equals(fieldUpdateCultureSourceName)) return CURRENT_THREAD;
    	if ("FIELD_CODE".equals(fieldUpdateCultureSourceName)) return FIELD_CODE;
    	throw new IllegalArgumentException("Unknown FieldUpdateCultureSource name.");
    }
    
    /// <summary>
    /// Returns array of FieldUpdateCultureSource values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CURRENT_THREAD,
    		FIELD_CODE,
    	};
    }

    public static final int length = 2;
}

