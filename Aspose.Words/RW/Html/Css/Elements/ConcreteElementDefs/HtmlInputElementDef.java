// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


class HtmlInputElementDef extends HtmlElementDef
{
    HtmlInputElementDef(/*CssDocumentMode*/int documentMode)
    {
        mDocumentMode = documentMode;
    }

    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.

        if (msString.equals(element.getAttributeValue("type", ""), "hidden", StringComparison.ORDINAL_IGNORE_CASE))
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("display", CssValue.None));
        }


        // Presentational hints.


        // The "size" attribute is not supported at the moment, as its processing requires information
        // about element's font.

        if (msString.equals(element.getAttributeValue("type", ""), "image",
            StringComparison.ORDINAL_IGNORE_CASE))
        {
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
                // that align this element's vertical middle with the parent element's baseline.
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
                    // Other 'align' attribute values are ignored.
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

            // "border" attribute
            // As stated by the HTML 5 specification, the following rules only apply when an element is contained
            // within a hyperlink. I hope, checking that parent element is an anchor is enough.
            IElementProvider parent = element.getParentElement();
            if ((parent != null) && ("a".equals(parent.getElementName())))
            {
                int borderValue = HtmlUtil.parseNonNegativeInteger(element.getAttributeValue("border"));
                if (borderValue > 0)
                {
                    CssValue borderWidth = new CssLengthValue(borderValue, CssUnit.PX);
                    cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-width", borderWidth));
                    cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-width", borderWidth));
                    cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-width", borderWidth));
                    cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-width", borderWidth));

                    cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.Solid));
                    cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.Solid));
                    cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.Solid));
                    cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.Solid));
                }
            }
        }

        if (mDocumentMode == CssDocumentMode.QUIRKS)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("box-sizing", CssValue.BorderBox));
        }
    }

    protected /*override*/ void applyOverridableFontStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {

        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("font-family",
            new CssFontFamilyPropertyValue(new CssIdentifierValue("sans-serif"))));
    }

    private /*final*/ /*CssDocumentMode*/int mDocumentMode;

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

