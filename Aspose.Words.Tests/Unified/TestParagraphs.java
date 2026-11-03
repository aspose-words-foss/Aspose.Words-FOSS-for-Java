// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.DrColor;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;

//TODO 3 Cannot properly read fast saved tab positions. See TestTabPositionsFastSaved.doc.


/// <summary>
/// Unified tests for paragraphs.
/// </summary>
@Test
public class TestParagraphs extends UnifiedTestsBase
{
    /// <summary>
    /// Test borders on ParagraphFormat are working and being inherited properly.
    /// </summary>
    @Test
    public void testParagraphBordersInheritance() throws Exception
    {
        Document doc = new Document();
        Style normal = doc.getStyles().get("Normal");
        Style h1 = doc.getStyles().get("Heading 1");

        Paragraph para = (Paragraph)doc.selectSingleNode("//Paragraph[1]");
        para.getParagraphFormat().setStyleName("Heading 1");

        //The borders are not visible although the count is there.
        msAssert.areEqual(6, para.getParagraphFormat().getBorders().getCount());
        msAssert.areEqual(0d, para.getParagraphFormat().getBorders().getByBorderType(BorderType.TOP).getLineWidth());

        //Set some borders in the normal style.
        normal.getParagraphFormat().getBorders().getByBorderType(BorderType.TOP).setLineWidth(1.0);
        normal.getParagraphFormat().getBorders().getByBorderType(BorderType.LEFT).setLineWidth(2.0);
        //Set some borders in Heading 1
        h1.getParagraphFormat().getBorders().getByBorderType(BorderType.RIGHT).setLineWidth(4.0);

        //Check they are inherited from the styles.
        msAssert.areEqual(1d, para.getParagraphFormat().getBorders().getByBorderType(BorderType.TOP).getLineWidth());
        msAssert.areEqual(2d, para.getParagraphFormat().getBorders().getByBorderType(BorderType.LEFT).getLineWidth());
        msAssert.areEqual(4d, para.getParagraphFormat().getBorders().getByBorderType(BorderType.RIGHT).getLineWidth());

        //Make Heading 1 override a border specified in Normal and check it takes precedence in inheritance.
        h1.getParagraphFormat().getBorders().getByBorderType(BorderType.LEFT).setLineWidth(3.0);
        msAssert.areEqual(3d, para.getParagraphFormat().getBorders().getByBorderType(BorderType.LEFT).getLineWidth());

        //Set some borders on the paragraph directly and make sure they take precedence.
        para.getParagraphFormat().getBorders().getByBorderType(BorderType.LEFT).setLineWidth(5.0);
        msAssert.areEqual(5d, para.getParagraphFormat().getBorders().getByBorderType(BorderType.LEFT).getLineWidth());

        //A simple check to make sure all complex attributes were copied
        msAssert.areNotSame(h1.getParagraphFormat().getBorders().getByBorderType(BorderType.LEFT), normal.getParagraphFormat().getBorders().getByBorderType(BorderType.LEFT));
        msAssert.areNotSame(para.getParagraphFormat().getBorders().getByBorderType(BorderType.LEFT), h1.getParagraphFormat().getBorders().getByBorderType(BorderType.LEFT));
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestParagraphInOneRun(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Para\\TestParagraphInOneRun", lf, sf);
        msAssert.areEqual(1, doc.selectNodes("//Run").getCount());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestParagraphInOneRunSourceDataProvider")
	public void unifiedTestParagraphInOneRunSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestParagraphInOneRun(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestParagraphInOneRunSourceDataProvider")
	Object[][] unifiedTestParagraphInOneRunSourceDataProvider() throws Exception
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
    void unifiedTestCustomColor(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Para\\TestCustomColor", lf, sf);

        Paragraph para = (Paragraph)doc.selectSingleNode("//Paragraph[2]");
        ParagraphFormat pf = para.getParagraphFormat();

        //Test shading with custom colors is set
        msAssert.areEqual(DrColor.fromArgb(255, 0, 0), pf.getShading().getForegroundPatternColorInternal());
        msAssert.areEqual(DrColor.fromArgb(250, 150, 50), pf.getShading().getBackgroundPatternColorInternal());

        msAssert.areEqual(TextureIndex.TEXTURE_10_PERCENT, pf.getShading().getTexture());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCustomColorSourceDataProvider")
	public void unifiedTestCustomColorSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCustomColor(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCustomColorSourceDataProvider")
	Object[][] unifiedTestCustomColorSourceDataProvider() throws Exception
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
    /// Test on a paragraph style that has no tab positions a few tab positions are
    /// added using direct formatting.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestAddTabPositions(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Para\\TestTabPositions", lf, sf);

        Paragraph para = (Paragraph)doc.selectSingleNode("//Paragraph[1]");
        TabStopCollection tabStops = para.getParagraphFormat().getTabStops();

        msAssert.areEqual(3, tabStops.getCount());
        msAssert.areEqual(1440, tabStops.getPositionTwipsByIndex(0));    //1"
        msAssert.areEqual(2880, tabStops.getPositionTwipsByIndex(1));    //2"
        msAssert.areEqual(5760, tabStops.getPositionTwipsByIndex(2));    //4"
        msAssert.areEqual(TabAlignment.RIGHT, tabStops.get(2).getAlignment());
        msAssert.areEqual(TabLeader.DOTS, tabStops.get(2).getLeader());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAddTabPositionsSourceDataProvider")
	public void unifiedTestAddTabPositionsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAddTabPositions(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAddTabPositionsSourceDataProvider")
	Object[][] unifiedTestAddTabPositionsSourceDataProvider() throws Exception
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
    /// Some positions were on the style and some added and moved using direct formatting.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestStyleAndDirectTabPositions(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Para\\TestTabPositions", lf, sf);

        //These are the tab stops on the style
        Style style = doc.getStyles().get("RKStyle1");
        TabStopCollection tabStops = style.getParagraphFormat().getTabStops();
        msAssert.areEqual(2, tabStops.getCount());

        msAssert.areEqual(360, tabStops.getPositionTwipsByIndex(0));        //add 0.25"
        msAssert.areEqual(TabAlignment.LEFT, tabStops.get(0).getAlignment());

        msAssert.areEqual(2880, tabStops.getPositionTwipsByIndex(1));    //add 2"
        msAssert.areEqual(TabAlignment.LEFT, tabStops.get(1).getAlignment());


        //These are the tab stops on the paragraph
        Paragraph para = (Paragraph)doc.selectSingleNode("//Paragraph[2]");
        tabStops = para.getParagraphFormat().getTabStops();
        msAssert.areEqual(3, tabStops.getCount());

        msAssert.areEqual(2160, tabStops.getPositionTwipsByIndex(0));    //add 1.5"
        msAssert.areEqual(TabAlignment.LEFT, tabStops.get(0).getAlignment());

        msAssert.areEqual(2880, tabStops.getPositionTwipsByIndex(1));    //delete 2"
        msAssert.areEqual(TabAlignment.CLEAR, tabStops.get(1).getAlignment());

        msAssert.areEqual(7200, tabStops.getPositionTwipsByIndex(2));    //add 5"
        msAssert.areEqual(TabAlignment.LEFT, tabStops.get(2).getAlignment());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestStyleAndDirectTabPositionsSourceDataProvider")
	public void unifiedTestStyleAndDirectTabPositionsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestStyleAndDirectTabPositions(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestStyleAndDirectTabPositionsSourceDataProvider")
	Object[][] unifiedTestStyleAndDirectTabPositionsSourceDataProvider() throws Exception
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
    public void testSetParagraphStyle() throws Exception
    {
        Document doc = new Document();
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(para.getParagraphFormat().getStyle(), doc.getStyles().get("Normal"));
        para.getParagraphFormat().setStyle(doc.getStyles().get("Heading 1"));
        msAssert.areEqual(para.getParagraphFormat().getStyle(), doc.getStyles().get("Heading 1"));
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testSetParagraphStyleNull() throws Exception
    {
        Document doc = new Document();
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        para.getParagraphFormat().setStyle(null);
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThis style belongs to a different document.\\E")
    public void testSetParagraphStyleWrongDocument() throws Exception
    {
        Document doc1 = new Document();
        Document doc2 = new Document();
        Paragraph para = doc1.getFirstSection().getBody().getFirstParagraph();
        para.getParagraphFormat().setStyle(doc2.getStyles().get("Normal"));
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThis style is not a paragraph style.\\E")
    public void testSetParagraphStyleWrongStyleType() throws Exception
    {
        Document doc = new Document();
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        para.getParagraphFormat().setStyle(doc.getStyles().getByStyleIdentifier(StyleIdentifier.DEFAULT_PARAGRAPH_FONT));
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDirection(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Para\\TestDirection", lf, sf);
        checkDirection(doc, 0, TextOrientation.HORIZONTAL);
        checkDirection(doc, 2, TextOrientation.UPWARD);
        checkDirection(doc, 4, TextOrientation.VERTICAL_FAR_EAST);
        checkDirection(doc, 6, TextOrientation.DOWNWARD);
        checkDirection(doc, 8, TextOrientation.HORIZONTAL_ROTATED_FAR_EAST);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDirectionSourceDataProvider")
	public void unifiedTestDirectionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDirection(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDirectionSourceDataProvider")
	Object[][] unifiedTestDirectionSourceDataProvider() throws Exception
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

    private static void checkDirection(Document doc, int paraIndex, /*TextOrientation*/int orientation)
    {
        Paragraph para = (Paragraph)doc.getFirstSection().getBody().getChild(NodeType.PARAGRAPH, paraIndex, true);
        msAssert.areEqual(para.getParagraphFormat().getFrameTextOrientation(), orientation);
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestAlignment(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestAlignment", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAlignmentSourceDataProvider")
	public void unifiedTestAlignmentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAlignment(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAlignmentSourceDataProvider")
	Object[][] unifiedTestAlignmentSourceDataProvider() throws Exception
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
    void unifiedTestAlignmentAndFirstIndent(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestAlignmentAndFirstIndent", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAlignmentAndFirstIndentSourceDataProvider")
	public void unifiedTestAlignmentAndFirstIndentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAlignmentAndFirstIndent(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAlignmentAndFirstIndentSourceDataProvider")
	Object[][] unifiedTestAlignmentAndFirstIndentSourceDataProvider() throws Exception
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
    void unifiedTestAlignmentAndFirstIndentBig(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestAlignmentAndFirstIndentBig", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAlignmentAndFirstIndentBigSourceDataProvider")
	public void unifiedTestAlignmentAndFirstIndentBigSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAlignmentAndFirstIndentBig(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAlignmentAndFirstIndentBigSourceDataProvider")
	Object[][] unifiedTestAlignmentAndFirstIndentBigSourceDataProvider() throws Exception
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
    void unifiedTestAlignmentAndFirstIndentNegative(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestAlignmentAndFirstIndentNegative", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAlignmentAndFirstIndentNegativeSourceDataProvider")
	public void unifiedTestAlignmentAndFirstIndentNegativeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAlignmentAndFirstIndentNegative(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAlignmentAndFirstIndentNegativeSourceDataProvider")
	Object[][] unifiedTestAlignmentAndFirstIndentNegativeSourceDataProvider() throws Exception
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
    void unifiedTestAlignmentAndLeftIndent(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestAlignmentAndLeftIndent", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAlignmentAndLeftIndentSourceDataProvider")
	public void unifiedTestAlignmentAndLeftIndentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAlignmentAndLeftIndent(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAlignmentAndLeftIndentSourceDataProvider")
	Object[][] unifiedTestAlignmentAndLeftIndentSourceDataProvider() throws Exception
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
    void unifiedTestAlignmentWithTabs(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestAlignmentWithTabs", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAlignmentWithTabsSourceDataProvider")
	public void unifiedTestAlignmentWithTabsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAlignmentWithTabs(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAlignmentWithTabsSourceDataProvider")
	Object[][] unifiedTestAlignmentWithTabsSourceDataProvider() throws Exception
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
    void unifiedTestAll(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestAll", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAllSourceDataProvider")
	public void unifiedTestAllSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAll(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAllSourceDataProvider")
	Object[][] unifiedTestAllSourceDataProvider() throws Exception
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
    void unifiedTestBorders(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestBorders", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBordersSourceDataProvider")
	public void unifiedTestBordersSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBorders(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBordersSourceDataProvider")
	Object[][] unifiedTestBordersSourceDataProvider() throws Exception
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
    void unifiedTestConforming(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestConforming", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestConformingSourceDataProvider")
	public void unifiedTestConformingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestConforming(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestConformingSourceDataProvider")
	Object[][] unifiedTestConformingSourceDataProvider() throws Exception
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
    void unifiedTestConformingBetween(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestConformingBetween", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestConformingBetweenSourceDataProvider")
	public void unifiedTestConformingBetweenSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestConformingBetween(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestConformingBetweenSourceDataProvider")
	Object[][] unifiedTestConformingBetweenSourceDataProvider() throws Exception
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
    void unifiedTestDefaultPara(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestDefaultPara", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefaultParaSourceDataProvider")
	public void unifiedTestDefaultParaSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefaultPara(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefaultParaSourceDataProvider")
	Object[][] unifiedTestDefaultParaSourceDataProvider() throws Exception
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
    void unifiedTestDropCap(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestDropCap", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDropCapSourceDataProvider")
	public void unifiedTestDropCapSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDropCap(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDropCapSourceDataProvider")
	Object[][] unifiedTestDropCapSourceDataProvider() throws Exception
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
    void unifiedTestFrame(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestFrame", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFrameSourceDataProvider")
	public void unifiedTestFrameSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFrame(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFrameSourceDataProvider")
	Object[][] unifiedTestFrameSourceDataProvider() throws Exception
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
    void unifiedTestHeadings(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestHeadings", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestHeadingsSourceDataProvider")
	public void unifiedTestHeadingsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestHeadings(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestHeadingsSourceDataProvider")
	Object[][] unifiedTestHeadingsSourceDataProvider() throws Exception
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
    void unifiedTestIndentAndBarTab(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestIndentAndBarTab", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestIndentAndBarTabSourceDataProvider")
	public void unifiedTestIndentAndBarTabSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestIndentAndBarTab(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestIndentAndBarTabSourceDataProvider")
	Object[][] unifiedTestIndentAndBarTabSourceDataProvider() throws Exception
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
    void unifiedTestIndentAndTab(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestIndentAndTab", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestIndentAndTabSourceDataProvider")
	public void unifiedTestIndentAndTabSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestIndentAndTab(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestIndentAndTabSourceDataProvider")
	Object[][] unifiedTestIndentAndTabSourceDataProvider() throws Exception
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
    void unifiedTestIndents(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestIndents", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestIndentsSourceDataProvider")
	public void unifiedTestIndentsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestIndents(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestIndentsSourceDataProvider")
	Object[][] unifiedTestIndentsSourceDataProvider() throws Exception
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
    void unifiedTestJustify(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestJustify", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJustifySourceDataProvider")
	public void unifiedTestJustifySource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJustify(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJustifySourceDataProvider")
	Object[][] unifiedTestJustifySourceDataProvider() throws Exception
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
    void unifiedTestJustifyWithBreaks(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestJustifyWithBreaks", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJustifyWithBreaksSourceDataProvider")
	public void unifiedTestJustifyWithBreaksSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJustifyWithBreaks(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJustifyWithBreaksSourceDataProvider")
	Object[][] unifiedTestJustifyWithBreaksSourceDataProvider() throws Exception
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
    void unifiedTestJustifyWithTabs(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestJustifyWithTabs", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJustifyWithTabsSourceDataProvider")
	public void unifiedTestJustifyWithTabsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJustifyWithTabs(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJustifyWithTabsSourceDataProvider")
	Object[][] unifiedTestJustifyWithTabsSourceDataProvider() throws Exception
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
    void unifiedTestKeeps(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestKeeps", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestKeepsSourceDataProvider")
	public void unifiedTestKeepsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestKeeps(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestKeepsSourceDataProvider")
	Object[][] unifiedTestKeepsSourceDataProvider() throws Exception
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
    void unifiedTestLineSpacing(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestLineSpacing", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLineSpacingSourceDataProvider")
	public void unifiedTestLineSpacingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLineSpacing(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLineSpacingSourceDataProvider")
	Object[][] unifiedTestLineSpacingSourceDataProvider() throws Exception
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
    void unifiedTestLineSpacingRule(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestLineSpacingRule", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLineSpacingRuleSourceDataProvider")
	public void unifiedTestLineSpacingRuleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLineSpacingRule(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLineSpacingRuleSourceDataProvider")
	Object[][] unifiedTestLineSpacingRuleSourceDataProvider() throws Exception
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
    void unifiedTestLineSpacings(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestLineSpacings", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLineSpacingsSourceDataProvider")
	public void unifiedTestLineSpacingsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLineSpacings(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLineSpacingsSourceDataProvider")
	Object[][] unifiedTestLineSpacingsSourceDataProvider() throws Exception
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
    void unifiedTestLineStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestLineStyle", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLineStyleSourceDataProvider")
	public void unifiedTestLineStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLineStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLineStyleSourceDataProvider")
	Object[][] unifiedTestLineStyleSourceDataProvider() throws Exception
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
    void unifiedTestList(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestList", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListSourceDataProvider")
	public void unifiedTestListSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestList(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListSourceDataProvider")
	Object[][] unifiedTestListSourceDataProvider() throws Exception
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
    void unifiedTestPageNumberInFrame(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestPageNumberInFrame", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestPageNumberInFrameSourceDataProvider")
	public void unifiedTestPageNumberInFrameSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestPageNumberInFrame(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestPageNumberInFrameSourceDataProvider")
	Object[][] unifiedTestPageNumberInFrameSourceDataProvider() throws Exception
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
    void unifiedTestParaAlignment(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestParaAlignment", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestParaAlignmentSourceDataProvider")
	public void unifiedTestParaAlignmentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestParaAlignment(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestParaAlignmentSourceDataProvider")
	Object[][] unifiedTestParaAlignmentSourceDataProvider() throws Exception
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
    void unifiedTestParaBlanks(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestParaBlanks", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestParaBlanksSourceDataProvider")
	public void unifiedTestParaBlanksSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestParaBlanks(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestParaBlanksSourceDataProvider")
	Object[][] unifiedTestParaBlanksSourceDataProvider() throws Exception
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
    void unifiedTestParaCustomTabs(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestParaCustomTabs", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestParaCustomTabsSourceDataProvider")
	public void unifiedTestParaCustomTabsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestParaCustomTabs(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestParaCustomTabsSourceDataProvider")
	Object[][] unifiedTestParaCustomTabsSourceDataProvider() throws Exception
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
    void unifiedTestRevision(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestRevision", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionSourceDataProvider")
	public void unifiedTestRevisionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevision(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionSourceDataProvider")
	Object[][] unifiedTestRevisionSourceDataProvider() throws Exception
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
    void unifiedTestSpacing(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestSpacing", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestSpacingSourceDataProvider")
	public void unifiedTestSpacingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestSpacing(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestSpacingSourceDataProvider")
	Object[][] unifiedTestSpacingSourceDataProvider() throws Exception
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
    void unifiedTestStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestStyle", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestStyleSourceDataProvider")
	public void unifiedTestStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestStyleSourceDataProvider")
	Object[][] unifiedTestStyleSourceDataProvider() throws Exception
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
    void unifiedTestTable(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestTable", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTableSourceDataProvider")
	public void unifiedTestTableSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTable(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTableSourceDataProvider")
	Object[][] unifiedTestTableSourceDataProvider() throws Exception
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
    void unifiedTestTabs(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestTabs", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTabsSourceDataProvider")
	public void unifiedTestTabsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTabs(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTabsSourceDataProvider")
	Object[][] unifiedTestTabsSourceDataProvider() throws Exception
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
    void unifiedTestTabStopWrap(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Para\\TestTabStopWrap", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTabStopWrapSourceDataProvider")
	public void unifiedTestTabStopWrapSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTabStopWrap(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTabStopWrapSourceDataProvider")
	Object[][] unifiedTestTabStopWrapSourceDataProvider() throws Exception
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
    void unifiedTestAutoColorOnShading(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaBorder\\TestAutoColorOnShading", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAutoColorOnShadingSourceDataProvider")
	public void unifiedTestAutoColorOnShadingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAutoColorOnShading(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAutoColorOnShadingSourceDataProvider")
	Object[][] unifiedTestAutoColorOnShadingSourceDataProvider() throws Exception
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
    void unifiedTestBorder2(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaBorder\\TestBorder2", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBorder2SourceDataProvider")
	public void unifiedTestBorder2Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBorder2(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBorder2SourceDataProvider")
	Object[][] unifiedTestBorder2SourceDataProvider() throws Exception
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
    void unifiedTestBorderWithShadow(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaBorder\\TestBorderWithShadow", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBorderWithShadowSourceDataProvider")
	public void unifiedTestBorderWithShadowSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBorderWithShadow(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBorderWithShadowSourceDataProvider")
	Object[][] unifiedTestBorderWithShadowSourceDataProvider() throws Exception
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
    void unifiedTestBorderAll(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaBorder\\TestBorderAll", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBorderAllSourceDataProvider")
	public void unifiedTestBorderAllSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBorderAll(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBorderAllSourceDataProvider")
	Object[][] unifiedTestBorderAllSourceDataProvider() throws Exception
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
    void unifiedTestColumnBreaks(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaBorder\\TestColumnBreaks", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestColumnBreaksSourceDataProvider")
	public void unifiedTestColumnBreaksSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestColumnBreaks(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestColumnBreaksSourceDataProvider")
	Object[][] unifiedTestColumnBreaksSourceDataProvider() throws Exception
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
    void unifiedTestComplex(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaBorder\\TestComplex", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestComplexSourceDataProvider")
	public void unifiedTestComplexSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestComplex(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestComplexSourceDataProvider")
	Object[][] unifiedTestComplexSourceDataProvider() throws Exception
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
    void unifiedTestConformingAtColumnEnd(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaBorder\\TestConformingAtColumnEnd", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestConformingAtColumnEndSourceDataProvider")
	public void unifiedTestConformingAtColumnEndSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestConformingAtColumnEnd(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestConformingAtColumnEndSourceDataProvider")
	Object[][] unifiedTestConformingAtColumnEndSourceDataProvider() throws Exception
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
    void unifiedTestConformingBorder(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaBorder\\TestConformingBorder", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestConformingBorderSourceDataProvider")
	public void unifiedTestConformingBorderSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestConformingBorder(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestConformingBorderSourceDataProvider")
	Object[][] unifiedTestConformingBorderSourceDataProvider() throws Exception
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
    void unifiedTestConformingDistance(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaBorder\\TestConformingDistance", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestConformingDistanceSourceDataProvider")
	public void unifiedTestConformingDistanceSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestConformingDistance(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestConformingDistanceSourceDataProvider")
	Object[][] unifiedTestConformingDistanceSourceDataProvider() throws Exception
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
    void unifiedTestConformingDouble(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaBorder\\TestConformingDouble", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestConformingDoubleSourceDataProvider")
	public void unifiedTestConformingDoubleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestConformingDouble(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestConformingDoubleSourceDataProvider")
	Object[][] unifiedTestConformingDoubleSourceDataProvider() throws Exception
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
    void unifiedTestConformingTriple(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaBorder\\TestConformingTriple", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestConformingTripleSourceDataProvider")
	public void unifiedTestConformingTripleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestConformingTriple(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestConformingTripleSourceDataProvider")
	Object[][] unifiedTestConformingTripleSourceDataProvider() throws Exception
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
    void unifiedTestConformingTripleCombo(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaBorder\\TestConformingTripleCombo", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestConformingTripleComboSourceDataProvider")
	public void unifiedTestConformingTripleComboSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestConformingTripleCombo(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestConformingTripleComboSourceDataProvider")
	Object[][] unifiedTestConformingTripleComboSourceDataProvider() throws Exception
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
    void unifiedTestFitsColumn(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaBorder\\TestFitsColumn", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFitsColumnSourceDataProvider")
	public void unifiedTestFitsColumnSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFitsColumn(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFitsColumnSourceDataProvider")
	Object[][] unifiedTestFitsColumnSourceDataProvider() throws Exception
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
    void unifiedTestSectionBreaks(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaBorder\\TestSectionBreaks", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestSectionBreaksSourceDataProvider")
	public void unifiedTestSectionBreaksSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestSectionBreaks(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestSectionBreaksSourceDataProvider")
	Object[][] unifiedTestSectionBreaksSourceDataProvider() throws Exception
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
    void unifiedTestSpansColumn(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaBorder\\TestSpansColumn", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestSpansColumnSourceDataProvider")
	public void unifiedTestSpansColumnSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestSpansColumn(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestSpansColumnSourceDataProvider")
	Object[][] unifiedTestSpansColumnSourceDataProvider() throws Exception
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
    void unifiedTestVariousProperties(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaBorder\\TestVariousProperties", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestVariousPropertiesSourceDataProvider")
	public void unifiedTestVariousPropertiesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestVariousProperties(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestVariousPropertiesSourceDataProvider")
	Object[][] unifiedTestVariousPropertiesSourceDataProvider() throws Exception
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
    void unifiedTestVerticalBorderMerged(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaBorder\\TestVerticalBorderMerged", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestVerticalBorderMergedSourceDataProvider")
	public void unifiedTestVerticalBorderMergedSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestVerticalBorderMerged(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestVerticalBorderMergedSourceDataProvider")
	Object[][] unifiedTestVerticalBorderMergedSourceDataProvider() throws Exception
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
    void unifiedTestKeepTogether(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaKeeps\\TestKeepTogether", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestKeepTogetherSourceDataProvider")
	public void unifiedTestKeepTogetherSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestKeepTogether(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestKeepTogetherSourceDataProvider")
	Object[][] unifiedTestKeepTogetherSourceDataProvider() throws Exception
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
    void unifiedTestKeepWithNext(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaKeeps\\TestKeepWithNext", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestKeepWithNextSourceDataProvider")
	public void unifiedTestKeepWithNextSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestKeepWithNext(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestKeepWithNextSourceDataProvider")
	Object[][] unifiedTestKeepWithNextSourceDataProvider() throws Exception
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
    void unifiedTestKeepWithNext2(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaKeeps\\TestKeepWithNext2", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestKeepWithNext2SourceDataProvider")
	public void unifiedTestKeepWithNext2Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestKeepWithNext2(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestKeepWithNext2SourceDataProvider")
	Object[][] unifiedTestKeepWithNext2SourceDataProvider() throws Exception
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
    void unifiedTestTough2(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaKeeps\\TestTough2", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTough2SourceDataProvider")
	public void unifiedTestTough2Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTough2(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTough2SourceDataProvider")
	Object[][] unifiedTestTough2SourceDataProvider() throws Exception
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
    void unifiedTestWidowOrphan(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaKeeps\\TestWidowOrphan", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestWidowOrphanSourceDataProvider")
	public void unifiedTestWidowOrphanSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestWidowOrphan(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestWidowOrphanSourceDataProvider")
	Object[][] unifiedTestWidowOrphanSourceDataProvider() throws Exception
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
    void unifiedTestWidowOrphan2(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaKeeps\\TestWidowOrphan2", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestWidowOrphan2SourceDataProvider")
	public void unifiedTestWidowOrphan2Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestWidowOrphan2(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestWidowOrphan2SourceDataProvider")
	Object[][] unifiedTestWidowOrphan2SourceDataProvider() throws Exception
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
    void unifiedTestBeforeAfter(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaSpacing\\TestBeforeAfter", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBeforeAfterSourceDataProvider")
	public void unifiedTestBeforeAfterSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBeforeAfter(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBeforeAfterSourceDataProvider")
	Object[][] unifiedTestBeforeAfterSourceDataProvider() throws Exception
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
    void unifiedTestBeforeAfterAuto(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaSpacing\\TestBeforeAfterAuto", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBeforeAfterAutoSourceDataProvider")
	public void unifiedTestBeforeAfterAutoSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBeforeAfterAuto(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBeforeAfterAutoSourceDataProvider")
	Object[][] unifiedTestBeforeAfterAutoSourceDataProvider() throws Exception
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
    void unifiedTestBeforeAfterZeroes(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaSpacing\\TestBeforeAfterZeroes", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBeforeAfterZeroesSourceDataProvider")
	public void unifiedTestBeforeAfterZeroesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBeforeAfterZeroes(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBeforeAfterZeroesSourceDataProvider")
	Object[][] unifiedTestBeforeAfterZeroesSourceDataProvider() throws Exception
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
    void unifiedTestLineSpacingAtLeast(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaSpacing\\TestLineSpacingAtLeast", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLineSpacingAtLeastSourceDataProvider")
	public void unifiedTestLineSpacingAtLeastSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLineSpacingAtLeast(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLineSpacingAtLeastSourceDataProvider")
	Object[][] unifiedTestLineSpacingAtLeastSourceDataProvider() throws Exception
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
    void unifiedTestLineSpacingExactly(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaSpacing\\TestLineSpacingExactly", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLineSpacingExactlySourceDataProvider")
	public void unifiedTestLineSpacingExactlySource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLineSpacingExactly(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLineSpacingExactlySourceDataProvider")
	Object[][] unifiedTestLineSpacingExactlySourceDataProvider() throws Exception
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
    void unifiedTestLineSpacingExactlyWithBorders(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaSpacing\\TestLineSpacingExactlyWithBorders", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLineSpacingExactlyWithBordersSourceDataProvider")
	public void unifiedTestLineSpacingExactlyWithBordersSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLineSpacingExactlyWithBorders(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLineSpacingExactlyWithBordersSourceDataProvider")
	Object[][] unifiedTestLineSpacingExactlyWithBordersSourceDataProvider() throws Exception
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
    void unifiedTestLineSpacingMultiple(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaSpacing\\TestLineSpacingMultiple", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLineSpacingMultipleSourceDataProvider")
	public void unifiedTestLineSpacingMultipleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLineSpacingMultiple(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLineSpacingMultipleSourceDataProvider")
	Object[][] unifiedTestLineSpacingMultipleSourceDataProvider() throws Exception
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
    void unifiedTestNoSpacingBetweenSameStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\ParaSpacing\\TestNoSpacingBetweenSameStyle", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestNoSpacingBetweenSameStyleSourceDataProvider")
	public void unifiedTestNoSpacingBetweenSameStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestNoSpacingBetweenSameStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestNoSpacingBetweenSameStyleSourceDataProvider")
	Object[][] unifiedTestNoSpacingBetweenSameStyleSourceDataProvider() throws Exception
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
    void unifiedTestAsiaProps(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\Para\\TestAsiaProps", lf);
        // Verify attributes read properly.
        verifyFeValues(doc.getNodeById("0.0.0"), true, true);
        verifyFeValues(doc.getNodeById("1.0.0"), true, false);
        verifyFeValues(doc.getNodeById("2.0.0"), false, true);
        verifyFeValues(doc.getNodeById("3.0.0"), false, false);

        // So TRUE value comes from defaults i.e collection doesn't contain corresponding direct attribute.
        verifyFePresence(doc.getNodeById("0.0.0"), false, false);
        verifyFePresence(doc.getNodeById("1.0.0"), false, true);
        verifyFePresence(doc.getNodeById("2.0.0"), true, false);
        verifyFePresence(doc.getNodeById("3.0.0"), true, true);

        doc = TestUtil.saveOpen(doc, "Model\\Para\\TestAsiaProps", lf, sf);
        // Verify attributes saved properly.
        verifyFeValues(doc.getNodeById("0.0.0"), true, true);
        verifyFeValues(doc.getNodeById("1.0.0"), true, false);
        verifyFeValues(doc.getNodeById("2.0.0"), false, true);
        verifyFeValues(doc.getNodeById("3.0.0"), false, false);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAsiaPropsSourceDataProvider")
	public void unifiedTestAsiaPropsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAsiaProps(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAsiaPropsSourceDataProvider")
	Object[][] unifiedTestAsiaPropsSourceDataProvider() throws Exception
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
    /// WORDSNET-6540 Paragraph Mirror Indenting Issue.
    /// andrnosk: Added mechanism to read/write MirrorIndents in Doc/Docx.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestMirrorIndents(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Para\\TestMirrorIndents", lf, sf);
        Paragraph paragraph = (Paragraph)doc.getNodeById("0.0.0");

        switch (TestUtil.getUnifiedScenario(lf, sf))
        {
            case UnifiedScenario.DOC_2_DOC:
            case UnifiedScenario.DOC_2_DOCX:
            case UnifiedScenario.DOCX_2_DOCX_NO_GOLD:
            case UnifiedScenario.DOC_2_RTF:
            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
                msAssert.isTrue((/*boolean*/Boolean)paragraph.getParaPr().get(ParaAttr.MIRROR_INDENTS));
                break;
            case UnifiedScenario.DOC_2_WML:
            case UnifiedScenario.WML_2_WML_NO_GOLD:
                msAssert.isNull(paragraph.getParaPr().get(ParaAttr.MIRROR_INDENTS));
                break;
            default:
                throw new IllegalStateException("Unknown file format.");
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestMirrorIndentsSourceDataProvider")
	public void unifiedTestMirrorIndentsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestMirrorIndents(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestMirrorIndentsSourceDataProvider")
	Object[][] unifiedTestMirrorIndentsSourceDataProvider() throws Exception
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
    /// WORDSNET-7533 Two Paragraph should display on single line during Doc to Rtf conversion.
    /// Added SpecialHidden (Style separator) attribute reading/writing to RTF.
    /// </summary>
    /// <remarks>
    /// AM. Additionally I found that Word doesn't like NonBreakingSpace written as \~ keyword and displays them incorrectly.
    /// Fixed here and verified by Gold.
    /// </remarks>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedJira7533(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Para\\TestJira7533", lf, sf);

        RunPr paraBreakRunPr = ((Paragraph)doc.getNodeById("1.2.0")).getParagraphBreakRunPr();

        // Special case for DOC's Toggle value.
        msAssert.areEqual((TestUtil.getUnifiedScenario(lf, sf) == UnifiedScenario.DOC_2_DOC) ? AttrBoolEx.TOGGLE : AttrBoolEx.TRUE, paraBreakRunPr.getDirectAttr(FontAttr.SPECIAL_HIDDEN));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedJira7533SourceDataProvider")
	public void unifiedJira7533Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedJira7533(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedJira7533SourceDataProvider")
	Object[][] unifiedJira7533SourceDataProvider() throws Exception
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
    /// Verify values of FarEast spacing.
    /// </summary>
    private static void verifyFeValues(Node paragraph, boolean farEastAndAlpha, boolean farEastAndDigit)
    {
        msAssert.areEqual(farEastAndAlpha, ((Paragraph)paragraph).getParagraphFormat().getAddSpaceBetweenFarEastAndAlpha());
        msAssert.areEqual(farEastAndDigit, ((Paragraph)paragraph).getParagraphFormat().getAddSpaceBetweenFarEastAndDigit());
    }

    /// <summary>
    /// Verify that property collection contains FarEast spacing attributes.
    /// </summary>
    private static void verifyFePresence(Node paragraph, boolean farEastAndAlpha, boolean farEastAndDigit)
    {
        msAssert.areEqual(farEastAndAlpha, ((Paragraph)paragraph).getParaPr().contains(ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_ALPHA));

        msAssert.areEqual(farEastAndDigit, ((Paragraph)paragraph).getParaPr().contains(ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_DIGIT));
    }

    /// <summary>
    /// WORDSNET-5937 While converting doc to rtf, paragraph left indent value is incorrect.
    /// Unit spacing/indent support.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestUnits(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\Para\\TestUnits", lf);

        msAssert.areEqual(SectionLayoutMode.LINE_GRID, doc.getFirstSection().getSectPr().getGridType());
        msAssert.areEqual(582, doc.getFirstSection().getSectPr().getLinePitch());
        ParaPr paraPr = doc.getFirstSection().getBody().getFirstParagraph().getParaPr();

        // 2 line before, no after
        msAssert.areEqual(200, paraPr.get(ParaAttr.SPACE_BEFORE_UNITS));
        msAssert.isNull(paraPr.get(ParaAttr.SPACE_AFTER_UNITS));

        msAssert.areEqual(1164, paraPr.getSpaceBefore());
        msAssert.areEqual(0, paraPr.getSpaceAfter());

        // 1 ch left and 2 ch right.
        msAssert.areEqual(100, paraPr.get(ParaAttr.LEFT_INDENT_UNITS));
        msAssert.areEqual(200, paraPr.get(ParaAttr.RIGHT_INDENT_UNITS));

        // These attributes present in original file but are updated in DocumentValidator anyway.
        msAssert.areEqual(200, paraPr.getLeftIndent());
        msAssert.areEqual(400, paraPr.getRightIndent());

        doc = TestUtil.saveOpen(doc, "Model\\Para\\TestUnits", lf, sf);
        paraPr = doc.getFirstSection().getBody().getFirstParagraph().getParaPr();
        // Units are preserved after open/save.
        msAssert.areEqual(200, paraPr.get(ParaAttr.SPACE_BEFORE_UNITS));
        msAssert.isNull(paraPr.get(ParaAttr.SPACE_AFTER_UNITS));
        msAssert.areEqual(100, paraPr.get(ParaAttr.LEFT_INDENT_UNITS));
        msAssert.areEqual(200, paraPr.getDirectAttr(ParaAttr.RIGHT_INDENT_UNITS));

        // Twips indents are updated properly and the same as in original document.
        msAssert.areEqual(200, paraPr.getLeftIndent());
        msAssert.areEqual(400, paraPr.getRightIndent());

        // Modify Normal Style and DocumentGrid line pitch.
        Style normal = doc.getStyles().getBySti(StyleIdentifier.NORMAL, true);
        normal.getRunPr().setSize(96); // 48 pt.
        doc.getFirstSection().getSectPr().setLinePitch(360);

        // Twips values are changed after open/save.
        doc = TestUtil.saveOpen(doc, "Model\\Para\\TestUnits Modified", lf, sf);
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        paraPr = para.getParaPr();

        msAssert.areEqual(200, paraPr.get(ParaAttr.SPACE_BEFORE_UNITS));                   // the same.
        msAssert.areEqual(720, paraPr.getSpaceBefore());                                  // was 1164.

        msAssert.areEqual(100, paraPr.getDirectAttr(ParaAttr.LEFT_INDENT_UNITS));      // the same.
        msAssert.areEqual(960, paraPr.getLeftIndent());                                   // was 100.

        // Test public setter removes units.
        msAssert.isNotNull(paraPr.get(ParaAttr.SPACE_BEFORE_UNITS));
        para.getParagraphFormat().setSpaceBefore(100.0);
        msAssert.isNull(paraPr.get(ParaAttr.SPACE_BEFORE_UNITS));

        msAssert.isNotNull(paraPr.get(ParaAttr.LEFT_INDENT_UNITS));
        para.getParagraphFormat().setLeftIndent(100.0);
        msAssert.isNull(paraPr.get(ParaAttr.LEFT_INDENT_UNITS));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestUnitsSourceDataProvider")
	public void unifiedTestUnitsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestUnits(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestUnitsSourceDataProvider")
	Object[][] unifiedTestUnitsSourceDataProvider() throws Exception
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
    /// WORDSNET-6998 Support suppressOverlap paragraph attribute.
    /// For details see:
    ///  1. DOCX - part 1 reference c059575_ISO_IEC_29500-1_2011 chapter 17.3.1.36 suppressOverlap (Prevent Text Frames From Overlapping),
    ///  2. DOC - MS-DOC chapter 2.6.2 Paragraph Properties,
    ///  3. Rtf - Word2007RTFSpec1.9.1 chapter Positioned Objects and Frames.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestFramesSuppressOverlap(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Para\\TestFramesSuppressOverlap", lf, sf);
        msAssert.isTrue(((Paragraph)doc.getNodeById("5.0.0")).getParaPr().getFrameSuppressOverlap());
        msAssert.isFalse(((Paragraph)doc.getNodeById("3.0.0")).getParaPr().getFrameSuppressOverlap());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFramesSuppressOverlapSourceDataProvider")
	public void unifiedTestFramesSuppressOverlapSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFramesSuppressOverlap(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFramesSuppressOverlapSourceDataProvider")
	Object[][] unifiedTestFramesSuppressOverlapSourceDataProvider() throws Exception
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
    /// WORDSNET-5375 Div elements are not supported in the model.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestHtmlBlock5375(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Para\\TestJira5375", lf, sf);

        /*UnifiedScenario*/int scenario = TestUtil.getUnifiedScenario(lf, sf);
        switch (scenario)
        {
            case UnifiedScenario.DOCX_2_DOCX_NO_GOLD:
            case UnifiedScenario.WML_2_WML_NO_GOLD:
                msAssert.areEqual(1, doc.getHtmlBlockCollection().getCount());
                break;
            default:
                msAssert.areEqual(2, doc.getHtmlBlockCollection().getCount());
                break;
        }

        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 1, true);
        int htmlBlockId = (/*int*/Integer)para.getParaPr().getDirectAttr(ParaAttr.HTML_BLOCK_ID);

        HtmlBlock htmlBlock = doc.getHtmlBlockCollection().getHtmlBlockById(htmlBlockId);
        msAssert.areEqual(HtmlBlockType.DIV, htmlBlock.getHtmlBlockType());
        msAssert.areEqual(720, htmlBlock.getParaPr().getDirectAttr(ParaAttr.HTML_MARGIN_LEFT));
        msAssert.isFalse(htmlBlock.getParaPr().contains(ParaAttr.HTML_MARGIN_RIGHT));

        // Verify expanding.
        msAssert.isFalse(para.getParaPr().contains(ParaAttr.LEFT_INDENT));
        ParaPr expParaPr = para.getExpandedParaPr(ParaPrExpandFlags.LAYOUT);
        msAssert.areEqual(720, expParaPr.getDirectAttr(ParaAttr.HTML_MARGIN_LEFT));
        msAssert.areEqual(0, expParaPr.getLeftIndent());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestHtmlBlock5375SourceDataProvider")
	public void unifiedTestHtmlBlock5375Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestHtmlBlock5375(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestHtmlBlock5375SourceDataProvider")
	Object[][] unifiedTestHtmlBlock5375SourceDataProvider() throws Exception
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
    /// Relates to WORDSNET-7898 Going to collect here various attribute collections
    /// to quickly test that ParaPr.IsFloating works correctly.
    /// </summary>
    @Test
    public void testParaPrIsFloating()
    {
        ParaPr paraPr = new ParaPr();

        // Insane test.
        msAssert.isFalse(paraPr.isFloating());

        // TestJira7898.doc
        paraPr.clear();
        paraPr.setAttr(ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION, RelativeHorizontalPosition.COLUMN);
        paraPr.setAttr(ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION, RelativeVerticalPosition.PAGE);
        msAssert.isFalse(paraPr.isFloating());

        // TestJira7720.docx
        paraPr.clear();
        paraPr.setAttr(ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION, RelativeHorizontalPosition.COLUMN);
        paraPr.setAttr(ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION, RelativeVerticalPosition.TEXT_FRAME_DEFAULT);
        paraPr.setAttr(ParaAttr.FRAME_WRAP_TYPE, WrapType.SQUARE);
        msAssert.isTrue(paraPr.isFloating());

        // TestDropCap.doc
        paraPr.clear();
        paraPr.setAttr(ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION, RelativeHorizontalPosition.COLUMN);
        paraPr.setAttr(ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION, RelativeVerticalPosition.TEXT_FRAME_DEFAULT);
        paraPr.setAttr(ParaAttr.FRAME_WRAP_TYPE, WrapType.SQUARE);
        paraPr.setAttr(ParaAttr.FRAME_HORIZONTAL_DISTANCE_FROM_TEXT, 80);
        msAssert.isTrue(paraPr.isFloating());

        // TestFloaterWrappedPositions.docx
        paraPr.clear();
        paraPr.setAttr(ParaAttr.FRAME_LEFT, 1081);
        paraPr.setAttr(ParaAttr.FRAME_TOP, 433);
        paraPr.setAttr(ParaAttr.FRAME_WIDTH, 2894);
        paraPr.setAttr(ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION, RelativeHorizontalPosition.PAGE);
        paraPr.setAttr(ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION, RelativeVerticalPosition.TEXT_FRAME_DEFAULT);
        paraPr.setAttr(ParaAttr.FRAME_WRAP_TYPE, WrapType.SQUARE);
        paraPr.setAttr(ParaAttr.FRAME_VERTICAL_DISTANCE_FROM_TEXT, 187);
        paraPr.setAttr(ParaAttr.FRAME_HORIZONTAL_DISTANCE_FROM_TEXT, 187);
        msAssert.isTrue(paraPr.isFloating());
    }


    /// <summary>
    /// WORDSNET-5641 Indent of the second line of list item is incorrect after rendering.
    /// TabStop expanding was fixed recently and I think additional test is not superfluous due to issue complexity.
    /// </summary>
    @Test
    public void testJira5641() throws Exception
    {
        Document doc = TestUtil.open("Model\\Para\\TestJira5641.docx");
        Node[] nodes = doc.getChildNodes(NodeType.PARAGRAPH, true).toArray();

        Paragraph para2 = (Paragraph)nodes[1];
        ParaPr paraPr2 = para2.getExpandedParaPr(ParaPrExpandFlags.DOCUMENT_DEFAULTS);

        TabStopCollection tabStops = paraPr2.getTabStops();
        msAssert.areEqual(2, tabStops.getCount());

        checkTabStop(tabStops.get(0), TabAlignment.LIST, 10.8);
        checkTabStop(tabStops.get(1), TabAlignment.CLEAR, 28.8);
    }



    /// <summary>
    /// WORDSNET-7279 providing a JoinRunsWithSameFormatting method that works on Paragraph level
    /// </summary>
    @Test
    public void testJira7279() throws Exception
    {
        Document doc = new Document();
        Paragraph p1 = new Paragraph(doc);
        Paragraph p2 = new Paragraph(doc);

        p1.appendChild(new Run(doc, "Join1"));
        p1.appendChild(new Run(doc, "Join2"));
        p1.appendChild(new Run(doc, "Join3"));

        p2.appendChild(new Run(doc, "Join1"));
        p2.appendChild(new Run(doc, "Join2"));
        p2.appendChild(new Run(doc, "Join3"));

        // All p1 runs different, thus no joins have to be performed.
        p1.getRuns().get(0).getRunPr().setAttr(FontAttr.SIZE, 72);
        p1.getRuns().get(1).getRunPr().setAttr(FontAttr.BOLD, AttrBoolEx.TRUE);
        p1.getRuns().get(2).getRunPr().setAttr(FontAttr.COLOR, DrColor.DARK_CYAN);
        msAssert.areEqual(0, p1.joinRunsWithSameFormatting());

        // All runs in p2 paragraph have same formatting, thus 2 joins have to be performed
        msAssert.areEqual(2, p2.joinRunsWithSameFormatting());
    }

    private static void checkTabStop(TabStop tab, /*TabAlignment*/int alignment, double position)
    {
        msAssert.areEqual(alignment, tab.getAlignment());
        msAssert.areEqual(position, tab.getPosition());
    }

    /// <summary>
    /// WORDSNET-8657 DropCapPostion property is not working.
    /// The problem occurs because it was not enough to set DropCapPosition to none,
    /// we also have to concatenate DropCap frame paragraph with original paragraph and restore the run formatting.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestSetDropCapPositionToNone(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\Para\\TesJira8657", lf);
        NodeCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(11, paragraphs.getCount());

        Paragraph parWithDrop = doc.getFirstSection().getBody().getParagraphs().get(4);
        msAssert.areEqual(DropCapPosition.NORMAL, parWithDrop.getParagraphFormat().getDropCapPosition());
        msAssert.areEqual(83.0d, parWithDrop.getFirstRun().getFont().getSize());

        // Set DropCapPosition to None for all paragraphs inside document.
        for (Paragraph para : (Iterable<Paragraph>) paragraphs)
            para.getParagraphFormat().setDropCapPosition(DropCapPosition.NONE);

        doc = TestUtil.saveOpen(doc, "Model\\Para\\TesJira8657", TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD);
        paragraphs = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(8, paragraphs.getCount());

        parWithDrop = doc.getFirstSection().getBody().getParagraphs().get(3);
        msAssert.areEqual(DropCapPosition.NONE, parWithDrop.getParagraphFormat().getDropCapPosition());
        msAssert.areEqual(11.0d, parWithDrop.getFirstRun().getFont().getSize());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestSetDropCapPositionToNoneSourceDataProvider")
	public void unifiedTestSetDropCapPositionToNoneSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestSetDropCapPositionToNone(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestSetDropCapPositionToNoneSourceDataProvider")
	Object[][] unifiedTestSetDropCapPositionToNoneSourceDataProvider() throws Exception
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
    /// Relates to WORDSNET-10090
    /// Tests how ParaPr.IsFloating works on various combination of frame attributes.
    /// </summary>
    @Test
    public void testIsFloating() throws Exception
    {
        // <w:framePr w:xAlign="left" w:yAlign="center" />
        verifyIsFloating("Model\\Para\\TestIsFloating A.xml", true);

        // <w:framePr w:xAlign="left" w:yAlign="inline" />
        verifyIsFloating("Model\\Para\\TestIsFloating B.xml", false);

        // Both FrameTop and FrameLeft are 0. Paragraph is not floating.
        // <w:framePr w:x="0" w:y="0" />
        verifyIsFloating("Model\\Para\\TestIsFloating C.xml", false);

        // FrameTop is greater than 0. Paragraph is floating.
        // <w:framePr w:x="0" w:y="1" />
        verifyIsFloating("Model\\Para\\TestIsFloating D.xml", true);

        // Only FrameWidth is defined. Paragraph is floating.
        // <w:framePr w:w="1701" />
        verifyIsFloating("Model\\Para\\TestIsFloating E.xml", true);

        // Non default RelativeHorizontalPosition. Paragraph is floating.
        //<w:framePr w:hAnchor="page" />
        verifyIsFloating("Model\\Para\\TestIsFloating F.xml", true);

        // Zero FrameWidth. Paragraph is not floating.
        //<w:framePr w:w="0" />
        verifyIsFloating("Model\\Para\\TestIsFloating G.xml", false);

        // Default RelativeHorizontalPosition. Paragraph is not floating.
        // <w:framePr w:hAnchor="text" />
        verifyIsFloating("Model\\Para\\TestIsFloating H.xml", false);

        // Only DistanceToText is specified. Paragraph is not floating.
        // <w:framePr w:hSpace="57" />
        verifyIsFloating("Model\\Para\\TestIsFloating I.xml", false);

        // RelativeVerticalPosition is Page. Paragraph is not floating.
        // <w:framePr w:hAnchor="text" w:vAnchor="page" />
        verifyIsFloating("Model\\Para\\TestIsFloating J.xml", false);

        // RelativeVerticalPosition is Margin. Paragraph is not floating.
        // <w:framePr w:hAnchor="text" w:vAnchor="margin" />
        verifyIsFloating("Model\\Para\\TestIsFloating K.xml", false);

        // RelativeVerticalPosition is Text. Paragraph is floating.
        // <w:framePr w:hAnchor="text" w:vAnchor="text" />
        verifyIsFloating("Model\\Para\\TestIsFloating L.xml", true);

        // Only RelativeVerticalPosition Text is specified. Paragraph is floating.
        // <w:framePr w:vAnchor="text" />
        verifyIsFloating("Model\\Para\\TestIsFloating M.xml", true);
    }


    /// <summary>
    /// Relates to WORDSNET-10090
    /// </summary>
    @Test
    public void testJira8883() throws Exception
    {
        Document doc = TestUtil.open("Model\\Para\\TestJira8883.docx");
        Paragraph p = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.isTrue(p.getExpandedParaPr(ParaPrExpandFlags.LAYOUT).isFloating());
    }



    /// <summary>
    /// WORDSNET-4870 Paragraph alignment “Justify Low” is changed to “Justified” after open/save.
    /// </summary>
    /// <remarks>
    /// AM. I think we are not ready to expose Arabic alignment to public, probably we even need separate build for it.
    /// That's why I decided to fix roundtrip in this strange way.
    /// As a result document is roundtriped properly but customer has no access to Arabic alignment types.
    /// </remarks>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestJira4870(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Para\\TestJira4870", TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(ParagraphAlignment.LEFT, paras.get(0).getParaPr().getAlignment());
        msAssert.areEqual(ParagraphAlignment.CENTER, paras.get(1).getParaPr().getAlignment());
        msAssert.areEqual(ParagraphAlignment.RIGHT, paras.get(2).getParaPr().getAlignment());
        msAssert.areEqual(ParagraphAlignment.JUSTIFY, paras.get(3).getParaPr().getAlignment());
        msAssert.areEqual(ParagraphAlignment.DISTRIBUTED, paras.get(4).getParaPr().getAlignment());
        msAssert.areEqual(ParagraphAlignment.ARABIC_LOW_KASHIDA, paras.get(5).getParaPr().getAlignment());
        msAssert.areEqual(ParagraphAlignment.ARABIC_MEDIUM_KASHIDA, paras.get(6).getParaPr().getAlignment());
        msAssert.areEqual(ParagraphAlignment.ARABIC_HIGH_KASHIDA, paras.get(7).getParaPr().getAlignment());
        msAssert.areEqual(ParagraphAlignment.THAI_DISTRIBUTED, paras.get(8).getParaPr().getAlignment());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJira4870SourceDataProvider")
	public void unifiedTestJira4870Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJira4870(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJira4870SourceDataProvider")
	Object[][] unifiedTestJira4870SourceDataProvider() throws Exception
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
    /// Relates to WORDSNET-12849 Checks that units conversion is correct for all paragraphs.
    /// </summary>
    @Test
    public void testJira12849A() throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\Para\\TestUnits ms.docx");
        Document dstDoc = TestUtil.open("Model\\Para\\TestUnits ms.docx");

        // Update indents in one of document.
        TestUtil.executeValidator(dstDoc, SaveFormat.DOCX);   // FOSS: was Pdf.

        // Verify indents are not changed.
        for (int i = 0; i < srcDoc.getFirstSection().getBody().getParagraphs().getCount(); i++)
        {
            ParaPr srcParaPr = srcDoc.getFirstSection().getBody().getParagraphs().get(i).getParaPr();
            ParaPr dstParaPr = dstDoc.getFirstSection().getBody().getParagraphs().get(i).getParaPr();

            // Check all indent attributes.
            msAssert.areEqual(srcParaPr.fetchAttr(ParaAttr.FIRST_LINE_INDENT), dstParaPr.fetchAttr(ParaAttr.FIRST_LINE_INDENT));
            msAssert.areEqual(srcParaPr.fetchAttr(ParaAttr.LEFT_INDENT), dstParaPr.fetchAttr(ParaAttr.LEFT_INDENT));
            msAssert.areEqual(srcParaPr.fetchAttr(ParaAttr.RIGHT_INDENT), dstParaPr.fetchAttr(ParaAttr.RIGHT_INDENT));

            // We don't update units but check them all as well.
            msAssert.areEqual(srcParaPr.fetchAttr(ParaAttr.FIRST_LINE_INDENT_UNITS), dstParaPr.fetchAttr(ParaAttr.FIRST_LINE_INDENT_UNITS));
            msAssert.areEqual(srcParaPr.fetchAttr(ParaAttr.LEFT_INDENT_UNITS), dstParaPr.fetchAttr(ParaAttr.LEFT_INDENT_UNITS));
            msAssert.areEqual(srcParaPr.fetchAttr(ParaAttr.RIGHT_INDENT_UNITS), dstParaPr.fetchAttr(ParaAttr.RIGHT_INDENT_UNITS));
        }
    }

    /// <summary>
    /// WORDSNET-14113 Add feature to support Frames in Aspose.Words
    /// Frame properties are exposed public.
    /// </summary>
    @Test
    public void testJira14113() throws Exception
    {
        Document doc = TestUtil.open("Model\\Para\\TestJira14113.xml");

        Style normal = doc.getStyles().get("Normal");
        msAssert.areEqual(0.0d, normal.getFrameFormat().getHorizontalPosition());
        msAssert.areEqual(0.0d, normal.getFrameFormat().getVerticalPosition());
        msAssert.isFalse(normal.getFrameFormat().isFrame());

        Style framed = doc.getStyles().get("Framed");
        msAssert.areEqual(0.0d, framed.getFrameFormat().getHorizontalPosition());
        msAssert.areEqual(85.05, .01, framed.getFrameFormat().getWidth());
        msAssert.isTrue(framed.getFrameFormat().isFrame());

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.isFalse(paras.get(0).getFrameFormat().isFrame());
        msAssert.isTrue(paras.get(1).getFrameFormat().isFrame());
        msAssert.isTrue(paras.get(2).getFrameFormat().isFrame());
    }


    /// <summary>
    /// WORDSNET-14123 Unsupported elements that are used for simultaneously editing of a document
    /// Added roundtrip for paraId and textId attributes.
    /// </summary>
    @Test
    public void testJira14123A() throws Exception
    {
        final String TEST_NAME = "Model\\Para\\TestJira14123A.docx";

        Document doc = TestUtil.open(TEST_NAME);
        OoxmlSaveOptions so = (OoxmlSaveOptions)SaveOptions.createSaveOptions(SaveFormat.DOCX);
        so.setWriteExtendedIds(true);
        doc = TestUtil.saveOpen(doc, TEST_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD, so);

        Paragraph p = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(0x07891758, p.getParaId());
        msAssert.areEqual(0x77777777, p.getTextId());
    }

    /// <summary>
    /// Relates to WORDSNET-14123 Tests that textId and paraId is preserved for rows.
    /// </summary>
    @Test
    public void testJira14123B() throws Exception
    {
        final String TEST_NAME = "Model\\Para\\TestJira14123B.docx";

        Document doc = TestUtil.open(TEST_NAME);
        OoxmlSaveOptions so = (OoxmlSaveOptions)SaveOptions.createSaveOptions(SaveFormat.DOCX);
        so.setWriteExtendedIds(true);
        doc = TestUtil.saveOpen(doc, TEST_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD, so);

        Row row = doc.getFirstSection().getBody().getTables().get(0).getFirstRow();
        msAssert.areEqual(0x111bfe40, row.getParaId());
        msAssert.areEqual(0x77777777, row.getTextId());
    }

    // FOSS: TestJira15260 removed — it loaded a .dot (removed format) and asserted a layout-computed
    // expansion (GetExpandedParaPr(Layout)); both .dot load and the layout engine are gone.

    /// <summary>
    /// WORDSNET-18436 Provide API to identify Style Separator Paragraph.
    /// <see cref="Paragraph.BreakIsStyleSeparator"/> public property was introduced.
    /// </summary>
    @Test
    public void testJira18436() throws Exception
    {
        final String TEST_NAME = "Model\\Para\\TestJira18436.docx";

        Document doc = TestUtil.open(TEST_NAME);
        Paragraph p = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.isTrue(p.getBreakIsStyleSeparator());
    }


    /// <summary>
    /// WORDSNET-19313 DOC to Fixed page format conversion issue with page header
    /// Any inline including shapes should be considered in twips per unit calculation.
    /// </summary>
    @Test
    public void test19313() throws Exception
    {
        Document doc = TestUtil.open("Model\\Para\\Test19313.docx");

        TestUtil.executeValidator(doc, SaveFormat.DOCX);

        ParaPr paraPr = doc.getFirstSection().getBody().getFirstParagraph().getParaPr();
        msAssert.areEqual(-1000, paraPr.get(ParaAttr.FIRST_LINE_INDENT_UNITS));

        msAssert.areEqual(1600, paraPr.get(ParaAttr.LEFT_INDENT));
        msAssert.areEqual(-1600, paraPr.get(ParaAttr.FIRST_LINE_INDENT));
    }





    /// <summary>
    /// WORDSNET-19747 Add feature to set/get paragraph property "Snap to grid when document grid is defined"
    /// SnapToGrid Font/ParagraphFormat property was added.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTest19747(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        //Check that property is reading well
        Document doc = TestUtil.open("Model\\Para\\Test19747", lf);
        checkSnapToGrid(doc, false);

        //Check that property is reading well after save
        doc = TestUtil.saveOpen(doc, "Model\\Para\\Test19747", TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD);
        checkSnapToGrid(doc, false);

        //Check that property is reading well after setting values and save
        toggleSnapToGrid(doc);
        doc = TestUtil.saveOpen(doc, "Model\\Para\\Test19747", TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD);
        checkSnapToGrid(doc, true);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTest19747SourceDataProvider")
	public void unifiedTest19747Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTest19747(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTest19747SourceDataProvider")
	Object[][] unifiedTest19747SourceDataProvider() throws Exception
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
    /// WORDSNET-19752 SpaceBefore and SpaceAfter return incorrect value for MS Word 2007 document
    /// Added unit conversion to DocumentPostLoader.
    /// </summary>
    @Test
    public void test19752() throws Exception
    {
        Document doc = TestUtil.open("Model\\Para\\Test19752.docx");

        ParagraphFormat format = doc.getFirstSection().getBody().getFirstParagraph().getParagraphFormat();
        msAssert.areEqual(10.5, format.getLeftIndent());
        msAssert.areEqual(21, format.getRightIndent());
        msAssert.areEqual(15.6, format.getSpaceBefore());
        msAssert.areEqual(31.2, format.getSpaceAfter());
    }

    /// <summary>
    /// Relates to WORDSNET-19752
    /// Tests customer documents.
    /// </summary>
    @Test
    public void test19752A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Para\\Test19752_2007.docx");
        msAssert.areEqual(312, doc.getFirstSection().getBody().getFirstParagraph().getParaPr().get(ParaAttr.SPACE_BEFORE));

        doc = TestUtil.open("Model\\Para\\Test19752_2016.docx");
        msAssert.areEqual(312, doc.getFirstSection().getBody().getFirstParagraph().getParaPr().get(ParaAttr.SPACE_BEFORE));
    }

    /// <summary>
    /// WORDSNET-20001 Provide API to get set Word for Chinese specific Paragraph formatting
    /// Added unit spacings and indents to public API.
    /// </summary>
    @Test
    public void test20001() throws Exception
    {
        Document doc = TestUtil.open("Model\\Para\\Test20001.docx");
        ParagraphFormat format = doc.getFirstSection().getBody().getFirstParagraph().getParagraphFormat();

        // Check preconditions.
        msAssert.areEqual(1, format.getLineUnitBefore());
        msAssert.areEqual(2, format.getLineUnitAfter());
        msAssert.areEqual(1, format.getCharacterUnitLeftIndent());
        msAssert.areEqual(2, format.getCharacterUnitRightIndent());
        msAssert.areEqual(0, format.getCharacterUnitFirstLineIndent());

        msAssert.areEqual(15.6, format.getSpaceBefore());
        msAssert.areEqual(31.2, 0.01, format.getSpaceAfter());
        msAssert.areEqual(10.5, format.getLeftIndent());
        msAssert.areEqual(21, format.getRightIndent());
        msAssert.areEqual(0, format.getFirstLineIndent());

        // Set units.
        format.setLineUnitBefore(10.0);
        msAssert.areEqual(10, format.getLineUnitBefore());
        msAssert.areEqual(156, format.getSpaceBefore());

        format.setLineUnitAfter(20.0);
        msAssert.areEqual(20, format.getLineUnitAfter());
        msAssert.areEqual(312, format.getSpaceAfter());

        format.setCharacterUnitLeftIndent(30.0);
        msAssert.areEqual(30, format.getCharacterUnitLeftIndent());
        msAssert.areEqual(315, format.getLeftIndent());

        format.setCharacterUnitRightIndent(40.0);
        msAssert.areEqual(40, format.getCharacterUnitRightIndent());
        msAssert.areEqual(420, format.getRightIndent());

        format.setCharacterUnitFirstLineIndent(50.0);
        msAssert.areEqual(50, format.getCharacterUnitFirstLineIndent());
        msAssert.areEqual(525, format.getFirstLineIndent());
    }

    /// <summary>
    /// Changed type for CharacterUnitLeftIndent, CharacterUnitRightIndent, CharacterUnitFirstLineIndent,
    /// LineUnitBefore and LineUnitAfter.
    /// </summary>
    @Test
    public void test20001Double() throws Exception
    {
        Document doc = TestUtil.open("Model\\Para\\Test20001.docx");
        ParagraphFormat format = doc.getFirstSection().getBody().getFirstParagraph().getParagraphFormat();

        format.setCharacterUnitLeftIndent(0.5);
        format.setCharacterUnitRightIndent(1.5);
        format.setCharacterUnitFirstLineIndent(2.5);
        format.setLineUnitBefore(3.5);
        format.setLineUnitAfter(4.5);

        doc = TestUtil.saveOpen(doc, "Model\\Para\\Test20001.docx");

        format = doc.getFirstSection().getBody().getFirstParagraph().getParagraphFormat();
        msAssert.areEqual(0.5, format.getCharacterUnitLeftIndent());
        msAssert.areEqual(1.5, format.getCharacterUnitRightIndent());
        msAssert.areEqual(2.5, format.getCharacterUnitFirstLineIndent());
        msAssert.areEqual(3.5, format.getLineUnitBefore());
        msAssert.areEqual(4.5, format.getLineUnitAfter());
    }

    /// <summary>
    /// WORDSNET-21040 Cell text is rendered at right side in output PDF.
    /// CharacterUnitLeftIndent and CharacterUnitFirstLineIndent should be ignored while expanding
    /// paragraph attributes with ListId explicitly set to 0. Some details can be also found
    /// in 17.9.18 numId (Numbering Definition Instance Reference) of ISO29500-1.
    /// </summary>
    // FOSS: only Docx survives — Pdf/Rtf/WordML save were removed.
    @Test (dataProvider = "test21040DataProvider")
    public void test21040(/*SaveFormat*/int sf) throws Exception
    {
        final String TEST_FILE_NAME = "Model\\Para\\Test21040";
        Document doc = TestUtil.open(TEST_FILE_NAME, LoadFormat.DOCX);

        // The problematic paragraph.
        Paragraph para = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getFirstCell().getFirstParagraph();
        ParaPr paraPrExp = para.getExpandedParaPr(ParaPrExpandFlags.NORMAL);
        msAssert.isNull(paraPrExp.get(ParaAttr.LEFT_INDENT_UNITS));
        msAssert.isNull(paraPrExp.get(ParaAttr.FIRST_LINE_INDENT_UNITS));

        msAssert.areEqual(0, para.getParagraphFormat().getCharacterUnitLeftIndent());
        msAssert.areEqual(0, para.getParagraphFormat().getCharacterUnitFirstLineIndent());

            String outFileName = msString.format("{0}{1}", TEST_FILE_NAME, FileFormatUtil.saveFormatToExtension(sf));
            TestUtil.save(doc, outFileName, SaveOptions.createSaveOptions(sf), true);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test21040DataProvider")
	public static Object[][] test21040DataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}




    /// <summary>
    /// WORDSNET-26172 Add public API to get/set MirrorIndents paragraph property.
    /// Tests MirrorIndents public API in ParagraphFormat.
    /// </summary>
    @Test
    public void test26172() throws Exception
    {
        final String TEST_FILE = "Model\\Para\\Test26172";
        Document doc = TestUtil.open(TEST_FILE, LoadFormat.DOCX);
        ParagraphFormat paraFormat1 = doc.getFirstSection().getBody().getParagraphs().get(0).getParagraphFormat();
        ParagraphFormat paraFormat2 = doc.getFirstSection().getBody().getParagraphs().get(1).getParagraphFormat();

        msAssert.isFalse(paraFormat1.getMirrorIndents());
        msAssert.isTrue(paraFormat2.getMirrorIndents());

        paraFormat1.setMirrorIndents(true);
        paraFormat2.setMirrorIndents(false);

        doc = TestUtil.saveOpen(doc, TEST_FILE, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        paraFormat1 = doc.getFirstSection().getBody().getParagraphs().get(0).getParagraphFormat();
        paraFormat2 = doc.getFirstSection().getBody().getParagraphs().get(1).getParagraphFormat();

        msAssert.isTrue(paraFormat1.getMirrorIndents());
        msAssert.isFalse(paraFormat2.getMirrorIndents());
    }

    /// <summary>
    /// WORDSNET-25493 Table row is moved to the next page after rendering.
    /// Resolved per WORDSNET-26551, just added test.
    /// </summary>
    @Test
    public void test25493() throws Exception
    {
        Document doc = TestUtil.open("Model\\Para\\Test25493.docx");
        Paragraph para = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY).getFirstParagraph();

        msAssert.areEqual(2900, para.getParaPr().get(ParaAttr.SPACE_BEFORE_UNITS));
        msAssert.areEqual(6960, para.getParaPr().get(ParaAttr.SPACE_BEFORE));
    }









    /// <summary>
    /// WORDSNET-28604. JoinRunsWithSameFormatting does not join runs.
    /// Tests JoinRunsWithSameFormatting() with new options.
    /// IgnoreSpacing case.
    /// </summary>
    @Test
    public void test28604_IgnoreSpacing() throws Exception
    {
        // FOSS: only the Test28604B input ships with this edition.
        int[] expectedB = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        testJoinRunsWithOption("Test28604B", "IgnoreSpacing", expectedB);
    }

    /// <summary>
    /// Relates to WORDSNET-28604.
    /// IgnoreRedundant case.
    /// </summary>
    @Test
    public void test28604_IgnoreRedundant() throws Exception
    {
        // FOSS: only the Test28604B input ships with this edition.
        int[] expectedB = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        testJoinRunsWithOption("Test28604B", "IgnoreRedundant", expectedB);
    }

    /// <summary>
    /// Relates to WORDSNET-28604.
    /// IgnoreInsignificant case.
    /// </summary>
    @Test
    public void test28604_IgnoreInsignificant() throws Exception
    {
        // FOSS: only the Test28604B input ships with this edition.
        int[] expectedB = new int[] { 0, 2, 0, 2, 4, 0, 0, 3, 1, 0, 1 };
        testJoinRunsWithOption("Test28604B", "IgnoreInsignificant", expectedB);
    }

    private void testJoinRunsWithOption(String fileName, String optionName, int[] expJoins) throws Exception
    {
        Document doc = TestUtil.open(msString.format("Model\\Para\\{0}.docx", fileName));

        JoinRunsOptions options = new JoinRunsOptions();
        switch (gStringSwitchMap.of(optionName))
        {
            case /*"IgnoreSpacing"*/0:
                options.setIgnoreSpacing(true);
                break;
            case /*"IgnoreRedundant"*/1:
                options.setIgnoreRedundant(true);
                break;
            case /*"IgnoreInsignificant"*/2:
                options.setIgnoreInsignificant(true);
                break;
        }

        NodeCollection paras = doc.getFirstSection().getBody().getChildNodes(NodeType.PARAGRAPH, true);
        int i = 0;
        for (Paragraph para : (Iterable<Paragraph>) paras)
        {
            int joinCount = para.joinRunsWithSameFormatting(options);
            msAssert.areEqual(expJoins[i++], joinCount);
        }

        TestUtil.saveCheckGold(doc, msString.format("Model\\Para\\{0}_{1}.docx", fileName, optionName));
    }

    /// <summary>
    /// Change false to true and true to false for the SnapToGrid property
    /// of the test document content.
    /// </summary>
    private static void toggleSnapToGrid(Document doc)
    {
        for (int i = 0; i < 4; i++)
        {
            Paragraph para = doc.getFirstSection().getBody().getParagraphs().get(i);
            para.getParagraphFormat().setSnapToGrid(!para.getParagraphFormat().getSnapToGrid());
            para.getFirstRun().getFont().setSnapToGrid(!para.getFirstRun().getFont().getSnapToGrid());
        }
    }

    /// <summary>
    /// Check SnapToGrid values for the test document content.
    /// </summary>
    /// <param name="doc"></param>
    /// <param name="isPropertyToggled"></param>
    private static void checkSnapToGrid(Document doc, boolean isPropertyToggled)
    {
        //The SnapToGrid property has the following distribution when isPropertyToggled is false
        //When isPropertyToggled is true, true and false values are swapped
        //
        //      | PARA  | FONT  |
        // Par1 | true  | true  |
        // Par2 | false | false |
        // Par3 | true  | false |
        // Par4 | false | true  |

        boolean toggledTrue = isPropertyToggled ? false : true;
        boolean toggledFalse = isPropertyToggled ? true : false;

        Paragraph par1 = doc.getFirstSection().getBody().getParagraphs().get(0);
        Paragraph par2 = doc.getFirstSection().getBody().getParagraphs().get(1);
        Paragraph par3 = doc.getFirstSection().getBody().getParagraphs().get(2);
        Paragraph par4 = doc.getFirstSection().getBody().getParagraphs().get(3);

        msAssert.areEqual(toggledTrue, par1.getParagraphFormat().getSnapToGrid());
        msAssert.areEqual(toggledFalse, par2.getParagraphFormat().getSnapToGrid());
        msAssert.areEqual(toggledTrue, par3.getParagraphFormat().getSnapToGrid());
        msAssert.areEqual(toggledFalse, par4.getParagraphFormat().getSnapToGrid());

        msAssert.areEqual(toggledTrue, par1.getFirstRun().getFont().getSnapToGrid());
        msAssert.areEqual(toggledFalse, par2.getFirstRun().getFont().getSnapToGrid());
        msAssert.areEqual(toggledFalse, par3.getFirstRun().getFont().getSnapToGrid());
        msAssert.areEqual(toggledTrue, par4.getFirstRun().getFont().getSnapToGrid());

    }

    /// <summary>
    /// Helper method. Verifies ParaPr.IsFloating result for fully resolved first paragraph in given document.
    /// </summary>
    private static void verifyIsFloating(String fileName, boolean isFloating) throws Exception
    {
        Document doc = TestUtil.open(fileName);
        Paragraph p = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual(isFloating, p.getExpandedParaPr(ParaPrExpandFlags.LAYOUT).isFloating());
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"IgnoreSpacing",
		"IgnoreRedundant",
		"IgnoreInsignificant"
	);

}

