package com.aspose.ms.System.Text;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.concurrent.atomic.AtomicReference;

class BaseGBEncoding extends BaseEncoding
{
    public BaseGBEncoding(EncodingDataItem data)
    {
        super(data);
    }

    @Override
    public int getCharsCore(byte[] bytes, int byteIndex, int byteCount, AtomicReference<char[]> chars, int charIndex)
    {
        CharBuffer charBuffer = chars.get() != null ? CharBuffer.wrap(chars.get()) : CharBuffer.allocate(byteCount);
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, byteIndex, byteCount);
        CharsetDecoder decoder = createCharsetDecoder();
        decoder.reset();

        while (byteBuffer.remaining() > 0 && charBuffer.remaining() > 0)
        {
            CoderResult result = decoder.decode(byteBuffer, charBuffer, false);

            if (result.isOverflow())
                break;

            if (result.isUnderflow())
            {
                if (byteBuffer.hasRemaining() && charBuffer.hasRemaining())
                {
                    byteBuffer.get();

                    if (byteBuffer.hasRemaining())
                        byteBuffer.get();

                    charBuffer.put(decoder.replacement());
                }
                break;
            }

            for (int length = result.length(); length > 0 && charBuffer.hasRemaining() && byteBuffer.hasRemaining(); length--)
            {
                //charBuffer.put((char) (byteBuffer.get() & 0xFF));
                byteBuffer.get();
                charBuffer.put(decoder.replacement());
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
}
