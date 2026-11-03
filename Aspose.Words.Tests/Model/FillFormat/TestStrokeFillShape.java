// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2023 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.msString;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;
import org.testng.annotations.DataProvider;


/// <summary>
/// The class for testing stroke fill of the Shape.
/// </summary>
@Test
public class TestStrokeFillShape extends TestFillFormatBase
{
    /// <summary>
    /// Tests Solid fill.
    /// </summary>
    @Test (dataProvider = "testSolidDataProvider")
    public void testSolid(String testName) throws Exception
    {
        Document doc = TestUtil.open(msString.format("{0}{1}", TEST_DIR, testName));
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);

        Fill fill = shape.getStroke().getFill();

        msAssert.areEqual(FillType.SOLID, fill.getFillType());
        msAssert.isTrue(fill.getVisible());
        msAssert.areEqual(0.93, 0.01, fill.getOpacity());

        checkColor(new Color((0xFF), (0x00), (0x00)), fill.getForeColor());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSolidDataProvider")
	public static Object[][] testSolidDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TestSolidStrokeFill.docx"},
		};
	}

    /// <summary>
    /// Tests Gradient fill (DML).
    /// </summary>
    @Test
    public void testGradientDml() throws Exception
    {
        Document doc = TestUtil.open(msString.format("{0}TestGradientStrokeFill.docx", TEST_DIR));
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);

        Fill fill = shape.getStroke().getFill();

        msAssert.areEqual(FillType.GRADIENT, fill.getFillType());
        msAssert.isTrue(fill.getVisible());
        msAssert.areEqual(0.93, 0.01, fill.getOpacity());

        GradientStopCollection gradients = fill.getGradientStops();

        msAssert.areEqual(5, gradients.getCount());
        checkGradientStop(gradients.get(0), new Color((0xFF), (0x99), (0x00)), 0.37, 0.07);
        checkGradientStop(gradients.get(1), new Color((0xFF), (0xFF), (0xFF)), 0.0, 0.0);
        checkGradientStop(gradients.get(2), new Color((0x99), (0xCC), (0xFF)), 0.74, 0.0);
        checkGradientStop(gradients.get(3), new Color((0x00), (0x00), (0xFF)), 0.83, 0.37);
        checkGradientStop(gradients.get(4), new Color((0xFF), (0xFF), (0x00)), 1.0, 0.00);
    }

    /// <summary>
    /// Tests NoFill.
    /// </summary>
    @Test (dataProvider = "testNoFillDataProvider") // DML
    public void testNoFill(String testName) throws Exception
    {
        Document doc = TestUtil.open(msString.format("{0}{1}", TEST_DIR, testName));
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);

        Fill fill = shape.getStroke().getFill();

        msAssert.areEqual(FillType.SOLID, fill.getFillType());
        msAssert.isFalse(fill.getVisible());

        checkColor(msColor.Empty, fill.getForeColor());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testNoFillDataProvider")
	public static Object[][] testNoFillDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TestNoFillStroke.docx"},
		};
	}

    private static final String TEST_DIR = "Model\\Shape\\Stroke\\Fill\\";
}

