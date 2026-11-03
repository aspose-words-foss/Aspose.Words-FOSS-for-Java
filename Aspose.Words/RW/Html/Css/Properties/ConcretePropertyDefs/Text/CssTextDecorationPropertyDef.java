// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.words.ref.RefInt;

/// <summary>
/// Implements 'text-decoration' CSS property.
/// </summary>
class CssTextDecorationPropertyDef extends CssIndividualPropertyDef
{
    CssTextDecorationPropertyDef()
    {
    	super("text-decoration", false, CssTextDecorationPropertyValue.NONE);
	
        // Empty constructor.
    }

    /*override*/ CssDeclaration createIndividualDeclaration(
        CssValueList cssValues,
        int startIndex,
        boolean important,
        boolean isInQuirksMode,
        /*out*/RefInt affectedValues)
    {
        Debug.assertTrue(false, "This property cannot be a shorthand property part.");
        affectedValues.set(0);
        return null;
    }

    protected /*override*/ CssPropertyValue createPropertyValue(
        CssValueList cssValues,
        boolean isInQuirksMode)
    {
        // text-decoration CSS property accepts the values: none | [ underline || overline || line-through || blink ] | inherit.
        CssPropertyValue propertyValue;
        if ((cssValues.getCount() == 1) && cssValues.get(0).equals(CssValue.None))
        {
            propertyValue = CssTextDecorationPropertyValue.NONE;
        }
        else if (cssValues.getCount() >= 1)
        {
            boolean allValuesAreValid = true;
            for (CssValue value : cssValues)
            {
                if (!isTextDecorationValue(value))
                {
                    allValuesAreValid = false;
                    break;
                }
            }
            propertyValue = allValuesAreValid
                ? new CssTextDecorationPropertyValue(cssValues)
                : null;
        }
        else
        {
            propertyValue = null;
        }

        return propertyValue;
    }

    private static boolean isTextDecorationValue(CssValue value)
    {
        return value.equalsAny(CssValue.Underline, CssValue.Overline, CssValue.LineThrough, CssValue.Blink);
    }
}

