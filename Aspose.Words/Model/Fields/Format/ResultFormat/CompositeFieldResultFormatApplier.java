// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/01/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// An implementation of <see cref="IFieldResultFormatApplier"/> that contains a collection of appliers
/// and applies all of them one by one.
/// </summary>
class CompositeFieldResultFormatApplier implements IFieldResultFormatApplier
{
    public void /*IFieldResultFormatApplier.*/applyFormat(NodeRange result) throws Exception
    {
        for (IFieldResultFormatApplier applier : mAppliers)
            applier.applyFormat(result);
    }

    void addApplier(IFieldResultFormatApplier applier)
    {
        msArrayList.add(mAppliers, applier);
    }

    private /*final*/ ArrayList<IFieldResultFormatApplier> mAppliers = new ArrayList<IFieldResultFormatApplier>();
}
