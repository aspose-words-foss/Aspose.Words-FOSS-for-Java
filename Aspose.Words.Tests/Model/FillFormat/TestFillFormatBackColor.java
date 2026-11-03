// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/12/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Drawing.msColor;
import java.awt.Color;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.msNumber;
import com.aspose.ms.System.Globalization.msCultureInfo;
import org.testng.annotations.DataProvider;


/// <summary>
/// The class for testing BackColor property of the FillFormat object.
/// </summary>
@Test
public class TestFillFormatBackColor extends TestFillFormatBase
{
    /// <summary>
    /// Tests BackColor of the various one-colored Font fills.
    /// </summary>
    /// <remarks> This is test for the fills that cannot have second color (back color) at all. </remarks>
    @Test (dataProvider = "testFontOneColoredFillDataProvider")
    public void testFontOneColoredFill(String testName) throws Exception
    {
        // DML
        testFillBackColor(testName, ShapeMarkupLanguage.DML, msColor.Empty, msColor.getPeru(), msColor.Empty);

        // VML
        testFillBackColor(testName, ShapeMarkupLanguage.VML, msColor.Empty, msColor.getPeru(), msColor.Empty);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFontOneColoredFillDataProvider")
	public static Object[][] testFontOneColoredFillDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TextSolid"},
			{"TextNoFill"},
		};
	}

    /// <summary>
    /// Tests BackColor of the gradient Font fills.
    /// </summary>
    @Test (dataProvider = "testFontGradientFillDataProvider")
    public void testFontGradientFill(String testName, int r, int g, int b) throws Exception
    {
        Color expectedOriginalColor = new Color((r), (g), (b));

        // DML
        testFillBackColor(testName, ShapeMarkupLanguage.DML, expectedOriginalColor, msColor.getPeru());

        // VML
        testFillBackColor(testName, ShapeMarkupLanguage.VML, msColor.Empty, msColor.getPeru(), msColor.Empty);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFontGradientFillDataProvider")
	public static Object[][] testFontGradientFillDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TextPresetGradient",  0xCC,  0xCC,  0xFF},
			{"TextTwoColorGradient",  0x00,  0x00,  0x00},
			{"TextOneColorGradient",  0x00,  0x80,  0x80},
		};
	}

    /// <summary>
    /// Tests BackColor of the various Shape fills.
    /// </summary>
    /// <remarks> These fills have second (back color) and are able to change it. </remarks>
    @Test (dataProvider = "testShapeFillDataProvider")
    public void testShapeFill(String testName, int r, int g, int b) throws Exception
    {
        Color expectedOriginalColor = new Color((r), (g), (b));

        // Dml
        testFillBackColor(testName, ShapeMarkupLanguage.DML, expectedOriginalColor, Color.BLACK);
        testFillBackColor(testName, ShapeMarkupLanguage.DML, expectedOriginalColor, Color.WHITE);
        testFillBackColor(testName, ShapeMarkupLanguage.DML, expectedOriginalColor, msColor.getPeru());

        // Vml
        testFillBackColor(testName, ShapeMarkupLanguage.VML, expectedOriginalColor, Color.BLACK);
        testFillBackColor(testName, ShapeMarkupLanguage.VML, expectedOriginalColor, Color.WHITE);
        testFillBackColor(testName, ShapeMarkupLanguage.VML, expectedOriginalColor, msColor.getPeru());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testShapeFillDataProvider")
	public static Object[][] testShapeFillDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient",  0x00,  0x3B,  0x3B},
			{"TwoColorGradient",  0xFF,  0xFF,  0xFF},
			{"PresetGradient",  0xCC,  0xCC,  0xFF},
			{"Patterned",  0xFF,  0xFF,  0xFF},
		};
	}

    /// <summary>
    /// Tests BackColor of the Shape with Solid fill.
    /// </summary>
    /// <remarks>
    /// Solid fill cannot have second color (back color). But in VBA it still can be applied to VML shape.
    /// </remarks>
    @Test
    public void testShapeSolidFill() throws Exception
    {
        final String TEST_NAME = "Solid";

        // DML
        testFillBackColor(TEST_NAME, ShapeMarkupLanguage.DML, msColor.Empty, msColor.getPeru(), msColor.Empty);

        // VML
        testFillBackColor(TEST_NAME, ShapeMarkupLanguage.VML, Color.WHITE, Color.BLACK);
        testFillBackColor(TEST_NAME, ShapeMarkupLanguage.VML, Color.WHITE, Color.WHITE);
        testFillBackColor(TEST_NAME, ShapeMarkupLanguage.VML, Color.WHITE, msColor.getPeru());
    }

    /// <summary>
    /// Tests BackColor of the various Shape fills.
    /// </summary>
    /// <remarks>
    /// Setting BackColor in Word VBA for these fills for DML shapes does not actually change the color (and also
    /// does not throw). But for VML it does.
    /// </remarks>
    @Test (dataProvider = "testShapeFillEmptyADataProvider")
    public void testShapeFillEmptyA(String testName) throws Exception
    {
        // DML
        testFillBackColor(testName, ShapeMarkupLanguage.DML, msColor.Empty, msColor.getPeru(), msColor.Empty);

        // VML
        testFillBackColor(testName, ShapeMarkupLanguage.VML, Color.WHITE, Color.BLACK);
        testFillBackColor(testName, ShapeMarkupLanguage.VML, Color.WHITE, msColor.getPeru());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testShapeFillEmptyADataProvider")
	public static Object[][] testShapeFillEmptyADataProvider() throws Exception
	{
		return new Object[][]
		{
			{"PresetTextured"},
			{"UserPicture"},
			{"UserTextured"},
		};
	}

    /// <summary>
    /// Tests BackColor of the 'NoFill' Shape fill.
    /// </summary>
    @Test
    public void testShapeNoFill() throws Exception
    {
        Document doc = testFillBackColor("NoFill", ShapeMarkupLanguage.DML, msColor.Empty, msColor.getPeru());
        msAssert.areEqual(FillType.PATTERNED, getFill(doc).getFillType());

        doc = testFillBackColor("NoFill", ShapeMarkupLanguage.VML, Color.WHITE, msColor.getPeru());
        msAssert.areEqual(FillType.SOLID, getFill(doc).getFillType());
    }

    /// <summary>
    /// Tests BackColor of the first fillable object in a document at a specified path.
    /// </summary>
    private static Document testFillBackColor(String testFileNameWithoutExtension, /*ShapeMarkupLanguage*/byte markupLanguage,
        Color originalColor, Color desiredColor) throws Exception
    {
        return testFillBackColor(testFileNameWithoutExtension, markupLanguage, originalColor, desiredColor, desiredColor);
    }

    /// <summary>
    /// Tests BackColor of the first fillable object in a document at a specified path.
    /// </summary>
    private static Document testFillBackColor(String testFileNameWithoutExtension, /*ShapeMarkupLanguage*/byte markupLanguage,
        Color originalColor, Color desiredColor, Color actualColor) throws Exception
    {
        Document doc = open(testFileNameWithoutExtension, markupLanguage);
        return testFillBackColor(doc, testFileNameWithoutExtension, markupLanguage,
            originalColor, desiredColor, actualColor);
    }

    /// <summary>
    /// Tests BackColor of the first fillable object in a specified document.
    /// </summary>
    private static Document testFillBackColor(Document doc, String testFileNameWithoutExtension,
        /*ShapeMarkupLanguage*/byte markupLanguage, Color originalColor, Color desiredColor, Color actualColor) throws Exception
    {
        Fill fill = getFill(doc);
        checkColor(originalColor, fill.getBackColor());

        // Set desired BackColor.
        fill.setBackColor(desiredColor);
        checkColor(actualColor, fill.getBackColor());

        String ext = markupLanguageToExtension(markupLanguage);
        String outFileName = msString.format("FillFormat\\{0}_BackColor_{1}{2}",
            testFileNameWithoutExtension, msNumber.formatInt32(desiredColor.getRGB(), "X", msCultureInfo.getInvariantCulture()), ext);

        // Roundtrip the document.
        doc = TestUtil.saveOpen(doc, outFileName, createSaveOptions(markupLanguage), false);

        fill = getFill(doc);
        checkColor(actualColor, fill.getBackColor());

        return doc;
    }
}

