// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/01/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the font family.
/// </summary>
/// <remarks>
/// <para>A font family is a set of fonts having common stroke width and serif characteristics.</para>
/// </remarks>
public /*enum*/ final class FontFamily
{
    private FontFamily(){}
    
    /// <summary>
    /// Specifies a generic family name. This name is used when information about a font 
    /// does not exist or does not matter. The default font is used.
    /// </summary>
    public static final int AUTO = 0;
    /// <summary>
    /// Specifies a proportional font with serifs. An example is Times New Roman.
    /// </summary>
    public static final int ROMAN = 1;
    /// <summary>
    /// Specifies a proportional font without serifs. An example is Arial.
    /// </summary>
    public static final int SWISS = 2;
    /// <summary>
    /// Specifies a monospace font with or without serifs. Monospace fonts are 
    /// usually modern; examples include Pica, Elite, and Courier New.
    /// </summary>
    public static final int MODERN = 3;
    /// <summary>
    /// Specifies a font that is designed to look like handwriting; examples include Script and Cursive.
    /// </summary>
    public static final int SCRIPT = 4;
    /// <summary>
    /// Specifies a novelty font. An example is Old English.
    /// </summary>
    public static final int DECORATIVE = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FontFamily value.
    /// </summary>
    public static String getName(/*FontFamily*/int fontFamily)
    {
    	switch (fontFamily)
    	{
    		case AUTO: return "AUTO";
    		case ROMAN: return "ROMAN";
    		case SWISS: return "SWISS";
    		case MODERN: return "MODERN";
    		case SCRIPT: return "SCRIPT";
    		case DECORATIVE: return "DECORATIVE";
    		default: return "Unknown FontFamily value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FontFamily value.
    /// </summary>
    public static String toString(/*FontFamily*/int fontFamily)
    {
    	switch (fontFamily)
    	{
    		case AUTO: return "Auto";
    		case ROMAN: return "Roman";
    		case SWISS: return "Swiss";
    		case MODERN: return "Modern";
    		case SCRIPT: return "Script";
    		case DECORATIVE: return "Decorative";
    		default: return "Unknown FontFamily value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FontFamily name.
    /// </summary>
    public static /*FontFamily*/int fromName(String fontFamilyName)
    {
    	if ("AUTO".equals(fontFamilyName)) return AUTO;
    	if ("ROMAN".equals(fontFamilyName)) return ROMAN;
    	if ("SWISS".equals(fontFamilyName)) return SWISS;
    	if ("MODERN".equals(fontFamilyName)) return MODERN;
    	if ("SCRIPT".equals(fontFamilyName)) return SCRIPT;
    	if ("DECORATIVE".equals(fontFamilyName)) return DECORATIVE;
    	throw new IllegalArgumentException("Unknown FontFamily name.");
    }
    
    /// <summary>
    /// Returns array of FontFamily values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AUTO,
    		ROMAN,
    		SWISS,
    		MODERN,
    		SCRIPT,
    		DECORATIVE,
    	};
    }

    public static final int length = 6;
}

