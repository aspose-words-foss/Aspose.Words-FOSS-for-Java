// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2024 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Provides a list of chart series to be used in <see cref="ChartSeriesCollection"/> as a source.
/// </summary>
interface IChartSeriesSource
{
    /// <summary>
    /// Removes a <see cref="ChartSeries"/> at the specified index.
    /// </summary>
    public void removeAt(int index);

    /// <summary>
    /// Removes all <see cref="ChartSeries"/> from this series source.
    /// </summary>
    public void clear();

    /// <summary>
    /// Gets a list of series.
    /// </summary>
    public ArrayList<ChartSeries> getSeriesList();

    /// <summary>
    /// Gets the DML chart to which adding series are placed.
    /// </summary>
    public DmlChart getDestinationChart();
}

