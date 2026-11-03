// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/05/2025 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.msObject;
import com.aspose.StringUtil;

class CssListRuleSelector
{
    CssListRuleSelector(
        String listDefName,
        String listLevelName,
        String lfoName)
    {
        mListDefName = listDefName;
        mListLevelName = msString.toLowerInvariant(listLevelName);
        mLfoName = lfoName;
    }

    CssListRuleSelector getListDefSelector()
    {
        return getListDefSelector("");
    }

    CssListRuleSelector getListDefSelector(String listLevelName)
    {
        return new CssListRuleSelector(getListDefName(), listLevelName, "");
    }

    CssListRuleSelector getLfoSelector()
    {
        return getLfoSelector("");
    }

    CssListRuleSelector getLfoSelector(String listLevelName)
    {
        return new CssListRuleSelector(getListDefName(), listLevelName, getLfoName());
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(obj, null))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (getClass() != obj.getClass())
            return false;

        CssListRuleSelector other = (CssListRuleSelector)obj;
        return (msString.equals(getListDefName(), other.getListDefName())) &&
            (msString.equals(getListLevelName(), other.getListLevelName())) &&
            (msString.equals(getLfoName(), other.getLfoName()));
    }

    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            int hashCode = 818794172;
            hashCode = hashCode * -1521134295 + ((getListDefName() == null) ? 0 : getListDefName().hashCode());
            hashCode = hashCode * -1521134295 + ((getListLevelName() == null) ? 0 : getListLevelName().hashCode());
            hashCode = hashCode * -1521134295 + ((getLfoName() == null) ? 0 : getLfoName().hashCode());
            return hashCode;
        }
    }

    boolean isListDefSelector() { return StringUtil.hasChars(getListDefName()) && !StringUtil.hasChars(getLfoName()); }

    boolean isLfoSelector() { return StringUtil.hasChars(getListDefName()) && StringUtil.hasChars(getLfoName()); }

    String getListDefName() { return mListDefName; };

    private String mListDefName;

    String getListLevelName() { return mListLevelName; };

    private String mListLevelName;

    String getLfoName() { return mLfoName; };

    private String mLfoName;
}

