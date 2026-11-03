// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/09/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown regular paragraphs.
/// </summary>
public class TestParagraphParser extends TestMarkdownParserBase
{
    /// <summary>
    /// Tests an empty input document.
    /// </summary>
    @Test
    public void testEmptyA() throws Exception
    {
        MarkdownDocument doc = open("TestEmptyA.md");
        msAssert.isTrue(doc.isEmpty());
    }

    /// <summary>
    /// Tests a document that contains only whitespace characters.
    /// </summary>
    @Test
    public void testEmptyB() throws Exception
    {
        MarkdownDocument doc = open("TestEmptyB.md");
        msAssert.isTrue(doc.isEmpty());
    }

    /// <summary>
    /// Tests paragraphs when there are no any whitespace characters or empty lines at the very start and end of a file.
    /// </summary>
    @Test
    public void testParagraphA() throws Exception
    {
        MarkdownDocument doc = open("TestParagraphA.md");

        checkParagraph(doc.get(0), "Lorem ipsum"+SOFT_BREAK+"dolor sit amet,"+SOFT_BREAK+"consectetur adipiscing");
        checkParagraph(doc.get(1), "elit, sed"+SOFT_BREAK+"do eiusmod");
        checkParagraph(doc.get(2), "tempor incididunt");
    }

    /// <summary>
    /// Tests paragraphs when there are blank lines at the start and end of a file.
    /// </summary>
    @Test
    public void testParagraphB() throws Exception
    {
        MarkdownDocument doc = open("TestParagraphB.md");

        checkParagraph(doc.get(0), "Lorem ipsum");
        checkParagraph(doc.get(1), "dolor sit amet,");
    }

    /// <summary>
    /// Tests paragraphs when input document has lines with spaces at the start.
    /// </summary>
    @Test
    public void testParagraphC() throws Exception
    {
        MarkdownDocument doc = open("TestParagraphC.md");
        checkParagraph(doc.get(0), "aaa"+SOFT_BREAK+"         bbb"+SOFT_BREAK+"                         ccc");
    }

    /// <summary>
    /// Tests paragraphs when input document has lines with spaces at the end.
    /// </summary>
    @Test
    public void testParagraphD() throws Exception
    {
        MarkdownDocument doc = open("TestParagraphD.md");
        checkParagraph(doc.get(0), "aaa"+HARD_BREAK_SPACES+"bbb");
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\Paragraphs\\{0}", fileName));
    }
}

