// Parts of this file are a Java port, made by Aspose, of .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/text/encoding.cs
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.Text;

import com.aspose.ms.System.msString;
import com.aspose.ms.java.IO.JavaThreadLocalCoders;
import com.aspose.ms.java.charsets.callback.CharsetDecoderDecorator;
import com.aspose.ms.java.charsets.callback.CharsetEncoderDecorator;
import com.aspose.ms.java.logging.Log;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Encoding implements Cloneable
{
    protected Encoding(EncodingDataItem data)
    {
        this(data, tryCreateFromAlias(data));
    }

    protected Encoding(EncodingDataItem data, Charset charset)
    {
        this.mData = data;
        this.mCharset = charset;
        this.isSingleByte = checkIsSingleByteEncoding();
        this.mMaxCharsPerByte = createCharsetDecoder().maxCharsPerByte();
        this.mMaxBytesPerChar = createCharsetEncoder().maxBytesPerChar();
    }

    // WORDSJAVA-2292 Returns the windows code page that most closely corresponds to this encoding.
    public int getWindowsCodePage()
    {
        return mData.WindowsCodePage;
    }

    public byte[] getPreamble()
    {
        return gNoPreamble;
    }

    // Returns the name for this encoding that can be used with mail agent body tags.
    // If the encoding may not be used, the string is empty.
    public String getHeaderName()
    {
        return mData.HeaderName;
    }

    // Returns a Decoder object for this encoding. The returned object
    // can be used to decode a sequence of bytes into a sequence of characters.
    // Contrary to the GetChars family of methods, a Decoder can
    // convert partial sequences of bytes into partial sequences of characters
    // by maintaining the appropriate state between the conversions.
    //
    // This default implementation returns a Decoder that simply
    // forwards calls to the GetCharCount and GetChars methods to
    // the corresponding methods of this encoding. Encodings that require state
    // to be maintained between successive conversions should override this
    // method and return an instance of an appropriate Decoder
    // implementation.
    public Decoder getDecoder()
    {
        return new DefaultDecoder(this);
    }

    // Returns an Encoder object for this encoding. The returned object
    // can be used to encode a sequence of characters into a sequence of bytes.
    // Contrary to the GetBytes family of methods, an Encoder can
    // convert partial sequences of characters into partial sequences of bytes
    // by maintaining the appropriate state between the conversions.
    //
    // This default implementation returns an Encoder that simply
    // forwards calls to the GetByteCount and GetBytes methods to
    // the corresponding methods of this encoding. Encodings that require state
    // to be maintained between successive conversions should override this
    // method and return an instance of an appropriate Encoder
    // implementation.
    public Encoder getEncoder()
    {
        return new DefaultEncoder(this);
    }

    /**
     * Analog of .Net System.Text.Encoding.WebName property but (NOTE!)
     * returns java Web Name that don't always match .Net Encoding.WebName property.
     * <p>
     * Returns the IANA-registered name for this encoding.
     * <p>
     * Note: doesn't work with our current implementation of utf7 CharsetProvider (that doesn't
     * registered in META-INF/service folder). See note to toJava(). - i.e. returns the
     * name which doesn't registered in java charset cache. toJava() should be used instead to
     * get the Charset, not just a name.
     */
    // Returns the array of IANA-registered names for this encoding.  If there is an
    // IANA preferred name, it is the first name in the array.
    public String getWebName()
    {
        // FIX WORDSJAVA-780. CodePageName in Java always matches WebName in .NET
        return mData.WebName;
    }

    public boolean isSingleByte()
    {
        return isSingleByte;
    }

    public int getByteCount(String s)
    {
        char[] chars = s.toCharArray();
        return getByteCount(chars, 0, chars.length);
    }

    // Returns the number of bytes required to encode a range of characters in
    // a character array.
    public abstract int getByteCount(char[] chars, int index, int count);

    // Returns a byte array containing the encoded representation of the given
    // string.
    public byte[] getBytes(String s)
    {
        return getBytes(s.toCharArray(), 0, s.length());
    }

    // Returns a byte array containing the encoded representation of a range
    // of characters in a character array.
    public byte[] getBytes(char[] chars, int index, int count)
    {
        AtomicReference<byte[]> result = new AtomicReference<byte[]>();
        getBytesCore(chars, index, count, result, 0);
        return result.get();
    }

    protected abstract int getBytesCore(char[] chars, int charIndex, int charCount, AtomicReference<byte[]> result, int byteIndex);

    protected abstract int getBytesCore(char[] chars, int charIndex, int charCount, AtomicReference<byte[]> result, int byteIndex, int byteCount);

    public int getBytes(String s, int charIndex, int charCount, byte[] bytes, int byteIndex)
    {
        return getBytes(s.toCharArray(), charIndex, charCount, bytes, byteIndex);
    }

    // Encodes a range of characters in a character array into a range of bytes
    // in a byte array. An exception occurs if the byte array is not large
    // enough to hold the complete encoding of the characters. The
    // GetByteCount method can be used to determine the exact number of
    // bytes that will be produced for a given range of characters.
    // Alternatively, the GetMaxByteCount method can be used to
    // determine the maximum number of bytes that will be produced for a given
    // number of characters, regardless of the actual character values.
    public int getBytes(char[] chars, int charIndex, int charCount,
                        byte[] bytes, int byteIndex)
    {
        return getBytes(chars, charIndex, charCount, bytes, byteIndex,bytes.length - byteIndex);
    }

    public int getBytes(char[] chars, int charIndex, int charCount,
                        byte[] bytes, int byteIndex, int byteCount)
    {
        AtomicReference<byte[]> result = new AtomicReference<byte[]>();
        result.set(bytes);
        return getBytesCore(chars, charIndex, charCount, result, byteIndex, byteCount);
    }

    // Returns the number of characters produced by decoding the given byte
    // array.
    //
    public int getCharCount(byte[] bytes)
    {
        if (bytes == null)
            throw new IllegalArgumentException("bytes cannot be null");

        return getCharCount(bytes, 0, bytes.length);
    }

    // Returns the number of characters produced by decoding a range of bytes
    // in a byte array.
    //
    public abstract int getCharCount(byte[] bytes, int index, int count);

    // Returns a string containing the decoded representation of a given byte
    // array.
    public String getString(byte[] bytes)
    {
        if (bytes == null)
            throw new IllegalArgumentException("bytes cannot be null");

        return getString(bytes, 0, bytes.length);
    }

    //  public abstract byte[] getBytesCore(char[] chars, int charIndex, int charCount, int byteIndex);

    // Returns a string containing the decoded representation of a range of
    // bytes in a byte array.
    //
    // Internally we override this for performance
    public String getString(byte[] bytes, int index, int count)
    {
        return new String(getChars(bytes, index, count));
    }

    // Returns a character array containing the decoded representation of a
    // range of bytes in a byte array.
    public char[] getChars(byte[] bytes, int index, int count)
    {
        AtomicReference<char[]> result = new AtomicReference<>();
        getCharsCore(bytes, index, count, result, 0);
        return result.get();
    }

    protected abstract int getCharsCore(byte[] bytes, int byteIndex, int byteCount, AtomicReference<char[]> chars, int charIndex);

    public int getChars(byte[] bytes, char[] chars)
    {
        return getChars(bytes, 0, bytes.length, chars, 0);
    }

    // Decodes a range of bytes in a byte array into a range of characters in a
    // character array. An exception occurs if the character array is not large
    // enough to hold the complete decoding of the bytes. The
    // GetCharCount method can be used to determine the exact number of
    // characters that will be produced for a given range of bytes.
    // Alternatively, the GetMaxCharCount method can be used to
    // determine the maximum number of characterss that will be produced for a
    // given number of bytes, regardless of the actual byte values.
    public int getChars(byte[] bytes, int byteIndex, int byteCount, char[] chars, int charIndex)
    {
        AtomicReference<char[]> charsRef = new AtomicReference<>();
        charsRef.set(chars);
        return getCharsCore(bytes, byteIndex, byteCount, charsRef, charIndex);
    }

    // Returns the maximum number of bytes required to encode a given number of
    // characters. This method can be used to determine an appropriate buffer
    // size for byte arrays passed to the GetBytes method of this
    // encoding or the GetBytes method of an Encoder for this
    // encoding. All encodings must guarantee that no buffer overflow
    // exceptions will occur if buffers are sized according to the results of
    // this method.
    //
    // WARNING: If you're using something besides the default replacement encoder fallback,
    // then you could have more bytes than this returned from an actual call to GetBytes().
    public abstract int getMaxByteCount(int charCount);

    // Returns the maximum number of characters produced by decoding a given
    // number of bytes. This method can be used to determine an appropriate
    // buffer size for character arrays passed to the GetChars method of
    // this encoding or the GetChars method of a Decoder for this
    // encoding. All encodings must guarantee that no buffer overflow
    // exceptions will occur if buffers are sized according to the results of
    // this method.
    public abstract int getMaxCharCount(int byteCount);

    public Charset toJava()
    {
        return mCharset;
    }

    public Encoding deepClone()
    {
        return (Encoding) clone();
    }

    public void setEncoderFallback(EncoderFallback encoderFallback)
    {
        mEncoderFallback = encoderFallback;
    }

    public void setDecoderFallback(DecoderFallback decoderFallback)
    {
        mDecoderFallback = decoderFallback;
    }

    @Override
    public Encoding clone()
    {
        try
        {
            Encoding clone = (Encoding) super.clone();
            clone.setEncoderFallback(null);
            clone.setDecoderFallback(null);
            return clone;
        }
        catch (CloneNotSupportedException e)
        {
            Log.error(e);
        }

        throw new RuntimeException("Cannot clone the encoding " + getCodePage());
    }

    // Returns the code page identifier of this encoding. The returned value is
    // an integer between 0 and 65535 if the encoding has a code page
    // identifier, or -1 if the encoding does not represent a code page.
    public int getCodePage()
    {
        return mData.CodePage;
    }

    // method is used internally to map codePage to encoding's name
    String getEncodingInfoName()
    {
        return mData.EncodingInfoName;
    }

    //SPEED: using System.arraycopy() instead of byteBuffer.get()
    protected byte[] toArray(ByteBuffer byteBuffer)
    {
        byte[] array = byteBuffer.array();
        int length = byteBuffer.remaining();

        if (length == array.length)
            return array;

        else
        {
            byte[] bytes = new byte[length];
            System.arraycopy(array, 0, bytes, 0, length);
            return bytes;
        }
    }

    protected char[] toArray(CharBuffer buffer)
    {
        char[] array = buffer.array();
        int length = buffer.remaining();

        if (length == array.length)
            return array;

        else
        {
            char[] chars = new char[length];
            System.arraycopy(array, 0, chars, 0, length);
            return chars;
        }
    }

    //Western European (Windows) !Note: BodyName is 'iso-8859-1'
    public static Encoding getWINDOWS_1252()
    {
        return getEncoding(EncodingProvider.CODE_PAGE_1252);
    }

    public static Encoding getEncoding(int codePage)
    {
        Encoding result = gEncodings.get(codePage);
        if (result != null)
            return result;

        if (codePage < 0)
            throw new IllegalArgumentException("Code page cannot be less than 0, but codePage=" + codePage);

        result = EncodingProvider.getEncodingFromProvider(codePage);
        if (codePage == 0)
            result = EncodingProvider.getEncodingFromProvider(EncodingProvider.CODE_PAGE_UNICODE);

        if (result == null)
        {
            Log.error("System does not support the given encoding: codepage=" + codePage + ", UTF-8 will be used instead");
            result = getUTF8();
        }

        Encoding value = gEncodings.get(codePage);
        if (value == null)
            gEncodings.put(codePage, result);

        return result;
    }

    public static Encoding getWINDOWS_1251()
    {
        return getEncoding(EncodingProvider.CODE_PAGE_1251);
    }

    public static Encoding getWINDOWS_1255()
    {
        return getEncoding(EncodingProvider.CODE_PAGE_1255);
    }

    /**
     * Copycat of .Net new UTF32Encoding(bool bigEndian, bool byteOrderMark) ctor.
     * <p>
     * UTF32 Encoding supported in jdk1.6 only.
     */
    public static Encoding newUTF32Encoding(boolean bigEndian, boolean byteOrderMark)
    {
        return bigEndian ? new UTF32BeEncoding(byteOrderMark) : new UTF32LeEncoding(byteOrderMark);
    }

    /**
     * Copycat of .Net new UnicodeEncoding(bool bigEndian, bool byteOrderMark) ctor.
     * <p>
     * Note: byteOrderMark param not used in java.
     */
    public static Encoding newUnicodeEncoding(boolean bigEndian, boolean byteOrderMark)
    {
        return bigEndian ? new UTF16BeEncoding(byteOrderMark) : new UTF16LeEncoding(byteOrderMark);
    }

    // Returns an encoding for the ASCII character set. The returned encoding
    // will be an instance of the ASCIIEncoding class.
    public static Encoding getASCII()
    {
        return getEncoding(EncodingProvider.CODE_PAGE_ASCII);
    }

    // Returns an encoding for the UTF-7 format. The returned encoding will be
    // an instance of the UTF7Encoding class.
    public static Encoding getUTF7()
    {
        return getEncoding(EncodingProvider.CODE_PAGE_UTF7);
    }

    // Returns an encoding for the UTF-8 format. The returned encoding will be
    // an instance of the UTF8Encoding class.
    public static Encoding getUTF8()
    {
        return getEncoding(EncodingProvider.CODE_PAGE_UTF8);
    }

    // Returns an encoding for Unicode format. The returned encoding will be
    // an instance of the UnicodeEncoding class.
    //
    // It will use little endian byte order, but will detect
    // input in big endian if it finds a byte order mark per Unicode 2.0.
    // //Unicode Little-endian
    public static Encoding getUnicode()
    {
        return getEncoding(EncodingProvider.CODE_PAGE_UNICODE);
    }

    // Returns an encoding for the UTF-32 format. The returned encoding will be
    // an instance of the UTF32Encoding class.
    public static Encoding getUTF32()
    {
        return getEncoding(EncodingProvider.CODE_PAGE_UNICODE_32);
    }

    // Returns an encoding for Unicode format. The returned encoding will be
    // an instance of the UnicodeEncoding class.
    //
    // It will use big endian byte order, but will detect
    //Unicode Big-endian
    public static Encoding getBigEndianUnicode()
    {
        return getEncoding(EncodingProvider.CODE_PAGE_BIG_ENDIAN_UNICODE);
    }

    //Chinese Traditional (Big5) with MS extensions.
    public static Encoding getBig5()
    {
        return getEncoding(EncodingProvider.CODE_PAGE_BIG_5);
    }

    // Returns an encoding for the system's current ANSI code page.
    public static Encoding getDefault()
    {
        return getEncoding(gDefaultSystemCharset.name());
    }

    //https://docs.microsoft.com/en-us/dotnet/api/system.text.encoding?view=netcore-3.1
    public static Encoding getEncoding(String name)
    {
        if (!gEncodingNames.containsKey(name))
        {
            int codePage = EncodingProvider.getCodePageFromName(name);
            if (codePage == -1)
                throw new IllegalArgumentException("System cannot find the given encoding by name=" + name);

            Integer value = gEncodingNames.get(name);
            if (value == null)
                gEncodingNames.put(name, codePage);
        }
        return getEncoding(gEncodingNames.get(name));
    }

    public static byte[] convert(Encoding srcEncoding, Encoding dstEncoding, byte[] bytes)
    {
        if (bytes == null)
            throw new NullPointerException("bytes");
        if (srcEncoding == null)
            throw new NullPointerException("srcEncoding");
        if (dstEncoding == null)
            throw new NullPointerException("dstEncoding");

        return dstEncoding.getBytes(srcEncoding.getChars(bytes));
    }

    // Returns a byte array containing the encoded representation of the given
    // character array.
    public byte[] getBytes(char[] chars)
    {
        return getBytes(chars, 0, chars.length);
    }

    // Returns a character array containing the decoded representation of a
    // given byte array.
    public char[] getChars(byte[] bytes)
    {
        return getChars(bytes, 0, bytes.length);
    }

    public static Encoding getEncoding(int codePage, EncoderFallback encoderFallback, DecoderFallback decoderFallback)
    {
        Encoding encoding = getEncoding(codePage);
        if (encoding != null)
        {
            encoding = encoding.deepClone();
            encoding.setEncoderFallback(encoderFallback);
            encoding.setDecoderFallback(decoderFallback);
        }
        return encoding;
    }

    public static Charset toJava(Encoding encoding)
    {
        return encoding == null ? null : encoding.mCharset;
    }

    public static Encoding fromJava(Charset charset)
    {
        if (charset == null)
            throw new IllegalArgumentException("The argument cannot be null ");

        // WORDSJAVA-2308. Via this method users set their encodings
        // Users can choose among Java's encoding, which could behave differently than .NETs
        // e.g. By default, .NET's UTF-8 has BOM but Java's doesn't.
        // So we should give users what they expect.
        return getEncoding(gNativeEncodingProvider.getCodePageByName(charset.displayName()));
    }

    /**
     * WORDSJAVA-1840 (fixed): .Net uses current system encoding but Java gets encoding from JVM.
     * The method tries to fetch system's current ANSI code page. If can't - returns JVM's encoding.
     * <p>
     * Besides Windows+Oracle JDK, the property "sun.jnu.encoding" tested also on Windows and Ubuntu
     * with OpenJDK and IBM JDK.
     */
    private static Charset getDefaultSystemCharset()
    {
        Charset jvmMEncoding = Charset.defaultCharset();
        String systemEncoding = System.getProperty("sun.jnu.encoding");
        Charset systemCharset = null;
        if (!msString.isNullOrEmpty(systemEncoding))
        {
            // Do nothing if the property doesn't exist or works differently on other JDK/platforms.
            try
            {
                systemCharset = Charset.forName(systemEncoding);
            }
            catch (Exception e)
            {
                Log.warn(e);
            }
        }

        return systemCharset != null ? systemCharset : jvmMEncoding;
    }

    /////////////////////////

    private static class DefaultDecoder extends Decoder
    {
        // Returns the number of characters the next call to GetChars will
        // produce if presented with the given range of bytes. The returned value
        // takes into account the state in which the decoder was left following the
        // last call to GetChars. The state of the decoder is not affected
        // by a call to this method.
        @Override
        public int getCharCount(byte[] bytes, int index, int count)
        {
            return getCharCount(bytes, index, count, false);
        }

        @Override
        public int getCharCount(byte[] bytes, int index, int count, boolean flush)
        {
            return mEncoding.getCharCount(bytes, index, count);
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
        @Override
        public int getChars(byte[] bytes, int byteIndex, int byteCount, char[] chars, int charIndex)
        {
            return getChars(bytes, byteIndex, byteCount, chars, charIndex, false);
        }

        @Override
        public int getChars(byte[] bytes, int byteIndex, int byteCount,
                            char[] chars, int charIndex, boolean flush)
        {
            return mEncoding.getChars(bytes, byteIndex, byteCount, chars, charIndex);
        }

        DefaultDecoder(Encoding encoding)
        {
            super(encoding);
            this.mFallbackBuffer = encoding.mDecoderFallback != null ? encoding.mDecoderFallback.createFallbackBuffer() : null;
        }
    }

    private static class DefaultEncoder extends Encoder
    {
        // Returns the number of bytes the next call to GetBytes will
        // produce if presented with the given range of characters and the given
        // value of the flush parameter. The returned value takes into
        // account the state in which the encoder was left following the last call
        // to GetBytes. The state of the encoder is not affected by a call
        // to this method.
        @Override
        public int getByteCount(char[] chars, int index, int count, boolean flush)
        {
            return mEncoding.getByteCount(chars, index, count);
        }

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
        @Override
        public int getBytes(char[] chars, int charIndex, int charCount, byte[] bytes, int byteIndex, boolean flush)
        {
            return mEncoding.getBytes(chars, charIndex, charCount, bytes, byteIndex);
        }

        @Override
        public int getBytes(char[] chars, int charIndex, int charCount, byte[] bytes, int byteIndex, int byteCount, boolean flush)
        {
            return mEncoding.getBytes(chars, charIndex, charCount, bytes, byteIndex, byteCount);
        }

        DefaultEncoder(Encoding encoding)
        {
            super(encoding);
            this.mFallbackBuffer = encoding.mEncoderFallback != null ? encoding.mEncoderFallback.createFallbackBuffer() : null;
        }
    }

    private static Charset tryCreateFromAlias(EncodingDataItem data)
    {
        if (Charset.isSupported(data.JavaCharsetName))
            return Charset.forName(data.JavaCharsetName);

        if (Charset.isSupported(data.EncodingInfoName))
            return Charset.forName(data.EncodingInfoName);

        if (Charset.isSupported(data.BodyName))
            return Charset.forName(data.BodyName);

        if (Charset.isSupported(data.HeaderName))
            return Charset.forName(data.HeaderName);

        if (Charset.isSupported(data.WebName))
            return Charset.forName(data.WebName);

        return UTF8;
    }

    private boolean checkIsSingleByteEncoding()
    {
        try
        {
            CharsetEncoder encoder = createCharsetEncoder();
            float maxBytesPerChar = encoder.maxBytesPerChar();
            return maxBytesPerChar <= 1.0;
        }
        catch (Exception e)
        {
            //some encoders did not implement maxBytesPerChar().
            return false;
        }
    }

    protected CharsetDecoder createCharsetDecoder()
    {
        try
        {
            // We have to create a new instance every time to guarantee Encoding methods thread safety. See comments at the top of the class.
            if (mDecoderFallback == null)
            {
                CharsetDecoder decoder = JavaThreadLocalCoders.decoderFor(mCharset);
                // Report Malformed and Unmappable characters to replace it later by numeric values as .Net does.
                decoder.onMalformedInput(CodingErrorAction.REPLACE);
                decoder.onUnmappableCharacter(CodingErrorAction.REPORT);
                decoder.replaceWith("?");
                return decoder;
            }
            else
            {
                // Set callback to replace malformed or unmappable character with some surrogate characters.
                return new CharsetDecoderDecorator(JavaThreadLocalCoders.decoderFor(mCharset), mDecoderFallback);
            }
        }
        catch (UnsupportedOperationException uoe)
        {
            Log.error(uoe);

            // Use UTF-8 by default
            // UnsupportedOperationException: ISO2022_CN, JISAutoDetect
            CharsetDecoder decoder = JavaThreadLocalCoders.decoderFor(UTF8);
            decoder.onUnmappableCharacter(CodingErrorAction.REPORT);
            decoder.onMalformedInput(CodingErrorAction.REPLACE);
            return decoder;
        }
    }

    protected CharsetEncoder createCharsetEncoder()
    {
        try
        {
            // We have to create a new instance every time to guarantee Encoding methods thread safety. See comments at the top of the class.
            if (mEncoderFallback == null)
            {
                // Set default Malformed and Unmappable Character actions to replace (bad character) by default symbol '/uFFFD'.
                CharsetEncoder encoder = JavaThreadLocalCoders.encoderFor(mCharset);
                encoder.onMalformedInput(CodingErrorAction.REPLACE);
                encoder.onUnmappableCharacter(CodingErrorAction.REPORT);
                return encoder;
            }
            else
            {
                // Set callback to replace malformed or unmappable character with some surrogate characters.
                return new CharsetEncoderDecorator(JavaThreadLocalCoders.encoderFor(mCharset), mEncoderFallback);
            }
        }
        catch (UnsupportedOperationException uoe)
        {
            Log.error(uoe);

            // Use UTF-8 by default
            // UnsupportedOperationException: ISO2022_CN, JISAutoDetect
            CharsetEncoder encoder = JavaThreadLocalCoders.encoderFor(UTF8);
            encoder.onMalformedInput(CodingErrorAction.REPLACE);
            encoder.onUnmappableCharacter(CodingErrorAction.REPORT);
            return encoder;
        }
    }

    // used for debug
    public String toString()
    {
        return mData.EncodingInfoName;
    }

    protected static final byte[] gNoPreamble = new byte[0];
    protected static final Charset UTF8 = Charset.forName("UTF-8");
    static final int NET_CODE_PAGE_MAX = 0xFFFF; // 65535
    private static final Charset gDefaultSystemCharset = getDefaultSystemCharset();
    private static final Map<Integer, Encoding> gEncodings = new ConcurrentHashMap<>();
    private static final Map<String, Integer> gEncodingNames = new ConcurrentHashMap<>();

    private static final NativeEncodingProvider gNativeEncodingProvider = new NativeEncodingProvider();

    static
    {
        // order is matter.
        EncodingProvider.registerProvider(new CustomEncodingProvider());
        EncodingProvider.registerProvider(new NetEncodingProvider());
        EncodingProvider.registerProvider(gNativeEncodingProvider);
    }

    protected final EncodingDataItem mData;
    protected final Charset mCharset;
    protected final float mMaxCharsPerByte;
    protected final float mMaxBytesPerChar;
    private final boolean isSingleByte;
    //https://msdn.microsoft.com/ru-ru/library/system.text.encoderfallbackbuffer.aspx
    private EncoderFallback mEncoderFallback;
    private DecoderFallback mDecoderFallback;
}
