// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/09/2005 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;
import java.util.ArrayList;


/// <summary>
/// Typed collection of properties.
/// </summary>
public class PropertyCollection
{
    public Property get(int index) { return mItems.get(index); }
    public void set(int index, Property value) { mItems.set(index, value); }

    public int getCount() { return mItems.size(); }

    public void add(Property item)
    {
        msArrayList.add(mItems, item);
    }

    /// <summary>
    /// Linear search for a property by id.
    /// </summary>
    public Property getById(int id)
    {
        for (int i = 0; i < mItems.size(); i++)
        {
            if (this.get(i).getId() == id)
                return this.get(i);
        }
        return null;
    }

    /// <summary>
    /// Linear search for a property by name.
    /// </summary>
    public Property getByName(String name)
    {
        for (int i = 0; i < mItems.size(); i++)
        {
            if (msString.equals(this.get(i).getName(), name))
                return this.get(i);
        }
        return null;
    }

    /// <summary>
    /// Returns true if at least one property has a name.
    /// </summary>
    public boolean hasNames()
    {
        for (int i = 0; i < mItems.size(); i++)
        {
            if (this.get(i).hasName())
                return true;
        }
        return false;
    }

    public int getCountOfPropertiesWithNames()
    {
        int result = 0;
        for (int i = 0; i < mItems.size(); i++)
        {
            if (this.get(i).hasName())
                result++;
        }
        return result;
    }

    private /*final*/ ArrayList<Property> mItems = new ArrayList<Property>();
}

