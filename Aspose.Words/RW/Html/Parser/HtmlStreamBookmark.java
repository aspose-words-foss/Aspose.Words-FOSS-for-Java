// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// A bookmarked position in the <see cref="HtmlStream"/>. 
/// </summary>
/// <remarks>
/// The position is used by <see cref="HtmlTokenizer"/> to reparse characters in case of an error.
/// </remarks>
class HtmlStreamBookmark
{
    HtmlStreamBookmark(HtmlStream stream, int position)
    {
        mStream = stream;
        mPosition = position;
    }

    HtmlStream getStream() { return mStream; }

    int getPosition() { return mPosition; }

    private /*final*/ HtmlStream mStream;

    private /*final*/ int mPosition;
}

