// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/12/2019 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


class HtmlModelListLevelPropertyValueCollection implements IHtmlModelListLevelPropertyValue
{
    HtmlModelListLevelPropertyValueCollection()
    {
        mPropertyValues = new ArrayList<IHtmlModelListLevelPropertyValue>();
    }

    public boolean canModifyListLevel(HtmlModelListLevel modelListLevel)
    {
        for (IHtmlModelListLevelPropertyValue propertyValue : mPropertyValues)
        {
            if (!propertyValue.canModifyListLevel(modelListLevel))
            {
                return false;
            }
        }

        return true;
    }

    public void modifyListLevel(HtmlModelListLevel modelListLevel)
    {
        for (IHtmlModelListLevelPropertyValue propertyValue : mPropertyValues)
        {
            propertyValue.modifyListLevel(modelListLevel);
        }
    }

    void add(IHtmlModelListLevelPropertyValue propertyValue)
    {
        msArrayList.add(mPropertyValues, propertyValue);
    }

    private /*final*/ ArrayList<IHtmlModelListLevelPropertyValue> mPropertyValues;
}

