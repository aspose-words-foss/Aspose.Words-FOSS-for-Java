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

import com.aspose.ms.java.ImageIO.tiff.decoder.decompressor.BaseTiffDecompressor;
import com.aspose.ms.java.ImageIO.tiff.decoder.decompressor.TIFFNullDecompressor;
import com.aspose.ms.java.ImageIO.tiff.decoder.decompressor.TIFFYCbCrDecompressor;

import java.awt.color.ColorSpace;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds all the metadata read from the TIFF stream.
 * see https://www.loc.gov/preservation/digital/formats/content/tiff_tags.shtml
 */
public class TiffMetadata
{
    public void fillMetadata(int tag, int type, Object value) throws IOException
    {
        switch (tag)
        {
            case BaselineTIFFTags.TAG_IMAGE_WIDTH:
                this.mWidth = BaselineTIFFTags.getAsInt(0, type, value);
                break;

            case BaselineTIFFTags.TAG_IMAGE_LENGTH:
                this.mHeight = BaselineTIFFTags.getAsInt(0, type, value);
                break;

            case BaselineTIFFTags.TAG_FILL_ORDER:
                this.mFillOrder = BaselineTIFFTags.getAsInt(0, type, value);
                break;

            case BaselineTIFFTags.TAG_COMPRESSION:
                this.mCompression = BaselineTIFFTags.getAsInt(0, type, value);
                this.mDecompressor = DecompressorFactory.getDecompressor(this.mCompression, mFillOrder, mPredictor);
                break;

            case BaselineTIFFTags.TAG_STRIP_OFFSETS:
                this.mStripOffsets = BaselineTIFFTags.getAsLongs(value);
                break;

            case BaselineTIFFTags.TAG_TILE_WIDTH:
                this.mTileWidth = BaselineTIFFTags.getAsInt(0, type, value);
                break;

            case BaselineTIFFTags.TAG_TILE_LENGTH:
                this.mTileLength = BaselineTIFFTags.getAsInt(0, type, value);
                break;

            case BaselineTIFFTags.TAG_ROWS_PER_STRIP:
                this.mRowsPerStrip = BaselineTIFFTags.getAsInt(0, type, value);
                break;

            case BaselineTIFFTags.TAG_BITS_PER_SAMPLE:
                this.mBitsPerSamples = BaselineTIFFTags.getAsInts(value);
                break;

            case BaselineTIFFTags.TAG_PHOTOMETRIC_INTERPRETATION:
                this.mPhotometricInterpretation = BaselineTIFFTags.getAsInt(0, type, value);
                break;

            case BaselineTIFFTags.TAG_SAMPLES_PER_PIXEL:
                this.mSamplesPerPixel = BaselineTIFFTags.getAsInt(0, type, value);
                break;

            case BaselineTIFFTags.TAG_X_RESOLUTION:
                if (mXResolution == null)
                    mXResolution = new long[1][2];

                this.mXResolution[0][0] = BaselineTIFFTags.getAsInt(0, type, value);
                break;

            case BaselineTIFFTags.TAG_Y_RESOLUTION:
                if (mYResolution == null)
                    mYResolution = new long[1][2];

                this.mYResolution[0][0] = BaselineTIFFTags.getAsInt(0, type, value);
                break;

            case BaselineTIFFTags.TAG_RESOLUTION_UNIT:
                this.mResolutionUnit = BaselineTIFFTags.getAsInt(0, type, value);
                break;

            case BaselineTIFFTags.TAG_PLANAR_CONFIGURATION: //How the components of each pixel are stored.
                this.mPlanarConfig = BaselineTIFFTags.getAsInt(0, type, value);
                break;

            case BaselineTIFFTags.TAG_PREDICTOR:
                this.mPredictor = BaselineTIFFTags.getAsInt(0, type, value);
                break;

            case BaselineTIFFTags.TAG_TILE_OFFSETS:
                this.mTileOffsets = BaselineTIFFTags.getAsLongs(value);
                break;

            case BaselineTIFFTags.TAG_JPEG_INTERCHANGE_FORMAT:
                this.mJpegInterChangeFormat = BaselineTIFFTags.getAsLongs(value);
                break;

            case BaselineTIFFTags.TAG_TILE_BYTE_COUNTS:
                this.mTileByteCounts = BaselineTIFFTags.getAsLongs(value);
                break;

            case BaselineTIFFTags.TAG_STRIP_BYTE_COUNTS:
                this.mStripByteCounts = BaselineTIFFTags.getAsLongs(value);
                break;

            case BaselineTIFFTags.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH:
                this.mJpegInterChangeFormatLength = BaselineTIFFTags.getAsLongs(value);
                break;

            case BaselineTIFFTags.TAG_T4_OPTIONS:
                this.mT4Options = BaselineTIFFTags.getAsInt(0, type, value);
                break;

            case BaselineTIFFTags.TAG_T6_OPTIONS:
                this.mT6Options = BaselineTIFFTags.getAsInt(0, type, value);
                break;

            case BaselineTIFFTags.TAG_SOFTWARE:
                this.mSoftware = String.valueOf(value);
                break;

            case BaselineTIFFTags.TAG_COLOR_MAP:
                this.mColorMap = BaselineTIFFTags.getAsChars(value);
                break;

            case BaselineTIFFTags.TAG_SAMPLE_FORMAT:
                this.mSampleFormats = BaselineTIFFTags.getAsInts(value);
                break;


            case BaselineTIFFTags.TAG_EXTRA_SAMPLES:
                this.mExtraSamples = BaselineTIFFTags.getAsInts(value);
                break;

            case BaselineTIFFTags.TAG_ICC_PROFILE:
                this.mIccProfile = BaselineTIFFTags.getAsBytes(value);
                break;
        }
    }

    public byte[] getIccProfile() { return mIccProfile; }

    public long getTileOrStripByteCount(int tileIndex)
    {
        if (mTileByteCounts != null)
            return mTileByteCounts[tileIndex];

        if (mStripByteCounts != null)
            return mStripByteCounts[tileIndex];

        if (mJpegInterChangeFormatLength != null)
            return mJpegInterChangeFormatLength[tileIndex];

        throw new IllegalStateException("TIFF directory contains neither StripByteCounts nor TileByteCounts field: we must attempt to calculate from strip or tile width and height.");
    }

    public long getTileOrStripOffset(int tileIndex)
    {
        if (mTileOffsets != null)
            return mTileOffsets[tileIndex];

        if (mStripOffsets != null)
            return mStripOffsets[tileIndex];

        if (mJpegInterChangeFormat != null)
            return mJpegInterChangeFormat[tileIndex];

        return -1;
    }

    /**
     * Returns tile width if image is tiled, else image width
     */
    public int getTileOrStripWidth()
    {
        return (mTileWidth == -1) ? mWidth : mTileWidth;
    }

    /**
     * Returns tile height if image is tiled, else strip height
     */
    public int getTileOrStripHeight()
    {
        if (mTileLength != -1)
            return mTileLength;

        // Default for ROWS_PER_STRIP is 2^32 - 1, i.e., infinity
        return (mRowsPerStrip == -1) ? mHeight : mRowsPerStrip;
    }

    public int getWidth()
    {
        return mWidth;
    }

    public int getHeight()
    {
        return mHeight;
    }

    public int getPredictor()
    {
        return mPredictor;
    }

    public void setPredictor(int predictor)
    {
        mPredictor = predictor;
    }

    public int getTileWidth()
    {
        return mTileWidth;
    }

    public void setTileWidth(int tileWidth)
    {
        mTileWidth = tileWidth;
    }

    public BaseTiffDecompressor getDecompressor(TiffBitmapData image)
    {

        if (mPhotometricInterpretation == BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_Y_CB_CR
                && mCompression != BaselineTIFFTags.COMPRESSION_JPEG && mCompression != BaselineTIFFTags.COMPRESSION_OLD_JPEG)
        {
            boolean convertYCbCrToRGB = image.getColorModel().getColorSpace().getType() == ColorSpace.TYPE_RGB;
            BaseTiffDecompressor wrappedDecompressor = mDecompressor instanceof TIFFNullDecompressor ? null : mDecompressor;
            mDecompressor = new TIFFYCbCrDecompressor(wrappedDecompressor, convertYCbCrToRGB);
        }

        return mDecompressor;
    }

    public int[] getBitsPerSample()
    {
        // BitsPerSample
        int samplesPerPixel = getSamplesPerPixel();
        if (samplesPerPixel == -1)
            return null;

        int[] bitsPerSample = new int[samplesPerPixel];
        int first = 0;
        int defaultBitDepth = 1;

        boolean replicateFirst = false;
        if (mBitsPerSamples == null)
        {
            replicateFirst = true;
            first = defaultBitDepth;
        }
        else if (mBitsPerSamples.length != getSamplesPerPixel())
        {
            replicateFirst = true;
            first = mBitsPerSamples[0];
        }

        for (int i = 0; i < getSamplesPerPixel(); i++)
        {
            // Replicate initial value if not enough values provided
            bitsPerSample[i] = replicateFirst ? first : mBitsPerSamples[i];
        }

        mBitsPerSamples = bitsPerSample;

        return mBitsPerSamples;
    }

    public int getSamplesPerPixel()
    {
        return mSamplesPerPixel;
    }

    public void setSamplesPerPixel(int samplesPerPixel)
    {
        mSamplesPerPixel = samplesPerPixel;
    }

    public void setBitsPerSample(char[] bitsPerSample) { mBitsPerSamples = copyFrom(bitsPerSample); }

    public void setT4Options(long[] oarray)
    {
        if (oarray == null || oarray.length < 1)
            return;
        mT4Options = (int) oarray[0];
    }

    public void setYCbCrSubSampling(char[] chars)
    {
        mYCbCrSubSampling = chars;
    }

    public void setYCbCrPositioning(char[] chars)
    {
        mYCbCrPositioning = chars;
    }

    public void setReferenceBlackWhite(long[][] longs)
    {
        mReferenceBlackWhite = longs;
    }

    private int[] copyFrom(char[] data)
    {
        if (data == null)
            return null;
        int[] result = new int[data.length];
        for (int i = 0; i < result.length; i++)
            result[i] = data[i];

        return result;
    }

    public int getPlanarConfig()
    {
        return mPlanarConfig;
    }

    public void setPlanarConfig(int planarConfig)
    {
        mPlanarConfig = planarConfig;
    }

    public int getCompression()
    {
        return mCompression;
    }

    public void setCompression(int compression)
    {
        mCompression = compression;
    }

    public int getRowsPerStrip()
    {
        return mRowsPerStrip;
    }

    public void setRowsPerStrip(int rowsPerStrip)
    {
        mRowsPerStrip = rowsPerStrip;
    }

    public long[] getStripOffsets()
    {
        return mStripOffsets;
    }

    public void setStripOffsets(int stripOffsets)
    {
        mStripOffsets = new long[stripOffsets];
    }

    public long[] getStripByteCounts()
    {
        return mStripByteCounts;
    }

    public void setStripByteCounts(int stripByteCounts) { mStripByteCounts = new long[stripByteCounts]; }

    public int getTileLength()
    {
        return mTileLength;
    }

    public void setTileLength(int tileLength)
    {
        mTileLength = tileLength;
    }

    public long[] getTileOffsets()
    {
        return mTileOffsets;
    }

    public void setTileOffsets(long[] tileOffsets)
    {
        mTileOffsets = tileOffsets;
    }

    public long[] getJpegInterChangeFormat()
    {
        return mJpegInterChangeFormat;
    }

    public long[] getJpegInterChangeFormatLength()
    {
        return mJpegInterChangeFormatLength;
    }

    public long[] getTileByteCounts()
    {
        return mTileByteCounts;
    }

    public void setTileByteCounts(long[] tileByteCounts)
    {
        mTileByteCounts = tileByteCounts;
    }

    public int getFillOrder()
    {
        return mFillOrder;
    }

    public int getPhotometricInterpretation()
    {
        if (mPhotometricInterpretation == -1)
        {
            if (mCompression == BaselineTIFFTags.COMPRESSION_CCITT_RLE
                    || mCompression == BaselineTIFFTags.COMPRESSION_CCITT_T_4
                    || mCompression == BaselineTIFFTags.COMPRESSION_CCITT_T_6)
                mPhotometricInterpretation = BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO;

            else if (this.getColorMap() != null)
                mPhotometricInterpretation = BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_PALETTE_COLOR;

            else if (getSamplesPerPixel() == 3 || getSamplesPerPixel() == 4)
                mPhotometricInterpretation = BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_RGB;

            else
                mPhotometricInterpretation = BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO;

        }
        return mPhotometricInterpretation;
    }


    public char[] getColorMap()
    {
        return mColorMap;
    }

    public void setPhotometricInterpretation(int photometricInterpretation) { mPhotometricInterpretation = photometricInterpretation; }

    public int getXResolution()
    {
        return mXResolution == null ? -1 : (int) mXResolution[0][0];
    }

    public void setXResolution(float XResolution)
    {
        if (mXResolution == null)
            mXResolution = new long[1][];
        if (mXResolution[0] == null)
            mXResolution[0] = new long[2];

        mXResolution[0][0] = (int) XResolution;
        mXResolution[0][1] = 1;
    }

    public int getYResolution()
    {
        return mYResolution == null ? -1 : (int) mYResolution[0][0];
    }

    public void setYResolution(long[][] resRational)
    {
        mYResolution = resRational;
    }

    public int getResolutionUnit()
    {
        return mResolutionUnit;
    }

    public void setResolutionUnit(int resolutionUnit)
    {
        mResolutionUnit = resolutionUnit;
    }

    public int getT4Options()
    {
        return mT4Options;
    }

    public int getT6Options()
    {
        return mT6Options;
    }

    public long getNextIFDOffset()
    {
        return mNextIFDOffset;
    }

    public void setNextIFDOffset(long nextIFDOffset)
    {
        mNextIFDOffset = nextIFDOffset;
    }

    public int[] getSampleFormat()
    {
        if (getSamplesPerPixel() == -1)
            return null;

        boolean replicateFirst;
        int first = -1;

        replicateFirst = false;
        if (mSampleFormats == null)
        {
            replicateFirst = true;
            first = BaselineTIFFTags.SAMPLE_FORMAT_UNDEFINED;
            mSampleFormats = new int[getSamplesPerPixel()];
        }
        else if (mSampleFormats.length != getSamplesPerPixel())
        {
            replicateFirst = true;
            first = mSampleFormats[0];
        }

        for (int i = 0; i < getSamplesPerPixel(); i++)
        {
            mSampleFormats[i] = replicateFirst ? first : mSampleFormats[i];
            if (mSampleFormats[i] !=
                    BaselineTIFFTags.SAMPLE_FORMAT_UNSIGNED_INTEGER &&
                    mSampleFormats[i] != BaselineTIFFTags.SAMPLE_FORMAT_SIGNED_INTEGER &&
                    mSampleFormats[i] != BaselineTIFFTags.SAMPLE_FORMAT_FLOATING_POINT &&
                    mSampleFormats[i] != BaselineTIFFTags.SAMPLE_FORMAT_UNDEFINED)
            {
                System.out.println("Illegal value for SAMPLE_FORMAT, assuming SAMPLE_FORMAT_UNDEFINED");
                mSampleFormats[i] = BaselineTIFFTags.SAMPLE_FORMAT_UNDEFINED;
            }
        }

        return mSampleFormats;
    }

    public void setSampleFormat(char[] sampleFormat)
    {
        mSampleFormats = copyFrom(sampleFormat);
    }

    public int[] getExtraSamples()
    {
        return mExtraSamples;
    }

    public void setExtraSamples(char[] extraSamples)
    {
        mExtraSamples = copyFrom(extraSamples);
    }

    public ByteOrder getByteOrder()
    {
        return mByteOrder;
    }

    public void setByteOrder(ByteOrder byteOrder)
    {
        mByteOrder = byteOrder;
    }

    public void setXResolution(long[][] resRational)
    {
        mXResolution = resRational;
    }

    public void setYResolution(float YResolution)
    {
        if (mYResolution == null)
            mYResolution = new long[1][];
        if (mYResolution[0] == null)
            mYResolution[0] = new long[2];

        mYResolution[0][0] = (int) YResolution;
        mYResolution[0][1] = 1;
    }

    public int getTagXPosition()
    {
        return mTagXPosition;
    }

    public int getTagYPosition()
    {
        return mTagYPosition;
    }

    public void setImageWidth(int imageWidth)
    {
        mImageWidth = imageWidth;
    }

    public void setImageLength(int imageLength)
    {
        mImageLength = imageLength;
    }

    public long writeToStream(TiffStreamWriter stream) throws Exception
    {
        List<TIFFField> numFields = getNumTIFFFields();
        stream.writeShort(numFields.size());

        long nextSpace = stream.getPosition() + 12 * numFields.size() + 4;

        for (TIFFField f : numFields)
        {
            int type = f.getType();
            int count = f.getCount();
            int size = count * TiffTagType.getSizeOfType(type);
            int tagNumber = f.getTagNumber();

            stream.writeShort(tagNumber);
            stream.writeShort(type);
            stream.writeInt(count);

            // Write a dummy value to fill space
            stream.writeInt(0);
            long markPos = stream.getPosition();//stream.mark(); // Mark beginning of next field
            stream.setPosition(stream.getPosition() - 4);//stream.skipBytes(-4);

            long pos;
            if (size > 4)
            {
                nextSpace = (nextSpace + 3) & ~0x3;

                //  if (tag.isIFDPointer())
                stream.writeInt((int) nextSpace);
                stream.setPosition(nextSpace);
                pos = nextSpace;

                stream.writeTIFFField(f);
                nextSpace = stream.getPosition();
            }
            else
            {
                pos = stream.getPosition();
                stream.writeTIFFField(f);
            }

            // If we are writing the data for the
            // StripByteCounts, TileByteCounts, StripOffsets,
            // TileOffsets, JPEGInterchangeFormat, or
            // JPEGInterchangeFormatLength fields, record the current stream
            // position for backpatching
            if (tagNumber == BaselineTIFFTags.TAG_STRIP_BYTE_COUNTS ||
                    tagNumber == BaselineTIFFTags.TAG_TILE_BYTE_COUNTS ||
                    tagNumber == BaselineTIFFTags.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH)
                this.stripOrTileByteCountsPosition = pos;

            else if (tagNumber == BaselineTIFFTags.TAG_STRIP_OFFSETS ||
                    tagNumber == BaselineTIFFTags.TAG_TILE_OFFSETS ||
                    tagNumber == BaselineTIFFTags.TAG_JPEG_INTERCHANGE_FORMAT)
                this.stripOrTileOffsetsPosition = pos;

            stream.setPosition(markPos); // Go to marked position of next field
        }

        return nextSpace;
    }

    private List<TIFFField> getNumTIFFFields()
    {
        // TODO:
        List<TIFFField> usedFields = new ArrayList<TIFFField>();
        usedFields.add(new TIFFField(BaselineTIFFTags.TAG_IMAGE_WIDTH, TiffTagType.TIFF_SHORT, 1, toChars(mImageWidth)));
        usedFields.add(new TIFFField(BaselineTIFFTags.TAG_IMAGE_LENGTH, TiffTagType.TIFF_SHORT, 1, toChars(mImageLength)));

        if (mBitsPerSamples != null)
            usedFields.add(new TIFFField(258, TiffTagType.TIFF_SHORT, 4, toChars(mBitsPerSamples)));

        usedFields.add(new TIFFField(259, TiffTagType.TIFF_SHORT, 1, toChars(mCompression)));
        usedFields.add(new TIFFField(262, TiffTagType.TIFF_SHORT, 1, toChars(mPhotometricInterpretation)));

        if (mFillOrder != -1)
            usedFields.add(new TIFFField(BaselineTIFFTags.TAG_FILL_ORDER, TiffTagType.TIFF_SHORT, 1, toChars(mFillOrder)));

        usedFields.add(new TIFFField(273, TiffTagType.TIFF_LONG, mStripOffsets.length, mStripOffsets));
        usedFields.add(new TIFFField(277, TiffTagType.TIFF_SHORT, 1, toChars(mSamplesPerPixel)));
        usedFields.add(new TIFFField(278, TiffTagType.TIFF_SHORT, 1, toChars(mRowsPerStrip)));
        usedFields.add(new TIFFField(279, TiffTagType.TIFF_LONG, mStripByteCounts.length, mStripByteCounts));

        if (mXResolution != null)
            usedFields.add(new TIFFField(282, TiffTagType.TIFF_RATIONAL, 1, mXResolution));

        if (mYResolution != null)
            usedFields.add(new TIFFField(283, TiffTagType.TIFF_RATIONAL, 1, mYResolution));

        if (mT4Options != -1)
            usedFields.add(new TIFFField(292, TiffTagType.TIFF_LONG, 1, new long[]{mT4Options}));

        if (mResolutionUnit != -1)
            usedFields.add(new TIFFField(296, TiffTagType.TIFF_SHORT, 1, toChars(mResolutionUnit)));

        if (mExtraSamples != null)
            usedFields.add(new TIFFField(338, TiffTagType.TIFF_SHORT, 1, toChars(mExtraSamples)));

        if (mYCbCrSubSampling != null)
            usedFields.add(new TIFFField(530, TiffTagType.TIFF_SHORT, 2, mYCbCrSubSampling));

        if (mYCbCrPositioning != null)
            usedFields.add(new TIFFField(531, TiffTagType.TIFF_SHORT, 1, mYCbCrPositioning));

        if (mReferenceBlackWhite != null)
            usedFields.add(new TIFFField(532, TiffTagType.TIFF_RATIONAL, 6, mReferenceBlackWhite));

        return usedFields;
    }

    private char[] toChars(int data)
    {
        return new char[]{(char) data};
    }

    private char[] toChars(int[] data)
    {
        if (data == null)
            return null;

        char[] result = new char[data.length];
        for (int i = 0; i < result.length; i++)
            result[i] = (char) data[i];

        return result;
    }

    public long getStripOrTileByteCountsPosition()
    {
        return stripOrTileByteCountsPosition;
    }

    public long getStripOrTileOffsetsPosition()
    {
        return stripOrTileOffsetsPosition;
    }

    public int getmPhotometricInterpretation() {
        return mPhotometricInterpretation;
    }

    private BaseTiffDecompressor mDecompressor;

    private int mCompression = -1;
    private int mHeight = -1;
    private int mWidth = -1;
    private int mRowsPerStrip = -1;
    private int mPlanarConfig = -1;
    private long[] mStripOffsets;
    private long[] mStripByteCounts;
    private int mPredictor = -1;
    private int mSamplesPerPixel = -1;
    private int[] mBitsPerSamples;
    private int mTileWidth = -1;
    private int mTileLength = -1;
    private long[] mTileOffsets;
    private long[] mJpegInterChangeFormat;
    private long[] mJpegInterChangeFormatLength;
    private long[] mTileByteCounts;
    private int mFillOrder = -1;
    private int mPhotometricInterpretation = -1;
    private long[][] mXResolution; // rational
    private long[][] mYResolution; // rational
    private int mResolutionUnit = -1;
    private int mT4Options = -1;
    private int mT6Options = -1;
    private long mNextIFDOffset = -1;
    private String mSoftware;
    private char[] mColorMap;
    private int[] mExtraSamples;
    private int[] mSampleFormats;
    private ByteOrder mByteOrder = ByteOrder.BIG_ENDIAN;
    private byte[] mIccProfile;
    private int mTagXPosition = -1;
    private int mTagYPosition = -1;
    private int mImageWidth = -1;
    private int mImageLength = -1;

    private long stripOrTileByteCountsPosition = -1;
    private long stripOrTileOffsetsPosition = -1;
    private char[] mYCbCrSubSampling;
    private char[] mYCbCrPositioning;
    private long[][] mReferenceBlackWhite;
}
