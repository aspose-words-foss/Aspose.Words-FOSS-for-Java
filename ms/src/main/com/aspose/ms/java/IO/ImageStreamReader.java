package com.aspose.ms.java.IO;

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.Stream;

import java.io.EOFException;
import java.nio.ByteOrder;

public class ImageStreamReader
{
    public ImageStreamReader(ByteOrder byteOrder, Stream stream)
    {
        mStream = stream;
        if (byteOrder == ByteOrder.BIG_ENDIAN)
            mBigEndianBinaryReader = new BigEndianBinaryReader(stream);
        else
            mBinaryReader = new BinaryReader(stream);
    }

    public long readUnsignedInt() throws Exception
    {
        long result;
        if (mBigEndianBinaryReader != null)
            result = mBigEndianBinaryReader.readInt32();
        else
            result = mBinaryReader.readInt32();
        return result & 0xffffffffL;
    }

    public int readUnsignedShort() throws Exception
    {
        int result;
        if (mBigEndianBinaryReader != null)
            result = mBigEndianBinaryReader.readInt16();
        else
            result = mBinaryReader.readInt16();
        return result & 0xffff;
    }

    public void readFully(byte[] b) throws Exception { readFully(b, 0, b.length); }

    public void readFully(byte[] b, int off, int len) throws Exception
    {
        if (off < 0 || len < 0 || off + len > b.length || off + len < 0)
            throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > b.length!");

        while (len > 0)
        {
            int nbytes = read(b, off, len);
            if (nbytes == -1)
                throw new EOFException();

            off += nbytes;
            len -= nbytes;
        }
    }

    public short readShort() throws Exception
    {
        short result;
        if (mBigEndianBinaryReader != null)
            result = mBigEndianBinaryReader.readInt16();
        else
            result = mBinaryReader.readInt16();
        return result;
    }

    public int readInt() throws Exception
    {
        int result;
        if (mBigEndianBinaryReader != null)
            result = mBigEndianBinaryReader.readInt32();
        else
            result = mBinaryReader.readInt32();
        return result;
    }

    public float readFloat() throws Exception
    {
        float result;
        if (mBigEndianBinaryReader != null)
            result = Float.intBitsToFloat(BitUtil.swapInt32(mBinaryReader.readInt32()));

        else
            result = mBinaryReader.readSingle();
        return result;
    }

    public double readDouble() throws Exception
    {
        double result;
        if (mBigEndianBinaryReader != null)
            result = Double.longBitsToDouble(BitUtil.swapInt64(mBinaryReader.readInt64()));
        else
            result = mBinaryReader.readDouble();
        return result;
    }

    public int read(byte[] b, int off, int len) throws Exception
    {
        int result;
        if (mBigEndianBinaryReader != null)
            result = mBigEndianBinaryReader.read(b, off, len);
        else
            result = mBinaryReader.read(b, off, len);
        return result;
    }

    public long getStreamPosition() throws Exception
    {
        return mStream.getPosition();
    }

    public void seek(long startPos) throws Exception
    {
        mStream.setPosition(startPos);
    }

    public void skipBytes(int i) throws Exception
    {
        mStream.setPosition(mStream.getPosition() + i);
    }

    private BinaryReader mBinaryReader;
    private BigEndianBinaryReader mBigEndianBinaryReader;
    private Stream mStream;
}
