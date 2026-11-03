// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/07/2016 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// List of chart axis attributes.
/// </summary>
/*enum*/ final class DmlChartAxisAttrs
{
    private DmlChartAxisAttrs(){}
    
    /// <summary>
    /// Id for the axis.
    /// Integer.
    /// </summary>
    public static final int AX_ID = 0;

    /// <summary>
    /// Specifies that this axis is a date or text axis based on the data that is used for the axis labels, not a specific choice.
    /// Boolean.
    /// </summary>
    public static final int IS_AUTO_CATEGORY_TYPE = 1;

    /// <summary>
    /// Specifies whether an axis is a datetime axis (otherwise it is an arbitrary set of categories).
    /// Boolean.
    /// </summary>
    public static final int IS_DATE_CATEGORY_AXIS = 2;

    /// <summary>
    /// Specifies the position of the axis on the chart.
    /// <see cref="AxisPosition"/>.
    /// </summary>
    public static final int AX_POS = 3;

    /// <summary>
    /// Returns or sets the smallest time unit that is represented on the date axis.
    /// <see cref="AxisTimeUnit"/>.
    /// </summary>
    public static final int BASE_TIME_UNIT = 4;

    /// <summary>
    /// Specifies how this axis crosses the perpendicular axis.
    /// </summary>
    public static final int CROSSES = 5;

    /// <summary>
    /// Specifies where on the axis the perpendicular axis crosses. 
    /// </summary>
    public static final int CROSSES_AT = 6;

    /// <summary>
    /// Specifies the ID of axis that this axis crosses.
    /// </summary>
    public static final int CROSS_AX = 7;

    /// <summary>
    /// Specifies whether the value axis crosses the category axis between categories.
    /// </summary>
    public static final int CROSS_BETWEEN = 8;

    /// <summary>
    /// Specifies the scaling value of the display units for the value axis.
    /// <see cref="AxisDisplayUnit"/>.
    /// </summary>
    public static final int DISPLAY_UNIT = 9;

    /// <summary>
    /// Specifies the text alignment for the tick labels on the axis.
    /// <see cref="LabelAlignment"/>.
    /// </summary>
    public static final int LBL_ALGN = 10;

    /// <summary>
    /// Specifies the distance of labels from the axis.
    /// Integer, 0 >= value >= 1000.
    /// </summary>
    public static final int TICK_LABEL_OFFSET = 11;

    /// <summary>
    /// Returns or sets the distance between major ticks.
    /// Valid range is greater then zero.
    /// </summary>
    public static final int MAJOR_UNIT = 12;

    /// <summary>
    /// Returns or sets the time unit for major tick marks.
    /// <see cref="AxisTimeUnit"/>.
    /// </summary>
    public static final int MAJOR_UNIT_SCALE = 13;

    /// <summary>
    /// Returns or sets the distance between minor tick marks.
    /// Valid range is greater then zero.
    /// </summary>
    public static final int MINOR_UNIT = 14;

    /// <summary>
    /// Returns or sets the time unit for the minor tick marks.
    /// <see cref="AxisTimeUnit"/>.
    /// </summary>
    public static final int MINOR_UNIT_SCALE = 15;

    /// <summary>
    /// Returns or sets the major tick marks for the axis.
    /// </summary>
    public static final int MAJOR_TICK_MARK = 16;

    /// <summary>
    /// Returns or sets the minor tick marks for the axis.
    /// </summary>
    public static final int MINOR_TICK_MARK = 17;

    /// <summary>
    /// Specifies major gridlines.
    /// <see cref="DmlChartSpPr"/>.
    /// </summary>
    public static final int MAJOR_GRIDLINES = 18;

    /// <summary>
    /// Specifies the minor gridlines.
    /// <see cref="DmlChartSpPr"/>.
    /// </summary>
    public static final int MINOR_GRIDLINES = 19;

    /// <summary>
    /// Specifies the labels shall be shown as flat text.
    /// Boolean.
    /// </summary>
    public static final int NO_MULTI_LVL_LBL = 20;

    /// <summary>
    /// Specifies number formatting for the parent element.
    /// <see cref="DmlChartNumFormat"/>.
    /// </summary>
    public static final int NUM_FMT = 21;

    /// <summary>
    /// Contains additional axis settings.
    /// <see cref="AxisScaling"/>.
    /// </summary>
    public static final int SCALING = 22;

    /// <summary>
    /// Specifies the formatting for the parent chart element.
    /// <see cref="DmlChartSpPr"/>.
    /// </summary>
    public static final int SP_PR = 23;

    /// <summary>
    /// Specifies the position of the tick labels on the axis.
    /// <see cref="AxisTickLabelPosition"/>.
    /// </summary>
    public static final int TICK_LBL_POS = 24;

    /// <summary>
    /// Specifies the interval at which tick labels are drawn.
    /// Integer, value must be greater than or equal to 1.
    /// </summary>
    public static final int TICK_LABEL_SPACING = 25;

    /// <summary>
    /// Specifies a flag indicating whether automatic interval of drawing tick labels shall be used.
    /// </summary>
    public static final int TICK_LABEL_SPACING_IS_AUTO = 26;

    /// <summary>
    /// Specifies tick labels extensions.
    /// </summary>
    /// <dev>
    /// Supported by charts of the schema http://schemas.microsoft.com/office/drawing/2014/chartex.
    /// </dev>
    public static final int TICK_LBL_EXTENSIONS = 27;

    /// <summary>
    /// Specifies how many tick marks shall be skipped before the next one shall be drawn.
    /// Integer, value must be greater or equals zero.
    /// </summary>
    public static final int TICK_MARK_SPACING = 28;

    /// <summary>
    /// Specifies major tick mark extensions.
    /// </summary>
    /// <dev>
    /// Supported by charts of the schema http://schemas.microsoft.com/office/drawing/2014/chartex.
    /// </dev>
    public static final int MAJOR_TICK_MARK_EXTENSIONS = 29;

    /// <summary>
    /// Specifies minor tick mark extensions.
    /// </summary>
    /// <dev>
    /// Supported by charts of the schema http://schemas.microsoft.com/office/drawing/2014/chartex.
    /// </dev>
    public static final int MINOR_TICK_MARK_EXTENSIONS = 30;

    /// <summary>
    /// Specifies text formatting.
    /// <see cref="DmlChartTxPr"/>.
    /// </summary>
    public static final int TX_PR = 31;

    /// <summary>
    /// Specifies whether this axis is hidden or not.
    /// <see cref="DmlChartTxPr"/>.
    /// </summary>
    /// <dev>
    /// Corresponds to the 21.2.2.40 delete element [ISO/IEC 29500] and to the hidden attribute of the 2.24.3.3
    /// CT_Axis complex type [MS-ODRAWXML].
    /// </dev>
    public static final int HIDDEN = 32;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlChartAxisAttrs value.
    /// </summary>
    public static String getName(/*DmlChartAxisAttrs*/int dmlChartAxisAttrs)
    {
    	switch (dmlChartAxisAttrs)
    	{
    		case AX_ID: return "AX_ID";
    		case IS_AUTO_CATEGORY_TYPE: return "IS_AUTO_CATEGORY_TYPE";
    		case IS_DATE_CATEGORY_AXIS: return "IS_DATE_CATEGORY_AXIS";
    		case AX_POS: return "AX_POS";
    		case BASE_TIME_UNIT: return "BASE_TIME_UNIT";
    		case CROSSES: return "CROSSES";
    		case CROSSES_AT: return "CROSSES_AT";
    		case CROSS_AX: return "CROSS_AX";
    		case CROSS_BETWEEN: return "CROSS_BETWEEN";
    		case DISPLAY_UNIT: return "DISPLAY_UNIT";
    		case LBL_ALGN: return "LBL_ALGN";
    		case TICK_LABEL_OFFSET: return "TICK_LABEL_OFFSET";
    		case MAJOR_UNIT: return "MAJOR_UNIT";
    		case MAJOR_UNIT_SCALE: return "MAJOR_UNIT_SCALE";
    		case MINOR_UNIT: return "MINOR_UNIT";
    		case MINOR_UNIT_SCALE: return "MINOR_UNIT_SCALE";
    		case MAJOR_TICK_MARK: return "MAJOR_TICK_MARK";
    		case MINOR_TICK_MARK: return "MINOR_TICK_MARK";
    		case MAJOR_GRIDLINES: return "MAJOR_GRIDLINES";
    		case MINOR_GRIDLINES: return "MINOR_GRIDLINES";
    		case NO_MULTI_LVL_LBL: return "NO_MULTI_LVL_LBL";
    		case NUM_FMT: return "NUM_FMT";
    		case SCALING: return "SCALING";
    		case SP_PR: return "SP_PR";
    		case TICK_LBL_POS: return "TICK_LBL_POS";
    		case TICK_LABEL_SPACING: return "TICK_LABEL_SPACING";
    		case TICK_LABEL_SPACING_IS_AUTO: return "TICK_LABEL_SPACING_IS_AUTO";
    		case TICK_LBL_EXTENSIONS: return "TICK_LBL_EXTENSIONS";
    		case TICK_MARK_SPACING: return "TICK_MARK_SPACING";
    		case MAJOR_TICK_MARK_EXTENSIONS: return "MAJOR_TICK_MARK_EXTENSIONS";
    		case MINOR_TICK_MARK_EXTENSIONS: return "MINOR_TICK_MARK_EXTENSIONS";
    		case TX_PR: return "TX_PR";
    		case HIDDEN: return "HIDDEN";
    		default: return "Unknown DmlChartAxisAttrs value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlChartAxisAttrs value.
    /// </summary>
    public static String toString(/*DmlChartAxisAttrs*/int dmlChartAxisAttrs)
    {
    	switch (dmlChartAxisAttrs)
    	{
    		case AX_ID: return "AxId";
    		case IS_AUTO_CATEGORY_TYPE: return "IsAutoCategoryType";
    		case IS_DATE_CATEGORY_AXIS: return "IsDateCategoryAxis";
    		case AX_POS: return "AxPos";
    		case BASE_TIME_UNIT: return "BaseTimeUnit";
    		case CROSSES: return "Crosses";
    		case CROSSES_AT: return "CrossesAt";
    		case CROSS_AX: return "CrossAx";
    		case CROSS_BETWEEN: return "CrossBetween";
    		case DISPLAY_UNIT: return "DisplayUnit";
    		case LBL_ALGN: return "LblAlgn";
    		case TICK_LABEL_OFFSET: return "TickLabelOffset";
    		case MAJOR_UNIT: return "MajorUnit";
    		case MAJOR_UNIT_SCALE: return "MajorUnitScale";
    		case MINOR_UNIT: return "MinorUnit";
    		case MINOR_UNIT_SCALE: return "MinorUnitScale";
    		case MAJOR_TICK_MARK: return "MajorTickMark";
    		case MINOR_TICK_MARK: return "MinorTickMark";
    		case MAJOR_GRIDLINES: return "MajorGridlines";
    		case MINOR_GRIDLINES: return "MinorGridlines";
    		case NO_MULTI_LVL_LBL: return "NoMultiLvlLbl";
    		case NUM_FMT: return "NumFmt";
    		case SCALING: return "Scaling";
    		case SP_PR: return "SpPr";
    		case TICK_LBL_POS: return "TickLblPos";
    		case TICK_LABEL_SPACING: return "TickLabelSpacing";
    		case TICK_LABEL_SPACING_IS_AUTO: return "TickLabelSpacingIsAuto";
    		case TICK_LBL_EXTENSIONS: return "TickLblExtensions";
    		case TICK_MARK_SPACING: return "TickMarkSpacing";
    		case MAJOR_TICK_MARK_EXTENSIONS: return "MajorTickMarkExtensions";
    		case MINOR_TICK_MARK_EXTENSIONS: return "MinorTickMarkExtensions";
    		case TX_PR: return "TxPr";
    		case HIDDEN: return "Hidden";
    		default: return "Unknown DmlChartAxisAttrs value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlChartAxisAttrs name.
    /// </summary>
    public static /*DmlChartAxisAttrs*/int fromName(String dmlChartAxisAttrsName)
    {
    	if ("AX_ID".equals(dmlChartAxisAttrsName)) return AX_ID;
    	if ("IS_AUTO_CATEGORY_TYPE".equals(dmlChartAxisAttrsName)) return IS_AUTO_CATEGORY_TYPE;
    	if ("IS_DATE_CATEGORY_AXIS".equals(dmlChartAxisAttrsName)) return IS_DATE_CATEGORY_AXIS;
    	if ("AX_POS".equals(dmlChartAxisAttrsName)) return AX_POS;
    	if ("BASE_TIME_UNIT".equals(dmlChartAxisAttrsName)) return BASE_TIME_UNIT;
    	if ("CROSSES".equals(dmlChartAxisAttrsName)) return CROSSES;
    	if ("CROSSES_AT".equals(dmlChartAxisAttrsName)) return CROSSES_AT;
    	if ("CROSS_AX".equals(dmlChartAxisAttrsName)) return CROSS_AX;
    	if ("CROSS_BETWEEN".equals(dmlChartAxisAttrsName)) return CROSS_BETWEEN;
    	if ("DISPLAY_UNIT".equals(dmlChartAxisAttrsName)) return DISPLAY_UNIT;
    	if ("LBL_ALGN".equals(dmlChartAxisAttrsName)) return LBL_ALGN;
    	if ("TICK_LABEL_OFFSET".equals(dmlChartAxisAttrsName)) return TICK_LABEL_OFFSET;
    	if ("MAJOR_UNIT".equals(dmlChartAxisAttrsName)) return MAJOR_UNIT;
    	if ("MAJOR_UNIT_SCALE".equals(dmlChartAxisAttrsName)) return MAJOR_UNIT_SCALE;
    	if ("MINOR_UNIT".equals(dmlChartAxisAttrsName)) return MINOR_UNIT;
    	if ("MINOR_UNIT_SCALE".equals(dmlChartAxisAttrsName)) return MINOR_UNIT_SCALE;
    	if ("MAJOR_TICK_MARK".equals(dmlChartAxisAttrsName)) return MAJOR_TICK_MARK;
    	if ("MINOR_TICK_MARK".equals(dmlChartAxisAttrsName)) return MINOR_TICK_MARK;
    	if ("MAJOR_GRIDLINES".equals(dmlChartAxisAttrsName)) return MAJOR_GRIDLINES;
    	if ("MINOR_GRIDLINES".equals(dmlChartAxisAttrsName)) return MINOR_GRIDLINES;
    	if ("NO_MULTI_LVL_LBL".equals(dmlChartAxisAttrsName)) return NO_MULTI_LVL_LBL;
    	if ("NUM_FMT".equals(dmlChartAxisAttrsName)) return NUM_FMT;
    	if ("SCALING".equals(dmlChartAxisAttrsName)) return SCALING;
    	if ("SP_PR".equals(dmlChartAxisAttrsName)) return SP_PR;
    	if ("TICK_LBL_POS".equals(dmlChartAxisAttrsName)) return TICK_LBL_POS;
    	if ("TICK_LABEL_SPACING".equals(dmlChartAxisAttrsName)) return TICK_LABEL_SPACING;
    	if ("TICK_LABEL_SPACING_IS_AUTO".equals(dmlChartAxisAttrsName)) return TICK_LABEL_SPACING_IS_AUTO;
    	if ("TICK_LBL_EXTENSIONS".equals(dmlChartAxisAttrsName)) return TICK_LBL_EXTENSIONS;
    	if ("TICK_MARK_SPACING".equals(dmlChartAxisAttrsName)) return TICK_MARK_SPACING;
    	if ("MAJOR_TICK_MARK_EXTENSIONS".equals(dmlChartAxisAttrsName)) return MAJOR_TICK_MARK_EXTENSIONS;
    	if ("MINOR_TICK_MARK_EXTENSIONS".equals(dmlChartAxisAttrsName)) return MINOR_TICK_MARK_EXTENSIONS;
    	if ("TX_PR".equals(dmlChartAxisAttrsName)) return TX_PR;
    	if ("HIDDEN".equals(dmlChartAxisAttrsName)) return HIDDEN;
    	throw new IllegalArgumentException("Unknown DmlChartAxisAttrs name.");
    }
    
    /// <summary>
    /// Returns array of DmlChartAxisAttrs values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AX_ID,
    		IS_AUTO_CATEGORY_TYPE,
    		IS_DATE_CATEGORY_AXIS,
    		AX_POS,
    		BASE_TIME_UNIT,
    		CROSSES,
    		CROSSES_AT,
    		CROSS_AX,
    		CROSS_BETWEEN,
    		DISPLAY_UNIT,
    		LBL_ALGN,
    		TICK_LABEL_OFFSET,
    		MAJOR_UNIT,
    		MAJOR_UNIT_SCALE,
    		MINOR_UNIT,
    		MINOR_UNIT_SCALE,
    		MAJOR_TICK_MARK,
    		MINOR_TICK_MARK,
    		MAJOR_GRIDLINES,
    		MINOR_GRIDLINES,
    		NO_MULTI_LVL_LBL,
    		NUM_FMT,
    		SCALING,
    		SP_PR,
    		TICK_LBL_POS,
    		TICK_LABEL_SPACING,
    		TICK_LABEL_SPACING_IS_AUTO,
    		TICK_LBL_EXTENSIONS,
    		TICK_MARK_SPACING,
    		MAJOR_TICK_MARK_EXTENSIONS,
    		MINOR_TICK_MARK_EXTENSIONS,
    		TX_PR,
    		HIDDEN,
    	};
    }

    public static final int length = 33;
}

