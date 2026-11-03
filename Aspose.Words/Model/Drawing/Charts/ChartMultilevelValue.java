// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/11/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msString;

/// <summary>
/// Represents a value for charts that display multilevel data.
/// </summary>
/// <dev>
/// The Word 2016 chart types allow three or fewer number of levels. The older chart types allow an unlimited
/// number of levels, but multilevel values are rarely used in these charts: let's limit the number of levels
/// to three for now.
/// </dev>
public class ChartMultilevelValue
{
    /// <summary>
    /// Initializes a new instance of this class that represents a three-level value.
    /// </summary>
    public ChartMultilevelValue(String level1, String level2, String level3)
    {
        mLevel1 = level1;
        mLevel2 = level2;
        mLevel3 = level3;
    }

    /// <summary>
    /// Initializes a new instance of this class that represents a two-level value.
    /// </summary>
    public ChartMultilevelValue(String level1, String level2)
    {
        mLevel1 = level1;
        mLevel2 = level2;
    }

    /// <summary>
    /// Initializes a new instance of this class that represents a single-level value.
    /// </summary>
    public ChartMultilevelValue(String level1)
    {
        mLevel1 = level1;
    }

    /// <summary>
    /// Gets a hash code for the current multilevel data object.
    /// </summary>
    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            int hash = 0;

            if (getLevel1() != null)
                hash = hash * 31 + getLevel1().hashCode();

            if (getLevel2() != null)
                hash = hash * 31 + getLevel2().hashCode();

            if (getLevel3() != null)
                hash = hash * 31 + getLevel3().hashCode();

            return hash;
        }
    }

    /// <summary>
    /// Gets a flag indicating whether the specified object is equal to the current multilevel data object.
    /// </summary>
    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        else if (msObject.referenceEquals(this, obj))
            return true;
        else if (obj.getClass() != getClass())
            return false;

        ChartMultilevelValue value = (ChartMultilevelValue)obj;
        
        return (msString.equals(value.getLevel1(), getLevel1())) && (msString.equals(value.getLevel2(), getLevel2())) && (msString.equals(value.getLevel3(), getLevel3()));
    }

    /// <summary>
    /// Gets the name of the chart top level that this value refers to.
    /// </summary>
    public String getLevel1() { return mLevel1; };

    private  String mLevel1;

    /// <summary>
    /// Gets the name of the chart intermediate level that this value refers to.
    /// </summary>
    public String getLevel2() { return mLevel2; };

    private  String mLevel2;

    /// <summary>
    /// Gets the name of the chart bottom level that this value refers to.
    /// </summary>
    public String getLevel3() { return mLevel3; };

    private  String mLevel3;
}

