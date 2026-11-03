// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Drawing.msPoint;
import com.aspose.ms.System.Drawing.msSize;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for shape geometry attributes.
/// </summary>
@Test
public class TestShapeGeometry extends UnifiedTestsBase
{
    /// <summary>
    /// Checks we can obtain standard shape type properties.
    /// </summary>
    @Test
    public void testShapeTypeGeometry()
    {
        // <v:shapetype id="_x0000_t94" coordsize="21600,21600" o:spt="94" 
        //  adj="16200,5400" 
        //  path="m@0,l @0@1, 0@1 @5,10800, 0@2 @0@2 @0,21600, 21600,10800xe">
        //  <v:stroke joinstyle="miter" />
        //  <v:formulas><v:f eqn="val #0" /><v:f eqn="val #1" /><v:f eqn="sum height 0 #1" />
        //      <v:f eqn="sum 10800 0 #1" /><v:f eqn="sum width 0 #0" /><v:f eqn="prod @4 @3 10800" />
        //      <v:f eqn="sum width 0 @5" />
        //  </v:formulas>
        //  <v:path o:connecttype="custom" o:connectlocs="@0,0;@5,10800;@0,21600;21600,10800" 
        //  o:connectangles="270,180,90,0" textboxrect="@5,@1,@6,@2" />
        //  <v:handles><v:h position="#0,#1" xrange="0,21600" yrange="0,10800" /></v:handles>
        // </v:shapetype>
        ShapePr shapePr = ShapeTypeLibrary.getShapeTypePr(ShapeType.NOTCHED_RIGHT_ARROW);

        // Just check some of the parsed points.
        PathPoint[] points = (PathPoint[])shapePr.get(ShapeAttr.GEOMETRY_VERTICES);
        msAssert.areEqual(8, points.length);
        msAssert.areEqual("X:@0, Y:0", points[0].toString());
        msAssert.areEqual("X:@0, Y:@1", points[1].toString());

        // Check some of the parsed path infos.
        PathInfo[] pathInfos = (PathInfo[])shapePr.get(ShapeAttr.GEOMETRY_SEGMENT_INFO);
        msAssert.areEqual(4, pathInfos.length);
        checkPathInfo(pathInfos[0], PathType.MOVE_TO, 0);
        checkPathInfo(pathInfos[1], PathType.LINE_TO, 7);
    }

    /// <summary>
    /// Check shape type definitions we obtain are cached and not created every time.
    /// </summary>
    @Test
    public void testShapeTypeCached()
    {
        ShapePr shapePrA = ShapeTypeLibrary.getShapeTypePr(ShapeType.UTURN_ARROW);
        ShapePr shapePrB = ShapeTypeLibrary.getShapeTypePr(ShapeType.UTURN_ARROW);
        msAssert.areEqual((Object)shapePrA, (Object)shapePrB); // Casting for C++.
    }

    /// <summary>
    /// Check shape types that do not have shape type definitions.
    /// </summary>
    @Test
    public void testShapeTypeMissing()
    {
        msAssert.areEqual(null, ShapeTypeLibrary.getShapeTypePr(ShapeType.CUSTOM_SHAPE));
    }

    /// <summary>
    /// This shape contains several bezier shapes and had a problem reading it in DOCX and WML.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestPathBezier(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Geometry\\TestPathBezier", lf, sf);

        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);

        // Check coord size.
        msAssert.areEqual(0, msPoint.getX(shape.getCoordOriginInternal()));
        msAssert.areEqual(0, msPoint.getY(shape.getCoordOriginInternal()));
        msAssert.areEqual(3840, msSize.getWidth(shape.getCoordSizeInternal()));
        msAssert.areEqual(3630, msSize.getHeight(shape.getCoordSizeInternal()));

        // Check the path vertices.
        PathPoint[] points = (PathPoint[])shape.fetchShapeAttrInternal(ShapeAttr.GEOMETRY_VERTICES);
        msAssert.areEqual(28, points.length);
        msAssert.areEqual(420, points[0].getX().getValue());
        msAssert.areEqual(3060, points[0].getY().getValue());
        // RK This is a start of a relative curve. The relative value is 300,-180, but it is translated
        // to this absolute value since the model, DOC and RTF do not support relative values in path.
        msAssert.areEqual(1080, points[7].getX().getValue());
        msAssert.areEqual(180, points[7].getY().getValue());

        // Check the path commands.
        PathInfo[] pathInfos = (PathInfo[])shape.fetchShapeAttrInternal(ShapeAttr.GEOMETRY_SEGMENT_INFO);
        switch (TestUtil.getUnifiedScenario(lf, sf))
        {
            case UnifiedScenario.DOC_2_DOC:
            case UnifiedScenario.DOC_2_DOCX:
            case UnifiedScenario.DOC_2_RTF:
            case UnifiedScenario.DOC_2_WML:
            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
            {
                // RK This is an array as read from DOC and RTF. 
                msAssert.areEqual(22, pathInfos.length);
                checkPathInfo(pathInfos[0], PathType.MOVE_TO, 0);
                checkPathInfo(pathInfos[1], PathType.ESCAPE_AUTO_CURVE, 0);
                checkPathInfo(pathInfos[2], PathType.CURVE_TO, 1);
                checkPathInfo(pathInfos[20], PathType.CLOSE, 1);
                checkPathInfo(pathInfos[21], PathType.END, 0);
                break;
            }
            case UnifiedScenario.DOCX_DML_2_DOCX_DML:
            case UnifiedScenario.DOCX_2_DOCX_NO_GOLD:
            case UnifiedScenario.WML_2_WML_NO_GOLD:
            {
                // RK This is an array as read from WML and DOCX. It is equivalent to the above, 
                // just MS Word seems to combine commands slightly differently.
                msAssert.areEqual(10, pathInfos.length);
                checkPathInfo(pathInfos[0], PathType.MOVE_TO, 0);
                checkPathInfo(pathInfos[1], PathType.CURVE_TO, 2);
                checkPathInfo(pathInfos[2], PathType.CURVE_TO, 1);
                checkPathInfo(pathInfos[8], PathType.CLOSE, 1);
                checkPathInfo(pathInfos[9], PathType.END, 0);
                break;
            }
            default:
                throw new IllegalStateException("Unknown file format.");
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestPathBezierSourceDataProvider")
	public void unifiedTestPathBezierSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestPathBezier(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestPathBezierSourceDataProvider")
	Object[][] unifiedTestPathBezierSourceDataProvider() throws Exception
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

    private static void checkPathInfo(PathInfo pathInfo, /*PathType*/int expectedPathType, int expectedSegments)
    {
        msAssert.areEqual(expectedPathType, pathInfo.getPathType());
        msAssert.areEqual(expectedSegments, pathInfo.getSegmentCount());
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestGeometry(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\Shape\\Geometry\\TestGeometry", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestGeometrySourceDataProvider")
	public void unifiedTestGeometrySource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestGeometry(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestGeometrySourceDataProvider")
	Object[][] unifiedTestGeometrySourceDataProvider() throws Exception
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
    /// This is a new shape inserted from ClipArt. In ClipArt is says format is WMF, but somehow 
    /// it gets converted by MS Word into a shape with geometry and adjustable handles.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestClipArt(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Geometry\\TestClipArt", lf, sf);

        // Check coordorigin and size of one of the group shapes. They used to be lost overridden
        // by geometry coordinate space.
        GroupShape groupShape = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 3, true);
        msAssert.areEqual(1824, msPoint.getX(groupShape.getCoordOriginInternal()));
        msAssert.areEqual(633, msPoint.getY(groupShape.getCoordOriginInternal()));
        msAssert.areEqual(2834, msSize.getWidth(groupShape.getCoordSizeInternal()));
        msAssert.areEqual(2849, msSize.getHeight(groupShape.getCoordSizeInternal()));

        // Check the tree shape.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 6, true);
        msAssert.areEqual("Tree", shape.getName());
        msAssert.areEqual(ShapeType.CUSTOM_SHAPE, shape.getShapeType());
        // It is important that this attribute is not missing for custom shapes, otherwise
        // MS Word does not draw such shapes when loaded from RTF.
        PathInfo[] pathInfos = (PathInfo[])shape.getShapePr().getDirectAttr(ShapeAttr.GEOMETRY_SEGMENT_INFO);
        msAssert.isNotNull(pathInfos);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestClipArtSourceDataProvider")
	public void unifiedTestClipArtSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestClipArt(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestClipArtSourceDataProvider")
	Object[][] unifiedTestClipArtSourceDataProvider() throws Exception
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
    /// Arcsize for roundrect in DOC and RTF store arcsize as adj1, but VML stores as arcsize.
    /// In the model we store as adj1 and convert VML arcsize to adj1.
    /// In VML arcsize is 16.16 percentage value. In the model adj1 is in geometry units.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestRoundRect(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Geometry\\TestRoundRect", lf, sf);

        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(5513, shape.getAdjust(1));

        shape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        msAssert.areEqual(0, shape.getAdjust(1));
        
        shape = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        msAssert.areEqual(10800, shape.getAdjust(1));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRoundRectSourceDataProvider")
	public void unifiedTestRoundRectSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRoundRect(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRoundRectSourceDataProvider")
	Object[][] unifiedTestRoundRectSourceDataProvider() throws Exception
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
    /// WORDSNET-13556 Exception is thrown in VmlShapeReader while reading VML shape
    /// Get shape properties from template when type is missing.
    /// </summary>
    @Test
    public void testJira13556() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Geometry\\TestJira13556.xml");

        ShapePr shapePr = doc.getFirstSection().getBody().getShapes().get(0).getShapePr();
        msAssert.isNotNull(shapePr.get(ShapeAttr.GEOMETRY_VERTICES));
        msAssert.isNotNull(shapePr.get(ShapeAttr.GEOMETRY_SEGMENT_INFO));
    }

    /// <summary>
    /// WORDSNET-16036 Coordinate space (the coordsize attribute) of a VML shape was not written,
    /// if only width or only height is defined directly.
    /// </summary>
    @Test
    public void testJira16036() throws Exception
    {
        // FOSS: Doc reader removed; input converted to .docx.
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Geometry\\TestJira16036",
            UnifiedScenario.DOCX_2_DOCX | UnifiedScenario.NO_GOLD);
        Shape shape = doc.getFirstSection().getBody().getShapes().get(1);
        msAssert.areEqual(25519, shape.getCoordSizeWidth());
    }

    // FOSS: Test21857A / Test21857B removed. Their source was a Word 2003 WordML (.wml) file with a
    // shape whose width/height were absent, which the WML reader marked with the InvalidShapeSizeDefault
    // (50.25) sentinel. The WML reader is removed in FOSS; the Word .wml->.docx conversion writes an
    // explicit size for the missing dimension, so the sentinel condition can't be reproduced.
}

