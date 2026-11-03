package com.aspose.ms.java.IO;

import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.logging.Log;

import java.io.IOException;
import java.nio.ByteOrder;

public class ImageStreamWriter
{
    public ImageStreamWriter(ByteOrder byteOrder, Stream stream) throws Exception
    {
        mByteOrder = byteOrder;
        if (byteOrder == ByteOrder.BIG_ENDIAN)
            mBigEndianWriter = new BigEndianBinaryWriter(stream);
        else
            mWriter = new BinaryWriter(stream);

        mIsBigEndian = byteOrder == ByteOrder.BIG_ENDIAN;
        mStream = stream;
    }

    public void writeBytes(byte[] val) throws Exception
    {
        if (mIsBigEndian)
            mBigEndianWriter.writeBytes(val);
        else
            mWriter.writeBytes(val);
    }

    public void writeByte(int val) throws Exception
    {
        if (mIsBigEndian)
            mBigEndianWriter.writeByte((byte) val);
        else
            mWriter.writeByte((byte) val);
    }

    public void writeInt(int val) throws Exception
    {
        if (mIsBigEndian)
            mBigEndianWriter.writeInt32(val);
        else
            mWriter.writeInt32(val);
    }

    public void writeShort(int size) throws Exception
    {
        if (mIsBigEndian)
            mBigEndianWriter.writeInt16(size);
        else
            mWriter.writeInt16((short) size);
    }

    public int getPosition() throws Exception
    {
        return (int) mStream.getPosition();
    }

    public void setPosition(long i) throws Exception
    {
        mStream.setPosition(i);
    }

    public void seek(long position) throws Exception
    {
        mStream.setPosition(position);
    }

    public void writeChars(char[] c, int off, int len) throws Exception
    {
        // Fix 4430357 - if off + len < 0, overflow occurred
        if (off < 0 || len < 0 || off + len > c.length || off + len < 0)
            throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > c.length!");

        byte[] b = new byte[len * 2];
        int boff = 0;
        if (mByteOrder == ByteOrder.BIG_ENDIAN)
        {
            for (int i = 0; i < len; i++)
            {
                char v = c[off + i];
                b[boff++] = (byte) (v >>> 8);
                b[boff++] = (byte) (v >>> 0);
            }
        }
        else
        {
            for (int i = 0; i < len; i++)
            {
                char v = c[off + i];
                b[boff++] = (byte) (v >>> 0);
                b[boff++] = (byte) (v >>> 8);
            }
        }

        mStream.write(b, 0, len * 2);
    }

    public void write(byte[] b, int off, int numBytes) throws IOException
    {
        try
        {
            mStream.write(b, off, numBytes);
        }
        catch (Exception e)
        {
            throw new IOException(e);
        }
    }

    public void writeShorts(short[] s, int off, int len) throws Exception
    {
        // Fix 4430357 - if off + len < 0, overflow occurred
        if (off < 0 || len < 0 || off + len > s.length || off + len < 0)
            throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > s.length!");

        byte[] b = new byte[len * 2];
        int boff = 0;
        if (mByteOrder == ByteOrder.BIG_ENDIAN)
        {
            for (int i = 0; i < len; i++)
            {
                short v = s[off + i];
                b[boff++] = (byte) (v >>> 8);
                b[boff++] = (byte) (v >>> 0);
            }
        }
        else
        {
            for (int i = 0; i < len; i++)
            {
                short v = s[off + i];
                b[boff++] = (byte) (v >>> 0);
                b[boff++] = (byte) (v >>> 8);
            }
        }

        mStream.write(b, 0, len * 2);
    }

    public void writeInts(int[] i, int off, int len) throws Exception
    {
        // Fix 4430357 - if off + len < 0, overflow occurred
        if (off < 0 || len < 0 || off + len > i.length || off + len < 0)
            throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > i.length!");

        byte[] b = new byte[len * 4];
        int boff = 0;
        if (mByteOrder == ByteOrder.BIG_ENDIAN)
        {
            for (int j = 0; j < len; j++)
            {
                int v = i[off + j];
                b[boff++] = (byte) (v >>> 24);
                b[boff++] = (byte) (v >>> 16);
                b[boff++] = (byte) (v >>> 8);
                b[boff++] = (byte) (v >>> 0);
            }
        }
        else
        {
            for (int j = 0; j < len; j++)
            {
                int v = i[off + j];
                b[boff++] = (byte) (v >>> 0);
                b[boff++] = (byte) (v >>> 8);
                b[boff++] = (byte) (v >>> 16);
                b[boff++] = (byte) (v >>> 24);
            }
        }

        mStream.write(b, 0, len * 4);
    }

    public void writeFloats(float[] f, int off, int len) throws Exception
    {
        // Fix 4430357 - if off + len < 0, overflow occurred
        if (off < 0 || len < 0 || off + len > f.length || off + len < 0)
            throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > f.length!");

        byte[] b = new byte[len * 4];
        int boff = 0;
        if (mByteOrder == ByteOrder.BIG_ENDIAN)
        {
            for (int i = 0; i < len; i++)
            {
                int v = Float.floatToIntBits(f[off + i]);
                b[boff++] = (byte) (v >>> 24);
                b[boff++] = (byte) (v >>> 16);
                b[boff++] = (byte) (v >>> 8);
                b[boff++] = (byte) (v >>> 0);
            }
        }
        else
        {
            for (int i = 0; i < len; i++)
            {
                int v = Float.floatToIntBits(f[off + i]);
                b[boff++] = (byte) (v >>> 0);
                b[boff++] = (byte) (v >>> 8);
                b[boff++] = (byte) (v >>> 16);
                b[boff++] = (byte) (v >>> 24);
            }
        }

        mStream.write(b, 0, len * 4);
    }

    public void writeDoubles(double[] d, int off, int len) throws Exception
    {
        // Fix 4430357 - if off + len < 0, overflow occurred
        if (off < 0 || len < 0 || off + len > d.length || off + len < 0)
            throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > d.length!");

        byte[] b = new byte[len * 8];
        int boff = 0;
        if (mByteOrder == ByteOrder.BIG_ENDIAN)
        {
            for (int i = 0; i < len; i++)
            {
                long v = Double.doubleToLongBits(d[off + i]);
                b[boff++] = (byte) (v >>> 56);
                b[boff++] = (byte) (v >>> 48);
                b[boff++] = (byte) (v >>> 40);
                b[boff++] = (byte) (v >>> 32);
                b[boff++] = (byte) (v >>> 24);
                b[boff++] = (byte) (v >>> 16);
                b[boff++] = (byte) (v >>> 8);
                b[boff++] = (byte) (v >>> 0);
            }
        }
        else
        {
            for (int i = 0; i < len; i++)
            {
                long v = Double.doubleToLongBits(d[off + i]);
                b[boff++] = (byte) (v >>> 0);
                b[boff++] = (byte) (v >>> 8);
                b[boff++] = (byte) (v >>> 16);
                b[boff++] = (byte) (v >>> 24);
                b[boff++] = (byte) (v >>> 32);
                b[boff++] = (byte) (v >>> 40);
                b[boff++] = (byte) (v >>> 48);
                b[boff++] = (byte) (v >>> 56);
            }
        }

        mStream.write(b, 0, len * 8);
    }

    public void skipBytes(int offset) throws Exception
    {
        mStream.setPosition(mStream.getPosition() + offset);
    }

    public Stream getBaseStream()
    {
        return mStream;
    }

    public void flush()
    {
        try
        {
            mStream.flush();
        }
        catch (Exception e)
        {
            Log.error(e);
        }
    }

    protected final ByteOrder mByteOrder;
    protected final Stream mStream;
    private final boolean mIsBigEndian;
    protected BigEndianBinaryWriter mBigEndianWriter;
    protected BinaryWriter mWriter;
}
