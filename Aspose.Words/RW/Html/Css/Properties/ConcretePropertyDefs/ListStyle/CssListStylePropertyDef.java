// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/05/2016 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Implements 'list-style' shorthand CSS property.
/// </summary>
/// <remarks>
/// The 'list-style' property is a shorthand notation for setting the three properties 
/// 'list-style-type', 'list-style-image', and 'list-style-position' at the same place in the style sheet.
/// https://www.w3.org/TR/CSS2/generate.html#propdef-list-style
/// </remarks>
class CssListStylePropertyDef extends CssOrderInsensitiveShorthandPropertyDef
{
    CssListStylePropertyDef()
    {
    	super("list-style", true);
	
        // Empty constructor.
    }

    protected /*override*/ CssPropertyValue createShorthandValue(CssDeclarationCollection individualDeclarations)
    {
        CssDeclaration typeDeclaration = individualDeclarations.get("list-style-type");
        CssDeclaration imageDeclaration = individualDeclarations.get("list-style-image");
        CssDeclaration positionDeclaration = individualDeclarations.get("list-style-position");

        Debug.assertTrue((typeDeclaration != null) || (imageDeclaration != null) || (positionDeclaration != null));

        return new CssListStylePropertyValue(
            (typeDeclaration != null) ? typeDeclaration.getValue().getFirstValue() : null,
            (imageDeclaration != null) ? imageDeclaration.getValue().getFirstValue() : null,
            (positionDeclaration != null) ? positionDeclaration.getValue().getFirstValue() : null);
    }

    protected /*override*/ ShorthandPropertyPart[] getIndividualProperties()
    {
        if (mIndividualProperties == null)
        {
            mIndividualProperties = new ShorthandPropertyPart[]
            {
                new ShorthandPropertyPart("list-style-type", false),
                new ShorthandPropertyPart("list-style-image", false),
                new ShorthandPropertyPart("list-style-position", false)
            };
        }
        return mIndividualProperties;
    }

    private ShorthandPropertyPart[] mIndividualProperties;
}

