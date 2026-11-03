// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/io/streamreader.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.IO;

import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.Text.Decoder;
import com.aspose.ms.System.Text.EncodingProvider;

import java.io.IOException;

/**
 * .NET System.IO.StreamReader ported from Rotor.
 * Note that only methods used by Aspose.Words are implemented.
 *
 * This class implements a TextReader for reading characters in
 * a particular Encoding from a Stream.
 *
 * ported by sk Apr 26, 2007
 */
public class StreamReader extends TextReader
{
    // Using a 1K byte buffer and a 4K FileStream buffer works out pretty well
    // perf-wise.  On even a 40 MB text file, any perf loss by using a 4K
    // buffer is negated by the win of allocating a smaller byte[], which
    // saves construction time.  This does break adaptive buffering,
    // but this is slightly faster.
    static final int DefaultBufferSize = 1024;  // Byte buffer size
    private static final int DefaultFileStreamBufferSize = 4096;
    private static final int MinBufferSize = 128;

    private Stream stream;
    private Encoding encoding;
    private Decoder decoder;
    private byte[] byteBuffer;
    private char[] charBuffer;
    private byte[] _preamble;   // Encoding's preamble, which identifies this encoding.
    private int charPos;
    private int charLen;
    // Record the number of valid bytes in the byteBuffer, for a few checks.
    private int byteLen;
    // This is used only for preamble detection
    private int bytePos;

    // This is the maximum number of chars we can get from one call to
    // ReadBuffer.  Used so ReadBuffer can tell when to copy data into
    // a user's char[] directly, instead of our char[].
    private int _maxCharsPerBuffer;

    // We will support looking for byte order marks in the stream and trying
    // to decide what the encoding might be from the byte order marks, IF they
    // exist.  But that's all we'll do.
    private boolean _detectEncoding;

    // Whether we must still check for the encoding's given preamble at the
    // beginning of this file.
    private boolean _checkPreamble;

    // Whether the stream is most likely not going to give us back as much
    // data as we want the next time we call it.  We must do the computation
    // before we do any byte order mark handling and save the result.  Note
    // that we need this to allow users to handle streams used for an
    // interactive protocol, where they block waiting for the remote end
    // to send a response, like logging in on a Unix machine.
    private boolean _isBlocked;

    // StreamReader by default will ignore illegal UTF8 characters. We don't want to
    // throw here because we want to be able to read ill-formed data without choking.
    // The high level goal is to be tolerant of encoding errors when we read and very strict
    // when we write. Hence, default StreamWriter encoding will throw on error.

    StreamReader() {
    }

    public StreamReader(Stream stream)
    {
        this(stream, true);
    }

    public StreamReader(Stream stream, boolean detectEncodingFromByteOrderMarks)
    {
        this(stream, Encoding.getUTF8(), detectEncodingFromByteOrderMarks, DefaultBufferSize);
    }

    public StreamReader(Stream stream, Encoding encoding)
    {
        this(stream, encoding, true, DefaultBufferSize);
    }

    public StreamReader(Stream stream, Encoding encoding, boolean detectEncodingFromByteOrderMarks)
    {
        this(stream, encoding, detectEncodingFromByteOrderMarks, DefaultBufferSize);
    }

    // Creates a new StreamReader for the given stream.  The
    // character encoding is set by encoding and the buffer size,
    // in number of 16-bit characters, is set by bufferSize.
    //
    // Note that detectEncodingFromByteOrderMarks is a very
    // loose attempt at detecting the encoding by looking at the first
    // 3 bytes of the stream.  It will recognize UTF-8, little endian
    // unicode, and big endian unicode text, but that's it.  If neither
    // of those three match, it will use the Encoding you provided.
    //
    public StreamReader(Stream stream, Encoding encoding, boolean detectEncodingFromByteOrderMarks, int bufferSize)
    {
        if (stream==null || encoding==null)
            throw new NullPointerException((stream==null ? "stream" : "encoding"));
        if (bufferSize <= 0)
            throw new IndexOutOfBoundsException("bufferSize");

        init(stream, encoding, detectEncodingFromByteOrderMarks, bufferSize);
    }

	public StreamReader(String path) throws IOException
	{
		this(path, Encoding.getUTF8());
	}

	public StreamReader(String path, Encoding encoding) throws IOException
    {
	    if (path == null || path.length() <= 0)
	        throw new IllegalArgumentException("The argument cannot be null or empty string.\r\nParameter name: path");

	    Stream stream = new FileStream(path, false, false);

        if (encoding == null)
            throw new NullPointerException("encoding");

        init(stream, encoding, true, DefaultBufferSize);
    }

    private void init(Stream stream, Encoding encoding, boolean detectEncodingFromByteOrderMarks, int bufferSize)
    {
        this.stream = stream;
        this.encoding = encoding;
        decoder = encoding.getDecoder();
        if (bufferSize < MinBufferSize) bufferSize = MinBufferSize;
        byteBuffer = new byte[bufferSize];
		//JAVA: + 2 added for part of char that can remain in previous bytebuffer
        //RK There was + 1, but it was not enough!
		_maxCharsPerBuffer = encoding.getMaxCharCount(bufferSize + 2);
        charBuffer = new char[_maxCharsPerBuffer];
        byteLen = 0;
        bytePos = 0;
        _detectEncoding = detectEncodingFromByteOrderMarks;
        _preamble = encoding.getPreamble();
        _checkPreamble = (_preamble.length > 0);
        _isBlocked = false;
    }

    public  void close() throws IOException
    {
        dispose(true);
    }

    protected  void dispose(boolean disposing) throws IOException
    {
        // Dispose of our resources if this StreamReader is closable.
        // Note that Console.In should not be closable.
        try
        {
            // Note that Stream.Close() can potentially throw here. So we need to
            // ensure cleaning up resources, inside the finally block.
            if (disposing && (stream != null))
                stream.close();
        }
        finally
        {
            if (stream != null)
            {
                stream = null;
                encoding = null;
                decoder = null;
                byteBuffer = null;
                charBuffer = null;
                charPos = 0;
                charLen = 0;
            }
        }
    }

    public Encoding getCurrentEncoding()
    {
        return encoding;
    }

    public Stream getBaseStream()
    {
        return stream;
    }

    public boolean getEndOfStream() throws Exception
    {
        if (stream == null)
            throw new IllegalStateException("StreamReader Closed");

        if (charPos < charLen)
            return false;

        // This may block on pipes!
        int numRead = readBuffer();
        return numRead == 0;
    }

    public  int peek() throws Exception
    {
        if (stream == null)
            throw new IllegalStateException("StreamReader Closed");

        if (charPos == charLen)
        {
            if (_isBlocked || readBuffer() == 0)
                return -1;
        }
        return charBuffer[charPos];
    }

    public  int read() throws Exception
    {
        if (stream == null)
            throw new IllegalStateException("StreamReader Closed");

        if (charPos == charLen)
        {
            if (readBuffer() == 0)
                return -1;
        }

        int result = charBuffer[charPos];
        charPos++;
        return result;
    }

    public  int read(char[] buffer, int index, int count) throws Exception
    {
        if (stream == null)
            throw new IllegalStateException("StreamReader Closed");
        if (buffer==null)
            throw new NullPointerException("buffer");
        if (index < 0 || count < 0)
            throw new IndexOutOfBoundsException((index < 0 ? "index" : "count"));
        if (buffer.length - index < count)
            throw new IllegalArgumentException("index and count don't match buffer length");

        int charsRead = 0;
        // As a perf optimization, if we had exactly one buffer's worth of
        // data read in, let's try writing directly to the user's buffer.
        boolean[] readToUserBuffer = new boolean[]{false};//JAVA: out param emulation
        while (count > 0)
        {
            int n = charLen - charPos;
            if (n == 0)
				n = readBuffer(buffer, index + charsRead, count, readToUserBuffer);
            if (n == 0)
				break;  // We're at EOF
            if (n > count)
				n = count;
            if (!readToUserBuffer[0])
            {
                //JAVA was: , deleted all ' * 2' - may be this was bug?
//                Buffer.InternalBlockCopy(charBuffer, charPos * 2, buffer, (index + charsRead) * 2, n*2);
                System.arraycopy(charBuffer, charPos, buffer, (index + charsRead), n);
                charPos += n;
            }
            charsRead += n;
            count -= n;
            if (_isBlocked)
                break;
        }
        return charsRead;
    }

    public  String readToEnd() throws Exception
    {
        if (stream == null)
            throw new IllegalStateException("StreamReader Closed");

        // Call ReadBuffer, then pull data out of charBuffer.
        StringBuilder sb = new StringBuilder(charLen - charPos);
        do
        {
            sb.append(charBuffer, charPos, charLen - charPos);
            charPos = charLen;  // Note we consumed these characters
            readBuffer();
        } while (charLen > 0);

        return sb.toString();
    }

    // Trims n bytes from the front of the buffer.
    private void compressBuffer(int n)
    {
        assert byteLen >= n : "CompressBuffer was called with a number of bytes greater than the current buffer length.  Are two threads using this StreamReader at the same time?";
        //JAVA was:
//        Buffer.InternalBlockCopy(byteBuffer, n, byteBuffer, 0, byteLen - n);
        System.arraycopy(byteBuffer, n, byteBuffer, 0, byteLen - n);
        byteLen -= n;
    }

    private void detectEncoding()
    {
        if (byteLen < 2)
            return;
        _detectEncoding = false;
        boolean changedEncoding = false;
        if (byteBuffer[0]==(byte)0xFE && byteBuffer[1]==(byte)0xFF)
        {
            // Big Endian Unicode

            encoding = Encoding.newUnicodeEncoding(true, true);
            compressBuffer(2);
            changedEncoding = true;
        }
        else if (byteBuffer[0]==(byte)0xFF && byteBuffer[1]==(byte)0xFE)
        {
            // Little Endian Unicode, or possibly little endian UTF32
            if (byteLen >= 4 && byteBuffer[2] == 0 && byteBuffer[3] == 0)
            {
                encoding = Encoding.newUTF32Encoding(false, true);
                compressBuffer(4);
            }
            else
            {
                encoding = Encoding.newUnicodeEncoding(false, true);
                compressBuffer(2);
            }
            changedEncoding = true;
        }
        else if (byteLen >= 3 && byteBuffer[0] == (byte)0xEF && byteBuffer[1] == (byte)0xBB && byteBuffer[2] == (byte)0xBF)
        {
            // UTF-8
            encoding = Encoding.getUTF8();
            compressBuffer(3);
            changedEncoding = true;
        }
        else if (byteLen >= 4 && byteBuffer[0] == 0 && byteBuffer[1] == 0 &&
                 byteBuffer[2] == (byte)0xFE && byteBuffer[3] == (byte)0xFF)
        {
            // Big Endian UTF32
            encoding = Encoding.newUTF32Encoding(true, true);
            changedEncoding = true;
        }
        //JAVA-added: since Aspose.Words's NrxXmlBuilder writes UTF7 BOM we have to detect and compress it out.
        //Note: there are several variants of UTF7 BOM but we use here only one that uses Aspose.Words.
        //(see FileFormatUtil.gUtf7_bom
        else if (byteLen >= 5 &&
                 byteBuffer[0] == (byte)0x2B &&
                 byteBuffer[1] == (byte)0x2F &&
                 byteBuffer[2] == (byte)0x76 &&
                 byteBuffer[3] == (byte)0x38 &&
                 byteBuffer[4] == (byte)0x2D)
        {
            // UTF-7
            if (encoding.getCodePage() != EncodingProvider.CODE_PAGE_UTF7)
            {
	            encoding = Encoding.getUTF7();
	            changedEncoding = true;
            }
	        compressBuffer(5);
        }
        else if (byteLen == 2)
            _detectEncoding = true;
        // Note: in the future, if we change this algorithm significantly,
        // we can support checking for the preamble of the given encoding.

        if (changedEncoding)
        {
            decoder = encoding.getDecoder();
			//JAVA: + 2 added for part of char that can remain in previous bytebuffer
            //RK There was + 1, but it was not enough!
			_maxCharsPerBuffer = encoding.getMaxCharCount(byteBuffer.length + 2);
            charBuffer = new char[_maxCharsPerBuffer];
        }
    }

    // Trims the preamble bytes from the byteBuffer. This routine can be called multiple times
    // and we will buffer the bytes read until the preamble is matched or we determine that
    // there is no match. If there is no match, every byte read previously will be available
    // for further consumption. If there is a match, we will compress the buffer for the
    // leading preamble bytes
    private boolean isPreamble()
    {
        if (!_checkPreamble)
            return _checkPreamble;

        assert bytePos <= _preamble.length : "_compressPreamble was called with the current bytePos greater than the preamble buffer length.  Are two threads using this StreamReader at the same time?";
        int len = (byteLen >= (_preamble.length))? (_preamble.length - bytePos) : (byteLen  - bytePos);

        for(int i=0; i<len; i++, bytePos++)
        {
            if (byteBuffer[bytePos] != _preamble[bytePos])
            {
                bytePos = 0;
                _checkPreamble = false;
                break;
            }
        }

        assert bytePos <= _preamble.length : "possible bug in _compressPreamble.  Are two threads using this StreamReader at the same time?";

        if (_checkPreamble)
        {
            if (bytePos == _preamble.length)
            {
                // We have a match
                compressBuffer(_preamble.length);
                bytePos = 0;
                _checkPreamble = false;
                _detectEncoding = false;
            }
        }

        return _checkPreamble;
    }

    private int readBuffer() throws Exception
    {
        charLen = 0;
        charPos = 0;

        if (!_checkPreamble)
            byteLen = 0;
        do
        {
            if (_checkPreamble)
            {
                assert bytePos <= _preamble.length : "possible bug in _compressPreamble.  Are two threads using this StreamReader at the same time?";
                int len = stream.read(byteBuffer, bytePos, byteBuffer.length - bytePos);
                assert len >= 0 : "Stream.Read returned a negative number!  This is a bug in your stream class.";

                if (len == 0)
                {
                    // EOF but we might have buffered bytes from previous
                    // attempts to detecting preamble that needs to decoded now
                    if (byteLen > 0)
                        charLen += decoder.getChars(byteBuffer, 0, byteLen, charBuffer, charLen);

                    return charLen;
                }

                byteLen += len;
            }
            else
            {
                assert bytePos == 0 : "bytePos can be non zero only when we are trying to _checkPreamble.  Are two threads using this StreamReader at the same time?";
                byteLen = stream.read(byteBuffer, 0, byteBuffer.length);
                assert byteLen >= 0 : "Stream.Read returned a negative number!  This is a bug in your stream class.";

                if (byteLen == 0)  // We're at EOF
                    return charLen;
            }

            _isBlocked = (byteLen < byteBuffer.length);

            // Check for preamble before detect encoding. This is not to  the
            // user suppplied Encoding for the one we implicitly detect. The user could
            // customize the encoding which we will loose, such as ThrowOnError on UTF8
            if (isPreamble())
                continue;

            // If we're supposed to detect the encoding and haven't done so yet,
            // do it.  Note this may need to be called more than once.
            if (_detectEncoding && byteLen >= 2)
                detectEncoding();

            charLen += decoder.getChars(byteBuffer, 0, byteLen, charBuffer, charLen);
        } while (charLen == 0);
        //Console.WriteLine("ReadBuffer called.  chars: "+charLen);
        return charLen;
    }

    // This version has a perf optimization to decode data DIRECTLY into the
    // user's buffer, bypassing StreamWriter's own buffer.
    // This gives a > 20% perf improvement for our encodings across the board,
    // but only when asking for at least the number of characters that one
    // buffer's worth of bytes could produce.
    // This optimization, if run, will break SwitchEncoding, so we must not do
    // this on the first call to ReadBuffer.
    private int readBuffer(char[] userBuffer, int userOffset, int desiredChars, boolean[] readToUserBuffer) throws Exception //JAVA: readToUserBuffer[] - out param emulation
    {
        charLen = 0;
        charPos = 0;

        if (!_checkPreamble)
            byteLen = 0;

        int charsRead = 0;

        // As a perf optimization, we can decode characters DIRECTLY into a
        // user's char[].  We absolutely must not write more characters
        // into the user's buffer than they asked for.  Calculating
        // encoding.GetMaxCharCount(byteLen) each time is potentially very
        // expensive - instead, cache the number of chars a full buffer's
        // worth of data may produce.  Yes, this makes the perf optimization
        // less aggressive, in that all reads that asked for fewer than AND
        // returned fewer than _maxCharsPerBuffer chars won't get the user
        // buffer optimization.  This affects reads where the end of the
        // Stream comes in the middle somewhere, and when you ask for
        // fewer chars than than your buffer could produce.
        readToUserBuffer[0] = desiredChars >= _maxCharsPerBuffer;

        do
        {
            if (_checkPreamble)
            {
                assert bytePos <= _preamble.length : "possible bug in _compressPreamble.  Are two threads using this StreamReader at the same time?";
                int len = stream.read(byteBuffer, bytePos, byteBuffer.length - bytePos);
                assert len >= 0 : "Stream.Read returned a negative number!  This is a bug in your stream class.";

                if (len == 0)
                {
                    // EOF but we might have buffered bytes from previous
                    // attempts to detecting preamble that needs to decoded now
                    if (byteLen > 0)
                    {
                        if (readToUserBuffer[0])
                        {
                            charsRead += decoder.getChars(byteBuffer, 0, byteLen, userBuffer, userOffset + charsRead);
                            charLen = 0;  // StreamReader's buffer is empty.
                        }
                        else
                        {
                            charsRead = decoder.getChars(byteBuffer, 0, byteLen, charBuffer, charsRead);
                            charLen += charsRead;  // Number of chars in StreamReader's buffer.
                        }
                    }
                    return charsRead;
                }

                byteLen += len;
            }
            else
            {
                assert bytePos == 0 : "bytePos can be non zero only when we are trying to _checkPreamble.  Are two threads using this StreamReader at the same time?";
                byteLen = stream.read(byteBuffer, 0, byteBuffer.length);
                assert byteLen >= 0 : "Stream.Read returned a negative number!  This is a bug in your stream class.";

                if (byteLen == 0)  // EOF
                    return charsRead;
            }

            _isBlocked = (byteLen < byteBuffer.length);

            // Check for preamble before detect encoding. This is not to  the
            // user suppplied Encoding for the one we implicitly detect. The user could
            // customize the encoding which we will loose, such as ThrowOnError on UTF8
            // Note: we don't need to recompute readToUserBuffer optimization as IsPreamble
            // doesn't change the encoding or affect _maxCharsPerBuffer
            if (isPreamble())
                continue;

            // On the first call to ReadBuffer, if we're supposed to detect the encoding, do it.
            if (_detectEncoding && byteLen >= 2)
            {
                detectEncoding();
                // DetectEncoding changes some buffer state.  Recompute this.
                readToUserBuffer[0] = desiredChars >= _maxCharsPerBuffer;
            }

            charPos = 0;
            if (readToUserBuffer[0])
            {
                charsRead += decoder.getChars(byteBuffer, 0, byteLen, userBuffer, userOffset + charsRead);
                charLen = 0;  // StreamReader's buffer is empty.
            }
            else
			{
                charsRead = decoder.getChars(byteBuffer, 0, byteLen, charBuffer, charsRead);
                charLen += charsRead;  // Number of chars in StreamReader's buffer.
            }
        } while (charsRead == 0);

        _isBlocked &= charsRead < desiredChars;

        //Console.WriteLine("ReadBuffer: charsRead: "+charsRead+"  readToUserBuffer: "+readToUserBuffer);
        return charsRead;
    }

    // Reads a line. A line is defined as a sequence of characters followed by
    // a carriage return ('\r'), a line feed ('\n'), or a carriage return
    // immediately followed by a line feed. The resulting String does not
    // contain the terminating carriage return and/or line feed. The returned
    // value is null if the end of the input stream has been reached.
    //
    public  String readLine() throws Exception
    {
        if (stream == null)
            throw new IllegalStateException("StreamReader Closed");

        if (charPos == charLen)
        {
            if (readBuffer() == 0) return null;
        }
        StringBuilder sb = null;
        do
        {
            int i = charPos;
            do
            {
                char ch = charBuffer[i];
                // Note the following common line feed chars:
                // \n - UNIX   \r\n - DOS   \r - Mac
                if (ch == '\r' || ch == '\n') {
                    String s;
                    if (sb != null) {
                        sb.append(charBuffer, charPos, i - charPos);
                        s = sb.toString();
                    }
                    else
                    {
                        s = new String(charBuffer, charPos, i - charPos);
                    }
                    charPos = i + 1;
                    if (ch == '\r' && (charPos < charLen || readBuffer() > 0))
                    {
                        if (charBuffer[charPos] == '\n') charPos++;
                    }
                    return s;
                }
                i++;
            } while (i < charLen);

            i = charLen - charPos;
            if (sb == null) sb = new StringBuilder(i + 80);
            sb.append(charBuffer, charPos, i);
        } while (readBuffer() > 0);
        return sb.toString();
    }


}
