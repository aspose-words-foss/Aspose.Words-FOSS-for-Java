// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/09/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown Quote feature.
/// </summary>
public class TestQuoteParser extends TestMarkdownParserBase
{
    /// <summary>
    /// Tests simple quote.
    /// </summary>
    @Test
    public void testQuoteA() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteA.md");

        msAssert.areEqual(1, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkAtxHeading(quoteBlock.get(0), 1, "Foo");
        checkParagraph(quoteBlock.get(1), "bar"+SOFT_BREAK+"baz");
    }

    /// <summary>
    /// Tests that spaces after the > characters can be omitted in a quote.
    /// </summary>
    @Test
    public void testQuoteB() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteB.md");

        msAssert.areEqual(1, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkAtxHeading(quoteBlock.get(0), 1, "Foo");
        checkParagraph(quoteBlock.get(1), "bar"+SOFT_BREAK+"baz");
    }

    /// <summary>
    /// Tests that the > characters can be indented 1-3 spaces in a quote.
    /// </summary>
    @Test
    public void testQuoteC() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteC.md");

        msAssert.areEqual(1, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkAtxHeading(quoteBlock.get(0), 1, "Foo");
        checkParagraph(quoteBlock.get(1), "bar"+SOFT_BREAK+"baz");
    }

    /// <summary>
    /// Tests that four indentation spaces are too much for a quote.
    /// </summary>
    @Test
    public void testQuoteD() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteD.md");
        checkIndentedCode(doc.get(0), "> # Foo\u000b> bar\u000b> baz");
    }

    /// <summary>
    /// Tests that we can omit the > before paragraph continuation text in a quote.
    /// </summary>
    @Test
    public void testQuoteE() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteE.md");

        msAssert.areEqual(1, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkAtxHeading(quoteBlock.get(0), 1, "Foo");
        checkParagraph(quoteBlock.get(1), "bar"+SOFT_BREAK+"baz");
    }

    /// <summary>
    /// Tests that a quote can contain some lazy and some non-lazy continuation lines.
    /// </summary>
    @Test
    public void testQuoteF() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteF.md");

        msAssert.areEqual(1, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkParagraph(quoteBlock.get(0), "bar"+SOFT_BREAK+"baz"+SOFT_BREAK+"foo");
    }

    /// <summary>
    /// Tests that sequence of --- characters will be parsed as a horizontal rule outside of quote.
    /// </summary>
    @Test
    public void testQuoteG() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteG.md");

        msAssert.areEqual(2, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkParagraph(quoteBlock.get(0), "foo");
        checkHorizontalRule(doc.get(1));
    }

    /// <summary>
    /// Tests that sequence of >--- characters will be parsed as a setext heading inside a quote.
    /// </summary>
    @Test
    public void testQuoteH() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteH.md");

        msAssert.areEqual(1, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkSetextHeading(quoteBlock.get(0), 2, "foo");
    }

    /// <summary>
    /// Tests that sequence of '>-' characters will be parsed as a list item inside a quote.
    /// </summary>
    @Test
    public void testQuoteI() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteI.md");

        msAssert.areEqual(1, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkBulletListItem(quoteBlock.get(0), ListMarker.MINUS, "foo");
        checkBulletListItem(quoteBlock.get(1), ListMarker.MINUS, "bar");
    }

    /// <summary>
    /// Tests that - without preceding > is a list item outside of a quote.
    /// </summary>
    @Test
    public void testQuoteJ() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteJ.md");

        msAssert.areEqual(2, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkBulletListItem(quoteBlock.get(0), ListMarker.MINUS, "foo");

        checkBulletListItem(doc.get(1), ListMarker.MINUS, "bar");
    }

    /// <summary>
    /// Tests lazy continuation line in a quote.
    /// </summary>
    @Test
    public void testQuoteK() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteK.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkParagraph(quoteBlock.get(0), "foo"+SOFT_BREAK+"- bar");
    }

    /// <summary>
    /// Tests a block quote can be empty.
    /// </summary>
    @Test
    public void testQuoteL() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteL.md");

        msAssert.areEqual(1, doc.getCount());
        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);

        msAssert.areEqual(0, quoteBlock.getCount());
    }

    /// <summary>
    /// Tests empty or whitespace lines can be inside a block quote.
    /// </summary>
    @Test
    public void testQuoteM() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteM.md");

        msAssert.areEqual(1, doc.getCount());
        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);

        msAssert.areEqual(0, quoteBlock.getCount());
    }


    /// <summary>
    /// Tests that blank line always separates block quotes.
    /// </summary>
    @Test
    public void testQuoteO() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteO.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkParagraph(quoteBlock.get(0), "foo");

        quoteBlock = (QuoteBlock)doc.get(1);
        checkParagraph(quoteBlock.get(0), "bar");
    }

    /// <summary>
    /// Tests that if we put block quotes together, we get a single block quote.
    /// </summary>
    @Test
    public void testQuoteP() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteP.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkParagraph(quoteBlock.get(0), "foo"+SOFT_BREAK+"bar");
    }

    /// <summary>
    /// Tests that if we put block quotes together, we get a single block quote.
    /// </summary>
    @Test
    public void testQuoteQ() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteQ.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkParagraph(quoteBlock.get(0), "foo");
        checkParagraph(quoteBlock.get(1), "bar");
    }

    /// <summary>
    /// Tests paragraph is not appended to a quote that is ended with a heading.
    /// </summary>
    @Test
    public void testQuoteR() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteR.md");

        msAssert.areEqual(2, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkAtxHeading(quoteBlock.get(0), 1, "foo");
        checkParagraph(doc.get(1), "bar");
    }

    /// <summary>
    /// Tests quote level when empty nested quote has equal or less level than parent quote.
    /// </summary>
    @Test
    public void testQuoteS() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteS.md");

        msAssert.areEqual(1, doc.getCount());

        QuoteBlock quoteLvl1 = (QuoteBlock)doc.get(0);
        checkParagraph(quoteLvl1.get(0), "This is the first level of quoting.");
        QuoteBlock quoteLvl2 = (QuoteBlock)quoteLvl1.get(1);
        checkParagraph(quoteLvl2.get(0), "This is nested blockquote.");
        checkParagraph(quoteLvl1.get(2), "Back to the first level.");
    }

    /// <summary>
    /// Tests quote level when empty nested quote has equal or greater level than parent quote.
    /// </summary>
    @Test
    public void testQuoteT() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteT.md");

        msAssert.areEqual(1, doc.getCount());

        QuoteBlock quoteLvl1 = (QuoteBlock)doc.get(0);
        checkParagraph(quoteLvl1.get(0), "lvl1");

        QuoteBlock quoteLvl2 = (QuoteBlock)quoteLvl1.get(1);
        QuoteBlock quoteLvl3 = (QuoteBlock)quoteLvl2.get(0);
        QuoteBlock quoteLvl4 = (QuoteBlock)quoteLvl3.get(0);
        QuoteBlock quoteLvl5 = (QuoteBlock)quoteLvl4.get(0);
        QuoteBlock quoteLvl6 = (QuoteBlock)quoteLvl5.get(0);
        msAssert.areEqual(0, quoteLvl6.getCount());


        checkParagraph(quoteLvl4.get(1), "lvl4_s1"+SOFT_BREAK+"lvl4_s2");

        QuoteBlock quoteLvl45 = (QuoteBlock)quoteLvl4.get(2);
        QuoteBlock quoteLvl46 = (QuoteBlock)quoteLvl45.get(0);
        checkParagraph(quoteLvl46.get(0), "lvl6");
    }

    /// <summary>
    /// Tests quote separated with blank quotes.
    /// </summary>
    @Test
    public void testQuoteU() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteU.md");

        msAssert.areEqual(2, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkAtxHeading(quoteBlock.get(0), 2, "This is a header.");
        checkParagraph(quoteBlock.get(1), "q1"+SOFT_BREAK+"q2");

        checkParagraph(doc.get(1), "text outside of quote");
    }

    /// <summary>
    ///  Tests quote separated with blank.
    /// </summary>
    @Test
    public void testQuoteV() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteV.md");

        msAssert.areEqual(2, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkParagraph(quoteBlock.get(0), "baz");

        quoteBlock = (QuoteBlock)((QuoteBlock)doc.get(1)).get(0);
        checkParagraph(quoteBlock.get(0), "tempor incididunt");
    }

    /// <summary>
    /// Tests quotes with Heading separated with blank lines.
    /// </summary>
    @Test
    public void testQuoteW() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteW.md");

        msAssert.areEqual(3, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkAtxHeading(quoteBlock.get(0), 1, "foo");

        quoteBlock = (QuoteBlock)doc.get(1);
        checkParagraph(quoteBlock.get(0), "bar");

        quoteBlock = (QuoteBlock)doc.get(2);
        checkAtxHeading(quoteBlock.get(0), 1, "baz");
        checkParagraph(quoteBlock.get(1), "bop");
    }

    /// <summary>
    /// Tests AtxHeading is inserted into an appropriate nesting quote level,
    /// when it is in a quote with the level less than a previous paragraph.
    /// </summary>
    @Test
    public void testQuoteHeadingLess() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteHeadingLess.md");

        // There should be one root Quote inside a document.
        msAssert.areEqual(1, doc.getCount());

        // This is a root Quote. The root Quote has two nested Quotes.
        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);

        // The first nested Quote has child AtxHeading and another one nested Quote.
        quoteBlock = (QuoteBlock)quoteBlock.get(0);
        checkAtxHeading(quoteBlock.get(1), 1, "bar");

        // The second nested quote has child paragraph.
        quoteBlock = (QuoteBlock)quoteBlock.get(0);
        checkParagraph(quoteBlock.get(0), "Foo");
    }

    /// <summary>
    /// Tests AtxHeading is inserted into an appropriate nesting quote level,
    /// when it is in a quote with the level greater than a previous paragraph.
    /// </summary>
    @Test
    public void testQuoteHeadingGreater() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteHeadingGreater.md");

        // There should be one root Quote inside a document.
        msAssert.areEqual(1, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkParagraph(quoteBlock.get(0), "Foo");

        quoteBlock = (QuoteBlock)quoteBlock.get(1);
        checkAtxHeading(quoteBlock.get(0), 1, "bar");
    }

    /// <summary>
    /// Tests AtxHeading is NOT inserted into a quote when it is just after
    /// a paragraph inside a quote, but not in a quote itself.
    /// </summary>
    @Test
    public void testQuoteHeadingOutside() throws Exception
    {
        MarkdownDocument doc = open("TestQuoteHeadingOutside.md");

        msAssert.areEqual(2, doc.getCount());

        // The first child of the document is a Quote that has two nested Quotes.
        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        quoteBlock = (QuoteBlock)quoteBlock.get(0);
        quoteBlock = (QuoteBlock)quoteBlock.get(0);

        // Last quote contains a paragraph.
        msAssert.areEqual(1, quoteBlock.getCount());
        checkParagraph(quoteBlock.get(0), "Foo");

        // AtxHeading is outside of Quotes and it is the second child of the document.
        checkAtxHeading(doc.get(1), 1, "bar");
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\Quotes\\{0}", fileName));
    }
}

