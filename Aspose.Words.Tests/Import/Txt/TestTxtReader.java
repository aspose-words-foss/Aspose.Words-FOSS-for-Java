// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import com.aspose.ms.System.Text.Encoding;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.FileAccess;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.Language;
import com.aspose.SystemPal;
import com.aspose.ms.System.msString;
import java.awt.Color;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msMath;
import com.aspose.ms.System.IO.StreamReader;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests import of various text files.
/// </summary>
@Test
public class TestTxtReader
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// WORDSNET-9958 Text document with form feeds/page breaks is not rendering to PDF correctly
    /// </summary>
    @Test
    public void testJira9958() throws Exception
    {
        LoadOptions loadOptions = new LoadOptions();
        loadOptions.setLoadFormat(LoadFormat.TEXT);
        loadOptions.setEncodingInternal(Encoding.getASCII());
        Document doc = TestUtil.open("ImportTxt\\TestJira9958.txt", loadOptions);
        msAssert.areEqual(4, doc.getSections().getCount());
    }

    @Test
    public void testReadSimpleParagraph() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\SimplePara1", LoadFormat.TEXT);

        checkParagraph(doc.getSections().get(0).getBody().getFirstChild(), "Line1 line1 line1\r", 0, 0);
        checkParagraph(doc.getSections().get(0).getBody().getFirstChild().getNextSibling(), "line2\f", 0, 0);
    }

    @Test
    public void testReadSimpleParagraph2() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\SimplePara2", LoadFormat.TEXT);

        Node n1 = doc.getSections().get(0).getBody().getFirstChild();
        checkParagraph(n1, "Line1 line1 line1\r", 0, 2);

        Node n2 = n1.getNextSibling();
        checkParagraph(n2, "line2\r", 0, 0);

        checkParagraph(n2.getNextSibling(), "Line3\f", 0, 3);
    }

    @Test
    public void testReadSimpleParagraph3() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\SimplePara3.txt");

        Node n1 = doc.getSections().get(0).getBody().getFirstChild();
        checkParagraph(n1, "Line1 line1 line1\r", 0, 3);

        Node n2 = n1.getNextSibling();
        checkParagraph(n2, "line2\r", 0, 3);

        checkParagraph(n2.getNextSibling(), "Line3 line3 line3\f", 0, 5);
    }

    @Test
    public void testReadSimpleParagraph5() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\SimplePara4.txt");

        Node n1 = doc.getSections().get(0).getBody().getFirstChild();
        checkParagraph(n1, "Line1 line1 line1\r", 0, 0);

        Node n2 = n1.getNextSibling();
        checkParagraph(n2, "line2\r", 0, 0);

        Node n3 = n2.getNextSibling();
        checkParagraph(n3, "\r", 0, 0);

        Node n4 = n3.getNextSibling();
        checkParagraph(n4, "Line3 line3 line3\r", 0, 0);

        checkParagraph(n4.getNextSibling(), "line3\f", 0, 0);
    }

    @Test
    public void testReadDefect1() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\Defect1", LoadFormat.TEXT);

        checkParagraph(doc.getSections().get(0).getBody().getFirstChild(), null, 0, 12);
    }

    /// <summary>
    /// Some of text documents contain numbering like:
    /// 1. some text1
    /// 2. some text2
    ///   2.1 some text 3  -  dot character is missed after 1 in this numbering
    /// </summary>
    @Test
    public void testReadArabicNumberingWithoutTrailingDot() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\NumberingWOTrailingDot", LoadFormat.TEXT);

        Node n1 = doc.getSections().get(0).getBody().getFirstChild();
        checkNumbering(n1, NumberStyle.ARABIC, 0, null);
        msAssert.areEqual(n1.getText(), "Line1\r");

        Node n2 = n1.getNextSibling();
        checkNumbering(n2, NumberStyle.ARABIC, 1, null);
        msAssert.areEqual(n2.getText(), "Line2\r");
    }

    /// <summary>
    /// Defect: line of several '*' is detected as bullet
    /// </summary>
    @Test
    public void testReadAsteriskLineDelimiter() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\AsteriskLineDelimiter", LoadFormat.TEXT);

        msAssert.isFalse(doc.getSections().get(0).getBody().getFirstParagraph().isListItem());
    }

    @Test
    public void testReadDelimiterLine() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\DelimiterLine", LoadFormat.TEXT);

        Node n1 = doc.getSections().get(0).getBody().getFirstChild();
        checkParagraph(n1, "License\r", 0, 0);

        Node n2 = n1.getNextSibling();
        checkParagraph(n2, "=======\r", 0, 0);

        Node n3 = n2.getNextSibling();
        checkParagraph(n3, "Some text\f", 0, 0);
    }

    @Test
    public void testReadBulletWithDifferentIndents() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\BulletWithDifferentIndents", LoadFormat.TEXT);

        Node n1 = doc.getSections().get(0).getBody().getFirstParagraph();
        ListFormat f1 = checkNumbering(n1, NumberStyle.BULLET, 0, null);
        msAssert.areEqual(n1.getText(), "line1\r");

        Node p2 = n1.getNextSibling();
        ListFormat f2 = checkNumbering(p2, NumberStyle.BULLET, 0, f1);
        msAssert.areEqual(p2.getText(), "line2\r");

        Node p3 = p2.getNextSibling();
        ListFormat f3 = checkNumbering(p3, NumberStyle.BULLET, 0, null);
        msAssert.isFalse(f3.getListLevel() == f2.getListLevel());
        msAssert.areEqual(p3.getText(), "line 3\r");

        Node p4 = p3.getNextSibling();
        checkNumbering(p4, NumberStyle.BULLET, 0, f3);
        msAssert.areEqual(p4.getText(), "line 4\f");
    }


    @Test
    public void testReadNumberingLevels() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\NumberingLevels", LoadFormat.TEXT);

        Node p1 = doc.getSections().get(0).getBody().getFirstChild();
        ListFormat f1 = checkNumbering(p1, NumberStyle.ARABIC, 0, null);

        Node p2 = p1.getNextSibling();
        ListFormat f2 = checkNumbering(p2, NumberStyle.ARABIC, 1, null);

        Node p3 = p2.getNextSibling();
        checkNumbering(p3, NumberStyle.ARABIC, 1, f2);

        Node p4 = p3.getNextSibling();
        checkNumbering(p4, NumberStyle.ARABIC, 0, f1);

        Node p5 = p4.getNextSibling();
        ListFormat f5 = checkNumbering(p5, NumberStyle.ARABIC, 0, null);
        msAssert.isFalse(f5.getListLevel() == f1.getListLevel());

        Node p6 = p5.getNextSibling();
        ListFormat f6 = checkNumbering(p6, NumberStyle.ARABIC, 1, null);
        msAssert.isFalse(f6.getListLevel() == f2.getListLevel());

        Node p7 = p6.getNextSibling();
        checkNumbering(p7, NumberStyle.ARABIC, 1, f6);

        Node p8 = p7.getNextSibling();
        checkNumbering(p8, NumberStyle.ARABIC, 1, f6);

        Node p9 = p8.getNextSibling();
        checkNumbering(p9, NumberStyle.ARABIC, 0, f5);
    }

    @Test
    public void testReadNumberingLatin1() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\NumberingLatin1", LoadFormat.TEXT);

        Node p1 = doc.getSections().get(0).getBody().getFirstChild();
        ListFormat f1 = checkNumbering(p1, NumberStyle.LOWERCASE_LETTER, 0, null);
        msAssert.areEqual(p1.getText(), "line 1\r");

        Node p2 = p1.getNextSibling();
        ListFormat f2 = checkNumbering(p2, NumberStyle.LOWERCASE_LETTER, 0, f1);
        msAssert.areEqual(p2.getText(), "line 2\r");

        Node p3 = p2.getNextSibling();
        checkNumbering(p3, NumberStyle.LOWERCASE_LETTER, 0, f2);
        msAssert.areEqual(p3.getText(), "line 3\f");
    }


    /// <summary>
    /// WORDSNET-8072 An ArgumentNullException exception occurs if encoding is null.
    /// Encoding can be null now, it will trigger autodetection.
    /// </summary>
    @Test
    public void testJira8072() throws Exception
    {
        Document doc = new Document();
        Stream stream = new FileStream(TestUtil.buildTestFileName("ImportTxt\\TestJira8072.txt"), FileMode.OPEN, FileAccess.READ);
        try /*JAVA: was using*/
        {
            TxtReader txtReader = new TxtReader(stream, doc, null);
            txtReader.read();
        }
        finally { if (stream != null) stream.close(); }
        Paragraph para = ms.as(doc.getChild(NodeType.PARAGRAPH, 0, true), Paragraph.class);
        msAssert.isNotNull(para);
        msAssert.areEqual("sometext:<<foo>>\r", para.getText());
    }

    /// <summary>
    /// WORDSNET-7677 Line breaks issue in text files.
    /// When a text file is loaded, single line breaks disappear from the output file.
    /// </summary>
    @Test
    public void testJira7677() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\TestJira7677.txt");

        Node n1 = doc.getSections().get(0).getBody().getFirstChild();
        checkParagraph(n1, "Line 1  dfdf\r", 0, 0);

        Node n2 = n1.getNextSibling();
        checkParagraph(n2, "Line 2 dfdf\r", 0, 0);

        Node n3 = n2.getNextSibling();
        checkParagraph(n3, "\r", 0, 0);

        Node n4 = n3.getNextSibling();
        checkParagraph(n4, "Line 4 fdgffgds\r", 0, 0);

        Node n5 = n4.getNextSibling();
        checkParagraph(n5, "\r", 0,0);

        Node n6 = n5.getNextSibling();
        checkParagraph(n6, "\r",0 ,0);

        Node n7 = n6.getNextSibling();
        checkParagraph(n7, "Line 7 fdgfdgfdg\r", 0, 0);

        Node n8 = n7.getNextSibling();
        checkParagraph(n8, "Line 8\r", 0, 0);

        Node n9 = n8.getNextSibling();
        checkParagraph(n9, "Line 9 sdfhsdkfsd\f", 0, 0);
    }

    /// <summary>
    /// WORDSNET-10456 Txt to Pdf/Doc/Docx/Txt convesion issue with number text.
    /// Search for previous numbered paragraph was performed beyond the last numbered list.
    /// </summary>
    @Test
    public void testJira10456() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\TestJira10456.txt");

        Node p1 = doc.getSections().get(0).getBody().getFirstChild();
        msAssert.areEqual(p1.getText(), "A\r");
        ListFormat f1 = checkNumbering(p1, NumberStyle.ARABIC, 0, null);

        Node p2 = p1.getNextSibling();
        msAssert.areEqual(p2.getText(), "B\r");
        checkNumbering(p2, NumberStyle.ARABIC, 0, f1);

        Node p3 = p2.getNextSibling();
        msAssert.areEqual(p3.getText(), "C\r");
        checkNumbering(p3, NumberStyle.ARABIC, 0, f1);

        Node p4 = p3.getNextSibling();
        msAssert.areEqual(p4.getText(), "D\r");
        checkNumbering(p4, NumberStyle.ARABIC, 0, f1);

        Node p5 = p4.getNextSibling();
        msAssert.areEqual(p5.getText(), "E\r");
        ListFormat f5 = checkNumbering(p5, NumberStyle.ARABIC, 0, null);

        Node p6 = p5.getNextSibling();
        msAssert.areEqual(p6.getText(), "F\r");
        checkNumbering(p6, NumberStyle.ARABIC, 0, f5);

        Node p7 = p6.getNextSibling();
        msAssert.areEqual(p7.getText(), "G\r");
        ListFormat f7 = checkNumbering(p7, NumberStyle.ARABIC, 0, null);

        Node p8 = p7.getNextSibling();
        msAssert.areEqual(p8.getText(), "H 3. I\r");
        checkNumbering(p8, NumberStyle.ARABIC, 0, f7);

        Node p9 = p8.getNextSibling();
        msAssert.areEqual("4. J\r", p9.getText());
        Paragraph paragraph9 = (Paragraph)p9;
        msAssert.isFalse(paragraph9.isListItem());

        Node p10 = p9.getNextSibling();
        msAssert.areEqual("4. K\f", p10.getText());
        Paragraph paragraph10 = (Paragraph)p10;
        msAssert.isFalse(paragraph10.isListItem());
    }

    /// <summary>
    /// WORDSNET-10612 Aspose.Word incorrectly loads floating point values into DOM.
    /// AW incorrectly detects list numbers for ArabicDotTxtNumberingStyle,
    /// if input string after list item number has no any symbols.
    /// </summary>
    @Test
    public void testJira10612() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\TestJira10612.txt");

        // Check each paragraph in the input file: is it a list number and has it a right text?
        // Input file is the customer one, with some related special cases added.

        Node p0 = doc.getSections().get(0).getBody().getFirstChild();
        checkParagraph(p0, "Test list\r", 0, 0);

        Node p1 = p0.getNextSibling();
        msAssert.areEqual("test 1\r", p1.getText());
        ListFormat f1 = checkNumbering(p1, NumberStyle.ARABIC, 0, null);

        Node p2 = p1.getNextSibling();
        msAssert.areEqual("test 2\r", p2.getText());
        checkNumbering(p2, NumberStyle.ARABIC, 0, f1);

        Node p3 = p2.getNextSibling();
        msAssert.areEqual("\r", p3.getText());
        ListFormat f3 = checkNumbering(p3, NumberStyle.ARABIC, 1, null);

        Node p4 = p3.getNextSibling();
        msAssert.areEqual("\r", p4.getText());
        checkNumbering(p4, NumberStyle.ARABIC, 1, f3);

        Node p5 = p4.getNextSibling();
        msAssert.areEqual("test 3\r", p5.getText());
        checkNumbering(p5, NumberStyle.ARABIC, 0, null);

        Node p6 = p5.getNextSibling();
        checkParagraph(p6, "4\r", 0, 0);

        Node p7 = p6.getNextSibling();
        checkParagraph(p7, "2.91\r", 0, 0);

        Node p8 = p7.getNextSibling();
        checkParagraph(p8, "2.91 text\r", 0, 0);

        Node p9 = p8.getNextSibling();
        checkParagraph(p9, "FLOATING POINT NUMBER:\r", 0, 0);

        Node p10 = p9.getNextSibling();
        checkParagraph(p10, "2.91 2.91\r", 0, 0);

        Node p11 = p10.getNextSibling();
        checkParagraph(p11, "2.91\f", 0, 0);
    }



    /// <summary>
    /// WORDSNET-13550 Wrong runs were generated on importing Hebrew text with numbers and
    /// number separators.
    /// </summary>
    @Test
    public void testHebrewText() throws Exception
    {
        LoadOptions options = new LoadOptions();
        options.setLoadFormat(LoadFormat.TEXT);
        options.setEncodingInternal(Encoding.getEncoding("DOS-862"));

        Document doc = TestUtil.open("ImportTxt\\TestJira13550HebrewDOS862.txt", options);

        checkBidiRuns(doc, false,
            "01020100104000007.00000000000.00",
            "רטוקב םיצוק תרדחהו מ\"מ 8 רטוקב חודיק תוברל ,תוספרמב תונירטיול תחתמ ןכו םיבוטר םירוזאב תוציחמ תיתחתב מ\"ס 20 דע הבוגבו מ\"ס 10-20 בחורב 20-ב ןוטב תורוגח");
    }


    /// <summary>
    /// WORDSNET-14858 Provide an option to preserve leading and trailing spaces.
    /// Added new TxtLeadingSpacesOptions and TxtTrailingSpacesOptions enums to control behavior.
    /// </summary>
    @Test
    public void testJira14858() throws Exception
    {
        // Verify leading space options.
        TxtParagraph paragraph = readTxtParagraphFromString("   Text   ", TxtLeadingSpacesOptions.PRESERVE, TxtTrailingSpacesOptions.PRESERVE);
        verifyTxtParagraph(paragraph, "   Text   ", 0, null);

        paragraph = readTxtParagraphFromString("   Text   ", TxtLeadingSpacesOptions.TRIM, TxtTrailingSpacesOptions.PRESERVE);
        verifyTxtParagraph(paragraph, "Text   ", 0, null);

        paragraph = readTxtParagraphFromString("   Text   ", TxtLeadingSpacesOptions.CONVERT_TO_INDENT, TxtTrailingSpacesOptions.PRESERVE);
        verifyTxtParagraph(paragraph, "Text   ", 3, null);

        // Verify leading space handling with DetectNumbering.
        paragraph = readTxtParagraphFromString("   1) Text   ", TxtLeadingSpacesOptions.CONVERT_TO_INDENT, TxtTrailingSpacesOptions.PRESERVE);
        verifyTxtParagraph(paragraph, "Text   ", 6, "1)");

        paragraph = readTxtParagraphFromString("   1) Text   ", TxtLeadingSpacesOptions.PRESERVE, TxtTrailingSpacesOptions.PRESERVE);
        verifyTxtParagraph(paragraph, "   1) Text   ", 0, null);

        paragraph = readTxtParagraphFromString("1) Text   ", TxtLeadingSpacesOptions.PRESERVE, TxtTrailingSpacesOptions.PRESERVE);
        verifyTxtParagraph(paragraph, "Text   ", 3, "1)");

        // Verify trailing space options.
        paragraph = readTxtParagraphFromString("   Text   ", TxtLeadingSpacesOptions.PRESERVE, TxtTrailingSpacesOptions.TRIM);
        verifyTxtParagraph(paragraph, "   Text", 0, null);

        paragraph = readTxtParagraphFromString("   Text   ", TxtLeadingSpacesOptions.TRIM, TxtTrailingSpacesOptions.TRIM);
        verifyTxtParagraph(paragraph, "Text", 0, null);

        paragraph = readTxtParagraphFromString("   Text   ", TxtLeadingSpacesOptions.CONVERT_TO_INDENT, TxtTrailingSpacesOptions.TRIM);
        verifyTxtParagraph(paragraph, "Text", 3, null);
    }

    /// <summary>
    /// WORDSNET-15894 UTF8 encoding Text to PDF rendering issue
    /// WORDSNET-15720 DOCX to PDF conversion issue with Khmer text
    /// Check mongolian, ethiopic, runic, russian, thai, khmer fonts in docx from txt
    /// </summary>
    @Test
    public void testJira15894() throws Exception
    {
        final String TEST_STRING = "aᠠᠡᠢᠣᠤᠥᠦᠧᠨᠩb\nካለኝ\nᚳᚹᚫ\nо́и́у́\nฉันกินกระจกได้\nត្រូវបានដាក់នៅលើសន្លឹក";
        Document doc;

        MemoryStream stream = new MemoryStream(Encoding.getUTF8().getBytes(TEST_STRING));
        try /*JAVA: was using*/
    	{
            doc = new Document(stream, new LoadOptions(LoadFormat.TEXT, null, null));
    	}
        finally { if (stream != null) stream.close(); }

        SaveOptions so = SaveOptions.createSaveOptions(SaveFormat.DOCX);
        so.setUpdateAmbiguousTextFont(true);
        // Start validator to set fonts
        TestUtil.executeValidator(doc, so);

        // Check fonts in document
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(6, paras.getCount());

        // Check combined fonts
        RunCollection runs = paras.get(0).getRuns();
        msAssert.areEqual(3, runs.getCount());
        msAssert.areEqual("Courier New", runs.get(0).getRunPr().getNameAscii());
        msAssert.areEqual("Mongolian Baiti", runs.get(1).getRunPr().getNameAscii());
        msAssert.areEqual("Courier New", runs.get(2).getRunPr().getNameAscii());

        // Check different fonts on each line
        msAssert.areEqual("Nyala", paras.get(1).getRuns().get(0).getRunPr().getNameAscii());
        msAssert.areEqual("Segoe UI Historic", paras.get(2).getRuns().get(0).getRunPr().getNameAscii());
        msAssert.areEqual("Courier New", paras.get(3).getRuns().get(0).getRunPr().getNameAscii());
        msAssert.areEqual("Angsana New", paras.get(4).getRuns().get(0).getRunPr().getNameAscii());
        msAssert.areEqual("DaunPenh", paras.get(5).getRuns().get(0).getRunPr().getNameAscii());
    }

    /// <summary>
    /// WORDSNET-16047 Japanese text is imported incorrectly into DOM.
    /// Check correct japanese text determination from ASCII file.
    /// </summary>
    @Test
    public void testJira16047() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\TestJira16047.txt");
        msAssert.areEqual("頎凞", doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getText());
    }

    /// <summary>
    /// WORDSNET-16496 Incorrectly detects sinhalese and ethiopic (Amharic) languages and fonts for it.
    /// Checks ethiopic and sinhalese fonts and languages in PDF document loaded from txt.
    /// </summary>
    @Test
    public void testJira16496() throws Exception
    {
        final String TEST_STRING = "ሰማይ හෙලෝ";
        Document doc;

        MemoryStream stream = new MemoryStream(Encoding.getUnicode().getBytes(TEST_STRING));
        try /*JAVA: was using*/
    	{
            doc = new Document(stream, new LoadOptions(LoadFormat.TEXT, null, null));
    	}
        finally { if (stream != null) stream.close(); }

        SaveOptions so = SaveOptions.createSaveOptions(SaveFormat.MARKDOWN);
        so.setUpdateAmbiguousTextFont(true);
        // Start validator to set fonts
        TestUtil.executeValidator(doc, so);

        // Check fonts in document
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        // Check if run properties are correct
        RunCollection runs = paras.get(0).getRuns();

        msAssert.areEqual(3, runs.getCount());

        msAssert.areEqual("Nyala", runs.get(0).getRunPr().getNameAscii());
        msAssert.isFalse(runs.get(0).getRunPr().getComplexScript().toBool());
        msAssert.areEqual(Language.NO_PROOF, (/*Language*/int)runs.get(0).getRunPr().getLocaleIdBi());

        msAssert.areEqual("Iskoola Pota", runs.get(2).getRunPr().getNameAscii());
        msAssert.isTrue(runs.get(2).getRunPr().getComplexScript().toBool());
        msAssert.areEqual(Language.SINHALESE, (/*Language*/int)runs.get(2).getRunPr().getLocaleIdBi());
    }

    /// <summary>
    /// WORDSNET-17877 PageSetup.PaperSize returns Letter for TXT document.
    /// PageSetup.PaperSize depends on locale. Added definition by locale.
    /// </summary>
    @Test (groups = "SingleThreadGroup", dataProvider = "test17877DataProvider")
    public void test17877(String culture, /*PaperSize*/int expectedPaperSize) throws Exception
    {
        String currentCulture = SystemPal.getCurrentCultureName();
        boolean testMode = SectPr.getTestMode();

        try
        {
            SectPr.setTestMode(false);
            SystemPal.setCulture(culture);

            Document doc = TestUtil.open("ImportTxt\\Test17877.txt");
            /*PaperSize*/int actualPaperSize = doc.getFirstSection().getPageSetup().getPaperSize();

            msAssert.areEqual(expectedPaperSize, actualPaperSize);
        }
        finally
        {
            SystemPal.setCulture(currentCulture);
            SectPr.setTestMode(testMode);
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test17877DataProvider")
	public static Object[][] test17877DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"ru-RU",  PaperSize.A4},
			{"en-NZ",  PaperSize.A4},
			{"ja-JP",  PaperSize.A4},
			{"de-DE",  PaperSize.A4},
			{"en-US",  PaperSize.LETTER},
		};
	}

    /// <summary>
    /// WORDSNET-19067 Add feature to detect text direction (RTL / LTR) while import TXT document.
    /// A new load option DocumentDirection was introduced with one of the possible values Auto.
    /// </summary>
    @Test (dataProvider = "test19067DataProvider")
    public void test19067(String testName, boolean expectedAutoBidi) throws Exception
    {
        String testFile = msString.format("ImportTxt\\{0}", testName);

        /*DocumentDirection*/int[] documentDirections =
            new /*DocumentDirection*/int[] {DocumentDirection.LEFT_TO_RIGHT, DocumentDirection.RIGHT_TO_LEFT, DocumentDirection.AUTO};

        for (/*DocumentDirection*/int direction : documentDirections)
        {
            TxtLoadOptions lo = new TxtLoadOptions();
            lo.setDocumentDirection(direction);

            Document doc = TestUtil.open(testFile, lo);
            boolean isBidi = doc.getFirstSection().getBody().getFirstParagraph().getParaPr().getBidi();

            String assertMessage = msString.format("DocumentDirection = {0}", direction);
            switch (direction)
            {
                case DocumentDirection.LEFT_TO_RIGHT:
                    msAssert.isFalse(isBidi, assertMessage);
                    break;
                case DocumentDirection.RIGHT_TO_LEFT:
                    msAssert.isTrue(isBidi, assertMessage);
                    break;
                case DocumentDirection.AUTO:
                {
                    msAssert.areEqual(expectedAutoBidi, isBidi, assertMessage);
                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected document direction.");
            }
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19067DataProvider")
	public static Object[][] test19067DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Test19067Hebrew.txt",  true},
			{"Test19067Arabic.txt",  true},
			{"Test19067English.txt",  false},
			{"Test19067Russian.txt",  false},
			{"Test19067Mixed.txt",  true},
		};
	}

    /// <summary>
    /// WORDSNET-20378 Extra paragraph is created when single line TXT document is imported into Document.
    /// </summary>
    @Test
    public void test20378() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\Test20378.txt");
        msAssert.areEqual(1, doc.getFirstSection().getBody().getCount());
    }



    /// <summary>
    /// WORDSJAVA-2579 GetText() method returns text plus a list bullet when working with .txt documents consisting of a third level marked list
    /// </summary>
    @Test
    public void testJava2579() throws Exception
    {
        final String TEST_STRING = "1. Item 1\r\n   a. Item 2\r\n      i. Item 3\r\n";
        Document doc;

        MemoryStream stream = new MemoryStream(Encoding.getUTF8().getBytes(TEST_STRING));
        try /*JAVA: was using*/
    	{
            doc = new Document(stream, new LoadOptions(LoadFormat.TEXT, null, null));
    	}
        finally { if (stream != null) stream.close(); }

        String docText = msString.trim(doc.getText());

        msAssert.areEqual("Item 1\rItem 2\rItem 3", docText);
    }



    /// <summary>
    /// Relates to WORDSNET-23562
    /// Tests another customer`s input document.
    /// </summary>
    @Test (dataProvider = "test23562ADataProvider")
    public void test23562A(boolean autoNumberingDetection) throws Exception
    {
        TxtLoadOptions options = new TxtLoadOptions(); { options.setAutoNumberingDetection(autoNumberingDetection);}
        Document doc = TestUtil.open("ImportTxt\\Test23562A.txt", options);

        Paragraph para = TestUtil.getParagraphWithText(doc.getFirstSection().getBody(), "...cm....");
        msAssert.areEqual(autoNumberingDetection, para.isListItem());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test23562ADataProvider")
	public static Object[][] test23562ADataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}


    /// <summary>
    /// Relates to WORDSNET-24408
    /// Checks that .docx, .odt and .sxw formats don't fallback to .txt format when they are detected as unknown.
    /// </summary>
    @Test (expectedExceptions = UnsupportedFileFormatException.class, dataProvider = "test24408ADataProvider")
    public void test24408A(String testName) throws Exception
    {
        TestUtil.open(msString.format("ImportTxt\\{0}", testName));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test24408ADataProvider")
	public static Object[][] test24408ADataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Test24408.docx"},
			{"Test24408.odt"},
			{"Test24408.sxw"},
		};
	}


    /// <summary>
    /// Relates to WORDSNET-25529
    /// Tests <see cref="TxtLoadOptions.HyperlinksColor"/> and
    /// <see cref="TxtLoadOptions.HyperlinksUnderline"/> options.
    /// </summary>
    @Test
    public void test25529A() throws Exception
    {
        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            final String TEXT = "http://link.com";
            stream.write(Encoding.getASCII().getBytes(TEXT), 0, TEXT.length());

            // Check default values.
            TxtLoadOptions loadOptions = new TxtLoadOptions(); { loadOptions.setDetectHyperlinks(true); }
            Document doc = new Document(stream, loadOptions);

            Run run = TestUtil.getRunWithText(doc, TEXT);
            msAssert.areEqual(Color.BLUE.getRGB(), run.getFont().getColor().getRGB());
            msAssert.areEqual(Underline.SINGLE, run.getFont().getUnderline());

            // Check set custom values.
            loadOptions.setHyperlinksColor(Color.RED);
            msAssert.areEqual(loadOptions.getHyperlinksColor(), Color.RED);

            loadOptions.setHyperlinksUnderline(Underline.DOUBLE);
            msAssert.areEqual(loadOptions.getHyperlinksUnderline(), Underline.DOUBLE);

            doc = new Document(stream, loadOptions);

            run = TestUtil.getRunWithText(doc, TEXT);
            msAssert.areEqual(Color.RED.getRGB(), run.getFont().getColor().getRGB());
            msAssert.areEqual(Underline.DOUBLE, run.getFont().getUnderline());
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Copies specified file into a MemoryStream.
    /// </summary>
    /// <remarks>
    /// Not used StreamUtil.CopyFileToStream() here, to mimic customer's scenario exactly.
    /// </remarks>
    private static MemoryStream copyFileToMemoryStream(String fileName) throws Exception
    {
        FileStream fs = new FileStream(fileName, FileMode.OPEN, FileAccess.READ);
        try /*JAVA: was using*/
        {
            MemoryStream ms = new MemoryStream();
            fs.copyTo(ms);
            ms.setPosition(0);

            return ms;
        }
        finally { if (fs != null) fs.close(); }
    }

    /// <summary>
    /// Returns array of all list items text in specified document.
    /// </summary>
    private static ArrayList<String> getArrayOfListItemsText(Document doc)
    {
        ArrayList<String> listItemsText = new ArrayList<String>();
        NodeCollection paras = doc.getChildNodes(NodeType.PARAGRAPH, true);
        for (Paragraph para : (Iterable<Paragraph>) paras)
        {
            if (para.isListItem())
                msArrayList.add(listItemsText, para.getText());
        }

        return listItemsText;
    }

    /// <summary>
    /// Combines text of continuous runs with same Rtl (Bidi) flag and checks them by comparing with the expected
    /// run text array.
    /// </summary>
    private static void checkBidiRuns(Document doc, boolean expectedIsFirstRtl, /*params*/ String... expectedRunText)
    {
        NodeCollection runs = doc.getFirstSection().getBody().getChildNodes(NodeType.RUN, true);
        msAssert.less(0, runs.getCount(), "Run count must be more than 0.");
        boolean isRtl = ((Run)runs.get(0)).getFont().getBidi();
        msAssert.areEqual(expectedIsFirstRtl, isRtl, "Wrong RTL value.");

        ArrayList<String> actualRunText = new ArrayList<String>();
        StringBuilder currentText = new StringBuilder();
        for (Run run : (Iterable<Run>) runs)
        {
            if (run.getFont().getBidi() != isRtl)
            {
                msArrayList.add(actualRunText, currentText.toString());
                currentText.setLength(0);
                isRtl = !isRtl;
            }
            msStringBuilder.append(currentText, run.getText());
        }
        msArrayList.add(actualRunText, currentText.toString());

        msAssert.areEqual(expectedRunText.length, actualRunText.size(), "Wrong run count.");
        for (int i = 0; i < expectedRunText.length; i++)
            msAssert.areEqual(msString.trim(expectedRunText[i]), msString.trim(actualRunText.get(i)), "Wrong run text.");
    }

    private static ListFormat checkNumbering(Node node,
        /*NumberStyle*/int numberStyle,
        int listLevelNumber,
        ListFormat previousSibling)
    {
        msAssert.isInstanceOf(Paragraph.class, node);
        Paragraph p = (Paragraph)node;
        msAssert.isTrue(p.isListItem());
        msAssert.areEqual(listLevelNumber, p.getListFormat().getListLevelNumber());
        msAssert.areEqual(numberStyle, p.getListFormat().getListLevel().getNumberStyle());
        if (previousSibling != null)
        {
            msAssert.areEqual(previousSibling.getListId(), p.getListFormat().getListId());
            msAssert.areEqual(previousSibling.getListLevelNumber(), p.getListFormat().getListLevelNumber());
        }

        return p.getListFormat();
    }

    private static void checkParagraph(Node node,
        String paragraphText,
        int expectedLeftIndent,
        int expectedFirstLineIndent) throws Exception
    {
        msAssert.isInstanceOf(Paragraph.class, node);
        Paragraph p = (Paragraph)node;
        if (paragraphText != null)
            msAssert.areEqual(paragraphText, p.getText());
        TxtReader txtReader = new TxtReader(new MemoryStream(), new Document(), null);
        msAssert.areEqual(msMath.round(txtReader.getIndentInPoints(expectedLeftIndent), 0), msMath.round(p.getParagraphFormat().getLeftIndent(), 0));
        msAssert.areEqual(msMath.round(txtReader.getIndentInPoints(expectedFirstLineIndent), 0), 0, msMath.round(p.getParagraphFormat().getFirstLineIndent()));
    }

    /// <summary>
    /// Reads a text from a string and returns a TxtParagraph.
    /// </summary>
    private static TxtParagraph readTxtParagraphFromString(String text, /*TxtLeadingSpacesOptions*/int leading, /*TxtTrailingSpacesOptions*/int trailing) throws Exception
    {
        StreamReader reader = new StreamReader(new MemoryStream(Encoding.getASCII().getBytes(text)));

        TxtLoadOptions loadOptions = new TxtLoadOptions();
        loadOptions.setLeadingSpacesOptions(leading);
        loadOptions.setTrailingSpacesOptions(trailing);

        TxtParagraphReader paragraphReader = new TxtParagraphReader(reader, loadOptions);
        TxtParagraph[] paragraphs = paragraphReader.read();

        return paragraphs[0];
    }

    /// <summary>
    /// Verifies TxtParagraph properties.
    /// </summary>
    private static void verifyTxtParagraph(TxtParagraph para, String expectedText, int expectedFirstLineIndent, String numberingText)
    {
        msAssert.areEqual(expectedText, para.getText());
        msAssert.areEqual(expectedFirstLineIndent, para.getFirstLineIndent());
        if (numberingText == null)
        {
            msAssert.isNull(para.getNumbering());
        }
        else
        {
            msAssert.areEqual(numberingText, para.getNumbering().getText());
        }
    }
}

