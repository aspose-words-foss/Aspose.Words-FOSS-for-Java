package com.aspose.ms.System.Text;

import com.aspose.words.ref.RefBoolean;
import com.aspose.words.ref.RefInt;

import java.nio.charset.CharsetEncoder;

// An Encoder is used to encode a sequence of blocks of characters into
// a sequence of blocks of bytes. Following instantiation of an encoder,
// sequential blocks of characters are converted into blocks of bytes through
// calls to the GetBytes method. The encoder maintains state between the
// conversions, allowing it to correctly encode character sequences that span
// adjacent blocks.
//
// Instances of specific implementations of the Encoder abstract base
// class are typically obtained through calls to the GetEncoder method
// of Encoding objects.
public abstract class Encoder
{
    protected Encoder(Encoding encoding)
    {
        mEncoding = encoding;
        mCharsetEncoder = encoding.createCharsetEncoder();
    }

    public CharsetEncoder toJava() {return mCharsetEncoder;}

    public EncoderFallback getFallback()
    {
        return mFallback;
    }

    public EncoderFallbackBuffer getFallbackBuffer()
    {
        return mFallbackBuffer;
    }

    // Reset the Encoder
    //
    // Normally if we call GetBytes() and an error is thrown we don't change the state of the encoder.  This
    // would allow the caller to correct the error condition and try again (such as if they need a bigger buffer.)
    //
    // If the caller doesn't want to try again after GetBytes() throws an error, then they need to call Reset().
    //
    // Virtual implimentation has to call GetBytes with flush and a big enough buffer to clear a 0 char string
    // We avoid GetMaxByteCount() because a) we can't call the base encoder and b) it might be really big.
    public void reset()
    {

    }

    // Returns the number of bytes the next call to GetBytes will
    // produce if presented with the given range of characters and the given
    // value of the flush parameter. The returned value takes into
    // account the state in which the encoder was left following the last call
    // to GetBytes. The state of the encoder is not affected by a call
    // to this method.
    //
    public abstract int getByteCount(char[] chars, int index, int count, boolean flush);

    // Encodes a range of characters in a character array into a range of bytes
    // in a byte array. The method encodes charCount characters from
    // chars starting at index charIndex, storing the resulting
    // bytes in bytes starting at index byteIndex. The encoding
    // takes into account the state in which the encoder was left following the
    // last call to this method. The flush parameter indicates whether
    // the encoder should flush any shift-states and partial characters at the
    // end of the conversion. To ensure correct termination of a sequence of
    // blocks of encoded bytes, the last call to GetBytes should specify
    // a value of true for the flush parameter.
    //
    // An exception occurs if the byte array is not large enough to hold the
    // complete encoding of the characters. The GetByteCount method can
    // be used to determine the exact number of bytes that will be produced for
    // a given range of characters. Alternatively, the GetMaxByteCount
    // method of the Encoding that produced this encoder can be used to
    // determine the maximum number of bytes that will be produced for a given
    // number of characters, regardless of the actual character values.
    //
    public abstract int getBytes(char[] chars, int charIndex, int charCount,
                                 byte[] bytes, int byteIndex, boolean flush);

    public abstract int getBytes(char[] chars, int charIndex, int charCount,
                                 byte[] bytes, int byteIndex, int byteCount, boolean flush);

    public void convert(char[] chars, int charIndex, int charCount, byte[] buffer, int byteIndex, int byteCount, boolean flush, RefInt referenceToCharsUsed, RefInt referenceToBytesUsed, RefBoolean referenceToCompleted)
    {
        int byteUsed = getBytes(chars, charIndex, charCount, buffer, byteIndex, byteCount, flush);
        referenceToCharsUsed.set(mCharUsed);
        referenceToBytesUsed.set(byteUsed);
        referenceToCompleted.set(byteUsed > 0);
    }

    protected final Encoding mEncoding;
    protected int mCharUsed = 0;
    protected final CharsetEncoder mCharsetEncoder;
    protected EncoderFallback mFallback = null;
    protected EncoderFallbackBuffer mFallbackBuffer = null;

}
