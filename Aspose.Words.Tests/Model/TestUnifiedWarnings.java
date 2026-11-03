// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/03/2012 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.Map;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.MemoryStream;
import org.testng.annotations.DataProvider;


/// <summary>
/// Test suite to verify unified Warnings.
/// Presently this test suite includes only tests for Load and Save operations across all Conversions modules.
/// </summary>
/// <remarks>
/// Tests related to rendering, layout and other modules except Conversions are scattered across other test suites and are not tested here.
/// </remarks>
@Test
public class TestUnifiedWarnings
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }




    /// <summary>
    /// Tests that the compatibility mode hint is generated on saving a document with calling
    /// the <see cref="CompatibilityOptions.OptimizeFor"/> method for MS Word 2010 and higher compatibility
    /// if OOXML format is ECMA-376.
    /// </summary>
    @Test
    public void testCompatibilityModeHint() throws Exception
    {
        Document doc = new Document();
        doc.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2010);

        TestWarningCallback warningCallback = new TestWarningCallback();
        doc.setWarningCallback(warningCallback);

        TestUtil.executeValidator(doc, OoxmlSaveOptions.docxWithCompliance(OoxmlComplianceCore.ECMA_376));

        int warningCount = warningCallback.getCount();
        msAssert.isTrue(warningCount > 0);
        msAssert.areEqual(WarningStrings.HINT_TO_REMOVE_COMPATIBILITY_MODE, warningCallback.get(warningCount - 1).getDescription());

        warningCallback.clear();
        TestUtil.executeValidator(doc, OoxmlSaveOptions.docxWithCompliance(OoxmlComplianceCore.ISO_TRANSITIONAL));

        msAssert.areEqual(warningCount - 1, warningCallback.getCount());
        if (warningCallback.getCount() > 0)
            msAssert.areNotEqual(WarningStrings.HINT_TO_REMOVE_COMPATIBILITY_MODE, warningCallback.get(warningCallback.getCount() - 1).getDescription());
    }

    /// <summary>
    /// WORDSNET-13779 Provide IWarningCallback mechanism for embedding font functionality.
    /// Tests that there is a warning about fonts are not embedded when <see cref="DocPr.EmbedSystemFonts"/>
    /// or <see cref="DocPr.SaveSubsetFonts"/> is set to 'true', because
    /// <see cref="DocPr.EmbedTrueTypeFonts"/> is set to false.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf save were removed.
    @Test (dataProvider = "testWarningWhenEmbedFontsOptionDisabledDataProvider")
    public void testWarningWhenEmbedFontsOptionDisabled(/*SaveFormat*/int sf) throws Exception
    {
        // Checks when 'EmbedSystemFonts' enabled.
        testWarningWhenEmbedFontsOptionDisabledCore(true, false, sf);

        // Checks when 'SaveSubsetFonts' enabled.
        testWarningWhenEmbedFontsOptionDisabledCore(false, true, sf);

        // Checks when both enabled.
        testWarningWhenEmbedFontsOptionDisabledCore(true, true, sf);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testWarningWhenEmbedFontsOptionDisabledDataProvider")
	public static Object[][] testWarningWhenEmbedFontsOptionDisabledDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// WORDSNET-13779 Provide IWarningCallback mechanism for embedding font functionality.
    /// Tests that there is no a warning <see cref="WarningStrings.FontsNotEmbeddedDueToEmbeddingDisabled"/>
    /// when all font embedding options are set to 'false'.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf save were removed.
    @Test (dataProvider = "testNoWarningWhenAllEmbedFontOptionsDisabledDataProvider")
    public void testNoWarningWhenAllEmbedFontOptionsDisabled(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = createTestDocumentWithEmbedFontsOptions(false, false, false, "Tahoma");
        TestWarningCallback warnings = saveDocumentWithWarningCallback(doc, SaveOptions.createSaveOptions(sf));
        msAssert.isFalse(containsWarning(warnings, WarningStrings.FONTS_NOT_EMBEDDED_DUE_TO_EMBEDDING_DISABLED));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testNoWarningWhenAllEmbedFontOptionsDisabledDataProvider")
	public static Object[][] testNoWarningWhenAllEmbedFontOptionsDisabledDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    // FOSS: TestWarningFontEmbeddingNotSupported (Odt/WordML only), TestWarningEmbeddingLargeFontNotUsedInText
    // and TestWarningEmbeddingLargeFontUsedInText removed — they assert font-embedding size/subset warnings,
    // but font-embedding OUTPUT (and subsetting) was removed, so those warnings are never produced.

    // FOSS: TestCellRevisionWarning removed — it verified the CellRevisionLost warning on saving to WordML,
    // a removed format.

    /// <summary>
    /// Tests that a warning is generated on saving a document that contains a table with defined
    /// <see cref="Table.Title"/> or <see cref="Table.Description"/> property.
    /// </summary>
    // FOSS: only Docx survives — Doc/WordML/Rtf/Odt save were removed.
    @Test (dataProvider = "testTableTitleAndDescriptionDataProvider")
    public void testTableTitleAndDescription(/*SaveFormat*/int format) throws Exception
    {
        SaveOptions options = SaveOptions.createSaveOptions(format);
        String formatAsString = SaveFormat.toString(format);
        if (format == SaveFormat.DOCX)
        {
            ((OoxmlSaveOptions)options).setCompliance(OoxmlCompliance.ECMA_376_2006);
            formatAsString = DocumentValidator.ECMA_376_FORMAT_TITLE;
        }

        Map<Integer, WarningInfo> toCheck = new HashMap<Integer, WarningInfo>();
        msDictionary.add(toCheck, 0, new WarningInfo(WarningType.DATA_LOSS, WarningSource.VALIDATOR,
            msString.format(WarningStrings.NOT_SUPPORTED_TABLE_TITLE, formatAsString)));
        msDictionary.add(toCheck, 1, new WarningInfo(WarningType.DATA_LOSS, WarningSource.VALIDATOR,
            msString.format(WarningStrings.NOT_SUPPORTED_TABLE_DESCRIPTION, formatAsString)));

        testSaveWarnings("ImportIso29500T\\TestTableTitleDescription.docx", -1, toCheck, options);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTableTitleAndDescriptionDataProvider")
	public static Object[][] testTableTitleAndDescriptionDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// WORDSNET-16830 Add warning if improper compatibility is set for LayoutInCell property.
    /// </summary>
    @Test
    public void testJira16830() throws Exception
    {
        final String FILE_NAME = "Model\\Shape\\Unsorted\\TestJira16546";
        Document doc = TestUtil.open(FILE_NAME, LoadFormat.DOCX);

        Shape shape = ms.as(doc.getChildNodes(NodeType.SHAPE, true).get(0), Shape.class);
        shape.isLayoutInCell(false);

        TestWarningCallback wc = new TestWarningCallback();
        doc.setWarningCallback(wc);

        doc.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2013);
        OoxmlSaveOptions saveOptions = new OoxmlSaveOptions();
        saveOptions.setSaveFormat(SaveFormat.DOCX);
        TestUtil.executeValidator(doc, saveOptions);
        WarningInfo wi = wc.get(0);
        msAssert.areEqual(1, wc.getCount());
        msAssert.areEqual(WarningSource.VALIDATOR, wi.getSource());
        msAssert.areEqual(WarningType.HINT, wi.getWarningType());
        msAssert.isTrue(wi.getDescription().contains("optimized for the version 2013 or higher"));
    }

    // FOSS: Test20932 removed — its input is a binary .doc (removed load format), and the unsupported-VML-color
    // warning it checked targets WordML/Doc output.

    /// <summary>
    /// WORDSNET-25162 Font style underline does not work.
    /// Tests generating a warning when TextPath.Underline for VML is being edited.
    /// </summary>
    // FOSS: Test25162 removed — it only saved to Doc/Rtf/WordML (all removed) and did not even assert the
    // resulting warning (the ContainsWarning result was unused).

    /// <summary>
    /// Opens document and checks that there is a warning with the
    /// specified <paramref name="source"/> and <paramref name="description"/>.
    /// </summary>
    private static void verifyOpenWarning(String fileName, /*WarningSource*/int source, String description) throws Exception
    {
        WarningInfoCollection warnings = new WarningInfoCollection();
        LoadOptions lo = new LoadOptions();
        lo.setWarningCallback(warnings);

        TestUtil.open(fileName, lo);
        msAssert.isTrue(TestUtil.containsWarningBySource(warnings, source, description), msString.format("Warning '{0}' missed.", description));
    }

    /// <summary>
    /// Saves document and checks that there is a warning with the specified
    /// <paramref name="type"/>, <paramref name="source"/> and <paramref name="description"/>.
    /// </summary>
    private static void verifySaveWarning(String fileName, /*WarningType*/int type, /*WarningSource*/int source, String description) throws Exception
    {
        verifySaveWarning(fileName, SaveFormat.DOC, type, source, description);
    }

    /// <summary>
    /// Saves document and checks that there is a warning with the specified
    /// <paramref name="type"/>, <paramref name="source"/> and <paramref name="description"/>.
    /// </summary>
    private static void verifySaveWarning(String fileName, /*SaveFormat*/int sf, /*WarningType*/int type, /*WarningSource*/int source, String description) throws Exception
    {
        Document doc = TestUtil.open(fileName);

        WarningInfoCollection warnings = new WarningInfoCollection();
        SaveOptions so = SaveOptions.createSaveOptions(sf);
        doc.setWarningCallback(warnings);

        doc.save(new MemoryStream(), so);
        msAssert.isTrue(TestUtil.containsWarning(warnings, type, source, description), msString.format("Warning '{0}' missed.", description));
    }

    /// <summary>
    /// Test that a model node of specified document that is not supported by Aspose.Words raises warning during export.
    /// </summary>
    /// <param name="documentPath">Test document path.</param>
    /// <param name="warningsCount">Expected warning count.</param>
    /// <param name="toCheck">List of warnings to check.</param>
    /// <param name="saveOptions">Save options for specified format.</param>
    private static void testSaveWarnings(String documentPath, int warningsCount,
        Map<Integer, WarningInfo> toCheck, SaveOptions saveOptions) throws Exception
    {
        Document doc = TestUtil.open(documentPath);
        TestWarningCallback warningCallback = saveDocumentWithWarningCallback(doc, saveOptions);

        testWarningsCore(warningCallback, warningsCount, toCheck);
    }

    /// <summary>
    /// Check warnings count, source, type and description.
    /// </summary>
    private static void testWarningsCore(TestWarningCallback warningCallback, int warningsCount,
        Map<Integer, WarningInfo> toCheck)
    {
        // Check warnings count for test document.
        if (warningsCount >= 0)
            msAssert.areEqual(warningsCount, warningCallback.getCount());

        for (Map.Entry<Integer, WarningInfo> entry : toCheck.entrySet())
        {
            WarningInfo warning = warningCallback.get(entry.getKey());
            WarningInfo expected = entry.getValue();

            msAssert.areEqual(expected.getSource(), warning.getSource());
            msAssert.areEqual(expected.getWarningType(), warning.getWarningType());
            msAssert.areEqual(expected.getDescription(), warning.getDescription());
        }
        warningCallback.clear();
    }

    /// <summary>
    /// Saves document with <see cref="TestWarningCallback"/> into temporary memory stream.
    /// </summary>
    private static TestWarningCallback saveDocumentWithWarningCallback(Document doc, SaveOptions so) throws Exception
    {
        TestWarningCallback warningCallback = new TestWarningCallback();
        doc.setWarningCallback(warningCallback);
        MemoryStream tempStream = new MemoryStream();
        try /*JAVA: was using*/
        {
            doc.save(tempStream, so);
        }
        finally { if (tempStream != null) tempStream.close(); }

        return warningCallback;
    }

    /// <summary>
    /// Returns true if <see cref="WarningInfo"/> with specified description exists in <paramref name="warnings"/>.
    /// </summary>
    private static boolean containsWarning(TestWarningCallback warnings, String warningDescription)
    {
        for (int i = 0; i < warnings.getCount(); i++)
            if (warnings.get(i).getDescription().equals(warningDescription))
                return true;

        return false;
    }

    /// <summary>
    /// Creates test document that contains text with the specified
    /// font and having specified font embedding options.
    /// </summary>
    private static Document createTestDocumentWithEmbedFontsOptions(boolean isEmbedFonts, boolean isEmbedSystem, boolean isSubset, String fontName) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getFont().setName(fontName);
        builder.write("RegularStyle");

        FontInfoCollection fontInfos = doc.getFontInfos();
        fontInfos.setEmbedTrueTypeFonts(isEmbedFonts);
        fontInfos.setEmbedSystemFonts(isEmbedSystem);
        fontInfos.setSaveSubsetFonts(isSubset);

        return doc;
    }

    /// <summary>
    /// Core method for <see cref="TestWarningWhenEmbedFontsOptionDisabled"/>.
    /// Creates and saves document for testing and checks that
    /// warning <see cref="WarningStrings.FontsNotEmbeddedDueToEmbeddingDisabled"/> exists.
    /// </summary>
    private static void testWarningWhenEmbedFontsOptionDisabledCore(boolean embedSystemFonts, boolean saveSubsetFonts, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = createTestDocumentWithEmbedFontsOptions(false, embedSystemFonts, saveSubsetFonts, "Tahoma");
        TestWarningCallback warnings = saveDocumentWithWarningCallback(doc, SaveOptions.createSaveOptions(sf));
        msAssert.isTrue(containsWarning(warnings, WarningStrings.FONTS_NOT_EMBEDDED_DUE_TO_EMBEDDING_DISABLED));
    }
}

