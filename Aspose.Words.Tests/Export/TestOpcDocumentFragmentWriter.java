// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/10/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.msObject;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.StreamWriter;
import com.aspose.ms.System.Text.UTF8Encoding;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests checks export of a document fragment to the FOPC format.
/// </summary>
@Test
public class TestOpcDocumentFragmentWriter
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    ///  WORDSNET-22409 Requesting feature to get OOXML of content control.
    ///  <see cref="StructuredDocumentTag.WordOpenXML"> and <see cref="StructuredDocumentTagRangeStart.WordOpenXML">
    ///  properties implemented for ability to export a SDT content to FOPC format.
    /// </summary>
    @Test (dataProvider = "test22409DataProvider")
    public void test22409(String tag, boolean isRange) throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409.docx");
        int sdtLength = this.<Node>findNodeByTag(doc, tag).getRange().getText().length();

        String fopcContent = isRange ?
            this.<StructuredDocumentTagRangeStart>findNodeByTag(doc, tag).getWordOpenXML() :
            this.<StructuredDocumentTag>findNodeByTag(doc, tag).getWordOpenXML();

        // Use golds due to priority level.
        doc = saveAndCheckGold(fopcContent, "ExportDocx\\Test22409" + tag);

        // There is not a simple way to obtain text for multi sections SDT. So, skip the case with range.
        msAssert.areEqual(sdtLength, isRange ? 0 : doc.getText().length());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test22409DataProvider")
	public static Object[][] test22409DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Component 1",  false},
			{"Component 2",  false},
			{"Component 3",  false},
			{"Component 4",  false},
			{"Component 5",  false},
			{"Component 6",  false},
			{"Component 7",  false},
			{"Component 8",  false},
			{"Component 9",  true},
			{"Component 10",  false},
			{"Component 11",  false},
			{"Component 12",  false},
			{"Component 13",  false},
		};
	}

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks simple cases for SDT on different levels.
    /// </summary>
    @Test (dataProvider = "test22409LevelsDataProvider")
    public void test22409Levels(String tag, String content) throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409Levels.docx");

        String fopcContent = this.<StructuredDocumentTag>findNodeByTag(doc, tag).getWordOpenXML();
        doc = openDocFromString(fopcContent);

        msAssert.areEqual(content, doc.getText());
        checkSectPr(doc.getFirstSection(), 1134, 1701, 850, 708);
        checkSettings(doc);
        checkStyles(doc);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test22409LevelsDataProvider")
	public static Object[][] test22409LevelsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Cell",  "Cell level\f"},
			{"Inline",  "Inline SDT\f"},
			{"Block",  "Block\rLevel\rSDT\f"},
			{"Row",  "Amount\u0007Term\u0007InterestFrequency\u0007\u0007\f"},
		};
	}


    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks the case when SDT row level SDT contains two rows.
    /// </summary>
    @Test
    public void test22409RowLevelTwoRows() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409RowLevelTwoRows.docx");

        String fopcContent = this.<StructuredDocumentTag>findNodeByTag(doc, "Rows").getWordOpenXML();
        doc = openDocFromString(fopcContent);

        Table tbl = doc.getFirstSection().getBody().getTables().get(0);
        msAssert.areEqual("Amount\u0007", tbl.getFirstRow().getCells().get(0).getText());
        msAssert.areEqual("Term\u0007", tbl.getFirstRow().getCells().get(1).getText());
        msAssert.areEqual("InterestFrequency\u0007", tbl.getFirstRow().getCells().get(2).getText());

        msAssert.areEqual("Amount2\u0007", tbl.getLastRow().getCells().get(0).getText());
        msAssert.areEqual("Term2\u0007", tbl.getLastRow().getCells().get(1).getText());
        msAssert.areEqual("InterestFrequency2\u0007", tbl.getLastRow().getCells().get(2).getText());

        msAssert.areEqual(2, tbl.getRows().getCount());
        msAssert.areEqual(3, tbl.getFirstRow().getCells().getCount());
        msAssert.areEqual(1, doc.getFirstSection().getBody().getTables().getCount());
    }

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks the case when SDT spreads to multiple sections.
    /// </summary>
    @Test
    public void test22409SdtRange() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409SdtRange.docx");

        String fopcContent = this.<StructuredDocumentTagRangeStart>findNodeByTag(doc, "MultiSections").getWordOpenXML();
        doc = openDocFromString(fopcContent);

        msAssert.areEqual(2, doc.getSections().getCount());
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
        msAssert.areEqual("\f", doc.getFirstSection().getBody().getFirstParagraph().getText());
        msAssert.areEqual("List Paragraph", doc.getFirstSection().getBody().getFirstParagraph().getParagraphFormat().getStyleName());

        checkPageSize(doc.getFirstSection(), 350.0, 350.0);

        msAssert.areEqual(1, doc.getLastSection().getBody().getParagraphs().getCount());
        msAssert.areEqual("Section break above is inside SDT\f", doc.getLastSection().getBody().getFirstParagraph().getText());
        msAssert.areEqual("List Paragraph", doc.getLastSection().getBody().getFirstParagraph().getParagraphFormat().getStyleName());

        checkPageSize(doc.getLastSection(), 612.0, 792.0);
    }

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks the case when SDT placed in the header and footer.
    /// </summary>
    @Test (dataProvider = "test22409HeaderFooterDataProvider")
    public void test22409HeaderFooter(String tag, String expectedContent) throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409HeaderFooter.docx");

        String fopcContent = this.<StructuredDocumentTag>findNodeByTag(doc, tag).getWordOpenXML();
        doc = openDocFromString(fopcContent);

        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
        msAssert.areEqual(expectedContent, doc.getFirstSection().getBody().getFirstParagraph().getText());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test22409HeaderFooterDataProvider")
	public static Object[][] test22409HeaderFooterDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Header",  "Header SDT\f"},
			{"Footer",  "Footer SDT\f"},
			{"Footnote",  "Footnote SDT\f"},
		};
	}

    /// <summary>
    /// Related with WORDSNET-22409
    /// Check behavior when SDT has non-updated content.
    /// Looks like it is not possible to set mapping only for a row or cell.
    /// So, skip cases for row/cell level SD's.
    /// </summary>
    @Test (dataProvider = "test22409ContentUpdateDataProvider")
    public void test22409ContentUpdate(String tag) throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409ContentUpdate.docx");
        doc.getBuiltInDocumentProperties().setComments("ABC");

        String fopcContent = this.<StructuredDocumentTag>findNodeByTag(doc, tag).getWordOpenXML();
        doc = openDocFromString(fopcContent);

        msAssert.areEqual("ABC\f", doc.getFirstSection().getBody().getFirstParagraph().getText());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test22409ContentUpdateDataProvider")
	public static Object[][] test22409ContentUpdateDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Block"},
			{"Inline"},
		};
	}

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks the case when SDT has not a content. AW writes empty document at this case.
    /// SDT node at this case still has reference to the document even node was not added.
    /// So, SDT may be exported when it is not in the document tree.
    /// </summary>
    @Test (dataProvider = "test22409EmptySdtDataProvider")
    public void test22409EmptySdt(/*MarkupLevel*/int level) throws Exception
    {
        StructuredDocumentTag sdt = new StructuredDocumentTag(new Document(), level);
        Document fopcDoc = openDocFromString(sdt.getWordOpenXML());

        msAssert.areEqual(1, fopcDoc.getSections().getCount());
        msAssert.areEqual("\f", fopcDoc.getFirstSection().getBody().getText());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test22409EmptySdtDataProvider")
	public static Object[][] test22409EmptySdtDataProvider() throws Exception
	{
		return new Object[][]
		{
			{MarkupLevel.ROW},
			{MarkupLevel.CELL},
			{MarkupLevel.BLOCK},
			{MarkupLevel.INLINE},
		};
	}

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks the case when SDT range has not a content. AW writes empty document at this case.
    /// </summary>
    @Test
    public void test22409EmptySdtRange() throws Exception
    {
        StructuredDocumentTagRangeStart sdtRangeStart = createEmptySdtRange();
        Document fopcDoc = openDocFromString(sdtRangeStart.getWordOpenXML());

        msAssert.areEqual(1, fopcDoc.getSections().getCount());
        msAssert.areEqual("\f", fopcDoc.getFirstSection().getBody().getText());
    }

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks the case when range start or end is not in the document tree.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "Structured document tag range (?s).*", dataProvider = "test22409SdtRangeNotInTreeDataProvider")

    public void test22409SdtRangeNotInTree(boolean start) throws Exception
    {
        StructuredDocumentTagRangeStart sdtRangeStart = createEmptySdtRange();

        Node nodeToRemove = start ? sdtRangeStart : (Node)sdtRangeStart.getRangeEnd();
        nodeToRemove.remove();

        openDocFromString(sdtRangeStart.getWordOpenXML());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test22409SdtRangeNotInTreeDataProvider")
	public static Object[][] test22409SdtRangeNotInTreeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks the case when SDT placed in the glossary document.
    /// AW does not write content for such SDT for a while.
    /// </summary>
    @Test
    public void test22409SdtInGlossary() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409SDTInGlossary.docx");
        StructuredDocumentTag sdt =
            (StructuredDocumentTag)doc.getGlossaryDocument().getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);

        Document fopcDoc = openDocFromString(sdt.getWordOpenXML());

        msAssert.areEqual(1, fopcDoc.getSections().getCount());
        msAssert.areEqual("\f", fopcDoc.getFirstSection().getBody().getText());
    }

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks that SDT export does not produce warnings notifications.
    /// </summary>
    @Test
    public void test22409Warns() throws Exception
    {
        Document doc = new Document();
        TestWarningCallback wc = new TestWarningCallback();
        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.INLINE);

        doc.ensureMinimum();
        doc.setWarningCallback(wc);
        doc.setAutomaticallyUpdateStyles(true);

        sdt.appendChild(new Run(doc, "test"));
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(sdt);

        // Save the document.
        MemoryStream ms = new MemoryStream();
        try /*JAVA: was using*/
    	{
            doc.save(ms, SaveFormat.FLAT_OPC);
    	}
        finally { if (ms != null) ms.close(); }

        wc.contains(WarningSource.VALIDATOR,
            WarningType.DATA_LOSS, WarningStrings.EMPTY_TEMPLATE_PATH);

        // Save SDTto FOPC.
        wc.clear();
        doc = openDocFromString(sdt.getWordOpenXML());

        msAssert.areEqual(0, wc.getCount());
        msAssert.areEqual("Click here to enter text.test\f", doc.getText());
    }

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks the case when SDT spreads to three sections.
    /// </summary>
    @Test
    public void test22409SdtRangeThreeSections() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409SdtRangeThreeSections.docx");

        String fopcContent = this.<StructuredDocumentTagRangeStart>findNodeByTag(doc, "MultiSections").getWordOpenXML();
        doc = openDocFromString(fopcContent);

        msAssert.areEqual(3, doc.getSections().getCount());
        checkPageSize(doc.getFirstSection(), 350.0, 350.0);
        checkPageSize(doc.getSections().get(1), 350.0, 350.0);
        checkPageSize(doc.getLastSection(), 612.0, 792.0);

        msAssert.areEqual("a\f", doc.getFirstSection().getBody().getText());
        msAssert.areEqual("b\f", doc.getSections().get(1).getBody().getText());
        msAssert.areEqual("Section break above is inside SDT\f", doc.getLastSection().getBody().getText());

        msAssert.less(0, doc.getFirstSection().getHeadersFooters().getCount());
        msAssert.less(0, doc.getSections().get(1).getHeadersFooters().getCount());
        msAssert.less(0, doc.getLastSection().getHeadersFooters().getCount());

        msAssert.areEqual("Header first section\r", doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getText());
        msAssert.areEqual("Header second section\r", doc.getSections().get(1).getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getText());
        msAssert.areEqual("Header third section\r", doc.getLastSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getText());
    }

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks the case when there is a content before the SDT range and this content should
    /// not be copied to the destination.
    /// </summary>
    @Test
    public void test22409SdtRangeContentBefore() throws Exception
    {
        final String TEXT_BEFORE = "Text before";
        Document doc = TestUtil.open("ExportDocx\\Test22409SdtRangeContentBefore.docx");

        msAssert.areEqual(TEXT_BEFORE + "\r", doc.getFirstSection().getBody().getFirstParagraph().getText());
        String fopcContent = this.<StructuredDocumentTagRangeStart>findNodeByTag(doc, "MultiSections").getWordOpenXML();
        doc = openDocFromString(fopcContent);

        msAssert.areEqual("\f", doc.getFirstSection().getBody().getFirstParagraph().getText());
    }

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks the case when there is a content after the SDT range and this content should
    /// not be copied to the destination.
    /// </summary>
    @Test
    public void test22409SdtRangeContentAfter() throws Exception
    {
        final String TEXT_AFTER = "Text after";
        Document doc = TestUtil.open("ExportDocx\\Test22409SdtRangeContentAfter.docx");

        msAssert.areEqual(TEXT_AFTER + "\f", doc.getLastSection().getBody().getLastParagraph().getText());
        String fopcContent = this.<StructuredDocumentTagRangeStart>findNodeByTag(doc, "MultiSections").getWordOpenXML();
        doc = openDocFromString(fopcContent);

        msAssert.areEqual("Section break above is inside SDT\f", doc.getLastSection().getBody().getLastParagraph().getText());
    }

    /// <summary>
    /// Related with WORDSNET-22409
    /// The case when nested SDT has mapped content.
    /// </summary>
    @Test
    public void test22409Nested() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409Nested.docx");

        String fopcContent = this.<StructuredDocumentTag>findNodeByTag(doc, "Top").getWordOpenXML();
        doc = openDocFromString(fopcContent);

        // Value mapped to document property. And in the new document this property is empty.
        // So, we will see placeholder text,
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getFirstSection().getBody().getFirstParagraph().getFirstChild();
        msAssert.areEqual("Click or tap here to enter text.", ((Run)sdt.getFirstChild()).getText());

        msAssert.areEqual(1, doc.getSections().getCount());
        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());
        msAssert.areEqual(1, doc.getFirstSection().getBody().getFirstParagraph().getChildNodes(NodeType.ANY, false).getCount());
    }

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks the case when a cell has two SDT ancestors with cell level.
    /// </summary>
    @Test
    public void test22409MultipleCellLevel() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409MultipleCellLevel.docx");

        String fopcContent = this.<StructuredDocumentTag>findNodeByTag(doc, "CellLevel").getWordOpenXML();
        doc = openDocFromString(fopcContent);

        msAssert.areEqual("RichText SDT inside Table inside other SDT Cell2\f", doc.getText());
        // Actually here should be one SDT. Skip for a while.
        msAssert.areEqual(0, doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).getCount());
    }



    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks export of the "checkbox" SDT.
    /// </summary>
    @Test
    public void test22409CheckboxSdt() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409CheckboxSdt.docx");

        String fopcContentUnchecked = this.<StructuredDocumentTag>findNodeByTag(doc, "Unchecked").getWordOpenXML();
        String fopcContentChecked = this.<StructuredDocumentTag>findNodeByTag(doc, "Checked").getWordOpenXML();

        doc = openDocFromString(fopcContentUnchecked);
        msAssert.areEqual("☐\f", doc.getText());

        doc = openDocFromString(fopcContentChecked);
        msAssert.areEqual("☒\f", doc.getText());
    }

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks export of the "combobox" SDT.
    /// </summary>
    @Test
    public void test22409ComboboxSdt() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409ComboboxSdt.docx");

        String fopcContentSelected = this.<StructuredDocumentTag>findNodeByTag(doc, "ItemSelected").getWordOpenXML();
        String fopcContentNoSelected = this.<StructuredDocumentTag>findNodeByTag(doc, "NoItemSelected").getWordOpenXML();

        doc = openDocFromString(fopcContentSelected);
        msAssert.areEqual("Item2\f", doc.getText());

        doc = openDocFromString(fopcContentNoSelected);
        msAssert.areEqual("Choose an item.\f", doc.getText());
    }

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks export of the "date" SDT.
    /// </summary>
    @Test
    public void test22409DateSdt() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409DateSdt.docx");

        String fopcContentSelected = this.<StructuredDocumentTag>findNodeByTag(doc, "DateSelected").getWordOpenXML();
        String fopcContentNoSelected = this.<StructuredDocumentTag>findNodeByTag(doc, "NoDateSelected").getWordOpenXML();

        doc = openDocFromString(fopcContentSelected);
        msAssert.areEqual("12.11.2021\f", doc.getText());

        doc = openDocFromString(fopcContentNoSelected);
        msAssert.areEqual("Click or tap to enter a date.\f", doc.getText());
    }

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks export of the "dropdown" SDT.
    /// </summary>
    @Test
    public void test22409DropdownSdt() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409DropdownSdt.docx");

        String fopcContentSelected = this.<StructuredDocumentTag>findNodeByTag(doc, "ItemSelected").getWordOpenXML();
        String fopcContentNoSelected = this.<StructuredDocumentTag>findNodeByTag(doc, "NoItemSelected").getWordOpenXML();

        doc = openDocFromString(fopcContentSelected);
        msAssert.areEqual("Item2\f", doc.getText());

        doc = openDocFromString(fopcContentNoSelected);
        msAssert.areEqual("Choose an item.\f", doc.getText());
    }

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks export of the "picture" SDT.
    /// Actually the Word produces empty document in this case.
    /// </summary>
    @Test
    public void test22409ImgSdt() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409ImgSdt.docx");

        String fopcContent = this.<StructuredDocumentTag>findNodeByTag(doc, "Img").getWordOpenXML();
        doc = openDocFromString(fopcContent);

        msAssert.areEqual("\f", doc.getText());

        Shape shape = (Shape)doc.getFirstSection().getBody().getFirstParagraph().getFirstChild();
        msAssert.isNotNull(shape.getImageData().getImageBytes());
        msAssert.areEqual(ShapeType.IMAGE, shape.getShapeType());
    }

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks export of the "repeating section" SDT.
    /// Actually the Word throws the exception in this case.
    /// </summary>
    @Test
    public void test22409RepeatingSectionSdt() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409RepeatingSectionSdt.docx");

        String fopcContent = this.<StructuredDocumentTag>findNodeByTag(doc, "RepeatingSection").getWordOpenXML();
        doc = openDocFromString(fopcContent);

        msAssert.areEqual("\rRow\u0007\u0007\u0007\r\rRow2\u0007\u0007\u0007\f", doc.getText());
    }

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks export of the "group" SDT.
    /// </summary>
    @Test
    public void test22409GroupSdt() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409GroupSdt.docx");

        String fopcContent = this.<StructuredDocumentTag>findNodeByTag(doc, "Group").getWordOpenXML();
        doc = openDocFromString(fopcContent);

        msAssert.areEqual("☒text\f", doc.getText());

        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(SdtType.CHECKBOX, ((StructuredDocumentTag)para.getFirstChild()).getSdtType());
        msAssert.areEqual(SdtType.PLAIN_TEXT, ((StructuredDocumentTag)para.getLastChild()).getSdtType());
    }



    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks export of the "docPartObj" SDT.
    /// Actually the Word produces empty document in this case.
    /// </summary>
    @Test
    public void test22409DocPartObjSdt() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409DocPartSdt.docx");

        String fopcContent = this.<StructuredDocumentTag>findNodeByTag(doc, "docPartObj").getWordOpenXML();
        doc = openDocFromString(fopcContent);

        msAssert.areEqual("Test\f", doc.getText());
    }


    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks the case when SDT placed in a shape.
    /// </summary>
    @Test
    public void test22409SdtInShape() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409SdtInShape.docx");

        String fopcContent = this.<StructuredDocumentTag>findNodeByTag(doc, "ShapeSdt").getWordOpenXML();
        doc = openDocFromString(fopcContent);

        msAssert.areEqual("Sdt text\f", doc.getText());
    }

    /// <summary>
    /// Related with WORDSNET-22409
    /// Checks the case when SDT has unknown level.
    /// </summary>
    @Test
    public void test22409SdtUnknownLevel() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, MarkupLevel.UNKNOWN);

        doc = openDocFromString(sdt.getWordOpenXML());
        msAssert.areEqual("\f", doc.getText());
    }

    /// <summary>
    /// WORDSNET-23220 The paragraph is missing when using WordOpenXML.
    /// Paragraph was lost because of wrong iteration approach through nodes.
    /// </summary>
    @Test
    public void test23220() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test23220.docx");
        doc.getRevisions().acceptAll();

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        doc = openDocFromString(sdt.getWordOpenXML());

        Body body = doc.getFirstSection().getBody();
        msAssert.areEqual(2, body.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("Lorem ipsum dolor sit amet\r", body.getFirstParagraph().getText());
        msAssert.areEqual("Maecenas porttitor congue massa. Fusce posuere.\f", body.getLastParagraph().getText());
    }

    /// <summary>
    /// WORDSNET-23887 Caption/Ref present on first para get missing.
    /// The Word inserts bookmark start node while exporting SDT content as XML. It happens when bookmark "start" follows
    /// before a SDT and resulted content has just bookmark end node. Mimic Word to fix the issue.
    /// </summary>
    @Test (dataProvider = "test23887DataProvider")
    public void test23887(String fileName) throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\" + fileName);

        final String TAG = "Component 1";
        String fopcContent = this.<StructuredDocumentTag>findNodeByTag(doc, TAG).getWordOpenXML();
        doc = openDocFromString(fopcContent);

        final String BOOKMARK_NAME = "_Ref101801046";
        BookmarkEnd end = BookmarkFinder.findBookmarkEnd(doc, BOOKMARK_NAME);
        BookmarkStart start = BookmarkFinder.findBookmarkStart(doc, BOOKMARK_NAME);

        msAssert.isNotNull(end);
        msAssert.isNotNull(start);
        msAssert.areEqual(DisplacedByType.UNSPECIFIED, end.getDisplacedBy());
        msAssert.areEqual(DisplacedByType.UNSPECIFIED, start.getDisplacedBy());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test23887DataProvider")
	public static Object[][] test23887DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Test23887.docx"},
			{"Test23887NoDisplacedByCustomXml.docx"},
		};
	}

    /// <summary>
    /// Related with WORDSNET-23887
    /// Checks the case when problematic document contains two bookmarks.
    /// </summary>
    @Test
    public void test23887TwoBookmarks() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test23887TwoBookmarks.docx");

        final String TAG = "Component 1";
        String fopcContent = this.<StructuredDocumentTag>findNodeByTag(doc, TAG).getWordOpenXML();
        doc = openDocFromString(fopcContent);

        final String BOOKMARK_NAME_1 = "bookmark1";
        BookmarkStart firstStart = BookmarkFinder.findBookmarkStart(doc, BOOKMARK_NAME_1);
        msAssert.isNotNull(firstStart);
        msAssert.isNotNull(BookmarkFinder.findBookmarkEnd(doc, BOOKMARK_NAME_1));

        final String BOOKMARK_NAME_2 = "_Ref101801046";
        BookmarkStart secondStart = BookmarkFinder.findBookmarkStart(doc, BOOKMARK_NAME_2);
        msAssert.isNotNull(secondStart);
        msAssert.isNotNull(BookmarkFinder.findBookmarkEnd(doc, BOOKMARK_NAME_2));

        // Check sequence.
        msAssert.isTrue(msObject.referenceEquals(firstStart, secondStart.getPreviousSibling()));
    }

    /// <summary>
    /// Related with WORDSNET-23887
    /// Checks the case when bookmark "start" does not exist at all.
    /// </summary>
    @Test
    public void test23887NoStart() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test23887NoStart.docx");

        final String TAG = "Component 1";
        String fopcContent = this.<StructuredDocumentTag>findNodeByTag(doc, TAG).getWordOpenXML();
        doc = openDocFromString(fopcContent);

        final String BOOKMARK_NAME = "_Ref101801046";
        msAssert.isNull(BookmarkFinder.findBookmarkStart(doc, BOOKMARK_NAME));
        msAssert.isNull(BookmarkFinder.findBookmarkEnd(doc, BOOKMARK_NAME));
    }

    /// <summary>
    /// WORDSNET-25558 - Add 'WordOpenXmlMinimal' Property to 'StructuredDocumentTagRangeStart' Class
    /// Added public WordOpenXMLMinimal for StructuredDocumentTagRangeStart node.
    /// </summary>
    @Test
    public void test25558() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test22409.docx");

        StructuredDocumentTagRangeStart sdtRangeStart =
            this.<StructuredDocumentTagRangeStart>findNodeByTag(doc, "Component 9");
        String fopcContentMinimal = sdtRangeStart.getWordOpenXMLMinimal();

        msAssert.greater(18000, fopcContentMinimal.length());
    }

    /// <summary>
    /// WORDSNET-28539 WordOpenXMLMinimal output includes document-level protection in protected DOCX.
    /// Tests that FlatOpc output document obtained via WordOpenXMLMinimal does not preserve an active protection type.
    /// </summary>
    @Test
    public void test28539() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test28539.docx");
        // The input document has an active protection type.
        msAssert.areEqual(ProtectionType.ALLOW_ONLY_REVISIONS, doc.getProtectionType());

        String fopcContent = this.<StructuredDocumentTag>findNodeByTag(doc, "Component 1").getWordOpenXMLMinimal();
        doc = openDocFromString(fopcContent);

        msAssert.areEqual(ProtectionType.NO_PROTECTION, doc.getProtectionType());
    }

    private static void checkPageSize(Section sect, double width, double height)
    {
        msAssert.areEqual(width, 0.001, sect.getPageSetup().getPageWidth());
        msAssert.areEqual(height, 0.001, sect.getPageSetup().getPageHeight());
    }

    private static StructuredDocumentTagRangeStart createEmptySdtRange() throws Exception
    {
        Document doc = new Document();
        doc.ensureMinimum();

        StructuredDocumentTagRangeStart sdtRangeStart = new StructuredDocumentTagRangeStart(doc, SdtType.RICH_TEXT);
        StructuredDocumentTagRangeEnd sdtRangeEnd = new StructuredDocumentTagRangeEnd(doc, sdtRangeStart.getId());

        doc.getFirstSection().getBody().prependChild(sdtRangeEnd);
        doc.getFirstSection().getBody().prependChild(sdtRangeStart);

        return sdtRangeStart;
    }

    /// <summary>
    /// Finds node in the document using specified tag.
    /// </summary>
    /// <typeparam name="T">Node type which contains the tag.</typeparam>
    /// <param name="doc">Source document.</param>
    /// <param name="tag">Node tag value.</param>
    /// <returns>Node from the source document.</returns>
    private static <T extends Node> T findNodeByTag(Document doc, String tag) throws Exception
    {
        ArrayList<Node> nodes = new ArrayList<Node>();

        NodeCollection filteredNodes = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        msArrayList.addRange(nodes, filteredNodes);

        filteredNodes = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, true);
        msArrayList.addRange(nodes, filteredNodes);

        for (Node node : nodes)
        {
            switch (node.getNodeType())
            {
                case NodeType.STRUCTURED_DOCUMENT_TAG:
                {
                    if (msString.equals(((StructuredDocumentTag)node).getTag(), tag))
                        return (T) node;

                    break;
                }

                case NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START:
                {
                    if (msString.equals(((StructuredDocumentTagRangeStart)node).getTag(), tag))
                        return (T) node;

                    break;
                }
            }
        }

        throw new Exception("Node with tag \"" + tag + "\" was not found.");
    }

    private static Document saveAndCheckGold(String fopcDocument, String fileName) throws Exception
    {
        Document outDoc = openDocFromString(fopcDocument);

        // Check markup via gold.
        outDoc = TestUtil.saveOpen(
            outDoc,
            fileName,
            TestUtil.buildScenario(LoadFormat.FLAT_OPC, SaveFormat.FLAT_OPC, false));

        return outDoc;
    }

    private static Document openDocFromString(String fopcDocument) throws Exception
    {
        MemoryStream ms = new MemoryStream();
        try /*JAVA: was using*/
    	{
        StreamWriter sw = new StreamWriter(ms, new UTF8Encoding(false));
        try /*JAVA: was using*/
        {
            sw.write(fopcDocument);
            sw.flush();

            LoadOptions lo = new LoadOptions();
            lo.setLoadFormat(LoadFormat.FLAT_OPC);

            ms.setPosition(0);
            Document outDoc = new Document(ms, lo);

            return outDoc;
        }
        finally { if (sw != null) sw.close(); }
    	}
        finally { if (ms != null) ms.close(); }
    }

    private static void checkSettings(Document doc)
    {
        // Check some values explicitly.
        // Actually the Word does not adds "app.xml" and "core.xml" at all to a package.
        // AW writes default values for a while.
        msAssert.isFalse(doc.getBuiltInDocumentProperties().contains(PropertyName.AUTHOR));
        msAssert.areEqual(786432, doc.getBuiltInDocumentProperties().get(PropertyName.VERSION).toInt()); // 12.0000

        msAssert.areEqual(149, doc.getDocPr().ViewOptions.getZoomPercent());
        msAssert.areEqual(MsWordVersionCore.WORD_2013, doc.getCompatibilityOptions().getMswVersion());
    }

    private static void checkStyles(Document doc)
    {
        Style style = doc.getStyles().getByName("MyCustomStyle_11", false);
        msAssert.areEqual(567, style.getParaPr().getLeftIndent());
    }

    private static void checkSectPr(Section sect, int topMargin, int leftMargin, int rightMargin, int headerDistance)
    {
        SectPr sectPr = sect.getSectPr();

        msAssert.areEqual(topMargin, sectPr.getTopMargin());
        msAssert.areEqual(leftMargin, sectPr.getLeftMargin());
        msAssert.areEqual(rightMargin, sectPr.getRightMargin());
        msAssert.areEqual(headerDistance, sectPr.getHeaderDistance());
        msAssert.isTrue(msObject.referenceEquals(((Document)sect.getDocument()).getLastSection(), sect));
    }
}

