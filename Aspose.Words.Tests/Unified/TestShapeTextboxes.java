// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.msObject;
import com.aspose.HashSetGeneric;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;

// TODO 2 Test user trying to put floating and inline shapes inside textboxes, inside textboxes.


/// <summary>
/// Unified tests for shapes with text.
/// </summary>
@Test
public class TestShapeTextboxes extends UnifiedTestsBase
{
    @BeforeMethod (alwaysRun = true)
    public void init()
    {
        RandomUtil.reset();
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTextboxFloating(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Textbox\\TestTextboxFloating", lf, sf);

        msAssert.areEqual("Simple shape with text.\r\f", doc.getText());

        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        // Drawing object appends the control character at the end of the text.
        msAssert.areEqual("Simple shape with text.\r", shape.getText());

        msAssert.areEqual(ShapeType.TEXT_BOX, shape.getShapeType());
        msAssert.areEqual("Simple shape with text.\r", shape.getText());

        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 0, true);
        msAssert.areEqual(para, shape.getParentNode());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTextboxFloatingSourceDataProvider")
	public void unifiedTestTextboxFloatingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTextboxFloating(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTextboxFloatingSourceDataProvider")
	Object[][] unifiedTestTextboxFloatingSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    /// WORDSNET-92 Inline textbox placement is incorrect when opened in Word 97.
    /// Inline escher is stored differently hence separate test.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTextboxInline(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Textbox\\TestTextboxInline", lf, sf);
        msAssert.areEqual("Before Textbox\r after.\f", doc.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTextboxInlineSourceDataProvider")
	public void unifiedTestTextboxInlineSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTextboxInline(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTextboxInlineSourceDataProvider")
	Object[][] unifiedTestTextboxInlineSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    void unifiedTestTextboxesInGroup(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Textbox\\TestTextboxesInGroup", lf, sf);

        GroupShape group = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true);
        msAssert.areEqual("Textbox1\rTextbox2\r", group.getText());

        msAssert.areEqual("", group.getChildNodes(NodeType.ANY, false).get(0).getText());
        msAssert.areEqual("Textbox1\r", group.getChildNodes(NodeType.ANY, false).get(1).getText());
        msAssert.areEqual("Textbox2\r", group.getChildNodes(NodeType.ANY, false).get(2).getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTextboxesInGroupSourceDataProvider")
	public void unifiedTestTextboxesInGroupSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTextboxesInGroup(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTextboxesInGroupSourceDataProvider")
	Object[][] unifiedTestTextboxesInGroupSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    /// At the moment linked textboxes are unlinked by Aspose.Words.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTextboxesLinked(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Textbox\\TestTextboxesLinked", lf, sf);

        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(ShapeType.TEXT_BOX, shape.getShapeType());

        // All text of the linked textboxes now appears in the first textbox in the chain.
        msAssert.areEqual("Text box 1\r\r\rText box 2\r\r\rText box 3\r", shape.getText());

        // The other textboxes that were linked have no text now.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        msAssert.areEqual("", shape.getText());

        shape = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        msAssert.areEqual("", shape.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTextboxesLinkedSourceDataProvider")
	public void unifiedTestTextboxesLinkedSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTextboxesLinked(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTextboxesLinkedSourceDataProvider")
	Object[][] unifiedTestTextboxesLinkedSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    void unifiedTestTextboxEmpty(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Textbox\\TestTextboxEmpty", lf, sf);

        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual("\r", shape.getText());

        // If textbox has no nodes, it results in no textbox written to the doc file.
        shape.removeAllChildren();
        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Textbox\\TestTextboxEmpty Modified", lf, sf);

        shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual("", shape.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTextboxEmptySourceDataProvider")
	public void unifiedTestTextboxEmptySource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTextboxEmpty(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTextboxEmptySourceDataProvider")
	Object[][] unifiedTestTextboxEmptySourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    void unifiedTestTextboxOptions(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Textbox\\TestTextboxOptions", lf, sf);

        // The default textbox.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(7.2, shape.getTextBox().getInternalMarginLeft());
        msAssert.areEqual(3.6, shape.getTextBox().getInternalMarginTop());
        msAssert.areEqual(7.2, shape.getTextBox().getInternalMarginRight());
        msAssert.areEqual(3.6, shape.getTextBox().getInternalMarginBottom());
        msAssert.areEqual(false, shape.getTextBox().getFitShapeToText());
        msAssert.areEqual(LayoutFlow.HORIZONTAL, shape.getTextBox().getLayoutFlow());
        msAssert.areEqual(TextBoxWrapMode.SQUARE, shape.getTextBox().getTextBoxWrapMode());
        msAssert.areEqual(TextBoxAnchor.TOP, shape.getTextBox().getTextBoxAnchor());

        // Textbox with internal margins.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        msAssert.areEqual(0.0, shape.getTextBox().getInternalMarginLeft());
        msAssert.areEqual(5.0, shape.getTextBox().getInternalMarginTop());
        msAssert.areEqual(10.0, shape.getTextBox().getInternalMarginRight());
        msAssert.areEqual(15.0, shape.getTextBox().getInternalMarginBottom());

        // Text centered vertically.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        msAssert.areEqual(TextBoxAnchor.MIDDLE, shape.getTextBox().getTextBoxAnchor());

        // Textbox with no text wrapping.
        // Although it is okay in the file, MS Word still does not seem to understand it.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 3, true);
        msAssert.areEqual(TextBoxWrapMode.NONE, shape.getTextBox().getTextBoxWrapMode());

        // Textbox where shape fits to text.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 4, true);
        msAssert.areEqual(true, shape.getTextBox().getFitShapeToText());

        // Text with vertical text.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 5, true);
        msAssert.areEqual(LayoutFlow.TOP_TO_BOTTOM, shape.getTextBox().getLayoutFlow());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTextboxOptionsSourceDataProvider")
	public void unifiedTestTextboxOptionsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTextboxOptions(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTextboxOptionsSourceDataProvider")
	Object[][] unifiedTestTextboxOptionsSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    void unifiedTestTextboxDirection(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Textbox\\TestTextboxDirection", lf, sf);

        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(LayoutFlow.HORIZONTAL, shape.getTextBox().getLayoutFlow());

        shape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        msAssert.areEqual(LayoutFlow.BOTTOM_TO_TOP, shape.getTextBox().getLayoutFlow());

        shape = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        msAssert.areEqual(LayoutFlow.TOP_TO_BOTTOM_IDEOGRAPHIC, shape.getTextBox().getLayoutFlow());

        shape = (Shape)doc.getChild(NodeType.SHAPE, 3, true);
        msAssert.areEqual(LayoutFlow.TOP_TO_BOTTOM, shape.getTextBox().getLayoutFlow());

        shape = (Shape)doc.getChild(NodeType.SHAPE, 4, true);
        msAssert.areEqual(LayoutFlow.HORIZONTAL_IDEOGRAPHIC, shape.getTextBox().getLayoutFlow());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTextboxDirectionSourceDataProvider")
	public void unifiedTestTextboxDirectionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTextboxDirection(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTextboxDirectionSourceDataProvider")
	Object[][] unifiedTestTextboxDirectionSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    void unifiedTestTextboxWithImage(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Textbox\\TestTextboxWithImage", lf, sf);

        Shape textbox = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(ShapeType.TEXT_BOX, textbox.getShapeType());

        Shape image = (Shape)textbox.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(ShapeType.IMAGE, image.getShapeType());

        Shape ole = (Shape)textbox.getChild(NodeType.SHAPE, 1, true);
        msAssert.areEqual(ShapeType.OLE_OBJECT, ole.getShapeType());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTextboxWithImageSourceDataProvider")
	public void unifiedTestTextboxWithImageSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTextboxWithImage(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTextboxWithImageSourceDataProvider")
	Object[][] unifiedTestTextboxWithImageSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    // FOSS Doc/Rtf are removed load formats.
    @Test (dataProvider = "testTextboxVerticalAnchorValidateDataProvider")
    public void testTextboxVerticalAnchorValidate(/*LoadFormat*/int lf) throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Textbox\\TestTextboxVerticalAnchor", lf);

        Shape textboxBottom = doc.getFirstSection().getBody().getShapes().get(0);
        Shape textboxMiddle = doc.getFirstSection().getBody().getShapes().get(1);
        Shape textboxTop = doc.getFirstSection().getBody().getShapes().get(2);

        msAssert.areEqual(TextBoxAnchor.TOP, textboxTop.getTextBox().getVerticalAnchor());
        msAssert.areEqual(TextBoxAnchor.MIDDLE, textboxMiddle.getTextBox().getVerticalAnchor());
        msAssert.areEqual(TextBoxAnchor.BOTTOM, textboxBottom.getTextBox().getVerticalAnchor());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTextboxVerticalAnchorValidateDataProvider")
	public static Object[][] testTextboxVerticalAnchorValidateDataProvider() throws Exception
	{
		return new Object[][]
		{
			{LoadFormat.DOCX},
			{LoadFormat.FLAT_OPC},
		};
	}

    // FOSS Doc/Rtf/WordML are removed formats; keep the OOXML/FlatOpc pairs.
    @Test (dataProvider = "testTextboxSetVerticalAnchorDataProvider")
    public void testTextboxSetVerticalAnchor(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        String srcFileName = "Model\\Shape\\Textbox\\TestTextboxVerticalAnchor";
        Document doc = TestUtil.open(srcFileName, lf);

        Shape textbox1 = doc.getFirstSection().getBody().getShapes().get(0);
        Shape textbox2 = doc.getFirstSection().getBody().getShapes().get(1);
        Shape textbox3 = doc.getFirstSection().getBody().getShapes().get(2);

        textbox3.getTextBox().setVerticalAnchor(TextBoxAnchor.MIDDLE);
        textbox2.getTextBox().setVerticalAnchor(TextBoxAnchor.BOTTOM_CENTERED);

        String outFileName = TestUtil.buildOutFileName(srcFileName, "", sf);
        doc = TestUtil.saveOpen(doc, outFileName, SaveOptions.createSaveOptions(sf), false);

        textbox1 = doc.getFirstSection().getBody().getShapes().get(0);
        textbox2 = doc.getFirstSection().getBody().getShapes().get(1);
        textbox3 = doc.getFirstSection().getBody().getShapes().get(2);

        msAssert.areEqual(TextBoxAnchor.BOTTOM, textbox1.getTextBox().getVerticalAnchor());
        msAssert.areEqual(TextBoxAnchor.TOP, textbox2.getTextBox().getVerticalAnchor());
        msAssert.areEqual(TextBoxAnchor.MIDDLE, textbox3.getTextBox().getVerticalAnchor());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTextboxSetVerticalAnchorDataProvider")
	public static Object[][] testTextboxSetVerticalAnchorDataProvider() throws Exception
	{
		return new Object[][]
		{
			{LoadFormat.DOCX,  SaveFormat.DOCX},
			{LoadFormat.DOCX,  SaveFormat.FLAT_OPC},
			{LoadFormat.FLAT_OPC,  SaveFormat.FLAT_OPC},
		};
	}

    // FOSS TestTextboxVerticalAnchorWarning removed: every case loads or saves a removed format
    // (Rtf/Doc/Dot/WordML) to check the format-specific vertical-anchor data-loss warnings.


    /// <summary>
    /// Verifies shape links, used in <see cref="TestDefect3862()" />
    /// </summary>
    private static void verifyShapeLinks(String testfile, Object[][] links, /*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen(testfile, lf, sf);

        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);

        for (int i = 0; i < shapes.getCount(); i++)
        {
            Shape shape = (Shape)shapes.get(i);
            msAssert.areEqual(shape.getId(), links[i][0]);
            msAssert.areEqual(shape.getDirectShapeAttrInternal(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID), links[i][1]);

            // Additionally check Txid of shape for DOC and RTF.
            if ((sf == SaveFormat.DOC) || (sf == SaveFormat.RTF))
                msAssert.areEqual(shape.getTxid(), links[i][2]);
        }
    }

    /// <summary>
    /// Append entire document to itself twice.
    /// </summary>
    @Test
    public void testLinkedImportAppendDocumentTwice() throws Exception
    {
        // In this case textboxes links should be preserved and output document should contain three textbox chains.
        //
        // Document clone operation produces shapes with the same IDs as original shapes and should be updated accordingly.
        // Currently AW uses simple method to get pseudo-unique shape IDs: it just adds new shape with ID started from big predefined value
        // and everything is fine till we don't append document twice. Second append operation numerates shapes from the same big predefined value
        // so shapes get lost uniqueness and malformed output is produced.
        //
        // That's why I'm forced to update maximum shape Id in destination document after each import.

        Document doc = TestUtil.open(LINKED_TEXTBOXES_FILENAME);
        // Verify preconditions.
        msAssert.areEqual(3, doc.getChildNodes(NodeType.SHAPE, true).getCount());
        msAssert.isTrue(areShapesLinked(doc, 0, 1));
        msAssert.isTrue(areShapesLinked(doc, 1, 2));
        msAssert.isTrue(isLinkedTail(doc, 2));

        Document clone1 = (Document)doc.deepClone(true);
        // Verify cloned document preserves links.
        msAssert.areEqual(3, clone1.getChildNodes(NodeType.SHAPE, true).getCount());
        msAssert.isTrue(areShapesLinked(clone1, 0, 1));
        msAssert.isTrue(areShapesLinked(clone1, 1, 2));
        msAssert.isTrue(isLinkedTail(clone1, 2));

        Document clone2 = (Document)doc.deepClone(true);
        msAssert.areEqual(3, clone2.getChildNodes(NodeType.SHAPE, true).getCount());

        // AppendDocument uses NodeImporter.
        doc.appendDocument(clone1, ImportFormatMode.USE_DESTINATION_STYLES);
        doc.appendDocument(clone2, ImportFormatMode.USE_DESTINATION_STYLES);

        // It is essential to verify uniqueness before saving.
        // Saved document will have unique shape Ids because DocumentValidator recalculates shape IDs and forces them unique.
        //
        // But if shape Ids were conflicting before saving textbox links will be lost.
        //
        // For example consider this test in details.
        //   Source document contains shape-1.2, shape-2.3, shape-3.0 (number before period is shape ID, number after period is TextboxNextShapeId).
        //   Cloned document contains exactly the same shapes: shape-1.2, shape-2.3, shape-3.0.
        //   After AppendDocument document contains two set of shapes with duplicated IDs. DocumentValidator fix shape IDs but he is not able to resolve
        //   shape links and produces the following shapes: shape-1.2, shape-2.3, shape-3.0, shape-4.2, shape-5.3 and shape-6.0
        //   This is not valid chains and Word fails to open such file.
        //   So shapes MUST have unique IDs and updated NextShapeIDs before saving.
        msAssert.isTrue(isShapeIdsUnique(doc));

        msAssert.areEqual(9, doc.getChildNodes(NodeType.SHAPE, true).getCount());
        // We get three linked chains now. First
        msAssert.isTrue(areShapesLinked(doc, 0, 1));
        msAssert.isTrue(areShapesLinked(doc, 1, 2));
        msAssert.isTrue(isLinkedTail(doc, 2));
        // Second.
        msAssert.isTrue(areShapesLinked(doc, 3, 4));
        msAssert.isTrue(areShapesLinked(doc, 4, 5));
        msAssert.isTrue(isLinkedTail(doc, 5));
        // And last one.
        msAssert.isTrue(areShapesLinked(doc, 6, 7));
        msAssert.isTrue(areShapesLinked(doc, 7, 8));
        msAssert.isTrue(isLinkedTail(doc, 8));

        // Save output for visual inspection.
        TestUtil.save(doc, "Model\\Shape\\Textbox\\TestLinkedImportAppendDocumentTwice.docx");
    }

    /// <summary>
    /// Append first and second shapes by separate import operations.
    /// </summary>
    @Test
    public void testLinkedImportPartOfChain() throws Exception
    {
        // In this case Word just copies textboxes itself and looses its content.
        // We could imitate the same but our implementation currently preserves content of first textbox.
        //
        // So in this test first shape should contain whole content and should not be linked to second textbox..
        Document doc = TestUtil.open("Model\\Shape\\Textbox\\TestTextboxesLinked.docx"); // FOSS .doc -> .docx
        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);
        Shape firstShape = (Shape)shapes.get(0);
        Shape secondShape = (Shape)shapes.get(1);
        Shape importedFirstShape = (Shape)doc.importNode(firstShape, true);
        Section section = doc.appendChild(new Section(doc));
        section.appendChild(new Body(doc));
        Paragraph para = section.getBody().appendChild(new Paragraph(doc));
        para.appendChild(importedFirstShape);
        Shape importedSecondShape = (Shape)doc.importNode(secondShape, true);
        para.appendChild(importedSecondShape);

        msAssert.isTrue(isShapeIdsUnique(doc));
        // First chain were existed in document before import.
        msAssert.isTrue(areShapesLinked(doc, 0, 1));
        msAssert.isTrue(areShapesLinked(doc, 1, 2));
        msAssert.isTrue(isLinkedTail(doc, 2));

        // Note link is broken and it's OK.
        msAssert.isFalse(areShapesLinked(doc, 3, 4));
        msAssert.isTrue(isLinkedTail(doc, 4));

        // Save output for visual inspection.
        TestUtil.save(doc, "Model\\Shape\\Textbox\\TestLinkedImportPartOfChain.docx");
    }

    /// <summary>
    /// Copy section to another doc which already has another shape links.
    /// </summary>
    @Test
    public void testLinkedImportToAnotherDocument() throws Exception
    {
        // Shape links should be preserved.
        Document doc = TestUtil.open(LINKED_TEXTBOXES_FILENAME);
        Document dstDoc = TestUtil.open(LINKED_TEXTBOXES_FILENAME);

        dstDoc.appendDocument(doc, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        msAssert.isTrue(isShapeIdsUnique(dstDoc));

        // First chain were existed in document before import.
        msAssert.isTrue(areShapesLinked(dstDoc, 0, 1));
        msAssert.isTrue(areShapesLinked(dstDoc, 1, 2));
        msAssert.isTrue(isLinkedTail(dstDoc, 2));

        // Second chain is imported from another document.
        msAssert.isTrue(areShapesLinked(dstDoc, 3, 4));
        msAssert.isTrue(areShapesLinked(dstDoc, 4, 5));
        msAssert.isTrue(isLinkedTail(dstDoc, 5));

        // Save output for visual inspection.
        TestUtil.save(dstDoc, "Model\\Shape\\Textbox\\TestLinkedImportToAnotherDocument.docx");
    }

    /// <summary>
    /// Copy all three textboxes by separate imports and restore links manually. Insane test.
    /// </summary>
    @Test
    public void testLinkedImportRestoreLinks() throws Exception
    {
        // Maybe we need such method? Something like Document.ImportLinkedTextboxes().
        Document doc = TestUtil.open(LINKED_TEXTBOXES_FILENAME);
        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);
        Shape firstShape = (Shape)shapes.get(0);
        Shape secondShape = (Shape)shapes.get(1);
        Shape thirdShape = (Shape)shapes.get(2);
        // Import texboxes one by one.
        Shape importedFirstShape = (Shape)doc.importNode(firstShape, true);
        Shape importedSecondShape = (Shape)doc.importNode(secondShape, true);
        Shape importedThirdShape = (Shape)doc.importNode(thirdShape, true);

        Section section = doc.appendChild(new Section(doc));
        section.appendChild(new Body(doc));
        Paragraph para = section.getBody().appendChild(new Paragraph(doc));
        para.appendChild(importedFirstShape);
        para.appendChild(importedSecondShape);
        para.appendChild(importedThirdShape);

        msAssert.areEqual(6, doc.getChildNodes(NodeType.SHAPE, true).getCount());

        // First chain were existed in document before import.
        msAssert.isTrue(areShapesLinked(doc, 0, 1));
        msAssert.isTrue(areShapesLinked(doc, 1, 2));
        msAssert.isTrue(isLinkedTail(doc, 2));

        // All imported shapes are not linked.
        msAssert.isFalse(areShapesLinked(doc, 3, 4));
        msAssert.isFalse(areShapesLinked(doc, 4, 5));

        // Restore link.
        importedFirstShape.setTextboxNextShapeId(importedSecondShape.getId());
        importedSecondShape.setTextboxNextShapeId(importedThirdShape.getId());

        msAssert.isTrue(isShapeIdsUnique(doc));

        msAssert.isTrue(areShapesLinked(doc, 0, 1));
        msAssert.isTrue(areShapesLinked(doc, 1, 2));
        msAssert.isTrue(isLinkedTail(doc, 2));

        msAssert.isTrue(areShapesLinked(doc, 3, 4));
        msAssert.isTrue(areShapesLinked(doc, 4, 5));
        msAssert.isTrue(isLinkedTail(doc, 5));

        // Save output for visual inspection.
        TestUtil.save(doc, "Model\\Shape\\Textbox\\TestLinkedImportRestoreLinks.docx");
    }

    /// <summary>
    /// Two imports of cloned section but without immediately append.
    /// </summary>
    @Test
    public void testLinkedImportDontAppendImmediately() throws Exception
    {
        // This test illustrates the case when user imports shapes but doesn't append for a while.
        Document doc = TestUtil.open(LINKED_TEXTBOXES_FILENAME);
        Section clonedSection1 = (Section)doc.getFirstSection().deepClone(true);
        Section clonedSection2 = (Section)doc.getFirstSection().deepClone(true);
        Section importedClonedSection1 = (Section)doc.importNode(clonedSection1, true);

        // At this point document not able to calculate maximum shape ID by traversing all shapes
        // because last imported shapes is not in node tree yet.
        Section importedClonedSection2 = (Section)doc.importNode(clonedSection2, true);
        doc.appendChild(importedClonedSection1);
        doc.appendChild(importedClonedSection2);

        msAssert.isTrue(isShapeIdsUnique(doc));

        msAssert.isTrue(areShapesLinked(doc, 0, 1));
        msAssert.isTrue(areShapesLinked(doc, 1, 2));
        msAssert.isTrue(isLinkedTail(doc, 2));

        msAssert.isTrue(areShapesLinked(doc, 3, 4));
        msAssert.isTrue(areShapesLinked(doc, 3, 4));
        msAssert.isTrue(isLinkedTail(doc, 5));

        // Save output for visual inspection.
        TestUtil.save(doc, "Model\\Shape\\Textbox\\TestLinkedImportDontAppendImmediately.docx");
    }

    /// <summary>
    /// Append cloned section as child without import.
    /// </summary>
    /// <remarks>
    /// AM. I couldn't find good solution for this problem. After discussion with DD we decided to postponed
    /// solution for this case to WORDSNET-5905 and integrate this as intermediate solution.
    /// </remarks>
    @Test
    public void testLinkedAppendChildWithoutImport() throws Exception
    {
        Document doc = TestUtil.open(LINKED_TEXTBOXES_FILENAME);

        Section clonedSection1 = (Section)doc.getFirstSection().deepClone(true);

        // Shape IDs is updated during import operation. But we do not import here and just AppendChild cloned node.
        // This code produces invalid document (Word shows error when opens output). And I don't see proper solution for this case.
        doc.appendChild(clonedSection1);

        msAssert.isTrue(areShapesLinked(doc, 0, 1));
        msAssert.isTrue(areShapesLinked(doc, 1, 2));
        msAssert.isTrue(isLinkedTail(doc, 2));

        // Uniqueness violation!
        msAssert.isFalse(isShapeIdsUnique(doc));

        // Note this! Forth shape refers to second but must to fifth.
        msAssert.isTrue(areShapesLinked(doc, 3, 1));
        // Note this! Fifth shape refers to third but must to sixth.
        msAssert.isTrue(areShapesLinked(doc, 4, 2));
        msAssert.isTrue(isLinkedTail(doc, 5));

        // This document is corrupted because of TextboxNextShapeId attribute of appended shapes are not updated properly.
        // Save output for visual inspection.
        TestUtil.save(doc, "Model\\Shape\\Textbox\\TestLinkedAppendChildWithoutImport.docx");
    }


    /// <summary>
    /// WORDSNET-7628 Other\TestBugInFilesFromRK.docx crashes.
    /// Word moves all content of linked textboxes into first textbox in chain.
    /// </summary>
    @Test
    public void testJira7628() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Textbox\\TestJira7628.docx");

        Shape first = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        Shape second = (Shape)doc.getChild(NodeType.SHAPE, 0, true);

        // Both shapes have one child paragraph with DrawingML in each.
        msAssert.areEqual(1, first.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual(1, second.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual(1, first.getChildNodes(NodeType.SHAPE, true).getCount());
        msAssert.areEqual(1, second.getChildNodes(NodeType.SHAPE, true).getCount());

        // FOSS Lets DocumentValidator work via a Docx save (Pdf removed).
        doc.save(new MemoryStream(), SaveFormat.DOCX);

        // Both DrawingML now is in paragraph of first shape. Second shape has no child.
        msAssert.isFalse(second.hasChildNodes());
        msAssert.areEqual(1, first.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual(2, first.getChildNodes(NodeType.SHAPE, true).getCount());
    }

    // FOSS TestJira8917 removed: it verifies linked-textbox target uniqueness when rendering a binary DOC
    // to PDF/HTML; DOC load and those render formats are removed, and its 5.6 MB DOC did not convert.


    // FOSS TestJira9455 removed: it loads and saves a binary DOC to verify AW no longer hangs on that
    // format's textbox-loop; DOC load/save is removed in the FOSS build.




    /// <summary>
    /// Relates to WORDSNET-11638 Additional test for WORDSNET-9672
    /// </summary>
    @Test
    public void testJira11638_9672() throws Exception
    {
        String inFile = TestUtil.buildTestFileName("ExportDocx\\TestJira9672.docx");
        String outFile = TestUtil.buildOutFileName(inFile, "", SaveFormat.DOCX); // FOSS Pdf -> Docx (still runs validation)

        Document doc = TestUtil.open(inFile);

        // Vefore chain before and after validation.
        msAssert.isFalse(areShapesLinked(doc, 0, 1));

        doc.save(outFile);

        msAssert.isFalse(areShapesLinked(doc, 0, 1));
    }


    /// <summary>
    /// WORDSNET-16745 Save method throws NullReferenceException after doing MailMerge
    /// Tests that only first link is preserved if duplicate links occurred
    /// </summary>
    @Test
    public void testJira16745B() throws Exception
    {
        Document doc = new Document();
        Shape shape = new Shape(doc); { shape.setId(100); shape.setTextboxNextShapeId(101); }
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(shape);
        shape = new Shape(doc); { shape.setId(101); }
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(shape);
        shape = new Shape(doc); { shape.setId(100); shape.setTextboxNextShapeId(101); }
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(shape);
        shape = new Shape(doc); { shape.setId(101); }
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(shape);
        TestUtil.executeValidator(doc, SaveFormat.DOCX);
        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);
        msAssert.areEqual(4, shapes.getCount());
        msAssert.areEqual(1025, (ms.as(shapes.get(0), Shape.class)).getId());
        msAssert.areEqual(1026, (ms.as(shapes.get(0), Shape.class)).getTextboxNextShapeId());
        msAssert.areEqual(1026, (ms.as(shapes.get(1), Shape.class)).getId());
        msAssert.areEqual(1027, (ms.as(shapes.get(2), Shape.class)).getId());
        msAssert.areEqual(0, (ms.as(shapes.get(2), Shape.class)).getTextboxNextShapeId());
        msAssert.areEqual(1028, (ms.as(shapes.get(3), Shape.class)).getId());
    }

    /// <summary>
    /// WORDSNET-11297 Add feature to create linked textboxes programmatically.
    /// <see cref="TextBox.Next"/> was implemented.
    /// </summary>
    @Test
    public void test11297() throws Exception
    {
        // Link 2 DML textboxes.
        Document doc = TestUtil.open("Model\\Shape\\Textbox\\Test11297DML.docx");

        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();

        msAssert.isTrue(shapes.get(1).getTextBox().isValidLinkTarget(shapes.get(0).getTextBox()));
        shapes.get(1).getTextBox().setNext(shapes.get(0).getTextBox());

        msAssert.isTrue(shapesAreLinked(shapes.get(1), shapes.get(0)));

        // Link 2 VML textboxes.
        doc = TestUtil.open("Model\\Shape\\Textbox\\Test11297VML.docx");
        shapes = doc.getFirstSection().getBody().getShapes();

        msAssert.isTrue(shapes.get(1).getTextBox().isValidLinkTarget(shapes.get(0).getTextBox()));
        shapes.get(1).getTextBox().setNext(shapes.get(0).getTextBox());

        msAssert.isTrue(shapesAreLinked(shapes.get(1), shapes.get(0)));

        // Link a textbox with a shape from GroupShape, which is not ShapeType.TextBox, but able to contain a text.
        doc = TestUtil.open("Model\\Shape\\Textbox\\Test11297_g.docx");
        shapes = doc.getFirstSection().getBody().getShapes();

        msAssert.isTrue(shapes.get(3).getTextBox().isValidLinkTarget(shapes.get(0).getTextBox()));
        shapes.get(3).getTextBox().setNext(shapes.get(0).getTextBox());

        msAssert.isTrue(shapesAreLinked(shapes.get(3), shapes.get(0)));
    }

    /// <summary>
    /// Additional checks for incorrect attributes in textboxes linking.
    /// </summary>
    @Test
    public void test11297Exceptions() throws Exception
    {
        // Attempt to link textbox to itself.
        Document doc = TestUtil.open("Model\\Shape\\Textbox\\Test11297DML.docx");
        msAssert.isTrue(verifyException(doc, 1, 1, WarningStrings.LINK_TEXTBOX_INVALID_TARGET));

        // Attempt to link textbox and a shape of another type.
        doc = TestUtil.open("Model\\Shape\\Textbox\\Test11297_a.docx");
        msAssert.isTrue(verifyException(doc, 1, 0, WarningStrings.LINK_TEXTBOX_BOTH_SHAPES_TEXT));

        // Attempt to link DML textbox with a VML textbox.
        doc = TestUtil.open("Model\\Shape\\Textbox\\Test11297_b.docx");
        msAssert.isTrue(verifyException(doc, 1, 0, WarningStrings.LINK_TEXTBOX_SAME_MARKUP));

        // Attempt to link textbox to non-empty textbox.
        doc = TestUtil.open("Model\\Shape\\Textbox\\Test11297_c.docx");
        msAssert.isTrue(verifyException(doc, 1, 0, WarningStrings.LINK_TEXTBOX_EMPTY_TARGET));

        // Verify behavior for the DML textboxes.

        // Attempt to link textbox to the target which already in a chain.
        doc = TestUtil.open("Model\\Shape\\Textbox\\Test11297_d.docx");
        msAssert.isTrue(verifyException(doc, 0, 1, WarningStrings.LINK_TEXTBOX_TARGET_IS_LINKED));

        // Attempt to link textbox which already has a link.
        doc = TestUtil.open("Model\\Shape\\Textbox\\Test11297_d.docx");
        msAssert.isTrue(verifyException(doc, 2, 0, WarningStrings.LINK_TEXTBOX_HAS_LINK));

        // Verify behavior for the VML textboxes.

        // Attempt to link textbox to the target which already in a chain.
        doc = TestUtil.open("Model\\Shape\\Textbox\\Test11297_e.docx");
        msAssert.isTrue(verifyException(doc, 0, 1, WarningStrings.LINK_TEXTBOX_TARGET_IS_LINKED));

        // Attempt to link textbox which already has a link.
        doc = TestUtil.open("Model\\Shape\\Textbox\\Test11297_e.docx");
        msAssert.isTrue(verifyException(doc, 2, 0, WarningStrings.LINK_TEXTBOX_HAS_LINK));

        // Attempt to link textbox from body to textbox from header.
        doc = TestUtil.open("Model\\Shape\\Textbox\\Test11297_f.docx");
        msAssert.isTrue(verifyException(doc, 2, 0, WarningStrings.LINK_TEXTBOX_DIFFERENT_STORY_TYPES));
    }

    /// <summary>
    /// Verifies BreakForwardLink.
    /// </summary>
    @Test (dataProvider = "test11297BreakForwardLinkDataProvider")
    public void test11297BreakForwardLink(String file) throws Exception
    {
        String filename = "Model\\Shape\\Textbox\\" + file;
        Document doc = TestUtil.open(filename);
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();

        // Initially all 4 shapes are linked.
        msAssert.isTrue(shapesAreLinked(shapes.get(0), shapes.get(1)));
        msAssert.isTrue(shapesAreLinked(shapes.get(1), shapes.get(2)));
        msAssert.isTrue(shapesAreLinked(shapes.get(2), shapes.get(3)));

        // Remove link at the first position.
        shapes.get(0).getTextBox().breakForwardLink();
        msAssert.isFalse(shapesAreLinked(shapes.get(0), shapes.get(1)));
        msAssert.isTrue(shapesAreLinked(shapes.get(1), shapes.get(2)));
        msAssert.isTrue(shapesAreLinked(shapes.get(2), shapes.get(3)));
        // DML check : Head of a newly created chain must contain an empty paragraph after the link remove.
        if (shapes.get(1).getMarkupLanguage() == ShapeMarkupLanguage.DML)
            msAssert.isTrue(shapes.get(1).hasChildNodes());

        doc = TestUtil.open(filename);
        shapes = doc.getFirstSection().getBody().getShapes();

        // Remove link at the second position.
        shapes.get(1).getTextBox().breakForwardLink();
        msAssert.isTrue(shapesAreLinked(shapes.get(0), shapes.get(1)));
        msAssert.isFalse(shapesAreLinked(shapes.get(1), shapes.get(2)));
        msAssert.isTrue(shapesAreLinked(shapes.get(2), shapes.get(3)));
        // DML check : Head of a newly created chain must contain an empty paragraph after the link remove.
        if (shapes.get(2).getMarkupLanguage() == ShapeMarkupLanguage.DML)
            msAssert.isTrue(shapes.get(2).hasChildNodes());

        doc = TestUtil.open(filename);
        shapes = doc.getFirstSection().getBody().getShapes();

        // Remove link at the third position by setting a null.
        shapes.get(2).getTextBox().setNext(null);
        msAssert.isTrue(shapesAreLinked(shapes.get(0), shapes.get(1)));
        msAssert.isTrue(shapesAreLinked(shapes.get(1), shapes.get(2)));
        msAssert.isFalse(shapesAreLinked(shapes.get(2), shapes.get(3)));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test11297BreakForwardLinkDataProvider")
	public static Object[][] test11297BreakForwardLinkDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Test11297BreakLinkVML.docx"},
			{"Test11297BreakLinkDML.docx"},
		};
	}

    /// <summary>
    /// WORDSNET-19044 Exception occurs in TextBox.InternalMarginBottom setter due to
    /// negative value. Negative values are allowed for InternalMargin attributes, no need to raise exception.
    /// </summary>
    @Test
    public void test19044() throws Exception
    {
        // Document illustrates, that negative values are allowed.
        Document doc = TestUtil.open("Model\\Shape\\Textbox\\Test19044.docx");
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);

        // Check values before set.
        msAssert.areEqual(-7.87, 0.01, shape.getTextBox().getInternalMarginTop());
        msAssert.areEqual(15, 0.01, shape.getTextBox().getInternalMarginBottom());
        msAssert.areEqual(-7.87, 0.01, shape.getTextBox().getInternalMarginLeft());
        msAssert.areEqual(10, 0.01, shape.getTextBox().getInternalMarginRight());

        shape.getTextBox().setInternalMarginTop(-5);
        shape.getTextBox().setInternalMarginBottom(-6);
        shape.getTextBox().setInternalMarginLeft(-7);
        shape.getTextBox().setInternalMarginRight(-8);

        MemoryStream stream = new MemoryStream();
        doc.save(stream, SaveFormat.DOCX);
        stream.setPosition(0);

        doc = new Document(stream);
        shape = doc.getFirstSection().getBody().getShapes().get(0);

        // Check values were set correctly.
        msAssert.areEqual(-5, 0.01, shape.getTextBox().getInternalMarginTop());
        msAssert.areEqual(-6, 0.01, shape.getTextBox().getInternalMarginBottom());
        msAssert.areEqual(-7, 0.01, shape.getTextBox().getInternalMarginLeft());
        msAssert.areEqual(-8, 0.01, shape.getTextBox().getInternalMarginRight());
    }

    /// <summary>
    /// WORDSNET-14369 Relations between linked textbox were broken after re-saving document.
    /// ShapeIdGenerator was improved to preserve textbox links.
    /// </summary>
    /// <remarks>
    /// This fix is a rework of WORDSNET-9455.
    /// </remarks>
    @Test
    public void test14369() throws Exception
    {
        // FOSS WordML -> Docx roundtrip (WordML removed).
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Textbox\\Test14369",
            TestUtil.getUnifiedScenario(LoadFormat.DOCX, SaveFormat.DOCX) | UnifiedScenario.NO_GOLD);

        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();
        msAssert.isTrue(shapesAreLinked(shapes.get(1), shapes.get(0)));
        msAssert.isTrue(shapesAreLinked(shapes.get(0), shapes.get(2)));
    }

    /// <summary>
    /// WORDSNET-21405 Image is inserted incorrectly into textbox after mail merge.
    /// If the shape is inside a textbox then Word scales the shape to fit in the textbox. Mimic Word.
    /// </summary>
    @Test
    public void test21405() throws Exception
    {
        String imagePath = TestUtil.buildTestFileName("Model\\Shape\\Textbox\\Test21405Image.png");
        Document doc = TestUtil.open("Model\\Shape\\Textbox\\Test21405.docx");

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToMergeField("mlOppStateCube1", true, true);
        Shape shape = builder.insertImageInternal(File.openRead(imagePath));

        msAssert.areEqual(110.05, 0.05d, shape.getWidth());
        msAssert.areEqual(117.3, 0.05d, shape.getHeight());
    }



    /// <summary>
    /// WORDSNET-27492 Linked text box chain is not loaded into the model (DOC)
    /// The <see cref="ShapeIdGenerator"/> class has been fixed to support the case of the input document.
    /// </summary>
    @Test
    public void test27492()
    {
        // FOSS Reduced: this is the binary-DOC variant of Test27369 (removed load format); the .docx
        // equivalent Test27369 already covers the same textbox chain, so nothing to load here.
    }

    /// <summary>
    /// Checks textbox chain of the Test26625.docx document.
    /// </summary>
    private static void check27369TextboxChain(Document doc)
    {
        // The linkage in the input DOCX file:
        // _x0000_s1027 => _x0000_s1034
        // _x0000_s1034 => _x0000_s1038
        // _x0000_s1039 => _x0000_s1031
        // _x0000_s1038 => _x0000_s1039
        // _x0000_s1032 => _x0000_s1032 // This cycle link will be removed during document validation.
        // _x0000_s1031 => _x0000_s1032

        Shape shape27 = (Shape)doc.getChild(NodeType.SHAPE, 3, true);
        Shape shape34 = (Shape)doc.getChild(NodeType.SHAPE, 5, true);
        Shape shape39 = (Shape)doc.getChild(NodeType.SHAPE, 6, true);
        Shape shape38 = (Shape)doc.getChild(NodeType.SHAPE, 8, true);
        Shape shape32 = (Shape)doc.getChild(NodeType.SHAPE, 9, true);
        Shape shape31 = (Shape)doc.getChild(NodeType.SHAPE, 11, true);

        msAssert.areEqual(shape34.getId(), shape27.getTextboxNextShapeId());
        msAssert.areEqual(shape38.getId(), shape34.getTextboxNextShapeId());
        msAssert.areEqual(shape39.getId(), shape38.getTextboxNextShapeId());
        msAssert.areEqual(shape31.getId(), shape39.getTextboxNextShapeId());
        msAssert.areEqual(shape32.getId(), shape31.getTextboxNextShapeId());
        msAssert.areEqual(shape32.getId(), shape32.getTextboxNextShapeId());
    }

    /// <summary>
    /// Test11297 routine. Creates link for textboxes. Verifies exception.
    /// </summary>
    private static boolean verifyException(Document doc, int source, int target, String message)
    {
        ShapeCollection shapes = new ShapeCollection(doc);

        if (shapes.get(source).getTextBox().isValidLinkTarget(shapes.get(target).getTextBox()))
            return false;

        try
        {
            shapes.get(source).getTextBox().setNext(shapes.get(target).getTextBox());
        }
        catch (IllegalArgumentException e)
        {
            return (msString.equals(e.getMessage(), message));
        }

        return false;
    }

    /// <summary>
    /// Verifies if shape1.TextBox is linked to shape2.TextBox.
    /// </summary>
    private static boolean shapesAreLinked(Shape shape1, Shape shape2)
    {
        return msObject.referenceEquals(shape1.getTextBox().getNext(), shape2.getTextBox());
    }

    /// <summary>
    /// Checks whether Shape.Id is unique for all shapes across whole document.
    /// </summary>
    private static boolean isShapeIdsUnique(Document doc)
    {
        HashSetGeneric<Integer> hashSet = new HashSetGeneric<Integer>();

        for (Shape shape : (Iterable<Shape>) doc.getChildNodes(NodeType.SHAPE, true))
            if (hashSet.contains(shape.getId()))
                return false;
            else
                hashSet.add(shape.getId());

        // No conflicting Ids.
        return true;
    }

    /// <summary>
    /// Verifies that shapes with given indexes are linked.
    /// </summary>
    private static boolean areShapesLinked(Document doc, int n1, int n2)
    {
        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);
        return (((Shape)shapes.get(n1)).getTextboxNextShapeId() == ((Shape)shapes.get(n2)).getId());
    }

    private static boolean isDmlsLinked(Document doc, int n1, int n2)
    {
        NodeCollection dmls = doc.getChildNodes(NodeType.SHAPE, true);
        int id1 = ((Shape)dmls.get(n1)).getTextboxId();
        int in2 = ((Shape)dmls.get(n2)).getLinkedTextboxId();

        if ((id1 <= 0) || (id1 <= 0))
            return false;

        return (id1 == in2);
    }

    /// <summary>
    /// Verifies that shape with given index is last in linked chain.
    /// </summary>
    private static boolean isLinkedTail(Document doc, int n)
    {
        Shape shape = (Shape)doc.getChildNodes(NodeType.SHAPE, true).get(n);
        return (shape.getTextboxNextShapeId() == 0);
    }

    /// <summary>
    /// Shape links, used in <see cref="TestDefect3862()" />
    /// </summary>
    private static /*final*/ Object[][] G_SHAPE_LINKS_1 = new Object[][] // Casting to object is for C++ porting.
                                                 {
                                                    { (Object)0x402, (Object)null,  (Object)0x0     },
                                                    { (Object)0x403, (Object)0x404, (Object)0x10000 },
                                                    { (Object)0x404, (Object)null,  (Object)0x10001 },
                                                    { (Object)0x405, (Object)null,  (Object)0x20000 },
                                                    { (Object)0x406, (Object)null,  (Object)0x30000 },
                                                    { (Object)0x407, (Object)null,  (Object)0x40000 },
                                                    { (Object)0x409, (Object)null,  (Object)0x0     },
                                                    { (Object)0x40a, (Object)0x40b, (Object)0x50000 },
                                                    { (Object)0x40b, (Object)null,  (Object)0x50001 },
                                                    { (Object)0x40c, (Object)null,  (Object)0x0     },
                                                    { (Object)0x40d, (Object)null,  (Object)0x60000 },
                                                    { (Object)0x40e, (Object)null,  (Object)0x70000 },
                                                    { (Object)0x40f, (Object)null,  (Object)0x80000 },
                                                    { (Object)0x410, (Object)null,  (Object)0x90000 },
                                                    { (Object)0x412, (Object)null,  (Object)0x0     },
                                                    { (Object)0x413, (Object)0x414, (Object)0xa0000 },
                                                    { (Object)0x414, (Object)null,  (Object)0xa0001 }
                                                 };

    private static /*final*/ Object[][] G_SHAPE_LINKS_2 = new Object[][]
                                                 {
                                                    { (Object)0x401, (Object)0x402, (Object)0x10000 },
                                                    { (Object)0x402, (Object)0x403, (Object)0x10001 },
                                                    { (Object)0x403, (Object)null,  (Object)0x10002 }
                                                 };

    /// <summary>
    /// Filename of document which is often used in linked textboxes tests.
    /// </summary>
    private static final String LINKED_TEXTBOXES_FILENAME = "Model\\Shape\\Textbox\\TestTextboxesLinked.docx"; // FOSS .doc -> .docx
}

