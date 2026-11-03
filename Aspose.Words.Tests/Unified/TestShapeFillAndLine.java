// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for shapes fill and line properties.
/// </summary>
@Test
public class TestShapeFillAndLine extends UnifiedTestsBase
{
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestFillStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Fill\\TestFillStyle", lf, sf);

        // Default fill
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(true, shape.getFilled());
        msAssert.areEqual(Color.WHITE.getRGB(), shape.getFillColor().getRGB());
        msAssert.areEqual(true, shape.getFill().getVisible());
        msAssert.areEqual(Color.WHITE.getRGB(), shape.getFill().getForeColor().getRGB());
        msAssert.areEqual(Color.WHITE.getRGB(), shape.getFill().getBackColor().getRGB());
        msAssert.areEqual(1.0, shape.getFill().getOpacity());
        msAssert.areEqual(1.0, shape.getFillCore().getOpacity2());
        msAssert.areEqual(FillType.SOLID, shape.getFill().getFillType());
        msAssert.areEqual(0, shape.getFillCore().getFocus());
        msAssert.areEqual(0.0, shape.getFillCore().getAngle());
        msAssert.areEqual(0.0, shape.getFillCore().getFocusLeft());
        msAssert.areEqual(0.0, shape.getFillCore().getFocusTop());
        msAssert.areEqual(null, shape.getFillCore().getGradientColors());

        // Red transparent fill
        shape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        msAssert.areEqual(Color.RED.getRGB(), shape.getFillColor().getRGB());
        msAssert.areEqual(0.6, 0.01, shape.getFill().getOpacity());

        // No fill
        shape = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        msAssert.areEqual(false, shape.getFilled());
        msAssert.areEqual(Color.WHITE.getRGB(), shape.getFillColor().getRGB());
        msAssert.areEqual(FillType.SOLID, shape.getFill().getFillType());

        // One color horizontal gradient.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 3, true);
        // TODO 2 No idea how to represent different gradient types in enum.
        msAssert.areEqual(FillTypeCore.SHADE_SCALE, shape.getFillCore().getFillType());
        msAssert.areEqual(100, shape.getFillCore().getFocus());
        msAssert.areEqual(Color.RED.getRGB(), shape.getFill().getForeColor().getRGB());
        // TODO 2 No idea what this color means. In WordML: "fill darken(51)". 0x33 means 51, 0x00100 means darken.
        msAssert.areEqual(/*unchecked*/((int)(int) 0xeff00133L), shape.getFillCore().getColor2Internal().toNativeColor().getRGB());

        // One color vertical gradient. Different darkness and focus. In MS Word focus is called "variant".
        shape = (Shape)doc.getChild(NodeType.SHAPE, 4, true);
        msAssert.areEqual(FillTypeCore.SHADE_SCALE, shape.getFillCore().getFillType());
        msAssert.areEqual(50, shape.getFillCore().getFocus());
        msAssert.areEqual(Color.RED.getRGB(), shape.getFill().getForeColor().getRGB());
        // TODO 2 No idea what this color means. In WordML: "fill lighten(102)". 0x66 means 102, 0x00200 means lighten.
        msAssert.areEqual(/*unchecked*/((int)(int) 0xeff00266L), shape.getFillCore().getColor2Internal().toNativeColor().getRGB());
        msAssert.areEqual(-90.0, shape.getFillCore().getAngle());

        // One color gradient from corner.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 5, true);
        msAssert.areEqual(FillTypeCore.SHADE_CENTER, shape.getFillCore().getFillType());
        msAssert.areEqual(0.0, shape.getFillCore().getFocusLeft());
        msAssert.areEqual(1.0, shape.getFillCore().getFocusTop());

        // One color gradient from center. Also a range of transparency.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 6, true);
        msAssert.areEqual(FillTypeCore.SHADE_SHAPE, shape.getFillCore().getFillType());
        msAssert.areEqual(0.5, shape.getFillCore().getFocusLeft());
        msAssert.areEqual(0.5, shape.getFillCore().getFocusTop());
        msAssert.areEqual(0.9, 0.01, shape.getFill().getOpacity());
        msAssert.areEqual(0.3, 0.01, shape.getFillCore().getOpacity2());

        // Two color diagonal gradient.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 8, true);
        msAssert.areEqual(FillTypeCore.SHADE_SCALE, shape.getFillCore().getFillType());
        msAssert.areEqual(Color.RED.getRGB(), shape.getFill().getColor().getRGB());
        msAssert.areEqual(Color.YELLOW.getRGB(), shape.getFillCore().getColor2Internal().toNativeColor().getRGB());

        // Rainbow predefined gradient. Fill rotated with the shape.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 9, true);
        msAssert.areEqual(FillTypeCore.SHADE_SCALE, shape.getFillCore().getFillType());
        GradientColor[] colors = shape.getFillCore().getGradientColors();
        msAssert.areEqual(7, colors.length);
        msAssert.areEqual(/*unchecked*/((int)(int) 0xff0819fbL), colors[1].Color.toArgb());
        msAssert.areEqual(13763, colors[1].Start);

        // Texture fill.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 11, true);
        msAssert.areEqual(FillType.TEXTURED, shape.getFill().getFillType());
        msAssert.isNotNull(shape.getFill().getImageBytes());
        // This is PresetTexture.FishFossil, but we only store this attribute in DOC and RTF.

        // Pattern fill.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 12, true);
        msAssert.areEqual(FillType.PATTERNED, shape.getFill().getFillType());
        // This is PresetTexture.HorizontalBrick, but we only store this attribute in DOC and RTF.

        // Picture fill.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 13, true);
        msAssert.areEqual(FillType.PICTURE, shape.getFill().getFillType());
        // This is PresetTexture.Custom.
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFillStyleSourceDataProvider")
	public void unifiedTestFillStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFillStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFillStyleSourceDataProvider")
	Object[][] unifiedTestFillStyleSourceDataProvider() throws Exception
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
    void unifiedTestFillPattern(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Fill\\TestFillPattern", lf, sf);

        // WORDSNET-1118 Pattern or texture fills are not saved to DOC.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.isNotNull(shape.getFill().getImageBytes());
        // This is PresetTexture.Newspint, but we only store this attribute in DOC and RTF.
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFillPatternSourceDataProvider")
	public void unifiedTestFillPatternSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFillPattern(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFillPatternSourceDataProvider")
	Object[][] unifiedTestFillPatternSourceDataProvider() throws Exception
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
    void unifiedTestLineStyle(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Stroke\\TestLineStyle", lf, sf);

        // Default line
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(true, shape.getStroked());
        msAssert.areEqual(Color.BLACK.getRGB(), shape.getStrokeColor().getRGB());
        msAssert.areEqual(0.75, shape.getStrokeWeight());
        msAssert.areEqual(ShapeLineStyle.SINGLE, shape.getStroke().getLineStyle());
        msAssert.areEqual(LineFillType.SOLID, shape.getStroke().getLineFillType());
        msAssert.areEqual(1.0, shape.getStroke().getOpacity());
        msAssert.areEqual(DashStyle.SOLID, shape.getStroke().getDashStyle());
        msAssert.areEqual(ArrowType.NONE, shape.getStroke().getStartArrowType());
        msAssert.areEqual(ArrowType.NONE, shape.getStroke().getEndArrowType());
        msAssert.areEqual(ArrowLength.MEDIUM, shape.getStroke().getStartArrowLength());
        msAssert.areEqual(ArrowWidth.MEDIUM, shape.getStroke().getStartArrowWidth());
        msAssert.areEqual(ArrowLength.MEDIUM, shape.getStroke().getEndArrowLength());
        msAssert.areEqual(ArrowWidth.MEDIUM, shape.getStroke().getEndArrowWidth());
        msAssert.areEqual(JoinStyle.MITER, shape.getStroke().getJoinStyle());
        msAssert.isNull(shape.getStroke().getImageBytes());

        // Invisible line
        shape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        msAssert.areEqual(false, shape.getStroked());
        msAssert.areEqual(Color.BLACK.getRGB(), shape.getStrokeColor().getRGB());
        msAssert.areEqual(0.75, shape.getStrokeWeight());
        msAssert.areEqual(ShapeLineStyle.SINGLE, shape.getStroke().getLineStyle());

        // Thick red
        shape = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        msAssert.areEqual(Color.RED.getRGB(), shape.getStrokeColor().getRGB());
        msAssert.areEqual(6.0, shape.getStrokeWeight());

        // Textured
        shape = (Shape)doc.getChild(NodeType.SHAPE, 3, true);
        msAssert.areEqual(LineFillType.PATTERN, shape.getStroke().getLineFillType());
        msAssert.areEqual(/*unchecked*/((int)(int) 0xff800000L), shape.getStroke().getColor().getRGB());
        msAssert.areEqual(Color.YELLOW.getRGB(), shape.getStroke().getColor2().getRGB());
        msAssert.isNotNull(shape.getStroke().getImageBytes());
        // This is PresetTexture.Weave, but we only store this attribute in DOC and RTF.

        // Transparent
        shape = (Shape)doc.getChild(NodeType.SHAPE, 4, true);
        msAssert.areEqual(0.32, 0.01, shape.getStroke().getOpacity());

        // Dotted
        shape = (Shape)doc.getChild(NodeType.SHAPE, 5, true);
        msAssert.areEqual(DashStyle.SHORT_DOT, shape.getStroke().getDashStyle());

        // Double
        shape = (Shape)doc.getChild(NodeType.SHAPE, 12, true);
        msAssert.areEqual(ShapeLineStyle.DOUBLE, shape.getStroke().getLineStyle());

        // Arrow
        shape = (Shape)doc.getChild(NodeType.SHAPE, 19, true);
        msAssert.areEqual(ArrowType.ARROW, shape.getStroke().getStartArrowType());
        msAssert.areEqual(ArrowType.OPEN, shape.getStroke().getEndArrowType());
        msAssert.areEqual(ArrowLength.LONG, shape.getStroke().getStartArrowLength());
        msAssert.areEqual(ArrowWidth.WIDE, shape.getStroke().getStartArrowWidth());
        msAssert.areEqual(ArrowLength.MEDIUM, shape.getStroke().getEndArrowLength());
        msAssert.areEqual(ArrowWidth.MEDIUM, shape.getStroke().getEndArrowWidth());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLineStyleSourceDataProvider")
	public void unifiedTestLineStyleSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLineStyle(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLineStyleSourceDataProvider")
	Object[][] unifiedTestLineStyleSourceDataProvider() throws Exception
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
    void unifiedTestLinePattern(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Stroke\\TestLinePattern", lf, sf);

        // WORDSNET-1118 Pattern or texture fills are not saved to DOC.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.isNotNull(shape.getStroke().getImageBytes());
        // This is PresetTexture.HorizontalBrick, but we only store this attribute in DOC and RTF.
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestLinePatternSourceDataProvider")
	public void unifiedTestLinePatternSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestLinePattern(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestLinePatternSourceDataProvider")
	Object[][] unifiedTestLinePatternSourceDataProvider() throws Exception
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
    /// Test horizontal line. This is inserted in MS Word in Format / Borders and Shading dialog box.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestHR(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Geometry\\TestHR", lf, sf);

        // HR with image
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(ShapeType.IMAGE, shape.getShapeType());
        msAssert.areEqual(true, shape.isInline());
        msAssert.areEqual(396, shape.getWidth());
        msAssert.areEqual(22.5, shape.getHeight());
        msAssert.areEqual(true, shape.isHorizontalRule());
        msAssert.areEqual(true, shape.getHorizontalRule().getOn());
        msAssert.areEqual(0, shape.getHorizontalRule().getPercent());
        msAssert.areEqual(HorizontalRuleAlignment.CENTER, shape.getHorizontalRule().getAlign());
        msAssert.areEqual(false, shape.getHorizontalRule().getStandard());

        // Standard HR
        shape = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        msAssert.areEqual(ShapeType.RECTANGLE, shape.getShapeType());
        msAssert.areEqual(true, shape.isInline());

        switch (lf)
        {
            case LoadFormat.DOC:
            case LoadFormat.RTF:
                msAssert.areEqual(432, shape.getWidth());
                break;
            case LoadFormat.WORD_ML:
            case LoadFormat.DOCX:
                // RK For DOCX and WML MS Word writes 0 here.
                // Maybe it means that shape width should equal to page width.
                msAssert.areEqual(0, shape.getWidth());
                break;
            default:
                throw new IllegalStateException("Unknown file format.");
        }

        msAssert.areEqual(1.5, shape.getHeight());
        msAssert.areEqual(true, shape.isHorizontalRule());
        msAssert.areEqual(true, shape.getHorizontalRule().getOn());
        msAssert.areEqual(100, shape.getHorizontalRule().getPercent());
        msAssert.areEqual(false, shape.getHorizontalRule().getNoShade());
        msAssert.areEqual(true, shape.getHorizontalRule().getStandard());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestHRSourceDataProvider")
	public void unifiedTestHRSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestHR(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestHRSourceDataProvider")
	Object[][] unifiedTestHRSourceDataProvider() throws Exception
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

