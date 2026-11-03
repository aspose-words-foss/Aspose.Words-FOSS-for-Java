// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// CT_Colors
/// </summary>
class DmlDiagramColors
{
    /*DmlHueDirection*/int getHueDirection() { return mHueDirection; }
    void setHueDirection(/*DmlHueDirection*/int value) { mHueDirection = value; }

    /*DmlColorApplicationMethod*/int getColorApplicationMethod() { return mColorApplicationMethod; }
    void setColorApplicationMethod(/*DmlColorApplicationMethod*/int value) { mColorApplicationMethod = value; }

    DmlColor[] getColors() { return mColors; }
    void setColors(DmlColor[] value) { mColors = value; }

    boolean isEmpty() { return getColors() == null || getColors().length == 0; }

    private /*DmlHueDirection*/int mHueDirection = DmlHueDirection.CLOCKWISE;
    private /*DmlColorApplicationMethod*/int mColorApplicationMethod = DmlColorApplicationMethod.SPAN;
    private DmlColor[] mColors;
}

