// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/03/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Contains list of attributes of 2.24.3.72 CT_SeriesLayoutProperties [MS-ODRAWXML].
/// </summary>
/*enum*/ final class DmlChartSeriesLayoutAttr
{
    private DmlChartSeriesLayoutAttr(){}
    
    /// <summary>
    /// Specifies the layout type for the parent labels.
    /// Property data type is <see cref="SimpleTypes.ParentLabelLayout"/>.
    /// </summary>
    public static final int PARENT_LABEL_LAYOUT = 0;

    /// <summary>
    /// Specifies the layout type for region labels of a geospatial series.
    /// Property data type is <see cref="SimpleTypes.RegionLabelLayout"/>.
    /// </summary>
    public static final int REGION_LABEL_LAYOUT = 1;

    /// <summary>
    /// Specifies the visibility of connector lines between data points.
    /// Property data type is <b>bool</b>.
    /// </summary>
    public static final int IS_CONNECTOR_LINES_VISIBLE = 2;

    /// <summary>
    /// Specifies the visibility of the line connecting all mean points.
    /// Property data type is <b>bool</b>.
    /// </summary>
    public static final int IS_MEAN_LINE_VISIBLE = 3;

    /// <summary>
    /// Specifies the visibility of markers denoting the mean.
    /// Property data type is <b>bool</b>.
    /// </summary>
    public static final int IS_MEAN_MARKER_VISIBLE = 4;

    /// <summary>
    /// Specifies the visibility of non-outlier data points.
    /// Property data type is <b>bool</b>.
    /// </summary>
    public static final int IS_NON_OUTLIERS_VISIBLE = 5;

    /// <summary>
    /// Specifies the visibility of outlier data points.
    /// Property data type is <b>bool</b>.
    /// </summary>
    public static final int IS_OUTLIERS_VISIBLE = 6;

    /// <summary>
    /// Specifies whether data aggregation is performed.
    /// Property data type is <b>bool</b>.
    /// </summary>
    public static final int IS_AGGREGATION = 7;

    /// <summary>
    /// Specifies the data binning properties for the series.
    /// Property data type is <see cref="DmlChartBinningPr"/>.
    /// </summary>
    public static final int BINNING = 8;

    /// <summary>
    /// Specifies layout properties for a geospatial series.
    /// </summary>
    public static final int GEOGRAPHY = 9;

    /// <summary>
    /// Specifies the quartile calculation method.
    /// Property data type is <see cref="SimpleTypes.QuartileMethod"/>.
    /// </summary>
    public static final int QUARTILE_METHOD = 10;

    /// <summary>
    /// Specifies a list of subtotal data points.
    /// Property data type is <see cref="List{T}"/> of <b>int</b>.
    /// </summary>
    public static final int SUBTOTALS = 11;

    /// <summary>
    /// Specifies an extensibility container.
    /// Property data type is <see cref="StringToObjDictionary{TValue}"/>.
    /// </summary>
    public static final int EXTENSIONS = 12;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlChartSeriesLayoutAttr value.
    /// </summary>
    public static String getName(/*DmlChartSeriesLayoutAttr*/int dmlChartSeriesLayoutAttr)
    {
    	switch (dmlChartSeriesLayoutAttr)
    	{
    		case PARENT_LABEL_LAYOUT: return "PARENT_LABEL_LAYOUT";
    		case REGION_LABEL_LAYOUT: return "REGION_LABEL_LAYOUT";
    		case IS_CONNECTOR_LINES_VISIBLE: return "IS_CONNECTOR_LINES_VISIBLE";
    		case IS_MEAN_LINE_VISIBLE: return "IS_MEAN_LINE_VISIBLE";
    		case IS_MEAN_MARKER_VISIBLE: return "IS_MEAN_MARKER_VISIBLE";
    		case IS_NON_OUTLIERS_VISIBLE: return "IS_NON_OUTLIERS_VISIBLE";
    		case IS_OUTLIERS_VISIBLE: return "IS_OUTLIERS_VISIBLE";
    		case IS_AGGREGATION: return "IS_AGGREGATION";
    		case BINNING: return "BINNING";
    		case GEOGRAPHY: return "GEOGRAPHY";
    		case QUARTILE_METHOD: return "QUARTILE_METHOD";
    		case SUBTOTALS: return "SUBTOTALS";
    		case EXTENSIONS: return "EXTENSIONS";
    		default: return "Unknown DmlChartSeriesLayoutAttr value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlChartSeriesLayoutAttr value.
    /// </summary>
    public static String toString(/*DmlChartSeriesLayoutAttr*/int dmlChartSeriesLayoutAttr)
    {
    	switch (dmlChartSeriesLayoutAttr)
    	{
    		case PARENT_LABEL_LAYOUT: return "ParentLabelLayout";
    		case REGION_LABEL_LAYOUT: return "RegionLabelLayout";
    		case IS_CONNECTOR_LINES_VISIBLE: return "IsConnectorLinesVisible";
    		case IS_MEAN_LINE_VISIBLE: return "IsMeanLineVisible";
    		case IS_MEAN_MARKER_VISIBLE: return "IsMeanMarkerVisible";
    		case IS_NON_OUTLIERS_VISIBLE: return "IsNonOutliersVisible";
    		case IS_OUTLIERS_VISIBLE: return "IsOutliersVisible";
    		case IS_AGGREGATION: return "IsAggregation";
    		case BINNING: return "Binning";
    		case GEOGRAPHY: return "Geography";
    		case QUARTILE_METHOD: return "QuartileMethod";
    		case SUBTOTALS: return "Subtotals";
    		case EXTENSIONS: return "Extensions";
    		default: return "Unknown DmlChartSeriesLayoutAttr value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlChartSeriesLayoutAttr name.
    /// </summary>
    public static /*DmlChartSeriesLayoutAttr*/int fromName(String dmlChartSeriesLayoutAttrName)
    {
    	if ("PARENT_LABEL_LAYOUT".equals(dmlChartSeriesLayoutAttrName)) return PARENT_LABEL_LAYOUT;
    	if ("REGION_LABEL_LAYOUT".equals(dmlChartSeriesLayoutAttrName)) return REGION_LABEL_LAYOUT;
    	if ("IS_CONNECTOR_LINES_VISIBLE".equals(dmlChartSeriesLayoutAttrName)) return IS_CONNECTOR_LINES_VISIBLE;
    	if ("IS_MEAN_LINE_VISIBLE".equals(dmlChartSeriesLayoutAttrName)) return IS_MEAN_LINE_VISIBLE;
    	if ("IS_MEAN_MARKER_VISIBLE".equals(dmlChartSeriesLayoutAttrName)) return IS_MEAN_MARKER_VISIBLE;
    	if ("IS_NON_OUTLIERS_VISIBLE".equals(dmlChartSeriesLayoutAttrName)) return IS_NON_OUTLIERS_VISIBLE;
    	if ("IS_OUTLIERS_VISIBLE".equals(dmlChartSeriesLayoutAttrName)) return IS_OUTLIERS_VISIBLE;
    	if ("IS_AGGREGATION".equals(dmlChartSeriesLayoutAttrName)) return IS_AGGREGATION;
    	if ("BINNING".equals(dmlChartSeriesLayoutAttrName)) return BINNING;
    	if ("GEOGRAPHY".equals(dmlChartSeriesLayoutAttrName)) return GEOGRAPHY;
    	if ("QUARTILE_METHOD".equals(dmlChartSeriesLayoutAttrName)) return QUARTILE_METHOD;
    	if ("SUBTOTALS".equals(dmlChartSeriesLayoutAttrName)) return SUBTOTALS;
    	if ("EXTENSIONS".equals(dmlChartSeriesLayoutAttrName)) return EXTENSIONS;
    	throw new IllegalArgumentException("Unknown DmlChartSeriesLayoutAttr name.");
    }
    
    /// <summary>
    /// Returns array of DmlChartSeriesLayoutAttr values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		PARENT_LABEL_LAYOUT,
    		REGION_LABEL_LAYOUT,
    		IS_CONNECTOR_LINES_VISIBLE,
    		IS_MEAN_LINE_VISIBLE,
    		IS_MEAN_MARKER_VISIBLE,
    		IS_NON_OUTLIERS_VISIBLE,
    		IS_OUTLIERS_VISIBLE,
    		IS_AGGREGATION,
    		BINNING,
    		GEOGRAPHY,
    		QUARTILE_METHOD,
    		SUBTOTALS,
    		EXTENSIONS,
    	};
    }

    public static final int length = 13;
}

