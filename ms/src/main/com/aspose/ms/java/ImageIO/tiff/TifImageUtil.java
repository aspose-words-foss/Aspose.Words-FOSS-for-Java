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
package com.aspose.ms.java.ImageIO.tiff;

import com.aspose.ms.java.ImageIO.tiff.decoder.TiffBitmapData;

import java.awt.image.*;
import java.io.IOException;

public class TifImageUtil
{
    // ceil(num/den)
    public static int iceil(int num, int den)
    {
        if (num > 0)
            num += den - 1;

        return num / den;
    }

    // floor(num/den)
    public static int ifloor(int num, int den)
    {
        if (num < 0)
            num -= den - 1;

        return num / den;
    }

    public static int XToTileX(int x, int tileGridXOffset, int tileWidth)
    {
        x -= tileGridXOffset;
        if (x < 0)
            x += 1 - tileWidth;        // force round to -infinity (ceiling)
        return x / tileWidth;
    }

    public static int YToTileY(int y, int tileGridYOffset, int tileHeight)
    {
        y -= tileGridYOffset;
        if (y < 0)
            y += 1 - tileHeight;     // force round to -infinity (ceiling)
        return y / tileHeight;
    }

    public static boolean imageIsContiguous(TiffBitmapData bitmapData)
    {
        SampleModel sm = bitmapData.getSampleModel();

        if (sm instanceof ComponentSampleModel)
        {
            // Ensure image rows samples are stored contiguously
            // in a single bank.
            ComponentSampleModel csm = (ComponentSampleModel) sm;

            if (csm.getPixelStride() != csm.getNumBands())
                return false;

            int[] bandOffsets = csm.getBandOffsets();
            for (int i = 0; i < bandOffsets.length; i++)
                if (bandOffsets[i] != i)
                    return false;

            int[] bankIndices = csm.getBankIndices();
            for (int i = 0; i < bandOffsets.length; i++)
                if (bankIndices[i] != 0)
                    return false;

            return true;
        }

        // Otherwise true if and only if it's a bilevel image with
        // a MultiPixelPackedSampleModel, 1 bit per pixel, and 1 bit
        // pixel stride.
        return isBinary(sm);
    }

    public static boolean isBinary(SampleModel sm)
    {
        return (sm instanceof MultiPixelPackedSampleModel)
                && (((MultiPixelPackedSampleModel) sm).getPixelBitStride() == 1)
                && (sm.getNumBands() == 1)
                && (sm.getDataType() == DataBufferByte.TYPE_BYTE);
    }

    /**
     * Returns the number of bits per pixel.
     */
    public static int getBitsPerPixel(SampleModel sm)
    {
        int bitsPerPixel = 0;
        int[] sampleSize = sm.getSampleSize();
        int numBands = sampleSize.length;
        for (int i = 0; i < numBands; i++)
            bitsPerPixel += sampleSize[i];

        return bitsPerPixel;
    }

    /**
     * Returns whether all samples have the same number of bits.
     */
    public static boolean areSampleSizesEqual(SampleModel sm)
    {
        boolean allSameSize = true;
        int[] sampleSize = sm.getSampleSize();
        int sampleSize0 = sampleSize[0];
        int numBands = sampleSize.length;

        for (int i = 1; i < numBands; i++)
        {
            if (sampleSize[i] != sampleSize0)
            {
                allSameSize = false;
                break;
            }
        }

        return allSameSize;
    }

    /**
     * Determines whether the <code>DataBuffer</code> is filled without
     * any interspersed padding bits.
     */
    public static boolean isDataBufferBitContiguous(SampleModel sm) throws IOException
    {
        int dataTypeSize = getDataTypeSize(sm.getDataType());

        if (sm instanceof ComponentSampleModel)
        {
            int numBands = sm.getNumBands();
            for (int i = 0; i < numBands; i++)
            {
                if (sm.getSampleSize(i) != dataTypeSize)
                {
                    // Sample does not fill data element.
                    return false;
                }
            }
        }
        else if (sm instanceof MultiPixelPackedSampleModel)
        {
            MultiPixelPackedSampleModel mppsm = (MultiPixelPackedSampleModel) sm;
            if (dataTypeSize % mppsm.getPixelBitStride() != 0)
            {
                // Pixels do not fill the data element.
                return false;
            }
        }
        else if (sm instanceof SinglePixelPackedSampleModel)
        {
            SinglePixelPackedSampleModel sppsm = (SinglePixelPackedSampleModel) sm;
            int numBands = sm.getNumBands();
            int numBits = 0;
            for (int i = 0; i < numBands; i++)
            {
                numBits += sm.getSampleSize(i);
            }
            if (numBits != dataTypeSize)
            {
                // Pixel does not fill the data element.
                return false;
            }
        }
        else
        {
            // Unknown SampleModel class.
            return false;
        }

        return true;
    }

    /**
     * Return the number of bits occupied by <code>dataType</code>
     * which must be one of the <code>DataBuffer</code> <code>TYPE</code>s.
     */
    private static int getDataTypeSize(int dataType) throws IOException
    {
        int dataTypeSize = 0;
        switch (dataType)
        {
            case DataBuffer.TYPE_BYTE:
                dataTypeSize = 8;
                break;
            case DataBuffer.TYPE_SHORT:
            case DataBuffer.TYPE_USHORT:
                dataTypeSize = 16;
                break;
            case DataBuffer.TYPE_INT:
            case DataBuffer.TYPE_FLOAT:
                dataTypeSize = 32;
                break;
            case DataBuffer.TYPE_DOUBLE:
                dataTypeSize = 64;
                break;
            default:
                throw new IOException("Unknown data type " + dataType);
        }

        return dataTypeSize;
    }


    public static int getDataTypeFromNumBits(int numBits, boolean isSigned)
    {
        int dataType;

        if (numBits <= 8)
            dataType = DataBuffer.TYPE_BYTE;

        else if (numBits <= 16)
            dataType = isSigned ? DataBuffer.TYPE_SHORT : DataBuffer.TYPE_USHORT;

        else
            dataType = DataBuffer.TYPE_INT;

        return dataType;
    }

    public static int createMask(int[] bitsPerSample, int band)
    {
        int mask = (1 << bitsPerSample[band]) - 1;
        for (int i = band + 1; i < bitsPerSample.length; i++)
            mask <<= bitsPerSample[i];

        return mask;
    }
}

