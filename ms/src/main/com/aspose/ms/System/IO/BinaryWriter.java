package com.aspose.ms.System.IO;

import com.aspose.ms.System.Text.Encoding;

import java.io.IOException;

/**
 * User: romeok
 * Date: Jul 28, 2005
 *
 * Copycat of .NET BinaryWriter.
 * Writes primitive data types as binary values.
 *
 * Note that only methods used by Aspose.Words are implemented.
 *
 * Note JAVA-changed method names of Write() overloads:
 * 1. In purpoe to distinguish in java Write(UInt16), Write(Int32), etc overloads:
 * we get in above primer a method signature conflict since both will be translated as write(int).
 *
 * 2. Another thing. In AW for clarity and maintainability always used 'writer.Write((Int32)i)'
 * or 'writer.Write((int)i)' -- i.e. desired Write() overload pointed explicitly.
 * Let's do the same in java by using different method names instead of explicit pointing to overloads of Write().
 */
public class BinaryWriter
{
	//Unicode is a default Encoding.
	public BinaryWriter(Stream stream)
	{
		mStream = stream;
		mBuffer = new byte[16]; //Enough to write primitive data types.
	}

	public BinaryWriter(Stream stream, Encoding encoding)
	{
		this(stream);
		mEncoding = encoding;
	}

	public Stream getBaseStream()
	{
		return mStream;
	}

	public void close() throws IOException
	{
		mStream.close();
	}

	public void flush() throws Exception
	{
		mStream.flush();
	}

    public long seek(int offset, /*SeekOrigin*/ int origin) throws Exception
    {
        return mStream.seek(offset, origin);
    }

	//Write(byte[] buffer, int index, int count)
	public void writeBytes(byte[] buffer, int offset, int count) throws Exception
	{
		mStream.write(buffer, offset, count);
	}

	//Write(byte[] buffer)
	public void writeBytes(byte[] buffer) throws Exception
	{
		mStream.write(buffer, 0, buffer.length);
	}

	//Write(sbyte value), Write(byte value)
	public void writeByte(byte value) throws Exception
	{
		mBuffer[0] = value;
		mStream.write(mBuffer, 0, 1);
	}

	//Write(byte value)
	//used only for manual porting of an unsigned byte used in arithmetic operations.
	//java: sinse C# byte is unsigned, we read it into short, so we need in method that write it back
	//We don't need in '& 0xFF' since "A narrowing conversion of a signed integer to an integral type T
	//simply discards all but the n lowest order bits." (Java Lang Spec).
	public void writeByte(int value) throws Exception
	{
		mBuffer[0] = (byte)value;
		mStream.write(mBuffer, 0, 1);
	}

	//Write(bool value)
	public void writeBoolean(boolean value) throws Exception
	{
		mBuffer[0] = (byte)(value ? 1 :0);
		mStream.write(mBuffer, 0, 1);
	}

	//Write(short value)
	public void writeInt16(short value) throws Exception
	{
		mBuffer[0] = (byte) value;
		mBuffer[1] = (byte) (value >>> 8);
		mStream.write(mBuffer, 0, 2);
	}

	//Write(ushort value)
	public void writeUInt16(/*ushort*/short value) throws Exception
	{
		writeInt16(value);
	}

	//Write(int value)
	public void writeInt32(int value) throws Exception
	{
		mBuffer[0] = (byte) value;
		mBuffer[1] = (byte) (value >>> 8);
		mBuffer[2] = (byte) (value >>> 16);
		mBuffer[3] = (byte) (value >>> 24);
		mStream.write(mBuffer, 0, 4);
	}

	//Write(uint value)
	public void writeUInt32(/*uint*/int value) throws Exception
	{
		writeInt32(value);
	}

	//Write(long value)
	public void writeInt64(long value) throws Exception
	{
		writeUInt32((int) value);
		writeUInt32((int) (value >>> 32));
	}

	//Write(ulong value)
	public void writeUInt64(long value) throws Exception
	{
		writeUInt32((int) value);
		writeUInt32((int) (value >>> 32));
	}

	//Write(float value)
	public void writeFloat(float value) throws Exception
	{
		writeInt32(Float.floatToIntBits(value));
	}

	//Write(double value)
	public void writeDouble(double value) throws Exception
	{
		writeInt64(Double.doubleToLongBits(value));
	}

	//Write(char[] chars)
	public void writeChars(char[] chars) throws Exception
	{
		if (chars == null || chars.length == 0)
			return;//empty char[]

		byte[] bytes = mEncoding.getBytes(chars);
		writeBytes(bytes);
	}

	private Stream mStream;
	private byte[] mBuffer;    // temp space for writing primitives to.

	//java-added for Encoding functionality
	//Unicode is a default Encoding if another doesn't set in a ctor.
	private Encoding mEncoding = Encoding.getUnicode();
}
