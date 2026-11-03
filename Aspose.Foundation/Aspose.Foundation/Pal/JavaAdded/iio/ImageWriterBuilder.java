package com.aspose;

import com.aspose.ms.System.Drawing.Rectangle;
import com.aspose.ms.java.debug.JavaDebug;

import java.awt.image.BufferedImage;

/**
 * This class creates ImageWriter which can fallback to the next writer in the chain if the current writer cannot perform the writing.
 * Not thread-safe.
 */
public class ImageWriterBuilder extends ChainBuilder<ImageWriter>
{
    public ImageWriterBuilder()
    {
        usePngEncoder = ExternalImageLibTestCore.hasPngEncoder();
    }

    public ImageWriterBuilder withSimplifiedTiff()
    {
        items.add(new SimplifiedJaiTiffWriter());
        return this;
    }

    public ImageWriterBuilder withPngEncoder()
    {
        if (usePngEncoder)
            items.add(new PngEncoderWriter());

        return this;
    }

    public ImageWriterBuilder withImageIo()
    {
        items.add(new ImageIoWriter());
        return this;
    }

    public ImageWriter build()
    {
        if (items.isEmpty())
        {
            if (JavaDebug.ON)
                System.out.println("There are no available writers. ImageIO is used by default");

            items.add(new ImageIoWriter());
            items.add(new PngEncoderWriter());
        }

        return (ImageWriter) buildFromChainResetItems();
    }

    public static class Options
    {
        private Integer outFormat;
        private byte threshold = (byte) 128;
        private Integer tiffCompression = TiffCompressionCore.NONE;
        private Integer binarizationMethod = ImageBinarizationMethodCore.THRESHOLD;
        private Integer jpegQuality = 80;
        private double horizontalResolution = ImageConstants.STANDARD_RESOLUTION;
        private double verticalResolution = ImageConstants.STANDARD_RESOLUTION;
        private Rectangle bounds;
        private BufferedImage outImage;
        private boolean isCmyk;

        public Options withThreshold(byte threshold)
        {
            this.threshold = threshold;
            return this;
        }

        public Byte getThreshold()
        {
            return threshold;
        }

        public Options withBinarizationMethod(Integer binarizationMethod)
        {
            this.binarizationMethod = binarizationMethod;
            return this;
        }

        public Integer binarizationMethod()
        {
            return binarizationMethod;
        }

        public Options withJpegQuality(Integer jpegQuality)
        {
            this.jpegQuality = jpegQuality;
            return this;
        }

        public Integer jpegQuality()
        {
            return jpegQuality;
        }

        public Options withBounds(Rectangle bounds)
        {
            this.bounds = bounds;
            return this;
        }

        public Rectangle bounds()
        {
            return bounds;
        }

        public Options withOutFormat(Integer outFormat)
        {
            this.outFormat = outFormat;
            return this;
        }

        public Integer outFormat()
        {
            return outFormat;
        }

        public Options withTiffCompression(Integer tiffCompression)
        {
            this.tiffCompression = tiffCompression;
            return this;
        }

        public Integer tiffCompression()
        {
            return tiffCompression;
        }

        public boolean isCmyk()
        {
            return isCmyk;
        }

        public Options withIsCmyk(boolean isCmyk)
        {
            this.isCmyk = isCmyk;
            return this;
        }

        public Options withHorizontalResolution(double res)
        {
            this.horizontalResolution = res;
            return this;
        }

        public Options withVerticalResolution(double res)
        {
            this.verticalResolution = res;
            return this;
        }

        public double horizontalResolution()
        {
            return horizontalResolution;
        }

        public double verticalResolution()
        {
            return verticalResolution;
        }

        public Options withOutImage(BufferedImage outImage)
        {
            this.outImage = outImage;
            return this;
        }

        public BufferedImage outImage()
        {
            return outImage;
        }
    }

    private final boolean usePngEncoder;
}
