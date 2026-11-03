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


@Test
public class TestTableGrid extends UnifiedTestsBase
{
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCeller(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\Grid\\TestCeller", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCellerSourceDataProvider")
	public void unifiedTestCellerSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCeller(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCellerSourceDataProvider")
	Object[][] unifiedTestCellerSourceDataProvider() throws Exception
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
    void unifiedTestCellerNarrow(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\Grid\\TestCellerNarrow", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCellerNarrowSourceDataProvider")
	public void unifiedTestCellerNarrowSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCellerNarrow(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCellerNarrowSourceDataProvider")
	Object[][] unifiedTestCellerNarrowSourceDataProvider() throws Exception
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
    void unifiedTestCellerTop(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\Grid\\TestCellerTop", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCellerTopSourceDataProvider")
	public void unifiedTestCellerTopSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCellerTop(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCellerTopSourceDataProvider")
	Object[][] unifiedTestCellerTopSourceDataProvider() throws Exception
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
    void unifiedTestCellerTopBottom(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\Grid\\TestCellerTopBottom", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCellerTopBottomSourceDataProvider")
	public void unifiedTestCellerTopBottomSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCellerTopBottom(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCellerTopBottomSourceDataProvider")
	Object[][] unifiedTestCellerTopBottomSourceDataProvider() throws Exception
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



    private static void verifyDefect1130(Document doc)
    {
        // The table that consists of three tables on page 1.
        msAssert.isTrue(getAllowBreak(doc, 0));
        msAssert.isTrue(getAllowBreak(doc, 1));
        msAssert.isTrue(getAllowBreak(doc, 2));

        // The outer Optionscheine table on page 3.
        msAssert.isTrue(getAllowBreak(doc, 3));
        // The inner table on page 3.
        msAssert.isFalse(getAllowBreak(doc, 4));

        // The outer Optionscheine table on page 4.
        msAssert.isTrue(getAllowBreak(doc, 5));
        // The inner table on page 4.
        msAssert.isFalse(getAllowBreak(doc, 6));

        // The outer WKN table on page 7.
        msAssert.isTrue(getAllowBreak(doc, 7));
        // The inner WKN table on page 7.
        msAssert.isFalse(getAllowBreak(doc, 8));

        // The outer Basiswert table on page 8.
        msAssert.isFalse(getAllowBreak(doc, 9));
        // The inner Basiswert table on page 8.
        msAssert.isFalse(getAllowBreak(doc, 10));
    }

    private static boolean getAllowBreak(Document doc, int tableIdx)
    {
        Table table = (Table)doc.getChild(NodeType.TABLE, tableIdx, true);
        return table.getRows().get(0).getRowFormat().getAllowBreakAcrossPages();
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestLeftIndent(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\Grid\\TestLeftIndent", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLeftIndentSourceDataProvider")
	public void unifiedTestLeftIndentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLeftIndent(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLeftIndentSourceDataProvider")
	Object[][] unifiedTestLeftIndentSourceDataProvider() throws Exception
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
    void unifiedTestLeftIndentDifferent(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\Grid\\TestLeftIndentDifferent", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLeftIndentDifferentSourceDataProvider")
	public void unifiedTestLeftIndentDifferentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLeftIndentDifferent(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLeftIndentDifferentSourceDataProvider")
	Object[][] unifiedTestLeftIndentDifferentSourceDataProvider() throws Exception
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
    void unifiedTestLeftIndentDifferent1(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\Grid\\TestLeftIndentDifferent1", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLeftIndentDifferent1SourceDataProvider")
	public void unifiedTestLeftIndentDifferent1Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLeftIndentDifferent1(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLeftIndentDifferent1SourceDataProvider")
	Object[][] unifiedTestLeftIndentDifferent1SourceDataProvider() throws Exception
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
    void unifiedTestMergedCells(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\Grid\\TestMergedCells", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestMergedCellsSourceDataProvider")
	public void unifiedTestMergedCellsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestMergedCells(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestMergedCellsSourceDataProvider")
	Object[][] unifiedTestMergedCellsSourceDataProvider() throws Exception
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
    void unifiedTestMergedCellsH(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\Grid\\TestMergedCellsH", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestMergedCellsHSourceDataProvider")
	public void unifiedTestMergedCellsHSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestMergedCellsH(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestMergedCellsHSourceDataProvider")
	Object[][] unifiedTestMergedCellsHSourceDataProvider() throws Exception
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
    void unifiedTestMergedCellsV(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\Grid\\TestMergedCellsV", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestMergedCellsVSourceDataProvider")
	public void unifiedTestMergedCellsVSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestMergedCellsV(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestMergedCellsVSourceDataProvider")
	Object[][] unifiedTestMergedCellsVSourceDataProvider() throws Exception
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



    private static void checkCellMerged(Table table, int rowIdx, int cellIdx, /*CellMerge*/int hMerge, /*CellMerge*/int vMerge)
    {
        CellPr cellPr = table.getRows().get(rowIdx).getCells().get(cellIdx).getCellPr();
        msAssert.areEqual(hMerge, cellPr.getHorizontalMerge());
        msAssert.areEqual(vMerge, cellPr.getVerticalMerge());
    }
}

