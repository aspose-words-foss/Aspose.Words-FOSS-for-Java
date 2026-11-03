// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/06/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlFormElementDef extends HtmlElementDef
{
    HtmlFormElementDef(/*CssDocumentMode*/int documentMode)
    {
        mDocumentMode = documentMode;
    }

    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("display", CssValue.Block));

        // Presentational hints.
        if (mDocumentMode == CssDocumentMode.QUIRKS)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-bottom", new CssLengthValue(1.0, CssUnit.EM)));
        }
    }

    private /*final*/ /*CssDocumentMode*/int mDocumentMode;
}
