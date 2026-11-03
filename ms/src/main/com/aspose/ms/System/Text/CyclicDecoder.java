package com.aspose.ms.System.Text;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CoderResult;

class CyclicDecoder extends Decoder
{
    CyclicDecoder(Encoding encoding)
    {
        super(encoding);
    }

    @Override
    public int getCharCount(byte[] bytes, int index, int count)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getChars(byte[] bytes, int byteIndex, int byteCount, char[] chars, int charIndex)
    {
        // Validate input parameters
        if (chars == null || bytes == null)
            throw new NullPointerException(chars == null ? "chars" : "bytes");
        if (byteCount < 0 || charIndex < 0)
            throw new IndexOutOfBoundsException((byteCount < 0 ? "byteCount" : "charIndex"));
        if (charIndex > chars.length || byteCount < 0 || byteCount > (bytes.length + byteIndex))
            throw new IndexOutOfBoundsException(charIndex > chars.length ? "charIndex" : "byteCount");

        //JAVA: wrap arrays into appropriate buffers
        int charsLength = chars.length - charIndex;
        CharBuffer charBuffer = CharBuffer.wrap(chars, charIndex, charsLength);
        ByteBuffer byteBuffer;
        //JAVA: if we have few bytes remaning from previous decoding - append them to the begin.
        if (mRemaining > 0)
        {
            byteBuffer = ByteBuffer.allocate(mRemaining + byteCount - byteIndex);
            byteBuffer.put(mArray);
            byteBuffer.put(bytes, byteIndex, byteCount);
            byteBuffer.rewind();
            mArray = null;
        }
        else
        {
            byteBuffer = ByteBuffer.wrap(bytes, byteIndex, byteCount);
        }

        //JAVA: decode, at last. Loop to insert numeric values instead of Malformed and Unmappable characters as .Net does.
        CoderResult coderResult = CoderResult.UNDERFLOW;

        while (byteBuffer.remaining() > 0 && charBuffer.remaining() > 0)
        {
            CoderResult result = mCharsetDecoder.decode(byteBuffer, charBuffer, false);

            // Excellent. Break the loop. All bytes are decoded or all chars are filled.
            if (result.isUnderflow() || result.isOverflow())
                break;

            assert result.isMalformed() || result.isUnmappable();

            // Replace Malformed and Unmappable characters by numeric values as .Net does.
            for (int length = result.length(); length > 0 && charBuffer.remaining() > 0; length--)
                charBuffer.put((char) (byteBuffer.get() & 0xFF));
        }

        //JAVA: few not decoded bytes remained in the input buffer - store them for subsequent use.
        mRemaining = byteBuffer.remaining();
        if (mRemaining > 0)
        {
            mArray = new byte[mRemaining];
            byteBuffer.get(mArray);
        }

        //JAVA: same as above but remained bytes already consumed by the decoder and was cause of error.
        if (coderResult.isError())
        {
            int errorLength = coderResult.length();
            mArray = new byte[errorLength];
            byteBuffer.position(byteBuffer.position() - errorLength);
            byteBuffer.get(mArray);
            mRemaining = errorLength;
        }

        if (coderResult.isOverflow())
            throw new IllegalArgumentException("Decode Overflow: insufficient space in the 'chars' array.");

        return charsLength - charBuffer.remaining();
    }

    protected int mRemaining = 0;
    protected byte[] mArray;
}
