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

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.IO.ImageStreamReader;
import com.aspose.ms.java.ImageIO.tiff.TifImageUtil;
import com.aspose.ms.java.ImageIO.tiff.decoder.BaselineTIFFTags;
import com.aspose.ms.java.ImageIO.tiff.decoder.TiffBitmapData;
import com.aspose.ms.java.ImageIO.tiff.decoder.TiffMetadata;
import com.aspose.ms.java.ImageIO.tiff.decoder.converter.TIFFColorConverter;

import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.awt.image.ColorModel;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteOrder;

public abstract class BaseTiffDecompressor
{
    public void setStream(Stream stream)
    {
        mStream = stream;
    }

    public void setOffset(long offset)
    {
        mOffset = offset;
    }

    public void setPredictor(int predictor)
    {
        mPredictor = predictor;
    }

    public void beginDecoding()
    {
        this.mAdjustBitDepths = false;
        int numBands = mDestinationBands.length;
        int[] destBitsPerSample = null;
        if (mPlanar)
        {
            int totalNumBands = mBitsPerSample.length;
            destBitsPerSample = new int[totalNumBands];
            int dbps = mImage.getSampleModel().getSampleSize(0);
            for (int b = 0; b < totalNumBands; b++)
                destBitsPerSample[b] = dbps;

        }
        else
            destBitsPerSample = mImage.getSampleModel().getSampleSize();

        // Make sure that the image is not CMYK (separated) or does not have
        // bits per sample of 1, 2, or 4 before trying adjust.
        if (getMetadata().getPhotometricInterpretation() != BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_CMYK || mBitsPerSample[0] != 1 && mBitsPerSample[0] != 2 && mBitsPerSample[0] != 4)
        {
            for (int b = 0; b < numBands; b++)
            {
                if (destBitsPerSample[mDestinationBands[b]] != mBitsPerSample[mSourceBands[b]])
                {
                    mAdjustBitDepths = true;
                    break;
                }
            }
        }

        // If the bit depths differ, create a lookup table per band to perform the conversion
        if (mAdjustBitDepths)
        {
            throw new UnsupportedOperationException();
            /*
            // Compute the table only if this is the first time one is being computed or if any of the variables on which the table is based have changed.
            if (this.isFirstBitDepthTable || mPlanar != planarCache
                    || !areIntArraysEqual(destBitsPerSample, destBitsPerSampleCache)
                    || !areIntArraysEqual(mSourceBands, sourceBandsCache)
                    || !areIntArraysEqual(mBitsPerSample, bitsPerSampleCache)
                    || !areIntArraysEqual(mDestinationBands, destinationBandsCache))
            {

                this.isFirstBitDepthTable = false;

                // Cache some variables.
                this.planarCache = mPlanar;
                this.destBitsPerSampleCache = (int[]) destBitsPerSample.clone(); // never null ...
                this.sourceBandsCache = mSourceBands == null ? null : (int[]) mSourceBands.clone();
                this.bitsPerSampleCache = mBitsPerSample == null ? null : (int[]) mBitsPerSample.clone();
                this.destinationBandsCache = mDestinationBands == null ? null : (int[]) mDestinationBands.clone();

                // Allocate and fill the table.
                mBitDepthScale = new int[numBands][];
                for (int b = 0; b < numBands; b++)
                {
                    int maxInSample = (1 << mBitsPerSample[mSourceBands[b]]) - 1;
                    int halfMaxInSample = maxInSample / 2;

                    int maxOutSample = (1 << destBitsPerSample[mDestinationBands[b]]) - 1;

                    mBitDepthScale[b] = new int[maxInSample + 1];
                    for (int s = 0; s <= maxInSample; s++)
                        mBitDepthScale[b][s] = (s * maxOutSample + halfMaxInSample) / maxInSample;
                }
            }
            */
        }
        else
        { // !mAdjustBitDepths
            // Clear any prior table.
            this.bitDepthScale = null;
        }

        boolean sourceBandsNormal = false;
        boolean destinationBandsNormal = false;
        if (numBands == mSamplesPerPixel)
        {
            sourceBandsNormal = true;
            destinationBandsNormal = true;
            for (int i = 0; i < numBands; i++)
            {
                if (mSourceBands[i] != i)
                    sourceBandsNormal = false;

                if (mDestinationBands[i] != i)
                    destinationBandsNormal = false;
            }
        }

        // Determine whether the image is bilevel and/or contiguous.
        // Note that a planar image could be bilevel but it will not
        // be contiguous unless it has a single component band stored
        // in a single bank.
        this.isBilevel = TifImageUtil.isBinary(this.mImage.getSampleModel());
        this.isContiguous = this.isBilevel ? true : TifImageUtil.imageIsContiguous(this.mImage);

        // Analyze destination image to see if we can copy into it
        // directly

        this.isImageSimple =
                (mColorConverter == null) &&
                        (mSubsampleX == 1) && (mSubsampleY == 1) &&
                        (mSrcWidth == mDstWidth) && (mSrcHeight == mDstHeight) &&
                        ((mDstMinX + mDstWidth) <= mImage.getWidth()) &&
                        ((mDstMinY + mDstHeight) <= mImage.getHeight()) &&
                        sourceBandsNormal && destinationBandsNormal && !mAdjustBitDepths;
    }

    public TiffMetadata getMetadata()
    {
        return mMetadata;
    }

    public void setMetadata(TiffMetadata metadata)
    {
        mMetadata = metadata;
    }

    public void decode() throws Exception
    {

        byte[] byteData = null;
        short[] shortData = null;
        int[] intData = null;
        float[] floatData = null;

        int dstOffset = 0;
        int pixelBitStride = 1;
        int scanlineStride = 0;

        int sampleModelTranslateX = -mDstMinX;
        int sampleModelTranslateY = -mDstMinY;
        int srcWidth = mSrcWidth;
        int srcHeight = mSrcHeight;

        this.rawImage = null;
        if (isImageSimple)
        {
            if (isBilevel)
                rawImage = this.mImage;
            else if (isContiguous)
                //rawImage = mImage.getSubimage(dstMinX, dstMinY, dstWidth, dstHeight);
                rawImage = this.mImage;
        }

        boolean isDirectCopy = rawImage != null;

        if (rawImage == null)
        {
            rawImage = createRawImage();
            if (rawImage == null)
                throw new IOException("Couldn't create image buffer!");
        }

        if (isBilevel)
        {
            // byteData = ImageUtil.getPackedBinaryData(ras, rect);
            DataBuffer db = rawImage.getDataBuffer();
            if (db instanceof DataBufferByte)
                byteData = ((DataBufferByte) db).getData();
            else
                throw new UnsupportedOperationException("Unsupported " + db);

            dstOffset = 0;
            pixelBitStride = 1;
            scanlineStride = (getMetadata().getWidth() + 7) / 8;
        }
        else
        {
            boolean isSupportedType = false;
            SampleModel sm = rawImage.getSampleModel();
            DataBuffer db = rawImage.getDataBuffer();

            if (sm instanceof ComponentSampleModel)
            {
                ComponentSampleModel csm = (ComponentSampleModel) sm;
                dstOffset = csm.getOffset(-sampleModelTranslateX, -sampleModelTranslateY);
                scanlineStride = csm.getScanlineStride();
                if (db instanceof DataBufferByte)
                {
                    DataBufferByte dbb = (DataBufferByte) db;
                    byteData = dbb.getData();
                    pixelBitStride = csm.getPixelStride() * 8;
                    isSupportedType = true;
                }
                else if (db instanceof DataBufferUShort)
                {
                    DataBufferUShort dbus = (DataBufferUShort) db;
                    shortData = dbus.getData();
                    pixelBitStride = csm.getPixelStride() * 16;
                    isSupportedType = true;
                }
                else if (db instanceof DataBufferShort)
                {
                    DataBufferShort dbs = (DataBufferShort) db;
                    shortData = dbs.getData();
                    pixelBitStride = csm.getPixelStride() * 16;
                    isSupportedType = true;
                }
                else if (db instanceof DataBufferInt)
                {
                    DataBufferInt dbi = (DataBufferInt) db;
                    intData = dbi.getData();
                    pixelBitStride = csm.getPixelStride() * 32;
                    isSupportedType = true;
                }
                else if (db instanceof DataBufferFloat)
                {
                    DataBufferFloat dbf = (DataBufferFloat) db;
                    floatData = dbf.getData();
                    pixelBitStride = csm.getPixelStride() * 32;
                    isSupportedType = true;
                }
            }
            else if (sm instanceof MultiPixelPackedSampleModel)
            {
                MultiPixelPackedSampleModel mppsm = (MultiPixelPackedSampleModel) sm;
                dstOffset = mppsm.getOffset(-sampleModelTranslateX, -sampleModelTranslateY);
                pixelBitStride = mppsm.getPixelBitStride();
                scanlineStride = mppsm.getScanlineStride();
                if (db instanceof DataBufferByte)
                {
                    DataBufferByte dbb = (DataBufferByte) db;
                    byteData = dbb.getData();
                    isSupportedType = true;
                }
                else if (db instanceof DataBufferUShort)
                {
                    DataBufferUShort dbus = (DataBufferUShort) db;
                    shortData = dbus.getData();
                    isSupportedType = true;
                }
                else if (db instanceof DataBufferInt)
                {
                    DataBufferInt dbi = (DataBufferInt) db;
                    intData = dbi.getData();
                    isSupportedType = true;
                }
            }
            else if (sm instanceof SinglePixelPackedSampleModel)
            {
                SinglePixelPackedSampleModel sppsm = (SinglePixelPackedSampleModel) sm;
                dstOffset = sppsm.getOffset(-sampleModelTranslateX, -sampleModelTranslateY);
                scanlineStride = sppsm.getScanlineStride();
                if (db instanceof DataBufferByte)
                {
                    DataBufferByte dbb = (DataBufferByte) db;

                    byteData = dbb.getData();
                    pixelBitStride = 8;
                    isSupportedType = true;
                }
                else if (db instanceof DataBufferUShort)
                {
                    DataBufferUShort dbus = (DataBufferUShort) db;
                    shortData = dbus.getData();
                    pixelBitStride = 16;
                    isSupportedType = true;
                }
                else if (db instanceof DataBufferInt)
                {
                    DataBufferInt dbi = (DataBufferInt) db;
                    intData = dbi.getData();
                    pixelBitStride = 32;
                    isSupportedType = true;
                }
            }

            if (!isSupportedType)
                throw new IOException("Unsupported raw image type: SampleModel = " + sm + "; DataBuffer = " + db);
        }

        if (isBilevel)
        {
            // Bilevel data are always in a contiguous byte buffer.
            decodeRaw(byteData, dstOffset, pixelBitStride, scanlineStride);
        }
        else
        {
            SampleModel sm = rawImage.getSampleModel();


            if (TifImageUtil.isDataBufferBitContiguous(sm))
            {
                // Use byte or float data directly.
                if (byteData != null)
                    decodeRaw(byteData, dstOffset, pixelBitStride, scanlineStride);

                else if (floatData != null)
                    decodeRaw(floatData, dstOffset, pixelBitStride, scanlineStride);

                else
                {
                    if (shortData != null)
                    {
                        if (TifImageUtil.areSampleSizesEqual(sm) && sm.getSampleSize(0) == 16)
                        {
                            // Decode directly into short data.
                            decodeRaw(shortData, dstOffset, pixelBitStride, scanlineStride);
                        }
                        else
                        {
                            // Decode into bytes and reformat into shorts.
                            int bpp = TifImageUtil.getBitsPerPixel(sm);
                            int bytesPerRow = (bpp * srcWidth + 7) / 8;
                            byte[] buf = new byte[bytesPerRow * srcHeight];
                            decodeRaw(buf, 0, bpp, bytesPerRow);
                            reformatData(buf, bytesPerRow, srcHeight, shortData, null, dstOffset, scanlineStride);
                        }
                    }
                    else if (intData != null)
                    {
                        if (TifImageUtil.areSampleSizesEqual(sm) && sm.getSampleSize(0) == 32)
                        {
                            // Decode directly into int data.
                            decodeRaw(intData, dstOffset, pixelBitStride, scanlineStride);
                        }
                        else
                        {
                            // Decode into bytes and reformat into ints.
                            int bpp = TifImageUtil.getBitsPerPixel(sm);
                            int bytesPerRow = (bpp * srcWidth + 7) / 8;
                            byte[] buf = new byte[bytesPerRow * srcHeight];
                            decodeRaw(buf, 0, bpp, bytesPerRow);
                            reformatData(buf, bytesPerRow, srcHeight, null, intData, dstOffset, scanlineStride);
                        }
                    }
                }
            }
            else
            {
                // Read discontiguous data into bytes and set the samples into the Raster.
                int bpp = TifImageUtil.getBitsPerPixel(sm);
                int bytesPerRow = (bpp * srcWidth + 7) / 8;
                byte[] buf = new byte[bytesPerRow * srcHeight];
                decodeRaw(buf, 0, bpp, bytesPerRow);
                reformatDiscontiguousData(buf, bytesPerRow, srcWidth, srcHeight);
            }
        }

        if (mColorConverter != null)
        {
            float[] rgb = new float[3];

            if (byteData != null)
            {
                for (int j = 0; j < mDstHeight; j++)
                {
                    int idx = dstOffset;
                    for (int i = 0; i < mDstWidth; i++)
                    {
                        float x0 = (float) (byteData[idx] & 0xff);
                        float x1 = (float) (byteData[idx + 1] & 0xff);
                        float x2 = (float) (byteData[idx + 2] & 0xff);

                        mColorConverter.toRGB(x0, x1, x2, rgb);

                        byteData[idx] = (byte) (rgb[0]);
                        byteData[idx + 1] = (byte) (rgb[1]);
                        byteData[idx + 2] = (byte) (rgb[2]);

                        idx += 3;
                    }

                    dstOffset += scanlineStride;
                }
            }
            else if (shortData != null)
            {
                if (mSampleFormat[0] == BaselineTIFFTags.SAMPLE_FORMAT_SIGNED_INTEGER)
                {
                    for (int j = 0; j < mDstHeight; j++)
                    {
                        int idx = dstOffset;
                        for (int i = 0; i < mDstWidth; i++)
                        {
                            float x0 = (float) shortData[idx];
                            float x1 = (float) shortData[idx + 1];
                            float x2 = (float) shortData[idx + 2];

                            mColorConverter.toRGB(x0, x1, x2, rgb);

                            shortData[idx] = (short) (rgb[0]);
                            shortData[idx + 1] = (short) (rgb[1]);
                            shortData[idx + 2] = (short) (rgb[2]);

                            idx += 3;
                        }

                        dstOffset += scanlineStride;
                    }
                }
                else
                {
                    for (int j = 0; j < mDstHeight; j++)
                    {
                        int idx = dstOffset;
                        for (int i = 0; i < mDstWidth; i++)
                        {
                            float x0 = (float) (shortData[idx] & 0xffff);
                            float x1 = (float) (shortData[idx + 1] & 0xffff);
                            float x2 = (float) (shortData[idx + 2] & 0xffff);

                            mColorConverter.toRGB(x0, x1, x2, rgb);

                            shortData[idx] = (short) (rgb[0]);
                            shortData[idx + 1] = (short) (rgb[1]);
                            shortData[idx + 2] = (short) (rgb[2]);

                            idx += 3;
                        }

                        dstOffset += scanlineStride;
                    }
                }
            }
            else if (intData != null)
            {
                for (int j = 0; j < mDstHeight; j++)
                {
                    int idx = dstOffset;
                    for (int i = 0; i < mDstWidth; i++)
                    {
                        float x0 = (float) intData[idx];
                        float x1 = (float) intData[idx + 1];
                        float x2 = (float) intData[idx + 2];

                        mColorConverter.toRGB(x0, x1, x2, rgb);

                        intData[idx] = (int) (rgb[0]);
                        intData[idx + 1] = (int) (rgb[1]);
                        intData[idx + 2] = (int) (rgb[2]);

                        idx += 3;
                    }

                    dstOffset += scanlineStride;
                }
            }
            else if (floatData != null)
            {
                for (int j = 0; j < mDstHeight; j++)
                {
                    int idx = dstOffset;
                    for (int i = 0; i < mDstWidth; i++)
                    {
                        float x0 = floatData[idx];
                        float x1 = floatData[idx + 1];
                        float x2 = floatData[idx + 2];

                        mColorConverter.toRGB(x0, x1, x2, rgb);

                        floatData[idx] = rgb[0];
                        floatData[idx + 1] = rgb[1];
                        floatData[idx + 2] = rgb[2];

                        idx += 3;
                    }

                    dstOffset += scanlineStride;
                }
            }
        }

        if (getMetadata().getPhotometricInterpretation() == BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO)
        {
            if (byteData != null)
            {
                int bytesPerRow = (srcWidth * pixelBitStride + 7) / 8;
                for (int y = 0; y < srcHeight; y++)
                {
                    int offset = dstOffset + y * scanlineStride;
                    for (int i = 0; i < bytesPerRow; i++)
                        byteData[offset + i] ^= 0xff;
                }
            }
            else if (shortData != null)
            {
                int shortsPerRow = (srcWidth * pixelBitStride + 15) / 16;
                if (getMetadata().getSampleFormat()[0] == BaselineTIFFTags.SAMPLE_FORMAT_SIGNED_INTEGER)
                {
                    for (int y = 0; y < srcHeight; y++)
                    {
                        int offset = dstOffset + y * scanlineStride;
                        for (int i = 0; i < shortsPerRow; i++)
                        {
                            int shortOffset = offset + i;
                            // XXX Does this make any sense?
                            shortData[shortOffset] = (short) (Short.MAX_VALUE - shortData[shortOffset]);
                        }
                    }
                }
                else
                {
                    for (int y = 0; y < srcHeight; y++)
                    {
                        int offset = dstOffset + y * scanlineStride;
                        for (int i = 0; i < shortsPerRow; i++)
                            shortData[offset + i] ^= 0xffff;
                    }
                }
            }
            else if (intData != null)
            {
                int intsPerRow = (srcWidth * pixelBitStride + 15) / 16;
                for (int y = 0; y < srcHeight; y++)
                {
                    int offset = dstOffset + y * scanlineStride;
                    for (int i = 0; i < intsPerRow; i++)
                    {
                        int intOffset = offset + i;
                        // XXX Does this make any sense?
                        intData[intOffset] = Integer.MAX_VALUE - intData[intOffset];
                    }
                }
            }
            else if (floatData != null)
            {
                int floatsPerRow = (srcWidth * pixelBitStride + 15) / 16;
                for (int y = 0; y < srcHeight; y++)
                {
                    int offset = dstOffset + y * scanlineStride;
                    for (int i = 0; i < floatsPerRow; i++)
                    {
                        int floatOffset = offset + i;
                        // XXX Does this make any sense?
                        floatData[floatOffset] = 1.0F - floatData[floatOffset];
                    }
                }
            }
        }

        // isDirectCopy is true when mImage is used. For 'planar' tiffs isDirectCopy = false
        if (isDirectCopy)
            return;

        // Copy the raw image data into the true destination image
        SampleModel src = rawImage.getSampleModel();
        int[] srcBandList = mPlanar ? null : mSourceBands;
        SampleModel srcChild = srcBandList == null ? src : src.createSubsetSampleModel(srcBandList);
        DataBuffer srcDataBuffer = rawImage.getDataBuffer();

        SampleModel dst = mImage.getSampleModel();
        SampleModel dstChild = mDestinationBands != null ? dst.createSubsetSampleModel(mDestinationBands) : dst;
        DataBuffer dstDataBuffer = mImage.getDataBuffer();

        if (mSubsampleX == 1 && mSubsampleY == 1 && !mAdjustBitDepths)
        {
            // srcChild = srcChild.createChild(activeSrcMinX, activeSrcMinY, activeSrcWidth, activeSrcHeight, dstMinX, dstMinY, null);
            setRect(srcChild, srcDataBuffer, dstChild, dstDataBuffer);
        }
        else if (mSubsampleX == 1 && !mAdjustBitDepths)
        {
            throw new UnsupportedOperationException();
        }
        else
        {
            throw new UnsupportedOperationException();
        }
    }

    public TiffBitmapData createRawImage()
    {
        int w = mSrcWidth;
        int h = mSrcHeight;

        if (mPlanar)
        {
            // Create a single-banded image of the appropriate data type.

            // Get the number of bits per sample.
            int bps = mBitsPerSample[mSourceBands[0]];

            // Determine the data type.
            int dataType;
            if (mSampleFormat[0] == BaselineTIFFTags.SAMPLE_FORMAT_FLOATING_POINT)
                dataType = DataBuffer.TYPE_FLOAT;

            else if (bps <= 8)
                dataType = DataBuffer.TYPE_BYTE;

            else if (bps <= 16)
            {
                if (mSampleFormat[0] == BaselineTIFFTags.SAMPLE_FORMAT_SIGNED_INTEGER)
                    dataType = DataBuffer.TYPE_SHORT;

                else
                    dataType = DataBuffer.TYPE_USHORT;
            }
            else
                dataType = DataBuffer.TYPE_INT;

            ColorSpace csGray = ColorSpace.getInstance(ColorSpace.CS_GRAY);

            TiffBitmapData its = null;
            // For planar images with 1, 2 or 4 bits per sample, we need to use a MultiPixelPackedSampleModel so that when the TIFF
            // decoder properly decodes the data per pixel, we know how to extract it back out into individual pixels.
            // This is how the pixels are actually stored in the planar bands.
            if (bps == 1 || bps == 2 || bps == 4)
            {
                int bits = bps;
                int size = 1 << bits;
                byte[] r = new byte[size];
                byte[] g = new byte[size];
                byte[] b = new byte[size];
                for (int j = 0; j < r.length; j++)
                {
                    r[j] = 0;
                    g[j] = 0;
                    b[j] = 0;
                }
                ColorModel cmGray = new IndexColorModel(bits, size, r, g, b);
                SampleModel smGray = new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE, w, h, bits);
                its = TiffBitmapData.createBitmapData(cmGray, smGray, mMetadata);
            }
            else
            {
                its = TiffBitmapData.createInterleaved(csGray, new int[]{0}, dataType, false, false, w, h);
                its.setMetadata(mMetadata);
            }

            return its;
        }
        else
        {
            TiffBitmapData its = TiffBitmapData.createBitmapData(mMetadata);
            if (its == null)
                return null;

            return its;// createBufferedImage(mSrcWidth, mSrcHeight);
        }
    }

    public abstract void decodeRaw(byte[] byteData, int dstOffset, int pixelBitStride, int scanlineStride) throws Exception;

    public void decodeRaw(float[] f, int dstOffset, int bitsPerPixel, int scanlineStride) throws Exception
    {
        int numBands = bitsPerPixel / 32;
        int floatsPerRow = mSrcWidth * numBands;
        int bytesPerRow = floatsPerRow * 4;

        byte[] b = new byte[bytesPerRow * mSrcHeight];
        decodeRaw(b, 0, bitsPerPixel, bytesPerRow);

        int bOffset = 0;
        if (mMetadata.getByteOrder() == ByteOrder.BIG_ENDIAN)
        {
            for (int j = 0; j < mSrcHeight; j++)
            {
                for (int i = 0; i < floatsPerRow; i++)
                {
                    int v0 = b[bOffset++] & 0xff;
                    int v1 = b[bOffset++] & 0xff;
                    int v2 = b[bOffset++] & 0xff;
                    int v3 = b[bOffset++] & 0xff;
                    int ival = (v0 << 24) | (v1 << 16) | (v2 << 8) | v3;
                    float fval = Float.intBitsToFloat(ival);
                    f[dstOffset + i] = fval;
                }

                dstOffset += scanlineStride;
            }
        }
        else
        { // ByteOrder.LITLE_ENDIAN
            for (int j = 0; j < mSrcHeight; j++)
            {
                for (int i = 0; i < floatsPerRow; i++)
                {
                    int v3 = b[bOffset++] & 0xff;
                    int v2 = b[bOffset++] & 0xff;
                    int v1 = b[bOffset++] & 0xff;
                    int v0 = b[bOffset++] & 0xff;
                    int ival = (v0 << 24) | (v1 << 16) | (v2 << 8) | v3;
                    float fval = Float.intBitsToFloat(ival);
                    f[dstOffset + i] = fval;
                }

                dstOffset += scanlineStride;
            }
        }
    }

    public void decodeRaw(short[] s, int dstOffset, int bitsPerPixel, int scanlineStride) throws Exception
    {
        int bytesPerRow = (mSrcWidth * bitsPerPixel + 7) / 8;
        int shortsPerRow = bytesPerRow / 2;

        byte[] b = new byte[bytesPerRow * mSrcHeight];
        decodeRaw(b, 0, bitsPerPixel, bytesPerRow);

        int bOffset = 0;
        if (mMetadata.getByteOrder() == ByteOrder.BIG_ENDIAN)
        {
            for (int j = 0; j < mSrcHeight; j++)
            {
                for (int i = 0; i < shortsPerRow; i++)
                {
                    short hiVal = b[bOffset++];
                    short loVal = b[bOffset++];
                    short sval = (short) ((hiVal << 8) | (loVal & 0xff));
                    s[dstOffset + i] = sval;
                }

                dstOffset += scanlineStride;
            }
        }
        else
        { // ByteOrder.LITLE_ENDIAN
            for (int j = 0; j < mSrcHeight; j++)
            {
                for (int i = 0; i < shortsPerRow; i++)
                {
                    short loVal = b[bOffset++];
                    short hiVal = b[bOffset++];
                    short sval = (short) ((hiVal << 8) | (loVal & 0xff));
                    s[dstOffset + i] = sval;
                }

                dstOffset += scanlineStride;
            }
        }
    }

    private void reformatData(byte[] buf, int bytesPerRow, int numRows, short[] shortData, int[] intData, int outOffset, int outStride) throws IOException
    {
        if (shortData != null)
        {
            int inOffset = 0;
            int shortsPerRow = bytesPerRow / 2;
            int numExtraBytes = bytesPerRow % 2;
            for (int j = 0; j < numRows; j++)
            {
                int k = outOffset;
                for (int i = 0; i < shortsPerRow; i++)
                    shortData[k++] = (short) (((buf[inOffset++] & 0xff) << 8) | (buf[inOffset++] & 0xff));

                if (numExtraBytes != 0)
                    shortData[k++] = (short) ((buf[inOffset++] & 0xff) << 8);

                outOffset += outStride;
            }
        }
        else if (intData != null)
        {
            int inOffset = 0;
            int intsPerRow = bytesPerRow / 4;
            int numExtraBytes = bytesPerRow % 4;
            for (int j = 0; j < numRows; j++)
            {
                int k = outOffset;
                for (int i = 0; i < intsPerRow; i++)
                    intData[k++] = ((buf[inOffset++] & 0xff) << 24) | ((buf[inOffset++] & 0xff) << 16) | ((buf[inOffset++] & 0xff) << 8) | (buf[inOffset++] & 0xff);

                if (numExtraBytes != 0)
                {
                    int shift = 24;
                    int ival = 0;
                    for (int b = 0; b < numExtraBytes; b++)
                    {
                        ival |= (buf[inOffset++] & 0xff) << shift;
                        shift -= 8;
                    }
                    intData[k++] = ival;
                }
                outOffset += outStride;
            }
        }
        else
            throw new IOException("shortData == null && intData == null!");
    }

    public void decodeRaw(int[] i, int dstOffset, int bitsPerPixel, int scanlineStride) throws Exception
    {
        int numBands = bitsPerPixel / 32;
        int intsPerRow = mSrcWidth * numBands;
        int bytesPerRow = intsPerRow * 4;

        byte[] b = new byte[bytesPerRow * mSrcHeight];
        decodeRaw(b, 0, bitsPerPixel, bytesPerRow);

        int bOffset = 0;
        if (mMetadata.getByteOrder() == ByteOrder.BIG_ENDIAN)
        {
            for (int j = 0; j < mSrcHeight; j++)
            {
                for (int k = 0; k < intsPerRow; k++)
                {
                    int v0 = b[bOffset++] & 0xff;
                    int v1 = b[bOffset++] & 0xff;
                    int v2 = b[bOffset++] & 0xff;
                    int v3 = b[bOffset++] & 0xff;
                    int ival = (v0 << 24) | (v1 << 16) | (v2 << 8) | v3;
                    i[dstOffset + k] = ival;
                }

                dstOffset += scanlineStride;
            }
        }
        else
        { // ByteOrder.LITLE_ENDIAN
            for (int j = 0; j < mSrcHeight; j++)
            {
                for (int k = 0; k < intsPerRow; k++)
                {
                    int v3 = b[bOffset++] & 0xff;
                    int v2 = b[bOffset++] & 0xff;
                    int v1 = b[bOffset++] & 0xff;
                    int v0 = b[bOffset++] & 0xff;
                    int ival = (v0 << 24) | (v1 << 16) | (v2 << 8) | v3;
                    i[dstOffset + k] = ival;
                }

                dstOffset += scanlineStride;
            }
        }
    }

    private void reformatDiscontiguousData(byte[] buf, int stride, int w, int h) throws IOException
    {
        // Get SampleModel info.
        SampleModel sm = rawImage.getSampleModel();
        int numBands = sm.getNumBands();
        int[] sampleSize = sm.getSampleSize();

        // Initialize input stream.
        //ByteArrayInputStream is = new ByteArrayInputStream(buf);
        MemoryStream iis = new MemoryStream(buf);

        //  StringBuilder str = new StringBuilder();

        int bitOffset = 0;
        int currentByte = 0;

        // Reformat.
        long iisPosition = 0L;
        int y = mSrcMinY;//raster.getMinY();
        for (int j = 0; j < h; j++, y++)
        {
            iis.setPosition(iisPosition);
            bitOffset = 0;

            int x = mSrcMinX;//raster.getMinX();
            for (int i = 0; i < w; i++, x++)
            {
                for (int b = 0; b < numBands; b++)
                {
                    if (bitOffset == 0)
                        currentByte = iis.readByte();

                    long bits = readBits(currentByte, sampleSize[b], bitOffset);
                    bitOffset = (bitOffset + sampleSize[b]) & 0x7; // turns 0 if it equals 8

                    sm.setSample(x, y, b, (int) bits, rawImage.getDataBuffer());
                }
            }
            iisPosition += stride;
        }

    }

    private void setRect(SampleModel srcChild, DataBuffer srcDataBuffer, SampleModel dstChild, DataBuffer dstDataBuffer)
    {
        if (isBytePackedRaster(rawImage) && isBytePackedRaster(mImage) && ((MultiPixelPackedSampleModel) srcChild).getPixelBitStride() == ((MultiPixelPackedSampleModel) dstChild).getPixelBitStride())
            setRectByteInterleaved(srcChild, srcDataBuffer, dstChild, dstDataBuffer);

        else
            setRectDefault(srcChild, srcDataBuffer, dstChild, dstDataBuffer);
    }

    public long readBits(int currentByte, int numBits, int bitOffset) throws IOException
    {

        if (numBits < 0 || numBits > 64)
            throw new IllegalArgumentException();

        if (numBits == 0)
            return 0L;

        int val = currentByte;
        if (val == -1)
            throw new EOFException();

        int bitsToRead = numBits + bitOffset;

        long accum = 0L;
        accum <<= 8;
        accum |= val;
        bitsToRead -= 8;

        // Shift away unwanted bits on the right.
        accum >>>= (-bitsToRead); // Negative of bitsToRead == extra bits read

        // Mask out unwanted bits on the left
        accum &= (-1L >>> (64 - numBits));

        return accum;
    }

    //This class is useful for describing 1, 2, or 4 bit image data elements.
    // This raster has one band whose pixels are packed together into individual bytes in a single byte array.
    // This type of raster can be used with an IndexColorModel.
    // This raster uses a MultiPixelPackedSampleModel
    private boolean isBytePackedRaster(TiffBitmapData img)
    {
        SampleModel sampleModel = img.getSampleModel();
        int numDataElements = sampleModel.getNumDataElements();
        //ColorModel colorModel = img.getColorModel();

        return (sampleModel instanceof MultiPixelPackedSampleModel)
                && (numDataElements == 1 || numDataElements == 2 || numDataElements == 4)
                && img.getDataBuffer() instanceof DataBufferByte;
    }

    private void setRectByteInterleaved(SampleModel srcChild, DataBuffer srcDataBuffer, SampleModel dstChild, DataBuffer dstDataBuffer)
    {
        int dx = 0;
        int dy = 0;
        int minX = 0;
        int minY = 0;

        int maxX = mImage.getWidth();
        int maxY = mImage.getHeight();

        int width = rawImage.getWidth();//srcRaster.getWidth();
        int height = rawImage.getHeight();// srcRaster.getHeight();
        int srcOffX = mSrcMinX; //srcRaster.getMinX();
        int srcOffY = mSrcMinY;// srcRaster.getMinY();

        int dstOffX = dx + srcOffX;
        int dstOffY = dy + srcOffY;

        // Clip to this raster
        if (dstOffX < minX)
        {
            int skipX = minX - dstOffX;
            width -= skipX;
            srcOffX += skipX;
            dstOffX = minX;
        }
        if (dstOffY < minY)
        {
            int skipY = minY - dstOffY;
            height -= skipY;
            srcOffY += skipY;
            dstOffY = minY;
        }
        if (dstOffX + width > maxX)
            width = maxX - dstOffX;

        if (dstOffY + height > maxY)
            height = maxY - dstOffY;

        if (width <= 0 || height <= 0)
            return;

        byte[] inData = ((DataBufferByte) srcDataBuffer).getData();
        byte[] outData = ((DataBufferByte) dstDataBuffer).getData();

        int inRasterMinY = 0;
        int inRasterMinX = 0;

        MultiPixelPackedSampleModel inRaster = ((MultiPixelPackedSampleModel) srcChild);
        MultiPixelPackedSampleModel dstRaster = ((MultiPixelPackedSampleModel) dstChild);

        int inscan = inRaster.getScanlineStride();//inRaster.scanlineStride;
        int outscan = dstRaster.getScanlineStride();//this.scanlineStride;
        int inbit = inRaster.getDataBitOffset() + 8 * (srcOffY - inRasterMinY) * inscan + (srcOffX - inRasterMinX) * inRaster.getPixelBitStride();
        int outbit = (dstRaster.getDataBitOffset() + 8 * (dstOffY - minY) * outscan + (dstOffX - minX) * dstRaster.getPixelBitStride());
        int copybits = width * dstRaster.getPixelBitStride();

        // Check whether the same bit alignment is present in both
        // Rasters; if so, we can copy whole bytes using
        // System.arraycopy.  If not, we must do a "funnel shift"
        // where adjacent bytes contribute to each destination byte.
        if ((inbit & 7) == (outbit & 7))
        {
            // copy is bit aligned
            int bitpos = outbit & 7;
            if (bitpos != 0)
            {
                int bits = 8 - bitpos;
                // Copy partial bytes on left
                int inbyte = inbit >> 3;
                int outbyte = outbit >> 3;
                int mask = 0xff >> bitpos;
                if (copybits < bits)
                {
                    // Fix bug 4399076: previously had '8 - copybits' instead
                    // of 'bits - copybits'.
                    //
                    // Prior to the this expression, 'mask' has its rightmost
                    // 'bits' bits set to '1'.  We want it to have a total
                    // of 'copybits' bits set, therefore we want to introduce
                    // 'bits - copybits' zeroes on the right.
                    mask &= 0xff << (bits - copybits);
                    bits = copybits;
                }
                for (int j = 0; j < height; j++)
                {
                    int element = outData[outbyte];
                    element &= ~mask;
                    element |= (inData[inbyte] & mask);
                    outData[outbyte] = (byte) element;
                    inbyte += inscan;
                    outbyte += outscan;
                }
                inbit += bits;
                outbit += bits;
                copybits -= bits;
            }
            if (copybits >= 8)
            {
                // Copy whole bytes
                int inbyte = inbit >> 3;
                int outbyte = outbit >> 3;
                int copybytes = copybits >> 3;
                if (copybytes == inscan && inscan == outscan)
                    System.arraycopy(inData, inbyte, outData, outbyte, inscan * height);

                else
                {
                    for (int j = 0; j < height; j++)
                    {
                        System.arraycopy(inData, inbyte, outData, outbyte, copybytes);
                        inbyte += inscan;
                        outbyte += outscan;
                    }
                }

                int bits = copybytes * 8;
                inbit += bits;
                outbit += bits;
                copybits -= bits;
            }
            if (copybits > 0)
            {
                // Copy partial bytes on right
                int inbyte = inbit >> 3;
                int outbyte = outbit >> 3;
                int mask = (0xff00 >> copybits) & 0xff;
                for (int j = 0; j < height; j++)
                {
                    int element = outData[outbyte];
                    element &= ~mask;
                    element |= (inData[inbyte] & mask);
                    outData[outbyte] = (byte) element;
                    inbyte += inscan;
                    outbyte += outscan;
                }
            }
        }
        else
        {
            // Unaligned case, see RFE #4284166
            // Note that the code in that RFE is not correct

            // Insert bits into the first byte of the output
            // if either the starting bit position is not zero or
            // we are writing fewer than 8 bits in total
            int bitpos = outbit & 7;
            if (bitpos != 0 || copybits < 8)
            {
                int bits = 8 - bitpos;
                int inbyte = inbit >> 3;
                int outbyte = outbit >> 3;

                int lshift = inbit & 7;
                int rshift = 8 - lshift;
                int mask = 0xff >> bitpos;
                if (copybits < bits)
                {
                    // Fix mask if we're only writing a partial byte
                    mask &= 0xff << (bits - copybits);
                    bits = copybits;
                }
                int lastByte = inData.length - 1;
                for (int j = 0; j < height; j++)
                {
                    // Read two bytes from the source if possible
                    // Don't worry about going over a scanline boundary
                    // since any extra bits won't get used anyway
                    byte inData0 = inData[inbyte];
                    byte inData1 = (byte) 0;
                    if (inbyte < lastByte)
                        inData1 = inData[inbyte + 1];

                    // Insert the new bits into the output
                    int element = outData[outbyte];
                    element &= ~mask;
                    element |= (((inData0 << lshift) | ((inData1 & 0xff) >> rshift)) >> bitpos) & mask;
                    outData[outbyte] = (byte) element;
                    inbyte += inscan;
                    outbyte += outscan;
                }

                inbit += bits;
                outbit += bits;
                copybits -= bits;
            }

            // Now we have outbit & 7 == 0 so we can write
            // complete bytes for a while

            // Make sure we have work to do in the central loop
            // to avoid reading past the end of the scanline
            if (copybits >= 8)
            {
                int inbyte = inbit >> 3;
                int outbyte = outbit >> 3;
                int copybytes = copybits >> 3;
                int lshift = inbit & 7;
                int rshift = 8 - lshift;

                for (int j = 0; j < height; j++)
                {
                    int ibyte = inbyte + j * inscan;
                    int obyte = outbyte + j * outscan;

                    int inData0 = inData[ibyte];
                    // Combine adjacent bytes while 8 or more bits left
                    for (int i = 0; i < copybytes; i++)
                    {
                        int inData1 = inData[ibyte + 1];
                        int val = (inData0 << lshift) | ((inData1 & 0xff) >> rshift);
                        outData[obyte] = (byte) val;
                        inData0 = inData1;

                        ++ibyte;
                        ++obyte;
                    }
                }

                int bits = copybytes * 8;
                inbit += bits;
                outbit += bits;
                copybits -= bits;
            }

            // Finish last byte
            if (copybits > 0)
            {
                int inbyte = inbit >> 3;
                int outbyte = outbit >> 3;
                int mask = (0xff00 >> copybits) & 0xff;
                int lshift = inbit & 7;
                int rshift = 8 - lshift;

                int lastByte = inData.length - 1;
                for (int j = 0; j < height; j++)
                {
                    byte inData0 = inData[inbyte];
                    byte inData1 = (byte) 0;
                    if (inbyte < lastByte)
                        inData1 = inData[inbyte + 1];

                    // Insert the new bits into the output
                    int element = outData[outbyte];
                    element &= ~mask;
                    element |= ((inData0 << lshift) | ((inData1 & 0xff) >> rshift)) & mask;
                    outData[outbyte] = (byte) element;

                    inbyte += inscan;
                    outbyte += outscan;
                }
            }
        }

    }

    private void setRectDefault(SampleModel srcChild, DataBuffer srcDataBuffer, SampleModel dstChild, DataBuffer dstDataBuffer)
    {
        int dx = 0;
        int dy = 0;
        int minX = 0;
        int minY = 0;

        int width = rawImage.getWidth();//srcRaster.getWidth();
        int height = rawImage.getHeight();// srcRaster.getHeight();
        int srcOffX = mSrcMinX; //srcRaster.getMinX();
        int srcOffY = mSrcMinY;// srcRaster.getMinY();
        int dstOffX = dx + srcOffX;
        int dstOffY = dy + srcOffY;

        // Clip to this raster

        if (dstOffX < minX)
        {
            int skipX = minX - dstOffX;
            width -= skipX;
            srcOffX += skipX;
            dstOffX = minX;
        }
        if (dstOffY < minY)
        {
            int skipY = minY - dstOffY;
            height -= skipY;
            srcOffY += skipY;
            dstOffY = minY;
        }
        if (dstOffX + width > minX + width)
            width = minX + width - dstOffX;

        if (dstOffY + height > minY + height)
            height = minY + height - dstOffY;

        if (width <= 0 || height <= 0)
            return;

        switch (srcChild.getDataType())
        {
            case DataBuffer.TYPE_BYTE:
            case DataBuffer.TYPE_SHORT:
            case DataBuffer.TYPE_USHORT:
            case DataBuffer.TYPE_INT:
                int[] iData = null;
                for (int startY = 0; startY < height; startY++)
                {
                    // Grab one scanline at a time
                    iData = srcChild.getPixels(srcOffX, srcOffY + startY, width, 1, iData, srcDataBuffer);
                    dstChild.setPixels(dstOffX, dstOffY + startY, width, 1, iData, dstDataBuffer);
                }
                break;

            case DataBuffer.TYPE_FLOAT:
                float[] fData = null;
                for (int startY = 0; startY < height; startY++)
                {
                    fData = srcChild.getPixels(srcOffX, srcOffY + startY, width, 1, fData, srcDataBuffer);
                    dstChild.setPixels(dstOffX, dstOffY + startY, width, 1, fData, dstDataBuffer);
                }
                break;

            case DataBuffer.TYPE_DOUBLE:
                double[] dData = null;
                for (int startY = 0; startY < height; startY++)
                {
                    // Grab one scanline at a time
                    dData = srcChild.getPixels(srcOffX, srcOffY + startY, width, 1, dData, srcDataBuffer);
                    dstChild.setPixels(dstOffX, dstOffY + startY, width, 1, dData, dstDataBuffer);
                }
                break;
        }
    }

    public void setDstMinX(int dstMinX)
    {
        mDstMinX = dstMinX;
    }

    public void setColorConverter(TIFFColorConverter colorConverter) { mColorConverter = colorConverter; }

    public void setDstWidth(int dstWidth)
    {
        mDstWidth = dstWidth;
    }

    public void setDstHeight(int dstHeight)
    {
        mDstHeight = dstHeight;
    }

    public void setSampleFormat(int[] sampleFormat)
    {
        mSampleFormat = sampleFormat;
    }

    public void setSubsampleX(int subsampleX)
    {
        mSubsampleX = subsampleX;
    }

    public void setSubsampleY(int subsampleY)
    {
        mSubsampleY = subsampleY;
    }

    public void setBitsPerSample(int[] bitsPerSample)
    {
        mBitsPerSample = bitsPerSample;
    }

    public void setSourceXOffset(int sourceXOffset)
    {
        mSourceXOffset = sourceXOffset;
    }

    public void setSourceYOffset(int sourceYOffset)
    {
        mSourceYOffset = sourceYOffset;
    }

    public void setDstXOffset(int dstXOffset)
    {
        mDstXOffset = dstXOffset;
    }

    public void setDstYOffset(int dstYOffset)
    {
        mDstYOffset = dstYOffset;
    }

    public void setSourceBands(int[] sourceBands)
    {
        mSourceBands = sourceBands;
    }

    public boolean isPlanar()
    {
        return mPlanar;
    }

    public void setPlanar(boolean planar)
    {
        mPlanar = planar;
    }

    public void setByteCount(int byteCount)
    {
        mByteCount = byteCount;
    }

    public void setReader(ImageStreamReader reader)
    {
        mReader = reader;
    }

    public void setSrcMinX(int srcMinX)
    {
        mSrcMinX = srcMinX;
    }

    public void setSrcMinY(int srcMinY)
    {
        mSrcMinY = srcMinY;
    }

    public void setSrcWidth(int srcWidth)
    {
        mSrcWidth = srcWidth;
    }

    public void setSrcHeight(int srcHeight)
    {
        mSrcHeight = srcHeight;
    }

    public void setSamplesPerPixel(int samplesPerPixel)
    {
        mSamplesPerPixel = samplesPerPixel;
    }

    public void setImage(TiffBitmapData image)
    {
        mImage = image;
    }

    public void setDstMinY(int dstMinY)
    {
        mDstMinY = dstMinY;
    }

    public void setDestinationBands(int[] destinationBands) { this.mDestinationBands = destinationBands == null ? null : (int[]) destinationBands.clone(); }

    public void setActiveSrcWidth(int activeSrcWidth)
    {
        this.activeSrcWidth = activeSrcWidth;
    }


    public void setActiveSrcMinX(int activeSrcMinX)
    {
        this.activeSrcMinX = activeSrcMinX;
    }

    public void setActiveSrcMinY(int activeSrcMinY)
    {
        this.activeSrcMinY = activeSrcMinY;
    }

    public void setActiveSrcHeight(int activeSrcHeight)
    {
        this.activeSrcHeight = activeSrcHeight;
    }


    protected int[] mBitsPerSample;
    protected int mSourceXOffset;
    protected int mSourceYOffset;
    protected int mDstXOffset;
    protected int mDstYOffset;
    protected int[] mSourceBands;
    protected boolean mPlanar;
    protected Stream mStream;
    protected long mOffset;
    protected int mByteCount;
    protected ImageStreamReader mReader;
    protected int mPredictor;

    protected int mSrcMinX;
    protected int mSrcMinY;
    protected int mSrcWidth;
    protected int mSrcHeight;
    protected int mSamplesPerPixel;
    protected int mDstMinY;
    protected TiffMetadata mMetadata;
    protected TiffBitmapData mImage; // result image
    protected int activeSrcMinX;
    protected int activeSrcMinY;
    protected int activeSrcWidth;
    protected int activeSrcHeight;
    private int[] mDestinationBands;
    private int mDstMinX;
    private TIFFColorConverter mColorConverter;
    private int mDstWidth;
    private int mDstHeight;
    private int[] mSampleFormat;
    private boolean mAdjustBitDepths;
    private boolean isBilevel;
    private boolean planarCache;
    private boolean isContiguous;
    private int[][] bitDepthScale;
    private int mSubsampleX;
    private int mSubsampleY;
    private boolean isImageSimple;
    private TiffBitmapData rawImage;
}
