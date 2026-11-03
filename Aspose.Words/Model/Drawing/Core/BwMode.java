// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/02/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how a shape will render on a black and white device.
/// </summary>
/// <seealso cref="ShapeAttr.BWMode"/>
/// <seealso cref="ShapeAttr.BWNormal"/>
/// <seealso cref="ShapeAttr.BWPure"/>
/*enum*/ final class BWMode
{
    private BWMode(){}
    
    /// <summary>
    /// Only used for predefined shades.
    /// </summary>
    public static final int COLOR = 0;          
    /// <summary>
    /// Depends on object type.
    /// </summary>
    public static final int AUTOMATIC = 1;      
    /// <summary>
    /// Shades of gray only.
    /// </summary>
    public static final int GRAY_SCALE = 2;      
    /// <summary>
    /// Shades of light gray only.
    /// </summary>
    public static final int LIGHT_GRAY_SCALE = 3; 
    /// <summary>
    /// Dark gray mapped to light gray, etc.
    /// </summary>
    public static final int INVERSE_GRAY = 4;    
    /// <summary>
    /// Pure gray and white.
    /// </summary>
    public static final int GRAY_OUTLINE = 5;    
    /// <summary>
    /// Black text and lines, all else grayscale.
    /// </summary>
    public static final int BLACK_TEXT_LINE = 6;  
    /// <summary>
    /// Pure black and white mode (no grays).
    /// </summary>
    public static final int HIGH_CONTRAST = 7;   
    /// <summary>
    /// Solid black.
    /// </summary>
    public static final int BLACK = 8;          
    /// <summary>
    /// Solid white.
    /// </summary>
    public static final int WHITE = 9;          
    /// <summary>
    /// Object not drawn.
    /// </summary>
    public static final int DONT_SHOW = 10;
    /// <summary>
    /// Default value is <see cref="Automatic"/>.
    /// </summary>
    public static final int DEFAULT = AUTOMATIC;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer BWMode value.
    /// </summary>
    public static String getName(/*BWMode*/int bWMode)
    {
    	switch (bWMode)
    	{
    		case COLOR: return "COLOR";
    		case AUTOMATIC:
    		/*case DEFAULT:*/
    			return "AUTOMATIC" + " | " + "DEFAULT";
    		case GRAY_SCALE: return "GRAY_SCALE";
    		case LIGHT_GRAY_SCALE: return "LIGHT_GRAY_SCALE";
    		case INVERSE_GRAY: return "INVERSE_GRAY";
    		case GRAY_OUTLINE: return "GRAY_OUTLINE";
    		case BLACK_TEXT_LINE: return "BLACK_TEXT_LINE";
    		case HIGH_CONTRAST: return "HIGH_CONTRAST";
    		case BLACK: return "BLACK";
    		case WHITE: return "WHITE";
    		case DONT_SHOW: return "DONT_SHOW";
    		default: return "Unknown BWMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer BWMode value.
    /// </summary>
    public static String toString(/*BWMode*/int bWMode)
    {
    	switch (bWMode)
    	{
    		case COLOR: return "Color";
    		case AUTOMATIC:
    		/*case DEFAULT:*/
    			return "Automatic" + " | " + "Default";
    		case GRAY_SCALE: return "GrayScale";
    		case LIGHT_GRAY_SCALE: return "LightGrayScale";
    		case INVERSE_GRAY: return "InverseGray";
    		case GRAY_OUTLINE: return "GrayOutline";
    		case BLACK_TEXT_LINE: return "BlackTextLine";
    		case HIGH_CONTRAST: return "HighContrast";
    		case BLACK: return "Black";
    		case WHITE: return "White";
    		case DONT_SHOW: return "DontShow";
    		default: return "Unknown BWMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by BWMode name.
    /// </summary>
    public static /*BWMode*/int fromName(String bWModeName)
    {
    	if ("COLOR".equals(bWModeName)) return COLOR;
    	if ("AUTOMATIC".equals(bWModeName)) return AUTOMATIC;
    	if ("GRAY_SCALE".equals(bWModeName)) return GRAY_SCALE;
    	if ("LIGHT_GRAY_SCALE".equals(bWModeName)) return LIGHT_GRAY_SCALE;
    	if ("INVERSE_GRAY".equals(bWModeName)) return INVERSE_GRAY;
    	if ("GRAY_OUTLINE".equals(bWModeName)) return GRAY_OUTLINE;
    	if ("BLACK_TEXT_LINE".equals(bWModeName)) return BLACK_TEXT_LINE;
    	if ("HIGH_CONTRAST".equals(bWModeName)) return HIGH_CONTRAST;
    	if ("BLACK".equals(bWModeName)) return BLACK;
    	if ("WHITE".equals(bWModeName)) return WHITE;
    	if ("DONT_SHOW".equals(bWModeName)) return DONT_SHOW;
    	if ("DEFAULT".equals(bWModeName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown BWMode name.");
    }
    
    /// <summary>
    /// Returns array of BWMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		COLOR,
    		AUTOMATIC,
    		GRAY_SCALE,
    		LIGHT_GRAY_SCALE,
    		INVERSE_GRAY,
    		GRAY_OUTLINE,
    		BLACK_TEXT_LINE,
    		HIGH_CONTRAST,
    		BLACK,
    		WHITE,
    		DONT_SHOW,
    		DEFAULT,
    	};
    }

    public static final int length = 12;
}

