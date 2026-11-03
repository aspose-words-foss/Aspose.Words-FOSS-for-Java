package com.aspose;

import com.aspose.ms.System.IO.MemoryStream;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is looking for supported external image processing libraries on classpath.
 * If it cannot find supported libraries it prints out the information how they can be installed.
 * To make sure that read and write works the same way as inside Aspose.Words for Java the BitmapPal class is used for read()/write() methods.
 */
public class ExternalImageLibTestCore
{
    public static void checkLibraries()
    {
        warnIfNotAvailable();
    }

    public static BufferedImage read(byte[] imageBytes) throws Exception
    {
        if (imageBytes == null || imageBytes.length == 0)
            throw new IllegalArgumentException("imageBytes cannot be empty");

        try
        {
            ImageReader.setDebug(true);
            BitmapPal bitmapPal = new BitmapPal(new MemoryStream(imageBytes));
            return bitmapPal.getNativeBitmap();
        }
        finally
        {
            ImageReader.setDebug(false);
        }
    }

    public static byte[] write(BufferedImage image, int fileFormat) throws Exception
    {
        if (image == null)
            throw new IllegalArgumentException("image cannot be null");

        if (fileFormat != FileFormat.TIFF &&
                fileFormat != FileFormat.PNG &&
                fileFormat != FileFormat.JPEG &&
                fileFormat != FileFormat.BMP &&
                fileFormat != FileFormat.GIF)
            throw new IllegalArgumentException("Supported formats: PNG, JPEG, BMP, GIF, TIFF");

        try
        {
            ImageWriter.setDebug(true);
            MemoryStream stream = new MemoryStream();
            BitmapPal bitmapPal = new BitmapPal(image);
            bitmapPal.save(stream, fileFormat);
            return stream.toArray();
        }
        finally
        {
            ImageWriter.setDebug(false);
        }
    }

    public static void warnIfLibsNotFoundOnce()
    {
        if (gWarningPrinted)
            return;

        warnIfNotAvailable();
        gWarningPrinted = true;
    }

    private static void warnIfNotAvailable()
    {
        if (hasPngEncoder())
            return;

        List<String> warnings = new ArrayList<String>(1);
        warnings.add(PNG_ENCODER_INSTALLATION_INFO);

        printWarnings(warnings);
    }

    static boolean hasPngEncoder()
    {
        return hasPngEncoder;
    }

    private static void printWarnings(List<String> warnings)
    {
        if (warnings == null || warnings.isEmpty())
            return;

        StringBuilder info = new StringBuilder();
        info.append(EXTERNAL_LIBS_HEADER);

        for (String warning : warnings)
            info.append(warning);

        System.out.println(info);//todo: change it to the new Log.warning()
    }

    private static boolean hasClass(String className)
    {
        try
        {
            Class.forName(className);
            return true;
        }
        catch (Throwable cnf)
        {
            //   cnf.printStackTrace();
            return false;
        }
    }

    /**
     * The method is used for test purposes only
     * */
    static void setHasPngEncoder(boolean val)
    {
        hasPngEncoder = val;
    }

    private static final String EXTERNAL_LIBS_HEADER = "Aspose.Words for Java is limited with JDK's built-in image encoders/decoders.\n" +
            "In order to improve reading/writing quality use the following external graphics libraries:\n";

    private static final String PNG_ENCODER_INSTALLATION_INFO = "   * Java PngEncoder - worlds fastest java png encoder\n" +
            "       - Download and add it to application classpath https://github.com/pngencoder/pngencoder ";

    private static boolean hasPngEncoder;
    private static volatile boolean gWarningPrinted = false;

    static
    {
        hasPngEncoder = hasClass("com.pngencoder.PngEncoder");
    }
}
