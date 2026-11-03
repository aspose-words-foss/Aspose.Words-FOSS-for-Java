// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// List of chart attributes.
/// </summary>
/*enum*/ final class DmlChartAttrs
{
    private DmlChartAttrs(){}
    
    /// <summary>
    /// Id of X axis.
    /// Integer.
    /// </summary>
    public static final int AX_ID_X = 0;

    /// <summary>
    /// Id of Y axis.
    /// Integer.
    /// </summary>
    public static final int AX_ID_Y = 1;
    
    /// <summary>
    /// Id of Z axis (for 3D charts).
    /// Integer.
    /// </summary>
    public static final int AX_ID_Z = 2;

    /// <summary>
    /// Specifies the settings for the data labels for the entire chart.
    /// <see cref="ChartDataLabelCollection"/>.
    /// </summary>
    public static final int D_LBLS = 3;

    /// <summary>
    /// Specifies drop lines formatting.
    /// <see cref="DmlChartSpPr"/>.
    /// </summary>
    public static final int DROP_LINES = 4;

    /// <summary>
    /// Specifies that drop lines are visible.
    /// Has effect only for Line, Area and Stock charts.
    /// Boolean.
    /// </summary>
    public static final int IS_DROP_LINES_VISIBLE = 5;
    
    /// <summary>
    /// Specifies the high-low lines formatting for the series.
    /// <see cref="DmlChartSpPr"/>.
    /// </summary>
    public static final int HI_LOW_LINES = 6;

    /// <summary>
    /// Specifies that high-low lines are visible.
    /// Has effect only for Line charts.
    /// Boolean.
    /// </summary>
    public static final int IS_HI_LOW_LINES_VISIBLE = 7;

    /// <summary>
    /// Specifies series lines formatting for the chart.
    /// <see cref="DmlChartSpPr"/>.
    /// </summary>
    public static final int SER_LINES = 8;

    /// <summary>
    /// Flag indicates whether series lines must be shown.
    /// </summary>
    public static final int SHOW_SER_LINE = 9;

    /// <summary>
    /// Specifies the space between bar or column clusters, as a percentage of the bar or column width.
    /// Integer [0;500].
    /// </summary>
    public static final int GAP_DEPTH = 10;

    /// <summary>
    /// Specifies the space between bar or column clusters, as a percentage of the bar or column width.
    /// Integer [0;500].
    /// </summary>
    public static final int GAP_WIDTH = 11;

    /// <summary>
    /// Specifies the type of grouping for a column, line, bar, or area chart.
    /// <see cref="SimpleTypes.Grouping"/>.
    /// </summary>
    public static final int GROUPING = 12;

    /// <summary>
    /// Specifies list of series of the chart.
    /// IList{T} with items of <see cref="ChartSeries"/> type.
    /// </summary>
    public static final int SERIES = 13;

    /// <summary>
    /// Specifies that each data marker in the series shall have a different color.
    /// Boolean.
    /// </summary>
    public static final int VARY_COLORS = 14;

    /// <summary>
    /// Specifies whether the series form a bar (horizontal) chart or a column (vertical) chart.
    /// <see cref="BarDirection"/>.
    /// </summary>
    public static final int BAR_DIR = 15;

    /// <summary>
    /// Specifies the shape of a 3-D bar chart.
    /// </summary>
    public static final int SHAPE = 16;

    /// <summary>
    /// Specifies how much bars and columns shall overlap on 2-D charts.
    /// Integer [-100;100].
    /// </summary>
    public static final int OVERLAP = 17;

    /// <summary>
    /// Specifies the scale factor for the bubble chart.
    /// Integer [0; 300].
    /// </summary>
    public static final int BUBBLE_SCALE = 18;

    /// <summary>
    /// Specifies negative sized bubbles shall be shown on a bubble chart.
    /// Boolean.
    /// </summary>
    public static final int SHOW_NEG_BUBBLES = 19;

    /// <summary>
    /// Specifies how the bubble size values are represented on the chart.
    /// <see cref="SimpleTypes.SizeRepresents"/>.
    /// </summary>
    public static final int SIZE_REPRESENTS = 20;

    /// <summary>
    /// Specifies the angle of the first pie or doughnut chart slice, in degrees (clockwise from up).
    /// Integer [0;300].
    /// </summary>
    public static final int FIRST_SLICE_ANG = 21;

    /// <summary>
    /// Specifies the size of the hole in a doughnut chart.
    /// Integer [10;90].
    /// </summary>
    public static final int HOLE_SIZE = 22;

    /// <summary>
    /// Specifies that the marker shall be shown.
    /// Boolean.
    /// </summary>
    public static final int SHOW_MARKER = 23;

    /// <summary>
    /// Specifies the line connecting the points on the chart shall be smoothed using Catmull-Rom splines.
    /// Boolean.
    /// </summary>
    public static final int SMOOTH = 24;

    /// <summary>
    /// Specifies the up and down bars.
    /// <see cref="DmlChartUpDownBars"/>.
    /// </summary>
    public static final int UP_DOWN_BARS = 25;

    /// <summary>
    /// Contains the custom split information for a pie-of-pie or bar-of-pie chart with a custom split type.
    /// Array of Integers.
    /// </summary>
    public static final int CUST_SPLIT = 26;

    /// <summary>
    /// Specifies whether this chart is pie of pie or bar of pie.
    /// <see cref="SimpleTypes.OfPieType"/>.
    /// </summary>
    public static final int OF_PIE_TYPE = 27;

    /// <summary>
    /// specifies the size of the second pie or bar of a pie of pie chart or a bar of pie chart, 
    /// as a percentage of the size of the first pie.
    /// Integer [5;200].
    /// </summary>
    public static final int SECOND_PIE_SIZE = 28;

    /// <summary>
    /// Specifies a value that shall be used to determine which data points 
    /// are in the second pie or bar on a pie of pie or bar of pie chart.
    /// Double.
    /// </summary>
    public static final int SPLIT_POS = 29;

    /// <summary>
    /// Specifies how to determine which data points are in the second pie or bar on a pie of pie or bar of pie chart.
    /// <see cref="SimpleTypes.SplitType"/>.
    /// </summary>
    public static final int SPLIT_TYPE = 30;

    /// <summary>
    /// Specifies what type of radar chart shall be drawn.
    /// <see cref="SimpleTypes.RadarStyle"/>.
    /// </summary>
    public static final int RADAR_STYLE = 31;

    /// <summary>
    /// Specifies the type of lines for the scatter chart.
    /// <see cref="SimpleTypes.ScatterStyle"/>.
    /// </summary>
    public static final int SCATTER_STYLE = 32;

    /// <summary>
    /// Contains a collection of formatting bands for a surface chart indexed from low to high.
    /// <see cref="DmlChartBandFormats"/>.
    /// </summary>
    public static final int BAND_FMTS = 33;

    /// <summary>
    /// Specifies the surface chart is drawn as a wireframe.
    /// Boolean.
    /// </summary>
    public static final int WIREFRAME = 34;

    /// <summary>
    /// extLst (Chart Extensibility) This element contains tags used for future extensibility of the file format
    /// <see cref="StringToObjDictionary{T}"/>
    /// </summary>
    public static final int EXTENSIONS = 35;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlChartAttrs value.
    /// </summary>
    public static String getName(/*DmlChartAttrs*/int dmlChartAttrs)
    {
    	switch (dmlChartAttrs)
    	{
    		case AX_ID_X: return "AX_ID_X";
    		case AX_ID_Y: return "AX_ID_Y";
    		case AX_ID_Z: return "AX_ID_Z";
    		case D_LBLS: return "D_LBLS";
    		case DROP_LINES: return "DROP_LINES";
    		case IS_DROP_LINES_VISIBLE: return "IS_DROP_LINES_VISIBLE";
    		case HI_LOW_LINES: return "HI_LOW_LINES";
    		case IS_HI_LOW_LINES_VISIBLE: return "IS_HI_LOW_LINES_VISIBLE";
    		case SER_LINES: return "SER_LINES";
    		case SHOW_SER_LINE: return "SHOW_SER_LINE";
    		case GAP_DEPTH: return "GAP_DEPTH";
    		case GAP_WIDTH: return "GAP_WIDTH";
    		case GROUPING: return "GROUPING";
    		case SERIES: return "SERIES";
    		case VARY_COLORS: return "VARY_COLORS";
    		case BAR_DIR: return "BAR_DIR";
    		case SHAPE: return "SHAPE";
    		case OVERLAP: return "OVERLAP";
    		case BUBBLE_SCALE: return "BUBBLE_SCALE";
    		case SHOW_NEG_BUBBLES: return "SHOW_NEG_BUBBLES";
    		case SIZE_REPRESENTS: return "SIZE_REPRESENTS";
    		case FIRST_SLICE_ANG: return "FIRST_SLICE_ANG";
    		case HOLE_SIZE: return "HOLE_SIZE";
    		case SHOW_MARKER: return "SHOW_MARKER";
    		case SMOOTH: return "SMOOTH";
    		case UP_DOWN_BARS: return "UP_DOWN_BARS";
    		case CUST_SPLIT: return "CUST_SPLIT";
    		case OF_PIE_TYPE: return "OF_PIE_TYPE";
    		case SECOND_PIE_SIZE: return "SECOND_PIE_SIZE";
    		case SPLIT_POS: return "SPLIT_POS";
    		case SPLIT_TYPE: return "SPLIT_TYPE";
    		case RADAR_STYLE: return "RADAR_STYLE";
    		case SCATTER_STYLE: return "SCATTER_STYLE";
    		case BAND_FMTS: return "BAND_FMTS";
    		case WIREFRAME: return "WIREFRAME";
    		case EXTENSIONS: return "EXTENSIONS";
    		default: return "Unknown DmlChartAttrs value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlChartAttrs value.
    /// </summary>
    public static String toString(/*DmlChartAttrs*/int dmlChartAttrs)
    {
    	switch (dmlChartAttrs)
    	{
    		case AX_ID_X: return "AxIdX";
    		case AX_ID_Y: return "AxIdY";
    		case AX_ID_Z: return "AxIdZ";
    		case D_LBLS: return "DLbls";
    		case DROP_LINES: return "DropLines";
    		case IS_DROP_LINES_VISIBLE: return "IsDropLinesVisible";
    		case HI_LOW_LINES: return "HiLowLines";
    		case IS_HI_LOW_LINES_VISIBLE: return "IsHiLowLinesVisible";
    		case SER_LINES: return "SerLines";
    		case SHOW_SER_LINE: return "ShowSerLine";
    		case GAP_DEPTH: return "GapDepth";
    		case GAP_WIDTH: return "GapWidth";
    		case GROUPING: return "Grouping";
    		case SERIES: return "Series";
    		case VARY_COLORS: return "VaryColors";
    		case BAR_DIR: return "BarDir";
    		case SHAPE: return "Shape";
    		case OVERLAP: return "Overlap";
    		case BUBBLE_SCALE: return "BubbleScale";
    		case SHOW_NEG_BUBBLES: return "ShowNegBubbles";
    		case SIZE_REPRESENTS: return "SizeRepresents";
    		case FIRST_SLICE_ANG: return "FirstSliceAng";
    		case HOLE_SIZE: return "HoleSize";
    		case SHOW_MARKER: return "ShowMarker";
    		case SMOOTH: return "Smooth";
    		case UP_DOWN_BARS: return "UpDownBars";
    		case CUST_SPLIT: return "CustSplit";
    		case OF_PIE_TYPE: return "OfPieType";
    		case SECOND_PIE_SIZE: return "SecondPieSize";
    		case SPLIT_POS: return "SplitPos";
    		case SPLIT_TYPE: return "SplitType";
    		case RADAR_STYLE: return "RadarStyle";
    		case SCATTER_STYLE: return "ScatterStyle";
    		case BAND_FMTS: return "BandFmts";
    		case WIREFRAME: return "Wireframe";
    		case EXTENSIONS: return "Extensions";
    		default: return "Unknown DmlChartAttrs value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlChartAttrs name.
    /// </summary>
    public static /*DmlChartAttrs*/int fromName(String dmlChartAttrsName)
    {
    	if ("AX_ID_X".equals(dmlChartAttrsName)) return AX_ID_X;
    	if ("AX_ID_Y".equals(dmlChartAttrsName)) return AX_ID_Y;
    	if ("AX_ID_Z".equals(dmlChartAttrsName)) return AX_ID_Z;
    	if ("D_LBLS".equals(dmlChartAttrsName)) return D_LBLS;
    	if ("DROP_LINES".equals(dmlChartAttrsName)) return DROP_LINES;
    	if ("IS_DROP_LINES_VISIBLE".equals(dmlChartAttrsName)) return IS_DROP_LINES_VISIBLE;
    	if ("HI_LOW_LINES".equals(dmlChartAttrsName)) return HI_LOW_LINES;
    	if ("IS_HI_LOW_LINES_VISIBLE".equals(dmlChartAttrsName)) return IS_HI_LOW_LINES_VISIBLE;
    	if ("SER_LINES".equals(dmlChartAttrsName)) return SER_LINES;
    	if ("SHOW_SER_LINE".equals(dmlChartAttrsName)) return SHOW_SER_LINE;
    	if ("GAP_DEPTH".equals(dmlChartAttrsName)) return GAP_DEPTH;
    	if ("GAP_WIDTH".equals(dmlChartAttrsName)) return GAP_WIDTH;
    	if ("GROUPING".equals(dmlChartAttrsName)) return GROUPING;
    	if ("SERIES".equals(dmlChartAttrsName)) return SERIES;
    	if ("VARY_COLORS".equals(dmlChartAttrsName)) return VARY_COLORS;
    	if ("BAR_DIR".equals(dmlChartAttrsName)) return BAR_DIR;
    	if ("SHAPE".equals(dmlChartAttrsName)) return SHAPE;
    	if ("OVERLAP".equals(dmlChartAttrsName)) return OVERLAP;
    	if ("BUBBLE_SCALE".equals(dmlChartAttrsName)) return BUBBLE_SCALE;
    	if ("SHOW_NEG_BUBBLES".equals(dmlChartAttrsName)) return SHOW_NEG_BUBBLES;
    	if ("SIZE_REPRESENTS".equals(dmlChartAttrsName)) return SIZE_REPRESENTS;
    	if ("FIRST_SLICE_ANG".equals(dmlChartAttrsName)) return FIRST_SLICE_ANG;
    	if ("HOLE_SIZE".equals(dmlChartAttrsName)) return HOLE_SIZE;
    	if ("SHOW_MARKER".equals(dmlChartAttrsName)) return SHOW_MARKER;
    	if ("SMOOTH".equals(dmlChartAttrsName)) return SMOOTH;
    	if ("UP_DOWN_BARS".equals(dmlChartAttrsName)) return UP_DOWN_BARS;
    	if ("CUST_SPLIT".equals(dmlChartAttrsName)) return CUST_SPLIT;
    	if ("OF_PIE_TYPE".equals(dmlChartAttrsName)) return OF_PIE_TYPE;
    	if ("SECOND_PIE_SIZE".equals(dmlChartAttrsName)) return SECOND_PIE_SIZE;
    	if ("SPLIT_POS".equals(dmlChartAttrsName)) return SPLIT_POS;
    	if ("SPLIT_TYPE".equals(dmlChartAttrsName)) return SPLIT_TYPE;
    	if ("RADAR_STYLE".equals(dmlChartAttrsName)) return RADAR_STYLE;
    	if ("SCATTER_STYLE".equals(dmlChartAttrsName)) return SCATTER_STYLE;
    	if ("BAND_FMTS".equals(dmlChartAttrsName)) return BAND_FMTS;
    	if ("WIREFRAME".equals(dmlChartAttrsName)) return WIREFRAME;
    	if ("EXTENSIONS".equals(dmlChartAttrsName)) return EXTENSIONS;
    	throw new IllegalArgumentException("Unknown DmlChartAttrs name.");
    }
    
    /// <summary>
    /// Returns array of DmlChartAttrs values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AX_ID_X,
    		AX_ID_Y,
    		AX_ID_Z,
    		D_LBLS,
    		DROP_LINES,
    		IS_DROP_LINES_VISIBLE,
    		HI_LOW_LINES,
    		IS_HI_LOW_LINES_VISIBLE,
    		SER_LINES,
    		SHOW_SER_LINE,
    		GAP_DEPTH,
    		GAP_WIDTH,
    		GROUPING,
    		SERIES,
    		VARY_COLORS,
    		BAR_DIR,
    		SHAPE,
    		OVERLAP,
    		BUBBLE_SCALE,
    		SHOW_NEG_BUBBLES,
    		SIZE_REPRESENTS,
    		FIRST_SLICE_ANG,
    		HOLE_SIZE,
    		SHOW_MARKER,
    		SMOOTH,
    		UP_DOWN_BARS,
    		CUST_SPLIT,
    		OF_PIE_TYPE,
    		SECOND_PIE_SIZE,
    		SPLIT_POS,
    		SPLIT_TYPE,
    		RADAR_STYLE,
    		SCATTER_STYLE,
    		BAND_FMTS,
    		WIREFRAME,
    		EXTENSIONS,
    	};
    }

    public static final int length = 36;
}

