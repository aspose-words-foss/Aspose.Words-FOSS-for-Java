// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/26/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 2.5.36 CT_NonVisualInkContentPartProperties.
/// A complex type that specifies non-visual ink properties for a content part. 
/// This provides additional information that does not affect the appearance of ink in the content part to be stored.
/// </summary>
class DmlCnvPrContentPart extends DmlCnvPrBase
{
    /// <summary>
    /// Specifies whether the ink shape is a comment or an annotation. 
    /// If true, the ink is a comment; otherwise, it is an annotation.
    /// Default is true.
    /// </summary>
    boolean isComment() { return mIsComment; }
    void isComment(boolean value) { mIsComment = value; }

    /*override*/ /*DmlNvHolder*/int getHolder() { return DmlNvHolder.CONTENT_PART; }

    private boolean mIsComment = true;
}

