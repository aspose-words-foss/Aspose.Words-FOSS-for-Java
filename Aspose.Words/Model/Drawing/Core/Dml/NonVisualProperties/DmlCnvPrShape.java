// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.3.2.8 cNvSpPr (Non-Visual Shape Drawing Properties)
/// This element specifies the non-visual drawing properties for a shape.
/// </summary>
class DmlCnvPrShape extends DmlCnvPrBase
{
    /*override*/ /*DmlNvHolder*/int getHolder() { return DmlNvHolder.SHAPE; }

    /// <summary>
    /// Specifies that the corresponding shape is a text box and thus should be treated as such by the generating application.
    /// Default is false.
    /// </summary>
    boolean getTextBox() { return mTextBox; }
    void setTextBox(boolean value) { mTextBox = value; }

    private boolean mTextBox;
}

