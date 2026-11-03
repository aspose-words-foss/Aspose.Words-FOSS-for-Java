// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/10/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.words.ref.RefInt;

/// <summary>
/// Implements 'stroke-dasharray' CSS property for SVG.
/// </summary>
class CssStrokeDasharrayPropertyDef extends CssIndividualPropertyDef
{
    CssStrokeDasharrayPropertyDef()
    {
    	super("stroke-dasharray", true, CssDashArrayPropertyValue.NONE);
	
        // Nothing to do.
    }

    protected /*override*/ CssPropertyValue createPropertyValue(
        CssValueList cssValues,
        boolean isInQuirksMode)
    {
        // stroke-dasharray CSS property accepts the values: none | < dasharray > | inherit.
        CssPropertyValue propertyValue;
        if ((cssValues.getCount() == 1) && cssValues.get(0).equals(CssValue.None))
        {
            propertyValue = CssDashArrayPropertyValue.NONE;
        }
        else if (cssValues.getCount() >= 1)
        {
            boolean legalValues = true;
            CssValueList values = new CssValueList();
            for (CssValue value : cssValues)
            {
                if ((value.getValueType() != CssValueType.NUMBER) &&
                    (value.getValueType() != CssValueType.LENGTH) &&
                    (value.getValueType() != CssValueType.PERCENTAGE) &&
                    (value.getValueType() != CssValueType.COMMA))
                {
                    legalValues = false;
                    break;
                }

                if (value.getValueType() != CssValueType.COMMA)
                    values.add(value);

            }

            propertyValue = (legalValues)
                ? new CssDashArrayPropertyValue(values)
                : null;
        }
        else
        {
            propertyValue = null;
        }

        return propertyValue;
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
}

