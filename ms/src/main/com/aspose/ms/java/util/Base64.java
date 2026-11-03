// Parts of this file are a Java port, made by Aspose, of .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/convert.cs (method fromBase64CharPtr)
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

//added by Konstantin Sidorenko 28/11/2010
//used by com.aspose.ms.System.Convert to emulate Convert.toBase64String()/fromBase64String()

package com.aspose.ms.java.util;

import com.aspose.ms.System.Diagnostics.Debug;

import java.util.Arrays;

/** A very fast and memory efficient class to encode and decode to and from BASE64 in full accordance
 * with RFC 2045.<br><br>
 * On Windows XP sp1 with 1.4.2_04 and later ;), this encoder and decoder is about 10 times faster
 * on small arrays (10 - 1000 bytes) and 2-3 times as fast on larger arrays (10000 - 1000000 bytes)
 * compared to <code>sun.misc.Encoder()/Decoder()</code>.<br><br>
 *
 * On byte arrays the encoder is about 20% faster than Jakarta Commons Base64 Codec for encode and
 * about 50% faster for decoding large arrays. This implementation is about twice as fast on very small
 * arrays (&lt 30 bytes). If source/destination is a <code>String</code> this
 * version is about three times as fast due to the fact that the Commons Codec result has to be recoded
 * to a <code>String</code> from <code>byte[]</code>, which is very expensive.<br><br>
 *
 * This encode/decode algorithm doesn't create any temporary arrays as many other codecs do, it only
 * allocates the resulting array. This produces less garbage and it is possible to handle arrays twice
 * as large as algorithms that create a temporary array. (E.g. Jakarta Commons Codec). It is unknown
 * whether Sun's <code>sun.misc.Encoder()/Decoder()</code> produce temporary arrays but since performance
 * is quite low it probably does.<br><br>
 *
 * The encoder produces the same output as the Sun one except that the Sun's encoder appends
 * a trailing line separator if the last character isn't a pad. Unclear why but it only adds to the
 * length and is probably a side effect. Both are in conformance with RFC 2045 though.<br>
 * Commons codec seem to always att a trailing line separator.<br><br>
 *
 * <b>Note!</b>
 * The encode/decode method pairs (types) come in three versions with the <b>exact</b> same algorithm and
 * thus a lot of code redundancy. This is to not create any temporary arrays for transcoding to/from different
 * format types. The methods not used can simply be commented out.<br><br>
 *
 * There is also a "fast" version of all decode methods that works the same way as the normal ones, but
 * har a few demands on the decoded input. Normally though, these fast verions should be used if the source if
 * the input is known and it hasn't bee tampered with.<br><br>
 *
 * If you find the code useful or you find a bug, please send me a note at base64 @ miginfocom . com.
 *
 * Licence (BSD):
 * ==============
 *
 * Copyright (c) 2004, Mikael Grev, MiG InfoCom AB. (base64 @ miginfocom . com)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * Neither the name of the MiG InfoCom AB nor the names of its contributors may be
 * used to endorse or promote products derived from this software without specific
 * prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 *
 * @version 2.2
 * @author Mikael Grev
 *         Date: 2004-aug-02
 *         Time: 11:31:11
 */
public class Base64
{
	private static final char[] CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
	private static final int[] IA = new int[256];
	static
    {
		Arrays.fill(IA, -1);
		for (int i = 0, iS = CA.length; i < iS; i++)
			IA[CA[i]] = i;
		IA['='] = 0;
	}

	// ****************************************************************************************
	// *  char[] version
	// ****************************************************************************************

	/** Encodes a raw byte array into a BASE64 <code>char[]</code> representation i accordance with RFC 2045.
	 * @param sArr The bytes to convert. If <code>null</code> or length 0 an empty array will be returned.
	 * @param lineSep Optional "\r\n" after 76 characters, unless end of file.<br>
	 * No line separator will be in breach of RFC 2045 which specifies max 76 per line but will be a
	 * little faster.
	 * @return A BASE64 encoded array. Never <code>null</code>.
	 */
	public static char[] encodeToChar(byte[] sArr, int offset, int length, boolean lineSep)
	{
		// Check special case
		if (sArr == null || sArr.length == 0)
			return new char[0];

		int sLen = length;
		int eLen = (sLen / 3) * 3;              // Length of even 24-bits.
		int cCnt = ((sLen - 1) / 3 + 1) << 2;   // Returned character count
		int dLen = cCnt + (lineSep ? (cCnt - 1) / 76 << 1 : 0); // Length of returned array
		char[] dArr = new char[dLen];

		// Encode even 24-bits
		for (int s = offset, d = 0, cc = 0; s < offset + eLen;)
		{
			// Copy next three bytes into lower 24 bits of int, paying attention to sign.
			int i = (sArr[s++] & 0xff) << 16 | (sArr[s++] & 0xff) << 8 | (sArr[s++] & 0xff);

			// Encode the int into four chars
			dArr[d++] = CA[(i >>> 18) & 0x3f];
			dArr[d++] = CA[(i >>> 12) & 0x3f];
			dArr[d++] = CA[(i >>> 6) & 0x3f];
			dArr[d++] = CA[i & 0x3f];

			// Add optional line separator
			if (lineSep && ++cc == 19 && d < dLen - 2) {
				dArr[d++] = '\r';
				dArr[d++] = '\n';
				cc = 0;
			}
		}

		// Pad and encode last bits if source isn't even 24 bits.
		int left = sLen - eLen; // 0 - 2.
		if (left > 0)
		{
			// Prepare the int
			int i = ((sArr[offset + eLen] & 0xff) << 10) | (left == 2 ? ((sArr[offset + sLen - 1] & 0xff) << 2) : 0);

			// Set last four chars
			dArr[dLen - 4] = CA[i >>> 12];
			dArr[dLen - 3] = CA[(i >>> 6) & 0x3f];
			dArr[dLen - 2] = left == 2 ? CA[i & 0x3f] : '=';
			dArr[dLen - 1] = '=';
		}

		return dArr;
	}

	// ****************************************************************************************
	// * String version
	// ****************************************************************************************

	/** Encodes a raw byte array into a BASE64 <code>String</code> representation i accordance with RFC 2045.
	 * @param sArr The bytes to convert. If <code>null</code> or length 0 an empty array will be returned.
	 * @param lineSep Optional "\r\n" after 76 characters, unless end of file.<br>
	 * No line separator will be in breach of RFC 2045 which specifies max 76 per line but will be a
	 * little faster.
	 * @return A BASE64 encoded array. Never <code>null</code>.
	 */
	public static String encodeToString(byte[] sArr, boolean lineSep)
	{
        return encodeToString(sArr, 0, sArr.length, lineSep);
	}

	public static String encodeToString(byte[] sArr, int offset, int length, boolean lineSep)
	{
		// Reuse char[] since we can't create a String incrementally anyway and StringBuffer/Builder would be slower.
		return new String(encodeToChar(sArr, offset, length, lineSep));
	}

	/**
     * Decodes a BASE64 encoded <code>String</code>.
     * All illegal characters will be ignored and can handle both strings with and without line separators.<br>
	 * <b>Note!</b> It can be up to about 2x the speed to call <code>decode(str.toCharArray())</code> instead.
     * That will create a temporary array though. This version will use <code>str.charAt(i)</code> to iterate the string.
	 * @param str The source string. <code>null</code> or length 0 will return an empty array.
	 * @return The decoded array of bytes. May be of length 0. Will be <code>null</code> if the legal characters
	 * (including '=') isn't divideable by 4.  (I.e. definitely corrupted).
	 */
	public static byte[] decode(String str)
	{
		// Check special case
		int sLen = str != null ? str.length() : 0;
		if (sLen == 0)
			return new byte[0];

		// Count illegal characters (including '\r', '\n') to know what size the returned array will be,
		// so we don't have to reallocate & copy it later.
		int sepCnt = 0; // Number of separator characters. (Actually illegal characters, but that's a bonus...)
		for (int i = 0; i < sLen; i++)  // If input is "pure" (I.e. no line separators or illegal chars) base64 this loop can be commented out.
			if (IA[str.charAt(i)] < 0)
				sepCnt++;

		// Check so that legal chars (including '=') are evenly divideable by 4 as specified in RFC 2045.
		if ((sLen - sepCnt) % 4 != 0)
			return null;

		// Count '=' at end
		int pad = 0;
		for (int i = sLen; i > 1 && IA[str.charAt(--i)] <= 0;)
			if (str.charAt(i) == '=')
				pad++;

		int len = ((sLen - sepCnt) * 6 >>> 3) - pad;

		byte[] dArr = new byte[len];       // Preallocate byte[] of exact length

        for (int s = 0, d = 0; d < len; )
        {
            // Assemble three bytes into an int from four "valid" characters.
            int i = 0;
            for (int j = 0; j < 4; j++)
            {   // j only increased if a valid char was found.
                int c = IA[str.charAt(s++)];
                if (c >= 0)
                    i |= c << (18 - j * 6);
                else
                    j--;
            }
            // Add the bytes
            dArr[d++] = (byte) (i >>> 16);
            if (d < len)
            {
                dArr[d++] = (byte) (i >>> 8);
                if (d < len)
                    dArr[d++] = (byte) i;
            }
        }
        return dArr;
    }

    /**
     * The code is ported to Java from System.Convert.FromBase64CharPtr(),
     * see https://referencesource.microsoft.com/#mscorlib/system/convert.cs,a7523aa4c36a3be4
     */
    public static byte[] fromBase64CharPtr(char[] inputPtr, int inputLength)
    {
        // The validity of parameters much be checked by callers, thus we are Critical here.

        Debug.assertTrue(0 <= inputLength);

        // We need to get rid of any trailing white spaces.
        // Otherwise we would be rejecting input such as "abc= ":
        while (inputLength > 0)
        {
            int lastChar = inputPtr[inputLength - 1];
            if (lastChar != (int) ' ' && lastChar != (int) '\n' && lastChar != (int) '\r' && lastChar != (int) '\t')
                break;
            inputLength--;
        }

        // Compute the output length:
        int resultLength = fromBase64ComputeResultLength(inputPtr, inputLength);

        Debug.assertTrue(0 <= resultLength);

        // resultLength can be zero. We will still enter FromBase64_Decode and process the input.
        // It may either simply write no bytes (e.g. input = " ") or throw (e.g. input = "ab").

        // Create result byte blob:
        byte[] decodedBytes = new byte[resultLength];

        // Convert Base64 chars into bytes:
        fromBase64Decode(inputPtr, inputLength, decodedBytes, resultLength);

        // Note that actualResultLength can differ from resultLength if the caller is modifying the array
        // as it is being converted. Silently ignore the failure.
        // Consider throwing exception in an non in-place release.

        // We are done:
        return decodedBytes;
    }

    private static int fromBase64ComputeResultLength(char[] inputPtr, int inputLength)
    {
        final long intEq = (long) '=';
        final long intSpace = (long) ' ';

        Debug.assertTrue(0 <= inputLength);

        int inputIndex = 0;
        int usefulInputLength = inputLength;
        int padding = 0;

        while (inputIndex < inputLength)
        {
            long c = (long) inputPtr[inputIndex] & 0xFFFFFFFFL;
            inputIndex++;

            // We want to be as fast as possible and filter out spaces with as few comparisons as possible.
            // We end up accepting a number of illegal chars as legal white-space chars.
            // This is ok: as soon as we hit them during actual decode we will recognise them as illegal and throw.
            if (c <= intSpace)
            {
                usefulInputLength--;
            }
            else if (c == intEq)
            {
                usefulInputLength--;
                padding++;
            }
        }

        Debug.assertTrue(0 <= usefulInputLength);

        // For legal input, we can assume that 0 <= padding < 3. But it may be more for illegal input.
        // We will notice it at decode when we see a '=' at the wrong place.
        Debug.assertTrue(0 <= padding);

        // Perf: reuse the variable that stored the number of '=' to store the number of bytes encoded by the
        // last group that contains the '=':
        if (padding != 0)
        {
            if (padding == 1)
                padding = 2;
            else if (padding == 2)
                padding = 1;
            else
                //WORDSJAVA-2407: Format_BadBase64Char should be thrown as NumberFormatException but not RuntimeException
                throw new NumberFormatException("Format_BadBase64Char");
        }

        // Done:
        return (usefulInputLength / 4) * 3 + padding;
    }

    private static int fromBase64Decode(char[] startInputPtr, int inputLength, byte[] startDestPtr, int destLength)
    {

        // You may find this method weird to look at. It’s written for performance, not aesthetics.
        // You will find unrolled loops label jumps and bit manipulations.

        final long intA = (long) 'A' & 0xFFFFFFFFL;
        final long inta = (long) 'a' & 0xFFFFFFFFL;
        final long int0 = (long) '0' & 0xFFFFFFFFL;
        final long intEq = (long) '=' & 0xFFFFFFFFL;
        final long intPlus = (long) '+' & 0xFFFFFFFFL;
        final long intSlash = (long) '/' & 0xFFFFFFFFL;
        final long intSpace = (long) ' ' & 0xFFFFFFFFL;
        final long intTab = (long) '\t' & 0xFFFFFFFFL;
        final long intNLn = (long) '\n' & 0xFFFFFFFFL;
        final long intCRt = (long) '\r' & 0xFFFFFFFFL;
        final long intAtoZ = (long) ('Z' - 'A') & 0xFFFFFFFFL;  // = ('z' - 'a')
        final long int0to9 = (long) ('9' - '0') & 0xFFFFFFFFL;

        int inputIndex = 0;
        int destIndex = 0;

        // Current char code/value:
        long currCode = 0;

        boolean allInputConsumed = false;

        // This 4-byte integer will contain the 4 codes of the current 4-char group.
        // Eeach char codes for 6 bits = 24 bits.
        // The remaining byte will be FF, we use it as a marker when 4 chars have been processed.
        long currBlockCodes = 0x000000FFL;

        while (true)
        {
            // break when done:
            if (inputIndex >= inputLength)
            {
                allInputConsumed = true;
                break;
            }

            // Get current char:
            currCode = (long) startInputPtr[inputIndex] & 0xFFFFFFFFL;
            inputIndex++;

            // Determine current char code:

            if (((currCode - intA) & 0xFFFFFFFFL) <= intAtoZ)
            {
                currCode = (currCode - intA) & 0xFFFFFFFFL;
            }
            else if (((currCode - inta) & 0xFFFFFFFFL) <= intAtoZ)
            {
                currCode = (currCode - ((inta - 26) & 0xFFFFFFFFL)) & 0xFFFFFFFFL;
            }
            else if (((currCode - int0) & 0xFFFFFFFFL) <= int0to9)
            {
                currCode = (currCode - ((int0 - 52) & 0xFFFFFFFFL)) & 0xFFFFFFFFL;
            }
            else
            {
                // Significant chars:
                if (intPlus == currCode)
                    currCode = 62 & 0xFFFFFFFFL;
                else if (intSlash == currCode)
                    currCode = 63 & 0xFFFFFFFFL;
                else if (intCRt == currCode || intNLn == currCode || intSpace == currCode || intTab == currCode)
                    continue;
                // The equality char is only legal at the end of the input.
                // Jump after the loop to make it easier for the JIT register predictor to do a good job for the loop itself:
                else if (intEq == currCode)
                    break;
                else
                    throw new NumberFormatException("Format_BadBase64Char");
            }

            currBlockCodes = ((currBlockCodes << 6) | currCode) & 0xFFFFFFFFL;

            // Last bit in currBlockCodes will be on after in shifted right 4 times:
            if ((currBlockCodes & 0x80000000 & 0xFFFFFFFFL) != 0)
            {

                if ((destLength - destIndex) < 3)
                    return -1;

                startDestPtr[destIndex] = (byte) (currBlockCodes >>> 16);
                startDestPtr[destIndex + 1] = (byte) ((currBlockCodes >>> 8) + 256);
                startDestPtr[destIndex + 2] = (byte) ((currBlockCodes) + 256);
                destIndex += 3;

                currBlockCodes = 0x000000FFL & 0xFFFFFFFFL;
            }
        }

        if (!allInputConsumed)
        {
            Debug.assertTrue(currCode == intEq);

            // Recall that inputPtr is now one position past where '=' was read.
            // '=' can only be at the last input pos:
            if (inputIndex == inputLength)
            {
                // Code is zero for trailing '=':
                currBlockCodes <<= 6;

                // The '=' did not complete a 4-group. The input must be bad:
                if ((currBlockCodes & 0x80000000 & 0xFFFFFFFFL) == 0)
                    throw new NumberFormatException("Format_BadBase64CharArrayLength");

                if ((int) (destLength - destIndex) < 2)  // Autch! We underestimated the output length!
                    return -1;

                // We are good, store bytes form this past group. We had a single "=", so we take two bytes:
                startDestPtr[destIndex] = (byte) ((currBlockCodes >>> 16) + 256);
                startDestPtr[destIndex + 1] = (byte) ((currBlockCodes >>> 8) + 256);
                destIndex += 2;
                currBlockCodes = 0x000000FF & 0xFFFFFFFFL;

            }
            else
            {
                // '=' can also be at the pre-last position iff the last is also a '=' excluding the white spaces:
                // We need to get rid of any intermediate white spaces.
                // Otherwise we would be rejecting input such as "abc= =":
                while (inputIndex < (inputLength - 1))
                {
                    int lastChar = startInputPtr[inputIndex];
                    if (lastChar != (int) ' ' && lastChar != (int) '\n' && lastChar != (int) '\r' && lastChar != (int) '\t')
                        break;
                    inputIndex++;
                }

                if (inputIndex == (inputLength - 1) && startInputPtr[inputIndex] == '=')
                {
                    // Code is zero for each of the two '=':
                    currBlockCodes <<= 12;

                    // The '=' did not complete a 4-group. The input must be bad:
                    if ((currBlockCodes & 0x80000000 & 0xFFFFFFFFL) == 0)
                        throw new NumberFormatException("Format_BadBase64CharArrayLength");

                    if ((destLength - destIndex) < 1)  // Autch! We underestimated the output length!
                        return -1;

                    // We are good, store bytes form this past group. We had a "==", so we take only one byte:
                    startDestPtr[destIndex] = (byte) ((currBlockCodes >>> 16) + 256);
                    destIndex++;
                    currBlockCodes = 0x000000FFL & 0xFFFFFFFFL;

                }
                else
                {
                    // '=' is not ok at places other than the end:
                    throw new NumberFormatException("Format_BadBase64Char");
                }
            }
        }

        // The last block of chars has less than 4 items
        if (currBlockCodes != (0x000000FFL & 0xFFFFFFFFL))
            throw new NumberFormatException("Format_BadBase64CharArrayLength");

        // Return how many bytes were actually recovered:
        return destIndex;
    }
}