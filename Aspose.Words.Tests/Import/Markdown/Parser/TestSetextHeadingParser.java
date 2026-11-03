// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/09/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown SeText Heading feature.
/// </summary>
public class TestSetextHeadingParser extends TestMarkdownParserBase
{
    /// <summary>
    /// Tests simple setext heading.
    /// </summary>
    @Test
    public void testSetextHeadingA() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingA.md");
        checkSetextHeading(doc.get(0), 1, "Foo *bar*");
        checkSetextHeading(doc.get(1), 2, "Foo *baz*");
    }

    /// <summary>
    /// Tests that content of the header may span more than one line.
    /// </summary>
    @Test
    public void testSetextHeadingB() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingB.md");
        checkSetextHeading(doc.get(0), 1, "Foo *bar"+SOFT_BREAK+"baz*");
    }

    /// <summary>
    /// Tests that underlining can be any length.
    /// </summary>
    @Test
    public void testSetextHeadingC() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingC.md");
        checkSetextHeading(doc.get(0), 2, "Foo");
        checkSetextHeading(doc.get(1), 1, "Bar");
    }

    /// <summary>
    /// Tests that heading content can be indented up to three spaces, and need not line up with the underlining.
    /// </summary>
    @Test
    public void testSetextHeadingD() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingD.md");
        checkSetextHeading(doc.get(0), 2, "Foo");
        checkSetextHeading(doc.get(1), 2, "Bar");
        checkSetextHeading(doc.get(2), 1, "baz");
    }

    /// <summary>
    /// Tests that four spaces indent is too much for setext heading.
    /// </summary>
    @Test
    public void testSetextHeadingE() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingE.md");
        checkIndentedCode(doc.get(0), "Foo\u000b---\u000b\u000bbar");
        checkHorizontalRule(doc.get(1));
    }

    /// <summary>
    /// Tests that setext heading underline can be indented up to three spaces, and may have trailing spaces.
    /// </summary>
    @Test
    public void testSetextHeadingF() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingF.md");
        checkSetextHeading(doc.get(0), 2, "Foo");
    }

    /// <summary>
    /// Tests that four opening indentation spaces is too much for setext heading.
    /// </summary>
    @Test
    public void testSetextHeadingG() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingG.md");
        checkParagraph(doc.get(0), "Foo"+SOFT_BREAK+"---");
    }

    /// <summary>
    /// Tests that setext heading underline cannot contain internal spaces.
    /// </summary>
    @Test
    public void testSetextHeadingH() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingH.md");

        msAssert.areEqual(3, doc.getCount());
        checkParagraph(doc.get(0), "Foo"+SOFT_BREAK+"= =");
        checkParagraph(doc.get(1), "Bar");
        checkHorizontalRule(doc.get(2));
    }

    /// <summary>
    /// Tests that trailing spaces in the content line do not cause a line break.
    /// </summary>
    @Test
    public void testSetextHeadingI() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingI.md");

        msAssert.areEqual(1, doc.getCount());
        checkSetextHeading(doc.get(0), 2, "Foo");
    }

    /// <summary>
    /// Tests that trailing backslash in the content line do not cause a line break.
    /// </summary>
    @Test
    public void testSetextHeadingJ() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingJ.md");

        msAssert.areEqual(1, doc.getCount());
        checkSetextHeading(doc.get(0), 2, "Foo\\");
    }

    /// <summary>
    /// Tests that setext heading indicators of block structure take precedence over indicators of inline structures.
    /// </summary>
    @Test
    public void testSetextHeadingK() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingK.md");

        msAssert.areEqual(4, doc.getCount());
        checkSetextHeading(doc.get(0), 2, "`Foo");
        checkParagraph(doc.get(1), "`");
        checkSetextHeading(doc.get(2), 2, "<a title=\"a lot");
        checkParagraph(doc.get(3), "of dashes\"/>");
    }

    /// <summary>
    /// Tests that setext heading underline cannot be a lazy continuation line in a block quote.
    /// </summary>
    @Test
    public void testSetextHeadingL() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingL.md");

        msAssert.areEqual(2, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkParagraph(quoteBlock.get(0), "Foo");

        checkHorizontalRule(doc.get(1));
    }

    /// <summary>
    /// Tests that setext heading underline cannot be a lazy continuation line in a multiline block quote.
    /// </summary>
    @Test
    public void testSetextHeadingM() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingM.md");

        msAssert.areEqual(1, doc.getCount());
        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);

        msAssert.areEqual(1, quoteBlock.getCount());
        checkParagraph(quoteBlock.get(0), "foo"+SOFT_BREAK+"bar"+SOFT_BREAK+"===");
    }

    /// <summary>
    /// Tests that setext heading underline cannot be a lazy continuation line in a list.
    /// </summary>
    @Test
    public void testSetextHeadingN() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingN.md");

        msAssert.areEqual(2, doc.getCount());
        checkBulletListItem(doc.get(0), ListMarker.MINUS, "Foo");
        checkHorizontalRule(doc.get(1));
    }

    /// <summary>
    /// Tests that a blank line is needed between a paragraph and a following setext heading,
    /// since otherwise the paragraph becomes part of the heading’s content.
    /// </summary>
    @Test
    public void testSetextHeadingO() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingO.md");

        msAssert.areEqual(1, doc.getCount());
        checkSetextHeading(doc.get(0), 2, "Foo"+SOFT_BREAK+"Bar");
    }

    /// <summary>
    /// Tests that in general a blank line is not required before or after setext headings.
    /// A first line in this test is HorizontalRule.
    /// </summary>
    @Test
    public void testSetextHeadingP() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingP.md");

        msAssert.areEqual(4, doc.getCount());

        checkHorizontalRule(doc.get(0));
        checkSetextHeading(doc.get(1), 2, "Foo");
        checkSetextHeading(doc.get(2), 2, "Bar");
        checkParagraph(doc.get(3), "Baz");
    }

    /// <summary>
    /// Tests that setext headings cannot be empty.
    /// </summary>
    @Test
    public void testSetextHeadingQ() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingQ.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "====");
    }

    /// <summary>
    /// Tests that setext heading text lines must not be interpretable as block constructs other than paragraphs.
    /// So, the line of dashes gets interpreted as a HorizontalRule.
    /// </summary>
    @Test
    public void testSetextHeadingR() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingR.md");

        msAssert.areEqual(2, doc.getCount());
        checkHorizontalRule(doc.get(0));
        checkHorizontalRule(doc.get(1));
    }

    /// <summary>
    /// Tests that setext heading text lines must not be interpretable as block constructs other than paragraphs.
    /// So, the line with dash gets interpreted as a List.
    /// </summary>
    @Test
    public void testSetextHeadingS() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingS.md");

        msAssert.areEqual(2, doc.getCount());
        checkBulletListItem(doc.get(0), ListMarker.MINUS, "foo");
        checkHorizontalRule(doc.get(1));
    }

    /// <summary>
    /// Tests that setext heading text lines must not be interpretable as block constructs other than paragraphs.
    /// So, the line with dashes gets interpreted as a HorizontalRule.
    /// </summary>
    @Test
    public void testSetextHeadingT() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingT.md");

        msAssert.areEqual(2, doc.getCount());
        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);

        msAssert.areEqual(1, quoteBlock.getCount());
        checkParagraph(quoteBlock.get(0), "foo");
        checkHorizontalRule(doc.get(1));
    }

    /// <summary>
    /// Tests that blank line not allowed in text of multiline setext headings.
    /// </summary>
    @Test
    public void testSetextHeadingU() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingU.md");

        msAssert.areEqual(3, doc.getCount());
        checkParagraph(doc.get(0), "Foo");
        checkSetextHeading(doc.get(1), 2, "bar");
        checkParagraph(doc.get(2), "baz");
    }

    /// <summary>
    /// Tests that blank lines can be put around dashes to interpret them as a HorizontalRule.
    /// </summary>
    @Test
    public void testSetextHeadingV() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingV.md");

        msAssert.areEqual(3, doc.getCount());
        checkParagraph(doc.get(0), "Foo"+SOFT_BREAK+"bar");
        checkHorizontalRule(doc.get(1));
        checkParagraph(doc.get(2), "baz");
    }

    /// <summary>
    /// Tests that line "* * *" is not interpreted as a setext heading underline.
    /// </summary>
    @Test
    public void testSetextHeadingW() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingW.md");

        msAssert.areEqual(3, doc.getCount());
        checkParagraph(doc.get(0), "Foo"+SOFT_BREAK+"bar");
        checkHorizontalRule(doc.get(1));
        checkParagraph(doc.get(2), "baz");
    }

    /// <summary>
    /// Tests that line with backslash before dashes is not interpreted as a setext heading underline.
    /// </summary>
    @Test
    public void testSetextHeadingX() throws Exception
    {
        MarkdownDocument doc = open("TestSetextHeadingX.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "Foo"+SOFT_BREAK+"bar"+SOFT_BREAK+"---"+SOFT_BREAK+"baz");
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\Headings\\Setext\\{0}", fileName));
    }
}

