// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/06/2017 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.IntStack;


/// <summary>
/// Matches a <see cref="CssComplexSelector"/> against HTML elements during depth-first traversal of an HTML tree.
/// </summary>
class CssComplexSelectorMatcher extends CssSelectorMatcher
{
    CssComplexSelectorMatcher(CssComplexSelector selector, /*CssDocumentMode*/int documentMode)
    {
    	super(selector, documentMode);
	
        mCombinators = selector.toCombinatorArray();
        Debug.assertTrue(mCombinators.length >= 2);
        mCombinatorIndexTransitions = new int[mCombinators.length];
        
        // The transition table links 
        int combinatorIndex = mCombinatorIndexTransitions.length - 1;
        mCombinatorIndexTransitions[mCombinatorIndexTransitions.length - 1] = combinatorIndex;
        for (int i = mCombinatorIndexTransitions.length - 1; i >= 1; i--)
        {
            if (mCombinators[i] instanceof CssDescendantCombinator)
            {
                mCombinatorIndexTransitions[i - 1] = combinatorIndex;
                combinatorIndex = i - 1;
            }
            else
            {
                // These transitions are not required and we fill them for debug purposes only.
                mCombinatorIndexTransitions[i - 1] = -1;
            }
        }

        // First level states to start with.
        mCurrentCombinatorIndexes.push(combinatorIndex);
        mSubtreeRoots.push(0);
    }

    /*override*/ boolean push(IElementProvider element)
    {
        Debug.assertTrue(mCurrentCombinatorIndexes.getCount() > 0);
        Debug.assertTrue(mSubtreeRoots.getCount() > 0);

        int combinatorIndex = mCurrentCombinatorIndexes.peek();
        Debug.assertTrue(combinatorIndex >= 0);
        int subtreeRootLevel = mSubtreeRoots.peek();
        Debug.assertTrue(subtreeRootLevel >= 0);

        // Implicit (anonymous) elements are not in the HTML tree and they cannot be selected by selectors.
        boolean matched = (!element.isImplicit()) &&
            matches(element, combinatorIndex, mCurrentCombinatorIndexes.getCount(), subtreeRootLevel);

        // SPEED. In order to speed up selector matching, we split complex expressions into sub-expressions by descendant
        // combinators. Sub-expressions are matched from left to right. As soon as we find an element that matches the last
        // selector of a sub-expression, we start looking for the next sub-expression. A sub-expression is matched in the
        // reversed order. That is, first we search for the last element, and then go backwards and match other selectors
        // of that sub-expression.
        // If the current sub-expression has been matched, at the next level we will be looking for the next sub-expression.
        int nextLevelCombinatorIndex = (matched)
            ? mCombinatorIndexTransitions[combinatorIndex]
            : combinatorIndex;
        Debug.assertTrue(nextLevelCombinatorIndex >= 0);
        mCurrentCombinatorIndexes.push(nextLevelCombinatorIndex);

        if (nextLevelCombinatorIndex != combinatorIndex)
        {
            subtreeRootLevel = mCurrentCombinatorIndexes.getCount();
        }
        mSubtreeRoots.push(subtreeRootLevel);

        return matched && (combinatorIndex == (mCombinators.length - 1));
    }

    /*override*/ void pop()
    {
        Debug.assertTrue(mCurrentCombinatorIndexes.getCount() > 1);
        Debug.assertTrue(mSubtreeRoots.getCount() > 1);
        mCurrentCombinatorIndexes.pop();
        mSubtreeRoots.pop();
    }

    private boolean matches(IElementProvider element, int combinatorIndex, int elementLevel, int subtreeRootLevel)
    {
        Debug.assertTrue(combinatorIndex >= 0);

        if (element == null)
        {
            return false;
        }

        if (!mCombinators[combinatorIndex].getSelector().selects(element, getDocumentMode()))
        {
            return false;
        }

        // Matched the rightmost selector of a sub-expression. Let's also check selectors to the left of the mathed one.
        CssCombinator combinator = mCombinators[combinatorIndex];
        // Sub-expressions are bounded by descendant combinators. Note that the first (leftmost) combinator is also always
        // descendant, so this condition prevents us from looking past the start of the combinator list.
        if (mCombinators[combinatorIndex] instanceof CssDescendantCombinator)
        {
            return true;
        }
        if (combinator instanceof CssGeneralSiblingCombinator)
        {
            // Go left the tree until we match another element.
            IElementProvider prevElement = element.getPreviousSiblingElement();
            while (prevElement != null)
            {
                if (matches(prevElement, combinatorIndex - 1, elementLevel, subtreeRootLevel))
                {
                    return true;
                }
                prevElement = prevElement.getPreviousSiblingElement();
            }
            return false;
        }
        if (combinator instanceof CssAdjacentSiblingCombinator)
        {
            // Go left the tree by one element.
            return matches(element.getPreviousSiblingElement(), combinatorIndex - 1, elementLevel, subtreeRootLevel);
        }
        if (combinator instanceof CssChildCombinator)
        {
            // Go up the tree by one level.
            // We are not allowed to search beyond the current subtree root.
            return (elementLevel > subtreeRootLevel) &&
                matches(element.getParentElement(), combinatorIndex - 1, elementLevel - 1, subtreeRootLevel);
            
        }
        throw new IllegalStateException("Unknown combinator type.");
    }

    private /*final*/ IntStack mCurrentCombinatorIndexes = new IntStack();
    private /*final*/ IntStack mSubtreeRoots = new IntStack();
    private /*final*/ int[] mCombinatorIndexTransitions;
    private /*final*/ CssCombinator[] mCombinators;
}

