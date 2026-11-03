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
package com.aspose.ms.java.ImageIO.tiff.encoder;

import com.aspose.ms.System.Drawing.Rectangle;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.IO.ImageStreamReader;
import com.aspose.ms.java.ImageIO.tiff.decoder.BaselineTIFFTags;
import com.aspose.ms.java.ImageIO.tiff.decoder.TiffMetadata;
import com.aspose.ms.java.ImageIO.tiff.decoder.TiffStreamWriter;
import com.aspose.ms.java.ImageIO.tiff.encoder.compressor.*;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.ColorModel;
import java.awt.image.*;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Arrays;

public class TIFFImageWriter
{
    public void setOutput(Stream output) throws Exception
    {
        setOutput(output, false);
    }

    private void setOutput(Stream output, boolean convertToGrayscale) throws Exception
    {
        mConvertToGrayscale = convertToGrayscale;
        mStream = output;
        if (mStream == null)
            throw new IllegalStateException("output == null!");

        try
        {
            headerPosition = mStream.getPosition();
            try
            {
                // Read byte order and magic number.
                byte[] b = new byte[4];
                mStream.read(b, 0, b.length);

                // Check bytes for TIFF header.
                if ((b[0] == (byte) 0x49 && b[1] == (byte) 0x49 && b[2] == (byte) 0x2a && b[3] == (byte) 0x00) ||
                        (b[0] == (byte) 0x4d && b[1] == (byte) 0x4d && b[2] == (byte) 0x00 && b[3] == (byte) 0x2a))
                {
                    // TIFF header.
                    this.nextSpace = mStream.getLength();
                }
                else
                {
                    // Neither TIFF header nor EOF: overwrite.
                    this.nextSpace = headerPosition;
                }
            }
            catch (Exception io)
            { // thrown by readFully()
                // At EOF or not at a TIFF header.
                this.nextSpace = headerPosition;
            }
            mStream.setPosition(headerPosition);
        }
        catch (Exception ioe)
        { // thrown by getStreamPosition()
            // Assume it's at zero.
            this.nextSpace = headerPosition = 0L;
        }
    }

    public void write(TiffMetadata metadata, BufferedImage image) throws Exception
    {
        write(metadata, image, true);
    }

    public void write(TiffMetadata metadata, BufferedImage image, boolean writeHeader) throws Exception
    {
        if (image == null)
            throw new IllegalArgumentException("image == null!");

        // We take any image but always treat it like ARGB because getPixelAsRGBA() works with ARGB.

        mMetadata = metadata;
        mWriter = new TiffStreamWriter(mMetadata.getByteOrder(), mStream);

        this.mImage = image;

        mConvertTo1Bpp =
                mMetadata.getCompression() == BaselineTIFFTags.COMPRESSION_CCITT_RLE
                        || mMetadata.getCompression() == BaselineTIFFTags.COMPRESSION_CCITT_T_4
                        || mMetadata.getCompression() == BaselineTIFFTags.COMPRESSION_CCITT_T_6;

        if (mConvertTo1Bpp)
            mImage = convertToGrayscaleColors(mImage);

        this.sourceXOffset = 0;
        this.sourceYOffset = 0;
        this.sourceWidth = image.getWidth();
        this.sourceHeight = image.getHeight();

        //  Rectangle imageBounds = new Rectangle(sourceXOffset, sourceYOffset, sourceWidth, sourceHeight);

        // SampleModel sampleModel = image.getSampleModel();

        this.sourceBands = null;
        this.periodX = 1;
        this.periodY = 1;
        this.numBands = 4;//sampleModel.getNumBands();

        this.periodX = 1;
        this.periodY = 1;

        // Compute output dimensions
        int destWidth = (sourceWidth + periodX - 1) / periodX;
        int destHeight = (sourceHeight + periodY - 1) / periodY;
        if (destWidth <= 0 || destHeight <= 0)
            throw new IllegalArgumentException("Empty source region!");

        if (writeHeader)
        {
            // Write the header.
            writeHeader();

            // Seek to the position of the IFD pointer in the header.
            mStream.setPosition(headerPosition + 4);

            // Ensure IFD is written on a word boundary
            nextSpace = (nextSpace + 3) & ~0x3;

            // Write the pointer to the first IFD after the header.
            mWriter.writeInt((int) nextSpace);
        }

        // Set or overwrite mandatory fields in the root IFD
        setupMetadata(image, destWidth, destHeight);

        // Metadata needs to be set on the compressor before the IFD is
        // written as the compressor could modify the metadata.
        compressor.setMetadata(mMetadata);
        compressor.setStream(mWriter);

        // Determine whether bilevel.

        // Check for photometric inversion.
        this.isInverted = (nativePhotometricInterpretation == BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO && photometricInterpretation == BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO)
                || (nativePhotometricInterpretation == BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO && photometricInterpretation == BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO);

        // Analyze image data suitability for direct copy.
        long lastIFDPosition = mMetadata.writeToStream(mWriter);

        this.nextIFDPointerPos = mStream.getPosition();
        mWriter.writeInt(0);

        // Seek to end of IFD data
        mStream.setPosition(lastIFDPosition);
        if (lastIFDPosition > this.nextSpace)
            this.nextSpace = lastIFDPosition;


        boolean writeData = true;

        // If not writing the image data, i.e., if writing or inserting an
        // empty image, return.
        if (!writeData)
            return;

        // Instead of BufferedImage image's bytes will be used in case of RLE, CCITT_3, CCITT_4 compressions
        if (mConvertTo1Bpp)
            mBitonalImageBytes = getGrayscaleBytes(mImage, mConvertToGrayscale, isInverted);

        // Get positions of fields within the IFD to update as we write
        // each strip or tile
        long stripOrTileByteCountsPosition = mMetadata.getStripOrTileByteCountsPosition();
        long stripOrTileOffsetsPosition = mMetadata.getStripOrTileOffsetsPosition();

        // Compute total number of pixels for progress notification
        this.totalPixels = tileWidth * tileLength * tilesDown * tilesAcross;
        this.pixelsDone = 0;

        // Write the image, a strip or tile at a time
        for (int tj = 0; tj < tilesDown; tj++)
        {
            for (int ti = 0; ti < tilesAcross; ti++)
            {
                long pos = mWriter.getPosition();

                // Write the (possibly compressed) tile data

                Rectangle tileRect =
                        new Rectangle(sourceXOffset + ti * tileWidth * periodX,
                                sourceYOffset + tj * tileLength * periodY,
                                tileWidth * periodX,
                                tileLength * periodY);

                try
                {
                    int byteCount = writeTile(tileRect, compressor);

                    if (pos + byteCount > nextSpace)
                        nextSpace = pos + byteCount;

                    pixelsDone += tileRect.getWidth() * tileRect.getHeight();

                    // Fill in the offset and byte count for the file
                    long mark = mWriter.getPosition();
                    mWriter.seek(stripOrTileOffsetsPosition);
                    mWriter.writeInt((int) pos);
                    stripOrTileOffsetsPosition += 4;

                    mWriter.seek(stripOrTileByteCountsPosition);
                    mWriter.writeInt(byteCount);
                    stripOrTileByteCountsPosition += 4;
                    mWriter.setPosition(mark);
                }
                catch (IOException e)
                {
                    throw new IOException("I/O error writing TIFF file!", e);
                }
            }
        }
    }

    public void writeInsert(int imageIndex, BufferedImage image) throws Exception
    {
        if (mStream == null)
            throw new IllegalStateException("Output not set!");

        if (image == null)
            throw new IllegalArgumentException("image == null!");

        // Locate the position of the old IFD (ifd) and the location
        // of the pointer to that position (ifdpos).
        long[] ifdpos = new long[1];
        long[] ifd = new long[1];

        // locateIFD() will throw an IndexOutOfBoundsException if
        // imageIndex is < -1 or is too big thereby satisfying the spec.
        locateIFD(imageIndex, ifdpos, ifd);

        // Seek to the position containing the pointer to the old IFD.
        mWriter.seek(ifdpos[0]);

        // Update next space pointer in anticipation of next write.
        if (ifdpos[0] + 4 > nextSpace)
            nextSpace = ifdpos[0] + 4;

        // Ensure IFD is written on a word boundary
        nextSpace = (nextSpace + 3) & ~0x3;

        // Update the value to point to the next available space.
        mWriter.writeInt((int) nextSpace);

        // Seek to the next available space.
        mWriter.seek(nextSpace);

        // Write the image (IFD and data).
        write(mMetadata, image, false);

        // Seek to the position containing the pointer in the new IFD.
        mWriter.seek(nextIFDPointerPos);

        // Update the new IFD to point to the old IFD.
        mWriter.writeInt((int) ifd[0]);
        // Don't need to update nextSpace here as already done in write().
    }

    // Locate start of IFD for image.
    // Throws IIOException if not at a TIFF header and
    // IndexOutOfBoundsException if imageIndex is < -1 or is too big.
    private void locateIFD(int imageIndex, long[] ifdpos, long[] ifd) throws Exception
    {
        if (imageIndex < -1)
            throw new IndexOutOfBoundsException("imageIndex < -1!");

        long startPos = mWriter.getPosition();

        mWriter.seek(headerPosition);

        // //readUnsignedShort
        byte[] buf = new byte[2];
        mStream.read(buf, 0, 2);
        int byteOrder = (((buf[0] & 0xff) << 8) | ((buf[1] & 0xff) << 0));

        ByteOrder streamByteOrder;
        if (byteOrder == 0x4d4d)
            streamByteOrder = ByteOrder.BIG_ENDIAN;

        else if (byteOrder == 0x4949)
            streamByteOrder = ByteOrder.LITTLE_ENDIAN;

        else
        {
            mWriter.seek(startPos);
            throw new IOException("Illegal byte order");
        }

        ImageStreamReader tiffStreamReader = new ImageStreamReader(streamByteOrder, mStream);
        if (tiffStreamReader.readUnsignedShort() != 42)
        {
            mWriter.seek(startPos);
            throw new IOException("Illegal magic number");
        }

        ifdpos[0] = tiffStreamReader.getStreamPosition();
        ifd[0] = tiffStreamReader.readUnsignedInt();

        if (ifd[0] == 0)
        {
            // imageIndex has to be >= -1 due to check above.
            if (imageIndex > 0)
            {
                tiffStreamReader.seek(startPos);
                throw new IndexOutOfBoundsException("imageIndex is greater than the largest available index!");
            }
            return;
        }
        tiffStreamReader.seek(ifd[0]);

        for (int i = 0; imageIndex == -1 || i < imageIndex; i++)
        {
            int numFields;
            try
            {
                numFields = tiffStreamReader.readShort();
            }
            catch (EOFException eof)
            {
                tiffStreamReader.seek(startPos);
                ifd[0] = 0;
                return;
            }

            tiffStreamReader.skipBytes(12 * numFields);

            ifdpos[0] = tiffStreamReader.getStreamPosition();
            ifd[0] = tiffStreamReader.readUnsignedInt();
            if (ifd[0] == 0)
            {
                if (imageIndex != -1 && i < imageIndex - 1)
                {
                    tiffStreamReader.seek(startPos);
                    throw new IndexOutOfBoundsException("imageIndex is greater than the largest available index!");
                }
                break;
            }
            tiffStreamReader.seek(ifd[0]);
        }
    }

    private void writeHeader() throws Exception
    {
        if (mMetadata != null)
            this.byteOrder = mMetadata.getByteOrder();

        else
            this.byteOrder = ByteOrder.BIG_ENDIAN;

        //stream.setByteOrder(byteOrder);
        if (byteOrder == ByteOrder.BIG_ENDIAN)
            mWriter.writeShort(0x4d4d);
        else
            mWriter.writeShort(0x4949);

        mWriter.writeShort(42); // Magic number
        mWriter.writeInt(0); // Offset of first IFD (0 == none)

        nextSpace = mStream.getPosition();
        headerPosition = nextSpace - 8;
    }

    private void setupMetadata(BufferedImage image, int destWidth, int destHeight) throws IOException
    {
        if (mMetadata.getPlanarConfig() != -1 && mMetadata.getPlanarConfig() != BaselineTIFFTags.PLANAR_CONFIGURATION_CHUNKY)
            mMetadata.setPlanarConfig(BaselineTIFFTags.PLANAR_CONFIGURATION_CHUNKY);

        //TAG_PHOTOMETRIC_INTERPRETATION

        ColorModel cm = image.getColorModel();

        mSampleSize = new int[]{8, 8, 8, 8};
        // When RLE, CCITT_3, CCITT_4 are used
        if (mConvertTo1Bpp)
            mSampleSize = new int[]{1};

        char[] extraSamples = null;
        this.photometricInterpretation = -1;
        boolean forcePhotometricInterpretation = false;

        if (mMetadata.getmPhotometricInterpretation() != -1)
        {
            photometricInterpretation = mMetadata.getmPhotometricInterpretation();
            if (photometricInterpretation == BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_PALETTE_COLOR)
                photometricInterpretation = -1;
            else
                forcePhotometricInterpretation = true;
        }

        int numBands = mSampleSize.length;//sm.getNumBands();
        int numExtraSamples = 0;

        // Check that numBands > 1 here because TIFF requires that
        // SamplesPerPixel = numBands + numExtraSamples and numBands
        // cannot be zero.
        if (numBands > 1 && cm.hasAlpha())
        {
            --numBands;
            numExtraSamples = 1;
            extraSamples = new char[1];
            if (cm.isAlphaPremultiplied()) // TODO: ? android's values are different from Java's
                extraSamples[0] = BaselineTIFFTags.EXTRA_SAMPLES_ASSOCIATED_ALPHA;
            else
                extraSamples[0] = BaselineTIFFTags.EXTRA_SAMPLES_UNASSOCIATED_ALPHA;
        }
        if (numBands == 3)
        {
            this.nativePhotometricInterpretation = BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_RGB;
            if (photometricInterpretation == -1)
                photometricInterpretation = BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_RGB;
        }
        else if (numBands == 1)
        {
            // Java determines whether black is 0 or 1 via IndexColorModel
            // We always use 0 as black and 1 as white
            nativePhotometricInterpretation = BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO;

            // If photometricInterpretation is already set to WhiteIsZero or BlackIsZero, leave it alone
            if (photometricInterpretation != BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO && photometricInterpretation != BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO)
            {
                photometricInterpretation =
                        // r0 == 0 ?
                        BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO;
                //       :BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO;
            }
        }
        // other combinations TODO??:

        // Set EXIF flag. Note that there is no way to determine definitively
        // when an uncompressed thumbnail is being written as the EXIF IFD
        // pointer field is optional for thumbnails.
        boolean isEXIF = false;

        // Emit compression tag
        if (mMetadata.getCompression() == -1)
            mMetadata.setCompression(BaselineTIFFTags.COMPRESSION_NONE);

        predictor = mMetadata.getPredictor();
        if (predictor != -1)
        {
            // We only support Horizontal Predictor for a bitDepth of 8
            if (mSampleSize[0] != 8 ||
                    // Check the value of the tag for validity
                    (predictor != BaselineTIFFTags.PREDICTOR_NONE && predictor != BaselineTIFFTags.PREDICTOR_HORIZONTAL_DIFFERENCING))
            {
                // XXX processWarningOccured ???
                // Set to default
                predictor = BaselineTIFFTags.PREDICTOR_NONE;

                // Emit this changed predictor value to metadata
                mMetadata.setPredictor(predictor);
            }
        }

        int compression = mMetadata.getCompression();
        this.compressor = null;
        if (compression == BaselineTIFFTags.COMPRESSION_CCITT_RLE)
        {
            if (compressor == null)
                compressor = new TIFFRLECompressor();

            if (!forcePhotometricInterpretation)
                photometricInterpretation = (BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO);
        }
        else if (compression == BaselineTIFFTags.COMPRESSION_CCITT_T_4)
        {
            if (compressor == null)
                compressor = new TIFFT4Compressor();

            if (!forcePhotometricInterpretation)
                photometricInterpretation = BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO;
        }
        else if (compression == BaselineTIFFTags.COMPRESSION_CCITT_T_6)
        {
            if (compressor == null)
                compressor = new TIFFT6Compressor();

            if (!forcePhotometricInterpretation)
                photometricInterpretation = (BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO);
        }
        else if (compression == BaselineTIFFTags.COMPRESSION_LZW)
            compressor = new TIFFLZWCompressor(predictor);

        else if (compression == BaselineTIFFTags.COMPRESSION_OLD_JPEG)
        {
            if (isEXIF)
                compressor = new TIFFEXIFJPEGCompressor();
            else
                throw new IOException("Old JPEG compression not supported!");
        }
        else if (compression == BaselineTIFFTags.COMPRESSION_JPEG)
        {
            mIsJPEGCompression = true;

            // Android almost always use ARGB_8888 so most likely numBands will be 4 because image.hasAlpha() returns true
            numExtraSamples = 0;
            if ((numBands == 3 || numBands == 4) && mSampleSize[0] == 8 && mSampleSize[1] == 8 && mSampleSize[2] == 8)
                photometricInterpretation = (BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_Y_CB_CR);

            else if (numBands == 1 && mSampleSize[0] == 8)
                photometricInterpretation = (BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO);

            else
                throw new IOException("JPEG compression supported for 1- and 3-band byte images only!");

            compressor = new TIFFJPEGCompressor();
        }

        else if (compression == BaselineTIFFTags.COMPRESSION_ZLIB)
            compressor = new TIFFZLibCompressor(predictor);

        else if (compression == BaselineTIFFTags.COMPRESSION_PACKBITS)
            compressor = new TIFFPackBitsCompressor();

        else if (compression == BaselineTIFFTags.COMPRESSION_DEFLATE)
            compressor = new TIFFDeflateCompressor(predictor);

        else
        {
            // Determine inverse fill setting.
            int fillOrder = mMetadata.getFillOrder();
            boolean inverseFill = fillOrder == 2;

            if (inverseFill)
                compressor = new TIFFLSBCompressor();

            else
                compressor = new TIFFNullCompressor();

        } // compression == ?

        //if (colorConverter == null) { }

        // Cannot at this time do YCbCr subsampling so set the

        mMetadata.setPhotometricInterpretation(photometricInterpretation);

        this.bitsPerSample = new char[numBands + numExtraSamples];
        this.bitDepth = 0;
        for (int i = 0; i < numBands; i++)
            this.bitDepth = Math.max(bitDepth, mSampleSize[i]);

        if (bitDepth == 3)
            bitDepth = 4;

        else if (bitDepth > 4 && bitDepth < 8)
            bitDepth = 8;

        else if (bitDepth > 8 && bitDepth < 16)
            bitDepth = 16;

        else if (bitDepth > 16)
            bitDepth = 32;

        for (int i = 0; i < bitsPerSample.length; i++)
            bitsPerSample[i] = (char) bitDepth;

        // Emit BitsPerSample. If the image is bilevel, emit if and only
        // if already in the metadata and correct (count and value == 1).
        if (bitsPerSample.length != 1 || bitsPerSample[0] != 1)
            mMetadata.setBitsPerSample(bitsPerSample);

        else
        { // bitsPerSample.length == 1 && bitsPerSample[0] == 1
            int[] bitsPerSampleField = mMetadata.getBitsPerSample();
            if (bitsPerSampleField != null)
            {
                int[] bps = bitsPerSampleField;
                if (bps == null || bps.length != 1 || bps[0] != 1)
                    mMetadata.setBitsPerSample(null);
            }
        }

        // Prepare SampleFormat field.
        int[] f = mMetadata.getSampleFormat();
        if (f == null && (bitDepth == 16 || bitDepth == 32))
        {
            // Set up default content for 16- and 32-bit cases.
            char sampleFormatValue;
            //int dataType = sm.getDataType();
            if (bitDepth == 16 /*&& dataType == DataBuffer.TYPE_USHORT*/)
                sampleFormatValue = (char) BaselineTIFFTags.SAMPLE_FORMAT_UNSIGNED_INTEGER;

            else if (bitDepth == 32 /*&& dataType == DataBuffer.TYPE_FLOAT*/)
                sampleFormatValue = (char) BaselineTIFFTags.SAMPLE_FORMAT_FLOATING_POINT;
            else
                sampleFormatValue = BaselineTIFFTags.SAMPLE_FORMAT_SIGNED_INTEGER;

            this.sampleFormat = (int) sampleFormatValue;
            char[] sampleFormatArray = new char[bitsPerSample.length];
            Arrays.fill(sampleFormatArray, sampleFormatValue);

            // Update the metadata.
            mMetadata.setSampleFormat(sampleFormatArray);
        }
        else if (f != null)
        {
            // Get whatever was provided.
            sampleFormat = f[0];
        }
        else
        {
            // Set default value for internal use only.
            sampleFormat = BaselineTIFFTags.SAMPLE_FORMAT_UNDEFINED;
        }

        if (extraSamples != null)
            mMetadata.setExtraSamples(extraSamples);
        else
            mMetadata.setExtraSamples(null);

        mMetadata.setSamplesPerPixel(bitsPerSample.length);

        //if (mMetadata.getPhotometricInterpretation() == BaselineTIFFTagSet.PHOTOMETRIC_INTERPRETATION_PALETTE_COLOR) {}

        //// Emit ICCProfile if th

        // Always emit XResolution and YResolution.
        if (mMetadata.getXResolution() == -1 && mMetadata.getYResolution() == -1)
        {
            long[][] resRational = new long[1][2];
            resRational[0] = new long[2];

            int ResolutionUnitField = mMetadata.getResolutionUnit();

            // Don't force dimensionless if one of the other dimensional
            // quantities is present.
            if (ResolutionUnitField == -1 && mMetadata.getTagXPosition() == -1 && mMetadata.getTagYPosition() == -1)
            {
                // Set resolution to unit and units to dimensionless.
                resRational[0][0] = 1;
                resRational[0][1] = 1;
                mMetadata.setResolutionUnit(BaselineTIFFTags.RESOLUTION_UNIT_NONE);
            }
            else
            {
                // Set resolution to a value which would make the maximum
                // image dimension equal to 4 inches as arbitrarily stated
                // in the description of ResolutionUnit in the TIFF 6.0
                // specification. If the ResolutionUnit field specifies
                // "none" then set the resolution to unity (1/1).

                int resolutionUnit = ResolutionUnitField != -1 ? ResolutionUnitField : BaselineTIFFTags.RESOLUTION_UNIT_INCH;
                int maxDimension = Math.max(destWidth, destHeight);
                switch (resolutionUnit)
                {
                    case BaselineTIFFTags.RESOLUTION_UNIT_INCH:
                        resRational[0][0] = maxDimension;
                        resRational[0][1] = 4;
                        break;
                    case BaselineTIFFTags.RESOLUTION_UNIT_CENTIMETER:
                        resRational[0][0] = 100L * maxDimension; // divide out 100
                        resRational[0][1] = 4 * 254; // 2.54 cm/inch * 100
                        break;
                    default:
                        resRational[0][0] = 1;
                        resRational[0][1] = 1;
                }
            }
            mMetadata.setXResolution(resRational);
            mMetadata.setYResolution(resRational);

        }
        else if (mMetadata.getXResolution() == -1 && mMetadata.getYResolution() != -1)
        {
            // Set XResolution to YResolution.
            mMetadata.setXResolution(mMetadata.getYResolution());
        }
        else if (mMetadata.getXResolution() != -1 && mMetadata.getYResolution() == -1)
        {
            // Set YResolution to XResolution.
            mMetadata.setYResolution(mMetadata.getXResolution());
        }

        // Set mandatory fields, overriding metadata passed in
        int width = destWidth;
        int height = destHeight;
        mMetadata.setImageWidth(width);
        mMetadata.setImageLength(height);

        // Determine rowsPerStrip

        int rowsPerStrip = mMetadata.getRowsPerStrip();
        if (rowsPerStrip != -1)
        {
            if (rowsPerStrip < 0) // TODO: ?
                rowsPerStrip = height;
        }
        else
        {
            int bitsPerPixel = bitDepth * (numBands + numExtraSamples);
            int bytesPerRow = (bitsPerPixel * width + 7) / 8;
            rowsPerStrip = Math.max(Math.max(DEFAULT_BYTES_PER_STRIP / bytesPerRow, 1), 8);
        }
        rowsPerStrip = Math.min(rowsPerStrip, height);


        // Tiling flag.
        boolean useTiling = false;

        this.tileWidth = width;
        this.tileLength = rowsPerStrip;

        this.tilesAcross = (width + tileWidth - 1) / tileWidth;
        this.tilesDown = (height + tileLength - 1) / tileLength;

        if (compression == BaselineTIFFTags.COMPRESSION_JPEG)
        {
            // Reset tile size per TTN2 spec for JPEG compression.
            int subX;
            int subY;
            if (numBands == 1)
                subX = subY = 1;
            else
                subX = subY = TIFFJPEGCompressor.CHROMA_SUBSAMPLING;

            if (useTiling)
            {
                int MCUMultipleX = 8 * subX;
                int MCUMultipleY = 8 * subY;
                tileWidth = Math.max(MCUMultipleX * ((tileWidth + MCUMultipleX / 2) / MCUMultipleX), MCUMultipleX);
                tileLength = Math.max(MCUMultipleY * ((tileLength + MCUMultipleY / 2) / MCUMultipleY), MCUMultipleY);
            }
            else if (rowsPerStrip < height)
            {
                int MCUMultiple = 8 * Math.max(subX, subY);
                rowsPerStrip = tileLength = Math.max(MCUMultiple * ((tileLength + MCUMultiple / 2) / MCUMultiple), MCUMultiple);
            }
        }
        //else if (isJPEGInterchange) { }

        if (!useTiling)
        {
            this.isTiled = false;
            mMetadata.setTileWidth(-1);
            mMetadata.setTileLength(-1);
            mMetadata.setTileOffsets(null);
            mMetadata.setTileByteCounts(null);

            mMetadata.setRowsPerStrip(rowsPerStrip);
            mMetadata.setStripOffsets(tilesDown);
            mMetadata.setStripByteCounts(tilesDown);
        }
        else
        {
            throw new UnsupportedOperationException();
        }

        // if(isEXIF) {}
    }

    /**
     * @param tileRect The area to be written which might be outside the image.
     */
    private int writeTile(Rectangle tileRect, TIFFCompressor compressor) throws Exception
    {
        Rectangle activeRect;
        Rectangle imageBounds = new Rectangle(0, 0, mImage.getWidth(), mImage.getHeight());//image.getMinX(), image.getMinY(), image.getWidth(), image.getHeight());

        if (!isTiled)
        {
            // Stripped
            activeRect = Rectangle.intersect(tileRect, imageBounds);
            tileRect = activeRect;
        }
        else if (imageBounds.contains(tileRect))
        {
            // Tiled, tile within image bounds
            activeRect = tileRect;
        }
        else
        {
            // Tiled, tile not within image bounds
            activeRect = Rectangle.intersect(imageBounds, tileRect);
        }

        // Shouldn't happen, but return early if empty intersection.
        if (activeRect.isEmpty())
            return 0;

        int minX = tileRect.getX();
        int minY = tileRect.getY();
        int width = tileRect.getWidth();
        int height = tileRect.getHeight();

        if (mConvertTo1Bpp)
        {
            int scanlineStride = (width + 7) / 8;
            byte[] buf = new byte[scanlineStride * height];

            packBytesFromRegion(mBitonalImageBytes, buf, activeRect, scanlineStride);
            return compressor.encode(buf, 0, width, height, mSampleSize, scanlineStride);
        }

        //if(isImageSimple) { }

        // Set offsets and skips based on source subsampling factors
        int xSkip = periodX;
        int ySkip = periodY;

        // Early exit if no data for this pass
        int hpixels = (width + xSkip - 1) / xSkip;
        int vpixels = (height + ySkip - 1) / ySkip;
        if (hpixels == 0 || vpixels == 0)
            return 0;

        // Convert X offset and skip from pixels to samples

        // Initialize sizes
        int samplesPerByte = 8 / bitDepth;
        int bytesPerRow = hpixels * numBands;

        // Create tile buffer
        byte[] currTile = null;

        // Sub-optimal case: shy of "isImageSimple" only by virtue of not being contiguous.
        // Internally we always use ARGB_8888, each pixel is stored on 4 bytes.
        // Assumed no tiling, no subsampling, no subbanding is used
        // So take default fast case.

        {

            // Use Bitmap.compress for JPEG
            if (mIsJPEGCompression)
            {
                //BufferedImage raster = mImage.getSubimage(activeRect.getX(), activeRect.getY(), activeRect.getWidth(), activeRect.getHeight());
                //return compressor.encode(raster, 0, width, height, mSampleSize, width * numBands);
                throw new UnsupportedOperationException("JPEG Compression is not supported");
            }
            else
            {
                // Encode byte array for the rest
                currTile = getPixelAsRGBA(activeRect, numBands);

                // Compressor and return.
                return compressor.encode(currTile, 0, width, height, mSampleSize, width * numBands);
            }
        }
    }

    private byte[] getPixelAsRGBA(Rectangle activeRect, int numBands)
    {
        int length = activeRect.getWidth() * activeRect.getHeight();

        byte[] bytePixels = new byte[length * numBands];

        int idx = 0;
        for (int y = activeRect.getY(); y < activeRect.getBottom(); y++)
        {
            for (int x = activeRect.getX(); x < activeRect.getRight(); x++)
            {
                int pixel = mImage.getRGB(x, y);

                bytePixels[idx++] = (byte) ((pixel >>> 16) & 0xFF); // R
                bytePixels[idx++] = (byte) ((pixel >>> 8) & 0xFF);// G;
                bytePixels[idx++] = (byte) (pixel & 0xFF); // B;
                if (numBands > 3)
                    bytePixels[idx++] = (byte) ((pixel >>> 24) & 0xFF); // A
            }
        }

        return bytePixels;
    }

    /**
     * This method is based on java.awt.BufferedImage.getData(Rectangle)
     */
    private void packBytesFromRegion(byte[] bitonalBytes, byte[] buf, Rectangle rect, int scanlineStride)
    {
        int width = rect.getWidth();
        int height = rect.getHeight();
        int startX = rect.getX();
        int startY = rect.getY();

        byte[] tdata = new byte[width];
        for (int i = startY; i < startY + height; i++)
        {
            getDataElements(startX, i, width, tdata, bitonalBytes);
            putByteData(startX, i, width, 1, tdata, buf, scanlineStride, rect);
        }
    }

    private void getDataElements(int startX, int curY, int width, byte[] outData, byte[] inData)
    {
        int idx = 0;
        int offset = startX + curY * width;
        for (int x = offset; x < offset + width; x++)
            outData[idx++] = inData[x];
    }

    /**
     * This method is based on sun.awt.image.BytePackedRaster.putByteData()
     */
    private void putByteData(int x, int y, int w, int h, byte[] inData, byte[] data, int scanlineStride, Rectangle rect)
    {
        int minX = rect.getX();
        int minY = rect.getY();
        int maxX = mImage.getWidth();//rect.getWidth();
        int maxY = mImage.getHeight();//rect.getHeight();

        if ((x < minX) || (y < minY) || (x + w > maxX) || (y + h > maxY))
            throw new ArrayIndexOutOfBoundsException("Coordinate out of bounds!");

        if (w == 0 || h == 0)
            return;

        int shiftOffset = 7;
        int bitMask = 1;
        int dataBitOffset = 0;
        int pixbits = 1;// pixelBitStride;

        int scanbit = dataBitOffset + (x - minX) * pixbits;
        int index = (y - minY) * scanlineStride;
        int outindex = 0;
        for (int j = 0; j < h; j++)
        {
            int bitnum = scanbit;
            int element;

            // Process initial portion of scanline
            int i = 0;
            while ((i < w) && ((bitnum & 7) != 0))
            {
                int shift = shiftOffset - (bitnum & 7);
                element = data[index + (bitnum >> 3)];
                element &= ~(bitMask << shift);
                element |= (inData[outindex++] & bitMask) << shift;
                data[index + (bitnum >> 3)] = (byte) element;

                bitnum += pixbits;
                i++;
            }

            // Process central portion of scanline 8 pixels at a time
            int inIndex = index + (bitnum >> 3);
            switch (pixbits)
            {
                case 1:
                    for (; i < w - 7; i += 8)
                    {
                        element = (inData[outindex++] & 1) << 7;
                        element |= (inData[outindex++] & 1) << 6;
                        element |= (inData[outindex++] & 1) << 5;
                        element |= (inData[outindex++] & 1) << 4;
                        element |= (inData[outindex++] & 1) << 3;
                        element |= (inData[outindex++] & 1) << 2;
                        element |= (inData[outindex++] & 1) << 1;
                        element |= (inData[outindex++] & 1);

                        data[inIndex++] = (byte) element;

                        bitnum += 8;
                    }
                    break;
            }

            // Process final portion of scanline
            for (; i < w; i++)
            {
                int shift = shiftOffset - (bitnum & 7);

                element = data[index + (bitnum >> 3)];
                element &= ~(bitMask << shift);
                element |= (inData[outindex++] & bitMask) << shift;
                data[index + (bitnum >> 3)] = (byte) element;

                bitnum += pixbits;
            }

            index += scanlineStride;
        }
    }

    /**
     * Returns an array of bytes (0 or 1) which represents the given image.
     */
    private static byte[] getGrayscaleBytes(BufferedImage grayScaleBitmap, boolean convertToGrayscale,
                                            boolean isInverted)
    {
        BufferedImage gray = new BufferedImage(grayScaleBitmap.getWidth(), grayScaleBitmap.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = gray.createGraphics();
        g.drawImage(grayScaleBitmap, 0, 0, null);
        g.dispose();

        byte[] pixels = ((DataBufferByte) gray.getRaster().getDataBuffer()).getData();

        byte[] bytePixels = new byte[pixels.length];
        // convert rgb color to 0 or 1 to create real bitonal image data.
        for (int i = 0; i < pixels.length; i++)
        {
            bytePixels[i] = convertToGrayscale ? (byte) ((pixels[i] & 0x80) >> 7) : (byte) pixels[i]; // make it grayscale
            if (isInverted)
                bytePixels[i] = (byte) (bytePixels[i] ^ 0xff);
        }

        return bytePixels;
    }

    /**
     * Converts the given bitmap into grayscale.
     * The config stays the same ARGB_8888.
     */
    private static BufferedImage convertToGrayscaleColors(BufferedImage image)
    {
        BufferedImageOp grayScaleFilter = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        return grayScaleFilter.filter(image, null);
    }

    private static final int DEFAULT_BYTES_PER_STRIP = 8192;

    int predictor;
    char[] bitsPerSample;
    int bitDepth; // bits per channel
    int tileWidth;
    int tileLength;
    int tilesAcross;
    int tilesDown;
    int sampleFormat = BaselineTIFFTags.SAMPLE_FORMAT_UNDEFINED;
    private Stream mStream;
    private int sourceXOffset;
    private int sourceYOffset;
    private int sourceWidth;
    private int sourceHeight;
    private int[] sourceBands;
    private int periodX;
    private int periodY;
    private int numBands;
    private TiffStreamWriter mWriter;
    private long headerPosition;
    private long nextSpace = 0L;
    private TiffMetadata mMetadata;
    private ByteOrder byteOrder;
    private TIFFCompressor compressor;
    private boolean isTiled;
    private long nextIFDPointerPos; //WORDSJAND-320
    private int photometricInterpretation;
    private int nativePhotometricInterpretation;
    private int totalPixels;
    private int pixelsDone;
    private BufferedImage mImage;
    private int[] mSampleSize;
    private boolean mConvertTo1Bpp;
    private boolean isInverted;
    private byte[] mBitonalImageBytes;
    private boolean mConvertToGrayscale;
    private boolean mIsJPEGCompression;
}
