// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/01/2011 by Andrey Soldatov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies the style of an embedded font inside a <see cref="FontInfo"/> object.
/// </summary>
public /*enum*/ final class EmbeddedFontStyle
{
    private EmbeddedFontStyle(){}
    
    //Integer values of enum items are used on reading/writing DOC files. So do not change them.

    /// <summary>
    /// Specifies the Regular embedded font.
    /// </summary>
    public static final int REGULAR = 0;

    /// <summary>
    /// Specifies the Bold embedded font.
    /// </summary>
    public static final int BOLD = 1;

    /// <summary>
    /// Specifies the Italic embedded font.
    /// </summary>
    public static final int ITALIC = 2;

    /// <summary>
    /// Specifies the Bold-Italic embedded font.
    /// </summary>
    public static final int BOLD_ITALIC = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer EmbeddedFontStyle value.
    /// </summary>
    public static String getName(/*EmbeddedFontStyle*/int embeddedFontStyle)
    {
    	switch (embeddedFontStyle)
    	{
    		case REGULAR: return "REGULAR";
    		case BOLD: return "BOLD";
    		case ITALIC: return "ITALIC";
    		case BOLD_ITALIC: return "BOLD_ITALIC";
    		default: return "Unknown EmbeddedFontStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer EmbeddedFontStyle value.
    /// </summary>
    public static Set<String> getNames(/*EmbeddedFontStyle*/int embeddedFontStyle)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((REGULAR & embeddedFontStyle) == REGULAR) names.add("REGULAR");
    	if ((BOLD & embeddedFontStyle) == BOLD) names.add("BOLD");
    	if ((ITALIC & embeddedFontStyle) == ITALIC) names.add("ITALIC");
    	if ((BOLD_ITALIC & embeddedFontStyle) == BOLD_ITALIC) names.add("BOLD_ITALIC");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer EmbeddedFontStyle value.
    /// </summary>
    public static String toString(/*EmbeddedFontStyle*/int embeddedFontStyle)
    {
    	switch (embeddedFontStyle)
    	{
    		case REGULAR: return "Regular";
    		case BOLD: return "Bold";
    		case ITALIC: return "Italic";
    		case BOLD_ITALIC: return "BoldItalic";
    		default: return toStringSet(embeddedFontStyle);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer EmbeddedFontStyle value.
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
    /// Returns integer representation by EmbeddedFontStyle name.
    /// </summary>
    public static /*EmbeddedFontStyle*/int fromName(String embeddedFontStyleName)
    {
    	if ("REGULAR".equals(embeddedFontStyleName)) return REGULAR;
    	if ("BOLD".equals(embeddedFontStyleName)) return BOLD;
    	if ("ITALIC".equals(embeddedFontStyleName)) return ITALIC;
    	if ("BOLD_ITALIC".equals(embeddedFontStyleName)) return BOLD_ITALIC;
    	throw new IllegalArgumentException("Unknown EmbeddedFontStyle name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by EmbeddedFontStyle names.
    /// </summary>
    public static /*EmbeddedFontStyle*/int fromNames(Set<String> embeddedFontStyleNames)
    {
    	/*EmbeddedFontStyle*/int result = 0;
    	for (String name : embeddedFontStyleNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of EmbeddedFontStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		REGULAR,
    		BOLD,
    		ITALIC,
    		BOLD_ITALIC,
    	};
    }

    public static final int length = 4;
}

