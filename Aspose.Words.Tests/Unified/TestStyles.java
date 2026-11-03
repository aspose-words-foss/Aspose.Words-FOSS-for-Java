// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.Debug;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import com.aspose.DrColor;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for styles that do not fall into any more specific category.
/// </summary>
@Test
public class TestStyles extends UnifiedTestsBase
{


    /// <summary>
    /// category - Style
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestBuiltin2000(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        checkBuiltin(TestUtil.openSaveOpen("Model\\Style\\TestBuiltin2000", lf, sf), 12.0, 16.0);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBuiltin2000SourceDataProvider")
	public void unifiedTestBuiltin2000Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBuiltin2000(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBuiltin2000SourceDataProvider")
	Object[][] unifiedTestBuiltin2000SourceDataProvider() throws Exception
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
    /// category - Style
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestBuiltin2003(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        checkBuiltin(TestUtil.openSaveOpen("Model\\Style\\TestBuiltin2003", lf, sf), 12.0, 16.0);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBuiltin2003SourceDataProvider")
	public void unifiedTestBuiltin2003Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBuiltin2003(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBuiltin2003SourceDataProvider")
	Object[][] unifiedTestBuiltin2003SourceDataProvider() throws Exception
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
    /// category - Style
    /// </summary>
    @Test
    public void testBuiltin2007() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestBuiltin2007.docx");
        for (Style style : doc.getStyles())
            Debug.writeLine(style.getName() + ": " + style.getStyleIdentifier());
    }

    /// <summary>
    /// Basic check of some font and paragraph properties of built in styles.
    /// </summary>
    private static void checkBuiltin(Document doc, double normalFontSize, double h1FontSize)
    {
        StyleCollection styles = doc.getStyles();

        Style normal = styles.get("Normal");
        msAssert.areEqual(StyleType.PARAGRAPH, normal.getType());
        msAssert.areEqual("Normal", normal.getName());
        msAssert.areEqual("", normal.getBaseStyleName());
        msAssert.areEqual("Normal", normal.getNextParagraphStyleName());
        msAssert.areEqual("Times New Roman", normal.getFont().getName());
        msAssert.areEqual(normalFontSize, normal.getFont().getSize());
        msAssert.areEqual(false, normal.getParagraphFormat().getKeepWithNext());
        msAssert.areEqual(true, normal.getBuiltIn());

        Style h1 = styles.get("Heading 1");
        msAssert.areEqual("Normal", h1.getBaseStyleName());
        msAssert.areEqual("Normal", h1.getNextParagraphStyleName());
        msAssert.areEqual("Arial", h1.getFont().getName());
        msAssert.areEqual(h1FontSize, h1.getFont().getSize());
        msAssert.areEqual(true, h1.getParagraphFormat().getKeepWithNext());

        Style hyperlink = styles.get("Hyperlink");
        msAssert.areEqual(StyleType.CHARACTER, hyperlink.getType());
        msAssert.areEqual(Color.BLUE.getRGB(), hyperlink.getFont().getColor().getRGB());
    }

    /// <summary>
    /// Test how styles with aliases are loaded and saved.
    ///
    /// category - Style
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestAlias(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Style\\TestAlias", lf, sf);

        StyleCollection styles = doc.getStyles();

        Style normal = styles.get("Normal");
        msAssert.areEqual("Normal", normal.getName());
        msAssert.isNotNull(normal);

        Style normalAlias = styles.get("NormalA");
        msAssert.isNotNull(normalAlias);

        msAssert.areEqual(normal, normalAlias);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAliasSourceDataProvider")
	public void unifiedTestAliasSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAlias(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAliasSourceDataProvider")
	Object[][] unifiedTestAliasSourceDataProvider() throws Exception
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
    /// category - Style
    /// </summary>
    @Test
    public void testSetNextStyle() throws Exception
    {
        Document doc = new Document();

        // Requesting a built in style that does not yet exist,
        // created it automatically, but that's tested elsewhere.

        Style style = doc.getStyles().get("Heading 1");
        msAssert.areEqual("Normal", style.getNextParagraphStyleName());    //Check before we change
        style.setNextParagraphStyleName("Heading 2");
        msAssert.areEqual("Heading 2", style.getNextParagraphStyleName());    //Check after we change
        msAssert.areEqual(StyleIndex.HEADING_2, style.getNextIstd());
    }

    /// <summary>
    /// category - Style
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QCannot find style 'XXX'.\\E")
    public void testSetNextStyleNotFound() throws Exception
    {
        Document doc = new Document();
        Style style = doc.getStyles().get("Heading 1");
        style.setNextParagraphStyleName("XXX");
    }

    /// <summary>
    /// category - Style
    /// </summary>
    @Test
    public void testSetNextStyleEmpty() throws Exception
    {
        try
        {
            Document doc = new Document();
            Style style = doc.getStyles().get("Heading 1");
            style.setNextParagraphStyleName("");
            Assert.fail();
        }
        catch (IllegalArgumentException e)
        {
            msAssert.isTrue(e.getMessage().indexOf("The argument cannot be null or empty string.") >= 0);
        }
    }

    /// <summary>
    /// category - Style
    /// </summary>
    @Test
    public void testSetNextStyleNull() throws Exception
    {
        try
        {
            Document doc = new Document();
            Style style = doc.getStyles().get("Heading 1");
            style.setNextParagraphStyleName(null);
            Assert.fail();
        }
        catch (IllegalArgumentException e)
        {
            msAssert.isTrue(e.getMessage().indexOf("The argument cannot be null or empty string.") >= 0);
        }
    }

    /// <summary>
    /// Check setting base style succeeds.
    ///
    /// category - Style
    /// </summary>
    @Test
    public void testSetBaseStyle() throws Exception
    {
        Document doc = new Document();
        StyleCollection styles = doc.getStyles();
        Style h1 = styles.get("Heading 1");
        h1.setBaseStyleName("");
        msAssert.areEqual("", h1.getBaseStyleName());
    }

    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThe built-in styles Normal and Default Paragraph Font cannot be based on any style.\\E")
    public void testSetBaseStyleOfNormalStyle() throws Exception
    {
        Document doc = new Document();
        StyleCollection styles = doc.getStyles();
        Style normal = styles.get("Normal");
        normal.setBaseStyleName("");
    }

    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QCannot find style 'AAA'.\\E")
    public void testSetBaseStyleNotFound() throws Exception
    {
        Document doc = new Document();
        StyleCollection styles = doc.getStyles();
        Style h1 = styles.get("Heading 1");
        h1.setBaseStyleName("AAA");
    }

    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThis operation will create a circular reference between styles.\\E")
    public void testSetBaseStyleCircularSelf() throws Exception
    {
        Document doc = new Document();
        StyleCollection styles = doc.getStyles();
        styles.get("Heading 1").setBaseStyleName("Heading 1");
    }

    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThis operation will create a circular reference between styles.\\E")
    public void testSetBaseStyleCircularSelfCustom() throws Exception
    {
        Document doc = new Document();
        Style style = doc.getStyles().add (StyleType.PARAGRAPH, "TOC 1");
        style.setBaseStyleName("TOC 1");
    }

    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThis operation will create a circular reference between styles.\\E")
    public void testSetBaseStyleCircularSimple() throws Exception
    {
        Document doc = new Document();
        StyleCollection styles = doc.getStyles();
        styles.get("Heading 1").setBaseStyleName("Heading 2");
        styles.get("Heading 2").setBaseStyleName("Heading 1");
    }

    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThis operation will create a circular reference between styles.\\E")
    public void testSetBaseStyleCircularComplex() throws Exception
    {
        Document doc = new Document();
        StyleCollection styles = doc.getStyles();
        styles.get("Heading 2").setBaseStyleName("Heading 1");
        styles.get("Heading 3").setBaseStyleName("Heading 2");
        styles.get("Heading 1").setBaseStyleName("Heading 3");
    }

    /// <summary>
    /// Check that a simple value inheritance works in styles for Font and ParagraphFormat.
    ///
    /// category - Style
    /// </summary>
    @Test
    public void testSimpleInheritance() throws Exception
    {
        Document doc = new Document();
        StyleCollection styles = doc.getStyles();

        Style aaa = styles.add(StyleType.PARAGRAPH, "AAA");
        msAssert.areEqual("", aaa.getBaseStyleName());

        //Check default attribute values are visible.
        msAssert.areEqual(10d, aaa.getFont().getSize());
        msAssert.areEqual(0d, aaa.getParagraphFormat().getSpaceBefore());

        //Base the style on another style and check the value is visible.
        aaa.setBaseStyleName("Heading 1");
        // FOSS RK I don't know why the value reported now is 14, whereas it was 16.
        // It obviously has to do with the migration from Blank.doc to Blank.docx, but check back later.
        // Space before went from 12 to 24 as part of that too.
        msAssert.areEqual(14d, aaa.getFont().getSize());
        msAssert.areEqual(24d, aaa.getParagraphFormat().getSpaceBefore());

        //Change the attr value in the base style and check it propagates to the derived style.
        styles.get("Heading 1").getFont().setSize(8.0);
        msAssert.areEqual(8d, aaa.getFont().getSize());
        styles.get("Heading 1").getParagraphFormat().setSpaceBefore(11.0);
        msAssert.areEqual(11d, aaa.getParagraphFormat().getSpaceBefore());

        //Change the attr value in the derived style and check it does not affect the parent style.
        aaa.getFont().setSize(4.0);
        msAssert.areEqual(4d, aaa.getFont().getSize());
        aaa.getParagraphFormat().setSpaceBefore(10.0);
        msAssert.areEqual(10d, aaa.getParagraphFormat().getSpaceBefore());
        msAssert.areEqual(8d, styles.get("Heading 1").getFont().getSize());
    }

    /// <summary>
    /// Checks how toggle value for bold etc inheritance works in paragraph styles.
    ///
    /// category - Style
    /// </summary>
    @Test
    public void testToggleInheritance() throws Exception
    {
        Document doc = new Document();
        StyleCollection styles = doc.getStyles();

        Style normal = styles.get("Normal");

        Style h1 = styles.get("Heading 1");
        h1.getRunPr().setBold(AttrBoolEx.TOGGLE);

        Style h2 = styles.get("Heading 2");
        h2.getRunPr().setBold(AttrBoolEx.TOGGLE);

        msAssert.areEqual(false, normal.getFont().getBold());    // Default value works okay.
        msAssert.areEqual(true, h1.getFont().getBold());        // Toggle once value works okay.
        msAssert.areEqual(true, h2.getFont().getBold());        // Toggle once value works okay.

        h2.setBaseStyleName("Heading 1");
        msAssert.areEqual(false, h2.getFont().getBold());        // Now this is a toggle twice value and works okay.

        h1.getFont().setBold(false);                        // Change the value in the parent.
        msAssert.areEqual(false, h1.getFont().getBold());        // Explicit value works okay.
        msAssert.areEqual(true, h2.getFont().getBold());        // Toggle once value works okay.
    }

    /// <summary>
    /// Test how a complex attribute Font.Border on style is inherited and instantiated on demand.
    ///
    /// category - Style
    /// </summary>
    @Test
    public void testFontBorderOnStyleInheritance() throws Exception
    {
        Document doc = new Document();
        StyleCollection styles = doc.getStyles();

        Style normal = styles.get("Normal");
        Style h1 = styles.get("Heading 1");
        Style h2 = styles.get("Heading 2");

        //Test defaults as created. This instantiates a new Border object in the "inherit from parent" state.
        msAssert.areEqual(LineStyle.NONE, normal.getFont().getBorder().getLineStyle());

        //Change the border in H1. This changes the Border in H1 from inherited to explicit state.
        h1.getFont().getBorder().setLineStyle(LineStyle.SINGLE);
        //Make sure it was changed in H1, but not changed in the parent.
        msAssert.areEqual(LineStyle.SINGLE, h1.getFont().getBorder().getLineStyle());
        msAssert.areEqual(LineStyle.NONE, normal.getFont().getBorder().getLineStyle());

        msAssert.areEqual(LineStyle.NONE, h2.getFont().getBorder().getLineStyle());
        //Make H2 based on H1 and observe H2 inheriting the Border from H1.
        h2.setBaseStyleName("Heading 1");
        msAssert.areEqual(LineStyle.SINGLE, h2.getFont().getBorder().getLineStyle());
        //Change Border in H1 once again and observe it is still inherited in H2.
        h1.getFont().getBorder().setLineStyle(LineStyle.DOUBLE);
        msAssert.areEqual(LineStyle.DOUBLE, h2.getFont().getBorder().getLineStyle());
    }

    /// <summary>
    /// Test how a complex attribute Font.Border on style is cloned from the parent
    /// when it is modified from inherited to explicit.
    ///
    /// category - Style
    /// </summary>
    @Test
    public void testFontBorderOnStyleCloneBeforeModify() throws Exception
    {
        Document doc = new Document();
        StyleCollection styles = doc.getStyles();

        Style normal = styles.get("Normal");
        Style h1 = styles.get("Heading 1");

        //Let's say we have a border on the base style.
        normal.getFont().getBorder().setLineStyle(LineStyle.DOUBLE);
        normal.getFont().getBorder().setLineWidth(10.0);

        //This request actually instantiates an inherited Border on H1,
        //but no physical copying of the parent border takes place yet.
        msAssert.areEqual(10d, h1.getFont().getBorder().getLineWidth());

        //This later helps to see that the border has not been copied to H1 yet.
        normal.getFont().getBorder().setLineWidth(5.0);

        //Change one of the border properties on the child style.
        //This causes the Border to copy from the base to the child style.
        h1.getFont().getBorder().setLineStyle(LineStyle.SINGLE);
        msAssert.areEqual(5d, h1.getFont().getBorder().getLineWidth());

        //Now change a property in the parent style and observe that it no longer has effect on the child border.
        normal.getFont().getBorder().setLineWidth(2.0);
        msAssert.areEqual(5d, h1.getFont().getBorder().getLineWidth());
    }

    /// <summary>
    /// Shows there is a difference when obtaining font attributes from character as opposed to paragraph style.
    ///
    /// category - Style
    /// </summary>
    @Test
    public void testFontOnCharVsParaStyle() throws Exception
    {
        Document doc = new Document();
        StyleCollection styles = doc.getStyles();

        Style p = styles.getByStyleIdentifier(StyleIdentifier.NORMAL);
        Style c = styles.getByStyleIdentifier(StyleIdentifier.DEFAULT_PARAGRAPH_FONT);

        //For public access, both char and para styles will resolve using default values.
        msAssert.areEqual(p.getFont().getSize(), 12d);
        msAssert.areEqual(c.getFont().getSize(), 10d);

        //However for internal purposes, para style will use default values, but char styles will not.
        //This resolves up all base styles and a default value.
        msAssert.areEqual(12 * 2, p.getFontAttr(FontAttr.SIZE, true));
        //This resolves all base styles, but not a default value.
        msAssert.areEqual(null, c.getFontAttr(FontAttr.SIZE, false));
    }


    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestBlank(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Style\\TestBlank", lf, sf);
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(11.0, para.getParagraphBreakFont().getSize());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBlankSourceDataProvider")
	public void unifiedTestBlankSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBlank(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBlankSourceDataProvider")
	Object[][] unifiedTestBlankSourceDataProvider() throws Exception
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
    /// Test that expanding font and paragraph formatting works.
    ///
    /// category - Style
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestExpandStyleFormatting(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Style\\TestExpandStyleFormatting", lf, sf);

        ParaPr paraPr = new ParaPr();
        doc.getStyles().get("Heading 1").expandParaPr(paraPr, ParaPrExpandFlags.NORMAL);

        ParagraphFormat pf = new ParagraphFormat(paraPr, null);
        msAssert.areEqual(ParagraphAlignment.RIGHT, pf.getAlignment());    //This is expanded from Normal
        msAssert.areEqual(12d, pf.getSpaceBefore());                        //This is overridden in Heading 1
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestExpandStyleFormattingSourceDataProvider")
	public void unifiedTestExpandStyleFormattingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestExpandStyleFormatting(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestExpandStyleFormattingSourceDataProvider")
	Object[][] unifiedTestExpandStyleFormattingSourceDataProvider() throws Exception
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
    public void testNoFontInListStyle() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestListStyle.docx");
        Style style = doc.getStyles().getByStyleIdentifier(StyleIdentifier.OUTLINE_LIST_1);
        // This throws because it is a list style.
        msAssert.areEqual(null, style.getFont());
    }

    @Test
    public void testNoListInParagraphStyle() throws Exception
    {
        Document doc = new Document();
        Style style = doc.getStyles().getByStyleIdentifier(StyleIdentifier.NORMAL);
        // This throws because it is not a list style.
        msAssert.areEqual(null, style.getList());
    }


    /// <summary>
    /// Test style id generation.
    ///
    /// category - style collection?
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void testWmlStyleIdGeneration(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Style\\TestWmlStyleIdGeneration", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "testWmlStyleIdGenerationSourceDataProvider")
	public void testWmlStyleIdGenerationSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		testWmlStyleIdGeneration(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "testWmlStyleIdGenerationSourceDataProvider")
	Object[][] testWmlStyleIdGenerationSourceDataProvider() throws Exception
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
    /// category - Style
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestBehaviorProperties(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Style\\TestBehaviorProperties", lf, sf);

        checkBehaviorProperties(doc, "Normal", false, 0, false, false, true, false, false, lf, sf);
        checkBehaviorProperties(doc, "Locked", true, 0, false, false, true, false, false, lf, sf);
        checkBehaviorProperties(doc, "UI Priority 99", false, 98, false, false, true, false, false, lf, sf);
        checkBehaviorProperties(doc, "UI Priority Last", false, 99, false, false, true, false, false, lf, sf);
        checkBehaviorProperties(doc, "Semi Hidden", false, 0, true, false, true, false, false, lf, sf);
        checkBehaviorProperties(doc, "Unhide When Used", false, 0, false, true, true, false, false, lf, sf);
        checkBehaviorProperties(doc, "Auto Redefine", false, 0, false, false, true, false, true, lf, sf);
        checkBehaviorProperties(doc, "No Quick Format", false, 0, false, false, false, false, false, lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBehaviorPropertiesSourceDataProvider")
	public void unifiedTestBehaviorPropertiesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBehaviorProperties(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBehaviorPropertiesSourceDataProvider")
	Object[][] unifiedTestBehaviorPropertiesSourceDataProvider() throws Exception
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

    private static void checkBehaviorProperties(
        Document doc,
        String styleName,
        boolean isLocked,
        int uiPriority,
        boolean isSemiHidden,
        boolean isUnhideWhenUsed,
        boolean isQuickFormat,
        boolean isHidden,
        boolean isAutoRedefine,
        /*LoadFormat*/int lf,
        /*SaveFormat*/int sf)
    {
        switch (TestUtil.getUnifiedScenario(lf, sf))
        {
            case UnifiedScenario.DOC_2_DOC:
            case UnifiedScenario.DOC_2_DOCX:
            case UnifiedScenario.DOC_2_RTF:
            case UnifiedScenario.DOCX_2_DOCX_NO_GOLD:
            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
                break;

            case UnifiedScenario.DOC_2_WML:
            case UnifiedScenario.WML_2_WML_NO_GOLD:
                // Exclude WML. It has no support for QuickFormat, SemiHidden, UnhideWhenUsed and UiPriority.
                return;

            default:
                throw new IllegalStateException("Unknown file format.");
        }

        Style style = doc.getStyles().get(styleName);
        msAssert.areEqual(isLocked, style.getLocked());
        msAssert.areEqual(uiPriority, style.getPriority());
        msAssert.areEqual(isSemiHidden, style.getSemiHidden());
        msAssert.areEqual(isUnhideWhenUsed, style.getUnhideWhenUsed());
        msAssert.areEqual(isQuickFormat, style.isQuickStyle());
        msAssert.areEqual(isHidden, style.getHidden());
        msAssert.areEqual(isAutoRedefine, style.getAutomaticallyUpdate());
    }

    /// <summary>
    /// category - latent styles
    /// </summary>
    @Test
    public void testLatentStylesClone() throws Exception
    {
        Document doc = new Document();
        LatentStyles latentStyles = doc.getStyles().getLatentStyles();
        LatentStyle latentStyle = new LatentStyle(StyleIdentifier.HYPERLINK, true, false, false, 9, true);
        latentStyles.add(latentStyle);

        Document clonedDoc = doc.deepClone();
        msAssert.isTrue(doc.getStyles().getLatentStyles() != clonedDoc.getStyles().getLatentStyles());
        LatentStyles clonedLatentStyles = clonedDoc.getStyles().getLatentStyles();
        msAssert.isTrue(latentStyles != clonedLatentStyles);
        msAssert.areEqual(latentStyles.getCount(), clonedLatentStyles.getCount());
        LatentStyle clonedLatentStyle = clonedLatentStyles.getByStyleIdentifier(StyleIdentifier.HYPERLINK);
        msAssert.areEqual(9, clonedLatentStyle.getUIPriority());
        msAssert.isTrue(latentStyle != clonedLatentStyle);
    }

    /// <summary>
    /// category - latent styles
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestLatentStyles(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // UI related style features are missing in WML format.
        if (sf == SaveFormat.WORD_ML)
            return;

        Document doc = TestUtil.openSaveOpen("Model\\Style\\TestLatentStyles", lf, sf);

        LatentStyles ls = doc.getStyles().getLatentStyles();

        // Check defaults.
        msAssert.areEqual(267, ls.getKnownStylesCount());
        msAssert.areEqual(false, ls.getDefaultLockedState());
        msAssert.areEqual(false, ls.getDefaultQuickFormat());
        msAssert.areEqual(true, ls.getDefaultSemiHidden());
        msAssert.areEqual(99, ls.getDefaultUIPriority());
        msAssert.areEqual(true, ls.getDefaultUnhideWhenUsed());

        switch (TestUtil.getUnifiedScenario(lf, sf))
        {
            case UnifiedScenario.DOC_2_DOC:
            case UnifiedScenario.DOC_2_DOCX:
            case UnifiedScenario.DOC_2_RTF:
            case UnifiedScenario.DOCX_2_DOCX_NO_GOLD:
            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
            case UnifiedScenario.DOCX_DML_2_DOCX_DML:
                // Check some lsds.
                msAssert.areEqual(137, ls.getCount());
                checkLatentStyle(ls, StyleIdentifier.NORMAL, false, true, false, 0, false);
                checkLatentStyle(ls, StyleIdentifier.MEDIUM_GRID_1_ACCENT_6, false, false, false, 67, false);
                msAssert.areEqual(null, ls.getByStyleIdentifier(StyleIdentifier.HYPERLINK));
                break;
            default:
                throw new IllegalStateException("Unknown file format.");
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLatentStylesSourceDataProvider")
	public void unifiedTestLatentStylesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLatentStyles(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLatentStylesSourceDataProvider")
	Object[][] unifiedTestLatentStylesSourceDataProvider() throws Exception
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

    private static void checkLatentStyle(LatentStyles ls, /*StyleIdentifier*/int sti, boolean locked, boolean quickFormat, boolean semiHidden, int uiPriority, boolean unhideWhenUsed)
    {
        LatentStyle latentStyle = ls.getByStyleIdentifier(sti);
        msAssert.areEqual(locked, latentStyle.getLocked());
        msAssert.areEqual(uiPriority, latentStyle.getUIPriority());
        msAssert.areEqual(semiHidden, latentStyle.getSemiHidden());
        msAssert.areEqual(unhideWhenUsed, latentStyle.getUnhideWhenUsed());
        msAssert.areEqual(quickFormat, latentStyle.getQuickStyle());
    }

    @Test
    public void testClonedFromStyleIdentifier() throws Exception
    {
        Document doc = new Document();

        Style hyperlinkStyle = doc.getStyles().getByStyleIdentifier(StyleIdentifier.HYPERLINK);
        Style clonedHyperlinkStyle = hyperlinkStyle.deepClone();

        msAssert.areEqual(StyleIdentifier.HYPERLINK, clonedHyperlinkStyle.getClonedFromStyleIdentifier());
    }

    /// <summary>
    /// Test creating document from scratch and checking BuildIn style.
    /// Expect Word 2003 styles by default.
    /// </summary>
    @Test
    public void testCreateDocumentFromScratch() throws Exception
    {
        Document doc = new Document();

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToDocumentEnd();
        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_1);
        builder.writeln("Heading 1");
        testStyle2003(doc);
    }

    /// <summary>
    /// Test importing BuildIn styles upon appending document.
    /// Expect Word 2007 styles in case of UseDestinationStyles specified. See WORDSNET-9188
    /// </summary>
    @Test
    public void testStyleImport() throws Exception
    {
        Document dstDoc = new Document();
        Document srcDoc = TestUtil.open("Model\\Style\\TestJira3826StyleImport.docx");

        dstDoc.appendDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES);

        dstDoc = TestUtil.saveOpen(dstDoc, "Model\\Style\\TestJira3826StyleImport.docx");
        testStyle2007(dstDoc, dstDoc.getLastSection().getBody().getFirstParagraph());
    }

    /// <summary>
    /// Test importing Docx to Doc.
    /// </summary>
    @Test
    public void testStyleImportDocxToDoc() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestJira3826StyleImport.docx");

        // FOSS No gold check (the Doc-era gold differs from the Docx roundtrip); TestStyle2007 is the point.
        doc = TestUtil.saveOpen(doc, "Model\\Style\\TestJira3826StyleImport.docx", (SaveOptions)null, false);
        testStyle2007(doc, doc.getFirstSection().getBody().getFirstParagraph());
    }

    /// <summary>
    /// WORDSNET-6467 Appending documents into empty (new Document()) stretches the contents of one page into multiple pages.
    /// andrnosk: The problem occurred because paragraphs (without directly specified spacing) inside tables inherited formatting from Defaults,
    /// but Table style was ignored. Fixed by expanding table style properties into paragraph.
    /// </summary>
    @Test
    public void testJira6467() throws Exception
    {
        Document dstDoc = new Document();
        dstDoc.removeAllChildren();
        Document srcDoc = TestUtil.open("Model\\Style\\TestJira6467.docx");

        dstDoc.appendDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        dstDoc = TestUtil.saveOpen(dstDoc, "Model\\Style\\TestJira6467.docx");

        Paragraph paragraph = dstDoc.getFirstSection().getBody().getTables().get(0).getRows().get(0).getCells().get(0).getFirstParagraph();
        msAssert.areEqual(0, paragraph.getParagraphFormat().getSpaceBefore());
        msAssert.areEqual(0, paragraph.getParagraphFormat().getSpaceAfter());
        msAssert.areEqual(LineSpacingRule.MULTIPLE, paragraph.getParagraphFormat().getLineSpacingRule());
        msAssert.areEqual(12.0d, paragraph.getParagraphFormat().getLineSpacing());
    }

    /// <summary>
    /// Check whether it is Word 2003 style.
    /// </summary>
    private static void testStyle2003(Document dstDoc)
    {
        msAssert.areEqual(StyleIdentifier.HEADING_1, dstDoc.getLastSection().getBody().getFirstParagraph().getParagraphFormat().getStyleIdentifier());
        // FOSS RK Arial 16 became Cambria 14 after I migrated from Blank.doc to Blank.docx. Check later.
        msAssert.areEqual("Cambria", dstDoc.getStyles().getByStyleIdentifier(StyleIdentifier.HEADING_1).getFont().getName());
        msAssert.areEqual(14.0d, dstDoc.getStyles().getByStyleIdentifier(StyleIdentifier.HEADING_1).getFont().getSize());
    }

    /// <summary>
    /// Check whether it is Word 2007 style.
    /// </summary>
    private static void testStyle2007(Document doc, Paragraph para)
    {
        msAssert.areEqual(StyleIdentifier.HEADING_1, para.getParagraphFormat().getStyleIdentifier());
        msAssert.areEqual("Cambria", doc.getStyles().getByStyleIdentifier(StyleIdentifier.HEADING_1).getFont().getName());
        msAssert.areEqual(14.0d, doc.getStyles().getByStyleIdentifier(StyleIdentifier.HEADING_1).getFont().getSize());
    }

    /// <summary>
    /// Test loading of all formats and checking BuildIn style.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestJira3826(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestJira3826", lf);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToDocumentEnd();
        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_1);
        builder.writeln("Heading 1");

        StyleCollection styles = doc.getStyles();
        Style h1 = styles.get("Heading 1");

        TestUtil.saveOpen(doc, "Model\\Style\\TestJira3826", lf, sf);

        switch (TestUtil.getUnifiedScenario(lf, sf))
        {
            case UnifiedScenario.DOC_2_DOC:
            case UnifiedScenario.DOC_2_DOCX:
            case UnifiedScenario.DOC_2_RTF:
            case UnifiedScenario.DOC_2_WML:
            case UnifiedScenario.WML_2_WML_NO_GOLD:
            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
                {
                    msAssert.areEqual("Arial", h1.getFont().getName());
                    msAssert.areEqual(16, h1.getFont().getSize());

                    ComplexFontName arial = ComplexFontName.fromName("Arial");
                    msAssert.areEqual(arial, h1.getRunPr().get(FontAttr.NAME_ASCII));
                    msAssert.areEqual(arial, h1.getRunPr().get(FontAttr.NAME_BI));
                    msAssert.isNull(h1.getRunPr().get(FontAttr.NAME_FAR_EAST));
                    msAssert.areEqual(arial, h1.getRunPr().get(FontAttr.NAME_OTHER));
                    break;
                }
            case UnifiedScenario.DOCX_2_DOCX_NO_GOLD:
                {
                    // NameAscii, NameBi, NameFarEast, NameOther are null,
                    // and ThemeAscii, ThemeBi, ThemeEastAsia, ThemeOther not null, it means font is specified through theme.
                    msAssert.areEqual(ComplexFontName.fromTheme(ThemeFontCore.MAJOR_H_ANSI), h1.getRunPr().get(FontAttr.NAME_ASCII));
                    msAssert.areEqual(ComplexFontName.fromTheme(ThemeFontCore.MAJOR_BIDI), h1.getRunPr().get(FontAttr.NAME_BI));
                    msAssert.areEqual(ComplexFontName.fromTheme(ThemeFontCore.MAJOR_EAST_ASIA), h1.getRunPr().get(FontAttr.NAME_FAR_EAST));
                    msAssert.areEqual(ComplexFontName.fromTheme(ThemeFontCore.MAJOR_H_ANSI), h1.getRunPr().get(FontAttr.NAME_OTHER));
                    break;
                }
            default:
                throw new IllegalStateException("Unknown file format.");
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJira3826SourceDataProvider")
	public void unifiedTestJira3826Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJira3826(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJira3826SourceDataProvider")
	Object[][] unifiedTestJira3826SourceDataProvider() throws Exception
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
    void unifiedTestRemoveParagraphStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        final String FILE_NAME = "Model\\Style\\RemoveParagraphStyle";
        Document doc = TestUtil.open(FILE_NAME, lf);

        Style oldStyle1 = doc.getStyles().get("Style1");
        // Style2 is a child of Style1.
        Style oldStyle2 = doc.getStyles().get("Style2");
        // Check all paragraph and run properties after collapse.
        if (lf == LoadFormat.DOC)
        {
            msAssert.areEqual(AttrBoolEx.TOGGLE, oldStyle1.getRunPr().getBold());
            msAssert.areEqual(AttrBoolEx.TOGGLE, oldStyle2.getRunPr().getItalic());
        }
        else
        {
            msAssert.areEqual(AttrBoolEx.TRUE, oldStyle1.getRunPr().getBold());
            msAssert.areEqual(AttrBoolEx.TRUE, oldStyle2.getRunPr().getItalic());
        }

        msAssert.areEqual(Underline.SINGLE, oldStyle1.getRunPr().getUnderline());
        msAssert.isFalse((/*boolean*/Boolean)oldStyle1.getParaPr().get(ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_ALPHA));
        msAssert.isFalse((/*boolean*/Boolean)oldStyle1.getParaPr().get(ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_DIGIT));
        msAssert.areEqual(1008, oldStyle1.getParaPr().getLeftIndent());

        // These properties were inherited from Style1.
        msAssert.isNull(oldStyle2.getParaPr().get(ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_ALPHA));
        msAssert.isNull(oldStyle2.getParaPr().get(ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_DIGIT));
        msAssert.areEqual(AttrBoolEx.FALSE, oldStyle2.getRunPr().getBold());

        doc.getStyles().remove(oldStyle1.getName());

        doc = TestUtil.saveOpen(doc, FILE_NAME, lf, sf);
        // Check that Style1 doesn't exist in StyleCollection.
        msAssert.isNull(doc.getStyles().get("Style1"));

        Style newStyle2 = doc.getStyles().get("Style2");

        // Check all properties which were inherited from Style1.
        msAssert.isTrue(newStyle2.getRunPr().getBold().toBool());
        msAssert.areEqual(Underline.SINGLE, newStyle2.getRunPr().getUnderline());
        msAssert.isTrue(newStyle2.getRunPr().getItalic().toBool());
        msAssert.isFalse((/*boolean*/Boolean)newStyle2.getParaPr().get(ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_ALPHA));
        msAssert.isFalse((/*boolean*/Boolean)newStyle2.getParaPr().get(ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_DIGIT));
        msAssert.areEqual(1008, newStyle2.getParaPr().getLeftIndent());

    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRemoveParagraphStyleSourceDataProvider")
	public void unifiedTestRemoveParagraphStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRemoveParagraphStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRemoveParagraphStyleSourceDataProvider")
	Object[][] unifiedTestRemoveParagraphStyleSourceDataProvider() throws Exception
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
    void unifiedTestRemoveCharacterStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        final String FILE_NAME = "Model\\Style\\RemoveCharacterStyle";

        Document doc = TestUtil.open(FILE_NAME, lf);
        Style oldStyle2 = doc.getStyles().get("Style2");
        Style oldStyle3 = doc.getStyles().get("Style3");

        msAssert.isTrue(oldStyle2.getFont().getSubscript());
        msAssert.areEqual("Arial", oldStyle2.getRunPr().getNameAscii());

        msAssert.isTrue(oldStyle3.getFont().getSubscript());
        msAssert.areEqual("Times New Roman", oldStyle3.getRunPr().getNameAscii());
        msAssert.areEqual(Underline.SINGLE, oldStyle3.getRunPr().getUnderline());

        doc.getStyles().remove(oldStyle2.getName());

        doc = TestUtil.saveOpen(doc, FILE_NAME, lf, sf);

        // Check that Style2 doesn't exist in StyleCollection.
        msAssert.isNull(doc.getStyles().get("Style2"));

        Style newStyle3 = doc.getStyles().get("Style3");

        // Check all properties which were inherited from Style2.
        msAssert.areEqual("Arial", newStyle3.getRunPr().getNameAscii());
        msAssert.isTrue(newStyle3.getFont().getSubscript());
        msAssert.areEqual(Underline.SINGLE, newStyle3.getRunPr().getUnderline());

    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRemoveCharacterStyleSourceDataProvider")
	public void unifiedTestRemoveCharacterStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRemoveCharacterStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRemoveCharacterStyleSourceDataProvider")
	Object[][] unifiedTestRemoveCharacterStyleSourceDataProvider() throws Exception
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
    void unifiedTestRemoveTableStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        if ((lf == LoadFormat.RTF) || (sf == SaveFormat.RTF))
        {
            Debug.writeLine("Table styles are not implemented in Rtf.");
            return;
        }

        final String FILE_NAME = "Model\\Style\\RemoveTableStyle";

        Document doc = TestUtil.open(FILE_NAME, lf);

        TableStyle tableStyle1 = (TableStyle)doc.getStyles().get("Style1");
        msAssert.isTrue(tableStyle1.getCellPr().getShading().getBackgroundPatternColorInternal().equals(new DrColor(0xff, 0x9B, 0xBB, 0x59)));
        ConditionalStyle oldConditionalStyle = tableStyle1.getConditionalStyles().getFirstRow();
        msAssert.isTrue(oldConditionalStyle.getRunPr().getBold().toBool());

        doc.getStyles().remove("Style1");
        doc = TestUtil.saveOpen(doc, FILE_NAME, lf, sf);

        // Check that Style doesn't exist in StyleCollection.
        msAssert.isNull(doc.getStyles().get("Style1"));

        TableStyle newTableStyle = (TableStyle) doc.getStyles().get("Style2");

        // Check all properties which were inherited from Style1.
        msAssert.isTrue(newTableStyle.getCellPr().getShading().getBackgroundPatternColorInternal().equals(new DrColor(0xff, 0x9B, 0xBB, 0x59)));
        ConditionalStyle newConditionalStyle = tableStyle1.getConditionalStyles().getFirstRow();
        msAssert.isTrue(newConditionalStyle.getRunPr().getBold().toBool());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRemoveTableStyleSourceDataProvider")
	public void unifiedTestRemoveTableStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRemoveTableStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRemoveTableStyleSourceDataProvider")
	Object[][] unifiedTestRemoveTableStyleSourceDataProvider() throws Exception
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
    void unifiedTestRemoveListStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        final String FILE_NAME = "Model\\Style\\RemoveListStyle";

        Document doc = TestUtil.open(FILE_NAME, lf);

        Style listStyle = doc.getStyles().get("Style1");
        Paragraph paragraph = (Paragraph)doc.getNodeById("0.0.0");
        ParagraphFormat pf = paragraph.getParagraphFormat();

        // The paragraph refers to a list that references the list style.
        msAssert.areEqual(2, pf.getListId());
        msAssert.areEqual(true, paragraph.getListFormat().getList().isListStyleReference());
        msAssert.areEqual(false, paragraph.getListFormat().getList().isListStyleDefinition());
        msAssert.areEqual(listStyle, paragraph.getListFormat().getList().getStyle());

        doc.getStyles().remove(listStyle.getName());

        // Check that Style1 doesn't exist in StyleCollection.
        msAssert.isNull(doc.getStyles().get("Style1"));
        msAssert.areEqual(0, paragraph.getParaPr().getListId());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRemoveListStyleSourceDataProvider")
	public void unifiedTestRemoveListStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRemoveListStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRemoveListStyleSourceDataProvider")
	Object[][] unifiedTestRemoveListStyleSourceDataProvider() throws Exception
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
    void unifiedTestCopyCharacterStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\CopyCharacterStyle", lf);
        Style oldStyle1 = doc.getStyles().get("Style1");
        // Style2 is a child of Style1.
        Style oldStyle2 = doc.getStyles().get("Style2");
        // Style3 is a child of Style2.
        Style oldStyle3 = doc.getStyles().get("Style3");

        // Check all paragraph and run properties after collapse.
        // These properties were inherited from Style1 and Style2.
        if (lf == LoadFormat.DOC)
        {
            msAssert.areEqual(AttrBoolEx.TOGGLE, oldStyle1.getRunPr().getItalic());
            msAssert.areEqual(AttrBoolEx.TOGGLE, oldStyle2.getRunPr().getBold());
        }
        else
        {
            msAssert.areEqual(AttrBoolEx.TRUE, oldStyle1.getRunPr().getItalic());
            msAssert.areEqual(AttrBoolEx.TRUE, oldStyle2.getRunPr().getBold());
        }

        msAssert.areEqual("Andalus", oldStyle3.getRunPr().getName());

        Document blank = new Document();
        blank.getStyles().addCopy(oldStyle3);
        Style newStyle3 = blank.getStyles().get("Style3");

        // Check all properties which were inherited from Style1 and Style2.
        if (lf == LoadFormat.DOC)
        {
            // IS. We should rewrite AttrBoolEx because we have Toggle and Same only in Doc format.
            // I set Italic in Style1 and  set Bold in Style2.
            // Problem occurs because in Doc format Bold and Italic are set to Toggle in dependencies styles
            // and these properties are set to Toggle in Style3 but it's incorrect.
            msAssert.areEqual(AttrBoolEx.FALSE, newStyle3.getRunPr().getItalic());
            msAssert.areEqual(AttrBoolEx.FALSE, newStyle3.getRunPr().getBold());
        }
        else
        {
            msAssert.areEqual(AttrBoolEx.TRUE, newStyle3.getRunPr().getItalic());
            msAssert.areEqual(AttrBoolEx.TRUE, newStyle3.getRunPr().getBold());
        }

        msAssert.areEqual("Andalus", newStyle3.getRunPr().getName());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCopyCharacterStyleSourceDataProvider")
	public void unifiedTestCopyCharacterStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCopyCharacterStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCopyCharacterStyleSourceDataProvider")
	Object[][] unifiedTestCopyCharacterStyleSourceDataProvider() throws Exception
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
    void unifiedTestCopyParagraphStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\CopyParagraphStyle", lf);

        Style oldStyle1 = doc.getStyles().get("Style1");
        // Style2 is a child of Style1.
        Style oldStyle2 = doc.getStyles().get("Style2");
        // Style3 is a child of Style2.
        Style oldStyle3 = doc.getStyles().get("Style3");

        // Check all paragraph and run properties after collapse.
        // These properties were inherited from Style1 and Style2.
        msAssert.areEqual(ParagraphAlignment.CENTER, oldStyle1.getParaPr().getAlignment());
        msAssert.areEqual((lf == LoadFormat.DOC) ? AttrBoolEx.TOGGLE : AttrBoolEx.TRUE, oldStyle1.getRunPr().getBold());
        msAssert.areEqual(720, oldStyle2.getParaPr().getFirstLineIndent());

        msAssert.areEqual(1440, oldStyle3.getParaPr().getLeftIndent());

        Document blank = new Document();
        blank.getStyles().addCopy(oldStyle3);
        Style newStyle3 = blank.getStyles().get("Style3");

        // Check all properties which were inherited from Style1 and Style2.
        msAssert.areEqual(ParagraphAlignment.CENTER, newStyle3.getParaPr().getAlignment());
        msAssert.areEqual(AttrBoolEx.TRUE, newStyle3.getRunPr().getBold());
        msAssert.areEqual(720, newStyle3.getParaPr().getFirstLineIndent());
        msAssert.areEqual(1440, newStyle3.getParaPr().getLeftIndent());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCopyParagraphStyleSourceDataProvider")
	public void unifiedTestCopyParagraphStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCopyParagraphStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCopyParagraphStyleSourceDataProvider")
	Object[][] unifiedTestCopyParagraphStyleSourceDataProvider() throws Exception
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
    void unifiedTestCopyTableStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        if (lf == LoadFormat.RTF || sf == SaveFormat.RTF)
        {
            Debug.writeLine("Table styles are not implemented in Rtf.");
            return;
        }

        Document doc = TestUtil.open("Model\\Style\\CopyTableStyle", lf);

        TableStyle oldTableStyle = (TableStyle)doc.getStyles().getByName("Table 3D effects 1", false);
        msAssert.areEqual("Table 3D effects 1", oldTableStyle.getName());
        msAssert.areEqual(DrColor.fromArgb(0xc0, 0xc0, 0xc0), oldTableStyle.getCellPr().getShading().getForegroundPatternColorInternal());
        ConditionalStyle oldConditionalStyle = oldTableStyle.getConditionalStyles().getFirstRow();
        msAssert.isTrue(oldConditionalStyle.getRunPr().getColor().equals(new DrColor(0xff, 0x80, 0x00, 0x80)));

        Document blank = new Document();
        blank.getStyles().addCopy(oldTableStyle);
        TableStyle newTableStyle = (TableStyle)doc.getStyles().get("Table 3D effects 1");
        msAssert.areEqual("Table 3D effects 1", oldTableStyle.getName());
        msAssert.areEqual(DrColor.fromArgb(0xc0, 0xc0, 0xc0), newTableStyle.getCellPr().getShading().getForegroundPatternColorInternal());
        ConditionalStyle conditionalStyle = newTableStyle.getConditionalStyles().getFirstRow();
        msAssert.isTrue(conditionalStyle.getRunPr().getColor().equals(new DrColor(0xff, 0x80, 0x00, 0x80)));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCopyTableStyleSourceDataProvider")
	public void unifiedTestCopyTableStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCopyTableStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCopyTableStyleSourceDataProvider")
	Object[][] unifiedTestCopyTableStyleSourceDataProvider() throws Exception
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
    /// WORDSNET-4782 Paragraph SpaceAfter/SpaceBefore is incorrect.
    /// andrnosk: If parent style is null we need to check style ParaPr defaults before getting global defaults.
    /// The same we already do for style RunPr defaults.
    /// </summary>
    @Test
    public void testJira4782() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestJira4782.docx");

        ParagraphFormat dirParagraphFormat = doc.getFirstSection().getBody().getFirstParagraph().getParagraphFormat();
        ParagraphFormat styleParagraphFormat = doc.getStyles().getByStyleIdentifier(StyleIdentifier.TITLE).getParagraphFormat();

        msAssert.areEqual(15, dirParagraphFormat.getSpaceAfter());
        msAssert.areEqual(18, dirParagraphFormat.getSpaceBefore());
        msAssert.areEqual(15, styleParagraphFormat.getSpaceAfter());
        msAssert.areEqual(18, styleParagraphFormat.getSpaceBefore());
    }



    /// <summary>
    /// WORDSNET-2304 Consider adding an ability to remove style from the document.
    /// Tests that attributes from base style being deleted are expanded into child styles.
    /// </summary>
    @Test
    public void testJira2304_Expanding() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestJira2304_Expanding.docx");

        msAssert.isNotNull(doc.getStyles().get("Style1"));

        msAssert.isNull(doc.getStyles().get("Style2").getRunPr().get(FontAttr.BOLD));
        msAssert.isNull(doc.getStyles().get("Style2").getRunPr().get(FontAttr.ITALIC));

        doc.getStyles().get("Style1").remove();

        // Style is removed.
        msAssert.isNull(doc.getStyles().get("Style1"));

        // Child style got attributes from removed style.
        msAssert.areEqual(AttrBoolEx.TRUE, doc.getStyles().get("Style2").getRunPr().get(FontAttr.BOLD));
        msAssert.areEqual(AttrBoolEx.TRUE, doc.getStyles().get("Style2").getRunPr().get(FontAttr.ITALIC));
    }

    /// <summary>
    /// WORDSNET-2304 Consider adding an ability to remove style from the document.
    /// Tests that if linked style is being deleted both styles are deleted.
    /// </summary>
    @Test
    public void testJira2304_Linked() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestJira2304_Linked.docx");

        Style style = doc.getStyles().get("Style3");
        msAssert.isNotNull(style.getLinkedStyle());

        doc.getStyles().get("Style3 Char").remove();

        // Both styles are removed.
        msAssert.isNull(doc.getStyles().get("Style3"));
        msAssert.isNull(doc.getStyles().get("Style3 Char"));
    }

    /// <summary>
    /// WORDSNET-2304 Consider adding an ability to remove style from the document.
    /// Tests that style for following paragraph (NextIstd) is set properly upon style deletion.
    /// </summary>
    @Test
    public void testJira2304_NextIstd() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestJira2304_NextIstd.docx");

        Style style1 = doc.getStyles().get("Style1");

        testStyle(doc, "Style2", "Style1", "Style1");
        testStyle(doc, "Style3", "Normal", "Style1");
        testStyle(doc, "Style4", "Style1", "Style1");
        testStyle(doc, "Style5", "Style2", "Style1");

        style1.remove();

        // Verify links to base and following styles are updated properly.
        testStyle(doc, "Style2", "Normal", "Style2");
        testStyle(doc, "Style3", "Normal", "Normal");
        testStyle(doc, "Style4", "Normal", "Style4");
        testStyle(doc, "Style5", "Style2", "Normal");
    }

    /// <summary>
    /// WORDSNET-2304 Consider adding an ability to remove style from the document.
    /// Tests how document model is updated.
    /// </summary>
    @Test
    public void testJira2304_DocumentModel() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestJira2304_DocumentModel.docx");

        // CharacterTest changes is tracked.
        msAssert.areEqual(1, doc.getRevisions().getCount());

        ParaPr paraPr = doc.getFirstSection().getBody().getParagraphs().get(0).getParaPr();
        msAssert.areEqual(0x0f, paraPr.getIstd());

        RunPr runPr = doc.getFirstSection().getBody().getParagraphs().get(1).getRuns().get(1).getRunPr();
        msAssert.areEqual(0x10, runPr.getIstd());

        TablePr tablePr = ((Row)doc.getChild(NodeType.ROW, 0, true)).getTablePr();
        msAssert.areEqual(0x12, tablePr.getIstd());

        // Remove paragraph styles.
        doc.getStyles().get("ParagraphTest").remove();
        msAssert.isNull(paraPr.get(ParaAttr.ISTD));

        // Remove character style. Style is also removed from Document.Revisions collection.
        doc.getStyles().get("CharacterTest").remove();
        msAssert.isNull(runPr.get(FontAttr.ISTD));
        msAssert.areEqual(0, doc.getRevisions().getCount());

        // Remove table style.
        doc.getStyles().get("TableTest").remove();
        msAssert.areEqual(0x0b, tablePr.getIstd());
    }


    /// <summary>
    /// WORDSNET-8850 doc.Styles[StyleIdentifier.NoteHeading] does not create style in the document.
    /// NoteHeading seems to be deprecated builtin style, it is presented in DOC but absent in DOCX.
    /// Special processing for this style is added to perform unified behavior.
    /// </summary>
    @Test
    public void testJira8850() throws Exception
    {
        Document doc = new Document();
        Style noteHeading = doc.getStyles().getBySti(StyleIdentifier.NOTE_HEADING, true);

        // Verify that "Note Heading" style is created.
        msAssert.isNotNull(noteHeading);
        msAssert.isTrue(noteHeading.getBuiltIn());

        // Verify that linked "Note Heading Char" is also created.
        Style noteHeadingChar = doc.getStyles().getByIstd(noteHeading.getLinkedIstd(), true);
        msAssert.areEqual("Note Heading Char", noteHeadingChar.getName());
        msAssert.isFalse(noteHeadingChar.getBuiltIn());
    }

    /// <summary>
    /// WORDSNET-10695 /list label attrs/ Docx to Pdf conversion issue with numbering style
    /// Word ignores any formatting specified in "Default Paragraph Font".
    /// </summary>
    @Test
    public void testJira10695() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestJira10695.docx");
        Paragraph p = doc.getFirstSection().getBody().getFirstParagraph();
        RunPr runPr = p.getExpandedParagraphBreakRunPr(RunPrExpandFlags.LAYOUT);
        msAssert.areEqual(AttrBoolEx.TRUE, runPr.getBold());
    }

    /// <summary>
    /// WORDSNET-10693 Paragraph spacing is lost after re-saving the Docx
    /// "type" attribute missing in style definition (we have Paragraph by default) and Table Normal becomes StyleType.Paragraph after loading.
    /// </summary>
    @Test
    public void testJira10693() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestJira10693.docx");

        TableStyle tableNormal = (TableStyle)doc.getStyles().getByIstd(StyleIndex.TABLE_NORMAL, false);
        msAssert.isNotNull(tableNormal);

        msAssert.areEqual(StyleType.TABLE, tableNormal.getType());
    }



    /// <summary>
    /// WORDSNET-10872 Provide Style.Aliases property
    /// </summary>
    @Test
    public void testJira10872_AliasesDoNotExist() throws Exception
    {
        Document doc = new Document();
        Style style = doc.getStyles().get("Normal");
        msAssert.isEmpty(style.getAliases());
    }

    /// <summary>
    /// WORDSNET-10872 Provide Style.Aliases property
    /// </summary>
    @Test
    public void testJira10872_AliasesExist() throws Exception
    {
        final String TEST_NAME = "Model\\Style\\TestJira10872.docx";
        Document doc = TestUtil.open(TEST_NAME);
        Style style = doc.getStyles().get("NewStyle");
        msAssert.areEqual(new String[] {"New-Style", "NouveauStyle", "N-S"}, style.getAliases());
    }




    /// <summary>
    /// Relates to WORDSNET-4079
    /// Checks how default paragraph formatting is accessed.
    /// </summary>
    @Test
    public void testJira4079ParagraphFormat() throws Exception
    {
        final String TEST_NAME = "Model\\Style\\TestJira4079 ParagraphFormat.docx";

        Document doc = TestUtil.open(TEST_NAME);

        msAssert.areEqual(8, doc.getStyles().getDefaultParagraphFormat().getSpaceAfter());

        doc.getStyles().getDefaultParagraphFormat().setSpaceAfter(20.0);
        doc = TestUtil.saveOpen(doc, TEST_NAME, SaveOptions.createSaveOptions(SaveFormat.DOCX), false);
        msAssert.areEqual(20, doc.getStyles().getDefaultParagraphFormat().getSpaceAfter());
    }




    /// <summary>
    /// WORDSNET-14467 Custom font formatting applied via Template gets lost in PDF.
    /// In addition to cloning 'defaults' into 'Normal' style,
    /// need to expand 'Normal' properties when updating styles from template.
    /// </summary>
    @Test
    public void testJira14467() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestJira14467.docx");
        doc.setAttachedTemplate(TestUtil.buildTestFileName("Model\\Style\\TestJira14467.dotx"));

        TestUtil.executeValidator(doc, SaveFormat.MARKDOWN);

        testJira14467Core(doc);
    }

    /// <summary>
    /// WORDSNET-14429 Document based on a template loses normal style after saving with Aspose.Words.
    /// This issue duplicates WORDSNET-14467
    /// </summary>
    @Test
    public void testJira14429() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Style\\TestJira14467", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        doc.setAttachedTemplate(TestUtil.buildTestFileName("Model\\Style\\NonExistentTemplateFile"));
        doc = TestUtil.saveOpen(doc, "Model\\Style\\TestJira14429", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        testJira14467Core(doc);
    }

    /// <summary>
    /// Tests links to base and following styles.
    /// </summary>
    private static void testStyle(Document doc, String styleName, String basedOnName, String followingByName)
    {
        Style style = doc.getStyles().getByName(styleName, false);

        msAssert.areEqual(basedOnName, style.getBaseStyleName());
        msAssert.areEqual(followingByName, style.getNextParagraphStyleName());
    }

    /// <summary>
    /// Core method for testing WORDSNET-14467 and WORDSNET-14429
    /// Checks some significant for these tests properties of 'Normal' style.
    /// </summary>
    private static void testJira14467Core(Document doc)
    {
        msAssert.isTrue(doc.getDocPr().LinkStyles);

        Style normal = doc.getStyles().getBySti(StyleIdentifier.NORMAL, false);

        // Check run attributes.
        RunPr runPr = normal.getRunPr();
        ComplexFontName officeFont = ComplexFontName.fromName("Aldhabi");
        // These are from 'Normal'. Defaults have these attributes too, but with some other values.
        msAssert.areEqual(officeFont, runPr.get(FontAttr.NAME_ASCII));
        msAssert.areEqual(DrColor.fromArgb(0xFF, 0x0, 0x0), runPr.get(FontAttr.COLOR));
        // These are from defaults. 'Normal' does not contain these attributes at all.
        msAssert.areEqual(200, runPr.get(FontAttr.SIZE));
        msAssert.areEqual(AttrBoolEx.TRUE, runPr.get(FontAttr.BOLD));
        // 'Italic' is set in 'Normal' of source document, but it is not defined in template at all.
        msAssert.isFalse(runPr.contains(FontAttr.ITALIC));

        // Check paragraph attributes.
        LineSpacing lineSpacing = (LineSpacing)normal.getParaPr().get(ParaAttr.LINE_SPACING);
        // 300 is in 'Normal', 259 is in defaults.
        msAssert.areEqual(300, lineSpacing.getValue());
    }

    /// <summary>
    /// WORDSNET-15274 Read-only access to style properties caused that the Style.Equals method returned
    /// <c>false</c> for identical styles.
    /// </summary>
    @Test
    public void testJira15274() throws Exception
    {
        Document doc = new Document();

        Style style1 = doc.getStyles().add(StyleType.PARAGRAPH, "my1");
        Style style2 = doc.getStyles().add(StyleType.PARAGRAPH, "my2");

        // Call properties to create and add them to internal attribute collection.
        msAssert.isNotNull(style1.getParagraphFormat().getShading());
        msAssert.isNotNull(style1.getParagraphFormat().getBorders().getByBorderType(BorderType.LEFT));
        // It is not fixed for the ParagraphFormat.TabStops property yet.

        msAssert.isTrue(style1.equals(style2));
    }


    /// <summary>
    /// WORDSNET-16374 Table row is rendered on previous page in output PDF.
    /// Word uses font size value from default paragraph style when setting “overrideTableStyleFontSizeAndJustification”
    /// is switched off, paragraph is located into the table, font size in the default paragraph style is 12 pt and actual
    /// size value before expanding direct formatting from style is 10 pt. Emulate the same behavior to fix the problem.
    /// </summary>
    @Test
    public void testJira16374() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestJira16374.docx");
        TableCollection tables = doc.getFirstSection().getBody().getTables();

        checkExpandedSizeVal(tables.get(0).getFirstRow().getFirstCell().getFirstParagraph(), FontAttr.SIZE, 26);
        checkExpandedSizeVal(tables.get(1).getFirstRow().getFirstCell().getFirstParagraph(), FontAttr.SIZE, 24);
        checkExpandedSizeVal(tables.get(2).getFirstRow().getFirstCell().getFirstParagraph(), FontAttr.SIZE, 22);
        checkExpandedSizeVal(tables.get(3).getFirstRow().getFirstCell().getFirstParagraph(), FontAttr.SIZE, 24);
        checkExpandedSizeVal(tables.get(4).getFirstRow().getFirstCell().getFirstParagraph(), FontAttr.SIZE, 18);
        checkExpandedSizeVal(tables.get(5).getFirstRow().getFirstCell().getFirstParagraph(), FontAttr.SIZE, 24);
        checkExpandedSizeVal(tables.get(6).getFirstRow().getFirstCell().getFirstParagraph(), FontAttr.SIZE, 40);
    }

    /// <summary>
    /// WORDSNET-19927 Incorrect font size in output PDF.
    /// Iso29500ComplianceEnforcer (incorrectly) changed unspecified value of compatibility
    /// setting “overrideTableStyleFontSizeAndJustification”.
    /// </summary>
    @Test
    public void test19927() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\Test19927.docx");
        Paragraph para = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getFirstCell().getFirstParagraph();

        CustomCompatibilitySetting setting =
            doc.getCompatibilityOptions().getCustomCompatibilitySettings().get("overrideTableStyleFontSizeAndJustification");

        msAssert.isNull(setting);
        checkExpandedSizeVal(para, FontAttr.SIZE, 40);

        TestUtil.executeValidator(doc, SaveFormat.MARKDOWN);

        setting = doc.getCompatibilityOptions().getCustomCompatibilitySettings().get("overrideTableStyleFontSizeAndJustification");
        msAssert.isNotNull(setting);
        msAssert.areEqual("0", setting.getValue());

        // Verify, that the resulting font size remained the same.
        checkExpandedSizeVal(para, FontAttr.SIZE, 40);
    }

    /// <summary>
    /// Relates with WORDSNET-16374
    /// Checks cases with sizes of the "rtl" content, while expanding properties of runs.
    /// </summary>
    @Test
    public void testJira16374Bidi() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestJira16374Bidi.docx");
        TableCollection tables = doc.getFirstSection().getBody().getTables();

        checkExpandedSizeVal(tables.get(0).getFirstRow().getFirstCell().getFirstParagraph(), FontAttr.SIZE_BI, 26);
        checkExpandedSizeVal(tables.get(1).getFirstRow().getFirstCell().getFirstParagraph(), FontAttr.SIZE_BI, 24);
        checkExpandedSizeVal(tables.get(2).getFirstRow().getFirstCell().getFirstParagraph(), FontAttr.SIZE_BI, 22);
        checkExpandedSizeVal(tables.get(3).getFirstRow().getFirstCell().getFirstParagraph(), FontAttr.SIZE_BI, 24);
        checkExpandedSizeVal(tables.get(4).getFirstRow().getFirstCell().getFirstParagraph(), FontAttr.SIZE_BI, 18);
        checkExpandedSizeVal(tables.get(5).getFirstRow().getFirstCell().getFirstParagraph(), FontAttr.SIZE_BI, 24);
        checkExpandedSizeVal(tables.get(6).getFirstRow().getFirstCell().getFirstParagraph(), FontAttr.SIZE_BI, 40);
    }





    /// <summary>
    /// WORDSNET-18557 Shading.BackgroundPatternColor returns incorrect cell's background color
    /// We need to update color from theme on style import.
    /// </summary>
    @Test
    public void test18557() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\Test18557.docx");

        Table table = (Table)doc.getChildNodes(NodeType.TABLE, true).get(0);
        table.setStyle(doc.getStyles().getByStyleIdentifier(StyleIdentifier.DARK_LIST_ACCENT_3));

        Cell cell = (Cell)doc.getChildNodes(NodeType.CELL, true).get(8);

        msAssert.areEqual(0x7B, cell.getCellFormat().getShading().getBackgroundPatternColor().getGreen());
        msAssert.areEqual(0x7B, cell.getCellFormat().getShading().getBackgroundPatternColor().getRed());
        msAssert.areEqual(0x7B, cell.getCellFormat().getShading().getBackgroundPatternColor().getBlue());
    }





    /// <summary>
    /// Tests how document with huge set of styles exceeding Word limit are written to Word native document formats.
    /// </summary>
    @Test (dataProvider = "testStyleLimitDataProvider")
    public void testStyleLimit(/*SaveFormat*/int sf, /*WarningSource*/int warningSource, int expectedCount) throws Exception
    {
        Document doc = createStyleLimitDocument();

        WarningInfoCollection warnings = new WarningInfoCollection();
        doc.setWarningCallback(warnings);

        // Check some edge styles before save.
        msAssert.areEqual(4204, doc.getStyles().getCount());
        checkStyle(doc.getStyles(), 4015, 4025, 4115, 4030);
        checkStyle(doc.getStyles(), 4079, 4089, 4179, 4094);
        checkStyle(doc.getStyles(), 4084, 4094, 4184, 4099);

        /*UnifiedScenario*/int uc = TestUtil.getUnifiedScenario(LoadFormat.DOCX, sf);

        // WML writer has unrelated roundtrip issue, exclude from gold check.
        if (sf == SaveFormat.WORD_ML)
            uc |= UnifiedScenario.NO_GOLD;

        doc = TestUtil.saveOpen(doc, "Model\\Style\\TestStyleLimit", uc);

        // Check the same style set after roundtrip.
        msAssert.areEqual(expectedCount, doc.getStyles().getCount());

        // "Followed by" feature is not supported in ODT format.
        checkStyle(doc.getStyles(), 4015, 4025, StyleIndex.NIL, (sf == SaveFormat.ODT) ? 4015 : 4030);
        checkStyle(doc.getStyles(), 4079, 4089, StyleIndex.NIL, 4079);
        checkStyle(doc.getStyles(), 4084, StyleIndex.NIL, StyleIndex.NIL, 4084);

        msAssert.isTrue(TestUtil.containsWarning(warnings, WarningType.DATA_LOSS, warningSource,
                "Style 'MyParagraphStyle 4094' has style index unsupported by target format."));

        msAssert.isTrue(TestUtil.containsWarning(warnings, WarningType.DATA_LOSS, warningSource,
                "Style 'MyLinkedStyle 4115' has style index unsupported by target format."));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testStyleLimitDataProvider")
	public static Object[][] testStyleLimitDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX,  WarningSource.DOCX,  4082},
		};
	}


    /// <summary>
    /// WORDSNET-21785 Remove styles from Style Gallery.
    /// Added public method ClearQuickStyleGallery().
    /// </summary>
    @Test
    public void test21785() throws Exception
    {
        Document doc = new Document();
        doc.getStyles().clearQuickStyleGallery();
        for (Style style : doc.getStyles())
            msAssert.isFalse(style.isQuickStyle());

        for (int i = 0; i < doc.getStyles().getLatentStyles().getCount(); i++)
        {
            LatentStyle lse = doc.getStyles().getLatentStyles().get(i);
            msAssert.isFalse(lse.getQuickStyle());
        }

        // Gold check, check that Quick Style Gallery is empty if modified.
        TestUtil.saveOpen(doc, "Model\\Style\\Test21785.docx", SaveOptions.createSaveOptions(SaveFormat.DOCX));
    }






    /// <summary>
    /// WORDSNET-24686 Make Style.AutomaticallyUpdate property public.
    /// Tests the public API of AutomaticallyUpdate in Style.
    /// </summary>
    @Test
    public void test24686() throws Exception
    {
        Document doc = new Document();
        Style style = doc.getStyles().add(StyleType.PARAGRAPH, "Redefined");
        style.setBaseStyleName("Normal");

        msAssert.isFalse(style.getAutomaticallyUpdate());
        style.setAutomaticallyUpdate(true);
        msAssert.isTrue(style.getAutomaticallyUpdate());

        doc = TestUtil.saveOpen(doc, "Model\\Style\\Test24686", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        style = doc.getStyles().getByName("Redefined", false);
        msAssert.isTrue(style.getAutomaticallyUpdate());
    }


    /// <summary>
    /// WORDSNET-23972 Provide an ability to get/set styles priority.
    /// Checks a new public API.
    /// </summary>
    @Test
    public void test23972() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\Test23972.docx");
        Style styleTitle = doc.getStyles().getByStyleIdentifier(StyleIdentifier.SUBTITLE);
        msAssert.areEqual(11, styleTitle.getPriority());

        styleTitle.setPriority(9);
        doc = TestUtil.saveOpen(doc, "Model\\Style\\Test23972.docx");
        styleTitle = doc.getStyles().getByStyleIdentifier(StyleIdentifier.SUBTITLE);
        msAssert.areEqual(9, styleTitle.getPriority());
    }

    /// <summary>
    /// WORDSNET-23973 Provide an ability to show/hide styles.
    /// Checks a new public API.
    /// </summary>
    @Test
    public void test23973() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\Test23973.docx");
        Style styleTitle = doc.getStyles().getByStyleIdentifier(StyleIdentifier.TITLE);
        msAssert.areEqual(false, styleTitle.getSemiHidden());
        msAssert.areEqual(false, styleTitle.getUnhideWhenUsed());

        styleTitle.setSemiHidden(true);
        styleTitle.setUnhideWhenUsed(true);
        doc = TestUtil.saveOpen(doc, "Model\\Style\\Test23973.docx");
        styleTitle = doc.getStyles().getByStyleIdentifier(StyleIdentifier.TITLE);
        msAssert.areEqual(true, styleTitle.getSemiHidden());
        msAssert.areEqual(true, styleTitle.getUnhideWhenUsed());
    }

    /// <summary>
    /// WORDSNET-24691 Allow setting LinkedStyleName property.
    /// Checks a new public API.
    /// </summary>
    @Test
    public void test24691() throws Exception
    {
        final String FILE_NAME = "Model\\Style\\Test24691";
        Document doc = TestUtil.open(FILE_NAME, LoadFormat.DOCX);
        // Checking the linked style values set via MS Word VBA.
        Style styleHeading1 = doc.getStyles().getByStyleIdentifier(StyleIdentifier.HEADING_1);
        Style styleHeading1Char1 = doc.getStyles().get("Heading 1 Char");
        Style styleHeading1Char2 = doc.getStyles().get("Heading 1 Char1");
        msAssert.areEqual("Heading 1 Char", styleHeading1.getLinkedStyleName());
        msAssert.areEqual("Heading 1", styleHeading1Char1.getLinkedStyleName());
        msAssert.areEqual("", styleHeading1Char2.getLinkedStyleName());

        styleHeading1.setLinkedStyleName("Heading 1 Char1");
        // Checking the linked style values set via the paragraph style.
        doc = TestUtil.saveOpen(doc, FILE_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        styleHeading1 = doc.getStyles().getByStyleIdentifier(StyleIdentifier.HEADING_1);
        styleHeading1Char1 = doc.getStyles().get("Heading 1 Char");
        styleHeading1Char2 = doc.getStyles().get("Heading 1 Char1");
        msAssert.areEqual("Heading 1 Char1", styleHeading1.getLinkedStyleName());
        msAssert.areEqual("", styleHeading1Char1.getLinkedStyleName());
        msAssert.areEqual("Heading 1", styleHeading1Char2.getLinkedStyleName());

        styleHeading1Char1.setLinkedStyleName("Heading 1");
        // Checking the linked style values set via the character style.
        doc = TestUtil.saveOpen(doc, FILE_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        styleHeading1 = doc.getStyles().getByStyleIdentifier(StyleIdentifier.HEADING_1);
        styleHeading1Char1 = doc.getStyles().get("Heading 1 Char");
        styleHeading1Char2 = doc.getStyles().get("Heading 1 Char1");
        msAssert.areEqual("Heading 1 Char", styleHeading1.getLinkedStyleName());
        msAssert.areEqual("Heading 1", styleHeading1Char1.getLinkedStyleName());
        msAssert.areEqual("", styleHeading1Char2.getLinkedStyleName());

        // The check of unlinking.
        styleHeading1.setLinkedStyleName("");
        msAssert.areEqual(StyleIndex.NIL, styleHeading1.getLinkedIstd());
        msAssert.areEqual(StyleIndex.NIL, styleHeading1Char1.getLinkedIstd());
        msAssert.areEqual(StyleIndex.NIL, styleHeading1Char2.getLinkedIstd());
    }

    /// <summary>
    /// Related to WORDSNET-24691
    /// Tests the case when LinkedStyleName is null.
    /// </summary>
    @Test (expectedExceptions = NullPointerException.class)
    public void test24691_Null() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\Test24691.docx");
        doc.getStyles().getByStyleIdentifier(StyleIdentifier.HEADING_1).setLinkedStyleName(null);
    }

    /// <summary>
    /// Related to WORDSNET-24691
    /// Tests the case when LinkedStyleName is assigned to a table style.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QOnly the paragraph and the character styles can be linked.\\E")
    public void test24691_StyleType() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\Test24691.docx");
        doc.getStyles().getByStyleIdentifier(StyleIdentifier.TABLE_NORMAL).setLinkedStyleName("Heading 1 Char1");
    }

    /// <summary>
    /// Related to WORDSNET-24691
    /// Tests the case when a list style is assigned as LinkedStyleName.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QOnly the paragraph and the character styles can be linked.\\E")
    public void test24691_LinkedStyleType() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\Test24691.docx");
        doc.getStyles().getByStyleIdentifier(StyleIdentifier.HEADING_1).setLinkedStyleName(doc.getStyles().getByStyleIdentifier(StyleIdentifier.LIST).getName());
    }

    /// <summary>
    /// Related to WORDSNET-24691
    /// Tests the case when DefaultParagraphFont is assigned as LinkedStyleName.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QDefault paragraph font cannot be linked style.\\E")
    public void test24691_DefaultParagraphFont() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\Test24691.docx");
        doc.getStyles().getByStyleIdentifier(StyleIdentifier.HEADING_1).setLinkedStyleName(doc.getStyles().getByStyleIdentifier(StyleIdentifier.DEFAULT_PARAGRAPH_FONT).getName());
    }

    /// <summary>
    /// WORDSNET-28303 Because of a bug, Aspose.Words couldn't instantiate the built-in "Macro Text" by name.
    /// </summary>
    @Test
    public void test28303() throws Exception
    {
        Document doc = new Document();
        Style style = doc.getStyles().get("Macro Text");

        msAssert.isNotNull(style);
        msAssert.isTrue(style.getBuiltIn());
        msAssert.areEqual(StyleIdentifier.MACRO, style.getStyleIdentifier());
    }

    /// <summary>
    /// Expands attributes of the first run for specified paragraph and checks calculated size attribute value.
    /// </summary>
    private static void checkExpandedSizeVal(Paragraph para, int sizeKey, int sizeValue)
    {
        Style style = para.getDocument().getStyles().getByIstd(para.getParaPr().getIstd(), false);
        msAssert.areEqual(StyleIdentifier.NORMAL, style.getStyleIdentifier());
        msAssert.areEqual(24, style.getRunPr().get(sizeKey));

        RunPr dstRunPr = para.getFirstRun().getExpandedRunPr(RunPrExpandFlags.NORMAL);
        msAssert.areEqual(sizeValue, dstRunPr.get(sizeKey));
    }

    /// <summary>
    /// Creates Document object with many styles exceeding Word style limit.
    /// </summary>
    /// <returns></returns>
    private static Document createStyleLimitDocument() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getFont().setStyle(doc.getStyles().getByStyleIdentifier(StyleIdentifier.DEFAULT_PARAGRAPH_FONT));
        builder.getParagraphFormat().setStyle(doc.getStyles().getByStyleIdentifier(StyleIdentifier.NORMAL));

        final int ISTD_BASE = 15;
        for (int i = 0; i < 4000; i++)
        {
            Style style = doc.getStyles().add(StyleType.CHARACTER, msString.format("MyCharStyle {0:D4}", ISTD_BASE + i));

            style.getRunPr().setAttr(FontAttr.COLOR, DrColor.fromArgb(i % 255, i % 255, i % 255));
            builder.getFont().setStyle(style);
            builder.write(Integer.toString((i % 10)));
        }

        builder.writeln();

        for (int i = 0; i < 100; i++)
        {
            Style style = doc.getStyles().add(StyleType.PARAGRAPH, msString.format("MyParagraphStyle {0:D4}", ISTD_BASE + 4000 + i));
            style.getParaPr().setAttr(ParaAttr.LEFT_INDENT, i * 10);


            builder.getParagraphFormat().setStyle(style);
            builder.writeln(msString.format("Line {0}", i));

            style.setBasedOnIstd((i < 90 ? style.getIstd() + 10 : StyleIndex.NIL));
            style.setNextIstd((i < 85 ? style.getIstd() + 15 : style.getIstd()));
        }

        // Make few character styles linked to paragraph styles.
        for (int i = 0; i < 100; i++)
        {
            Style style = doc.getStyles().add(StyleType.CHARACTER, msString.format("MyLinkedStyle {0:D4}", ISTD_BASE + 4000 + 100 + i));
            // Link to paragraph styles generated in above step.
            Style paraStyle = doc.getStyles().getByName(msString.format("MyParagraphStyle {0:D4}", ISTD_BASE + 4000 + i), false);

            paraStyle.setLinkedIstd(style.getIstd());
            style.setLinkedIstd(paraStyle.getIstd());

            style.getRunPr().setAttr(FontAttr.COLOR, DrColor.fromArgb(i % 255, 0, i % 255));
            builder.getFont().setStyle(style);
            builder.write(Integer.toString((i % 10)));
        }

        return doc;
    }

    /// <summary>
    /// Checks BasedOn, Linked and Next Istd of given style.
    /// </summary>
    private static void checkStyle(StyleCollection styles, int istd, int istdBasedOn, int istdLinked, int istdNext)
    {
        Style style = styles.getByIstd(istd, false);
        msAssert.areEqual(istdBasedOn, style.getBasedOnIstd());
        msAssert.areEqual(istdLinked, style.getLinkedIstd());
        msAssert.areEqual(istdNext, style.getNextIstd());
    }
}

