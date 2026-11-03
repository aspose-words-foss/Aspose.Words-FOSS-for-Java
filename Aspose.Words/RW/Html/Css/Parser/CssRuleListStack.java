// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/08/2022 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msStack;
import java.util.ArrayList;


/// <summary>
/// Stack of CSS rule lists used by our CSS parser.
/// </summary>
/// <remarks>
/// This class is used for parsing content of nested @media rules in an iterative manner.
/// </remarks>
class CssRuleListStack
{
    void beginNestedMediaRule(CssMediaQueryList mediaQueryList)
    {
        if (nestedRuleLists == null)
            nestedRuleLists = new msStack<ArrayList<CssRule>>();
        if (nestedMediaRuleQueries == null)
            nestedMediaRuleQueries = new msStack<CssMediaQueryList>();

        ArrayList<CssRule> mediaRuleNestedRules = new ArrayList<CssRule>();
        nestedRuleLists.push(mediaRuleNestedRules);
        nestedMediaRuleQueries.push(mediaQueryList);
    }

    void endNestedMediaRule()
    {
        if ((nestedRuleLists.getCount() == 0) || (nestedMediaRuleQueries.getCount() == 0))
        {
            throw new IllegalStateException("The stack of nested rule lists is empty.");
        }

        nestedMediaRuleQueries.pop();
        nestedRuleLists.pop();
    }

    boolean isTopLevel() { return (nestedRuleLists == null) || (nestedRuleLists.getCount() == 0); }

    ArrayList<CssRule> getCurrentRuleList()
    {
        if ( isTopLevel() )
            return mTopLevelRuleList;
        else
            return nestedRuleLists.peek();
    }

    CssMediaQueryList getCurrentMediaQueryList()
    {
        return isTopLevel()
            ? null
            : nestedMediaRuleQueries.peek();
    }

    private /*final*/ ArrayList<CssRule> mTopLevelRuleList = new ArrayList<CssRule>();
    private msStack<ArrayList<CssRule>> nestedRuleLists = null;
    private msStack<CssMediaQueryList> nestedMediaRuleQueries = null;
}

