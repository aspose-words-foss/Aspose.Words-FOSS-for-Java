// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Drawing.msPoint;
import com.aspose.ms.System.Drawing.msSize;
import com.aspose.ms.System.Drawing.RectangleF;
import com.aspose.ConvertUtilCore;
import java.util.ArrayList;
import com.aspose.ms.System.msMath;
import com.aspose.ms.System.MidpointRounding;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for group shapes.
/// </summary>
@Test (groups = "SingleThreadGroup")
public class TestShapeGroup extends UnifiedTestsBase
{
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestGroupShape(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Geometry\\TestGroupShape", lf, sf);

        msAssert.areEqual(2, doc.getChildNodes(NodeType.GROUP_SHAPE, true).getCount());

        // Check the group.
        GroupShape group = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true);
        msAssert.areEqual(true, group.isGroup());
        msAssert.areEqual(EditAs.GROUP, group.getEditAs());
        msAssert.areEqual(true, group.isTopLevel());
        checkPosition(group, 30.0, 35.0, 94.0, 64.0);
        checkCoordinateSpace(group, 2400, 2140, 1880, 1280);
        msAssert.areEqual(3, group.getCount());

        // This shape is in parent coordinate space.
        Shape rect = (Shape)group.getFirstChild();
        msAssert.areEqual(false, rect.isTopLevel());
        msAssert.areEqual(ShapeType.RECTANGLE, rect.getShapeType());
        checkPosition(rect, 2400.0, 2140.0, 1080.0, 900.0);

        // I don't play with group CoordSize and CoordOrigin, it will work, but it will make
        // the shape selection look a bit ugly because the bounding rectangle should be synced.
        // I will leave this as is for now. Maybe later make nice public shape moving methods.

        // Widen the whole group.
        group.setWidth(400.0);
        // Make the rectangle as high as the group itself.
        rect.setHeight(1280.0);

        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Geometry\\TestGroupShape Modified", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestGroupShapeSourceDataProvider")
	public void unifiedTestGroupShapeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestGroupShape(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestGroupShapeSourceDataProvider")
	Object[][] unifiedTestGroupShapeSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}



    private static void checkPosition(ShapeBase shape, double left, double top, double width, double height)
    {
        msAssert.areEqual(left, shape.getLeft());
        msAssert.areEqual(top, shape.getTop());
        msAssert.areEqual(width, shape.getWidth());
        msAssert.areEqual(height, shape.getHeight());
    }

    private static void checkCoordinateSpace(ShapeBase shape, int originX, int originY, int sizeX, int sizeY)
    {
        msAssert.areEqual(originX, msPoint.getX(shape.getCoordOriginInternal()));
        msAssert.areEqual(originY, msPoint.getY(shape.getCoordOriginInternal()));
        msAssert.areEqual(sizeX, msSize.getWidth(shape.getCoordSizeInternal()));
        msAssert.areEqual(sizeY, msSize.getHeight(shape.getCoordSizeInternal()));
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCanvas(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Layout\\TestCanvas", lf, sf);

        GroupShape group = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true);
        msAssert.areEqual(EditAs.CANVAS, group.getEditAs());

        Shape shape = (Shape)group.getChild(NodeType.SHAPE, 0, true);
        // This shape is actually positioned 36 pt from top left corner of the canvas.
        // So these values below are somewhat ugly for the user to deal with, but no other ideas at this stage.
        msAssert.areEqual(2446.0, shape.getLeft());
        msAssert.areEqual(RelativeHorizontalPosition.COLUMN, shape.getRelativeHorizontalPosition());

        shape = (Shape)group.getChild(NodeType.SHAPE, 1, true);
        // This shape is positioned at 0,0 from the Canvas center. Although MS Word allows to do that in
        // the user interface, after clicking OK in the dialog box it shows position from the top left corner.
        // So the shape is positioned 216 pt from the top left corner.
        msAssert.areEqual(3046.0, shape.getLeft());
        msAssert.areEqual(RelativeHorizontalPosition.COLUMN, shape.getRelativeHorizontalPosition());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCanvasSourceDataProvider")
	public void unifiedTestCanvasSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCanvas(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCanvasSourceDataProvider")
	Object[][] unifiedTestCanvasSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
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
    /// WORDSNET-27303 Shape.Bounds returned by Aspose.Words is not correct for rotated shape in group shape.
    /// Now shape rotation is taken into account when calculating the bounds.
    /// </summary>
    @Test (dataProvider = "test27303DataProvider")
    public void test27303(String fileName, double expectedWidth, double expectedHeight) throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\" + fileName);
        Shape rotatedShape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        RectangleF bounds = rotatedShape.getBoundsInPointsInternal();

        msAssert.areEqual(expectedWidth, 0.005, ConvertUtilCore.pointToCm(bounds.getWidth()));
        msAssert.areEqual(expectedHeight, 0.005, ConvertUtilCore.pointToCm(bounds.getHeight()));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test27303DataProvider")
	public static Object[][] test27303DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Test27303.docx",  4.69,  2.80},
			{"Test27303_30Rot.docx",  1.82,  7.23},
			{"Test27303_45Rot.docx",  4.69,  2.80},
			{"Test27303_135Rot.docx",  1.82,  7.23},
		};
	}

    /// <summary>
    /// WORDSNET-27896 Coordinates of rotated shape are returned differently after 24.12 version
    /// Changed the way shape coordinates are calculated to support different scaling along the X and Y axes with
    /// rotated shapes.
    /// </summary>
    @Test (dataProvider = "test27896DataProvider")
    public void test27896(String fileName, double expectedWidth, double expectedHeight) throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\" + fileName);
        ArrayList<Shape> shapes = doc.getChildNodes(NodeType.SHAPE, true).<Shape>toList();

        // ShapeBase.BoundInPoints returns shape coordinates without rotation: the coordinates are checked taking this
        // into account. Also, shape's outline appears to be drawn over its rectangle, which increases the shape's
        // display bouds.
        // The coordinates are taken from a screenshot of Test27896NoGroupRotation.docx in MS Word.
        checkShapeBounds(240, 130, 316, 306, shapes.get(0).getBoundsInPointsInternal(), shapes.get(0).getRotation());
        checkShapeBounds(444, 137, 609, 280, shapes.get(1).getBoundsInPointsInternal(), shapes.get(1).getRotation());
        checkShapeBounds(472, 333, 548, 522, shapes.get(2).getBoundsInPointsInternal(), shapes.get(2).getRotation());
        checkShapeBounds(190, 366, 354, 508, shapes.get(3).getBoundsInPointsInternal(), shapes.get(3).getRotation());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test27896DataProvider")
	public static Object[][] test27896DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Test27896.docx",  4.69,  2.80},
			{"Test27896NoGroupRotation.docx",  1.82,  7.23},
		};
	}

    /// <summary>
    /// WORDSNET-28627 Regression: Bounds are correct only after setting the width/height of the groupShape.
    /// Tests getting BoundsInPoints after VML shapes grouping.
    /// </summary>
    @Test
    public void test28627() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Creates new VML shape.
        Shape shape = new Shape(doc, ShapeType.RECTANGLE);
        shape.setWidth(75.1);
        shape.setHeight(56.4);

        GroupShape group = builder.insertGroupShape(0d, 0d, 0d, 0d, shape);

        RectangleF boundsInPoints1 = shape.getBoundsInPointsInternal();

        msAssert.areEqual(0, 0.1, boundsInPoints1.getLeft());
        msAssert.areEqual(0, 0.1, boundsInPoints1.getTop());
        msAssert.areEqual(75.1, 0.1, boundsInPoints1.getWidth());
        msAssert.areEqual(56.4, 0.1, boundsInPoints1.getHeight());

        group.setWidth(100.0);
        group.setHeight(100.0);

        RectangleF boundsInPoints2 = ((Shape)(group.getFirstChild())).getBoundsInPointsInternal();

        msAssert.areEqual(boundsInPoints1.getLeft(), 0.1, boundsInPoints2.getLeft());
        msAssert.areEqual(boundsInPoints1.getTop(), 0.1, boundsInPoints2.getTop());
        msAssert.areEqual(boundsInPoints1.getWidth(), 0.1, boundsInPoints2.getWidth());
        msAssert.areEqual(boundsInPoints1.getHeight(), 0.1, boundsInPoints2.getHeight());
    }

    /// <summary>
    /// Checks shape bounds in pixels.
    /// </summary>
    private static void checkShapeBounds(int pxX1, int pxY1, int pxX2, int pxY2, RectangleF bounds, double rotation)
    {
        boolean swapXY = ((int)msMath.round(rotation / 90.0, 0, MidpointRounding.AWAY_FROM_ZERO) % 2 == 1);
        RectangleF rect = swapXY ? ShapeBase.rotate90Degrees(bounds) : bounds;
        final double ACCURACY = 1d; // Includes possible increase in the size of the shape due to the outline.

        msAssert.areEqual(pxX1, ACCURACY, ConvertUtil.pointToPixel(rect.getX()));
        msAssert.areEqual(pxY1, ACCURACY, ConvertUtil.pointToPixel(rect.getY()));
        msAssert.areEqual(pxX2, ACCURACY, ConvertUtil.pointToPixel(rect.getX() + rect.getWidth()));
        msAssert.areEqual(pxY2, ACCURACY, ConvertUtil.pointToPixel(rect.getY() + rect.getHeight()));
    }
}

