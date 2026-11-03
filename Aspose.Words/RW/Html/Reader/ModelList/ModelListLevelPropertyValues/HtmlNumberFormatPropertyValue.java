// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/12/2019 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;

class HtmlNumberFormatPropertyValue implements IHtmlModelListLevelPropertyValue
{
    HtmlNumberFormatPropertyValue(String numberFormat)
    {
        mNumberFormat = numberFormat;
    }

    public boolean /*IHtmlModelListLevelPropertyValue.*/canModifyListLevel(HtmlModelListLevel modelListLevel)
    {
        return !modelListLevel.isNumberFormatSet() || (msString.equals(mNumberFormat, modelListLevel.getNumberFormat()));
    }

    public void /*IHtmlModelListLevelPropertyValue.*/modifyListLevel(HtmlModelListLevel modelListLevel)
    {
        if (!modelListLevel.isNumberFormatSet())
        {
            modelListLevel.setNumberFormat(mNumberFormat);
        }
    }

    private /*final*/ String mNumberFormat;
}

