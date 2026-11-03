// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/06/2017 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.DictionaryExtension;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.HashMap;


/// <summary>
/// Stores CSS style rules that match an HTML element. Rules are grouped by element parts they apply to.
/// </summary>
class CssMatchedRules
{
    void add(ArrayList<CssSelector> selectors, CssDeclarationCollection declarations)
    {
        pickSelectorsAndAddRule(HtmlElementPart.ELEMENT, selectors, declarations);
        pickSelectorsAndAddRule(HtmlElementPart.BEFORE, selectors, declarations);
        pickSelectorsAndAddRule(HtmlElementPart.AFTER, selectors, declarations);
        // Other pseudo-element types are not supported at the moment.
    }

    /// <summary>
    /// Gets a list of <see cref="CssSelector"/> that select only the given part of this HTML element.
    /// </summary>
    ArrayList<CssSelector> getSelectors(/*HtmlElementPart*/int part)
    {
        ArrayList<CssSelector> result = new ArrayList<CssSelector>();
        ArrayList<CssStyleRule> rules = getRules(part);
        if (rules != null)
        {
            for (CssStyleRule elementRule : rules)
            {
                for (CssSelector selector : elementRule.getSelectors())
                {
                    result.add(selector);
                }
            }
        }
        return result;
    }

    /// <summary>
    /// Gets CSS rules that apply to the given part of this HTML element.
    /// </summary>
    ArrayList<CssStyleRule> getRules(/*HtmlElementPart*/int part)
    {
        ArrayList<CssStyleRule> rules;
        if (part == HtmlElementPart.ELEMENT)
        {
            rules = mElementRules;
        }
        else if (mPseudoElementRules != null)
        {
            rules = DictionaryExtension.getValueOrNull(mPseudoElementRules, part);
            if (rules == null)
            {
                rules = G_EMPTY_RULES;
            }
        }
        else
        {
            rules = G_EMPTY_RULES;
        }
        return rules;
    }

    private void pickSelectorsAndAddRule(
        /*HtmlElementPart*/int part,
        ArrayList<CssSelector> selectors,
        CssDeclarationCollection declarations)
    {
        ArrayList<CssSelector> pickedSelectors = new ArrayList<CssSelector>();

        // Pick selectors that match the given part of the element.
        for (CssSelector selector : selectors)
        {
            if (selector.getSelectedPart() == part)
            {
                msArrayList.add(pickedSelectors, selector);
            }
        }

        // Create and store a new rule containing only selectors that match the given part of the element.
        if (pickedSelectors.size() > 0)
        {
            CssSelector[] pickedSelectorsArray = msArrayList.toArray(pickedSelectors, new CssSelector[0]);
            addRule(part, new CssStyleRule(pickedSelectorsArray, declarations));
        }
    }

    private void addRule(/*HtmlElementPart*/int part, CssStyleRule rule)
    {
        if (part == HtmlElementPart.ELEMENT)
        {
            msArrayList.add(mElementRules, rule);
        }
        else
        {
            if (mPseudoElementRules == null)
            {
                mPseudoElementRules = new HashMap<Integer, ArrayList<CssStyleRule>>();
            }
            ArrayList<CssStyleRule> rules = DictionaryExtension.getValueOrNull(mPseudoElementRules, part);
            if (rules == null)
            {
                rules = new ArrayList<CssStyleRule>();
                mPseudoElementRules.put(part, rules);
            }
            msArrayList.add(rules, rule);
        }
    }

    static /*final*/ CssMatchedRules EMPTY = new CssMatchedRules();

    private static /*final*/ ArrayList<CssStyleRule> G_EMPTY_RULES = new ArrayList<CssStyleRule>(0);

    private /*final*/ ArrayList<CssStyleRule> mElementRules = new ArrayList<CssStyleRule>();

    /// <summary>
    /// Most HTML elements don't have visible pseudo-elements. This field is lazily initialized when needed.
    /// </summary>
    private HashMap<Integer, ArrayList<CssStyleRule>> mPseudoElementRules;
}

