// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/04/2021 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import org.testng.annotations.DataProvider;


/// <summary>
/// The class for testing PresetTextured() method of Fill object.
/// </summary>
@Test
public class TestFillPresetTextured extends TestFillFormatBase
{
    /// <summary>
    /// Checks attribute values when applying different PresetTexture to VML shape.
    /// </summary>
    /// <remarks>
    /// Mimics MS Word behavior.
    /// </remarks>
    @Test (dataProvider = "testShapePresetTexturedVmlDataProvider")
    public void testShapePresetTexturedVml(/*PresetTexture*/int presetTexture, String textureTitle, int r, int g, int b) throws Exception
    {
        Document doc = open("NoFill", ShapeMarkupLanguage.VML);

        Shape shape = (Shape)getFirstFillable(doc);
        Fill fill = shape.getFill();

        fill.presetTextured(presetTexture);

        msAssert.areEqual(FillType.TEXTURED, fill.getFillType());
        msAssert.areEqual(presetTexture, fill.getPresetTexture());
        if (r == -1)
            msAssert.isNull(shape.getShapePr().get(ShapeAttr.FILL_COLOR));
        else
            msAssert.areEqual(new Color((r), (g), (b)), fill.getForeColor());
        msAssert.areEqual(textureTitle, shape.getShapePr().get(ShapeAttr.FILL_BLIP_NAME));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testShapePresetTexturedVmlDataProvider")
	public static Object[][] testShapePresetTexturedVmlDataProvider() throws Exception
	{
		return new Object[][]
		{
			{PresetTexture.BLUE_TISSUE_PAPER,  "Blue tissue paper",  204,  236,  255},
			{PresetTexture.BOUQUET,  "Bouquet",  204,  204,  255},
			{PresetTexture.BROWN_MARBLE,  "Brown marble",  102,  51,  0},
			{PresetTexture.CANVAS,  "Canvas",  255,  255,  204},
			{PresetTexture.CORK,  "Cork",  153,  102,  0},
			{PresetTexture.DENIM,  "Denim",  102,  153,  255},
			{PresetTexture.FISH_FOSSIL,  "Fish fossil",  255,  204,  153},
			{PresetTexture.OAK,  "Oak",  255,  204,  153},
			{PresetTexture.PAPER_BAG,  "Paper bag",  255,  204,  153},
			{PresetTexture.PAPYRUS,  "Papyrus",  255,  204,  153},
			{PresetTexture.GRANITE,  "Granite",  221,  221,  221},
			{PresetTexture.GREEN_MARBLE,  "Green marble",  0,  102,  0},
			{PresetTexture.MEDIUM_WOOD,  "Medium wood",  153,  102,  51},
			{PresetTexture.NEWSPRINT,  "Newsprint",  248,  248,  248},
			{PresetTexture.PARCHMENT,  "Parchment",  255,  255,  204},
			{PresetTexture.PINK_TISSUE_PAPER,  "Pink tissue paper",  255,  204,  204},
			{PresetTexture.PURPLE_MESH,  "Purple mesh",  153,  0,  204},
			{PresetTexture.RECYCLED_PAPER,  "Recycled paper",  -1,  -1,  -1},
			{PresetTexture.WHITE_MARBLE,  "White marble",  -1,  -1,  -1},
			{PresetTexture.SAND,  "Sand",  192,  192,  192},
			{PresetTexture.STATIONERY,  "Stationery",  255,  255,  204},
			{PresetTexture.WALNUT,  "Walnut",  102,  51,  0},
			{PresetTexture.WATER_DROPLETS,  "Water droplets",  204,  255,  255},
			{PresetTexture.WOVEN_MAT,  "Woven mat",  255,  204,  102},
		};
	}

    /// <summary>
    /// Checks texture setting when applying different PresetTexture to DML shape.
    /// </summary>
    @Test (dataProvider = "testShapePresetTexturedDmlDataProvider")
    public void testShapePresetTexturedDml(/*PresetTexture*/int presetTexture) throws Exception
    {
        Document doc = open("NoFill", ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);
        msAssert.areEqual(FillType.SOLID, fill.getFillType());

        fill.presetTextured(presetTexture);

        msAssert.areEqual(FillType.TEXTURED, fill.getFillType());
        msAssert.areEqual(presetTexture, fill.getPresetTexture());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testShapePresetTexturedDmlDataProvider")
	public static Object[][] testShapePresetTexturedDmlDataProvider() throws Exception
	{
		return new Object[][]
		{
			{PresetTexture.BLUE_TISSUE_PAPER},
			{PresetTexture.BOUQUET},
			{PresetTexture.BROWN_MARBLE},
			{PresetTexture.CANVAS},
			{PresetTexture.CORK},
			{PresetTexture.DENIM},
			{PresetTexture.FISH_FOSSIL},
			{PresetTexture.GRANITE},
			{PresetTexture.GREEN_MARBLE},
			{PresetTexture.MEDIUM_WOOD},
			{PresetTexture.NEWSPRINT},
			{PresetTexture.OAK},
			{PresetTexture.PAPER_BAG},
			{PresetTexture.PAPYRUS},
			{PresetTexture.PARCHMENT},
			{PresetTexture.PURPLE_MESH},
			{PresetTexture.RECYCLED_PAPER},
			{PresetTexture.SAND},
			{PresetTexture.STATIONERY},
			{PresetTexture.WALNUT},
			{PresetTexture.WATER_DROPLETS},
			{PresetTexture.WHITE_MARBLE},
			{PresetTexture.WOVEN_MAT},
		};
	}

    /// <summary>
    /// Checks determining texture from source documents.
    /// </summary>
    @Test (dataProvider = "testShapeTextureDataProvider")
    public void testShapeTexture(String fileName, /*PresetTexture*/int presetTexture) throws Exception
    {
        Document dmlDoc = open(fileName, ShapeMarkupLanguage.DML);
        Fill fill = getFill(dmlDoc);
        msAssert.areEqual(FillType.TEXTURED, fill.getFillType());
        msAssert.areEqual(presetTexture, fill.getPresetTexture());

        Document vmlDoc = open(fileName, ShapeMarkupLanguage.VML);
        fill = getFill(vmlDoc);
        msAssert.areEqual(FillType.TEXTURED, fill.getFillType());
        msAssert.areEqual(presetTexture, fill.getPresetTexture());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testShapeTextureDataProvider")
	public static Object[][] testShapeTextureDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"BlueTissuePaper",  PresetTexture.BLUE_TISSUE_PAPER},
			{"Bouquet",  PresetTexture.BOUQUET},
			{"BrownMarble",  PresetTexture.BROWN_MARBLE},
			{"Canvas",  PresetTexture.CANVAS},
			{"Cork",  PresetTexture.CORK},
			{"Denim",  PresetTexture.DENIM},
			{"FishFossil",  PresetTexture.FISH_FOSSIL},
			{"Oak",  PresetTexture.OAK},
			{"PaperBag",  PresetTexture.PAPER_BAG},
			{"Papyrus",  PresetTexture.PAPYRUS},
			{"Granite",  PresetTexture.GRANITE},
			{"GreenMarble",  PresetTexture.GREEN_MARBLE},
			{"MediumWood",  PresetTexture.MEDIUM_WOOD},
			{"Newsprint",  PresetTexture.NEWSPRINT},
			{"Parchment",  PresetTexture.PARCHMENT},
			{"PinkTissuePaper",  PresetTexture.PINK_TISSUE_PAPER},
			{"PurpleMesh",  PresetTexture.PURPLE_MESH},
			{"RecycledPaper",  PresetTexture.RECYCLED_PAPER},
			{"WhiteMarble",  PresetTexture.WHITE_MARBLE},
			{"Sand",  PresetTexture.SAND},
			{"Stationery",  PresetTexture.STATIONERY},
			{"Walnut",  PresetTexture.WALNUT},
			{"WaterDroplets",  PresetTexture.WATER_DROPLETS},
			{"WovenMat",  PresetTexture.WOVEN_MAT},
		};
	}

    /// <summary>
    /// Checks applying texture to different types of Dml fill.
    /// </summary>
    @Test (dataProvider = "testTexturedSolidFillDataProvider")
    public void testTexturedSolidFill(String fileName) throws Exception
    {
        Document doc = open(fileName, ShapeMarkupLanguage.DML);

        Shape shape = (Shape)getFirstFillable(doc);
        Fill fill = shape.getFill();
        msAssert.areEqual(0.37, fill.getTransparency());

        fill.presetTextured(PresetTexture.FISH_FOSSIL);

        msAssert.areEqual(FillType.TEXTURED, fill.getFillType());
        msAssert.areEqual(PresetTexture.FISH_FOSSIL, fill.getPresetTexture());
        msAssert.areEqual(0, fill.getTransparency());

        DmlBlipFill dmlBlipFill = (DmlBlipFill)shape.getDmlShape().getFill();
        DmlBlipFillTile dmlBlipFillTile = (DmlBlipFillTile)dmlBlipFill.getBlipFillMode();
        msAssert.areEqual(DmlRectangleAlignment.TOP_LEFT, dmlBlipFillTile.getAlignment());
        msAssert.areEqual(DmlTileFlipMode.NONE, dmlBlipFillTile.getTileFlipMode());
        msAssert.areEqual(0, dmlBlipFillTile.getHorizontalOffset());
        msAssert.areEqual(0, dmlBlipFillTile.getVerticalOffset());
        msAssert.areEqual(1.0, dmlBlipFillTile.getHorizontalRatio());
        msAssert.areEqual(1.0, dmlBlipFillTile.getVerticalRatio());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTexturedSolidFillDataProvider")
	public static Object[][] testTexturedSolidFillDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Solid"},
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
    /// Checks applying texture to different types of Vml fill.
    /// </summary>
    @Test (dataProvider = "testTexturedSolidFillVmlDataProvider")
    public void testTexturedSolidFillVml(String fileName, int r, int g, int b) throws Exception
    {
        Document doc = open(fileName, ShapeMarkupLanguage.VML);

        Fill fill = getFill(doc);

        msAssert.areEqual(0.37, 0.01, fill.getTransparency());
        msAssert.areEqual(new Color((r), (g), (b)), fill.getForeColor());

        fill.presetTextured(PresetTexture.FISH_FOSSIL);

        msAssert.areEqual(FillType.TEXTURED, fill.getFillType());
        msAssert.areEqual(PresetTexture.FISH_FOSSIL, fill.getPresetTexture());
        msAssert.areEqual(0.37, 0.01, fill.getTransparency());
        msAssert.areEqual(new Color((255), (204), (153)), fill.getForeColor());
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
			{"Patterned",  128,  0,  0},
			{"PresetTextured",  255,  255,  255},
			{"UserPicture",  255,  255,  255},
			{"UserTextured",  255,  255,  255},
		};
	}

    /// <summary>
    /// Checks applying texture to Font fill.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QPreset texture cannot be applied to Font.\\E")
    public void testTextPresetTextured() throws Exception
    {
        Document doc = open("TextNoFill", ShapeMarkupLanguage.VML);

        Fill fill = getFill(doc);
        fill.presetTextured(PresetTexture.FISH_FOSSIL);
    }

    /// <summary>
    /// Checks obtaining texture from Font fill.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QPreset texture cannot be applied to Font.\\E")
    public void testTextGetPresetTexture() throws Exception
    {
        Document doc = open("TextNoFill", ShapeMarkupLanguage.VML);

        Fill fill = getFill(doc);
        Assert.fail(PresetTexture.toString(fill.getPresetTexture()));
    }

    /// <summary>
    /// Checks obtaining texture from GroupShape fill.
    /// </summary>
    @Test (dataProvider = "testPresetTextureMixedDataProvider")
    public void testPresetTextureMixed(/*ShapeMarkupLanguage*/byte markupLanguage) throws Exception
    {
        Document doc = open("FillMixed", markupLanguage);

        GroupShape groupShape = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true);
        Fill fill = groupShape.getFill();

        msAssert.areEqual(FillType.SOLID, fill.getFillType());
        msAssert.areEqual(PresetTexture.NONE, fill.getPresetTexture());
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
    /// Checks obtaining texture from fill which is not FillType.Textured.
    /// </summary>
    /// <remarks>
    /// Mimics MS Word behavior.
    /// </remarks>
    @Test (dataProvider = "testPresetTextureNoneAsDefaultDataProvider")
    public void testPresetTextureNoneAsDefault(String fileName) throws Exception
    {
        Document doc = open(fileName, ShapeMarkupLanguage.VML);

        Fill fill = getFill(doc);

        msAssert.areEqual(PresetTexture.NONE, fill.getPresetTexture());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testPresetTextureNoneAsDefaultDataProvider")
	public static Object[][] testPresetTextureNoneAsDefaultDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Solid"},
			{"OneColorGradient"},
			{"TwoColorGradient"},
			{"PresetGradient"},
			{"Patterned"},
			{"UserPicture"},
			{"UserTextured"},
		};
	}

    /// <summary>
    /// Checks setting PresetTexture.None.
    /// </summary>
    /// <remarks>
    /// Mimics MS Word behavior.
    /// </remarks>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QPresetTexture.None cannot be applied directly.\\E")
    public void testPresetTexturedMixed() throws Exception
    {
        Document doc = open("NoFill", ShapeMarkupLanguage.VML);

        Fill fill = getFill(doc);
        fill.presetTextured(PresetTexture.NONE);
    }
}

