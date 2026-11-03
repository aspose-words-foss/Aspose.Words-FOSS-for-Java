// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/03/2023 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a type of a chart series.
/// </summary>
public /*enum*/ final class ChartSeriesType
{
    private ChartSeriesType(){}
    
    /// <summary>
    /// Represents an Area chart series.
    /// </summary>
    public static final int AREA = 0;

    /// <summary>
    /// Represents a Stacked Area chart series.
    /// </summary>
    public static final int AREA_STACKED = 1;

    /// <summary>
    /// Represents a 100% Stacked Area chart series.
    /// </summary>
    public static final int AREA_PERCENT_STACKED = 2;

    /// <summary>
    /// Represents a 3D Area chart series.
    /// </summary>
    public static final int AREA_3_D = 3;

    /// <summary>
    /// Represents a 3D Stacked Area chart series.
    /// </summary>
    public static final int AREA_3_D_STACKED = 4;

    /// <summary>
    /// Represents a 3D 100% Stacked Area chart series.
    /// </summary>
    public static final int AREA_3_D_PERCENT_STACKED = 5;

    /// <summary>
    /// Represents a Bar chart series.
    /// </summary>
    public static final int BAR = 6;

    /// <summary>
    /// Represents a Stacked Bar chart series.
    /// </summary>
    public static final int BAR_STACKED = 7;

    /// <summary>
    /// Represents a 100% Stacked Bar chart series.
    /// </summary>
    public static final int BAR_PERCENT_STACKED = 8;

    /// <summary>
    /// Represents a 3D Bar chart series.
    /// </summary>
    public static final int BAR_3_D = 9;

    /// <summary>
    /// Represents a 3D Stacked Bar chart series.
    /// </summary>
    public static final int BAR_3_D_STACKED = 10;

    /// <summary>
    /// Represents a 3D 100% Stacked Bar chart series.
    /// </summary>
    public static final int BAR_3_D_PERCENT_STACKED = 11;

    /// <summary>
    /// Represents a Bubble chart series.
    /// </summary>
    public static final int BUBBLE = 12;

    /// <summary>
    /// Represents a 3D Bubble chart series.
    /// </summary>
    public static final int BUBBLE_3_D = 13;

    /// <summary>
    /// Represents a Column chart series.
    /// </summary>
    public static final int COLUMN = 14;

    /// <summary>
    /// Represents a Stacked Column chart series.
    /// </summary>
    public static final int COLUMN_STACKED = 15;

    /// <summary>
    /// Represents a 100% Stacked Column chart series.
    /// </summary>
    public static final int COLUMN_PERCENT_STACKED = 16;

    /// <summary>
    /// Represents a 3D Column chart series.
    /// </summary>
    public static final int COLUMN_3_D = 17;

    /// <summary>
    /// Represents a 3D Stacked Column chart series.
    /// </summary>
    public static final int COLUMN_3_D_STACKED = 18;

    /// <summary>
    /// Represents a 3D 100% Stacked Column chart series.
    /// </summary>
    public static final int COLUMN_3_D_PERCENT_STACKED = 19;

    /// <summary>
    /// Represents a 3D Clustered Column chart series.
    /// </summary>
    public static final int COLUMN_3_D_CLUSTERED = 20;

    /// <summary>
    /// Represents a Doughnut chart series.
    /// </summary>
    public static final int DOUGHNUT = 21;

    /// <summary>
    /// Represents a Line chart series.
    /// </summary>
    public static final int LINE = 22;

    /// <summary>
    /// Represents a Stacked Line chart series.
    /// </summary>
    public static final int LINE_STACKED = 23;

    /// <summary>
    /// Represents a 100% Stacked Line chart series.
    /// </summary>
    public static final int LINE_PERCENT_STACKED = 24;

    /// <summary>
    /// Represents a 3D Line chart series.
    /// </summary>
    public static final int LINE_3_D = 25;

    /// <summary>
    /// Represents a Pie chart series.
    /// </summary>
    public static final int PIE = 26;

    /// <summary>
    /// Represents a 3D Pie chart series.
    /// </summary>
    public static final int PIE_3_D = 27;

    /// <summary>
    /// Represents a Pie of Bar chart series.
    /// </summary>
    public static final int PIE_OF_BAR = 28;

    /// <summary>
    /// Represents a Pie of Pie chart series.
    /// </summary>
    public static final int PIE_OF_PIE = 29;

    /// <summary>
    /// Represents a Radar chart series.
    /// </summary>
    public static final int RADAR = 30;

    /// <summary>
    /// Represents a Scatter chart series.
    /// </summary>
    public static final int SCATTER = 31;

    /// <summary>
    /// Represents a Stock chart series.
    /// </summary>
    public static final int STOCK = 32;

    /// <summary>
    /// Represents a Surface chart series.
    /// </summary>
    public static final int SURFACE = 33;

    /// <summary>
    /// Represents a 3D Surface chart series.
    /// </summary>
    public static final int SURFACE_3_D = 34;

    /// <summary>
    /// Represents a Treemap chart series.
    /// </summary>
    public static final int TREEMAP = 35;

    /// <summary>
    /// Represents a Sunburst chart series.
    /// </summary>
    public static final int SUNBURST = 36;

    /// <summary>
    /// Represents a Histogram chart series.
    /// </summary>
    public static final int HISTOGRAM = 37;

    /// <summary>
    /// Represents a Pareto chart series.
    /// </summary>
    public static final int PARETO = 38;

    /// <summary>
    /// Represents a Pareto Line chart series.
    /// </summary>
    public static final int PARETO_LINE = 39;

    /// <summary>
    /// Represents a Box and Whisker chart series.
    /// </summary>
    public static final int BOX_AND_WHISKER = 40;

    /// <summary>
    /// Represents a Waterfall chart series.
    /// </summary>
    public static final int WATERFALL = 41;

    /// <summary>
    /// Represents a Funnel chart series.
    /// </summary>
    public static final int FUNNEL = 42;

    /// <summary>
    /// Represents a Region Map chart series.
    /// </summary>
    public static final int REGION_MAP = 43;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ChartSeriesType value.
    /// </summary>
    public static String getName(/*ChartSeriesType*/int chartSeriesType)
    {
    	switch (chartSeriesType)
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
    		case PARETO_LINE: return "PARETO_LINE";
    		case BOX_AND_WHISKER: return "BOX_AND_WHISKER";
    		case WATERFALL: return "WATERFALL";
    		case FUNNEL: return "FUNNEL";
    		case REGION_MAP: return "REGION_MAP";
    		default: return "Unknown ChartSeriesType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ChartSeriesType value.
    /// </summary>
    public static String toString(/*ChartSeriesType*/int chartSeriesType)
    {
    	switch (chartSeriesType)
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
    		case PARETO_LINE: return "ParetoLine";
    		case BOX_AND_WHISKER: return "BoxAndWhisker";
    		case WATERFALL: return "Waterfall";
    		case FUNNEL: return "Funnel";
    		case REGION_MAP: return "RegionMap";
    		default: return "Unknown ChartSeriesType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ChartSeriesType name.
    /// </summary>
    public static /*ChartSeriesType*/int fromName(String chartSeriesTypeName)
    {
    	if ("AREA".equals(chartSeriesTypeName)) return AREA;
    	if ("AREA_STACKED".equals(chartSeriesTypeName)) return AREA_STACKED;
    	if ("AREA_PERCENT_STACKED".equals(chartSeriesTypeName)) return AREA_PERCENT_STACKED;
    	if ("AREA_3_D".equals(chartSeriesTypeName)) return AREA_3_D;
    	if ("AREA_3_D_STACKED".equals(chartSeriesTypeName)) return AREA_3_D_STACKED;
    	if ("AREA_3_D_PERCENT_STACKED".equals(chartSeriesTypeName)) return AREA_3_D_PERCENT_STACKED;
    	if ("BAR".equals(chartSeriesTypeName)) return BAR;
    	if ("BAR_STACKED".equals(chartSeriesTypeName)) return BAR_STACKED;
    	if ("BAR_PERCENT_STACKED".equals(chartSeriesTypeName)) return BAR_PERCENT_STACKED;
    	if ("BAR_3_D".equals(chartSeriesTypeName)) return BAR_3_D;
    	if ("BAR_3_D_STACKED".equals(chartSeriesTypeName)) return BAR_3_D_STACKED;
    	if ("BAR_3_D_PERCENT_STACKED".equals(chartSeriesTypeName)) return BAR_3_D_PERCENT_STACKED;
    	if ("BUBBLE".equals(chartSeriesTypeName)) return BUBBLE;
    	if ("BUBBLE_3_D".equals(chartSeriesTypeName)) return BUBBLE_3_D;
    	if ("COLUMN".equals(chartSeriesTypeName)) return COLUMN;
    	if ("COLUMN_STACKED".equals(chartSeriesTypeName)) return COLUMN_STACKED;
    	if ("COLUMN_PERCENT_STACKED".equals(chartSeriesTypeName)) return COLUMN_PERCENT_STACKED;
    	if ("COLUMN_3_D".equals(chartSeriesTypeName)) return COLUMN_3_D;
    	if ("COLUMN_3_D_STACKED".equals(chartSeriesTypeName)) return COLUMN_3_D_STACKED;
    	if ("COLUMN_3_D_PERCENT_STACKED".equals(chartSeriesTypeName)) return COLUMN_3_D_PERCENT_STACKED;
    	if ("COLUMN_3_D_CLUSTERED".equals(chartSeriesTypeName)) return COLUMN_3_D_CLUSTERED;
    	if ("DOUGHNUT".equals(chartSeriesTypeName)) return DOUGHNUT;
    	if ("LINE".equals(chartSeriesTypeName)) return LINE;
    	if ("LINE_STACKED".equals(chartSeriesTypeName)) return LINE_STACKED;
    	if ("LINE_PERCENT_STACKED".equals(chartSeriesTypeName)) return LINE_PERCENT_STACKED;
    	if ("LINE_3_D".equals(chartSeriesTypeName)) return LINE_3_D;
    	if ("PIE".equals(chartSeriesTypeName)) return PIE;
    	if ("PIE_3_D".equals(chartSeriesTypeName)) return PIE_3_D;
    	if ("PIE_OF_BAR".equals(chartSeriesTypeName)) return PIE_OF_BAR;
    	if ("PIE_OF_PIE".equals(chartSeriesTypeName)) return PIE_OF_PIE;
    	if ("RADAR".equals(chartSeriesTypeName)) return RADAR;
    	if ("SCATTER".equals(chartSeriesTypeName)) return SCATTER;
    	if ("STOCK".equals(chartSeriesTypeName)) return STOCK;
    	if ("SURFACE".equals(chartSeriesTypeName)) return SURFACE;
    	if ("SURFACE_3_D".equals(chartSeriesTypeName)) return SURFACE_3_D;
    	if ("TREEMAP".equals(chartSeriesTypeName)) return TREEMAP;
    	if ("SUNBURST".equals(chartSeriesTypeName)) return SUNBURST;
    	if ("HISTOGRAM".equals(chartSeriesTypeName)) return HISTOGRAM;
    	if ("PARETO".equals(chartSeriesTypeName)) return PARETO;
    	if ("PARETO_LINE".equals(chartSeriesTypeName)) return PARETO_LINE;
    	if ("BOX_AND_WHISKER".equals(chartSeriesTypeName)) return BOX_AND_WHISKER;
    	if ("WATERFALL".equals(chartSeriesTypeName)) return WATERFALL;
    	if ("FUNNEL".equals(chartSeriesTypeName)) return FUNNEL;
    	if ("REGION_MAP".equals(chartSeriesTypeName)) return REGION_MAP;
    	throw new IllegalArgumentException("Unknown ChartSeriesType name.");
    }
    
    /// <summary>
    /// Returns array of ChartSeriesType values.
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
    		PARETO_LINE,
    		BOX_AND_WHISKER,
    		WATERFALL,
    		FUNNEL,
    		REGION_MAP,
    	};
    }

    public static final int length = 44;
}

