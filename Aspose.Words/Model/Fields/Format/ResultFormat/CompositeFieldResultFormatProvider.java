// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/01/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.lang.Class;
import java.util.ArrayList;


/// <summary>
/// An implementation of <see cref="IFieldResultFormatProvider"/> that contains a collection of providers
/// and uses all of them one by one to build <see cref="CompositeFieldResultFormatApplier"/>.
/// </summary>
class CompositeFieldResultFormatProvider implements IFieldResultFormatProvider
{
    public Inline /*IFieldResultFormatProvider.*/getSourceNode()
    {
        if (isEmpty())
            return null;

        for (int i = mProviders.size() - 1; i >= 0; i--)
        {
            Inline node = mProviders.get(i).getSourceNode();
            if (node != null)
                return node;
        }

        return null;
    }

    public IFieldResultFormatApplier /*IFieldResultFormatProvider.*/getFormatApplier() throws Exception
    {
        CompositeFieldResultFormatApplier applier = new CompositeFieldResultFormatApplier();

        for (IFieldResultFormatProvider provider : mProviders)
            applier.addApplier(provider.getFormatApplier());

        return applier;
    }

    /// <summary>
    /// Inserts <paramref name="provider"/> at the beginning.
    /// Does not insert provider if it already exists.
    /// </summary>
    void insertProvider(IFieldResultFormatProvider provider)
    {
        if (hasProvider(provider))
            return;

        mProviders.add(0, provider);
    }

    /// <summary>
    /// Appends <paramref name="provider"/> to the end.
    /// Does not append provider if it already exists.
    /// </summary>
    void appendProvider(IFieldResultFormatProvider provider)
    {
        if (hasProvider(provider))
            return;

        msArrayList.add(mProviders, provider);
    }

    boolean isEmpty() { return mProviders.size() == 0; }

    private boolean hasProvider(IFieldResultFormatProvider provider)
    {
        Class providerType = provider.getClass();

        for (IFieldResultFormatProvider existedProvider : mProviders)
            if (existedProvider.getClass() == providerType)
                return true;

        return false;
    }

    private /*final*/ ArrayList<IFieldResultFormatProvider> mProviders = new ArrayList<IFieldResultFormatProvider>();
}

