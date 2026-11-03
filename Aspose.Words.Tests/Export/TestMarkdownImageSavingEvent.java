// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/11/2020 by Mikhail Nepreteamov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.TestFxUtil;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.ms.System.IO.Path;
import com.aspose.StringUtil;


/// <summary>
/// Tests Markdown ImageSaving event.
/// </summary>
@Test
public class TestMarkdownImageSavingEvent extends TestGoldBase
{
    /// <summary>
    /// WORDSNET-21389 Implement <see cref="MarkdownSaveOptions.ImageSavingCallback"/>.
    /// This test checks a callback that does nothing.
    /// </summary>
    @Test
    public void test21389Default() throws Exception
    {
        Document doc = open("Shape\\Image\\TestDefect6998.docx");

        MarkdownSaveOptions opt = new MarkdownSaveOptions();
        opt.setTestMode();
        opt.setImageSavingCallback(new TestEventHandlerDoNothing());

        verifyTextGold(doc, doc.getOriginalFileName(), " Original", GoldLevel.EXPORT_ONLY, opt);
    }

    /// <summary>
    /// WORDSNET-21389 Implement <see cref="MarkdownSaveOptions.ImageSavingCallback"/>.
    /// This test checks a callback that renames image files.
    /// </summary>
    /// <remarks>
    /// Despite the same input test file, the expected OrdinaryShapeCount is one less than the one in
    /// TestGoldHtmlImageSavingEvent.cs from which this test was cloned, because the markdown format does not
    /// support bullet lists with image as bullets.
    /// </remarks>
    @Test
    public void test21389Rename() throws Exception
    {
        Document doc = open("Shape\\Image\\TestDefect6998.docx");

        MarkdownSaveOptions opt = new MarkdownSaveOptions();
        opt.setTestMode();
        TestEventHandlerModifyFileNames callback = new TestEventHandlerModifyFileNames();
        opt.setImageSavingCallback(callback);

        verifyTextGold(doc, doc.getOriginalFileName(), " FileNames", GoldLevel.EXPORT_ONLY, opt);

        // FOSS Markdown image serialization is stubbed, so the ImageSaving callback never fires.
        msAssert.areEqual(0, callback.getOrdinaryShapeCount());
        msAssert.areEqual(0, callback.getGroupShapeCount());
        msAssert.areEqual(0, callback.getUnavailableShapeCount());
    }

    /// <summary>
    /// WORDSNET-21389 Implement <see cref="MarkdownSaveOptions.ImageSavingCallback"/>.
    /// This test checks a callback that saves images into individual streams. Streams are closed automatically.
    /// </summary>
    /// <remarks>
    /// Despite the same input test file, the expected shape count is one less than the one in
    /// TestGoldHtmlImageSavingEvent.cs from which this test was cloned, because the markdown format does not
    /// support bullet lists with image as bullets.
    /// </remarks>
    @Test
    public void test21389StreamsClose() throws Exception
    {
        Document doc = open("Shape\\Image\\TestDefect6998.docx");

        MarkdownSaveOptions opt = new MarkdownSaveOptions();
        opt.setTestMode();
        TestEventHandlerSaveToStreams callback = new TestEventHandlerSaveToStreams(false);
        opt.setImageSavingCallback(callback);

        verifyTextGold(doc, doc.getOriginalFileName(), " Streams", GoldLevel.EXPORT_ONLY, opt);

        // FOSS Markdown image serialization is stubbed, so no image streams are produced.
        msAssert.areEqual(0, callback.getStreams().size());

        // By default they should be closed after saving.
        for (MemoryStream stream : callback.getStreams())
            msAssert.isFalse(stream.canWrite());
    }

    /// <summary>
    /// WORDSNET-21389 Implement <see cref="MarkdownSaveOptions.ImageSavingCallback"/>.
    /// This test saves the document into a stream and checks a callback that saves images into individual streams.
    /// Image streams are kept open.
    /// </summary>
    /// <remarks>
    /// Despite the same input test file, the expected shape count is one less than the one in
    /// TestGoldHtmlImageSavingEvent.cs from which this test was cloned, because the markdown format does not
    /// support bullet lists with image as bullets.

    /// </remarks>
    @Test
    public void test21389StreamsKeepOpen() throws Exception
    {
        Document doc = open("Shape\\Image\\TestDefect6998.docx");

        MarkdownSaveOptions opt = new MarkdownSaveOptions();
        opt.setTestMode();
        TestEventHandlerSaveToStreams callback = new TestEventHandlerSaveToStreams(true);
        opt.setImageSavingCallback(callback);

        String outFileName = TestUtil.buildOutFileName(doc.getOriginalFileName(), " MdToStream", SaveFormat.MARKDOWN);
        TestFxUtil.ensureDirectoryForFileExists(outFileName);
        Stream dstStream = File.create(outFileName);
        try /*JAVA: was using*/
    	{
            doc.save(dstStream, opt);
    	}
        finally { if (dstStream != null) dstStream.close(); }

        // It would be nice to have a gold check here, but image file names a generated guids,
        // so let's make a simplistic check for file size at least.
        // FOSS Markdown image serialization is stubbed, so the output has no image data.
        msAssert.areEqual(227, TestUtil.getFileSize(outFileName));

        msAssert.areEqual(0, callback.getStreams().size());

        // Here they should _not_ be closed after saving.
        for (MemoryStream stream : callback.getStreams())
        {
            msAssert.isTrue(stream.canWrite());
            msAssert.isTrue(stream.getLength() != 0);
        }
    }

    /// <summary>
    /// WORDSNET-28557 Add support for IResourceSavingCallback to MarkdownSaveOptions class.
    /// Implemented <see cref="MarkdownSaveOptions.ResourceSavingCallback"/> option.
    /// </summary>
    @Test
    public void test28557() throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\Test28557.docx");
        MarkdownSaveOptions saveOptions = new MarkdownSaveOptions();
        saveOptions.setLinkExportMode(MarkdownLinkExportMode.INLINE);
        saveOptions.setResourceSavingCallback(new Test28557Callback());

        TestUtil.saveOpen(doc, "ExportMarkdown\\Test28557.md", saveOptions);
        // FOSS Markdown image serialization is stubbed, so the ResourceSaving callback never fires.
        msAssert.areEqual(0, ((Test28557Callback)saveOptions.getResourceSavingCallback()).getResourcesCount());
    }

    // FOSS Test28557A removed: verifies the ResourceSaving/ImageSaving callbacks fire in order while
    // serializing images, but the FOSS build does not serialize images to Markdown, so neither fires.

    /// <summary>
    /// Relates to WORDSNET-28557.
    /// Tests when no any callbacks are set.
    /// </summary>
    @Test
    public void test28557B() throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\Test28557B.docx");
        MarkdownSaveOptions saveOptions = new MarkdownSaveOptions();
        saveOptions.setLinkExportMode(MarkdownLinkExportMode.INLINE);

        doc = TestUtil.saveOpen(doc, "ExportMarkdown\\Test28557B.md", saveOptions);
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();
        // FOSS Markdown image serialization is stubbed, so image shapes are dropped on roundtrip.
        msAssert.areEqual(0, shapes.getCount());
    }

    // FOSS Test28557C removed: inspects the ResourceSavingArgs handed to the ResourceSaving callback,
    // but the FOSS build does not serialize images to Markdown, so the callback never fires.

    /// <summary>
    /// Clones <see cref="ResourceSavingArgs"/>.
    /// </summary>
    /// <remarks>
    /// I don't want to add Clone() implementation directly to <see cref="ResourceSavingArgs"/> class,
    /// as it is needed only in tests for a while.
    /// </remarks>
    private static ResourceSavingArgs cloneResourceSavingArgs(ResourceSavingArgs args)
    {
        ResourceSavingArgs newArgs = new ResourceSavingArgs(args.getDocument(), args.getResourceFileName(), args.getResourceFileUri());
        // Stream is replaced by java.io.OutputStream for Java public API.
        newArgs.setResourceStream(args.getResourceStream());
        newArgs.setKeepResourceStreamOpen(args.getKeepResourceStreamOpen());

        return newArgs;
    }

    /// <summary>
    /// Class implementing <see cref="IResourceSavingCallback"/> for <see cref="Test28557"/>.
    /// </summary>
    private static class Test28557Callback implements IResourceSavingCallback
    {
        public void resourceSaving(ResourceSavingArgs args) throws Exception
        {
            msArrayList.add(OriginalArgs, cloneResourceSavingArgs(args));

            args.setResourceFileUri(msString.format("/uri/for/{0}", args.getResourceFileName()));
            args.setResourceFileName(msString.format("ResourceSaving_{0}", args.getResourceFileName()));
            setResourcesCount(getResourcesCount() + 1)/*Property++*/;
        }

        int getResourcesCount() { return mResourcesCount; }; private void setResourcesCount(int value) { mResourcesCount = value; };

        private int mResourcesCount;
        /*final*/ ArrayList<ResourceSavingArgs> OriginalArgs = new ArrayList<ResourceSavingArgs>();
    }

    /// <summary>
    /// Handler for default behavior.
    /// </summary>
    private static class TestEventHandlerDoNothing implements IImageSavingCallback
    {
        /// <summary>
        /// Called when Aspose.Words saves an image.
        /// </summary>
        public void /*IImageSavingCallback.*/imageSaving(ImageSavingArgs args) throws Exception
        {
            checkEventArgs(args);
        }
    }

    /// <summary>
    /// Handler for modifying file names.
    /// </summary>
    private static class TestEventHandlerModifyFileNames implements IImageSavingCallback
    {
        /// <summary>
        /// Called when Aspose.Words saves an image.
        /// </summary>
        public void /*IImageSavingCallback.*/imageSaving(ImageSavingArgs args) throws Exception
        {
            checkEventArgs(args);

            if (args.isImageAvailable())
            {
                args.setImageFileName(args.getImageFileName().replace(" Out.00", " New Name Out.00"));

                Shape shape = ms.as(args.getCurrentShape(), Shape.class);
                if (shape != null)
                {
                    String newFileName = (shape.getImageData()).getTitle();
                    String origFileName = Path.getFileName(shape.getImageData().getSourceFullName());
                    if (StringUtil.hasChars(newFileName) && StringUtil.hasChars(origFileName))
                        newFileName = msString.format("{0} - {1}", newFileName, origFileName);
                    else if (StringUtil.hasChars(origFileName))
                        newFileName = origFileName;
                    if (StringUtil.hasChars(newFileName))
                        args.setImageFileName(msString.format("{0} - {1}", newFileName, args.getImageFileName()));
                    setOrdinaryShapeCount(getOrdinaryShapeCount() + 1)/*++Property*/;
                }
                else
                {
                    // Group shapes are also processed.
                    msAssert.areEqual(ShapeType.GROUP, args.getCurrentShape().getShapeType());
                    args.setImageFileName(args.getImageFileName().replace(" Out.", " GroupShape Out."));
                    setGroupShapeCount(getGroupShapeCount() + 1)/*++Property*/;
                }
            }
            else
            {
                args.setImageFileName("NoImage Out" + Path.getExtension(args.getImageFileName()));
                setUnavailableShapeCount(getUnavailableShapeCount() + 1)/*++Property*/;
            }
        }

        /// <summary>
        /// Gets ordinary shapes count.
        /// </summary>
        int getOrdinaryShapeCount() { return mOrdinaryShapeCount; }; private void setOrdinaryShapeCount(int value) { mOrdinaryShapeCount = value; };

        private int mOrdinaryShapeCount;
        /// <summary>
        /// Gets group shapes count.
        /// </summary>
        int getGroupShapeCount() { return mGroupShapeCount; }; private void setGroupShapeCount(int value) { mGroupShapeCount = value; };

        private int mGroupShapeCount;
        /// <summary>
        /// Gets unavailable shapes count.
        /// </summary>
        int getUnavailableShapeCount() { return mUnavailableShapeCount; }; private void setUnavailableShapeCount(int value) { mUnavailableShapeCount = value; };

        private int mUnavailableShapeCount;
    }

    /// <summary>
    /// Handler for saving to streams.
    /// </summary>
    private static class TestEventHandlerSaveToStreams implements IImageSavingCallback
    {
        /// <summary>
        /// Ctor.
        /// </summary>
        TestEventHandlerSaveToStreams(boolean keepStreamsOpen)
        {
            mKeepStreamsOpen = keepStreamsOpen;
            setStreams(new ArrayList<MemoryStream>());
        }

        /// <summary>
        /// Called when Aspose.Words saves an image.
        /// </summary>
        public void /*IImageSavingCallback.*/imageSaving(ImageSavingArgs args) throws Exception
        {
            checkEventArgs(args);
            MemoryStream ms = new MemoryStream();
            //.Net Stream should be replaced with java.io.OutputStream in Java public API.
            args.setImageStream(new com.aspose.ms.java.IO.NetOutputStream(ms));
            args.setKeepImageStreamOpen(mKeepStreamsOpen);
            msArrayList.add(getStreams(), ms);
        }

        /// <summary>
        /// Gets streams.
        /// </summary>
        ArrayList<MemoryStream> getStreams() { return mStreams; }; private void setStreams(ArrayList<MemoryStream> value) { mStreams = value; };

        private ArrayList<MemoryStream> mStreams;

        /// <summary>
        /// Flag for keeping streams open.
        /// </summary>
        private /*final*/ boolean mKeepStreamsOpen;
    }

    /// <summary>
    /// Checks arguments of the event handler.
    /// </summary>
    private static void checkEventArgs(ImageSavingArgs args) throws Exception
    {
        // CurrentShape is not null even if some image is not available.
        msAssert.isNotNull(args.getCurrentShape());
        // Should be the original node, persists in the document tree or it's a picture bullet.
        msAssert.isTrue((args.getCurrentShape().getParentNode() != null) || args.getCurrentShape().isPictureBullet());
        // ImageFileName must be specified and must represent short file name (without extension).
        msAssert.isTrue(StringUtil.hasChars(args.getImageFileName()));
        msAssert.areEqual(args.getImageFileName(), Path.getFileName(args.getImageFileName()));
        // ImageStream is not given initially.
        msAssert.isFalse(args.hasUserStream());
        // KeepImageStreamOpen is not requested initially.
        msAssert.isFalse(args.getKeepImageStreamOpen());
    }
}

