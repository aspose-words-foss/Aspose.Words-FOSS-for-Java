// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/11/2021 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents single layer of the layered colored glyph.
/// </summary>
public class TTGlyphColoredLayer
{
    /// <summary>
    /// Ctor.
    /// </summary>
    public TTGlyphColoredLayer(int glyphIndex, DrColor color)
    {
        mGlyphIndex = glyphIndex;
        mColor = color;
    }

    /// <summary>
    /// Glyph index of the glyph in the layer.
    /// </summary>
    public int getGlyphIndex() { return mGlyphIndex; };

    private  int mGlyphIndex;

    /// <summary>
    /// Color of the layer.
    /// </summary>
    public DrColor getColor() { return mColor; };

    private DrColor mColor;
}

