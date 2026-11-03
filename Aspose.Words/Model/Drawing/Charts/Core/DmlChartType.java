// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies chart type.
/// </summary>
/*enum*/ final class DmlChartType
{
    private DmlChartType(){}
    
    public static final int AREA_CHART = 0;
    public static final int AREA_3_D_CHART = 1;
    public static final int LINE_CHART = 2;
    public static final int LINE_3_D_CHART = 3;
    public static final int STOCK_CHART = 4;
    public static final int RADAR_CHART = 5;
    public static final int SCATTER_CHART = 6;
    public static final int PIE_CHART = 7;
    public static final int PIE_3_D_CHART = 8;
    public static final int DOUGHNUT_CHART = 9;
    public static final int BAR_CHART = 10;
    public static final int BAR_3_D_CHART = 11;
    public static final int OF_PIE_CHART = 12;
    public static final int SURFACE_CHART = 13;
    public static final int SURFACE_3_D_CHART = 14;
    public static final int BUBBLE_CHART = 15;
    /// <summary>
    /// Specifies a chart of the http://schemas.microsoft.com/office/drawing/2014/chartex schema [MS-ODRAWXML].
    /// Charts of the schema are introduced in MS Office 2016. Detailed type is defined in chart series.
    /// </summary>
    public static final int CHART_EX_CHART = 16;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlChartType value.
    /// </summary>
    public static String getName(/*DmlChartType*/int dmlChartType)
    {
    	switch (dmlChartType)
    	{
    		case AREA_CHART: return "AREA_CHART";
    		case AREA_3_D_CHART: return "AREA_3_D_CHART";
    		case LINE_CHART: return "LINE_CHART";
    		case LINE_3_D_CHART: return "LINE_3_D_CHART";
    		case STOCK_CHART: return "STOCK_CHART";
    		case RADAR_CHART: return "RADAR_CHART";
    		case SCATTER_CHART: return "SCATTER_CHART";
    		case PIE_CHART: return "PIE_CHART";
    		case PIE_3_D_CHART: return "PIE_3_D_CHART";
    		case DOUGHNUT_CHART: return "DOUGHNUT_CHART";
    		case BAR_CHART: return "BAR_CHART";
    		case BAR_3_D_CHART: return "BAR_3_D_CHART";
    		case OF_PIE_CHART: return "OF_PIE_CHART";
    		case SURFACE_CHART: return "SURFACE_CHART";
    		case SURFACE_3_D_CHART: return "SURFACE_3_D_CHART";
    		case BUBBLE_CHART: return "BUBBLE_CHART";
    		case CHART_EX_CHART: return "CHART_EX_CHART";
    		default: return "Unknown DmlChartType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlChartType value.
    /// </summary>
    public static String toString(/*DmlChartType*/int dmlChartType)
    {
    	switch (dmlChartType)
    	{
    		case AREA_CHART: return "AreaChart";
    		case AREA_3_D_CHART: return "Area3DChart";
    		case LINE_CHART: return "LineChart";
    		case LINE_3_D_CHART: return "Line3DChart";
    		case STOCK_CHART: return "StockChart";
    		case RADAR_CHART: return "RadarChart";
    		case SCATTER_CHART: return "ScatterChart";
    		case PIE_CHART: return "PieChart";
    		case PIE_3_D_CHART: return "Pie3DChart";
    		case DOUGHNUT_CHART: return "DoughnutChart";
    		case BAR_CHART: return "BarChart";
    		case BAR_3_D_CHART: return "Bar3DChart";
    		case OF_PIE_CHART: return "OfPieChart";
    		case SURFACE_CHART: return "SurfaceChart";
    		case SURFACE_3_D_CHART: return "Surface3DChart";
    		case BUBBLE_CHART: return "BubbleChart";
    		case CHART_EX_CHART: return "ChartExChart";
    		default: return "Unknown DmlChartType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlChartType name.
    /// </summary>
    public static /*DmlChartType*/int fromName(String dmlChartTypeName)
    {
    	if ("AREA_CHART".equals(dmlChartTypeName)) return AREA_CHART;
    	if ("AREA_3_D_CHART".equals(dmlChartTypeName)) return AREA_3_D_CHART;
    	if ("LINE_CHART".equals(dmlChartTypeName)) return LINE_CHART;
    	if ("LINE_3_D_CHART".equals(dmlChartTypeName)) return LINE_3_D_CHART;
    	if ("STOCK_CHART".equals(dmlChartTypeName)) return STOCK_CHART;
    	if ("RADAR_CHART".equals(dmlChartTypeName)) return RADAR_CHART;
    	if ("SCATTER_CHART".equals(dmlChartTypeName)) return SCATTER_CHART;
    	if ("PIE_CHART".equals(dmlChartTypeName)) return PIE_CHART;
    	if ("PIE_3_D_CHART".equals(dmlChartTypeName)) return PIE_3_D_CHART;
    	if ("DOUGHNUT_CHART".equals(dmlChartTypeName)) return DOUGHNUT_CHART;
    	if ("BAR_CHART".equals(dmlChartTypeName)) return BAR_CHART;
    	if ("BAR_3_D_CHART".equals(dmlChartTypeName)) return BAR_3_D_CHART;
    	if ("OF_PIE_CHART".equals(dmlChartTypeName)) return OF_PIE_CHART;
    	if ("SURFACE_CHART".equals(dmlChartTypeName)) return SURFACE_CHART;
    	if ("SURFACE_3_D_CHART".equals(dmlChartTypeName)) return SURFACE_3_D_CHART;
    	if ("BUBBLE_CHART".equals(dmlChartTypeName)) return BUBBLE_CHART;
    	if ("CHART_EX_CHART".equals(dmlChartTypeName)) return CHART_EX_CHART;
    	throw new IllegalArgumentException("Unknown DmlChartType name.");
    }
    
    /// <summary>
    /// Returns array of DmlChartType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AREA_CHART,
    		AREA_3_D_CHART,
    		LINE_CHART,
    		LINE_3_D_CHART,
    		STOCK_CHART,
    		RADAR_CHART,
    		SCATTER_CHART,
    		PIE_CHART,
    		PIE_3_D_CHART,
    		DOUGHNUT_CHART,
    		BAR_CHART,
    		BAR_3_D_CHART,
    		OF_PIE_CHART,
    		SURFACE_CHART,
    		SURFACE_3_D_CHART,
    		BUBBLE_CHART,
    		CHART_EX_CHART,
    	};
    }

    public static final int length = 17;
}

