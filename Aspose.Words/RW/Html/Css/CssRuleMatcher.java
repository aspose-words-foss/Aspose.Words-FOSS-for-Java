// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/06/2017 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Matches a <see cref="CssStyleRule"/> against HTML elements during depth-first traversal of an HTML tree.
/// </summary>
class CssRuleMatcher
{
    CssRuleMatcher(CssStyleRule rule, /*CssDocumentMode*/int documentMode)
    {
        mDeclarations = rule.getDeclarations();
        mSelectorMatchers = new CssSelectorMatcher[rule.getSelectors().length];
        for (int i = 0; i < rule.getSelectors().length; i++)
        {
            mSelectorMatchers[i] = rule.getSelectors()[i].createMatcher(documentMode);
        }
    }

    ArrayList<CssSelector> push(IElementProvider element)
    {
        Debug.assertTrue(element != null);

        ArrayList<CssSelector> matchedSelectors = null;
        for (CssSelectorMatcher selectorMatcher : mSelectorMatchers)
        {
            boolean matches = selectorMatcher.push(element);
            if (matches)
            {
                if (matchedSelectors == null)
                    matchedSelectors =  new ArrayList<CssSelector>(mSelectorMatchers.length);

                msArrayList.add(matchedSelectors, selectorMatcher.getSelector());
            }
        }

        return matchedSelectors;
    }

    void pop()
    {
        for (CssSelectorMatcher selectorMatcher : mSelectorMatchers)
        {
            selectorMatcher.pop();
        }
    }

    CssDeclarationCollection getDeclarations() { return mDeclarations; }

    private /*final*/ CssSelectorMatcher[] mSelectorMatchers;
    private /*final*/ CssDeclarationCollection mDeclarations;
}

