// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/02/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
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


/// <summary>
/// Test suite for Office Math feature.
/// </summary>
@Test
public class TestMath extends UnifiedTestsBase
{
    @BeforeMethod (alwaysRun = true)
    public void eachTestSetUp()
    {
        RandomUtil.reset();
    }

    /// <summary>
    /// Test that bookmarks inside Office Math subtree are roundtripped in/out of the model.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestMathBookmarks(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setConvertShapeToOfficeMath(true);

        Document doc = checkAndLoadDocument("Model\\Math\\TestMathBookmarks", lf, sf, lo);

        OfficeMath node = getOfficeMathTestNode(doc);
        msAssert.areEqual(MathObjectType.N_ARY, node.getMathObjectType());

        node = (OfficeMath)node.getChildNodes(NodeType.ANY, false).get(0);
        msAssert.areEqual(MathObjectType.SUBSCRIPT_PART, node.getMathObjectType());

        node = (OfficeMath)node.getChildNodes(NodeType.ANY, false).get(0);
        msAssert.areEqual(MathObjectType.ARRAY, node.getMathObjectType());

        node = (OfficeMath)node.getChildNodes(NodeType.ANY, false).get(1);
        msAssert.areEqual(MathObjectType.ARGUMENT, node.getMathObjectType());

        msAssert.areEqual(NodeType.BOOKMARK_START, node.getChildNodes(NodeType.ANY, false).get(1).getNodeType());
        msAssert.areEqual(NodeType.RUN, node.getChildNodes(NodeType.ANY, false).get(2).getNodeType());
        msAssert.areEqual(NodeType.BOOKMARK_END, node.getChildNodes(NodeType.ANY, false).get(3).getNodeType());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestMathBookmarksSourceDataProvider")
	public void unifiedTestMathBookmarksSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestMathBookmarks(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestMathBookmarksSourceDataProvider")
	Object[][] unifiedTestMathBookmarksSourceDataProvider() throws Exception
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
    /// Test document-wide math properties.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestMathProperties(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        //WML format doesn't support default math properties.
        if (sf == SaveFormat.WORD_ML)
            return;

        final String TEST_NAME = "Model\\Math\\TestMathProperties";

        Document doc = new Document();
        MathProperties mathPr = doc.getDocPr().MathProperties;
        mathPr.setBreakOnBinary(MathBreakOnBinary.REPEAT);
        mathPr.setBreakOnBinarySubtraction(MathBreakOnBinarySubtraction.PLUS_MINUS);
        mathPr.setDefaultFont("Times New Roman");
        mathPr.setDefaultJustification(OfficeMathJustification.RIGHT);
        mathPr.setIntegralLimitLocation(MathLimitLocation.UNDER_OVER);
        mathPr.setNaryLimitLocation(MathLimitLocation.SUBSCRIPT_SUPERSCRIPT);
        mathPr.setLeftMargin(10);
        mathPr.setRightMargin(20);
        mathPr.setInterEquationSpacing(30);
        mathPr.setIntraEquationSpacing(40);
        mathPr.setPostParagraphSpacing(50);
        mathPr.setPreParagraphSpacing(60);
        mathPr.isSmallFraction(true);
        mathPr.setUseDisplayMathDefaults(false);

        MathProperties test = TestUtil.saveOpen(doc, TEST_NAME, lf, sf).getDocPr().MathProperties;

        if (lf != LoadFormat.DOC)
        {
            msAssert.areEqual(MathBreakOnBinary.REPEAT, test.getBreakOnBinary());
            msAssert.areEqual(MathBreakOnBinarySubtraction.PLUS_MINUS, test.getBreakOnBinarySubtraction());
            msAssert.areEqual("Times New Roman", test.getDefaultFont());
            msAssert.areEqual(OfficeMathJustification.RIGHT, test.getDefaultJustification());
            msAssert.areEqual(MathLimitLocation.UNDER_OVER, mathPr.getIntegralLimitLocation());
            msAssert.areEqual(MathLimitLocation.SUBSCRIPT_SUPERSCRIPT, mathPr.getNaryLimitLocation());
            msAssert.areEqual(10, test.getLeftMargin());
            msAssert.areEqual(20, test.getRightMargin());
            msAssert.areEqual(30, test.getInterEquationSpacing());
            msAssert.areEqual(40, test.getIntraEquationSpacing());
            msAssert.areEqual(50, test.getPostParagraphSpacing());
            msAssert.areEqual(60, test.getPreParagraphSpacing());
            msAssert.isTrue(test.isSmallFraction());
            msAssert.isFalse(test.getUseDisplayMathDefaults());
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestMathPropertiesSourceDataProvider")
	public void unifiedTestMathPropertiesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestMathProperties(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestMathPropertiesSourceDataProvider")
	Object[][] unifiedTestMathPropertiesSourceDataProvider() throws Exception
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
    /// Office Math can contain runs with w:rPr and m:rPr.
    /// Test that both properties work.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestMathRunPr(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setConvertShapeToOfficeMath(true);

        Document doc = checkAndLoadDocument("Model\\Math\\TestMathRunPr", lf, sf, lo);
        Paragraph p = (Paragraph)(doc.getSections().get(0).getBody()).getChildNodes(NodeType.ANY, false).get(1);
        Run run = (Run)((OfficeMath)((OfficeMath)p.getFirstChild()).getFirstChild()).getFirstChild();
        RunPr rPr = run.getRunPr();

        msAssert.areEqual(com.aspose.words.MathStyle.BOLD_ITALIC, rPr.getMathStyle());
        msAssert.areEqual(DrColor.YELLOW, rPr.getHighlightColor());
        msAssert.areEqual(AttrBoolEx.TRUE, rPr.getStrikeThrough());
        msAssert.areEqual(AttrBoolEx.TRUE, rPr.getSmallCaps());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestMathRunPrSourceDataProvider")
	public void unifiedTestMathRunPrSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestMathRunPr(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestMathRunPrSourceDataProvider")
	Object[][] unifiedTestMathRunPrSourceDataProvider() throws Exception
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
    /// Test oMathPara, test argPr
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestOMathPara(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setConvertShapeToOfficeMath(true);

        Document doc = checkAndLoadDocument("Model\\Math\\TestOMathPara", lf, sf, lo);
        OfficeMath node = (OfficeMath)((CompositeNode)((doc.getSections().get(0).getBody()).getChildNodes(NodeType.ANY, false).get(0))).getChildNodes(NodeType.ANY, false).get(0);
        OfficeMath arg = (OfficeMath) getOfficeMathTestNode(doc).getChildNodes(NodeType.ANY, false).get(0);

        msAssert.areEqual(MathObjectType.O_MATH_PARA, node.getMathObject().getMathObjectType());
        if((lf == LoadFormat.DOC) || (lf==LoadFormat.WORD_ML))
        {
            msAssert.areEqual(OfficeMathJustification.CENTER_GROUP, ((MathObjectOMathPara)node.getMathObject()).getJustification());
            msAssert.areEqual(0, ((MathObjectArgumentBase)arg.getMathObject()).getArgumentSize());
        }
        else
        {
            msAssert.areEqual(OfficeMathJustification.RIGHT, ((MathObjectOMathPara) node.getMathObject()).getJustification());
            msAssert.areEqual(-1, ((MathObjectArgumentBase) arg.getMathObject()).getArgumentSize());
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestOMathParaSourceDataProvider")
	public void unifiedTestOMathParaSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestOMathPara(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestOMathParaSourceDataProvider")
	Object[][] unifiedTestOMathParaSourceDataProvider() throws Exception
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
    /// Test m:acc is roundtripped.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestAccent(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setConvertShapeToOfficeMath(true);

        Document doc = checkAndLoadDocument("Model\\Math\\TestAccent", lf, sf, lo);
        OfficeMath node = getOfficeMathTestNode(doc);

        msAssert.areEqual(MathObjectType.ACCENT, node.getMathObject().getMathObjectType());
        msAssert.areEqual('\u0308', ((MathObjectAccent)node.getMathObject()).getCharacter());
        validateMathArgumentContent(node, 0, "simple accent");
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAccentSourceDataProvider")
	public void unifiedTestAccentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAccent(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAccentSourceDataProvider")
	Object[][] unifiedTestAccentSourceDataProvider() throws Exception
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
    /// Test that m:eqArr is roundtripped.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestArray(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setConvertShapeToOfficeMath(true);

        Document doc = checkAndLoadDocument("Model\\Math\\TestArray", lf, sf, lo);
        OfficeMath node = getOfficeMathTestNode(doc);

        msAssert.areEqual(MathObjectType.ARRAY, node.getMathObject().getMathObjectType());
        MathObjectArray arr = (MathObjectArray)node.getMathObject();
        msAssert.areEqual(123, arr.getRowSpacing());
        msAssert.areEqual(MathSpacingRule.EXACTLY, arr.getRowSpacingRule());
        msAssert.areEqual(MathBaseJustification.BOTTOM, arr.getBaseJustification());
        msAssert.isTrue(arr.isMaximumDistribution());
        msAssert.isTrue(arr.isObjectDistribution());

        // test that children are read.
        msAssert.areEqual(2, node.getChildNodes(NodeType.ANY, false).getCount());
        validateMathArgumentContent(node, 0, "el1");
        validateMathArgumentContent(node, 1, "el2");
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestArraySourceDataProvider")
	public void unifiedTestArraySource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestArray(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestArraySourceDataProvider")
	Object[][] unifiedTestArraySourceDataProvider() throws Exception
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
    /// Test m:bar is roundtripped.
    /// WORDSNET-6085 Bar elements was read improperly due to the mistake in the code.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestBar(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setConvertShapeToOfficeMath(true);

        Document doc = checkAndLoadDocument("Model\\Math\\TestBar", lf, sf, lo);
        OfficeMath node = getOfficeMathTestNode(doc);

        msAssert.areEqual(MathObjectType.BAR, node.getMathObject().getMathObjectType());
        validateMathArgumentContent(node, 0, "overbar");
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBarSourceDataProvider")
	public void unifiedTestBarSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBar(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBarSourceDataProvider")
	Object[][] unifiedTestBarSourceDataProvider() throws Exception
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
    /// Test sub/superscript object including:
    /// m:sPre, m:sSup, m:sSub, m:sSubSup
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestSubSupAssorti(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setConvertShapeToOfficeMath(true);

        Document doc = checkAndLoadDocument("Model\\Math\\TestSubSupAssorti", lf, sf, lo);
        // test m:sSup
        OfficeMath node = getOfficeMathTestNode(doc);
        msAssert.areEqual(MathObjectType.SUPERSCRIPT, node.getMathObject().getMathObjectType());
        msAssert.areEqual(2, node.getChildNodes(NodeType.ANY, false).getCount());
        validateMathArgumentContent(node, 0, "sup_text");
        validateMathArgumentContent(node, 1, "superscript", MathObjectType.SUPERSCRIPT_PART);

        // test m:sSub
        node = getOfficeMathTestNode(doc, 1);
        msAssert.areEqual(MathObjectType.SUBSCRIPT, node.getMathObject().getMathObjectType());
        msAssert.areEqual(2, node.getChildNodes(NodeType.ANY, false).getCount());
        validateMathArgumentContent(node, 0, "sub_text");
        validateMathArgumentContent(node, 1, "subscript", MathObjectType.SUBSCRIPT_PART);

        // test m:sSubSup
        node = getOfficeMathTestNode(doc, 2);
        msAssert.areEqual(MathObjectType.SUB_SUPERSCRIPT, node.getMathObject().getMathObjectType());
        msAssert.areEqual(3, node.getChildNodes(NodeType.ANY, false).getCount());
        validateMathArgumentContent(node, 0, "sub_sup_text");
        validateMathArgumentContent(node, 2, "superscript", MathObjectType.SUPERSCRIPT_PART);
        validateMathArgumentContent(node, 1, "subscript", MathObjectType.SUBSCRIPT_PART);

        // test m:sSubSup
        node = getOfficeMathTestNode(doc, 3);
        msAssert.areEqual(MathObjectType.PRE_SUB_SUPERSCRIPT, node.getMathObject().getMathObjectType());
        msAssert.areEqual(3, node.getChildNodes(NodeType.ANY, false).getCount());
        validateMathArgumentContent(node, 0, "subscript", MathObjectType.SUBSCRIPT_PART);
        validateMathArgumentContent(node, 1, "superscript", MathObjectType.SUPERSCRIPT_PART);
        validateMathArgumentContent(node, 2, "pre_text");
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestSubSupAssortiSourceDataProvider")
	public void unifiedTestSubSupAssortiSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestSubSupAssorti(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestSubSupAssortiSourceDataProvider")
	Object[][] unifiedTestSubSupAssortiSourceDataProvider() throws Exception
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
    /// Test m:borderBox
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestBorderBox(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setConvertShapeToOfficeMath(true);

        Document doc = checkAndLoadDocument("Model\\Math\\TestBorderBox", lf, sf, lo);
        OfficeMath node = getOfficeMathTestNode(doc);

        msAssert.areEqual(MathObjectType.BORDER_BOX, node.getMathObject().getMathObjectType());
        MathObjectBorderBox box = (MathObjectBorderBox)node.getMathObject();
        msAssert.isTrue(box.getHideTopEdge());
        msAssert.isTrue(box.getHideRightEdge());
        msAssert.isFalse(box.getHideBottomEdge());
        msAssert.isFalse(box.getHideLeftEdge());

        msAssert.isTrue(box.getStrikeBLTR());
        msAssert.isFalse(box.getStrikeH());
        msAssert.isFalse(box.getStrikeV());
        msAssert.isFalse(box.getStrikeTLBR());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBorderBoxSourceDataProvider")
	public void unifiedTestBorderBoxSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBorderBox(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBorderBoxSourceDataProvider")
	Object[][] unifiedTestBorderBoxSourceDataProvider() throws Exception
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
    /// Test m:d
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDelimiter(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setConvertShapeToOfficeMath(true);

        Document doc = checkAndLoadDocument("Model\\Math\\TestDelimiter", lf, sf, lo);
        OfficeMath node = getOfficeMathTestNode(doc);

        msAssert.areEqual(MathObjectType.DELIMITER, node.getMathObject().getMathObjectType());
        MathObjectDelimiter d = (MathObjectDelimiter)node.getMathObject();
        msAssert.areEqual(MathDelimiterShape.MATCH, d.getDelimiterShape());
        msAssert.areEqual('*', d.getSeparatorCharacter());

        if((lf != LoadFormat.DOC) && (lf!= LoadFormat.WORD_ML))
            msAssert.isTrue(d.getGrowToMatchOperand());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDelimiterSourceDataProvider")
	public void unifiedTestDelimiterSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDelimiter(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDelimiterSourceDataProvider")
	Object[][] unifiedTestDelimiterSourceDataProvider() throws Exception
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
    /// Test m:f
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestFraction(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setConvertShapeToOfficeMath(true);

        Document doc = checkAndLoadDocument("Model\\Math\\TestFraction", lf, sf, lo);
        OfficeMath node = getOfficeMathTestNode(doc);

        msAssert.areEqual(MathObjectType.FRACTION, node.getMathObject().getMathObjectType());
        MathObjectFraction f = (MathObjectFraction)node.getMathObject();
        msAssert.areEqual(MathFractionType.SKEWED, f.getFractionType());

        msAssert.areEqual(2, node.getChildNodes(NodeType.ANY, false).getCount());
        validateMathArgumentContent(node, 0, "A", MathObjectType.NUMERATOR);
        validateMathArgumentContent(node, 1, "B", MathObjectType.DENOMINATOR);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFractionSourceDataProvider")
	public void unifiedTestFractionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFraction(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFractionSourceDataProvider")
	Object[][] unifiedTestFractionSourceDataProvider() throws Exception
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
    /// Test "m:limLow" object
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestLimLow(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setConvertShapeToOfficeMath(true);

        Document doc = checkAndLoadDocument("Model\\Math\\TestLimLow", lf, sf, lo);
        OfficeMath node = getOfficeMathTestNode(doc);

        msAssert.areEqual(MathObjectType.FUNCTION, node.getMathObject().getMathObjectType());

        OfficeMath fName = (OfficeMath)node.getChildNodes(NodeType.ANY, false).get(0);
        msAssert.areEqual(MathObjectType.FUNCTION_NAME, fName.getMathObject().getMathObjectType());

        msAssert.areEqual(1, fName.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual(MathObjectType.LOWER_LIMIT, ((OfficeMath)fName.getChildNodes(NodeType.ANY, false).get(0)).getMathObject().getMathObjectType());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLimLowSourceDataProvider")
	public void unifiedTestLimLowSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLimLow(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLimLowSourceDataProvider")
	Object[][] unifiedTestLimLowSourceDataProvider() throws Exception
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
    /// Test "m:limUpp" object
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestLimUpp(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setConvertShapeToOfficeMath(true);

        Document doc = checkAndLoadDocument("Model\\Math\\TestLimUpp", lf, sf, lo);
        OfficeMath node = getOfficeMathTestNode(doc);
        msAssert.areEqual(MathObjectType.UPPER_LIMIT, node.getMathObject().getMathObjectType());

        msAssert.areEqual(2, node.getChildNodes(NodeType.ANY, false).getCount());

        OfficeMath groupObj = (OfficeMath)node.getChildNodes(NodeType.ANY, false).get(0);
        msAssert.areEqual(MathObjectType.ARGUMENT, groupObj.getMathObject().getMathObjectType());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLimUppSourceDataProvider")
	public void unifiedTestLimUppSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLimUpp(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLimUppSourceDataProvider")
	Object[][] unifiedTestLimUppSourceDataProvider() throws Exception
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
    /// Test "m:phant" object
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestPhantom(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        if ((lf != LoadFormat.DOCX) && (lf != LoadFormat.RTF))
            return;

        final String TEST_NAME = "Model\\Math\\TestPhantom";

        LoadOptions lo = new LoadOptions();
        lo.setConvertShapeToOfficeMath(true);

        Document doc = TestUtil.open(TEST_NAME, lf, lo);
        OfficeMath node = getOfficeMathTestNode(doc);

        msAssert.areEqual(MathObjectType.PHANTOM, node.getMathObject().getMathObjectType());
        msAssert.areEqual(1, node.getChildNodes(NodeType.ANY, false).getCount());
        validateMathArgumentContent(node, 0, "phantom content");

        MathObjectPhantom p = (MathObjectPhantom)node.getMathObject();
        msAssert.isTrue(p.isZeroAscent());
        msAssert.isTrue(p.isZeroDescent());
        msAssert.isTrue(p.isZeroWidth());
        TestUtil.saveOpen(doc, TEST_NAME, TestUtil.getUnifiedScenario(lf, sf), lo);

        p.isShown(false);
        p.isTransparent(true);
        TestUtil.saveOpen(doc, TEST_NAME.replace("Phantom", "PhantomInvisible"), TestUtil.getUnifiedScenario(lf, sf), lo);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestPhantomSourceDataProvider")
	public void unifiedTestPhantomSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestPhantom(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestPhantomSourceDataProvider")
	Object[][] unifiedTestPhantomSourceDataProvider() throws Exception
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
    /// Test m:m (matrix). Check the follwing:
    /// - Verify rows and row elements are read;
    /// - Verify embedded matrices are read;
    /// - Verify properties are read;
    /// - Verify that validator fixes with non-valid properties of matrix columnPrCollection when loading this into the model.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestMatrixValidateColumnPr(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setConvertShapeToOfficeMath(true);

        Document doc = checkAndLoadDocument("Model\\Math\\TestMatrixValidateColumnPr", lf, sf, lo);
        OfficeMath node = getOfficeMathTestNode(doc);

        msAssert.areEqual(MathObjectType.MATRIX, node.getMathObject().getMathObjectType());

        MathObjectMatrix matrix = (MathObjectMatrix)node.getMathObject();
        msAssert.areEqual(2, matrix.getColumnPrCollection().getCount());
        msAssert.areEqual(HorizontalAlignment.CENTER, matrix.getColumnPrCollection().get(0).getHorizontalAlignment());
        msAssert.areEqual(HorizontalAlignment.CENTER, matrix.getColumnPrCollection().get(1).getHorizontalAlignment());

        msAssert.areEqual(2, node.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual(NodeType.OFFICE_MATH, node.getChildNodes(NodeType.ANY, false).get(0).getNodeType());
        msAssert.areEqual(NodeType.OFFICE_MATH, node.getChildNodes(NodeType.ANY, false).get(1).getNodeType());

        OfficeMath innerNode = (OfficeMath)((OfficeMath)node.getChildNodes(NodeType.ANY, false).get(1)).getChildNodes(NodeType.ANY, false).get(1);
        msAssert.areEqual(MathObjectType.ARGUMENT, innerNode.getMathObject().getMathObjectType());
        MathObjectMatrix embeddedMatrix = (MathObjectMatrix)((OfficeMath)innerNode.getChildNodes(NodeType.ANY, false).get(0)).getMathObject();

        msAssert.areEqual(MathBaseJustification.BOTTOM, embeddedMatrix.getBaseJustification());

        msAssert.areEqual(MathSpacingRule.EXACTLY, embeddedMatrix.getRowSpacingRule());
        msAssert.areEqual(360, embeddedMatrix.getRowSpacing());

        msAssert.areEqual(MathSpacingRule.MULTIPLE, embeddedMatrix.getColumnSpacingRule());
        msAssert.areEqual(240, embeddedMatrix.getMinimumColumnWidth());

        msAssert.areEqual(1, embeddedMatrix.getColumnPrCollection().getCount());
        msAssert.areEqual(HorizontalAlignment.RIGHT, embeddedMatrix.getColumnPrCollection().get(0).getHorizontalAlignment());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestMatrixValidateColumnPrSourceDataProvider")
	public void unifiedTestMatrixValidateColumnPrSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestMatrixValidateColumnPr(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestMatrixValidateColumnPrSourceDataProvider")
	Object[][] unifiedTestMatrixValidateColumnPrSourceDataProvider() throws Exception
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
    /// Test m:m (matrix). Check the if some arguments in a row are missing, then they are inserted by validator as empty arguments.
    /// That is what MS Word does upon resaving of bad document.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestMatrixCellMissing(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setConvertShapeToOfficeMath(true);

        Document doc = checkAndLoadDocument("Model\\Math\\TestMatrixCellMissing", lf, sf, lo);
        OfficeMath node = getOfficeMathTestNode(doc);

        msAssert.areEqual(MathObjectType.MATRIX, node.getMathObject().getMathObjectType());

        msAssert.areEqual(2, node.getChildNodes(NodeType.ANY, false).getCount());
        OfficeMath firstRow = ((OfficeMath)node.getChildNodes(NodeType.ANY, false).get(0));
        msAssert.areEqual(2, firstRow.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual(0, ((OfficeMath)firstRow.getChildNodes(NodeType.ANY, false).get(0)).getChildNodes(NodeType.ANY, false).getCount());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestMatrixCellMissingSourceDataProvider")
	public void unifiedTestMatrixCellMissingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestMatrixCellMissing(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestMatrixCellMissingSourceDataProvider")
	Object[][] unifiedTestMatrixCellMissingSourceDataProvider() throws Exception
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
    /// Test m:nary
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestNAry(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setConvertShapeToOfficeMath(true);

        Document doc = checkAndLoadDocument("Model\\Math\\TestNary", lf, sf, lo);
        OfficeMath node = getOfficeMathTestNode(doc);

        msAssert.areEqual(MathObjectType.N_ARY, node.getMathObject().getMathObjectType());
        MathObjectNAry nAry = (MathObjectNAry)node.getMathObject();
        msAssert.areEqual(MathLimitLocation.UNDER_OVER, nAry.getLimitLocation());
        msAssert.isTrue(nAry.isHideSubscript());
        msAssert.isTrue(nAry.isHideSuperscript());
        msAssert.isTrue(nAry.getGrowToMatchOperand());
        msAssert.areEqual('\u222D', nAry.getCharacter());

        // test that children are read.
        msAssert.areEqual(3, node.getChildNodes(NodeType.ANY, false).getCount());
        validateMathArgumentContent(node, 0, "lower_lim", MathObjectType.SUBSCRIPT_PART);
        validateMathArgumentContent(node, 1, "upper_lim", MathObjectType.SUPERSCRIPT_PART);
        validateMathArgumentContent(node, 2, "main_text");
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestNArySourceDataProvider")
	public void unifiedTestNArySource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestNAry(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestNArySourceDataProvider")
	Object[][] unifiedTestNArySourceDataProvider() throws Exception
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
    /// Test m:rad, test that inserted/deleted runs are read correctly.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestRadical(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setConvertShapeToOfficeMath(true);

        Document doc = checkAndLoadDocument("Model\\Math\\TestRadical", lf, sf, lo);
        OfficeMath node = getOfficeMathTestNode(doc);

        msAssert.areEqual(MathObjectType.RADICAL, node.getMathObject().getMathObjectType());
        msAssert.areEqual(2, node.getChildNodes(NodeType.ANY, false).getCount());
        OfficeMath degree = validateMathArgumentContent(node, 0, "degree_textnew", MathObjectType.DEGREE);

        msAssert.areEqual("new", degree.getChildNodes(NodeType.ANY, false).get(1).getText());
        msAssert.isTrue(((Run)degree.getChildNodes(NodeType.ANY, false).get(1)).isInsertRevision());

        OfficeMath radical = validateMathArgumentContent(node, 1, "radical_text");
        msAssert.areEqual("ical_tex", radical.getChildNodes(NodeType.ANY, false).get(1).getText());
        msAssert.isTrue(((Run)radical.getChildNodes(NodeType.ANY, false).get(1)).isDeleteRevision());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRadicalSourceDataProvider")
	public void unifiedTestRadicalSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRadical(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRadicalSourceDataProvider")
	Object[][] unifiedTestRadicalSourceDataProvider() throws Exception
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
    /// Verify that rPr of a math node are cloned properly.
    /// </summary>
    @Test
    public void testCloneRunPr() throws Exception
    {
        Document blank = new Document();
        RunPr testRunPr = new RunPr();
        testRunPr.setAllCaps(AttrBoolEx.TRUE);
        testRunPr.setMathLineBreak(new MathLineBreak());
        testRunPr.getMathLineBreak().setAlignment(25);

        OfficeMath m = new OfficeMath(blank, new MathObjectNAry(), testRunPr);

        OfficeMath copy = (OfficeMath)m.deepClone(true);
        RunPr copyRunPr = copy.getRunPr();
        msAssert.areEqual(AttrBoolEx.TRUE, copyRunPr.getAllCaps());
        msAssert.isNotNull(copyRunPr.getMathLineBreak());
        msAssert.areEqual(25, copyRunPr.getMathLineBreak().getAlignment());

        copyRunPr.setAllCaps(AttrBoolEx.FALSE);
        msAssert.areEqual(AttrBoolEx.TRUE, testRunPr.getAllCaps());
        copyRunPr.getMathLineBreak().setAlignment(3);
        msAssert.areEqual(25, testRunPr.getMathLineBreak().getAlignment());
    }

    /// <summary>
    /// Verify that right ancestors can be inserted at the right parent nodes.
    /// </summary>
    @Test
    public void testParentChildConsistency() throws Exception
    {
        Document blank = new Document();
        OfficeMath arg = new OfficeMath(blank, new MathObjectArgumentBase(MathObjectType.ARGUMENT));
        OfficeMath func = new OfficeMath(blank, new MathObjectFunction());
        OfficeMath fName = new OfficeMath(blank, new MathObjectArgumentBase(MathObjectType.FUNCTION_NAME));
        OfficeMath matrix = new OfficeMath(blank, new MathObjectMatrix());
        OfficeMath row = new OfficeMath(blank, new MathObjectMatrixRow());
        OfficeMath arr = new OfficeMath(blank, new MathObjectArray());
        OfficeMath officeMath= new OfficeMath(blank, new MathObjectOMath());

        msAssert.isTrue(func.canInsert(arg));
        msAssert.isFalse(fName.canInsert(arg));
        msAssert.isTrue(arg.canInsert(func));
        msAssert.isTrue(func.canInsert(fName));
        msAssert.isFalse(matrix.canInsert(arr));
        msAssert.isFalse(row.canInsert(arr));
        msAssert.isTrue(row.canInsert(arg));
        msAssert.isTrue(arr.canInsert(arg));
        msAssert.isTrue(officeMath.canInsert(func));
        msAssert.isFalse(officeMath.canInsert(arg));
        msAssert.isFalse(row.canInsert(officeMath));

        msAssert.isTrue(officeMath.canInsert(new CommentRangeStart(blank, 0)));
        msAssert.isTrue(arg.canInsert(new BookmarkStart(blank)));
        msAssert.isTrue(fName.canInsert(new Run(blank, "test")));
        msAssert.isFalse(arr.canInsert(new Shape(blank)));
    }

    // FOSS TestJira11897 removed: it round-trips the math EquationXML through the removed WordML
    // format (its WML input case loads a removed format), and the docx case relies on the removed
    // shape->OfficeMath conversion which yields no MathObject in the FOSS build.

    // FOSS TestJira16909 removed: OfficeMath equation-XML encoding from a WordML (.xml) input; WordML load removed.

    /// <summary>
    /// WORDSNET-17185 <see cref="ArgumentOutOfRangeException"/> occurred when we tried to read math a matrix
    /// without columns properties (or with too few column properties). Now we restore missing column properties upon
    /// loading a document.
    /// </summary>
    @Test
    public void testJira17185() throws Exception
    {
        Document doc = TestUtil.open("Model\\Math\\TestJira17185.docx");

        // Find the matrix node.
        CompositeNode node = doc.getFirstSection().getBody().getFirstParagraph();
        while ((node.getNodeType() != NodeType.OFFICE_MATH) || (((OfficeMath)node).getMathObjectType() != MathObjectType.MATRIX))
        {
            node = ms.as(node.getFirstChild(), CompositeNode.class);
        }

        MathObjectMatrix matrix = (MathObjectMatrix)(((OfficeMath)node).getMathObject());
        msAssert.areEqual(3, matrix.getColumnPrCollection().getCount());
        // FOSS Dropped the HTML save smoke-check (removed format); the matrix-column restore above is the point.
    }

    /// <summary>
    /// Verify that inserted/deleted Office Math subtrees are accepted well.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestAcceptRevisions(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        if ((lf != LoadFormat.DOCX) && (lf != LoadFormat.RTF))
            return;

        final String TEST_NAME = "Model\\Math\\TestAcceptRevisionsMath";
        Document doc = TestUtil.open(TEST_NAME, lf);
        doc.acceptAllRevisions();
        TestUtil.saveOpen(doc, TEST_NAME, lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAcceptRevisionsSourceDataProvider")
	public void unifiedTestAcceptRevisionsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAcceptRevisions(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAcceptRevisionsSourceDataProvider")
	Object[][] unifiedTestAcceptRevisionsSourceDataProvider() throws Exception
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
    /// Test that CtrlPr child of mathPr containing "w:del", "w:ins" and "w:rPr" is read correctly.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestControlPrResiliency(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        final String TEST_NAME = "Model\\Math\\TestControlPrResiliency";

        // todo 6220 add new task for fix this problem.
        if ((lf != LoadFormat.DOCX) && (lf != LoadFormat.RTF))
            return;

        Document doc = TestUtil.open(TEST_NAME, lf);
        OfficeMath node = getOfficeMathTestNode(doc);
        msAssert.isTrue(node.getRunPr().hasDeleteRevision());
        msAssert.isTrue(node.getRunPr().hasInsertRevision());

        msAssert.areEqual("Vasya", node.getRunPr().getInsertRevision().getAuthor());
        msAssert.areEqual("Denis", node.getRunPr().getDeleteRevision().getAuthor());
        msAssert.areEqual(AttrBoolEx.TRUE, node.getRunPr().getBold());
        msAssert.areEqual(DrColor.SILVER, node.getRunPr().getHighlightColor());
        msAssert.areEqual("Times New Roman", node.getRunPr().getNameAscii());
        msAssert.areEqual("Times New Roman", node.getRunPr().getNameOther());
        if (!((lf == LoadFormat.DOC) && (sf == SaveFormat.WORD_ML)))
            TestUtil.saveOpen(doc, TEST_NAME, lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestControlPrResiliencySourceDataProvider")
	public void unifiedTestControlPrResiliencySource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestControlPrResiliency(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestControlPrResiliencySourceDataProvider")
	Object[][] unifiedTestControlPrResiliencySourceDataProvider() throws Exception
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
    /// WORDSNET-14602 Justification of OfficeMath object.
    /// New <see cref="OfficeMathJustification.Inline"/> type was added.
    /// <see cref="OfficeMath.Justification"/> and <see cref="OfficeMath.DisplayType"/> were exposed to public.
    /// Tests get/set <see cref="OfficeMath.DisplayType"/> and <see cref="OfficeMath.Justification"/>.
    /// </summary>
    @Test
    public void testOMathDisplayToInline() throws Exception
    {
        Document doc = TestUtil.open("Model\\Math\\TestOMathDisplayType.docx");

        NodeCollection officeMaths = doc.getChildNodes(NodeType.OFFICE_MATH, true);

        OfficeMath officeMath = (OfficeMath)officeMaths.get(0);
        msAssert.isTrue(officeMath.isTopLevel());
        msAssert.areEqual(OfficeMathDisplayType.DISPLAY, officeMath.getDisplayType());
        msAssert.areEqual(OfficeMathJustification.CENTER_GROUP, officeMath.getJustification());

        // Change to Inline.
        officeMath.setDisplayType(OfficeMathDisplayType.INLINE);
        msAssert.areEqual(OfficeMathDisplayType.INLINE, officeMath.getDisplayType());
        msAssert.areEqual(OfficeMathJustification.INLINE, officeMath.getJustification());

        doc = TestUtil.saveOpen(doc, "Model\\Math\\TestOMathDisplayToInline.docx", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        officeMaths = doc.getChildNodes(NodeType.OFFICE_MATH, true);

        // Check after save.
        officeMath = (OfficeMath)officeMaths.get(0);
        msAssert.areEqual(OfficeMathDisplayType.INLINE, officeMath.getDisplayType());
        msAssert.areEqual(OfficeMathJustification.INLINE, officeMath.getJustification());
    }

    /// <summary>
    /// Part of WORDSNET-14602
    /// All child Office Math have Inline DisplayType and Inline Justification.
    /// </summary>
    @Test
    public void testDisplayTypeOfNestedOMath() throws Exception
    {
        Document doc = TestUtil.open("Model\\Math\\TestOMathDisplayType.docx");

        NodeCollection officeMaths = doc.getChildNodes(NodeType.OFFICE_MATH, true);

        OfficeMath officeMath = (OfficeMath)officeMaths.get(1);
        msAssert.isFalse(officeMath.isTopLevel());
        msAssert.areEqual(OfficeMathDisplayType.INLINE, officeMath.getDisplayType());
        msAssert.areEqual(OfficeMathJustification.INLINE, officeMath.getJustification());
    }

    /// <summary>
    /// Part of WORDSNET-14602
    /// Tests get/set oMath Justification.
    /// </summary>
    @Test
    public void testSetOMathJustification() throws Exception
    {
        Document doc = TestUtil.open("Model\\Math\\TestOMathDisplayType.docx");

        NodeCollection officeMaths = doc.getChildNodes(NodeType.OFFICE_MATH, true);

        OfficeMath officeMath = (OfficeMath)officeMaths.get(5);
        msAssert.areEqual(OfficeMathDisplayType.DISPLAY, officeMath.getDisplayType());
        msAssert.areEqual(OfficeMathJustification.LEFT, officeMath.getJustification());

        officeMath = (OfficeMath)officeMaths.get(10);
        msAssert.areEqual(OfficeMathJustification.RIGHT, officeMath.getJustification());

        officeMath = (OfficeMath)officeMaths.get(15);
        msAssert.areEqual(OfficeMathJustification.CENTER, officeMath.getJustification());

        // Change Justification.
        officeMath.setJustification(OfficeMathJustification.RIGHT);
        msAssert.areEqual(OfficeMathJustification.RIGHT, officeMath.getJustification());

        doc = TestUtil.saveOpen(doc, "Model\\Math\\TestSetOMathJustification.docx", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        officeMaths = doc.getChildNodes(NodeType.OFFICE_MATH, true);

        // Check after save.
        officeMath = (OfficeMath)officeMaths.get(15);
        msAssert.areEqual(OfficeMathDisplayType.DISPLAY, officeMath.getDisplayType());
        msAssert.areEqual(OfficeMathJustification.RIGHT, officeMath.getJustification());
    }

    /// <summary>
    /// Part of WORDSNET-14602
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QDisplayType cannot be changed for the nested Office Math. Please, check the parent node type to make sure it is top level Office Math.\\E")
    public void testSetDisplayTypeForNestedOMath() throws Exception
    {
        Document doc = TestUtil.open("Model\\Math\\TestOMathDisplayType.docx");
        OfficeMath officeMath = (OfficeMath)doc.getChild(NodeType.OFFICE_MATH, 2, true);

        msAssert.isFalse(officeMath.isTopLevel());

        // Returned display format type is always Inline for nested Office Math.
        msAssert.areEqual(OfficeMathDisplayType.INLINE, officeMath.getDisplayType());
        msAssert.areEqual(OfficeMathJustification.INLINE, officeMath.getJustification());

        // Set Inline DisplayType.
        officeMath.setDisplayType(OfficeMathDisplayType.INLINE);
        msAssert.areEqual(OfficeMathDisplayType.INLINE, officeMath.getDisplayType());

        // Try to set DisplayType for the nested Office Math.
        officeMath.setDisplayType(OfficeMathDisplayType.DISPLAY);
    }

    /// <summary>
    /// Part of WORDSNET-14602
    /// Test how the OMath siblings were proceed upon changing the <see cref="OfficeMath.DisplayType"/> to Inline.
    /// </summary>
    @Test
    public void testOMathInlineSiblings() throws Exception
    {
        Document doc = TestUtil.open("Model\\Math\\TestOMathInlineSiblings.docx");

        NodeCollection officeMaths = doc.getChildNodes(NodeType.OFFICE_MATH, true);

        for (OfficeMath officeMath : (Iterable<OfficeMath>) officeMaths)
        {
            // Check office math is top level (IsTopLevel property is internal)
            if (officeMath.getParentNode().getNodeType() != NodeType.OFFICE_MATH)
            {
                msAssert.areEqual(OfficeMathDisplayType.DISPLAY, officeMath.getDisplayType());
                officeMath.setDisplayType(OfficeMathDisplayType.INLINE);
            }
        }

        // Gold comparing in this case is more suitable, because the are lot of test cases.
        TestUtil.saveOpen(doc, "Model\\Math\\TestOMathInlineSiblings", UnifiedScenario.DOCX_2_DOCX);
    }

    /// <summary>
    /// Part of WORDSNET-14602
    /// Test how the OMath siblings were proceed upon changing the <see cref="OfficeMath.DisplayType"/> to Display.
    /// </summary>
    @Test
    public void testOMathDisplaySiblings() throws Exception
    {
        Document doc = TestUtil.open("Model\\Math\\TestOMathDisplaySiblings.docx");
        NodeCollection officeMaths = doc.getChildNodes(NodeType.OFFICE_MATH, true);

        for (OfficeMath officeMath : (Iterable<OfficeMath>) officeMaths)
        {
            // Check office math is top level (IsTopLevel property is internal)
            if (officeMath.getParentNode().getNodeType() != NodeType.OFFICE_MATH)
            {
                msAssert.areEqual(OfficeMathDisplayType.INLINE, officeMath.getDisplayType());
                officeMath.setDisplayType(OfficeMathDisplayType.DISPLAY);
            }
        }

        // Gold comparing in this case is more suitable, because the are lot of test cases.
        TestUtil.saveOpen(doc, "Model\\Math\\TestOMathDisplaySiblings", UnifiedScenario.DOCX_2_DOCX);
    }

    /// <summary>
    /// Part of WORDSNET-14602
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QJustification cannot be set to the Office Math displayed inline with text. Please, use OfficeMath.DisplayType property to change OfficeMathDisplayType.\\E")
    public void testOMathJustificationA() throws Exception
    {
        Document doc = TestUtil.open("Model\\Math\\TestOMathDisplayType.docx");
        OfficeMath officeMath = (OfficeMath)doc.getChild(NodeType.OFFICE_MATH, 1, true);

        msAssert.areEqual(OfficeMathDisplayType.INLINE, officeMath.getDisplayType());
        msAssert.areEqual(OfficeMathJustification.INLINE, officeMath.getJustification());

        // Set inline justification.
        officeMath.setJustification(OfficeMathJustification.INLINE);
        msAssert.areEqual(OfficeMathJustification.INLINE, officeMath.getJustification());

        // Try to set any other justification for the office math with DisplayType equals Inline.
        officeMath.setJustification(OfficeMathJustification.LEFT);
    }

    /// <summary>
    /// Part of WORDSNET-14602
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QInline justification cannot be set to the Office Math displayed on its own line. Please, use OfficeMath.DisplayType property to change OfficeMathDisplayType.\\E")
    public void testOMathJustificationB() throws Exception
    {
        Document doc = TestUtil.open("Model\\Math\\TestOMathDisplayType.docx");
        OfficeMath officeMath = (OfficeMath)doc.getChild(NodeType.OFFICE_MATH, 0, true);

        msAssert.areEqual(OfficeMathDisplayType.DISPLAY, officeMath.getDisplayType());
        msAssert.areEqual(OfficeMathJustification.CENTER_GROUP, officeMath.getJustification());

        // Set left justification.
        officeMath.setJustification(OfficeMathJustification.LEFT);
        msAssert.areEqual(OfficeMathJustification.LEFT, officeMath.getJustification());

        // Try to set inline justification for the office math with DisplayType equals Display.
        officeMath.setJustification(OfficeMathJustification.INLINE);
    }




    /// <summary>
    /// WORDSNET-26171 Resetting italic does not work for runs inside office math.
    /// Translate italic attribute into MathStyle.
    /// </summary>
    @Test
    public void test26171() throws Exception
    {
        Document doc = TestUtil.open("Model\\Math\\Test26171.docx");
        Run run = (Run)doc.getChild(NodeType.RUN, 0, true);

        run.getFont().setItalic(false);
        msAssert.isNull(run.getRunPr().get(FontAttr.ITALIC));
        msAssert.areEqual(MathStyle.PLAIN, run.getRunPr().get(FontAttr.MATH_STYLE));

        run.getFont().setItalic(true);
        msAssert.isNull(run.getRunPr().get(FontAttr.ITALIC));
        msAssert.areEqual(MathStyle.ITALIC, run.getRunPr().get(FontAttr.MATH_STYLE));
    }

    /// <summary>
    /// Opens and saves document, also checks state of the shape with "EquationXml".
    /// </summary>
    private static void openSaveOpenDocumentWithEquationXmlShape(String pathPattern, /*LoadFormat*/int loadFromat, /*SaveFormat*/int saveFormat) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        msAssert.isFalse(lo.getConvertShapeToOfficeMath());
        String sourceExt = FileFormatUtil.loadFormatToExtension(loadFromat);

        // On loading expected, that math shape should be preserved (according to load settings).
        Document doc = TestUtil.open(msString.format(pathPattern, sourceExt), lo);
        msAssert.isTrue(doc.getFirstSection().getBody().getShapes().get(0).getShapePr().contains(ShapeAttr.EQUATION_XML));
        msAssert.isNull(doc.getFirstSection().getBody().getChild(NodeType.OFFICE_MATH, 0, true));

        String dstExt = FileFormatUtil.saveFormatToExtension(saveFormat);
        String dstFileName = msString.format(pathPattern, sourceExt.replace(".", "").toUpperCase());

        SaveOptions so = SaveOptions.createSaveOptions(saveFormat);
        so.setTestMode();

        TestWarningCallback wc = new TestWarningCallback();
        doc.setWarningCallback(wc);

        if (saveFormat == SaveFormat.DOCX)
            ((OoxmlSaveOptions)so).setComplianceCore(OoxmlComplianceCore.ISO_TRANSITIONAL);

        doc = TestUtil.saveOpen(doc, dstFileName, TestUtil.buildScenario(loadFromat, saveFormat, true), so, lo);

        switch (saveFormat)
        {
            case SaveFormat.RTF:
            case SaveFormat.DOC:
            case SaveFormat.WORD_ML:
            case SaveFormat.FLAT_OPC:
            {
                Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
                // Expected, that "EquationXml" will be lost after saving to RTF.
                msAssert.areEqual(saveFormat != SaveFormat.RTF, shape.getShapePr().contains(ShapeAttr.EQUATION_XML));
                msAssert.isNull(doc.getFirstSection().getBody().getChild(NodeType.OFFICE_MATH, 0, true));
                msAssert.isTrue(shape.hasImageBytes());
                break;
            }
            case SaveFormat.DOCX:
            {
                OfficeMath officeMath = (OfficeMath)doc.getFirstSection().getBody().getChild(NodeType.OFFICE_MATH, 0, true);
                msAssert.areEqual(OfficeMathJustification.CENTER_GROUP, officeMath.getJustification());
                msAssert.isNull(doc.getFirstSection().getBody().getChild(NodeType.SHAPE, 0, true));
                msAssert.isNotNull(officeMath);

                msAssert.isFalse(wc.contains(WarningSource.VALIDATOR,
                        WarningType.DATA_LOSS, WarningStrings.INVALID_OFFICE_MATH_XML));
                break;
            }
            default:
                throw new IllegalArgumentException("Specified output format is not supported.");
        }
    }

    /// <summary>
    /// Used for check the file with gold. Returns the loaded model from file.
    /// Word keeps different schemes to shape's prop(ShapeAttr.EquationXML) to Doc and WML formats.
    /// </summary>
    private static Document checkAndLoadDocument(String fileName, /*LoadFormat*/int lf, /*SaveFormat*/int sf, LoadOptions lo) throws Exception
    {
        Document document = null;
        if ((sf == SaveFormat.WORD_ML) && (lf == LoadFormat.DOC))
        {
            document = TestUtil.open(fileName, lf, lo);
            fileName = TestUtil.save(document, fileName+".wml");
            document = TestUtil.open(fileName, lo);
        }
        else
        {
            document = TestUtil.openSaveOpen(fileName, TestUtil.getUnifiedScenario(lf, sf), lo);
        }
        return document;
    }

    /// <summary>
    /// Retrieve i-th argument from a Office Math node.
    /// Assert that the argument is of <see cref="MathObjectType.Argument"/>
    /// </summary>
    private static OfficeMath validateMathArgumentContent(OfficeMath node, int index, String runText)
    {
        return validateMathArgumentContent(node, index, runText, MathObjectType.ARGUMENT);
    }

    /// <summary>
    /// Retrieve i-th argument of a Office Math node.
    /// Assert that the argument is of requested <see cref="MathObjectType"/>
    /// </summary>
    private static OfficeMath validateMathArgumentContent(OfficeMath node, int index, String runText, /*MathObjectType*/int assertType)
    {
        msAssert.areEqual(NodeType.OFFICE_MATH, node.getChildNodes(NodeType.ANY, false).get(index).getNodeType());
        OfficeMath result = (OfficeMath)node.getChildNodes(NodeType.ANY, false).get(index);
        msAssert.areEqual(assertType, result.getMathObject().getMathObjectType());

        if (!"".equals(runText)) // With non empty runText to check we consider argument child to be run.
        {
            msAssert.areEqual(NodeType.RUN, result.getChildNodes(NodeType.ANY, false).get(0).getNodeType());
            msAssert.areEqual(runText, result.getText());
        }
        return result;
    }

    private static OfficeMath getOfficeMathTestNode(Document doc)
    {
        return getOfficeMathTestNode(doc, 0);
    }

    /// <summary>
    /// Since large number of docs contains tested data nested as in para\oMathPara\oMath\testNode.
    /// This method returns only testNode from the i-th para in the doc.
    /// </summary>
    private static OfficeMath getOfficeMathTestNode(Document doc, int index)
    {
        Paragraph p = (Paragraph)(doc.getSections().get(0).getBody()).getChildNodes(NodeType.ANY, false).get(index);
        if (p.getFirstChild().getNodeType() == NodeType.OFFICE_MATH)
            return (OfficeMath)((OfficeMath)((OfficeMath)p.getFirstChild()).getFirstChild()).getFirstChild();
        else
            return null;
    }
}

