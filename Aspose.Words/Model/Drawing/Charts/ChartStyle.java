// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/01/2025 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies predefined styles of a chart.
/// </summary>
public /*enum*/ final class ChartStyle
{
    private ChartStyle(){}
    
    /// <summary>
    /// Represents the default chart style.
    /// </summary>
    public static final int NORMAL = 0;

    /// <summary>
    /// A style with muted colors.
    /// </summary>
    public static final int MUTED = 1;

    /// <summary>
    /// A style with more saturated colors.
    /// </summary>
    public static final int SATURATED = 2;

    /// <summary>
    /// A style with shaded data points.
    /// </summary>
    public static final int SHADED = 3;

    /// <summary>
    /// A style with flat data points without gradient.
    /// </summary>
    public static final int FLAT = 4;

    /// <summary>
    /// A style with data points having a shadow.
    /// </summary>
    public static final int SHADOWED = 5;

    /// <summary>
    /// A style with gradient fill of data points.
    /// </summary>
    public static final int GRADIENT = 6;

    /// <summary>
    /// A style with an original appearance of a chart.
    /// </summary>
    public static final int ORIGINAL = 7;

    /// <summary>
    /// A style with transparent data points.
    /// </summary>
    public static final int TRANSPARENT_1 = 8;

    /// <summary>
    /// A style with transparent data points.
    /// </summary>
    public static final int TRANSPARENT_2 = 9;

    /// <summary>
    /// A style with data points having no fill, but only an outline.
    /// </summary>
    public static final int OUTLINE = 10;

    /// <summary>
    /// A style with black chart background, in which data points have no fill, but only an outline.
    /// </summary>
    public static final int OUTLINE_BLACK = 11;

    /// <summary>
    /// A style with black chart background.
    /// </summary>
    public static final int BLACK = 12;

    /// <summary>
    /// A style with gray gradient chart background.
    /// </summary>
    public static final int GREY = 13;

    /// <summary>
    /// A style with blue chart background.
    /// </summary>
    public static final int BLUE = 14;

    /// <summary>
    /// A style, in which the plot area is shaded.
    /// </summary>
    public static final int SHADED_PLOT = 15;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ChartStyle value.
    /// </summary>
    public static String getName(/*ChartStyle*/int chartStyle)
    {
    	switch (chartStyle)
    	{
    		case NORMAL: return "NORMAL";
    		case MUTED: return "MUTED";
    		case SATURATED: return "SATURATED";
    		case SHADED: return "SHADED";
    		case FLAT: return "FLAT";
    		case SHADOWED: return "SHADOWED";
    		case GRADIENT: return "GRADIENT";
    		case ORIGINAL: return "ORIGINAL";
    		case TRANSPARENT_1: return "TRANSPARENT_1";
    		case TRANSPARENT_2: return "TRANSPARENT_2";
    		case OUTLINE: return "OUTLINE";
    		case OUTLINE_BLACK: return "OUTLINE_BLACK";
    		case BLACK: return "BLACK";
    		case GREY: return "GREY";
    		case BLUE: return "BLUE";
    		case SHADED_PLOT: return "SHADED_PLOT";
    		default: return "Unknown ChartStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ChartStyle value.
    /// </summary>
    public static String toString(/*ChartStyle*/int chartStyle)
    {
    	switch (chartStyle)
    	{
    		case NORMAL: return "Normal";
    		case MUTED: return "Muted";
    		case SATURATED: return "Saturated";
    		case SHADED: return "Shaded";
    		case FLAT: return "Flat";
    		case SHADOWED: return "Shadowed";
    		case GRADIENT: return "Gradient";
    		case ORIGINAL: return "Original";
    		case TRANSPARENT_1: return "Transparent1";
    		case TRANSPARENT_2: return "Transparent2";
    		case OUTLINE: return "Outline";
    		case OUTLINE_BLACK: return "OutlineBlack";
    		case BLACK: return "Black";
    		case GREY: return "Grey";
    		case BLUE: return "Blue";
    		case SHADED_PLOT: return "ShadedPlot";
    		default: return "Unknown ChartStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ChartStyle name.
    /// </summary>
    public static /*ChartStyle*/int fromName(String chartStyleName)
    {
    	if ("NORMAL".equals(chartStyleName)) return NORMAL;
    	if ("MUTED".equals(chartStyleName)) return MUTED;
    	if ("SATURATED".equals(chartStyleName)) return SATURATED;
    	if ("SHADED".equals(chartStyleName)) return SHADED;
    	if ("FLAT".equals(chartStyleName)) return FLAT;
    	if ("SHADOWED".equals(chartStyleName)) return SHADOWED;
    	if ("GRADIENT".equals(chartStyleName)) return GRADIENT;
    	if ("ORIGINAL".equals(chartStyleName)) return ORIGINAL;
    	if ("TRANSPARENT_1".equals(chartStyleName)) return TRANSPARENT_1;
    	if ("TRANSPARENT_2".equals(chartStyleName)) return TRANSPARENT_2;
    	if ("OUTLINE".equals(chartStyleName)) return OUTLINE;
    	if ("OUTLINE_BLACK".equals(chartStyleName)) return OUTLINE_BLACK;
    	if ("BLACK".equals(chartStyleName)) return BLACK;
    	if ("GREY".equals(chartStyleName)) return GREY;
    	if ("BLUE".equals(chartStyleName)) return BLUE;
    	if ("SHADED_PLOT".equals(chartStyleName)) return SHADED_PLOT;
    	throw new IllegalArgumentException("Unknown ChartStyle name.");
    }
    
    /// <summary>
    /// Returns array of ChartStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NORMAL,
    		MUTED,
    		SATURATED,
    		SHADED,
    		FLAT,
    		SHADOWED,
    		GRADIENT,
    		ORIGINAL,
    		TRANSPARENT_1,
    		TRANSPARENT_2,
    		OUTLINE,
    		OUTLINE_BLACK,
    		BLACK,
    		GREY,
    		BLUE,
    		SHADED_PLOT,
    	};
    }

    public static final int length = 16;
}

