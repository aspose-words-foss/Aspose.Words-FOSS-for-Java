// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/02/2026 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Represents a CSS list at-rule's prelude.
/// </summary>
class CssListRulePrelude
{
    CssListRulePrelude(
        String listDefName,
        String listLevelName,
        String lfoName)
    {
        Debug.assertTrue(StringUtil.hasChars(listDefName));

        mListDefName = listDefName;
        mListLevelName = msString.toLowerInvariant(listLevelName);
        mLfoName = lfoName;

        boolean hasListLevelName = StringUtil.hasChars(getListLevelName());
        mListRuleType = StringUtil.hasChars(getLfoName())
            ? hasListLevelName
                ? CssListRuleType.LIST_LEVEL_LFO
                : CssListRuleType.LIST_LFO
            : hasListLevelName
                ? CssListRuleType.LIST_LEVEL
                : CssListRuleType.LIST_DEF;
    }

    boolean matches(
        /*CssListRuleType*/int listRuleType,
        CssListRuleSelector selector)
    {
        if (listRuleType != getListRuleType())
        {
            return false;
        }

        if (!StringUtil.equalsIgnoreCase(selector.getListDefName(), getListDefName()))
        {
            return false;
        }

        boolean listLevelNameMatches = StringUtil.equalsIgnoreCase(selector.getListLevelName(), getListLevelName());
        boolean lfoNameMatches = StringUtil.equalsIgnoreCase(selector.getLfoName(), getLfoName());
        switch (listRuleType)
        {
            case CssListRuleType.LIST_DEF:
                // We have already checked that the list def name matches.
                return true;
            case CssListRuleType.LIST_LEVEL:
                return listLevelNameMatches;
            case CssListRuleType.LIST_LFO:
                return lfoNameMatches;
            case CssListRuleType.LIST_LEVEL_LFO:
                return listLevelNameMatches && lfoNameMatches;
            default:
                Debug.assertTrue(false);
                return false;
        }
    }

    String toCss()
    {
        StringBuilder cssBuilder = new StringBuilder();

        msStringBuilder.append(cssBuilder, "@list ");
        msStringBuilder.append(cssBuilder, getListDefName());

        if (StringUtil.hasChars(getListLevelName()))
        {
            cssBuilder.append(':');
            msStringBuilder.append(cssBuilder, getListLevelName());
        }

        if (StringUtil.hasChars(getLfoName()))
        {
            cssBuilder.append(' ');
            msStringBuilder.append(cssBuilder, getLfoName());
        }

        return cssBuilder.toString();
    }

    String getListDefName() { return mListDefName; };

    private String mListDefName;

    String getListLevelName() { return mListLevelName; };

    private String mListLevelName;

    String getLfoName() { return mLfoName; };

    private String mLfoName;

    /*CssListRuleType*/int getListRuleType() { return mListRuleType; };

    private /*CssListRuleType*/int mListRuleType;
}

