// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/io/stringreader.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.IO;

import com.aspose.ms.System.msString;


/**
 * .NET System.IO.StringReader ported from Rotor.
 *
 * This class implements a text reader that reads from a string.
 *
 * ported by sk Apr 25, 2007
 */
public class StringReader extends TextReader
{
    private String _s;
    private int _pos;
    private int _length;

    public StringReader(String s)
    {
        if (s == null)
            throw new NullPointerException("s");
        _s = s;
        _length = s.length();
    }

    protected void dispose(boolean disposing)
    {
        _s = null;
        _pos = 0;
        _length = 0;
    }

    // Returns the next available character without actually reading it from
    // the underlying String. The current position of the StringReader is not
    // changed by this operation. The returned value is -1 if no further
    // characters are available.
    //
    public int peek() throws Exception
    {
        if (_s == null)
            throw new IllegalStateException("StringReader Closed");
        if (_pos == _length) return -1;
        return _s.charAt(_pos);
    }

    // Reads the next character from the underlying String. The returned value
    // is -1 if no further characters are available.
    //
    public int read() throws Exception
    {
        if (_s == null)
            throw new IllegalStateException("StringReader Closed");
        if (_pos == _length) return -1;
        return _s.charAt(_pos++);
    }

    // Reads a block of characters. This method will read up to count
    // characters from this StringReader into the buffer character
    // array starting at position index. Returns the actual number of
    // characters read, or zero if the end of the String is reached.
    //
    public  int read(char[] buffer, int index, int count) throws Exception
    {
        if (buffer==null)
            throw new NullPointerException("buffer");
        if (index < 0)
            throw new IndexOutOfBoundsException("index");
        if (count < 0)
            throw new IndexOutOfBoundsException("count");
        if (buffer.length - index < count)
            throw new IllegalArgumentException("index and count don't match buffer length");
        if (_s == null)
            throw new IllegalStateException("StringReader Closed");

        int n = _length - _pos;
        if (n > 0)
        {
            if (n > count) n = count;
            msString.copyTo(_s, _pos, buffer, index, n);
            _pos += n;
        }
        return n;
    }

    public  String readToEnd() throws Exception
    {
        if (_s == null)
            throw new IllegalStateException("StringReader Closed");
        String s;
        if (_pos==0)
            s = _s;
        else
            s = _s.substring(_pos, _length);
        _pos = _length;
        return s;
    }

    // Reads a line. A line is defined as a sequence of characters followed by
    // a carriage return ('\r'), a line feed ('\n'), or a carriage return
    // immediately followed by a line feed. The resulting String does not
    // contain the terminating carriage return and/or line feed. The returned
    // value is null if the end of the underlying String has been reached.
    //
    public  String readLine() throws Exception
    {
        if (_s == null)
            throw new IllegalStateException("StringReader Closed");
        int i = _pos;
        while (i < _length)
        {
            char ch = _s.charAt(i);
            if (ch == '\r' || ch == '\n')
            {
                String result = _s.substring(_pos, i);
                _pos = i + 1;
                if (ch == '\r' && _pos < _length && _s.charAt(_pos) == '\n')
                    _pos++;
                return result;
            }
            i++;
        }
        if (i > _pos)
        {
            String result = _s.substring(_pos, i);
            _pos = i;
            return result;
        }
        return null;
    }
}
