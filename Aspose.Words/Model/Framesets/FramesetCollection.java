// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/08/2010 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// Represents a collection of instances of the <see cref="Frameset"/> class.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/programming-with-documents/">Programming with Documents</a> documentation article.</para>
/// </summary>
public class FramesetCollection implements Iterable<Frameset>
{
    /// <summary>
    /// Gets the number of frames or frames pages contained in the collection.
    /// </summary>
    public int getCount() { return mItems.size(); }

    /// <summary>
    /// Gets a frame or frames page at the specified index.
    /// </summary>
    public Frameset get(int index) { return mItems.get(index); }

    /// <summary>
    /// Returns an enumerator that iterates through the collection.
    /// </summary>
    public Iterator<Frameset> iterator()
    {
        return mItems.iterator();
    }

    /// <summary>
    /// Returns a dictionary <ms>enumerator</ms><java>iterator</java><cpp>enumerator</cpp> object that can be used to iterate over all items in the collection.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Adds Frame object to collection.
    /// </summary>
    /// <param name="frame"></param>
    void add(Frameset frame)
    {
        msArrayList.add(mItems, frame);
    }

    private /*final*/ ArrayList<Frameset> mItems = new ArrayList<Frameset>();
}

