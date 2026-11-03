// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/09/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown IndentedCode feature.
/// </summary>
public class TestIndentedCodeParser extends TestMarkdownParserBase
{
    /// <summary>
    /// Tests simple indented code block.
    /// </summary>
    @Test
    public void testIndentedCodeA() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeA.md");

        msAssert.areEqual(1, doc.getCount());
        checkIndentedCode(doc.get(0), "a simple\u000b  indented code block");
    }

    /// <summary>
    /// Tests the list item interpretation takes precedence.
    /// </summary>
    @Test
    public void testIndentedCodeB() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeB.md");

        msAssert.areEqual(1, doc.getCount());

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());

        checkParagraph(bulletListItemBlock.get(0), "foo");
        checkParagraph(bulletListItemBlock.get(1), "bar");
    }

    /// <summary>
    /// Tests the list item interpretation takes precedence.
    /// </summary>
    @Test
    public void testIndentedCodeC() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeC.md");

        msAssert.areEqual(1, doc.getCount());

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        checkParagraph(orderedListItemBlock.get(0), "foo");
        checkBulletListItem(orderedListItemBlock.get(1), ListMarker.MINUS, "bar");
    }

    /// <summary>
    /// Tests that the contents of a code block are literal text, and do not get parsed as Markdown.
    /// </summary>
    @Test
    public void testIndentedCodeD() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeD.md");

        checkIndentedCode(doc.get(0), "<a/>\u000b*hi*\u000b\u000b- one");
    }


    /// <summary>
    /// Tests that any initial spaces beyond four will be included in the content, even in interior blank lines.
    /// </summary>
    @Test
    public void testIndentedCodeF() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeF.md");

        msAssert.areEqual(1, doc.getCount());
        checkIndentedCode(doc.get(0), "chunk1\u000b  \u000b  chunk2");
    }

    /// <summary>
    /// Tests that an indented code block cannot interrupt a paragraph.
    /// </summary>
    @Test
    public void testIndentedCodeG() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeG.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "Foo"+SOFT_BREAK+"bar");
    }

    /// <summary>
    /// Tests that any non-blank line with fewer than four leading spaces ends the code block immediately.
    /// So a paragraph may occur immediately after indented code.
    /// </summary>
    @Test
    public void testIndentedCodeH() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeH.md");

        msAssert.areEqual(2, doc.getCount());
        checkIndentedCode(doc.get(0), "foo");
        checkParagraph(doc.get(1), "bar");
    }

    /// <summary>
    /// Tests that indented code can occur immediately before and after other kinds of blocks.
    /// </summary>
    @Test
    public void testIndentedCodeI() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeI.md");

        msAssert.areEqual(5, doc.getCount());
        checkAtxHeading(doc.get(0), 1, "Heading");
        checkIndentedCode(doc.get(1), "foo");
        checkSetextHeading(doc.get(2), 2, "Heading");
        checkIndentedCode(doc.get(3), "foo");
        checkHorizontalRule(doc.get(4));
    }

    /// <summary>
    /// Tests that the first line can be indented more than four spaces.
    /// </summary>
    @Test
    public void testIndentedCodeJ() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeJ.md");

        msAssert.areEqual(1, doc.getCount());
        checkIndentedCode(doc.get(0), "    foo\u000bbar");
    }

    /// <summary>
    /// Tests that blank lines preceding or following an indented code block are not included in it.
    /// </summary>
    @Test
    public void testIndentedCodeK() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeK.md");

        msAssert.areEqual(1, doc.getCount());
        checkIndentedCode(doc.get(0), "foo");
    }

    /// <summary>
    /// Tests that trailing spaces are included in the code block’s content.
    /// </summary>
    @Test
    public void testIndentedCodeL() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeL.md");

        msAssert.areEqual(1, doc.getCount());
        checkIndentedCode(doc.get(0), "foo  ");
    }

    /// <summary>
    /// Tests paragraph with only whitespace characters inside indented code block.
    /// </summary>
    @Test
    public void testIndentedCodeM() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeM.md");

        msAssert.areEqual(1, doc.getCount());
        checkIndentedCode(doc.get(0), "foo\u000b\u000bbar");
    }

    /// <summary>
    /// Tests indented code block inside block quote.
    /// </summary>
    @Test
    public void testIndentedCodeN() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeN.md");

        msAssert.areEqual(2, doc.getCount());
        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkIndentedCode(quoteBlock.get(0), "foo");
        checkParagraph(doc.get(1), "bar");
    }

    /// <summary>
    /// Tests indented code block inside block quote with multiple lines.
    /// </summary>
    @Test
    public void testIndentedCodeO() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeO.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkIndentedCode(quoteBlock.get(0), "foo");
        checkParagraph(quoteBlock.get(1), "bar");
    }

    /// <summary>
    /// Tests indented code block inside block quote with multiple lines.
    /// </summary>
    @Test
    public void testIndentedCodeP() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeP.md");

        msAssert.areEqual(2, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkIndentedCode(quoteBlock.get(0), "foo");

        quoteBlock = (QuoteBlock)doc.get(1);
        checkParagraph(quoteBlock.get(0), "bar");
    }

    /// <summary>
    /// Tests indented code block inside block quote with multiple lines.
    /// </summary>
    @Test
    public void testIndentedCodeQ() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeQ.md");

        msAssert.areEqual(2, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkIndentedCode(quoteBlock.get(0), "foo");

        quoteBlock = (QuoteBlock)doc.get(1);
        checkIndentedCode(quoteBlock.get(0), "bar");
    }

    /// <summary>
    /// Tests indented code block inside block quote with multiple lines.
    /// </summary>
    @Test
    public void testIndentedCodeR() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeR.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkIndentedCode(quoteBlock.get(0), "foo\u000b\u000bbar");
    }

    /// <summary>
    /// Tests indented code block inside block quote with multiple lines.
    /// </summary>
    @Test
    public void testIndentedCodeS() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeS.md");

        msAssert.areEqual(3, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkIndentedCode(quoteBlock.get(0), "foo");

        checkAtxHeading(doc.get(1), 1, "Heading");

        quoteBlock = (QuoteBlock)doc.get(2);
        checkIndentedCode(quoteBlock.get(0), "bar");
    }

    /// <summary>
    /// Tests indented code block inside various block quotes.
    /// </summary>
    @Test
    public void testIndentedCodeT() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeT.md");

        msAssert.areEqual(1, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkParagraph(quoteBlock.get(0), "magna"+SOFT_BREAK+"aliqua.");
        QuoteBlock nestedQuoteBlock = (QuoteBlock)quoteBlock.get(1);
        msAssert.areEqual(0, nestedQuoteBlock.getCount());
        checkIndentedCode(quoteBlock.get(2), "Ut enim");
    }

    /// <summary>
    /// Tests indented code block inside block quote with line continuation.
    /// </summary>
    @Test
    public void testIndentedCodeU() throws Exception
    {
        MarkdownDocument doc = open("TestIndentedCodeU.md");

        msAssert.areEqual(2, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkParagraph(quoteBlock.get(0), "magna"+SOFT_BREAK+"aliqua.");
        checkIndentedCode(quoteBlock.get(1), "Ut enim");

        checkIndentedCode(doc.get(1), "ad minim");
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\Code\\IndentedCode\\{0}", fileName));
    }
}

