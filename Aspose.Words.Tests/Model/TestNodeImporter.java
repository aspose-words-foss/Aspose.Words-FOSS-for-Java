// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/04/2016 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.XmlUtilPal;
import java.util.Map;
import java.util.HashMap;
import com.aspose.Language;
import com.aspose.ms.System.IO.Path;
import com.aspose.DrColor;
import org.testng.annotations.DataProvider;


@Test
public class TestNodeImporter
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
    /// WORDSNET-13302 DocumentBuilder.InsertDocument changes the font size of list label in Docx.
    /// Should remove paragraph break properties equal to its style properties.
    /// </summary>
    @Test
    public void testJira13302() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());
        // Customer's scenario.
        Style normal = builder.getDocument().getStyles().get("Normal");
        normal.getFont().setName("Verdana");
        normal.getFont().setSize(8.0);

        Document importedDoc = TestUtil.open("Model\\Nodes\\TestJira13302.docx");
        builder.insertDocument(importedDoc, ImportFormatMode.USE_DESTINATION_STYLES);

        Paragraph para = builder.getDocument().getFirstSection().getBody().getParagraphs().get(1);
        msAssert.isNull(para.getParagraphBreakRunPr().get(FontAttr.SIZE));
        msAssert.areEqual(8, para.getParagraphBreakFont().getSize());
    }





    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that ALL nodes are expanded, even if attributes are different only in a some single node.
    /// </summary>
    @Test
    public void testJira14587CheckNeedExpandAll() throws Exception
    {
        // The styles in source and destination documents are completely equal.
        Document dstDoc = appendDocuments("TestJira14587AllStylesDst.xml", "TestJira14587AllStylesSrc.xml");
        checkAllStylesPreserved(dstDoc.getFirstSection().getBody().getParagraphs());

        // This source file differs from the above source file only in one property in CharacterStyle.
        dstDoc = appendDocuments("TestJira14587AllStylesDst.xml", "TestJira14587AllStylesSrcOneStyleDiff.xml");
        checkAllStylesExpanded(dstDoc.getFirstSection().getBody().getParagraphs());

        // This source file is the same as previous, but different attribute in CharacterStyle is overridden
        // with the direct attribute in runs. So, expanded attributes are equal in source and destination files.
        // Despite on that, Word expands styles. It seems, Word always expand styles, if at least one of them
        // is different in source and destination documents.
        dstDoc = appendDocuments("TestJira14587AllStylesDst.xml", "TestJira14587AllStylesSrcOneStyleDiff2.xml");
        checkAllStylesExpanded(dstDoc.getFirstSection().getBody().getParagraphs());
    }


    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that styles are not expanded inside header when import whole document.
    /// </summary>
    @Test
    public void testJira14587Header() throws Exception
    {
        // The custom style ParagraphStyle is different in source and destination documents.
        // Source document has header with exactly the same content as in body.
        Document dstDoc = appendDocuments("TestJira14587AllStylesDst.xml", "TestJira14587AllStylesWithHeaderSrc.xml");

        // Check styles are expanded in body,
        msAssert.isFalse(dstDoc.getFirstSection().getBody().getFirstParagraph().getParaPr().contains(ParaAttr.ISTD));
        // but are not expanded in header.
        checkAllStylesPreserved(dstDoc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getParagraphs());
    }

    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that styles are expanded inside header when import not whole document.
    /// </summary>
    @Test
    public void testJira14587HeaderSingleNode() throws Exception
    {
        Document dstDoc = TestUtil.open("Model\\Nodes\\TestJira14587AllStylesDst.xml");
        Document srcDoc = TestUtil.open("Model\\Nodes\\TestJira14587AllStylesWithHeaderSrc.xml");

        NodeImporter nodeImporter = new NodeImporter(srcDoc, dstDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        // Import paragraph from header.
        HeaderFooter header = srcDoc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);
        Paragraph para = (Paragraph)nodeImporter.importNode(header.getLastParagraph(), true);
        msAssert.areEqual(2100, para.getParaPr().get(ParaAttr.LEFT_INDENT));
        msAssert.areEqual(28, para.getFirstRun().getRunPr().get(FontAttr.SIZE));
        msAssert.isFalse(para.getParaPr().contains(ParaAttr.ISTD));
    }

    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that styles are not expanded inside footnote when import whole document.
    /// </summary>
    @Test
    public void testJira14587Footnote() throws Exception
    {
        // Source document has footnote with style FootnoteReference different from those in destination document.
        Document dstDoc = appendDocuments("TestJira14587AllStylesExDst.xml", "TestJira14587FootnoteSrc.xml");

        Body body = dstDoc.getFirstSection().getBody();
        // Check styles are expanded in body,
        msAssert.isFalse(body.getFirstParagraph().getParaPr().contains(ParaAttr.ISTD));
        // but are not expanded in footnote.
        Footnote footnote = (Footnote)body.getLastParagraph().getLastChild();
        msAssert.isTrue(footnote.getFirstParagraph().getParaPr().contains(ParaAttr.ISTD));
    }

    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that styles are expanded inside footnote when import some single node.
    /// </summary>
    @Test
    public void testJira14587FootnoteSingleNodeA() throws Exception
    {
        Document dstDoc = TestUtil.open("Model\\Nodes\\TestJira14587AllStylesExDst.xml");
        Document srcDoc = TestUtil.open("Model\\Nodes\\TestJira14587FootnoteSrc.xml");

        NodeImporter nodeImporter = new NodeImporter(srcDoc, dstDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        Footnote footnote = (Footnote)srcDoc.getChild(NodeType.FOOTNOTE, 0, true);

        // Import footnote reference.
        Node newNode = nodeImporter.importNode(footnote.getFirstParagraph().getFirstChild(), false);
        msAssert.areEqual(30, ((SpecialChar)newNode).getRunPr().get(FontAttr.SIZE));

        // Import run from footnote.
        newNode = nodeImporter.importNode(footnote.getFirstParagraph().getFirstRun(), false);
        msAssert.isFalse(((Run)newNode).getRunPr().contains(FontAttr.SIZE));
        msAssert.isFalse(((Run)newNode).getRunPr().contains(FontAttr.SIZE_BI));
    }


    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that styles are not expanded inside comment when import whole document.
    /// </summary>
    @Test
    public void testJira14587Comment() throws Exception
    {
        // Source document has comment with style CommentReference different from those in destination document.
        Document dstDoc = appendDocuments("TestJira14587AllStylesExDst.xml", "TestJira14587CommentSrc.xml");

        Body body = dstDoc.getFirstSection().getBody();
        // Check styles are expanded in body,
        msAssert.isFalse(body.getFirstParagraph().getParaPr().contains(ParaAttr.ISTD));
        // but are not expanded in comment.
        Comment comment = (Comment)body.getLastParagraph().getLastChild();
        msAssert.isTrue(comment.getFirstParagraph().getParaPr().contains(ParaAttr.ISTD));
    }

    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that styles are expanded inside comment when import not whole document.
    /// </summary>
    @Test
    public void testJira14587CommentSingleNode() throws Exception
    {
        Document dstDoc = TestUtil.open("Model\\Nodes\\TestJira14587AllStylesExDst.xml");
        Document srcDoc = TestUtil.open("Model\\Nodes\\TestJira14587CommentSrc.xml");

        NodeImporter nodeImporter = new NodeImporter(srcDoc, dstDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        Comment comment = (Comment)srcDoc.getChild(NodeType.COMMENT, 0, true);

        // Import paragraph from comment.
        Paragraph para = (Paragraph)nodeImporter.importNode(comment.getFirstParagraph(), true);
        msAssert.areEqual(2000, para.getParaPr().get(ParaAttr.LEFT_INDENT));
        msAssert.areEqual(AttrBoolEx.TRUE, para.getParagraphBreakRunPr().get(FontAttr.BOLD));
        Run run = para.getFirstRun();
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().get(FontAttr.BOLD));
        msAssert.isFalse(run.getRunPr().contains(FontAttr.SIZE));

        // Import run from comment (without parent paragraph).
        run = (Run)nodeImporter.importNode(comment.getFirstParagraph().getFirstRun(), false);
        msAssert.isFalse(run.getRunPr().contains(FontAttr.SIZE));
    }

    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that styles are not expanded inside shape when import whole document.
    /// </summary>
    @Test
    public void testJira14587Shape() throws Exception
    {
        // The style ParagraphStyle is different in source and destination documents.
        Document dstDoc = appendDocuments("TestJira14587AllStylesDst.xml", "TestJira14587ShapeSrc.xml");

        Paragraph para = dstDoc.getFirstSection().getBody().getFirstParagraph();
        // Check styles are expanded outside of shape,
        msAssert.isFalse(para.getParaPr().contains(ParaAttr.ISTD));
        // but are not expanded inside it.
        Shape shape = (Shape)para.getFirstChild();
        msAssert.isTrue(shape.getFirstParagraph().getParaPr().contains(ParaAttr.ISTD));
    }

    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that styles are expanded inside shape when import not whole document.
    /// </summary>
    @Test
    public void testJira14587ShapeSingleNode() throws Exception
    {
        Document dstDoc = TestUtil.open("Model\\Nodes\\TestJira14587AllStylesExDst.xml");
        Document srcDoc = TestUtil.open("Model\\Nodes\\TestJira14587ShapeSrc.xml");

        NodeImporter nodeImporter = new NodeImporter(srcDoc, dstDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        Shape shape = (Shape)srcDoc.getChild(NodeType.SHAPE, 0, true);

        // Import paragraph from shape.
        Paragraph para = (Paragraph)nodeImporter.importNode(shape.getFirstParagraph(), true);
        msAssert.areEqual(2100, para.getParaPr().get(ParaAttr.LEFT_INDENT));
        msAssert.areEqual(2, para.getParaPr().getCount()); //LeftIndent and Rsid.
    }


    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that paragraph with BuiltIn style in original revision is not expanded.
    /// </summary>
    @Test
    public void testJira14587ParaBuiltinStyleRevision() throws Exception
    {
        Document dstDoc = appendDocuments(null, "TestJira14587ParaBuiltinStyleRevision.docx");

        // If BuiltIn style does not exist in destination document, then it should be created from scratch (not imported!).
        // Run has no revisions, so in this case it is considered as 'final' revision
        // and should be expanded with corresponding flag in NodeImporter.
        Paragraph para = dstDoc.getFirstSection().getBody().getFirstParagraph();
        checkTestJira14587ParaStyleRevision(para);

        ParaPr paraPr = para.getParaPr();
        msAssert.areEqual(2, paraPr.get(ParaAttr.ISTD)); // Heading2
    }

    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that paragraph with custom style in original revision is not expanded.
    /// </summary>
    @Test
    public void testJira14587ParaUserDefinedStyleRevision() throws Exception
    {
        Document dstDoc = appendDocuments(null, "TestJira14587ParaUserDefinedStyleRevision.docx");

        // If custom style does not exist in destination document, then it should be imported.
        // Run has no revisions, so in this case it is considered as 'final' revision
        // and should be expanded with corresponding flag in NodeImporter.
        Paragraph para = dstDoc.getFirstSection().getBody().getFirstParagraph();
        checkTestJira14587ParaStyleRevision(para);

        ParaPr paraPr = para.getParaPr();
        msAssert.areEqual(15, paraPr.get(ParaAttr.ISTD)); // Style1
    }


    /// <summary>
    /// Relates to WORDSNET-14587
    /// Source document has paragraph with custom style, which does not exist inside destination document.
    /// In that case Word does not calculate difference for direct paragraph attributes.
    /// Instead, it imports style with calculated difference and does not remove this style from the paragraph.
    /// Test checks this case.
    /// </summary>
    @Test
    public void testJira14587NonExistentCustomStyle() throws Exception
    {
        Document dstDoc = appendDocuments(null, "TestJira14587NonExistentCustomStyle.docx");

        Paragraph para = dstDoc.getFirstSection().getBody().getFirstParagraph();
        ParaPr paraPr = para.getParaPr();

        // Istd, RsidP.
        msAssert.areEqual(2, paraPr.getCount());
        msAssert.areEqual(15, paraPr.get(ParaAttr.ISTD)); //User defined style 'Answer'.
        msAssert.isTrue(paraPr.contains(ParaAttr.RSID_P));

        // RsidR, RsidRpr.
        RunPr runPr = para.getParagraphBreakRunPr();
        msAssert.areEqual(2, runPr.getCount());
        msAssert.isTrue(runPr.contains(FontAttr.RSID_R));
        msAssert.isTrue(runPr.contains(FontAttr.RSID_R_PR));

        runPr = para.getFirstRun().getRunPr();
        msAssert.areEqual(0, runPr.getCount());

        Style style = para.getParagraphStyle();

        ParaPr goldParaPr = new ParaPr(); { goldParaPr.setSpaceBefore(80); goldParaPr.setSpaceAfter(140); goldParaPr.setLineSpacing(220); goldParaPr.setLineSpacingRule(LineSpacingRule.EXACTLY); goldParaPr.setLeftIndent(389); }
        testPr(goldParaPr, style.getParaPr(), G_PARA_SET_1);

        // Source Normal is 'Bold'. So, 'Bold' had to be expanded into style attributes.
        RunPr goldRunPr = new RunPr(); { goldRunPr.setSize(19); goldRunPr.setSizeBi(20); goldRunPr.setBold(AttrBoolEx.TRUE); }
        testPr(goldRunPr, style.getRunPr(), G_RUN_SET_1);
    }

    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that equal to parent table style attributes are removed.
    /// </summary>
    @Test
    public void testJira14587ParentTableStyleRemoveEqualAttrs() throws Exception
    {
        Document dstDoc = appendDocuments("TestJira14587ParentTableStyleDst.xml", "TestJira14587ParentTableStyleSrc.xml");

        // Paragraphs are inside a table with TableGrid1 style.
        // TableGrid1 style has spacing after=333 and font size=42.
        ParagraphCollection paras = dstDoc.getFirstSection().getBody().getTables().get(0).getFirstRow().getFirstCell().getParagraphs();

        // First paragraph in table has different from table style attributes, so it must be preserved.
        // SpaceAfter=222, break run FontSize=32
        msAssert.areEqual(222, paras.get(0).getParaPr().getSpaceAfter());
        msAssert.areEqual(32, paras.get(0).getParagraphBreakRunPr().getSize());
        // Second paragraph in table has the same as in table style attributes, so it must be removed.
        // SpaceAfter=333, FontSize=42
        msAssert.isFalse(paras.get(1).getParaPr().contains(ParaAttr.SPACE_AFTER));
        msAssert.isFalse(paras.get(1).getParagraphBreakRunPr().contains(FontAttr.SIZE));

        RunCollection runs = paras.get(0).getRuns();
        // First run in table has the same as in table style attributes, so it must be removed.
        // FontSize=42
        msAssert.isFalse(runs.get(0).getRunPr().contains(FontAttr.SIZE));
        // Second run in table has different from table style attributes, so it must be preserved.
        // FontSize=52
        msAssert.areEqual(52, runs.get(1).getRunPr().getSize());
    }

    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that styles are preserved at the nodes,
    /// when they are the same in destination and source documents
    /// </summary>
    @Test
    public void testJira14587PreserveStylesAllEquals() throws Exception
    {
        // The styles in source and destination documents are completely equal.
        Document dstDoc = appendDocuments("TestJira14587AllStylesDst.xml", "TestJira14587AllStylesSrc.xml");

        ParagraphCollection paras = dstDoc.getFirstSection().getBody().getParagraphs();

        // First para.
        Paragraph para = paras.get(0);
        msAssert.areEqual(1, para.getParaPr().getIstd()); // Heading 1
        msAssert.areEqual(17, para.getFirstRun().getRunPr().getIstd()); // Strong

        // Second para.
        msAssert.areEqual(16, paras.get(1).getParaPr().getIstd()); // NoSpacing

        // Third para.
        para = paras.get(2);
        msAssert.areEqual(16, paras.get(1).getParaPr().getIstd()); // NoSpacing
        msAssert.areEqual(17, para.getParagraphBreakRunPr().getIstd()); // Strong
        msAssert.areEqual(17, para.getFirstRun().getRunPr().getIstd()); // Strong

        // Last para.
        para = paras.get(5);
        msAssert.areEqual(21, para.getParaPr().getIstd()); // ParagraphStyle
        msAssert.areEqual(19, para.getParagraphBreakRunPr().getIstd()); // CharacterStyle
        msAssert.areEqual(19, para.getFirstRun().getRunPr().getIstd()); // CharacterStyle

    }

    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that styles are preserved at the nodes, when they are equal,
    /// despite documents contain other styles that are different, but were not used in imported nodes.
    /// </summary>
    @Test
    public void testJira14587CheckOnlyUsedStyles() throws Exception
    {
        // The destination document contains custom character style that is different from those in source document.
        Document srcDoc = TestUtil.open("Model\\Nodes\\TestJira14587AllStylesSrc.xml");
        Document dstDoc = TestUtil.open("Model\\Nodes\\TestJira14587CustomCharStyleDiffDst.xml");

        // Import and append paragraph, which style is equal in source and destination documents (Heading1).
        NodeImporter nodeImporter = new NodeImporter(srcDoc, dstDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);
        dstDoc.getFirstSection().getBody().appendChild(nodeImporter.importNode(srcDoc.getFirstSection().getBody().getFirstChild(), true));

        // Check despite documents have different styles, the imported paragraph has direct style,
        // i.e. it was not expanded.
        Paragraph para = dstDoc.getFirstSection().getBody().getLastParagraph();
        msAssert.isTrue(para.getParaPr().contains(ParaAttr.ISTD));
    }

    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that if one of the used source styles is different from the style with the same name in destination,
    /// then source styles must be expanded.
    /// </summary>
    @Test (dataProvider = "testJira14587PreserveStylesOneOfStylesDifferentDataProvider") // There is different BuiltIn character style Strong.
    public void testJira14587PreserveStylesOneOfStylesDifferent(String dstDocName) throws Exception
    {
        final String SRC_DOC_NAME = "TestJira14587AllStylesSrc.xml";
        Document dstDoc = appendDocuments(dstDocName, SRC_DOC_NAME);

        // Check that some styles (Heading1 and Strong) were expanded.
        checkHeading1AndStrongExpanded(dstDoc.getFirstSection().getBody().getFirstParagraph());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira14587PreserveStylesOneOfStylesDifferentDataProvider")
	public static Object[][] testJira14587PreserveStylesOneOfStylesDifferentDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TestJira14587BuiltinCharStyleDiffDst.xml"},
			{"TestJira14587BuiltinParaStyleDiffDst.xml"},
			{"TestJira14587CustomCharStyleDiffDst.xml"},
			{"TestJira14587CustomParaStyleDiffDst.xml"},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that if used source BuiltIn linked style is missed in destination,
    /// then source styles must be expanded.
    /// </summary>
    @Test
    public void testJira14587PreserveStylesMissedBuiltinLinked() throws Exception
    {
        // The destination document has exactly the same styles as source document,
        // but there is missed BuiltIn linked style Heading1.
        Document dstDoc = appendDocuments("TestJira14587BuiltinLinkedStyleMissedDst.xml", "TestJira14587AllStylesSrc.xml");

        // Check that some styles (Heading1 and Strong) were expanded.
        checkHeading1AndStrongExpanded(dstDoc.getFirstSection().getBody().getFirstParagraph());
    }

    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that when any other than BuiltIn linked style, which is used in source document is missed in destination,
    /// then source styles can be preserved.
    /// </summary>
    @Test (dataProvider = "testJira14587PreserveStylesMissedOtherThanBuiltinLinkedDataProvider") // There is missed custom linked style LinkedStyle.
    public void testJira14587PreserveStylesMissedOtherThanBuiltinLinked(String dstDocName) throws Exception
    {
        final String SRC_DOC_NAME = "TestJira14587AllStylesSrc.xml";
        Document dstDoc = appendDocuments(dstDocName, SRC_DOC_NAME);
        checkAllStylesPreserved(dstDoc.getFirstSection().getBody().getParagraphs());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira14587PreserveStylesMissedOtherThanBuiltinLinkedDataProvider")
	public static Object[][] testJira14587PreserveStylesMissedOtherThanBuiltinLinkedDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TestJira14587CustomLinkedStyleMissedDst.xml"},
			{"TestJira14587BuiltinParaStyleMissedDst.xml"},
			{"TestJira14587CustomCharStyleMissedDst.xml"},
			{"TestJira14587BuiltinCharStyleMissedDst.xml"},
			{"TestJira14587CustomParaStyleMissedDst.xml"},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-14587
    /// It checks that styles are expanded when there are different defaults in documents
    /// and there is an imported node with the custom character style.
    /// </summary>
    @Test
    public void testJira14587DifferentDefaultsCustomCharacterStyle() throws Exception
    {
        // There are different Defaults in these documents and run has custom style CharacterStyle.
        // Despite expanded attributes are equal, Word expands styles.
        Document dstDoc = appendDocuments("TestJira14587DefaultsDiffDstA.xml", "TestJira14587DefaultsDiffSrcA.xml");

        // Check paragraph does not contain direct style that means it was expanded.
        msAssert.isFalse(dstDoc.getFirstSection().getBody().getFirstParagraph().getParaPr().contains(ParaAttr.ISTD));
    }

    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that styles are preserved even when defaults are different.
    /// </summary>
    @Test
    public void testJira14587DifferentDefaultsNoCharacterStyle() throws Exception
    {
        // There are different Defaults in these documents,
        // but run has no character style (so it is DefaultParagraphFont).
        // Expanded attributes are equal, because of paragraph style,
        // so Word preserves all styles.
        Document dstDoc = appendDocuments("TestJira14587DefaultsDiffDstB.xml", "TestJira14587DefaultsDiffSrcB.xml");
        // Check styles are preserved.
        msAssert.isTrue(dstDoc.getFirstSection().getBody().getFirstParagraph().getParaPr().contains(ParaAttr.ISTD));
    }

    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that styles are not expanded in revisions
    /// and style in revision is created instead of been imported.
    /// </summary>
    @Test
    public void testJira14587RevisionStyle() throws Exception
    {
        Document dstDoc = appendDocuments("TestJira14587RevisionStyleDst.xml", "TestJira14587RevisionStyleSrc.xml");

        Paragraph para = dstDoc.getFirstSection().getBody().getFirstParagraph();

        // Check style is preserved in revision.
        Style style = para.getParagraphStyle(RevisionsView.ORIGINAL);
        msAssert.areEqual("No Spacing", style.getName());

        // Check style in revision was created from the scratch instead of importing.
        msAssert.isFalse(style.getRunPr().contains(FontAttr.SIZE));
    }

    /// <summary>
    /// Relates to WORDSNET-14587
    /// Checks that UpdateFields() with INCLUDETEXT uses UseDestinationStyles mode.
    /// </summary>
    @Test
    public void testJira14587UpdateField() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        String includeDoc = TestUtil.getInTestDataPath("Fields\\LinksAndReferences\\TestJira14587Include.xml");
        builder.insertField(msString.format("INCLUDETEXT \"{0}\"", includeDoc.replace("\\", "\\\\")));

        doc.updateFields();

        builder.moveToDocumentEnd();
        builder.insertDocument(TestUtil.open(includeDoc), ImportFormatMode.KEEP_SOURCE_FORMATTING);

        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();

        Field field = para.getRange().getFields().get(0);
        Run run = (Run)field.getSeparator().getNextSibling();
        msAssert.areEqual(0, run.getRunPr().getCount()); // Nothing is preserved from styles and defaults of the source 'include' file inside field.

        run = ((Paragraph)para.getNextSibling()).getFirstRun();
        msAssert.areEqual(ComplexFontName.fromName("Comic Sans MS"), run.getRunPr().get(FontAttr.NAME_ASCII)); // Inherited from defaults.
        msAssert.areEqual(36, run.getRunPr().get(FontAttr.SIZE)); // Inherited from Normal.
    }



    /// <summary>
    /// WORDSNET-15561 List numbers missing in subsequent section.
    /// We should preserve style when expanding attributes.
    /// This is a duplicate part of WORDSNET-14587
    /// </summary>
    @Test
    public void testJira15561() throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\Nodes\\TestJira15561Src.docx");
        Document dstDoc = TestUtil.open("Model\\Nodes\\TestJira15561Dst.docx");

        dstDoc.appendDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        Paragraph para = (Paragraph)dstDoc.getLastSection().getBody().getLastParagraph().getPreviousNonAnnotationSibling();
        checkParaStyleAndList(para, "Heading 1", 12, "6.");
    }





    /// <summary>
    /// Relates to WORDSNET-18541 Checks that missed Normal style should be imported from the
    /// source document as well as any other missed style.
    /// </summary>
    @Test
    public void testImportNormalStyle() throws Exception
    {
        Document doc = appendDocuments("Test18541Dst.docx", "Test18541Src.docx");

        Style normalStyle = doc.getStyles().get("Normal");
        msAssert.areEqual(32, normalStyle.getRunPr().getSize());
        msAssert.areEqual(44, normalStyle.getRunPr().getSizeBi());
    }

    /// <summary>
    /// WORDSNET-23865 KeepSourceFormatting does not honor source document style.
    /// AW may to merge paragraphs with <see cref="ParaAttr.DropCapPosition"> attribute while saving the document.
    /// This attribute appears in the direct formating after formatting difference calculation due to expanded
    /// style attributes contain global defaults. Exclude global defaults from the style attributes expanding to
    /// fix the issue.
    /// </summary>
    @Test
    public void test23865() throws Exception
    {
        Document src = TestUtil.open("Model\\Nodes\\Test23865Src.docx");
        Document dst = TestUtil.open("Model\\Nodes\\Test23865Dst.docx");

        dst.appendDocument(src, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        ParaPr paraPr = dst.getLastSection().getBody().getFirstParagraph().getParaPr();
        msAssert.isNull(paraPr.get(ParaAttr.DROP_CAP_POSITION));
        msAssert.isNull(paraPr.get(ParaAttr.ISTD));

        msAssert.areEqual(400, paraPr.get(ParaAttr.SPACE_BEFORE));
        msAssert.areEqual(120, paraPr.get(ParaAttr.SPACE_AFTER));
    }


    /// <summary>
    /// Related to WORDSNET-18958
    /// Checks case when effective override values are equal however overrides are not identical(one of overrides is empty).
    /// </summary>
    @Test
    public void test18958EmptyOverrides() throws Exception
    {
        Document doc = appendDocuments(null, "Test18873A1.docx", "Test18873B1.docx");

        msAssert.areNotEqual((/*int*/Integer)doc.getFirstSection().getBody().getFirstParagraph().getParaPr().get(ParaAttr.LIST_ID), (/*int*/Integer)doc.getLastSection().getBody().getFirstParagraph().getParaPr().get(ParaAttr.LIST_ID));
    }


    /// <summary>
    /// WORDSNET-19927 Incorrect font size and alignment inside an inserted table.
    /// FormattingDifferenceCalculator was improved in order to consider the
    /// "overrideTableStyleFontSizeAndJustification" compatibility setting.
    /// </summary>
    /// <remarks>
    /// Destination document contains a Normal style with "12pt" size and "Left" alignment.
    /// The overrideTableStyleFontSizeAndJustification setting is true.
    /// In this case, these values (12pt, Left) will override the values from the table style (11pt, Right).
    /// It is important to preserve correctly calculated direct attributes in a FormattingDifferenceCalculator,
    /// even if they are equals to the attributes from the table style.
    /// </remarks>
    @Test
    public void test19927() throws Exception
    {
        Document doc = appendDocuments("Test19927Dst.docx", "Test19927Src.docx");
        Paragraph para = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getFirstCell().getFirstParagraph();

        ParaPr paraPr = para.getParaPr();
        msAssert.isTrue(paraPr.contains(ParaAttr.ALIGNMENT));
        msAssert.areEqual(ParagraphAlignment.RIGHT, paraPr.getAlignment());

        RunPr runPr = para.getFirstRun().getRunPr();
        msAssert.isTrue(runPr.contains(FontAttr.SIZE));
        msAssert.areEqual(22, runPr.getSize());
        msAssert.isTrue(runPr.contains(FontAttr.SIZE_BI));
        msAssert.areEqual(22, runPr.getSizeBi());
    }

    /// <summary>
    /// WORDSNET-19796 different output using ImportNode (Section) and AppendDocument.
    /// NodeImporter was fixed to not apply format difference for section.
    /// </summary>
    @Test
    public void test19796() throws Exception
    {
        final String EXPECTED_FONT_NAME = "Times New Roman";

        Document usingAppendDocument = getOutputUsingAppendDocument();
        Run run = usingAppendDocument.getSections().get(1).getHeadersFooters().get(4).getParagraphs().get(1).getRuns().get(0);
        msAssert.areEqual(EXPECTED_FONT_NAME, run.getFont().getName());

        Document usingImportNode = getOutputUsingImportNode();
        run = usingImportNode.getSections().get(1).getHeadersFooters().get(4).getParagraphs().get(1).getRuns().get(0);
        msAssert.areEqual(EXPECTED_FONT_NAME, run.getFont().getName());
    }


    /// <summary>
    /// WORDSNET-19757 Calibri (Body) was changed to Calibri during appending documents.
    /// </summary>
    @Test
    public void test19757() throws Exception
    {
        ImportFormatOptions importOptions = new ImportFormatOptions();
        {
            importOptions.setSmartStyleBehavior(true);
            importOptions.setKeepSourceNumbering(true);
        }
        Document dst = appendDocuments("Test19757Dst.docx", importOptions, "Test19757Src.docx");

        Paragraph para1 = dst.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual("AAA / AAA Aaaaaa Aaaaaaa\r", para1.getText());

        Run run = para1.getFirstRun();
        msAssert.isNull(run.getRunPr().get(FontAttr.NAME_ASCII));
        msAssert.isNull(run.getRunPr().get(FontAttr.NAME_FAR_EAST));
        msAssert.isNull(run.getRunPr().get(FontAttr.NAME_OTHER));
        msAssert.isNull(run.getRunPr().get(FontAttr.NAME_BI));

        // Check that inherited font is MinorAscii theme font.
        ComplexFontName fontName = (ComplexFontName)((IRunAttrSource)run).fetchInheritedRunAttr(FontAttr.NAME_ASCII);
        msAssert.isTrue(fontName.isThemeFont());
        msAssert.areEqual(ThemeFontCore.MINOR_H_ANSI, fontName.getThemeFontCore());

        Paragraph para2 = dst.getFirstSection().getBody().getParagraphs().get(2);
        msAssert.areEqual("Aaaaa aa aaa.\f", para2.getText());

        RunPr runPr2 = para2.getFirstRun().getRunPr();
        msAssert.areEqual("Calibri", ((ComplexFontName)runPr2.get(FontAttr.NAME_ASCII)).getName());
        msAssert.areEqual("Times New Roman", ((ComplexFontName)runPr2.get(FontAttr.NAME_FAR_EAST)).getName());
        msAssert.areEqual("Calibri", ((ComplexFontName)runPr2.get(FontAttr.NAME_OTHER)).getName());
        msAssert.areEqual("Times New Roman", ((ComplexFontName)runPr2.get(FontAttr.NAME_BI)).getName());
    }

    /// <summary>
    /// WORDSNET-20376 Font style of header/footer content gets lost after appending two documents.
    /// IgnoreHeaderFooter property has been added.
    /// </summary>
    @Test (dataProvider = "test20376DataProvider")
    public void test20376(boolean ignoreHeaderFooter) throws Exception
    {
        Document dstDocument = TestUtil.open("Model\\Nodes\\Test20376_1.docx");
        Document srcDocument = TestUtil.open("Model\\Nodes\\Test20376_2.docx");

        ImportFormatOptions importFormatOptions = new ImportFormatOptions();
        importFormatOptions.setIgnoreHeaderFooter(ignoreHeaderFooter);

        dstDocument.appendDocument(srcDocument, ImportFormatMode.KEEP_SOURCE_FORMATTING, importFormatOptions);

        HeaderFooter header = dstDocument.getSections().get(1).getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_FIRST);
        HeaderFooter footer = dstDocument.getSections().get(1).getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_FIRST);

        int expectedFontSize = ignoreHeaderFooter ? 20 : 22;
        String expectedFontName = ignoreHeaderFooter ? "Times New Roman" : "Arial";

        msAssert.areEqual(expectedFontSize, header.getFirstParagraph().getFirstRun().getRunPr().getSize());
        msAssert.areEqual(expectedFontName, header.getFirstParagraph().getFirstRun().getRunPr().getName());

        msAssert.areEqual(expectedFontSize, footer.getFirstParagraph().getFirstRun().getRunPr().getSize());
        msAssert.areEqual(expectedFontName, footer.getFirstParagraph().getFirstRun().getRunPr().getName());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test20376DataProvider")
	public static Object[][] test20376DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}




    /// <summary>
    /// WORDSNET-20912 KeepDifferentStyles behavior changed for Normal style.
    /// The issue duplicates WORDSNET-21101
    /// </summary>
    @Test
    public void test20912() throws Exception
    {
        Document dstDoc = appendDocuments("Test20912Dst.docx", "Test20912Src.docx", ImportFormatMode.KEEP_DIFFERENT_STYLES);

        Paragraph paragraph = dstDoc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual("Normal_0", paragraph.getParagraphStyle().getName());
        msAssert.areEqual("Arial Narrow", paragraph.getFirstRun().getFont().getName());
        msAssert.areEqual(8, paragraph.getFirstRun().getFont().getSize());
    }

    /// <summary>
    /// WORDSNET-21233 Table.InsertAfter throws System.InvalidOperationException.
    /// Attempting to add the Sdt with an already existing Id.
    /// </summary>
    @Test
    public void test21233() throws Exception
    {
        Document dstDoc = TestUtil.open("Model\\Nodes\\Test21233Dst.docx");
        Table dstTbl = dstDoc.getFirstSection().getBody().getTables().get(0);

        Document rowDoc = TestUtil.open("Model\\Nodes\\Test21233Row.docx");
        Table srcTbl = rowDoc.getFirstSection().getBody().getTables().get(0);

        NodeImporter importer = new NodeImporter(rowDoc, dstDoc, ImportFormatMode.USE_DESTINATION_STYLES);
        dstTbl.insertAfter(importer.importNode(srcTbl.getRows().get(0), true), dstTbl.getLastRow());

        // Trying to insert the Sdt with the same Id.
        dstTbl.insertAfter(importer.importNode(srcTbl.getRows().get(0), true), dstTbl.getLastRow());
        msAssert.areEqual(3, dstTbl.getRows().getCount());
    }


    /// <summary>
    /// WORDSNET-21485 List numbering updates when KeepSourceNumbering is true (20.12).
    /// Should import even equal styles when 'KeepSourceNumbering' option
    /// is enabled in 'KeepDifferentStyles' importing mode.
    /// </summary>
    @Test (dataProvider = "test21485DataProvider")
    public void test21485(boolean isKeepSourceNumbering, String expectedListLabel) throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\Nodes\\Test21485.docx");
        Document dstDoc = srcDoc.deepClone();

        ImportFormatOptions options = new ImportFormatOptions();
        options.setKeepSourceNumbering(isKeepSourceNumbering);

        NodeImporter importer = new NodeImporter(srcDoc, dstDoc, ImportFormatMode.KEEP_DIFFERENT_STYLES, options);
        for (Node paragraph : srcDoc.getFirstSection().getBody().getParagraphs().toArray())
        {
            Node importedNode = importer.importNode(paragraph, true);
            dstDoc.getFirstSection().getBody().appendChild(importedNode);
        }

        dstDoc.updateListLabels();

        Paragraph para = dstDoc.getLastSection().getBody().getLastParagraph();
        msAssert.areEqual(expectedListLabel, para.getListLabel().getLabelString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test21485DataProvider")
	public static Object[][] test21485DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true,  "6."},
			{false,  "7."},
		};
	}

    /// <summary>
    /// WORDSNET-22103 Merging two documents and list numbering doesn't continue.
    /// Reworked AppendDocument method to use internally DocumentInserter instead of just NodeImporter.
    /// The MergePastedLists option is made public.
    /// </summary>
    @Test (dataProvider = "test22103DataProvider")
    public void test22103(boolean isMergePastedLists) throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\Nodes\\Test22103Src.docx");
        Document dstDoc = TestUtil.open("Model\\Nodes\\Test22103Dst.docx");

        ImportFormatOptions importFormatOptions = new ImportFormatOptions();
        importFormatOptions.setMergePastedLists(isMergePastedLists);
        dstDoc.appendDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES, importFormatOptions);
        dstDoc.updateListLabels();

        // Check the original list items of destination document are not changed.
        ParagraphCollection paras = dstDoc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual("1.", paras.get(0).getListLabel().getLabelString());
        msAssert.areEqual("2.", paras.get(1).getListLabel().getLabelString());
        msAssert.areEqual("3.", paras.get(2).getListLabel().getLabelString());

        // Check the imported list items of source document either continue the existing list
        // of destination document, or starts a new one depending on MergePastedLists option.
        paras = dstDoc.getLastSection().getBody().getParagraphs();
        msAssert.areEqual(isMergePastedLists ? "4." : "1.", paras.get(0).getListLabel().getLabelString());
        msAssert.areEqual(isMergePastedLists ? "5." : "2.", paras.get(1).getListLabel().getLabelString());
        msAssert.areEqual(isMergePastedLists ? "6." : "3.", paras.get(2).getListLabel().getLabelString());
        msAssert.areEqual(isMergePastedLists ? "7." : "4.", paras.get(3).getListLabel().getLabelString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test22103DataProvider")
	public static Object[][] test22103DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}



    /// <summary>
    /// WORDSNET-23299 ArgumentException is thrown while inserting document.
    /// AW treats SDT range start node as annotation item and tries to move it to inline level of the paragraph.
    /// Stop copying when node with problematic type occurs to fix the issue.
    /// </summary>
    @Test (dataProvider = "test23299DataProvider")
    public void test23299(String path) throws Exception
    {
        Document srcDoc = TestUtil.open(path);
        boolean hasBookmarks = srcDoc.getFirstSection().getBody().getFirstChild().getNodeType() == NodeType.BOOKMARK_START;

        DocumentBuilder builder = new DocumentBuilder();
        builder.insertDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES);

        // Check that bookmarks were copied.
        msAssert.areEqual(hasBookmarks ? 2 : 0, builder.getDocument().getFirstSection().getBody().getFirstParagraph().getChildNodes(NodeType.ANY, false).getCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test23299DataProvider")
	public static Object[][] test23299DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Nodes\\Test23299.docx"},
			{"Model\\Nodes\\Test23299Annotations.docx"},
		};
	}

    /// <summary>
    /// WORDSNET-23366 ImportFormatMode.KeepSourceFormatting behaves differently in AW and MS Word.
    /// We should remove attributes equal to parent table style attributes only when they are not
    /// overriden by the corresponding Normal attributes when calculating formatting difference.
    /// </summary>
    @Test
    public void test23366() throws Exception
    {
        Document doc = appendDocuments("Test23366Dst.docx", "Test23366Src.docx");

        // The problematic run is located in the first imported cell.
        Run run = doc.getLastSection().getBody().getTables().get(0).getFirstRow().getFirstCell().getFirstParagraph().getFirstRun();
        msAssert.areEqual("Calibri", ((ComplexFontName)run.getRunPr().get(FontAttr.NAME_ASCII)).getName());
    }





    /// <summary>
    /// WORDSNET-24305 Third level numbering is not preserved after appending document.
    /// Should ensure that list level linked style refers to this list level after import. If it is not so,
    /// then mimic Word by setting corresponding numbering properties into direct paragraph attributes.
    /// </summary>
    @Test
    public void test24305() throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\Nodes\\Test24305Src.docx");
        Document dstDoc = TestUtil.open("Model\\Nodes\\Test24305Dst.docx");

        dstDoc.appendDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES);

        // The first inserted paragraph is problematic one.
        Paragraph para1 = dstDoc.getLastSection().getBody().getFirstParagraph();
        // Check listId is set into direct attributes of the paragraph.
        msAssert.areEqual(2, para1.getParaPr().getListId());

        // This paragraph does not have the same problem as previous one,
        // so check list id is not set in direct attributes.
        Paragraph para2 = (Paragraph)para1.getNextSibling();
        msAssert.isNull(para2.getParaPr().get(ParaAttr.LIST_ID));

        // At last, check actual list labels.
        dstDoc.updateListLabels();
        msAssert.areEqual("3.1.3.", para1.getListLabel().getLabelString());
        msAssert.areEqual("3.2.", para2.getListLabel().getLabelString());
    }


    /// <summary>
    /// WORDSNET-25249 Numbering is broken after importing section.
    /// Consider inherited attributes when take a decision either to set list's attributes
    /// into direct paragraph attributes in <see cref="NodeImporter.ImportList"/>.
    /// </summary>
    @Test
    public void test25249() throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\Nodes\\Test25249.docx");
        Document dstDoc = (Document)srcDoc.deepClone(false);

        dstDoc.appendDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES);

        dstDoc.updateListLabels();
        Paragraph para = dstDoc.getFirstSection().getBody().getParagraphs().get(1);
        msAssert.areEqual("1.1", para.getListLabel().getLabelString());
    }









    /// <summary>
    /// WORDSNET-27734 Paragraphs are not numbered after appending documents with ImportFormatMode.KeepSourceFormatting mode.
    /// Word collapses numbering properties of the destination style over a Normal style and
    /// applies it to the direct properties of imported paragraph. To put it simply, Word sets
    /// ListId=1 and ListLevel=0 in imported paragraph in this case.
    /// </summary>
    @Test
    public void test27734() throws Exception
    {
        Document doc = appendDocuments("Test27734Dst.docx", "Test27734Src.docx");

        doc.updateListLabels();
        Paragraph para = TestUtil.getParagraphWithText(doc, "...Überschrift 1...");
        msAssert.areEqual("1", para.getListLabel().getLabelString());
    }

    /// <summary>
    /// WORDSNET-27678 Font size is changed after importing nodes with ImportFormatMode.KeepSourceFormatting.
    /// We should not collapse revised attributes that exist also in before-changes attributes collection over
    /// a destination 'Normal' style in <see cref="FormattingDifferenceCalculator.GetRunPrDiff"/>.
    /// </summary>
    @Test
    public void test27678() throws Exception
    {
        Document doc = appendDocuments(null, "Test27678.docx");
        doc = TestUtil.saveOpen(doc, "Model\\Nodes\\Test27678", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        ParagraphCollection paras = doc.getLastSection().getBody().getParagraphs();
        msAssert.isFalse(paras.get(0).getFirstRun().getRunPr().hasFormatRevision());
        msAssert.areEqual(20, paras.get(0).getFirstRun().getRunPr().get(FontAttr.SIZE));
        msAssert.areEqual(22, paras.get(1).getFirstRun().getRunPr().getFormatRevision().getRevPr().get(FontAttr.SIZE));
        msAssert.areEqual(24, paras.get(2).getFirstRun().getRunPr().getFormatRevision().getRevPr().get(FontAttr.SIZE));
        msAssert.areEqual(26, paras.get(3).getFirstRun().getRunPr().getFormatRevision().getRevPr().get(FontAttr.SIZE));
        msAssert.areEqual(28, paras.get(4).getFirstRun().getRunPr().getFormatRevision().getRevPr().get(FontAttr.SIZE));
    }







    /// <summary>
    /// Returns Citation source by its Tag name.
    /// </summary>
    private static org.w3c.dom.Node getCitationSourceByTagName(Document doc, String tagName) throws Exception
    {
        CustomXmlPart citationSources = doc.getCustomXmlParts().getCitationSources();
        MemoryStream stream = new MemoryStream(citationSources.getData());
        try /*JAVA: was using*/
        {
            org.w3c.dom.Document xmlDoc = XmlUtilPal.loadXml(stream, true);
            Map<String, String> xmlNames = new HashMap<String, String>();
            {
                xmlNames.put( "b", "http://schemas.openxmlformats.org/officeDocument/2006/bibliography");
            }
            return XmlUtilPal.selectSingleNode(xmlDoc.getDocumentElement(), msString.format("b:Source[b:Tag='{0}']", tagName), xmlNames);
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Returns Title of the specified Citation.
    /// </summary>
    private static String getCitationTitle(org.w3c.dom.Node citation)
    {
        Map<String, String> xmlNames = new HashMap<String, String>();
        {
            xmlNames.put( "b", "http://schemas.openxmlformats.org/officeDocument/2006/bibliography");
        }

        return XmlUtilPal.selectSingleNode((org.w3c.dom.Element)citation, "b:Title", xmlNames).getTextContent();
    }

    /// <summary>
    /// Returns Title of the Citation with a specified Tag name.
    /// </summary>
    private static String getCitationTitle(Document doc, String citationTagName) throws Exception
    {
        org.w3c.dom.Node citationSource = getCitationSourceByTagName(doc, citationTagName);
        return getCitationTitle(citationSource);
    }

    /// <summary>
    /// Returns document for the Test19796 using AppendDocument.
    /// </summary>
    private static Document getOutputUsingAppendDocument() throws Exception
    {
        Document dstDoc = TestUtil.open("Model\\Nodes\\Test19796A.docx");
        Document srcDoc = TestUtil.open("Model\\Nodes\\Test19796B.docx");

        srcDoc.getFirstSection().getPageSetup().setSectionStart(SectionStart.NEW_PAGE);
        srcDoc.getFirstSection().getHeadersFooters().linkToPrevious(false);
        srcDoc.getFirstSection().getPageSetup().setRestartPageNumbering(true);
        srcDoc.getFootnoteOptions().setRestartRule(FootnoteNumberingRule.RESTART_SECTION);

        ImportFormatOptions options = new ImportFormatOptions();
        options.setKeepSourceNumbering(true);

        dstDoc.appendDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING, options);

        return dstDoc;
    }

    /// <summary>
    /// Returns document for the Test19796 using ImportNode (Section).
    /// </summary>
    private static Document getOutputUsingImportNode() throws Exception
    {
        Document dstDoc = TestUtil.open("Model\\Nodes\\Test19796A.docx");
        Document srcDoc = TestUtil.open("Model\\Nodes\\Test19796B.docx");

        for (Section section : (Iterable<Section>) srcDoc.getSections())
        {
            Node importNode = dstDoc.importNode(section, true, ImportFormatMode.KEEP_SOURCE_FORMATTING);
            dstDoc.appendChild(importNode);
        }

        return dstDoc;
    }

    /// <summary>
    /// Checks paragraph style, listId and list label string.
    /// </summary>
    private static void checkParaStyleAndList(Paragraph para, String styleName, int listId, String listLabel) throws Exception
    {
        msAssert.areEqual(styleName, para.getParagraphStyle().getName());
        msAssert.areEqual(listId, para.getParagraphFormat().getListId());

        Document doc = ms.as(para.getDocument(), Document.class);
        msAssert.isNotNull(doc);
        doc.updateListLabels();
        msAssert.areEqual(listLabel, para.getListLabel().getLabelString());
    }

    /// <summary>
    /// This is helper method for checking common properties of paragraph in TestJira14587 family tests.
    /// </summary>
    private static void checkTestJira14587ParaStyleRevision(Paragraph para)
    {
        ParaPr revParaPr = (ParaPr)para.getParaPr().getFormatRevision().getRevPr();
        // Must be 'Istd', 'FormatRevision', 'SpaceAfter' and 'LineSpacing'.
        msAssert.areEqual(4, revParaPr.getCount());
        ParaPr goldParaPr = new ParaPr(); { goldParaPr.setSpaceAfter(200); goldParaPr.setLineSpacing(276); goldParaPr.setLineSpacingRule(LineSpacingRule.MULTIPLE); }
        testPr(goldParaPr, revParaPr, G_PARA_SET_1);
        msAssert.areEqual(0, revParaPr.get(ParaAttr.ISTD));

        ParaPr paraPr = para.getParaPr();
        // Must be 'Istd', 'FormatRevision' and 'RSidP'.
        msAssert.areEqual(3, paraPr.getCount());

        RunPr breakRunPr = para.getParagraphBreakRunPr();
        msAssert.isNull(breakRunPr.getFormatRevision());

        // Must be 'RSidRpr', 'Size', 'SizeBi', Fonts x 4 and Lang x 3.
        msAssert.areEqual(10, breakRunPr.getCount());
        RunPr goldRunPr = new RunPr(); { goldRunPr.setSize(22); goldRunPr.setSizeBi(22); }
        testPr(goldRunPr, breakRunPr, G_RUN_SET_1);
        goldRunPr = new RunPr(); { goldRunPr.setNameAscii("Calibri"); goldRunPr.setNameBi("Arial"); goldRunPr.setNameFarEast("ＭＳ 明朝"); goldRunPr.setNameOther("Calibri"); }
        testPr(goldRunPr, breakRunPr, RunPr.FONT_NAME_ATTRIBUTES);
        msAssert.areEqual(Language.JAPANESE_JAPAN, (/*Language*/Integer)breakRunPr.get(FontAttr.LOCALE_ID_FAR_EAST));
    }

    /// <summary>
    /// Appends documents located in test directory "Model\Nodes\" with the
    /// specified names using import mode KeepSourceFormatting.
    /// If dstDocName is null, then creates a new destination document.
    /// </summary>
    private static Document appendDocuments(String dstDocName, /*params*/ String... srcDocNames) throws Exception
    {
        return appendDocuments(dstDocName, null, srcDocNames);
    }

    /// <summary>
    /// Appends two specified documents located in test directory "Model\Nodes\".
    /// If dstDocName is null, then creates a new destination document.
    /// </summary>
    private static Document appendDocuments(String dstDocName, String srcDocName, /*ImportFormatMode*/int mode) throws Exception
    {
        return appendDocuments(dstDocName, mode, null, srcDocName);
    }

    /// <summary>
    /// Appends documents located in test directory "Model\Nodes\" with the
    /// specified names using import mode KeepSourceFormatting.
    /// If dstDocName is null, then creates a new destination document.
    /// </summary>
    private static Document appendDocuments(String dstDocName, ImportFormatOptions options, /*params*/ String... srcDocNames) throws Exception
    {
        return appendDocuments(dstDocName, ImportFormatMode.KEEP_SOURCE_FORMATTING, options, srcDocNames);
    }

    /// <summary>
    /// Appends documents located in test directory "Model\Nodes\".
    /// If dstDocName is null, then creates a new destination document.
    /// </summary>
    private static Document appendDocuments(String dstDocName, /*ImportFormatMode*/int mode, ImportFormatOptions options,
        /*params*/ String... srcDocNames) throws Exception
    {
        final String TEST_PATH = "Model\\Nodes\\";

        Document dstDoc = (dstDocName == null) ? new Document() : TestUtil.open(Path.combine(TEST_PATH, dstDocName));
        dstDoc.removeAllChildren();

        if (options == null)
            options = new ImportFormatOptions();

        for (String srcDocName : srcDocNames)
        {
            Document srcDoc = TestUtil.open(Path.combine(TEST_PATH, srcDocName));
            dstDoc.appendDocument(srcDoc, mode, options);
        }

        return dstDoc;
    }

    /// <summary>
    /// Returns true if all specified nodes have no direct style attribute.
    /// If node is paragraph, then also checks all its runs.
    /// </summary>
    private static <T extends Node> void checkAllStylesExpanded(Iterable<T> nodes)
    {
        checkDirectStyleExistence(nodes, false);
    }

    /// <summary>
    /// Returns true if all specified nodes have styles in direct attributes.
    /// If node is paragraph, then also checks all its runs.
    /// </summary>
    private static <T extends Node> void checkAllStylesPreserved(Iterable<T> nodes)
    {
        checkDirectStyleExistence(nodes, true);
    }

    /// <summary>
    /// Returns true if all specified nodes contain direct style attribute.
    /// If node is paragraph, then also checks all its runs.
    /// </summary>
    private static <T extends Node> void checkDirectStyleExistence(Iterable<T> nodes, boolean expectedStyleExistance)
    {
        for (Node node : nodes)
            switch (node.getNodeType())
            {
                case NodeType.RUN:
                {
                    msAssert.areEqual(expectedStyleExistance, ((Run)node).getRunPr().contains(FontAttr.ISTD));
                    break;
                }
                case NodeType.PARAGRAPH:
                {
                    Paragraph para = (Paragraph)node;
                    msAssert.areEqual(expectedStyleExistance, para.getParaPr().contains(ParaAttr.ISTD));

                    checkDirectStyleExistence(para.getRuns(), expectedStyleExistance);
                    break;
                }

                default:
                    throw new IllegalStateException((msString.format("Unexpected node type: {0}", node.getNodeType())));
            }
    }

    /// <summary>
    /// The helper method for WORDSNET-14587 that checks specific properties of paragraph and its first run.
    /// </summary>
    /// <param name="para">
    /// The paragraph that has Heading1 style and contains run with Strong style in source document.
    /// </param>
    /// <remarks>
    /// When paragraph have such properties in destination document, it means that
    /// Heading1 and Strong styles are expanded into its direct properties.
    /// </remarks>
    private static void checkHeading1AndStrongExpanded(Paragraph para)
    {
        msAssert.isFalse(para.getParaPr().contains(ParaAttr.ISTD));
        ParaPr goldParaPr = new ParaPr(); { goldParaPr.setSpaceBefore(240); goldParaPr.setSpaceAfter(0); }
        testPr(goldParaPr, para.getParaPr(), G_PARA_SET_1);
        RunPr goldRunPr = new RunPr();
        {
            goldRunPr.setSize(32);
            goldRunPr.setSizeBi(32);
            goldRunPr.setColor(DrColor.fromArgb(0xFF, 0x2F, 0x54, 0x96));
        }
        testPr(goldRunPr, para.getParagraphBreakRunPr(), G_RUN_SET_1);
        goldRunPr.setBold(AttrBoolEx.TRUE);
        testPr(goldRunPr, para.getFirstRun().getRunPr(), G_RUN_SET_1);

        goldRunPr = new RunPr();
        {
            goldRunPr.setThemeAscii(ThemeFontCore.MAJOR_H_ANSI);
            goldRunPr.setThemeBi(ThemeFontCore.MAJOR_BIDI);
            goldRunPr.setThemeFarEast(ThemeFontCore.MAJOR_EAST_ASIA);
            goldRunPr.setThemeOther(ThemeFontCore.MAJOR_H_ANSI);
        }
        testPr(goldRunPr, para.getParagraphBreakRunPr(), RunPr.FONT_NAME_ATTRIBUTES);
        testPr(goldRunPr, para.getFirstRun().getRunPr(), RunPr.FONT_NAME_ATTRIBUTES);
    }

    private static void testPr(AttrCollection goldPr, AttrCollection testPr, int[] keySet)
    {
        for (int key : keySet)
            msAssert.areEqual(goldPr.get(key), testPr.get(key));
    }

    private static /*final*/ int[] G_RUN_SET_1 = new int[]
        {FontAttr.SIZE, FontAttr.SIZE_BI, FontAttr.BOLD, FontAttr.ITALIC, FontAttr.COLOR };

    private static /*final*/ int[] G_PARA_SET_1 = new int[]
        {ParaAttr.SPACE_BEFORE, ParaAttr.SPACE_AFTER, ParaAttr.LINE_SPACING, ParaAttr.LEFT_INDENT};
}

