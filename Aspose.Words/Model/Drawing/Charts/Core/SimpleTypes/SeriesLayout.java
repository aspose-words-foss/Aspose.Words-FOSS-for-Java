// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/03/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies series layout types.
/// Corresponds to the 2.24.4.18 ST_SeriesLayout simple type [MS-ODRAWXML].
/// </summary>
/*enum*/ final class SeriesLayout
{
    private SeriesLayout(){}
    
    /// <summary>
    /// The series is laid out as box and whisker.
    /// </summary>
    public static final int BOX_WHISKER = 0;
    /// <summary>
    /// The series is laid out as clustered columns.
    /// </summary>
    public static final int CLUSTERED_COLUMN = 1;
    /// <summary>
    /// This series is laid out as a funnel.
    /// </summary>
    public static final int FUNNEL = 2;
    /// <summary>
    /// The series is laid out as pareto lines.
    /// </summary>
    public static final int PARETO_LINE = 3;
    /// <summary>
    /// This series is laid out as a region-based map, also known as a choropleth map.
    /// </summary>
    public static final int REGION_MAP = 4;
    /// <summary>
    /// The series is laid out as a sunburst.
    /// </summary>
    public static final int SUNBURST = 5;
    /// <summary>
    /// The series is laid out as a treemap.
    /// </summary>
    public static final int TREEMAP = 6;
    /// <summary>
    /// The series is laid out as a waterfall.
    /// </summary>
    public static final int WATERFALL = 7;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SeriesLayout value.
    /// </summary>
    public static String getName(/*SeriesLayout*/int seriesLayout)
    {
    	switch (seriesLayout)
    	{
    		case BOX_WHISKER: return "BOX_WHISKER";
    		case CLUSTERED_COLUMN: return "CLUSTERED_COLUMN";
    		case FUNNEL: return "FUNNEL";
    		case PARETO_LINE: return "PARETO_LINE";
    		case REGION_MAP: return "REGION_MAP";
    		case SUNBURST: return "SUNBURST";
    		case TREEMAP: return "TREEMAP";
    		case WATERFALL: return "WATERFALL";
    		default: return "Unknown SeriesLayout value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SeriesLayout value.
    /// </summary>
    public static String toString(/*SeriesLayout*/int seriesLayout)
    {
    	switch (seriesLayout)
    	{
    		case BOX_WHISKER: return "BoxWhisker";
    		case CLUSTERED_COLUMN: return "ClusteredColumn";
    		case FUNNEL: return "Funnel";
    		case PARETO_LINE: return "ParetoLine";
    		case REGION_MAP: return "RegionMap";
    		case SUNBURST: return "Sunburst";
    		case TREEMAP: return "Treemap";
    		case WATERFALL: return "Waterfall";
    		default: return "Unknown SeriesLayout value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SeriesLayout name.
    /// </summary>
    public static /*SeriesLayout*/int fromName(String seriesLayoutName)
    {
    	if ("BOX_WHISKER".equals(seriesLayoutName)) return BOX_WHISKER;
    	if ("CLUSTERED_COLUMN".equals(seriesLayoutName)) return CLUSTERED_COLUMN;
    	if ("FUNNEL".equals(seriesLayoutName)) return FUNNEL;
    	if ("PARETO_LINE".equals(seriesLayoutName)) return PARETO_LINE;
    	if ("REGION_MAP".equals(seriesLayoutName)) return REGION_MAP;
    	if ("SUNBURST".equals(seriesLayoutName)) return SUNBURST;
    	if ("TREEMAP".equals(seriesLayoutName)) return TREEMAP;
    	if ("WATERFALL".equals(seriesLayoutName)) return WATERFALL;
    	throw new IllegalArgumentException("Unknown SeriesLayout name.");
    }
    
    /// <summary>
    /// Returns array of SeriesLayout values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BOX_WHISKER,
    		CLUSTERED_COLUMN,
    		FUNNEL,
    		PARETO_LINE,
    		REGION_MAP,
    		SUNBURST,
    		TREEMAP,
    		WATERFALL,
    	};
    }

    public static final int length = 8;
}

