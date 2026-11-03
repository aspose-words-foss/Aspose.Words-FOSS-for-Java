// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/04/2024 by Vyacheslav Deryushev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// Represents a read-only collection of <see cref="Adjustment"/> adjust values that are applied to the specified shape.
/// </summary>
public class AdjustmentCollection
{
    /// <summary>
    /// Creates instance of <see cref="AdjustmentCollection"/> class.
    /// </summary>
    AdjustmentCollection()
    {
    }

    /// <summary>
    /// Adds an <see cref="Adjustment"/> to the end of the list.
    /// </summary>
    void add(Adjustment adjustment)
    {
        msArrayList.add(mAdjustments, adjustment);
    }

    /// <summary>
    /// Returns an adjustment at the specified index.
    /// </summary>
    /// <param name="index">An index into the collection.</param>
    public Adjustment get(int index) { return mAdjustments.get(index); }

    /// <summary>
    /// Gets the number of elements contained in the collection.
    /// </summary>
    public int getCount() { return mAdjustments.size(); }

    private /*final*/ ArrayList<Adjustment> mAdjustments = new ArrayList<Adjustment>();
}

