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


import com.aspose.ms.java.ImageIO.tiff.TifImageUtil;
import com.aspose.ms.java.ImageIO.tiff.colorpsace.BogusColorSpace;
import com.aspose.ms.java.ImageIO.tiff.colorpsace.SimpleCMYKColorSpace;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.ColorModel;
import java.awt.image.*;

/**
 * Creates {@link SampleModel}, {@link ColorModel}, {@link ColorSpace} according to metadata.
 * Also it creates  {@link DataBuffer} to hold decoded pixels data.
 */
public class TiffBitmapData
{
    public DataBuffer getDataBuffer() { return mDataBuffer; }

    public void setDataBuffer(DataBuffer dataBuffer) { mDataBuffer = dataBuffer; }

    public boolean isHasAlpha() { return hasAlpha; }

    public void setHasAlpha(boolean hasAlpha) { this.hasAlpha = hasAlpha; }

    public int getWidth() { return mMetadata.getWidth(); }

    public int getHeight() { return mMetadata.getHeight(); }

    public java.awt.image.ColorModel getColorModel()
    {
        return mColorModel;
    }

    public SampleModel getSampleModel()
    {
        return mSampleModel;
    }

    public TiffMetadata getMetadata() { return mMetadata; }

    public void setMetadata(TiffMetadata metadata) { mMetadata = metadata; }

    public static TiffBitmapData createBitmapData(ColorModel cm, SampleModel sm, TiffMetadata metadata) { return new TiffBitmapData(cm, sm, metadata); }

    public static TiffBitmapData createBitmapData(TiffMetadata metadata)
    {
        TiffBitmapData image = createBitmapData(
                metadata.getPhotometricInterpretation(), metadata.getCompression(),
                metadata.getSamplesPerPixel(), metadata.getBitsPerSample(),
                metadata.getSampleFormat(), metadata.getExtraSamples(),
                metadata.getColorMap(), metadata.getWidth(), metadata.getHeight());

        image.mMetadata = metadata;
        return image;
    }

    private static TiffBitmapData createBitmapData(int photometricInterpretation,
                                                   int compression, int samplesPerPixel,
                                                   int[] bitsPerSample, int[] sampleFormat,
                                                   int[] extraSamples, char[] colorMap,
                                                   int width, int height)
    {
        // 1, 2, 4, 8, or 16 bit grayscale or indexed images
        if (samplesPerPixel == 1 && (bitsPerSample[0] == 1 || bitsPerSample[0] == 2 || bitsPerSample[0] == 4 || bitsPerSample[0] == 8 || bitsPerSample[0] == 16))
        {
            if (colorMap == null)
            {
                // Grayscale
                boolean isSigned = (sampleFormat[0] == BaselineTIFFTags.SAMPLE_FORMAT_SIGNED_INTEGER);
                int dataType;
                if (bitsPerSample[0] <= 8)
                    dataType = DataBuffer.TYPE_BYTE;

                else
                    dataType = sampleFormat[0] == BaselineTIFFTags.SAMPLE_FORMAT_SIGNED_INTEGER ? DataBuffer.TYPE_SHORT : DataBuffer.TYPE_USHORT;

                return createGrayscale(bitsPerSample[0], dataType, isSigned, width, height);
            }
            else
            {
                //Indexed
                int mapSize = 1 << bitsPerSample[0];
                byte[] redLut = new byte[mapSize];
                byte[] greenLut = new byte[mapSize];
                byte[] blueLut = new byte[mapSize];
                byte[] alphaLut = null;

                for (int i = 0; i < mapSize; i++)
                {
                    redLut[i] = (byte) ((colorMap[i] * 255) / 65535);
                    greenLut[i] = (byte) ((colorMap[mapSize + i] * 255) / 65535);
                    blueLut[i] = (byte) ((colorMap[2 * mapSize + i] * 255) / 65535);
                }

                int dataType = bitsPerSample[0] == 8 ? DataBuffer.TYPE_BYTE : DataBuffer.TYPE_USHORT;
                return createIndexed(dataType, bitsPerSample[0], redLut, greenLut, blueLut, alphaLut, width, height);
            }
        }

        // 8-bit gray-alpha
        if (samplesPerPixel == 2 && bitsPerSample[0] == 8 && bitsPerSample[1] == 8)
        {
            int dataType = DataBuffer.TYPE_BYTE;
            boolean alphaPremultiplied = false;
            if (extraSamples != null && extraSamples[0] == BaselineTIFFTags.EXTRA_SAMPLES_ASSOCIATED_ALPHA)
                alphaPremultiplied = true;

            return createGrayscale(8, dataType, false, alphaPremultiplied, width, height);
        }

        // 16-bit gray-alpha
        if (samplesPerPixel == 2 && bitsPerSample[0] == 16 && bitsPerSample[1] == 16)
        {
            int dataType = sampleFormat[0] == BaselineTIFFTags.SAMPLE_FORMAT_SIGNED_INTEGER ? DataBuffer.TYPE_SHORT : DataBuffer.TYPE_USHORT;
            boolean alphaPremultiplied = false;
            if (extraSamples != null && extraSamples[0] == BaselineTIFFTags.EXTRA_SAMPLES_ASSOCIATED_ALPHA)
                alphaPremultiplied = true;

            boolean isSigned = dataType == DataBuffer.TYPE_SHORT;
            return createGrayscale(16, dataType, isSigned, alphaPremultiplied, width, height);
        }

        ColorSpace rgb = ColorSpace.getInstance(ColorSpace.CS_sRGB);

        // 8-bit RGB
        if (samplesPerPixel == 3 && bitsPerSample[0] == 8 && bitsPerSample[1] == 8 && bitsPerSample[2] == 8)
        {
            int[] bandOffsets = new int[3];
            bandOffsets[0] = 0;
            bandOffsets[1] = 1;
            bandOffsets[2] = 2;

            int dataType = DataBuffer.TYPE_BYTE;
            ColorSpace theColorSpace;

            if ((photometricInterpretation == BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_Y_CB_CR
                    && compression != BaselineTIFFTags.COMPRESSION_JPEG && compression != BaselineTIFFTags.COMPRESSION_OLD_JPEG)
                    || photometricInterpretation == BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_CIELAB)
                theColorSpace = ColorSpace.getInstance(ColorSpace.CS_LINEAR_RGB);
            else
                theColorSpace = rgb;

            return createInterleaved(theColorSpace, bandOffsets, dataType, false, false, width, height);
        }

        // 8-bit RGBA
        if (samplesPerPixel == 4 && bitsPerSample[0] == 8 && bitsPerSample[1] == 8 && bitsPerSample[2] == 8 && bitsPerSample[3] == 8)
        {
            int[] bandOffsets = new int[4];
            bandOffsets[0] = 0;
            bandOffsets[1] = 1;
            bandOffsets[2] = 2;
            bandOffsets[3] = 3;

            int dataType = DataBuffer.TYPE_BYTE;
            ColorSpace theColorSpace;

            boolean hasAlpha;
            boolean alphaPremultiplied = false;
            if (photometricInterpretation == BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_CMYK)
            {
                theColorSpace = SimpleCMYKColorSpace.getInstance();
                hasAlpha = false;
            }
            else
            {
                theColorSpace = rgb;
                hasAlpha = true;
                if (extraSamples != null && extraSamples[0] == BaselineTIFFTags.EXTRA_SAMPLES_ASSOCIATED_ALPHA)
                    alphaPremultiplied = true;
            }

            return createInterleaved(theColorSpace, bandOffsets, dataType, hasAlpha, alphaPremultiplied, width, height);
        }

        // 16-bit RGB
        if (samplesPerPixel == 3 && bitsPerSample[0] == 16 && bitsPerSample[1] == 16 && bitsPerSample[2] == 16)
        {
            int[] bandOffsets = new int[3];
            bandOffsets[0] = 0;
            bandOffsets[1] = 1;
            bandOffsets[2] = 2;
            int dataType = sampleFormat[0] == BaselineTIFFTags.SAMPLE_FORMAT_SIGNED_INTEGER ? DataBuffer.TYPE_SHORT : DataBuffer.TYPE_USHORT;
            return createInterleaved(rgb, bandOffsets, dataType, false, false, width, height);

        }

        // 16-bit RGBA
        if (samplesPerPixel == 4 && bitsPerSample[0] == 16 && bitsPerSample[1] == 16 && bitsPerSample[2] == 16 && bitsPerSample[3] == 16)
        {
            int[] bandOffsets = new int[4];
            bandOffsets[0] = 0;
            bandOffsets[1] = 1;
            bandOffsets[2] = 2;
            bandOffsets[3] = 3;
            int dataType = sampleFormat[0] == BaselineTIFFTags.SAMPLE_FORMAT_SIGNED_INTEGER ? DataBuffer.TYPE_SHORT : DataBuffer.TYPE_USHORT;

            boolean alphaPremultiplied = false;
            if (extraSamples != null && extraSamples[0] == BaselineTIFFTags.EXTRA_SAMPLES_ASSOCIATED_ALPHA)
                alphaPremultiplied = true;

            return createInterleaved(rgb, bandOffsets, dataType, true, alphaPremultiplied, width, height);
        }


        // Support for Tiff files containing half-tone data in more than 1 channel
        if ((photometricInterpretation == BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_CMYK) && (bitsPerSample[0] == 1 || bitsPerSample[0] == 2 || bitsPerSample[0] == 4))
        {
            ColorSpace cs = null;
            if (samplesPerPixel == 4)
                cs = SimpleCMYKColorSpace.getInstance();
            else
                cs = new BogusColorSpace(samplesPerPixel);
            // By specifying the bits per sample the color values
            // will scale on display
            ColorModel cm = new ComponentColorModel(cs, bitsPerSample, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
            return new TiffBitmapData(cm, cm.createCompatibleSampleModel(width, height));
        }

        // Compute bits per pixel.
        int totalBits = 0;
        for (int i = 0; i < bitsPerSample.length; i++)
            totalBits += bitsPerSample[i];

        // Packed: 3- or 4-band, 8- or 16-bit.
        if ((samplesPerPixel == 3 || samplesPerPixel == 4) && (totalBits == 8 || totalBits == 16))
        {
            int redMask = TifImageUtil.createMask(bitsPerSample, 0);
            int greenMask = TifImageUtil.createMask(bitsPerSample, 1);
            int blueMask = TifImageUtil.createMask(bitsPerSample, 2);
            int alphaMask = (samplesPerPixel == 4) ? TifImageUtil.createMask(bitsPerSample, 3) : 0;
            int transferType = totalBits == 8 ? DataBuffer.TYPE_BYTE : DataBuffer.TYPE_USHORT;
            boolean alphaPremultiplied = false;
            if (extraSamples != null && extraSamples[0] == BaselineTIFFTags.EXTRA_SAMPLES_ASSOCIATED_ALPHA)
                alphaPremultiplied = true;

            return createPacked(rgb, redMask, greenMask, blueMask, alphaMask, transferType, alphaPremultiplied, width, height);
        }

        // Generic components with 8X bits per sample.
        if (bitsPerSample[0] % 8 == 0)
        {
            // Check whether all bands have same bit depth.
            boolean allSameBitDepth = true;
            for (int i = 1; i < bitsPerSample.length; i++)
            {
                if (bitsPerSample[i] != bitsPerSample[i - 1])
                {
                    allSameBitDepth = false;
                    break;
                }
            }

            // Proceed if all bands have same bit depth.
            if (allSameBitDepth)
            {
                // Determine the data type.
                int dataType = -1;
                boolean isDataTypeSet = false;
                switch (bitsPerSample[0])
                {
                    case 8:
                        if (sampleFormat[0] != BaselineTIFFTags.SAMPLE_FORMAT_FLOATING_POINT)
                        {
                            // Ignore whether signed or unsigned:
                            // treat all as unsigned.
                            dataType = DataBuffer.TYPE_BYTE;
                            isDataTypeSet = true;
                        }
                        break;
                    case 16:
                        if (sampleFormat[0] != BaselineTIFFTags.SAMPLE_FORMAT_FLOATING_POINT)
                        {
                            if (sampleFormat[0] == BaselineTIFFTags.SAMPLE_FORMAT_SIGNED_INTEGER)
                                dataType = DataBuffer.TYPE_SHORT;

                            else
                                dataType = DataBuffer.TYPE_USHORT;

                            isDataTypeSet = true;
                        }
                        break;
                    case 32:
                        if (sampleFormat[0] == BaselineTIFFTags.SAMPLE_FORMAT_FLOATING_POINT)
                            dataType = DataBuffer.TYPE_FLOAT;

                        else
                            dataType = DataBuffer.TYPE_INT;

                        isDataTypeSet = true;
                        break;
                }

                if (isDataTypeSet)
                {
                    // Create the SampleModel.
                    SampleModel sm = createInterleavedSM(dataType, samplesPerPixel, 1, 1);

                    // Create the ColorModel.
                    ColorModel cm;
                    if (samplesPerPixel >= 1 && samplesPerPixel <= 4 && (dataType == DataBuffer.TYPE_INT || dataType == DataBuffer.TYPE_FLOAT))
                    {
                        // Handle the 32-bit cases for 1-4 bands.
                        ColorSpace cs = samplesPerPixel <= 2 ? ColorSpace.getInstance(ColorSpace.CS_GRAY) : rgb;
                        boolean hasAlpha = ((samplesPerPixel % 2) == 0);
                        boolean alphaPremultiplied = false;
                        if (hasAlpha && extraSamples != null && extraSamples[0] == BaselineTIFFTags.EXTRA_SAMPLES_ASSOCIATED_ALPHA)
                            alphaPremultiplied = true;

                        cm = createComponentCM(cs, samplesPerPixel, dataType, hasAlpha, alphaPremultiplied);
                    }
                    else
                    {
                        ColorSpace cs = new BogusColorSpace(samplesPerPixel);
                        cm = createComponentCM(cs, samplesPerPixel, dataType, false, false);
                    }

                    //System.out.println(cm); // XXX
                    return new TiffBitmapData(cm, sm.createCompatibleSampleModel(width, height));
                }
            }
        }

        // Other more bizarre cases including discontiguous DataBuffers
        // such as for the image in bug 4918959.

        if (colorMap == null && sampleFormat[0] != BaselineTIFFTags.SAMPLE_FORMAT_FLOATING_POINT)
        {

            // Determine size of largest sample.
            int maxBitsPerSample = 0;
            for (int i = 0; i < bitsPerSample.length; i++)
            {
                if (bitsPerSample[i] > maxBitsPerSample)
                    maxBitsPerSample = bitsPerSample[i];
            }

            // Determine whether data are signed.
            boolean isSigned = (sampleFormat[0] == BaselineTIFFTags.SAMPLE_FORMAT_SIGNED_INTEGER);

            // Grayscale
            if (samplesPerPixel == 1)
            {
                int dataType = TifImageUtil.getDataTypeFromNumBits(maxBitsPerSample, isSigned);
                return createGrayscale(maxBitsPerSample, dataType, isSigned, width, height);
            }

            // Gray-alpha
            if (samplesPerPixel == 2)
            {
                boolean alphaPremultiplied = false;
                if (extraSamples != null && extraSamples[0] == BaselineTIFFTags.EXTRA_SAMPLES_ASSOCIATED_ALPHA)
                    alphaPremultiplied = true;

                int dataType = TifImageUtil.getDataTypeFromNumBits(maxBitsPerSample, isSigned);
                return createGrayscale(maxBitsPerSample, dataType, false, alphaPremultiplied, width, height);
            }

            if (samplesPerPixel == 3 || samplesPerPixel == 4)
            {
                if (totalBits <= 32 && !isSigned)
                {
                    // Packed RGB or RGBA
                    int redMask = TifImageUtil.createMask(bitsPerSample, 0);
                    int greenMask = TifImageUtil.createMask(bitsPerSample, 1);
                    int blueMask = TifImageUtil.createMask(bitsPerSample, 2);
                    int alphaMask = (samplesPerPixel == 4) ? TifImageUtil.createMask(bitsPerSample, 3) : 0;
                    int transferType = TifImageUtil.getDataTypeFromNumBits(totalBits, false);
                    boolean alphaPremultiplied = false;
                    if (extraSamples != null && extraSamples[0] == BaselineTIFFTags.EXTRA_SAMPLES_ASSOCIATED_ALPHA)
                        alphaPremultiplied = true;

                    return createPacked(rgb, redMask, greenMask, blueMask, alphaMask, transferType, alphaPremultiplied, width, height);
                }
                else if (samplesPerPixel == 3)
                {
                    // Interleaved RGB
                    int[] bandOffsets = new int[]{0, 1, 2};
                    int dataType = TifImageUtil.getDataTypeFromNumBits(maxBitsPerSample, isSigned);
                    return createInterleaved(rgb, bandOffsets, dataType, false, false, width, height);
                }
                else if (samplesPerPixel == 4)
                {
                    // Interleaved RGBA
                    int[] bandOffsets = new int[]{0, 1, 2, 3};
                    int dataType = TifImageUtil.getDataTypeFromNumBits(maxBitsPerSample, isSigned);
                    boolean alphaPremultiplied = false;
                    if (extraSamples != null && extraSamples[0] == BaselineTIFFTags.EXTRA_SAMPLES_ASSOCIATED_ALPHA)
                        alphaPremultiplied = true;

                    return createInterleaved(rgb, bandOffsets, dataType, true, alphaPremultiplied, width, height);
                }
            }
            else
            {
                // Arbitrary Interleaved.
                int dataType = TifImageUtil.getDataTypeFromNumBits(maxBitsPerSample, isSigned);
                SampleModel sm = createInterleavedSM(dataType, samplesPerPixel, width, height);
                ColorSpace cs = new BogusColorSpace(samplesPerPixel);
                ColorModel cm = createComponentCM(cs, samplesPerPixel, dataType, false, false);
                return new TiffBitmapData(cm, sm);
            }
        }

        return null;
    }

    private static TiffBitmapData createGrayscale(int bits, int dataType, boolean isSigned, int w, int h)
    {
        return createGrayscale(bits, dataType, isSigned, false, false, w, h);
    }

    private static TiffBitmapData createIndexed(int dataType, int bits, byte[] redLUT, byte[] greenLUT, byte[] blueLUT, byte[] alphaLUT, int width, int height)
    {
        if (redLUT == null || greenLUT == null || blueLUT == null)
            throw new IllegalArgumentException("LUT is null!");

        if (bits != 1 && bits != 2 && bits != 4 && bits != 8 && bits != 16)
            throw new IllegalArgumentException("Bad value for bits!");

        if (dataType != DataBuffer.TYPE_BYTE && dataType != DataBuffer.TYPE_SHORT && dataType != DataBuffer.TYPE_USHORT && dataType != DataBuffer.TYPE_INT)
            throw new IllegalArgumentException("Bad value for dataType!");

        if ((bits > 8 && dataType == DataBuffer.TYPE_BYTE) || (bits > 16 && dataType != DataBuffer.TYPE_INT))
            throw new IllegalArgumentException("Too many bits for dataType!");

        int len = 1 << bits;
        if (redLUT.length != len || greenLUT.length != len || blueLUT.length != len || (alphaLUT != null && alphaLUT.length != len))
            throw new IllegalArgumentException("LUT has improper length!");

        ColorModel colorModel = null;
        SampleModel sampleModel = null;

        if (alphaLUT == null)
            colorModel = new IndexColorModel(bits, redLUT.length, redLUT, greenLUT, blueLUT);

        else
            colorModel = new IndexColorModel(bits, redLUT.length, redLUT, greenLUT, blueLUT, alphaLUT);

        if ((bits == 8 && dataType == DataBuffer.TYPE_BYTE) || (bits == 16 && (dataType == DataBuffer.TYPE_SHORT || dataType == DataBuffer.TYPE_USHORT)))
        {
            int[] bandOffsets = {0};
            sampleModel = new PixelInterleavedSampleModel(dataType, width, height, 1, width, bandOffsets);
        }
        else
            sampleModel = new MultiPixelPackedSampleModel(dataType, width, height, bits);

        TiffBitmapData image = new TiffBitmapData(colorModel, sampleModel);
        image.setHasAlpha(alphaLUT != null);
        return image;
    }

    private static TiffBitmapData createGrayscale(int bits, int dataType, boolean isSigned, boolean isAlphaPremultiplied, int w, int h)
    {
        return createGrayscale(bits, dataType, isSigned, true, isAlphaPremultiplied, w, h);
    }

    public static TiffBitmapData createInterleaved(ColorSpace colorSpace, int[] bandOffsets, int dataType, boolean hasAlpha, boolean isAlphaPremultiplied, int w, int h)
    {
        if (colorSpace == null)
            throw new IllegalArgumentException("colorSpace == null!");

        if (bandOffsets == null)
            throw new IllegalArgumentException("bandOffsets == null!");

        int numBands = colorSpace.getNumComponents() + (hasAlpha ? 1 : 0);
        if (bandOffsets.length != numBands)
            throw new IllegalArgumentException("bandOffsets.length is wrong!");

        if (dataType != DataBuffer.TYPE_BYTE && dataType != DataBuffer.TYPE_SHORT && dataType != DataBuffer.TYPE_USHORT && dataType != DataBuffer.TYPE_INT && dataType != DataBuffer.TYPE_FLOAT && dataType != DataBuffer.TYPE_DOUBLE)
            throw new IllegalArgumentException("Bad value for dataType!");


        ColorModel colorModel = createComponentCM(colorSpace, bandOffsets.length, dataType, hasAlpha, isAlphaPremultiplied);

        int minBandOffset = bandOffsets[0];
        int maxBandOffset = minBandOffset;
        for (int i = 0; i < bandOffsets.length; i++)
        {
            int offset = bandOffsets[i];
            minBandOffset = Math.min(offset, minBandOffset);
            maxBandOffset = Math.max(offset, maxBandOffset);
        }
        int pixelStride = maxBandOffset - minBandOffset + 1;

        SampleModel sampleModel = new PixelInterleavedSampleModel(dataType, w, h, pixelStride, w * pixelStride, bandOffsets);
        return new TiffBitmapData(colorModel, sampleModel);
    }

    private static TiffBitmapData createPacked(ColorSpace colorSpace, int redMask, int greenMask, int blueMask, int alphaMask, int transferType, boolean isAlphaPremultiplied, int w, int h)
    {
        if (colorSpace == null)
            throw new IllegalArgumentException("colorSpace == null!");

        if (colorSpace.getType() != ColorSpace.TYPE_RGB)
            throw new IllegalArgumentException("colorSpace is not of type TYPE_RGB!");

        if (transferType != DataBuffer.TYPE_BYTE && transferType != DataBuffer.TYPE_USHORT && transferType != DataBuffer.TYPE_INT)
            throw new IllegalArgumentException("Bad value for transferType!");

        if (redMask == 0 && greenMask == 0 && blueMask == 0 && alphaMask == 0)
            throw new IllegalArgumentException("No mask has at least 1 bit set!");

        int bits = 32;

        DirectColorModel colorModel = new DirectColorModel(colorSpace, bits, redMask, greenMask, blueMask, alphaMask, isAlphaPremultiplied, transferType);
        return new TiffBitmapData(colorModel, colorModel.createCompatibleSampleModel(w, h));
    }

    static SampleModel createInterleavedSM(int dataType, int numBands, int w, int h)
    {
        int[] bandOffsets = new int[numBands];
        for (int i = 0; i < numBands; i++)
            bandOffsets[i] = i;

        return new PixelInterleavedSampleModel(dataType, w, h, numBands, numBands, bandOffsets);
    }

    static ColorModel createComponentCM(ColorSpace colorSpace, int numBands, int dataType, boolean hasAlpha, boolean isAlphaPremultiplied)
    {
        int transparency = hasAlpha ? Transparency.TRANSLUCENT : Transparency.OPAQUE;

        ColorModel colorModel;
        if (dataType == DataBuffer.TYPE_FLOAT || dataType == DataBuffer.TYPE_DOUBLE)
            colorModel = new ComponentColorModel(colorSpace, hasAlpha, isAlphaPremultiplied, transparency, dataType);

        else
        {
            int[] numBits = new int[numBands];
            int bits = DataBuffer.getDataTypeSize(dataType);

            for (int i = 0; i < numBands; i++)
                numBits[i] = bits;

            colorModel = new ComponentColorModel(colorSpace, numBits, hasAlpha, isAlphaPremultiplied, transparency, dataType);
        }

        return colorModel;
    }

    private static TiffBitmapData createGrayscale(int bits, int dataType, boolean isSigned, boolean hasAlpha, boolean isAlphaPremultiplied, int w, int h)
    {
        if (bits != 1 && bits != 2 && bits != 4 && bits != 8 && bits != 16)
            throw new IllegalArgumentException("Bad value for bits!");

        if (dataType != DataBuffer.TYPE_BYTE && dataType != DataBuffer.TYPE_SHORT && dataType != DataBuffer.TYPE_USHORT)
            throw new IllegalArgumentException("Bad value for dataType!");

        if (bits > 8 && dataType == DataBuffer.TYPE_BYTE)
            throw new IllegalArgumentException("Too many bits for dataType!");

        ColorModel colorModel = null;
        SampleModel sampleModel = null;
        ColorSpace colorSpace = ColorSpace.getInstance(ColorSpace.CS_GRAY);

        if ((bits == 8 && dataType == DataBuffer.TYPE_BYTE) || (bits == 16 && (dataType == DataBuffer.TYPE_SHORT || dataType == DataBuffer.TYPE_USHORT)))
        {
            // Use component color model & sample model
            int numBands = hasAlpha ? 2 : 1;
            int transparency = hasAlpha ? Transparency.TRANSLUCENT : Transparency.OPAQUE;

            int[] nBits = new int[numBands];
            nBits[0] = bits;
            if (numBands == 2)
                nBits[1] = bits;

            colorModel = new ComponentColorModel(colorSpace, nBits, hasAlpha, isAlphaPremultiplied, transparency, dataType);

            int[] bandOffsets = new int[numBands];
            bandOffsets[0] = 0;
            if (numBands == 2)
                bandOffsets[1] = 1;

            sampleModel = new PixelInterleavedSampleModel(dataType, w, h, numBands, w * numBands, bandOffsets);
        }
        else
        {
            int numEntries = 1 << bits;
            byte[] arr = new byte[numEntries];
            for (int i = 0; i < numEntries; i++)
                arr[i] = (byte) (i * 255 / (numEntries - 1));

            colorModel = new IndexColorModel(bits, numEntries, arr, arr, arr);
            sampleModel = new MultiPixelPackedSampleModel(dataType, w, h, bits);
        }

        return new TiffBitmapData(colorModel, sampleModel);
    }

    private TiffBitmapData(java.awt.image.ColorModel cm, SampleModel sampleModel)
    {
        this(cm, sampleModel, null);
    }

    private TiffBitmapData(java.awt.image.ColorModel cm, SampleModel sampleModel, TiffMetadata metadata)
    {
        this.mColorModel = cm;
        this.mSampleModel = sampleModel;
        setDataBuffer(sampleModel.createDataBuffer());
        hasAlpha = mColorModel.hasAlpha();
        mMetadata = metadata;
    }

    private boolean hasAlpha;
    private java.awt.image.ColorModel mColorModel;
    private SampleModel mSampleModel;
    private DataBuffer mDataBuffer;
    private TiffMetadata mMetadata;
}
