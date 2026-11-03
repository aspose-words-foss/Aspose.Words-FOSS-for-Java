// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/io/streamwriter.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.
// ported 28/07/2008 by Konstantin Sidorenko
/*============================================================
**
** Class:  StreamWriter
**
**
** Purpose: For writing text to streams in a particular
** encoding.
**
**
===========================================================*/

package com.aspose.ms.System.IO;

import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.Text.Encoder;
import com.aspose.ms.System.Text.UTF8Encoding;
import com.aspose.ms.System.msString;

import java.io.IOException;

// This class implements a TextWriter for writing characters to a Stream.
// This is designed for character output in a particular Encoding,
// whereas the Stream class is designed for byte input and output.
//
public class StreamWriter extends TextWriter//@todo 1 sk StreamWriter to java OutputStream so we will save rtf directly to OutputStreams
{
    // For UTF-8, the values of 1K for the default buffer size and 4K for the
    // file stream buffer size are reasonable & give very reasonable
    // performance for in terms of construction time for the StreamWriter and
    // write perf.  Note that for UTF-8, we end up allocating a 4K byte buffer,
    // which means we take advantage of adaptive buffering code.
    // The performance using UnicodeEncoding is acceptable.
    private static final int DefaultBufferSize = 1024;   // char[]
    private static final int DefaultFileStreamBufferSize = 4096;
    private static final int MinBufferSize = 128;

	//JAVA-deleted
    // Bit bucket - Null has no backing store. Non closable.
//    public static final StreamWriter Null = new StreamWriter(Stream.Null, Encoding.getUTF8(), MinBufferSize, false);

    Stream stream;
    private Encoding encoding;
    private Encoder encoder;
    byte[] byteBuffer;
    char[] charBuffer;
    int charPos;
    int charLen;
    boolean autoFlush;
    private boolean haveWrittenPreamble;
    private boolean closable;  // For Console.Out - should Finalize call Dispose?

    // The high level goal is to be tolerant of encoding errors when we read and very strict
    // when we write. Hence, default StreamWriter encoding will throw on encoding error.
    // Note: when StreamWriter throws on invalid encoding chars (for ex, high surrogate character
    // D800-DBFF without a following low surrogate character DC00-DFFF), it will cause the
    // internal StreamWriter's state to be irrecoverable as it would have buffered the
    // illegal chars and any subsequent call to Flush() would hit the encoding error again.
    // Even Close() will hit the exception as it would try to flush the unwritten data.
    // May be we can add a DiscardBufferedData() method to get out of such situation (like
    // StreamRerader though for different reason). Eitherway, the buffered data will be lost!
    private static Encoding _UTF8NoBOM;

    static Encoding getUTF8NoBOM()
	{
		if (gUtf8NoBom == null)
            gUtf8NoBom = new UTF8Encoding(false);
        return gUtf8NoBom;
    }

    StreamWriter()
    {
	    // Ask for CurrentCulture all the time
	    super();//JAVA was: super(null);
    }

    public StreamWriter(Stream stream) throws Exception
    {
		this(stream, getUTF8NoBOM(), DefaultBufferSize);
    }

    public StreamWriter(Stream stream, Encoding encoding) throws Exception
    {
	    this(stream, encoding, DefaultBufferSize);
    }

    // Creates a new StreamWriter for the given stream.  The
    // character encoding is set by encoding and the buffer size,
    // in number of 16-bit characters, is set by bufferSize.
    //
    public StreamWriter(Stream stream, Encoding encoding, int bufferSize) throws Exception
    {
	    // Ask for CurrentCulture all the time
	    super();//JAVA was: super(null);
        if (stream==null || encoding==null)
            throw new NullPointerException((stream==null ? "stream" : "encoding"));
        if (!stream.canWrite())
            throw new IllegalArgumentException("stream can't write");
        if (bufferSize <= 0) throw new IndexOutOfBoundsException("bufferSize");

        init(stream, encoding, bufferSize);
    }

    // For non closable streams such as Console.Out
    StreamWriter(Stream stream, Encoding encoding, int bufferSize, boolean closeable) throws Exception
    {
	    this(stream, encoding, bufferSize);
        closable = closeable;
    }

    public StreamWriter(String path) throws Exception
    {
	    this(path, false, getUTF8NoBOM(), DefaultBufferSize);
    }

    public StreamWriter(String path, boolean append) throws Exception
    {
	    this(path, append, getUTF8NoBOM(), DefaultBufferSize);
    }

    public StreamWriter(String path, boolean append, Encoding encoding) throws Exception
    {
	    this(path, append, encoding, DefaultBufferSize);
    }

    public StreamWriter(String path, boolean append, Encoding encoding, int bufferSize) throws Exception
    {
	    // Ask for CurrentCulture all the time
	    super();//JAVA was: super(null);

        if (path==null || encoding==null)
            throw new NullPointerException((path==null ? "path" : "encoding"));
        if (bufferSize <= 0) throw new IndexOutOfBoundsException("bufferSize");

        Stream stream = null;
	    try
	    {
		    stream = createFile(path, append);
            init(stream, encoding, bufferSize);
	    }
	    catch (IOException e)
	    {
		    if (stream != null)
			    stream.close();

		    throw e;
	    }
    }

    private void init(Stream stream, Encoding encoding, int bufferSize) throws Exception
    {
        this.stream = stream;
        this.encoding = encoding;
        this.encoder = encoding.getEncoder();
        if (bufferSize < MinBufferSize)
	        bufferSize = MinBufferSize;
        charBuffer = new char[bufferSize];
        byteBuffer = new byte[encoding.getMaxByteCount(bufferSize)];
        charLen = bufferSize;
        // If we're appending to a Stream that already has data, don't write
        // the preamble.
        if (stream.canSeek() && stream.getPosition() > 0)
            haveWrittenPreamble = true;
        closable = true;
    }

    private static Stream createFile(String path, boolean append) throws IOException
    {
	    //JAVA-changed from:
        int mode = append? FileMode.APPEND : FileMode.CREATE;//JAVA was: FileMode enum
        FileStream f = new FileStream(path, mode, FileAccess.WRITE);
        return f;
    }

    public void close() throws Exception
    {
        dispose(true);
        //GC.SuppressFinalize(this); //JAVA-deleted: not applicable to java.
    }

    protected void dispose(boolean disposing) throws Exception
    {
        try {
            // We need to flush any buffered data if we are being closed/disposed.
            // Also, we never close the handles for stdout & friends.  So we can safely
            // write any buffered data to those streams even during finalization, which
            // is generally the right thing to do.
            if (stream != null) {
                // Note: flush on the underlying stream can throw (ex., low disk space)
	            //JAVA-changed from:
                //if (disposing || (!getClosable() && stream is __ConsoleStream))
                if (disposing || !getClosable())
	            {
                    flush(true, true);
                }
            }
        }
        finally {
            // Dispose of our resources if this StreamWriter is closable.
            // Note: Console.Out and other such non closable streamwriters should be left alone
            if (getClosable() && stream != null) {
                try {
                    // Attempt to close the stream even if there was an IO error from Flushing.
                    // Note that Stream.Close() can potentially throw here (may or may not be
                    // due to the same Flush error). In this case, we still need to ensure
                    // cleaning up internal resources, hence the finally block.
                    if (disposing)
                    { stream.close(); }
                }
                finally {
                    stream = null;
                    byteBuffer = null;
                    charBuffer = null;
                    encoding = null;
                    encoder = null;
                    charLen = 0;
                    super.dispose(disposing);
                }
            }
        }
    }

    public void flush() throws Exception
    {
        flush(true, true);
    }

    private void flush(boolean flushStream, boolean flushEncoder) throws Exception
    {
        // flushEncoder should be true at the end of the file and if
        // the user explicitly calls Flush (though not if AutoFlush is true).
        // This is required to flush any dangling characters from our UTF-7
        // and UTF-8 encoders.
        if (stream == null)
            throw new IllegalStateException("StreamWriter already closed.");//JAVA was: System.err.println("Writer Closed.");//__Error.WriterClosed();

        // Perf boost for Flush on non-dirty writers.
        if (charPos==0 && !flushStream && !flushEncoder)
            return;

        if (!haveWrittenPreamble)
        {
            haveWrittenPreamble = true;
            byte[] preamble = encoding.getPreamble();
            if (preamble.length > 0)
                stream.write(preamble, 0, preamble.length);
        }

	    //JAVA: really we don't flush java encoder because it can be flushed only once
	    //at the very end, but .Net can flush many times, even autoflush.
        int count = encoder.getBytes(charBuffer, 0, charPos, byteBuffer, 0, flushEncoder);
        charPos = 0;
        if (count > 0)
            stream.write(byteBuffer, 0, count);
        // By definition, calling Flush should flush the stream, but this is
        // only necessary if we passed in true for flushStream.  The Web
        // Services guys have some perf tests where flushing needlessly hurts.
        if (flushStream)
        {
            //stream.Flush();//JAVA: Flush() not applicable for aspose's Memory- and FileStream.
        }
    }

    public boolean getAutoFlush()
    {
        return autoFlush;
    }

	public void setAutoFlush(boolean value) throws Exception
	{
		autoFlush = value;
		if (value) flush(true, false);
    }

    public Stream getBaseStream()
    {
        return stream;
    }

    boolean getClosable()
    {
        return closable;
        //set { closable = value; }
    }

    void setHaveWrittenPreamble(boolean value)
    {
        haveWrittenPreamble= value;
    }

    public Encoding getEncoding()
    {
        return encoding;
    }

    public void write(char value) throws Exception
    {
        if (charPos == charLen)
	        flush(false, false);
        charBuffer[charPos] = value;
        charPos++;
        if (autoFlush)
	        flush(true, false);
    }

    public void write(char[] buffer) throws Exception
    {
        // This may be faster than the one with the index & count since it
        // has to do less argument checking.
        if (buffer==null)
            return;
        int index = 0;
        int count = buffer.length;
        while (count > 0) {
            if (charPos == charLen) flush(false, false);
            int n = charLen - charPos;
            if (n > count) n = count;
            assert n > 0 : "StreamWriter::Write(char[]) isn't making progress!  This is most likely a race in user code.";
	        //JAVA-changed from:
            //Buffer.InternalBlockCopy(buffer, index*2, charBuffer, charPos*2, n*2);
	        System.arraycopy(buffer, index, charBuffer, charPos, n);
            charPos += n;
            index += n;
            count -= n;
        }
        if (autoFlush) flush(true, false);
    }

    public void write(char[] buffer, int index, int count) throws Exception
    {
        if (buffer==null)
            throw new NullPointerException("buffer");
        if (index < 0)
            throw new IndexOutOfBoundsException("index");
        if (count < 0)
            throw new IndexOutOfBoundsException("count");
        if (buffer.length - index < count)
            throw new IllegalArgumentException("buffer.length - index < count");

        while (count > 0) {
            if (charPos == charLen) flush(false, false);
            int n = charLen - charPos;
            if (n > count) n = count;
            assert n > 0 : "StreamWriter::Write(char[], int, int) isn't making progress!  This is most likely a race condition in user code.";
	        //JAVA-changed from:
            //Buffer.InternalBlockCopy(buffer, index*2, charBuffer, charPos*2, n*2);
	        System.arraycopy(buffer, index, charBuffer, charPos, n);
            charPos += n;
            index += n;
            count -= n;
        }
        if (autoFlush)
	        flush(true, false);
    }

    public void write(String value) throws Exception
    {
        if (value != null)
        {
            int count = value.length();
            int index = 0;
            while (count > 0)
            {
                if (charPos == charLen)
	                flush(false, false);
                int n = charLen - charPos;
                if (n > count)
	                n = count;
                assert n > 0 : "StreamWriter::Write(String) isn't making progress!  This is most likely a race condition in user code.";
	            //JAVA-changed from:
                //value.CopyTo(index, charBuffer, charPos, n);
	            msString.copyTo(value, index, charBuffer, charPos, n);
                charPos += n;
                index += n;
                count -= n;
            }
            if (autoFlush) flush(true, false);
        }
    }

    private static Encoding gUtf8NoBom;
}
