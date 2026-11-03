// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/04/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies possible values in which numeral spacing can be displayed.
/// </summary>
public /*enum*/ final class NumSpacing
{
    private NumSpacing(){}
    
    /// <summary>
    /// Specifies that numerals are displayed in the font’s default form.
    /// </summary>
    public static final int DEFAULT = 0;

    /// <summary>
    /// Specifies that the forms of the numerals designed as proportionally spaced are displayed if supported by the font.
    /// </summary>
    public static final int PROPORTIONAL = 1;

    /// <summary>
    /// Specifies that the forms of the numerals designed as tabular are displayed if supported by the font.
    /// </summary>
    public static final int TABULAR = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer NumSpacing value.
    /// </summary>
    public static String getName(/*NumSpacing*/int numSpacing)
    {
    	switch (numSpacing)
    	{
    		case DEFAULT: return "DEFAULT";
    		case PROPORTIONAL: return "PROPORTIONAL";
    		case TABULAR: return "TABULAR";
    		default: return "Unknown NumSpacing value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NumSpacing value.
    /// </summary>
    public static String toString(/*NumSpacing*/int numSpacing)
    {
    	switch (numSpacing)
    	{
    		case DEFAULT: return "Default";
    		case PROPORTIONAL: return "Proportional";
    		case TABULAR: return "Tabular";
    		default: return "Unknown NumSpacing value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by NumSpacing name.
    /// </summary>
    public static /*NumSpacing*/int fromName(String numSpacingName)
    {
    	if ("DEFAULT".equals(numSpacingName)) return DEFAULT;
    	if ("PROPORTIONAL".equals(numSpacingName)) return PROPORTIONAL;
    	if ("TABULAR".equals(numSpacingName)) return TABULAR;
    	throw new IllegalArgumentException("Unknown NumSpacing name.");
    }
    
    /// <summary>
    /// Returns array of NumSpacing values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFAULT,
    		PROPORTIONAL,
    		TABULAR,
    	};
    }

    public static final int length = 3;
}

