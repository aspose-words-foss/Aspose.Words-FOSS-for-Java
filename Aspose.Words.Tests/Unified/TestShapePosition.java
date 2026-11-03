// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Drawing.RectangleF;
import com.aspose.ms.System.Drawing.msPoint;
import com.aspose.ms.System.Drawing.msSize;
import com.aspose.ms.System.Drawing.msPointF;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for shape position and wrapping properties.
/// </summary>
@Test
public class TestShapePosition extends UnifiedTestsBase
{
    @Test
    public void testDefaultDimensions() throws Exception
    {
        Document doc = new Document();
        Shape shape = new Shape(doc, ShapeType.ELLIPSE);
        msAssert.areEqual(RectangleF.Empty, shape.getBoundsInternal());
        msAssert.areEqual(RectangleF.Empty, shape.getBoundsInPointsInternal());
        msAssert.areEqual(msPoint.Empty, shape.getCoordOriginInternal());
        msAssert.areEqual(msSize.ctor(21600, 21600), shape.getCoordSizeInternal());
    }

    @Test
    public void testNegativeLocation() throws Exception
    {
        Document doc = new Document();
        Shape shape = new Shape(doc, ShapeType.ELLIPSE);
        shape.setLeft(-10);
        shape.setTop(-20);
        msAssert.areEqual(msPointF.ctor(-10, -20), shape.getBoundsInternal().getLocation());
    }

    @Test
    public void testNegativeWidth() throws Exception
    {
        try
        {
            Document doc = new Document();
            Shape shape = new Shape(doc, ShapeType.ELLIPSE);
            shape.setWidth(-10);
            Assert.fail();
        }
        catch (IllegalArgumentException e)
        {
            // RK We do not use NUnit's ExpectedEsception and ExpectedMessage because 
            // MatchType is ignored when the test is run from ReSharper.
            msAssert.isTrue(e.getMessage().indexOf("Shape width cannot be less than 0.") >= 0);
        }
    }

    @Test
    public void testNegativeHeight() throws Exception
    {
        try
        {
            Document doc = new Document();
            Shape shape = new Shape(doc, ShapeType.ELLIPSE);
            shape.setHeight(-10);
            Assert.fail();
        }
        catch (IllegalArgumentException e)
        {
            msAssert.isTrue(e.getMessage().indexOf("Shape height cannot be less than 0.") >= 0);
        }
    }

    /// <summary>
    /// Top level shapes have limit on the shape size.
    /// </summary>
    @Test
    public void testHugeTopLevelWidth() throws Exception
    {
        try
        {
            Document doc = new Document();

            Shape shape = new Shape(doc, ShapeType.ELLIPSE);
            doc.getFirstSection().getBody().getFirstParagraph().appendChild(shape);

            msAssert.areEqual(true, shape.isTopLevel());
            // This line throws and it is expected behavior.
            shape.setWidth(1585.0);
            Assert.fail();
        }
        catch (IllegalArgumentException e)
        {
            msAssert.isTrue(e.getMessage().indexOf("Shape width cannot be greater than 1584 points.") >= 0);
        }
    }

    /// <summary>
    /// Child shapes have no limit on the shape size.
    /// </summary>
    @Test
    public void testHugeChildWidth() throws Exception
    {
        Document doc = new Document();
        
        GroupShape topLevel = new GroupShape(doc);
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(topLevel);

        Shape child = new Shape(doc, ShapeType.RECTANGLE);
        topLevel.appendChild(child);

        msAssert.areEqual(false, child.isTopLevel());
        child.setWidth(1585.0);
        msAssert.areEqual(1585, child.getWidth());
    }

    @Test
    public void testSetZeroCoordSize() throws Exception
    {
        try
        {
            Document doc = new Document();
            GroupShape group = new GroupShape(doc);
            group.setCoordSizeInternal(msSize.ctor(100, -1));
            Assert.fail();
        }
        catch (IllegalArgumentException e)
        {
            msAssert.isTrue(e.getMessage().indexOf("Local coordinate space size cannot be less than zero.") >= 0);
        }
    }

    @Test
    public void testSetCoordSize() throws Exception
    {
        Document doc = new Document();
        GroupShape group = new GroupShape(doc);
        
        // Set a valid value and check.
        group.setCoordSizeInternal(msSize.ctor(50, 20));
        msAssert.areEqual(group.getCoordSizeInternal(), msSize.ctor(50, 20));

        // Set an invalid value safely, it sets to default.
        group.setCoordSizeSafe(msSize.ctor(-1, -1));
        msAssert.areEqual(group.getCoordSizeInternal(), msSize.ctor(1000, 1000));

        // Set zero coordsize.
        group.setCoordSizeSafe(msSize.ctor(0, 0));
        msAssert.areEqual(group.getCoordSizeInternal(), msSize.ctor(0, 0));
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTopLevelBounds(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();

        Shape shape = new Shape(doc, ShapeType.ELLIPSE);
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(shape);
        shape.setLeft(40.0);
        shape.setTop(50.0);
        shape.setWidth(200.0);
        shape.setHeight(100.0);

        msAssert.areEqual(new RectangleF(40f, 50f, 200f, 100f), shape.getBoundsInternal());
        msAssert.areEqual(new RectangleF(40f, 50f, 200f, 100f), shape.getBoundsInPointsInternal());

        TestUtil.saveOpen(doc, "Model\\Shape\\Layout\\TestTopLevelBounds", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTopLevelBoundsSourceDataProvider")
	public void unifiedTestTopLevelBoundsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTopLevelBounds(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTopLevelBoundsSourceDataProvider")
	Object[][] unifiedTestTopLevelBoundsSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    void unifiedTestChildBounds(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();

        GroupShape group = new GroupShape(doc);
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(group);
        group.setBoundsInternal(new RectangleF(40f, 50f, 200f, 100f));
        group.setCoordSizeInternal(msSize.ctor(1000, 2000));
        group.setCoordOriginInternal(msPoint.ctor(-500, -1000));

        Shape shape = new Shape(doc, ShapeType.ELLIPSE);
        shape.setBoundsInternal(new RectangleF(0f, 0f, 100f, 100f));
        group.appendChild(shape);

        RectangleF rect = shape.getBoundsInPointsInternal();
        // The group's coordinates are set up in such a way that the child's 0,0 
        // will be at the center of the parent.
        msAssert.areEqual(40.0 + 200.0 / 2.0, (double)rect.getX());
        msAssert.areEqual(50.0 + 100.0 / 2.0, (double)rect.getY());

        // Width of the shape is 100 out of 1000 and height of the shape is 100 out of 2000.
        // Multiple by points to come to the points.
        msAssert.areEqual(100.0 / 1000.0 * 200.0, (double)rect.getWidth());
        msAssert.areEqual(100.0 / 2000.0 * 100.0, (double)rect.getHeight());

        TestUtil.saveOpen(doc, "Model\\Shape\\Layout\\TestChildBounds", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestChildBoundsSourceDataProvider")
	public void unifiedTestChildBoundsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestChildBounds(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestChildBoundsSourceDataProvider")
	Object[][] unifiedTestChildBoundsSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    void unifiedTestChildBoundsNoCoordSize(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();

        // Create a group shape that does not have CoordSize specified.
        GroupShape group = new GroupShape(doc);
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(group);
        group.setBoundsInternal(new RectangleF(40f, 50f, 200f, 100f));

        Shape shape = new Shape(doc, ShapeType.ELLIPSE);
        shape.setBoundsInternal(new RectangleF(0f, 0f, 100f, 100f));
        group.appendChild(shape);

        RectangleF rect = shape.getBoundsInPointsInternal();
        msAssert.areEqual(40.0, (double)rect.getX());
        msAssert.areEqual(50.0, (double)rect.getY());
        // Default coord size is 1000.
        msAssert.areEqual(100.0 / 1000.0 * 200.0, (double)rect.getWidth());
        msAssert.areEqual(100.0 / 1000.0 * 100.0, (double)rect.getHeight());

        TestUtil.saveOpen(doc, "Model\\Shape\\Layout\\TestChildBoundsNoCoordSize", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestChildBoundsNoCoordSizeSourceDataProvider")
	public void unifiedTestChildBoundsNoCoordSizeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestChildBoundsNoCoordSize(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestChildBoundsNoCoordSizeSourceDataProvider")
	Object[][] unifiedTestChildBoundsNoCoordSizeSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    void unifiedTestOuterLeftTopWidthHeight(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // Verify open save works.
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Layout\\TestOuterLeftTopWidthHeight", lf, sf);
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(50.0, shape.getLeft());
        msAssert.areEqual(40.0, shape.getTop());
        msAssert.areEqual(80.0, shape.getWidth());
        msAssert.areEqual(35.0, shape.getHeight());

        // Modify and verify save open works.
        shape.setLeft(10.0);
        shape.setTop(20.0);
        shape.setWidth(200.0);
        shape.setHeight(5.0);

        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Layout\\TestOuterLeftTopWidthHeight Modified", lf, sf);
        shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(10.0, shape.getLeft());
        msAssert.areEqual(20.0, shape.getTop());
        msAssert.areEqual(200.0, shape.getWidth());
        msAssert.areEqual(5.0, shape.getHeight());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestOuterLeftTopWidthHeightSourceDataProvider")
	public void unifiedTestOuterLeftTopWidthHeightSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestOuterLeftTopWidthHeight(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestOuterLeftTopWidthHeightSourceDataProvider")
	Object[][] unifiedTestOuterLeftTopWidthHeightSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    /// Check that the Absolute positioning options in the Layout / Advanced Layout / Picture Position work.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestPositionAbsolute(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // Verify open save works.
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Layout\\TestPositionAbsolute", lf, sf);
        checkPositionAbsolute(doc, 0, RelativeHorizontalPosition.PAGE, RelativeVerticalPosition.PAGE);
        checkPositionAbsolute(doc, 1, RelativeHorizontalPosition.MARGIN, RelativeVerticalPosition.MARGIN);
        checkPositionAbsolute(doc, 2, RelativeHorizontalPosition.COLUMN, RelativeVerticalPosition.PARAGRAPH);
        checkPositionAbsolute(doc, 3, RelativeHorizontalPosition.CHARACTER, RelativeVerticalPosition.LINE);

        // These are shapes created in Word 2007 using new position options.
        // When MS Word saves this to WordML or DOC it converts them to Page relative.
        // When MS Word saves this to DOCX it only saves them in DrawingML.
        // I don't yet know how to support these in the model and how to down convert so leave till later.
        // SonarIgnoreStart
        // CheckPositionAbsolute(doc, 4, RelativeHorizontalPosition.LeftMargin, RelativeVerticalPosition.TopMargin);
        // CheckPositionAbsolute(doc, 5, RelativeHorizontalPosition.RightMargin, RelativeVerticalPosition.BottomMargin);
        // CheckPositionAbsolute(doc, 6, RelativeHorizontalPosition.InsideMargin, RelativeVerticalPosition.InsideMargin);
        // CheckPositionAbsolute(doc, 7, RelativeHorizontalPosition.OutsideMargin, RelativeVerticalPosition.OutsideMargin);
        // SonarIgnoreEnd

        // Modify and very save open works.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        shape.setRelativeHorizontalPosition(RelativeHorizontalPosition.MARGIN);
        shape.setRelativeVerticalPosition(RelativeVerticalPosition.MARGIN);
        shape.setTop(-20);
        shape.setLeft(-20);

        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Layout\\TestPositionAbsolute Modified", lf, sf);
        checkPositionAbsolute(doc, 0, RelativeHorizontalPosition.MARGIN, RelativeVerticalPosition.MARGIN);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestPositionAbsoluteSourceDataProvider")
	public void unifiedTestPositionAbsoluteSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestPositionAbsolute(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestPositionAbsoluteSourceDataProvider")
	Object[][] unifiedTestPositionAbsoluteSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    private static void checkPositionAbsolute(
        Document doc, 
        int objectIdx, 
        /*RelativeHorizontalPosition*/int hPos, 
        /*RelativeVerticalPosition*/int vPos)
    {
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, objectIdx, true);
        msAssert.areEqual(hPos, shape.getRelativeHorizontalPosition());
        msAssert.areEqual(vPos, shape.getRelativeVerticalPosition());
    }

    /// <summary>
    /// Check that the Alignment positioning options in the Layout / Advanced Layout / Picture Position work.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestPositionAlignment(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // Verify open save works.
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Layout\\TestPositionAlignment", lf, sf);
        checkPositionAlignment(doc, 0, HorizontalAlignment.LEFT, VerticalAlignment.TOP);
        checkPositionAlignment(doc, 1, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        checkPositionAlignment(doc, 2, HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM);
        checkPositionAlignment(doc, 3, HorizontalAlignment.INSIDE, VerticalAlignment.INSIDE);
        checkPositionAlignment(doc, 4, HorizontalAlignment.OUTSIDE, VerticalAlignment.OUTSIDE);
        checkPositionAlignment(doc, 5, HorizontalAlignment.NONE, VerticalAlignment.NONE);

        // Modify and very save open works.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        shape.setHorizontalAlignment(HorizontalAlignment.CENTER);
        shape.setVerticalAlignment(VerticalAlignment.INSIDE);

        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Layout\\TestPositionAlignment Modified", lf, sf);
        checkPositionAlignment(doc, 0, HorizontalAlignment.CENTER, VerticalAlignment.INSIDE);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestPositionAlignmentSourceDataProvider")
	public void unifiedTestPositionAlignmentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestPositionAlignment(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestPositionAlignmentSourceDataProvider")
	Object[][] unifiedTestPositionAlignmentSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    private static void checkPositionAlignment(
        Document doc, 
        int objectIdx, 
        /*HorizontalAlignment*/int hAlign, 
        /*VerticalAlignment*/int vAlign)
    {
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, objectIdx, true);
        msAssert.areEqual(hAlign, shape.getHorizontalAlignment());
        msAssert.areEqual(vAlign, shape.getVerticalAlignment());
    }

    /// <summary>
    /// Check that the Book Layout positioning options in the Layout / Advanced Layout / Picture Position work.
    /// </summary>
    @Test
    public void testPositionBookLayout() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Layout\\TestPositionBookLayout.docx");
        
        // This positioning is actually just normal HorizontalAlignment.Inside and HoirzontalAlignment.Outside Relative to Margin or Page.
        checkPositionBookLayout(doc, 0, HorizontalAlignment.INSIDE, RelativeHorizontalPosition.MARGIN);
        checkPositionBookLayout(doc, 1, HorizontalAlignment.OUTSIDE, RelativeHorizontalPosition.MARGIN);
        checkPositionBookLayout(doc, 2, HorizontalAlignment.INSIDE, RelativeHorizontalPosition.PAGE);
        checkPositionBookLayout(doc, 3, HorizontalAlignment.OUTSIDE, RelativeHorizontalPosition.PAGE);
    }

    private static void checkPositionBookLayout(
        Document doc,
        int objectIdx,
        /*HorizontalAlignment*/int hAlign,
        /*RelativeHorizontalPosition*/int hPos)
    {
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, objectIdx, true);
        msAssert.areEqual(hAlign, shape.getHorizontalAlignment());
        msAssert.areEqual(hPos, shape.getRelativeHorizontalPosition());
    }

    /// <summary>
    /// Check that the Relative positioning options in the Layout / Advanced Layout / Picture Position work.
    /// WORDSNET-24133 /shape percent position/ Sidebar missing after opening and saving with Aspose.
    /// </summary>
    @Test
    public void testPositionRelative() throws Exception
    {
        // FOSS Rtf/Wml are removed; verify the Docx roundtrip (relative positioning fully supported).
        testPositionRelative(UnifiedScenario.DOCX_2_DOCX_NO_GOLD, true);
    }

    private static void testPositionRelative(/*UnifiedScenario*/int scenario, boolean hasRelative) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Layout\\TestPositionRelative", scenario);

        // Green rectangles.
        checkPositionRelative(doc, 0, 0.0, 0.0, 0, 0, RelativeHorizontalPosition.MARGIN, RelativeVerticalPosition.MARGIN, hasRelative);
        checkPositionRelative(doc, 1, 126.0, 144.0, 500, 500, RelativeHorizontalPosition.MARGIN, RelativeVerticalPosition.MARGIN, hasRelative);
        checkPositionRelative(doc, 2, 252.0, 288.0, 1000, 1000, RelativeHorizontalPosition.MARGIN, RelativeVerticalPosition.MARGIN, hasRelative);

        // Violet rectangles
        checkPositionRelative(doc, 3, 0.0, 0.0, 0, 0, RelativeHorizontalPosition.PAGE, RelativeVerticalPosition.PAGE, hasRelative);
        checkPositionRelative(doc, 4, 144.0, 172.8, 400, 400, RelativeHorizontalPosition.PAGE, RelativeVerticalPosition.PAGE, hasRelative);
        checkPositionRelative(doc, 5, 324.0, 388.8, 900, 900, RelativeHorizontalPosition.PAGE, RelativeVerticalPosition.PAGE, hasRelative);

        // Orange rectangles
        checkPositionRelative(doc, 6, -5.4, -3.6, -100, -50, RelativeHorizontalPosition.LEFT_MARGIN, RelativeVerticalPosition.BOTTOM_MARGIN, hasRelative);
        checkPositionRelative(doc, 7, 0.0, 0.0, 0, 0, RelativeHorizontalPosition.RIGHT_MARGIN, RelativeVerticalPosition.TOP_MARGIN, hasRelative);

        // Gray rectangles.
        checkPositionRelative(doc, 8, -27.0, 79.2, -500, 1100, RelativeHorizontalPosition.INSIDE_MARGIN, RelativeVerticalPosition.INSIDE_MARGIN, hasRelative);
        checkPositionRelative(doc, 9, 0.0, 122.4, 0, 1700, RelativeHorizontalPosition.OUTSIDE_MARGIN, RelativeVerticalPosition.OUTSIDE_MARGIN, hasRelative);
    }

    private static void checkPositionRelative(
        Document doc,
        int objectIdx,
        double left,
        double top,
        int leftPercent,
        int topPercent,
        /*RelativeHorizontalPosition*/int hPos,
        /*RelativeVerticalPosition*/int vPos,
        boolean hasRelative)
    {
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, objectIdx, true);

        // alexnosk: Use small delta to compare size. Delta is half of twip converted to point.
        // When we write to DOC or RTF, value is converted to twip and precision of the value might be decreased.
        // When we convert points to twips we round resulting value and half of twip is maximum value we can lose. 
        final double DELTA = 0.05;

        // These values are calculated by document validator.
        msAssert.areEqual(left, DELTA, shape.getLeft());
        msAssert.areEqual(top, DELTA, shape.getTop());

        if (hasRelative)
        {
            msAssert.areEqual(leftPercent, shape.getLeftPercent());
            msAssert.areEqual(topPercent, shape.getTopPercent());
        }
        else
        {
            msAssert.isNull(shape.getLeftPercent());
            msAssert.isNull(shape.getTopPercent());
        }

        msAssert.areEqual(hPos, shape.getRelativeHorizontalPosition());
        msAssert.areEqual(vPos, shape.getRelativeVerticalPosition());
    }

    /// <summary>
    /// Check that the relative size works.
    /// </summary>
    @Test
    public void testSizeRelative() throws Exception
    {
        // FOSS Rtf/Wml are removed; verify the Docx roundtrip (relative size fully supported).
        testSizeRelative(UnifiedScenario.DOCX_2_DOCX_NO_GOLD, true);
    }

    private static void testSizeRelative(/*UnifiedScenario*/int scenario, boolean hasRelative) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Layout\\TestSizeRelative", scenario);

        checkSizeRelative(doc, 0, 126.0, 43.2, 500, 150, RelativeHorizontalSize.MARGIN, RelativeVerticalSize.MARGIN, hasRelative);
        checkSizeRelative(doc, 1, 108.0, 43.2, 300, 100, RelativeHorizontalSize.PAGE, RelativeVerticalSize.PAGE, hasRelative);
        checkSizeRelative(doc, 2, 108.0, 36.0, 2000, 500, RelativeHorizontalSize.LEFT_MARGIN, RelativeVerticalSize.TOP_MARGIN, hasRelative);
        checkSizeRelative(doc, 3, 54.0, 72.0, 1000, 1000, RelativeHorizontalSize.RIGHT_MARGIN, RelativeVerticalSize.BOTTOM_MARGIN, hasRelative);
        checkSizeRelative(doc, 4, 54.0, 72.0, 1000, 1000, RelativeHorizontalSize.INNER_MARGIN, RelativeVerticalSize.INNER_MARGIN, hasRelative);
        checkSizeRelative(doc, 5, 54.0, 72.0, 1000, 1000, RelativeHorizontalSize.OUTER_MARGIN, RelativeVerticalSize.OUTER_MARGIN, hasRelative);
    }

    private static void checkSizeRelative(
        Document doc,
        int objectIdx,
        double width,
        double height,
        int widthPercent,
        int heightPercent,
        /*RelativeHorizontalSize*/int relWidth,
        /*RelativeVerticalSize*/int relHeight,
        boolean hasRelative)
    {
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, objectIdx, true);

        // alexnosk: Use small delta to compare size. Delta is half of twip converted to point.
        // When we write to DOC or RTF, value is converted to twip and precision of the value might be decreased.
        // When we convert points to twips we round resulting value and half of twip is maximum value we can lose. 
        final double DELTA = 0.05;

        // These values are calculated by document validator.
        msAssert.areEqual(width, DELTA, shape.getWidth());
        msAssert.areEqual(height, DELTA, shape.getHeight());

        if (hasRelative)
        {
            msAssert.areEqual(widthPercent, shape.getWidthPercent());
            msAssert.areEqual(heightPercent, shape.getHeightPercent());

            msAssert.areEqual(relWidth, shape.getRelativeHorizontalSize());
            msAssert.areEqual(relHeight, shape.getRelativeVerticalSize());
        }
        else
        {
            msAssert.isNull(shape.getWidthPercent());
            msAssert.isNull(shape.getHeightPercent());

            msAssert.areEqual(RelativeHorizontalSize.PAGE, shape.getRelativeHorizontalSize());
            msAssert.areEqual(RelativeVerticalSize.PAGE, shape.getRelativeVerticalSize());
        }
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestWrapType(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // Verify open save works.
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Layout\\TestWrapType", lf, sf);
        
        checkWrapType(doc, 0, WrapType.SQUARE, false);
        checkWrapType(doc, 1, WrapType.TIGHT, true);    // It seems for this types of wrapping MS Word sets shape "behind text" flag on.
        checkWrapType(doc, 2, WrapType.THROUGH, false);    
        checkWrapType(doc, 3, WrapType.TOP_BOTTOM, false);
        checkWrapType(doc, 4, WrapType.NONE, true);        // This shape is Behind text.
        checkWrapType(doc, 5, WrapType.NONE, false);    // This shape is in Front of text.
        checkWrapType(doc, 6, WrapType.INLINE, false);

        // Modify and very save open works.
        // Convert floating shape into inline.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        // Pretty cool, we can now convert non inline shapes into inline and back easily!
        shape.setWrapType(WrapType.INLINE);    
        // MS Word seems to be doing this too when changing from floating to inline, 
        // but I don't think we should do this automatically.
        shape.setAnchorLocked(true);
        shape.getLocks().setRotationLocked(true);
        shape.getLocks().setPositionLocked(true);

        // Make the shape that was below text to float above text.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 4, true);
        shape.setBehindText(false);

        // Make the shape that was above text to be behind text.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 5, true);
        shape.setBehindText(true);

        // Convert inline shape to floating.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 6, true);
        shape.setWrapType(WrapType.SQUARE);
        shape.setLeft(50.0);
        shape.setTop(20.0);
        // MS Word seems to be doing this too when changing from inline shape to floating, 
        // but I don't think we should do this automatically.
        shape.setRelativeHorizontalPosition(RelativeHorizontalPosition.COLUMN);
        shape.setRelativeVerticalPosition(RelativeVerticalPosition.PARAGRAPH);
        shape.setAnchorLocked(false);
        shape.getLocks().setRotationLocked(false);
        shape.getLocks().setPositionLocked(false);

        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Layout\\TestWrapType Modified", lf, sf);
        checkWrapType(doc, 0, WrapType.INLINE, false);
        checkWrapType(doc, 4, WrapType.NONE, false);    
        checkWrapType(doc, 5, WrapType.NONE, true);    
        checkWrapType(doc, 6, WrapType.SQUARE, false);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestWrapTypeSourceDataProvider")
	public void unifiedTestWrapTypeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestWrapType(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestWrapTypeSourceDataProvider")
	Object[][] unifiedTestWrapTypeSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    private static void checkWrapType(Document doc, int objectIdx, /*WrapType*/int wrapType, boolean isBehindText)
    {
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, objectIdx, true);
        msAssert.areEqual(wrapType, shape.getWrapType());
        msAssert.areEqual(isBehindText, shape.getBehindText());
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestInlineShape(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Layout\\TestInlineShape", lf, sf);
        
        // This checks that inline shapes are represented just like floating shapes.
        // The SHAPE field that is needed for inline shapes is synthesized in the DOC file.
        // So you need to open the file in MS Word various versions to check its all okay there.
        msAssert.areEqual("The quick dog.\r\rHello inline shapes.\f", doc.getText());

        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(true, shape.isInline());
        msAssert.areEqual(WrapType.INLINE, shape.getWrapType());
        msAssert.areEqual(0.0, shape.getLeft());
        msAssert.areEqual(0.0, shape.getTop());

        // Try to modify the inline shape, but it does not have effect in the DOC file because its inline.
        shape.setLeft(10.0);
        shape.setTop(10.0);
        shape.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
        shape.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);

        TestUtil.saveOpen(doc, "Model\\Shape\\Layout\\TestInlineShape Modified", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestInlineShapeSourceDataProvider")
	public void unifiedTestInlineShapeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestInlineShape(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestInlineShapeSourceDataProvider")
	Object[][] unifiedTestInlineShapeSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    void unifiedTestWrapSide(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Layout\\TestWrapSide", lf, sf);
        checkWrapSide(doc, 0, WrapSide.BOTH, 1.0, 2.0, 3.0, 4.0);
        checkWrapSide(doc, 1, WrapSide.LEFT, 0.0, 0.0, 9.0, 9.0);
        checkWrapSide(doc, 2, WrapSide.RIGHT, 0.0, 0.0, 9.0, 9.0);
        checkWrapSide(doc, 3, WrapSide.LARGEST, 0.0, 0.0, 9.0, 9.0);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestWrapSideSourceDataProvider")
	public void unifiedTestWrapSideSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestWrapSide(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestWrapSideSourceDataProvider")
	Object[][] unifiedTestWrapSideSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    private static void checkWrapSide(
        Document doc, 
        int objectIdx, 
        /*WrapSide*/int wrapSide,
        double distanceTop,
        double distanceBottom,
        double distanceLeft,
        double distanceRight)
    {
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, objectIdx, true);
        msAssert.areEqual(wrapSide, shape.getWrapSide());
        msAssert.areEqual(distanceTop, shape.getDistanceTop());
        msAssert.areEqual(distanceBottom, shape.getDistanceBottom());
        msAssert.areEqual(distanceLeft, shape.getDistanceLeft());
        msAssert.areEqual(distanceRight, shape.getDistanceRight());
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestPositionOptions(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Layout\\TestPositionOptions", lf, sf);
        checkPositionOptions(doc, 0, false, true, true);    // These are default settings.
        checkPositionOptions(doc, 1, true, true, true);        // Test AnchorLocked
        checkPositionOptions(doc, 2, false, false, true);    // Test AllowOverlap false
        checkPositionOptions(doc, 3, false, true, false);    // Test AllowInCell false
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestPositionOptionsSourceDataProvider")
	public void unifiedTestPositionOptionsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestPositionOptions(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestPositionOptionsSourceDataProvider")
	Object[][] unifiedTestPositionOptionsSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    private static void checkPositionOptions(
        Document doc,
        int objectIdx,
        boolean lockAnchor,
        boolean allowOverlap,
        boolean allowInCell)
    {
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, objectIdx, true);
        msAssert.areEqual(lockAnchor, shape.getAnchorLocked());
        msAssert.areEqual(allowOverlap, shape.getAllowOverlap());
        msAssert.areEqual(allowInCell, shape.isLayoutInCell());
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestRotation(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Geometry\\TestRotation", lf, sf);

        // These are top level shapes.
        checkRotation(doc, 0, 14.0, FlipOrientation.NONE, 102.55, 38.3);
        checkRotation(doc, 1, 54.0, FlipOrientation.NONE, 102.55, 38.3);
        checkRotation(doc, 2, 0.0, FlipOrientation.HORIZONTAL, 102.55, 38.3);
        checkRotation(doc, 3, 0.0, FlipOrientation.VERTICAL, 102.55, 38.3);
        checkRotation(doc, 4, 0.0, FlipOrientation.BOTH, 102.55, 38.3);
       
        // These are shapes in a group.
        checkRotation(doc, 5, 14.0, FlipOrientation.NONE, 2051.0, 766.0);
        checkRotation(doc, 6, 0.0, FlipOrientation.HORIZONTAL, 2051.0, 766.0);
        checkRotation(doc, 7, 0.0, FlipOrientation.VERTICAL, 2051.0, 766.0);
        checkRotation(doc, 8, 0.0, FlipOrientation.BOTH, 2051.0, 766.0);
        checkRotation(doc, 9, 54.0, FlipOrientation.NONE, 2051.0, 766.0);

        // Check the group itself.
        GroupShape group = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true);
        msAssert.areEqual(45, 1.0, group.getRotation());
        msAssert.areEqual(108.35, group.getWidth());
        msAssert.areEqual(291.9, group.getHeight());

        // Modify one of the shapes.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        shape.setRotation(54.0);

        TestUtil.saveOpen(doc, "Model\\Shape\\Geometry\\TestRotation Modified", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRotationSourceDataProvider")
	public void unifiedTestRotationSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRotation(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRotationSourceDataProvider")
	Object[][] unifiedTestRotationSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    private static void checkRotation(Document doc, int shapeIdx, double angle, /*FlipOrientation*/int orientation, double width, double height)
    {
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, shapeIdx, true);
        msAssert.areEqual(angle, 1.0, shape.getRotation());
        msAssert.areEqual(orientation, shape.getFlipOrientation());
        msAssert.areEqual(width, shape.getWidth());
        msAssert.areEqual(height, shape.getHeight());
    }

    /// <summary>
    /// Test that ZOrder works when shapes are anchored to different paragraphs.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestZOrderAnchorsDifferent(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // Verify open save works.
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Layout\\TestZOrderAnchorsDifferent", lf, sf);
        checkZOrder(doc, 0, 0, ShapeType.RECTANGLE, false);
        checkZOrder(doc, 1, 1, ShapeType.ELLIPSE, false);
        checkZOrder(doc, 2, 2, ShapeType.TRIANGLE, false);

        // Modify. This brings the rectangle to the top.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        shape.setZOrder(4);

        // Verify modified ZOrder works.
        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Layout\\TestZOrderAnchorsDifferent Modified", lf, sf);

        // Now the order is bottom Ellipse, Triangle, Rectangle top.
        checkZOrder(doc, 0, 2, ShapeType.RECTANGLE, false); 
        checkZOrder(doc, 1, 0, ShapeType.ELLIPSE, false);   
        checkZOrder(doc, 2, 1, ShapeType.TRIANGLE, false);  
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestZOrderAnchorsDifferentSourceDataProvider")
	public void unifiedTestZOrderAnchorsDifferentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestZOrderAnchorsDifferent(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestZOrderAnchorsDifferentSourceDataProvider")
	Object[][] unifiedTestZOrderAnchorsDifferentSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    /// Test that ZOrder works when shapes are anchored to the same paragraph.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestZOrderAnchorsSame(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // Verify open save works.
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Layout\\TestZOrderAnchorsSame", lf, sf);
        // Although drawing objects themselves appear in the document text in reverse order,
        // their Z order is still correct.
        checkZOrder(doc, 0, 2, ShapeType.TRIANGLE, false);
        checkZOrder(doc, 1, 1, ShapeType.ELLIPSE, false);
        checkZOrder(doc, 2, 0, ShapeType.RECTANGLE, false);

        // Modify. This brings the triangle to the bottom.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        shape.setZOrder(-1);    

        // Verify modified ZOrder works.
        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Layout\\TestZOrderAnchorsSame Modified", lf, sf);
        
        // Now the order is bottom Triangle, Rectangle, Ellipse top.
        checkZOrder(doc, 0, 0, ShapeType.TRIANGLE, false); 
        checkZOrder(doc, 1, 2, ShapeType.ELLIPSE, false);  
        checkZOrder(doc, 2, 1, ShapeType.RECTANGLE, false);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestZOrderAnchorsSameSourceDataProvider")
	public void unifiedTestZOrderAnchorsSameSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestZOrderAnchorsSame(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestZOrderAnchorsSameSourceDataProvider")
	Object[][] unifiedTestZOrderAnchorsSameSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    /// Test that ZOrder works for shapes behind and in front of text.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestZOrderBehind(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Layout\\TestZOrderBehind", lf, sf);
        // Current order: BehindText(Diamond, Circle), InFrontOfText(Rectangle, Line)
        checkZOrder(doc, 0, 2, ShapeType.RECTANGLE, false);
        checkZOrder(doc, 1, 1, ShapeType.FLOW_CHART_SUMMING_JUNCTION, true);
        checkZOrder(doc, 2, 0, ShapeType.DIAMOND, true);
        checkZOrder(doc, 3, 3, ShapeType.STRAIGHT_CONNECTOR_1, false);

        // New order I want: BehindText(Line, Diamond), InFrontOfText(Rectangle, Circle)

        // Modify line
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 3, true);
        shape.setBehindText(true);
        shape.setZOrder(10);

        // Modify diamond
        shape = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        shape.setBehindText(true);
        shape.setZOrder(20);

        // Modify rectangle
        shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        shape.setBehindText(false);
        shape.setZOrder(30);

        // Modify circle
        shape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        shape.setBehindText(false);
        shape.setZOrder(40);

        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Layout\\TestZOrderBehind Modified", lf, sf);

        checkZOrder(doc, 0, 2, ShapeType.RECTANGLE, false);
        checkZOrder(doc, 1, 3, ShapeType.FLOW_CHART_SUMMING_JUNCTION, false); 
        checkZOrder(doc, 2, 1, ShapeType.DIAMOND, true);  
        checkZOrder(doc, 3, 0, ShapeType.STRAIGHT_CONNECTOR_1, true); 
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestZOrderBehindSourceDataProvider")
	public void unifiedTestZOrderBehindSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestZOrderBehind(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestZOrderBehindSourceDataProvider")
	Object[][] unifiedTestZOrderBehindSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    private static void checkZOrder(Document doc, int objectIdx, int zOrder, /*ShapeType*/int shapeType, boolean isBehind)
    {
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, objectIdx, true);
        msAssert.areEqual(shapeType, shape.getShapeType());
        msAssert.areEqual(zOrder, shape.getZOrder());
        msAssert.areEqual(isBehind, shape.getBehindText());
    }



    /// <summary>
    /// WORDSNET-16546 Implemented the public property <see cref="ShapeBase.IsLayoutInCell"/>.
    /// </summary>
    @Test
    public void testJira16546() throws Exception
    {
        final String FILE_NAME = "Model\\Shape\\Unsorted\\TestJira16546";
        Document doc = TestUtil.open(FILE_NAME, LoadFormat.DOCX); // FOSS .doc -> .docx
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        msAssert.isTrue(shape.isLayoutInCell());

        shape.isLayoutInCell(false);
        TestUtil.saveOpen(doc, FILE_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        shape = doc.getFirstSection().getBody().getShapes().get(0);
        msAssert.isFalse(shape.isLayoutInCell());

        shape.isLayoutInCell(true);
        TestUtil.saveOpen(doc, FILE_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        shape = doc.getFirstSection().getBody().getShapes().get(0);
        msAssert.isTrue(shape.isLayoutInCell());
    }




}

