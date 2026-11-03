package com.aspose;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Iterator;

/**
 * This class allows to reduce code duplication required when writing using Java classes.
 */
public class ImageWriterHelper
{
    private final BufferedImage mBitmapPal;

    public ImageWriterHelper(BufferedImage bitmapPal, String imageFormat, String preferredImageWriterClassName)
    {
        this.mBitmapPal = bitmapPal;

        mWriter = fetchImageWriter(imageFormat, preferredImageWriterClassName);
        mParam = mWriter.getDefaultWriteParam();
        mMetadata = mWriter.getDefaultImageMetadata(ImageTypeSpecifier.createFromRenderedImage(bitmapPal), mParam);
    }

    public ImageWriteParam getParam()
    {
        return mParam;
    }

    public IIOMetadata getMetadata()
    {
        return mMetadata;
    }

    public javax.imageio.ImageWriter getWriter()
    {
        return mWriter;
    }

    public void write(OutputStream stream) throws Exception
    {
        // The code is taken from here http://stackoverflow.com/questions/233504/write-dpi-metadata-to-a-jpeg-image-in-java

        // Set where writer will write to.
        ImageOutputStream ios = ImageIO.createImageOutputStream(stream);
        mWriter.setOutput(ios);

        IIOImage ioImage = new IIOImage(mBitmapPal, null, mMetadata);

        mWriter.write(null, ioImage, mParam);

        // This is required, otherwise not all data might be written to our stream.
        ios.flush();
		ios.close();

        // The Java documentation recommends disposing image readers and writers asap.
        mWriter.dispose();
    }

    /**
     * <p>Retrieve suitable writer. </p>
     */
    private static javax.imageio.ImageWriter fetchImageWriter(String imageFormat, String preferredWriterClassName)
    {
        Iterator writers = ImageIO.getImageWritersByFormatName(imageFormat);
        javax.imageio.ImageWriter writer = null;
        while (writers.hasNext())
        {
            javax.imageio.ImageWriter candidateWriter = (javax.imageio.ImageWriter) writers.next();

            if (!StringUtil.hasChars(preferredWriterClassName))
            {
                writer = candidateWriter;
                break;
            }

            if ((writer == null) || preferredWriterClassName.equals(candidateWriter.getOriginatingProvider().getPluginClassName()))
                writer = candidateWriter;
        }

        if (writer == null)
            throw new IllegalStateException("Cannot find an ImageIO writer for the specified format: " + imageFormat + ".");

        return writer;
    }

    private javax.imageio.ImageWriter mWriter;
    private ImageWriteParam mParam;
    private IIOMetadata mMetadata;
}
