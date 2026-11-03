// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/03/2023 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;
import java.util.ArrayList;


/// <summary>
/// Represents a collection of chart axes.
/// </summary>
public class ChartAxisCollection implements Iterable<ChartAxis>
{
    /// <summary>
    /// Creates an instance of this class.
    /// </summary>
    ChartAxisCollection(DmlChartSpace chartSpace)
    {
        mChartSpace = chartSpace;
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<ChartAxis> iterator()
    {
        return getAxes().iterator();
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Gets the axis at the specified index.
    /// </summary>
    public ChartAxis get(int index) { return getAxes().get(index); }

    /// <summary>
    /// Gets the number of axes in this collection.
    /// </summary>
    public int getCount() { return getAxes().size(); }

    /// <summary>
    /// Gets the underlying list of the axes.
    /// </summary>
    private ArrayList<ChartAxis> getAxes() { return mChartSpace.getChartFormat().getPlotArea().getAxes(); }

    private /*final*/ DmlChartSpace mChartSpace;
}

