// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import com.aspose.DrColor;
import com.aspose.SystemPal;
import com.aspose.Language;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.TextReader;
import com.aspose.ms.System.IO.StreamReader;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.UnicodeUtil;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for how character formatting is read and parsed and saved.
/// </summary>
@Test
public class TestRuns extends UnifiedTestsBase
{
    /// <summary>
    /// Test that non English characters and special characters a preserved.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestChars(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Run\\Text\\TestChars", lf, sf);

        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 1, true);
        msAssert.areEqual("А Җ ' \" <> {xxx} ‘ “ & < > £ ¥ § ¨ © ª « ® » ¼ \\ \\\\\r", para.getText());

        // Empty paragraph.
        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 2, true);
        msAssert.areEqual("\r", para.getText());
        msAssert.areEqual(24, para.getParagraphBreakFont().getSize());


        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 4, true);
        // In all other formats we simply get them as characters.
        msAssert.areEqual("\uf075 \uf0a8\r", para.getText());
        msAssert.areEqual(3, para.getChildNodes(NodeType.RUN, false).getCount());

        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 10, true);
        msAssert.areEqual("tab1\ttab2\ttab3\r", para.getText());
        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 11, true);
        msAssert.areEqual(" “double opening and closing quotes”\r", para.getText());
        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 12, true);
        msAssert.areEqual("‘single opening and closing quotes’\r", para.getText());

        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 14, true);
        msAssert.areEqual("em\u2014dash and em\u2003space\r", para.getText());
        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 15, true);
        msAssert.areEqual("en\u2013dash and en\u2002space\r", para.getText());

        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 17, true);
        msAssert.areEqual("nonbreaking\u00a0space\r", para.getText());
        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 18, true);
        msAssert.areEqual("one fourths em\u2005space\r", para.getText());

        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 20, true);
        msAssert.areEqual("non\u001ebreaking hyphen\r", para.getText()); // Note this is not a Unicode char.
        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 21, true);
        msAssert.areEqual("op\u001ftional hyphen\r", para.getText()); // Note this is not a Unicode char.

        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 23, true);
        msAssert.areEqual("left-to-right mark\u200etest\u200fright-to-left mark\r", para.getText());

        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 25, true);
        msAssert.areEqual("zero-width non-joiner\u200ca.k.a No-Width Optional Break\r", para.getText());
        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 26, true);
        msAssert.areEqual("zero-width joiner\u200da.k.a No-Width Non Break\r", para.getText());

        // Checks that \r (carriage return) is possible in text (not as a paragraph break).
        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 28, true);

        if (lf == LoadFormat.RTF)
        {
            // Original file RTF saved from DOC.
            // RK MS Word cannot save carriage returns that are not paragraph breaks into RTF.
            msAssert.areEqual("Line 1 ends with a carriage return, not a paragraph break.Line 2.\r", para.getText());
        }
        else if (sf == SaveFormat.RTF)
        {
            // Original file is DOC.
            // WORDSNET-3480 Paragraph is split at carriage return. Line2 goes to separate paragraph.
            msAssert.areEqual("Line 1 ends with a carriage return, not a paragraph break.\r", para.getText());
        }
        else
        {
            // RK This is our (more correct) way.
            msAssert.areEqual("Line 1 ends with a carriage return, not a paragraph break.\rLine 2.\r", para.getText());
        }

        // Because paragraph could be split we need to adjust below text retrieval.
        int inc = (lf != LoadFormat.RTF) && (sf == SaveFormat.RTF) ? 1 : 0;

        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 30 + inc, true);
        msAssert.areEqual("<MyTag1>My text</MyTag1>\r", para.getText());
        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 31 + inc, true);
        msAssert.areEqual("<MyTag2 a=”hello”/>\r", para.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCharsSourceDataProvider")
	public void unifiedTestCharsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestChars(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCharsSourceDataProvider")
	Object[][] unifiedTestCharsSourceDataProvider() throws Exception
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
    void unifiedTestFontNameEnums(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Run\\Font\\TestFontNameEnums", lf, sf);

        FontInfo font;

        if (lf != LoadFormat.WORD_ML)
        {
            // RK It looks like MS Word 2003 does not write standard fonts like Courier New,
            // Arial, Time New Roman, Symbol to WordML. However, Word 2007 seems to write them.
            // To make this test run, we simply don't test standard fonts in WordML.

            font = doc.getFontInfos().get("Courier New");
            msAssert.areEqual(FontPitch.FIXED, font.getPitch());
            msAssert.areEqual(com.aspose.words.FontFamily.MODERN, font.getFamily());

            font = doc.getFontInfos().get("Times New Roman");
            msAssert.areEqual(FontPitch.VARIABLE, font.getPitch());
            msAssert.areEqual(com.aspose.words.FontFamily.ROMAN, font.getFamily());

            font = doc.getFontInfos().get("Arial");
            msAssert.areEqual(FontPitch.VARIABLE, font.getPitch());
            msAssert.areEqual(com.aspose.words.FontFamily.SWISS, font.getFamily());
        }

        // These are non standard fonts and they are written in all MS Word formats.
        font = doc.getFontInfos().get("Vladimir Script");
        msAssert.areEqual(FontPitch.VARIABLE, font.getPitch());
        msAssert.areEqual(com.aspose.words.FontFamily.SCRIPT, font.getFamily());

        font = doc.getFontInfos().get("Bauhaus 93");
        msAssert.areEqual(FontPitch.VARIABLE, font.getPitch());
        msAssert.areEqual(com.aspose.words.FontFamily.DECORATIVE, font.getFamily());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFontNameEnumsSourceDataProvider")
	public void unifiedTestFontNameEnumsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFontNameEnums(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFontNameEnumsSourceDataProvider")
	Object[][] unifiedTestFontNameEnumsSourceDataProvider() throws Exception
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
    /// Tests the chain of inheritance for font attributes
    /// (direct formatting, char styles, paragraph styles).
    ///
    /// RK This does not actually test any import/export. It purely test the model.
    /// Maybe we should move tests that test the model only out of the unified tests?
    /// </summary>
    @Test
    public void testRunWithCharAndParaStyle() throws Exception
    {
        Document doc = new Document();

        Paragraph para = (Paragraph)doc.selectSingleNode("//Paragraph[1]");
        Run run = para.appendChild(new Run(doc, "Hello"));

        //These attrs comes from default font formatting.
        msAssert.areEqual("Times New Roman", run.getFont().getName());
        msAssert.areEqual(12d, run.getFont().getSize());
        msAssert.areEqual(false, run.getFont().getBold());

        //Check paragraph style works.
        //FOSS: the blank document is now loaded from Blank.docx (Word2007-style) instead of Blank.doc,
        //so Heading 9 resolves to the theme major font (Cambria) rather than the old Word2003 Arial.
        para.getParagraphFormat().setStyleName("Heading 9");
        msAssert.areEqual("Cambria", run.getFont().getName());
        msAssert.areEqual(10d, run.getFont().getSize());
        msAssert.areEqual(false, run.getFont().getBold());

        //Check character style works.
        run.getFont().setStyleName("Strong");
        msAssert.areEqual(true, run.getFont().getBold());

        //Check explicit formatting on the run works.
        run.getFont().setSize(20.0);
        msAssert.areEqual(20d, run.getFont().getSize());
    }

    /// <summary>
    /// Test how font border complex attributes work on a text run.
    /// </summary>
    @Test
    public void testFontBorderOnRunInheritance() throws Exception
    {
        Document doc = new Document();

        Paragraph para = (Paragraph)doc.selectSingleNode("//Paragraph[1]");
        Run run = para.appendChild(new Run(doc, "Hello"));

        //No font border by default.
        msAssert.areEqual(0d, run.getFont().getBorder().getLineWidth());

        //Change the paragraph style font border and check it gets inherited
        para.getParagraphFormat().getStyle().getFont().getBorder().setLineWidth(1.0);
        msAssert.areEqual(1d, doc.getStyles().get("Normal").getFont().getBorder().getLineWidth()); //Check we changed it in the style.
        msAssert.areEqual(1d, run.getFont().getBorder().getLineWidth());

        //Change the character style font border and check it gets inherited
        run.getFont().getStyle().getFont().getBorder().setLineWidth(10.0);

        // AM. According to my tests Normal paragraph doesn't inherit Default Paragraph Font. See saved output file.
        msAssert.areEqual(10d, doc.getStyles().getByStyleIdentifier(StyleIdentifier.DEFAULT_PARAGRAPH_FONT).getFont().getBorder().getLineWidth());
        msAssert.areEqual(1d, run.getFont().getBorder().getLineWidth());

        // FOSS: Doc writer removed; these were smoke saves after the real model assertions above.

        //Change the border on the run itself and check it overrides font border specified in the styles.
        run.getFont().getBorder().setLineWidth(3.0);
        msAssert.areEqual(3d, run.getFont().getBorder().getLineWidth());
    }

    /// <summary>
    /// WORDSNET-2932 Tab characters are lost after open/save document.
    /// Verify that absolute tabs are nicely saved to other formats and rendered without a crash.
    /// </summary>
    @Test
    public void testAbsolutePositionTabNoCrash() throws Exception
    {
        final String TEST_NAME = "Model\\Run\\Text\\TestAbsolutePositionTab";
        Document doc = TestUtil.open(TEST_NAME + ".docx");
        // FOSS: doc/wml/odt/html/pdf writers removed. Keep a Docx round-trip as the no-crash check.
        TestUtil.saveOpen(doc, TEST_NAME + ".docx", (SaveOptions)null, false);
    }

    /// <summary>
    /// TODO 3 Note that when a table is autoformatted some character formatting does not
    /// seem to be stored in CHPX so I cannot get for example font to be white.
    /// I guess I need to make a copy of table autoformats and recreate formats
    /// explicitly when displaying or saving into other formats.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestWhiteFont(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Run\\Font\\TestWhiteFont", lf, sf);

        Run run = (Run)doc.selectSingleNode("//Run[1]");
        Font font = run.getFont();
        msAssert.areEqual(Color.WHITE.getRGB(), font.getColor().getRGB());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestWhiteFontSourceDataProvider")
	public void unifiedTestWhiteFontSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestWhiteFont(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestWhiteFontSourceDataProvider")
	Object[][] unifiedTestWhiteFontSourceDataProvider() throws Exception
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
    /// Tab characters are lost after open/save document.
    /// Added new node to the model.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTestAbsolutePositionTab(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        if ((lf != LoadFormat.DOC) && (lf != LoadFormat.WORD_ML))
        {
            Document doc = TestUtil.openSaveOpen("Model\\Run\\Text\\TestAbsolutePositionTab", lf, sf);

            AbsolutePositionTab tab = (AbsolutePositionTab)doc.getNodeById("1.0.0.0");
            msAssert.areEqual("\t", tab.getText());
            msAssert.areEqual(AbsolutePositionTabLeaderChar.DOT, tab.getLeaderChar());

            tab = (AbsolutePositionTab)doc.getNodeById("1.4.0.0");
            msAssert.areEqual(AbsolutePositionTabLeaderChar.DOT, tab.getLeaderChar());
            msAssert.areEqual(AbsolutePositionTabAlignment.CENTER, tab.getAlignment());
            msAssert.areEqual(AbsolutePositionTabPositioningBase.INDENT, tab.getPositioningBase());

            tab = (AbsolutePositionTab)doc.getNodeById("2.4.0.0");
            msAssert.areEqual(AbsolutePositionTabLeaderChar.NONE, tab.getLeaderChar());
            msAssert.areEqual(AbsolutePositionTabAlignment.RIGHT, tab.getAlignment());
            msAssert.areEqual(AbsolutePositionTabPositioningBase.MARGIN, tab.getPositioningBase());
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTestAbsolutePositionTabSourceDataProvider")
	public void unifiedTestTestAbsolutePositionTabSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTestAbsolutePositionTab(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTestAbsolutePositionTabSourceDataProvider")
	Object[][] unifiedTestTestAbsolutePositionTabSourceDataProvider() throws Exception
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
    /// There was a problem with direct formatting applied to a character
    /// style not working when direct formatting was switching off features like bold
    /// or strike through defined in the character style.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDirectFormattingOnCharStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Run\\Style\\TestDirectFormattingOnCharStyle", lf, sf);

        // 0       1       2   3
        // [Word1 ][Word2 ][Wo][rd3][\r]
        NodeList runs = doc.selectNodes("//Run");
        msAssert.areEqual(4, runs.getCount());

        //This run has bold and strike inherited from character style.
        Run run = (Run)runs.get(1);
        msAssert.areEqual("Word2 ", run.getText());
        msAssert.areEqual(true, run.getFont().getBold());
        msAssert.areEqual(0, run.getFont().getHighlightColor().getRGB());
        msAssert.areEqual(true, run.getFont().getStrikeThrough());

        run = (Run)runs.get(2);
        msAssert.areEqual("Wo", run.getText());
        msAssert.areEqual(true, run.getFont().getBold());

        // Highlight is switched on by direct formatting.
        msAssert.areEqual(Color.CYAN.getRGB(), run.getFont().getHighlightColor().getRGB());

        //Strike switched off by direct formatting
        msAssert.areEqual(false, run.getFont().getStrikeThrough());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDirectFormattingOnCharStyleSourceDataProvider")
	public void unifiedTestDirectFormattingOnCharStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDirectFormattingOnCharStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDirectFormattingOnCharStyleSourceDataProvider")
	Object[][] unifiedTestDirectFormattingOnCharStyleSourceDataProvider() throws Exception
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


    private static void checkRun(Run run, String expectedText, /*Underline*/int expectedUnderline, Color expectedColor,
        String expectedStyle)
    {
        msAssert.areEqual(expectedText, run.getText());
        msAssert.areEqual(expectedUnderline, run.getFont().getUnderline());
        msAssert.areEqual(expectedColor.getRGB(), run.getFont().getColor().getRGB());
        msAssert.areEqual(expectedStyle, run.getFont().getStyleName());
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCustomColor(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Run\\Font\\TestCustomColor", lf, sf);

        Run run = (Run)doc.selectSingleNode("//Run[1]");
        msAssert.areEqual("Word1", run.getText());
        DrColor reddish = DrColor.fromArgb(218, 24, 70);
        DrColor greenish = DrColor.fromArgb(0, 100, 50);
        //Test custom color in the original doc
        //Font color
        msAssert.areEqual(reddish, run.getFont().getColorInternal());
        //Shading colors
        msAssert.areEqual(greenish, run.getFont().getShading().getBackgroundPatternColorInternal());
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
    /// In this the RTL attributes are different from the normal italic and dont size.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestLostFontSize(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Run\\Font\\TestLostFontSize", lf, sf);

        Run run = (Run)doc.selectSingleNode("//Run[1]");
        msAssert.areEqual(false, run.getFont().getItalic());
        msAssert.areEqual(true, run.getFont().getItalicBi());
        msAssert.areEqual(9d, run.getFont().getSize());
        msAssert.areEqual(10d, run.getFont().getSizeBi());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLostFontSizeSourceDataProvider")
	public void unifiedTestLostFontSizeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLostFontSize(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLostFontSizeSourceDataProvider")
	Object[][] unifiedTestLostFontSizeSourceDataProvider() throws Exception
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
    void unifiedTestSymbol(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Run\\Text\\TestSymbol", lf, sf);

        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);

        Run run = (Run)runs.get(0);
        msAssert.areEqual("Arial", run.getFont().getName());
        msAssert.areEqual("\u2264", run.getText());

        run = (Run)runs.get(1);

        msAssert.areEqual("Symbol", run.getFont().getNameAscii());
        msAssert.areEqual("Symbol", run.getFont().getNameOther());
        msAssert.areEqual("\uf0a3", run.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestSymbolSourceDataProvider")
	public void unifiedTestSymbolSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestSymbol(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestSymbolSourceDataProvider")
	Object[][] unifiedTestSymbolSourceDataProvider() throws Exception
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
    void unifiedTestLineBreakType(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Run\\Text\\TestLineBreakType", lf, sf);

        switch (lf)
        {
            case LoadFormat.DOC:
            case LoadFormat.RTF:
                {
                    msAssert.areEqual(6, doc.getChildNodes(NodeType.RUN, true).getCount());

                    Run run = (Run)doc.getChild(NodeType.RUN, 0, true);
                    msAssert.areEqual("Line break\u000bLine break clear left", run.getText());
                    msAssert.areEqual(LineBreakClear.NONE, run.getRunPr().getLineBreakClear());

                    run = (Run)doc.getChild(NodeType.RUN, 1, true);
                    msAssert.areEqual("\u000b", run.getText());
                    msAssert.areEqual(LineBreakClear.LEFT, run.getRunPr().getLineBreakClear());

                    run = (Run)doc.getChild(NodeType.RUN, 3, true);
                    msAssert.areEqual("\u000b", run.getText());
                    msAssert.areEqual(LineBreakClear.RIGHT, run.getRunPr().getLineBreakClear());

                    run = (Run)doc.getChild(NodeType.RUN, 5, true);
                    msAssert.areEqual("\u000b", run.getText());
                    msAssert.areEqual(LineBreakClear.ALL, run.getRunPr().getLineBreakClear());
                    break;
                }
            case LoadFormat.WORD_ML:
            case LoadFormat.DOCX:
                {
                    // RK When saved as WordML or DOCX MS Word breaks runs differently.
                    msAssert.areEqual(5, doc.getChildNodes(NodeType.RUN, true).getCount());

                    Run run = (Run)doc.getChild(NodeType.RUN, 0, true);
                    msAssert.areEqual("Line break", run.getText());

                    run = (Run)doc.getChild(NodeType.RUN, 1, true);
                    msAssert.areEqual("\u000bLine break clear left", run.getText());
                    msAssert.areEqual(LineBreakClear.NONE, run.getRunPr().getLineBreakClear());

                    run = (Run)doc.getChild(NodeType.RUN, 2, true);
                    msAssert.areEqual("\u000bLine break clear right", run.getText());
                    msAssert.areEqual(LineBreakClear.LEFT, run.getRunPr().getLineBreakClear());

                    run = (Run)doc.getChild(NodeType.RUN, 3, true);
                    msAssert.areEqual("\u000bText wrapping break", run.getText());
                    msAssert.areEqual(LineBreakClear.RIGHT, run.getRunPr().getLineBreakClear());

                    run = (Run)doc.getChild(NodeType.RUN, 4, true);
                    msAssert.areEqual("\u000b", run.getText());
                    msAssert.areEqual(LineBreakClear.ALL, run.getRunPr().getLineBreakClear());
                    break;
                }
            default:
                throw new IllegalStateException("Unknown file format.");
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLineBreakTypeSourceDataProvider")
	public void unifiedTestLineBreakTypeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLineBreakType(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLineBreakTypeSourceDataProvider")
	Object[][] unifiedTestLineBreakTypeSourceDataProvider() throws Exception
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
    /// RK There is a date custom property in the document.
    /// RK When reading RTF, MS Word seems to use current culture.
    /// Therefore we need to switch to the culture in which the document was created.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestPageBreak(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        SystemPal.saveCulture();
        try
        {
            SystemPal.setStandardCulture();

            Document doc = TestUtil.openSaveOpen("Model\\Run\\Text\\TestPageBreak", lf, sf);

            msAssert.areEqual("Text before page break.\r\f" +
                        "Text after page break.\f", doc.getText());

            NodeList runs = doc.selectNodes("//Run");
            msAssert.areEqual(3, runs.getCount());

            //Page break seems to have its own character run in Word document that can have properties.
            Run pageBreak = (Run)runs.get(1);
            msAssert.areEqual(Color.RED.getRGB(), pageBreak.getFont().getColor().getRGB());

            //Page break belongs seems to belong to a paragraph object.
            Paragraph para = pageBreak.getParentParagraph();
            //Some settings I modified on page break has affected the paragraph.
            msAssert.areEqual(24.0, para.getParagraphFormat().getSpaceBefore());
        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestPageBreakSourceDataProvider")
	public void unifiedTestPageBreakSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestPageBreak(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestPageBreakSourceDataProvider")
	Object[][] unifiedTestPageBreakSourceDataProvider() throws Exception
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
    /// WORDSNET-803 Setting LocaleID does not influence the language setting,
    /// seeing in statusbar of MS Word.
    /// Test creating a document and specifying locale id for runs.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestLocaleId(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        DocumentBuilder b = new DocumentBuilder();
        b.getFont().setLocaleId(1030);
        b.writeln("Hello");
        TestUtil.saveOpen(b.getDocument(), "Model\\Run\\Text\\TestLocaleId", lf, sf);
        // Can't really test anything automatically.
        // Open the file in MS Word and see what language is displayed.
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLocaleIdSourceDataProvider")
	public void unifiedTestLocaleIdSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLocaleId(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLocaleIdSourceDataProvider")
	Object[][] unifiedTestLocaleIdSourceDataProvider() throws Exception
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
    public void testSetCharacterStyle() throws Exception
    {
        Document doc = new Document();
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(para.getParagraphBreakFont().getStyle(), doc.getStyles().get("Default Paragraph Font"));
        para.getParagraphBreakFont().setStyle(doc.getStyles().get("Hyperlink"));
        msAssert.areEqual(para.getParagraphBreakFont().getStyle(), doc.getStyles().get("Hyperlink"));
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testSetCharacterStyleNull() throws Exception
    {
        Document doc = new Document();
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        para.getParagraphBreakFont().setStyle(null);
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThis style belongs to a different document.\\E")
    public void testSetCharacterStyleWrongDocument() throws Exception
    {
        Document doc1 = new Document();
        Document doc2 = new Document();
        Paragraph para = doc1.getFirstSection().getBody().getFirstParagraph();
        para.getParagraphBreakFont().setStyle(doc2.getStyles().get("Hyperlink"));
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThis style is not a character style.\\E")
    public void testSetCharacterStyleWrongStyleType() throws Exception
    {
        Document doc = new Document();
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        para.getParagraphBreakFont().setStyle(doc.getStyles().get("Normal"));
    }

    /// <summary>
    /// Test language settings.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestLanguageSettings(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        final String FILE_NAME = "Model\\Run\\Text\\TestLanguageSettings";

        Document doc = TestUtil.open(FILE_NAME, lf);
        SaveOptions so = SaveOptions.createSaveOptions(sf);
        so.setTestMode();
        so.setUpdateAmbiguousTextFont(true);
        TestUtil.saveOpen(doc, FILE_NAME, TestUtil.getUnifiedScenario(lf, sf), so);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLanguageSettingsSourceDataProvider")
	public void unifiedTestLanguageSettingsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLanguageSettings(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLanguageSettingsSourceDataProvider")
	Object[][] unifiedTestLanguageSettingsSourceDataProvider() throws Exception
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
    void unifiedTestFontHighlight(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestFontHighlight", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFontHighlightSourceDataProvider")
	public void unifiedTestFontHighlightSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFontHighlight(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFontHighlightSourceDataProvider")
	Object[][] unifiedTestFontHighlightSourceDataProvider() throws Exception
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
    /// WORDSNET-4653 Font.HighlightColor doesn’t work for DOCX documents.
    /// Export of highlight was incorrect.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCreateHighlight(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Check a "stock color".
        builder.getFont().setHighlightColor(Color.YELLOW);
        builder.writeln("Highlighted in stock yellow (from stock).");

        // Check a custom color. Highlights are only 16 predefined colors and
        // such custom colors get converted to the nearest color.
        builder.getFont().setHighlightColor(DrColor.fromArgb(240, 0, 0).toNativeColor());
        builder.writeln("Highlighted in red (from custom).");

        doc = TestUtil.saveOpen(doc, "Model\\Run\\Font\\TestCreateHighlight", lf, sf);

        // This was set using stock color.
        Run run = (Run)doc.getChild(NodeType.RUN, 0, true);
        msAssert.areEqual(Color.YELLOW.getRGB(), run.getFont().getHighlightColor().getRGB());

        // This was set using custom color.
        run = (Run)doc.getChild(NodeType.RUN, 1, true);
        msAssert.areEqual(Color.RED.getRGB(), run.getFont().getHighlightColor().getRGB());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCreateHighlightSourceDataProvider")
	public void unifiedTestCreateHighlightSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCreateHighlight(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCreateHighlightSourceDataProvider")
	Object[][] unifiedTestCreateHighlightSourceDataProvider() throws Exception
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
    /// Test that BoolEx toggle values are property calculated as booleans and written to wordml.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestBoolEx(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestBoolEx", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBoolExSourceDataProvider")
	public void unifiedTestBoolExSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBoolEx(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBoolExSourceDataProvider")
	Object[][] unifiedTestBoolExSourceDataProvider() throws Exception
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
    void unifiedTestLineBreak(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Text\\TestLineBreak", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLineBreakSourceDataProvider")
	public void unifiedTestLineBreakSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLineBreak(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLineBreakSourceDataProvider")
	Object[][] unifiedTestLineBreakSourceDataProvider() throws Exception
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
    void unifiedTestUnicode(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Text\\TestUnicode", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestUnicodeSourceDataProvider")
	public void unifiedTestUnicodeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestUnicode(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestUnicodeSourceDataProvider")
	Object[][] unifiedTestUnicodeSourceDataProvider() throws Exception
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
    void unifiedTestWingdings(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Text\\TestWingdings", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestWingdingsSourceDataProvider")
	public void unifiedTestWingdingsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestWingdings(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestWingdingsSourceDataProvider")
	Object[][] unifiedTestWingdingsSourceDataProvider() throws Exception
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
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestAll", lf, sf);
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
    void unifiedTestCaps(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestCaps", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCapsSourceDataProvider")
	public void unifiedTestCapsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCaps(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCapsSourceDataProvider")
	Object[][] unifiedTestCapsSourceDataProvider() throws Exception
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
    void unifiedTestDefaultFont(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestDefaultFont", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefaultFontSourceDataProvider")
	public void unifiedTestDefaultFontSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefaultFont(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefaultFontSourceDataProvider")
	Object[][] unifiedTestDefaultFontSourceDataProvider() throws Exception
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
    void unifiedTestEffects(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestEffects", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestEffectsSourceDataProvider")
	public void unifiedTestEffectsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestEffects(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestEffectsSourceDataProvider")
	Object[][] unifiedTestEffectsSourceDataProvider() throws Exception
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
    void unifiedTestEmboss(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestEmboss", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestEmbossSourceDataProvider")
	public void unifiedTestEmbossSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestEmboss(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestEmbossSourceDataProvider")
	Object[][] unifiedTestEmbossSourceDataProvider() throws Exception
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
    void unifiedTestFontBoldItalic(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestFontBoldItalic", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFontBoldItalicSourceDataProvider")
	public void unifiedTestFontBoldItalicSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFontBoldItalic(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFontBoldItalicSourceDataProvider")
	Object[][] unifiedTestFontBoldItalicSourceDataProvider() throws Exception
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
    void unifiedTestFontMetrics(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestFontMetrics", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFontMetricsSourceDataProvider")
	public void unifiedTestFontMetricsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFontMetrics(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFontMetricsSourceDataProvider")
	Object[][] unifiedTestFontMetricsSourceDataProvider() throws Exception
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
    void unifiedTestFontNameStyleSize(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestFontNameStyleSize", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFontNameStyleSizeSourceDataProvider")
	public void unifiedTestFontNameStyleSizeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFontNameStyleSize(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFontNameStyleSizeSourceDataProvider")
	Object[][] unifiedTestFontNameStyleSizeSourceDataProvider() throws Exception
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
    void unifiedTestFontOutline(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestFontOutline", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFontOutlineSourceDataProvider")
	public void unifiedTestFontOutlineSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFontOutline(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFontOutlineSourceDataProvider")
	Object[][] unifiedTestFontOutlineSourceDataProvider() throws Exception
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
    void unifiedTestFontRaisedLowered(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestFontRaisedLowered", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFontRaisedLoweredSourceDataProvider")
	public void unifiedTestFontRaisedLoweredSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFontRaisedLowered(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFontRaisedLoweredSourceDataProvider")
	Object[][] unifiedTestFontRaisedLoweredSourceDataProvider() throws Exception
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
    void unifiedTestFontSize(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestFontSize", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFontSizeSourceDataProvider")
	public void unifiedTestFontSizeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFontSize(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFontSizeSourceDataProvider")
	Object[][] unifiedTestFontSizeSourceDataProvider() throws Exception
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
    void unifiedTestFontWidth(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestFontWidth", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFontWidthSourceDataProvider")
	public void unifiedTestFontWidthSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFontWidth(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFontWidthSourceDataProvider")
	Object[][] unifiedTestFontWidthSourceDataProvider() throws Exception
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
    void unifiedTestFontSubscript(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestFontSubscript", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFontSubscriptSourceDataProvider")
	public void unifiedTestFontSubscriptSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFontSubscript(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFontSubscriptSourceDataProvider")
	Object[][] unifiedTestFontSubscriptSourceDataProvider() throws Exception
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
    void unifiedTestHiddenInvisible(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestHiddenInvisible", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestHiddenInvisibleSourceDataProvider")
	public void unifiedTestHiddenInvisibleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestHiddenInvisible(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestHiddenInvisibleSourceDataProvider")
	Object[][] unifiedTestHiddenInvisibleSourceDataProvider() throws Exception
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
    void unifiedTestHiddenVisible(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestHiddenVisible", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestHiddenVisibleSourceDataProvider")
	public void unifiedTestHiddenVisibleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestHiddenVisible(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestHiddenVisibleSourceDataProvider")
	Object[][] unifiedTestHiddenVisibleSourceDataProvider() throws Exception
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
    void unifiedTestProofing(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestProofing", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestProofingSourceDataProvider")
	public void unifiedTestProofingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestProofing(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestProofingSourceDataProvider")
	Object[][] unifiedTestProofingSourceDataProvider() throws Exception
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
    void unifiedTestRevisionEdit(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Other\\TestRevisionEdit", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionEditSourceDataProvider")
	public void unifiedTestRevisionEditSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionEdit(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionEditSourceDataProvider")
	Object[][] unifiedTestRevisionEditSourceDataProvider() throws Exception
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
    void unifiedTestRevisionFormatting(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Other\\TestRevisionFormatting", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionFormattingSourceDataProvider")
	public void unifiedTestRevisionFormattingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionFormatting(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionFormattingSourceDataProvider")
	Object[][] unifiedTestRevisionFormattingSourceDataProvider() throws Exception
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
    void unifiedTestRevisionNumbering(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Other\\TestRevisionNumbering", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRevisionNumberingSourceDataProvider")
	public void unifiedTestRevisionNumberingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRevisionNumbering(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRevisionNumberingSourceDataProvider")
	Object[][] unifiedTestRevisionNumberingSourceDataProvider() throws Exception
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
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestSpacing", lf, sf);
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
    void unifiedTestStrikeThrough(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestStrikeThrough", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestStrikeThroughSourceDataProvider")
	public void unifiedTestStrikeThroughSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestStrikeThrough(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestStrikeThroughSourceDataProvider")
	Object[][] unifiedTestStrikeThroughSourceDataProvider() throws Exception
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
    void unifiedTestStrikeUnderline(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestStrikeUnderline", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestStrikeUnderlineSourceDataProvider")
	public void unifiedTestStrikeUnderlineSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestStrikeUnderline(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestStrikeUnderlineSourceDataProvider")
	Object[][] unifiedTestStrikeUnderlineSourceDataProvider() throws Exception
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
    void unifiedTestSubscript(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestSubscript", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestSubscriptSourceDataProvider")
	public void unifiedTestSubscriptSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestSubscript(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestSubscriptSourceDataProvider")
	Object[][] unifiedTestSubscriptSourceDataProvider() throws Exception
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
    void unifiedTestUnderline(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestUnderline", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestUnderlineSourceDataProvider")
	public void unifiedTestUnderlineSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestUnderline(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestUnderlineSourceDataProvider")
	Object[][] unifiedTestUnderlineSourceDataProvider() throws Exception
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
    void unifiedTestUnderlineAverageMetrics(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestUnderlineAverageMetrics", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestUnderlineAverageMetricsSourceDataProvider")
	public void unifiedTestUnderlineAverageMetricsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestUnderlineAverageMetrics(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestUnderlineAverageMetricsSourceDataProvider")
	Object[][] unifiedTestUnderlineAverageMetricsSourceDataProvider() throws Exception
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
    void unifiedTestUnderlineBigFont(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestUnderlineBigFont", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestUnderlineBigFontSourceDataProvider")
	public void unifiedTestUnderlineBigFontSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestUnderlineBigFont(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestUnderlineBigFontSourceDataProvider")
	Object[][] unifiedTestUnderlineBigFontSourceDataProvider() throws Exception
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
    void unifiedTestUnderlineMetrics(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestUnderlineMetrics", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestUnderlineMetricsSourceDataProvider")
	public void unifiedTestUnderlineMetricsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestUnderlineMetrics(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestUnderlineMetricsSourceDataProvider")
	Object[][] unifiedTestUnderlineMetricsSourceDataProvider() throws Exception
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
    void unifiedTestUnderlinePosition(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Font\\TestUnderlinePosition", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestUnderlinePositionSourceDataProvider")
	public void unifiedTestUnderlinePositionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestUnderlinePosition(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestUnderlinePositionSourceDataProvider")
	Object[][] unifiedTestUnderlinePositionSourceDataProvider() throws Exception
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
    void unifiedTestAutoColorBackground(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Border\\TestAutoColorBackground", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAutoColorBackgroundSourceDataProvider")
	public void unifiedTestAutoColorBackgroundSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAutoColorBackground(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAutoColorBackgroundSourceDataProvider")
	Object[][] unifiedTestAutoColorBackgroundSourceDataProvider() throws Exception
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
    void unifiedTestAutoColorTexture(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Border\\TestAutoColorTexture", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAutoColorTextureSourceDataProvider")
	public void unifiedTestAutoColorTextureSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAutoColorTexture(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAutoColorTextureSourceDataProvider")
	Object[][] unifiedTestAutoColorTextureSourceDataProvider() throws Exception
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
    void unifiedTestFontBorder(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Border\\TestFontBorder", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFontBorderSourceDataProvider")
	public void unifiedTestFontBorderSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFontBorder(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFontBorderSourceDataProvider")
	Object[][] unifiedTestFontBorderSourceDataProvider() throws Exception
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
    void unifiedTestFontShading(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Border\\TestFontShading", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFontShadingSourceDataProvider")
	public void unifiedTestFontShadingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFontShading(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFontShadingSourceDataProvider")
	Object[][] unifiedTestFontShadingSourceDataProvider() throws Exception
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
    void unifiedTestTextureShading(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Border\\TestTextureShading", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTextureShadingSourceDataProvider")
	public void unifiedTestTextureShadingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTextureShading(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTextureShadingSourceDataProvider")
	Object[][] unifiedTestTextureShadingSourceDataProvider() throws Exception
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
    void unifiedTestComment(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Other\\TestComment", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCommentSourceDataProvider")
	public void unifiedTestCommentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestComment(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCommentSourceDataProvider")
	Object[][] unifiedTestCommentSourceDataProvider() throws Exception
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
    void unifiedTestHyperlinkLayout(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Other\\TestHyperlinkLayout", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestHyperlinkLayoutSourceDataProvider")
	public void unifiedTestHyperlinkLayoutSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestHyperlinkLayout(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestHyperlinkLayoutSourceDataProvider")
	Object[][] unifiedTestHyperlinkLayoutSourceDataProvider() throws Exception
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
    void unifiedTestInlinePicture(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Other\\TestInlinePicture", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestInlinePictureSourceDataProvider")
	public void unifiedTestInlinePictureSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestInlinePicture(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestInlinePictureSourceDataProvider")
	Object[][] unifiedTestInlinePictureSourceDataProvider() throws Exception
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
    void unifiedTestInlinePictureEffects(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Other\\TestInlinePictureEffects", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestInlinePictureEffectsSourceDataProvider")
	public void unifiedTestInlinePictureEffectsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestInlinePictureEffects(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestInlinePictureEffectsSourceDataProvider")
	Object[][] unifiedTestInlinePictureEffectsSourceDataProvider() throws Exception
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
    void unifiedTestInlinePicturePosition(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Other\\TestInlinePicturePosition", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestInlinePicturePositionSourceDataProvider")
	public void unifiedTestInlinePicturePositionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestInlinePicturePosition(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestInlinePicturePositionSourceDataProvider")
	Object[][] unifiedTestInlinePicturePositionSourceDataProvider() throws Exception
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
    void unifiedTestOle2(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Other\\TestOle2", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestOle2SourceDataProvider")
	public void unifiedTestOle2Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestOle2(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestOle2SourceDataProvider")
	Object[][] unifiedTestOle2SourceDataProvider() throws Exception
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
    void unifiedTestPrivate(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Other\\TestPrivate", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestPrivateSourceDataProvider")
	public void unifiedTestPrivateSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestPrivate(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestPrivateSourceDataProvider")
	Object[][] unifiedTestPrivateSourceDataProvider() throws Exception
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
    void unifiedTestTextInputAsText(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Other\\TestTextInputAsText", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTextInputAsTextSourceDataProvider")
	public void unifiedTestTextInputAsTextSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTextInputAsText(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTextInputAsTextSourceDataProvider")
	Object[][] unifiedTestTextInputAsTextSourceDataProvider() throws Exception
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
    void unifiedTestTabLeader(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Other\\TestTabLeader", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTabLeaderSourceDataProvider")
	public void unifiedTestTabLeaderSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTabLeader(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTabLeaderSourceDataProvider")
	Object[][] unifiedTestTabLeaderSourceDataProvider() throws Exception
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
    void unifiedTestType1Font(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Other\\TestType1Font", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestType1FontSourceDataProvider")
	public void unifiedTestType1FontSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestType1Font(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestType1FontSourceDataProvider")
	Object[][] unifiedTestType1FontSourceDataProvider() throws Exception
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
    void unifiedTestYen(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Run\\Text\\TestYen", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestYenSourceDataProvider")
	public void unifiedTestYenSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestYen(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestYenSourceDataProvider")
	Object[][] unifiedTestYenSourceDataProvider() throws Exception
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
    /// Tests emphasis attribute for text.
    /// </summary>
    /// <remarks>
    /// AM. I couldn't understand how to make text emphasized in MS Word. There is no such menu command or something.
    /// So I made test file in weird way - just write various emphasis value for CKcd sprm (from 0 to 4).
    /// Original test file contains just one emphasis option, this file contains all of them.
    /// </remarks>
    /// <param name="lf"></param>
    /// <param name="sf"></param>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestEmphasis(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Run\\Font\\TestDefect4081-2", lf, sf);

        msAssert.areEqual(EmphasisMark.NONE, ((Run)doc.getNodeById("2.1.0.0")).getRunPr().getEmphasisMark());
        msAssert.areEqual(EmphasisMark.OVER_SOLID_CIRCLE, ((Run)doc.getNodeById("2.2.0.0")).getRunPr().getEmphasisMark());
        msAssert.areEqual(EmphasisMark.OVER_COMMA, ((Run)doc.getNodeById("2.3.0.0")).getRunPr().getEmphasisMark());
        msAssert.areEqual(EmphasisMark.OVER_WHITE_CIRCLE, ((Run)doc.getNodeById("2.4.0.0")).getRunPr().getEmphasisMark());
        msAssert.areEqual(EmphasisMark.UNDER_SOLID_CIRCLE, ((Run)doc.getNodeById("2.5.0.0")).getRunPr().getEmphasisMark());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestEmphasisSourceDataProvider")
	public void unifiedTestEmphasisSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestEmphasis(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestEmphasisSourceDataProvider")
	Object[][] unifiedTestEmphasisSourceDataProvider() throws Exception
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
    /// WORDSNET-5184 Size of font is changed during converting to RTF.
    /// "Default Paragraph Font" style should not be used as default font.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestJira5184(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\Run\\Style\\TestJira5184", lf);

        Style defaultParagraphFont = doc.getStyles().getByIstd(StyleIndex.DEFAULT_PARAGRAPH_FONT, false);
        msAssert.areEqual("Arial", defaultParagraphFont.getFont().getName());
        // Modify "Default Paragraph Font" style in order to be sure that its attributes are not inherited.
        defaultParagraphFont.getRunPr().setColor(DrColor.RED);

        Run run = (Run)doc.getNodeById("0.0.3.0");
        Paragraph para = run.getParentParagraph();
        msAssert.areEqual("Footer", para.getParagraphStyle().getName());
        // This means no character style is assigned.
        msAssert.areEqual("Default Paragraph Font", run.getCharacterStyle().getName());

        // Verify direct formatting absence. RTF stores resolved attributes as well.
        if(lf != LoadFormat.RTF)
            msAssert.isNull(run.getRunPr().getDirectAttr(FontAttr.NAME_ASCII));

        // Attribute can be resolved in two ways. Verify font is inherited from paragraph style.
        msAssert.areEqual("Algerian", run.getFont().getNameAscii());
        msAssert.areEqual("Algerian", run.getExpandedRunPr(RunPrExpandFlags.NORMAL).getNameAscii());

        // The same is for font size.
        if(lf != LoadFormat.RTF)
            msAssert.isNull(run.getRunPr().getDirectAttr(FontAttr.SIZE));

        msAssert.areEqual(48, run.getFont().getSize());
        // Size is multiplied by 2 in attribute collection.
        msAssert.areEqual(96, run.getExpandedRunPr(RunPrExpandFlags.NORMAL).getSize());

        // Character style is applied.
        run = (Run)doc.getNodeById("1.3.3.0");
        para = run.getParentParagraph();
        msAssert.areEqual("Footer", para.getParagraphStyle().getName());
        msAssert.areEqual("Emphasis", run.getCharacterStyle().getName());
        msAssert.areEqual("Arial", run.getFont().getName());
        msAssert.areEqual("Arial", run.getExpandedRunPr(RunPrExpandFlags.NORMAL).getNameAscii());
        msAssert.areEqual(10, run.getFont().getSize());
        msAssert.areEqual(20, run.getExpandedRunPr(RunPrExpandFlags.NORMAL).getSize());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJira5184SourceDataProvider")
	public void unifiedTestJira5184Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJira5184(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJira5184SourceDataProvider")
	Object[][] unifiedTestJira5184SourceDataProvider() throws Exception
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
    /// WORDSNET-5894 Incorrect style applied while saving document object as Pdf.
    /// Character style is applied to paragraph - use Normal paragraph style instead.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestJira5894(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\Run\\Style\\TestJira5894", lf);

        Run run = (Run)doc.getNodeById("0.1.0.0");
        RunPr expandedRunPr = run.getExpandedRunPr(RunPrExpandFlags.LAYOUT);
        msAssert.areNotEqual(DrColor.BLUE, expandedRunPr.getColor());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJira5894SourceDataProvider")
	public void unifiedTestJira5894Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJira5894(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJira5894SourceDataProvider")
	Object[][] unifiedTestJira5894SourceDataProvider() throws Exception
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
    /// WORDSNET-11771 Support the dir and the bdo elements.
    /// Added reading and writing 'dir' and 'bdo' elements for DOCX.
    /// These elements in model are represented as runs with Unicode BiDi control character.
    /// </summary>
    @Test (dataProvider = "unifiedTestJira11771DataProvider")
    // FOSS: Doc/Rtf/Wml formats removed. Keep the Docx roundtrip case.
    public void unifiedTestJira11771(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Bidi\\TestJira11771", scenario | UnifiedScenario.NO_GOLD);

        // Paragraph with BiDi control chars.
        Paragraph para = (Paragraph) doc.getFirstSection().getBody().getFirstParagraph().getNextSibling();

        //Check runs with BiDi control chars.
        NodeCollection runs = para.getChildNodes(NodeType.RUN, true);

        /*LoadFormat*/int lf = TestUtil.getLoadFormat(scenario);
        /*SaveFormat*/int sf = TestUtil.getSaveFormat(scenario);

        // MS Word produces different number of BiDi Control char runs depending on file format.
        int expectedRunsCount;
        if (lf == LoadFormat.DOCX)
            expectedRunsCount = (sf == SaveFormat.DOCX) ? 21 : 17;
        else
            expectedRunsCount = 21;

        msAssert.areEqual(expectedRunsCount, runs.getCount());

        msAssert.areEqual("\u202a", ((Run)runs.get(4)).getText()); //LRE
        msAssert.areEqual("\u202b", ((Run)runs.get(6)).getText()); //RLE
        msAssert.areEqual("\u202c", ((Run)runs.get(8)).getText()); //PDF
        msAssert.areEqual("\u202d", ((Run)runs.get(9)).getText()); //LRO
        msAssert.areEqual("\u202e", ((Run)runs.get(11)).getText()); //RLO

        for (int i = 13; i < expectedRunsCount; i++)
            msAssert.areEqual("\u202c", ((Run)runs.get(i)).getText()); //PDF
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "unifiedTestJira11771DataProvider")
	public static Object[][] unifiedTestJira11771DataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.DOCX_2_DOCX},
		};
	}

    /// <summary>
    /// WORDSNET-27952 Toggle attributes not resolved correctly when defined in Character style and Paragraph style
    /// </summary>
    @Test
    public void testDefect27952CharStyleVsParaStyle() throws Exception
    {
        Document doc = TestUtil.open("Model\\Run\\Font\\TestDefect27952CharStyleVsParaStyle.docx");

        checkStyleAttributes(doc);
        testCombinedAttributes(doc);
    }

    /// <summary>
    /// Test WORDSNET-7965 compliance.
    /// </summary>
    @Test (enabled = false, description = "WORDSNET-8160 Work in progress.")
    public void testJira7965Compliance() throws Exception
    {
        Document doc = TestUtil.open("Model\\Run\\Other\\TestJira7965Styles.docx");

        // Verify compliance info is properly set.
        msAssert.isTrue(doc.getComplianceInfo().isDocxExtensions());

        OoxmlSaveOptions so = new OoxmlSaveOptions(SaveFormat.DOCX);
        so.setCompliance(OoxmlCompliance.ECMA_376_2006);

        doc = TestUtil.saveOpen(doc, "Model\\Run\\Other\\TestJira7965Styles ECMA376", UnifiedScenario.DOCX_2_DOCX_NO_GOLD, so);
        msAssert.isFalse(doc.getComplianceInfo().isDocxExtensions());
    }

    /// <summary>
    /// WORDSNET-10102 Alignment setting are not preserved during open/save a doc
    /// Added support for FontAttr.FitText
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestJira10102(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Run\\Text\\TestJira10102", lf, sf);

        RunCollection runs = doc.getFirstSection().getBody().getParagraphs().get(1).getRuns();

        // FitTextId is not supported in RTF format so we have to auto-generate it.
        int fitTextId = ((lf == LoadFormat.RTF) || (sf == SaveFormat.RTF)) ? 1 : 0x25135a00;

        msAssert.areEqual(new FitText(2100, fitTextId), runs.get(1).getRunPr().get(FontAttr.FIT_TEXT));
        msAssert.areEqual(new FitText(2100, fitTextId), runs.get(2).getRunPr().get(FontAttr.FIT_TEXT));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJira10102SourceDataProvider")
	public void unifiedTestJira10102Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJira10102(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJira10102SourceDataProvider")
	Object[][] unifiedTestJira10102SourceDataProvider() throws Exception
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
    /// Relates to WORDSNET-11841 Additional check for run split logic.
    /// </summary>
    @Test
    public void testJira11841Text() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.writeln("ὕαλον ϕαγεῖν δύναμαι· τοῦτο οὔ με βλάπτει. ฉันกินกระจกได้แต่มันไม่ทำให้ฉันเจ็บ 私はガラスを食べられます。それは私を傷つけません。");
        builder.writeln("ฉันกินกระจกได้แต่มันไม่ทำให้ฉันเจ็บ 私はガラスを食べられます。それは私を傷つけません。");
        builder.writeln("ὕαλον ϕαγεῖν δύναμαι· τοῦτο οὔ με βλάπτει. ฉันกิน");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        // FOSS: Doc writer removed; Markdown (NonMSWordFormat) also extracts script subruns.
        TestUtil.executeValidator(doc, SaveFormat.MARKDOWN);

        // After validator is executed Thai subruns should be extracted.
        Paragraph p = paras.get(0);
        msAssert.areEqual(3, p.getRuns().getCount());
        checkNotThai(p.getRuns().get(0));
        checkThai(p.getRuns().get(1));
        checkNotThai(p.getRuns().get(2));

        p = paras.get(1);
        msAssert.areEqual(2, p.getRuns().getCount());
        checkThai(p.getRuns().get(0));
        checkNotThai(p.getRuns().get(1));

        p = paras.get(2);
        msAssert.areEqual(2, p.getRuns().getCount());
        checkNotThai(p.getRuns().get(0));
        checkThai(p.getRuns().get(1));
    }

    /// <summary>
    /// Fields were parsed incorrectly so "Error!" is rendered instead of ruby.
    /// </summary>
    @Test
    public void testRubyFieldError() throws Exception
    {
        // EQ field can be written to OOXML. We need to convert it too.
        // FOSS: Doc reader removed; the FlatOpc branch exercises the same conversion.
        for (/*LoadFormat*/int lf : new /*LoadFormat*/int[] { LoadFormat.FLAT_OPC })
        {
            Document doc = TestUtil.open("Model\\Run\\Text\\Ruby\\TestRubyFromField", lf);

            FieldExtractorToCollection fieldExtractor = new FieldExtractorToCollection();
            fieldExtractor.extract(doc.getFirstSection().getBody());

            // Verify that no fields were loaded to model.
            msAssert.areEqual(0, fieldExtractor.getFields().size());
        }
    }

    /// <summary>
    /// WORDSNET-4136 Ruby (EQ fields) are lost after open/save.
    /// Added roundtrip support and made initial rendering.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestJira4136(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Run\\Text\\Ruby\\TestJira4136", TestUtil.getUnifiedScenario(lf, sf));
        Run rubyRun = doc.getFirstSection().getBody().getFirstParagraph().getLastRun();

        Ruby ruby = (Ruby)rubyRun.getRunPr().get(FontAttr.RUBY);

        msAssert.areEqual("ait", ruby.getTop().get(0).Text);
        msAssert.areEqual(12, ruby.getTop().get(0).RunPr.get(FontAttr.SIZE));

        msAssert.areEqual("8", ruby.getBase().get(0).Text);
        msAssert.areEqual(28, ruby.getBase().get(0).RunPr.get(FontAttr.SIZE));

        msAssert.areEqual(28, ruby.BaseSize);
        msAssert.areEqual(12, ruby.TopSize);
        msAssert.areEqual(26, ruby.Distance);

        msAssert.areEqual(RubyAlignment.DISTRIBUTE_SPACE, ruby.Alignment);
        msAssert.areEqual(Language.CHINESE_CHINA, ruby.Language);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJira4136SourceDataProvider")
	public void unifiedTestJira4136Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJira4136(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJira4136SourceDataProvider")
	Object[][] unifiedTestJira4136SourceDataProvider() throws Exception
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
    /// WORDSNET-4136 Ruby (EQ fields) are lost after open/save.
    /// </summary>
    /// <remarks>
    /// AM. Save using SaveFormat.Text is original WORDSNET-4136 customer request.
    /// </remarks>
    @Test
    public void testJira4136ToText() throws Exception
    {
        Document doc = TestUtil.open("Model\\Run\\Text\\Ruby\\TestJira4136.docx");

        MemoryStream stream = new MemoryStream();
        doc.save(stream, SaveFormat.TEXT);

        stream.setPosition(0);
        TextReader reader = new StreamReader(stream);
        String text = reader.readToEnd();

        msAssert.areEqual("Phonetic Guide: 1(wun)\t2(too)\t4(fow er)\t8(ait)\r\n\r\n", text);
    }

    /// <summary>
    /// Relates to WORDSNET-4136 Tests various alignment.
    /// </summary>
    @Test
    public void testRubyAlignment() throws Exception
    {
        // FOSS: Doc writer removed; FlatOpc also preserves ruby through the roundtrip (input is .fopc).
        Document doc = TestUtil.openSaveOpen("Model\\Run\\Text\\Ruby\\TestRubyAlignment",
            TestUtil.getUnifiedScenario(LoadFormat.FLAT_OPC, SaveFormat.FLAT_OPC) | UnifiedScenario.NO_GOLD);

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        Ruby ruby = (Ruby)paras.get(1).getFirstRun().getRunPr().get(FontAttr.RUBY);
        msAssert.areEqual(RubyAlignment.CENTER, ruby.Alignment);

        ruby = (Ruby)paras.get(4).getFirstRun().getRunPr().get(FontAttr.RUBY);
        msAssert.areEqual(RubyAlignment.DISTRIBUTE_LETTER, ruby.Alignment);

        ruby = (Ruby)paras.get(7).getFirstRun().getRunPr().get(FontAttr.RUBY);
        msAssert.areEqual(RubyAlignment.DISTRIBUTE_SPACE, ruby.Alignment);

        ruby = (Ruby)paras.get(10).getFirstRun().getRunPr().get(FontAttr.RUBY);
        msAssert.areEqual(RubyAlignment.LEFT, ruby.Alignment);

        ruby = (Ruby)paras.get(13).getFirstRun().getRunPr().get(FontAttr.RUBY);
        msAssert.areEqual(RubyAlignment.RIGHT, ruby.Alignment);

        ruby = (Ruby)paras.get(16).getFirstRun().getRunPr().get(FontAttr.RUBY);
        msAssert.areEqual(RubyAlignment.RIGHT_VERTICAL, ruby.Alignment);
    }

    /// <summary>
    /// Tests that EQ field is completely removed after document is saved.
    /// </summary>
    @Test
    public void testRubyConversionReverted() throws Exception
    {
        Document doc = TestUtil.open("Model\\Run\\Text\\Ruby\\TestSimpleRuby.xml");

        // FOSS: Doc writer removed; Markdown (NonMSWordFormat) runs the same ruby->field convert/revert.
        doc.save(new MemoryStream(), SaveFormat.MARKDOWN);

        // Verify that there is still only one empty run with ruby collection.
        Paragraph p = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(1, p.getRuns().getCount());
        msAssert.areEqual(0, p.getFirstRun().getTextLength());
        msAssert.isTrue(p.getFirstRun().getRunPr().contains(FontAttr.RUBY));
    }

    /// <summary>
    /// WORDSNET-11935 EQ field is lost after saving document
    /// </summary>
    @Test
    public void testJira11935() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Run\\Text\\Ruby\\TestJira11935", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        Ruby ruby = (Ruby)doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getRunPr().get(FontAttr.RUBY);

        msAssert.areEqual(14, ruby.BaseSize);
        msAssert.areEqual(12, ruby.TopSize);
        msAssert.areEqual("yorosii", ruby.getTop().getText());
        msAssert.areEqual("よろしい", ruby.getBase().getText());
    }




    /// <summary>
    /// WORDSNET-12559 Need support for ruby with multiple base and top parts and each part having its own formatting.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestJira12559(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Run\\Text\\Ruby\\TestJira12559", TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD);

        Run rubyRun = doc.getFirstSection().getBody().getFirstParagraph().getFirstRun();

        Ruby ruby = ms.as(rubyRun.getRunPr().get(FontAttr.RUBY), Ruby.class);
        msAssert.isNotNull(ruby);
        msAssert.areEqual(22, ruby.BaseSize);
        msAssert.areEqual(24, ruby.TopSize);
        msAssert.areEqual(22, ruby.Distance);

        msAssert.areEqual(2, ruby.getTop().getCount());
        msAssert.areEqual(2, ruby.getBase().getCount());

        RunPr runPr = ruby.getTop().get(0).RunPr;
        msAssert.areEqual(DrColor.fromArgb(0x00, 0xff, 0x00), runPr.get(FontAttr.COLOR));
        msAssert.isNotNull(runPr.get(FontAttr.BOLD));
        msAssert.isNotNull(runPr.get(FontAttr.ITALIC));
        msAssert.areEqual(Underline.SINGLE, runPr.get(FontAttr.UNDERLINE));
        msAssert.areEqual(144, runPr.get(FontAttr.SIZE));
        msAssert.areEqual(144, runPr.get(FontAttr.SIZE_BI));
        msAssert.areEqual(Underline.SINGLE, runPr.get(FontAttr.UNDERLINE));

        runPr = ruby.getTop().get(1).RunPr;
        msAssert.areEqual(DrColor.fromArgb(0x00, 0x00, 0xff), runPr.get(FontAttr.COLOR));
        msAssert.isNotNull(runPr.get(FontAttr.BOLD));
        msAssert.isNotNull(runPr.get(FontAttr.ITALIC));

        runPr = ruby.getBase().get(0).RunPr;
        msAssert.areEqual(DrColor.fromArgb(0xff, 0x00, 0x00), runPr.get(FontAttr.COLOR));
        msAssert.isNotNull(runPr.get(FontAttr.BOLD));
        msAssert.isNotNull(runPr.get(FontAttr.ITALIC));
        msAssert.areEqual(Underline.SINGLE, runPr.get(FontAttr.UNDERLINE));
        msAssert.areEqual(72, runPr.get(FontAttr.SIZE));
        msAssert.areEqual(72, runPr.get(FontAttr.SIZE_BI));
        msAssert.areEqual(Underline.SINGLE, runPr.get(FontAttr.UNDERLINE));

        runPr = ruby.getBase().get(1).RunPr;
        msAssert.areEqual(DrColor.fromArgb(0x00, 0xff, 0x00), runPr.get(FontAttr.COLOR));
        msAssert.isNull(runPr.get(FontAttr.BOLD));
        msAssert.isNull(runPr.get(FontAttr.ITALIC));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJira12559SourceDataProvider")
	public void unifiedTestJira12559Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJira12559(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJira12559SourceDataProvider")
	Object[][] unifiedTestJira12559SourceDataProvider() throws Exception
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

    // FOSS: TestJira12559FontName removed. It asserted the ruby->EQ-field representation, which only the
    // removed legacy writers (Doc/Rtf/WordML) produce; supported formats (Markdown/Text) flatten ruby to
    // plain text, so the EQ-field field code cannot be produced in FOSS.



    /// <summary>
    /// Tests that ruby constructed properly when text is split into few runs.
    /// </summary>
    @Test
    public void testJira13920A() throws Exception
    {
        checkRuby(TestUtil.open("Model\\Run\\Text\\Ruby\\TestRubyTextSplit.xml"), 0, "amorozov", "morzal");
    }

    private static void checkRuby(Document doc, int idx, String baseText, String topText)
    {
        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);

        // Look for Nth ruby
        int cur = 0;
        Ruby ruby = null;
        for (Run run : (Iterable<Run>) runs)
        {
            if (!run.getRunPr().contains(FontAttr.RUBY))
                continue;

            if (idx == cur)
            {
                ruby = (Ruby) run.getRunPr().get(FontAttr.RUBY);
                break;
            }

            cur++;
        }

        msAssert.isNotNull(ruby);
        msAssert.areEqual(baseText, ruby.getBase().getText());
        msAssert.areEqual(topText, ruby.getTop().getText());
    }


    /// <summary>
    /// WORDSNET-15005 x-none language is not supported for DOCX
    /// Added support for 'x-none' (LanguageNotSet) value.
    /// </summary>
    /// <remarks>
    /// Implementation should be simpler but I found that NrxRunEnum is used in PDF export.
    /// I tested produced PDFs and Acrobat Reader opens documents with 'x-none' language but I'm afraid
    /// if any 3rd party will not able to open such files. So I decided to isolate 'x-none' language to DOCX import/export only.
    /// </remarks>
    @Test
    public void testJira15005() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Run\\Font\\TestJira15005", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        msAssert.areEqual(Language.LANGUAGE_NOT_SET, (/*Language*/Integer)doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getRunPr().get(FontAttr.LOCALE_ID));
    }





    /// <summary>
    /// Additional test for WORDSNET-13910 Tests that Ruby has 30 chars limit for its parts.
    /// </summary>
    @Test
    public void testJira13910C() throws Exception
    {
        Document doc = TestUtil.open("Model\\Run\\Text\\Ruby\\TestJira13910C.xml");

        // First part of very long ruby is moved to separate run before.
        Run run = doc.getFirstSection().getBody().getFirstParagraph().getRuns().get(0);
        msAssert.areEqual("123456789123456", run.getText());
        msAssert.isNull(run.getRunPr().get(FontAttr.RUBY));

        // Only 30 characters are left in ruby top.
        Ruby ruby = (Ruby)doc.getFirstSection().getBody().getFirstParagraph().getRuns().get(1).getRunPr().get(FontAttr.RUBY);
        msAssert.areEqual("789123456789123456789123456789", ruby.getTop().getText());
    }



    /// <summary>
    /// WORDSNET-15827 DOCX to PNG conversion issue with text formatting.
    /// Positioning in the document is incorrect because length of "Ruby" run determines as zero.
    /// Change calculation of the node length in <see cref="DocumentPosition"/> to fix the problem.
    /// </summary>
    @Test
    public void testJira15827() throws Exception
    {
        Document doc = TestUtil.open("Model\\Run\\Text\\Ruby\\TestJira15827.docx");
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        Run firstRunInRunCollection = para.getRuns().get(0);

        NodeRange range = new NodeRange(para, para);
        Run firstRunInNodeRange = null;

        for (Node node : range)
        {
            if (node.getNodeType() == NodeType.RUN)
            {
                firstRunInNodeRange = (Run)node;
                break;
            }
        }

        msAssert.isTrue(firstRunInRunCollection.equals(firstRunInNodeRange));
    }



    // FOSS: removed Test16734 (WORDSNET-16734). It asserted font-specific Font.LineSpacing
    // values (Calibri vs bidi vs Segoe UI Semibold); FOSS resolves every font to the last-resort
    // font, so font-specific line-spacing metrics no longer exist.


    /// <summary>
    /// Simplified case for WORDSNET-20144
    /// </summary>
    @Test
    public void test20144A()
    {
        RunPr runPr1 = new RunPr();
        runPr1.setAttr(FontAttr.BOLD, AttrBoolEx.TOGGLE);

        RunPr runPr2 = AttrCollectionExtension.deepClone(runPr1);

        runPr1.expandTo(runPr2);        // Must not throw.

        msAssert.areEqual(AttrBoolEx.TRUE, runPr2.get(FontAttr.BOLD));
    }

    /// <summary>
    /// WORDSNET-13983 Add feature to support 'Emphasis Mark' font setting.
    /// </summary>
    @Test
    public void test13983() throws Exception
    {
        final String TEST_NAME = "Model\\Run\\Font\\Test13983";

        Document doc = TestUtil.open(TEST_NAME + ".docx");

        Run run = doc.getFirstSection().getBody().getFirstParagraph().getRuns().get(2);
        msAssert.areEqual(EmphasisMark.NONE, run.getFont().getEmphasisMark());
        run.getFont().setEmphasisMark(EmphasisMark.OVER_WHITE_CIRCLE);
        msAssert.areEqual(EmphasisMark.OVER_WHITE_CIRCLE, run.getFont().getEmphasisMark());
    }

    /// <summary>
    /// Case for WORDSNET-13983
    /// Check the emphasis mark inheritance from the specified style.
    /// </summary>
    @Test
    public void test13983_EmphasisStyle() throws Exception
    {
        Document doc = new Document();

        Style style = doc.getStyles().getByIstd(StyleIndex.NORMAL, false);
        style.getFont().setEmphasisMark(EmphasisMark.OVER_WHITE_CIRCLE);

        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        para.getParagraphFormat().setStyle(style);
        Run run = para.appendChild(new Run(doc, "Emphasis"));

        msAssert.areEqual(EmphasisMark.OVER_WHITE_CIRCLE, run.getFont().getEmphasisMark());
    }




    /// <summary>
    /// WORDSNET-24973 Ability to determine whether run is ruby text.
    /// Added new public property <see cref="Run.IsPhoneticGuide"/>.
    /// </summary>
    @Test
    public void test24973() throws Exception
    {
        Document doc = TestUtil.open("Model\\Run\\Text\\Ruby\\Test24973.xml");

        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.isTrue(para.getRuns().get(0).isPhoneticGuide());
        msAssert.isFalse(para.getRuns().get(1).isPhoneticGuide());
    }

    /// <summary>
    /// WORDSNET-25173 Programmatically get both base text and annotation of Run nodes that contain ruby text.
    /// Added new corresponding public properties.
    /// </summary>
    @Test
    public void test25173() throws Exception
    {
        Document doc = TestUtil.open("Model\\Run\\Text\\Ruby\\Test25713.docx");
        Run run = doc.getFirstSection().getBody().getFirstParagraph().getFirstRun();
        msAssert.areEqual("", run.getText());
        msAssert.areEqual("base", run.getPhoneticGuide().getBaseText());
        msAssert.areEqual("ruby", run.getPhoneticGuide().getRubyText());
    }

    /// <summary>
    /// Relates to WORDSNET-25173
    /// Tests when Run is not a phonetic guide.
    /// </summary>
    @Test
    public void test25173NotRuby() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        builder.writeln("text");

        Run run = builder.getDocument().getFirstSection().getBody().getFirstParagraph().getFirstRun();
        msAssert.areEqual("text", run.getText());
        msAssert.isFalse(run.isPhoneticGuide());
        msAssert.areEqual("", run.getPhoneticGuide().getBaseText());
        msAssert.areEqual("", run.getPhoneticGuide().getRubyText());
    }


    /// <summary>
    /// Relates to  WORDSNET-25856
    /// Tests getting the effective font name from rFonts.
    /// </summary>
    @Test
    public void test25856_Fonts() throws Exception
    {
        final String EXP_FONT_NAMES = "DaunPenh,DaunPenh,Tahoma,Tahoma,MoolBoran,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran," +
            "PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran," +
            "PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran,MoolBoran,PMingLiU," +
            "MoolBoran,MoolBoran,PMingLiU,MoolBoran,MoolBoran,PMingLiU,MoolBoran,MoolBoran,PMingLiU,MoolBoran,MoolBoran,PMingLiU," +
            "MoolBoran,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran," +
            "PMingLiU,MoolBoran,PMingLiU,MoolBoran,Tahoma,Tahoma,Tahoma,Tahoma,Tahoma,PMingLiU,PMingLiU,PMingLiU,MoolBoran," +
            "MoolBoran,PMingLiU,MoolBoran,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran," +
            "PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran," +
            "PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,MoolBoran," +
            "PMingLiU,MoolBoran,PMingLiU,MoolBoran,PMingLiU,PMingLiU,PMingLiU,PMingLiU,PMingLiU,PMingLiU,PMingLiU,PMingLiU," +
            "PMingLiU,PMingLiU,PMingLiU,PMingLiU,PMingLiU,PMingLiU,PMingLiU,PMingLiU,PMingLiU,PMingLiU,Tahoma,Tahoma,Tahoma";

        Document doc = TestUtil.open("Model\\Run\\Font\\Test25856_Fonts.docx");

        // Tests getting a font name from an empty Run.
        msAssert.areEqual("Tahoma", doc.getFirstSection().getBody().getFirstParagraph().getParagraphBreakFont().getName());

        ArrayList<String> fontNames = new ArrayList<String>();
        for (Paragraph para : (Iterable<Paragraph>) doc.getFirstSection().getBody().getParagraphs())
            for (Run run : (Iterable<Run>) para.getRuns())
                // We ignore font comments here.
                if (run.getText().length() < 3)
                    msArrayList.add(fontNames, run.getFont().getName());

        msAssert.areEqual(EXP_FONT_NAMES, String.join(",", fontNames));
    }

    /// <summary>
    /// WORDSNET-25882 Wk: How can we determine whether the text inside a shape is white or black?
    /// Shape style was not taken into account when resolving font color of a run located in a shape.
    /// </summary>
    @Test
    public void test25882() throws Exception
    {
        Document doc = TestUtil.open("Model\\Run\\Font\\Test25882.docx");
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();

        Paragraph paragraph1 = shapes.get(0).getFirstParagraph();
        msAssert.areEqual("Black text in shape 2\r", paragraph1.getText());
        msAssert.areEqual(new Color((0), (0), (0)), paragraph1.getFirstRun().getFont().getColor());

        Paragraph paragraph2 = shapes.get(1).getFirstParagraph();
        msAssert.areEqual("white text in shape 1\r", paragraph2.getText());
        msAssert.areEqual(new Color((0xff), (0xff), (0xff)), paragraph2.getFirstRun().getFont().getColor());
    }


    /// <summary>
    /// WORDSNET-26904 Consider exposing FontAttr.OpenTypeNumSpacing.
    /// Tests new public API Font.NumberSpacing.
    /// </summary>
    @Test
    public void test26904() throws Exception
    {
        Document doc = TestUtil.open("Model\\Run\\Font\\Test26904", LoadFormat.DOCX);

        RunCollection coll = doc.getFirstSection().getBody().getFirstParagraph().getRuns();
        Run run1 = coll.get(0);
        Run run2 = coll.get(1);
        Run run3 = coll.get(2);
        msAssert.areEqual(NumSpacing.DEFAULT, run1.getFont().getNumberSpacing());
        msAssert.areEqual(NumSpacing.PROPORTIONAL, run2.getFont().getNumberSpacing());
        msAssert.areEqual(NumSpacing.TABULAR, run3.getFont().getNumberSpacing());

        run1.getFont().setNumberSpacing(NumSpacing.PROPORTIONAL);
        run2.getFont().setNumberSpacing(NumSpacing.TABULAR);
        run3.getFont().setNumberSpacing(NumSpacing.DEFAULT);

        msAssert.areEqual(NumSpacing.PROPORTIONAL, run1.getFont().getNumberSpacing());
        msAssert.areEqual(NumSpacing.TABULAR, run2.getFont().getNumberSpacing());
        msAssert.areEqual(NumSpacing.DEFAULT, run3.getFont().getNumberSpacing());
    }

    /// <summary>
    /// Checks that run has only Thai characters and forced to be Thai by certain attributes.
    /// </summary>
    private static void checkThai(Run run)
    {
        for (int i = 0; i < run.getText().length(); i++)
        {
            char ch = run.getText().charAt(i);
            msAssert.isTrue(UnicodeUtil.isThaiCharacter(ch));
        }

        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().get(FontAttr.COMPLEX_SCRIPT));
        msAssert.areEqual(Language.THAI_THAILAND, (/*Language*/Integer)run.getRunPr().get(FontAttr.LOCALE_ID_BI));
    }

    /// <summary>
    /// Checks that run has no Thai characters and not forced to be Thai.
    /// </summary>
    private static void checkNotThai(Run run)
    {
        for (int i = 0; i < run.getText().length(); i++)
        {
            char ch = run.getText().charAt(i);
            msAssert.isFalse(UnicodeUtil.isThaiCharacter(ch));
        }

        msAssert.isNull(run.getRunPr().get(FontAttr.COMPLEX_SCRIPT));
        msAssert.isNull(run.getRunPr().get(FontAttr.LOCALE_ID_BI));
    }

    private static void checkStyleAttributes(Document doc)
    {
        checkStyleAttributes(doc, "Heading 1", AttrBoolEx.TRUE, null);
        checkStyleAttributes(doc, "Heading 4", AttrBoolEx.TRUE, AttrBoolEx.TRUE);
        checkStyleAttributes(doc, "Strong", AttrBoolEx.TRUE, null);
        checkStyleAttributes(doc, "Emphasis", null, AttrBoolEx.TRUE);
        checkStyleAttributes(doc, "Intense Emphasis", AttrBoolEx.TRUE, AttrBoolEx.TRUE);
    }

    private static void checkStyleAttributes(Document doc, String styleName, AttrBoolEx bold, AttrBoolEx italic)
    {
        Style style = doc.getStyles().get(styleName);
        checkStyleAttribute(style, FontAttr.BOLD, bold);
        checkStyleAttribute(style, FontAttr.ITALIC, italic);
    }

    private static void checkStyleAttribute(Style style, int attr, AttrBoolEx expectedValue)
    {
        AttrBoolEx styleAttrValue = (AttrBoolEx)style.getFontAttr(attr, false);
        msAssert.areEqual(expectedValue, styleAttrValue);
    }

    private static void testCombinedAttributes(Document doc)
    {
        NodeCollection runs = doc.getFirstSection().getBody().getChildNodes(NodeType.RUN, true);

        int runCounter = 0;

        Run run = (Run)runs.get(runCounter);
        checkCombinedRunAttributes(run, "JustHeading1 ", true, false);

        run = (Run)runs.get(++runCounter);
        checkCombinedRunAttributes(run, "Heading1PlusStrong ", false, false);

        run = (Run)runs.get(++runCounter);
        checkCombinedRunAttributes(run, "Heading1PlusEmphasis ", true, true);

        run = (Run)runs.get(++runCounter);
        checkCombinedRunAttributes(run, "Heading1PlusIntenseEmphasis ", false, true);

        run = (Run)runs.get(++runCounter);
        checkCombinedRunAttributes(run, "JustHeading4 ", true, true);

        run = (Run)runs.get(++runCounter);
        checkCombinedRunAttributes(run, "Heading4PlusStrong ", false, true);

        run = (Run)runs.get(++runCounter);
        checkCombinedRunAttributes(run, "Heading4PlusEmphasis ", true, false);

        run = (Run)runs.get(++runCounter);
        checkCombinedRunAttributes(run, "Heading4PlusIntenseEmphasis ", false, false);

        run = (Run)runs.get(++runCounter);
        checkCombinedRunAttributes(run, "NormalText ", false, false);

        run = (Run)runs.get(++runCounter);
        checkCombinedRunAttributes(run, "JustStrong ", true, false);

        run = (Run)runs.get(++runCounter);
        checkCombinedRunAttributes(run, "JustEmphasis ", false, true);

        run = (Run)runs.get(++runCounter);
        checkCombinedRunAttributes(run, "JustIntenseEmphasis ", true, true);
    }

    private static void checkCombinedRunAttributes(Run run, String expectedText, boolean bold, boolean italic)
    {
        msAssert.areEqual(expectedText, run.getText());
        msAssert.areEqual(bold, run.getFont().getBold(), "Unexpected Font.Bold value for {0}" + expectedText);
        msAssert.areEqual(italic, run.getFont().getItalic(), "Unexpected Font.Italic value for {0}" + expectedText);
    }
}

