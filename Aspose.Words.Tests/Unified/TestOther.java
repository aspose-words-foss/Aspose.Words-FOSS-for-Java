// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.TestEnvironment;
import com.aspose.Language;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.IO.Stream;
import java.util.Iterator;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unsorted unified tests. At some stage should look at them and move into appropriate places.
/// </summary>
@Test
public class TestOther extends UnifiedTestsBase
{
    /// <summary>
    /// Check that output contains generator name by default.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestGeneratorName(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        SaveOptions opt = SaveOptions.createSaveOptions(sf);
        msAssert.areEqual(true, opt.getExportGeneratorName());
        opt.setPrettyFormat(true);
        opt.setSetBuiltInThemeIfNull(false);
        TestUtil.saveOpen(doc, "Model\\Other\\TestGeneratorName", TestUtil.getUnifiedScenario(lf, sf), opt);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestGeneratorNameSourceDataProvider")
	public void unifiedTestGeneratorNameSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestGeneratorName(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestGeneratorNameSourceDataProvider")
	Object[][] unifiedTestGeneratorNameSourceDataProvider() throws Exception
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
    /// Check that the document has no pretty format by default.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestNoPrettyFormat(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        SaveOptions opt = SaveOptions.createSaveOptions(sf);
        opt.setExportGeneratorName(false);
        opt.setSetBuiltInThemeIfNull(false);
        msAssert.areEqual(false, opt.getPrettyFormat());
        TestUtil.saveOpen(doc, "Model\\Other\\TestNoPrettyFormat", TestUtil.getUnifiedScenario(lf, sf), opt);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestNoPrettyFormatSourceDataProvider")
	public void unifiedTestNoPrettyFormatSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestNoPrettyFormat(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestNoPrettyFormatSourceDataProvider")
	Object[][] unifiedTestNoPrettyFormatSourceDataProvider() throws Exception
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
    /// WORDSNET-761 Support subdocuments.
    /// </summary>
    /// <remarks>
    /// AM. There is some problem to get normal unified testing.
    /// When Word saved original document into another format it re-saves subdocuments to the same format and
    /// also changes file links in master document. So gold comparsion doesn't work.
    /// </remarks>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedSubDocuments(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        final String MASTER_FILE = "Model\\SubDocument\\Master document";
        Document doc = TestUtil.open(MASTER_FILE, lf);

        msAssert.areEqual(3, doc.getSections().getCount());

        // First subdocument is referred by absolute filename.
        verifyDirectoryAndFileName(doc, 0, TestEnvironment.getUserHome() + "Documents", "Absolute referred subdocument");

        // Second subdocument is referred by relative filename, converted to absolute on load.
        verifyDirectoryAndFileName(doc, 1, TestEnvironment.getTestData() + "Model\\SubDocument", "Relative referred subdocument");

        // Document is saved to another directory.
        String savedMasterFile = TestUtil.buildOutFileName(TestUtil.buildTestFileName(MASTER_FILE), "", sf);
        doc.save(savedMasterFile);
        doc = TestUtil.open(savedMasterFile);

        // First subdocument remains absolute referred.
        verifyDirectoryAndFileName(doc, 0, TestEnvironment.getUserHome() + "Documents", "Absolute referred subdocument");

        // Second subdocument is now referred by absolute filename.
        verifyDirectoryAndFileName(doc, 1, TestEnvironment.getTestData() + "Model\\SubDocument", "Relative referred subdocument");

        // Make document from scratch.
        final String NEW_FILE = "Model\\SubDocument\\New document";

        doc = new Document();
        Body body = doc.appendChild(new Section(doc)).appendChild(new Body(doc));
        Paragraph para = body.appendParagraph("Document with subdocuments");
        para = body.appendChild(new Paragraph(doc));
        para.appendChild(new SubDocument(doc, "X:\\Invalid.doc"));
        para = body.appendChild(new Paragraph(doc));
        para.appendChild(new SubDocument(doc, ":\\not a file name at all/:"));

        String savedNewFile = TestUtil.buildOutFileName(TestUtil.buildTestFileName(NEW_FILE), "", sf);
        doc.save(savedNewFile);
        doc = TestUtil.open(savedNewFile);

        verifyDirectoryAndFileName(doc, 0, "X:\\", "Invalid");
        // This throws in VerifyDirectoryAndFileName so check manually.
        msAssert.areEqual(":\\not a file name at all/:", ((SubDocument)doc.getChildNodes(NodeType.SUB_DOCUMENT, true).get(1)).getFileName());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedSubDocumentsSourceDataProvider")
	public void unifiedSubDocumentsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedSubDocuments(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedSubDocumentsSourceDataProvider")
	Object[][] unifiedSubDocumentsSourceDataProvider() throws Exception
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
    /// WORDSNET-5320 Fix locale reading.
    /// Verifies languages information is read/written properly.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedLanguages(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Other\\TestLanguages", lf, sf);

        // Verify styles.
        verifyLocales(doc.getStyles().getByName("StyleKorean", false).getRunPr(), Language.ENGLISH_US, Language.KOREAN_KOREA, false);
        verifyLocales(doc.getStyles().getByName("StyleKoreanNoProof", false).getRunPr(), Language.NO_PROOF, Language.KOREAN_KOREA, true);

        verifyLocales(doc.getStyles().getByName("StyleRussian", false).getRunPr(), Language.RUSSIAN_RUSSIA, Language.NO_PROOF, false);
        verifyLocales(doc.getStyles().getByName("StyleRussianNoProof", false).getRunPr(), Language.RUSSIAN_RUSSIA, Language.NO_PROOF, true);

        verifyLocales(((Run)doc.getNodeById("0.0.0.0")).getRunPr(), Language.FRENCH_FRANCE, Language.NO_PROOF, false);
        verifyLocales(((Run)doc.getNodeById("0.1.0.0")).getRunPr(), Language.FRENCH_FRANCE, Language.NO_PROOF, true);

        verifyLocales(((Run)doc.getNodeById("0.4.0.0")).getRunPr(), Language.ENGLISH_US, Language.KOREAN_KOREA, false);
        verifyLocales(((Run)doc.getNodeById("0.5.0.0")).getRunPr(), Language.ENGLISH_US, Language.KOREAN_KOREA, true);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedLanguagesSourceDataProvider")
	public void unifiedLanguagesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedLanguages(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedLanguagesSourceDataProvider")
	Object[][] unifiedLanguagesSourceDataProvider() throws Exception
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


    // FOSS: Test21179 removed — it verified non-seekable-stream saving for Doc/Rtf/WordML, all of which
    // were removed. The surviving Docx non-seek case is covered by Test21179DocxGoldCheck.


    // FOSS: TestUpdatingFrame / TestUpdatingFrameDefaultUrlOfFramesPage /
    // TestUpdatingIsFrameLinkToFileOfFramesPage removed — framesets can only be loaded from .doc or
    // HTML, both removed in FOSS, so the frameset scenarios cannot be constructed.


    /// <summary>
    /// WORDSNET-24982 PPTX document is wrongly loaded into Document as Text document.
    /// Word recognizes ZIP signature and if recognized, then does not fall back to TXT format,
    /// when it further cannot be properly recognized as one of the known Word formats.
    /// </summary>
    @Test (expectedExceptions = UnsupportedFileFormatException.class)
    public void test24982() throws Exception
    {
        Document doc = TestUtil.open("Other\\Test24982.pptx");
        msAssert.isNull(doc);
    }

    /// <summary>
    /// WORDSNET-25109 ZIP archive is loaded as TXT document into Aspose.Words.
    /// Duplicates WORDSNET-24982
    /// </summary>
    @Test (expectedExceptions = UnsupportedFileFormatException.class)
    public void test25109() throws Exception
    {
        Document doc = TestUtil.open("Other\\Test25109.zip");
        msAssert.isNull(doc);
    }

    /// <summary>
    /// WORDSNET-25454 File is mistakenly detected as PDF by Aspose.Words.
    /// We should check PDF file trailer '%%EOF' along with '%PDF-' signature in header
    /// to filter out non-valid PDF files.
    /// </summary>
    @Test
    public void test25454() throws Exception
    {
        Document doc = TestUtil.open("Other\\Test25454.mso");
        msAssert.areEqual(LoadFormat.TEXT, doc.getOriginalLoadFormat());
    }

    /// <summary>
    /// Relates to WORDSNET-25454
    /// Another file that is successfully opened by Word as TXT format.
    /// </summary>
    @Test
    public void test25454A() throws Exception
    {
        Document doc = TestUtil.open("Other\\Test25454A.mso");
        msAssert.areEqual(LoadFormat.TEXT, doc.getOriginalLoadFormat());
    }

    /// <summary>
    /// WORDSNET-25512 Check whether customXml part is required in AllStyles2003.docx resource.
    /// Checks whether CustomXml parts are absent in AllStyles2003.docx and AllStyles2007.docx resource documents.
    /// </summary>
    @Test
    public void test25512() throws Exception
    {
        checkCustomXmlPartsIsEmpty("Aspose.Words.Resources.AllStyles2003.docx");
        checkCustomXmlPartsIsEmpty("Aspose.Words.Resources.AllStyles2007.docx");
    }

    /// <summary>
    /// WORDSNET-27211 Corrupted DOCX document is loaded as TXT if load from stream.
    /// Don't fallback to TXT when format is set explicitly and auto-detection is failed with returning format 'Unknown'.
    /// </summary>
    @Test (expectedExceptions = UnsupportedFileFormatException.class)
    public void test27211() throws Exception
    {
        String fileName = TestUtil.buildTestFileName("Other\\Test27211.docx");
        byte[] docBytes = File.readAllBytes(fileName);
        MemoryStream stream = new MemoryStream(docBytes);
        try /*JAVA: was using*/
        {
            LoadOptions options = new LoadOptions(); { options.setLoadFormat(LoadFormat.DOCX); }
            Document doc = new Document(stream, options);
            msAssert.areEqual(LoadFormat.UNKNOWN, doc.getOriginalLoadFormat());
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// WORDSNET-26255 Corrupted file is loaded as TXT by Aspose.Words.
    /// Added weight to long strings in <see cref="FileFormatDetector.DetectPlainText"/>.
    /// The longer the string, the more weight it has for now.
    /// </summary>
    @Test (expectedExceptions = UnsupportedFileFormatException.class)
    public void test26255() throws Exception
    {
        Document doc = TestUtil.open("Other\\Test26255.docx");
        msAssert.isNull(doc);
    }

    public static Iterable<TestCaseData> getUnifiedResourceLoadingCallbackCases()
    {
        ArrayList<TestCaseData> cases = new ArrayList<TestCaseData>();
        // FOSS: only Docx survives — Doc/Rtf/WordML load were removed.
        msArrayList.add(cases, new TestCaseData(LoadFormat.DOCX, new UserProvidedDataHandler()));
        return cases;
    }

    /// <summary>
    /// Helper method for UnifiedLanguages test.
    /// </summary>
    private static void verifyLocales(RunPr runPr, /*Language*/int localeId, /*Language*/int localeIdFarEast, boolean noProof)
    {
        msAssert.areEqual(localeId, (/*Language*/int)runPr.getLocaleId());
        msAssert.areEqual(localeIdFarEast, (/*Language*/int)runPr.getLocaleIdFarEast());
        msAssert.areEqual(noProof, runPr.getNoProofing().toBool());
    }

    /// <summary>
    /// Verifies path and name without file extension.
    /// </summary>
    private static void verifyDirectoryAndFileName(Document doc, int index, String directoryName, String fileName) throws Exception
    {
        SubDocument subDocument = (SubDocument)doc.getChildNodes(NodeType.SUB_DOCUMENT, true).get(index);

        msAssert.areEqual(fileName, Path.getFileNameWithoutExtension(subDocument.getFileName()));
        msAssert.areEqual(directoryName, Path.getDirectoryName(subDocument.getFileName()));
    }

    private static void checkCustomXmlPartsIsEmpty(String resourceName) throws Exception
    {
        Stream stream = ResourceUtil.fetchResourceStream(resourceName);
        try /*JAVA: was using*/
        {
            LoadOptions lo = new LoadOptions();
            lo.setLoadFormat(LoadFormat.DOCX);
            Document doc = new Document(stream, lo);

            msAssert.areEqual(0, doc.getCustomXmlParts().getCount());
        }
        finally { if (stream != null) stream.close(); }
    }
}

