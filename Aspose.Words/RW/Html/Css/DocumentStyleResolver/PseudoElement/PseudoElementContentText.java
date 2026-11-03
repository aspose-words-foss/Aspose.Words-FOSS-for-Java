// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/11/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents a text string, which is a souce of generated content for pseudo-elements.
/// </summary>
class PseudoElementContentText extends PseudoElementContentPart
{
    PseudoElementContentText(String text)
    {
        Debug.assertTrue(text != null);
        mText = text;
    }

    /*override*/ void accept(IPseudoElementContentPartVisitor visitor)
    {
        visitor.visitText(mText);
    }


    private /*final*/ String mText;
}

