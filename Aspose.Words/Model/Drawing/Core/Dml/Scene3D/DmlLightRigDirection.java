// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 20.1.10.29 ST_LightRigDirection (Light Rig Direction)
/// Represents the direction from which the light rig is positioned relative to the scene. The light rig, itself, can be
/// made up of multiple lights in any orientation around a given shape. This simple type defines the orientation of
/// the light rig as a whole, and not the individual lights within the rig.
/// </summary>
/*enum*/ final class DmlLightRigDirection
{
    private DmlLightRigDirection(){}
    
    public static final int TOP_LEFT = 0;
    public static final int TOP = 1;
    public static final int TOP_RIGHT = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;
    public static final int BOTTOM_LEFT = 5;
    public static final int BOTTOM = 6;
    public static final int BOTTOM_RIGHT = 7;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlLightRigDirection value.
    /// </summary>
    public static String getName(/*DmlLightRigDirection*/int dmlLightRigDirection)
    {
    	switch (dmlLightRigDirection)
    	{
    		case TOP_LEFT: return "TOP_LEFT";
    		case TOP: return "TOP";
    		case TOP_RIGHT: return "TOP_RIGHT";
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		case BOTTOM_LEFT: return "BOTTOM_LEFT";
    		case BOTTOM: return "BOTTOM";
    		case BOTTOM_RIGHT: return "BOTTOM_RIGHT";
    		default: return "Unknown DmlLightRigDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlLightRigDirection value.
    /// </summary>
    public static String toString(/*DmlLightRigDirection*/int dmlLightRigDirection)
    {
    	switch (dmlLightRigDirection)
    	{
    		case TOP_LEFT: return "TopLeft";
    		case TOP: return "Top";
    		case TOP_RIGHT: return "TopRight";
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		case BOTTOM_LEFT: return "BottomLeft";
    		case BOTTOM: return "Bottom";
    		case BOTTOM_RIGHT: return "BottomRight";
    		default: return "Unknown DmlLightRigDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlLightRigDirection name.
    /// </summary>
    public static /*DmlLightRigDirection*/int fromName(String dmlLightRigDirectionName)
    {
    	if ("TOP_LEFT".equals(dmlLightRigDirectionName)) return TOP_LEFT;
    	if ("TOP".equals(dmlLightRigDirectionName)) return TOP;
    	if ("TOP_RIGHT".equals(dmlLightRigDirectionName)) return TOP_RIGHT;
    	if ("LEFT".equals(dmlLightRigDirectionName)) return LEFT;
    	if ("RIGHT".equals(dmlLightRigDirectionName)) return RIGHT;
    	if ("BOTTOM_LEFT".equals(dmlLightRigDirectionName)) return BOTTOM_LEFT;
    	if ("BOTTOM".equals(dmlLightRigDirectionName)) return BOTTOM;
    	if ("BOTTOM_RIGHT".equals(dmlLightRigDirectionName)) return BOTTOM_RIGHT;
    	throw new IllegalArgumentException("Unknown DmlLightRigDirection name.");
    }
    
    /// <summary>
    /// Returns array of DmlLightRigDirection values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TOP_LEFT,
    		TOP,
    		TOP_RIGHT,
    		LEFT,
    		RIGHT,
    		BOTTOM_LEFT,
    		BOTTOM,
    		BOTTOM_RIGHT,
    	};
    }

    public static final int length = 8;
}

