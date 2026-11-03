// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Helps to store information about a real HTML element (not a pseudo-element) in <see cref="DocumentFormatter"/>.
/// Stores intermediate values used for performance optimization.
/// </summary>
class RealElementInfo extends HtmlElementInfo
{
    RealElementInfo(
        IHtmlElementProvider element,
        CssDeclarationCollection declarations,
        CssDeclarationCollection beforePseudoElementDeclarations,
        CssDeclarationCollection afterPseudoElementDeclarations)
    {
    	super(element, declarations);
	
        Debug.assertTrue(beforePseudoElementDeclarations != null);
        Debug.assertTrue(afterPseudoElementDeclarations != null);

        mBeforePseudoElementDeclarations = beforePseudoElementDeclarations;
        mAfterPseudoElementDeclarations = afterPseudoElementDeclarations;
    }

    /*override*/ /*HtmlElementPart*/int getPart() { return HtmlElementPart.ELEMENT; }

    /*override*/ PseudoElementContent getGeneratedContent() { return null; }

    /*override*/ CssDeclarationCollection getBeforePseudoElementDeclarations() { return mBeforePseudoElementDeclarations; }

    /*override*/ CssDeclarationCollection getAfterPseudoElementDeclarations() { return mAfterPseudoElementDeclarations; }

    private /*final*/ CssDeclarationCollection mBeforePseudoElementDeclarations;

    private /*final*/ CssDeclarationCollection mAfterPseudoElementDeclarations;
}

