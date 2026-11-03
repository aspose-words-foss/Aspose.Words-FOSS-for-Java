// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/01/2018 by Dmitry Matveenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntList;
import com.aspose.ms.System.msObject;


/// <summary>
/// This is a wrapper for storing a list of table grid column widths in a table attribute that implements <see cref="IComplexAttr"/>
/// </summary>
class TableGridColumnsAttr implements IComplexAttr
{
    TableGridColumnsAttr(IntList columnWidths)
    {
        if (columnWidths == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "columnWidths");

        GridColumns = columnWidths;
    }

    public boolean /*IComplexAttr.*/isInheritedComplexAttr() { return false; }

    public IComplexAttr /*IComplexAttr.*/deepCloneComplexAttr()
    {
        IntList clonedList = new IntList(GridColumns.getCount());
        clonedList.addRange(GridColumns);
        return new TableGridColumnsAttr(clonedList);
    }

    /// <summary>
    /// Determines whether the specified object is equal in value to the current object.
    /// </summary>
    /// <remarks>
    /// It is used for testing.
    /// </remarks>
    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (obj.getClass() != TableGridColumnsAttr.class)
            return false;

        return GridColumns.equals(((TableGridColumnsAttr)obj).GridColumns);
    }

    public /*override*/ int hashCode()
    {
        return GridColumns.hashCode();
    }

    /*final*/ IntList GridColumns;
}

