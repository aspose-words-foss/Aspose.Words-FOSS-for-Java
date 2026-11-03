package com.aspose;

import com.aspose.ms.System.Drawing.Imaging.PixelFormat;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.IO.NetOutputStream;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

public abstract class BaseTiffWriterPal implements Closeable
{
    public void saveFirstFrame(Stream stream, int compression, int binarizationMethod, byte threshold, /*PixelFormat*/int pixelFormat, BitmapPal bitmap,
            boolean isMultiframe) throws Exception
    {
        mPixelFormat = pixelFormat;
        saveFirstFrame(bitmap, new NetOutputStream(stream), compression, binarizationMethod, threshold, isMultiframe);
    }

    public void saveFirstFrame(BitmapPal frame, OutputStream stream, int compression, int binarizationMethod, boolean isMultiframe) throws Exception
    {
        saveFirstFrame(frame, stream, compression, binarizationMethod, gDefaultThreshold, isMultiframe);
    }

    public abstract void saveFirstFrame(BitmapPal frame, OutputStream stream, int compression, int binarizationMethod, byte threshold, boolean isMultiframe) throws Exception;

    public abstract void saveIntermediateFrame(BitmapPal frame) throws Exception;

    public abstract void flushMultiframe() throws Exception;

    public void dispose() throws IOException { close(); }

    @Override
    public void close() throws IOException { }

    protected /*BufferedImage*/BitmapPal convertTo1BppIfRequired(
            /*BufferedImage*/BitmapPal bitmap,
            /*TiffCompressionCore*/int compression,
            /*ImageBinarizationMethodCore*/int binarizationMethod,
                             byte threshold) throws Exception
    {
        switch (compression)
        {
            case TiffCompressionCore.NONE:
            case TiffCompressionCore.LZW:
                if (mPixelFormat == PixelFormat.FORMAT_1_BPP_INDEXED)
                    bitmap.convertToBitonal();
                break;
            case TiffCompressionCore.JPEG:
                //JPEG-in-TIFF encoding supported only for 8-bit samples and either 1 (grayscale) or 3 (RGB or YCbCr) samples per pixel.
                if (bitmap.getPixelFormat() == BufferedImage.TYPE_INT_ARGB
                        || bitmap.getPixelFormat() == BufferedImage.TYPE_4BYTE_ABGR
                        || bitmap.getPixelFormat() == BufferedImage.TYPE_INT_ARGB_PRE
                        || bitmap.getPixelFormat() == BufferedImage.TYPE_4BYTE_ABGR_PRE
                        || (bitmap.getPixelFormat() == BufferedImage.TYPE_CUSTOM && bitmap.getNativeBitmap().getSampleModel().getNumBands() > 3)
                        || bitmap.getNativeBitmap().getSampleModel().getNumBands() == 1)
                    bitmap.convertToIntRgb();

                break;

            case TiffCompressionCore.CCITT_3:
            case TiffCompressionCore.CCITT_4:
                bitmap = getBitmapDependingFromMethod(binarizationMethod, bitmap, threshold);

                // Fix J820, J819, J1740.
                // We need to invert color model
                // (0 bit for White and 1 bit for Black)
                // which is used as default by TIFF viewers not supporting photometric tag.
                // Thus, we provide equal behavior for TIFF viewers (readers)
                // with and without photometric tag support
                bitmap = invertColorModel(bitmap);

                break;
            // In Java we have to convert image into Bitonal one otherwise we get
            //java.lang.Error: Bilevel encodings are supported for bilevel images only.
            case TiffCompressionCore.RLE:
                if (bitmap.getImageType() != BufferedImage.TYPE_BYTE_BINARY)
                {
                    bitmap.convertTo1Bpp();
                    bitmap = invertColorModel(bitmap);
                }
                break;
            default:
                // Other compression types doesn't require binarization.
                break;
        }

        return bitmap;
    }

    //JAVA-added: create bitmap with inverted color model
    // (0 bit for White and 1 bit for Black)
    private static BitmapPal invertColorModel(BitmapPal bitmap)
    {
        // Inverted color model
        // which is used as default by TIFF viewers without photometric tag support
        byte[] colors = new byte[]{(byte) 255, (byte) 0};
        IndexColorModel colorModel = new IndexColorModel(1, 2, colors, colors, colors);

        // Create image with new color model
        BufferedImage newImage = new BufferedImage(colorModel, bitmap.getNativeBitmap().getRaster(), false, null);

        // Invert bits to match new color model
        byte[] bytes = ((DataBufferByte) newImage.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < bytes.length; i++)
        {
            // Invert byte
            bytes[i] = (byte) ~bytes[i];
        }

        return new BitmapPal(newImage);
    }

    private  /*BufferedImage*/BitmapPal getBitmapDependingFromMethod(/*ImageBinarizationMethodCore*/int binarizationMethod, /*BufferedImage*/BitmapPal bitmap, byte threshold)
    {
        switch (binarizationMethod)
        {
            case ImageBinarizationMethodCore.THRESHOLD:
                // Fix Jira6015.
                // BitonalConverter produces not very good results on some images.
                // GDI+ seems to convert bitmap to bitonal itself when needed.
                // Uncomment the lines below in the case of exceptions.
                // Fix Jira6374.
                // Microsoft seems to change Tiff decoder in Windows 7.
                // So if the OS is equal or greater than Windows 7 - let GDI+ convert to b&w if needed.
                // In other cases - use BitonalConverter  for this.

                // JAVA-deleted:
                //if (!PalSystem.isOsEqualOrGreaterThenWindows7() &&
                //   (bitmap.getType() != PixelFormat.FORMAT_1_BPP_INDEXED))
                //{
                //        bitmap = new BitonalConverter().convertToBitonal(bitmap);
                //}

                // In Java we have to convert image into Bitonal one otherwise we get
                //java.lang.Error: Bilevel encodings are supported for bilevel images only.
                if (bitmap.getImageType() != BufferedImage.TYPE_BYTE_BINARY)
                    bitmap.convertTo1Bpp();
                break;
            case ImageBinarizationMethodCore.FLOYD_STEINBERG_DITHERING:
                // Fix Jira6236.
                // New binarization method is introduced.
                //Java-changed:
                //bitmap = getDitheredImage(bitmap);
                BufferedImage bufferedImage = getDitheredImage(bitmap.getNativeBitmap(), threshold);
                bitmap = new BitmapPal(bufferedImage);
                break;
            default:
                throw new IllegalArgumentException("Parameter name: " + "binarizationMethod");
        }

        return bitmap;
    }

    private static BufferedImage getDitheredImage(BufferedImage bitmap, byte threshold)
    {
        // Source image has to be converted to grayscale first,
        // then dithering algorithm should be applied.
        // Grayscale accepts 24 or 32 bpp images only.
        // Convert to such format if needed.
        bitmap = convertTo32BppIfNeeded(bitmap);
        // Get grayscale image.
        bitmap = Grayscale.rMY().apply(bitmap);
        // Binarize image using Floyd-Steinberg dithering algorithm.
        FloydSteinbergColorDithering floydSteinbergColorDithering = new FloydSteinbergColorDithering();
        floydSteinbergColorDithering.setThresholdValue(threshold);
        bitmap = floydSteinbergColorDithering.apply(bitmap);

        return bitmap;
    }

    private static BufferedImage convertTo32BppIfNeeded(BufferedImage bitmap)
    {
        if (bitmap.getType() == BufferedImage.TYPE_INT_ARGB || bitmap.getType() == BufferedImage.TYPE_INT_RGB)
            return bitmap;

        BufferedImage converted = new BufferedImage(bitmap.getWidth(), bitmap.getHeight(), BufferedImage.TYPE_INT_ARGB);
        //JAVA-deleted: resolution is not supported by java
        //converted.SetResolution(bitmap.HorizontalResolution, bitmap.VerticalResolution);

        Graphics2D g = BitmapUtilPal.graphicsFromImage(converted);
        try /*JAVA: was using*/
        {
            //JAVA-changed: java-specific logic
            //g.DrawImageUnscaled(bitmap, 0, 0);
            g.drawImage(bitmap, 0, 0, null);

        }
        //JAVA-changed: java-specific method
        //finally { if (g != null) g.close(); }
        finally
        {
            if (g != null) g.dispose();
        }

        return converted;
    }

    protected  /*TiffCompressionCore*/ int mCompression;
    protected /*ImageBinarizationMethodCore*/ int mBinarizationMethod;
    private /*PixelFormat*/int mPixelFormat;
    protected byte mThreshold;
    private static final byte gDefaultThreshold = (byte) 128;
}
