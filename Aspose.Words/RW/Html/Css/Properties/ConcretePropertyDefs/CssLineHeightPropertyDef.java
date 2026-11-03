// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;

/// <summary>
/// Implements 'line-height' CSS property.
/// </summary>
/// <remarks>CSS line height is applied to a model format with <see cref="CssLineHeightStyleConverter"/></remarks>
class CssLineHeightPropertyDef extends CssIndividualSimplePropertyDef
{
    CssLineHeightPropertyDef()
    {
    	super(
            "line-height",
            true,
            CssValue.Normal,
            // normal | <number> | <length> | <percentage>
            CssValueFilter.anyOf(
                CssValueFilter.value(CssValue.Normal),
                CssValueFilter.NON_NEGATIVE_NUMBER,
                CssValueFilter.NON_NEGATIVE_LENGTH,
                CssValueFilter.NON_NEGATIVE_PERCENTAGE));
	
        // Empty constructor.
    }

    protected /*override*/ CssPropertyValue toComputedValueCore(CssPropertyValue specifiedValue, CssCascadeContext cssContext)
    {
        if ((specifiedValue.getCount() == 1) &&
            (specifiedValue.getFirstValue().getValueType() == CssValueType.PERCENTAGE) &&
            !MathUtil.isZero(cssContext.getElementFontSize()))
        {
            CssPercentageValue percentage = (CssPercentageValue)specifiedValue.getFirstValue();
            double lineHeightInPoints = cssContext.getElementFontSize() * percentage.getDoubleValue() / 100.0;
            CssLengthValue computedLineHeight = new CssLengthValue(lineHeightInPoints, CssUnit.PT);
            specifiedValue = new CssPropertyValue(computedLineHeight);
        }
        return computeRelativeLengthValue(specifiedValue, cssContext);
    }
}

