// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/01/2011 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows to specify how text inside a document should be rendered
/// when saving in SVG format.
/// </summary>
public /*enum*/ final class SvgTextOutputMode
{
    private SvgTextOutputMode(){}
    
    /// <summary>
    /// SVG fonts are used to render text. Note, not all browsers support SVG fonts.
    /// </summary>
    public static final int USE_SVG_FONTS = 0;
    /// <summary>
    /// Fonts installed on the target machine are used to render text. 
    /// Note, if some of fonts used in the document are not available on the target machine, document can look differently.
    /// </summary>
    public static final int USE_TARGET_MACHINE_FONTS = 1;
    /// <summary>
    /// Text is rendered using curves. Note, text selection will not work if you use this option.
    /// </summary>
    public static final int USE_PLACED_GLYPHS = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SvgTextOutputMode value.
    /// </summary>
    public static String getName(/*SvgTextOutputMode*/int svgTextOutputMode)
    {
    	switch (svgTextOutputMode)
    	{
    		case USE_SVG_FONTS: return "USE_SVG_FONTS";
    		case USE_TARGET_MACHINE_FONTS: return "USE_TARGET_MACHINE_FONTS";
    		case USE_PLACED_GLYPHS: return "USE_PLACED_GLYPHS";
    		default: return "Unknown SvgTextOutputMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SvgTextOutputMode value.
    /// </summary>
    public static String toString(/*SvgTextOutputMode*/int svgTextOutputMode)
    {
    	switch (svgTextOutputMode)
    	{
    		case USE_SVG_FONTS: return "UseSvgFonts";
    		case USE_TARGET_MACHINE_FONTS: return "UseTargetMachineFonts";
    		case USE_PLACED_GLYPHS: return "UsePlacedGlyphs";
    		default: return "Unknown SvgTextOutputMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SvgTextOutputMode name.
    /// </summary>
    public static /*SvgTextOutputMode*/int fromName(String svgTextOutputModeName)
    {
    	if ("USE_SVG_FONTS".equals(svgTextOutputModeName)) return USE_SVG_FONTS;
    	if ("USE_TARGET_MACHINE_FONTS".equals(svgTextOutputModeName)) return USE_TARGET_MACHINE_FONTS;
    	if ("USE_PLACED_GLYPHS".equals(svgTextOutputModeName)) return USE_PLACED_GLYPHS;
    	throw new IllegalArgumentException("Unknown SvgTextOutputMode name.");
    }
    
    /// <summary>
    /// Returns array of SvgTextOutputMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		USE_SVG_FONTS,
    		USE_TARGET_MACHINE_FONTS,
    		USE_PLACED_GLYPHS,
    	};
    }

    public static final int length = 3;
}

