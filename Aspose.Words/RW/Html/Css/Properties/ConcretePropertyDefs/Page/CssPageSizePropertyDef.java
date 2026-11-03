// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.words.ref.RefInt;

/// <summary>
/// Implements 'size' CSS property of @page rule.
/// </summary>
/// <remarks>
/// This property specifies the target size and orientation of the page box’s containing block. In the general case,
/// where one page box is rendered onto one page sheet, the ‘size’ property also indicates the size of the destination page sheet.
/// http://www.w3.org/TR/css3-page/#page-size-prop
/// </remarks>
class CssPageSizePropertyDef extends CssIndividualPropertyDef
{
    CssPageSizePropertyDef()
    {
    	super("size", false, CssPageSizePropertyValue.createAuto());
	
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
        // Value: <length>{1,2} | auto | [ <page-size> || [ portrait | landscape] ]
        switch (cssValues.getCount())
        {
            case 1:
            {
                CssValue value = cssValues.get(0);

                if (value.equals(CssValue.Auto))
                    return CssPageSizePropertyValue.createAuto();

                if (value.getValueType() == CssValueType.IDENTIFIER)
                    return CssPageSizePropertyValue.createPageSize((CssIdentifierValue)value);

                // MS Word accepts unitless lengths in this case.
                CssLengthValue valueAsLength = value.toLength(true);
                if (valueAsLength != null)
                {
                    return CssPageSizePropertyValue.createLength(valueAsLength);
                }

                break;
            }
            case 2:
            {
                CssValue left = cssValues.get(0);
                CssValue right = cssValues.get(1);
                if ((left.getValueType() == CssValueType.IDENTIFIER) && (right.getValueType() == CssValueType.IDENTIFIER))
                {
                    return CssPageSizePropertyValue.createPageSize((CssIdentifierValue)left, (CssIdentifierValue)right);
                }

                // MS Word accepts unitless lengths in this case.
                CssLengthValue leftAsLength = left.toLength(true);
                CssLengthValue rightAsLength = right.toLength(true);

                if ((leftAsLength != null) && (rightAsLength != null))
                {
                    return CssPageSizePropertyValue.createLength(leftAsLength, rightAsLength);
                }

                break;
            }
            default:
                break;
        }
        return null;
    }
}

