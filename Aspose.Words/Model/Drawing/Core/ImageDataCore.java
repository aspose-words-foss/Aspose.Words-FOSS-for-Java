// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;
import com.aspose.ImageUtil;
import com.aspose.ms.System.IO.MemoryStream;
import java.awt.image.BufferedImage;
import com.aspose.BitmapPal;
import com.aspose.StreamUtil;
import com.aspose.ArgumentUtil;
import com.aspose.ArrayUtil;
import com.aspose.ms.System.IO.File;
import com.aspose.CompressedData;
import com.aspose.StringUtil;
import com.aspose.FileFormat;
import com.aspose.FileFormatCore;
import com.aspose.UriUtil;
import com.aspose.ms.System.IO.Directory;
import com.aspose.ms.System.IO.Path;


/// <summary>
/// Contains common logic for ImageData classes.
/// </summary>
class ImageDataCore
{
    /// <summary>
    /// Ctor.
    /// Document is needed to resolve relative image URIs in <see cref="ToStream" />, <see cref="ToByteArray" />
    /// and <see cref="SetImage(string)" />.
    /// Document might be null if in glossary, let's think what to do with them.
    /// </summary>
    ImageDataCore(Document document,
        IImageDataSource imageDataSource)
    {
        mDocument = document;
        mImageDataSource = imageDataSource;
    }

    /// <summary>
    /// Extract bmp/png raw bytes with the given index from .ico file.
    /// See https://en.wikipedia.org/wiki/ICO_(file_format)
    /// </summary>
    public static byte[] getIcoImageBytes(Stream stream, int imageIndex) throws Exception
    {
        BinaryReader reader = new BinaryReader(stream);

        int reserved = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue(reserved == 0);
        int type = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue(type == 1);
        int nrEntries = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue(nrEntries > 0);

        if (imageIndex < 0 || imageIndex >= nrEntries)
        {
            throw new IllegalArgumentException("Couldn't extract image with index " + imageIndex +
                                        " from .ico file, because it contains only " + nrEntries + " image(s).");
        }

        for (int i = 0; i < nrEntries; ++i)
        {
            int width = (reader.readByte() & 0xFF);
            // Specifies image width in pixels. Can be any number between 0 and 255. Value 0 means image width is 256 pixels.
            width = (width == 0 ? 256 : width);
            // Specifies image height in pixels. Can be any number between 0 and 255. Value 0 means image height is 256 pixels.
            int height = (reader.readByte() & 0xFF);
            height = (height == 0 ? 256 : height);

            // Skip unused nrColorPalette.
            reader.readByte();
            int reservedByte = (reader.readByte() & 0xFF);
            Debug.assertTrue(reservedByte == 0);
            int nrColorPanes = (reader.readUInt16() & 0xFFFF);
            Debug.assertTrue(nrColorPanes == 0 || nrColorPanes == 1);
            // Skip unused bitsPerPixel.
            reader.readUInt16();
            int sizeInBytes = (int)(reader.readUInt32() & 0xFFFFFFFFL);
            long offset = (reader.readUInt32() & 0xFFFFFFFFL);
            if (i == imageIndex)
            {
                byte[] imageBytes = new byte[sizeInBytes];
                long pos = stream.getPosition();
                stream.setPosition(offset);
                reader.read(imageBytes, 0, sizeInBytes);
                if (nrEntries > 1 && pos != offset)
                    stream.setPosition(offset);
                else
                    stream.setPosition(pos);

                // WORDSNET-18086 Added support for .ICO files containing PNG images.
                return ImageUtil.isPng(imageBytes)
                    ? imageBytes
                    : ImageUtil.prependBmpHeader(reader, sizeInBytes, width, height);
            }
        }

        throw new IllegalStateException("Couldn't extract BMP/PNG image from .ico file.");
    }

    /// <summary>
    /// Extract bmp/png raw bytes with the given index from .ico file.
    /// </summary>
    /// <remarks>
    /// See https://en.wikipedia.org/wiki/ICO_(file_format)
    /// </remarks>
    static byte[] getIcoImageBytes(byte[] imageBytes, int imageIndex) throws Exception
    {
        MemoryStream stream = new MemoryStream(imageBytes);
        try /*JAVA: was using*/
    	{
            return getIcoImageBytes(stream, imageIndex);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Returns number of images stored in the ICO file.
    /// </summary>
    /// <remarks>
    /// See https://en.wikipedia.org/wiki/ICO_(file_format)
    /// </remarks>
    static int getIcoImagesCount(byte[] icoBytes) throws Exception
    {
        Debug.assertTrue(ImageUtil.isIco(icoBytes));

        // The number of images in the file is stored in two little-endian bytes started from offset 4.
        final int OFFEST = 4;
        return (icoBytes[OFFEST] & 0xFF) + ((icoBytes[OFFEST + 1] & 0xFF) << 8);
    }

    /// <summary>
    /// Sets the image that the shape displays.
    /// </summary>
    /// <param name="image">The image object.</param>
    void setImage(BufferedImage image) throws Exception
    {
        if (image == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "image");

        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            BitmapPal.saveNativeImageForWord97(image, stream);
            setImage(stream);
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Sets the image that the shape displays.
    /// </summary>
    /// <param name="stream">The stream that contains the image.
    /// <java> The stream will be read from the current position, so one should be careful about stream position.</java>
    /// </param>
    /// <javaName>void setImage(java.io.InputStream stream)</javaName>
    void setImage(Stream stream) throws Exception
    {
        if (stream == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "stream");

        setImageBytes(StreamUtil.copyStreamToByteArray(stream));
    }

    /// <summary>
    /// Sets the image that the shape displays.
    /// </summary>
    /// <param name="fileName">The image file. Can be a file name or a URL.</param>
    void setImage(String fileName) throws Exception
    {
        ArgumentUtil.checkHasChars(fileName, "fileName");

        setImageBytes(ImageDataUtil.loadImageBytes(fileName, mDocument));
    }

    /// <summary>
    /// Gets the image stored in the shape as a <ms>.NET Image</ms><java>java <tt>BufferedImage</tt></java><cpp>Image</cpp> object.
    /// </summary>
    BufferedImage toImage() throws Exception
    {
        // We don't close the stream here because .NET requires the stream to remain open while the Image exists.
        return BitmapPal.loadNativeImage(toStream());
    }

    /// <summary>
    /// For description of this method see comments in <see cref="ImageData"/>.
    /// </summary>
    Stream toStream() throws Exception
    {
        byte[] imageBytes = getImageBytes();
        if (ArrayUtil.hasData(imageBytes))
            return new MemoryStream(imageBytes);

        Stream imageStream;
        try
        {
            // Added condition to check for null for C++ porting. Do not use LoadImageBytesNoImageFallback here to avoid creating and disposing NoImage stream.
            imageBytes = loadImageBytes();
            imageStream = (imageBytes != null) ? new MemoryStream(imageBytes) : ImageUtil.getNoImageStream();
        }
        catch(Exception e)
        {
            imageStream = ImageUtil.getNoImageStream();
        }

        return imageStream;
    }

    /// <summary>
    /// Returns image bytes for any image regardless whether the image is stored or linked.
    /// </summary>
    /// <remarks>
    /// <para>If the image is linked, downloads the image every time it is called.</para>
    ///
    /// <seealso cref="ImageBytes"/>
    /// </remarks>
    byte[] toByteArray() throws Exception
    {
        byte[] imageBytes = getImageBytes();
        return ArrayUtil.hasData(imageBytes) ? imageBytes : loadImageBytesNoImageFallback();
    }

    /// <overloads>Saves the image of the shape.</overloads>
    /// <summary>
    /// Saves the image into the specified stream.
    /// </summary>
    /// <param name="stream">The stream where to save the image to.</param>
    /// <remarks>
    /// <p>Is it the responsibility of the caller to dispose the stream object.</p>
    /// </remarks>
    /// <javaName>void save(java.io.OutputStream stream)</javaName>
    void save(Stream stream) throws Exception
    {
        if (stream == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "stream");

        Stream srcStream = toStream();
        try /*JAVA: was using*/
    	{
            StreamUtil.copyStream(srcStream, stream);
    	}
        finally { if (srcStream != null) srcStream.close(); }
    }

    /// <summary>
    /// Saves the image into a file.
    /// </summary>
    /// <param name="fileName">The file name where to save the image.</param>
    void save(String fileName) throws Exception
    {
        ArgumentUtil.checkHasChars(fileName, "fileName");

        Stream stream = File.create(fileName);
        try /*JAVA: was using*/
    	{
            save(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Call this during loading of a document instead SetImage because not showing
    /// an image is better than throwing exception that stops loading the document.
    ///
    /// Sets the image that the shape displays, but silences exceptions
    /// if the image data is invalid.
    /// </summary>
    /// <param name="imageBytes">Bytes of the image.</param>
    /// <returns> True, if setting imageBytes was successful, otherwise - False.</returns>
    boolean setImageSafe(byte[] imageBytes) throws Exception
    {
        boolean result;
        try
        {
            if (gExtractImagesToFolder != null)
                saveImageToFile(imageBytes);
            setImageBytes(imageBytes);
            result = true;
        }
        catch(Exception e)
        {
            // Silence all exceptions because this is what we want to do.
            result = false;
        }
        return result;
    }

    /// <summary>
    /// Loads image bytes referenced by SourceFullName.
    /// Returns null if any problem occurs.
    /// </summary>
    byte[] loadImageBytes() throws Exception
    {
        return ImageDataUtil.loadImageBytes(mImageDataSource.getSourceFullName(), mDocument);
    }

    /// <summary>
    /// If file format supported, returns the original image bytes, otherwise converts to PNG.
    /// </summary>
    static byte[] getImageBytes(byte[] imageBytes) throws Exception
    {
        if (imageBytes == null)
            return null;

        imageBytes = CompressedData.getData(imageBytes);
        if (isFormatSupported(imageBytes))
        {
            // When image type is natively supported by MS Word, just store the bytes.

            // We do not let metafiles without a placeable header to lurk in our model.
            if (ImageUtil.isStandardMetafile(imageBytes))
                imageBytes = ImageUtil.prependWmfPlaceableHeader(imageBytes, ImageUtil.getMetafileSize(imageBytes));
        }
        else
        {
            // When image type is not natively supported by MS Word,
            // we load into a native image using the codecs provided by the platform
            // (we are hoping the codecs can load gif and tiff and bmp, which is usually the case)
            // and convert to PNG and then store the bytes in the model.
            imageBytes = ImageUtil.convertToPng(imageBytes);
        }

        return imageBytes;
    }

    /// <summary>
    /// If this set by TestUtil all source images from a document should be extracted to the given folder
    /// (before any conversions) .
    /// </summary>
    static void setExtractOriginalImages(String imageFolderName)
    {
        gExtractImagesToFolder = imageFolderName;
    }

    /// <summary>
    /// Gets or sets the path and name of the source file for the linked image.
    /// </summary>
    /// <remarks>
    /// <p>The default value is an empty string.</p>
    /// <p>If <see cref="SourceFullName"/> is not an empty string, the image is linked.</p>
    /// </remarks>
    String getSourceFullName() throws Exception { return mImageDataSource.getSourceFullName(); }
    void setSourceFullName(String value) throws Exception { mImageDataSource.setSourceFullName(value); }

    /// <summary>
    /// Gets or sets the raw bytes of the image stored in the shape.
    /// Setting null or empty array will remove the image from the shape.
    /// </summary>
    byte[] getImageBytes() throws Exception { return mImageDataSource.getImageBytes(); }
    void setImageBytes(byte[] value) throws Exception
    {
        mImageSizeCache = null;

        if (!ArrayUtil.hasData(value))
        {
            mImageDataSource.setImageBytes(null);
            return;
        }

        if (ImageUtil.isIco(value)) // WORDSJAVA-1333 In case of ICO file, analyze ico header and extract internal bmp/png image
            mImageDataSource.setImageBytes(getIcoImageBytes(value, 0));
        else
            mImageDataSource.setImageBytes(getImageBytes(value));
    }

    /// <summary>
    /// Returns true if the shape has image bytes or links an image.
    /// </summary>
    boolean hasImage() throws Exception { return (hasImageBytes() || isLink()); }

    /// <summary>
    /// Gets the information about image size and resolution.
    /// </summary>
    /// <remarks>
    /// <p>If the image is linked only and not stored in the document, returns zero size.</p>
    /// </remarks>
    ImageSize getImageSize() throws Exception
    {
        if (mImageSizeCache == null)
        {
            mImageSizeCache = hasImageBytes() ?
                new ImageSize(ImageUtil.getImageSize(getImageBytes())) :
                new ImageSize(0, 0);
        }

        return mImageSizeCache;
    }

    /// <summary>
    /// Gets the type of the image.
    /// </summary>
    /*ImageType*/int getImageType() throws Exception { return FileFormatUtil.toImageType(getFileFormat()); }

    /// <summary>
    /// Returns true if the image is linked to the shape (when <see cref="SourceFullName"/> is specified).
    /// </summary>
    boolean isLink() throws Exception { return StringUtil.hasChars(mImageDataSource.getSourceFullName()); }

    /// <summary>
    /// Returns true if the image is linked and not stored in the document.
    /// </summary>
    boolean isLinkOnly() throws Exception { return (isLink() && !hasImageBytes()); }

    boolean hasImageBytes() throws Exception { return mImageDataSource.hasImageBytes(); }

    /// <summary>
    /// Gets the type of the image.
    /// </summary>
    /*FileFormat*/int getFileFormat() throws Exception
    {
        if (hasImageBytes())
            return ImageUtil.getImageType(getImageBytes());
        else if (isLink())
            return FileFormatCore.fromExt(UriUtil.getExtension(null, mImageDataSource.getSourceFullName()));
        else
            return FileFormat.UNKNOWN;
    }

    /// <summary>
    /// Saves imageBytes into a file for debug purposes.
    /// </summary>
    private void saveImageToFile(byte[] imageBytes) throws Exception
    {
        try
        {
            if (!Directory.exists(gExtractImagesToFolder))
                Directory.createDirectory(gExtractImagesToFolder);

            String path = gExtractImagesToFolder + Path.DirectorySeparatorChar +
                          Path.getFileName(mDocument.getOriginalFileName()) + "." +
                          getNextImageIndex() + "." + FileFormatCore.toExt(ImageUtil.getImageType(imageBytes));

            Stream stream = File.create(path);
            try /*JAVA: was using*/
            {
                stream.write(imageBytes, 0, imageBytes.length);
                stream.flush();
            }
            finally { if (stream != null) stream.close(); }
        }
        catch (Exception e)
        {
            Debug.writeLine(e.getMessage());
        }
    }

    private static int getNextImageIndex()
    {
        return ++gSaveImageIdx;
    }

    /// <summary>
    /// Loads image bytes referenced by SourceFullName.
    /// Makes a fallback to "no image" if any problem occurs.
    /// </summary>
    private byte[] loadImageBytesNoImageFallback() throws Exception
    {
        byte[] imageBytes = loadImageBytes();
        return (imageBytes != null) ? imageBytes : ImageUtil.getNoImageBytes();
    }

    /// <summary>
    /// Returns true if given image is natively supported by MS Word.
    /// </summary>
    private static boolean isFormatSupported(byte[] imageData) throws Exception
    {
        return isFormatSupported(ImageUtil.getImageType(imageData));
    }

    /// <summary>
    /// <para>Returns <c>true</c> if image of given type is stored 'as is'.</para>
    /// <para>Compare the method with see RW.MarkupLanguage.Writer.ImageResourceWriter.IsFormatSupported -
    /// they handle file types differently.</para>
    /// </summary>
    private static boolean isFormatSupported(/*FileFormat*/int imageType)
    {
        switch (imageType)
        {
            case FileFormat.WMF:
            case FileFormat.EMF:
            case FileFormat.JPEG:
            case FileFormat.PNG:
            case FileFormat.PICT:
            case FileFormat.ICO:
                return true;
            // These image types are not natively supported by MS Word, therefore we load into a native image and
            // convert to PNG and then store the bytes in the model.
            case FileFormat.GIF:
            case FileFormat.TIFF:
            case FileFormat.WEB_P:
            // WORDSNET-3702 RK We no longer store BMP (DIB) in MS Word document directly because
            // MS Word fails on some DIB formats (32 bit color for example). This code converts it to PNG.
            case FileFormat.BMP:
            // SVG is not directly supported in MS Word document, so return false here.
            // SVG image will be converted to format supported by MS Word document.
            case FileFormat.SVG:
            // If there is something we cannot recognize still lets try loading via a native image object.
            default:
                return false;
        }
    }

    /// <summary>
    /// Document providing context for any local relative URIs.
    /// </summary>
    private /*final*/ Document mDocument;

    private /*final*/ IImageDataSource mImageDataSource;

    /// <summary>
    /// Image size.
    /// </summary>
    private ImageSize mImageSizeCache;

    // Used to extract and save original images to files for debug purposes.
    private static String gExtractImagesToFolder;
    private static int gSaveImageIdx;
}

