// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/05/2021 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.DrColor;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.TestFxUtil;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;
import org.testng.annotations.DataProvider;


/// <summary>
/// The class for testing PresetTextured() method of Fill object.
/// </summary>
@Test
public class TestFillPatterned extends TestFillFormatBase
{
    /// <summary>
    /// Test shows slightly confusing behavior related to background color fill and fill type change in DML.
    /// It is interesting that MS Word does the same.
    /// </summary>
    @Test
    public void testPatternedNotPatternFillDml() throws Exception
    {
        Document doc = open("Solid", ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);
        fill.setForeColor(DrColor.BLUE.toNativeColor());
        fill.setBackColor(DrColor.RED.toNativeColor());
        fill.patterned(PatternType.DARK_DOWNWARD_DIAGONAL);
        msAssert.areEqual(fill.getForeColor(), DrColor.BLUE.toNativeColor());
        // Solid fill has no background color and it's not preserved when the fill type is changed.
        // It is interesting that MS Word does the same
        msAssert.areNotEqual(fill.getBackColor(), DrColor.RED.toNativeColor());

        // Let's try again.
        // Pattern fill preserves background color.
        fill.setForeColor(DrColor.BLUE.toNativeColor());
        fill.setBackColor(DrColor.RED.toNativeColor());
        fill.patterned(PatternType.DARK_HORIZONTAL);
        msAssert.areEqual(fill.getForeColor(), DrColor.BLUE.toNativeColor());
        msAssert.areEqual(fill.getBackColor(), DrColor.RED.toNativeColor());
    }

    /// <summary>
    /// Checks if solid fill already preserves background color in VML.
    /// </summary>
    @Test
    public void testPatternedNotPatternFillVml() throws Exception
    {
        Document doc = open("Solid", ShapeMarkupLanguage.VML);
        Fill fill = getFill(doc);
        fill.setForeColor(DrColor.BLUE.toNativeColor());
        fill.setBackColor(DrColor.RED.toNativeColor());
        fill.patterned(PatternType.DARK_DOWNWARD_DIAGONAL);
        msAssert.areEqual(fill.getForeColor(), DrColor.BLUE.toNativeColor());
        // Solid fill already preserves background color.
        // MS Word has the same behaviour.
        msAssert.areEqual(fill.getBackColor(), DrColor.RED.toNativeColor());
    }

    /// <summary>
    /// Checks pattern setting to DML shape.
    /// </summary>
    @Test (dataProvider = "testShapePatternedDmlDataProvider")
    public void testShapePatternedDml(/*PatternType*/int patternType) throws Exception
    {
        Document doc = open("NoFill", ShapeMarkupLanguage.DML);

        Fill fill = getFill(doc);
        msAssert.areEqual(FillType.SOLID, fill.getFillType());

        fill.patterned(patternType);

        msAssert.areEqual(FillType.PATTERNED, fill.getFillType());
        msAssert.areEqual(patternType, fill.getPattern());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testShapePatternedDmlDataProvider")
	public static Object[][] testShapePatternedDmlDataProvider() throws Exception
	{
		return new Object[][]
		{
			{PatternType.PERCENT_5},
			{PatternType.PERCENT_10},
			{PatternType.PERCENT_25},
			{PatternType.PERCENT_30},
			{PatternType.PERCENT_40},
			{PatternType.PERCENT_50},
			{PatternType.PERCENT_60},
			{PatternType.PERCENT_70},
			{PatternType.PERCENT_80},
			{PatternType.PERCENT_90},
			{PatternType.CROSS},
			{PatternType.DARK_DOWNWARD_DIAGONAL},
			{PatternType.DARK_HORIZONTAL},
			{PatternType.DARK_UPWARD_DIAGONAL},
			{PatternType.DARK_VERTICAL},
			{PatternType.DASHED_DOWNWARD_DIAGONAL},
			{PatternType.DASHED_HORIZONTAL},
			{PatternType.DASHED_UPWARD_DIAGONAL},
			{PatternType.DASHED_VERTICAL},
			{PatternType.DIAGONAL_BRICK},
			{PatternType.DIAGONAL_CROSS},
			{PatternType.DIVOT},
			{PatternType.DOTTED_DIAMOND},
			{PatternType.DOTTED_GRID},
			{PatternType.DOWNWARD_DIAGONAL},
			{PatternType.HORIZONTAL},
			{PatternType.HORIZONTAL_BRICK},
			{PatternType.LARGE_CHECKER_BOARD},
			{PatternType.LARGE_CONFETTI},
			{PatternType.LARGE_GRID},
			{PatternType.LIGHT_DOWNWARD_DIAGONAL},
			{PatternType.LIGHT_HORIZONTAL},
			{PatternType.LIGHT_UPWARD_DIAGONAL},
			{PatternType.LIGHT_VERTICAL},
			{PatternType.NARROW_HORIZONTAL},
			{PatternType.NARROW_VERTICAL},
			{PatternType.OUTLINED_DIAMOND},
			{PatternType.PLAID},
			{PatternType.SHINGLE},
			{PatternType.SMALL_CHECKER_BOARD},
			{PatternType.SMALL_CONFETTI},
			{PatternType.SMALL_GRID},
			{PatternType.SOLID_DIAMOND},
			{PatternType.SPHERE},
			{PatternType.TRELLIS},
			{PatternType.UPWARD_DIAGONAL},
			{PatternType.VERTICAL},
			{PatternType.WAVE},
			{PatternType.WEAVE},
			{PatternType.WIDE_DOWNWARD_DIAGONAL},
			{PatternType.WIDE_UPWARD_DIAGONAL},
			{PatternType.ZIG_ZAG},
		};
	}




    /// <summary>
    /// Checks setting patterned default colors.
    /// </summary>
    @Test
    public void testPatternedDefaultColorsDml() throws Exception
    {
        Document doc = open("NoFill", ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);

        fill.patterned(PatternType.DARK_DOWNWARD_DIAGONAL);
        
        msAssert.areEqual(fill.getForeColor(), doc.getTheme().getColors().getAccent1());
        TestFxUtil.areEqualArgb(fill.getBackColor(), Color.WHITE);
    }

    /// <summary>
    /// Checks setting patterned default colors.
    /// </summary>
    @Test
    public void testPatternedDefaultColorsVml() throws Exception
    {
        Document doc = open("NoFill", ShapeMarkupLanguage.VML);
        Fill fill = getFill(doc);

        fill.patterned(PatternType.DARK_DOWNWARD_DIAGONAL);
       
        msAssert.areEqual(fill.getForeColor(), DrColor.BLACK.toNativeColor());
        TestFxUtil.areEqualArgb(fill.getBackColor(), Color.WHITE);
    }

    /// <summary>
    /// Checks applying pattern to different types of DML fill.
    /// </summary>
    @Test (dataProvider = "testPatternedFillDmlDataProvider")
    public void testPatternedFillDml(String fileName, int r, int g, int b) throws Exception
    {
        Document doc = open(fileName, ShapeMarkupLanguage.DML);

        Fill fill = getFill(doc);

        msAssert.areEqual(0.37, 0.01, fill.getTransparency());
        Color expectedColor = (r != -1)
            ? new Color((r), (g), (b))
            : msColor.Empty;
        msAssert.areEqual(expectedColor, fill.getForeColor());

        fill.patterned(PatternType.DARK_DOWNWARD_DIAGONAL, Color.BLUE, Color.RED);

        msAssert.areEqual(FillType.PATTERNED, fill.getFillType());
        msAssert.areEqual(PatternType.DARK_DOWNWARD_DIAGONAL, fill.getPattern());
        msAssert.areEqual(0.0, 0.01, fill.getTransparency());
        msAssert.areEqual(new Color((0), (0), (255), (255)), fill.getForeColor());
        msAssert.areEqual(new Color((255), (0), (0), (255)), fill.getBackColor());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testPatternedFillDmlDataProvider")
	public static Object[][] testPatternedFillDmlDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Solid",  255,  0,  0},
			{"OneColorGradient",  0,  128,  128},
			{"TwoColorGradient",  128,  0,  0},
			{"PresetGradient",  204,  204,  255},
			{"PresetTextured",  -1,  -1,  -1},
			{"UserPicture",  -1,  -1,  -1},
			{"UserTextured",  -1,  -1,  -1},
		};
	}

    /// <summary>
    /// Checks applying pattern to different types of VML fill.
    /// </summary>
    @Test (dataProvider = "testTexturedSolidFillVmlDataProvider")
    public void testTexturedSolidFillVml(String fileName, int r, int g, int b) throws Exception
    {
        Document doc = open(fileName, ShapeMarkupLanguage.VML);

        Fill fill = getFill(doc);

        msAssert.areEqual(0.37, 0.01, fill.getTransparency());
        msAssert.areEqual(new Color((r), (g), (b)), fill.getForeColor());

        fill.patterned(PatternType.DARK_DOWNWARD_DIAGONAL, Color.BLUE, Color.RED);

        msAssert.areEqual(FillType.PATTERNED, fill.getFillType());
        msAssert.areEqual(PatternType.DARK_DOWNWARD_DIAGONAL, fill.getPattern());
        msAssert.areEqual(0.37, 0.01, fill.getTransparency());
        msAssert.areEqual(new Color((0), (0), (255), (255)), fill.getForeColor());
        msAssert.areEqual(new Color((255), (0), (0), (255)), fill.getBackColor());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTexturedSolidFillVmlDataProvider")
	public static Object[][] testTexturedSolidFillVmlDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Solid",  255,  0,  0},
			{"OneColorGradient",  0,  128,  128},
			{"TwoColorGradient",  128,  0,  0},
			{"PresetGradient",  204,  204,  255},
			{"PresetTextured",  255,  255,  255},
			{"UserPicture",  255,  255,  255},
			{"UserTextured",  255,  255,  255},
		};
	}

    /// <summary>
    /// Checks applying pattern to Font fill.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QPattern cannot be applied to Font.\\E")
    public void testTextPatterned() throws Exception
    {
        Document doc = open("TextNoFill", ShapeMarkupLanguage.VML);

        Fill fill = getFill(doc);
        fill.patterned(PatternType.PERCENT_10, Color.BLUE, Color.BLUE);
    }

    /// <summary>
    /// Checks obtaining pattern from GroupShape fill.
    /// </summary>
    @Test (dataProvider = "testPresetTextureMixedDataProvider")
    public void testPresetTextureMixed(/*ShapeMarkupLanguage*/byte markupLanguage) throws Exception
    {
        Document doc = open("FillMixed", markupLanguage);

        GroupShape groupShape = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true);
        Fill fill = groupShape.getFill();

        msAssert.areEqual(FillType.SOLID, fill.getFillType());
        msAssert.areEqual(PatternType.NONE, fill.getPattern());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testPresetTextureMixedDataProvider")
	public static Object[][] testPresetTextureMixedDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ShapeMarkupLanguage.VML},
			{ShapeMarkupLanguage.DML},
		};
	}

    /// <summary>
    /// Checks obtaining pattern type from fill which is not FillType.Patterned.
    /// </summary>
    /// <remarks>
    /// Mimics MS Word behavior.
    /// </remarks>
    @Test (dataProvider = "testPresetTextureNoneAsDefaultDataProvider")
    public void testPresetTextureNoneAsDefault(String fileName) throws Exception
    {
        Document doc = open(fileName, ShapeMarkupLanguage.VML);

        Fill fill = getFill(doc);

        msAssert.areEqual(PatternType.NONE, fill.getPattern());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testPresetTextureNoneAsDefaultDataProvider")
	public static Object[][] testPresetTextureNoneAsDefaultDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"NoFill"},
			{"Solid"},
			{"OneColorGradient"},
			{"TwoColorGradient"},
			{"PresetGradient"},
			{"PresetTextured"},
			{"UserPicture"},
			{"UserTextured"},
		};
	}

    /// <summary>
    /// Checks setting PatternType.None.
    /// </summary>
    /// <remarks>
    /// Mimics MS Word behavior.
    /// </remarks>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QPatternType.None cannot be applied directly.\\E")
    public void testPatternNone() throws Exception
    {
        Document doc = open("NoFill", ShapeMarkupLanguage.VML);

        Fill fill = getFill(doc);
        fill.patterned(PatternType.NONE, Color.BLUE, Color.BLUE);
    }
}

