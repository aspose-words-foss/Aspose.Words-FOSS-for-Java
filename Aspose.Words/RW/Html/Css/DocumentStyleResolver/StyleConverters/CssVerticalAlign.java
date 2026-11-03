// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/06/2015 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Stores information about sup or sub tags and other tags inside.
/// </summary>
class CssVerticalAlign
{
    CssVerticalAlign(CssDeclarationCollection declarations)
    {
        CssDeclaration verticalAlignDeclaration = declarations.get("vertical-align");
        if (verticalAlignDeclaration != null)
        {
            mVerticalAlignValue = verticalAlignDeclaration.getValue();
        }
    }

    CssVerticalAlign(CssPropertyValue verticalAlignValue)
    {
        mVerticalAlignValue = verticalAlignValue;
    }

    CssPropertyValue getVerticalAlignValue() { return mVerticalAlignValue; }

    private /*final*/ CssPropertyValue mVerticalAlignValue;
}
