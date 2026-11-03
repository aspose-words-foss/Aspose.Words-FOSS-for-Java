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

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.java.ImageIO.tiff.decoder.BaselineTIFFTags;
import com.aspose.ms.java.ImageIO.tiff.decoder.TiffMetadata;

import java.awt.image.BufferedImage;

/**
 * https://programtalk.com/vs/openjdk/jdk/src/java.desktop/share/classes/com/sun/imageio/plugins/tiff/TIFFJPEGCompressor.java/
 */
public class TIFFJPEGCompressor extends TIFFCompressor
{
    @Override
    public void setMetadata(TiffMetadata metadata)
    {
        super.setMetadata(metadata);

        // overwrite for JPEG
        metadata.setExtraSamples(null);
        metadata.setBitsPerSample(new char[]{8, 8, 8});
        metadata.setSamplesPerPixel(3);

        int numBands = metadata.getSamplesPerPixel();
        if (numBands == 1)
        {
            // grayscale
            metadata.setYCbCrSubSampling(null);
            metadata.setYCbCrPositioning(null);
            metadata.setReferenceBlackWhite(null);
        }

        if (numBands == 3 || numBands == 4) // Android always uses 4
        {
            metadata.setYCbCrSubSampling(new char[]{CHROMA_SUBSAMPLING, CHROMA_SUBSAMPLING}); // TIFF_SHORT TAG_Y_CB_CR_SUBSAMPLING
            metadata.setYCbCrPositioning(new char[]{BaselineTIFFTags.Y_CB_CR_POSITIONING_CENTERED}); //TIFF_SHORT TAG_Y_CB_CR_POSITIONING
            metadata.setReferenceBlackWhite(
                    new long[][]{ // no headroon/footroom
                            {0, 1}, {255, 1},
                            {128, 1}, {255, 1},
                            {128, 1}, {255, 1}
                    }); //TIFF_RATIONAL TAG_REFERENCE_BLACK_WHITE
        }
    }

    @Override
    public int encode(BufferedImage raster, int off, int width, int height, int[] bitsPerSample, int scanlineStride)
    {
        throw new UnsupportedOperationException("TIFFJPEGCompressor.encode not implemented yet.");

    }

    @Override
    public int encode(byte[] b, int off, int width, int height, int[] bitsPerSample, int scanlineStride)
    {
        throw new UnsupportedOperationException("Use int encode(Bitmap raster, int off, int width, int height, int[] bitsPerSample, int scanlineStride) method");
    }

    public static final int CHROMA_SUBSAMPLING = 2;
}
