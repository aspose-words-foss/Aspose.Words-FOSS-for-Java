// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for headers and footers.
/// </summary>
@Test
public class TestHeaders extends UnifiedTestsBase
{
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestHeaderSimple(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Header\\TestHeaderSimple", lf, sf);
        msAssert.areEqual("Body\f", doc.getFirstSection().getBody().getText());
        msAssert.areEqual("Header\r", doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getText());
        msAssert.areEqual(1, doc.selectNodes("//HeaderFooter").getCount());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestHeaderSimpleSourceDataProvider")
	public void unifiedTestHeaderSimpleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestHeaderSimple(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestHeaderSimpleSourceDataProvider")
	Object[][] unifiedTestHeaderSimpleSourceDataProvider() throws Exception
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
    /// Headers and footers same for all pages in one section.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestHeaderSame(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Header\\TestHeaderSame", lf, sf);

        Section sect = doc.getFirstSection();
        msAssert.areEqual("SimpleHeader\r", sect.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getText());
        msAssert.areEqual("SimpleFooter\r", sect.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY).getText());
        msAssert.areEqual(null, sect.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_FIRST));
        msAssert.areEqual(null, sect.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_EVEN));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestHeaderSameSourceDataProvider")
	public void unifiedTestHeaderSameSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestHeaderSame(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestHeaderSameSourceDataProvider")
	Object[][] unifiedTestHeaderSameSourceDataProvider() throws Exception
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
    /// Many sections, some headers are linked to previous, different first/even/odd.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestHeaderMany(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Header\\TestHeaderMany", lf, sf);

        //The odd header is not really visible in the document because only two pages in the section.
        Section sect = doc.getFirstSection();

        // RK I don't know why page number is 5 in the model when reading from DOC,
        // but in other formats it is clearly 1.
        String firstHeaderText = sect.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_FIRST).getText();
        if (lf == LoadFormat.DOC)
        {
            msAssert.areEqual("First Header 1\t\t\u0013 PAGE \u00145\u0015\r", firstHeaderText);
        }
        else
        {
            msAssert.areEqual("First Header 1\t\t\u0013 PAGE \u00141\u0015\r", firstHeaderText);
        }

        msAssert.areEqual("Even Header 1\r", sect.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_EVEN).getText());
        msAssert.areEqual("Odd Header 1\r", sect.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getText());

        //There is only one page in this section, but all headers footers are linked to previous.
        sect = (Section)sect.getNextSibling();
        msAssert.areEqual(null, sect.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_FIRST));
        msAssert.areEqual(null, sect.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_EVEN));
        msAssert.areEqual(null, sect.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY));

        sect = (Section)sect.getNextSibling();
        //This is linked to previous
        msAssert.areEqual(null, sect.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_FIRST));
        msAssert.areEqual("\r", sect.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_EVEN).getText());
        //This is redefined
        msAssert.areEqual("Odd Header 2\r", sect.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestHeaderManySourceDataProvider")
	public void unifiedTestHeaderManySource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestHeaderMany(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestHeaderManySourceDataProvider")
	Object[][] unifiedTestHeaderManySourceDataProvider() throws Exception
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
    void unifiedTestHeaderClear(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Header\\TestHeaderClear", lf, sf);

        msAssert.areEqual("SimpleHeader\rSimpleFooter\rPage 1\r\fPage 2\f", doc.getText());
        doc.getFirstSection().clearHeadersFooters();
        msAssert.areEqual("Page 1\r\fPage 2\f", doc.getText());
        
        TestUtil.saveOpen(doc, "Model\\Header\\TestHeaderClear Modified", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestHeaderClearSourceDataProvider")
	public void unifiedTestHeaderClearSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestHeaderClear(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestHeaderClearSourceDataProvider")
	Object[][] unifiedTestHeaderClearSourceDataProvider() throws Exception
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
    public void testNoHeaders() throws Exception
    {
        Document doc = new Document();
        msAssert.areEqual(0, doc.selectNodes("//HeaderFooter").getCount());
    }

    @Test
    public void testHeaderFooterLinkToPrevious() throws Exception
    {
        Document doc = new Document();

        //Create a header and check it is linked.
        HeaderFooter hf = new HeaderFooter(doc, HeaderFooterType.HEADER_PRIMARY);
        doc.getSections().get(0).getHeadersFooters().add(hf);
        msAssert.areEqual(true, hf.isLinkedToPrevious());
        msAssert.areEqual(true, hf.isHeader());

        //Add some text to the header and check it is no longer linked.
        Paragraph para = new Paragraph(doc);
        para.appendChild(new Run(doc, "Hello"));
        hf.appendChild(para);
        msAssert.areEqual(false, hf.isLinkedToPrevious());

        //Setting linked to false a header that is not linked should not have changed it.
        hf.isLinkedToPrevious(false);
        msAssert.areEqual("Hello\r", hf.getText());

        //Setting a non empty header to be linked clears text.
        hf.isLinkedToPrevious(true);
        msAssert.areEqual(false, hf.hasChildNodes());
    }

    @Test
    public void testHeadersFootersLinkToPrevious() throws Exception
    {
        Document doc = new Document();

        //This actually creates all headers and footers.
        doc.getSections().get(0).getHeadersFooters().linkToPrevious(false);
        HeaderFooter hf = doc.getSections().get(0).getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);
        msAssert.areEqual(false, hf.isLinkedToPrevious());

        doc.getSections().get(0).getHeadersFooters().linkToPrevious(true);
        msAssert.areEqual(true, hf.isLinkedToPrevious());
    }

    /// <summary>
    /// WORDSNET-26552 Consider adding an option into Section.ClearHeadersFooters to keep watermarks.
    /// Tests the retainWatermarks parameter for the Section.ClearHeadersFooters() public method.
    /// </summary>
    @Test
    public void test26552() throws Exception
    {
        String[] watermarks = new String[]
        {
            "PowerPlusWaterMarkObject254598344", "PowerPlusWaterMarkObject254598345", "PowerPlusWaterMarkObject254598343",
            "PowerPlusWaterMarkObject254598346", "PowerPlusWaterMarkObject254598347", "PowerPlusWaterMarkObject254598348",
            "PowerPlusWaterMarkObject254598346", "PowerPlusWaterMarkObject1292134439", "PowerPlusWaterMarkObject1296749533",
            "PowerPlusWaterMarkObject1296749533", "PowerPlusWaterMarkObject1292134437", "PowerPlusWaterMarkObject1292134438",
            "PowerPlusWaterMarkObject1292134439", "PowerPlusWaterMarkObject1292134437", "WordPictureWatermark1292364843",
            "WordPictureWatermark1292364844", "WordPictureWatermark1292364845", "PowerPlusWaterMarkObject1292134439",
            "WordPictureWatermark1292364843", "PowerPlusWaterNoMarkObject62518", "PowerPlusWaterNoMarkObject62518",
            "WordPictureWatermark19187", "PowerPlusWaterNoMarkObject62517", "PowerPlusWaterMarkObject254598344",
            "PowerPlusWaterMarkObject62517", "WordPictureWatermark100001", "WordPictureWatermark24319",
            "PowerPlusWaterMarkObject254598343", "PowerPlusWaterMarkObject254598346", "PowerPlusWaterMarkObject254598347",
            "PowerPlusWaterMarkObject206392", "WordPictureWatermark165523", "PowerPlusWaterMarkObject254598346",
            "PowerPlusWaterMarkObject254598349", "PowerPlusWaterMarkObject254598350", "PowerPlusWaterMarkObject132896",
            "WordPictureWatermark94552", "PowerPlusWaterMarkObject254598349", "PowerPlusWaterMarkObject62517",
            "PowerPlusWaterMarkObject254598344", "PowerPlusWaterMarkObject62517", "WordPictureWatermark100001",
            "WordPictureWatermark24319", "PowerPlusWaterMarkObject254598343", "PowerPlusWaterMarkObject206392",
            "PowerPlusWaterMarkObject254598347", "PowerPlusWaterMarkObject206392", "WordPictureWatermark165523",
            "PowerPlusWaterMarkObject254598346", "WordPictureWatermark94552", "PowerPlusWaterMarkObject254598350",
            "PowerPlusWaterMarkObject132896", "WordPictureWatermark94552", "PowerPlusWaterMarkObject254598349",
            "WordPictureWatermark197457890", "WordPictureWatermark197457891", "WordPictureWatermark197457892",
            "WordPictureWatermark197457890"
        };

        Document doc = TestUtil.open("Model\\Header\\Test26552.docx");
        int nodeCount = doc.getChildNodes(NodeType.ANY, true).getCount();

        for (Section sect : (Iterable<Section>) doc.getSections())
            sect.clearHeadersFooters(true);

        msAssert.greater(nodeCount, doc.getChildNodes(NodeType.ANY, true).getCount());
        msAssert.areEqual(296, nodeCount);
        msAssert.areEqual(249, doc.getChildNodes(NodeType.ANY, true).getCount());

        int i = 0;
        for (Section sect : (Iterable<Section>) doc.getSections())
        {
            NodeCollection shapes = sect.getChildNodes(NodeType.SHAPE, true);
            for (Shape shape : (Iterable<Shape>) shapes)
            {
                msAssert.areEqual(watermarks[i], shape.getName());
                i++;
            }
            i++;
        }
    }

}

