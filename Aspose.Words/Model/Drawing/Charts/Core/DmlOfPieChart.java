// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Class represents OfPie Chart.
/// 5.7.2.127 ofPieChart (Pie of Pie or Bar of Pie Charts)
/// </summary>
class DmlOfPieChart extends DmlPieChart
{
    /*override*/ /*DmlChartType*/int getChartType() { return DmlChartType.OF_PIE_CHART; }

    /// <summary>
    /// Gets the type of series that such DML chart contains by default.
    /// </summary>
    /*override*/ /*ChartSeriesType*/int getDefaultSeriesType()
    {
        switch (getChartPr().getOfPieType())
        {
            case OfPieType.BAR:
                return ChartSeriesType.PIE_OF_BAR;
            case OfPieType.PIE:
            default:
                return ChartSeriesType.PIE_OF_PIE;
        }
    }

    /// <summary>
    /// Specifies whether this chart is pie of pie or bar of pie.
    /// </summary>
    /*OfPieType*/int getOfPieType() { return getChartPr().getOfPieType(); }
    void setOfPieType(/*OfPieType*/int value) { getChartPr().setOfPieType(value); }

    /// <summary>
    /// Specifies the space between bar or column clusters, as a percentage of the bar or column width.
    /// </summary>
    int getGapWidth() { return (/*int*/Integer)getChartPr().getProperty(DmlChartAttrs.GAP_WIDTH); }
    void setGapWidth(int value) { getChartPr().setProperty(DmlChartAttrs.GAP_WIDTH, value); }

    /// <summary>
    /// Specifies how to determine which data points are in the second pie or bar on a pie of pie or bar of pie chart.
    /// </summary>
    /*SplitType*/int getSplitType() { return (/*SplitType*/Integer)getChartPr().getProperty(DmlChartAttrs.SPLIT_TYPE); }
    void setSplitType(/*SplitType*/int value) { getChartPr().setProperty(DmlChartAttrs.SPLIT_TYPE, value); }

    /// <summary>
    /// Specifies a value that shall be used to determine which data points 
    /// are in the second pie or bar on a pie of pie or bar of pie chart.
    /// </summary>
    double getSplitPos() { return (/*double*/Double)getChartPr().getProperty(DmlChartAttrs.SPLIT_POS); }
    void setSplitPos(double value) { getChartPr().setProperty(DmlChartAttrs.SPLIT_POS, value); }

    /// <summary>
    /// Contains the custom split information for a pie-of-pie or bar-of-pie chart with a custom split type.
    /// </summary>
    int[] getCustSplit() { return (int[])getChartPr().getProperty(DmlChartAttrs.CUST_SPLIT); }
    void setCustSplit(int[] value) { getChartPr().setProperty(DmlChartAttrs.CUST_SPLIT, value); }

    /// <summary>
    /// specifies the size of the second pie or bar of a pie of pie chart or a bar of pie chart, 
    /// as a percentage of the size of the first pie.
    /// </summary>
    int getSecondPieSize() { return (/*int*/Integer)getChartPr().getProperty(DmlChartAttrs.SECOND_PIE_SIZE); }
    void setSecondPieSize(int value) { getChartPr().setProperty(DmlChartAttrs.SECOND_PIE_SIZE, value); }

    DmlChartSpPr getSerLines() { return (DmlChartSpPr)getChartPr().getProperty(DmlChartAttrs.SER_LINES); }
}

