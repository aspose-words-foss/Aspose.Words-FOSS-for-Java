// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2015 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.FontStyle;


/// <summary>
/// Interface for the font provider.
/// </summary>
public interface IFontProvider
{
    /// <summary>
    /// Gets the specified font. Returns null if font is not available.
    /// </summary>
    public TTFont getTTFont(String familyName, /*FontStyle*/int style);

    /// <summary>
    /// Fetches the TrueType font and tries to perform a simple font substitution.
    /// </summary>
    public TTFont fetchTTFont(String familyName, /*FontStyle*/int style) throws Exception;

    /// <summary>
    /// Returns fallback font for the specified font and character code.
    /// Returns null if fallback font is not found.
    /// </summary>
    /// <param name="font">Font for which fallback font is searched.</param>
    /// <param name="charCode">Character code.</param>
    /// <param name="useCharacterReplacements">
    /// Specifies if character replacements should be used when checking fallback font for glyph availability.
    /// </param>
    public TTFont getFallbackFont(TTFont font, int charCode, boolean useCharacterReplacements);
}

