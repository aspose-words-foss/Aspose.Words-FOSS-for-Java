// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies marker symbol style.
/// </summary>
public /*enum*/ final class MarkerSymbol
{
    private MarkerSymbol(){}
    
    // Corresponds ST_MarkerStyle simple type (5.7.3.27).
    
    /// <summary>
    /// Specifies a default marker symbol shall be drawn at each data point.
    /// </summary>
    public static final int DEFAULT = 0;

    /// <summary>
    /// Specifies a circle shall be drawn at each data point.
    /// </summary>
    public static final int CIRCLE = 1;

    /// <summary>
    /// Specifies a dash shall be drawn at each data point.
    /// </summary>
    public static final int DASH = 2;

    /// <summary>
    /// Specifies a diamond shall be drawn at each data point.
    /// </summary>
    public static final int DIAMOND = 3;

    /// <summary>
    /// Specifies a dot shall be drawn at each data point.
    /// </summary>
    public static final int DOT = 4;

    /// <summary>
    /// Specifies nothing shall be drawn at each data point.
    /// </summary>
    public static final int NONE = 5;

    /// <summary>
    /// Specifies a picture shall be drawn at each data point.
    /// </summary>
    public static final int PICTURE = 6;

    /// <summary>
    /// Specifies a plus shall be drawn at each data point.
    /// </summary>
    public static final int PLUS = 7;

    /// <summary>
    /// Specifies a square shall be drawn at each data point.
    /// </summary>
    public static final int SQUARE = 8;

    /// <summary>
    /// Specifies a star shall be drawn at each data point.
    /// </summary>
    public static final int STAR = 9;

    /// <summary>
    /// Specifies a triangle shall be drawn at each data point.
    /// </summary>
    public static final int TRIANGLE = 10;

    /// <summary>
    /// Specifies an X shall be drawn at each data point.
    /// </summary>
    public static final int X = 11;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MarkerSymbol value.
    /// </summary>
    public static String getName(/*MarkerSymbol*/int markerSymbol)
    {
    	switch (markerSymbol)
    	{
    		case DEFAULT: return "DEFAULT";
    		case CIRCLE: return "CIRCLE";
    		case DASH: return "DASH";
    		case DIAMOND: return "DIAMOND";
    		case DOT: return "DOT";
    		case NONE: return "NONE";
    		case PICTURE: return "PICTURE";
    		case PLUS: return "PLUS";
    		case SQUARE: return "SQUARE";
    		case STAR: return "STAR";
    		case TRIANGLE: return "TRIANGLE";
    		case X: return "X";
    		default: return "Unknown MarkerSymbol value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MarkerSymbol value.
    /// </summary>
    public static String toString(/*MarkerSymbol*/int markerSymbol)
    {
    	switch (markerSymbol)
    	{
    		case DEFAULT: return "Default";
    		case CIRCLE: return "Circle";
    		case DASH: return "Dash";
    		case DIAMOND: return "Diamond";
    		case DOT: return "Dot";
    		case NONE: return "None";
    		case PICTURE: return "Picture";
    		case PLUS: return "Plus";
    		case SQUARE: return "Square";
    		case STAR: return "Star";
    		case TRIANGLE: return "Triangle";
    		case X: return "X";
    		default: return "Unknown MarkerSymbol value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MarkerSymbol name.
    /// </summary>
    public static /*MarkerSymbol*/int fromName(String markerSymbolName)
    {
    	if ("DEFAULT".equals(markerSymbolName)) return DEFAULT;
    	if ("CIRCLE".equals(markerSymbolName)) return CIRCLE;
    	if ("DASH".equals(markerSymbolName)) return DASH;
    	if ("DIAMOND".equals(markerSymbolName)) return DIAMOND;
    	if ("DOT".equals(markerSymbolName)) return DOT;
    	if ("NONE".equals(markerSymbolName)) return NONE;
    	if ("PICTURE".equals(markerSymbolName)) return PICTURE;
    	if ("PLUS".equals(markerSymbolName)) return PLUS;
    	if ("SQUARE".equals(markerSymbolName)) return SQUARE;
    	if ("STAR".equals(markerSymbolName)) return STAR;
    	if ("TRIANGLE".equals(markerSymbolName)) return TRIANGLE;
    	if ("X".equals(markerSymbolName)) return X;
    	throw new IllegalArgumentException("Unknown MarkerSymbol name.");
    }
    
    /// <summary>
    /// Returns array of MarkerSymbol values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFAULT,
    		CIRCLE,
    		DASH,
    		DIAMOND,
    		DOT,
    		NONE,
    		PICTURE,
    		PLUS,
    		SQUARE,
    		STAR,
    		TRIANGLE,
    		X,
    	};
    }

    public static final int length = 12;
}

