// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import com.aspose.Debug;
import com.aspose.DrColor;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.msObject;
import com.aspose.StreamUtil;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for lists.
/// </summary>
@Test
public class TestLists extends UnifiedTestsBase
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
        RandomUtil.reset();
    }

    /// <summary>
    /// Tests how lists are copied inside one document.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCopyInSameDoc(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\List\\TestListCopyNumber", lf, sf);

        //Copy using all available methods.
        doc.getSections().get(0).prependContent(doc.getSections().get(0));
        doc.getSections().get(0).appendContent(doc.getSections().get(0));
        doc.appendChild(doc.getSections().get(0).deepClone());

        TestUtil.saveOpen(doc, "Model\\List\\TestListCopyNumber Modified", lf, sf);

        // Copying list within the same document does not create new lists.
        // This allows to achieve the same effect as in Microsoft Word.
        // If you select a paragraph formatted with a list number and copy it anywhere
        // in the document, it will continue numbering, meaning it refers to the same list.
        msAssert.areEqual(1, doc.getLists().getCount());
        msAssert.areEqual(1, doc.getLists().getListDefCount());

        NodeList paras = doc.selectNodes("//Paragraph");
        msAssert.areEqual(1, ((Paragraph)paras.get(0)).getParagraphFormat().getListId());    //First prepended item
        msAssert.areEqual(1, ((Paragraph)paras.get(2)).getParagraphFormat().getListId());    //First original item
        msAssert.areEqual(1, ((Paragraph)paras.get(4)).getParagraphFormat().getListId());    //First appended item
        msAssert.areEqual(1, ((Paragraph)paras.get(6)).getParagraphFormat().getListId());    //First item in the added section
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCopyInSameDocSourceDataProvider")
	public void unifiedTestCopyInSameDocSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCopyInSameDoc(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCopyInSameDocSourceDataProvider")
	Object[][] unifiedTestCopyInSameDocSourceDataProvider() throws Exception
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
    /// Tests how lists are copied between different documents.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCopyInDifferentDoc(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document dstDoc = new Document();

        Document srcDoc1 = TestUtil.openSaveOpen("Model\\List\\TestListCopyNumber", lf, sf);
        //Copy using all available methods
        dstDoc.getSections().get(0).prependContent(srcDoc1.getSections().get(0));
        dstDoc.getSections().get(0).appendContent(srcDoc1.getSections().get(0));
        dstDoc.appendChild(dstDoc.importNode(srcDoc1.getSections().get(0), true));

        Document srcDoc2 = TestUtil.openSaveOpen("Model\\List\\TestListCopyBullet", lf, sf);
        //Throw in some extra from yet another document.
        dstDoc.appendChild(dstDoc.importNode(srcDoc2.getSections().get(0), true));

        TestUtil.saveOpen(dstDoc, "Model\\List\\TestListCopyInDifferentDoc", lf, sf);

        msAssert.areEqual(2, dstDoc.getLists().getCount());
        msAssert.areEqual(2, dstDoc.getLists().getListDefCount());

        // It is expected that first imported list was reused.
        NodeList paras = dstDoc.selectNodes("//Paragraph");
        msAssert.areEqual(1, ((Paragraph)paras.get(0)).getParagraphFormat().getListId());    //First prepended item
        //There is one empty para break between first two lists - it is the original empty section para break.
        msAssert.areEqual(1, ((Paragraph)paras.get(3)).getParagraphFormat().getListId());    //First appended item
        msAssert.areEqual(1, ((Paragraph)paras.get(5)).getParagraphFormat().getListId());    //First copied item
        msAssert.areEqual(2, ((Paragraph)paras.get(7)).getParagraphFormat().getListId());    //List from the second document.
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCopyInDifferentDocSourceDataProvider")
	public void unifiedTestCopyInDifferentDocSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCopyInDifferentDoc(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCopyInDifferentDocSourceDataProvider")
	Object[][] unifiedTestCopyInDifferentDocSourceDataProvider() throws Exception
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
    void unifiedTestImportWithLinkedParagraphStyles(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document srcDoc = TestUtil.openSaveOpen("Model\\List\\TestImportWithLinkedParagraphStyles", lf, sf);

        // Check the original document is as expected.
        msAssert.areEqual(1, srcDoc.getLists().getCount());
        msAssert.areEqual(1, srcDoc.getLists().getListDefCount());
        List srcList = srcDoc.getLists().get(0);
        msAssert.areEqual("MyStyle1", srcList.getListLevels().get(0).getLinkedStyle().getName());
        msAssert.areEqual("MyStyle2", srcList.getListLevels().get(1).getLinkedStyle().getName());

        Document dstDoc = new Document();
        // Import the list into the destination document.
        List dstList = dstDoc.getLists().addCopy(srcList);
        msAssert.areEqual(1, dstDoc.getLists().getCount());
        msAssert.areEqual(1, srcDoc.getLists().getListDefCount());

        // Check the paragraph styles referenced by the list were imported too.
        msAssert.isNotNull(dstDoc.getStyles().get("MyStyle1"));
        msAssert.isNotNull(dstDoc.getStyles().get("MyStyle2"));
        msAssert.areEqual("MyStyle1", dstList.getListLevels().get(0).getLinkedStyle().getName());
        msAssert.areEqual("MyStyle2", dstList.getListLevels().get(1).getLinkedStyle().getName());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestImportWithLinkedParagraphStylesSourceDataProvider")
	public void unifiedTestImportWithLinkedParagraphStylesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestImportWithLinkedParagraphStyles(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestImportWithLinkedParagraphStylesSourceDataProvider")
	Object[][] unifiedTestImportWithLinkedParagraphStylesSourceDataProvider() throws Exception
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
    public void testImportWithNodeImporter() throws Exception
    {
        Document srcDoc = new Document();
        DocumentBuilder builder = new DocumentBuilder(srcDoc);
        builder.getListFormat().applyNumberDefault();
        builder.writeln("Line 1");
        builder.writeln("Line 2");

        Document dstDoc = new Document();
        dstDoc.removeAllChildren();

        NodeImporter importer = new NodeImporter(srcDoc, dstDoc, ImportFormatMode.USE_DESTINATION_STYLES);

        // Import and append a section twice.
        // Because we are importing using the same importer (same import context),
        // only one list is created in the destination document.
        dstDoc.appendChild(importer.importNode(srcDoc.getFirstSection(), true));
        dstDoc.appendChild(importer.importNode(srcDoc.getFirstSection(), true));

        msAssert.areEqual(1, dstDoc.getLists().getCount());

        TestUtil.save(dstDoc, "Model\\List\\TestImportWithNodeImporter.docx", null, false);   // FOSS: was .doc
    }

    /// <summary>
    /// The file had multiple sections and numbered list going across the sections.
    /// Deleting one section created a document with list which did not automatically
    /// renumber in word and when opening list formatting properties caused Word crashing.
    ///
    /// I'm not exactly sure what fixed it, so opening the file in MS Word and visually checking
    /// list numbering is continious and going into list formatting properties does not crash
    /// is a good idea.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDeleteSection(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\List\\TestListDeleteSection", lf, sf);
        doc.getSections().get(1).getRange().delete();
        doc = TestUtil.saveOpen(doc, "Model\\List\\TestListDeleteSection Modified", lf, sf);

        //Just a simple check that all numbered paragraphs refer to the same list.
        //Not sure if it is actually enough to make sure the problem is fixed.
        msAssert.areEqual(1, doc.getLists().getCount());
        msAssert.areEqual(1, doc.getLists().getListDefCount());

        NodeList paras = doc.selectNodes("//Paragraph");
        msAssert.areEqual(1, ((Paragraph)paras.get(0)).getParagraphFormat().getListId());
        msAssert.areEqual(1, ((Paragraph)paras.get(3)).getParagraphFormat().getListId());
        msAssert.areEqual(1, ((Paragraph)paras.get(6)).getParagraphFormat().getListId());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDeleteSectionSourceDataProvider")
	public void unifiedTestDeleteSectionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDeleteSection(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDeleteSectionSourceDataProvider")
	Object[][] unifiedTestDeleteSectionSourceDataProvider() throws Exception
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
    /// Test creation of a default bulleted list.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestSimpleBulleted(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("List heading paragraph.");
        //This starts a bulleted list.
        builder.getListFormat().applyBulletDefault();
        builder.writeln("List item 1.");
        builder.writeln("List item 2.");
        //Switches to second level in the list.
        builder.getListFormat().listIndent();
        builder.writeln("List item 1.1");
        //Returns to the first level.
        builder.getListFormat().listOutdent();
        builder.writeln("List item 3.");
        //Stop the bulleted list.
        builder.getListFormat().removeNumbers();
        builder.writeln("Normal text.");
        doc = TestUtil.saveOpen(doc, "Model\\List\\TestSimpleBulleted", lf, sf);

        //At least a few integrity checks.
        msAssert.areEqual(1, doc.getLists().getCount());

        msAssert.areEqual(1, doc.getLists().getListDefCount());
        ListDef listDef = doc.getLists().getListDefByIndex(0);
        msAssert.areEqual(9, listDef.getLevels().getCount());

        //First list item
        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 1, true);
        msAssert.areEqual(1, para.getListFormat().getListId());
        msAssert.areEqual(0, para.getListFormat().getListLevelNumber());

        //List item at second level.
        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 3, true);
        msAssert.areEqual(1, para.getListFormat().getListId());
        msAssert.areEqual(1, para.getListFormat().getListLevelNumber());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestSimpleBulletedSourceDataProvider")
	public void unifiedTestSimpleBulletedSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestSimpleBulleted(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestSimpleBulletedSourceDataProvider")
	Object[][] unifiedTestSimpleBulletedSourceDataProvider() throws Exception
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
    /// Test creation of a default numbered list.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestSimpleNumbered(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("List heading paragraph.");
        builder.getListFormat().applyNumberDefault();
        builder.writeln("List item 1.");
        builder.writeln("List item 2.");
        builder.getListFormat().listIndent();
        builder.writeln("List item 1.1");
        builder.getListFormat().listOutdent();
        builder.writeln("List item 3.");
        builder.getListFormat().removeNumbers();
        builder.writeln("Normal text.");
        doc = TestUtil.saveOpen(doc, "Model\\List\\TestSimpleNumbered", lf, sf);

        // At least some basic list property checks.
        msAssert.areEqual(1, doc.getLists().getCount());

        List list = doc.getLists().get(0);
        msAssert.areEqual(NumberStyle.ARABIC, list.getListLevels().get(0).getNumberStyle());
        msAssert.areEqual(NumberStyle.LOWERCASE_LETTER, list.getListLevels().get(1).getNumberStyle());
        msAssert.areEqual(NumberStyle.LOWERCASE_ROMAN, list.getListLevels().get(2).getNumberStyle());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestSimpleNumberedSourceDataProvider")
	public void unifiedTestSimpleNumberedSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestSimpleNumbered(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestSimpleNumberedSourceDataProvider")
	Object[][] unifiedTestSimpleNumberedSourceDataProvider() throws Exception
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
    /// Test that inserting into existing list inserts numbered or bulleted paragraphs.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestInsertIntoExistingList(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\List\\TestInsertIntoExistingList", lf, sf);
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToBookmark("Line2");
        builder.writeln("Inserted line.");
        doc = TestUtil.saveOpen(doc, "Model\\List\\TestInsertIntoExistingList Modified", lf, sf);

        //The paragraph was set to be numbered.
        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 1, true);
        msAssert.areEqual("Inserted line.\r", para.getText());
        msAssert.areEqual(1, para.getListFormat().getListId());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestInsertIntoExistingListSourceDataProvider")
	public void unifiedTestInsertIntoExistingListSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestInsertIntoExistingList(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestInsertIntoExistingListSourceDataProvider")
	Object[][] unifiedTestInsertIntoExistingListSourceDataProvider() throws Exception
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
    /// A customer complained when a list is created inside a table it does not work well.
    /// The problem was that lists were getting the same lsid assigned to them.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestMultipleLists(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getListFormat().applyBulletDefault();
        builder.writeln("List 1.1");
        builder.writeln("List 1.2");
        builder.getListFormat().removeNumbers();

        builder.getListFormat().applyBulletDefault();
        builder.writeln("List 2.1");
        builder.writeln("List 2.2");
        builder.getListFormat().removeNumbers();

        doc = TestUtil.saveOpen(doc, "Model\\List\\TestDocBuilderListMultiple", lf, sf);

        //List id within same lists are the same.
        msAssert.areEqual(getListDefForParagraph(doc, 0), getListDefForParagraph(doc, 1));
        msAssert.areEqual(getListDefForParagraph(doc, 2), getListDefForParagraph(doc, 3));
        //But they are different between the lists
        msAssert.isTrue(getListDefForParagraph(doc, 0) != getListDefForParagraph(doc, 2));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestMultipleListsSourceDataProvider")
	public void unifiedTestMultipleListsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestMultipleLists(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestMultipleListsSourceDataProvider")
	Object[][] unifiedTestMultipleListsSourceDataProvider() throws Exception
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

    private static int getListDefForParagraph(Document doc, int paragraphIndex)
    {
        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, paragraphIndex, true);
        int listId = para.getListFormat().getListId();
        return doc.getLists().getListByListId(listId).getListDefId();
    }

    /// <summary>
    /// Create a custom list and excercise its basic properties.
    /// </summary>
    @Test
    public void testListProperties() throws Exception
    {
        Document doc = new Document();
        // Check basic Lists properties.
        msAssert.areEqual(doc, doc.getLists().getDocument());
        msAssert.areEqual(0, doc.getLists().getCount());

        // Create a list and check basic properties.
        List list1 = doc.getLists().add(ListTemplate.NUMBER_DEFAULT);
        msAssert.areEqual(1, doc.getLists().getCount());
        msAssert.areEqual(list1, doc.getLists().get(0));
        msAssert.areEqual(doc, list1.getDocument());
        msAssert.areEqual(true, list1.isMultiLevel());

        // Check list has levels.
        ListLevelCollection levels = list1.getListLevels();
        msAssert.areEqual(9, levels.getCount());

        // Check properties of one level.
        ListLevel level = levels.get(0);
        msAssert.areEqual(1, level.getStartAt());
        msAssert.areEqual(NumberStyle.ARABIC, level.getNumberStyle());
        msAssert.areEqual(ListLevelAlignment.LEFT, level.getAlignment());
        msAssert.areEqual(false, level.isLegal());
        msAssert.areEqual("\u0000.", level.getNumberFormat());
        msAssert.areEqual(-1, level.getRestartAfterLevel());
        msAssert.areEqual(level.getFont(), level.getFont());    //Check Font object is created only once.
        msAssert.areEqual(false, level.getFont().getBold());
        msAssert.areEqual(doc.getStyles().get("Default Paragraph Font"), level.getFont().getStyle());
        msAssert.areEqual(36d, level.getTabPosition());
        msAssert.areEqual(18d, level.getNumberPosition());
        msAssert.areEqual(36d, level.getTextPosition());
        msAssert.areEqual(null, level.getLinkedStyle());

        // A new list id was generated and assigned to the list.
        // At the moment it is a 1-based index of the list, so we know the value.
        msAssert.areEqual(1, list1.getListId());
        msAssert.areEqual(list1, doc.getLists().getListByListId(list1.getListId()));

        // Lists with these IDs are not found.
        msAssert.areEqual(null, doc.getLists().getListByListId(0));
        msAssert.areEqual(null, doc.getLists().getListByListId(2));

        // Create one more list.
        List list2 = doc.getLists().add(ListTemplate.BULLET_DEFAULT);
        msAssert.areEqual(2, doc.getLists().getCount());
        msAssert.areEqual(list2, doc.getLists().get(1));
        msAssert.areEqual(2, list2.getListId());
        msAssert.areEqual(doc, list2.getDocument());

        // Test enumerator
        int i = 0;
        for (List list : doc.getLists())
        {
            msAssert.isNotNull(list);    // This just silences the "list declared by not used warning".
            i++;
        }
        msAssert.areEqual(2, i);
    }

    /// <summary>
    /// Test modify properties of a list level.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestModifyList(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        // This creates and applies a default list.
        builder.getListFormat().applyNumberDefault();

        // Modify the default list slightly.
        List list = builder.getListFormat().getList();

        ListLevel level = list.getListLevels().get(0);
        level.setStartAt(2);
        level.getFont().setColor(Color.RED);
        level.setNumberStyle(NumberStyle.UPPERCASE_ROMAN);
        level.setNumberFormat("\u0000.");
        level.setLinkedStyle(builder.getDocument().getStyles().get("Heading 1"));

        builder.writeln("Item 1");
        builder.writeln("Item 2");

        builder.getListFormat().removeNumbers();
        Document doc = TestUtil.saveOpen(builder.getDocument(), "Model\\List\\TestModifyList", lf, sf);

        // Check at least something that we set in the list formatting.
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(true, para.isListItem());

        list = para.getListFormat().getList();
        msAssert.areEqual(list, doc.getLists().get(0));

        level = list.getListLevels().get(0);
        msAssert.areEqual(2, level.getStartAt());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestModifyListSourceDataProvider")
	public void unifiedTestModifyListSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestModifyList(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestModifyListSourceDataProvider")
	Object[][] unifiedTestModifyListSourceDataProvider() throws Exception
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
    /// Test how paragraph indents and list text/tab/number positions correlate.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestListIndentsAndPositions(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.getListFormat().applyNumberDefault();

        // These levels go at default positions.
        builder.getListFormat().setListLevelNumber(0);
        builder.writeln("Item 1");
        builder.getListFormat().setListLevelNumber(1);
        builder.writeln("Item 2");
        builder.getListFormat().setListLevelNumber(2);
        builder.writeln("Item 3");

        // Test setting custom positions works.
        builder.getListFormat().setListLevelNumber(3);
        builder.getListFormat().getListLevel().setNumberPosition(10.0);
        builder.getListFormat().getListLevel().setTextPosition(40.0);
        builder.getListFormat().getListLevel().setTabPosition(55.0);
        builder.writeln("Item 4");

        builder.getListFormat().removeNumbers();
        Document doc = TestUtil.saveOpen(builder.getDocument(), "Model\\List\\TestListIndentsAndPositions", lf, sf);

        // 1st list level paragraph
        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 0, true);
        checkIndentsAndPositions(para, 36.0, -18, 18.0, 36.0, 36.0);

        // 2nd list level paragraph
        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 1, true);
        checkIndentsAndPositions(para, 72.0, -18, 54.0, 72.0, 72.0);

        // 3rd list level paragraph
        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 2, true);
        checkIndentsAndPositions(para, 108.0, -9, 99.0, 108.0, 108.0);

        // 4th list level paragraph
        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 3, true);
        checkIndentsAndPositions(para, 40.0, -30, 10.0, 55.0, 40.0);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListIndentsAndPositionsSourceDataProvider")
	public void unifiedTestListIndentsAndPositionsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListIndentsAndPositions(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListIndentsAndPositionsSourceDataProvider")
	Object[][] unifiedTestListIndentsAndPositionsSourceDataProvider() throws Exception
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

    private static void checkIndentsAndPositions(
        Paragraph para,
        double leftIndent,
        double firstLineIndent,
        double numberPosition,
        double tabPosition,
        double textPosition)
    {
        msAssert.areEqual(leftIndent, para.getParagraphFormat().getLeftIndent());
        msAssert.areEqual(firstLineIndent, para.getParagraphFormat().getFirstLineIndent());

        ListLevel level = para.getListFormat().getListLevel();
        msAssert.areEqual(numberPosition, level.getNumberPosition());
        msAssert.areEqual(tabPosition, level.getTabPosition());
        msAssert.areEqual(textPosition, level.getTextPosition());
    }

    /// <summary>
    /// Test that we can assign list formatting to a paragraph.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestSetList(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        List list = doc.getLists().add(ListTemplate.BULLET_DEFAULT);

        // Apply list formatting by settings the List property.
        builder.getListFormat().setList(list);
        builder.getListFormat().setListLevelNumber(0);
        builder.writeln("Item 1");
        builder.getListFormat().setListLevelNumber(1);
        builder.writeln("Item 2");
        // End list formatting.
        builder.getListFormat().setList(null);

        doc = TestUtil.saveOpen(builder.getDocument(), "Model\\List\\TestSetList", lf, sf);

        // Check the document that was just created.
        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 0, true);
        msAssert.areEqual(0, para.getListFormat().getListLevelNumber());
        msAssert.areEqual(1, para.getListFormat().getListId());

        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 1, true);
        msAssert.areEqual(1, para.getListFormat().getListLevelNumber());
        msAssert.areEqual(1, para.getListFormat().getListId());

        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 2, true);
        msAssert.areEqual(0, para.getListFormat().getListLevelNumber());
        msAssert.areEqual(0, para.getListFormat().getListId());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestSetListSourceDataProvider")
	public void unifiedTestSetListSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestSetList(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestSetListSourceDataProvider")
	Object[][] unifiedTestSetListSourceDataProvider() throws Exception
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
    /// Test attempt to set ListFormat.List to a list that belongs to a different document.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThe list belongs to a different document.\\E")
    public void testSetListWrongDocument() throws Exception
    {
        DocumentBuilder b1 = new DocumentBuilder();
        DocumentBuilder b2 = new DocumentBuilder();

        b1.getListFormat().applyNumberDefault();
        b1.writeln("Item 1");

        List list = b1.getListFormat().getList();

        // The list is from another document, must throw.
        b2.getListFormat().setList(list);
    }

    /// <summary>
    /// Set ListFormat.List to a list that is a reference to a list style.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestSetListToListStyleReference(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\List\\TestSetListToListStyle", lf, sf);

        // Check the lists are as we expect them.
        msAssert.areEqual(2, doc.getLists().getCount());
        msAssert.areEqual(2, doc.getLists().getListDefCount());

        msAssert.areEqual(true, doc.getLists().get(0).isListStyleDefinition());
        msAssert.areEqual(false, doc.getLists().get(0).isListStyleReference());

        msAssert.areEqual(false, doc.getLists().get(1).isListStyleDefinition());
        msAssert.areEqual(true, doc.getLists().get(1).isListStyleReference());

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToDocumentEnd();

        // Makes the paragraph a list item (with a list style applied to it).
        builder.getListFormat().setList(doc.getLists().get(1));
        builder.write("Item 3");

        doc = TestUtil.saveOpen(doc, "Model\\List\\TestSetListToListStyle Modified", lf, sf);

        // There are still only 2 lists in the document.
        msAssert.areEqual(2, doc.getLists().getCount());

        // First paragraph that was in the document still referns to the list that references a list style.
        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 0, true);
        msAssert.areEqual(2, para.getListFormat().getListId());

        // The paragraph that was bulleted also refers to the same list.
        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 2, true);
        msAssert.areEqual("Item 3\f", para.getText());
        msAssert.areEqual(2, para.getListFormat().getListId());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestSetListToListStyleReferenceSourceDataProvider")
	public void unifiedTestSetListToListStyleReferenceSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestSetListToListStyleReference(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestSetListToListStyleReferenceSourceDataProvider")
	Object[][] unifiedTestSetListToListStyleReferenceSourceDataProvider() throws Exception
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
    /// Test attempt to set ListFormat.List to a list that is a list style definition.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThe list is a definition of a list style.\\E")
    public void testSetListToListStyleDefinition() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestSetListToListStyle ms.docx");   // FOSS: was .doc

        List list = doc.getLists().get(0);
        msAssert.areEqual(true, list.isListStyleDefinition());

        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 0, true);

        // A paragraph cannot be applied a list that is a definition of a list style, this must throw.
        para.getListFormat().setList(list);
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestModifyListStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\List\\TestModifyListStyle", lf, sf);

        // There are two lists and two list definitions.
        msAssert.areEqual(2, doc.getLists().getCount());
        msAssert.areEqual(2, doc.getLists().getListDefCount());

        Style style = doc.getStyles().get("MyListStyle");
        // This list is a definition of the list style.
        msAssert.areEqual(doc.getLists().get(0), style.getList());
        msAssert.areEqual(0, style.getList().getListLevels().get(0).getFont().getColor().getRGB());

        // Lets modify the formatting of the list style.
        style.getList().getListLevels().get(0).getFont().setColor(Color.RED);

        // But note that the properties of the list that references the list style do not get modified.
        // The net result is that it looks okay in MS Word 2003, but earlier versions that
        // did not have list styles do not show the formatting changed.
        // I might have to update lists that are references to list styles during just before writing to disk.

        doc = TestUtil.saveOpen(doc, "Model\\List\\TestModifyListStyle Modified", lf, sf);

        style = doc.getStyles().get("MyListStyle");
        msAssert.areEqual(Color.RED.getRGB(), style.getList().getListLevels().get(0).getFont().getColor().getRGB());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestModifyListStyleSourceDataProvider")
	public void unifiedTestModifyListStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestModifyListStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestModifyListStyleSourceDataProvider")
	Object[][] unifiedTestModifyListStyleSourceDataProvider() throws Exception
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
    /// Create a new list from an existing list.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestAddCopy(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        List list1 = doc.getLists().add(ListTemplate.BULLET_DEFAULT);
        // This makes a copy of a list and its list definition.
        List list2 = doc.getLists().addCopy(list1);

        builder.getListFormat().setList(list1);
        builder.writeln("Item 1 of List 1");
        builder.getListFormat().setList(list2);
        builder.writeln("Item 1 of List 2");
        builder.getListFormat().removeNumbers();

        doc = TestUtil.saveOpen(doc, "Model\\List\\TestAddCopy", lf, sf);

        // Check the document has two lists and two list definitions.
        msAssert.areEqual(2, doc.getLists().getCount());
        msAssert.areEqual(2, doc.getLists().getListDefCount());
        msAssert.areEqual(doc.getLists().get(0).getListDef(), doc.getLists().getListDefByIndex(0));
        msAssert.areEqual(doc.getLists().get(1).getListDef(), doc.getLists().getListDefByIndex(1));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAddCopySourceDataProvider")
	public void unifiedTestAddCopySource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAddCopy(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAddCopySourceDataProvider")
	Object[][] unifiedTestAddCopySourceDataProvider() throws Exception
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
    /// Create a new list from a list that is defined in another document.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestAddCopyFromOtherDocument(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc1 = new Document();
        List list1 = doc1.getLists().add(ListTemplate.BULLET_DEFAULT);

        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getListFormat().applyNumberDefault();
        builder.writeln("Numbered");

        // Make a copy of a list from a different document.
        List list2 = doc.getLists().addCopy(list1);
        builder.getListFormat().setList(list2);
        builder.writeln("Bulleted");
        builder.getListFormat().removeNumbers();

        doc = TestUtil.saveOpen(doc, "Model\\List\\TestAddCopyFromOtherDocument", lf, sf);

        // Check the document has two lists and two list definitions.
        msAssert.areEqual(2, doc.getLists().getCount());
        msAssert.areEqual(2, doc.getLists().getListDefCount());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAddCopyFromOtherDocumentSourceDataProvider")
	public void unifiedTestAddCopyFromOtherDocumentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAddCopyFromOtherDocument(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAddCopyFromOtherDocumentSourceDataProvider")
	Object[][] unifiedTestAddCopyFromOtherDocumentSourceDataProvider() throws Exception
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
    /// Create a new list based on a list that references a list style.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestAddCopyFromListStyleReference(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\List\\TestAddFromListStyle", lf, sf);

        List list1 = doc.getLists().get(1);
        msAssert.areEqual(true, list1.isListStyleReference());

        // The newly created list does not reference the original list style in any way.
        List list2 = doc.getLists().addCopy(list1);
        msAssert.areEqual(false, list2.isListStyleDefinition());
        msAssert.areEqual(false, list2.isListStyleReference());
        msAssert.areEqual(null, list2.getStyle());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAddCopyFromListStyleReferenceSourceDataProvider")
	public void unifiedTestAddCopyFromListStyleReferenceSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAddCopyFromListStyleReference(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAddCopyFromListStyleReferenceSourceDataProvider")
	Object[][] unifiedTestAddCopyFromListStyleReferenceSourceDataProvider() throws Exception
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
    /// Create a new list based on a list that defines a list style.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestAddCopyFromListStyleDefinition(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\List\\TestAddFromListStyle", lf, sf);

        List list1 = doc.getLists().get(0);
        msAssert.areEqual(true, list1.isListStyleDefinition());

        // The newly created list does not reference the original list style in any way.
        List list2 = doc.getLists().addCopy(list1);
        msAssert.areEqual(false, list2.isListStyleDefinition());
        msAssert.areEqual(false, list2.isListStyleReference());
        msAssert.areEqual(null, list2.getStyle());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAddCopyFromListStyleDefinitionSourceDataProvider")
	public void unifiedTestAddCopyFromListStyleDefinitionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAddCopyFromListStyleDefinition(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAddCopyFromListStyleDefinitionSourceDataProvider")
	Object[][] unifiedTestAddCopyFromListStyleDefinitionSourceDataProvider() throws Exception
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
    /// Create a new list that references a list style.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestAddFromListStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\List\\TestAddFromListStyle", lf, sf);

        // This creates a list that references a list style.
        List list = doc.getLists().add(doc.getStyles().get("MyListStyle"));

        msAssert.areEqual(3, doc.getLists().getCount());
        msAssert.areEqual(3, doc.getLists().getListDefCount());
        msAssert.areEqual(3, list.getListId());
        msAssert.areEqual(false, list.isListStyleDefinition());
        msAssert.areEqual(true, list.isListStyleReference());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAddFromListStyleSourceDataProvider")
	public void unifiedTestAddFromListStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAddFromListStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAddFromListStyleSourceDataProvider")
	Object[][] unifiedTestAddFromListStyleSourceDataProvider() throws Exception
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
    /// Attempt to create a list from a non list style.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThe style is not a list style.\\E")
    public void testAddFromNonListStyle() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestAddFromListStyle ms.docx");   // FOSS: was .doc
        // This throws.
        doc.getLists().add(doc.getStyles().get("Normal"));
    }

    /// <summary>
    /// Attempt to create a list from a style that belongs to a different document.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThe list style belongs to a different document.\\E")
    public void testAddFromListStyleWrongDocument() throws Exception
    {
        Document doc1 = TestUtil.open("Model\\List\\TestAddFromListStyle ms.docx");   // FOSS: was .doc
        Document doc2 = new Document();
        // This throws.
        doc2.getLists().add(doc1.getStyles().get("MyListStyle"));
    }

    /// <summary>
    /// Test that we can create bulleted lists that look like in MS Word.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestBulletedTemplates(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.getListFormat().setList(doc.getLists().add(ListTemplate.BULLET_DISK));
        builder.writeln("Disk");
        builder.getListFormat().setList(doc.getLists().add(ListTemplate.BULLET_CIRCLE));
        builder.writeln("Circle");
        builder.getListFormat().setList(doc.getLists().add(ListTemplate.BULLET_SQUARE));
        builder.writeln("Square");
        builder.getListFormat().setList(doc.getLists().add(ListTemplate.BULLET_DIAMONDS));
        builder.writeln("Diamonds");
        builder.getListFormat().setList(doc.getLists().add(ListTemplate.BULLET_ARROW_HEAD));
        builder.writeln("Arrow");
        builder.getListFormat().setList(doc.getLists().add(ListTemplate.BULLET_TICK));
        builder.writeln("Tick");
        builder.getListFormat().removeNumbers();

        doc = TestUtil.saveOpen(doc, "Model\\List\\TestBulletedTemplates", lf, sf);
        checkBulletedTemplate(doc.getLists().get(0), "Symbol", "\uf0b7");    //disc
        checkBulletedTemplate(doc.getLists().get(1), "Courier New", "o");    //circle
        checkBulletedTemplate(doc.getLists().get(2), "Wingdings", "\uf0a7");    //square
        checkBulletedTemplate(doc.getLists().get(3), "Wingdings", "\uf076");    //diamonds
        checkBulletedTemplate(doc.getLists().get(4), "Wingdings", "\uf0d8");    //arrow
        checkBulletedTemplate(doc.getLists().get(5), "Wingdings", "\uf0fc");    //tick
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBulletedTemplatesSourceDataProvider")
	public void unifiedTestBulletedTemplatesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBulletedTemplates(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBulletedTemplatesSourceDataProvider")
	Object[][] unifiedTestBulletedTemplatesSourceDataProvider() throws Exception
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

    private static void checkBulletedTemplate(List list, String font, String bullet)
    {
        msAssert.areEqual(font, list.getListLevels().get(0).getFont().getName());
        msAssert.areEqual(bullet, list.getListLevels().get(0).getNumberFormat());
    }

    /// <summary>
    /// Test that we can create numbered lists that look like lists in MS Word.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestNumberedTemplates(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.getListFormat().setList(doc.getLists().add(ListTemplate.NUMBER_ARABIC_DOT));
        builder.writeln("1.");
        builder.getListFormat().setList(doc.getLists().add(ListTemplate.NUMBER_ARABIC_PARENTHESIS));
        builder.writeln("1)");
        builder.getListFormat().setList(doc.getLists().add(ListTemplate.NUMBER_UPPERCASE_ROMAN_DOT));
        builder.writeln("I.");
        builder.getListFormat().setList(doc.getLists().add(ListTemplate.NUMBER_UPPERCASE_LETTER_DOT));
        builder.writeln("A.");
        builder.getListFormat().setList(doc.getLists().add(ListTemplate.NUMBER_LOWERCASE_LETTER_PARENTHESIS));
        builder.writeln("a)");
        builder.getListFormat().setList(doc.getLists().add(ListTemplate.NUMBER_LOWERCASE_LETTER_DOT));
        builder.writeln("a.");
        builder.getListFormat().setList(doc.getLists().add(ListTemplate.NUMBER_LOWERCASE_ROMAN_DOT));
        builder.writeln("i.");
        builder.getListFormat().removeNumbers();

        TestUtil.saveOpen(doc, "Model\\List\\TestNumberedTemplates", lf, sf);

        // Can't be bothered to programmatically check these.
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestNumberedTemplatesSourceDataProvider")
	public void unifiedTestNumberedTemplatesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestNumberedTemplates(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestNumberedTemplatesSourceDataProvider")
	Object[][] unifiedTestNumberedTemplatesSourceDataProvider() throws Exception
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


    private static void applyOutlineListToParagraphs(ParagraphCollection paras, List list, int startParaIndex)
    {
        for (int i = 0; i < 9; i++)
        {
            Paragraph para = paras.get(startParaIndex + i);
            para.getListFormat().setList(list);
            para.getListFormat().setListLevelNumber(i);
        }
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestOutlineHeadingTemplates(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // Verify golds as created by MS Word.
        TestUtil.openSaveOpen("Model\\List\\TestOutlineHeadingTemplates", lf, sf);

        // Create new programmatically.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        List list = doc.getLists().add(ListTemplate.OUTLINE_HEADINGS_ARTICLE_SECTION);
        addOutlineHeadingParagraphs(builder, list, "Aspose.Words Outline 1");

        list = doc.getLists().add(ListTemplate.OUTLINE_HEADINGS_LEGAL);
        addOutlineHeadingParagraphs(builder, list, "Aspose.Words Outline 2");

        builder.insertBreak(BreakType.PAGE_BREAK);

        list = doc.getLists().add(ListTemplate.OUTLINE_HEADINGS_NUMBERS);
        addOutlineHeadingParagraphs(builder, list, "Aspose.Words Outline 3");

        list = doc.getLists().add(ListTemplate.OUTLINE_HEADINGS_CHAPTER);
        addOutlineHeadingParagraphs(builder, list, "Aspose.Words Outline 4");

        TestUtil.saveOpen(doc, "Model\\List\\TestOutlineHeadingTemplates Modified", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestOutlineHeadingTemplatesSourceDataProvider")
	public void unifiedTestOutlineHeadingTemplatesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestOutlineHeadingTemplates(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestOutlineHeadingTemplatesSourceDataProvider")
	Object[][] unifiedTestOutlineHeadingTemplatesSourceDataProvider() throws Exception
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

    private static void addOutlineHeadingParagraphs(DocumentBuilder builder, List list, String title)
    {
        builder.getParagraphFormat().clearFormatting();
        builder.writeln(title);

        for (int i = 0; i < 9; i++)
        {
            builder.getListFormat().setList(list);
            builder.getListFormat().setListLevelNumber(i);

            String styleName = "Heading " + Integer.toString((i + 1));
            builder.getParagraphFormat().setStyleName(styleName);
            builder.writeln(styleName);
        }

        builder.getListFormat().removeNumbers();
    }




    /// <summary>
    /// Tests picture bullets are loaded and saved in all formats.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestPictureBullet(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\List\\TestPictureBullet", lf, sf);
        msAssert.areEqual(6, doc.getLists().getPictureBulletCount());

        // This is the list in a textbox.
        ListDef listDef = doc.getLists().getListDefByIndex(1);
        msAssert.areEqual(5, listDef.getLevels().get(0).getPictureBulletId());
        msAssert.areEqual(-1, listDef.getLevels().get(1).getPictureBulletId());
        // It uses an imported WMF picture as a picture bullet.
        Shape shape = doc.getLists().getPictureBullet(5);
        msAssert.areEqual(ImageType.WMF, shape.getImageData().getImageType());

        // This is a system bookmark used in a DOC file, we don't have it in the model.
        msAssert.areEqual(null, doc.getRange().getBookmarks().get("_PictureBullets"));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestPictureBulletSourceDataProvider")
	public void unifiedTestPictureBulletSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestPictureBullet(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestPictureBulletSourceDataProvider")
	Object[][] unifiedTestPictureBulletSourceDataProvider() throws Exception
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
    /// Test cloning a complete document with picture bullets works.
    /// </summary>
    @Test
    public void testPictureBulletClone() throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\List\\TestPictureBulletClone.docx");
        Document dstDoc = srcDoc.deepClone();

        // Check the number of picture bullets was copied correctly.
        msAssert.areEqual(1, srcDoc.getLists().getPictureBulletCount());
        msAssert.areEqual(1, dstDoc.getLists().getPictureBulletCount());

        Shape srcPictureBullet = srcDoc.getLists().getPictureBullet(0);
        msAssert.areEqual(srcDoc, srcPictureBullet.getDocument());

        // Check the picture bullet shape was cloned correctly and belongs to the new document.
        Shape dstPictureBullet = dstDoc.getLists().getPictureBullet(0);
        msAssert.areEqual(dstDoc, dstPictureBullet.getDocument());

        // Check the image bytes themselves were not duplicated.
        msAssert.areEqual(srcPictureBullet.getImageData().getImageBytes(), dstPictureBullet.getImageData().getImageBytes());
    }

    @Test
    public void testPictureBulletCloneInSameDoc() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestPictureBulletClone.docx");

        doc.appendChild(doc.getSections().get(0).deepClone());
        TestUtil.save(doc, "Model\\List\\TestPictureBulletCloneInSameDoc.docx", null, false);   // FOSS: was .doc

        // Copying list within the same document does not create new lists.
        msAssert.areEqual(1, doc.getLists().getCount());
        msAssert.areEqual(1, doc.getLists().getListDefCount());
        msAssert.areEqual(1, doc.getLists().getPictureBulletCount());
    }

    @Test
    public void testPictureBulletCloneInDifferentDoc() throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\List\\TestPictureBulletClone.docx");
        Document dstDoc = TestUtil.open("Model\\List\\TestPictureBulletClone.docx");

        dstDoc.appendChild(dstDoc.importNode(srcDoc.getFirstSection(), true));
        TestUtil.save(dstDoc, "Model\\List\\TestPictureBulletCloneInDifferentDoc.docx");

        // Check that AW mimics MSW and reuses lists and definitions of lists.
        msAssert.areEqual(1, dstDoc.getLists().getCount());
        msAssert.areEqual(1, dstDoc.getLists().getListDefCount());
        msAssert.areEqual(1, dstDoc.getLists().getPictureBulletCount());

        // This is the original paragraph. It points to the original list.
        Paragraph oldPara = (Paragraph)dstDoc.getChild(NodeType.PARAGRAPH, 0, true);
        msAssert.areEqual(1, oldPara.getListFormat().getListId());
        // This is the original list. It points to the original picture bullet.
        List oldList = dstDoc.getLists().getListByListId(1);
        oldList.getListDef().getLevels().get(0).setPictureBulletId(0);
        // This is the original picture bullet.
        Shape oldShape = dstDoc.getLists().getPictureBullet(0);

        // This is the imported paragraph. It points to the reused list.
        Paragraph newPara = (Paragraph)dstDoc.getChild(NodeType.PARAGRAPH, 2, true);
        msAssert.areEqual(1, newPara.getListFormat().getListId());
        // This is the reused list. It points to the imported picture bullet.
        List newList = dstDoc.getLists().getListByListId(1);
        newList.getListDef().getLevels().get(0).setPictureBulletId(1);
        // This is the reused picture bullet.
        Shape newShape = dstDoc.getLists().getPictureBullet(0);

        // Check the shape was not cloned and was reused from the destination document.
        msAssert.isTrue(newShape == oldShape);
        msAssert.areEqual(dstDoc, newShape.getDocument());

        // Check that the image bytes were actually duplicated.
        msAssert.areEqual(oldShape.getImageData().getImageBytes(), newShape.getImageData().getImageBytes());
    }





    /// <summary>
    /// Test for <see cref="ListLabel" /> class.
    /// Checks basic constraints and set/get operations.
    /// </summary>
    @Test
    public void testListLabelBasicManip() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.getListFormat().applyBulletDefault();
        builder.writeln("List item 1.");
        builder.writeln("List item 2.");
        builder.getListFormat().removeNumbers();

        NodeCollection paras = doc.getChildNodes(NodeType.PARAGRAPH, true);
        Debug.assertTrue((paras != null) && (paras.getCount() == 3));

        Paragraph item1 = (Paragraph)paras.get(0);
        Paragraph item2 = (Paragraph)paras.get(1);

        // Checking basic constraints.
        ListLabel label1 = item1.getListLabel();
        msAssert.areSame(label1, item1.getListLabel());                    // ListLabel is lazily inited, should be the same every time is queried.
        Paragraph item1Clone = (Paragraph)item1.deepClone(true);
        msAssert.areNotSame(label1, item1Clone.getListLabel());            // Should be a different object after paragraph cloning.
        Font label1Font = label1.getFont();
        msAssert.areSame(label1Font, label1.getFont());                    // Font is lazily inited, should be the same every time is queried.

        // Checking how defaults are retrieved.
        msAssert.areEqual("Symbol", label1Font.getName());                 // Default font
        msAssert.areEqual(12, label1Font.getSize());                       // Default size
        msAssert.areEqual(0, label1Font.getColor().getRGB());              // Default color
        msAssert.areEqual(false, label1Font.getBold());                    // Default bold (BoolEx attrs are tricky)
        msAssert.areEqual(Underline.NONE, label1Font.getUnderline());      // Default underline

        msAssert.areEqual(2, label1.getListLevel().getRunPr().getCount());     // How many direct attributes are there in default list label?

        // Let's manipulate with first one and check how this affects the second.
        label1Font.setSize(14.0);
        label1Font.setColor(Color.BLUE);
        label1Font.setBold(true);
        label1Font.setUnderline(Underline.SINGLE);
        ListLabel label2 = item2.getListLabel();
        Font label2Font = label2.getFont();
        msAssert.areEqual(14, label2Font.getSize());
        msAssert.areEqual(Color.BLUE.getRGB(), label2Font.getColor().getRGB());
        msAssert.areEqual(true, label2Font.getBold());
        msAssert.areEqual(Underline.SINGLE, label2Font.getUnderline());

        msAssert.areEqual(6, label2.getListLevel().getRunPr().getCount());

        TestUtil.save(doc, "Model\\List\\TestListLabelBasicManip.docx", null, false);   // FOSS: was .doc

        // Clear the direct formatting and check again.
        label1Font.clearFormatting();

        msAssert.areEqual(0, label2.getListLevel().getRunPr().getCount());           // It's questionable. Maybe we should preserve some attributes (font name).

        // Some checks with complex attrs.
        Shading shd1 = label1Font.getShading();
        msAssert.areSame(shd1, label1Font.getShading());                   // Lazily inited, same object.

        msAssert.areEqual(1, label2.getListLevel().getRunPr().getCount());           // Implicitly added to the direct attrs.
        Shading shd2 = label2Font.getShading();
        msAssert.areSame(shd1, shd2);                                 // Also same objects because they are stored in one collection.
        shd1.setBackgroundPatternColor(DrColor.GREEN.toNativeColor());
        msAssert.areEqual(DrColor.GREEN, shd2.getBackgroundPatternColorInternal());

        msAssert.areEqual(1, label2.getListLevel().getRunPr().getCount());           // Shading properties are not written to the collection, stored in the Shading object.

        label1Font.setName("Symbol");                                     // Restore it before saving.
        TestUtil.save(doc, "Model\\List\\TestListLabelBasicManip2.docx", null, false);   // FOSS: was .doc
    }

    /// <summary>
    /// WORDSNET-9984 List label is generated incorrectly for list levels marked with IsLegal.
    /// (Don't forget calling numGen.ProcessListItem on any preceding list items for proper tesing results.)
    /// </summary>
    @Test
    public void testListIsLegal() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        String[] prompts = { "A multilevel list with different labels:", "The same but on 2nd level (1st from zero) IsLegal is applied:" };
        String[] labels = { "II.a.", "2.1." };
        for (int i = 0; i < 2; ++i)
        {
            builder.writeln(prompts[i]);
            builder.getListFormat().applyNumberDefault();
            builder.getListFormat().getListLevel().setNumberStyle(NumberStyle.UPPERCASE_ROMAN);
            builder.writeln("Item 1");
            builder.writeln("Item 2");
            builder.getListFormat().listIndent();
            builder.getListFormat().getListLevel().setNumberFormat("\u0000.\u0001.");
            builder.getListFormat().getListLevel().isLegal((i != 0));
            ListLabelUpdater.updateListLabels(doc);
            msAssert.areEqual(labels[i], builder.getCurrentParagraph().getListLabel().getLabelString());
            builder.writeln("Item 2.1");
            builder.writeln("Item 2.2");
            builder.getListFormat().listIndent();
            builder.getListFormat().getListLevel().setNumberFormat("\u0000.\u0001.\u0002.");
            builder.writeln("Item 2.2.1");
            builder.writeln("Item 2.2.2");
            builder.getListFormat().removeNumbers();
        }

        builder.writeln("Testing all number styles:");
        builder.insertCell();
        builder.write("Normal lists");
        builder.insertCell();
        builder.write("Lists with IsLegal = true");
        builder.endRow();
        for (/*NumberStyle*/int ns = NumberStyle.ARABIC; ns <= NumberStyle.NONE; ns = (ns == NumberStyle.UPPERCASE_RUSSIAN) ? NumberStyle.NONE : (ns + 1))
        {
            String nsName = DocxEnum.numberStyleToDocx(ns);
            builder.insertCell();
            builder.getListFormat().applyNumberDefault();
            builder.getListFormat().getListLevel().setNumberStyle(ns);
            ListLabelUpdater.updateListLabels(doc);
            String labelWithoutIsLegal = builder.getCurrentParagraph().getListLabel().getLabelString();
            builder.write(nsName);
            builder.insertCell();
            builder.getListFormat().applyNumberDefault();
            builder.getListFormat().getListLevel().setNumberStyle(ns);
            builder.getListFormat().getListLevel().isLegal(true);
            ListLabelUpdater.updateListLabels(doc);
            String labelWithIsLegal = builder.getCurrentParagraph().getListLabel().getLabelString();
            builder.write(nsName);
            builder.endRow();

            // Bullet and None are the special cases. But we expect that they are not affected by IsLegal flag.
            boolean isRetained = (ns == NumberStyle.ARABIC) || (ns == NumberStyle.LEADING_ZERO) || (ns == NumberStyle.BULLET) || (ns == NumberStyle.NONE);
            String expectedLabel = isRetained ? labelWithoutIsLegal : "1.";
            msAssert.areEqual(expectedLabel, labelWithIsLegal);
        }
        builder.endTable();

        TestUtil.save(doc, "Model\\List\\TestListIsLegal.docx", null, false);   // FOSS: was .doc
    }

    /// <summary>
    /// WORDSNET-4284 Enumeration of multilevel list is exported from WML to DOC not properly.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestRestartAfterHigher(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\List\\TestRestartAfterHigher", lf, sf);

        msAssert.areEqual(2, doc.getLists().getListDefCount());

        // This is a default outline list.
        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 0, true);
        List list = para.getListFormat().getList();
        msAssert.areEqual(false, list.getListLevels().get(0).isRestartAfterLevelCustom());
        msAssert.areEqual(-1, list.getListLevels().get(0).getRestartAfterLevel());
        msAssert.areEqual(false, list.getListLevels().get(1).isRestartAfterLevelCustom());
        msAssert.areEqual(0, list.getListLevels().get(1).getRestartAfterLevel());
        msAssert.areEqual(false, list.getListLevels().get(2).isRestartAfterLevelCustom());
        msAssert.areEqual(1, list.getListLevels().get(2).getRestartAfterLevel());

        // This is a modified outline list.
        para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 7, true);
        list = para.getListFormat().getList();
        // These are still default values.
        msAssert.areEqual(false, list.getListLevels().get(0).isRestartAfterLevelCustom());
        msAssert.areEqual(-1, list.getListLevels().get(0).getRestartAfterLevel());
        msAssert.areEqual(false, list.getListLevels().get(1).isRestartAfterLevelCustom());
        msAssert.areEqual(0, list.getListLevels().get(1).getRestartAfterLevel());

        // This restart was explicitly switched off. Level 8 does not restart.
        msAssert.areEqual(true, list.getListLevels().get(7).isRestartAfterLevelCustom());
        msAssert.areEqual(-1, list.getListLevels().get(7).getRestartAfterLevel());

        // This restart was explicitly set. Level 9 restart after level 5.
        // RK The actual restart level is lost in RTF. MS Word loses it too.
        msAssert.areEqual(true, list.getListLevels().get(8).isRestartAfterLevelCustom());
        if ((lf == LoadFormat.RTF) || (sf == SaveFormat.RTF))
            msAssert.areEqual(-1, list.getListLevels().get(8).getRestartAfterLevel());
        else
            msAssert.areEqual(4, list.getListLevels().get(8).getRestartAfterLevel());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRestartAfterHigherSourceDataProvider")
	public void unifiedTestRestartAfterHigherSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRestartAfterHigher(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRestartAfterHigherSourceDataProvider")
	Object[][] unifiedTestRestartAfterHigherSourceDataProvider() throws Exception
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
    void unifiedTestBulletDefault(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestBulletDefault", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBulletDefaultSourceDataProvider")
	public void unifiedTestBulletDefaultSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBulletDefault(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBulletDefaultSourceDataProvider")
	Object[][] unifiedTestBulletDefaultSourceDataProvider() throws Exception
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
    void unifiedTestListAlignment(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestListAlignment", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListAlignmentSourceDataProvider")
	public void unifiedTestListAlignmentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListAlignment(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListAlignmentSourceDataProvider")
	Object[][] unifiedTestListAlignmentSourceDataProvider() throws Exception
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
    void unifiedTestListBulleted(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestListBulleted", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListBulletedSourceDataProvider")
	public void unifiedTestListBulletedSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListBulleted(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListBulletedSourceDataProvider")
	Object[][] unifiedTestListBulletedSourceDataProvider() throws Exception
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
    void unifiedTestListIndents(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestListIndents", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListIndentsSourceDataProvider")
	public void unifiedTestListIndentsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListIndents(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListIndentsSourceDataProvider")
	Object[][] unifiedTestListIndentsSourceDataProvider() throws Exception
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
    void unifiedTestListLabelFormatting(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestListLabelFormatting", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListLabelFormattingSourceDataProvider")
	public void unifiedTestListLabelFormattingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListLabelFormatting(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListLabelFormattingSourceDataProvider")
	Object[][] unifiedTestListLabelFormattingSourceDataProvider() throws Exception
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
    void unifiedTestListNasty2(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestListNasty2", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListNasty2SourceDataProvider")
	public void unifiedTestListNasty2Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListNasty2(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListNasty2SourceDataProvider")
	Object[][] unifiedTestListNasty2SourceDataProvider() throws Exception
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
    void unifiedTestListNonTab(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestListNonTab", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListNonTabSourceDataProvider")
	public void unifiedTestListNonTabSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListNonTab(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListNonTabSourceDataProvider")
	Object[][] unifiedTestListNonTabSourceDataProvider() throws Exception
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
    void unifiedTestListNumberAndBullet(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestListNumberAndBullet", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListNumberAndBulletSourceDataProvider")
	public void unifiedTestListNumberAndBulletSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListNumberAndBullet(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListNumberAndBulletSourceDataProvider")
	Object[][] unifiedTestListNumberAndBulletSourceDataProvider() throws Exception
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
    void unifiedTestListNumbered(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestListNumbered", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListNumberedSourceDataProvider")
	public void unifiedTestListNumberedSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListNumbered(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListNumberedSourceDataProvider")
	Object[][] unifiedTestListNumberedSourceDataProvider() throws Exception
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
    void unifiedTestListNumberedNestedWithText(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestListNumberedNestedWithText", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListNumberedNestedWithTextSourceDataProvider")
	public void unifiedTestListNumberedNestedWithTextSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListNumberedNestedWithText(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListNumberedNestedWithTextSourceDataProvider")
	Object[][] unifiedTestListNumberedNestedWithTextSourceDataProvider() throws Exception
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
    void unifiedTestListNumberedRestart(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestListNumberedRestart", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListNumberedRestartSourceDataProvider")
	public void unifiedTestListNumberedRestartSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListNumberedRestart(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListNumberedRestartSourceDataProvider")
	Object[][] unifiedTestListNumberedRestartSourceDataProvider() throws Exception
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
    void unifiedTestListNumberedRestartAfterBreak(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestListNumberedRestartAfterBreak", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListNumberedRestartAfterBreakSourceDataProvider")
	public void unifiedTestListNumberedRestartAfterBreakSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListNumberedRestartAfterBreak(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListNumberedRestartAfterBreakSourceDataProvider")
	Object[][] unifiedTestListNumberedRestartAfterBreakSourceDataProvider() throws Exception
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
    void unifiedTestListNumberStyles(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestListNumberStyles", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListNumberStylesSourceDataProvider")
	public void unifiedTestListNumberStylesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListNumberStyles(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListNumberStylesSourceDataProvider")
	Object[][] unifiedTestListNumberStylesSourceDataProvider() throws Exception
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
    void unifiedTestListTab(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestListTab", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListTabSourceDataProvider")
	public void unifiedTestListTabSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListTab(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListTabSourceDataProvider")
	Object[][] unifiedTestListTabSourceDataProvider() throws Exception
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
    void unifiedTestListWithSection(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestListWithSection", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListWithSectionSourceDataProvider")
	public void unifiedTestListWithSectionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListWithSection(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListWithSectionSourceDataProvider")
	Object[][] unifiedTestListWithSectionSourceDataProvider() throws Exception
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
    void unifiedTestListWithTable(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestListWithTable", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListWithTableSourceDataProvider")
	public void unifiedTestListWithTableSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListWithTable(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListWithTableSourceDataProvider")
	Object[][] unifiedTestListWithTableSourceDataProvider() throws Exception
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
    void unifiedTestOutlineDefault(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestOutlineDefault", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestOutlineDefaultSourceDataProvider")
	public void unifiedTestOutlineDefaultSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestOutlineDefault(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestOutlineDefaultSourceDataProvider")
	Object[][] unifiedTestOutlineDefaultSourceDataProvider() throws Exception
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
    /// <para>Defect 18603 Output list labels to various formats.</para>
    /// <para>List labels for lists with the same id but in different document areas
    /// (see enum <see cref="ListLabelUpdater.ListNumberingArea"/>) were not exported correctly, fixed.</para>
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestListsInDifferentAreas(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\List\\TestListsInDifferentAreas", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListsInDifferentAreasSourceDataProvider")
	public void unifiedTestListsInDifferentAreasSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListsInDifferentAreas(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListsInDifferentAreasSourceDataProvider")
	Object[][] unifiedTestListsInDifferentAreasSourceDataProvider() throws Exception
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
    /// Verifies import/export of <see cref="ListDef.IsRestartAtEachSection"/>
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestListRestartAtEachSection(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // Not supported for WML format.
        if (sf != SaveFormat.WORD_ML)
        {
            Document doc = TestUtil.open("Model\\List\\TestRestartEachSection", lf);

            SaveOptions saveOptions = SaveOptions.createSaveOptions(sf);

            if (sf == SaveFormat.DOCX)
                saveOptions = OoxmlSaveOptions.docxWithCompliance(OoxmlComplianceCore.ISO_STRICT);

            doc = TestUtil.saveOpen(doc, "Model\\List\\TestRestartEachSection", TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD, saveOptions);

            msAssert.isTrue(doc.getLists().get(0).getListDef().isRestartAtEachSection());
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListRestartAtEachSectionSourceDataProvider")
	public void unifiedTestListRestartAtEachSectionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListRestartAtEachSection(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListRestartAtEachSectionSourceDataProvider")
	Object[][] unifiedTestListRestartAtEachSectionSourceDataProvider() throws Exception
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
    /// Tests how <see cref="ListLabelUpdater"/> works.
    /// </summary>
    @Test
    public void testListLabelUpdater() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestListLabelUpdater.docx");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        for (Paragraph para : (Iterable<Paragraph>) paras)
        {
            msAssert.areEqual(0, para.getListLabel().getLabelValue());
            msAssert.areEqual("", para.getListLabel().getLabelString());
        }

        doc.updateListLabels();

        msAssert.areEqual(1, paras.get(0).getListLabel().getLabelValue());
        msAssert.areEqual("1.", paras.get(0).getListLabel().getLabelString());

        msAssert.areEqual(1, paras.get(1).getListLabel().getLabelValue());
        msAssert.areEqual("a.", paras.get(1).getListLabel().getLabelString());

        msAssert.areEqual(0, paras.get(2).getListLabel().getLabelValue());
        msAssert.areEqual("", paras.get(2).getListLabel().getLabelString());

        msAssert.areEqual(2, paras.get(3).getListLabel().getLabelValue());
        msAssert.areEqual("2.", paras.get(3).getListLabel().getLabelString());

        msAssert.areEqual(1, paras.get(4).getListLabel().getLabelValue());
        msAssert.areEqual("a.", paras.get(4).getListLabel().getLabelString());

        msAssert.areEqual(0, paras.get(5).getListLabel().getLabelValue());
        msAssert.areEqual("", paras.get(5).getListLabel().getLabelString());

        msAssert.areEqual(3, paras.get(6).getListLabel().getLabelValue());
        msAssert.areEqual("3.", paras.get(6).getListLabel().getLabelString());

        msAssert.areEqual(4, paras.get(7).getListLabel().getLabelValue());
        msAssert.areEqual("4.", paras.get(7).getListLabel().getLabelString());

        msAssert.areEqual(1, paras.get(8).getListLabel().getLabelValue());
        msAssert.areEqual("a.", paras.get(8).getListLabel().getLabelString());

        msAssert.areEqual(2, paras.get(9).getListLabel().getLabelValue());
        msAssert.areEqual("b.", paras.get(9).getListLabel().getLabelString());

        msAssert.areEqual(0, paras.get(10).getListLabel().getLabelValue());
        msAssert.areEqual("", paras.get(10).getListLabel().getLabelString());

        msAssert.areEqual(1, paras.get(11).getListLabel().getLabelValue());
        msAssert.areEqual("1.", paras.get(11).getListLabel().getLabelString());

        msAssert.areEqual(1, paras.get(12).getListLabel().getLabelValue());
        msAssert.areEqual("\uf0b7", paras.get(12).getListLabel().getLabelString()); // Bullet sign.

        msAssert.areEqual(5, paras.get(13).getListLabel().getLabelValue());
        msAssert.areEqual("5th.", paras.get(13).getListLabel().getLabelString());
        msAssert.areEqual("Arial", paras.get(13).getListLabel().getFont().getName());
        msAssert.isTrue(paras.get(13).getListLabel().getFont().getBold());

        msAssert.areEqual(2, paras.get(14).getListLabel().getLabelValue());
        msAssert.areEqual("\uf0b7", paras.get(14).getListLabel().getLabelString()); // Bullet sign.

        // The paragraph is a list item but only contains a section break and therefore has no list label.
        msAssert.isTrue(paras.get(15).isListItem());
        msAssert.areEqual(0, paras.get(15).getListLabel().getLabelValue());
        msAssert.areEqual("", paras.get(15).getListLabel().getLabelString());
    }

    /// <summary>
    /// Tests how <see cref="ListLabelUpdater"/> works.
    /// </summary>
    @Test
    public void testListNumbersComplex() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestListNumbersComplex.docx");

        doc.updateListLabels();

        // Test headers/footers.

        ListLabel listLabel = doc.getSections().get(0).getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_FIRST).getFirstParagraph().getListLabel();
        msAssert.areEqual(5, listLabel.getLabelValue());
        msAssert.areEqual("5.", listLabel.getLabelString());

        listLabel = doc.getSections().get(0).getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_FIRST).getFirstParagraph().getListLabel();
        msAssert.areEqual(6, listLabel.getLabelValue());
        msAssert.areEqual("6.", listLabel.getLabelString());

        listLabel = doc.getSections().get(1).getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_FIRST).getFirstParagraph().getListLabel();
        msAssert.areEqual(9, listLabel.getLabelValue());
        msAssert.areEqual("9.", listLabel.getLabelString());

        listLabel = doc.getSections().get(1).getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_FIRST).getFirstParagraph().getListLabel();
        msAssert.areEqual(10, listLabel.getLabelValue());
        msAssert.areEqual("10.", listLabel.getLabelString());

        listLabel = doc.getSections().get(2).getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_FIRST).getFirstParagraph().getListLabel();
        msAssert.areEqual(13, listLabel.getLabelValue());
        msAssert.areEqual("13.", listLabel.getLabelString());

        listLabel = doc.getSections().get(2).getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_FIRST).getFirstParagraph().getListLabel();
        msAssert.areEqual(14, listLabel.getLabelValue());
        msAssert.areEqual("14.", listLabel.getLabelString());

        listLabel = doc.getSections().get(3).getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_FIRST).getFirstParagraph().getListLabel();
        msAssert.areEqual(15, listLabel.getLabelValue());
        msAssert.areEqual("15.", listLabel.getLabelString());

        listLabel = doc.getSections().get(3).getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_FIRST).getFirstParagraph().getListLabel();
        msAssert.areEqual(16, listLabel.getLabelValue());
        msAssert.areEqual("16.", listLabel.getLabelString());

        // Test body paragraphs.

        // Section 1.

        NodeCollection paras = doc.getSections().get(0).getBody().getChildNodes(NodeType.PARAGRAPH, true);
        msAssert.areEqual(4, paras.getCount());

        Paragraph para = (Paragraph)paras.get(0);
        msAssert.areEqual(1, para.getListLabel().getLabelValue());
        msAssert.areEqual("1.", para.getListLabel().getLabelString());

        para = (Paragraph)paras.get(1);
        msAssert.isTrue(para.getParentNode().getNodeType() == NodeType.SHAPE);  // The paragraph in a textbox.
        msAssert.areEqual(1, para.getListLabel().getLabelValue());
        msAssert.areEqual("1.", para.getListLabel().getLabelString());

        para = (Paragraph)paras.get(2);
        msAssert.areEqual(2, para.getListLabel().getLabelValue());
        msAssert.areEqual("2.", para.getListLabel().getLabelString());

        para = (Paragraph)paras.get(3);
        msAssert.areEqual(0, para.getListLabel().getLabelValue());
        msAssert.areEqual("", para.getListLabel().getLabelString());

        // Section 2.

        paras = doc.getSections().get(1).getBody().getChildNodes(NodeType.PARAGRAPH, true);
        msAssert.areEqual(12, paras.getCount());

        para = (Paragraph)paras.get(0);
        msAssert.isTrue(para.getParaPr().isFloating()); // The text frame.
        msAssert.areEqual(3, para.getListLabel().getLabelValue());
        msAssert.areEqual("3.", para.getListLabel().getLabelString());

        para = (Paragraph)paras.get(1);
        msAssert.isTrue(para.getParentNode().getNodeType() == NodeType.FOOTNOTE);  // The paragraph in a footnote.
        msAssert.areEqual(1, para.getListLabel().getLabelValue());
        msAssert.areEqual("1.", para.getListLabel().getLabelString());

        para = (Paragraph)paras.get(2);
        msAssert.areEqual(4, para.getListLabel().getLabelValue());
        msAssert.areEqual("4.", para.getListLabel().getLabelString());

        para = (Paragraph)paras.get(3);
        msAssert.areEqual(5, para.getListLabel().getLabelValue());
        msAssert.areEqual("5.", para.getListLabel().getLabelString());

        para = (Paragraph)paras.get(4);
        msAssert.isTrue(para.getParentNode().getNodeType() == NodeType.FOOTNOTE);  // The paragraph in a footnote.
        msAssert.areEqual(2, para.getListLabel().getLabelValue());
        msAssert.areEqual("2.", para.getListLabel().getLabelString());

        para = (Paragraph)paras.get(5);
        msAssert.isTrue(para.getParentNode().getNodeType() == NodeType.CELL);  // The paragraph in a table cell.
        msAssert.areEqual(6, para.getListLabel().getLabelValue());
        msAssert.areEqual("6.", para.getListLabel().getLabelString());

        para = (Paragraph)paras.get(6);
        msAssert.isTrue(para.getParentNode().getNodeType() == NodeType.COMMENT);  // The paragraph in a comment.
        msAssert.areEqual(1, para.getListLabel().getLabelValue());
        msAssert.areEqual("1.", para.getListLabel().getLabelString());

        para = (Paragraph)paras.get(7);
        msAssert.areEqual(7, para.getListLabel().getLabelValue());
        msAssert.areEqual("7.", para.getListLabel().getLabelString());

        para = (Paragraph)paras.get(8);
        msAssert.areEqual(8, para.getListLabel().getLabelValue());
        msAssert.areEqual("8.", para.getListLabel().getLabelString());

        para = (Paragraph)paras.get(9);
        msAssert.isTrue(para.getParentNode().getNodeType() == NodeType.FOOTNOTE);  // The paragraph in an endnote.
        msAssert.areEqual(1, para.getListLabel().getLabelValue());
        msAssert.areEqual("1.", para.getListLabel().getLabelString());

        para = (Paragraph)paras.get(10);
        msAssert.areEqual(0, para.getListLabel().getLabelValue());
        msAssert.areEqual("", para.getListLabel().getLabelString());

        para = (Paragraph)paras.get(11);
        msAssert.areEqual(0, para.getListLabel().getLabelValue());
        msAssert.areEqual("", para.getListLabel().getLabelString());

        // Section 3.

        paras = doc.getSections().get(2).getBody().getChildNodes(NodeType.PARAGRAPH, true);
        msAssert.areEqual(1, paras.getCount());

        para = (Paragraph)paras.get(0);
        msAssert.areEqual(0, para.getListLabel().getLabelValue());
        msAssert.areEqual("", para.getListLabel().getLabelString());

        // Section 4.
        // WORDSNET-4826 AW does not support section inside markup so SDT node is deleted and content partially moved to next section.

        paras = doc.getSections().get(3).getBody().getChildNodes(NodeType.PARAGRAPH, true);
        msAssert.areEqual(1, paras.getCount());

        para = (Paragraph)paras.get(0);
        msAssert.isFalse(para.getParentNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG);  // SDT node was deleted.
        msAssert.areEqual(0, para.getListLabel().getLabelValue());
        msAssert.areEqual("", para.getListLabel().getLabelString());

        // Section 5.

        paras = doc.getSections().get(4).getBody().getChildNodes(NodeType.PARAGRAPH, true);
        msAssert.areEqual(2, paras.getCount());

        para = (Paragraph)paras.get(0);
        msAssert.areEqual(11, para.getListLabel().getLabelValue());
        msAssert.areEqual("11.", para.getListLabel().getLabelString());

        para = (Paragraph)paras.get(1);
        msAssert.areEqual(2, para.getListLabel().getLabelValue());
        msAssert.areEqual("2.", para.getListLabel().getLabelString());
    }


    /// <summary>
    /// Tests updating list labels by <see cref="Document.UpdateListLabels"/> after changing of document.
    /// </summary>
    @Test
    public void testListNumbersUpdating() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getListFormat().applyNumberDefault();
        builder.writeln("First");
        builder.writeln("Second");

        NodeCollection paras = doc.getSections().get(0).getBody().getChildNodes(NodeType.PARAGRAPH, true);

        doc.updateListLabels();
        msAssert.areEqual(2, ((Paragraph)paras.get(1)).getListLabel().getLabelValue());
        msAssert.areEqual("2.", ((Paragraph)paras.get(1)).getListLabel().getLabelString());

        ((Paragraph)paras.get(1)).getListFormat().removeNumbers();
        doc.updateListLabels();
        msAssert.areEqual(0, ((Paragraph)paras.get(1)).getListLabel().getLabelValue());
        msAssert.areEqual("", ((Paragraph)paras.get(1)).getListLabel().getLabelString());

        ((Paragraph)paras.get(0)).getListFormat().removeNumbers();
        ((Paragraph)paras.get(1)).getListFormat().applyNumberDefault();
        doc.updateListLabels();
        msAssert.areEqual(1, ((Paragraph)paras.get(1)).getListLabel().getLabelValue());
        msAssert.areEqual("1.", ((Paragraph)paras.get(1)).getListLabel().getLabelString());

        ((Paragraph)paras.get(0)).getListFormat().setListId(((Paragraph)paras.get(1)).getListFormat().getListId());
        doc.updateListLabels();
        msAssert.areEqual(2, ((Paragraph)paras.get(1)).getListLabel().getLabelValue());
        msAssert.areEqual("2.", ((Paragraph)paras.get(1)).getListLabel().getLabelString());

        ((Paragraph)paras.get(0)).getListFormat().applyBulletDefault();
        doc.updateListLabels();
        msAssert.areEqual(1, ((Paragraph)paras.get(0)).getListLabel().getLabelValue());
        msAssert.areEqual("\uF0B7", ((Paragraph)paras.get(0)).getListLabel().getLabelString());
        msAssert.areEqual(1, ((Paragraph)paras.get(1)).getListLabel().getLabelValue());
        msAssert.areEqual("1.", ((Paragraph)paras.get(1)).getListLabel().getLabelString());

    }


    /// <summary>
    /// <para>WORDSNET-24241 Restarting of list numbering depending on change
    /// of number of higher level was incorrect, fixed.</para>
    /// <para>Tests small document with custom Restart List After property for various list levels.</para>
    /// </summary>
    @Test
    public void testDefect24241() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestDefect24241.docx");
        doc.updateListLabels();

        String[] labelStrings = {
                "1.", "i.", "ii.", "1st.", "iii.", "2nd.", "b.",
                "iv.", "2.", "1st.", "ii.", "3.", "4."
            };

        NodeCollection paras = doc.getSections().get(0).getBody().getChildNodes(NodeType.PARAGRAPH, true);

        for (int i = 0; i < labelStrings.length; i++)
            msAssert.areEqual(labelStrings[i], ((Paragraph)paras.get(i)).getListLabel().getLabelString(), "for i = " + i);
    }

    /// <summary>
    /// WORDSNET-18137 Aspose.Words returns incorrect LeftIndent of paragraph.
    /// Fixed. Turned out to be same earlier fixed 1545, but this one was not fixed in the bottom-up attribute expansion.
    /// The problem was that when a paragraph is explicitly marked as non list item, then MS Word resets its indents
    /// to zero in the attribute expansion algorithm just before expanding the direct formatting.
    /// </summary>
    @Test
    public void testDefect18137() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestDefect18137.docx");

        // This uses the bottom-up attribute expansion. There was a problem in it.
        Paragraph para = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual(0.0, para.getParagraphFormat().getLeftIndent());            // This was returned incorrect.
        msAssert.areEqual(10.8, para.getParagraphFormat().getFirstLineIndent());

        // This uses the top-bottom attribute expansion, it already had a fix for 1545 which as the same problem.
        ParaPr paraPr = para.getExpandedParaPr(ParaPrExpandFlags.NORMAL);
        msAssert.areEqual(0, ConvertUtilCore.twipToPoint(paraPr.getLeftIndent()));
        msAssert.areEqual(10.8, ConvertUtilCore.twipToPoint(paraPr.getFirstLineIndent()));

        // This paragraph had correct values, just check them.
        para = doc.getFirstSection().getBody().getParagraphs().get(1);
        msAssert.areEqual(1.0, para.getParagraphFormat().getLeftIndent());
        msAssert.areEqual(10.8, para.getParagraphFormat().getFirstLineIndent());
    }


    /// <summary>
    /// WORDSNET-5272 Custom footnote marks are rendered as numbers.
    /// Added conversion from ChicagoManualOfStyle to Unicode numbering format.
    /// </summary>
    @Test
    public void testJira5272()
    {
        String[] etalons = new String[] {"*", "†", "‡", "§", "**", "††", "‡‡", "§§", "***"};

        final int N_SAMPLES = 9;

        for (int number = 1; number <= N_SAMPLES; number++)
            msAssert.areEqual(etalons[number - 1], NumberConverter.numberToString(number, NumberStyle.CHICAGO_MANUAL, true));
    }


    /// <summary>
    /// WORDSNET-7856 MS Word 2007 incorrectly layouts List at second ListLevel in DOC.
    /// HybridMultiLevel type is changed to MultiLevel when ListFactory creates Number list.
    /// </summary>
    /// <remarks>
    /// AM. HybridMultiLevel list type causes such behavior probably because SttbRgtplc structure which specifies
    /// the bullet/numbering formats for a hybrid bulleted/numbered multi-level list is not processed.
    /// I carefully tested output files including binary DOC and it seems that everything OK.
    /// </remarks>
    @Test
    public void testJira7856() throws Exception
    {
        Document doc = new Document();

        List list = doc.getLists().add(ListTemplate.NUMBER_ARABIC_DOT);

        ListLevel level1 = list.getListLevels().get(0);
        level1.setNumberStyle(NumberStyle.ARABIC);
        level1.setNumberFormat("\u0000");

        ListLevel level2 = list.getListLevels().get(1);
        level2.setNumberStyle(NumberStyle.ARABIC);
        level2.setNumberFormat("\u0000.\u0001");

        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getListFormat().setList(list);
        builder.writeln("The quick brown fox...");
        builder.writeln("The quick brown fox...");

        builder.getListFormat().listIndent();
        builder.writeln("jumped over the lazy dog.");
        builder.writeln("jumped over the lazy dog.");

        builder.getListFormat().listOutdent();
        builder.writeln("The quick brown fox...");

        doc = TestUtil.saveOpen(builder.getDocument(), "Model\\List\\TestJira7856.docx", null, false);   // FOSS: was .doc
        msAssert.areEqual(ListType.MULTI_LEVEL, doc.getLists().getListDefByIndex(0).getListType());
    }

    /// <summary>
    /// WORDSNET-8684 Amount of Tab space between List number and Text is incorrectly calculated during rendering to PDF.
    /// andrnosk: The original problem occurred because LegacySpace is specified.
    /// In this case we need to know the length of the label text.
    /// To calculate the length of the label of text we need to know its formatting and measure the string.
    /// I have added mechanism to calculate length of the label of text during converting LegacyListFormatting.
    /// </summary>
    // FOSS: TestJira8684 removed — it triggered LegacyListFormatting conversion via a PDF save and
    // asserted layout-computed tab positions (GetExpandedParaPr(Layout)); layout/rendering is gone.

    /// <summary>
    /// WORDSNET-8699 List changes its number format after appending one document to another.
    /// The problem occurs because of incorrectly calculated NextAvaliableListId and NextAvaliableListDefId.
    /// Fixed the code so it calculate them correctly during importing.
    /// </summary>
    @Test
    public void testJira8699() throws Exception
    {
        Document dstDoc = TestUtil.open("Model\\List\\TestJira8699A.docx");
        Document srcDoc = TestUtil.open("Model\\List\\TestJira8699B.docx");

        dstDoc.appendDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);
        dstDoc = TestUtil.saveOpen(dstDoc, "Model\\List\\TestJira8699.docx", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        ListFormat listFormat = dstDoc.getSections().get(0).getBody().getParagraphs().get(1).getListFormat();
        msAssert.areEqual(4, listFormat.getListId());
        // Check number format.
        msAssert.areEqual("\u0000.", listFormat.getListLevel().getNumberFormat());

        listFormat = dstDoc.getSections().get(1).getBody().getParagraphs().get(1).getListFormat();
        msAssert.areEqual(6, listFormat.getListId());
        msAssert.areEqual("\u0000.", listFormat.getListLevel().getNumberFormat());
    }



    /// <summary>
    /// WORDSNET-9517 Bullets do not render in PDF for tracked document
    /// Improved ListLabelUpdater behaviour related to revisions.
    /// </summary>
    @Test
    public void testJira9517() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestJira9517.docx");
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();

        msAssert.areEqual("", para.getListLabel().getLabelString());
        doc.updateListLabels();   // FOSS: was a Pdf save that triggered the list-label update.

        // Verify that label was updated correctly.
        msAssert.areEqual("", para.getListLabel().getLabelStringOriginal());
        msAssert.areEqual("1.", para.getListLabel().getLabelStringFinal());
    }

    /// <summary>
    /// WORDSNET-16207 List Label is not rendered correctly in output PNG.
    /// It seems Word gets RestartAfterLevel value from ListLevel but not from Override.
    /// Case: abstractNum.lvlRestart = 0, lvlOverride doen't have lvlRestart
    /// </summary>
    @Test
    public void testJira16207a() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestJira16207a.xml");
        doc.updateListLabels();

        NodeCollection paras = doc.getSections().get(0).getBody().getChildNodes(NodeType.PARAGRAPH, true);

        msAssert.areEqual("7.20.", ((Paragraph)paras.get(26)).getListLabel().getLabelString());

    }

    /// <summary>
    /// WORDSNET-16207 List Label is not rendered correctly in output PNG.
    /// Case: abstractNum doen't have lvlRestart, lvlOverride.lvlRestart = 0
    /// </summary>
    @Test
    public void testJira16207b() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestJira16207b.xml");
        doc.updateListLabels();

        NodeCollection paras = doc.getSections().get(0).getBody().getChildNodes(NodeType.PARAGRAPH, true);

        msAssert.areEqual("7.3.", ((Paragraph)paras.get(26)).getListLabel().getLabelString());

    }



    /// <summary>
    /// WORDSNET-10219 ListLevel.Font return incorrect values for font name/size.
    /// Added IRunAttrSource to ListLevel to inherit formatting from parent object.
    /// </summary>
    /// <remarks>
    /// AM. Main problem here that we should give customer different ListLevel objects
    /// for different paragraphs althought all paragraphs can refer to single ListLevel in list definition.
    /// This is implemented using inherited ListLevel objects which refers to both paragraph and real ListLevel in list definition.
    /// TestJira10219A below illustrates this.
    /// </remarks>
    @Test
    public void testJira10219() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestJira10219.docx");

        Paragraph tocItemPara = (Paragraph)doc.getFirstSection().getBody().getChild(NodeType.PARAGRAPH, 21, true);
        msAssert.areEqual("Calibri", tocItemPara.getParagraphBreakFont().getName());

        ListLevel level = tocItemPara.getListFormat().getListLevel();
        Font font = level.getFont();

        msAssert.areEqual("Calibri", font.getName());
        msAssert.areEqual(11, font.getSize());
    }

    /// <summary>
    /// Relates to WORDSNET-10219
    /// Checks that we expose inherited copy properly in ListFormat.ListLevel.
    /// </summary>
    @Test
    public void testJira10219A() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestJira10219A.docx");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        ListLevel levelPara0 = paras.get(0).getListFormat().getListLevel();
        msAssert.areEqual("Times New Roman", levelPara0.getFont().getName());

        // Check that although ListLevel is inherited from one ListLevel we get different font name in Font object.
        ListLevel levelPara1 = paras.get(1).getListFormat().getListLevel();
        msAssert.areEqual("Times New Roman", levelPara0.getFont().getName());
        msAssert.areEqual("Tahoma", levelPara1.getFont().getName());

        // Check that ListLevel is cached.
        ListLevel anotherLevelPara0 = paras.get(0).getListFormat().getListLevel();
        msAssert.isTrue(msObject.referenceEquals(levelPara0, anotherLevelPara0));

        // Check that ListFormat.ListLevel reflects parent paragraph changes.
        paras.get(0).getParagraphBreakFont().setName("Courier");
        msAssert.areEqual("Courier", levelPara0.getFont().getName());

        // Test another list and check that ListLevel cache is invalidated upon ListFormat operations.
        Paragraph p = paras.get(3);
        msAssert.areEqual("Algerian", p.getListFormat().getListLevel().getFont().getName());
        p.getListFormat().setListLevelNumber(p.getListFormat().getListLevelNumber() + 1)/*Property++*/;
        msAssert.areEqual("Calibri", p.getListFormat().getListLevel().getFont().getName());
    }




    /// <summary>
    /// WORDSNET-11324 Issues with table heights in PDF
    /// Problematic paragraph has LisLevel equal to 12 while max level is 8.
    /// </summary>
    /// <remarks>
    /// AM. This is very weird issue. Word resaves this file preserving invalid list level but renders it in special way.
    /// If document is saved to binary DOC format Word even produces invalid document (Protected View is shown).
    ///
    /// Problematic paragraph is shown in Word like a no-list paragraph with additional Tab character at paragraph start and
    /// I decided to correct the paragraph this way.
    /// </remarks>
    @Test (dataProvider = "testJira11324DataProvider")
    public void testJira11324(/*MsWordVersion*/int version) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setMswVersion(version);

        Document doc = TestUtil.open("Model\\List\\TestJira11324.docx", lo);

        Paragraph p = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getCells().get(2).getFirstParagraph();
        msAssert.areEqual(0, p.getParaPr().get(ParaAttr.LIST_ID));
        msAssert.areEqual(12, p.getParaPr().get(ParaAttr.LIST_LEVEL));

        String expected = version <= MsWordVersion.WORD_2013
            ? "\u0009This is the task\r"
            : "This is the task\r";

        msAssert.areEqual(expected, p.getText());

        msAssert.areEqual(0, p.getExpandedParaPr(ParaPrExpandFlags.LAYOUT).getLeftIndent());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira11324DataProvider")
	public static Object[][] testJira11324DataProvider() throws Exception
	{
		return new Object[][]
		{
			{MsWordVersion.WORD_2013},
			{MsWordVersion.WORD_2019},
		};
	}


    /// <summary>
    /// Relates to TestJira11352. Tests behavior in various scenarios.
    /// </summary>
    @Test
    public void testJira11352A() throws Exception
    {
        // Paragraph has direct negative indent. Word updates LeftIndent by left indent + first line indent.
        testApplyBulletDefault("Model\\List\\TestLeftIndentNegative.docx", -567, -207);

        // paragraph has direct positive indent. Word updates LeftIndent by left indent + first line indent.
        testApplyBulletDefault("Model\\List\\TestLeftIndentPositive.docx", 1134, 1494);

        // Paragraph has no direct indent. Word doesn't do anything with paragraph left indent.
        testApplyBulletDefault("Model\\List\\TestLeftIndentNone.docx", null, null);

        // Paragraph has zero direct left indent and overrides value inherited from style. Word removes it.
        testApplyBulletDefault("Model\\List\\TestLeftIndentOverriden.docx", 0, null);
    }



    /// <summary>
    /// WORDSNET-11043 Document generated by Aspose.Words opens in 'Protected view'
    /// Added warning for exceeding list count limit.
    /// </summary>
    @Test
    public void testJira11043() throws Exception
    {
        WarningInfoCollection warnings = new WarningInfoCollection();
        Document doc = new Document();
        doc.setWarningCallback(warnings);
        DocumentBuilder docBuilder = new DocumentBuilder(doc);

        for (int count = 0; count < 10250; count++)
        {
            if (count % 5 == 0)
            {
                docBuilder.getParagraphFormat().clearFormatting();
                docBuilder.writeln();
                docBuilder.writeln("Test");
                docBuilder.getListFormat().applyBulletDefault();
            }
            docBuilder.write("Test " + count);
            docBuilder.writeln();
        }

        msAssert.isTrue(TestUtil.containsWarningBySource(warnings, WarningSource.VALIDATOR, WarningStrings.LIST_COUNT_EXCEED_LIMIT));
    }


    /// <summary>
    /// WORDSNET-11867 Add feature to get/set the Shape/Image of Picture Bullet.
    /// Create PictureBullet from scratch.
    /// </summary>
    @Test
    public void testJira11867A() throws Exception
    {
        Document doc = new Document();

        // Create a list based on one of the Microsoft Word list templates.
        List list = doc.getLists().add(ListTemplate.NUMBER_DEFAULT);
        ListLevel level1 = list.getListLevels().get(0);

        // Check PictureBullet shape is null.
        Shape shapeBullet = level1.getPictureBullet();
        msAssert.isNull(shapeBullet);

        byte[] imageBytes = StreamUtil.copyFileToByteArray(TestUtil.buildTestFileName("DocBuilder\\Image\\Bullet1.png"));
        MemoryStream stream = new MemoryStream(imageBytes);

        // Set PictureBullet using stream.
        level1.createPictureBullet();
        level1.getImageData().setImage(stream);

        ListLevel level2 = list.getListLevels().get(1);
        level2.createPictureBullet();
        level2.getImageData().setImage(TestUtil.buildTestFileName("DocBuilder\\Image\\Bullet2.png"));

        // Create a list based on one of the Microsoft Word list templates.
        List list2 = doc.getLists().add(ListTemplate.BULLET_DEFAULT);

        ListLevel levelbullet2 = list2.getListLevels().get(1);
        levelbullet2.createPictureBullet();
        levelbullet2.getImageData().setImage(TestUtil.buildTestFileName("DocBuilder\\Image\\Bullet3.png"));

        // Now add some text that uses the list that we created.
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getListFormat().setList(list);

        builder.writeln("The quick brown fox...");
        builder.getListFormat().listIndent();

        builder.writeln("jumped over the lazy dog.");
        builder.getListFormat().listOutdent();

        builder.writeln("The quick brown fox...");

        // Here we change list.
        builder.getListFormat().setList(list2);
        builder.getListFormat().listIndent();

        builder.writeln("jumped over the lazy dog.");

        // Here we change list again.
        builder.getListFormat().setList(list);
        builder.getListFormat().listOutdent();
        builder.writeln("The quick brown fox...");

        builder.getListFormat().removeNumbers();

        doc = TestUtil.saveOpen(doc, "Model\\List\\TestJira11867A", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        ListCollection listCollection = doc.getLists();

        msAssert.isTrue(listCollection.get(0).getListLevels().get(0).hasPictureBullet());

        // Check all picture bullets are applied.
        msAssert.areEqual(839, listCollection.getPictureBullet(0).getImageData().getImageBytes().length);
        msAssert.areEqual(1737, listCollection.getPictureBullet(1).getImageData().getImageBytes().length);
        msAssert.areEqual(1653, listCollection.getPictureBullet(2).getImageData().getImageBytes().length);
    }

    /// <summary>
    /// WORDSNET-11867 Add feature to get/set the Shape/Image of Picture Bullet.
    /// Reset PictureBullet image data.
    /// </summary>
    @Test
    public void testJira11867B() throws Exception
    {
        String imageSrc1 = TestUtil.buildTestFileName("DocBuilder\\Image\\Bullet1.png");
        String imageSrc2 = TestUtil.buildTestFileName("DocBuilder\\Image\\Bullet2.png");
        String imageSrc3 = TestUtil.buildTestFileName("DocBuilder\\Image\\Bullet3.png");

        Document doc = TestUtil.open("Model\\List\\TestJira11867B.docx");

        msAssert.areEqual(3, doc.getLists().getPictureBulletCount());

        // Set picture bullets to different lists.
        List list0 = doc.getLists().get(0);
        ListLevel listLevel0 = list0.getListLevels().get(0);
        msAssert.isTrue(listLevel0.hasPictureBullet());

        // Get PictureBullet shape.
        Shape shapeBullet = listLevel0.getPictureBullet();

        // Check its size.
        msAssert.areEqual(27850, shapeBullet.getImageData().getImageBytes().length);

        // Set new image.
        listLevel0.getImageData().setImage(imageSrc1);

        shapeBullet = listLevel0.getPictureBullet();
        msAssert.areEqual(839, shapeBullet.getImageData().getImageBytes().length);

        List list1 = doc.getLists().get(1);

        // Before setting picture bullet.
        msAssert.isFalse(list1.getListLevels().get(0).hasPictureBullet());
        list1.getListLevels().get(0).createPictureBullet();
        list1.getListLevels().get(0).getImageData().setImage(imageSrc1);

        // After setting picture bullet.
        msAssert.isTrue(list1.getListLevels().get(0).hasPictureBullet());

        list1.getListLevels().get(1).createPictureBullet();
        list1.getListLevels().get(1).getImageData().setImage(imageSrc2);
        list1.getListLevels().get(2).createPictureBullet();
        list1.getListLevels().get(2).getImageData().setImage(imageSrc3);

        doc.getLists().get(2).getListLevels().get(0).createPictureBullet();
        doc.getLists().get(2).getListLevels().get(0).getImageData().setImage(imageSrc1);

        doc.getLists().get(3).getListLevels().get(0).getImageData().setImage(imageSrc1);
        doc.getLists().get(4).getListLevels().get(0).getImageData().setImage(imageSrc2);
        doc.getLists().get(5).getListLevels().get(0).getImageData().setImage(imageSrc3);

        doc = TestUtil.saveOpen(doc, "Model\\List\\TestJira11867B", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        ListCollection listCollection = doc.getLists();

        msAssert.isTrue(listCollection.get(0).getListLevels().get(0).hasPictureBullet());
        msAssert.areEqual(7, listCollection.getPictureBulletCount());

        ListLevel listLevel00 = listCollection.get(0).getListLevels().get(0);

        // Delete picture bullet.
        listLevel00.deletePictureBullet();
        msAssert.isFalse(listLevel00.hasPictureBullet());
        msAssert.isNull(listLevel00.getPictureBullet());
        msAssert.isNull(listLevel00.getImageData());
    }

    /// <summary>
    /// WORDSNET-11867 Add feature to get/set the Shape/Image of Picture Bullet.
    /// Universal use-case.
    /// </summary>
    @Test
    public void testJira11867C() throws Exception
    {
        String imageSrc1 = TestUtil.buildTestFileName("DocBuilder\\Image\\Bullet1.png");

        // Create a document and document builder.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Create a list.
        List list = doc.getLists().add(ListTemplate.BULLET_CIRCLE);

        ListLevel listLevel0 = list.getListLevels().get(0);
        // Configure list if necessary
        // Configure first level.
        msAssert.isFalse(listLevel0.hasPictureBullet());
        msAssert.isNull(listLevel0.getImageData());

        // Create picture bullet with default red cross image for the current list level.
        listLevel0.createPictureBullet();
        msAssert.isTrue(listLevel0.hasPictureBullet());
        msAssert.isNotNull(listLevel0.getImageData());
        msAssert.areEqual(924, listLevel0.getImageData().getImageBytes().length);

        // Set your own picture bullet image.
        listLevel0.getImageData().setImage(imageSrc1);
        msAssert.areEqual(839, listLevel0.getImageData().getImageBytes().length);

        // Configure second level
        list.getListLevels().get(1).setNumberStyle(NumberStyle.ARABIC);
        list.getListLevels().get(1).setNumberFormat("\u0001.");
        // Configure next levels if necessary
        // ........................................

        // Apply the list to the current paragraph.
        builder.getListFormat().setList(list);
        builder.writeln("item 1");

        // Increase level.
        builder.getListFormat().listIndent();
        builder.writeln("item 1.1");
        builder.writeln("item 1.2");
        builder.writeln("item 1.3");

        // Decrease level
        builder.getListFormat().listOutdent();
        builder.write("item 2");

        // Save output.
        doc = TestUtil.saveOpen(doc, "Model\\List\\TestJira11867C", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        listLevel0 = doc.getLists().get(0).getListLevels().get(0);

        msAssert.isTrue(listLevel0.hasPictureBullet());
        msAssert.areEqual(839, listLevel0.getImageData().getImageBytes().length);

        // Delete picture bullet.
        // Default bullet will be shown after deleting.
        listLevel0.deletePictureBullet();

        msAssert.isFalse(listLevel0.hasPictureBullet());
        msAssert.isNull(listLevel0.getImageData());
    }




    /// <summary>
    /// WORDSNET-12929 A document, in which a picture bullet list is created with using Aspose.Words,
    /// was not opened in MS Word for Mac. Bullet shape was created with <see cref="ShapeType.NonPrimitive"/> type,
    /// but it should be <see cref="ShapeType.Image"/>.
    /// </summary>
    @Test
    public void testJira12929() throws Exception
    {
        Document document = new Document();
        ListCollection lists = document.getLists();
        msAssert.areEqual(0, lists.getPictureBulletCount());

        List list = lists.add(ListTemplate.BULLET_DIAMONDS);
        list.getListLevels().get(0).createPictureBullet();
        msAssert.areEqual(1, lists.getPictureBulletCount());

        msAssert.areEqual(ShapeType.IMAGE, lists.getPictureBullet(0).getShapeType());
    }

    /// <summary>
    /// WORDSNET-16807 A shape of a picture bullet list created using Aspose.Words had zero size that
    /// caused error on building document layout. Now shape size of a picture bullet is updated on document validation.
    /// </summary>
    @Test
    public void testJira16807() throws Exception
    {
        Document document = new Document();
        DocumentBuilder builder = new DocumentBuilder(document);

        // Create bullet list.
        ListCollection listCollection = document.getLists();
        List list = listCollection.add(ListTemplate.BULLET_DIAMONDS);
        list.getListLevels().get(0).createPictureBullet();
        list.getListLevels().get(0).getImageData().setImage(TestUtil.buildTestFileName("ImportHtml\\List\\Test1.jpg"));

        // Set list to paragraph.
        builder.moveToDocumentStart();
        builder.getCurrentParagraph().getListFormat().setListLevelNumber(0);
        builder.getCurrentParagraph().getListFormat().setList(list);

        // An exception was raised on building document layout.
        // Now bullet shape size is fixed in List Validator.
        // No update page layout in FOSS.

        // Check the picture bullet was created.
        msAssert.areEqual(1, listCollection.getPictureBulletCount());

        // FOSS: shape Width/Height assertions removed — the picture-bullet size is derived from the
        // image during document validation/layout (image handling is gone), so it stays 0 here.
    }


    /// <summary>
    /// WORDSNET-13523 Missing Numbering when save as PDF in Memory Stream.
    /// Should display list label for 'PageBreak' character depending on
    /// compatibility option 'SplitPgBreakAndParaMark'.
    /// </summary>
    @Test
    public void testJira13523() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestJira13523.docx");

        // 'SplitPgBreakAndParaMark' option is set in test document.
        msAssert.isTrue(doc.getDocPr().CompatibilityOptions.getSplitPgBreakAndParaMark());

        // The problematic paragraph is 'PageBreak'.
        Paragraph para = doc.getFirstSection().getBody().getParagraphs().get(1);
        msAssert.areEqual("\f\r", para.getText());

        doc.updateListLabels();
        msAssert.areEqual("2.", para.getListLabel().getLabelString());

        doc.getDocPr().CompatibilityOptions.setSplitPgBreakAndParaMark(false);
        doc.updateListLabels();
        msAssert.areEqual("", para.getListLabel().getLabelString());
    }

    // FOSS: TestJira13689 and TestDmlPictureBullet removed — both depend on DML->VML picture-bullet
    // fallback conversion (via the removed rendering/ProcessFallback path) and validate against the
    // removed .doc / Ecma376 fixed-format save paths.

    /// <summary>
    /// WORDSNET-13607 Bullet list is converted to numbered list after re-saving Docx.
    /// Ignore List definitions with duplicated 'abstractNumId'.
    /// </summary>
    @Test
    public void testJira13607() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestJira13607.docx");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(NumberStyle.BULLET, paras.get(1).getListFormat().getListLevel().getNumberStyle());
        msAssert.areEqual(NumberStyle.BULLET, paras.get(8).getListFormat().getListLevel().getNumberStyle());
    }




    // FOSS: TestJira13576 removed — it verified a DOC-import quirk (a style referring to a non-existent
    // list left the paragraph unnumbered on load, then numbered after DOCX save). Converting the .doc
    // input to .docx (in Word) resolves the dangling list reference, so the quirk no longer occurs.



    /// <summary>
    /// WORDSNET-14514 List Labels are incorrect after conversion from Docx to Pdf.
    /// The document has empty list level overrides. MS Word sets their 'StartOverride' to 0.
    /// </summary>
    @Test
    public void testJira14514() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestJira14514.docx");
        doc.updateListLabels();

        // Check labels of problematic paragraphs.
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkParagraphLabelAndText(paras.get(2), "1.0.", "test");
        checkParagraphLabelAndText(paras.get(3), "1.0.1.", "test");
    }

    /// <summary>
    /// WORDSNET-14864 List label size differs in layout.
    /// Mimic MS Word glitch.
    /// </summary>
    // FOSS: TestJira14864 removed — it asserted a layout-computed list-label size
    // (GetExpandedRunPr(RunPrExpandFlags.Layout), a "MS Word glitch" only visible in layout);
    // the layout engine is gone.


    /// <summary>
    /// Test is concerned with WORDSNET-14504
    /// Checks that imported paragraph reuses the list in the destination.
    /// However, hanging of the list level in the destination is not the same as in the source.
    /// So, expected that additional formatting will be applied to imported paragraph.
    /// </summary>
    @Test
    public void testJira14504ReuseDstList() throws Exception
    {
        Document dstDoc = TestUtil.open("Model\\List\\TestImportList14504ReuseDst.docx");
        Document srcDoc = TestUtil.open("Model\\List\\TestImportList14504ReuseSrc.docx");
        Paragraph[] importedParas = importParas(srcDoc, dstDoc, new int[] { 0 }, new int[] { 1 });

        Paragraph importedPara = importedParas[0];
        msAssert.areEqual(-720, importedPara.getParaPr().get(ParaAttr.FIRST_LINE_INDENT));

        ListFormat format = importedPara.getListFormat();
        msAssert.areEqual(1, format.getListId());
        msAssert.areEqual(0, format.getListLevelNumber());

        ListCollection lists = importedPara.getDocument().getLists();
        msAssert.areEqual(1, lists.getCount());
        msAssert.areEqual(1, lists.get(0).getListId());
        msAssert.areEqual(0x75190AB7, lists.get(0).getListDefId());
    }

    /// <summary>
    /// Test is concerned with WORDSNET-14504
    /// Checks that imported paragraph reuses the list in the destination.
    /// However, tabs positions of the list level in the destination is not the same as in the source.
    /// So, also it checks how tab positions move from source definition to destination paragraph level.
    /// </summary>
    @Test
    public void testJira14504ReuseSrcTabs() throws Exception
    {
        Document dstDoc = TestUtil.open("Model\\List\\TestImportList14504ReuseDst.docx");
        Document srcDoc = TestUtil.open("Model\\List\\TestImportList14504ReuseSrcTabs.docx");
        Paragraph[] importedParas = importParas(srcDoc, dstDoc, new int[] { 0 }, new int[] { 2 });

        Paragraph importedPara = importedParas[0];
        TabStopCollection tabStops = importedPara.getParaPr().getTabStops();
        msAssert.areEqual(3, tabStops.getCount());
        msAssert.areEqual(1080, tabStops.get(0).getPositionTwips());
        msAssert.areEqual(1440, tabStops.get(1).getPositionTwips());
        msAssert.areEqual(1800, tabStops.get(2).getPositionTwips());

        msAssert.areEqual(0, importedPara.getParaPr().get(ParaAttr.FIRST_LINE_INDENT));

        ListFormat format = importedPara.getListFormat();
        msAssert.areEqual(1, format.getListId());
        msAssert.areEqual(0, format.getListLevelNumber());

        ListCollection lists = importedPara.getDocument().getLists();
        msAssert.areEqual(1, lists.getCount());
        msAssert.areEqual(1, lists.get(0).getListId());
        msAssert.areEqual(0x75190AB7, lists.get(0).getListDefId());
    }

    /// <summary>
    /// Test is concerned with WORDSNET-14504
    /// Checks that difference between list definitions attributes of the source and destinations
    /// calculates and moves to paragraph level only when list definitions were reused.
    /// </summary>
    @Test
    public void testJira14504ReuseSrcExpand() throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\List\\TestImportList14504ExpandSrc.docx");
        Document dstDoc = TestUtil.open("Model\\List\\TestImportList14504ReuseDst.docx");

        ListFormat listFormat = srcDoc.getFirstSection().getBody().getLastParagraph().getListFormat();
        ListDefStub listDefStub = new ListDefStub(listFormat.getList().getListDef());
        List newList = listFormat.getList().deepClone(srcDoc, srcDoc.getLists().getNextAvailableListId());
        srcDoc.getLists().addListDef(listDefStub);
        srcDoc.getLists().addList(newList);

        newList.setListDefId(listDefStub.getListDefId());
        listFormat.setList(newList);
        msAssert.areEqual(listDefStub, listFormat.getList().getListDef());

        NodeImporter imorter = new NodeImporter(srcDoc, dstDoc, ImportFormatMode.USE_DESTINATION_STYLES);
        Paragraph para = (Paragraph)imorter.importNode(srcDoc.getFirstSection().getBody().getLastParagraph(), true);

        msAssert.areEqual(srcDoc.getFirstSection().getBody().getLastParagraph().getParaPr().getCount(), para.getParaPr().getCount());
    }

    /// <summary>
    /// Test is concerned with WORDSNET-14504
    /// Checks that imported paragraphs reuse the list in the destination.
    /// However, indentions of the list levels in the destination is not the same as in the source.
    /// So, also it checks how indentions of paragraphs from source definition merge with destination values.
    /// </summary>
    @Test
    public void testImportList14504ReuseSrcLeftIndMiss() throws Exception
    {
        Document dstDoc = TestUtil.open("Model\\List\\TestImportList14504ReuseDst2.docx");
        Document srcDoc = TestUtil.open("Model\\List\\TestImportList14504ReuseSrcLeftIndMiss.docx");
        Paragraph[] importedParas = importParas(srcDoc, dstDoc, new int[] { 0, 1, 2 }, new int[] { 3, 3, 1 });

        ParaPr pr = importedParas[0].getParaPr();
        msAssert.areEqual(0, pr.get(ParaAttr.FIRST_LINE_INDENT));
        msAssert.areEqual(720, pr.get(ParaAttr.LEFT_INDENT));
        msAssert.areEqual(1440, pr.get(ParaAttr.RIGHT_INDENT));
        msAssert.areEqual(1, pr.getListId());
        msAssert.areEqual(0, pr.getListLevel());

        pr = importedParas[1].getParaPr();
        msAssert.areEqual(0, pr.get(ParaAttr.FIRST_LINE_INDENT));
        msAssert.areEqual(720, pr.get(ParaAttr.LEFT_INDENT));
        msAssert.areEqual(2160, pr.get(ParaAttr.RIGHT_INDENT));
        msAssert.areEqual(1, pr.getListId());
        msAssert.areEqual(1, pr.getListLevel());

        pr = importedParas[2].getParaPr();
        msAssert.areEqual(0, pr.get(ParaAttr.LEFT_INDENT));
        msAssert.areEqual(1, pr.getListId());
        msAssert.areEqual(2, pr.getListLevel());

        ListCollection lists = importedParas[0].getDocument().getLists();
        msAssert.areEqual(1, lists.getCount());
        msAssert.areEqual(1, lists.get(0).getListId());
        msAssert.areEqual(0x75190AB7, lists.get(0).getListDefId());
    }

    /// <summary>
    /// Test is concerned with WORDSNET-14504
    /// Checks how indentation of the previous not empty paragraph of the list item influences to list reusing.
    /// Source document has empty paragraph and left indent + first line indent of the not empty paragraph
    /// greater then the left indent + first line indent of the list item. So, list definition will be reused.
    /// </summary>
    @Test
    public void testJira14504ParaIndent() throws Exception
    {
        // Attributes of the elements in the source document are the following:
        // Paragraph left indent: 567,
        // Paragraph first line: None,
        // List item left indent: 360,
        // List item hanging: 720.

        Document dstDoc = TestUtil.open("Model\\List\\TesttJira14504ParaIndentDst.fopc");
        Document srcDoc = TestUtil.open("Model\\List\\TesttJira14504ParaIndentSrc.fopc");
        msAssert.areEqual(10, dstDoc.getLists().getListDefs().size());

        dstDoc.appendDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);
        msAssert.areEqual(10, dstDoc.getLists().getListDefs().size());

        Paragraph para = dstDoc.getFirstSection().getBody().getParagraphs().get(1);
        msAssert.areEqual(4, para.getListFormat().getListId());
        msAssert.areEqual(0, para.getListFormat().getListLevel().getLevelNumber());
        msAssert.areEqual(8, para.getListFormat().getList().getListDefId());

        para = dstDoc.getLastSection().getBody().getParagraphs().get(0);
        msAssert.isFalse(para.getListFormat().isListItem());

        para = dstDoc.getLastSection().getBody().getParagraphs().get(2);
        msAssert.areEqual(6, para.getListFormat().getListId());
        msAssert.areEqual(0, para.getListFormat().getListLevel().getLevelNumber());
        msAssert.areEqual(8, para.getListFormat().getList().getListDefId());

        para = dstDoc.getLastSection().getBody().getParagraphs().get(3);
        msAssert.areEqual(6, para.getListFormat().getListId());
        msAssert.areEqual(0, para.getListFormat().getListLevel().getLevelNumber());
        msAssert.areEqual(8, para.getListFormat().getList().getListDefId());
    }

    /// <summary>
    /// Test is concerned with WORDSNET-14504
    /// Checks how indentation of the previous not empty paragraph of the list item influences to list reusing.
    /// Source document has non empty paragraph with left indent less then left indent of the list item.
    /// However, left indent + first line indent of the not empty paragraph greater then the
    /// left indent + first line indent of the list item. So, list definition will be reused.
    /// </summary>
    @Test
    public void testJira14504ParaIndent2() throws Exception
    {
        // Attributes of the elements in the source document are the following:
        // Paragraph left indent: 57,
        // Paragraph first line: None,
        // List item left indent: 360,
        // List item hanging: 720.
        checkListImportParaIndent("Model\\List\\TesttJira14504ParaIndentSrc2.fopc", true);
    }

    /// <summary>
    /// Test is concerned with WORDSNET-14504
    /// Checks how indention of the previous not empty paragraph of the list item influences to list reusing.
    /// Source document has empty paragraph and left indent + first line indent of the not empty paragraph
    /// less then the left indent + first line indent of the list item. So, list definition will not be reused.
    /// </summary>
    @Test
    public void testJira14504ParaIndent3() throws Exception
    {
        // Attributes of the elements in the source document are the following:
        // Paragraph left indent: 400,
        // Paragraph hanging: 2000,
        // List item left indent: 360,
        // List item hanging: 720.
        checkListImportParaIndent("Model\\List\\TestJira14504ParaIndentSrc3.fopc", false);
    }

    /// <summary>
    /// Test is concerned with WORDSNET-14504
    /// Checks how indention of the previous not empty paragraph of the list item influences to list reusing.
    /// Source document has non empty paragraph with first line greater then first line of the list item.
    /// However, left indent + first line indent of the not empty paragraph greater then the
    /// left indent + first line indent of the list item. So, list definition will be reused.
    /// </summary>
    @Test
    public void testJira14504ParaIndent4() throws Exception
    {
        // Attributes of the elements in the source document are the following:
        // Paragraph left indent: -400,
        // Paragraph first line: 100,
        // List item left indent: 360,
        // List item hanging: 720.
        checkListImportParaIndent("Model\\List\\TesttJira14504ParaIndentSrc4.fopc", true);
    }

    /// <summary>
    /// Test is concerned with WORDSNET-14504
    /// Checks how indention of the previous not empty paragraph of the list item influences to list reusing.
    /// Source document has non empty paragraph with first line and left indent equal to first line and left
    /// indent of the list item. Expected that list definition will be reused.
    /// </summary>
    @Test
    public void testJira14504ParaIndent5() throws Exception
    {
        // Attributes of the elements in the source document are the following:
        // Paragraph left indent: 360,
        // Paragraph hanging: 720,
        // List item left indent: 360,
        // List item hanging: 720.
        checkListImportParaIndent("Model\\List\\TesttJira14504ParaIndentSrc5.fopc", true);
    }

    /// <summary>
    /// Test is concerned with WORDSNET-14504
    /// Checks how indention of the previous not empty paragraph of the list item influences to list reusing.
    /// Source document has non empty paragraph which contains value of the left indent equal to absolute value
    /// of the first line of the list item and absolute value of the first line of the paragraph equal to
    /// left indent of the list item. Expected that list definition will not be reused.
    /// </summary>
    @Test
    public void testJira14504ParaIndent6() throws Exception
    {
        // Attributes of the elements in the source document are the following:
        // Paragraph left indent: 360,
        // Paragraph hanging: 720,
        // List item left indent: 720,
        // List item hanging: 360.
        checkListImportParaIndent("Model\\List\\TesttJira14504ParaIndentSrc6.fopc", false);
    }

    /// <summary>
    /// Test is concerned with WORDSNET-14504
    /// Checks how indention of the previous not empty paragraph of the list item influences to list reusing.
    /// Source document has non empty paragraph only with a picture and left indent + first line indent of the
    /// not empty paragraph less then the left indent + first line indent of the list item.
    /// So, list definition will not be reused.
    /// </summary>
    @Test
    public void testJira14504ParaIndent7() throws Exception
    {
        // Attributes of the elements in the source document are the following:
        // Paragraph left indent: -567,
        // Paragraph hanging: None.
        // List item left indent: 360,
        // List item hanging: 720.
        checkListImportParaIndent("Model\\List\\TesttJira14504ParaIndentSrc7.fopc", false);
    }

    /// <summary>
    /// Test is concerned with WORDSNET-14504
    /// Test checks how indention of the paragraph influences to list reusing.
    /// Source document has non empty paragraph only with white spaces and left indent + first line indent of the
    /// not empty paragraph greater then the left indent + first line indent of the list item.
    /// So, list definition will be reused.
    /// </summary>
    @Test
    public void testJira14504ParaIndent8() throws Exception
    {
        // Attributes of the elements in the source document are the following:
        // Paragraph left indent: 1247,
        // Paragraph hanging: None,
        // List item left indent: 720,
        // List item hanging: 360.

        checkListImportParaIndent("Model\\List\\TesttJira14504ParaIndentSrc8.fopc", true);
    }

    /// <summary>
    /// Test is concerned with WORDSNET-14504
    /// Test checks how indention of the paragraph influences to list reusing.
    /// Source document has empty paragraph with insert revision which contains a white spaces.
    /// Also left indent + first line indent of it greater then the left indent + first line indent of the list item.
    /// So, list definition will be reused.
    /// </summary>
    @Test
    public void testJira14504ParaIndent9() throws Exception
    {
        // Attributes of the elements in the source document are the following:
        // Paragraph left indent: 1134.
        // Paragraph hanging: None.
        // List item left indent: 720.
        // List item hanging: 360.
        checkListImportParaIndent("Model\\List\\TesttJira14504ParaIndentSrc9.fopc", true);
    }

    /// <summary>
    /// Test is concerned with WORDSNET-15054
    /// ListDef.IsRestartAtEachSection is exposed to public.
    /// </summary>
    @Test
    public void testJira15054A() throws Exception
    {
        Document doc = new Document();
        doc.getLists().add(ListTemplate.NUMBER_DEFAULT);

        List list = doc.getLists().get(0);

        // By default is false.
        msAssert.areEqual(false, list.isRestartAtEachSection());

        // Set true.
        list.isRestartAtEachSection(true);
        msAssert.areEqual(true, list.isRestartAtEachSection());

        // IsRestartAtEachSection will be written only if compliance is higher then OoxmlComplianceCore.Ecma376
        OoxmlSaveOptions options = new OoxmlSaveOptions();
        options.setCompliance(OoxmlCompliance.ISO_29500_2008_TRANSITIONAL);

        doc = TestUtil.saveOpen(doc, "Model\\List\\TestJira15054A.docx", options, false);
        list = doc.getLists().get(0);
        msAssert.areEqual(true, list.isRestartAtEachSection());
    }

    /// <summary>
    /// WORDSNET-15788 List label values are incorrect after joining documents.
    /// Imported lists re-use while joining documents. Only lists which initially located
    /// in the destination can be re-used. Imported lists have to be excluded while re-using
    /// to fix the problem.
    /// </summary>
    @Test
    public void testJira15788() throws Exception
    {
        Document dstDoc = new Document();
        Document srcDoc = TestUtil.open("ImportDocx\\TestJira15788", LoadFormat.DOCX);
        dstDoc.appendDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES);

        ParagraphCollection paras = dstDoc.getLastSection().getBody().getParagraphs();
        Paragraph paraFirst = paras.get(8);
        ParaPr paraPr = paraFirst.getParaPr();


        msAssert.areEqual(3, paraPr.get(ParaAttr.LIST_ID));
        msAssert.areEqual(0, paraPr.get(ParaAttr.LIST_LEVEL));

        Paragraph paraLast = paras.get(15);
        paraPr = paraLast.getParaPr();
        msAssert.areEqual(4, paraPr.get(ParaAttr.LIST_ID));
        msAssert.areEqual(0, paraPr.get(ParaAttr.LIST_LEVEL));

        msAssert.areEqual(0x6C647412, dstDoc.getLists().getListByListId(3).getListDefId());
        msAssert.areEqual(0x6C647412, dstDoc.getLists().getListByListId(4).getListDefId());

        // One more insane check that test actual label numbers.
        dstDoc.updateListLabels();
        checkParagraphLabelAndText(paraLast, "1)", "Text");
        checkParagraphLabelAndText(paraFirst, "1)", "Text");
    }

    /// <summary>
    /// WORDSNET-15226 List label is changed after conversion from DOC to DOCX/PDF.
    /// We should ignore terminal list definition when updating list labels, if this is style definition.
    /// </summary>
    // FOSS: TestJira15226 removed — it verified a DOC->DOCX list-label conversion quirk from a .doc
    // input; converting that input to .docx (in Word) normalizes the list so the quirk no longer occurs.


    /// <summary>
    /// WORDSNET-17658 incorrect resolving of lists in a broken document.
    /// Implemented correct resolving of problematic chain of list references, which leads to nonexistent list.
    /// Each list which involved in such chain of lists, transfromed into self-referenced list, i.e.
    /// List.Style.List points to the List itself.
    /// </summary>
    @Test
    public void testJira9911Improve() throws Exception
    {
        // Verify no exception.
        Document doc = TestUtil.open("ExportDocx\\TestJira9911.docx");

        // List uses listStyle "BulletList" which refers to List with listStyle "NumberList" which refers to
        // List with listStyle "LijstTim". Style "LijstTim" points to an nonexistent list with Id = 15.
        // MSW removes all these references, and transforms all three lists into List Style Definitions.

        // Verify correct resolving.
        // List style "BulletList"
        verifySelfReferencedList(doc.getLists().getListByListId(1), "BulletList");
        // List style "NumberList"
        verifySelfReferencedList(doc.getLists().getListByListId(2), "NumberList");
        // List style "LijstTim"
        verifySelfReferencedList(doc.getLists().getListByListId(4), "LijstTim");
    }


    /// <summary>
    /// WORDSNET-16755 Incorrect conversion of bullet points in word.
    /// This issue was fixed per WORDSNET-16660
    /// </summary>
    /// <remarks>
    /// This test is implemented for verification the customer document from
    /// WORDSNET-16755 exclusively to check it has been actually fixed already.
    /// </remarks>
    // FOSS: TestJira16755 removed \u2014 its customer input (TestJira16755.docx) is detected as HTML,
    // and HTML load is not supported in FOSS. It only re-verified an already-fixed customer case.

    /// <summary>
    /// WORDSNET-17335 "Not expected other boolex values here" error occurs upon invoking ListFormat.ListLevel.Font.Italic.
    /// The problem occurs because the inherited value is "AttrBoolEx Toggle"
    /// and need to be resolved using parent values and not just converted to bool.
    /// </summary>
    // FOSS: TestJira17335 removed — it checked an AttrBoolEx-Toggle inheritance quirk on a Cyrillic
    // heading style loaded from a .doc; converting the input to .docx restructures the style so the
    // scenario no longer reproduces.

    /// <summary>
    /// WORDSNET-17717 Incorrect conversion of list markers.
    /// When SingleLevel list with overrides is used for ListParagraphs with different levels,
    /// we must use 0-th Override ListLevel, regardless ListParagraph's level.
    /// </summary>
    /// <remarks>
    /// Please, check <see cref="List.GetFormattingOverride"/> for the details.
    /// </remarks>
    @Test
    public void testJira17717() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestJira17717.docx");
        doc.updateListLabels();
        Paragraph para = doc.getFirstSection().getBody().getParagraphs().get(7);
        msAssert.areEqual("Symbol", para.getListLabel().getFont().getName());
        msAssert.areEqual("\uF0B7", para.getListLabel().getLabelString());
    }

    /// <summary>
    /// WORDSJAVA-1908 Dashes in bullets list disappear in rendered PDF
    /// </summary>
    @Test
    public void testJiraJ1908() throws Exception
    {
        Document doc = TestUtil.open("Model\\Header\\TestJiraJ1908.docx");
        doc.updateListLabels();

        Paragraph para = doc.getFirstSection().getBody().getParagraphs().get(0);
        checkParagraphLabelAndText(para, "\u00ad", "1");

        para = doc.getFirstSection().getBody().getParagraphs().get(1);
        checkParagraphLabelAndText(para, "", "2");
    }



    /// <summary>
    /// WORDSNET-18228 AppendDocument destroys lists.
    /// The problematic list has the same list definition identifier in the source and destination documents. So, the list
    /// definition from destination was used in a copy instead of importing it from the source document.
    /// Introduced the additional ImportFormatOption parameter to use in AppendDocument() that allows to specify how
    /// to import lists with the equal list definition ids.
    /// </summary>
    @Test
    public void testJira18228() throws Exception
    {
        final String DST_FILE_NAME = "Model\\List\\TestJira18228Dst.docx";
        final String SRC_FILE_NAME = "Model\\List\\TestJira18228Src.docx";

        // By default Word and AW reuse equal list definition ids from a destination document.
        ImportFormatOptions importFormatOptions = new ImportFormatOptions();
        Document doc = appendDocuments(DST_FILE_NAME, SRC_FILE_NAME, ImportFormatMode.USE_DESTINATION_STYLES, importFormatOptions);
        checkParagraphLabelAndText(doc.getSections().get(1).getBody().getLastParagraph(), "2.", "13->13");

        // This is what the customer wants.
        importFormatOptions.setKeepSourceNumbering(true);
        doc = appendDocuments(DST_FILE_NAME, SRC_FILE_NAME, ImportFormatMode.USE_DESTINATION_STYLES, importFormatOptions);
        checkParagraphLabelAndText(doc.getSections().get(1).getBody().getLastParagraph(), "1.", "13->13");
    }

    /// <summary>
    /// WORDSNET-18475 Invalid numbering after Section Break.
    /// If w15:restartNumberingAfterBreak is 1 and the list paragraphs are splitted by section break
    /// Word restarts numbering starting from level of first occurred paragraph in next section.
    /// </summary>
    @Test (enabled = false)
    public void test18475(String fileName, String expectedNPlusOneLevelNumber, String expectedNLevelNumber) throws Exception
    {
        Document doc = TestUtil.open(fileName);
        doc.updateListLabels();

        String actualNPlusOneLevelNumber = doc.getSections().get(1).getBody().getFirstParagraph().getListLabel().getLabelString();
        msAssert.areEqual(expectedNPlusOneLevelNumber, actualNPlusOneLevelNumber);

        String actualNLevelNumber = doc.getSections().get(1).getBody().getLastParagraph().getListLabel().getLabelString();
        msAssert.areEqual(expectedNLevelNumber, actualNLevelNumber);
    }


    /// <summary>
    /// WORDSNET-18925 Unexpected increase of paragraph left indent on assigning
    /// <see cref="ListFormat.ListId"/>.
    /// </summary>
    // FOSS: Test18925 removed — its input is an .mht (MHTML) file and MHTML load was removed.

    /// <summary>
    /// WORDSNET-19316 Numbering disappear during appending documents.
    /// When taken decision on whether to expand style formatting to direct paragraph
    /// attributes we need to consider also list formatting for the styles with numbering.
    /// </summary>
    @Test (dataProvider = "test19316DataProvider")
    public void test19316(String dstFileName, boolean isKeepSourceNumbering, String expectedLabelString) throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\List\\Test19316src.docx");
        Document dstDoc = TestUtil.open(msString.format("Model\\List\\{0}", dstFileName));

        ImportFormatOptions options = new ImportFormatOptions();
        options.setKeepSourceNumbering(isKeepSourceNumbering);

        dstDoc.appendDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING, options);
        dstDoc.updateListLabels();

        Paragraph para = dstDoc.getSections().get(1).getBody().getParagraphs().get(1);
        msAssert.areEqual(expectedLabelString, para.getListLabel().getLabelString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19316DataProvider")
	public static Object[][] test19316DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Test19316dst.docx",  false,  "1."},
			{"Test19316dst.docx",  true,  "1."},
			{"Test19316DstSameListDefId.docx",  false,  ""},
			{"Test19316DstSameListDefId.docx",  true,  "1."},
		};
	}

    /// <summary>
    /// WORDSNET-19316 Numbering chapters disappear during appending documents.
    /// Do not expand style to direct formatting when lists identifier in the source and in the destination are different.
    /// </summary>
    @Test (dataProvider = "test19275DataProvider")
    public void test19275(boolean isKeepSourceNumbering) throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\List\\Test19275Src.docx");
        Document dstDoc = TestUtil.open("Model\\List\\Test19275DifferentListIds.docx");
        msAssert.areEqual(2, dstDoc.getLists().getCount());

        ImportFormatOptions options = new ImportFormatOptions();
        options.setKeepSourceNumbering(isKeepSourceNumbering);

        dstDoc.appendDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING, options);
        dstDoc.updateListLabels();

        Paragraph para = dstDoc.getLastSection().getBody().getParagraphs().get(1);

        msAssert.areEqual(isKeepSourceNumbering ? 3: 2, dstDoc.getLists().getCount());
        msAssert.areEqual(isKeepSourceNumbering ? "1." : "", para.getListLabel().getLabelString());

        Object listId = para.getParaPr().get(ParaAttr.LIST_ID);
        if (isKeepSourceNumbering)
            msAssert.isNotNull(listId);
        else
            msAssert.isNull(listId);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19275DataProvider")
	public static Object[][] test19275DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}

    /// <summary>
    /// Related with WORDSNET-19316
    /// Checks case when list identifiers are equal in the source and in the destination.
    /// </summary>
    @Test (dataProvider = "test19275SameListIdsDataProvider")
    public void test19275SameListIds(boolean isKeepSourceNumbering) throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\List\\Test19275Src.docx");
        Document dstDoc = TestUtil.open("Model\\List\\Test19275SameListIds.docx");
        msAssert.areEqual(2, dstDoc.getLists().getCount());

        ImportFormatOptions options = new ImportFormatOptions();
        options.setKeepSourceNumbering(isKeepSourceNumbering);

        dstDoc.appendDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING, options);
        dstDoc.updateListLabels();

        Paragraph para = dstDoc.getLastSection().getBody().getParagraphs().get(1);

        msAssert.areEqual("1.", para.getListLabel().getLabelString());

        msAssert.areEqual(3, dstDoc.getLists().getCount());
        msAssert.areEqual(3, para.getParaPr().get(ParaAttr.LIST_ID));
        msAssert.areEqual(720, para.getParaPr().get(ParaAttr.LEFT_INDENT));
        msAssert.areEqual(-360, para.getParaPr().get(ParaAttr.FIRST_LINE_INDENT));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19275SameListIdsDataProvider")
	public static Object[][] test19275SameListIdsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}


    /// <summary>
    /// WORDSNET-20874 DOCX to PDF conversion issue with list item rendering
    /// Improved unexpected list level handling.
    /// </summary>
    @Test (dataProvider = "test20874DataProvider")
    public void test20874(/*MsWordVersion*/int loadVersion) throws Exception
    {
        LoadOptions lo = new LoadOptions(); { lo.setMswVersion(loadVersion); }
        Document doc = TestUtil.open("Model\\List\\Test20874.docx", lo);

        Paragraph para = doc.getFirstSection().getBody().getParagraphs().get(1);
        msAssert.areEqual(0, para.getParaPr().get(ParaAttr.LIST_ID));
        msAssert.areEqual(12, para.getParaPr().get(ParaAttr.LIST_LEVEL));

        String expected = loadVersion == MsWordVersion.WORD_2013
            ? "\t2\u00b0 \tLorem Ipsum\r"
            : "2\u00b0 \tLorem Ipsum\r";

        msAssert.areEqual(expected, para.getText());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test20874DataProvider")
	public static Object[][] test20874DataProvider() throws Exception
	{
		return new Object[][]
		{
			{MsWordVersion.WORD_2013},
			{MsWordVersion.WORD_2019},
		};
	}

    /// <summary>
    /// WORDSNET-19255 Debug.Assert failure in the layout code when a DOCX document is converted to PDF.
    /// <see cref="ListLevel.Legacy"/> was set to false for the bullet list. Word doesn't do that. Mimic Word.
    /// </summary>
    // FOSS: Test19255 removed — it ran the PDF-layout validator and asserted a layout-computed
    // legacy tab position (GetExpandedParaPr(Layout)); the layout engine is gone.


    /// <summary>
    /// WORDSNET-22525 WK: NumberStyle CUSTOM for LEADING_ZERO formats.
    /// A new feature has been implemented.
    /// </summary>
    /// <remarks>
    /// <para><see cref="ListLevel.GetEffectiveValue(int, NumberStyle, string)"/></para>
    /// <para><see cref="ListLevel.CustomNumberStyleFormat"/></para>
    /// </remarks>
    @Test
    public void test22525() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\Test22525", LoadFormat.DOCX);
        ListLevel listLevel = doc.getFirstSection().getBody().getParagraphs().get(0).getListFormat().getListLevel();
        msAssert.areEqual("001, 002, 003, ...", listLevel.getCustomNumberStyleFormat());
        msAssert.areEqual("005", ListLevel.getEffectiveValue(5, listLevel.getNumberStyle(), listLevel.getCustomNumberStyleFormat()));
    }

    /// <summary>
    /// Related with WORDSNET-22525
    /// Checks the method.
    /// </summary>
    @Test
    public void test22525_02()
    {
        msAssert.areEqual("iv", ListLevel.getEffectiveValue(4, NumberStyle.LOWERCASE_ROMAN, null));
        msAssert.areEqual("005", ListLevel.getEffectiveValue(5, NumberStyle.CUSTOM, "001, 002, 003, ..."));
    }

    /// <summary>
    /// Related with WORDSNET-22525
    /// Checks the <see cref="ArgumentOutOfRangeException"/> for the method.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, dataProvider = "test22525_03DataProvider")
    public void test22525_03(int index)
    {
        ListLevel.getEffectiveValue(index, NumberStyle.LOWERCASE_ROMAN, null);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test22525_03DataProvider")
	public static Object[][] test22525_03DataProvider()
	{
		return new Object[][]
		{
			{0},
			{32768},
		};
	}

    /// <summary>
    /// Related with WORDSNET-22525
    /// Checks the <see cref="ArgumentException"/> for the method.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, dataProvider = "test22525_04DataProvider")
    public void test22525_04(/*NumberStyle*/int numberStyle, String customNumberStyleFormat)
    {
        ListLevel.getEffectiveValue(4, numberStyle, customNumberStyleFormat);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test22525_04DataProvider")
	public static Object[][] test22525_04DataProvider()
	{
		return new Object[][]
		{
			{NumberStyle.CUSTOM,  ""},
			{NumberStyle.CUSTOM,  null},
			{NumberStyle.LOWERCASE_ROMAN,  "001, 002, 003, ..."},
			{NumberStyle.CUSTOM,  "000001, 000002, 000003, ..."},
		};
	}

    /// <summary>
    /// WORDSNET-17658 Verifies cross-format issues in import/export of ListStyleReference ListDefs.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestListStyleReferences(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestListStyleReference", lf);

        doc = TestUtil.saveOpen(doc, "Model\\List\\TestListStyleReference", TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD);

        String desiredValue = (sf == SaveFormat.DOC) ? "43" : "58";

        doc.updateListLabels();
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(desiredValue, para.getListLabel().getLabelString());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestListStyleReferencesSourceDataProvider")
	public void unifiedTestListStyleReferencesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestListStyleReferences(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestListStyleReferencesSourceDataProvider")
	Object[][] unifiedTestListStyleReferencesSourceDataProvider() throws Exception
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
    /// WORDSNET-24142 Providing a public API to determine the origin of lists from the same template.
    /// </summary>
    @Test
    public void test24142() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\Test24142.docx");
        List list1 = doc.getLists().get(0);
        List list2 = doc.getLists().get(1);
        List list3 = doc.getLists().get(2);

        msAssert.isTrue(list1.hasSameTemplate(list2));
        msAssert.isFalse(list2.hasSameTemplate(list3));
    }










    /// <summary>
    /// WORDSNET-26149 Allow creating a single level lists through public API.
    /// Tests the public API of AddSingleLevelList in ListCollection.
    /// </summary>
    @Test (dataProvider = "test26149_BulletedDataProvider")
    public void test26149_Bulleted(/*ListTemplate*/int listTemplate, String numberFormat, String fontName) throws Exception
    {
        Document doc = new Document();
        List list = doc.getLists().addSingleLevelList(listTemplate);

        msAssert.areEqual(ListType.SINGLE_LEVEL, list.getListDef().getListType());
        msAssert.areEqual(1, list.getListLevels().getCount());

        ListLevel listLevel = list.getListLevels().get(0);
        msAssert.areEqual(720, listLevel.getParaPr().getLeftIndent());
        msAssert.areEqual(-360, listLevel.getParaPr().getFirstLineIndent());
        msAssert.areEqual(720, listLevel.getParaPr().getTabStops().get(0).getPositionTwips());

        msAssert.areEqual(numberFormat, listLevel.getNumberFormat());
        msAssert.areEqual(fontName, listLevel.getFont().getNameOther());
        msAssert.areEqual(fontName, listLevel.getFont().getNameAscii());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test26149_BulletedDataProvider")
	public static Object[][] test26149_BulletedDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ListTemplate.BULLET_DISK,  "\uf0b7",  "Symbol"},
			{ListTemplate.BULLET_CIRCLE,  "o",  "Courier New"},
			{ListTemplate.BULLET_SQUARE,  "\uf0a7",  "Wingdings"},
			{ListTemplate.BULLET_DIAMONDS,  "\uf076",  "Wingdings"},
			{ListTemplate.BULLET_ARROW_HEAD,  "\uf0d8",  "Wingdings"},
			{ListTemplate.BULLET_TICK,  "\uf0fc",  "Wingdings"},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-26149.
    /// </summary>
    @Test (dataProvider = "test26149_NumberedDataProvider")
    public void test26149_Numbered(/*ListTemplate*/int listTemplate, /*NumberStyle*/int numberStyle, String numberFormat, /*ListLevelAlignment*/int alignment) throws Exception
    {
        Document doc = new Document();
        List list = doc.getLists().addSingleLevelList(listTemplate);

        msAssert.areEqual(ListType.SINGLE_LEVEL, list.getListDef().getListType());
        msAssert.areEqual(1, list.getListLevels().getCount());

        ListLevel listLevel = list.getListLevels().get(0);
        msAssert.areEqual(720, listLevel.getParaPr().getLeftIndent());
        msAssert.areEqual(-360, listLevel.getParaPr().getFirstLineIndent());
        msAssert.areEqual(720, listLevel.getParaPr().getTabStops().get(0).getPositionTwips());

        msAssert.areEqual(numberStyle, listLevel.getNumberStyle());
        msAssert.areEqual(numberFormat, listLevel.getNumberFormat());
        msAssert.areEqual(alignment, listLevel.getAlignment());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test26149_NumberedDataProvider")
	public static Object[][] test26149_NumberedDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ListTemplate.NUMBER_ARABIC_DOT,  NumberStyle.ARABIC,  "\u0000.",  ListLevelAlignment.LEFT},
			{ListTemplate.NUMBER_ARABIC_PARENTHESIS,  NumberStyle.ARABIC,  "\u0000)",  ListLevelAlignment.LEFT},
			{ListTemplate.NUMBER_UPPERCASE_ROMAN_DOT,  NumberStyle.UPPERCASE_ROMAN,  "\u0000.",  ListLevelAlignment.RIGHT},
			{ListTemplate.NUMBER_UPPERCASE_LETTER_DOT,  NumberStyle.UPPERCASE_LETTER,  "\u0000.",  ListLevelAlignment.LEFT},
			{ListTemplate.NUMBER_LOWERCASE_LETTER_PARENTHESIS,  NumberStyle.LOWERCASE_LETTER,  "\u0000)",  ListLevelAlignment.LEFT},
			{ListTemplate.NUMBER_LOWERCASE_LETTER_DOT,  NumberStyle.LOWERCASE_LETTER,  "\u0000.",  ListLevelAlignment.LEFT},
			{ListTemplate.NUMBER_LOWERCASE_ROMAN_DOT,  NumberStyle.LOWERCASE_ROMAN,  "\u0000.",  ListLevelAlignment.RIGHT},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-26149.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, dataProvider = "test26149_OutlinedDataProvider")
    public void test26149_Outlined(/*ListTemplate*/int listTemplate) throws Exception
    {
        Document doc = new Document();
        doc.getLists().addSingleLevelList(ListTemplate.OUTLINE_HEADINGS_ARTICLE_SECTION);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test26149_OutlinedDataProvider")
	public static Object[][] test26149_OutlinedDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ListTemplate.OUTLINE_NUMBERS},
			{ListTemplate.OUTLINE_LEGAL},
			{ListTemplate.OUTLINE_BULLETS},
			{ListTemplate.OUTLINE_HEADINGS_ARTICLE_SECTION},
			{ListTemplate.OUTLINE_HEADINGS_LEGAL},
			{ListTemplate.OUTLINE_HEADINGS_NUMBERS},
			{ListTemplate.OUTLINE_HEADINGS_CHAPTER},
		};
	}

    /// <summary>
    /// WORDSNET-28555 Formatting multi-level list issue.
    /// Tests importing a document with duplicate Lists and ListDefs identifiers.
    /// </summary>
    @Test
    public void test28555() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\Test28555.docx");

        doc.updateListLabels();
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.get(2).getListLabel().getLabelValue());
        msAssert.areEqual("1.", paras.get(2).getListLabel().getLabelString());

        msAssert.areEqual(2, paras.get(3).getListLabel().getLabelValue());
        msAssert.areEqual("2.", paras.get(3).getListLabel().getLabelString());

        msAssert.areEqual(1, paras.get(4).getListLabel().getLabelValue());
        msAssert.areEqual("", paras.get(4).getListLabel().getLabelString());

        msAssert.areEqual(1, paras.get(7).getListLabel().getLabelValue());
        msAssert.areEqual("·", paras.get(7).getListLabel().getLabelString());

        msAssert.areEqual(1, paras.get(8).getListLabel().getLabelValue());
        msAssert.areEqual("", paras.get(8).getListLabel().getLabelString());
    }

    /// <summary>
    /// WORDSNET-28878 List numbering is wrong after converting DOCX to PDF
    /// Restart numbering after break according to the list identifier.
    /// </summary>
    @Test (enabled = false)
    public void test28878(String testFile) throws Exception
    {
        Document doc = TestUtil.open(testFile);
        doc.updateListLabels();

        Paragraph para = TestUtil.getParagraphWithText(doc, "Judgment of Overall Residual Risk Acceptability...");
        msAssert.areEqual("4.2.1", para.getListLabel().getLabelString());
    }

    private static void verifySelfReferencedList(List list, String styleName)
    {
        msAssert.isNotNull(list);
        msAssert.areEqual(styleName, list.getStyle().getName());
        msAssert.areEqual(list.getListId(), list.getStyle().getList().getListId());
    }

    /// <summary>
    /// Imports paragraphs with specified indexes from source to destination and
    /// checks count of attributes which directly set to the destination paragraph.
    /// </summary>
    private static Paragraph[] importParas(Document srcDoc,Document dstDoc, int[] parasNums, int[] expectedAttrCount)
    {
        NodeImporter importer = new NodeImporter(srcDoc, dstDoc, ImportFormatMode.USE_DESTINATION_STYLES);
        ParagraphCollection paras = srcDoc.getFirstSection().getBody().getParagraphs();
        Paragraph[] importedParas = new Paragraph[parasNums.length];

        for (int i = 0; i < paras.getCount(); ++i)
        {
            int importIndex = -1;
            for (int j = 0; j < parasNums.length; ++j)
            {
                if (i == parasNums[j])
                {
                    importIndex = j;
                    break;
                }
            }

            if (importIndex < 0)
                continue;

            int initialAttrCount = paras.get(i).getParaPr().getCount();
            Paragraph importedPara = (Paragraph)importer.importNode(paras.get(i), true);
            importedParas[i] = importedPara;

            // Expected additional attributes which was moved from list definition level.
            msAssert.areEqual(initialAttrCount + expectedAttrCount[importIndex], importedParas[importIndex].getParaPr().getCount());
        }

        return importedParas;
    }

    /// <summary>
    /// Checks number of picture bullets and properties of the first bullet.
    /// </summary>
    private static void checkPictureBullets(Document doc, int expectedCount,
        /*ShapeMarkupLanguage*/byte expectedMarkup, boolean expectedHasFallback)
    {
        msAssert.areEqual(expectedCount, doc.getLists().getPictureBulletCount());

        Shape shape = doc.getLists().getPictureBullet(0);

        msAssert.areEqual(expectedMarkup, shape.getMarkupLanguage());
        msAssert.areEqual(expectedHasFallback, shape.getFallbackShape() != null);
        if (expectedHasFallback)
        {
            msAssert.areEqual((expectedMarkup == ShapeMarkupLanguage.DML) ? ShapeMarkupLanguage.VML : ShapeMarkupLanguage.DML, shape.getFallbackShape().getMarkupLanguage());
        }
    }

    /// <summary>
    /// Tests direct left indent before and after ApplyBylletDefault method called for paragraph.
    /// </summary>
    private static void testApplyBulletDefault(String fileName, Object before, Object after) throws Exception
    {
        Document doc = TestUtil.open(fileName);

        Paragraph p = doc.getFirstSection().getBody().getFirstParagraph();

        if (before != null)
            msAssert.areEqual((/*int*/Integer)before, p.getParaPr().get(ParaAttr.LEFT_INDENT));
        else
            msAssert.isNull(p.getParaPr().get(ParaAttr.LEFT_INDENT));

        p.getListFormat().applyBulletDefault();

        if (after != null)
            msAssert.areEqual((/*int*/Integer)after, p.getParaPr().get(ParaAttr.LEFT_INDENT));
        else
            msAssert.isNull(p.getParaPr().get(ParaAttr.LEFT_INDENT));
    }

    /// <summary>
    /// Checks paragraph's label string and
    /// that paragraph's text starts with the specified <paramref name="expectedText"/>.
    /// </summary>
    private static void checkParagraphLabelAndText(Paragraph para, String expectedLabelString, String expectedText)
    {
        String paraText = para.getText();
        msAssert.isTrue(paraText.startsWith(expectedText), paraText);
        msAssert.areEqual(expectedLabelString, para.getListLabel().getLabelString());
    }

    /// <summary>
    /// This is helper method for TestJira14504 methods family.
    /// It checks how list definitions are imported to destination from the specified file.
    /// </summary>
    /// <param name="srcFile">Path to source file.</param>
    /// <param name="isReuseListDef">If true, then it is expected that list definitions have to be reused.</param>
    private static void checkListImportParaIndent(String srcFile, boolean isReuseListDef) throws Exception
    {
        Document dstDoc = TestUtil.open("Model\\List\\TesttJira14504ParaIndentDst.fopc");
        Document srcDoc = TestUtil.open(srcFile);
        int defsCountBefore = dstDoc.getLists().getListDefs().size();

        if (isReuseListDef)
            srcDoc.getFirstSection().getBody().getParagraphs().get(1).getChildNodes(NodeType.ANY, false).add(new Run(srcDoc, ""));

        dstDoc.appendDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);
        msAssert.areEqual(defsCountBefore + (isReuseListDef ? 0 : 1), dstDoc.getLists().getListDefs().size());

        Paragraph para = dstDoc.getLastSection().getBody().getParagraphs().get(2);
        msAssert.areEqual(6, para.getListFormat().getListId());
        msAssert.areEqual(0, para.getListFormat().getListLevel().getLevelNumber());
        msAssert.areEqual(isReuseListDef ? 8 : 11, para.getListFormat().getList().getListDefId());
    }

    /// <summary>
    /// Appends one document to another.
    /// </summary>
    private static Document appendDocuments(String dstFileName, String srcFileName,
        /*ImportFormatMode*/int importFormatMode, ImportFormatOptions importFormatOptions) throws Exception
    {
        Document srcDoc = TestUtil.open(srcFileName);
        Document dstDoc = TestUtil.open(dstFileName);

        dstDoc.appendDocument(srcDoc, importFormatMode, importFormatOptions);

        dstDoc.updateListLabels();

        return dstDoc;
    }
}

