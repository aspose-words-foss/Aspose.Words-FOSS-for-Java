// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/11/2010 by Alexey Morozov
package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.FileAccess;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.File;
import java.util.ArrayList;


public class TempFiler
{
    /// <summary>
    /// Holds information about temporary files were created.
    /// It is internal class I think it's better to place it here.
    /// </summary>
    private static class TempFile
    {
        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="stream"></param>
        /// <param name="filename"></param>
        TempFile(FileStream stream, String filename)
        {
            mStream = stream;
            mFilename = filename;
        }

        public FileStream getStream() { return mStream; }

        public String getFilename() { return mFilename; }

        private /*final*/ FileStream mStream;
        private /*final*/ String mFilename;
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="saveInfo"></param>
    public TempFiler(String tempFolder)
    {
        mTempFolder = tempFolder;
    }

    /// <summary>
    /// Creates either MemoryStream or FileStream depending on <see cref="SaveOptions.TempFolder"/> or
    /// <see cref="LoadOptions.TempFolder"/> setting. 
    /// Returned stream will be used as temporary stream when reading or writing doc.
    /// </summary>
    public Stream getNewStream() throws Exception
    {
        Stream stream;

        if (isTempFilesEnabled())
        {
            // Temporary file is required for stream.
            String fileName = Path.combine(mTempFolder, msString.format("{0}.tmp", Guid.newGuid()));
            stream = new FileStream(fileName, FileMode.CREATE, FileAccess.READ_WRITE);
            msArrayList.add(mTempFiles, new TempFile((FileStream)stream, fileName));
        }
        else
        {
            // Temporary files are not required so create in memory stream.
            stream = new MemoryStream();
        }

        return stream;
    }

    /// <summary>
    /// Delete temporary files if they were created.
    /// </summary>
    /// <remarks>
    /// I thought about Finalize but decided that better if we can control 
    /// deletetion of temporary files itself.
    /// </remarks>
    public void cleanup() throws Exception
    {
        for (TempFile tempFile : (Iterable<TempFile>) mTempFiles)
        {
            tempFile.getStream().close();
            File.delete(tempFile.getFilename());
        }
    }

    /// <summary>
    /// Indicates that TempFiler will return FileStream object instead of MemoryStream.
    /// </summary>
    public boolean isTempFilesEnabled() { return StringUtil.hasChars(mTempFolder); }

    /// <summary>
    /// Holds temporary files created to able to delete it.
    /// </summary>
    private /*final*/ ArrayList<TempFiler.TempFile> mTempFiles = new ArrayList<TempFiler.TempFile>();
    private /*final*/ String mTempFolder;
}

