package com.aspose;

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.logging.Log;
import com.aspose.words.NativeLibSettings;

import java.awt.image.BufferedImage;

/**
 * This class creates ImageReader which can fallback to the next reader in the chain if the current reader cannot perform the reading.
 * Not thread-safe.
 */
public abstract class ImageReader extends ChainBuilder.ChainLink<ImageReader>
{
    private static boolean gDebug = JavaDebug.ON;

    public static void setDebug(boolean enabled) { gDebug = enabled;}

    public BufferedImage read(Stream stream) throws Exception
    {
        return read(stream, null);
    }

    public BufferedImage read(Stream stream, ImageReaderBuilder.Options options) throws Exception
    {
        if (stream == null || stream.getLength() == 0)
            return null;

        long position = stream.getPosition();

        if (options == null)
            options = new ImageReaderBuilder.Options();

        BufferedImage result;
        try
        {
            result = doRead(stream, options);
        }
        catch (Throwable anyError)
        {
            if (gDebug)
                Log.info("Current reader " + getClass().getName() + " throws " + anyError + " try next=" + next);

            stream.setPosition(position);
            return tryNext(stream, options, anyError);
        }


        if (result == null)
        {
            stream.setPosition(position);
            return tryNext(stream, options);
        }

        if (gDebug)
            Log.info("Image was read by " + getClass().getName());

        return result;
    }

    protected abstract BufferedImage doRead(Stream stream, ImageReaderBuilder.Options options) throws Exception;

    private BufferedImage tryNext(Stream stream, ImageReaderBuilder.Options options) throws Exception
    {
        return tryNext(stream, options, null);
    }

    private BufferedImage tryNext(Stream stream, ImageReaderBuilder.Options options, Throwable anyError) throws Exception
    {
        if (next == null)
        {
            if(NativeLibSettings.getInterruptThreadIfImageExceptionThrown())
                throw new Exception();

            ExternalImageLibTestCore.warnIfLibsNotFoundOnce();

            if (anyError != null)
                throw new Exception(anyError);

            return null;
        }

        if (anyError != null)
            next.previousException = anyError;

        return ((ImageReader) next).read(stream, options);
    }
}
