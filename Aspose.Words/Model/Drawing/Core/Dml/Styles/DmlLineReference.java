// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.4.2.19 lnRef (Line Reference)
/// This element defines a reference to a line style within the style matrix.
/// </summary>
class DmlLineReference extends DmlStyleReferenceBase
{
    /// <summary>
    /// Specifies the style matrix index of the style referred to.
    /// The content is a restriction of the W3C XML Schema unsignedInt datatype.
    /// </summary>
    int getStyleMatrixIndex() { return mStyleMatrixIndex; }
    void setStyleMatrixIndex(int value) { mStyleMatrixIndex = value; }

    private int mStyleMatrixIndex;
}

