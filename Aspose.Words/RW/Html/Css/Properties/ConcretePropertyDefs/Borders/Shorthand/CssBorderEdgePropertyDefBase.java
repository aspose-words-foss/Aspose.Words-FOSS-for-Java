// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msObject;

/// <summary>
/// Base class for 'border-[top|right|bottom|left]' CSS property.
/// </summary>
/// <remarks>
/// This CSS property is a shorthand that sets the values of border-edge-color, border-edge-style, and border-edge-width. 
/// The three values of the shorthand property can be specified in any order, and one or two of them may be omitted.
/// </remarks>
abstract class CssBorderEdgePropertyDefBase extends CssOrderInsensitiveShorthandPropertyDef
{
    protected CssBorderEdgePropertyDefBase(
        String shorthandPropertyName,
        String widthPropertyName,
        String stylePropertyName,
        String colorPropertyName)
    {
    	super(shorthandPropertyName, false);
	
        Debug.assertTrue(StringUtil.hasChars(widthPropertyName));
        Debug.assertTrue(StringUtil.hasChars(stylePropertyName));
        Debug.assertTrue(StringUtil.hasChars(colorPropertyName));

        Debug.assertTrue(StringUtil.isAsciiLowerCase(widthPropertyName));
        Debug.assertTrue(StringUtil.isAsciiLowerCase(stylePropertyName));
        Debug.assertTrue(StringUtil.isAsciiLowerCase(colorPropertyName));

        mWidthPropertyName = widthPropertyName;
        mStylePropertyName = stylePropertyName;
        mColorPropertyName = colorPropertyName;
    }

    protected /*override*/ CssPropertyValue createShorthandValue(CssDeclarationCollection declarations)
    {
        // Note that the shorthand property always sets all of the corresponding longhand properties.
        // If no value is specified for a longhand property, it is reset to the initial value.
        CssValue widthValue = getLonghandValue(declarations, getWidthPropertyName());
        CssValue styleValue = getLonghandValue(declarations, getStylePropertyName());
        CssValue colorValue = getLonghandValue(declarations, getColorPropertyName());

        // All three longhand property values must be specified or equal to 'initial'.
        if ((widthValue == null) ||
            (styleValue == null) ||
            (colorValue == null))
        {
            return null;
        }

        if (widthValue.equals(CssValue.Initial) &&
            styleValue.equals(CssValue.Initial) &&
            colorValue.equals(CssValue.Initial))
        {
            // This is the default value for the 'border-xxx-style' property, and 'border-xxx:none' is essentially
            // an equivalent of 'border-xxx:initial'. Since 'border-xxx:none' is used more often, we use this variant.
            return new CssPropertyValue(CssValue.None);
        }

        if (widthValue.equals(CssValue.Inherit) &&
            styleValue.equals(CssValue.Inherit) &&
            colorValue.equals(CssValue.Inherit))
        {
            return new CssPropertyValue(CssValue.Inherit);
        }

        CssValueList values = new CssValueList();

        // No need to write 'initial' values, because they are implied by the shorthand property.
        if (!widthValue.equals(CssValue.Initial))
        {
            values.add(widthValue);
        }
        if (!styleValue.equals(CssValue.Initial))
        {
            values.add(styleValue);
        }
        if (!colorValue.equals(CssValue.Initial))
        {
            values.add(colorValue);
        }

        return new CssPropertyValue(values);
    }

    private static CssValue getLonghandValue(
        CssDeclarationCollection declarations,
        String propertyName)
    {
        CssDeclaration declaration = declarations.get(propertyName);
        if ((declaration == null) || (declaration.getValue().getCount() != 1))
        {
            return null;
        }
        CssIndividualPropertyDef propertyDef = CssPropertyDefFactory.getIndividualPropertyDef(propertyName);
        if (propertyDef == null)
        {
            return null;
        }
        CssValue value = declaration.getValue().getFirstValue();
        if (msObject.equals(propertyDef.getInitialValue(), value))
        {
            return CssValue.Initial;
        }
        return value;
    }

    protected /*override*/ ShorthandPropertyPart[] getIndividualProperties()
    {
        if (mIndividualProperties != null)
            return mIndividualProperties;

        mIndividualProperties = new ShorthandPropertyPart[]
        {
            new ShorthandPropertyPart(getWidthPropertyName(), false),
            new ShorthandPropertyPart(getStylePropertyName(), false),
            new ShorthandPropertyPart(getColorPropertyName(), false)
        };
        return mIndividualProperties;
    }

    protected String getWidthPropertyName() { return mWidthPropertyName; };

    private  String mWidthPropertyName;

    protected String getStylePropertyName() { return mStylePropertyName; };

    private  String mStylePropertyName;

    protected String getColorPropertyName() { return mColorPropertyName; };

    private  String mColorPropertyName;

    private ShorthandPropertyPart[] mIndividualProperties;
}

