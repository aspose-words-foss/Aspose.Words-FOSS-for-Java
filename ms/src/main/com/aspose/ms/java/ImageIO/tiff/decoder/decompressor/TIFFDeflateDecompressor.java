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
package com.aspose.ms.java.ImageIO.tiff.decoder.decompressor;

import com.aspose.ms.java.ImageIO.tiff.decoder.BaselineTIFFTags;

import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class TIFFDeflateDecompressor extends BaseTiffDecompressor
{
    @Override
    public void decodeRaw(byte[] byteData, int dstOffset, int bitsPerPixel, int scanlineStride) throws Exception
    {
        if (mPredictor != BaselineTIFFTags.PREDICTOR_NONE && mPredictor != BaselineTIFFTags.PREDICTOR_HORIZONTAL_DIFFERENCING)
            throw new IOException("Illegal value for Predictor in TIFF file");

        // Seek to current tile data offset.
        mStream.setPosition(mOffset);

        // Read the deflated data.
        byte[] srcData = new byte[mByteCount];
        mReader.readFully(srcData);

        int bytesPerRow = (mSrcWidth * bitsPerPixel + 7) / 8;
        byte[] buf;
        int bufOffset;
        if (bytesPerRow == scanlineStride)
        {
            buf = byteData;
            bufOffset = dstOffset;
        }
        else
        {
            buf = new byte[bytesPerRow * mSrcHeight];
            bufOffset = 0;
        }

        // Set the input to the Inflater.
        mInflater.setInput(srcData);

        // Inflate the data.
        try
        {
            mInflater.inflate(buf, bufOffset, bytesPerRow * mSrcHeight);
        }
        catch (DataFormatException dfe)
        {
            throw new IOException("Error inflating data.", dfe);
        }

        // Reset the Inflater.
        mInflater.reset();

        if (mPredictor == BaselineTIFFTags.PREDICTOR_HORIZONTAL_DIFFERENCING)
        {

            for (int j = 0; j < mSrcHeight; j++)
            {
                int count = bufOffset + mSamplesPerPixel * (j * mSrcWidth + 1);
                for (int i = mSamplesPerPixel; i < mSrcWidth * mSamplesPerPixel; i++)
                {
                    buf[count] += buf[count - mSamplesPerPixel];
                    count++;
                }
            }
        }

        if (bytesPerRow != scanlineStride)
        {
            int off = 0;
            for (int y = 0; y < mSrcHeight; y++)
            {
                System.arraycopy(buf, off, byteData, dstOffset, bytesPerRow);
                off += bytesPerRow;
                dstOffset += scanlineStride;
            }
        }
    }

    public TIFFDeflateDecompressor()
    {
        mInflater = new Inflater();
    }

    private Inflater mInflater;
}
