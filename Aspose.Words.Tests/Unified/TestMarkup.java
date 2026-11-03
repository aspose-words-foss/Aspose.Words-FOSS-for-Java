// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.StringUtil;
import com.aspose.TestEnvironment;
import com.aspose.ms.System.msString;
import com.aspose.ArrayUtil;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for how model handles custom XML, smart tags and control controls.
/// </summary>
@Test
public class TestMarkup extends UnifiedTestsBase
{
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestXmlSettings(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // Check defaults.
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\TestXmlSettingsDefault", lf, sf);
        DocPr docPr = doc.getDocPr();

        msAssert.areEqual(false, docPr.SaveXmlDataOnly);
        msAssert.areEqual(false, docPr.UseXsltWhenSaving);
        msAssert.areEqual(false, StringUtil.hasChars(docPr.SaveThroughXslt));

        msAssert.areEqual(true, docPr.ValidateAgainstSchema);
        msAssert.areEqual(false, docPr.DoNotUnderlineInvalidXml);
        msAssert.areEqual(false, docPr.IgnoreMixedContent);
        msAssert.areEqual(false, docPr.SaveInvalidXml);

        msAssert.areEqual(false, docPr.AlwaysShowPlaceholderText);


        // Check use XSLT settings.
        doc = TestUtil.openSaveOpen("Model\\Markup\\TestXmlSettingsUseXslt", lf, sf);
        docPr = doc.getDocPr();
        msAssert.areEqual(true, docPr.UseXsltWhenSaving);
        msAssert.areEqual(TestEnvironment.getTestData("Model\\Markup\\Test.xslt"), docPr.SaveThroughXslt);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestXmlSettingsSourceDataProvider")
	public void unifiedTestXmlSettingsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestXmlSettings(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestXmlSettingsSourceDataProvider")
	Object[][] unifiedTestXmlSettingsSourceDataProvider() throws Exception
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
    /// Verifies that Custom Xml is successfully read from document parts such as header, footer and body.
    /// </summary>
    /// <remarks>See remarks for <see cref="UnifiedTestCustomXmlMarkup"/>. The schema is applicable
    /// to the test file in this test as well.</remarks>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCustomXmlDocumentParts(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        checkCustomXmlMarkup("Model\\Markup\\TestCustomXmlDocumentParts", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCustomXmlDocumentPartsSourceDataProvider")
	public void unifiedTestCustomXmlDocumentPartsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCustomXmlDocumentParts(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCustomXmlDocumentPartsSourceDataProvider")
	Object[][] unifiedTestCustomXmlDocumentPartsSourceDataProvider() throws Exception
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
    /// Verifies that Custom Xml is successfully read and markup levels are set properly.
    /// </summary>
    /// <remarks>See remarks for <see cref="UnifiedTestCustomXmlMarkup"/>. The schema is applicable
    /// to the test file in this test as well.</remarks>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCustomXmlLevels(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        checkCustomXmlMarkup("Model\\Markup\\TestCustomXmlLevels", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCustomXmlLevelsSourceDataProvider")
	public void unifiedTestCustomXmlLevelsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCustomXmlLevels(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCustomXmlLevelsSourceDataProvider")
	Object[][] unifiedTestCustomXmlLevelsSourceDataProvider() throws Exception
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
    /// Verifies block-level markups are put inside each other in several combinations.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCustomXmlInnerBlocks(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        checkCustomXmlMarkup("Model\\Markup\\TestCustomXmlInnerBlocks", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCustomXmlInnerBlocksSourceDataProvider")
	public void unifiedTestCustomXmlInnerBlocksSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCustomXmlInnerBlocks(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCustomXmlInnerBlocksSourceDataProvider")
	Object[][] unifiedTestCustomXmlInnerBlocksSourceDataProvider() throws Exception
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
    /// Verifies wordml-like markup used as custom xml.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCustomXmlWmlMarkup(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = checkCustomXmlMarkup("Model\\Markup\\TestCustomXmlWmlMarkup", lf, sf);
        // verify table read correctly.
        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        msAssert.areEqual(1, table.getRows().getCount());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCustomXmlWmlMarkupSourceDataProvider")
	public void unifiedTestCustomXmlWmlMarkupSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCustomXmlWmlMarkup(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCustomXmlWmlMarkupSourceDataProvider")
	Object[][] unifiedTestCustomXmlWmlMarkupSourceDataProvider() throws Exception
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
    /// This file contains custom xml markup which starts outside of table and ends within last table cell.
    /// It means that start and end of custom xml is occurred on different levels in model.
    ///
    /// Word treats this as custom xml wrap whole table.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCustomXmlDiffLevel(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = checkCustomXmlMarkup("Model\\Markup\\TestCustomXmlDiffLevel", lf, sf);

        // check last paragraph is not wrapped by custom xml markup
        msAssert.areEqual(NodeType.PARAGRAPH, doc.getNodeById("3.0.0").getNodeType());

        Table table = (Table)doc.getNodeById("2.0.0");
        msAssert.areEqual(2, table.getRows().getCount());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCustomXmlDiffLevelSourceDataProvider")
	public void unifiedTestCustomXmlDiffLevelSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCustomXmlDiffLevel(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCustomXmlDiffLevelSourceDataProvider")
	Object[][] unifiedTestCustomXmlDiffLevelSourceDataProvider() throws Exception
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
    /// This file contains two section and two markups.
    /// First markup wraps paragraphs within on section and should be preserved.
    /// Second wraps paragraphs in oth sections and should be ignored.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCustomXmlSections(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = checkCustomXmlMarkup("Model\\Markup\\TestCustomXmlSections", lf, sf);

        // verify all sections were read
        msAssert.areEqual(NodeType.SECTION, doc.getNodeById("0").getNodeType());
        msAssert.areEqual(NodeType.SECTION, doc.getNodeById("1").getNodeType());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCustomXmlSectionsSourceDataProvider")
	public void unifiedTestCustomXmlSectionsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCustomXmlSections(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCustomXmlSectionsSourceDataProvider")
	Object[][] unifiedTestCustomXmlSectionsSourceDataProvider() throws Exception
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
    /// WORDSNET-10749 Remove Custom XML feature from the code.
    /// MS Word violates i4i's patent #5,787,449 and removes custom Xml from the doc, docx, rtf, wml.
    /// See details on http://support.microsoft.com/kb/2761189
    /// We do the same in AW now.
    /// </summary>
    @Test (dataProvider = "unifiedTestJira10749DataProvider")
    // FOSS Doc/Rtf/Wml load+save are removed; keep only the Docx2Docx roundtrip.
    public void unifiedTestJira10749(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.openAndVerifyCustomXmlWarning("Model\\Markup\\TestJira10749", TestUtil.getLoadFormat(scenario));
        doc = TestUtil.saveOpen(doc, "Model\\Markup\\TestJira10749", scenario | UnifiedScenario.NO_GOLD);

        // Check that content inside custom xml was successfully round-tripped.
        Paragraph para = (Paragraph) doc.getChild(NodeType.PARAGRAPH, 0, true);
        msAssert.areEqual("TextBefore Text in custom Xml TextAfter\f", para.getText());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "unifiedTestJira10749DataProvider")
	public static Object[][] unifiedTestJira10749DataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.DOCX_2_DOCX},
		};
	}

    // FOSS TestDefect7105 removed: RTF->DOC smart-tag namespace test; both formats removed.

    /// <summary>
    /// Utility verification. Used in <see cref="UnifiedTestDefect4995" />.
    /// </summary>
    private static void verifyProperty(CustomXmlProperty property, String name, String uri, String value)
    {
        msAssert.areEqual(property.getName(), name);
        msAssert.areEqual(property.getUri(), uri);
        msAssert.areEqual(property.getValue(), value);
    }

    /// <summary>
    /// WORDSNET-16454 Preserve Custom Payload in OOXML
    /// </summary>
    @Test
    public void testCustomParts() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\TestCustomParts.docx");

        msAssert.areEqual(2, doc.getPackageCustomParts().getCount());

        // This part is an arbitrary part attached to the package.
        CustomPart part = doc.getPackageCustomParts().get(0);
        msAssert.areEqual("/payload/payload_on_package.test", part.getName());
        msAssert.areEqual("mytest/somedata", part.getContentType());
        msAssert.areEqual("http://mytest.payload.internal", part.getRelationshipType());
        msAssert.areEqual(false, part.isExternal());
        msAssert.areEqual(18, part.getData().length);

        // This is an external relationship attached to the package.
        part = doc.getPackageCustomParts().get(1);
        msAssert.areEqual("http://www.aspose.com/Images/aspose-logo.jpg", part.getName());
        msAssert.areEqual("", part.getContentType());
        msAssert.areEqual("http://mytest.payload.external", part.getRelationshipType());
        msAssert.areEqual(true, part.isExternal());
        msAssert.areEqual(0, part.getData().length);
    }





    // FOSS TestJira6148_NotOOXML removed: tests non-OOXML SDT data-binding via a Pdf save (removed fixed format).

    /// <summary>
    /// WORDSNET-9811 Paragraph and some content is incorrectly rendering to next page in PDF
    /// Cell level SDT is incorrectly updated from placeholder.
    /// </summary>
    /// <remarks>
    /// AM. This issue was introduced by WORDSNET-9337
    /// To quickly remove regression I decided to limit WORDSNET-9337 functionality by inline SDT only and
    /// raised separate WORDSNET-9886
    /// </remarks>
    @Test
    public void testJira9811() throws Exception
    {
        // FOSS Save via Docx (Doc removed) to update SDTs.
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\TestJira9811", LoadFormat.DOCX, SaveFormat.DOCX);

        // Verify problematic content.
        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        Row row = table.getRows().get(2);

        msAssert.areEqual(2, row.getCells().getCount());
        msAssert.areEqual("Help send the high school choir to sing \rin New York City\u0007", row.getCells().get(0).getText());
        msAssert.areEqual("Help send the high school choir to sing \rin New York City\u0007", row.getCells().get(1).getText());
    }



    // FOSS TestJira11496 removed: reads a glossary from a DOC (.dot) template; DOC load removed.

    // FOSS TestDocTwoBlocks removed: reads AutoText glossary from a DOC (.dot); DOC load removed.

    // FOSS TestDocTables removed: reads AutoText-with-tables glossary from a DOC (.dot); DOC load removed.

    // FOSS TestDocShapes removed: reads AutoText-with-shape glossary from a DOC (.dot); DOC load removed.

    /// <summary>
    /// Relates to WORDSNET-9962 Customer code snippet.
    /// </summary>
    private static void createCover(Document docx, Document docBuildingBlocks, String coverName)
    {
        GlossaryDocument glossaryDocument = docBuildingBlocks.getGlossaryDocument();
        for (BuildingBlock buildingBlock : (Iterable<BuildingBlock>) glossaryDocument.getBuildingBlocks())
        {
            if ((buildingBlock.getGallery() == BuildingBlockGallery.COVER_PAGE) && (msString.equals(buildingBlock.getName(), coverName)))
            {
                // clone section which you want to keep layout of
                Section sec = docx.getFirstSection().deepClone();
                // remove existing content of this section
                sec.removeAllChildren();
                // append content of buildingBlock to this section
                for (Node node : (Iterable<Node>) ((Section)buildingBlock.getFirstChild()).getChildNodes(NodeType.ANY, false))
                    sec.appendChild(docx.importNode(node, true));
                docx.insertAfter(sec, docx.getFirstChild());
                break;
            }
        }
    }

    private static void testCustomXmlParts(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\TestCustomXmlParts", lf, sf);

        // There are several parts that are auto generated by MS Word and it seems to generate one less for DOC files.
        int expectedPartCount = (lf == LoadFormat.DOC) ? 5 : 6;
        msAssert.areEqual(expectedPartCount, doc.getCustomXmlParts().getCount());

        // Check details of one of the Custom XML Data Storage parts.
        CustomXmlPart customXmlPart = doc.getCustomXmlParts().getById("{B0048027-F405-4DA1-AB3E-1A1F5695C61E}");
        msAssert.areEqual(8312, customXmlPart.getData().length);
        msAssert.areEqual("{B0048027-F405-4DA1-AB3E-1A1F5695C61E}", customXmlPart.getId());
        msAssert.areEqual(11, customXmlPart.getSchemas().getCount());
        msAssert.areEqual("http://schemas.microsoft.com/office/2006/metadata/contentType", customXmlPart.getSchemas().get(0));
        msAssert.areEqual("http://schemas.microsoft.com/office/internal/2005/internalDocumentation", customXmlPart.getSchemas().get(10));
    }

    /// <summary>
    /// WORDSNET-15050 Old incorrect value is rendered inside a SDT in PDF
    /// Word extracts TermInfo/TermName element if Terms element was selected.
    /// </summary>
    @Test
    public void testJira15050() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira15050.docx");
        TestUtil.executeValidator(doc, SaveFormat.DOCX); // FOSS Doc -> Docx validation

        msAssert.areEqual("Test\r\rTestValue2\f", doc.getText());
    }

    // FOSS TestJira13324 removed: RTF CustomXmlParts datastore roundtrip (Rtf2Rtf); RTF removed.

    // FOSS TestJira13324A removed: RTF CustomXmlParts datastore roundtrip (Rtf2Rtf); RTF removed.

    // FOSS TestJira16352 removed: loads CustomXmlParts from RTF; RTF load removed.

    /// <summary>
    /// Tests creation of a custom XML part by its XML code.
    /// </summary>
    @Test
    public void testCreationOfCustomXmlPartByXml() throws Exception
    {
        Document doc = new Document();
        CustomXmlPart customXmlPart = doc.getCustomXmlParts().add("ID", "<a>Я</a>");

        msAssert.areEqual("ID", customXmlPart.getId());
        msAssert.areEqual("3C 61 3E D0 AF 3C 2F 61 3E ", ArrayUtil.dumpArray(customXmlPart.getData()));
    }




    /// <summary>
    /// WORDSNET-23544 Document missing sections after saving
    /// Empty content SDT should be handled in special way.
    /// </summary>
    @Test (dataProvider = "test23544_2013DataProvider")
    public void test23544_2013(String xPath, int id) throws Exception
    {
        checkSdtLocation("Model\\Markup\\Test23544.docx", MsWordVersion.WORD_2013, xPath, id);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test23544_2013DataProvider")
	public static Object[][] test23544_2013DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"//Body/Paragraph[2]/following-sibling::*",  1994680633},
			{"//Body/Paragraph[3]/following-sibling::*",  2120024171},
			{"//Body/Table/Row[1]/Cell[1]/Paragraph/StructuredDocumentTag",  -220439517},
			{"//Body/Table/Row[2]/Cell[1]/StructuredDocumentTag",  1437783286},
			{"//Body/Paragraph[9]/following-sibling::*",  -1122755837},
			{"//Body/Paragraph[10]/following-sibling::*",  -1662928133},
			{"//Body/Paragraph[13]/StructuredDocumentTag",  854304033},
			{"//Body/Paragraph[13]/following-sibling::*",  -1100014938},
			{"//Body/Paragraph[15]/following-sibling::*",  -1412542219},
			{"//Body/Paragraph[16]/following-sibling::*",  -831825925},
			{"//Body/Paragraph[19]/StructuredDocumentTag",  1941408926},
			{"//Body/Paragraph[19]/following-sibling::*",  -1405287596},
		};
	}

    /// <summary>
    /// WORDSNET-23544 Document missing sections after saving
    /// Empty content SDT should be handled in special way.
    /// </summary>
    @Test (dataProvider = "test23544_2019DataProvider")
    public void test23544_2019(String xPath, int id) throws Exception
    {
        checkSdtLocation("Model\\Markup\\Test23544.docx", MsWordVersion.WORD_2019, xPath, id);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test23544_2019DataProvider")
	public static Object[][] test23544_2019DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"//Body/Paragraph[2]/following-sibling::*",  1994680633},
			{"//Body/Paragraph[3]/following-sibling::*",  2120024171},
			{"//Body/Table/Row[1]/Cell[1]/StructuredDocumentTag",  -220439517},
			{"//Body/Table/Row[2]/Cell[1]/StructuredDocumentTag",  1437783286},
			{"//Body/Paragraph[9]/following-sibling::*",  -1122755837},
			{"//Body/Paragraph[10]/following-sibling::*",  -1662928133},
			{"//Body/Paragraph[12]/following-sibling::*",  854304033},
			{"//Body/Paragraph[13]/following-sibling::*",  -1100014938},
			{"//Body/Paragraph[15]/following-sibling::*",  -1412542219},
			{"//Body/Paragraph[16]/following-sibling::*",  -831825925},
			{"//Body/Paragraph[18]/following-sibling::*",  1941408926},
			{"//Body/Paragraph[19]/following-sibling::*",  -1405287596},
		};
	}







    /// <summary>
    /// WORDSNET-27401 Children count returned by GetChildNodes is incorrect for nested SDTs.
    /// Tests the correctness of NodeCollection filling in case of nested StructuredDocumentTags.
    /// </summary>
    @Test
    public void test27401() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test27401.docx");

        // Checks child paragraphs of the parent SDT.
        IStructuredDocumentTag sdt1 = doc.getRange().getStructuredDocumentTags().get(0);

        NodeCollection shallowParagraphs1 = sdt1.getChildNodes(NodeType.PARAGRAPH, false);
        msAssert.areEqual(2, shallowParagraphs1.getCount());
        NodeCollection deepParagraphs1 = sdt1.getChildNodes(NodeType.PARAGRAPH, true);
        msAssert.areEqual(4, deepParagraphs1.getCount());

        // Checks child paragraphs of the child SDT.
        IStructuredDocumentTag sdt2 = doc.getRange().getStructuredDocumentTags().get(1);

        NodeCollection shallowParagraphs2 = sdt2.getChildNodes(NodeType.PARAGRAPH, false);
        msAssert.areEqual(2, shallowParagraphs2.getCount());
        NodeCollection deepParagraphs2 = sdt2.getChildNodes(NodeType.PARAGRAPH, true);
        msAssert.areEqual(2, deepParagraphs2.getCount());
    }

    /// <summary>
    /// Checks SDT is placed into specified location and has expected Id.
    /// </summary>
    private static void checkSdtLocation(String testName, /*MsWordVersion*/int mswVersion, String xPath, int expectedId) throws Exception
    {
        LoadOptions lo = new LoadOptions(); { lo.setMswVersion(mswVersion); }
        Document doc = TestUtil.open(testName, lo);

        Node node = doc.selectSingleNode(xPath);

        msAssert.areEqual(NodeType.STRUCTURED_DOCUMENT_TAG, node.getNodeType());
        msAssert.areEqual(expectedId, ((StructuredDocumentTag)node).getId());
    }

    /// <summary>
    /// Round-trips document. Checks warning for CustomXmlMarkup and verifies gold.
    /// </summary>
    private static Document checkCustomXmlMarkup(String filename, /*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openAndVerifyCustomXmlWarning(filename, lf);
        return TestUtil.saveOpen(doc, filename, lf, sf);
    }
}

