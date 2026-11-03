// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.SystemPal;
import org.testng.Assert;
import com.aspose.FormatterPal;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.Path;
import java.util.ArrayList;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests index and tables fields.
/// </summary>
@Test
public class TestIndexAndTables extends TestFieldsBase
{
    /// <summary>
    /// Tests how the TOC field is updated.
    ///
    /// JAVAGOLD PNG are encoded differently on Java.
    /// </summary>
    @Test
    public void testToc() throws Exception
    {
        Document doc = openSaveOpenUpdateFields("Fields\\IndexAndTables\\TestToc.docx");
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestToc Modified.docx");
    }

    /// <summary>
    /// Tests how the TOC field with no entries in the document is updated.
    /// </summary>
    @Test
    public void testTocNoEntries() throws Exception
    {
        Document doc = openSaveOpenUpdateFields("Fields\\IndexAndTables\\TestTocNoEntries.docx");
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestTocNoEntries Modified.docx");
    }

    /// <summary>
    /// Tests how TOC fields residing in a paragraph with a Heading style are updated.
    /// </summary>
    @Test
    public void testTocHeadingStyle() throws Exception
    {
        Document doc = openSaveOpenUpdateFields("Fields\\IndexAndTables\\TestTocHeadingStyle.docx");
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestTocHeadingStyle Modified.docx");
    }

    /// <summary>
    /// WORDSNET-16258 "No table of contents entries found" text is shown instead of TOC after updating fields.
    /// </summary>
    @Test
    public void testDefect16258() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect16258.docx");

        // Set some weird culture with ";" as a list separator.
        SystemPal.setCulture("de-De");
        verifyExpectedListSeparator(';');

        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestDefect16258 Modified.docx");
    }

    /// <summary>
    /// WORDSNET-15752 Aspose.Words hangs if insert TOC into heading paragraph.
    /// </summary>
    @Test
    public void testDefect15752() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.insertTableOfContents("\\o \"1-3\" \\h \\z \\u");
        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_1);
        builder.getDocument().updateFields();
        // Nothing to assert, just should not hang here.
    }

    /// <summary>
    /// WORDSNET-15774 Unexpected behavior upon updating TOC if heading paragraphs contains pagebreaks.
    /// </summary>
    @Test
    public void testDefect15774() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect15774.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestDefect15774 Modified.docx");
    }







    /// <summary>
    /// WORDSNET-19076 Table of Content looks incorrect.
    /// </summary>
    @Test
    public void testDefect19076() throws Exception
    {
        Document dstDoc = TestUtil.open("Fields\\IndexAndTables\\TestDefect19076_dst.dotx");
        Document srcDoc = TestUtil.open("Fields\\IndexAndTables\\TestDefect19076_src.dotx");

        dstDoc.appendDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);
        dstDoc.updateFields();

        TestUtil.saveOpenDocxExportOnly(dstDoc, "Fields\\IndexAndTables\\TestDefect19076 Modified.docx");
    }

    /// <summary>
    /// WORDSNET-24890 Column and page breaks appear inside TOC
    /// </summary>
    @Test
    public void testDefect24890TocAndColumnBreaks() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect24890TocAndColumnBreaks.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestDefect24890TocAndColumnBreaks Modified.docx");
    }

    /// <summary>
    /// WORDSNET-24890 Column and page breaks appear inside TOC
    /// </summary>
    @Test
    public void testDefect24890TocAndPageBreaks() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect24890TocAndPageBreaks.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestDefect24890TocAndPageBreaks Modified.docx");
    }



    /// <summary>
    /// WORDSNET-16998 Extra items have been added to TOC after UpdateFields.
    /// </summary>
    @Test
    public void testDefect16998ExtraEntriesInToc() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect16998ExtraEntriesInToc.docx");

        // Set a culture with ',' as a list separator.
        SystemPal.setCulture("en-nz");
        verifyExpectedListSeparator(',');

        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestDefect16998ExtraEntriesInToc Modified.docx");
    }

    private static void verifyExpectedListSeparator(char expectedSeparator)
    {
        msAssert.areEqual(expectedSeparator, FormatterPal.getListSeparatorCurrent(), msString.format(                "This test must get {0} as a list separator from MiscUtil.GetListSeparator() in order to work!",                 expectedSeparator));
    }

    /// <summary>
    /// WORDSNET-16998 Extra items have been added to TOC after UpdateFields.
    /// </summary>
    @Test
    public void testDefect16998CustomListSeparator() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect16998ExtraEntriesInToc.docx");

        // Set some weird culture with ";" as a list separator.
        SystemPal.setCulture("ru-Ru");
        verifyExpectedListSeparator(';');

        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestDefect16998ListSeparator Modified.docx");
    }

    /// <summary>
    /// WORDSNET-16998 \\t switch cancels \\u switch
    /// </summary>
    @Test
    public void testDefect16998TswitchCancelsUswitch() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect16998TswitchCancelsUswitch.docx");

        // Set a culture with ',' as a list separator.
        SystemPal.setCulture("en-nz");
        verifyExpectedListSeparator(',');

        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestDefect16998TswitchCancelsUswitch Modified.docx");
    }

    /// <summary>
    /// WORDSNET-26975 Font formatting of TOC is lost after updating fields.
    /// </summary>
    @Test (dataProvider = "testDefect26975BodyDataProvider")
    public void testDefect26975Body(String fileName) throws Exception
    {
        Document doc = TestUtil.open(fileName);

        doc.updateFields();

        TestUtil.saveOpenDocxExportOnly(doc, fileName.replace(".docx", " Modified.docx"));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDefect26975BodyDataProvider")
	public static Object[][] testDefect26975BodyDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Fields\\IndexAndTables\\TestDefect26975TocHSwitchVsDirectFormatting.docx"},
			{"Fields\\IndexAndTables\\TestDefect26975TocNoHSwitchVsDirectFormatting.docx"},
		};
	}

    /// <summary>
    /// WORDSNET-26975 Checks different font attributes when Hyperlink style is changed.
    /// </summary>
    @Test
    public void testDefect26975HyperlinkStyleChanged() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect26975HyperlinkStyleChanged.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestDefect26975HyperlinkStyleChanged Modified.docx");
    }

    /// <summary>
    /// WORDSNET-26975 Checks cases when character style is applied to the TOC entries.
    /// </summary>
    @Test
    public void testDefect26975HyperLinkStyleHasNoColor() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect26975HyperLinkStyleHasNoColor.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestDefect26975HyperLinkStyleHasNoColor Modified.docx");
    }

    /// <summary>
    /// WORDSNET-26975 Checks cases when character style is applied to the TOC entries.
    /// </summary>
    @Test
    public void testDefect26975CharStyle() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect26975CharStyle.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestDefect26975CharStyle Modified.docx");
    }

    /// <summary>
    /// WORDSNET-26975 Checks more cases when character style is applied to the TOC entries.
    /// </summary>
    @Test
    public void testDefect26975CharStyleAttributes() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect26975CharStyleAttributes.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestDefect26975CharStyleAttributes Modified.docx");
    }

    /// <summary>
    /// WORDSNET-26975 Checks more cases when character style is applied to the TOC entries.
    /// </summary>
    @Test
    public void testDefect26975CharStyleInherited() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect26975CharStyleInherited.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestDefect26975CharStyleInherited Modified.docx");
    }


    /// <summary>
    /// WORDSNET-3786 Page numbers lost in TOC when multiple \\n switches are present.
    /// </summary>
    @Test
    public void testDefect3786TocSwitchOrder() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect3786TocSwitchOrder.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestDefect3786TocSwitchOrder Modified.docx");
    }

    /// <summary>
    /// Tests how table of figures is updated.
    /// </summary>
    @Test
    public void testTableOfFigures() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestTableOfFigures.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestTableOfFigures Modified.docx");
    }

    @Test
    public void testCaptionlessTableOfFiguresWithTrailingSeqField() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestCaptionlessTableOfFiguresWithTrailingSeqField.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestCaptionlessTableOfFiguresWithTrailingSeqField.docx");
    }

    /// <summary>
    /// Tests how table of figures without entries is updated.
    /// </summary>
    @Test
    public void testTableOfFiguresNoEntries() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestTableOfFiguresNoEntries.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestTableOfFiguresNoEntries Modified.docx");
    }

    /// <summary>
    /// WORDSNET-3511 Support TOC of bookmarked content.
    /// </summary>
    @Test
    public void testDefect3511TocBookmarkedRange() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect3511TocBookmarkedRange.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestDefect3511TocBookmarkedRange Modified.docx");
    }

    /// <summary>
    /// WORDSNET-3511 Support TOC of bookmarked content.
    /// </summary>
    @Test
    public void testDefect3511TocBookmarkedRangeStart() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect3511TocBookmarkedRangeStart.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestDefect3511TocBookmarkedRangeStart Modified.docx");
    }

    /// <summary>
    /// WORDSNET-3511 Support TOC of bookmarked content.
    /// </summary>
    @Test
    public void testDefect3511TocBookmarkedRangeSpecial() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect3511TocBookmarkedRangeSpecial.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestDefect3511TocBookmarkedRangeSpecial Modified.docx");
    }


    /// <summary>
    /// WORDSNET-5009 NullReferenceException occurs during UpdateFields.
    /// </summary>
    @Test
    public void testDefect5009NullReference() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect5009NullReference.docx");
        doc.updateFields();
    }

    /// <summary>
    /// WORDSNET-23793 Quotation marks are missed from TOC entries.
    /// All TOC entries are unescaped during copying to TOC while only TC argument should be.
    /// </summary>
    @Test
    public void testDefect23793() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert TOC.
        builder.insertTableOfContents("\\o \"1-3\" \\h \\z \\u");
        builder.writeln();

        // Insert Heading pagegraph with quotation marks.
        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_1);
        builder.write("Test \"content\" bla bla");

        // Update TOC.
        doc.updateFields();

        TestUtil.saveCheckGold(doc, "Fields\\IndexAndTables\\TestDefect23793.docx");
    }


    /// <summary>
    /// WORDSNET-5091 Extra item in TOC.
    /// </summary>
    @Test
    public void testDefect5091TocExtraItem() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect5091TocExtraItem.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestDefect5091TocExtraItem Modified.docx");
    }


    /// <summary>
    /// Tests how tab position is calculated for a normal TOC.
    /// </summary>
    @Test
    public void testTocTabPositionNormal() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestTocTabPositionNormal.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestTocTabPositionNormal Modified.docx");
    }

    /// <summary>
    /// Tests how tab position is calculated for a TOC with a wide entry's label.
    /// </summary>
    @Test
    public void testTocTabPositionWideLabel() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestTocTabPositionWideLabel.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestTocTabPositionWideLabel Modified.docx");
    }

    /// <summary>
    /// Tests how tab position is calculated for a TOC based on a modified Normal style with a large font size.
    /// </summary>
    @Test
    public void testTocTabPositionLargeFontSize() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestTocTabPositionLargeFontSize.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestTocTabPositionLargeFontSize Modified.docx");
    }

    /// <summary>
    /// Tests how tab position is calculated for a TOC that has different font size for each entry.
    /// </summary>
    @Test
    public void testTocTabPositionDifferentFontSize() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestTocTabPositionDifferentFontSize.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestTocTabPositionDifferentFontSize Modified.docx");
    }

    /// <summary>
    /// Tests how tab stop and paragraph break font is set in a document having a theme.
    /// </summary>
    @Test
    public void testTocEntryFontWithTheme() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestTocEntryFontWithTheme.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestTocEntryFontWithTheme Modified.docx");
    }

    /// <summary>
    /// Tests how tab stop and paragraph break font is set in a document having no theme.
    /// </summary>
    @Test
    public void testTocEntryFontWithoutTheme() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestTocEntryFontWithoutTheme.docx");
        doc.updateFields();
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestTocEntryFontWithoutTheme Modified.docx");
    }




    /// <summary>
    /// WORDSNET-6150 Calling UpdateFields twice corrupts TOС/SEQ fields.
    /// </summary>
    @Test
    public void testJira6150() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.insertField("SEQ Table \\* ARABIC \\* MERGEFORMAT");
        builder.writeln();
        builder.insertTableOfContents("\\h \\z \\c \"Table\"");
        builder.getDocument().updateFields();
        builder.getDocument().updateFields();

        TestUtil.saveOpenDocxExportOnly(builder.getDocument(), "Fields\\IndexAndTables\\TestJira6150 Modified.docx");
    }

    /// <summary>
    /// WORDSNET-5497 Code of XE field is shown in TOC after updating fields.
    /// </summary>
    @Test
    public void testJira5497() throws Exception
    {
        Document doc = TestUtil.openUpdateFields("Fields\\IndexAndTables\\TestJira5497.docx");
        TestUtil.saveOpenDocxExportOnly(doc, "Fields\\IndexAndTables\\TestJira5497 Modified.docx");
    }


    /// <summary>
    /// The initial test bundle for INDEX field update.
    /// </summary>
    /// <remarks>
    /// Each of the used input documents contains description in its beginning.
    /// </remarks>
    @Test (dataProvider = "testIndexGeneralDataProvider")
    public void testIndexGeneral(String fileName, boolean useBidiText) throws Exception
    {
        String filePath = Path.combine("Fields\\IndexAndTables\\FieldIndexGeneral", fileName);

        Document doc = TestUtil.open(filePath);

        doc.getFieldOptions().isBidiTextSupportedOnUpdate(useBidiText);
        doc.updateFields();

        TestUtil.saveCheckGold(doc, filePath);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testIndexGeneralDataProvider")
	public static Object[][] testIndexGeneralDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TestFieldIndexColumns.docx",  false},
			{"TestFieldIndexColumnsUpdated.docx",  false},
			{"TestFieldIndexConstraints.docx",  false},
			{"TestFieldIndexEntrySort.docx",  false},
			{"TestFieldIndexFontAttrs.docx",  false},
			{"TestFieldIndexForbiddenPlacements.docx",  false},
			{"TestFieldIndexMisc.docx",  false},
			{"TestFieldIndexPageFormats.docx",  false},
			{"TestFieldIndexPageNumberMerge.docx",  false},
			{"TestFieldIndexRtl.docx",  true},
			{"TestFieldIndexSeparators.docx",  false},
		};
	}

    /// <summary>
    /// Part of TestIndexGeneral test. If this document is included to TestCase of TestIndexGeneral test it fails.
    /// So this separate test was created to avoid this problem on Jekins.
    /// </summary>
    @Test
    public void testIndexGeneralForJenkins() throws Exception
    {
        final String FILE_PATH = "Fields\\IndexAndTables\\FieldIndexGeneral\\TestFieldIndexSubentries.docx";

        Document doc = TestUtil.open(FILE_PATH);

        doc.getFieldOptions().isBidiTextSupportedOnUpdate(false);
        doc.updateFields();

        TestUtil.saveCheckGold(doc, FILE_PATH);
    }


    /// <summary>
    /// Tests how tab stop positions are calculated while updating of INDEX and TOC fields contained in sections
    /// with multiple columns.
    /// </summary>
    @Test
    public void testIndexTocMultipleColumns() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestIndexTocMultipleColumns.docx");
        doc.updateFields();
        TestUtil.saveCheckGold(doc, "Fields\\IndexAndTables\\TestIndexTocMultipleColumns.docx");
    }

    /// <summary>
    /// WORDSNET-9466 custom style separator in \t switch
    /// </summary>
    @Test
    public void testDefect9466() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect9466.docx");
        doc.getFieldOptions().setCustomTocStyleSeparator("#");
        doc.updateFields();

        TestUtil.saveCheckGold(doc, "Fields\\IndexAndTables\\TestDefect9466.docx");
    }


    /// <summary>
    /// WORDSNET-10280 update only page numbers for TOC.
    /// </summary>
    @Test
    public void testDefect10280_1() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect10280_1.docx");

        FieldStart fieldStart = (FieldStart)doc.getChildNodes(NodeType.FIELD_START, true).get(0);
        FieldToc tocField = (FieldToc)fieldStart.getField();

        msAssert.isTrue(tocField.updatePageNumbers());
        TestUtil.saveCheckGold(doc, "Fields\\IndexAndTables\\TestDefect10280_1.docx");
    }

    /// <summary>
    /// WORDSNET-10280 UpdatePageNumbers should return false, if one of related TOC bookmarks removed.
    /// </summary>
    @Test
    public void testDefect10280_2() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect10280_2.docx");

        FieldStart fieldStart = (FieldStart)doc.getChildNodes(NodeType.FIELD_START, true).get(0);
        FieldToc tocField = (FieldToc)fieldStart.getField();

        msAssert.isFalse(tocField.updatePageNumbers());
        TestUtil.saveCheckGold(doc, "Fields\\IndexAndTables\\TestDefect10280_2.docx");
    }

    /// <summary>
    /// WORDSNET-10465 TOC field ("Überschrift 1;1;Überschrift 2;2;Überschrift 3;3") does not work for German language
    /// </summary>
    @Test
    public void testDefect10465() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect10465.docx");
        doc.updateFields();

        TestUtil.saveCheckGold(doc, "Fields\\IndexAndTables\\TestDefect10465.docx");
    }

    /// <summary>
    /// WORDSNET-10610 White spaces are lost when updating a TOC
    /// </summary>
    @Test
    public void testDefect10610() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestDefect10610.docx");
        doc.updateFields();

        // With trailing character
        msAssert.areEqual("Book 1 Test 1\r", getTocEntryText(doc, "0.0.0"));
        msAssert.areEqual("Chapter 1 Test 1.1\r", getTocEntryText(doc, "1.0.0"));
        msAssert.areEqual("Article 1\tTest 1.1.1\r", getTocEntryText(doc, "2.0.0"));
        msAssert.areEqual("Book 2 Test 2\r", getTocEntryText(doc, "3.0.0"));
        msAssert.areEqual("Chapter 2 Test 2.2\r", getTocEntryText(doc, "4.0.0"));
        msAssert.areEqual("Article 2\tTest 2.2.2\r", getTocEntryText(doc, "5.0.0"));

        // Without trailing character
        msAssert.areEqual("Book 3\r", getTocEntryText(doc, "6.0.0"));
        msAssert.areEqual("Chapter 3\r", getTocEntryText(doc, "7.0.0"));
        msAssert.areEqual("Article 3\r", getTocEntryText(doc, "8.0.0"));

        TestUtil.saveCheckGold(doc, "Fields\\IndexAndTables\\TestDefect10610.docx");
    }

    private static String getTocEntryText(Document doc, String paraId)
    {
        Node para = doc.getNodeById(paraId);
        return NodeTextCollector.getText(para, true, para, true, true);
    }

    /// <summary>
    /// WORDSNET-11387 TOC displays "No table of contents entries found" message in genereted output
    /// </summary>
    @Test
    public void testJira11387() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestJira11387.docx");

        doc.updateFields();

        TestUtil.saveCheckGold(doc, "Fields\\IndexAndTables\\TestJira11387.docx");
        // Dmatv: a gold with field value run attributes expanded accepted per WORDSNET-19791
        // See WORDSNET-24486 for the behavior explanation.
    }

    /// <summary>
    /// WORDSNET-11449 UpdateFields does not update TOC field values
    /// </summary>
    @Test
    public void testJira11449() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestJira11449.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.insertTableOfContents("\\o \"1-3\" \\h \\z \\u");

        doc.updateFields();

        // Dmatv: I've accepted a new gold with tblGrids calculated by the new algorithm.
        // However the values in the calculated grid may not match what MS Word saves for the test document.
        // This is because the document is missing page margins in the section properties.
        // MS Word behavior in this case depends on locale, or English 1 inch (72 points) is normally set.
        // AW imitates this behavior in regular mode, but defaults to 90 points in test mode.
        // The gold was accepted with the grid values calculated from 90pt margins.
        // The grids match MS Word save result perfectly if 90pt margins are set in the document via UI.
        int expectedMargin = 90 * 20;
        SectPr sectionPr = doc.getFirstSection().getSectPr();
        msAssert.areEqual(expectedMargin, sectionPr.getLeftMargin());
        msAssert.areEqual(expectedMargin, sectionPr.getRightMargin());

        // Dmatv one of the aut-fit grids becomes incorrect on reading/writing the export result without updating layout.
        // I did not analyze how model manages to do that. The grid becomes correct again with layout update.
        // I changed the test to verify export only.
        TestUtil.saveCheckGoldExportOnly(doc, "Fields\\IndexAndTables\\TestJira11449.docx");
    }

    /// <summary>
    /// WORDSNET-11271 TabStops, Left and Hanging indent values of TOC items are not preserved by UpdateFields.
    /// </summary>
    @Test
    public void testJira11271() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestJira11271.docx");

        doc.updateFields();

        TestUtil.saveCheckGold(doc, "Fields\\IndexAndTables\\TestJira11271.docx");
    }

    /// <summary>
    /// Tests how the TOC field trims leading and trailing whitespaces.
    /// </summary>
    @Test
    public void testTocTrimLeadingAndTrailingWhitespaces() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestTocTrimLeadingAndTrailingWhitespaces.docx");

        doc.updateFields();

        TestUtil.saveCheckGold(doc, "Fields\\IndexAndTables\\TestTocTrimLeadingAndTrailingWhitespaces.docx");
    }

    /// <summary>
    /// Tests how the TOC field is updated by using TC entries.
    /// </summary>
    @Test
    public void testTocWithTc() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestTocWithTC.docx");

        doc.updateFields();

        TestUtil.saveCheckGold(doc, "Fields\\IndexAndTables\\TestTocWithTC.docx");
    }



    /// <summary>
    /// WORDSNET-12288 Document.UpdateFields does not update TOC field
    /// </summary>
    @Test
    public void testJira12288() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestJira12288.docx");

        doc.updateFields();

        TestUtil.saveCheckGold(doc, "Fields\\IndexAndTables\\TestJira12288.docx");
    }


    /// <summary>
    /// Test how the TOC field updates tab leaders.
    /// </summary>
    @Test
    public void testTocTabLeader() throws Exception
    {
        Document doc = TestUtil.open("Fields\\IndexAndTables\\TestTocTabLeader.docx");

        doc.updateFields();

        TestUtil.saveCheckGold(doc, "Fields\\IndexAndTables\\TestTocTabLeader.docx");
    }



    /// <summary>
    /// WORDSNET-12905 UpdateFields produces TOC that is different to Word.
    /// </summary>
    @Test
    public void testJira12905() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestJira12905.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestJira12905.docx");
    }


    /// <summary>
    /// WORDSNET-13268 Creating TOC for text with non-BodyText OutlineLevel works unexpected
    /// </summary>
    @Test
    public void testJira13268() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestJira13268.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestJira13268.docx");
    }

    /// <summary>
    /// WORDSNET-13419 Document.UpdateFields corrupts the document.
    /// </summary>
    @Test
    public void testJira13419() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestJira13419.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestJira13419.docx");
    }

    /// <summary>
    /// WORDSNET-13586 TOC update shows Error Bookmark not defined.
    /// </summary>
    @Test
    public void testJira13586() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestJira13586.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestJira13586.docx");
    }

    /// <summary>
    /// WORDSNET-13587 TOC update shows Error Bookmark not defined.
    /// </summary>
    @Test
    public void testJira13587() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestJira13587.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestJira13587.docx");
    }

    /// <summary>
    /// WORDSNET-11066 TOC is built incorrectly.
    /// </summary>
    @Test
    public void testJira11066() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestJira11066.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestJira11066.docx");
    }


    /// <summary>
    /// WORDSNET-14422 Document.UpdateFields updates the INDEX field incorrectly.
    /// </summary>
    @Test
    public void testJira14422() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestJira14422.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestJira14422.docx");
    }


    /// <summary>
    /// WORDSNET-14865 TOC with \b switch should not add entry for paragraph with bookmark start at the middle.
    /// </summary>
    @Test
    public void testJira14865() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestJira14865.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestJira14865.docx");
    }

    /// <summary>
    /// WORDSNET-14873 Switch \\u does not work, if TOC contains \\t switch.
    /// </summary>
    @Test
    public void testJira14873() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestJira14873.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestJira14873.docx");
    }

    /// <summary>
    /// WORDSNET-14911 Incorrect finding of entry start/finish.
    /// </summary>
    @Test
    public void testJira14911() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestJira14911.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestJira14911.docx");
    }


    /// <summary>
    /// WORDSNET-4930 Outline level should not be ignored when heading range is specified.
    /// </summary>
    @Test
    public void testJira4930OutlineLevelsWithHeadingRangeAndCustomStyles() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestJira4930OutlineLevelsWithHeadingRangeAndCustomStyles.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestJira4930OutlineLevelsWithHeadingRangeAndCustomStyles.docx");
    }

    /// <summary>
    /// Tests how the TOC field is updated with revisions.
    /// </summary>
    @Test
    public void testTocRevisions() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestTocRevisions.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestTocRevisions.docx");
    }

    @Test
    public void testTocEntriesFinalRevision() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestTocEntriesFinalRevision.xml");

        ArrayList<ITocEntry> entries = TocEntryExtractor.extractTocEntries(document, false);

        msAssert.areEqual(3, entries.size());
        msAssert.areEqual(2, entries.get(0).getLevel());
        msAssert.areEqual("a)	Numbering was changed by style change.", entries.get(0).getDocumentOutlineTitle());
        msAssert.areEqual(2, entries.get(1).getLevel());
        msAssert.areEqual("b)	Just a list item but revised because above item is revised.", entries.get(1).getDocumentOutlineTitle());
        msAssert.areEqual(1, entries.get(2).getLevel());
        msAssert.areEqual("Numbering is removed by style change.", entries.get(2).getDocumentOutlineTitle());
    }

    /// <summary>
    /// WORDSNET-15052 Simplifiy OMath when copying it into toc entry.
    /// </summary>
    @Test
    public void testJira15052() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestJira15052.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestJira15052.docx");
    }

    @Test
    public void testToa() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestToa.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestToa.docx");
    }

    @Test
    public void testToaSectionResetNumbering() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestToaSectionResetNumbering.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestToaSectionResetNumbering.docx");
    }

    @Test
    public void testToaPassim() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestToaPassim.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestToaPassim.docx");
    }

    @Test
    public void testToaBoldItalic() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestToaBoldItalic.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestToaBoldItalic.docx");
    }

    @Test
    public void testToaPageRange() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestToaPageRange.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestToaPageRange.docx");
    }

    @Test
    public void testToaSequencedPageRange() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestToaSequencedPageRange.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestToaSequencedPageRange.docx");
    }

    @Test
    public void testToaBetweenText() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestToaBetweenText.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestToaBetweenText.docx");
    }

    @Test (dataProvider = "testToaCategoriesOptionDataProvider")
    public void testToaCategoriesOption(boolean defaultOptionsCase) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        ToaCategories categories;
        if (defaultOptionsCase)
        {
            categories = ToaCategories.getDefaultCategories();
        }
        else
        {
            builder.getDocument().getFieldOptions().setToaCategories(new ToaCategories());
            categories = builder.getDocument().getFieldOptions().getToaCategories();
        }

        try
        {
            builder.insertField("TA \\c 1 \\l entry");
            builder.writeln();
            Field field = builder.insertField("TOA \\c 1 \\h", null);
            categories.set(1, "Blah");

            field.update();

            msAssert.areEqual("Blah\rentry\t0\r", field.getResult());
        }
        finally
        {
            categories.set(1, "Cases");
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testToaCategoriesOptionDataProvider")
	public static Object[][] testToaCategoriesOptionDataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}


    @Test
    public void testTocCustomStyles() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TocCustomStyles.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TocCustomStyles.docx");
    }

    /// <summary>
    /// WORDSNET-15662 AW produces slightly different TOC than MS Word.
    /// </summary>
    @Test
    public void testJira15662() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestJira15662.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestJira15662.docx");
    }

    /// <summary>
    /// WORDSNET-15778 Document.UpdateFields throws System.NullReferenceException.
    /// </summary>
    @Test
    public void testJira15778() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToDocumentStart();
        builder.insertField("INDEX \\e \"\t\" \\c \"1\"");
        builder.insertField("XE test");

        doc.updateFields();

        // There is no exception
    }



    /// <summary>
    ///  Checks that specified "Istd" and content were applied to the run.
    /// </summary>
    private static void checkRunStyleAndContent(Run run, int istd, String text)
    {
        msAssert.areEqual(text, run.getText());
        msAssert.areEqual(istd, run.getRunPr().getIstd());
    }


    /// <summary>
    /// WORDSNET-17249 TOC isn't rendered properly in PDF output
    /// </summary>
    @Test
    public void testJira17249() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestJira17249.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestJira17249.docx");
    }


    /// <summary>
    /// WORDSNET-17663 UpdateFields method recreates bookmarks used in the ToC.
    /// </summary>
    @Test (dataProvider = "testJira17663DataProvider")
    public void testJira17663(int updateCount) throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestJira17663.docx");

        assertNotNullBookmarks(document,
            "_Toc529527385",
            "_Toc529527386",
            "_Toc529527387",
            "_Toc529527388",
            "_Toc529527389",
            "_Toc529527390",
            "_Toc529527391",
            "_Toc529527392",
            "_Toc529527393",
            "_Toc529527394",
            "_Toc529527395",
            "_Toc529527396",
            "_Toc529527397",
            "_Toc529527398",
            "_Toc529527399",
            "_Toc529527400",
            "_Toc529527401",
            "_Toc529527402");

        for (int i = 0; i < updateCount; i++)
            document.updateFields();

        msAssert.areEqual(19 + 3 * (updateCount - 1), document.getRange().getBookmarks().getCount());

        assertNotNullBookmarks(document,
            "_Toc529527389",
            "_Toc529527392",
            "_Toc529527394",
            "_Toc529527395",
            "_Toc529527396",
            "_Toc529527398",
            "_Toc529527401");
        assertNullBookmarks(document,
            "_Toc529527385",
            "_Toc529527386",
            "_Toc529527387",
            "_Toc529527388",
            "_Toc529527390",
            "_Toc529527391",
            "_Toc529527393",
            "_Toc529527397",
            "_Toc529527399",
            "_Toc529527400",
            "_Toc529527402");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira17663DataProvider")
	public static Object[][] testJira17663DataProvider() throws Exception
	{
		return new Object[][]
		{
			{1},
			{2},
			{3},
			{5},
			{7},
		};
	}

    private static void assertNotNullBookmarks(Document document, /*params*/ String... expected)
    {
        BookmarkCollection bookmarks = document.getRange().getBookmarks();
        for (String bookmark : expected)
            msAssert.isNotNull(bookmarks.get(bookmark));
    }

    private static void assertNullBookmarks(Document document, /*params*/ String... expected)
    {
        BookmarkCollection bookmarks = document.getRange().getBookmarks();
        for (String bookmark : expected)
            msAssert.isNull(bookmarks.get(bookmark));
    }


    @Test
    public void testTocHiddenOutlineEntries() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestTocHiddenOutlineEntries.docx");
        Field field = document.getRange().getFields().get(0);

        field.update();

        msAssert.areEqual("No table of contents entries found.", field.getResult());
    }


    /// <summary>
    /// WORDSNET-17952 Document.UpdateFields throws System.NotSupportedException.
    /// </summary>
    @Test
    public void testJira17952() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestJira17952.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestJira17952.docx");
    }


    /// <summary>
    /// WORDSNET-18448 List of Tables adds continuation entries in PDF after calling UpdateFields.
    /// </summary>
    @Test
    public void test18448() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test18448.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\Test18448.docx");
    }


    /// <summary>
    /// WORDSNET-18715 ToC is indented differently by Aspose.Words vs MS Word.
    /// </summary>
    @Test (dataProvider = "test18715DataProvider")
    public void test18715(int hanging) throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test18715.docx");

        document.getStyles().getByStyleIdentifier(StyleIdentifier.TABLE_OF_FIGURES).getParagraphFormat().setFirstLineIndent(-hanging);
        document.getStyles().getByStyleIdentifier(StyleIdentifier.TABLE_OF_FIGURES).getParagraphFormat().setLeftIndent(hanging);
        document.getStyles().getByStyleIdentifier(StyleIdentifier.TOC_1).getParagraphFormat().setFirstLineIndent(-hanging);
        document.getStyles().getByStyleIdentifier(StyleIdentifier.TOC_1).getParagraphFormat().setLeftIndent(hanging);

        document.updateFields();

        TestUtil.saveCheckGold(document, msString.format("Fields\\IndexAndTables\\Test18715 {0}.docx", hanging));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test18715DataProvider")
	public static Object[][] test18715DataProvider() throws Exception
	{
		return new Object[][]
		{
			{0},
			{60},
			{120},
		};
	}


    /// <summary>
    /// WORDSNET-18988 Advance field is lost after calling Document.UpdateFields method.
    /// </summary>
    @Test
    public void test18988() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test18988.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\Test18988.docx");
    }


    /// <summary>
    /// WORDSNET-18084 TOC field sequence separator does not display properly until manual field update.
    /// </summary>
    @Test
    public void test18084() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test18084.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\Test18084.docx");
    }

    /// <summary>
    /// WORDSNET-19435 Document.UpdateFields updates the table of content incorrectly.
    /// </summary>
    @Test
    public void test19435() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test19435.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\Test19435.docx");
    }

    /// <summary>
    /// WORDSNET-20130 UpdateFields does not process TOC field.
    /// </summary>
    @Test
    public void test20130() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test20130.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\Test20130.docx");
    }

    private static String[] getIndexEntries(Document document, int indexFieldIndex, /*params*/ int... subEntryIndexes)
    {
        FieldIndex field = (FieldIndex)FieldExtractor.extractToCollection(document, false, FieldType.FIELD_INDEX).get(indexFieldIndex);

        FieldSeqDataProvider provider = field.hasSequenceName() ? new FieldSeqDataProvider(document) : null;
        IndexEntry entry = IndexEntry.getRootEntry(new FieldCodeIndex(field), provider, document);

        for (int subEntryIndex : subEntryIndexes)
            entry = entry.getSubentry(subEntryIndex);

        String[] entries = new String[entry.getSubentryCount()];
        for (int i = 0; i < entry.getSubentryCount(); i++)
            entries[i] = entry.getSubentryText(i);

        return entries;
    }

    private static String[] getTocEntries(Node document, int tocFieldIndex) throws Exception
    {
        FieldToc field = (FieldToc)FieldExtractor.extractToCollection(document, false, FieldType.FIELD_TOC).get(tocFieldIndex);
        return getTocEntries(field);
    }

    private static String[] getTocEntries(FieldToc field) throws Exception
    {
        Document document = field.fetchDocument();

        document.updateListLabels();

        ArrayList<ITocEntry> entries = TocEntryExtractor.extractTocEntries(document, field.parseOptions());

        String[] result = new String[entries.size()];

        for (int i = 0; i < entries.size(); i++)
        {
            ITocEntry entry = entries.get(i);

            int level = entry.getLevel();
            NodeRange labelRange = entry.getLabelRange();
            NodeRange titleRange = entry.insertBookmark("");

            result[i] = msString.format(
                "{0}{1}{2}",
                msString.newString('\t', level - 1),
                labelRange != null ? NodeTextCollector.getText(labelRange) : "",
                NodeTextCollector.getText(titleRange));
        }

        return result;
    }

    private static String[] getDocumentOutlineEntries(Document document, boolean extractFromTables) throws Exception
    {
        ArrayList<ITocEntry> entries = TocEntryExtractor.extractTocEntries(document, extractFromTables);

        String[] result = new String[entries.size()];
        for (int i = 0; i < entries.size(); i++)
            result[i] = entries.get(i).getDocumentOutlineTitle();

        return result;
    }

    private static String[] getDocumentOutlineEntries(Document document) throws Exception
    {
        return getDocumentOutlineEntries(document, false);
    }

    /// <summary>
    /// WORDSNET-20374 INDEX entries have incorrect Font Style
    /// WORDSNET-20117 Table of Contents does use wrong style
    /// </summary>
    @Test
    public void testEntriesWithMinorHAnsiTheme() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestEntriesWithMinorHAnsiTheme.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestEntriesWithMinorHAnsiTheme.docx");
    }


    /// <summary>
    /// WORDSNET-20691 Document.UpdateFields does not update the TOC correctly.
    /// </summary>
    @Test
    public void test20691() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test20691.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\Test20691.docx");
    }

    @Test (dataProvider = "testIdentifyFieldIndexFormatDataProvider")
    public void testIdentifyFieldIndexFormat(String file, /*FieldIndexFormat*/int format) throws Exception
    {
        Document document = TestUtil.open(file);

        msAssert.areEqual(format, document.getFieldOptions().getFieldIndexFormat());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testIdentifyFieldIndexFormatDataProvider")
	public static Object[][] testIdentifyFieldIndexFormatDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Fields\\IndexAndTables\\TestIdentifyFieldIndexFormat Bulleted.docx",  FieldIndexFormat.BULLETED},
			{"Fields\\IndexAndTables\\TestIdentifyFieldIndexFormat Classic.docx",  FieldIndexFormat.CLASSIC},
			{"Fields\\IndexAndTables\\TestIdentifyFieldIndexFormat Fancy.docx",  FieldIndexFormat.FANCY},
			{"Fields\\IndexAndTables\\TestIdentifyFieldIndexFormat Formal.docx",  FieldIndexFormat.FORMAL},
			{"Fields\\IndexAndTables\\TestIdentifyFieldIndexFormat Modern.docx",  FieldIndexFormat.MODERN},
			{"Fields\\IndexAndTables\\TestIdentifyFieldIndexFormat Simple.docx",  FieldIndexFormat.SIMPLE},
		};
	}

    @Test (dataProvider = "testApplyFieldIndexFormatDataProvider")
    public void testApplyFieldIndexFormat(/*FieldIndexFormat*/int format) throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestApplyFieldIndexFormat.docx");

        document.getFieldOptions().setFieldIndexFormat(format);

        TestUtil.saveCheckGold(
            document,
            msString.format("Fields\\IndexAndTables\\TestApplyFieldIndexFormat {0}.docx", FieldIndexFormat.toString(format)));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testApplyFieldIndexFormatDataProvider")
	public static Object[][] testApplyFieldIndexFormatDataProvider() throws Exception
	{
		return new Object[][]
		{
			{FieldIndexFormat.TEMPLATE},
			{FieldIndexFormat.BULLETED},
			{ FieldIndexFormat.CLASSIC},
			{FieldIndexFormat.FANCY},
			{FieldIndexFormat.FORMAL},
			{FieldIndexFormat.MODERN},
			{FieldIndexFormat.SIMPLE},
		};
	}

    @Test (dataProvider = "testFieldIndexFormatRoundtripDataProvider")
    public void testFieldIndexFormatRoundtrip(/*FieldIndexFormat*/int format) throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestApplyFieldIndexFormat.docx");

        document.getFieldOptions().setFieldIndexFormat(format);

        msAssert.areEqual(format, document.getFieldOptions().getFieldIndexFormat());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFieldIndexFormatRoundtripDataProvider")
	public static Object[][] testFieldIndexFormatRoundtripDataProvider() throws Exception
	{
		return new Object[][]
		{
			{FieldIndexFormat.TEMPLATE},
			{FieldIndexFormat.BULLETED},
			{ FieldIndexFormat.CLASSIC},
			{FieldIndexFormat.FANCY},
			{FieldIndexFormat.FORMAL},
			{FieldIndexFormat.MODERN},
			{FieldIndexFormat.SIMPLE},
		};
	}

    /// <summary>
    /// WORDSNET-21564 TabStop Leader is lost after updating TOC.
    /// </summary>
    @Test
    public void test21564() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test21564.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\Test21564.docx");
    }


    /// <summary>
    /// WORDSNET-18083 TOC field with designated bookmark picks up SEQ entries from outside of it.
    /// </summary>
    @Test
    public void test18083() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test18083.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\Test18083.docx");
    }

    /// <summary>
    /// WORDSNET-22438 Option for Include label and number in Table of Figures.
    /// </summary>
    @Test
    public void test22438() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test22438.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\Test22438.docx");
    }


    /// <summary>
    /// WORDSNET-22509 Make TOA field consider RD entries.
    /// </summary>
    @Test
    public void test22509() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test22509.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\Test22509.docx");
    }

    /// <summary>
    /// WORDSNET-22508 Make INDEX field consider RD entries.
    /// </summary>
    @Test
    public void test22508() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test22508.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\Test22508.docx");
    }

    /// <summary>
    /// WORDSNET-22978 Unexpected behavior of UpdateFields().
    /// </summary>
    @Test
    public void test22978() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test22978.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\Test22978.docx");
    }

    /// <summary>
    /// WORDSNET-23924 InvalidCastException is thrown upon updating fields.
    /// </summary>
    @Test
    public void test23924() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test23924.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\Test23924.docx");
    }

    /// <summary>
    /// WORDSNET-24373 "No table of figures entries found" is shown after updating fields in the document.
    /// </summary>
    @Test
    public void test24373() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test24373.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\Test24373.docx");
    }

    /// <summary>
    /// WORDSNET-24910 Aspose.Words does not include an empty heading paragraph with numbering into the TOC.
    /// </summary>
    @Test
    public void test24910() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test24910.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\Test24910.docx");
    }

    /// <summary>
    /// WORDSNET-25056 Items are missed in INDEX after updating fields.
    /// </summary>
    @Test
    public void test25056() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test25056.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\Test25056.docx");
    }



    /// <summary>
    /// WORDSNET-26492 InvalidOperationException is thrown upon updating fields.
    /// </summary>
    @Test
    public void test26492() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test26492.docx");

        document.updateFields();

        IStructuredDocumentTag sdt = document.getRange().getStructuredDocumentTags().get(1);
        msAssert.isTrue(sdt.isMultiSection());

        StructuredDocumentTagRangeStart sdtStart = (StructuredDocumentTagRangeStart)sdt.getNode();
        StructuredDocumentTagRangeEnd sdtEnd = sdtStart.getRangeEnd();
        Field field = FieldExtractor.extractToCollection(document, false, FieldType.FIELD_INDEX).get(0);

        msAssert.isTrue(sdtStart.isAbove(field.getStart()));
        msAssert.isFalse(sdtEnd.isAbove(field.getEnd()));
    }

    /// <summary>
    /// WORDSNET-26327 InvalidOperationException is thrown upon updating fields.
    /// </summary>
    @Test
    public void test26327() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test26327.docx");

        document.updateFields();

        TestUtil.save(document, "Fields\\IndexAndTables\\Test26327.docx");
    }

    /// <summary>
    /// Tests whether or not TOC field error message has leading paragraph break.
    /// </summary>
    @Test (dataProvider = "testTocFieldErrorMessageDataProvider")
    public void testTocFieldErrorMessage(String fieldCode, String result) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.write("TOC:");
        Field field = builder.insertField(fieldCode, null);

        field.update();

        msAssert.areEqual(result, field.getResult());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTocFieldErrorMessageDataProvider")
	public static Object[][] testTocFieldErrorMessageDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TOC",  "\rNo table of contents entries found."},
			{"TOC \\l 1",  "Error! Not a valid heading level range."},
		};
	}


    @Test (dataProvider = "testTocFieldTCEntriesDataProvider")
    public void testTocFieldTCEntries(String fieldCode, String[] result) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        builder.insertField("TC A \\f 1 \\l 1");
        builder.insertField("TC B \\f 1 \\l 2");
        builder.insertField("TC C \\f \"\" \\l 1");
        builder.insertField("TC D \\f \"\" \\l 2");
        builder.insertField("TC E \\f \\l 1");
        builder.insertField("TC F \\f \\l 2");
        builder.insertField("TC G \\l 1");
        builder.insertField("TC H \\l 2");
        builder.writeln();

        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_1);
        builder.writeln("Heading");
        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.NORMAL);

        FieldToc field = (FieldToc)builder.insertField(fieldCode);

        String[] actual = getTocEntries(field);

        msAssert.areEqual(result, actual);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTocFieldTCEntriesDataProvider")
	public static Object[][] testTocFieldTCEntriesDataProvider() throws Exception
	{
		return new Object[][]
		{
			{" TOC ",  new String[] { "Heading" }},
			{" TOC \\l  ",  new String[] {}},
			{" TOC \\l 1-1 ",  new String[] { "E", "G" }},
			{" TOC \\f  ",  new String[] { "E", "\tF", "G", "\tH" }},
			{" TOC \\f  \\l  ",  new String[] { "E", "\tF", "G", "\tH" }},
			{" TOC \\f  \\l 1-1 ",  new String[] { "E", "G" }},
			{" TOC \\f \"\" ",  new String[] { "C", "\tD" }},
			{" TOC \\f \"\" \\l  ",  new String[] { "C", "\tD" }},
			{" TOC \\f \"\" \\l 1-1 ",  new String[] { "C" }},
			{" TOC \\f 1 ",  new String[] { "A", "\tB" }},
			{" TOC \\f 1 \\l  ",  new String[] { "A", "\tB" }},
			{" TOC \\f 1 \\l 1-1 ",  new String[] { "A" }},
		};
	}

    @Test (dataProvider = "testTocCategoryCDataProvider")
    public void testTocCategoryC(String fieldCode, String[] result) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        builder.insertField("TC ItemMissed \\l 1");
        builder.insertField("TC ItemNull \\f \\l 1");
        builder.insertField("TC ItemEmpty \\f \"\" \\l 1");
        builder.insertField("TC ItemA \\f A \\l 1");
        builder.insertField("TC ItemB \\f B \\l 1");
        builder.insertField("TC ItemC \\f C \\l 1");
        builder.insertField("TC ItemD \\f D \\l 1");
        builder.writeln();

        FieldToc field = (FieldToc)builder.insertField(fieldCode);

        String[] actual = getTocEntries(field);

        msAssert.areEqual(result, actual);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTocCategoryCDataProvider")
	public static Object[][] testTocCategoryCDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TOC \\f",  new String[] { "ItemMissed", "ItemNull", "ItemC" }},
			{"TOC \\f \"\"",  new String[] { "ItemEmpty" }},
			{"TOC \\f C",  new String[] { "ItemMissed", "ItemNull", "ItemC" }},
		};
	}

    @Test
    public void testLinkedStyleEntryStyleFormatting() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestLinkedStyleEntryStyleFormatting.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestLinkedStyleEntryStyleFormatting.docx");
    }

    @Test
    public void testLinkedStyleEntryDirectFormatting() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestLinkedStyleEntryDirectFormatting.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestLinkedStyleEntryDirectFormatting.docx");
    }

    @Test
    public void testLinkedCustomStyles() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestLinkedCustomStyles.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestLinkedCustomStyles.docx");
    }

    @Test
    public void testListLabelFormatting() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\TestListLabelFormatting.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\TestListLabelFormatting.docx");
    }




    /// <summary>
    /// WORDSNET-27759 Extra empty TOC item appears after updating fields.
    /// </summary>
    @Test
    public void test27759() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test27759.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\IndexAndTables\\Test27759.docx");
    }

    /// <summary>
    /// WORDSNET-27595 Page numbers in INDEX field are incorrect.
    /// WORDSNET-27606 Page numbers in INDEX are incorrect if call update field once.
    /// </summary>
    @Test
    public void test27595() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test27595.docx");

        document.updateFields();

        Field field = FieldExtractor.extractToCollection(document, false, FieldType.FIELD_INDEX).get(0);

        msAssert.areEqual("Index Entry, 0\r", field.getResult());
    }

    /// <summary>
    /// WORDSNET-28790 TOC field includes invalid entry.
    /// </summary>
    @Test
    public void test28790() throws Exception
    {
        Document document = TestUtil.open("Fields\\IndexAndTables\\Test28790.docx");

        String[] actual = getTocEntries(document, 0);
        String[] expected =
        {
            "Item 1 (para style)",
            "Item 2 (para style)",
            "Item 3 (char style)",
            "Item 3 (para style)"
        };

        msAssert.areEqual(expected, actual);
    }


}

