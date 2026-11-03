// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/02/2023 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


/// <summary>
/// The class for testing ForeTintAndShade and BackTintAndShade of Fill object.
/// </summary>
@Test
public class TestFillTintAndShade extends TestFillFormatBase
{
    /// <summary>
    /// Tests getting and setting TintAndShade for both the theme and RGB colors.
    /// Dml case.
    /// </summary>
    @Test
    public void testTintAndShadeDml() throws Exception
    {
        Document doc = open("TintAndShade", ShapeMarkupLanguage.DML);

        Shape shapeSimple = doc.getFirstSection().getBody().getShapes().get(0);
        Shape shapeTheme = doc.getFirstSection().getBody().getShapes().get(1);

        msAssert.areEqual(0.6, 0.01, shapeSimple.getFill().getForeTintAndShade());
        msAssert.areEqual(0.6, 0.01, shapeSimple.getFill().getBackTintAndShade());
        msAssert.areEqual(0.6, 0.01, shapeTheme.getFill().getForeTintAndShade());
        msAssert.areEqual(0.6, 0.01, shapeTheme.getFill().getBackTintAndShade());

        checkTintAndShadeDml(doc, 0.8);
        checkTintAndShadeDml(doc, -0.6);
        checkTintAndShadeDml(doc, 1.0);
        checkTintAndShadeDml(doc, -1);
        checkTintAndShadeDml(doc, 0.0);
    }

    /// <summary>
    /// Tests getting and setting TintAndShade for different fill types.
    /// </summary>
    @Test (dataProvider = "testForeTintAndShadeFillTypesDataProvider")
    public void testForeTintAndShadeFillTypes(String fileName, /*ShapeMarkupLanguage*/byte markupLanguage) throws Exception
    {
        Document doc = open(fileName, markupLanguage);

        Fill fill = (fileName.contains("Text"))
            ? doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getFont().getFill()
            : doc.getFirstSection().getBody().getShapes().get(0).getFill();

        if (fileName.contains("Text"))
            fill.setForeThemeColor(ThemeColor.ACCENT_1);

        msAssert.areEqual(0, fill.getForeTintAndShade());
        // Checks Tint.
        fill.setForeTintAndShade(0.6);
        msAssert.areEqual(0.6, fill.getForeTintAndShade());
        // Checks Shade.
        fill.setForeTintAndShade(-0.6);
        msAssert.areEqual(-0.6, fill.getForeTintAndShade());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testForeTintAndShadeFillTypesDataProvider")
	public static Object[][] testForeTintAndShadeFillTypesDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient",  ShapeMarkupLanguage.VML},
			{"TwoColorGradient",  ShapeMarkupLanguage.VML},
			{"Patterned",  ShapeMarkupLanguage.VML},
			{"Solid",  ShapeMarkupLanguage.VML},
			{"TextSolid",  ShapeMarkupLanguage.VML},
			{"OneColorGradient",  ShapeMarkupLanguage.DML},
			{"TwoColorGradient",  ShapeMarkupLanguage.DML},
			{"Patterned",  ShapeMarkupLanguage.DML},
			{"Solid",  ShapeMarkupLanguage.DML},
			{"TextSolid",  ShapeMarkupLanguage.DML},
		};
	}

    /// <summary>
    /// Tests that exception is thrown when applying value to TintAndShade out of [-1, 1] range.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, dataProvider = "testForeTintAndShadeOutOfRangeDataProvider")
    public void testForeTintAndShadeOutOfRange(/*ShapeMarkupLanguage*/byte shapeMarkupLanguage, double tintAndShade) throws Exception
    {
        Document doc = open("ThemeColors", shapeMarkupLanguage);

        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        // Expect ArgumentOutOfRangeException here.
        shape.getFill().setForeTintAndShade(tintAndShade);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testForeTintAndShadeOutOfRangeDataProvider")
	public static Object[][] testForeTintAndShadeOutOfRangeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ShapeMarkupLanguage.DML,  -1.5},
			{ShapeMarkupLanguage.DML,  1.5},
			{ShapeMarkupLanguage.VML,  -1.5},
			{ShapeMarkupLanguage.VML,  1.5},
		};
	}

    /// <summary>
    /// Tests throwing an exception when trying to set ForeTintAndShade value for certain Fill types.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QCannot apply ForeTintAndShade to this Fill.\\E", dataProvider = "testForeTintAndShadeUnsupportedFillTypesDataProvider")
    public void testForeTintAndShadeUnsupportedFillTypes(String fileName, /*ShapeMarkupLanguage*/byte shapeMarkupLanguage) throws Exception
    {
        Document doc = open(fileName, shapeMarkupLanguage);

        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        msAssert.areEqual(0, shape.getFill().getForeTintAndShade());
        // Expect InvalidOperationException here.
        shape.getFill().setForeTintAndShade(0.5);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testForeTintAndShadeUnsupportedFillTypesDataProvider")
	public static Object[][] testForeTintAndShadeUnsupportedFillTypesDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"NoFill",  ShapeMarkupLanguage.DML},
			{"NoFill",  ShapeMarkupLanguage.VML},
			{"UserPicture",  ShapeMarkupLanguage.DML},
			{"UserPicture",  ShapeMarkupLanguage.VML},
		};
	}

    /// <summary>
    /// Tests throwing an exception when trying to set BackTintAndShade value for certain Fill types.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QCannot apply BackTintAndShade to this Fill.\\E", dataProvider = "testBackTintAndShadeUnsupportedFillTypesDataProvider")
    public void testBackTintAndShadeUnsupportedFillTypes(String fileName, /*ShapeMarkupLanguage*/byte shapeMarkupLanguage) throws Exception
    {
        Document doc = open(fileName, shapeMarkupLanguage);

        Fill fill = (fileName.contains("Text"))
            ? doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getFont().getFill()
            : doc.getFirstSection().getBody().getShapes().get(0).getFill();

        msAssert.areEqual(0.0d, fill.getBackTintAndShade());

        // Expect InvalidOperationException here.
        fill.setBackTintAndShade(0.5);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testBackTintAndShadeUnsupportedFillTypesDataProvider")
	public static Object[][] testBackTintAndShadeUnsupportedFillTypesDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"NoFill",  ShapeMarkupLanguage.DML},
			{"NoFill",  ShapeMarkupLanguage.VML},
			{"UserPicture",  ShapeMarkupLanguage.DML},
			{"UserPicture",  ShapeMarkupLanguage.VML},
			{"TextSolid",  ShapeMarkupLanguage.DML},
			{"TextSolid",  ShapeMarkupLanguage.VML},
		};
	}

    private void checkTintAndShadeVml(Document doc, String fileName, /*UnifiedScenario*/int scenario,
        double expTintAndShade) throws Exception
    {
        Fill fill = doc.getFirstSection().getBody().getShapes().get(0).getFill();

        fill.setForeTintAndShade(expTintAndShade);
        fill.setBackTintAndShade(expTintAndShade);

        doc = TestUtil.saveOpen(doc, fileName, scenario);

        // MS Word does not write zero modifiers (TintAndShade == 1) to the output Wml file.
        if (scenario != UnifiedScenario.WML_2_WML_NO_GOLD || !((expTintAndShade) == (1.0)))
        {
            fill = doc.getFirstSection().getBody().getShapes().get(0).getFill();

            msAssert.areEqual(expTintAndShade, 0.01, fill.getForeTintAndShade());
            msAssert.areEqual(expTintAndShade, 0.01, fill.getBackTintAndShade());
        }
    }

    private void checkTintAndShadeDml(Document doc, double expTintAndShade) throws Exception
    {
        Shape shapeSimple = doc.getFirstSection().getBody().getShapes().get(0);
        Shape shapeTheme = doc.getFirstSection().getBody().getShapes().get(1);

        shapeSimple.getFill().setForeTintAndShade(expTintAndShade);
        shapeSimple.getFill().setBackTintAndShade(expTintAndShade);
        shapeTheme.getFill().setForeTintAndShade(expTintAndShade);
        shapeTheme.getFill().setBackTintAndShade(expTintAndShade);

        doc = TestUtil.saveOpen(doc, "Model\\FillFormat\\TintAndShade", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        shapeSimple = doc.getFirstSection().getBody().getShapes().get(0);
        shapeTheme = doc.getFirstSection().getBody().getShapes().get(1);

        msAssert.areEqual(expTintAndShade, 0.01, shapeSimple.getFill().getForeTintAndShade());
        msAssert.areEqual(expTintAndShade, 0.01, shapeSimple.getFill().getBackTintAndShade());
        msAssert.areEqual(expTintAndShade, 0.01, shapeTheme.getFill().getForeTintAndShade());
        msAssert.areEqual(expTintAndShade, 0.01, shapeTheme.getFill().getBackTintAndShade());

        // Checks whether DmlShade and DmlTint modifiers are removed.
        if (expTintAndShade == 0)
        {
            msAssert.areEqual(0, ((DmlFill)shapeSimple.getFillCore()).getDmlColorInternal().getColorModifiers().size());
            msAssert.areEqual(0, ((DmlFill)shapeTheme.getFillCore()).getDmlColorInternal().getColorModifiers().size());
        }
    }
}

