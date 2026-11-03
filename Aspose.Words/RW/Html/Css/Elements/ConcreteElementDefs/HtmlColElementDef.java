// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlColElementDef extends HtmlElementDef
{
    protected /*override*/ boolean canBeHidden()
    {
        return false;
    }

    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("display", CssValue.TableColumn));
        if (element.getAttributeValue("hidden") != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("visibility", CssValue.Collapse));
        }

        // WORDSNET-12020 Support 'width' attribute of 'col' element in HTML import
        CssValue widthValue = CssValue.parseLegacyDimension(element.getAttributeValue("width"));
        if (widthValue != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("width", widthValue));
        }
    }
}

