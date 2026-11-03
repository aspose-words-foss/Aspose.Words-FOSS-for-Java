// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/02/2023 by Artem Shabarshin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/*struct*/ final class HtmlTreeAndCss
{
	public HtmlTreeAndCss(){}
	
    HtmlDocument getHtml() { return mHtml; }; void setHtml(HtmlDocument value) { mHtml = value; };

    private HtmlDocument mHtml;

    ArrayList<CssStyleRule> getStyleRules() { return mStyleRules; }; void setStyleRules(ArrayList<CssStyleRule> value) { mStyleRules = value; };

    private ArrayList<CssStyleRule> mStyleRules;

    ArrayList<CssPageRule> getPageRules() { return mPageRules; }; void setPageRules(ArrayList<CssPageRule> value) { mPageRules = value; };

    private ArrayList<CssPageRule> mPageRules;
}

