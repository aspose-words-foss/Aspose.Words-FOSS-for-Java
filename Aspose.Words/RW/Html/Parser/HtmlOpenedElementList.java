// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// List of still opened HTML elements used by the HTML tree constructor.
/// </summary>
/// <remarks>
/// This data structure is called a stack by the HTML Specification, but in some situations it is accessed as a list.
/// The most recently added elements comes last.
/// </remarks>
class HtmlOpenedElementList
{
    void add(HtmlElementNode element)
    {
        Debug.assertTrue(element != null);
        msArrayList.add(mElements, element);
    }

    HtmlElementNode getLast()
    {
        Debug.assertTrue(mElements.size() > 0);
        return mElements.get(mElements.size() - 1);
    }

    void removeLast()
    {
        Debug.assertTrue(mElements.size() > 0);
        mElements.remove(mElements.size() - 1);
    }

    void removeRange(int startIndex)
    {
        Debug.assertTrue(startIndex >= 0);
        Debug.assertTrue(startIndex < mElements.size());
        msArrayList.removeRange(mElements, startIndex, mElements.size() - startIndex);
    }

    void remove(HtmlElementNode element)
    {
        Debug.assertTrue(element != null);
        mElements.remove(element);
    }

    void insert(int index, HtmlElementNode element)
    {
        Debug.assertTrue(index >= 0);
        Debug.assertTrue(index <= mElements.size());
        Debug.assertTrue(element != null);
        mElements.add(index, element);
    }

    void clear()
    {
        mElements.clear();
    }

    boolean isEmpty() { return mElements.size() == 0; }

    int indexOf(HtmlElementNode element)
    {
        return mElements.indexOf(element);
    }

    boolean contains(HtmlElementNode element)
    {
        Debug.assertTrue(element != null);
        return mElements.contains(element);
    }

    int getCount() { return mElements.size(); }

    HtmlElementNode get(int index) { return mElements.get(index); }
    void set(int index, HtmlElementNode value)
    {
        Debug.assertTrue(value != null);
        mElements.set(index, value);
    }

    private /*final*/ ArrayList<HtmlElementNode> mElements = new ArrayList<HtmlElementNode>();
}

