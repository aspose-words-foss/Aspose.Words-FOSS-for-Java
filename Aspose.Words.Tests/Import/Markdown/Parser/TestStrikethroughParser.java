// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/09/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown Strikethrough feature.
/// </summary>
public class TestStrikethroughParser extends TestMarkdownParserBase
{

    /// <summary>
    /// Tests simple strikethrough.
    /// </summary>
    @Test
    public void testStrikethroughA() throws Exception
    {
        MarkdownDocument doc = open("TestStrikethroughA.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.STRIKETHROUGH, "~~Hi~~"), new ExpectedInline(BlockType.INLINE, " Hello, world!")});
    }

    /// <summary>
    /// Tests simple strikethrough.
    /// </summary>
    @Test
    public void testStrikethroughB() throws Exception
    {
        MarkdownDocument doc = open("TestStrikethroughB.md");

        msAssert.areEqual(2, doc.getCount());
        checkParagraph(doc.get(0), "This ~~has a");
        checkParagraph(doc.get(1), "new paragraph~~.");

        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "This ~~has a")});
        checkInlines(doc.get(1), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "new paragraph~~.")});
    }

    /// <summary>
    /// Tests complex strikethrough with nested emphases.
    /// </summary>
    @Test
    public void testStrikethroughC() throws Exception
    {
        MarkdownDocument doc = open("TestStrikethroughC.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "~~Not "), new ExpectedInline(BlockType.STRIKETHROUGH, "~~*~~a~~ **code** block*~~")});

        StrikethroughInlineBlock strikethroughBlock = (StrikethroughInlineBlock)((ParagraphBlock)(doc.get(0))).get(1);
        checkInlines(strikethroughBlock, new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*~~a~~ **code** block*")});
    }

    /// <summary>
    /// Tests strikethrough inside Quote.
    /// </summary>
    @Test
    public void testStrikethroughD() throws Exception
    {
        MarkdownDocument doc = open("TestStrikethroughD.md");

        msAssert.areEqual(1, doc.getCount());
        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        ParagraphBlock paragraphBlock = (ParagraphBlock)quoteBlock.get(0);

        checkInlines(paragraphBlock, new ExpectedInline[] {new ExpectedInline(BlockType.STRIKETHROUGH, "~~Foo"+SOFT_BREAK+"bar~~")});
    }

    /// <summary>
    /// Tests strikethrough inside a Quote separated with blank line.
    /// </summary>
    @Test
    public void testStrikethroughE() throws Exception
    {
        MarkdownDocument doc = open("TestStrikethroughE.md");

        msAssert.areEqual(2, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkParagraph(quoteBlock.get(0), "~~Foo");

        quoteBlock = (QuoteBlock)doc.get(1);
        checkParagraph(quoteBlock.get(0), "bar~~");
    }

    /// <summary>
    /// Tests strikethrough inside ATX Heading.
    /// </summary>
    @Test
    public void testStrikethroughF() throws Exception
    {
        MarkdownDocument doc = open("TestStrikethroughF.md");

        msAssert.areEqual(1, doc.getCount());
        checkAtxHeading(doc.get(0), 1, "~~Foo~~");

        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.STRIKETHROUGH, "~~Foo~~")});
    }

    /// <summary>
    /// Tests strikethrough inside Setext Heading.
    /// </summary>
    @Test
    public void testStrikethroughG() throws Exception
    {
        MarkdownDocument doc = open("TestStrikethroughG.md");

        msAssert.areEqual(1, doc.getCount());
        checkSetextHeading(doc.get(0), 1, "~~Foo~~");

        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.STRIKETHROUGH, "~~Foo~~")});
    }

    /// <summary>
    /// Tests strikethrough inside InlineCode.
    /// </summary>
    @Test
    public void testStrikethroughH() throws Exception
    {
        MarkdownDocument doc = open("TestStrikethroughH.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "`~~Foo~~`")});

        InlineCodeBlock inlineCodeBlock = (InlineCodeBlock)((ParagraphBlock)doc.get(0)).get(0);
        checkInlines(inlineCodeBlock, new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "~~Foo~~")});
    }

    /// <summary>
    /// Tests strikethrough with InlineCode inside.
    /// </summary>
    @Test
    public void testStrikethroughI() throws Exception
    {
        MarkdownDocument doc = open("TestStrikethroughI.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.STRIKETHROUGH, "~~`Foo`~~")});

        StrikethroughInlineBlock strikethroughBlock = (StrikethroughInlineBlock)((ParagraphBlock)doc.get(0)).get(0);
        checkInlines(strikethroughBlock, new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "`Foo`")});
    }

    /// <summary>
    /// Tests that one tilde is not enough to be a strikethrough.
    /// </summary>
    @Test
    public void testStrikethroughJ() throws Exception
    {
        MarkdownDocument doc = open("TestStrikethroughJ.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "~Foo~")});
    }

    /// <summary>
    /// Tests that closing tildes can be length greater than two.
    /// </summary>
    @Test
    public void testStrikethroughK() throws Exception
    {
        MarkdownDocument doc = open("TestStrikethroughK.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.STRIKETHROUGH, "~~Foo~~"), new ExpectedInline(BlockType.INLINE, "~")});
    }

    /// <summary>
    /// Tests that there should be at least two not broken closing tildes.
    /// </summary>
    @Test
    public void testStrikethroughL() throws Exception
    {
        MarkdownDocument doc = open("TestStrikethroughL.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "~~Foo~ ~~")});
    }

    /// <summary>
    /// Tests that new line acts as whitespace so that tildes at the beginning of the line can not be a closing sequence.
    /// </summary>
    @Test
    public void testStrikethroughM() throws Exception
    {
        MarkdownDocument doc = open("TestStrikethroughM.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "~~Foo"+SOFT_BREAK+"~~")});
    }

    /// <summary>
    /// Tests that strikethrough has less precedence than inline code.
    /// </summary>
    @Test
    public void testStrikethroughN() throws Exception
    {
        MarkdownDocument doc = open("TestStrikethroughN.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "~~"), new ExpectedInline(BlockType.INLINE_CODE, "`Foo~~`")});
    }

    /// <summary>
    /// Tests that tildes are allowed intraword.
    /// </summary>
    @Test
    public void testStrikethroughO() throws Exception
    {
        MarkdownDocument doc = open("TestStrikethroughO.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "F"), new ExpectedInline(BlockType.STRIKETHROUGH, "~~o~~"), new ExpectedInline(BlockType.INLINE, "o")});
    }

    /// <summary>
    /// Tests strikethrough with different length of opening and closing sequence of tildes.
    /// </summary>
    @Test
    public void testStrikethroughP() throws Exception
    {
        MarkdownDocument doc = open("TestStrikethroughP.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0),
            new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "F~~"), new ExpectedInline(BlockType.STRIKETHROUGH, "~~o~~"), new ExpectedInline(BlockType.INLINE, "~o")});
    }

    /// <summary>
    /// Tests that when tildes are of different lengths, they are cut and remainder can be merged with neighbour tildes.
    /// </summary>
    @Test
    public void testStrikethroughQ() throws Exception
    {
        MarkdownDocument doc = open("TestStrikethroughQ.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0),
            new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "F"), new ExpectedInline(BlockType.STRIKETHROUGH, "~~~~o~~~o~~")});
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\Inlines\\Strikethrough\\{0}", fileName));
    }
}

