// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;

class HtmlBRElementDef extends HtmlElementDef
{
    HtmlBRElementDef(boolean applyFormattingAsMsWord)
    {
        mApplyFormattingAsMsWord = applyFormattingAsMsWord;
    }

    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // Presentational hints.

        // "clear" attribute
        // MS Word processes the "clear" attribute and the "clear" CSS property in a non-standard way. In order to mimic
        // this behavior, we process these values at a higher level.
        if (!mApplyFormattingAsMsWord)
        {
            switch (gStringSwitchMap.of(msString.toLowerInvariant(element.getAttributeValue("clear", ""))))
            {
                case /*"left"*/0:
                    cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("clear", CssValue.Left));
                    break;
                case /*"right"*/1:
                    cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("clear", CssValue.Right));
                    break;
                case /*"all"*/2:
                case /*"both"*/3:
                    cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("clear", CssValue.Both));
                    break;
                default:
                    // Other 'clear' attribute values are ignored.
                    break;
            }
        }
    }

    private /*final*/ boolean mApplyFormattingAsMsWord;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"left",
		"right",
		"all",
		"both"
	);

}

