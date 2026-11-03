// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/06/2017 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Matches a <see cref="CssCombinableSelector"/> against HTML elements during depth-first traversal of an HTML tree.
/// </summary>
class CssCombinableSelectorMatcher extends CssSelectorMatcher
{
    CssCombinableSelectorMatcher(CssCombinableSelector selector, /*CssDocumentMode*/int documentMode)
    {
    	super(selector, documentMode);
	
        // Empty constructor.
    }

    /*override*/ boolean push(IElementProvider element)
    {
        // Implicit (anonymous) elements are not in the HTML tree and they cannot be selected by selectors.
        return (!element.isImplicit()) &&
            ((CssCombinableSelector)getSelector()).selects(element, getDocumentMode());
    }

    /*override*/ void pop()
    {
        // Empty method. Nothing to do.
    }
}

