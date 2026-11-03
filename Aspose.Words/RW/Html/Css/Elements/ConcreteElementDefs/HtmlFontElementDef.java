// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;

class HtmlFontElementDef extends HtmlElementDef
{
    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.
        // "color" attribute
        CssValue colorValue = CssValue.parseLegacyColor(element.getAttributeValue("color"));
        if (colorValue != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("color", colorValue));
        }

        // "face" attribute
        String faceValue = element.getAttributeValue("face");
        if ((faceValue != null) && (!msString.equals(msString.trim(faceValue), "")))
        {
            // WORDSNET-4667 Multiple font families specified in HTML are not imported as expected. 
            // Attribute "face" may have list of fonts separated by comma.
            String[] fontValues = msString.split(faceValue, ',');

            CssValueList cssValueList = new CssValueList();
            for (String value : fontValues)
            {
                String fontValue = msString.trim(value);
                if (!msString.isNullOrEmpty(fontValue))
                {
                    cssValueList.add(CssValue.createFontFamilyValue(fontValue));
                }
            }

            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration(
                "font-family",
                new CssFontFamilyPropertyValue(cssValueList)));
        }

        // "size" attribute
        CssValue sizeValue = CssValue.parseLegacyFontSize(element.getAttributeValue("size"));
        if (sizeValue != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("font-size", sizeValue));
        }
    }
}

