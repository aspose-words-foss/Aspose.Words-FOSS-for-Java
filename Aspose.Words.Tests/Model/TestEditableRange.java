// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/06/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import java.util.ArrayList;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


/// <summary>
/// Basic EditableRange functionality tests.
/// </summary>
@Test
public class TestEditableRange
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
    /// Test editable ranges located inside SDT.
    /// </summary>
    @Test
    public void editableRangeInsideSdt() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\EditableRange\\EditableRangeInsideSdt.docx");

        ArrayList<EditableRangeStart> starts =
            doc.getChildNodes(NodeType.EDITABLE_RANGE_START, true).<EditableRangeStart>toList();
        ArrayList<EditableRangeEnd> ends =
            doc.getChildNodes(NodeType.EDITABLE_RANGE_END, true).<EditableRangeEnd>toList();
        msAssert.areEqual(3, starts.size());
        msAssert.areEqual(3, ends.size());

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, -1, true);
        msAssert.areSame(sdt, starts.get(2).getParentNode());
        msAssert.areSame(sdt, ends.get(2).getParentNode());

        msAssert.areEqual(DisplacedByType.PREV, starts.get(2).getDisplacedBy());
        msAssert.areEqual(DisplacedByType.NEXT, ends.get(2).getDisplacedBy());
    }

    /// <summary>
    /// Test editable ranges located inside Footnote.
    /// According to MS Word behavior, there is no way to insert editable ranges inside Footnote.
    /// But according to specification ISO29500-1 p.17.13.7.2 it is possible.
    /// So I have created this test document manually.
    /// </summary>
    @Test
    public void editableRangeInsideFootnote() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\EditableRange\\EditableRangeInsideFootnote", UnifiedScenario.DOCX_2_DOCX);
        msAssert.areEqual(1, doc.getChildNodes(NodeType.EDITABLE_RANGE_START, true).getCount());
        msAssert.areEqual(1, doc.getChildNodes(NodeType.EDITABLE_RANGE_END, true).getCount());
    }



    /// <summary>
    /// WORDSNET-9000 Add an ability to define new EditableRanges in API.
    /// </summary>
    @Test
    public void testEditableRangeStartEnd() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        EditableRangeStart start = builder.startEditableRange();
        builder.writeln("Some Text Inside EditableRange");
        builder.writeln("Some Text Inside EditableRange");
        builder.endEditableRange(start);

        builder.writeln("Some Text outside EditableRange");
        builder.writeln("Some Text outside EditableRange");

        start.getEditableRange().setEditorGroup(EditorType.EVERYONE);
        doc.protect(ProtectionType.READ_ONLY, "password");

        doc = TestUtil.saveOpen(doc, "Model\\EditableRange\\TestEditableRangeStartEnd.docx", new OoxmlSaveOptions(SaveFormat.DOCX));

        NodeCollection starts = doc.getChildNodes(NodeType.EDITABLE_RANGE_START, true);
        NodeCollection ends = doc.getChildNodes(NodeType.EDITABLE_RANGE_END, true);

        // Check editable range are present.
        msAssert.areEqual(1, starts.getCount());
        msAssert.areEqual(1, ends.getCount());
    }

    @Test
    public void testEditableRangeWithoutEnd() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        EditableRangeStart start = builder.startEditableRange();

        builder.writeln("Some Text Inside EditableRange");

        start.getEditableRange().setEditorGroup(EditorType.EVERYONE);
        doc.protect(ProtectionType.READ_ONLY, "4444");

        DocumentValidator validator = new DocumentValidator();
        validator.execute(new SaveInfo(doc, null, null, new OoxmlSaveOptions()));

        NodeCollection starts = doc.getChildNodes(NodeType.EDITABLE_RANGE_START, true);
        NodeCollection ends = doc.getChildNodes(NodeType.EDITABLE_RANGE_END, true);

        // Check editable start was removed by validator.
        msAssert.areEqual(0, starts.getCount());
        msAssert.areEqual(0, ends.getCount());
    }

    @Test
    public void testEditableRangeInDifferentSections() throws Exception
    {
        Document doc = TestUtil.open("Model\\EditableRange\\TestJira9000.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        EditableRangeStart start = builder.startEditableRange();
        builder.writeln("Some Text Inside EditableRange");

        builder.insertImage(TestUtil.buildTestFileName("DocBuilder\\Image\\TestPng.png"));

        builder.moveTo(doc.getLastSection().getBody().getFirstParagraph(), 0);
        builder.endEditableRange(start);
        start.getEditableRange().setEditorGroup(EditorType.EVERYONE);
        doc.protect(ProtectionType.READ_ONLY, "4444");
        doc = TestUtil.saveOpen(doc, "Model\\EditableRange\\TestEditableRangeInDifferentSections.docx", new OoxmlSaveOptions(SaveFormat.DOCX));

        NodeCollection starts = doc.getChildNodes(NodeType.EDITABLE_RANGE_START, true);
        NodeCollection ends = doc.getChildNodes(NodeType.EDITABLE_RANGE_END, true);

        // Check editable range are present.
        msAssert.areEqual(1, starts.getCount());
        msAssert.areEqual(1, ends.getCount());
    }

    @Test
    public void testEditableRangeInTable() throws Exception
    {
        Document doc = TestUtil.open("Model\\EditableRange\\TestJira9000.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table = doc.getLastSection().getBody().getTables().get(0);
        builder.moveTo(table.getRows().get(0).getCells().get(2).getFirstParagraph(), 0);

        EditableRangeStart start = builder.startEditableRange();

        builder.writeln("EditableRange");

        builder.moveTo(table.getRows().get(2).getCells().get(4).getFirstParagraph(), 0);
        builder.endEditableRange(start);

        DocumentValidator validator = new DocumentValidator();
        validator.execute(new SaveInfo(doc, null, null, new OoxmlSaveOptions()));

        table = doc.getLastSection().getBody().getTables().get(0);
        NodeCollection starts = table.getRows().get(0).getCells().get(2).getChildNodes(NodeType.EDITABLE_RANGE_START, true);
        NodeCollection ends = table.getRows().get(2).getCells().get(4).getChildNodes(NodeType.EDITABLE_RANGE_END, true);

        // Check editable range are present.
        msAssert.areEqual(1, starts.getCount());
        msAssert.areEqual(1, ends.getCount());
    }

    @Test
    public void testEditableRangeNested() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // [ {} ] Range inside range.
        EditableRangeStart startRange1 = builder.startEditableRange();
        builder.writeln("EditableRange1");

        EditableRangeStart startRange2 = builder.startEditableRange();
        builder.writeln("EditableRange2");
        builder.endEditableRange(startRange2);

        builder.writeln("EditableRange1");
        builder.endEditableRange(startRange1);

        builder.writeln("Simple Text Separator");

        // [ {] } Overlaped ranges.
        EditableRangeStart startRange3 = builder.startEditableRange();
        builder.writeln("EditableRange3");

        EditableRangeStart startRange4 = builder.startEditableRange();
        builder.writeln("EditableRange4");

        builder.endEditableRange(startRange3);

        builder.writeln("EditableRange4");
        builder.endEditableRange(startRange4);

        startRange1.getEditableRange().setEditorGroup(EditorType.EVERYONE);
        startRange2.getEditableRange().setEditorGroup(EditorType.EDITORS);

        doc.protect(ProtectionType.READ_ONLY, "4444");

        doc = TestUtil.saveOpen(doc, "Model\\EditableRange\\TestEditableRangeNested.docx", new OoxmlSaveOptions(SaveFormat.DOCX));

        NodeCollection starts = doc.getChildNodes(NodeType.EDITABLE_RANGE_START, true);
        NodeCollection ends = doc.getChildNodes(NodeType.EDITABLE_RANGE_END, true);

        // Check editable start was removed by validator.
        msAssert.areEqual(4, starts.getCount());
        msAssert.areEqual(4, ends.getCount());
    }

    @Test
    public void testEditableRangeEndWithoutParam() throws Exception
    {
        final String TEXT_INSIDE_RANGE = "Some Text inside EditableRange";
        final String TEXT_OUTSIDE_RANGE = "Some Text outside EditableRange";

        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.startEditableRange();
        builder.writeln(TEXT_INSIDE_RANGE);
        builder.endEditableRange();

        builder.writeln(TEXT_OUTSIDE_RANGE);

        builder.startEditableRange();
        builder.writeln(TEXT_INSIDE_RANGE);
        builder.endEditableRange();

        builder.writeln(TEXT_OUTSIDE_RANGE);

        builder.startEditableRange();
        builder.writeln(TEXT_INSIDE_RANGE);
        builder.endEditableRange();

        builder.writeln(TEXT_OUTSIDE_RANGE);

        builder.startEditableRange();
        builder.writeln(TEXT_INSIDE_RANGE);
        builder.endEditableRange();

        builder.writeln(TEXT_OUTSIDE_RANGE);

        doc = TestUtil.saveOpen(doc, "Model\\EditableRange\\TestEditableRangeEndWithoutParam.docx", new OoxmlSaveOptions(SaveFormat.DOCX));

        NodeCollection starts = doc.getChildNodes(NodeType.EDITABLE_RANGE_START, true);
        NodeCollection ends = doc.getChildNodes(NodeType.EDITABLE_RANGE_END, true);

        // Check editable range are present.
        msAssert.areEqual(4, starts.getCount());
        msAssert.areEqual(4, ends.getCount());
    }

    @Test (expectedExceptions = IllegalStateException.class)
    public void testEditableRangeWithoutStart() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.endEditableRange();
    }


    /// <summary>
    /// WORDSNET-18124 FileCorruptedException was thrown on loading created RTF.
    /// Fixed a string constant for the case, when SingleUser is empty and EditorGroup is not specified.
    /// </summary>
    @Test
    public void testJira18124() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveTo(doc.getSections().get(0).getBody().getFirstParagraph());
        builder.startEditableRange();
        builder.insertNode(new Run(doc));
        builder.endEditableRange();

        doc.protect(ProtectionType.READ_ONLY, "somePassword");

        // Verify no exception on document open.
        // FOSS: Rtf writer removed; verify the EditableRange roundtrips through Docx instead.
        doc = TestUtil.saveOpen(doc, "ExportRtf\\TestJira18124.docx", null, false);

        // Verify correct import of EditableRange's SingleUser and EditorGroup.
        // FOSS: swapped Rtf->Docx; Docx preserves an unspecified editor group as Unspecified
        // (the removed Rtf writer instead defaulted it to None).
        EditableRangeStart start = (EditableRangeStart)doc.getChild(NodeType.EDITABLE_RANGE_START, 0, true);
        msAssert.areEqual("", start.getSingleUser());
        msAssert.areEqual(EditorType.UNSPECIFIED, start.getEditorGroup());
    }

    /// <summary>
    /// WORDSNET-18924 EditableRange seems not working when saving to DOC format.
    /// Wrong default value of the <see cref="EditorType"/>. Fixed default value.
    /// </summary>
    @Test (dataProvider = "test18924DataProvider")
    public void test18924(/*EditorType*/int editorType) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Document doc = builder.getDocument();
        Paragraph para = builder.insertParagraph();

        Run run = new Run(doc);
        run.setText("Test");
        para.appendChild(run);
        EditableRangeStart start = builder.startEditableRange();
        para.insertBefore(start, run);
        para.insertAfter(builder.endEditableRange(), run);

        doc.protect(ProtectionType.READ_ONLY);

        msAssert.areEqual("", start.getSingleUser());
        msAssert.areEqual(EditorType.UNSPECIFIED, start.getEditorGroup());

        start.setEditorGroup(editorType);

        // FOSS: Doc writer removed; roundtrip through Docx (no committed .docx gold).
        doc = TestUtil.saveOpen(doc, "Model\\EditableRange\\Test18924.docx", (SaveOptions)null, false);
        start = (EditableRangeStart)doc.getChild(NodeType.EDITABLE_RANGE_START, 0, true);

        msAssert.areEqual("", start.getSingleUser());
        msAssert.areEqual(editorType, start.getEditorGroup());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test18924DataProvider")
	public static Object[][] test18924DataProvider() throws Exception
	{
		return new Object[][]
		{
			{EditorType.NONE},
			{EditorType.CURRENT},
			{EditorType.EDITORS},
			{EditorType.OWNERS},
			{EditorType.CONTRIBUTORS},
			{EditorType.ADMINISTRATORS},
			{EditorType.EVERYONE},
		};
	}

}

