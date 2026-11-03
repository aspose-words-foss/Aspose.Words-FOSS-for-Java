// mf

package com.aspose.ms.System.Drawing.Imaging;

import java.awt.image.BufferedImage;

// REFLECTOR
public class PixelFormat
{
    public static final int ALPHA = 0x40000;
    public static final int CANONICAL = 0x200000;
    public static final int DONT_CARE = 0;
    public static final int EXTENDED = 0x100000;
    public static final int FORMAT_16_BPP_ARGB_1555 = 0x61007;
    public static final int FORMAT_16_BPP_GRAY_SCALE = 0x101004;
    public static final int FORMAT_16_BPP_RGB_555 = 0x21005;
    public static final int FORMAT_16_BPP_RGB_565 = 0x21006;
    public static final int FORMAT_1_BPP_INDEXED = 0x30101;
    public static final int FORMAT_24_BPP_RGB = 0x21808;
    public static final int FORMAT_32_BPP_ARGB = 0x26200a;
    public static final int FORMAT_32_BPP_P_ARGB = 0xe200b;
    public static final int FORMAT_32_BPP_RGB = 0x22009;
    public static final int FORMAT_48_BPP_RGB = 0x10300c;
    public static final int FORMAT_4_BPP_INDEXED = 0x30402;
    public static final int FORMAT_64_BPP_ARGB = 0x34400d;
    public static final int FORMAT_64_BPP_P_ARGB = 0x1c400e;
    public static final int FORMAT_8_BPP_INDEXED = 0x30803;
    public static final int GDI = 0x20000;
    public static final int INDEXED = 0x10000;
    public static final int MAX = 15;
    public static final int P_ALPHA = 0x80000;
    public static final int UNDEFINED = 0;

	//JAVA-added
	/**
	 *  Maps System.Drawing.Imaging.PixelFormat to java.awt.image.BufferedImage TYPE
	 */
	public static int getBufferedImageType(/*PixelFormat*/ int pixelFormat)
	{
		switch (pixelFormat)
		{
			// PixelFormat with 16 bit returns bad image in java

			//Specifies that the format is 24 bits per pixel; 8 bits each are used for the red, green, and blue components.
			case PixelFormat.FORMAT_24_BPP_RGB:
				return BufferedImage.TYPE_INT_RGB;

			//Specifies that the format is 32 bits per pixel; 8 bits each are used for the alpha, red, green, and blue components.
			case PixelFormat.FORMAT_32_BPP_ARGB:
				return BufferedImage.TYPE_INT_ARGB;

			//Specifies that the format is 32 bits per pixel; 8 bits each are used for the alpha, red, green, and blue components. The red, green, and blue components are premultiplied, according to the alpha component.
			case PixelFormat.FORMAT_32_BPP_P_ARGB:
				return BufferedImage.TYPE_INT_ARGB_PRE;

			//The pixel data contains color-indexed values, which means the values are an index to colors in the system color table, as opposed to individual color values.
			case PixelFormat.INDEXED:
				return BufferedImage.TYPE_BYTE_INDEXED;
		}

		return BufferedImage.TYPE_INT_ARGB;
	}

    public static int getBitesPerPixel(/*PixelFormat*/ int pixelFormat)
    {
        switch (pixelFormat)
        {
            case ALPHA:
            case FORMAT_1_BPP_INDEXED:
                return 1;

            case FORMAT_16_BPP_ARGB_1555:
            case FORMAT_16_BPP_GRAY_SCALE:
            case FORMAT_16_BPP_RGB_555:
            case FORMAT_16_BPP_RGB_565:
                return 16;

            case FORMAT_24_BPP_RGB:
                return 24;

            case FORMAT_32_BPP_P_ARGB:
            case FORMAT_32_BPP_RGB:
                return 32;

            case FORMAT_48_BPP_RGB:
                return 48;

            case FORMAT_4_BPP_INDEXED:
                return 4;

            case FORMAT_64_BPP_ARGB:
            case FORMAT_64_BPP_P_ARGB:
                return 65;

            case FORMAT_8_BPP_INDEXED:
                return 8;

            case CANONICAL:
            default:
                return 32;
        }
    }
}
