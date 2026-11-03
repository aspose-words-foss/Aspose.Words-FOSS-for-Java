// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents a sequence of simple CSS selectors.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/css3-selectors/#sequence-of-simple-selectors
/// </remarks>
class CssCompoundSelector extends CssCombinableSelector
{
    CssCompoundSelector(CssSimpleSelector head, CssCombinableSelector tail)
    {
        Debug.assertTrue(head != null);
        Debug.assertTrue(tail != null);

        // A pseudo-element must be the last component of a compound selector,
        // so the head cannot be a pseudo-element.
        Debug.assertTrue(!(head instanceof CssPseudoElementSelector));

        mHead = head;
        mTail = tail;
    }

    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        // SPEED. We use the short-circuit logical and operator (&&) to skip tail selector matching if the head selector
        // doesn't match the element. This improves performance slightly, since selector matching is a very frequent
        // operation.
        return mHead.selects(element, documentMode) && mTail.selects(element, documentMode);
    }

    /*override*/ CssSelectorSpecificity getSpecificity() { return mHead.getSpecificity().add(mTail.getSpecificity()); }

    /*override*/ String toCss()
    {
        return mHead.toCss() + mTail.toCss();
    }

    /*override*/ boolean selectsPseudoElement()
    {
        // A pseudo-element must be the last component of a compound selector,
        // so the head cannot be a pseudo-element.
        return mTail.selectsPseudoElement();
    }

    /*override*/ /*HtmlElementPart*/int getSelectedPart()
    {
        // A pseudo-element must be the last component of a compound selector, so we should look at the tail to tell
        // whether this selector selects a pseudo-element. The head always selects the real element.
        return mTail.getSelectedPart();
    }

    CssSimpleSelector getHead() { return mHead; }

    CssCombinableSelector getTail() { return mTail; }

    protected /*override*/ String makePreferableStyleName()
    {
        return mHead.getPreferableStyleName() + "_" + mTail.getPreferableStyleName();
    }

    private /*final*/ CssSimpleSelector mHead;

    private /*final*/ CssCombinableSelector mTail;
}

