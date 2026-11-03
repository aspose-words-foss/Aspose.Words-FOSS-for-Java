// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/06/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents any unknown (or unsupported by our CSS engine) CSS property definition.
/// </summary>
class CssUnknownPropertyDef extends CssPropertyDef
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="name">Property name.</param>
    CssUnknownPropertyDef(String name)
    {
    	super(name, false);
	
        // Empty constructor.
    }

    /*override*/ CssDeclarationCollection createDeclarations(
        CssValueList cssValues,
        boolean important,
        boolean isInQuirksMode)
    {
        CssPropertyValue propertyValue = new CssPropertyValue(cssValues);
        return new CssDeclarationCollection(new CssSpecifiedDeclaration(getName(), propertyValue, important));
    }
}

