// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.words.ref.RefInt;

/// <summary>
/// Base class for CSS individual properties with one CSS value.
/// e.g: margin-left: 10px
/// </summary>
abstract class CssIndividualSimplePropertyDef extends CssIndividualPropertyDef
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="name">Property name.</param>
    /// <param name="inherited">Indicates that the property is normally inherited.</param>
    /// <param name="initialValue">Initial value of the property. Can be null.</param>
    /// <param name="valueFilter">Filters for values accepted by this property.</param>
    protected CssIndividualSimplePropertyDef(
        String name,
        boolean inherited,
        CssValue initialValue,
        ICssValueFilter valueFilter)
    {
    	super(
            name,
            inherited,
            (initialValue != null) ? new CssPropertyValue(initialValue) : null);
	
        Debug.assertTrue(valueFilter != null);

        mValueFilter = valueFilter;
    }

    /*override*/ CssDeclaration createIndividualDeclaration(
        CssValueList cssValues,
        int startIndex,
        boolean important,
        boolean isInQuirksMode,
        /*out*/RefInt affectedValues)
    {
        CssPropertyValue propertyValue = createPropertyValue(cssValues.get(startIndex), isInQuirksMode);
        if (propertyValue != null)
        {
            affectedValues.set(1);
            return new CssSpecifiedDeclaration(getName(), propertyValue, important);
        }

        affectedValues.set(0);
        return null;
    }

    protected /*override*/ CssPropertyValue createPropertyValue(
        CssValueList cssValues,
        boolean isInQuirksMode)
    {
        // Only one CSS value is allowed for this property type.
        return (cssValues.getCount() == 1)
            ? createPropertyValue(cssValues.get(0), isInQuirksMode)
            : null;
    }

    /// <summary> 
    /// Returns a CSS property value if the property can accept the specified value.
    /// </summary>
    /// <param name="cssValue">CSS value.</param>
    /// <param name="isInQuirksMode">
    /// Indicates whether the CSS document is being processed in the Quirks mode, as opposed to the Standards mode.
    /// </param>
    /// <returns>CSS property value if the property can accept the specified value; null otherwise.</returns>
    private CssPropertyValue createPropertyValue(CssValue cssValue, boolean isInQuirksMode)
    {
        return mValueFilter.accepts(cssValue, isInQuirksMode)
            ? new CssPropertyValue(cssValue)
            : null;
    }

    /// <summary>
    /// A filter that accepts (passes) only CSS values that are valid for this property.
    /// </summary>
    private /*final*/ ICssValueFilter mValueFilter;
}

