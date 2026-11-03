/**
 * User: sk Date: May 11, 2007
 */

package com.aspose.ms.System.Text;

import java.nio.charset.CharsetDecoder;

// A Decoder is used to decode a sequence of blocks of bytes into a
// sequence of blocks of characters. Following instantiation of a decoder,
// sequential blocks of bytes are converted into blocks of characters through
// calls to the GetChars method. The decoder maintains state between the
// conversions, allowing it to correctly decode byte sequences that span
// adjacent blocks.
//
// Instances of specific implementations of the Decoder abstract base
// class are typically obtained through calls to the GetDecoder method
// of Encoding objects.
public abstract class Decoder
{
    protected Decoder(Encoding encoding)
    {
        mEncoding = encoding;
        mCharsetDecoder = encoding.createCharsetDecoder();
    }

    public CharsetDecoder toJava()
    {
        return mCharsetDecoder;
    }

    public DecoderFallback getFallback()
    {
        return mFallback;
    }

    public DecoderFallbackBuffer getFallbackBuffer()
    {
        return mFallbackBuffer;
    }

    public void reset() {}

    public int getCharCount(byte[] bytes, int index, int count, boolean flush)
    {
        return getCharCount(bytes, index, count);
    }

    // Returns the number of characters the next call to GetChars will
    // produce if presented with the given range of bytes. The returned value
    // takes into account the state in which the decoder was left following the
    // last call to GetChars. The state of the decoder is not affected
    // by a call to this method.
    //
    public abstract int getCharCount(byte[] bytes, int index, int count);

    public int getChars(byte[] bytes, int byteIndex, int byteCount,
                        char[] chars, int charIndex, boolean flush)
    {
        return getChars(bytes, byteIndex, byteCount, chars, charIndex);
    }

    // Decodes a range of bytes in a byte array into a range of characters
    // in a character array. The method decodes byteCount bytes from
    // bytes starting at index byteIndex, storing the resulting
    // characters in chars starting at index charIndex. The
    // decoding takes into account the state in which the decoder was left
    // following the last call to this method.
    //
    // An exception occurs if the character array is not large enough to
    // hold the complete decoding of the bytes. The GetCharCount method
    // can be used to determine the exact number of characters that will be
    // produced for a given range of bytes. Alternatively, the
    // GetMaxCharCount method of the Encoding that produced this
    // decoder can be used to determine the maximum number of characters that
    // will be produced for a given number of bytes, regardless of the actual
    // byte values.
    //
    public abstract int getChars(byte[] bytes, int byteIndex, int byteCount,
                                 char[] chars, int charIndex);

    protected final Encoding mEncoding;
    protected final CharsetDecoder mCharsetDecoder;
    protected DecoderFallback mFallback = null;
    protected DecoderFallbackBuffer mFallbackBuffer = null;
}
