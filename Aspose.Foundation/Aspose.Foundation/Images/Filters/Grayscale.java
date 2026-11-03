// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2013 by Sergey Merkulov

package com.aspose;

import java.awt.image.*;

import com.aspose.ms.System.Drawing.Rectangle;
import com.aspose.ms.System.Drawing.Imaging.PixelFormat;
import java.awt.Color;


/// <summary>
/// Base class for image grayscaling.
/// Main code and idea are taken from AForge.NET framework.
/// </summary>
/// <remarks>
/// <para>The filter accepts 24, 32 bpp color images and produces
/// 8 bpp grayscale image.</para>
/// </remarks>
//[JavaManual("Manually ported to java")]
class Grayscale
{
    /// <summary>
    /// Initializes a new instance of the <see cref="Grayscale"/> class.
    /// </summary>
    /// <param name="cr">Red coefficient.</param>
    /// <param name="cg">Green coefficient.</param>
    /// <param name="cb">Blue coefficient.</param>
    public Grayscale(double cr, double cg, double cb)
    {
        mRedCoefficient = cr;
        mGreenCoefficient = cg;
        mBlueCoefficient = cb;
    }

    /// <summary>
    /// Grayscale image using R-Y algorithm.
    /// </summary>
    /// 
    /// <remarks><para>The instance uses <b>R-Y</b> algorithm to convert color image
    /// to grayscale. The conversion coefficients are:
    /// <list type="bullet">
    /// <item>Red: 0.5;</item>
    /// <item>Green: 0.419;</item>
    /// <item>Blue: 0.081.</item>
    /// </list></para>
    /// </remarks>
    public static Grayscale rMY()
    {
        return new Grayscale(0.5000, 0.4190, 0.0810);
    }

    public BufferedImage apply(BufferedImage sourceBitmap)
    {
        checkSourceFormat(sourceBitmap.getType());

        //JAVA-changed: java-specific logic
        /*
        // Prepare source data.
        BitmapData sourceData = sourceBitmap.LockBits(
            new Rectangle(0, 0, sourceBitmap.Width, sourceBitmap.Height),
            ImageLockMode.ReadOnly,
            sourceBitmap.getType());

        // Prepare destination bitmap and data.
        BufferedImage destBitmap = new BufferedImage(sourceBitmap.Width, sourceBitmap.Height, PixelFormat.FORMAT_8_BPP_INDEXED);
        destBitmap.SetResolution(sourceBitmap.HorizontalResolution, sourceBitmap.VerticalResolution);
        setGrayscalePalette(destBitmap);
        BitmapData destData = destBitmap.LockBits(
            new Rectangle(0, 0, sourceBitmap.Width, sourceBitmap.Height),
            ImageLockMode.WriteOnly, PixelFormat.FORMAT_8_BPP_INDEXED);

        // Do the job.
        processFilter(sourceData, destData);

        // Unlock bitmaps.
        sourceBitmap.UnlockBits(sourceData);
        destBitmap.UnlockBits(destData);
        */

        // Prepare destination bitmap.
        IndexColorModel palette  = getGrayscalePalette();
        BufferedImage destBitmap = new BufferedImage(sourceBitmap.getWidth(), sourceBitmap.getHeight(), BufferedImage.TYPE_BYTE_INDEXED, palette);

        // Do the job.
        processFilter(sourceBitmap, destBitmap);

        return destBitmap;
    }

    //JAVA-deleted: replaced with other method
    /*
    /// <summary>
    /// Set palette of the 8 bpp indexed image to grayscale.
    /// </summary>
    /// <param name="image">Image to initialize.</param>
    /// <remarks>The method initializes palette of
    /// <see cref="System.Drawing.Imaging.PixelFormat">Format8bppIndexed</see>
    /// image with 256 gradients of gray color.</remarks>
    public static void setGrayscalePalette(BufferedImage image)
    {
        // Check pixel format.
        if (image.getType() != PixelFormat.FORMAT_8_BPP_INDEXED)
            throw new IllegalStateException("Source image is not 8 bpp image.");

        // Get palette.
        ColorPalette cp = image.Palette;
        // Init palette.
        for (int i = 0; i < 256; i++)
        {
            cp.Entries[i] = new Color((i), (i), (i));
        }
        // Set palette back.
        image.Palette = cp;
    }
    */

    //JAVA-added: replacement for setGrayscalePalette() method
    /// <summary>
    /// Get pallete of the 8 bpp indexed image to grayscale.
    /// </summary>
    /// <remarks>The method initializes palette for
    /// <see cref="System.Drawing.Imaging.PixelFormat">Format8bppIndexed</see>
    /// image with 256 gradients of gray color.</remarks>
    private static IndexColorModel getGrayscalePalette()
    {
        int size = 256;
        byte[] r = new byte[size];
        byte[] g = new byte[size];
        byte[] b = new byte[size];

        // Init palette.
        for (int i = 0; i < size; i++)
        {
            r[i] = (byte) i;
            g[i] = (byte) i;
            b[i] = (byte) i;
        }

        // Create palette.
        IndexColorModel palette = new IndexColorModel(8, size, r, g, b);
        return palette;
    }

    private static void checkSourceFormat(/*PixelFormat*/int pixelFormat)
    {
        //JAVA-changed: java-specific image types
        //if (pixelFormat == PixelFormat.FORMAT_24_BPP_RGB || pixelFormat == PixelFormat.FORMAT_32_BPP_RGB ||
        //    pixelFormat == PixelFormat.FORMAT_32_BPP_ARGB)
        //    return;
        if (pixelFormat == BufferedImage.TYPE_INT_ARGB || pixelFormat == BufferedImage.TYPE_INT_RGB)
            return;

        throw new IllegalStateException("Unsupported pixel format");
    }

    //JAVA-deleted: java-specific logic
    /*
    /// <summary>
    /// Process the filter on the specified image.
    /// </summary>
    /// 
    /// <param name="sourceData">Source image data.</param>
    /// <param name="destinationData">Destination image data.</param>
    /// 
    //protected void processFilter(BitmapData sourceData, BitmapData destinationData)
    {
        // Get width and height.
        int width = sourceData.Width;
        int height = sourceData.Height;

        int pixelSize = (sourceData.PixelFormat == PixelFormat.FORMAT_24_BPP_RGB) ? 3 : 4;
        int srcOffset = sourceData.Stride - width * pixelSize;
        int dstOffset = destinationData.Stride - width;

        int rc = (int)(0x10000 * mRedCoefficient);
        int gc = (int)(0x10000 * mGreenCoefficient);
        int bc = (int)(0x10000 * mBlueCoefficient);

        int sourcePointer = 0;
        int destPointer = 0;

        // Get source image bytes.
        byte[] srcData = new byte[sourceData.Height * sourceData.Stride];
        Marshal.Copy(sourceData.Scan0, srcData, 0, srcData.length);

        // Prepare destination buffer.
        byte[] destBuffer = new byte[destinationData.Height * destinationData.Stride];

        // Do the job.
        // For each line
        for (int y = 0; y < height; y++)
        {
            // For each pixel
            for (int x = 0; x < width; x++, sourcePointer += pixelSize, destPointer++)
            {
                destBuffer[destPointer] =
                    (byte)
                    ((rc * (srcData[sourcePointer + DrColor.R_INDEX] & 0xFF) + gc * (srcData[sourcePointer + DrColor.G_INDEX] & 0xFF) +
                      bc * (srcData[sourcePointer + DrColor.B_INDEX] & 0xFF)) >> 16);
            }
            sourcePointer += srcOffset;
            destPointer += dstOffset;
        }

        Marshal.Copy(destBuffer, 0, destinationData.Scan0, destBuffer.length);
    }
    */

    /// <summary>
    /// Process the filter on the specified image.
    /// </summary>
    ///
    /// <param name="sourceData">Source image data.</param>
    /// <param name="destinationData">Destination image data.</param>
    ///
    protected void processFilter(BufferedImage sourceImage, BufferedImage destinationImage)
    {
        // Get width and height.
        //Java-changed: java-specific logic
        /*
        int width = sourceData.Width;
        int height = sourceData.Height;
        */
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();

        //Java-changed: java-specific logic
        /*
        int pixelSize = (sourceData.PixelFormat == PixelFormat.FORMAT_24_BPP_RGB) ? 3 : 4;
        int srcOffset = sourceData.Stride - width * pixelSize;
        int dstOffset = destinationData.Stride - width;
        */
        int pixelSize = 1;
        int srcOffset = width - width * pixelSize;
        int dstOffset = 0;

        int rc = (int)(0x10000 * mRedCoefficient);
        int gc = (int)(0x10000 * mGreenCoefficient);
        int bc = (int)(0x10000 * mBlueCoefficient);

        int sourcePointer = 0;
        int destPointer = 0;

        // Get source image bytes.
        //Java-changed: java-specific logic
        /*
        byte[] srcData = new byte[sourceData.Height * sourceData.Stride];
        Marshal.Copy(sourceData.Scan0, srcData, 0, srcData.length);
        */
        int[] srcData = ((DataBufferInt)sourceImage.getRaster().getDataBuffer()).getData();

        // Prepare destination buffer.
        //Java-changed: java-specific logic
        //byte[] destBuffer = new byte[destinationData.Height * destinationData.Stride];
        byte[] destBuffer = ((DataBufferByte)destinationImage.getRaster().getDataBuffer()).getData();

        // Do the job.
        // For each line
        for (int y = 0; y < height; y++)
        {
            // For each pixel
            for (int x = 0; x < width; x++, sourcePointer += pixelSize, destPointer++)
            {
                //Java-changed: java-specific logic
                /*
                destBuffer[destPointer] =
                        (byte)
                                ((rc * (srcData[sourcePointer + DrColor.R_INDEX] & 0xFF) + gc * (srcData[sourcePointer + DrColor.G_INDEX] & 0xFF) +
                                        bc * (srcData[sourcePointer + DrColor.B_INDEX] & 0xFF)) >> 16);
                */
                int rgb = srcData[sourcePointer];
                Color c = new Color(rgb);
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();

                destBuffer[destPointer] = (byte) ((rc * red + gc * green + bc * blue) >> 16);
            }
            sourcePointer += srcOffset;
            destPointer += dstOffset;
        }

        //Java-deleted: no need to copy buffer for java
        //Marshal.Copy(destBuffer, 0, destinationData.Scan0, destBuffer.length);
    }

    /// <summary>
    /// Portion of red channel's value to use during conversion from RGB to grayscale.
    /// </summary>
    private /*final*/ double mRedCoefficient;

    /// <summary>
    /// Portion of green channel's value to use during conversion from RGB to grayscale.
    /// </summary>
    private /*final*/ double mGreenCoefficient;

    /// <summary>
    /// Portion of blue channel's value to use during conversion from RGB to grayscale.
    /// </summary>
    private /*final*/ double mBlueCoefficient;
}

