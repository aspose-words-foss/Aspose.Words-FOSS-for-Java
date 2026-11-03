// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/06/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enum to index <see cref="DmlChartStyleEntry"/> elements of a <see cref="DmlChartStyle"/> instance.
/// </summary>
/*enum*/ final class DmlChartStyleItem
{
    private DmlChartStyleItem(){}
    
    /// <summary>
    /// Represents axisTitle: a CT_StyleEntry element that specifies default formatting for an axis title.
    /// </summary>
    public static final int AXIS_TITLE = 0;
    /// <summary>
    /// Represents categoryAxis: a CT_StyleEntry element that specifies default formatting for a category axis.
    /// </summary>
    public static final int CATEGORY_AXIS = 1;
    /// <summary>
    /// Represents chartArea: a CT_StyleEntry element that specifies default formatting for a chart area.
    /// </summary>
    public static final int CHART_AREA = 2;
    /// <summary>
    /// Represents dataLabel: a CT_StyleEntry element that specifies default formatting for a data label.
    /// </summary>
    public static final int DATA_LABEL = 3;
    /// <summary>
    /// Represents dataLabelCallout: a CT_StyleEntry element that specifies default formatting for a data label
    /// callout. It is not required element of the CT_ChartStyle type.
    /// </summary>
    public static final int DATA_LABEL_CALLOUT = 4;
    /// <summary>
    /// Represents dataPoint: a CT_StyleEntry element that specifies default formatting for a data point on 
    /// a 2-D chart of type column, bar, filled radar, stock, bubble, pie, doughnut, and area as well 
    /// as 3-D bubble.
    /// </summary>
    public static final int DATA_POINT = 5;
    /// <summary>
    /// Represents dataPoint3D: a CT_StyleEntry element that specifies default formatting for a data point on
    /// a 3-D chart of type column, bar, line, pie, area and surface.
    /// </summary>
    public static final int DATA_POINT_3_D = 6;
    /// <summary>
    /// Represents dataPointLine: a CT_StyleEntry element that specifies default formatting for a data point
    /// on a 2-D chart of type line, scatter and radar.
    /// </summary>
    public static final int DATA_POINT_LINE = 7;
    /// <summary>
    /// Represents dataPointMarker: a CT_StyleEntry element that specifies default formatting for markers.
    /// </summary>
    public static final int DATA_POINT_MARKER = 8;
    /// <summary>
    /// Represents dataPointWireframe: a CT_StyleEntry element that specifies default formatting for a data 
    /// point on a surface wireframe chart.
    /// </summary>
    public static final int DATA_POINT_WIREFRAME = 9;
    /// <summary>
    /// Represents dataTable: a CT_StyleEntry element that specifies default formatting for a data table.
    /// </summary>
    public static final int DATA_TABLE = 10;
    /// <summary>
    /// Represents downBar: a CT_StyleEntry element that specifies default formatting for a down bar.
    /// </summary>
    public static final int DOWN_BAR = 11;
    /// <summary>
    /// Represents dropLine: a CT_StyleEntry element that specifies default formatting for a drop line.
    /// </summary>
    public static final int DROP_LINE = 12;
    /// <summary>
    /// Represents errorBar: a CT_StyleEntry element that specifies default formatting for an error bar.
    /// </summary>
    public static final int ERROR_BAR = 13;
    /// <summary>
    /// Represents floor: a CT_StyleEntry element that specifies default formatting for a floor.
    /// </summary>
    public static final int FLOOR = 14;
    /// <summary>
    /// Represents gridlineMajor: a CT_StyleEntry element that specifies default formatting for 
    /// a major gridline.
    /// </summary>
    public static final int GRIDLINE_MAJOR = 15;
    /// <summary>
    /// Represents gridlineMinor: a CT_StyleEntry element that specifies default formatting for 
    /// a minor gridline.
    /// </summary>
    public static final int GRIDLINE_MINOR = 16;
    /// <summary>
    /// Represents hiLoLine: a CT_StyleEntry element that specifies default formatting for a high low line.
    /// </summary>
    public static final int HIGH_LOW_LINE = 17;
    /// <summary>
    /// Represents leaderLine: a CT_StyleEntry element that specifies default formatting for a leader line.
    /// </summary>
    public static final int LEADER_LINE = 18;
    /// <summary>
    /// Represents legend: a CT_StyleEntry element that specifies default formatting for a legend.
    /// </summary>
    public static final int LEGEND = 19;
    /// <summary>
    /// Represents plotArea: a CT_StyleEntry element that specifies default formatting for a plot area on 
    /// a 2-D chart.
    /// </summary>
    public static final int PLOT_AREA = 20;
    /// <summary>
    /// Represents plotArea3D: a CT_StyleEntry element that specifies default formatting for a 3-D chart.
    /// </summary>
    public static final int PLOT_AREA_3_D = 21;
    /// <summary>
    /// Represents seriesAxis: a CT_StyleEntry element that specifies default formatting for a series axis.
    /// </summary>
    public static final int SERIES_AXIS = 22;
    /// <summary>
    /// Represents seriesLine: a CT_StyleEntry element that specifies default formatting for a series line.
    /// </summary>
    public static final int SERIES_LINE = 23;
    /// <summary>
    /// Represents title: a CT_StyleEntry element that specifies default formatting for a chart title.
    /// </summary>
    public static final int TITLE = 24;
    /// <summary>
    /// Represents trendline: a CT_StyleEntry element that specifies default formatting for a trend line.
    /// </summary>
    public static final int TREND_LINE = 25;
    /// <summary>
    /// Represents trendlineLabel: a CT_StyleEntry element that specifies default formatting for 
    /// a trend line label.
    /// </summary>
    public static final int TRENDLINE_LABEL = 26;
    /// <summary>
    /// Represents upBar: a CT_StyleEntry element that specifies default formatting for an up bar.
    /// </summary>
    public static final int UP_BAR = 27;
    /// <summary>
    /// Represents valueAxis: a CT_StyleEntry element that specifies default formatting for a value axis.
    /// </summary>
    public static final int VALUE_AXIS = 28;
    /// <summary>
    /// Represents wall: a CT_StyleEntry element that specifies default formatting for a wall.
    /// </summary>
    public static final int WALL = 29;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlChartStyleItem value.
    /// </summary>
    public static String getName(/*DmlChartStyleItem*/int dmlChartStyleItem)
    {
    	switch (dmlChartStyleItem)
    	{
    		case AXIS_TITLE: return "AXIS_TITLE";
    		case CATEGORY_AXIS: return "CATEGORY_AXIS";
    		case CHART_AREA: return "CHART_AREA";
    		case DATA_LABEL: return "DATA_LABEL";
    		case DATA_LABEL_CALLOUT: return "DATA_LABEL_CALLOUT";
    		case DATA_POINT: return "DATA_POINT";
    		case DATA_POINT_3_D: return "DATA_POINT_3_D";
    		case DATA_POINT_LINE: return "DATA_POINT_LINE";
    		case DATA_POINT_MARKER: return "DATA_POINT_MARKER";
    		case DATA_POINT_WIREFRAME: return "DATA_POINT_WIREFRAME";
    		case DATA_TABLE: return "DATA_TABLE";
    		case DOWN_BAR: return "DOWN_BAR";
    		case DROP_LINE: return "DROP_LINE";
    		case ERROR_BAR: return "ERROR_BAR";
    		case FLOOR: return "FLOOR";
    		case GRIDLINE_MAJOR: return "GRIDLINE_MAJOR";
    		case GRIDLINE_MINOR: return "GRIDLINE_MINOR";
    		case HIGH_LOW_LINE: return "HIGH_LOW_LINE";
    		case LEADER_LINE: return "LEADER_LINE";
    		case LEGEND: return "LEGEND";
    		case PLOT_AREA: return "PLOT_AREA";
    		case PLOT_AREA_3_D: return "PLOT_AREA_3_D";
    		case SERIES_AXIS: return "SERIES_AXIS";
    		case SERIES_LINE: return "SERIES_LINE";
    		case TITLE: return "TITLE";
    		case TREND_LINE: return "TREND_LINE";
    		case TRENDLINE_LABEL: return "TRENDLINE_LABEL";
    		case UP_BAR: return "UP_BAR";
    		case VALUE_AXIS: return "VALUE_AXIS";
    		case WALL: return "WALL";
    		default: return "Unknown DmlChartStyleItem value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlChartStyleItem value.
    /// </summary>
    public static String toString(/*DmlChartStyleItem*/int dmlChartStyleItem)
    {
    	switch (dmlChartStyleItem)
    	{
    		case AXIS_TITLE: return "AxisTitle";
    		case CATEGORY_AXIS: return "CategoryAxis";
    		case CHART_AREA: return "ChartArea";
    		case DATA_LABEL: return "DataLabel";
    		case DATA_LABEL_CALLOUT: return "DataLabelCallout";
    		case DATA_POINT: return "DataPoint";
    		case DATA_POINT_3_D: return "DataPoint3D";
    		case DATA_POINT_LINE: return "DataPointLine";
    		case DATA_POINT_MARKER: return "DataPointMarker";
    		case DATA_POINT_WIREFRAME: return "DataPointWireframe";
    		case DATA_TABLE: return "DataTable";
    		case DOWN_BAR: return "DownBar";
    		case DROP_LINE: return "DropLine";
    		case ERROR_BAR: return "ErrorBar";
    		case FLOOR: return "Floor";
    		case GRIDLINE_MAJOR: return "GridlineMajor";
    		case GRIDLINE_MINOR: return "GridlineMinor";
    		case HIGH_LOW_LINE: return "HighLowLine";
    		case LEADER_LINE: return "LeaderLine";
    		case LEGEND: return "Legend";
    		case PLOT_AREA: return "PlotArea";
    		case PLOT_AREA_3_D: return "PlotArea3D";
    		case SERIES_AXIS: return "SeriesAxis";
    		case SERIES_LINE: return "SeriesLine";
    		case TITLE: return "Title";
    		case TREND_LINE: return "TrendLine";
    		case TRENDLINE_LABEL: return "TrendlineLabel";
    		case UP_BAR: return "UpBar";
    		case VALUE_AXIS: return "ValueAxis";
    		case WALL: return "Wall";
    		default: return "Unknown DmlChartStyleItem value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlChartStyleItem name.
    /// </summary>
    public static /*DmlChartStyleItem*/int fromName(String dmlChartStyleItemName)
    {
    	if ("AXIS_TITLE".equals(dmlChartStyleItemName)) return AXIS_TITLE;
    	if ("CATEGORY_AXIS".equals(dmlChartStyleItemName)) return CATEGORY_AXIS;
    	if ("CHART_AREA".equals(dmlChartStyleItemName)) return CHART_AREA;
    	if ("DATA_LABEL".equals(dmlChartStyleItemName)) return DATA_LABEL;
    	if ("DATA_LABEL_CALLOUT".equals(dmlChartStyleItemName)) return DATA_LABEL_CALLOUT;
    	if ("DATA_POINT".equals(dmlChartStyleItemName)) return DATA_POINT;
    	if ("DATA_POINT_3_D".equals(dmlChartStyleItemName)) return DATA_POINT_3_D;
    	if ("DATA_POINT_LINE".equals(dmlChartStyleItemName)) return DATA_POINT_LINE;
    	if ("DATA_POINT_MARKER".equals(dmlChartStyleItemName)) return DATA_POINT_MARKER;
    	if ("DATA_POINT_WIREFRAME".equals(dmlChartStyleItemName)) return DATA_POINT_WIREFRAME;
    	if ("DATA_TABLE".equals(dmlChartStyleItemName)) return DATA_TABLE;
    	if ("DOWN_BAR".equals(dmlChartStyleItemName)) return DOWN_BAR;
    	if ("DROP_LINE".equals(dmlChartStyleItemName)) return DROP_LINE;
    	if ("ERROR_BAR".equals(dmlChartStyleItemName)) return ERROR_BAR;
    	if ("FLOOR".equals(dmlChartStyleItemName)) return FLOOR;
    	if ("GRIDLINE_MAJOR".equals(dmlChartStyleItemName)) return GRIDLINE_MAJOR;
    	if ("GRIDLINE_MINOR".equals(dmlChartStyleItemName)) return GRIDLINE_MINOR;
    	if ("HIGH_LOW_LINE".equals(dmlChartStyleItemName)) return HIGH_LOW_LINE;
    	if ("LEADER_LINE".equals(dmlChartStyleItemName)) return LEADER_LINE;
    	if ("LEGEND".equals(dmlChartStyleItemName)) return LEGEND;
    	if ("PLOT_AREA".equals(dmlChartStyleItemName)) return PLOT_AREA;
    	if ("PLOT_AREA_3_D".equals(dmlChartStyleItemName)) return PLOT_AREA_3_D;
    	if ("SERIES_AXIS".equals(dmlChartStyleItemName)) return SERIES_AXIS;
    	if ("SERIES_LINE".equals(dmlChartStyleItemName)) return SERIES_LINE;
    	if ("TITLE".equals(dmlChartStyleItemName)) return TITLE;
    	if ("TREND_LINE".equals(dmlChartStyleItemName)) return TREND_LINE;
    	if ("TRENDLINE_LABEL".equals(dmlChartStyleItemName)) return TRENDLINE_LABEL;
    	if ("UP_BAR".equals(dmlChartStyleItemName)) return UP_BAR;
    	if ("VALUE_AXIS".equals(dmlChartStyleItemName)) return VALUE_AXIS;
    	if ("WALL".equals(dmlChartStyleItemName)) return WALL;
    	throw new IllegalArgumentException("Unknown DmlChartStyleItem name.");
    }
    
    /// <summary>
    /// Returns array of DmlChartStyleItem values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AXIS_TITLE,
    		CATEGORY_AXIS,
    		CHART_AREA,
    		DATA_LABEL,
    		DATA_LABEL_CALLOUT,
    		DATA_POINT,
    		DATA_POINT_3_D,
    		DATA_POINT_LINE,
    		DATA_POINT_MARKER,
    		DATA_POINT_WIREFRAME,
    		DATA_TABLE,
    		DOWN_BAR,
    		DROP_LINE,
    		ERROR_BAR,
    		FLOOR,
    		GRIDLINE_MAJOR,
    		GRIDLINE_MINOR,
    		HIGH_LOW_LINE,
    		LEADER_LINE,
    		LEGEND,
    		PLOT_AREA,
    		PLOT_AREA_3_D,
    		SERIES_AXIS,
    		SERIES_LINE,
    		TITLE,
    		TREND_LINE,
    		TRENDLINE_LABEL,
    		UP_BAR,
    		VALUE_AXIS,
    		WALL,
    	};
    }

    public static final int length = 30;
}

