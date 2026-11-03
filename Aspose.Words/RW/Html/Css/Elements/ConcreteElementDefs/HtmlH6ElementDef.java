// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlH6ElementDef extends HtmlElementDef
{
    HtmlH6ElementDef(boolean applyFormattingAsMsWord)
    {
        mApplyFormattingAsMsWord = applyFormattingAsMsWord;
    }

    protected /*override*/ void applyStyles(
        IHtmlElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("display", CssValue.Block));

        // Presentational hints.
        translateAlignAttributeToCss(element, cssDeclarations, false);
    }

    protected /*override*/ void applyOverridableFontStyles(
        IHtmlElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations)
    {
        CssValue fontSize = mApplyFormattingAsMsWord
            ? new CssLengthValue(7.5, CssUnit.PT)
            : new CssLengthValue(0.67, CssUnit.EM);
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("font-size", fontSize));

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("font-weight", CssValue.Bold));
    }

    private /*final*/ boolean mApplyFormattingAsMsWord;
}

