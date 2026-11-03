// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;


/// <summary>
/// Base class for CSS properties definition.
/// http://www.w3.org/TR/CSS21/syndata.html#declaration
/// </summary>
abstract class CssPropertyDef
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="name">Property name.</param>
    /// <param name="inherited">Indicates that the property is normally inherited.</param>
    protected CssPropertyDef(String name, boolean inherited)
    {
        Debug.assertTrue(StringUtil.hasChars(name));
        Debug.assertTrue(StringUtil.isAsciiLowerCase(name));

        mName = name;
        mInherited = inherited;
    }

    /// <summary> 
    /// Returns CSS declarations if the property can accept the specified value.
    /// </summary>
    /// <param name="cssValue">CSS value.</param>
    /// <param name="important">Indicates that the declarations should be marked as !important.</param>
    /// <param name="isInQuirksMode">
    /// Indicates whether the CSS document is being processed in the Quirks mode, as opposed to the Standards mode.
    /// </param>
    /// <returns>CSS declarations if the property can accept the specified value; null otherwise.</returns>
    /// <remarks>Used only for testing.</remarks>
    CssDeclarationCollection createDeclarations(
        CssValue cssValue,
        boolean important,
        boolean isInQuirksMode)
    {
        return createDeclarations(new CssValueList(cssValue), important, isInQuirksMode);
    }

    /// <summary> 
    /// Returns CSS declarations if the property can accept the specified values.
    /// </summary>
    /// <param name="cssValues">CSS values.</param>
    /// <param name="important">Indicates that the declaration should be marked as !important.</param>
    /// <param name="isInQuirksMode">
    /// Indicates whether the CSS document is being processed in the Quirks mode, as opposed to the Standards mode.
    /// </param>
    /// <returns>CSS declarations if the property can accept the specified values; null otherwise.</returns>
    abstract CssDeclarationCollection createDeclarations(
        CssValueList cssValues,
        boolean important,
        boolean isInQuirksMode);

    /// <summary>
    /// Resolves specified value to computed value. E.g. 'em' and 'ex' units are computed to absolute lengths.
    /// </summary>
    /// <remarks>
    /// The computed value is the result of resolving the specified value, generally absolutizing it in preparation for inheritance.
    /// A specified value can be either absolute (i.e., not relative to another value, as in red or 2mm) or relative (i.e., relative to another value, 
    /// as in auto, 2em). Computing a relative value generally absolutizes it:
    ///   - values with relative units (em, ex, vh, vw) must be made absolute by multiplying with the appropriate reference size
    ///   - certain keywords (e.g., smaller, bolder) must be replaced according to their definitions
    ///   - percentages on some properties must be multiplied by a reference value (defined by the property)
    ///   - valid relative URLs must be resolved to become absolute.
    /// </remarks>
    /// <param name="specifiedValue">Specified CSS value.</param>
    /// <param name="cssContext">CSS context.</param>
    /// <returns>Computed CSS value.</returns>
    /*virtual*/ CssPropertyValue toComputedValue(CssPropertyValue specifiedValue, CssCascadeContext cssContext)
    {
        // If we don't know how this property computes its value, leave it unchanged.
        return specifiedValue;
    }

    /// <summary>
    /// Applies CSS property value to a model table.
    /// </summary>
    /*virtual*/ void toTable(CssPropertyValue propertyValue, Table table) throws Exception
    {
    }

    /// <summary>
    /// Applies CSS property value to a model row.
    /// </summary>
    /*virtual*/ void toRow(CssPropertyValue propertyValue, Row row) throws Exception
    {
    }

    /// <summary>
    /// Applies CSS property value to a model cell format.
    /// </summary>
    /*virtual*/ void toCellFormat(CssPropertyValue propertyValue, CellFormat cellFormat) throws Exception
    {
    }

    /// <summary>
    /// Applies CSS property value to a document.
    /// </summary>
    /*virtual*/ void toDocument(CssPropertyValue propertyValue, Document document)
    {
    }

    /// <summary>
    /// Applies CSS property value to a horizontal rule shape.
    /// </summary>
    /*virtual*/ void toHorizontalRule(CssPropertyValue propertyValue, Shape horizontalRuleShape) throws Exception
    {
    }

    /// <summary>
    /// Applies CSS property value to a shape.
    /// </summary>
    /*virtual*/ void toShape(CssPropertyValue propertyValue, Shape shape) throws Exception
    {
    }

    /// <summary>
    /// The property name.
    /// </summary>
    String getName() { return mName; };

    private String mName;

    /// <summary>
    /// Indicates that the property is normally inherited.
    /// </summary>
    boolean getInherited() { return mInherited; };

    private boolean mInherited;

    /// <summary>
    /// Initial value of the property i.e. the value to be used in the absence of any stylesheet or equivalent;
    /// <c>null</c> if the property has no an initial value.
    /// </summary>
    /*virtual*/ CssPropertyValue getInitialValue() { return null; }
}

