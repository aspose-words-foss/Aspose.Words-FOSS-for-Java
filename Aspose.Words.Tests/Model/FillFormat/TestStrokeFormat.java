// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/11/2023 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import org.testng.annotations.DataProvider;



/// <summary>
/// The class for testing various properties of the Stroke (VBA LineFormat) object.
/// </summary>
@Test
public class TestStrokeFormat extends TestFillFormatBase
{
    /// <summary>
    /// Tests getting and setting theme colors for different fill types.
    /// Dml case.
    /// </summary>
    @Test (dataProvider = "testStrokeThemeColorDmlDataProvider")
    public void testStrokeThemeColorDml(String fileName, boolean backColorSupported) throws Exception
    {
        Document doc = open(fileName, ShapeMarkupLanguage.DML);
        Stroke stroke = doc.getFirstSection().getBody().getShapes().get(0).getStroke();
        msAssert.areEqual(ThemeColor.NONE, stroke.getForeThemeColor());
        msAssert.areEqual(ThemeColor.NONE, stroke.getBackThemeColor());
        stroke.setForeThemeColor(ThemeColor.ACCENT_3);
        stroke.setBackThemeColor(ThemeColor.ACCENT_2);

        doc = TestUtil.saveOpen(doc, "Model\\FillFormat\\TestStrokeThemeColorDml", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        stroke = doc.getFirstSection().getBody().getShapes().get(0).getStroke();
        msAssert.areEqual(ThemeColor.ACCENT_3, stroke.getForeThemeColor());
        if (backColorSupported)
            msAssert.areEqual(ThemeColor.ACCENT_2, stroke.getBackThemeColor());
        stroke.setForeThemeColor(ThemeColor.NONE);
        stroke.setBackThemeColor(ThemeColor.NONE);

        doc = TestUtil.saveOpen(doc, "Model\\FillFormat\\TestStrokeThemeColorDml", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        stroke = doc.getFirstSection().getBody().getShapes().get(0).getStroke();
        msAssert.areEqual(ThemeColor.NONE, stroke.getForeThemeColor());
        msAssert.areEqual(new Color((165), (165), (165), (255)), stroke.getForeColor());
        msAssert.areEqual(ThemeColor.NONE, stroke.getBackThemeColor());
        if (backColorSupported)
            msAssert.areEqual(new Color((237), (125), (49), (255)), stroke.getBackColor());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testStrokeThemeColorDmlDataProvider")
	public static Object[][] testStrokeThemeColorDmlDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"SolidOutline",  false},
			{"PatternedOutline",  true},
			{"Gradient1Outline",  true},
			{"Gradient2Outline",  true},
		};
	}

    /// <summary>
    /// Tests getting and setting Tint and Shade for different fill types.
    /// Dml case.
    /// </summary>
    @Test (dataProvider = "testStrokeTintAndShadeDmlDataProvider")
    public void testStrokeTintAndShadeDml(String fileName, boolean backModifierSupported) throws Exception
    {
        Document doc = open(fileName, ShapeMarkupLanguage.DML);
        Stroke stroke = doc.getFirstSection().getBody().getShapes().get(0).getStroke();
        msAssert.areEqual(0, stroke.getForeTintAndShade());
        msAssert.areEqual(0, stroke.getBackTintAndShade());
        stroke.setForeTintAndShade(0.6);
        stroke.setBackTintAndShade(0.3);

        doc = TestUtil.saveOpen(doc, "Model\\FillFormat\\TestStrokeTintAndShadeDml", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        stroke = doc.getFirstSection().getBody().getShapes().get(0).getStroke();
        msAssert.areEqual(0.6, 0.01, stroke.getForeTintAndShade());
        if (backModifierSupported)
            msAssert.areEqual(0.3, 0.01, stroke.getBackTintAndShade());
        stroke.setForeTintAndShade(-0.3);
        stroke.setBackTintAndShade(-0.6);

        doc = TestUtil.saveOpen(doc, "Model\\FillFormat\\TestStrokeTintAndShadeDml", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        stroke = doc.getFirstSection().getBody().getShapes().get(0).getStroke();
        msAssert.areEqual(-0.3, 0.01, stroke.getForeTintAndShade());
        if (backModifierSupported)
            msAssert.areEqual(-0.6, 0.01, stroke.getBackTintAndShade());
        stroke.setForeTintAndShade(0.0);
        stroke.setBackTintAndShade(0.0);

        doc = TestUtil.saveOpen(doc, "Model\\FillFormat\\TestStrokeTintAndShadeDml", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        stroke = doc.getFirstSection().getBody().getShapes().get(0).getStroke();
        msAssert.areEqual(0, stroke.getForeTintAndShade());
        msAssert.areEqual(0, stroke.getBackTintAndShade());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testStrokeTintAndShadeDmlDataProvider")
	public static Object[][] testStrokeTintAndShadeDmlDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"SolidOutline",  false},
			{"PatternedOutline",  true},
			{"Gradient1Outline",  true},
			{"Gradient2Outline",  true},
		};
	}

    /// <summary>
    /// Tests that exception is thrown when applying value to ForeTintAndShade out of [-1, 1] range.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, dataProvider = "testForeTintAndShadeOutOfRangeDataProvider")
    public void testForeTintAndShadeOutOfRange(/*ShapeMarkupLanguage*/byte shapeMarkupLanguage, double tintAndShade) throws Exception
    {
        Document doc = open("TintShadeOutline", shapeMarkupLanguage);

        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        // Expect ArgumentOutOfRangeException here.
        shape.getStroke().setForeTintAndShade(tintAndShade);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testForeTintAndShadeOutOfRangeDataProvider")
	public static Object[][] testForeTintAndShadeOutOfRangeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ShapeMarkupLanguage.DML,  -1.5},
			{ShapeMarkupLanguage.DML,  1.5},
		};
	}

    /// <summary>
    /// Tests that exception is thrown when applying value to BackTintAndShade out of [-1, 1] range.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, dataProvider = "testBackTintAndShadeOutOfRangeDataProvider")
    public void testBackTintAndShadeOutOfRange(/*ShapeMarkupLanguage*/byte shapeMarkupLanguage, double tintAndShade) throws Exception
    {
        Document doc = open("TintShadeOutline", shapeMarkupLanguage);

        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        // Expect ArgumentOutOfRangeException here.
        shape.getStroke().setBackTintAndShade(tintAndShade);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testBackTintAndShadeOutOfRangeDataProvider")
	public static Object[][] testBackTintAndShadeOutOfRangeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ShapeMarkupLanguage.DML,  -1.5},
			{ShapeMarkupLanguage.DML,  1.5},
		};
	}
}

