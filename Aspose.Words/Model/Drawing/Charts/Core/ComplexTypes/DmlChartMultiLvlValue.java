// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/02/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;


/// <summary>
/// Base class for multi-level numeric and string values.
/// </summary>
abstract class DmlChartMultiLvlValue extends DmlChartValue
{
    protected DmlChartMultiLvlValue(int index, /*DmlChartValueType*/int valueType)
    {
    	super(index, valueType);
	
    }

    /// <summary>
    /// Creates a copy of this chart data value.
    /// </summary>
    /*override*/ DmlChartValue deepClone()
    {
        DmlChartMultiLvlValue lhs = (DmlChartMultiLvlValue)super.deepClone();

        lhs.mLevels = new ArrayList<Object>();
        for (Object value : mLevels)
            msArrayList.add(lhs.mLevels, value);

        return lhs;
    }

    /// <summary>
    /// Adds a value of the specified level index. Level index is ignored if negative.
    /// </summary>
    void addLevelValue(Object value, int levelIndex)
    {
        if (levelIndex >= 0)
        {
            while (mLevels.size() <= levelIndex)
                msArrayList.add(mLevels, null);

            mLevels.set(levelIndex, value);
        }
        else
        {
            msArrayList.add(mLevels, value);
        }

        String stringValue = ms.as(value, String.class);

        // WORDSNET-23206 To render a multilevel value, MS Word uses the text of all levels, sorted in reverse order.
        if (!msString.isNullOrEmpty(stringValue))
            mMultiLevelString = msString.concat(stringValue, ControlChar.SPACE, mMultiLevelString);

        markCollectionAsChanged();
    }

    /// <summary>
    /// Returns a list of values by levels.
    /// </summary>
    ArrayList<Object> getLevels() { return mLevels; }

    /// <summary>
    /// Number of levels at this value index (<see cref="DmlChartValue.Index"/>).
    /// </summary>
    /*override*/ int getLevelsCount() { return getLevels().size(); }

    /// <summary>
    /// The string representation of the multilevel value. Used to render series labels and legend items.
    /// </summary>
    /*override*/ String getStringValue() { return mMultiLevelString; }
    
    private String mMultiLevelString;
    private ArrayList<Object> mLevels = new ArrayList<Object>();
}

