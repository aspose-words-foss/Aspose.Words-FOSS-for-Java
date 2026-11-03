// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2019 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// List level identifier.
/// </summary>
class HtmlListLevelId
{
    HtmlListLevelId(int htmlListLevelNumber)
    {
        Debug.assertTrue(htmlListLevelNumber >= ListLevel.MIN_LEVEL);
        Debug.assertTrue(ListLevel.MIN_LEVEL == 0);

        mListLevelNumber = htmlListLevelNumber % ListLevel.MAX_LEVELS;
        mHtmlListLevelNumber = htmlListLevelNumber;
        mListNestedLevel = htmlListLevelNumber / ListLevel.MAX_LEVELS;
    }

    /// <summary>
    /// List level number at which list item should be added.
    /// </summary>
    int getListLevelNumber() { return mListLevelNumber; };

    private int mListLevelNumber;

    /// <summary>
    /// List level number which was specified by HTML list.
    /// </summary>
    int getHtmlListLevelNumber() { return mHtmlListLevelNumber; };

    private int mHtmlListLevelNumber;

    /// <summary>
    /// List nested level.
    /// </summary>
    /// <remarks>
    /// When list exhausts all its list levels, another list is created. So we get sequence of nested lists.
    /// Each list has its list nested level.
    /// </remarks>
    int getListNestedLevel() { return mListNestedLevel; };

    private int mListNestedLevel;
}

