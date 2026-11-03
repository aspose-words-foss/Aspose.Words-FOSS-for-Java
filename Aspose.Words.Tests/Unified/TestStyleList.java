// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/09/2011 by Roman Korchagin

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
/// Tests for List Styles.
/// </summary>
@Test
public class TestStyleList extends UnifiedTestsBase
{
    /// <summary>
    /// WORDSNET-1568 The problem was that DOCX and WordML do not store copies of list formatting 
    /// in the list, which is a style reference. They only store list formatting in the list, which is a 
    /// list style definition. Modified attribute resolution so we retrieve the list which is a list style definition. 
    /// 
    /// category - Style, list style
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestListStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Style\\TestListStyle", lf, sf);

        // Check all is set up correctly from the list style side point of view.
        Style style = doc.getStyles().getByStyleIdentifier(StyleIdentifier.OUTLINE_LIST_1);

        msAssert.areEqual(StyleType.LIST, style.getType());
        msAssert.areEqual(StyleIndex.NO_LIST, style.getBasedOnIstd());
        msAssert.areEqual(0x0f, style.getIstd());
        msAssert.areEqual(0x0f, style.getNextIstd());

        // Check the link from the list style to the list and back.
        msAssert.areEqual(1, style.getParaPr().getListId());
        msAssert.areEqual(false, style.getList().isListStyleReference());
        msAssert.areEqual(true, style.getList().isListStyleDefinition());
        msAssert.areEqual(style, style.getList().getStyle());

        // Check all is set up correctly from the paragraph point of view.
        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 0, true);
        ParagraphFormat pf = para.getParagraphFormat();

        // The paragraph style is Normal, its correct.
        msAssert.areEqual(StyleIdentifier.NORMAL, pf.getStyleIdentifier());

        // The paragraph refers to a list that references the list style.
        msAssert.areEqual(2, pf.getListId());
        msAssert.areEqual(true, para.getListFormat().getList().isListStyleReference());
        msAssert.areEqual(false, para.getListFormat().getList().isListStyleDefinition());
        msAssert.areEqual(style, para.getListFormat().getList().getStyle());

        // Check the list formatting makes its way on to the paragraph.
        // This happens not through the list style, but through normal list formatting that references the list style.
        msAssert.areEqual(18d, pf.getLeftIndent());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListStyleSourceDataProvider")
	public void unifiedTestListStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListStyleSourceDataProvider")
	Object[][] unifiedTestListStyleSourceDataProvider() throws Exception
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
    /// category - list style, import style
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestImportListStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document srcDoc = TestUtil.openSaveOpen("Model\\Style\\TestImportListStyle", lf, sf);

        // Check things are the way we expect them in the source document.
        Style style = srcDoc.getStyles().getBySti(StyleIdentifier.OUTLINE_LIST_1, false);
        msAssert.areEqual(16, style.getIstd());
        msAssert.areEqual(11, style.getParaPr().getListId());

        // This list defines the style.
        List list = srcDoc.getLists().getListByListId(11);
        msAssert.areEqual(true, list.isListStyleDefinition());
        msAssert.areEqual(style, list.getStyle());

        // This list references the style.
        list = srcDoc.getLists().getListByListId(12);
        msAssert.areEqual(true, list.isListStyleReference());
        msAssert.areEqual(style, list.getStyle());


        Document dstDoc = new Document();

        // Import a paragraph that has list style formatting.
        Paragraph srcPara = srcDoc.getFirstSection().getBody().getParagraphs().get(0);
        Paragraph dstPara = (Paragraph)dstDoc.importNode(srcPara, true, ImportFormatMode.USE_DESTINATION_STYLES);
        dstDoc.getFirstSection().getBody().getParagraphs().add(dstPara);

        // The "normal" list was imported first and became the first list.
        msAssert.areEqual(1, dstPara.getListFormat().getList().getListId());
        // Word preserves source list definition identifier while importing.
        msAssert.areEqual(0x7C8F5509, dstPara.getListFormat().getList().getListDefId());

        // Check the list style was imported into the document.
        style = dstDoc.getStyles().getBySti(StyleIdentifier.OUTLINE_LIST_1, false);
        // Note the list style has a different istd in the destination document.
        msAssert.areEqual(15, style.getIstd());
        // Check the list style points to the proper list definition.
        msAssert.areEqual(2, style.getList().getListId());
        // Word preserves source list definition identifier while importing.
        msAssert.areEqual(0x49AF0B72, style.getList().getListDefId());

        // There should be two lists and two list definitions imported.
        msAssert.areEqual(2, dstDoc.getLists().getCount());
        msAssert.areEqual(2, dstDoc.getLists().getListDefCount());
        msAssert.areEqual(style, dstDoc.getLists().get(0).getStyle());
        msAssert.areEqual(style, dstDoc.getLists().get(1).getStyle());
        msAssert.areEqual(false, dstDoc.getLists().get(0).isListStyleDefinition());
        msAssert.areEqual(true, dstDoc.getLists().get(1).isListStyleDefinition());

        // And just for fun, import another paragraph and request creating another list style.
        // Style will not be imported, because list in the destination will be reused.
        srcPara = srcDoc.getFirstSection().getBody().getParagraphs().get(1);
        dstPara = (Paragraph)dstDoc.importNode(srcPara, true, ImportFormatMode.KEEP_SOURCE_FORMATTING);
        dstDoc.getFirstSection().getBody().getParagraphs().add(dstPara);

        // The "normal" list was imported first.
        msAssert.areEqual(1, dstPara.getListFormat().getList().getListId());
        msAssert.areEqual(0x7C8F5509, dstPara.getListFormat().getList().getListDefId());

        // WORDSNET-14587 Do not create new style, if such style already exists in destination.
        msAssert.isNull(dstDoc.getStyles().getByName("Normal_0", false));
        msAssert.isNull(dstDoc.getStyles().getByName("1 / a / i_0", false));

        TestUtil.saveOpen(dstDoc, "Model\\Style\\TestImportListStyle Modified", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestImportListStyleSourceDataProvider")
	public void unifiedTestImportListStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestImportListStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestImportListStyleSourceDataProvider")
	Object[][] unifiedTestImportListStyleSourceDataProvider() throws Exception
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
    /// category - list style
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCreateListStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        Style style = doc.getStyles().add(StyleType.LIST, "MyListStyle");

        // Check that the default list style created has the "1 / a / i" numbering like in MS Word.
        msAssert.areEqual(NumberStyle.ARABIC, style.getList().getListLevels().get(0).getNumberStyle());
        msAssert.areEqual(NumberStyle.LOWERCASE_LETTER, style.getList().getListLevels().get(1).getNumberStyle());
        msAssert.areEqual(NumberStyle.LOWERCASE_ROMAN, style.getList().getListLevels().get(2).getNumberStyle());

        // Modify the list style to our liking.
        style.getList().getListLevels().get(0).setNumberStyle(NumberStyle.UPPERCASE_ROMAN);

        // Create a list based on the list style and use it for some text.
        List list = doc.getLists().add(style);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.getListFormat().setList(list);
        builder.getListFormat().setListLevelNumber(0);
        builder.writeln("Level 1");
        builder.getListFormat().setListLevelNumber(1);
        builder.writeln("Level 2");
        builder.getListFormat().setListLevelNumber(0);
        builder.writeln("Level 1");
        builder.getListFormat().removeNumbers();

        doc = TestUtil.saveOpen(doc, "Model\\Style\\TestCreateListStyle", lf, sf);

        msAssert.areEqual(2, doc.getLists().getCount());
        msAssert.areEqual(2, doc.getLists().getListDefCount());
        style = doc.getStyles().get("MyListStyle");
        msAssert.areEqual(1, style.getList().getListId());
        msAssert.areEqual(NumberStyle.UPPERCASE_ROMAN, list.getListLevels().get(0).getNumberStyle());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCreateListStyleSourceDataProvider")
	public void unifiedTestCreateListStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCreateListStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCreateListStyleSourceDataProvider")
	Object[][] unifiedTestCreateListStyleSourceDataProvider() throws Exception
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

