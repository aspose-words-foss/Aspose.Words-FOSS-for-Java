// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlSupElementDef extends HtmlElementDef
{
    HtmlSupElementDef(boolean applyFormattingAsMsWord)
    {
        mApplyFormattingAsMsWord = applyFormattingAsMsWord;
    }

    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("vertical-align", CssValue.Super));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("line-height", CssValue.Normal));
    }

    protected /*override*/ void applyOverridableFontStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.
        if (!mApplyFormattingAsMsWord)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("font-size", CssValue.Smaller));
        }
    }

    private /*final*/ boolean mApplyFormattingAsMsWord;
}

