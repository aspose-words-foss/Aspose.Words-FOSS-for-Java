// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 13/06/2012 by Vyacheslav Durin
package com.aspose;

import com.aspose.ms.System.Drawing.msSizeF;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.logging.Log;

import java.awt.*;
import java.awt.image.BufferedImage;

/// <summary>
/// This class is to be ported manually to Java.
/// </summary>
public /*static*/ class BitmapUtilPal
{
    /* Simulation of static class by using private constructor */
    private BitmapUtilPal()
    {}

    /// <summary>
    /// Converts a metafile as a stream into a bitmap and saves it to a byte array.
    /// </summary>
    /// <param name="imageStream">Metafile stream.</param>
    /// <param name="resolution">Metafile resolution.</param>
    /// <param name="emulateRasterOperations">Used only in Java.</param>
    /// <returns>Bitmap as a bytes array.</returns>
    public static byte[] convertMetafileToBitmapUsingGdiPlus(Stream imageStream, /*SizeF*/long resolution, boolean emulateRasterOperations, IFontProvider fontProvider) throws Exception
    {
	    assert(msSizeF.getHeight(resolution) != 0 && msSizeF.getWidth(resolution) != 0);
	    byte[] resultImgBytes = null;
        BitmapPal bitmap = null;
        MemoryStream memoryStream = null;

        try
        {
            bitmap = new BitmapPal(imageStream, true, fontProvider);

            // Copy bitmap as a png file into a byte array.
            memoryStream = new MemoryStream();
            bitmap.save(memoryStream, FileFormat.PNG);
            resultImgBytes = StreamUtil.copyStreamToByteArray(memoryStream);
        }
        finally
        {

            if (bitmap != null)
                bitmap.dispose();

            if (memoryStream != null)
            {
                try
                {
                    memoryStream.close();
                }
                catch (Exception ex)
                {
                    Log.error(ex);
                }
            }
        }

	    if (resultImgBytes == null || resultImgBytes.length <= 0)
		    resultImgBytes = StreamUtil.copyStreamToByteArray(imageStream);

        return resultImgBytes;
    }

    /// <summary>
    /// Creates Graphics object from an image.
    /// It can also prepare the most suitable/stable options if placed here.
    /// </summary>
    public static Graphics2D graphicsFromImage(BufferedImage image)
    {
        return image.createGraphics();
    }
}
