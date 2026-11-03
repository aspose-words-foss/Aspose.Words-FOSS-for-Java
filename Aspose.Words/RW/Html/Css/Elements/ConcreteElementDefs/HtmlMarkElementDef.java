// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;


class HtmlMarkElementDef extends HtmlElementDef
{
    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("background-color", G_YELLOW_COLOR_VALUE));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("color", CssValue.Black));
    }

    private static /*final*/ CssHashValue G_YELLOW_COLOR_VALUE = CssHashValue.fromColor(DrColor.YELLOW);
}

