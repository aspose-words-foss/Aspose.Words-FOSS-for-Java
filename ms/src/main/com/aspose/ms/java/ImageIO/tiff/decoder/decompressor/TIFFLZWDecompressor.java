/*
 * This file is derived from jai-imageio-core (https://github.com/jai-imageio/jai-imageio-core),
 * reduced and adapted by Aspose. The notice of the original follows.
 *
 * Copyright (c) 2005 Sun Microsystems, Inc. All  Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistribution of source code must retain the above copyright
 *   notice, this  list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR
 * ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL,
 * CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND
 * REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF OR
 * INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed or intended for
 * use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */
// Copyright (c) 2001-2017 Aspose Pty Ltd. All Rights Reserved.
// August 2017 by Slava Durin
package com.aspose.ms.java.ImageIO.tiff.decoder.decompressor;

import com.aspose.ms.java.ImageIO.tiff.decoder.BaselineTIFFTags;

import java.io.IOException;

public class TIFFLZWDecompressor extends BaseTiffDecompressor
{
    @Override
    public void decodeRaw(byte[] byteData, int dstOffset, int pixelBitStride, int scanlineStride) throws Exception
    {
        mStream.setPosition(mOffset);

        byte[] sdata = new byte[mByteCount];
        mReader.readFully(sdata);

        byte[] buf = byteData;
        int bufOffset = dstOffset;

        int numBytesDecoded = decode(sdata, 0, buf, bufOffset);
        // System.out.println("numBytesDecoded=" + numBytesDecoded);
    }

    public int decode(byte[] sdata, int srcOffset, byte[] ddata, int dstOffset) throws IOException
    {
        if (sdata[0] == (byte) 0x00 && sdata[1] == (byte) 0x01)
            throw new IOException("TIFF 5.0-style LZW compression is not supported!");

        srcData = sdata;
        dstData = ddata;

        srcIndex = srcOffset;
        dstIndex = dstOffset;

        nextData = 0;
        nextBits = 0;

        initializeStringTable();

        int code, oldCode = 0;
        byte[] string;

        while ((code = getNextCode()) != 257)
        {
            if (code == 256)
            {
                initializeStringTable();
                code = getNextCode();
                if (code == 257)
                    break;

                writeString(stringTable[code]);
                oldCode = code;
            }
            else
            {
                if (code < tableIndex)
                {
                    string = stringTable[code];

                    writeString(string);
                    addStringToTable(stringTable[oldCode], string[0]);
                    oldCode = code;
                }
                else
                {
                    string = stringTable[oldCode];
                    string = composeString(string, string[0]);
                    writeString(string);
                    addStringToTable(string);
                    oldCode = code;
                }
            }
        }

        if (mPredictor == BaselineTIFFTags.PREDICTOR_HORIZONTAL_DIFFERENCING)
        {

            for (int j = 0; j < mSrcHeight; j++)
            {

                int count = dstOffset + mSamplesPerPixel * (j * mSrcWidth + 1);

                for (int i = mSamplesPerPixel; i < mSrcWidth * mSamplesPerPixel; i++)
                {
                    dstData[count] += dstData[count - mSamplesPerPixel];
                    count++;
                }
            }
        }

        return dstIndex - dstOffset;
    }

    private void initializeStringTable()
    {
        stringTable = new byte[4096][];

        for (int i = 0; i < 256; i++)
        {
            stringTable[i] = new byte[1];
            stringTable[i][0] = (byte) i;
        }

        tableIndex = 258;
        bitsToGet = 9;
    }

    // Returns the next 9, 10, 11 or 12 bits
    public int getNextCode()
    {
        // Attempt to get the next code. The exception is caught to make
        // this robust to cases wherein the EndOfInformation code has been
        // omitted from a strip. Examples of such cases have been observed
        // in practice.

        try
        {
            nextData = (nextData << 8) | (srcData[srcIndex++] & 0xff);
            nextBits += 8;

            if (nextBits < bitsToGet)
            {
                nextData = (nextData << 8) | (srcData[srcIndex++] & 0xff);
                nextBits += 8;
            }

            int code = (nextData >> (nextBits - bitsToGet)) & andTable[bitsToGet - 9];
            nextBits -= bitsToGet;

            return code;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            // Strip not terminated as expected: return EndOfInformation code.
            return 257;
        }
    }

    public void writeString(byte string[])
    {
        if (dstIndex < dstData.length)
        {
            int maxIndex = Math.min(string.length, dstData.length - dstIndex);
            for (int i = 0; i < maxIndex; i++)
                dstData[dstIndex++] = string[i];
        }
    }

    public void addStringToTable(byte oldString[], byte newString)
    {
        int length = oldString.length;
        byte string[] = new byte[length + 1];
        System.arraycopy(oldString, 0, string, 0, length);
        string[length] = newString;

        // Add this new String to the table
        stringTable[tableIndex++] = string;

        if (tableIndex == 511)
            bitsToGet = 10;

        else if (tableIndex == 1023)
            bitsToGet = 11;

        else if (tableIndex == 2047)
            bitsToGet = 12;
    }

    public byte[] composeString(byte oldString[], byte newString)
    {
        int length = oldString.length;
        byte string[] = new byte[length + 1];
        System.arraycopy(oldString, 0, string, 0, length);
        string[length] = newString;

        return string;
    }

    public void addStringToTable(byte string[])
    {
        // Add this new String to the table
        stringTable[tableIndex++] = string;

        if (tableIndex == 511)
            bitsToGet = 10;

        else if (tableIndex == 1023)
            bitsToGet = 11;

        else if (tableIndex == 2047)
            bitsToGet = 12;
    }
    private static final int andTable[] = {
            511,
            1023,
            2047,
            4095
    };
    byte[] srcData;
    byte[] dstData;
    int srcIndex;
    int dstIndex;
    byte stringTable[][];
    int tableIndex, bitsToGet = 9;
    int nextData = 0;
    int nextBits = 0;
}
