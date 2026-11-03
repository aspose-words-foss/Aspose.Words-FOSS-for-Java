// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/12/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown Ordered List feature.
/// </summary>
public class TestOrderedListParser extends TestMarkdownParserBase
{
    /// <summary>
    /// Tests simple ordered lists with various markers ('.', ')').
    /// </summary>
    @Test
    public void testOrderedListA() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListA.md");

        checkOrderedListItem(doc.get(0), 1, ListMarker.PARENTHESIS, "Foo");
        checkOrderedListItem(doc.get(1), 2, ListMarker.PARENTHESIS, "bar");
        checkOrderedListItem(doc.get(2), 1, ListMarker.DOT, "baz");
        checkOrderedListItem(doc.get(3), 2, ListMarker.DOT, "bop");

        msAssert.areEqual(4, doc.getCount());
    }

    /// <summary>
    /// Tests how far content must be indented to be put under the list item.
    /// </summary>
    @Test
    public void testOrderedListB() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListB.md");

        checkOrderedListItem(doc.get(0), 1, ListMarker.PARENTHESIS, "one");
        checkParagraph(doc.get(1), "two");

        msAssert.areEqual(2, doc.getCount());
    }

    /// <summary>
    /// Tests how far content must be indented to be put under the list item.
    /// </summary>
    @Test
    public void testOrderedListC() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListC.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());
        checkParagraph(orderedListItemBlock.get(0), "one");
        checkParagraph(orderedListItemBlock.get(1), "two");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests how far content must be indented to be put under the list item.
    /// </summary>
    @Test
    public void testOrderedListD() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListD.md");

        checkOrderedListItem(doc.get(0), 5, ListMarker.DOT, "one");
        checkIndentedCode(doc.get(1),  " two");

        msAssert.areEqual(2, doc.getCount());
    }

    /// <summary>
    /// Tests how far content must be indented to be put under the list item.
    /// </summary>
    @Test
    public void testOrderedListE() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListE.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.PARENTHESIS, orderedListItemBlock.getMarker());
        checkParagraph(orderedListItemBlock.get(0), "one");
        checkParagraph(orderedListItemBlock.get(1), "two");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests how far content must be indented to be put under the list item
    /// when it is embedded in other constructions.
    /// </summary>
    @Test
    public void testOrderedListF() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListF.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        QuoteBlock nestedQuoteBlock = (QuoteBlock)quoteBlock.get(0);

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)nestedQuoteBlock.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());
        checkParagraph(orderedListItemBlock.get(0), "one");
        checkParagraph(orderedListItemBlock.get(1), "two");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests how far content must be indented to be put under the list item
    /// when it is embedded in other constructions.
    /// </summary>
    @Test
    public void testOrderedListG() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListG.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        QuoteBlock nestedQuoteBlock = (QuoteBlock)quoteBlock.get(0);

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)nestedQuoteBlock.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());
        checkParagraph(orderedListItemBlock.get(0), "one");

        checkParagraph(nestedQuoteBlock.get(1), "two");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests that at least one space is needed between the list marker and any following content.
    /// </summary>
    @Test
    public void testOrderedListH() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListH.md");

        checkParagraph(doc.get(0), "1)one");
        checkParagraph(doc.get(1), "2.two");

        msAssert.areEqual(2, doc.getCount());
    }

    /// <summary>
    /// Tests that a list item may contain blocks that are separated by more than one blank line.
    /// </summary>
    @Test
    public void testOrderedListI() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListI.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());

        checkParagraph(orderedListItemBlock.get(0), "foo");
        checkParagraph(orderedListItemBlock.get(1), "bar");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests that a list item may contain any kind of block.
    /// </summary>
    @Test
    public void testOrderedListJ() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListJ.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());

        checkParagraph(orderedListItemBlock.get(0), "foo");
        checkFencedCode(orderedListItemBlock.get(1), "bar");
        checkParagraph(orderedListItemBlock.get(2), "baz");

        QuoteBlock quoteBlock = (QuoteBlock)orderedListItemBlock.get(3);
        checkParagraph(quoteBlock.get(0), "bam");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests that a list item that contains an indented code block will
    /// preserve empty lines within the code block verbatim.
    /// </summary>
    @Test
    public void testOrderedListK() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListK.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());

        checkParagraph(orderedListItemBlock.get(0), "Foo");
        checkIndentedCode(orderedListItemBlock.get(1), "bar\u000b\u000b\u000bbaz");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests that an indented code block will have to be indented four spaces
    /// beyond the edge of the region where text will be included in the list item.
    /// </summary>
    @Test
    public void testOrderedListL() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListL.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());

        checkParagraph(orderedListItemBlock.get(0), "foo");
        checkIndentedCode(orderedListItemBlock.get(1), "bar");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests that if the first block in the list item is an indented code block,
    /// then the contents must be indented one space after the list marker.
    /// </summary>
    @Test
    public void testOrderedListM() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListM.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());

        checkIndentedCode(orderedListItemBlock.get(0), "indented code");
        checkParagraph(orderedListItemBlock.get(1), "paragraph");
        checkIndentedCode(orderedListItemBlock.get(2), "more code");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests that if the first block in the list item is an indented code block,
    /// then the contents must be indented one space after the list marker.
    /// </summary>
    @Test
    public void testOrderedListN() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListN.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());

        checkIndentedCode(orderedListItemBlock.get(0), " indented code");
        checkParagraph(orderedListItemBlock.get(1), "paragraph");
        checkIndentedCode(orderedListItemBlock.get(2), "more code");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests that if the first block in the list item is an indented code block,
    /// then the contents must be indented one space after the list marker.
    /// </summary>
    @Test
    public void testOrderedListO() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListO.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());

        checkParagraph(orderedListItemBlock.get(0), "foo");
        checkParagraph(doc.get(1), "bar");

        msAssert.areEqual(2, doc.getCount());
    }

    /// <summary>
    /// Tests that the indentation can always be removed without a change in interpretation.
    /// </summary>
    @Test
    public void testOrderedListP() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListP.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());

        checkParagraph(orderedListItemBlock.get(0), "foo");
        checkParagraph(orderedListItemBlock.get(1), "bar");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests list items that start with a blank line but are not empty.
    /// </summary>
    @Test
    public void testOrderedListQ() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListQ.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());
        checkParagraph(orderedListItemBlock.get(0), "foo");

        orderedListItemBlock = (OrderedListItemBlock)doc.get(1);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());
        checkFencedCode(orderedListItemBlock.get(0), "bar");

        orderedListItemBlock = (OrderedListItemBlock)doc.get(2);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());
        checkIndentedCode(orderedListItemBlock.get(0), "baz");

        msAssert.areEqual(3, doc.getCount());
    }


    /// <summary>
    /// Tests an empty list item.
    /// </summary>
    @Test
    public void testOrderedListS() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListS.md");

        checkOrderedListItem(doc.get(0), 1, ListMarker.DOT, "foo");
        checkOrderedListItem(doc.get(1), 1, ListMarker.DOT, "");
        checkOrderedListItem(doc.get(2), 1, ListMarker.DOT, "bar");

        msAssert.areEqual(3, doc.getCount());
    }

    /// <summary>
    /// Tests that it does not matter whether there are spaces
    /// following the list marker in an empty list item.
    /// </summary>
    @Test
    public void testOrderedListT() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListT.md");

        checkOrderedListItem(doc.get(0), 1, ListMarker.DOT, "foo");
        checkOrderedListItem(doc.get(1), 2, ListMarker.DOT, "");
        checkOrderedListItem(doc.get(2), 3, ListMarker.DOT, "bar");

        msAssert.areEqual(3, doc.getCount());
    }

    /// <summary>
    /// Tests that a list may start or end with an empty list item.
    /// </summary>
    @Test
    public void testOrderedListU() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListU.md");

        checkOrderedListItem(doc.get(0), 1, ListMarker.PARENTHESIS, "");
        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests that an empty list item cannot interrupt a paragraph.
    /// </summary>
    @Test
    public void testOrderedListV() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListV.md");

        checkParagraph(doc.get(0), "foo"+SOFT_BREAK+"1.");
        checkParagraph(doc.get(1), "foo"+SOFT_BREAK+"2.");
        msAssert.areEqual(2, doc.getCount());
    }

    /// <summary>
    /// Tests that four spaces indent gives a code block instead of list item.
    /// </summary>
    @Test
    public void testOrderedListW() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListW.md");

        checkIndentedCode(doc.get(0), "1.  A paragraph\u000b    with two lines.\u000b\u000b        indented code\u000b\u000b    > A block quote.");
        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests that indentation can be partially deleted.
    /// </summary>
    @Test
    public void testOrderedListX() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListX.md");

        checkOrderedListItem(doc.get(0), 1, ListMarker.DOT, "A paragraph"+SOFT_BREAK+"with two lines.");
        msAssert.areEqual(1, doc.getCount());
    }


    /// <summary>
    /// Tests how laziness can work in nested structures.
    /// </summary>
    @Test
    public void testOrderedListZ() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)quoteBlock.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());

        QuoteBlock nestedQuoteBlock = (QuoteBlock)orderedListItemBlock.get(0);
        checkParagraph(nestedQuoteBlock.get(0), "Blockquote"+SOFT_BREAK+"continued here.");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests nested lists.
    /// </summary>
    @Test
    public void testOrderedListZ1() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ1.md");

        // Root list.
        OrderedListItemBlock bulletListItemBlock = (OrderedListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.DOT, bulletListItemBlock.getMarker());
        checkParagraph(bulletListItemBlock.get(0), "foo");

        // The first nesting level.
        bulletListItemBlock = (OrderedListItemBlock)bulletListItemBlock.get(1);
        msAssert.areEqual(ListMarker.DOT, bulletListItemBlock.getMarker());
        checkParagraph(bulletListItemBlock.get(0), "bar");

        // The second nesting level.
        bulletListItemBlock = (OrderedListItemBlock)bulletListItemBlock.get(1);
        msAssert.areEqual(ListMarker.DOT, bulletListItemBlock.getMarker());
        checkParagraph(bulletListItemBlock.get(0), "baz");

        // The third nesting level.
        bulletListItemBlock = (OrderedListItemBlock)bulletListItemBlock.get(1);
        msAssert.areEqual(ListMarker.DOT, bulletListItemBlock.getMarker());
        checkParagraph(bulletListItemBlock.get(0), "boo");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests that one indentation is not enough to make lists nested.
    /// </summary>
    @Test
    public void testOrderedListZ2() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ2.md");

        checkOrderedListItem(doc.get(0), 1, ListMarker.DOT, "foo");
        checkOrderedListItem(doc.get(1), 1, ListMarker.DOT, "bar");
        checkOrderedListItem(doc.get(2), 1, ListMarker.DOT, "baz");

        msAssert.areEqual(3, doc.getCount());
    }

    /// <summary>
    /// Tests that a list may be the first block in a list item.
    /// </summary>
    @Test
    public void testOrderedListZ3() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ3.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());

        checkOrderedListItem(orderedListItemBlock.get(0), 7, ListMarker.PARENTHESIS, "foo");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests that a list item can contain a heading.
    /// </summary>
    @Test
    public void testOrderedListZ4() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ4.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());
        checkAtxHeading(orderedListItemBlock.get(0), 1, "Foo");

        orderedListItemBlock = (OrderedListItemBlock)doc.get(1);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());
        checkSetextHeading(orderedListItemBlock.get(0), 2, "Bar");
        checkParagraph(orderedListItemBlock.get(1), "baz");

        msAssert.areEqual(2, doc.getCount());
    }

    /// <summary>
    /// Tests that ordered list can interrupt a paragraph only if it starts at 1.
    /// </summary>
    @Test
    public void testOrderedListZ5() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ5.md");

        checkParagraph(doc.get(0), "Foo"+SOFT_BREAK+"2. bar");
        checkOrderedListItem(doc.get(1), 1, ListMarker.DOT, "baz");

        msAssert.areEqual(2, doc.getCount());
    }

    /// <summary>
    /// Tests that there can be any number of blank lines between items.
    /// </summary>
    @Test
    public void testOrderedListZ6() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ6.md");

        checkOrderedListItem(doc.get(0), 1, ListMarker.PARENTHESIS, "foo");
        checkOrderedListItem(doc.get(1), 2, ListMarker.PARENTHESIS, "bar");
        checkOrderedListItem(doc.get(2), 3, ListMarker.PARENTHESIS, "baz");

        msAssert.areEqual(3, doc.getCount());
    }

    /// <summary>
    /// Tests that there can be any number of blank lines between items in nested lists.
    /// </summary>
    @Test
    public void testOrderedListZ7() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ7.md");

        // Root list.
        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());
        checkParagraph(orderedListItemBlock.get(0), "foo");

        // The first nesting level.
        orderedListItemBlock = (OrderedListItemBlock)orderedListItemBlock.get(1);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());
        checkParagraph(orderedListItemBlock.get(0), "bar");

        // The second nesting level.
        orderedListItemBlock = (OrderedListItemBlock)orderedListItemBlock.get(1);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());
        checkParagraph(orderedListItemBlock.get(0), "baz");
        checkParagraph(orderedListItemBlock.get(1), "bim");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests that to separate consecutive lists of the same type, or to separate a list from
    /// an indented code block that would otherwise be parsed as a subparagraph of the final
    /// list item, you can insert a blank HTML comment.
    /// </summary>
    /// <remarks>While HTML feature is not implemented, the HTML snippet is parsed as a paragraph.</remarks>
    @Test
    public void testOrderedListZ8() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ8.md");

        msAssert.areEqual(5, doc.getCount());

        checkOrderedListItem(doc.get(0), 1, ListMarker.DOT, "foo");
        checkOrderedListItem(doc.get(1), 2, ListMarker.DOT, "bar");
        checkParagraph(doc.get(2), "");
        checkOrderedListItem(doc.get(3), 3, ListMarker.DOT, "baz");
        checkOrderedListItem(doc.get(4), 4, ListMarker.DOT, "bim");
    }

    /// <summary>
    /// Tests that list items need not be indented to the same level. The following list items will be treated
    /// as items at the same list level, since none is indented enough to belong to the previous list item.
    /// </summary>
    @Test
    public void testOrderedListZ9() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ9.md");

        checkOrderedListItem(doc.get(0), 1, ListMarker.DOT, "a");
        checkOrderedListItem(doc.get(1), 2, ListMarker.DOT, "b");
        checkOrderedListItem(doc.get(2), 3, ListMarker.DOT, "c");

        msAssert.areEqual(3, doc.getCount());
    }

    /// <summary>
    /// Tests that list items may not be indented more than three spaces.
    /// </summary>
    @Test
    public void testOrderedListZ10() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ10.md");

        checkOrderedListItem(doc.get(0), 1, ListMarker.DOT, "a");
        checkOrderedListItem(doc.get(1), 2, ListMarker.DOT, "b");
        // Here, 3. c is treated as in indented code block,
        // because it is indented four spaces and preceded by a blank line.
        checkIndentedCode(doc.get(2), "3. c");

        msAssert.areEqual(3, doc.getCount());
    }

    /// <summary>
    /// Tests list items with tab indentation.
    /// </summary>
    @Test
    public void testOrderedListZ11() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ11.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());
        checkParagraph(orderedListItemBlock.get(0), "Foo");
        checkParagraph(orderedListItemBlock.get(1), "bar");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests that horizontal rule falls under a list item in Quote if it has needed indentation.
    /// </summary>
    @Test
    public void testOrderedListZ12() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ12.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)quoteBlock.get(0);
        msAssert.areEqual(ListMarker.DOT, orderedListItemBlock.getMarker());
        checkIndentedCode(orderedListItemBlock.get(0), "   foo");
        checkHorizontalRule(orderedListItemBlock.get(1));

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests nested Quotes and Lists with empty items.
    /// </summary>
    @Test
    public void testOrderedListZ13() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ13.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)quoteBlock.get(0);
        QuoteBlock nestedQuoteBlock = (QuoteBlock)orderedListItemBlock.get(0);
        nestedQuoteBlock = (QuoteBlock)nestedQuoteBlock.get(0);
        checkOrderedListItem(nestedQuoteBlock.get(0), 1, ListMarker.DOT, "foo");

        orderedListItemBlock = (OrderedListItemBlock)quoteBlock.get(1);
        nestedQuoteBlock = (QuoteBlock)orderedListItemBlock.get(0);
        nestedQuoteBlock = (QuoteBlock)nestedQuoteBlock.get(0);
        checkParagraph(nestedQuoteBlock.get(0), "bar");
    }

    /// <summary>
    /// Tests nested Quotes and Lists with non-empty items.
    /// </summary>
    @Test
    public void testOrderedListZ14() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ14.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkOrderedListItem(quoteBlock.get(0), 1, ListMarker.DOT, "foo >> 1. bar");
        checkOrderedListItem(quoteBlock.get(1), 1, ListMarker.DOT, "bob >>   baz");
    }

    /// <summary>
    /// Tests bullet list many levels.
    /// </summary>
    @Test
    public void testOrderedListZ15() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ15.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        checkParagraph(orderedListItemBlock.get(0), "one");

        orderedListItemBlock = (OrderedListItemBlock)orderedListItemBlock.get(1);
        checkParagraph(orderedListItemBlock.get(0), "two");

        orderedListItemBlock = (OrderedListItemBlock)orderedListItemBlock.get(1);
        checkParagraph(orderedListItemBlock.get(0), "three");

        orderedListItemBlock = (OrderedListItemBlock)orderedListItemBlock.get(1);
        checkParagraph(orderedListItemBlock.get(0), "four");

        orderedListItemBlock = (OrderedListItemBlock)orderedListItemBlock.get(1);
        checkParagraph(orderedListItemBlock.get(0), "five");

        orderedListItemBlock = (OrderedListItemBlock)orderedListItemBlock.get(1);
        checkParagraph(orderedListItemBlock.get(0), "six");

        orderedListItemBlock = (OrderedListItemBlock)orderedListItemBlock.get(1);
        checkParagraph(orderedListItemBlock.get(0), "seven");

        orderedListItemBlock = (OrderedListItemBlock)orderedListItemBlock.get(1);
        checkParagraph(orderedListItemBlock.get(0), "eight");

        orderedListItemBlock = (OrderedListItemBlock)orderedListItemBlock.get(1);
        checkParagraph(orderedListItemBlock.get(0), "nine");

        orderedListItemBlock = (OrderedListItemBlock)orderedListItemBlock.get(1);
        checkParagraph(orderedListItemBlock.get(0), "ten");

        msAssert.areEqual(1, doc.getCount());
    }


    /// <summary>
    /// Tests nested list items with Headings.
    /// </summary>
    @Test
    public void testOrderedListZ17() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ17.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        checkParagraph(orderedListItemBlock.get(0), "consectetur");

        OrderedListItemBlock nestedOrderedListItemBlock = (OrderedListItemBlock)orderedListItemBlock.get(1);
        checkParagraph(nestedOrderedListItemBlock.get(0), "adipiscing"+SOFT_BREAK+"elit");
        checkAtxHeading(nestedOrderedListItemBlock.get(1), 1, "sed");

        checkSetextHeading(orderedListItemBlock.get(2), 2, "do");
    }

    /// <summary>
    /// Tests that in order to block fall into a list item inside a Quote, this block must be in Quote too.
    /// </summary>
    @Test
    public void testOrderedListZ18() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ18.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)quoteBlock.get(0);
        checkHorizontalRule(orderedListItemBlock.get(0));
        checkParagraph(orderedListItemBlock.get(1), "aliqua.");

        OrderedListItemBlock nestedOrderedListItemBlock = (OrderedListItemBlock)orderedListItemBlock.get(2);
        // The line '1. enim' does not fall under the list item, but is lazy continuation of the previous paragraph.
        checkParagraph(nestedOrderedListItemBlock.get(0), "Ut"+SOFT_BREAK+"1. enim");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests that if paragraph is not in list item, then empty list item cannot interrupt it
    /// and this is a lazy continuation of this paragraph.
    /// </summary>
    @Test
    public void testOrderedListZ19() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ19.md");

        checkParagraph(doc.get(0), "Foo"+SOFT_BREAK+"1.");
        checkOrderedListItem(doc.get(1), 1, ListMarker.DOT, "bar");

        msAssert.areEqual(2, doc.getCount());
    }

    /// <summary>
    /// Tests complex nested bullet lists and quotes.
    /// </summary>
    @Test
    public void testOrderedListZ20() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ20.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);
        checkOrderedListItem(quoteBlock.get(0), 1, ListMarker.DOT, "a");

        checkOrderedListItem(doc.get(1), 1, ListMarker.DOT, "b");

        quoteBlock = (QuoteBlock)doc.get(2);

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)quoteBlock.get(0);
        QuoteBlock nestedQuoteBlock = (QuoteBlock)orderedListItemBlock.get(0);
        orderedListItemBlock = (OrderedListItemBlock)nestedQuoteBlock.get(0);
        nestedQuoteBlock = (QuoteBlock)orderedListItemBlock.get(0);
        checkOrderedListItem(nestedQuoteBlock.get(0), 1, ListMarker.DOT, "c");

        orderedListItemBlock = (OrderedListItemBlock)quoteBlock.get(1);
        OrderedListItemBlock nestedOrderedListItemBlock = (OrderedListItemBlock)orderedListItemBlock.get(0);
        nestedQuoteBlock = (QuoteBlock)nestedOrderedListItemBlock.get(0);
        nestedOrderedListItemBlock = (OrderedListItemBlock)nestedQuoteBlock.get(0);
        nestedOrderedListItemBlock = (OrderedListItemBlock)nestedOrderedListItemBlock.get(0);
        nestedQuoteBlock = (QuoteBlock)nestedOrderedListItemBlock.get(0);
        nestedOrderedListItemBlock = (OrderedListItemBlock)nestedQuoteBlock.get(0);
        checkOrderedListItem(nestedOrderedListItemBlock.get(0), 1, ListMarker.DOT, "d");

        orderedListItemBlock = (OrderedListItemBlock)doc.get(3);
        nestedOrderedListItemBlock = (OrderedListItemBlock)orderedListItemBlock.get(0);
        nestedQuoteBlock = (QuoteBlock)nestedOrderedListItemBlock.get(0);
        nestedOrderedListItemBlock = (OrderedListItemBlock)nestedQuoteBlock.get(0);
        nestedOrderedListItemBlock = (OrderedListItemBlock)nestedOrderedListItemBlock.get(0);
        nestedQuoteBlock = (QuoteBlock)nestedOrderedListItemBlock.get(0);
        nestedOrderedListItemBlock = (OrderedListItemBlock)nestedQuoteBlock.get(0);
        nestedOrderedListItemBlock = (OrderedListItemBlock)nestedOrderedListItemBlock.get(0);
        nestedQuoteBlock = (QuoteBlock)nestedOrderedListItemBlock.get(0);
        checkParagraph(nestedQuoteBlock.get(0), "e");

        msAssert.areEqual(4, doc.getCount());
    }

    /// <summary>
    /// Tests lists in quote with headings.
    /// </summary>
    @Test
    public void testOrderedListZ21() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ21.md");

        QuoteBlock quoteBlock = (QuoteBlock)doc.get(0);

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)quoteBlock.get(0);
        checkParagraph(orderedListItemBlock.get(0), "a");

        orderedListItemBlock = (OrderedListItemBlock)quoteBlock.get(1);
        checkAtxHeading(orderedListItemBlock.get(0), 1, "b");

        orderedListItemBlock = (OrderedListItemBlock)quoteBlock.get(2);
        checkSetextHeading(orderedListItemBlock.get(0), 2, "c");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests how ordered list can break a paragraph.
    /// </summary>
    @Test
    public void testOrderedListZ22() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ22.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        checkSetextHeading(orderedListItemBlock.get(0), 2, "Foo"+SOFT_BREAK+"   2. Bar");
        checkParagraph(orderedListItemBlock.get(1), "baz");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests how ordered list can break a paragraph.
    /// </summary>
    @Test
    public void testOrderedListZ23() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ23.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        checkParagraph(orderedListItemBlock.get(0), "Foo"+SOFT_BREAK+"   2. ```"+SOFT_BREAK+"   Bar");
        checkFencedCode(orderedListItemBlock.get(1), "baz");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests how ordered list can break a paragraph.
    /// </summary>
    @Test
    public void testOrderedListZ24() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ24.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        checkParagraph(orderedListItemBlock.get(0), "Foo");

        // Nested list item.
        orderedListItemBlock = (OrderedListItemBlock)orderedListItemBlock.get(1);
        checkFencedCode(orderedListItemBlock.get(0), "Bar");
        checkParagraph(orderedListItemBlock.get(1), "baz");

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests how ordered list can break a paragraph.
    /// </summary>
    @Test
    public void testOrderedListZ25() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ25.md");

        checkParagraph(doc.get(0), "Foo"+SOFT_BREAK+"2.  ```Bar```"+SOFT_BREAK+"baz");
        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Foo"+SOFT_BREAK+"2.  "),
            new ExpectedInline(BlockType.INLINE_CODE, "```Bar```"),
            new ExpectedInline(BlockType.INLINE, SOFT_BREAK+"baz")});

        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests that ordered list start numbers must be nine digits or less.
    /// </summary>
    @Test
    public void testOrderedListZ26() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ26.md");
        checkOrderedListItem(doc.get(0), 123456789, ListMarker.DOT, "ok");
    }

    /// <summary>
    /// Tests that ordered list start numbers must be nine digits or less.
    /// </summary>
    @Test
    public void testOrderedListZ27() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ27.md");
        checkParagraph(doc.get(0), "1234567890. not ok");
    }

    /// <summary>
    /// Tests that a start number may begin with 0s.
    /// </summary>
    @Test
    public void testOrderedListZ28() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ28.md");
        checkOrderedListItem(doc.get(0), 0, ListMarker.DOT, "ok");
        checkOrderedListItem(doc.get(1), 3, ListMarker.DOT, "ok");
    }

    /// <summary>
    /// Tests that a start number may not be negative.
    /// </summary>
    @Test
    public void testOrderedListZ29() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ29.md");
        checkParagraph(doc.get(0), "-1. not ok");
    }

    /// <summary>
    /// Tests that ordered list can interrupt a paragraph only if it starts at 1 and it is at the very start of the list.
    /// </summary>
    @Test
    public void testOrderedListZ30() throws Exception
    {
        MarkdownDocument doc = open("TestOrderedListZ30.md");

        OrderedListItemBlock orderedListItemBlock = (OrderedListItemBlock)doc.get(0);
        checkParagraph(orderedListItemBlock.get(0), "a");
        checkOrderedListItem(orderedListItemBlock.get(1), 1, ListMarker.DOT, "b");
        checkOrderedListItem(orderedListItemBlock.get(2), 5, ListMarker.PARENTHESIS, "c");

        checkOrderedListItem(doc.get(1), 8, ListMarker.DOT, "d");

        msAssert.areEqual(2, doc.getCount());
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\Lists\\OrderedList\\{0}", fileName));
    }
}

