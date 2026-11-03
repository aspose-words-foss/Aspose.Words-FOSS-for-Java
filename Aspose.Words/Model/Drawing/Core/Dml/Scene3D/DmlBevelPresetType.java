// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 20.1.10.9 ST_BevelPresetType (Bevel Presets)
/// Represents a preset for a type of bevel which can be applied to a shape in 3D. 
/// The bevel properties are applied differently depending on the type of bevel defined for a shape.
/// </summary>
/*enum*/ final class DmlBevelPresetType
{
    private DmlBevelPresetType(){}
    
    public static final int RELAXED_INSET = 0;
    public static final int CIRCLE = 1;
    public static final int SLOPE = 2;
    public static final int CROSS = 3;
    public static final int ANGLE = 4;
    public static final int SOFT_ROUND = 5;
    public static final int CONVEX = 6;
    public static final int COOL_SLANT = 7;
    public static final int DIVOT = 8;
    public static final int RIBLET = 9;
    public static final int HARD_EDGE = 10;
    public static final int ART_DECO = 11;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlBevelPresetType value.
    /// </summary>
    public static String getName(/*DmlBevelPresetType*/int dmlBevelPresetType)
    {
    	switch (dmlBevelPresetType)
    	{
    		case RELAXED_INSET: return "RELAXED_INSET";
    		case CIRCLE: return "CIRCLE";
    		case SLOPE: return "SLOPE";
    		case CROSS: return "CROSS";
    		case ANGLE: return "ANGLE";
    		case SOFT_ROUND: return "SOFT_ROUND";
    		case CONVEX: return "CONVEX";
    		case COOL_SLANT: return "COOL_SLANT";
    		case DIVOT: return "DIVOT";
    		case RIBLET: return "RIBLET";
    		case HARD_EDGE: return "HARD_EDGE";
    		case ART_DECO: return "ART_DECO";
    		default: return "Unknown DmlBevelPresetType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlBevelPresetType value.
    /// </summary>
    public static String toString(/*DmlBevelPresetType*/int dmlBevelPresetType)
    {
    	switch (dmlBevelPresetType)
    	{
    		case RELAXED_INSET: return "RelaxedInset";
    		case CIRCLE: return "Circle";
    		case SLOPE: return "Slope";
    		case CROSS: return "Cross";
    		case ANGLE: return "Angle";
    		case SOFT_ROUND: return "SoftRound";
    		case CONVEX: return "Convex";
    		case COOL_SLANT: return "CoolSlant";
    		case DIVOT: return "Divot";
    		case RIBLET: return "Riblet";
    		case HARD_EDGE: return "HardEdge";
    		case ART_DECO: return "ArtDeco";
    		default: return "Unknown DmlBevelPresetType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlBevelPresetType name.
    /// </summary>
    public static /*DmlBevelPresetType*/int fromName(String dmlBevelPresetTypeName)
    {
    	if ("RELAXED_INSET".equals(dmlBevelPresetTypeName)) return RELAXED_INSET;
    	if ("CIRCLE".equals(dmlBevelPresetTypeName)) return CIRCLE;
    	if ("SLOPE".equals(dmlBevelPresetTypeName)) return SLOPE;
    	if ("CROSS".equals(dmlBevelPresetTypeName)) return CROSS;
    	if ("ANGLE".equals(dmlBevelPresetTypeName)) return ANGLE;
    	if ("SOFT_ROUND".equals(dmlBevelPresetTypeName)) return SOFT_ROUND;
    	if ("CONVEX".equals(dmlBevelPresetTypeName)) return CONVEX;
    	if ("COOL_SLANT".equals(dmlBevelPresetTypeName)) return COOL_SLANT;
    	if ("DIVOT".equals(dmlBevelPresetTypeName)) return DIVOT;
    	if ("RIBLET".equals(dmlBevelPresetTypeName)) return RIBLET;
    	if ("HARD_EDGE".equals(dmlBevelPresetTypeName)) return HARD_EDGE;
    	if ("ART_DECO".equals(dmlBevelPresetTypeName)) return ART_DECO;
    	throw new IllegalArgumentException("Unknown DmlBevelPresetType name.");
    }
    
    /// <summary>
    /// Returns array of DmlBevelPresetType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		RELAXED_INSET,
    		CIRCLE,
    		SLOPE,
    		CROSS,
    		ANGLE,
    		SOFT_ROUND,
    		CONVEX,
    		COOL_SLANT,
    		DIVOT,
    		RIBLET,
    		HARD_EDGE,
    		ART_DECO,
    	};
    }

    public static final int length = 12;
}

