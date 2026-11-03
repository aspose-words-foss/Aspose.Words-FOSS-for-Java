// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/10/2016 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ImageUtil;
import java.io.EOFException;
import com.aspose.CantCreateBitmapException;


/// <summary>
/// Utility functions to work with images in HTML import/export.
/// </summary>
/*static*/ class HtmlImageUtil
{
	/* Simulation of static class by using private constructor */
	private HtmlImageUtil()
	{}

    /// <summary>
    /// Checks an image on possibility of inserting to the document model.
    /// </summary>
    static boolean isInsertableImage(MemoryStream imageStream) throws Exception
    {

        if (imageStream == null)
            return false;

        if (isJpeg(imageStream))
            return true;

        if (isPng(imageStream))
            return true;

        if (isGif(imageStream))
            return true;

        if (isSvg(imageStream))
            return true;

        try
        {
            byte[] imageData = imageStream.toArray();
            return isSupportedImage(imageData) || ImageUtil.isPictFile(imageData);
        }
        catch (EOFException e)
        {
            // Format detection failed. This image format is not supported.
            return false;
        }
    }

    /// <summary>
    /// Indicates whether the image is visible in IE when attached to an MHTML file.
    /// </summary>
    static boolean isSupportedAttachedImageFormat(MemoryStream imageStream) throws Exception
    {
        if (imageStream == null)
        {
            return false;
        }

        return isJpeg(imageStream) ||
            isPng(imageStream) ||
            isGif(imageStream) ||
            isIco(imageStream) ||
            isBmp(imageStream);
    }

    /// <summary>
    /// Checks whether image format is supported by MSW as is or after conversion.
    /// </summary>
    static boolean isSupportedImage(byte[] imageBytes) throws Exception
    {
        return getSupportedImageBytes(imageBytes) != null;
    }

    /// <summary>
    /// Makes sure the image is fully supported by our document model.
    /// </summary>
    /// <remarks>
    ///   <para>
    ///   Some image formats are not fully supported by our document model. For example: GIF, TIFF, BMP (see WORDSNET-1878).
    ///   </para>
    ///   <para>
    ///   If the format of the original image is fully supported, the source image bytes are returned as is.
    ///   Otherwise, the image is converted to PNG and the method returns PNG image bytes.
    ///   </para>
    /// </remarks>
    static byte[] getSupportedImageBytes(byte[] imageBytes) throws Exception
    {
        try
        {
            return ImageDataCore.getImageBytes(imageBytes);
        }
        catch (CantCreateBitmapException e)
        {
            return null;
        }
    }

    /// <summary>
    /// Checks an image on possibility of inserting to the document model.
    /// </summary>
    static boolean isInsertableImage(byte[] imageBytes) throws Exception
    {
        if ((imageBytes == null) || (imageBytes.length == 0))
            return false;

        MemoryStream ms = new MemoryStream(imageBytes);
        try /*JAVA: was using*/
        {
            return isInsertableImage(ms);
        }
        finally { if (ms != null) ms.close(); }
    }

    /// <summary>
    /// Returns byte array with the radiobutton image in PNG format.
    /// </summary>
    static byte[] createRadioButtonImage(Document doc, boolean selected)
    {
        throw new UnsupportedOperationException("FOSS");
    }

    private static boolean isGif(MemoryStream imageStream) throws Exception
    {
        try
        {
            imageStream.setPosition(0);
            if (ImageUtil.isGif(imageStream))
                return true;
        }
        catch (EOFException e)
        {
            // Format detection failed. This is not a GIF image.
        }

        return false;
    }

    private static boolean isPng(MemoryStream imageStream) throws Exception
    {
        try
        {
            imageStream.setPosition(0);
            if (ImageUtil.isPng(imageStream))
                return true;
        }
        catch (EOFException e)
        {
            // Format detection failed. This is not a PNG image.
        }

        return false;
    }

    private static boolean isJpeg(MemoryStream imageStream) throws Exception
    {
        try
        {
            imageStream.setPosition(0);
            if (ImageUtil.isJpeg(imageStream))
                return true;
        }
        catch (EOFException e)
        {
            // Format detection failed. This is not a JPEG image.
        }

        return false;
    }

    private static boolean isBmp(MemoryStream imageStream) throws Exception
    {
        try
        {
            imageStream.setPosition(0);
            return ImageUtil.isBmp(imageStream);
        }
        catch (EOFException e)
        {
            // Format detection failed. This is not a BMP image.
            return false;
        }
    }

    private static boolean isIco(MemoryStream imageStream) throws Exception
    {
        try
        {
            imageStream.setPosition(0);
            return ImageUtil.isIco(imageStream);
        }
        catch (EOFException e)
        {
            // Format detection failed. This is not an ICO image.
            return false;
        }
    }

    private static boolean isSvg(MemoryStream imageStream) throws Exception
    {
        try
        {
            imageStream.setPosition(0);
            return ImageUtil.isSvg(imageStream);
        }
        catch (EOFException e)
        {
            // Format detection failed. This is not a SVG image.
            return false;
        }
    }
}

