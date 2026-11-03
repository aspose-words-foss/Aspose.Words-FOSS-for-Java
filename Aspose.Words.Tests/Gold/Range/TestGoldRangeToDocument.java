// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/09/2012 by Alexey Butalov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.Debug;


/// <summary>
/// Gold tests of public ToDocument() method of the Range class.
/// It tests ranges attached to a single node only.
/// </summary>
@Test
public class TestGoldRangeToDocument extends TestGoldBase
{
    @Test
    public void testDocumentToDocument() throws Exception
    {
        final String DOC_FILE_NAME = "Document\\TestSimple.docx";
        Document doc = open(TestUtil.getInModelPath(DOC_FILE_NAME));

        verifyNodeRangeToDocument(DOC_FILE_NAME, "Document", doc);
    }

    @Test
    public void testParagraphToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Document\\TestSimple.docx", "Paragraph", NodeType.PARAGRAPH, 2);
    }

    @Test
    public void testRunToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Document\\TestSimple.docx", "Run", NodeType.RUN, 7);
    }

    @Test
    public void testRunWithStyleToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Document\\TestSimple.docx", "RunWithStyle", NodeType.RUN);
    }

    @Test
    public void testBookmarkToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Bookmark\\TestBookmarks.docx", "Bookmark", NodeType.BOOKMARK_START);
    }


    @Test
    public void testTableToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Table\\TestSimple.docx", "Table", NodeType.TABLE);
    }

    @Test
    public void testRowToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Table\\TestSimple.docx", "Row", NodeType.ROW, 1);
    }

    @Test
    public void testCellToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Table\\TestSimple.docx", "Cell", NodeType.CELL);
    }

    @Test
    public void testSectionToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Section\\TestSections.docx", "Section", NodeType.SECTION, 1);
    }

    @Test
    public void testBodyToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Stories\\TestBody.docx", "Body", NodeType.BODY);
    }

    @Test
    public void testFootnoteToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Stories\\TestBodyFootnoteEndnote.docx", "Footnote", NodeType.FOOTNOTE);
    }

    @Test
    public void testHeaderToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Stories\\TestBodyHeaderFooter.docx", "Header", NodeType.HEADER_FOOTER);
    }

    @Test
    public void testFooterToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Stories\\TestBodyHeaderFooter.docx", "Footer", NodeType.HEADER_FOOTER, 1);
    }

    @Test
    public void testImageToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Shape\\Image\\TestImageInline.docx", "Shape", NodeType.SHAPE);
    }

    @Test
    public void testFieldStartToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Field\\TestFields.docx", "FieldStart", NodeType.FIELD_START);
    }

    @Test
    public void testFieldEndToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Field\\TestFields.docx", "FieldEnd", NodeType.FIELD_END);
    }

    @Test
    public void testFormFieldTextInputToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Field\\TestFields.docx", "FormFieldTextInput", NodeType.FORM_FIELD);
    }

    @Test
    public void testFormFieldCheckBoxToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Field\\TestFields.docx", "FormFieldCheckBox", NodeType.FORM_FIELD, 1);
    }

    @Test
    public void testFormFieldDropDownToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Field\\TestFields.docx", "FormFieldDropDown", NodeType.FORM_FIELD, 2);
    }


    @Test
    public void testSdtParagraphToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Markup\\TestSdtPlaceholders.docx", "SDTPara", "0.0.0.0.0");
    }

    @Test
    public void testSdtShapeToDocument() throws Exception
    {
        verifyNodeRangeToDocument("Markup\\TestSdtPlaceholders.docx", "SDTShape", NodeType.STRUCTURED_DOCUMENT_TAG, 3);
    }

    @Test
    public void testDrawingMLToDocument() throws Exception
    {
        verifyNodeRangeToDocument("DrawingML\\TestImageData.docx", "DrawingMLImage", NodeType.SHAPE);
    }


    @Test
    public void testBuildingBlockToDocument() throws Exception
    {
        final String DOC_FILE_NAME = "BuildingBlocks\\TestBuildingBlocks.dotx";
        Document doc = open(DOC_FILE_NAME);
        Debug.assertTrue(doc.getGlossaryDocument() != null);
        Debug.assertTrue(doc.getGlossaryDocument().getFirstBuildingBlock() != null);
        verifyNodeRangeToDocument(DOC_FILE_NAME, "BuildingBlock", doc.getGlossaryDocument().getFirstBuildingBlock());
    }

    @Test
    public void testGlossaryDocumentRunToDocument() throws Exception
    {
        final String DOC_FILE_NAME = "BuildingBlocks\\TestBuildingBlocks.dotx";
        Document doc = open(DOC_FILE_NAME);
        Node node = doc.getGlossaryDocument().getChild(NodeType.RUN, 0, true);
        Debug.assertTrue(node != null);
        verifyNodeRangeToDocument(DOC_FILE_NAME, "GlossaryDocumentRun", node);
    }

    @Test
    public void testGlossaryDocumentToDocument() throws Exception
    {
        final String DOC_FILE_NAME = "BuildingBlocks\\TestBuildingBlocks.dotx";
        Document doc = open(DOC_FILE_NAME);
        verifyNodeRangeToDocument(DOC_FILE_NAME, "GlossaryDocument", doc.getGlossaryDocument());
    }

    /// <summary>
    /// This is a standard test for (<see cref="Range.ToDocument()"></see>) with compare against a gold file.
    /// It tests ranges attached to a single node.
    /// It creates a document from a file, finds node of a specified type, builds a new document from this node,
    /// saves this document into the Docx format and verifies against a gold file.
    /// </summary>
    /// <param name="docFileName">Name of a original file, relative to the TestData\Model folder.</param>
    /// <param name="suffix">File name suffix for a output file.</param>
    /// <param name="nodeType">Specifies type of a node to test a range based on it.</param>
    /// <param name="index">Zero based index of a node to select.
    /// Negative indexes are also allowed and indicate access from the end,
    /// that is -1 means the last node.</param>
    private static void verifyNodeRangeToDocument(String docFileName,
        String suffix,
        /*NodeType*/int nodeType,
        int index) throws Exception
    {
        Document doc = open(TestUtil.getInModelPath(docFileName));
        Node node = doc.getChild(nodeType, index, true);
        Debug.assertTrue(node != null);
        verifyNodeRangeToDocument(docFileName, suffix, node);
    }

    /// <summary>
    /// This is a standard test for (<see cref="Range.ToDocument()"></see>) with compare against a gold file.
    /// It tests ranges attached to a single node.
    /// It creates a document from a file, finds node of a specified type, builds a new document from this node,
    /// saves this document into the Docx format and verifies against a gold file.
    /// </summary>
    /// <param name="docFileName">Name of a original file, relative to the TestData\Model folder.</param>
    /// <param name="suffix">File name suffix for a output file.</param>
    /// <param name="nodeType">Specifies type of a node to test a range based on it.</param>
    private static void verifyNodeRangeToDocument(String docFileName,
        String suffix,
        /*NodeType*/int nodeType) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        if (nodeType == NodeType.OFFICE_MATH)
            lo.setConvertShapeToOfficeMath(true);

        Document doc = open(TestUtil.getInModelPath(docFileName), lo);
        Node node = doc.getChild(nodeType, 0, true);
        Debug.assertTrue(node != null);
        verifyNodeRangeToDocument(docFileName, suffix, node);
    }

    /// <summary>
    /// This is a standard test for (<see cref="Range.ToDocument()"></see>) with compare against a gold file.
    /// It tests ranges attached to a single node.
    /// It creates a document from a file, finds node of a specified type, builds a new document from this node,
    /// saves this document into the Docx format and verifies against a gold file.
    /// </summary>
    /// <param name="docFileName">Name of a original file, relative to the TestData\Model folder.</param>
    /// <param name="suffix">File name suffix for a output file.</param>
    /// <param name="id">Node string id</param>
    private static void verifyNodeRangeToDocument(String docFileName, String suffix, String id) throws Exception
    {
        Document doc = open(TestUtil.getInModelPath(docFileName));
        Node node = doc.getNodeById(id);
        Debug.assertTrue(node != null);
        verifyNodeRangeToDocument(docFileName, suffix, node);
    }

    /// <summary>
    /// This is a standard test for (<see cref="Range.ToDocument()"></see>) with compare against a gold file.
    /// It tests ranges attached to a single node.
    /// It builds a document from a node, saves this document into the Docx format and verifies against a gold file.
    /// </summary>
    /// <param name="docFileName">Name of a original file, relative to the TestData\Model folder.</param>
    /// <param name="suffix">File name suffix for a output file.</param>
    /// <param name="node">Node to test.</param>
    private static void verifyNodeRangeToDocument(String docFileName, String suffix, Node node) throws Exception
    {
        docFileName = TestUtil.getInModelPath(docFileName);
        suffix = " NodeToDoc " + suffix;
        String outFileName = TestUtil.buildOutFileName(docFileName, suffix, SaveFormat.DOCX);
        TestUtil.ensureDirectoryForFileExists(outFileName);

        Document doc = node.getRange().toDocument();
        OoxmlSaveOptions saveOptions = new OoxmlSaveOptions();
        saveOptions.setTestMode();
        doc.save(outFileName, saveOptions);

        String goldFileName = TestUtil.buildGoldFileName(docFileName, suffix, SaveFormat.DOCX);
        TestUtil.verifyGold(docFileName, outFileName, goldFileName, "", saveOptions, false);
    }
}

