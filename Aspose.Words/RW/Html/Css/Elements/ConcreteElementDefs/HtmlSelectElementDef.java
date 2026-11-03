// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/03/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlSelectElementDef extends HtmlElementDef
{
    protected /*override*/ void applyStyles(
        IHtmlElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations)
    {
        // Empty method.
    }

    protected /*override*/ void applyOverridableFontStyles(
        IHtmlElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations)
    {
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("font-family", new CssFontFamilyPropertyValue(
            new CssIdentifierValue("sans-serif"))));
    }
}

