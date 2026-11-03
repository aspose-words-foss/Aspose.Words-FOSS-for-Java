// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ArgumentUtil;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import java.util.Collections;
import java.util.Arrays;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Represents chart series properties.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with Charts</a> documentation article.</para>
/// </summary>
public class ChartSeries implements IChartDataPoint, IDmlExtensionListSource, Cloneable
{
    /// <summary>
    /// 5.7.2.168 ser (Bubble Chart Series)
    /// 5.7.2.169 ser (Line Chart Series)
    /// 5.7.2.170 ser (Pie Chart Series)
    /// 5.7.2.171 ser (Surface Chart Series)
    /// 5.7.2.172 ser (Scatter Chart Series)
    /// 5.7.2.173 ser (Radar Chart Series)
    /// 5.7.2.174 ser (Area Chart Series)
    /// 5.7.2.175 ser (Bar Chart Series)
    /// 2.24.3.70 CT_Series [MS-ODRAWXML]
    /// </summary>
    ChartSeries(DmlChart chart)
    {
        mDPtCollection = new ChartDataPointCollection(this);
        mChart = chart;
    }

    /// <summary>
    /// Adds the specified X value to the chart series. If the series supports Y values and bubble sizes, they will
    /// be empty for the X value.
    /// </summary>
    public void add(ChartXValue xValue)
    {
        ensureAddingValuesSupported();
        getDataProvider().addValue(xValue, null, DEFAULT_BUBBLE_SIZE);
    }

    /// <summary>
    /// Adds the specified X and Y values to the chart series.
    /// </summary>
    public void add(ChartXValue xValue, ChartYValue yValue)
    {
        ensureAddingValuesSupported();

        if (!getDataProvider().isYValueSupported())
            mChart.warn(WarningType.DATA_LOSS, WarningStrings.CANNOT_SET_Y_VALUE);

        getDataProvider().addValue(xValue, yValue, DEFAULT_BUBBLE_SIZE);
    }

    /// <summary>
    /// Adds the specified X value, Y value and bubble size to the chart series.
    /// </summary>
    public void add(ChartXValue xValue, ChartYValue yValue, double bubbleSize)
    {
        ensureAddingValuesSupported();

        if (!getDataProvider().isYValueSupported())
            mChart.warn(WarningType.DATA_LOSS, WarningStrings.CANNOT_SET_Y_VALUE);

        if (!getDataProvider().isBubbleSizeSupported())
            mChart.warn(WarningType.DATA_LOSS, WarningStrings.CANNOT_SET_BUBBLE_SIZE);

        getDataProvider().addValue(xValue, yValue, bubbleSize);
    }

    /// <summary>
    /// Inserts the specified X value into the chart series at the specified index. If the series supports Y values
    /// and bubble sizes, they will be empty for the X value.
    /// </summary>
    /// <remarks>
    /// The corresponding data point with default formatting will be inserted into the data point collection. And,
    /// if data labels are displayed, the corresponding data label with default formatting will be inserted too.
    /// </remarks>
    public void insert(int index, ChartXValue xValue)
    {
        ensureAddingValuesSupported();
        insertCore(index, xValue, null, DEFAULT_BUBBLE_SIZE);
    }

    /// <summary>
    /// Inserts the specified X and Y values into the chart series at the specified index.
    /// </summary>
    /// <remarks>
    /// The corresponding data point with default formatting will be inserted into the data point collection. And,
    /// if data labels are displayed, the corresponding data label with default formatting will be inserted too.
    /// </remarks>
    public void insert(int index, ChartXValue xValue, ChartYValue yValue)
    {
        ensureAddingValuesSupported();

        if (!getDataProvider().isYValueSupported())
            mChart.warn(WarningType.DATA_LOSS, WarningStrings.CANNOT_SET_Y_VALUE);

        insertCore(index, xValue, yValue, DEFAULT_BUBBLE_SIZE);
    }

    /// <summary>
    /// Inserts the specified X value, Y value and bubble size into the chart series at the specified index.
    /// </summary>
    /// <remarks>
    /// The corresponding data point with default formatting will be inserted into the data point collection. And,
    /// if data labels are displayed, the corresponding data label with default formatting will be inserted too.
    /// </remarks>
    public void insert(int index, ChartXValue xValue, ChartYValue yValue, double bubbleSize)
    {
        ensureAddingValuesSupported();

        if (!getDataProvider().isYValueSupported())
            mChart.warn(WarningType.DATA_LOSS, WarningStrings.CANNOT_SET_Y_VALUE);

        if (!getDataProvider().isBubbleSizeSupported())
            mChart.warn(WarningType.DATA_LOSS, WarningStrings.CANNOT_SET_BUBBLE_SIZE);

        insertCore(index, xValue, yValue, bubbleSize);
    }

    /// <summary>
    /// Removes the X value, Y value, and bubble size, if supported, from the chart series at the specified index.
    /// The corresponding data point and data label are also removed.
    /// </summary>
    public void remove(int index)
    {
        if ((index < 0) || (index >= getDataProvider().getValueCount()))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");

        getDataProvider().removeValue(index);
    }

    /// <summary>
    /// Removes all data values from the chart series. Format of all individual data points and data labels is cleared.
    /// </summary>
    public void clear()
    {
        clearValues();

        getDataPoints().clearFormat();
        getDataLabels().clearFormat();
    }

    /// <summary>
    /// Removes all data values from the chart series with preserving the format of the data points and data labels.
    /// </summary>
    public void clearValues()
    {
        getDataProvider().removeAllValues();
    }

    /// <summary>
    /// Copies default data point format from the data point with the specified index.
    /// </summary>
    public void copyFormatFrom(int dataPointIndex)
    {
        // The upper bound of the index is currently not checked because we cannot calculate DataPoints.Count
        // exactly for some chart types of MS Word 2016 (the same behavior as in the 'DataPoints.this' property).
        ArgumentUtil.checkNonNegative(dataPointIndex, "dataPointIndex");

        // To copy format from a data point of this series, we need to merge its changed properties to default
        // data point properties.
        getDefaultDataPoint().mergeFormatFrom(getDataPoints().get(dataPointIndex));
    }

    /// <summary>
    /// Sets the parent chart of this series.
    /// </summary>
    void setChart(DmlChart chart)
    {
        mChart = chart;

        if (mDefaultDPt != null)
            mDefaultDPt.setChart(chart);

        for (ChartDataPoint dataPoint : mDPtCollection.getMaterializedDataPoints())
            dataPoint.setChart(chart);

        if (mDLbls != null)
            mDLbls.setChart(chart, this);
    }

    ChartSeries deepClone()
    {
        ChartSeries lhs = (ChartSeries)memberwiseClone();

        if (mDefaultDPt != null)
            lhs.mDefaultDPt = mDefaultDPt.deepClone();

        if (isChartExChart())
        {
            // Data is cloned when cloning chart space.
            lhs.mX = null;
            lhs.mY = null;
            lhs.mSize = null;
        }
        else
        {
            if (mX != null)
                lhs.mX = mX.deepClone();

            if (mY != null)
                lhs.mY = mY.deepClone();

            if (mSize != null)
                lhs.mSize = mSize.deepClone();
        }

        if (mXErrBars != null)
        {
            lhs.mXErrBars = mXErrBars.deepClone();
            lhs.mXErrBars.setSeries(lhs);
        }

        if (mYErrBars != null)
        {
            lhs.mYErrBars = mYErrBars.deepClone();
            lhs.mYErrBars.setSeries(lhs);
        }

        if (mTx != null)
            lhs.mTx = mTx.deepClone();

        if (mDLbls != null)
            lhs.mDLbls = mDLbls.deepClone();
        if (lhs.hasDataLabels() != hasDataLabels())
            lhs.setHasDataLabels(hasDataLabels());

        lhs.mTrendlines = new ArrayList<DmlChartTrendline>();
        for (DmlChartTrendline trendline : mTrendlines)
        {
            DmlChartTrendline cloned = trendline.deepClone();
            cloned.setSeries(lhs);
            msArrayList.add(lhs.mTrendlines, cloned);
        }

        lhs.mDPtCollection = mDPtCollection.deepClone();
        lhs.mDPtCollection.setSeries(lhs);

        if (mLayoutPr != null)
            lhs.mLayoutPr = mLayoutPr.deepClone();

        if (mValueColors != null)
            lhs.mValueColors = mValueColors.deepClone();

        if (mExtensions != null)
            lhs.mExtensions = DmlExtensionListSource.cloneExtensions(mExtensions);

        lhs.mInheritedXVal = null;
        lhs.mXValues = null;
        lhs.mYValues = null;
        lhs.mBubbleSizes = null;
        lhs.mDataProvider = null;

        return lhs;
    }

    /// <summary>
    /// Creates a data storage for this series.
    /// </summary>
    /// <remarks>
    /// This method has effect only for series of Word 2016 charts, in which chart data is stored separately as
    /// an item of the chart data collection <see cref="DmlChartSpace.Data"/>.
    /// </remarks>
    void createChartExDataStorage()
    {
        if (!isChartExChart())
            return;

        DmlChartData data = getChart().getChartSpace().getData().add();
        mDataId = data.getId();

        DmlChartDataSource xDataSource = new DmlChartDataSource();
        data.getDataSources().add(xDataSource);
        mX = new DmlChartDimensionData(xDataSource);

        // Histogram charts do not have Y values.
        if (getChart().getSeriesType() != ChartSeriesType.HISTOGRAM)
        {
            DmlChartDataSource yDataSource = new DmlChartDataSource();
            data.getDataSources().add(yDataSource);
            mY = new DmlChartDimensionData(yDataSource);
        }
    }

    /// <summary>
    /// Configures the layout type and layout properties according to the specified series type.
    /// </summary>
    /// <remarks>
    /// This method has effect for series of Word 2016 charts only.
    /// </remarks>
    void configureChartExLayout(/*ChartSeriesType*/int seriesType)
    {
        if (!isChartExChart())
            return;

        switch (seriesType)
        {
            case ChartSeriesType.TREEMAP:
            {
                setLayoutType(SeriesLayout.TREEMAP);
                getLayoutPr().setProperty(DmlChartSeriesLayoutAttr.PARENT_LABEL_LAYOUT, ParentLabelLayout.OVERLAPPING);
                getDataLabels().getLabelPr().setProperty(DmlChartDataLabelAttrs.D_LBL_POS, ChartDataLabelPosition.INSIDE_END);
                break;
            }
            case ChartSeriesType.SUNBURST:
            {
                setLayoutType(SeriesLayout.SUNBURST);
                getDataLabels().getLabelPr().setProperty(DmlChartDataLabelAttrs.D_LBL_POS, ChartDataLabelPosition.CENTER);
                break;
            }
            case ChartSeriesType.HISTOGRAM:
            {
                setLayoutType(SeriesLayout.CLUSTERED_COLUMN);
                getLayoutPr().setBinning(new DmlChartBinningPr());
                getLayoutPr().getBinning().setProperty(DmlChartBinningAttr.INTERVAL_CLOSED, IntervalClosedSide.RIGHT);
                break;
            }
            case ChartSeriesType.PARETO:
            {
                setLayoutType(SeriesLayout.CLUSTERED_COLUMN);
                getLayoutPr().isAggregation(true);
                break;
            }
            case ChartSeriesType.BOX_AND_WHISKER:
            {
                setLayoutType(SeriesLayout.BOX_WHISKER);
                getLayoutPr().isMeanLineVisible(false);
                getLayoutPr().isMeanMarkerVisible(true);
                getLayoutPr().isNonOutliersVisible(false);
                getLayoutPr().isOutliersVisible(true);
                getLayoutPr().setQuartileMethod(QuartileMethod.EXCLUSIVE_MEDIAN);
                break;
            }
            case ChartSeriesType.WATERFALL:
            {
                setLayoutType(SeriesLayout.WATERFALL);
                getDataLabels().getLabelPr().setProperty(DmlChartDataLabelAttrs.D_LBL_POS, ChartDataLabelPosition.OUTSIDE_END);
                break;
            }
            case ChartSeriesType.FUNNEL:
            {
                setLayoutType(SeriesLayout.FUNNEL);
                break;
            }
            default:
            {
                Debug.assertTrue(false);
                break;
            }
        }
    }

    /// <summary>
    /// Sets the X values of the series with defining the required dimension type.
    /// </summary>
    void setXValues(DmlChartValueCollection values)
    {
        getX().setValues(values);
        getX().setDimensionType(getXDimensionType());
    }

    /// <summary>
    /// Sets the Y values of the series with defining the required dimension type.
    /// </summary>
    void setYValues(DmlChartValueCollection values)
    {
        getY().setValues(values);
        getY().setDimensionType(getYDimensionType());
    }

    /// <summary>
    /// Inserts the specified X value, Y value and bubble size into the chart series at the specified index.
    /// </summary>
    private void insertCore(int index, ChartXValue xValue, ChartYValue yValue, double bubbleSize)
    {
        if ((index < 0) || (index > getDataProvider().getValueCount()))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");

        getDataProvider().insertValue(index, xValue, yValue, bubbleSize);
    }

    /// <summary>
    /// Throws an exception if values cannot be added to the chart series.
    /// </summary>
    private void ensureAddingValuesSupported()
    {
        if (!getDataProvider().isXValueSupported() && !getDataProvider().isYValueSupported() && !getDataProvider().isBubbleSizeSupported())
            throw new IllegalStateException("This series type cannot have data.");
    }

    /// <summary>
    /// Sets a flag indicating whether data labels are displayed for the series without applying the chart style.
    /// </summary>
    void setHasDataLabels(boolean value)
    {
        mHasDataLabels = value;
    }

    /// <summary>
    /// Specifies a default data point formatting for this series.
    /// Use this property explicitly when you need to get formatting of whole series,
    /// if formatting of concrete data point is needed use <see cref="DataPoints"/> collection.
    ///
    /// Internal, for public access <see cref="IChartDataPoint"/> is used.
    /// </summary>
    ChartDataPoint getDefaultDataPoint()
    {
        if (mDefaultDPt == null)
            mDefaultDPt = new ChartDataPoint(mChart);

        return mDefaultDPt;
    }

    //>>>>>>>> #region  IChartDataPoint implementation
    /// <summary>
    /// Specifies the amount the data point shall be moved from the center of the pie.
    /// Can be negative, negative means that property is not set and no explosion should be applied.
    /// Applies only to Pie charts.
    /// </summary>
    public int getExplosion() { return getDefaultDataPoint().getExplosion(); }
    public void setExplosion(int value) { getDefaultDataPoint().setExplosion(value); }

    /// <summary>
    /// Specifies whether the parent element shall inverts its colors if the value is negative.
    /// </summary>
    public boolean getInvertIfNegative() { return getDefaultDataPoint().getInvertIfNegative(); }
    public void setInvertIfNegative(boolean value) { getDefaultDataPoint().setInvertIfNegative(value); }

    /// <summary>
    /// Specifies a data marker. Marker is automatically created when requested.
    /// </summary>
    public ChartMarker getMarker() { return getDefaultDataPoint().getMarker(); }

    /// <summary>
    /// Specifies whether the bubbles in Bubble chart should have a 3-D effect applied to them.
    /// </summary>
    public boolean getBubble3D() { return getDefaultDataPoint().getBubble3D(); }
    public void setBubble3D(boolean value) { getDefaultDataPoint().setBubble3D(value); }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Returns a collection of formatting objects for all data points in this series.
    /// </summary>
    public ChartDataPointCollection getDataPoints() { return mDPtCollection; }

    /// <summary>
    /// Gets or sets the name of the series, if name is not set explicitly it is generated using index.
    /// By default returns Series plus one based index.
    /// </summary>
    public String getName() { return (getTx() == null) ? msString.format("Series{0}", (getIndex() + 1)) : getTx().getText(); }
    public void setName(String value)
    {
        if (!StringUtil.hasChars(value))
        {
            // Reset series name to default. Set Tx to null, in this case getter returns default series name 'SeriesN'.
            setTx(null);
            mChart.warn(WarningType.DATA_LOSS, "Series name cannot be empty, name as Series plus one based index will be generated.");
            return;
        }

        if (getTx() == null)
            setTx(new DmlChartTx());

        getTx().setPlainText(value);
    }

    /// <summary>
    /// Allows to specify whether the line connecting the points on the chart shall be smoothed using Catmull-Rom splines.
    /// </summary>
    public boolean getSmooth() { return mSmooth; }
    public void setSmooth(boolean value)
    {
        mSmooth = value;
        mSmoothExplicitlySet = true;
    }

    /// <summary>
    /// Gets or sets a flag indicating whether data labels are displayed for the series.
    /// </summary>
    public boolean hasDataLabels() { return mHasDataLabels; }
    public void hasDataLabels(boolean value)
    {
        if (mHasDataLabels == value)
            return;

        mHasDataLabels = value;
        if (mHasDataLabels)
            getChart().getChartSpace().applyChartStyle(getDataLabels());
    }

    /// <summary>
    /// Specifies the settings for the data labels for the entire series.
    /// </summary>
    public ChartDataLabelCollection getDataLabels()
    {
        if (mDLbls == null)
            mDLbls = new ChartDataLabelCollection(mChart, this);

        return mDLbls;
    }
    void setDataLabels(ChartDataLabelCollection value) { mDLbls = value; }

    /// <summary>
    /// Provides access to fill and line formatting of the series.
    /// </summary>
    public ChartFormat getFormat() { return getDefaultDataPoint().getFormat(); }

    /// <summary>
    /// Gets a legend entry for this chart series.
    /// </summary>
    public ChartLegendEntry getLegendEntry()
    {
        DmlChartFormat dmlChartFormat = mChart.getPlotArea().getParentChartFormat();
        if (dmlChartFormat.getLegend() == null)
            dmlChartFormat.setLegend(new ChartLegend(dmlChartFormat));

        return dmlChartFormat.getLegend().getLegendEntries().getSeriesLegendEntry(this);
    }

    /// <summary>
    /// Gets the type of this chart series.
    /// </summary>
    public /*ChartSeriesType*/int getSeriesType()
    {
        switch (mChart.getChartType())
        {
            case DmlChartType.AREA_CHART:
            case DmlChartType.AREA_3_D_CHART:
            case DmlChartType.BAR_CHART:
            case DmlChartType.BAR_3_D_CHART:
            case DmlChartType.BUBBLE_CHART:
            case DmlChartType.DOUGHNUT_CHART:
            case DmlChartType.LINE_CHART:
            case DmlChartType.LINE_3_D_CHART:
            case DmlChartType.OF_PIE_CHART:
            case DmlChartType.PIE_CHART:
            case DmlChartType.PIE_3_D_CHART:
            case DmlChartType.RADAR_CHART:
            case DmlChartType.SCATTER_CHART:
            case DmlChartType.STOCK_CHART:
            case DmlChartType.SURFACE_CHART:
            case DmlChartType.SURFACE_3_D_CHART:
                return mChart.getDefaultSeriesType();
            case DmlChartType.CHART_EX_CHART:
            {
                switch (mLayoutType)
                {
                    case SeriesLayout.BOX_WHISKER:
                        return ChartSeriesType.BOX_AND_WHISKER;
                    case SeriesLayout.CLUSTERED_COLUMN:
                        return getLayoutPr().isAggregation() ? ChartSeriesType.PARETO : ChartSeriesType.HISTOGRAM;
                    case SeriesLayout.FUNNEL:
                        return ChartSeriesType.FUNNEL;
                    case SeriesLayout.PARETO_LINE:
                        return ChartSeriesType.PARETO_LINE;
                    case SeriesLayout.REGION_MAP:
                        return ChartSeriesType.REGION_MAP;
                    case SeriesLayout.SUNBURST:
                        return ChartSeriesType.SUNBURST;
                    case SeriesLayout.TREEMAP:
                        return ChartSeriesType.TREEMAP;
                    case SeriesLayout.WATERFALL:
                        return ChartSeriesType.WATERFALL;
                    default:
                        throw new IllegalStateException("Unknown chart of Word 2016 or later.");
                }
            }
            default:
                throw new IllegalStateException("Unknown chart type.");
        }
    }

    /// <summary>
    /// Gets a collection of X values for this chart series.
    /// </summary>
    public ChartXValueCollection getXValues()
    {
        if (mXValues == null)
        {
            if (getDataProvider().isXValueSupported() && (getX().getValues() == null))
                getX().setValues(new DmlChartValueCollection(DmlChartValueType.NUMERIC));

            mXValues = new ChartXValueCollection(getDataProvider());
        }

        return mXValues;
    }

    /// <summary>
    /// Gets a collection of Y values for this chart series.
    /// </summary>
    public ChartYValueCollection getYValues()
    {
        if (mYValues == null)
        {
            if (getDataProvider().isYValueSupported() && (getY().getValues() == null))
                getY().setValues(new DmlChartValueCollection(DmlChartValueType.NUMERIC));

            mYValues = new ChartYValueCollection(getDataProvider());
        }

        return mYValues;
    }

    /// <summary>
    /// Gets a collection of bubble sizes for this chart series.
    /// </summary>
    public BubbleSizeCollection getBubbleSizes()
    {
        if (mBubbleSizes == null)
        {
            if (getDataProvider().isBubbleSizeSupported() && (getSize().getValues() == null))
                getSize().setValues(new DmlChartValueCollection(DmlChartValueType.NUMERIC));

            mBubbleSizes = new BubbleSizeCollection(getDataProvider());
        }

        return mBubbleSizes;
    }

    /// <summary>
    /// Depending on the error bars directions, sets either X or Y error bars.
    /// </summary>
    void addErrorBars(DmlChartErrorBars errorBars)
    {
        errorBars.setSeries(this);

        switch (errorBars.getErrDir())
        {
            case ErrorBarDirection.X:
                mXErrBars = errorBars;
                break;
            case ErrorBarDirection.Y:
            default:
                mYErrBars = errorBars;
                break;
        }
    }

    /// <summary>
    /// Adds trend line to the series.
    /// </summary>
    void addTrendline(DmlChartTrendline trendline)
    {
        trendline.setSeries(this);
        msArrayList.add(mTrendlines, trendline);
    }

    /// <summary>
    /// Returns an ordered array of X values.
    /// </summary>
    private DmlChartValue[] getOrderedXValuesArrayInternal()
    {
        if (mOrderedXValuesArray != null)
            return mOrderedXValuesArray;

        ArrayList<DmlChartValue> xvaluesArray = new ArrayList<DmlChartValue>();
        // If X values are not present, generate array of dummy values.
        if (getXValuesInherited().getData() == null)
        {
            for (int i = 0; i <= getLastNonEmptyValueIndex(); i++)
                msArrayList.add(xvaluesArray, new DmlChartDummyValue(i, i + 1));
        }
        else
        {
            for (int i = 0; i < getValueCount(); i++)
            {
                DmlChartValue xVal = getXValuesInherited().getValue(i);

                if (DmlChartValue.isNullOrNaN(xVal))
                    continue;

                msArrayList.add(xvaluesArray, xVal);
            }
        }

        Collections.sort(xvaluesArray, new DmlChartValueComparer());
        mOrderedXValuesArray = msArrayList.toArray(xvaluesArray, new DmlChartValue[0]);

        return mOrderedXValuesArray;
    }

    /// <summary>
    /// Gets the index of the <see cref="DmlChartValue"/>. If value is null returns -1
    /// </summary>
    /// <remarks>
    /// Used to link a data label or marker with a series value. In normal cases, the marker or label
    /// is associated with the series element with the same index. But if the values are dates, MS Word reorders the values
    /// in ascending order.
    /// </remarks>
    /// <param name="xValue">Specified <see cref="DmlChartValue"/></param>
    /// <returns>The index of the specified <see cref="DmlChartValue"/> in the ordered array.</returns>
    int getIndexInOrderedXValuesArray(DmlChartValue xValue)
    {
        if (xValue == null)
            return -1;

        // If XValues are dates, take the index in the reordered array.
        return (getX().isDate()) ? Arrays.binarySearch(getOrderedXValuesArrayInternal(), xValue) : xValue.getIndex();
    }

    /// <summary>
    /// Gets the index in the ordered array of <see cref="DmlChartValue"/> with specified index.
    /// </summary>
    /// <param name="index">The specified index</param>
    /// <returns>The index in the ordered array of <see cref="DmlChartValue"/> with specified index</returns>
    int getIndexInOrderedXValuesArray(int index)
    {
        DmlChartValue x = getX().getValue(index);

        // If XValues are dates, take the index in the reordered array.
        return getIndexInOrderedXValuesArray(x);
    }

    /// <summary>
    /// Gets X-value by index, considering that if the data type is "Date" the values should be ordered.
    /// </summary>
    DmlChartValue getXValueByIndex(int index)
    {
        return (getX().isDate() && !isScatterChartSeries())
            ? (index < getOrderedXValuesArray().length) ? getOrderedXValuesArray()[index] : null
            : getXValuesInherited().getValue(index);
    }

    /// <summary>
    /// Call this method when finish reading series.
    /// </summary>
    void endReading()
    {
        // WORDSNET-8709 There are series with empty datasource, which caused incorrect axis values calculation.
        // To prevent incorrect values calculating, fill rendering data with dummy values if it is empty.
        if (!isChartExChart() && !getX().isEmpty())
            getX().getData().addDummyData(getLastNonEmptyValueIndex() + 1);
    }

    /// <summary>
    /// Specifies the index of the series in the chart.
    /// </summary>
    int getIndex() { return mIndex; }
    void setIndex(int value) { mIndex = value; }

    /// <summary>
    /// Specifies the order of the series in the collection. It is 0 based.
    /// </summary>
    int getOrder() { return mOrder; }
    void setOrder(int value) { mOrder = value; }

    /// <summary>
    /// Returns true if <see cref="Smooth"/> flag is explicitly set in the chart.
    /// </summary>
    boolean getSmoothExplicitlySet() { return mSmoothExplicitlySet; }

    /// <summary>
    /// Specifies the shape of a series or a 3-D bar chart.
    /// </summary>
    /*BarShape*/int getShape() { return mShape; }
    void setShape(/*BarShape*/int value) { mShape = value; }

    /// <summary>
    /// Returns data for X axis.
    /// If data for x axis is not specified for this series returns data from the first chart's series.
    /// Use this property only for rendering.
    /// </summary>
    DmlChartDimensionData getXValuesInherited()
    {
        if (mInheritedXVal == null)
            mInheritedXVal = searchForProperXValuesSource();
        return mInheritedXVal;
    }

    /// <summary>
    /// Returns ordered array of X values.
    /// </summary>
    DmlChartValue[] getOrderedXValuesArray()
    {
        return getOrderedXValuesArrayInternal();
    }

    /// <summary>
    /// Method returns X values source that should be used for rendering the chart.
    /// MS Word not always uses X values specified in series.
    /// </summary>
    /// <remarks>
    /// Code is experimental, since this behavior is not described in spec.
    /// </remarks>
    private DmlChartDimensionData searchForProperXValuesSource()
    {
        // WORDSNET-18662 Series of ScatterChart can have non-equal numbers of X and Y values.
        // WORDSNET-24211 For Scatter chart use the values from source. Do not replace them.
        if (isScatterChartSeries())
            return getX();

        // If x values of current series is empty try to use x values of the first series.
        DmlChartDimensionData src = getX().isEmpty() ? mChart.getFirstSeries().getX() : getX();

        // If number of points in X values does not match number of points in y values,
        // try to find other x values source with the same number of points.
        if (!src.isEmpty() && (src.getData().getValueCount() == getY().getData().getValueCount()))
            return src;

        DmlChartDimensionData firstNotEmptyChartDataSource = getChartDimensionData();

        return (firstNotEmptyChartDataSource != null) ? firstNotEmptyChartDataSource : src;
    }

    /// <summary>
    /// Gets the first not empty <see cref="DmlChartDimensionData"/>
    /// </summary>
    /// <returns>The first not empty <see cref="DmlChartDimensionData"/> </returns>
    private DmlChartDimensionData getChartDimensionData()
    {
        // There is nothing to search if current chart does not have axis.
        IDmlChart2D srcChart2D = ms.as(mChart, IDmlChart2D.class);

        // There is nothing to search if current chart does not have axis.
        if (srcChart2D == null)
            return null;

        for (DmlChart chart : mChart.getPlotArea().getNonEmptyCharts())
        {
            IDmlChart2D chart2D = ms.as(chart, IDmlChart2D.class);
            // Skip charts without axis.
            if (chart2D == null)
                continue;

            // skip charts that are rendered on other axis that current chart.
            if (chart2D.getAxX().getAxId() != srcChart2D.getAxX().getAxId())
                continue;

            DmlChartDimensionData nextChartValues = chart.getFirstSeries().getX();

            // Use X values with the same number of not points as Y values has.
            // WORDSNET-18098 LastNonEmptyValueIndex should be checked too.
            if (!nextChartValues.isEmpty() &&
                ((nextChartValues.getValues().getLastNonEmptyValueIndex() == getY().getValues().getLastNonEmptyValueIndex()) ||
                 (nextChartValues.getValues().getValueCount() == getY().getValues().getValueCount())))
            {
                return nextChartValues;
            }
        }

        return null;
    }

    /// <summary>
    /// Get the number of series values.
    /// </summary>
    /// <remarks>
    /// WORDSNET-24750 Use (-1) as an indicator that the data has no value. In <see cref="ValueCount"/> method,
    /// this value is converted to zero.
    /// </remarks>
    private static int getValueCount(DmlChartDimensionData values, boolean useNonEmptyValueCount)
    {
        if (values.isEmpty())
            return -1;

        int valueCount = useNonEmptyValueCount ? values.getData().getNonEmptyValueCount() : values.getData().getValueCount();

        return (valueCount > 0) ? valueCount : -1;
    }

    /// <summary>
    /// Get the number of series values.
    /// </summary>
    private static int getValueCount(DmlChartDimensionData values)
    {
        return getValueCount(values, false);
    }

    /// <summary>
    /// Refers to data of a Word 2016 chart series.
    /// </summary>
    DmlChartData getChartExChartData()
    {
        if (!isChartExChart() || (mDataId < 0) || (mChart.getChartSpace() == null))
            return null;

        return mChart.getChartSpace().getData().get(mDataId);
    }

    /// <summary>
    /// Specifies the data used for the X axis.
    /// 'cat' element in 'Line Chart', 'Pie Chart', 'Surface Chart', 'Radar Chart', 'Area Chart' and 'Bar Chart' Series.
    /// 'xVal' element in 'Bubble Chart Series' and 'Scatter Chart Series'.
    /// 'val' dimension of Histogram Word 2016 chart series.
    /// 'cat' dimension of other Word 2016 chart series.
    /// </summary>
    DmlChartDimensionData getX()
    {
        if (mX == null)
        {
            DmlChartDataSource dataSource = (getChartExChartData() != null)
                ? getChartExChartData().getDataSource(getXDimensionType())
                : new DmlChartDataSource();

            mX = new DmlChartDimensionData(dataSource);
        }

        return mX;
    }

    /// <summary>
    /// Specifies the data used for the Y axis.
    /// 'val' element in 'Line Chart', 'Pie Chart', 'Surface Chart', 'Radar Chart', 'Area Chart' and 'Bar Chart' Series.
    /// 'yVal' element in 'Bubble Chart Series' and 'Scatter Chart Series'.
    /// 'val' dimension of Funnel, Pareto, Waterfall and Box-and-Whisker Word 2016 chart series.
    /// 'size' dimension of Treemap and Sunburst Word 2016 chart series.
    /// Histogram chart has no Y values.
    /// Values are recalculated after reading all series of the chart.
    /// </summary>
    DmlChartDimensionData getY()
    {
        if (mY == null)
        {
            // A Histogram chart has only X values.
            DmlChartDataSource dataSource = ((getChartExChartData() != null) && !isHistogramChart())
                ? getChartExChartData().getDataSource(getYDimensionType())
                : new DmlChartDataSource();

            mY = new DmlChartDimensionData(dataSource);
        }

        return mY;
    }

    /// <summary>
    /// Specifies the data for the sizes of the bubbles on the bubble chart.
    /// </summary>
    DmlChartDimensionData getSize()
    {
        if (mSize == null)
            mSize = new DmlChartDimensionData(new DmlChartDataSource());

        return mSize;
    }

    int getValueCount()
    {
        // WORDSNET-21034 The radar chart uses only non-empty values for category axis data.
        int xCount = getValueCount(getX(), mChart.isRadarChart());
        int yCount = getValueCount(getY());

        return Math.max(getValueBasedOnChartType(yCount, xCount), 0);
    }

    /// <summary>
    /// Returns index of the last non-empty chart data value.
    /// </summary>
    int getLastNonEmptyValueIndex()
    {
        int yIndex = getY().isEmpty() ? -1 : getY().getData().getLastNonEmptyValueIndex();
        int xIndex = getX().isEmpty() ? -1 : getX().getData().getLastNonEmptyValueIndex();
        return getValueBasedOnChartType(yIndex, xIndex);
    }

    /// <summary>
    /// Returns X error bars, if null no error bars is applied to X.
    /// </summary>
    DmlChartErrorBars getXErrorBars() { return mXErrBars; }

    /// <summary>
    /// Returns Y error bars, if null no error bars is applied to Y.
    /// </summary>
    DmlChartErrorBars getYErrorBars() { return mYErrBars; }

    /// <summary>
    /// Returns collection of trendlines.
    /// Do not add trendlines to this collection directly, instead use <see cref="AddTrendline"/> method.
    /// </summary>
    ArrayList<DmlChartTrendline> getTrendlines() { return mTrendlines; }

    /// <summary>
    /// Specifies text for a series name, without rich text formatting.
    /// </summary>
    DmlChartTx getTx() { return mTx; }
    void setTx(DmlChartTx value) { mTx = value; }

    /// <summary>
    /// Represents extLst: a CT_OfficeArtExtensionList ([ISO/IEC29500-1:2012] section A.4.1) element that specifies
    /// the extension list in which all future extensions of element type ext is defined.
    /// </summary>
    /// <remarks>
    /// Explicit implementation - do not need this in public.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public StringToObjDictionary<DmlExtension> /*IDmlExtensionListSource.*/getExtensions() { return mExtensions; }
    @ReservedForInternalUse @Deprecated
    public void /*IDmlExtensionListSource.*/setExtensions(StringToObjDictionary<DmlExtension> value) { mExtensions = value; }

    /// <summary>
    /// Returns the parent chart of this series.
    /// </summary>
    DmlChart getChart() { return mChart; }

    /// <summary>
    /// Returns <c>true</c> if the series has y points including empty ones.
    /// Values for x can be auto generated, in case of empty y value series must be ignored.
    /// </summary>
    boolean hasPoints() { return (mY != null) && !mY.isEmpty() && (mY.getValues().getValueCount() > 0); }

    /// <summary>
    /// Gets a flag indicating whether data labels range data is defined for the series.
    /// </summary>
    /// <remarks>
    /// The mentioned data can be displayed using the <see cref="ChartDataLabelCollection.ShowDataLabelsRange"/>
    /// property.
    /// </remarks>
    boolean hasDataLabelsRangeData()
    {
        if (mDataLabelsRangeData != null)
            return mDataLabelsRangeData.getData() != null;

        if (mExtensions == null)
            return false;

        DmlExtension extension = mExtensions.get(DmlExtensionUri.FILTERING);
        if (extension == null)
            return false;

        return extension.getDataLabelsRangeData().getValues() != null;
    }

    /// <summary>
    /// Specifies the data for the data labels.
    /// Note: it is not required to write this data back to document, these values are written with extensions list.
    /// </summary>
    DmlChartDimensionData getDataLabelsRangeData()
    {
        if (mDataLabelsRangeData == null)
        {
            if (mExtensions == null)
                mExtensions = new StringToObjDictionary<DmlExtension>();

            DmlExtension extension = DmlExtension.getOrCreateExtension(mExtensions, DmlExtensionUri.FILTERING);

            mDataLabelsRangeData =  new DmlChartDimensionData(extension.getDataLabelsRangeData());
        }

        return mDataLabelsRangeData;
    }

    /// <summary>
    /// Gets or sets the colors used to represent data values as a continuous gradient of colors.
    /// </summary>
    /// <dev>
    /// Supported by charts of the schema http://schemas.microsoft.com/office/drawing/2014/chartex.
    /// </dev>
    DmlChartValueColors getValueColors() { return mValueColors; }
    void setValueColors(DmlChartValueColors value) { mValueColors = value; }

    /// <summary>
    /// Gets or sets the layout type of this series.
    /// </summary>
    /// <dev>
    /// Supported by charts of the schema http://schemas.microsoft.com/office/drawing/2014/chartex.
    /// </dev>
    /*SeriesLayout*/int getLayoutType() { return mLayoutType; }
    void setLayoutType(/*SeriesLayout*/int value) { mLayoutType = value; }

    /// <summary>
    /// Gets or sets a flag indicating whether this series is hidden from layout.
    /// </summary>
    /// <dev>
    /// Supported by charts of the schema http://schemas.microsoft.com/office/drawing/2014/chartex.
    /// </dev>
    boolean getHidden() { return mHidden; }
    void setHidden(boolean value) { mHidden = value; }

    /// <summary>
    /// Gets or sets a series that owns this series.
    /// </summary>
    /// <dev>
    /// Supported by charts of the schema http://schemas.microsoft.com/office/drawing/2014/chartex.
    /// </dev>
    ChartSeries getOwner() { return mOwner; }
    void setOwner(ChartSeries value) { mOwner = value; }

    /// <summary>
    /// Gets or sets a unique identifier for the series.
    /// </summary>
    /// <dev>
    /// Supported by charts of the schema http://schemas.microsoft.com/office/drawing/2014/chartex.
    /// </dev>
    String getUniqueId() { return mUniqueId; }
    void setUniqueId(String value) { mUniqueId = value; }

    /// <summary>
    /// Gets or sets the index of the format to use for default styling of this series.
    /// If value is negative, the property is not defined.
    /// </summary>
    /// <dev>
    /// Supported by charts of the schema http://schemas.microsoft.com/office/drawing/2014/chartex.
    /// </dev>
    int getFormatIndex() { return mFormatIndex; }
    void setFormatIndex(int value) { mFormatIndex = value; }

    /// <summary>
    /// Gets or sets the identifier of the data source for this series.
    /// If value is negative, the property is not defined.
    /// </summary>
    /// <dev>
    /// Supported by charts of the schema http://schemas.microsoft.com/office/drawing/2014/chartex.
    /// </dev>
    int getDataId() { return mDataId; }
    void setDataId(int value) { mDataId = value; }

    /// <summary>
    /// Gets or sets the identifier of an axis for this series.
    /// If value is negative, the property is not defined.
    /// </summary>
    /// <dev>
    /// Supported by charts of the schema http://schemas.microsoft.com/office/drawing/2014/chartex.
    /// </dev>
    int getAxisId() { return mAxisId; }
    void setAxisId(int value) { mAxisId = value; }

    /// <summary>
    /// Gets or sets the properties of the series layout.
    /// </summary>
    /// <dev>
    /// Supported by charts of the schema http://schemas.microsoft.com/office/drawing/2014/chartex.
    /// </dev>
    DmlChartSeriesLayoutPr getLayoutPr()
    {
        if (mLayoutPr == null)
            mLayoutPr = new DmlChartSeriesLayoutPr();
        return mLayoutPr;
    }

    /// <summary>
    /// Indicates whether the parent chart is <see cref=" DmlChartType.ScatterChart"/>
    /// or <see cref=" DmlChartType.BubbleChart"/>.
    /// </summary>
    boolean isScatterChartSeries()
    {
        switch (getChart().getChartType())
        {
            case DmlChartType.SCATTER_CHART:
                return true;
            default:
                return isBubbleChartSeries();
        }
    }

    /// <summary>
    /// Indicates whether the parent chart is <see cref=" DmlChartType.BubbleChart"/>.
    /// </summary>
    boolean isBubbleChartSeries() { return getChart().isBubbleChart(); }

    /// <summary>
    /// Indicates whether the type of the parent chart is "Pie".
    /// </summary>
    boolean isPieChartSeries() { return getChart().isPieChart(); }

    /// <summary>
    /// Gets a flag indicating whether the series is a Box and Whisker chart series.
    /// </summary>
    boolean isBoxAndWhiskerChartSeries() { return isChartExChart() && (mLayoutType == SeriesLayout.BOX_WHISKER); }

    DmlChartDataAnalyzer getDataAnalyzer() { return mDataAnalyzer; }

    /// <summary>
    /// Gets a flag indicating whether the parent chart is a Word 2016 chart.
    /// </summary>
    private boolean isChartExChart() { return mChart.isChartEx(); }

    /// <summary>
    /// Gets a flag indicating whether the parent chart is a Word 2016 Histogram chart.
    /// </summary>
    private boolean isHistogramChart()
    {
        return
            isChartExChart() &&
            (mLayoutType == SeriesLayout.CLUSTERED_COLUMN) &&
            !getLayoutPr().isAggregation();
    }

    /// <summary>
    /// Gets a data provider instance which operates with the series data.
    /// </summary>
    private ChartSeriesDataProvider getDataProvider()
    {
        if (mDataProvider == null)
            mDataProvider = new ChartSeriesDataProvider(this);

        return mDataProvider;
    }

    /// <summary>
    /// Gets the dimension type of X values.
    /// </summary>
    /// <remarks>
    /// Dimension types are used for Word 2016 charts only.
    /// </remarks>
    private /*DimensionType*/int getXDimensionType()
    {
        // Histogram chart has only 'val' dimension, which relates to X values.
        return isHistogramChart() ? DimensionType.VALUE : DimensionType.CATEGORY;
    }

    /// <summary>
    /// Gets the dimension type of Y values.
    /// </summary>
    /// <remarks>
    /// Dimension types are used for Word 2016 charts only.
    /// </remarks>
    private /*DimensionType*/int getYDimensionType()
    {
        switch (getLayoutType())
        {
            case SeriesLayout.TREEMAP:
            case SeriesLayout.SUNBURST:
                return DimensionType.SIZE;
            case SeriesLayout.REGION_MAP:
                return DimensionType.COLOR_VALUE;
            default:
                return DimensionType.VALUE;
        }
    }

    /// <summary>
    /// Returns the number of chart data values or the index of the last non-empty data value taking into account
    /// the chart type.
    /// </summary>
    private int getValueBasedOnChartType(int y, int x)
    {
        // WORDSNET-19737 If the chart type is "Scatter", the minimum between the number of x values and
        // the number of y values should be used.
        if (isScatterChartSeries() && (y > 0) && (x > 0))
            return Math.min(y, x);
        else if (y >= 0)
            return y;
        else
            return x;
    }

    private DmlChartValue[] mOrderedXValuesArray;
    private int mIndex;
    private int mOrder;
    private boolean mSmooth;
    private boolean mSmoothExplicitlySet;
    private /*BarShape*/int mShape = BarShape.NONE;
    private DmlChartDimensionData mX;
    private DmlChartDimensionData mInheritedXVal;
    private DmlChartDimensionData mY;
    private DmlChartDimensionData mSize;
    private DmlChartDimensionData mDataLabelsRangeData;
    private ChartXValueCollection mXValues;
    private ChartYValueCollection mYValues;
    private BubbleSizeCollection mBubbleSizes;
    private ChartDataLabelCollection mDLbls;
    private ArrayList<DmlChartTrendline> mTrendlines = new ArrayList<DmlChartTrendline>();
    private DmlChartTx mTx;
    private ChartDataPoint mDefaultDPt;
    private ChartDataPointCollection mDPtCollection;
    private DmlChartErrorBars mXErrBars;
    private DmlChartErrorBars mYErrBars;
    private DmlChart mChart;
    private StringToObjDictionary<DmlExtension> mExtensions;
    private DmlChartValueColors mValueColors;
    private /*SeriesLayout*/int mLayoutType;
    private boolean mHidden;
    private ChartSeries mOwner;
    private String mUniqueId;
    private int mFormatIndex = -1;
    private int mDataId = -1;
    private int mAxisId = -1;
    private DmlChartSeriesLayoutPr mLayoutPr;
    private /*final*/ DmlChartDataAnalyzer mDataAnalyzer = new DmlChartDataAnalyzer();
    private ChartSeriesDataProvider mDataProvider;
    private boolean mHasDataLabels;

    /// <summary>
    /// The default value for a bubble size when using the add value method without specifying a bubble size.
    /// </summary>
    /// <remarks>
    /// The same default value as when using <see cref="ChartSeriesCollection.Add(string,double[],double[])"/> for
    /// a bubble chart.
    /// </remarks>
    private static final double DEFAULT_BUBBLE_SIZE = Double.NaN;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

