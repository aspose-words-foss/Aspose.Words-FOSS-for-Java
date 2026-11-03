// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/11/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.TimeSpan;


/// <summary>
/// Contains methods used to provide a public API for getting/modifying chart series data.
/// </summary>
/// <remarks>
/// Also the class implements the <see cref="ISeriesDataStore"/> interface that is used by API classes
/// <see cref="ChartXValueCollection"/>, <see cref="ChartYValueCollection"/> and <see cref="BubbleSizeCollection"/>
/// to get chart data and perform changes in it.
/// </remarks>
class ChartSeriesDataProvider implements ISeriesDataStore
{
    /// <summary>
    /// Creates an instance of this class.
    /// </summary>
    ChartSeriesDataProvider(ChartSeries series)
    {
        mSeries = series;
    }

    /// <summary>
    /// Adds the X value, Y value and bubble size to the end of the series.
    /// </summary>
    void addValue(ChartXValue xValue, ChartYValue yValue, double bubbleSize)
    {
        int index = getValueCount();

        // Set the Count properties of the all internal DmlChartValue collections to ensure that the number of
        // values is the same.
        setValueCount(index + 1);

        if (isXValueSupported())
        {
            mSeries.getXValues().setValue(index, xValue);
            DmlChartValue dmlValue = chartXValueToDmlChartValue(xValue, index);
            getXDmlChartValues().addNullAware(dmlValue, index);
        }

        if (isYValueSupported())
        {
            mSeries.getYValues().setValue(index, yValue);
            DmlChartValue dmlValue = chartYValueToDmlChartValue(yValue, index);
            getYDmlChartValues().addNullAware(dmlValue, index);
        }

        if (isBubbleSizeSupported())
        {
            mSeries.getBubbleSizes().set(index, bubbleSize);
            DmlChartValue dmlValue = bubbleSizeToDmlChartValue(bubbleSize, index);
            getBubbleSizeDmlChartValues().addNullAware(dmlValue, index);
        }
    }

    /// <summary>
    /// Inserts the X value, Y value and bubble size to the series at the specified index.
    /// </summary>
    void insertValue(int index, ChartXValue xValue, ChartYValue yValue, double bubbleSize)
    {
        Debug.assertTrue((index >= 0) && (index <= getValueCount()));

        DataPointInfo[] oldPointInfos = null;
        int oldPointCount = 0;
        if (hasDataPointsOrLabels() && isComplexCorrespondenceBetweenDataPointsAndXValues())
        {
            oldPointInfos = isXValueAffectsNextValueDataPoints()
                ? getDataPointInfoRetriever().getDataPointInfos(index, false)
                : new DataPointInfo[0];
            oldPointCount = getDataPoints().getCount();
        }

        if (isXValueSupported())
        {
            mSeries.getXValues().insert(index, xValue);
            DmlChartValue dmlValue = chartXValueToDmlChartValue(xValue, index);
            getXDmlChartValues().insert(index, dmlValue);
        }

        if (isYValueSupported())
        {
            mSeries.getYValues().insert(index, yValue);
            DmlChartValue dmlValue = chartYValueToDmlChartValue(yValue, index);
            getYDmlChartValues().insert(index, dmlValue);
        }

        if (isBubbleSizeSupported())
        {
            mSeries.getBubbleSizes().insert(index, bubbleSize);
            DmlChartValue dmlValue = bubbleSizeToDmlChartValue(bubbleSize, index);
            getBubbleSizeDmlChartValues().insert(index, dmlValue);
        }

        if (hasDataPointsOrLabels() && !isNoCorrespondenceBetweenDataPointsAndValues())
        {
            if (isComplexCorrespondenceBetweenDataPointsAndXValues())
            {
                DataPointInfo[] newPointInfos = getDataPointInfoRetriever().getDataPointInfos(index, true);
                int newPointCount = getDataPoints().getCount();

                // Make ValueIndex in oldPointInfos compatible with newPointInfos.
                // oldPointInfos contains point infos generated before the insertion for the current 'index + 1' value.
                // 'i' is an index from the end of oldPointInfos and newPointInfos.
                for (int i = 0; i < Math.min(oldPointInfos.length, newPointInfos.length); i++)
                {
                    int newInfoIndex = newPointInfos.length - i - 1;

                    // Process only point infos related to the value after the inserted one. If there are still
                    // non-processed point infos left in oldPointInfos, they now refers to the inserted value.
                    if (newPointInfos[newInfoIndex].getValueIndex() != index + 1)
                        break;

                    int oldInfoIndex = oldPointInfos.length - i - 1;
                    oldPointInfos[oldInfoIndex].setValueIndex(oldPointInfos[oldInfoIndex].getValueIndex() + 1);
                }

                updatePoints(oldPointInfos, oldPointCount, newPointInfos, newPointCount);
            }
            else
            {
                getDataPoints().insert(index);
                getDataLabels().insert(index);
            }
        }

        dataChanged();
    }

    /// <summary>
    /// Removes the X value, Y value and bubble size from the series at the specified index.
    /// </summary>
    void removeValue(int index)
    {
        DataPointInfo[] oldPointInfos = null;
        int oldPointCount = 0;
        if (hasDataPointsOrLabels() && !isNoCorrespondenceBetweenDataPointsAndValues())
        {
            if (isComplexCorrespondenceBetweenDataPointsAndXValues())
            {
                oldPointInfos = getDataPointInfoRetriever().getDataPointInfos(index, true);
                oldPointCount = getDataPoints().getCount();
            }
            else
            {
                getDataPoints().remove(index);
                getDataLabels().remove(index);
            }
        }

        if (isXValueSupported())
        {
            mSeries.getXValues().remove(index);
            getXDmlChartValues().remove(index);
        }

        if (isYValueSupported())
        {
            mSeries.getYValues().remove(index);
            getYDmlChartValues().remove(index);
        }

        if (isBubbleSizeSupported())
        {
            mSeries.getBubbleSizes().remove(index);
            getBubbleSizeDmlChartValues().remove(index);
        }

        if (hasDataPointsOrLabels() && isComplexCorrespondenceBetweenDataPointsAndXValues())
        {
            DataPointInfo[] newPointInfos = isXValueAffectsNextValueDataPoints()
                ? getDataPointInfoRetriever().getDataPointInfos(index, false)
                : new DataPointInfo[0];
            int newPointCount = getDataPoints().getCount();

            // Make ValueIndex in newPointInfos compatible with oldPointInfos.
            // oldPointInfos contains point infos generated before the insertion for the removed and next values.
            // 'i' is an index from the end of oldPointInfos and newPointInfos.
            for (int i = 0; i < Math.min(oldPointInfos.length, newPointInfos.length); i++)
            {
                int oldInfoIndex = oldPointInfos.length - i - 1;

                // Process only point infos related to the value after the removed one. If there are still
                // non-processed point infos left in newInfosIndex, they have become irrelevant after the value
                // removal, and the corresponding data points/labels should be removed.
                if (oldPointInfos[oldInfoIndex].getValueIndex() != index + 1)
                    break;

                int newInfoIndex = newPointInfos.length - i - 1;
                newPointInfos[newInfoIndex].setValueIndex(newPointInfos[newInfoIndex].getValueIndex() + 1);
            }

            updatePoints(oldPointInfos, oldPointCount, newPointInfos, newPointCount);
        }

        dataChanged();
    }

    /// <summary>
    /// Removes all data values from the series.
    /// </summary>
    void removeAllValues()
    {
        if (mSeries.getChartExChartData() != null)
        {
            for (DmlChartDataSource dataSource : mSeries.getChartExChartData().getDataSources())
                dataSource.getValues().clear();
        }

        if (isXValueSupported())
            mSeries.getXValues().clear();
        if (getXDmlChartValues() != null)
            getXDmlChartValues().clear();

        if (isYValueSupported())
            mSeries.getYValues().clear();
        if (getYDmlChartValues() != null)
            getYDmlChartValues().clear();

        if (isBubbleSizeSupported())
            mSeries.getBubbleSizes().clear();
        if (getBubbleSizeDmlChartValues() != null)
            getBubbleSizeDmlChartValues().clear();

        dataChanged();
    }

    /// <summary>
    /// Converts the specified X value to an instance of a <see cref="DmlChartValue"/> descendant.
    /// </summary>
    private DmlChartValue chartXValueToDmlChartValue(ChartXValue value, int index)
    {
        if (value == null)
            return null;

        switch (value.getValueType())
        {
            case ChartXValueType.STRING:
            {
                switch (getXDmlChartValues().getValueType())
                {
                    case DmlChartValueType.STRING:
                        return new DmlChartStrValue(index, value.getStringValue());
                    case DmlChartValueType.MULTI_LVL_STRING:
                    {
                        DmlChartMultiLvlStrValue multiLevelValue = new DmlChartMultiLvlStrValue(index);
                        multiLevelValue.addLevelValue(value.getStringValue(), 0);
                        return multiLevelValue;
                    }
                    default:
                        Debug.assertTrue(false);
                        return null;
                }
            }
            case ChartXValueType.MULTILEVEL:
            {
                Debug.assertTrue(getXDmlChartValues().getValueType() == DmlChartValueType.MULTI_LVL_STRING);
                DmlChartMultiLvlStrValue multiLevelValue = new DmlChartMultiLvlStrValue(index);
                ChartMultilevelValue multilevel = value.getMultilevelValue();

                // Levels in DmlChartMultiLvlStrValue are in the reverse order.
                // If the destination DmlChartValueCollection and the creating value have different level numbers,
                // this is resolved in DmlChartValueCollection.AddPointNullAware.

                int level = 0;
                if (multilevel.getLevel3() != null)
                {
                    multiLevelValue.addLevelValue(multilevel.getLevel3(), level);
                    level++;
                }

                if ((multilevel.getLevel2() != null) || (level > 0))
                {
                    multiLevelValue.addLevelValue(multilevel.getLevel2(), level);
                    level++;
                }

                multiLevelValue.addLevelValue(multilevel.getLevel1(), level);

                return multiLevelValue;
            }
            case ChartXValueType.DOUBLE:
                return createNumberBasedChartValue(index, value.getDoubleValue(), getXDmlChartValues());
            case ChartXValueType.DATE_TIME:
                double wordDateTime = DmlChartUtil.getDoubleFromDate(value.getDateTimeValueInternal());
                return createNumberBasedChartValue(index, wordDateTime, getXDmlChartValues());
            case ChartXValueType.TIME:
                double numericTime = value.getTimeValueInternal().getTotalDays();
                return createNumberBasedChartValue(index, numericTime, getXDmlChartValues());
            default:
                Debug.assertTrue(false);
                return null;
        }
    }

    /// <summary>
    /// Creates an instance of a <see cref="DmlChartValue"/> descendant for the specified numeric value.
    /// </summary>
    private static DmlChartValue createNumberBasedChartValue(int index, double value,
        DmlChartValueCollection collection)
    {
        switch (collection.getValueType())
        {
            case DmlChartValueType.NUMERIC:
                return new DmlChartNumValue(index, value);
            case DmlChartValueType.MULTI_LVL_NUMERIC:
            {
                DmlChartMultiLvlNumValue multiLevelValue =
                    new DmlChartMultiLvlNumValue(index, collection.getLevelProperties());
                multiLevelValue.addLevelValue(value, 0);
                return multiLevelValue;
            }
            default:
                Debug.assertTrue(false);
                return null;
        }
    }

    /// <summary>
    /// Converts the specified Y value to an instance of a <see cref="DmlChartValue"/> descendant.
    /// </summary>
    private DmlChartValue chartYValueToDmlChartValue(ChartYValue value, int index)
    {
        if (value == null)
            return null;

        double numericValue;
        switch (value.getValueType())
        {
            case ChartYValueType.DOUBLE:
                numericValue = value.getDoubleValue();
                break;
            case ChartYValueType.DATE_TIME:
                numericValue = DmlChartUtil.getDoubleFromDate(value.getDateTimeValueInternal());
                break;
            case ChartYValueType.TIME:
                numericValue = value.getTimeValueInternal().getTotalDays();
                break;
            default:
                Debug.assertTrue(false);
                return null;
        }

        return createNumberBasedChartValue(index, numericValue, getYDmlChartValues());
    }

    /// <summary>
    /// Converts the specified bubble size value to an instance of a <see cref="DmlChartValue"/> descendant.
    /// </summary>
    private DmlChartValue bubbleSizeToDmlChartValue(double value, int index)
    {
        return !isNullBubbleSize(value)
            ? (DmlChartValue)new DmlChartNumValue(index, value, getBubbleSizeDmlChartValues().getFormatCode())
            : null;
    }

    /// <summary>
    /// Returns a flag indicating whether the specified bubble size value represents an empty value.
    /// </summary>
    private static boolean isNullBubbleSize(double value)
    {
        return Double.isNaN(value);
    }

    /// <summary>
    /// Updates data points and labels based on data point infos before and after the data change.
    /// </summary>
    private void updatePoints(DataPointInfo[] oldPointInfos, int oldPointCount,
        DataPointInfo[] newPointInfos, int newPointCount)
    {
        // For any of the change of the X value collection of a Treemap or Sunburst charts, i.e. removing/inserting/
        // updating a value, except the data points/labels of the affected X value itself, it is needed to check the
        // data points/labels of the next X value, because there may be added/removed data points/labels related to
        // it. For example, when an X value is being removed, its data points/labels for branch/stem/leaf are needed
        // to be removed too. And if branch and stem of the next X value equal to branch and stem of the previous X
        // value, but are different than branch and stem of the removing X value, data points/labels related to branch
        // and stem of the next X value must be removed too.

        Debug.assertTrue((oldPointInfos != null) && (newPointInfos != null));

        // It is expected that infos are ordered by DataPointIndex. The indexes of the contained data points are
        // continuous except for a Box&Whisker chart.
        for (int i = 1; i < oldPointInfos.length; i++)
            Debug.assertTrue(oldPointInfos[i - 1].getIndex() < oldPointInfos[i].getIndex());
        for (int i = 1; i < newPointInfos.length; i++)
            Debug.assertTrue(newPointInfos[i - 1].getIndex() < newPointInfos[i].getIndex());

        // Preserve data points/labels if possible.

        // Check from the end at first because the oldPointInfos/newPointInfos arrays may contain data points for
        // the non-affected next data value.
        for (int i = 0; i < Math.min(oldPointInfos.length, newPointInfos.length); i++)
        {
            int oldPointInfoIndex = oldPointInfos.length - i - 1;
            DataPointInfo oldPointInfo = oldPointInfos[oldPointInfoIndex];
            int newPointInfoIndex = newPointInfos.length - i - 1;
            DataPointInfo newPointInfo = newPointInfos[newPointInfoIndex];

            if ((oldPointInfo.getValueIndex() != newPointInfo.getValueIndex()) ||
                (oldPointInfo.getValueLevel() != newPointInfo.getValueLevel()))
            {
                break;
            }

            int oldPointIndexFromEnd = oldPointCount - oldPointInfo.getIndex() - 1;
            int newPointIndexFromEnd = newPointCount - newPointInfo.getIndex() - 1;
            if (oldPointIndexFromEnd != newPointIndexFromEnd)
                break;

            // Preserve this data point and label.
            oldPointInfos[oldPointInfoIndex] = null;
            newPointInfos[newPointInfoIndex] = null;
        }

        // Check from the beginning.
        for (int i = 0; i < Math.min(oldPointInfos.length, newPointInfos.length); i++)
        {
            DataPointInfo oldPointInfo = oldPointInfos[i];
            DataPointInfo newPointInfo = newPointInfos[i];
            if ((oldPointInfo == null) || (newPointInfo == null))
                break;

            if ((oldPointInfo.getIndex() != newPointInfo.getIndex()) ||
                (oldPointInfo.getValueIndex() != newPointInfo.getValueIndex()) ||
                (oldPointInfo.getValueLevel() != newPointInfo.getValueLevel()))
            {
                break;
            }

            // Preserve this data point and label.
            oldPointInfos[i] = null;
            newPointInfos[i] = null;
        }

        // Remove data points/labels.
        for (int i = oldPointInfos.length - 1; i >= 0; i--)
        {
            DataPointInfo info = oldPointInfos[i];
            if ((info == null) || info.isPointReferencedByOtherValues())
                continue; // The point/label should be preserved.

            getDataPoints().remove(info.getIndex());
            getDataLabels().remove(info.getIndex());
        }

        // Insert data points/labels.
        for (DataPointInfo info : newPointInfos)
        {
            // IsPointReferencedByOtherValues means that the data point existed before the change.
            if ((info == null) || info.isPointReferencedByOtherValues())
                continue; // Data point/label already exists: no need to insert.

            getDataPoints().insert(info.getIndex());
            getDataLabels().insert(info.getIndex());
        }
    }

    /// <summary>
    /// Updates other chart structures to be actual after changing the chart data. Removes the embedded XLSX file
    /// that stores the chart data, and marks the data as literal. Removes the fallback image shape.
    /// </summary>
    private void dataChanged()
    {
        // Box&Whisker chart may have unknown 'size' dimension. There was an issues, that after changing data in
        // AW, and then opening the document in MS Word, if this dimension is preserved, after selecting Edit Data,
        // MS Word copies some data range from it to the Y values dimension. Remove it.
        if (isChartExChart() && (getLayoutType() == SeriesLayout.BOX_WHISKER))
            mSeries.getChartExChartData().removeDataSource(DimensionType.SIZE);

        // Remove a fallback image shape as it has become non-actual after data was changed. The fallback will be
        // regenerated in DmlShapeValidator.ProcessFallback.
        RunPr shapeRunPr = getChart().getChartSpace().getDml().getRunPr();
        if (shapeRunPr.getAlternateContent() != null)
        {
            // For Word 2016 charts, let's remove the alternate content at all because the renderer throws exceptions
            // when draws such charts. TODO: When the renderer will support drawing these chart types, just remove
            // the fallback like for the older chart types so that it will be regenerated in the shape validator.
            if (isChartExChart())
                shapeRunPr.remove(FontAttr.ALTERNATE_CONTENT);
            else
                shapeRunPr.getAlternateContent().setFallBack(null);
        }

        // Need to mark the collections as literal data to remove XLSX formulas, otherwise MS Word may generate
        // wrong data when building the XLSX document.

        if (isXValueSupported() && (mSeries.getX().getDataSource().getValueRef() != null))
            mSeries.getX().getDataSource().getValueRef().markAsLiteralData();

        if (isYValueSupported() && (mSeries.getY().getDataSource().getValueRef() != null))
            mSeries.getY().getDataSource().getValueRef().markAsLiteralData();

        if (isBubbleSizeSupported() && (mSeries.getSize().getDataSource().getValueRef() != null))
            mSeries.getSize().getDataSource().getValueRef().markAsLiteralData();

        if (mSeries.getTx() != null && mSeries.getTx().getStrRef() != null)
            mSeries.getTx().getStrRef().markAsLiteralData();

        // After changing series data, an existing embedded/linked XLSX document does not contain the changes, we
        // need to remove it, otherwise when clicking Edit Data in MS Word, it restores the old data from XLSX.
        if (getChart().getChartSpace().hasExternalData())
        {
            getChart().getChartSpace().removeExternalDataLinkage();

            // Removed XLSX document might contain some formatting or additional data: generate a warning.
            getChart().getDocument().warn(WarningType.DATA_LOSS, WarningSource.DRAWING_ML, WarningStrings.CHART_DATA_REGENERATION);
        }
    }

    /// <summary>
    /// Gets a flag indicating whether the specified format code is a datetime format. Returns <b>false</b> for a
    /// time-only format.
    /// </summary>
    private static boolean isDateTimeFormat(String formatCode)
    {
        return
            DmlChartFormatCodeValidator.isDateFormat(formatCode) &&
            !DmlChartFormatCodeValidator.isTimeFormat(formatCode);
    }

    /// <summary>
    /// Gets a flag indicating whether the specified format code is a numeric format.
    /// </summary>
    private static boolean isNumericFormat(String formatCode)
    {
        // Let's just check that the format is not a date or time.
        return
            StringUtil.hasChars(formatCode) &&
            !DmlChartFormatCodeValidator.isDateFormat(formatCode);
    }

    /// <summary>
    /// Gets a flag indicating whether the specified X value type is supported by the series.
    /// </summary>
    private boolean isXValueTypeSupported(/*ChartXValueType*/int value)
    {
        if (!isChartExChart())
        {
            // Although string and multilevel values are not displayed and replaced with numbers in Bubble and
            // Scatter charts, but the SeriesCollection.Add method can be used to create series with string values
            // in these chart types: so let's allow the all value types in non-Word 2016 charts.
            return true;
        }

        switch (getLayoutType())
        {
            // Looks like MS Word creates only string dimensions for X values of these chart types and numeric
            // values may be not displayed.
            case SeriesLayout.BOX_WHISKER:
            case SeriesLayout.FUNNEL:
            case SeriesLayout.WATERFALL:
            case SeriesLayout.REGION_MAP:
                return (value == ChartXValueType.STRING);
            case SeriesLayout.TREEMAP:
            case SeriesLayout.SUNBURST:
                return (value == ChartXValueType.MULTILEVEL);
            case SeriesLayout.CLUSTERED_COLUMN:
            {
                if (mSeries.getLayoutPr().isAggregation())
                {
                    // Pareto chart
                    return (value == ChartXValueType.STRING);
                }
                else
                {
                    // Histogram chart
                    return (value != ChartXValueType.STRING) && (value != ChartXValueType.MULTILEVEL);
                }
            }
            case SeriesLayout.PARETO_LINE:
                return false; // Pareto line cannot has data.
            default:
                Debug.assertTrue(false);
                return false;
        }
    }

    /// <summary>
    /// Sets the category type of the specified axis to <see cref="AxisCategoryType.Automatic"/> and resets
    /// the date category flag.
    /// </summary>
    private static void setAutomaticAxisCategoryType(ChartAxis axis)
    {
        // Set using attributes because ChartAxis.CategoryType.set does not change IsDateCategoryAxis when
        // specifying the AxisCategoryType.Automatic value.
        axis.getChartAxisPr().setProperty(DmlChartAxisAttrs.IS_AUTO_CATEGORY_TYPE, true);
        axis.getChartAxisPr().setProperty(DmlChartAxisAttrs.IS_DATE_CATEGORY_AXIS, false);
    }

    /// <summary>
    /// Adjusts the format codes according to the specified X value type.
    /// </summary>
    private void adjustXFormatCodes(/*ChartXValueType*/int xValueType)
    {
        IDmlChart2D chart2d = (ms.as(getChart(), IDmlChart2D.class));
        ChartAxis axis = (chart2d != null) ? chart2d.getAxX() : null;

        switch (xValueType)
        {
            case ChartXValueType.STRING:
            case ChartXValueType.MULTILEVEL:
            {
                getXDmlChartValues().setFormatCode(null);

                if (axis != null)
                {
                    setAutomaticAxisCategoryType(axis);
                    axis.getNumberFormat().setFormatCode(ChartNumberFormat.GENERAL_FORMAT_CODE);
                    axis.getNumberFormat().isLinkedToSource(true);
                }

                break;
            }
            case ChartXValueType.DOUBLE:
            {
                // Do not change current format if it is already numeric.
                if (!isNumericFormat(getXDmlChartValues().getFormatCode()))
                    getXDmlChartValues().setFormatCode(ChartNumberFormat.GENERAL_FORMAT_CODE);

                if (axis != null)
                {
                    setAutomaticAxisCategoryType(axis);

                    // Do not change current format if it is already numeric.
                    if (!isNumericFormat(axis.getNumberFormat().getFormatCode()))
                        axis.getNumberFormat().setFormatCode(ChartNumberFormat.GENERAL_FORMAT_CODE);
                }

                break;
            }
            case ChartXValueType.DATE_TIME:
            {
                // Do not change current format if it is already DateTime.
                if (!isDateTimeFormat(getXDmlChartValues().getFormatCode()))
                    getXDmlChartValues().setFormatCode(ChartNumberFormat.DEFAULT_DATE_FORMAT_CODE);

                if (axis != null)
                {
                    axis.setCategoryType(AxisCategoryType.TIME);

                    // Do not change current format if it is already DateTime.
                    if (!isDateTimeFormat(axis.getNumberFormat().getFormatCode()))
                        axis.getNumberFormat().setFormatCode(ChartNumberFormat.DEFAULT_DATE_FORMAT_CODE);
                }

                break;
            }
            case ChartXValueType.TIME:
            {
                // Do not change current format if it is already Time.
                if (!DmlChartFormatCodeValidator.isTimeFormat(getXDmlChartValues().getFormatCode()))
                    getXDmlChartValues().setFormatCode(ChartNumberFormat.DEFAULT_TIME_FORMAT_CODE);

                if (axis != null)
                {
                    // Need to set the Automatic category type, not Time, otherwise MS Word does not display data.
                    axis.setCategoryType(AxisCategoryType.AUTOMATIC);

                    // Do not change current format if it is already DateTime.
                    if (!DmlChartFormatCodeValidator.isTimeFormat(axis.getNumberFormat().getFormatCode()))
                        axis.getNumberFormat().setFormatCode(ChartNumberFormat.DEFAULT_TIME_FORMAT_CODE);
                }

                break;
            }
            default:
                Debug.assertTrue(false);
                break;
        }

    }

    /// <summary>
    /// Adjusts the format codes according to the specified Y value type.
    /// </summary>
    private void adjustYFormatCodes(/*ChartYValueType*/int yValueType)
    {
        IDmlChart2D chart2d = (ms.as(getChart(), IDmlChart2D.class));
        ChartAxis axis = (chart2d != null) ? chart2d.getAxY() : null;

        switch (yValueType)
        {
            case ChartYValueType.DOUBLE:
            {
                // Do not change current format if it is already numeric.
                if (DmlChartFormatCodeValidator.isDateFormat(getYDmlChartValues().getFormatCode()))
                    getYDmlChartValues().setFormatCode(ChartNumberFormat.GENERAL_FORMAT_CODE);

                if (mSeries.hasDataLabels() &&
                    getDataLabels().getShowValue() &&
                    DmlChartFormatCodeValidator.isDateFormat(getDataLabels().getNumberFormat().getFormatCode()))
                {
                    getDataLabels().getNumberFormat().setFormatCode(ChartNumberFormat.GENERAL_FORMAT_CODE);
                }

                if (axis != null)
                {
                    setAutomaticAxisCategoryType(axis);

                    // Do not change current format if it is already numeric.
                    if (DmlChartFormatCodeValidator.isDateFormat(axis.getNumberFormat().getFormatCode()))
                        axis.getNumberFormat().setFormatCode(ChartNumberFormat.GENERAL_FORMAT_CODE);
                }

                break;
            }
            case ChartYValueType.DATE_TIME:
            {
                // Do not change current format if it is already DateTime.
                if (!isDateTimeFormat(getYDmlChartValues().getFormatCode()))
                    getYDmlChartValues().setFormatCode(ChartNumberFormat.DEFAULT_DATE_FORMAT_CODE);

                if (mSeries.hasDataLabels() &&
                    getDataLabels().getShowValue() &&
                    !isDateTimeFormat(getDataLabels().getNumberFormat().getFormatCode()))
                {
                    getDataLabels().getNumberFormat().setFormatCode(ChartNumberFormat.DEFAULT_DATE_FORMAT_CODE);
                }

                if ((axis != null) && !getChart().isPercentStacked())
                {
                    axis.setCategoryType(AxisCategoryType.TIME);

                    // Do not change current format if it is already DateTime.
                    if (!isDateTimeFormat(axis.getNumberFormat().getFormatCode()))
                        axis.getNumberFormat().setFormatCode(ChartNumberFormat.DEFAULT_DATE_FORMAT_CODE);
                }

                break;
            }
            case ChartYValueType.TIME:
            {
                // Do not change current format if it is already Time.
                if (!DmlChartFormatCodeValidator.isTimeFormat(getYDmlChartValues().getFormatCode()))
                    getYDmlChartValues().setFormatCode(ChartNumberFormat.DEFAULT_TIME_FORMAT_CODE);

                if (mSeries.hasDataLabels() &&
                    getDataLabels().getShowValue() &&
                    !DmlChartFormatCodeValidator.isTimeFormat(getDataLabels().getNumberFormat().getFormatCode()))
                {
                    getDataLabels().getNumberFormat().setFormatCode(ChartNumberFormat.DEFAULT_TIME_FORMAT_CODE);
                }

                if ((axis != null) && !getChart().isPercentStacked())
                {
                    // Need to set the Automatic category type, not Time, otherwise MS Word does not display data.
                    axis.setCategoryType(AxisCategoryType.AUTOMATIC);

                    // Do not change current format if it is already Time.
                    if (!DmlChartFormatCodeValidator.isTimeFormat(axis.getNumberFormat().getFormatCode()))
                        axis.getNumberFormat().setFormatCode(ChartNumberFormat.DEFAULT_TIME_FORMAT_CODE);
                }

                break;
            }
            default:
                Debug.assertTrue(false);
                break;
        }
    }

    /// <summary>
    /// Gets format code of the specified dimension data.
    /// </summary>
    private static String getFormatCode(DmlChartDimensionData dimensionData)
    {
        String formatCode = dimensionData.getValues().getFormatCode();
        if (StringUtil.hasChars(formatCode))
            return formatCode;

        DmlChartValue chartValue = dimensionData.getValues().getFirstNotNullValue();

        DmlChartNumValue chartNumValue = ms.as(chartValue, DmlChartNumValue.class);
        if (chartNumValue != null)
            return chartNumValue.getFormatCode();

        DmlChartMultiLvlNumValue chartMultilevelValue = ms.as(chartValue, DmlChartMultiLvlNumValue.class);
        if (chartMultilevelValue != null)
            return chartMultilevelValue.getFormatCode(0);

        return null;
    }

    /// <summary>
    /// Gets a X value type that corresponds to the specified format code.
    /// </summary>
    private static /*ChartXValueType*/int getXValueType(String formatCode)
    {
        boolean isDateTime =
            StringUtil.hasChars(formatCode) &&
            DmlChartFormatCodeValidator.isDateFormat(formatCode);

        if (!isDateTime)
            return ChartXValueType.DOUBLE;

        return DmlChartFormatCodeValidator.isTimeFormat(formatCode)
            ? ChartXValueType.TIME
            : ChartXValueType.DATE_TIME;
    }

    /// <summary>
    /// Gets a Y value type that corresponds to the specified format code.
    /// </summary>
    private static /*ChartYValueType*/int getYValueType(String formatCode)
    {
        boolean isDateTime =
            StringUtil.hasChars(formatCode) &&
            DmlChartFormatCodeValidator.isDateFormat(formatCode);

        if (!isDateTime)
            return ChartYValueType.DOUBLE;

        return DmlChartFormatCodeValidator.isTimeFormat(formatCode)
            ? ChartYValueType.TIME
            : ChartYValueType.DATE_TIME;
    }

    //>>>>>>>> #region  ISeriesDataStore

    public ChartXValue /*ISeriesDataStore.*/getXValue(int index, /*ChartXValueType*/int valueType)
    {
        DmlChartValue dmlValue = getXDmlChartValues().get(index);
        if (DmlChartValue.isNullOrNone(dmlValue))
            return null;

        switch (valueType)
        {
            case ChartXValueType.STRING:
            {
                switch (dmlValue.getValueType())
                {
                    case DmlChartValueType.STRING:
                        return ChartXValue.fromString(dmlValue.getStringValue());
                    case DmlChartValueType.MULTI_LVL_STRING:
                    {
                        DmlChartMultiLvlStrValue multiLevelValue = (DmlChartMultiLvlStrValue)dmlValue;
                        Debug.assertTrue(multiLevelValue.getLevelsCount() == 1);
                        return ChartXValue.fromString((String)multiLevelValue.getLevels().get(0));
                    }
                    default:
                        Debug.assertTrue(false);
                        return null;
                }
            }
            case ChartXValueType.MULTILEVEL:
            {
                Debug.assertTrue(dmlValue.getValueType() == DmlChartValueType.MULTI_LVL_STRING);
                DmlChartMultiLvlStrValue value = (DmlChartMultiLvlStrValue)dmlValue;
                return ChartXValue.fromMultilevelValue(new ChartMultilevelValue(
                    value.getLevelTextInReverseOrder(0),
                    value.getLevelTextInReverseOrder(1),
                    value.getLevelTextInReverseOrder(2)));
            }
            case ChartXValueType.DOUBLE:
            {
                Debug.assertTrue(
                    (dmlValue.getValueType() == DmlChartValueType.NUMERIC) ||
                    (dmlValue.getValueType() == DmlChartValueType.MULTI_LVL_NUMERIC));
                return ChartXValue.fromDouble(dmlValue.getValue());
            }
            case ChartXValueType.DATE_TIME:
            {
                Debug.assertTrue((dmlValue.getValueType() == DmlChartValueType.NUMERIC) && dmlValue.isDate());
                DateTime dateTime = DmlChartUtil.getDateFromDouble(dmlValue.getValue());
                return ChartXValue.fromDateTimeInternal(dateTime);
            }
            case ChartXValueType.TIME:
            {
                Debug.assertTrue((dmlValue.getValueType() == DmlChartValueType.NUMERIC) && dmlValue.isDate());
                TimeSpan timeSpan = TimeSpan.fromDays(dmlValue.getValue());
                return ChartXValue.fromTimeSpanInternal(timeSpan);
            }
            default:
                Debug.assertTrue(false);
                return null;
        }
    }

    public ChartYValue /*ISeriesDataStore.*/getYValue(int index, /*ChartYValueType*/int valueType)
    {
        DmlChartValue dmlValue = getYDmlChartValues().get(index);
        if (DmlChartValue.isNullOrNone(dmlValue))
            return null;

        Debug.assertTrue(
            (dmlValue.getValueType() == DmlChartValueType.NUMERIC) ||
            ((dmlValue.getValueType() == DmlChartValueType.MULTI_LVL_NUMERIC) && (dmlValue.getLevelsCount() == 1)));

        switch (valueType)
        {
            case ChartYValueType.DOUBLE:
                return ChartYValue.fromDouble(dmlValue.getValue());
            case ChartYValueType.DATE_TIME:
            {
                Debug.assertTrue(getYDmlChartValues().isDate() || dmlValue.isDate());
                DateTime dateTime = DmlChartUtil.getDateFromDouble(dmlValue.getValue());
                return ChartYValue.fromDateTimeInternal(dateTime);
            }
            case ChartYValueType.TIME:
            {
                Debug.assertTrue(getYDmlChartValues().isDate() || dmlValue.isDate());
                TimeSpan timeSpan = TimeSpan.fromDays(dmlValue.getValue());
                return ChartYValue.fromTimeSpanInternal(timeSpan);
            }
            default:
                Debug.assertTrue(false);
                return null;
        }
    }

    public double /*ISeriesDataStore.*/getBubbleSize(int index)
    {
        DmlChartValue dmlValue = getBubbleSizeDmlChartValues().get(index);
        return !DmlChartValue.isNullOrNone(dmlValue)
            ? dmlValue.getValue()
            : NULL_BUBBLE_SIZE;
    }

    /// <summary>
    /// Changes the value in the <see cref="XDmlChartValues"/> collection at the specified index.
    /// </summary>
    public void /*ISeriesDataStore.*/changeXValue(ChartXValue xValue, int index)
    {
        DataPointInfo[] oldPointInfos = null;
        int oldPointCount = 0;
        if (hasDataPointsOrLabels() && isComplexCorrespondenceBetweenDataPointsAndXValues())
        {
            oldPointInfos = getDataPointInfoRetriever().getDataPointInfos(index, true);
            oldPointCount = getDataPoints().getCount();
        }

        DmlChartValue dmlValue = chartXValueToDmlChartValue(xValue, index);
        getXDmlChartValues().addNullAware(dmlValue, index);

        if (hasDataPointsOrLabels() && isComplexCorrespondenceBetweenDataPointsAndXValues())
        {
            DataPointInfo[] newPointInfos = getDataPointInfoRetriever().getDataPointInfos(index, true);
            int newPointCount = getDataPoints().getCount();
            updatePoints(oldPointInfos, oldPointCount, newPointInfos, newPointCount);
        }

        dataChanged();
    }

    /// <summary>
    /// Changes the value in the <see cref="YDmlChartValues"/> collection at the specified index.
    /// </summary>
    public void /*ISeriesDataStore.*/changeYValue(ChartYValue yValue, int index)
    {
        DataPointInfo[] oldPointInfos = null;
        int oldPointCount = 0;
        if (hasDataPointsOrLabels() && isComplexCorrespondenceBetweenDataPointsAndYValues())
        {
            oldPointInfos = getDataPointInfoRetriever().getDataPointInfos(index, true);
            oldPointCount = getDataPoints().getCount();
        }

        DmlChartValue dmlValue = chartYValueToDmlChartValue(yValue, index);
        getYDmlChartValues().addNullAware(dmlValue, index);

        if (hasDataPointsOrLabels() && isComplexCorrespondenceBetweenDataPointsAndYValues())
        {
            DataPointInfo[] newPointInfos = getDataPointInfoRetriever().getDataPointInfos(index, true);
            int newPointCount = getDataPoints().getCount();
            updatePoints(oldPointInfos, oldPointCount, newPointInfos, newPointCount);
        }

        dataChanged();
    }

    /// <summary>
    /// Changes the value in the <see cref="BubbleSizeDmlChartValues"/> collection at the specified index.
    /// </summary>
    public void /*ISeriesDataStore.*/changeBubbleSize(double bubbleSize, int index)
    {
        DmlChartValue dmlValue = bubbleSizeToDmlChartValue(bubbleSize, index);
        getBubbleSizeDmlChartValues().addNullAware(dmlValue, index);

        dataChanged();
    }

    public /*ChartXValueType*/int /*ISeriesDataStore.*/getXValueType()
    {
        if (getXDmlChartValues() == null)
            return ChartXValueType.DOUBLE; // Just return some value, it should not be used.

        /*DmlChartValueType*/int dmlValueType = getXDmlChartValues().getValueType();

        switch (dmlValueType)
        {
            case DmlChartValueType.NONE:
                Debug.assertTrue(false);
                return ChartXValueType.DOUBLE; // Just return some value, it should not be used.
            case DmlChartValueType.STRING:
                return ChartXValueType.STRING;
            case DmlChartValueType.MULTI_LVL_STRING:
            {
                return
                    (!isChartExChart() ||
                     ((getLayoutType() == SeriesLayout.TREEMAP) || (getLayoutType() == SeriesLayout.SUNBURST)))
                        ? ChartXValueType.MULTILEVEL
                        : ChartXValueType.STRING;
            }
            case DmlChartValueType.NUMERIC:
            case DmlChartValueType.MULTI_LVL_NUMERIC:
            {
                Debug.assertTrue(
                    (dmlValueType != DmlChartValueType.MULTI_LVL_NUMERIC) ||
                    (getXDmlChartValues().getLevelProperties().size() == 1));

                String formatCode = getFormatCode(mSeries.getX());

                return getXValueType(formatCode);
            }
            default:
                Debug.assertTrue(false);
                return ChartXValueType.DOUBLE;
        }
    }
    public void /*ISeriesDataStore.*/setXValueType(/*ChartXValueType*/int value)
    {
        if (((ISeriesDataStore)this).getXValueType() == value)
            return;

        if (!isXValueSupported())
            throw new IllegalStateException("X values are not supported by this series type.");

        if (!isXValueTypeSupported(value))
            throw new IllegalStateException("The series cannot contain an X value of this type.");

        Debug.assertTrue(getXDmlChartValues() != null);

        if (getXDmlChartValues().hasNonEmptyValues())
        {
            // This exception is generated when ChartXValue is set/inserted to ChartXValueCollection.
            throw new IllegalStateException(
                "The value must be of the same type as the existing items in the collection.");
        }

        switch (value)
        {
            case ChartXValueType.STRING:
            case ChartXValueType.MULTILEVEL:
            {
                getXDmlChartValues().setValueType((isChartExChart() || (value == ChartXValueType.MULTILEVEL))
                    ? DmlChartValueType.MULTI_LVL_STRING
                    : DmlChartValueType.STRING);

                break;
            }
            default:
            {
                getXDmlChartValues().setValueType(isChartExChart()
                    ? DmlChartValueType.MULTI_LVL_NUMERIC
                    : DmlChartValueType.NUMERIC);

                break;
            }
        }

        adjustXFormatCodes(value);
    }

    public /*ChartYValueType*/int /*ISeriesDataStore.*/getYValueType()
    {
        if (getYDmlChartValues() == null)
            return ChartYValueType.DOUBLE;

        /*DmlChartValueType*/int dmlValueType = getYDmlChartValues().getValueType();
        if ((dmlValueType != DmlChartValueType.NUMERIC) && (dmlValueType != DmlChartValueType.MULTI_LVL_NUMERIC))
        {
            Debug.assertTrue(false);
            return ChartYValueType.DOUBLE;
        }

        String formatCode = getFormatCode(mSeries.getY());
        return getYValueType(formatCode);
    }
    public void /*ISeriesDataStore.*/setYValueType(/*ChartYValueType*/int value)
    {
        if (((ISeriesDataStore)this).getYValueType() == value)
            return;

        if (!isYValueSupported())
            throw new IllegalStateException("Y values are not supported by this series type.");

        Debug.assertTrue(getYDmlChartValues() != null);

        if (getYDmlChartValues().hasNonEmptyValues())
        {
            // This exception is generated when ChartYValue is set/inserted to ChartYValueCollection.
            throw new IllegalStateException(
                "The value must be of the same type as the existing items in the collection.");
        }

        getYDmlChartValues().setValueType(isChartExChart()
            ? DmlChartValueType.MULTI_LVL_NUMERIC
            : DmlChartValueType.NUMERIC);

        adjustYFormatCodes(value);
    }

    public String /*ISeriesDataStore.*/getXFormatCode()
    {
        return (isXValueSupported() && StringUtil.hasChars(getXDmlChartValues().getFormatCode()))
            ? getXDmlChartValues().getFormatCode()
            : "";
    }
    public void /*ISeriesDataStore.*/setXFormatCode(String value)
    {
        if (!isXValueSupported())
            throw new IllegalStateException("X values are not supported by this series type.");

        // Let's disallow setting format code for string values in a Word 2016 chart because it may cause
        // error when opening the document in MS Word.
        /*ChartXValueType*/int valueType = ((ISeriesDataStore)this).getXValueType();
        boolean isStringData = ((valueType == ChartXValueType.STRING) || (valueType == ChartXValueType.MULTILEVEL));
        if (isChartExChart() && isStringData)
        {
            throw new IllegalStateException(
                "Format code cannot be specified for string X values in Word 2016 charts.");
        }

        getXDmlChartValues().setFormatCode(value);
    }

    public String /*ISeriesDataStore.*/getYFormatCode()
    {
        return (isYValueSupported() && StringUtil.hasChars(getYDmlChartValues().getFormatCode()))
            ? getYDmlChartValues().getFormatCode()
            : "";
    }
    public void /*ISeriesDataStore.*/setYFormatCode(String value)
    {
        if (!isYValueSupported())
            throw new IllegalStateException("Y values are not supported by this series type.");

        getYDmlChartValues().setFormatCode(value);
    }

    public String /*ISeriesDataStore.*/getBubbleSizeFormatCode()
    {
        return (isBubbleSizeSupported() && StringUtil.hasChars(getBubbleSizeDmlChartValues().getFormatCode()))
            ? getBubbleSizeDmlChartValues().getFormatCode()
            : "";
    }
    public void /*ISeriesDataStore.*/setBubbleSizeFormatCode(String value)
    {
        if (!isBubbleSizeSupported())
            throw new IllegalStateException("Bubble sizes are not supported by this series type.");

        getBubbleSizeDmlChartValues().setFormatCode(value);
    }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets a flag indicating whether the series supports X values.
    /// </summary>
    public boolean isXValueSupported()
    {
        // Word 2016 Pareto line series does not have data.
        return !isChartExChart() || (getLayoutType() != SeriesLayout.PARETO_LINE);
    }

    /// <summary>
    /// Gets a flag indicating whether the series supports Y values.
    /// </summary>
    public boolean isYValueSupported()
    {
        if (!isChartExChart())
            return true;

        return
            // Word 2016 Histogram chart has only X values.
            !((getLayoutType() == SeriesLayout.CLUSTERED_COLUMN) && !mSeries.getLayoutPr().isAggregation()) &&
            // Word 2016 Pareto line series does not have data.
            (getLayoutType() != SeriesLayout.PARETO_LINE);
    }

    /// <summary>
    /// Gets a flag indicating whether the series supports bubble sizes.
    /// </summary>
    public boolean isBubbleSizeSupported() { return (getChart().getChartType() == DmlChartType.BUBBLE_CHART); }

    /// <summary>
    /// Gets or sets a number of values in the series.
    /// </summary>
    public int getValueCount()
    {
        int count = 0;

        // When last values of a collection are empty, sometimes MS Word reduces PointsCount, thus we must
        // check PointsCount of all collections here.

        if (isXValueSupported() && (getXDmlChartValues() != null))
            count = getXDmlChartValues().getValueCount();

        if (isYValueSupported() && (getYDmlChartValues() != null))
            count = Math.max(count, getYDmlChartValues().getValueCount());

        if (isBubbleSizeSupported() && (getBubbleSizeDmlChartValues() != null))
            count = Math.max(count, getBubbleSizeDmlChartValues().getValueCount());

        return count;
    }
    public void setValueCount(int value)
    {
        if (isXValueSupported())
            getXDmlChartValues().setValueCount(value);

        if (isYValueSupported())
            getYDmlChartValues().setValueCount(value);

        if (isBubbleSizeSupported())
            getBubbleSizeDmlChartValues().setValueCount(value);
    }

    /// <summary>
    /// Gets the <see cref="DmlChartValueCollection"/> of the X values of the series.
    /// </summary>
    private DmlChartValueCollection getXDmlChartValues() { return mSeries.getX().getValues(); }

    /// <summary>
    /// Gets the <see cref="DmlChartValueCollection"/> of the Y values of the series.
    /// </summary>
    private DmlChartValueCollection getYDmlChartValues() { return mSeries.getY().getValues(); }

    /// <summary>
    /// Gets the <see cref="DmlChartValueCollection"/> of the bubble sizes of the series.
    /// </summary>
    private DmlChartValueCollection getBubbleSizeDmlChartValues() { return mSeries.getSize().getValues(); }

    /// <summary>
    /// Gets the parent chart of the series.
    /// </summary>
    private DmlChart getChart() { return mSeries.getChart(); }

    /// <summary>
    /// Gets a flag indicating whether the parent chart is a Word 2016 chart.
    /// </summary>
    private boolean isChartExChart() { return (getChart().getChartType() == DmlChartType.CHART_EX_CHART); }

    /// <summary>
    /// Gets the layout type of the series. Relates to Word 2016 charts only.
    /// </summary>
    private /*SeriesLayout*/int getLayoutType() { return mSeries.getLayoutType(); }

    /// <summary>
    /// Gets a flag indicating whether data points/labels of the next chart data value may be affected by changing
    /// an X value.
    /// </summary>
    private boolean isXValueAffectsNextValueDataPoints()
    {
        if (!isChartExChart())
            return false;

        switch (getLayoutType())
        {
            case SeriesLayout.TREEMAP:
            case SeriesLayout.SUNBURST:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Gets a flag indicating whether inserting/removing an X value may affect inserting/removing multiple data
    /// points/labels associated with it.
    /// </summary>
    private boolean isComplexCorrespondenceBetweenDataPointsAndXValues()
    {
        if (!isChartExChart())
            return false;

        switch (getLayoutType())
        {
            case SeriesLayout.BOX_WHISKER:
            case SeriesLayout.TREEMAP:
            case SeriesLayout.SUNBURST:
                return true;
            case SeriesLayout.CLUSTERED_COLUMN:
                return mSeries.getLayoutPr().isAggregation(); // Pareto chart.
            default:
                return false;
        }
    }

    /// <summary>
    /// Gets a flag indicating whether inserting/removing an Y value may affect inserting/removing multiple data
    /// points/labels associated with it.
    /// </summary>
    private boolean isComplexCorrespondenceBetweenDataPointsAndYValues() { return isChartExChart() && (getLayoutType() == SeriesLayout.BOX_WHISKER); }

    /// <summary>
    /// Gets a flag indicating whether changing a chart data value does not affect inserting/removing data
    /// points/labels or MS Word algorithm of this is too complex.
    /// </summary>
    private boolean isNoCorrespondenceBetweenDataPointsAndValues()
    {
        return
            // Histogram chart
            isChartExChart() &&
            (getLayoutType() == SeriesLayout.CLUSTERED_COLUMN) &&
            !mSeries.getLayoutPr().isAggregation();
    }

    /// <summary>
    /// Gets the data point collection of the series.
    /// </summary>
    private ChartDataPointCollection getDataPoints() { return mSeries.getDataPoints(); }

    /// <summary>
    /// Gets the data label collection of the series.
    /// </summary>
    private ChartDataLabelCollection getDataLabels() { return mSeries.getDataLabels(); }

    /// <summary>
    /// Gets a flag indicating whether the series contains any individual data points or labels.
    /// </summary>
    private boolean hasDataPointsOrLabels() { return getDataLabels().hasItems() || getDataPoints().hasItems(); }

    /// <summary>
    /// Get an instance of the <see cref="ChartExDataPointInfoRetriever"/> class used to get information about
    /// data points/labels related to a particular data value.
    /// </summary>
    private ChartExDataPointInfoRetriever getDataPointInfoRetriever()
    {
        if (mDataPointInfoRetriever == null)
            mDataPointInfoRetriever = new ChartExDataPointInfoRetriever(mSeries, true);

        return mDataPointInfoRetriever;
    }

    private /*final*/ ChartSeries mSeries;
    private ChartExDataPointInfoRetriever mDataPointInfoRetriever;

    private static final double NULL_BUBBLE_SIZE = Double.NaN;
}

