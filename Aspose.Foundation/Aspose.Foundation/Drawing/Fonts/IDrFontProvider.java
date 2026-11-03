// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/11/2016 by Alexey Butalov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.FontStyle;


/// <summary>
/// Interface for the font provider.
/// This interface is extracted from IFontProvider to break circular dependency Aspose.Fonts from Aspose.Drawing.
/// </summary>
public interface IDrFontProvider extends IFontProvider
{
    /// <summary>
    /// Fetches the drawing font and tries to perform a simple font substitution.
    /// </summary>
    public DrFont fetchDrFont(String familyName, float sizePoints, /*FontStyle*/int style) throws Exception;

    /// <summary>
    /// Fetches the drawing font and tries to perform a simple font substitution.
    /// </summary>
    public DrFont fetchDrFont(String familyName, float sizePoints, /*FontStyle*/int actualStyle, /*FontStyle*/int fontFaceStyle,
                       boolean isVertical) throws Exception;
}

