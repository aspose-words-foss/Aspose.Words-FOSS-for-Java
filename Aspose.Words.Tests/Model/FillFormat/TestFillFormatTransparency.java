// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/11/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Drawing.msColor;
import java.awt.Color;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


/// <summary>
/// The class for testing Transparency property of the FillFormat object.
/// </summary>
@Test
public class TestFillFormatTransparency extends TestFillFormatBase
{
    /// <summary>
    /// Tests Transparency of the various Blip fills.
    /// </summary>
    /// <remarks> These fills behave the same in DML and VML. </remarks>
    @Test (dataProvider = "testBlipTransparencyDataProvider")
    public void testBlipTransparency(String testName) throws Exception
    {
        final double EXPECTED_ORIGINAL_VALUE = 0.37d;

        // DML
        testFillTransparency(testName, ShapeMarkupLanguage.DML, EXPECTED_ORIGINAL_VALUE, 0.0);
        testFillTransparency(testName, ShapeMarkupLanguage.DML, EXPECTED_ORIGINAL_VALUE, 1.0);
        testFillTransparency(testName, ShapeMarkupLanguage.DML, EXPECTED_ORIGINAL_VALUE, 0.52);
        // VML
        testFillTransparency(testName, ShapeMarkupLanguage.VML, EXPECTED_ORIGINAL_VALUE, 0.0);
        testFillTransparency(testName, ShapeMarkupLanguage.VML, EXPECTED_ORIGINAL_VALUE, 1.0);
        testFillTransparency(testName, ShapeMarkupLanguage.VML, EXPECTED_ORIGINAL_VALUE, 0.52);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testBlipTransparencyDataProvider")
	public static Object[][] testBlipTransparencyDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Patterned"},
			{"PresetTextured"},
			{"UserPicture"},
			{"UserTextured"},
		};
	}

    /// <summary>
    /// Tests Transparency of the various one-colored DML fills.
    /// </summary>
    @Test (dataProvider = "testDmlTransparencyDataProvider")
    public void testDmlTransparency(String testName, double expectedOriginalValue) throws Exception
    {
        testFillTransparency(testName, ShapeMarkupLanguage.DML, expectedOriginalValue, 0.0);
        testFillTransparency(testName, ShapeMarkupLanguage.DML, expectedOriginalValue, 1.0);
        testFillTransparency(testName, ShapeMarkupLanguage.DML, expectedOriginalValue, 0.52);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDmlTransparencyDataProvider")
	public static Object[][] testDmlTransparencyDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TextSolid",  0.39},
			{"TextOneColorGradient",  0.65},
			{"TextNoFill",  1.0},
			{"Solid",  0.37},
			{"NoFill",  1.0},
		};
	}

    /// <summary>
    /// Tests Transparency of the various gradient fills.
    /// It has additional check for each gradient stop, as transparency setter should change them all.
    /// </summary>
    @Test (dataProvider = "testDmlGradientTransparencyDataProvider")
    public void testDmlGradientTransparency(String testName, double expectedOriginalValue) throws Exception
    {
        Document doc = testFillTransparency(testName, ShapeMarkupLanguage.DML, expectedOriginalValue, 0.0);
        checkGradientStops(doc, 0.0);

        doc = testFillTransparency(testName, ShapeMarkupLanguage.DML, expectedOriginalValue, 1.0);
        checkGradientStops(doc, 1.0);

        doc = testFillTransparency(testName, ShapeMarkupLanguage.DML, expectedOriginalValue, 0.52);
        checkGradientStops(doc, 0.52);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDmlGradientTransparencyDataProvider")
	public static Object[][] testDmlGradientTransparencyDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TextTwoColorGradient",  0.16},
			{"TextPresetGradient",  0.39},
			{"OneColorGradient",  0.37},
			{"TwoColorGradient",  0.37},
			{"PresetGradient",  0.37},
		};
	}

    /// <summary>
    /// Tests Transparency of the various VML shapes fills.
    /// </summary>
    @Test (dataProvider = "testVmlShapeTransparencyDataProvider")
    public void testVmlShapeTransparency(String testName, double expectedOriginalValue) throws Exception
    {
        testFillTransparency(testName, ShapeMarkupLanguage.VML, expectedOriginalValue, 0.0);
        testFillTransparency(testName, ShapeMarkupLanguage.VML, expectedOriginalValue, 1.0);
        testFillTransparency(testName, ShapeMarkupLanguage.VML, expectedOriginalValue, 0.52);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testVmlShapeTransparencyDataProvider")
	public static Object[][] testVmlShapeTransparencyDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient",  0.37},
			{"PresetGradient",  0.37},
			{"TwoColorGradient",  0.37},
			{"Solid",  0.37},
			{"NoFill",  0},
		};
	}

    /// <summary>
    /// Tests Transparency when there is no Alpha in the color of the fill.
    /// </summary>
    @Test (dataProvider = "testTransparencyNoAlphaDataProvider")
    public void testTransparencyNoAlpha(String testName) throws Exception
    {
        Document doc = open(testName, ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);

        // Apply color without Alpha (full opaque).
        fill.setForeColor(msColor.getGreen());
        msAssert.areEqual(0.0, 0.01, fill.getTransparency());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTransparencyNoAlphaDataProvider")
	public static Object[][] testTransparencyNoAlphaDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Patterned"},
			{"OneColorGradient"},
			{"PresetGradient"},
			{"TwoColorGradient"},
			{"Solid"},
			{"NoFill"},
			{"TextSolid"},
			{"TextOneColorGradient"},
			{"TextTwoColorGradient"},
			{"TextPresetGradient"},
			{"TextNoFill"},
		};
	}

    /// <summary>
    /// Checks gradient stops.
    /// </summary>
    private static void checkGradientStops(Node node, double expectedTransparency) throws Exception
    {
        Node fillableNode = getFirstFillable(node);

        DmlGradientFill fill;
        double expectedAlpha;
        if (fillableNode.getNodeType() == NodeType.SHAPE)
        {
            Shape shape = (Shape)fillableNode;
            fill = (DmlGradientFill)((IDmlCommonShapePrSource)shape.getDmlNode()).getFill();
            // Shapes keep opacity instead of transparency.
            expectedAlpha = 1.0 - expectedTransparency;
        }
        else
        {
            Run run = (Run)fillableNode;
            fill = (DmlGradientFill)run.getRunPr().get(FontAttr.EFFECT_FILL);
            expectedAlpha = expectedTransparency;
        }

        for (DmlGradientStop gradientStop : fill.getGradientStops())
            msAssert.areEqual(expectedAlpha, 0.001, gradientStop.getColor().getAlpha().getValue());
    }

    /// <summary>
    /// Tests Transparency of the first fillable object in a document at a specified path.
    /// </summary>
    private static Document testFillTransparency(String testFileNameWithoutExtension, /*ShapeMarkupLanguage*/byte markupLanguage,
        double originalValue, double desiredValue) throws Exception
    {
        return testFillTransparency(testFileNameWithoutExtension, markupLanguage, originalValue, desiredValue, desiredValue);
    }

    /// <summary>
    /// Tests Transparency of the first fillable object in a document at a specified path.
    /// </summary>
    private static Document testFillTransparency(String testFileNameWithoutExtension, /*ShapeMarkupLanguage*/byte markupLanguage,
        double originalValue, double desiredValue, double actualValue) throws Exception
    {
        Document doc = open(testFileNameWithoutExtension, markupLanguage);
        return testFillTransparency(doc, testFileNameWithoutExtension, markupLanguage,
            originalValue, desiredValue, actualValue);
    }

    /// <summary>
    /// Tests Transparency of the first fillable object in a specified document.
    /// </summary>
    private static Document testFillTransparency(Document doc, String testFileNameWithoutExtension,
        /*ShapeMarkupLanguage*/byte markupLanguage, double originalValue, double desiredValue, double actualValue) throws Exception
    {
        Fill fill = getFill(doc);
        msAssert.areEqual(originalValue, 0.005, fill.getTransparency());

        boolean isNoFill = !fill.getVisible() && (fill.getFillType() == FillType.SOLID);

        // Set desired Value.
        fill.setTransparency(desiredValue);
        msAssert.areEqual(desiredValue, 0.005, fill.getTransparency());

        // WORDSNET-25216 Need to specify color for a DML pattern fill otherwise it will not be written to
        // the output document.
        if (isNoFill && (fill.getFillType() == FillType.PATTERNED) && (markupLanguage == ShapeMarkupLanguage.DML))
        {
            fill.setForeColor(Color.BLACK);
            fill.setTransparency(desiredValue);
        }

        String ext = markupLanguageToExtension(markupLanguage);
        String outFileName = msString.format("FillFormat\\{0}_Transparency_{1}{2}",
            testFileNameWithoutExtension, desiredValue, ext);

        // Roundtrip the document.
        doc = TestUtil.saveOpen(doc, outFileName, createSaveOptions(markupLanguage), false);

        fill = getFill(doc);
        msAssert.areEqual(actualValue, 0.005, fill.getTransparency());

        return doc;
    }
}

