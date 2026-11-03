package com.aspose.ms.System.Text;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.concurrent.atomic.AtomicReference;

public class UTF16LeEncoding extends BaseUnicodeEncoding
{
    public UTF16LeEncoding()
    {
        this(true);
    }

    public UTF16LeEncoding(boolean bom)
    {
        super(new EncodingDataItem(1200, "utf-16", "utf-16", "utf-16", "utf-16", 1200, "UTF-16LE"), bom);
    }

    @Override
    public Decoder getDecoder()
    {
        return new UTF16LEDecoder(this);
    }

    @Override
    public byte[] getPreamble()
    {
        return mUseBOM ? Utf16LEPreamble : gNoPreamble;
    }

    protected static final byte[] Utf16LEPreamble = new byte[]{(byte) 0xff, (byte) 0xfe};

    @Override
    public int getCharsCore(byte[] bytes, int byteIndex, int byteCount, AtomicReference<char[]> chars, int charIndex)
    {
        CharBuffer charBuffer = chars.get() != null ? CharBuffer.wrap(chars.get()) : CharBuffer.allocate(byteCount);

        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, byteIndex, byteCount);

        //JAVA: don't use java.nio.charset.CharsetDecoder.decode(ByteBuffer in) because
        // it can't properly decode single one-byte symbol of multibyte encodings (like Shift-Jis).
        // so let's do all by hands.
        CharsetDecoder decoder = createCharsetDecoder();
        decoder.reset();

        // FIX TestLayoutRun.testJira8480(). Handle unicode in special way to emulate .NET behaviour.
        while (byteBuffer.remaining() > 0 && charBuffer.remaining() > 0)
        {
            CoderResult result = decoder.decode(byteBuffer, charBuffer, false);

            // Strange "non-optimized" code. But after optimization I get problems with
            // multithreading tests.

            // All chars are filled
            if (result.isOverflow())
                break;

            // If result.isUnderflow(), byte buffer can still contain malformed bytes as practice shows.
            if (result.isUnderflow())
            {
                // Skip pair of malformed bytes and substitute the default unicode replacement
                if (byteBuffer.remaining() > 1 && charBuffer.remaining() > 0)
                {
                    byteBuffer.get();
                    byteBuffer.get();

                    charBuffer.put(decoder.replacement());
                }
                else
                    break;
            }
            else
            {
                // Skip pair of malformed bytes and substitute the default unicode replacement

                if (byteBuffer.remaining() > 1 && charBuffer.remaining() > 0)
                {
                    byteBuffer.get();
                    byteBuffer.get();

                    charBuffer.put(decoder.replacement());
                }
            }
        }

        charBuffer.flip();

        if (chars.get() == null)
        {
            char[] newChars = new char[charBuffer.limit()];
            charBuffer.get(newChars);
            chars.set(newChars);
        }

        return byteBuffer.position();
    }

    private static class UTF16LEDecoder extends CyclicDecoder
    {
        UTF16LEDecoder(Encoding encoding)
        {
            super(encoding);
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

            // FIX TestGoldTxtOther.testJiraJ767(). Handle unicode in special way to emulate .NET behaviour.

            while (byteBuffer.remaining() > 0 && charBuffer.remaining() > 0)
            {
                //in java we should set param endOfInput to true for the last decode,
                //but we provide exact size of input bytes so can leave without this.
                CoderResult result = mCharsetDecoder.decode(byteBuffer, charBuffer, false);

                // All chars are filled
                if (result.isOverflow())
                    break;

                // If result.isUnderflow(), byte buffer can still contain malformed bytes as practice shows.
                if (result.isUnderflow())
                {
                    // Skip pair of malformed bytes and substitute the default unicode replacement
                    if (byteBuffer.remaining() > 1 && charBuffer.remaining() > 0)
                    {
                        byteBuffer.get();
                        byteBuffer.get();

                        charBuffer.put(mCharsetDecoder.replacement());
                    }
                    else
                    {
                        break;
                    }
                }
                else
                {
                    // Skip pair of malformed bytes and substitute the default unicode replacement
                    if (byteBuffer.remaining() > 1 && charBuffer.remaining() > 0)
                    {
                        byteBuffer.get();
                        byteBuffer.get();

                        charBuffer.put(mCharsetDecoder.replacement());
                    }
                }
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
    }
}
