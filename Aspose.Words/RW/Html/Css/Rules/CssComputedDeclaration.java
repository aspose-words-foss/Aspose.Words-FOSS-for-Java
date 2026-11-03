// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/01/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Represents a single CSS declaration (a property and a value). The value of this declaration is computed
/// (context dependency is resolved).
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/CSS2/cascade.html#computed-value for more information on computed CSS property values.
/// </remarks>
class CssComputedDeclaration extends CssDeclaration
{
    CssComputedDeclaration(
        CssPropertyDef propertyDef,
        CssPropertyValue value,
        boolean important,
        CssPropertyValue originalSpecifiedValue)
    {
    	super(value, important);
	
        Debug.assertTrue(propertyDef != null);
        Debug.assertTrue(originalSpecifiedValue != null);
        mPropertyDef = propertyDef;
        mOriginalSpecifiedValue = originalSpecifiedValue;
    }

    /// <summary>
    /// Applies the CSS declaration to a model table.
    /// </summary>
    void toTable(Table table) throws Exception
    {
        mPropertyDef.toTable(getValue(), table);
    }

    /// <summary>
    /// Applies the CSS declaration to a model row.
    /// </summary>
    void toRow(Row row) throws Exception
    {
        mPropertyDef.toRow(getValue(), row);
    }

    /// <summary>
    /// Applies the CSS declaration to a model cell format.
    /// </summary>
    void toCellFormat(CellFormat cellFormat) throws Exception
    {
        mPropertyDef.toCellFormat(getValue(), cellFormat);
    }

    /// <summary>
    /// Applies the CSS declaration to a document.
    /// </summary>
    void toDocument(Document document)
    {
        mPropertyDef.toDocument(getValue(), document);
    }

    /// <summary>
    /// Applies the CSS declaration to a horizontal rule shape.
    /// </summary>
    void toHorizontalRule(Shape horizontalRuleShape) throws Exception
    {
        mPropertyDef.toHorizontalRule(getValue(), horizontalRuleShape);
    }

    /// <summary>
    /// Applies the CSS declaration to a shape.
    /// </summary>
    void toShape(Shape shape) throws Exception
    {
        mPropertyDef.toShape(getValue(), shape);
    }

    /// <summary>
    /// The original specified value for which this value has been computed.
    /// </summary>
    CssPropertyValue getOriginalSpecifiedValue() { return mOriginalSpecifiedValue; };

    private CssPropertyValue mOriginalSpecifiedValue;

    /*override*/ String getProperty() { return mPropertyDef.getName(); }

    private /*final*/ CssPropertyDef mPropertyDef;
}

