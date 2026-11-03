// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/12/2019 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlNumberStylePropertyValue implements IHtmlModelListLevelPropertyValue
{
    HtmlNumberStylePropertyValue(/*NumberStyle*/int numberStyle)
    {
        mNumberStyle = numberStyle;
    }

    public boolean /*IHtmlModelListLevelPropertyValue.*/canModifyListLevel(HtmlModelListLevel modelListLevel)
    {
        return !modelListLevel.isNumberStyleSet() || (mNumberStyle == modelListLevel.getNumberStyle());
    }

    public void /*IHtmlModelListLevelPropertyValue.*/modifyListLevel(HtmlModelListLevel modelListLevel)
    {
        if (!modelListLevel.isNumberStyleSet())
        {
            modelListLevel.setNumberStyle(mNumberStyle);
        }
    }

    private /*final*/ /*NumberStyle*/int mNumberStyle;
}

