// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToIntBidirectionalMap;


/// <summary>
/// Class contains method for converting string values read from XML to the corresponding enum of simple types values.
/// </summary>
/*static*/ class DmlChartsEnum
{
	/* Simulation of static class by using private constructor */
	private DmlChartsEnum()
	{}

    static /*AxisPosition*/int dmlToAxisPosition(String value)
    {
        return (/*AxisPosition*/int)G_AXIS_POSITION_MAP.getValue(value, (int)AxisPosition.BOTTOM);
    }

    static String axisPositionToDml(/*AxisPosition*/int value)
    {
        return G_AXIS_POSITION_MAP.getValue((int)value, "");
    }

    static /*BarDirection*/int dmlToBarDirection(String value)
    {
        return (/*BarDirection*/int)G_BAR_DIRECTION_MAP.getValue(value, (int)BarDirection.COLUMN);
    }

    static String barDirectionToDml(/*BarDirection*/int value)
    {
        return G_BAR_DIRECTION_MAP.getValue((int)value, "");
    }

    static /*Grouping*/int dmlToGrouping(String value)
    {
        return (/*Grouping*/int)G_BAR_GROUPING_MAP.getValue(value, (int)Grouping.CLUSTERED);
    }

    static String groupingToDml(/*Grouping*/int value)
    {
        return G_BAR_GROUPING_MAP.getValue((int)value, "");
    }

    static /*AxisBuiltInUnit*/int dmlToBuiltInUnit(String value)
    {
        return (/*AxisBuiltInUnit*/int)G_BUILT_IN_UNIT_MAP.getValue(value, (int)AxisBuiltInUnit.THOUSANDS);
    }

    static String builtInUnitToDml(/*AxisBuiltInUnit*/int value)
    {
        return G_BUILT_IN_UNIT_MAP.getValue((int)value, "");
    }

    static /*CrossBetween*/int dmlToCrossBetween(String value)
    {
        return (/*CrossBetween*/int)G_CROSS_BETWEEN_MAP.getValue(value, (int)CrossBetween.BETWEEN);
    }

    static String crossBetweenToDml(/*CrossBetween*/int value)
    {
        return G_CROSS_BETWEEN_MAP.getValue((int)value, "");
    }

    static /*AxisCrosses*/int dmlToCrosses(String value)
    {
        return (/*AxisCrosses*/int)G_CROSSES_MAP.getValue(value, (int)AxisCrosses.AUTOMATIC);
    }

    static String crossesToDml(/*AxisCrosses*/int value)
    {
        return G_CROSSES_MAP.getValue((int)value, "");
    }

    static /*ChartDataLabelPosition*/int dmlToDataLabelPosition(String value)
    {
        return (/*ChartDataLabelPosition*/int)G_DATA_LABEL_POSITION_MAP.getValue(value, (int)DmlChartDataLabelPr.DEFAULT_POSITION);
    }

    static String dataLabelPositionToDml(/*ChartDataLabelPosition*/int value)
    {
        return G_DATA_LABEL_POSITION_MAP.getValue((int)value, "");
    }

    static /*DisplayBlanksAs*/int dmlToDisplayBlanksAs(String value)
    {
        return (/*DisplayBlanksAs*/int)G_DISPLAY_BLANKS_AS_MAP.getValue(value, (int)DisplayBlanksAs.ZERO);
    }

    static String displayBlanksAsToDml(/*DisplayBlanksAs*/int value)
    {
        return G_DISPLAY_BLANKS_AS_MAP.getValue((int) value, "");
    }

    static /*ErrorBarDirection*/int dmlToErrorBarDirection(String value)
    {
        return (/*ErrorBarDirection*/int)G_ERROR_BAR_DIRECTION_MAP.getValue(value, (int)ErrorBarDirection.X);
    }

    static String errorBarDirectionToDml(/*ErrorBarDirection*/int value)
    {
        return G_ERROR_BAR_DIRECTION_MAP.getValue((int)value, "");
    }

    static /*ErrorBarType*/int dmlToErrorBarType(String value)
    {
        return (/*ErrorBarType*/int)G_ERROR_BAR_TYPE_MAP.getValue(value, (int)ErrorBarType.BOTH);
    }

    static String errorBarTypeToDml(/*ErrorBarType*/int value)
    {
        return G_ERROR_BAR_TYPE_MAP.getValue((int)value, "");
    }

    static /*ErrorValueType*/int dmlToErrorValueType(String value)
    {
        return (/*ErrorValueType*/int)G_ERROR_VALUE_TYPE_MAP.getValue(value, (int)ErrorValueType.FIXED_VALUE);
    }

    static String errorValueTypeToDml(/*ErrorValueType*/int value)
    {
        return G_ERROR_VALUE_TYPE_MAP.getValue((int)value, "");
    }

    static /*LabelAlignment*/int dmlToLabelAlignment(String value)
    {
        return (/*LabelAlignment*/int)G_LABEL_ALIGNMENT_MAP.getValue(value, (int)LabelAlignment.CENTER);
    }

    static String labelAlignmentToDml(/*LabelAlignment*/int value)
    {
        return G_LABEL_ALIGNMENT_MAP.getValue((int)value, "");
    }

    static /*LayoutMode*/int dmlToLayoutMode(String value)
    {
        return (/*LayoutMode*/int)G_LAYOUT_MODE_MAP.getValue(value, (int)LayoutMode.FACTOR);
    }

    static String layoutModeToDml(/*LayoutMode*/int value)
    {
        return G_LAYOUT_MODE_MAP.getValue((int)value, "");
    }

    static /*LayoutTarget*/int dmlToLayoutTarget(String value)
    {
        return (/*LayoutTarget*/int)G_LAYOUT_TARGET_MAP.getValue(value, (int)LayoutTarget.OUTER);
    }

    static String layoutTargetToDml(/*LayoutTarget*/int value)
    {
        return G_LAYOUT_TARGET_MAP.getValue((int)value, "");
    }

    static /*LegendPosition*/int dmlToLegendPosition(String value)
    {
        return (/*LegendPosition*/int)G_LEGEND_POSITION_MAP.getValue(value, (int)ChartLegend.DEFAULT_POSITION);
    }

    static String legendPositionToDml(/*LegendPosition*/int value)
    {
        return G_LEGEND_POSITION_MAP.getValue((int)value, "");
    }

    static /*MarkerSymbol*/int dmlToMarkerStyle(String value)
    {
        return (/*MarkerSymbol*/int)G_MARKER_STYLE_MAP.getValue(value, (int)MarkerSymbol.DEFAULT);
    }

    static String markerStyleToDml(/*MarkerSymbol*/int value)
    {
        return G_MARKER_STYLE_MAP.getValue((int)value, "");
    }

    static /*OfPieType*/int dmlToOfPieType(String value)
    {
        return (/*OfPieType*/int)G_OF_PIE_TYPE_MAP.getValue(value, (int)OfPieType.PIE);
    }

    static String ofPieTypeToDml(/*OfPieType*/int value)
    {
        return G_OF_PIE_TYPE_MAP.getValue((int)value, "");
    }

    static /*AxisOrientation*/int dmlToAxisOrientation(String value)
    {
        return (/*AxisOrientation*/int)G_ORIENTATION_MAP.getValue(value, (int)AxisOrientation.MIN_MAX);
    }

    static String axisOrientationToDml(/*AxisOrientation*/int value)
    {
        return G_ORIENTATION_MAP.getValue((int)value, "");
    }

    static /*PageSetupOrientation*/int dmlToPageSetupOrientation(String value)
    {
        return (/*PageSetupOrientation*/int)G_PAGE_SETUP_ORIENTATION_MAP.getValue(value, (int)PageSetupOrientation.DEFAULT);
    }

    static String pageSetupOrientationToDml(/*PageSetupOrientation*/int value)
    {
        return G_PAGE_SETUP_ORIENTATION_MAP.getValue((int)value, "");
    }

    static /*PictureFormat*/int dmlToPictureFormat(String value)
    {
        return (/*PictureFormat*/int)G_PICTURE_FORMAT_MAP.getValue(value, (int)PictureFormat.STACK);
    }

    static String pictureFormatToDml(/*PictureFormat*/int value)
    {
        return G_PICTURE_FORMAT_MAP.getValue((int)value, "");
    }

    static /*RadarStyle*/int dmlToRadarStyle(String value)
    {
        return (/*RadarStyle*/int)G_RADAR_STYLE_MAP.getValue(value, (int)RadarStyle.STANDARD);
    }

    static String radarStyleToDml(/*RadarStyle*/int value)
    {
        return G_RADAR_STYLE_MAP.getValue((int)value, "");
    }

    static /*ScatterStyle*/int dmlToScatterStyle(String value)
    {
        return (/*ScatterStyle*/int)G_SCATTER_STYLE.getValue(value, (int)ScatterStyle.MARKER);
    }

    static String scatterStyleToDml(/*ScatterStyle*/int value)
    {
        return G_SCATTER_STYLE.getValue((int)value, "");
    }

    static /*BarShape*/int dmlToShape(String value)
    {
        return (/*BarShape*/int)G_SHAPE_MAP.getValue(value, (int)BarShape.BOX);
    }

    static String shapeToDml(/*BarShape*/int value)
    {
        return G_SHAPE_MAP.getValue((int)value, "");
    }

    static /*SizeRepresents*/int dmlToSizeRepresents(String value)
    {
        return (/*SizeRepresents*/int)G_SIZE_REPRESENTS_MAP.getValue(value, (int)SizeRepresents.AREA);
    }

    static String sizeRepresentsToDml(/*SizeRepresents*/int value)
    {
        return G_SIZE_REPRESENTS_MAP.getValue((int)value, "");
    }

    static /*SplitType*/int dmlToSplitType(String value)
    {
        return (/*SplitType*/int)G_SPLIT_TYPE_MAP.getValue(value, (int)SplitType.AUTO);
    }

    static String splitTypeToDml(/*SplitType*/int value)
    {
        return G_SPLIT_TYPE_MAP.getValue((int)value, "");
    }

    static /*AxisTickLabelPosition*/int dmlToTickLabelPosition(String value)
    {
        return (/*AxisTickLabelPosition*/int)G_TICK_LABEL_POSITION_MAP.getValue(value, (int)AxisTickLabelPosition.NEXT_TO_AXIS);
    }

    static String tickLabelPositionToDml(/*AxisTickLabelPosition*/int value)
    {
        return G_TICK_LABEL_POSITION_MAP.getValue((int)value, "");
    }

    static /*AxisTickMark*/int dmlToTickMark(String value)
    {
        return (/*AxisTickMark*/int)G_TICK_MARK_MAP.getValue(value, (int)AxisTickMark.NONE);
    }

    static String tickMarkToDml(/*AxisTickMark*/int value)
    {
        return G_TICK_MARK_MAP.getValue((int)value, "");
    }

    static /*AxisTimeUnit*/int dmlToTimeUnit(String value)
    {
        return (/*AxisTimeUnit*/int)G_TIME_UNIT_MAP.getValue(value, (int)AxisTimeUnit.DAYS);
    }

    static String timeUnitToDml(/*AxisTimeUnit*/int value)
    {
        return G_TIME_UNIT_MAP.getValue((int)value, "");
    }

    static /*TrendlineType*/int dmlToTrendlineType(String value)
    {
        return (/*TrendlineType*/int)G_TRENDLINE_TYPE_MAP.getValue(value, (int)TrendlineType.LINEAR);
    }

    static String trendlineTypeToDml(/*TrendlineType*/int value)
    {
        return G_TRENDLINE_TYPE_MAP.getValue((int)value, "");
    }

    static /*DimensionType*/int dmlToDimensionType(String value)
    {
        return (/*DimensionType*/int)G_DIMENSION_TYPE_MAP.getValue(value, (int)DimensionType.VALUE);
    }

    static String dimensionTypeToDml(/*DimensionType*/int value)
    {
        return G_DIMENSION_TYPE_MAP.getValue((int)value, "");
    }

    static /*FormulaDirection*/int dmlToFormulaDirection(String value)
    {
        return (/*FormulaDirection*/int)G_FORMULA_DIRECTION_MAP.getValue(value, (int)FormulaDirection.COLUMN);
    }

    static String formulaDirectionToDml(/*FormulaDirection*/int value)
    {
        return G_FORMULA_DIRECTION_MAP.getValue((int)value, "");
    }

    static /*SidePosition*/int dmlToSidePosition(String value, /*SidePosition*/int defaultValue)
    {
        return (/*SidePosition*/int)G_SIDE_POSITION_MAP.getValue(value, (int)defaultValue);
    }

    static String sidePositionToDml(/*SidePosition*/int value)
    {
        return G_SIDE_POSITION_MAP.getValue((int)value, "");
    }

    static /*PositionAlignment*/int dmlToPositionAlignment(String value)
    {
        return (/*PositionAlignment*/int)G_POSITION_ALIGNMENT_MAP.getValue(value, (int)PositionAlignment.CENTER);
    }

    static String positionAlignmentToDml(/*PositionAlignment*/int value)
    {
        return G_POSITION_ALIGNMENT_MAP.getValue((int)value, "");
    }

    static /*SeriesLayout*/int dmlToSeriesLayout(String value)
    {
        return (/*SeriesLayout*/int)G_SERIES_LAYOUT_MAP.getValue(value, (int)SeriesLayout.BOX_WHISKER);
    }

    static String seriesLayoutToDml(/*SeriesLayout*/int value)
    {
        return G_SERIES_LAYOUT_MAP.getValue((int)value, "");
    }

    static /*RegionLabelLayout*/int dmlToRegionLabelLayout(String value)
    {
        return (/*RegionLabelLayout*/int)G_REGION_LABEL_LAYOUT_MAP.getValue(value, (int)RegionLabelLayout.NONE);
    }

    static String regionLabelLayoutToDml(/*RegionLabelLayout*/int value)
    {
        return G_REGION_LABEL_LAYOUT_MAP.getValue((int)value, "");
    }

    static /*ParentLabelLayout*/int dmlToParentLabelLayout(String value)
    {
        return (/*ParentLabelLayout*/int)G_PARENT_LABEL_LAYOUT_MAP.getValue(value, (int)ParentLabelLayout.NONE);
    }

    static String parentLabelLayoutToDml(/*ParentLabelLayout*/int value)
    {
        return G_PARENT_LABEL_LAYOUT_MAP.getValue((int)value, "");
    }

    static /*IntervalClosedSide*/int dmlToIntervalClosedSide(String value)
    {
        return (/*IntervalClosedSide*/int)G_INTERVAL_CLOSED_SIDE_MAP.getValue(value, (int)IntervalClosedSide.LEFT);
    }

    static String intervalClosedSideToDml(/*IntervalClosedSide*/int value)
    {
        return G_INTERVAL_CLOSED_SIDE_MAP.getValue((int)value, "");
    }

    static /*QuartileMethod*/int dmlToQuartileMethod(String value)
    {
        return (/*QuartileMethod*/int)G_QUARTILE_METHOD_MAP.getValue(value, (int)QuartileMethod.INCLUSIVE_MEDIAN);
    }

    static String quartileMethodToDml(/*QuartileMethod*/int value)
    {
        return G_QUARTILE_METHOD_MAP.getValue((int)value, "");
    }

    /// <summary>
    /// Hash tables where key is string and value is the corresponding enum value.
    /// </summary>
    private static /*final*/ StringToIntBidirectionalMap G_AXIS_POSITION_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_BAR_DIRECTION_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_BAR_GROUPING_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_BUILT_IN_UNIT_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_CROSS_BETWEEN_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_CROSSES_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_DATA_LABEL_POSITION_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_DISPLAY_BLANKS_AS_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_ERROR_BAR_DIRECTION_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_ERROR_BAR_TYPE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_ERROR_VALUE_TYPE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_LABEL_ALIGNMENT_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_LAYOUT_MODE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_LAYOUT_TARGET_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_LEGEND_POSITION_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_MARKER_STYLE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_OF_PIE_TYPE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_ORIENTATION_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_PAGE_SETUP_ORIENTATION_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_PICTURE_FORMAT_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_RADAR_STYLE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_SCATTER_STYLE;
    private static /*final*/ StringToIntBidirectionalMap G_SHAPE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_SIZE_REPRESENTS_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_SPLIT_TYPE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_TICK_LABEL_POSITION_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_TICK_MARK_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_TIME_UNIT_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_TRENDLINE_TYPE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_DIMENSION_TYPE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_FORMULA_DIRECTION_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_SIDE_POSITION_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_POSITION_ALIGNMENT_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_SERIES_LAYOUT_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_REGION_LABEL_LAYOUT_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_PARENT_LABEL_LAYOUT_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_INTERVAL_CLOSED_SIDE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_QUARTILE_METHOD_MAP;

    static/* DmlChartsEnum()*/
    {
        G_AXIS_POSITION_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "b", "l", "r", "t" },
            new int[] { (int)AxisPosition.BOTTOM, (int)AxisPosition.LEFT, (int)AxisPosition.RIGHT, (int)AxisPosition.TOP });

        G_BAR_DIRECTION_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "bar", "col" },
            new int[] { (int)BarDirection.BAR, (int)BarDirection.COLUMN });

        G_BAR_GROUPING_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "clustered", "percentStacked", "stacked", "standard" },
            new int[] { (int)Grouping.CLUSTERED, (int)Grouping.PERCENT_STACKED, (int)Grouping.STACKED, (int)Grouping.STANDARD });

        G_BUILT_IN_UNIT_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "billions", "hundredMillions", "hundreds", "hundredThousands",
                "millions", "tenMillions", "tenThousands", "thousands", "trillions", "percentage" },
            new int[] { (int)AxisBuiltInUnit.BILLIONS, (int)AxisBuiltInUnit.HUNDRED_MILLIONS,
                (int)AxisBuiltInUnit.HUNDREDS, (int)AxisBuiltInUnit.HUNDRED_THOUSANDS,
                (int)AxisBuiltInUnit.MILLIONS, (int)AxisBuiltInUnit.TEN_MILLIONS, (int)AxisBuiltInUnit.TEN_THOUSANDS,
                (int)AxisBuiltInUnit.THOUSANDS, (int)AxisBuiltInUnit.TRILLIONS, (int)AxisBuiltInUnit.PERCENTAGE });

        G_CROSS_BETWEEN_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "between", "midCat" },
            new int[] { (int)CrossBetween.BETWEEN, (int)CrossBetween.MIDPOINT_OF_CATEGORY });

        G_CROSSES_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "autoZero", "max", "min" },
            new int[] { (int)AxisCrosses.AUTOMATIC, (int)AxisCrosses.MAXIMUM, (int)AxisCrosses.MINIMUM });

        G_DATA_LABEL_POSITION_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "b", "bestFit", "ctr", "inBase", "inEnd", "l", "outEnd", "r", "t" },
            new int[] { (int)ChartDataLabelPosition.BELOW, (int)ChartDataLabelPosition.BEST_FIT,
                (int)ChartDataLabelPosition.CENTER, (int)ChartDataLabelPosition.INSIDE_BASE,
                (int)ChartDataLabelPosition.INSIDE_END, (int)ChartDataLabelPosition.LEFT,
                (int)ChartDataLabelPosition.OUTSIDE_END, (int)ChartDataLabelPosition.RIGHT,
                (int)ChartDataLabelPosition.ABOVE});

        G_DISPLAY_BLANKS_AS_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "gap", "span", "zero" },
            new int[] { (int)DisplayBlanksAs.GAP, (int)DisplayBlanksAs.SPAN, (int)DisplayBlanksAs.ZERO });

        G_ERROR_BAR_DIRECTION_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "x", "y" },
            new int[] { (int)ErrorBarDirection.X, (int)ErrorBarDirection.Y });

        G_ERROR_BAR_TYPE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "both", "minus", "plus" },
            new int[] { (int)ErrorBarType.BOTH, (int)ErrorBarType.MINUS, (int)ErrorBarType.PLUS });

        G_ERROR_VALUE_TYPE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "cust", "fixedVal", "percentage", "stdDev", "stdErr" },
            new int[] { (int)ErrorValueType.CUSTOM_ERROR_BARS, (int)ErrorValueType.FIXED_VALUE, (int)ErrorValueType.PERCENTAGE,
                (int)ErrorValueType.STANDARD_DEVIATION, (int)ErrorValueType.STANDARD_ERROR });

        G_LABEL_ALIGNMENT_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "ctr", "l", "r" },
            new int[] { (int)LabelAlignment.CENTER, (int)LabelAlignment.LEFT, (int)LabelAlignment.RIGHT });

        G_LAYOUT_MODE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "edge", "factor" },
            new int[] { (int)LayoutMode.EDGE, (int)LayoutMode.FACTOR });

        G_LAYOUT_TARGET_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "inner", "outer" },
            new int[] { (int)LayoutTarget.INNER, (int)LayoutTarget.OUTER });

        G_LEGEND_POSITION_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "b", "l", "r", "t", "tr" },
            new int[] { (int)LegendPosition.BOTTOM, (int)LegendPosition.LEFT, (int)LegendPosition.RIGHT, (int)LegendPosition.TOP, (int)LegendPosition.TOP_RIGHT });

        G_MARKER_STYLE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "circle", "dash", "diamond", "dot", "none", "picture",
                "plus", "square", "star", "triangle", "x" },
            new int[] { (int)MarkerSymbol.CIRCLE, (int)MarkerSymbol.DASH, (int)MarkerSymbol.DIAMOND, (int)MarkerSymbol.DOT, (int)MarkerSymbol.NONE, (int)MarkerSymbol.PICTURE,
                (int)MarkerSymbol.PLUS, (int)MarkerSymbol.SQUARE, (int)MarkerSymbol.STAR, (int)MarkerSymbol.TRIANGLE, (int)MarkerSymbol.X});

        G_OF_PIE_TYPE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "bar", "pie" },
            new int[] { (int)OfPieType.BAR, (int)OfPieType.PIE });

        G_ORIENTATION_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "maxMin", "minMax" },
            new int[] { (int)AxisOrientation.MAX_MIN, (int)AxisOrientation.MIN_MAX });

        G_PAGE_SETUP_ORIENTATION_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "default", "landscape", "portrait" },
            new int[] { (int)PageSetupOrientation.DEFAULT, (int)PageSetupOrientation.LANDSCAPE, (int)PageSetupOrientation.PORTRAIT });

        G_PICTURE_FORMAT_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "stack", "stackScale", "stretch" },
            new int[] { (int)PictureFormat.STACK, (int)PictureFormat.STACK_SCALE, (int)PictureFormat.STRETCH });

        G_RADAR_STYLE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "filled", "marker", "standard" },
            new int[] { (int)RadarStyle.FILLED, (int)RadarStyle.MARKER, (int)RadarStyle.STANDARD });

        G_SCATTER_STYLE = DmlEnumUtil.initHashtableWithValues(
            new String[] { "line", "lineMarker", "marker", "none", "smooth", "smoothMarker" },
            new int[] { (int)ScatterStyle.LINE, (int)ScatterStyle.LINE_MARKER, (int)ScatterStyle.MARKER, (int)ScatterStyle.NONE, (int)ScatterStyle.SMOOTH, (int)ScatterStyle.SMOOTH_MARKER });

        G_SHAPE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "box", "cone", "coneToMax", "cylinder", "pyramid", "pyramidToMax" },
            new int[] { (int)BarShape.BOX, (int)BarShape.CONE, (int)BarShape.CONE_TO_MAX, (int)BarShape.CYLINDER, (int)BarShape.PYRAMID, (int)BarShape.PYRAMID_TO_MAX });

        G_SIZE_REPRESENTS_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "area", "w" },
            new int[] { (int)SizeRepresents.AREA, (int)SizeRepresents.WIDTH });

        G_SPLIT_TYPE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "auto", "cust", "percent", "pos", "val" },
            new int[] { (int)SplitType.AUTO, (int)SplitType.CUSTOM, (int)SplitType.PERCENTAGE, (int)SplitType.POSITION, (int)SplitType.VALUE });

        G_TICK_LABEL_POSITION_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "high", "low", "nextTo", "none" },
            new int[] { (int)AxisTickLabelPosition.HIGH, (int)AxisTickLabelPosition.LOW,
                (int)AxisTickLabelPosition.NEXT_TO_AXIS, (int)AxisTickLabelPosition.NONE });

        G_TICK_MARK_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "cross", "in", "none", "out" },
            new int[] { (int)AxisTickMark.CROSS, (int)AxisTickMark.INSIDE, (int)AxisTickMark.NONE,
                (int)AxisTickMark.OUTSIDE });

        G_TIME_UNIT_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "days", "months", "years" },
            new int[] { (int)AxisTimeUnit.DAYS, (int)AxisTimeUnit.MONTHS, (int)AxisTimeUnit.YEARS });

        G_TRENDLINE_TYPE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "exp", "linear", "log", "movingAvg", "poly", "power" },
            new int[] { (int)TrendlineType.EXPONENTIAL, (int)TrendlineType.LINEAR, (int)TrendlineType.LOGARITHMIC, (int)TrendlineType.MOVING_AVERAGE,
                (int)TrendlineType.POLYNOMIAL, (int)TrendlineType.POWER});

        G_DIMENSION_TYPE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "val", "x", "y", "size", "cat", "colorVal" },
            new int[] { (int)DimensionType.VALUE, (int)DimensionType.X, (int)DimensionType.Y,
                (int)DimensionType.SIZE, (int)DimensionType.CATEGORY, (int)DimensionType.COLOR_VALUE });

        G_FORMULA_DIRECTION_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "col", "row" },
            new int[] { (int)FormulaDirection.COLUMN, (int)FormulaDirection.ROW });

        G_SIDE_POSITION_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "l", "t", "r", "b" },
            new int[] { (int)SidePosition.LEFT, (int)SidePosition.TOP, (int)SidePosition.RIGHT,
                (int)SidePosition.BOTTOM });

        G_POSITION_ALIGNMENT_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "min", "ctr", "max" },
            new int[] { (int)PositionAlignment.MINIMUM, (int)PositionAlignment.CENTER,
                (int)PositionAlignment.MAXIMUM });

        G_SERIES_LAYOUT_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "boxWhisker", "clusteredColumn", "funnel", "paretoLine", "regionMap", "sunburst",
                "treemap", "waterfall" },
            new int[] { (int)SeriesLayout.BOX_WHISKER, (int)SeriesLayout.CLUSTERED_COLUMN, (int)SeriesLayout.FUNNEL,
                (int)SeriesLayout.PARETO_LINE, (int)SeriesLayout.REGION_MAP, (int)SeriesLayout.SUNBURST,
                (int)SeriesLayout.TREEMAP, (int)SeriesLayout.WATERFALL });

        G_REGION_LABEL_LAYOUT_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "none", "bestFitOnly", "showAll" },
            new int[] { (int)RegionLabelLayout.NONE, (int)RegionLabelLayout.BEST_FIT_ONLY,
                (int)RegionLabelLayout.SHOW_ALL });

        G_PARENT_LABEL_LAYOUT_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "none", "banner", "overlapping" },
            new int[] { (int)ParentLabelLayout.NONE, (int)ParentLabelLayout.BANNER,
                (int)ParentLabelLayout.OVERLAPPING });

        G_INTERVAL_CLOSED_SIDE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "l", "r" },
            new int[] { (int)IntervalClosedSide.LEFT, (int)IntervalClosedSide.RIGHT });

        G_QUARTILE_METHOD_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "inclusive", "exclusive" },
            new int[] { (int)QuartileMethod.INCLUSIVE_MEDIAN, (int)QuartileMethod.EXCLUSIVE_MEDIAN });
    }
}

