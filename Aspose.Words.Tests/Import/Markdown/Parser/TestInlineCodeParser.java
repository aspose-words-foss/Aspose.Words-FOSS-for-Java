// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/09/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown InlineCode feature (code spans).
/// </summary>
public class TestInlineCodeParser extends TestMarkdownParserBase
{
    /// <summary>
    /// Tests simple code span.
    /// </summary>
    @Test
    public void testInlineCodeA() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeA.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "`foo`")});
    }

    /// <summary>
    /// Tests that there should be an equal number of opening and closing backticks and
    /// single leading and trailing space are stripped.
    /// </summary>
    @Test
    public void testInlineCodeB() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeB.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "``foo ` bar``")});
    }

    /// <summary>
    /// Tests that there should be an equal number of opening and closing backticks and
    /// single leading and trailing space are stripped.
    /// </summary>
    @Test
    public void testInlineCodeC() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeC.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "````")});
    }

    /// <summary>
    /// Tests that only one space is stripped.
    /// </summary>
    @Test
    public void testInlineCodeD() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeD.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "` `` `")});
    }

    /// <summary>
    /// Tests that stripping only happens if the space is on both sides of the string.
    /// </summary>
    @Test
    public void testInlineCodeE() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeE.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "` a`")});
    }

    /// <summary>
    /// Tests that only spaces, and not Unicode whitespace in general, are stripped.
    /// </summary>
    @Test
    public void testInlineCodeF() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeF.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "`\u00a0b\u00a0`")});
    }

    /// <summary>
    /// Tests that no stripping occurs if the code span contains only spaces.
    /// </summary>
    @Test
    public void testInlineCodeG() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeG.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE_CODE, "` `"),
            new ExpectedInline(BlockType.INLINE, SOFT_BREAK),
            new ExpectedInline(BlockType.INLINE_CODE, "`  `")});
    }

    /// <summary>
    /// Tests that line endings are treated like spaces.
    /// </summary>
    @Test
    public void testInlineCodeH() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeH.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "``foo bar   baz``")});
    }

    /// <summary>
    /// Tests that line endings are treated like spaces.
    /// </summary>
    @Test
    public void testInlineCodeI() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeI.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "``foo ``")});
    }

    /// <summary>
    /// Tests that interior spaces are not collapsed.
    /// </summary>
    @Test
    public void testInlineCodeJ() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeJ.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "`foo   bar  baz`")});
    }

    /// <summary>
    /// Tests that backslash escapes do not work in code spans. All backslashes are treated literally.
    /// </summary>
    @Test
    public void testInlineCodeK() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeK.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "`foo\\`"), new ExpectedInline(BlockType.INLINE, "bar`")});
    }

    /// <summary>
    /// Tests that backslash escapes are never needed, because one can always
    /// choose a string of n backtick characters as delimiters.
    /// </summary>
    @Test
    public void testInlineCodeL() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeL.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "``foo`bar``")});
    }

    /// <summary>
    /// Tests that backslash escapes are never needed, because one can always
    /// choose a string of n backtick characters as delimiters.
    /// </summary>
    @Test
    public void testInlineCodeM() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeM.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "`foo `` bar`")});
    }

    /// <summary>
    /// Tests that code span backticks have higher precedence than any other inline constructs
    /// except HTML tags and autolinks. Thus, for example, this is not parsed as emphasized text,
    /// since the second * is part of a code span.
    /// </summary>
    @Test
    public void testInlineCodeN() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeN.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "*foo"), new ExpectedInline(BlockType.INLINE_CODE, "`*`")});
    }

    /// <summary>
    /// Tests that code span backticks have higher precedence than any other inline constructs
    /// except HTML tags and autolinks. Thus, for example, this is not parsed as emphasized text,
    /// since the second * is part of a code span.
    /// </summary>
    @Test
    public void testInlineCodeO() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeO.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0),
            new ExpectedInline[]
            {
                new ExpectedInline(BlockType.INLINE, "[not a "), new ExpectedInline(BlockType.INLINE_CODE, "`link](/foo`"), new ExpectedInline(BlockType.INLINE, ")")
            });
    }

    /// <summary>
    /// Tests that code spans, HTML tags, and autolinks have the same precedence. Thus, this is code.
    /// </summary>
    @Test
    public void testInlineCodeP() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeP.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "`<a href=\"`"), new ExpectedInline(BlockType.INLINE, "\">`")});
    }


    /// <summary>
    /// Tests that code spans, HTML tags, and autolinks have the same precedence. Thus, this is code.
    /// </summary>
    @Test
    public void testInlineCodeR() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeR.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "`<http://foo.bar.`"), new ExpectedInline(BlockType.INLINE, "baz>`")});
    }

    /// <summary>
    /// Tests that code spans, HTML tags, and autolinks have the same precedence. Thus, this is an autolink.
    /// </summary>
    @Test
    public void testInlineCodeS() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeS.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.AUTOLINK, "<http://foo.bar.`baz>"),
            new ExpectedInline(BlockType.INLINE, "`") });
    }

    /// <summary>
    /// Tests that when a backtick string is not closed by a matching backtick string, we just have literal backticks.
    /// </summary>
    @Test
    public void testInlineCodeT() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeT.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "```foo``")});
    }

    /// <summary>
    /// Tests that when a backtick string is not closed by a matching backtick string, we just have literal backticks.
    /// </summary>
    @Test
    public void testInlineCodeU() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeU.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "`foo")});
    }

    /// <summary>
    /// Tests that opening and closing backtick strings have to be equal in length.
    /// </summary>
    @Test
    public void testInlineCodeV() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeV.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "`foo"), new ExpectedInline(BlockType.INLINE_CODE, "``bar``")});
    }

    /// <summary>
    /// Tests a backtick-delimited string in a code span: `` `foo` ``
    /// </summary>
    @Test
    public void testInlineCodeW() throws Exception
    {
        MarkdownDocument doc = open("TestInlineCodeW.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] { new ExpectedInline(BlockType.INLINE_CODE, "```foo```") });
    }

    /// <summary>
    /// Returns Markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\Inlines\\InlineCode\\{0}", fileName));
    }
}

