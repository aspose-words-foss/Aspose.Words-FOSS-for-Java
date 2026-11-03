// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/11/2023 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown Underline feature.
/// </summary>
public class TestUnderlineParser extends TestMarkdownParserBase
{

    /// <summary>
    /// Tests simple underline.
    /// </summary>
    @Test
    public void testUnderlineA() throws Exception
    {
        MarkdownDocument doc = open("TestUnderlineA.md", loadOptions);

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.UNDERLINE, "++Hello++"), new ExpectedInline(BlockType.INLINE, " world!")});
    }

    /// <summary>
    /// Tests simple underline.
    /// </summary>
    @Test
    public void testUnderlineB() throws Exception
    {
        MarkdownDocument doc = open("TestUnderlineB.md");

        msAssert.areEqual(2, doc.getCount());
        checkParagraph(doc.get(0), "This ++has a");
        checkParagraph(doc.get(1), "new paragraph++.");

        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "This ++has a")});
        checkInlines(doc.get(1), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "new paragraph++.")});
    }

    /// <summary>
    /// Tests complex underline with nested emphases.
    /// </summary>
    @Test
    public void testUnderlineC() throws Exception
    {
        MarkdownDocument doc = open("TestUnderlineC.md", loadOptions);

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "++Not "), new ExpectedInline(BlockType.UNDERLINE, "++*++a++ **code** block*++")});

        UnderlineInlineBlock underlineBlock = (UnderlineInlineBlock)((ParagraphBlock)(doc.get(0))).get(1);
        checkInlines(underlineBlock, new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*++a++ **code** block*")});
    }

    /// <summary>
    /// Tests underline inside Quote.
    /// </summary>
    @Test
    public void testUnderlineD() throws Exception
    {
        MarkdownDocument doc = open("TestUnderlineD.md", loadOptions);

        msAssert.areEqual(1, doc.getCount());
        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        ParagraphBlock paragraphBlock = (ParagraphBlock)quoteBlock.get(0);

        checkInlines(paragraphBlock, new ExpectedInline[] {new ExpectedInline(BlockType.UNDERLINE, "++Foo"+SOFT_BREAK+"bar++")});
    }

    /// <summary>
    /// Tests underline inside a Quote separated with blank line.
    /// </summary>
    @Test
    public void testUnderlineE() throws Exception
    {
        MarkdownDocument doc = open("TestUnderlineE.md");

        msAssert.areEqual(2, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkParagraph(quoteBlock.get(0), "++Foo");

        quoteBlock = (QuoteBlock)doc.get(1);
        checkParagraph(quoteBlock.get(0), "bar++");
    }

    /// <summary>
    /// Tests underline inside ATX Heading.
    /// </summary>
    @Test
    public void testUnderlineF() throws Exception
    {
        MarkdownDocument doc = open("TestUnderlineF.md", loadOptions);

        msAssert.areEqual(1, doc.getCount());
        checkAtxHeading(doc.get(0), 1, "++Foo++");

        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.UNDERLINE, "++Foo++")});
    }

    /// <summary>
    /// Tests underline inside Setext Heading.
    /// </summary>
    @Test
    public void testUnderlineG() throws Exception
    {
        MarkdownDocument doc = open("TestUnderlineG.md", loadOptions);

        msAssert.areEqual(1, doc.getCount());
        checkSetextHeading(doc.get(0), 1, "++Foo++");

        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.UNDERLINE, "++Foo++")});
    }

    /// <summary>
    /// Tests underline inside InlineCode.
    /// </summary>
    @Test
    public void testUnderlineH() throws Exception
    {
        MarkdownDocument doc = open("TestUnderlineH.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "`++Foo++`")});

        InlineCodeBlock inlineCodeBlock = (InlineCodeBlock)((ParagraphBlock)doc.get(0)).get(0);
        checkInlines(inlineCodeBlock, new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "++Foo++")});
    }

    /// <summary>
    /// Tests underline with InlineCode inside.
    /// </summary>
    @Test
    public void testUnderlineI() throws Exception
    {
        MarkdownDocument doc = open("TestUnderlineI.md", loadOptions);

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.UNDERLINE, "++`Foo`++")});

        UnderlineInlineBlock underlineBlock = (UnderlineInlineBlock)((ParagraphBlock)doc.get(0)).get(0);
        checkInlines(underlineBlock, new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "`Foo`")});
    }

    /// <summary>
    /// Tests that one plus is not enough to be an underline.
    /// </summary>
    @Test
    public void testUnderlineJ() throws Exception
    {
        MarkdownDocument doc = open("TestUnderlineJ.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "+Foo+")});
    }

    /// <summary>
    /// Tests that closing pluses can be length greater than two.
    /// </summary>
    @Test
    public void testUnderlineK() throws Exception
    {
        MarkdownDocument doc = open("TestUnderlineK.md", loadOptions);

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.UNDERLINE, "++Foo++"), new ExpectedInline(BlockType.INLINE, "+")});
    }

    /// <summary>
    /// Tests that there should be at least two not broken closing pluses.
    /// </summary>
    @Test
    public void testUnderlineL() throws Exception
    {
        MarkdownDocument doc = open("TestUnderlineL.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "++Foo+ ++")});
    }

    /// <summary>
    /// Tests that new line acts as whitespace so that pluses at the beginning of the line can not be a closing sequence.
    /// </summary>
    @Test
    public void testUnderlineM() throws Exception
    {
        MarkdownDocument doc = open("TestUnderlineM.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "++Foo"+SOFT_BREAK+"++")});
    }

    /// <summary>
    /// Tests that underline has less precedence than inline code.
    /// </summary>
    @Test
    public void testUnderlineN() throws Exception
    {
        MarkdownDocument doc = open("TestUnderlineN.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "++"), new ExpectedInline(BlockType.INLINE_CODE, "`Foo++`")});
    }

    /// <summary>
    /// Tests that pluses are allowed intraword.
    /// </summary>
    @Test
    public void testUnderlineO() throws Exception
    {
        MarkdownDocument doc = open("TestUnderlineO.md", loadOptions);

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "F"), new ExpectedInline(BlockType.UNDERLINE, "++o++"), new ExpectedInline(BlockType.INLINE, "o")});
    }

    /// <summary>
    /// Tests underline with different length of opening and closing sequence of pluses.
    /// </summary>
    @Test
    public void testUnderlineP() throws Exception
    {
        MarkdownDocument doc = open("TestUnderlineP.md", loadOptions);

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0),
            new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "F++"), new ExpectedInline(BlockType.UNDERLINE, "++o++"), new ExpectedInline(BlockType.INLINE, "+o")});
    }

    /// <summary>
    /// Tests that when pluses are of different lengths, they are cut and remainder can be merged with neighbour pluses.
    /// </summary>
    @Test
    public void testUnderlineQ() throws Exception
    {
        MarkdownDocument doc = open("TestUnderlineQ.md", loadOptions);

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0),
            new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "F"), new ExpectedInline(BlockType.UNDERLINE, "++++o+++o++")});
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return open(fileName, null);
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName, MarkdownLoadOptions loadOptions) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\Inlines\\Underline\\{0}", fileName), loadOptions);
    }

    private MarkdownLoadOptions loadOptions = loadOptionsInitializer();
    /*JAVA-added private static Initializer*/
    private static MarkdownLoadOptions loadOptionsInitializer()
    {
        MarkdownLoadOptions loadOptions = new MarkdownLoadOptions(); { loadOptions.setImportUnderlineFormatting(true); }
        return loadOptions;
    }
}

