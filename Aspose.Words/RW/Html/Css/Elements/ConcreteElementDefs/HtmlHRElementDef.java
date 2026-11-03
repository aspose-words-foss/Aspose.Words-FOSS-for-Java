// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;


class HtmlHRElementDef extends HtmlElementDef
{
    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("display", CssValue.Block));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("color", CssValue.Gray));

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-width", CssValue.OnePx));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-width", CssValue.OnePx));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-width", CssValue.OnePx));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-width", CssValue.OnePx));

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.Inset));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.Inset));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.Inset));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.Inset));

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-right", CssValue.Auto));
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-left", CssValue.Auto));

        // Presentational hints.

        // "align" attribute
        switch (gStringSwitchMap.of(msString.toLowerInvariant(element.getAttributeValue("align", ""))))
        {
            case /*"center"*/0:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-left", CssValue.Auto));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-right", CssValue.Auto));
                break;
            case /*"left"*/1:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-left", CssValue.Zero));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-right", CssValue.Auto));
                break;
            case /*"right"*/2:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-left", CssValue.Auto));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-right", CssValue.Zero));
                break;
            default:
                // Other 'align' attribute values are ignored.
                break;
        }

        // Border style if both "color" and "noshade" attribute is not present.
        if ((element.getAttributeValue("color") != null) || (element.getAttributeValue("noshade") != null))
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.Solid));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.Solid));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.Solid));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.Solid));

            // "color" attribute
            // The following code violates the HTML 5 specification, but browsers do the same.
            CssValue colorValue = CssValue.parseLegacyColor(element.getAttributeValue("color"));
            if (colorValue != null)
            {
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-color", colorValue));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-color", colorValue));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-color", colorValue));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-color", colorValue));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("background-color", colorValue));
            }
            else if (element.getAttributeValue("noshade") != null)
            {
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-color", CssValue.Gray));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-color", CssValue.Gray));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-color", CssValue.Gray));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-color", CssValue.Gray));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("background-color", CssValue.Gray));
            }
        }

        // "size" attribute
        // The algrorithm of parsing a "size" attribute value provided in HTML 5 is too complicated.
        // It states that "size" must affect "border-size" properties, but it is not the case in modern browsers.
        // The following code mimics Google Chrome behavior instead.
        // This code assumes that a UA style sheet sets HR's "border-width" properties to 1px.
        String sizeValueStr = element.getAttributeValue("size");
        if (sizeValueStr == null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("height", CssValue.Zero));
        }
        else
        {
            int sizeValue = HtmlUtil.parseNonNegativeInteger(sizeValueStr);
            if (sizeValue <= 1)
            {
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-width", CssValue.Zero));
            }
            CssValue heightValue = (sizeValue <= 2)
                ? (CssValue)CssValue.Zero
                : new CssLengthValue(sizeValue - 2, CssUnit.PX);
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("height", heightValue));
        }

        translateWidthAttributeToCss(element, cssDeclarations);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"center",
		"left",
		"right"
	);

}

