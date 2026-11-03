// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


class HtmlTextareaElementDef extends HtmlElementDef
{
    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("font-family",
            new CssFontFamilyPropertyValue(CssValue.Monospace)));

        // Presentational hints.

        // The "cols" and "rows" attributes are not supported, as their processing requires information
        // about the font size of the element.

        if (msString.equals(element.getAttributeValue("wrap", ""), "off", StringComparison.ORDINAL_IGNORE_CASE))
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("white-space", CssValue.Pre));
        }
    }
}

