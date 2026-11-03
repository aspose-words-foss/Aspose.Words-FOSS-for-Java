// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2020 by Artem Shabarshin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;

/// <summary>
/// Base class for '-aw-border-[top|right|bottom|left]' CSS extended border property.
/// </summary>
/// <remarks>
/// This CSS property is a shorthand that sets the values of -aw-border-edge-color, -aw-border-edge-style, and -aw-border-edge-width. 
/// The three values of the shorthand property can be specified in any order, and one or two of them may be omitted.
/// </remarks>
abstract class CssAWBorderEdgePropertyDefBase extends CssOrderInsensitiveShorthandPropertyDef
{
    protected CssAWBorderEdgePropertyDefBase(
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

    protected /*override*/ CssPropertyValue createShorthandValue(CssDeclarationCollection individualDeclarations)
    {
        CssValueList values = new CssValueList();

        CssDeclaration widthDeclaration = individualDeclarations.get(getWidthPropertyName());
        if (widthDeclaration != null)
        {
            values.add(widthDeclaration.getValue().getFirstValue());
        }

        CssDeclaration styleDeclaration = individualDeclarations.get(getStylePropertyName());
        if (styleDeclaration != null)
        {
            values.add(styleDeclaration.getValue().getFirstValue());
        }

        CssDeclaration colorDeclaration = individualDeclarations.get(getColorPropertyName());
        if (colorDeclaration != null)
        {
            values.add(colorDeclaration.getValue().getFirstValue());
        }

        return new CssPropertyValue(values);
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

