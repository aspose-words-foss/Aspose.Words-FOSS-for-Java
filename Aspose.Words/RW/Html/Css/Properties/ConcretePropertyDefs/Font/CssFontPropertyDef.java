// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.words.ref.RefInt;


/// <summary>
/// Implements 'font' CSS shorthand property. 
/// </summary>
/// <remarks>
/// The font property is a shorthand property for setting the individual font properties (i.e., 'font-style', 'font-variant', 
/// 'font-weight', 'font-size', 'line-height' and 'font-family') at the same place in the style sheet.
/// The properties that can be set, are (in order): "font-style font-variant font-weight font-size/line-height font-family"
/// The font-size and font-family values are required. If one of the other values are missing, the default values will be inserted, if any.
/// </remarks>
class CssFontPropertyDef extends CssShorthandPropertyDef
{
    CssFontPropertyDef()
    {
    	super("font", true);
	
        // Empty constructor.
    }

    protected /*override*/ CssPropertyValue createShorthandValue(CssDeclarationCollection individualDeclarations)
    {
        CssDeclaration fontStyleDeclaration = individualDeclarations.get("font-style");
        CssDeclaration fontSizeDeclaration = individualDeclarations.get("font-size");
        CssDeclaration fontFamilyDeclaration = individualDeclarations.get("font-family");
        CssDeclaration fontVariantDeclaration = individualDeclarations.get("font-variant");
        CssDeclaration fontWeightDeclaration = individualDeclarations.get("font-weight");
        CssDeclaration lineHeightDeclaration = individualDeclarations.get("line-height");

        return new CssFontPropertyValue(
            (fontStyleDeclaration != null) ? fontStyleDeclaration.getValue().getFirstValue() : null,
            (fontVariantDeclaration != null) ? fontVariantDeclaration.getValue().getFirstValue() : null,
            (fontWeightDeclaration != null) ? fontWeightDeclaration.getValue().getFirstValue() : null,
            fontSizeDeclaration.getValue().getFirstValue(),
            (lineHeightDeclaration != null) ? lineHeightDeclaration.getValue().getFirstValue() : null,
            fontFamilyDeclaration.getValue().getFirstValue());
    }

    protected /*override*/ ShorthandPropertyPart[] getIndividualProperties()
    {
        // The properties that can be set, are (in order): "font-style font-variant font-weight font-size/line-height font-family"
        if (mIndividualProperties != null)
            return mIndividualProperties;

        mIndividualProperties = new ShorthandPropertyPart[]
        {
            new ShorthandPropertyPart("font-style", false),
            new ShorthandPropertyPart("font-variant", false),
            new ShorthandPropertyPart("font-weight", false),
            new ShorthandPropertyPart("font-size", true),
            new ShorthandPropertyPart("line-height", false),
            new ShorthandPropertyPart("font-family", true)
        };
        return mIndividualProperties;
    }

    protected /*override*/ CssDeclarationCollection createIndividualDeclarations(
        CssValueList cssValues,
        boolean important,
        boolean isInQuirksMode)
    {
        // [ [ <'font-style'> || <'font-variant'> || <'font-weight'> ]? <'font-size'> [ / <'line-height'> ]? <'font-family'> ] | 
        // caption | icon | menu | message-box | small-caption | status-bar | inherit
        if (cssValues.getCount() == 0)
            return null;

        // Try to find caption | icon | menu | message-box | small-caption | status-bar | inherit
        if (cssValues.getCount() == 1)
        {
            CssDeclarationCollection systemFontDeclarations = tryCreateFromSystemFont(cssValues.get(0));
            if (systemFontDeclarations != null)
                return systemFontDeclarations;
        }

        // The properties that can be set, are (in order):
        //   font-style font-variant font-weight font-size/line-height font-family
        // The font-size and font-family values are required. If one of the other values are missing, the default values
        // will be inserted, if any.
        // The order of the values is not completely free: font-style, font-variant and font-weight must be defined,
        // if any, before the font-size value.
        // The line-height value must be defined immediately after the font-size, preceded by a mandatory /. Finally,
        // the font-family is mandatory and must be the last value defined (inherit value does not work).

        CssValueList cssValuesCopy = new CssValueList(cssValues);
        CssDeclarationCollectionBuilder result = new CssDeclarationCollectionBuilder();

        int affectedValues = 0;
        CssDeclaration sizeDeclaration = null;
        int fontSizeValueIndex = -1;
        for (int index = cssValuesCopy.getCount() - 1; index >= 0; index--)
        {
            if ((index > 0) && cssValuesCopy.get(index - 1).equals(CssValue.Solidus))
                continue;

            // Font-size value cannot be number in standards and quirks modes.
            if (cssValuesCopy.get(index).getValueType() == CssValueType.NUMBER)
                continue;

            CssIndividualPropertyDef fontSizePropertyDef = CssPropertyDefFactory.getIndividualPropertyDef("font-size");
            RefInt referenceToAffectedValues = new RefInt(affectedValues);
            sizeDeclaration = fontSizePropertyDef.createIndividualDeclaration(
                cssValuesCopy,
                index,
                important,
                isInQuirksMode,
                /*out*/ referenceToAffectedValues);
            affectedValues = referenceToAffectedValues.get();
            if (sizeDeclaration != null)
            {
                result.add(sizeDeclaration);
                fontSizeValueIndex = index;
                for (int i = 0; i < affectedValues; i++)
                    cssValuesCopy.removeAt(fontSizeValueIndex);
                break;
            }
        }

        // font-size part is mandatory.
        if (sizeDeclaration == null)
            return null;
        // font-family is mandatory and must be the last value defined (must be after font-size).
        if (fontSizeValueIndex > cssValuesCopy.getCount() - 1)
            return null;

        CssIndividualPropertyDef lineHeigthPropertyDef = CssPropertyDefFactory.getIndividualPropertyDef("line-height");
        if (cssValuesCopy.get(fontSizeValueIndex).equals(CssValue.Solidus))
        {
            cssValuesCopy.removeAt(fontSizeValueIndex);
            if (fontSizeValueIndex > cssValuesCopy.getCount() - 1)
                return null;

            RefInt referenceToAffectedValues = new RefInt(affectedValues);
            CssDeclaration lineHeightDeclaration = lineHeigthPropertyDef.createIndividualDeclaration(
                cssValuesCopy,
                fontSizeValueIndex,
                important,
                isInQuirksMode,
                /*out*/ referenceToAffectedValues);
            affectedValues = referenceToAffectedValues.get();
            // Line height part is mandatory when solidus exists.
            if (lineHeightDeclaration == null)
                return null;

            result.add(lineHeightDeclaration);
            for (int i = 0; i < affectedValues; i++)
                cssValuesCopy.removeAt(fontSizeValueIndex);
        }
        else
        {
            result.addOrReplace(lineHeigthPropertyDef.createInitialDeclaration(important));
        }

        // font-family is mandatory and must be the last value defined (must be after font-size).
        if (fontSizeValueIndex > cssValuesCopy.getCount() - 1)
            return null;

        CssIndividualPropertyDef fontFamilyPropertyDef = CssPropertyDefFactory.getIndividualPropertyDef("font-family");
        RefInt referenceToAffectedValues = new RefInt(affectedValues);
        CssDeclaration fontFamilyDeclaration = fontFamilyPropertyDef.createIndividualDeclaration(
            cssValuesCopy,
            fontSizeValueIndex,
            important,
            isInQuirksMode,
            /*out*/ referenceToAffectedValues);
        affectedValues = referenceToAffectedValues.get();
        if (fontFamilyDeclaration == null)
            return null;

        result.add(fontFamilyDeclaration);
        for (int i = 0; i < affectedValues; i++)
            cssValuesCopy.removeAt(fontSizeValueIndex);

        ArrayList<CssIndividualPropertyDef> additionalProperties = new ArrayList<CssIndividualPropertyDef>();
        msArrayList.add(additionalProperties, CssPropertyDefFactory.getIndividualPropertyDef("font-style"));
        msArrayList.add(additionalProperties, CssPropertyDefFactory.getIndividualPropertyDef("font-variant"));
        msArrayList.add(additionalProperties, CssPropertyDefFactory.getIndividualPropertyDef("font-weight"));

        int propertyIndex = 0;
        while ((propertyIndex < additionalProperties.size()) && (cssValuesCopy.getCount() != 0))
        {
            CssIndividualPropertyDef property = additionalProperties.get(propertyIndex);
            referenceToAffectedValues.set(affectedValues);
            CssDeclaration propertyDeclaration = property.createIndividualDeclaration(
                cssValuesCopy,
                0,
                important,
                isInQuirksMode,
                /*out*/ referenceToAffectedValues);
            affectedValues = referenceToAffectedValues.get();
            if (propertyDeclaration != null)
            {
                result.addOrReplace(propertyDeclaration);
                additionalProperties.remove(propertyIndex);
                for (int i = 0; i < affectedValues; i++)
                    cssValuesCopy.removeAt(0);
                propertyIndex = 0;
            }
            else
            {
                propertyIndex++;
            }
        }

        if (cssValuesCopy.getCount() != 0)
            return null;

        for (CssIndividualPropertyDef property : additionalProperties)
            result.addOrReplace(((CssIndividualSimplePropertyDef)property).createInitialDeclaration(important));

        return result.getDeclarations();
    }

    /// <summary>
    /// Processes system fonts (caption | icon | menu | message-box | small-caption | status-bar).
    /// </summary>
    private static CssDeclarationCollection tryCreateFromSystemFont(CssValue cssValue)
    {
        // At the moment we just recognize these values, but don't do anything with the font.
        if (cssValue.equals(CssValue.Caption) || cssValue.equals(CssValue.Icon) ||
            cssValue.equals(CssValue.Menu) || cssValue.equals(CssValue.MessageBox) ||
            cssValue.equals(CssValue.SmallCaption) || cssValue.equals(CssValue.StatusBar))
        {
            return CssDeclarationCollection.EMPTY;
        }

        return null;
    }

    private ShorthandPropertyPart[] mIndividualProperties;
}

