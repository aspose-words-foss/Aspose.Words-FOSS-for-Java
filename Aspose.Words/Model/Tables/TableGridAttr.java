// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/01/2018 by Dmitry Matveenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntList;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.msObject;


/// <summary>
/// Represents the result of grid calculation and implements <see cref="IComplexAttr"/>.
/// </summary>
class TableGridAttr implements IComplexAttr
{
    TableGridAttr()
    {
        // Empty column list.
        setColumns(new IntList());
    }

    TableGridAttr(IntList columnWidths)
    {
        setColumns(columnWidths);
    }

    /// <summary>
    /// Represents different states during grid calculation.
    /// </summary>
    /// <remarks>
    /// The grid may be calculated but not applied.
    /// It is used for testing.
    /// </remarks>
    /*GridState*/int getState() { return mState; }
    void setState(/*GridState*/int value) { mState = value; }

    /// <summary>
    /// Gets a boolean value indicating if the given grid is not null and has Applied state.
    /// </summary>
    static boolean isApplied(TableGridAttr grid)
    {
        return (grid != null) && (grid.getState() == GridState.APPLIED);
    }

    // The method is used by the layout engine only.
    int[] toLisArray()
    {
        int[] columns = new int[getColumns().getCount()];

        for (int i = 0; i < getColumns().getCount(); ++i)
        {
            int twips = getColumns().get(i);
            columns[i] = ConvertUtilCore.twipToLi(twips);
        }

        return columns;
    }

    public boolean /*IComplexAttr.*/isInheritedComplexAttr() { return false; }

    public IComplexAttr /*IComplexAttr.*/deepCloneComplexAttr()
    {
        IntList clonedColumns = new IntList(getColumns().getCount());
        clonedColumns.addRange(getColumns());

        TableGridAttr clonedGrid = new TableGridAttr(clonedColumns);

        clonedGrid.setState(getState());

        return clonedGrid;
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
        if (obj.getClass() != TableGridAttr.class)
            return false;

        TableGridAttr otherGrid = (TableGridAttr)obj;
        return getColumns().equals(otherGrid.getColumns()) && (getState() == otherGrid.getState());
    }

    public /*override*/ int hashCode()
    {
        // It is not actually used. It is overridden because Equals() is overridden.
        // alexnosk: modified for C++ porting.
        int hash = (/*int.hashCode()*/((int)getState()));
        for (int i = 0; i < mColumns.getCount(); i++)
            hash ^= (/*int.hashCode()*/mColumns.get(i));

        return hash;
    }

    /// <summary>
    /// A collection of grid column widths.
    /// </summary>
    IntList getColumns() { return mColumns; }
    void setColumns(IntList value) { mColumns = value; }

    private IntList mColumns;
    private /*GridState*/int mState;
}

