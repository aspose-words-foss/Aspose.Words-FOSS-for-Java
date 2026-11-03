// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Represents a complex CSS selector, which contains a combinable selector and one or more combinators.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/css3-selectors/#combinators
/// </remarks>
class CssComplexSelector extends CssSelector
{
    CssComplexSelector(CssCombinableSelector left, ArrayList<CssCombinator> combinators)
    {
        Debug.assertTrue(left != null);
        Debug.assertTrue(combinators != null);
        Debug.assertTrue(combinators.size() > 0);

        mLeftSelector = left;
        mCombinators = combinators;

        mSpecificity = left.getSpecificity();
        for (CssCombinator combinator : combinators)
        {
            mSpecificity = mSpecificity.add(combinator.getSelector().getSpecificity());
        }
    }

    /*override*/ /*HtmlElementPart*/int getSelectedPart()
    {
        // Only the last selector in a combination is allowed to select a pseudo-element. All other selectors select
        // the real element itself and there is no need to check them.
        return mCombinators.get(mCombinators.size() - 1).getSelector().getSelectedPart();
    }

    /*override*/ CssSelectorSpecificity getSpecificity() { return mSpecificity; }

    /*override*/ String toCss()
    {
        StringBuilder result = new StringBuilder();
        msStringBuilder.append(result, mLeftSelector.toCss());
        for (CssCombinator combinator : mCombinators)
        {
            msStringBuilder.append(result, combinator.toCss());
        }
        return result.toString();
    }

    /// <summary>
    /// Creates an instance of <see cref="CssComplexSelectorMatcher"/> to match this selector against HTML elements 
    /// in the specified mode.
    /// </summary>
    /*override*/ CssSelectorMatcher createMatcher(/*CssDocumentMode*/int documentMode)
    {
        return new CssComplexSelectorMatcher(this, documentMode);
    }

    /// <summary>
    /// Converts this complex selector to an array of combinators.
    /// The leftmost selector becomes <see cref="CssDescendantCombinator"/>. Other combinators are copied.
    /// </summary>
    CssCombinator[] toCombinatorArray()
    {
        CssCombinator[] result = new CssCombinator[mCombinators.size() + 1];
        result[0] = new CssDescendantCombinator(mLeftSelector);
        for (int i = 0; i < mCombinators.size(); i++)
        {
            result[i + 1] = mCombinators.get(i);
        }
        return result;
    }

    protected /*override*/ String makePreferableStyleName()
    {
        StringBuilder result = new StringBuilder();
        msStringBuilder.append(result, mLeftSelector.getPreferableStyleName());
        for (CssCombinator combinator : mCombinators)
        {
            msStringBuilder.append(result, combinator.getPreferableStyleName());
        }
        return result.toString();
    }

    private /*final*/ CssCombinableSelector mLeftSelector;

    private /*final*/ ArrayList<CssCombinator> mCombinators;

    private /*final*/ CssSelectorSpecificity mSpecificity;
}

