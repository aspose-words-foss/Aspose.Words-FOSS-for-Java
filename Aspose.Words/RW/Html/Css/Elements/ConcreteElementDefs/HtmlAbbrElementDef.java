// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlAbbrElementDef extends HtmlElementDef
{
    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.
        if (element.getAttributeValue("title") != null)
        {
            cssDeclarations.addOrReplace(G_DEFAULT_STYLE);
        }
    }

    private static /*final*/ CssDeclarationCollection G_DEFAULT_STYLE = new CssDeclarationCollection(
        new CssSpecifiedDeclaration("border-bottom-style", CssValue.Dotted),
        new CssSpecifiedDeclaration("border-bottom-width", CssValue.OnePx));
}

