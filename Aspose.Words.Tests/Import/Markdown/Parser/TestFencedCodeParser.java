// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/09/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown FencedCode feature.
/// </summary>
public class TestFencedCodeParser extends TestMarkdownParserBase
{
    /// <summary>
    /// Tests simple fenced code with backticks.
    /// </summary>
    @Test
    public void testFencedCodeA() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeA.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "<\u000b >");
    }

    /// <summary>
    /// Tests simple fenced code with tildes.
    /// </summary>
    @Test
    public void testFencedCodeB() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeB.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "<\u000b >");
    }

    /// <summary>
    /// Tests that fewer than three backticks is not enough.
    /// </summary>
    @Test
    public void testFencedCodeC() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeC.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "``foo``");
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE_CODE, "``foo``")});
    }

    /// <summary>
    /// Tests that the closing code fence uses the same character as the opening fence.
    /// </summary>
    @Test
    public void testFencedCodeD() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeD.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "aaa\u000b~~~");
    }

    /// <summary>
    /// Tests that the closing code fence uses the same character as the opening fence.
    /// </summary>
    @Test
    public void testFencedCodeE() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeE.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "aaa\u000b```");
    }

    /// <summary>
    /// Tests that the closing code fence must be at least as long as the opening fence.
    /// </summary>
    @Test
    public void testFencedCodeF() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeF.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "aaa\u000b```");
    }

    /// <summary>
    /// Tests that the closing code fence must be at least as long as the opening fence.
    /// </summary>
    @Test
    public void testFencedCodeG() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeG.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "aaa\u000b~~~");
    }


    /// <summary>
    /// Tests that unclosed code blocks are closed by the end of the document.
    /// </summary>
    @Test
    public void testFencedCodeI() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeI.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "\u000b```\u000baaa");
    }

    /// <summary>
    /// Tests that unclosed code blocks are closed by the enclosing block quote.
    /// </summary>
    @Test
    public void testFencedCodeJ() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeJ.md");

        msAssert.areEqual(2, doc.getCount());
        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkFencedCode(quoteBlock.get(0), "aaa");
        checkParagraph(doc.get(1), "bbb");
    }

    /// <summary>
    /// Tests that a code block can have all empty lines as its content.
    /// </summary>
    @Test
    public void testFencedCodeK() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeK.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "\u000b  ");
    }


    /// <summary>
    /// Tests that fences can be indented. If the opening fence is indented, content lines will have
    /// equivalent opening indentation removed, if present.
    /// </summary>
    @Test
    public void testFencedCodeM() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeM.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "aaa\u000baaa");
    }

    /// <summary>
    /// Tests that fences can be indented. If the opening fence is indented, content lines will have
    /// equivalent opening indentation removed, if present.
    /// </summary>
    @Test
    public void testFencedCodeN() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeN.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "aaa\u000baaa\u000baaa");
    }

    /// <summary>
    /// Tests that fences can be indented. If the opening fence is indented, content lines will have
    /// equivalent opening indentation removed, if present.
    /// </summary>
    @Test
    public void testFencedCodeO() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeO.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "aaa\u000b aaa\u000baaa");
    }

    /// <summary>
    /// Tests that four spaces indentation produces an indented code block.
    /// </summary>
    @Test
    public void testFencedCodeP() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeP.md");

        msAssert.areEqual(1, doc.getCount());
        checkIndentedCode(doc.get(0), "```\u000baaa\u000b```");
    }

    /// <summary>
    /// Tests that closing fences may be indented by 0-3 spaces,
    /// and their indentation need not match that of the opening fence.
    /// </summary>
    @Test
    public void testFencedCodeQ() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeQ.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "aaa");
    }

    /// <summary>
    /// Tests that closing fences may be indented by 0-3 spaces,
    /// and their indentation need not match that of the opening fence.
    /// </summary>
    @Test
    public void testFencedCodeR() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeR.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "aaa");
    }

    /// <summary>
    /// Tests that this is not a closing fence, because it is indented 4 spaces.
    /// </summary>
    @Test
    public void testFencedCodeS() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeS.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "aaa\u000b    ```");
    }

    /// <summary>
    /// Tests that opening fence cannot contain internal spaces.
    /// </summary>
    @Test
    public void testFencedCodeT() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeT.md");

        msAssert.areEqual(1, doc.getCount());
        ParagraphBlock paragraphBlock = (ParagraphBlock)doc.get(0);
        checkParagraph(paragraphBlock, "``` ```"+SOFT_BREAK+"aaa");
        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE_CODE, "``` ```"),
            new ExpectedInline(BlockType.INLINE, SOFT_BREAK+"aaa")});
    }

    /// <summary>
    /// Tests that closing fence cannot contain internal spaces.
    /// </summary>
    @Test
    public void testFencedCodeU() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeU.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "aaa\u000b~~~ ~~");
    }

    /// <summary>
    /// Tests that fenced code blocks can interrupt paragraphs, and can be followed directly by paragraphs,
    /// without a blank line between.
    /// </summary>
    @Test
    public void testFencedCodeV() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeV.md");

        msAssert.areEqual(3, doc.getCount());
        checkParagraph(doc.get(0), "foo");
        checkFencedCode(doc.get(1), "bar");
        checkParagraph(doc.get(2), "baz");
    }

    /// <summary>
    /// Tests that other blocks can also occur before and after fenced code blocks without an intervening blank line.
    /// </summary>
    @Test
    public void testFencedCodeW() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeW.md");

        msAssert.areEqual(3, doc.getCount());
        checkSetextHeading(doc.get(0), 2, "foo");
        checkFencedCode(doc.get(1), "bar");
        checkAtxHeading(doc.get(2), 1, "baz");
    }

    /// <summary>
    /// Tests that an info string can be provided after the opening code fence.
    /// </summary>
    @Test
    public void testFencedCodeX() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeX.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "def foo(x)\u000b  return 3\u000bend", "ruby");
    }

    /// <summary>
    /// Tests that an info string is truncated to the first occurred whitespace character.
    /// </summary>
    @Test
    public void testFencedCodeY() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeY.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "def foo(x)\u000b  return 3\u000bend", "ruby");
    }

    /// <summary>
    /// Tests that the content of fenced code block can be empty when info string exists.
    /// </summary>
    @Test
    public void testFencedCodeZ() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeZ.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "", ";");
    }

    /// <summary>
    /// Tests that info strings for backtick code blocks cannot contain backticks.
    /// </summary>
    @Test
    public void testFencedCodeZ1() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeZ1.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "```aa```"+SOFT_BREAK+"foo");
        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE_CODE, "```aa```"),
            new ExpectedInline(BlockType.INLINE, SOFT_BREAK+"foo")});
    }

    /// <summary>
    /// Tests that info strings for tilde code blocks can contain backticks and tildes.
    /// </summary>
    @Test
    public void testFencedCodeZ2() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeZ2.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "foo", "aa");
    }

    /// <summary>
    /// Tests that closing code fences cannot have info strings.
    /// </summary>
    @Test
    public void testFencedCodeZ3() throws Exception
    {
        MarkdownDocument doc = open("TestFencedCodeZ3.md");

        msAssert.areEqual(1, doc.getCount());
        checkFencedCode(doc.get(0), "``` aaa");
    }

    /// <summary>
    /// Tests parsing of a complex markdown document with FencedCode.
    /// </summary>
    @Test
    public void testFencedCode() throws Exception
    {
        MarkdownDocument doc = super.open("ImportMarkdown\\TestFencedCode.md");

        checkFencedCode(doc.get(0), "Lorem ipsum");
        checkFencedCode(doc.get(1), "dolor", "C#");
        checkFencedCode(doc.get(2), "sit\u000bamet,", "C++");
        checkAtxHeading(doc.get(3), 3, "adipiscing elit,");
        checkFencedCode(doc.get(4), "# eiusmod");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(5);
        checkFencedCode(quoteBlock.get(0), "   incididunt", "Java");

        checkFencedCode(doc.get(6), "> ut labore\u000b> et dlr");
        checkFencedCode(doc.get(7), "quis");
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\Code\\FencedCode\\{0}", fileName));
    }
}

