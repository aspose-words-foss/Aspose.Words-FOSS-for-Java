// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 20.1.4.1.17 fontRef (Font Reference)
/// This element represents a reference to a themed font. 
/// When used it specifies which themed font to use along with a choice of color.
/// </summary>
class DmlFontReference extends DmlStyleReferenceBase
{
    /// <summary>
    /// Specifies the identifier of the font to reference. 
    /// </summary>
    /*DmlFontCollectionIndex*/int getFontCollectionIndex() { return mFontCollectionIndex; }
    void setFontCollectionIndex(/*DmlFontCollectionIndex*/int value) { mFontCollectionIndex = value; }

    private /*DmlFontCollectionIndex*/int mFontCollectionIndex;
}

