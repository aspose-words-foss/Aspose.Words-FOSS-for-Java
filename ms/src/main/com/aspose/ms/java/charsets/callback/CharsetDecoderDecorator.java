// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 17/11/2021 by Alexey Maslov

package com.aspose.ms.java.charsets.callback;

import com.aspose.ms.System.Text.DecoderFallback;
import com.aspose.ms.System.Text.DecoderFallbackBuffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.LinkedList;

/**
 * Extends (decorates) basic functionality of CharsetEncoder:
 * Invokes user-defined callback to handle unsupported CodePoints while decoding.
 */
public class CharsetDecoderDecorator extends CharsetDecoder
{
    private static class CharsetDecoderCallbackDecorator implements CharsetDecoderCallback
    {
        @Override
        public char[] getSurrogateChars(CharsetDecoderDecorator decoder, byte[] invalidBytes)
        {
            for(int i = 0; i < invalidBytes.length; i++)
            {
                byte[] b = new byte[1];
                b[0] = invalidBytes[i];

                if(!Character.isValidCodePoint(b[0]))
                {
                    mCallback.fallback(b, 0);

                    // rewind
                    while (mCallback.movePrevious());

                    mSurrogates.setLength(0);
                    while (mCallback.getRemaining() > 0)
                        mSurrogates.append(mCallback.getNextChar());
                }
                // valid bytes processing (sometimes valid bytes may be included in the invalidBytes array)
                // ignore zero value bytes
                else if(b[0] != 0)
                {
                    mSurrogates.append(Character.toChars(b[0]));
                }
            }

            if(mSurrogates.length() > 0)
                return mSurrogates.toString().toCharArray();

            return gEmptyArray;
        }

        public CharsetDecoderCallbackDecorator(DecoderFallback callback)
        {
            this.mCallback = callback.createFallbackBuffer();
            this.maxCharCount = callback.getMaxCharCount();
            this.mSurrogates = new StringBuilder();
        }

        private final DecoderFallbackBuffer mCallback;
        private final int maxCharCount;
        private final StringBuilder mSurrogates;
        private static final char[] gEmptyArray = new char[0];
    }

    public CharsetDecoderDecorator(CharsetDecoder decoder, CharsetDecoderCallback callback)
    {
        super(decoder.charset(), decoder.averageCharsPerByte(), decoder.maxCharsPerByte());
        if(callback == null)
            throw new IllegalArgumentException("Decorator cannot be created with callback = null");

        mCallback = callback;
        mUnderlyingDecoder = decoder;
        mUnderlyingDecoder.onMalformedInput(CodingErrorAction.REPORT);
        mUnderlyingDecoder.onUnmappableCharacter(CodingErrorAction.REPORT);
    }

    public CharsetDecoderDecorator(CharsetDecoder decoder, DecoderFallback callback)
    {
        this(decoder, new CharsetDecoderCallbackDecorator(callback));
    }

    @Override
    protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out)
    {
        // It needs to increase size of output buffer
        if (mCharsToWriteCount > out.remaining())
            return CoderResult.OVERFLOW;

        // Put all surrogate bytes into output buffer
        for (char[] chars : mCharsToWrite)
            out.put(chars);

        // All surrogate chars have been written into output buffer.
        mCharsToWrite.clear();
        mCharsToWriteCount = 0;

        while (in.remaining() > 0)
        {
            CoderResult cr = mUnderlyingDecoder.decode(in, out, false);
            if(cr.isOverflow())
                return cr;

            if(cr.isUnderflow())
            {
                if (in.remaining() > 1 && out.remaining() > 0)
                {
                    byte[] invalidBytes = new byte[in.remaining()];
                    in.get(invalidBytes);
                    char[] surrogateChars = mCallback.getSurrogateChars(this, invalidBytes);

                    if(surrogateChars.length > out.remaining())
                    {
                        mCharsToWrite.add(surrogateChars);
                        mCharsToWriteCount += surrogateChars.length;
                        return CoderResult.OVERFLOW;
                    }

                    out.put(surrogateChars);

                    continue;
                }
                else
                {
                    return cr;
                }
            }

            // An unmapped or malformed bytes that cannot be decoded using a given charset
            byte[] invalidBytes = new byte[cr.length()];
            in.get(invalidBytes);
            char[] surrogateChars = mCallback.getSurrogateChars(this, invalidBytes);

            if(surrogateChars.length > out.remaining())
            {
                mCharsToWrite.add(surrogateChars);
                mCharsToWriteCount += surrogateChars.length;
                return CoderResult.OVERFLOW;
            }

            out.put(surrogateChars);
        }
        // This method will be probably invoked again with further input.
        return CoderResult.UNDERFLOW;
    }

    /// <summary>
    /// List of char arrays containing decoded surrogate chars to be written to output buffer.
    /// </summary>
    private final LinkedList<char[]> mCharsToWrite = new LinkedList<char[]>();

    /// <summary>
    /// Total number of decoded surrogate chars to be written to output buffer.
    /// </summary>
    private int mCharsToWriteCount = 0;

    private final CharsetDecoder mUnderlyingDecoder;
    private final CharsetDecoderCallback mCallback;
}
