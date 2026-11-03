// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/03/2021 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import java.awt.Color;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Drawing.msColor;
import org.testng.annotations.DataProvider;


/// <summary>
/// The class for testing Solid() method of Fill object.
/// </summary>
@Test
public class TestFillSolid extends TestFillFormatBase
{
    /// <summary>
    /// Tests setting Solid for various Shape fills (DML and VML).
    /// </summary>
    @Test (dataProvider = "testShapeDataProvider")
    public void testShape(String testName, int r, int g, int b) throws Exception
    {
        Color expectedColor = new Color((r), (g), (b));

        // DML
        // Word VBA changes transparency in DML to fully opaque color.
        double expectedTransparency = 0.0;
        checkSetSolid(testName, ShapeMarkupLanguage.DML, expectedColor, expectedTransparency);
        // VML
        // Word VBA changes only type of the fill in VML (i.e. preserves original transparency and color)
        expectedTransparency = 0.37;
        checkSetSolid(testName, ShapeMarkupLanguage.VML, expectedColor, expectedTransparency);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testShapeDataProvider")
	public static Object[][] testShapeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient",  0x00,  0x80,  0x80},
			{"TwoColorGradient",  0x80,  0x00,  0x00},
			{"PresetGradient",  0xCC,  0xCC,  0xFF},
			{"Patterned",  0x80,  0x00,  0x00},
			{"PresetTextured",  0xFF,  0xFF,  0xFF},
			{"UserPicture",  0xFF,  0xFF,  0xFF},
			{"UserTextured",  0xFF,  0xFF,  0xFF},
		};
	}

    /// <summary>
    /// Tests setting Solid for a shape that has Solid fill already.
    /// </summary>
    @Test (dataProvider = "testShapeSolidDataProvider")
    public void testShapeSolid(String testName) throws Exception
    {
        // Word VBA does not change anything either in DML or VML when the fill has Solid fill already.
        Color expectedColor = new Color((0xFF), (0x00), (0x00));
        final double EXPECTED_TRANSPARENCY = 0.37;
        // DML
        checkSetSolid(testName, ShapeMarkupLanguage.DML, expectedColor, EXPECTED_TRANSPARENCY);
        // VML
        checkSetSolid(testName, ShapeMarkupLanguage.VML, expectedColor, EXPECTED_TRANSPARENCY);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testShapeSolidDataProvider")
	public static Object[][] testShapeSolidDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Solid"},
		};
	}

    /// <summary>
    /// Tests setting Solid for a DML shape that has NoFill fill.
    /// </summary>
    @Test (dataProvider = "testDmlShapeNoFillDataProvider")
    public void testDmlShapeNoFill(String testName) throws Exception
    {
        Document doc = open(testName, ShapeMarkupLanguage.DML);
        Shape shape = (Shape)getFirstFillable(doc);
        Fill fill = shape.getFill();

        fill.solid();

        // Word VBA does not change fill type for NoFill in DML
        msAssert.areEqual(DmlFillType.NO_FILL, ((DmlFill)shape.getFillCore()).getDmlFillType());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDmlShapeNoFillDataProvider")
	public static Object[][] testDmlShapeNoFillDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"NoFill"},
		};
	}

    /// <summary>
    /// Tests setting Solid for a VML shape that has NoFill fill.
    /// </summary>
    @Test (dataProvider = "testVmlShapeNoFillDataProvider")
    public void testVmlShapeNoFill(String testName) throws Exception
    {
        // Word VBA sets in VML fully opaque white color.
        checkSetSolid(testName, ShapeMarkupLanguage.VML, Color.WHITE, 0.0);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testVmlShapeNoFillDataProvider")
	public static Object[][] testVmlShapeNoFillDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"NoFill"},
		};
	}

    /// <summary>
    /// Tests setting Solid for a fill of DML Font object.
    /// </summary>
    @Test (dataProvider = "testDmlFontDataProvider")
    public void testDmlFont(String testName) throws Exception
    {
        // Word VBA for DML Font resets fill to fully opaque black color.
        checkSetSolid(testName, ShapeMarkupLanguage.DML, Color.BLACK, 0.0);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDmlFontDataProvider")
	public static Object[][] testDmlFontDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TextOneColorGradient"},
			{"TextTwoColorGradient"},
			{"TextPresetGradient"},
			{"TextNoFill"},
		};
	}

    /// <summary>
    /// Tests setting Solid for a fill of DML Font object that was Solid originally.
    /// </summary>
    @Test (dataProvider = "testDmlFontSolidDataProvider")
    public void testDmlFontSolid(String testName) throws Exception
    {
        // Word VBA does not change anything if the fill was Solid originally.
        checkSetSolid(testName, ShapeMarkupLanguage.DML, Color.RED, 0.39);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDmlFontSolidDataProvider")
	public static Object[][] testDmlFontSolidDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TextSolid"},
		};
	}

    /// <summary>
    /// Tests setting Solid for a fill of VML (document opened in compatibility mode) Font object.
    /// </summary>
    @Test (dataProvider = "testVmlFontDataProvider")
    public void testVmlFont(String testName) throws Exception
    {
        // Word VBA throws when call to Solid() for fill of VML Font object.
        // But we agreed to change the fill to DmlSolidFill implicitly.
        checkSetSolid(testName, ShapeMarkupLanguage.VML, Color.BLACK, 0.0);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testVmlFontDataProvider")
	public static Object[][] testVmlFontDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TextOneColorGradient"},
			{"TextTwoColorGradient"},
			{"TextPresetGradient"},
			{"TextNoFill"},
			{"TextSolid"},
		};
	}

    /// <summary>
    /// Tests setting Solid with a specified desired color to a Shape.
    /// </summary>
    @Test (dataProvider = "testShapeSolidWithColorDataProvider")
    public void testShapeSolidWithColor(String testName) throws Exception
    {
        // DML
        checkSetSolid(testName, ShapeMarkupLanguage.DML, msColor.getAqua(), msColor.getAqua(), 0.0);
        // VML
        checkSetSolid(testName, ShapeMarkupLanguage.VML, msColor.getAqua(), msColor.getAqua(), 0.37);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testShapeSolidWithColorDataProvider")
	public static Object[][] testShapeSolidWithColorDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient"},
			{"TwoColorGradient"},
			{"PresetGradient"},
			{"Patterned"},
			{"PresetTextured"},
			{"UserPicture"},
			{"UserTextured"},
		};
	}

    /// <summary>
    /// Tests setting Solid with a specified desired color to a Shape with Solid already.
    /// </summary>
    @Test (dataProvider = "testSolidShapeSetSolidWithColorDataProvider")
    public void testSolidShapeSetSolidWithColor(String testName) throws Exception
    {
        // DML
        // Note, Word VBA does not reset transparency to fully opaque, but we do it for a while in this case.
        checkSetSolid(testName, ShapeMarkupLanguage.DML, msColor.getAqua(), msColor.getAqua(), 0.0);
        // VML
        checkSetSolid(testName, ShapeMarkupLanguage.VML, msColor.getAqua(), msColor.getAqua(), 0.37);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSolidShapeSetSolidWithColorDataProvider")
	public static Object[][] testSolidShapeSetSolidWithColorDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Solid"},
		};
	}

    /// <summary>
    /// Tests setting Solid with a specified desired color to a Shape that has a NoFill fill.
    /// </summary>
    @Test (dataProvider = "testSolidWithColorVmlDataProvider")
    public void testSolidWithColorVml(String testName) throws Exception
    {
        // DML
        checkSetSolid(testName, ShapeMarkupLanguage.DML, msColor.getAqua(), msColor.getAqua(), 0.0);
        // VML
        checkSetSolid(testName, ShapeMarkupLanguage.VML, msColor.getAqua(), msColor.getAqua(), 0.0);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSolidWithColorVmlDataProvider")
	public static Object[][] testSolidWithColorVmlDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"NoFill"},
		};
	}

    /// <summary>
    /// Tests setting Solid with a specified desired color to a Font.
    /// </summary>
    @Test (dataProvider = "testFontSolidWithColorDataProvider")
    public void testFontSolidWithColor(String testName) throws Exception
    {
        // DML
        checkSetSolid(testName, ShapeMarkupLanguage.DML, msColor.getAqua(), msColor.getAqua(), 0.0);
        // VML
        checkSetSolid(testName, ShapeMarkupLanguage.VML, msColor.getAqua(), msColor.getAqua(), 0.0);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFontSolidWithColorDataProvider")
	public static Object[][] testFontSolidWithColorDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TextOneColorGradient"},
			{"TextTwoColorGradient"},
			{"TextPresetGradient"},
			{"TextSolid"},
			{"TextNoFill"},
		};
	}

    /// <summary>
    /// Checks setting Solid fill for a test file with a specified name.
    /// </summary>
    private static void checkSetSolid(String testName,
        /*ShapeMarkupLanguage*/byte markupLanguage, Color expectedColor, double expectedTransparency) throws Exception
    {
        Document doc = open(testName, markupLanguage);
        Fill fill = getFill(doc);

        fill.solid();

        checkSolidFill(fill, expectedColor, expectedTransparency);
    }

    /// <summary>
    /// Checks setting Solid fill for a test file with a specified name.
    /// </summary>
    private static void checkSetSolid(String testName, /*ShapeMarkupLanguage*/byte markupLanguage,
        Color desiredColor, Color expectedColor, double expectedTransparency) throws Exception
    {
        Document doc = open(testName, markupLanguage);
        Fill fill = getFill(doc);

        fill.solid(desiredColor);

        checkSolidFill(fill, expectedColor, expectedTransparency);
    }

    /// <summary>
    /// Checks a specified Solid fill.
    /// </summary>
    private static void checkSolidFill(Fill solidFill, Color expectedColor, double expectedTransparency)
    {
        msAssert.areEqual(FillType.SOLID, solidFill.getFillType());
        msAssert.areEqual(expectedTransparency, 0.01, solidFill.getTransparency());
        checkColor(expectedColor, solidFill.getForeColor());
    }
}

