// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/12/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.msNumber;
import com.aspose.ms.System.Globalization.msCultureInfo;
import org.testng.annotations.DataProvider;


/// <summary>
/// The class for testing ForeColor property of the FillFormat object.
/// </summary>
@Test
public class TestFillFormatForeColor extends TestFillFormatBase
{
    /// <summary>
    /// Tests ForeColor of the various Font fills.
    /// </summary>
    @Test (dataProvider = "testFontFillForeColorDataProvider")
    public void testFontFillForeColor(String testName, int r, int g, int b) throws Exception
    {
        Color expectedOriginalColor = new Color((r), (g), (b));

        // DML
        testFillForeColor(testName, ShapeMarkupLanguage.DML, expectedOriginalColor, Color.BLACK);
        testFillForeColor(testName, ShapeMarkupLanguage.DML, expectedOriginalColor, Color.WHITE);
        testFillForeColor(testName, ShapeMarkupLanguage.DML, expectedOriginalColor, msColor.getPeru());

        // FOSS There is no fill for font in VML.
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFontFillForeColorDataProvider")
	public static Object[][] testFontFillForeColorDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TextSolid",  0xFF,  0x00,  0x00},
			{"TextOneColorGradient",  0x00,  0x80,  0x80},
			{"TextTwoColorGradient",  0x80,  0x00,  0x00},
			{"TextPresetGradient",  0xCC,  0xCC,  0xFF},
		};
	}

    /// <summary>
    /// Tests ForeColor of the various Shape fills.
    /// </summary>
    @Test (dataProvider = "testShapeFillForeColorRGBDataProvider")
    public void testShapeFillForeColorRGB(String testName, int r, int g, int b) throws Exception
    {
        Color expectedOriginalColor = new Color((r), (g), (b));

        // DML
        testFillForeColor(testName, ShapeMarkupLanguage.DML, expectedOriginalColor, Color.BLACK);
        testFillForeColor(testName, ShapeMarkupLanguage.DML, expectedOriginalColor, Color.WHITE);
        testFillForeColor(testName, ShapeMarkupLanguage.DML, expectedOriginalColor, msColor.getPeru());

        // VML
        testFillForeColor(testName, ShapeMarkupLanguage.VML, expectedOriginalColor, Color.BLACK);
        testFillForeColor(testName, ShapeMarkupLanguage.VML, expectedOriginalColor, Color.WHITE);
        testFillForeColor(testName, ShapeMarkupLanguage.VML, expectedOriginalColor, msColor.getPeru());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testShapeFillForeColorRGBDataProvider")
	public static Object[][] testShapeFillForeColorRGBDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Solid",  0xFF,  0x00,  0x00},
			{"OneColorGradient",  0x00,  0x80,  0x80},
			{"TwoColorGradient",  0x80,  0x00,  0x00},
			{"PresetGradient",  0xCC,  0xCC,  0xFF},
			{"Patterned",  0x80,  0x00,  0x00},
		};
	}

    /// <summary>
    /// Tests ForeColor of the various Shape fills.
    /// </summary>
    /// <remarks>
    /// Setting ForeColor in Word VBA for these fills for DML shapes does not actually change the color (and also
    /// does not throw). But for VML it does.
    /// </remarks>
    @Test (dataProvider = "testShapeFillForeColorDataProvider")
    public void testShapeFillForeColor(String testName) throws Exception
    {
        // DML
        testFillForeColor(testName, ShapeMarkupLanguage.DML, msColor.Empty, msColor.getPeru(), msColor.Empty);

        // VML
        testFillForeColor(testName, ShapeMarkupLanguage.VML, Color.WHITE, Color.BLACK);
        testFillForeColor(testName, ShapeMarkupLanguage.VML, Color.WHITE, Color.WHITE);
        testFillForeColor(testName, ShapeMarkupLanguage.VML, Color.WHITE, msColor.getPeru());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testShapeFillForeColorDataProvider")
	public static Object[][] testShapeFillForeColorDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"PresetTextured"},
			{"UserPicture"},
			{"UserTextured"},
		};
	}

    /// <summary>
    /// Tests ForeColor of the 'NoFill' for Font fill.
    /// </summary>
    @Test
    public void testFontNoFill() throws Exception
    {
        // DML
        Document doc = testFillForeColor("TextNoFill", ShapeMarkupLanguage.DML, msColor.Empty, msColor.getPeru());
        msAssert.areEqual(FillType.SOLID, getFill(doc).getFillType());

        // FOSS There is no fill for font in VML.
        // VML
    }

    /// <summary>
    /// Tests ForeColor of the 'NoFill' for Shape fill.
    /// </summary>
    @Test
    public void testShapeNoFill() throws Exception
    {
        // DML
        Document doc = testFillForeColor("NoFill", ShapeMarkupLanguage.DML, msColor.Empty, msColor.getPeru());
        msAssert.areEqual(FillType.SOLID, getFill(doc).getFillType());

        // VML
        doc = testFillForeColor("NoFill", ShapeMarkupLanguage.VML, Color.WHITE, msColor.getPeru());
        msAssert.areEqual(FillType.SOLID, getFill(doc).getFillType());
    }

    /// <summary>
    /// Tests ForeColor of the first fillable object in a document at a specified path.
    /// </summary>
    private static Document testFillForeColor(String testFileNameWithoutExtension, /*ShapeMarkupLanguage*/byte markupLanguage,
        Color expectedOriginalValue, Color desiredValue) throws Exception
    {
        return testFillForeColor(testFileNameWithoutExtension, markupLanguage,
            expectedOriginalValue, desiredValue, desiredValue);
    }

    /// <summary>
    /// Tests ForeColor of the first fillable object in a document at a specified path.
    /// </summary>
    private static Document testFillForeColor(String testFileNameWithoutExtension, /*ShapeMarkupLanguage*/byte markupLanguage,
        Color expectedOriginalValue, Color desiredValue, Color actualValue) throws Exception
    {
        Document doc = open(testFileNameWithoutExtension, markupLanguage);
        return testFillForeColor(doc, testFileNameWithoutExtension, markupLanguage,
            expectedOriginalValue, desiredValue, actualValue);
    }

    /// <summary>
    /// Tests ForeColor of the first fillable object in a specified document.
    /// </summary>
    private static Document testFillForeColor(Document doc, String testFileNameWithoutExtension,
        /*ShapeMarkupLanguage*/byte markupLanguage, Color expectedOriginalValue, Color desiredValue, Color actualValue) throws Exception
    {
        Fill fill = getFill(doc);
        checkColor(expectedOriginalValue, fill.getForeColor());

        // Set desired value.
        fill.setForeColor(desiredValue);
        checkColor(actualValue, fill.getForeColor());

        String ext = markupLanguageToExtension(markupLanguage);
        String outFileName = msString.format("FillFormat\\{0}_ForeColor_{1}{2}",
            testFileNameWithoutExtension, msNumber.formatInt32(desiredValue.getRGB(), "X", msCultureInfo.getInvariantCulture()), ext);

        // Roundtrip the document.
        doc = TestUtil.saveOpen(doc, outFileName, createSaveOptions(markupLanguage), false);

        Node node = getFirstFillable(doc);
        fill = (node.getNodeType() == NodeType.RUN) ? ((Run)node).getFont().getFill() : ((Shape)node).getFill();

        // There is no fill for font in VML. So after export to VML-based format (like DOC),
        // the color is reset to its original value (Empty).
        Color expectedColor = ((markupLanguage == ShapeMarkupLanguage.VML) && (node.getNodeType() == NodeType.RUN))
            ? expectedOriginalValue
            : actualValue;
        checkColor(expectedColor, fill.getForeColor());

        return doc;
    }
}

