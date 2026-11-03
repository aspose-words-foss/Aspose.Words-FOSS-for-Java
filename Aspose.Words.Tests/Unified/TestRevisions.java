// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.DateTime;
import java.awt.Color;
import java.util.ArrayList;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.msArray;
import com.aspose.DrColor;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests for revisions, includes both unified and regular tests.
/// </summary>
@Test
public class TestRevisions extends UnifiedTestsBase
{
    @BeforeMethod (alwaysRun = true)
    public void init()
    {
        RandomUtil.reset();
    }


    /// <summary>
    /// Test that applying style in revision is correctly processed in the model.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestRevisionAll(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRevisionAll", lf, sf);

        // ListId = 1 is correct here, because this is what the paragraph had before a revision.
        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 0, true);
        msAssert.areEqual(1, para.getParagraphFormat().getListId());

        doc.acceptAllRevisions();

        // ListBullet style was applied to paragraph in a revision, so after accept the ListId should be 2.
        msAssert.areEqual(2, para.getParagraphFormat().getListId());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionAllSourceDataProvider")
	public void unifiedTestRevisionAllSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionAll(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionAllSourceDataProvider")
	Object[][] unifiedTestRevisionAllSourceDataProvider() throws Exception
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
    void unifiedTestRevisionRun(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRevisionRun", lf, sf);

        NodeList runs = doc.selectNodes("//Run");

        //Test inserted text
        Run run = (Run)runs.get(1);
        msAssert.areEqual("inserted ", run.getText());

        EditRevision editRevision = run.getRunPr().getInsertRevision();
        msAssert.isNotNull(editRevision);
        msAssert.areEqual("RK", editRevision.getAuthor());
        msAssert.areEqual(new DateTime(2005, 3, 8, 15, 29, 0), editRevision.getDateTime());


        //Test deleted text
        run = (Run)runs.get(3);
        msAssert.areEqual("fore", run.getText());

        editRevision = run.getRunPr().getDeleteRevision();
        msAssert.isNotNull(editRevision);
        msAssert.areEqual("RK", editRevision.getAuthor());
        msAssert.areEqual(new DateTime(2005, 3, 8, 15, 37, 0), editRevision.getDateTime());


        // Test formatted text
        run = (Run)runs.get(5);
        msAssert.areEqual("revisions", run.getText());
        FormatRevision formatRevision = run.getRunPr().getFormatRevision();
        msAssert.isNull(run.getRunPr().getInsertRevision());
        msAssert.isNull(run.getRunPr().getDeleteRevision());

        msAssert.isNotNull(formatRevision);
        msAssert.areEqual(true, formatRevision.getRevPr().containsKey(FontAttr.BOLD));

        //Test inserted and formatted
        run = (Run)runs.get(6);
        msAssert.areEqual("xxx", run.getText());
        msAssert.isNotNull(run.getRunPr().getInsertRevision());
        msAssert.isNull(run.getRunPr().getDeleteRevision());

        msAssert.isNotNull(formatRevision);

        //Test how simple changes within one paragraph were accepted.
        msAssert.areEqual(true, doc.hasRevisions());
        doc.acceptAllRevisions();
        msAssert.areEqual(false, doc.hasRevisions());
        TestUtil.saveOpen(doc, "Model\\Revision\\TestRevisionRun Accepted", lf, sf);
        msAssert.areEqual("Text inserted be revisionsxxx.\f", doc.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionRunSourceDataProvider")
	public void unifiedTestRevisionRunSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionRun(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionRunSourceDataProvider")
	Object[][] unifiedTestRevisionRunSourceDataProvider() throws Exception
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
    /// Test two paragraphs got joined.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestRevisionJoinPara(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRevisionJoinPara", lf, sf);

        msAssert.areEqual(true, doc.hasRevisions());
        doc.acceptAllRevisions();
        msAssert.areEqual(false, doc.hasRevisions());
        TestUtil.saveOpen(doc, "Model\\Revision\\TestRevisionJoinPara Accepted", lf, sf);

        NodeList paras = doc.selectNodes("//Paragraph");
        msAssert.areEqual(2, paras.getCount());
        msAssert.areEqual("Para 2.\r", paras.get(0).getText());
        msAssert.areEqual("Para 3.\f", paras.get(1).getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionJoinParaSourceDataProvider")
	public void unifiedTestRevisionJoinParaSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionJoinPara(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionJoinParaSourceDataProvider")
	Object[][] unifiedTestRevisionJoinParaSourceDataProvider() throws Exception
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
    /// Test one paragraph got completely deleted and two others got joined.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestRevisionDeletePara(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRevisionDeletePara", lf, sf);

        msAssert.areEqual(true, doc.hasRevisions());
        doc.acceptAllRevisions();
        msAssert.areEqual(false, doc.hasRevisions());
        TestUtil.saveOpen(doc, "Model\\Revision\\TestRevisionDeletePara Accepted", lf, sf);

        NodeList paras = doc.selectNodes("//Paragraph");
        msAssert.areEqual(1, paras.getCount());
        msAssert.areEqual("Para 3.\f", paras.get(0).getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionDeleteParaSourceDataProvider")
	public void unifiedTestRevisionDeleteParaSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionDeletePara(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionDeleteParaSourceDataProvider")
	Object[][] unifiedTestRevisionDeleteParaSourceDataProvider() throws Exception
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
    /// Interesting enough, MS Word does not allow to delete a paragraph mark
    /// that is just before a table when track changes is turned on.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestRevisionDeleteParaBeforeTable(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRevisionDeleteParaBeforeTable", lf, sf);

        msAssert.areEqual(true, doc.hasRevisions());
        doc.acceptAllRevisions();
        msAssert.areEqual(false, doc.hasRevisions());
        TestUtil.saveOpen(doc, "Model\\Revision\\TestRevisionDeleteParaBeforeTable Accepted", lf, sf);

        //One empty before table, one in the table and one after.
        NodeList paras = doc.selectNodes("//Paragraph");
        msAssert.areEqual(3, paras.getCount());
        msAssert.areEqual("\r\u0007\u0007\f", doc.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionDeleteParaBeforeTableSourceDataProvider")
	public void unifiedTestRevisionDeleteParaBeforeTableSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionDeleteParaBeforeTable(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionDeleteParaBeforeTableSourceDataProvider")
	Object[][] unifiedTestRevisionDeleteParaBeforeTableSourceDataProvider() throws Exception
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
    /// Test inserting a paragraph accepted okay.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestRevisionInsertPara(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRevisionInsertPara", lf, sf);
        msAssert.areEqual(true, doc.hasRevisions());
        doc.acceptAllRevisions();
        msAssert.areEqual(false, doc.hasRevisions());
        TestUtil.saveOpen(doc, "Model\\Revision\\TestRevisionInsertPara Accepted", lf, sf);

        NodeList paras = doc.selectNodes("//Paragraph");
        msAssert.areEqual(2, paras.getCount());
        msAssert.areEqual("Para 2.\r", paras.get(0).getText());
        msAssert.areEqual("Para 1.\f", paras.get(1).getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionInsertParaSourceDataProvider")
	public void unifiedTestRevisionInsertParaSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionInsertPara(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionInsertParaSourceDataProvider")
	Object[][] unifiedTestRevisionInsertParaSourceDataProvider() throws Exception
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
    void unifiedTestRevisionJoinSection(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRevisionJoinSection", lf, sf);

        msAssert.areEqual(true, doc.hasRevisions());
        doc.acceptAllRevisions();
        msAssert.areEqual(false, doc.hasRevisions());
        TestUtil.saveOpen(doc, "Model\\Revision\\TestRevisionJoinSection Accepted", lf, sf);

        msAssert.areEqual(2, doc.getSections().getCount());
        msAssert.areEqual("Section 2.\r\f", doc.getSections().get(0).getText());
        msAssert.areEqual("Section 3.\f", doc.getSections().get(1).getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionJoinSectionSourceDataProvider")
	public void unifiedTestRevisionJoinSectionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionJoinSection(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionJoinSectionSourceDataProvider")
	Object[][] unifiedTestRevisionJoinSectionSourceDataProvider() throws Exception
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
    void unifiedTestRevisionDeleteHeader(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRevisionDeleteHeader", lf, sf);

        msAssert.areEqual(true, doc.hasRevisions());
        doc.acceptAllRevisions();
        msAssert.areEqual(false, doc.hasRevisions());
        TestUtil.saveOpen(doc, "Model\\Revision\\TestRevisionDeleteHeader Accepted", lf, sf);

        msAssert.areEqual("xxx\rSection 1.\fHeader 2.\rSection 2.\f", doc.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionDeleteHeaderSourceDataProvider")
	public void unifiedTestRevisionDeleteHeaderSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionDeleteHeader(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionDeleteHeaderSourceDataProvider")
	Object[][] unifiedTestRevisionDeleteHeaderSourceDataProvider() throws Exception
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
    void unifiedTestRevisionDeleteRow(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRevisionDeleteRow", lf, sf);

        msAssert.areEqual(true, doc.hasRevisions());
        doc.acceptAllRevisions();
        msAssert.areEqual(false, doc.hasRevisions());
        TestUtil.saveOpen(doc, "Model\\Revision\\TestRevisionDeleteRow Accepted", lf, sf);

        msAssert.areEqual(1, doc.selectNodes("//Row").getCount());
        msAssert.areEqual("Row 1\u0007\u0007\f", doc.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionDeleteRowSourceDataProvider")
	public void unifiedTestRevisionDeleteRowSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionDeleteRow(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionDeleteRowSourceDataProvider")
	Object[][] unifiedTestRevisionDeleteRowSourceDataProvider() throws Exception
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
    void unifiedTestRevisionDeleteTable(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRevisionDeleteTable", lf, sf);

        msAssert.areEqual(true, doc.hasRevisions());
        doc.acceptAllRevisions();
        msAssert.areEqual(false, doc.hasRevisions());
        TestUtil.saveOpen(doc, "Model\\Revision\\TestRevisionDeleteTable Accepted", lf, sf);

        msAssert.areEqual(0, doc.selectNodes("//Table").getCount());
        msAssert.areEqual("\f", doc.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionDeleteTableSourceDataProvider")
	public void unifiedTestRevisionDeleteTableSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionDeleteTable(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionDeleteTableSourceDataProvider")
	Object[][] unifiedTestRevisionDeleteTableSourceDataProvider() throws Exception
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
    void unifiedTestRevisionPara(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRevisionPara", lf, sf);

        //Check before the revision
        Paragraph para = (Paragraph)doc.selectSingleNode("//Paragraph");
        msAssert.areEqual(ParagraphAlignment.LEFT, para.getParagraphFormat().getAlignment());

        msAssert.areEqual(true, doc.hasRevisions());
        doc.acceptAllRevisions();
        msAssert.areEqual(false, doc.hasRevisions());
        TestUtil.saveOpen(doc, "Model\\Revision\\TestRevisionPara Accepted", lf, sf);

        //Test after accepting the revision
        msAssert.areEqual(ParagraphAlignment.CENTER, para.getParagraphFormat().getAlignment());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionParaSourceDataProvider")
	public void unifiedTestRevisionParaSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionPara(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionParaSourceDataProvider")
	Object[][] unifiedTestRevisionParaSourceDataProvider() throws Exception
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
    void unifiedTestRevisionSection(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRevisionSection", lf, sf);

        //Check paper size before the revision.
        Section section = doc.getSections().get(0);
        msAssert.areEqual(PaperSize.A4, section.getPageSetup().getPaperSize());

        msAssert.areEqual(true, doc.hasRevisions());
        doc.acceptAllRevisions();
        msAssert.areEqual(false, doc.hasRevisions());
        TestUtil.saveOpen(doc, "Model\\Revision\\TestRevisionSection Accepted", lf, sf);

        //Check the paper size was changed by accepting the revision.
        msAssert.areEqual(PaperSize.A5, section.getPageSetup().getPaperSize());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionSectionSourceDataProvider")
	public void unifiedTestRevisionSectionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionSection(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionSectionSourceDataProvider")
	Object[][] unifiedTestRevisionSectionSourceDataProvider() throws Exception
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
    void unifiedTestRevisionNumbered(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRevisionNumbered", lf, sf);

        // Inserted numbering.
        Paragraph para = (Paragraph)doc.selectSingleNode("//Paragraph[1]");
        ParagraphNumberRevision rev = para.getParaPr().getNumberRevision();
        msAssert.isNotNull(rev);
        msAssert.areEqual(5, para.getParaPr().getListId());
        msAssert.areEqual(true, rev.isInsertion());
        msAssert.areEqual(false, rev.getWasNumbered());
        msAssert.areEqual("", rev.getNumberFormat());

        // Deleted numbering.
        // RK MS Word does not seem to store deleted numbering revision in RTF.
        if ((lf != LoadFormat.RTF) && (sf != SaveFormat.RTF))
        {
            para = (Paragraph)doc.selectSingleNode("//Paragraph[3]");
            rev = para.getParaPr().getNumberRevision();
            msAssert.isNotNull(rev);
            msAssert.areEqual(0, para.getParaPr().getListId());
            msAssert.areEqual(false, rev.isInsertion());
            msAssert.areEqual(true, rev.getWasNumbered());
            msAssert.areEqual("\u0000.", rev.getNumberFormat());
        }

        msAssert.areEqual(true, doc.hasRevisions());
        doc.acceptAllRevisions();
        msAssert.areEqual(false, doc.hasRevisions());
        TestUtil.saveOpen(doc, "Model\\Revision\\TestRevisionNumbered Accepted", lf, sf);

        //After accepting the revision, the numbering revision is simply deleted.
        para = (Paragraph)doc.selectSingleNode("//Paragraph[1]");
        msAssert.areEqual(5, para.getParaPr().getListId());
        msAssert.areEqual(false, para.getParaPr().containsKey(RevisionAttr.NUMBER_REVISION));

        para = (Paragraph)doc.selectSingleNode("//Paragraph[3]");
        msAssert.areEqual(0, para.getParaPr().getListId());
        msAssert.areEqual(false, para.getParaPr().containsKey(RevisionAttr.NUMBER_REVISION));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionNumberedSourceDataProvider")
	public void unifiedTestRevisionNumberedSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionNumbered(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionNumberedSourceDataProvider")
	Object[][] unifiedTestRevisionNumberedSourceDataProvider() throws Exception
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
    void unifiedTestRevisionNumberAdded(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRevisionNumberAdded", lf, sf);

        // When list formatting was added to a paragraph:
        checkNumberRevision(doc, 0,
            true,    // list id is set
            true,    // is inserted true
            false,    // was numbered false
            "");    // format etc fields in number revision are empty
        msAssert.areEqual(true, doc.hasRevisions());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionNumberAddedSourceDataProvider")
	public void unifiedTestRevisionNumberAddedSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionNumberAdded(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionNumberAddedSourceDataProvider")
	Object[][] unifiedTestRevisionNumberAddedSourceDataProvider() throws Exception
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
    void unifiedTestRevisionNumberAccepted(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRevisionNumberAccepted", lf, sf);

        checkNoNumberRevision(doc, 0);
        msAssert.areEqual(false, doc.hasRevisions());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionNumberAcceptedSourceDataProvider")
	public void unifiedTestRevisionNumberAcceptedSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionNumberAccepted(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionNumberAcceptedSourceDataProvider")
	Object[][] unifiedTestRevisionNumberAcceptedSourceDataProvider() throws Exception
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
    void unifiedTestRevisionNumberRemoved(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRevisionNumberRemoved", lf, sf);

        // When list formatting was deleted from a paragraph:
        checkNumberRevision(doc, 0,
            false,    // list id is no longer set
            false,    // is inserted false
            true,    // was numbered true
            "\u0000.");    // format etc fields in number revisions are set
        msAssert.areEqual(true, doc.hasRevisions());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionNumberRemovedSourceDataProvider")
	public void unifiedTestRevisionNumberRemovedSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionNumberRemoved(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionNumberRemovedSourceDataProvider")
	Object[][] unifiedTestRevisionNumberRemovedSourceDataProvider() throws Exception
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
    /// WORDSNET-2078 Accepted revisions still affect HasRevisions.
    /// Same as TestRevisionNumberAccepted, but list is set in the style, not in the paragraph.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDefect2078(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestDefect2078", lf, sf);

        checkNoNumberRevision(doc, 0);
        msAssert.areEqual(false, doc.hasRevisions());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefect2078SourceDataProvider")
	public void unifiedTestDefect2078Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefect2078(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefect2078SourceDataProvider")
	Object[][] unifiedTestDefect2078SourceDataProvider() throws Exception
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

    private static void checkNumberRevision(
        Document doc,
        int paraIndex,
        boolean isListItem,
        boolean isInserted,
        boolean wasNumbered,
        String numberFormat)
    {
        Paragraph para = (Paragraph)doc.getFirstSection().getBody().getChild(NodeType.PARAGRAPH, paraIndex, true);
        msAssert.areEqual(isListItem, para.isListItem());
        msAssert.areEqual(isInserted, para.getParaPr().getNumberRevision().isInsertion());
        msAssert.areEqual(wasNumbered, para.getParaPr().getNumberRevision().getWasNumbered());
        msAssert.areEqual(numberFormat, para.getParaPr().getNumberRevision().getNumberFormat());
    }

    private static void checkNoNumberRevision(Document doc, int paraIndex)
    {
        Paragraph para = (Paragraph)doc.getFirstSection().getBody().getChild(NodeType.PARAGRAPH, paraIndex, true);
        msAssert.isNull(para.getParaPr().getNumberRevision());
    }

    /// <summary>
    /// WORDSNET-5515 Enable "Track changes" automatically when protect document with AllowOnlyRevisions protection type.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedAutoUpdateTrackRevisions(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // Test the legacy protect method.
        Document doc = new Document();
        msAssert.isFalse(doc.getTrackRevisions());
        // Setting this protection turns on revision tracking.
        doc.protect(ProtectionType.ALLOW_ONLY_REVISIONS);
        msAssert.isTrue(doc.getTrackRevisions());

        // Test the new protect method.
        doc = new Document();
        msAssert.isFalse(doc.getTrackRevisions());
        // Setting this protection turns on revision tracking.
        doc.protect(ProtectionType.ALLOW_ONLY_REVISIONS, "password");
        msAssert.isTrue(doc.getTrackRevisions());

        // When you open this document in MS Word, you should be able to edit it and the track changes will be on.
        TestUtil.saveOpen(doc, "Model\\Revision\\TestAutoUpdateTrackRevisions", lf, sf);

        // WORDSNET-7715 Setting another protection type does not turn it off if tracking is on already.
        msAssert.isTrue(doc.getTrackRevisions());
        doc.protect(ProtectionType.READ_ONLY);
        msAssert.isTrue(doc.getTrackRevisions());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedAutoUpdateTrackRevisionsSourceDataProvider")
	public void unifiedAutoUpdateTrackRevisionsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedAutoUpdateTrackRevisions(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedAutoUpdateTrackRevisionsSourceDataProvider")
	Object[][] unifiedAutoUpdateTrackRevisionsSourceDataProvider() throws Exception
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
    /// WORDSNET-5483 Track change disappears after open/save document using Aspose.Words.
    /// RK Our DOC importer used to skip zero highlight color for unknown reason. Fixed.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestRevisionHighlight(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRevisionHighlight", lf, sf);

        Run run = (Run)doc.getChild(NodeType.RUN, -1, true);
        msAssert.areEqual("Test.", run.getText());
        msAssert.areEqual(Color.YELLOW.getRGB(), run.getFont().getHighlightColor().getRGB());

        doc.acceptAllRevisions();

        msAssert.areEqual(0, run.getFont().getHighlightColor().getRGB());

        TestUtil.saveOpen(doc, "Model\\Revision\\TestRevisionHighlight Accepted", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionHighlightSourceDataProvider")
	public void unifiedTestRevisionHighlightSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionHighlight(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionHighlightSourceDataProvider")
	Object[][] unifiedTestRevisionHighlightSourceDataProvider() throws Exception
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
    /// WORDSNET-9012 When imported from RTF, TA fields are not deleted when revisions are accepted.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDeletedTAFields(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\TestDeletedTAFields", lf);

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        final int NOT_DELETED_FIELD_COUNT = 3;
        msAssert.areEqual(NOT_DELETED_FIELD_COUNT, fields.size());

        NodeCollection fieldStarts = doc.getChildNodes(NodeType.FIELD_START, true);
        NodeCollection fieldEnds = doc.getChildNodes(NodeType.FIELD_END, true);
        final int ALL_FIELDS_COUNT = 6;
        msAssert.areEqual(ALL_FIELDS_COUNT, fieldStarts.getCount());
        msAssert.areEqual(ALL_FIELDS_COUNT, fieldEnds.getCount());

        FieldStart fieldStart = (FieldStart)fieldStarts.get(2);
        msAssert.isTrue(fieldStart.isDeleteRevision());
        Run run = (Run)fieldStart.getNextSibling();
        msAssert.areEqual(" TA \\s \"voluptua\" ", run.getText());
        msAssert.isTrue(run.isDeleteRevision());

        FieldEnd fieldEnd = (FieldEnd)fieldEnds.get(2);
        msAssert.isTrue(fieldEnd.isDeleteRevision());

        doc.acceptAllRevisions();

        fields = FieldExtractor.extractToCollection(doc);
        msAssert.areEqual(NOT_DELETED_FIELD_COUNT, fields.size());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDeletedTAFieldsSourceDataProvider")
	public void unifiedTestDeletedTAFieldsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDeletedTAFields(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDeletedTAFieldsSourceDataProvider")
	Object[][] unifiedTestDeletedTAFieldsSourceDataProvider() throws Exception
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
    /// WORDSNET-9525 SmartTags are not removed when accept all revisions.
    /// Added deletion of smart tags when they become empty after deleting revisions.
    /// </summary>
    @Test
    public void testDeleteRevisionWithSmartTags() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\TestDeleteRevisionWithSmartTags.docx");
        doc.acceptAllRevisions();
        msAssert.areEqual(0, doc.getChildNodes(NodeType.SMART_TAG, true).getCount());
    }

    /// <summary>
    /// WORDSNET-19791 Accepting revisions in the cloned document affects the original document.
    /// Fixed. The problem was that paragraph break run properties were not cloned inside Paragraph.Clone.
    /// </summary>
    @Test
    public void testRevisionClone() throws Exception
    {
        Document docA = TestUtil.open("Model\\Revision\\TestRevisionClone.docx");
        Document docB = docA.deepClone();

        // Check run insert revision is cloned.
        Paragraph p0A = (Paragraph)docA.getChild(NodeType.PARAGRAPH, 0, true);
        Paragraph p0B = (Paragraph)docB.getChild(NodeType.PARAGRAPH, 0, true);
        checkRunRevisionCloned(p0A.getParagraphBreakRunPr(), p0B.getParagraphBreakRunPr(), RevisionAttr.INSERT_REVISION);
        checkRunRevisionCloned(p0A.getRuns().get(1).getRunPr(), p0B.getRuns().get(1).getRunPr(), RevisionAttr.INSERT_REVISION);

        // Check run delete revision is cloned.
        Paragraph p1A = (Paragraph)docA.getChild(NodeType.PARAGRAPH, 1, true);
        Paragraph p1B = (Paragraph)docB.getChild(NodeType.PARAGRAPH, 1, true);
        checkRunRevisionCloned(p1A.getParagraphBreakRunPr(), p1B.getParagraphBreakRunPr(), RevisionAttr.DELETE_REVISION);
        checkRunRevisionCloned(p1A.getRuns().get(1).getRunPr(), p1B.getRuns().get(1).getRunPr(), RevisionAttr.DELETE_REVISION);

        // Check run format revision is cloned.
        Paragraph p2A = (Paragraph)docA.getChild(NodeType.PARAGRAPH, 2, true);
        Paragraph p2B = (Paragraph)docB.getChild(NodeType.PARAGRAPH, 2, true);
        checkRunRevisionCloned(p2A.getParagraphBreakRunPr(), p2B.getParagraphBreakRunPr(), RevisionAttr.FORMAT_REVISION);
        checkRunRevisionCloned(p2A.getRuns().get(1).getRunPr(), p2B.getRuns().get(1).getRunPr(), RevisionAttr.FORMAT_REVISION);

        // Check para format revision is cloned.
        Paragraph p3A = (Paragraph)docA.getChild(NodeType.PARAGRAPH, 3, true);
        Paragraph p3B = (Paragraph)docB.getChild(NodeType.PARAGRAPH, 3, true);
        checkRunRevisionCloned(p3A.getParaPr(), p3B.getParaPr(), RevisionAttr.FORMAT_REVISION);
    }

    private static void checkRunRevisionCloned(AttrCollection a, AttrCollection b, int key)
    {
        Object revA = a.getDirectAttr(key);
        Object revB = b.getDirectAttr(key);
        msAssert.isNotNull(revA);
        msAssert.isNotNull(revB);
        msAssert.areNotSame(revA, revB);
    }

    /// <summary>
    /// WORDSNET-1095 Nodes with revisions might not copy well into a different document.
    /// Fixed. Revision attributes collection is stored inside the main attributes collection and the revision
    /// attributes collection can contain a reference to a list or to a style and that needs importing too, done that.
    /// There are some differences in formatting possible, but they seem to be not related to revisions.
    /// </summary>
    @Test
    public void testRevisionImport() throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\Revision\\TestRevisionClone.docx");
        Document dstDoc = new Document();

        dstDoc.removeAllChildren();
        dstDoc.appendDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        TestUtil.saveOpen(dstDoc, "Model\\Revision\\TestRevisionImport.docx");
    }


    /// <summary>
    /// Relates to WORDSNET-8423
    /// Tests that PositiveDifference is calculated properly for runs.
    /// </summary>
    @Test
    public void testJira8423_Runs() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestJira8423_Runs.docx");

        // 1
        Run run = (Run)doc.getChild(NodeType.RUN, 0, true);
        RunPr revRunPr = (RunPr)run.getRunPr().getFormatRevision().getRevPr();

        msAssert.isNull(run.getRunPr().get(FontAttr.ITALIC));
        msAssert.areEqual(1, revRunPr.getCount());
        msAssert.areEqual(AttrBoolEx.TRUE, revRunPr.get(FontAttr.ITALIC));

        // 2
        run = (Run)doc.getChild(NodeType.RUN, 1, true);
        revRunPr = (RunPr)run.getRunPr().getFormatRevision().getRevPr();

        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().get(FontAttr.BOLD));
        msAssert.areEqual(2, revRunPr.getCount());
        msAssert.areEqual(AttrBoolEx.FALSE, revRunPr.get(FontAttr.BOLD));
    }

    /// <summary>
    /// Relates to WORDSNET-8423
    /// Deep test PositiveDifference calculation if both style and paragraph was revised.
    /// </summary>
    @Test
    public void testJira8423_BothStyleAndParagraph() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestJira8423_BothStyleAndParagraph.docx");

        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 0, true);
        ParaPr revParaPr = (ParaPr)para.getParaPr().getFormatRevision().getRevPr();

        // Revised attributes is inherited from revised style.
        // AM. Actually we don't need any attributes here because they all inherited but so far it's OK.
        msAssert.areEqual(0x01, revParaPr.get(ParaAttr.ISTD));
        msAssert.areEqual(ParagraphAlignment.RIGHT, revParaPr.get(ParaAttr.ALIGNMENT));
        msAssert.areEqual(2, revParaPr.getCount());

        Style heading1 = doc.getStyles().getByIstd(0x01, false);

        revParaPr = (ParaPr)heading1.getParaPr().getFormatRevision().getRevPr();
        msAssert.areEqual(ParagraphAlignment.CENTER, revParaPr.get(ParaAttr.ALIGNMENT));
        msAssert.areEqual(0, revParaPr.get(ParaAttr.SPACE_AFTER));
        msAssert.areEqual(2, revParaPr.getCount());
    }

    /// <summary>
    /// Relates to WORDSNET-8423
    /// Tests style PositiveDifference calculation for attribute defined in base style.
    /// </summary>
    @Test
    public void testJira8423_TestStyleRevisions() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\TestStyleRevisions.docx");

        // Style has Italic=true and at seems that we need to compensate it by Italic=false in positive-difference but it's wrong.
        // Italic=true is also defined in base "SubtleEmphasis" style and revised properties should inherit from this base.
        // That's why we need to collapse over fully resolved attributes.
        Style emphasis = doc.getStyles().getByIstd(0x1c, false);
        RunPr revRunPr = (RunPr)emphasis.getRunPr().getFormatRevision().getRevPr();
        msAssert.areEqual(AttrBoolEx.TRUE, emphasis.getRunPr().get(FontAttr.ITALIC));
        msAssert.isNull(revRunPr.get(FontAttr.ITALIC));

        // This file is also perfect example why we need to join ThemeColor and ThemeTint into one attribute.
        // We inherit ThemeTint here as standalone attribute so result color is different: we have "text1" + "7F" tint in original properties and
        // "accent1" in positive-difference and get "accent1" + "7F" after revision accepted but need only "accent1".
        // Of course we can made some hack but we need it in AcceptRevision, in Style.ExpandRunPr, in Paragraph.GetExpandedRunPr and so on.
        msAssert.areEqual("text1", emphasis.getRunPr().get(FontAttr.THEME_COLOR));
        msAssert.areEqual("7F", emphasis.getRunPr().get(FontAttr.THEME_TINT));

        msAssert.areEqual("accent1", revRunPr.get(FontAttr.THEME_COLOR));
        msAssert.isEmpty((String)revRunPr.get(FontAttr.THEME_TINT));

        emphasis.getRunPr().acceptFormatRevision();
        msAssert.areEqual("accent1", emphasis.getRunPr().get(FontAttr.THEME_COLOR));

        // Default value is expected.
        msAssert.isEmpty((String)emphasis.getRunPr().get(FontAttr.THEME_TINT));
    }


    /// <summary>
    /// Relates to WORDSNET-8423
    /// Shows problem with style format revision and document defaults.
    /// </summary>
    @Test
    public void testJira8423_StyleAndDefaults() throws Exception
    {
        // Very weird Word behavior. Seems to be some of compatibility hack.
        // If style has at least one property defined for either paragraph or run in original attributes,
        // paragraphs of this style should not use Document Defaults.
        for (int i = 1; i <= 3; i++)
        {
            Document doc = TestUtil.open(msString.format("Model\\Revision\\TestJira8423_StyleAndDefaults{0}.xml", i));

            Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
            Run run = para.getFirstRun();
            ParaPr paraPr = para.getExpandedParaPr(ParaPrExpandFlags.LAYOUT);
            RunPr runPr = run.getExpandedRunPr(RunPrExpandFlags.LAYOUT);

            // Expanded properties is absolutely the same for every document. This is wrong.
            // Word renders original markup differently:
            //  1. Style has no attributes in both original and final. Everything fine.
            //  2. Style has only Size in original. Word shows SpaceAfter = 0, 10pt Times New Roman.
            //  3. Style has only SpaceAfter in original. Word shows 10pt Times New Roman.
            //
            // Word does this hack only for styles. Paragraph or Run revision is OK.
            msAssert.areEqual(1600, paraPr.get(ParaAttr.SPACE_AFTER));
            msAssert.areEqual(ThemeFontCore.MINOR_H_ANSI, ((ComplexFontName)runPr.get(FontAttr.NAME_ASCII)).getThemeFontCore());
        }

        // Additional test for this issue.
        Document awRejectedDoc = TestUtil.open("Model\\Revision\\TestJira8423_StyleAndDefaults2.xml");
        // Verify that no spacing or font are specified in originals before rejection.
        Style style = awRejectedDoc.getStyles().getByName("Normal Test", false);
        msAssert.isNull(style.getParaPr().get(ParaAttr.SPACE_AFTER));
        msAssert.isNull(style.getRunPr().get(FontAttr.NAME_ASCII));

        awRejectedDoc.getRevisions().rejectAll();

        // Spacing and font are still not redefined and thus are inherited from DocumentDefaults.
        Style awStyle = awRejectedDoc.getStyles().getByName("Normal Test", false);
        msAssert.isNull(awStyle.getParaPr().get(ParaAttr.SPACE_AFTER));
        msAssert.isNull(awStyle.getRunPr().get(FontAttr.NAME_ASCII));

        // This file is saved in Word after all revisions are rejected in UI.
        Document msRejectedDoc = TestUtil.open("Model\\Revision\\TestJira8423_StyleAndDefaults2 ms Rejected.xml");

        // Word makes "hack" and writes SpaceAfter = 0, Single LineSpacing and Times New Roman directly to style.
        Style msStyle = msRejectedDoc.getStyles().getByName("Normal Test", false);
        msAssert.areEqual(0, msStyle.getParaPr().get(ParaAttr.SPACE_AFTER));
        msAssert.areEqual("Times New Roman", ((ComplexFontName)msStyle.getRunPr().get(FontAttr.NAME_ASCII)).getName());
    }

    /// <summary>
    /// Relates to WORDSNET-8423
    /// Test PositiveDifference calculation for inherited styles.
    /// </summary>
    /// <remarks>
    /// AM. I have strong feeling that calculation must be done in certain order, started from most bottom style,
    /// but failed to make such test.
    /// For this test positive-difference is calculated correctly regardless of calculation order.
    /// </remarks>
    @Test
    public void testJira8423_DifferenceInheritance() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestJira8423_DifferenceInheritance.docx");

        // 30pt space after is directly specified for Style1.
        Style style1 = doc.getStyles().getByName("Style1", false);
        msAssert.areEqual(600, style1.getParaPr().get(ParaAttr.SPACE_AFTER));

        // Style2 is based on Style1, define some spacing for original and has no directly specified spacing for final.
        // Positive-difference must resolve this value from base style.
        Style style2 = doc.getStyles().getByName("Style2", false);
        msAssert.areEqual(600, style2.getParaPr().getFormatRevision().getRevPr().get(ParaAttr.SPACE_AFTER));

        // The same for Style3: Positive-difference must resolve this value from base style.
        Style style3 = doc.getStyles().getByName("Style3", false);
        msAssert.areEqual(600, style3.getParaPr().getFormatRevision().getRevPr().get(ParaAttr.SPACE_AFTER));
    }


    /// <summary>
    /// Tests how style revision is imported/exported in various format.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestStyleRevisions(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // Style revision is not supported in RTF.
        if ((lf == LoadFormat.RTF) || (sf == SaveFormat.RTF))
            return;

        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestStyleRevisions", lf, sf);

        Style style = doc.getStyles().getBySti(StyleIdentifier.HEADING_2, false);

        msAssert.isNotNull(style.getParaPr().getFormatRevision());

        ParaPr paraPr = style.getParaPr();

        // Test few attributes in original and final properties.
        msAssert.areEqual(1, paraPr.get(ParaAttr.LIST_LEVEL));
        msAssert.areEqual(1, paraPr.get(ParaAttr.LIST_ID));
        msAssert.areEqual(200, paraPr.get(ParaAttr.SPACE_BEFORE));
        msAssert.isNull(paraPr.get(ParaAttr.SPACE_AFTER));
        msAssert.areEqual(OutlineLevel.LEVEL_2, paraPr.get(ParaAttr.OUTLINE_LEVEL));
        msAssert.areEqual(276, ((LineSpacing)paraPr.get(ParaAttr.LINE_SPACING)).getValue());
        msAssert.areEqual(LineSpacingRule.MULTIPLE, ((LineSpacing)paraPr.get(ParaAttr.LINE_SPACING)).getRule());

        ParaPr revParaPr = (ParaPr)style.getParaPr().getFormatRevision().getRevPr();
        msAssert.areEqual(80, revParaPr.get(ParaAttr.SPACE_BEFORE));
        msAssert.areEqual(0, revParaPr.get(ParaAttr.SPACE_AFTER));
        msAssert.areEqual(480, ((LineSpacing)revParaPr.get(ParaAttr.LINE_SPACING)).getValue());
        msAssert.areEqual(LineSpacingRule.MULTIPLE, ((LineSpacing)revParaPr.get(ParaAttr.LINE_SPACING)).getRule());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestStyleRevisionsSourceDataProvider")
	public void unifiedTestStyleRevisionsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestStyleRevisions(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestStyleRevisionsSourceDataProvider")
	Object[][] unifiedTestStyleRevisionsSourceDataProvider() throws Exception
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
    /// WORDSNET-8664 ( fixed) - Document.AcceptAllRevisions do not work correctly.
    /// Improved revision handling for conflicting situation when cell is inserted into a row marked as deleted.
    /// </summary>
    @Test
    public void testJira8664() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\TestJira8664.docx");
        doc.acceptAllRevisions();
        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        msAssert.areEqual(2, table.getRows().getCount());
    }






    /// <summary>
    /// WORDSNET-10582 Revision.Accept does not work for Docx
    /// Enumerator should become invalid after revision is accepted/rejected.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testJira10582Accept() throws Exception
    {
        Document doc = createTestDocument();

        for (Revision revision : doc.getRevisions())
            revision.accept();
    }

    /// <summary>
    /// WORDSNET-10582 Revision.Accept does not work for Docx
    /// Enumerator should become invalid after revision is accepted/rejected.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testJira10582Reject() throws Exception
    {
        Document doc = createTestDocument();

        for (Revision revision : doc.getRevisions())
            revision.reject();
    }

    /// <summary>
    /// WORDSNET-10582 Revision.Accept does not work for Docx
    /// Enumerator should become invalid after revision is accepted/rejected.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testJira10582AcceptAll() throws Exception
    {
        Document doc = createTestDocument();

        for (Revision revision : doc.getRevisions())
            doc.getRevisions().acceptAll();
    }

    /// <summary>
    /// WORDSNET-10582 Revision.Accept does not work for Docx
    /// Enumerator should become invalid after revision is accepted/rejected.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testJira10582RejectAll() throws Exception
    {
        Document doc = createTestDocument();

        for (Revision revision : doc.getRevisions())
            doc.getRevisions().rejectAll();
    }


    @Test
    public void testJira10571B() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\TestJira10571B.docx");

        doc.acceptAllRevisions();
        msAssert.areEqual(0, doc.getRevisions().getCount());
        msAssert.areEqual(17, doc.getFirstSection().getBody().getParagraphs().getCount());
    }




    /// <summary>
    /// WORDSNET-10575 Revision without timestamp was changed during conversion.
    /// MSW allows revisions without timestamps. In this case:
    /// Zero value for a dttm structure for a DOC or RTF formats.
    /// Missing of "w:date" revision's attribute for DOCX.
    /// Missing of "aml:createdate" revision's attribute for WML.
    /// Missing of "\\revdttm" control word for RTF.
    /// <see cref="RevisionBase.DateTime"/> stores DateTime.MinValue in this case.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestNoTimeStampInRevision(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestNoTimeStampInRevision", lf, sf);
        Revision revision = doc.getRevisions().get(0);
        msAssert.areEqual(DateTime.MinValue, revision.getDateTimeInternal());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestNoTimeStampInRevisionSourceDataProvider")
	public void unifiedTestNoTimeStampInRevisionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestNoTimeStampInRevision(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestNoTimeStampInRevisionSourceDataProvider")
	Object[][] unifiedTestNoTimeStampInRevisionSourceDataProvider() throws Exception
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

    // FOSS: TestJira15133 removed. It roundtripped the rsid table of a binary .doc; the Word .doc->.docx
    // conversion strips the rsid table entirely, so there is nothing to roundtrip. The rsid-table
    // roundtrip is still covered by UnifiedTestRsidTable (FlatOpc input) below.


    private static Document createTestDocument() throws Exception
    {
        Document doc = new Document();
        doc.startTrackRevisions("amorozov");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("Hello World!");
        builder.writeln("Bye!");

        return doc;
    }


    /// <summary>
    /// Tests that the IsFormatRevision properties of the Paragraph and Inline classes return correct value.
    /// </summary>
    @Test
    public static void testIsFormatRevisionFlag() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\TestIsFormatRevisionFlag.docx");

        Paragraph paragraph1 = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.isTrue(paragraph1.isFormatRevision(), "Wrong IsFormatRevision value for paragraph 1.");
        msAssert.isFalse(paragraph1.getFirstRun().isFormatRevision(), "Wrong IsFormatRevision value for run 1.");

        Paragraph paragraph2 = (Paragraph)paragraph1.getNextNonAnnotationSibling();
        msAssert.isFalse(paragraph2.isFormatRevision(), "Wrong IsFormatRevision value for paragraph 2.");
        msAssert.isTrue(paragraph2.getFirstRun().isFormatRevision(), "Wrong IsFormatRevision value for run 2.");

        Paragraph paragraph3 = (Paragraph)paragraph2.getNextSibling();
        msAssert.isFalse(paragraph3.isFormatRevision(), "Wrong IsFormatRevision value for paragraph 3.");
        msAssert.isFalse(paragraph3.getFirstRun().isFormatRevision(), "Wrong IsFormatRevision value for run 3.");
    }





    /// <summary>
    /// WORDSNET-14046 Support edit revisions for table cells.
    /// Implemented support for w:cellIns and w:cellDel elements.
    /// </summary>
    @Test
    public void testJira14046() throws Exception
    {
        // Check w:cellIns.
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestJira14046Ins", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        Cell cell = doc.getFirstSection().getBody().getTables().get(0).getRows().get(0).getLastCell();
        msAssert.isTrue(cell.getCellPr().hasInsertRevision());

        // Check w:cellDel.
        doc = TestUtil.openSaveOpen("Model\\Revision\\TestJira14046Del", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        cell = doc.getFirstSection().getBody().getTables().get(0).getRows().get(0).getLastCell();
        msAssert.isTrue(cell.getCellPr().hasDeleteRevision());
    }


    /// <summary>
    /// Relates to WORDSNET-14309 Issue with cell revision accept/reject.
    /// </summary>
    @Test
    public void testCellRevisionAccept() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\TestCellRevisionAccept.docx");
        msAssert.areEqual(1, doc.getRevisions().getCount());

        doc.acceptAllRevisions();
        // Force collection rebuild.
        doc.markChanged();

        msAssert.areEqual(0, doc.getRevisions().getCount());
    }

    // FOSS: TestJira14997 / Test19608 / Test19608Export removed. Their inputs were binary .doc and
    // asserted .doc-specific representations lost on the Word .doc->.docx conversion: the Word97 legacy
    // indent-compat attrs (Sys_LeftIndent97/Sys_FirstLineIndent97) don't exist in OOXML, and the exact
    // list-revision structure that made AcceptAllRevisions drop the indent isn't preserved. The .doc
    // reader is removed in FOSS, so these scenarios can't be reproduced.


    /// <summary>
    /// WORDSNET-14290 Support Structured Document Tag edit revisions (customXmlDelRangeStart/customXmlDelRangeEnd).
    /// Implemented support for Structured Document Tag edit revisions.
    /// </summary>
    /// <remarks>Tests block level SDT revisions.</remarks>
    @Test
    public void testJira14290BlockLevelSdt() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestBlockLevelSdtRevision", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        msAssert.areEqual(13, doc.getRevisions().getCount());

        NodeCollection sdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);

        checkSdtHasDeleteRevision((StructuredDocumentTag)sdts.get(0));
        checkSdtHasDeleteRevision((StructuredDocumentTag)sdts.get(1));
        checkSdtHasDeleteRevision((StructuredDocumentTag)sdts.get(2));
        checkSdtHasInsertRevision((StructuredDocumentTag)sdts.get(3));

        checkAcceptSdtRevision(doc, 1, "☒\f");
        checkRejectSdtRevision(doc, 3, "ds\rdsdsds\r\r");
    }

    /// <summary>
    /// WORDSNET-14290 Support Structured Document Tag edit revisions (customXmlDelRangeStart/customXmlDelRangeEnd).
    /// Implemented support for Structured Document Tag edit revisions.
    /// </summary>
    /// <remarks>Tests inline level SDT revisions.</remarks>
    @Test
    public void testJira14290InlineLevelSdt() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestInlineLevelSdtRevision", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        msAssert.areEqual(4, doc.getRevisions().getCount());

        NodeCollection sdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);

        checkSdtHasDeleteRevision((StructuredDocumentTag)sdts.get(0));
        checkSdtHasInsertRevision((StructuredDocumentTag)sdts.get(1));

        checkAcceptSdtRevision(doc, 1, "Choose an item.");
        checkRejectSdtRevision(doc, 1, "zxcvb");
    }

    /// <summary>
    /// WORDSNET-14290 Support Structured Document Tag edit revisions (customXmlDelRangeStart/customXmlDelRangeEnd).
    /// Implemented support for Structured Document Tag edit revisions.
    /// </summary>
    /// <remarks>Tests row level SDT revisions.</remarks>
    @Test
    public void testJira14290RowLevelSdt() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestRowLevelSdtRevision", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        msAssert.areEqual(4, doc.getRevisions().getCount());

        NodeCollection sdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);

        checkSdtHasDeleteRevision((StructuredDocumentTag)sdts.get(0));
        checkSdtHasInsertRevision((StructuredDocumentTag)sdts.get(1));

        checkAcceptSdtRevision(doc, 1, "ddddddddddddddd\u0007");
        checkRejectSdtRevision(doc, 1, "asdfghjkl\u0007");
    }

    /// <summary>
    /// WORDSNET-14290 Support Structured Document Tag edit revisions (customXmlDelRangeStart/customXmlDelRangeEnd).
    /// Implemented support for Structured Document Tag edit revisions.
    /// </summary>
    /// <remarks>Tests revisions for SDT with data binding.</remarks>
    @Test
    public void testJira14290DataBindingSdt() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\TestDataBindingSdtRevision.docx");

        // WORDSNET-16303 When track changes SDT content is updated even for DOCX. Reset this flag to pass this test.
        doc.setTrackRevisions(false);
        doc = TestUtil.saveOpen(doc, "Model\\Revision\\TestDataBindingSdtRevision", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        msAssert.areEqual(4, doc.getRevisions().getCount());

        NodeCollection sdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);

        checkSdtHasDeleteRevision((StructuredDocumentTag)sdts.get(0));
        checkSdtHasInsertRevision((StructuredDocumentTag)sdts.get(1));

        checkAcceptSdtRevision(doc, 1, "Second\f");
        checkRejectSdtRevision(doc, 1, "22222222222\r");
    }

    /// <summary>
    /// WORDSNET-15445 Annoying empty SDTs and unremoved move range nodes were present after accepting revisions
    /// in the test documents.
    /// </summary>
    @Test (dataProvider = "testMovedSdtsDataProvider")
    public void testMovedSdts(String fileName, int expectedSdtCount) throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\" + fileName);

        doc.acceptAllRevisions();

        msAssert.areEqual(expectedSdtCount, doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).getCount());
        checkMoveRangeNodesAreRemoved(doc, "");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testMovedSdtsDataProvider")
	public static Object[][] testMovedSdtsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TestJira15445.docx",  1},
			{"TestMovedSdts1.docx",  2},
			{"TestMovedSdts2.docx",  2},
		};
	}


    /// <summary>
    /// Tests that move revision is either preserved or converted to insertion/deletion depending on output format.
    /// </summary>
    /// <param name="scenario">Scenario/output format.</param>
    /// <param name="isExpectedMove">If <c>true</c>, it is expected that a move revision will be preserved as move;
    /// if <c>false</c>, it will be converted to an edit revision.</param>
    // FOSS: Wml/Doc/Rtf writers removed. Keep the Docx roundtrip case (move revisions are preserved).
    @Test (dataProvider = "testExportingMoveRevisionDataProvider")
    public void testExportingMoveRevision(/*UnifiedScenario*/int scenario, boolean isExpectedMove) throws Exception
    {
        final String FILE_NAME = "Model\\Revision\\TestSdtMovingRevisions";
        Document doc = TestUtil.open(FILE_NAME, LoadFormat.DOCX);

        Paragraph para = doc.getFirstSection().getBody().getParagraphs().get(1);
        checkRevisionPresence(para, para.getFirstRun(), "Block-level SDT", true, true);

        para = doc.getFirstSection().getBody().getParagraphs().get(8);
        checkRevisionPresence(para, para.getFirstRun(), "Block-level SDT", true, false);

        doc = TestUtil.saveOpen(doc, FILE_NAME, scenario | UnifiedScenario.NO_GOLD);

        para = doc.getFirstSection().getBody().getParagraphs().get(1);
        checkRevisionPresence(para, para.getFirstRun(), "Block-level SDT", isExpectedMove, true);

        para = doc.getFirstSection().getBody().getParagraphs().get(8);
        checkRevisionPresence(para, para.getFirstRun(), "Block-level SDT", isExpectedMove, false);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testExportingMoveRevisionDataProvider")
	public static Object[][] testExportingMoveRevisionDataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.DOCX_2_DOCX,  true},
		};
	}

    /// <summary>
    /// Checks presence of move-to, move-from, insert and delete revisions in the specified paragraph and run.
    /// </summary>
    /// <param name="para">Paragraph to check.</param>
    /// <param name="run">Run to check.</param>
    /// <param name="expectedRunText">Expected run text.</param>
    /// <param name="isExpectedMove">If <c>true</c>, we expect presence of a move-from or move-to revision.</param>
    /// <param name="isExpectedRemoval">If <c>true</c>, we expects presence of a move-from or delete revision.</param>
    private static void checkRevisionPresence(Paragraph para, Run run, String expectedRunText,
        boolean isExpectedMove, boolean isExpectedRemoval)
    {
        // Check that expected run is tested.
        msAssert.areEqual(expectedRunText, run.getText());

        msAssert.areEqual(isExpectedMove && !isExpectedRemoval, para.isMoveToRevision());
        msAssert.areEqual(isExpectedMove && isExpectedRemoval, para.isMoveFromRevision());
        msAssert.areEqual(!isExpectedMove && !isExpectedRemoval, para.isInsertRevision());
        msAssert.areEqual(!isExpectedMove && isExpectedRemoval, para.isDeleteRevision());

        msAssert.areEqual(isExpectedMove && !isExpectedRemoval, run.isMoveToRevision());
        msAssert.areEqual(isExpectedMove && isExpectedRemoval, run.isMoveFromRevision());
        msAssert.areEqual(!isExpectedMove && !isExpectedRemoval, run.isInsertRevision());
        msAssert.areEqual(!isExpectedMove && isExpectedRemoval, run.isDeleteRevision());
    }

    /// <summary>
    /// Tests reading/writing and accepting moving revisions (customXmlMoveXXXRangeStart/customXmlMoveXXXRangeEnd)
    /// of structured document tags.
    /// </summary>
    @Test
    public void testSdtMovingRevision() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Revision\\TestSdtMovingRevisions",
            UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        NodeCollection sdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);

        // All SDTs are moved down that all moved-from SDTs are before moved-to ones.
        int count = sdts.getCount();
        for (int i = 0; i < count; i++)
        {
            StructuredDocumentTag sdt = (StructuredDocumentTag)sdts.get(i);
            // Word has not marked inline-level SDTs with move revision, skip them.
            if (sdt.getLevel() != MarkupLevel.INLINE)
            {
                if (i < count / 2)
                    msAssert.isTrue(sdt.getContentsRunPr().hasMoveFromRevision());
                else
                    msAssert.isTrue(sdt.getContentsRunPr().hasMoveToRevision());
            }
            else
            {
                msAssert.isFalse(sdt.getContentsRunPr().hasRevisions());
            }
        }

        Document clone = doc.deepClone();
        doc.acceptAllRevisions();
        msAssert.areEqual("Text before block SDT.\rParagraph before table.\r\u0007\u0007\u0007\u0007Cell 1\u0007Cell 2.\u0007\u0007\u0007\u0007" +
                "\u0007\u0007\u0007\rMove destination.\rBlock-level SDT.\rContains two paragraphs.\rText after block SDT." +
                "\rParagraph that contains inline-level SDT.\rText after move-to range.\f", doc.getText());

        clone.getRevisions().rejectAll();
        msAssert.areEqual("Text before block SDT.\rBlock-level SDT.\rContains two paragraphs.\rText after " +
                "block SDT.\rParagraph that contains inline-level SDT.\rParagraph before table.\rCell 1\u0007" +
                "Cell 2.\u0007\u0007\u0007\u0007\u0007\u0007\u0007\u0007\u0007\u0007\u0007\rMove destination.\rText after move-to range.\f", clone.getText());
    }


    /// <summary>
    /// Accepts revisions one by one in order required for move revisions.
    /// </summary>
    private static void acceptRevisionsOneByOne(RevisionCollection revisions) throws Exception
    {
        for (Revision revision : revisions.toList())
        {
            if (revision.getParentNode().getNodeLevel() == NodeLevel.INLINE)
                revision.accept();
        }

        for (Revision revision : revisions.toList())
        {
            if (revision.getParentNode().getNodeType() == NodeType.PARAGRAPH)
                revision.accept();
        }

        for (Revision revision : revisions.toList())
            revision.accept();
    }

    /// <summary>
    /// Checks result of acceptance of document with move ranges included structured document tags.
    /// </summary>
    private static void checkRevisionAcceptanceResult(Document doc, String expectedText,
        int expectedSdtCount, int[] expectedInlineSdts, String testSubject)
    {
        msAssert.areEqual(expectedText, doc.getText(), testSubject);

        ArrayList<StructuredDocumentTag> sdts =
            doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).<StructuredDocumentTag>toList();
        msAssert.areEqual(expectedSdtCount, sdts.size(), testSubject);

        for (int i = 0; i < sdts.size(); i++)
        {
            if ((expectedInlineSdts != null) && (msArray.indexOf((expectedInlineSdts), (i), 0, (expectedInlineSdts).length) >= 0))
                msAssert.areEqual(MarkupLevel.INLINE, sdts.get(i).getLevel(), testSubject);
            else
                msAssert.areNotEqual(MarkupLevel.INLINE, sdts.get(i).getLevel(), testSubject);
        }

        msAssert.areEqual(0, doc.getRevisions().getCount(), testSubject);
        checkMoveRangeNodesAreRemoved(doc, testSubject);
    }


    /// <summary>
    /// WORDSNET-14692 Tests that revisions are also taken from fallback shape segments and are accepted.
    /// </summary>
    @Test
    public void testRevisionsInFallback() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\TestRevisionsInFallback.docx");
        msAssert.areEqual(6, doc.getRevisions().getCount());

        doc.acceptAllRevisions();

        msAssert.areEqual(0, doc.getRevisions().getCount());
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        NodeCollection collection = new NodeCollection(shape.getFallbackShape(), new RevisionNodeMatcher(), true);
        msAssert.areEqual(0, collection.getCount());
    }

    /// <summary>
    /// WORDSNET-15871 Document.AcceptAllRevisions throws System.InvalidOperationException
    /// We should not try to remove the same empty row twice in <see cref="RevisionUtil.DeleteLastParaFromCell"/>.
    /// </summary>
    @Test
    public void testJira15871() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\TestJira15871.docx");
        doc.acceptAllRevisions();

        msAssert.isFalse(doc.hasRevisions());
        msAssert.areEqual(1, doc.getFirstSection().getBody().getTables().getCount());
    }




    /// <summary>
    /// WORDSNET-18903 ListLabel.LabelString returns incorrect value when paragraph has revision.
    /// For customer case, <see cref="ListLabel.LabelStringOriginal"/> and <see cref="ListLabel.LabelStringFinal"/>
    /// are required. Added property <see cref="Document.RevisionsView"/>.
    /// </summary>
    @Test
    public void test18903() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\Test18903.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        // RevisionsView.Original by default.
        msAssert.areEqual("1.", paras.get(0).getListLabel().getLabelString());
        msAssert.areEqual("a.", paras.get(1).getListLabel().getLabelString());
        msAssert.areEqual("", paras.get(2).getListLabel().getLabelString());

        doc.setRevisionsView(RevisionsView.FINAL);
        msAssert.areEqual("", paras.get(0).getListLabel().getLabelString());
        msAssert.areEqual("1.", paras.get(1).getListLabel().getLabelString());
        msAssert.areEqual("a.", paras.get(2).getListLabel().getLabelString());
    }




    /// <summary>
    /// WORDSNET-13113 ParagraphFormat.StyleName returns incorrect style name after track changes
    /// Improved ParagraphFormat.Style, ParagraphFormat.StyleName to get RevisionsView aware values.
    /// </summary>
    @Test
    public void test13113() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\Test13113.docx");

        doc.setRevisionsView(RevisionsView.ORIGINAL);
        msAssert.areEqual("OldStyle", doc.getFirstSection().getBody().getFirstParagraph().getParagraphFormat().getStyle().getName());
        msAssert.areEqual("OldStyle", doc.getFirstSection().getBody().getFirstParagraph().getParagraphFormat().getStyleName());

        doc.setRevisionsView(RevisionsView.FINAL);
        msAssert.areEqual("Normal", doc.getFirstSection().getBody().getFirstParagraph().getParagraphFormat().getStyle().getName());
        msAssert.areEqual("Normal", doc.getFirstSection().getBody().getFirstParagraph().getParagraphFormat().getStyleName());
    }





    /// <summary>
    /// WORDSNET-22956 Revision.Accept throws System.InvalidOperationException.
    /// We should accept/reject only current revision when it is called for a single format revision
    /// instead of doing it for all revisions of the parent node.
    /// </summary>
    @Test (dataProvider = "test22956DataProvider")
    public void test22956(boolean isAccept) throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\Test22956.docx");

        // There are formatting and deletion revisions.
        msAssert.areEqual(11, doc.getRevisions().getCount());

        // Customer's scenario is to process only formatting revisions.
        for (Revision revision : doc.getRevisions().toList())
        {
            if (revision.getRevisionType() == RevisionType.FORMAT_CHANGE)
            {
                if (isAccept)
                    revision.accept();
                else
                    revision.reject();
            }
        }

        // Check that only the deletion revisions are remained.
        msAssert.areEqual(5, doc.getRevisions().getCount());
        for (Revision revision : doc.getRevisions())
            msAssert.areEqual(RevisionType.DELETION, revision.getRevisionType());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test22956DataProvider")
	public static Object[][] test22956DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}











    /// <summary>
    /// WORDSNET-24550 Font size is incorrect after rendering text with revision.
    /// Improved positive difference calculation in <see cref="FormatRevisionUtil"/>
    /// when parent Paragraph of Run has style revision.
    /// </summary>
    @Test
    public void test24550() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\Test24550.docx");
        Run run = doc.getFirstSection().getBody().getFirstParagraph().getFirstRun();

        // Check before changes.
        msAssert.areEqual(6, run.getRunPr().getCount());
        msAssert.areEqual(DrColor.BLACK, run.getRunPr().get(FontAttr.COLOR));
        msAssert.areEqual(27, run.getRunPr().get(FontAttr.SIZE));
        msAssert.areEqual(27, run.getRunPr().get(FontAttr.SIZE_BI));

        // Check revision. Here should be a positive difference to "before changes".
        WordAttrCollection revPr = run.getRunPr().getFormatRevision().getRevPr();
        msAssert.areEqual(3, revPr.getCount());
        msAssert.areEqual(DrColor.EMPTY, revPr.get(FontAttr.COLOR));
        msAssert.areEqual(18, revPr.get(FontAttr.SIZE));
        msAssert.areEqual(20, revPr.get(FontAttr.SIZE_BI));
    }

    /// <summary>
    /// Relates to WORDSNET-24550
    /// Test differs from <see cref="Test24550"/> in one missed attribute in direct "before" run properties.
    /// In this case this attribute should be also missed in revision "after" properties of the run.
    /// </summary>
    @Test
    public void test24550A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\Test24550A.docx");
        Run run = doc.getFirstSection().getBody().getFirstParagraph().getFirstRun();

        // Check before changes.
        msAssert.areEqual(5, run.getRunPr().getCount());
        msAssert.areEqual(DrColor.BLACK, run.getRunPr().get(FontAttr.COLOR));
        msAssert.areEqual(27, run.getRunPr().get(FontAttr.SIZE_BI));

        // Check revision. Here should be a positive difference to "before changes".
        WordAttrCollection revPr = run.getRunPr().getFormatRevision().getRevPr();
        msAssert.areEqual(2, revPr.getCount());
        msAssert.areEqual(DrColor.EMPTY, revPr.get(FontAttr.COLOR));
        msAssert.areEqual(20, revPr.get(FontAttr.SIZE_BI));
    }



    /// <summary>
    /// Tests how the field numbering revisions are accepted and rejected.
    /// </summary>
    @Test (dataProvider = "testFieldNumberingRevisionDataProvider")
    public void testFieldNumberingRevision(boolean accept) throws Exception
    {
        Document document = TestUtil.open("Model\\Revision\\TestFieldNumberingRevision.docx");

        if (accept)
            document.getRevisions().acceptAll();
        else
            document.getRevisions().rejectAll();

        for (FieldEnd node : (Iterable<FieldEnd>) document.getChildNodes(NodeType.FIELD_END, true))
            msAssert.isNull(node.getRunPr().get(RevisionAttr.NUMBER_REVISION));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFieldNumberingRevisionDataProvider")
	public static Object[][] testFieldNumberingRevisionDataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}

    /// <summary>
    /// Checks accept SDT revision.
    /// </summary>
    private static void checkAcceptSdtRevision(Document originalDocument, int expectedCountRemainedSdt, String expectedTextFirstRemainedSdt) throws Exception
    {
        // Accept revisions.
        Document acceptedDoc = originalDocument.deepClone();
        acceptedDoc.acceptAllRevisions();

        // Accepted document should not have revisions.
        msAssert.isFalse(acceptedDoc.hasRevisions());

        // Deleted sdts had to be deleted and only inserted sdts are remained in document.
        NodeCollection sdts = acceptedDoc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        msAssert.areEqual(expectedCountRemainedSdt, sdts.getCount());
        StructuredDocumentTag sdt = (StructuredDocumentTag) sdts.get(0);
        checkSdtHasNoEditRevision(sdt);
        msAssert.areEqual(expectedTextFirstRemainedSdt, sdt.getText());
    }

    /// <summary>
    /// Checks reject SDT revision.
    /// </summary>
    private static void checkRejectSdtRevision(Document originalDocument, int expectedCountRemainedSdt, String expectedTextFirstRemainedSdt) throws Exception
    {
        // Check reject revisions.
        Document rejectedDoc = originalDocument.deepClone();
        rejectedDoc.getRevisions().rejectAll();

        // Rejected document should not have revisions.
        msAssert.isFalse(rejectedDoc.hasRevisions());

        // Inserted sdts had to be deleted and only deleted sdts are remained in document.
        NodeCollection sdts = rejectedDoc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        msAssert.areEqual(expectedCountRemainedSdt, sdts.getCount());
        StructuredDocumentTag sdt = (StructuredDocumentTag)sdts.get(0);
        checkSdtHasNoEditRevision(sdt);
        msAssert.areEqual(expectedTextFirstRemainedSdt, sdt.getText());
    }

    /// <summary>
    /// Checks SDT has no edit revisions.
    /// </summary>
    private static void checkSdtHasNoEditRevision(StructuredDocumentTag sdt)
    {
        WordAttrCollection attrs = sdt.getContentsRunPr();
        msAssert.isFalse(attrs.hasDeleteRevision());
        msAssert.isFalse(attrs.hasInsertRevision());
        msAssert.isFalse(attrs.hasMoveFromRevision());
        msAssert.isFalse(attrs.hasMoveToRevision());
    }

    /// <summary>
    /// Checks SDT has delete revision.
    /// </summary>
    private static void checkSdtHasDeleteRevision(StructuredDocumentTag sdt)
    {
        msAssert.isTrue(sdt.getContentsRunPr().hasDeleteRevision());
    }

    /// <summary>
    /// Checks SDT has insert revision.
    /// </summary>
    private static void checkSdtHasInsertRevision(StructuredDocumentTag sdt)
    {
        msAssert.isTrue(sdt.getContentsRunPr().hasInsertRevision());
    }

    /// <summary>
    /// Checks that the document contains no move range nodes.
    /// </summary>
    private static void checkMoveRangeNodesAreRemoved(Document doc, String testSubject)
    {
        msAssert.areEqual(0, doc.getChildNodes(NodeType.MOVE_FROM_RANGE_START, true).getCount(), testSubject);
        msAssert.areEqual(0, doc.getChildNodes(NodeType.MOVE_FROM_RANGE_END, true).getCount(), testSubject);
        msAssert.areEqual(0, doc.getChildNodes(NodeType.MOVE_TO_RANGE_START, true).getCount(), testSubject);
        msAssert.areEqual(0, doc.getChildNodes(NodeType.MOVE_TO_RANGE_END, true).getCount(), testSubject);
    }
}

