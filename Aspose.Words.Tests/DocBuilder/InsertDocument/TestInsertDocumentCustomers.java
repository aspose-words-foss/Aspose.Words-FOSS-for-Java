// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/22/2016 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.DrColor;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


/// <summary>
/// Class for testing issues reported by customers related to DocumentBuilder.InsertDocument().
/// </summary>
public class TestInsertDocumentCustomers
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// WORDSNET-14073 DocumentBuilder.InsertDocument changes page margins in output document.
    /// This special case when inserting document into the
    /// last empty section is processing correctly now.
    /// </summary>
    @Test
    public void testJira14073() throws Exception
    {
        Document dstDoc = openTestDoc("TestJira14073dst.docx");
        Document srcDoc = openTestDoc("TestJira14073src.docx");

        insertAtEndWithBreak(dstDoc, srcDoc, BreakType.SECTION_BREAK_CONTINUOUS, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        PageSetup expectedPgSetup = srcDoc.getLastSection().getPageSetup();
        PageSetup actualPgSetup = dstDoc.getLastSection().getPageSetup();

        msAssert.areEqual(expectedPgSetup.getTopMargin(), actualPgSetup.getTopMargin());
        msAssert.areEqual(expectedPgSetup.getBottomMargin(), actualPgSetup.getBottomMargin());
        msAssert.areEqual(expectedPgSetup.getLeftMargin(), actualPgSetup.getLeftMargin());
        msAssert.areEqual(expectedPgSetup.getRightMargin(), actualPgSetup.getRightMargin());
    }

    /// <summary>
    /// WORDSNET-14438 Unexpected empty space is appeared after using DocumentBuilder.InsertDocument.
    /// Should consider '\f' character as whitespace in DocumentInserter.PostInsertFormatting().
    /// </summary>
    @Test
    public void testJira14438() throws Exception
    {
        Paragraph firstInsertedPara = (Paragraph)insertAtEndWithBreak("TestJira14438dst.docx", "TestJira14438src.docx",
            BreakType.PAGE_BREAK, ImportFormatMode.USE_DESTINATION_STYLES);

        msAssert.areEqual("\fTHIS IS OME HEADLINE.\r", firstInsertedPara.getText());
    }


    /// <summary>
    /// WORDSNET-16127 Different behavior of Section New Page Break in 16.7 and 17.11
    /// When inserting into the last empty section, BreakType should be preserved from the destination section.
    /// </summary>
    @Test (dataProvider = "testJira16127DataProvider")
    public void testJira16127(/*BreakType*/int breakType) throws Exception
    {
        Document dstDoc = openTestDoc("TestJira16127dst.docx");
        Document srcDoc = openTestDoc("TestJira16127src.docx");

        insertAtEndWithBreak(dstDoc, srcDoc, breakType, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        msAssert.areEqual(toSectionStart(breakType), dstDoc.getLastSection().getSectPr().get(SectAttr.SECTION_START));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira16127DataProvider")
	public static Object[][] testJira16127DataProvider() throws Exception
	{
		return new Object[][]
		{
			{BreakType.SECTION_BREAK_CONTINUOUS},
			{BreakType.SECTION_BREAK_NEW_PAGE},
			{BreakType.SECTION_BREAK_EVEN_PAGE},
			{BreakType.SECTION_BREAK_ODD_PAGE},
			{BreakType.SECTION_BREAK_NEW_COLUMN},
		};
	}




    /// <summary>
    /// WORDSNET-19351 List number does not restart when document is inserted using InsertDocument method.
    /// An ImportFormatOptions was not properly passed into a NodeImporter while inserting a document.
    /// </summary>
    @Test (dataProvider = "test19351DataProvider")
    public void test19351(boolean isKeepSourceNumbering, String expectedLabelString) throws Exception
    {
        // Open the same document twice in order to list definitions be the same in source and destination documents.
        Document dstDoc = openTestDoc("Test19351.docx");
        Document srcDoc = openTestDoc("Test19351.docx");

        DocumentBuilder builder = new DocumentBuilder(dstDoc);
        builder.moveToDocumentEnd();
        builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);

        ImportFormatOptions options = new ImportFormatOptions();
        options.setKeepSourceNumbering(isKeepSourceNumbering);
        Paragraph para = (Paragraph)builder.insertDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING, options);

        dstDoc.updateListLabels();

        msAssert.areEqual(expectedLabelString, para.getListLabel().getLabelString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19351DataProvider")
	public static Object[][] test19351DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true,  "1."},
			{false,  "2."},
		};
	}

    /// <summary>
    /// WORDSNET-19532 Issues with output formatting when using KeepSourceFormatting and KeepDifferentStyles
    /// of ImportFormatMode enum.
    /// When concatenating last paragraph before inserted content with a first imported paragraph,
    /// the style of last one should be preserved.
    /// </summary>
    @Test (dataProvider = "test19532DataProvider")
    public void test19532(/*ImportFormatMode*/int importFormatMode) throws Exception
    {
        Document dstDoc = openTestDoc("Test19532Dst.docx");
        Document srcDoc = openTestDoc("Test19532Src.docx");

        DocumentBuilder builder = new DocumentBuilder(dstDoc);

        // Insert into the middle of the paragraph.
        Run run = TestUtil.getRunWithText(dstDoc.getFirstSection().getBody().getFirstParagraph(), "...ipsum...");
        builder.moveTo(run);
        builder.insertDocument(srcDoc, importFormatMode);

        // Check style is preserved.
        ParagraphCollection paras = dstDoc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual("Heading 1", paras.get(0).getParagraphStyle().getName());

        // Check formatting of the concatenated paragraph.
        // ParaPr:
        msAssert.areEqual(333, paras.get(0).getParaPr().get(ParaAttr.SPACE_BEFORE));
        // RunPr:
        RunPr paragraphBreakRunPr = paras.get(0).getParagraphBreakRunPr();
        msAssert.areEqual(DrColor.fromArgb(0x2E, 0x74, 0xB5), paragraphBreakRunPr.get(FontAttr.COLOR));
        msAssert.areEqual(ComplexFontName.fromName("MingLiU_HKSCS"), paragraphBreakRunPr.get(FontAttr.NAME_ASCII));
        msAssert.areEqual(AttrBoolEx.FALSE, paragraphBreakRunPr.get(FontAttr.BOLD));
        msAssert.areEqual(32, paragraphBreakRunPr.get(FontAttr.SIZE));
        msAssert.isNull(paragraphBreakRunPr.get(FontAttr.SIZE_BI));

        // Check formatting of the left part of concatenated paragraph.
        run = paras.get(0).getFirstRun();
        RunPr runRunPr = run.getRunPr();
        msAssert.isNull(runRunPr.get(FontAttr.COLOR));
        msAssert.isNull(runRunPr.get(FontAttr.NAME_ASCII));
        msAssert.isNull(runRunPr.get(FontAttr.BOLD));

        // Check formatting of the right part of concatenated paragraph.
        runRunPr = ((Run)run.getNextSibling()).getRunPr();
        msAssert.areEqual(DrColor.fromArgb(0x2E, 0x74, 0xB5), runRunPr.get(FontAttr.COLOR));
        msAssert.areEqual(ComplexFontName.fromName("MingLiU_HKSCS"), runRunPr.get(FontAttr.NAME_ASCII));
        msAssert.areEqual(AttrBoolEx.FALSE, runRunPr.get(FontAttr.BOLD));
        msAssert.areEqual(32, runRunPr.get(FontAttr.SIZE));
        msAssert.isNull(runRunPr.get(FontAttr.SIZE_BI));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19532DataProvider")
	public static Object[][] test19532DataProvider() throws Exception
	{
		return new Object[][]
		{
			{ImportFormatMode.KEEP_SOURCE_FORMATTING},
			{ImportFormatMode.KEEP_DIFFERENT_STYLES},
		};
	}

    /// <summary>
    /// WORDSNET-20413 Comment becomes part of content.
    /// Implemented inserting of comment nodes in DocumentInserter.
    /// </summary>
    @Test
    public void test20413() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\InsertDocument\\Customers\\Test20413.docx");

        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        StructuredDocumentTag sdt = (StructuredDocumentTag)para.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, false);

        // Check there is a run before comment.
        Run run = (Run)sdt.getFirstChild();
        msAssert.areEqual("Test content ", run.getText());

        // Check there is a comment range start inside SDT.
        CommentRangeStart commentRangeStart = (CommentRangeStart)run.getNextSibling();
        msAssert.isNotNull(commentRangeStart);

        // Check the problematic run is inside the comment range.
        run = (Run)commentRangeStart.getNextSibling();
        msAssert.areEqual("1", run.getText());

        // Check there is a corresponding comment range end inside SDT.
        CommentRangeEnd commentRangeEnd = (CommentRangeEnd)run.getNextSibling();
        msAssert.areEqual(commentRangeStart.getId(), commentRangeEnd.getId());

        // Check there is also a comment itself.
        Comment comment = (Comment)commentRangeEnd.getNextSibling();
        msAssert.areEqual("Test comment ", comment.getFirstParagraph().getFirstRun().getText());
    }


    /// <summary>
    /// WORDSNET-22446 InsertDocument does not preserve HeaderFooters and formatting when inserting into empty section.
    /// Reworked algorithm of inserting document into empty destination section.
    /// </summary>
    @Test (dataProvider = "test22446DataProvider")
    public void test22446(String testName) throws Exception
    {
        final String TEST_DIR = "DocBuilder\\InsertDocument\\DstBlank";
        final String SRC_FILE_NAME = "Src.docx";
        String srcPath = Path.combine(TEST_DIR, SRC_FILE_NAME);
        String dstFileName = msString.format("{0}.docx", testName);
        String dstPath = Path.combine(TEST_DIR, dstFileName);

        Document srcDoc = TestUtil.open(srcPath);

        // Check document insertion in both modes: 'UseDestination' and 'KeepSource'.
        /*ImportFormatMode*/int[] importFormatModes = { ImportFormatMode.USE_DESTINATION_STYLES, ImportFormatMode.KEEP_SOURCE_FORMATTING };
        for (/*ImportFormatMode*/int mode : importFormatModes)
        {
            String suffix = (mode == ImportFormatMode.USE_DESTINATION_STYLES) ? "ms_UseDestination" : "ms_KeepSource";

            // Check document insertion into every section of destination document.
            int sectCount = 1;
            int curSectIdx = 0;
            do
            {
                Document dstDoc = TestUtil.open(dstPath);
                if (curSectIdx == 0)
                    sectCount = dstDoc.getSections().getCount();

                // Insert document into the current destination section.
                DocumentBuilder builder = new DocumentBuilder(dstDoc);
                builder.moveToSection(curSectIdx);
                builder.insertDocument(srcDoc, mode);

                // Check all sections against gold document produced by Word.
                String sectNum = msString.format("Sect{0}", curSectIdx);
                String goldName = msString.format("{0} {1}{2}.docx", dstFileName, suffix, sectNum);
                checkSections(dstDoc, Path.combine(TEST_DIR, goldName));

                curSectIdx++;
            }
            while (curSectIdx < sectCount);
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test22446DataProvider")
	public static Object[][] test22446DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"DstBlank"},
			{"DstNonBlank"},
			{"DstBlankWithHeader"},
			{"DstBlankMultiSections"},
			{"DstBlankMultiWithAllSectHeader"},
			{"DstBlankMultiWithSect0Header"},
			{"DstBlankMultiWithSect1Header"},
			{"DstBlankMultiWithSect2Header"},
			{"DstNonEmptyBody0"},
			{"DstNonEmptyBody1"},
			{"DstNonEmptyBody2"},
		};
	}







    /// <summary>
    /// Checks that specified ParaPr is as expected in <see cref="Test22149"/>.
    /// </summary>
    private static void checkParaPr22149(Paragraph paragraph, String expectedText)
    {
        msAssert.isTrue(paragraph.getText().startsWith(expectedText));
        msAssert.areEqual("", paragraph.getListLabel().getLabelString());

        ParaPr paraPr = paragraph.getParaPr();

        msAssert.areEqual(0, paraPr.get(ParaAttr.LIST_ID));
        msAssert.areEqual(0, paraPr.get(ParaAttr.LIST_LEVEL));
        msAssert.areEqual(113, paraPr.get(ParaAttr.LEFT_INDENT));

        msAssert.isTrue(paraPr.contains(ParaAttr.ISTD));
        msAssert.isTrue(paraPr.contains(ParaAttr.RSID_P));

        msAssert.areEqual(5, paraPr.getCount());
    }

    /// <summary>
    /// The helper method for <see cref="TestJira14397"/>.
    /// It checks ListId, LeftIndent and SpaceBefore of specified paragraph attributes.
    /// </summary>
    private static void checkParaPr14397(ParaPr paraPr)
    {
        msAssert.areEqual(2, paraPr.getListId());
        msAssert.areEqual(1440, paraPr.getLeftIndent());
        msAssert.areEqual(600, paraPr.getSpaceBefore());
    }

    /// <summary>
    /// Checks <see cref="TestJira17083"/> with a specified options.
    /// </summary>
    private static void checkTestJira17083(ImportFormatOptions options) throws Exception
    {
        Document dstDoc = openTestDoc("TestJira17083Dst.docx");
        Document srcDoc = openTestDoc("TestJira17083Src.docx");

        DocumentBuilder builder = new DocumentBuilder(dstDoc);

        builder.moveToMergeField("MERGE-TANF-LINE", false, true);
        Paragraph para = (Paragraph)builder.insertDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING, options);

        // This is first imported run before which we check run with white space.
        Run run = TestUtil.getRunWithText(para, "...Because you receive TANF cash and medical benefits...");
        msAssert.areEqual(options.getAdjustSentenceAndWordSpacing(), ((Run)run.getPreviousSibling()).getText().equals(" "));
    }

    /// <summary>
    /// Converts BreakType to SectionStart.
    /// </summary>
    private static /*SectionStart*/int toSectionStart(/*BreakType*/int breakType)
    {
        switch (breakType)
        {
            case BreakType.SECTION_BREAK_CONTINUOUS:
                return SectionStart.CONTINUOUS;
            case BreakType.SECTION_BREAK_NEW_PAGE:
                return SectionStart.NEW_PAGE;
            case BreakType.SECTION_BREAK_EVEN_PAGE:
                return SectionStart.EVEN_PAGE;
            case BreakType.SECTION_BREAK_ODD_PAGE:
                return SectionStart.ODD_PAGE;
            case BreakType.SECTION_BREAK_NEW_COLUMN:
                return SectionStart.NEW_COLUMN;
            default:
                throw new IllegalStateException(msString.format("Unexpected break type: {0}", breakType));
        }
    }

    /// <summary>
    /// Inserts one document at the end of another with break between them.
    /// </summary>
    /// <returns>First inserted node inside destination document.</returns>
    private static Node insertAtEndWithBreak(Document dstDoc, Document srcDoc, /*BreakType*/int breakType, /*ImportFormatMode*/int mode)
    {
        DocumentBuilder builder = new DocumentBuilder(dstDoc);
        builder.moveToDocumentEnd();
        builder.insertBreak(breakType);

        return builder.insertDocument(srcDoc, mode);
    }

    /// <summary>
    /// Inserts one document at the end of another with break between them.
    /// </summary>
    /// <returns>First inserted node inside destination document.</returns>
    private static Node insertAtEndWithBreak(String dstFileName, String srcFileName, /*BreakType*/int breakType, /*ImportFormatMode*/int mode) throws Exception
    {
        Document dstDoc = openTestDoc(dstFileName);
        Document srcDoc = openTestDoc(srcFileName);

        return insertAtEndWithBreak(dstDoc, srcDoc, breakType, mode);
    }

    private static Document openTestDoc(String filename) throws Exception
    {
        return TestUtil.open(getTestPath(filename));
    }

    /// <summary>
    /// Returns path relative to 'DocBuilder\InsertDocument\Customers'.
    /// </summary>
    private static String getTestPath(String filename) throws Exception
    {
        return Path.combine("DocBuilder\\InsertDocument\\Customers\\", filename);
    }

    /// <summary>
    /// Checks sections of a specified document against gold document.
    /// </summary>
    private static void checkSections(Document doc, String goldPath) throws Exception
    {
        Document goldDoc = TestUtil.open(TestUtil.getInTestGoldPath(goldPath));

        msAssert.areEqual(goldDoc.getSections().getCount(), doc.getSections().getCount());

        for (int i = 0; i < goldDoc.getSections().getCount(); i++)
            areEqual(goldDoc.getSections().get(i), doc.getSections().get(i));
    }

    /// <summary>
    /// Returns true, if text and formatting of specified sections are equal.
    /// </summary>
    private static void areEqual(Section sectionA, Section sectionB)
    {
        msAssert.areEqual(sectionA.getText(), sectionB.getText());
        msAssert.isTrue(sectionA.getSectPr().equals(sectionB.getSectPr()));
    }
}

