// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/02/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// This class represents the 2.24.3.15 CT_Data complex type [MS-ODRAWXML].
/// It specifies number or string dimensions for chart data.
/// </summary>
class DmlChartData extends DmlExtensionListSource implements Cloneable
{
    /// <summary>
    /// Returns a copy of this instance.
    /// </summary>
    DmlChartData deepClone()
    {
        DmlChartData lhs = (DmlChartData)memberwiseClone();

        lhs.mDataSources = new ArrayList<DmlChartDataSource>();
        for (DmlChartDataSource dataSource : mDataSources)
            lhs.getDataSources().add(dataSource.deepClone());

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    /// <summary>
    /// Gets a chart dimension data source of the specified type.
    /// </summary>
    DmlChartDataSource getDataSource(/*DimensionType*/int type)
    {
        for (DmlChartDataSource dataSource : mDataSources)
        {
            if ((dataSource.getValueRef() != null) && (dataSource.getValueRef().getDimensionType() == type))
                return dataSource;
        }

        return null;
    }

    /// <summary>
    /// Removes a chart dimension data source of the specified type from the chart data.
    /// </summary>
    void removeDataSource(/*DimensionType*/int type)
    {
        for (DmlChartDataSource dataSource : mDataSources)
        {
            if ((dataSource.getValueRef() != null) && (dataSource.getValueRef().getDimensionType() == type))
            {
                mDataSources.remove(dataSource);
                break;
            }
        }
    }

    /// <summary>
    /// Gets a list containing chart dimension data sources that store data of the CT_NumericDimension or
    /// CT_StringDimension complex type (the numDim or strDim element).
    /// </summary>
    ArrayList<DmlChartDataSource> getDataSources() { return mDataSources; }

    /// <summary>
    /// Gets or sets identifier of this data.
    /// </summary>
    int getId() { return mId; }
    void setId(int value) { mId = value; }

    private ArrayList<DmlChartDataSource> mDataSources = new ArrayList<DmlChartDataSource>();
    private int mId;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

