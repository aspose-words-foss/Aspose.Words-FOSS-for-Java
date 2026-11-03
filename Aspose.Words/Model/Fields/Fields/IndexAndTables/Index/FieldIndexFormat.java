// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/08/2020 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the formatting for the <see cref="FieldIndex"/> fields in a document.
/// </summary>
public /*enum*/ final class FieldIndexFormat
{
    private FieldIndexFormat(){}
    
    /// <summary>From template.</summary>
    public static final int TEMPLATE = 0;
    /// <summary>Classic.</summary>
    public static final int CLASSIC = 1;
    /// <summary>Fancy.</summary>
    public static final int FANCY = 2;
    /// <summary>Modern.</summary>
    public static final int MODERN = 3;
    /// <summary>Bulleted.</summary>
    public static final int BULLETED = 4;
    /// <summary>Formal.</summary>
    public static final int FORMAL = 5;
    /// <summary>Simple.</summary>
    public static final int SIMPLE = 6;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldIndexFormat value.
    /// </summary>
    public static String getName(/*FieldIndexFormat*/int fieldIndexFormat)
    {
    	switch (fieldIndexFormat)
    	{
    		case TEMPLATE: return "TEMPLATE";
    		case CLASSIC: return "CLASSIC";
    		case FANCY: return "FANCY";
    		case MODERN: return "MODERN";
    		case BULLETED: return "BULLETED";
    		case FORMAL: return "FORMAL";
    		case SIMPLE: return "SIMPLE";
    		default: return "Unknown FieldIndexFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldIndexFormat value.
    /// </summary>
    public static String toString(/*FieldIndexFormat*/int fieldIndexFormat)
    {
    	switch (fieldIndexFormat)
    	{
    		case TEMPLATE: return "Template";
    		case CLASSIC: return "Classic";
    		case FANCY: return "Fancy";
    		case MODERN: return "Modern";
    		case BULLETED: return "Bulleted";
    		case FORMAL: return "Formal";
    		case SIMPLE: return "Simple";
    		default: return "Unknown FieldIndexFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FieldIndexFormat name.
    /// </summary>
    public static /*FieldIndexFormat*/int fromName(String fieldIndexFormatName)
    {
    	if ("TEMPLATE".equals(fieldIndexFormatName)) return TEMPLATE;
    	if ("CLASSIC".equals(fieldIndexFormatName)) return CLASSIC;
    	if ("FANCY".equals(fieldIndexFormatName)) return FANCY;
    	if ("MODERN".equals(fieldIndexFormatName)) return MODERN;
    	if ("BULLETED".equals(fieldIndexFormatName)) return BULLETED;
    	if ("FORMAL".equals(fieldIndexFormatName)) return FORMAL;
    	if ("SIMPLE".equals(fieldIndexFormatName)) return SIMPLE;
    	throw new IllegalArgumentException("Unknown FieldIndexFormat name.");
    }
    
    /// <summary>
    /// Returns array of FieldIndexFormat values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TEMPLATE,
    		CLASSIC,
    		FANCY,
    		MODERN,
    		BULLETED,
    		FORMAL,
    		SIMPLE,
    	};
    }

    public static final int length = 7;
}

