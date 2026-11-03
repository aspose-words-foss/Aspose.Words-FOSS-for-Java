// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/12/2019 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlCurrentNumberValuePropertyValue implements IHtmlModelListLevelPropertyValue
{
    HtmlCurrentNumberValuePropertyValue(int currentNumberValue)
    {
        mCurrentNumberValue = currentNumberValue;
    }

    public boolean /*IHtmlModelListLevelPropertyValue.*/canModifyListLevel(HtmlModelListLevel modelListLevel)
    {
        return !modelListLevel.isNumberValueSet() || (mCurrentNumberValue == modelListLevel.getNumberValue());
    }

    public void /*IHtmlModelListLevelPropertyValue.*/modifyListLevel(HtmlModelListLevel modelListLevel)
    {
        if (!modelListLevel.isNumberValueSet())
        {
            modelListLevel.setNumberValue(mCurrentNumberValue);
        }
    }

    private /*final*/ int mCurrentNumberValue;
}

