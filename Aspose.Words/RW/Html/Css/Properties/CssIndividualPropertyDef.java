// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.words.ref.RefInt;

/// <summary>
/// Base class for CSS individual properties definitions.
/// Individual properties are CSS properties that let you set the value of one CSS property only.
/// e.g: margin-left: 10px
///      background-position: 10px 30px
/// </summary>
abstract class CssIndividualPropertyDef extends CssPropertyDef
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="name">Property name.</param>
    /// <param name="inherited">Indicates that the property is normally inherited.</param>
    /// <param name="initialValue">Initial value of the property. Can be null.</param>
    protected CssIndividualPropertyDef(String name, boolean inherited, CssPropertyValue initialValue)
    {
    	super(name, inherited);
	
        mInitialValue = initialValue;
    }

    CssDeclaration createInitialDeclaration(boolean important)
    {
        return (mInitialValue != null)
                   ? new CssSpecifiedDeclaration(getName(), mInitialValue, important)
                   : null;
    }

    abstract CssDeclaration createIndividualDeclaration(
        CssValueList cssValues,
        int startIndex,
        boolean important,
        boolean isInQuirksMode,
        /*out*/RefInt affectedValues);

    /*virtual*/ CssDeclaration createInheritDeclaration(boolean important)
    {
        return new CssSpecifiedDeclaration(getName(), new CssPropertyValue(CssValue.Inherit), important);
    }

    /*override*/ CssDeclarationCollection createDeclarations(
        CssValueList cssValues,
        boolean important,
        boolean isInQuirksMode)
    {
        CssDeclaration declaration = createDeclaration(cssValues, important, isInQuirksMode);
        return (declaration != null)
            ? new CssDeclarationCollection(declaration)
            : null;
    }

    /// <summary>
    /// Replaces the "initial" keyword with an initial value of a CSS property and calls a method that resolves a
    /// specified value to a computed one.
    /// </summary>
    /*override*/ CssPropertyValue toComputedValue(CssPropertyValue specifiedValue, CssCascadeContext cssContext)
    {
        if ((specifiedValue.getCount() > 0) &&
            specifiedValue.isInitial() &&
            (mInitialValue != null))
        {
            specifiedValue = mInitialValue;
        }

        return toComputedValueCore(specifiedValue, cssContext);
    }

    /// <summary>
    /// Resolves a specified property value to a computed one.
    /// </summary>
    /// <remarks>
    /// This method is overridden in some properties that compute their values in a special way.
    /// </remarks>
    protected /*virtual*/ CssPropertyValue toComputedValueCore(
        CssPropertyValue specifiedValue,
        CssCascadeContext cssContext)
    {
        return computeRelativeLengthValue(specifiedValue, cssContext);
    }

    /// <summary>
    /// Computes a relative length value. If the value is not a relative length, returns it unchanged.
    /// </summary>
    protected static CssPropertyValue computeRelativeLengthValue(
        CssPropertyValue specifiedValue,
        CssCascadeContext cssContext)
    {
        if ((specifiedValue.getCount() == 1) && (specifiedValue.getFirstValue().getValueType() == CssValueType.LENGTH))
        {
            CssLengthValue cssLength = (CssLengthValue)specifiedValue.getFirstValue();
            if (cssLength.isRelative())
            {
                double absoluteLength = CssUtil.relativeLengthToPoint(
                    cssLength,
                    cssContext.getRootElementDeclarations(),
                    cssContext.getElementFontSize());
                if (!MathUtil.areEqual(absoluteLength, -Double.MAX_VALUE))
                    return new CssPropertyValue(new CssLengthValue(absoluteLength, CssUnit.PT));
            }
        }

        return specifiedValue;
    }

    protected CssDeclaration createDeclaration(
        CssValueList cssValues,
        boolean important,
        boolean isInQuirksMode)
    {
        CssPropertyValue propertyValue;
        // The 'inherit' and 'initial' values are allowed for all CSS properties.
        if ((cssValues.getCount() == 1) && cssValues.get(0).equals(CssValue.Inherit))
            propertyValue = new CssPropertyValue(CssValue.Inherit);
        else if ((cssValues.getCount() == 1) && cssValues.get(0).equals(CssValue.Initial))
            propertyValue = new CssPropertyValue(CssValue.Initial);
        else
            propertyValue = createPropertyValue(cssValues, isInQuirksMode);

        return (propertyValue != null)
            ? new CssSpecifiedDeclaration(getName(), propertyValue, important)
            : null;
    }

    /// <summary> 
    /// Implement in derived classes and return a CSS property value if the property can accept the specified values.
    /// You should not process 'inherit' and 'initial' input values in this function.
    /// </summary>
    /// <param name="cssValues">CSS values.</param>
    /// <param name="isInQuirksMode">
    /// Indicates whether the CSS document is being processed in the Quirks mode, as opposed to the Standards mode.
    /// </param>
    /// <returns>CSS property value if the property can accept the specified values; null otherwise.</returns>
    protected abstract CssPropertyValue createPropertyValue(CssValueList cssValues, boolean isInQuirksMode);

    String getPropertyName() { return getName(); }

    /// <summary>
    /// Initial value of the property i.e. the value to be used in the absence of any stylesheet or equivalent. Can be null.
    /// </summary>
    /*override*/ CssPropertyValue getInitialValue() { return mInitialValue; }

    private /*final*/ CssPropertyValue mInitialValue;
}

