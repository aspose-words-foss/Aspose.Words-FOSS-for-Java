// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/08/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.DateTime;
import com.aspose.MathUtil;
import com.aspose.ms.System.msMath;


/*static*/ class DmlChartUtil
{
	/* Simulation of static class by using private constructor */
	private DmlChartUtil()
	{}

    /// <summary>
    /// Converts double to date, taking into account DateTime.MinValue and DateTime.MaxValue.
    /// </summary>
    /// <param name="value">The specified double</param>
    /// <returns>The converted datetime value</returns>
    static DateTime getDateFromDouble(double value)
    {
        // The minimum date for MS Word is "00.01.1900", internally "0", which is used for time-only values. But this
        // date is not valid, so use "01.01.1900" as a minimum.
        // There is a difference between MS Word and .NET while converting a double to a date. "1" is "01/01/1900" in
        // MS Word and "31/12/1899" in .NET. Also, MS Word considers that "29/02/1900" (internally "60") exists, but
        // .NET doesn't.
        // If the value is more than 60, then there is no difference in conversion.
        double correctedValue = value;

        if (value < WORD_ONE_DAY_DIFFERENCE_NUMERIC_DATE_VALUE)
            correctedValue += (value < WORD_TWO_DAYS_DIFFERENCE_NUMERIC_DATE_VALUE) ? 2 : 1;

        DateTime baseDate;

        if (MathUtil.isLessOrEqual(correctedValue, DateTime.MinValue.toOADate()))
            baseDate = DateTime.MinValue;
        else if (MathUtil.isGreaterOrEqual(correctedValue, DateTime.MaxValue.toOADate()))
            baseDate = DateTime.MaxValue;
        else
            baseDate = getMsWordDate(correctedValue);

        return baseDate;
    }

    /// <summary>
    /// Converts date to double.
    /// </summary>
    static double getDoubleFromDate(DateTime value)
    {
        // The minimum date for MS Word is "00.01.1900", internally "0", which is used for time-only values. But this
        // date is not valid, so use "01.01.1900" as a minimum.
        // There is a difference between MS Word and .NET while converting a double to a date. "1" is "01/01/1900" in
        // MS Word and "31/12/1899" in .NET. Also, MS Word considers that "29/02/1900" (internally "60") exists, but
        // .NET doesn't.
        // If the value is more than 60, then there is no difference in conversion.
        double numericValue = value.toOADate();
        if (numericValue < WORD_ONE_DAY_DIFFERENCE_NUMERIC_DATE_VALUE)
            numericValue -= (numericValue < WORD_TWO_DAYS_DIFFERENCE_NUMERIC_DATE_VALUE + 1) ? 2 : 1;

        return numericValue;
    }

    /// <summary>
    /// Checks whether the specified double value is within the allowed datetime range.
    /// </summary>
    static boolean canBeDate(double value)
    {
        DateTime date = getDateFromDouble(value);

        return (DateTime.greater(date, DateTime.MinValue)) && (DateTime.less(date, DateTime.MaxValue));
    }

    /// <summary>
    /// Indicates whether the current document is created in MS Word 2007 or in an earlier version.
    /// </summary>
    static boolean isMsWord2007OrLower(DocumentBase doc)
    {
        Document document = (ms.as(doc, Document.class));
        if (document == null)
            return true;

        /*MsWordVersionCore*/int version = (/*MsWordVersionCore*/int)(document.getBuiltInDocumentProperties().getVersion() >> 16);

        return (version <= MsWordVersionCore.WORD_2007);
    }

    /// <summary>
    /// Moves the specified X axis to a position opposite the specified position.
    /// </summary>
    static void setXAxisOppositePosition(ChartAxis axis, /*AxisPosition*/int referencedPosition)
    {
        switch (referencedPosition)
        {
            case AxisPosition.BOTTOM:
            {
                if (axis.getActualAxisPosition() == AxisPosition.TOP)
                    return;

                axis.setAxPos(AxisPosition.TOP);
                axis.setCrosses(AxisCrosses.MAXIMUM);
                // Axis title overlaps tick labels without this.
                axis.setCrossBetween(CrossBetween.MIDPOINT_OF_CATEGORY);
                break;
            }
            case AxisPosition.TOP:
            {
                if (axis.getActualAxisPosition() == AxisPosition.BOTTOM)
                    return;

                axis.setAxPos(AxisPosition.BOTTOM);
                axis.setCrosses(AxisCrosses.AUTOMATIC);
                break;
            }
        }
    }

    /// <summary>
    /// Moves the specified Y axis to a position opposite the specified position.
    /// </summary>
    static void setYAxisOppositePosition(ChartAxis axis, /*AxisPosition*/int referencedPosition)
    {
        switch (referencedPosition)
        {
            case AxisPosition.LEFT:
            {
                if (axis.getActualAxisPosition() == AxisPosition.RIGHT)
                    return;

                axis.setAxPos(AxisPosition.RIGHT);
                axis.setCrosses(AxisCrosses.MAXIMUM);
                break;
            }
            case AxisPosition.RIGHT:
            {
                if (axis.getActualAxisPosition() == AxisPosition.LEFT)
                    return;

                axis.setAxPos(AxisPosition.LEFT);
                axis.setCrosses(AxisCrosses.AUTOMATIC);
                break;
            }
        }
    }

    /// <summary>
    /// Gets a corresponding <see cref="ChartType"/> for the specified <see cref="ChartSeriesType"/>.
    /// Throws an exception if the conversion cannot be performed.
    /// </summary>
    static /*ChartType*/int seriesTypeToChartType(/*ChartSeriesType*/int seriesType)
    {
        switch (seriesType)
        {
            case ChartSeriesType.AREA:
                return ChartType.AREA;
            case ChartSeriesType.AREA_STACKED:
                return ChartType.AREA_STACKED;
            case ChartSeriesType.AREA_PERCENT_STACKED:
                return ChartType.AREA_PERCENT_STACKED;
            case ChartSeriesType.AREA_3_D:
                return ChartType.AREA_3_D;
            case ChartSeriesType.AREA_3_D_STACKED:
                return ChartType.AREA_3_D_STACKED;
            case ChartSeriesType.AREA_3_D_PERCENT_STACKED:
                return ChartType.AREA_3_D_PERCENT_STACKED;
            case ChartSeriesType.BAR:
                return ChartType.BAR;
            case ChartSeriesType.BAR_STACKED:
                return ChartType.BAR_STACKED;
            case ChartSeriesType.BAR_PERCENT_STACKED:
                return ChartType.BAR_PERCENT_STACKED;
            case ChartSeriesType.BAR_3_D:
                return ChartType.BAR_3_D;
            case ChartSeriesType.BAR_3_D_STACKED:
                return ChartType.BAR_3_D_STACKED;
            case ChartSeriesType.BAR_3_D_PERCENT_STACKED:
                return ChartType.BAR_3_D_PERCENT_STACKED;
            case ChartSeriesType.BUBBLE:
                return ChartType.BUBBLE;
            case ChartSeriesType.BUBBLE_3_D:
                return ChartType.BUBBLE_3_D;
            case ChartSeriesType.COLUMN:
                return ChartType.COLUMN;
            case ChartSeriesType.COLUMN_STACKED:
                return ChartType.COLUMN_STACKED;
            case ChartSeriesType.COLUMN_PERCENT_STACKED:
                return ChartType.COLUMN_PERCENT_STACKED;
            case ChartSeriesType.COLUMN_3_D:
                return ChartType.COLUMN_3_D;
            case ChartSeriesType.COLUMN_3_D_STACKED:
                return ChartType.COLUMN_3_D_STACKED;
            case ChartSeriesType.COLUMN_3_D_PERCENT_STACKED:
                return ChartType.COLUMN_3_D_PERCENT_STACKED;
            case ChartSeriesType.COLUMN_3_D_CLUSTERED:
                return ChartType.COLUMN_3_D_CLUSTERED;
            case ChartSeriesType.DOUGHNUT:
                return ChartType.DOUGHNUT;
            case ChartSeriesType.LINE:
                return ChartType.LINE;
            case ChartSeriesType.LINE_STACKED:
                return ChartType.LINE_STACKED;
            case ChartSeriesType.LINE_PERCENT_STACKED:
                return ChartType.LINE_PERCENT_STACKED;
            case ChartSeriesType.LINE_3_D:
                return ChartType.LINE_3_D;
            case ChartSeriesType.PIE:
                return ChartType.PIE;
            case ChartSeriesType.PIE_3_D:
                return ChartType.PIE_3_D;
            case ChartSeriesType.PIE_OF_BAR:
                return ChartType.PIE_OF_BAR;
            case ChartSeriesType.PIE_OF_PIE:
                return ChartType.PIE_OF_PIE;
            case ChartSeriesType.RADAR:
                return ChartType.RADAR;
            case ChartSeriesType.SCATTER:
                return ChartType.SCATTER;
            case ChartSeriesType.STOCK:
                return ChartType.STOCK;
            case ChartSeriesType.SURFACE:
                return ChartType.SURFACE;
            case ChartSeriesType.SURFACE_3_D:
                return ChartType.SURFACE_3_D;
            case ChartSeriesType.TREEMAP:
                return ChartType.TREEMAP;
            case ChartSeriesType.SUNBURST:
                return ChartType.SUNBURST;
            case ChartSeriesType.HISTOGRAM:
                return ChartType.HISTOGRAM;
            case ChartSeriesType.PARETO:
            case ChartSeriesType.PARETO_LINE:
                return ChartType.PARETO;
            case ChartSeriesType.BOX_AND_WHISKER:
                return ChartType.BOX_AND_WHISKER;
            case ChartSeriesType.WATERFALL:
                return ChartType.WATERFALL;
            case ChartSeriesType.FUNNEL:
                return ChartType.FUNNEL;
            // There are no corresponding chart types for these series types.
            case ChartSeriesType.REGION_MAP:
            default:
                return UNKNOWN_CHART_TYPE;
        }
    }

    /// <summary>
    /// Gets a value indicating whether the position is supported by data labels in a series of the specified type.
    /// </summary>
    static boolean isDataLabelPositionSupported(/*ChartSeriesType*/int seriesType, /*ChartDataLabelPosition*/int value)
    {
        switch (seriesType)
        {
            case ChartSeriesType.AREA:
            case ChartSeriesType.AREA_STACKED:
            case ChartSeriesType.AREA_PERCENT_STACKED:
            case ChartSeriesType.AREA_3_D:
            case ChartSeriesType.AREA_3_D_STACKED:
            case ChartSeriesType.AREA_3_D_PERCENT_STACKED:
            case ChartSeriesType.BAR_3_D:
            case ChartSeriesType.BAR_3_D_STACKED:
            case ChartSeriesType.BAR_3_D_PERCENT_STACKED:
            case ChartSeriesType.COLUMN_3_D:
            case ChartSeriesType.COLUMN_3_D_STACKED:
            case ChartSeriesType.COLUMN_3_D_PERCENT_STACKED:
            case ChartSeriesType.COLUMN_3_D_CLUSTERED:
            case ChartSeriesType.DOUGHNUT:
            case ChartSeriesType.LINE_3_D:
            case ChartSeriesType.RADAR:
            case ChartSeriesType.SURFACE:
            case ChartSeriesType.SURFACE_3_D:
            case ChartSeriesType.TREEMAP:
            case ChartSeriesType.SUNBURST:
            case ChartSeriesType.PARETO_LINE:
            case ChartSeriesType.FUNNEL:
            case ChartSeriesType.REGION_MAP:
                return false;
            case ChartSeriesType.BAR:
            case ChartSeriesType.COLUMN:
            case ChartSeriesType.HISTOGRAM:
            case ChartSeriesType.PARETO:
            case ChartSeriesType.WATERFALL:
                return
                    (value == ChartDataLabelPosition.CENTER) || (value == ChartDataLabelPosition.INSIDE_BASE) ||
                    (value == ChartDataLabelPosition.INSIDE_END) || (value == ChartDataLabelPosition.OUTSIDE_END);
            case ChartSeriesType.BAR_STACKED:
            case ChartSeriesType.BAR_PERCENT_STACKED:
            case ChartSeriesType.COLUMN_STACKED:
            case ChartSeriesType.COLUMN_PERCENT_STACKED:
                return
                    (value == ChartDataLabelPosition.CENTER) || (value == ChartDataLabelPosition.INSIDE_BASE) ||
                    (value == ChartDataLabelPosition.INSIDE_END);
            case ChartSeriesType.BUBBLE:
            case ChartSeriesType.BUBBLE_3_D:
            case ChartSeriesType.LINE:
            case ChartSeriesType.LINE_STACKED:
            case ChartSeriesType.LINE_PERCENT_STACKED:
            case ChartSeriesType.SCATTER:
            case ChartSeriesType.STOCK:
                return
                    (value == ChartDataLabelPosition.CENTER) || (value == ChartDataLabelPosition.LEFT) ||
                    (value == ChartDataLabelPosition.RIGHT) || (value == ChartDataLabelPosition.ABOVE) ||
                    (value == ChartDataLabelPosition.BELOW);
            case ChartSeriesType.PIE:
            case ChartSeriesType.PIE_3_D:
            case ChartSeriesType.PIE_OF_BAR:
            case ChartSeriesType.PIE_OF_PIE:
                return
                    (value == ChartDataLabelPosition.CENTER) || (value == ChartDataLabelPosition.INSIDE_END) ||
                    (value == ChartDataLabelPosition.OUTSIDE_END) || (value == ChartDataLabelPosition.BEST_FIT);
            case ChartSeriesType.BOX_AND_WHISKER:
                return
                    (value == ChartDataLabelPosition.LEFT) || (value == ChartDataLabelPosition.RIGHT) ||
                    (value == ChartDataLabelPosition.ABOVE) || (value == ChartDataLabelPosition.BELOW);
            default:
                throw new IllegalStateException("Unknown series type.");
        }
    }

    /// <summary>
    /// Gets the default position of data labels in a series of the specified type.
    /// </summary>
    static /*ChartDataLabelPosition*/int getDataLabelDefaultPosition(/*ChartSeriesType*/int seriesType)
    {
        switch (seriesType)
        {
            case ChartSeriesType.AREA:
            case ChartSeriesType.AREA_STACKED:
            case ChartSeriesType.AREA_PERCENT_STACKED:
            case ChartSeriesType.AREA_3_D:
            case ChartSeriesType.AREA_3_D_STACKED:
            case ChartSeriesType.AREA_3_D_PERCENT_STACKED:
            case ChartSeriesType.BAR_3_D:
            case ChartSeriesType.BAR_3_D_STACKED:
            case ChartSeriesType.BAR_3_D_PERCENT_STACKED:
            case ChartSeriesType.COLUMN_3_D:
            case ChartSeriesType.COLUMN_3_D_STACKED:
            case ChartSeriesType.COLUMN_3_D_PERCENT_STACKED:
            case ChartSeriesType.COLUMN_3_D_CLUSTERED:
            case ChartSeriesType.DOUGHNUT:
            case ChartSeriesType.LINE_3_D:
            case ChartSeriesType.RADAR:
            case ChartSeriesType.SURFACE:
            case ChartSeriesType.SURFACE_3_D:
            case ChartSeriesType.TREEMAP:
            case ChartSeriesType.SUNBURST:
            case ChartSeriesType.PARETO_LINE:
            case ChartSeriesType.FUNNEL:
            case ChartSeriesType.REGION_MAP:
                return DmlChartDataLabelPr.DEFAULT_POSITION;
            case ChartSeriesType.BAR:
            case ChartSeriesType.COLUMN:
            case ChartSeriesType.HISTOGRAM:
            case ChartSeriesType.PARETO:
            case ChartSeriesType.WATERFALL:
                return ChartDataLabelPosition.OUTSIDE_END;
            case ChartSeriesType.BAR_STACKED:
            case ChartSeriesType.BAR_PERCENT_STACKED:
            case ChartSeriesType.COLUMN_STACKED:
            case ChartSeriesType.COLUMN_PERCENT_STACKED:
                return ChartDataLabelPosition.CENTER;
            case ChartSeriesType.BUBBLE:
            case ChartSeriesType.BUBBLE_3_D:
            case ChartSeriesType.LINE:
            case ChartSeriesType.LINE_STACKED:
            case ChartSeriesType.LINE_PERCENT_STACKED:
            case ChartSeriesType.SCATTER:
            case ChartSeriesType.STOCK:
                return ChartDataLabelPosition.RIGHT;
            case ChartSeriesType.PIE:
            case ChartSeriesType.PIE_3_D:
            case ChartSeriesType.PIE_OF_BAR:
            case ChartSeriesType.PIE_OF_PIE:
                return ChartDataLabelPosition.BEST_FIT;
            case ChartSeriesType.BOX_AND_WHISKER:
                return ChartDataLabelPosition.RIGHT;
            default:
                throw new IllegalStateException("Unknown series type.");
        }
    }

    /// <summary>
    /// Gets a date based on the double representation of the one second in MS Word.
    /// </summary>
    private static DateTime getMsWordDate(double value)
    {

        double intPart = msMath.round(value);
        double delta = value - intPart;
        DateTime baseDate = DateTime.fromOADate(intPart);

        if (MathUtil.isZero(delta))
            return baseDate;

        // WORDSNET-19731 The numbers after the point must be converted to hours, minutes and seconds. But .Net
        // and MS Word convert the value differently, the minimum unit of time in MS Word is a second, and .Net is a
        // millisecond.
        double seconds = msMath.round(delta / ONE_MS_WORD_SECOND);

        return baseDate.addSeconds(seconds);
    }

    /// <summary>
    /// Gets the minimum date value allowed in MS Excel (01.01.1900).
    /// </summary>
    static /*final*/ DateTime MIN_DATE_VALUE = new DateTime(1900, 1, 1);

    /// <summary>
    /// Double representation of one second in MS Word charts.
    /// </summary>
    /// <remarks>
    /// This is an experimental value.
    /// </remarks>
    private static final double ONE_MS_WORD_SECOND = 1.15740740727512E-05d;

    private static final int WORD_ONE_DAY_DIFFERENCE_NUMERIC_DATE_VALUE = 61;
    private static final int WORD_TWO_DAYS_DIFFERENCE_NUMERIC_DATE_VALUE = 1;

    static final /*ChartType*/int UNKNOWN_CHART_TYPE = (/*ChartType*/int)(-1);

    static final String CHART_EX_UNSUPPORTED_PROPERTY = "The property cannot be set in a Word 2016 chart.";
}

