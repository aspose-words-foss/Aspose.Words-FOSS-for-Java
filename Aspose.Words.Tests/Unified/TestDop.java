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
/// Unified for the document properties (the DOP structure).
/// </summary>
@Test
public class TestDop extends UnifiedTestsBase
{
    @Test
    public void testNew() throws Exception
    {
        Document doc = new Document();
        DocPr pr = doc.getDocPr();

        // RK Can add other properties to test.

        msAssert.areEqual(false, pr.CompatibilityOptions.getUICompat97To2003());
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDefault(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Dop\\TestDefault", lf, sf);
        DocPr pr = doc.getDocPr();

        msAssert.areEqual(false, pr.PrintFormsData);
        msAssert.areEqual(false, pr.TrackRevisions);
        msAssert.areEqual(1440, pr.DefaultTabStop);
        msAssert.areEqual(true, pr.EvenAndOddHeaders);
        msAssert.areEqual(9, pr.ShowOutlineLevels);

        // RK These seem to be DOC properties only.
        if (sf == SaveFormat.DOC)
        {
            msAssert.areEqual(true, pr.ShowHidden);
            msAssert.areEqual(true, pr.ShowFieldResults);
        }

        msAssert.areEqual(ProtectionType.NO_PROTECTION, pr.DocumentProtection.getEdit());
        msAssert.areEqual(false, pr.DocumentProtection.getEnforcement());
        msAssert.areEqual(false, pr.DocumentProtection.getFormatting());
        msAssert.areEqual(false, pr.DocumentProtection.getSelectFormFieldsOnly());

        FootnoteOptions footnoteOptions = doc.getFootnoteOptions();
        msAssert.areEqual(FootnotePosition.BOTTOM_OF_PAGE, footnoteOptions.getPosition());
        msAssert.areEqual(NumberStyle.ARABIC, footnoteOptions.getNumberStyle());
        msAssert.areEqual(1, footnoteOptions.getStartNumber());
        msAssert.areEqual(FootnoteNumberingRule.CONTINUOUS, footnoteOptions.getRestartRule());

        EndnoteOptions endnoteOptions = doc.getEndnoteOptions();
        msAssert.areEqual(EndnotePosition.END_OF_DOCUMENT, endnoteOptions.getPosition());
        msAssert.areEqual(NumberStyle.LOWERCASE_ROMAN, endnoteOptions.getNumberStyle());
        msAssert.areEqual(1, endnoteOptions.getStartNumber());
        msAssert.areEqual(FootnoteNumberingRule.CONTINUOUS, endnoteOptions.getRestartRule());

        msAssert.areEqual(false, doc.getViewOptions().getDisplayBackgroundShape());
        msAssert.areEqual(false, doc.getViewOptions().getDoNotDisplayPageBoundaries());

        msAssert.areEqual(false, pr.StyleLockQuickFormatSet);
        msAssert.areEqual(false, pr.StyleLockTheme);
        msAssert.areEqual(0x3f01, pr.StylePaneFormatFilterSettings.getData());
        msAssert.areEqual(StylePaneSortMethod.DEFAULT, pr._StylePaneSortMethod);
        msAssert.areEqual(true, pr.CompatibilityOptions.getUICompat97To2003());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefaultSourceDataProvider")
	public void unifiedTestDefaultSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefault(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefaultSourceDataProvider")
	Object[][] unifiedTestDefaultSourceDataProvider() throws Exception
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
    void unifiedTestRevMarks(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Dop\\TestRevMarks", lf, sf);
        DocPr pr = doc.getDocPr();

        msAssert.areEqual(true, pr.TrackRevisions);
        msAssert.areEqual(true, pr.ShowMarkup);
        msAssert.areEqual(true, pr.ShowAnnotations);
        msAssert.areEqual(true, pr.ShowFormatting);
        msAssert.areEqual(true, pr.ShowInsertionsDeletions);
        msAssert.areEqual(true, pr.getShowRevisions());

        // This seems to be DOC only property.
        if (sf == SaveFormat.DOC)
            msAssert.areEqual(true, pr.PrintRevisions);

        doc.setTrackRevisions(false);

        doc = TestUtil.saveOpen(doc, "Model\\Dop\\TestRevMarks Off", lf, sf);
        msAssert.areEqual(false, doc.getTrackRevisions());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevMarksSourceDataProvider")
	public void unifiedTestRevMarksSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevMarks(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevMarksSourceDataProvider")
	Object[][] unifiedTestRevMarksSourceDataProvider() throws Exception
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
    void unifiedTestShowGrammarErrors(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // RTF format hasn't got 'HideGrammaticalErrors' option.
        if (sf != SaveFormat.RTF)
        {
            Document doc = TestUtil.openSaveOpen("Model\\Dop\\TestShowGrammarErrors", lf, sf);
            msAssert.isTrue(doc.getShowGrammaticalErrors());
            msAssert.isFalse(doc.getDocPr().HideGrammaticalErrors);

            doc.setShowGrammaticalErrors(false);
            doc = TestUtil.saveOpen(doc, "Model\\Dop\\TestShowGrammarErrors Off", lf, sf);
            msAssert.isFalse(doc.getShowGrammaticalErrors());
            msAssert.isTrue(doc.getDocPr().HideGrammaticalErrors);
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestShowGrammarErrorsSourceDataProvider")
	public void unifiedTestShowGrammarErrorsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestShowGrammarErrors(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestShowGrammarErrorsSourceDataProvider")
	Object[][] unifiedTestShowGrammarErrorsSourceDataProvider() throws Exception
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
    void unifiedTestShowSpellingErrors(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // RTF format hasn't got 'HideSpellingErrors' option.
        if (sf != SaveFormat.RTF)
        {
            Document doc = TestUtil.openSaveOpen("Model\\Dop\\TestShowSpellingErrors", lf, sf);
            msAssert.isTrue(doc.getShowSpellingErrors());
            msAssert.isFalse(doc.getDocPr().HideSpellingErrors);

            doc.setShowSpellingErrors(false);
            doc = TestUtil.saveOpen(doc, "Model\\Dop\\TestShowSpellingErrors Off", lf, sf);
            msAssert.isFalse(doc.getShowSpellingErrors());
            msAssert.isTrue(doc.getDocPr().HideSpellingErrors);
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestShowSpellingErrorsSourceDataProvider")
	public void unifiedTestShowSpellingErrorsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestShowSpellingErrors(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestShowSpellingErrorsSourceDataProvider")
	Object[][] unifiedTestShowSpellingErrorsSourceDataProvider() throws Exception
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
    void unifiedTestProtect(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Dop\\TestProtectRevisions", lf, sf);

        DocPr pr = doc.getDocPr();
        msAssert.areEqual(ProtectionType.ALLOW_ONLY_REVISIONS, pr.DocumentProtection.getEdit());
        msAssert.areEqual(true, pr.DocumentProtection.getEnforcement());

        doc = TestUtil.openSaveOpen("Model\\Dop\\TestProtectComments", lf, sf);
        pr = doc.getDocPr();
        msAssert.areEqual(ProtectionType.ALLOW_ONLY_COMMENTS, pr.DocumentProtection.getEdit());
        msAssert.areEqual(true, pr.DocumentProtection.getEnforcement());

        doc = TestUtil.openSaveOpen("Model\\Dop\\TestProtectFormFields", lf, sf);
        pr = doc.getDocPr();
        msAssert.areEqual(ProtectionType.ALLOW_ONLY_FORM_FIELDS, pr.DocumentProtection.getEdit());
        msAssert.areEqual(true, pr.DocumentProtection.getEnforcement());

        // Seems to be a DOC property only.
        if (sf == SaveFormat.DOC)
            msAssert.areEqual(true, pr.DocumentProtection.getSelectFormFieldsOnly());

        doc = TestUtil.openSaveOpen("Model\\Dop\\TestProtectReadOnly", lf, sf);
        pr = doc.getDocPr();
        msAssert.areEqual(ProtectionType.READ_ONLY, pr.DocumentProtection.getEdit());
        msAssert.areEqual(true, pr.DocumentProtection.getEnforcement());

        doc = TestUtil.openSaveOpen("Model\\Dop\\TestProtectionPassword", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestProtectSourceDataProvider")
	public void unifiedTestProtectSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestProtect(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestProtectSourceDataProvider")
	Object[][] unifiedTestProtectSourceDataProvider() throws Exception
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
    void unifiedTestZoom(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Dop\\TestZoom75", lf, sf);
        msAssert.areEqual(75, doc.getViewOptions().getZoomPercent());
        msAssert.areEqual(ZoomType.NONE, doc.getViewOptions().getZoomType());

        doc = TestUtil.openSaveOpen("Model\\Dop\\TestZoomPageWidth", lf, sf);
        msAssert.areEqual(ZoomType.PAGE_WIDTH, doc.getViewOptions().getZoomType());

        doc = TestUtil.openSaveOpen("Model\\Dop\\TestZoomTextWidth", lf, sf);
        msAssert.areEqual(ZoomType.TEXT_FIT, doc.getViewOptions().getZoomType());

        doc = TestUtil.openSaveOpen("Model\\Dop\\TestZoomWholePage", lf, sf);
        msAssert.areEqual(ZoomType.FULL_PAGE, doc.getViewOptions().getZoomType());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestZoomSourceDataProvider")
	public void unifiedTestZoomSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestZoom(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestZoomSourceDataProvider")
	Object[][] unifiedTestZoomSourceDataProvider() throws Exception
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
    void unifiedTestView(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Dop\\TestViewNormal", lf, sf);
        msAssert.areEqual(ViewType.NORMAL, doc.getViewOptions().getViewType());

        doc = TestUtil.openSaveOpen("Model\\Dop\\TestViewPageLayout", lf, sf);
        msAssert.areEqual(ViewType.PAGE_LAYOUT, doc.getViewOptions().getViewType());

        doc = TestUtil.openSaveOpen("Model\\Dop\\TestViewOutline", lf, sf);
        msAssert.areEqual(ViewType.OUTLINE, doc.getViewOptions().getViewType());

        doc = TestUtil.openSaveOpen("Model\\Dop\\TestViewWeb", lf, sf);
        msAssert.areEqual(ViewType.WEB, doc.getViewOptions().getViewType());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestViewSourceDataProvider")
	public void unifiedTestViewSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestView(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestViewSourceDataProvider")
	Object[][] unifiedTestViewSourceDataProvider() throws Exception
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
    void unifiedTestViewOptions(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Dop\\TestViewOptions", lf, sf);

        msAssert.areEqual(true, doc.getViewOptions().getDisplayBackgroundShape());
        msAssert.areEqual(true, doc.getViewOptions().getDoNotDisplayPageBoundaries());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestViewOptionsSourceDataProvider")
	public void unifiedTestViewOptionsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestViewOptions(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestViewOptionsSourceDataProvider")
	Object[][] unifiedTestViewOptionsSourceDataProvider() throws Exception
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
    void unifiedTestFootnoteEndnote(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Dop\\TestFootnoteEndnote", lf, sf);

        msAssert.areEqual(FootnotePosition.BENEATH_TEXT, doc.getFootnoteOptions().getPosition());
        msAssert.areEqual(NumberStyle.LOWERCASE_LETTER, doc.getFootnoteOptions().getNumberStyle());
        msAssert.areEqual(2, doc.getFootnoteOptions().getStartNumber());
        msAssert.areEqual(FootnoteNumberingRule.CONTINUOUS, doc.getFootnoteOptions().getRestartRule());

        msAssert.areEqual(EndnotePosition.END_OF_SECTION, doc.getEndnoteOptions().getPosition());
        msAssert.areEqual(NumberStyle.UPPERCASE_ROMAN, doc.getEndnoteOptions().getNumberStyle());
        msAssert.areEqual(1, doc.getEndnoteOptions().getStartNumber());
        msAssert.areEqual(FootnoteNumberingRule.RESTART_SECTION, doc.getEndnoteOptions().getRestartRule());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFootnoteEndnoteSourceDataProvider")
	public void unifiedTestFootnoteEndnoteSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFootnoteEndnote(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFootnoteEndnoteSourceDataProvider")
	Object[][] unifiedTestFootnoteEndnoteSourceDataProvider() throws Exception
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
    void unifiedTestCompatibilityOptions(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Dop\\TestCompatibilityOptions", lf, sf);
        doTestCompatibilityOptions(doc.getDocPr().CompatibilityOptions);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCompatibilityOptionsSourceDataProvider")
	public void unifiedTestCompatibilityOptionsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCompatibilityOptions(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCompatibilityOptionsSourceDataProvider")
	Object[][] unifiedTestCompatibilityOptionsSourceDataProvider() throws Exception
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

    private static void doTestCompatibilityOptions(CompatibilityOptions co)
    {
        msAssert.areEqual(false, co.getSpaceForUL());
        msAssert.areEqual(false, co.getAdjustLineHeightInTable());
        msAssert.areEqual(true, co.getAlignTablesRowByRow());
        msAssert.areEqual(true, co.getLayoutTableRowsApart());
        msAssert.areEqual(true, co.getGrowAutofit());
        msAssert.areEqual(false, co.getAutoSpaceLikeWord95());
        msAssert.areEqual(true, co.getBalanceSingleByteDoubleByteWidth());
        msAssert.areEqual(false, co.getUseSingleBorderforContiguousCells());
        msAssert.areEqual(false, co.getDoNotLeaveBackslashAlone());
        msAssert.areEqual(false, co.getWPJustification());
        msAssert.areEqual(false, co.getNoTabHangInd());
        msAssert.areEqual(false, co.getNoSpaceRaiseLower());
        msAssert.areEqual(false, co.getNoLeading());
        msAssert.areEqual(true, co.getDoNotWrapTextWithPunct());
        msAssert.areEqual(false, co.getNoColumnBalance());
        msAssert.areEqual(false, co.getTransparentMetafiles());
        msAssert.areEqual(false, co.getNoExtraLineSpacing());
        msAssert.areEqual(false, co.getDoNotExpandShiftReturn());
        msAssert.areEqual(true, co.getDoNotUseEastAsianBreakRules());
        msAssert.areEqual(true, co.getDoNotUseHTMLParagraphAutoSpacing());
        msAssert.areEqual(false, co.getUlTrailSpace());
        msAssert.areEqual(false, co.getSpacingInWholePoints());
        msAssert.areEqual(true, co.getForgetLastTabAlignment());
        msAssert.areEqual(true, co.getShapeLayoutLikeWW8());
        msAssert.areEqual(true, co.getFootnoteLayoutLikeWW8());
        msAssert.areEqual(true, co.getLayoutRawTableWidth());
        msAssert.areEqual(false, co.getLineWrapLikeWord6());
        msAssert.areEqual(false, co.getPrintBodyTextBeforeHeader());
        msAssert.areEqual(false, co.getPrintColBlack());
        msAssert.areEqual(true, co.getSelectFldWithFirstOrLastChar());
        msAssert.areEqual(false, co.getWPSpaceWidth());
        msAssert.areEqual(false, co.getShowBreaksInFrames());
        msAssert.areEqual(false, co.getSubFontBySize());
        msAssert.areEqual(false, co.getSuppressBottomSpacing());
        msAssert.areEqual(false, co.getSuppressTopSpacing());
        msAssert.areEqual(false, co.getSuppressSpacingAtTopOfPage());
        msAssert.areEqual(false, co.getSuppressSpBfAfterPgBrk());
        msAssert.areEqual(true, co.getSwapBordersFacingPgs());
        msAssert.areEqual(false, co.getConvMailMergeEsc());
        msAssert.areEqual(false, co.getTruncateFontHeightsLikeWP6());
        msAssert.areEqual(true, co.getMWSmallCaps());
        msAssert.areEqual(false, co.getApplyBreakingRules());
        msAssert.areEqual(false, co.getUsePrinterMetrics());
        msAssert.areEqual(true, co.getUseWord2002TableStyleRules());
        msAssert.areEqual(false, co.getDoNotSuppressParagraphBorders());
        msAssert.areEqual(true, co.getUseWord97LineBreakRules());
        msAssert.areEqual(false, co.getWrapTrailSpaces());
    }



    /// <summary>
    /// WORDSNET-9847 Track Formatting option is not preserved during open/save a document
    /// </summary>
    /// <remarks>
    /// AM. There is bad issue with binary DOC format. I found that Word ignores DOP2007 i.e DoNotTrackFormatting, DoNotTrackMoves,
    /// StylePaneSort if nFib value is less than Word2007. I adopted DocPrFiler to this logic.
    /// Currently we write Word2003 nFib value for our files exported in binary DOC format.
    ///
    /// Sometimes we need to switch to Word2007 but lets wait till customer complains.
    /// </remarks>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestJira9847(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        boolean isWordML = (lf == LoadFormat.WORD_ML) || (sf == SaveFormat.WORD_ML);
        boolean isDoc = (lf == LoadFormat.DOC) || (sf == SaveFormat.DOC);
        /*UnifiedScenario*/int scenario = TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD;

        if (isWordML)
        {
            // This option is missed in WordML.
            return;
        }

        // Test option is switched on.
        Document doc = TestUtil.open("Model\\Dop\\TestJira9847A", lf);
        msAssert.isTrue(doc.getDocPr().DoNotTrackFormatting);

        if (!isDoc)
        {
            doc = TestUtil.saveOpen(doc, "Model\\Dop\\TestJira9847A", scenario);
            msAssert.isTrue(doc.getDocPr().DoNotTrackFormatting);
        }

        // Test option is switched off.
        doc = TestUtil.open("Model\\Dop\\TestJira9847B", lf);
        msAssert.isFalse(doc.getDocPr().DoNotTrackFormatting);

        if (!isDoc)
        {
            doc = TestUtil.saveOpen(doc, "Model\\Dop\\TestJira9847B", scenario);
            msAssert.isFalse(doc.getDocPr().DoNotTrackFormatting);
        }

        // Test non default style pane sort method.
        doc = TestUtil.open("Model\\Dop\\TestJira9847C", lf);
        msAssert.areEqual(StylePaneSortMethod.BASED_ON, doc.getDocPr()._StylePaneSortMethod);

        if (!isDoc)
        {
            doc = TestUtil.saveOpen(doc, "Model\\Dop\\TestJira9847C", scenario);
            msAssert.areEqual(StylePaneSortMethod.BASED_ON, doc.getDocPr()._StylePaneSortMethod);
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJira9847SourceDataProvider")
	public void unifiedTestJira9847Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJira9847(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJira9847SourceDataProvider")
	Object[][] unifiedTestJira9847SourceDataProvider() throws Exception
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
    /// WORDSNET-10473 Implement load/save w:hideSpellingErrors into DOM for relevant formats.
    /// Here is the test for all formats, except the 'rtf', which is tested in TestUnifiedWarnings.TestJira10473().
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestJira10473(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        /*UnifiedScenario*/int scenario = TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD;

        Document doc = new Document();

        // By default MSW doesn't hide spelling errors.
        msAssert.isFalse(doc.getDocPr().HideSpellingErrors);

        // Checks disabled property, after resaving remained disabled.
        doc = TestUtil.saveOpen(doc, "Model\\Dop\\TestJira10473", scenario);
        msAssert.isFalse(doc.getDocPr().HideSpellingErrors);

        // Checks enabled property, after resaving remained enabled.
        if (sf != SaveFormat.RTF)
        {
            doc.getDocPr().HideSpellingErrors = true;
            doc = TestUtil.saveOpen(doc, "Model\\Dop\\TestJira10473", scenario);
            msAssert.isTrue(doc.getDocPr().HideSpellingErrors);
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJira10473SourceDataProvider")
	public void unifiedTestJira10473Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJira10473(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJira10473SourceDataProvider")
	Object[][] unifiedTestJira10473SourceDataProvider() throws Exception
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
    /// WORDSNET-10474 Implement load/save w:hideGrammaticalErrors into DOM for relevant formats.
    /// Here is the test for all formats, except the 'rtf', which is tested in TestUnifiedWarnings.TestJira10474().
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestJira10474(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        /*UnifiedScenario*/int scenario = TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD;

        Document doc = new Document();

        // By default MSW doesn't hide grammatical errors.
        msAssert.isFalse(doc.getDocPr().HideGrammaticalErrors);

        // Checks disabled property, after resaving remained disabled.
        doc = TestUtil.saveOpen(doc, "Model\\Dop\\TestJira10474", scenario);
        msAssert.isFalse(doc.getDocPr().HideGrammaticalErrors);

        // Checks enabled property, after resaving remained enabled.
        if (sf != SaveFormat.RTF)
        {
            doc.getDocPr().HideGrammaticalErrors = true;
            doc = TestUtil.saveOpen(doc, "Model\\Dop\\TestJira10474", scenario);
            msAssert.isTrue(doc.getDocPr().HideGrammaticalErrors);
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJira10474SourceDataProvider")
	public void unifiedTestJira10474Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJira10474(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJira10474SourceDataProvider")
	Object[][] unifiedTestJira10474SourceDataProvider() throws Exception
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
    /// Tests loading/saving <see cref="Document.HyphenationOptions"/> properties.
    /// </summary>
    // FOSS: only Docx survives — Doc/WordML/Rtf/Odt save were removed.
    @Test (dataProvider = "testHyphenationOptionsDataProvider")
    public void testHyphenationOptions(/*SaveFormat*/int saveFormat, boolean autoHyphenation,
        int consecutiveHyphenLimit, int hyphenationZone, boolean hyphenateCaps) throws Exception
    {
        Document doc = new Document();

        HyphenationOptions options = doc.getHyphenationOptions();
        options.setAutoHyphenation(autoHyphenation);
        options.setConsecutiveHyphenLimit(consecutiveHyphenLimit);
        options.setHyphenationZone(hyphenationZone);
        options.setHyphenateCaps(hyphenateCaps);

        Document outDoc = TestUtil.saveOpen(doc, "Model\\Dop\\TestHyphenationOptions",
            SaveOptions.createSaveOptions(saveFormat), false);

        options = outDoc.getHyphenationOptions();
        msAssert.areEqual(autoHyphenation, options.getAutoHyphenation(), "AutoHyphenation property value is wrong.");
        msAssert.areEqual(consecutiveHyphenLimit, options.getConsecutiveHyphenLimit(), "ConsecutiveHyphenLimit property value is wrong.");
        msAssert.areEqual(hyphenationZone, options.getHyphenationZone(), "HyphenationZone property value is wrong.");
        msAssert.areEqual(hyphenateCaps, options.getHyphenateCaps(), "HyphenateCaps property value is wrong.");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testHyphenationOptionsDataProvider")
	public static Object[][] testHyphenationOptionsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX,  true,  5,  720,  false},
		};
	}

    /// <summary>
    /// Tests that an exception is raised on setting wrong value to the
    /// <see cref="HyphenationOptions.ConsecutiveHyphenLimit"/> property.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testValidationOfConsecutiveHyphenLimit() throws Exception
    {
        Document doc = new Document();
        doc.getHyphenationOptions().setConsecutiveHyphenLimit(-1);
    }

    /// <summary>
    /// Tests that an exception is raised on setting wrong value to the
    /// <see cref="HyphenationOptions.HyphenationZone"/> property.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testValidationOfHyphenationZone() throws Exception
    {
        Document doc = new Document();
        doc.getHyphenationOptions().setHyphenationZone(0);
    }

    // FOSS: TestJira13764 removed — RTF load/save is gone and its third input is a customer .rtf.
    // FOSS: TestJira13596B removed — it reads DisplayBackgroundShape via binary-.doc nFib/FIB behavior.

     private static void checkProtection(Document doc, /*ProtectionType*/int type, boolean formatting, boolean enforcement)
    {
        msAssert.areEqual(type, doc.getDocumentProtection().getEdit());
        msAssert.areEqual(formatting, doc.getDocumentProtection().getFormatting());
        msAssert.areEqual(enforcement, doc.getDocumentProtection().getEnforcement());
    }

    /// <summary>
    /// WORDSNET-14109 Support of the following document settings: clickAndTypeStyle, defaultTableStyle,
    /// doNotAutoCompressPictures, updateFields, forceUpgrade, removeDateAndTime, w15:chartTrackingRefBased,
    /// w15:docId, w14:discardImageEditingData, w14:defaultImageDpi.
    /// </summary>
    /// <remarks>
    /// Not all settings are supported by DOC, RTF and WML formats now.
    /// </remarks>
    // FOSS: only the Docx2Docx scenario survives — Doc/Rtf/WordML load+save were removed.
    @Test (dataProvider = "testJira14109DataProvider")
    public void testJira14109(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.saveOpen(generateTestDocForJira14109(), "Model\\Dop\\TestJira14109",
            scenario | UnifiedScenario.NO_GOLD);

        msAssert.areEqual(StyleIndex.HEADING_1, doc.getDocPr().ClickTypeParaStyleIstd);
        Style style = doc.getStyles().getByName("MyTable", false);
        msAssert.isNotNull(style);
        msAssert.areEqual(style.getIstd(), doc.getDocPr().DefaultTableStyleIstd);
        // Some options are not supported in some formats.
        if (scenario != UnifiedScenario.RTF_2_RTF && scenario != UnifiedScenario.WML_2_WML)
        {
            msAssert.areEqual(true, doc.getDocPr().DoNotAutoCompressPictures);
            msAssert.areEqual(true, doc.getDocPr().RemoveDateAndTime);

            if (scenario != UnifiedScenario.DOC_2_DOC)
            {
                msAssert.areEqual(true, doc.getDocPr().UpdateFields);
                msAssert.areEqual(true, doc.getDocPr().ForceUpgrade);
                msAssert.areEqual(true, doc.getDocPr().ChartTrackingRefBased);
                msAssert.areEqual("{D3633DB6-ADDC-42C4-B64B-CF101F95ED34}", doc.getDocPr().DocumentSetId);
                msAssert.areEqual(true, doc.getDocPr().DiscardImageEditingData);
                msAssert.areEqual(200, doc.getDocPr().DefaultImageDpi);
            }
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira14109DataProvider")
	public static Object[][] testJira14109DataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.DOCX_2_DOCX},
		};
	}

    /// <summary>
    /// Generates a test document with settings, which support is added on implementing WORDSNET-14109
    /// </summary>
    private static Document generateTestDocForJira14109() throws Exception
    {
        Document doc = new Document();
        doc.setComplianceInfo(new OoxmlComplianceInfo());
        doc.getComplianceInfo().isDocxExtensions(true);
        doc.getCompatibilityOptions().setDoNotUseIndentAsNumberingTabStop(false); // for DOC format to write as Word2007

        msAssert.areEqual(DocPr.CLICK_TYPE_PARA_STYLE_ISTD_DEFAULT, doc.getDocPr().ClickTypeParaStyleIstd);
        doc.getStyles().getByIstd(StyleIndex.HEADING_1, true); // This adds the style into the style collection.
        doc.getDocPr().ClickTypeParaStyleIstd = StyleIndex.HEADING_1;

        msAssert.areEqual(DocPr.DEFAULT_TABLE_STYLE_ISTD_DEFAULT, doc.getDocPr().DefaultTableStyleIstd);
        Style style = doc.getStyles().add(StyleType.PARAGRAPH, "MyTable");
        style.setBaseStyleName("Heading 4");
        doc.getDocPr().DefaultTableStyleIstd = style.getIstd();

        msAssert.isFalse(doc.getDocPr().DoNotAutoCompressPictures);
        doc.getDocPr().DoNotAutoCompressPictures = true;

        msAssert.isFalse(doc.getDocPr().RemoveDateAndTime);
        doc.getDocPr().RemoveDateAndTime = true;

        msAssert.isFalse(doc.getDocPr().UpdateFields);
        doc.getDocPr().UpdateFields = true;

        msAssert.isFalse(doc.getDocPr().ForceUpgrade);
        doc.getDocPr().ForceUpgrade = true;

        msAssert.isFalse(doc.getDocPr().ChartTrackingRefBased);
        doc.getDocPr().ChartTrackingRefBased = true;

        msAssert.isNull(doc.getDocPr().DocumentSetId);
        doc.getDocPr().DocumentSetId = "{D3633DB6-ADDC-42C4-B64B-CF101F95ED34}";

        msAssert.isFalse(doc.getDocPr().DiscardImageEditingData);
        doc.getDocPr().DiscardImageEditingData = true;

        msAssert.areEqual(0, doc.getDocPr().DefaultImageDpi);
        doc.getDocPr().DefaultImageDpi = 200;

        msAssert.isNull(doc.getDocPr().DocId);
        doc.getDocPr().DocId = "62376D8B";

        return doc;
    }

    // FOSS: TestWarningIfSettingNotSupported removed — it only verified data-loss warnings when
    // downgrading to Doc/Rtf/WordML, all of which were removed. No surviving format exercises it.

    /// <summary>
    /// WORDSNET-21349 The w14:docId has been removed after re-saving.
    /// The new DocId document property has been added.
    /// </summary>
    @Test
    public void test21349() throws Exception
    {
        Document doc = TestUtil.open("Model\\Dop\\Test21349.docx");
        OoxmlSaveOptions options = new OoxmlSaveOptions(SaveFormat.DOCX);
        options.setTestMode();
        options.setWriteW14DocId(true);
        doc = TestUtil.saveOpen(doc, "Model\\Dop\\Test21349.docx", options, false);

        msAssert.areEqual("62376D8B", doc.getDocPr().DocId);
    }

    /// <summary>
    /// Test that RemovePersonalInformation flag is correctly imported and exported.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestJira16914(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        /*UnifiedScenario*/int scenario = TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD;

        // Set option.
        Document doc = TestUtil.open("Model\\Dop\\TestJira16914 false", lf);
        msAssert.isFalse(doc.getRemovePersonalInformation());

        doc.setRemovePersonalInformation(true);

        doc = TestUtil.saveOpen(doc, "Model\\Dop\\TestJira16914", scenario);
        msAssert.isTrue(doc.getRemovePersonalInformation());

        // Remove option.
        doc = TestUtil.open("Model\\Dop\\TestJira16914 true", lf);
        msAssert.isTrue(doc.getRemovePersonalInformation());

        doc.setRemovePersonalInformation(false);

        doc = TestUtil.saveOpen(doc, "Model\\Dop\\TestJira16914", scenario);
        msAssert.isFalse(doc.getRemovePersonalInformation());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJira16914SourceDataProvider")
	public void unifiedTestJira16914Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJira16914(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJira16914SourceDataProvider")
	Object[][] unifiedTestJira16914SourceDataProvider() throws Exception
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

}

