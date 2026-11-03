// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2013 by Sergey Merkulov

package com.aspose;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;

import com.aspose.ms.System.Drawing.Imaging.PixelFormat;
import com.aspose.ms.System.Drawing.Rectangle;


/// <summary>
/// Base class for error diffusion dithering.
/// Main code and idea are taken from AForge.NET framework.
/// </summary>
/// <remarks><para>The class is the base class for binarization algorithms based on
/// <a href="http://en.wikipedia.org/wiki/Error_diffusion">error diffusion</a>.</para>
/// <para>Each pixel is binarized based not only
/// on its own value, but on values of some surrounding pixels. During pixel's binarization, its <b>binarization
/// error</b> is distributed (diffused) to some neighbor pixels with some coefficients. This error diffusion
/// updates neighbor pixels changing their values, what affects their upcoming binarization. Error diffuses
/// only on unprocessed yet neighbor pixels, which are right and bottom pixels usually (in the case if image
/// processing is done from upper left corner to bottom right corner). <b>Binarization error</b> equals
/// to processing pixel value, if it is below threshold value, or pixel value minus 255 otherwise.</para>
/// <para>The filter accepts 8 bpp grayscale images for processing.</para>
/// </remarks>
//[JavaManual("Manually ported to java")]
abstract class ErrorDiffusionDithering
{
    /// <summary>
    /// Perform color dithering for the specified image.
    /// <see cref="sourceImage"/> must be 8 bpp grayscale image.
    /// The output bitmap is 1 bpp image. 
    /// </summary>
    public BufferedImage apply(BufferedImage sourceImage)
    {
        checkSourceFormat(sourceImage.getType());

        //Java-deleted: java-specific logic
        /*
        // Prepare source data.
        BitmapData sourceData = prepareSourceData(sourceImage);
        byte[] bytes = new byte[sourceData.Height * sourceData.Stride];
        Marshal.Copy(sourceData.Scan0, bytes, 0, bytes.length);
        */
        prepareSourceData(sourceImage);
        byte[] bytes = ((DataBufferByte)sourceImage.getRaster().getDataBuffer()).getData();

        //Java-deleted: java-specific logic
        /*
        // Create destination image.
        BufferedImage destImage = new BufferedImage(sourceImage.Width, sourceImage.Height, PixelFormat.FORMAT_1_BPP_INDEXED);
        destImage.SetResolution(sourceImage.HorizontalResolution, sourceImage.VerticalResolution);
        */
        // Create bilevel destination image.
        BufferedImage destImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

        //Java-deleted: java-specific logic
        /*
        // Lock destination image.
        BitmapData destData = destImage.LockBits(new Rectangle(0, 0, Width, Height),
            ImageLockMode.WriteOnly, destImage.getType());
        // Prepare destination image bytes and BitWriter associated with it.
        byte[] destBytes = new byte[Height*destData.Stride];
        */

        byte[] destBytes = ((DataBufferByte)destImage.getRaster().getDataBuffer()).getData();
        BitWriter writer = new BitWriter(destBytes);
        
        int ptr = 0;
        //Java-deleted: no need for offset in java
        //int offset = Stride - Width;

        //JAVA-added:
        // Destination stride
        int stride = (int) Math.ceil(Width/8.0);

        // Do the job:
        // For each line
        for (Y = 0; Y < Height; Y++)
        {
            //Java-changed: java-specific logic
            //writer.setByteIndex(Y*destData.Stride);
            writer.setByteIndex(Y * stride);
            // For each pixel
            for (X = 0; X < Width; X++, ptr++)
            {
                processPixel(bytes, writer, ptr);
            }
            //Java-deleted: no need for offset in java
            //ptr += offset;
        }

        //Java-deleted: no need in java
        //finishProcess(sourceImage, sourceData, destImage, destData, destBytes);

        return destImage;
    }

    private static void checkSourceFormat(/*PixelFormat*/int pixelFormat)
    {
        //JAVA-changed: java-specific image types
        //if (pixelFormat == PixelFormat.FORMAT_8_BPP_INDEXED)
        if (pixelFormat == BufferedImage.TYPE_BYTE_INDEXED)
            return;

        throw new IllegalStateException("Unsupported pixel format");
    }

    //Java-deleted: changed signature in java
    /*
    private BitmapData prepareSourceData(BufferedImage sourceImage)
    {
        BitmapData sourceData = sourceImage.LockBits(
            new Rectangle(0, 0, sourceImage.Width, sourceImage.Height),
            ImageLockMode.ReadOnly, sourceImage.getType());

        // get image size
        Width = sourceData.Width;
        Height = sourceData.Height;
        Stride = sourceData.Stride;
        PixelSize = BufferedImage.GetPixelFormatSize(sourceImage.getType()) / 8;
        return sourceData;
    }
    */

    private void prepareSourceData(BufferedImage sourceImage)
    {
        // get image size
        Width = sourceImage.getWidth();
        Height = sourceImage.getHeight();
        //Java-changed: java-specific logic
        Stride = Width;
        //Java-deleted: not used in java
        //PixelSize = BufferedImage.GetPixelFormatSize(sourceImage.getType()) / 8;
        //return sourceData;
    }

    private void processPixel(byte[] bytes, BitWriter writer, int ptr)
    {
        // pixel value
        int v = (bytes[ptr] & 0xFF);
        // error value
        int error;
        
        // fill the next destination pixel
        if (v >= (mThreshold & 0xFF))
        {
            bytes[ptr] = (byte) 255;
            error = v - 255;
        }
        else
        {
            bytes[ptr] = (byte) 0;
            error = v;
        }

        // Set correct bit in result 1bpp image.
        if((bytes[ptr] & 0xFF)==255)
            writer.writeOneInCurrentBit();
        writer.moveToNextBit();

        // do error diffusion
        diffuse(error, bytes, ptr);
    }

    //Java-deleted: no need in java
    /*
    private static void finishProcess(BufferedImage sourceImage, BitmapData sourceData, BufferedImage destImage, BitmapData destData, byte[] destBytes)
    {
        sourceImage.UnlockBits(sourceData);
        Marshal.Copy(destBytes, 0, destData.Scan0, destBytes.length);
        destImage.UnlockBits(destData);
    }
    */

    /// <summary>
    /// Do error diffusion.
    /// </summary>
    /// <param name="error">Current error value.</param>
    /// <param name="bytes">Pixels data.</param>
    /// <param name="ptr">Pointer to current processing pixel.</param>
    protected abstract void diffuse(int error, byte[] bytes, int ptr);

    /// <summary>
    /// Threshold value.
    /// </summary>
    /// <remarks>Default value is 128.</remarks>
    public byte getThresholdValue() { return mThreshold; }
    public void setThresholdValue(byte value) { mThreshold = value; }

    /// <summary>
    /// Current processing X coordinate.
    /// </summary>
    protected int X;

    /// <summary>
    /// Current processing Y coordinate.
    /// </summary>
    protected int Y;

    /// <summary>
    /// Processing image's width.
    /// </summary>
    protected int Width;

    /// <summary>
    /// Processing image's height.
    /// </summary>
    protected int Height;

    /// <summary>
    /// Processing image's stride (line size).
    /// </summary>
    protected int Stride;

    //Java-deleted: not used in java
    /// <summary>
    /// Processing image's pixel size in bytes.
    /// </summary>
    //protected int PixelSize;

    private byte mThreshold = (byte) 128;
}

