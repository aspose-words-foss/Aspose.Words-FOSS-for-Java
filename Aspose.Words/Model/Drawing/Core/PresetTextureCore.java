// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/10/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies one of the Microsoft Word's built-in fill textures.
/// </summary>
/*enum*/ final class PresetTextureCore
{
    private PresetTextureCore(){}
    
    // These are textures in MS Word.
    public static final int BLUE_TISSUE_PAPER = 16; 
    public static final int BOUQUET = 19; 
    public static final int BROWN_MARBLE = 10; 
    public static final int CANVAS = 1; 
    public static final int CORK = 20; 
    public static final int DENIM = 2; 
    public static final int FISH_FOSSIL = 6; 
    public static final int GRANITE = 11; 
    public static final int GREEN_MARBLE = 8; 
    public static final int MEDIUM_WOOD = 23; 
    public static final int NEWSPRINT = 12; 
    public static final int OAK = 22; 
    public static final int PAPER_BAG = 5; 
    public static final int PAPYRUS = 0; 
    public static final int PARCHMENT = 14; 
    public static final int PINK_TISSUE_PAPER = 17; 
    public static final int PURPLE_MESH = 18; 
    public static final int RECYCLED_PAPER = 13; 
    public static final int SAND = 7; 
    public static final int STATIONERY = 15; 
    public static final int WALNUT = 21; 
    public static final int WATER_DROPLETS = 4; 
    public static final int WHITE_MARBLE = 9; 
    public static final int WOVEN_MAT = 3; 

    // These are patterns in MS word in separate enum. At the moment I keep them all in one enum.
    public static final int PERCENT_5 = 196; 
    public static final int PERCENT_10 = 204; 
    public static final int PERCENT_20 = 212; 
    public static final int PERCENT_25 = 220; 
    public static final int PERCENT_30 = 228; 
    public static final int PERCENT_40 = 236; 

    public static final int PERCENT_50 = 197; 
    public static final int PERCENT_60 = 205; 
    public static final int PERCENT_70 = 213; 
    public static final int PERCENT_75 = 221; 
    public static final int PERCENT_80 = 229; 
    public static final int PERCENT_90 = 237;

    public static final int LIGHT_DOWNWARD_DIAGONAL = 198;
    public static final int LIGHT_UPWARD_DIAGONAL = 206; 
    public static final int DARK_DOWNWARD_DIAGONAL = 214;
    public static final int DARK_UPWARD_DIAGONAL = 222;
    public static final int WIDE_DOWNWARD_DIAGONAL = 230;
    public static final int WIDE_UPWARD_DIAGONAL = 238;

    public static final int LIGHT_VERTICAL = 199;
    public static final int LIGHT_HORIZONTAL = 207;
    public static final int NARROW_VERTICAL = 215;
    public static final int NARROW_HORIZONTAL = 223;
    public static final int DARK_VERTICAL = 231;
    public static final int DARK_HORIZONTAL = 239;

    public static final int DASHED_DOWNWARD_DIAGONAL = 200;
    public static final int DASHED_UPWARD_DIAGONAL = 208; 
    public static final int DASHED_HORIZONTAL = 216; 
    public static final int DASHED_VERTICAL = 224;
    public static final int SMALL_CONFETTI = 232;
    public static final int LARGE_CONFETTI = 240;

    public static final int ZIG_ZAG = 201;
    public static final int WAVE = 209;
    public static final int DIAGONAL_BRICK = 217;
    public static final int HORIZONTAL_BRICK = 225;
    public static final int WEAVE = 233;
    public static final int PLAID = 241; 

    public static final int DIVOT = 202;
    public static final int DOTTED_GRID = 210;
    public static final int DOTTED_DIAMOND = 218;
    public static final int SHINGLE = 226;
    public static final int TRELLIS = 234;
    public static final int SPHERE = 242;

    public static final int SMALL_GRID = 203;
    public static final int LARGE_GRID = 211;
    public static final int SMALL_CHECKER_BOARD = 219; 
    public static final int LARGE_CHECKER_BOARD = 227; 
    public static final int OUTLINED_DIAMOND = 235; 
    public static final int SOLID_DIAMOND = 243; 

    public static final int CUSTOM = 255;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PresetTextureCore value.
    /// </summary>
    public static String getName(/*PresetTextureCore*/int presetTextureCore)
    {
    	switch (presetTextureCore)
    	{
    		case BLUE_TISSUE_PAPER: return "BLUE_TISSUE_PAPER";
    		case BOUQUET: return "BOUQUET";
    		case BROWN_MARBLE: return "BROWN_MARBLE";
    		case CANVAS: return "CANVAS";
    		case CORK: return "CORK";
    		case DENIM: return "DENIM";
    		case FISH_FOSSIL: return "FISH_FOSSIL";
    		case GRANITE: return "GRANITE";
    		case GREEN_MARBLE: return "GREEN_MARBLE";
    		case MEDIUM_WOOD: return "MEDIUM_WOOD";
    		case NEWSPRINT: return "NEWSPRINT";
    		case OAK: return "OAK";
    		case PAPER_BAG: return "PAPER_BAG";
    		case PAPYRUS: return "PAPYRUS";
    		case PARCHMENT: return "PARCHMENT";
    		case PINK_TISSUE_PAPER: return "PINK_TISSUE_PAPER";
    		case PURPLE_MESH: return "PURPLE_MESH";
    		case RECYCLED_PAPER: return "RECYCLED_PAPER";
    		case SAND: return "SAND";
    		case STATIONERY: return "STATIONERY";
    		case WALNUT: return "WALNUT";
    		case WATER_DROPLETS: return "WATER_DROPLETS";
    		case WHITE_MARBLE: return "WHITE_MARBLE";
    		case WOVEN_MAT: return "WOVEN_MAT";
    		case PERCENT_5: return "PERCENT_5";
    		case PERCENT_10: return "PERCENT_10";
    		case PERCENT_20: return "PERCENT_20";
    		case PERCENT_25: return "PERCENT_25";
    		case PERCENT_30: return "PERCENT_30";
    		case PERCENT_40: return "PERCENT_40";
    		case PERCENT_50: return "PERCENT_50";
    		case PERCENT_60: return "PERCENT_60";
    		case PERCENT_70: return "PERCENT_70";
    		case PERCENT_75: return "PERCENT_75";
    		case PERCENT_80: return "PERCENT_80";
    		case PERCENT_90: return "PERCENT_90";
    		case LIGHT_DOWNWARD_DIAGONAL: return "LIGHT_DOWNWARD_DIAGONAL";
    		case LIGHT_UPWARD_DIAGONAL: return "LIGHT_UPWARD_DIAGONAL";
    		case DARK_DOWNWARD_DIAGONAL: return "DARK_DOWNWARD_DIAGONAL";
    		case DARK_UPWARD_DIAGONAL: return "DARK_UPWARD_DIAGONAL";
    		case WIDE_DOWNWARD_DIAGONAL: return "WIDE_DOWNWARD_DIAGONAL";
    		case WIDE_UPWARD_DIAGONAL: return "WIDE_UPWARD_DIAGONAL";
    		case LIGHT_VERTICAL: return "LIGHT_VERTICAL";
    		case LIGHT_HORIZONTAL: return "LIGHT_HORIZONTAL";
    		case NARROW_VERTICAL: return "NARROW_VERTICAL";
    		case NARROW_HORIZONTAL: return "NARROW_HORIZONTAL";
    		case DARK_VERTICAL: return "DARK_VERTICAL";
    		case DARK_HORIZONTAL: return "DARK_HORIZONTAL";
    		case DASHED_DOWNWARD_DIAGONAL: return "DASHED_DOWNWARD_DIAGONAL";
    		case DASHED_UPWARD_DIAGONAL: return "DASHED_UPWARD_DIAGONAL";
    		case DASHED_HORIZONTAL: return "DASHED_HORIZONTAL";
    		case DASHED_VERTICAL: return "DASHED_VERTICAL";
    		case SMALL_CONFETTI: return "SMALL_CONFETTI";
    		case LARGE_CONFETTI: return "LARGE_CONFETTI";
    		case ZIG_ZAG: return "ZIG_ZAG";
    		case WAVE: return "WAVE";
    		case DIAGONAL_BRICK: return "DIAGONAL_BRICK";
    		case HORIZONTAL_BRICK: return "HORIZONTAL_BRICK";
    		case WEAVE: return "WEAVE";
    		case PLAID: return "PLAID";
    		case DIVOT: return "DIVOT";
    		case DOTTED_GRID: return "DOTTED_GRID";
    		case DOTTED_DIAMOND: return "DOTTED_DIAMOND";
    		case SHINGLE: return "SHINGLE";
    		case TRELLIS: return "TRELLIS";
    		case SPHERE: return "SPHERE";
    		case SMALL_GRID: return "SMALL_GRID";
    		case LARGE_GRID: return "LARGE_GRID";
    		case SMALL_CHECKER_BOARD: return "SMALL_CHECKER_BOARD";
    		case LARGE_CHECKER_BOARD: return "LARGE_CHECKER_BOARD";
    		case OUTLINED_DIAMOND: return "OUTLINED_DIAMOND";
    		case SOLID_DIAMOND: return "SOLID_DIAMOND";
    		case CUSTOM: return "CUSTOM";
    		default: return "Unknown PresetTextureCore value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PresetTextureCore value.
    /// </summary>
    public static String toString(/*PresetTextureCore*/int presetTextureCore)
    {
    	switch (presetTextureCore)
    	{
    		case BLUE_TISSUE_PAPER: return "BlueTissuePaper";
    		case BOUQUET: return "Bouquet";
    		case BROWN_MARBLE: return "BrownMarble";
    		case CANVAS: return "Canvas";
    		case CORK: return "Cork";
    		case DENIM: return "Denim";
    		case FISH_FOSSIL: return "FishFossil";
    		case GRANITE: return "Granite";
    		case GREEN_MARBLE: return "GreenMarble";
    		case MEDIUM_WOOD: return "MediumWood";
    		case NEWSPRINT: return "Newsprint";
    		case OAK: return "Oak";
    		case PAPER_BAG: return "PaperBag";
    		case PAPYRUS: return "Papyrus";
    		case PARCHMENT: return "Parchment";
    		case PINK_TISSUE_PAPER: return "PinkTissuePaper";
    		case PURPLE_MESH: return "PurpleMesh";
    		case RECYCLED_PAPER: return "RecycledPaper";
    		case SAND: return "Sand";
    		case STATIONERY: return "Stationery";
    		case WALNUT: return "Walnut";
    		case WATER_DROPLETS: return "WaterDroplets";
    		case WHITE_MARBLE: return "WhiteMarble";
    		case WOVEN_MAT: return "WovenMat";
    		case PERCENT_5: return "Percent5";
    		case PERCENT_10: return "Percent10";
    		case PERCENT_20: return "Percent20";
    		case PERCENT_25: return "Percent25";
    		case PERCENT_30: return "Percent30";
    		case PERCENT_40: return "Percent40";
    		case PERCENT_50: return "Percent50";
    		case PERCENT_60: return "Percent60";
    		case PERCENT_70: return "Percent70";
    		case PERCENT_75: return "Percent75";
    		case PERCENT_80: return "Percent80";
    		case PERCENT_90: return "Percent90";
    		case LIGHT_DOWNWARD_DIAGONAL: return "LightDownwardDiagonal";
    		case LIGHT_UPWARD_DIAGONAL: return "LightUpwardDiagonal";
    		case DARK_DOWNWARD_DIAGONAL: return "DarkDownwardDiagonal";
    		case DARK_UPWARD_DIAGONAL: return "DarkUpwardDiagonal";
    		case WIDE_DOWNWARD_DIAGONAL: return "WideDownwardDiagonal";
    		case WIDE_UPWARD_DIAGONAL: return "WideUpwardDiagonal";
    		case LIGHT_VERTICAL: return "LightVertical";
    		case LIGHT_HORIZONTAL: return "LightHorizontal";
    		case NARROW_VERTICAL: return "NarrowVertical";
    		case NARROW_HORIZONTAL: return "NarrowHorizontal";
    		case DARK_VERTICAL: return "DarkVertical";
    		case DARK_HORIZONTAL: return "DarkHorizontal";
    		case DASHED_DOWNWARD_DIAGONAL: return "DashedDownwardDiagonal";
    		case DASHED_UPWARD_DIAGONAL: return "DashedUpwardDiagonal";
    		case DASHED_HORIZONTAL: return "DashedHorizontal";
    		case DASHED_VERTICAL: return "DashedVertical";
    		case SMALL_CONFETTI: return "SmallConfetti";
    		case LARGE_CONFETTI: return "LargeConfetti";
    		case ZIG_ZAG: return "ZigZag";
    		case WAVE: return "Wave";
    		case DIAGONAL_BRICK: return "DiagonalBrick";
    		case HORIZONTAL_BRICK: return "HorizontalBrick";
    		case WEAVE: return "Weave";
    		case PLAID: return "Plaid";
    		case DIVOT: return "Divot";
    		case DOTTED_GRID: return "DottedGrid";
    		case DOTTED_DIAMOND: return "DottedDiamond";
    		case SHINGLE: return "Shingle";
    		case TRELLIS: return "Trellis";
    		case SPHERE: return "Sphere";
    		case SMALL_GRID: return "SmallGrid";
    		case LARGE_GRID: return "LargeGrid";
    		case SMALL_CHECKER_BOARD: return "SmallCheckerBoard";
    		case LARGE_CHECKER_BOARD: return "LargeCheckerBoard";
    		case OUTLINED_DIAMOND: return "OutlinedDiamond";
    		case SOLID_DIAMOND: return "SolidDiamond";
    		case CUSTOM: return "Custom";
    		default: return "Unknown PresetTextureCore value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PresetTextureCore name.
    /// </summary>
    public static /*PresetTextureCore*/int fromName(String presetTextureCoreName)
    {
    	if ("BLUE_TISSUE_PAPER".equals(presetTextureCoreName)) return BLUE_TISSUE_PAPER;
    	if ("BOUQUET".equals(presetTextureCoreName)) return BOUQUET;
    	if ("BROWN_MARBLE".equals(presetTextureCoreName)) return BROWN_MARBLE;
    	if ("CANVAS".equals(presetTextureCoreName)) return CANVAS;
    	if ("CORK".equals(presetTextureCoreName)) return CORK;
    	if ("DENIM".equals(presetTextureCoreName)) return DENIM;
    	if ("FISH_FOSSIL".equals(presetTextureCoreName)) return FISH_FOSSIL;
    	if ("GRANITE".equals(presetTextureCoreName)) return GRANITE;
    	if ("GREEN_MARBLE".equals(presetTextureCoreName)) return GREEN_MARBLE;
    	if ("MEDIUM_WOOD".equals(presetTextureCoreName)) return MEDIUM_WOOD;
    	if ("NEWSPRINT".equals(presetTextureCoreName)) return NEWSPRINT;
    	if ("OAK".equals(presetTextureCoreName)) return OAK;
    	if ("PAPER_BAG".equals(presetTextureCoreName)) return PAPER_BAG;
    	if ("PAPYRUS".equals(presetTextureCoreName)) return PAPYRUS;
    	if ("PARCHMENT".equals(presetTextureCoreName)) return PARCHMENT;
    	if ("PINK_TISSUE_PAPER".equals(presetTextureCoreName)) return PINK_TISSUE_PAPER;
    	if ("PURPLE_MESH".equals(presetTextureCoreName)) return PURPLE_MESH;
    	if ("RECYCLED_PAPER".equals(presetTextureCoreName)) return RECYCLED_PAPER;
    	if ("SAND".equals(presetTextureCoreName)) return SAND;
    	if ("STATIONERY".equals(presetTextureCoreName)) return STATIONERY;
    	if ("WALNUT".equals(presetTextureCoreName)) return WALNUT;
    	if ("WATER_DROPLETS".equals(presetTextureCoreName)) return WATER_DROPLETS;
    	if ("WHITE_MARBLE".equals(presetTextureCoreName)) return WHITE_MARBLE;
    	if ("WOVEN_MAT".equals(presetTextureCoreName)) return WOVEN_MAT;
    	if ("PERCENT_5".equals(presetTextureCoreName)) return PERCENT_5;
    	if ("PERCENT_10".equals(presetTextureCoreName)) return PERCENT_10;
    	if ("PERCENT_20".equals(presetTextureCoreName)) return PERCENT_20;
    	if ("PERCENT_25".equals(presetTextureCoreName)) return PERCENT_25;
    	if ("PERCENT_30".equals(presetTextureCoreName)) return PERCENT_30;
    	if ("PERCENT_40".equals(presetTextureCoreName)) return PERCENT_40;
    	if ("PERCENT_50".equals(presetTextureCoreName)) return PERCENT_50;
    	if ("PERCENT_60".equals(presetTextureCoreName)) return PERCENT_60;
    	if ("PERCENT_70".equals(presetTextureCoreName)) return PERCENT_70;
    	if ("PERCENT_75".equals(presetTextureCoreName)) return PERCENT_75;
    	if ("PERCENT_80".equals(presetTextureCoreName)) return PERCENT_80;
    	if ("PERCENT_90".equals(presetTextureCoreName)) return PERCENT_90;
    	if ("LIGHT_DOWNWARD_DIAGONAL".equals(presetTextureCoreName)) return LIGHT_DOWNWARD_DIAGONAL;
    	if ("LIGHT_UPWARD_DIAGONAL".equals(presetTextureCoreName)) return LIGHT_UPWARD_DIAGONAL;
    	if ("DARK_DOWNWARD_DIAGONAL".equals(presetTextureCoreName)) return DARK_DOWNWARD_DIAGONAL;
    	if ("DARK_UPWARD_DIAGONAL".equals(presetTextureCoreName)) return DARK_UPWARD_DIAGONAL;
    	if ("WIDE_DOWNWARD_DIAGONAL".equals(presetTextureCoreName)) return WIDE_DOWNWARD_DIAGONAL;
    	if ("WIDE_UPWARD_DIAGONAL".equals(presetTextureCoreName)) return WIDE_UPWARD_DIAGONAL;
    	if ("LIGHT_VERTICAL".equals(presetTextureCoreName)) return LIGHT_VERTICAL;
    	if ("LIGHT_HORIZONTAL".equals(presetTextureCoreName)) return LIGHT_HORIZONTAL;
    	if ("NARROW_VERTICAL".equals(presetTextureCoreName)) return NARROW_VERTICAL;
    	if ("NARROW_HORIZONTAL".equals(presetTextureCoreName)) return NARROW_HORIZONTAL;
    	if ("DARK_VERTICAL".equals(presetTextureCoreName)) return DARK_VERTICAL;
    	if ("DARK_HORIZONTAL".equals(presetTextureCoreName)) return DARK_HORIZONTAL;
    	if ("DASHED_DOWNWARD_DIAGONAL".equals(presetTextureCoreName)) return DASHED_DOWNWARD_DIAGONAL;
    	if ("DASHED_UPWARD_DIAGONAL".equals(presetTextureCoreName)) return DASHED_UPWARD_DIAGONAL;
    	if ("DASHED_HORIZONTAL".equals(presetTextureCoreName)) return DASHED_HORIZONTAL;
    	if ("DASHED_VERTICAL".equals(presetTextureCoreName)) return DASHED_VERTICAL;
    	if ("SMALL_CONFETTI".equals(presetTextureCoreName)) return SMALL_CONFETTI;
    	if ("LARGE_CONFETTI".equals(presetTextureCoreName)) return LARGE_CONFETTI;
    	if ("ZIG_ZAG".equals(presetTextureCoreName)) return ZIG_ZAG;
    	if ("WAVE".equals(presetTextureCoreName)) return WAVE;
    	if ("DIAGONAL_BRICK".equals(presetTextureCoreName)) return DIAGONAL_BRICK;
    	if ("HORIZONTAL_BRICK".equals(presetTextureCoreName)) return HORIZONTAL_BRICK;
    	if ("WEAVE".equals(presetTextureCoreName)) return WEAVE;
    	if ("PLAID".equals(presetTextureCoreName)) return PLAID;
    	if ("DIVOT".equals(presetTextureCoreName)) return DIVOT;
    	if ("DOTTED_GRID".equals(presetTextureCoreName)) return DOTTED_GRID;
    	if ("DOTTED_DIAMOND".equals(presetTextureCoreName)) return DOTTED_DIAMOND;
    	if ("SHINGLE".equals(presetTextureCoreName)) return SHINGLE;
    	if ("TRELLIS".equals(presetTextureCoreName)) return TRELLIS;
    	if ("SPHERE".equals(presetTextureCoreName)) return SPHERE;
    	if ("SMALL_GRID".equals(presetTextureCoreName)) return SMALL_GRID;
    	if ("LARGE_GRID".equals(presetTextureCoreName)) return LARGE_GRID;
    	if ("SMALL_CHECKER_BOARD".equals(presetTextureCoreName)) return SMALL_CHECKER_BOARD;
    	if ("LARGE_CHECKER_BOARD".equals(presetTextureCoreName)) return LARGE_CHECKER_BOARD;
    	if ("OUTLINED_DIAMOND".equals(presetTextureCoreName)) return OUTLINED_DIAMOND;
    	if ("SOLID_DIAMOND".equals(presetTextureCoreName)) return SOLID_DIAMOND;
    	if ("CUSTOM".equals(presetTextureCoreName)) return CUSTOM;
    	throw new IllegalArgumentException("Unknown PresetTextureCore name.");
    }
    
    /// <summary>
    /// Returns array of PresetTextureCore values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BLUE_TISSUE_PAPER,
    		BOUQUET,
    		BROWN_MARBLE,
    		CANVAS,
    		CORK,
    		DENIM,
    		FISH_FOSSIL,
    		GRANITE,
    		GREEN_MARBLE,
    		MEDIUM_WOOD,
    		NEWSPRINT,
    		OAK,
    		PAPER_BAG,
    		PAPYRUS,
    		PARCHMENT,
    		PINK_TISSUE_PAPER,
    		PURPLE_MESH,
    		RECYCLED_PAPER,
    		SAND,
    		STATIONERY,
    		WALNUT,
    		WATER_DROPLETS,
    		WHITE_MARBLE,
    		WOVEN_MAT,
    		PERCENT_5,
    		PERCENT_10,
    		PERCENT_20,
    		PERCENT_25,
    		PERCENT_30,
    		PERCENT_40,
    		PERCENT_50,
    		PERCENT_60,
    		PERCENT_70,
    		PERCENT_75,
    		PERCENT_80,
    		PERCENT_90,
    		LIGHT_DOWNWARD_DIAGONAL,
    		LIGHT_UPWARD_DIAGONAL,
    		DARK_DOWNWARD_DIAGONAL,
    		DARK_UPWARD_DIAGONAL,
    		WIDE_DOWNWARD_DIAGONAL,
    		WIDE_UPWARD_DIAGONAL,
    		LIGHT_VERTICAL,
    		LIGHT_HORIZONTAL,
    		NARROW_VERTICAL,
    		NARROW_HORIZONTAL,
    		DARK_VERTICAL,
    		DARK_HORIZONTAL,
    		DASHED_DOWNWARD_DIAGONAL,
    		DASHED_UPWARD_DIAGONAL,
    		DASHED_HORIZONTAL,
    		DASHED_VERTICAL,
    		SMALL_CONFETTI,
    		LARGE_CONFETTI,
    		ZIG_ZAG,
    		WAVE,
    		DIAGONAL_BRICK,
    		HORIZONTAL_BRICK,
    		WEAVE,
    		PLAID,
    		DIVOT,
    		DOTTED_GRID,
    		DOTTED_DIAMOND,
    		SHINGLE,
    		TRELLIS,
    		SPHERE,
    		SMALL_GRID,
    		LARGE_GRID,
    		SMALL_CHECKER_BOARD,
    		LARGE_CHECKER_BOARD,
    		OUTLINED_DIAMOND,
    		SOLID_DIAMOND,
    		CUSTOM,
    	};
    }

    public static final int length = 73;
}

