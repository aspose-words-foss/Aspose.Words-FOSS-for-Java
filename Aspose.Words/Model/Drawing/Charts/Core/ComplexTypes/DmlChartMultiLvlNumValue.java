// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/02/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Represents a multi-level numeric point (the complex type 2.24.3.59 CT_NumericValue of [MS-ODRAWXML]).
/// This element specifies numeric data for a specific data point.
/// </summary>
class DmlChartMultiLvlNumValue extends DmlChartMultiLvlValue
{
    /// <summary>
    /// Default ctor. Allows specifying level properties that contain format string by levels.
    /// </summary>
    DmlChartMultiLvlNumValue(int index, ArrayList<DmlChartDataLevelProperties> levelProperties)
    {
    	super(index, DmlChartValueType.MULTI_LVL_NUMERIC);
	
        mLevelProperties = levelProperties;
    }

    /// <summary>
    /// Returns value of the specified level.
    /// </summary>
    /*override*/ DmlChartValue getLevelValue(int levelIndex)
    {
        if (levelIndex < 0 || levelIndex >= getLevelsCount() || getLevels().get(levelIndex) == null)
            return new DmlChartDummyValue(getIndex(), getValue());

        return new DmlChartNumValue(getIndex(), (/*double*/Double)getLevels().get(levelIndex), getFormatCode(levelIndex));
    }

    /// <summary>
    /// Sets level properties.
    /// </summary>
    void setLevelProperties(ArrayList<DmlChartDataLevelProperties> levelProperties)
    {
        mLevelProperties = levelProperties;
    }

    /// <summary>
    /// Returns format code for the specified level index.
    /// </summary>
    String getFormatCode(int levelIndex)
    {
        if (mLevelProperties != null && mLevelProperties.size() > levelIndex)
            return mLevelProperties.get(levelIndex).getFormatCode();
        else
            return null;
    }

    /// <summary>
    /// Returns value of the first level formatted with using the format string.
    /// </summary>
    /*override*/ String getStringValue() { return DmlChartNumValue.valueToString(getValue(), 1.0, getFormatCode(0)); }

    /// <summary>
    /// Just returns value of the first level.
    /// </summary>
    /*override*/ double getValue() { return getLevels().get(0) != null ? (/*double*/Double)getLevels().get(0) : 0.0; }

    /// <summary>
    /// Returns true if format string of the first level is date format.
    /// </summary>
    /*override*/ boolean isDate() { return DmlChartFormatCodeValidator.isDateFormat(getFormatCode(0)); }

    private ArrayList<DmlChartDataLevelProperties> mLevelProperties;
}

