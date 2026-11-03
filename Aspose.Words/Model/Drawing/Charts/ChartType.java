// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/13/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies type of a chart.
/// </summary>
public /*enum*/ final class ChartType
{
    private ChartType(){}
    
    /// <summary>
    /// Area chart. 
    /// </summary>
    public static final int AREA = 0;

    /// <summary>
    /// Stacked Area chart.
    /// </summary>
    public static final int AREA_STACKED = 1;

    /// <summary>
    /// 100% Stacked Area chart.
    /// </summary>
    public static final int AREA_PERCENT_STACKED = 2;

    /// <summary>
    /// 3D Area chart.
    /// </summary>
    public static final int AREA_3_D = 3;

    /// <summary>
    /// 3D Stacked Area chart.
    /// </summary>
    public static final int AREA_3_D_STACKED = 4;

    /// <summary>
    /// 3D 100% Stacked Area chart.
    /// </summary>
    public static final int AREA_3_D_PERCENT_STACKED = 5;

    /// <summary>
    /// Bar chart.
    /// </summary>
    public static final int BAR = 6;

    /// <summary>
    /// Stacked Bar chart.
    /// </summary>
    public static final int BAR_STACKED = 7;

    /// <summary>
    /// 100% Stacked Bar chart.
    /// </summary>
    public static final int BAR_PERCENT_STACKED = 8;

    /// <summary>
    /// 3D Bar chart.
    /// </summary>
    public static final int BAR_3_D = 9;

    /// <summary>
    /// 3D Stacked Bar chart.
    /// </summary>
    public static final int BAR_3_D_STACKED = 10;

    /// <summary>
    /// 3D 100% Stacked Bar chart.
    /// </summary>
    public static final int BAR_3_D_PERCENT_STACKED = 11;

    /// <summary>
    /// Bubble chart.
    /// </summary>
    public static final int BUBBLE = 12;

    /// <summary>
    /// 3D Bubble chart.
    /// </summary>
    public static final int BUBBLE_3_D = 13;

    /// <summary>
    /// Column chart.
    /// </summary>
    public static final int COLUMN = 14;

    /// <summary>
    /// Stacked Column chart.
    /// </summary>
    public static final int COLUMN_STACKED = 15;

    /// <summary>
    /// 100% Stacked Column chart.
    /// </summary>
    public static final int COLUMN_PERCENT_STACKED = 16;

    /// <summary>
    /// 3D Column chart.
    /// </summary>
    public static final int COLUMN_3_D = 17;

    /// <summary>
    /// 3D Stacked Column chart.
    /// </summary>
    public static final int COLUMN_3_D_STACKED = 18;

    /// <summary>
    /// 3D 100% Stacked Column chart.
    /// </summary>
    public static final int COLUMN_3_D_PERCENT_STACKED = 19;

    /// <summary>
    /// 3D Clustered Column chart.
    /// </summary>
    public static final int COLUMN_3_D_CLUSTERED = 20;

    /// <summary>
    /// Doughnut chart.
    /// </summary>
    public static final int DOUGHNUT = 21;

    /// <summary>
    /// Line chart.
    /// </summary>
    public static final int LINE = 22;

    /// <summary>
    /// Stacked Line chart.
    /// </summary>
    public static final int LINE_STACKED = 23;

    /// <summary>
    /// 100% Stacked Line chart.
    /// </summary>
    public static final int LINE_PERCENT_STACKED = 24;

    /// <summary>
    /// 3D Line chart.
    /// </summary>
    public static final int LINE_3_D = 25;

    /// <summary>
    /// Pie chart.
    /// </summary>
    public static final int PIE = 26;

    /// <summary>
    /// 3D Pie chart.
    /// </summary>
    public static final int PIE_3_D = 27;

    /// <summary>
    /// Pie of Bar chart.
    /// </summary>
    public static final int PIE_OF_BAR = 28;

    /// <summary>
    /// Pie of Pie chart.
    /// </summary>
    public static final int PIE_OF_PIE = 29;

    /// <summary>
    /// Radar chart.
    /// </summary>
    public static final int RADAR = 30;

    /// <summary>
    /// Scatter chart.
    /// </summary>
    public static final int SCATTER = 31;

    /// <summary>
    /// Stock chart.
    /// </summary>
    public static final int STOCK = 32;

    /// <summary>
    /// Surface chart.
    /// </summary>
    public static final int SURFACE = 33;

    /// <summary>
    /// 3D Surface chart.
    /// </summary>
    public static final int SURFACE_3_D = 34;

    /// <summary>
    /// Treemap chart.
    /// </summary>
    public static final int TREEMAP = 35;

    /// <summary>
    /// Sunburst chart.
    /// </summary>
    public static final int SUNBURST = 36;

    /// <summary>
    /// Histogram chart.
    /// </summary>
    public static final int HISTOGRAM = 37;

    /// <summary>
    /// Pareto chart.
    /// </summary>
    public static final int PARETO = 38;

    /// <summary>
    /// Box and Whisker chart.
    /// </summary>
    public static final int BOX_AND_WHISKER = 39;

    /// <summary>
    /// Waterfall chart.
    /// </summary>
    public static final int WATERFALL = 40;

    /// <summary>
    /// Funnel chart.
    /// </summary>
    public static final int FUNNEL = 41;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ChartType value.
    /// </summary>
    public static String getName(/*ChartType*/int chartType)
    {
    	switch (chartType)
    	{
    		case AREA: return "AREA";
    		case AREA_STACKED: return "AREA_STACKED";
    		case AREA_PERCENT_STACKED: return "AREA_PERCENT_STACKED";
    		case AREA_3_D: return "AREA_3_D";
    		case AREA_3_D_STACKED: return "AREA_3_D_STACKED";
    		case AREA_3_D_PERCENT_STACKED: return "AREA_3_D_PERCENT_STACKED";
    		case BAR: return "BAR";
    		case BAR_STACKED: return "BAR_STACKED";
    		case BAR_PERCENT_STACKED: return "BAR_PERCENT_STACKED";
    		case BAR_3_D: return "BAR_3_D";
    		case BAR_3_D_STACKED: return "BAR_3_D_STACKED";
    		case BAR_3_D_PERCENT_STACKED: return "BAR_3_D_PERCENT_STACKED";
    		case BUBBLE: return "BUBBLE";
    		case BUBBLE_3_D: return "BUBBLE_3_D";
    		case COLUMN: return "COLUMN";
    		case COLUMN_STACKED: return "COLUMN_STACKED";
    		case COLUMN_PERCENT_STACKED: return "COLUMN_PERCENT_STACKED";
    		case COLUMN_3_D: return "COLUMN_3_D";
    		case COLUMN_3_D_STACKED: return "COLUMN_3_D_STACKED";
    		case COLUMN_3_D_PERCENT_STACKED: return "COLUMN_3_D_PERCENT_STACKED";
    		case COLUMN_3_D_CLUSTERED: return "COLUMN_3_D_CLUSTERED";
    		case DOUGHNUT: return "DOUGHNUT";
    		case LINE: return "LINE";
    		case LINE_STACKED: return "LINE_STACKED";
    		case LINE_PERCENT_STACKED: return "LINE_PERCENT_STACKED";
    		case LINE_3_D: return "LINE_3_D";
    		case PIE: return "PIE";
    		case PIE_3_D: return "PIE_3_D";
    		case PIE_OF_BAR: return "PIE_OF_BAR";
    		case PIE_OF_PIE: return "PIE_OF_PIE";
    		case RADAR: return "RADAR";
    		case SCATTER: return "SCATTER";
    		case STOCK: return "STOCK";
    		case SURFACE: return "SURFACE";
    		case SURFACE_3_D: return "SURFACE_3_D";
    		case TREEMAP: return "TREEMAP";
    		case SUNBURST: return "SUNBURST";
    		case HISTOGRAM: return "HISTOGRAM";
    		case PARETO: return "PARETO";
    		case BOX_AND_WHISKER: return "BOX_AND_WHISKER";
    		case WATERFALL: return "WATERFALL";
    		case FUNNEL: return "FUNNEL";
    		default: return "Unknown ChartType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ChartType value.
    /// </summary>
    public static String toString(/*ChartType*/int chartType)
    {
    	switch (chartType)
    	{
    		case AREA: return "Area";
    		case AREA_STACKED: return "AreaStacked";
    		case AREA_PERCENT_STACKED: return "AreaPercentStacked";
    		case AREA_3_D: return "Area3D";
    		case AREA_3_D_STACKED: return "Area3DStacked";
    		case AREA_3_D_PERCENT_STACKED: return "Area3DPercentStacked";
    		case BAR: return "Bar";
    		case BAR_STACKED: return "BarStacked";
    		case BAR_PERCENT_STACKED: return "BarPercentStacked";
    		case BAR_3_D: return "Bar3D";
    		case BAR_3_D_STACKED: return "Bar3DStacked";
    		case BAR_3_D_PERCENT_STACKED: return "Bar3DPercentStacked";
    		case BUBBLE: return "Bubble";
    		case BUBBLE_3_D: return "Bubble3D";
    		case COLUMN: return "Column";
    		case COLUMN_STACKED: return "ColumnStacked";
    		case COLUMN_PERCENT_STACKED: return "ColumnPercentStacked";
    		case COLUMN_3_D: return "Column3D";
    		case COLUMN_3_D_STACKED: return "Column3DStacked";
    		case COLUMN_3_D_PERCENT_STACKED: return "Column3DPercentStacked";
    		case COLUMN_3_D_CLUSTERED: return "Column3DClustered";
    		case DOUGHNUT: return "Doughnut";
    		case LINE: return "Line";
    		case LINE_STACKED: return "LineStacked";
    		case LINE_PERCENT_STACKED: return "LinePercentStacked";
    		case LINE_3_D: return "Line3D";
    		case PIE: return "Pie";
    		case PIE_3_D: return "Pie3D";
    		case PIE_OF_BAR: return "PieOfBar";
    		case PIE_OF_PIE: return "PieOfPie";
    		case RADAR: return "Radar";
    		case SCATTER: return "Scatter";
    		case STOCK: return "Stock";
    		case SURFACE: return "Surface";
    		case SURFACE_3_D: return "Surface3D";
    		case TREEMAP: return "Treemap";
    		case SUNBURST: return "Sunburst";
    		case HISTOGRAM: return "Histogram";
    		case PARETO: return "Pareto";
    		case BOX_AND_WHISKER: return "BoxAndWhisker";
    		case WATERFALL: return "Waterfall";
    		case FUNNEL: return "Funnel";
    		default: return "Unknown ChartType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ChartType name.
    /// </summary>
    public static /*ChartType*/int fromName(String chartTypeName)
    {
    	if ("AREA".equals(chartTypeName)) return AREA;
    	if ("AREA_STACKED".equals(chartTypeName)) return AREA_STACKED;
    	if ("AREA_PERCENT_STACKED".equals(chartTypeName)) return AREA_PERCENT_STACKED;
    	if ("AREA_3_D".equals(chartTypeName)) return AREA_3_D;
    	if ("AREA_3_D_STACKED".equals(chartTypeName)) return AREA_3_D_STACKED;
    	if ("AREA_3_D_PERCENT_STACKED".equals(chartTypeName)) return AREA_3_D_PERCENT_STACKED;
    	if ("BAR".equals(chartTypeName)) return BAR;
    	if ("BAR_STACKED".equals(chartTypeName)) return BAR_STACKED;
    	if ("BAR_PERCENT_STACKED".equals(chartTypeName)) return BAR_PERCENT_STACKED;
    	if ("BAR_3_D".equals(chartTypeName)) return BAR_3_D;
    	if ("BAR_3_D_STACKED".equals(chartTypeName)) return BAR_3_D_STACKED;
    	if ("BAR_3_D_PERCENT_STACKED".equals(chartTypeName)) return BAR_3_D_PERCENT_STACKED;
    	if ("BUBBLE".equals(chartTypeName)) return BUBBLE;
    	if ("BUBBLE_3_D".equals(chartTypeName)) return BUBBLE_3_D;
    	if ("COLUMN".equals(chartTypeName)) return COLUMN;
    	if ("COLUMN_STACKED".equals(chartTypeName)) return COLUMN_STACKED;
    	if ("COLUMN_PERCENT_STACKED".equals(chartTypeName)) return COLUMN_PERCENT_STACKED;
    	if ("COLUMN_3_D".equals(chartTypeName)) return COLUMN_3_D;
    	if ("COLUMN_3_D_STACKED".equals(chartTypeName)) return COLUMN_3_D_STACKED;
    	if ("COLUMN_3_D_PERCENT_STACKED".equals(chartTypeName)) return COLUMN_3_D_PERCENT_STACKED;
    	if ("COLUMN_3_D_CLUSTERED".equals(chartTypeName)) return COLUMN_3_D_CLUSTERED;
    	if ("DOUGHNUT".equals(chartTypeName)) return DOUGHNUT;
    	if ("LINE".equals(chartTypeName)) return LINE;
    	if ("LINE_STACKED".equals(chartTypeName)) return LINE_STACKED;
    	if ("LINE_PERCENT_STACKED".equals(chartTypeName)) return LINE_PERCENT_STACKED;
    	if ("LINE_3_D".equals(chartTypeName)) return LINE_3_D;
    	if ("PIE".equals(chartTypeName)) return PIE;
    	if ("PIE_3_D".equals(chartTypeName)) return PIE_3_D;
    	if ("PIE_OF_BAR".equals(chartTypeName)) return PIE_OF_BAR;
    	if ("PIE_OF_PIE".equals(chartTypeName)) return PIE_OF_PIE;
    	if ("RADAR".equals(chartTypeName)) return RADAR;
    	if ("SCATTER".equals(chartTypeName)) return SCATTER;
    	if ("STOCK".equals(chartTypeName)) return STOCK;
    	if ("SURFACE".equals(chartTypeName)) return SURFACE;
    	if ("SURFACE_3_D".equals(chartTypeName)) return SURFACE_3_D;
    	if ("TREEMAP".equals(chartTypeName)) return TREEMAP;
    	if ("SUNBURST".equals(chartTypeName)) return SUNBURST;
    	if ("HISTOGRAM".equals(chartTypeName)) return HISTOGRAM;
    	if ("PARETO".equals(chartTypeName)) return PARETO;
    	if ("BOX_AND_WHISKER".equals(chartTypeName)) return BOX_AND_WHISKER;
    	if ("WATERFALL".equals(chartTypeName)) return WATERFALL;
    	if ("FUNNEL".equals(chartTypeName)) return FUNNEL;
    	throw new IllegalArgumentException("Unknown ChartType name.");
    }
    
    /// <summary>
    /// Returns array of ChartType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AREA,
    		AREA_STACKED,
    		AREA_PERCENT_STACKED,
    		AREA_3_D,
    		AREA_3_D_STACKED,
    		AREA_3_D_PERCENT_STACKED,
    		BAR,
    		BAR_STACKED,
    		BAR_PERCENT_STACKED,
    		BAR_3_D,
    		BAR_3_D_STACKED,
    		BAR_3_D_PERCENT_STACKED,
    		BUBBLE,
    		BUBBLE_3_D,
    		COLUMN,
    		COLUMN_STACKED,
    		COLUMN_PERCENT_STACKED,
    		COLUMN_3_D,
    		COLUMN_3_D_STACKED,
    		COLUMN_3_D_PERCENT_STACKED,
    		COLUMN_3_D_CLUSTERED,
    		DOUGHNUT,
    		LINE,
    		LINE_STACKED,
    		LINE_PERCENT_STACKED,
    		LINE_3_D,
    		PIE,
    		PIE_3_D,
    		PIE_OF_BAR,
    		PIE_OF_PIE,
    		RADAR,
    		SCATTER,
    		STOCK,
    		SURFACE,
    		SURFACE_3_D,
    		TREEMAP,
    		SUNBURST,
    		HISTOGRAM,
    		PARETO,
    		BOX_AND_WHISKER,
    		WATERFALL,
    		FUNNEL,
    	};
    }

    public static final int length = 42;
}

