// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/12/2019 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlNextNumberValuePropertyValue implements IHtmlModelListLevelPropertyValue
{
    HtmlNextNumberValuePropertyValue(int nextNumberValue)
    {
        mNextNumberValue = nextNumberValue;
    }

    public boolean /*IHtmlModelListLevelPropertyValue.*/canModifyListLevel(HtmlModelListLevel modelListLevel)
    {
        return !modelListLevel.isNumberValueSet() || (mNextNumberValue == (modelListLevel.getNumberValue() + 1));
    }

    public void /*IHtmlModelListLevelPropertyValue.*/modifyListLevel(HtmlModelListLevel modelListLevel)
    {
        if (!modelListLevel.isNumberValueSet())
        {
            modelListLevel.setNumberValue(mNextNumberValue);
        }
    }

    private /*final*/ int mNextNumberValue;
}

