// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/07/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.IntToObjDictionary;
import com.aspose.StringToObjDictionary;
import java.util.Collections;
import java.util.Comparator;


/// <summary>
/// Represents 5.7.2.146 plotArea (Plot Area) element.
/// This element specifies the plot area of the chart.
/// </summary>
class DmlChartPlotArea extends DmlExtensionListSource implements IChartSeriesSource, Cloneable
{
    DmlChartPlotArea(DmlChartFormat chartFormat)
    {
        mParentChartFormat = chartFormat;
    }

    void warn(/*WarningType*/int warningType, String message)
    {
        mParentChartFormat.warn(warningType, message);
    }

    /// <summary>
    /// Adds axis to the collection.
    /// Axis can be gotten by its axid.
    /// </summary>
    void addAxis(ChartAxis axis)
    {
        axis.setPlotArea(this);
        msArrayList.add(mAxes, axis);
        mAxesMap.set(axis.getAxId(), axis);
    }

    /// <summary>
    /// Remove the axis with the specified ID from this plot area.
    /// </summary>
    void removeAxis(int axisId)
    {
        ChartAxis axis = mAxesMap.get(axisId);
        if (axis == null)
            return;

        mAxesMap.remove(axisId);
        mAxes.remove(axis);
    }

    /// <summary>
    /// Remove all axes from this plot area.
    /// </summary>
    void clearAxes()
    {
        mAxes.clear();
        mAxesMap.clear();
    }

    /// <summary>
    /// Adds chart to the collection. There might be more than one chart on one plot area.
    /// </summary>
    void addChart(DmlChart chart)
    {
        chart.setPlotArea(this);
        msArrayList.add(mCharts, chart);
    }

    /// <summary>
    /// Returns axis that corresponds the specified id.
    /// </summary>
    ChartAxis getAxis(int axId)
    {
        return mAxesMap.get(axId);
    }

    /// <summary>
    /// Returns axis that crosses the specified one.
    /// </summary>
    ChartAxis getCrossAxis(int axId, int supposedCrossAxIdX)
    {
        // Get supposed cross axis and check whether it actually crosses the specified axis.
        ChartAxis crossAx = getAxis(supposedCrossAxIdX);
        if ((crossAx == null) || (crossAx.getCrossAx() == axId))
            return crossAx;

        for (ChartAxis axis : mAxes)
        {
            if (axis.getCrossAx() == axId)
                return axis;
        }

        return crossAx;
    }

    /// <summary>
    /// Gets or creates a secondary X axis.
    /// </summary>
    ChartAxis getOrCreateSecondaryXAxis()
    {
        // MS Word treats the first X axis and the first Y axis as primary. And it expects that they are linked
        // using the CrossAxis property.
        // The next two axes are secondary and must also be linked.
        // If only one secondary axis is needed: X or Y, the other must exist and be hidden.
        // A combo chart cannot have 3D DML charts, so there is no secondary Z axis.

        ChartAxis secondaryXAxis = getSecondaryXAxis();
        if (secondaryXAxis != null)
            return secondaryXAxis;

        Debug.assertTrue(getPrimaryXAxis() != null);

        secondaryXAxis = getPrimaryXAxis().deepClone();
        secondaryXAxis.setAxId(generateAxisId());
        DmlChartUtil.setXAxisOppositePosition(secondaryXAxis, secondaryXAxis.getActualAxisPosition());

        ChartAxis secondaryYAxis = getSecondaryYAxis();
        if (secondaryYAxis != null)
            linkAxes(secondaryXAxis, secondaryYAxis);

        msArrayList.add(mAxes, secondaryXAxis);
        return secondaryXAxis;
    }

    /// <summary>
    /// Gets or creates a secondary Y axis.
    /// </summary>
    ChartAxis getOrCreateSecondaryYAxis()
    {
        ChartAxis secondaryYAxis = getSecondaryYAxis();
        if (secondaryYAxis != null)
            return secondaryYAxis;

        Debug.assertTrue(getPrimaryYAxis() != null);

        secondaryYAxis = getPrimaryYAxis().deepClone();
        secondaryYAxis.setAxId(generateAxisId());
        DmlChartUtil.setYAxisOppositePosition(secondaryYAxis, secondaryYAxis.getActualAxisPosition());

        ChartAxis secondaryXAxis = getSecondaryXAxis();
        if (secondaryXAxis != null)
            linkAxes(secondaryXAxis, secondaryYAxis);

        msArrayList.add(mAxes, secondaryYAxis);
        return secondaryYAxis;
    }

    DmlChartPlotArea deepClone()
    {
        DmlChartPlotArea lhs = (DmlChartPlotArea)memberwiseClone();

        if (mDataTable != null)
            lhs.mDataTable = mDataTable.deepClone();

        if (mLayout != null)
            lhs.mLayout = mLayout.deepClone();

        if (mSpPr != null)
            lhs.mSpPr = mSpPr.deepClone();

        if (mCharts != null)
        {
            ArrayList<DmlChart> chartList = new ArrayList<DmlChart>();
            for (DmlChart chart : mCharts)
            {
               DmlChart cloned = chart.deepClone();
               cloned.setPlotArea(lhs);
               msArrayList.add(chartList, cloned);
               lhs.mCharts = chartList;
            }
        }

        if (mSurface != null)
            lhs.mSurface = mSurface.deepClone();

        lhs.mRegionExtensions = cloneExtensions(mRegionExtensions);

        lhs.mAxes = new ArrayList<ChartAxis>(mAxes.size());
        lhs.mAxesMap = new IntToObjDictionary<ChartAxis>();
        for (ChartAxis axis : mAxes)
        {
            ChartAxis clonedAxis = axis.deepClone();
            lhs.addAxis(clonedAxis);
            int chartIndex = mCharts.indexOf(axis.getChart());
            clonedAxis.setChart((chartIndex < 0) ? null : lhs.mCharts.get(chartIndex));
        }

        lhs.setExtensions(cloneExtensions());

        lhs.mSeriesList = null;

        return lhs;
    }

    /// <summary>
    /// Adds the series to the plot area.
    /// </summary>
    void addSeries(ChartSeries series)
    {
        // On opening in MS Word, series are displayed on a chart by the order as they are located in XML markup
        // (i.e. by the order of DmlChart objects in DmlChartPlotArea.Charts and then by the order of series in
        // DmlChart.Series). In the list that is opened by clicking 'Change Chart Series Type', series are ordered
        // by the Order property value.
        series.setIndex(getMaxSeriesIndex() + 1);
        series.setOrder(getMaxSeriesOrder() + 1);

        if (mSeriesList != null)
            msArrayList.add(mSeriesList, series);

        series.getChart().getSeries().add(series);
    }

    /// <summary>
    /// Removes the specified series from the series list.
    /// </summary>
    void removeSeries(ChartSeries series)
    {
        if (mSeriesList != null)
            mSeriesList.remove(series);
    }

    /// <summary>
    /// Fills in the <see cref="ChartAxis.Direction"/> property of all axes of the plot area.
    /// </summary>
    void initAxisDirection()
    {
        for (DmlChart chart : mCharts)
        {
            if (chart.getChartPr().isPropertySpecified(DmlChartAttrs.AX_ID_X))
            {
                int xAxisId = (/*int*/Integer)chart.getChartPr().getProperty(DmlChartAttrs.AX_ID_X);
                ChartAxis xAxis = getAxis(xAxisId);
                if ((xAxis != null) && (xAxis.getDirection() == AxisDirection.UNSPECIFIED))
                    xAxis.setDirection(AxisDirection.X);
            }

            if (chart.getChartPr().isPropertySpecified(DmlChartAttrs.AX_ID_Y))
            {
                int yAxisId = (/*int*/Integer)chart.getChartPr().getProperty(DmlChartAttrs.AX_ID_Y);
                ChartAxis yAxis = getAxis(yAxisId);
                if ((yAxis != null) && (yAxis.getDirection() == AxisDirection.UNSPECIFIED))
                    yAxis.setDirection(AxisDirection.Y);
            }

            if (chart.getChartPr().isPropertySpecified(DmlChartAttrs.AX_ID_Z))
            {
                int zAxisId = (/*int*/Integer)chart.getChartPr().getProperty(DmlChartAttrs.AX_ID_Z);
                ChartAxis zAxis = getAxis(zAxisId);
                if ((zAxis != null) && (zAxis.getDirection() == AxisDirection.UNSPECIFIED))
                    zAxis.setDirection(AxisDirection.Z);
            }
        }

        // Let's use axis position as a fallback.
        for (ChartAxis axis : getAxes())
        {
            if (axis.getDirection() != AxisDirection.UNSPECIFIED)
                continue;

            axis.setDirection(((axis.getAxPos() == AxisPosition.BOTTOM) || (axis.getAxPos() == AxisPosition.TOP))
                ? AxisDirection.X
                : AxisDirection.Y);
        }
    }

    /// <summary>
    /// Gets a flag indicating whether the plot area contains series related to the specified X axis.
    /// </summary>
    boolean hasSeriesOfXAxis(ChartAxis axisX)
    {
        for (DmlChart chart : getCharts())
        {
            if (chart.hasAxis() && (chart.getAxisX() == axisX) && (chart.getSeries().getCount() > 0))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Calculates the height correction factor for 3D charts.
    /// </summary>
    /// <remarks>
    /// This experimental method is trying to repeat the MS Office behavior.
    /// </remarks>
    /// <param name="chart">the specified chart</param>
    /// <param name="heightFactor">the base height factor</param>
    /// <returns>the height correction factor</returns>
    private float getHeightCorrectionCoeff(DmlChart chart, float heightFactor)
    {
        float factor = 1f;
        int countCategories = 0;

        if (!chart.isEmpty())
            for (ChartSeries series : chart.getSeries())
                if (countCategories < series.getValueCount())
                    countCategories = series.getValueCount();

        //If the number of categories is 1, use a special coefficient.
        if (countCategories == 1)
            return 1f / (2f * heightFactor);

        IDmlChart3D chart3D = (IDmlChart3D)chart;

        if ((chart3D.getAxZ() != null) && !chart.isSurface3DChart())
        {
            // The 3DAreaChart behavior is slightly different from 3DLineChart and 3DBarChart.
            // This can be seen clearly on a chart with zero values of RotX and RotY.
            factor = (chart.getChartType() == DmlChartType.AREA_3_D_CHART)
                ? getHeightFactorArea3DChart(chart, heightFactor)
                : getHeightFactor3D(chart, heightFactor, factor);
        }

        return factor;
    }

    /// <summary>
    /// Calculates the height factor for 3D charts.
    /// </summary>
    private float getHeightFactor3D(DmlChart chart, float heightFactor, float factor)
    {
        if (mParentChartFormat.getView3D().getRotX() != 0)
        {
            if (chart.getSeries().getCount() == 1)
                factor = (heightFactor < 0.85) ? 0.67f : 0.49f;
            else
                factor = (heightFactor < 0.84) ? 0.86f : 0.55f;
        }

        return factor;
    }

    /// <summary>
    /// Calculates the height factor for Area3D charts.
    /// </summary>
    private static float getHeightFactorArea3DChart(DmlChart chart, float heightFactor)
    {
        float factor;

        if (chart.getSeries().getCount() == 1)
            factor = (heightFactor < 0.85) ? 0.67f : 0.45f;
        else
            factor = (heightFactor < 0.83) ? 0.85f : 0.55f;

        return factor;
    }

    /// <summary>
    /// Generates an unique axis ID.
    /// </summary>
    private int generateAxisId()
    {
        int id = mLastAxisId;
        boolean found;

        do
        {
            found = false;
            id++;

            for (ChartAxis axis : mAxes)
            {
                if (axis.getAxId() == id)
                {
                    found = true;
                    break;
                }
            }
        }
        while (found);

        mLastAxisId = id;
        return id;
    }

    /// <summary>
    /// Links the specified X and Y axes.
    /// </summary>
    private static void linkAxes(ChartAxis xAxis, ChartAxis yAxis)
    {
        xAxis.setCrossAx(yAxis.getAxId());
        xAxis.setCrossAxis(yAxis);
        yAxis.setCrossAx(xAxis.getAxId());
        yAxis.setCrossAxis(xAxis);
    }

    /// <summary>
    /// Finds the first or second axis of the specified direction depending on the <paramref name="returnSecond"/>
    /// parameter.
    /// </summary>
    private ChartAxis findAxis(/*AxisDirection*/int direction, boolean returnSecond)
    {
        boolean isFirst = true;

        for (ChartAxis axis : getAxes())
        {
            if (axis.getDirection() == direction)
            {
                if (!returnSecond || !isFirst)
                    return axis;

                isFirst = false;
            }

            Debug.assertTrue(axis.getDirection() != AxisDirection.UNSPECIFIED);
        }

        return null;
    }

    /// <summary>
    /// Returns true if legend should be rendered for each data point.
    /// </summary>
    boolean getRenderLegendForDataPoints()
    {
        // Legend is rendered for each data point if there is only one chart and "RenderLegendForDataPoints"
        // property of the first series is "true".
        if (getCharts().size() == 1)
            return getFirstChart().getRenderLegendForDataPoints();

        return false;
    }

    /// <summary>
    /// Returns list of charts of the current plot area.
    /// Do not add items to this list, use <see cref="AddChart"/> method instead.
    /// </summary>
    ArrayList<DmlChart> getCharts() { return mCharts; }

    /// <summary>
    /// Returns the list of not empty charts of the current plot area.
    /// </summary>
    ArrayList<DmlChart> getNonEmptyCharts()
    {
        if (mNonEmptyCharts == null)
        {
            mNonEmptyCharts = new ArrayList<DmlChart>();
            for (DmlChart chart : getCharts())
            {
                if (!chart.isEmpty())
                    msArrayList.add(mNonEmptyCharts, chart);
            }
        }

        return mNonEmptyCharts;
    }

    DmlChart getFirstChart()
    {
        if (getCharts().size() > 0)
            return getCharts().get(0);

        return null;
    }

    DmlChartFormat getParentChartFormat() { return mParentChartFormat; }

    /// <summary>
    /// Sets the parent chart format of this plot area.
    /// </summary>
    void setChartFormat(DmlChartFormat chartFormat)
    {
        mParentChartFormat = chartFormat;
        if (mDataTable != null)
            mDataTable.setPlotArea(chartFormat.getPlotArea());
    }

    /// <summary>
    /// Gets a list of chart axes of this plot area.
    /// </summary>
    ArrayList<ChartAxis> getAxes() { return mAxes; }

    ChartDataTable getDataTable() { return mDataTable; }
    void setDataTable(ChartDataTable value) { mDataTable = value; }

    DmlChartManualLayout getLayout() { return mLayout; }
    void setLayout(DmlChartManualLayout value) { mLayout = value; }

    DmlChartSpPr getSpPr()
    {
        if (mSpPr == null)
            mSpPr = new DmlChartSpPr();

        return mSpPr;
    }

    /// <summary>
    /// Returns true if plot area represent surface 3D chart.
    /// </summary>
    boolean isSurface3D() { return (getFirstChart() != null) && getFirstChart().isSurface3DChart(); }

    /// <summary>
    /// Returns true if plot area contains a pie chart.
    /// </summary>
    boolean hasPieChart()
    {
        for (DmlChart chart : getCharts())
            if (!chart.isEmpty() && chart.isPieChart() && (chart.getChartType() != DmlChartType.OF_PIE_CHART))
                    return true;

        return false;
    }

    /// <summary>
    /// Returns true if the plot area has axis.
    /// </summary>
    boolean hasAxis() { return (mAxes.size() > 0); }

    /// <summary>
    /// Provides access to the primary X axis of the plot area.
    /// </summary>
    ChartAxis getPrimaryXAxis()
    {
        // MS Word treats the first X axis as primary. And it expects that the first X axis and the first Y axis
        // are linked using the CrossAxis property, otherwise there may be issues with the axes in MS Word.
        // So, just get the first X axis.
        return findAxis(AxisDirection.X, false);
    }

    /// <summary>
    /// Provides access to the primary Y axis of the plot area.
    /// </summary>
    ChartAxis getPrimaryYAxis()
    {
        // MS Word treats the first Y axis as primary.
        return findAxis(AxisDirection.Y, false);
    }

    /// <summary>
    /// Provides access to the Z axis of the plot area.
    /// </summary>
    /// <dev>
    /// It seems in contrast with X and Y axes, a chart can contain only one Z axis.
    /// </dev>
    ChartAxis getZAxis()
    {
        return findAxis(AxisDirection.Z, false);
    }

    /// <summary>
    /// Provides access to the secondary X axis of the plot area.
    /// </summary>
    ChartAxis getSecondaryXAxis()
    {
        // The first X and first Y axes are primary axes, they are lined using the CrossAxis property.
        // The other two axes are X and Y secondary axes. They must be linked to each other too.
        // If it is needed only X or Y secondary axis, the other must exist and be hidden.
        // Combo chart cannot contain 3D DML charts, so it looks like there cannot be a secondary Z axis.
        return findAxis(AxisDirection.X, true);
    }

    /// <summary>
    /// Provides access to the secondary Y axis of the plot area.
    /// </summary>
    ChartAxis getSecondaryYAxis()
    {
        // See the comment in SecondaryXAxis.
        return findAxis(AxisDirection.Y, true);
    }

    /// <summary>
    /// Gets or sets properties of the canvas on which the series is plotted.
    /// </summary>
    /// <dev>
    /// Stores data of the plotSurface element of the 2.24.3.66 CT_PlotSurface complex type [MS-ODRAWXML].
    /// </dev>
    DmlChartSurface getSurface() { return mSurface; }
    void setSurface(DmlChartSurface value) { mSurface = value; }

    /// <summary>
    /// Gets or sets extensions of plot area region.
    /// </summary>
    /// <dev>
    /// Stores data of the extLst element of the 2.24.3.65 CT_PlotAreaRegion complex type [MS-ODRAWXML].
    /// </dev>
    StringToObjDictionary<DmlExtension> getRegionExtensions() { return mRegionExtensions; }
    void setRegionExtensions(StringToObjDictionary<DmlExtension> value) { mRegionExtensions = value; }

    /// <summary>
    /// Gets the maximum value of <see cref="ChartSeries.Index"/> property of all series in this plot area.
    /// </summary>
    private int getMaxSeriesIndex()
    {
        int maxIndex = -1;

        for (DmlChart chart : getCharts())
        {
            for (ChartSeries series : chart.getSeries())
            {
                if (maxIndex < series.getIndex())
                    maxIndex = series.getIndex();
            }
        }

        return maxIndex;
    }

    /// <summary>
    /// Gets the maximum value of <see cref="ChartSeries.Order"/> property of all series in this plot area.
    /// </summary>
    private int getMaxSeriesOrder()
    {
        int maxOrder = -1;

        for (DmlChart chart : getCharts())
        {
            for (ChartSeries series : chart.getSeries())
            {
                if (maxOrder < series.getOrder())
                    maxOrder = series.getOrder();
            }
        }

        return maxOrder;
    }

    /// <summary>
    /// Specifies that the plot area size shall determine the size of the plot area,
    /// not including the tick marks and axis labels.
    /// </summary>
    boolean isInner() { return ((getLayout() != null) && (getLayout().getLayoutTarget() == LayoutTarget.INNER)); }

    //>>>>>>>> #region  IChartSeriesSource

    /// <summary>
    /// Removes the series at the specified index from the chart.
    /// </summary>
    public void /*IChartSeriesSource.*/removeAt(int index)
    {
        ChartSeries series = ((IChartSeriesSource)this).getSeriesList().get(index);
        mSeriesList.remove(index);
        series.getChart().getSeries().remove(series);
    }

    /// <summary>
    /// Removes all the series from the chart.
    /// </summary>
    public void /*IChartSeriesSource.*/clear()
    {
        if (mSeriesList != null)
            mSeriesList.clear();

        DmlChartSpace chartSpace = mParentChartFormat.getDmlChartSpace();
        if (chartSpace.isChartEx())
            chartSpace.getData().clear();

        for (DmlChart chart : getCharts())
        {
            chart.getSeries().clear();

            // Need to clear the X axis date flag otherwise the axis type may not match inserted data.
            chart.setXAxisDateCategoryAttribute(false);
        }
    }

    /// <summary>
    /// Returns a list of all chart series of the current plot area.
    /// Do not add/remove chart series to/from this list directly.
    /// </summary>
    public ArrayList<ChartSeries> /*IChartSeriesSource.*/getSeriesList()
    {
        if (mSeriesList == null)
        {
            mSeriesList = new ArrayList<ChartSeries>();
            for (DmlChart chart : mCharts)
                msArrayList.addRange(mSeriesList, chart.getSeries());

            Collections.sort(mSeriesList, new ChartSeriesComparerByIndex());
        }

        return mSeriesList;
    }

    /// <summary>
    /// Gets the DML chart to which adding series are placed.
    /// </summary>
    public DmlChart /*IChartSeriesSource.*/getDestinationChart() { return getFirstChart(); }

    //<<<<<<<< #endregion  IChartSeriesSource

    private ArrayList<DmlChart> mCharts = new ArrayList<DmlChart>();
    private ArrayList<DmlChart> mNonEmptyCharts;
    private IntToObjDictionary<ChartAxis> mAxesMap = new IntToObjDictionary<ChartAxis>();
    private ArrayList<ChartAxis> mAxes = new ArrayList<ChartAxis>();
    private ChartDataTable mDataTable;
    private DmlChartManualLayout mLayout;
    private DmlChartSpPr mSpPr;
    private DmlChartFormat mParentChartFormat;
    private DmlChartSurface mSurface;
    private StringToObjDictionary<DmlExtension> mRegionExtensions;
    private ArrayList<ChartSeries> mSeriesList;
    private int mLastAxisId;

    /// <summary>
    /// Compares chart series by <see cref="ChartSeries.Index"/>.
    /// </summary>
    private static class ChartSeriesComparerByIndex implements Comparator<ChartSeries>
    {
        public int /*IComparer<ChartSeries>.*/compare(ChartSeries series1, ChartSeries series2)
        {
            return series1.getIndex() - series2.getIndex();
        }
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

