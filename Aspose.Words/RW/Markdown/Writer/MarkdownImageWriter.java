// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/07/2020 by Mikhail Nepreteamov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.Stream;
import com.aspose.UriUtil;


/// <summary>
/// The class responsible for writing Images into markdown.
/// </summary>
class MarkdownImageWriter
{
    /// <summary>
    /// Ctor.
    /// </summary>
    MarkdownImageWriter(String fileName, MarkdownSaveOptions saveOptions, MarkdownLinkDefinitionWriter linkDefinitionWriter) throws Exception
    {
        mSaveOptions = saveOptions;
        mLinkDefinitionWriter = linkDefinitionWriter;

        mImageSavingCallback = (mSaveOptions.getResourceSavingCallback() != null)
            ? new SavingCallbackProxy(mSaveOptions.getResourceSavingCallback(), mSaveOptions.getImageSavingCallback())
            : mSaveOptions.getImageSavingCallback();

        ImageResourceWriter imageResourceWriter = new ImageResourceWriter(
            fileName,
            null,
            mSaveOptions.getImagesFolder(),
            mSaveOptions.getImagesFolderAlias(),
            "",
            false,
            mImageSavingCallback);

        // FOSS
    }

    /// <summary>
    /// Processes a specified Shape.
    /// </summary>
    boolean onShape(ShapeBase shapeBase)
    {
        Debug.assertTrue(shapeBase != null);

        if (!MarkupLanguageShapeWriter.isShapeRenderable(shapeBase))
            return false;

        mShape = shapeBase;

        // FOSS
        return false;
    }

    /// <summary>
    /// Processes a specified Shape.
    /// </summary>
    boolean onShape(Shape shape) throws Exception
    {
        Debug.assertTrue(shape != null);

        if (!MarkupLanguageShapeWriter.isShapeRenderable(shape))
            return false;

        // WORDSNET-22787 We don't want to get NoImage as an output, just leave inaccessible SourceFullName unchanged.
        if ((shape.getDocument().getResourceLoadingCallback() == null) && !isImageAccessible(shape))
        {
            mShape = shape;

            WarningUtil.warn(
                shape.getDocument().getWarningCallback(),
                WarningType.DATA_LOSS_CATEGORY,
                WarningSource.IMAGE,
                msString.format("The image is not available: {0}.", shape.getImageData().getSourceFullName()));

            mShapeDestination = shape.getImageData().getSourceFullName();
            mLinkDefinitionWriter.onShape(mShape, mShapeDestination);

            return true;
        }

        return onShape((ShapeBase)shape);
    }

    /// <summary>
    /// Returns true, if image in a specified shape is accessible.
    /// </summary>
    /// <remarks>
    /// The image can be inaccessible due to it is defined as a link only
    /// and the link is not accessible.
    /// </remarks>
    private static boolean isImageAccessible(Shape shape) throws Exception
    {
        if (shape.getImageData() == null)
            return false;

        if (!shape.getImageData().isLinkOnly())
            return true;

        Document doc = ms.as(shape.getDocument(), Document.class);
        if (doc != null)
        {
            try
            {
                Stream stream = ImageDataUtil.openStreamUnsafe(shape.getImageData().getSourceFullName(), doc);
                try /*JAVA: was using*/
            	{
                    return (stream.getLength() != 0);
            	}
                finally { if (stream != null) stream.close(); }
            }
            catch(Exception e)
            {
                // Mute exception
            }
        }

        return false;
    }

    /// <summary>
    /// Gets a string that is ready for writing into markdown.
    /// </summary>
    String getText()
    {
        String referenceLabel = mLinkDefinitionWriter.getReferenceString(mShape, mShapeDestination);
        String title = (!msString.isNullOrEmpty(getTitle()))
            ? msString.format(" \"{0}\"", getTitle())
            : "";

        // Local and remote paths including ImageFolder shall be correct URI paths.
        String uri = mSaveOptions.getExportImagesAsBase64()
            ? mShapeDestination
            : UriUtil.hrefToUri(mShapeDestination);

        // Exported as Inline if the corresponding option is set or if the reference link could not have been resolved.
        boolean isExportAsInline = (mSaveOptions.getLinkExportMode() == MarkdownLinkExportMode.INLINE) ||
                                (!mLinkDefinitionWriter.hasCollapsedLinkDefinition(mShapeDestination, getTitle()));

        // WORDSNET-28748 Replace some characters with their hexadecimal references in Alt text.
        String altText = MarkdownUtil.replaceWithHexRef(mShape.getAlternativeText(), "\r\n");

        String resultText = msString.format("{0}{1}{2}{3}{4}{5}{6}",
            ImageDescriptionBlock.OPENING_DELIMITER,
            altText, ImageDescriptionBlock.CLOSING_DELIMITER,
            LinkDestinationBlock.OPENING_DELIMITER, uri, title, LinkDestinationBlock.CLOSING_DELIMITER);

        if (!isExportAsInline)
        {
            // Exported as a shortcut if referenceLabel is empty or matches the alternative text of the current Shape.
            boolean isExportAsShortcut = (msString.equals(altText, "")) ||
                                      ((!msString.equals(referenceLabel, "")) &&
                                       (msString.equals(referenceLabel.substring((1), (1) + (referenceLabel.length() - 2)),
                                        mShape.getAlternativeText())));
            resultText = isExportAsShortcut
                ? msString.format("!{0}", referenceLabel)
                : msString.format("{0}{1}{2}{3}", ImageDescriptionBlock.OPENING_DELIMITER, altText,
                    ImageDescriptionBlock.CLOSING_DELIMITER, referenceLabel);
        }

        return resultText;
    }

    /// <summary>
    /// Gets the current shape title.
    /// </summary>
    private String getTitle()
    {
        Shape shape = ms.as(mShape, Shape.class);
        return shape != null
            ? shape.getImageData().getTitle()
            : mShape.getTitle();
    }

    /// <summary>
    /// The writer of the link definitions.
    /// </summary>
    private /*final*/ MarkdownLinkDefinitionWriter mLinkDefinitionWriter;

    /// <summary>
    /// Save options of the source document.
    /// </summary>
    private /*final*/ MarkdownSaveOptions mSaveOptions;

    /// <summary>
    /// The shape.
    /// </summary>
    private ShapeBase mShape;

    /// <summary>
    /// The shape image destination.
    /// </summary>
    private String mShapeDestination;

    /// <summary>
    /// Image saving callback is either <see cref="MarkdownSaveOptions.ImageSavingCallback"/>,
    /// or <see cref="SavingCallbackProxy"/>.
    /// </summary>
    private /*final*/ IImageSavingCallback mImageSavingCallback;

    /// <summary>
    /// The class to proxy <see cref="mResourceSavingCallback"/>.
    /// </summary>
    /// <remarks>
    /// There are only images can be written as external resources in Markdown.
    /// However, defining <see cref="MarkdownSaveOptions.ResourceSavingCallback"/> allows you additionally
    /// to change <see cref="ResourceSavingArgs.ResourceFileUri"/>. So, you can change actual saved file name
    /// to something, like ![](/some/custom/uri).
    /// </remarks>
    private static class SavingCallbackProxy implements IImageSavingCallback
    {
        SavingCallbackProxy(
            IResourceSavingCallback resourceSavingCallback,
            IImageSavingCallback imageSavingCallback)
        {
            mResourceSavingCallback = resourceSavingCallback;
            mImageSavingCallback = imageSavingCallback;
        }

        /// <summary>
        /// Called when Aspose.Words saves an image to Markdown.
        /// </summary>
        public void imageSaving(ImageSavingArgs args) throws Exception
        {
            // Process ResourceSavingCallback.
            if (mResourceSavingCallback != null)
            {
                mArgs = new ResourceSavingArgs(args.getDocument(), args.getImageFileName(), "");
                mResourceSavingCallback.resourceSaving(mArgs);

                args.setImageFileName(mArgs.getResourceFileName());
                args.setImageStream(mArgs.getResourceStream());
                args.setKeepImageStreamOpen(mArgs.getKeepResourceStreamOpen());
            }

            // Process ImageSavingCallback.
            if (mImageSavingCallback != null)
                mImageSavingCallback.imageSaving(args);
        }

        /// <summary>
        /// Indicates whether the user has provided a custom URI for the resource file.
        /// </summary>
        boolean hasUserResourceFileUri() { return mArgs.hasUserResourceFileUri(); }

        /// <summary>
        /// Gets or sets the uniform resource identifier (URI) used to reference the resource file from the document.
        /// </summary>
        String getResourceFileUri() { return mArgs.getResourceFileUri(); }

        private ResourceSavingArgs mArgs;

        private /*final*/ IResourceSavingCallback mResourceSavingCallback;
        private /*final*/ IImageSavingCallback mImageSavingCallback;
    }
}

