// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/02/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.HashMap;
import java.util.Iterator;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.DictionaryExtension;


/// <summary>
/// This class represents 2.24.3.10 CT_ChartData complex type [MS-ODRAWXML] (except the externalData 
/// element, which contents are stored directly in the <see cref="DmlChartSpace"/> object).
/// It specifies the data source for the chart object.
/// </summary>
class DmlChartDataCollection extends DmlExtensionListSource implements Iterable<DmlChartData>, Cloneable
{
    /// <summary>
    /// Returns a copy of this instance.
    /// </summary>
    DmlChartDataCollection deepClone()
    {
        DmlChartDataCollection lhs = (DmlChartDataCollection)memberwiseClone();

        lhs.mData = new HashMap<Integer, DmlChartData>();
        for (DmlChartData item : (Iterable<DmlChartData>) mData.values())
            lhs.add(item.deepClone());

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    /// <summary>
    /// Returns an enumerator that iterates through data items.
    /// </summary>
    public Iterator<DmlChartData> iterator()
    {
        return mData.values().iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Adds an item to this collection of chart data.
    /// </summary>
    void add(DmlChartData data)
    {
        msDictionary.add(mData, data.getId(), data);
    }

    /// <summary>
    /// Creates a <see cref="DmlChartData"/> instance and adds it to this chart data collection.
    /// </summary>
    DmlChartData add()
    {
        DmlChartData data = new DmlChartData();
        data.setId(getNextDataId());

        add(data);
        return data;
    }

    /// <summary>
    /// Gets the next unused data ID.
    /// </summary>
    private int getNextDataId()
    {
        int nextId = mLastDataId;
        boolean found;

        do
        {
            found = false;
            nextId++;

            for (int id : (Iterable<Integer>) mData.keySet())
            {
                if (id == nextId)
                {
                    found = true;
                    break;
                }
            }
        }
        while (found);

        mLastDataId = nextId;
        return nextId;
    }

    /// <summary>
    /// Removes the data item with the specified ID from this collection.
    /// </summary>
    void remove(int id)
    {
        msDictionary.remove(mData, id);
    }

    /// <summary>
    /// Removes all the data from this chart data collection.
    /// </summary>
    void clear()
    {
        mData.clear();
    }

    /// <summary>
    /// Gets number of items of this chart data collection.
    /// </summary>
    int getCount() { return mData.size(); }

    /// <summary>
    /// Gets an item of this chart data collection.
    /// </summary>
    DmlChartData get(int dataId) { return DictionaryExtension.getValueOrNull(mData, dataId); }

    private HashMap<Integer, DmlChartData> mData = new HashMap<Integer, DmlChartData>();
    private int mLastDataId = -1;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

