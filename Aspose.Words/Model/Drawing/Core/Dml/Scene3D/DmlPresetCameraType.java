// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 20.1.10.46 ST_PresetCameraType (Preset Camera Type)
/// These enumeration values represent different algorithmic methods for setting all camera properties, including position.
/// </summary>
/*enum*/ final class DmlPresetCameraType
{
    private DmlPresetCameraType(){}
    
    public static final int LEGACY_OBLIQUE_TOP_LEFT = 0;
    public static final int LEGACY_OBLIQUE_TOP = 1;
    public static final int LEGACY_OBLIQUE_TOP_RIGHT = 2;
    public static final int LEGACY_OBLIQUE_LEFT = 3;
    public static final int LEGACY_OBLIQUE_FRONT = 4;
    public static final int LEGACY_OBLIQUE_RIGHT = 5;
    public static final int LEGACY_OBLIQUE_BOTTOM_LEFT = 6;
    public static final int LEGACY_OBLIQUE_BOTTOM = 7;
    public static final int LEGACY_OBLIQUE_BOTTOM_RIGHT = 8;
    public static final int LEGACY_PERSPECTIVE_TOP_LEFT = 9;
    public static final int LEGACY_PERSPECTIVE_TOP = 10;
    public static final int LEGACY_PERSPECTIVE_TOP_RIGHT = 11;
    public static final int LEGACY_PERSPECTIVE_LEFT = 12;
    public static final int LEGACY_PERSPECTIVE_FRONT = 13;
    public static final int LEGACY_PERSPECTIVE_RIGHT = 14;
    public static final int LEGACY_PERSPECTIVE_BOTTOM_LEFT = 15;
    public static final int LEGACY_PERSPECTIVE_BOTTOM = 16;
    public static final int LEGACY_PERSPECTIVE_BOTTOM_RIGHT = 17;
    public static final int ORTHOGRAPHIC_FRONT = 18;
    public static final int ISOMETRIC_TOP_UP = 19;
    public static final int ISOMETRIC_TOP_DOWN = 20;
    public static final int ISOMETRIC_BOTTOM_UP = 21;
    public static final int ISOMETRIC_BOTTOM_DOWN = 22;
    public static final int ISOMETRIC_LEFT_UP = 23;
    public static final int ISOMETRIC_LEFT_DOWN = 24;
    public static final int ISOMETRIC_RIGHT_UP = 25;
    public static final int ISOMETRIC_RIGHT_DOWN = 26;
    public static final int ISOMETRIC_OFF_AXIS_1_LEFT = 27;
    public static final int ISOMETRIC_OFF_AXIS_1_RIGHT = 28;
    public static final int ISOMETRIC_OFF_AXIS_1_TOP = 29;
    public static final int ISOMETRIC_OFF_AXIS_2_LEFT = 30;
    public static final int ISOMETRIC_OFF_AXIS_2_RIGHT = 31;
    public static final int ISOMETRIC_OFF_AXIS_2_TOP = 32;
    public static final int ISOMETRIC_OFF_AXIS_3_LEFT = 33;
    public static final int ISOMETRIC_OFF_AXIS_3_RIGHT = 34;
    public static final int ISOMETRIC_OFF_AXIS_3_BOTTOM = 35;
    public static final int ISOMETRIC_OFF_AXIS_4_LEFT = 36;
    public static final int ISOMETRIC_OFF_AXIS_4_RIGHT = 37;
    public static final int ISOMETRIC_OFF_AXIS_4_BOTTOM = 38;
    public static final int OBLIQUE_TOP_LEFT = 39;
    public static final int OBLIQUE_TOP = 40;
    public static final int OBLIQUE_TOP_RIGHT = 41;
    public static final int OBLIQUE_LEFT = 42;
    public static final int OBLIQUE_RIGHT = 43;
    public static final int OBLIQUE_BOTTOM_LEFT = 44;
    public static final int OBLIQUE_BOTTOM = 45;
    public static final int OBLIQUE_BOTTOM_RIGHT = 46;
    public static final int PERSPECTIVE_FRONT = 47;
    public static final int PERSPECTIVE_LEFT = 48;
    public static final int PERSPECTIVE_RIGHT = 49;
    public static final int PERSPECTIVE_ABOVE = 50;
    public static final int PERSPECTIVE_BELOW = 51;
    public static final int PERSPECTIVE_ABOVE_LEFT_FACING = 52;
    public static final int PERSPECTIVE_ABOVE_RIGHT_FACING = 53;
    public static final int PERSPECTIVE_CONTRASTING_LEFT_FACING = 54;
    public static final int PERSPECTIVE_CONTRASTING_RIGHT_FACING = 55;
    public static final int PERSPECTIVE_HEROIC_LEFT_FACING = 56;
    public static final int PERSPECTIVE_HEROIC_RIGHT_FACING = 57;
    public static final int PERSPECTIVE_HEROIC_EXTREME_LEFT_FACING = 58;
    public static final int PERSPECTIVE_HEROIC_EXTREME_RIGHT_FACING = 59;
    public static final int PERSPECTIVE_RELAXED = 60;
    public static final int PERSPECTIVE_RELAXED_MODERATELY = 61;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlPresetCameraType value.
    /// </summary>
    public static String getName(/*DmlPresetCameraType*/int dmlPresetCameraType)
    {
    	switch (dmlPresetCameraType)
    	{
    		case LEGACY_OBLIQUE_TOP_LEFT: return "LEGACY_OBLIQUE_TOP_LEFT";
    		case LEGACY_OBLIQUE_TOP: return "LEGACY_OBLIQUE_TOP";
    		case LEGACY_OBLIQUE_TOP_RIGHT: return "LEGACY_OBLIQUE_TOP_RIGHT";
    		case LEGACY_OBLIQUE_LEFT: return "LEGACY_OBLIQUE_LEFT";
    		case LEGACY_OBLIQUE_FRONT: return "LEGACY_OBLIQUE_FRONT";
    		case LEGACY_OBLIQUE_RIGHT: return "LEGACY_OBLIQUE_RIGHT";
    		case LEGACY_OBLIQUE_BOTTOM_LEFT: return "LEGACY_OBLIQUE_BOTTOM_LEFT";
    		case LEGACY_OBLIQUE_BOTTOM: return "LEGACY_OBLIQUE_BOTTOM";
    		case LEGACY_OBLIQUE_BOTTOM_RIGHT: return "LEGACY_OBLIQUE_BOTTOM_RIGHT";
    		case LEGACY_PERSPECTIVE_TOP_LEFT: return "LEGACY_PERSPECTIVE_TOP_LEFT";
    		case LEGACY_PERSPECTIVE_TOP: return "LEGACY_PERSPECTIVE_TOP";
    		case LEGACY_PERSPECTIVE_TOP_RIGHT: return "LEGACY_PERSPECTIVE_TOP_RIGHT";
    		case LEGACY_PERSPECTIVE_LEFT: return "LEGACY_PERSPECTIVE_LEFT";
    		case LEGACY_PERSPECTIVE_FRONT: return "LEGACY_PERSPECTIVE_FRONT";
    		case LEGACY_PERSPECTIVE_RIGHT: return "LEGACY_PERSPECTIVE_RIGHT";
    		case LEGACY_PERSPECTIVE_BOTTOM_LEFT: return "LEGACY_PERSPECTIVE_BOTTOM_LEFT";
    		case LEGACY_PERSPECTIVE_BOTTOM: return "LEGACY_PERSPECTIVE_BOTTOM";
    		case LEGACY_PERSPECTIVE_BOTTOM_RIGHT: return "LEGACY_PERSPECTIVE_BOTTOM_RIGHT";
    		case ORTHOGRAPHIC_FRONT: return "ORTHOGRAPHIC_FRONT";
    		case ISOMETRIC_TOP_UP: return "ISOMETRIC_TOP_UP";
    		case ISOMETRIC_TOP_DOWN: return "ISOMETRIC_TOP_DOWN";
    		case ISOMETRIC_BOTTOM_UP: return "ISOMETRIC_BOTTOM_UP";
    		case ISOMETRIC_BOTTOM_DOWN: return "ISOMETRIC_BOTTOM_DOWN";
    		case ISOMETRIC_LEFT_UP: return "ISOMETRIC_LEFT_UP";
    		case ISOMETRIC_LEFT_DOWN: return "ISOMETRIC_LEFT_DOWN";
    		case ISOMETRIC_RIGHT_UP: return "ISOMETRIC_RIGHT_UP";
    		case ISOMETRIC_RIGHT_DOWN: return "ISOMETRIC_RIGHT_DOWN";
    		case ISOMETRIC_OFF_AXIS_1_LEFT: return "ISOMETRIC_OFF_AXIS_1_LEFT";
    		case ISOMETRIC_OFF_AXIS_1_RIGHT: return "ISOMETRIC_OFF_AXIS_1_RIGHT";
    		case ISOMETRIC_OFF_AXIS_1_TOP: return "ISOMETRIC_OFF_AXIS_1_TOP";
    		case ISOMETRIC_OFF_AXIS_2_LEFT: return "ISOMETRIC_OFF_AXIS_2_LEFT";
    		case ISOMETRIC_OFF_AXIS_2_RIGHT: return "ISOMETRIC_OFF_AXIS_2_RIGHT";
    		case ISOMETRIC_OFF_AXIS_2_TOP: return "ISOMETRIC_OFF_AXIS_2_TOP";
    		case ISOMETRIC_OFF_AXIS_3_LEFT: return "ISOMETRIC_OFF_AXIS_3_LEFT";
    		case ISOMETRIC_OFF_AXIS_3_RIGHT: return "ISOMETRIC_OFF_AXIS_3_RIGHT";
    		case ISOMETRIC_OFF_AXIS_3_BOTTOM: return "ISOMETRIC_OFF_AXIS_3_BOTTOM";
    		case ISOMETRIC_OFF_AXIS_4_LEFT: return "ISOMETRIC_OFF_AXIS_4_LEFT";
    		case ISOMETRIC_OFF_AXIS_4_RIGHT: return "ISOMETRIC_OFF_AXIS_4_RIGHT";
    		case ISOMETRIC_OFF_AXIS_4_BOTTOM: return "ISOMETRIC_OFF_AXIS_4_BOTTOM";
    		case OBLIQUE_TOP_LEFT: return "OBLIQUE_TOP_LEFT";
    		case OBLIQUE_TOP: return "OBLIQUE_TOP";
    		case OBLIQUE_TOP_RIGHT: return "OBLIQUE_TOP_RIGHT";
    		case OBLIQUE_LEFT: return "OBLIQUE_LEFT";
    		case OBLIQUE_RIGHT: return "OBLIQUE_RIGHT";
    		case OBLIQUE_BOTTOM_LEFT: return "OBLIQUE_BOTTOM_LEFT";
    		case OBLIQUE_BOTTOM: return "OBLIQUE_BOTTOM";
    		case OBLIQUE_BOTTOM_RIGHT: return "OBLIQUE_BOTTOM_RIGHT";
    		case PERSPECTIVE_FRONT: return "PERSPECTIVE_FRONT";
    		case PERSPECTIVE_LEFT: return "PERSPECTIVE_LEFT";
    		case PERSPECTIVE_RIGHT: return "PERSPECTIVE_RIGHT";
    		case PERSPECTIVE_ABOVE: return "PERSPECTIVE_ABOVE";
    		case PERSPECTIVE_BELOW: return "PERSPECTIVE_BELOW";
    		case PERSPECTIVE_ABOVE_LEFT_FACING: return "PERSPECTIVE_ABOVE_LEFT_FACING";
    		case PERSPECTIVE_ABOVE_RIGHT_FACING: return "PERSPECTIVE_ABOVE_RIGHT_FACING";
    		case PERSPECTIVE_CONTRASTING_LEFT_FACING: return "PERSPECTIVE_CONTRASTING_LEFT_FACING";
    		case PERSPECTIVE_CONTRASTING_RIGHT_FACING: return "PERSPECTIVE_CONTRASTING_RIGHT_FACING";
    		case PERSPECTIVE_HEROIC_LEFT_FACING: return "PERSPECTIVE_HEROIC_LEFT_FACING";
    		case PERSPECTIVE_HEROIC_RIGHT_FACING: return "PERSPECTIVE_HEROIC_RIGHT_FACING";
    		case PERSPECTIVE_HEROIC_EXTREME_LEFT_FACING: return "PERSPECTIVE_HEROIC_EXTREME_LEFT_FACING";
    		case PERSPECTIVE_HEROIC_EXTREME_RIGHT_FACING: return "PERSPECTIVE_HEROIC_EXTREME_RIGHT_FACING";
    		case PERSPECTIVE_RELAXED: return "PERSPECTIVE_RELAXED";
    		case PERSPECTIVE_RELAXED_MODERATELY: return "PERSPECTIVE_RELAXED_MODERATELY";
    		default: return "Unknown DmlPresetCameraType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlPresetCameraType value.
    /// </summary>
    public static String toString(/*DmlPresetCameraType*/int dmlPresetCameraType)
    {
    	switch (dmlPresetCameraType)
    	{
    		case LEGACY_OBLIQUE_TOP_LEFT: return "LegacyObliqueTopLeft";
    		case LEGACY_OBLIQUE_TOP: return "LegacyObliqueTop";
    		case LEGACY_OBLIQUE_TOP_RIGHT: return "LegacyObliqueTopRight";
    		case LEGACY_OBLIQUE_LEFT: return "LegacyObliqueLeft";
    		case LEGACY_OBLIQUE_FRONT: return "LegacyObliqueFront";
    		case LEGACY_OBLIQUE_RIGHT: return "LegacyObliqueRight";
    		case LEGACY_OBLIQUE_BOTTOM_LEFT: return "LegacyObliqueBottomLeft";
    		case LEGACY_OBLIQUE_BOTTOM: return "LegacyObliqueBottom";
    		case LEGACY_OBLIQUE_BOTTOM_RIGHT: return "LegacyObliqueBottomRight";
    		case LEGACY_PERSPECTIVE_TOP_LEFT: return "LegacyPerspectiveTopLeft";
    		case LEGACY_PERSPECTIVE_TOP: return "LegacyPerspectiveTop";
    		case LEGACY_PERSPECTIVE_TOP_RIGHT: return "LegacyPerspectiveTopRight";
    		case LEGACY_PERSPECTIVE_LEFT: return "LegacyPerspectiveLeft";
    		case LEGACY_PERSPECTIVE_FRONT: return "LegacyPerspectiveFront";
    		case LEGACY_PERSPECTIVE_RIGHT: return "LegacyPerspectiveRight";
    		case LEGACY_PERSPECTIVE_BOTTOM_LEFT: return "LegacyPerspectiveBottomLeft";
    		case LEGACY_PERSPECTIVE_BOTTOM: return "LegacyPerspectiveBottom";
    		case LEGACY_PERSPECTIVE_BOTTOM_RIGHT: return "LegacyPerspectiveBottomRight";
    		case ORTHOGRAPHIC_FRONT: return "OrthographicFront";
    		case ISOMETRIC_TOP_UP: return "IsometricTopUp";
    		case ISOMETRIC_TOP_DOWN: return "IsometricTopDown";
    		case ISOMETRIC_BOTTOM_UP: return "IsometricBottomUp";
    		case ISOMETRIC_BOTTOM_DOWN: return "IsometricBottomDown";
    		case ISOMETRIC_LEFT_UP: return "IsometricLeftUp";
    		case ISOMETRIC_LEFT_DOWN: return "IsometricLeftDown";
    		case ISOMETRIC_RIGHT_UP: return "IsometricRightUp";
    		case ISOMETRIC_RIGHT_DOWN: return "IsometricRightDown";
    		case ISOMETRIC_OFF_AXIS_1_LEFT: return "IsometricOffAxis1Left";
    		case ISOMETRIC_OFF_AXIS_1_RIGHT: return "IsometricOffAxis1Right";
    		case ISOMETRIC_OFF_AXIS_1_TOP: return "IsometricOffAxis1Top";
    		case ISOMETRIC_OFF_AXIS_2_LEFT: return "IsometricOffAxis2Left";
    		case ISOMETRIC_OFF_AXIS_2_RIGHT: return "IsometricOffAxis2Right";
    		case ISOMETRIC_OFF_AXIS_2_TOP: return "IsometricOffAxis2Top";
    		case ISOMETRIC_OFF_AXIS_3_LEFT: return "IsometricOffAxis3Left";
    		case ISOMETRIC_OFF_AXIS_3_RIGHT: return "IsometricOffAxis3Right";
    		case ISOMETRIC_OFF_AXIS_3_BOTTOM: return "IsometricOffAxis3Bottom";
    		case ISOMETRIC_OFF_AXIS_4_LEFT: return "IsometricOffAxis4Left";
    		case ISOMETRIC_OFF_AXIS_4_RIGHT: return "IsometricOffAxis4Right";
    		case ISOMETRIC_OFF_AXIS_4_BOTTOM: return "IsometricOffAxis4Bottom";
    		case OBLIQUE_TOP_LEFT: return "ObliqueTopLeft";
    		case OBLIQUE_TOP: return "ObliqueTop";
    		case OBLIQUE_TOP_RIGHT: return "ObliqueTopRight";
    		case OBLIQUE_LEFT: return "ObliqueLeft";
    		case OBLIQUE_RIGHT: return "ObliqueRight";
    		case OBLIQUE_BOTTOM_LEFT: return "ObliqueBottomLeft";
    		case OBLIQUE_BOTTOM: return "ObliqueBottom";
    		case OBLIQUE_BOTTOM_RIGHT: return "ObliqueBottomRight";
    		case PERSPECTIVE_FRONT: return "PerspectiveFront";
    		case PERSPECTIVE_LEFT: return "PerspectiveLeft";
    		case PERSPECTIVE_RIGHT: return "PerspectiveRight";
    		case PERSPECTIVE_ABOVE: return "PerspectiveAbove";
    		case PERSPECTIVE_BELOW: return "PerspectiveBelow";
    		case PERSPECTIVE_ABOVE_LEFT_FACING: return "PerspectiveAboveLeftFacing";
    		case PERSPECTIVE_ABOVE_RIGHT_FACING: return "PerspectiveAboveRightFacing";
    		case PERSPECTIVE_CONTRASTING_LEFT_FACING: return "PerspectiveContrastingLeftFacing";
    		case PERSPECTIVE_CONTRASTING_RIGHT_FACING: return "PerspectiveContrastingRightFacing";
    		case PERSPECTIVE_HEROIC_LEFT_FACING: return "PerspectiveHeroicLeftFacing";
    		case PERSPECTIVE_HEROIC_RIGHT_FACING: return "PerspectiveHeroicRightFacing";
    		case PERSPECTIVE_HEROIC_EXTREME_LEFT_FACING: return "PerspectiveHeroicExtremeLeftFacing";
    		case PERSPECTIVE_HEROIC_EXTREME_RIGHT_FACING: return "PerspectiveHeroicExtremeRightFacing";
    		case PERSPECTIVE_RELAXED: return "PerspectiveRelaxed";
    		case PERSPECTIVE_RELAXED_MODERATELY: return "PerspectiveRelaxedModerately";
    		default: return "Unknown DmlPresetCameraType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlPresetCameraType name.
    /// </summary>
    public static /*DmlPresetCameraType*/int fromName(String dmlPresetCameraTypeName)
    {
    	if ("LEGACY_OBLIQUE_TOP_LEFT".equals(dmlPresetCameraTypeName)) return LEGACY_OBLIQUE_TOP_LEFT;
    	if ("LEGACY_OBLIQUE_TOP".equals(dmlPresetCameraTypeName)) return LEGACY_OBLIQUE_TOP;
    	if ("LEGACY_OBLIQUE_TOP_RIGHT".equals(dmlPresetCameraTypeName)) return LEGACY_OBLIQUE_TOP_RIGHT;
    	if ("LEGACY_OBLIQUE_LEFT".equals(dmlPresetCameraTypeName)) return LEGACY_OBLIQUE_LEFT;
    	if ("LEGACY_OBLIQUE_FRONT".equals(dmlPresetCameraTypeName)) return LEGACY_OBLIQUE_FRONT;
    	if ("LEGACY_OBLIQUE_RIGHT".equals(dmlPresetCameraTypeName)) return LEGACY_OBLIQUE_RIGHT;
    	if ("LEGACY_OBLIQUE_BOTTOM_LEFT".equals(dmlPresetCameraTypeName)) return LEGACY_OBLIQUE_BOTTOM_LEFT;
    	if ("LEGACY_OBLIQUE_BOTTOM".equals(dmlPresetCameraTypeName)) return LEGACY_OBLIQUE_BOTTOM;
    	if ("LEGACY_OBLIQUE_BOTTOM_RIGHT".equals(dmlPresetCameraTypeName)) return LEGACY_OBLIQUE_BOTTOM_RIGHT;
    	if ("LEGACY_PERSPECTIVE_TOP_LEFT".equals(dmlPresetCameraTypeName)) return LEGACY_PERSPECTIVE_TOP_LEFT;
    	if ("LEGACY_PERSPECTIVE_TOP".equals(dmlPresetCameraTypeName)) return LEGACY_PERSPECTIVE_TOP;
    	if ("LEGACY_PERSPECTIVE_TOP_RIGHT".equals(dmlPresetCameraTypeName)) return LEGACY_PERSPECTIVE_TOP_RIGHT;
    	if ("LEGACY_PERSPECTIVE_LEFT".equals(dmlPresetCameraTypeName)) return LEGACY_PERSPECTIVE_LEFT;
    	if ("LEGACY_PERSPECTIVE_FRONT".equals(dmlPresetCameraTypeName)) return LEGACY_PERSPECTIVE_FRONT;
    	if ("LEGACY_PERSPECTIVE_RIGHT".equals(dmlPresetCameraTypeName)) return LEGACY_PERSPECTIVE_RIGHT;
    	if ("LEGACY_PERSPECTIVE_BOTTOM_LEFT".equals(dmlPresetCameraTypeName)) return LEGACY_PERSPECTIVE_BOTTOM_LEFT;
    	if ("LEGACY_PERSPECTIVE_BOTTOM".equals(dmlPresetCameraTypeName)) return LEGACY_PERSPECTIVE_BOTTOM;
    	if ("LEGACY_PERSPECTIVE_BOTTOM_RIGHT".equals(dmlPresetCameraTypeName)) return LEGACY_PERSPECTIVE_BOTTOM_RIGHT;
    	if ("ORTHOGRAPHIC_FRONT".equals(dmlPresetCameraTypeName)) return ORTHOGRAPHIC_FRONT;
    	if ("ISOMETRIC_TOP_UP".equals(dmlPresetCameraTypeName)) return ISOMETRIC_TOP_UP;
    	if ("ISOMETRIC_TOP_DOWN".equals(dmlPresetCameraTypeName)) return ISOMETRIC_TOP_DOWN;
    	if ("ISOMETRIC_BOTTOM_UP".equals(dmlPresetCameraTypeName)) return ISOMETRIC_BOTTOM_UP;
    	if ("ISOMETRIC_BOTTOM_DOWN".equals(dmlPresetCameraTypeName)) return ISOMETRIC_BOTTOM_DOWN;
    	if ("ISOMETRIC_LEFT_UP".equals(dmlPresetCameraTypeName)) return ISOMETRIC_LEFT_UP;
    	if ("ISOMETRIC_LEFT_DOWN".equals(dmlPresetCameraTypeName)) return ISOMETRIC_LEFT_DOWN;
    	if ("ISOMETRIC_RIGHT_UP".equals(dmlPresetCameraTypeName)) return ISOMETRIC_RIGHT_UP;
    	if ("ISOMETRIC_RIGHT_DOWN".equals(dmlPresetCameraTypeName)) return ISOMETRIC_RIGHT_DOWN;
    	if ("ISOMETRIC_OFF_AXIS_1_LEFT".equals(dmlPresetCameraTypeName)) return ISOMETRIC_OFF_AXIS_1_LEFT;
    	if ("ISOMETRIC_OFF_AXIS_1_RIGHT".equals(dmlPresetCameraTypeName)) return ISOMETRIC_OFF_AXIS_1_RIGHT;
    	if ("ISOMETRIC_OFF_AXIS_1_TOP".equals(dmlPresetCameraTypeName)) return ISOMETRIC_OFF_AXIS_1_TOP;
    	if ("ISOMETRIC_OFF_AXIS_2_LEFT".equals(dmlPresetCameraTypeName)) return ISOMETRIC_OFF_AXIS_2_LEFT;
    	if ("ISOMETRIC_OFF_AXIS_2_RIGHT".equals(dmlPresetCameraTypeName)) return ISOMETRIC_OFF_AXIS_2_RIGHT;
    	if ("ISOMETRIC_OFF_AXIS_2_TOP".equals(dmlPresetCameraTypeName)) return ISOMETRIC_OFF_AXIS_2_TOP;
    	if ("ISOMETRIC_OFF_AXIS_3_LEFT".equals(dmlPresetCameraTypeName)) return ISOMETRIC_OFF_AXIS_3_LEFT;
    	if ("ISOMETRIC_OFF_AXIS_3_RIGHT".equals(dmlPresetCameraTypeName)) return ISOMETRIC_OFF_AXIS_3_RIGHT;
    	if ("ISOMETRIC_OFF_AXIS_3_BOTTOM".equals(dmlPresetCameraTypeName)) return ISOMETRIC_OFF_AXIS_3_BOTTOM;
    	if ("ISOMETRIC_OFF_AXIS_4_LEFT".equals(dmlPresetCameraTypeName)) return ISOMETRIC_OFF_AXIS_4_LEFT;
    	if ("ISOMETRIC_OFF_AXIS_4_RIGHT".equals(dmlPresetCameraTypeName)) return ISOMETRIC_OFF_AXIS_4_RIGHT;
    	if ("ISOMETRIC_OFF_AXIS_4_BOTTOM".equals(dmlPresetCameraTypeName)) return ISOMETRIC_OFF_AXIS_4_BOTTOM;
    	if ("OBLIQUE_TOP_LEFT".equals(dmlPresetCameraTypeName)) return OBLIQUE_TOP_LEFT;
    	if ("OBLIQUE_TOP".equals(dmlPresetCameraTypeName)) return OBLIQUE_TOP;
    	if ("OBLIQUE_TOP_RIGHT".equals(dmlPresetCameraTypeName)) return OBLIQUE_TOP_RIGHT;
    	if ("OBLIQUE_LEFT".equals(dmlPresetCameraTypeName)) return OBLIQUE_LEFT;
    	if ("OBLIQUE_RIGHT".equals(dmlPresetCameraTypeName)) return OBLIQUE_RIGHT;
    	if ("OBLIQUE_BOTTOM_LEFT".equals(dmlPresetCameraTypeName)) return OBLIQUE_BOTTOM_LEFT;
    	if ("OBLIQUE_BOTTOM".equals(dmlPresetCameraTypeName)) return OBLIQUE_BOTTOM;
    	if ("OBLIQUE_BOTTOM_RIGHT".equals(dmlPresetCameraTypeName)) return OBLIQUE_BOTTOM_RIGHT;
    	if ("PERSPECTIVE_FRONT".equals(dmlPresetCameraTypeName)) return PERSPECTIVE_FRONT;
    	if ("PERSPECTIVE_LEFT".equals(dmlPresetCameraTypeName)) return PERSPECTIVE_LEFT;
    	if ("PERSPECTIVE_RIGHT".equals(dmlPresetCameraTypeName)) return PERSPECTIVE_RIGHT;
    	if ("PERSPECTIVE_ABOVE".equals(dmlPresetCameraTypeName)) return PERSPECTIVE_ABOVE;
    	if ("PERSPECTIVE_BELOW".equals(dmlPresetCameraTypeName)) return PERSPECTIVE_BELOW;
    	if ("PERSPECTIVE_ABOVE_LEFT_FACING".equals(dmlPresetCameraTypeName)) return PERSPECTIVE_ABOVE_LEFT_FACING;
    	if ("PERSPECTIVE_ABOVE_RIGHT_FACING".equals(dmlPresetCameraTypeName)) return PERSPECTIVE_ABOVE_RIGHT_FACING;
    	if ("PERSPECTIVE_CONTRASTING_LEFT_FACING".equals(dmlPresetCameraTypeName)) return PERSPECTIVE_CONTRASTING_LEFT_FACING;
    	if ("PERSPECTIVE_CONTRASTING_RIGHT_FACING".equals(dmlPresetCameraTypeName)) return PERSPECTIVE_CONTRASTING_RIGHT_FACING;
    	if ("PERSPECTIVE_HEROIC_LEFT_FACING".equals(dmlPresetCameraTypeName)) return PERSPECTIVE_HEROIC_LEFT_FACING;
    	if ("PERSPECTIVE_HEROIC_RIGHT_FACING".equals(dmlPresetCameraTypeName)) return PERSPECTIVE_HEROIC_RIGHT_FACING;
    	if ("PERSPECTIVE_HEROIC_EXTREME_LEFT_FACING".equals(dmlPresetCameraTypeName)) return PERSPECTIVE_HEROIC_EXTREME_LEFT_FACING;
    	if ("PERSPECTIVE_HEROIC_EXTREME_RIGHT_FACING".equals(dmlPresetCameraTypeName)) return PERSPECTIVE_HEROIC_EXTREME_RIGHT_FACING;
    	if ("PERSPECTIVE_RELAXED".equals(dmlPresetCameraTypeName)) return PERSPECTIVE_RELAXED;
    	if ("PERSPECTIVE_RELAXED_MODERATELY".equals(dmlPresetCameraTypeName)) return PERSPECTIVE_RELAXED_MODERATELY;
    	throw new IllegalArgumentException("Unknown DmlPresetCameraType name.");
    }
    
    /// <summary>
    /// Returns array of DmlPresetCameraType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEGACY_OBLIQUE_TOP_LEFT,
    		LEGACY_OBLIQUE_TOP,
    		LEGACY_OBLIQUE_TOP_RIGHT,
    		LEGACY_OBLIQUE_LEFT,
    		LEGACY_OBLIQUE_FRONT,
    		LEGACY_OBLIQUE_RIGHT,
    		LEGACY_OBLIQUE_BOTTOM_LEFT,
    		LEGACY_OBLIQUE_BOTTOM,
    		LEGACY_OBLIQUE_BOTTOM_RIGHT,
    		LEGACY_PERSPECTIVE_TOP_LEFT,
    		LEGACY_PERSPECTIVE_TOP,
    		LEGACY_PERSPECTIVE_TOP_RIGHT,
    		LEGACY_PERSPECTIVE_LEFT,
    		LEGACY_PERSPECTIVE_FRONT,
    		LEGACY_PERSPECTIVE_RIGHT,
    		LEGACY_PERSPECTIVE_BOTTOM_LEFT,
    		LEGACY_PERSPECTIVE_BOTTOM,
    		LEGACY_PERSPECTIVE_BOTTOM_RIGHT,
    		ORTHOGRAPHIC_FRONT,
    		ISOMETRIC_TOP_UP,
    		ISOMETRIC_TOP_DOWN,
    		ISOMETRIC_BOTTOM_UP,
    		ISOMETRIC_BOTTOM_DOWN,
    		ISOMETRIC_LEFT_UP,
    		ISOMETRIC_LEFT_DOWN,
    		ISOMETRIC_RIGHT_UP,
    		ISOMETRIC_RIGHT_DOWN,
    		ISOMETRIC_OFF_AXIS_1_LEFT,
    		ISOMETRIC_OFF_AXIS_1_RIGHT,
    		ISOMETRIC_OFF_AXIS_1_TOP,
    		ISOMETRIC_OFF_AXIS_2_LEFT,
    		ISOMETRIC_OFF_AXIS_2_RIGHT,
    		ISOMETRIC_OFF_AXIS_2_TOP,
    		ISOMETRIC_OFF_AXIS_3_LEFT,
    		ISOMETRIC_OFF_AXIS_3_RIGHT,
    		ISOMETRIC_OFF_AXIS_3_BOTTOM,
    		ISOMETRIC_OFF_AXIS_4_LEFT,
    		ISOMETRIC_OFF_AXIS_4_RIGHT,
    		ISOMETRIC_OFF_AXIS_4_BOTTOM,
    		OBLIQUE_TOP_LEFT,
    		OBLIQUE_TOP,
    		OBLIQUE_TOP_RIGHT,
    		OBLIQUE_LEFT,
    		OBLIQUE_RIGHT,
    		OBLIQUE_BOTTOM_LEFT,
    		OBLIQUE_BOTTOM,
    		OBLIQUE_BOTTOM_RIGHT,
    		PERSPECTIVE_FRONT,
    		PERSPECTIVE_LEFT,
    		PERSPECTIVE_RIGHT,
    		PERSPECTIVE_ABOVE,
    		PERSPECTIVE_BELOW,
    		PERSPECTIVE_ABOVE_LEFT_FACING,
    		PERSPECTIVE_ABOVE_RIGHT_FACING,
    		PERSPECTIVE_CONTRASTING_LEFT_FACING,
    		PERSPECTIVE_CONTRASTING_RIGHT_FACING,
    		PERSPECTIVE_HEROIC_LEFT_FACING,
    		PERSPECTIVE_HEROIC_RIGHT_FACING,
    		PERSPECTIVE_HEROIC_EXTREME_LEFT_FACING,
    		PERSPECTIVE_HEROIC_EXTREME_RIGHT_FACING,
    		PERSPECTIVE_RELAXED,
    		PERSPECTIVE_RELAXED_MODERATELY,
    	};
    }

    public static final int length = 62;
}

