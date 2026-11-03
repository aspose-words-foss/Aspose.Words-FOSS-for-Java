// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2019 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;

/// <summary>
/// Represents a CSS @import rule.
/// </summary>
class CssImportRule extends CssRule
{
    CssImportRule(
        String url,
        CssMediaQueryList mediaQuery)
    {
    	super(CssRuleType.IMPORT);
	
        Debug.assertTrue(StringUtil.hasChars(url));
        Debug.assertTrue(mediaQuery != null);

        mUrl = url;
        mMediaQuery = mediaQuery;
    }

    String getUrl() { return mUrl; };

    private String mUrl;

    CssMediaQueryList getMediaQuery() { return mMediaQuery; };

    private CssMediaQueryList mMediaQuery;

    /*override*/ String toCss()
    {
        return "@import url('" + getUrl() + "') " + getMediaQuery() + ";";
    }
}

