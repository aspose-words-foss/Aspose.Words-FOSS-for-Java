// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 28/07/2010 by Roman Korchagin

package com.aspose;

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.IO.JavaOnlyStreamUtil;
import com.aspose.ms.java.IO.NetOutputStream;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.attribute.FileTime;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/// <summary>
/// Implement this class in Java manually.
///
/// This class abstracts the ZIP library.
/// </summary>
public final class ZipWriterPal implements Closeable
{

    public ZipWriterPal(Stream dstStream)
    {
        this(dstStream, DEFAULT_COMPRESSION_LEVEL, DEFAULT_ZIP_64_MODE);
    }

    /// <summary>
    /// Creates a new ZipWriterPal object with a specific destination stream and compression level.
    /// </summary>
    public ZipWriterPal(Stream dstStream, int compressionLevel, int zip64)
    {
        mZipStream = new ZipOutputStream(new NetOutputStream(dstStream));
        mZipStream.setLevel(compressionLevel);
    }

    public void dispose() throws IOException
    {
        close();
    }

    public void close() throws IOException
    {
        // Do nothing, closing mZipStream causes exceptions.
    }

    /// <summary>
    /// Adds a new entry to the zip archive.
    /// The caller needs to keep the entry stream open until your call <see cref="Finish"/>.
    /// The caller is responsible for disposing the entry stream after that.
    /// </summary>
    public void addEntry(String fileName, Stream srcStream) throws Exception
    {
        addEntry(fileName, srcStream, DateTime.MinValue);
    }

    /// <summary>
    /// Adds a new entry to the zip archive.
    /// If useEntryDateTime is true, then entryDateTime will be used for the LastModified property of the entry.
    /// </summary>
    void addEntry(String fileName, Stream srcStream, DateTime entryDateTime) throws Exception
    {
        //JAVA-changed: java-specific logic to create zip entry
        //mZipFile.AddEntry(fileName, null, srcStream);
        ZipEntry entry = new ZipEntry(fileName);

        // This condition means that entryDateTime wasn't set.
        if (entryDateTime== null || DateTime.MinValue.equals(entryDateTime))
            entry.setTime(DateTimeUtil.getNow().toJava().getTime());
        else
            entry.setTime(entryDateTime.toUniversalTime().getTicks());

        mZipStream.putNextEntry(entry);
        JavaOnlyStreamUtil.copyStream(srcStream, mZipStream);
        mZipStream.closeEntry();
    }

    /// <summary>
    /// Adds a stored (uncompressed) entry to the zip acrhive.
    /// </summary>
    public void addStoredEntry(String fileName, byte[] data) throws Exception
    {
        //JAVA-changed: java-specific logic to create a stored entry
        // ZipEntry entry = mZipFile.AddEntry(fileName, null, data);
        // entry.CompressionMethod = 0x00; // No compression.
        ZipEntry entry = new ZipEntry(fileName);
        entry.setMethod(ZipEntry.STORED);
        entry.setSize(data.length);
        entry.setCompressedSize(data.length);
        entry.setTime(DateTimeUtil.getNow().toJava().getTime());

        // Using Java CRC32 because our own autoported does not seem to work yet.
        java.util.zip.CRC32 crc = new CRC32();
        //WORDSJAVA-2723 Fix Compatibility Issues with Java SE 11 and Later
        //crc.update(ByteBuffer.wrap(data)); // not compile in jdk16
        crc.update(data);
        entry.setCrc(crc.getValue());

        mZipStream.putNextEntry(entry);
        mZipStream.write(data);
        mZipStream.closeEntry();
    }

    /// <summary>
    /// Finalizes writing of the zip archive.
    /// </summary>
    public void finish() throws Exception
    {
        mZipStream.finish();
        // WORDSJAVA-1619 In order to MemoryToJavaOutputStream work ZipOutputStream needs to be
        // flushed after finish() method.
        // IntelJ Idea underlines this method by mistake because ZipOutputStream extends
        // DeflaterOutputStream which in its turn extends FilterOutputStream that has flush.
        mZipStream.flush();
    }

    static final /*ZipCompressionLevel*/int DEFAULT_COMPRESSION_LEVEL = 5;
    static final /*Zip64Option*/int DEFAULT_ZIP_64_MODE = Zip64Option.AS_NECESSARY;

    //JAVA-deleted: not needed in java.
    /// <summary>
    /// Although <see cref="ZipFile"/> is IDisposable, it is not responsible for any resources in our case hence we don't dispose it.
    /// </summary>
    // private readonly ZipFile mZipFile;
    private ZipOutputStream mZipStream;
}
