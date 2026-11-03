// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 20.1.10.30 ST_LightRigType (Light Rig Type)
/// Represents a preset light right that can be applied to a shape. 
/// The light rig represents a group of lights oriented in a specific way relative to a 3D scene.
/// </summary>
/*enum*/ final class DmlLightRigType
{
    private DmlLightRigType(){}
    
    public static final int LEGACY_FLAT_1 = 0;
    public static final int LEGACY_FLAT_2 = 1;
    public static final int LEGACY_FLAT_3 = 2;
    public static final int LEGACY_FLAT_4 = 3;
    public static final int LEGACY_NORMAL_1 = 4;
    public static final int LEGACY_NORMAL_2 = 5;
    public static final int LEGACY_NORMAL_3 = 6;
    public static final int LEGACY_NORMAL_4 = 7;
    public static final int LEGACY_HARSH_1 = 8;
    public static final int LEGACY_HARSH_2 = 9;
    public static final int LEGACY_HARSH_3 = 10;
    public static final int LEGACY_HARSH_4 = 11;
    public static final int THREE_PT = 12;
    public static final int BALANCED = 13;
    public static final int SOFT = 14;
    public static final int HARSH = 15;
    public static final int FLOOD = 16;
    public static final int CONTRASTING = 17;
    public static final int MORNING = 18;
    public static final int SUNRISE = 19;
    public static final int SUNSET = 20;
    public static final int CHILLY = 21;
    public static final int FREEZING = 22;
    public static final int FLAT = 23;
    public static final int TWO_PT = 24;
    public static final int GLOW = 25;
    public static final int BRIGHT_ROOM = 26;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlLightRigType value.
    /// </summary>
    public static String getName(/*DmlLightRigType*/int dmlLightRigType)
    {
    	switch (dmlLightRigType)
    	{
    		case LEGACY_FLAT_1: return "LEGACY_FLAT_1";
    		case LEGACY_FLAT_2: return "LEGACY_FLAT_2";
    		case LEGACY_FLAT_3: return "LEGACY_FLAT_3";
    		case LEGACY_FLAT_4: return "LEGACY_FLAT_4";
    		case LEGACY_NORMAL_1: return "LEGACY_NORMAL_1";
    		case LEGACY_NORMAL_2: return "LEGACY_NORMAL_2";
    		case LEGACY_NORMAL_3: return "LEGACY_NORMAL_3";
    		case LEGACY_NORMAL_4: return "LEGACY_NORMAL_4";
    		case LEGACY_HARSH_1: return "LEGACY_HARSH_1";
    		case LEGACY_HARSH_2: return "LEGACY_HARSH_2";
    		case LEGACY_HARSH_3: return "LEGACY_HARSH_3";
    		case LEGACY_HARSH_4: return "LEGACY_HARSH_4";
    		case THREE_PT: return "THREE_PT";
    		case BALANCED: return "BALANCED";
    		case SOFT: return "SOFT";
    		case HARSH: return "HARSH";
    		case FLOOD: return "FLOOD";
    		case CONTRASTING: return "CONTRASTING";
    		case MORNING: return "MORNING";
    		case SUNRISE: return "SUNRISE";
    		case SUNSET: return "SUNSET";
    		case CHILLY: return "CHILLY";
    		case FREEZING: return "FREEZING";
    		case FLAT: return "FLAT";
    		case TWO_PT: return "TWO_PT";
    		case GLOW: return "GLOW";
    		case BRIGHT_ROOM: return "BRIGHT_ROOM";
    		default: return "Unknown DmlLightRigType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlLightRigType value.
    /// </summary>
    public static String toString(/*DmlLightRigType*/int dmlLightRigType)
    {
    	switch (dmlLightRigType)
    	{
    		case LEGACY_FLAT_1: return "LegacyFlat1";
    		case LEGACY_FLAT_2: return "LegacyFlat2";
    		case LEGACY_FLAT_3: return "LegacyFlat3";
    		case LEGACY_FLAT_4: return "LegacyFlat4";
    		case LEGACY_NORMAL_1: return "LegacyNormal1";
    		case LEGACY_NORMAL_2: return "LegacyNormal2";
    		case LEGACY_NORMAL_3: return "LegacyNormal3";
    		case LEGACY_NORMAL_4: return "LegacyNormal4";
    		case LEGACY_HARSH_1: return "LegacyHarsh1";
    		case LEGACY_HARSH_2: return "LegacyHarsh2";
    		case LEGACY_HARSH_3: return "LegacyHarsh3";
    		case LEGACY_HARSH_4: return "LegacyHarsh4";
    		case THREE_PT: return "ThreePt";
    		case BALANCED: return "Balanced";
    		case SOFT: return "Soft";
    		case HARSH: return "Harsh";
    		case FLOOD: return "Flood";
    		case CONTRASTING: return "Contrasting";
    		case MORNING: return "Morning";
    		case SUNRISE: return "Sunrise";
    		case SUNSET: return "Sunset";
    		case CHILLY: return "Chilly";
    		case FREEZING: return "Freezing";
    		case FLAT: return "Flat";
    		case TWO_PT: return "TwoPt";
    		case GLOW: return "Glow";
    		case BRIGHT_ROOM: return "BrightRoom";
    		default: return "Unknown DmlLightRigType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlLightRigType name.
    /// </summary>
    public static /*DmlLightRigType*/int fromName(String dmlLightRigTypeName)
    {
    	if ("LEGACY_FLAT_1".equals(dmlLightRigTypeName)) return LEGACY_FLAT_1;
    	if ("LEGACY_FLAT_2".equals(dmlLightRigTypeName)) return LEGACY_FLAT_2;
    	if ("LEGACY_FLAT_3".equals(dmlLightRigTypeName)) return LEGACY_FLAT_3;
    	if ("LEGACY_FLAT_4".equals(dmlLightRigTypeName)) return LEGACY_FLAT_4;
    	if ("LEGACY_NORMAL_1".equals(dmlLightRigTypeName)) return LEGACY_NORMAL_1;
    	if ("LEGACY_NORMAL_2".equals(dmlLightRigTypeName)) return LEGACY_NORMAL_2;
    	if ("LEGACY_NORMAL_3".equals(dmlLightRigTypeName)) return LEGACY_NORMAL_3;
    	if ("LEGACY_NORMAL_4".equals(dmlLightRigTypeName)) return LEGACY_NORMAL_4;
    	if ("LEGACY_HARSH_1".equals(dmlLightRigTypeName)) return LEGACY_HARSH_1;
    	if ("LEGACY_HARSH_2".equals(dmlLightRigTypeName)) return LEGACY_HARSH_2;
    	if ("LEGACY_HARSH_3".equals(dmlLightRigTypeName)) return LEGACY_HARSH_3;
    	if ("LEGACY_HARSH_4".equals(dmlLightRigTypeName)) return LEGACY_HARSH_4;
    	if ("THREE_PT".equals(dmlLightRigTypeName)) return THREE_PT;
    	if ("BALANCED".equals(dmlLightRigTypeName)) return BALANCED;
    	if ("SOFT".equals(dmlLightRigTypeName)) return SOFT;
    	if ("HARSH".equals(dmlLightRigTypeName)) return HARSH;
    	if ("FLOOD".equals(dmlLightRigTypeName)) return FLOOD;
    	if ("CONTRASTING".equals(dmlLightRigTypeName)) return CONTRASTING;
    	if ("MORNING".equals(dmlLightRigTypeName)) return MORNING;
    	if ("SUNRISE".equals(dmlLightRigTypeName)) return SUNRISE;
    	if ("SUNSET".equals(dmlLightRigTypeName)) return SUNSET;
    	if ("CHILLY".equals(dmlLightRigTypeName)) return CHILLY;
    	if ("FREEZING".equals(dmlLightRigTypeName)) return FREEZING;
    	if ("FLAT".equals(dmlLightRigTypeName)) return FLAT;
    	if ("TWO_PT".equals(dmlLightRigTypeName)) return TWO_PT;
    	if ("GLOW".equals(dmlLightRigTypeName)) return GLOW;
    	if ("BRIGHT_ROOM".equals(dmlLightRigTypeName)) return BRIGHT_ROOM;
    	throw new IllegalArgumentException("Unknown DmlLightRigType name.");
    }
    
    /// <summary>
    /// Returns array of DmlLightRigType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEGACY_FLAT_1,
    		LEGACY_FLAT_2,
    		LEGACY_FLAT_3,
    		LEGACY_FLAT_4,
    		LEGACY_NORMAL_1,
    		LEGACY_NORMAL_2,
    		LEGACY_NORMAL_3,
    		LEGACY_NORMAL_4,
    		LEGACY_HARSH_1,
    		LEGACY_HARSH_2,
    		LEGACY_HARSH_3,
    		LEGACY_HARSH_4,
    		THREE_PT,
    		BALANCED,
    		SOFT,
    		HARSH,
    		FLOOD,
    		CONTRASTING,
    		MORNING,
    		SUNRISE,
    		SUNSET,
    		CHILLY,
    		FREEZING,
    		FLAT,
    		TWO_PT,
    		GLOW,
    		BRIGHT_ROOM,
    	};
    }

    public static final int length = 27;
}

