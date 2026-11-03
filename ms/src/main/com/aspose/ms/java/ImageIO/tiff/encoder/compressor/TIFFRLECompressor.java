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
package com.aspose.ms.java.ImageIO.tiff.encoder.compressor;


import com.aspose.ms.java.ImageIO.tiff.decoder.decompressor.TIFFFaxDecompressor;

import java.io.IOException;

public class TIFFRLECompressor extends TIFFFaxCompressor
{
    /**
     * Encode a row of data using Modified Huffman Compression also known as
     * CCITT RLE (Run Lenth Encoding).
     *
     * @param data      The row of data to compress.
     * @param rowOffset Starting index in <code>data</code>.
     * @param colOffset Bit offset within first <code>data[rowOffset]</code>.
     * @param rowLength Number of bits in the row.
     * @param compData  The compressed data.
     * @return The number of bytes saved in the compressed data array.
     */
    public int encodeRLE(byte[] data,
                         int rowOffset,
                         int colOffset,
                         int rowLength,
                         byte[] compData)
    {
        //
        // Initialize bit buffer machinery.
        //
        initBitBuf();

        //
        // Run-length encode line.
        //
        int outIndex =
                encode1D(data, rowOffset, colOffset, rowLength, compData, 0);

        //
        // Flush pending bits
        //
        while (ndex > 0)
        {
            compData[outIndex++] = (byte) (bits >>> 24);
            bits <<= 8;
            ndex -= 8;
        }

        //
        // Flip the bytes if inverse fill was requested.
        //
        if (inverseFill)
        {
            byte[] flipTable = TIFFFaxDecompressor.flipTable;
            for (int i = 0; i < outIndex; i++)
            {
                compData[i] = flipTable[compData[i] & 0xff];
            }
        }

        return outIndex;
    }

    @Override
    public int encode(byte[] b, int off, int width, int height, int[] bitsPerSample, int scanlineStride) throws Exception
    {
        if (bitsPerSample.length != 1 || bitsPerSample[0] != 1)
        {
            throw new IOException("Bits per sample must be 1 for RLE compression!");
        }

        // In the worst case, 2 bits of input will result in 9 bits of output,
        // plus 2 extra bits if the row starts with black.
        int maxBits = 9 * ((width + 1) / 2) + 2;
        byte[] compData = new byte[(maxBits + 7) / 8];

        int bytes = 0;
        int rowOffset = off;

        for (int i = 0; i < height; i++)
        {
            int rowBytes = encodeRLE(b, rowOffset, 0, width, compData);
            mStream.write(compData, 0, rowBytes);

            rowOffset += scanlineStride;
            bytes += rowBytes;
        }

        return bytes;
    }
}
