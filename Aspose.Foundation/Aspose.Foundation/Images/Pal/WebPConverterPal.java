// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/04/2024 by Anatoly Sidorenko

package com.aspose;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

import com.aspose.ms.System.IO.Stream;
import com.twelvemonkeys.imageio.plugins.webp.WebPImageReaderSpi;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;

/**
 * This class is used to read and write WebP with twelvemonkeys.imageio.webp
 */
class WebPConverterPal
{
	/* Simulation of static class by using private constructor */
	private WebPConverterPal()
	{}

    static BufferedImage readBySkia(Stream stream) throws IOException
    {
        // read by twelvemonkeys.imageio.webp
        for (Iterator<javax.imageio.ImageReader> it = ImageIO.getImageReadersByFormatName("wbp"); it.hasNext(); )
        {
            ImageReader reader = it.next();
            // Configure the input on the ImageReader
            reader.setInput(new MemoryCacheImageInputStream(Stream.toJavaInputStream(stream)));
            // Decode the image
            return reader.read(0);
        }
        return ImageIO.read(new MemoryCacheImageInputStream(Stream.toJavaInputStream(stream)));
    }

    static void writeBySkia(BufferedImage image, Stream stream) throws IOException
    {
        // write by org.sejda.imageio webp-imageio
        ImageIO.write(image, "webp", Stream.toJavaOutputStream(stream));
    }

}

