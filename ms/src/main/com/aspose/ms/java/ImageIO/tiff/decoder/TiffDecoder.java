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

import com.aspose.ms.System.Drawing.Rectangle;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.IO.ImageStreamReader;
import com.aspose.ms.java.ImageIO.tiff.TifImageUtil;
import com.aspose.ms.java.ImageIO.tiff.decoder.converter.TIFFCIELabColorConverter;
import com.aspose.ms.java.ImageIO.tiff.decoder.converter.TIFFColorConverter;
import com.aspose.ms.java.ImageIO.tiff.decoder.converter.TIFFYCbCrColorConverter;
import com.aspose.ms.java.ImageIO.tiff.decoder.decompressor.BaseTiffDecompressor;
import com.aspose.ms.java.ImageIO.tiff.decoder.decompressor.TIFFYCbCrDecompressor;
import com.aspose.ms.java.debug.JavaDebug;

import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.awt.image.ColorModel;
import java.io.EOFException;
import java.nio.ByteOrder;

/**
 * Main class to read and decompress TIFF images.
 * At the moment it can process the first image in TIFF stream only.
 * <p>
 * Use {@link #read(Stream)} method to retrieve TIFF data and {@link #getBitmap()} to create  {@link java.awt.image.BufferedImage} instance.
 * Use {@link #getMetadata()} to get TIFF metadata details.
 */
public class TiffDecoder
{
    public void read(Stream stream) throws Exception
    {
        mStream = stream;
        readHeader();
    }

    private void readHeader() throws Exception
    {
        mStream.read(mBuffer, 0, 2);
        int byteOrder = (((mBuffer[0] & 0xff) << 8) | ((mBuffer[1] & 0xff) << 0)); // ByteOrder.BIG_ENDIAN by default

        if (byteOrder == 0x4d4d)
            mByteOrder = ByteOrder.BIG_ENDIAN;
        else if (byteOrder == 0x4949)
            mByteOrder = ByteOrder.LITTLE_ENDIAN;
        else
            System.err.println("Bad byte order flag. Use LITTLE_ENDIAN by default");

        mReader = new ImageStreamReader(mByteOrder, mStream);

        int magic = mReader.readUnsignedShort();
        if (magic != 42)
            System.err.println("Bad magic number: " + magic);

        firstIFDOffset = mReader.readUnsignedInt();
        mStream.setPosition(firstIFDOffset);

        // System.out.println("DATA=" + mByteOrder + ", mReader=" + mReader + ", firstIFDOffset=" + firstIFDOffset + " mStream.pos=" + mStream.getPosition());

        this.mMetadata = new TiffMetadata();
        mMetadata.setByteOrder(mByteOrder);

        // initialize Metadata
        readMetadata(mMetadata);

        // atm the first one only
        readImage(mMetadata);
    }

    //http://www.fileformat.info/format/tiff/egff.htm
    //http://www.awaresystems.be/imaging/tiff/faq.html#q4
    private void readMetadata(TiffMetadata metadata) throws Exception
    {
        int numEntries = mReader.readUnsignedShort();
        long currentOffset = mStream.getPosition();

        for (int i = 0; i < numEntries; i++)
        {
            mStream.setPosition(currentOffset);

            // Read tag number, value type, and value count.
            int tag = mReader.readUnsignedShort();
            int type = mReader.readUnsignedShort();
            int count = (int) mReader.readUnsignedInt();

            int typeSize = TiffTagType.getSizeOfType(type);
            if (count * typeSize > 4)
            {
                // offset to the actual data
                long valueOffset = mReader.readUnsignedInt();
                mStream.setPosition(valueOffset);
            }

            Object value = BaselineTIFFTags.readTagValue(mReader, type, count);
            metadata.fillMetadata(tag, type, value);

            currentOffset += 12;
        }

        //TODO: Offset to next IFD, if there is a next IFD 0 otherwise
        try
        {
            long nextIFDOffset = mReader.readUnsignedInt();
            if (nextIFDOffset > 0)
                metadata.setNextIFDOffset(nextIFDOffset);

            // System.out.println("IDF ENDS: next IDF pos=" + metadata.getNextIFDOffset());
        }
        catch (EOFException e)
        {
            if (JavaDebug.ON)
                System.err.println(e.getMessage() + " but it seems to be ok. There is no other images.");
        }
    }

    private void readImage(TiffMetadata metadata) throws Exception
    {
        int tileOrStripWidth = metadata.getTileOrStripWidth(); // 150
        int tileOrStripHeight = metadata.getTileOrStripHeight(); // 20
        int width = metadata.getWidth();
        int height = metadata.getHeight();
        int[] bitsPerSample = metadata.getBitsPerSample();
        int samplesPerPixel = metadata.getSamplesPerPixel();
        int planarConfig = metadata.getPlanarConfig();

        Rectangle srcRegion = new Rectangle(0, 0, width, height);
        //  Rectangle destRegion = new Rectangle(0, 0, width, height);

        sourceXOffset = srcRegion.getX();
        sourceYOffset = srcRegion.getY();

        this.mTilesAcross = (width + tileOrStripWidth - 1) / tileOrStripWidth; // 1
        this.mTilesDown = (height + tileOrStripHeight - 1) / tileOrStripHeight; // 4
        this.mImage = TiffBitmapData.createBitmapData(metadata);

        // see https://github.com/colorchief/colorchief/blob/master/app/src/main/java/com/github/colorchief/colorchief/ICCProfile.java
        //if (mMetadata.getIccProfile() != null && mImage.getColorModel() instanceof ComponentColorModel)
        //    System.err.println("ICC_Profile has been read from the TIFF image but it cannot be applied at the moment.");

        mDecompressor = metadata.getDecompressor(mImage);

        TIFFColorConverter colorConverter = null;
        if (metadata.getPhotometricInterpretation() == BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_CIELAB && mImage.getColorModel().getColorSpace().getType() == ColorSpace.TYPE_RGB)
            colorConverter = new TIFFCIELabColorConverter();

        else if (metadata.getPhotometricInterpretation() == BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_Y_CB_CR && !(mDecompressor instanceof TIFFYCbCrDecompressor) &&
                metadata.getCompression() != BaselineTIFFTags.COMPRESSION_JPEG && metadata.getCompression() != BaselineTIFFTags.COMPRESSION_OLD_JPEG)
            colorConverter = new TIFFYCbCrColorConverter(metadata);

        mDecompressor.setBitsPerSample(bitsPerSample);
        mDecompressor.setSourceXOffset(sourceXOffset);
        mDecompressor.setSourceYOffset(sourceYOffset);
        mDecompressor.setSubsampleX(srcXSubsampling);
        mDecompressor.setSubsampleY(srcYSubsampling);

        mDecompressor.setDstXOffset(dstXOffset);
        mDecompressor.setDstYOffset(dstYOffset);

        mDecompressor.setSamplesPerPixel(samplesPerPixel);
        mDecompressor.setColorConverter(colorConverter);
        mDecompressor.setSampleFormat(metadata.getSampleFormat());

        // Compute bounds on the tile indices for this source region.
        int minTileX = TifImageUtil.XToTileX(srcRegion.getX(), 0, tileOrStripWidth);
        int minTileY = TifImageUtil.YToTileY(srcRegion.getY(), 0, tileOrStripHeight);
        int maxTileX = TifImageUtil.XToTileX(srcRegion.getX() + srcRegion.getWidth() - 1, 0, tileOrStripWidth);
        int maxTileY = TifImageUtil.YToTileY(srcRegion.getY() + srcRegion.getHeight() - 1, 0, tileOrStripHeight);

        int numBands = metadata.getSamplesPerPixel();
        int[] sourceBands = new int[numBands];
        for (int i = 0; i < numBands; i++)
            sourceBands[i] = i;

        int destNumBands = numBands;
        int[] destinationBands = new int[destNumBands];
        for (int i = 0; i < destNumBands; i++)
            destinationBands[i] = i;

        mDecompressor.setSourceBands(sourceBands);
        mDecompressor.setDestinationBands(destinationBands);

        if (planarConfig == BaselineTIFFTags.PLANAR_CONFIGURATION_PLANAR)
        {
            mDecompressor.setPlanar(true);
            int[] sb = new int[1];
            int[] db = new int[1];
            for (int tj = minTileY; tj <= maxTileY; tj++)
            {
                for (int ti = minTileX; ti <= maxTileX; ti++)
                {
                    for (int band = 0; band < numBands; band++)
                    {
                        sb[0] = sourceBands[band];
                        mDecompressor.setSourceBands(sb);
                        db[0] = destinationBands[band];
                        mDecompressor.setDestinationBands(db);

                        decodeTile(ti, tj, band, metadata);
                    }
                }
            }
        }
        else
        {
            for (int tj = minTileY; tj <= maxTileY; tj++)
            {
                for (int ti = minTileX; ti <= maxTileX; ti++)
                    decodeTile(ti, tj, -1, metadata);
            }
        }
    }

    private void decodeTile(int ti, int tj, int band, TiffMetadata metadata) throws Exception
    {
        Rectangle tileRect = new Rectangle(ti * metadata.getTileOrStripWidth(), tj * metadata.getTileOrStripHeight(), metadata.getTileOrStripWidth(), metadata.getTileOrStripHeight());
        if (!isImageTiled())
            tileRect = Rectangle.intersect(tileRect, new Rectangle(0, 0, metadata.getWidth(), metadata.getHeight()));

        // Return if the intersection is empty.
        if (tileRect.getWidth() <= 0 || tileRect.getHeight() <= 0)
            return;

        int srcMinX = tileRect.getX();
        int srcMinY = tileRect.getY();
        int srcWidth = tileRect.getWidth();
        int srcHeight = tileRect.getHeight();

        dstMinX = TifImageUtil.iceil(srcMinX - sourceXOffset, srcXSubsampling);
        int dstMaxX = TifImageUtil.ifloor(srcMinX + srcWidth - 1 - sourceXOffset, srcXSubsampling);

        dstMinY = TifImageUtil.iceil(srcMinY - sourceYOffset, srcYSubsampling);
        int dstMaxY = TifImageUtil.ifloor(srcMinY + srcHeight - 1 - sourceYOffset, srcYSubsampling);

        dstWidth = dstMaxX - dstMinX + 1;
        dstHeight = dstMaxY - dstMinY + 1;

        dstMinX += dstXOffset;
        dstMinY += dstYOffset;

        Rectangle dstRect = new Rectangle(dstMinX, dstMinY, dstWidth, dstHeight);
        dstRect = Rectangle.intersect(dstRect, new Rectangle(0, 0, metadata.getWidth(), metadata.getHeight()));

        dstMinX = dstRect.getX();
        dstMinY = dstRect.getY();
        dstWidth = dstRect.getWidth();
        dstHeight = dstRect.getHeight();

        if (dstWidth <= 0 || dstHeight <= 0)
            return;

        int activeSrcMinX = (dstMinX - dstXOffset) * srcXSubsampling + sourceXOffset;
        int sxmax = (dstMinX + dstWidth - 1 - dstXOffset) * srcXSubsampling + sourceXOffset;
        int activeSrcWidth = sxmax - activeSrcMinX + 1;
        int activeSrcMinY = (dstMinY - dstYOffset) * srcYSubsampling + sourceYOffset;
        int symax = (dstMinY + dstHeight - 1 - dstYOffset) * srcYSubsampling + sourceYOffset;
        int activeSrcHeight = symax - activeSrcMinY + 1;

        int tileIndex = tj * mTilesAcross + ti;

        if (metadata.getPlanarConfig() == BaselineTIFFTags.PLANAR_CONFIGURATION_PLANAR)
            tileIndex += band * mTilesAcross * mTilesDown;

        long offset = metadata.getTileOrStripOffset(tileIndex);
        long byteCount = metadata.getTileOrStripByteCount(tileIndex);

        mDecompressor.setSrcMinX(srcMinX);
        mDecompressor.setSrcMinY(srcMinY);
        mDecompressor.setSrcWidth(srcWidth);
        mDecompressor.setSrcHeight(srcHeight);

        mDecompressor.setDstMinX(dstMinX);
        mDecompressor.setDstMinY(dstMinY);
        mDecompressor.setDstWidth(dstWidth);
        mDecompressor.setDstHeight(dstHeight);

        mDecompressor.setActiveSrcMinX(activeSrcMinX);
        mDecompressor.setActiveSrcMinY(activeSrcMinY);
        mDecompressor.setActiveSrcWidth(activeSrcWidth);
        mDecompressor.setActiveSrcHeight(activeSrcHeight);

        mDecompressor.setMetadata(mMetadata);
        mDecompressor.setStream(mStream);
        mDecompressor.setReader(mReader);
        mDecompressor.setOffset(offset);
        mDecompressor.setPredictor(metadata.getPredictor());
        mDecompressor.setByteCount((int) byteCount);
        mDecompressor.setImage(mImage);

        mDecompressor.beginDecoding();

        long pos = mStream.getPosition();
        mDecompressor.decode();
        mStream.setPosition(pos);
    }

    private boolean isImageTiled()
    {
        return mMetadata.getTileWidth() != -1;
    }

    public BufferedImage getBitmap()
    {
        int width = mImage.getWidth();
        int height = mImage.getHeight();

        ColorModel colorModel = mImage.getColorModel();
        SampleModel sampleModel = mImage.getSampleModel();
        DataBuffer data = mImage.getDataBuffer();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Object components = null;

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                components = sampleModel.getDataElements(x, y, components, data);
                int color = colorModel.getRGB(components);
                result.setRGB(x, y, color);
            }
        }

        return result;
    }

    public TiffMetadata getMetadata() { return mMetadata; }

    private int sourceXOffset;
    private int sourceYOffset;
    private int srcXSubsampling = 1;
    private int srcYSubsampling = 1;
    private int dstXOffset;
    private int dstYOffset;
    private int dstWidth;
    private int dstHeight;
    private int dstMinX;
    private int dstMinY;
    private Stream mStream;
    private byte[] mBuffer = new byte[12];
    private ByteOrder mByteOrder;
    private ImageStreamReader mReader;
    private long firstIFDOffset = -1;
    private int mTilesAcross = -1;
    private int mTilesDown = -1;
    private TiffMetadata mMetadata;
    private TiffBitmapData mImage;
    private BaseTiffDecompressor mDecompressor;
}
