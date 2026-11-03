// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


class HtmlTRElementDef extends HtmlElementDef
{
    protected /*override*/ boolean canBeHidden()
    {
        return false;
    }

    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("display", CssValue.TableRow));

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-color", CssValue.Inherit));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-color", CssValue.Inherit));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-color", CssValue.Inherit));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-color", CssValue.Inherit));

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("vertical-align", CssValue.Inherit));

        if (element.getAttributeValue("hidden") != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("visibility", CssValue.Collapse));
        }

        // Presentational hints.

        // The "background" attribute is not supported.

        translateAlignAttributeToCss(element, cssDeclarations, true);
        translateVAlignAttributeToCss(element, cssDeclarations);

        // "height" attribute
        // Read HTML row height. Normally, there is no such attribute, but we do write it
        // (like Microsoft Word) for last row to support misaligned columns.
        String heightStr = element.getAttributeValue("height");
        if (StringUtil.hasChars(heightStr))
        {
            CssValue heightValue = CssValue.parseLegacyDimension(heightStr);
            if (heightValue == null)
                heightValue = CssValue.parseLegacyPixelLength(heightStr);
            if (heightValue != null)
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("height", heightValue));
        }

        translateBGColorAttributeToCss(element, cssDeclarations);

        //>>>>>>>> #region  Parent TABLE's attributes

        // Find the parent TABLE element.
        IElementProvider parent = element.getParentElement();
        if ((parent != null) && (("thead".equals(parent.getElementName())) || ("tbody".equals(parent.getElementName())) || ("tfoot".equals(parent.getElementName()))))
        {
            parent = parent.getParentElement();
        }

        // Process parent TABLE's attributes.
        if ((parent != null) && ("table".equals(parent.getElementName())))
        {
            // "rules" attribute
            if (msString.equals(parent.getAttributeValue("rules", ""), "rows", StringComparison.ORDINAL_IGNORE_CASE))
            {
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-width", CssValue.OnePx));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-width", CssValue.OnePx));

                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.Solid));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.Solid));
            }
        }

        //<<<<<<<< #endregion 
    }
}

