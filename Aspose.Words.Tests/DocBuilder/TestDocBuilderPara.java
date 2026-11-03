// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.java.util.JavaUtil;
import com.aspose.DrColor;
import com.aspose.ms.System.msObject;
import org.testng.annotations.DataProvider;


@Test
public class TestDocBuilderPara
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }




    @Test
    public void testWritelnEmpty() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("");
        msAssert.areEqual("\r\f", doc.getText());
    }




    @Test
    public void testLeftIndentWord2000() throws Exception
    {
        DocumentBuilder b = new DocumentBuilder();
        b.getParagraphFormat().setLeftIndent(72.0);    //1"
        b.write("Hello");
        ParagraphFormat pf = ((Paragraph)b.getDocument().getChild(NodeType.PARAGRAPH, 0, true)).getParagraphFormat();
        msAssert.areEqual(72.0, pf.getLeftIndent());
    }

    /// <summary>
    /// Need to visually check in Word2000 and WordXP because writes rtl
    /// attributes that are only in the binary file, but not in the model.
    /// </summary>
    @Test
    public void testCreateRtlPara() throws Exception
    {
        DocumentBuilder b = new DocumentBuilder();
        b.getParagraphFormat().setBidi(true);
        b.getParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);
        b.getParagraphFormat().setRightIndent(36.0);
        b.getParagraphFormat().setLeftIndent(72.0);
        b.writeln("Hello");
        TestUtil.save(b.getDocument(), "DocBuilder\\TestCreateRtlPara.docx", null, false);
    }

    @Test
    public void testMoveToParaInSection() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\TestMoveToPara.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        //Move to beginning of section
        builder.moveToSection(1);
        builder.writeln("Test1");

        //Just out of interest, to make sure inserting sections works okay.
        builder.insertBreak(BreakType.SECTION_BREAK_CONTINUOUS);

        //Move to end of section
        builder.moveToSection(2);
        builder.moveToParagraph(-1, -1);
        builder.write(" Test2");

        TestUtil.save(doc, "DocBuilder\\TestMoveToParaInSection.docx");

        msAssert.areEqual("Section 1, Paragraph 1.\r" +
                "Section 1, Paragraph 2.\f", doc.getSections().get(0).getBody().getText());

        msAssert.areEqual("Test1\r\f", doc.getSections().get(1).getBody().getText());

        msAssert.areEqual("Section 2, Paragraph 1. Test2\f", doc.getSections().get(2).getBody().getText());
    }

    @Test
    public void testMoveToParaStart() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\TestMoveToPara.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToParagraph(1, 0);
        builder.writeln("Inserted text.");

        doc = TestUtil.saveOpen(doc, "DocBuilder\\TestMoveToParaStart.docx", null, false);

        msAssert.areEqual("Section 1, Paragraph 1.\r" +
                "Inserted text.\r" +
                "Section 1, Paragraph 2.\f", doc.getSections().get(0).getBody().getText());

        msAssert.areEqual("Section 2, Paragraph 1.\f", doc.getSections().get(1).getBody().getText());
    }

    @Test
    public void testMoveToParaEnd() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\TestMoveToPara.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToParagraph(0, -1);
        builder.write(" Inserted text.");

        doc = TestUtil.saveOpen(doc, "DocBuilder\\TestMoveToParaEnd.docx", null, false);
        msAssert.areEqual("Section 1, Paragraph 1. Inserted text.\r" +
                "Section 1, Paragraph 2.\f", doc.getSections().get(0).getBody().getText());
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testMoveToParaBeyondDoc() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\TestMoveToPara.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToParagraph(3, 0);
    }

    /// <summary>
    /// WORDSNET-19480 ParagraphFormat.SpaceAfter validation issue.
    /// Word doesn't allow setting values ​​outside the range [0; 1584] for "SpaceAfter". Mimic Word.
    /// </summary>
    @Test (dataProvider = "test19480DataProvider")
    public void test19480(double spaceAfter, boolean expectedIsThrown) throws Exception
    {
        boolean isThrown = false;
        DocumentBuilder builder = new DocumentBuilder();
        builder.getCurrentParagraph().getParagraphFormat().setSpaceAfterAuto(false);

        try
        {
            builder.getCurrentParagraph().getParagraphFormat().setSpaceAfter(spaceAfter);
        }
        catch (IllegalArgumentException e)
        {
            msAssert.areEqual("SpaceAfter", JavaUtil.getParamName(e));
            isThrown = true;
        }

        msAssert.areEqual(expectedIsThrown, isThrown);
        msAssert.areEqual(isThrown ? 0.0 : spaceAfter, builder.getCurrentParagraph().getParagraphFormat().getSpaceAfter());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19480DataProvider")
	public static Object[][] test19480DataProvider() throws Exception
	{
		return new Object[][]
		{
			{-2,  true},
			{-1,  true},
			{0,  false},
			{10,  false},
			{1584,  false},
			{1585,  true},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-19480 Checks validation for "SpaceBefore".
    /// </summary>
    @Test (dataProvider = "test19480SpaceBeforeDataProvider")
    public void test19480SpaceBefore(double spaceBefore, boolean expectedIsThrown) throws Exception
    {
        boolean isThrown = false;
        DocumentBuilder builder = new DocumentBuilder();
        builder.getCurrentParagraph().getParagraphFormat().setSpaceBeforeAuto(false);

        try
        {
            builder.getCurrentParagraph().getParagraphFormat().setSpaceBefore(spaceBefore);
        }
        catch (IllegalArgumentException e)
        {
            msAssert.areEqual("SpaceBefore", JavaUtil.getParamName(e));
            isThrown = true;
        }

        msAssert.areEqual(expectedIsThrown, isThrown);
        msAssert.areEqual(isThrown ? 0.0 : spaceBefore, builder.getCurrentParagraph().getParagraphFormat().getSpaceBefore());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19480SpaceBeforeDataProvider")
	public static Object[][] test19480SpaceBeforeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{-2,  true},
			{-1,  true},
			{0,  false},
			{10,  false},
			{1584,  false},
			{1585,  true},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-19667
    /// Checks how document builder inserts paragraph in the Word manner.
    /// </summary>
    @Test
    public void test19667InsertPara() throws Exception
    {
        Document template = TestUtil.open("DocBuilder\\Test19667InsertPara.docx");

        // 1. Positioned to paragraph.
        Document doc = template.deepClone();
        checkParagraphInsertion(new DocumentBuilder(doc), doc.getFirstSection().getBody().getFirstParagraph(),
            DrColor.YELLOW, DrColor.BLUE, "ab\r", "\f");

        // 2. Positioned to first run.
        doc = template.deepClone();
        checkParagraphInsertion(new DocumentBuilder(doc), doc.getFirstSection().getBody().getFirstParagraph().getFirstRun(),
            DrColor.RED, DrColor.BLUE, "\r", "ab\f");

        // 3. Positioned to last run.
        doc = template.deepClone();
        checkParagraphInsertion(new DocumentBuilder(doc), doc.getFirstSection().getBody().getFirstParagraph().getLastRun(),
            DrColor.RED, DrColor.BLUE, "a\r", "b\f");
    }

    /// <summary>
    /// Relates to WORDSNET-19667
    /// Checks what revisions are generated by AW while paragraph inserting.
    /// </summary>
    @Test
    public void test19667InsertParaRevision() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\Test19667InsertPara.docx");
        doc.startTrackRevisions("am");

        checkParagraphInsertion(new DocumentBuilder(doc), doc.getFirstSection().getBody().getFirstParagraph().getLastRun(),
            DrColor.RED, DrColor.BLUE, "a\r", "b\f");

        msAssert.areEqual(1, doc.getRevisions().getCount());
        msAssert.areEqual(RevisionType.INSERTION, doc.getRevisions().get(0).getRevisionType());
        msAssert.isTrue(msObject.referenceEquals(doc.getFirstSection().getBody().getFirstParagraph(), doc.getRevisions().get(0).getParentNode()));
    }

    /// <summary>
    /// Relates to WORDSNET-19667
    /// Checks with an image in the paragraph content.
    /// </summary>
    @Test
    public void test19667InsertParaImg() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\Test19667InsertParaImg.docx");
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(DrColor.GREEN, shape.getRunPr().get(FontAttr.COLOR));

        checkParagraphInsertion(new DocumentBuilder(doc), doc.getFirstSection().getBody().getFirstParagraph().getLastRun(),
            DrColor.RED, DrColor.BLUE, "a\r", "b\f");
    }

    /// <summary>
    /// Relates to WORDSNET-19667
    /// Checks how document builder inserts paragraph to another empty one.
    /// </summary>
    @Test
    public void test19667InsertToEmptyPara() throws Exception
    {
        Document doc = new Document();
        doc.getFirstSection().getBody().getFirstParagraph().getParagraphBreakRunPr().set(FontAttr.COLOR, DrColor.RED);

        checkParagraphInsertion(new DocumentBuilder(doc), doc.getFirstSection().getBody().getFirstParagraph(),
            DrColor.RED, DrColor.RED, "\r", "\f");
    }

    /// <summary>
    /// WORDSNET-22834 Page break at bookmark position does not work.
    /// Word adds paragraph after the page break depending from the compatibility setting "splitPgBreakAndParaMark" and
    /// compatibility mode. Mimic the Word behavior for the client case to fix the problem.
    /// </summary>
    @Test
    public void test22834() throws Exception
    {
        final String BOOKMARK_NAME = "MYBOOKMARK";
        Document doc = TestUtil.open("DocBuilder\\Test22834.docx");

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToBookmark(BOOKMARK_NAME);

        builder.insertBreak(BreakType.PAGE_BREAK);

        BookmarkEnd bookmarkEnd = (BookmarkEnd)builder.getCurrentNode();
        msAssert.areEqual(BOOKMARK_NAME, bookmarkEnd.getName());
        msAssert.isNull(bookmarkEnd.getPreviousSibling());

        Run breakRun = (Run)((Paragraph)bookmarkEnd.getParentNode().getPreviousSibling()).getLastChild();
        msAssert.areEqual(ControlChar.PAGE_BREAK, breakRun.getText());

        BookmarkStart bookmarkStart = (BookmarkStart)breakRun.getPreviousSibling();
        msAssert.areEqual(BOOKMARK_NAME, bookmarkStart.getName());

        // Add gold because of enterprise level of the client.
        TestUtil.openSaveOpen("DocBuilder\\Test22834.docx");
    }

    /// <summary>
    /// Related with WORDSNET-22834
    /// The case checks that additional paragraph should not be added depending from the compatibility settings.
    /// </summary>
    @Test
    public void test22834NoPara() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\Test22834NoPara.docx");
        Paragraph para = doc.getFirstSection().getBody().getLastParagraph();

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveTo(doc.getFirstSection().getBody().getLastParagraph());

        builder.insertBreak(BreakType.PAGE_BREAK);

        msAssert.areEqual(31, doc.getFirstSection().getBody().getParagraphs().getCount());
        msAssert.isTrue(msObject.referenceEquals(para, doc.getFirstSection().getBody().getLastParagraph()));

        msAssert.areEqual(1, para.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual(ControlChar.PAGE_BREAK, para.getFirstRun().getText());
    }

    /// <summary>
    /// Test that when the cursor is moved, the <see cref="ParagraphFormat"/> property retains its value, but
    /// the formatting properties are updated.
    /// </summary>
    @Test
    public void testParagraphFormat() throws Exception
    {
        Document doc = new Document();
        Body body = doc.getFirstSection().getBody();
        body.prependChild(new Paragraph(doc));
        body.getFirstParagraph().getParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);
        body.prependChild(new Paragraph(doc));
        body.getFirstParagraph().getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToParagraph(0, 0);
        ParagraphFormat paragraphFormat = builder.getParagraphFormat();
        msAssert.areEqual(ParagraphAlignment.CENTER, paragraphFormat.getAlignment());

        builder.moveToParagraph(1, 0);
        msAssert.areSame(paragraphFormat, builder.getParagraphFormat());
        msAssert.areEqual(ParagraphAlignment.RIGHT, builder.getParagraphFormat().getAlignment());
    }

    /// <summary>
    /// Related with WORDSNET-22834
    /// Checks the case when cursor positioned in the content of the paragraph.
    /// Actually the Word adds two paragraphs at this case. AW preserves previous behavior for a while.
    /// </summary>
    @Test (dataProvider = "test22834ContentDataProvider")
    public void test22834Content(String fileName) throws Exception
    {
        final String BOOKMARK_NAME = "MYBOOKMARK";
        Document doc = TestUtil.open("DocBuilder\\" + fileName);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToBookmark(BOOKMARK_NAME);

        builder.insertBreak(BreakType.PAGE_BREAK);

        msAssert.areEqual(31, doc.getFirstSection().getBody().getParagraphs().getCount());
        Paragraph para = doc.getFirstSection().getBody().getLastParagraph();

        Run run = (Run)para.getFirstChild();
        msAssert.areEqual("A", run.getText());

        BookmarkStart bookmarkStart = (BookmarkStart)run.getNextSibling();
        run = (Run)bookmarkStart.getNextSibling();
        msAssert.areEqual(ControlChar.PAGE_BREAK, run.getText());

        BookmarkEnd bookmarkEnd = (BookmarkEnd)run.getNextSibling();
        run = (Run)bookmarkEnd.getNextSibling();
        msAssert.areEqual("BC", run.getText());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test22834ContentDataProvider")
	public static Object[][] test22834ContentDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Test22834Content.docx"},
		};
	}

    /// <summary>
    /// WORDSNET-24033 <see cref="DocumentBuilder.InsertParagraph"/> works incorrectly when cursor is
    /// inside inline-level SDT
    /// It is implemented that <see cref="InvalidOperationException"/> is thrown in this case.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QCannot insert a node of this type at this location.\\E", dataProvider = "test24033DataProvider")
    public void test24033(int positionInSdt) throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\Test24033.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        final String EXPECTED_PARAGRAPH_TEXT = "Text before  Text in SDT Text after SDT\f";
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(EXPECTED_PARAGRAPH_TEXT, paragraph.getText());

        StructuredDocumentTag sdt =
            (StructuredDocumentTag)paragraph.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, false);
        builder.moveToStructuredDocumentTag(sdt, positionInSdt);

        // InsertParagraph throws an exception because the cursor is inside an inline-level SDT.
        builder.insertParagraph();
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test24033DataProvider")
	public static Object[][] test24033DataProvider() throws Exception
	{
		return new Object[][]
		{
			{0},
			{8},
			{-1},
		};
	}

    private void checkParagraphInsertion(DocumentBuilder builder, Node position,
            DrColor color1, DrColor color2, String content1, String content2)
    {
        builder.moveTo(position);
        builder.insertParagraphAsWord();

        Body body = builder.getDocument().getFirstSection().getBody();
        msAssert.areEqual(2, body.getParagraphs().getCount());
        msAssert.areEqual(color1, body.getFirstParagraph().getParagraphBreakRunPr().get(FontAttr.COLOR));
        msAssert.areEqual(color2, body.getLastParagraph().getParagraphBreakRunPr().get(FontAttr.COLOR));

        msAssert.areEqual(content1, body.getFirstParagraph().getRange().getText());
        msAssert.areEqual(content2, body.getLastParagraph().getRange().getText());
    }
}

