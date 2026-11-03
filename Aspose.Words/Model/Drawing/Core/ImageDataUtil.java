// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ImageUtil;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.StreamUtil;
import com.aspose.UriUtil;
import com.aspose.SystemPal;
import com.aspose.ms.System.Convert;


/// <summary>
/// Helper class containing common methods used to draw image.
/// </summary>
class ImageDataUtil
{
    /// <summary>
    /// Load image bytes using ResourceLoadingCallback. 
    /// </summary>
    static byte[] loadImageBytes(String fileName, DocumentBase doc) throws Exception
    {
        byte[] imageBytes = null;

        if ((doc != null) && (doc.getResourceLoadingCallback() != null))
        {
            ResourceLoadingArgs args = new ResourceLoadingArgs("", fileName, ResourceType.IMAGE);
            switch (doc.getResourceLoadingCallback().resourceLoading(args))
            {
                case ResourceLoadingAction.USER_PROVIDED:
                    imageBytes = args.getData();
                    break;
                case ResourceLoadingAction.DEFAULT:
                    imageBytes = loadImageBytesCore(fileName, doc);
                    break;
                case ResourceLoadingAction.SKIP:
                    // If customer would like to skip loading external image, we should show NoImage cross. 
                    imageBytes = ImageUtil.getNoImageBytes();
                    break;
                default:
                    break;
            }
        }
        else
        {
            imageBytes = loadImageBytesCore(fileName, doc);
        }

        return imageBytes;
    }

    /// <summary>
    /// Opens image stream using ResourceLoadingCallback. 
    /// </summary>
    static Stream openStream(String fileName, DocumentBase doc) throws Exception
    {
        byte[] imageBytes = loadImageBytes(fileName, doc);

        return new MemoryStream(imageBytes);
    }

    /// <summary>
    /// Opens stream and copy it to byte array.
    /// Mute exception if any errors are occurred during stream reading.
    /// </summary>
    private static byte[] loadImageBytesCore(String fileName, DocumentBase doc) throws Exception
    {
        // WORDSNET-9480 The problem occurred because ImageData.ToByteArray method throws when it cannot read an image bytes.
        // Made code resilient. No-image is returned in case of some problems.
        byte[] imageBytes = null;
        try
        {
            Stream stream = openStreamUnsafe(fileName, ms.as(doc, Document.class));
            try /*JAVA: was using*/
        	{
                imageBytes = StreamUtil.copyStreamToByteArray(stream);
        	}
            finally { if (stream != null) stream.close(); }
        }
        catch(Exception e)
        {
            // Mute exception
        }
        return imageBytes;
    }

    /// <summary>
    /// Opens stream resolving relative paths via the current document (BaseUri of). 
    /// This code won't work properly once we implement loading documents from http etc. 
    /// <see cref="Document.MapFileName" /> and this method should be revised in this case.
    /// Consider using UriUtil.IsAbsoluteHref and UriUtil.CombineHref.
    /// </summary>
    public static Stream openStreamUnsafe(String href, Document document) throws Exception
    {
        if ((document != null) && !UriUtil.isHrefWithScheme(href))
            href = document.mapFileName(UriUtil.unescapeHref(href));

        return SystemPal.openStreamFromHref(href);
    }

    /// <summary>
    /// Calculate textbox IDs.
    /// </summary>
    public static int calcTextIds(int value)
    {
        return Convert.toInt32((value + 1) << 16);
    }
}

