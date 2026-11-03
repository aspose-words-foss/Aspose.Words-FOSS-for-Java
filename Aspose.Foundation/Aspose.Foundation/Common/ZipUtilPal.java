// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2006 by Roman Korchagin
package com.aspose;

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.IO.NetInputStream;
import com.aspose.ms.java.IO.JavaOnlyStreamUtil;
import com.aspose.ms.java.IO.NetOutputStream;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.*;


/// <summary>
/// Utility functions that simplify work with the zip library.
/// </summary>
public /*static*/ class ZipUtilPal
{
    /* Simulation of static class by using private constructor */
    private ZipUtilPal()
    {}

    /// <summary>
    /// Checks a stream to see if it contains a valid zip archive.
    /// </summary>
    /// <param name="stream">The stream to check.</param>
    /// <returns>true if the stream contains a valid zip archive.</returns>
    public static boolean isZipFile(Stream stream)
    {
        try
        {
            if (!stream.canRead())
                return false;

            ZipReaderPal zipFile = new ZipReaderPal(stream);
            return zipFile.moveToNextEntry();
        }
        catch(Exception e)
        {
            // in all situation then we catch exception this is not zip file
            return false;
        }
    }

    /// <summary>
    /// Decompresses data that was compressed using the Deflate or Zlib compression.
    /// </summary>
    /// <param name="srcBytes">The compressed data.</param>
    /// <param name="uncompressedLength">The size of the data when decompressed. If you don't know, just pass zero.</param>
    /// <param name="method">Deflation method that was used to deflate the data
    /// supposed to be inflated. One of the <see cref="ZipMethodPal"/></param>
    /// <returns>Decompressed data.</returns>
    public static byte[] inflate(byte[] srcBytes, int uncompressedLength, /*ZipMethodPal*/int method) throws Exception
    {
        MemoryStream srcStream = new MemoryStream(srcBytes);
        try /*JAVA: was using*/
        {
            return inflate(srcStream, uncompressedLength, method);
        }
        finally { if (srcStream != null) srcStream.close(); }
    }

    /// <summary>
    /// Decompresses data that was compressed using the Deflate or Zlib compression.
    /// </summary>
    /// <param name="srcStream">The compressed data.</param>
    /// <param name="uncompressedLength">The size of the data when decompressed. If you don't know, just pass zero.</param>
    /// <param name="method">Deflation method that was used to deflate the data
    /// supposed to be inflated. One of the <see cref="ZipMethodPal"/></param>
    /// <returns>Decompressed data.</returns>
    public static byte[] inflate(Stream srcStream, int uncompressedLength, /*ZipMethodPal*/int method) throws Exception
    {
        try
        {
            return inflateCore(srcStream, uncompressedLength, method);
        }
        catch (EOFException e)
        {
            //JAVA-added:
            // RK Model\Shape\Textbox\TestTextboxWithImage.wml loading one zipped EMF in this document causes
            // an exception in the Java ZIP library. I think ZIP is correct, the problem is in the Java ZIP library.
            // This is a workaround to resolve the problem. If loading ZIP failed wit that EOF problem, then
            // create a source stream with just one extra zero byte at the end and uncompressing it works.
            MemoryStream tempSrcStream = new MemoryStream((int)srcStream.getLength() + 1);
            srcStream.setPosition(0);
            StreamUtil.copyStream(srcStream, tempSrcStream);
            tempSrcStream.writeByte((byte)0);   // This adds the extra zero that helps unzip to succeed.
            tempSrcStream.setPosition(0);
            return inflateCore(tempSrcStream, uncompressedLength, method);
        }
    }

    //JAVA-added: was added to reuse the code because of Java ZIP library behaviour. See above the catch block
    private static byte[] inflateCore(Stream srcStream, int uncompressedLength, /*ZipMethodPal*/int method) throws Exception
    {
        if (uncompressedLength == 0)
            uncompressedLength = (int)srcStream.getLength();
        MemoryStream dstStream = new MemoryStream(uncompressedLength);

        InflaterInputStream inflaterStream = createInflaterStream(method, srcStream);
        try //JAVA: was using
        {
            JavaOnlyStreamUtil.copyStream(inflaterStream, dstStream);
        }
        finally
        {
            // In .NET we dispose the inflater/deflater stream and specify an option not to close the underlying stream,
            // but in Java there is no such option and we must not let the underlying stream to be closed.
            //
            // So in .NET we dispose inflater stream here, but in Java we must do nothing here.
        }

        return dstStream.toArray();
    }

    /// <summary>
    /// Compresses a byte array using the Deflate or Zlib compression.
    /// </summary>
    /// <param name="srcBytes">The original data.</param>
    /// <param name="method">Deflation method to be used. One of the <see cref="ZipMethodPal"/></param>
    /// <returns>The compressed data.</returns>
    public static byte[] deflate(byte[] srcBytes, /*ZipMethodPal*/int method) throws Exception
    {
        MemoryStream srcStream = new MemoryStream(srcBytes);
        try /*JAVA: was using*/
        {
            return deflate(srcStream, method);
        }
        finally { if (srcStream != null) srcStream.close();}
    }

    /// <summary>
    /// Compresses a stream using the Deflate, Zlib or GZip compression.
    /// Suitable, for example, for compressing EMF and WMF to WMZ.
    /// </summary>
    /// <param name="srcStream">The original data.</param>
    /// <param name="method">Deflation method to be used. One of the <see cref="ZipMethodPal"/></param>
    /// <returns>The compressed data.</returns>
    public static byte[] deflate(Stream srcStream, /*ZipMethodPal*/int method) throws Exception
    {
        MemoryStream dstStream = new MemoryStream();
        try /*JAVA: was using*/
        {
            deflate(srcStream, dstStream, method);
            return dstStream.toArray();
        }
        finally { if (dstStream != null) dstStream.close(); }
    }

    /// <summary>
    /// Compresses a stream into a memory stream. 
    /// The destination memory stream can be at any position.
    /// </summary>
    /// <param name="srcStream">The original data.</param>
    /// <param name="dstStream">Where the compressed is written. Written at the current position.</param>
    /// <param name="method">Deflation method to be used. One of the <see cref="ZipMethodPal"/></param>
    /// <returns>The length of the compressed data.</returns>
    public static int deflate(Stream srcStream, MemoryStream dstStream, /*ZipMethodPal*/int method) throws Exception
    {
        int startPos = (int)dstStream.getPosition();

        DeflaterOutputStream deflaterStream = createDeflaterStream(method, dstStream);
        try /*JAVA: was using*/
        {
            JavaOnlyStreamUtil.copyStream(srcStream, deflaterStream);
        }
        finally
        {
            // In .NET we dispose the inflater/deflater stream and specify an option not to close the underlying stream,
            // but in Java there is no such option and we must not let the underlying stream to be closed. 
            deflaterStream.finish();
        }

        return (int)dstStream.getPosition() - startPos;
    }

    public static void deflate(byte[] bytes, int offset, int count, Stream dstStream, /*ZipMethodPal*/int method) throws Exception
    {
	    //J459: convert document to pdf takes about 1 hr.
	    //Input array generated form the test document is 107M bytes size.
	    //With default buffer size 512 bytes the time is about 1 hr.
	    //When i changed buffer size to 4096 bytes the time is lowered to 5.5 min.
	    //With the last setting of buffer size (only one pass for deflater) the time is 21 sec and
	    //profiler shows Deflater.deflate() method on 8 place with only 2% of cpu time usage (instead of 98%).
	    int bufSize = count < 10485760 ? count : count / 3;

        Deflater deflater = new Deflater();
        deflater.setInput(bytes, offset, count);
        deflater.finish();

        byte[] compressedBuf = new byte[bufSize];
        while (true)
        {
            int compressedLen = deflater.deflate(compressedBuf, 0, compressedBuf.length);
            if (compressedLen > 0)
                dstStream.write(compressedBuf, 0, compressedLen);
            else
                break;
        }
    }

    //JAVA-deleted: this method was split up to 2 methods, createDeflaterStream and createInflaterStream.
    // private static Stream createInflaterOrDeflaterStream(/*ZipMethodPal*/int method, Stream stream, CompressionMode compressionMode)
    // {
    //     switch (method)
    //     {
    //         case ZipMethodPal.DEFLATE:
    //             return new DeflateStream(stream, compressionMode, true);
    //         case ZipMethodPal.ZLIB:
    //             return new ZlibStream(stream, compressionMode, true);
    //         default:
    //             throw new IllegalStateException("Unknown compression method specified.");
    //     }
    // }

    //JAVA-added: to create deflate stream
    private static DeflaterOutputStream createDeflaterStream(/*ZipMethodPal*/int method, Stream stream) throws Exception
    {
        switch (method)
        {
            case ZipMethod.DEFLATE:
                // Deflate is the "raw" algorithm. ZLIB uses Deflate and wraps it with a header and trailer.
                return new DeflaterOutputStream(new NetOutputStream(stream), new Deflater(Deflater.DEFAULT_COMPRESSION, true));
            case ZipMethod.ZLIB:
                return new DeflaterOutputStream(new NetOutputStream(stream), new Deflater(Deflater.DEFAULT_COMPRESSION, false));
            default:
                throw new Exception("Unknown compression method specified.");
        }
    }

    //JAVA-added: to create inflate stream
    private static InflaterInputStream createInflaterStream(/*ZipMethodPal*/int method, Stream stream) throws Exception
    {
        switch (method)
        {
            case ZipMethod.DEFLATE:
                return new InflaterInputStream(new NetInputStream(stream), new Inflater(true));
            case ZipMethod.ZLIB:
                return new InflaterInputStream(new NetInputStream(stream), new Inflater(false));
            default:
                throw new Exception("Unknown compression method specified.");
        }
    }
}
