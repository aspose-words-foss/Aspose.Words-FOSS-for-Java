// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class DmlFontType
{
    private DmlFontType(){}
    
    public static final int LATIN = 0;
    public static final int EAST_ASIAN = 1;
    public static final int SYMBOL = 2;
    public static final int COMPLEX_SCRIPT = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlFontType value.
    /// </summary>
    public static String getName(/*DmlFontType*/int dmlFontType)
    {
    	switch (dmlFontType)
    	{
    		case LATIN: return "LATIN";
    		case EAST_ASIAN: return "EAST_ASIAN";
    		case SYMBOL: return "SYMBOL";
    		case COMPLEX_SCRIPT: return "COMPLEX_SCRIPT";
    		default: return "Unknown DmlFontType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlFontType value.
    /// </summary>
    public static String toString(/*DmlFontType*/int dmlFontType)
    {
    	switch (dmlFontType)
    	{
    		case LATIN: return "Latin";
    		case EAST_ASIAN: return "EastAsian";
    		case SYMBOL: return "Symbol";
    		case COMPLEX_SCRIPT: return "ComplexScript";
    		default: return "Unknown DmlFontType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlFontType name.
    /// </summary>
    public static /*DmlFontType*/int fromName(String dmlFontTypeName)
    {
    	if ("LATIN".equals(dmlFontTypeName)) return LATIN;
    	if ("EAST_ASIAN".equals(dmlFontTypeName)) return EAST_ASIAN;
    	if ("SYMBOL".equals(dmlFontTypeName)) return SYMBOL;
    	if ("COMPLEX_SCRIPT".equals(dmlFontTypeName)) return COMPLEX_SCRIPT;
    	throw new IllegalArgumentException("Unknown DmlFontType name.");
    }
    
    /// <summary>
    /// Returns array of DmlFontType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LATIN,
    		EAST_ASIAN,
    		SYMBOL,
    		COMPLEX_SCRIPT,
    	};
    }

    public static final int length = 4;
}
