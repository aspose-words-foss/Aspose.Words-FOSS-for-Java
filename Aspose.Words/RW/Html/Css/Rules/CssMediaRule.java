// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/06/2019 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Represents a CSS @media rule.
/// </summary>
/// <remarks>
/// See https://www.w3.org/TR/CSS2/media.html#at-media-rule
/// Nested rules are style, @page, or other @media rules.
/// </remarks>
class CssMediaRule extends CssRule
{
    CssMediaRule(
        CssMediaQueryList mediaQueryList,
        CssRule[] rules)
    {
    	super(CssRuleType.MEDIA);
	
        Debug.assertTrue(mediaQueryList != null);
        Debug.assertTrue(rules != null);

        mMediaQueryList = mediaQueryList;
        mRules = rules;
    }

    CssMediaQueryList getMediaQueryList() { return mMediaQueryList; };

    private CssMediaQueryList mMediaQueryList;

    CssRule[] getRules() { return mRules; };

    private CssRule[] mRules;

    /*override*/ String toCss()
    {
        StringBuilder result = new StringBuilder();
        msStringBuilder.append(result, "@media ");
        msStringBuilder.append(result, getMediaQueryList());
        msStringBuilder.append(result, " { ");
        boolean isFirstRule = true;
        for (CssRule rule : getRules())
        {
            if (!isFirstRule)
            {
                result.append(' ');
            }
            isFirstRule = false;
            msStringBuilder.append(result, rule.toCss());
        }
        msStringBuilder.append(result, " }");
        return result.toString();
    }
}

