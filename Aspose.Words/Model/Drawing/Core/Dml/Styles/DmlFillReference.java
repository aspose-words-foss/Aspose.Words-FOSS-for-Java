// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.4.2.10 fillRef (Fill Reference)
/// This element defines a reference to a fill style within the style matrix.
/// </summary>
class DmlFillReference extends DmlStyleReferenceBase
{
    /// <summary>
    /// Specifies the style matrix index of the style referred to.
    /// The content is a restriction of the W3C XML Schema unsignedInt datatype.
    /// A value of 0 or 1000 indicates no background,
    /// values 1-999 refer to the index of a fill style within the fillStyleLst element,
    /// and values 1001 and above refer to the index of a background fill
    /// style within the bgFillStyleLst element. The value 1001 corresponds to the
    /// first background fill style, 1002 to the second background fill style, and so on.
    /// </summary>
    int getStyleMatrixIndex() { return mStyleMatrixIndex; }
    void setStyleMatrixIndex(int value) { mStyleMatrixIndex = value; }

    private static final int INDEX_OF_FIRST_BACKGROUND_FILL_STYLE = 1001;
    private static final int INDEX_OF_FIRST_FILL_STYLE = 1;
    private static final int INDEX_OF_LAST_FILL_STYLE = 999;

    private int mStyleMatrixIndex;
}

