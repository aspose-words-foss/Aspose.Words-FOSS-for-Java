// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/08/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


@Test
public class TestDocumentValidator
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Empty tables should be removed on validation stage.
    /// </summary>
    @Test
    public void testValidateEmptyTable() throws Exception
    {
        Document doc = new Document();
        Body body = doc.getFirstSection().getBody();
        body.removeAllChildren();
        Table emptyTable = new Table(doc);
        body.appendChild(emptyTable);

        msAssert.areEqual(emptyTable, body.getTables().get(0));
        DocumentValidator validator = new DocumentValidator();
        validator.execute(new SaveInfo(doc, null, null, new HtmlSaveOptions()));
        msAssert.areEqual(0, body.getTables().getCount());
    }


    /// <summary>
    /// WORDSNET-11133 (fixed) The text formatting of DrawingML is lost after converting from Docx to Pdf/HTML.
    /// andrnosk: The problem occurred because Dml was replaced with Vml fallback.
    /// Now after validation we still have Dml (with textboxes) in the model,
    /// and then during converting to Html ShapeRenderer converts these Dml to simple images.
    /// </summary>
    @Test
    public void testJira11133() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestJira11133.docx");
        DocumentValidator validator = new DocumentValidator();
        validator.execute(new SaveInfo(doc, null, null, new HtmlSaveOptions()));

        GroupShape groupShape = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true);

        msAssert.areEqual(ShapeMarkupLanguage.DML, groupShape.getMarkupLanguage());
    }

    /// <summary>
    /// WORDSNET-11458 Document.Save generates huge size html for Odt
    /// TabStop value in document is very big, more than page size.
    /// Word and OO.Writer don't display this value and use default tabstop.
    /// On validation we remove tabstop if value of it more than page size.
    /// </summary>
    // FOSS: TestJira11458 removed — it loads .odt (removed) and validates tab stops against an HTML save
    // target (removed).




    /// <summary>
    /// WORDSNET-13768 Aspose.Words allows any string in the CustomXmlPart.Id property like old versions of
    /// MS Word, but Microsoft Word Online fails to open a document created with a non-GUID value.
    /// Now on saving to ISO/IEC 29500 formats we replace text values with GUIDs during document validation.
    /// </summary>
    @Test
    public void testJira13768() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira13768.docx");
        TestUtil.executeValidator(doc, OoxmlSaveOptions.docxWithCompliance(OoxmlComplianceCore.ISO_TRANSITIONAL));

        String id = doc.getCustomXmlParts().get(0).getId();
        boolean isGuid = id.startsWith("{") && id.endsWith("}") && (id.length() == 38);
        msAssert.isTrue(isGuid);

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.areEqual(id, sdt.getXmlMapping().getStoreItemId());

        // Check that correct GUID value is not changed.
        doc = TestUtil.open("Model\\Markup\\TestDefect10445.docx");
        TestUtil.executeValidator(doc, OoxmlSaveOptions.docxWithCompliance(OoxmlComplianceCore.ISO_TRANSITIONAL));

        msAssert.areEqual("{31A4723B-B9FA-494A-A75D-8D07FD6A44F7}", doc.getCustomXmlParts().get(0).getId());
    }


    /// <summary>
    /// Related to WORDSNET-16910.
    /// Checks cases related with formatting and attribute inheritance.
    /// </summary>
    @Test
    public void testJira16910Simplified() throws Exception
    {
        Document doc = TestUtil.open("Model\\Run\\Font\\TestJira16910S.docx");
        SaveOptions so = SaveOptions.createSaveOptions(SaveFormat.DOCX);
        so.setUpdateAmbiguousTextFont(true);
        TestUtil.executeValidator(doc, so);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        // 1. Character style is applied to the run, which has not direct formatting.
        // Check that "BoldBi" and "SizeCs" attributes will be set directly for the run.
        Run run = paras.get(0).getFirstRun();
        msAssert.isFalse(run.getRunPr().contains(FontAttr.BOLD));
        msAssert.isFalse(run.getRunPr().contains(FontAttr.SIZE));
        msAssert.isFalse(run.getRunPr().contains(FontAttr.ITALIC));
        msAssert.isFalse(run.getRunPr().contains(FontAttr.ITALIC_BI));
        msAssert.areEqual(16, (/*int*/Integer)run.getRunPr().get(FontAttr.SIZE_BI));
        msAssert.areEqual(AttrBoolEx.TRUE, (AttrBoolEx)run.getRunPr().get(FontAttr.BOLD_BI));

        // 2. Run formatting contains direct "Size" and "SizeCs" attributes.
        // Check that "SizeCs" will be updated for the run.
        run = paras.get(1).getFirstRun();
        msAssert.areEqual(14, (/*int*/Integer)run.getRunPr().get(FontAttr.SIZE));
        msAssert.areEqual(14, (/*int*/Integer)run.getRunPr().get(FontAttr.SIZE_BI));
        msAssert.isFalse(run.getRunPr().contains(FontAttr.ITALIC_BI));
        msAssert.isFalse(run.getRunPr().contains(FontAttr.BOLD_BI));

        // 3. "ComplexScript" attribute is set for the run.
        // Check that at this case formatting and size are not updated.
        run = paras.get(2).getFirstRun();
        msAssert.areEqual(14, (/*int*/Integer)run.getRunPr().get(FontAttr.SIZE));
        msAssert.areEqual(20, (/*int*/Integer)run.getRunPr().get(FontAttr.SIZE_BI));
        msAssert.isFalse(run.getRunPr().contains(FontAttr.ITALIC_BI));
        msAssert.isFalse(run.getRunPr().contains(FontAttr.BOLD_BI));

        // 4. Check that "Italic" attribute updates. Also font size does not update, when
        // "Size" and "SizeBi" have the same values.
        run = paras.get(3).getFirstRun();
        msAssert.isFalse(run.getRunPr().contains(FontAttr.BOLD));
        msAssert.isFalse(run.getRunPr().contains(FontAttr.ITALIC));
        msAssert.isFalse(run.getRunPr().contains(FontAttr.SIZE));
        msAssert.isFalse(run.getRunPr().contains(FontAttr.SIZE_BI));
        msAssert.areEqual(AttrBoolEx.TRUE, (AttrBoolEx)run.getRunPr().get(FontAttr.ITALIC_BI));
        msAssert.areEqual(AttrBoolEx.FALSE, (AttrBoolEx)run.getRunPr().get(FontAttr.BOLD_BI));

        // 5. Font of the run is out of the font list, which requires the updating of size and formatting.
        run = paras.get(4).getFirstRun();
        msAssert.isFalse(run.getRunPr().contains(FontAttr.BOLD));
        msAssert.isFalse(run.getRunPr().contains(FontAttr.BOLD_BI));
        msAssert.isFalse(run.getRunPr().contains(FontAttr.ITALIC));
        msAssert.isFalse(run.getRunPr().contains(FontAttr.ITALIC_BI));
        msAssert.isFalse(run.getRunPr().contains(FontAttr.SIZE_BI));
        msAssert.areEqual(14, (/*int*/Integer)run.getRunPr().get(FontAttr.SIZE));
    }



    /// <summary>
    /// Related to WORDSNET-17046.
    /// Case when "FontInfos" does not contain information about the font, which is checked to be overridden.
    /// </summary>
    @Test
    public void testJira17046NoFontInfo() throws Exception
    {
        Document doc = TestUtil.open("Model\\Run\\Font\\TestJira17046NoFontInfo.docx");
        TestUtil.executeValidator(doc, SaveFormat.DOCX);

        Run run = doc.getFirstSection().getBody().getFirstParagraph().getFirstRun();
        msAssert.areEqual("Times New Roman", ((ComplexFontName)run.getRunPr().get(FontAttr.NAME_ASCII)).getName());
        msAssert.areEqual("Cordia New", ((ComplexFontName)run.getRunPr().get(FontAttr.NAME_BI)).getName());
    }



    /// <summary>
    /// Replates to WORDSNET-19668 Checks that the font "Segoe UI Emoji" is set for all emojis from
    /// the emoticons block (0x1F600 - 0x1F64F).
    /// </summary>
    @Test
    public void test19668Emoticons() throws Exception
    {
        Document doc = TestUtil.open("Model\\Run\\Font\\Test19668Emoticons.docx");
        SaveOptions so = SaveOptions.createSaveOptions(SaveFormat.DOCX);
        so.setUpdateAmbiguousTextFont(true);
        TestUtil.executeValidator(doc, so);

        NodeCollection nodes = doc.getFirstSection().getBody().getChildNodes(NodeType.RUN, true);
        ArrayList<Run> runs = nodes.<Run>toList();

        msAssert.areEqual(1, runs.size());
        msAssert.areEqual("Segoe UI Emoji", runs.get(0).getFont().getName());
    }

    // FOSS: Test20143_DMLFloatingStory / _DMLFloatingInTextbox / _VMLFloatingInFootnote / _VMLFloatingInTextbox
    // removed — they exercised the fixed-format (PDF) rendering-prep validation that makes/moves floating
    // shapes in unexpected stories inline; that layout/rendering-prep path is gone.


    /// <summary>
    /// Related with WORDSNET-24026.
    /// This test related with the case when a shape is shrink by AW when it has large size.
    /// Comment was the following:
    /// "We normally validate shape size when width or height is set, but we also allow validation
    /// of the shape size before document save. This is needed in case some child-level
    /// shape with huge local coordinates was turned into a top-level shape by the user."
    /// However, the Word does not shrink a shape at this case. This test checks that AW behavior mimics the Word.
    /// </summary>
    // FOSS: Test24026UnGroup removed — it loads a WordML (.wml) input (removed) and validates for the
    // removed Doc format.


    /// <summary>
    /// WORDSNET-23156 Vertical table cell merge disappears on saving to docx, pdf.
    /// The problem related with the vertical merge algorithm which calling on a table validation.
    /// Update vertical merge algorithm to fix the issue.
    /// </summary>
    @Test
    public void test23156() throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\Test23156.docx");
        TestUtil.executeValidator(doc, SaveFormat.DOCX);

        Table tbl = doc.getFirstSection().getBody().getTables().get(1);

        checkVertMergeAndGridSpan(tbl.getFirstRow().getFirstCell().getCellPr(), CellMerge.FIRST, 2);
        checkVertMergeAndGridSpan(tbl.getFirstRow().getLastCell().getCellPr(), CellMerge.NONE, 1);

        checkVertMergeAndGridSpan(tbl.getLastRow().getFirstCell().getCellPr(), CellMerge.PREVIOUS, 1);
        checkVertMergeAndGridSpan(tbl.getLastRow().getLastCell().getCellPr(), CellMerge.NONE, 2);

        tbl = doc.getFirstSection().getBody().getTables().get(2);

        checkVertMergeAndGridSpan(tbl.getFirstRow().getFirstCell().getCellPr(), CellMerge.FIRST, 2);
        checkVertMergeAndGridSpan(tbl.getFirstRow().getLastCell().getCellPr(), CellMerge.NONE, 1);

        checkVertMergeAndGridSpan(tbl.getLastRow().getFirstCell().getCellPr(), CellMerge.PREVIOUS, 1);
        checkVertMergeAndGridSpan(tbl.getLastRow().getLastCell().getCellPr(), CellMerge.NONE, 2);
    }

    /// <summary>
    /// WORDSNET-25489 Accessing paragraph border creates None border in the output document.
    /// We should not reset <see cref="Border.IsInherited"/> flag in validator,
    /// as we rely on it when writing border in writers.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf save were removed.
    @Test (dataProvider = "test25489DataProvider")
    public void test25489(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.write("This paragraph should have border.");

        // This getter creates inherited border, that should not affect output.
        Border border = doc.getFirstSection().getBody().getFirstParagraph().getParagraphFormat().getBorders().getBottom();
        msAssert.areEqual(LineStyle.NONE, border.getLineStyle());

        // Validator should not reset Border.IsInherited flag.
        TestUtil.executeValidator(doc, SaveFormat.DOCX);
        msAssert.isTrue(border.isInherited());

        // Otherwise, setting border in style will be ignored, that is incorrect.
        Style style = doc.getStyles().add(StyleType.PARAGRAPH, "Heading1");
        style.getParagraphFormat().getBorders().getBottom().setLineStyle(LineStyle.SINGLE);
        style.getParagraphFormat().getBorders().getBottom().setLineWidth(5.0);

        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        para.getParagraphFormat().setStyle(style);

        TestUtil.saveOpen(doc, msString.format("Model\\ParaBorder\\Test25489.{0}", FileFormatUtil.saveFormatToExtension(sf)));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test25489DataProvider")
	public static Object[][] test25489DataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-25489.
    /// Checks roundtrip of the document when border of the paragraph is set explicitly to 'none'.
    /// </summary>
    // FOSS: only Docx2Docx survives — Doc/Rtf load+save were removed.
    @Test (dataProvider = "test25489ADataProvider")
    public void test25489A(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\ParaBorder\\Test25489None", scenario);
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();

        Border border = para.getParagraphFormat().getBorders().getBottom();
        msAssert.areEqual(LineStyle.NONE, border.getLineStyle());
        msAssert.isFalse(border.isInherited());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test25489ADataProvider")
	public static Object[][] test25489ADataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.DOCX_2_DOCX},
		};
	}

    // FOSS: Test26634 removed — it verified REF-field preservation specifically for DOTM->DOC conversion;
    // DOC save is removed, and the DOTM->DOCX path yields a different field breakdown (field[2] becomes
    // FieldNone), so the DOC-specific scenario no longer applies.


    /// <summary>
    /// WORDSNET-26819 UpdateFields raises 'IndexOutOfRangeException'.
    /// Missed custom footnote reference mark causes exception in layout.
    /// </summary>
    @Test
    public void test26819_Footnotes() throws Exception
    {
        Document document = TestUtil.open("Model\\Footnote\\Test26819 Footnotes.docx");

        TestUtil.executeValidator(document, SaveFormat.DOCX);

        msAssert.areEqual(9, document.getChildNodes(NodeType.FOOTNOTE, true).getCount());

        ParagraphCollection paragraphs = document.getFirstSection().getBody().getParagraphs();
        assertFootnote(paragraphs.get(2), true, "", "\u0002 Footnote 1\r");
        assertFootnote(paragraphs.get(5), false, "Move", "Footnote 2\r");
        assertFootnote(paragraphs.get(8), true, "", "\u0002 Footnote 3\r");
        assertFootnote(paragraphs.get(9), true, "", "\u0002 Footnote 4\r");
        assertFootnote(paragraphs.get(12), false, "Move to", "\u0002 Footnote 6\r\u0002 Footnote 5\r");
        assertFootnote(paragraphs.get(14), false, "Move to", "Footnote 8\rFootnote 7\r");
        assertFootnote(paragraphs.get(16), false, "Move to", "Footnote 10\r\u0002 Footnote 9\r");
        assertFootnote(paragraphs.get(18), false, "Move to", "\u0002 Footnote 12\rFootnote 11\r");

        assertFootnote(
            document.getFirstSection().getBody().getTables().get(0).getLastRow().getLastCell().getParagraphs().get(0),
            true,
            "",
            "\u0002 Footnote 13\r");
    }

    /// <summary>
    /// WORDSNET-26819 UpdateFields raises 'IndexOutOfRangeException'.
    /// Missed custom footnote reference mark causes exception in layout.
    /// </summary>
    @Test
    public void test26819_Endnotes() throws Exception
    {
        Document document = TestUtil.open("Model\\Footnote\\Test26819 Endnotes.docx");

        TestUtil.executeValidator(document, SaveFormat.DOCX);

        msAssert.areEqual(9, document.getChildNodes(NodeType.FOOTNOTE, true).getCount());

        ParagraphCollection paragraphs = document.getFirstSection().getBody().getParagraphs();
        assertFootnote(paragraphs.get(2), false, "Move", "\u0002 Endnote 1\r");
        assertFootnote(paragraphs.get(5), false, "Move", "Endnote 2\r");
        assertFootnote(paragraphs.get(8), false, "Move from and to", "\u0002 Endnote 3\r");
        assertFootnote(paragraphs.get(9), false, "Move to", "\u0002 Endnote 4\r");
        assertFootnote(paragraphs.get(12), false, "Move to", "\u0002 Endnote 6\r\u0002 Endnote 5\r");
        assertFootnote(paragraphs.get(14), false, "Move to", "Endnote 8\rEndnote 7\r");
        assertFootnote(paragraphs.get(16), false, "Move to", "Endnote 10\r\u0002 Endnote 9\r");
        assertFootnote(paragraphs.get(18), false, "Move to", "\u0002 Endnote 12\rEndnote 11\r");

        assertFootnote(
            document.getFirstSection().getBody().getTables().get(0).getLastRow().getLastCell().getParagraphs().get(0),
            false,
            "Move to",
            "\u0002 Endnote 13\r");
    }

    private static void assertFootnote(Paragraph paragraph, boolean isAuto, String referenceMark, String footnoteText)
    {
        Footnote footnote = (Footnote)paragraph.getFirstChild();

        msAssert.areEqual(isAuto, footnote.isAuto());
        msAssert.areEqual(referenceMark, footnote.getReferenceMark());
        msAssert.areEqual(footnoteText, footnote.getText());
    }

    private static void checkVertMergeAndGridSpan(CellPr cellPr, /*CellMerge*/int expectedMerge, int expectedSpan)
    {
        msAssert.areEqual(expectedMerge, cellPr.getVerticalMerge());
        msAssert.areEqual(expectedSpan, cellPr.getGridSpan());
    }
}

