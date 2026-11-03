// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlFigureElementDef extends HtmlElementDef
{
    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.
        cssDeclarations.addOrReplace(G_DEFAULT_STYLE);
    }

    private static /*final*/ CssValue G_FORTY_PX = new CssLengthValue(40.0, CssUnit.PX);

    private static /*final*/ CssDeclarationCollection G_DEFAULT_STYLE = new CssDeclarationCollection(
        new CssSpecifiedDeclaration("display", CssValue.Block),
        new CssSpecifiedDeclaration("margin-left", G_FORTY_PX),
        new CssSpecifiedDeclaration("margin-right", G_FORTY_PX));

}

