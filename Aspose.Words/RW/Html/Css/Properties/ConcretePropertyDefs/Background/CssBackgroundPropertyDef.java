// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Implements 'background' shorthand CSS property.
/// </summary>
/// <remarks>
/// The background shorthand property sets all the background properties in one declaration.
/// The properties that can be set, are: background-color, background-position, background-size, 
/// background-repeat, background-origin, background-clip, background-attachment, and background-image.
/// Syntax:
///   background: color position size repeat origin clip attachment image;
/// </remarks>
class CssBackgroundPropertyDef extends CssOrderInsensitiveShorthandPropertyDef
{
    CssBackgroundPropertyDef()
    {
    	super("background", false);
	
        // Empty constructor.
    }

    protected /*override*/ CssPropertyValue createShorthandValue(CssDeclarationCollection individualDeclarations)
    {
        CssDeclaration colorDeclaration = individualDeclarations.get("background-color");
        CssDeclaration imageDeclaration = individualDeclarations.get("background-image");
        CssDeclaration repeatDeclaration = individualDeclarations.get("background-repeat");
        CssDeclaration attachmentDeclaration = individualDeclarations.get("background-attachment");
        CssDeclaration positionDeclaration = individualDeclarations.get("background-position");

        Debug.assertTrue((colorDeclaration != null) || (imageDeclaration != null) ||
                     (repeatDeclaration != null) || (attachmentDeclaration != null) ||
                     (positionDeclaration != null));
        Debug.assertTrue((positionDeclaration == null) || (positionDeclaration.getValue() instanceof CssBackgroundPositionPropertyValue));

        return new CssBackgroundPropertyValue(
            (colorDeclaration != null) ? colorDeclaration.getValue().getFirstValue() : null,
            (imageDeclaration != null) ? imageDeclaration.getValue().getFirstValue() : null,
            (repeatDeclaration != null) ? repeatDeclaration.getValue().getFirstValue() : null,
            (attachmentDeclaration != null) ? attachmentDeclaration.getValue().getFirstValue() : null,
            (positionDeclaration != null)
                ? (CssBackgroundPositionPropertyValue)positionDeclaration.getValue()
                : null);
    }

    protected /*override*/ ShorthandPropertyPart[] getIndividualProperties()
    {
        if (mIndividualProperties != null)
            return mIndividualProperties;

        mIndividualProperties = new ShorthandPropertyPart[]
        {
            new ShorthandPropertyPart("background-color", false),
            new ShorthandPropertyPart("background-image", false),
            new ShorthandPropertyPart("background-repeat", false),
            new ShorthandPropertyPart("background-attachment", false),
            new ShorthandPropertyPart("background-position", false)
        };
        return mIndividualProperties;
    }

    private ShorthandPropertyPart[] mIndividualProperties;
}

