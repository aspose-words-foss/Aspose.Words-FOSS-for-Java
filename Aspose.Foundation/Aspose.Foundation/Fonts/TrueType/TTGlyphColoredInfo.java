// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/11/2021 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Represents information about colored version of a glyph.
/// </summary>
/// <remarks>
/// At the moment only layered colored glyphs are supported. Later the support of bitmap and SVG colored glyphs
/// may be added.
/// </remarks>
public class TTGlyphColoredInfo
{
    public TTGlyphColoredInfo(ArrayList<TTGlyphColoredLayer> layers)
    {
        mLayers = layers;
    }

    /// <summary>
    /// List of colored layers.
    /// </summary>
    public ArrayList<TTGlyphColoredLayer> getLayers() { return mLayers; };

    private ArrayList<TTGlyphColoredLayer> mLayers;
}

