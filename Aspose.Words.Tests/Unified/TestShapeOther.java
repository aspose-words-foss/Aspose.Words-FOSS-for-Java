// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msPoint;
import com.aspose.ms.System.Drawing.msSize;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.Drawing.msSizeF;
import com.aspose.SystemPal;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for various shape features.
/// </summary>
@Test
public class TestShapeOther extends UnifiedTestsBase
{
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDefaultShapes(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Geometry\\TestDefaultShapes", lf, sf);

        // Verify some properties of a default oval.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(1025, shape.getId());

        msAssert.areEqual(ShapeType.ELLIPSE, shape.getShapeType());
        msAssert.areEqual(1, shape.getZOrder());

        // Verify most of the properties of a default rectangle.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        msAssert.areEqual(1026, shape.getId());

        msAssert.areEqual(ShapeType.RECTANGLE, shape.getShapeType());
        msAssert.areEqual(0, shape.getZOrder());

        msAssert.areEqual("", shape.getText());
        msAssert.areEqual(BWMode.AUTOMATIC, (/*BWMode*/Integer)shape.fetchShapeAttrInternal(ShapeAttr.BW_MODE));

        msAssert.areEqual(true, shape.getFilled());
        msAssert.areEqual(Color.WHITE.getRGB(), shape.getFillColor().getRGB());

        msAssert.areEqual(true, shape.getStroked());
        msAssert.areEqual(0.75, shape.getStrokeWeight());
        msAssert.areEqual(Color.BLACK.getRGB(), shape.getStrokeColor().getRGB());

        msAssert.areEqual(RelativeHorizontalPosition.COLUMN, shape.getRelativeHorizontalPosition());
        msAssert.areEqual(RelativeVerticalPosition.PARAGRAPH, shape.getRelativeVerticalPosition());
        msAssert.areEqual(24.0, shape.getLeft());
        msAssert.areEqual(18.0, shape.getTop());
        msAssert.areEqual(96.0, shape.getWidth());
        msAssert.areEqual(45.0, shape.getHeight());

        msAssert.areEqual(false, shape.getBehindText());
        msAssert.areEqual(WrapType.NONE, shape.getWrapType());

        msAssert.areEqual(false, shape.getAnchorLocked());
        msAssert.areEqual(true, shape.getAllowOverlap());
        msAssert.areEqual(true, shape.isLayoutInCell());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefaultShapesSourceDataProvider")
	public void unifiedTestDefaultShapesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefaultShapes(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefaultShapesSourceDataProvider")
	Object[][] unifiedTestDefaultShapesSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    /// Test generation of shape identifiers when creating or cloning shapes.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestShapeIdGenerate(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();

        // We generate unique shape identifiers for every new shape create.
        Shape shape1 = new Shape(doc, ShapeType.RECTANGLE);
        shape1.setWidth(100.0);
        shape1.setHeight(20.0);
        msAssert.areEqual(100001, shape1.getId());

        GroupShape group1 = new GroupShape(doc);
        msAssert.areEqual(100002, group1.getId());
        group1.appendChild(shape1);
        group1.setWidth(100.0);
        group1.setHeight(20.0);
        group1.setWrapType(WrapType.NONE);
        group1.setCoordOriginInternal(msPoint.Empty);
        group1.setCoordSizeInternal(msSize.ctor(100, 20));

        // Add the first group to the main text.
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(group1);


        // We no longer generate unique shape identifiers when cloning existing shapes.
        GroupShape group2 = (GroupShape)group1.deepClone(true);
        msAssert.areEqual(100002, group2.getId());
        Shape shape2 = (Shape)group2.getFirstChild();
        msAssert.areEqual(100001, shape2.getId());

        // Add the second group to the header.
        Paragraph para = new Paragraph(doc);
        para.appendChild(group2);
        HeaderFooter headerFooter = new HeaderFooter(doc, HeaderFooterType.HEADER_PRIMARY);
        headerFooter.appendChild(para);
        doc.getFirstSection().appendChild(headerFooter);


        // Save will generate new shape identifiers that are valid in MS Word.
        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Layout\\TestShapeIdGenerate", lf, sf);

        // Check the proper identifiers for shapes in the main text were generated.
        group1 = (GroupShape)doc.getFirstSection().getBody().getChild(NodeType.GROUP_SHAPE, 0, true);
        msAssert.areEqual(0x401, group1.getId());
        shape1 = (Shape)group1.getFirstChild();
        msAssert.areEqual(0x402, shape1.getId());

        // Check the proper identifiers for shapes in the header were generated.
        headerFooter = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);
        group2 = (GroupShape)headerFooter.getChild(NodeType.GROUP_SHAPE, 0, true);
        msAssert.areEqual(0x801, group2.getId());
        shape2 = (Shape)group2.getFirstChild();
        msAssert.areEqual(0x802, shape2.getId());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestShapeIdGenerateSourceDataProvider")
	public void unifiedTestShapeIdGenerateSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestShapeIdGenerate(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestShapeIdGenerateSourceDataProvider")
	Object[][] unifiedTestShapeIdGenerateSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    void unifiedTestImportShapes(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\Shape\\Layout\\TestImportShapes", lf);

        // This is how the shapes are in the original document.
        GroupShape srcGroup = (GroupShape)srcDoc.getChild(NodeType.GROUP_SHAPE, 0, true);
        msAssert.areEqual(1028, srcGroup.getId());
        Shape srcShape1 = (Shape)srcGroup.getChild(NodeType.SHAPE, 1, false);
        msAssert.areEqual(1026, srcShape1.getId());
        msAssert.areEqual(true, srcShape1.isImage());


        Document dstDoc = new Document();

        // Skip several shape ids in the destination document so it is easier to verify
        // shape ids of the imported shapes.
        for (int i = 0; i < 5; i++)
            dstDoc.getNextShapeId();

        dstDoc.getSections().removeAt(0);
        dstDoc.getSections().add(dstDoc.importNode(srcDoc.getSections().get(0), true));

        // The node importer used to generate shape id's, but not anymore.
        GroupShape dstGroup = (GroupShape)dstDoc.getChild(NodeType.GROUP_SHAPE, 0, true);
        msAssert.areEqual(1028, dstGroup.getId());

        // The node importer used to generate shape id's, but not anymore.
        Shape dstShape0 = (Shape)dstGroup.getChild(NodeType.SHAPE, 0, false);
        msAssert.areEqual(101379, dstShape0.getId());
        Shape dstShape1 = (Shape)dstGroup.getChild(NodeType.SHAPE, 1, false);
        msAssert.areEqual(101378, dstShape1.getId());
        msAssert.areEqual(true, srcShape1.isImage());

        // The image bytes were not duplicated, this is correct.
        msAssert.isTrue(srcShape1.getImageData().getImageBytes() == dstShape1.getImageData().getImageBytes());

        TestUtil.saveOpen(dstDoc, "Model\\Shape\\Layout\\TestImportShapes Modified", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestImportShapesSourceDataProvider")
	public void unifiedTestImportShapesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestImportShapes(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestImportShapesSourceDataProvider")
	Object[][] unifiedTestImportShapesSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    /// WordArt is called GeoText in MS Word specification. In VML it is called TextPath.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestGeoText(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\WordArt\\TestGeoText", lf, sf);

        // Pretty much default shape.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(ShapeType.TEXT_PLAIN_TEXT, shape.getShapeType());
        TextPath path = shape.getTextPath();
        msAssert.areEqual("Your Text 1", path.getText());
        msAssert.areEqual(true, path.getFitPath());
        msAssert.areEqual(true, path.getFitShape());
        msAssert.areEqual("Arial Black", path.getFontFamily());
        msAssert.areEqual(36.0, path.getSize());
        msAssert.areEqual(false, path.getBold());
        msAssert.areEqual(false, path.getItalic());
        msAssert.areEqual(false, path.getSmallCaps());
        msAssert.areEqual(false, path.getShadow());
        msAssert.areEqual(true, path.getOn());
        msAssert.areEqual(false, path.getUnderline());
        msAssert.areEqual(false, path.getStrikeThrough());
        msAssert.areEqual(true, path.getTrim());
        msAssert.areEqual(false, path.getRotateLetters());
        msAssert.areEqual(false, path.getSameLetterHeights());
        msAssert.areEqual(TextPathAlignment.CENTER, path.getTextPathAlignment());
        msAssert.areEqual(true, path.getKerning());
        msAssert.areEqual(false, path.getReverseRows());
        msAssert.areEqual(1.0, path.getSpacing());
        msAssert.areEqual(false, path.getXScale());

        // Changed some font properties.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        path = shape.getTextPath();
        msAssert.areEqual("Your Text 2", path.getText());
        msAssert.areEqual("Times New Roman", path.getFontFamily());
        msAssert.areEqual(20.0, path.getSize());
        msAssert.areEqual(false, path.getBold());
        msAssert.areEqual(true, path.getItalic());

        // Changed more font properties.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        path = shape.getTextPath();
        msAssert.areEqual("Your Text 3", path.getText());
        msAssert.areEqual(true, path.getBold());
        msAssert.areEqual(false, path.getItalic());
        msAssert.areEqual(true, path.getSameLetterHeights());
        msAssert.areEqual(false, path.getKerning());
        msAssert.areEqual(0.9, 0.01, path.getSpacing());
        msAssert.areEqual(3935, shape.getFill().getImageBytes().length);

        shape = (Shape)doc.getChild(NodeType.SHAPE, 3, true);
        msAssert.areEqual(ShapeType.TEXT_CURVE_UP, shape.getShapeType());
        path = shape.getTextPath();
        msAssert.areEqual("Your Text 4", path.getText());
        msAssert.areEqual(1.5, path.getSpacing());

        shape = (Shape)doc.getChild(NodeType.SHAPE, 4, true);
        path = shape.getTextPath();
        msAssert.areEqual(false, path.getFitShape());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestGeoTextSourceDataProvider")
	public void unifiedTestGeoTextSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestGeoText(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestGeoTextSourceDataProvider")
	Object[][] unifiedTestGeoTextSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    void unifiedTest3D(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Shape\\Extrusion\\Test3D", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTest3DSourceDataProvider")
	public void unifiedTest3DSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTest3D(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTest3DSourceDataProvider")
	Object[][] unifiedTest3DSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    void unifiedTest3DEx(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Shape\\Extrusion\\Test3DEx", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTest3DExSourceDataProvider")
	public void unifiedTest3DExSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTest3DEx(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTest3DExSourceDataProvider")
	Object[][] unifiedTest3DExSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    void unifiedTestCallout(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Shape\\Geometry\\TestCallout", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCalloutSourceDataProvider")
	public void unifiedTestCalloutSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCallout(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCalloutSourceDataProvider")
	Object[][] unifiedTestCalloutSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    void unifiedTestBackground(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document srcDoc = TestUtil.openSaveOpen("Model\\Shape\\Background\\TestBackground", lf, sf);

        msAssert.isNotNull(srcDoc.getBackgroundShape());
        msAssert.isTrue(srcDoc == srcDoc.getBackgroundShape().getDocument());
        msAssert.areEqual(true, srcDoc.getBackgroundShape().getFilled());
        msAssert.areEqual(FillTypeCore.SHADE_SCALE, srcDoc.getBackgroundShape().getFillCore().getFillType());
        msAssert.areEqual(-90.0, srcDoc.getBackgroundShape().getFillCore().getAngle());

        // Verify cloning of a document makes a background shape that belongs to the destination document.
        Document dstDoc = srcDoc.deepClone();
        msAssert.isNotNull(dstDoc.getBackgroundShape());
        // Background shape was cloned.
        msAssert.isTrue(srcDoc.getBackgroundShape() != dstDoc.getBackgroundShape());
        // Background shape belongs to the new document.
        msAssert.isTrue(dstDoc == dstDoc.getBackgroundShape().getDocument());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBackgroundSourceDataProvider")
	public void unifiedTestBackgroundSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBackground(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBackgroundSourceDataProvider")
	Object[][] unifiedTestBackgroundSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    void unifiedTestBackgroundFill(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Background\\TestBackgroundFill", lf, sf);

        Shape shape = doc.getBackgroundShape();
        msAssert.areEqual(true, shape.getFilled());
        msAssert.areEqual(/*unchecked*/((int)(int) 0xFFFFFF00L), shape.getFillColor().getRGB());
        msAssert.areEqual(FillType.SOLID, shape.getFill().getFillType());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBackgroundFillSourceDataProvider")
	public void unifiedTestBackgroundFillSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBackgroundFill(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBackgroundFillSourceDataProvider")
	Object[][] unifiedTestBackgroundFillSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    void unifiedTestBackgroundImage(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Background\\TestBackgroundImage", lf, sf);

        Shape shape = doc.getBackgroundShape();
        msAssert.areEqual(true, shape.getFilled());
        msAssert.areEqual(FillType.TEXTURED, shape.getFill().getFillType());
        // The image bytes is in the fill, not in the shape itself.
        msAssert.areEqual(null, shape.getImageData().getImageBytes());

        // RK Image fill is not supported in DOC yet.
        if (lf != LoadFormat.DOC)
            msAssert.isTrue(shape.getFill().getImageBytes() != null);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBackgroundImageSourceDataProvider")
	public void unifiedTestBackgroundImageSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBackgroundImage(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBackgroundImageSourceDataProvider")
	Object[][] unifiedTestBackgroundImageSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    /// Test shapes with hyperlinks.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestHyperlink(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Layout\\TestHyperlink", lf, sf);

        // This group is a canvas. The canvas itself is not hyperlinked.
        GroupShape group = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true);
        msAssert.areEqual("", group.getHRef());
        msAssert.areEqual("", group.getScreenTip());

        // The first shape inside the canvas is an empty picture frame (thanks to MS Word).
        // It is hyperlinked.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(ShapeType.IMAGE, shape.getShapeType());
        msAssert.areEqual("mailto:romeok@rosvet.com", shape.getHRef());
        msAssert.areEqual("", shape.getScreenTip());

        // Floating shape inside the canvas, hyperlinked, screen tip.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        msAssert.areEqual(ShapeType.ELLIPSE, shape.getShapeType());
        msAssert.areEqual("http://www.aspose.com/", shape.getHRef());
        msAssert.areEqual("Link to Aspose.com", shape.getScreenTip());

        // Floating picture inside the canvas, local hyperlink.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        msAssert.areEqual(ShapeType.IMAGE, shape.getShapeType());
        msAssert.areEqual("#bmk1", shape.getHRef());
        msAssert.areEqual("", shape.getScreenTip());

        // Inline picture.
        // A HYPERLINK field in the DOC file that contains a shape.
        // In the model the field is removed and the shape itself is hyperlinked.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 3, true);
        msAssert.areEqual(ShapeType.IMAGE, shape.getShapeType());
        msAssert.areEqual(false, shape.getImageData().isLink());
        msAssert.areEqual("http://www.aspose.com/Products/Default.aspx#Suites", shape.getHRef());
        msAssert.areEqual("Link to Suites.", shape.getScreenTip());

        // Inline shape.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 4, true);
        msAssert.areEqual(ShapeType.ELLIPSE, shape.getShapeType());
        msAssert.areEqual("http://www.microsoft.com/", shape.getHRef());
        msAssert.areEqual("", shape.getScreenTip());
        msAssert.areEqual("Test\r", shape.getText());

        // Inline textbox. Has HYPERLINK field inside the textbox.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 5, true);
        msAssert.areEqual(ShapeType.TEXT_BOX, shape.getShapeType());
        msAssert.areEqual("http://www.yahoo.com/", shape.getHRef());
        msAssert.areEqual("", shape.getScreenTip());
        msAssert.areEqual("\u0013 HYPERLINK \"http://www.aspose.com/\" \u0014http://www.aspose.com/\u0015\r", shape.getText());

        // Linked picture.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 6, true);
        msAssert.areEqual(ShapeType.IMAGE, shape.getShapeType());
        msAssert.areEqual(true, shape.getImageData().isLink());
        msAssert.areEqual("http://www.test.com/", shape.getHRef());
        msAssert.areEqual("", shape.getScreenTip());

        // There were several fields in the DOC for SHAPE, INCLUDEPICTURE and HYPERLINK,
        // but only one field remained in the model, it is for the hyperlink inside the textbox.
        msAssert.areEqual(1, doc.getChildNodes(NodeType.FIELD_START, true).getCount());
        msAssert.areEqual(1, doc.getChildNodes(NodeType.FIELD_SEPARATOR, true).getCount());
        msAssert.areEqual(1, doc.getChildNodes(NodeType.FIELD_END, true).getCount());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestHyperlinkSourceDataProvider")
	public void unifiedTestHyperlinkSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestHyperlink(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestHyperlinkSourceDataProvider")
	Object[][] unifiedTestHyperlinkSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    /// Tests hyperlink with and without a bookmark, with and without a frame, inline and float.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestHyperlinkOptions(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Layout\\TestHyperlinkOptions", lf, sf);

        // Test hyperlinks on floating shapes.
        verifyShapeHyperlink(doc, 0, "http://www.aspose.com/", "test title", "", false);
        verifyShapeHyperlink(doc, 1, "http://www.aspose.com/Products/Default.aspx#Suites", "", "", false);

        switch (TestUtil.getUnifiedScenario(lf, sf))
        {
            case UnifiedScenario.DOC_2_RTF:
            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
                // When a floating image is hyperlinked in RTF, there is no way to store target frame.
                verifyShapeHyperlink(doc, 2, "http://www.aspose.com/Products/Default.aspx#Suites", "", "", false);
                verifyShapeHyperlink(doc, 3, "#bmk1", "", "", false);
                break;
            default:
                verifyShapeHyperlink(doc, 2, "http://www.aspose.com/Products/Default.aspx#Suites", "", "_blank", false);
                verifyShapeHyperlink(doc, 3, "#bmk1", "", "_top", false);
                break;
        }

        // In DOC and RTF, MS Word can store relative path, but in WML it stores absolute path.
        // It is ugly, but I leave it as is. I don't want to resolve urls myself.
        verifyShapeHyperlink(doc, 4, "C:\\Program Files\\Automated QA\\AQtime 4\\EULA.rtf#test", "", "", false);

        // Test hyperlinks on inline shapes.
        verifyShapeHyperlink(doc, 5, "mailto:test@test.com?subject=TestSubject", "tip", "", true);
        verifyShapeHyperlink(doc, 6, "http://www.aspose.com/Products/Default.aspx#Suites", "", "", true);
        verifyShapeHyperlink(doc, 7, "http://www.aspose.com/Products/Default.aspx#Suites", "", "_blank", true);
        verifyShapeHyperlink(doc, 8, "#bmk1", "", "_top", true);
        verifyShapeHyperlink(doc, 9, "../../../../Program%20Files/Automated%20QA/AQtime%204/EULA.rtf#test", "", "", true);
        verifyShapeHyperlink(doc, 10, "\\\\Vladimira\\NetDir\\SomeDirectory\\Some Document.doc", "", "", true);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestHyperlinkOptionsSourceDataProvider")
	public void unifiedTestHyperlinkOptionsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestHyperlinkOptions(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestHyperlinkOptionsSourceDataProvider")
	Object[][] unifiedTestHyperlinkOptionsSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    private static void verifyShapeHyperlink(
        Document doc,
        int shapeIdx,
        String href,
        String title,
        String target,
        boolean isInline)
    {
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, shapeIdx, true);
        msAssert.areEqual(ShapeType.IMAGE, shape.getShapeType());
        msAssert.areEqual(href, shape.getHRef());
        msAssert.areEqual(title, shape.getScreenTip());
        msAssert.areEqual(target, shape.getTarget());
        msAssert.areEqual(isInline, shape.isInline());
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestShapesSimple(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Shape\\Unsorted\\TestShapesSimple", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestShapesSimpleSourceDataProvider")
	public void unifiedTestShapesSimpleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestShapesSimple(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestShapesSimpleSourceDataProvider")
	Object[][] unifiedTestShapesSimpleSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    /// WORDSNET-52 romeok - Duplicating floating images and shapes during mail merge problems.
    /// Shape ids were not recalculated and copying floating shapes created a mess of course.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCloneShape(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Unsorted\\TestCloneShape", lf, sf);

        doc.getSections().add(doc.getSections().get(0).deepClone(true));

        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Unsorted\\TestCloneShape Modified", lf, sf);

        // The dummy background shape was filtered out.
        // 0x401 - group, 0x402 - rect, 0x403 - freeform, 0x404 - oval with text.
        GroupShape group = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true);
        msAssert.areEqual(0x0401, group.getId());

        Shape shape = (Shape)group.getFirstChild();
        msAssert.areEqual(0x0402, shape.getId());

        //0x405 - group, 0x406 - rect, 0x407 - freeform, 0x408 - oval with text.
        group = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 1, true);
        msAssert.areEqual(0x0405, group.getId());

        shape = (Shape)group.getFirstChild();
        msAssert.areEqual(0x0406, shape.getId());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCloneShapeSourceDataProvider")
	public void unifiedTestCloneShapeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCloneShape(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCloneShapeSourceDataProvider")
	Object[][] unifiedTestCloneShapeSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    /// WORDSNET-584 Inserted images make resulting document much larger.
    /// This was because all raster images
    /// were written as png. Now changed so it looks for the original image format. If the original
    /// image format is jpeg, writes as jpeg, otherwise writes as png.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDefect584(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.insertImage(TestUtil.buildTestFileName("Model\\Shape\\Unsorted\\TestDefect584.jpg"));

        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Unsorted\\TestDefect584", lf, sf);
        String dstFileName = doc.getOriginalFileName();

        int maxExpectedSize = 67 * 1024;
        // RK For RTF we need a bigger size because image data is written in hexadecimal form
        // and it is written twice (the second time for old readers).
        if (sf == SaveFormat.RTF)
            maxExpectedSize = 190 * 1024;

        Stream stream = File.openRead(dstFileName);
        try /*JAVA: was using*/
    	{
            msAssert.isTrue(stream.getLength() < maxExpectedSize);
    	}
        finally { if (stream != null) stream.close(); }    //Without jpeg compression, the file was 428kb or so.
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefect584SourceDataProvider")
	public void unifiedTestDefect584Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefect584(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefect584SourceDataProvider")
	Object[][] unifiedTestDefect584SourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    /// WORDSNET-1508 Over 1024 shapes results in invalid DOC file.
    /// </summary>
    /// <param name="lf"></param>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestMany(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Layout\\TestMany", lf, sf);

        Shape shape;

        Body body = doc.getFirstSection().getBody();
        shape = (Shape)body.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(0x0401, shape.getId());

        shape = (Shape)body.getChild(NodeType.SHAPE, -1, true);
        msAssert.areEqual(0x0840, shape.getId());

        HeaderFooter header = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);
        shape = (Shape)header.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(0x0c01, shape.getId());    // 0x0c00 is patriarch.

        shape = (Shape)header.getChild(NodeType.SHAPE, -1, true);
        msAssert.areEqual(0x1040, shape.getId());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestManySourceDataProvider")
	public void unifiedTestManySource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestMany(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestManySourceDataProvider")
	Object[][] unifiedTestManySourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    /// Tests <see cref="SignatureLine"/> object properties.
    /// </summary>
    @Test
    public void testSignatureLineProperties() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Unsorted\\TestSignatureLineProperties.docx");
        Shape shape1 = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        checkSignatureLine(shape1.getSignatureLine(), "John Doe", "Manager", "john@doe.com",
            true, "", false, true);
        Shape shape2 = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        checkSignatureLine(shape2.getSignatureLine(), "Tom Lane", "Developer", "tom@lane.com",
            false, "", true, false);
        Shape shape3 = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        checkSignatureLine(shape3.getSignatureLine(), "Sean Bean", "Manager", "sean@bean.com",
            false, "test", false, true);

        SignatureLine signatureLine1 = shape1.getSignatureLine();
        signatureLine1.setSigner("Tom Lane");
        signatureLine1.setSignerTitle("Developer");
        signatureLine1.setEmail("tom@lane.com");
        signatureLine1.setDefaultInstructions(false);
        signatureLine1.setInstructions("test");
        signatureLine1.setAllowComments(true);
        signatureLine1.setShowDate(false);

        doc = TestUtil.saveOpen(doc, "ImportDocx\\TestSignatureLine.docx", null, false);
        shape1 = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        checkSignatureLine(shape1.getSignatureLine(), "Tom Lane", "Developer", "tom@lane.com",
            false, "test", true, false);
    }

    /// <summary>
    /// Checks signature line properties.
    /// </summary>
    private static void checkSignatureLine(SignatureLine signatureLine, String expectedSigner,
        String expectedSigner2, String expectedEmail, boolean expectedDefaultInstructions,
        String expectedInstructions, boolean expectedAllowComments, boolean expectedShowDate)
    {
        msAssert.isNotNull(signatureLine, "Wrong SignatureLine object.");
        msAssert.areEqual(expectedSigner, signatureLine.getSigner(), "Wrong signer.");
        msAssert.areEqual(expectedSigner2, signatureLine.getSignerTitle(), "Wrong signer title.");
        msAssert.areEqual(expectedEmail, signatureLine.getEmail(), "Wrong e-mail.");
        msAssert.areEqual(expectedDefaultInstructions, signatureLine.getDefaultInstructions(), "Wrong default instructions flag.");
        msAssert.areEqual(expectedInstructions, signatureLine.getInstructions(), "Wrong instructions.");
        msAssert.areEqual(expectedAllowComments, signatureLine.getAllowComments(), "Wrong allow comments flag.");
        msAssert.areEqual(expectedShowDate, signatureLine.getShowDate(), "Wrong show date flag.");
    }



    /// <summary>
    /// WORDSNET 7021 Doc to PDF conversion issue with image rendering.
    /// The problem occurred because there is negative angle (-90).
    /// Fixed by processing this angle.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestJira7021(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Unsorted\\TestJira7021", lf, sf);

        Node[] shapes = doc.getChildNodes(NodeType.SHAPE, true).toArray();

        for (Node shapeNode : shapes)
        {
            Shape shape = (Shape) shapeNode;//casting for java
            msAssert.areEqual(99.7, 0.9, msSizeF.getHeight(shape.getSize()));
            msAssert.areEqual(200.4, 0.9, msSizeF.getWidth(shape.getSize()));
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestJira7021SourceDataProvider")
	public void unifiedTestJira7021Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestJira7021(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestJira7021SourceDataProvider")
	Object[][] unifiedTestJira7021SourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    /// WORDSNET-12918 Add feature to get/set Alt Text Title property of Shape.
    /// We do not need regular unified scenarios here, we need to save Docx to Docx/Doc/Wml/Rtf/Odt,
    /// and check how the title was saved.
    /// </summary>
    @Test (dataProvider = "unifiedTestJira12918DataProvider")
    // FOSS Doc/WordML/Rtf/Odt are removed save formats; keep the Docx roundtrip.
    public void unifiedTestJira12918(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        SystemPal.saveCulture();
        // JAVA-deleted SaveUICulture(): No analog in Java.
        SystemPal.setStandardCulture();
        // JAVA-deleted SetStandardUICulture(): No analog in Java.
        try
        {
            Document doc = TestUtil.openSaveOpen("Model\\Shape\\Unsorted\\TestJira12918", lf, sf);

            Node[] shapes = doc.getChildNodes(NodeType.SHAPE, true).toArray();
            Shape shape0 = (Shape)shapes[0];
            Shape shape1 = (Shape)shapes[1];
            Shape shape2 = (Shape)shapes[2];
            Shape shape3 = (Shape)shapes[3];
            Shape shape4 = (Shape)shapes[4];

            switch (sf)
            {
                case SaveFormat.DOC:
                case SaveFormat.WORD_ML:
                case SaveFormat.RTF:
                {
                    msAssert.areEqual("Title: TestShapeTitle - Description: TestShapeDescription", shape0.getAlternativeText());
                    msAssert.areEqual("", shape0.getTitle());

                    msAssert.areEqual("Title: TestImageTitle - Description: TestImageDescription", shape1.getAlternativeText());
                    msAssert.areEqual("", shape1.getTitle());

                    msAssert.areEqual("Title: TestChartTitle - Description: TestChartDescription", shape2.getAlternativeText());
                    msAssert.areEqual("", shape2.getTitle());

                    msAssert.areEqual("TestImageDescription", shape3.getAlternativeText());
                    msAssert.areEqual("", shape3.getTitle());

                    msAssert.areEqual("Title: TestImageTitle", shape4.getAlternativeText());
                    msAssert.areEqual("", shape4.getTitle());
                    break;
                }
                case SaveFormat.DOCX:
                case SaveFormat.ODT:
                {
                    msAssert.areEqual("TestShapeDescription", shape0.getAlternativeText());
                    msAssert.areEqual("TestShapeTitle", shape0.getTitle());

                    msAssert.areEqual("TestImageDescription", shape1.getAlternativeText());
                    msAssert.areEqual("TestImageTitle", shape1.getTitle());

                    msAssert.areEqual("TestChartDescription", shape2.getAlternativeText());
                    msAssert.areEqual("TestChartTitle", shape2.getTitle());

                    msAssert.areEqual("TestImageDescription", shape3.getAlternativeText());
                    msAssert.areEqual("", shape3.getTitle());

                    msAssert.areEqual("", shape4.getAlternativeText());
                    msAssert.areEqual("TestImageTitle", shape4.getTitle());
                    break;
                }
                default:
                    throw new IllegalStateException("Unknown file format.");
            }
        }
        finally
        {
            SystemPal.restoreCulture();
            // JAVA-deleted RestoreUICulture(): No analog in Java.
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "unifiedTestJira12918DataProvider")
	public static Object[][] unifiedTestJira12918DataProvider() throws Exception
	{
		return new Object[][]
		{
			{LoadFormat.DOCX,  SaveFormat.DOCX},
		};
	}



    @Test
    public void testJira6729() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Unsorted\\TestJira6729.docx");
        Shape dml = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        dml.getFont().setHidden(true);
        TestUtil.saveOpen(doc, "Model\\Shape\\Unsorted\\TestJira6729.docx");
    }



    /// <summary>
    /// WORDSNET-12060 Watermark (shape node) is lost after re-saving Docx.
    /// Watermark shape has fraction in shape type.
    /// </summary>
    @Test
    public void testJira12060() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Unsorted\\TestJira12060.docx");
        Shape shape = (Shape)doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getChild(NodeType.SHAPE, 0, true);

        msAssert.areEqual(ShapeType.TEXT_PLAIN_TEXT, shape.getShapeType());
    }

    /// <summary>
    /// WORDSNET-12866 Provide API to set the Aspect Ratio to Scale.
    /// Made the ShapeBase.AspectRatioLocked property public.
    /// </summary>
    @Test
    public void testJira12866() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Unsorted\\TestJira12866.docx");

        ShapeBase groupShape = getShapeBase(doc, NodeType.GROUP_SHAPE, 0);
        Shape childShape0 = (Shape)groupShape.getChildNodes(NodeType.ANY, false).get(0);
        Shape childShape1 = (Shape)groupShape.getChildNodes(NodeType.ANY, false).get(1);

        ShapeBase shape2 = getShapeBase(doc, NodeType.SHAPE, 2);
        ShapeBase shape3 = getShapeBase(doc, NodeType.SHAPE, 3);
        ShapeBase shape4 = getShapeBase(doc, NodeType.SHAPE, 4);

        msAssert.isFalse(groupShape.getAspectRatioLocked(), "Group Shape");
        // It is possible to get/set AspectRatioLocked for child shape (mimic MS Word),
        // but please note it has effect only for top level shapes.
        msAssert.isTrue(childShape0.getAspectRatioLocked());
        msAssert.isFalse(childShape1.getAspectRatioLocked());

        msAssert.isFalse(shape2.getAspectRatioLocked());
        msAssert.isTrue(shape3.getAspectRatioLocked());
        msAssert.isFalse(shape4.getAspectRatioLocked());

        // Reset AspectRatioLocked.
        groupShape.setAspectRatioLocked(true);
        childShape0.setAspectRatioLocked(false);
        childShape1.setAspectRatioLocked(true);

        shape2.setAspectRatioLocked(true);
        shape3.setAspectRatioLocked(false);
        shape4.setAspectRatioLocked(true);

        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Unsorted\\TestJira12866", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        groupShape = getShapeBase(doc, NodeType.GROUP_SHAPE, 0);
        childShape0 = (Shape)groupShape.getChildNodes(NodeType.ANY, false).get(0);
        childShape1 = (Shape)groupShape.getChildNodes(NodeType.ANY, false).get(1);

        shape2 = getShapeBase(doc, NodeType.SHAPE, 2);
        shape3 = getShapeBase(doc, NodeType.SHAPE, 3);
        shape4 = getShapeBase(doc, NodeType.SHAPE, 4);

        msAssert.isTrue(groupShape.getAspectRatioLocked(), "Group Shape");
        msAssert.isFalse(childShape0.getAspectRatioLocked());
        msAssert.isTrue(childShape1.getAspectRatioLocked());

        msAssert.isTrue(shape2.getAspectRatioLocked());
        msAssert.isFalse(shape3.getAspectRatioLocked());
        msAssert.isTrue(shape4.getAspectRatioLocked());
    }

    /// <summary>
    /// WORDSNET-14006 Aspose.Words.FileCorruptedException is thrown while loading Docx.
    /// Shape's element 'xdr:txBody' has an undeclared namespace. MS Word ignores such shapes.
    /// </summary>
    @Test
    public void testJira14006() throws Exception
    {
        SystemPal.saveCulture();
        // JAVA-deleted SaveUICulture(): No analog in Java.
        try
        {
            // Warning message can be localized.
            SystemPal.setStandardCulture();
            // JAVA-deleted SetStandardUICulture(): No analog in Java.

            LoadOptions lo = new LoadOptions();
            lo.setWarningCallback(new WarningInfoCollection());
            lo.setLoadFormat(LoadFormat.DOCX);
            // Customer complaints about the document cannot be read due to filename extension.
            // Despite this is not actually a reason of the problem, lets open document with '.zip' extension.
            Document doc = TestUtil.open("Model\\Shape\\Unsorted\\TestJira14006.zip", lo);

            // Check warning is issued.
            WarningInfoCollection warnings = (WarningInfoCollection) doc.getWarningCallback();
            WarningInfo warningInfo = warnings.get(1);
            msAssert.areEqual(WarningType.UNEXPECTED_CONTENT, warningInfo.getWarningType());
            msAssert.areEqual(WarningSource.DRAWING_ML, warningInfo.getSource());
            // .Net exception:        "Shape is ignored due to: 'xdr' is an undeclared namespace."
            // NetStandard exception: "Shape is ignored due to: 'xdr' is an undeclared prefix"
            // Java one:              "Undeclared namespace prefix "xdr"..."
            msAssert.isTrue(warningInfo.getDescription().contains("ndeclared"));
            msAssert.isTrue(warningInfo.getDescription().contains("xdr"));

            NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);

            // Check all shapes were read.
            msAssert.areEqual(2, shapes.getCount());

            // Check charts were read successfully.
            msAssert.areEqual(2, (((Shape) shapes.get(1)).getChart().getChartSpace().getCharts().size()));

            // Check that group shape with shapes that have bad namespaces was not read.
            ShapeBase groupShape = getShapeBase(doc, NodeType.GROUP_SHAPE, 0);
            msAssert.isNull(groupShape);
        }
        finally
        {
            SystemPal.restoreCulture();
            // JAVA-deleted RestoreUICulture(): No analog in Java.
        }
    }

    /// <summary>
    /// WORDSNET-14012 Generating of the shapes identifiers.
    /// Pre-increment have to be used inside GetNextShapeId to avoid duplicate shape identifiers.
    /// </summary>
    @Test
    public void testJira14012() throws Exception
    {
        Document doc = new Document();
        doc.setNextShapeId(50);

        // GetNextShapeId have to return 50 + 1. In case of using post-increment it returns already used value (50).
        msAssert.areEqual(51, doc.getNextShapeId());
    }

    private ShapeBase getShapeBase(Document doc, /*NodeType*/int shapeType, int index)
    {
        return (ShapeBase)doc.getChild(shapeType, index, true);
    }

    @Test
    public void testAspectRatioLocked() throws Exception
    {
        Document doc = new Document();

        DocumentBuilder builder = new DocumentBuilder(doc);

        final double FRAME_HEIGHT = 100.0;
        final double FRAME_WIDTH = 300.0;
        Shape img = builder.insertImage(TestUtil.buildTestFileName("DocBuilder\\Image\\TestPng.png"));

        // Set AspectRatioLocked equals true and try to reset shape height.
        img.setAspectRatioLocked(true);

        img.setHeight(FRAME_HEIGHT);
        if (img.getWidth() > FRAME_WIDTH)
            img.setWidth(FRAME_WIDTH);

        msAssert.areEqual(100, img.getHeight());
        msAssert.areEqual(100, img.getWidth());
    }


    // FOSS TestJira14927 removed: it verifies the Fill type / Recolor flags written during the DML->VML
    // conversion when saving DOCX to WML, a removed format.



    /// <summary>
    /// WORDSNET-26193 Make ShapeBase.Hidden property public.
    /// Tests public property Shape.Hidden.
    /// </summary>
    @Test
    public void test26193() throws Exception
    {
        final String TEST_FILE_NAME = "Model\\Shape\\Unsorted\\Test26193";
        Document doc = TestUtil.open(TEST_FILE_NAME, LoadFormat.DOCX);

        Shape shape1 = doc.getFirstSection().getBody().getShapes().get(0);
        Shape shape2 = doc.getFirstSection().getBody().getShapes().get(1);

        msAssert.isTrue(shape1.getHidden());
        msAssert.isFalse(shape2.getHidden());

        shape1.setHidden(false);
        shape2.setHidden(true);

        doc = TestUtil.saveOpen(doc, TEST_FILE_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        msAssert.isFalse(doc.getFirstSection().getBody().getShapes().get(0).getHidden());
        msAssert.isTrue(doc.getFirstSection().getBody().getShapes().get(1).getHidden());
    }
}

