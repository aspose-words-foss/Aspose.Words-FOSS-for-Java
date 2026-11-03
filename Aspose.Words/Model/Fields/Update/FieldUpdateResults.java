// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/11/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies results of a field update.
/// </summary>
/*enum*/ final class FieldUpdateResults
{
    private FieldUpdateResults(){}
    
    /// <summary>
    /// Specifies that nothing was changed during a field update.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Specifies that a field's result was changed during its update.
    /// </summary>
    public static final int FIELD_RESULT_CHANGED = 1;
    /// <summary>
    /// Specifies that a field's code was changed during its update.
    /// </summary>
    public static final int FIELD_CODE_CHANGED = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldUpdateResults value.
    /// </summary>
    public static String getName(/*FieldUpdateResults*/int fieldUpdateResults)
    {
    	switch (fieldUpdateResults)
    	{
    		case NONE: return "NONE";
    		case FIELD_RESULT_CHANGED: return "FIELD_RESULT_CHANGED";
    		case FIELD_CODE_CHANGED: return "FIELD_CODE_CHANGED";
    		default: return "Unknown FieldUpdateResults value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer FieldUpdateResults value.
    /// </summary>
    public static Set<String> getNames(/*FieldUpdateResults*/int fieldUpdateResults)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & fieldUpdateResults) == NONE) names.add("NONE");
    	if ((FIELD_RESULT_CHANGED & fieldUpdateResults) == FIELD_RESULT_CHANGED) names.add("FIELD_RESULT_CHANGED");
    	if ((FIELD_CODE_CHANGED & fieldUpdateResults) == FIELD_CODE_CHANGED) names.add("FIELD_CODE_CHANGED");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldUpdateResults value.
    /// </summary>
    public static String toString(/*FieldUpdateResults*/int fieldUpdateResults)
    {
    	switch (fieldUpdateResults)
    	{
    		case NONE: return "None";
    		case FIELD_RESULT_CHANGED: return "FieldResultChanged";
    		case FIELD_CODE_CHANGED: return "FieldCodeChanged";
    		default: return toStringSet(fieldUpdateResults);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldUpdateResults value.
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
    /// Returns integer representation by FieldUpdateResults name.
    /// </summary>
    public static /*FieldUpdateResults*/int fromName(String fieldUpdateResultsName)
    {
    	if ("NONE".equals(fieldUpdateResultsName)) return NONE;
    	if ("FIELD_RESULT_CHANGED".equals(fieldUpdateResultsName)) return FIELD_RESULT_CHANGED;
    	if ("FIELD_CODE_CHANGED".equals(fieldUpdateResultsName)) return FIELD_CODE_CHANGED;
    	throw new IllegalArgumentException("Unknown FieldUpdateResults name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by FieldUpdateResults names.
    /// </summary>
    public static /*FieldUpdateResults*/int fromNames(Set<String> fieldUpdateResultsNames)
    {
    	/*FieldUpdateResults*/int result = 0;
    	for (String name : fieldUpdateResultsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of FieldUpdateResults values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		FIELD_RESULT_CHANGED,
    		FIELD_CODE_CHANGED,
    	};
    }

    public static final int length = 3;
}

