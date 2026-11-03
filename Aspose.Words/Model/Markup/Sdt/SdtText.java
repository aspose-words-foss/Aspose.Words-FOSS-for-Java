// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/06/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents plain/rich text SDT. See Iso29500, part 1, chapter 17.5.2.44 
/// text (Plain Text Structured Document Tag)
/// </summary>
class SdtText extends SdtControlProperties
{
    /// <summary>
    /// ctor.
    /// </summary>
    /// <param name="isRichText">if false, then this text control only allows plaintext, otherwise allows richtext.</param>
    SdtText(boolean isRichText)
    {
        mIsRichtext = isRichText;
    }

    /// <summary> Allow Soft Line Breaks. Specifies whether soft line breaks can be added to 
    /// the contents of this structured document tag when this document is modified. 
    /// </summary>
    /// <remarks>This setting shall not affect the ability of the structured document tag to display 
    /// existing soft line breaks (which shall be preserved) and shall only affect the ability to add 
    /// line breaks when the document is modified by an application.</remarks>
    boolean isMultiline() { return mIsMultiline; }
    void isMultiline(boolean value) { mIsMultiline = value; }

    /*override*/ /*SdtType*/int getType() { return (mIsRichtext)? SdtType.RICH_TEXT : SdtType.PLAIN_TEXT; }

    private boolean mIsMultiline;
    private /*final*/ boolean mIsRichtext;
}

