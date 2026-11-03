// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/04/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown Table feature.
/// </summary>
public class TestTableParser extends TestMarkdownParserBase
{
    /// <summary>
    /// Tests simple table without leading and trailing separator.
    /// </summary>
    @Test
    public void testTableA() throws Exception
    {
        MarkdownDocument doc = open("TestTableA.md");
        msAssert.areEqual(1, doc.getCount());

        TableBlock table = (TableBlock)doc.get(0);
        msAssert.areEqual(1, table.getCount());

        RowBlock row = (RowBlock)table.get(0);
        msAssert.areEqual(2, row.getCount());
        msAssert.areEqual("|Foo|bar|", row.getText());
    }

    /// <summary>
    /// Tests simple table consisting only from the two delimiter rows.
    /// </summary>
    @Test
    public void testTableB() throws Exception
    {
        MarkdownDocument doc = open("TestTableB.md");
        msAssert.areEqual(1, doc.getCount());

        TableBlock table = (TableBlock)doc.get(0);
        msAssert.areEqual(1, table.getCount());

        RowBlock row = (RowBlock)table.get(0);
        msAssert.areEqual(2, row.getCount());
        msAssert.areEqual("|-|-|", row.getText());
    }

    /// <summary>
    /// Tests table with header row that has no terminal separators, but with data row that has them.
    /// </summary>
    @Test
    public void testTableC() throws Exception
    {
        MarkdownDocument doc = open("TestTableC.md");
        msAssert.areEqual(1, doc.getCount());

        TableBlock table = (TableBlock)doc.get(0);
        msAssert.areEqual(3, table.getCount());

        msAssert.areEqual("|a|b|", ((RowBlock)table.get(0)).getText());
        msAssert.areEqual("|c|d|", ((RowBlock)table.get(1)).getText());
        msAssert.areEqual("|-|-|", ((RowBlock)table.get(2)).getText());
    }

    /// <summary>
    /// Tests table with many columns.
    /// </summary>
    @Test
    public void testTableD() throws Exception
    {
        MarkdownDocument doc = open("TestTableD.md");
        msAssert.areEqual(1, doc.getCount());

        TableBlock table = (TableBlock)doc.get(0);
        msAssert.areEqual(2, table.getCount());

        msAssert.areEqual("|1|2|3|4|5|6|7|8|9|10|", ((RowBlock)table.get(0)).getText());
        msAssert.areEqual("|11|12|13|14|15|16|17|18|19|20|", ((RowBlock)table.get(1)).getText());
    }

    /// <summary>
    /// Tests number of columns in delimiter and header rows must be equal.
    /// </summary>
    @Test
    public void testTableE() throws Exception
    {
        MarkdownDocument doc = open("TestTableE.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "1|2|3|"+SOFT_BREAK+"|-|-|");
        checkParagraph(doc.get(1), "1|2"+SOFT_BREAK+"|-|-|-");
    }

    /// <summary>
    /// Tests cells with indentation and alignment.
    /// </summary>
    @Test
    public void testTableF() throws Exception
    {
        MarkdownDocument doc = open("TestTableF.md");
        msAssert.areEqual(1, doc.getCount());

        TableBlock table = (TableBlock)doc.get(0);
        msAssert.areEqual(2, table.getCount());

        RowBlock row = (RowBlock)table.get(0);
        msAssert.areEqual("|1|2|3|4|", row.getText());
        msAssert.areEqual(ParagraphAlignment.LEFT, ((CellBlock)row.get(0)).getAlignment());
        msAssert.areEqual(ParagraphAlignment.CENTER, ((CellBlock)row.get(1)).getAlignment());
        msAssert.areEqual(ParagraphAlignment.RIGHT, ((CellBlock)row.get(2)).getAlignment());
        msAssert.areEqual(ParagraphAlignment.CENTER, ((CellBlock)row.get(3)).getAlignment());

        row = (RowBlock)table.get(1);
        msAssert.areEqual("|foo|bar|bop|baz|", row.getText());
        msAssert.areEqual(ParagraphAlignment.LEFT, ((CellBlock)row.get(0)).getAlignment());
        msAssert.areEqual(ParagraphAlignment.CENTER, ((CellBlock)row.get(1)).getAlignment());
        msAssert.areEqual(ParagraphAlignment.RIGHT, ((CellBlock)row.get(2)).getAlignment());
        msAssert.areEqual(ParagraphAlignment.CENTER, ((CellBlock)row.get(3)).getAlignment());
    }

    /// <summary>
    /// Tests how table is created from the paragraph with multiple lines.
    /// </summary>
    @Test
    public void testTableG() throws Exception
    {
        MarkdownDocument doc = open("TestTableG.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "1  |  2  |  3  |");

        TableBlock table = (TableBlock)doc.get(1);
        msAssert.areEqual(2, table.getCount());

        msAssert.areEqual("|4|5|6|", ((RowBlock)table.get(0)).getText());
        msAssert.areEqual("|foo|bar|baz|", ((RowBlock)table.get(1)).getText());
    }

    /// <summary>
    /// Tests that delimiter row can be indented with more than 3 characters.
    /// </summary>
    @Test
    public void testTableH() throws Exception
    {
        MarkdownDocument doc = open("TestTableH.md");
        msAssert.areEqual(1, doc.getCount());

        TableBlock table = (TableBlock)doc.get(0);
        msAssert.areEqual(2, table.getCount());

        msAssert.areEqual("|1|2|", ((RowBlock)table.get(0)).getText());
        msAssert.areEqual("|3|4|", ((RowBlock)table.get(1)).getText());
    }

    /// <summary>
    /// Tests table cannot be created from the indented code block.
    /// </summary>
    @Test
    public void testTableJ() throws Exception
    {
        MarkdownDocument doc = open("TestTableJ.md");
        msAssert.areEqual(2, doc.getCount());

        checkIndentedCode(doc.get(0), "1  |  2 ");
        checkParagraph(doc.get(1), "--|--"+SOFT_BREAK+"3  |  4");
    }

    /// <summary>
    /// Tests that delimiter row must have at least one cell separator to be valid.
    /// </summary>
    @Test
    public void testTableK() throws Exception
    {
        MarkdownDocument doc = open("TestTableK.md");
        msAssert.areEqual(1, doc.getCount());

        checkSetextHeading(doc.get(0), 2, "1|2");
    }

    /// <summary>
    /// Tests recognizing table with cell separator inside inline code block.
    /// </summary>
    @Test (enabled = false, description = "WORDSNET-20302")
    public void testTableL() throws Exception
    {
        MarkdownDocument doc = open("TestTableL.md");
        msAssert.areEqual(1, doc.getCount());

        checkParagraph(doc.get(0), "1`|`2\u000b-|-");
    }

    /// <summary>
    /// Tests that two consequent alignment characters are not allowed in content of cell.
    /// </summary>
    @Test
    public void testTableM() throws Exception
    {
        MarkdownDocument doc = open("TestTableM.md");
        msAssert.areEqual(1, doc.getCount());

        checkParagraph(doc.get(0), "1|2 "+SOFT_BREAK+"-::|-");
    }

    /// <summary>
    /// Tests table is allowed inside Quote.
    /// </summary>
    @Test
    public void testTableN() throws Exception
    {
        MarkdownDocument doc = open("TestTableN.md");
        msAssert.areEqual(1, doc.getCount());

        QuoteBlock quote = (QuoteBlock)doc.get(0);
        TableBlock table = (TableBlock)quote.get(0);

        msAssert.areEqual("|1|2|", ((RowBlock)table.get(0)).getText());
    }

    /// <summary>
    /// Tests that paragraph must be at the same Quote level as the table to became the Row of the table.
    /// </summary>
    @Test
    public void testTableO() throws Exception
    {
        MarkdownDocument doc = open("TestTableO.md");
        msAssert.areEqual(1, doc.getCount());

        QuoteBlock quote = (QuoteBlock)doc.get(0);
        QuoteBlock nestedQuote = (QuoteBlock)quote.get(0);

        TableBlock table = (TableBlock)nestedQuote.get(0);
        msAssert.areEqual("|1|2|", ((RowBlock)table.get(0)).getText());

        checkParagraph(quote.get(1), "3|4");
    }

    /// <summary>
    /// Tests blank line breaks table.
    /// </summary>
    @Test
    public void testTableP() throws Exception
    {
        MarkdownDocument doc = open("TestTableP.md");
        msAssert.areEqual(2, doc.getCount());

        TableBlock table = (TableBlock)doc.get(0);
        msAssert.areEqual("|1|2|", ((RowBlock)table.get(0)).getText());

        table = (TableBlock)doc.get(1);
        msAssert.areEqual("|3|4|", ((RowBlock)table.get(0)).getText());
    }

    /// <summary>
    /// Tests table cannot be inside a list.
    /// </summary>
    @Test
    public void testTableQ() throws Exception
    {
        MarkdownDocument doc = open("TestTableQ.md");
        msAssert.areEqual(2, doc.getCount());

        checkOrderedListItem(doc.get(0), 1, ListMarker.DOT, "1|2");
        checkOrderedListItem(doc.get(1), 1, ListMarker.DOT, "-|-");
    }

    /// <summary>
    /// Tests various formatting of inlines in table cells.
    /// </summary>
    @Test
    public void testTableR() throws Exception
    {
        MarkdownDocument doc = open("TestTableR.md");
        msAssert.areEqual(1, doc.getCount());

        TableBlock table = (TableBlock)doc.get(0);

        RowBlock row = (RowBlock)table.get(0);
        msAssert.areEqual("|f**o**o|b*a*r|b~~a~~z|", row.getText());
        checkInlines(row.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "f"),
            new ExpectedInline(BlockType.BOLD_INLINE, "**o**"),
            new ExpectedInline(BlockType.INLINE, "o")});

        checkInlines(row.get(1),
            new ExpectedInline[]
            {
                new ExpectedInline(BlockType.INLINE, "b"),
                new ExpectedInline(BlockType.ITALIC_INLINE, "*a*"),
                new ExpectedInline(BlockType.INLINE, "r")
            });

        checkInlines(row.get(2),
            new ExpectedInline[]
            {
                new ExpectedInline(BlockType.INLINE, "b"),
                new ExpectedInline(BlockType.STRIKETHROUGH, "~~a~~"),
                new ExpectedInline(BlockType.INLINE, "z")
            });
    }

    /// <summary>
    /// Tests the table is broken at the beginning of another block-level structure.
    /// </summary>
    @Test
    public void testTableS() throws Exception
    {
        MarkdownDocument doc = open("TestTableS.md");
        msAssert.areEqual(2, doc.getCount());

        TableBlock table = (TableBlock)doc.get(0);
        QuoteBlock quote = (QuoteBlock)doc.get(1);

        msAssert.areEqual("|abc|def|", ((RowBlock)table.get(0)).getText());
        msAssert.areEqual("|bar|baz|", ((RowBlock)table.get(1)).getText());

        checkParagraph(quote.get(0), "bop");
    }

    /// <summary>
    /// Tests the table is broken at the beginning of another block-level structure.
    /// </summary>
    @Test
    public void testTableT() throws Exception
    {
        MarkdownDocument doc = open("TestTableT.md");
        msAssert.areEqual(3, doc.getCount());

        TableBlock table = (TableBlock)doc.get(0);

        msAssert.areEqual("|abc|def|", ((RowBlock)table.get(0)).getText());
        msAssert.areEqual("|foo|bar|", ((RowBlock)table.get(1)).getText());

        checkParagraph(doc.get(1), "baz");
        checkParagraph(doc.get(2), "bop");
    }

    /// <summary>
    /// Tests that data rows may vary in the number of cells.
    /// </summary>
    @Test
    public void testTableU() throws Exception
    {
        MarkdownDocument doc = open("TestTableU.md");
        msAssert.areEqual(1, doc.getCount());

        TableBlock table = (TableBlock)doc.get(0);

        msAssert.areEqual("|abc|def|", ((RowBlock)table.get(0)).getText());
        msAssert.areEqual("|foo|", ((RowBlock)table.get(1)).getText());
        msAssert.areEqual("|bar|baz|boo|", ((RowBlock)table.get(2)).getText());

        msAssert.areEqual(2, table.getDelimiterRow().getCount());
    }


    /// <summary>
    /// Tests table with inline html. This allows to construct a multi-paragraph and multiline cells.
    /// </summary>
    /// <remarks>
    /// Until inline html is implemented, this will be just single-line cells.
    /// </remarks>
    @Test
    public void testTableW() throws Exception
    {
        MarkdownDocument doc = open("TestTableW.md");
        msAssert.areEqual(1, doc.getCount());

        TableBlock table = (TableBlock)doc.get(0);

        msAssert.areEqual("|Multiline<br>cell|", ((RowBlock)table.get(0)).getText());
        msAssert.areEqual("|<p>Multiparagraph</p><p>cell</p>|", ((RowBlock)table.get(1)).getText());
        msAssert.areEqual("|<p>Multi<br>paragraph<br>and<br>line</p><p>cell</p>|", ((RowBlock)table.get(2)).getText());
    }

    /// <summary>
    /// Tests table combined with Quotes.
    /// </summary>
    @Test
    public void testTableX() throws Exception
    {
        MarkdownDocument doc = open("TestTableX.md");

        QuoteBlock quote = (QuoteBlock)doc.get(0);
        checkParagraph(quote.get(0), "Foo"+SOFT_BREAK+"|bar|"+SOFT_BREAK+"|---|");

        quote = (QuoteBlock)doc.get(1);
        checkParagraph(quote.get(0), "a");

        checkParagraph(doc.get(2), "|b|");

        quote = (QuoteBlock)doc.get(3);
        checkParagraph(quote.get(0), "|-|");

        quote = (QuoteBlock)doc.get(4);
        TableBlock table = (TableBlock)quote.get(0);
        msAssert.areEqual("|baz|", ((RowBlock)table.get(0)).getText());

        checkParagraph(doc.get(5), "bop");

        quote = (QuoteBlock)doc.get(6);
        table = (TableBlock)quote.get(0);
        msAssert.areEqual("|bem|", ((RowBlock)table.get(0)).getText());

        msAssert.areEqual(7, doc.getCount());
    }

    /// <summary>
    /// Tests table surrounded with List.
    /// </summary>
    @Test
    public void testTableY() throws Exception
    {
        MarkdownDocument doc = open("TestTableY.md");

        OrderedListItemBlock orderedList = (OrderedListItemBlock)doc.get(0);
        checkParagraph(orderedList.get(0), "Top level");
        checkOrderedListItem(orderedList.get(1), 1, ListMarker.DOT, "Sub level");

        TableBlock table = (TableBlock)doc.get(1);
        msAssert.areEqual("|Cell A|Cell B|", ((RowBlock)table.get(0)).getText());

        checkOrderedListItem(doc.get(2), 1, ListMarker.DOT, "ItemA");
        checkOrderedListItem(doc.get(3), 1, ListMarker.DOT, "ItemB");

        msAssert.areEqual(4, doc.getCount());
    }

    /// <summary>
    /// Tests table and FencedCode.
    /// </summary>
    @Test
    public void testTableZ() throws Exception
    {
        MarkdownDocument doc = open("TestTableZ.md");

        checkFencedCode(doc.get(0), "|---|\u000bbaz", "|");
        msAssert.areEqual(1, doc.getCount());
    }

    /// <summary>
    /// Tests table and IndentedCode.
    /// </summary>
    @Test
    public void testTableZ1() throws Exception
    {
        MarkdownDocument doc = open("TestTableZ1.md");

        checkIndentedCode(doc.get(0), "|Foo|");
        checkParagraph(doc.get(1), "|---|");
        msAssert.areEqual(2, doc.getCount());
    }

    /// <summary>
    /// Tests table and SetextHeading.
    /// </summary>
    @Test
    public void testTableZ2() throws Exception
    {
        MarkdownDocument doc = open("TestTableZ2.md");

        checkSetextHeading(doc.get(0), 2, "|Foo|");
        checkParagraph(doc.get(1), "|---|");
        msAssert.areEqual(2, doc.getCount());
    }

    /// <summary>
    /// Tests table and AtxHeading.
    /// </summary>
    @Test
    public void testTableZ3() throws Exception
    {
        MarkdownDocument doc = open("TestTableZ3.md");

        checkAtxHeading(doc.get(0), 1, "|Foo|");
        checkParagraph(doc.get(1), "|---|");
        msAssert.areEqual(2, doc.getCount());
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\Tables\\{0}", fileName));
    }
}

