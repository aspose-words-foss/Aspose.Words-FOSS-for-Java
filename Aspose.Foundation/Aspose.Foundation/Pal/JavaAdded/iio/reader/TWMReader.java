// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/07/2024 by Alexey Maslov

package com.aspose;

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.IO.NetInputStream;
import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageReader;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.util.Iterator;

/**
 * This class is used to read TIFF with twelvemonkeys.imageio.tiff
 */
public class TWMReader extends ImageReader
{
    @Override
    protected BufferedImage doRead(Stream stream, ImageReaderBuilder.Options options) throws Exception
    {
        try (ImageInputStream iis = ImageIO.createImageInputStream(new NetInputStream(stream)))
        {
            Iterator<javax.imageio.ImageReader> readers = ImageIO.getImageReaders(iis);

            if (!readers.hasNext())
                throw new Exception("No suitable reader found for image");

            javax.imageio.ImageReader reader = readers.next();
            reader.setInput(iis);

            ImageReadParam readParam = reader.getDefaultReadParam();

            return reader.read(0, readParam);
        }
    }
}
