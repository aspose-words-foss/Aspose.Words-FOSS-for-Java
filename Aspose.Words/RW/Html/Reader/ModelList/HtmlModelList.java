// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/06/2019 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Helps to work with document model lists, track numbering, etc.
/// </summary>
class HtmlModelList
{
    HtmlModelList(List list, int startedAtListLevelNumber)
    {
        Debug.assertTrue(list != null);
        Debug.assertTrue(ListLevel.isLevelNumberValid(startedAtListLevelNumber));

        mList = list;
        mStartedAtListLevelNumber = startedAtListLevelNumber;

        Debug.assertTrue(ListLevel.MIN_LEVEL == 0);
        mModelListLevels = new HtmlModelListLevel[ListLevel.MAX_LEVELS];

        for (int i = ListLevel.MIN_LEVEL; i < ListLevel.MAX_LEVELS; i++)
        {
            HtmlModelListLevel modelListLevel = new HtmlModelListLevel(getList().getListLevels().get(i));
            mModelListLevels[i] = modelListLevel;
        }
    }

    void addListItem(int listLevelNumber)
    {
        Debug.assertTrue(ListLevel.isLevelNumberValid(listLevelNumber));

        for (int i = ListLevel.MIN_LEVEL; i < listLevelNumber; i++)
        {
            HtmlModelListLevel modelListLevel = getListLevel(i);
            if (!modelListLevel.hasListItems())
            {
                modelListLevel.addListItem();
            }
        }

        HtmlModelListLevel currentModelListLevel = getListLevel(listLevelNumber);
        currentModelListLevel.addListItem();

        for (int i = listLevelNumber + 1; i < ListLevel.MAX_LEVELS; i++)
        {
            HtmlModelListLevel modelListLevel = getListLevel(i);
            modelListLevel.deleteAllListItems();
        }
    }

    HtmlModelListLevel getListLevel(int listLevelNumber)
    {
        Debug.assertTrue(ListLevel.isLevelNumberValid(listLevelNumber));

        return mModelListLevels[listLevelNumber];
    }

    List getList() { return mList; };

    private List mList;

    int getStartedAtListLevelNumber() { return mStartedAtListLevelNumber; };

    private int mStartedAtListLevelNumber;

    private /*final*/ HtmlModelListLevel[] mModelListLevels;
}

