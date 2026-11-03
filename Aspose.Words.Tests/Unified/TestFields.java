// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import java.util.ArrayList;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for document fields.
/// </summary>
@Test
public class TestFields extends UnifiedTestsBase
{
    @Test
    public void testParseHyperlinkField()
    {
        checkHyperlink(" HYPERLINK \"http://www.aspose.com\" ", "http://www.aspose.com", "", "", "");
        checkHyperlink(" HYPERLINK  \\l \"xxx yyy\" ", "", "xxx yyy", "", "");
        checkHyperlink(" HYPERLINK \"\" \\l \"xxx yyy\" ", "", "xxx yyy", "", "");
    }

    /// <summary>
    /// Field extraction of a field that starts outside of the extraction range fails with exception. Fixed.
    /// </summary>
    @Test
    public void test27() throws Exception
    {
        // FOSS: input converted .doc -> .docx (field extraction is format-agnostic).
        Document doc = TestUtil.open("Model\\Field\\27.docx");
        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 14, true);
        ArrayList<Field> fields = FieldExtractor.extractToCollection(para);
        msAssert.areEqual(2, fields.size());
        Field field = fields.get(0);
        msAssert.areEqual(FieldType.FIELD_PAGE_REF, field.getType());
        field = fields.get(1);
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, field.getType());
    }

    private static void checkHyperlink(String fieldCode, String address, String subAddress, String target, String screenTip)
    {
        FieldCodeHyperlink field = FieldCodeHyperlink.parse(fieldCode);
        msAssert.areEqual(field.getAddress(), address);
        msAssert.areEqual(field.getSubAddress(), subAddress);
        msAssert.areEqual(field.getTarget(), target);
        msAssert.areEqual(field.getScreenTip(), screenTip);
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTOC(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Field\\TestTOC", lf, sf);

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);

        // 4 hyperlinks, 4 page ref fields, 1 TOC field and 1 TC field.
        msAssert.areEqual(10, fields.size());

        msAssert.areEqual(FieldType.FIELD_PAGE_REF, fields.get(0).getType());
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, fields.get(1).getType());
        msAssert.areEqual(FieldType.FIELD_TOC, fields.get(8).getType());
        msAssert.areEqual(FieldType.FIELD_TOC_ENTRY, fields.get(9).getType());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTOCSourceDataProvider")
	public void unifiedTestTOCSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTOC(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTOCSourceDataProvider")
	Object[][] unifiedTestTOCSourceDataProvider() throws Exception
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
    /// Tests a dead field (TC field in this case).
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDeadField(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Field\\TestDeadField", lf, sf);

        FieldExtractorToCollection extractor = new FieldExtractorToCollection();
        extractor.extract(doc);
        msAssert.areEqual(1, extractor.getFields().size());

        Field field = extractor.getFields().get(0);

        msAssert.areEqual(FieldType.FIELD_TOC_ENTRY, field.getStart().getFieldType());

        msAssert.areEqual(FieldType.FIELD_TOC_ENTRY, field.getEnd().getFieldType());
        msAssert.areEqual(false, field.getEnd().hasSeparator());

        // MSW adds double quotes for RTF format, mimic this behavior.
        String correctCode = (sf == SaveFormat.RTF) ? " TC  \"TestTC\" " : " TC  TestTC ";
        msAssert.areEqual(correctCode, field.getFieldCode());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDeadFieldSourceDataProvider")
	public void unifiedTestDeadFieldSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDeadField(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDeadFieldSourceDataProvider")
	Object[][] unifiedTestDeadFieldSourceDataProvider() throws Exception
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


    private static void checkXEStart(Node node)
    {
        msAssert.areEqual(NodeType.FIELD_START, node.getNodeType());
        msAssert.areEqual(FieldType.FIELD_INDEX_ENTRY, ((FieldStart)node).getFieldType());
        msAssert.areEqual(ControlChar.FIELD_START, node.getText());
    }

    private static void checkXEEnd(Node node)
    {
        msAssert.areEqual(NodeType.FIELD_END, node.getNodeType());
        msAssert.areEqual(FieldType.FIELD_INDEX_ENTRY, ((FieldEnd)node).getFieldType());
        msAssert.areEqual(false, ((FieldEnd)node).hasSeparator());
        msAssert.areEqual(ControlChar.FIELD_END, node.getText());
    }

    private static void checkRun(Node node, String text)
    {
        msAssert.areEqual(NodeType.RUN, node.getNodeType());
        msAssert.areEqual(text, node.getText());
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestFields(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Field\\TestFields", lf, sf);

        int shapeIdx = 1;

        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, shapeIdx, true);
        msAssert.areEqual(true, shape.isImage());
        msAssert.areEqual("http://www.aspose.com/images/banner-top-mid.gif", shape.getImageData().getSourceFullName());
        // RK Need to check with DV why this does not work in RTF.
        if (lf != LoadFormat.RTF)
            msAssert.areEqual(true, shape.getImageData().isLinkOnly());

        shape = (Shape)doc.getChild(NodeType.SHAPE, shapeIdx + 1, true);
        msAssert.areEqual(true, shape.isImage());
        msAssert.areEqual("http://www.aspose.com/images/banner-top-mid.gif", shape.getImageData().getSourceFullName());
        msAssert.areEqual(false, shape.getImageData().isLinkOnly());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFieldsSourceDataProvider")
	public void unifiedTestFieldsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFields(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFieldsSourceDataProvider")
	Object[][] unifiedTestFieldsSourceDataProvider() throws Exception
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
    /// Test that field result character formatting is preserved when the field result is updated.
    /// WORDSNET-3089 UpdateFields should take formatting from field value, if possible.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestMergeFormat(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Field\\TestMergeFormat", lf, sf);

        doc.getCustomDocumentProperties().get("XXX").setValue("Hello1\rHello2");
        doc.getRange().updateFields();

        doc = TestUtil.saveOpen(doc, "Model\\Field\\TestMergeFormat Modified", lf, sf);

        // Now the field result is across two paragraphs, but has original formatting.
        FieldSeparator fieldSeparator = (FieldSeparator)doc.getChild(NodeType.FIELD_SEPARATOR, 0, true);
        Run run = (Run)fieldSeparator.getNextSibling();
        msAssert.areEqual("Hello1", run.getText());
        msAssert.areEqual(24.0, run.getFont().getSize());
        msAssert.isTrue(run.getNextSibling() == null);

        Paragraph para = (Paragraph)run.nextPreOrder(doc);
        run = (Run)para.getFirstChild();
        msAssert.areEqual("Hello2", run.getText());
        msAssert.areEqual(24.0, run.getFont().getSize());
        msAssert.isTrue(run.getNextSibling() instanceof FieldEnd);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestMergeFormatSourceDataProvider")
	public void unifiedTestMergeFormatSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestMergeFormat(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestMergeFormatSourceDataProvider")
	Object[][] unifiedTestMergeFormatSourceDataProvider() throws Exception
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

    // FOSS: UnifiedTestNestedFields (and its GetFieldCharCollection helper) removed. It compared
    // field-char nodes against a baseline loaded from TestNestedFields.doc, and its whole purpose
    // was verifying DOC-import separator synthesis — both the .doc load and that import path are gone.


    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestIfMailMerge(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Field\\TestIfMailMerge", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestIfMailMergeSourceDataProvider")
	public void unifiedTestIfMailMergeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestIfMailMerge(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestIfMailMergeSourceDataProvider")
	Object[][] unifiedTestIfMailMergeSourceDataProvider() throws Exception
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

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestInsertedPageFields(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Field\\TestInsertedPageFields", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestInsertedPageFieldsSourceDataProvider")
	public void unifiedTestInsertedPageFieldsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestInsertedPageFields(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestInsertedPageFieldsSourceDataProvider")
	Object[][] unifiedTestInsertedPageFieldsSourceDataProvider() throws Exception
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

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestNestedInFieldCode(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Field\\TestNestedInFieldCode", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestNestedInFieldCodeSourceDataProvider")
	public void unifiedTestNestedInFieldCodeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestNestedInFieldCode(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestNestedInFieldCodeSourceDataProvider")
	Object[][] unifiedTestNestedInFieldCodeSourceDataProvider() throws Exception
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

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestPageRef(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Field\\TestPageRef", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestPageRefSourceDataProvider")
	public void unifiedTestPageRefSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestPageRef(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestPageRefSourceDataProvider")
	Object[][] unifiedTestPageRefSourceDataProvider() throws Exception
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

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTextInput(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Field\\TestTextInput", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTextInputSourceDataProvider")
	public void unifiedTestTextInputSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTextInput(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTextInputSourceDataProvider")
	Object[][] unifiedTestTextInputSourceDataProvider() throws Exception
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

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTextInputManyParagraphs(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Field\\TestTextInputManyParagraphs", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTextInputManyParagraphsSourceDataProvider")
	public void unifiedTestTextInputManyParagraphsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTextInputManyParagraphs(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTextInputManyParagraphsSourceDataProvider")
	Object[][] unifiedTestTextInputManyParagraphsSourceDataProvider() throws Exception
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

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestToc1(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Field\\TestToc1", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestToc1SourceDataProvider")
	public void unifiedTestToc1Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestToc1(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestToc1SourceDataProvider")
	Object[][] unifiedTestToc1SourceDataProvider() throws Exception
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
    /// WORDSNET-9987 "NullReferenceException" exception occurs upon saving document in RTF format.
    /// The problem occurred, because there is LINK field, which does not have EmbeddedObject.
    /// In case if LINK field does not contain EmbeddedObject (EmbeddedObject==null)
    /// we should write such LINK field as a normal field.
    /// To fix the issue created method HasOleObject, which checks if current field has EmbeddedObject.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDefect9987(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Field\\TestDefect9987", lf, sf);

        // The document should contain only one field.
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(paragraph.getChildNodes(NodeType.ANY, false).get(0).getNodeType(), NodeType.FIELD_START);
        msAssert.areEqual(paragraph.getChildNodes(NodeType.ANY, false).get(1).getNodeType(), NodeType.RUN);
        msAssert.areEqual(paragraph.getChildNodes(NodeType.ANY, false).get(2).getNodeType(), NodeType.FIELD_SEPARATOR);
        msAssert.areEqual(paragraph.getChildNodes(NodeType.ANY, false).get(3).getNodeType(), NodeType.RUN);
        msAssert.areEqual(paragraph.getChildNodes(NodeType.ANY, false).get(4).getNodeType(), NodeType.FIELD_END);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefect9987SourceDataProvider")
	public void unifiedTestDefect9987Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefect9987(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefect9987SourceDataProvider")
	Object[][] unifiedTestDefect9987SourceDataProvider() throws Exception
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
    /// WORDSNET-3704 Hyperlinks asociated with embedded OLE objects are not preserved after processing documents.
    /// Inline OLE should be included into HYPERLINK field the same way as for inline pictures.
    /// </summary>
    /// <param name="lf"></param>
    /// <param name="sf"></param>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDefect3704(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // AM. DOC file should be verified manually. We delete hyperlink field upon load and shape have no property to test if there was hyperlink field or was not.
        // Other formats are tested against gold files.
        TestUtil.openSaveOpen("Model\\Field\\TestDefect3704", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefect3704SourceDataProvider")
	public void unifiedTestDefect3704Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefect3704(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefect3704SourceDataProvider")
	Object[][] unifiedTestDefect3704SourceDataProvider() throws Exception
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

    private static void testDefect11155(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.open("Model\\Field\\TestDefect11155", TestUtil.getLoadFormat(scenario));
        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        msAssert.areEqual(3, fields.size());
        msAssert.areEqual(FieldType.FIELD_PAGE, fields.get(2).getType());
        TestUtil.saveOpen(doc, "Model\\Field\\TestDefect11155", scenario);
    }
}

