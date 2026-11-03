package com.aspose.ms.System.Text;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class SBCSCodePageEncoding extends BaseEncoding
{
    public SBCSCodePageEncoding(EncodingDataItem data)
    {
        super(data);
        byteToCharMapping = new HashMap<Integer, Integer>();
        charToByteMapping = new HashMap<Integer, Integer>();
    }

    public SBCSCodePageEncoding setByteToCharMapping(Map<Integer, Integer> replacements)
    {
        if (replacements != null)
            byteToCharMapping.putAll(replacements);
        else
            byteToCharMapping.clear();

        return this;
    }

    public SBCSCodePageEncoding setCharToByteMapping(Map<Integer, Integer> replacements)
    {
        if (replacements != null)
            charToByteMapping.putAll(replacements);
        else
            charToByteMapping.clear();

        return this;
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
                int cChar = chars.get();

                if (charToByteMapping.containsKey(cChar))
                    result.put(charToByteMapping.get(cChar).byteValue());

                else if (cChar < 256)
                    result.put((byte) cChar);
                else
                    result.put(encoder.replacement()[0]);
            }

            if (cr.isUnderflow() || cr.isMalformed() || cr.isOverflow())
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
        CharBuffer charBuffer = chars.get() != null ? CharBuffer.wrap(chars.get()) : CharBuffer.allocate(byteCount);
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, byteIndex, byteCount);
        CharsetDecoder decoder = createCharsetDecoder();

        while (byteBuffer.remaining() > 0 && charBuffer.remaining() > 0)
        {
            CoderResult result = decoder.decode(byteBuffer, charBuffer, false);

            if (result.isOverflow())
                break;

            if (result.isUnderflow())
                break;

            for (int length = result.length(); length > 0 && charBuffer.hasRemaining() && byteBuffer.hasRemaining(); length--)
            {
                int b = byteBuffer.get() & 0xFF;
                if (byteToCharMapping.containsKey(b))
                    charBuffer.put(Character.toChars(byteToCharMapping.get(b)));
                else
                    charBuffer.put((char) b);
            }
        }

        charBuffer.flip();

        if (chars.get() == null)
            chars.set(toArray(charBuffer));

        return charBuffer.limit();
    }

    protected Map<Integer, Integer> byteToCharMapping;
    protected Map<Integer, Integer> charToByteMapping;
}
