// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/02/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;

class HtmlLIElementDef extends HtmlElementDef
{
    HtmlLIElementDef(boolean applyFormattingAsMsWord)
    {
        mApplyFormattingAsMsWord = applyFormattingAsMsWord;
    }

    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("display", CssValue.ListItem));
        // This style is applied only by Chrome. Firefox and Internet Explorer do not specify any special text alignment
        // on <li> elements. Although this style is uncommon, we still apply it for the following reasons:
        //  - during export this style helps to style <li> elements so that they look identical in all browsers;
        //  - Chrome is the most popular browser, so it is reasonable to use this browser's styles as a reference
        //    during import; moreover, Chrome formatting looks more consistent when a list contains mixed LTR and RTL items.
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("text-align", CssValue.AwMatchParent));

        if (mApplyFormattingAsMsWord)
        {
            return;
        }

        // Presentational hints.

        // "type" attribute
        String type = element.getAttributeValue("type", "");

        switch (gStringSwitchMap.of(type))
        {
            case /*"1"*/0:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("list-style-type", CssValue.Decimal));
                break;
            case /*"a"*/1:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("list-style-type", CssValue.LowerAlpha));
                break;
            case /*"A"*/2:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("list-style-type", CssValue.UpperAlpha));
                break;
            case /*"i"*/3:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("list-style-type", CssValue.LowerRoman));
                break;
            case /*"I"*/4:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("list-style-type", CssValue.UpperRoman));
                break;
            default:
                // SQ fix: nothing to do.
                break;
        }

        switch (gStringSwitchMap.of(msString.toLowerInvariant(type)))
        {
            case /*"disc"*/5:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("list-style-type", CssValue.Disc));
                break;
            case /*"circle"*/6:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("list-style-type", CssValue.Circle));
                break;
            case /*"square"*/7:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("list-style-type", CssValue.Square));
                break;
            default:
                // SQ fix: nothing to do.
                break;
        }
    }

    private /*final*/ boolean mApplyFormattingAsMsWord;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"1",
		"a",
		"A",
		"i",
		"I",
		"disc",
		"circle",
		"square"
	);

}

