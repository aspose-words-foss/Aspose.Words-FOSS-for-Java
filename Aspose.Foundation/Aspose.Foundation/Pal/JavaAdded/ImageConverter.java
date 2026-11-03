package com.aspose;

import com.aspose.ms.System.Drawing.Rectangle;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

class ImageConverter
{

    /// <summary>
    /// Converts the bitmap to 24bppRgb pixel format.
    /// </summary>
    public static BufferedImage convertTo24BppRgb(BufferedImage bitmap, Rectangle bounds)
    {
        //PixelFormat.FORMAT_24_BPP_RGB => BufferedImage.TYPE_INT_BGR
        if (bitmap.getType() != BufferedImage.TYPE_INT_BGR)
            return ImageConverter.convertTo(bitmap, bounds, BufferedImage.TYPE_INT_BGR);

        return bitmap;
    }

    public static BufferedImage convertTo1Bpp(BufferedImage bitmap, Rectangle bounds)
    {
        return convertTo(bitmap, bounds, BufferedImage.TYPE_BYTE_BINARY);
    }

    public static BufferedImage convertToIntRgb(BufferedImage bitmap, Rectangle bounds)
    {
        return convertTo(bitmap, bounds, BufferedImage.TYPE_INT_RGB);
    }

    public static BufferedImage convertToIntArgb(BufferedImage bitmap, Rectangle bounds)
    {
        return convertTo(bitmap, bounds, BufferedImage.TYPE_INT_ARGB);
    }

    //JAVA-added
    // Converts this bitmap into a different bpp / color bitmap using Java's conversion.
    public static BufferedImage convertTo(BufferedImage bitmap, Rectangle bounds, int bufferedImageType)
    {
        // Remove alpha channel
        if (hasAlphaChannel(bitmap.getType()) && !hasAlphaChannel(bufferedImageType))
            removeAlphaChannel(bitmap);

        BufferedImage newBitmap = new BufferedImage(bitmap.getWidth(), bitmap.getHeight(), bufferedImageType);
        PalBitmapCore.drawImage(bitmap, bounds, newBitmap, bounds);

        return newBitmap;
    }

    /// JAVA-added. Removes alpha channel
    private static void removeAlphaChannel(BufferedImage src)
    {
        int imageType = src.getType();

        if (imageType == BufferedImage.TYPE_4BYTE_ABGR || imageType == BufferedImage.TYPE_4BYTE_ABGR_PRE)
        {
            // [A][B][G][R]
            byte[] data = ((DataBufferByte) src.getRaster().getDataBuffer()).getData();
            int len = data.length;

            for (int i = 0; i < len; i += 4)
                data[i] = (byte) 255;
        }
        else if (imageType == BufferedImage.TYPE_INT_ARGB || imageType == BufferedImage.TYPE_INT_ARGB_PRE)
        {
            // [ARGB]
            int[] data = ((DataBufferInt) src.getRaster().getDataBuffer()).getData();
            int len = data.length;

            for (int i = 0; i < len; i++)
                data[i] = data[i] | 0xFF000000;
        }
    }


    //JAVA-added
    // Returns true if image has alpha channe;
    static boolean hasAlphaChannel(int imageType)
    {
        return imageType == BufferedImage.TYPE_INT_ARGB_PRE
                || imageType == BufferedImage.TYPE_INT_ARGB
                || imageType == BufferedImage.TYPE_4BYTE_ABGR
                || imageType == BufferedImage.TYPE_4BYTE_ABGR_PRE;
    }

}
