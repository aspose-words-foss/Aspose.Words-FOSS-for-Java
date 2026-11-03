// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/09/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown HorizontalRule feature.
/// </summary>
public class TestHorizontalRuleParser extends TestMarkdownParserBase
{
    /// <summary>
    /// Tests simple horizontal rules.
    /// </summary>
    @Test
    public void testHorizontalRuleA() throws Exception
    {
        MarkdownDocument doc = open("TestHorizontalRuleA.md");

        msAssert.areEqual(3, doc.getCount());
        checkHorizontalRule(doc.get(0));
        checkHorizontalRule(doc.get(1));
        checkHorizontalRule(doc.get(2));
    }

    /// <summary>
    /// Tests wrong characters sequence +++ does not constitute a horizontal rule.
    /// </summary>
    @Test
    public void testHorizontalRuleB() throws Exception
    {
        MarkdownDocument doc = open("TestHorizontalRuleB.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "+++");
    }

    /// <summary>
    /// Tests wrong characters sequence === does not constitute a horizontal rule.
    /// </summary>
    @Test
    public void testHorizontalRuleC() throws Exception
    {
        MarkdownDocument doc = open("TestHorizontalRuleC.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "===");
    }

    /// <summary>
    /// Tests that a sequence of opening characters MUST have a minimum length of 3 to constitute a horizontal rule.
    /// </summary>
    @Test
    public void testHorizontalRuleD() throws Exception
    {
        MarkdownDocument doc = open("TestHorizontalRuleD.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "--"+SOFT_BREAK+"**"+SOFT_BREAK+"__");
    }

    /// <summary>
    /// Tests that one to three spaces indent are allowed in a horizontal rule.
    /// </summary>
    @Test
    public void testHorizontalRuleE() throws Exception
    {
        MarkdownDocument doc = open("TestHorizontalRuleE.md");

        msAssert.areEqual(3, doc.getCount());
        checkHorizontalRule(doc.get(0));
        checkHorizontalRule(doc.get(1));
        checkHorizontalRule(doc.get(2));
    }

    /// <summary>
    /// Tests that four indentation spaces are too many for a horizontal rule.
    /// </summary>
    @Test
    public void testHorizontalRuleF() throws Exception
    {
        MarkdownDocument doc = open("TestHorizontalRuleF.md");

        msAssert.areEqual(1, doc.getCount());
        checkIndentedCode(doc.get(0), "***");
    }

    /// <summary>
    /// Tests that four indentation spaces are too many for a horizontal rule.
    /// </summary>
    @Test
    public void testHorizontalRuleG() throws Exception
    {
        MarkdownDocument doc = open("TestHorizontalRuleG.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "Foo"+SOFT_BREAK+"***");
    }

    /// <summary>
    /// Tests that more than three characters may be used in a horizontal rule.
    /// </summary>
    @Test
    public void testHorizontalRuleH() throws Exception
    {
        MarkdownDocument doc = open("TestHorizontalRuleH.md");

        msAssert.areEqual(1, doc.getCount());
        checkHorizontalRule(doc.get(0));
    }

    /// <summary>
    /// Tests that spaces are allowed between the characters in a horizontal rule.
    /// </summary>
    @Test
    public void testHorizontalRuleI() throws Exception
    {
        MarkdownDocument doc = open("TestHorizontalRuleI.md");

        msAssert.areEqual(3, doc.getCount());
        msAssert.areEqual(BlockType.HORIZONTAL_RULE, doc.get(0).getType());
        msAssert.areEqual(BlockType.HORIZONTAL_RULE, doc.get(1).getType());
        msAssert.areEqual(BlockType.HORIZONTAL_RULE, doc.get(2).getType());
    }

    /// <summary>
    /// Tests that spaces are allowed at the end in a horizontal rule.
    /// </summary>
    @Test
    public void testHorizontalRuleJ() throws Exception
    {
        MarkdownDocument doc = open("TestHorizontalRuleJ.md");

        msAssert.areEqual(1, doc.getCount());
        msAssert.areEqual(BlockType.HORIZONTAL_RULE, doc.get(0).getType());
    }

    /// <summary>
    /// Tests that no other characters may occur in the line in a horizontal rule except of allowed Openings.
    /// </summary>
    @Test
    public void testHorizontalRuleK() throws Exception
    {
        MarkdownDocument doc = open("TestHorizontalRuleK.md");

        msAssert.areEqual(3, doc.getCount());
        checkParagraph(doc.get(0), "_ _ _ _ a");
        checkParagraph(doc.get(1), "a------");
        checkParagraph(doc.get(2), "---a---");
    }

    /// <summary>
    /// Tests that all of the non-whitespace characters MUST be the same.
    /// So, in this test it is not a horizontal rule.
    /// </summary>
    @Test
    public void testHorizontalRuleL() throws Exception
    {
        MarkdownDocument doc = open("TestHorizontalRuleL.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "*-*");
    }

    /// <summary>
    /// Tests that horizontal rules do not need blank lines before or after.
    /// </summary>
    @Test
    public void testHorizontalRuleM() throws Exception
    {
        MarkdownDocument doc = open("TestHorizontalRuleM.md");

        msAssert.areEqual(3, doc.getCount());
        checkBulletListItem(doc.get(0), ListMarker.MINUS, "foo");
        checkHorizontalRule(doc.get(1));
        checkBulletListItem(doc.get(2), ListMarker.MINUS, "bar");
    }

    /// <summary>
    /// Tests that horizontal rules can interrupt a paragraph.
    /// </summary>
    @Test
    public void testHorizontalRuleN() throws Exception
    {
        MarkdownDocument doc = open("TestHorizontalRuleN.md");

        msAssert.areEqual(3, doc.getCount());
        checkParagraph(doc.get(0), "Foo");
        checkHorizontalRule(doc.get(1));
        checkParagraph(doc.get(2), "bar");
    }

    /// <summary>
    /// Tests that if a line of dashes that can be interpreted as either a horizontal rule or the underline of a setext
    /// heading, the interpretation as a setext heading takes precedence.
    /// </summary>
    @Test
    public void testHorizontalRuleO() throws Exception
    {
        MarkdownDocument doc = open("TestHorizontalRuleO.md");

        msAssert.areEqual(2, doc.getCount());
        checkSetextHeading(doc.get(0), 2, "Foo");
        checkParagraph(doc.get(1), "bar");
    }

    /// <summary>
    /// Tests that when both a horizontal rule and a list item are possible interpretations of a line,
    /// the horizontal rule takes precedence.
    /// </summary>
    @Test
    public void testHorizontalRuleP() throws Exception
    {
        MarkdownDocument doc = open("TestHorizontalRuleP.md");

        msAssert.areEqual(3, doc.getCount());
        checkBulletListItem(doc.get(0), ListMarker.ASTERISK, "Foo");
        checkHorizontalRule(doc.get(1));
        checkBulletListItem(doc.get(2), ListMarker.ASTERISK, "Bar");
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\HorizontalRules\\{0}", fileName));
    }
}

