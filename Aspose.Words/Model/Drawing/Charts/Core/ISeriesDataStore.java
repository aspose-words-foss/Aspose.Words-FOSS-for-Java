// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/08/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents an interface for use by the chart data facade classes <see cref="ChartXValueCollection"/>,
/// <see cref="ChartYValueCollection"/> and <see cref="BubbleSizeCollection"/> to retrieve and modify
/// chart data values from/in the series's data store based on <see cref="DmlChartValueCollection"/>
/// instances.
/// </summary>
interface ISeriesDataStore
{
    /// <summary>
    /// Gets the X value as <see cref="ChartXValue"/> instance from the series's internal data store at the
    /// specified index. The required data type is specified by the <paramref name="valueType"/> parameter.
    /// </summary>
    public ChartXValue getXValue(int index, /*ChartXValueType*/int valueType);

    /// <summary>
    /// Gets the Y value as <see cref="ChartYValue"/> instance from the series's internal data store at the
    /// specified index. The required data type is specified by the <paramref name="valueType"/> parameter.
    /// </summary>
    public ChartYValue getYValue(int index, /*ChartYValueType*/int valueType);

    /// <summary>
    /// Gets the bubble size from the series's internal data store at the specified index.
    /// </summary>
    public double getBubbleSize(int index);

    /// <summary>
    /// Changes the X value in the series's internal data store at the specified index, according to the specified
    /// X value.
    /// </summary>
    public void changeXValue(ChartXValue xValue, int index);

    /// <summary>
    /// Changes the Y value in the series's internal data store at the specified index, according to the specified
    /// Y value.
    /// </summary>
    public void changeYValue(ChartYValue yValue, int index);

    /// <summary>
    /// Changes the bubble size in the series's internal data store at the specified index.
    /// </summary>
    public void changeBubbleSize(double bubbleSize, int index);

    /// <summary>
    /// Gets or sets the X value type of the series.
    /// </summary>
    public /*ChartXValueType*/int getXValueType(); public void setXValueType(/*ChartXValueType*/int value);

    /// <summary>
    /// Gets or sets the Y value type of the series.
    /// </summary>
    public /*ChartYValueType*/int getYValueType(); public void setYValueType(/*ChartYValueType*/int value);

    /// <summary>
    /// Gets a flag indicating whether the series supports X values.
    /// </summary>
    public boolean isXValueSupported();

    /// <summary>
    /// Gets a flag indicating whether the series supports Y values.
    /// </summary>
    public boolean isYValueSupported();

    /// <summary>
    /// Gets a flag indicating whether the series supports bubble sizes.
    /// </summary>
    public boolean isBubbleSizeSupported();

    /// <summary>
    /// Gets or sets a number of values in the series.
    /// </summary>
    public int getValueCount(); public void setValueCount(int value);

    /// <summary>
    /// Gets or sets the format code applied to the X values.
    /// </summary>
    public String getXFormatCode(); public void setXFormatCode(String value);

    /// <summary>
    /// Gets or sets the format code applied to the Y values.
    /// </summary>
    public String getYFormatCode(); public void setYFormatCode(String value);

    /// <summary>
    /// Gets or sets the format code applied to the bubble sizes.
    /// </summary>
    public String getBubbleSizeFormatCode(); public void setBubbleSizeFormatCode(String value);
}

