// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/04/2025 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown hard line break feature.
/// </summary>
public class TestHardLineBreakParser extends TestMarkdownParserBase
{
    @BeforeClass (alwaysRun = true)
    public void testFixtureSetUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests simple hard line break with 2 spaces.
    /// </summary>
    @Test
    public void testHardLineBreakA() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreakA.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "foo"+HARD_BREAK_SPACES+"baz");
    }

    /// <summary>
    /// Tests simple hard line break with backslash '\'.
    /// </summary>
    @Test
    public void testHardLineBreakB() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreakB.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "foo"+HARD_BREAK_SLASH+"baz");
    }


    /// <summary>
    /// Tests that more than two spaces can be used.
    /// </summary>
    @Test
    public void testHardLineBreakC() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreakC.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "foo"+HARD_BREAK_SPACES+"baz");
    }

    /// <summary>
    /// Tests that leading spaces at the beginning of the next line are ignored in line break with two spaces.
    /// </summary>
    @Test
    public void testHardLineBreakD() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreakD.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "foo"+ HARD_BREAK_SPACES +" bar");
    }


    /// <summary>
    /// Tests that leading spaces at the beginning of the next line are ignored in line break with backslash.
    /// </summary>
    @Test
    public void testHardLineBreakE() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreakE.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "foo"+HARD_BREAK_SLASH+" bar");
    }

    /// <summary>
    /// Tests that hard line breaks can occur inside emphasis, links, and other constructs that allow inline content.
    /// </summary>
    /// <remarks>Line break with two spaces.</remarks>
    @Test
    public void testHardLineBreakF() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreakF.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "*foo"+ HARD_BREAK_SPACES +"bar*");
        checkItalicInline(((ParagraphBlock)doc.get(0)).get(0), "*foo"+HARD_BREAK_SPACES+"bar*");
    }

    /// <summary>
    /// Tests that hard line breaks can occur inside emphasis, links, and other constructs that allow inline content.
    /// </summary>
    /// <remarks>Line break with backslash.</remarks>
    @Test
    public void testHardLineBreakG() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreakG.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "*foo"+HARD_BREAK_SLASH+"bar*");
        checkItalicInline(((ParagraphBlock)doc.get(0)).get(0), "*foo"+HARD_BREAK_SLASH+"bar*");
    }

    /// <summary>
    /// Tests that hard line breaks do not occur inside code spans.
    /// </summary>
    /// <remarks>Line break with two spaces.</remarks>
    @Test
    public void testHardLineBreakH() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreakH.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "`code   span`");
    }

    /// <summary>
    /// Tests that hard line breaks do not occur inside code spans.
    /// </summary>
    /// <remarks>Line break with backslash.</remarks>
    @Test
    public void testHardLineBreakI() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreakI.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "`code\\ span`");
    }


    /// <summary>
    /// Tests that hard line breaks do not occur inside HTML tags.
    /// </summary>
    /// <remarks>Line break with two spaces.</remarks>
    @Test
    public void testHardLineBreakJ() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreakJ.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "<a href=\"foo"+HARD_BREAK_SPACES+"bar\">");
    }


    /// <summary>
    /// Tests that hard line breaks do not occur inside HTML tags.
    /// </summary>
    /// <remarks>Line break with backslash.</remarks>
    @Test
    public void testHardLineBreakK() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreakK.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "<a href=\"foo"+HARD_BREAK_SLASH+"bar\">");
    }

    /// <summary>
    /// Tests that neither syntax for hard line breaks works at the end
    /// of a paragraph or other block element.
    /// </summary>
    @Test
    public void testHardLineBreakL() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreakL.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "foo\\");
    }

    /// <summary>
    /// Tests that neither syntax for hard line breaks works at the end
    /// of a paragraph or other block element.
    /// </summary>
    @Test
    public void testHardLineBreakM() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreakM.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "foo");
    }

    /// <summary>
    /// Tests that neither syntax for hard line breaks works at the end
    /// of a paragraph or other block element.
    /// </summary>
    @Test
    public void testHardLineBreakN() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreakN.md");

        msAssert.areEqual(1, doc.getCount());
        checkAtxHeading(doc.get(0), 3, "foo\\");
    }

    /// <summary>
    /// Tests that neither syntax for hard line breaks works at the end
    /// of a paragraph or other block element.
    /// </summary>
    @Test
    public void testHardLineBreakO() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreakO.md");

        msAssert.areEqual(1, doc.getCount());
        checkAtxHeading(doc.get(0), 3, "foo");
    }

    /// <summary>
    /// Tests hard break with whitespace lines.
    /// </summary>
    @Test
    public void testHardLineBreak1() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreak1.md");

        msAssert.areEqual(2, doc.getCount());
        checkIndentedCode(doc.get(0), "\\");
        checkParagraph(doc.get(1), HARD_BREAK_SLASH+"\\");
    }

    /// <summary>
    /// Tests single backslash in file.
    /// </summary>
    @Test
    public void testHardLineBreak2() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreak2.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "\\");
    }

    /// <summary>
    /// Tests two lines with only one backslash in each.
    /// </summary>
    @Test
    public void testHardLineBreak3() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreak3.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), HARD_BREAK_SLASH + "\\");
    }

    /// <summary>
    /// Tests escaped backslash.
    /// </summary>
    @Test
    public void testHardLineBreak4() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreak4.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "abc\\" + SOFT_BREAK + "def");
    }

    /// <summary>
    /// Tests two lines.
    /// </summary>
    @Test
    public void testHardLineBreak5() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreak5.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "abc"+HARD_BREAK_SLASH+"def");
    }

    /// <summary>
    /// Tests backslash hard line with backslash before it followed by space.
    /// </summary>
    @Test
    public void testHardLineBreak6() throws Exception
    {
        MarkdownDocument doc = open("TestHardLineBreak6.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "abc\\ "+HARD_BREAK_SLASH+"def");
    }

    /// <summary>
    /// Returns Markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return open(fileName, null);
    }

    /// <summary>
    /// Returns Markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName, MarkdownLoadOptions loadOptions) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\HardLineBreak\\{0}", fileName), loadOptions);
    }
}

