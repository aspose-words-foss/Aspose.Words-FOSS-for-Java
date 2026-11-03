// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Helps to store information about a pseudo-element in <see cref="DocumentFormatter"/>.
/// Stores intermediate values used for performance optimization.
/// </summary>
class PseudoElementInfo extends HtmlElementInfo
{
    PseudoElementInfo(
        IHtmlElementProvider element,
        CssDeclarationCollection declarations,
        /*HtmlElementPart*/int part,
        PseudoElementContent content)
    {
    	super(element, declarations);
	
        Debug.assertTrue(part != HtmlElementPart.ELEMENT);
        Debug.assertTrue(content != null);

        mPart = part;
        mGeneratedContent = content;
    }

    /*override*/ CssDeclarationCollection getBeforePseudoElementDeclarations() { return CssDeclarationCollection.EMPTY; }

    /*override*/ CssDeclarationCollection getAfterPseudoElementDeclarations() { return CssDeclarationCollection.EMPTY; }

    /*override*/ /*HtmlElementPart*/int getPart() { return mPart; }

    /*override*/ PseudoElementContent getGeneratedContent() { return mGeneratedContent; }

    private /*final*/ /*HtmlElementPart*/int mPart;

    private /*final*/ PseudoElementContent mGeneratedContent;
}

