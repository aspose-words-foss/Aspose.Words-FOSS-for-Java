// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/11/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import com.aspose.EmptyEnumerator;


/// <summary>
/// Represents a collection of <see cref="IFieldUpdateDataProvider"/> instances.
/// </summary>
class FieldUpdateDataProviderCollection implements Iterable<IFieldUpdateDataProvider>
{
    /// <summary>
    /// Adds the specified data provider to the collection.
    /// </summary>
    void add(IFieldUpdateDataProvider dataProvider)
    {
        if (!hasDataProviders())
            mDataProviders = new ArrayList<IFieldUpdateDataProvider>(); // Create on the first demand.

        msArrayList.add(mDataProviders, dataProvider);
    }

    /// <summary>
    /// Removes the specified data provider from the collection.
    /// </summary>
    void remove(IFieldUpdateDataProvider dataProvider)
    {
        if (!hasDataProviders())
            return;

        mDataProviders.remove(dataProvider);

        if (mDataProviders.size() == 0)
            mDataProviders = null;
    }

    /// <summary>
    /// Iterates over collected data providers and returns the first value provided for the specified field.
    /// </summary>
    Constant getValue(Field field) throws Exception
    {
        if (hasDataProviders())
        {
            for (IFieldUpdateDataProvider dataProvider : mDataProviders)
            {
                Constant value = dataProvider.getValue(field);
                if (value != null)
                    return value;
            }
        }

        return null;
    }

    /// <summary>
    /// Iterates over collected data providers and returns the first data object provided for the specified field.
    /// </summary>
    Object getData(Field field)
    {
        if (hasDataProviders())
        {
            for (IFieldUpdateDataProvider dataProvider : mDataProviders)
            {
                Object data = dataProvider.getData(field);
                if (data != null)
                    return data;
            }
        }

        return null;
    }

    public Iterator<IFieldUpdateDataProvider> iterator()
    {
        if ( hasDataProviders() )
            return mDataProviders.iterator();
        else
            return EmptyEnumerator.getInstance();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private boolean hasDataProviders() { return (mDataProviders != null); }

    private ArrayList<IFieldUpdateDataProvider> mDataProviders;
}

