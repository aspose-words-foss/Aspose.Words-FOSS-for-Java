// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/02/2014 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests different aspects of Document.OptimizeFor method, which should instruct Model or DOCX export on how to process documents in order
/// to mimic all MSW version-specific behavior known to date.
/// </summary>
@Test
public class TestMsWordVersionSpecificBehavior
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// WORDSNET-4777 MS Word 2010 shows "Compatibility mode"on the title bar 
    /// during opening the document saved by Aspose.Words with Iso29500_2008_Transitional.
    /// Compatibility setting string should be written to suppress warning.
    /// </summary>
    @Test
    public void testDefect4777() throws Exception
    {
        // There was error in StylePaneFormatFilterSettings - flags set worked incorrectly. Test it's OK now.
        StylePaneFormatFilterSettings settings = new StylePaneFormatFilterSettings();
        settings.setAllStyles(true);
        msAssert.isTrue(settings.getAllStyles());
        settings.setAllStyles(false);
        msAssert.isFalse(settings.getAllStyles());

        // Customer test case.
        Document doc = new Document();
        OoxmlSaveOptions opt = new OoxmlSaveOptions(SaveFormat.DOCX);
        opt.setCompliance(OoxmlCompliance.ISO_29500_2008_TRANSITIONAL);
        opt.setTestMode();
        doc.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2010);
        // Verify against gold file.
        TestUtil.saveOpen(doc, "Model\\MswVersionSpecific\\TestDefect4777.docx", opt);
    }

    @Test
    public void testBuiltInStylesOptimized() throws Exception
    {
        Document doc = new Document();
        doc.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2000);
        msAssert.areEqual(8, doc.getStyles().get("Heading 1").getRunPr().getCount());

        doc = new Document();
        doc.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2010);
        msAssert.areEqual(10, doc.getStyles().get("Heading 1").getRunPr().getCount());
    }

    /// <summary>
    /// WORDSNET-7748 Export layout options as per Microsoft Word 2010 standard.
    /// The problem occurs because upon creating new document from scratch, AW loads "Aspose.Words.Resources.Blank.doc" 
    /// and of course Layout Options will be set for MS Word 2003.
    /// andrnosk: Fixed by adding an ability to set needed set of default options for specific formats. 
    /// </summary>
    @Test
    public void testJira7748InitWord2010Options() throws Exception
    {
        Document doc = new Document();

        // Initialize compatibility options to Word 2010 default values.
        doc.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2010);

        OoxmlSaveOptions opt = new OoxmlSaveOptions(SaveFormat.DOCX);
        opt.setCompliance(OoxmlCompliance.ISO_29500_2008_TRANSITIONAL);

        doc = TestUtil.saveOpen(doc, "Model\\MswVersionSpecific\\TestJira7748A.docx", opt);
        msAssert.areEqual("14", doc.getCompatibilityOptions().getCustomCompatibilitySettings().get("compatibilityMode").getValue());
        msAssert.areEqual(5, doc.getCompatibilityOptions().getCustomCompatibilitySettings().getCount());
    }

    @Test
    public void testJira7748InitWord2013Options() throws Exception
    {
        Document doc = new Document();

        // Initialize compatibility options to Word 2013 default values.
        doc.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2013);

        OoxmlSaveOptions opt = new OoxmlSaveOptions(SaveFormat.DOCX);
        opt.setCompliance(OoxmlCompliance.ISO_29500_2008_TRANSITIONAL);

        doc = TestUtil.saveOpen(doc, "Model\\MswVersionSpecific\\TestJira7748B.docx", opt);
        msAssert.areEqual("15", doc.getCompatibilityOptions().getCustomCompatibilitySettings().get("compatibilityMode").getValue());
        msAssert.areEqual(5, doc.getCompatibilityOptions().getCustomCompatibilitySettings().getCount());
    }

    /// <summary>
    /// Tests default values of compatibility options for MS Word 2016.
    /// </summary>
    @Test
    public void testOptimizingForWord2016() throws Exception
    {
        Document doc = new Document();

        doc.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2016);

        CustomCompatibilitySettingCollection options = doc.getCompatibilityOptions().getCustomCompatibilitySettings();
        msAssert.areEqual(5, options.getCount());
        msAssert.areEqual("15", options.get("compatibilityMode").getValue());
        msAssert.areEqual("1", options.get("overrideTableStyleFontSizeAndJustification").getValue());
        msAssert.areEqual("1", options.get("enableOpenTypeFeatures").getValue());
        msAssert.areEqual("1", options.get("doNotFlipMirrorIndents").getValue());
        msAssert.areEqual("1", options.get("differentiateMultirowTableHeaders").getValue());
    }

    /// <summary>
    /// Tests changing of document properties after running OptimizeFor.
    /// </summary>
    @Test (dataProvider = "testDocumentPropertiesDataProvider")
    public void testDocumentProperties(/*MsWordVersion*/int version, boolean expectedChartTrackingRefBased,
        String expectedDocumentSetId, boolean expectedDiscardImageEditingData, int expectedDefaultImageDpi,
        String expectedDocId) throws Exception
    {
        Document doc = new Document();

        DocPr docPr = doc.getDocPr();
        docPr.ChartTrackingRefBased = true;
        docPr.DocumentSetId = TEST_GUID;
        docPr.DiscardImageEditingData = true;
        docPr.DefaultImageDpi = 96;
        docPr.DocId = expectedDocId;

        doc.getCompatibilityOptions().optimizeFor(version);

        OoxmlSaveOptions opt = new OoxmlSaveOptions(SaveFormat.DOCX);
        opt.setCompliance(OoxmlCompliance.ISO_29500_2008_TRANSITIONAL);
        opt.setTestMode();
        opt.setWriteW14DocId(true);

        doc = TestUtil.saveOpen(doc, "Model\\MswVersionSpecific\\TestDocumentProperties.docx", opt, false);

        docPr = doc.getDocPr();
        msAssert.areEqual(expectedChartTrackingRefBased, docPr.ChartTrackingRefBased);
        msAssert.areEqual(expectedDocumentSetId, docPr.DocumentSetId);
        msAssert.areEqual(expectedDiscardImageEditingData, docPr.DiscardImageEditingData);
        msAssert.areEqual(expectedDefaultImageDpi, docPr.DefaultImageDpi);
        msAssert.areEqual(expectedDocId, docPr.DocId);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDocumentPropertiesDataProvider")
	public static Object[][] testDocumentPropertiesDataProvider() throws Exception
	{
		return new Object[][]
		{
			{MsWordVersion.WORD_2007,  false,  null,  false,  0,  null},
			{MsWordVersion.WORD_2010,  false,  null,  true,  96,  "62376D8B"},
			{MsWordVersion.WORD_2013,  true,  TEST_GUID,  true,  96,  "62376D8B"},
			{MsWordVersion.WORD_2016,  true,  TEST_GUID,  true,  96,  "62376D8B"},
		};
	}

    /// <summary>
    /// WORDSNET-19908 Verifies MSWord 2013 setting as a default value.
    /// Checks DefaultParaPr values setting for versions MSWord2007 and MSWord2013.
    /// </summary>
    @Test
    public void testJira19908() throws Exception
    {
        // Loads a document created in WPS office with the missing DefaultParaPr section.
        Document doc = TestUtil.open("Model\\Document\\TestJira19908.docx");
        ParaPr paraPr = doc.getStyles().getDefaultParaPr();
        // Checks DefaultParaPr values for MSWord 2013.
        msAssert.areEqual(160, paraPr.getSpaceAfter());
        msAssert.areEqual(259, paraPr.getLineSpacing());

        LoadOptions lo = new LoadOptions();
        lo.setMswVersion(MsWordVersion.WORD_2007);
        // Explicitly sets MSWord 2007.
        doc = TestUtil.open("Model\\Document\\TestJira19908.docx", lo);
        paraPr = doc.getStyles().getDefaultParaPr();
        // Checks DefaultParaPr values for MSWord 2007.
        msAssert.areEqual(200, paraPr.getSpaceAfter());
        msAssert.areEqual(276, paraPr.getLineSpacing());
    }

    /// <summary>
    /// Test GUID for the <see cref="TestDocumentProperties"/> test.
    /// </summary>
    private static final String TEST_GUID = "{E461F57B-44F5-469F-AE1F-68CCE877F320}";
}

