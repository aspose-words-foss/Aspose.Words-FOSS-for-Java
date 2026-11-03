// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2020 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntList;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.MathUtil;


/// <summary>
/// Contains methods to get number of chart data points and data labels and their indexes.
/// </summary>
class ChartSeriesHelper
{
    ChartSeriesHelper(ChartSeries series)
    {
        mSeries = series;
    }

    /// <summary>
    /// Gets label indexes that are displayed by default by MS Word with the current data.
    /// </summary>
    /// <remarks>
    /// In some chart types of Word 2016, depending on chart data and series options, some label indexes are unused,
    /// a label with such index is not displayed/created by MS Word/VBA. Although, if a label with unused index is
    /// explicitly defined, MS Word e.g. may display it as duplicate of another label. It seems MS Word preserves
    /// explicitly defined labels with unused indexes on re-saving.
    /// This method returns used label indexes. Indexes of deleted/hidden labels are included too.
    /// The returned indexes are sorted in ascending order.
    /// </remarks>
    IntList getLabelIndexesDeterminedByData()
    {
        if (mSeries == null)
            return null;

        if (mSeries.getChart().getChartType() != DmlChartType.CHART_EX_CHART)
            return null;

        // It seems only charts of the following two Word 2016 types can have unused label indexes.
        switch (mSeries.getLayoutType())
        {
            case SeriesLayout.BOX_WHISKER:
            {
                // It seems the behaviour of MS Word is more complex than the implemented below and depends on chart
                // size: labels that are too near to other ones are not displayed. Skip it for now.

                // See, for example, https://www150.statcan.gc.ca/n1/edu/power-pouvoir/ch12/5214889-eng.htm for
                // naming of elements of a Box and Whisker plot.

                Debug.assertTrue(mSeries.getX().getDimensionType() == DimensionType.CATEGORY);
                Debug.assertTrue(mSeries.getY().getDimensionType() == DimensionType.VALUE);

                IntList indexes = new IntList();

                ArrayList<BoxAndWhiskerPlot> items = mSeries.getDataAnalyzer().getBoxAndWhiskerPlots(mSeries.getX().getValues(),
                    mSeries.getY().getValues(), mSeries.getLayoutPr().getQuartileMethod());
                int categoryFirstLabelIndex = 0;

                for (BoxAndWhiskerPlot item : items)
                {
                    fillBoxAndWhiskerPlotLabelIndexes(item, categoryFirstLabelIndex, indexes);
                    categoryFirstLabelIndex += item.getValues().length + 4;
                }

                return indexes;
            }
            case SeriesLayout.TREEMAP:
            {
                Debug.assertTrue(mSeries.getX().getDimensionType() == DimensionType.CATEGORY);

                return getTreemapLabelIndexes();
            }
            default:
                return null;
        }
    }

    private void fillBoxAndWhiskerPlotLabelIndexes(BoxAndWhiskerPlot item, int categoryFirstLabelIndex, IntList indexes)
    {
        DmlChartSeriesLayoutPr layoutPr = mSeries.getLayoutPr();

        // Indexes of lower outlier labels.
        if (layoutPr.isOutliersVisible())
            fillLowerOutlierLabelIndexes(item, categoryFirstLabelIndex, indexes);

        // Index of lower whisker/extreme label.
        if (MathUtil.isLessOrEqual(item.getLowerExtreme(), item.getLowerQuartile()))
            indexes.add(categoryFirstLabelIndex + item.getLowerOutlierCount());

        // Indexes of non-outlier labels.
        if (layoutPr.isNonOutliersVisible())
            fillNonOutlierLabelIndexes(item, categoryFirstLabelIndex, indexes);

        int valueCount = item.getValues().length;

        // Index of upper whisker/extreme label.
        if ((valueCount > 1) && MathUtil.isGreaterOrEqual(item.getUpperExtreme(), item.getUpperQuartile()))
            indexes.add(categoryFirstLabelIndex + valueCount - item.getUpperOutlierCount() - 1);

        // Indexes of upper outlier labels.
        if (layoutPr.isOutliersVisible())
            fillUpperOutlierLabelIndexes(item, categoryFirstLabelIndex, indexes);

        int additionalLabelsFirstIndex = categoryFirstLabelIndex + valueCount;

        // Indexes of lower quantile, median, upper quantile labels.
        indexes.add(additionalLabelsFirstIndex);
        indexes.add(additionalLabelsFirstIndex + 1);
        indexes.add(additionalLabelsFirstIndex + 2);

        // Index of mean label.
        if (layoutPr.isMeanMarkerVisible())
            indexes.add(additionalLabelsFirstIndex + 3);
    }

    private static void fillLowerOutlierLabelIndexes(BoxAndWhiskerPlot plot, int categoryFirstLabelIndex,
        IntList indexes)
    {
        for (int i = 0; i < plot.getLowerOutlierCount(); i++)
        {
            // Skip duplicates.
            if ((i == 0) || !MathUtil.areEqual(plot.getValues()[i], plot.getValues()[i - 1]))
                indexes.add(categoryFirstLabelIndex + i);
        }
    }

    private static void fillNonOutlierLabelIndexes(BoxAndWhiskerPlot plot, int categoryFirstLabelIndex,
        IntList indexes)
    {
        // The extremes are not included into the cycle.
        for (int i = plot.getLowerOutlierCount() + 1; i < plot.getValues().length - plot.getUpperOutlierCount() - 1; i++)
        {
            // Skip duplicates.
            if (!MathUtil.areEqual(plot.getValues()[i], plot.getValues()[i - 1]))
                indexes.add(categoryFirstLabelIndex + i);
        }
    }

    private static void fillUpperOutlierLabelIndexes(BoxAndWhiskerPlot item, int categoryFirstLabelIndex,
        IntList indexes)
    {
        int valueCount = item.getValues().length;

        for (int i = valueCount - item.getUpperOutlierCount(); i < valueCount; i++)
        {
            // Skip duplicates.
            if (!MathUtil.areEqual(item.getValues()[i], item.getValues()[i - 1]))
                indexes.add(categoryFirstLabelIndex + i);
        }
    }

    /// <summary>
    /// Gets the number of data labels 1) that can be displayed for the parent series and 2) that are explicitly
    /// defined. Returns <b>0</b> if the property <see cref="ChartSeries.HasDataLabels"/> is <c>false</c>.
    /// </summary>
    ItemCountInfo getDataLabelCount()
    {
        // VBA does not count deleted/hidden labels of Word 2016 charts, but counts for old charts. This is determined
        // by differences in the way that such labels are represented in format. This different behaviour for different
        // chart types looks as not correct, so let's always count them for now.

        if ((mSeries == null) || (mSeries.getChart() == null) || !mSeries.hasDataLabels())
            return new ItemCountInfo(0);

        if (mSeries.getChart().getChartType() == DmlChartType.CHART_EX_CHART)
            return getChartExDataLabelCount();

        int count = mSeries.getValueCount();

        // OfPieChart contains additional label that represents sum of two smallest values.
        if ((mSeries.getChart().getChartType() == DmlChartType.OF_PIE_CHART) && (count > 2))
            count++;

        return new ItemCountInfo(count);
    }

    /// <summary>
    /// Gets the number of data points that can be displayed for the parent series.
    /// </summary>
    int getDataPointCount()
    {
        if ((mSeries == null) || (mSeries.getChart() == null))
            return 0;

        if (mSeries.getChart().getChartType() == DmlChartType.CHART_EX_CHART)
            return getChartExDataPointCount();

        int count = mSeries.getValueCount();

        // OfPieChart contains additional point that represents sum of two smallest values.
        return ((mSeries.getChart().getChartType() == DmlChartType.OF_PIE_CHART) && (count > 2)) ? count + 1 : count;
    }

    /// <summary>
    /// Gets indexes of data labels of the parent Word 2016 treemap chart series.
    /// </summary>
    private IntList getTreemapLabelIndexes()
    {
        IntList indexes = new IntList();
        final int STEM_LEVEL = 1;
        ArrayList<DmlChartDataAnalyzer.Category> categories = mSeries.getDataAnalyzer().getCategories(mSeries.getX().getValues());

        // Stem labels are not shown in a Treemap charts: exclude their indexes.
        for (int index = 0; index < categories.size(); index++)
        {
            if (categories.get(index).getLevelIndex() != STEM_LEVEL)
                indexes.add(index);
        }

        return indexes;
    }

    /// <summary>
    /// Returns data label count of a <see cref="DmlChartType.ChartExChart"/> chart.
    /// </summary>
    /// <remarks>
    /// The chart types before Word 2016 have count of data labels equal to number of data items (except
    /// <see cref="DmlChartType.OfPieChart"/> chart in which there is one additional label). In some charts of Word
    /// 2016 label count is different than data item count and depends on data or on series options.
    /// </remarks>
    private ItemCountInfo getChartExDataLabelCount()
    {
        int count;
        int lastUsedLabelIndex;

        switch (mSeries.getLayoutType())
        {
            case SeriesLayout.BOX_WHISKER:
            case SeriesLayout.TREEMAP:
            {
                IntList labelIndexes = getLabelIndexesDeterminedByData();
                Debug.assertTrue(labelIndexes != null);

                lastUsedLabelIndex = labelIndexes.get(labelIndexes.getCount() - 1);
                count = labelIndexes.getCount();

                // Calc labels that have unused indexes but have non-default formatting.
                for (ChartDataLabel label : mSeries.getDataLabels().getMaterializedDataLabels())
                {
                    if ((labelIndexes.binarySearch(label.getIndex()) < 0) && label.hasNonDefaultFormatting())
                        count++;
                }

                break;
            }
            default:
            {
                // For other chart types, number of labels equals to number of data points.
                count = getChartExDataPointCount();
                lastUsedLabelIndex = count - 1;
                break;
            }
        }

        return new ItemCountInfo(count, lastUsedLabelIndex);
    }

    /// <summary>
    /// Returns data point count of a <see cref="DmlChartType.ChartExChart"/> chart.
    /// </summary>
    /// <remarks>
    /// The chart types before Word 2016 have count of data points equal to number of data items (except
    /// <see cref="DmlChartType.OfPieChart"/> chart in which there is one additional point). In some charts of Word
    /// 2016 point count is different than data item count and depends on data.
    /// </remarks>
    private int getChartExDataPointCount()
    {
        switch (mSeries.getLayoutType())
        {
            case SeriesLayout.BOX_WHISKER:
            {
                int categoryCount = mSeries.getDataAnalyzer().getUniqueCategoryCount(mSeries.getX().getValues(), true);
                return mSeries.getX().getValues().getLastNonEmptyValueIndex() + 1 + categoryCount * BOX_WHISKER_SPECIAL_POINT_COUNT;
            }
            case SeriesLayout.TREEMAP:
            {
                ArrayList<DmlChartDataAnalyzer.Category> categories =
                    mSeries.getDataAnalyzer().getCategories(mSeries.getX().getValues());
                return categories.size();
            }
            case SeriesLayout.CLUSTERED_COLUMN:
            {
                if (mSeries.getLayoutPr().isAggregation())
                {
                    // Pareto chart
                    Debug.assertTrue(mSeries.getX().getDimensionType() == DimensionType.CATEGORY);
                    return mSeries.getDataAnalyzer().getUniqueCategoryCount(mSeries.getX().getValues(), false);
                }
                else
                {
                    // Histogram chart
                    return getHistogramDataPointCount();
                }
            }
            case SeriesLayout.PARETO_LINE:
                // It seems it is not possible to display data labels for a pareto line in MS Word.
                return 0;
            case SeriesLayout.SUNBURST:
            {
                Debug.assertTrue(mSeries.getX().getDimensionType() == DimensionType.CATEGORY);
                ArrayList<DmlChartDataAnalyzer.Category> categories =
                    mSeries.getDataAnalyzer().getCategories(mSeries.getX().getValues());
                return categories.size();
            }
            case SeriesLayout.FUNNEL:
            case SeriesLayout.REGION_MAP:
            case SeriesLayout.WATERFALL:
                return mSeries.getValueCount();
            default:
            {
                Debug.assertTrue(false, "Unknown series layout type.");
                return mSeries.getValueCount();
            }
        }
    }

    /// <summary>
    /// Calculates number of data points of the parent Word 2016 histogram chart series.
    /// </summary>
    private int getHistogramDataPointCount()
    {
        DmlChartBinningPr binning = mSeries.getLayoutPr().getBinning();

        if (binning.getBinCount() > 0)
        {
            return binning.getBinCount();
        }
        else if (!Double.isNaN(binning.getBinSize()))
        {
            Debug.assertTrue(mSeries.getX().getDimensionType() == DimensionType.VALUE);

            DmlChartDataAnalyzer.ValueRange range = mSeries.getDataAnalyzer().getValueRange(mSeries.getX().getValues());
            if (range == null)
                return 0;

            double size = Math.abs(binning.getBinSize());
            int additionalCount = 0;

            double min = range.getStart();
            if (!binning.getUnderflow().isNullOrAuto())
            {
                min = binning.getUnderflow().getValue();
                additionalCount++;
            }

            double max = range.getEnd();
            if (!binning.getOverflow().isNullOrAuto())
            {
                max = binning.getOverflow().getValue();
                additionalCount++;
            }

            if (max < min)
                max = min;

            // Calc with rounding to greater value.
            return (int)Math.ceil((max - min) / size) + additionalCount;
        }
        else
        {
            // If bins are not defined explicitly, MS Word uses some algorithm of calculation high
            // bound and bin size, we just return max value got experimentally for now.
            final int MAX_AUTOMATIC_BIN_COUNT = 11;
            return MAX_AUTOMATIC_BIN_COUNT;
        }
    }

    private /*final*/ ChartSeries mSeries;

    static class ItemCountInfo
    {
        ItemCountInfo(int count)
        {
        	this(count, count - 1);
	
        }

        ItemCountInfo(int count, int lastUsedIndex)
        {
            setCount(count);
            setLastUsedIndex(lastUsedIndex);
        }

        int getCount() { return mCount; }; private void setCount(int value) { mCount = value; };

        private int mCount;

        int getLastUsedIndex() { return mLastUsedIndex; }; private void setLastUsedIndex(int value) { mLastUsedIndex = value; };

        private int mLastUsedIndex;
    }

    /// <summary>
    /// Gets a number of special data points/labels of a category in a Box and Whisker chart. The data points/labels
    /// are: lower quantile, median, upper quantile, mean.
    /// </summary>
    static final int BOX_WHISKER_SPECIAL_POINT_COUNT = 4;
}

