package com.aspose.ms.System.IO;

import com.aspose.ms.System.Text.Encoding;

import java.io.EOFException;
import java.io.IOException;

/**
 * User: romeok
 * Date: Jul 28, 2005
 *
 * Copycat of .NET BinaryReader.
 * Reads primitive data types as binary values.
 *
 * Note that only methods used by Aspose.Words are implemented.
 */
public class BinaryReader
{
	// Java: Utf8 is default encoding in .Net BinaryReader.
	public BinaryReader(Stream stream)
	{
		assert stream != null;
		mStream = stream;
		mBuffer = new byte[16]; //Enough to read simple data types.
		mEncoding = Encoding.getUTF8();
	}

	//@todo 2 sk in many cases we use ctor with Encoding and don't ReadChars
	//@todo 2 sk in some cases we use ctor with (or without) Encoding and read encoded chars and strings by another (system-wide) methods - check this for performance.
	//Java: use this ctor when needed read (and decode) of chars (method ReadChars).
	public BinaryReader(Stream stream, Encoding encoding)
	{
		this(stream);
		assert encoding != null;
		mEncoding = encoding;
	}

	public Stream getBaseStream()
	{
		return mStream;
	}

	public void close() throws Exception
	{
		mStream.close();
	}

	public int read(byte[] buffer, int index, int count) throws Exception
	{
		return mStream.read(buffer, index, count);
	}

	public byte[] readBytes(int count) throws Exception
	{
		if (count < 0) throw new IllegalArgumentException("count");

		byte[] result = new byte[count];
		int numRead = 0;
		do
		{
			int n = mStream.read(result, numRead, count);
			if (n == 0)
				break;

			numRead += n;
			count -= n;
		} while (count > 0);

		if (numRead != result.length)
		{
			// Trim array. This could happen on EOF & possibly net streams.
			byte[] copy = new byte[numRead];
			System.arraycopy(result, 0, copy, 0, numRead);
			result = copy;
		}

		return result;
	}

	public byte readByte() throws Exception
	{
		return readSByte();
	}

	//Java's byte is signed, so .Net sbyte == java byte.
	public byte readSByte() throws Exception
	{
		fillBuffer(1);
		return mBuffer[0];
	}

	public short readInt16() throws Exception
	{
		fillBuffer(2);
		//Java: Here and everywhere else use " & 0xFF" so when a negative byte value is extended
		//it does not have 0xFFFF... at the front of it.
		return (short)((mBuffer[0] & 0xFF) | ((mBuffer[1] & 0xFF) << 8));
	}

    public boolean readBoolean() throws Exception
    {
        fillBuffer(1);
        return (mBuffer[0] != 0);
    }

	//Note the result type is upscaled and masked to avoid negative values.
	public short readUInt16() throws Exception
	{
		return readInt16();
	}

	public int readInt32() throws Exception
	{
		fillBuffer(4);
		return ((mBuffer[0] & 0xFF) | ((mBuffer[1] & 0xFF) << 8) |
				((mBuffer[2] & 0xFF) << 16) | ((mBuffer[3] & 0xFF) << 24));
	}

	// Note: since java lacks unsigned, the result can be (arithmetically wrong) negative value.
	public int readUInt32() throws Exception
	{
		return readInt32();
	}

	public long readInt64() throws Exception
	{
		long lo = readUInt32() & 0xFFFFFFFFL;
		long hi = readUInt32() & 0xFFFFFFFFL;
		return (hi << 32) | lo;
	}

	public long readUInt64() throws Exception
	{
		return readInt64();//returns the same bits, anyway java hasn't ulong.
	}

	/**
	 * Also analog for com.sun.media.jai.codec.readFloatLE() (used by com.aspose.metafiles).
	 */
	public float readSingle() throws Exception
	{
		return Float.intBitsToFloat(readInt32());
	}

	public double readDouble() throws Exception
	{
		return Double.longBitsToDouble(readInt64());
	}

	public char readChar() throws Exception
	{
		char[] chars = readChars(1);
		assert chars.length == 1;
	    return chars[0];
	}

	//java-added for reading chars in different encodings
	public char[] readChars(int count, Encoding encoding) throws Exception
	{
		assert encoding != null;

		Encoding main = mEncoding;
		mEncoding = encoding;

		char[] chars = readChars(count);
		mEncoding = main;
		return chars;
	}

	public char[] readChars(int count) throws Exception
	{
		if (count < 0) throw new IllegalArgumentException("count");

		if (count == 0)
			return new char[0];//empty char[]

		char[] chars = new char[count];
		//we use the worst-case size of the Byte buffer, so we can read the bytes only once.
		int maxBytes = mEncoding.getMaxByteCount(count);
		byte[] bytes = new byte[maxBytes];

		// returns zero if end of stream reached.
		int readBytes = mStream.read(bytes, 0, maxBytes);

		//decode bytes into chars
		int consumedBytes = mEncoding.getChars(bytes, chars);

		//if we consumed not all bytes - rewind the Stream position.
		int diff = consumedBytes - readBytes;
		if (diff != 0)
			mStream.seek(diff, SeekOrigin.CURRENT);

		return chars;
	}

	//Fills the internal buffer.
	private void fillBuffer(int numBytes) throws Exception
	{
		if ((numBytes <= 0) || (numBytes > mBuffer.length))
			throw new IllegalArgumentException("numBytes");

		int bytesRead = 0;
		do
		{
			int n = mStream.read(mBuffer, bytesRead, numBytes - bytesRead);
			if (n == 0)
				throw new EOFException("End of file reached.");

			bytesRead += n;
		} while (bytesRead < numBytes);
	}

	private Stream mStream;
	private byte[] mBuffer;

	//java-added for Encoding functionality
	private Encoding mEncoding;
}
