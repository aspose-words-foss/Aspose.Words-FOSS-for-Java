// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/02/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.Debug;
import java.util.ArrayList;


/// <summary>
/// The collection of pages of a <see cref="MultiPageControl"/>.
/// </summary>
/// <remarks>
/// The pages in the collection are represented as <see cref="FormControl"/> objects.
/// </remarks>
class PageCollection
{
    /// <summary>
    /// Creates collection of pages based on a source collection of Forms 2.0 controls.
    /// </summary>
    PageCollection(Forms2OleControlCollection source)
    {
        mItems = source.getItems();
    }

    /// <summary>
    /// Adds a page to the collection.
    /// </summary>
    FormControl add(FormControl page) throws Exception
    {
        // MultiPage should have TabStrip control, that organizes tabs on it.
        TabStripControl tabStripControl = getTabStripControl();
        if (tabStripControl == null)
        {
            tabStripControl = new TabStripControl("");
            msArrayList.add(mItems, tabStripControl);
        }

        // Add new tab.
        TabStripTab tab = new TabStripTab(((IEmbeddedObject)page).getName());
        tabStripControl.getTabs().add(tab);

        // Add new page.
        msArrayList.add(mItems, page);
        return page;
    }

    /// <summary>
    /// Removes a page from the collection.
    /// </summary>
    void remove(FormControl page)
    {
        int index = indexOf(page);
        if (index != -1)
        {
            TabStripControl tabStripControl = getTabStripControl();
            // If there are pages in the collection, then there must be a corresponding TabStripControl.
            Debug.assertTrue(tabStripControl != null);
            tabStripControl.getTabs().remove(index);

            mItems.remove(page);
        }
    }

    /// <summary>
    /// Gets a TabStripControl object from the collection of child nodes.
    /// </summary>
    private TabStripControl getTabStripControl()
    {
        for (Forms2OleControl item : mItems)
        {
            if (item.getType() == Forms2OleControlType.TAB_STRIP)
                return (TabStripControl)item;
        }

        return null;
    }

    /// <summary>
    /// Returns index of a specified page in the collection, or -1 if not found.
    /// </summary>
    private int indexOf(FormControl page)
    {
        int index = 0;
        for (Forms2OleControl control : mItems)
        {
            if (control.getType() == Forms2OleControlType.FORM)
            {
                if (control.equals(page))
                    return index;

                index++;
            }
        }

        return -1;
    }

    /// <summary>
    /// Gets page at a specified index.
    /// </summary>
    FormControl get(int index)
    {
        int curIndex = -1;
        for (Forms2OleControl control : mItems)
        {
            if (control.getType() == Forms2OleControlType.FORM)
            {
                curIndex++;
                if (curIndex == index)
                    return (FormControl)control;
            }
        }

        return null;
    }

    /// <summary>
    /// Gets a count of pages in the collection.
    /// </summary>
    int getCount()
    {
        int count = 0;
        for (Forms2OleControl control : mItems)
        {
            if (control.getType() == Forms2OleControlType.FORM)
                count++;
        }

        return count;
    }

    private /*final*/ ArrayList<Forms2OleControl> mItems;
}

