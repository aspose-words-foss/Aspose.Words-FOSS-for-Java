// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a multi-level string point (the element 5.7.2.152 pt of ISO/IEC 29500 and 
/// the complex type 2.24.3.76 CT_StringValue of [MS-ODRAWXML]).
/// This element specifies string data for a specific data point.
/// </summary>
class DmlChartMultiLvlStrValue extends DmlChartMultiLvlValue
{
    DmlChartMultiLvlStrValue(int index)
    {
    	super(index, DmlChartValueType.MULTI_LVL_STRING);
	
    }

    /// <summary>
    /// Returns value of the specified level.
    /// </summary>
    /*override*/ DmlChartValue getLevelValue(int levelIndex)
    {
        if (levelIndex < 0 || levelIndex >= getLevelsCount() || getLevels().get(levelIndex) == null)
            return new DmlChartDummyValue(getIndex(), getValue());

        return new DmlChartStrValue(getIndex(), (String)getLevels().get(levelIndex));
    }

    /// <summary>
    /// Gets text of the specified level, indexed from the end of the level list.
    /// </summary>
    String getLevelTextInReverseOrder(int levelIndexFromEnd)
    {
        return (levelIndexFromEnd < getLevelsCount())
            ? (String)getLevels().get(getLevelsCount() - levelIndexFromEnd - 1)
            : null;
    }

    /// <summary>
    /// Returns one based index as double value.
    /// </summary>
    /*override*/ double getValue() { return (getIndex() + 1); }
}

