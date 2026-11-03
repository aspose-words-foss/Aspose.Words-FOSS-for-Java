// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlPreElementDef extends HtmlElementDef
{
    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("display", CssValue.Block));

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("font-family",
                                                                 new CssFontFamilyPropertyValue(CssValue.Monospace)));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("white-space", CssValue.Pre));

        // Presentational hints.

        if (element.getAttributeValue("wrap") != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("white-space", CssValue.PreWrap));
        }
    }
}

