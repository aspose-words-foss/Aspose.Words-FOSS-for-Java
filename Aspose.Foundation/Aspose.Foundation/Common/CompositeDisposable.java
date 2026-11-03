// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2023 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IDisposable;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


public final class CompositeDisposable<T extends Object & IDisposable> implements IDisposable
{
    public CompositeDisposable(Iterable<T> items)
    {
        mItems = msArrayList.ctor(items);
    }

    public void /*IDisposable.*/dispose() throws Exception
    {
        for (T item : mItems)
        {
            if (item != null)
                item.dispose();
        }
    }

    private /*final*/ ArrayList<T> mItems;
}

