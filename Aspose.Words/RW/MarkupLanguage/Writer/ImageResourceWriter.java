// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/08/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FileFormat;
import com.aspose.FileFormatCore;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.File;
import com.aspose.FormatterPal;


/// <summary>
/// Helps to write images from the document model into most of the non-MS markup language formats.
/// </summary>
class ImageResourceWriter extends ResourceWriterBase
{
    /// <summary>
    /// Call this before building the document.
    /// </summary>
    /// <param name="fileName">Pass the name of the file you want to save to.
    /// Pass null if saving into a stream. Used to extract path and base name for images.</param>
    /// <param name="subsidiaryContentPartCollector">Can be null. If not null, then we collect all subsidiary
    /// parts to be written separately or externally.</param>
    /// <param name="exportImagesFolder">ExportImagesFolder.</param>
    /// <param name="exportImagesFolderAlias">ExportImagesFolderAlias.</param>
    /// <param name="messageWhenNoResourceFolder">Message that is put into exception when we cannot write 
    /// files because we have no clearly specified output.</param>
    /// <param name="isMakeResourceFolderAbsolute"></param>
    /// <param name="imageSavingCallback"></param>
    ImageResourceWriter(
        String fileName,
        SubsidiaryContentPartCollector subsidiaryContentPartCollector,
        String exportImagesFolder,
        String exportImagesFolderAlias,
        String messageWhenNoResourceFolder,
        boolean isMakeResourceFolderAbsolute,
        IImageSavingCallback imageSavingCallback) throws Exception
    {
    	super(fileName, subsidiaryContentPartCollector, exportImagesFolder, exportImagesFolderAlias,
            messageWhenNoResourceFolder, false, isMakeResourceFolderAbsolute);
	
        mImageSavingCallback = imageSavingCallback;
    }

    String writeExternalImage(
        ShapeBase shape,
        byte[] imageBytes,
        int offset,
        int length,
        /*FileFormat*/int fileFormat,
        boolean isImageAvailable) throws Exception
    {
        String fileExtension = FileFormatCore.toExt(fileFormat);
        String shortFileName = getNextImageFileName(fileExtension);
        String fullFileName = getFullFileName(shortFileName);

        UserStreamWrapper userStreamWrapper = null;
        if (mImageSavingCallback != null)
        {
            ImageSavingArgs args = new ImageSavingArgs(shape, isImageAvailable, shortFileName);
            mImageSavingCallback.imageSaving(args);
            shortFileName = args.getImageFileName();
            fullFileName = getFullFileName(shortFileName);

            if (args.hasUserStream())
                userStreamWrapper = args.createUserStreamWrapper();
        }

        if (getSubsidiaryContentPartCollector() != null)
        {
            // Collect it, don't write files.
            Debug.assertTrue(msString.equals(Path.getFileName(shortFileName), shortFileName));
            getSubsidiaryContentPartCollector().addPart(new BinaryContentPart(
                FileFormatCore.toContentType(fileFormat),
                shortFileName,
                imageBytes,
                offset,
                length));
        }
        else if (userStreamWrapper != null)
        {
            Stream userStream = userStreamWrapper.beginUserStream();
            userStream.write(imageBytes, offset, length);
        }
        else
        {
            createFolderIfNeeded();
            FileStream fileStream = File.create(fullFileName);
            try /*JAVA: was using*/
            {
                fileStream.write(imageBytes, offset, length);
            }
            finally { if (fileStream != null) fileStream.close(); }
        }

        if (userStreamWrapper != null)
            userStreamWrapper.endUserStream();

        return mapResourceFileName(fullFileName);
    }

    /// <summary>
    /// Calls the image saving callback to notify the user that a shape is about to be saved.
    /// </summary>
    void notifyCallback(
        ShapeBase shape,
        /*FileFormat*/int fileFormat,
        boolean isImageAvailable) throws Exception
    {
        if (mImageSavingCallback == null)
        {
            return;
        }

        String fileExtension = FileFormatCore.toExt(fileFormat);
        String shortFileName = getNextImageFileName(fileExtension);
        ImageSavingArgs args = new ImageSavingArgs(shape, isImageAvailable, shortFileName);
        mImageSavingCallback.imageSaving(args);

        if (args.hasUserStream())
        {
            // Make sure we close the user-provided stream if we are told to do so.
            UserStreamWrapper userStreamWrapper = args.createUserStreamWrapper();
            userStreamWrapper.endUserStream();
        }
    }

    /// <summary>
    /// Generates short (without path) image file name and increments inner counter.
    /// </summary>
    private String getNextImageFileName(String imageType)
    {
        ++mImageNum;
        return msString.format(
            "{0}.{1}.{2}",
            (getSubsidiaryContentPartCollector() != null) ? "image" : getResourceBaseName(),
            FormatterPal.intToStrD3(mImageNum),
            imageType);
    }

    /// <summary>
    /// A user-provided callback that is called for each image written.
    /// </summary>
    private /*final*/ IImageSavingCallback mImageSavingCallback;

    /// <summary>
    /// Current image number, used in generation of the unique image file name.
    /// </summary>
    private int mImageNum;
}

