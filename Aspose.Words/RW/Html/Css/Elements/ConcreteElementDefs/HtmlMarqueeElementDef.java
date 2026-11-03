// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlMarqueeElementDef extends HtmlElementDef
{
    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // Presentational hints.

        translateBGColorAttributeToCss(element, cssDeclarations);
        translateHeightAttributeToCss(element, cssDeclarations);
        translateWidthAttributeToCss(element, cssDeclarations);

        // "hspace" attribute
        CssValue hspaceValue = CssValue.parseLegacyDimension(element.getAttributeValue("hspace"));
        if (hspaceValue != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-left", hspaceValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-right", hspaceValue));
        }

        // "vspace" attribute
        CssValue vspaceValue = CssValue.parseLegacyDimension(element.getAttributeValue("vspace"));
        if (vspaceValue != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-top", vspaceValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-bottom", vspaceValue));
        }
    }
}

