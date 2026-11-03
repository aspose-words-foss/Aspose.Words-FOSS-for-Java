// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/06/2024 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Defines options for the <see cref="FieldCharCounter"/>.
/// </summary>
/*enum*/ final class FieldCharCounterOptions
{
    private FieldCharCounterOptions(){}
    
    /// <summary>
    /// Default option.
    /// </summary>
    public static final int DEFAULT = 0;

    /// <summary>
    /// Starts counter within parent field's result.
    /// </summary>
    public static final int IN_FIELD_RESULT = 1;

    /// <summary>
    /// Ignores invalid field chars sequence.
    /// </summary>
    public static final int SAFE_MODE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldCharCounterOptions value.
    /// </summary>
    public static String getName(/*FieldCharCounterOptions*/int fieldCharCounterOptions)
    {
    	switch (fieldCharCounterOptions)
    	{
    		case DEFAULT: return "DEFAULT";
    		case IN_FIELD_RESULT: return "IN_FIELD_RESULT";
    		case SAFE_MODE: return "SAFE_MODE";
    		default: return "Unknown FieldCharCounterOptions value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer FieldCharCounterOptions value.
    /// </summary>
    public static Set<String> getNames(/*FieldCharCounterOptions*/int fieldCharCounterOptions)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((DEFAULT & fieldCharCounterOptions) == DEFAULT) names.add("DEFAULT");
    	if ((IN_FIELD_RESULT & fieldCharCounterOptions) == IN_FIELD_RESULT) names.add("IN_FIELD_RESULT");
    	if ((SAFE_MODE & fieldCharCounterOptions) == SAFE_MODE) names.add("SAFE_MODE");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldCharCounterOptions value.
    /// </summary>
    public static String toString(/*FieldCharCounterOptions*/int fieldCharCounterOptions)
    {
    	switch (fieldCharCounterOptions)
    	{
    		case DEFAULT: return "Default";
    		case IN_FIELD_RESULT: return "InFieldResult";
    		case SAFE_MODE: return "SafeMode";
    		default: return toStringSet(fieldCharCounterOptions);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldCharCounterOptions value.
    /// </summary>
    public static String toStringSet(int attr)
    {
    	String result = "";
    	String separator = "";
    	for (int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by FieldCharCounterOptions name.
    /// </summary>
    public static /*FieldCharCounterOptions*/int fromName(String fieldCharCounterOptionsName)
    {
    	if ("DEFAULT".equals(fieldCharCounterOptionsName)) return DEFAULT;
    	if ("IN_FIELD_RESULT".equals(fieldCharCounterOptionsName)) return IN_FIELD_RESULT;
    	if ("SAFE_MODE".equals(fieldCharCounterOptionsName)) return SAFE_MODE;
    	throw new IllegalArgumentException("Unknown FieldCharCounterOptions name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by FieldCharCounterOptions names.
    /// </summary>
    public static /*FieldCharCounterOptions*/int fromNames(Set<String> fieldCharCounterOptionsNames)
    {
    	/*FieldCharCounterOptions*/int result = 0;
    	for (String name : fieldCharCounterOptionsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of FieldCharCounterOptions values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFAULT,
    		IN_FIELD_RESULT,
    		SAFE_MODE,
    	};
    }

    public static final int length = 3;
}

