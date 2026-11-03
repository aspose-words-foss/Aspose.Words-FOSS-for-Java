package com.aspose;

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.IO.NetOutputStream;

import java.awt.image.BufferedImage;

public class SimplifiedJaiTiffWriter extends ImageWriter
{

    @Override
    protected void doWriteTiff(BufferedImage image, Stream stream, ImageWriterBuilder.Options options) throws Exception
    {
        BaseTiffWriterPal tiffWriter = new SimpleJaiTiffWriterPal();
        try /*JAVA: was using*/
        {
            //JAVA-changed: getBitmap() (BufferedImage in java) can't store resolution.
            BitmapPal bitmap = new BitmapPal(image.getWidth(), image.getHeight(), (float) options.horizontalResolution(), (float) options.verticalResolution());
            bitmap.setNativeImage(image);

            tiffWriter.saveFirstFrame(bitmap, new NetOutputStream(stream), options.tiffCompression(), options.binarizationMethod(), options.getThreshold(), false);
        }
        finally
        {
            if (tiffWriter != null) tiffWriter.close();
        }
    }

}
