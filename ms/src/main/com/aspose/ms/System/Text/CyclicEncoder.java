package com.aspose.ms.System.Text;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

public class CyclicEncoder extends Encoder
{
    CyclicEncoder(Encoding encoding) {super(encoding);}

    @Override
    public int getByteCount(char[] chars, int index, int count, boolean flush)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getBytes(char[] chars, int charIndex, int charCount, byte[] bytes, int byteIndex, boolean flush)
    {
        return getBytes(chars, charIndex, charCount, bytes, byteIndex, Integer.MAX_VALUE, flush);
    }

    @Override
    public int getBytes(char[] chars, int charIndex, int charCount, byte[] bytes, int byteIndex, int byteCount, boolean flush)
    {
        // Validate input parameters
        if (chars == null)
            throw new NullPointerException("chars");
        if (bytes == null)
            throw new NullPointerException("bytes");
        if (charCount < 0 || charIndex < 0 || ((charIndex + charCount) > chars.length))
            throw new IndexOutOfBoundsException("charIndex, charCount");
        if (byteIndex < 0 || byteIndex > bytes.length)
            throw new IndexOutOfBoundsException("byteIndex");

        mCharsetEncoder.reset();
        mCharsetEncoder.onUnmappableCharacter(CodingErrorAction.REPLACE);

        //JAVA: wrap arrays into appropriate buffers
        CharBuffer charBuffer = CharBuffer.wrap(chars, charIndex, charCount);
        int bytesLength = Math.min(bytes.length - byteIndex, byteCount);
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, byteIndex, bytesLength);

        //JAVA: decode, at last,
        //in java we should set param endOfInput to true for the last decode,
        //but we provide exact size of input bytes so can leave without this.
        //Note: Flush is always false because .net can auto flush, but java encoder
        // can flush only once: at the very end.
        CoderResult coderResult = mCharsetEncoder.encode(charBuffer, byteBuffer, false);

        if (coderResult.isOverflow())
        {
            mCharsetEncoder.encode(charBuffer, byteBuffer, true);
            coderResult = mCharsetEncoder.flush(byteBuffer);
        }

        if (coderResult.isOverflow())
            throw new IllegalArgumentException("Encode Overflow: insufficient space in the 'bytes' array.");

        mCharUsed = charCount - charBuffer.length();
        return bytesLength - byteBuffer.remaining();
    }
}
