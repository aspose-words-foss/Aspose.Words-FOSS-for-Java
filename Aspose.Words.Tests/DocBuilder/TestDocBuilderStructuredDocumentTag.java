// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/09/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import org.testng.annotations.DataProvider;


/// <summary>
/// Test moving cursor to a <see cref="StructuredDocumentTag"/>.
/// </summary>
@Test
public class TestDocBuilderStructuredDocumentTag
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }


    /// <summary>
    /// Tests moving the cursor to an inline-level SDT.
    /// </summary>
    @Test
    public void testMovingToInlineLevelSdt() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestSdts.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);
        msAssert.isFalse(builder.isAtEndOfStructuredDocumentTag());

        final int SDT_INDEX = 1;
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getFirstSection().getBody().getStructuredDocumentTags().get(SDT_INDEX);
        msAssert.areEqual(MarkupLevel.INLINE, sdt.getLevel());
        Paragraph paragraph = doc.getFirstSection().getBody().getParagraphs().get(3);
        msAssert.areEqual("Text. Inline-level SDT. Text.\r", paragraph.getText());
        msAssert.areEqual("Inline-level SDT.", sdt.getText());

        builder.moveToStructuredDocumentTag(SDT_INDEX, 0);
        msAssert.isFalse(builder.isAtEndOfStructuredDocumentTag());
        msAssert.areSame(sdt, builder.getCurrentStructuredDocumentTag());
        builder.write("At start. ");

        builder.moveToStructuredDocumentTag(sdt, -1);
        msAssert.isTrue(builder.isAtEndOfStructuredDocumentTag());
        msAssert.areSame(sdt, builder.getCurrentStructuredDocumentTag());
        builder.write(" At end.");
        msAssert.isTrue(builder.isAtEndOfStructuredDocumentTag());
        msAssert.areSame(sdt, builder.getCurrentStructuredDocumentTag());

        msAssert.areEqual("Text. At start. Inline-level SDT. At end. Text.\r", paragraph.getText());
        msAssert.areEqual("At start. Inline-level SDT. At end.", sdt.getText());

        sdt.removeAllChildren();
        msAssert.areEqual("Text.  Text.\r", paragraph.getText());
        msAssert.areEqual("", sdt.getText());

        builder.moveToStructuredDocumentTag(SDT_INDEX, 0);
        builder.write("In SDT.");

        msAssert.areEqual("Text. In SDT. Text.\r", paragraph.getText());
        msAssert.areEqual("In SDT.", sdt.getText());

        sdt.removeAllChildren();
        builder.moveToStructuredDocumentTag(sdt, -1);
        builder.write("In SDT.");

        msAssert.areEqual("Text. In SDT. Text.\r", paragraph.getText());
        msAssert.areEqual("In SDT.", sdt.getText());

        builder.moveToStructuredDocumentTag(sdt, 3);
        builder.write("inline ");

        msAssert.areEqual("Text. In inline SDT. Text.\r", paragraph.getText());
        msAssert.areEqual("In inline SDT.", sdt.getText());

        builder.moveToStructuredDocumentTag(SDT_INDEX, -5);
        builder.write("level ");

        msAssert.areEqual("Text. In inline level SDT. Text.\r", paragraph.getText());
        msAssert.areEqual("In inline level SDT.", sdt.getText());

        // Move to the end of the SDT.
        builder.moveToStructuredDocumentTag(SDT_INDEX, 20);
        msAssert.isTrue(builder.isAtEndOfStructuredDocumentTag());
        msAssert.areSame(sdt, builder.getCurrentStructuredDocumentTag());
        builder.write(" At end.");
        msAssert.isTrue(builder.isAtEndOfStructuredDocumentTag());

        // Move to the beginning of the SDT.
        builder.moveToStructuredDocumentTag(SDT_INDEX, -29);
        msAssert.isFalse(builder.isAtEndOfStructuredDocumentTag());
        msAssert.areSame(sdt, builder.getCurrentStructuredDocumentTag());
        builder.write("At start.");

        msAssert.areEqual("Text. At start.In inline level SDT. At end. Text.\r", paragraph.getText());
        msAssert.areEqual("At start.In inline level SDT. At end.", sdt.getText());

        builder.insertNode(new Run(doc, " "));
        msAssert.areEqual("At start. In inline level SDT. At end.", sdt.getText());
    }

    /// <summary>
    /// Tests moving the cursor to a block-level SDT.
    /// </summary>
    @Test
    public void testMovingToBlockLevelSdt() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestSdts.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        final int SDT_INDEX = 0;
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getFirstSection().getBody().getStructuredDocumentTags().get(SDT_INDEX);
        msAssert.areEqual(MarkupLevel.BLOCK, sdt.getLevel());
        msAssert.areEqual("Block-level structured document tag.\rSecond line.\r", sdt.getText());
        // First paragraph of the document is inside the sdt.
        msAssert.areSame(sdt, builder.getCurrentStructuredDocumentTag());

        builder.moveToStructuredDocumentTag(SDT_INDEX, 0);
        builder.write("At start. ");

        builder.moveToStructuredDocumentTag(sdt, -2);
        builder.write(" At end.");

        msAssert.areEqual("At start. Block-level structured document tag.\rSecond line. At end.\r", sdt.getText());

        builder.moveToStructuredDocumentTag(sdt, 45);
        builder.write(" contains 2 lines");

        builder.moveToStructuredDocumentTag(SDT_INDEX, -11);
        builder.write(" is last");

        msAssert.areEqual("At start. Block-level structured document tag contains 2 lines.\rSecond line is last. At end.\r", sdt.getText());

        // Move cursor outside of the removing nodes.
        builder.moveToParagraph(2, 0);
        sdt.removeAllChildren();
        builder.moveToStructuredDocumentTag(SDT_INDEX, -1);
        msAssert.isTrue(builder.isAtEndOfStructuredDocumentTag());
        msAssert.areSame(sdt, builder.getCurrentStructuredDocumentTag());
        builder.insertParagraph();
        msAssert.isFalse(builder.isAtEndOfStructuredDocumentTag());
        msAssert.areSame(sdt, builder.getCurrentStructuredDocumentTag());
        msAssert.areEqual("\r", sdt.getText());

        sdt.removeAllChildren();
        builder.moveToStructuredDocumentTag(sdt, 0);
        builder.insertParagraph();
        msAssert.areEqual("\r", sdt.getText());

        builder.moveToStructuredDocumentTag(SDT_INDEX, -2);
        builder.write("456");

        builder.moveToStructuredDocumentTag(sdt, 0);
        builder.write("123");

        msAssert.areEqual("123456\r", sdt.getText());

        builder.moveToStructuredDocumentTag(SDT_INDEX, 0);
        builder.insertParagraph();
        builder.write("0");

        builder.moveToStructuredDocumentTag(SDT_INDEX, -1);
        builder.insertParagraph();
        builder.write("7");

        msAssert.areEqual("\r0123456\r7\r", sdt.getText());

        // Move to the end of the SDT.
        builder.moveToStructuredDocumentTag(SDT_INDEX, 11);
        msAssert.isTrue(builder.isAtEndOfStructuredDocumentTag());
        builder.insertParagraph();
        builder.write("8");

        // Move to the beginning of the SDT.
        builder.moveToStructuredDocumentTag(SDT_INDEX, -14);
        msAssert.isFalse(builder.isAtEndOfStructuredDocumentTag());
        builder.write("9");

        msAssert.areEqual("9\r0123456\r7\r8\r", sdt.getText());

        sdt.removeAllChildren();
        builder.moveToStructuredDocumentTag(sdt, 0);
        builder.write("1");
        msAssert.areEqual("1\r", sdt.getText());

        builder.moveToStructuredDocumentTag(sdt, -1);
        msAssert.areSame(sdt, builder.getCurrentStructuredDocumentTag());
        builder.insertNode(new Paragraph(doc));
        msAssert.isTrue(builder.isAtEndOfStructuredDocumentTag());
        msAssert.areEqual("1\r\r", sdt.getText());
    }

    /// <summary>
    /// Tests moving the cursor to a cell-level SDT.
    /// </summary>
    @Test
    public void testMovingToCellLevelSdt() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestSdts.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        final int SDT_INDEX = 6;
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getFirstSection().getBody().getStructuredDocumentTags().get(SDT_INDEX);
        msAssert.areEqual(MarkupLevel.CELL, sdt.getLevel());
        msAssert.areEqual("Cell 1.\u0007", sdt.getText());

        builder.moveToStructuredDocumentTag(SDT_INDEX, 0);
        msAssert.isFalse(builder.isAtEndOfStructuredDocumentTag());
        msAssert.areSame(sdt, builder.getCurrentStructuredDocumentTag());
        builder.write("At start. ");

        builder.moveToStructuredDocumentTag(sdt, -2);
        msAssert.isFalse(builder.isAtEndOfStructuredDocumentTag());
        msAssert.areSame(sdt, builder.getCurrentStructuredDocumentTag());
        builder.write(" At end.");

        msAssert.areEqual("At start. Cell 1. At end.\u0007", sdt.getText());

        builder.moveToStructuredDocumentTag(sdt, 15);
        builder.write("#");

        builder.moveToStructuredDocumentTag(SDT_INDEX, -10);
        builder.write("\rSecond line.");

        msAssert.areEqual("At start. Cell #1.\rSecond line. At end.\u0007", sdt.getText());

        // Move cursor outside of the removing nodes.
        builder.moveToParagraph(2, 0);
        Cell cell = (Cell)sdt.getChild(NodeType.CELL, 0, false);
        cell.getFirstParagraph().remove();
        cell.getFirstParagraph().removeAllChildren();
        msAssert.areEqual("\u0007", sdt.getText());

        builder.moveToStructuredDocumentTag(SDT_INDEX, 0);
        builder.write("456");

        // Move to the beginning of the SDT.
        builder.moveToStructuredDocumentTag(sdt, -5);
        builder.write("123");

        msAssert.areEqual("123456\u0007", sdt.getText());

        builder.moveToStructuredDocumentTag(sdt, -1);
        msAssert.areSame(sdt, builder.getCurrentStructuredDocumentTag());
        sdt.removeAllChildren();
        cell = new Cell(doc);
        builder.insertNode(cell);
        cell.ensureMinimum();
        msAssert.isTrue(builder.isAtEndOfStructuredDocumentTag());
        msAssert.areEqual("\u0007", sdt.getText());
    }

    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QCannot insert text at this cursor position.\\E")
    public void testWritingToCellLevelSdt() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestSdts.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        final int SDT_INDEX = 6;
        builder.moveToStructuredDocumentTag(SDT_INDEX, -1);
        msAssert.isTrue(builder.isAtEndOfStructuredDocumentTag());
        builder.write("123");
    }

    /// <summary>
    /// Tests moving the cursor to a row-level SDT.
    /// </summary>
    @Test
    public void testMovingToRowLevelSdt() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestSdts.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        final int SDT_INDEX = 5;
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getFirstSection().getBody().getStructuredDocumentTags().get(SDT_INDEX);
        Row row = (Row)sdt.getChild(NodeType.ROW, 0, false);
        Cell firstCell = row.getFirstCell();
        Cell middleCell = row.getCells().get(1);
        Cell lastCell = row.getLastCell();
        msAssert.areEqual(MarkupLevel.ROW, sdt.getLevel());
        msAssert.areEqual("Cell 1.\u0007Cell 2.\u0007Cell 3.\u0007\u0007", sdt.getText());

        builder.moveToStructuredDocumentTag(SDT_INDEX, 0);
        msAssert.isFalse(builder.isAtEndOfStructuredDocumentTag());
        builder.write("At start. ");

        builder.moveToStructuredDocumentTag(sdt, -3);
        msAssert.isFalse(builder.isAtEndOfStructuredDocumentTag());
        builder.write(" At end.");

        msAssert.areEqual("At start. Cell 1.\u0007", firstCell.getText());
        msAssert.areEqual("Cell 3. At end.\u0007", lastCell.getText());

        builder.moveToStructuredDocumentTag(sdt, 18);
        builder.write("Start of middle cell. ");

        builder.moveToStructuredDocumentTag(SDT_INDEX, -19);
        builder.write(" End of middle cell.");

        msAssert.areEqual("Start of middle cell. Cell 2. End of middle cell.\u0007", middleCell.getText());

        firstCell.getFirstParagraph().removeAllChildren();
        middleCell.getFirstParagraph().removeAllChildren();
        lastCell.getFirstParagraph().removeAllChildren();
        msAssert.areEqual("\u0007\u0007\u0007\u0007", sdt.getText());

        // Move to the beginning of the SDT.
        builder.moveToStructuredDocumentTag(SDT_INDEX, -5);
        builder.write("123");

        builder.moveToStructuredDocumentTag(sdt, 5);
        builder.write("456");

        msAssert.areEqual("123\u0007\u0007456\u0007\u0007", sdt.getText());

        builder.moveToStructuredDocumentTag(sdt, -1);
        msAssert.areSame(sdt, builder.getCurrentStructuredDocumentTag());
        sdt.removeAllChildren();
        row = new Row(doc);
        builder.insertNode(row);
        msAssert.isTrue(builder.isAtEndOfStructuredDocumentTag());
        msAssert.areEqual("\u0007", sdt.getText());
    }

    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QCannot insert text at this cursor position.\\E")
    public void testWritingToRowLevelSdt() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestSdts.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        final int SDT_INDEX = 5;
        builder.moveToStructuredDocumentTag(SDT_INDEX, -1);
        msAssert.isTrue(builder.isAtEndOfStructuredDocumentTag());
        builder.write("123");
    }

    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThe node must be a block or an inline.\\E")
    public void testMovingToRow() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestSdts.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        final int SDT_INDEX = 5;
        builder.moveToStructuredDocumentTag(SDT_INDEX, -2);
    }

    /// <summary>
    /// Tests that the font properties are taken from an SDT when text is inserted at the end of an empty
    /// inline-level SDT.
    /// </summary>
    @Test
    public void testFontAtEndOfStructuredDocumentTag() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestSdts.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        StructuredDocumentTag blockSdt = (StructuredDocumentTag)doc.getFirstSection().getBody().getStructuredDocumentTags().get(0);
        blockSdt.getContentsFont().setSize(5.0);
        ((Paragraph)blockSdt.getLastNonAnnotationChild()).getParagraphBreakRunPr().setSize(40);
        builder.moveToStructuredDocumentTag(blockSdt, -1);
        builder.write("123");

        StructuredDocumentTag inlineSdt = (StructuredDocumentTag)doc.getFirstSection().getBody().getStructuredDocumentTags().get(1);
        inlineSdt.getContentsFont().setSize(5.0);
        inlineSdt.removeAllChildren();
        builder.moveToStructuredDocumentTag(inlineSdt, -1);
        builder.write("123");

        TestUtil.saveCheckGoldExportOnly(doc, "DocBuilder\\TestFontAtEndOfStructuredDocumentTag.docx");
    }

    /// <summary>
    /// Test a possible user case to insert a row level repeating section SDT.
    /// </summary>
    @Test
    public void testInsertingRepeatingSection() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        msAssert.isNull(builder.getCurrentStructuredDocumentTag());

        CustomXmlPart xmlPart = doc.getCustomXmlParts().add("C8FFD3B6-7EF8-4963-895B-3565F68A0000",
            "<root><row><cell1>Cell 1</cell1><cell2>Cell 2</cell2></row></root>");

        Table table = builder.startTable();
        builder.insertCell();
        builder.write("Header 1");
        builder.insertCell();
        builder.write("Header 2");
        builder.endRow();

        builder.endTable();

        StructuredDocumentTag repeatingSection =
            new StructuredDocumentTag(doc, SdtType.REPEATING_SECTION, MarkupLevel.ROW);
        table.appendChild(repeatingSection);
        repeatingSection.getXmlMapping().setMapping(xmlPart, "/root/row", null);

        StructuredDocumentTag repeatingSectionItem =
            new StructuredDocumentTag(doc, SdtType.REPEATING_SECTION_ITEM, MarkupLevel.ROW);
        repeatingSection.appendChild(repeatingSectionItem);
        Row row = new Row(doc);
        // repeatingSectionItem.AppendChild(row) can be used instead of the following. Just check moving cursor.
        builder.moveToStructuredDocumentTag(repeatingSectionItem, -1);
        msAssert.isTrue(builder.isAtEndOfStructuredDocumentTag());
        msAssert.areSame(repeatingSectionItem, builder.getCurrentStructuredDocumentTag());
        builder.insertNode(row);

        StructuredDocumentTag cell1Sdt = new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.CELL);
        row.appendChild(cell1Sdt);
        cell1Sdt.getXmlMapping().setMapping(xmlPart, "/root/row/cell1", null);

        StructuredDocumentTag cell2Sdt = new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.CELL);
        row.appendChild(cell2Sdt);
        cell2Sdt.getXmlMapping().setMapping(xmlPart, "/root/row/cell2", null);

        doc.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2013);

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Markup\\TestInsertingRepeatingSection.docx");
    }

    /// <summary>
    /// WORDSNET-24490 DocumentBuilder.InsertField methods do not support cursor position at the end of
    /// a structured document tag.
    /// Checks the main issue of the request.
    /// </summary>
    @Test
    public void testInsertingFieldToSdtEnd() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, SdtType.RICH_TEXT, MarkupLevel.INLINE);
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();
        paragraph.appendChild(sdt);

        sdt.isShowingPlaceholderText(false);
        sdt.removeAllChildren();

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToStructuredDocumentTag(sdt, -1);
        builder.insertField("CITATION Don18 \\l 2055 ", "(Donin, Bueno, & Campos, 2018)");

        msAssert.areEqual(1, paragraph.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual(5, sdt.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual(NodeType.FIELD_START, sdt.getFirstChild().getNodeType());
        msAssert.areEqual(NodeType.FIELD_END, sdt.getLastChild().getNodeType());
    }

    /// <summary>
    /// Tests SDT insertion at document end while document end is inside existing SDT.
    /// </summary>
    @Test
    public void testInsertAtDocumentEndInside() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test25317.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToDocumentEnd();
        builder.insertStructuredDocumentTag(SdtType.RICH_TEXT);

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().get(0);
        StructuredDocumentTag newSdt = (StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().get(1);

        // MS Word changes level of existing SDT to inline and moves to previously contained paragraph.
        msAssert.areEqual(MarkupLevel.INLINE, sdt.getLevel());

        // New SDT is inline and is placed right after existing SDT.
        msAssert.areEqual(MarkupLevel.INLINE, newSdt.getLevel());
        msAssert.areEqual(newSdt, sdt.getNextSibling());

        // New SDT inherited formatting.
        msAssert.areEqual("Placeholder Text", ((Run)newSdt.getFirstChild()).getCharacterStyle().getName());
    }


    /// <summary>
    /// Test SDT insertion at existing SDT end.
    /// </summary>
    @Test
    public void testInsertAtSdtEnd() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test25317.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToStructuredDocumentTag((StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().get(0), -1);
        builder.insertStructuredDocumentTag(SdtType.RICH_TEXT);

        // New SDT inherited formatting.
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().get(0);
        StructuredDocumentTag newSdt = (StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().get(1);

        // Existing SDT remains at Block level.
        msAssert.areEqual(MarkupLevel.BLOCK, sdt.getLevel());

        // New SDT is placed as child of existing SDT.
        msAssert.areEqual(sdt, newSdt.getParentNode());
        msAssert.areEqual(MarkupLevel.BLOCK, newSdt.getLevel());

        // New SDT inherited formatting.
        msAssert.areEqual("Caption", ((Paragraph)newSdt.getFirstChild()).getParagraphStyle().getName());
        msAssert.areEqual("Placeholder Text", ((Paragraph)newSdt.getFirstChild()).getFirstRun().getCharacterStyle().getName());
    }

    /// <summary>
    /// Move cursor to the document end, insert paragraph break and then insert new SDT.
    /// Case to produce customer expected output.
    /// </summary>
    /// <remarks>
    /// AM. This case introduces breaking changes in DocumentBuilder.InsertParagraph().
    ///
    /// In order to create expected document we need to insert new paragraph AFTER existing SDT.
    ///
    /// That's why we detect when cursor position at the document end and document end is inside of SDT and
    /// insert new paragraph after SDT. Otherwise there is no way to create document expected by customer.
    /// </remarks>
    @Test
    public void test25317Customer() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test25317.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToDocumentEnd();

        // This paragraph will be inserted AFTER existing SDT.
        builder.insertParagraph();
        builder.insertStructuredDocumentTag(SdtType.RICH_TEXT);

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().get(0);
        StructuredDocumentTag newSdt = (StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().get(1);
        msAssert.areEqual(newSdt.getPreviousSibling(), sdt);    // new SDT is placed AFTER existing SDT.

        // New SDT inherited formatting.
        msAssert.areEqual("Caption", ((Paragraph)newSdt.getFirstChild()).getParagraphStyle().getName());
        msAssert.areEqual("Placeholder Text", ((Paragraph)newSdt.getFirstChild()).getFirstRun().getCharacterStyle().getName());

        // There was some XML mapping issue with output, check by gold.
        TestUtil.saveCheckGold(doc, "Model\\Markup\\Test25317Customer.docx");
    }

    /// <summary>
    /// Tests SDT insertion after new paragraph has been inserted at existing SDT end.
    /// </summary>
    @Test
    public void test25317B() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test25317.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToStructuredDocumentTag((StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().get(0), -1);
        builder.insertParagraph();

        StructuredDocumentTag newSdt = builder.insertStructuredDocumentTag(SdtType.RICH_TEXT);

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().get(0);

        msAssert.areEqual(newSdt.getParentNode(), sdt);    // new SDT is placed INSIDE existing SDT.
        msAssert.areEqual("Caption", ((Paragraph)newSdt.getFirstChild()).getParagraphFormat().getStyleName());

        // There was some XML mapping issue with output, check by gold.
        TestUtil.saveCheckGold(doc, "Model\\Markup\\Test25317B.docx");
    }

    /// <summary>
    /// Tests RichText SDT insertion at different positions.
    /// </summary>
    @Test
    public void test25317RichText() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test25317 Template1.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertStructuredDocumentTag(SdtType.RICH_TEXT);
        builder.moveTo(builder.getCurrentParagraph(), 43);
        builder.insertStructuredDocumentTag(SdtType.RICH_TEXT);

        // Both SDTs should have the same formatting and inserted at Inline level.
        for (IStructuredDocumentTag isdt : doc.getRange().getStructuredDocumentTags())
        {
            StructuredDocumentTag sdt = (StructuredDocumentTag)isdt;
            msAssert.areEqual(MarkupLevel.INLINE, sdt.getLevel());

            msAssert.areEqual(AttrBoolEx.TRUE, sdt.getContentsRunPr().get(FontAttr.ITALIC));
            msAssert.areEqual(AttrBoolEx.TRUE, sdt.getContentsRunPr().get(FontAttr.ITALIC_BI));

            msAssert.areEqual("Placeholder Text", ((Run)sdt.getFirstChild()).getCharacterStyle().getName());
        }
    }

    /// <summary>
    /// Tests RichText SDT insertion at an empty paragraph.
    /// </summary>
    @Test
    public void testInsertAtEmptyParagraph() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test25317 Template1.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToParagraph(1, -1);
        StructuredDocumentTag newSdt = builder.insertStructuredDocumentTag(SdtType.RICH_TEXT);

        // SDT should be inserted at block level and formatting should be inherited.
        msAssert.areEqual(MarkupLevel.BLOCK, newSdt.getLevel());
        msAssert.areEqual("Caption", ((Paragraph)newSdt.getFirstChild()).getParagraphStyle().getName());
        msAssert.areEqual("Placeholder Text", ((Paragraph)newSdt.getFirstChild()).getFirstRun().getCharacterStyle().getName());
    }

    /// <summary>
    /// Tests that both RepeatingSection and RepeatingSectionItem SDTs cannot be inserted.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, dataProvider = "test25317RepeatingSectionDataProvider")
    public void test25317RepeatingSection(/*SdtType*/int type) throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test25317 Template1.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertStructuredDocumentTag(type);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test25317RepeatingSectionDataProvider")
	public static Object[][] test25317RepeatingSectionDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SdtType.REPEATING_SECTION},
			{SdtType.REPEATING_SECTION_ITEM},
		};
	}

    /// <summary>
    /// Tests CheckBox SDT insertion at table cell.
    /// </summary>
    @Test
    public void testInsertCheckboxAtCell() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test25317 Template1.docx");

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToCell(0, 0, 1, 0);

        StructuredDocumentTag newSdt = builder.insertStructuredDocumentTag(SdtType.CHECKBOX);

        // SDT should be inserted at inline level and formatting should be inherited.
        msAssert.areEqual(MarkupLevel.INLINE, newSdt.getLevel());
        msAssert.areEqual(AttrBoolEx.TRUE, newSdt.getContentsRunPr().get(FontAttr.BOLD));
        msAssert.areEqual(AttrBoolEx.TRUE, newSdt.getContentsRunPr().get(FontAttr.BOLD_BI));

        Run sdtRun = (Run)newSdt.getFirstChild();
        msAssert.areEqual(AttrBoolEx.TRUE, sdtRun.getRunPr().get(FontAttr.BOLD));
        msAssert.areEqual(AttrBoolEx.TRUE, sdtRun.getRunPr().get(FontAttr.BOLD_BI));
    }

    /// <summary>
    /// Tests RichText SDT insertion at table cell.
    /// </summary>
    @Test
    public void testInsertRichTextAtCell() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test25317 Template1.docx");

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToCell(0, 0, 1, 0);

        StructuredDocumentTag newSdt = builder.insertStructuredDocumentTag(SdtType.RICH_TEXT);

        // SDT should be inserted at inline level and formatting should be inherited.
        msAssert.areEqual(MarkupLevel.INLINE, newSdt.getLevel());
        msAssert.areEqual(AttrBoolEx.TRUE, newSdt.getContentsRunPr().get(FontAttr.BOLD));
        msAssert.areEqual(AttrBoolEx.TRUE, newSdt.getContentsRunPr().get(FontAttr.BOLD_BI));

        // Character formatting is not applied to placeholder text.
        Run sdtRun = (Run)newSdt.getFirstChild();
        msAssert.isNull(sdtRun.getRunPr().get(FontAttr.BOLD));
        msAssert.isNull(sdtRun.getRunPr().get(FontAttr.BOLD_BI));

        // Character style is applied from placeholder.
        msAssert.areEqual("Placeholder Text", sdtRun.getCharacterStyle().getName());
    }

    /// <summary>
    /// Tests PlainText/RichText SDTs insertion at different positions.
    /// </summary>
    @Test (dataProvider = "testInsertTextSdtsDataProvider")
    public void testInsertTextSdts(/*SdtType*/int type) throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test25317 Template2.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToDocumentStart();
        builder.insertStructuredDocumentTag(type);
        builder.moveToParagraph(0, -1);
        builder.insertStructuredDocumentTag(type);
        builder.moveToParagraph(1, 0);
        builder.insertStructuredDocumentTag(type);
        builder.moveToParagraph(2, 0);
        builder.insertStructuredDocumentTag(type);

        ArrayList<StructuredDocumentTag> sdts =
            doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).<StructuredDocumentTag>toList();

        msAssert.areEqual(MarkupLevel.INLINE, sdts.get(0).getLevel());
        msAssert.areEqual(MarkupLevel.INLINE, sdts.get(1).getLevel());
        msAssert.areEqual(MarkupLevel.BLOCK, sdts.get(2).getLevel());
        msAssert.areEqual(MarkupLevel.BLOCK, sdts.get(3).getLevel());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testInsertTextSdtsDataProvider")
	public static Object[][] testInsertTextSdtsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SdtType.PLAIN_TEXT},
			{SdtType.RICH_TEXT},
		};
	}

    /// <summary>
    /// Tests insertion of SDT having allowed type and represented in MS Word UI.
    /// Checked all these types, behaviour is the same in Word.
    /// </summary>
    @Test (dataProvider = "testInsertAllowedTypesDataProvider")
    public void testInsertAllowedTypes(/*SdtType*/int sdtType) throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestInsertAllowedTypes.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToDocumentStart();
        StructuredDocumentTag sdt1 = builder.insertStructuredDocumentTag(sdtType);

        builder.moveToParagraph(0, -1);
        StructuredDocumentTag sdt2 = builder.insertStructuredDocumentTag(sdtType);

        builder.moveTo(doc.getFirstSection().getBody().getParagraphs().get(1));
        StructuredDocumentTag sdt3 = builder.insertStructuredDocumentTag(sdtType);

        // 1st SDT is inserted at inline level.
        msAssert.areEqual(sdtType, sdt1.getSdtType());
        msAssert.areEqual(MarkupLevel.INLINE, sdt1.getLevel());

        // 2nd SDT is inserted at inline level.
        msAssert.areEqual(sdtType, sdt2.getSdtType());
        msAssert.areEqual(MarkupLevel.INLINE, sdt2.getLevel());

        // 3rd SDT is inserted at block level because paragraph was empty.
        msAssert.areEqual(sdtType, sdt3.getSdtType());
        msAssert.areEqual(MarkupLevel.BLOCK, sdt3.getLevel());

        // All SDT is showing placeholder.
        msAssert.isTrue(sdt1.isShowingPlaceholderText());
        msAssert.isTrue(sdt2.isShowingPlaceholderText());
        msAssert.isTrue(sdt3.isShowingPlaceholderText());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testInsertAllowedTypesDataProvider")
	public static Object[][] testInsertAllowedTypesDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SdtType.RICH_TEXT},
			{SdtType.CHECKBOX},
			{SdtType.PLAIN_TEXT},
			{SdtType.DROP_DOWN_LIST},
			{SdtType.DATE},
			{SdtType.PICTURE},
			{SdtType.COMBO_BOX},
		};
	}

    /// <summary>
    /// WORDSNET-27645 Part of content inserted using DocumentBuilder is outside SDT.
    /// Ensure that whole multi-line text is inserted into SDT.
    /// </summary>
    @Test
    public void test27645() throws Exception
    {
        final String TEXT = "Lorem ipsum dolor sit amet,\r\n pretium.";

        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        StructuredDocumentTag textSdt = new StructuredDocumentTag(doc, SdtType.RICH_TEXT, MarkupLevel.BLOCK);
        doc.getLastSection().getBody().appendChild(textSdt);
        Paragraph paraText2 = new Paragraph(doc);
        textSdt.appendChild(paraText2);
        builder.moveTo(paraText2);
        builder.write(TEXT.replace("\n", ControlChar.LF).replace("\r", ControlChar.CR));

        // Check that whole content is inserted into SDT.
        StructuredDocumentTag sdt = ms.as(doc.getRange().getStructuredDocumentTags().get(0), StructuredDocumentTag.class);
        msAssert.areEqual("Click here to enter text.\rLorem ipsum dolor sit amet,\r pretium.\f", sdt.getText());
    }
    /// <summary>
    /// WORDSNET-29077 Unexpected DocumentBuilder behavior when moving inside SDT.
    /// Improved "last paragraph of last SDT" condition.
    /// </summary>
    @Test
    public void test29077() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test29077.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        sdt.removeAllChildren();

        for (int i = 0; i < 3; i++)
        {
            Paragraph para = new Paragraph(doc);
            para.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_2);
            para.appendChild(new Run(doc, "TOPIC " + i));
            sdt.appendChild(para);

            para = new Paragraph(doc);
            para.getParagraphFormat().setStyle(doc.getStyles().get("Text"));
            para.appendChild(new Run(doc, "my description"));
            sdt.appendChild(para);

            builder.moveTo(para); // moves to end of Paragraph in content element
            builder.writeln();

            for (int itemNo = 0; itemNo < 4; itemNo++)
            {
                builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_3);
                builder.writeln("Item " + itemNo);
                builder.getParagraphFormat().setStyle(doc.getStyles().get("Text"));
                builder.writeln("Item description");
            }
        }

        msAssert.areEqual(33, sdt.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.endWith("Item 3\rItem description\r\r", sdt.getText());
    }
}

