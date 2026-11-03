// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/01/2020 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class FieldFormattingResult
{
    FieldFormattingResult(RichString text, boolean preserveRichFormatting)
    {
        mText = text;
        mPreserveRichFormatting = preserveRichFormatting;
    }

    FieldFormattingResult(String text)
    {
    	this(RichString.createFromString(text), false);
	
    }

    RichString getText() { return mText; };

    private RichString mText;
    boolean getPreserveRichFormatting() { return mPreserveRichFormatting; };

    private boolean mPreserveRichFormatting;
}

