package com.aspose.ms.System.Text;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.concurrent.atomic.AtomicReference;

class BaseUnicodeEncoding extends BaseEncoding
{
    public BaseUnicodeEncoding(EncodingDataItem data, boolean bom)
    {
        super(data);
        mUseBOM = bom;
    }

    public BaseUnicodeEncoding(EncodingDataItem data, Charset charset)
    {
        this(data, charset, false);
    }

    public BaseUnicodeEncoding(EncodingDataItem data, Charset charset, boolean bom)
    {
        super(data, charset);
        mUseBOM = bom;
    }

    @Override
    public int getBytesCore(char[] chars, int charIndex, int charCount, AtomicReference<byte[]> bytes, int byteIndex)
    {
        return getBytesCore(chars, charIndex, charCount, bytes, byteIndex, Integer.MAX_VALUE);
    }

    @Override
    public int getBytesCore(char[] chars, int charIndex, int charCount, AtomicReference<byte[]> bytes, int byteIndex, int byteCount)
    {
        CharsetEncoder encoder = createCharsetEncoder();
        CharBuffer charBuffer = CharBuffer.wrap(chars, charIndex, charCount);

        int resultCapacity = (int) (charBuffer.remaining() * encoder.averageBytesPerChar());

        ByteBuffer byteBuffer = bytes.get() != null
                ? ByteBuffer.wrap(bytes.get(), byteIndex, Math.min(bytes.get().length - byteIndex, byteCount))
                : ByteBuffer.allocate(resultCapacity);

        while (charBuffer.hasRemaining())
        {
            CoderResult coderResult = encoder.encode(charBuffer, byteBuffer, false);
            if (coderResult.isUnderflow())
                break;

            if (coderResult.isOverflow() && charBuffer.hasRemaining())
            {
                resultCapacity = 2 * resultCapacity + 1;    // Ensure progress; n might be 0!
                ByteBuffer o = ByteBuffer.allocate(resultCapacity);
                byteBuffer.flip();
                o.put(byteBuffer);
                byteBuffer = o;
                continue;
            }

            if (coderResult.isUnmappable())
            {
                for (int i = 0; i < coderResult.length(); i++)
                {
                    charBuffer.get();
                    byteBuffer.put(encoder.replacement());
                }
            }

        }
        byteBuffer.flip();

        if (bytes.get() == null)
            bytes.set(toArray(byteBuffer));

        return byteBuffer.limit();

    }

    @Override
    protected CharsetDecoder createCharsetDecoder()
    {
        CharsetDecoder decoder = super.createCharsetDecoder();
        decoder.replaceWith("�");
        return decoder;
    }

    protected boolean mUseBOM;
}
