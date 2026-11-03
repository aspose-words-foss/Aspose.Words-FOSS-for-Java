// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/03/2008 by Viktor Sazhaev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// One part of document subsidiary content.
/// </summary>
abstract class SubsidiaryContentPart
{
    protected SubsidiaryContentPart(Encoding textEncoding, String contentType, String uri)
    {
        mTextEncoding = textEncoding;
        mContentType = contentType;
        mUri = uri;
    }

    /// <summary>
    /// Creates a stream to access content. Should be disposed.
    /// </summary>
    abstract MemoryStream createStream() throws Exception;

    /// <summary>
    /// Indicates whether subsidiary part should be output as text.
    /// Is is assumed that for any text part encoding is known.
    /// </summary>
    boolean isText() { return mTextEncoding != null; }

    /// <summary>
    /// Returns text encoding. Applicable for text parts.
    /// </summary>
    Encoding getTextEncoding() { return mTextEncoding; }

    /// <summary>
    /// Returns content type
    /// </summary>
    String getContentType() { return mContentType; }

    /// <summary>
    /// Returns URI
    /// </summary>
    String getUri() { return mUri; }

    private /*final*/ Encoding mTextEncoding;
    private /*final*/ String mContentType;
    private /*final*/ String mUri;
}

