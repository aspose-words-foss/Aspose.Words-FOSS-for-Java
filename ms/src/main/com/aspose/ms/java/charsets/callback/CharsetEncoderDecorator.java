// Copyright (c) 2001-2018 Aspose Pty Ltd. All Rights Reserved.
// 01/06/2013 by Pavel Gorbunov

package com.aspose.ms.java.charsets.callback;

import com.aspose.ms.System.Text.EncoderFallback;
import com.aspose.ms.System.Text.EncoderFallbackBuffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.LinkedList;

/**
 * This encoder invokes user-defined callback to handle unsupported characters while encoding.
 * It extends (decorates) basic functionality of CharsetEncoder.
 */
public class CharsetEncoderDecorator extends CharsetEncoder
{
    private static class CharsetEncoderCallbackDecorator implements CharsetEncoderCallback
    {
        @Override
        public byte[] getSurrogateBytes(CharsetEncoderDecorator encoder, char[] invalidChars)
        {
            for (int i = 0; i < invalidChars.length; i++)
            {
                char c1 = invalidChars[i];
                if (Character.isHighSurrogate(c1) && ++i < invalidChars.length)
                {
                    char c2 = invalidChars[i];
                    if (Character.isLowSurrogate(c2))
                    {
                        mCallback.fallback(c1, c2, 0);
                        continue;
                    }
                }
                mCallback.fallback(c1, 0);
            }

            // rewind
            while (mCallback.movePrevious()) ;

            mSurrogates.setLength(0);
            while (mCallback.getRemaining() > 0)
                mSurrogates.append(mCallback.getNextChar());

            if (mSurrogates.length() > 0)
                return encoder.charset().encode(mSurrogates.toString()).array();
            else
                return gEmptyArray;
        }

        public CharsetEncoderCallbackDecorator(EncoderFallback callback)
        {
            this.mCallback = callback.createFallbackBuffer();
            this.maxCharCount = callback.getMaxCharCount();
            this.mSurrogates = new StringBuilder();
        }

        private final EncoderFallbackBuffer mCallback;
        private final int maxCharCount;
        private final StringBuilder mSurrogates;
        private static final byte[] gEmptyArray = new byte[0];
    }

    public CharsetEncoderDecorator(CharsetEncoder encoder, CharsetEncoderCallback callback)
    {
        super(encoder.charset(), encoder.averageBytesPerChar(), encoder.maxBytesPerChar(), encoder.replacement());
        if (callback == null)
            throw new IllegalArgumentException("Decorator cannot be created with callback = null");

        mCallback = callback;
        mUnderlyingEncoder = encoder;
        mUnderlyingEncoder.onMalformedInput(CodingErrorAction.REPORT);
        mUnderlyingEncoder.onUnmappableCharacter(CodingErrorAction.REPORT);
    }

    public CharsetEncoderDecorator(CharsetEncoder encoder, EncoderFallback callback)
    {
        this(encoder, new CharsetEncoderCallbackDecorator(callback));
    }

    @Override
    protected CoderResult encodeLoop(CharBuffer in, ByteBuffer out)
    {
        // It needs to increase size of output buffer
        if (mBytesToWriteCount > out.remaining())
            return CoderResult.OVERFLOW;

        // Put all surrogate bytes into output buffer
        for (byte[] bytes : mBytesToWrite)
            out.put(bytes);

        // All surrogate bytes have been written into output buffer.
        mBytesToWrite.clear();
        mBytesToWriteCount = 0;

        while (in.remaining() > 0)
        {
            CoderResult cr = mUnderlyingEncoder.encode(in, out, false);
            if (cr.isUnderflow() || cr.isOverflow())
                return cr;

            // An unmapped or malformed characters that cannot be encoded using a given charset
            char[] invalidChars = new char[cr.length()];
            in.get(invalidChars);
            byte[] surrogateBytes = mCallback.getSurrogateBytes(this, invalidChars);

            if (surrogateBytes.length > out.remaining())
            {
                mBytesToWrite.add(surrogateBytes);
                mBytesToWriteCount += surrogateBytes.length;
                return CoderResult.OVERFLOW;
            }

            out.put(surrogateBytes);
        }
        // This method will be probably invoked again with further input.
        return CoderResult.UNDERFLOW;
    }

    /// <summary>
    /// List of byte arrays containing encoded surrogate bytes to be written to output buffer.
    /// </summary>
    private final LinkedList<byte[]> mBytesToWrite = new LinkedList<byte[]>();

    /// <summary>
    /// Total number of encoded surrogate bytes to be written to output buffer.
    /// </summary>
    private int mBytesToWriteCount = 0;

    private final CharsetEncoder mUnderlyingEncoder;
    private final CharsetEncoderCallback mCallback;
}


