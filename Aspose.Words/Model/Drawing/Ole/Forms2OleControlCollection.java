// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// Represents collection of <see cref="Forms2OleControl"/> objects.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-ole-objects/">Working with Ole Objects</a> documentation article.</para>
/// </summary>
public class Forms2OleControlCollection implements Iterable<Forms2OleControl>
{
    /// <summary>
    /// Gets enumerator.
    /// </summary>
    public Iterator<Forms2OleControl> iterator()
    {
        return mItems.iterator();
    }

    /// <summary>
    /// Gets enumerator.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Adds a specified control to the collection.
    /// </summary>
    Forms2OleControl add(Forms2OleControl control)
    {
        msArrayList.add(mItems, control);
        return control;
    }

    /// <summary>
    /// Gets <see cref="Forms2OleControl" /> object at a specified index.
    /// </summary>
    public Forms2OleControl get(int index) { return mItems.get(index); }

    /// <summary>
    /// Gets count of objects in the collection.
    /// </summary>
    public int getCount() { return mItems.size(); }

    /// <summary>
    /// Gets an internal list of the items.
    /// </summary>
    ArrayList<Forms2OleControl> getItems() { return mItems; }

    private /*final*/ ArrayList<Forms2OleControl> mItems = new ArrayList<Forms2OleControl>();
}

