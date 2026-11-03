// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlBlockquoteElementDef extends HtmlElementDef
{
    HtmlBlockquoteElementDef(boolean applyFormattingAsMsWord)
    {
        CssDeclarationCollectionBuilder defaultStyleBuilder = new CssDeclarationCollectionBuilder();

        defaultStyleBuilder.add(new CssSpecifiedDeclaration("display", CssValue.Block));

        if (applyFormattingAsMsWord)
        {
            // MS Word uses the following default margins for `blockquote` elements.

            CssValue horizontalMargin = new CssLengthValue(36.0, CssUnit.PT);
            defaultStyleBuilder.add(new CssSpecifiedDeclaration("margin-left", horizontalMargin));
            defaultStyleBuilder.add(new CssSpecifiedDeclaration("margin-right", horizontalMargin));

            CssValue verticalMargin = new CssLengthValue(5.0, CssUnit.PT);
            defaultStyleBuilder.add(new CssSpecifiedDeclaration("margin-top", verticalMargin));
            defaultStyleBuilder.add(new CssSpecifiedDeclaration("margin-bottom", verticalMargin));
        }
        else
        {
            // Default margins as specified by the HTML standard.
            CssValue margin = new CssLengthValue(40.0, CssUnit.PX);
            defaultStyleBuilder.add(new CssSpecifiedDeclaration("margin-left", margin));
            defaultStyleBuilder.add(new CssSpecifiedDeclaration("margin-right", margin));
        }

        mDefaultDeclarations = defaultStyleBuilder.getDeclarations();
    }

    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        cssDeclarations.addOrReplace(mDefaultDeclarations);
    }

    private /*final*/ CssDeclarationCollection mDefaultDeclarations;
}

