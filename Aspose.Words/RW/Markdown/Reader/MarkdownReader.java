// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.IO.Stream;
import com.aspose.Debug;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.StreamReader;


/// <summary>
/// Imports markdown file into a Document object.
/// </summary>
class MarkdownReader implements IDocumentReader
{
    /// <summary>
    /// Ctor.
    /// </summary>
    MarkdownReader(Stream stream, LoadOptions loadOptions, FileFormatInfo fileFormatInfo, Document document) throws Exception
    {
        Debug.assertTrue(stream != null);
        Debug.assertTrue(document != null);

        Encoding encoding = TxtReader.getEncoding(loadOptions, fileFormatInfo);
        mStreamReader = new StreamReader(stream, encoding);

        mLoadOptions = ms.as(loadOptions, MarkdownLoadOptions.class);
        if (mLoadOptions == null)
            mLoadOptions = new MarkdownLoadOptions(loadOptions);

        mContext = new MarkdownReaderContext(document);
        mDocument = document;
    }

    /// <summary>
    /// Reads markdown file into the model.
    /// </summary>
    public void read() throws Exception
    {
        MarkdownDocument markdownDocument = new MarkdownDocument(mLoadOptions);
        LoadingProgressProcessor progressProcessor = new LoadingProgressProcessor(mDocument, mLoadOptions);

        markdownDocument.read(mStreamReader, progressProcessor);
        markdownDocument.write(mContext);
    }

    //>>>>>>>> #region  IDocumentReader implementation

    public Stream decrypt()
    {
        Debug.assertTrue(false, "Not supported");
        return null;
    }

    public boolean isEncrypted() { return false; }

    //<<<<<<<< #endregion 

    private /*final*/ StreamReader mStreamReader;
    private /*final*/ MarkdownReaderContext mContext;
    private /*final*/ Document mDocument;
    private /*final*/ MarkdownLoadOptions mLoadOptions;
}

