// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Drawing.msSizeF;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.SystemPal;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for PageSetup and text columns.
/// </summary>
@Test
public class TestPageSetup extends UnifiedTestsBase
{
    /// <summary>
    /// WORDSNET-3301 Allow specifying "Text Flow" through PageSetup.
    /// </summary>
    @Test
    public void testOrientations() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        for (/*TextOrientation*/int to : TextOrientation.getValues())
        {
            builder.getCurrentSection().getPageSetup().setTextOrientation(to);
            builder.writeln(TextOrientation.toString(to));
            builder.insertSection(SectionStart.NEW_PAGE);
        }

        final String TEST_NAME = "Model\\Page\\orientations";

        // FOSS: only the Docx roundtrip survives — Doc/Rtf/WordML save were removed.
        assertOrientations(TestUtil.saveOpen(doc, TEST_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD));
    }

    private static void assertOrientations(Document saved)
    {
        int count = 0;
        for (/*TextOrientation*/int to : TextOrientation.getValues())
        {
            Section current = saved.getSections().get(count++);
            msAssert.areEqual(to, current.getPageSetup().getTextOrientation());
        }
    }

    @Test
    public void testDefaults() throws Exception
    {
        ISectionAttrSource sectionAttrSource = new SectPr();
        PageSetup ps = new PageSetup(sectionAttrSource, new DocPr());

        msAssert.areEqual(SectionStart.NEW_PAGE, ps.getSectionStart());
        msAssert.areEqual(false, ps.getDifferentFirstPageHeaderFooter());
        msAssert.areEqual(false, ps.getSuppressEndnotes());
        msAssert.areEqual(PageVerticalAlignment.TOP, ps.getVerticalAlignment());
        msAssert.areEqual(TextFlow.HORIZONTAL, ps.getTextFlow());
        msAssert.areEqual(false, ps.getBidi());

        msAssert.areEqual(ConvertUtil.inchToPoint(8.5), ps.getPageWidth());
        msAssert.areEqual(ConvertUtil.inchToPoint(11.0), ps.getPageHeight());
        msAssert.areEqual(Orientation.PORTRAIT, ps.getOrientation());
        msAssert.areEqual(0, ps.getPaperCode());

        msAssert.areEqual(ConvertUtil.inchToPoint(1.25), ps.getLeftMargin());
        msAssert.areEqual(ConvertUtil.inchToPoint(1.25), ps.getRightMargin());
        msAssert.areEqual(ConvertUtil.inchToPoint(1.0), ps.getTopMargin());
        msAssert.areEqual(ConvertUtil.inchToPoint(1.0), ps.getBottomMargin());
        msAssert.areEqual(ConvertUtil.inchToPoint(0.5), ps.getHeaderDistance());
        msAssert.areEqual(ConvertUtil.inchToPoint(0.5), ps.getFooterDistance());
        msAssert.areEqual(0d, ps.getGutter());

        msAssert.areEqual(0, ps.getFirstPageTray());
        msAssert.areEqual(0, ps.getOtherPagesTray());

        msAssert.areEqual(ChapterPageSeparator.HYPHEN, ps.getChapterPageSeparator());
        msAssert.areEqual(0, ps.getHeadingLevelForChapter());
        msAssert.areEqual(NumberStyle.ARABIC, ps.getPageNumberStyle());
        msAssert.areEqual(false, ps.getRestartPageNumbering());
        msAssert.areEqual(1, ps.getPageStartingNumber());

        msAssert.areEqual(LineNumberRestartMode.RESTART_PAGE, ps.getLineNumberRestartMode());
        msAssert.areEqual(0, ps.getLineNumberCountBy());
        msAssert.areEqual(0d, ps.getLineNumberDistanceFromText());
        msAssert.areEqual(1, ps.getLineStartingNumber());    

        msAssert.areEqual(true, ps.getTextColumns().getEvenlySpaced());
        msAssert.areEqual(ConvertUtil.inchToPoint(0.5), ps.getTextColumns().getSpacing());
        msAssert.areEqual(ConvertUtil.inchToPoint(8.5 - 1.25 * 2.0), ps.getTextColumns().getWidth());
        msAssert.areEqual(1, ps.getTextColumns().getCount());
        msAssert.areEqual(false, ps.getTextColumns().getLineBetween());

        msAssert.areEqual(false, ps.getRtlGutter());
        msAssert.areEqual(false, ps.getUnlocked());

        msAssert.areEqual(LineStyle.NONE, ps.getBorders().getByBorderType(BorderType.LEFT).getLineStyle());
        msAssert.areEqual(PageBorderAppliesTo.ALL_PAGES, ps.getBorderAppliesTo());
        msAssert.areEqual(PageBorderDistanceFrom.TEXT, ps.getBorderDistanceFrom());
        msAssert.areEqual(true, ps.getBorderAlwaysInFront());

        msAssert.areEqual(0, ps.getCharSpace());
        msAssert.areEqual(0d, ps.getLinePitch());
        msAssert.areEqual(SectionLayoutMode.DEFAULT, ps.getLayoutMode());
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testMinOneColumn()
    {
        ISectionAttrSource sectionAttrSource = new SectPr();
        PageSetup ps = new PageSetup(sectionAttrSource, new DocPr());
        TextColumnCollection cs = ps.getTextColumns();
        cs.setCount(0);
    }

    @Test
    public void testDefaultColumnsNotChanged()
    {
        //Modify columns of a section.
        ISectionAttrSource sectionAttrSource1 = new SectPr();
        PageSetup ps1 = new PageSetup(sectionAttrSource1, new DocPr());
        TextColumnCollection cs1 = ps1.getTextColumns();
        cs1.setCount(2);
        cs1.get(0).setWidth(100.0);

        //Just make sure we didn't modify the default attributes.
        ISectionAttrSource sectionAttrSource2 = new SectPr();
        PageSetup ps2 = new PageSetup(sectionAttrSource2, new DocPr());
        TextColumnCollection cs2 = ps2.getTextColumns();
        msAssert.areEqual(1, cs2.getCount());
        msAssert.areEqual(0d, cs2.get(0).getWidth());
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestChangeColumns(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        TextColumnCollection cs = doc.getFirstSection().getPageSetup().getTextColumns();
        
        //Initially the columns are evenly spaced.
        cs.setCount(2);

        //Check that individual columns can be accessed in evenly spaced mode and can be changed.
        msAssert.areEqual(2, cs.getCount());
        msAssert.areEqual(0d, cs.get(0).getWidth());
        msAssert.areEqual(0d, cs.get(0).getSpaceAfter());
        msAssert.areEqual(0d, cs.get(1).getWidth());
        msAssert.areEqual(0d, cs.get(1).getSpaceAfter());

        //Set column widths.
        cs.get(0).setWidth(100.0);
        cs.get(0).setSpaceAfter(20.0);
        cs.get(1).setWidth(300.0);

        //Make sure the evenly spaced flag does not get automatically reset.
        msAssert.areEqual(true, cs.getEvenlySpaced());
        cs.setEvenlySpaced(false);

        //Read and write and check the data.
        doc = TestUtil.saveOpen(doc, "Model\\Page\\TestChangeColumns", lf, sf);
        cs = doc.getFirstSection().getPageSetup().getTextColumns();

        msAssert.areEqual(2, cs.getCount());
        msAssert.areEqual(false, cs.getEvenlySpaced());
        msAssert.areEqual(100d, cs.get(0).getWidth());
        msAssert.areEqual(20d, cs.get(0).getSpaceAfter());
        msAssert.areEqual(300d, cs.get(1).getWidth());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestChangeColumnsSourceDataProvider")
	public void unifiedTestChangeColumnsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestChangeColumns(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestChangeColumnsSourceDataProvider")
	Object[][] unifiedTestChangeColumnsSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    @Test
    public void testCloneColumns() throws Exception
    {
        Document doc = new Document();

        //Modify columns in the first section.
        TextColumnCollection cs1 = doc.getFirstSection().getPageSetup().getTextColumns();
        cs1.setCount(2);
        cs1.setLineBetween(true);
        cs1.get(0).setWidth(100.0);
        cs1.get(0).setSpaceAfter(10.0);
        cs1.get(1).setWidth(200.0);
        
        //Clone the section, second section should have same columns as the first now.
        doc.appendChild(doc.getFirstSection().deepClone(true));

        //Modify first section.
        cs1.setLineBetween(false);
        cs1.setCount(1);
        cs1.get(0).setWidth(999.0);

        //Check second section remains original.
        TextColumnCollection cs2 = doc.getLastSection().getPageSetup().getTextColumns();
        msAssert.isTrue(cs1 != cs2);
        msAssert.isTrue(cs1.get(0) != cs2.get(0));
        msAssert.areEqual(2, cs2.getCount());
        msAssert.areEqual(true, cs2.getLineBetween());
        msAssert.areEqual(100d, cs2.get(0).getWidth());
        msAssert.areEqual(10d, cs2.get(0).getSpaceAfter());
        msAssert.areEqual(200d, cs2.get(1).getWidth());
    }

    @Test
    public void testDefaultBordersNotChanged() throws Exception
    {
        //Modify borders one a section.
        ISectionAttrSource sectionAttrSource1 = new SectPr();
        PageSetup ps1 = new PageSetup(sectionAttrSource1, new DocPr());
        ps1.getBorders().getByBorderType(BorderType.LEFT).setLineStyle(LineStyle.DOUBLE);

        //Make sure that when we modify page border, it modified the border of the section, not the default border.
        ISectionAttrSource sectionAttrSource2 = new SectPr();
        PageSetup ps2 = new PageSetup(sectionAttrSource2, new DocPr());
        msAssert.areEqual(LineStyle.NONE, ps2.getBorders().getByBorderType(BorderType.LEFT).getLineStyle());
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestChangeBorders(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // TODO 1 DV Implement reading page borders.
        if (lf == LoadFormat.RTF)
            return;

        Document doc = new Document();
        PageSetup ps = doc.getFirstSection().getPageSetup();
        
        //Modify borders
        ps.getBorders().getByBorderType(BorderType.LEFT).setLineStyle(LineStyle.DOUBLE);

        //Read and write
        doc = TestUtil.saveOpen(doc, "Model\\Page\\TestChangeBorders", lf, sf);
        ps = doc.getFirstSection().getPageSetup();

        //Check the borders
        msAssert.areEqual(LineStyle.DOUBLE, ps.getBorders().getByBorderType(BorderType.LEFT).getLineStyle());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestChangeBordersSourceDataProvider")
	public void unifiedTestChangeBordersSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestChangeBorders(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestChangeBordersSourceDataProvider")
	Object[][] unifiedTestChangeBordersSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    @Test
    public void testCloneBorders() throws Exception
    {
        Document doc = new Document();

        //Modify borders in the first section.
        BorderCollection b1 = doc.getFirstSection().getPageSetup().getBorders();
        //Top will be an explicit border.
        b1.getByBorderType(BorderType.TOP).setLineWidth(4.0);    
        //Left will be an inherited border, inherited borders should not be cloned at all.
        Border left1 = b1.getByBorderType(BorderType.LEFT);    
        
        //Clone the section, second section should now have same borders as the first.
        doc.appendChild(doc.getFirstSection().deepClone(true));

        //Modify borders in the first section again.
        b1.getByBorderType(BorderType.TOP).setLineWidth(10.0);

        //Check second section borders remain original.
        BorderCollection b2 = doc.getLastSection().getPageSetup().getBorders();
        msAssert.isTrue(b1 != b2);
        msAssert.isTrue(b1.getByBorderType(BorderType.TOP) != b2.getByBorderType(BorderType.TOP));
        msAssert.areEqual(4d, b2.getByBorderType(BorderType.TOP).getLineWidth());

        //This is actually a new inherited border created for section 2.
        Border left2 = b2.getByBorderType(BorderType.LEFT);
        msAssert.isTrue(left1 != left2);

        //Just a sanity check that modifying one inherited border does not affect the other.
        left1.setLineStyle(LineStyle.DOT);
        msAssert.areEqual(LineStyle.NONE, left2.getLineStyle());
    }

    /// <summary>
    /// Tests the <see cref="FootnoteOptions.Columns"/> property.
    /// </summary>
    @Test
    public void testFootnoteColumns() throws Exception
    {
        Document doc = new Document();

        doc.getFirstSection().getSectPr().setFootnoteColumns(2);
        msAssert.areEqual(2, doc.getFirstSection().getPageSetup().getFootnoteOptions().getColumns());
        doc.getFirstSection().getPageSetup().getFootnoteOptions().setColumns(0);
        msAssert.areEqual(0, doc.getFirstSection().getSectPr().getFootnoteColumns());

        doc.getFirstSection().getSectPr().setFootnoteColumns(2);
        // FootnoteColumns are not written in ECMA-376.
        doc.setComplianceInfo(new OoxmlComplianceInfo());
        doc.getComplianceInfo().markAsIsoTransitional();

        doc = TestUtil.saveOpen(doc, "Model\\Footnote\\TestFootnoteColumns", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        msAssert.areEqual(2, doc.getFirstSection().getPageSetup().getFootnoteOptions().getColumns());
        msAssert.areEqual(2, doc.getFirstSection().getSectPr().getFootnoteColumns());
    }

    /// <summary>
    /// Tests that values of the <see cref="PageSetup.CharactersPerLine"/> and <see cref="PageSetup.LinesPerPage"/>
    /// properties are calculated correctly.
    /// </summary>
    @Test
    public void testDocumentGridCalculation() throws Exception
    {
        Document doc = TestUtil.open("Model\\Page\\TestDocumentGrid.docx");
        SectionCollection sections = doc.getSections();

        checkDocumentGrid(sections.get(0), SectionLayoutMode.LINE_GRID, 32, 28);
        checkDocumentGrid(sections.get(1), SectionLayoutMode.GRID, 20, 24);
        checkDocumentGrid(sections.get(2), SectionLayoutMode.SNAP_TO_CHARS, 30, 20);
        checkDocumentGrid(sections.get(3), SectionLayoutMode.GRID, 7, 32);
        checkDocumentGrid(sections.get(4), SectionLayoutMode.GRID, 7, 32);
    }

    /// <summary>
    /// Tests setters of the <see cref="PageSetup.CharactersPerLine"/> and <see cref="PageSetup.LinesPerPage"/>
    /// properties.
    /// </summary>
    @Test
    public void testSettingDocumentGridOptions() throws Exception
    {
        Document doc = new Document();

        PageSetup pageSetup = doc.getFirstSection().getPageSetup();
        pageSetup.setLayoutMode(SectionLayoutMode.GRID);
        pageSetup.setCharactersPerLine(32);
        pageSetup.setLinesPerPage(28);
        checkDocumentGrid(doc.getFirstSection(), SectionLayoutMode.GRID, 32, 28);

        pageSetup.setTextOrientation(TextOrientation.DOWNWARD);
        pageSetup.setCharactersPerLine(32);
        pageSetup.setLinesPerPage(25);
        checkDocumentGrid(doc.getFirstSection(), SectionLayoutMode.GRID, 32, 25);
    }

    /// <summary>
    /// Checks document grid options of the specified section.
    /// </summary>
    private static void checkDocumentGrid(Section section, /*SectionLayoutMode*/int expectedGridType,
        int expectedCharsPerLine, int expectedLinesPerPage)
    {
        PageSetup pageSetup = section.getPageSetup();
        msAssert.areEqual(expectedGridType, pageSetup.getLayoutMode());
        msAssert.areEqual(expectedCharsPerLine, pageSetup.getCharactersPerLine());
        msAssert.areEqual(expectedLinesPerPage, pageSetup.getLinesPerPage());
    }

    /// <summary>
    /// WORDSNET-16349 The PageSetup.ContentXXX properties are changed to use 
    /// the <see cref="PageMarginCalculator"/> object that performs more correct calculation 
    /// of margin size including gutter.
    /// </summary>
    @Test
    public void testMarginCalculation() throws Exception
    {
        Document doc = TestUtil.open("Model\\Page\\TestMarginCalculation.docx");
        checkContent(doc.getFirstSection().getPageSetup(), 56.7, 85.05, 113.4, 198.45);
        checkContent(doc.getLastSection().getPageSetup(), 56.7, 56.7, 198.45, 113.4);

        // Check shape coordinates that are calculated as percent of content size.
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        final double DELTA = 0.001d;
        msAssert.areEqual(51.03, DELTA, shape.getLeft());
        msAssert.areEqual(102.06, DELTA, shape.getTop());
    }

    /// <summary>
    /// WORDSNET-19250Issues with PaperSize.Ledger and PaperSize.Quarto.
    /// Paper sizes are incorrect. Corrected values for paper sizes.
    /// </summary>
    @Test (dataProvider = "test19250DataProvider")
    public void test19250(/*PaperSize*/int paperSize, float expectedWidth, float expectedHeight) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.getPageSetup().setPaperSize(paperSize);
        
        msAssert.areEqual(expectedWidth, msSizeF.getWidth(builder.getPageSetup().getPageSize()));
        msAssert.areEqual(expectedHeight, msSizeF.getHeight(builder.getPageSetup().getPageSize()));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19250DataProvider")
	public static Object[][] test19250DataProvider() throws Exception
	{
		return new Object[][]
		{
			{PaperSize.LEDGER,  1224.0f,  792.0f},
			{PaperSize.QUARTO,  609.849976f,  779.75f},
			{PaperSize.FOLIO,  612.0f,  936.0f},
		};
	}

    /// <summary>
    /// WORDSNET-20656 A generic error occurred in GDI+ when converting DOCX to PNG. 
    /// Incorrect (bigger than Word's limit) page sizes now truncated on validation.
    /// </summary>
    @Test
    public void test20656() throws Exception
    {
        Document doc = TestUtil.open("Model\\Page\\Test20656.docx");

        MemoryStream ms = new MemoryStream();
        try /*JAVA: was using*/
        {
            doc.save(ms, SaveFormat.DOCX);   // FOSS: was Png; the page-size truncation runs on document validation.
        }
        finally { if (ms != null) ms.close(); }

        final double MAXIMUM_SIZE = 3052.8;

        msAssert.areEqual(MAXIMUM_SIZE, doc.getFirstSection().getPageSetup().getPageWidth());
        msAssert.areEqual(MAXIMUM_SIZE, doc.getFirstSection().getPageSetup().getPageHeight());
    }

    /// <summary>
    /// Checks ContentXXX properties of the specified page setup object.
    /// </summary>
    private void checkContent(PageSetup pageSetup, double expectedLeft, 
        double expectedTop, double expectedRight, double expectedBottom)
    {
        final double DELTA = 0.001d;
        msAssert.areEqual(expectedLeft, DELTA, pageSetup.getContentLeft());
        msAssert.areEqual(expectedTop, DELTA, pageSetup.getContentTop());
        msAssert.areEqual(expectedRight, DELTA, pageSetup.getContentRight());
        msAssert.areEqual(expectedBottom, DELTA, pageSetup.getContentBottom());
    }

    /// <summary>
    /// WORDSNET-870 TextColumns.SetCount does not work when setting count lesser than current.
    /// Forum thread: 'textcolumns count changes not working'
    /// https://www.aspose.com/Community/Forums/44376/ShowPost.aspx
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDefect870(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
    
        builder.getFont().setSize(36.0);

        // write text to first section of document
        builder.write("This text should be in 1 column.");

        // insert new section
        builder.insertBreak(BreakType.SECTION_BREAK_CONTINUOUS);

        // set number of columns for this section to 3
        builder.getPageSetup().getTextColumns().setCount(3);

        // write text to 3-column section
        builder.write("This text should be in 3 columns.");
        
        // insert new section
        builder.insertBreak(BreakType.SECTION_BREAK_CONTINUOUS);

        // set number of columns for this section to 1
        builder.getPageSetup().getTextColumns().setCount(1);

        // write text
        builder.write("This text shoudl be in 1 column.");

        TestUtil.saveOpen(builder.getDocument(), "Model\\Page\\TestDefect870", lf, sf);

        // check the actual number of columns for the last section
        msAssert.areEqual(1, builder.getPageSetup().getTextColumns().getCount());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefect870SourceDataProvider")
	public void unifiedTestDefect870Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefect870(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefect870SourceDataProvider")
	Object[][] unifiedTestDefect870SourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// WORDSNET-7689 Support Book Fold multiple page setup.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestJira7689(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        PageSetup ps = doc.getFirstSection().getPageSetup();

        // Check defaults.
        msAssert.areEqual(MultiplePagesType.DEFAULT, ps.getMultiplePages());
        msAssert.areEqual(0, ps.getSheetsPerBooklet());

        // Set BookFoldPrinting and SheetsPerBooklet.
        ps.setMultiplePages(MultiplePagesType.BOOK_FOLD_PRINTING);
        ps.setSheetsPerBooklet(4);
        doc = TestUtil.saveOpen(doc, "Model\\Page\\TestJira7689A", TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD);
        ps = doc.getFirstSection().getPageSetup();
        msAssert.areEqual(MultiplePagesType.BOOK_FOLD_PRINTING, ps.getMultiplePages());
        msAssert.areEqual(4, ps.getSheetsPerBooklet());

        // Set BookFoldPrintingReverse.
        ps.setMultiplePages(MultiplePagesType.BOOK_FOLD_PRINTING_REVERSE);
        ps.setSheetsPerBooklet(6);
        doc = TestUtil.saveOpen(doc, "Model\\Page\\TestJira7689B", TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD);
        ps = doc.getFirstSection().getPageSetup();
        msAssert.areEqual(MultiplePagesType.BOOK_FOLD_PRINTING_REVERSE, ps.getMultiplePages());
        msAssert.areEqual(6, ps.getSheetsPerBooklet());

        // Set MirrorMargins, in this case SheetsPerBooklet should not be written.
        ps.setMultiplePages(MultiplePagesType.MIRROR_MARGINS);
        ps.setSheetsPerBooklet(6);
        doc = TestUtil.saveOpen(doc, "Model\\Page\\TestJira7689C", TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD);
        ps = doc.getFirstSection().getPageSetup();
        msAssert.areEqual(MultiplePagesType.MIRROR_MARGINS, ps.getMultiplePages());
        msAssert.areEqual(0, ps.getSheetsPerBooklet());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJira7689SourceDataProvider")
	public void unifiedTestJira7689Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJira7689(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJira7689SourceDataProvider")
	Object[][] unifiedTestJira7689SourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// WORDSNET-10869 Introduced page number formatting options.
    /// </summary>
    @Test
    public void test10869() throws Exception
    {
        Document document = new Document();

        PageSetup pageSetup = document.getFirstSection().getPageSetup();
        pageSetup.setPageNumberStyle(NumberStyle.UPPERCASE_ROMAN);
        pageSetup.setChapterPageSeparator(ChapterPageSeparator.COLON);
        pageSetup.setHeadingLevelForChapter(1);

        document = TestUtil.saveOpen(document, "Model\\Page\\Test10869.docx");

        pageSetup = document.getFirstSection().getPageSetup();
        msAssert.areEqual(NumberStyle.UPPERCASE_ROMAN, pageSetup.getPageNumberStyle());
        msAssert.areEqual(ChapterPageSeparator.COLON, pageSetup.getChapterPageSeparator());
        msAssert.areEqual(1, pageSetup.getHeadingLevelForChapter());
    }

    /// <summary>
    /// WORDSNET-23931 Margins property added.
    /// Checks obtaining preset Margins from PageSetup.
    /// </summary>
    @Test
    public void testGetMargins() throws Exception
    {
        SectPr.setTestMode(false);
        String currentCulture = SystemPal.getCurrentCultureName();
        SystemPal.setCulture("en-US");

        Document doc = TestUtil.open("Model\\Page\\TestMargins.docx");

        msAssert.areEqual(Margins.NORMAL, doc.getSections().get(0).getPageSetup().getMargins());
        msAssert.areEqual(Margins.NARROW, doc.getSections().get(1).getPageSetup().getMargins());
        msAssert.areEqual(Margins.MODERATE, doc.getSections().get(2).getPageSetup().getMargins());
        msAssert.areEqual(Margins.WIDE, doc.getSections().get(3).getPageSetup().getMargins());
        msAssert.areEqual(Margins.MIRRORED, doc.getSections().get(4).getPageSetup().getMargins());
        msAssert.areEqual(Margins.CUSTOM, doc.getSections().get(5).getPageSetup().getMargins());

        SystemPal.setCulture(currentCulture);
        SectPr.setTestMode(true);
    }

    /// <summary>
    /// WORDSNET-23931 Margins property added.
    /// Checks applying preset Margins to PageSetup.
    /// </summary>
    @Test (dataProvider = "testSetMarginsDataProvider")
    public void testSetMargins(/*Margins*/int margins, int left, int right, int top, int bottom) throws Exception
    {
        SectPr.setTestMode(false);
        String currentCulture = SystemPal.getCurrentCultureName();
        SystemPal.setCulture("en-US");

        Document doc = new Document();

        doc.getFirstSection().getPageSetup().setMargins(margins);
        msAssert.areEqual(left, doc.getFirstSection().getSectPr().getLeftMargin());
        msAssert.areEqual(right, doc.getFirstSection().getSectPr().getRightMargin());
        msAssert.areEqual(top, doc.getFirstSection().getSectPr().getTopMargin());
        msAssert.areEqual(bottom, doc.getFirstSection().getSectPr().getBottomMargin());
        if (margins == Margins.MIRRORED)
            msAssert.areEqual(MultiplePagesType.MIRROR_MARGINS, doc.getDocPr().MultiplePages);
        SystemPal.setCulture(currentCulture);
        SectPr.setTestMode(true);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSetMarginsDataProvider")
	public static Object[][] testSetMarginsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{Margins.NORMAL,  1440,  1440,  1440,  1440},
			{Margins.NARROW,  720,  720,  720,  720},
			{Margins.MODERATE,  1080,  1080,  1440,  1440},
			{Margins.WIDE,  2880,  2880,  1440,  1440},
			{Margins.MIRRORED,  1800,  1440,  1440,  1440},
		};
	}

    // FOSS: Test22749 removed — it inferred PaperSize from EditingLanguage while loading an HTML file
    // (a format with no page setup); HTML load was removed, so the inference scenario can't be set up.

    /// <summary>
    /// WORDSNET-27516 Add JISB4, JISB5 sizes to PaperSize.
    /// Testing new paper sizes.
    /// </summary>
    @Test (dataProvider = "test27516DataProvider")
    public void test27516(/*PaperSize*/int paperSize, int expectedWidth, int expectedHeight) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.getPageSetup().setPaperSize(paperSize);

        Section sect = builder.getDocument().getFirstSection();
        msAssert.areEqual(expectedWidth, sect.getSectPr().get(SectAttr.PAGE_WIDTH));
        msAssert.areEqual(expectedHeight, sect.getSectPr().get(SectAttr.PAGE_HEIGHT));
        msAssert.areEqual(paperSize, sect.getPageSetup().getPaperSize());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test27516DataProvider")
	public static Object[][] test27516DataProvider() throws Exception
	{
		return new Object[][]
		{
			{PaperSize.JIS_B_4,  14570,  20636},
			{PaperSize.JIS_B_5,  10318,  14570},
		};
	}
}

