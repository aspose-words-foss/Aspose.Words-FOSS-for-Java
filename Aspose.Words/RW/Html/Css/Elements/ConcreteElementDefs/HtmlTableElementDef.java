// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;


class HtmlTableElementDef extends HtmlElementDef
{
    HtmlTableElementDef(/*CssDocumentMode*/int documentMode)
    {
        mDocumentMode = documentMode;
    }

    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.
        cssDeclarations.addOrReplace(G_DEFAULT_STYLE);

        // Presentational hints.

        // The "background" attribute is not supported.

        // "align" attribute
        switch (gStringSwitchMap.of(msString.toLowerInvariant(element.getAttributeValue("align", ""))))
        {
            case /*"left"*/0:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("float", CssValue.Left));
                break;
            case /*"right"*/1:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("float", CssValue.Right));
                break;
            case /*"center"*/2:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-left", CssValue.Auto));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-right", CssValue.Auto));
                break;
            default:
                // Other 'align' attribute values are ignored.
                break;
        }

        // "rules" attribute
        switch (gStringSwitchMap.of(msString.toLowerInvariant(element.getAttributeValue("rules", ""))))
        {
            case /*"none"*/3:
            case /*"groups"*/4:
            case /*"rows"*/5:
            case /*"cols"*/6:
            case /*"all"*/7:
            {
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-collapse", CssValue.Collapse));

                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-color", CssValue.Black));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-color", CssValue.Black));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-color", CssValue.Black));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-color", CssValue.Black));
                break;
            }
            default:
                // Other 'rules' attribute values are ignored.
                break;
        }

        // "border" attribute
        String borderValue = element.getAttributeValue("border");
        if ((borderValue != null) && (HtmlUtil.parseNonNegativeInteger(borderValue) != 0))
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.Outset));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.Outset));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.Outset));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.Outset));
        }
        if (borderValue != null)
        {
            CssValue borderWidthValue = CssValue.parseLegacyPixelLength(borderValue);
            if (borderWidthValue == null)
                borderWidthValue = CssValue.OnePx;

            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-width", borderWidthValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-width", borderWidthValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-width", borderWidthValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-width", borderWidthValue));
        }

        // "frame" attribute
        switch (gStringSwitchMap.of(msString.toLowerInvariant(element.getAttributeValue("frame", ""))))
        {
            case /*"void"*/8:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.Hidden));
                break;
            case /*"above"*/9:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.Outset));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.Hidden));
                break;
            case /*"below"*/10:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.Outset));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.Hidden));
                break;
            case /*"hsides"*/11:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.Outset));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.Outset));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.Hidden));
                break;
            case /*"lhs"*/12:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.Outset));
                break;
            case /*"rhs"*/13:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.Outset));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.Hidden));
                break;
            case /*"vsides"*/14:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.Outset));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.Hidden));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.Outset));
                break;
            case /*"box"*/15:
            case /*"border"*/16:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.Outset));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.Outset));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.Outset));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.Outset));
                break;
            default:
                // Other 'frame' attribute values are ignored.
                break;
        }
        switch (gStringSwitchMap.of(msString.toLowerInvariant(element.getAttributeValue("frame", ""))))
        {
            case /*"void"*/8:
            case /*"above"*/9:
            case /*"below"*/10:
            case /*"hsides"*/11:
            case /*"lhs"*/12:
            case /*"rhs"*/13:
            case /*"vsides"*/14:
            case /*"box"*/15:
            case /*"border"*/16:
            {
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-color", CssValue.Black));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-color", CssValue.Black));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-color", CssValue.Black));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-color", CssValue.Black));
                break;
            }
            default:
                // Other 'frame' attribute values are ignored.
                break;
        }

        // "cellpadding" attribute
        CssValue cellpaddingValue = CssValue.parseLegacyPixelLength(element.getAttributeValue("cellpadding"));
        if (cellpaddingValue != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("padding-top", cellpaddingValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("padding-right", cellpaddingValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("padding-bottom", cellpaddingValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("padding-left", cellpaddingValue));
        }

        // "cellspacing" attribute
        CssValue cellspacingValue = CssValue.parseLegacyPixelLength(element.getAttributeValue("cellspacing"));
        if (cellspacingValue != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-spacing", cellspacingValue));
        }

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

        translateHeightAttributeToCss(element, cssDeclarations);
        translateWidthAttributeToCss(element, cssDeclarations);
        translateBGColorAttributeToCss(element, cssDeclarations);

        // "bordercolor" attribute
        CssValue bordercolorValue = CssValue.parseLegacyColor(element.getAttributeValue("bordercolor"));
        if (bordercolorValue != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-color", bordercolorValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-color", bordercolorValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-color", bordercolorValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-color", bordercolorValue));
        }

        if (mDocumentMode == CssDocumentMode.QUIRKS)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("line-height", CssValue.Initial));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("white-space", CssValue.Initial));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("text-align", CssValue.Initial));
        }
    }

    protected /*override*/ void applyOverridableFontStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        if (mDocumentMode == CssDocumentMode.QUIRKS)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("font-weight", CssValue.Initial));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("font-style", CssValue.Initial));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("font-variant", CssValue.Initial));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("font-size", CssValue.Initial));
        }
    }

    private /*final*/ /*CssDocumentMode*/int mDocumentMode;


    private static /*final*/ CssDeclarationCollection G_DEFAULT_STYLE = new CssDeclarationCollection(
        new CssSpecifiedDeclaration("padding-top", CssValue.OnePx),
        new CssSpecifiedDeclaration("padding-right", CssValue.OnePx),
        new CssSpecifiedDeclaration("padding-bottom", CssValue.OnePx),
        new CssSpecifiedDeclaration("padding-left", CssValue.OnePx),

        new CssSpecifiedDeclaration("display", CssValue.Table),
        new CssSpecifiedDeclaration("border-spacing", new CssLengthValue(2.0, CssUnit.PX)),
        new CssSpecifiedDeclaration("border-collapse", CssValue.Separate),

        new CssSpecifiedDeclaration("border-top-color", CssValue.Gray),
        new CssSpecifiedDeclaration("border-right-color", CssValue.Gray),
        new CssSpecifiedDeclaration("border-bottom-color", CssValue.Gray),
        new CssSpecifiedDeclaration("border-left-color", CssValue.Gray));

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"left",
		"right",
		"center",
		"none",
		"groups",
		"rows",
		"cols",
		"all",
		"void",
		"above",
		"below",
		"hsides",
		"lhs",
		"rhs",
		"vsides",
		"box",
		"border"
	);

}

