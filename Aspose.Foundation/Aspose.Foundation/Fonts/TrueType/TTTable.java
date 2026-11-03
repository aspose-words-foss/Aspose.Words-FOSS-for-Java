// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/06/2009 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;


public abstract class TTTable
{
    /// <summary>
    /// All concrete classes need to implement this.
    /// </summary>
    abstract void write(BigEndianBinaryWriter writer) throws Exception;

    /// <summary>
    /// Writes this table to a new memory stream.
    /// </summary>
    MemoryStream toMemoryStream() throws Exception
    {
        MemoryStream stream = new MemoryStream();
        BigEndianBinaryWriter writer = new BigEndianBinaryWriter(stream);
        write(writer);
        return stream;
    }

    /// <summary>
    /// Writes this table to a byte array.
    /// </summary>
    byte[] toByteArray() throws Exception
    {
        MemoryStream stream = toMemoryStream();
        try /*JAVA: was using*/
        {
            return stream.toArray();
        }
        finally { if (stream != null) stream.close(); }
    }
}

