// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;

class HtmlIframeElementDef extends HtmlElementDef
{
    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // Presentational hints.

        // "frameborder" attribute
        String frameborderAttributeValue = msString.toLowerInvariant(element.getAttributeValue("frameborder", ""));
        if (("0".equals(frameborderAttributeValue)) || ("no".equals(frameborderAttributeValue)))
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.None));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.None));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.None));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.None));
        }

        // "align" attribute
        switch (gStringSwitchMap.of(msString.toLowerInvariant(element.getAttributeValue("align", ""))))
        {
            case /*"left"*/0:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("float", CssValue.Left));
                break;
            case /*"right"*/1:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("float", CssValue.Right));
                break;
            case /*"top"*/2:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("vertical-align", CssValue.Top));
                break;
            case /*"baseline"*/3:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("vertical-align", CssValue.Baseline));
                break;
            case /*"texttop"*/4:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("vertical-align", CssValue.TextTop));
                break;
            // "center" and "middle" values should be mapped to special CSS property values
            // that align IFRAME's vertical middle with the parent element's baseline.
            // Consider implementing that special CSS value (for example, "-aw-baseline-middle"),
            // if the CSS "middle" value causes trouble.
            case /*"center"*/5:
            case /*"middle"*/6:
            case /*"absmiddle"*/7:
            case /*"abscenter"*/8:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("vertical-align", CssValue.Middle));
                break;
            case /*"bottom"*/9:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("vertical-align", CssValue.Bottom));
                break;
            default:
                // Other values of the 'align' attribute are ignored.
                break;
        }

        translateHeightAttributeToCss(element, cssDeclarations);
        translateWidthAttributeToCss(element, cssDeclarations);

        // "hspace" attribute
        CssValue hspaceValue = CssValue.parseLegacyDimension(element.getAttributeValue("hspace"));
        if (hspaceValue != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-left", hspaceValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-right", hspaceValue));
        }

        // "vspace" attribute
        CssValue vspaceValue = CssValue.parseLegacyDimension(element.getAttributeValue("vspace"));
        if (vspaceValue != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-top", vspaceValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-bottom", vspaceValue));
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"left",
		"right",
		"top",
		"baseline",
		"texttop",
		"center",
		"middle",
		"absmiddle",
		"abscenter",
		"bottom"
	);

}

