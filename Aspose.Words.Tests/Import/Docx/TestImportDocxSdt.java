// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/07/2010 by Denis Darkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.FormatterPal;
import com.aspose.LocaleConverter;
import com.aspose.ms.System.Text.msStringBuilder;
import java.util.ArrayList;
import com.aspose.StringUtil;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Test Structured Document Tags (SDT).
/// See Defect 4295 - Support Structured Document Tags (SDT) (Content Controls).
/// Sdt required a lot of changes across all AW code, not only conversions, which required additional testing.
/// </summary>
/// <remarks>
/// Additional tests related to Model and public API parts of Sdt feature are placed in the Model testing area
/// <see cref="TestSdt"/>
/// </remarks>
@Test
public class TestImportDocxSdt
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @BeforeMethod (alwaysRun = true)
    public void init()
    {
        RandomUtil.reset();
    }

    /// <summary>
    /// Validate that placeholder elements of sdt are read if Sdt is a part of GlossaryDocument itself.
    /// </summary>
    @Test
    public void testGlossarySdtPlaceholderText() throws Exception
    {
        Document doc = TestUtil.open("Model\\BuildingBlocks\\TestBuiltIn.dotx");
        GlossaryDocument glossary = doc.getGlossaryDocument();

        NodeCollection sdtCollection = glossary.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        msAssert.isNotNull(sdtCollection);

        StructuredDocumentTag sdt = ((StructuredDocumentTag)(sdtCollection.get(100)));
        msAssert.areEqual("[Type text]\f", sdt.getPlaceholder().getText());
    }

    /// <summary>
    /// Test that properties of sdt-wrapped paragraphs are correct.
    /// </summary>
    @Test
    public void testSdtPara() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\Markup\\TestSdtPara.docx");
        Paragraph paragraph = (Paragraph)
            ((CompositeNode) doc.getFirstSection().getHeadersFooters().get(0).getFirstChild()).getFirstNonMarkupCompositeDescendant();
        msAssert.areEqual("Para wrapped in double Sdt present in header\r", paragraph.getText());
        msAssert.isTrue(paragraph.isEndOfHeaderFooter());

        Table table0 = doc.getFirstSection().getBody().getTables().get(0);
        paragraph = (Paragraph)table0.getRows().get(0).getCells().get(0).getFirstNonMarkupCompositeDescendant();
        msAssert.areEqual("First Cell-nested para wrapped with sdt\r", paragraph.getText());
        msAssert.isTrue(paragraph.isStartOfCell());
        msAssert.isTrue(paragraph.isStartOfRow());
        msAssert.isTrue(paragraph.isStartOfTable());
        msAssert.isFalse(paragraph.isEndOfCell());

        paragraph = (Paragraph)paragraph.getNextNonMarkupCompositeLimited();
        msAssert.areEqual("Second cell-nested para wrapped with sdt\u0007", paragraph.getText());
        msAssert.isFalse(paragraph.isStartOfCell());
        msAssert.isFalse(paragraph.isStartOfRow());
        msAssert.isFalse(paragraph.isStartOfTable());
        msAssert.isTrue(paragraph.isEndOfCell());

        //check that non sdt para props also work after sdt modifications
        paragraph = (Paragraph)table0.getRows().get(0).getCells().get(1).getFirstNonMarkupCompositeDescendant();
        msAssert.areEqual("Simple para, no sdt\u0007", paragraph.getText());
        msAssert.isTrue(paragraph.isStartOfCell());
        msAssert.isTrue(paragraph.isEndOfRow());
        msAssert.isFalse(paragraph.isStartOfTable());
        msAssert.isTrue(paragraph.isEndOfCell());

        paragraph = (Paragraph)table0.getRows().get(1).getCells().get(1).getLastNonMarkupCompositeDescendant();
        msAssert.areEqual("Last-row, last-cell, para wrapped with sdt\u0007", paragraph.getText());
        msAssert.isTrue(paragraph.isStartOfCell());
        msAssert.isTrue(paragraph.isEndOfCell());
        msAssert.isFalse(paragraph.isStartOfRow());
        msAssert.isTrue(paragraph.isEndOfRow());
        msAssert.isFalse(paragraph.isStartOfTable());
        msAssert.isTrue(paragraph.isEndOfTable());

        paragraph = (Paragraph)table0.getNextSibling().getNextSibling().getNextNonMarkupCompositeLimited();
        msAssert.isTrue(paragraph.isInMainTextStory());
        msAssert.areEqual("Para wrapped in RichText Sdt present in main story\r", paragraph.getText());

        paragraph = (Paragraph)paragraph.getNextNonMarkupCompositeLimited().getNextNonMarkupCompositeLimited();
        msAssert.areEqual(NodeType.COMMENT_RANGE_START, paragraph.getPreviousSibling().getNodeType());
        msAssert.areEqual("Commented ", paragraph.getFirstChild().getText());
        msAssert.areEqual(NodeType.STRUCTURED_DOCUMENT_TAG, paragraph.getFirstChild().getNextSibling().getNodeType());
        msAssert.areEqual("text", paragraph.getFirstChild().getNextSibling().getText());
        msAssert.areEqual(NodeType.COMMENT, paragraph.getFirstChild().getNextSibling().getNextSibling().getNodeType());

        paragraph = (Paragraph)paragraph.getNextNonMarkupCompositeLimited().getNextNonMarkupCompositeLimited();
        msAssert.areEqual(" HYPERLINK \"http://www.sharewareplaza.com/hdd-repairer-seagate-barracuda-7200-7-downloads_51134.html\" My hyperlink wrapped with Sdt node\r", paragraph.getText());
    }

    /// <summary>
    /// Verify that elements at various levels are accessible if wrapped with Sdt parent node.
    /// </summary>
    @Test
    public void testSdtLevels() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Markup\\TestSdtLevels.docx");
        msAssert.areEqual(1, doc.getSections().get(0).getBody().getTables().getCount());
        Table testedTable = doc.getSections().get(0).getBody().getTables().get(0);
        msAssert.areEqual(2, testedTable.getRows().getCount());

        final String TESTED_ROW_CONTENTS = "Table row rich text sdt control test\u0007";
        msAssert.areEqual(TESTED_ROW_CONTENTS, testedTable.getLastRow().getFirstCell().getText());
        msAssert.areEqual(TESTED_ROW_CONTENTS, testedTable.getFirstRow().getNextRow().getFirstCell().getText());

        final String TESTED_TABLE_CONTENTS = "Table wide sdt control\u0007";
        msAssert.areEqual(TESTED_TABLE_CONTENTS, testedTable.getFirstRow().getFirstCell().getText());
        msAssert.areEqual(TESTED_TABLE_CONTENTS, (testedTable.getLastRow().getPreviousRow()).getFirstCell().getText());

        final String TESTED_CELL_TEXT = "Cell richText sdt control\u0007";
        msAssert.areEqual(TESTED_CELL_TEXT, testedTable.getFirstRow().getFirstCell().getNextNonMarkupCompositeLimited().getText());
        msAssert.areEqual(TESTED_CELL_TEXT, testedTable.getFirstRow().getLastCell().getPreviousNonMarkupCompositeLimited().getText());
    }

    /// <summary>
    /// Verify that node collection enumeration works in presence of wrapping Sdt nodes in both deep and flat modes.
    /// in the test doc two tables reside inside an Sdt and one is inside main story.
    /// </summary>
    @Test
    public void testMultipleTablesInSdt() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Markup\\TestMultipleTablesInSdt.docx");
        validateNodeCollection(new NodeCollection(doc.getSections().get(0).getBody(), NodeType.TABLE, true));
        validateNodeCollection(new NodeCollection(doc.getSections().get(0).getBody(), NodeType.TABLE, false));
    }

    private static void validateNodeCollection(NodeCollection nodes)
    {
        msAssert.areEqual(3, nodes.getCount());
        for (int i = 0; i < nodes.getCount(); i++)
            msAssert.areEqual(NodeType.TABLE, nodes.get(i).getNodeType());

        msAssert.areEqual("Table 1\u0007\u0007\u0007", nodes.get(0).getText());
        msAssert.areEqual("Table 2\u0007\u0007\u0007\u0007", nodes.get(1).getText());
        msAssert.areEqual("Table 3\u0007\u0007\u0007", nodes.get(2).getText());
    }


    /// <summary>
    /// Validates particular test <see cref="TestSdtFullCollection"/>.
    /// Is called twice to validate that sdt titles are as expected.
    /// </summary>
    /// <param name="allSdt"></param>
    private static void validateTitles(NodeCollection allSdt)
    {
        msAssert.areEqual(G_ALIASES_FOR_TEST_SDT_FULL_COLLECTION.length, allSdt.getCount());
        for (int i = 0; i < G_ALIASES_FOR_TEST_SDT_FULL_COLLECTION.length; i++)
        {
            try
            {
                msAssert.areEqual(G_ALIASES_FOR_TEST_SDT_FULL_COLLECTION[i], ((StructuredDocumentTag)allSdt.get(i)).getTitle());
            }
            catch(Exception e)
            {
                throw new IllegalStateException(msString.format(
                    "Mismatch at {0}-tht Sdt, required {1} Title name but was {2}.",
                    i, G_ALIASES_FOR_TEST_SDT_FULL_COLLECTION[i], ((StructuredDocumentTag)allSdt.get(i)).getTitle()));
            }
        }
    }


    // FOSS TestLayout removed: it verifies the layout engine doesn't crash on SDT nodes by rendering to
    // Pdf, a removed format (the FOSS build has no fixed-page rendering).


    /// <summary>
    /// Verify that sdt content is read.
    /// </summary>
    @Test
    public void testContentControls() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\Markup\\TestContentControls.docx");

        // Inline content control.
        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 0, true);
        msAssert.areEqual("Inline-level content control: Test text\r", para.getText());

        // Block level content control with two paragraphs.
        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 1, true);
        msAssert.areEqual("Block-level content control in body.\r", para.getText());
        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 2, true);
        msAssert.areEqual("Contains two paragraphs.\r", para.getText());

        // Block level content control contains a table.
        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        msAssert.areEqual("Block-level content control in body.\u0007Contains a 2x2 table\u0007\u0007\u0007\u0007\u0007", table.getText());

        table = (Table)doc.getChild(NodeType.TABLE, 1, true);

        // Row-level content control
        Row row = table.getRows().get(0);
        msAssert.areEqual("Row-level content control\u0007\u0007\u0007", row.getText());

        // Cell-level content control
        row = table.getRows().get(1);
        Cell cell = row.getCells().get(0);
        msAssert.areEqual("Cell-level content control\u0007", cell.getText());

        // Cell level content control with nested block level content control with nested inline level control.
        cell = row.getCells().get(1);
        msAssert.areEqual("Block level-content control in cell.\rContains two paragraphs. " +
                "Also a nested content control.\r\u0007", cell.getText());
    }

    /// <summary>
    /// Test1: Validate  the following child sdt elements of the spec:
    /// - dataBinding,
    ///     - xPath,
    ///     - storeItemID
    /// - alias,
    /// - tag,
    /// - id,
    /// - placeholder,
    /// - showingPlcHdr
    /// - date
    ///     - dateFormat,
    ///     - lid,
    ///     - storeMappedDataAs,
    ///     - calendar
    /// - comboBox
    ///     - listItem
    /// - dropDownList
    ///     - listItem
    /// </summary>
    @Test
    public void testSdtOpenSave1() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Markup\\TestSdtOpenSave1.docx");
        validateSdtNodesTest1(doc.getFirstSection().getBody().getChildNodes(NodeType.ANY, false));
        doc = TestUtil.saveOpen(doc, "ImportDocx\\Markup\\TestSdtOpenSave1.docx", LoadFormat.DOCX, SaveFormat.DOCX);
        validateSdtNodesTest1(doc.getFirstSection().getBody().getChildNodes(NodeType.ANY, false));
    }

    /// <summary>
    /// Tests that Office2010 SDT checkbox elements are read/written properly.
    /// </summary>
    @Test
    public void testSdtCheckbox() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\Markup\\TestSdtCheckbox.docx");

        // First checkbox has custom characters and font.
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getNodeById("0.0.0");
        msAssert.areEqual(SdtType.CHECKBOX, sdt.getControlProperties().getType());
        SdtCheckBox checkbox = (SdtCheckBox)sdt.getControlProperties();
        msAssert.isTrue(checkbox.getChecked());
        SdtCheckBoxStateInfo stateInfo = checkbox.getCheckedStateInfo();
        msAssert.areEqual("Times New Roman", stateInfo.getFontName());
        msAssert.areEqual(0x00A9, stateInfo.getCharacterCode());
        stateInfo = checkbox.getUncheckedStateInfo();
        msAssert.areEqual("Times New Roman", stateInfo.getFontName());
        msAssert.areEqual(0x00AE, stateInfo.getCharacterCode());

        // Second is default checkbox.
        sdt = (StructuredDocumentTag)doc.getNodeById("1.0.0");
        msAssert.areEqual(SdtType.CHECKBOX, sdt.getControlProperties().getType());
        checkbox = (SdtCheckBox)sdt.getControlProperties();
        msAssert.isFalse(checkbox.getChecked());
        stateInfo = checkbox.getCheckedStateInfo();
        msAssert.areEqual("MS Gothic", stateInfo.getFontName());
        msAssert.areEqual(0x2612, stateInfo.getCharacterCode());
        stateInfo = checkbox.getUncheckedStateInfo();
        msAssert.areEqual("MS Gothic", stateInfo.getFontName());
        msAssert.areEqual(0x2610, stateInfo.getCharacterCode());
    }

    /// <summary>
    /// Core body of Test 1
    /// </summary>
    private static void validateSdtNodesTest1(NodeCollection nodes)
    {
        StructuredDocumentTag testTag = getValidSdt(nodes, 5);
        msAssert.areEqual(SdtType.DATE, testTag.getControlProperties().getType());
        msAssert.isFalse(testTag.getXmlMapping().isEmpty());
        msAssert.areEqual("/root[1]/sale[1]/type[1]", testTag.getXmlMapping().getXPath());
        msAssert.areEqual("{DD7E7B92-2A46-4758-BAF1-556DA4C1931C}", testTag.getXmlMapping().getStoreItemId());
        msAssert.areEqual("", testTag.getXmlMapping().getPrefixMappings());
        msAssert.areEqual("SaleDate", testTag.getTitle());
        msAssert.areEqual("saleDateTag", testTag.getTag());
        msAssert.areEqual(91945806, testTag.getId());
        msAssert.areEqual("DefaultPlaceholder_22675705", testTag.getPlaceholderName());
        msAssert.areEqual("Click here to enter a date.\f", testTag.getPlaceholder().getText());
        msAssert.isTrue(testTag.isShowingPlaceholderText());

        SdtDate date = (SdtDate)testTag.getControlProperties();
        msAssert.areEqual(FormatterPal.xmlToDateTime("2010-07-07T00:00:00Z"), date.getFullDate());
        msAssert.areEqual("M/d/yyyy", date.getDateFormat());
        msAssert.areEqual(LocaleConverter.docxTagToLocale("en-US"), date.getLid());
        msAssert.areEqual(SdtCalendarType.GREGORIAN, date.getCalendarType());

        testTag = getValidSdt(nodes, 9);
        msAssert.areEqual(SdtType.COMBO_BOX, testTag.getControlProperties().getType());
        verifyDropDownListBase(((SdtComboBox)testTag.getControlProperties()).getListItems());

        testTag = getValidSdt(nodes, 12);
        msAssert.areEqual(SdtType.DROP_DOWN_LIST, testTag.getControlProperties().getType());
        verifyDropDownListBase(((SdtDropDownList)testTag.getControlProperties()).getListItems());
    }

    /// <summary>
    /// Verify that we look at an Sdt node and return it.
    /// </summary>
    private static StructuredDocumentTag getValidSdt(NodeCollection nodes, int index)
    {
        msAssert.isTrue(nodes.get(index) instanceof StructuredDocumentTag);
        return (StructuredDocumentTag)nodes.get(index);
    }

    /// <summary>
    /// Verify comboBox and dropDownList.
    /// </summary>
    private static void verifyDropDownListBase(SdtListItemCollection listItemCollection)
    {
        msAssert.areEqual(null, listItemCollection.getSelectedValue());
        msAssert.areEqual(1, listItemCollection.getCount());
        msAssert.areEqual("Choose an item.", listItemCollection.get(0).getValue());

        // andrnosk: MS Word does not write DisplayText for "Choose an item.", we do the same now.
        msAssert.areEqual("", listItemCollection.get(0).getDisplayText());
    }

    /// <summary>
    /// Test that roundtrip of a lot of nested sdts is working and also a check that
    /// flat node collection with skipping sdts is working properly.
    /// </summary>
    @Test
    public void testNestedInlineSdt() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\Markup\\TestNestedInlineSdt.docx");

        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 2, true);
        NodeCollection runs = para.getChildNodes(NodeType.RUN, false);

        // Build a string from start to end to check forward iteration.
        StringBuilder startToBack = new StringBuilder();
        for (int i = 0; i < runs.getCount(); i++)
            msStringBuilder.append(startToBack, runs.get(i).getText());

        // Build a string from end to start to check backward iteration.
        StringBuilder backToStart = new StringBuilder();
        for (int i = runs.getCount() - 1; i >= 0; i--)
            backToStart.insert(0, runs.get(i).getText());

        String expectedString = para.getChildrenText();
        msAssert.areEqual(574, expectedString.length());
        msAssert.areEqual(startToBack.toString(), expectedString);
        msAssert.areEqual(backToStart.toString(), expectedString);
    }

    /// <summary>
    /// Test that a really lot of nested sdts at various levels and flat collections still enumerates nodes properly.
    /// </summary>
    @Test
    public void testNestedVariousSdt() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\Markup\\TestNestedVariousSdt.docx");

        final String ROW_0_TEXT = "AAA\rClick here to enter text.BBB\u0007CCC\u0007\u0007DDD\u0007EEE\u0007\u0007FFF\u0007GGG\u0007";
        final String ROW_1_TEXT = "HHH\u0007III\rJJJ\u0007";
        final String TABLE_0_TEXT = ROW_0_TEXT + "\u0007" + ROW_1_TEXT + "\u0007";

        // Test at the row level.
        Table table0 = doc.getFirstSection().getBody().getTables().get(0);
        msAssert.areEqual(TABLE_0_TEXT, table0.getText());
        checkFlatCollectionText(table0.getRows(), TABLE_0_TEXT);

        // Test at the cell level.
        Row row0 = table0.getFirstRow();
        checkFlatCollectionText(row0.getCells(), ROW_0_TEXT);

        // Test at the block level inside cells.
        Cell cell0 = row0.getFirstCell();
        checkFlatCollectionText(cell0.getParagraphs(), "AAA\rFFF\u0007");
        checkFlatCollectionText(cell0.getTables(), "Click here to enter text.BBB\u0007CCC\u0007\u0007DDD\u0007EEE\u0007\u0007");

        // Test at the inline level in a nested table.
        Cell cellDDD = (Cell)doc.getChild(NodeType.CELL, 3, true);
        msAssert.areEqual("DDD\u0007", cellDDD.getText());
        checkFlatCollectionText(cellDDD.getChildNodes(NodeType.ANY, false), "DDD\u0007");
    }


    /// <summary>
    /// This is just a way to check that iterating over a flat collection and skipping SDT nodes works.
    /// The code loops and appends GetText of each node in the collection and compares with the expected string.
    /// </summary>
    private static void checkFlatCollectionText(NodeCollection nodes, String expectedString)
    {
        // Check from to back enumeration is correct.
        StringBuilder startToBack = new StringBuilder();
        for (int i = 0; i < nodes.getCount(); i++)
            msStringBuilder.append(startToBack, nodes.get(i).getText());

        // Check back to front enumeration is correct.
        StringBuilder backToStart = new StringBuilder();
        for (int i = nodes.getCount() - 1; i >= 0; i--)
            backToStart.insert(0, nodes.get(i).getText());

        msAssert.areEqual(expectedString, startToBack.toString());
        msAssert.areEqual(expectedString, backToStart.toString());
    }


    private static final String TERM_1 = "Term-1", TERM_2 = "Term-2";
    private static final String GUID_1 = "34e9f913-0364-4e37-a4a7-c278ba81f3f9", GUID_2 = "c0fa27e1-9949-4b70-8ba6-122d77d6f0ab";

    /// <summary>
    /// WORDSSP-243 - SharePoint 2013 ListItem Metadata and Word Quickparts not in sync
    /// It is necessary to introduce new logic for Get/Set taxomony SDT fields
    /// </summary>
    @Test
    public void testSdtGetSet() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Markup\\TestSdtGetSet.docx");
        String sdtValue = getSdtValue(doc, "TestColumnTaxHTField0");
        msAssert.areEqual(sdtValue, TERM_1);

        ArrayList<String> guids = new ArrayList<String>(); { guids.add(GUID_2); }
        ArrayList<String> termLabels = new ArrayList<String>(); { termLabels.add(TERM_2); }
        setSdtValue(doc, "TestColumnTaxHTField0", "TestColumn", guids, termLabels);
        SaveOptions so = SaveOptions.createSaveOptions(SaveFormat.DOCX);
        doc = TestUtil.saveOpen(doc, "ImportDocx\\Markup\\TestSdtGetSet.docx", so, false);
        String sdtNewValue = getSdtValue(doc, "TestColumnTaxHTField0");
        msAssert.areEqual(sdtNewValue, TERM_2);
    }

    /// <summary>
    /// WORDSSP-243 - SharePoint 2013 ListItem Metadata and Word Quickparts not in sync.
    /// It is necessary to introduce new logic for Get/Set taxomony SDT fields.
    /// </summary>
    @Test
    public void testSdtGetSetMulti() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Markup\\TestSdtGetSetMulti.docx");
        String sdtValue1 = getSdtValue(doc, "TestColumnTaxHTField0");
        String sdtValue2 = getSdtValue(doc, "TestColumnMultiTaxHTField0");
        msAssert.areEqual(sdtValue1, TERM_1);
        msAssert.areEqual(sdtValue2, TERM_1 + "; " + TERM_2);

        ArrayList<String> termGuids1 = new ArrayList<String>(); { termGuids1.add(GUID_2); }
        ArrayList<String> termLabels1 = new ArrayList<String>(); { termLabels1.add(TERM_2); }
        ArrayList<String> termGuids2 = new ArrayList<String>(); { termGuids2.add(GUID_2); termGuids2.add(GUID_1); }
        ArrayList<String> termLabels2 = new ArrayList<String>(); { termLabels2.add(TERM_2); termLabels2.add(TERM_1); }
        setSdtValue(doc, "TestColumnTaxHTField0", "TestColumn", termGuids1, termLabels1);
        setSdtValue(doc, "TestColumnMultiTaxHTField0", "TestColumnMulti", termGuids2, termLabels2);

        SaveOptions so = SaveOptions.createSaveOptions(SaveFormat.DOCX);
        doc = TestUtil.saveOpen(doc, "ImportDocx\\Markup\\TestSdtGetSetMulti.docx", so, false);
        String sdtValueNew1 = getSdtValue(doc, "TestColumnTaxHTField0");
        String sdtValueNew2 = getSdtValue(doc, "TestColumnMultiTaxHTField0");
        msAssert.areEqual(sdtValueNew1, TERM_2);
        msAssert.areEqual(sdtValueNew2, TERM_2 + "; " + TERM_1);
    }

    // FOSS TestJira16659 removed: it verifies how an SDT flushes relative to an altChunk reference node;
    // the input's altChunk had to be flattened via Word for the FOSS build, which removes that behavior.


    /// <summary>
    /// WORDSNET-18731 DOCX to HtmlFixed conversion issue with inline content control.
    /// Implemented inserting Shape node into inline SDT in DocumentInserter that allows to update bound SDT data properly.
    /// </summary>
    @Test
    public void test18731() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Markup\\Test18731.docx");

        Paragraph para = doc.getFirstSection().getBody().getParagraphs().get(1);
        StructuredDocumentTag sdt = (StructuredDocumentTag)para.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        Shape shape = (Shape)sdt.getFirstChild();

        msAssert.isNotNull(shape);
    }


    /// <summary>
    /// Related with WORDSNET-24520
    /// Checks how AW reads and writes "storeItemChecksum" attribute for different SDT types.
    /// </summary>
    @Test
    public void test24520SdtTypes() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Markup\\Test24520SdtTypes.docx");
        NodeCollection sdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);

        // Check checksum after import.
        for (StructuredDocumentTag sdt : (Iterable<StructuredDocumentTag>) sdts)
             msAssert.areEqual(sdt.getSdtType() == SdtType.RICH_TEXT, StringUtil.hasChars(sdt.getXmlMapping().getStoreItemChecksum()));

        // Check exported markup.
        org.w3c.dom.Document xmlDoc = DocxExportContext.saveAndGetXmlDocument(doc, new OoxmlSaveOptions(), "word/document.xml");
        ArrayList<org.w3c.dom.Node> bindings = new ArrayList<org.w3c.dom.Node>();

        org.w3c.dom.NodeList binding = xmlDoc.getElementsByTagName("w15:dataBinding");
        for (int i= 0; i < binding.getLength(); i++)
            msArrayList.add(bindings, binding.item(i));

        binding = xmlDoc.getElementsByTagName("w:dataBinding");
        for (int i = 0; i < binding.getLength(); i++)
            msArrayList.add(bindings, binding.item(i));

        msAssert.areEqual(10, bindings.size());
        for(org.w3c.dom.Node xmlNode : bindings)
        {
            org.w3c.dom.Attr storeItemChecksum = (org.w3c.dom.Attr)xmlNode.getAttributes().getNamedItem("w16sdtdh:storeItemChecksum");

            // Checksum must be written only for the reach textbox SDT.
            if (xmlNode.getAttributes().getNamedItem("w:xpath").getNodeValue().contains("richtextbox"))
                msAssert.isTrue(StringUtil.hasChars(storeItemChecksum.getNodeValue()));
            else
                msAssert.isNull(storeItemChecksum, "unexpected storeItemChecksum");
        }
    }

    /// <summary>
    /// Related with WORDSNET-24520
    /// Checks that appropriate namespace is writing for the "storeItemChecksum" attribute.
    /// </summary>
    @Test
    public void test24520Namespace() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\Markup\\Test24520Namespace", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        DocxExportContext exporter = new DocxExportContext(doc, "word/document.xml");

        // There are related issues like WORDSNET-23287, WORDSNET-23386 and etc.
        // Which no longer write "storeItemChecksum" attribute. So, check namespace for the rich textbox SDT type.
        org.w3c.dom.Node docNode = exporter.getXmlNode("//w:document");
        msAssert.isNotNull(docNode.getAttributes().getNamedItem("xmlns:w16sdtdh"));
    }

    /// <summary>
    /// Get text value of SDT field with a given tag name.
    /// </summary>
    /// <returns>Text value (or null if SDT field with given tag name was not found)</returns>
    private static String getSdtValue(Document doc, String tagName)
    {
        NodeCollection nodes = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        for (Node node : (Iterable<Node>) nodes)
        {
            StructuredDocumentTag sdtNode = (StructuredDocumentTag)node;
            if (!sdtNode.getTag().equals(tagName))
                continue;

            Node sdtContentNode = sdtNode.getChild(NodeType.RUN, 0, true);
            if (sdtContentNode == null)
                return null;

            Run run = (Run)sdtContentNode;
            return run.getText();
        }
        return null;
    }

    /// <summary>
    /// Set text value for SDT field with a given tag/alias name.
    /// </summary>
    /// <returns>1 - text value was set, -1 - SDT field with given tag name was not found</returns>
    @Test (enabled = false)
    public static int setSdtValue(Document doc, String tagName, String aliasName,
        ArrayList<String> termGuids, ArrayList<String> termLabels) throws Exception
    {
        NodeCollection nodes = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        for (Node node : (Iterable<Node>) nodes)
        {
            StructuredDocumentTag sdtNode = (StructuredDocumentTag)node;
            if (!sdtNode.getTag().equals(tagName))
                continue;

            Node sdtContentNode = sdtNode.getChild(NodeType.RUN, 0, true);
            if (sdtContentNode == null)
                return -2;

            // Update XmlMapping
            if (!sdtNode.getXmlMapping().isEmpty())
                sdtNode.getXmlMapping().setValues(termLabels);

            // Update run text
            Run run = (Run)sdtContentNode;
            run.setText(concatenateTermLabels(termLabels));

            // Update custom properties as well
            doc.getCustomDocumentProperties().get(aliasName).setValue(concatenateTermValues(termGuids, termLabels));

            return 1;
        }
        return -1;
    }

    private static String concatenateTermLabels(ArrayList<String> termLabels)
    {
        String result = "";
        int length = termLabels.size();
        for (int i = 0; i < length; i++)
        {
            result = msString.plusEqOperator(result, termLabels.get(i));
            if (i != length - 1)
                result = msString.plusEqOperator(result, "; ");
        }
        return result;
    }

    private static String concatenateTermValues(ArrayList<String> termGuids, ArrayList<String> termLabels)
    {
        String result = "";
        int length = termGuids.size();
        for (int i = 0; i < length; i++)
        {
            result = msString.plusEqOperator(result, (i + 1) + ";#" + termLabels.get(i) + "|" + termGuids.get(i));
            if (i != length - 1)
                result = msString.plusEqOperator(result, ";#");
        }
        return result;
    }


    /// <summary>
    /// Title names for testing sdt nodes in the <see cref="TestSdtFullCollection"/>
    /// </summary>
    private static /*final*/ String[] G_ALIASES_FOR_TEST_SDT_FULL_COLLECTION = new String[]
        {
            // first page header
            "", // empty alias for docPartObj
            "Account Number",
            "Statement Date",

            // first page content
            "", // empty alias for group obj
            "Client Contact",
            "Client Name",
            "Address 1",
            "AddressLine2",
            "AddressLine3",
            "AddressLine4",
            "AddressLine5",
            "Postcode",
            "Title",
            "Previous Balance",
            "Payments Received",
            "Invoice Number",
            "Invoice Ammount",
            "Invoice Adjustments",
            "DiscountPercentage",
            "DiscountAmount",
            "Outstanding Balance",
            "Payment Due Date",
            "Remittance Account Number",
            "Remittance Invoice Number",
            "Remittance Invoice Date",
            "Remittance Invoice Amount Due ",
            "CHNumber",

            // second page header Sdt
            "", // docpart
            "", // group
            "Invoice Date",
            "Account Number",
            "Invoice Number",
            "", // one more group
            "Customer Name",
            "Address 1",
            "Address 2",
            "Address 3",
            "Address 4",
            "Address 5",
            "Postcode",

            // second page content
            "Trasaction Order Number",
            "Transaction Order Date",
            "Transaction Description",
            "Transaction Customer Reference",
            "Transaction Company Number",
            "Transaction Company Name",
            "Transaction Document Date",
            "Transaction Document Type",
            "Transaction Cost",
            "TotalTransactions"
        };
}

