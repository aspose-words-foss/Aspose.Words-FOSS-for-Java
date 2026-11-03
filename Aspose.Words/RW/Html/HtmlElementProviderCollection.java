// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/10/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Represents collection of <see cref="IHtmlElementProvider"/> elements.
/// </summary>
class HtmlElementProviderCollection implements Iterable<IHtmlElementProvider>
{
    HtmlElementProviderCollection()
    {
        mList = new ArrayList<IHtmlElementProvider>();
    }

    public Iterator<IHtmlElementProvider> iterator()
    {
        return mList.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Adds item into the collection.
    /// </summary>
    int add(IHtmlElementProvider item)
    {
        msArrayList.add(mList, item);
        return mList.size() - 1;
    }

    void insert(int index, IHtmlElementProvider item)
    {
        mList.add(index, item);
    }

    IHtmlElementProvider[] toArray()
    {
        return msArrayList.toArray(mList, new IHtmlElementProvider[0]);
    }

    IHtmlElementProvider get(int index) { return mList.get(index); }

    int getCount() { return mList.size(); }

    private /*final*/ ArrayList<IHtmlElementProvider> mList;
}
