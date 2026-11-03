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


import com.aspose.ms.java.ImageIO.tiff.decoder.BaselineTIFFTags;

public class TIFFLZWCompressor extends TIFFCompressor
{
    @Override
    public int encode(byte[] b, int off, int width, int height, int[] bitsPerSample, int scanlineStride) throws Exception
    {
        LZWCompressor lzwCompressor = new LZWCompressor(mStream.getBaseStream(), 8, true);

        int samplesPerPixel = bitsPerSample.length;
        int bitsPerPixel = 0;
        for (int i = 0; i < samplesPerPixel; i++)
        {
            bitsPerPixel += bitsPerSample[i];
        }
        int bytesPerRow = (bitsPerPixel * width + 7) / 8;

        long initialStreamPosition = mStream.getPosition();

        boolean usePredictor = mPredictor == BaselineTIFFTags.PREDICTOR_HORIZONTAL_DIFFERENCING;

        if (bytesPerRow == scanlineStride && !usePredictor)
        {
            lzwCompressor.compress(b, off, bytesPerRow * height);
        }
        else
        {
            byte[] rowBuf = usePredictor ? new byte[bytesPerRow] : null;
            for (int i = 0; i < height; i++)
            {
                if (usePredictor)
                {
                    // Cannot modify b[] in place as it might be a data
                    // array from the image being written so make a copy.
                    System.arraycopy(b, off, rowBuf, 0, bytesPerRow);
                    for (int j = bytesPerRow - 1; j >= samplesPerPixel; j--)
                    {
                        rowBuf[j] -= rowBuf[j - samplesPerPixel];
                    }
                    lzwCompressor.compress(rowBuf, 0, bytesPerRow);
                }
                else
                {
                    lzwCompressor.compress(b, off, bytesPerRow);
                }
                off += scanlineStride;
            }
        }

        lzwCompressor.flush();

        int bytesWritten = (int) (mStream.getPosition() - initialStreamPosition);

        return bytesWritten;
    }

    public TIFFLZWCompressor(int predictor)
    {
        mPredictor = predictor;
    }

    private final int mPredictor;
}
