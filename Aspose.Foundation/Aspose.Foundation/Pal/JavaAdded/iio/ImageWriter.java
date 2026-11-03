package com.aspose;

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.logging.Log;

import java.awt.image.BufferedImage;

public abstract class ImageWriter extends ChainBuilder.ChainLink<ImageWriter>
{
    private static boolean gDebug = false;

    public static void setDebug(boolean enabled) { gDebug = enabled;}

    /* Public methods */

    public final void writeGif(BufferedImage image, Stream stream) throws Exception
    {
        write(image, stream, FileFormat.GIF, null);
    }

    public final void writeBmp(BufferedImage image, Stream stream, ImageWriterBuilder.Options options) throws Exception
    {
        write(image, stream, FileFormat.BMP, options);
    }

    public final void writeJpeg(BufferedImage image, Stream stream, ImageWriterBuilder.Options options) throws Exception
    {
        write(image, stream, FileFormat.JPEG, options);
    }

    public final void writePng(BufferedImage image, Stream stream, ImageWriterBuilder.Options options) throws Exception
    {
        write(image, stream, FileFormat.PNG, options);
    }

    public final void writeTiff(final BufferedImage image, final Stream stream, final ImageWriterBuilder.Options options) throws Exception
    {
        write(image, stream, FileFormat.TIFF, options);
    }

    /* Protected methods. There methods have to be overriden. Which ones depends on a writer. Users mustn't use them directly. */

    protected void doWriteGif(BufferedImage image, Stream stream, ImageWriterBuilder.Options options) throws Exception { throw new UnsupportedOperationException("This writer cannot write GIF");}

    protected void doWritePng(BufferedImage image, Stream stream, ImageWriterBuilder.Options options) throws Exception { throw new UnsupportedOperationException("This writer cannot write PNG");}

    protected void doWriteJpeg(BufferedImage image, Stream stream, ImageWriterBuilder.Options options) throws Exception { throw new UnsupportedOperationException("This writer cannot write JPEG");}

    protected void doWriteBmp(BufferedImage image, Stream stream, ImageWriterBuilder.Options options) throws Exception { throw new UnsupportedOperationException("This writer cannot write BMP");}

    protected void doWriteTiff(BufferedImage image, Stream stream, ImageWriterBuilder.Options options) throws Exception { throw new UnsupportedOperationException("This writer cannot write TIFF");}

    /* workflow methods */

    private void write(BufferedImage image, Stream stream, int fileFormat, ImageWriterBuilder.Options options) throws Exception
    {
        if (options == null)
            options = new ImageWriterBuilder.Options();

        options.withOutFormat(fileFormat);

        long pos = stream.getPosition();
        try
        {
            switch (options.outFormat())
            {
                case FileFormat.TIFF:
                    doWriteTiff(image, stream, options);
                    break;

                case FileFormat.BMP:
                    doWriteBmp(image, stream, options);
                    break;

                case FileFormat.JPEG:
                    doWriteJpeg(image, stream, options);
                    break;

                case FileFormat.PNG:
                    doWritePng(image, stream, options);
                    break;

                case FileFormat.GIF:
                    doWriteGif(image, stream, options);
                    break;
            }

            if (gDebug)
                Log.info("Image was written by " + getClass().getName());

        }
        catch (Throwable e)
        {
            if (gDebug)
                Log.warn(e);

            stream.setPosition(pos);
            tryNext(image, stream, options, e);
        }
    }

    private void tryNext(BufferedImage image, Stream stream, ImageWriterBuilder.Options options, Throwable anyError) throws Exception
    {
        if (next == null)
        {
            ExternalImageLibTestCore.warnIfLibsNotFoundOnce();

            if (anyError != null)
                throw new Exception(anyError);

            return;
        }

        if (anyError != null)
            next.previousException = anyError;

        ImageWriter nextWriter = (ImageWriter) next;
        switch (options.outFormat())
        {
            case FileFormat.TIFF:
                nextWriter.writeTiff(image, stream, options);
                break;

            case FileFormat.BMP:
                nextWriter.writeBmp(image, stream, options);
                break;

            case FileFormat.JPEG:
                nextWriter.writeJpeg(image, stream, options);
                break;

            case FileFormat.PNG:
                nextWriter.writePng(image, stream, options);
                break;

            case FileFormat.GIF:
                nextWriter.writeGif(image, stream);
                break;
        }
    }
}
