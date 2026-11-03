// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 25/06/2010 by Roman Korchagin

package com.aspose;

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.DateTimeKind;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.IO.NetInputStream;
import com.aspose.ms.java.IO.JavaOnlyStreamUtil;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

abstract class ZipReaderBase implements Closeable
{
    abstract public boolean moveToNextEntry() throws Exception;

    abstract public String getEntryFileName();

    abstract public int getEntryCompressionMethod();

    abstract public int getEntryLength();

    abstract public DateTime getEntryLastModified();

    /// <summary>
    /// Extract content of the current zip entry into a new memory stream.
    /// The returned stream's position is set to 0.
    /// </summary>
    public MemoryStream loadEntryToMemory() throws Exception
    {
        MemoryStream result = new MemoryStream();
        extractEntryToStream(result);
        result.setPosition(0);
        return result;
    }

    /// <summary>
    /// Extract content of the current zip entry into a byte array.
    /// </summary>
    public byte[] loadEntryToByteArray() throws Exception
    {
        return StreamUtil.copyStreamToByteArray(loadEntryToMemory());
    }

    /// <summary>
    /// Extract compressed content of the current zip entry into a new memory stream.
    /// The returned stream should decompress entry while reading the stream.
    /// </summary>
    public MemoryStream loadCompressedEntryToMemory() throws Exception
    {
        // We realised the Java version of ZipStream in another commit,
        // but Net code very often reopens closed stream and change position.
        return loadEntryToMemory();
    }

    /// <summary>
    /// Extract content of the current zip entry into the specified stream.
    /// </summary>
    abstract public void extractEntryToStream(Stream stream) throws Exception;

    public void dispose() throws IOException
    {
        close();
    }

    public abstract void close() throws IOException;
}

/// <summary>
/// This class abstracts the ZIP library.
/// 
/// Note this class is disposable only for autoporting to Java. This class does not close the stream!
/// </summary>
public final class ZipReaderPal extends ZipReaderBase
{
    public ZipReaderPal(Stream stream) throws Exception
    {
        this(stream, null);
    }

    public ZipReaderPal(Stream stream, String password) throws Exception
    {
        // FIX WORDSJAVA-976: Explaining message in exception for unsupported encrypted ODT documents.
        // gpv: Unfortunately, ZipInputStream has a well-known bug "only DEFLATED entries can have EXT descriptor"
        // romeok has already asked about this issue http://stackoverflow.com/questions/4498554/how-to-unzip-an-encrypted-odt-opendocument-in-java
        // There's is a workaround http://stackoverflow.com/questions/17638505/android-studio-get-error-java-util-zip-zipexception-only-deflated-entries-ca
        // Replacing file works fine, but it doesn't solve our problem.

        // We can use ZipFile instead of ZipInputStream. ZipFile quickly calls into native code to work with a file.
        // It is a bad idea to create a temp file and work with a file instead of a stream,
        // but I couldn't find a better solution.

        // WORDSJAVA-1952: we need ZipInputStream for password-encrypted zips.
        boolean useZipInputStream = (password != null);
        // WORDSJAVA-1332: optimization.
        if (!(stream instanceof FileStream))
        {
            // Extremely slow
            long pos = stream.getPosition();
            useZipInputStream = tryZipInputStream(stream);
            stream.setPosition(pos);
        }

        // WORDSJAVA-1952: we need ZipInputStream for password-encrypted zips.
        if (password != null)
            stream = new PasswordEncryptedZipInputStream(stream, password).toStream();

        mZipReader = (useZipInputStream) ? new ZipInputStreamReader(stream) : new ZipFileReader(stream);
    }

    public ZipReaderPal(String fileName) throws IOException
    {
        mZipReader = new ZipFileReader(fileName);
    }

    public ZipReaderPal(String fileName, String password) throws Exception
    {
        // create stream for reading encrypted zip file
        mZipReader = new ZipFileReader(new PasswordEncryptedZipInputStream(new FileInputStream(fileName), password).toStream());
    }

    private boolean tryZipInputStream(Stream stream) throws Exception
    {
        try
        {
            final int FILE_LIMIT = 12*1024*1024;    // 12 Mb

            MemoryStream buffer = new MemoryStream();
            ZipInputStream zis = new ZipInputStream(new NetInputStream(stream));
            ZipEntry mZipEntry = null;

            while ((mZipEntry = zis.getNextEntry()) != null)
            {
                // WORDSJAVA-1332, optimization. We usually read a whole file inside the zip, so we can do the same here
                // TODO: it's better to remove this method (tryZipInputStream) at all
                int size = (int)mZipEntry.getSize();

                // Skip big files
                if (size > 0 && size < FILE_LIMIT)
                {
                    buffer.setPosition(0);
                    JavaOnlyStreamUtil.copyStream(zis, buffer, size);
                }
            }
        }
        catch (ZipException ze)
        {
            return false;
        }

        return true;
    }

    public boolean moveToNextEntry() throws Exception
    {
        return mZipReader.moveToNextEntry();
    }

    public String getEntryFileName()
    {
        return mZipReader.getEntryFileName();
    }

    public int getEntryCompressionMethod()
    {
        return mZipReader.getEntryCompressionMethod();
    }

    public DateTime getEntryLastModified()
    {
        return mZipReader.getEntryLastModified();
    }

    public static byte[] getFileHeaderSignature() { return G_FILE_HEADER_SIGNATURE; }

    /// <summary>
    /// Extract content of the current zip entry into the specified stream.
    /// </summary>
    public void extractEntryToStream(Stream stream) throws Exception
    {
        mZipReader.extractEntryToStream(stream);
    }

    public int getEntryLength() {
        int lenght = mZipReader.getEntryLength();
        return lenght > 0 ? lenght : 0;
    }

    /// <summary>
    /// Loads compressed content of the current zip entry into a new byte array.
    /// Stores it as a special compressed data chunk which is marked by a header.
    /// </summary>
    public byte[] loadCompressedEntryToByteArray() throws Exception
    {
        if (isCompressedEntry())
        {
            byte[] compressedBytes = ZipUtilPal.deflate(loadEntryToByteArray(), ZipMethod.DEFLATE);
            return CompressedData.create(compressedBytes, getEntryLength(), isCompressedEntry());
        }
        else
        {
            return CompressedData.create(loadEntryToByteArray(), getEntryLength(), false);
        }
    }

    /// <summary>
    /// Creates a new memory stream which is based on the compressed stream.
    /// The returned stream should decompress data while reading the stream.
    /// </summary>
    static MemoryStream loadCompressedEntryToMemory(Stream inputStream) throws Exception
    {
        try
        {
            BinaryReader reader = new BinaryReader(inputStream);
            int length = (int)reader.readInt64();
            boolean isCompressed = reader.readBoolean();
            int compressedSize = reader.readInt32();

            if (isCompressed)
                return new MemoryStream(ZipUtilPal.inflate(reader.readBytes(compressedSize), length, ZipMethod.DEFLATE));
            else
                return new MemoryStream(reader.readBytes(length));
        }
        catch (Exception ex1)
        {
            if (ex1 instanceof ZipException)
                // wrap the original exception and throw
                throw new ZipException("Cannot create ZipStream from byte array");
            else
                throw ex1;
        }
    }

    /// <summary>
    /// Returns true if current entry stream is compressed.
    /// </summary>
    private boolean isCompressedEntry() { return (getEntryCompressionMethod()== 0x08); }

    public void close() throws IOException
    {
        mZipReader.close();
    }

    private ZipReaderBase mZipReader;
    private static /*final*/ byte[] G_FILE_HEADER_SIGNATURE = new byte[] { (byte) 80, (byte) 75, (byte) 3, (byte) 4 };
    private static final long MB = 1 << 20;
}

class ZipInputStreamReader extends ZipReaderBase
{
    ZipInputStreamReader(Stream stream) throws Exception
    {
        mZipStream = new ZipInputStream(new NetInputStream(stream));
    }

    public boolean moveToNextEntry() throws Exception
    {
        mZipEntry = mZipStream.getNextEntry();
        return (mZipEntry != null);
    }

    public String getEntryFileName() { return mZipEntry.getName(); }

    public int getEntryCompressionMethod() { return mZipEntry.getMethod(); }

    public DateTime getEntryLastModified()
    {
        return new DateTime(mZipEntry.getTime(), DateTimeKind.LOCAL);
    }

    /// <summary>
    /// Extract content of the current zip entry into the specified stream.
    /// </summary>
    public void extractEntryToStream(Stream stream) throws Exception
    {
        JavaOnlyStreamUtil.copyStream(mZipStream, stream, (int)mZipEntry.getSize());
    }

    public int getEntryLength()
    {
        return (int)mZipEntry.getSize();
    }

    public void close() throws IOException
    {
        // Do nothing, closing mZipStream causes exceptions.
    }

    private ZipInputStream mZipStream;
    private ZipEntry mZipEntry;
}

class ZipFileReader extends ZipReaderBase
{
    ZipFileReader(Stream stream) throws Exception
    {
        if (stream instanceof FileStream)
        {
            // Do not copy the file if we already use it
            mFile = new File(((FileStream)stream).getFileName());
            mFileTemporary = false;
        }
        else
        {
            // create a temp file
            String fileName = Guid.newGuid().toString();
            mFile = File.createTempFile(fileName, ".zip");
            mFileTemporary = true;

            // write to file
            FileOutputStream fos = new FileOutputStream(mFile);
            JavaOnlyStreamUtil.copyStream(stream, fos);
            fos.close();
        }

        // now we can create an instance of ZipFile
        mZipFile = new ZipFile(mFile);
        mEntries = mZipFile.entries();
    }

    ZipFileReader(String fileName) throws IOException
    {
        // Do not copy the file if we already use it
        mFile = new File(fileName);
        mFileTemporary = false;

        // now we can create an instance of ZipFile
        mZipFile = new ZipFile(mFile);
        mEntries = mZipFile.entries();
    }

    public boolean moveToNextEntry()
    {
        boolean hasNext = mEntries.hasMoreElements();
        if (hasNext)
            mZipEntry = mEntries.nextElement();
        return hasNext;
    }

    public String getEntryFileName() { return mZipEntry.getName(); }

    public int getEntryCompressionMethod() { return mZipEntry.getMethod(); }

    public DateTime getEntryLastModified()
    {
        return new DateTime(mZipEntry.getTime(), DateTimeKind.LOCAL);
    }
    /// <summary>
    /// Extract content of the current zip entry into the specified stream.
    /// </summary>
    public void extractEntryToStream(Stream stream) throws Exception
    {
        InputStream is = mZipFile.getInputStream(mZipEntry);
        JavaOnlyStreamUtil.copyStream(is, stream, (int)mZipEntry.getSize());
        is.close();
    }

    public int getEntryLength()
    {
        return (int)mZipEntry.getSize();
    }

    public void close() throws IOException
    {
        // As long as some stream on the file is open, it is locked (at least on the windows-implementation of the JVM).
        // So it cannot be deleted.
        mZipFile.close();

        if (mFileTemporary)
            mFile.delete();
    }

    private File mFile;
    private ZipFile mZipFile;
    private Enumeration<? extends ZipEntry> mEntries;
    private ZipEntry mZipEntry;
    private boolean mFileTemporary;
}

/**
 * WORDSJAVA-1952
 * This code was taken from http://blog.alutam.com/2009/10/31/reading-password-protected-zip-files-in-java/
 * Only the “Traditional PKWARE Encryption” is supported (spec. section VII)
 * Files that have the “compressed length” information at the end of the data section (rather than at the beginning)
 * are not supported (see “general purpose bit flag”, bit 3 in section V, subsection J in the spec.)
 */
class PasswordEncryptedZipInputStream extends InputStream
{
    private static final int[] CORRECT_SIGNATURE = {0x50, 0x4b, 0x03, 0x04};
    private static final int HEADER_SIZE = 12;
    private static final int[] CRC_TABLE = new int[256];

    static
    {
        // init CRC table
        for (int i = 0; i < 256; i++)
        {
            int r = i;
            for (int j = 0; j < 8; j++)
            {
                if ((r & 1) == 1)
                {
                    r = (r >>> 1) ^ 0xedb88320;
                }
                else
                {
                    r >>>= 1;
                }
            }
            CRC_TABLE[i] = r;
        }
    }

    private InputStream input;
    private String password;
    private int skipTransform = 0;
    private int compressedSize = 0;
    private int position = 0;
    private int value = 0;
    private int valueInc;
    private FilePart currentPart = FilePart.SIGNATURE;
    private int[] keys = new int[3];

    PasswordEncryptedZipInputStream(Stream stream, String password)
    {
        this(Stream.toJavaInputStream(stream), password);
    }

    PasswordEncryptedZipInputStream(InputStream input, String password)
    {
        this.input = input;
        this.password = password;
    }

    Stream toStream()
    {
        return Stream.fromJavaInputStream(this);
    }

    @Override
    public int read() throws IOException
    {
        int b = input.read();
        if (skipTransform == 0)
        {
            return transform(b);
        }
        else
        {
            skipTransform--;
            return b;
        }
    }

    @Override
    public void close() throws IOException
    {
        input.close();
        super.close();
    }

    private int transform(int b) throws IOException
    {
        switch (currentPart)
        {
            case SIGNATURE:
                // check signature
                if (b == CORRECT_SIGNATURE[position])
                {
                    position++;
                    if (position >= CORRECT_SIGNATURE.length)
                    {
                        skipTransform = 2;
                        currentPart = FilePart.FLAGS;
                    }
                }
                else
                {
                    currentPart = FilePart.TAIL;
                }
                return b;
            case FLAGS:
                // check flags
                if ((b & 0x01) == 0)
                {
                    throw new RuntimeException("Zip file not password protected.");
                }
                if ((b & 0x08) != 0)
                {
                    throw new RuntimeException("Unsupported zip format.");
                }
                if ((b & 0x40) != 0)
                {
                    throw new RuntimeException("Strong encryption used in zip file.");
                }
                valueInc = HEADER_SIZE;
                position = 0;
                skipTransform = 11;
                currentPart = FilePart.COMPRESSED_SIZE;
                compressedSize = 0;
                return (b - 1);
            case COMPRESSED_SIZE:
                // calculate compressed size
                compressedSize += (b << (8 * position));
                b -= valueInc;
                if (b < 0)
                {
                    valueInc = 1;
                    b += 256;
                }
                else
                {
                    valueInc = 0;
                }
                position++;
                if (position > 3)
                {
                    position = 0;
                    value = 0;
                    skipTransform = 4;
                    currentPart = FilePart.FILE_NAME_LENGTH;
                }
                return b;
            case FILE_NAME_LENGTH:
            case EF_SIZE:
                value += b << (8 * position);
                if (position == 1)
                {
                    position = 0;
                    if (currentPart == FilePart.FILE_NAME_LENGTH)
                    {
                        currentPart = FilePart.EF_SIZE;
                    }
                    else
                    {
                        currentPart = FilePart.HEADER;
                        skipTransform = value;
                    }
                }
                else
                {
                    position = 1;
                }
                return b;
            case HEADER:
                // calculate keys
                initKeys();
                for (int i = 0; i < HEADER_SIZE; i++)
                {
                    updateKeys((byte) (b ^ decrypt()));
                    b = input.read();
                }
                compressedSize -= HEADER_SIZE;
                currentPart = FilePart.DATA;
                // no break
            case DATA:
                // decrypt data
                b = (b ^ decrypt()) & 0xff;
                updateKeys((byte) b);
                compressedSize--;
                if (compressedSize == 0)
                {
                    position = 0;
                    currentPart = FilePart.SIGNATURE;
                }
                // no break
            case TAIL:
                return b;
        }
        // we shouldn't be here
        throw new RuntimeException("Decrypting file error");
    }

    private void initKeys()
    {
        keys[0] = 305419896;
        keys[1] = 591751049;
        keys[2] = 878082192;

        for (int i = 0; i < password.length(); i++)
        {
            updateKeys((byte) (password.charAt(i) & 0xff));
        }
    }

    private void updateKeys(byte b)
    {
        keys[0] = crc(keys[0], b);
        keys[1] = (keys[1] + (keys[0] & 0xff)) * 134775813 + 1;
        keys[2] = crc(keys[2], (byte) (keys[1] >> 24));
    }

    private int crc(int oldCrc, byte b)
    {
        return ((oldCrc >>> 8) ^ CRC_TABLE[(oldCrc ^ b) & 0xff]);
    }

    private byte decrypt()
    {
        int k = keys[2] | 2;
        return (byte) ((k * (k ^ 1)) >>> 8);
    }

    // parts of encrypted zip file
    enum FilePart
    {
        SIGNATURE, FLAGS, COMPRESSED_SIZE, FILE_NAME_LENGTH, EF_SIZE, HEADER, DATA, TAIL
    }
}