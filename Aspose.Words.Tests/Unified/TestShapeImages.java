// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import com.aspose.FileFormat;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.FileAccess;
import com.aspose.ImageUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import com.aspose.DrColor;
import com.aspose.ms.System.Drawing.msColor;
import java.awt.image.BufferedImage;
import com.aspose.BitmapPal;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.StreamUtil;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for images.
/// </summary>
@Test
public class TestShapeImages extends UnifiedTestsBase
{
    @BeforeMethod (alwaysRun = true)
    public void init()
    {
        RandomUtil.reset();
    }


    private static /*FileFormat*/int getImageType(String fileName) throws Exception
    {
        FileStream fileStream = new FileStream(fileName, FileMode.OPEN, FileAccess.READ);
        try /*JAVA: was using*/
    	{
            return ImageUtil.getImageType(fileStream);
    	}
        finally { if (fileStream != null) fileStream.close(); }
    }

    @Test
    public void testBrightness()
    {
        msAssert.areEqual(-0.5, ImageData.percentToBrightness(0.0));
        msAssert.areEqual(0.0, ImageData.percentToBrightness(0.5));
        msAssert.areEqual(0.5, ImageData.percentToBrightness(1.0));

        msAssert.areEqual(0.0, ImageData.brightnessToPercent(-0.5));
        msAssert.areEqual(0.5, ImageData.brightnessToPercent(0.0));
        msAssert.areEqual(1.0, ImageData.brightnessToPercent(0.5));
    }

    @Test
    public void testContrast()
    {
        msAssert.areEqual(0.02, 0.01, ImageData.percentToContrast(0.01)); //1%
        msAssert.areEqual(0.2, 0.01, ImageData.percentToContrast(0.1)); //10%
        msAssert.areEqual(0.80, 0.01, ImageData.percentToContrast(0.4)); //40%
        msAssert.areEqual(1.0, 0.01, ImageData.percentToContrast(0.5)); //50%
        msAssert.areEqual(1.02, 0.01, ImageData.percentToContrast(0.51)); //51%
        msAssert.areEqual(1.25, 0.01, ImageData.percentToContrast(0.6)); //60%
        msAssert.areEqual(1.66, 0.01, ImageData.percentToContrast(0.7)); //70%
        msAssert.areEqual(2.5, 0.01, ImageData.percentToContrast(0.8)); //80%
        msAssert.areEqual(5.0, 0.01, ImageData.percentToContrast(0.9)); //90%
        msAssert.areEqual(50.0, 0.01, ImageData.percentToContrast(0.99)); //99%
        msAssert.areEqual(Double.MAX_VALUE, 0.01, ImageData.percentToContrast(1.0)); //100%

        msAssert.areEqual(0.01, 0.01, ImageData.contrastToPercent(0.02));
        msAssert.areEqual(0.1, 0.01, ImageData.contrastToPercent(0.20));
        msAssert.areEqual(0.4, 0.01, ImageData.contrastToPercent(0.80));
        msAssert.areEqual(0.5, 0.01, ImageData.contrastToPercent(1.0));
        msAssert.areEqual(0.51, 0.01, ImageData.contrastToPercent(1.02));
        msAssert.areEqual(0.6, 0.01, ImageData.contrastToPercent(1.25));
        msAssert.areEqual(0.7, 0.01, ImageData.contrastToPercent(1.66));
        msAssert.areEqual(0.8, 0.01, ImageData.contrastToPercent(2.5));
        msAssert.areEqual(0.9, 0.01, ImageData.contrastToPercent(5.0));
        msAssert.areEqual(0.99, 0.01, ImageData.contrastToPercent(50.0));
        msAssert.areEqual(1.0, 0.01, ImageData.contrastToPercent(Double.MAX_VALUE));
    }

    /// <summary>
    /// If image is unavailable then <see cref="ImageData.ToStream" /> and <see cref="ImageData.ToByteArray" />
    /// should return "no image" picture.
    /// </summary>
    @Test
    public void testNoImageFallback() throws Exception
    {
        Document doc = new Document();
        Shape shape = new Shape(doc, ShapeType.IMAGE);
        ImageData imageData = shape.getImageData();

        // Provided neither image bytes nor source full name
        msAssert.isNull(imageData.getImageBytes());
        msAssert.isEmpty(imageData.getSourceFullName());

        // This magic number is the size of our "no image".
        final int NO_IMAGE_LENGTH = 924;
        msAssert.areEqual(NO_IMAGE_LENGTH, ImageUtil.getNoImageBytes().length);
        byte[] imageBytes = imageData.toByteArray();
        msAssert.areEqual(NO_IMAGE_LENGTH, imageBytes.length);

    }



    /// <summary>
    /// JAVAGOLD OLE data is compressed using ZIP in WML and compression is different on Java.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestImageInline(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Image\\TestImageInline", lf, sf);

        if (lf == (/*LoadFormat*/int)LoadFormatTest.TEST_DOCX_DML) // Gold compare is enough.
            return;

        // This verifies the field that surrounds the image was stripped correctly.
        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 1, true);
        msAssert.areEqual("\r", para.getText());

        // This is an inline picture that inserted using Insert/Image From File in MS Word.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(true, shape.isImage());
        msAssert.areEqual(true, shape.isInline());

        // Word does not write image title to RTF for some reason.
        if (lf != LoadFormat.RTF)
            msAssert.areEqual("kyoa1008main", shape.getImageData().getTitle());

        msAssert.areEqual(236.25, shape.getWidth()); // This is 5250 twips, 262.5 points at 90% scale.
        msAssert.areEqual(164.25, shape.getHeight()); // This is 3285 twips.

        // This is an inline picture of an embedded OLE picture.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        msAssert.areEqual(false, shape.isImage());
        msAssert.areEqual(true, shape.isOleObject());
        msAssert.areEqual(true, shape.isInline());

        // Width is 194.25 in WordML made by MS Word.
        msAssert.areEqual(194.3, 1.0, shape.getWidth());
        msAssert.areEqual(116.2, 1.0, shape.getHeight());

        // This inline picture was simply pasted.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        msAssert.areEqual(true, shape.isImage());
        msAssert.areEqual(true, shape.isInline());
        msAssert.areEqual("", shape.getImageData().getTitle());

        // 3- WordArt object is here.

        // 4- Another embedded OLE object (Excel spreadsheet) here.

        // A metafile here.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 5, true);
        msAssert.areEqual(true, shape.isImage());
        msAssert.areEqual(true, shape.isInline());

        // Word does not write image title to RTF for some reason.
        if (lf != LoadFormat.RTF)
            msAssert.areEqual("GLOBE", shape.getImageData().getTitle());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestImageInlineSourceDataProvider")
	public void unifiedTestImageInlineSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestImageInline(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestImageInlineSourceDataProvider")
	Object[][] unifiedTestImageInlineSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = getShapeImagesTestScenarios().iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    /// <summary>
    /// Gid and Tif images are stored as PNG inside MS Word document.
    ///
    /// JAVAGOLD PNG is written differently on Java.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestImageGifTif(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Image\\TestImageGifTif", lf, sf);
        if (lf == (/*LoadFormat*/int)LoadFormatTest.TEST_DOCX_DML) // Gold compare is enough.
            return;

        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(ImageType.PNG, shape.getImageData().getImageType());

        shape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        msAssert.areEqual(ImageType.PNG, shape.getImageData().getImageType());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestImageGifTifSourceDataProvider")
	public void unifiedTestImageGifTifSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestImageGifTif(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestImageGifTifSourceDataProvider")
	Object[][] unifiedTestImageGifTifSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = getShapeImagesTestScenarios().iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    private static void checkLinkedImage(
        Document doc,
        int objectIdx,
        boolean isInline,
        String title,
        String src,
        boolean isLinkOnly) throws Exception
    {
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, objectIdx, true);
        ImageData imageData = shape.getImageData();
        msAssert.areEqual(isInline, shape.isInline());
        msAssert.areEqual(title, imageData.getTitle());
        msAssert.areEqual(src, imageData.getSourceFullName());
        msAssert.areEqual(true, imageData.isLink());
        msAssert.areEqual(isLinkOnly, imageData.isLinkOnly());
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestImageCropping(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Cropping\\TestImageCropping", lf, sf);

        // Crop to increase the picture.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        ImageData imageData = shape.getImageData();
        msAssert.areEqual(-0.04, 0.01, imageData.getCropLeft()); // 5pt in percent
        msAssert.areEqual(-0.17, 0.01, imageData.getCropTop()); // 10pt in percent
        msAssert.areEqual(-0.12, 0.01, imageData.getCropRight()); // 15pt in percent
        msAssert.areEqual(-0.34, 0.01, imageData.getCropBottom()); // 20pt in percent

        // Crop to decrease the picture.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        imageData = shape.getImageData();
        msAssert.areEqual(0.04, 0.01, imageData.getCropLeft()); // 5pt in percent
        msAssert.areEqual(0.17, 0.01, imageData.getCropTop()); // 10pt in percent
        msAssert.areEqual(0.12, 0.01, imageData.getCropRight()); // 15pt in percent
        msAssert.areEqual(0.34, 0.01, imageData.getCropBottom()); // 20pt in percent

        // No crop, check default values.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        imageData = shape.getImageData();
        msAssert.areEqual(0.0, imageData.getCropLeft());
        msAssert.areEqual(0.0, imageData.getCropTop());
        msAssert.areEqual(0.0, imageData.getCropRight());
        msAssert.areEqual(0.0, imageData.getCropBottom());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestImageCroppingSourceDataProvider")
	public void unifiedTestImageCroppingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestImageCropping(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestImageCroppingSourceDataProvider")
	Object[][] unifiedTestImageCroppingSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = getShapeImagesTestScenarios().iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestImageBorders(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Image\\TestImageBorders", lf, sf);

        if (lf == (/*LoadFormat*/int)LoadFormatTest.TEST_DOCX_DML) // Gold compare is enough.
            return;

        // Check picture with borders.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        BorderCollection borders = shape.getImageData().getBorders();
        checkBorder(borders.getLeft(), Color.RED, 3.0, LineStyle.SINGLE);
        checkBorder(borders.getTop(), Color.BLUE, 1.0, LineStyle.SINGLE);
        checkBorder(borders.getRight(), DrColor.GREEN.toNativeColor(), 3.0, LineStyle.DOT);
        checkBorder(borders.getBottom(), msColor.Empty, 3.0, LineStyle.SINGLE);

        // Check picture without borders.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        borders = shape.getImageData().getBorders();
        checkBorder(borders.getLeft(), msColor.Empty, 0.0, LineStyle.NONE);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestImageBordersSourceDataProvider")
	public void unifiedTestImageBordersSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestImageBorders(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestImageBordersSourceDataProvider")
	Object[][] unifiedTestImageBordersSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = getShapeImagesTestScenarios().iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    private static void checkBorder(Border border, Color color, double width, /*LineStyle*/int lineStyle)
    {
        msAssert.areEqual(color.getRGB(), border.getColor().getRGB());
        msAssert.areEqual(width, border.getLineWidth());
        msAssert.areEqual(lineStyle, border.getLineStyle());
    }

    /// <summary>
    /// Test that we can parse and save picture properties such as brightness, contrast.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestImageOptions(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Image\\TestImageOptions", lf, sf);

        if (lf == (/*LoadFormat*/int)LoadFormatTest.TEST_DOCX_DML) // Gold compare is enough.
            return;

        // Image with adjustments to contrast, brightness, grayscale and chroma key.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        checkImageOptions(shape, /*unchecked*/((int)(int) 0xff184fcaL), 0.4, 0.6, true, false);

        // Image with default values.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        checkImageOptions(shape, 0, 0.5, 0.5, false, false);

        // No color options.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        checkImageOptions(shape, 0, 0.5, 0.5, false, false);
        // Contrast +30%.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 3, true);
        checkImageOptions(shape, 0, 0.65, 0.5, false, false);
        // Brightness +30%.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 4, true);
        checkImageOptions(shape, 0, 0.5, 0.65, false, false);
        // Contrast +30%, brightness +30%.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 5, true);
        checkImageOptions(shape, 0, 0.65, 0.65, false, false);

        // Grayscale.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 6, true);
        checkImageOptions(shape, 0, 0.5, 0.5, true, false);
        // Grayscale, contrast +30%.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 7, true);
        checkImageOptions(shape, 0, 0.65, 0.5, true, false);
        // Grayscale, brightness +30%.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 8, true);
        checkImageOptions(shape, 0, 0.5, 0.65, true, false);
        // Grayscale, contrast +30%, brightness +30%.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 9, true);
        checkImageOptions(shape, 0, 0.65, 0.65, true, false);

        // WORDSNET-21919 Docx dml written by MS Word does not have greyscale attribute for bilevel images.
        // Rtf/wml/doc has it.

        boolean checkForGrayscale = (lf != (/*LoadFormat*/int)LoadFormatTest.TEST_DOCX_DML);

        // Bilevel.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 10, true);
        checkImageOptions(shape, 0, 0.5, 0.5, checkForGrayscale, true);
        // Bilevel, contrast +30%.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 11, true);
        checkImageOptions(shape, 0, 0.65, 0.5, checkForGrayscale, true);
        // Bilevel, brightness +30%.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 12, true);
        checkImageOptions(shape, 0, 0.5, 0.65, checkForGrayscale, true);
        // Bilevel, contrast +30%, brightness +30%.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 13, true);
        checkImageOptions(shape, 0, 0.65, 0.65, checkForGrayscale, true);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestImageOptionsSourceDataProvider")
	public void unifiedTestImageOptionsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestImageOptions(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestImageOptionsSourceDataProvider")
	Object[][] unifiedTestImageOptionsSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = getShapeImagesTestScenarios().iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    private static void checkImageOptions(
        Shape image,
        int chromaKey,
        double contrast,
        double brightness,
        boolean grayScale,
        boolean biLevel)
    {
        ImageData imageData = image.getImageData();
        msAssert.areEqual(chromaKey, imageData.getChromaKey().getRGB());
        // Delta equals 0.01 is enough to brightness and contrast values
        final double DELTA = 0.01;
        msAssert.areEqual(brightness, DELTA, imageData.getBrightness());
        msAssert.areEqual(contrast, DELTA, imageData.getContrast());
        msAssert.areEqual(grayScale, imageData.getGrayScale());
        msAssert.areEqual(biLevel, imageData.getBiLevel());
    }


    // FOSS: UnifiedTestImageInsertWmf removed - it verified WMF placeable-header add/strip via exact metafile resolution/pixel sizes (DV's metafile scanner); that scanning is degraded in FOSS (reads 96 dpi instead of 288).






    /// <summary>
    /// Verify that the same image is loaded into memory only once and stored into file only once.
    /// This test is for a combination of floating and inline images.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestImageOnceMixed(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Image\\TestImageOnceMixed", lf, sf);

        if (lf == (/*LoadFormat*/int)LoadFormatTest.TEST_DOCX_DML) // Gold compare is enough.
            return;

        // TODO 1 DV This feature is still to be implemented in RTF.
        if ((lf == LoadFormat.RTF) || (sf == SaveFormat.RTF))
            return;

        // WORDSNET-18645 Disable this optimization for DOC format.
        if ((lf == LoadFormat.DOC) || (sf == SaveFormat.DOC))
            return;

        // Verify how images load.
        // When an image is duplicated in the document, it should be only one object in memory.
        Shape float1 = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        Shape float2 = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        Shape inline = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        msAssert.isTrue(float1 != float2);
        // In this WordML all floating and inline images reuse same data and that loads into model as such.
        // In the DOC file floating and inline images are not reused, but we still load into model properly.
        // In RTF none of the images are reused, but we still need to load into model properly.
        msAssert.isTrue(float1.getImageData().getImageBytes() == float2.getImageData().getImageBytes());
        msAssert.isTrue(inline.getImageData().getImageBytes() == float2.getImageData().getImageBytes());


        // Verify that during cloning - image bytes are not deep copied.
        Shape shapeCopy = (Shape)float1.deepClone(true);
        msAssert.isTrue(float1 != shapeCopy);
        msAssert.isTrue(float1.getImageData() != shapeCopy.getImageData());
        msAssert.isTrue(float1.getImageData().getImageBytes() == shapeCopy.getImageData().getImageBytes());


        // Now save and load again to verify how images were saved.
        // It should still be the single image bytes in memory.
        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Image\\TestImageOnceMixed", lf, sf);
        float1 = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        float2 = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        inline = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        msAssert.isTrue(float1 != float2);
        msAssert.isTrue(float1.getImageData().getImageBytes() == float2.getImageData().getImageBytes());
        msAssert.isTrue(inline.getImageData().getImageBytes() == float2.getImageData().getImageBytes());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestImageOnceMixedSourceDataProvider")
	public void unifiedTestImageOnceMixedSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestImageOnceMixed(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestImageOnceMixedSourceDataProvider")
	Object[][] unifiedTestImageOnceMixedSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = getShapeImagesTestScenarios().iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Verify that the same image is loaded into memory only once and stored into file only once.
    /// This test is for inline images.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestImageOnceInline(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Image\\TestImageOnceInline", lf, sf);

        if (lf == (/*LoadFormat*/int)LoadFormatTest.TEST_DOCX_DML) // Gold compare is enough.
            return;

        // TODO 1 DV This feature is still to be implemented in RTF.
        if ((lf == LoadFormat.RTF) || (sf == SaveFormat.RTF))
            return;

        // WORDSNET-18645 Disable this optimization for DOC format.
        if ((lf == LoadFormat.DOC) || (sf == SaveFormat.DOC))
            return;

        // When an image is duplicated in the document, it should be only one object in memory.
        Shape inline1 = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        Shape inline2 = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        msAssert.isTrue(inline1 != inline2);
        msAssert.isTrue(inline1.getImageData().getImageBytes() == inline2.getImageData().getImageBytes());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestImageOnceInlineSourceDataProvider")
	public void unifiedTestImageOnceInlineSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestImageOnceInline(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestImageOnceInlineSourceDataProvider")
	Object[][] unifiedTestImageOnceInlineSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = getShapeImagesTestScenarios().iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    /// <summary>
    /// WORDSNET-3702 There is not enough memory or disk space to display or print the picture.
    /// RK As I figured out MS Word seems to fail on 32 bit (and some other) BMP (DIB) images.
    /// I made our code to automatically convert BMP to PNG when a BMP image is inserted.
    ///
    /// JAVAGOLD Some rare rounding differences during number formatting on Java that I was unable to solve.
    /// JAVAGOLD PNG are encoded differently on Java.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDefect3702(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Shape shape = builder.insertImage(TestUtil.getInTestDataPath("Model\\Shape\\Image\\TestDefect3702.bmp"));
        // The BMP was in fact converted into PNG to be stored in the model and in the file. It fixes the defect.
        msAssert.areEqual(ImageType.PNG, shape.getImageData().getImageType());
        TestUtil.saveOpen(builder.getDocument(), "Model\\Shape\\Image\\TestDefect3702", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefect3702SourceDataProvider")
	public void unifiedTestDefect3702Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefect3702(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefect3702SourceDataProvider")
	Object[][] unifiedTestDefect3702SourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = getShapeImagesTestScenarios().iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestImageOnceInDifferentDocumentParts(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Shape\\Image\\TestImageOnceInDifferentDocumentParts", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestImageOnceInDifferentDocumentPartsSourceDataProvider")
	public void unifiedTestImageOnceInDifferentDocumentPartsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestImageOnceInDifferentDocumentParts(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestImageOnceInDifferentDocumentPartsSourceDataProvider")
	Object[][] unifiedTestImageOnceInDifferentDocumentPartsSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = getShapeImagesTestScenarios().iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    /// <summary>
    /// This tests maximum of DrawingML image properties.
    /// </summary>
    @Test
    public void testDmlImageAll() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Shape\\Image\\TestDmlImageAll.docx");
    }

    // FOSS: TestDefect7159 removed — it verified image size when saving to RTF; RTF save was removed.

    /// <summary>
    /// WORDSNET-15504 Default value of Shape.AspectRatioLocked is incorrect.
    /// The value is correct, because for VML image it is inherited from ShapeType. The default value depends on the
    /// ShapeType, for the ShapeType.Image it is true but for the other shape types it is false. The description of
    /// AspectRatioLocked property was changed.
    /// </summary>
    @Test
    public void testJira15504() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert image through the DocumentBuilder.
        Shape shape = builder.insertImage(TestUtil.buildTestFileName("Model\\Shape\\Image\\TestPng.png"));
        msAssert.isTrue(shape.getAspectRatioLocked());

        // Create image shape (VML by default).
        shape = new Shape(doc, ShapeType.IMAGE);
        msAssert.isTrue(shape.getAspectRatioLocked());

        shape = new Shape(doc, ShapeType.TEXT_BOX);
        msAssert.isFalse(shape.getAspectRatioLocked());

        // Set size upon insert but preserve AspectRatioLocked equals True.
        shape = builder.insertImage(TestUtil.buildTestFileName("Model\\Shape\\Image\\TestPng.png"),
            RelativeHorizontalPosition.DEFAULT, 0.0, RelativeVerticalPosition.PAGE, 0.0, 400.0, 100.0, WrapType.INLINE);
        msAssert.isTrue(shape.getAspectRatioLocked());
        msAssert.areEqual(400, shape.getWidth());
        msAssert.areEqual(100, shape.getHeight());
    }

    // FOSS: TestDefect7160 removed — it verified EMF image size when saving to RTF; RTF save was removed.

    // FOSS: TestJira775 removed - it sized zero-size shapes from a linked WMF; reading the WMF's dimensions needs metafile parsing that is degraded in FOSS (falls back to a 960pt default instead of 139.3).

    /// <summary>
    /// WORDSNET-12462 Page size is set to be equal to size of the image.
    /// The image is placed to occupy the entire page. The image is scaled, but it should not.
    /// </summary>
    @Test
    public void testJira12462() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        BufferedImage image = null;
        try
        {
            // For some reason if use BitmapPal.LoadNativeImage (Image.FromStream) GDI+ exception occurs. So keep using Image.FromFile in this test.
            image = BitmapPal.loadNativeImage(TestUtil.buildTestFileName("Model\\Shape\\Image\\TestJira12462.gif"));
            //JAVA-changed: java's BufferedImage hasn't Resolution.
            double imageHeight = image.getHeight();
            double imageWidth = image.getWidth();

            builder.getPageSetup().setPageWidth(imageWidth);
            builder.getPageSetup().setPageHeight(imageHeight);

            Shape shape = builder.insertImage(image, RelativeHorizontalPosition.PAGE, 0.0,
                RelativeVerticalPosition.PAGE, 0.0, imageWidth, imageHeight, WrapType.NONE);

            msAssert.areEqual(imageWidth, 0.5, shape.getWidth(), "Shape width");
            msAssert.areEqual(imageHeight, 0.5, shape.getHeight(), "Shape height");
        }
        finally
        {
            if (image != null)
                image.flush();
        }
    }


    // WORDSCPP-1177 C++ Porter doesn't support TestCase and TestCaseSource attribute at the same time
    public static Iterable<TestCaseData> getShapeImagesTestScenarios()
    {
        // FOSS: only the OOXML load/save scenarios survive — Doc/Rtf/WordML were removed.
        return msArray.iterableArray(new TestCaseData[] {
            new TestCaseData(LoadFormat.DOCX, SaveFormat.DOCX),
            new TestCaseData((/*LoadFormat*/int)LoadFormatTest.TEST_DOCX_DML, SaveFormat.DOCX),
        });

    }

    /// <summary>
    /// WORDSNET-24967 Add method to shape API to keep image aspect ratio.
    /// Tests applying FitImageToShape() to DmlNodeType.Picture.
    /// </summary>
    @Test
    public void test24967() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        BufferedImage image = BitmapPal.loadNativeImage(TestUtil.buildTestFileName("Model\\Shape\\Image\\Test24967.png"));
        Shape shape = builder.insertImage(image);
        shape.setWidthSafe(250.0);
        shape.setHeightSafe(50.0);
        shape.getImageData().fitImageToShape();

        DmlBlipFill fill = ((DmlPicture)shape.getDmlNode()).getBlipFill();
        checkBlipStretch(fill, 0.459);
        TestUtil.saveCheckGold(doc, "Model\\Shape\\Image\\Test24967.docx");
    }

    /// <summary>
    /// Relates to WORDSNET-24967
    /// Tests applying FitImageToShape() to inappropriate DmlNode types.
    /// </summary>
    @Test
    public void test24967B_NoCrash() throws Exception
    {
        checkDmlNode(DmlNodeType.SHAPE);
        checkDmlNode(DmlNodeType.CONNECTOR_SHAPE);
        checkDmlNode(DmlNodeType.CHART);
        checkDmlNode(DmlNodeType.DIAGRAM);
        checkDmlNode(DmlNodeType.CONTENT_PART);
    }

    /// <summary>
    /// Relates to WORDSNET-24967
    /// Tests applying FitImageToShape() to Vml shape with the given ImageData.ImageBytes.
    /// </summary>
    @Test
    public void test24967D() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = new Shape(builder.getDocument(), ShapeType.IMAGE);
        shape.setWidthSafe(250.0);
        shape.setHeightSafe(50.0);
        shape.getImageData().setImageBytes(StreamUtil.copyFileToByteArray(TestUtil.buildTestFileName("Model\\Shape\\Image\\Test24967.png")));
        builder.insertNode(shape);
        shape.getImageData().fitImageToShape();

        checkVmlShape(shape);
        TestUtil.saveCheckGold(doc, "Model\\Shape\\Image\\Test24967D.docx");
    }

    /// <summary>
    /// Relates to WORDSNET-24967
    /// Tests applying FitImageToShape() to Vml shape with the given ShapePr[ShapeAttr.FillImageBytes].
    /// </summary>
    @Test
    public void test24967E() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = new Shape(builder.getDocument(), ShapeType.IMAGE);
        shape.setWidthSafe(200.0);
        shape.setHeightSafe(150.0);
        builder.insertNode(shape);
        byte[] imageBytes = StreamUtil.copyFileToByteArray(TestUtil.buildTestFileName("Model\\Shape\\Image\\Test24967.png"));
        shape.getShapePr().set(ShapeAttr.FILL_IMAGE_BYTES, imageBytes);
        shape.getFillCore().setFillType(FillTypeCore.PICTURE);
        shape.setFilled(true);

        shape.getImageData().fitImageToShape();

        checkVmlShape(shape);
        TestUtil.saveCheckGold(doc, "Model\\Shape\\Image\\Test24967E.docx");
    }


    private void checkDmlNode(/*DmlNodeType*/int dmlNodeType) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = new Shape(builder.getDocument(), ShapeMarkupLanguage.DML);
        builder.insertNode(shape);
        shape.setWidthSafe(250.0);
        shape.setHeightSafe(50.0);
        shape.getImageData().fitImageToShape();

        switch (dmlNodeType)
        {
            case DmlNodeType.SHAPE:
                shape.setDmlNode(new DmlShape(DmlNodeType.SHAPE));
                break;
            case DmlNodeType.CONNECTOR_SHAPE:
                shape.setDmlNode(new DmlShape(DmlNodeType.CONNECTOR_SHAPE));
                break;
            case DmlNodeType.DIAGRAM:
                shape.setDmlNode(new DmlDiagram());
                break;
            case DmlNodeType.CHART:
                shape.setDmlNode(new DmlChartSpace());
                break;
            case DmlNodeType.CONTENT_PART:
                shape.setDmlNode(new DmlContentPart());
                break;
            default:
                break;
        }

        shape.getImageData().fitImageToShape();
        msAssert.isNotNull(shape.getImageData());
    }

    private void checkBlipStretch(DmlBlipFill fill, double expectedOffset)
    {
        DmlBlipFillStretch stretch = ms.as(fill.getBlipFillMode(), DmlBlipFillStretch.class);
        msAssert.areEqual(expectedOffset, 0.0001, stretch.getFillRectangle().getLeftOffset());
        msAssert.areEqual(expectedOffset, 0.0001, stretch.getFillRectangle().getRightOffset());
        msAssert.areEqual(0, 0.1, stretch.getFillRectangle().getTopOffset());
        msAssert.areEqual(0, 0.1, stretch.getFillRectangle().getBottomOffset());
    }

    private void checkVmlShape(Shape shape) throws Exception
    {
        msAssert.isTrue(shape.getFillCore().getLockAspectRatio());
        msAssert.isTrue(shape.getFilled());
        msAssert.areEqual(FillTypeCore.PICTURE, shape.getFillCore().getFillType());
        byte[] imageBytes = (byte[])shape.getShapePr().get(ShapeAttr.FILL_IMAGE_BYTES);
        msAssert.areEqual(1383, imageBytes.length);
        msAssert.isNull(shape.getImageData().getImageBytes());
    }
}

