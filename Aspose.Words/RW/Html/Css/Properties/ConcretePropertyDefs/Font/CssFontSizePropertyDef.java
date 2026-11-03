// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msObject;
import com.aspose.MathUtil;
import com.aspose.Debug;

/// <summary>
/// Implements 'font-size' CSS property.
/// </summary>
class CssFontSizePropertyDef extends CssIndividualSimplePropertyDef
{
    CssFontSizePropertyDef()
    {
    	super(
            "font-size",
            true,
            CssValue.Medium,
            // <absolute-size> | <relative-size> | <length> | <percentage> | inherit
            CssValueFilter.anyOf(
                CssValueFilter.values(
                    CssValue.XxSmall,
                    CssValue.XSmall,
                    CssValue.Small,
                    CssValue.Medium,
                    CssValue.XxLarge,
                    CssValue.XLarge,
                    CssValue.Large,
                    CssValue.XxxLarge,
                    CssValue.Larger,
                    CssValue.Smaller),
                CssValueFilter.NON_NEGATIVE_QUIRKY_LENGTH,
                CssValueFilter.NON_NEGATIVE_PERCENTAGE));
	
        // Empty constructor.
    }

    protected /*override*/ CssPropertyValue toComputedValueCore(CssPropertyValue specifiedValue, CssCascadeContext cssContex)
    {
        if (specifiedValue.getCount() == 1)
        {
            // Replace identifiers (like 'smaller' or 'larger') with equivalent lengths.
            // Also replace percentages with lengths in Em units.
            CssValue cssValue = specifiedValue.getFirstValue();
            CssValue equivalentLengthValue = getEquivalentLengthValue(cssValue);
            if (!msObject.referenceEquals(equivalentLengthValue, cssValue))
            {
                specifiedValue = new CssPropertyValue(equivalentLengthValue);
            }

            // Computation of relative 'font-size' values differs from other CSS values. 'em' and 'ex' for this property
            // refer to 'font-size' of the parent element, not to this element itself.
            // Note that this rule also further converts values created for special identifiers like 'smaller' or 'larger'.
            CssLengthValue valueAsLength = ms.as(specifiedValue.getFirstValue(), CssLengthValue.class);
            if ((valueAsLength != null) && valueAsLength.isRelative())
            {
                double absoluteLength = CssUtil.relativeLengthToPoint(
                    valueAsLength,
                    cssContex.getRootElementDeclarations(),
                    CssUtil.computeFontSize(cssContex.getParentElementDeclarations()));
                if (!MathUtil.areEqual(absoluteLength, -Double.MAX_VALUE))
                {
                    specifiedValue =
                        new CssPropertyValue(new CssLengthValue(absoluteLength, CssUnit.PT));
                }
            }
        }

        return specifiedValue;
    }

    /// <summary>
    /// Gets a length value (absolute or relative) equivalent to the specified value if possible.
    /// </summary>
    /// <returns>
    /// If the specified value is an identifier, the method returns a length value equivalent to that identifier.
    /// If the specified value is a percentage, the method returns a value in Em units equivalent to the percentage.
    /// Otherwise, the method does nothing and returns the specified value.
    /// </returns>
    private static CssValue getEquivalentLengthValue(CssValue value)
    {
        CssValue result = value;
        switch (value.getValueType())
        {
            case CssValueType.IDENTIFIER:
            {
                double fontSize = -1;
                if (value.equals(CssValue.XxSmall))
                {
                    fontSize = 7.0;
                }
                else if (value.equals(CssValue.XSmall))
                {
                    fontSize = 7.5;
                }
                else if (value.equals(CssValue.Small))
                {
                    fontSize = 10.0;
                }
                else if (value.equals(CssValue.Medium))
                {
                    fontSize = 12.0;
                }
                else if (value.equals(CssValue.Large))
                {
                    fontSize = 13.5;
                }
                else if (value.equals(CssValue.XLarge))
                {
                    fontSize = 18.0;
                }
                else if (value.equals(CssValue.XxLarge))
                {
                    fontSize = 24.0;
                }
                else if (value.equals(CssValue.XxxLarge))
                {
                    fontSize = 24.0 * 1.5;
                }
                else
                {
                    if (value.equals(CssValue.Larger))
                        result = new CssLengthValue(LARGER_SMALLER_RATIO, CssUnit.EM);
                    else if (value.equals(CssValue.Smaller))
                        result = new CssLengthValue(1.0 / LARGER_SMALLER_RATIO, CssUnit.EM);
                }
                if (fontSize > 0)
                    result = new CssLengthValue(fontSize, CssUnit.PT);
                break;
            }
            case CssValueType.PERCENTAGE:
            {
                CssPercentageValue percentageValue = (CssPercentageValue)value;
                double percents = percentageValue.getDoubleValue() / 100.0;
                result = new CssLengthValue(percents, CssUnit.EM);
                break;
            }
            case CssValueType.STRING:
            case CssValueType.NUMBER:
            case CssValueType.LENGTH:
            case CssValueType.URI:
            case CssValueType.COMMA:
            case CssValueType.SOLIDUS:
            {
                break;
            }
            default:
            {
                Debug.assertTrue(false);
                break;
            }
        }
        return result;
    }

    // We use a scale 1.2 but, there is no standard and browser results will differ.
    private static final double LARGER_SMALLER_RATIO = 1.2;
}

