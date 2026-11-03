// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/03/2020 by Alexander Sevidov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.BinaryReader;


/// <summary>
/// This class implements working with compressed data.
/// </summary>
/// <remarks>
/// This is first step to handle EMF images compressed.
/// Currently it solves WORDSNET-18977 and gives significant performance boost.
/// </remarks>
public /*static*/ class CompressedData
{
	/* Simulation of static class by using private constructor */
	private CompressedData()
	{}

    /// <summary>
    /// Creates data chunk marked as compressed.
    /// </summary>
    /// <remarks>
    /// Adds a required for decompression header to the beginning of the chunk.
    /// </remarks>
    public static byte[] create(byte[] compressedData, long length, boolean isCompressed) throws Exception
    {
        final int ZIP_ENTRY_HEADER_LEN = 13;

        int headerSize = COMPRESSED_DATA_SIGNATURE.length() + ZIP_ENTRY_HEADER_LEN;

        MemoryStream stream = new MemoryStream(headerSize + compressedData.length);
        try /*JAVA: was using*/
        {
            BinaryWriter writer = new BinaryWriter(stream);
            try /*JAVA: was using*/
            {
                writer.writeBytes(Encoding.getASCII().getBytes(COMPRESSED_DATA_SIGNATURE));
                writer.writeInt64(length);
                writer.writeBoolean(isCompressed);
                writer.writeInt32(compressedData.length);
                writer.writeBytes(compressedData);

                return stream.toArray();
            }
            finally { if (writer != null) writer.close(); }
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Returns decompressed bytes if compressed, or returns bytes itself otherwise.
    /// </summary>
    public static byte[] getData(byte[] bytes)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	if (bytes == null)
            	return null;

        	MemoryStream stream = new MemoryStream(bytes);
        	try /*JAVA: was using*/
        	{
            	if (!isCompressedData(stream))
                	return bytes;
        	}
        	finally { if (stream != null) stream.close(); }

        	MemoryStream stream1 = new MemoryStream(bytes,
            	COMPRESSED_DATA_SIGNATURE.length(), bytes.length - COMPRESSED_DATA_SIGNATURE.length());
        	try /*JAVA: was using*/
        	{
            	MemoryStream zipStream = ZipReaderPal.loadCompressedEntryToMemory(stream1);
            	try /*JAVA: was using*/
            	{
                	return zipStream.toArray();
            	}
            	finally { if (zipStream != null) zipStream.close(); }
        	}
        	finally { if (stream1 != null) stream1.close(); }
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// If compressed returns <see cref="ZipStream"/> which allows reading decompressed data 
    /// or returns <paramref name="stream"/> itself otherwise.
    /// </summary>
    public static Stream getStream(Stream stream) throws Exception
    {
        if (!isCompressedData(stream))
            return stream;

        // Otherwise create ZipStream to extract data runtime.
        long savedPos = stream.getPosition();

        stream.setPosition(stream.getPosition() + COMPRESSED_DATA_SIGNATURE.length());
        MemoryStream zipStream = ZipReaderPal.loadCompressedEntryToMemory(stream);

        stream.setPosition(savedPos);

        return zipStream;
    }

    /// <summary>
    /// Returns true when source stream is compressed, false otherwise.
    /// </summary>
    private static boolean isCompressedData(Stream stream) throws Exception
    {
        BinaryReader reader = new BinaryReader(stream);

        if (!StreamUtil.hasEnoughBytesToRead(reader, COMPRESSED_DATA_SIGNATURE.length()))
            return false;

        long savedPos = stream.getPosition();
        String header = Encoding.getASCII().getString(reader.readBytes(COMPRESSED_DATA_SIGNATURE.length()));
        stream.setPosition(savedPos);

        return COMPRESSED_DATA_SIGNATURE.equals(header);
    }

    private static final String COMPRESSED_DATA_SIGNATURE = "{60b5c89b-4108-45a6-8493-673a7dd2ea68}";
}

