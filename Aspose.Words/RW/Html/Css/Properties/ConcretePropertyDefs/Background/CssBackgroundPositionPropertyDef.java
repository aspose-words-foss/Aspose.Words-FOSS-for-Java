// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.words.ref.RefInt;

/// <summary>
/// Implements 'background-position' CSS property.
/// </summary>
class CssBackgroundPositionPropertyDef extends CssIndividualPropertyDef
{
    CssBackgroundPositionPropertyDef()
    {
    	super("background-position", false, new CssBackgroundPositionPropertyValue(CssValue.ZeroPercentage, CssValue.ZeroPercentage));
	
    }

    /*override*/ CssDeclaration createIndividualDeclaration(
        CssValueList cssValues,
        int startIndex,
        boolean important,
        boolean isInQuirksMode,
        /*out*/RefInt affectedValues)
    {
        affectedValues.set(0);
        if (startIndex >= cssValues.getCount())
            return null;

        CssDeclaration declaration = null;
        if (isSupportedXValue(cssValues.get(startIndex)) && (startIndex + 1 < cssValues.getCount()) &&
            isSupportedYValue(cssValues.get(startIndex + 1)))
        {
            declaration = createDeclaration(
                new CssValueList(cssValues.get(startIndex), cssValues.get(startIndex + 1)),
                important,
                isInQuirksMode);
            if (declaration != null)
                affectedValues.set(2);
        }

        if ((declaration == null) && isSupportedXValue(cssValues.get(startIndex)))
        {
            declaration = createDeclaration(
                new CssValueList(cssValues.get(startIndex)),
                important,
                isInQuirksMode);
            if (declaration != null)
                affectedValues.set(1);
        }

        return declaration;
    }

    /*override*/ CssDeclaration createInheritDeclaration(boolean important)
    {
        return new CssSpecifiedDeclaration(getName(),
            new CssBackgroundPositionPropertyValue(CssValue.Inherit, CssValue.Inherit),
            important);
    }

    protected /*override*/ CssPropertyValue createPropertyValue(
        CssValueList cssValues,
        boolean isInQuirksMode)
    {
        // [ [ <percentage> | <length> | left | center | right ] [ <percentage> | <length> | top | center | bottom ]? ] | [ [ left | center | right ] || [ top | center | bottom ] ] | inherit
        CssPropertyValue propertyValue = null;
        switch (cssValues.getCount())
        {
            case 1:
            {
                // If only one value is specified, the second value is assumed to be 'center'.
                if (isSupportedXValue(cssValues.get(0)))
                    propertyValue = new CssBackgroundPositionPropertyValue(cssValues.get(0), CssValue.Center);
                break;
            }
            case 2:
            {
                if (isSupportedXValue(cssValues.get(0)) && isSupportedYValue(cssValues.get(1)))
                    propertyValue = new CssBackgroundPositionPropertyValue(cssValues.get(0), cssValues.get(1));
                break;
            }
            default:
                // SQ fix: nothing to do.
                break;
        }

        return propertyValue;
    }

    private static boolean isSupportedXValue(CssValue cssValue)
    {
        return (cssValue.getValueType() == CssValueType.LENGTH) ||
               (cssValue.getValueType() == CssValueType.PERCENTAGE) ||
               cssValue.equals(CssValue.Left) ||
               cssValue.equals(CssValue.Center) ||
               cssValue.equals(CssValue.Right) ||
               // WORDSNET-10223 We must read 'x' position in number values
               // which is applied by some browsers in quirks mode.
               (cssValue.getValueType() == CssValueType.NUMBER);
    }

    private static boolean isSupportedYValue(CssValue cssValue)
    {
        return (cssValue.getValueType() == CssValueType.LENGTH) ||
               (cssValue.getValueType() == CssValueType.PERCENTAGE) ||
               cssValue.equals(CssValue.Top) ||
               cssValue.equals(CssValue.Center) ||
               cssValue.equals(CssValue.Bottom) ||
               // WORDSNET-10223 We must read 'y' position in number values
               // which is applied by some browsers in quirks mode.
               (cssValue.getValueType() == CssValueType.NUMBER);
    }
}

