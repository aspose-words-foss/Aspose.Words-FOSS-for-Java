// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 20.1.10.49 ST_PresetMaterialType (Preset Material Type)
/// Describes surface appearance of a shape. 
/// The material type combines with lighting characteristics to create the final look and feel of a shape.
/// </summary>
/*enum*/ final class DmlPresetMaterialType
{
    private DmlPresetMaterialType(){}
    
    public static final int LEGACY_MATTE = 0;
    public static final int LEGACY_PLASTIC = 1;
    public static final int LEGACY_METAL = 2;
    public static final int LEGACY_WIREFRAME = 3;
    public static final int MATTE = 4;
    public static final int PLASTIC = 5;
    public static final int METAL = 6;
    public static final int WARM_MATTE = 7;
    public static final int TRANSLUCENT_POWDER = 8;
    public static final int POWDER = 9;
    public static final int DARK_EDGE = 10;
    public static final int SOFT_EDGE = 11;
    public static final int CLEAR = 12;
    public static final int FLAT = 13;
    public static final int SOFT_METAL = 14;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlPresetMaterialType value.
    /// </summary>
    public static String getName(/*DmlPresetMaterialType*/int dmlPresetMaterialType)
    {
    	switch (dmlPresetMaterialType)
    	{
    		case LEGACY_MATTE: return "LEGACY_MATTE";
    		case LEGACY_PLASTIC: return "LEGACY_PLASTIC";
    		case LEGACY_METAL: return "LEGACY_METAL";
    		case LEGACY_WIREFRAME: return "LEGACY_WIREFRAME";
    		case MATTE: return "MATTE";
    		case PLASTIC: return "PLASTIC";
    		case METAL: return "METAL";
    		case WARM_MATTE: return "WARM_MATTE";
    		case TRANSLUCENT_POWDER: return "TRANSLUCENT_POWDER";
    		case POWDER: return "POWDER";
    		case DARK_EDGE: return "DARK_EDGE";
    		case SOFT_EDGE: return "SOFT_EDGE";
    		case CLEAR: return "CLEAR";
    		case FLAT: return "FLAT";
    		case SOFT_METAL: return "SOFT_METAL";
    		default: return "Unknown DmlPresetMaterialType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlPresetMaterialType value.
    /// </summary>
    public static String toString(/*DmlPresetMaterialType*/int dmlPresetMaterialType)
    {
    	switch (dmlPresetMaterialType)
    	{
    		case LEGACY_MATTE: return "LegacyMatte";
    		case LEGACY_PLASTIC: return "LegacyPlastic";
    		case LEGACY_METAL: return "LegacyMetal";
    		case LEGACY_WIREFRAME: return "LegacyWireframe";
    		case MATTE: return "Matte";
    		case PLASTIC: return "Plastic";
    		case METAL: return "Metal";
    		case WARM_MATTE: return "WarmMatte";
    		case TRANSLUCENT_POWDER: return "TranslucentPowder";
    		case POWDER: return "Powder";
    		case DARK_EDGE: return "DarkEdge";
    		case SOFT_EDGE: return "SoftEdge";
    		case CLEAR: return "Clear";
    		case FLAT: return "Flat";
    		case SOFT_METAL: return "SoftMetal";
    		default: return "Unknown DmlPresetMaterialType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlPresetMaterialType name.
    /// </summary>
    public static /*DmlPresetMaterialType*/int fromName(String dmlPresetMaterialTypeName)
    {
    	if ("LEGACY_MATTE".equals(dmlPresetMaterialTypeName)) return LEGACY_MATTE;
    	if ("LEGACY_PLASTIC".equals(dmlPresetMaterialTypeName)) return LEGACY_PLASTIC;
    	if ("LEGACY_METAL".equals(dmlPresetMaterialTypeName)) return LEGACY_METAL;
    	if ("LEGACY_WIREFRAME".equals(dmlPresetMaterialTypeName)) return LEGACY_WIREFRAME;
    	if ("MATTE".equals(dmlPresetMaterialTypeName)) return MATTE;
    	if ("PLASTIC".equals(dmlPresetMaterialTypeName)) return PLASTIC;
    	if ("METAL".equals(dmlPresetMaterialTypeName)) return METAL;
    	if ("WARM_MATTE".equals(dmlPresetMaterialTypeName)) return WARM_MATTE;
    	if ("TRANSLUCENT_POWDER".equals(dmlPresetMaterialTypeName)) return TRANSLUCENT_POWDER;
    	if ("POWDER".equals(dmlPresetMaterialTypeName)) return POWDER;
    	if ("DARK_EDGE".equals(dmlPresetMaterialTypeName)) return DARK_EDGE;
    	if ("SOFT_EDGE".equals(dmlPresetMaterialTypeName)) return SOFT_EDGE;
    	if ("CLEAR".equals(dmlPresetMaterialTypeName)) return CLEAR;
    	if ("FLAT".equals(dmlPresetMaterialTypeName)) return FLAT;
    	if ("SOFT_METAL".equals(dmlPresetMaterialTypeName)) return SOFT_METAL;
    	throw new IllegalArgumentException("Unknown DmlPresetMaterialType name.");
    }
    
    /// <summary>
    /// Returns array of DmlPresetMaterialType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEGACY_MATTE,
    		LEGACY_PLASTIC,
    		LEGACY_METAL,
    		LEGACY_WIREFRAME,
    		MATTE,
    		PLASTIC,
    		METAL,
    		WARM_MATTE,
    		TRANSLUCENT_POWDER,
    		POWDER,
    		DARK_EDGE,
    		SOFT_EDGE,
    		CLEAR,
    		FLAT,
    		SOFT_METAL,
    	};
    }

    public static final int length = 15;
}

