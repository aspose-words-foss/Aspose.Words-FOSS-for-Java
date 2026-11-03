// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/03/2016 by Anatoly Sidorenko

/*
 * ******************************************************************************
 * Copyright (C) 2005-2014, International Business Machines Corporation and    *
 * others. All Rights Reserved.                                                *
 * ******************************************************************************
 */
// 24/03/16 port to C# by Anatoly Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// <para>
/// <c>CharsetDetector</c> provides a facility for detecting the
/// charset or encoding of character data in an unknown format.
/// The input data can either be from an input stream or an array of bytes.
/// The result of the detection operation is a list of possibly matching
/// charsets, or, for simple use, you can just ask for a Java Reader that
/// will work over the input data.
/// </para>
/// <para>
/// Character set detection is at best an imprecise operation.  The detection
/// process will attempt to identify the charset that best matches the characteristics
/// of the byte data, but the process is partly statistical in nature, and
/// the results can not be guaranteed to always be correct.
/// </para>
/// <para>
/// For best accuracy in charset detection, the input data should be primarily
/// in a single language, and a minimum of a few hundred bytes worth of plain text
/// in the language are needed.  The detection process will attempt to
/// ignore html or xml style markup that could otherwise obscure the content.
/// </para>
/// <para>@stable ICU 3.4</para>
/// </summary>
public class CharsetDetector
{

    //   Question: Should we have getters corresponding to the setters for input text and declared encoding?

    //   A thought: If we were to create our own type of Java Reader, we could defer
    //   figuring out an actual charset for data that starts out with too much English
    //   only ASCII until the user actually read through to something that didn't look
    //   like 7 bit English.  If  nothing else ever appeared, we would never need to
    //   actually choose the "real" charset.  All assuming that the application just
    //   wants the data, and doesn't care about a char set name.


    // ------------- Aspose ------------------------------------
    public static Encoding detect(Stream stream) throws Exception
    {
        long pos = stream.getPosition();
        try
        {
            stream.setPosition(0);
            CharsetDetector detector = new CharsetDetector(stream);
            CharsetMatch match = detector.detect();
            if (match == null)
                return Encoding.getUTF8();

            return getEncoding(match);
        }
        catch (Exception ex)
        {
            // The line will be removed from production both in .Net and Java.
            Debug.writeLine(ex.getMessage());
        }
        finally
        {
            stream.setPosition(pos);
        }

        return null;
    }

    private static Encoding getEncoding(CharsetMatch match) throws Exception
    {
        String charsetName = match.getName();
        Encoding encoding = EncodingUtil.getEncoding(charsetName);

        // Some windows-125X and iso8859-Y charsets are very similar, but ICU4J reports
        // ISO-8859-* by default. It reports windows-125* only if short input text
        // contains bytes in the range 0x80 - 0x9F.
        // Lets reverse defaults for Windows platform.
        //
        // See also CharsetSingleByteRecognizer and
        // http://www.i18nqa.com/debug/table-iso8859-1-vs-windows-1252.html,
        // http://konfiguracja.c0.pl/iso02vscp1250en.html and google "windows-125X vs iso8859-Y".
        if (PlatformUtilPal.isWindows())
        {
            if (msString.equals("ISO-8859-1", charsetName, StringComparison.ORDINAL))
                encoding = Encoding.getEncoding("Windows-1252");
            else if (msString.equals("ISO-8859-2", charsetName, StringComparison.ORDINAL))
                encoding = Encoding.getEncoding("Windows-1250");
            else if (msString.equals("ISO-8859-7", charsetName, StringComparison.ORDINAL) &&
                msString.equals("el", match.getLanguage(), StringComparison.ORDINAL))
                encoding = Encoding.getEncoding("Windows-1253");
            else if (msString.equals("ISO-8859-8", charsetName, StringComparison.ORDINAL) &&
                msString.equals("he", match.getLanguage(), StringComparison.ORDINAL))
                encoding = Encoding.getEncoding("Windows-1255");
            else if (msString.equals("ISO-8859-8-I", charsetName, StringComparison.ORDINAL) &&
                msString.equals("he", match.getLanguage(), StringComparison.ORDINAL))
                encoding = Encoding.getEncoding("Windows-1255");
            else if (msString.equals("ISO-8859-9", charsetName, StringComparison.ORDINAL) &&
                msString.equals("tr", match.getLanguage(), StringComparison.ORDINAL))
                encoding = Encoding.getEncoding("Windows-1254");
        }

        return encoding;
    }

    /// <summary>
    /// Return the charset that best matches the supplied input data.
    /// <p/>
    /// Note though, that because the detection
    /// only looks at the start of the input data,
    /// there is a possibility that the returned charset will fail to handle
    /// the full set of input data.
    /// <p/>
    /// Raise an exception if
    /// <ul>
    /// <li>no charset appears to match the data.</li>
    /// <li>no input text has been provided</li>
    /// </ul>
    /// <returns>a CharsetMatch object representing the best matching charset, or
    /// <code>null</code> if there are no matches.</returns>
    /// </summary>
    private CharsetMatch detect()
    {
        // Iterate over all possible charsets, remember all that give a match quality > 0.
        CharsetMatch bestMatch = null;
        for (CharsetRecognizer charsetRecognizer : G_CHARSET_RECOGNIZERS)
        {
            CharsetMatch curMatch = charsetRecognizer.match(this);
            if (curMatch == null)
                continue;

            if (curMatch.compareTo(bestMatch) >= 0)
                bestMatch = curMatch;
        }

        return bestMatch;
    }

    public byte[] getRawInput()
    {
        return mRawInput;
    }

    public int getRawLength()
    {
        return mRawInput.length;
    }

    public byte[] getInputBytes()
    {
        return mInputBytes;
    }

    public int getInputLen()
    {
        return mInputBytes.length;
    }

    public boolean hasC1Bytes()
    {
        return mHasC1Bytes;
    }

    /// <summary>
    /// Initializes a new instance of <see cref="CharsetDetector"/> class.
    /// </summary>
    private CharsetDetector(Stream inputStream) throws Exception
    {
        mRawInput = StreamUtil.readStream(inputStream, MAX_RAW_BUF_SIZE);

        inputStream.setPosition(0);
        // Set the input text (byte) data whose charset is to be detected.
        mInputBytes = readStreamFiltered(inputStream, MAX_INPUT_BUF_SIZE, getIgnorableTextRanges());

        mHasC1Bytes = hasC1Bytes(mInputBytes);
    }

    /// <summary>
    /// Returns true, if a specified byte array has C1 codes.
    /// </summary>
    /// <remarks>
    /// C1 codes are the range 80h–9Fh and the default C1 set was originally
    /// defined in ECMA-48 (harmonized later with ISO 6429).
    /// </remarks>
    private static boolean hasC1Bytes(byte[] array)
    {
        for (byte b : array)
        {
            if ((b & 0xFF) >= 0x80)
                return true;
        }

        return false;
    }

    /// <summary>
    /// Reads stream and filter out specified text.
    /// </summary>
    /// <param name="stream">The input stream.</param>
    /// <param name="length">The length of the returning byte array. Note, as some text may be removed
    /// from the input stream, the number of bytes read from the stream can be greater than the
    /// specified length. This also can be less than the specified length, if there is insufficient
    /// number of bytes in the input stream. </param>
    /// <param name="textRangesToRemove">Specifies text ranges that will be filtered out while reading the stream.</param>
    private static byte[] readStreamFiltered(Stream stream, int length, ArrayList<TextRange> textRangesToRemove) throws Exception
    {
        // Initial read.
        byte[] srcBuf = StreamUtil.readStream(stream, length);
        if (srcBuf.length == 0)
            return srcBuf;

        // Result array.
        byte[] dstBuf = new byte[srcBuf.length];

        int srcIndex = 0;
        int dstIndex = 0;
        while (dstIndex < dstBuf.length)
        {
            // Read next portion of bytes to buffer being analyzed.
            if (srcIndex >= srcBuf.length)
            {
                srcBuf = StreamUtil.readStream(stream, length);
                if (srcBuf.length == 0)
                    return ArrayUtil.resizeArray(dstBuf, dstIndex);

                srcIndex = 0;
            }

            // Find text range with minimal start position to filter out.
            TextRange rangeToRemove = null;
            for (TextRange range : textRangesToRemove)
            {
                if (range.find(srcBuf, srcIndex) && range.isBefore(rangeToRemove))
                    rangeToRemove = range;
            }

            // Copy either whole source buffer, or only bytes before text being removed.
            int count = 0;
            int lengthToCopy = (rangeToRemove == null) ? length : rangeToRemove.getStartIndex() - srcIndex;
            if (lengthToCopy > 0)
            {
                count = ArrayUtil.copy(srcBuf, srcIndex, lengthToCopy, dstBuf, dstIndex);
                if (count == 0)
                    return ArrayUtil.resizeArray(dstBuf, dstIndex);
            }

            srcIndex += count;
            dstIndex += count;

            if (rangeToRemove == null)
                continue;

            // There is a range of text to remove, but its end has not been found yet.
            while (rangeToRemove.getEndIndex() == -1)
            {
                // Read next portion of bytes to find end of range to remove.
                srcBuf = StreamUtil.readStream(stream, length);
                if (srcBuf.length == 0)
                    return ArrayUtil.resizeArray(dstBuf, dstIndex);

                rangeToRemove.findEnd(srcBuf, 0);
            }

            // There is no end of range, exit.
            if (rangeToRemove.getEndIndex() == -1)
                return ArrayUtil.resizeArray(dstBuf, dstIndex);

            // Skip filtered out range of text.
            srcIndex = rangeToRemove.getNextIndex();
        }

        return dstBuf;
    }

    /// <summary>
    /// Returns a collection of <see cref="TextRange"/> objects to ignore in detection buffer.
    /// </summary>
    private static ArrayList<TextRange> getIgnorableTextRanges()
    {
        ArrayList<TextRange> ignorableTextRanges = new ArrayList<TextRange>();

        // WORDSNET-24148 Strip base64 image data from the array of detecting bytes.
        msArrayList.add(ignorableTextRanges, new TextRange("\"data:image", "\"", new String[] { "base64" }));

        // WORDSNET-27980 Strip javascript and styles html tags from the array.
        msArrayList.add(ignorableTextRanges, new TextRange("<script", "script>", new String[] { "type=\"text/javascript\"" }));
        msArrayList.add(ignorableTextRanges, new TextRange("<style", "style>", new String[] { "type=\"text/css\"" }));

        // WORDSNET-28178 Ignore more HTML tags in detection buffer.
        // Note, that only tag names are ignored, but not the text inside them.
        // For example, in the following snippet: <td>text</td>,
        // the tags <td> and </td> will be ignored, but the 'text'
        // will be considered.
        String[] htmlTagNames = new String[]
        {
            "title", "body", "table", "tr", "td", "th", "font", "center", "b", "i", "div"
        };
        for (String htmlTagName : htmlTagNames)
        {
            msArrayList.add(ignorableTextRanges, new TextRange(msString.format("<{0}", htmlTagName), ">"));
            msArrayList.add(ignorableTextRanges, new TextRange(msString.format("</{0}", htmlTagName), ">"));
        }

        return ignorableTextRanges;
    }

    //List of recognizers for all charsets known to the implementation.
    private static /*final*/ ArrayList<CharsetRecognizer> G_CHARSET_RECOGNIZERS;

    /// <summary>
    /// The max size of array with specially prepared input bytes.
    /// </summary>
    /// <remarks>
    /// WORDSNET-18929 The read limit is increased from 512 bytes to 3kb as there is ‘ at the offset 2891,
    /// which should be taken into account to properly detect the encoding.
    /// </remarks>
    private static final int MAX_INPUT_BUF_SIZE = 1024 * 3;

    /// <summary>
    /// The text to be checked. Markup will be removed if appropriate.
    /// </summary>
    private /*final*/ byte[] mInputBytes;

    /// <summary>
    /// The max size of array with raw input bytes.
    /// </summary>
    private static final int MAX_RAW_BUF_SIZE = 1024 * 3;

    /// <summary>
    /// Original, untouched input bytes.
    /// </summary>
    private /*final*/ byte[] mRawInput;

    /// <summary>
    /// The max size of array with raw input bytes.
    /// </summary>
    /// <remarks>
    /// True if any bytes in the range 0x80 - 0x9F are in the input.
    /// </remarks>
    private /*final*/ boolean mHasC1Bytes;

    static/* CharsetDetector()*/
    {
        G_CHARSET_RECOGNIZERS = new ArrayList<CharsetRecognizer>();

        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetUTF7Recognizer());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetUTF8Recognizer());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new Charset_UTF_16_BERecognizer());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new Charset_UTF_16_LERecognizer());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new Charset_UTF_32_BERecognizer());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new Charset_UTF_32_LERecognizer());

        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetISO_2022RecognizerJP());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetISO_2022RecognizerCN());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetISO_2022RecognizerKR());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetRecog_gb_18030());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetRecog_euc_jp());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetRecog_euc_kr());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetRecog_sjis());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetRecog_big5());

        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetRecog_8859_1());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetRecog_8859_2());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetRecog_8859_5_ru());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetRecog_8859_6_ar());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetRecog_8859_7_el());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetRecog_8859_8_I_he());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetRecog_8859_8_he());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetRecog_windows_1251());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetRecog_windows_1256());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetRecog_KOI8_R());
        msArrayList.add(G_CHARSET_RECOGNIZERS, new CharsetRecog_8859_9_tr());
    }
}

