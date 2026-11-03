// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/02/2009 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.System.msMath;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Drawing.RectangleF;
import org.testng.annotations.DataProvider;


/// <summary>
/// Test that OOXML ISO/IEC 29500 Transitional compliance level files are opened correctly.
/// Also test that when saving to pre-ISO29500 formats all the newer stuff is not written.
/// </summary>
@Test
public class TestIso29500Transitional
{

    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }


    /// <summary>
    /// Test that <see cref="DocxXmlReader.ConvertUniversalMeasure"/> works.
    /// </summary>
    @Test (dataProvider = "testUniversalMeasureReaderDataProvider")
    public void testUniversalMeasureReader(String inputValue, int targetUnit, double expectedResult,
        int expectedCompliance)
    {
        OoxmlComplianceInfo complianceInfo = new OoxmlComplianceInfo();
        double actual = NrxXmlReader.convertUniversalMeasure(inputValue, (/*NrxUnit*/int)targetUnit, complianceInfo);
        msAssert.areEqual(msMath.round(expectedResult, 3), msMath.round(actual, 3));
        msAssert.areEqual((/*OoxmlComplianceCore*/int)expectedCompliance, complianceInfo.getCompliance());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testUniversalMeasureReaderDataProvider")
	public static Object[][] testUniversalMeasureReaderDataProvider()
	{
		return new Object[][]
		{
			{"1",  (int)NrxUnit.HALF_POINTS,  1d,  (int)OoxmlComplianceCore.ECMA_376},
			{"1",  (int)NrxUnit.TWIPS,  1d,  (int)OoxmlComplianceCore.ECMA_376},
			{"1",  (int)NrxUnit.HUNDREDTHS_OF_POINT,  1d,  (int)OoxmlComplianceCore.ECMA_376},
			{"1",  (int)NrxUnit.EMUS,  1d,  (int)OoxmlComplianceCore.ECMA_376},
			{"1pt",  (int)NrxUnit.HALF_POINTS,  2d,  (int)OoxmlComplianceCore.ISO_TRANSITIONAL},
			{"1pt",  (int)NrxUnit.TWIPS,  20d,  (int)OoxmlComplianceCore.ISO_TRANSITIONAL},
			{"1pt",  (int)NrxUnit.HUNDREDTHS_OF_POINT,  100d,  (int)OoxmlComplianceCore.ISO_TRANSITIONAL},
			{"1pt",  (int)NrxUnit.EMUS,  12700d,  (int)OoxmlComplianceCore.ISO_TRANSITIONAL},
			{"1in",  (int)NrxUnit.HALF_POINTS,  144d,  (int)OoxmlComplianceCore.ISO_TRANSITIONAL},
			{"0.5in",  (int)NrxUnit.TWIPS,  720d,  (int)OoxmlComplianceCore.ISO_TRANSITIONAL},
			{"1in",  (int)NrxUnit.HUNDREDTHS_OF_POINT,  7200d,  (int)OoxmlComplianceCore.ISO_TRANSITIONAL},
			{"1in",  (int)NrxUnit.EMUS,  914400d,  (int)OoxmlComplianceCore.ISO_TRANSITIONAL},
			{"2.54cm",  (int)NrxUnit.HALF_POINTS,  144d,  (int)OoxmlComplianceCore.ISO_TRANSITIONAL},
			{"127mm",  (int)NrxUnit.TWIPS,  7200d,  (int)OoxmlComplianceCore.ISO_TRANSITIONAL},
			{"2.54cm",  (int)NrxUnit.HUNDREDTHS_OF_POINT,  7200d,  (int)OoxmlComplianceCore.ISO_TRANSITIONAL},
			{"25.4mm",  (int)NrxUnit.EMUS,  914400d,  (int)OoxmlComplianceCore.ISO_TRANSITIONAL},
			{"24pc",  (int)NrxUnit.HALF_POINTS,  576d,  (int)OoxmlComplianceCore.ISO_TRANSITIONAL},
			{"24pi",  (int)NrxUnit.TWIPS,  5760d,  (int)OoxmlComplianceCore.ISO_TRANSITIONAL},
			{"10pc",  (int)NrxUnit.HUNDREDTHS_OF_POINT,  12000d,  (int)OoxmlComplianceCore.ISO_TRANSITIONAL},
			{"10pi",  (int)NrxUnit.EMUS,  1524000d,  (int)OoxmlComplianceCore.ISO_TRANSITIONAL},
		};
	}

    /// <summary>
    /// Tests that <see cref="DmlAdjustableCoordinate"/> supports values in format defined
    /// by the ST_UniversalMeasure simple type.
    /// </summary>
    @Test (dataProvider = "testUniversalMeasureInAdjustableCoordinateDataProvider")
    public void testUniversalMeasureInAdjustableCoordinate(String value, double expectedResult)
    {
        DmlAdjustableCoordinate coordinate = new DmlAdjustableCoordinate(value);
        msAssert.areEqual(msMath.round(expectedResult), msMath.round(coordinate.getValue(null)), msString.format(                "Wrong conversion of {0}.", value));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testUniversalMeasureInAdjustableCoordinateDataProvider")
	public static Object[][] testUniversalMeasureInAdjustableCoordinateDataProvider()
	{
		return new Object[][]
		{
			{"1pt",  12700d},
			{"-0.01in",  -9144d},
			{"10",  10d},
		};
	}

    /// <summary>
    /// Tests that <see cref="DmlTextBoxRect"/> supports values in format defined
    /// by the ST_UniversalMeasure simple type.
    /// </summary>
    @Test (dataProvider = "testUniversalMeasureInRectDataProvider")
    public void testUniversalMeasureInRect(String left, String top, String right, String bottom,
        float expectedOutputL, float expectedOutputT, float expectedOutputR, float expectedOutputB)
    {
        DmlTextBoxRect textBoxRect = new DmlTextBoxRect(left, top, right, bottom);
        RectangleF rect = textBoxRect.getRectangle(null, false);

        msAssert.areEqual(msMath.round(expectedOutputL), msMath.round(rect.getLeft()), msString.format(                "Wrong left coordinate (input value: {0}).", left));
        msAssert.areEqual(msMath.round(expectedOutputT), msMath.round(rect.getTop()), msString.format(                "Wrong top coordinate (input value: {0}).", top));
        msAssert.areEqual(msMath.round(expectedOutputR), msMath.round(rect.getRight()), msString.format(                "Wrong right coordinate (input value: {0}).", right));
        msAssert.areEqual(msMath.round(expectedOutputB), msMath.round(rect.getBottom()), msString.format(                "Wrong bottom coordinate (input value: {0}).", bottom));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testUniversalMeasureInRectDataProvider")
	public static Object[][] testUniversalMeasureInRectDataProvider()
	{
		return new Object[][]
		{
			{"1pt",  "-0.1in",  "30000",  "5.08mm",  12700f,  -91440f,  30000f,  182880f},
		};
	}


    private static void verifyDocProtectionProperties(Document doc)
    {
        msAssert.areEqual(4, doc.getDocumentProtection().getPasswordHash().CryptAlgorithmSid);
        msAssert.areEqual(100000, doc.getDocumentProtection().getPasswordHash().CryptSpinCount);
    }

    private static void verifyCustomCompatSettings(Document doc, int nSettings)
    {
        msAssert.areEqual(nSettings, doc.getCompatibilityOptions().getCustomCompatibilitySettings().getCount());
        msAssert.areEqual("compatibilityMode", doc.getCompatibilityOptions().getCustomCompatibilitySettings().get(0).getName());
        msAssert.areEqual("http://schemas.microsoft.com/office/word", doc.getCompatibilityOptions().getCustomCompatibilitySettings().get(0).getUri());

        msAssert.areEqual("doNotFlipMirrorIndents", doc.getCompatibilityOptions().getCustomCompatibilitySettings().get(3).getName());
        msAssert.areEqual("http://schemas.microsoft.com/office/word", doc.getCompatibilityOptions().getCustomCompatibilitySettings().get(3).getUri());
        msAssert.areEqual("1", doc.getCompatibilityOptions().getCustomCompatibilitySettings().get(3).getValue());
    }


    private static void verifyStylePaneFormatFilterSettings(Document readDoc)
    {
        msAssert.areEqual(38696, readDoc.getDocPr().StylePaneFormatFilterSettings.getData());
        msAssert.isFalse(readDoc.getDocPr().StylePaneFormatFilterSettings.getAllStyles());
        msAssert.isFalse(readDoc.getDocPr().StylePaneFormatFilterSettings.getCustomStyles());
        msAssert.isFalse(readDoc.getDocPr().StylePaneFormatFilterSettings.getLatentStyles());
        msAssert.isTrue(readDoc.getDocPr().StylePaneFormatFilterSettings.getStylesInUse());
        msAssert.isTrue(readDoc.getDocPr().StylePaneFormatFilterSettings.getHeadingStyles());
        msAssert.isFalse(readDoc.getDocPr().StylePaneFormatFilterSettings.getNumberingStyles());
        msAssert.isFalse(readDoc.getDocPr().StylePaneFormatFilterSettings.getTableStyles());

        msAssert.isTrue(readDoc.getDocPr().StylePaneFormatFilterSettings.getDirectFormattingOnNumbering());
        msAssert.isTrue(readDoc.getDocPr().StylePaneFormatFilterSettings.getDirectFormattingOnParagraphs());
        msAssert.isTrue(readDoc.getDocPr().StylePaneFormatFilterSettings.getDirectFormattingOnRuns());
        msAssert.isFalse(readDoc.getDocPr().StylePaneFormatFilterSettings.getDirectFormattingOnTables());

        msAssert.isTrue(readDoc.getDocPr().StylePaneFormatFilterSettings.getClearFormatting());
        msAssert.isFalse(readDoc.getDocPr().StylePaneFormatFilterSettings.getTop3HeadingStyles());
        msAssert.isFalse(readDoc.getDocPr().StylePaneFormatFilterSettings.getVisibleStyles());
        msAssert.isTrue(readDoc.getDocPr().StylePaneFormatFilterSettings.getAlternateStyleNames());
    }



    private static void verifyNewBorderArtEnumMembers(Document readDoc)
    {
        msAssert.isTrue(readDoc.getSections().get(0).getSectPr().getBorderLeft().isPageBorderArt());
        msAssert.areEqual((/*LineStyle*/int)PageBorderArt.TRIANGLE_CIRCLE_2, readDoc.getSections().get(0).getSectPr().getBorderTop().getLineStyle());
        msAssert.areEqual((/*LineStyle*/int)PageBorderArt.TRIANGLE_CIRCLE_2, readDoc.getSections().get(0).getSectPr().getBorderRight().getLineStyle());
        msAssert.areEqual((/*LineStyle*/int)PageBorderArt.TRIANGLE_CIRCLE_2, readDoc.getSections().get(0).getSectPr().getBorderBottom().getLineStyle());
    }

    /// <summary>
    /// Verify that new table elements: title and description are read and saved correctly.
    /// </summary>
    @Test
    public void testNewTableElements() throws Exception
    {
        final String TEST_NAME = "ImportIso29500T\\TestTableTitleDescription.docx";

        Document readDoc = TestUtil.open(TEST_NAME);
        verifyNewTableElements(readDoc);

        Document writtenDoc = saveIso29500Open(readDoc, TEST_NAME);
        verifyNewTableElements(writtenDoc);
    }

    private static void verifyNewTableElements(Document doc)
    {
        msAssert.areEqual("Table title. ", doc.getSections().get(0).getBody().getTables().get(0).getTitle());
        msAssert.areEqual("Table descriptions.", doc.getSections().get(0).getBody().getTables().get(0).getDescription());
    }

    /// <summary>
    /// Some of the rPr elements can now be present in newer format: points, inches etc.
    /// Test that this can be read properly. Write tests are not required since we will write as usual.
    /// </summary>
    @Test
    public void testNewPrNumericFormat() throws Exception
    {
        final String TEST_NAME = "ImportIso29500T\\TestrPrNewNumericFormats.docx";
        Document readDoc = TestUtil.open(TEST_NAME);
        msAssert.areEqual(27 * 12 * 2, readDoc.getSections().get(0).getBody().getFirstParagraph().getRuns().get(0).getRunPr().getSize());
        msAssert.areEqual(12 * 2, readDoc.getSections().get(0).getBody().getFirstParagraph().getRuns().get(0).getRunPr().getSizeBi());
        msAssert.areEqual(-24, readDoc.getSections().get(0).getBody().getFirstParagraph().getRuns().get(0).getRunPr().getPosition());
    }


    /// <summary>
    /// Combined test for testing reading ISO29500 new stuff related to formatting properties:
    /// - tblLook new attributes,
    /// - tcBorders start/end
    /// </summary>
    @Test
    public void testTableStartEndTblLook() throws Exception
    {
        final String TEST_NAME = "ImportIso29500T\\TestTableStartEndTblLook.docx";
        Document readDoc = TestUtil.open(TEST_NAME);

        msAssert.areEqual(TableStyleOptions.FIRST_ROW | TableStyleOptions.FIRST_COLUMN | TableStyleOptions.ROW_BANDS, (readDoc.getSections().get(0).getBody().getTables().get(0).getRows().get(0).getTablePr().getStyleOptions()));

        CellPr cellProperties = readDoc.getSections().get(0).getBody().getTables().get(0).getRows().get(0).getCells().get(0).getCellPr();
        msAssert.areEqual(LineStyle.DOUBLE_WAVE, cellProperties.getBorderLeft().getLineStyle());
        msAssert.areEqual(LineStyle.SINGLE, cellProperties.getBorderRight().getLineStyle());

        cellProperties = readDoc.getSections().get(0).getBody().getTables().get(0).getRows().get(1).getCells().get(0).getCellPr();
        msAssert.areEqual(LineStyle.DOUBLE_WAVE, cellProperties.getBorderRight().getLineStyle());
    }

    /// <summary>
    /// Verify that optimize for browser setting is read and written.
    /// </summary>
    @Test
    public void testOptimizeForBrowser() throws Exception
    {
        final String TEST_NAME = "ImportIso29500T\\TestOptimizeForBrowserAndPercentage.docx";
        Document readDoc = TestUtil.open(TEST_NAME);
        msAssert.areEqual(WebTarget.XHTML_PLUS_CSS_2, readDoc.getDocPr()._WebTarget);

        Document iso29500loaded = saveIso29500Open(readDoc, TEST_NAME);
        msAssert.areEqual(WebTarget.XHTML_PLUS_CSS_2, iso29500loaded.getDocPr()._WebTarget);

        Document isoECMA376loaded = savePreIso29500Open(readDoc, TEST_NAME);
        msAssert.areEqual(WebTarget.NONE, isoECMA376loaded.getDocPr()._WebTarget);
    }

    /// <summary>
    /// Test transitional feature 9.10.8 ST_UnqualifiedPercentage, and verify
    /// that qualified e.g. with "%" sign, and unqualified percentage values are read
    /// correctly.
    /// </summary>
    @Test
    public void testPercentage() throws Exception
    {
        final String TEST_NAME = "ImportIso29500T\\TestOptimizeForBrowserAndPercentage.docx";
        Document readDoc = TestUtil.open(TEST_NAME);
        msAssert.areEqual(PreferredWidth.fromPercent(100.0).getValue(), 0.0001, readDoc.getSections().get(0).getBody().getTables().get(0).getRows().get(0).getTablePr().getPreferredWidth().getValue());
        msAssert.areEqual(PreferredWidth.fromPercent(30.0).getValue(), 0.0001, readDoc.getSections().get(0).getBody().getTables().get(0).getRows().get(0).getCells().get(0).getCellPr().getPreferredWidth().getValue());
    }

    /// <summary>
    /// Verify that keywords are read ok in doc core properties package.
    /// </summary>
    @Test
    public void testKeywords() throws Exception
    {
        final String KEYWORD_STRING = "test, document, aspose, docx, iso29500, transitional, strict";

        Document readDoc = TestUtil.open("ImportIso29500T\\TestKeywords.docx");
        msAssert.areEqual(KEYWORD_STRING, readDoc.getBuiltInDocumentProperties().getKeywords());

        Document readOlderDoc = TestUtil.open("ImportIso29500T\\TestOlderKeywords.docx");
        msAssert.areEqual(KEYWORD_STRING, readOlderDoc.getBuiltInDocumentProperties().getKeywords());


        Document readCombinedKeywords = TestUtil.open("ImportIso29500T\\TestCombinedKeywords.docx");
        msAssert.areEqual(KEYWORD_STRING, readCombinedKeywords.getBuiltInDocumentProperties().getKeywords());
    }


    /// <summary>
    /// Test cloning document with AlternateContent (clone AlternateContent complex attribute).
    /// </summary>
    @Test
    public void alternateContentClone() throws Exception
    {
        Document doc = TestUtil.open("ImportIso29500T\\AlternateContentClone.docx");
        Shape originalDrawingMl = (Shape)doc.getChild(NodeType.SHAPE, 0, true);

        Document clonedDocument = (Document)doc.deepClone(true);
        Shape clonedDrawingMl = (Shape)clonedDocument.getChild(NodeType.SHAPE, 0, true);

        msAssert.areNotSame(originalDrawingMl.getRunPr().getAlternateContent(), clonedDrawingMl.getRunPr().getAlternateContent());

        Shape originalFallBackShape = (Shape) originalDrawingMl.getRunPr().getAlternateContent().getFallBack().getFirstChild();
        Shape clonedFallBackShape = (Shape) originalDrawingMl.getRunPr().getAlternateContent().getFallBack().getFirstChild().deepClone(true);
        msAssert.areNotSame(originalFallBackShape, clonedFallBackShape);
    }


    /// <summary>
    /// WORDSNET-6139 Open XML SDK 2.0 Doesn't validate the DOCX generated by Aspose.Words.
    /// 1. Exclude scriptAnchor from picture for Docx format, absolute for tblpXSpec and tblpYSpec.
    /// 2. Write prop lang for noLineBreaksAfter and noLineBreaksBefore (Custom Set of Characters
    /// Which Cannot End a Line, Custom Set Of Characters Which Cannot Begin A Line) part 1 reference c059575_ISO_IEC_29500-1_2011.
    /// 3. MaxLength in txextInput is Int16
    /// 4. Write lnNumType as in spec (17.6.8 lnNumType (Line Numbering Settings) part 1 reference c059575_ISO_IEC_29500-1_2011.
    /// 5. Header and footer in pgMar - UInt32.
    /// 6. Write anchorx , anchory as in spec.
    /// </summary>
    /// <remarks>
    /// If this test broke, check Out file using x:\awnet\Aspose.Foundation\Tools\DocumentValidator.
    /// </remarks>
    @Test
    public void testJira6139() throws Exception
    {
        TestUtil.openSaveOpen("ImportIso29500T\\TestJira6139.docx");
    }

    /// <summary>
    /// andrnosk: WORDSNET-6710 Unable to access SDT nodes from inside DrawingML.
    /// Fixed by adding mechanism to check whether Choice contains unsupported elements,
    /// if contains Fallback will be read instead.
    /// </summary>
    @Test
    public void testJira6710() throws Exception
    {
        Document doc = TestUtil.open("ImportIso29500T\\TestJira6710.docx");
        msAssert.isTrue(doc.getComplianceInfo().isDrawingExtensions());
        msAssert.areEqual(4, doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).getCount());
    }

    /// <summary>
    /// Saves ISO 29500 transitional docx and then opens it without gold comparison.
    /// Returns opened document.
    /// </summary>
    private static Document saveIso29500Open(Document readDoc, String testName) throws Exception
    {
        OoxmlSaveOptions opt = new OoxmlSaveOptions();
        opt.setCompliance(OoxmlCompliance.ISO_29500_2008_TRANSITIONAL);
        String outFileName = TestUtil.save(readDoc, testName, opt, false);
        return TestUtil.open(outFileName);
    }

    private static Document saveRtfOpen(Document readDoc, String testName) throws Exception
    {
        String srcFileName = TestUtil.buildTestFileName(testName);
        String outFileName = TestUtil.buildOutFileName(srcFileName, "", SaveFormat.RTF);
        TestUtil.save(readDoc, outFileName);
        return TestUtil.open(outFileName);
    }

    /// <summary>
    /// Saves pre-ISO29500 format docx and then opes it without gold comparison.
    /// Allows to verify that all new stuff is lost during saves to older formats.
    /// </summary>
    private static Document savePreIso29500Open(Document readDoc, String testName) throws Exception
    {
        OoxmlSaveOptions opt = new OoxmlSaveOptions();
        opt.setCompliance(OoxmlCompliance.ECMA_376_2006);
        String outFileName = TestUtil.save(readDoc, testName, opt, false);
        return TestUtil.open(outFileName);
    }
}

