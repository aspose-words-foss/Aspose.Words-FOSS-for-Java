// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/10/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown Bullet List feature.
/// </summary>
public class TestBulletListParser extends TestMarkdownParserBase
{
    /// <summary>
    /// Tests simple bullet lists with various markers (-, +, *).
    /// </summary>
    @Test
    public void testBulletListA() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListA.md");

        msAssert.areEqual(3, doc.getCount());

        checkBulletListItem(doc.get(0), ListMarker.MINUS, "Foo");
        checkBulletListItem(doc.get(1), ListMarker.PLUS, "bar");
        checkBulletListItem(doc.get(2), ListMarker.ASTERISK, "baz");
    }

    /// <summary>
    /// Tests how far content must be indented to be put under the list item.
    /// </summary>
    @Test
    public void testBulletListB() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListB.md");

        msAssert.areEqual(2, doc.getCount());

        checkBulletListItem(doc.get(0), ListMarker.MINUS, "one");
        checkParagraph(doc.get(1), "two");
    }

    /// <summary>
    /// Tests how far content must be indented to be put under the list item.
    /// </summary>
    @Test
    public void testBulletListC() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListC.md");

        msAssert.areEqual(1, doc.getCount());

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());
        checkParagraph(bulletListItemBlock.get(0), "one");
        checkParagraph(bulletListItemBlock.get(1), "two");
    }

    /// <summary>
    /// Tests how far content must be indented to be put under the list item.
    /// </summary>
    @Test
    public void testBulletListD() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListD.md");

        msAssert.areEqual(2, doc.getCount());

        checkBulletListItem(doc.get(0), ListMarker.MINUS, "one");
        checkIndentedCode(doc.get(1),  " two");
    }

    /// <summary>
    /// Tests how far content must be indented to be put under the list item.
    /// </summary>
    @Test
    public void testBulletListE() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListE.md");

        msAssert.areEqual(1, doc.getCount());

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());
        checkParagraph(bulletListItemBlock.get(0), "one");
        checkParagraph(bulletListItemBlock.get(1), "two");
    }

    /// <summary>
    /// Tests how far content must be indented to be put under the list item
    /// when it is embedded in other constructions.
    /// </summary>
    @Test
    public void testBulletListF() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListF.md");

        msAssert.areEqual(1, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        QuoteBlock nestedQuoteBlock = (QuoteBlock)quoteBlock.get(0);

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)nestedQuoteBlock.get(0);
        msAssert.areEqual(ListMarker.ASTERISK, bulletListItemBlock.getMarker());
        checkParagraph(bulletListItemBlock.get(0), "one");
        checkParagraph(bulletListItemBlock.get(1), "two");
    }

    /// <summary>
    /// Tests how far content must be indented to be put under the list item
    /// when it is embedded in other constructions.
    /// </summary>
    @Test
    public void testBulletListG() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListG.md");

        msAssert.areEqual(1, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        QuoteBlock nestedQuoteBlock = (QuoteBlock)quoteBlock.get(0);

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)nestedQuoteBlock.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());
        checkParagraph(bulletListItemBlock.get(0), "one");

        checkParagraph(nestedQuoteBlock.get(1), "two");
    }

    /// <summary>
    /// Tests that at least one space is needed between the list marker and any following content.
    /// </summary>
    @Test
    public void testBulletListH() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListH.md");

        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "-one");
        checkParagraph(doc.get(1), "+two");
    }

    /// <summary>
    /// Tests that a list item may contain blocks that are separated by more than one blank line.
    /// </summary>
    @Test
    public void testBulletListI() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListI.md");

        msAssert.areEqual(1, doc.getCount());

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());

        checkParagraph(bulletListItemBlock.get(0), "foo");
        checkParagraph(bulletListItemBlock.get(1), "bar");
    }

    /// <summary>
    /// Tests that a list item may contain any kind of block.
    /// </summary>
    @Test
    public void testBulletListJ() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListJ.md");

        msAssert.areEqual(1, doc.getCount());

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.PLUS, bulletListItemBlock.getMarker());

        checkParagraph(bulletListItemBlock.get(0), "foo");
        checkFencedCode(bulletListItemBlock.get(1), "bar");
        checkParagraph(bulletListItemBlock.get(2), "baz");

        QuoteBlock quoteBlock = (QuoteBlock)bulletListItemBlock.get(3);
        checkParagraph(quoteBlock.get(0), "bam");
    }

    /// <summary>
    /// Tests that a list item that contains an indented code block will
    /// preserve empty lines within the code block verbatim.
    /// </summary>
    @Test
    public void testBulletListK() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListK.md");

        msAssert.areEqual(1, doc.getCount());

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());

        checkParagraph(bulletListItemBlock.get(0), "Foo");
        checkIndentedCode(bulletListItemBlock.get(1), "bar\u000b\u000b\u000bbaz");
    }

    /// <summary>
    /// Tests that an indented code block will have to be indented four spaces
    /// beyond the edge of the region where text will be included in the list item.
    /// </summary>
    @Test
    public void testBulletListL() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListL.md");

        msAssert.areEqual(1, doc.getCount());

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());

        checkParagraph(bulletListItemBlock.get(0), "foo");
        checkIndentedCode(bulletListItemBlock.get(1), "bar");
    }

    /// <summary>
    /// Tests that if the first block in the list item is an indented code block,
    /// then the contents must be indented one space after the list marker.
    /// </summary>
    @Test
    public void testBulletListM() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListM.md");

        msAssert.areEqual(3, doc.getCount());

        checkIndentedCode(doc.get(0), "indented code");
        checkParagraph(doc.get(1), "paragraph");
        checkIndentedCode(doc.get(2), "more code");
    }

    /// <summary>
    /// Tests that if the first block in the list item is an indented code block,
    /// then the contents must be indented one space after the list marker.
    /// </summary>
    @Test
    public void testBulletListN() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListN.md");

        msAssert.areEqual(1, doc.getCount());

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.PLUS, bulletListItemBlock.getMarker());

        checkIndentedCode(bulletListItemBlock.get(0), " indented code");
        checkParagraph(bulletListItemBlock.get(1), "paragraph");
        checkIndentedCode(bulletListItemBlock.get(2), " more code");
    }

    /// <summary>
    /// Tests that if the first block in the list item is an indented code block,
    /// then the contents must be indented one space after the list marker.
    /// </summary>
    @Test
    public void testBulletListO() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListO.md");

        msAssert.areEqual(2, doc.getCount());

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());

        checkParagraph(bulletListItemBlock.get(0), "foo");
        checkParagraph(doc.get(1), "bar");
    }

    /// <summary>
    /// Tests that the indentation can always be removed without a change in interpretation.
    /// </summary>
    @Test
    public void testBulletListP() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListP.md");

        msAssert.areEqual(1, doc.getCount());

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());

        checkParagraph(bulletListItemBlock.get(0), "foo");
        checkParagraph(bulletListItemBlock.get(1), "bar");
    }

    /// <summary>
    /// Tests list items that start with a blank line but are not empty.
    /// </summary>
    @Test
    public void testBulletListQ() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListQ.md");

        msAssert.areEqual(3, doc.getCount());

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());
        checkParagraph(bulletListItemBlock.get(0), "foo");

        bulletListItemBlock = (BulletListItemBlock)doc.get(1);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());
        checkFencedCode(bulletListItemBlock.get(0), "bar");

        bulletListItemBlock = (BulletListItemBlock)doc.get(2);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());
        checkIndentedCode(bulletListItemBlock.get(0), "baz");
    }

    /// <summary>
    /// Tests that a list item can begin with at most one blank line.
    /// </summary>
    @Test
    public void testBulletListR() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListR.md");

        msAssert.areEqual(2, doc.getCount());

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());
        msAssert.areEqual(0, bulletListItemBlock.getCount());

        checkParagraph(doc.get(1), "foo");
    }

    /// <summary>
    /// Tests an empty bullet list item.
    /// </summary>
    @Test
    public void testBulletListS() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListS.md");

        msAssert.areEqual(3, doc.getCount());

        checkBulletListItem(doc.get(0), ListMarker.MINUS, "foo");
        checkBulletListItem(doc.get(1), ListMarker.MINUS, "");
        checkBulletListItem(doc.get(2), ListMarker.MINUS, "bar");
    }

    /// <summary>
    /// Tests that it does not matter whether there are spaces
    /// following the list marker in an empty bullet list item.
    /// </summary>
    @Test
    public void testBulletListT() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListT.md");

        msAssert.areEqual(3, doc.getCount());

        checkBulletListItem(doc.get(0), ListMarker.MINUS, "foo");
        checkBulletListItem(doc.get(1), ListMarker.MINUS, "");
        checkBulletListItem(doc.get(2), ListMarker.MINUS, "bar");
    }

    /// <summary>
    /// Tests that a list may start or end with an empty list item.
    /// </summary>
    @Test
    public void testBulletListU() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListU.md");

        msAssert.areEqual(1, doc.getCount());
        checkBulletListItem(doc.get(0), ListMarker.ASTERISK, "");
    }

    /// <summary>
    /// Tests that an empty list item cannot interrupt a paragraph.
    /// </summary>
    @Test
    public void testBulletListV() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListV.md");

        msAssert.areEqual(2, doc.getCount());
        checkParagraph(doc.get(0), "foo"+SOFT_BREAK+"*");
        checkParagraph(doc.get(1), "foo"+SOFT_BREAK+"+");
    }

    /// <summary>
    /// Tests that four spaces indent gives a code block instead of list item.
    /// </summary>
    @Test
    public void testBulletListW() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListW.md");

        msAssert.areEqual(1, doc.getCount());
        checkIndentedCode(doc.get(0), "-   A paragraph\u000b    with two lines.\u000b\u000b        indented code\u000b\u000b    > A block quote.");
    }

    /// <summary>
    /// Tests that indentation can be partially deleted.
    /// </summary>
    @Test
    public void testBulletListX() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListX.md");

        msAssert.areEqual(1, doc.getCount());
        checkBulletListItem(doc.get(0), ListMarker.PLUS, "A paragraph"+SOFT_BREAK+"with two lines.");
    }

    /// <summary>
    /// Tests how laziness can work in nested structures.
    /// </summary>
    @Test
    public void testBulletListY() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListY.md");

        msAssert.areEqual(1, doc.getCount());
        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)quoteBlock.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());

        QuoteBlock nestedQuoteBlock = (QuoteBlock)bulletListItemBlock.get(0);
        checkParagraph(nestedQuoteBlock.get(0), "Blockquote"+SOFT_BREAK+"continued here.");
    }

    /// <summary>
    /// Tests how laziness can work in nested structures.
    /// </summary>
    @Test
    public void testBulletListZ() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ.md");

        msAssert.areEqual(1, doc.getCount());
        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)quoteBlock.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());

        QuoteBlock nestedQuoteBlock = (QuoteBlock)bulletListItemBlock.get(0);
        checkParagraph(nestedQuoteBlock.get(0), "Blockquote"+SOFT_BREAK+"continued here.");
    }

    /// <summary>
    /// Tests nested lists.
    /// </summary>
    @Test
    public void testBulletListZ1() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ1.md");

        msAssert.areEqual(1, doc.getCount());

        // Root list.
        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());
        checkParagraph(bulletListItemBlock.get(0), "foo");

        // The first nesting level.
        bulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(1);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());
        checkParagraph(bulletListItemBlock.get(0), "bar");

        // The second nesting level.
        bulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(1);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());
        checkParagraph(bulletListItemBlock.get(0), "baz");

        // The third nesting level.
        bulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(1);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());
        checkParagraph(bulletListItemBlock.get(0), "boo");
    }

    /// <summary>
    /// Tests that one indentation is not enough to make lists nested.
    /// </summary>
    @Test
    public void testBulletListZ2() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ2.md");

        msAssert.areEqual(4, doc.getCount());

        checkBulletListItem(doc.get(0), ListMarker.MINUS, "foo");
        checkBulletListItem(doc.get(1), ListMarker.MINUS, "bar");
        checkBulletListItem(doc.get(2), ListMarker.MINUS, "baz");
        checkBulletListItem(doc.get(3), ListMarker.MINUS, "boo");
    }

    /// <summary>
    /// Tests that a list may be the first block in a list item.
    /// </summary>
    @Test
    public void testBulletListZ3() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ3.md");

        msAssert.areEqual(1, doc.getCount());

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());

        checkBulletListItem(bulletListItemBlock.get(0), ListMarker.MINUS, "foo");
    }

    /// <summary>
    /// Tests that a list item can contain a heading.
    /// </summary>
    @Test
    public void testBulletListZ4() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ4.md");

        msAssert.areEqual(2, doc.getCount());

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());
        checkAtxHeading(bulletListItemBlock.get(0), 1, "Foo");

        bulletListItemBlock = (BulletListItemBlock)doc.get(1);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());
        checkSetextHeading(bulletListItemBlock.get(0), 2, "Bar");
        checkParagraph(bulletListItemBlock.get(1), "baz");
    }

    /// <summary>
    /// Tests that a list can interrupt a paragraph. That is, no blank line
    /// is needed to separate a paragraph from a following list.
    /// </summary>
    @Test
    public void testBulletListZ5() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ5.md");

        msAssert.areEqual(3, doc.getCount());

        checkParagraph(doc.get(0), "Foo");
        checkBulletListItem(doc.get(1), ListMarker.MINUS, "bar");
        checkBulletListItem(doc.get(2), ListMarker.MINUS, "baz");
    }

    /// <summary>
    /// Tests that there can be any number of blank lines between items.
    /// </summary>
    @Test
    public void testBulletListZ6() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ6.md");

        msAssert.areEqual(3, doc.getCount());

        checkBulletListItem(doc.get(0), ListMarker.MINUS, "foo");
        checkBulletListItem(doc.get(1), ListMarker.MINUS, "bar");
        checkBulletListItem(doc.get(2), ListMarker.MINUS, "baz");
    }

    /// <summary>
    /// Tests that there can be any number of blank lines between items in nested lists.
    /// </summary>
    @Test
    public void testBulletListZ7() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ7.md");

        msAssert.areEqual(1, doc.getCount());

        // Root list.
        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());
        checkParagraph(bulletListItemBlock.get(0), "foo");

        // The first nesting level.
        bulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(1);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());
        checkParagraph(bulletListItemBlock.get(0), "bar");

        // The second nesting level.
        bulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(1);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());
        checkParagraph(bulletListItemBlock.get(0), "baz");
        checkParagraph(bulletListItemBlock.get(1), "bim");
    }

    /// <summary>
    /// Tests that to separate consecutive lists of the same type, or to separate a list from
    /// an indented code block that would otherwise be parsed as a subparagraph of the final
    /// list item, you can insert a blank HTML comment.
    /// </summary>
    @Test
    public void testBulletListZ8() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ8.md");

        msAssert.areEqual(5, doc.getCount());

        checkBulletListItem(doc.get(0), ListMarker.MINUS, "foo");
        checkBulletListItem(doc.get(1), ListMarker.MINUS, "bar");
        checkParagraph(doc.get(2), "");
        checkBulletListItem(doc.get(3), ListMarker.MINUS, "baz");
        checkBulletListItem(doc.get(4), ListMarker.MINUS, "bim");
    }

    /// <summary>
    /// Tests that list items need not be indented to the same level. The following list items will be treated
    /// as items at the same list level, since none is indented enough to belong to the previous list item.
    /// </summary>
    @Test
    public void testBulletListZ9() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ9.md");

        msAssert.areEqual(7, doc.getCount());

        checkBulletListItem(doc.get(0), ListMarker.MINUS, "a");
        checkBulletListItem(doc.get(1), ListMarker.MINUS, "b");
        checkBulletListItem(doc.get(2), ListMarker.MINUS, "c");
        checkBulletListItem(doc.get(3), ListMarker.MINUS, "d");
        checkBulletListItem(doc.get(4), ListMarker.MINUS, "e");
        checkBulletListItem(doc.get(5), ListMarker.MINUS, "f");
        checkBulletListItem(doc.get(6), ListMarker.MINUS, "g");
    }

    /// <summary>
    /// Tests that list items may not be indented more than three spaces.
    /// </summary>
    @Test
    public void testBulletListZ10() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ10.md");

        msAssert.areEqual(4, doc.getCount());

        checkBulletListItem(doc.get(0), ListMarker.MINUS, "a");
        checkBulletListItem(doc.get(1), ListMarker.MINUS, "b");
        checkBulletListItem(doc.get(2), ListMarker.MINUS, "c");
        //  Here - e is treated as a paragraph continuation line, because it is indented more than three spaces.
        checkBulletListItem(doc.get(3), ListMarker.MINUS, "d"+SOFT_BREAK+"- e");
    }

    /// <summary>
    /// Tests list items with tab indentation.
    /// </summary>
    @Test
    public void testBulletListZ11() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ11.md");

        msAssert.areEqual(1, doc.getCount());

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.MINUS, bulletListItemBlock.getMarker());
        checkParagraph(bulletListItemBlock.get(0), "Foo");
        checkParagraph(bulletListItemBlock.get(1), "bar");
    }

    /// <summary>
    /// Tests that horizontal rule falls under a list item in Quote if it has needed indentation.
    /// </summary>
    @Test
    public void testBulletListZ12() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ12.md");

        msAssert.areEqual(1, doc.getCount());

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)quoteBlock.get(0);
        msAssert.areEqual(ListMarker.ASTERISK, bulletListItemBlock.getMarker());
        checkIndentedCode(bulletListItemBlock.get(0), "   foo");
        checkHorizontalRule(bulletListItemBlock.get(1));
    }

    /// <summary>
    /// Tests nested Quotes and Lists with empty items.
    /// </summary>
    @Test
    public void testBulletListZ13() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ13.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)quoteBlock.get(0);
        QuoteBlock nestedQuoteBlock = (QuoteBlock)bulletListItemBlock.get(0);
        nestedQuoteBlock = (QuoteBlock)nestedQuoteBlock.get(0);
        checkBulletListItem(nestedQuoteBlock.get(0), ListMarker.MINUS, "foo");

        bulletListItemBlock = (BulletListItemBlock)quoteBlock.get(1);
        nestedQuoteBlock = (QuoteBlock)bulletListItemBlock.get(0);
        nestedQuoteBlock = (QuoteBlock)nestedQuoteBlock.get(0);
        checkParagraph(nestedQuoteBlock.get(0), "bar");
    }

    /// <summary>
    /// Tests nested Quotes and Lists with non-empty items.
    /// </summary>
    @Test
    public void testBulletListZ14() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ14.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkBulletListItem(quoteBlock.get(0), ListMarker.MINUS, "foo >> - bar");
        checkBulletListItem(quoteBlock.get(1), ListMarker.MINUS, "bob >>   baz");
    }

    /// <summary>
    /// Tests bullet list many levels.
    /// </summary>
    @Test
    public void testBulletListZ15() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ15.md");

        msAssert.areEqual(1, doc.getCount());

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        checkParagraph(bulletListItemBlock.get(0), "one");

        bulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(1);
        checkParagraph(bulletListItemBlock.get(0), "two");

        bulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(1);
        checkParagraph(bulletListItemBlock.get(0), "three");

        bulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(1);
        checkParagraph(bulletListItemBlock.get(0), "four");

        bulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(1);
        checkParagraph(bulletListItemBlock.get(0), "five");

        bulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(1);
        checkParagraph(bulletListItemBlock.get(0), "six");

        bulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(1);
        checkParagraph(bulletListItemBlock.get(0), "seven");

        bulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(1);
        checkParagraph(bulletListItemBlock.get(0), "eight");

        bulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(1);
        checkParagraph(bulletListItemBlock.get(0), "nine");

        bulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(1);
        checkParagraph(bulletListItemBlock.get(0), "ten");
    }

    /// <summary>
    /// Tests nested bullet list items.
    /// </summary>
    @Test
    public void testBulletListZ16() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ16.md");

        msAssert.areEqual(2, doc.getCount());

        BulletListItemBlock listItemLevel1 = (BulletListItemBlock)doc.get(0);
        checkParagraph(listItemLevel1.get(0), "one");

        BulletListItemBlock listItemLevel2 = (BulletListItemBlock)listItemLevel1.get(1);
        checkParagraph(listItemLevel2.get(0), "two");

        listItemLevel2 = (BulletListItemBlock)listItemLevel1.get(2);
        checkParagraph(listItemLevel2.get(0), "three");

        listItemLevel2 = (BulletListItemBlock)listItemLevel1.get(3);
        checkParagraph(listItemLevel2.get(0), "four"+SOFT_BREAK+"- five");

        BulletListItemBlock listItemLevel3 = (BulletListItemBlock)listItemLevel2.get(1);
        checkParagraph(listItemLevel3.get(0), "six");

        listItemLevel2 = (BulletListItemBlock)listItemLevel1.get(4);
        checkParagraph(listItemLevel2.get(0), "seven");

        listItemLevel3 = (BulletListItemBlock)listItemLevel2.get(1);
        checkParagraph(listItemLevel3.get(0), "eight");

        listItemLevel1 = (BulletListItemBlock)doc.get(1);
        checkParagraph(listItemLevel1.get(0), "nine"+SOFT_BREAK+"- ten");
    }

    /// <summary>
    /// Tests nested bullet list items with Headings.
    /// </summary>
    @Test
    public void testBulletListZ17() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ17.md");

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)doc.get(0);
        checkParagraph(bulletListItemBlock.get(0), "consectetur");

        BulletListItemBlock nestedBulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(1);
        checkParagraph(nestedBulletListItemBlock.get(0), "adipiscing"+SOFT_BREAK+"elit");
        checkAtxHeading(nestedBulletListItemBlock.get(1), 1, "sed");

        checkSetextHeading(bulletListItemBlock.get(2), 2, "do");
    }

    /// <summary>
    /// Tests that in order to block fall into a list item inside a Quote, this block must be in Quote too.
    /// </summary>
    @Test
    public void testBulletListZ18() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ18.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)quoteBlock.get(0);
        checkHorizontalRule(bulletListItemBlock.get(0));
        checkParagraph(bulletListItemBlock.get(1), "aliqua.");

        BulletListItemBlock nestedBulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(2);
        // The line '+ enim' does not fall under the list item, but is lazy continuation of the previous paragraph.
        checkParagraph(nestedBulletListItemBlock.get(0), "Ut"+SOFT_BREAK+"+ enim");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests that if paragraph is not in list item, then empty list item cannot interrupt it
    /// and this is a lazy continuation of this paragraph.
    /// </summary>
    @Test
    public void testBulletListZ19() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ19.md");

        checkParagraph(doc.get(0), "Foo"+SOFT_BREAK+"+");
        checkBulletListItem(doc.get(1), ListMarker.PLUS, "bar");

        msAssert.areEqual(2, doc.getCount());
    }

    /// <summary>
    /// Tests complex nested bullet lists and quotes.
    /// </summary>
    @Test
    public void testBulletListZ20() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ20.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkBulletListItem(quoteBlock.get(0), ListMarker.MINUS, "a");

        checkBulletListItem(doc.get(1), ListMarker.MINUS, "b");

        quoteBlock = (QuoteBlock)doc.get(2);

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)quoteBlock.get(0);
        QuoteBlock nestedQuoteBlock = (QuoteBlock)bulletListItemBlock.get(0);
        bulletListItemBlock = (BulletListItemBlock)nestedQuoteBlock.get(0);
        nestedQuoteBlock = (QuoteBlock)bulletListItemBlock.get(0);
        checkBulletListItem(nestedQuoteBlock.get(0), ListMarker.MINUS, "c");

        bulletListItemBlock = (BulletListItemBlock)quoteBlock.get(1);
        BulletListItemBlock nestedBulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(0);
        nestedQuoteBlock = (QuoteBlock)nestedBulletListItemBlock.get(0);
        nestedBulletListItemBlock = (BulletListItemBlock)nestedQuoteBlock.get(0);
        nestedBulletListItemBlock = (BulletListItemBlock)nestedBulletListItemBlock.get(0);
        nestedQuoteBlock = (QuoteBlock)nestedBulletListItemBlock.get(0);
        nestedBulletListItemBlock = (BulletListItemBlock)nestedQuoteBlock.get(0);
        checkBulletListItem(nestedBulletListItemBlock.get(0), ListMarker.MINUS, "d");

        bulletListItemBlock = (BulletListItemBlock)doc.get(3);
        nestedBulletListItemBlock = (BulletListItemBlock)bulletListItemBlock.get(0);
        nestedQuoteBlock = (QuoteBlock)nestedBulletListItemBlock.get(0);
        nestedBulletListItemBlock = (BulletListItemBlock)nestedQuoteBlock.get(0);
        nestedBulletListItemBlock = (BulletListItemBlock)nestedBulletListItemBlock.get(0);
        nestedQuoteBlock = (QuoteBlock)nestedBulletListItemBlock.get(0);
        nestedBulletListItemBlock = (BulletListItemBlock)nestedQuoteBlock.get(0);
        nestedBulletListItemBlock = (BulletListItemBlock)nestedBulletListItemBlock.get(0);
        nestedQuoteBlock = (QuoteBlock)nestedBulletListItemBlock.get(0);
        checkParagraph(nestedQuoteBlock.get(0), "e");

        msAssert.areEqual(4, doc.getCount());
    }

    /// <summary>
    /// Tests bullet lists in quote with headings.
    /// </summary>
    @Test
    public void testBulletListZ21() throws Exception
    {
        MarkdownDocument doc = open("TestBulletListZ21.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);

        BulletListItemBlock bulletListItemBlock = (BulletListItemBlock)quoteBlock.get(0);
        checkParagraph(bulletListItemBlock.get(0), "a");

        bulletListItemBlock = (BulletListItemBlock)quoteBlock.get(1);
        checkAtxHeading(bulletListItemBlock.get(0), 1, "b");

        bulletListItemBlock = (BulletListItemBlock)quoteBlock.get(2);
        checkSetextHeading(bulletListItemBlock.get(0), 2, "c");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\Lists\\BulletList\\{0}", fileName));
    }
}

