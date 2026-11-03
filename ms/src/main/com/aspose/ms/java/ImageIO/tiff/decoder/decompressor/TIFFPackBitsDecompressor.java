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

import com.aspose.ms.java.logging.Log;

import java.io.IOException;

public class TIFFPackBitsDecompressor extends BaseTiffDecompressor
{
    @Override
    public void decodeRaw(byte[] byteData, int dstOffset, int bitsPerPixel, int scanlineStride) throws Exception
    {
        mStream.setPosition(mOffset);

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

        decode(srcData, 0, buf, bufOffset);

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

    public int decode(byte[] srcData, int srcOffset, byte[] dstData, int dstOffset) throws IOException
    {

        int srcIndex = srcOffset;
        int dstIndex = dstOffset;

        int dstArraySize = dstData.length;
        int srcArraySize = srcData.length;
        try
        {
            while (dstIndex < dstArraySize && srcIndex < srcArraySize)
            {
                byte b = srcData[srcIndex++];

                if (b >= 0 && b <= 127)
                { // second test not needed?
                    // Literal run packet

                    for (int i = 0; i < b + 1; i++)
                    {
                        dstData[dstIndex++] = srcData[srcIndex++];
                    }
                }
                else if (b <= -1 && b >= -127)
                {
                    // 2-byte encoded run packet
                    byte repeat = srcData[srcIndex++];
                    for (int i = 0; i < (-b + 1); i++)
                    {
                        dstData[dstIndex++] = repeat;
                    }
                }
                else
                {
                    // No-op packet, do nothing
                    ++srcIndex;
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            Log.error(e);
        }

        return dstIndex - dstOffset;
    }
}
