// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/08/2013 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.File;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Text.Encoding;
import org.testng.annotations.DataProvider;


@Test
public class TestImageUtils
{
    @Test (dataProvider = "test24480DataProvider")
    public void test24480(String testName, boolean isColored) throws Exception
    {
        String testFileName = TestFxUtil.buildTestFileName(msString.format("Images\\{0}", testName));
        Stream stream = File.openRead(testFileName);
        try /*JAVA: was using*/
        {
            byte[] imageBytes = StreamUtil.copyStreamToByteArray(stream);
            msAssert.areEqual(isColored, ImageUtil.isImageColored(imageBytes));
        }
        finally { if (stream != null) stream.close(); }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test24480DataProvider")
	public static Object[][] test24480DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"CMYK\\TestCMYKImages_8.jpeg",  true},
			{"TestWrongHeight.jpg",  true},
			{"TestWrongHeightGrayscale.jpg",  false},
			{"CMYK\\TestCMYKImages_10_gray.png",  false},
			{"CMYK\\TestCMYKImages_9.jpeg",  true},
			{"CMYK\\TestCMYKImages_9_gray.jpeg",  false},
			{"TestDefect11932.jpg",  true},
			{"TestDefect11932_gray.jpg",  false},
			{"TestJpegSize.jpg",  true},
			{"TestJpegSize_gray.jpg",  false},
			{"TestJiraJ1847.gif",  true},
			{"TestJiraJ1847_gray.jpg",  false},
			{"CMYK\\TestJira6368_1.jpeg",  true},
			{"CMYK\\TestJira6368_1_gray.jpg",  false},
		};
	}

    /// <summary>
    /// Reading resolution in Photoshop metadata.
    /// </summary>
    @Test
    public void test23954() throws Exception
    {
        ImageSizeCore imageSize = ImageUtil.getImageSize(TestFxUtil.buildTestFileName("Images\\Test23594.jpeg"));
        msAssert.areEqual(300, imageSize.getHorizontalResolution());
        msAssert.areEqual(300, imageSize.getVerticalResolution());
    }

    /// <summary>
    /// WORDSNET-1463, fixed - InsertImage scales image size to 60%.
    /// </summary>
    @Test
    public void testDefect1463() throws Exception
    {
        ImageSizeCore imageSize = ImageUtil.getImageSize(TestFxUtil.buildTestFileName("Images\\TestDefect1463.jpg"));
        msAssert.areEqual(595, imageSize.getWidth());
        msAssert.areEqual(149, imageSize.getHeight());
        msAssert.areEqual(72.0, imageSize.getHorizontalResolution());
        msAssert.areEqual(72.0, imageSize.getVerticalResolution());

        imageSize = ImageUtil.getImageSize(TestFxUtil.buildTestFileName("Images\\TestDefect1463 2.jpg"));
        msAssert.areEqual(80, imageSize.getWidth());
        msAssert.areEqual(34, imageSize.getHeight());
        msAssert.areEqual(96.0, imageSize.getHorizontalResolution());
        msAssert.areEqual(96.0, imageSize.getVerticalResolution());
    }

    @Test
    public void testWrongHeight() throws Exception
    {
        ImageSizeCore imageSize = ImageUtil.getImageSize(TestFxUtil.buildTestFileName("Images\\TestWrongHeight.jpg"));

        msAssert.areEqual(3072, imageSize.getWidth());
        msAssert.areEqual(2304, imageSize.getHeight());
        msAssert.areEqual(72.0, imageSize.getHorizontalResolution());
        msAssert.areEqual(72.0, imageSize.getVerticalResolution());
    }

    @Test
    public void testGif() throws Exception
    {
        ImageSizeCore imageSize = ImageUtil.getImageSize(TestFxUtil.buildTestFileName("Images\\AWReference.gif"));

        msAssert.areEqual(467, imageSize.getWidth());
        msAssert.areEqual(382, imageSize.getHeight());
    }

    /// <summary>
    /// WORDSNET-4239 Cannot insert PNG image, error Unable to read beyond the end of the stream.
    /// </summary>
    @Test
    public void testDefect4239() throws Exception
    {
        ImageSizeCore imageSize = ImageUtil.getImageSize(TestFxUtil.buildTestFileName("Images\\TestDefect4239.png"));
        msAssert.areEqual(1200, imageSize.getWidth());
        msAssert.areEqual(500, imageSize.getHeight());
    }

    @Test
    public void testTiffMacByteOrder() throws Exception
    {
        ImageSizeCore imageSize = ImageUtil.getImageSize(TestFxUtil.buildTestFileName("Images\\TestTiffMacByteOrder.tif"));

        msAssert.areEqual(123, imageSize.getWidth());
        msAssert.areEqual(321, imageSize.getHeight());
        msAssert.areEqual(72.0, imageSize.getHorizontalResolution());
        msAssert.areEqual(72.0, imageSize.getVerticalResolution());
    }

    @Test
    public void testTiffIBMByteOrder() throws Exception
    {
        ImageSizeCore imageSize = ImageUtil.getImageSize(TestFxUtil.buildTestFileName("Images\\TestTiffIBMByteOrder.tif"));

        msAssert.areEqual(123, imageSize.getWidth());
        msAssert.areEqual(321, imageSize.getHeight());
        msAssert.areEqual(72.0, imageSize.getHorizontalResolution());
        msAssert.areEqual(72.0, imageSize.getVerticalResolution());
    }

    /// <summary>
    /// Fixed. GetImageSize returns negative value.
    /// </summary>
    @Test
    public void testDefect9161() throws Exception
    {
        ImageSizeCore imageSize = ImageUtil.getImageSize(TestFxUtil.buildTestFileName("Rendering\\Aps\\TestImages\\TestImage FormatBmp24bitR8G8B8FlipRows.bmp"));

        msAssert.areEqual(100, imageSize.getWidth());
        msAssert.areEqual(100, imageSize.getHeight());
    }

    @Test
    public void testJpegSize() throws Exception
    {
        ImageSizeCore imageSize = ImageUtil.getImageSize(TestFxUtil.buildTestFileName("Images\\TestJpegSize.jpg"));

        msAssert.areEqual(196, imageSize.getWidth());
        msAssert.areEqual(266, imageSize.getHeight());
        msAssert.areEqual(96.0, imageSize.getHorizontalResolution());
        msAssert.areEqual(96.0, imageSize.getVerticalResolution());
    }

    /// <summary>
    /// WORDSNET-11932 "DivideByZeroException" exception occurs, when try to insert image into a document.
    /// The problem occurred because denominator in the image is zero, so DivideByZeroException exception occurs.
    /// Fixed by adding a condition to avoid dividing by zero.
    /// </summary>
    @Test
    public void testDefect11932() throws Exception
    {
        ImageSizeCore imageSize = ImageUtil.getImageSize(TestFxUtil.buildTestFileName("Images\\TestDefect11932.jpg"));
        msAssert.areEqual(ImageConstants.STANDARD_RESOLUTION, (float)imageSize.getHorizontalResolution());
        msAssert.areEqual(ImageConstants.STANDARD_RESOLUTION, (float)imageSize.getVerticalResolution());
        msAssert.areEqual(408, imageSize.getWidth());
        msAssert.areEqual(306, imageSize.getHeight());
    }

    /// <summary>
    /// WORDSNET-5142 Image size is incorrect during converting to XPS.
    /// The problem occurred because resolution read from Exif is 0 dpi, there is no actualy value of resolution.
    /// So default resolution was used. To resolve the problem check whether values read from Exif are not zeros.
    /// </summary>
    @Test
    public void testDefectJira5142() throws Exception
    {
        ImageSizeCore imageSize = ImageUtil.getImageSize(TestFxUtil.buildTestFileName("Images\\TestDefectJira5142.jpg"));
        msAssert.areEqual(300f, (float)imageSize.getHorizontalResolution());
        msAssert.areEqual(300f, (float)imageSize.getVerticalResolution());
        msAssert.areEqual(604, imageSize.getWidth());
        msAssert.areEqual(660, imageSize.getHeight());
    }

    @Test (dataProvider = "testIsSvgTrueDataProvider")
    public void testIsSvgTrue(String svgString)
    {
        msAssert.isTrue(checkSvgString(svgString));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testIsSvgTrueDataProvider")
	public static Object[][] testIsSvgTrueDataProvider()
	{
		return new Object[][]
		{
			{"<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"200\" height=\"200\"></svg>"},
			{" \t<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"200\" height=\"200\"></svg>"},
			{"<?xml version='1.0' encoding='UTF-8' standalone='no'?><svg xmlns=\"http://www.w3.org/2000/svg\"></svg>"},
			{"<?xml version='1.0' encoding='UTF-8' standalone='no'?><!DOCTYPE svg><svg xmlns=\"http://www.w3.org/2000/svg\"></svg>"},
		};
	}

    @Test (dataProvider = "testIsSvgFalseDataProvider")
    // HTML document with an embedded SVG node should not be considered an SVG image itself.
    public void testIsSvgFalse(String svgContent)
    {
        msAssert.isFalse(checkSvgString(svgContent));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testIsSvgFalseDataProvider")
	public static Object[][] testIsSvgFalseDataProvider()
	{
		return new Object[][]
		{
			{""},
			{" "},
			{"\t\t"},
			{"this is simple text"},
			{"http://www.w3.org/2000/svg"},
			{"<html><div><svg xmlns='http://www.w3.org/2000/svg'></svg></div></html>"},
		};
	}

    /// <summary>
    /// WORDSNET-10356 DocumentBuilder.InsertImage lose original size of image.
    /// Image resolution was read from Exif in centimeters, but we always store it in the model in inches.
    /// </summary>
    @Test
    public void testJira10356() throws Exception
    {
        ImageSizeCore imageSize = ImageUtil.getImageSize(TestFxUtil.buildTestFileName("Images\\TestJira10356.jpg"));

        // The resolution of this image should be 93.98 inches (37 centimeters).
        final double RESOLUTION_INCH = 93.98;
        msAssert.isTrue(MathUtil.areEqual(RESOLUTION_INCH, imageSize.getHorizontalResolution(), 0.01));
        msAssert.isTrue(MathUtil.areEqual(RESOLUTION_INCH, imageSize.getVerticalResolution(), 0.01));
    }

    /// <summary>
    /// WORDSJAVA-1650 Converting png with alpha to bmp throws IndexOutOfBoundsException.
    /// </summary>
    @Test
    public void testJira1650() throws Exception
    {
        // Input
        byte[] imageBytes = StreamUtil.copyFileToByteArray(TestFxUtil.buildTestFileName("Images\\TestJira1650.png"));
        BitmapPal bitmap = new BitmapPal(imageBytes);

        // Output
        MemoryStream outputSgtream = new MemoryStream();
        bitmap.saveBmp(outputSgtream);
    }

    /// <summary>
    /// WORDSNET-20272 Some format detection ("IsXxx)" methods raised exceptions if there were not enough input data.
    /// All these methods are expected to return <c>false</c> in this case.
    /// </summary>
    @Test
    public void test20272IsXxx() throws Exception
    {
        byte[] data = { (byte) 0x00 };
        MemoryStream stream = new MemoryStream(data);
        try /*JAVA: was using*/
        {
            msAssert.isFalse(ImageUtil.isTiff(stream));
            stream.setPosition(0);

            msAssert.isFalse(ImageUtil.isJpeg(stream));
            stream.setPosition(0);

            msAssert.isFalse(ImageUtil.isPng(stream));
            stream.setPosition(0);

            msAssert.isFalse(ImageUtil.isBmp(stream));
            stream.setPosition(0);

            msAssert.isFalse(ImageUtil.isPict(stream));
            stream.setPosition(0);

            msAssert.isFalse(ImageUtil.isIco(stream));
            stream.setPosition(0);
        }
        finally { if (stream != null) stream.close(); }
        msAssert.isFalse(ImageUtil.isDib(data));
    }

    /// <summary>
    /// Returns true if the specified string is SVG.
    /// </summary>
    private static boolean checkSvgString(String data)
    {
        return ImageUtil.isSvg(Encoding.getUTF8().getBytes(data));
    }

    @Test (dataProvider = "testIsCmykDataProvider")
    // This image causes problems while reading.



    // WORDSNET-24845 Some images detected as PixelFormat.PixelFormat32bppCMYK in GDI and caused an issue in SkiaSharp.
    public void testIsCmyk(String imageFileName, boolean expectedValue) throws Exception
    {
        String testImageFileName = TestFxUtil.buildTestFileName(imageFileName);

        msAssert.areEqual(expectedValue, ImageUtil.isCmykOrYCCK(File.readAllBytes(testImageFileName)));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testIsCmykDataProvider")
	public static Object[][] testIsCmykDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Images\\CMYK\\Test18764_0.jpeg",  true},
			{"Images\\CMYK\\Test18764_1.jpeg",  true},
			{"Images\\CMYK\\Test420CMYKImage_0.jpeg",  true},
			{"Images\\CMYK\\TestCMYK517_0.jpeg",  true},
			{"Images\\CMYK\\TestCmykImage.jpeg",  true},
			{"Images\\CMYK\\TestCmykImageDefectJ21_0.jpeg",  true},
			{"Images\\CMYK\\TestCmykImageDefectJ422_0.jpeg",  true},
			{"Images\\CMYK\\TestCmykImagePhotoshop.jpeg",  true},
			{"Images\\CMYK\\TestCMYKImages_0.jpeg",  true},
			{"Images\\CMYK\\TestCMYKImages_1.jpeg",  true},
			{"Images\\CMYK\\TestCMYKImages_10.jpeg",  true},
			{"Images\\CMYK\\TestCMYKImages_11.jpeg",  true},
			{"Images\\CMYK\\TestCMYKImages_12.jpeg",  true},
			{"Images\\CMYK\\TestCMYKImages_2.jpeg",  true},
			{"Images\\CMYK\\TestCMYKImages_3.jpeg",  true},
			{"Images\\CMYK\\TestCMYKImages_4.jpeg",  true},
			{"Images\\CMYK\\TestCMYKImages_5.jpeg",  true},
			{"Images\\CMYK\\TestCMYKImages_6.jpeg",  true},
			{"Images\\CMYK\\TestCMYKImages_7.jpeg",  true},
			{"Images\\CMYK\\TestCMYKImages_8.jpeg",  true},
			{"Images\\CMYK\\TestCMYKImages_9.jpeg",  true},
			{"Images\\CMYK\\TestCMYKJ595_0.jpeg",  true},
			{"Images\\CMYK\\TestCMYKJ595_1.jpeg",  true},
			{"Images\\CMYK\\TestDefect23947_1.jpeg",  true},
			{"Images\\CMYK\\TestDefect5841.jpeg",  true},
			{"Images\\CMYK\\TestDefect6728.jpeg",  true},
			{"Images\\CMYK\\TestJira13620.jpeg",  true},
			{"Images\\CMYK\\TestJira5366_0.jpeg",  true},
			{"Images\\CMYK\\TestJira6368_1.jpeg",  true},
			{"Images\\CMYK\\TestJira6368_2.jpeg",  true},
			{"Images\\CMYK\\TestJira6409_0.jpeg",  true},
			{"Images\\CMYK\\TestJira7626_1.jpeg",  true},
			{"Images\\CMYK\\TestJira8459_0.jpeg",  true},
			{"Images\\CMYK\\TestJiraJ883.jpg",  true},
			{"Images\\CMYK\\TestShapeColorChanged489_0.jpeg",  true},
			{"Images\\CMYK\\TestJira6573_0.jpeg",  false},
			{"Rendering\\Aps\\TestImages\\TestImage Format24bppRgb.jpg",  false},
			{"Rendering\\Aps\\TestImages\\TestImage FormatJpegBaseline.jpg",  false},
			{"Rendering\\Aps\\TestImages\\TestImage FormatJpegOptimizedHuffman.jpg",  false},
			{"Rendering\\Aps\\TestImages\\TestImage FormatJpegProgressive3Scans.jpg",  false},
			{"Images\\TestDefect1463 2.jpg",  false},
			{"Images\\TestDefect1463.jpg",  false},
			{"Images\\TestDefect11932.jpg",  false},
			{"Images\\TestDefectJira5142.jpg",  false},
			{"Images\\TestJira10356.jpg",  false},
			{"Images\\TestJpegSize.jpg",  false},
			{"Images\\TestWrongHeight.jpg",  false},
			{"Images\\CMYK\\TestPixelFormat32bppCMYK.jpg",  true},
			{"Images\\CMYK\\Test24845.jpg",  true},
		};
	}
}

