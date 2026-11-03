// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/02/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;


class HtmlULElementDef extends HtmlElementDef
{
    HtmlULElementDef(boolean applyFormattingAsMsWord)
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

        int nestingLevel = 0;
        final int MAX_NESTING_LEVEL = 2;
        IElementProvider parent = element.getParentElement();
        while ((parent != null) && (nestingLevel < MAX_NESTING_LEVEL))
        {
            if (("ol".equals(parent.getElementName())) ||
                ("ul".equals(parent.getElementName())) ||
                ("dir".equals(parent.getElementName())) ||
                ("menu".equals(parent.getElementName())))
            {
                ++nestingLevel;
            }
            parent = parent.getParentElement();
        }

        CssValue listStyleTypeValue;
        switch (nestingLevel)
        {
            case 0:
                listStyleTypeValue = CssValue.Disc;
                break;
            case 1:
                listStyleTypeValue = CssValue.Circle;
                break;
            default:
                listStyleTypeValue = CssValue.Square;
                break;
        }
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("list-style-type", listStyleTypeValue));

        // Presentational hints.

        // "type" attribute
        switch (gStringSwitchMap.of(msString.toLowerInvariant(element.getAttributeValue("type", ""))))
        {
            case /*"disc"*/0:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("list-style-type", CssValue.Disc));
                break;
            case /*"circle"*/1:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("list-style-type", CssValue.Circle));
                break;
            case /*"square"*/2:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("list-style-type", CssValue.Square));
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
		"disc",
		"circle",
		"square"
	);

}

