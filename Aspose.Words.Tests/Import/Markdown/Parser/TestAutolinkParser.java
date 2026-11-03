// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/03/2020 by Mikhail Nepreteamov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown Autolink feature.
/// </summary>
public class TestAutolinkParser extends TestMarkdownParserBase
{
    /// <summary>
    /// Tests simple Autolink.
    /// </summary>
    @Test
    public void testAutolinkA() throws Exception
    {
        MarkdownDocument doc = open("TestAutolinkA.md");
        msAssert.areEqual(1, doc.getCount());

        checkInlines(doc.get(0), new ExpectedInline[] { new ExpectedInline(BlockType.AUTOLINK, "<http://example.com>") });
        AutolinkInlineBlock autolinkBlock = (AutolinkInlineBlock)((ParagraphBlock)doc.get(0)).get(0);
        checkInlines(autolinkBlock, new ExpectedInline[] { new ExpectedInline(BlockType.INLINE, "http://example.com") });
    }

    /// <summary>
    /// Tests simple Autolinks.
    /// </summary>
    @Test
    public void testAutolinkB() throws Exception
    {
        MarkdownDocument doc = open("TestAutolinkB.md");
        msAssert.areEqual(6, doc.getCount());

        checkParagraph(doc.get(0), "text <http://example.com> text");
        checkParagraph(doc.get(1), "text <http://example.com>");
        checkParagraph(doc.get(2), "<http://example.com> text");
        checkParagraph(doc.get(3), "text<http://example.com>text");
        checkParagraph(doc.get(4), "text<http://example.com>");
        checkParagraph(doc.get(5), "<http://example.com>text");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "text "),
            new ExpectedInline(BlockType.AUTOLINK, "<http://example.com>"),
            new ExpectedInline(BlockType.INLINE, " text") });
        checkInlines(doc.get(1), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "text "),
            new ExpectedInline(BlockType.AUTOLINK, "<http://example.com>") });
        checkInlines(doc.get(2), new ExpectedInline[] {
            new ExpectedInline(BlockType.AUTOLINK, "<http://example.com>"),
            new ExpectedInline(BlockType.INLINE, " text") });
        checkInlines(doc.get(3), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "text"),
            new ExpectedInline(BlockType.AUTOLINK, "<http://example.com>"),
            new ExpectedInline(BlockType.INLINE, "text") });
        checkInlines(doc.get(4), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "text"),
            new ExpectedInline(BlockType.AUTOLINK, "<http://example.com>") });
        checkInlines(doc.get(5), new ExpectedInline[] {
            new ExpectedInline(BlockType.AUTOLINK, "<http://example.com>"),
            new ExpectedInline(BlockType.INLINE, "text") });
    }

    /// <summary>
    /// Tests simple Autolinks.
    /// </summary>
    @Test
    public void testAutolinkC() throws Exception
    {
        MarkdownDocument doc = open("TestAutolinkC.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "text <http://example.com> text <http://example.com> text");
        checkParagraph(doc.get(1), "text <http://example.com><http://example.com> text");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "text "),
            new ExpectedInline(BlockType.AUTOLINK, "<http://example.com>"),
            new ExpectedInline(BlockType.INLINE, " text "),
            new ExpectedInline(BlockType.AUTOLINK, "<http://example.com>"),
            new ExpectedInline(BlockType.INLINE, " text") });
        checkInlines(doc.get(1), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "text "),
            new ExpectedInline(BlockType.AUTOLINK, "<http://example.com>"),
            new ExpectedInline(BlockType.AUTOLINK, "<http://example.com>"),
            new ExpectedInline(BlockType.INLINE, " text") });
    }

    /// <summary>
    /// Tests Autolinks with Whitespace characters.
    /// </summary>
    @Test
    public void testAutolinkD() throws Exception
    {
        MarkdownDocument doc = open("TestAutolinkD.md");
        msAssert.areEqual(3, doc.getCount());

        checkParagraph(doc.get(0), "<http://example"+SOFT_BREAK+".com>");
        checkParagraph(doc.get(1), "<http://example.com>");
        checkParagraph(doc.get(2), "<http://example\t\t.com>");

        checkInlines(doc.get(0), new ExpectedInline[] { new ExpectedInline(BlockType.HTML_TAG, "<http://example"+SOFT_BREAK+".com>") });
        checkInlines(doc.get(1), new ExpectedInline[] { new ExpectedInline(BlockType.HTML_TAG, "<http://example.com>") });
        checkInlines(doc.get(2), new ExpectedInline[] { new ExpectedInline(BlockType.INLINE, "<http://example\t\t.com>") });
    }


    /// <summary>
    /// Tests Autolink without colon and commercial "At".
    /// </summary>
    @Test
    public void testAutolinkF() throws Exception
    {
        MarkdownDocument doc = open("TestAutolinkF.md");
        msAssert.areEqual(1, doc.getCount());

        checkParagraph(doc.get(0), "<http//example.com>");

        checkInlines(doc.get(0), new ExpectedInline[] { new ExpectedInline(BlockType.HTML_TAG, "<http//example.com>") });
    }

    /// <summary>
    /// Tests Autolink with colon and backslash not in the schema name.
    /// </summary>
    @Test
    public void testAutolinkG() throws Exception
    {
        MarkdownDocument doc = open("TestAutolinkG.md");
        msAssert.areEqual(1, doc.getCount());

        checkParagraph(doc.get(0), "<http://examp\\le.com>");

        checkInlines(doc.get(0), new ExpectedInline[] { new ExpectedInline(BlockType.AUTOLINK, "<http://examp\\le.com>") });
    }

    /// <summary>
    /// Tests Autolink with commercial "At" and backslash.
    /// </summary>
    @Test
    public void testAutolinkH() throws Exception
    {
        MarkdownDocument doc = open("TestAutolinkH.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "<email@examp\\le.com>");
        checkParagraph(doc.get(1), "<ema\\il@example.com>");

        checkInlines(doc.get(0), new ExpectedInline[] { new ExpectedInline(BlockType.HTML_TAG, "<email@examp\\le.com>") });
        checkInlines(doc.get(1), new ExpectedInline[] { new ExpectedInline(BlockType.HTML_TAG, "<ema\\il@example.com>") });
    }

    /// <summary>
    /// Tests Autolink with colon and schema name with incorrect length.
    /// </summary>
    @Test
    public void testAutolinkI() throws Exception
    {
        MarkdownDocument doc = open("TestAutolinkI.md");
        msAssert.areEqual(5, doc.getCount());

        checkParagraph(doc.get(0), "<://example.com>");
        checkParagraph(doc.get(1), "<h:example.com>");
        checkParagraph(doc.get(2), "<ht://example.com>");
        checkParagraph(doc.get(3), "<http5678901234567890123456789012://example.com>");
        checkParagraph(doc.get(4), "<http56789012345678901234567890123:example.com>");

        checkInlines(doc.get(0), new ExpectedInline[] { new ExpectedInline(BlockType.INLINE, "<://example.com>") });
        checkInlines(doc.get(1), new ExpectedInline[] { new ExpectedInline(BlockType.HTML_TAG, "<h:example.com>") });
        checkInlines(doc.get(2), new ExpectedInline[] { new ExpectedInline(BlockType.AUTOLINK, "<ht://example.com>") });
        checkInlines(doc.get(3), new ExpectedInline[] {
            new ExpectedInline(BlockType.AUTOLINK, "<http5678901234567890123456789012://example.com>") });
        checkInlines(doc.get(4), new ExpectedInline[] {
            new ExpectedInline(BlockType.HTML_TAG, "<http56789012345678901234567890123:example.com>") });
    }

    /// <summary>
    /// Tests Autolink with colon and schema name with wrong first symbol (must be [a-zA-Z]).
    /// </summary>
    @Test
    public void testAutolinkJ() throws Exception
    {
        MarkdownDocument doc = open("TestAutolinkJ.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "<1tr://example.com>");
        checkParagraph(doc.get(1), "<ammdftfsg://example.com>");

        checkInlines(doc.get(0), new ExpectedInline[] { new ExpectedInline(BlockType.INLINE, "<1tr://example.com>") });
        checkInlines(doc.get(1), new ExpectedInline[] { new ExpectedInline(BlockType.AUTOLINK, "<ammdftfsg://example.com>") });
    }

    /// <summary>
    /// Tests Autolink with colon and schema name with wrong not-first symbol (must be [a-zA-Z0-9.+-]).
    /// </summary>
    @Test
    public void testAutolinkK() throws Exception
    {
        MarkdownDocument doc = open("TestAutolinkK.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "<h.t0.+-89-r://example.com>");
        checkParagraph(doc.get(1), "<a*mfsg:example.com>");

        checkInlines(doc.get(0), new ExpectedInline[] { new ExpectedInline(BlockType.AUTOLINK, "<h.t0.+-89-r://example.com>") });
        checkInlines(doc.get(1), new ExpectedInline[] { new ExpectedInline(BlockType.HTML_TAG, "<a*mfsg:example.com>") });
    }

    /// <summary>
    /// Tests Autolink with commercial "At" and incorrect symbols.
    /// </summary>
    /// <remarks>
    /// ^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@
    /// [a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$
    /// </remarks>
    @Test
    public void testAutolinkL() throws Exception
    {
        MarkdownDocument doc = open("TestAutolinkL.md");
        msAssert.areEqual(10, doc.getCount());

        checkParagraph(doc.get(0), "<email@example.com>");
        checkParagraph(doc.get(1), "<e@mail@example.com>");
        checkParagraph(doc.get(2), "<email@e@xample.com>");
        checkParagraph(doc.get(3), "<eM12.!#$%&'*+/=?^_`{|}~-@example.com>");
        checkParagraph(doc.get(4), "<email@e-xample.com>");
        checkParagraph(doc.get(5), "<email@-example.com>");
        checkParagraph(doc.get(6), "<email@example-.com>");
        checkParagraph(doc.get(7), "<email@example.c-om>");
        checkParagraph(doc.get(8), "<email@example.-com>");
        checkParagraph(doc.get(9), "<email@example.com->");

        checkInlines(doc.get(0), new ExpectedInline[] { new ExpectedInline(BlockType.AUTOLINK, "<email@example.com>") });
        checkInlines(doc.get(1), new ExpectedInline[] { new ExpectedInline(BlockType.HTML_TAG, "<e@mail@example.com>") });
        checkInlines(doc.get(2), new ExpectedInline[] { new ExpectedInline(BlockType.HTML_TAG, "<email@e@xample.com>") });
        checkInlines(doc.get(3), new ExpectedInline[] { new ExpectedInline(BlockType.AUTOLINK,
            "<eM12.!#$%&'*+/=?^_`{|}~-@example.com>") });
        checkInlines(doc.get(4), new ExpectedInline[] { new ExpectedInline(BlockType.AUTOLINK, "<email@e-xample.com>") });
        checkInlines(doc.get(5), new ExpectedInline[] { new ExpectedInline(BlockType.HTML_TAG, "<email@-example.com>") });
        checkInlines(doc.get(6), new ExpectedInline[] { new ExpectedInline(BlockType.HTML_TAG, "<email@example-.com>") });
        checkInlines(doc.get(7), new ExpectedInline[] { new ExpectedInline(BlockType.AUTOLINK, "<email@example.c-om>") });
        checkInlines(doc.get(8), new ExpectedInline[] { new ExpectedInline(BlockType.HTML_TAG, "<email@example.-com>") });
        checkInlines(doc.get(9), new ExpectedInline[] { new ExpectedInline(BlockType.HTML_TAG, "<email@example.com->") });
    }

    /// <summary>
    /// Tests Autolink with colon and any unexpected symbol after the schema name.
    /// </summary>
    @Test
    public void testAutolinkM() throws Exception
    {
        MarkdownDocument doc = open("TestAutolinkM.md");
        msAssert.areEqual(1, doc.getCount());

        checkParagraph(doc.get(0), "<http://examp@$%5^&#@$56&^%1!>");

        checkInlines(doc.get(0), new ExpectedInline[] { new ExpectedInline(BlockType.AUTOLINK, "<http://examp@$%5^&#@$56&^%1!>") });
    }




    /// <summary>
    /// Tests non-Autolinks.
    /// </summary>
    @Test
    public void testAutolinkR() throws Exception
    {
        MarkdownDocument doc = open("TestAutolinkR.md");
        msAssert.areEqual(3, doc.getCount());

        checkParagraph(doc.get(0), "<>");
        checkParagraph(doc.get(1), "e-mail@gmail.com");
        checkParagraph(doc.get(2), "http://example.com");

        checkInlines(doc.get(0), new ExpectedInline[] { new ExpectedInline(BlockType.INLINE, "<>") });
        checkInlines(doc.get(1), new ExpectedInline[] { new ExpectedInline(BlockType.INLINE, "e-mail@gmail.com") });
        checkInlines(doc.get(2), new ExpectedInline[] { new ExpectedInline(BlockType.INLINE, "http://example.com") });
    }

    /// <summary>
    /// Tests Autolink with bookmark.
    /// </summary>
    @Test
    public void testAutolinkS() throws Exception
    {
        MarkdownDocument doc = open("TestAutolinkS.md");
        msAssert.areEqual(1, doc.getCount());

        checkParagraph(doc.get(0), "<https://spec.commonmark.org/0.28/#autolinks>");

        checkInlines(doc.get(0), new ExpectedInline[] { new ExpectedInline(BlockType.AUTOLINK,
            "<https://spec.commonmark.org/0.28/#autolinks>") });
    }

    /// <summary>
    /// Tests that code spans and Autolinks have the same precedence.
    /// </summary>
    @Test
    public void testAutolinkU() throws Exception
    {
        MarkdownDocument doc = open("TestAutolinkU.md");
        msAssert.areEqual(4, doc.getCount());

        checkParagraph(doc.get(0), "<http://foo.bar.`baz>`");
        checkParagraph(doc.get(1), "`<http://foo.bar.`baz>");
        checkParagraph(doc.get(2), "<http://foo.`bar.`baz>");
        checkParagraph(doc.get(3), "`<http://foo.bar.baz>`");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.AUTOLINK, "<http://foo.bar.`baz>"),
            new ExpectedInline(BlockType.INLINE, "`") });
        checkInlines(doc.get(1), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE_CODE, "`<http://foo.bar.`"),
            new ExpectedInline(BlockType.INLINE, "baz>") });
        checkInlines(doc.get(2), new ExpectedInline[] { new ExpectedInline(BlockType.AUTOLINK, "<http://foo.`bar.`baz>") });
        checkInlines(doc.get(3), new ExpectedInline[] { new ExpectedInline(BlockType.INLINE_CODE, "`<http://foo.bar.baz>`") });
    }

    /// <summary>
    /// Tests Autolinks with closer angle brackets.
    /// </summary>
    @Test
    public void testAutolinkV() throws Exception
    {
        MarkdownDocument doc = open("TestAutolinkV.md");
        msAssert.areEqual(3, doc.getCount());

        checkParagraph(doc.get(0), "<http://foo.bar.baz>>");
        checkParagraph(doc.get(1), "<<http://foo.bar.baz>");
        checkParagraph(doc.get(2), "<<http://foo.bar.baz>>");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.AUTOLINK, "<http://foo.bar.baz>"),
            new ExpectedInline(BlockType.INLINE, ">") });
        checkInlines(doc.get(1), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "<"),
            new ExpectedInline(BlockType.AUTOLINK, "<http://foo.bar.baz>") });
        checkInlines(doc.get(2), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "<"),
            new ExpectedInline(BlockType.AUTOLINK, "<http://foo.bar.baz>"),
            new ExpectedInline(BlockType.INLINE, ">") });
    }

    /// <summary>
    /// Tests that backslash-escaped left angle bracket is not valid for Autolink.
    /// </summary>
    @Test
    public void testAutolinkW() throws Exception
    {
        MarkdownDocument doc = open("TestAutolinkW.md");
        msAssert.areEqual(1, doc.getCount());

        checkParagraph(doc.get(0), "<http://uri>");

        checkInlines(doc.get(0), new ExpectedInline[] { new ExpectedInline(BlockType.HTML_TAG, "<http://uri>") });
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\Inlines\\Autolink\\{0}", fileName));
    }
}

