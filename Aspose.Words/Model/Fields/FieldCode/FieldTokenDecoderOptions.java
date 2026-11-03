// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/05/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Represents options for the <see cref="FieldTokenDecoder"/> class.
/// </summary>
/*enum*/ final class FieldTokenDecoderOptions
{
    private FieldTokenDecoderOptions(){}
    
    public static final int NONE = 0;
    public static final int ESCAPE_CHARS = 1;
    public static final int TRIM_DOUBLE_QUOTES = 2;
    public static final int PRESERVE_SYMBOLIC_QUOTES = 4;

    public static final int ALL = ESCAPE_CHARS | TRIM_DOUBLE_QUOTES | PRESERVE_SYMBOLIC_QUOTES;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldTokenDecoderOptions value.
    /// </summary>
    public static String getName(/*FieldTokenDecoderOptions*/int fieldTokenDecoderOptions)
    {
    	switch (fieldTokenDecoderOptions)
    	{
    		case NONE: return "NONE";
    		case ESCAPE_CHARS: return "ESCAPE_CHARS";
    		case TRIM_DOUBLE_QUOTES: return "TRIM_DOUBLE_QUOTES";
    		case PRESERVE_SYMBOLIC_QUOTES: return "PRESERVE_SYMBOLIC_QUOTES";
    		case ALL: return "ALL";
    		default: return "Unknown FieldTokenDecoderOptions value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer FieldTokenDecoderOptions value.
    /// </summary>
    public static Set<String> getNames(/*FieldTokenDecoderOptions*/int fieldTokenDecoderOptions)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & fieldTokenDecoderOptions) == NONE) names.add("NONE");
    	if ((ESCAPE_CHARS & fieldTokenDecoderOptions) == ESCAPE_CHARS) names.add("ESCAPE_CHARS");
    	if ((TRIM_DOUBLE_QUOTES & fieldTokenDecoderOptions) == TRIM_DOUBLE_QUOTES) names.add("TRIM_DOUBLE_QUOTES");
    	if ((PRESERVE_SYMBOLIC_QUOTES & fieldTokenDecoderOptions) == PRESERVE_SYMBOLIC_QUOTES) names.add("PRESERVE_SYMBOLIC_QUOTES");
    	if ((ALL & fieldTokenDecoderOptions) == ALL) names.add("ALL");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldTokenDecoderOptions value.
    /// </summary>
    public static String toString(/*FieldTokenDecoderOptions*/int fieldTokenDecoderOptions)
    {
    	switch (fieldTokenDecoderOptions)
    	{
    		case NONE: return "None";
    		case ESCAPE_CHARS: return "EscapeChars";
    		case TRIM_DOUBLE_QUOTES: return "TrimDoubleQuotes";
    		case PRESERVE_SYMBOLIC_QUOTES: return "PreserveSymbolicQuotes";
    		case ALL: return "All";
    		default: return toStringSet(fieldTokenDecoderOptions);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldTokenDecoderOptions value.
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
    /// Returns integer representation by FieldTokenDecoderOptions name.
    /// </summary>
    public static /*FieldTokenDecoderOptions*/int fromName(String fieldTokenDecoderOptionsName)
    {
    	if ("NONE".equals(fieldTokenDecoderOptionsName)) return NONE;
    	if ("ESCAPE_CHARS".equals(fieldTokenDecoderOptionsName)) return ESCAPE_CHARS;
    	if ("TRIM_DOUBLE_QUOTES".equals(fieldTokenDecoderOptionsName)) return TRIM_DOUBLE_QUOTES;
    	if ("PRESERVE_SYMBOLIC_QUOTES".equals(fieldTokenDecoderOptionsName)) return PRESERVE_SYMBOLIC_QUOTES;
    	if ("ALL".equals(fieldTokenDecoderOptionsName)) return ALL;
    	throw new IllegalArgumentException("Unknown FieldTokenDecoderOptions name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by FieldTokenDecoderOptions names.
    /// </summary>
    public static /*FieldTokenDecoderOptions*/int fromNames(Set<String> fieldTokenDecoderOptionsNames)
    {
    	/*FieldTokenDecoderOptions*/int result = 0;
    	for (String name : fieldTokenDecoderOptionsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of FieldTokenDecoderOptions values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		ESCAPE_CHARS,
    		TRIM_DOUBLE_QUOTES,
    		PRESERVE_SYMBOLIC_QUOTES,
    		ALL,
    	};
    }

    public static final int length = 5;
}

