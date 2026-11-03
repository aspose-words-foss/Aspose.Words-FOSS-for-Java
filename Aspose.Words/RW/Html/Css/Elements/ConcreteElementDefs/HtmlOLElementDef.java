// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/02/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


class HtmlOLElementDef extends HtmlElementDef
{
    HtmlOLElementDef(boolean applyFormattingAsMsWord)
    {
        mApplyFormattingAsMsWord = applyFormattingAsMsWord;
    }

    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("display", CssValue.Block));

        // We use MS Word-specific list indent in our user agent.
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("-aw-padding-start", new CssLengthValue(ListLevel.LEFT_INDENT, CssUnit.PT)));

        if (mApplyFormattingAsMsWord)
        {
            return;
        }

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("list-style-type", CssValue.Decimal));

        // Presentational hints.

        // "type" attribute
        switch (gStringSwitchMap.of(element.getAttributeValue("type")))
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
                // Other 'type' attribute values are ignored.
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
		"I"
	);

}

