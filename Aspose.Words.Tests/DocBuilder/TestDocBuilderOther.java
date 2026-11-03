// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;
import com.aspose.SystemPal;
import com.aspose.ms.System.msString;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.DrColor;
import org.testng.annotations.DataProvider;


@Test
public class TestDocBuilderOther
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }





    @Test
    public void testMoveToBookmark() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\TestMoveToBookmark.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        msAssert.isTrue(builder.moveToBookmark("Test1", true, false));
        builder.write("[BeforeStart]");
        msAssert.isTrue(builder.moveToBookmark("Test1"));
        msAssert.isFalse(builder.moveToBookmark("NonExistantName"));
        builder.write("[AfterStart]");
        builder.moveToBookmark("Test1", false, false);
        builder.write("[BeforeEnd]");
        builder.moveToBookmark("Test1", false, true);
        builder.write("[AfterEnd]");

        doc = TestUtil.saveOpen(doc, "DocBuilder\\TestMoveToBookmark.docx", null, false);
        doc.joinRunsWithSameFormatting();

        msAssert.areEqual("Has [BeforeStart][AfterStart]bookmark[BeforeEnd][AfterEnd] inside.\f", doc.getSections().get(0).getBody().getText());

        BookmarkStart bmkStart = (BookmarkStart)doc.getChild(NodeType.BOOKMARK_START, 0, true);
        msAssert.areEqual("[AfterStart]bookmark[BeforeEnd]", bmkStart.getNextSibling().getText());
    }

    @Test
    public void testInsertTOC() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\TestInsertTOC.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.insertTableOfContents("\\o \"1-3\" \\h \\z \\u");
        TestUtil.saveOpen(doc, "DocBuilder\\TestInsertTOC.docx", null, false);
    }

    /// <summary>
    /// WORDSNET-940 Add support for formula fields to DocumentBuilder.InsertField
    /// </summary>
    @Test
    public void testInsertFormula() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\TestInsertFormula.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToCell(0, 3, 1, 0);
        builder.insertField("=SUM(ABOVE)", "");

        TestUtil.save(doc, "DocBuilder\\TestInsertFormula.docx",  null, false);

        Cell cell = (Cell)doc.getChild(NodeType.CELL, 7, true);
        msAssert.areEqual("\u0013=SUM(ABOVE)\u0014\u0015\u0007", cell.getText());
    }

    /// <summary>
    /// Open the document in MS Word and check.
    /// </summary>
    @Test
    public void testInsertDeadField() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertField("TC TestTC", null);

        doc = TestUtil.saveOpen(doc, "DocBuilder\\TestInsertDeadFields.docx", null, false);

        FieldExtractorToCollection extractor = new FieldExtractorToCollection();
        extractor.extract(doc);
        msAssert.areEqual(1, extractor.getFields().size());

        Field field = extractor.getFields().get(0);

        msAssert.areEqual(FieldType.FIELD_TOC_ENTRY, field.getStart().getFieldType());

        msAssert.areEqual(FieldType.FIELD_TOC_ENTRY, field.getEnd().getFieldType());
        msAssert.areEqual(false, field.getEnd().hasSeparator());

        msAssert.areEqual("TC TestTC", field.getFieldCode());
    }

    /// <summary>
    /// Test inserting form text fields.
    /// </summary>
    @Test
    public void testInsertTextInput() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        //This creates soft refresheable collection of form fields.
        msAssert.areEqual(0, doc.getRange().getFormFields().getCount());

        FormField formField = builder.insertTextInput("Test1", TextFormFieldType.REGULAR, "", "test test", 0);
        msAssert.isNotNull(formField);

        builder.insertParagraph();
        builder.insertTextInput("Test2", TextFormFieldType.NUMBER, "0.00", "\u2002\u2002 123", 5);

        //Magic! the collection of form fields got updated.
        FormField f1 = doc.getRange().getFormFields().get("Test1");
        msAssert.isTrue(f1 != null);
        msAssert.areEqual("test test", f1.getResult());

        FormField f2 = doc.getRange().getFormFields().get("Test2");
        msAssert.isTrue(f2 != null);
        msAssert.areEqual("\u2002\u2002 123", f2.getResult());

        //Check bookmark is indeed created.
        msAssert.isTrue(doc.getRange().getBookmarks().get("Test1") != null);

        TestUtil.saveOpen(doc, "DocBuilder\\TestDocBuilderInsertTextInput.docx", null, false);
    }

    /// <summary>
    /// WORDSNET-5287 DocumentBuilder behaved improperly on inserting a multi-line formfield.
    /// Fixed by setting correct DocumentBuilder cursor position at <see cref="DocumentBuilder.InsertTextInput"/>
    /// </summary>
    @Test
    public void test5287() throws Exception
    {
        final String RUN_TEXT = "This text must be outside the formfield.";

        // 1) insert TextInput with a Name specified as a last child.
        DocumentBuilder builder = new DocumentBuilder();
        builder.insertTextInput("test", TextFormFieldType.REGULAR, "", "line1\nline2", 100);
        builder.writeln(RUN_TEXT);

        // This BookmarkEnd points to the end of the FormField nodes.
        BookmarkEnd bookmarkEnd = builder.getDocument().getRange().getBookmarks().get("test").getBookmarkEnd();

        // Next node must be a Run with a text: "This text must be outside the formfield."
        verifyRun(RUN_TEXT, bookmarkEnd.getNextSibling());


        // 2) insert TextInput without a specified Name.
        builder = new DocumentBuilder();
        builder.insertTextInput("", TextFormFieldType.REGULAR, "", "line1\nline2", 100);
        builder.writeln(RUN_TEXT);

        // This FieldEnd points to the end of the FormField nodes.
        FieldEnd fieldEnd = builder.getDocument().getRange().getFormFields().get(0).getField().getEnd();

        // Next node must be a Run with a text: "This text must be outside the formfield."
        verifyRun(RUN_TEXT, fieldEnd.getNextSibling());
    }

    @Test
    public void testInsertCheckBox() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        FormField formField = builder.insertCheckBox("Test1", true, 22);
        msAssert.isNotNull(formField);

        builder.insertParagraph();
        builder.insertCheckBox("Test2", false, 0);

        FormField f1 = doc.getRange().getFormFields().get("Test1");
        msAssert.isTrue(f1 != null);
        msAssert.areEqual("1", f1.getResult());

        FormField f2 = doc.getRange().getFormFields().get("Test2");
        msAssert.isTrue(f2 != null);
        msAssert.areEqual("0", f2.getResult());

        TestUtil.saveOpen(doc, "DocBuilder\\TestDocBuilderInsertCheckBox.docx", null, false);
    }

    /// <summary>
    /// Test inserting a checkbox form field with setting checked and default properties separately.
    /// </summary>
    @Test
    public void testInsertCheckBoxWithCheckedAndDefault() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        FormField formField = builder.insertCheckBox("Test1", true, false, 50);
        msAssert.isNotNull(formField);

        builder.insertParagraph();
        builder.insertCheckBox("Test2", false, true, 50);

        FormField field1 = doc.getRange().getFormFields().get("Test1");
        msAssert.isNotNull(field1);
        msAssert.areEqual(false, field1.getChecked(), "Field 1 checled");
        msAssert.areEqual(true, field1.getDefault(), "Field 1 default value");

        FormField field2 = doc.getRange().getFormFields().get("Test2");
        msAssert.isNotNull(field2);
        msAssert.areEqual(true, field2.getChecked(), "Field 2 checled");
        msAssert.areEqual(false, field2.getDefault(), "Field 2 default value");

        doc = TestUtil.saveOpen(doc, "DocBuilder\\TestInsertCheckBoxWithCheckedAndDefault.docx", null, false);

        field1 = doc.getRange().getFormFields().get("Test1");
        msAssert.isNotNull(field1);
        msAssert.areEqual(false, field1.getChecked(), "Field 1 checled after reopen");
        msAssert.areEqual(true, field1.getDefault(), "Field 1 default value after reopen");

        field2 = doc.getRange().getFormFields().get("Test2");
        msAssert.isNotNull(field2);
        msAssert.areEqual(true, field2.getChecked(), "Field 2 checled after reopen");
        msAssert.areEqual(false, field2.getDefault(), "Field 2 default value after reopen");
    }

    @Test
    public void testInsertComboBox() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        FormField formField = builder.insertComboBox(
            "Test1",
            new String[] {"Line1", "Line2", "Line3"},
            1);
        msAssert.isNotNull(formField);

        FormField f1 = doc.getRange().getFormFields().get("Test1");
        msAssert.isTrue(f1 != null);
        msAssert.areEqual("Line2", f1.getResult());

        TestUtil.saveOpen(doc, "DocBuilder\\TestDocBuilderInsertComboBox.docx", null, false);
    }

    @Test
    public void testDocumentSet() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
        builder.write("Doc1, center.");
        Document doc1 = builder.getDocument();

        //Assign new doc to the builder.
        Document doc2 = new Document();
        builder.setDocument(doc2);
        builder.write("Doc2, left.");

        builder.getParagraphFormat().setStyleName("Normal");

        msAssert.areEqual("Doc1, center.\f", doc1.getText());
        Paragraph para = (Paragraph)doc1.getChild(NodeType.PARAGRAPH, 0, true);
        msAssert.areEqual(ParagraphAlignment.CENTER, para.getParagraphFormat().getAlignment());

        msAssert.areEqual("Doc2, left.\f", doc2.getText());
        para = (Paragraph)doc2.getChild(NodeType.PARAGRAPH, 0, true);
        msAssert.areEqual(ParagraphAlignment.LEFT, para.getParagraphFormat().getAlignment());
    }

    /// <summary>
    /// WORDSNET-3052 Poor quality foot notes.
    /// Added a method to insert a footnote to document builder.
    /// </summary>
    @Test
    public void testInsertFootnote() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.write("Some text is added.");
        builder.insertFootnote(FootnoteType.FOOTNOTE, "Footnote line 1.\rFootnote line 2.");

        doc = TestUtil.saveOpen(doc, "DocBuilder\\TestInsertFootnote.docx", null, false);
        doc.joinRunsWithSameFormatting();

        // Check footnote.
        Footnote ftn = (Footnote)doc.getFirstSection().getBody().getFirstParagraph().getLastChild();
        msAssert.areEqual(FootnoteType.FOOTNOTE, ftn.getFootnoteType());
        msAssert.areEqual(true, ftn.isAuto());
        msAssert.areEqual(StyleIdentifier.FOOTNOTE_REFERENCE, ftn.getFont().getStyleIdentifier());

        Paragraph para = (Paragraph)ftn.getFirstChild();
        msAssert.areEqual("\u0002 Footnote line 1.\r", para.getText());
        msAssert.areEqual(StyleIdentifier.FOOTNOTE_TEXT, para.getParagraphFormat().getStyleIdentifier());

        // Chekc footnote number.
        SpecialChar ftnNum = (SpecialChar)para.getFirstChild();
        msAssert.areEqual("\u0002", ftnNum.getText());
        msAssert.areEqual(StyleIdentifier.FOOTNOTE_REFERENCE, ftnNum.getFont().getStyleIdentifier());

        // Check text of the first paragraph.
        Run run = (Run)ftnNum.getNextSibling();
        msAssert.areEqual(" Footnote line 1.", run.getText());
        msAssert.areEqual(StyleIdentifier.DEFAULT_PARAGRAPH_FONT, run.getFont().getStyleIdentifier());

        // Check text of the second paragraph.
        para = (Paragraph)para.getNextSibling();
        msAssert.areEqual("Footnote line 2.\r", para.getText());
        msAssert.areEqual(StyleIdentifier.FOOTNOTE_TEXT, para.getParagraphFormat().getStyleIdentifier());
    }

    /// <summary>
    /// WORDSNET-11107 Support Custom Mark for FootNote
    ///
    /// InsertFootnote with reference mark must add 3 runs, and first run must contain reference mark.
    /// </summary>
    @Test
    public void testDefect11107InsertFootnote() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        String customMark = "test123456";
        String text = "testfootnote";
        Footnote footnote = builder.insertFootnote(FootnoteType.FOOTNOTE, text, customMark);

        msAssert.isNotNull(footnote);
        msAssert.isFalse(footnote.isAuto());
        msAssert.areEqual(customMark, footnote.getReferenceMark());
        msAssert.areEqual(1, footnote.getParagraphs().getCount());
        msAssert.areEqual(3, footnote.getFirstParagraph().getRuns().getCount());

        msAssert.areEqual(customMark, footnote.getFirstParagraph().getRuns().get(0).getText());
        msAssert.areEqual(" ", footnote.getFirstParagraph().getRuns().get(1).getText());
        msAssert.areEqual(text, footnote.getFirstParagraph().getRuns().get(2).getText());
    }


    /// <summary>
    /// Tests the <see cref="DocumentBuilder.MoveTo(Node)"/> method on moving to block level bookmarks.
    /// </summary>
    @Test
    public void testMoveToBlockLevelBookmark() throws Exception
    {
        Document doc = new Document();
        Body body = doc.getFirstSection().getBody();
        body.removeAllChildren();

        // Generate document of structure:
        // bookmarkStart1
        // paragraph1
        // bookmarkEnd1
        // sdt1
        //   paragraph2
        //     run1
        //   bookmarkStart2
        // table1
        //   bookmarkEnd2
        //   row1
        //     sdt2
        //       cell1
        //         paragraph3
        //         bookmarkStart3
        //   row2
        //     cell2
        //       paragraph4
        //         run2
        //       bookmarkEnd3
        // paragraph5

        BookmarkStart bookmarkStart1 = new BookmarkStart(doc, "BM1");
        body.appendChild(bookmarkStart1);
        Paragraph paragraph1 = new Paragraph(doc);
        body.appendChild(paragraph1);
        BookmarkEnd bookmarkEnd1 = new BookmarkEnd(doc, "BM1");
        body.appendChild(bookmarkEnd1);

        StructuredDocumentTag sdt1 = new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.BLOCK);
        sdt1.removeAllChildren();
        body.appendChild(sdt1);
        Paragraph paragraph2 = new Paragraph(doc);
        sdt1.appendChild(paragraph2);
        Run run1 = new Run(doc);
        paragraph2.appendChild(run1);
        BookmarkStart bookmarkStart2 = new BookmarkStart(doc, "BM2");
        sdt1.appendChild(bookmarkStart2);

        Table table1 = new Table(doc);
        body.appendChild(table1);
        BookmarkEnd bookmarkEnd2 = new BookmarkEnd(doc, "BM2");
        table1.appendChild(bookmarkEnd2);

        Row row1 = new Row(doc);
        table1.appendChild(row1);
        StructuredDocumentTag sdt2 = new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.CELL);
        sdt2.removeAllChildren();
        row1.appendChild(sdt2);
        Cell cell1 = new Cell(doc);
        sdt2.appendChild(cell1);
        Paragraph paragraph3 = new Paragraph(doc);
        cell1.appendChild(paragraph3);
        BookmarkStart bookmarkStart3 = new BookmarkStart(doc, "BM3");
        cell1.appendChild(bookmarkStart3);

        Row row2 = new Row(doc);
        table1.appendChild(row2);
        Cell cell2 = new Cell(doc);
        row2.appendChild(cell2);
        Paragraph paragraph4 = new Paragraph(doc);
        cell2.appendChild(paragraph4);
        Run run2 = new Run(doc);
        paragraph4.appendChild(run2);
        BookmarkEnd bookmarkEnd3 = new BookmarkEnd(doc, "BM3");
        cell2.appendChild(bookmarkEnd3);

        Paragraph paragraph5 = new Paragraph(doc);
        body.appendChild(paragraph5);

        // Test moving to bookmarks.
        DocumentBuilder builder = new DocumentBuilder(doc);
        checkMove(builder, bookmarkStart1, paragraph1, null);
        checkMove(builder, bookmarkStart2, paragraph3, null);
        checkMove(builder, bookmarkStart3, paragraph4, run2);
        checkMove(builder, bookmarkEnd1, paragraph2, run1);
        checkMove(builder, bookmarkEnd2, paragraph3, null);
        checkMove(builder, bookmarkEnd3, paragraph5, null);
    }

    /// <summary>
    /// Checks that the <see cref="DocumentBuilder.MoveTo(Node)"/> method moves cursor to expected node.
    /// </summary>
    private static void checkMove(DocumentBuilder builder, Node moveTo,
        Paragraph expectedCurrentParagraph, Node expectedCurrentNode)
    {
        builder.moveTo(moveTo);
        msAssert.areSame(expectedCurrentParagraph, builder.getCurrentParagraph());
        msAssert.areSame(expectedCurrentNode, builder.getCurrentNode());
    }


    /// <summary>
    /// WORDSNET-8216 DocumentBuilder does not inherit Bookmark formatting when moving cursor to that Bookmark.
    /// Try to get formatting from previous inline. Seems Word do the same.
    /// </summary>
    @Test
    public void testJira8216() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\TestJira8216.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToBookmark("bold");
        builder.write("This is a very cool bookmark.");

        doc = TestUtil.saveOpen(doc, "DocBuilder\\TestJira8216.docx", null, false);
        Run run = doc.getFirstSection().getBody().getFirstParagraph().getRuns().get(2);
        msAssert.areEqual("This is a very cool bookmark.", run.getText());
        msAssert.isTrue(run.getFont().getBold());
    }

    /// <summary>
    /// WORDSNET-9823 Setting Font.Color in DocumentBuilder does not change the color of the first paragraph's
    /// list label.
    /// andrnosk: It seems the problem occurs because upon SetRunAttr through the DocumentBuilder,
    /// if CurrentParagraph is empty, we have to set ParagraphBreakRunPr attrs too.
    /// </summary>
    @Test
    public void testJira9823() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.getFont().setBold(true);
        builder.getFont().setColor(Color.RED);

        builder.getListFormat().applyNumberDefault();
        builder.writeln("Item 1");
        builder.writeln("Item 2");
        builder.write("Item 3");

        ParagraphCollection papas = builder.getDocument().getFirstSection().getBody().getParagraphs();
        msAssert.isTrue(papas.get(0).getListLabel().getFont().getBold());
        msAssert.areEqual(papas.get(0).getListLabel().getFont().getColor().getRGB(), Color.RED.getRGB());
        msAssert.isTrue(papas.get(1).getListLabel().getFont().getBold());
        msAssert.isTrue(papas.get(2).getListLabel().getFont().getBold());
    }

    /// <summary>
    /// WORDSNET-10765 DocumentBuilder.Font formatting is incorrectly applied using 14.8.0.
    /// When we set run attributes, we also set them for ParagraphBreakRunPr of CurrentParagraph (see WORDSNET-9823).
    /// Therefore, clearing these attributes should clear them for ParagraphBreakRunPr too.
    /// </summary>
    @Test
    public void testJira10765() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        builder.writeln();
        builder.getFont().setColor(msColor.getGreen());
        builder.write("Green text");

        builder.getFont().clearFormatting();
        builder.write(" non green");

        builder.writeln();
        builder.writeln();

        builder.getListFormat().applyBulletDefault();
        builder.getFont().setColor(Color.BLUE);
        builder.getFont().clearFormatting();
        builder.write("list item 1");

        builder.getFont().clearFormatting();
        builder.writeln();

        builder.getFont().setColor(Color.BLUE);
        builder.write("list item 2");

        builder.getFont().clearFormatting();
        builder.writeln();

        builder.getFont().setColor(Color.BLUE);
        builder.write("list item 3");
        builder.getFont().clearFormatting();

        ParagraphCollection paras = builder.getDocument().getFirstSection().getBody().getParagraphs();

        checkParaBreakRunPrColor(paras.get(1));

        checkParaBreakRunPrColor(paras.get(3));
        checkParaListLabelColor(paras.get(3));

        checkParaBreakRunPrColor(paras.get(4));
        checkParaListLabelColor(paras.get(4));

        checkParaBreakRunPrColor(paras.get(5));
        checkParaListLabelColor(paras.get(5));
    }

    /// <summary>
    /// WORDSNET-8087 DocumentBuilder.Font.ClearFormatting do not reset to default font formatting.
    /// Should change ParagraphBreakRunPr in SetRunAttr() and ClearRunAttrs() for empty paragraphs too.
    /// </summary>
    @Test
    public void testJira8087() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());

        // We need a bookmark by a customer scenario.
        builder.getDocument().getFirstSection().getBody().getFirstParagraph().appendChild(new BookmarkStart(builder.getDocument()));
        builder.moveToDocumentStart();

        builder.writeln("Sample Document");
        Paragraph para = (Paragraph)builder.getCurrentParagraph().getPreviousSibling();
        msAssert.areEqual(12, 0.1, para.getFirstRun().getFont().getSize());
        msAssert.areEqual(12, 0.1, builder.getCurrentParagraph().getParagraphBreakFont().getSize());

        builder.getFont().setSize(48.0);
        builder.writeln("Test Large Text");
        para = (Paragraph)builder.getCurrentParagraph().getPreviousSibling();
        msAssert.areEqual(48, 0.1, para.getFirstRun().getFont().getSize());
        msAssert.areEqual(48, 0.1, builder.getCurrentParagraph().getParagraphBreakFont().getSize());

        builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);

        builder.getFont().clearFormatting();

        // Table 1
        builder.startTable();
        builder.insertCell();
        builder.write("Tbl 1");
        para = builder.getCurrentParagraph();
        msAssert.areEqual(12, 0.1, para.getFirstRun().getFont().getSize());
        msAssert.areEqual(12, 0.1, para.getParagraphBreakFont().getSize());
        builder.endTable();

        builder.insertParagraph();
        msAssert.areEqual(12, 0.1, builder.getCurrentParagraph().getParagraphBreakFont().getSize());

        // Table 2
        builder.startTable();
        builder.insertCell();
        builder.write("Tbl 2");
        para = builder.getCurrentParagraph();
        msAssert.areEqual(12, 0.1, para.getFirstRun().getFont().getSize());
        msAssert.areEqual(12, 0.1, para.getParagraphBreakFont().getSize());
    }


    /// <summary>
    /// WORDSNET-6124 Allow DocumentBuilder to move correctly to StructuredDocumentTags
    /// check that DocumentBuilder can move to sdt block level and tables
    /// </summary>
    @Test
    public void testJira6124() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\TestJira6124.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        // inlineLevelTag
        StructuredDocumentTag inlineSdtNode = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        builder.moveTo(inlineSdtNode);
        msAssert.areEqual(builder.getCurrentNode(), inlineSdtNode);

        // blockLevelTag
        StructuredDocumentTag blockSdtNode = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 1, true);

        Table tableNode = (Table)doc.getChild(NodeType.TABLE, 0, true);

        for (CompositeNode node : new CompositeNode[] { blockSdtNode, tableNode })
        {
            builder.moveTo(node);
            msAssert.areEqual(node.getChild(NodeType.PARAGRAPH, 0, true), builder.getCurrentParagraph());
        }
    }

    /// <summary>
    /// WORDSNET-17397 Add feature to insert Horizontal Rule into document.
    /// </summary>
    @Test
    public void testJira17397() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.insertHorizontalRule();

        //Tests that the shape was saved successfully.
        doc = TestUtil.saveOpen(doc, "DocBuilder\\TestJira17397.docx", new OoxmlSaveOptions(), false);
        Shape shapeHorizontalRule = (Shape)doc.getChildNodes(NodeType.SHAPE, true).get(0);

        msAssert.areEqual(ShapeMarkupLanguage.VML, shapeHorizontalRule.getMarkupLanguage());
        msAssert.areEqual(1.5, shapeHorizontalRule.getHeight());
        msAssert.areEqual(doc.getFirstSection().getPageSetup().getContentWidth(), shapeHorizontalRule.getWidth());
        msAssert.areEqual(ShapeType.RECTANGLE, shapeHorizontalRule.getShapeType());
        msAssert.areEqual(shapeHorizontalRule.getWrapType(), WrapType.INLINE);
    }

    /// <summary>
    /// Tests that check box can be inserted into inline SDT.
    /// </summary>
    @Test
    public void testJira17319Builder() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\TestJira17319Builder.docx");

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        sdt.isShowingPlaceholderText(false);

        DocumentBuilder builder = new DocumentBuilder(doc);
        Run run = (Run)doc.getChild(NodeType.RUN, 0, true);

        builder.moveTo(run);
        builder.insertCheckBox("test", true, 0);
    }

    /// <summary>
    /// WORDSNET-18182 Add feature to set properties of horizontal rule.
    /// </summary>
    @Test (dataProvider = "test18182DataProvider")
    public void test18182(/*HorizontalRuleAlignment*/int expectedAlignment, double expectedWidthPercent, double expectedHeight,
        int argbColor, boolean expectedNoShade, boolean isDefault) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        if (!isDefault)
        {
            HorizontalRuleFormat horizontalRuleFormat = builder.insertHorizontalRule().getHorizontalRuleFormat();
            horizontalRuleFormat.setAlignment(expectedAlignment);
            horizontalRuleFormat.setWidthPercent(expectedWidthPercent);
            horizontalRuleFormat.setHeight(expectedHeight);
            horizontalRuleFormat.setColor(new Color((argbColor), true));
            horizontalRuleFormat.setNoShade(expectedNoShade);
        }
        else
            builder.insertHorizontalRule();

        OoxmlSaveOptions so = new OoxmlSaveOptions();
        Document doc = TestUtil.saveOpen(builder.getDocument(), "DocBuilder\\Test18182.docx", so, false);

        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0,true);
        HorizontalRuleFormat savedHorizontalRuleFormat = shape.getHorizontalRuleFormat();

        msAssert.areEqual(expectedAlignment, savedHorizontalRuleFormat.getAlignment());
        msAssert.areEqual(expectedWidthPercent, savedHorizontalRuleFormat.getWidthPercent());
        msAssert.areEqual(expectedHeight, savedHorizontalRuleFormat.getHeight());
        msAssert.areEqual(argbColor, savedHorizontalRuleFormat.getColor().getRGB());
        msAssert.areEqual(expectedNoShade, savedHorizontalRuleFormat.getNoShade());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test18182DataProvider")
	public static Object[][] test18182DataProvider() throws Exception
	{
		return new Object[][]
		{
			{HorizontalRuleAlignment.RIGHT,  50,  10,  -16776961,  true,  false},
			{HorizontalRuleAlignment.LEFT,  100,  1.5,  -8355712,  false,  true},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-18182. Checks the behavior of a validation for boundary values.
    /// </summary>
    @Test (dataProvider = "test18182CheckBoundaryValuesDataProvider")
    public void test18182CheckBoundaryValues(double widthPercent,  double height) throws Exception
    {
        SystemPal.saveCulture();
        // JAVA-deleted SaveUICulture(): No analog in Java.
        try
        {
            SystemPal.setStandardCulture();
            // JAVA-deleted SetStandardUICulture(): No analog in Java.

            DocumentBuilder builder = new DocumentBuilder();
            HorizontalRuleFormat horizontalRuleFormat = builder.insertHorizontalRule().getHorizontalRuleFormat();

            int countThrows = 0;
            try
            {
                horizontalRuleFormat.setWidthPercent(widthPercent);
            }
            catch (IllegalArgumentException e)
            {
                msAssert.isTrue(msString.trimEnd(e.getMessage(), '\'', ')').endsWith("WidthPercent"));
                countThrows++;
            }

            try
            {
                horizontalRuleFormat.setHeight(height);
            }
            catch (IllegalArgumentException e)
            {
                msAssert.isTrue(msString.trimEnd(e.getMessage(), '\'', ')').endsWith("Height"));
                countThrows++;
            }

            msAssert.areEqual(2, countThrows);
        }
        finally
        {
            SystemPal.restoreCulture();
            // JAVA-deleted RestoreUICulture(): No analog in Java.
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test18182CheckBoundaryValuesDataProvider")
	public static Object[][] test18182CheckBoundaryValuesDataProvider() throws Exception
	{
		return new Object[][]
		{
			{0,  -1},
			{101,  1585},
		};
	}

    /// <summary>
    /// Case for WORDSNET-13983
    /// Writing emphasis marks for simple runs.
    /// </summary>
    @Test
    public void test13983_EmphasisRun() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getFont().setEmphasisMark(EmphasisMark.UNDER_SOLID_CIRCLE);

        builder.write("Emphasis text");
        builder.writeln();
        builder.getFont().clearFormatting();
        builder.write("Simple text");

        ParagraphCollection paras = builder.getDocument().getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(EmphasisMark.UNDER_SOLID_CIRCLE, paras.get(0).getFirstRun().getFont().getEmphasisMark());
        msAssert.areEqual(EmphasisMark.NONE, paras.get(1).getFirstRun().getFont().getEmphasisMark());
    }

    /// <summary>
    /// Case for WORDSNET-13983
    /// Writing emphasis marks for a numbered list.
    /// </summary>
    @Test
    public void test13983_EmphasisList() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getFont().setEmphasisMark(EmphasisMark.UNDER_SOLID_CIRCLE);

        builder.getListFormat().applyNumberDefault();
        builder.writeln("Item 1");
        builder.writeln("Item 2");
        builder.write("Item 3");

        ParagraphCollection paras = builder.getDocument().getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(EmphasisMark.UNDER_SOLID_CIRCLE, paras.get(0).getListLabel().getFont().getEmphasisMark());
        msAssert.areEqual(EmphasisMark.UNDER_SOLID_CIRCLE, paras.get(1).getListLabel().getFont().getEmphasisMark());
        msAssert.areEqual(EmphasisMark.UNDER_SOLID_CIRCLE, paras.get(2).getListLabel().getFont().getEmphasisMark());
    }

    /// <summary>
    /// WORDSNET-10148 Tests cursor moving to a specific character index within a paragraph.
    /// </summary>
    @Test
    public void testMoveToParagraphCharIndex() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("Paragraph 1");
        builder.write("Run 1 ");
        builder.write("Run 2 ");
        builder.writeln("Run 3");

        Paragraph paragraph1 = doc.getFirstSection().getBody().getParagraphs().get(0);
        Paragraph paragraph2 = doc.getFirstSection().getBody().getParagraphs().get(1);

        msAssert.areEqual(1, paragraph1.getChildNodes(NodeType.ANY, false).getCount());

        builder.moveToParagraph(0, 9);

        // Runs are split only after inserting contents.
        msAssert.areSame(paragraph1.getFirstRun(), builder.getCurrentNode());
        msAssert.areEqual(1, paragraph1.getChildNodes(NodeType.ANY, false).getCount());

        final String INSERTED_TEXT = " (Inserted)";
        builder.write(INSERTED_TEXT);
        msAssert.areEqual(3, paragraph1.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("Paragraph", paragraph1.getRuns().get(0).getText());
        msAssert.areEqual(INSERTED_TEXT, paragraph1.getRuns().get(1).getText());
        msAssert.areEqual(" 1", paragraph1.getRuns().get(2).getText());
        msAssert.areSame(paragraph1.getRuns().get(2), builder.getCurrentNode());

        builder.moveToParagraph(0, -(2 + INSERTED_TEXT.length()) - 1);
        msAssert.areSame(paragraph1.getRuns().get(1), builder.getCurrentNode());

        builder.write(INSERTED_TEXT);

        msAssert.areEqual("Paragraph" + INSERTED_TEXT + INSERTED_TEXT + " 1\r", paragraph1.getText());

        builder.moveToParagraph(1, 6);
        msAssert.areSame(paragraph2.getRuns().get(1), builder.getCurrentNode());
        msAssert.areEqual(3, paragraph2.getChildNodes(NodeType.ANY, false).getCount());

        builder.write(">");
        builder.write("> ");
        msAssert.areEqual(5, paragraph2.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("Run 1 ", paragraph2.getRuns().get(0).getText());
        msAssert.areEqual(">", paragraph2.getRuns().get(1).getText());
        msAssert.areEqual("> ", paragraph2.getRuns().get(2).getText());
        msAssert.areEqual("Run 2 ", paragraph2.getRuns().get(3).getText());
        msAssert.areEqual("Run 3", paragraph2.getRuns().get(4).getText());
        msAssert.areSame(paragraph2.getRuns().get(3), builder.getCurrentNode());

        builder.moveToParagraph(1, -2);
        builder.write(">>");

        msAssert.areEqual("Run 1 >> Run 2 Run >>3\r", paragraph2.getText());
    }

    /// <summary>
    /// WORDSNET-10148 Tests cursor moving to the special character indexes (0, -1) within a paragraph.
    /// </summary>
    @Test
    public void testMoveToParagraphSpecialCharIndex() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.startBookmark("Bookmark1");
        builder.write("Run 1 ");
        builder.write("Run 2 ");
        builder.endBookmark("Bookmark1");
        builder.writeln("");

        Paragraph paragraph = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual(4, paragraph.getChildNodes(NodeType.ANY, false).getCount());

        builder.moveToParagraph(0, -1);

        msAssert.isNull(builder.getCurrentNode());
        msAssert.areSame(paragraph, builder.getCurrentParagraph());

        builder.moveToParagraph(0, 0);

        msAssert.areEqual(NodeType.BOOKMARK_START, builder.getCurrentNode().getNodeType());
        msAssert.areSame(paragraph, builder.getCurrentParagraph());
    }

    /// <summary>
    /// WORDSNET-10148 Tests cursor moving to a specific character index within a table cell.
    /// </summary>
    @Test
    public void testMoveToCellCharIndex() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.startTable();

        Cell cell1 = builder.insertCell();
        builder.writeln("Cell 1");
        builder.write("Paragraph 2");

        Cell cell2 = builder.insertCell();
        builder.writeln("Cell 2");
        builder.write("Paragraph 2");

        builder.endRow();
        builder.endTable();

        Debug.assertTrue(cell1.getParagraphs().getCount() == 2);
        Debug.assertTrue(cell2.getParagraphs().getCount() == 2);

        builder.moveToCell(0, 0, 1, -1);
        msAssert.isNull(builder.getCurrentNode());
        msAssert.areSame(cell2.getLastParagraph(), builder.getCurrentParagraph());

        builder.write(" Test");
        msAssert.areEqual("Paragraph 2 Test\u0007", cell2.getLastParagraph().getText());

        builder.moveToCell(0, 0, 1, 0);
        msAssert.areSame(cell2.getFirstParagraph().getFirstRun(), builder.getCurrentNode());
        msAssert.areSame(cell2.getFirstParagraph(), builder.getCurrentParagraph());

        builder.write("Test ");
        msAssert.areEqual("Test Cell 2\r", cell2.getFirstParagraph().getText());

        builder.moveToCell(0, 0, 0, -14);
        msAssert.areSame(cell1.getFirstParagraph().getFirstRun(), builder.getCurrentNode());
        msAssert.areSame(cell1.getFirstParagraph(), builder.getCurrentParagraph());

        builder.write("#");

        builder.moveToCell(0, 0, 0, 18);
        msAssert.areSame(cell1.getLastParagraph().getFirstRun(), builder.getCurrentNode());
        msAssert.areSame(cell1.getLastParagraph(), builder.getCurrentParagraph());

        builder.write("#");
        msAssert.areEqual("Cell #1\rParagraph #2\u0007", cell1.getText());
    }


    /// <summary>
    /// Relates to WORDSNET-27240.
    /// Tests InsertGroupShape() with an empty list of shapes.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThe shapes parameter shall contain at least one shape.\\E")
    public void test27240_Empty() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());
        builder.insertGroupShape();
    }

    /// <summary>
    /// Relates to WORDSNET-27240.
    /// Tests InsertGroupShape() with dimensions with an empty list of shapes.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThe shapes parameter shall contain at least one shape.\\E")
    public void test27240_EmptyWithSize() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());
        builder.insertGroupShape(100.0, 100.0, 100.0, 100.0);
    }



    /// <summary>
    /// WORDSNET-27467 Grouping group shapes resets inner group shapes dimensions.
    /// Tests transformation data update when grouping shapes.
    /// </summary>
    @Test
    public void test27467_Level0() throws Exception
    {
        double[][] expTransforms = {
            // Group 100017
            new double[] { 0.0, 0.0, 723900.0, 1206500.0, 0.0, 0.0, 723900.0, 1206500.0 },
            new double[] { 0.0, 0.0, 590550.0, 1143000.0 },
            new double[] { 127000.0, 63500.0, 596900.0, 1143000.0 },
            // Group 100018
            new double[] { 0.0, 0.0, 723900.0, 1206500.0, 0.0, 0.0, 723900.0, 1206500.0 },
            new double[] { 0.0, 0.0, 590550.0, 1143000.0 },
            new double[] { 127000.0, 63500.0, 596900.0, 1143000.0 },
            // Group 100019
            new double[] { 0.0, 0.0, 723900.0, 1206500.0, 0.0, 0.0, 723900.0, 1206500.0 },
            new double[] { 0.0, 0.0, 590550.0, 1143000.0 },
            new double[] { 127000.0, 63500.0, 596900.0, 1143000.0 },
            // Group 100020
            new double[] { 0.0, 0.0, 723900.0, 1206500.0, 0.0, 0.0, 723900.0, 1206500.0 },
            new double[] { 0.0, 0.0, 590550.0, 1143000.0 },
            new double[] { 127000.0, 63500.0, 596900.0, 1143000.0 },
        };

        Document doc = TestUtil.open("DocBuilder\\Test27467_Lvl0.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        ArrayList<Shape> shapes = doc.getFirstSection().getBody().getChildNodes(NodeType.SHAPE, true).<Shape>toList();
        for (int i = 0; i < 4; i++)
        {
            builder.moveTo(doc.getFirstSection().getBody().getFirstParagraph());
            Shape shape1 = shapes.get(i * 2);
            Shape shape2 = shapes.get(i * 2 + 1);
            GroupShape group = builder.insertGroupShape(shape1, shape2);

            // Checks transformations after GroupShape insert.
            checkTransform(group.getDmlNode().getTransform(), expTransforms[i * 3]);
            checkTransform(shape1.getDmlNode().getTransform(), expTransforms[i * 3 + 1]);
            checkTransform(shape2.getDmlNode().getTransform(), expTransforms[i * 3 + 2]);
        }
    }

    /// <summary>
    /// WORDSNET-27467 Grouping group shapes resets inner group shapes dimensions.
    /// Tests transformation data update when grouping group shapes.
    /// </summary>
    @Test
    public void test27467_Level1() throws Exception
    {
        double[][] expTransforms = {
            // Group 100025
            new double[] { 0.0, 0.0, 1616075.0, 1209675.0, 0.0, 0.0, 1616075.0, 1209675.0 },
            new double[] { 895350.0, 0.0, 720725.0, 1209675.0, 0.0, 0.0, 720725.0, 1209675.0 },
            new double[] { 0.0, 0.0, 730250.0, 1209675.0, 0.0, 0.0, 730250.0, 1209675.0 },
            // Group 100026
            new double[] { 0.0, 0.0, 1606550.0, 1209675.0, 0.0, 0.0, 1606550.0, 1209675.0 },
            new double[] { 885825.0, 0.0, 720725.0, 1209675.0, 0.0, 0.0, 720725.0, 1209675.0 },
            new double[] { 0.0, 0.0, 720725.0, 1209675.0, 0.0, 0.0, 720725.0, 1209675.0 },
        };

        Document doc = TestUtil.open("DocBuilder\\Test27467_Lvl1.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        ArrayList<GroupShape> grShapes = doc.getFirstSection().getBody().getChildNodes(NodeType.GROUP_SHAPE, true).<GroupShape>toList();
        for (int i = 0; i < 2; i++)
        {
            builder.moveTo(doc.getFirstSection().getBody().getFirstParagraph());
            GroupShape grShape1 = grShapes.get(i * 2);
            GroupShape grShape2 = grShapes.get(i * 2 + 1);
            GroupShape newGroup = builder.insertGroupShape(grShape1, grShape2);

            // Checks transformations after GroupShape insert.
            checkTransform(newGroup.getDmlNode().getTransform(), expTransforms[i * 3]);
            checkTransform(grShape1.getDmlNode().getTransform(), expTransforms[i * 3 + 1]);
            checkTransform(grShape2.getDmlNode().getTransform(), expTransforms[i * 3 + 2]);
        }
    }

    /// <summary>
    /// WORDSNET-27467 Grouping group shapes resets inner group shapes dimensions.
    /// Tests transformation data update when grouping nested group shapes.
    /// </summary>
    @Test
    public void test27467_Level2() throws Exception
    {
        double[][] expTransforms = {
            new double[] { 0.0, 0.0, 3387725.0, 1209675.0, 0.0, 0.0, 3387725.0, 1209675.0 },
            new double[] { 1771650.0, 0.0, 1616075.0, 1209675.0, 0.0, 0.0, 1616075.0, 1209675.0 },
            new double[] { 0.0, 0.0, 1606550.0, 1209675.0, 0.0, 0.0, 1606550.0, 1209675.0 },
        };

        Document doc = TestUtil.open("DocBuilder\\Test27467_Lvl2.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveTo(doc.getFirstSection().getBody().getFirstParagraph());
        GroupShape grShape1 = (GroupShape)doc.getFirstSection().getBody().getChild(NodeType.GROUP_SHAPE, 0, true);
        GroupShape grShape2 = (GroupShape)doc.getFirstSection().getBody().getChild(NodeType.GROUP_SHAPE, 3, true);
        GroupShape newGroup = builder.insertGroupShape(grShape1, grShape2);

        // Checks transformations after GroupShape insert.
        checkTransform(newGroup.getDmlNode().getTransform(), expTransforms[0]);
        checkTransform(grShape1.getDmlNode().getTransform(), expTransforms[1]);
        checkTransform(grShape2.getDmlNode().getTransform(), expTransforms[2]);
    }

    /// <summary>
    /// Checks DML transformation data.
    /// </summary>
    private static void checkTransform(DmlTransform transform, /*params*/ double... expTrans)
    {
        msAssert.areEqual(expTrans[0], 0.1, transform.getX());
        msAssert.areEqual(expTrans[1], 0.1, transform.getY());
        msAssert.areEqual(expTrans[2], 0.1, transform.getWidth());
        msAssert.areEqual(expTrans[3], 0.1, transform.getHeight());

        DmlGroupTransform grTransform = ms.as(transform, DmlGroupTransform.class);
        if (grTransform == null)
            return;

        msAssert.areEqual(expTrans[4], 0.1, grTransform.getChildX());
        msAssert.areEqual(expTrans[5], 0.1, grTransform.getChildY());
        msAssert.areEqual(expTrans[6], 0.1, grTransform.getChildWidth());
        msAssert.areEqual(expTrans[7], 0.1, grTransform.getChildHeight());
    }


    /// <summary>
    /// Checks color of paragraph break.
    /// </summary>
    private static void checkParaBreakRunPrColor(Paragraph para)
    {
        msAssert.areEqual(DrColor.EMPTY, para.getParagraphBreakRunPr().getColor());
    }

    /// <summary>
    /// Checks color of paragraph list label.
    /// </summary>
    private static void checkParaListLabelColor(Paragraph para)
    {
        msAssert.areEqual(msColor.Empty, para.getListLabel().getFont().getColor());
    }

    /// <summary>
    /// Verifies <paramref name="node"/> type and text.
    /// </summary>
    private static void verifyRun(String runText, Node node)
    {
        msAssert.isNotNull(node);
        msAssert.areEqual(NodeType.RUN, node.getNodeType());
        msAssert.areEqual(runText, node.getText());
    }


}

