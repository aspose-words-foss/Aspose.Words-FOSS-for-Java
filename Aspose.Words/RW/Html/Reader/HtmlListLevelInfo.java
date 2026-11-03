// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/04/2014 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Stores some information about list level.
/// </summary>
class HtmlListLevelInfo
{
    HtmlListLevelInfo(int listLevelNumber, boolean isImplicit)
    {
        setListLevelNumber(listLevelNumber);
        mIsImplicit = isImplicit;

        setStartValue(1);
        setListTemplate(ListTemplate.BULLET_DEFAULT);
    }

    boolean isImplicit() { return mIsImplicit; };

    private boolean mIsImplicit;

    ListLevel getCurrentListLevel() { return mCurrentListLevel; }; void setCurrentListLevel(ListLevel value) { mCurrentListLevel = value; };

    private ListLevel mCurrentListLevel;

    int getListLevelNumber() { return mListLevelNumber; }; void setListLevelNumber(int value) { mListLevelNumber = value; };

    private int mListLevelNumber;

    int getStartValue() { return mStartValue; }; void setStartValue(int value) { mStartValue = value; };

    private int mStartValue;

    int getListLevelNumberValue() { return getStartValue() + getListItemCount() - 1; }

    HtmlListItemMarker getDefaultListItemMarker() { return mDefaultListItemMarker; }; void setDefaultListItemMarker(HtmlListItemMarker value) { mDefaultListItemMarker = value; };

    private HtmlListItemMarker mDefaultListItemMarker;

    /*ListTemplate*/int getListTemplate() { return mListTemplate; }; void setListTemplate(/*ListTemplate*/int value) { mListTemplate = value; };

    private /*ListTemplate*/int mListTemplate;

    int getListItemCount() { return mListItemCount; }; void setListItemCount(int value) { mListItemCount = value; };

    private int mListItemCount;

    HtmlListItemMarker getListItemMarker() { return mListItemMarker; }; void setListItemMarker(HtmlListItemMarker value) { mListItemMarker = value; };

    private HtmlListItemMarker mListItemMarker;

    boolean isNoneListStyle() { return mIsNoneListStyle; }; void isNoneListStyle(boolean value) { mIsNoneListStyle = value; };

    private boolean mIsNoneListStyle;
}

