// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/01/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the font pitch. 
/// </summary>
/// <remarks>The pitch indicates if the font is fixed pitch, proportionally spaced, or relies on a default setting.</remarks>
public /*enum*/ final class FontPitch
{
    private FontPitch(){}
    
    /// <summary>
    /// Specifies that no information is available about the pitch of a font.
    /// </summary>
    public static final int DEFAULT = 0;
    /// <summary>
    /// Specifies that this is a fixed width font.
    /// </summary>
    public static final int FIXED = 1;
    /// <summary>
    /// Specifies that this is a proportional width font.
    /// </summary>
    public static final int VARIABLE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FontPitch value.
    /// </summary>
    public static String getName(/*FontPitch*/int fontPitch)
    {
    	switch (fontPitch)
    	{
    		case DEFAULT: return "DEFAULT";
    		case FIXED: return "FIXED";
    		case VARIABLE: return "VARIABLE";
    		default: return "Unknown FontPitch value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FontPitch value.
    /// </summary>
    public static String toString(/*FontPitch*/int fontPitch)
    {
    	switch (fontPitch)
    	{
    		case DEFAULT: return "Default";
    		case FIXED: return "Fixed";
    		case VARIABLE: return "Variable";
    		default: return "Unknown FontPitch value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FontPitch name.
    /// </summary>
    public static /*FontPitch*/int fromName(String fontPitchName)
    {
    	if ("DEFAULT".equals(fontPitchName)) return DEFAULT;
    	if ("FIXED".equals(fontPitchName)) return FIXED;
    	if ("VARIABLE".equals(fontPitchName)) return VARIABLE;
    	throw new IllegalArgumentException("Unknown FontPitch name.");
    }
    
    /// <summary>
    /// Returns array of FontPitch values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFAULT,
    		FIXED,
    		VARIABLE,
    	};
    }

    public static final int length = 3;
}


