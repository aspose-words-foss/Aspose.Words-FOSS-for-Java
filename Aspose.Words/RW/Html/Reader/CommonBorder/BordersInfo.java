// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/10/2015 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Stores information about top, right, bottom and left borders.
/// </summary>
class BordersInfo
{
    BordersInfo(CssDeclarationCollection declarations)
    {
        Debug.assertTrue(declarations != null);

        Top = new BorderInfo(declarations, BorderType.TOP, "padding-top");
        Right = new BorderInfo(declarations, BorderType.RIGHT, "padding-right");
        Bottom = new BorderInfo(declarations, BorderType.BOTTOM, "padding-bottom");
        Left = new BorderInfo(declarations, BorderType.LEFT, "padding-left");
    }

    void applyBorderPropertiesTo(BorderCollection borders) throws Exception
    {
        Debug.assertTrue(borders != null);

        Top.applyBorderPropertiesTo(borders.getTop());
        Right.applyBorderPropertiesTo(borders.getRight());
        Bottom.applyBorderPropertiesTo(borders.getBottom());
        Left.applyBorderPropertiesTo(borders.getLeft());
    }

    void applyPaddingTo(BorderCollection borders) throws Exception
    {
        Debug.assertTrue(borders != null);

        Top.applyPaddingTo(borders.getTop());
        Right.applyPaddingTo(borders.getRight());
        Bottom.applyPaddingTo(borders.getBottom());
        Left.applyPaddingTo(borders.getLeft());
    }

    boolean isVisible() { return Top.isVisible() || Right.isVisible() || Bottom.isVisible() || Left.isVisible(); }

    boolean hasPaddingOrVisibleBorders() { return isVisible() || Top.hasPadding() || Right.hasPadding() || Bottom.hasPadding() || Left.hasPadding(); }

    /*final*/ BorderInfo Top;
    /*final*/ BorderInfo Right;
    /*final*/ BorderInfo Bottom;
    /*final*/ BorderInfo Left;
}

