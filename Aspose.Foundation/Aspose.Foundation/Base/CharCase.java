// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/12/2016 by Alexey Butalov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


public /*enum*/ final class CharCase
{
    private CharCase(){}
    
    /// <summary>
    /// No change to existing char case.
    /// </summary>
    public static final int DEFAULT = 0;
    public static final int UPPER = 1;
    public static final int LOWER = 2;
    public static final int CAPS = 3;
    public static final int FIRST_CAP = 4;

    /// <summary>
    /// Indicates th DBCHAR formatting.
    /// </summary>
    public static final int DB_CHAR = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CharCase value.
    /// </summary>
    public static String getName(/*CharCase*/int charCase)
    {
    	switch (charCase)
    	{
    		case DEFAULT: return "DEFAULT";
    		case UPPER: return "UPPER";
    		case LOWER: return "LOWER";
    		case CAPS: return "CAPS";
    		case FIRST_CAP: return "FIRST_CAP";
    		case DB_CHAR: return "DB_CHAR";
    		default: return "Unknown CharCase value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CharCase value.
    /// </summary>
    public static String toString(/*CharCase*/int charCase)
    {
    	switch (charCase)
    	{
    		case DEFAULT: return "Default";
    		case UPPER: return "Upper";
    		case LOWER: return "Lower";
    		case CAPS: return "Caps";
    		case FIRST_CAP: return "FirstCap";
    		case DB_CHAR: return "DbChar";
    		default: return "Unknown CharCase value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CharCase name.
    /// </summary>
    public static /*CharCase*/int fromName(String charCaseName)
    {
    	if ("DEFAULT".equals(charCaseName)) return DEFAULT;
    	if ("UPPER".equals(charCaseName)) return UPPER;
    	if ("LOWER".equals(charCaseName)) return LOWER;
    	if ("CAPS".equals(charCaseName)) return CAPS;
    	if ("FIRST_CAP".equals(charCaseName)) return FIRST_CAP;
    	if ("DB_CHAR".equals(charCaseName)) return DB_CHAR;
    	throw new IllegalArgumentException("Unknown CharCase name.");
    }
    
    /// <summary>
    /// Returns array of CharCase values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFAULT,
    		UPPER,
    		LOWER,
    		CAPS,
    		FIRST_CAP,
    		DB_CHAR,
    	};
    }

    public static final int length = 6;
}

