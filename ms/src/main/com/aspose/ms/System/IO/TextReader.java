// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/io/textreader.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.IO;

import com.aspose.ms.System.IDisposable;

/**
 * .NET System.IO.TextReader ported from Rotor.
 * Note that only methods used by Aspose.Words are implemented.
 *
 * This abstract base class represents a reader that can read a sequential
 * stream of characters.  This is not intended for reading bytes -
 * there are methods on the Stream class to read bytes.
 * A subclass must minimally implement the Peek() and Read() methods.
 *
 * ported by sk Apr 25, 2007
 */
public abstract class TextReader implements IDisposable
{
    public void close() throws Exception
    {
        dispose(true);
    }

    public void dispose() throws Exception
    {
        dispose(true);
    }

    protected abstract void dispose(boolean disposing) throws Exception;

    public abstract int peek() throws Exception;

    public abstract int read() throws Exception;

    /* Reads a block of characters. This method will read up to
     * count characters from this TextReader into the
     * buffer character array starting at position
     * index. Returns the actual number of characters read.
     */
    public int read(char[] buffer, int index, int count) throws Exception
    {
        if (buffer==null)
            throw new NullPointerException("buffer");
        if (index < 0)
            throw new IndexOutOfBoundsException("index");
        if (count < 0)
            throw new IndexOutOfBoundsException("count");
        if (buffer.length - index < count)
            throw new IllegalArgumentException("index and count don't match buffer length");

        int n = 0;
        do {
            int ch = read();
            if (ch == -1) break;
            buffer[index + n++] = (char)ch;
        } while (n < count);
        return n;
    }

    /*
     * Reads all characters from the current position to the end of the
     * TextReader, and returns them as one String.
     */
    public String readToEnd() throws Exception
    {
        char[] chars = new char[4096];
        int len;
        StringBuilder sb = new StringBuilder(4096);
        while((len= read(chars, 0, chars.length)) != 0)
        {
            sb.append(chars, 0, len);
        }
        return sb.toString();
    }

    /*
     * Blocking version of read.  Returns only when count
     * characters have been read or the end of the file was reached.
     */
    public int readBlock(char[] buffer, int index, int count) throws Exception
    {
        int i, n = 0;
        do {
            n += (i = read(buffer, index + n, count - n));
        } while (i > 0 && n < count);
        return n;
    }

    /*
     * Reads a line. A line is defined as a sequence of characters followed by
     * a carriage return ('\r'), a line feed ('\n'), or a carriage return
     * immediately followed by a line feed. The resulting String does not
     * contain the terminating carriage return and/or line feed. The returned
     * value is null if the end of the input stream has been reached.
     */
    public String readLine() throws Exception
    {
        StringBuilder sb = new StringBuilder();
        while (true) {
            int ch = read();
            if (ch == -1) break;
            if (ch == '\r' || ch == '\n')
            {
                if (ch == '\r' && peek() == '\n') read();
                return sb.toString();
            }
            sb.append((char)ch);
        }
        if (sb.length() > 0) return sb.toString();
        return null;
    }
}
