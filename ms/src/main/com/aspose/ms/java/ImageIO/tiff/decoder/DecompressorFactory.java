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
package com.aspose.ms.java.ImageIO.tiff.decoder;

import com.aspose.ms.java.ImageIO.tiff.decoder.decompressor.*;

import java.io.IOException;

/**
 * Creates an instance of {@link BaseTiffDecompressor} according to "compression" tag value
 */
public class DecompressorFactory
{
    public static BaseTiffDecompressor getDecompressor(int compression, int fillOrder, int predictor) throws IOException
    {
        switch (compression)
        {
            case BaselineTIFFTags.COMPRESSION_LZW:
                return new TIFFLZWDecompressor();

            case BaselineTIFFTags.COMPRESSION_NONE:
                return fillOrder == 2 ? new TIFFLSBDecompressor() : new TIFFNullDecompressor();

            case BaselineTIFFTags.COMPRESSION_JPEG:
                return new TIFFJPEGDecompressor();

            case BaselineTIFFTags.COMPRESSION_OLD_JPEG:
                return new TIFFOldJPEGDecompressor();

            case BaselineTIFFTags.COMPRESSION_PACKBITS:
                return new TIFFPackBitsDecompressor();

            case BaselineTIFFTags.COMPRESSION_DEFLATE:
            case BaselineTIFFTags.COMPRESSION_ZLIB:
                return new TIFFDeflateDecompressor();

            case BaselineTIFFTags.COMPRESSION_CCITT_RLE:
                return new TIFFFaxDecompressor();

            case BaselineTIFFTags.COMPRESSION_CCITT_T_4:
                return new TIFFFaxDecompressor();

            case BaselineTIFFTags.COMPRESSION_CCITT_T_6:
                return new TIFFFaxDecompressor();

            default:
                throw new IOException("Unsupported compression type (tag number = " + compression + ")!");
        }
    }
}
