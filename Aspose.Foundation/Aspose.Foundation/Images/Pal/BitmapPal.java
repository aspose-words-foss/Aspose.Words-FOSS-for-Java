// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 02/08/2010 by Roman Korchagin

package com.aspose;

import com.aspose.ms.System.Drawing.Imaging.PixelFormat;
import com.aspose.ms.System.Drawing.Rectangle;
import com.aspose.ms.System.Drawing.RectangleF;
import com.aspose.ms.System.Drawing.msSize;
import com.aspose.ms.System.IO.*;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.logging.Log;
import com.aspose.words.NativeLibSettings;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.*;

import static com.aspose.ImageReaderBuilder.Options;

/// <summary>
/// Port this class manually to Java.
///
/// Useful links about working with Images in Java:
///     http://java.sun.com/j2se/1.4/pdf/imageio_guide.pdf
///     http://java.sun.com/developer/technicalArticles/GUI/java2d/java2dpart2.html
///
/// </summary>
public class BitmapPal extends ImagePal
{
    //JAVA-added: used by loadNativeImage()
    private BitmapPal()
    {}

    /// <summary>
    /// Creates a bitmap of the specified width and height in pixels.
    /// The resolution is 96dpi.
    /// The color model is 32bpp ARGB.
    /// </summary>
    public BitmapPal(int width, int height)
    {
        this(width, height, ImageConstants.STANDARD_RESOLUTION, ImageConstants.STANDARD_RESOLUTION);
    }

    /// <summary>
    /// Creates a bitmap of the specified width and height in pixels and resolution in the 32bpp ARGB 
    /// pixel format and color model.
    /// </summary>
    public BitmapPal(int width, int height, float hRes, float vRes)
    {
        this(width, height, hRes, vRes, BufferedImage.TYPE_INT_ARGB, true);
    }

    public BitmapPal(int width, int height, float hRes, float vRes, /*PixelFormat*/int pixelFormat)
    {
        this(width, height, hRes, vRes, PixelFormat.getBufferedImageType(pixelFormat), true);
    }

    /**
     * Internal ctor with BufferedImage.TYPE_* imageType argument (Contrary to PixelFormat argument in the above public
     * ctor). Both arguments are int in java so we get signature conflict here. To resolve the conflict i added non used
     * 'boolean internal' argument to internal ctor.
     */
    BitmapPal(int width, int height, float hRes, float vRes, int imageType, boolean internal)
    {
        // In general, we want to honor the image color model, but most likely the caller wants to create a
        // Graphics object on the bitmap and .NET Graphics can only be created on a bitmap with a non-indexed format.
        // So we use 32bpp ARGB when the source pixel format is indexed.
        // JAVA: This has some problems either way. If we create indexed bitmaps (using special BufferedImage ctor
        // the color sometimes comes out dithered and transparency lost. If we create ARGB bitmaps some
        // bitmap colors get screwed occasionally. So leave as is for until find a perfect solution.
        if (getColorModel(imageType) == ColorModel.INDEXED)
            imageType = BufferedImage.TYPE_INT_ARGB;

        //JAVA-added: I have seen some PNG images return TYPE_CUSTOM and creating a BufferedImage of this type fails,
        // so we fallback to a more suitable value.
        if (imageType == BufferedImage.TYPE_CUSTOM)
            imageType = BufferedImage.TYPE_INT_ARGB;

        // WORDSJAVA-1865, WORDSNET-16561. CantCreateBitmapException is now thrown upon trying to create bitmap with invalid parameters.
        // Unfortunately there's no easy way to find the exact reason of a problem automatically.
        BufferedImage bmp;
        try
        {
            bmp = new BufferedImage(width, height, imageType);
        }
        catch (Throwable any) // Catch both Exceptions and runtime Errors (Out Of Memory and likes).
        {
            if (JavaDebug.ON)
                System.out.println("CantCreateBitmapException: " + any.toString());

            throw new CantCreateBitmapException(any.toString());
        }

        setBitmapInternal(
                bmp,
                FileFormat.BMP, // I think that's what .NET does.
                ImageSizeCore.createWithResolution(width, height, hRes, vRes));
    }

    public BitmapPal(int width, int height, /*PixelFormat*/int pixelFormat, byte[] bytes, DrColor[] palette)
    {
        //JAVA-changed: specific logic
        /*
        BufferedImage bitmap = new BufferedImage(width, height, pixelFormat);
        try
        {
             // Setup palette colors.
             if (palette != null)
             {
                 for (int i = 0; i < bitmap.Palette.Entries.length && i < palette.length; i++)
                 {
                     bitmap.Palette.Entries[i] = palette[i].toNativeColor();
                }
            }
            Rectangle rect = new Rectangle(0, 0, width, height);
             BitmapData bitmapData = bitmap.LockBits(rect, ImageLockMode.WriteOnly, pixelFormat);
            try
             {
                 Marshal.Copy(bytes, 0, bitmapData.Scan0, bytes.length);
             }
             finally
             {
                 bitmap.UnlockBits(bitmapData);
             }

             setBitmapInternal(bitmap);
        }
        catch(Exception e)
        {
             bitmap.flush();
             throw e;
        }
		*/
        try
        {
            if (palette != null)
            {
                if (JavaDebug.ON)
                    System.out.println("= Changing Palette Colors is not implemented yet =" + palette);
            }

            int imageType = PixelFormat.getBufferedImageType(pixelFormat);
            BufferedImage result = new BufferedImage(width, height, imageType);
            PalBitmapCore.setPixels(result, bytes, pixelFormat);

            ImageSizeCore size = ImageSizeCore.createWithResolution(width, height, ImageConstants.STANDARD_RESOLUTION, ImageConstants.STANDARD_RESOLUTION);
            setBitmapInternal(result, FileFormat.BMP, size);

        }
        catch (Throwable any)
        {
            throw new CantCreateBitmapException(any.toString());
        }
    }

    /// <summary>
    /// Loads a bitmap from image bytes.
    /// What formats are to be supported? Need to list to make sure it works both in .NET and JAVA.
    /// </summary>
    public BitmapPal(byte[] imageBytes) throws Exception
    {
        this(imageBytes, null);
    }

    public BitmapPal(byte[] imageBytes, ImageSizeCore scaledSize) throws Exception
    {
        this(imageBytes, scaledSize, null);
    }

    public BitmapPal(byte[] imageBytes, ImageSizeCore scaledSize, IFontProvider fontProvider) throws Exception
    {
        this(new MemoryStream(imageBytes), scaledSize, true, fontProvider);
    }

    public BitmapPal(Stream imageStream) throws Exception
    {
        this(imageStream, true, null);
    }

    public BitmapPal(Stream imageStream, boolean emulateRasterOperations, IFontProvider fontProvider) throws Exception
    {
        this(imageStream, null, emulateRasterOperations, fontProvider);
    }

    private BitmapPal(Stream imageStream, ImageSizeCore scaledSize, boolean emulateRasterOperations, IFontProvider fontProvider) throws Exception
    {
        imageStream = CompressedData.getStream(imageStream);
        int imageType = ImageUtil.getImageType(imageStream);

        // WORDSNET-7384 If failed to recognize image data as a common image type, then try to load imagedata from rare image formats.
        if (imageType == FileFormat.UNKNOWN)
        {
            if (ImageUtil.isDib(imageStream))
            {
                BinaryReader reader = new BinaryReader(imageStream);
                byte[] imageBytes = ImageUtil.prependBmpHeader(reader, (int) imageStream.getLength());
                imageType = ImageUtil.getImageType(imageBytes);// should be bmp.
                imageStream = new MemoryStream(imageBytes);
            }
            else if (ImageUtil.isWebP(imageStream))
            {
                imageStream.setPosition(0);
                imageType = FileFormat.WEB_P;
            }
        }

        if (!ImageUtil.isImage(imageType))
            throw new CantCreateBitmapException("Parameter is not valid. FileFormat =" + imageType);

        BitmapPal image = imageFromStream(imageStream, imageType, scaledSize, fontProvider, emulateRasterOperations);
        setBitmapInternal(image.getNativeBitmap(), imageType, image.getImageSize());
        setIsOriginalResolutionZero(image.getImageSize(), imageType);
        mWasCmykOrYcck = image.mWasCmykOrYcck;
    }

    /// <summary>
    /// Creates instance of <see cref="BitmapPal"/> class from a specified Bitmap.
    /// </summary>
    public BitmapPal(BufferedImage bitmap)
    {
        setBitmapInternal(
                bitmap,
                FileFormat.BMP,
                ImageSizeCore.createWithResolution(
                        bitmap.getWidth(), bitmap.getHeight(),
                        ImageConstants.STANDARD_RESOLUTION, ImageConstants.STANDARD_RESOLUTION));
    }

    public BitmapPal(BufferedImage bitmap, /*Size*/long size)
    {
        try
        {
            int width = msSize.getWidth(size);
            int height = msSize.getHeight(size);

            bitmap = PalBitmapCore.getScaledInstance(bitmap, width, height, true);
            setBitmapInternal(
                    bitmap,
                    FileFormat.BMP,
                    ImageSizeCore.createWithResolution(
                            width, height,
                            ImageConstants.STANDARD_RESOLUTION, ImageConstants.STANDARD_RESOLUTION));
        }
        catch (Exception originalException)
        {
            throw new CantCreateBitmapException(originalException);
        }
    }

    public BitmapPal(BufferedImage image, float hRes, float vRes)
    {
        setBitmapInternal(
                image,
                FileFormat.BMP,
                ImageSizeCore.createWithResolution(image.getWidth(), image.getHeight(), hRes, vRes));
    }

    public void convertToBitonal()
    {
        this.convertTo1Bpp();
    }

    public int getPixelFormat()
    {
        return getNativeBitmap().getType();
    }

    private void setBitmapInternal(BufferedImage bitmap, /*FileFormat*/int imageType, ImageSizeCore imageSize)
    {
        // FIX WORDSJAVA-932: resize canvas to match size specified in image header
        if (bitmap.getWidth() < imageSize.getWidth() || bitmap.getHeight() < imageSize.getHeight())
            bitmap = resizeCanvas(bitmap, imageSize);

        setBitmap(bitmap, imageType, imageSize);

        //JAVA-deleted:
        // FIX 6728 Conversation was moved here from SaveJpeg method.
        // FIX 23947 MS Word seems to convert CMYK bitmaps to RGB when writing to PDF so we do the same.
        //if (isCmykPixelFormat())
        //    convertTo24BppRgb();
        //
        //setImageType(imageFormatToImageType(getBitmap().RawFormat));
    }

    // JAVA-added: FIX WORDSJAVA-932: resize canvas to match size specified in image header
    private static BufferedImage resizeCanvas(BufferedImage in, ImageSizeCore imageSize)
    {
        int wOut = imageSize.getWidth();
        int hOut = imageSize.getHeight();

        int wIn = in.getWidth();
        int hIn = in.getHeight();

        // translate transform to center original image on new canvas
        int left = (wOut - wIn) / 2;
        int top = (hOut - hIn) / 2;
        // FIX WORDSJAVA-932: Hardcode fix to conform .NET output: move image 1 pixel to the left; can't explain it
        if (left > 0) left--;

        if (in.getType() == BufferedImage.TYPE_BYTE_BINARY || in.getType() == BufferedImage.TYPE_BYTE_INDEXED)
        {
            byte[] dataIn = (byte[]) in.getRaster().getDataElements(0, 0, wIn, hIn, null);
            IndexColorModel icmIn = (IndexColorModel) in.getColorModel();

            if (icmIn.hasAlpha() && icmIn.getTransparentPixel() != -1)
            {
                // FIX WORDSJAVA-1046: FileCorruptedException is thrown while loading Docx.
                // If the source index color model already supports alpha channel and contains fully transparent pixel
                // then we can use it for resized raster.

                WritableRaster rasterOut = icmIn.createCompatibleWritableRaster(wOut, hOut);
                byte[] dataOut = new byte[wOut * hOut];

                // copy pixel data from source bitmap to resized bitmap
                int tmpIn, tmpOut;
                for (int y = 0; y < hIn; y++)
                {
                    tmpIn = y * wIn;
                    tmpOut = (y + top) * wOut;

                    for (int x = 0; x < wIn; x++)
                        dataOut[tmpOut + (x + left)] = dataIn[tmpIn + x];
                }
                rasterOut.setDataElements(0, 0, wOut, hOut, dataOut);
                return new BufferedImage(icmIn, rasterOut, false, null);
            }
            else
            {
                int[] cmapIn = new int[icmIn.getMapSize()];
                icmIn.getRGBs(cmapIn);

                int[] cmapOut = new int[cmapIn.length + 1];
                cmapOut[0] = 0; // transparent pixel
                System.arraycopy(cmapIn, 0, cmapOut, 1, cmapIn.length);
                // Custom color model for indexed image with alpha channel
                IndexColorModel icmOut = new IndexColorModel(8, 4, cmapOut, 0, true, 0, DataBuffer.TYPE_BYTE);
                WritableRaster rasterOut = icmOut.createCompatibleWritableRaster(wOut, hOut);
                byte[] dataOut = new byte[wOut * hOut];

                int tmpIn, tmpOut;
                for (int y = 0; y < hIn; y++)
                {
                    tmpIn = y * wIn;
                    tmpOut = (y + top) * wOut;
                    for (int x = 0; x < wIn; x++)
                    {
                        // because of added transparent pixel, all indexes in the color map will be increased by one
                        dataOut[tmpOut + (x + left)] = (byte) (dataIn[tmpIn + x] + 1);
                    }
                }
                rasterOut.setDataElements(0, 0, wOut, hOut, dataOut);
                return new BufferedImage(icmOut, rasterOut, false, null);
            }
        }
        else
        {
            BufferedImage out = new BufferedImage(wOut, hOut, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = out.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
            g.setComposite(AlphaComposite.SrcOver);
            g.drawImage(in, left, top, null);
            g.dispose();
            return out;
        }
    }

    /// <summary>
    /// Gets or sets internal native bitmap.
    /// </summary>
    private BufferedImage getBitmap() { return (BufferedImage) getNativeImage(); }

    // JAVA: not used for bitmap initialization in java.
    // Used only for mask operations like flip(), applyChromaKey(), applyImageAttributes() etc.
    private void setBitmap(BufferedImage value) { setNativeImage(value); }

    //JAVA-added. The main setter used for image initialization in java.
    private void setBitmap(BufferedImage value, /*FileFormat*/int imageType, ImageSizeCore imageSize)
    {
        setNativeImage(value, imageType, imageSize);
    }

    /// <summary>
    /// Temporary platform-specific non-optimized implementation of ImageUtil.HasAlphaChannel(byte[]).
    /// </summary>
    public static boolean imageHasAlphaChannel(byte[] imageBytes) throws Exception
    {
        switch (ImageUtil.getImageType(imageBytes))
        {
            case FileFormat.WMF:
            case FileFormat.EMF:
                return true;
            case FileFormat.PNG:
                //Custom PNG alpha channel detector (see WORDSJAVA-2843)
                return isPNGImageHasAlphaChannel(new MemoryStream(imageBytes));
            default:
                BufferedImage bufferedImage = loadNativeImage(new MemoryStream(imageBytes));
                return bufferedImage.getColorModel().hasAlpha();
        }
    }

    /// <summary>
    /// Returns the color model that will be used when <see cref="GetPixels"/> is called.
    /// The parsing color model is normally the same as the underlying bitmap color model.
    ///
    /// But it might be different. For example on .NET if we encounter a 16bit gray scale image,
    /// then we will request its data as 24bit RGB for pixel parsing.
    /// </summary>
    public /*ColorModel*/int getParsingColorModel()
    {
        return getColorModel(getParsingPixelFormat(getBitmap().getType()));
    }

    /// <summary>
    /// Gets the native object that this wrapper encapsulates. Should be the native type in each platform implementation.
    /// </summary>
    public /*Bitmap*/BufferedImage getNativeBitmap()
    {
        return getBitmap();
    }

    /// <summary>
    /// Draws this bitmap onto another bitmap. Allows to positively crop and to scale the image.
    /// </summary>
    public void drawImage(Rectangle srcRect, BitmapPal dstBitmap, Rectangle dstRect)
    {
        drawImage(srcRect, dstBitmap, dstRect, null);
    }

    /// <summary>
    /// Draws this bitmap onto another bitmap. Allows to positively crop and to scale the image. The bitmap can be
    /// drawn on a colored canvas.
    /// </summary>
    private void drawImage(Rectangle srcRect, BitmapPal dstBitmap, Rectangle dstRect, DrColor backgroundColor)
    {
        PalBitmapCore.drawImage(getBitmap(), srcRect, dstBitmap.getBitmap(), dstRect, backgroundColor);
    }

    /// <summary>
    /// Positively crops (only making the image smaller) the image and returns the new image.
    /// The new image has the same resolution as the original image.
    /// </summary>
    public BitmapPal positivelyCrop(Rectangle srcRect)
    {
        // WORDSNET-27377
        // Image manipulation using the System.Drawing.Graphics object destroys the transparency of GIF images.
        // Previously we used  GDI+ Graphics.DrawImage for cropping here. The problem is that GDI+ cannot create
        // Graphics object for indexed image format Bitmap (e.g this happens when the Bitmap is loaded from GIF).
        // So we had to create the destination bitmap with 32-bit ARGB format. When using Graphics.DrawImage
        // the transparency information is converted into alpha channel values which produce the black background
        // when writing to indexed image formats.
        // Fortunately, there is a more direct way to positively crop an image without using the Graphics object.
        Rectangle cropRect = Rectangle.intersect(srcRect, getBounds());
        BufferedImage croppedImage = getBitmap().getSubimage(cropRect.getLeft(), cropRect.getTop(), cropRect.getWidth(), cropRect.getHeight());

        // WORDSNET-14121 Set image type of the resulting bitmap to the original bitmap's image type.
        BitmapPal result = new BitmapPal(croppedImage);
        result.setImageType(getImageType());

        return result;
    }

    /// <summary>
    /// Scales the specified crop rectangle of the bitmap to specified size.
    /// </summary>
    public BitmapPal scaleCore(Rectangle cropRect, /*Size*/long dstSize, float hRes, float vRes, boolean useMultiStepUpscaling)
    {
        // kvk: GDI+ DrawImage method has a bug in the interpolation algorithm.
        // When image is stretched/shrinked DrawImage takes the pixels outside of the cropping rectangle
        // to calculate the border pixels of the resulting image. In case when cropping rectangle equals
        // to the image bounds, pixels outside of the cropping rectangle are considered fully transparent.
        // So in this case the border of the resulting image becomes partially transparent even for fully
        // opaque images. Here is more info about this bug http://www.codeproject.com/Articles/14884/BorderBug.

        // When rendering to PDF (for which this method is used now) transparency is highly undesirable.
        // So to fix the problem use PixelOffsetMode.HighQuality and WrapMode.TileFlipXY.
        // PixelOffsetMode.HighQuality gives better border quality.
        // WrapMode.TileFlipXY prevents transparency of the border.

        // Probably this hack could be used in BitmapPal.DrawImage method which may scale the images too.
        // But I'm not sure that it doesn't have side effects. And there was no complains about the border problem.
        // So use it only for PDF rendering for now.

        // Crop
        Rectangle srcRect = Rectangle.intersect(getBounds(), cropRect);
        BufferedImage bmp = getBitmap().getSubimage(srcRect.getLeft(), srcRect.getTop(), srcRect.getWidth(), srcRect.getHeight());

        // Scale
        if (JavaDebug.ON)
            System.out.println("Scale from " + srcRect.getWidth() + "x" + srcRect.getHeight() + " to " + msSize.getWidth(dstSize) + "x" + msSize.getHeight(dstSize));

        bmp = PalBitmapCore.getScaledInstance(bmp, msSize.getWidth(dstSize), msSize.getHeight(dstSize), useMultiStepUpscaling);
        return new BitmapPal(bmp, hRes, vRes);
    }

    /// <summary>
    /// Returns image bounds.
    /// </summary>
    public Rectangle getBounds()
    {
        return new Rectangle(0, 0, getBitmap().getWidth(), getBitmap().getHeight());
    }

    /// <summary>
    /// Saves the image.
    /// </summary>
    public void save(Stream stream, /*FileFormat*/int imageType) throws Exception
    {
        setStandardResolutionIfOriginalResolutionWasZero();

        switch (imageType)
        {
            case FileFormat.TIFF:
                // 128 - Default value threshold for ImageBinarizationMethodCore.FloydSteinbergDithering
                saveTiff(stream, TiffCompressionCore.LZW, ImageBinarizationMethodCore.THRESHOLD, (byte) 128, getPixelFormat());
                break;
            case FileFormat.PNG:
                savePng(stream);
                break;
            case FileFormat.JPEG:
                // GDI+ has default quality close to this value. I am setting it explicitly here because
                // I hope this will help to make some Java JPEGs more similar to .NET.
                saveJpeg(stream, 80);
                break;
            case FileFormat.BMP:
                saveBmp(stream);
                break;
            case FileFormat.GIF:
                saveGif(stream);
                break;
            case FileFormat.WEB_P:
                saveWebP(stream);
                break;
            default:
                throw new IllegalStateException("Cannot save in this image format.");
        }
    }

    /// <summary>
    /// Saves the image.
    /// </summary>
    /// <param name="stream"></param>
    /// <param name="jpegQuality">Valid range is 0-100.</param>
    public void saveJpeg(Stream stream, int jpegQuality) throws Exception
    {
        ImageWriterBuilder.Options options = new ImageWriterBuilder.Options()
                .withJpegQuality(jpegQuality)
                .withHorizontalResolution(getHorizontalResolution())
                .withVerticalResolution(getVerticalResolution())
                .withIsCmyk(isCmykPixelFormat())
                .withBounds(getBounds());
        new ImageWriterBuilder()
                .withImageIo()
                .build()
                .writeJpeg(getNativeImage(), stream, options);
        setBitmap(options.outImage());
    }

    /// <summary>
    /// Saves the image in GIF format into specified stream.
    /// </summary>
    public void saveGif(Stream stream) throws Exception
    {
        new ImageWriterBuilder().withImageIo().build().writeGif(getNativeImage(), stream);
    }

    public void savePng(Stream stream) throws Exception
    {
        // FIX WORDSJAVA-1239: Docx to PNG performance issue
        // As practice shows, PngEncoder works faster than ImageIO. See http://objectplanet.com/pngencoder/ for details.
        ImageWriterBuilder.Options options = new ImageWriterBuilder.Options()
                .withHorizontalResolution(getHorizontalResolution())
                .withVerticalResolution(getVerticalResolution())
                .withBounds(getBounds());
        new ImageWriterBuilder()
                .withPngEncoder()
                .withImageIo()
                .build()
                .writePng(getNativeImage(), stream, options);
        setBitmap(options.outImage());

        //JAVA-deleted MONO workaround
        //if (PalSystem.getPlatform() == Platform.WINDOWS)
        //    PalBitmapCore.savePngCore(this, new NetOutputStream(stream));
        //else
        //{
        //    // FIX 12001 MONO. Add image resolution to PNG data manually. (Mono doesn't do it.)
        //    Stream tmp = new MemoryStream();
        //    ImageUtil.savePngResolution(tmp, stream, getHorizontalResolution(), getVerticalResolution());
        //}
    }

    public void saveBmp(Stream stream) throws Exception
    {
        ImageWriterBuilder.Options options = new ImageWriterBuilder.Options().withBounds(getBounds());
        new ImageWriterBuilder().withImageIo().build().writeBmp(getNativeImage(), stream, options);
        setBitmap(options.outImage());
    }

    /// <summary>
    /// Saves a single frame TIFF.
    /// </summary>
    public void saveTiff(Stream stream, /*TiffCompressionCore*/int compression, /*ImageBinarizationMethodCore*/int binarizationMethod, byte threshold,
            /*PixelFormat*/int pixelFormat) throws Exception
    {
        ImageWriterBuilder.Options options = new ImageWriterBuilder.Options()
                .withTiffCompression(compression)
                .withBinarizationMethod(binarizationMethod)
                .withHorizontalResolution(getHorizontalResolution())
                .withVerticalResolution(getVerticalResolution())
                .withThreshold(threshold);

        new ImageWriterBuilder().withImageIo().withSimplifiedTiff().build().writeTiff(getNativeImage(), stream, options);
    }

    /// <summary>
    /// Saves the image in WebP format into specified stream.
    /// </summary>
    public void saveWebP(Stream stream) throws Exception
    {
        setStandardResolutionIfOriginalResolutionWasZero();
        saveWebPCore(getBitmap(), stream);
    }

    private static void saveWebPCore(BufferedImage image, Stream stream) throws Exception
    {
        WebPConverterPal.writeBySkia(image, stream);
    }

    /// <summary>
    /// Returns true if specified pixel format supports alpha channel.
    /// </summary>
    private static boolean doesPixelFormatSupportsAlphaChannel(java.awt.image.ColorModel pixelFormat)
    {
        return pixelFormat.hasAlpha();
    }

    /// <summary>
    /// Extracts all the pixel color values into byte arrays and returns in a huge memory object.
    /// Also uses chroma key in alpha values calculation.
    /// </summary>
    /// <param name="isConvertTo1Bpp">If true, causes the image pixels to be returned as 1bpp black and white.</param>
    public DrPixels getPixels(boolean isConvertTo1Bpp)
    {
        if (isConvertTo1Bpp)
        {
            convertTo1Bpp();

            // Both parseIndexed and parseGrayScale will do the job okay. But I call parseGrayScale
            // because this result is returned to PDF renderer which expects it to be grayscale.
            DrPixels pixels = PalBitmapCore.parseGrayScale(getBitmap());

            // It seems that inversion is required.
            byte[] colorValues = pixels.getColorValues();
            for (int i = 0; i < colorValues.length; i++)
                colorValues[i] = (byte) (~colorValues[i]);

            return pixels;
        }
        else
        {
            // We might select a pixel format different from the original to make it simpler for us to parse
            // the pixel data and LockBits will convert data into that format for us.
            /*PixelFormat*/
            int parsingPixelFormat = getParsingPixelFormat(getBitmap().getType());

            //JAVA-deleted:
            // Useful info here http://www.bobpowell.net/lockingbits.htm
            //bitmapData = getBitmap().LockBits(new Rectangle(0, 0, getBitmap().Width, getBitmap().Height),
            //	ImageLockMode.ReadOnly, parsingPixelFormat);

            switch (getColorModel(parsingPixelFormat))
            {
                case ColorModel.RGB:
                    return parseRgb(getBitmap(), false);
                case ColorModel.INDEXED:
                    return parseIndexed(getBitmap());
                case ColorModel.GRAY_SCALE:
                    return PalBitmapCore.parseGrayScale(getBitmap());
                default:
                    throw new IllegalStateException("Unknown color space.");
            }
        }
    }

    /// <summary>
    /// Converts a pixel format into a simpler format that will be used when <see cref="GetPixels"/> is called.
    /// </summary>
    private static /*PixelFormat*/int getParsingPixelFormat(/*PixelFormat*/int pixelFormat)
    {
        //JAVA-changed
        return pixelFormat;
    }

    // JAVA-changed: PixelFormat not used here, BufferedImage.TYPE_* used instead.
    private static int getColorModel(/*PixelFormat*/ int imageType)
    {
        // All the bitmap types that are currently in the switch - have been tested.
        // Untested bitmap types fail with an exception. When you get an unknown bitmap type, add it appropriately.
        switch (imageType)
        {
            case BufferedImage.TYPE_INT_RGB:
            case BufferedImage.TYPE_INT_ARGB:
            case BufferedImage.TYPE_INT_ARGB_PRE:
            case BufferedImage.TYPE_INT_BGR:
            case BufferedImage.TYPE_3BYTE_BGR:
            case BufferedImage.TYPE_4BYTE_ABGR:
            case BufferedImage.TYPE_4BYTE_ABGR_PRE:
            case BufferedImage.TYPE_USHORT_565_RGB:
            case BufferedImage.TYPE_USHORT_555_RGB:
            case BufferedImage.TYPE_CUSTOM:
                return ColorModel.RGB;
            case BufferedImage.TYPE_BYTE_BINARY:
            case BufferedImage.TYPE_BYTE_INDEXED:
                return ColorModel.INDEXED;
            case BufferedImage.TYPE_BYTE_GRAY:
            case BufferedImage.TYPE_USHORT_GRAY:
                return ColorModel.GRAY_SCALE;
            default:
                throw new IllegalStateException("Unknown bitmap type: " + imageType);
        }
    }

    /// <summary>
    /// Convert GDI+ BitmapData into a binary array.
    /// Can only parse 8bit per channel RGB or ARGB data.
    /// </summary>
    private static DrPixels parseRgb(
            BufferedImage bitmap,
            boolean extractOnlyAlphaChannel)
    {
        boolean hasAlphaChannel = doesPixelFormatSupportsAlphaChannel(bitmap.getColorModel());

        // If the is no alpha in the image and we need only alpha then we could stop here.
        if (!hasAlphaChannel && extractOnlyAlphaChannel)
            return new DrPixels(ArrayUtil.EMPTY_BYTE_ARRAY, ArrayUtil.EMPTY_BYTE_ARRAY, false, ColorModel.RGB, 8, null);

        // We want exactly 3 color component RGB in this array.
        byte[] colorValues = new byte[extractOnlyAlphaChannel ? 0 : bitmap.getWidth() * bitmap.getHeight() * 3];
        byte[] alphaValues = new byte[(hasAlphaChannel) ? bitmap.getWidth() * bitmap.getHeight() : 0];

        boolean hasTransparentPixels = false;
        int colorValueIdx = 0;
        int alphaValueIdx = 0;

        int[] componentSizes = bitmap.getColorModel().getComponentSize();
        int colorSpaceType = bitmap.getColorModel().getColorSpace().getType();

        Raster raster = bitmap.getRaster();
        // This will be 3 elements if RGB and 4 elements if ARGB.
        int[] pixelData = new int[componentSizes.length];

        for (int y = 0; y < bitmap.getHeight(); y++)
        {
            for (int x = 0; x < bitmap.getWidth(); x++)
            {
                raster.getPixel(x, y, pixelData);

                for (int i = 0; i < pixelData.length; i++)
                    pixelData[i] = PalBitmapCore.scaleTo8BitColor(pixelData[i], componentSizes[i]);

                if (colorSpaceType == ColorSpace.TYPE_CMYK || colorSpaceType == ColorSpace.TYPE_5CLR)
                    PalBitmapCore.cmykToRgb(pixelData);

                byte r = (byte) (pixelData[0] & 0xFF);
                byte g = (byte) (pixelData[1] & 0xFF);
                byte b = (byte) (pixelData[2] & 0xFF);

                if (hasAlphaChannel)
                {
                    byte alphaValue = (byte) (pixelData[3] & 0xFF);
                    alphaValues[alphaValueIdx++] = alphaValue;

                    if ((alphaValue & 0xFF) < 0xFF)
                        hasTransparentPixels = true;
                }

                if (!extractOnlyAlphaChannel)
                {
                    colorValues[colorValueIdx + 2] = b;
                    colorValues[colorValueIdx + 1] = g;
                    colorValues[colorValueIdx + 0] = r;
                    colorValueIdx += 3;
                }
            }
        }

        return new DrPixels(colorValues, alphaValues, hasTransparentPixels, ColorModel.RGB, 8, null);
    }

    private static DrPixels parseIndexed(BufferedImage bitmap)
    {
        int bitsPerComponent = bitmap.getColorModel().getComponentSize(0);

        // @nixspirit. It seems we don't need it anymore. All tests pass well.
        // This I can see with JAI libraries installed on Format1bppIndexed.png,
        // the color model returns 8bpp, but it is a 1bpp bitmap actually.
        // if ((bitmap.getType() == BufferedImage.TYPE_BYTE_BINARY) && (bitsPerComponent == 8))
        //      bitsPerComponent = 1;

        byte[] colorValues = new byte[bitmap.getWidth() * bitmap.getHeight()];

        boolean hasAlphaValues = hasPaletteAlphaValues(bitmap);
        byte[] alphaValues = new byte[(hasAlphaValues) ? bitmap.getWidth() * bitmap.getHeight() : 0];
        int alphaIdx = 0;
        boolean hasTransparentPixels = false;

        int colorValueIdx = 0;

        // RK This code works for TestHR.doc. It has an indexed-color PNG with a transparent color.
        IndexColorModel colorModel = (IndexColorModel) bitmap.getColorModel();
        SampleModel sampleModel = bitmap.getSampleModel();
        DataBuffer data = bitmap.getRaster().getDataBuffer();

        //Actual parsing of the data
        try
        {
            for (int y = 0; y < bitmap.getHeight(); y++)
            {
                // If we need to get alpha channel, we have to parse image data.
                for (int x = 0; x < bitmap.getWidth(); x++)
                {
                    int pixel = getPixel(sampleModel, data, x, y);

                    colorValues[colorValueIdx++] = (byte) (pixel & 0xFF);

                    if (hasAlphaValues)
                    {
                        DrColor pixelColor = getPixelFromIndexedImage(colorModel, pixel);

                        byte alphaValue = (byte) pixelColor.getA();
                        alphaValues[alphaIdx++] = alphaValue;

                        if (alphaValue < 0xFF)
                            hasTransparentPixels = true;
                    }
                }
            }
        }
        catch (Exception e)
        {
            Log.error(e);
        }

        return new DrPixels(colorValues, alphaValues, hasTransparentPixels, ColorModel.INDEXED, bitsPerComponent, paletteToColorTable(bitmap));
    }

    private static DrColor[] paletteToColorTable(BufferedImage palette)
    {
        IndexColorModel colorModel = (IndexColorModel) palette.getColorModel();
        DrColor[] result = new DrColor[colorModel.getMapSize()];
        for (int i = 0; i < result.length; i++)
            result[i] = new DrColor(colorModel.getRGB(i));

        return result;
    }

    private static boolean hasPaletteAlphaValues(BufferedImage src)
    {
        //JAVA-changed from:
        //foreach (Color color in palette.Entries)
        //if (color.A != 0xFF)
        //    return true;

        java.awt.image.ColorModel palette = src.getColorModel();
        return palette.hasAlpha();
    }

    /// <summary>
    /// Returns pixel from indexed image.
    /// </summary>
    /// <param name="imageData">Indexed image data.</param>
    /// <param name="bitsPerComponent">Number of bits per color component.</param>
    /// <param name="palette">Palette.</param>
    /// <param name="rowStartIndex">Start index in the image data of the required row.</param>
    /// <param name="column">Required column index.</param>
    /// <returns>Pixel color.</returns>
    private static DrColor getPixelFromIndexedImage(IndexColorModel palette, int pixel)
    {
        int a = palette.getAlpha(pixel);
        int r = palette.getRed(pixel);
        int g = palette.getGreen(pixel);
        int b = palette.getBlue(pixel);

        Color nativeColor = new Color(r, g, b, a);
        return DrColor.fromNativeColor(nativeColor);
    }

    /// <summary>
    /// Loads a native image from stream.
    /// </summary>
    public static BufferedImage loadNativeImage(Stream stream) throws Exception
    {
        return imageFromStream(stream, ImageUtil.getImageType(stream), null, null, true).getNativeBitmap();
    }

    /// <summary>
    /// Loads a native image from file.
    /// </summary>
    public static BufferedImage loadNativeImage(String fileName) throws Exception
    {
        FileStream stream = new FileStream(fileName, FileMode.OPEN);
        try /*JAVA: was using*/
        {
            return loadNativeImage(stream);
        }
        finally
        {
            if (stream != null) stream.close();
        }
    }

    /// <summary>
    /// Saves an image into a stream in a format that will satisfy MS Word 97.
    ///
    /// In .NET the image parameter is a GDI+ Image, but in Java the image parameter is BufferedImage.
    ///
    /// This is a static method (not an instance method) because in .NET it accepts a <see cref="Image"/> parameter
    /// that can either be a metafile or bitmap and saves it accordingly.
    /// </summary>
    /// <param name="image">The image to save.</param>
    /// <param name="stream">The destination stream.</param>
    public static void saveNativeImageForWord97(BufferedImage image, Stream stream) throws Exception
    {
        // In .NET this code worked by using the original image format obtained from the image object.
        // In Java I don't think we can get the original image format and therefore let's just always save as PNG.
        BitmapPal bitmap = new BitmapPal(image);
        bitmap.savePng(stream);
        // This is not really needed on Java. But in .NET this avoids our code disposing the bitmap object provided by the user.
        bitmap.setBitmap(null);
    }

    /// <summary>
    /// Returns image bytes of specified image in a format that will satisfy MS Word 97.
    /// </summary>
    public static byte[] getImageBytes(ImagePal imagePal) throws Exception
    {
        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            BitmapPal.saveNativeImageForWord97(imagePal.getNativeImage(), stream);
            return StreamUtil.copyStreamToByteArray(stream);
        }
        finally
        {
            if (stream != null) stream.close();
        }
    }

    /* //JAVA-deleted:
    /// <summary>
    /// This is needed to save a WMF or EMF metafile object as an EMF file again. Seems no easier way in .NET.
    /// Note this makes the destination EMF resolution 96dpi. I don't like this (it would be better
    /// to keep the dpi of the original metafile), but I don't know how to do this in .NET.
    ///
    /// AL: To set resolution simply change the hdc resolution. RK ??
    /// </summary>
    private static void saveMetafile(Metafile srcMetafile, Stream dstStream) throws Exception
	{
	}
	*/

    /// JAVA-added. Method to extract pixel from the sample model
    private static int getPixel(SampleModel sampleModel, DataBuffer data, int x, int y)
    {
        // It seems fore IndexColorModel we use always 0 as a band to return .
        return sampleModel.getSample(x, y, 0, data);
    }

    private static BitmapPal imageFromStream(Stream stream, /*ImageTypeCore*/ int imageType, ImageSizeCore scaledSize, IFontProvider fontProvider, boolean emulateRasterOperations) throws Exception
    {
        // Throw this to make our image behave more like GDI+ images.
        if (imageType == FileFormat.UNKNOWN)
            throw new IllegalArgumentException(
                    "The image format is not supported. FileFormat=" + FileFormat.toString(imageType));

        BufferedImage result;
        ImageSizeCore imageSize;
        final ImageReaderBuilder builder = new ImageReaderBuilder();
        final Options options = new Options().withInFormat(imageType);

        stream.setPosition(0);
        if (ImageUtil.isMetafile(imageType))
        {
            // This build has no metafile renderer, so a WMF or EMF cannot be turned into a bitmap.
            throw new CantCreateBitmapException("Metafiles cannot be rasterized. FileFormat=" + FileFormat.toString(imageType));
        }
        else if (imageType == FileFormat.TIFF)
        {
            imageSize = ImageUtil.getImageSize(stream, imageType);

            // ImageIO TIFF reader read some TIFFs incorrectly, so try TWM first then ImageIO.
            ImageReader reader = builder.withTWM().withImageIo().build();
            result = readImageWithConventionalException(stream, reader, options);
        }
        else if (imageType == FileFormat.WEB_P)
        {
            imageSize = ImageUtil.getImageSize(stream, imageType);
            result = WebPConverterPal.readBySkia(stream);
        }
        else
        {
            imageSize = ImageUtil.getImageSize(stream, imageType);

            // Others
            ImageReader reader = builder.withImageIo().build();

            result = readImageWithConventionalException(stream, reader, options);
        }

        BitmapPal bitmap = new BitmapPal();
        bitmap.setImageSize(imageSize);
        bitmap.setBitmap(result);
        bitmap.mWasCmykOrYcck = options.isCmyk();
        return bitmap;
    }

    private static BufferedImage readImageWithConventionalException(Stream stream, ImageReader reader, Options options)
    {
        try
        {
            return reader.read(stream, options);
        }
        catch (Exception e)
        {
            if(NativeLibSettings.getInterruptThreadIfImageExceptionThrown())
            {
                Thread.currentThread().interrupt();
                throw new ImageProcessingInterruptException();
            }
            else
            {
                throw new CantCreateBitmapException(e);
            }
        }
    }

    /// <summary>
    /// Converts the bitmap to 24bppRgb pixel format.
    /// </summary>
    /// <param name="backgroundColor">Canvas color to re-draw the image on.</param>
    private void convertTo24BppRgb(DrColor backgroundColor)
    {
        BitmapPal rgbBitmap = new BitmapPal(
            getWidth(), getHeight(), getHorizontalResolution(), getVerticalResolution(), PixelFormat.FORMAT_24_BPP_RGB);

        Rectangle rect = getBounds();
        drawImage(rect, rgbBitmap, rect, backgroundColor);
        setBitmap(rgbBitmap.getBitmap());
        rgbBitmap.setBitmap(null);
    }

    /// <summary>
    /// Converts the bitmap to 32BppArgb pixel format.
    /// <remarks>
    /// If the bitmap is in 32BppArgb pixel format, returns this one, otherwise creates a new image.
    /// </remarks>
    /// </summary>
    BitmapPal convertTo32BppArgb()
    {
        //PixelFormat.FORMAT_32_BPP_ARGB => BufferedImage.TYPE_INT_ARGB
        // Convert only if original bitmap is not already in 32 BPP ARGB format.
        if (getBitmap().getType() == BufferedImage.TYPE_INT_ARGB)
            return this;

        BufferedImage newBitmap = ImageConverter.convertTo(getBitmap(), getBounds(), BufferedImage.TYPE_INT_ARGB);
        return new BitmapPal(newBitmap);
    }

    /**
     * Used by PdfImage to check whether original image bytes can be saved without transformations. If this returns true
     * - original bytes should be converted before save since pdf doesn't understand cmyks.
     */
    public boolean isCmykPixelFormat()
    {
        //JAVA-changed completely: mWasCmykOrYcck is set by java-added manual check for Cmyk and Ycck.
        // Fix Jira6409.
        // Systems under Windows 7 don't recognize PixelFormat right for Cmyk images.
        // In such cases we must use Image.Flags to determinate the Cmyk color space.
        ///*ImageFlags*/int flags = (/*ImageFlags*/int)getBitmap().Flags;
        //if (((flags & ImageFlags.ColorSpaceCmyk) == ImageFlags.ColorSpaceCmyk) ||
        //    ((flags & ImageFlags.ColorSpaceYcck) == ImageFlags.ColorSpaceYcck))
        //{
        //    return true;
        //}

        return mWasCmykOrYcck;
    }

    //JAVA-deleted: Java doesn't have ColorMap, ImageAttributes etc, so we don't use it.
    /*
    /// <summary>
    /// Creates an array of native ColorMap objects from an array of DrColorMap objects.
    /// </summary>
    private static ColorMap[] createNativeColorMaps(DrColorMap[] colorMaps)
    {
        ColorMap[] nativeColorMaps = new ColorMap[colorMaps.length];
        for (int i = 0; i < colorMaps.length; ++i)
        {
            ColorMap nativeColorMap = new ColorMap();
            nativeColorMap.OldColor = colorMaps[i].getOldColor().toNativeColor();
            nativeColorMap.NewColor = colorMaps[i].getNewColor().toNativeColor();
            nativeColorMaps[i] = nativeColorMap;
        }
        return nativeColorMaps;
    }

    /// <summary>
    /// Sets color (chroma) key of the native image attributes.
    /// </summary>
    private static void setChromaKey(ImageAttributes nativeImageAttributes, ChromaKeyRegion chromaKeyRegion)
    {
        nativeImageAttributes.SetColorKey(chromaKeyRegion.getLowColor().toNativeColor(),
            chromaKeyRegion.getHighColor().toNativeColor());
    }

    /// <summary>
    /// Creates a native color matrix from DrColorMatrix object.
    /// </summary>
    private static ColorMatrix createNativeColorMatrix(DrColorMatrix colorMatrix)
    {
        ColorMatrix nativeColorMatrix = new ColorMatrix();

        for (int i = 0; i < DrColorMatrix.COLOR_MATRIX_SIZE; ++i)
            for (int j = 0; j < DrColorMatrix.COLOR_MATRIX_SIZE; ++j)
                nativeColorMatrix.set(i, j, colorMatrix.get(i, j));

        return nativeColorMatrix;
    }
    */

    private static boolean isPNGImageHasAlphaChannel(Stream imageStream) throws Exception
    {
        boolean retValue = false;
        BigEndianBinaryReader reader = new BigEndianBinaryReader(imageStream);

        final int SIGNATURE_SIZE = 8;
        imageStream.setPosition(SIGNATURE_SIZE);

        //Shift to the color type (Width (4 bytes) + Height (4 bytes) + Bit depth (1 byte))
        int colorTypeShift = (int)((4 + 4 + 1) & 0xFFFFFFFFL);
        boolean isExit = false;

        while (!isExit && (imageStream.getPosition() < imageStream.getLength()))
        {
            //Read chunk length (4 bytes)
            int chunkLength = reader.readUInt32();
            //Read chunk type (4 bytes)
            String chunkType = new String(reader.readByteChars(4));

            switch (gStringSwitchMap.of(chunkType))
            {
                case /*"IHDR"*/0:
                    imageStream.seek(colorTypeShift, SeekOrigin.CURRENT);
                    byte colorType = reader.readSByte();
                    if(colorType == 3)
                    {
                        //Each pixel is a palette index; a PLTE chunk shall appear.
                        //Seek back and looking for tRNS chunk
                        imageStream.seek(-colorTypeShift - 1, SeekOrigin.CURRENT);
                    }
                    else if(colorType == 4 || colorType == 6)
                    {
                        //4: Each pixel is a grayscale sample, followed by an alpha sample.
                        //6: Each pixel is an R,G,B triple, followed by an alpha sample.
                        retValue = true;
                        isExit = true;
                    }
                    break;
                case /*"IEND"*/1:
                    isExit = true;
                    break;
                case /*"tRNS"*/2:
                    //Specifies either alpha values that are associated with palette entries (for indexed-colour images)
                    isExit = true;
                    retValue = true;
                    break;
                default:
                    break;
            }

            // Calculate shifting (chunk length bytes and CRC (4 bytes))
            int shift = (int)((chunkLength & 0xFFFFFFFFL) + 4);
            if ((imageStream.getPosition() + (shift & 0xFFFFFFFFL) + 8 >= imageStream.getLength()))
                break;
            //Skip chunk length bytes and CRC
            imageStream.seek((shift), SeekOrigin.CURRENT);
        }

        return retValue;
    }

/* //JAVA-deleted: DrImageAttributes applied manually in java.
    private static ImageAttributes convertDrImageAttributesToNative(DrImageAttributes imageAttributes)
    {
        // Creates native image attributes.
        ImageAttributes nativeImageAttributes = new ImageAttributes();

        // Set threshold value.
        if (imageAttributes.isThresholdUsed())
            nativeImageAttributes.SetThreshold(imageAttributes.getThreshold());

        // Set color remap table.
        if (imageAttributes.getColorMaps().length > 0)
            nativeImageAttributes.SetRemapTable(createNativeColorMaps(imageAttributes.getColorMaps()));

        // Set color matrix.
        if (!imageAttributes.getColorMatrix().isIdentityMatrix())
            nativeImageAttributes.SetColorMatrix(createNativeColorMatrix(imageAttributes.getColorMatrix()));

        // Set chroma key.
        if (imageAttributes.getChromaKeyRegion() != null)
            setChromaKey(nativeImageAttributes, imageAttributes.getChromaKeyRegion());

        return nativeImageAttributes;
    }
*/

/* //JAVA-deleted: explicit file format used, like FileFormat.BMP in the SetBitmap() method.
    /// <summary>
    /// Converts from .NET ImageFormat into our ImageType.
    /// </summary>
    private static *//*FileFormat*//*int imageFormatToImageType(ImageFormat imageFormat)
    {
        if (imageFormat.equals(ImageFormat.Jpeg))
            return FileFormat.JPEG;
        else if (imageFormat.equals(ImageFormat.Png))
            return FileFormat.PNG;
        else if (imageFormat.equals(ImageFormat.Emf))
            return FileFormat.EMF;
        else if (imageFormat.equals(ImageFormat.Wmf))
            return FileFormat.WMF;
        else if (imageFormat.equals(ImageFormat.Bmp))
            return FileFormat.BMP;
        else if (imageFormat.equals(ImageFormat.Gif))
            return FileFormat.GIF;
        else if (imageFormat.equals(ImageFormat.Tiff))
            return FileFormat.TIFF;
        else
             return FileFormat.UNKNOWN;
    }
*/

    //JAVA-added:
    /// <summary>
    /// Converts this bitmap into a 1bpp indexed pixel format.
    /// </summary>
    public void convertTo1Bpp()
    {
        // This creates a 1bpp indexed image.
        setBitmap(ImageConverter.convertTo1Bpp(getBitmap(), getBounds()));
    }

    //JAVA-added:
    public void convertToIntArgb()
    {
        // This creates a 1bpp indexed image.
        setBitmap(ImageConverter.convertToIntArgb(getBitmap(), getBounds()));
    }

    //JAVA-added:
    public void convertToIntRgb()
    {
        // This creates a 1bpp indexed image.
        setBitmap(ImageConverter.convertToIntRgb(getBitmap(), getBounds()));
    }

    //JAVA-added:
    public void flip(int wrapMode)
    {
        setBitmap(ImageEffectApplier.flip(getNativeBitmap(), wrapMode));
    }

    public boolean hasPalette()
    {
        BufferedImage img = getNativeBitmap();
        return (img.getType() == BufferedImage.TYPE_BYTE_INDEXED) &&
                (img.getColorModel() instanceof IndexColorModel);
    }

    public BitmapDataPal lockBits()
    {
        return new BitmapDataPal(getBitmap());
    }

    private static boolean isRunningInTestMode()
    {
        return "true".equals(System.getProperty("isTesting"));
    }

    //JAVA-added
    private boolean mWasCmykOrYcck;
    private final static int gMaxMetafileWidth = 2048;

    private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
            (
                    "IHDR",
                    "IEND",
                    "tRNS"
            );
    private static final double LIMIT_MEMORY_RATIO = 0.7;
    //JAVA-deleted: no need in java
    /// <summary>
    /// Set to System.Drawing.Imaging.ColorPalette.Flags when the palette contains alpha information.
    /// </summary>
    // private static final int PALETTE_CONTAINS_ALPHA = 0x1;
    /// <summary>
    /// Set to System.Drawing.Imaging.ColorPalette.Flags when the palette contains grayscale values.
    /// </summary>
    // private static final int PALETTE_GRAYSCALE = 0x2;
    /// <summary>
    /// Set to System.Drawing.Imaging.ColorPalette.Flags when the palette contains halftone values.
    /// </summary>
    // private static final int PALETTE_HALFTONE = 0x4;

    // private static final int CMYK_PIXEL_FORMAT = 0x0000200f;
}

