// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/09/2016 by Ivan Lyagin
// 
package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.File;


/// <summary>
/// Provides document file streams to perform a GOLD comparison test.
/// </summary>
class FileComparisonStreamProvider implements IComparisonStreamProvider
{
    /// <summary>
    /// Creates a new instance of the class.
    /// </summary>
    FileComparisonStreamProvider(String fileNameOut, String fileNameGold, String fileNameOriginalGold, String fileNameMS)
    {
        mFileNameOut = fileNameOut;
        mFileNameGold = fileNameGold;
        mFileNameOriginalGold = fileNameOriginalGold;
        mFileNameMS = fileNameMS;
    }

    public Stream /*IComparisonStreamProvider.*/getStreamOut() throws Exception
    {
        return getFileStream(mFileNameOut);
    }

    public Stream /*IComparisonStreamProvider.*/getStreamGold() throws Exception
    {
        return getFileStream(mFileNameGold);
    }

    public Stream /*IComparisonStreamProvider.*/getStreamOriginalGold() throws Exception
    {
        return getFileStream(mFileNameOriginalGold);
    }

    public Stream /*IComparisonStreamProvider.*/getStreamMS() throws Exception
    {
        return getFileStream(mFileNameMS);
    }

    private static Stream getFileStream(String fileName) throws Exception
    {
        return File.exists(fileName) ? File.openRead(fileName) : null;
    }

    private /*final*/ String mFileNameOut;
    private /*final*/ String mFileNameGold;
    private /*final*/ String mFileNameOriginalGold;
    private /*final*/ String mFileNameMS;
}

