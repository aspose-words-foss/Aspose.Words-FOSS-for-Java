package com.aspose;

import java.awt.color.*;
import java.awt.image.*;
import java.awt.image.ColorModel;

/**
 * This class helps to apply CMYK and YCCK color models to an image
 */
public class ImageReaderHelper
{
    private final static int MAX_VALUE_BYTE = 255;
    private final static int BYTE_SIZE = MAX_VALUE_BYTE + 1;
    private final static int MAX_VALUE_7BIT = 128;
    private final static int[] CR_R_TAB = new int[BYTE_SIZE];
    private final static int[] CB_B_TAB = new int[BYTE_SIZE];
    private final static int[] CR_G_TAB = new int[BYTE_SIZE];
    private final static int[] CB_G_TAB = new int[BYTE_SIZE];

    /*
     * Initialize tables for YCC->RGB colorspace conversion.
     */
    private static synchronized void initYCCtoRGBtable()
    {
        for (int i = 0, x = -MAX_VALUE_7BIT; i <= MAX_VALUE_BYTE; i++, x++)
        {
            /* Cr=>R value as 1.40200 * x */
            CR_R_TAB[i] = (int) (1.40200 * x);
            /* Cb=>B value as 1.77200 * x */
            CB_B_TAB[i] = (int) (1.77200 * x);
            /* Cr=>G value as -0.71414 * x */
            CR_G_TAB[i] = -(int) (0.71414 * x);
            /* Cb=>G value as -0.34414 * x */
            CB_G_TAB[i] = -(int) (0.34414 * x);
        }
    }

    /**
     * Create an image from raster by using ICC_Profile or conversion algorithm.
     *
     * @return new buffered image.
     */
    public static BufferedImage createImage(Raster cmykRaster, ICC_Profile cmykProfile, ICC_Profile defaultProfile,
                                            boolean inverted, boolean fromYCCK)
    {
        BufferedImage image;
        int w = cmykRaster.getWidth();
        int h = cmykRaster.getHeight();

        // if we managed to read ICC_Profile from the origin we use it to convert to RGB colorspace.
        if (cmykProfile != null)
        {
            java.awt.color.ColorSpace cmykCS = new ICC_ColorSpace(cmykProfile);
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            WritableRaster rgbRaster = image.getRaster();
            java.awt.color.ColorSpace rgbCS = image.getColorModel().getColorSpace();
            ColorConvertOp cmykToRgb = new ColorConvertOp(cmykCS, rgbCS, null);
            try
            {
                cmykToRgb.filter(getConvertedRaster(cmykRaster, w, h, inverted, fromYCCK, true), rgbRaster);
            }
            catch (Exception e)
            {
                try
                {
                    if (defaultProfile != null)
                    {
                        // if something went wrong we try the default ICC_Profile.
                        ICC_Profile profile = defaultProfile;
                        cmykCS = new ICC_ColorSpace(profile);
                        cmykToRgb = new ColorConvertOp(cmykCS, rgbCS, null);
                        cmykToRgb.filter(getConvertedRaster(cmykRaster, w, h, inverted, fromYCCK, true), rgbRaster);
                    }
                    else
                    {
                        // Try to apply profile only once and return null if DefaultProfile is undefined
                        return null;
                    }
                }
                catch (Exception e2)
                {
                    // last resort.
                    image = getImageFromRGB(getConvertedRaster(cmykRaster, w, h, inverted, fromYCCK, false));
                }
            }
        }
        else
        {
            image = getImageFromRGB(getConvertedRaster(cmykRaster, w, h, inverted, fromYCCK, false));
        }

        return image;
    }

    /**
     * Convert CMYK|YCCK colorspace into not inverted CMYK|sRGB colorspace.
     *
     * @return raster.
     */
    private static Raster getConvertedRaster(Raster cmykRaster, int w, int h, boolean inverted, boolean fromYCCK, boolean toCMYK)
    {
        if (toCMYK && !(inverted || fromYCCK)) return cmykRaster;

        int[] pixels = new int[w * h];
        int[] C = cmykRaster.getSamples(0, 0, w, h, 0, (int[]) null);
        int[] M = cmykRaster.getSamples(0, 0, w, h, 1, (int[]) null);
        int[] Y = cmykRaster.getSamples(0, 0, w, h, 2, (int[]) null);
        int[] K = cmykRaster.getSamples(0, 0, w, h, 3, (int[]) null);

        int y, cb, cr;
        int cmykC, cmykM, cmykY;

        for (int i = 0; i < C.length; i++)
        {
            if (inverted)
            {
                C[i] = 255 - C[i];
                M[i] = 255 - M[i];
                Y[i] = 255 - Y[i];
                K[i] = 255 - K[i];
            }

            if (fromYCCK)
            {
                y = C[i];
                cb = M[i];
                cr = Y[i];

                cmykC = MAX_VALUE_BYTE - (y + CR_R_TAB[cr]); // red
                cmykM = MAX_VALUE_BYTE - (y + CR_G_TAB[cr] + CB_G_TAB[cb]); // green
                cmykY = MAX_VALUE_BYTE - (y + CB_B_TAB[cb]); // blue

                C[i] = clamp(cmykC);
                M[i] = clamp(cmykM);
                Y[i] = clamp(cmykY);
            }

            if (toCMYK)
                pixels[i] = C[i] << 24 | M[i] << 16 | Y[i] << 8 | K[i];
            else
                pixels[i] = (255 - Math.min(255, C[i] + K[i])) << 16 |
                        (255 - Math.min(255, M[i] + K[i])) << 8 |
                        (255 - Math.min(255, Y[i] + K[i]));
        }

        if (toCMYK)
            return Raster.createPackedRaster(new DataBufferInt(pixels, pixels.length), w, h, w,
                    new int[]{0xff000000, 0xff0000, 0xff00, 0xff}, null);

        return Raster.createPackedRaster(new DataBufferInt(pixels, pixels.length), w, h, w,
                new int[]{0xff0000, 0xff00, 0xff}, null);
    }

    /**
     * Create an image from raster. Raster must have not inverted sRGB colorspace.
     *
     * @return a new sRGB image.
     */
    private static BufferedImage getImageFromRGB(Raster raster)
    {
        java.awt.color.ColorSpace cs = java.awt.color.ColorSpace.getInstance(java.awt.color.ColorSpace.CS_sRGB);
        ColorModel cm = new DirectColorModel(cs, 24, 0xff0000, 0xff00, 0xff,
                0x0, false, DataBuffer.TYPE_INT);
        return new BufferedImage(cm, (WritableRaster) raster, true, null);
    }

    /**
     * Check if the given number is in between 0 and 255.
     *
     * @return the same number if <code>val</code> is in between 0 and 255, otherwise 0 or 255 will be returned.
     */
    private static int clamp(int val)
    {
        return val < 0 ? 0 : (val > 255) ? 255 : val;
    }

    static
    {
        initYCCtoRGBtable();
    }
}
