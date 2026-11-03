// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/03/2014 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



class MarkupLanguageShapeWriter
{
    static boolean isShapeFloating(ShapeBase shapeBase)
    {
        return (shapeBase.getWrapType() == WrapType.NONE);
    }

    static boolean isShapeRenderable(ShapeBase shapeBase)
    {
        // Non-top level shapes will be rendered as parts of corresponding group shapes.
        return shapeBase.isTopLevel() && !shapeBase.getHidden() && !shapeBase.getFont().getHidden();
    }
}

