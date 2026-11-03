// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/05/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Stores information about 'option' element.
/// </summary>
class HtmlOptionElementInfo
{
    HtmlOptionElementInfo(String text, String value, boolean isSelected)
    {
        mText = text;
        mValue = value;
        mIsSelected = isSelected;
    }

    String getText() { return mText; }
    void setText(String value) { mText = value; }

    boolean isSelected() { return mIsSelected; }
    void isSelected(boolean value) { mIsSelected = value; }

    String getValue() { return mValue; }
    void setValue(String value) { mValue = value; }

    private String mText;
    private boolean mIsSelected;
    private String mValue;
}

