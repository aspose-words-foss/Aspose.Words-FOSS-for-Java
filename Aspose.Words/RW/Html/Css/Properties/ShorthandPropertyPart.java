// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/10/2015 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.words.ref.RefInt;

/// <summary>
/// Implements an individual property, which can be a shorthand property part.
/// </summary>
class ShorthandPropertyPart
{
    ShorthandPropertyPart(String propertyName, boolean required)
    {
        mPropertyDef = CssPropertyDefFactory.getIndividualPropertyDef(propertyName);
        mRequired = required;
    }

    /// <summary> 
    /// Creates and returns CSS declarations for the individual property, which is part of shorthand property.
    /// </summary>
    /// <remarks>
    /// 'initial' and 'inherit' values aren't available for individual property, which is part of shorthand property. 
    /// </remarks>
    /// <param name="cssValues">CSS values.</param>
    /// <param name="startIndex">The zero-based index at which looking begins.</param>
    /// <param name="important">Indicates that the declarations should be marked as !important.</param>
    /// <param name="isInQuirksMode">
    /// Indicates whether the CSS document is being processed in the Quirks mode, as opposed to the Standards mode.
    /// </param>
    /// <param name="affectedValues">Affected values count.</param>
    /// <returns>CSS declarations if the property can accept the specified values; null otherwise.</returns>
    CssDeclarationCollection createIndividualDeclarations(
        CssValueList cssValues,
        int startIndex,
        boolean important,
        boolean isInQuirksMode,
        /*out*/RefInt affectedValues)
    {
        CssDeclaration declaration = mPropertyDef.createIndividualDeclaration(
            cssValues,
            startIndex,
            important,
            isInQuirksMode,
            /*out*/ affectedValues);
        return (declaration != null)
            ? new CssDeclarationCollection(declaration)
            : null;
    }

    /// <summary> 
    /// Returns CSS declarations with 'inherit' values.
    /// </summary>
    /// <param name="important">Indicates that the declarations should be marked as !important.</param>
    /// <returns>CSS declarations with 'inherit' values.</returns>
    CssDeclarationCollection createInheritDeclarations(boolean important)
    {
        return new CssDeclarationCollection(mPropertyDef.createInheritDeclaration(important));
    }

    /// <summary> 
    /// Returns CSS declarations with initial values of the properties.
    /// </summary>
    /// <param name="important">Indicates that the declarations should be marked as !important.</param>
    /// <returns>CSS declarations with initial values of the properties if the property has initial value; null otherwise.</returns>
    CssDeclarationCollection createInitialDeclarations(boolean important)
    {
        CssDeclaration initialDeclaration = mPropertyDef.createInitialDeclaration(important);
        return (initialDeclaration != null)
            ? new CssDeclarationCollection(initialDeclaration)
            : null;
    }

    /// <summary>
    /// Whether the property is required.
    /// </summary>
    boolean getRequired() { return mRequired; };

    private boolean mRequired;

    /// <summary>
    /// Individual property name.
    /// </summary>
    String getPropertyName() { return mPropertyDef.getPropertyName(); }

    private /*final*/ CssIndividualPropertyDef mPropertyDef;
}

