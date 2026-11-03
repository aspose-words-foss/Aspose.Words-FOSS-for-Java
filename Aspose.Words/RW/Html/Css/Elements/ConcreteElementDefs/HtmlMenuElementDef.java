// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlMenuElementDef extends HtmlElementDef
{
    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("display", CssValue.Block));

        int nestingLevel = 0;
        final int MAX_NESTING_LEVEL = 2;
        IElementProvider parent = element.getParentElement();
        while ((parent != null) && (nestingLevel < MAX_NESTING_LEVEL))
        {
            if (("ol".equals(parent.getElementName())) ||
                ("ul".equals(parent.getElementName())) ||
                ("dir".equals(parent.getElementName())) ||
                ("menu".equals(parent.getElementName())))
            {
                ++nestingLevel;
            }
            parent = parent.getParentElement();
        }

        CssValue listStyleTypeValue;
        switch (nestingLevel)
        {
            case 0:
                listStyleTypeValue = CssValue.Disc;
                break;
            case 1:
                listStyleTypeValue = CssValue.Circle;
                break;
            default:
                listStyleTypeValue = CssValue.Square;
                break;
        }
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("list-style-type", listStyleTypeValue));

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("-aw-padding-start", new CssLengthValue(40.0, CssUnit.PX)));
    }
}

