// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlH1ElementDef extends HtmlElementDef
{
    HtmlH1ElementDef(boolean applyFormattingAsMsWord)
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
        CssLengthValue fontSizeValue = mApplyFormattingAsMsWord
            ? new CssLengthValue(24.0, CssUnit.PT)
            : getHtmlFontSize(element);
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("font-size", fontSizeValue));

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("font-weight", CssValue.Bold));
    }

    private static CssLengthValue getHtmlFontSize(IHtmlElementProvider element)
    {
        int nestingLevel = 0;
        final int MAX_NESTING_LEVEL = 5;
        IElementProvider parent = element.getParentElement();
        while ((parent != null) && (nestingLevel < MAX_NESTING_LEVEL))
        {
            if (("article".equals(parent.getElementName())) ||
                ("aside".equals(parent.getElementName())) ||
                ("nav".equals(parent.getElementName())) ||
                ("section".equals(parent.getElementName())))
            {
                ++nestingLevel;
            }
            parent = parent.getParentElement();
        }

        CssLengthValue fontSizeValue;
        switch (nestingLevel)
        {
            case 0:
                fontSizeValue = new CssLengthValue(2.0, CssUnit.EM);
                break;
            case 1:
                fontSizeValue = new CssLengthValue(1.5, CssUnit.EM);
                break;
            case 2:
                fontSizeValue = new CssLengthValue(1.17, CssUnit.EM);
                break;
            case 3:
                fontSizeValue = new CssLengthValue(1.0, CssUnit.EM);
                break;
            case 4:
                fontSizeValue = new CssLengthValue(0.83, CssUnit.EM);
                break;
            default:
                fontSizeValue = new CssLengthValue(0.67, CssUnit.EM);
                break;
        }

        return fontSizeValue;
    }

    private /*final*/ boolean mApplyFormattingAsMsWord;
}

