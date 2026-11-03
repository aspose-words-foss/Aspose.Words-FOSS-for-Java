// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlBodyElementDef extends HtmlElementDef
{
    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.
        cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("display", CssValue.Block));

        // Presentational hints.

        // The "link", "alink", and "vlink" attributes are not supported.
        // WORDSNET-8615 "background" attribute is not supported in HTML5 but we support it.
        translateBackgroundAttributeToCss(element, cssDeclarations);

        //>>>>>>>> #region  Margin attributes

        CssValue topMarginValue = mapFirstMetAttributeToMarginValue(element, new String[] { "marginheight", "topmargin" });
        if (topMarginValue != null)
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-top", topMarginValue));

        CssValue rightMarginValue = mapFirstMetAttributeToMarginValue(element, new String[] { "marginwidth", "rightmargin" });
        if (rightMarginValue != null)
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-right", rightMarginValue));

        CssValue bottomMarginValue = mapFirstMetAttributeToMarginValue(element, new String[] { "marginheight", "bottommargin" });
        if (bottomMarginValue != null)
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-bottom", bottomMarginValue));

        CssValue leftMarginValue = mapFirstMetAttributeToMarginValue(element, new String[] { "marginwidth", "leftmargin" });
        if (leftMarginValue != null)
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("margin-left", leftMarginValue));

        //<<<<<<<< #endregion 

        translateBGColorAttributeToCss(element, cssDeclarations);

        // "text" attribute
        CssValue textValue = CssValue.parseLegacyColor(element.getAttributeValue("text"));
        if (textValue != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("color", textValue));
        }
    }

    private static CssValue mapFirstMetAttributeToMarginValue(IHtmlElementProvider element, String[] attributeNames)
    {
        CssValue result = null;
        for (String attributeName : attributeNames)
        {
            String attributeValue = element.getAttributeValue(attributeName);
            if (attributeValue != null)
            {
                result = CssValue.parseLegacyPixelLength(attributeValue);
                break;
            }
        }

        // The HTML 5 specification says that <body> elements should have default 8px margins.
        // We do not apply the default margins (return null), because in Word documents we have page margins.
        return result;
    }

    /// <summary>
    /// Translate 'background' attribute to corresponding CSS rule.
    /// </summary>
    private static void translateBackgroundAttributeToCss(IHtmlElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations)
    {
        // "background" attribute
        String backgroundUri = element.getAttributeValue("background");
        if (backgroundUri != null)
        {
            CssValue backgroundValue = new CssUriValue(backgroundUri);
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("background-image", backgroundValue));
        }
    }
}

