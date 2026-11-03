// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/09/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.W3CNamespaces;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Represents a record for a SVG element in the user agent style sheet.
/// Derived classes created for concrete SVG elements
/// should be registered in <see cref="DefaultElementStyleResolver" />.
/// </summary>
class SvgElementDef extends ElementDef
{
    protected /*override*/ void applyStyles(
        IElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations)
    {
        Debug.assertTrue(W3CNamespaces.SVG.equals(element.getElementNamespace()));

        handleFontFamilyProperty(element, cssDeclarations);
        handleTextDecorationProperty(element, cssDeclarations);
        handleStrokeDasharrayProperty(element, cssDeclarations);
        handleSimpleValueAttributes(element, cssDeclarations);
    }

    private static void handleStrokeDasharrayProperty(
        IElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations)
    {
        String value = element.getAttributeValue("stroke-dasharray");
        if ((value == null) || !StringUtil.hasChars(msString.trim(value)))
        {
            return;
        }

        CssValueList cssValueCollection = new CssValueList();
        for (String dashLengthString : getNonWhitespaceSubstrings(value, ',', ' '))
        {
            double dashLength = FormatterPal.parseDouble(dashLengthString);
            cssValueCollection.add(new CssNumberValue(dashLength));
        }

        CssPropertyDef propertyDef = CssPropertyDefFactory.getIndividualPropertyDef("stroke-dasharray");
        CssDeclarationCollection declarations = propertyDef.createDeclarations(
            cssValueCollection,
            false,
            false);

        if (declarations == null)
            return;

        cssDeclarations.addOrReplace(declarations);
    }

    private static void handleTextDecorationProperty(
        IElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations)
    {
        String value = element.getAttributeValue("text-decoration");
        if ((value == null) || !StringUtil.hasChars(msString.trim(value)))
        {
            return;
        }

        CssValueList cssValueCollection = new CssValueList();
        for (String decorationValue : getNonWhitespaceSubstrings(value, ' '))
        {
            cssValueCollection.add(new CssIdentifierValue(decorationValue));
        }

        CssIndividualPropertyDef propertyDef = CssPropertyDefFactory.getIndividualPropertyDef("text-decoration");
        CssDeclarationCollection declarations = propertyDef.createDeclarations(
            cssValueCollection,
            false,
            false);

        if (declarations == null)
            return;

        cssDeclarations.addOrReplace(declarations);
    }

    private static void handleFontFamilyProperty(
        IElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations)
    {
        String value = element.getAttributeValue("font-family");
        if ((value == null) || !StringUtil.hasChars(msString.trim(value)))
        {
            return;
        }

        CssValueList cssValueCollection = new CssValueList();
        for (String fontName : getNonWhitespaceSubstrings(value, ','))
        {
            // WORDSNET-17895 Replace first pair of inner quotes to support for quoted family names
            // if the passed string complies with requirements.
            // The requirements are different from one browser to another. Here is the rules of IE.

            Regex quotedFontNameRegEx = new Regex("^([\"\'])([^\"\'\\s])+?(.)*$");
            String unquotedFontName = quotedFontNameRegEx.isMatch(fontName)
                ? Regex.replace(fontName, "^[\"\']|[\"\']$", "")
                : fontName;

            if (cssValueCollection.getCount() > 0)
            {
                cssValueCollection.add(CssValue.Comma);
            }
            cssValueCollection.add(CssValue.createFontFamilyValue(unquotedFontName));
        }

        CssIndividualPropertyDef propertyDef = CssPropertyDefFactory.getIndividualPropertyDef("font-family");
        CssDeclarationCollection declarations = propertyDef.createDeclarations(
            cssValueCollection,
            false,
            false);

        if (declarations == null)
            return;

        cssDeclarations.addOrReplace(declarations);
    }

    private static void handleSimpleValueAttributes(
        IElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations)
    {
        for (String attributeName : G_STYLING_ATTRIBUTE_NAMES)
        {
            String value = element.getAttributeValue(attributeName);
            if ((value == null) || !StringUtil.hasChars(msString.trim(value)))
            {
                continue;
            }

            CssValue cssValue = CssParser.parseValue(value);
            if (cssValue == null)
            {
                continue;
            }

            if ((cssValue.getValueType() == CssValueType.NUMBER) &&
                (("width".equals(attributeName)) ||
                 ("height".equals(attributeName)) ||
                 ("font-size".equals(attributeName)) ||
                 ("letter-spacing".equals(attributeName))))
            {
                cssValue = new CssLengthValue(cssValue.getDoubleValue(), CssUnit.PX);
            }

            CssPropertyDef propertyDef = CssPropertyDefFactory.getPropertyDef(attributeName);
            CssDeclarationCollection declarations = propertyDef.createDeclarations(cssValue, false, false);
            if (declarations != null)
            {
                cssDeclarations.addOrReplace(declarations);
            }
        }
    }

    /// <summary>
    /// Splits a string into substrings by the specified separators. Returns only non-whitespace substrings.
    /// </summary>
    private static ArrayList<String> getNonWhitespaceSubstrings(String value, /*params*/ char... separators)
    {
        Debug.assertTrue(value != null);

        ArrayList<String> subValueList = new ArrayList<String>();

        String[] subValues = msString.split(value, separators);
        for (String subValue : subValues)
        {
            String trimmedSubValue = msString.trim(subValue);
            if (!msString.isNullOrEmpty(trimmedSubValue))
            {
                msArrayList.add(subValueList, trimmedSubValue);
            }
        }

        return subValueList;
    }

    /// <summary>
    /// Names of attributes whose values are translated to CSS properties.
    /// </summary>
    /// <remarks>
    /// Note that the following attributes are not in this list, because they have complex values that are processed
    /// separately: 'font-family', 'text-decoration', and 'stroke-dasharray'.
    /// </remarks>
    private static /*final*/ String[] G_STYLING_ATTRIBUTE_NAMES = new String[]
    {
        "height",
        "width",
        //Font properties:
        "font",
        "font-size",
        "font-size-adjust",
        "font-stretch",
        "font-style",
        "font-variant",
        "font-weight",
        //Text properties:
        "direction",
        "letter-spacing",
        "unicode-bidi",
        "word-spacing",
        //Other properties for visual media:
        "clip",
        "color",
        "cursor",
        "display",
        "overflow",
        "visibility",
        //The following SVG properties are not defined in CSS2.
        //Clipping, Masking and Compositing properties:
        "clip-path",
        "clip-rule",
        "mask",
        "opacity",
        //Filter Effects properties:
        "enable-background",
        "filter",
        "flood-color",
        "flood-opacity",
        "lighting-color",
        //Gradient properties:
        "stop-color",
        "stop-opacity",
        //Interactivity properties:
        "pointer-events",
        //Color and Painting properties:
        "color-interpolation",
        "color-interpolation-filters",
        "color-profile",
        "color-rendering",
        "fill",
        "fill-opacity",
        "fill-rule",
        "image-rendering",
        "marker",
        "marker-end",
        "marker-mid",
        "marker-start",
        "shape-rendering",
        "stroke",
        "stroke-dashoffset",
        "stroke-linecap",
        "stroke-linejoin",
        "stroke-miterlimit",
        "stroke-opacity",
        "stroke-width",
        "text-rendering",
        //Text properties:
        "alignment-baseline",
        "baseline-shift",
        "dominant-baseline",
        "glyph-orientation-horizontal",
        "glyph-orientation-vertical",
        "kerning",
        "text-anchor",
        "writing-mode",
    };
}

