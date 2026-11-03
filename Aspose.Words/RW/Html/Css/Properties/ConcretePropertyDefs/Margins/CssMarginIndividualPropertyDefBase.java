// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;

/// <summary>
/// Represents base class for individual 'margin-xxx' CSS properties.
/// </summary>
abstract class CssMarginIndividualPropertyDefBase extends CssIndividualSimplePropertyDef
{
    protected CssMarginIndividualPropertyDefBase(String name)
    {
    	super(
            name,
            false,
            CssValue.Zero,
            CssValueFilter.anyOf(
                CssValueFilter.value(CssValue.Auto),
                CssValueFilter.PERCENTAGE,
                CssValueFilter.QUIRKY_LENGTH));
	
        // Empty constructor.
    }

    /// <summary>
    /// Computes and returns indent value measured in points.
    /// </summary>
    /// <param name="propertyValue">Property value.</param>
    /// <returns>An indent value in points; zero, if the property value is illegal or unsupported.</returns>
    static double getIndent(CssPropertyValue propertyValue)
    {
        double indentValue = CssUtil.lengthToPoint(propertyValue);
        if (MathUtil.areEqual(indentValue, -Double.MAX_VALUE))
            indentValue = 0.0;
        return indentValue;
    }
}

