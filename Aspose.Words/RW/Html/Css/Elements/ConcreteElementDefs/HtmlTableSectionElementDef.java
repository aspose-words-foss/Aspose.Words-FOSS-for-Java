// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


class HtmlTableSectionElementDef extends HtmlElementDef
{
    protected /*override*/ boolean canBeHidden()
    {
        return false;
    }

    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("vertical-align", CssValue.Middle));

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-color", CssValue.Inherit));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-color", CssValue.Inherit));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-color", CssValue.Inherit));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-color", CssValue.Inherit));

        switch (gStringSwitchMap.of(element.getElementName()))
        {
            case /*"thead"*/0:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("display", CssValue.TableHeaderGroup));
                break;
            case /*"tbody"*/1:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("display", CssValue.TableRowGroup));
                break;
            case /*"tfoot"*/2:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("display", CssValue.TableFooterGroup));
                break;
            default:
                Debug.assertTrue(false, "Unknown table's section.");
                break;
        }

        if (element.getAttributeValue("hidden") != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("visibility", CssValue.Collapse));
        }

        // Presentational hints.

        // The "background" attribute is not supported.

        translateAlignAttributeToCss(element, cssDeclarations, true);
        translateVAlignAttributeToCss(element, cssDeclarations);
        translateBGColorAttributeToCss(element, cssDeclarations);

        // Parent TABLE's "rules" attribute
        IElementProvider parent = element.getParentElement();
        if ((parent != null) && ("table".equals(parent.getElementName())))
        {
            if (msString.equals(parent.getAttributeValue("rules", ""), "groups",
                StringComparison.ORDINAL_IGNORE_CASE))
            {
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-width", CssValue.OnePx));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-width", CssValue.OnePx));

                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.Solid));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.Solid));
            }
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"thead",
		"tbody",
		"tfoot"
	);

}

