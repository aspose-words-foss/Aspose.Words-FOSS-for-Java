// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/01/2017 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;


/// <summary>
/// Stores 'background-color' CSS value. Used for background color resolution.
/// </summary>
class CssBackgroundColor
{
    CssBackgroundColor(CssDeclarationCollection declarations)
    {
    	this(declarations.get("background-color"));
	
        // Empty constructor.
    }

    CssBackgroundColor(CssDeclaration declaration)
    {
        if (declaration == null)
        {
            return;
        }

        DrColor color = declaration.getValue().parseAsColor();
        if (color != null)
        {
            mIsDefined = true;
            mColor = color;
        }
    }

    void toShading(IRunAttrSource runAttrSource)
    {
        if (isDefined())
        {
            Shading shading = new Shading();
            toShading(shading);
            runAttrSource.setRunAttr(FontAttr.SHADING, shading);
        }
    }

    void toShading(Shading shading)
    {
        if (isDefined())
        {
            // Plain background color corresponds to BackgroundPatternColor and TextureNone in the model.
            shading.setTexture(TextureIndex.TEXTURE_NONE);
            shading.setBackgroundPatternColorInternal(mColor);
        }
    }

    boolean isDefined() { return mIsDefined; };

    private boolean mIsDefined;

    private /*final*/ DrColor mColor;
}

