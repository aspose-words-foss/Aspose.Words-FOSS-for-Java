// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/04/2020 by Artem Ptitsin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import com.aspose.BitmapPal;
import com.aspose.ms.System.msMath;
import com.aspose.ms.System.MidpointRounding;
import com.aspose.ImageUtil;
import com.aspose.HashSetGeneric;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.Drawing.msColor;
import java.awt.Color;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests the watermark.
/// </summary>
@Test
public class TestWatermark
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// WORDSNET-4879 Add a helper class for working with a document watermark.
    /// <see cref="Document.Watermark"/>
    /// </summary>
    @Test (dataProvider = "test4879DataProvider")
    public void test4879(String pathFile) throws Exception
    {
        Document doc = TestUtil.open(pathFile);

        doc.getWatermark().setText("Test1");
        doc.getWatermark().setText("Test2");

        for (HeaderFooter headerFooter : (Iterable<HeaderFooter>) doc.getFirstSection().getHeadersFooters())
        {
            if (headerFooter.isHeader())
            {
                msAssert.areEqual(1, headerFooter.getShapes().getCount());
                Shape watermark = headerFooter.getShapes().get(0);
                msAssert.isTrue(watermark.isWatermark());
                msAssert.areEqual("Test2", watermark.getTextPath().getText());
                msAssert.areEqual(headerFooter.getFirstParagraph(), watermark.getParentParagraph());
            }
        }

        doc.getWatermark().remove();

        for (HeaderFooter headerFooter : (Iterable<HeaderFooter>) doc.getFirstSection().getHeadersFooters())
        {
            if (headerFooter.isHeader())
                msAssert.areEqual(0, headerFooter.getShapes().getCount());
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test4879DataProvider")
	public static Object[][] test4879DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Shape\\Watermark\\Test4879A.docx"},
			{"Model\\Shape\\Watermark\\Test4879B.docx"},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-4879 Checks that the watermark is correctly detected.
    /// </summary>
    @Test (dataProvider = "test4879IsWatermarkDataProvider")
    public void test4879IsWatermark(String pathFile, int countShapes, int countWatermarks) throws Exception
    {
        Document doc = TestUtil.open(pathFile);
        ArrayList<Shape> shapes = doc.getChildNodes(NodeType.SHAPE, true).<Shape>toList();

        msAssert.areEqual(countShapes, shapes.size());

        int countRealWatermark = 0;
        for (Shape shape : shapes)
        {
            if (shape.isWatermark())
            {
                countRealWatermark++;
                assertIsWatermark(shape);
            }
        }

        msAssert.areEqual(countWatermarks, countRealWatermark);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test4879IsWatermarkDataProvider")
	public static Object[][] test4879IsWatermarkDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Shape\\Watermark\\Test4879IsWatermarkA.docx",  3,  0},
			{"Model\\Shape\\Watermark\\Test4879IsWatermarkB.docx",  3,  0},
			{"Model\\Shape\\Watermark\\Test4879IsWatermarkD.docx",  13,  10},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-4879 Checks that the watermark type is correctly detected.
    /// </summary>
    @Test (dataProvider = "test4879DefineWatermarkTypeDataProvider")
    public void test4879DefineWatermarkType(String path, /*WatermarkType*/int type) throws Exception
    {
        Document doc = TestUtil.open(path);
        msAssert.areEqual(type, doc.getWatermark().getType());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test4879DefineWatermarkTypeDataProvider")
	public static Object[][] test4879DefineWatermarkTypeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Shape\\Watermark\\Test4879DefineWatermarkTypeA.docx",  WatermarkType.NONE},
			{"Model\\Shape\\Watermark\\Test4879DefineWatermarkTypeB.docx",  WatermarkType.NONE},
			{"Model\\Shape\\Watermark\\Test4879DefineWatermarkTypeC.docx",  WatermarkType.TEXT},
			{"Model\\Shape\\Watermark\\Test4879DefineWatermarkTypeD.docx",  WatermarkType.TEXT},
			{"Model\\Shape\\Watermark\\Test4879DefineWatermarkTypeE.docx",  WatermarkType.IMAGE},
			{"Model\\Shape\\Watermark\\Test4879DefineWatermarkTypeF.docx",  WatermarkType.TEXT},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-4879 Checks the addition of a watermark to a new document.
    /// </summary>
    @Test
    public void test4879NewDocument() throws Exception
    {
        Document doc = new Document();
        doc.getWatermark().setText("Test");

        int countHeaders = 0;
        for (HeaderFooter headerFooter : (Iterable<HeaderFooter>) doc.getFirstSection().getHeadersFooters())
        {
            if (headerFooter.isHeader())
            {
                countHeaders++;
                Shape watermark = headerFooter.getShapes().get(0);
                msAssert.isTrue(watermark.isWatermark());
                msAssert.areEqual(headerFooter.getFirstParagraph(), watermark.getParentParagraph());
            }
        }

        msAssert.areEqual(3, countHeaders);
    }

    /// <summary>
    /// Relates to WORDSNET-4879 Checks the addition of a watermark to a new document
    /// with several sections of different types.
    /// </summary>
    @Test
    public void test4879NewDocumentSeveralSections() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
        builder.insertBreak(BreakType.SECTION_BREAK_EVEN_PAGE);
        builder.insertBreak(BreakType.SECTION_BREAK_ODD_PAGE);
        builder.insertBreak(BreakType.SECTION_BREAK_NEW_COLUMN);
        builder.insertBreak(BreakType.SECTION_BREAK_CONTINUOUS);

        Document doc = builder.getDocument();
        doc.getWatermark().setText("Test");

        int countHeaders = 0;
        for (HeaderFooter headersFooter : (Iterable<HeaderFooter>) doc.getFirstSection().getHeadersFooters())
        {
            if (headersFooter.isHeader())
            {
                countHeaders++;
                msAssert.isTrue(headersFooter.getShapes().get(0).isWatermark());
            }
        }

        msAssert.areEqual(3, countHeaders);
    }

    /// <summary>
    /// Relates to WORDSNET-4879 Checks that all watermarks are removed.
    /// </summary>
    @Test (dataProvider = "test4879RemoveWatermarkDataProvider")
    public void test4879RemoveWatermark(String path, int countWatermarks, int countShapes) throws Exception
    {
        Document doc = TestUtil.open(path);
        ArrayList<Shape> shapes = doc.getChildNodes(NodeType.SHAPE, true).<Shape>toList();

        msAssert.areEqual(countShapes, shapes.size());
        int countWatermark = 0;
        for (Shape shape : shapes)
        {
            if (shape.isWatermark())
            {
                assertIsWatermark(shape);
                countWatermark++;
            }
        }

        msAssert.areEqual(countWatermarks, countWatermark);
        doc.getWatermark().remove();

        shapes = doc.getChildNodes(NodeType.SHAPE, true).<Shape>toList();
        msAssert.areEqual(countShapes - countWatermarks, shapes.size());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test4879RemoveWatermarkDataProvider")
	public static Object[][] test4879RemoveWatermarkDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Shape\\Watermark\\Test4879RemoveWatermarkA.docx",  10,  13},
			{"Model\\Shape\\Watermark\\Test4879RemoveWatermarkB.docx",  1,  4},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-4879 Checks the default text watermark properties.
    /// </summary>
    @Test
    public void test4879TextWatermarkDefaults() throws Exception
    {
        Document doc = new Document();
        doc.getWatermark().setText("Test");
        Shape watermark = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_FIRST).getShapes().get(0);
        checkDefaultTextWatermark(watermark);
    }

    /// <summary>
    /// Relates to WORDSNET-4879 Checks the auto size font of the text watermark at different page sizes.
    /// </summary>
    @Test (dataProvider = "test4879TextWatermarkAutoSizeDataProvider") // Defaults
    public void test4879TextWatermarkAutoSize(/*Orientation*/int orientation, /*WatermarkLayout*/int layout, double width,
        double height) throws Exception
    {
        Document doc = new Document();
        doc.getFirstSection().getPageSetup().setOrientation(orientation);
        TextWatermarkOptions options = new TextWatermarkOptions();
        options.setLayout(layout);

        doc.getWatermark().setText("Test", options);
        Shape watermark = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_FIRST).getShapes().get(0);

        msAssert.areEqual(new double[] { width, height }, new double[] { watermark.getWidth(), watermark.getHeight() });
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test4879TextWatermarkAutoSizeDataProvider")
	public static Object[][] test4879TextWatermarkAutoSizeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{Orientation.PORTRAIT,  WatermarkLayout.DIAGONAL,  351.42,  259.52},
			{Orientation.LANDSCAPE,  WatermarkLayout.DIAGONAL,  380.7,  281.15},
			{Orientation.PORTRAIT,  WatermarkLayout.HORIZONTAL,  432,  319.03},
			{Orientation.LANDSCAPE,  WatermarkLayout.HORIZONTAL,  612,  451.97},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-4879 Checks the different size fonts of the text watermark.
    /// </summary>
    @Test (dataProvider = "test4879TextWatermarkSizeDataProvider") // Auto.
    public void test4879TextWatermarkSize(float size, double width, double height) throws Exception
    {
        try
        {
            Document doc = new Document();
            TextWatermarkOptions options = new TextWatermarkOptions();
            options.setFontSize(size);

            if ((size == -1) || (size == 1639))
                Assert.fail();

            doc.getWatermark().setText("Test", options);
            Shape watermark = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_FIRST).getShapes().get(0);

            msAssert.areEqual(new double[] { width, height }, new double[] { watermark.getWidth(), watermark.getHeight() });
        }
        catch (IllegalArgumentException e)
        {
            if ((size != -1) && (size != 1639))
                Assert.fail();
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test4879TextWatermarkSizeDataProvider")
	public static Object[][] test4879TextWatermarkSizeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{0,  351.42,  259.52},
			{1,  1.74,  1.29},
			{1638,  2850.12,  2104.83},
			{-1,  0,  0},
			{1639,  0,  0},
			{54,  93.96,  69.39},
			{55,  95.7,  70.68},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-4879 Checks the different text values of the text watermark.
    /// </summary>
    @Test (dataProvider = "test4879TextWatermarkTextDataProvider")
    public void test4879TextWatermarkText(String text) throws Exception
    {
        checkWatermarkText(text);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test4879TextWatermarkTextDataProvider")
	public static Object[][] test4879TextWatermarkTextDataProvider() throws Exception
	{
		return new Object[][]
		{
			{null},
			{""},
			{"   "},
			{"   Test   "},
			{"Test"},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-4879 Checks the upper bound of the allowed length of the watermark text.
    /// </summary>
    @Test (dataProvider = "test4879TextWatermarkUpperBoundTextLengthDataProvider")
    public void test4879TextWatermarkUpperBoundTextLength(int countChars) throws Exception
    {
        checkWatermarkText(generateStringSpecificLength(countChars));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test4879TextWatermarkUpperBoundTextLengthDataProvider")
	public static Object[][] test4879TextWatermarkUpperBoundTextLengthDataProvider() throws Exception
	{
		return new Object[][]
		{
			{199},
			{200},
			{201},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-4879 Checks the opacity of a text watermark
    /// when the semitrasparent property was set to false.
    /// </summary>
    @Test
    public void test4879TextWatermarkIsSemitrasparentFalse() throws Exception
    {
        Document doc = new Document();
        TextWatermarkOptions options = new TextWatermarkOptions();
        options.isSemitrasparent(false);

        doc.getWatermark().setText("Test", options);
        Shape watermark = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_FIRST).getShapes().get(0);

        msAssert.areEqual(1, watermark.getFill().getOpacity());
    }

    /// <summary>
    /// Relates to WORDSNET-4879 Checks the default image watermark properties.
    /// </summary>
    @Test
    public void test4879ImageWatermarkDefaults() throws Exception
    {
        final String PATH_IMAGE = "Model\\Shape\\Watermark\\Test4879WatermarkImage.png";
        Document doc = new Document();
        doc.getWatermark().setImage(BitmapPal.loadNativeImage(TestUtil.getInTestDataPath(PATH_IMAGE)));

        Shape watermark = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_FIRST).getShapes().get(0);

        msAssert.areEqual(0.15, msMath.round(watermark.getImageData().getContrast(), 2, MidpointRounding.AWAY_FROM_ZERO));
        msAssert.areEqual(0.85, msMath.round(watermark.getImageData().getBrightness(), 2, MidpointRounding.AWAY_FROM_ZERO));
        msAssert.areEqual(432, watermark.getWidth());
        msAssert.areEqual(432, watermark.getHeight());

        checkStandardWatermarkAttributes(watermark, "WordPictureWatermark");
    }

    /// <summary>
    /// Relates to WORDSNET-4879 Checks the different scales of the image watermark.
    /// Relates to WORDSJAVA-2386 Different scales of the image watermark.
    /// </summary>
    @Test (dataProvider = "test4879ImageWatermarkScaleDataProvider") // Default values - Auto.
    public void test4879ImageWatermarkScale(double scale, double width, double height) throws Exception
    {
        final String PATH_IMAGE = "Model\\Shape\\Watermark\\Test4879WatermarkImage.png";
        Document doc = new Document();
        ImageWatermarkOptions options = new ImageWatermarkOptions();

        try
        {
            options.setScale(scale);

            if ((scale == -1) || (scale == 65.51))
                Assert.fail();

            doc.getWatermark().setImage(TestUtil.getInTestDataPath(PATH_IMAGE), options);
            Shape watermark = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_FIRST).getShapes().get(0);

            double widthDiff = Math.abs(width - watermark.getWidth());
            double heightDiff = Math.abs(height - watermark.getHeight());

            msAssert.greater(1.0, widthDiff);
            msAssert.greater(1.0, heightDiff);
        }
        catch (IllegalArgumentException e)
        {
            if ((scale != -1) && (scale != 65.51))
                Assert.fail();
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test4879ImageWatermarkScaleDataProvider")
	public static Object[][] test4879ImageWatermarkScaleDataProvider() throws Exception
	{
		return new Object[][]
		{
			{0,  432,  432},
			{65.5,  4127.73,  4127.73},
			{0.01,  0.63,  0.63},
			{1,  63.02,  63.02},
			{-1,  432,  432},
			{65.51,  432,  432},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-4879 Checks the properties of a image watermark
    /// when the washout property was set to false.
    /// </summary>
    @Test
    public void test4879ImageWatermarkIsWashoutFalse() throws Exception
    {
        final String PATH_IMAGE = "Model\\Shape\\Watermark\\Test4879WatermarkImage.png";
        Document doc = new Document();
        ImageWatermarkOptions options = new ImageWatermarkOptions();
        options.isWashout(false);

        doc.getWatermark().setImage(BitmapPal.loadNativeImage(TestUtil.getInTestDataPath(PATH_IMAGE)), options);

        Shape watermark = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_FIRST).getShapes().get(0);

        msAssert.areEqual(0.5, watermark.getImageData().getContrast());
        msAssert.areEqual(0.5, watermark.getImageData().getBrightness());
    }

    /// <summary>
    /// Relates to WORDSNET-4879 Checks that image cannot be null.
    /// </summary>
    @Test
    public void test4879ImageWatermarkImageNull() throws Exception
    {
        try
        {
            new Document().getWatermark().setImage(null);
            Assert.fail();
        }
        catch (NullPointerException e) { }
    }

    /// <summary>
    /// Relates to WORDSNET-4879 Checks that if set null instead of <see cref="ImageWatermarkOptions"/> or
    /// <see cref="TextWatermarkOptions"/>, the parameters will be by default.
    /// </summary>
    @Test
    public void test4879OptionsIsNull() throws Exception
    {
        final String PATH_IMAGE = "Model\\Shape\\Watermark\\Test4879WatermarkImage.png";
        Document doc = new Document();

        doc.getWatermark().setText("Test", null);
        Shape shape = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getShapes().get(0);
        checkDefaultTextWatermark(shape);

        doc.getWatermark().setImage(BitmapPal.loadNativeImage(TestUtil.getInTestDataPath(PATH_IMAGE)), null);
        shape = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getShapes().get(0);
        checkDefaultImageWatermark(shape);
    }

    /// <summary>
    /// WORDSNET-23231 Shape.Id is duplicated for watermarks inserted by Aspose.Words.
    /// AW clones and inserts watermark shape to headers with original name and identifier.
    /// Need update shape names ans id's to fix the problem.
    /// </summary>
    @Test (dataProvider = "test23231DataProvider")
    public void test23231(boolean text) throws Exception
    {
        Document doc = new Document();

        if (text)
            doc.getWatermark().setText("Watermark");
        else
            doc.getWatermark().setImage(new BitmapPal(ImageUtil.getNoImageBytes()).getNativeImage());

        HashSetGeneric<Integer> shapeIds = new HashSetGeneric<Integer>();
        for(Section sect : (Iterable<Section>) doc.getSections())
        {
            for(HeaderFooter hf : (Iterable<HeaderFooter>) sect.getHeadersFooters())
            {
                msAssert.areEqual(1, hf.getShapes().getCount());
                Shape shape = hf.getShapes().get(0);

                assertIsWatermark(shape);

                if (shapeIds.contains(shape.getId()))
                    throw new Exception("duplicate shape id.");

                shapeIds.add(shape.getId());

                String name = shape.hasImage() ? Watermark.IMAGE_NAME_PREFIX : Watermark.TEXT_NAME_PREFIX;
                name = msString.plusEqOperator(name, Integer.toString(shape.getId()));

                msAssert.areEqual(name, shape.getName());
            }
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test23231DataProvider")
	public static Object[][] test23231DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}

    /// <summary>
    /// WORDSNET-24516 NullReferenceException is thrown when check watermark type in the document without header/footer.
    /// Added resilience for the document without header/footer.
    /// </summary>
    @Test
    public void test24516() throws Exception
    {
        Document doc = new Document();
        msAssert.areEqual(WatermarkType.NONE, doc.getWatermark().getType());
    }

    /// <summary>
    /// WORDSNET-24431 Watermark is displayed over the text
    /// Now the <see cref="ShapeBase.BehindText"/> property of a watermark shape is set to 'true' like MS Word does.
    /// </summary>
    @Test
    public void test24431() throws Exception
    {
        Document doc = new Document();

        doc.getWatermark().setText(msString.newString('i', 200));

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
        builder.writeln(msString.newString('i', 2000));
        builder.moveToHeaderFooter(HeaderFooterType.FOOTER_PRIMARY);
        builder.writeln(msString.newString('i', 2000));
        builder.moveToSection(0);
        builder.writeln(msString.newString('i', 2000));

        Shape shape = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getShapes().get(0);
        msAssert.isTrue(shape.getBehindText());

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Shape\\Watermark\\Test24431.docx");
    }


    // FOSS: Test27938 (set watermark image from an SVG stream) removed — SVG image
    // decoding is part of the removed image-rendering subsystem, so loading the .svg
    // watermark throws CantCreateBitmapException. Raster (PNG) image watermarks are
    // still covered by Test4879ImageWatermarkDefaults / Test4879ImageWatermarkScale.

    /// <summary>
    /// WORDSNET-28260 Exception is occurred in Document.Watermark.Type property.
    /// Added resilience for the completely empty document without any sections.
    /// </summary>
    @Test
    public void test28260() throws Exception
    {
        Document doc = new Document();
        doc.getSections().clear();
        msAssert.areEqual(WatermarkType.NONE, doc.getWatermark().getType());
    }

    private static void assertIsWatermark(Shape shape)
    {
        if (shape.getMarkupLanguage() != ShapeMarkupLanguage.VML)
            Assert.fail();

        boolean isWatermark = (shape.getName().contains("PowerPlusWaterMarkObject") && shape.isWordArt())
                           || (shape.getName().contains("WordPictureWatermark") && shape.isImage());

        if (isWatermark)
        {
            HeaderFooter headerFooter = ms.as(shape.getStoryAncestor(NodeType.HEADER_FOOTER), HeaderFooter.class);
            isWatermark = (headerFooter != null) && headerFooter.isHeader();
        }

        msAssert.isTrue(isWatermark);
    }

    private static void checkStandardWatermarkAttributes(Shape watermark, String requiredNamePart)
    {
        msAssert.areEqual(RelativeHorizontalPosition.MARGIN, watermark.getRelativeHorizontalPosition());
        msAssert.areEqual(RelativeVerticalPosition.MARGIN, watermark.getRelativeVerticalPosition());
        msAssert.areEqual(WrapType.NONE, watermark.getWrapType());
        msAssert.areEqual(VerticalAlignment.CENTER, watermark.getVerticalAlignment());
        msAssert.areEqual(HorizontalAlignment.CENTER, watermark.getHorizontalAlignment());

        msAssert.isTrue(watermark.getName().contains(requiredNamePart));
    }

    private static String generateStringSpecificLength(int countChars)
    {
        final char CH = 'a';
        StringBuilder builder = new StringBuilder(countChars);
        msStringBuilder.append(builder, CH, countChars);
        return builder.toString();
    }

    private static void checkWatermarkText(String text) throws Exception
    {
        try
        {
            Document doc = new Document();
            doc.getWatermark().setText(text);

            if ((text == null) || (msString.equals(text, "")) || (msString.trim(text).length() == 0) || (text.length() > 200))
                Assert.fail();
        }
        catch (IllegalArgumentException e)
        {
            if ((!msString.equals(text, "")) && (msString.trim(text).length() != 0) && (text.length() < 200))
                Assert.fail();
        }
        catch (NullPointerException e)
        {
            if (text != null)
                Assert.fail();
        }
    }

    private static void checkDefaultTextWatermark(Shape watermark)
    {
        msAssert.areEqual(0.5, watermark.getFill().getOpacity());
        msAssert.areEqual(msColor.getSilver().getRGB(), watermark.getFill().getColor().getRGB());
        msAssert.areEqual(msColor.getSilver().getRGB(), watermark.getStrokeColor().getRGB());
        msAssert.areEqual("Calibri", watermark.getTextPath().getFontFamily());
        msAssert.areEqual(315, watermark.getRotation());
        msAssert.areEqual(new double[] { 351.42, 259.52 }, new double[] { watermark.getWidth(), watermark.getHeight() });

        checkStandardWatermarkAttributes(watermark, "PowerPlusWaterMarkObject");
    }

    private static void checkDefaultImageWatermark(Shape watermark)
    {
        msAssert.areEqual(0.15, msMath.round(watermark.getImageData().getContrast(), 2, MidpointRounding.AWAY_FROM_ZERO));
        msAssert.areEqual(0.85, msMath.round(watermark.getImageData().getBrightness(), 2, MidpointRounding.AWAY_FROM_ZERO));
        msAssert.areEqual(432, watermark.getWidth());
        msAssert.areEqual(432, watermark.getHeight());

        checkStandardWatermarkAttributes(watermark, "WordPictureWatermark");
    }
}

