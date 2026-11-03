// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.3.2.6 cNvPicPr (Non-Visual Picture Drawing Properties)
/// This element specifies the non-visual properties for the picture canvas.
/// </summary>
class DmlCnvPrPicture extends DmlCnvPrBase
{
    /*override*/ /*DmlNvHolder*/int getHolder() { return DmlNvHolder.PICTURE; }

    /// <summary>
    /// Specifies if the user interface should show the resizing of the picture
    /// based on the picture's current size or its original size.
    /// Default is true.
    /// </summary>
    boolean getPreferRelativeResize() { return mPreferRelativeResize; }
    void setPreferRelativeResize(boolean value) { mPreferRelativeResize = value; }

    private boolean mPreferRelativeResize = true;
}

