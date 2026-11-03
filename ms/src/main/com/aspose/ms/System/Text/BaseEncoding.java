package com.aspose.ms.System.Text;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.concurrent.atomic.AtomicReference;

class BaseEncoding extends Encoding
{
    public BaseEncoding(EncodingDataItem data, Charset charset)
    {
        super(data, charset);
    }

    public BaseEncoding(EncodingDataItem data)
    {
        super(data);
    }

    @Override
    public Decoder getDecoder()
    {
        return new CyclicDecoder(this);
    }

    @Override
    public Encoder getEncoder()
    {
        return new CyclicEncoder(this);
    }

    @Override
    public int getByteCount(char[] chars, int index, int count)
    {
        // NOTE: Unfortunately, we have to just decode the given chars.
        byte[] bytes = getBytes(chars, index, count);
        return bytes.length;
    }

    @Override
    public int getCharCount(byte[] bytes, int index, int count)
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int getMaxByteCount(int charCount)
    {
        return (int) (charCount * mMaxBytesPerChar);
    }

    @Override
    public int getMaxCharCount(int byteCount)
    {
        return (int) (byteCount * mMaxCharsPerByte);
    }

    @Override
    public int getBytesCore(char[] charsArr, int charIndex, int charCount, AtomicReference<byte[]> bytes, int byteIndex)
    {
        return getBytesCore(charsArr, charIndex, charCount, bytes, byteIndex, Integer.MAX_VALUE);
    }

    @Override
    public int getBytesCore(char[] charsArr, int charIndex, int charCount, AtomicReference<byte[]> bytes, int byteIndex, int byteCount)
    {
        CharsetEncoder encoder = createCharsetEncoder();

        CharBuffer chars = CharBuffer.wrap(charsArr);

        int resultCapacity = (int) (chars.remaining() * encoder.averageBytesPerChar());
        ByteBuffer result = bytes.get() != null
                ? ByteBuffer.wrap(bytes.get(), byteIndex, Math.min(bytes.get().length - byteIndex, byteCount))
                : ByteBuffer.allocate(resultCapacity);

        while (chars.hasRemaining())
        {
            CoderResult cr = encoder.encode(chars, result, false);

            if (cr.isUnmappable())
            {
                chars.get();
                result.put(encoder.replacement());
            }

            if (cr.isOverflow() && chars.hasRemaining())
            {
                resultCapacity = 2 * resultCapacity + 1;    // Ensure progress; n might be 0!
                ByteBuffer o = ByteBuffer.allocate(resultCapacity);
                result.flip();
                o.put(result);
                result = o;
                continue;
            }

            if (cr.isUnderflow() || cr.isMalformed())
                break;

        }

        result.flip();
        if (bytes.get() == null)
            bytes.set(toArray(result));

        return result.limit();
    }

    @Override
    public int getCharsCore(byte[] bytes, int byteIndex, int byteCount, AtomicReference<char[]> chars, int charIndex)
    {
        CharBuffer charBuffer = chars.get() != null
                ? CharBuffer.wrap(chars.get())
                : CharBuffer.allocate(byteCount);

        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, byteIndex, byteCount);
        CharsetDecoder decoder = createCharsetDecoder();
        decoder.reset();
        while (byteBuffer.remaining() > 0 && charBuffer.remaining() > 0)
        {
            CoderResult result = decoder.decode(byteBuffer, charBuffer, false);

            // Excellent. All bytes are decoded or all chars are filled.
            if (result.isOverflow())
                break;

            if (result.isUnderflow())
                break;

            // Replace Malformed and Unmappable characters by numeric values as .Net does.
            // WORDSANDROID-184 check if byteBuffer still has bytes
            for (int length = result.length(); length > 0 && charBuffer.hasRemaining() && byteBuffer.hasRemaining(); length--)
            {
                // charBuffer.put((char) (byteBuffer.get() & 0xFF));
                int unmappable = (byteBuffer.get() & 0xFF);
                charBuffer.put(decoder.replacement());
            }
        }

        charBuffer.flip();

        if (chars.get() == null)
            chars.set(toArray(charBuffer));

        return charBuffer.limit();
    }
}
