// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/04/2021 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.Drawing2D.HatchStyle;
import com.aspose.HashUtil;
import com.aspose.DrColor;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.msMath;
import com.aspose.ms.System.MidpointRounding;
import com.aspose.ms.System.IO.Stream;
import com.aspose.StreamUtil;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Helper class containing methods used for fill formatting.
/// </summary>
/*static*/ class FillUtil
{
	/* Simulation of static class by using private constructor */
	private FillUtil()
	{}

    /// <summary>
    /// Returns <see cref="PresetTexture"/> according to the specified hash value.
    /// </summary>
    static /*PresetTexture*/int getTextureByHash(int textureHash)
    {
        /*unchecked*/
        {
            switch (textureHash)
            {
                case 0x4b6f30f3:
                    return PresetTexture.BLUE_TISSUE_PAPER;
                case 0x19f95cce:
                    return PresetTexture.BOUQUET;
                case 0x6060b43e:
                    return PresetTexture.BROWN_MARBLE;
                case 0x749f777e:
                    return PresetTexture.CANVAS;
                case 0x7d93c35b:
                    return PresetTexture.CORK;
                case 0x49d6b1d0:
                    return PresetTexture.DENIM;
                case (int)(int) 0xc4befeeaL:
                    return PresetTexture.FISH_FOSSIL;
                case 0x520dad48:
                    return PresetTexture.GRANITE;
                case 0x229b9757:
                    return PresetTexture.GREEN_MARBLE;
                case 0x706c7a5:
                    return PresetTexture.MEDIUM_WOOD;
                case (int)(int) 0xf7b9214cL:
                    return PresetTexture.NEWSPRINT;
                case 0x7193b784:
                    return PresetTexture.OAK;
                case (int)(int) 0xac1bca2cL:
                    return PresetTexture.PAPER_BAG;
                case (int)(int) 0x82bdb4e2L:
                    return PresetTexture.PAPYRUS;
                case (int)(int) 0xc64275c1L:
                    return PresetTexture.PARCHMENT;
                case (int)(int) 0xe58841faL:
                    return PresetTexture.PINK_TISSUE_PAPER;
                case 0xa02b859:
                    return PresetTexture.PURPLE_MESH;
                case 0x514b7f5a:
                    return PresetTexture.RECYCLED_PAPER;
                case (int)(int) 0xb28a8bc0L:
                    return PresetTexture.SAND;
                case 0x2bb08a79:
                    return PresetTexture.STATIONERY;
                case (int)(int) 0xfaedF5e7L:
                    return PresetTexture.WALNUT;
                case 0x7af8100d:
                    return PresetTexture.WATER_DROPLETS;
                case (int)(int) 0xaa54ea96L:
                    return PresetTexture.WHITE_MARBLE;
                case (int)(int) 0xd993b560L:
                    return PresetTexture.WOVEN_MAT;
                default:
                    return PresetTexture.NONE;
            }
        }
    }

    /// <summary>
    /// Returns the texture name according to the specified <see cref="PresetTexture"/>.
    /// </summary>
    static String getTextureName(/*PresetTexture*/int presetTexture)
    {
        switch (presetTexture)
        {
            case PresetTexture.BLUE_TISSUE_PAPER:
                return "BlueTissuePaper";
            case PresetTexture.BOUQUET:
                return "Bouquet";
            case PresetTexture.BROWN_MARBLE:
                return "BrownMarble";
            case PresetTexture.CANVAS:
                return "Canvas";
            case PresetTexture.CORK:
                return "Cork";
            case PresetTexture.DENIM:
                return "Denim";
            case PresetTexture.FISH_FOSSIL:
                return "FishFossil";
            case PresetTexture.GRANITE:
                return "Granite";
            case PresetTexture.GREEN_MARBLE:
                return "GreenMarble";
            case PresetTexture.MEDIUM_WOOD:
                return "MediumWood";
            case PresetTexture.NEWSPRINT:
                return "Newsprint";
            case PresetTexture.OAK:
                return "Oak";
            case PresetTexture.PAPER_BAG:
                return "PaperBag";
            case PresetTexture.PAPYRUS:
                return "Papyrus";
            case PresetTexture.PARCHMENT:
                return "Parchment";
            case PresetTexture.PINK_TISSUE_PAPER:
                return "PinkTissuePaper";
            case PresetTexture.PURPLE_MESH:
                return "PurpleMesh";
            case PresetTexture.RECYCLED_PAPER:
                return "RecycledPaper";
            case PresetTexture.SAND:
                return "Sand";
            case PresetTexture.STATIONERY:
                return "Stationery";
            case PresetTexture.WALNUT:
                return "Walnut";
            case PresetTexture.WATER_DROPLETS:
                return "WaterDroplets";
            case PresetTexture.WHITE_MARBLE:
                return "WhiteMarble";
            case PresetTexture.WOVEN_MAT:
                return "WovenMat";
            default:
                throw new IllegalArgumentException("The specified texture is out of range." + "\r\nParameter name: " + "presetTexture");
        }
    }

    /// <summary>
    /// Returns the pattern name according to the specified <see cref="PatternType"/>.
    /// </summary>
    static String getPatternName(/*PatternType*/int patternType)
    {
        switch (patternType)
        {
            case PatternType.PERCENT_5:
                return "5Percent";
            case PatternType.PERCENT_10:
                return "10Percent";
            case PatternType.PERCENT_20:
                return "20Percent";
            case PatternType.PERCENT_25:
                return "25Percent";
            case PatternType.PERCENT_30:
                return "30Percent";
            case PatternType.PERCENT_40:
                return "40Percent";
            case PatternType.PERCENT_50:
                return "50Percent";
            case PatternType.PERCENT_60:
                return "60Percent";
            case PatternType.PERCENT_70:
                return "70Percent";
            case PatternType.PERCENT_75:
                return "75Percent";
            case PatternType.PERCENT_80:
                return "80Percent";
            case PatternType.PERCENT_90:
                return "90Percent";
            case PatternType.CROSS:
                return "Cross";
            case PatternType.DARK_DOWNWARD_DIAGONAL:
                return "DarkDownwardDiagonal";
            case PatternType.DARK_HORIZONTAL:
                return "DarkHorizontal";
            case PatternType.DARK_UPWARD_DIAGONAL:
                return "DarkUpwardDiagonal";
            case PatternType.DARK_VERTICAL:
                return "DarkVertical";
            case PatternType.DASHED_DOWNWARD_DIAGONAL:
                return "DashedDownwardDiagonal";
            case PatternType.DASHED_HORIZONTAL:
                return "DashedHorizontal";
            case PatternType.DASHED_UPWARD_DIAGONAL:
                return "DashedUpwardDiagonal";
            case PatternType.DASHED_VERTICAL:
                return "DashedVertical";
            case PatternType.DIAGONAL_BRICK:
                return "DiagonalBrick";
            case PatternType.DIAGONAL_CROSS:
                return "DiagonalCross";
            case PatternType.DIVOT:
                return "Divot";
            case PatternType.DOTTED_DIAMOND:
                return "DottedDiamond";
            case PatternType.DOTTED_GRID:
                return "DottedGrid";
            case PatternType.DOWNWARD_DIAGONAL:
                return "DownwardDiagonal";
            case PatternType.HORIZONTAL:
                return "Horizontal";
            case PatternType.HORIZONTAL_BRICK:
                return "HorizontalBrick";
            case PatternType.LARGE_CHECKER_BOARD:
                return "LargeCheckerBoard";
            case PatternType.LARGE_CONFETTI:
                return "LargeConfetti";
            case PatternType.LARGE_GRID:
                return "LargeGrid";
            case PatternType.LIGHT_DOWNWARD_DIAGONAL:
                return "LightDownwardDiagonal";
            case PatternType.LIGHT_HORIZONTAL:
                return "LightHorizontal";
            case PatternType.LIGHT_UPWARD_DIAGONAL:
                return "LightUpwardDiagonal";
            case PatternType.LIGHT_VERTICAL:
                return "LightVertical";
            case PatternType.NARROW_HORIZONTAL:
                return "NarrowHorizontal";
            case PatternType.NARROW_VERTICAL:
                return "NarrowVertical";
            case PatternType.OUTLINED_DIAMOND:
                return "OutlinedDiamond";
            case PatternType.PLAID:
                return "Plaid";
            case PatternType.SHINGLE:
                return "Shingle";
            case PatternType.SMALL_CHECKER_BOARD:
                return "SmallCheckerBoard";
            case PatternType.SMALL_CONFETTI:
                return "SmallConfetti";
            case PatternType.SMALL_GRID:
                return "SmallGrid";
            case PatternType.SOLID_DIAMOND:
                return "SolidDiamond";
            case PatternType.SPHERE:
                return "Sphere";
            case PatternType.TRELLIS:
                return "Trellis";
            case PatternType.UPWARD_DIAGONAL:
                return "UpwardDiagonal";
            case PatternType.VERTICAL:
                return "Vertical";
            case PatternType.WAVE:
                return "Wave";
            case PatternType.WEAVE:
                return "Weave";
            case PatternType.WIDE_DOWNWARD_DIAGONAL:
                return "WideDownwardDiagonal";
            case PatternType.WIDE_UPWARD_DIAGONAL:
                return "WideUpwardDiagonal";
            case PatternType.ZIG_ZAG:
                return "ZigZag";
            default:
                throw new IllegalArgumentException("The specified pattern is out of range." + "\r\nParameter name: " + "patternType");
        }
    }

    /// <summary>
    /// Returns <see cref="PatternType"/> according to the specified image data.
    /// </summary>
    static /*PatternType*/int getPatternType(byte[] binData) throws Exception
    {
        /*HatchStyle*/int style = com.aspose.words.VmlEnum.getHatchStyle(binData);
        if ((style != HatchStyle.HORIZONTAL) && (style != HatchStyle.VERTICAL) &&
            // .NET Standard code cannot distinguish WideDownwardDiagonal vs DownwardDiagonal and WideUpwardDiagonal vs UpwardDiagonal.
            // The reason seems to be BitonalConverter, it is hard to tune it to work like in .NET.
            // So fall back to the code below to determine the pattern type by binData hash.
            (style != HatchStyle.WIDE_UPWARD_DIAGONAL) && (style != HatchStyle.WIDE_DOWNWARD_DIAGONAL) &&
            (style != HatchStyle.BACKWARD_DIAGONAL) && (style != HatchStyle.FORWARD_DIAGONAL) &&
            (style != HatchStyle.DIAGONAL_CROSS))
            return hatchStyleToPatternType(style);

        int textureHash = HashUtil.getSHA512Hash(binData).hashCode();
        /*unchecked*/
        {
            switch (textureHash)
            {
                case (int)(int) 0xa58e7134L:
                    return PatternType.HORIZONTAL;
                case (int)(int) 0x865eeb88L:
                    return PatternType.VERTICAL;
                case (int)(int) 0xc23f7cc5L:
                    return PatternType.DOWNWARD_DIAGONAL;
                case 0x14226ec2:
                    return PatternType.UPWARD_DIAGONAL;
                case 0x23bb9fa8:
                    return PatternType.DIAGONAL_CROSS;
                // Not sure why, but in TestShapePatternedVml and in TestShapePatternedVml the same patterns has different hashes.
                case (int)(int) 0x90780831L:
                case 0xb0cc343:
                    return PatternType.WIDE_UPWARD_DIAGONAL;
                case (int)(int) 0xe2eb1a93L:
                case (int)(int) 0xacde8b53L:
                    return PatternType.WIDE_DOWNWARD_DIAGONAL;
                default:
                    return PatternType.NONE;
            }
        }
    }

    /// <summary>
    /// Returns the texture title according to the specified <see cref="PresetTexture"/>.
    /// </summary>
    static String getTextureTitle(/*PresetTexture*/int presetTexture)
    {
        switch (presetTexture)
        {
            case PresetTexture.BLUE_TISSUE_PAPER:
                return "Blue tissue paper";
            case PresetTexture.BOUQUET:
                return "Bouquet";
            case PresetTexture.BROWN_MARBLE:
                return "Brown marble";
            case PresetTexture.CANVAS:
                return "Canvas";
            case PresetTexture.CORK:
                return "Cork";
            case PresetTexture.DENIM:
                return "Denim";
            case PresetTexture.FISH_FOSSIL:
                return "Fish fossil";
            case PresetTexture.GRANITE:
                return "Granite";
            case PresetTexture.GREEN_MARBLE:
                return "Green marble";
            case PresetTexture.MEDIUM_WOOD:
                return "Medium wood";
            case PresetTexture.NEWSPRINT:
                return "Newsprint";
            case PresetTexture.OAK:
                return "Oak";
            case PresetTexture.PAPER_BAG:
                return "Paper bag";
            case PresetTexture.PAPYRUS:
                return "Papyrus";
            case PresetTexture.PARCHMENT:
                return "Parchment";
            case PresetTexture.PINK_TISSUE_PAPER:
                return "Pink tissue paper";
            case PresetTexture.PURPLE_MESH:
                return "Purple mesh";
            case PresetTexture.RECYCLED_PAPER:
                return "Recycled paper";
            case PresetTexture.SAND:
                return "Sand";
            case PresetTexture.STATIONERY:
                return "Stationery";
            case PresetTexture.WALNUT:
                return "Walnut";
            case PresetTexture.WATER_DROPLETS:
                return "Water droplets";
            case PresetTexture.WHITE_MARBLE:
                return "White marble";
            case PresetTexture.WOVEN_MAT:
                return "Woven mat";
            default:
                throw new IllegalArgumentException("The specified texture is out of range." + "\r\nParameter name: " + "presetTexture");
        }
    }

    /// <summary>
    /// Returns the value <see cref="DrColor"/> according to the specified <see cref="PresetTexture"/>.
    /// </summary>
    static DrColor getFillColor(/*PresetTexture*/int presetTexture)
    {
        switch (presetTexture)
        {
            case PresetTexture.BLUE_TISSUE_PAPER:
                return DrColor.fromArgb(204, 236, 255);
            case PresetTexture.BOUQUET:
                return DrColor.fromArgb(204, 204, 255);
            case PresetTexture.BROWN_MARBLE:
                return DrColor.fromArgb(102, 51, 0);
            case PresetTexture.CANVAS:
                return DrColor.fromArgb(255, 255, 204);
            case PresetTexture.CORK:
                return DrColor.fromArgb(153, 102, 0);
            case PresetTexture.DENIM:
                return DrColor.fromArgb(102, 153, 255);
            case PresetTexture.FISH_FOSSIL:
            case PresetTexture.OAK:
            case PresetTexture.PAPER_BAG:
            case PresetTexture.PAPYRUS:
                return DrColor.fromArgb(255, 204, 153);
            case PresetTexture.GRANITE:
                return DrColor.fromArgb(221, 221, 221);
            case PresetTexture.GREEN_MARBLE:
                return DrColor.fromArgb(0, 102, 0);
            case PresetTexture.MEDIUM_WOOD:
                return DrColor.fromArgb(153, 102, 51);
            case PresetTexture.NEWSPRINT:
                return DrColor.fromArgb(248, 248, 248);
            case PresetTexture.PARCHMENT:
                return DrColor.fromArgb(255, 255, 204);
            case PresetTexture.PINK_TISSUE_PAPER:
                return DrColor.fromArgb(255, 204, 204);
            case PresetTexture.PURPLE_MESH:
                return DrColor.fromArgb(153, 0, 204);
            case PresetTexture.RECYCLED_PAPER:
            case PresetTexture.WHITE_MARBLE:
                return null;
            case PresetTexture.SAND:
                return DrColor.SILVER;
            case PresetTexture.STATIONERY:
                return DrColor.fromArgb(255, 255, 204);
            case PresetTexture.WALNUT:
                return DrColor.fromArgb(102, 51, 0);
            case PresetTexture.WATER_DROPLETS:
                return DrColor.fromArgb(204, 255, 255);
            case PresetTexture.WOVEN_MAT:
                return DrColor.fromArgb(255, 204, 102);
            default:
                return null;
        }
    }

    /// <summary>
    /// Returns the texture binary data according to the specified texture name.
    /// </summary>
    static byte[] getTextureBytes(String textureName)
    {
        byte[] bytes = getResourceBytesCache().get(textureName);
        if ((bytes == null) || (bytes.length == 0))
        {
            bytes = loadResource(msString.format("Aspose.Words.Resources.PresetTextures.{0}.jpeg", textureName));
            getResourceBytesCache().set(textureName, bytes);
        }

        return bytes;
    }

    /// <summary>
    /// Returns the pattern binary data according to the specified pattern name.
    /// </summary>
    static byte[] getPatternBytes(String patternName)
    {
        byte[] bytes = getResourceBytesCache().get(patternName);
        if ((bytes == null) || (bytes.length == 0))
        {
            bytes = loadResource(msString.format("Aspose.Words.Resources.Patterns.{0}.png", patternName));
            getResourceBytesCache().set(patternName, bytes);
        }

        return bytes;
    }

    /// <summary>
    /// Returns PatternType according to the specified HatchStyle.
    /// </summary>
    /// <remarks>
    /// HatchStyle.Cross processed as the special case. HatchStyle.Min and HatchStyle.Max not currently used.
    /// </remarks>
    static /*PatternType*/int hatchStyleToPatternType(/*HatchStyle*/int value)
    {
        switch (value)
        {
            case HatchStyle.BACKWARD_DIAGONAL:
                return PatternType.DOWNWARD_DIAGONAL;
            case HatchStyle.FORWARD_DIAGONAL:
                return PatternType.UPWARD_DIAGONAL;
            case HatchStyle.LARGE_GRID:
                return PatternType.LARGE_GRID;
            case HatchStyle.HORIZONTAL:
                return PatternType.HORIZONTAL;
            case HatchStyle.VERTICAL:
                return PatternType.VERTICAL;
            case HatchStyle.DIAGONAL_CROSS:
                return PatternType.DIAGONAL_CROSS;
            case HatchStyle.PERCENT_05:
                return PatternType.PERCENT_5;
            case HatchStyle.PERCENT_10:
                return PatternType.PERCENT_10;
            case HatchStyle.PERCENT_20:
                return PatternType.PERCENT_20;
            case HatchStyle.PERCENT_25:
                return PatternType.PERCENT_25;
            case HatchStyle.PERCENT_30:
                return PatternType.PERCENT_30;
            case HatchStyle.PERCENT_40:
                return PatternType.PERCENT_40;
            case HatchStyle.PERCENT_50:
                return PatternType.PERCENT_50;
            case HatchStyle.PERCENT_60:
                return PatternType.PERCENT_60;
            case HatchStyle.PERCENT_70:
                return PatternType.PERCENT_70;
            case HatchStyle.PERCENT_75:
                return PatternType.PERCENT_75;
            case HatchStyle.PERCENT_80:
                return PatternType.PERCENT_80;
            case HatchStyle.PERCENT_90:
                return PatternType.PERCENT_90;
            case HatchStyle.LIGHT_DOWNWARD_DIAGONAL:
                return PatternType.LIGHT_DOWNWARD_DIAGONAL;
            case HatchStyle.LIGHT_UPWARD_DIAGONAL:
                return PatternType.LIGHT_UPWARD_DIAGONAL;
            case HatchStyle.DARK_DOWNWARD_DIAGONAL:
                return PatternType.DARK_DOWNWARD_DIAGONAL;
            case HatchStyle.DARK_UPWARD_DIAGONAL:
                return PatternType.DARK_UPWARD_DIAGONAL;
            case HatchStyle.WIDE_DOWNWARD_DIAGONAL:
                return PatternType.WIDE_DOWNWARD_DIAGONAL;
            case HatchStyle.WIDE_UPWARD_DIAGONAL:
                return PatternType.WIDE_UPWARD_DIAGONAL;
            case HatchStyle.LIGHT_VERTICAL:
                return PatternType.LIGHT_VERTICAL;
            case HatchStyle.LIGHT_HORIZONTAL:
                return PatternType.LIGHT_HORIZONTAL;
            case HatchStyle.NARROW_VERTICAL:
                return PatternType.NARROW_VERTICAL;
            case HatchStyle.NARROW_HORIZONTAL:
                return PatternType.NARROW_HORIZONTAL;
            case HatchStyle.DARK_VERTICAL:
                return PatternType.DARK_VERTICAL;
            case HatchStyle.DARK_HORIZONTAL:
                return PatternType.DARK_HORIZONTAL;
            case HatchStyle.DASHED_DOWNWARD_DIAGONAL:
                return PatternType.DASHED_DOWNWARD_DIAGONAL;
            case HatchStyle.DASHED_UPWARD_DIAGONAL:
                return PatternType.DASHED_UPWARD_DIAGONAL;
            case HatchStyle.DASHED_HORIZONTAL:
                return PatternType.DASHED_HORIZONTAL;
            case HatchStyle.DASHED_VERTICAL:
                return PatternType.DASHED_VERTICAL;
            case HatchStyle.SMALL_CONFETTI:
                return PatternType.SMALL_CONFETTI;
            case HatchStyle.LARGE_CONFETTI:
                return PatternType.LARGE_CONFETTI;
            case HatchStyle.ZIG_ZAG:
                return PatternType.ZIG_ZAG;
            case HatchStyle.WAVE:
                return PatternType.WAVE;
            case HatchStyle.DIAGONAL_BRICK:
                return PatternType.DIAGONAL_BRICK;
            case HatchStyle.HORIZONTAL_BRICK:
                return PatternType.HORIZONTAL_BRICK;
            case HatchStyle.WEAVE:
                return PatternType.WEAVE;
            case HatchStyle.PLAID:
                return PatternType.PLAID;
            case HatchStyle.DIVOT:
                return PatternType.DIVOT;
            case HatchStyle.DOTTED_GRID:
                return PatternType.DOTTED_GRID;
            case HatchStyle.DOTTED_DIAMOND:
                return PatternType.DOTTED_DIAMOND;
            case HatchStyle.SHINGLE:
                return PatternType.SHINGLE;
            case HatchStyle.TRELLIS:
                return PatternType.TRELLIS;
            case HatchStyle.SPHERE:
                return PatternType.SPHERE;
            case HatchStyle.SMALL_GRID:
                return PatternType.SMALL_GRID;
            case HatchStyle.SMALL_CHECKER_BOARD:
                return PatternType.SMALL_CHECKER_BOARD;
            case HatchStyle.LARGE_CHECKER_BOARD:
                return PatternType.LARGE_CHECKER_BOARD;
            case HatchStyle.OUTLINED_DIAMOND:
                return PatternType.OUTLINED_DIAMOND;
            case HatchStyle.SOLID_DIAMOND:
                return PatternType.SOLID_DIAMOND;
            default:
                return PatternType.NONE;
        }
    }

    /// <summary>
    /// Returns HatchStyle according to the specified PatternType.
    /// </summary>
    static /*HatchStyle*/int getHatchStyle(/*PatternType*/int value)
    {
        switch (value)
        {
            case PatternType.DOWNWARD_DIAGONAL:
                return HatchStyle.BACKWARD_DIAGONAL;
            case PatternType.UPWARD_DIAGONAL:
                return HatchStyle.FORWARD_DIAGONAL;
            case PatternType.LARGE_GRID:
                return HatchStyle.LARGE_GRID;
            case PatternType.HORIZONTAL:
                return HatchStyle.HORIZONTAL;
            case PatternType.VERTICAL:
                return HatchStyle.VERTICAL;
            case PatternType.DIAGONAL_CROSS:
                return HatchStyle.DIAGONAL_CROSS;
            case PatternType.PERCENT_5:
                return HatchStyle.PERCENT_05;
            case PatternType.PERCENT_10:
                return HatchStyle.PERCENT_10;
            case PatternType.PERCENT_20:
                return HatchStyle.PERCENT_20;
            case PatternType.PERCENT_25:
                return HatchStyle.PERCENT_25;
            case PatternType.PERCENT_30:
                return HatchStyle.PERCENT_30;
            case PatternType.PERCENT_40:
                return HatchStyle.PERCENT_40;
            case PatternType.PERCENT_50:
                return HatchStyle.PERCENT_50;
            case PatternType.PERCENT_60:
                return HatchStyle.PERCENT_60;
            case PatternType.PERCENT_70:
                return HatchStyle.PERCENT_70;
            case PatternType.PERCENT_75:
                return HatchStyle.PERCENT_75;
            case PatternType.PERCENT_80:
                return HatchStyle.PERCENT_80;
            case PatternType.PERCENT_90:
                return HatchStyle.PERCENT_90;
            case PatternType.LIGHT_DOWNWARD_DIAGONAL:
                return HatchStyle.LIGHT_DOWNWARD_DIAGONAL;
            case PatternType.LIGHT_UPWARD_DIAGONAL:
                return HatchStyle.LIGHT_UPWARD_DIAGONAL;
            case PatternType.DARK_DOWNWARD_DIAGONAL:
                return HatchStyle.DARK_DOWNWARD_DIAGONAL;
            case PatternType.DARK_UPWARD_DIAGONAL:
                return HatchStyle.DARK_UPWARD_DIAGONAL;
            case PatternType.WIDE_DOWNWARD_DIAGONAL:
                return HatchStyle.WIDE_DOWNWARD_DIAGONAL;
            case PatternType.WIDE_UPWARD_DIAGONAL:
                return HatchStyle.WIDE_UPWARD_DIAGONAL;
            case PatternType.LIGHT_VERTICAL:
                return HatchStyle.LIGHT_VERTICAL;
            case PatternType.LIGHT_HORIZONTAL:
                return HatchStyle.LIGHT_HORIZONTAL;
            case PatternType.NARROW_VERTICAL:
                return HatchStyle.NARROW_VERTICAL;
            case PatternType.NARROW_HORIZONTAL:
                return HatchStyle.NARROW_HORIZONTAL;
            case PatternType.DARK_VERTICAL:
                return HatchStyle.DARK_VERTICAL;
            case PatternType.DARK_HORIZONTAL:
                return HatchStyle.DARK_HORIZONTAL;
            case PatternType.DASHED_DOWNWARD_DIAGONAL:
                return HatchStyle.DASHED_DOWNWARD_DIAGONAL;
            case PatternType.DASHED_UPWARD_DIAGONAL:
                return HatchStyle.DASHED_UPWARD_DIAGONAL;
            case PatternType.DASHED_HORIZONTAL:
                return HatchStyle.DASHED_HORIZONTAL;
            case PatternType.DASHED_VERTICAL:
                return HatchStyle.DASHED_VERTICAL;
            case PatternType.SMALL_CONFETTI:
                return HatchStyle.SMALL_CONFETTI;
            case PatternType.LARGE_CONFETTI:
                return HatchStyle.LARGE_CONFETTI;
            case PatternType.ZIG_ZAG:
                return HatchStyle.ZIG_ZAG;
            case PatternType.WAVE:
                return HatchStyle.WAVE;
            case PatternType.DIAGONAL_BRICK:
                return HatchStyle.DIAGONAL_BRICK;
            case PatternType.HORIZONTAL_BRICK:
                return HatchStyle.HORIZONTAL_BRICK;
            case PatternType.WEAVE:
                return HatchStyle.WEAVE;
            case PatternType.PLAID:
                return HatchStyle.PLAID;
            case PatternType.DIVOT:
                return HatchStyle.DIVOT;
            case PatternType.DOTTED_GRID:
                return HatchStyle.DOTTED_GRID;
            case PatternType.DOTTED_DIAMOND:
                return HatchStyle.DOTTED_DIAMOND;
            case PatternType.SHINGLE:
                return HatchStyle.SHINGLE;
            case PatternType.TRELLIS:
                return HatchStyle.TRELLIS;
            case PatternType.SPHERE:
                return HatchStyle.SPHERE;
            case PatternType.SMALL_GRID:
                return HatchStyle.SMALL_GRID;
            case PatternType.SMALL_CHECKER_BOARD:
                return HatchStyle.SMALL_CHECKER_BOARD;
            case PatternType.LARGE_CHECKER_BOARD:
                return HatchStyle.LARGE_CHECKER_BOARD;
            case PatternType.OUTLINED_DIAMOND:
                return HatchStyle.OUTLINED_DIAMOND;
            case PatternType.SOLID_DIAMOND:
                return HatchStyle.SOLID_DIAMOND;
            case PatternType.CROSS:
                return HatchStyle.CROSS;
            default:
                return HatchStyle.MIN;
        }
    }

    /// <summary>
    /// Returns TextureAlignment according to the specified DmlRectangleAlignment.
    /// </summary>
    static /*TextureAlignment*/int dmlRectangleToTextureAlignment(/*DmlRectangleAlignment*/int value)
    {
        switch (value)
        {
            case DmlRectangleAlignment.TOP_LEFT:
                return TextureAlignment.TOP_LEFT;
            case DmlRectangleAlignment.TOP:
                return TextureAlignment.TOP;
            case DmlRectangleAlignment.TOP_RIGHT:
                return TextureAlignment.TOP_RIGHT;
            case DmlRectangleAlignment.LEFT:
                return TextureAlignment.LEFT;
            case DmlRectangleAlignment.CENTER:
                return TextureAlignment.CENTER;
            case DmlRectangleAlignment.RIGHT:
                return TextureAlignment.RIGHT;
            case DmlRectangleAlignment.BOTTOM_LEFT:
                return TextureAlignment.BOTTOM_LEFT;
            case DmlRectangleAlignment.BOTTOM:
                return TextureAlignment.BOTTOM;
            case DmlRectangleAlignment.BOTTOM_RIGHT:
                return TextureAlignment.BOTTOM_RIGHT;
            default:
                return TextureAlignment.NONE;
        }
    }

    /// <summary>
    /// Returns DmlRectangleAlignment according to the specified TextureAlignment.
    /// </summary>
    static /*DmlRectangleAlignment*/int textureToDmlRectangleAlignment(/*TextureAlignment*/int value)
    {
        switch (value)
        {
            case TextureAlignment.TOP_LEFT:
                return DmlRectangleAlignment.TOP_LEFT;
            case TextureAlignment.TOP:
                return DmlRectangleAlignment.TOP;
            case TextureAlignment.TOP_RIGHT:
                return DmlRectangleAlignment.TOP_RIGHT;
            case TextureAlignment.LEFT:
                return DmlRectangleAlignment.LEFT;
            case TextureAlignment.CENTER:
                return DmlRectangleAlignment.CENTER;
            case TextureAlignment.RIGHT:
                return DmlRectangleAlignment.RIGHT;
            case TextureAlignment.BOTTOM_LEFT:
                return DmlRectangleAlignment.BOTTOM_LEFT;
            case TextureAlignment.BOTTOM:
                return DmlRectangleAlignment.BOTTOM;
            case TextureAlignment.BOTTOM_RIGHT:
                return DmlRectangleAlignment.BOTTOM_RIGHT;
            default:
                return DmlRectangleAlignment.NONE;
        }
    }

    /// <summary>
    /// Returns <see cref="DrColor"/> according to the specified <see cref="ThemeColor"/>.
    /// </summary>
    static DrColor themeToNativeColor(/*ThemeColor*/int themeColor, Theme theme) throws Exception
    {
        // Word VBA resets wdNotThemeColor to Color.White.
        if (themeColor == ThemeColor.NONE)
            return DrColor.WHITE;

        DmlSchemeColor schemeColor = new DmlSchemeColor(themeColor);

        if (theme == null)
            theme = getDefaultTheme();

        return schemeColor.createDrColor(theme, null);
    }

    /// <summary>
    /// Returns <see cref="ThemeColor"/> according to the specified <see cref="DrColor"/>.
    /// </summary>
    static /*ThemeColor*/int nativeToThemeColor(DrColor nativeColor, Theme theme) throws Exception
    {
        /*ThemeColor*/int[] themeColors = new /*ThemeColor*/int[]
        {
            ThemeColor.ACCENT_1, ThemeColor.ACCENT_2, ThemeColor.ACCENT_3, ThemeColor.ACCENT_4, ThemeColor.ACCENT_5,
            ThemeColor.ACCENT_6, ThemeColor.LIGHT_1, ThemeColor.LIGHT_2, ThemeColor.DARK_1, ThemeColor.DARK_2,
            ThemeColor.TEXT_1, ThemeColor.TEXT_2, ThemeColor.BACKGROUND_1, ThemeColor.BACKGROUND_2,
            ThemeColor.FOLLOWED_HYPERLINK, ThemeColor.HYPERLINK
        };

        if (nativeColor == null)
            return ThemeColor.NONE;

        for (/*ThemeColor*/int themeColor : themeColors)
        {
            DrColor curColor = themeToNativeColor(themeColor, theme);
            if (curColor.equals(nativeColor))
                return themeColor;
        }

        return ThemeColor.NONE;
    }

    /// <summary>
    /// Returns the integer value of TintAndShade for VML.
    /// </summary>
    static int vmlTintAndShadeModifier(double value)
    {
        return roundNear(255.0 * (1.0 - Math.abs(value)));
    }

    /// <summary>
    /// Returns the <see cref="DrColor"/> converted as VML Tint modifier.
    /// </summary>
    /// <remarks>
    /// The algorithm is interpolated by MS Word VBA multiple results.
    /// Specifications for the original algorithm have not been found.
    /// </remarks>
    static DrColor vmlTint(DrColor color, int modifier)
    {
        double tintFactor = 1.0d - (modifier / 255.0d);

        return DrColor.fromArgb(
            roundNear(color.getR() + (255 - color.getR()) * tintFactor),
            roundNear(color.getG() + (255 - color.getG()) * tintFactor),
            roundNear(color.getB() + (255 - color.getB()) * tintFactor));
    }

    /// <summary>
    /// Returns the <see cref="DrColor"/> converted as VML Shade modifier.
    /// </summary>
    /// <remarks>
    /// The algorithm is interpolated by MS Word VBA multiple results.
    /// Specifications for the original algorithm have not been found.
    /// </remarks>
    static DrColor vmlShade(DrColor color, int modifier)
    {
        double shadeFactor = 1.0d - (modifier / 255.0d);

        return DrColor.fromArgb(
            (int)(color.getR() * (1.0 - shadeFactor)),
            (int)(color.getG() * (1.0 - shadeFactor)),
            (int)(color.getB() * (1.0 - shadeFactor)));
    }

    /// <summary>
    /// Rounds up to the nearest integer value, casts result to int.
    /// </summary>
    private static int roundNear(double value)
    {
        return (int)msMath.round(value, 0, MidpointRounding.AWAY_FROM_ZERO);
    }

    private static byte[] loadResource(String resourceName)
    {
        try
        {
            Stream stream = ResourceUtil.fetchResourceStream(resourceName);
            try /*JAVA: was using*/
            {
                byte[] bytes = StreamUtil.copyStreamToByteArray(stream);
                return bytes;
            }
            finally { if (stream != null) stream.close(); }
        }
        catch(Exception e)
        {
            return new byte[0];
        }
    }

    private static StringToObjDictionary<byte[]> getResourceBytesCache()
    {
        if (gResourceBytesCache == null)
        {
            synchronized (G_RESOURCE_BYTES_CACHE_SYNC_ROOT)
            {
                if (gResourceBytesCache == null)
                    gResourceBytesCache = new StringToObjDictionary<byte[]>();
            }
        }

        return gResourceBytesCache;
    }

    private static Theme getDefaultTheme() throws Exception
    {
        if (gDefaultTheme == null)
        {
            synchronized (G_DEFAULT_THEME_SYNC_ROOT)
            {
                IDefaultThemeProvider defaultThemeProvider = ReaderFactory.createDefaultThemeProvider();
                gDefaultTheme = defaultThemeProvider.getDefaultTheme();
            }
        }

        return gDefaultTheme;
    }

    private static Theme gDefaultTheme;
    private static /*final*/ Object G_DEFAULT_THEME_SYNC_ROOT = new Object();
    private static StringToObjDictionary<byte[]> gResourceBytesCache;
    private static /*final*/ Object G_RESOURCE_BYTES_CACHE_SYNC_ROOT = new Object();
}

