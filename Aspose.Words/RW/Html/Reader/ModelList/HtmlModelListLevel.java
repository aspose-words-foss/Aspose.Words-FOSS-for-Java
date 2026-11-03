// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/06/2019 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Helps to work with model list levels, track numbering, etc.
/// </summary>
class HtmlModelListLevel
{
    HtmlModelListLevel(ListLevel listLevel)
    {
        Debug.assertTrue(listLevel != null);

        setListLevel(listLevel);
    }

    void addListItem()
    {
        mListItemCount++;
    }

    void deleteAllListItems()
    {
        mListItemCount = 0;
    }

    ListLevel getListLevel() { return mListLevel; }; private void setListLevel(ListLevel value) { mListLevel = value; };

    private ListLevel mListLevel;

    boolean hasListItems() { return mListItemCount > 0; }

    /*HtmlModelListLevelType*/int getListLevelType() { return mListLevelType; }
    void setListLevelType(/*HtmlModelListLevelType*/int value)
    {
        Debug.assertTrue(!isListLevelTypeSet());

        mListLevelType = value;
        isListLevelTypeSet(true);
    }

    boolean isListLevelTypeSet() { return mIsListLevelTypeSet; }; private void isListLevelTypeSet(boolean value) { mIsListLevelTypeSet = value; };

    private boolean mIsListLevelTypeSet;

    String getNumberFormat() { return getListLevel().getNumberFormat(); }
    void setNumberFormat(String value)
    {
        Debug.assertTrue(!isNumberFormatSet());

        getListLevel().setNumberFormatSafe(value);
        isNumberFormatSet(true);
    }

    boolean isNumberFormatSet() { return mIsNumberFormatSet; }; private void isNumberFormatSet(boolean value) { mIsNumberFormatSet = value; };

    private boolean mIsNumberFormatSet;

    int getNumberValue() { return getListLevel().getStartAt() + mListItemCount - 1; }
    void setNumberValue(int value)
    {
        Debug.assertTrue(!isNumberValueSet());

        // WORDSNET-20444 Ignore "start at" values that are not accepted by MS Word. This is what MS Word does when
        // it loads HTML.
        int startAtValue = value - mListItemCount + 1;
        if (ListLevel.isStartAtValid(startAtValue))
        {
            getListLevel().setStartAt(startAtValue);
            isNumberValueSet(true);
        }
    }

    boolean isNumberValueSet() { return mIsNumberValueSet; }; private void isNumberValueSet(boolean value) { mIsNumberValueSet = value; };

    private boolean mIsNumberValueSet;

    /*NumberStyle*/int getNumberStyle() { return getListLevel().getNumberStyle(); }
    void setNumberStyle(/*NumberStyle*/int value)
    {
        Debug.assertTrue(!isNumberStyleSet());

        getListLevel().setNumberStyle(value);
        isNumberStyleSet(true);
    }

    boolean isNumberStyleSet() { return mIsNumberStyleSet; }; private void isNumberStyleSet(boolean value) { mIsNumberStyleSet = value; };

    private boolean mIsNumberStyleSet;

    int getPictureBulletId() { return getListLevel().getPictureBulletId(); }
    void setPictureBulletId(int value)
    {
        Debug.assertTrue(!isPictureBulletIdSet());

        getListLevel().setPictureBulletId(value);
        isPictureBulletIdSet(true);
    }

    boolean isPictureBulletIdSet() { return mIsPictureBulletIdSet; }; private void isPictureBulletIdSet(boolean value) { mIsPictureBulletIdSet = value; };

    private boolean mIsPictureBulletIdSet;

    private /*HtmlModelListLevelType*/int mListLevelType;
    private int mListItemCount;
}

