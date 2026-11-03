// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//18/10/2010 by Roman Korchagin

package com.aspose.ms.System.Text;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.concurrent.atomic.AtomicReference;

public class UTF8Encoding extends BaseUnicodeEncoding
{
    public UTF8Encoding()
    {
        this(true);
    }

    public UTF8Encoding(boolean useByteOrderMark)
    {
        super(new EncodingDataItem(65001, "utf-8", "utf-8", "utf-8", "utf-8", 1200), useByteOrderMark);
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

            // Excellent. All bytes are decoded or all chars are filled.
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

    @Override
    public byte[] getPreamble()
    {
        return mUseBOM ? gUtf8Preamble : gNoPreamble;
    }

    private static final byte[] gUtf8Preamble = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
}
