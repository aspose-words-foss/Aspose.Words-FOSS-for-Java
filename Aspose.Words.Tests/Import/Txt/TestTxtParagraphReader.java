// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2012 by Alexey Butalov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.StreamReader;


/// <summary>
/// Tests how TxtParagraphReader detects paragraphs in various text. 
/// </summary>
@Test
public class TestTxtParagraphReader
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// WORDSNET-11467 Paragraph with big left indentation value is rendering incorrectly in PDF
    /// WORDSNET-11468 Page breaks are not preserved in PDF
    /// 
    /// Leading spaces and page break should be imported to model 
    /// </summary>
    @Test
    public void testJira11468_11468() throws Exception
    {
        final String TEXT = "                                                                     PAGE 0 OF Y\r\nPROTOCOL";
        TxtParagraphReader paragraphReader = createParagraphReader(TEXT);
        TxtParagraph[] paragraphs = paragraphReader.read();

        msAssert.areEqual(2, paragraphs.length);
        checkParagraph(paragraphs[0], 69, 0, "PAGE 0 OF Y");
        checkParagraph(paragraphs[1], 0, 0, "\fPROTOCOL");
    }

    @Test
    public void testReadParagraphs() throws Exception
    {
        final String TEXT = "line1 line1 line1\r\nline2\r\n    line3 line3\r\n line4\r\n\r\n                line6\r\nline7";
        TxtParagraphReader paragraphReader = createParagraphReader(TEXT);
        TxtParagraph[] paragraphs = paragraphReader.read();

        msAssert.areEqual(7, paragraphs.length);
        checkParagraph(paragraphs[0], 0, 0, "line1 line1 line1");
        checkParagraph(paragraphs[1], 0, 0, "line2");
        checkParagraph(paragraphs[2], 4, 0, "line3 line3");
        checkParagraph(paragraphs[3], 1, 0, "line4");
        checkParagraph(paragraphs[4], 0, 0, "");
        checkParagraph(paragraphs[5], 16, 0, "line6");
        checkParagraph(paragraphs[6], 0 ,0, "line7");
    }

    @Test
    public void testReadBullet1() throws Exception
    {
        final String TEXT = "  * line1 line1\r\n  line2\r\n  * line3 line3";
        TxtParagraphReader paragraphReader = createParagraphReader(TEXT);
        TxtParagraph[] paragraphs = paragraphReader.read();

        msAssert.areEqual(3, paragraphs.length);
        checkParagraph(paragraphs[0], 4, 0, "line1 line1");
        checkNumbering(paragraphs[0].getNumbering(), 2, null, null, "*");
        checkParagraph(paragraphs[1], 2, 0, "line2");
        checkParagraph(paragraphs[2], 4, 0, "line3 line3");
        checkNumbering(paragraphs[2].getNumbering(), 2, paragraphs[0], null, "*");
    }

    @Test
    public void testReadNumbering1() throws Exception
    {
        final String TEXT = " 1. line1 line1 \r\n\r\n 2. line2 line2";
        TxtParagraphReader paragraphReader = createParagraphReader(TEXT);
        TxtParagraph[] paragraphs = paragraphReader.read();

        msAssert.areEqual(3, paragraphs.length);
        checkParagraph(paragraphs[0], 4, 0, "line1 line1");
        checkNumbering(paragraphs[0].getNumbering(), 1, null, null, "1");
        checkParagraph(paragraphs[1], 0, 0, "");
        checkParagraph(paragraphs[2], 4, 0, "line2 line2");
        checkNumbering(paragraphs[2].getNumbering(), 1, paragraphs[0], null, "2");
    }

    @Test
    public void testReadMultilineNumbering1() throws Exception
    {
        final String TEXT = " 1. line1 line1 \r\n    line2 line2";
        TxtParagraphReader paragraphReader = createParagraphReader(TEXT);
        TxtParagraph[] paragraphs = paragraphReader.read();

        msAssert.areEqual(2, paragraphs.length);
        checkParagraph(paragraphs[0], 4, 0, "line1 line1");
        checkNumbering(paragraphs[0].getNumbering(), 1, null, null, "1");
        checkParagraph(paragraphs[1], 4, 0, "line2 line2");
    }

    @Test
    public void testReadNumberingMultilevel1() throws Exception
    {
        final String TEXT = "1. line1 line1 \r\n  1.1. line1.1\r\n  1.2. line1.2\r\n 2. line2 line2";
        TxtParagraphReader paragraphReader = createParagraphReader(TEXT);
        TxtParagraph[] paragraphs = paragraphReader.read();

        msAssert.areEqual(4, paragraphs.length);
        checkParagraph(paragraphs[0], 3, 0, "line1 line1");
        checkNumbering(paragraphs[0].getNumbering(), 0, null, null, "1");
        checkParagraph(paragraphs[1], 7, 0, "line1.1");
        checkNumbering(paragraphs[1].getNumbering(), 2, null, paragraphs[0], "1", "1");
        checkParagraph(paragraphs[2], 7, 0, "line1.2");
        checkNumbering(paragraphs[2].getNumbering(), 2, paragraphs[1], null, "1", "2");
        checkParagraph(paragraphs[3], 4, 0, "line2 line2");
        checkNumbering(paragraphs[3].getNumbering(), 1, paragraphs[0], null, "2");
    }



    /// <summary>
    /// WORDSNET-9968 The first asterisk in TXT file imported with a "Times New Roman" font.
    /// We should specify font properties for a ListLevel.
    /// </summary>
    @Test
    public void testJira9968() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\TestJira9968.txt");
        
        Font font = doc.getLists().get(0).getListLevels().get(0).getFont();

        msAssert.areEqual(font.getName(), "Courier New");
        msAssert.areEqual(font.getSize(), 10.5);
        msAssert.areEqual(font.getSizeBi(), 10.5);
    }

    private static void checkNumbering(TxtNumbering numbering,
        int numberPosition,
        TxtParagraph prevNumberingParagraph,
        TxtParagraph parentNumberingParagraph,
        /*params*/ String... numbers)
    {
        msAssert.isNotNull(numbering);
        msAssert.areEqual(numberPosition, numbering.getNumberPosition());
        msAssert.areEqual(prevNumberingParagraph, numbering.getPrevNumberingParagraph());
        msAssert.areEqual(parentNumberingParagraph, numbering.getParentNumberingParagraph());
        if (numbers.length != 0)
        {
            msAssert.areEqual(numbers.length, numbering.getNumbers().length);
            for (int i = 0; i < numbers.length; i++)
                msAssert.areEqual(numbers[i], numbering.getNumbers()[i]);
        }
    }

    private static void checkParagraph(TxtParagraph paragraph, int firstLineIndent, int leftIndent, String text)
    {
        msAssert.isNotNull(paragraph);
        msAssert.areEqual(firstLineIndent, paragraph.getFirstLineIndent());
        msAssert.areEqual(leftIndent, paragraph.getLeftIndent());
        msAssert.areEqual(text, paragraph.getText());
    }

    private static TxtParagraphReader createParagraphReader(String text) throws Exception
    {
        byte[] bytes = Encoding.getUnicode().getBytes(text);
        MemoryStream stream = new MemoryStream(bytes);
        StreamReader streamReader = new StreamReader(stream, Encoding.getUnicode());
        return new TxtParagraphReader(streamReader);
    }
}

