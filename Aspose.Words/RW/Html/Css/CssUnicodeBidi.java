// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/07/2014 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Values of the 'unicode-bidi' CSS property. Control how HTML element's text is processed by the Unicode bidi algorithm.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/CSS2/visuren.html#propdef-unicode-bidi for description of the property.
/// </remarks>
/*enum*/ final class CssUnicodeBidi
{
    private CssUnicodeBidi(){}
    
    public static final int NORMAL = 0;
    public static final int EMBED = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssUnicodeBidi value.
    /// </summary>
    public static String getName(/*CssUnicodeBidi*/int cssUnicodeBidi)
    {
    	switch (cssUnicodeBidi)
    	{
    		case NORMAL: return "NORMAL";
    		case EMBED: return "EMBED";
    		default: return "Unknown CssUnicodeBidi value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssUnicodeBidi value.
    /// </summary>
    public static String toString(/*CssUnicodeBidi*/int cssUnicodeBidi)
    {
    	switch (cssUnicodeBidi)
    	{
    		case NORMAL: return "Normal";
    		case EMBED: return "Embed";
    		default: return "Unknown CssUnicodeBidi value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CssUnicodeBidi name.
    /// </summary>
    public static /*CssUnicodeBidi*/int fromName(String cssUnicodeBidiName)
    {
    	if ("NORMAL".equals(cssUnicodeBidiName)) return NORMAL;
    	if ("EMBED".equals(cssUnicodeBidiName)) return EMBED;
    	throw new IllegalArgumentException("Unknown CssUnicodeBidi name.");
    }
    
    /// <summary>
    /// Returns array of CssUnicodeBidi values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NORMAL,
    		EMBED,
    	};
    }

    public static final int length = 2;
}

