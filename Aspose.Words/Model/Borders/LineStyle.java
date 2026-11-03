// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Michael Morozoff

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies line style of a <see cref="Border"/>.
/// </summary>
public /*enum*/ final class LineStyle
{
    private LineStyle(){}
    
    /// <summary>
    /// </summary>
    /// <dev>
    /// In MS Word binary format LineStyle.None means no border for all objects, except table cells.
    ///
    /// For table cells LineStyle.None means inherit from the default table border.
    ///
    /// Apparently this looks like a very bad hack made by someone at MS and I work around it
    /// by making LineStyle.None to mean "no border" for any object in the model including table cells.
    /// The translation is performed in the binary cell reader/writer.
    ///
    /// I also use a single approach to indicate that a border is inherited. At first the border attribute
    /// is simply missing, but if a use requests it, a Border object is instantiated in "inherited" mode.
    /// 
    /// DS 12.10.2010. Right now the enum contains the continuous range of values and it is used in 
    /// Border.HasValidLineStyle to define if a line style belongs to the enum or not. If new values
    /// are added to the enum then Border.HasValidLineStyle should be reworked correspondingly.
    /// </dev>
    public static final int NONE = 0;
    /// <summary>
    /// </summary>
    public static final int SINGLE = 1;
    /// <summary>
    /// </summary>
    public static final int THICK = 2;
    /// <summary>
    /// </summary>
    public static final int DOUBLE = 3;
    /// <summary>
    /// </summary>
    public static final int HAIRLINE = 5;
    /// <summary>
    /// </summary>
    public static final int DOT = 6;
    /// <summary>
    /// </summary>
    public static final int DASH_LARGE_GAP = 7;
    /// <summary>
    /// </summary>
    public static final int DOT_DASH = 8;
    /// <summary>
    /// </summary>
    public static final int DOT_DOT_DASH = 9;
    /// <summary>
    /// </summary>
    public static final int TRIPLE = 10;
    /// <summary>
    /// </summary>
    public static final int THIN_THICK_SMALL_GAP = 11;         // | ||
    /// <summary>
    /// </summary>
    public static final int THICK_THIN_SMALL_GAP = 12;         // || |
    /// <summary>
    /// </summary>
    public static final int THIN_THICK_THIN_SMALL_GAP = 13;     // | || |
    /// <summary>
    /// </summary>
    public static final int THIN_THICK_MEDIUM_GAP = 14;        // |  ||
    /// <summary>
    /// </summary>
    public static final int THICK_THIN_MEDIUM_GAP = 15;        // ||  |
    /// <summary>
    /// </summary>
    public static final int THIN_THICK_THIN_MEDIUM_GAP = 16;    // |  ||  |
    /// <summary>
    /// </summary>
    public static final int THIN_THICK_LARGE_GAP = 17;         // |   ||
    /// <summary>
    /// </summary>
    public static final int THICK_THIN_LARGE_GAP = 18;         // ||   |
    /// <summary>
    /// </summary>
    public static final int THIN_THICK_THIN_LARGE_GAP = 19;     // |   ||   |
    /// <summary>
    /// </summary>
    public static final int WAVE = 20;
    /// <summary>
    /// </summary>
    public static final int DOUBLE_WAVE = 21;
    /// <summary>
    /// </summary>
    public static final int DASH_SMALL_GAP = 22;
    /// <summary>
    /// </summary>
    public static final int DASH_DOT_STROKER = 23;
    /// <summary>
    /// </summary>
    public static final int EMBOSS_3_D = 24;
    /// <summary>
    /// </summary>
    public static final int ENGRAVE_3_D = 25;
    /// <summary>
    /// </summary>
    public static final int OUTSET = 26;
    /// <summary>
    /// </summary>
    public static final int INSET = 27;

    // Codes 64 - 230 represent border art types and are used only for page borders.
    // They are represented using the PageBorderArt enum.

    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer LineStyle value.
    /// </summary>
    public static String getName(/*LineStyle*/int lineStyle)
    {
    	switch (lineStyle)
    	{
    		case NONE: return "NONE";
    		case SINGLE: return "SINGLE";
    		case THICK: return "THICK";
    		case DOUBLE: return "DOUBLE";
    		case HAIRLINE: return "HAIRLINE";
    		case DOT: return "DOT";
    		case DASH_LARGE_GAP: return "DASH_LARGE_GAP";
    		case DOT_DASH: return "DOT_DASH";
    		case DOT_DOT_DASH: return "DOT_DOT_DASH";
    		case TRIPLE: return "TRIPLE";
    		case THIN_THICK_SMALL_GAP: return "THIN_THICK_SMALL_GAP";
    		case THICK_THIN_SMALL_GAP: return "THICK_THIN_SMALL_GAP";
    		case THIN_THICK_THIN_SMALL_GAP: return "THIN_THICK_THIN_SMALL_GAP";
    		case THIN_THICK_MEDIUM_GAP: return "THIN_THICK_MEDIUM_GAP";
    		case THICK_THIN_MEDIUM_GAP: return "THICK_THIN_MEDIUM_GAP";
    		case THIN_THICK_THIN_MEDIUM_GAP: return "THIN_THICK_THIN_MEDIUM_GAP";
    		case THIN_THICK_LARGE_GAP: return "THIN_THICK_LARGE_GAP";
    		case THICK_THIN_LARGE_GAP: return "THICK_THIN_LARGE_GAP";
    		case THIN_THICK_THIN_LARGE_GAP: return "THIN_THICK_THIN_LARGE_GAP";
    		case WAVE: return "WAVE";
    		case DOUBLE_WAVE: return "DOUBLE_WAVE";
    		case DASH_SMALL_GAP: return "DASH_SMALL_GAP";
    		case DASH_DOT_STROKER: return "DASH_DOT_STROKER";
    		case EMBOSS_3_D: return "EMBOSS_3_D";
    		case ENGRAVE_3_D: return "ENGRAVE_3_D";
    		case OUTSET: return "OUTSET";
    		case INSET: return "INSET";
    		default: return "Unknown LineStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer LineStyle value.
    /// </summary>
    public static String toString(/*LineStyle*/int lineStyle)
    {
    	switch (lineStyle)
    	{
    		case NONE: return "None";
    		case SINGLE: return "Single";
    		case THICK: return "Thick";
    		case DOUBLE: return "Double";
    		case HAIRLINE: return "Hairline";
    		case DOT: return "Dot";
    		case DASH_LARGE_GAP: return "DashLargeGap";
    		case DOT_DASH: return "DotDash";
    		case DOT_DOT_DASH: return "DotDotDash";
    		case TRIPLE: return "Triple";
    		case THIN_THICK_SMALL_GAP: return "ThinThickSmallGap";
    		case THICK_THIN_SMALL_GAP: return "ThickThinSmallGap";
    		case THIN_THICK_THIN_SMALL_GAP: return "ThinThickThinSmallGap";
    		case THIN_THICK_MEDIUM_GAP: return "ThinThickMediumGap";
    		case THICK_THIN_MEDIUM_GAP: return "ThickThinMediumGap";
    		case THIN_THICK_THIN_MEDIUM_GAP: return "ThinThickThinMediumGap";
    		case THIN_THICK_LARGE_GAP: return "ThinThickLargeGap";
    		case THICK_THIN_LARGE_GAP: return "ThickThinLargeGap";
    		case THIN_THICK_THIN_LARGE_GAP: return "ThinThickThinLargeGap";
    		case WAVE: return "Wave";
    		case DOUBLE_WAVE: return "DoubleWave";
    		case DASH_SMALL_GAP: return "DashSmallGap";
    		case DASH_DOT_STROKER: return "DashDotStroker";
    		case EMBOSS_3_D: return "Emboss3D";
    		case ENGRAVE_3_D: return "Engrave3D";
    		case OUTSET: return "Outset";
    		case INSET: return "Inset";
    		default: return "Unknown LineStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by LineStyle name.
    /// </summary>
    public static /*LineStyle*/int fromName(String lineStyleName)
    {
    	if ("NONE".equals(lineStyleName)) return NONE;
    	if ("SINGLE".equals(lineStyleName)) return SINGLE;
    	if ("THICK".equals(lineStyleName)) return THICK;
    	if ("DOUBLE".equals(lineStyleName)) return DOUBLE;
    	if ("HAIRLINE".equals(lineStyleName)) return HAIRLINE;
    	if ("DOT".equals(lineStyleName)) return DOT;
    	if ("DASH_LARGE_GAP".equals(lineStyleName)) return DASH_LARGE_GAP;
    	if ("DOT_DASH".equals(lineStyleName)) return DOT_DASH;
    	if ("DOT_DOT_DASH".equals(lineStyleName)) return DOT_DOT_DASH;
    	if ("TRIPLE".equals(lineStyleName)) return TRIPLE;
    	if ("THIN_THICK_SMALL_GAP".equals(lineStyleName)) return THIN_THICK_SMALL_GAP;
    	if ("THICK_THIN_SMALL_GAP".equals(lineStyleName)) return THICK_THIN_SMALL_GAP;
    	if ("THIN_THICK_THIN_SMALL_GAP".equals(lineStyleName)) return THIN_THICK_THIN_SMALL_GAP;
    	if ("THIN_THICK_MEDIUM_GAP".equals(lineStyleName)) return THIN_THICK_MEDIUM_GAP;
    	if ("THICK_THIN_MEDIUM_GAP".equals(lineStyleName)) return THICK_THIN_MEDIUM_GAP;
    	if ("THIN_THICK_THIN_MEDIUM_GAP".equals(lineStyleName)) return THIN_THICK_THIN_MEDIUM_GAP;
    	if ("THIN_THICK_LARGE_GAP".equals(lineStyleName)) return THIN_THICK_LARGE_GAP;
    	if ("THICK_THIN_LARGE_GAP".equals(lineStyleName)) return THICK_THIN_LARGE_GAP;
    	if ("THIN_THICK_THIN_LARGE_GAP".equals(lineStyleName)) return THIN_THICK_THIN_LARGE_GAP;
    	if ("WAVE".equals(lineStyleName)) return WAVE;
    	if ("DOUBLE_WAVE".equals(lineStyleName)) return DOUBLE_WAVE;
    	if ("DASH_SMALL_GAP".equals(lineStyleName)) return DASH_SMALL_GAP;
    	if ("DASH_DOT_STROKER".equals(lineStyleName)) return DASH_DOT_STROKER;
    	if ("EMBOSS_3_D".equals(lineStyleName)) return EMBOSS_3_D;
    	if ("ENGRAVE_3_D".equals(lineStyleName)) return ENGRAVE_3_D;
    	if ("OUTSET".equals(lineStyleName)) return OUTSET;
    	if ("INSET".equals(lineStyleName)) return INSET;
    	throw new IllegalArgumentException("Unknown LineStyle name.");
    }
    
    /// <summary>
    /// Returns array of LineStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		SINGLE,
    		THICK,
    		DOUBLE,
    		HAIRLINE,
    		DOT,
    		DASH_LARGE_GAP,
    		DOT_DASH,
    		DOT_DOT_DASH,
    		TRIPLE,
    		THIN_THICK_SMALL_GAP,
    		THICK_THIN_SMALL_GAP,
    		THIN_THICK_THIN_SMALL_GAP,
    		THIN_THICK_MEDIUM_GAP,
    		THICK_THIN_MEDIUM_GAP,
    		THIN_THICK_THIN_MEDIUM_GAP,
    		THIN_THICK_LARGE_GAP,
    		THICK_THIN_LARGE_GAP,
    		THIN_THICK_THIN_LARGE_GAP,
    		WAVE,
    		DOUBLE_WAVE,
    		DASH_SMALL_GAP,
    		DASH_DOT_STROKER,
    		EMBOSS_3_D,
    		ENGRAVE_3_D,
    		OUTSET,
    		INSET,
    	};
    }

    public static final int length = 27;
}

