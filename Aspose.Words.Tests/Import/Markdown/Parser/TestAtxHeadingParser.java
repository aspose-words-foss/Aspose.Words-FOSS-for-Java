// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/09/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown ATX Heading feature.
/// </summary>
public class TestAtxHeadingParser extends TestMarkdownParserBase
{
    /// <summary>
    /// Tests simple ATX headings.
    /// </summary>
    @Test
    public void testAtxHeadingA() throws Exception
    {
        MarkdownDocument doc = open("TestATXHeadingA.md");
        checkAtxHeading(doc.get(0), 1, "Heading 1");
        checkAtxHeading(doc.get(1), 2, "Heading 2");
        checkAtxHeading(doc.get(2), 3, "Heading 3");
        checkAtxHeading(doc.get(3), 4, "Heading 4");
        checkAtxHeading(doc.get(4), 5, "Heading 5");
        checkAtxHeading(doc.get(5), 6, "Heading 6");
    }

    /// <summary>
    /// Tests that more than six # characters is not a ATX heading.
    /// </summary>
    @Test
    public void testAtxHeadingB() throws Exception
    {
        MarkdownDocument doc = open("TestATXHeadingB.md");
        checkParagraph(doc.get(0), "####### foo");
    }

    /// <summary>
    /// Tests that at least one space is required between the # characters and the heading’s contents in ATX heading.
    /// </summary>
    @Test
    public void testAtxHeadingC() throws Exception
    {
        MarkdownDocument doc = open("TestATXHeadingC.md");
        checkParagraph(doc.get(0), "#5 bolt");
        checkParagraph(doc.get(1), "#hashtag");
    }

    /// <summary>
    /// Tests ATX heading with leading and trailing blanks.
    /// </summary>
    @Test
    public void testAtxHeadingD() throws Exception
    {
        MarkdownDocument doc = open("TestATXHeadingD.md");
        checkAtxHeading(doc.get(0), 1, "foo");
    }

    /// <summary>
    /// Tests that one to three spaces indentation are allowed in ATX headings.
    /// </summary>
    @Test
    public void testAtxHeadingE() throws Exception
    {
        MarkdownDocument doc = open("TestATXHeadingE.md");
        checkAtxHeading(doc.get(0), 3, "foo");
        checkAtxHeading(doc.get(1), 2, "bar");
        checkAtxHeading(doc.get(2), 1, "baz");
    }

    /// <summary>
    /// Tests that four indentation spaces are too much for a ATX heading.
    /// </summary>
    @Test
    public void testAtxHeadingF() throws Exception
    {
        MarkdownDocument doc = open("TestATXHeadingF.md");
        checkIndentedCode(doc.get(0), "# foo");
    }

    /// <summary>
    /// Tests a closing sequence of # characters is optional in ATX headings.
    /// </summary>
    @Test
    public void testAtxHeadingG() throws Exception
    {
        MarkdownDocument doc = open("TestATXHeadingG.md");
        checkAtxHeading(doc.get(0), 2, "foo");
        checkAtxHeading(doc.get(1), 3, "bar");
    }

    /// <summary>
    /// Tests a closing sequence need not be the same length as the opening sequence in a ATX heading.
    /// </summary>
    @Test
    public void testAtxHeadingH() throws Exception
    {
        MarkdownDocument doc = open("TestATXHeadingH.md");
        checkAtxHeading(doc.get(0), 1, "foo");
        checkAtxHeading(doc.get(1), 5, "bar");
    }

    /// <summary>
    /// Tests spaces are allowed after the closing sequence in a ATX heading.
    /// </summary>
    @Test
    public void testAtxHeadingI() throws Exception
    {
        MarkdownDocument doc = open("TestATXHeadingI.md");
        checkAtxHeading(doc.get(0), 3, "foo");
    }

    /// <summary>
    /// Tests that a sequence of # characters with anything but spaces following it is not a closing sequence,
    /// but counts as part of the contents of the heading.
    /// </summary>
    @Test
    public void testAtxHeadingJ() throws Exception
    {
        MarkdownDocument doc = open("TestATXHeadingJ.md");
        checkAtxHeading(doc.get(0), 3, "foo ### b");
    }

    /// <summary>
    /// Tests the closing sequence must be preceded by a space in a ATX heading.
    /// </summary>
    @Test
    public void testAtxHeadingK() throws Exception
    {
        MarkdownDocument doc = open("TestATXHeadingK.md");
        checkAtxHeading(doc.get(0), 1, "foo#");
    }

    /// <summary>
    /// Tests ATX headings need not be separated from surrounding content by blank lines.
    /// </summary>
    @Test
    public void testAtxHeadingL() throws Exception
    {
        MarkdownDocument doc = open("TestATXHeadingL.md");
        checkParagraph(doc.get(0), "Foo bar");
        checkAtxHeading(doc.get(1), 1, "baz");
        checkParagraph(doc.get(2), "Bar foo");
    }

    /// <summary>
    /// Tests that ATX headings can be empty.
    /// </summary>
    @Test
    public void testAtxHeadingM() throws Exception
    {
        MarkdownDocument doc = open("TestATXHeadingM.md");
        checkAtxHeading(doc.get(0), 2, "");
        checkAtxHeading(doc.get(1), 1, "");
        checkAtxHeading(doc.get(2), 3, "");
    }
    
    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\Headings\\ATX\\{0}", fileName));
    }
}

