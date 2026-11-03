// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/06/2017 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Collection;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ms.System.msString;
import java.util.HashMap;
import com.aspose.words.ref.Ref;


/// <summary>
/// Matches a set of <see cref="CssStyleRule"/> against HTML elements during depth-first traversal of an HTML tree.
/// </summary>
class CssMatcher
{
    CssMatcher(Collection<CssStyleRule> cssRules, /*CssDocumentMode*/int documentMode)
    {
        // It's important that we initialize the document mode value before we start caching rules with ID selectors.
        // Otherwise, normalization of ID values won't work correctly.
        mDocumentMode = documentMode;

        ArrayList<CssRuleMatcher> ruleMatchers = new ArrayList<CssRuleMatcher>(cssRules.size());
        for (CssStyleRule rule : cssRules)
        {
            // SPEED - In order to speed up matching of ID selectors, we collect rules matched by each ID selector into
            // a dictionary.
            if (cacheRuleWithIdSelector(rule))
            {
                // If the rule contains any other selector types in addition to ID selectors, we split the rule into two
                // parts: one with ID selectors and another with all other selectors.
                // This is needed to preserve the order of rule matching.
                if (rule.getSelectors().length > 1)
                {
                    ArrayList<CssSelector> nonIdSelectors = new ArrayList<CssSelector>();
                    for (CssSelector selector : rule.getSelectors())
                    {
                        if (!(selector instanceof CssIdSelector))
                            msArrayList.add(nonIdSelectors, selector);
                    }
                    if (nonIdSelectors.size() > 0)
                    {
                        CssStyleRule nonIdSelectorsRule = new CssStyleRule(msArrayList.toArray(nonIdSelectors, new CssSelector[0]), rule.getDeclarations());
                        msArrayList.add(ruleMatchers, new CssRuleMatcher(nonIdSelectorsRule, documentMode));
                    }
                }
            }
            else
            {
                msArrayList.add(ruleMatchers, new CssRuleMatcher(rule, documentMode));
            }
        }
        mRuleMatchers = msArrayList.toArray(ruleMatchers, new CssRuleMatcher[0]);
    }

    CssMatchedRules push(IElementProvider element)
    {
        Debug.assertTrue(element != null);
        // SPEED - In order to speed up matching of ID selectors, we match them separately from other types of selectors.
        // For ID selectors, we get matched CSS rules directly by element's ID value from a dictionary.
        CssMatchedRules matchedRules = matchCssRulesByCssIdSelector(element);

        for (CssRuleMatcher ruleMatcher : mRuleMatchers)
        {
            ArrayList<CssSelector> matchedSelectors = ruleMatcher.push(element);
            if (matchedSelectors != null)
            {
                if (matchedRules == null)
                    matchedRules = new CssMatchedRules();

                matchedRules.add(matchedSelectors, ruleMatcher.getDeclarations());
            }
        }

        if (matchedRules == null)
        {
            matchedRules = CssMatchedRules.EMPTY;
        }
        return matchedRules;
    }

    void pop()
    {
        for (CssRuleMatcher ruleMatcher : mRuleMatchers)
        {
            ruleMatcher.pop();
        }
    }

    private boolean cacheRuleWithIdSelector(CssStyleRule rule)
    {
        Debug.assertTrue(rule.getSelectors().length > 0);
        boolean ruleContainsCssIdSelectors = false;
        for (CssSelector cssSelector : rule.getSelectors())
        {
            if (cssSelector instanceof CssIdSelector)
            {
                CssIdSelector cssIdSelector = (CssIdSelector)cssSelector;

                String id = normalizeElementId(cssIdSelector.getId());
                ArrayList<CssDeclarationCollection> matchedRulesDeclarations = null;
                Ref<ArrayList<CssDeclarationCollection>> referenceToMatchedRulesDeclarations = new Ref<ArrayList<CssDeclarationCollection>>(matchedRulesDeclarations);
                boolean outRefCondition0 = !msDictionary.tryGetValue(mRulesDeclarationsMatchedByIdSelector, id, /*out*/ referenceToMatchedRulesDeclarations);
                matchedRulesDeclarations = referenceToMatchedRulesDeclarations.get();
                if (outRefCondition0)
                {
                    matchedRulesDeclarations = new ArrayList<CssDeclarationCollection>();
                    msDictionary.add(mRulesDeclarationsMatchedByIdSelector, id, matchedRulesDeclarations);
                }
                msArrayList.add(matchedRulesDeclarations, rule.getDeclarations());
                ruleContainsCssIdSelectors = true;
            }
        }
        return ruleContainsCssIdSelectors;
    }

    private CssMatchedRules matchCssRulesByCssIdSelector(IElementProvider element)
    {
        CssMatchedRules matchedRules = null;

        String elementId = element.getAttributeValue("id");
        if (elementId != null)
        {
            elementId = normalizeElementId(elementId);

            ArrayList<CssDeclarationCollection> rulesDeclarationsMatchedById = null;
            Ref<ArrayList<CssDeclarationCollection>> referenceToRulesDeclarationsMatchedById = new Ref<ArrayList<CssDeclarationCollection>>(rulesDeclarationsMatchedById);
            boolean outRefCondition1 = msDictionary.tryGetValue(mRulesDeclarationsMatchedByIdSelector, elementId, /*out*/ referenceToRulesDeclarationsMatchedById);
            rulesDeclarationsMatchedById = referenceToRulesDeclarationsMatchedById.get();
            if (outRefCondition1)
            {
                matchedRules = new CssMatchedRules();
                CssIdSelector idSelector = new CssIdSelector(elementId);
                for (CssDeclarationCollection ruleDeclarations : rulesDeclarationsMatchedById)
                {
                    ArrayList<CssSelector> list = new ArrayList<CssSelector>(); { list.add(idSelector); }
                    matchedRules.add(list, ruleDeclarations);
                }
            }
        }

        return matchedRules;
    }

    private String normalizeElementId(String id)
    {
        return (mDocumentMode == CssDocumentMode.QUIRKS)
            ? msString.toLowerInvariant(id)
            : id;
    }

    private /*final*/ CssRuleMatcher[] mRuleMatchers;

    /// <summary>
    /// List of CSS rules that are matched by certain CSS ID selectors.
    /// </summary>
    /// <remarks>
    /// Key - Value of a CSS ID selector (normalized to lower case if needed).
    /// Value - list of CSS rules matched by that selector.
    /// </remarks>
    private /*final*/ HashMap<String, ArrayList<CssDeclarationCollection>> mRulesDeclarationsMatchedByIdSelector =
        new HashMap<String, ArrayList<CssDeclarationCollection>>();

    private /*final*/ /*CssDocumentMode*/int mDocumentMode;

}

