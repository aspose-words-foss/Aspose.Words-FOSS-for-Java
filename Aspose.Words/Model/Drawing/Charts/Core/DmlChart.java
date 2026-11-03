// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.Debug;


/// <summary>
/// Class represents chart element.
/// See 5.7 section in OOXML spec.
/// </summary>
abstract class DmlChart implements IChartSeriesSource, Cloneable
{
    void warn(/*WarningType*/int warningType, String message)
    {
        if (mPlotArea != null)
            mPlotArea.warn(warningType, message);
    }

    /// <summary>
    /// Use this method to add axis id to the chart, there might be 2 or 3 ids.
    /// It seems they are ordered like x, y, z. So the first is for X axis.
    /// </summary>
    /// <remarks>
    /// A Pareto chart has two Y axes: for Y value series and for Pareto lines.
    /// </remarks>
    /// <returns>Direction of the referenced axis.</returns>
    /*AxisDirection*/int addAxId(int axId)
    {
        /*AxisDirection*/int direction = AxisDirection.UNSPECIFIED;

        switch (mAxIdIndex)
        {
            case 0:
            {
                direction = (getSeriesType() == ChartSeriesType.FUNNEL) ? AxisDirection.Y :AxisDirection.X;
                getChartPr().setProperty(DmlChartAttrs.AX_ID_X, axId);

                break;
            }
            case 1:
            {
                getChartPr().setProperty(DmlChartAttrs.AX_ID_Y, axId);
                direction = AxisDirection.Y;
                break;
            }
            case 2:
            {
                // Word 2016 chart has no Z axes. This is a secondary Y axis in that chart.
                if (isChartEx())
                {
                    if (getSeriesType() == ChartSeriesType.PARETO)
                        direction = AxisDirection.Y;
                }
                else
                {
                    getChartPr().setProperty(DmlChartAttrs.AX_ID_Z, axId);
                    direction = AxisDirection.Z;
                }

                break;
            }
            default:
            {
                throw new IllegalArgumentException("there cannot be more than 3 axis.");
            }
        }

        mAxIdIndex++;

        return direction;
    }

    void setPlotArea(DmlChartPlotArea plotArea)
    {
        mPlotArea = plotArea;
    }

    /// <summary>
    /// Sets the <see cref="DmlChartAxisAttrs.IsDateCategoryAxis"/> attribute of the X axis.
    /// </summary>
    void setXAxisDateCategoryAttribute(boolean value)
    {
        if ((getAxisX() != null) && getAxisX().isCategory())
            getAxisX().getChartAxisPr().setProperty(DmlChartAxisAttrs.IS_DATE_CATEGORY_AXIS, value);
    }

    /// <summary>
    /// Updates the <see cref="SeriesType"/> property in accordance with the type of an existing chart series.
    /// </summary>
    void updateSeriesType()
    {
        if ((mSeriesType != UNKNOWN_SERIES_TYPE) || (mSeries == null) || (mSeries.getCount() == 0))
            return;

        mSeriesType = mSeries.get(0).getSeriesType();

        // A pareto line is a special series in a pareto chart.
        if (mSeriesType == ChartSeriesType.PARETO_LINE)
            mSeriesType = ChartSeriesType.PARETO;
    }

    /// <summary>
    /// Sets stacked Y-values.
    /// </summary>
    /// <param name="series">The specified <see cref="ChartSeries"/></param>
    /// <param name="yValuesStackNegative">Array of stacked negative values</param>
    /// <param name="yValuesStackPositive">Array of stacked positive values</param>
    /// <param name="maxYValues">Array of Y values sum</param>
    private void setYStackedValues(ChartSeries series, double[] yValuesStackNegative, double[] yValuesStackPositive,
        double[] maxYValues)
    {
        DmlChartDimensionData yValues = series.getY();

        for (int i = 0; i < getMaxPointsCount(); i++)
        {
            DmlChartValue value = yValues.getData().getOriginalValue(i);
            boolean isNumValue = (value != null) && (value.getValueType() == DmlChartValueType.NUMERIC);

            // WORDSNET-9080 Check whether value is num value. Dummy value might occur here.
            String formatCode = isNumValue ? ((DmlChartNumValue)value).getFormatCode() : null;

            double currentNumValue = isNumValue
                ? Double.isNaN(value.getValue()) ? 0.0d : value.getValue()
                : 0.0d;

            // Use different stacks only for bar chart.
            double[] yValuesStack = (currentNumValue < 0) && isBarChart()
                ? yValuesStackNegative
                : yValuesStackPositive;

            yValuesStack[i] += currentNumValue;
            double currentValue = isPercentStacked() ? (yValuesStack[i] / maxYValues[i]) : yValuesStack[i];

            // Method add will reset or add value at the specified index.
            yValues.getData().addValue(new DmlChartNumValue(i, currentValue, formatCode));
        }
    }

    /// <summary>
    /// Clones this DML chart.
    /// </summary>
    /*virtual*/ DmlChart deepClone()
    {
        DmlChart lhs = (DmlChart)memberwiseClone();

        lhs.mPlotArea = null;
        lhs.mNonEmptySeries = null;

        if (mChartPr != null)
            lhs.setChartPr(mChartPr.deepClone());

        lhs.mSeries = new DmlChartSeriesCollection(lhs);
        for (ChartSeries series : lhs.getSeries())
            series.setChart(lhs);

        ChartDataLabelCollection labels =
            (ChartDataLabelCollection)lhs.getChartPr().getDirectProperty(DmlChartAttrs.D_LBLS);
        if (labels != null)
            labels.setChart(lhs, null);

        lhs.mAxisX = null;
        lhs.mAxisY = null;
        lhs.mAxisZ = null;

        return lhs;
    }

    /// <summary>
    /// Returns the series index, which is used when displaying the Z-axis, based on axis orientation.
    /// </summary>
    /// <param name="index">The sequential number</param>
    /// <returns>
    /// Sequential number if the standard orientation is used, otherwise - sequential number from the end of
    /// the collection
    /// </returns>
    int getSeriesIndex(int index)
    {
        return isZAxisWithReverseOrder() ? (getNonEmptySeries().size() - index - 1) : index;
    }

    /// <summary>
    /// Removes series data with the specified ID from a Word 2016 chart. Data of these charts is stored separately
    /// from series.
    /// </summary>
    void removeSeriesData(int dataId)
    {
        if (isChartEx() && (dataId >= 0))
            getChartSpace().getData().remove(dataId);
    }

    /// <summary>
    /// Removes the specified series, including series data, from the parent plot area.
    /// </summary>
    private void removeSeries(ChartSeries series)
    {
        removeSeriesData(series.getDataId());
        getPlotArea().removeSeries(series);
    }

    /// <summary>
    /// Must be overridden and return type of the concrete chart.
    /// </summary>
    abstract /*DmlChartType*/int getChartType();

    /// <summary>
    /// Gets the type of series that such DML chart contains by default.
    /// </summary>
    /// <remarks>
    /// The returned value is always equal to <see cref="SeriesType"/> for pre Word 2016 charts. For Word 2016
    /// charts, series layout type stored in <see cref="ChartSeries"/> is needed to determine series type.
    /// </remarks>
    abstract /*ChartSeriesType*/int getDefaultSeriesType();

    /// <summary>
    /// Legend is rendered for each data point if there is only one chart, chart contains only one series
    /// and at least one data point has custom formatting or at each data marker in the series shall have a different color.
    /// </summary>
    /*virtual*/ boolean getRenderLegendForDataPoints() { return ((getSeries().getCount() == 1) && (getFirstSeries() != null) &&
                  (getFirstSeries().getDataPoints().hasCustomDataPoints() || getVaryColors())
                  && (getSeriesType() != ChartSeriesType.WATERFALL)); }

    /// <summary>
    /// Returns plot area of the current chart.
    /// </summary>
    DmlChartPlotArea getPlotArea() { return mPlotArea; }

    /// <summary>
    /// Gets <see cref="DocumentBase"/> that this node belongs to.
    /// </summary>
    DocumentBase getDocument()
    {
        if (mDocument == null)
            mDocument = ((mPlotArea == null) || (getChartSpace() == null)) ? null : getChartSpace().getDml().getDocument();

        return mDocument;
    }

    void setDocument(DocumentBase value) { mDocument = value; }

    /// <summary>
    /// Returns chart space that specifies overall settings of the current chart.
    /// </summary>
    DmlChartSpace getChartSpace() { return mPlotArea.getParentChartFormat().getDmlChartSpace(); }

    /// <summary>
    /// Returns the first series in the chart.
    /// </summary>
    ChartSeries getFirstSeries()
    {
        if (mFirstSeries == null)
        {
            mFirstSeries =  (getNonEmptySeries().size() == 0) ? null : getNonEmptySeries().get(0);

            // WORDSNET-23643 Use the first series containing x and y values.
            for (int i = 0; i < getNonEmptySeries().size(); i++)
            {
                if (!getNonEmptySeries().get(i).getX().isEmpty())
                {
                    mFirstSeries = getNonEmptySeries().get(i);
                    break;
                }
            }
        }

        return mFirstSeries;
    }

    /// <summary>
    /// Returns a collection of <see cref="ChartSeries"/> of this chart.
    /// </summary>
    DmlChartSeriesCollection getSeries()
    {
        if (mSeries == null)
            mSeries = new DmlChartSeriesCollection(this);

        return mSeries;
    }

    /// <summary>
    /// Returns the type of series of this DML chart.
    /// </summary>
    /*ChartSeriesType*/int getSeriesType()
    {
        if (mSeriesType == UNKNOWN_SERIES_TYPE)
            return getDefaultSeriesType();

        return mSeriesType;
    }

    /// <summary>
    /// Returns only series that has points.
    /// Use this collection for proper rendering of bar charts, since number of series affects width of bars.
    /// </summary>
    ArrayList<ChartSeries> getNonEmptySeries()
    {
        if (mNonEmptySeries == null)
        {
            mNonEmptySeries = new ArrayList<ChartSeries>();
            for (ChartSeries series : getSeries())
            {
                if (series.hasPoints())
                    msArrayList.add(mNonEmptySeries, series);
            }
        }

        return mNonEmptySeries;
    }

    void resetNonEmtpySeries()
    {
        mNonEmptySeries = null;
        mFirstSeries = null;
    }

    /// <summary>
    /// Flag specifies that each data marker in the series shall have a different color.
    /// Has no effect for surface and stock charts.
    /// </summary>
    boolean getVaryColors() { return (/*boolean*/Boolean)getChartPr().getProperty(DmlChartAttrs.VARY_COLORS); }
    void setVaryColors(boolean value) { getChartPr().setProperty(DmlChartAttrs.VARY_COLORS, value); }

    /// <summary>
    /// Specifies the settings for the data labels for an entire series or the entire chart.
    /// Has no effect for surface charts.
    /// </summary>
    ChartDataLabelCollection getDataLabels()
    {
       Object dataLabels = getChartPr().getProperty(DmlChartAttrs.D_LBLS);
       return  (dataLabels != null) ? (ChartDataLabelCollection)(dataLabels) : new ChartDataLabelCollection(this, null);
    }

    /// <summary>
    /// Returns properties of this chart.
    /// </summary>
    DmlChartPr getChartPr()
    {
        if (mChartPr == null)
            mChartPr = new DmlChartPr(getDocument());

        return mChartPr;
    }

    void setChartPr(DmlChartPr value) { mChartPr = value; }

    /// <summary>
    /// Returns true if the chart has 3D chart.
    /// </summary>
    boolean getIs3D()
    {
        switch (getChartType())
        {
            case DmlChartType.PIE_3_D_CHART:
            case DmlChartType.AREA_3_D_CHART:
            case DmlChartType.LINE_3_D_CHART:
            case DmlChartType.BAR_3_D_CHART:
            case DmlChartType.SURFACE_3_D_CHART:
            case DmlChartType.SURFACE_CHART:
                return true;
            case DmlChartType.AREA_CHART:
            case DmlChartType.LINE_CHART:
            case DmlChartType.STOCK_CHART:
            case DmlChartType.RADAR_CHART:
            case DmlChartType.SCATTER_CHART:
            case DmlChartType.BAR_CHART:
            case DmlChartType.BUBBLE_CHART:
            case DmlChartType.PIE_CHART:
            case DmlChartType.DOUGHNUT_CHART:
            case DmlChartType.OF_PIE_CHART:
            default:
                return false;
        }
    }

    /// <summary>
    /// Indicates whether the chart has Z-axis and axis values should be rendered in reverse order (from max to min).
    /// </summary>
    private boolean isZAxisWithReverseOrder()  { return (getIs3D() && (getAxisZ() != null) && getAxisZ().getReverseOrder()); }

    /// <summary>
    /// Returns true if the chart has axis.
    /// Returns false, if chart type is Pie or Doughnut, because these charts cannot have axis.
    /// </summary>
    boolean hasAxis()
    {
        switch (getChartType())
        {
            case DmlChartType.AREA_CHART:
            case DmlChartType.AREA_3_D_CHART:
            case DmlChartType.LINE_CHART:
            case DmlChartType.LINE_3_D_CHART:
            case DmlChartType.STOCK_CHART:
            case DmlChartType.RADAR_CHART:
            case DmlChartType.SCATTER_CHART:
            case DmlChartType.BAR_CHART:
            case DmlChartType.BAR_3_D_CHART:
            case DmlChartType.SURFACE_CHART:
            case DmlChartType.SURFACE_3_D_CHART:
            case DmlChartType.BUBBLE_CHART:
                return true;
            case DmlChartType.CHART_EX_CHART:
            {
                if (getSeriesType() == ChartSeriesType.SUNBURST ||
                    getSeriesType() == ChartSeriesType.TREEMAP ||
                    getSeriesType() == ChartSeriesType.REGION_MAP)
                    return false;

                return true;
            }
            case DmlChartType.PIE_CHART:
            case DmlChartType.PIE_3_D_CHART:
            case DmlChartType.DOUGHNUT_CHART:
            case DmlChartType.OF_PIE_CHART:
            default:
                return false;
        }
    }

    /// <summary>
    /// Provides access to properties of the X axis of the chart.
    /// </summary>
    ChartAxis getAxisX()
    {
        if ((mAxisX == null) && hasAxis())
        {
            IDmlChart2D chart2D = ms.as(this, IDmlChart2D.class);
            if (chart2D != null)
                mAxisX = chart2D.getAxX();
        }

        return mAxisX;
    }
    void setAxisX(ChartAxis value)
    {
        // Cannot remove axis from a DML chart.
        Debug.assertTrue(value != null);

        getChartPr().setProperty(DmlChartAttrs.AX_ID_X, value.getAxId());
        mAxisX = value;
    }

    /// <summary>
    /// Provides access to properties of the Y axis of the chart.
    /// </summary>
    ChartAxis getAxisY()
    {
        if ((mAxisY == null) && hasAxis())
        {
            IDmlChart2D chart2D = ms.as(this, IDmlChart2D.class);
            if (chart2D != null)
                mAxisY = chart2D.getAxY();
        }

        return mAxisY;
    }
    void setAxisY(ChartAxis value)
    {
        // Cannot remove axis from a DML chart.
        Debug.assertTrue(value != null);

        getChartPr().setProperty(DmlChartAttrs.AX_ID_Y, value.getAxId());
        mAxisY = value;
    }

    /// <summary>
    /// Provides access to properties of the Z axis of the chart.
    /// </summary>
    ChartAxis getAxisZ()
    {
        if ((mAxisZ == null) && hasAxis())
        {
            IDmlChart3D chart3D = ms.as(this, IDmlChart3D.class);
            if (chart3D != null)
                mAxisZ = chart3D.getAxZ();
        }

        return mAxisZ;
    }

    /// <summary>
    /// Returns maximum number of data points in all chart's series.
    /// </summary>
    int getMaxPointsCount()
    {
        if (mMaxPointsCount<0)
        {
            for (ChartSeries series : getSeries())
                mMaxPointsCount = Math.max(mMaxPointsCount, series.getValueCount());
        }
        return mMaxPointsCount;
    }

    /// <summary>
    /// Indicates whether the chart is "RadarChart".
    /// </summary>
    boolean isRadarChart() { return (getChartType() == DmlChartType.RADAR_CHART); }

    /// <summary>
    /// Indicates whether the chart is "PieChart".
    /// </summary>
    boolean isPieChart()
    {
        switch (getChartType())
        {
            case DmlChartType.PIE_CHART:
            case DmlChartType.PIE_3_D_CHART:
            case DmlChartType.OF_PIE_CHART:
            case DmlChartType.DOUGHNUT_CHART:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Indicates whether the chart is <see cref=" DmlChartType.ScatterChart"/>
    /// or <see cref=" DmlChartType.BubbleChart"/>.
    /// </summary>
    boolean isScatter()
    {
        switch (getChartType())
        {
            case DmlChartType.SCATTER_CHART:
            case DmlChartType.BUBBLE_CHART:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Indicates whether the chart is "SurfaceChart".
    /// </summary>
    boolean isSurfaceChart() { return getChartType() == DmlChartType.SURFACE_CHART;}

    /// <summary>
    /// Indicates whether the chart is "Surface3DChart".
    /// </summary>
    boolean isSurface3DChart() { return getChartType() == DmlChartType.SURFACE_3_D_CHART; }

    /// <summary>
    /// Indicates whether the chart is "BubbleChart".
    /// </summary>
    boolean isBubbleChart() { return getChartType() == DmlChartType.BUBBLE_CHART; }

    /// <summary>
    /// Indicates whether the chart is "LineChart".
    /// </summary>
    boolean isLineChart() { return getChartType() == DmlChartType.LINE_CHART; }

    /// <summary>
    /// Indicates whether chart can be rendered.
    /// </summary>
    /// <remark>
    /// The histogram is currently supported.
    /// </remark>
    boolean isSupportedChartEx()
    {
        return (getSeriesType() == ChartSeriesType.HISTOGRAM) ||
            (getSeriesType() == ChartSeriesType.WATERFALL) ||
            (getSeriesType() == ChartSeriesType.FUNNEL);
    }

    /// <summary>
    /// Indicates whether the chart type is "BarChart" or "Bar3DChart".
    /// </summary>
    boolean isBarChart()
    {
        switch (getChartType())
        {
            case DmlChartType.BAR_CHART:
            case DmlChartType.BAR_3_D_CHART:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Gets a flag indicating whether the chart is a Word 2016 chart.
    /// </summary>
    boolean isChartEx() { return (getChartType() == DmlChartType.CHART_EX_CHART); }

    /// <summary>
    /// Indicated whether the chart is percent stacked.
    /// </summary>
    boolean isPercentStacked() { return (getChartPr().getGrouping() == Grouping.PERCENT_STACKED); }

    /// <summary>
    /// Indicated whether the chart is stacked.
    /// </summary>
    boolean isStacked()
    {
        /*Grouping*/int grouping = getChartPr().getGrouping();
        return (grouping == Grouping.PERCENT_STACKED) || (grouping == Grouping.STACKED);
    }

    /// <summary>
    /// Indicates whether the chart type is supported.
    /// </summary>
    boolean isSupported()
    {
        switch (getChartType())
        {
            case DmlChartType.LINE_CHART:
            case DmlChartType.LINE_3_D_CHART:
            case DmlChartType.BUBBLE_CHART:
            case DmlChartType.PIE_CHART:
            case DmlChartType.DOUGHNUT_CHART:
            case DmlChartType.PIE_3_D_CHART:
            case DmlChartType.OF_PIE_CHART:
            case DmlChartType.AREA_CHART:
            case DmlChartType.AREA_3_D_CHART:
            case DmlChartType.BAR_CHART:
            case DmlChartType.BAR_3_D_CHART:
            case DmlChartType.SCATTER_CHART:
            case DmlChartType.STOCK_CHART:
            case DmlChartType.RADAR_CHART:
            case DmlChartType.SURFACE_CHART:
            case DmlChartType.SURFACE_3_D_CHART:
                return true;
            case DmlChartType.CHART_EX_CHART:
                if (isSupportedChartEx())
                    return true;
                else
                    return false;
            default:
                return false;
        }
    }

    /// <summary>
    /// Indicates that the chart contains no series or is not supported.
    /// </summary>
    boolean isEmpty() { return !isSupported() || ((getNonEmptySeries().size() == 0) &&
            (getSeriesType() != ChartSeriesType.WATERFALL) &&
            (getSeriesType() != ChartSeriesType.FUNNEL)); }

    //>>>>>>>> #region  IChartSeriesSource

    /// <summary>
    /// Removes the series at the specified index from the chart.
    /// </summary>
    public void /*IChartSeriesSource.*/removeAt(int index)
    {
        ChartSeries series = getSeries().get(index);
        getSeries().removeAt(index);
        removeSeries(series);
    }

    /// <summary>
    /// Removes all the series from the chart.
    /// </summary>
    public void /*IChartSeriesSource.*/clear()
    {
        for (ChartSeries series : getSeries())
            removeSeries(series);

        getSeries().clear();

        if ((getAxisX() != null) && getAxisX().isCategory() && !getPlotArea().hasSeriesOfXAxis(getAxisX()))
        {
            // Need to clear the X axis date flag otherwise the axis type may not match inserted data.
            setXAxisDateCategoryAttribute(false);
        }
    }

    /// <summary>
    /// Returns a list of chart series of the DML chart.
    /// Do not add/remove chart series to/from this list directly.
    /// </summary>
    public 
    ArrayList<ChartSeries> /*IChartSeriesSource.*/getSeriesList() { return getSeries().getSeriesList(); }

    /// <summary>
    /// Gets the DML chart to which adding series are placed.
    /// </summary>
    public 
    DmlChart /*IChartSeriesSource.*/getDestinationChart() { return this; }

    //<<<<<<<< #endregion  IChartSeriesSource

    /// <summary>
    /// Negative value means the value must be recalculated.
    /// </summary>
    private ArrayList<ChartSeries> mNonEmptySeries;
    private DmlChartSeriesCollection mSeries;
    private ChartSeries mFirstSeries;
    private DmlChartPr mChartPr;
    private DocumentBase mDocument;
    private int mAxIdIndex;
    private DmlChartPlotArea mPlotArea;

    /// <summary>
    /// Negative values means that values should be recalculated.
    /// </summary>
    private int mMaxPointsCount = -1;
    private ChartAxis mAxisX;
    private ChartAxis mAxisY;
    private ChartAxis mAxisZ;
    private /*ChartSeriesType*/int mSeriesType = UNKNOWN_SERIES_TYPE;

    private static final /*ChartSeriesType*/int UNKNOWN_SERIES_TYPE = (/*ChartSeriesType*/int)(-1);

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

