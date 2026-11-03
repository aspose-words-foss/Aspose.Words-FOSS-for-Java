// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/04/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies possible values in which numerals can be displayed.
/// </summary>
/*enum*/ final class NumForm
{
    private NumForm(){}
    
    /// <summary>
    /// Numerals are displayed in the font’s default form.
    /// </summary>
    public static final int DEFAULT = 0;

    /// <summary>
    /// Lining numerals are displayed if the font supports them.
    /// </summary>
    public static final int LINING = 1;

    /// <summary>
    /// Oldstyle numerals are displayed if the font supports them.
    /// </summary>
    public static final int OLD_STYLE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer NumForm value.
    /// </summary>
    public static String getName(/*NumForm*/int numForm)
    {
    	switch (numForm)
    	{
    		case DEFAULT: return "DEFAULT";
    		case LINING: return "LINING";
    		case OLD_STYLE: return "OLD_STYLE";
    		default: return "Unknown NumForm value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NumForm value.
    /// </summary>
    public static String toString(/*NumForm*/int numForm)
    {
    	switch (numForm)
    	{
    		case DEFAULT: return "Default";
    		case LINING: return "Lining";
    		case OLD_STYLE: return "OldStyle";
    		default: return "Unknown NumForm value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by NumForm name.
    /// </summary>
    public static /*NumForm*/int fromName(String numFormName)
    {
    	if ("DEFAULT".equals(numFormName)) return DEFAULT;
    	if ("LINING".equals(numFormName)) return LINING;
    	if ("OLD_STYLE".equals(numFormName)) return OLD_STYLE;
    	throw new IllegalArgumentException("Unknown NumForm name.");
    }
    
    /// <summary>
    /// Returns array of NumForm values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFAULT,
    		LINING,
    		OLD_STYLE,
    	};
    }

    public static final int length = 3;
}


