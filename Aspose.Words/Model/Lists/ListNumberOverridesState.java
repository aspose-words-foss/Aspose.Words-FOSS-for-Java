// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/05/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Implements calculation logic for current numbers which related with processing overridden formatting on list level.
/// </summary>
/// <remarks>
/// Word calculates current numbers according to "StartAt" value in the overridden formatting. Although, previous
/// numbers were calculated according to values from a list definition. This class attempts to mimic this logic.
/// </remarks>
class ListNumberOverridesState
{
    ListNumberOverridesState(int[] numbers, List list)
    {
        mNumbers = numbers;
        mMinLevelWithRecalcStartAt = list.getListLevels().getCount();
    }

    /// <summary>
    /// Updates numbers according to overridden formatting.
    /// </summary>
    void updateNumbers(List list, int prevLevel, int newLevel)
    {
        Debug.assertTrue(list.getOverrides().getCount() > 0);

        if (mInitialLevel < 0)
            mInitialLevel = prevLevel;

        int startLevel = newLevel < prevLevel ? newLevel : prevLevel + 1;
        if ((mMinLevelWithRecalcStartAt > startLevel) && (startLevel != mInitialLevel))
        {
            for (int i = startLevel; i < mMinLevelWithRecalcStartAt; ++i)
            {
                ListLevelOverride formattingOverride = getLevelOverride(i, list);
                if (formattingOverride == null)
                    continue;

                int usedLines = mNumbers[i] - list.getStartAtOverrideAware(i);
                mNumbers[i] = formattingOverride.getListLevel().getStartAt() + usedLines;
            }

            mMinLevelWithRecalcStartAt = startLevel;
        }
    }

    /// <summary>
    /// Gets level override when it applicable.
    /// </summary>
    static ListLevelOverride getLevelOverride(int level, List list)
    {
        // DS: Skip for a while cases when both "StartOverride" and "StartAt" is specified  in overrides.
        return !list.isStartAtOverridden(level)
            ? list.getFormattingOverride(level)
            : null;
    }

    /// <summary>
    /// Current numbers on list levels.
    /// </summary>
    private /*final*/ int[] mNumbers;

    /// <summary>
    /// Holds list level number which was used before the first calculation of numbers based on new "StartAt" value.
    /// The Word has special logic when a new list level equal to initial list level.
    /// </summary>
    private int mInitialLevel = -1;

    /// <summary>
    /// Holds number of the level which already was switched to new "StartAt" value.
    /// This property is required to avoid extra calculations of current numbers based on new "StartAt".
    /// Also it allows to skip logic which calculates previous "StartAt" value (it is necessary to subtract this value).
    /// </summary>
    private int mMinLevelWithRecalcStartAt;
}

