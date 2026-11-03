// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


class HtmlColgroupElementDef extends HtmlElementDef
{
    protected /*override*/ boolean canBeHidden()
    {
        return false;
    }

    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("display", CssValue.TableColumnGroup));
        if (element.getAttributeValue("hidden") != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("visibility", CssValue.Collapse));
        }

        // Presentational hints.

        // Parent TABLE's "rules" attribute
        IElementProvider parent = element.getParentElement();
        if ((parent != null) && ("table".equals(parent.getElementName())))
        {
            if (msString.equals(parent.getAttributeValue("rules", ""), "groups", StringComparison.ORDINAL_IGNORE_CASE))
            {
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-width", CssValue.OnePx));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-width", CssValue.OnePx));

                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.Solid));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.Solid));
            }
        }
    }
}

