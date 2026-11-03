// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/io/memorystream.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.IO;

import com.aspose.ms.System.msArray;

import java.io.IOException;

/**
 * User: romeok
 * Date: Jul 27, 2005
 *
 * This is a direct port of MemoryStream.cs from Rotor, for more info see MemoryStream documentation in MSDN.
 *
 * Purpose: A Stream whose backing store is memory.  Great for temporary storage without creating a temp file.
 * Also lets users expose a byte[] as a stream.
 */
public class MemoryStream extends Stream //java: all public members changed to package-private
{
    private byte[] _buffer;    // Either allocated internally or externally.
    private int _origin;       // For user-provided arrays, start at this origin
    private int _position;     // read/write head.
    private int _length;       // Number of bytes within the memory stream
    private int _capacity;     // length of usable portion of buffer for stream
    // Note that _capacity == _buffer.Length for non-user-provided byte[]'s

    private boolean _expandable;  // User-provided buffers aren't expandable.
    private boolean _writable;    // Can user write to this stream?
    private boolean _exposable;   // Whether the array can be returned to the user.
    private boolean _isOpen;      // Is this stream open or closed?

    private static final int MEM_STREAM_MAX_LENGTH = Integer.MAX_VALUE;

    //Initializes a new resizable stream with capacity initialized to zero.
    public MemoryStream()
    {
        this(0);
    }

    //Initializes a new resizable stream with capacity initialized as specified.
    public MemoryStream(int capacity)
    {
        if (capacity < 0)
            throw new IllegalArgumentException("capacity");

        _buffer = new byte[capacity];
        _capacity = capacity;
        _expandable = true;
        _writable = true;
        _exposable = true;
        _origin = 0;      // Must be 0 for byte[]'s created by MemoryStream
        _isOpen = true;
    }

    //Initializes a new non-resizable stream based on the specified byte array.
    public MemoryStream(byte[] buffer)
    {
        this(buffer, true);
    }

    //Initializes a new non-resizable stream based on the specified byte array with the CanWrite property set as specified.
    public MemoryStream(byte[] buffer, boolean writable)
    {
        if (buffer == null)
            throw new NullPointerException("buffer");

        _buffer = buffer;
        _length = _capacity = buffer.length;
        _writable = writable;
        _exposable = false;
        _origin = 0;
        _isOpen = true;
    }

    //Initializes a new non-resizable stream based on the specified region (index) of a byte array.
    public MemoryStream(byte[] buffer, int index, int count)
    {
        this(buffer, index, count, true, false);
    }

    //Initializes a new non-resizable stream based on the specified region of a byte array,
    // with the CanWrite property set as specified.
    public MemoryStream(byte[] buffer, int index, int count, boolean writable)
    {
        this(buffer, index, count, writable, false);
    }

    //Initializes a new non-resizable stream based on the specified region of a byte array,
    // with the CanWrite property set as specified, and the ability to call GetBuffer set as specified.
    public MemoryStream(byte[] buffer, int index, int count, boolean writable, boolean publiclyVisible)
    {
        if (buffer == null)
            throw new NullPointerException("buffer");
        if (index < 0)
            throw new IllegalArgumentException("index");
        if (count < 0)
            throw new IllegalArgumentException("count");
        if (buffer.length - index < count)
            throw new IllegalArgumentException("The buffer length minus index is less than count.");

        _buffer = buffer;
        _origin = _position = index;
        _length = _capacity = index + count;
        _writable = writable;
        _exposable = publiclyVisible;
        _expandable = false;
        _isOpen = true;
    }

    public boolean canRead()
    {
        return _isOpen;
    }

	public boolean canSeek()
	{
		return _isOpen;
	}

	public boolean canWrite()
	{
		return _writable;
	}

	public void dispose()
	{
		close();
	}

    public void close()
    {
        _isOpen = false;
        _writable = false;
        _expandable = false;
        // Don't set buffer to null - allow GetBuffer & ToArray to work.
    }

    //JAVA-added to reopen streams closed by system/third-party libraries.
    public void reopen()
    {
        _isOpen = true;
        _writable = true;
        _expandable = true;
    }

    public void flush() throws IOException
    {
        // Do nothing.
    }

    // returns a bool saying whether we allocated a new array.
    private boolean ensureCapacity(int value) throws IOException
    {
        // Check for overflow
        if (value < 0)
            throw new IllegalArgumentException("value");

        if (value > _capacity)
        {
            int newCapacity = value;

            if (newCapacity < 256)
                newCapacity = 256;

            if (newCapacity < _capacity << 1)
                newCapacity = _capacity << 1;

            capacity(newCapacity);
            return true;
        }
        return false;
    }

    public byte[] getBuffer()
    {
        if (!_exposable)
            throw new UnsupportedOperationException("UnauthorizedAccess_MemStreamBuffer");

        return _buffer;
    }

    public int capacity() throws IOException
    {
        if (!_isOpen)
            errorStreamIsClosed();

        return _capacity - _origin;
    }

    public void capacity(int capacity) throws IOException
    {
        if (!_isOpen)
            errorStreamIsClosed();

        if (capacity != _capacity)
        {
            if (!_expandable)
                throw new  UnsupportedOperationException("This stream is not expandable.");
            if (capacity < _length)
                throw new IllegalArgumentException("capacity");

            if (capacity > 0)
            {
                byte[] newBuffer = new byte[capacity];
                if (_length > 0)
					System.arraycopy(_buffer, 0, newBuffer, 0, _length);
                _buffer = newBuffer;
            }
            else
            {
                _buffer = null;
            }
            _capacity = capacity;
        }
    }

    public long getLength() throws IOException
    {
        if (!_isOpen)
            errorStreamIsClosed();

        return _length - _origin;
    }

    public long getPosition() throws IOException
    {
        if (!_isOpen)
            errorStreamIsClosed();

        return _position - _origin;
    }

    public void setPosition(long length) throws IOException
    {
        if (!_isOpen)
            errorStreamIsClosed();
        if (length < 0)
            throw new IllegalArgumentException("length = " + length);
        if (length > MEM_STREAM_MAX_LENGTH)
            throw new IllegalArgumentException("length = " + length);

        _position = _origin + (int)length;
    }

	public long seek(long offset, /*SeekOrigin*/ int origin) throws IOException
	{
		switch (origin)
		{
			case SeekOrigin.BEGIN:
					setPosition(offset);
					break;
			case SeekOrigin.CURRENT:
					setPosition(getPosition() + offset);
					break;
			case SeekOrigin.END:
					setPosition(getLength() + offset);
					break;
			default:
				throw new IllegalStateException("Unknown SeekOrigin type.");
		}
		assert _position >= 0;
		return _position;
	}

	public int read(byte[] buffer, int offset, int count) throws IOException
    {
        if (!_isOpen)
            errorStreamIsClosed();
        if (buffer == null)
            throw new NullPointerException("buffer");
        if (offset < 0)
            throw new IllegalArgumentException("offset");
        if (count < 0)
            throw new IllegalArgumentException("count");
        if (buffer.length - offset < count)
            throw new IllegalArgumentException("The buffer length minus index is less than count.");

          int n = _length - _position;
          if (n > count) n = count;
          if (n <= 0)
              return 0;

          assert _position + n >= 0 : "_position + n >= 0";  // len is less than 2^31 -1.

          if (n <= 8)
          {
              int byteCount = n;
              while (--byteCount >= 0)
                  buffer[offset + byteCount] = _buffer[_position + byteCount];
          }
          else
          {
			  System.arraycopy(_buffer, _position, buffer, offset, n);
          }

          _position += n;
          return n;
    }


	public int readByte() throws IOException
	{
		if (!_isOpen)
			errorStreamIsClosed();

		if (_position >= _length)
			return -1;

		return _buffer[_position++] & 0xFF;//JAVA-added to exclude sign extension.
	}

	// Sets the length of the stream to a given value.  The new
    // value must be nonnegative and less than the space remaining in
    // the array, Int32.MaxValue - origin
    // Origin is 0 in all cases other than a MemoryStream created on
    // top of an existing array and a specific starting offset was passed
    // into the MemoryStream constructor.  The upper bounds prevents any
    // situations where a stream may be created on top of an array then
    // the stream is made longer than the maximum possible length of the
    // array (Int32.MaxValue).
    public void setLength(long value) throws IOException
    {
          if (!_writable)
              errorWriteNotSupported();
          if (value > MEM_STREAM_MAX_LENGTH)
              throw new IllegalArgumentException("value");
          if (value < 0 || value > (MEM_STREAM_MAX_LENGTH - _origin))
              throw new IllegalArgumentException("value");

          int newLength = _origin + (int)value;
          boolean allocatedNewArray = ensureCapacity(newLength);
          if (!allocatedNewArray && newLength > _length)
            msArray.fill(_buffer, _length, newLength - _length, (byte)0);

          _length = newLength;
          if (_position > newLength)
              _position = newLength;
      }

    public byte[] toArray()
    {
        // BCLDebug.Perf(_exposable, "MemoryStream::GetBuffer will let you avoid a copy.");
        byte[] copy = new byte[_length - _origin];
        System.arraycopy(_buffer, _origin, copy, 0, _length - _origin);
        return copy;
    }

    public void write(byte[] buffer, int offset, int count) throws IOException
    {
        if (!_isOpen)
            errorStreamIsClosed();
        if (!_writable)
            errorWriteNotSupported();
        if (buffer == null)
            throw new NullPointerException("buffer");
        if (offset < 0)
            throw new IllegalArgumentException("offset");
        if (count < 0)
            throw new IllegalArgumentException("count");
        if (buffer.length - offset < count)
            throw new IllegalArgumentException("Argument_InvalidOffLen");

        int i = _position + count;
        // Check for overflow
        if (i < 0)
            throw new IOException("StreamTooLong");

        if (i > _length)
        {
            boolean mustZero = _position > _length;
            if (i > _capacity)
            {
                boolean allocatedNewArray = ensureCapacity(i);
                if (allocatedNewArray)
                    mustZero = false;
            }
            if (mustZero)
                msArray.fill(_buffer, _length, i - _length, (byte)0);

            _length = i;
        }

        if (count <= 8)
        {
            int byteCount = count;
            while (--byteCount >= 0)
                _buffer[_position + byteCount] = buffer[offset + byteCount];
        }
        else
        {
			System.arraycopy(buffer, offset, _buffer, _position, count);
        }

        _position = i;
    }

	public void writeByte(byte value) throws IOException
	{
		if (!_isOpen)
			errorStreamIsClosed();
		if (!_writable)
			errorWriteNotSupported();

		if (_position >= _length)
		{
			int newLength = _position + 1;
			boolean mustZero = _position > _length;
			if (newLength >= _capacity)
			{
				boolean allocatedNewArray = ensureCapacity(newLength);
				if (allocatedNewArray)
					mustZero = false;
			}
			if (mustZero)
				msArray.clear(_buffer, _length, _position - _length);
			_length = newLength;
		}
		_buffer[_position++] = value;
	}

    public void copyTo(Stream stream) throws Exception
    {
        // Java streams not copy only write
        writeTo(stream);
    }

	public void writeTo(Stream stream) throws Exception
    {
        if (!_isOpen)
            errorStreamIsClosed();
        if (stream == null)
            throw new IllegalArgumentException("stream");

        stream.write(_buffer, _origin, _length - _origin);
    }

    private void errorStreamIsClosed() throws IOException
    {
        throw new IOException("The stream is closed.");
    }

    private void errorWriteNotSupported()
    {
        throw new UnsupportedOperationException("The stream does not support write operations.");
    }

	//JAVA-added
	public String toString()
	{
		return msArray.arrayToStringCombined(_buffer, _origin, _length);
	}
}
