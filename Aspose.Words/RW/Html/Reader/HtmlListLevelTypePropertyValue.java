// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/12/2019 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlListLevelTypePropertyValue implements IHtmlModelListLevelPropertyValue
{
    HtmlListLevelTypePropertyValue(/*HtmlModelListLevelType*/int listLevelType)
    {
        mListLevelType = listLevelType;
    }

    public boolean /*IHtmlModelListLevelPropertyValue.*/canModifyListLevel(HtmlModelListLevel modelListLevel)
    {
        return !modelListLevel.isListLevelTypeSet() || (mListLevelType == modelListLevel.getListLevelType());
    }

    public void /*IHtmlModelListLevelPropertyValue.*/modifyListLevel(HtmlModelListLevel modelListLevel)
    {
        if (!modelListLevel.isListLevelTypeSet())
        {
            modelListLevel.setListLevelType(mListLevelType);
        }
    }

    private /*final*/ /*HtmlModelListLevelType*/int mListLevelType;
}

