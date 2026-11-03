// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//15/12/11 by Vyacheslav Durin
package com.aspose;


import com.aspose.ms.System.Drawing.Imaging.PixelFormat;
import com.aspose.ms.System.Drawing.Rectangle;
import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.logging.Log;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;

// This class contains helper methods to be used in BitmapPal to convert raster, save metadata etc
class PalBitmapCore
{

    PalBitmapCore()
    {
    }

    static DrPixels parseGrayScale(BufferedImage bitmap)
    {
        int bitsPerPixel = bitmap.getColorModel().getPixelSize();

        Raster raster = bitmap.getData();
        DataBuffer buf = raster.getDataBuffer();

        boolean convertToBytes = (buf.getDataType() == DataBuffer.TYPE_USHORT);
        byte[] colorValues = convertToBytes ? new byte[buf.getSize() * 2] : new byte[buf.getSize()];

        for (int i = 0, j = 0; i < buf.getSize(); i++)
        {
            int v = buf.getElem(i);
            if (convertToBytes)
            {
                // WORDSJAVA-1483 BufferedImage.TYPE_USHORT_GRAY images have DataBufferUShort
                // We have to convert 16-bit unsigned short into 8-bit signed byte.
                colorValues[j++] = (byte) ((v >> 8) & 0xff);
                colorValues[j++] = (byte) (v & 0xff);
            }
            else
                // BufferedImage.TYPE_BYTE_GRAY images have DataBufferByte works as usual
                colorValues[j++] = (byte) v;
        }

        return new DrPixels(colorValues, new byte[0], false, ColorModel.GRAY_SCALE, bitsPerPixel, null);
    }

	//JAVA-added
    /**
     * Draws this bitmap onto another bitmap. Allows to positively crop and to scale the image.
     */
    static void drawImage(BufferedImage srcBitmap, Rectangle srcRect, BufferedImage dstBitmap, Rectangle dstRect)
    {
        drawImage(srcBitmap, srcRect, dstBitmap, dstRect, null);
    }

    /**
     * Draws this bitmap onto another bitmap. Allows to positively crop and to scale the image.
     */
    static void drawImage(BufferedImage srcBitmap, Rectangle srcRect, BufferedImage dstBitmap, Rectangle dstRect, DrColor backgroundColor)
    {
        Graphics2D g = dstBitmap.createGraphics();
        try
        {
            Color bgcolor = null;
            if ((backgroundColor != null) && (!DrColor.equals(backgroundColor, DrColor.EMPTY)))
                bgcolor = backgroundColor.toNativeColor();

            // FIX 25705 - Image processing takes too long.
            // RK Setting any other interpolation or renderung value can increase the time taken extremely!
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
	        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            g.drawImage(
                    srcBitmap,
                    dstRect.getLeft(), dstRect.getTop(), dstRect.getRight(), dstRect.getBottom(),
                    srcRect.getLeft(), srcRect.getTop(), srcRect.getRight(), srcRect.getBottom(),
                    bgcolor,null);
        }
        catch (Exception e)
        {
            Log.warn(e);
        }
        finally
        {
            g.dispose();
        }
    }


    /**
     * Converts this bitmap into a different bpp / color bitmap using Java's conversion.
     */
    static BufferedImage convertTo(BufferedImage bitmap, int imageType)
    {
        BufferedImage newBitmap = new BufferedImage(bitmap.getWidth(), bitmap.getHeight(), imageType);
        Graphics2D g = newBitmap.createGraphics();
        g.drawImage(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), null);
        g.dispose();
        return newBitmap;
    }


    /**
     * http://today.java.net/pub/a/today/2007/04/03/perils-of-image-getscaledinstance.html
     *
     * Convenience method that returns a scaled instance of the provided {@code BufferedImage}.
     *
     * If downscaling ratio is more than twice of original size a multi-step scaling technique
     * will be used because usual one-step technique provides too bad results in this case.
     *
     * @param image the original image to be scaled
     * @param targetWidth the desired width of the scaled instance,
     *    in pixels
     * @param targetHeight the desired height of the scaled instance,
     *    in pixels
     * @return a scaled version of the original {@code BufferedImage}
     */
    static BufferedImage getScaledInstance(BufferedImage image, int targetWidth, int targetHeight, boolean useMultiStepUpscaling)
    {
        int type = BufferedImage.TYPE_INT_ARGB;

        BufferedImage ret = image;

        // WORDSJAVA-588 negative width (broken metadata?) causes (warning) exception and missed image as a result.
        targetWidth = Math.abs(targetWidth);
        targetHeight = Math.abs(targetHeight);
        // Do not "scale" image to zero as .Net Graphics.DrawImage() does.
        if (targetWidth == 0 || targetHeight == 0)
            return ret;

        int srcWidth = image.getWidth();
        int srcHeight = image.getHeight();
        float scaleX = (float)targetWidth / (float)srcWidth;
        float scaleY = (float)targetHeight / (float)srcHeight;

        // In certain cases, when AffineTransform within Graphics2D is configured incorrectly with excessively
        // large scaleX and scaleY values, an OOM occurs. Small hack to prevent this.
        if (srcWidth == targetWidth && srcHeight == targetHeight || scaleX > 200 && scaleY > 200)
        {
            if (image.getType() != BufferedImage.TYPE_INT_ARGB)
                image = convertTo(image, BufferedImage.TYPE_INT_ARGB);
            return image;
        }

        // Use ordinal one-step technique for upScaling.
        if (scaleX > 1 && scaleY > 1)
        {
            // FIX WORDSJAVA-1107: Print quality via Aspose.Words library.
            // Multi-step scaling gives more clear-cut barcode, but it breaks TestRendering.testJiraAwjava548(),
            // so we use this technique only when printing for now.
            if (useMultiStepUpscaling)
                return getMultiStepScaledInstance(ret, type, targetWidth, targetHeight);
            else
                return getOneStepScaledInstance(ret, type, targetWidth, targetHeight);
        }

        // Use alternative technique for downscaling. It works even slightly better than multi-step downscaling.
        // (FIX WORDSJAVA-507: downscaling less to 2 using usual algorithm produces bad pictures.)
        return ImageScaling.getArgbScaledInstance(ret, targetWidth, targetHeight);
	}

	/**
	 * Implementation of one-step scaling with optimal ratio between quality and speed.
	 */
	static private BufferedImage getOneStepScaledInstance(BufferedImage image, int imageType, int targetWidth, int targetHeight)
    {
		BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, imageType);
		Graphics2D g = scaledImage.createGraphics();

        // RenderingHints.VALUE_INTERPOLATION_BICUBIC can produce even better quality but this
        // hint doesn't work in java 1.4 and twice as slower than VALUE_INTERPOLATION_BILINEAR.
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        // Antialiasing improves some images too.
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.drawImage(image, 0, 0, targetWidth, targetHeight, null);
		g.dispose();

		return scaledImage;
    }

    static private BufferedImage getMultiStepScaledInstance(BufferedImage image, int imageType, int targetWidth, int targetHeight)
    {
        if (image.getWidth() == targetWidth && image.getHeight() == targetHeight)
            return image;

        int w = image.getWidth() * 2;
        int h = image.getHeight() * 2;

        while (w < targetWidth && h < targetHeight)
        {
            BufferedImage scaledImage = new BufferedImage(w, h, imageType);
            Graphics2D g = scaledImage.createGraphics();
            g.drawImage(image, 0, 0, w, h, null);
            g.dispose();

            w *= 2;
            h *= 2;
            image = scaledImage;
        }

        BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, imageType);
        Graphics2D g = scaledImage.createGraphics();

        // Use antialiasing only on the final step.
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        g.dispose();

        return scaledImage;
    }

    // From what I can understand it is hard to convert CMYK to RGB exactly, one needs a device profile for it.
    // I tried using Java's ColorSpace methods to perform the conversion but it results in plain incorrect picture.
    // So I'm just using a simple formula here that seems to work better.
    static void cmykToRgb(int[] pixelData)
    {
        int cyan = pixelData[0];
        int magenta = pixelData[1];
        int yellow = pixelData[2];
        int black = pixelData[3];

        // http://bytes.com/topic/java/answers/16180-rgb-cmyk
        float colors = 255.0f - black;
        int red = (int) (colors * (255.0f - cyan) / 255.0f);
        int green = (int) (colors * (255.0f - magenta) / 255.0f);
        int blue = (int) (colors * (255.0f - yellow) / 255.0f);

        pixelData[0] = red;
        pixelData[1] = green;
        pixelData[2] = blue;
        pixelData[3] = 0xff;    // alpha
    }

    // I can get the ColorModel class to calculate this, bit my calculations here seem to be
    // giving result close to .NET's calculations and also seem to be more efficient.
    // The ColorModel has many methods that are hard to understand reading their source they
    // seem to work with arrays etc which is not really needed here.
    static int scaleTo8BitColor(int value, int componentSize)
    {
        switch (componentSize)
        {
            case 5:
                // This is a 5 bit color in the range of 0..31. We need to scale it to be within 0..255.
                // This seems to be the formula that gives exactly same pixel values as in .NET.
                return (int) Math.min(value * 256.0 / 31.0, 255.0);
            case 6:
                return (int) Math.min(value * 256.0 / 63.0, 255.0);
            case 8:
                return value;
            case 16:
                // In this case there seems to be two 8bit values in two bytes. They seem to be either the same
                // or different by one. If we take the upper byte it seems to produce the same color as in .NET.
                return ((value >> 8) & 0xff);
            default:
                throw new UnsupportedOperationException("Don't know how to handle this color component size.");
        }
    }

    static void setPixels(BufferedImage result, byte[] bytes, int pixelFormat)
    {
        int channels = result.getColorModel().getNumComponents();
        if (channels != 3 && channels != 4)
            throw new UnsupportedOperationException("setPixels doesn't support BufferedImage type=" + result.getType()
                    + " raster=" + result.getRaster() + " pixelFormat=" + pixelFormat);

        int dataType = result.getRaster().getDataBuffer().getDataType();
        if (dataType != DataBuffer.TYPE_INT)
            throw new UnsupportedOperationException("setPixels doesn't support dataType=" + dataType);

        int[] data = ((DataBufferInt) result.getRaster().getDataBuffer()).getData();
        int width = result.getWidth();
        int height = result.getHeight();

        int strideWithoutPadding = channels * width; // real row's length
        int bitsPerPixel = PixelFormat.getBitesPerPixel(pixelFormat);

        // The magnitude of the stride for a Windows Forms bitmap is "4 * ((bitsPerPixel * width + 31) / 32)"
        // i.e. real row's length + padding. .NET BitmapData seems to calculate it this way
        int strideWithPadding = 4 * ((bitsPerPixel * width + 31) / 32);

        int i = 0;
        int[] rgba = new int[] {0, 0, 0, 255}; // rgba
        for (int y = 0; y < height; y++)
        {
            int rowStart = y * strideWithPadding;
            for (int x = 0; x < strideWithPadding; x += channels)
            {
                if ((x + channels) > strideWithoutPadding) // take into account real data only
                    break;

                int pixelPositionInRow = rowStart + x; // decode each pixel in the row
                for (int c = 0; c < channels; c++)
                    rgba[c] = bytes[pixelPositionInRow + c] & 0xFF;

                data[i++] =((rgba[3] & 0xFF) << 24) | ((rgba[2] & 0xFF) << 16) | ((rgba[1] & 0xFF) << 8)  | ((rgba[0] & 0xFF));
            }
        }
    }
}
