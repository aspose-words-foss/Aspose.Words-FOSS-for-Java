// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/13/2015 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;
import com.aspose.ms.System.DateTime;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.StringUtil;


/// <summary>
/// Represents collection of a <see cref="ChartSeries"/>.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with Charts</a> documentation article.</para>
/// </summary>
public class ChartSeriesCollection implements Iterable<ChartSeries>
{
    ChartSeriesCollection(IChartSeriesSource chartSeriesSource)
    {
        mChartSeriesSource = chartSeriesSource;
    }

    /// <summary>
    /// Returns a <see cref="ChartSeries"/> at the specified index.
    /// </summary>
    /// <include file='..\..\Docs\Text.xml' path='Topics/Topic[@name="NodeCollection.IndexerCommon"]/*'/>
    /// <param name="index">An index into the collection.</param>
    public ChartSeries get(int index) { return getSeries().get(index); }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<ChartSeries> iterator()
    {
        return getSeries().iterator();
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Removes a <see cref="ChartSeries"/> at the specified index.
    /// </summary>
    /// <param name="index">The zero-based index of the <see cref="ChartSeries"/> to remove.</param>
    public void removeAt(int index)
    {
        mChartSeriesSource.removeAt(index);
    }

    /// <summary>
    /// Removes all <see cref="ChartSeries"/> from this collection.
    /// </summary>
    public void clear()
    {
        mChartSeriesSource.clear();
    }

    /// <summary>
    /// Adds new <see cref="ChartSeries"/> to this collection.
    /// Use this method to add series to any type of Bar, Column, Line and Surface charts.
    /// </summary>
    /// <returns>Recently added <see cref="ChartSeries"/> object.</returns>
    public ChartSeries add(String seriesName, String[] categories, double[] values)
    {
        return applyStyle(add(getDestinationChart(), seriesName, categories, values));
    }

    /// <summary>
    /// Adds new <see cref="ChartSeries"/> to this collection.
    /// Use this method to add series to Waterfall charts.
    /// </summary>
    /// <remarks>
    /// For chart types other than Waterfall, <paramref name="isSubtotal"/> values are ignored.
    /// </remarks>
    /// <param name="seriesName">A name of the series to be added.</param>
    /// <param name="categories">Category names for the X axis.</param>
    /// <param name="values">Y-axis values.</param>
    /// <param name="isSubtotal">Values indicating whether the corresponding Y value is a subtotal.</param>
    /// <returns>Recently added <see cref="ChartSeries"/> object.</returns>
    public ChartSeries add(String seriesName, String[] categories, double[] values, boolean[] isSubtotal)
    {
        return applyStyle(add(getDestinationChart(), seriesName, categories, values, isSubtotal));
    }

    /// <summary>
    /// Adds new <see cref="ChartSeries"/> to this collection.
    /// Use this method to add series to any type of Scatter charts.
    /// </summary>
    /// <returns>Recently added <see cref="ChartSeries"/> object.</returns>
    public ChartSeries add(String seriesName, double[] xValues, double[] yValues)
    {
        return applyStyle(add(getDestinationChart(), seriesName, xValues, yValues));
    }

    /// <summary>
    /// Adds new <see cref="ChartSeries"/> to this collection.
    /// Use this method to add series to any type of Area, Radar and Stock charts.
    /// </summary>
    //JAVA: method is internalized
	ChartSeries addInternal(String seriesName, DateTime[] dates, double[] values)
    {
        return applyStyle(add(getDestinationChart(), seriesName, dates, values));
    }

	//JAVA-added public wrapper for internalized method
	public ChartSeries add(String seriesName, java.util.Date[] dates, double[] values)
	{
		return addInternal(seriesName, DateTime.fromJava(dates), values);
	}

    /// <summary>
    /// Adds new <see cref="ChartSeries"/> to this collection.
    /// Use this method to add series to any type of Bubble charts.
    /// </summary>
    /// <returns>Recently added <see cref="ChartSeries"/> object.</returns>
    public ChartSeries add(String seriesName, double[] xValues, double[] yValues, double[] bubbleSizes)
    {
        return applyStyle(add(getDestinationChart(), seriesName, xValues, yValues, bubbleSizes));
    }

    /// <summary>
    /// Adds new <see cref="ChartSeries"/> to this collection.
    /// Use this method to add series that have multi-level data categories.
    /// </summary>
    /// <returns>Recently added <see cref="ChartSeries"/> object.</returns>
    public ChartSeries add(String seriesName, ChartMultilevelValue[] categories, double[] values)
    {
        return applyStyle(add(getDestinationChart(), seriesName, categories, values));
    }

    /// <summary>
    /// Adds new <see cref="ChartSeries"/> to this collection.
    /// Use this method to add series to Histogram charts.
    /// </summary>
    /// <remarks>
    /// For chart types other than Histogram, this method adds a series with empty Y values.
    /// </remarks>
    /// <returns>Recently added <see cref="ChartSeries"/> object.</returns>
    public ChartSeries add(String seriesName, double[] xValues)
    {
        return applyStyle(add(getDestinationChart(), seriesName, xValues));
    }

    /// <summary>
    /// Adds a new <see cref="ChartSeries"/> to this collection as a child of the specified DML chart.
    /// </summary>
    ChartSeries add(DmlChart dmlChart, String seriesName, String[] categories, double[] values)
    {
        if ((categories == null) || (values == null))
            throw new IllegalArgumentException("Data arrays must not be null.");

        if ((categories.length <= 0) || (values.length <= 0))
            throw new IllegalArgumentException("Data arrays must not be empty.");

        if (categories.length != values.length)
            throw new IllegalArgumentException("Data arrays must be of the same size.");

        ChartSeries series = addCore(dmlChart, seriesName, values);

        series.setXValues(collectStringValues(categories, dmlChart.isChartEx()));

        return series;
    }

    /// <summary>
    /// Adds a new <see cref="ChartSeries"/> to this collection as a child of the specified DML chart.
    /// </summary>
    ChartSeries add(DmlChart dmlChart, String seriesName, double[] xValues, double[] yValues)
    {
        if ((xValues == null) || (yValues == null))
            throw new IllegalArgumentException("Data arrays must not be null.");

        if ((xValues.length <= 0) || (yValues.length <= 0))
            throw new IllegalArgumentException("Data arrays must not be empty.");

        if (xValues.length != yValues.length)
            throw new IllegalArgumentException("Data arrays must be of the same size.");

        ChartSeries series = addCore(dmlChart, seriesName, yValues);

        series.setXValues(collectNumValues(xValues, dmlChart.isChartEx()));

        return series;
    }

    /// <summary>
    /// Adds a new <see cref="ChartSeries"/> to this collection as a child of the specified DML chart.
    /// </summary>
    private ChartSeries add(DmlChart dmlChart, String seriesName, DateTime[] dates, double[] values)
    {
        if ((dates == null) || (values == null))
            throw new IllegalArgumentException("Data arrays must not be null.");

        if ((dates.length <= 0) || (values.length <= 0))
            throw new IllegalArgumentException("Data arrays must not be empty.");

        if (dates.length != values.length)
            throw new IllegalArgumentException("Data arrays must be of the same size.");

        ChartSeries series = addCore(dmlChart, seriesName, values);

        series.setXValues(collectDateValues(dates, dmlChart.isChartEx()));

        // WORDSNET-16119 Mark the category axis as date/time. (Except radar chart like MS Word does.)
        if (!dmlChart.isRadarChart())
            dmlChart.setXAxisDateCategoryAttribute(true);

        return series;
    }

    /// <summary>
    /// Adds a new <see cref="ChartSeries"/> to this collection as a child of the specified DML chart.
    /// </summary>
    ChartSeries add(DmlChart dmlChart, String seriesName, double[] xValues, double[] yValues,
        double[] bubbleSizes)
    {
        if ((xValues == null) || (yValues == null))
            throw new IllegalArgumentException("Data arrays must not be null.");

        if ((xValues.length <= 0) || (yValues.length <= 0))
            throw new IllegalArgumentException("Data arrays must not be empty.");

        if (xValues.length != yValues.length)
            throw new IllegalArgumentException("Data arrays must be of the same size.");

        if (dmlChart.getChartType() != DmlChartType.BUBBLE_CHART)
            dmlChart.warn(WarningType.DATA_LOSS, WarningStrings.CANNOT_SET_BUBBLE_SIZE);

        ChartSeries series = addCore(dmlChart, seriesName, yValues);

        series.getDefaultDataPoint().getPointPr().setProperty(
            DmlChartDataPointAttr.BUBBLE_3_D,
            (series.getChart() instanceof DmlBubbleChart) && ((DmlBubbleChart)series.getChart()).getBubble3D());

        series.setXValues(collectNumValues(xValues, dmlChart.isChartEx()));

        if (bubbleSizes != null)
            series.getSize().setValues(collectNumValues(bubbleSizes, dmlChart.isChartEx()));

        return series;
    }

    /// <summary>
    /// Adds a new <see cref="ChartSeries"/> to this collection as a child of the specified DML chart.
    /// </summary>
    private ChartSeries add(DmlChart dmlChart, String seriesName, ChartMultilevelValue[] categories, double[] values)
    {
        if ((categories == null) || (values == null))
            throw new IllegalArgumentException("Data arrays must not be null.");

        if ((categories.length == 0) || (values.length == 0))
            throw new IllegalArgumentException("Data arrays must not be empty.");

        if (categories.length != values.length)
            throw new IllegalArgumentException("Data arrays must be of the same size.");

        ChartSeries series = addCore(dmlChart, seriesName, values);

        series.setXValues(collectMultilevelValues(categories));

        return series;
    }

    /// <summary>
    /// Adds a new <see cref="ChartSeries"/> to this collection as a child of the specified DML chart.
    /// </summary>
    private ChartSeries add(DmlChart dmlChart, String seriesName, double[] xValues)
    {
        if (xValues == null)
            throw new IllegalArgumentException("Data array must not be null.");

        if (xValues.length == 0)
            throw new IllegalArgumentException("Data array must not be empty.");

        // Need to provide Y values for non-Histogram charts, otherwise there is an exception when writing the document.
        double[] yValues = (dmlChart.getSeriesType() != ChartSeriesType.HISTOGRAM)
            ? generateArrayOfNaN(xValues.length)
            : null;

        ChartSeries series = addCore(dmlChart, seriesName, yValues);

        series.setXValues(collectNumValues(xValues, dmlChart.isChartEx()));

        return series;
    }

    /// <summary>
    /// Generates an array of <see cref="double.NaN"/> values.
    /// </summary>
    private static double[] generateArrayOfNaN(int length)
    {
        double[] data = new double[length];

        for (int i = 0; i < length; i++)
            data[i] = Double.NaN;

        return data;
    }

    /// <summary>
    /// Adds a new <see cref="ChartSeries"/> to this collection as a child of the specified DML chart.
    /// </summary>
    private ChartSeries add(DmlChart dmlChart, String seriesName, String[] categories, double[] yValues,
        boolean[] isSubtotal)
    {
        if ((categories == null) || (yValues == null) || (isSubtotal == null))
            throw new IllegalArgumentException("Data arrays must not be null.");

        if ((categories.length == 0) || (yValues.length == 0) || (isSubtotal.length == 0))
            throw new IllegalArgumentException("Data arrays must not be empty.");

        if ((categories.length != yValues.length) || (categories.length != isSubtotal.length))
            throw new IllegalArgumentException("Data arrays must be of the same size.");

        ChartSeries series = addCore(dmlChart, seriesName, yValues);

        series.setXValues(collectStringValues(categories, dmlChart.isChartEx()));

        if (dmlChart.getSeriesType() == ChartSeriesType.WATERFALL)
            fillSubTotalIndexes(series, isSubtotal);

        return series;
    }

    /// <summary>
    /// Fills the <see cref="DmlChartSeriesLayoutPr.SubTotals"/> property of the specified series with indexes of
    /// subtotal data points.
    /// </summary>
    private static void fillSubTotalIndexes(ChartSeries series, boolean[] isSubtotal)
    {
        series.getLayoutPr().setSubTotals(new ArrayList<Integer>());

        for (int i = 0; i < isSubtotal.length; i++)
        {
            if (isSubtotal[i])
                msArrayList.add(series.getLayoutPr().getSubTotals(), i);
        }
    }

    private ChartSeries addCore(DmlChart dmlChart, String seriesName, double[] values)
    {
        ChartSeries series = new ChartSeries(dmlChart);
        series.setName(seriesName);
        series.configureChartExLayout(dmlChart.getSeriesType());
        series.createChartExDataStorage();

        setSeriesTypedProperties(series);

        if (values != null)
            series.setYValues(collectNumValues(values, dmlChart.isChartEx()));

        // After adding a series, an existing embedded/linked XLSX document does not contain the added data, we need
        // to remove it, so that when the document is opened, MS Word will generate a new embedded file that includes
        // the entire chart data.
        DmlChartSpace chartSpace = mChartSeriesSource.getDestinationChart().getChartSpace();
        chartSpace.removeExternalDataLinkage();

        DmlChartPlotArea plotArea = chartSpace.getChartFormat().getPlotArea();
        plotArea.addSeries(series);

        if (series.getSeriesType() == ChartSeriesType.PARETO)
            addParetoLineSeries(dmlChart, series);

        return series;
    }

    /// <summary>
    /// Adds a pareto line series to the DML chart using the specified chart series as an owner.
    /// </summary>
    private static void addParetoLineSeries(DmlChart dmlChart, ChartSeries ownerSeries)
    {
        ChartSeries paretoLine = new ChartSeries(dmlChart);
        paretoLine.setLayoutType(SeriesLayout.PARETO_LINE);
        paretoLine.setOwner(ownerSeries);
        dmlChart.getPlotArea().addSeries(paretoLine);

        ChartAxis paretoLineAxis = dmlChart.getPlotArea().getOrCreateSecondaryYAxis();
        AxisScaling scaling = new AxisScaling();
        scaling.setMinimum(new AxisBound(0.0));
        scaling.setMaximum(new AxisBound(1.0));
        paretoLineAxis.getChartAxisPr().setProperty(DmlChartAxisAttrs.SCALING, scaling);
        paretoLineAxis.getDisplayUnit().setUnit(AxisBuiltInUnit.PERCENTAGE);
        paretoLineAxis.hasMajorGridlines(false);

        paretoLine.setAxisId(paretoLineAxis.getAxId());
    }

    private static void setSeriesTypedProperties(ChartSeries series)
    {
        // This is required because if SpPr is not set explicitly, the default properties are returned.
        // If customer changes properties, all series with default properties are changed, that is incorrect,
        // only concrete series must be changed.
        series.getDefaultDataPoint().getPointPr().setProperty(DmlChartDataPointAttr.SP_PR, new DmlChartSpPr());

        // MS Word initializes invertIfNegative to false by default. And the property has this value in created
        // charts. Let's do the same.
        if (ChartDataPointCollection.supportsInvertIfNegative(series.getChart().getChartType()))
            series.setInvertIfNegative(false);

        switch (series.getChart().getChartType())
        {
            case DmlChartType.LINE_CHART:
            case DmlChartType.RADAR_CHART:
            {
                setMarkerDefaults(series, MarkerSymbol.NONE);
                break;
            }
            case DmlChartType.STOCK_CHART:
            {
                setMarkerDefaults(series, MarkerSymbol.NONE);
                setScatterSpPrDefaults(series);
                break;
            }
            case DmlChartType.SCATTER_CHART:
            {
                setMarkerDefaults(series, MarkerSymbol.CIRCLE);
                setScatterSpPrDefaults(series);
                break;
            }
            case DmlChartType.PIE_CHART:
            case DmlChartType.PIE_3_D_CHART:
            case DmlChartType.OF_PIE_CHART:
            case DmlChartType.DOUGHNUT_CHART:
            {
                setPieChartSpPrDefaults(series);
                break;
            }
            default:
            {
                break;
            }
        }
    }

    private static void setPieChartSpPrDefaults(ChartSeries series)
    {
        DmlChartSpPr spPr = new DmlChartSpPr();
        spPr.setOutline(new DmlOutline());
        DmlSchemeColor schemeColor = new DmlSchemeColor(ThemeColor.LIGHT_1);
        spPr.getOutline().setFill(new DmlSolidFill(schemeColor));
        spPr.getOutline().setWidthInEmus(19050.0);
        series.getDefaultDataPoint().getPointPr().setProperty(DmlChartDataPointAttr.SP_PR, spPr);
    }

    private static void setScatterSpPrDefaults(ChartSeries series)
    {
        DmlChartSpPr spPr = new DmlChartSpPr();
        spPr.setOutline(new DmlOutline());
        spPr.getOutline().setFill(new DmlNoFill());
        spPr.getOutline().setEndCap(EndCap.ROUND);
        spPr.getOutline().setWidthInEmus(19050.0);
        series.getDefaultDataPoint().getPointPr().setProperty(DmlChartDataPointAttr.SP_PR, spPr);
    }

    private static void setMarkerDefaults(ChartSeries series, /*MarkerSymbol*/int markerStyle)
    {
        ChartMarker marker = new ChartMarker(series.getChart());
        marker.getMarkerPr().setProperty(DmlChartMarkerAttr.SYMBOL, markerStyle);
        series.getDefaultDataPoint().getPointPr().setProperty(DmlChartDataPointAttr.MARKER, marker);
    }

    private static DmlChartValueCollection collectNumValues(double[] values, boolean asMultiLevelChartValue)
    {
        return collectNumValues(values, asMultiLevelChartValue, ChartNumberFormat.GENERAL_FORMAT_CODE);
    }

    private static DmlChartValueCollection collectNumValues(double[] values,
        boolean asMultiLevelChartValue, String formatCode)
    {
        int i = 0;
        DmlChartValueCollection dmlChartValues = new DmlChartValueCollection(
            asMultiLevelChartValue ? DmlChartValueType.MULTI_LVL_NUMERIC : DmlChartValueType.NUMERIC);

        dmlChartValues.setFormatCode(formatCode);

        for (double value : values)
        {
            // Consider double.NaN value as empty values in chart data.
            // On the one hand the same have to be done for chart DateValues (CollectDateValues method).
            // But on the other hand, DateValues are used as X-values and if one of them is empty all corresponding Y-values
            // of series will be ignored (MSW behavior). And also since DateTime is a value type you cannot assign null to it.
            if (!Double.isNaN(value))
            {
                // Do not add to the value collection data points with double.NaN values.

                if (asMultiLevelChartValue)
                {
                    DmlChartMultiLvlNumValue chartValue = new DmlChartMultiLvlNumValue(i, null);
                    chartValue.getLevels().add(value);
                    dmlChartValues.add(chartValue);
                }
                else
                {
                    dmlChartValues.add(new DmlChartNumValue(i, value));
                }
            }

            i++;
        }

        dmlChartValues.setValueCount(values.length);
        return dmlChartValues;
    }

    private static DmlChartValueCollection collectDateValues(DateTime[] dates, boolean asMultiLevelChartValue)
    {
        return collectNumValues(
            toWordNumericDateArray(dates),
            asMultiLevelChartValue,
            ChartNumberFormat.DEFAULT_DATE_FORMAT_CODE);
    }

    private static DmlChartValueCollection collectStringValues(String[] categories, boolean asMultiLevelChartValue)
    {
        int i = 0;
        DmlChartValueCollection dmlChartValues = new DmlChartValueCollection(
            asMultiLevelChartValue ? DmlChartValueType.MULTI_LVL_STRING : DmlChartValueType.STRING);

        for (String category : categories)
        {
            if (asMultiLevelChartValue)
            {
                DmlChartMultiLvlStrValue chartValue = new DmlChartMultiLvlStrValue(i);
                chartValue.getLevels().add(category);
                dmlChartValues.add(chartValue);
            }
            else
            {
                dmlChartValues.add(new DmlChartStrValue(i, category));
            }

            i++;
        }

        dmlChartValues.setValueCount(categories.length);

        return dmlChartValues;
    }

    /// <summary>
    /// Collects the specified multi-level values in a created <see cref="DmlChartValueCollection"/>.
    /// </summary>
    private static DmlChartValueCollection collectMultilevelValues(ChartMultilevelValue[] values)
    {
        DmlChartValueCollection dmlChartValues = new DmlChartValueCollection(DmlChartValueType.MULTI_LVL_STRING);

        int levelCount = getLevelCount(values);
        dmlChartValues.setValueCountForAllLevels(values.length, levelCount);

        int i = 0;

        for (ChartMultilevelValue multilevelValue : values)
        {
            DmlChartMultiLvlStrValue value = new DmlChartMultiLvlStrValue(i++);

            if (levelCount == 3)
                value.getLevels().add(multilevelValue.getLevel3());
            if (levelCount >= 2)
                value.getLevels().add(multilevelValue.getLevel2());
            value.getLevels().add(multilevelValue.getLevel1());

            dmlChartValues.add(value);
        }

        return dmlChartValues;
    }

    /// <summary>
    /// Gets the maximum number of levels in the specified multi-level values.
    /// </summary>
    private static int getLevelCount(ChartMultilevelValue[] values)
    {
        int levelCount = 1;

        for (ChartMultilevelValue multilevelValue : values)
        {
            if (StringUtil.hasChars(multilevelValue.getLevel3()))
                return 3;

            if (StringUtil.hasChars(multilevelValue.getLevel2()))
                levelCount = 2;
        }

        return levelCount;
    }

    /// <summary>
    /// Converts the specified datetime values to Word's numeric datetime representations.
    /// </summary>
    private static double[] toWordNumericDateArray(DateTime[] dates)
    {
        double[] doubles = new double[dates.length];

        for (int i = 0; i < dates.length; i++)
            doubles[i] = DmlChartUtil.getDoubleFromDate(dates[i]);

        return doubles;
    }

    /// <summary>
    /// Applies the current chart style to the specified series and returns it.
    /// </summary>
    private ChartSeries applyStyle(ChartSeries series)
    {
        mChartSeriesSource.getDestinationChart().getChartSpace().applyChartStyle(series);
        return series;
    }

    /// <summary>
    /// Returns the number of <see cref="ChartSeries"/> in this collection.
    /// </summary>
    public int getCount() { return getSeries().size(); }

    /// <summary>
    /// Gets a list of series of the plot area.
    /// </summary>
    private ArrayList<ChartSeries> getSeries() { return mChartSeriesSource.getSeriesList(); }

    /// <summary>
    /// Gets the DML chart to which adding series are placed.
    /// Currently series are added to the first DML chart of the plot area only.
    /// </summary>
    private DmlChart getDestinationChart() { return mChartSeriesSource.getDestinationChart(); }

    private /*final*/ IChartSeriesSource mChartSeriesSource;
}

