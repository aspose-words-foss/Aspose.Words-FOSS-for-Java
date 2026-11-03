// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/01/2023 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import com.aspose.DrColor;
import org.testng.annotations.DataProvider;


/// <summary>
/// The class for testing ForeThemeColor and BackThemeColor of Fill object.
/// </summary>
@Test
public class TestFillThemeColors extends TestFillFormatBase
{
    /// <summary>
    /// Tests getting and setting all theme colors.
    /// Dml case.
    /// </summary>
    @Test
    public void testThemeColorsDml() throws Exception
    {
        /*ThemeColor*/int[] expThemeColors = new /*ThemeColor*/int[]
        {
            ThemeColor.NONE, ThemeColor.DARK_2, ThemeColor.TEXT_1, ThemeColor.LIGHT_1, ThemeColor.DARK_1,
            ThemeColor.ACCENT_2, ThemeColor.ACCENT_4, ThemeColor.ACCENT_1, ThemeColor.LIGHT_2, ThemeColor.ACCENT_3,
            ThemeColor.BACKGROUND_1, ThemeColor.FOLLOWED_HYPERLINK, ThemeColor.ACCENT_5, ThemeColor.ACCENT_6,
            ThemeColor.HYPERLINK, ThemeColor.TEXT_2
        };

        Document doc = open("ThemeColors", ShapeMarkupLanguage.DML);

        for (int i = 0; i < doc.getFirstSection().getBody().getShapes().getCount(); i++)
        {
            Shape shape = doc.getFirstSection().getBody().getShapes().get(i);

            msAssert.areEqual(expThemeColors[i], shape.getFill().getBackThemeColor());
            msAssert.areEqual(expThemeColors[i], shape.getFill().getForeThemeColor());

            if (i < doc.getFirstSection().getBody().getShapes().getCount() - 1)
            {
                shape.getFill().setBackThemeColor(expThemeColors[i + 1]);
                shape.getFill().setForeThemeColor(expThemeColors[i + 1]);
                msAssert.areEqual(expThemeColors[i + 1], shape.getFill().getBackThemeColor());
                msAssert.areEqual(expThemeColors[i + 1], shape.getFill().getForeThemeColor());
            }
            else
            {
                shape.getFill().setBackThemeColor(ThemeColor.NONE);
                shape.getFill().setForeThemeColor(ThemeColor.NONE);
                msAssert.areEqual(ThemeColor.NONE, shape.getFill().getForeThemeColor());
                msAssert.areEqual(new Color((68), (84), (106), (255)), shape.getFill().getForeColor());
                msAssert.areEqual(ThemeColor.NONE, shape.getFill().getBackThemeColor());
                msAssert.areEqual(new Color((68), (84), (106), (255)), shape.getFill().getBackColor());
            }
        }
    }



    /// <summary>
    /// Tests getting and setting ForeThemeColor for different fill types.
    /// Vml case.
    /// </summary>
    @Test (dataProvider = "testForeThemeColorVmlSupportedFillsDataProvider")
    public void testForeThemeColorVmlSupportedFills(String fileName) throws Exception
    {
        Document doc = open(fileName, ShapeMarkupLanguage.VML);

        Fill fill = (fileName.contains("Text"))
            ? doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getFont().getFill()
            : doc.getFirstSection().getBody().getShapes().get(0).getFill();

        msAssert.areEqual(ThemeColor.NONE, fill.getForeThemeColor());
        fill.setForeThemeColor(ThemeColor.ACCENT_1);

        fill = (fileName.contains("Text"))
            ? doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getFont().getFill()
            : doc.getFirstSection().getBody().getShapes().get(0).getFill();
        msAssert.areEqual(ThemeColor.ACCENT_1, fill.getForeThemeColor());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testForeThemeColorVmlSupportedFillsDataProvider")
	public static Object[][] testForeThemeColorVmlSupportedFillsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"NoFill"},
			{"FillMixed"},
			{"OneColorGradient"},
			{"TwoColorGradient"},
			{"Patterned"},
			{"Solid"},
			{"TextSolid"},
		};
	}

    /// <summary>
    /// Tests getting and setting BackThemeColor for different fill types.
    /// Vml case.
    /// </summary>
    @Test (dataProvider = "testBackThemeColorVmlSupportedFillsDataProvider")
    public void testBackThemeColorVmlSupportedFills(String fileName, /*ThemeColor*/int expThemeColor) throws Exception
    {
        Document doc = open(fileName, ShapeMarkupLanguage.VML);

        Fill fill = doc.getFirstSection().getBody().getShapes().get(0).getFill();
        // For a fill other than NoFill in VML, there is no way to distinguish between ThemeColor.Light1 and ThemeColor.None.
        msAssert.areEqual(expThemeColor, fill.getBackThemeColor());
        fill.setBackThemeColor(ThemeColor.ACCENT_1);

        fill = doc.getFirstSection().getBody().getShapes().get(0).getFill();
        msAssert.areEqual(ThemeColor.ACCENT_1, fill.getBackThemeColor());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testBackThemeColorVmlSupportedFillsDataProvider")
	public static Object[][] testBackThemeColorVmlSupportedFillsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"NoFill",  ThemeColor.NONE},
			{"FillMixed",  ThemeColor.NONE},
			{"OneColorGradient",  ThemeColor.NONE},
			{"TwoColorGradient",  ThemeColor.LIGHT_1},
			{"Patterned",  ThemeColor.LIGHT_1},
			{"Solid",  ThemeColor.LIGHT_1},
		};
	}

    /// <summary>
    /// Tests throwing an exception when trying to set ForeThemeColor value for certain Fill types.
    /// ForeThemeColor cannot be set for BlipFill and PresetFill fills.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QCannot set ForeThemeColor to this Fill.\\E", dataProvider = "testForeThemeColorUnsupportedFillDataProvider")
    public void testForeThemeColorUnsupportedFill(String fileName, /*ShapeMarkupLanguage*/byte shapeMarkupLanguage) throws Exception
    {
        Document doc = open(fileName, shapeMarkupLanguage);

        Fill fill = doc.getFirstSection().getBody().getShapes().get(0).getFill();

        msAssert.areEqual(ThemeColor.NONE, fill.getForeThemeColor());
        // Expect InvalidOperationException here.
        fill.setForeThemeColor(ThemeColor.ACCENT_1);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testForeThemeColorUnsupportedFillDataProvider")
	public static Object[][] testForeThemeColorUnsupportedFillDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"UserPicture",  ShapeMarkupLanguage.VML},
			{"PresetTextured",  ShapeMarkupLanguage.VML},
			{"UserPicture",  ShapeMarkupLanguage.DML},
			{"PresetTextured",  ShapeMarkupLanguage.DML},
		};
	}

    /// <summary>
    /// Tests throwing an exception when trying to set BackThemeColor value for certain Fill types.
    /// BackThemeColor cannot be set for BlipFill and PresetFill fills.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QCannot set BackThemeColor to this Fill.\\E", dataProvider = "testBackThemeColorUnsupportedFillDataProvider")
    public void testBackThemeColorUnsupportedFill(String fileName, /*ShapeMarkupLanguage*/byte shapeMarkupLanguage) throws Exception
    {
        Document doc = open(fileName, shapeMarkupLanguage );

        Fill fill = (fileName.contains("TextSolid"))
            ? doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getFont().getFill()
            : doc.getFirstSection().getBody().getShapes().get(0).getFill();

        msAssert.areEqual(ThemeColor.NONE, fill.getBackThemeColor());
        // Expect InvalidOperationException here.
        fill.setBackThemeColor(ThemeColor.ACCENT_1);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testBackThemeColorUnsupportedFillDataProvider")
	public static Object[][] testBackThemeColorUnsupportedFillDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"UserPicture",  ShapeMarkupLanguage.VML},
			{"PresetTextured",  ShapeMarkupLanguage.VML},
			{"UserPicture",  ShapeMarkupLanguage.DML},
			{"PresetTextured",  ShapeMarkupLanguage.DML},
			{"TextSolid",  ShapeMarkupLanguage.DML},
			{"TextSolid",  ShapeMarkupLanguage.VML},
		};
	}

    private void checkForeThemeColorDmlSupportedFill(String fileName, /*DmlFillType*/int dmlFillType, boolean fillChanging) throws Exception
    {
        Document doc = open(fileName, ShapeMarkupLanguage.DML);

        ShapeBase shapeNode = (dmlFillType == DmlFillType.GROUP_FILL)
            ? (ShapeBase)doc.getChild(NodeType.GROUP_SHAPE, 0, true)
            : doc.getFirstSection().getBody().getShapes().get(0);
        DmlFill dmlFill = (DmlFill)shapeNode.getFillCore();
        Fill fill = shapeNode.getFill();
        msAssert.areEqual(ThemeColor.NONE, fill.getForeThemeColor());

        fill.setForeThemeColor(ThemeColor.ACCENT_1);
        dmlFill = (DmlFill)shapeNode.getFillCore();
        fill = shapeNode.getFill();
        msAssert.areEqual(ThemeColor.ACCENT_1, fill.getForeThemeColor());
        // Setting ForeThemeColor value should not change BackThemeColor values.
        msAssert.areEqual(ThemeColor.NONE, fill.getBackThemeColor());
        // Setting ForeThemeColor for NoFill, StyleFill and GroupFill changes the fill type to SolidFill.
        // The fill type does not change for other types.
        msAssert.areEqual((fillChanging) ? DmlFillType.SOLID_FILL : dmlFillType, dmlFill.getDmlFillType());

        // Checks setting ThemeColor.None.
        DrColor expColor = dmlFill.getColorInternal();
        fill.setForeThemeColor(ThemeColor.NONE);
        dmlFill = (DmlFill)shapeNode.getFillCore();
        fill = shapeNode.getFill();
        msAssert.areEqual(ThemeColor.NONE, fill.getForeThemeColor());
        msAssert.areEqual(expColor, dmlFill.getColorInternal());
    }

    private void checkBackThemeColorDmlSupportedFill(String fileName, /*DmlFillType*/int dmlFillType, boolean fillChanging) throws Exception
    {
        Document doc = open(fileName, ShapeMarkupLanguage.DML);

        ShapeBase shapeNode = (dmlFillType == DmlFillType.GROUP_FILL)
            ? (ShapeBase)doc.getChild(NodeType.GROUP_SHAPE, 0, true)
            : doc.getFirstSection().getBody().getShapes().get(0);

        DmlFill dmlFill = (DmlFill)shapeNode.getFillCore();
        Fill fill = shapeNode.getFill();
        msAssert.areEqual(ThemeColor.NONE, fill.getBackThemeColor());

        fill.setBackThemeColor(ThemeColor.ACCENT_1);
        dmlFill = (DmlFill)shapeNode.getFillCore();
        fill = shapeNode.getFill();
        msAssert.areEqual(ThemeColor.ACCENT_1, fill.getBackThemeColor());

        // Setting BackThemeColor for GradientFill and PatternFill does not change the fill type and ForeThemeColor value.
        // PatternFill with ForeThemeColor equal to BackThemeColor is set for all other fill types.
        msAssert.areEqual((fillChanging) ? DmlFillType.PATTERN_FILL : dmlFillType, dmlFill.getDmlFillType());
        msAssert.areEqual((fillChanging) ? fill.getBackThemeColor() : ThemeColor.NONE, fill.getForeThemeColor());

        // Checks setting ThemeColor.None.
        DrColor expColor = dmlFill.getColor2Internal();
        fill.setBackThemeColor(ThemeColor.NONE);
        dmlFill = (DmlFill)shapeNode.getFillCore();
        fill = shapeNode.getFill();
        msAssert.areEqual(ThemeColor.NONE, fill.getBackThemeColor());
        msAssert.areEqual(expColor, dmlFill.getColor2Internal());
    }

    /// <summary>
    /// DefaultTheme contains the same color values for Dark1 and Text1, Dark2 and Text2,
    /// Background1 and Light1, and Background2 and Light2.
    /// Therefore, these ThemeColor-s for VML cannot be distinguished.
    /// </summary>
    private /*ThemeColor*/int getVmlRealThemeColor(/*ThemeColor*/int desiredColor)
    {
        switch (desiredColor)
        {
            case ThemeColor.TEXT_1:
                return ThemeColor.DARK_1;
            case ThemeColor.TEXT_2:
                return ThemeColor.DARK_2;
            case ThemeColor.BACKGROUND_1:
                return ThemeColor.LIGHT_1;
            case ThemeColor.BACKGROUND_2:
                return ThemeColor.LIGHT_2;
            default:
                return desiredColor;
        }
    }
}

