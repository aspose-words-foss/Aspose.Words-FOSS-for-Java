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


public class TIFFNullDecompressor extends BaseTiffDecompressor
{
    @Override
    public void beginDecoding()
    {
        // Determine number of bits per pixel.
        int bitsPerPixel = 0;
        for (int i = 0; i < mBitsPerSample.length; i++)
            bitsPerPixel += mBitsPerSample[i];

        // Can read active region only if row starts on a byte boundary.
        if ((activeSrcMinX != mSrcMinX || activeSrcMinY != mSrcMinY || activeSrcWidth != mSrcWidth || activeSrcHeight != mSrcHeight) && ((activeSrcMinX - mSrcMinX) * bitsPerPixel) % 8 == 0)
        {
            // Set flag.
            isReadActiveOnly = true;

            // Cache original region.
            originalSrcMinX = mSrcMinX;
            originalSrcMinY = mSrcMinY;
            originalSrcWidth = mSrcWidth;
            originalSrcHeight = mSrcHeight;

            // Replace region with active region.
            mSrcMinX = activeSrcMinX;
            mSrcMinY = activeSrcMinY;
            mSrcWidth = activeSrcWidth;
            mSrcHeight = activeSrcHeight;
        }
        else
        {
            // Clear flag.
            isReadActiveOnly = false;
        }

        super.beginDecoding();
    }

    public void decode() throws Exception
    {
        super.decode();

        // Reset state.
        if (isReadActiveOnly)
        {
            // Restore original source region values.
            mSrcMinX = originalSrcMinX;
            mSrcMinY = originalSrcMinY;
            mSrcWidth = originalSrcWidth;
            mSrcHeight = originalSrcHeight;

            // Unset flag.
            isReadActiveOnly = false;
        }
    }

    @Override
    public void decodeRaw(byte[] byteData, int dstOffset, int bitsPerPixel, int scanlineStride) throws Exception
    {

        if (isReadActiveOnly)
        {
            // Read the active source region only.

            int activeBytesPerRow = (activeSrcWidth * bitsPerPixel + 7) / 8;
            int totalBytesPerRow = (originalSrcWidth * bitsPerPixel + 7) / 8;
            int bytesToSkipPerRow = totalBytesPerRow - activeBytesPerRow;

            //
            // Seek to the start of the active region:
            //
            // active offset = original offset +
            //                 number of bytes to start of first active row +
            //                 number of bytes to first active pixel within row
            //
            // Since the condition for reading from the active region only is
            //
            //     ((activeSrcMinX - srcMinX)*bitsPerPixel) % 8 == 0
            //
            // the bit offset to the first active pixel within the first
            // active row is a multiple of 8.
            //
            mStream.setPosition(mOffset +
                    (activeSrcMinY - originalSrcMinY) * totalBytesPerRow +
                    ((activeSrcMinX - originalSrcMinX) * bitsPerPixel) / 8);

            int lastRow = activeSrcHeight - 1;
            for (int y = 0; y < activeSrcHeight; y++)
            {
                mStream.read(byteData, dstOffset, activeBytesPerRow);
                dstOffset += scanlineStride;

                // Skip unneeded bytes (row suffix + row prefix).
                if (y != lastRow)
                    //mStream.skipBytes(bytesToSkipPerRow);
                    mStream.setPosition(mStream.getPosition() + bytesToSkipPerRow);
            }
        }
        else
        {

            mStream.setPosition(mOffset);
            int bytesPerRow = (mSrcWidth * bitsPerPixel + 7) / 8;
            if (bytesPerRow == scanlineStride)
                mReader.read(byteData, dstOffset, bytesPerRow * mSrcHeight);

            else
            {
                for (int y = 0; y < mSrcHeight; y++)
                {
                    mReader.read(byteData, dstOffset, bytesPerRow);
                    dstOffset += scanlineStride;
                }
            }
        }
    }

    private boolean isReadActiveOnly = false;
    private int originalSrcMinX;
    private int originalSrcMinY;
    private int originalSrcWidth;
    private int originalSrcHeight;
}
