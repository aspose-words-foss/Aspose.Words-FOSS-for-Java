// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/04/2021 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies texture to be used to fill a shape.
/// </summary>
public /*enum*/ final class PresetTexture
{
    private PresetTexture(){}
    
    /// <summary>
    /// No Texture.
    /// </summary>
    public static final int NONE = -1;

    /// <summary>
    /// Blue tissue paper texture.
    /// </summary>
    public static final int BLUE_TISSUE_PAPER = 1;

    /// <summary>
    /// Bouquet texture.
    /// </summary>
    public static final int BOUQUET = 2;

    /// <summary>
    /// Brown marble texture.
    /// </summary>
    public static final int BROWN_MARBLE = 3;

    /// <summary>
    /// Canvas texture.
    /// </summary>
    public static final int CANVAS = 4;

    /// <summary>
    /// Cork texture.
    /// </summary>
    public static final int CORK = 5;

    /// <summary>
    /// Denim texture.
    /// </summary>
    public static final int DENIM = 6;

    /// <summary>
    /// Fish fossil texture.
    /// </summary>
    public static final int FISH_FOSSIL = 7;

    /// <summary>
    /// Granite texture.
    /// </summary>
    public static final int GRANITE = 8;

    /// <summary>
    /// Green marble texture.
    /// </summary>
    public static final int GREEN_MARBLE = 9;

    /// <summary>
    /// Medium wood texture.
    /// </summary>
    public static final int MEDIUM_WOOD = 10;

    /// <summary>
    /// Newsprint texture.
    /// </summary>
    public static final int NEWSPRINT = 11;

    /// <summary>
    /// Oak texture.
    /// </summary>
    public static final int OAK = 12;

    /// <summary>
    /// Paper bag texture.
    /// </summary>
    public static final int PAPER_BAG = 13;

    /// <summary>
    /// Papyrus texture.
    /// </summary>
    public static final int PAPYRUS = 14;

    /// <summary>
    /// Parchment texture.
    /// </summary>
    public static final int PARCHMENT = 15;

    /// <summary>
    /// Pink tissue paper texture.
    /// </summary>
    public static final int PINK_TISSUE_PAPER = 16;

    /// <summary>
    /// Purple mesh texture.
    /// </summary>
    public static final int PURPLE_MESH = 17;

    /// <summary>
    /// Recycled paper texture.
    /// </summary>
    public static final int RECYCLED_PAPER = 18;

    /// <summary>
    /// Sand texture.
    /// </summary>
    public static final int SAND = 19;

    /// <summary>
    /// Stationery texture.
    /// </summary>
    public static final int STATIONERY = 20;

    /// <summary>
    /// Walnut texture.
    /// </summary>
    public static final int WALNUT = 21;

    /// <summary>
    /// Water droplets texture.
    /// </summary>
    public static final int WATER_DROPLETS = 22;

    /// <summary>
    /// White marble texture.
    /// </summary>
    public static final int WHITE_MARBLE = 23;

    /// <summary>
    /// Woven mat texture.
    /// </summary>
    public static final int WOVEN_MAT = 24;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PresetTexture value.
    /// </summary>
    public static String getName(/*PresetTexture*/int presetTexture)
    {
    	switch (presetTexture)
    	{
    		case NONE: return "NONE";
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
    		default: return "Unknown PresetTexture value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PresetTexture value.
    /// </summary>
    public static String toString(/*PresetTexture*/int presetTexture)
    {
    	switch (presetTexture)
    	{
    		case NONE: return "None";
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
    		default: return "Unknown PresetTexture value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PresetTexture name.
    /// </summary>
    public static /*PresetTexture*/int fromName(String presetTextureName)
    {
    	if ("NONE".equals(presetTextureName)) return NONE;
    	if ("BLUE_TISSUE_PAPER".equals(presetTextureName)) return BLUE_TISSUE_PAPER;
    	if ("BOUQUET".equals(presetTextureName)) return BOUQUET;
    	if ("BROWN_MARBLE".equals(presetTextureName)) return BROWN_MARBLE;
    	if ("CANVAS".equals(presetTextureName)) return CANVAS;
    	if ("CORK".equals(presetTextureName)) return CORK;
    	if ("DENIM".equals(presetTextureName)) return DENIM;
    	if ("FISH_FOSSIL".equals(presetTextureName)) return FISH_FOSSIL;
    	if ("GRANITE".equals(presetTextureName)) return GRANITE;
    	if ("GREEN_MARBLE".equals(presetTextureName)) return GREEN_MARBLE;
    	if ("MEDIUM_WOOD".equals(presetTextureName)) return MEDIUM_WOOD;
    	if ("NEWSPRINT".equals(presetTextureName)) return NEWSPRINT;
    	if ("OAK".equals(presetTextureName)) return OAK;
    	if ("PAPER_BAG".equals(presetTextureName)) return PAPER_BAG;
    	if ("PAPYRUS".equals(presetTextureName)) return PAPYRUS;
    	if ("PARCHMENT".equals(presetTextureName)) return PARCHMENT;
    	if ("PINK_TISSUE_PAPER".equals(presetTextureName)) return PINK_TISSUE_PAPER;
    	if ("PURPLE_MESH".equals(presetTextureName)) return PURPLE_MESH;
    	if ("RECYCLED_PAPER".equals(presetTextureName)) return RECYCLED_PAPER;
    	if ("SAND".equals(presetTextureName)) return SAND;
    	if ("STATIONERY".equals(presetTextureName)) return STATIONERY;
    	if ("WALNUT".equals(presetTextureName)) return WALNUT;
    	if ("WATER_DROPLETS".equals(presetTextureName)) return WATER_DROPLETS;
    	if ("WHITE_MARBLE".equals(presetTextureName)) return WHITE_MARBLE;
    	if ("WOVEN_MAT".equals(presetTextureName)) return WOVEN_MAT;
    	throw new IllegalArgumentException("Unknown PresetTexture name.");
    }
    
    /// <summary>
    /// Returns array of PresetTexture values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
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
    	};
    }

    public static final int length = 25;
}

