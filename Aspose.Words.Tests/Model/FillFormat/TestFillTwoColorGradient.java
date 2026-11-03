// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/06/2021 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Drawing.msColor;
import java.awt.Color;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


/// <summary>
/// The class for testing TwoColorGradient() method of Fill object.
/// </summary>
@Test
public class TestFillTwoColorGradient extends TestFillFormatBase
{
    /// <summary>
    /// Tests all two-color gradients in text from scratch without specifying colors.
    /// </summary>
    /// <remarks>
    /// Word does not allow to work with gradient stops for Font object in VBA and throws an exception.
    /// However, user can change colors in GUI. In AW setting gradient fill mimics Word behavior in VBA for shapes by
    /// changing gradient stops forcibly to the same colors as in shapes with corresponding gradient style and variant.
    /// </remarks>
    @Test (dataProvider = "testTextFromScratchDataProvider")
    public void testTextFromScratch(/*ShapeMarkupLanguage*/byte markupLanguage) throws Exception
    {
        Document doc = createDocumentWithAllGradientsText(markupLanguage);

        String outFileName = msString.format("Model\\FillFormat\\TwoColorGradient_{0}_Text.docx", ShapeMarkupLanguage.toString(markupLanguage));
        TestUtil.saveCheckGold(doc, outFileName);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTextFromScratchDataProvider")
	public static Object[][] testTextFromScratchDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ShapeMarkupLanguage.DML},
			{ShapeMarkupLanguage.VML},
		};
	}

    /// <summary>
    /// Tests all two-color gradients in text from scratch with specifying colors.
    /// </summary>
    @Test (dataProvider = "testTextFromScratchWithColorsDataProvider")
    public void testTextFromScratchWithColors(/*ShapeMarkupLanguage*/byte markupLanguage) throws Exception
    {
        Document doc = createDocumentWithAllGradientsText(markupLanguage, msColor.getChocolate(), Color.BLUE);

        String outFileName = msString.format("Model\\FillFormat\\TwoColorGradient_{0}_Text_WithColors.docx", ShapeMarkupLanguage.toString(markupLanguage));
        TestUtil.saveCheckGold(doc, outFileName);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTextFromScratchWithColorsDataProvider")
	public static Object[][] testTextFromScratchWithColorsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ShapeMarkupLanguage.DML},
			{ShapeMarkupLanguage.VML},
		};
	}

    /// <summary>
    /// Tests setting TwoColorGradient for various DML Shape fills.
    /// </summary>
    @Test (dataProvider = "testShapeFillsDmlDataProvider")
    /*-----------------------------------------------------------------*/
    // Actually, all BlipFills in VBA returns Color.White, but we returns Color.Empty and then convert it to Accent1.
    // We probable should override DmlBlipFill.DmlColorInternal to return DrColor.White to mimic Word VBA.
    /*-----------------------------------------------------------------*/
    public void testShapeFillsDml(String testName,
        int foreR, int foreG, int foreB,
        int backR, int backG, int backB) throws Exception
    {
        Document doc = open(testName, ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);

        Color expectedForeColor = new Color((foreR), (foreG), (foreB));
        Color expectedBackColor = new Color((backR), (backG), (backB));

        // Set some two-color gradient.
        final /*GradientStyle*/int STYLE = GradientStyle.FROM_CENTER;
        final /*GradientVariant*/int VARIANT = GradientVariant.VARIANT_4;
        fill.twoColorGradient(STYLE, VARIANT);

        msAssert.areEqual(FillType.GRADIENT, fill.getFillType());
        msAssert.areEqual(STYLE, fill.getGradientStyle());

        checkColor(expectedForeColor, fill.getForeColor());
        checkColor(expectedBackColor, fill.getBackColor());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testShapeFillsDmlDataProvider")
	public static Object[][] testShapeFillsDmlDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient",  0x00,  0x80,  0x80,  /**/ 0x00,  0x3B,  0x3B},
			{"TwoColorGradient",  0x80,  0x00,  0x00,  /**/ 0xFF,  0xFF,  0xFF},
			{"PresetGradient",  0xCC,  0xCC,  0xFF,  /**/ 0xCC,  0xCC,  0xFF},
			{"Patterned",  0x80,  0x00,  0x00,  /**/ 0xFF,  0xFF,  0xFF},
			{"PresetTextured",  0x44,  0x72,  0xC4,  /**/ 0xFF,  0xFF,  0xFF},
			{"UserPicture",  0x44,  0x72,  0xC4,  /**/ 0xFF,  0xFF,  0xFF},
			{"UserTextured",  0x44,  0x72,  0xC4,  /**/ 0xFF,  0xFF,  0xFF},
			{"Solid",  0xFF,  0x00,  0x00,  /**/ 0xFF,  0xFF,  0xFF},
			{"NoFill",  0x44,  0x72,  0xC4,  /**/ 0xFF,  0xFF,  0xFF},
		};
	}

    /// <summary>
    /// Tests setting TwoColorGradient for various VML Shape fills.
    /// </summary>
    @Test (dataProvider = "testShapeFillsVmlDataProvider")
    public void testShapeFillsVml(String testName,
        int foreR, int foreG, int foreB,
        int backR, int backG, int backB) throws Exception
    {
        Document doc = open(testName, ShapeMarkupLanguage.VML);
        Fill fill = getFill(doc);

        Color expectedForeColor = new Color((foreR), (foreG), (foreB));
        Color expectedBackColor = new Color((backR), (backG), (backB));

        // Set some two-color gradient.
        final /*GradientStyle*/int STYLE = GradientStyle.HORIZONTAL;
        final /*GradientVariant*/int VARIANT = GradientVariant.VARIANT_3;
        fill.twoColorGradient(STYLE, VARIANT);

        msAssert.areEqual(FillType.GRADIENT, fill.getFillType());
        msAssert.areEqual(STYLE, fill.getGradientStyle());
        msAssert.areEqual(VARIANT, fill.getGradientVariant());

        checkColor(expectedForeColor, fill.getForeColor());
        checkColor(expectedBackColor, fill.getBackColor());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testShapeFillsVmlDataProvider")
	public static Object[][] testShapeFillsVmlDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"OneColorGradient",  0x00,  0x80,  0x80,  /**/ 0x00,  0x3B,  0x3B},
			{"TwoColorGradient",  0x80,  0x00,  0x00,  /**/ 0xFF,  0xFF,  0xFF},
			{"PresetGradient",  0xCC,  0xCC,  0xFF,  /**/ 0xCC,  0xCC,  0xFF},
			{"Patterned",  0x80,  0x00,  0x00,  /**/ 0xFF,  0xFF,  0xFF},
			{"PresetTextured",  0x00,  0x00,  0x00,  /**/ 0xFF,  0xFF,  0xFF},
			{"UserPicture",  0x00,  0x00,  0x00,  /**/ 0xFF,  0xFF,  0xFF},
			{"UserTextured",  0x00,  0x00,  0x00,  /**/ 0xFF,  0xFF,  0xFF},
			{"Solid",  0xFF,  0x00,  0x00,  /**/ 0xFF,  0xFF,  0xFF},
			{"NoFill",  0x00,  0x00,  0x00,  /**/ 0xFF,  0xFF,  0xFF},
		};
	}

    /// <summary>
    /// Tests setting TwoColorGradient for various Font fills (DML).
    /// </summary>
    @Test (dataProvider = "testFontFillsDmlDataProvider")
    public void testFontFillsDml(String testName,
        int foreR, int foreG, int foreB,
        int backR, int backG, int backB) throws Exception
    {
        Document doc = open(testName, ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);

        Color expectedForeColor = new Color((foreR), (foreG), (foreB));
        Color expectedBackColor = new Color((backR), (backG), (backB));

        // Set some two-color gradient.
        final /*GradientStyle*/int STYLE = GradientStyle.HORIZONTAL;
        final /*GradientVariant*/int VARIANT = GradientVariant.VARIANT_3;
        fill.twoColorGradient(STYLE, VARIANT);

        msAssert.areEqual(FillType.GRADIENT, fill.getFillType());
        msAssert.areEqual(STYLE, fill.getGradientStyle());
        msAssert.areEqual(VARIANT, fill.getGradientVariant());

        checkColor(expectedForeColor, fill.getForeColor());
        checkColor(expectedBackColor, fill.getBackColor());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFontFillsDmlDataProvider")
	public static Object[][] testFontFillsDmlDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TextOneColorGradient",  0x00,  0x80,  0x80,  /**/ 0x00,  0x80,  0x80},
			{"TextTwoColorGradient",  0x80,  0x00,  0x00,  /**/ 0x80,  0x00,  0x00},
			{"TextPresetGradient",  0xCC,  0xCC,  0xFF,  /**/ 0xCC,  0xCC,  0xFF},
			{"TextSolid",  0xFF,  0x00,  0x00,  /**/ 0xFF,  0x00,  0x00},
			{"TextNoFill",  0x00,  0x00,  0x00,  /**/ 0x00,  0x00,  0x00},
		};
	}

    /// <summary>
    /// Tests <see cref="Fill.GradientAngle"/> in text.
    /// </summary>
    @Test
    public void testGradientAngleText() throws Exception
    {
        Document doc = createDocumentWithAllGradientsText(ShapeMarkupLanguage.DML);
        checkGradientAngleText(doc);
    }

    /// <summary>
    /// Returns gradient variant expected for a specified gradient style and variant for Shapes.
    /// </summary>
    private static int getExpectedVariant(/*GradientStyle*/int style, int variant, /*ShapeMarkupLanguage*/byte markupLanguage)
    {
        if ((style != GradientStyle.FROM_CENTER) || (markupLanguage == ShapeMarkupLanguage.VML))
            return variant;

        switch (variant)
        {
            // Variants 1 and 2 of a 'FromCenter' gradient in DML differ only in that the Fore and Back colors
            // are swapped. Thus, a variant 2 is recognized as a variant 1.
            case 2:
                return 1;
            // The same is with variants 3 and 4.
            case 4:
                return 3;
            default:
                return variant;
        }
    }

    /// <summary>
    /// Returns gradient variant expected for a specified gradient style and variant for Text.
    /// </summary>
    private static int getExpectedVariantText(/*GradientStyle*/int style, int variant)
    {
        if (style != GradientStyle.FROM_CENTER)
            return variant;

        switch (variant)
        {
            // Variants 1 and 2 of a 'FromCenter' gradient in DML differ only in that the Fore and Back colors
            // are swapped. Thus, a variant 2 is recognized as a variant 1.
            case 2:
                return 1;
            // The same is with variants 3 and 4.
            case 4:
                return 3;
            default:
                return variant;
        }
    }

    /// <summary>
    /// Creates document with all gradients texts with default colors.
    /// </summary>
    private static Document createDocumentWithAllGradientsText(/*ShapeMarkupLanguage*/byte markupLanguage) throws Exception
    {
        return createDocumentWithAllGradientsText(markupLanguage, msColor.Empty, msColor.Empty);
    }

    /// <summary>
    /// Creates document with all gradients texts with the specified colors.
    /// </summary>
    private static Document createDocumentWithAllGradientsText(/*ShapeMarkupLanguage*/byte markupLanguage, Color color1, Color color2) throws Exception
    {
        DocumentBuilder builder = createDocument(markupLanguage);
        builder.getDocument().getFirstSection().getPageSetup().setOrientation(Orientation.LANDSCAPE);
        builder.getDocument().getFirstSection().getPageSetup().setLeftMargin(10.0);
        builder.getDocument().getFirstSection().getPageSetup().setRightMargin(10.0);

        builder.getFont().setSize(24.0);
        /*GradientStyle*/int[] styles = new /*GradientStyle*/int[]
        {
            GradientStyle.HORIZONTAL, GradientStyle.VERTICAL, GradientStyle.DIAGONAL_DOWN, GradientStyle.DIAGONAL_UP,
            GradientStyle.FROM_CENTER, GradientStyle.FROM_CORNER
        };
        for (/*GradientStyle*/int style : styles)
        {
            for (int variant = 1; variant <= 4; variant++)
            {
                if ((color1 == msColor.Empty) || (color2 == msColor.Empty))
                    builder.getFont().getFill().twoColorGradient(style, (/*GradientVariant*/int)variant);
                else
                    builder.getFont().getFill().twoColorGradient(color1, color2, style, (/*GradientVariant*/int)variant);

                builder.write(msString.format("{0}_{1}\t", GradientStyle.toString(style), variant));

                int expectedVariant = getExpectedVariantText(style, variant);
                String msg = msString.format("Style: {0}; Variant: {1}", GradientStyle.toString(style), variant);
                Run run = (Run)builder.getCurrentParagraph().getLastChild();
                msAssert.areEqual(FillType.GRADIENT, run.getFont().getFill().getFillType(), msg);
                msAssert.areEqual(style, run.getFont().getFill().getGradientStyle(), msg);
                msAssert.areEqual((/*GradientVariant*/int)expectedVariant, run.getFont().getFill().getGradientVariant(), msg);
            }

            builder.writeln();
        }

        return builder.getDocument();
    }
}

