package com.aspose.words.utils;

import com.aspose.ExternalImageLibTestCore;
import com.aspose.FileFormat;
import com.aspose.words.SaveFormat;

import java.awt.image.BufferedImage;

//JAVA-added to check external image libraries.

/// <summary>
/// Allows to test if Aspose.Words for Java can find on its classpath and use external graphics libraries such as PngEncoder.
/// </summary>
public class ExternalImageLibTest
{
    /// <summary>
    /// Prints detailed message how to install supported external libraries in case if the are not found on classpath.
    /// </summary>
    public static void checkLibraries()
    {
        ExternalImageLibTestCore.checkLibraries();
    }

    /// <summary>
    /// Reads the image from the given byte array if can.
    /// Prints warnings if any supported 3rd party image processing libraries are not found on classpath.
    /// <param name="imageBytes">image bytes.</param>
    /// </summary>
    public static BufferedImage read(byte[] imageBytes) throws Exception
    {
        return ExternalImageLibTestCore.read(imageBytes);
    }

    /// <summary>
    /// Converts the given image into a byte array if can.
    /// Prints warnings if any supported 3rd party image processing libraries are not found on classpath.
    /// <param name="imageBytes">the image needs to be saved.</param>
    /// <param name="saveFormat">SaveFormat.PNG, SaveFormat.JPEG, SaveFormat.BMP, SaveFormat.GIF or SaveFormat.TIFF.</param>
    /// </summary>
    public static byte[] write(BufferedImage image, int saveFormat) throws Exception
    {
        int fileFormat = -1;

        if (saveFormat == SaveFormat.PNG)
            fileFormat = FileFormat.PNG;

        else if (saveFormat == SaveFormat.JPEG)
            fileFormat = FileFormat.JPEG;

        else if (saveFormat == SaveFormat.BMP)
            fileFormat = FileFormat.BMP;

        else if (saveFormat == SaveFormat.GIF)
            fileFormat = FileFormat.GIF;

        else if (saveFormat == SaveFormat.TIFF)
            fileFormat = FileFormat.TIFF;

        return ExternalImageLibTestCore.write(image, fileFormat);
    }
}