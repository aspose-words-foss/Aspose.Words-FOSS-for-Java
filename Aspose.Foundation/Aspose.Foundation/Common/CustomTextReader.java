// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/03/2012 by Andrey Soldatov
// 11/02/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// <para>
/// The class is helper for detecting file format,
/// it wraps a stream and provides convenient operations needed for a process of detection of a stream format.
/// </para>
/// <para>
/// It's used both for textual and binary file formats. Although operations needed for textual and binary formats are
/// quite different, there are some common operations which makes this combination acceptable.
/// </para>
/// </summary>
public class CustomTextReader
{
    /// <summary>
    /// Ctor.
    /// </summary>
    public CustomTextReader(Stream stream) throws Exception
    {
    	this(stream, null);
	
    }

    /// <summary>
    /// Ctor. Allows specifying encoding of the input stream. Otherwise it will be autodetected.
    /// </summary>
    public CustomTextReader(Stream stream, Encoding encoding) throws Exception
    {
        resetReadLimit();
        mStream = stream;
        mStreamInitPos = stream.getPosition();
        mEncoding = (encoding != null ? encoding : CharsetDetector.detect(mStream));

        // WORDSNET-24468 Fallback to UTF8, when encoding cannot be properly detected by CharsetDetector.
        if (mEncoding == null)
            mEncoding = Encoding.getUTF8();

        readMore();

        // Detect BOM at the beginning to ignore it when reading even single-byte encodings.
        mBom = CodePage.getBom(mDetectionBuffer);

        mTextBuffer = getEncoding().getString(mDetectionBuffer, mBom.length, mBytesRead - mBom.length);
    }

    /// <summary>
    /// Reverts the stream to initial position to make possible specific
    /// format readers to read a document from the current position.
    /// </summary>
    public void rewindStream() throws Exception
    {
        mStream.setPosition(mStreamInitPos + getUtf7BomShift());
    }

    //>>>>>>>> #region  Binary Operations

    /// <summary>
    /// Whether the stream has given byte prefix.
    /// String is used as parameter for convenience of prefix definition in code.
    /// </summary>
    public boolean hasBinaryPrefix(String asciiString)
    {
        if (mBytesRead < asciiString.length())
            return false;

        for (int i = 0; i < asciiString.length(); i++)
            if ((mDetectionBuffer[i] & 0xFF) != asciiString.charAt(i))
                return false;

        return true;
    }

    /// <summary>
    /// Whether the stream has given byte prefix.
    /// Byte array is used as parameter for convenience of prefix definition in code.
    /// </summary>
    public boolean hasBinaryPrefix(byte[] bytes)
    {
        if (mBytesRead < bytes.length)
            return false;

        for (int i = 0; i < bytes.length; i++)
            if (mDetectionBuffer[i] != bytes[i])
                return false;

        return true;
    }

    public boolean containsBinary(byte[] bytes)
    {
        if (mBytesRead < bytes.length)
            return false;

        boolean found = false;

        for (int i = 0; i < mBytesRead - bytes.length && !found; i++)
        {
            for (int j = 0; j < bytes.length; j++)
            {
                if (mDetectionBuffer[j + i] != bytes[j])
                {
                    found = false;
                    break;
                }

                found = true;
            }
        }

        return found;
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  Text Operations

    /// <summary>
    /// <para>Sets the current encoding of textual stream to Windows Latin1 code page preparing
    /// the reader for <see cref="ReadChar"/> and other textual operations.</para>
    /// </summary>
    /// <remarks>
    /// <para>This mode is used by MhtmlFormatDetector.</para>
    /// </remarks>
    public void setEncodingLatin() throws Exception
    {
        resetState();
        mTextBuffer = getEncoding().getString(mDetectionBuffer, mBom.length, mBytesRead - mBom.length);
    }

    /// <summary>
    /// <para>Sets the current encoding of textual stream to one determined by BOM (Byte Order Mark) preparing
    /// the reader for <see cref="ReadChar"/> and other textual operations.</para>
    /// </summary>
    /// <remarks>
    /// <para>This mode is used by FileFormatDetector.</para>
    /// </remarks>
    public void setEncodingByBom() throws Exception
    {
        resetState();

        // WORDSNET-23607 Set encoding by BOM.
        int codePage = CodePage.getByBom(mBom);
        if (codePage != CodePage.UNKNOWN)
            mEncoding = EncodingUtil.getEncoding(codePage);

        mTextBuffer = getEncoding().getString(mDetectionBuffer, mBom.length, mBytesRead - mBom.length);
    }

    /// <summary>
    /// <para>Increases a reading limit.</para>
    /// <para>One of goals of the class is to avoid reading of whole file during process of detection of the stream
    /// format. To achieve this, a reading limit is implemented. But there are some cases (which are responsibility
    /// of format detectors) when the reading limit should be increased.</para>
    /// </summary>
    public void resetReadLimit()
    {
        if (mIsResetReadLimitAvailable)
            mReadLimit = 1024;
    }

    /// <summary>
    /// Checks whether prefix of the stream contains given value. Uses a part of the stream which is already read
    /// to internal buffer of the reader but not more than 512 characters.
    /// </summary>
    public boolean contains(String value)
    {
        return msString.indexOf(mTextBuffer, value, 0, Math.min(mTextBuffer.length(), 512), StringComparison.ORDINAL) != -1;
    }

    /// <summary>
    /// <para>Locks possibility to reset a reading limit.</para>
    /// <para>One of goals of the class is to avoid reading of whole file during process of detection of the stream format.
    /// To achieve this, a reading limit is implemented. But there are some cases (which are responsibility of format
    /// detectors) when the reading limit should not be increased even if <see cref="ResetReadLimit"/> is called.</para>
    /// <para>This command has a precedence over <see cref="ResetReadLimit"/>.</para>
    /// </summary>
    public void lockResetReadLimit()
    {
        mIsResetReadLimitAvailable = false;
    }

    /// <summary>
    /// <para>Reads one character from the stream.</para>
    /// <para>Calling side is responsible for check
    /// whether there is a character to read (use <see cref="HasChars"/>).</para>
    /// </summary>
    public char readChar() throws Exception
    {
        if (!hasChars())
        {
            Debug.fail("Cannot read behind a read limit.");
            return '\u0000';
        }

        mReadLimit--;
        return mTextBuffer.charAt(mTextCursor++);
    }

    /// <summary>
    /// <para>Rewind the reader one character back.</para>
    /// <para>Calling side is responsible for calling the method only after at least one <see cref="ReadChar"/>.</para>
    /// </summary>
    public void stepBack()
    {
        if (mTextCursor == 0)
            Debug.fail("Cannot step back.");

        mReadLimit++;
        mTextCursor--;
    }

    /// <summary>
    /// <para>Rewind the reader to beginning.</para>
    /// </summary>
    public void stepFirst()
    {
        mTextCursor = 0;
        mIsResetReadLimitAvailable = true;
        resetReadLimit();
    }

    /// <summary>
    /// Whether a character can be read by <see cref="ReadChar"/>.
    /// </summary>
    public boolean hasChars() throws Exception
    {
        Debug.assertTrue(mTextCursor <= mTextBuffer.length());

        if (mReadLimit <= 0)
            return false;

        if (mTextCursor < mTextBuffer.length())
            return true;

        if (!isReadCompletely() && readMore())
            mTextBuffer = getEncoding().getString(mDetectionBuffer, mBom.length, mBytesRead - mBom.length);

        return mTextCursor < mTextBuffer.length();
    }

    /// <summary>
    /// Returns the underlying stream. I hope it is okay and does not violate any encapsulation.
    /// </summary>
    public Stream getStream() { return mStream; }

    /// <summary>
    /// Whether whole stream was read to the buffer.
    /// </summary>
    public boolean isReadCompletely() { return mBytesRead != mDetectionBuffer.length; }

    /// <summary>
    /// Encoding used to convert stream data to characters.
    /// </summary>
    public Encoding getEncoding() { return mEncoding; }

    /// <summary>
    /// Gets a total number of read characters.
    /// </summary>
    public int getTotalCharsRead() { return mTextCursor; }

    /// <summary>
    /// Gets character at the current cursor position within a buffer.
    /// </summary>
    public char getCurChar()
    {
        return (mTextCursor < mTextBuffer.length()) ? mTextBuffer.charAt(mTextCursor) : '\u0000';
    }

    /// <summary>
    /// Gets character at the next cursor position within a buffer.
    /// </summary>
    public char getNextChar()
    {
        return (mTextCursor + 1 < mTextBuffer.length()) ? mTextBuffer.charAt(mTextCursor + 1) : '\u0000';
    }

    //<<<<<<<< #endregion 

    public void resetState() throws Exception
    {
        mStream.setPosition(mStreamInitPos);
        mTextBuffer = null;
        mTextCursor = 0;
        mIsResetReadLimitAvailable = true;
        resetReadLimit();
    }

    /// <summary>
    /// Invalidates current detection buffer and reads from the handled stream a new one which is 4 times longer.
    /// Reads 512-byte buffer for the first time.
    /// Returns whether any new data was read after increasing the buffer.
    /// </summary>
    /// <remarks>
    /// For the most test files, buffers of 512 and 2048 bytes are used.
    /// There is no limit for buffer length used during the detection process. However, a simple test shows that most
    /// of our tests require a buffer of 512 or 2048 bytes. Only TestJira5527 and TestJira5760 require a larger buffer.
    /// </remarks>
    private boolean readMore() throws Exception
    {
        if (isReadCompletely())
            return false;

        int oldBytesRead = mBytesRead;
        mDetectionBuffer = new byte[(mDetectionBuffer.length == 0) ? BUFFER_INITIAL_SIZE : mDetectionBuffer.length * 4];
        readToExistingBuffer();
        return mBytesRead > oldBytesRead;
    }

    /// <summary>
    /// Reads data from the stream to the existing buffer
    /// until the end of the buffer or the end of the stream.
    /// </summary>
    private void readToExistingBuffer() throws Exception
    {
        mStream.setPosition(mStreamInitPos);
        mBytesRead = 0;

        while (true)
        {
            int bytesRead = mStream.read(mDetectionBuffer, mBytesRead, mDetectionBuffer.length - mBytesRead);
            if (bytesRead == 0)
                break;
            mBytesRead += bytesRead;
        }
    }

    /// <summary>
    /// .NET readers (used for loading files of specific formats) cannot recognize Utf7 BOM, so we shift the stream's
    /// position right after the Utf7 BOM when <see cref="RewindStream"/> is called.
    /// </summary>
    private int getUtf7BomShift()
    {
        // UTF7 BOM has unique length  among other BOMs, so we can use it here.
        return (mBom.length == CodePage.UTF_7_BOM.length) ? mBom.length : 0;
    }

    private /*final*/ Stream mStream;
    private /*final*/ long mStreamInitPos;

    private byte[] mDetectionBuffer = ArrayUtil.EMPTY_BYTE_ARRAY;
    private int mBytesRead;

    private String mTextBuffer;
    private int mTextCursor;

    private /*final*/ byte[] mBom;

    private int mReadLimit;
    private boolean mIsResetReadLimitAvailable = true;

    /// <summary>
    /// Size of the trial buffer that is read at the beginning of the stream. HTML 4.01 states that "meta charset"
    /// declaration "should appear as early as possible", however in HTML 5.0 it is restricted to the first 512 bytes.
    /// We saw a document containing the "meta charset" declaration truncated by 512-bytes edge (WORDSNET-9285).
    /// try-catch block was added to absorb the exception.
    /// References:
    /// http://www.w3.org/TR/REC-html40/charset.html#spec-char-encoding
    /// http://www.w3.org/TR/html5-diff/#character-encoding
    /// </summary>
    private static final int BUFFER_INITIAL_SIZE = 512;
    private Encoding mEncoding;
}

