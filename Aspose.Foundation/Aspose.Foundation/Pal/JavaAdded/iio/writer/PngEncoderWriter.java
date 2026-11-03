package com.aspose;

import com.aspose.ms.System.IO.Stream;
import com.pngencoder.PngEncoder;
import com.pngencoder.PngEncoderPhysicalPixelDimensions;

import java.awt.image.BufferedImage;

public class PngEncoderWriter extends ImageWriter
{
    @Override
    protected void doWritePng(BufferedImage image, Stream stream, ImageWriterBuilder.Options options) throws Exception
    {
        int imageType = image.getType();
        boolean tryIndexedEncoding = false;

        switch (imageType)
        {
            case BufferedImage.TYPE_INT_RGB:
            case BufferedImage.TYPE_3BYTE_BGR:
            case BufferedImage.TYPE_USHORT_565_RGB:
            case BufferedImage.TYPE_USHORT_555_RGB:
            case BufferedImage.TYPE_4BYTE_ABGR:
            case BufferedImage.TYPE_4BYTE_ABGR_PRE:
            case BufferedImage.TYPE_INT_ARGB:
            case BufferedImage.TYPE_INT_ARGB_PRE:
            case BufferedImage.TYPE_CUSTOM:
            case BufferedImage.TYPE_BYTE_GRAY:
            case BufferedImage.TYPE_USHORT_GRAY:
                break;

            case BufferedImage.TYPE_INT_BGR:
                image = ImageConverter.convertToIntRgb(image, options.bounds());
                break;

            case BufferedImage.TYPE_BYTE_BINARY:
            case BufferedImage.TYPE_BYTE_INDEXED:
                tryIndexedEncoding = true;
                break;

            default:
                throw new IllegalStateException("Unknown bitmap type: " + imageType);
        }

        int hRes = ImageUtil.dpiToPpm(options.horizontalResolution());
        int vRes = ImageUtil.dpiToPpm(options.verticalResolution());

        PngEncoderPhysicalPixelDimensions dimensions = PngEncoderPhysicalPixelDimensions.pixelsPerMeter(hRes, vRes);

        byte[] pngImage = new PngEncoder().withBufferedImage(image).
                withTryIndexedEncoding(tryIndexedEncoding).
                withPhysicalPixelDimensions(dimensions).
                withMultiThreadedCompressionEnabled(true).
                toBytes();

        stream.write(pngImage, 0, pngImage.length);
        options.withOutImage(image);
    }
}