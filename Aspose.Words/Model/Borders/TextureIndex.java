// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/04/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies shading texture.
/// </summary>
public /*enum*/ final class TextureIndex
{
    private TextureIndex(){}
    
    /// <summary>
    /// </summary>
    public static final int TEXTURE_10_PERCENT = 3;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_12_PT_5_PERCENT = 37;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_15_PERCENT = 38;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_17_PT_5_PERCENT = 39;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_20_PERCENT = 4;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_22_PT_5_PERCENT = 40;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_25_PERCENT = 5;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_27_PT_5_PERCENT = 41;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_2_PT_5_PERCENT = 35;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_30_PERCENT = 6;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_32_PT_5_PERCENT = 42;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_35_PERCENT = 43;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_37_PT_5_PERCENT = 44;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_40_PERCENT = 7;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_42_PT_5_PERCENT = 45;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_45_PERCENT = 46;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_47_PT_5_PERCENT = 47;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_50_PERCENT = 8;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_52_PT_5_PERCENT = 48;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_55_PERCENT = 49;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_57_PT_5_PERCENT = 50;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_5_PERCENT = 2;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_60_PERCENT = 9;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_62_PT_5_PERCENT = 51;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_65_PERCENT = 52;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_67_PT_5_PERCENT = 53;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_70_PERCENT = 10;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_72_PT_5_PERCENT = 54;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_75_PERCENT = 11;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_77_PT_5_PERCENT = 55;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_7_PT_5_PERCENT = 36;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_80_PERCENT = 12;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_82_PT_5_PERCENT = 56;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_85_PERCENT = 57;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_87_PT_5_PERCENT = 58;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_90_PERCENT = 13;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_92_PT_5_PERCENT = 59;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_95_PERCENT = 60;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_97_PT_5_PERCENT = 61;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_CROSS = 24;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_DARK_CROSS = 18;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_DARK_DIAGONAL_CROSS = 19;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_DARK_DIAGONAL_DOWN = 16;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_DARK_DIAGONAL_UP = 17;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_DARK_HORIZONTAL = 14;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_DARK_VERTICAL = 15;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_DIAGONAL_CROSS = 25;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_DIAGONAL_DOWN = 22;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_DIAGONAL_UP = 23;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_HORIZONTAL = 20;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_NONE = 0;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_SOLID = 1;
    /// <summary>
    /// </summary>
    public static final int TEXTURE_VERTICAL = 21;
    /// <summary>
    /// Specifies that there shall be no pattern used on the current shaded region 
    /// (i.e. the pattern shall be a complete fill with the background color).
    /// </summary>
    /// <dev>
    /// Actually the Word does not renders shading with such pattern type i.e. shading is invisible (WORDSNET-17402).
    /// </dev>
    public static final int TEXTURE_NIL = 65535;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TextureIndex value.
    /// </summary>
    public static String getName(/*TextureIndex*/int textureIndex)
    {
    	switch (textureIndex)
    	{
    		case TEXTURE_10_PERCENT: return "TEXTURE_10_PERCENT";
    		case TEXTURE_12_PT_5_PERCENT: return "TEXTURE_12_PT_5_PERCENT";
    		case TEXTURE_15_PERCENT: return "TEXTURE_15_PERCENT";
    		case TEXTURE_17_PT_5_PERCENT: return "TEXTURE_17_PT_5_PERCENT";
    		case TEXTURE_20_PERCENT: return "TEXTURE_20_PERCENT";
    		case TEXTURE_22_PT_5_PERCENT: return "TEXTURE_22_PT_5_PERCENT";
    		case TEXTURE_25_PERCENT: return "TEXTURE_25_PERCENT";
    		case TEXTURE_27_PT_5_PERCENT: return "TEXTURE_27_PT_5_PERCENT";
    		case TEXTURE_2_PT_5_PERCENT: return "TEXTURE_2_PT_5_PERCENT";
    		case TEXTURE_30_PERCENT: return "TEXTURE_30_PERCENT";
    		case TEXTURE_32_PT_5_PERCENT: return "TEXTURE_32_PT_5_PERCENT";
    		case TEXTURE_35_PERCENT: return "TEXTURE_35_PERCENT";
    		case TEXTURE_37_PT_5_PERCENT: return "TEXTURE_37_PT_5_PERCENT";
    		case TEXTURE_40_PERCENT: return "TEXTURE_40_PERCENT";
    		case TEXTURE_42_PT_5_PERCENT: return "TEXTURE_42_PT_5_PERCENT";
    		case TEXTURE_45_PERCENT: return "TEXTURE_45_PERCENT";
    		case TEXTURE_47_PT_5_PERCENT: return "TEXTURE_47_PT_5_PERCENT";
    		case TEXTURE_50_PERCENT: return "TEXTURE_50_PERCENT";
    		case TEXTURE_52_PT_5_PERCENT: return "TEXTURE_52_PT_5_PERCENT";
    		case TEXTURE_55_PERCENT: return "TEXTURE_55_PERCENT";
    		case TEXTURE_57_PT_5_PERCENT: return "TEXTURE_57_PT_5_PERCENT";
    		case TEXTURE_5_PERCENT: return "TEXTURE_5_PERCENT";
    		case TEXTURE_60_PERCENT: return "TEXTURE_60_PERCENT";
    		case TEXTURE_62_PT_5_PERCENT: return "TEXTURE_62_PT_5_PERCENT";
    		case TEXTURE_65_PERCENT: return "TEXTURE_65_PERCENT";
    		case TEXTURE_67_PT_5_PERCENT: return "TEXTURE_67_PT_5_PERCENT";
    		case TEXTURE_70_PERCENT: return "TEXTURE_70_PERCENT";
    		case TEXTURE_72_PT_5_PERCENT: return "TEXTURE_72_PT_5_PERCENT";
    		case TEXTURE_75_PERCENT: return "TEXTURE_75_PERCENT";
    		case TEXTURE_77_PT_5_PERCENT: return "TEXTURE_77_PT_5_PERCENT";
    		case TEXTURE_7_PT_5_PERCENT: return "TEXTURE_7_PT_5_PERCENT";
    		case TEXTURE_80_PERCENT: return "TEXTURE_80_PERCENT";
    		case TEXTURE_82_PT_5_PERCENT: return "TEXTURE_82_PT_5_PERCENT";
    		case TEXTURE_85_PERCENT: return "TEXTURE_85_PERCENT";
    		case TEXTURE_87_PT_5_PERCENT: return "TEXTURE_87_PT_5_PERCENT";
    		case TEXTURE_90_PERCENT: return "TEXTURE_90_PERCENT";
    		case TEXTURE_92_PT_5_PERCENT: return "TEXTURE_92_PT_5_PERCENT";
    		case TEXTURE_95_PERCENT: return "TEXTURE_95_PERCENT";
    		case TEXTURE_97_PT_5_PERCENT: return "TEXTURE_97_PT_5_PERCENT";
    		case TEXTURE_CROSS: return "TEXTURE_CROSS";
    		case TEXTURE_DARK_CROSS: return "TEXTURE_DARK_CROSS";
    		case TEXTURE_DARK_DIAGONAL_CROSS: return "TEXTURE_DARK_DIAGONAL_CROSS";
    		case TEXTURE_DARK_DIAGONAL_DOWN: return "TEXTURE_DARK_DIAGONAL_DOWN";
    		case TEXTURE_DARK_DIAGONAL_UP: return "TEXTURE_DARK_DIAGONAL_UP";
    		case TEXTURE_DARK_HORIZONTAL: return "TEXTURE_DARK_HORIZONTAL";
    		case TEXTURE_DARK_VERTICAL: return "TEXTURE_DARK_VERTICAL";
    		case TEXTURE_DIAGONAL_CROSS: return "TEXTURE_DIAGONAL_CROSS";
    		case TEXTURE_DIAGONAL_DOWN: return "TEXTURE_DIAGONAL_DOWN";
    		case TEXTURE_DIAGONAL_UP: return "TEXTURE_DIAGONAL_UP";
    		case TEXTURE_HORIZONTAL: return "TEXTURE_HORIZONTAL";
    		case TEXTURE_NONE: return "TEXTURE_NONE";
    		case TEXTURE_SOLID: return "TEXTURE_SOLID";
    		case TEXTURE_VERTICAL: return "TEXTURE_VERTICAL";
    		case TEXTURE_NIL: return "TEXTURE_NIL";
    		default: return "Unknown TextureIndex value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TextureIndex value.
    /// </summary>
    public static String toString(/*TextureIndex*/int textureIndex)
    {
    	switch (textureIndex)
    	{
    		case TEXTURE_10_PERCENT: return "Texture10Percent";
    		case TEXTURE_12_PT_5_PERCENT: return "Texture12Pt5Percent";
    		case TEXTURE_15_PERCENT: return "Texture15Percent";
    		case TEXTURE_17_PT_5_PERCENT: return "Texture17Pt5Percent";
    		case TEXTURE_20_PERCENT: return "Texture20Percent";
    		case TEXTURE_22_PT_5_PERCENT: return "Texture22Pt5Percent";
    		case TEXTURE_25_PERCENT: return "Texture25Percent";
    		case TEXTURE_27_PT_5_PERCENT: return "Texture27Pt5Percent";
    		case TEXTURE_2_PT_5_PERCENT: return "Texture2Pt5Percent";
    		case TEXTURE_30_PERCENT: return "Texture30Percent";
    		case TEXTURE_32_PT_5_PERCENT: return "Texture32Pt5Percent";
    		case TEXTURE_35_PERCENT: return "Texture35Percent";
    		case TEXTURE_37_PT_5_PERCENT: return "Texture37Pt5Percent";
    		case TEXTURE_40_PERCENT: return "Texture40Percent";
    		case TEXTURE_42_PT_5_PERCENT: return "Texture42Pt5Percent";
    		case TEXTURE_45_PERCENT: return "Texture45Percent";
    		case TEXTURE_47_PT_5_PERCENT: return "Texture47Pt5Percent";
    		case TEXTURE_50_PERCENT: return "Texture50Percent";
    		case TEXTURE_52_PT_5_PERCENT: return "Texture52Pt5Percent";
    		case TEXTURE_55_PERCENT: return "Texture55Percent";
    		case TEXTURE_57_PT_5_PERCENT: return "Texture57Pt5Percent";
    		case TEXTURE_5_PERCENT: return "Texture5Percent";
    		case TEXTURE_60_PERCENT: return "Texture60Percent";
    		case TEXTURE_62_PT_5_PERCENT: return "Texture62Pt5Percent";
    		case TEXTURE_65_PERCENT: return "Texture65Percent";
    		case TEXTURE_67_PT_5_PERCENT: return "Texture67Pt5Percent";
    		case TEXTURE_70_PERCENT: return "Texture70Percent";
    		case TEXTURE_72_PT_5_PERCENT: return "Texture72Pt5Percent";
    		case TEXTURE_75_PERCENT: return "Texture75Percent";
    		case TEXTURE_77_PT_5_PERCENT: return "Texture77Pt5Percent";
    		case TEXTURE_7_PT_5_PERCENT: return "Texture7Pt5Percent";
    		case TEXTURE_80_PERCENT: return "Texture80Percent";
    		case TEXTURE_82_PT_5_PERCENT: return "Texture82Pt5Percent";
    		case TEXTURE_85_PERCENT: return "Texture85Percent";
    		case TEXTURE_87_PT_5_PERCENT: return "Texture87Pt5Percent";
    		case TEXTURE_90_PERCENT: return "Texture90Percent";
    		case TEXTURE_92_PT_5_PERCENT: return "Texture92Pt5Percent";
    		case TEXTURE_95_PERCENT: return "Texture95Percent";
    		case TEXTURE_97_PT_5_PERCENT: return "Texture97Pt5Percent";
    		case TEXTURE_CROSS: return "TextureCross";
    		case TEXTURE_DARK_CROSS: return "TextureDarkCross";
    		case TEXTURE_DARK_DIAGONAL_CROSS: return "TextureDarkDiagonalCross";
    		case TEXTURE_DARK_DIAGONAL_DOWN: return "TextureDarkDiagonalDown";
    		case TEXTURE_DARK_DIAGONAL_UP: return "TextureDarkDiagonalUp";
    		case TEXTURE_DARK_HORIZONTAL: return "TextureDarkHorizontal";
    		case TEXTURE_DARK_VERTICAL: return "TextureDarkVertical";
    		case TEXTURE_DIAGONAL_CROSS: return "TextureDiagonalCross";
    		case TEXTURE_DIAGONAL_DOWN: return "TextureDiagonalDown";
    		case TEXTURE_DIAGONAL_UP: return "TextureDiagonalUp";
    		case TEXTURE_HORIZONTAL: return "TextureHorizontal";
    		case TEXTURE_NONE: return "TextureNone";
    		case TEXTURE_SOLID: return "TextureSolid";
    		case TEXTURE_VERTICAL: return "TextureVertical";
    		case TEXTURE_NIL: return "TextureNil";
    		default: return "Unknown TextureIndex value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TextureIndex name.
    /// </summary>
    public static /*TextureIndex*/int fromName(String textureIndexName)
    {
    	if ("TEXTURE_10_PERCENT".equals(textureIndexName)) return TEXTURE_10_PERCENT;
    	if ("TEXTURE_12_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_12_PT_5_PERCENT;
    	if ("TEXTURE_15_PERCENT".equals(textureIndexName)) return TEXTURE_15_PERCENT;
    	if ("TEXTURE_17_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_17_PT_5_PERCENT;
    	if ("TEXTURE_20_PERCENT".equals(textureIndexName)) return TEXTURE_20_PERCENT;
    	if ("TEXTURE_22_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_22_PT_5_PERCENT;
    	if ("TEXTURE_25_PERCENT".equals(textureIndexName)) return TEXTURE_25_PERCENT;
    	if ("TEXTURE_27_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_27_PT_5_PERCENT;
    	if ("TEXTURE_2_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_2_PT_5_PERCENT;
    	if ("TEXTURE_30_PERCENT".equals(textureIndexName)) return TEXTURE_30_PERCENT;
    	if ("TEXTURE_32_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_32_PT_5_PERCENT;
    	if ("TEXTURE_35_PERCENT".equals(textureIndexName)) return TEXTURE_35_PERCENT;
    	if ("TEXTURE_37_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_37_PT_5_PERCENT;
    	if ("TEXTURE_40_PERCENT".equals(textureIndexName)) return TEXTURE_40_PERCENT;
    	if ("TEXTURE_42_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_42_PT_5_PERCENT;
    	if ("TEXTURE_45_PERCENT".equals(textureIndexName)) return TEXTURE_45_PERCENT;
    	if ("TEXTURE_47_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_47_PT_5_PERCENT;
    	if ("TEXTURE_50_PERCENT".equals(textureIndexName)) return TEXTURE_50_PERCENT;
    	if ("TEXTURE_52_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_52_PT_5_PERCENT;
    	if ("TEXTURE_55_PERCENT".equals(textureIndexName)) return TEXTURE_55_PERCENT;
    	if ("TEXTURE_57_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_57_PT_5_PERCENT;
    	if ("TEXTURE_5_PERCENT".equals(textureIndexName)) return TEXTURE_5_PERCENT;
    	if ("TEXTURE_60_PERCENT".equals(textureIndexName)) return TEXTURE_60_PERCENT;
    	if ("TEXTURE_62_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_62_PT_5_PERCENT;
    	if ("TEXTURE_65_PERCENT".equals(textureIndexName)) return TEXTURE_65_PERCENT;
    	if ("TEXTURE_67_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_67_PT_5_PERCENT;
    	if ("TEXTURE_70_PERCENT".equals(textureIndexName)) return TEXTURE_70_PERCENT;
    	if ("TEXTURE_72_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_72_PT_5_PERCENT;
    	if ("TEXTURE_75_PERCENT".equals(textureIndexName)) return TEXTURE_75_PERCENT;
    	if ("TEXTURE_77_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_77_PT_5_PERCENT;
    	if ("TEXTURE_7_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_7_PT_5_PERCENT;
    	if ("TEXTURE_80_PERCENT".equals(textureIndexName)) return TEXTURE_80_PERCENT;
    	if ("TEXTURE_82_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_82_PT_5_PERCENT;
    	if ("TEXTURE_85_PERCENT".equals(textureIndexName)) return TEXTURE_85_PERCENT;
    	if ("TEXTURE_87_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_87_PT_5_PERCENT;
    	if ("TEXTURE_90_PERCENT".equals(textureIndexName)) return TEXTURE_90_PERCENT;
    	if ("TEXTURE_92_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_92_PT_5_PERCENT;
    	if ("TEXTURE_95_PERCENT".equals(textureIndexName)) return TEXTURE_95_PERCENT;
    	if ("TEXTURE_97_PT_5_PERCENT".equals(textureIndexName)) return TEXTURE_97_PT_5_PERCENT;
    	if ("TEXTURE_CROSS".equals(textureIndexName)) return TEXTURE_CROSS;
    	if ("TEXTURE_DARK_CROSS".equals(textureIndexName)) return TEXTURE_DARK_CROSS;
    	if ("TEXTURE_DARK_DIAGONAL_CROSS".equals(textureIndexName)) return TEXTURE_DARK_DIAGONAL_CROSS;
    	if ("TEXTURE_DARK_DIAGONAL_DOWN".equals(textureIndexName)) return TEXTURE_DARK_DIAGONAL_DOWN;
    	if ("TEXTURE_DARK_DIAGONAL_UP".equals(textureIndexName)) return TEXTURE_DARK_DIAGONAL_UP;
    	if ("TEXTURE_DARK_HORIZONTAL".equals(textureIndexName)) return TEXTURE_DARK_HORIZONTAL;
    	if ("TEXTURE_DARK_VERTICAL".equals(textureIndexName)) return TEXTURE_DARK_VERTICAL;
    	if ("TEXTURE_DIAGONAL_CROSS".equals(textureIndexName)) return TEXTURE_DIAGONAL_CROSS;
    	if ("TEXTURE_DIAGONAL_DOWN".equals(textureIndexName)) return TEXTURE_DIAGONAL_DOWN;
    	if ("TEXTURE_DIAGONAL_UP".equals(textureIndexName)) return TEXTURE_DIAGONAL_UP;
    	if ("TEXTURE_HORIZONTAL".equals(textureIndexName)) return TEXTURE_HORIZONTAL;
    	if ("TEXTURE_NONE".equals(textureIndexName)) return TEXTURE_NONE;
    	if ("TEXTURE_SOLID".equals(textureIndexName)) return TEXTURE_SOLID;
    	if ("TEXTURE_VERTICAL".equals(textureIndexName)) return TEXTURE_VERTICAL;
    	if ("TEXTURE_NIL".equals(textureIndexName)) return TEXTURE_NIL;
    	throw new IllegalArgumentException("Unknown TextureIndex name.");
    }
    
    /// <summary>
    /// Returns array of TextureIndex values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TEXTURE_10_PERCENT,
    		TEXTURE_12_PT_5_PERCENT,
    		TEXTURE_15_PERCENT,
    		TEXTURE_17_PT_5_PERCENT,
    		TEXTURE_20_PERCENT,
    		TEXTURE_22_PT_5_PERCENT,
    		TEXTURE_25_PERCENT,
    		TEXTURE_27_PT_5_PERCENT,
    		TEXTURE_2_PT_5_PERCENT,
    		TEXTURE_30_PERCENT,
    		TEXTURE_32_PT_5_PERCENT,
    		TEXTURE_35_PERCENT,
    		TEXTURE_37_PT_5_PERCENT,
    		TEXTURE_40_PERCENT,
    		TEXTURE_42_PT_5_PERCENT,
    		TEXTURE_45_PERCENT,
    		TEXTURE_47_PT_5_PERCENT,
    		TEXTURE_50_PERCENT,
    		TEXTURE_52_PT_5_PERCENT,
    		TEXTURE_55_PERCENT,
    		TEXTURE_57_PT_5_PERCENT,
    		TEXTURE_5_PERCENT,
    		TEXTURE_60_PERCENT,
    		TEXTURE_62_PT_5_PERCENT,
    		TEXTURE_65_PERCENT,
    		TEXTURE_67_PT_5_PERCENT,
    		TEXTURE_70_PERCENT,
    		TEXTURE_72_PT_5_PERCENT,
    		TEXTURE_75_PERCENT,
    		TEXTURE_77_PT_5_PERCENT,
    		TEXTURE_7_PT_5_PERCENT,
    		TEXTURE_80_PERCENT,
    		TEXTURE_82_PT_5_PERCENT,
    		TEXTURE_85_PERCENT,
    		TEXTURE_87_PT_5_PERCENT,
    		TEXTURE_90_PERCENT,
    		TEXTURE_92_PT_5_PERCENT,
    		TEXTURE_95_PERCENT,
    		TEXTURE_97_PT_5_PERCENT,
    		TEXTURE_CROSS,
    		TEXTURE_DARK_CROSS,
    		TEXTURE_DARK_DIAGONAL_CROSS,
    		TEXTURE_DARK_DIAGONAL_DOWN,
    		TEXTURE_DARK_DIAGONAL_UP,
    		TEXTURE_DARK_HORIZONTAL,
    		TEXTURE_DARK_VERTICAL,
    		TEXTURE_DIAGONAL_CROSS,
    		TEXTURE_DIAGONAL_DOWN,
    		TEXTURE_DIAGONAL_UP,
    		TEXTURE_HORIZONTAL,
    		TEXTURE_NONE,
    		TEXTURE_SOLID,
    		TEXTURE_VERTICAL,
    		TEXTURE_NIL,
    	};
    }

    public static final int length = 54;
}

