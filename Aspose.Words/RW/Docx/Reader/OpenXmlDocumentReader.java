// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/01/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.Debug;


class OpenXmlDocumentReader implements IDocumentReader
{
    OpenXmlDocumentReader(Stream stream, LoadOptions loadOptions, FileFormatInfo fileFormatInfo, Document document)
    {
        // alexnosk: Do not assert if fileFormatInfo is null, this means use specified LoadFormat explicitly without detection.
        // If code fails to read file it will fallback to file format detection. 
        Debug.assertTrue(loadOptions != null);
        Debug.assertTrue(stream != null);
        Debug.assertTrue(document != null);
        mLoadOptions = loadOptions;
        mStream = stream;
        mFileFormatInfo = fileFormatInfo;
        mDocument = document;
    }

    public void read() throws Exception
    {
        Debug.assertTrue ((mFileFormatInfo == null) || !mFileFormatInfo.isEncrypted());

        mDocument.setComplianceInfo(new OoxmlComplianceInfo());
        DocxReader reader = new DocxReader(mStream, mDocument, mLoadOptions);
        reader.read();
    }

    public boolean isEncrypted() { return (mFileFormatInfo != null) && mFileFormatInfo.isEncrypted(); }

    public Stream decrypt() throws Exception
    {
        return DigitalSignatureUtil.decryptFileSystem(mFileFormatInfo.getFileSystem(), mLoadOptions.getPassword());
    }

    private /*final*/ LoadOptions mLoadOptions;
    private /*final*/ Stream mStream;
    private /*final*/ FileFormatInfo mFileFormatInfo;
    private /*final*/ Document mDocument;
}

