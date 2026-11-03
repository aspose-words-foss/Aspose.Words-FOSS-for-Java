// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;


class HtmlCellElementDef extends HtmlElementDef
{
    HtmlCellElementDef(/*CssDocumentMode*/int documentMode)
    {
        mDocumentMode = documentMode;
    }

    protected /*override*/ boolean canBeHidden()
    {
        return false;
    }

    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.
        cssDeclarations.addOrReplace(G_DEFAULT_STYLE);

        if (element.getAttributeValue("hidden") != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("visibility", CssValue.Collapse));
        }

        if ("th".equals(element.getElementName()))
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("font-weight", CssValue.Bold));
        }

        // Presentational hints.

        // The "background" attribute is not supported.
        processParentTableAttributes(element, cssDeclarations);
        processCellAttributes(element, cssDeclarations);
    }

    private static /*final*/ CssValue G_GRAY_COLOR = CssValue.Gray;

    private static /*final*/ CssDeclarationCollection G_DEFAULT_STYLE = new CssDeclarationCollection(
        new CssSpecifiedDeclaration("display", CssValue.TableCell),

        // WORDSNET-14473 Although the HTML 5 specification requires that by default table cells (td and th) be gray 
        // (see https://www.w3.org/TR/html/rendering.html#tables), modern browsers don't apply this user agent style.
        // These styles removed from here.

        new CssSpecifiedDeclaration("padding-top", CssValue.OnePx),
        new CssSpecifiedDeclaration("padding-right", CssValue.OnePx),
        new CssSpecifiedDeclaration("padding-bottom", CssValue.OnePx),
        new CssSpecifiedDeclaration("padding-left", CssValue.OnePx),

        new CssSpecifiedDeclaration("vertical-align", CssValue.Inherit));

    /// <summary>
    /// Processes parent TABLE's attributes.
    /// </summary>
    private static void processParentTableAttributes(
        IHtmlElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations)
    {
        IElementProvider parentTable = getParentTableElement(element);
        if (parentTable == null)
            return;

        // "border" attribute
        String borderValue = parentTable.getAttributeValue("border");
        if ((borderValue != null) && (HtmlUtil.parseNonNegativeInteger(borderValue) != 0))
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-width", CssValue.OnePx));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-width", CssValue.OnePx));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-width", CssValue.OnePx));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-width", CssValue.OnePx));

            // WORDSNET-14473 Table with "border" attribute has gray border color by default.
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-color", G_GRAY_COLOR));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-color", G_GRAY_COLOR));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-color", G_GRAY_COLOR));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-color", G_GRAY_COLOR));

            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.Inset));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.Inset));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.Inset));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.Inset));
        }

        // "rules" attribute
        String rulesValue = msString.toLowerInvariant(parentTable.getAttributeValue("rules", ""));
        switch (gStringSwitchMap.of(rulesValue))
        {
            case /*"none"*/0:
            case /*"groups"*/1:
            case /*"rows"*/2:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-width", CssValue.OnePx));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-width", CssValue.OnePx));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-width", CssValue.OnePx));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-width", CssValue.OnePx));

                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.None));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.None));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.None));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.None));
                break;
            case /*"cols"*/3:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-width", CssValue.OnePx));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-width", CssValue.OnePx));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-width", CssValue.OnePx));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-width", CssValue.OnePx));

                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.None));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.Solid));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.None));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.Solid));
                break;
            case /*"all"*/4:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-width", CssValue.OnePx));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-width", CssValue.OnePx));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-width", CssValue.OnePx));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-width", CssValue.OnePx));

                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-style", CssValue.Solid));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-style", CssValue.Solid));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-style", CssValue.Solid));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-style", CssValue.Solid));
                break;
            default:
                // Other 'rules' attribute values are ignored.
                break;
        }

        // "bordercolor" attribute
        CssValue bordercolorValue = CssValue.parseLegacyColor(parentTable.getAttributeValue("bordercolor"));

        if (("none".equals(rulesValue)) ||
            ("groups".equals(rulesValue)) ||
            ("rows".equals(rulesValue)) ||
            ("cols".equals(rulesValue)) ||
            ("all".equals(rulesValue)))
        {
            if (bordercolorValue == null)
                bordercolorValue = CssValue.Black;

            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-color", bordercolorValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-color", bordercolorValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-color", bordercolorValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-color", bordercolorValue));

        }
        else if (bordercolorValue != null)
        {
            // WORDSNET-27791 Although the CSS standard doesn't state this, tests show that table cells inherit
            // 'border-xxx-color' properties from their parent elements in Google Chrome.
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-top-color", CssValue.Inherit));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-right-color", CssValue.Inherit));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-bottom-color", CssValue.Inherit));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("border-left-color", CssValue.Inherit));
        }

        // "cellpadding" attribute
        CssValue cellpaddingValue = CssValue.parseLegacyPixelLength(parentTable.getAttributeValue("cellpadding"));
        if (cellpaddingValue != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("padding-top", cellpaddingValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("padding-right", cellpaddingValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("padding-bottom", cellpaddingValue));
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("padding-left", cellpaddingValue));
        }

        // "nowrap" attribute
        if (element.getAttributeValue("nowrap") != null)
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("white-space", CssValue.Nowrap));
    }

    /// <summary>
    /// Finds the parent TABLE element.
    /// </summary>
    private static IElementProvider getParentTableElement(IElementProvider element)
    {
        IElementProvider parent = element.getParentElement();
        if ((parent != null) && ("tr".equals(parent.getElementName())))
        {
            parent = parent.getParentElement();
            if ((parent != null) && (("thead".equals(parent.getElementName())) || ("tbody".equals(parent.getElementName())) || ("tfoot".equals(parent.getElementName()))))
            {
                parent = parent.getParentElement();
            }
        }

        return ((parent != null) && ("table".equals(parent.getElementName())))
                   ? parent
                   : null;
    }

    private void processCellAttributes(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        translateVAlignAttributeToCss(element, cssDeclarations);
        translateBGColorAttributeToCss(element, cssDeclarations);
        translateAlignAttributeToCss(element, cssDeclarations, true);
        translateHeightAttributeToCss(element, cssDeclarations);

        // "width" attribute
        CssValue widthValue = CssValue.parseLegacyDimension(element.getAttributeValue("width"));
        if (widthValue != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("width", widthValue));
        }

        // "nowrap" attribute
        if (element.getAttributeValue("nowrap") != null)
        {
            if ((mDocumentMode == CssDocumentMode.QUIRKS) && (widthValue != null) &&
                (widthValue.getValueType() == CssValueType.LENGTH))
            {
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("white-space", CssValue.Normal));
            }
            else
            {
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("white-space", CssValue.Nowrap));
            }
        }
    }

    private /*final*/ /*CssDocumentMode*/int mDocumentMode;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"none",
		"groups",
		"rows",
		"cols",
		"all"
	);

}

