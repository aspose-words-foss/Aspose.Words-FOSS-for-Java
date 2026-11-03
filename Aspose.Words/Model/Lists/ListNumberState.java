// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/04/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.HashSetGeneric;
import com.aspose.Debug;
import com.aspose.BitUtil;


/// <summary>
/// Used by <see cref="ListNumberGenerator"/> to keep current numbers.
/// </summary>
class ListNumberState
{
    /// <summary>
    /// Ctor to create a mutable instance.
    /// </summary>
    ListNumberState(List list)
    {
        mListDef = list.getListDef();
        mList = list;
        mUsedListIDs = new HashSetGeneric<Integer>();
        init();
    }

    /// <summary>
    /// Ctor to create an immutable instance.
    /// </summary>
    ListNumberState(List list, int level, int[] numbers)
    {
        mList = list;
        setCurrentLevel(level);
        mNumbers = numbers;
        mNumberDecrements = new int[mList.getListLevels().getCount()];
    }

    /// <summary>
    /// Snapshot ctor.
    /// </summary>
    private ListNumberState(ListNumberState source)
    {
        mList = source.mList;
        setCurrentLevel(source.getCurrentLevel());
        setLocaleId(source.getLocaleId());

        mNumbers = new int[source.mNumbers.length];
        System.arraycopy(source.mNumbers, 0, mNumbers, 0, source.mNumbers.length);

        mNumberDecrements = new int[source.mNumberDecrements.length];
        System.arraycopy(source.mNumberDecrements, 0, mNumberDecrements, 0, source.mNumberDecrements.length);

        setUsedLevels(source.getUsedLevels());
        mNumberOverridesState = new ListNumberOverridesState(mNumbers, mList);
    }

    /// <summary>
    /// Snapshot is almost Clone but returns immutable object and skips cloning
    /// some members which don't make sense for immutable ListNumberState.
    /// </summary>
    ListNumberState snapshot()
    {
        return new ListNumberState(this);
    }

    /// <summary>
    /// Inits numbering from a specific list level.
    /// </summary>
    void init(int level)
    {
        int previousLevel = level - 1;
        setCurrentLevel(previousLevel);
        setUsedLevels(previousLevel);
        mNumberDecrements = new int[mList.getListLevels().getCount()];

        if (mNumbers == null)
            mNumbers = new int[mList.getListLevels().getCount()];
        else
            for (int i = level; i < mNumbers.length; i++)
                mNumbers[i] = 0;

        for (int i = level; i < mNumbers.length; i++)
            mNumbers[i] = mList.getStartAtOverrideAware(i) - 1;

        mNumberOverridesState = new ListNumberOverridesState(mNumbers, mList);
    }

    /// <summary>
    /// Selects the next item at the specified list level.
    /// Increments and resets numbers where appropriate.
    /// </summary>
    void nextItem(List list, int newLevel)
    {
        nextItem(list, newLevel, true);
    }

    /// <summary>
    /// Selects the next item at the specified list level.
    /// Increments and resets numbers where appropriate.
    /// </summary>
    void nextItem(List list, int newLevel, boolean setListNumStartAtLevel)
    {
        Debug.assertTrue(list.getListDef() == getListDef());

        // mUsedListIDs == null and mListDef == null for 'snapshot' ListNumberState, got by Snapshot method,
        // it's immutable object and NextItem operation is not available for it.
        Debug.assertTrue((mUsedListIDs != null) && (getListDef() != null));

        mList = list;
        newLevel = mList.getListLevels().correctLevel(newLevel);

        // AS If currently processed list has different ListId from previous AND
        // it was not encountered before AND its Start At value is overridden,
        // it's considered as started. It's MS Word behavior.
        if (!mUsedListIDs.contains(list.getListId()))
        {
            for (int i = newLevel; i < mNumbers.length; i++)
            {
                if (list.isStartAtOverridden(i))
                {
                    mUsedListIDs.add(list.getListId());
                    mNumbers[i] = list.getStartAtOverrideAware(i) - 1;
                }
            }
        }

        mNumbers[newLevel]++;

        // AS For the case when list level has increased by more than one,
        // we need to increment values of skipped levels.
        for (int i = getCurrentLevel() + 1; i < newLevel; i++)
        {
            int delta = getNumberDeltaForMissingLabels(getRestartAfterLevel(i), i);
            mNumbers[i] += delta;
        }

        // WORDSNET-22207 Switch current numbers to new starting value from overridden formatting.
        if ((getUsedLevels() != 0) && (newLevel != getCurrentLevel()) && (list.getOverrides().getCount() > 0))
            mNumberOverridesState.updateNumbers(list, getCurrentLevel(), newLevel);

        if (newLevel < getCurrentLevel())
        {
            for (int i = newLevel + 1; i < mNumbers.length; i++)
            {
                // WORDSNET-16207 It seems Word gets RestartAfterLevel value from ListLevel
                // but not from override settings for a list level.
                if (getRestartAfterLevel(i) < newLevel)
                    continue;

                ListLevelOverride formattingOverride = ListNumberOverridesState.getLevelOverride(i, list);
                mNumbers[i] = formattingOverride != null
                    ? formattingOverride.getListLevel().getStartAt()
                    : list.getStartAtOverrideAware(i);

                --mNumbers[i];
            }
        }

        setCurrentLevel(newLevel);
        if (setListNumStartAtLevel)
            setListNumStartAtLevel(newLevel);

        setUsedLevels(BitUtil.setBit(getUsedLevels(), 1 << newLevel, true));
        setUsedLevels(BitUtil.truncateBits(getUsedLevels(), newLevel + 1));
    }

    private int getNumberDeltaForMissingLabels(int restartAfterLevel, int currentLevel)
    {
        // WORDSNET-6402 MS Word applies sequential numeration when level has 'RestartAfter' property explicitly set 'off'
        // or no any levels of this list were used.
        if (getUsedLevels() == 0)
            return 1;

        if (restartAfterLevel < 0)
            return 0;

        for (int j = restartAfterLevel + 1; j < currentLevel; j++)
        {
            // WORDSNET-24444 Skip counter increment when there is a label with level between "lvlRestart" and current level.
            if (BitUtil.isSetInt32(getUsedLevels(), 1 << j))
                return 0;
        }
        return 1;
    }

    /// <summary>
    /// Gets the current number for the current level.
    /// </summary>
    int getNumber()
    {
        return getNumber(getCurrentLevel());
    }

    /// <summary>
    /// Sets the current number for the current level.
    /// </summary>
    void setNumber(int value)
    {
        setNumber(getCurrentLevel(), value);
    }

    /// <summary>
    /// Increase decrement value for current level.
    /// Call this method when encounter paragraph insertions
    /// and final revisions is not shown.
    /// </summary>
    void increaseDecrement()
    {
        mNumberDecrements[mList.getListLevels().correctLevel(getCurrentLevel())]++;
    }

    /// <summary>
    /// Gets the current number for the specified level.
    /// </summary>
    int getNumber(int level)
    {
        int correctLevel = mList.getListLevels().correctLevel(level);
        return mNumbers[correctLevel] - mNumberDecrements[correctLevel];
    }

    /// <summary>
    /// Gets the level properties for the current level.
    /// </summary>
    ListLevel getListLevel()
    {
        return getListLevel(getCurrentLevel());
    }

    /// <summary>
    /// Gets the level properties for the specified level.
    /// </summary>
    ListLevel getListLevel(int level)
    {
        return mList.getListLevelOverrideAware(level);
    }

    /// <summary>
    /// Inits numbering.
    /// </summary>
    private void init()
    {
        setCurrentLevel(-1);
        mNumbers = new int[mList.getListLevels().getCount()];
        mNumberDecrements = new int[mList.getListLevels().getCount()];
        setUsedLevels(0);
        mNumberOverridesState = new ListNumberOverridesState(mNumbers, mList);

        for (int i = 0; i < mNumbers.length; i++)
            mNumbers[i] = mList.getStartAtOverrideAware(i) - 1;
    }

    /// <summary>
    /// Get the RestartAfterLevel value for the specified level.
    /// </summary>
    /// <returns></returns>
    private int getRestartAfterLevel(int level)
    {
        return mList.getListLevels().fetchListLevel(level).getRestartAfterLevel();
    }

    /// <summary>
    /// Sets the current number for the specified level.
    /// </summary>
    private void setNumber(int level, int value)
    {
        mNumbers[mList.getListLevels().correctLevel(level)] = value;
    }

    int getListId() { return mList.getListId(); }

    /// <summary>
    /// Zero based current level in this list.
    /// </summary>
    int getCurrentLevel() { return mCurrentLevel; }; private void setCurrentLevel(int value) { mCurrentLevel = value; };

    private int mCurrentLevel;

    /// <summary>
    /// Zero based level in this list used to maintain LISTNUM field routines. Do not use it in other circumstances.
    /// </summary>
    int getListNumStartAtLevel() { return mListNumStartAtLevel; }; private void setListNumStartAtLevel(int value) { mListNumStartAtLevel = value; };

    private int mListNumStartAtLevel;

    /// <summary>
    /// Gets or sets the locale identifier (language) of corresponded paragraph.
    /// </summary>
    /// <remarks>
    /// Locale identifier (language) is used to determine LocalizedOrdinalSuffix.
    /// </remarks>
    int getLocaleId() { return mLocaleId; }; void setLocaleId(int value) { mLocaleId = value; };

    private int mLocaleId;

    /// <summary>
    /// The list definition that is wrapped by this object.
    /// </summary>
    ListDef getListDef() { return mListDef; };

    private ListDef mListDef;

    /// <summary>
    /// Usage bit map. If Nth bit set then Nth level was used in numbering.
    /// </summary>
    int getUsedLevels() { return mUsedLevels; }; private void setUsedLevels(int value) { mUsedLevels = value; };

    private int mUsedLevels;

    /// <summary>
    /// Last used list that referred to our list definition.
    /// </summary>
    private List mList;

    /// <summary>
    /// List IDs of all encountered lists of <see cref="ListDef"/> corresponding to this <see cref="ListNumberState"/>.
    /// </summary>
    private /*final*/ HashSetGeneric<Integer> mUsedListIDs;

    /// <summary>
    /// int[9] or int[1] that keeps the current numbers for each level.
    /// </summary>
    private int[] mNumbers;
    private int[] mNumberDecrements;

    /// <summary>
    /// Holds calculating current numbers logic and data for the case when level formatting is overridden.
    /// </summary>
    private ListNumberOverridesState mNumberOverridesState;
}

