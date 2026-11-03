// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2015 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.lang.Class;


/// <summary>
/// Represents access to <see cref="IFieldUpdateDataProvider"/>.
/// </summary>
class FieldUpdateDataProvider
{
    FieldUpdateDataProvider(FieldUpdater updater, FieldUpdateDataProviderCollection dataProviderCollection)
    {
        mUpdater = updater;
        mDataProviders = dataProviderCollection;
    }

    /// <summary>
    /// Adds the specified data provider.
    /// </summary>
    void add(IFieldUpdateDataProvider dataProvider)
    {
        mDataProviders.add(dataProvider);
    }

    <T extends IFieldUpdateDataProvider>T ensure(T dataProvider)
    {
        IFieldUpdateDataProvider result = getOfType(dataProvider.getClass());
        if (result != null)
            return (T)result;

        mDataProviders.add(dataProvider);

        return dataProvider;
    }

    /// <summary>
    /// Removes the specified data provider.
    /// </summary>
    void remove(IFieldUpdateDataProvider dataProvider)
    {
        mDataProviders.remove(dataProvider);
    }

    /// <summary>
    /// Returns the first value provided for the specified field.
    /// </summary>
    Constant getValue(Field field) throws Exception
    {
        // Treat display context as a data provider either.
        if (mUpdater.getDisplayContext() != null)
        {
            Constant value = mUpdater.getDisplayContext().getFieldValue(field);
            if (value != null)
                return value;
        }

        return mDataProviders.getValue(field);
    }

    /// <summary>
    /// Returns the first data object provided for the specified field.
    /// </summary>
    Object getData(Field field)
    {
        return mDataProviders.getData(field);
    }

    IFieldUpdateDataProvider getOfType(Class type)
    {
        IFieldUpdateDataProvider result = null;

        for (IFieldUpdateDataProvider provider : mDataProviders)
            if (provider.getClass() == type)
                result = provider;

        return result;
    }

    private /*final*/ FieldUpdater mUpdater;
    private /*final*/ FieldUpdateDataProviderCollection mDataProviders;
}

