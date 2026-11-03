// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/05/2021 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the fill pattern to be used to fill a shape.
/// </summary>
/// <dev>
/// HatchStyle is not appropriate for DML, because Patterned() for DML can address all 55 patterns,
/// and HatchStyle contains several identical values for different constants.
/// </dev>
public /*enum*/ final class PatternType
{
    private PatternType(){}
    
    /// <summary>
    /// No pattern.
    /// </summary>
    public static final int NONE = -1;

    /// <summary>
    /// 10% of the foreground color.
    /// </summary>
    public static final int PERCENT_10 = 1;

    /// <summary>
    /// 20% of the foreground color.
    /// </summary>
    public static final int PERCENT_20 = 2;

    /// <summary>
    /// 25% of the foreground color.
    /// </summary>
    public static final int PERCENT_25 = 3;

    /// <summary>
    /// 30% of the foreground color.
    /// </summary>
    public static final int PERCENT_30 = 4;

    /// <summary>
    /// 40% of the foreground color
    /// </summary>
    public static final int PERCENT_40 = 5;

    /// <summary>
    /// 50% of the foreground color
    /// </summary>
    public static final int PERCENT_50 = 6;

    /// <summary>
    /// 5% of the foreground color.
    /// </summary>
    public static final int PERCENT_5 = 7;

    /// <summary>
    /// 60% of the foreground color.
    /// </summary>
    public static final int PERCENT_60 = 8;

    /// <summary>
    /// 70% of the foreground color.
    /// </summary>
    public static final int PERCENT_70 = 9;

    /// <summary>
    /// 75% of the foreground color.
    /// </summary>
    public static final int PERCENT_75 = 10;

    /// <summary>
    /// 80% of the foreground color.
    /// </summary>
    public static final int PERCENT_80 = 11;

    /// <summary>
    /// 90% of the foreground color.
    /// </summary>
    public static final int PERCENT_90 = 12;

    /// <summary>
    /// Cross.
    /// </summary>
    public static final int CROSS = 13;

    /// <summary>
    /// Dark downward diagonal.
    /// </summary>
    public static final int DARK_DOWNWARD_DIAGONAL = 14;

    /// <summary>
    /// Dark horizontal.
    /// </summary>
    public static final int DARK_HORIZONTAL = 15;

    /// <summary>
    /// Dark upward diagonal.
    /// </summary>
    public static final int DARK_UPWARD_DIAGONAL = 16;

    /// <summary>
    /// Dark vertical.
    /// </summary>
    public static final int DARK_VERTICAL = 17;

    /// <summary>
    /// Dashed downward diagonal.
    /// </summary>
    public static final int DASHED_DOWNWARD_DIAGONAL = 18;

    /// <summary>
    /// Dashed horizontal.
    /// </summary>
    public static final int DASHED_HORIZONTAL = 19;

    /// <summary>
    /// Dashed upward diagonal.
    /// </summary>
    public static final int DASHED_UPWARD_DIAGONAL = 20;

    /// <summary>
    /// Dashed vertical.
    /// </summary>
    public static final int DASHED_VERTICAL = 21;

    /// <summary>
    /// Diagonal brick.
    /// </summary>
    public static final int DIAGONAL_BRICK = 22;

    /// <summary>
    /// Diagonal cross.
    /// </summary>
    public static final int DIAGONAL_CROSS = 23;

    /// <summary>
    /// Pattern divot.
    /// </summary>
    public static final int DIVOT = 24;

    /// <summary>
    /// Dotted diamond.
    /// </summary>
    public static final int DOTTED_DIAMOND = 25;

    /// <summary>
    /// Dotted grid.
    /// </summary>
    public static final int DOTTED_GRID = 26;

    /// <summary>
    /// Downward diagonal.
    /// </summary>
    public static final int DOWNWARD_DIAGONAL = 27;

    /// <summary>
    /// Horizontal.
    /// </summary>
    public static final int HORIZONTAL = 28;

    /// <summary>
    /// Horizontal brick.
    /// </summary>
    public static final int HORIZONTAL_BRICK = 29;

    /// <summary>
    /// Large checker board.
    /// </summary>
    public static final int LARGE_CHECKER_BOARD = 30;

    /// <summary>
    /// Large confetti.
    /// </summary>
    public static final int LARGE_CONFETTI = 31;

    /// <summary>
    /// Large grid.
    /// </summary>
    public static final int LARGE_GRID = 32;

    /// <summary>
    /// Light downward diagonal.
    /// </summary>
    public static final int LIGHT_DOWNWARD_DIAGONAL = 33;

    /// <summary>
    /// Light horizontal.
    /// </summary>
    public static final int LIGHT_HORIZONTAL = 34;

    /// <summary>
    /// Light upward diagonal.
    /// </summary>
    public static final int LIGHT_UPWARD_DIAGONAL = 36;

    /// <summary>
    /// Light vertical.
    /// </summary>
    public static final int LIGHT_VERTICAL = 37;

    /// <summary>
    /// Narrow horizontal.
    /// </summary>
    public static final int NARROW_HORIZONTAL = 38;

    /// <summary>
    /// Narrow vertical.
    /// </summary>
    public static final int NARROW_VERTICAL = 39;

    /// <summary>
    /// Outlined diamond.
    /// </summary>
    public static final int OUTLINED_DIAMOND = 40;

    /// <summary>
    /// Plaid.
    /// </summary>
    public static final int PLAID = 41;

    /// <summary>
    /// Shingle.
    /// </summary>
    public static final int SHINGLE = 42;

    /// <summary>
    /// Small checker board.
    /// </summary>
    public static final int SMALL_CHECKER_BOARD = 43;

    /// <summary>
    /// Small confetti.
    /// </summary>
    public static final int SMALL_CONFETTI = 44;

    /// <summary>
    /// Small grid.
    /// </summary>
    public static final int SMALL_GRID = 45;

    /// <summary>
    /// Solid diamond.
    /// </summary>
    public static final int SOLID_DIAMOND = 46;

    /// <summary>
    /// Sphere.
    /// </summary>
    public static final int SPHERE = 47;

    /// <summary>
    /// Trellis.
    /// </summary>
    public static final int TRELLIS = 48;

    /// <summary>
    /// Upward diagonal.
    /// </summary>
    public static final int UPWARD_DIAGONAL = 49;

    /// <summary>
    /// Vertical.
    /// </summary>
    public static final int VERTICAL = 50;

    /// <summary>
    /// Wave.
    /// </summary>
    public static final int WAVE = 51;

    /// <summary>
    /// Weave.
    /// </summary>
    public static final int WEAVE = 52;

    /// <summary>
    /// Wide downward diagonal.
    /// </summary>
    public static final int WIDE_DOWNWARD_DIAGONAL = 53;

    /// <summary>
    /// Wide upward diagonal.
    /// </summary>
    public static final int WIDE_UPWARD_DIAGONAL = 54;

    /// <summary>
    /// Zig zag.
    /// </summary>
    public static final int ZIG_ZAG = 55;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PatternType value.
    /// </summary>
    public static String getName(/*PatternType*/int patternType)
    {
    	switch (patternType)
    	{
    		case NONE: return "NONE";
    		case PERCENT_10: return "PERCENT_10";
    		case PERCENT_20: return "PERCENT_20";
    		case PERCENT_25: return "PERCENT_25";
    		case PERCENT_30: return "PERCENT_30";
    		case PERCENT_40: return "PERCENT_40";
    		case PERCENT_50: return "PERCENT_50";
    		case PERCENT_5: return "PERCENT_5";
    		case PERCENT_60: return "PERCENT_60";
    		case PERCENT_70: return "PERCENT_70";
    		case PERCENT_75: return "PERCENT_75";
    		case PERCENT_80: return "PERCENT_80";
    		case PERCENT_90: return "PERCENT_90";
    		case CROSS: return "CROSS";
    		case DARK_DOWNWARD_DIAGONAL: return "DARK_DOWNWARD_DIAGONAL";
    		case DARK_HORIZONTAL: return "DARK_HORIZONTAL";
    		case DARK_UPWARD_DIAGONAL: return "DARK_UPWARD_DIAGONAL";
    		case DARK_VERTICAL: return "DARK_VERTICAL";
    		case DASHED_DOWNWARD_DIAGONAL: return "DASHED_DOWNWARD_DIAGONAL";
    		case DASHED_HORIZONTAL: return "DASHED_HORIZONTAL";
    		case DASHED_UPWARD_DIAGONAL: return "DASHED_UPWARD_DIAGONAL";
    		case DASHED_VERTICAL: return "DASHED_VERTICAL";
    		case DIAGONAL_BRICK: return "DIAGONAL_BRICK";
    		case DIAGONAL_CROSS: return "DIAGONAL_CROSS";
    		case DIVOT: return "DIVOT";
    		case DOTTED_DIAMOND: return "DOTTED_DIAMOND";
    		case DOTTED_GRID: return "DOTTED_GRID";
    		case DOWNWARD_DIAGONAL: return "DOWNWARD_DIAGONAL";
    		case HORIZONTAL: return "HORIZONTAL";
    		case HORIZONTAL_BRICK: return "HORIZONTAL_BRICK";
    		case LARGE_CHECKER_BOARD: return "LARGE_CHECKER_BOARD";
    		case LARGE_CONFETTI: return "LARGE_CONFETTI";
    		case LARGE_GRID: return "LARGE_GRID";
    		case LIGHT_DOWNWARD_DIAGONAL: return "LIGHT_DOWNWARD_DIAGONAL";
    		case LIGHT_HORIZONTAL: return "LIGHT_HORIZONTAL";
    		case LIGHT_UPWARD_DIAGONAL: return "LIGHT_UPWARD_DIAGONAL";
    		case LIGHT_VERTICAL: return "LIGHT_VERTICAL";
    		case NARROW_HORIZONTAL: return "NARROW_HORIZONTAL";
    		case NARROW_VERTICAL: return "NARROW_VERTICAL";
    		case OUTLINED_DIAMOND: return "OUTLINED_DIAMOND";
    		case PLAID: return "PLAID";
    		case SHINGLE: return "SHINGLE";
    		case SMALL_CHECKER_BOARD: return "SMALL_CHECKER_BOARD";
    		case SMALL_CONFETTI: return "SMALL_CONFETTI";
    		case SMALL_GRID: return "SMALL_GRID";
    		case SOLID_DIAMOND: return "SOLID_DIAMOND";
    		case SPHERE: return "SPHERE";
    		case TRELLIS: return "TRELLIS";
    		case UPWARD_DIAGONAL: return "UPWARD_DIAGONAL";
    		case VERTICAL: return "VERTICAL";
    		case WAVE: return "WAVE";
    		case WEAVE: return "WEAVE";
    		case WIDE_DOWNWARD_DIAGONAL: return "WIDE_DOWNWARD_DIAGONAL";
    		case WIDE_UPWARD_DIAGONAL: return "WIDE_UPWARD_DIAGONAL";
    		case ZIG_ZAG: return "ZIG_ZAG";
    		default: return "Unknown PatternType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PatternType value.
    /// </summary>
    public static String toString(/*PatternType*/int patternType)
    {
    	switch (patternType)
    	{
    		case NONE: return "None";
    		case PERCENT_10: return "Percent10";
    		case PERCENT_20: return "Percent20";
    		case PERCENT_25: return "Percent25";
    		case PERCENT_30: return "Percent30";
    		case PERCENT_40: return "Percent40";
    		case PERCENT_50: return "Percent50";
    		case PERCENT_5: return "Percent5";
    		case PERCENT_60: return "Percent60";
    		case PERCENT_70: return "Percent70";
    		case PERCENT_75: return "Percent75";
    		case PERCENT_80: return "Percent80";
    		case PERCENT_90: return "Percent90";
    		case CROSS: return "Cross";
    		case DARK_DOWNWARD_DIAGONAL: return "DarkDownwardDiagonal";
    		case DARK_HORIZONTAL: return "DarkHorizontal";
    		case DARK_UPWARD_DIAGONAL: return "DarkUpwardDiagonal";
    		case DARK_VERTICAL: return "DarkVertical";
    		case DASHED_DOWNWARD_DIAGONAL: return "DashedDownwardDiagonal";
    		case DASHED_HORIZONTAL: return "DashedHorizontal";
    		case DASHED_UPWARD_DIAGONAL: return "DashedUpwardDiagonal";
    		case DASHED_VERTICAL: return "DashedVertical";
    		case DIAGONAL_BRICK: return "DiagonalBrick";
    		case DIAGONAL_CROSS: return "DiagonalCross";
    		case DIVOT: return "Divot";
    		case DOTTED_DIAMOND: return "DottedDiamond";
    		case DOTTED_GRID: return "DottedGrid";
    		case DOWNWARD_DIAGONAL: return "DownwardDiagonal";
    		case HORIZONTAL: return "Horizontal";
    		case HORIZONTAL_BRICK: return "HorizontalBrick";
    		case LARGE_CHECKER_BOARD: return "LargeCheckerBoard";
    		case LARGE_CONFETTI: return "LargeConfetti";
    		case LARGE_GRID: return "LargeGrid";
    		case LIGHT_DOWNWARD_DIAGONAL: return "LightDownwardDiagonal";
    		case LIGHT_HORIZONTAL: return "LightHorizontal";
    		case LIGHT_UPWARD_DIAGONAL: return "LightUpwardDiagonal";
    		case LIGHT_VERTICAL: return "LightVertical";
    		case NARROW_HORIZONTAL: return "NarrowHorizontal";
    		case NARROW_VERTICAL: return "NarrowVertical";
    		case OUTLINED_DIAMOND: return "OutlinedDiamond";
    		case PLAID: return "Plaid";
    		case SHINGLE: return "Shingle";
    		case SMALL_CHECKER_BOARD: return "SmallCheckerBoard";
    		case SMALL_CONFETTI: return "SmallConfetti";
    		case SMALL_GRID: return "SmallGrid";
    		case SOLID_DIAMOND: return "SolidDiamond";
    		case SPHERE: return "Sphere";
    		case TRELLIS: return "Trellis";
    		case UPWARD_DIAGONAL: return "UpwardDiagonal";
    		case VERTICAL: return "Vertical";
    		case WAVE: return "Wave";
    		case WEAVE: return "Weave";
    		case WIDE_DOWNWARD_DIAGONAL: return "WideDownwardDiagonal";
    		case WIDE_UPWARD_DIAGONAL: return "WideUpwardDiagonal";
    		case ZIG_ZAG: return "ZigZag";
    		default: return "Unknown PatternType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PatternType name.
    /// </summary>
    public static /*PatternType*/int fromName(String patternTypeName)
    {
    	if ("NONE".equals(patternTypeName)) return NONE;
    	if ("PERCENT_10".equals(patternTypeName)) return PERCENT_10;
    	if ("PERCENT_20".equals(patternTypeName)) return PERCENT_20;
    	if ("PERCENT_25".equals(patternTypeName)) return PERCENT_25;
    	if ("PERCENT_30".equals(patternTypeName)) return PERCENT_30;
    	if ("PERCENT_40".equals(patternTypeName)) return PERCENT_40;
    	if ("PERCENT_50".equals(patternTypeName)) return PERCENT_50;
    	if ("PERCENT_5".equals(patternTypeName)) return PERCENT_5;
    	if ("PERCENT_60".equals(patternTypeName)) return PERCENT_60;
    	if ("PERCENT_70".equals(patternTypeName)) return PERCENT_70;
    	if ("PERCENT_75".equals(patternTypeName)) return PERCENT_75;
    	if ("PERCENT_80".equals(patternTypeName)) return PERCENT_80;
    	if ("PERCENT_90".equals(patternTypeName)) return PERCENT_90;
    	if ("CROSS".equals(patternTypeName)) return CROSS;
    	if ("DARK_DOWNWARD_DIAGONAL".equals(patternTypeName)) return DARK_DOWNWARD_DIAGONAL;
    	if ("DARK_HORIZONTAL".equals(patternTypeName)) return DARK_HORIZONTAL;
    	if ("DARK_UPWARD_DIAGONAL".equals(patternTypeName)) return DARK_UPWARD_DIAGONAL;
    	if ("DARK_VERTICAL".equals(patternTypeName)) return DARK_VERTICAL;
    	if ("DASHED_DOWNWARD_DIAGONAL".equals(patternTypeName)) return DASHED_DOWNWARD_DIAGONAL;
    	if ("DASHED_HORIZONTAL".equals(patternTypeName)) return DASHED_HORIZONTAL;
    	if ("DASHED_UPWARD_DIAGONAL".equals(patternTypeName)) return DASHED_UPWARD_DIAGONAL;
    	if ("DASHED_VERTICAL".equals(patternTypeName)) return DASHED_VERTICAL;
    	if ("DIAGONAL_BRICK".equals(patternTypeName)) return DIAGONAL_BRICK;
    	if ("DIAGONAL_CROSS".equals(patternTypeName)) return DIAGONAL_CROSS;
    	if ("DIVOT".equals(patternTypeName)) return DIVOT;
    	if ("DOTTED_DIAMOND".equals(patternTypeName)) return DOTTED_DIAMOND;
    	if ("DOTTED_GRID".equals(patternTypeName)) return DOTTED_GRID;
    	if ("DOWNWARD_DIAGONAL".equals(patternTypeName)) return DOWNWARD_DIAGONAL;
    	if ("HORIZONTAL".equals(patternTypeName)) return HORIZONTAL;
    	if ("HORIZONTAL_BRICK".equals(patternTypeName)) return HORIZONTAL_BRICK;
    	if ("LARGE_CHECKER_BOARD".equals(patternTypeName)) return LARGE_CHECKER_BOARD;
    	if ("LARGE_CONFETTI".equals(patternTypeName)) return LARGE_CONFETTI;
    	if ("LARGE_GRID".equals(patternTypeName)) return LARGE_GRID;
    	if ("LIGHT_DOWNWARD_DIAGONAL".equals(patternTypeName)) return LIGHT_DOWNWARD_DIAGONAL;
    	if ("LIGHT_HORIZONTAL".equals(patternTypeName)) return LIGHT_HORIZONTAL;
    	if ("LIGHT_UPWARD_DIAGONAL".equals(patternTypeName)) return LIGHT_UPWARD_DIAGONAL;
    	if ("LIGHT_VERTICAL".equals(patternTypeName)) return LIGHT_VERTICAL;
    	if ("NARROW_HORIZONTAL".equals(patternTypeName)) return NARROW_HORIZONTAL;
    	if ("NARROW_VERTICAL".equals(patternTypeName)) return NARROW_VERTICAL;
    	if ("OUTLINED_DIAMOND".equals(patternTypeName)) return OUTLINED_DIAMOND;
    	if ("PLAID".equals(patternTypeName)) return PLAID;
    	if ("SHINGLE".equals(patternTypeName)) return SHINGLE;
    	if ("SMALL_CHECKER_BOARD".equals(patternTypeName)) return SMALL_CHECKER_BOARD;
    	if ("SMALL_CONFETTI".equals(patternTypeName)) return SMALL_CONFETTI;
    	if ("SMALL_GRID".equals(patternTypeName)) return SMALL_GRID;
    	if ("SOLID_DIAMOND".equals(patternTypeName)) return SOLID_DIAMOND;
    	if ("SPHERE".equals(patternTypeName)) return SPHERE;
    	if ("TRELLIS".equals(patternTypeName)) return TRELLIS;
    	if ("UPWARD_DIAGONAL".equals(patternTypeName)) return UPWARD_DIAGONAL;
    	if ("VERTICAL".equals(patternTypeName)) return VERTICAL;
    	if ("WAVE".equals(patternTypeName)) return WAVE;
    	if ("WEAVE".equals(patternTypeName)) return WEAVE;
    	if ("WIDE_DOWNWARD_DIAGONAL".equals(patternTypeName)) return WIDE_DOWNWARD_DIAGONAL;
    	if ("WIDE_UPWARD_DIAGONAL".equals(patternTypeName)) return WIDE_UPWARD_DIAGONAL;
    	if ("ZIG_ZAG".equals(patternTypeName)) return ZIG_ZAG;
    	throw new IllegalArgumentException("Unknown PatternType name.");
    }
    
    /// <summary>
    /// Returns array of PatternType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		PERCENT_10,
    		PERCENT_20,
    		PERCENT_25,
    		PERCENT_30,
    		PERCENT_40,
    		PERCENT_50,
    		PERCENT_5,
    		PERCENT_60,
    		PERCENT_70,
    		PERCENT_75,
    		PERCENT_80,
    		PERCENT_90,
    		CROSS,
    		DARK_DOWNWARD_DIAGONAL,
    		DARK_HORIZONTAL,
    		DARK_UPWARD_DIAGONAL,
    		DARK_VERTICAL,
    		DASHED_DOWNWARD_DIAGONAL,
    		DASHED_HORIZONTAL,
    		DASHED_UPWARD_DIAGONAL,
    		DASHED_VERTICAL,
    		DIAGONAL_BRICK,
    		DIAGONAL_CROSS,
    		DIVOT,
    		DOTTED_DIAMOND,
    		DOTTED_GRID,
    		DOWNWARD_DIAGONAL,
    		HORIZONTAL,
    		HORIZONTAL_BRICK,
    		LARGE_CHECKER_BOARD,
    		LARGE_CONFETTI,
    		LARGE_GRID,
    		LIGHT_DOWNWARD_DIAGONAL,
    		LIGHT_HORIZONTAL,
    		LIGHT_UPWARD_DIAGONAL,
    		LIGHT_VERTICAL,
    		NARROW_HORIZONTAL,
    		NARROW_VERTICAL,
    		OUTLINED_DIAMOND,
    		PLAID,
    		SHINGLE,
    		SMALL_CHECKER_BOARD,
    		SMALL_CONFETTI,
    		SMALL_GRID,
    		SOLID_DIAMOND,
    		SPHERE,
    		TRELLIS,
    		UPWARD_DIAGONAL,
    		VERTICAL,
    		WAVE,
    		WEAVE,
    		WIDE_DOWNWARD_DIAGONAL,
    		WIDE_UPWARD_DIAGONAL,
    		ZIG_ZAG,
    	};
    }

    public static final int length = 55;
}

