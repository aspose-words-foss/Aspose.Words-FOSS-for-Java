// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/08/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;

/// <summary>
/// This object is returned to the caller after a document is saved and contains additional information that 
/// has been generated or calculated during the save operation. The caller can use or ignore this object.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/save-a-document/">Save a Document</a> documentation article.</para>
/// </summary>
public class SaveOutputParameters
{
    SaveOutputParameters(String contentType)
    {
        setContentType(contentType);
    }

    void setContentType(String contentType)
    {
        ArgumentUtil.checkHasChars(contentType, "contentType");
        mContentType = contentType;
    }

    /// <summary>
    /// Returns the Content-Type string (Internet Media Type) that identifies the type of the saved document.
    /// </summary>
    public String getContentType() { return mContentType; }

    /// <summary>
    /// Here is a table with content types of some file formats:
    /// http://www.utoronto.ca/web/HTMLdocs/Book/Book-3ed/appb/mimetype.html
    /// </summary>
    private String mContentType;
}

