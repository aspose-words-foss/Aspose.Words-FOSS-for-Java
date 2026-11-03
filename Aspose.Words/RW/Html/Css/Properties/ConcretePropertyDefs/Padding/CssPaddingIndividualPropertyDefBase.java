// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/07/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.words.ref.RefDouble;

/// <summary>
/// Represents base class for individual 'padding-xxx' CSS properties. 
/// </summary>
abstract class CssPaddingIndividualPropertyDefBase extends CssIndividualSimplePropertyDef
{
    protected CssPaddingIndividualPropertyDefBase(String name)
    {
    	super(
            name,
            false,
            CssValue.Zero,
            // <length> | <percentage>
            CssValueFilter.anyOf(
                CssValueFilter.NON_NEGATIVE_QUIRKY_LENGTH,
                CssValueFilter.NON_NEGATIVE_PERCENTAGE));
	
        // Empty constructor.
    }

    /// <summary>
    /// Computes and returns padding value measured in points.
    /// </summary>
    /// <param name="propertyValue">Property value.</param>
    /// <returns>A padding value in points; zero, if the property value is illegal or unsupported.</returns>
    static double getPadding(CssPropertyValue propertyValue)
    {
        double padding = 0.0;
        if (propertyValue.getCount() == 1)
    	{
            RefDouble referenceToPadding = new RefDouble(padding);
            convertValueToPadding(propertyValue.getFirstValue(), /*out*/ referenceToPadding);
            padding = referenceToPadding.get();
    	}
        return padding;
    }

    /// <summary>
    /// Converts a CSS value to padding value measured in points.
    /// </summary>
    /// <param name="cssValue">CSS value</param>
    /// <param name="paddingPoints">Padding value, points.</param>
    /// <returns>true, if the value was successfully converted; false, otherwise. </returns>
    protected static boolean convertValueToPadding(CssValue cssValue, /*out*/RefDouble paddingPoints)
    {
        double length = CssUtil.lengthToPoint(cssValue);
        if (!MathUtil.areEqual(length, -Double.MAX_VALUE))
        {
            paddingPoints.set(length);
            return true;
        }

        paddingPoints.set((double)0.0);
        return false;
    }
}

