package com.aspose.ms.java.charsets.dos720charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.Arrays;


/**
 * SingleByteCharset encodes and decodes single-byte charsets.
 */
public abstract class SingleByteCharset extends Charset
{
    @Override
    public boolean contains(Charset charset)
    {
        return this.getClass().isInstance(charset);
    }

    @Override
    public CharsetDecoder newDecoder()
    {
        return new Decoder(this);
    }

    @Override
    public CharsetEncoder newEncoder()
    {
        return new Encoder(this);
    }

    /**
     * Creates an inverse lookup table for the given table.
     */
    public static int[][] createInverseLookupTable(int[] chars)
    {
        int[][] tables = new int[gTableSize][];
        for (int i = 0; i < gTableSize; i++)
        {
            int c = chars[i];
            if (c > -1)
                updateInverseLookupTable(tables, c, i);
        }
        return tables;
    }

    /**
     * Replace or add an inverse lookup table with an additional mapping
     */
    private static int[][] updateInverseLookupTable(int[][] tables, int c, int b)
    {
        final int high = (c >>> 8) & 0xFF;
        final int low = c & 0xFF;
        int[] table = tables[high];

        if (table != null)
            table[low] = b;

        else if (b > -1)
        {
            table = initializeLookupTable();
            tables[high] = table;
            table[low] = b;
        }
        return tables;
    }

    private static int[] initializeLookupTable()
    {
        int[] table = new int[gTableSize];
        Arrays.fill(table, -1);
        return table;
    }

    /**
     * Encodes the charset using the lookup table.
     */
    protected class Encoder extends CharsetEncoder
    {
        /**
         * Encodes the given characters into bytes.
         */
        @Override
        protected CoderResult encodeLoop(CharBuffer in, ByteBuffer out)
        {
            final int[][] charToByteTable = mCharToByte;
            int remainingOut = out.remaining();

            while (in.hasRemaining())
            {
                if (remainingOut-- < 1)
                    return CoderResult.OVERFLOW;

                int ch = in.get();
                int[] table = charToByteTable[ch >>> 8];
                int b = (table != null ? table[ch & 0xFF] : -1);
                if (b == -1)
                {
                    in.position(in.position() - 1);
                    return CoderResult.unmappableForLength(1);
                }
                out.put((byte) (b & 0xFF));
            }
            return CoderResult.UNDERFLOW;
        }

        protected Encoder(Charset charset)
        {
            super(charset, 1f, 1f);
        }

    }

    /**
     * Decodes the charset using the lookup table.
     */
    protected class Decoder extends CharsetDecoder
    {
        /**
         * Decodes the given characters into bytes.
         */
        @Override
        protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out)
        {
            final int[] byteToCharTable = mByteToChar;
            int remainingOut = out.remaining();

            while (in.hasRemaining())
            {
                if (remainingOut-- < 1)
                    return CoderResult.OVERFLOW;

                int ch = byteToCharTable[in.get() & 0xFF];
                if (ch == -1)
                {
                    in.position(in.position() - 1);
                    return CoderResult.malformedForLength(1);
                }
                out.put((char) ch);
            }
            return CoderResult.UNDERFLOW;
        }

        protected Decoder(Charset charset)
        {
            super(charset, 1f, 1f);
        }
    }

    private static final int gTableSize = 256;

    /**
     * Creates a new charset with the given name and aliases.
     */
    protected SingleByteCharset(String canonicalName, String[] aliases, int[] byteToChar, int[][] charToByte)
    {
        super(canonicalName, aliases);
        this.mByteToChar = byteToChar;
        this.mCharToByte = charToByte;
    }

    private final int[] mByteToChar;
    private final int[][] mCharToByte;
}
