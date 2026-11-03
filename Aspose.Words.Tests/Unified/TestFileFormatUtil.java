// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.PlatformUtilPal;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.MemoryStream;
import org.testng.annotations.DataProvider;


/// <summary>
/// Covers detection of file format, performing by <see cref="FileFormatUtil"/> class.
/// </summary>
@Test
public class TestFileFormatUtil
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests that HTML documents with different variants of empty HTML comments are detected correctly.
    /// </summary>
    @Test (dataProvider = "testDetectHtmlWithEmptyCommentDataProvider")
    public void testDetectHtmlWithEmptyComment(String comment) throws Exception
    {
        // The empty HTML comment at the beginning wasn't parsed correctly and prevented the HTML detector from
        // reading other HTML tags.
        String html = comment + "<html><p>Text</p></html>";
        checkHtmlFileFormatDetection(html);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDetectHtmlWithEmptyCommentDataProvider")
	public static Object[][] testDetectHtmlWithEmptyCommentDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"<!-->"},
			{"<!--->"},
			{"<!---->"},
		};
	}

    @Test
    public void testDetectMhtml() throws Exception
    {
        // First header in MIME heading is very long, more than initial buffer size. Will succeed from the second chance.
        checkDetectFileFormat("ImportMhtml\\DetectFileFormat\\TestDetectFileFormat LongMimeHeading.mht",
            LoadFormat.MHTML,
            Encoding.getASCII(),
            0);
    }




    /// <summary>
    /// WORDSNET-5527 Behavior changed! Now this file is considered as not Html. There are too few Html specific features.
    /// </summary>
    @Test
    public void testJira5527B() throws Exception
    {
        // No Bom, no extended code points.
        String expectedEncodingName = PlatformUtilPal.isUnixLike() ? "iso-8859-1" : "Windows-1252";
        // This edition reads neither HTML nor XML, so on both platforms a file like this is plain text.
        // (The full product detects it as XML in Java, where one tag is enough for that.)
        /*LoadFormat*/int loadFormat = LoadFormat.TEXT;
        checkDetectFileFormat("ImportHtml\\DetectFileFormat\\TestDetectFileFormat LongFirstTag.html",
            loadFormat, Encoding.getEncoding(expectedEncodingName), 0);
    }


    /// <summary>
    /// WORDSNET-7954 Content from Text file is not read during loading into DOM. The issue was in incorrect work of MhtmlDetector
    /// </summary>
    @Test
    public void testJira7954() throws Exception
    {
        String filename = TestUtil.buildTestFileName("ImportTxt/TestJira7954.txt");
        String expectedEncodingName = PlatformUtilPal.isUnixLike() ? "iso-8859-2" : "Windows-1250";
        checkDetectFileFormat(filename, LoadFormat.TEXT, Encoding.getEncoding(expectedEncodingName), 0);
    }

    /// <summary>
    /// WORDSNET-5987 Add utility methods FileFormatUtil.ContentTypeToLoadFormat(string contentType),
    /// FileFormatUtil.ContentTypeToSaveFormat(string contentType).
    /// </summary>
    @Test
    public void testContentTypeToLoadSaveFormats()
    {
        checkContentTypeToLoadSaveFormats("image/tiff", LoadFormat.UNKNOWN, SaveFormat.TIFF);
        checkContentTypeToLoadSaveFormats("image/bmp", LoadFormat.UNKNOWN, SaveFormat.BMP);
        checkContentTypeToLoadSaveFormats("image/png", LoadFormat.UNKNOWN, SaveFormat.PNG);
        checkContentTypeToLoadSaveFormats("image/gif", LoadFormat.UNKNOWN, SaveFormat.GIF);
        checkContentTypeToLoadSaveFormats("image/jpeg", LoadFormat.UNKNOWN, SaveFormat.JPEG);
        checkContentTypeToLoadSaveFormats("image/x-emf", LoadFormat.UNKNOWN, SaveFormat.EMF);
        checkContentTypeToLoadSaveFormats("application/vnd.ms-xpsdocument", LoadFormat.UNKNOWN, SaveFormat.XPS);
        checkContentTypeToLoadSaveFormats("application/pdf", LoadFormat.PDF, SaveFormat.PDF);
        checkContentTypeToLoadSaveFormats("image/svg+xml", LoadFormat.UNKNOWN, SaveFormat.SVG);
        checkContentTypeToLoadSaveFormats("application/x-mobi8-ebook", LoadFormat.AZW_3, SaveFormat.AZW_3);
        checkContentTypeToLoadSaveFormats("application/x-mobipocket-ebook", LoadFormat.MOBI, SaveFormat.MOBI);
        checkContentTypeToLoadSaveFormats("application/epub+zip", LoadFormat.EPUB, SaveFormat.EPUB);
        checkContentTypeToLoadSaveFormats("text/html", LoadFormat.HTML, SaveFormat.HTML);
        checkContentTypeToLoadSaveFormats("text/plain", LoadFormat.TEXT, SaveFormat.TEXT);
        checkContentTypeToLoadSaveFormats("application/msword", LoadFormat.DOC, SaveFormat.DOC);
        checkContentTypeToLoadSaveFormats("application/rtf", LoadFormat.RTF, SaveFormat.RTF);
        checkContentTypeToLoadSaveFormats("text/xml", LoadFormat.UNKNOWN, SaveFormat.UNKNOWN);
        checkContentTypeToLoadSaveFormats("multipart/related", LoadFormat.MHTML, SaveFormat.MHTML);
        checkContentTypeToLoadSaveFormats("application/vnd.openxmlformats-officedocument.wordprocessingml.document", LoadFormat.DOCX, SaveFormat.DOCX);
        checkContentTypeToLoadSaveFormats("application/vnd.ms-word.document.macroEnabled.12", LoadFormat.DOCM, SaveFormat.DOCM);
        checkContentTypeToLoadSaveFormats("application/vnd.openxmlformats-officedocument.wordprocessingml.template", LoadFormat.DOTX, SaveFormat.DOTX);
        checkContentTypeToLoadSaveFormats("application/vnd.ms-word.template.macroEnabled.12", LoadFormat.DOTM, SaveFormat.DOTM);
        checkContentTypeToLoadSaveFormats("application/vnd.oasis.opendocument.text", LoadFormat.ODT, SaveFormat.ODT);
        checkContentTypeToLoadSaveFormats("application/vnd.oasis.opendocument.text-template", LoadFormat.OTT, SaveFormat.OTT);
        checkContentTypeToLoadSaveFormats("application/vnd.openxmlformats-officedocument.wordprocessingml.document+xml", LoadFormat.FLAT_OPC, SaveFormat.FLAT_OPC);

        checkContentTypeToLoadSaveFormats("application/xaml+xml", LoadFormat.UNKNOWN, SaveFormat.XAML_FLOW);
        checkContentTypeToLoadSaveFormats("application/xml", LoadFormat.UNKNOWN, SaveFormat.UNKNOWN);

        checkContentTypeToLoadSaveFormats("beem", LoadFormat.UNKNOWN, SaveFormat.UNKNOWN);
        checkContentTypeToLoadSaveFormats(null, LoadFormat.UNKNOWN, SaveFormat.UNKNOWN);
    }

    @Test
    public void testImageTypeToExtension()
    {
        msAssert.areEqual(FileFormatUtil.imageTypeToExtension(ImageType.EMF), ".emf");
        msAssert.areEqual(FileFormatUtil.imageTypeToExtension(ImageType.WMF), ".wmf");
        msAssert.areEqual(FileFormatUtil.imageTypeToExtension(ImageType.PICT), ".pict");
        msAssert.areEqual(FileFormatUtil.imageTypeToExtension(ImageType.JPEG), ".jpeg");
        msAssert.areEqual(FileFormatUtil.imageTypeToExtension(ImageType.PNG), ".png");
        msAssert.areEqual(FileFormatUtil.imageTypeToExtension(ImageType.BMP), ".bmp");
        msAssert.areEqual(FileFormatUtil.imageTypeToExtension(ImageType.EPS), ".eps");
        msAssert.areEqual(FileFormatUtil.imageTypeToExtension(ImageType.WEB_P), ".webp");
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testNoImageToExtension()
    {
        msAssert.areEqual(FileFormatUtil.imageTypeToExtension(ImageType.NO_IMAGE), "");
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testUnknownImageToExtension()
    {
        msAssert.areEqual(FileFormatUtil.imageTypeToExtension(ImageType.UNKNOWN), "");
    }

    /// <summary>
    /// WORDSNET-8073 Fix FileFormatDetector to detect the file as text
    /// </summary>
    @Test
    public void testJira8073() throws Exception
    {
        String filename = TestUtil.buildTestFileName("ImportTxt/TestJira8073.txt");
        String expectedEncodingName = PlatformUtilPal.isUnixLike() ? "iso-8859-1" : "Windows-1252";
        checkDetectFileFormat(filename, LoadFormat.TEXT, Encoding.getEncoding(expectedEncodingName), 0);
    }


    @Test
    public void testJira8491() throws Exception
    {
        checkDetectFileFormat("ImportMhtml\\DetectFileFormat\\TestJira8491.mhtml", LoadFormat.MHTML, Encoding.getASCII(), 0);
    }

    /// <summary>
    /// WORDSNET-20799 FileFormatUtil.DetectFileFormat throws Aspose.Words.FileCorruptedException.
    /// Zip entry comment reading was fixed.
    /// </summary>
    @Test
    public void test20799() throws Exception
    {
        checkDetectFileFormat("Other\\Test20799.xlsx", LoadFormat.UNKNOWN, false);
    }


    /// <summary>
    /// WORDSNET-23492 FileFormatUtil.DetectFileFormat() detects some TIFF images as text.
    /// Should check images in detector explicitly to filter out.
    /// </summary>
    @Test
    public void test23492() throws Exception
    {
        checkDetectFileFormat("Other\\Test23492.tif", LoadFormat.UNKNOWN, false);
    }


    /// <summary>
    /// Relates to WORDSNET-25454
    /// Checks file format detection in addition to main test <see cref="TestOther.Test25454"/>.
    /// </summary>
    @Test
    public void test25454() throws Exception
    {
        checkDetectFileFormat("Other\\Test25454.mso", LoadFormat.UNKNOWN, false);
    }

    /// <summary>
    /// Relates to WORDSNET-25454
    /// Checks file format detection in addition to main test <see cref="TestOther.Test25454A"/>.
    /// </summary>
    @Test
    public void test25454A() throws Exception
    {
        checkDetectFileFormat("Other\\Test25454A.mso", LoadFormat.UNKNOWN, null, 0);
    }

    /// <summary>
    /// WORDSNET-26980 Increased max supported length of HTML comments in the HTML format detector.
    /// </summary>
    @Test
    public void test26980() throws Exception
    {
        checkDetectFileFormat("ImportHtml\\DetectFileFormat\\Test26980.html", LoadFormat.HTML, Encoding.getUTF8(), 0);
    }



    private static void checkDetectApplicationType(
        String fileName,
        /*ApplicationType*/int appType) throws Exception
    {
        Stream stream = File.openRead(TestUtil.buildTestFileName(fileName));
        try /*JAVA: was using*/
        {
            FileFormatInfo info = FileFormatUtil.detectFileFormat(stream);
            msAssert.areEqual(appType, info.getApplication());
        }
        finally { if (stream != null) stream.close(); }
    }

    private static void checkDetectFileFormat(
        String fileName,
        /*LoadFormat*/int expectedFormat,
        Encoding expectedEncoding,
        int expectedPosition) throws Exception
    {
        Stream stream = File.openRead(TestUtil.buildTestFileName(fileName));
        try /*JAVA: was using*/
        {
            checkDetectFileFormat(stream, expectedFormat, false, expectedEncoding, expectedPosition);
        }
        finally { if (stream != null) stream.close(); }
    }

    private static void checkDetectFileFormat(
        String fileName,
        /*LoadFormat*/int expectedFormat,
        boolean expectedEncrypted) throws Exception
    {
        Stream stream = File.openRead(TestUtil.buildTestFileName(fileName));
        try /*JAVA: was using*/
        {
            checkDetectFileFormat(stream, expectedFormat, expectedEncrypted, null, 0);
        }
        finally { if (stream != null) stream.close(); }
    }

    private static void checkHtmlFileFormatDetection(String html) throws Exception
    {
        Stream stream = new MemoryStream(Encoding.getUTF8().getBytes(html));
        try /*JAVA: was using*/
        {
            checkDetectFileFormat(stream, LoadFormat.HTML, false, Encoding.getUTF8(), 0);
        }
        finally { if (stream != null) stream.close(); }
    }

    private static void checkDetectFileFormat(
        Stream stream,
        /*LoadFormat*/int expectedFormat,
        boolean expectedEncrypted,
        Encoding expectedEncoding,
        int expectedPosition) throws Exception
    {
        FileFormatInfo info = FileFormatUtil.detectFileFormat(stream);
        msAssert.areEqual(expectedFormat, info.getLoadFormat());
        msAssert.areEqual(expectedEncrypted, info.isEncrypted());
        msAssert.areEqual(expectedEncoding, info.getEncodingInternal());
        // Check that the stream was returned to the original position or advanced to skip a particular BOM.
        msAssert.areEqual(expectedPosition, stream.getPosition());
    }

    private static void checkContentTypeToLoadSaveFormats(
        String contentType,
        /*LoadFormat*/int expectedLoadFormat,
        /*SaveFormat*/int expectedSaveFormat)
    {
        try
        {
            msAssert.areEqual(expectedLoadFormat, FileFormatUtil.contentTypeToLoadFormat(contentType));
            if (expectedLoadFormat == LoadFormat.UNKNOWN)
                Assert.fail("An exception must be thrown instead of LoadFormat.Unknown.");
        }
        catch (IllegalArgumentException e)
        {
            if (expectedLoadFormat != LoadFormat.UNKNOWN)
                Assert.fail("An exception must no be thrown is expected LoadFormat is valid.");
        }

        try
        {
            msAssert.areEqual(expectedSaveFormat, FileFormatUtil.contentTypeToSaveFormat(contentType));
            if (expectedSaveFormat == SaveFormat.UNKNOWN)
                Assert.fail("An exception must be thrown instead of SaveFormat.Unknown.");
        }
        catch (IllegalArgumentException e)
        {
            if (expectedSaveFormat != SaveFormat.UNKNOWN)
                Assert.fail("An exception must no be thrown is expected SaveFormat is valid.");
        }
    }
}

