// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/02/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;

class HtmlCaptionElementDef extends HtmlElementDef
{
    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("display", CssValue.TableCaption));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("text-align", CssValue.Center));

        // Presentational hints.
        switch (gStringSwitchMap.of(msString.toLowerInvariant(element.getAttributeValue("align", ""))))
        {
            case /*"top"*/0:
            case /*"left"*/1:
            case /*"right"*/2:
                // Modern browsers accept "left" and "right" values, but treat them as "top": the caption is placed
                // before the table, and the caption contents are centered.
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("caption-side", CssValue.Top));
                break;
            case /*"bottom"*/3:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("caption-side", CssValue.Bottom));
                break;
            default:
                // Other 'align' attribute values are ignored.
                break;
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"top",
		"left",
		"right",
		"bottom"
	);

}
