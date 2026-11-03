// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.64 ST_VariableType (Variable Type)
/// </summary>
/*enum*/ final class DmlVariableType
{
    private DmlVariableType(){}
    
    public static final int NONE = 0;
    public static final int ANIM_LEVEL = 1;
    public static final int ANIM_ONE = 2;
    public static final int BULLET_ENABLED = 3;
    public static final int CHILD_MAX = 4;
    public static final int CHILD_PREF = 5;
    public static final int DIRECTION = 6;
    public static final int HIER_BRANCH_STYLE = 7;
    public static final int ORG_CHART = 8;
    public static final int RESIZE_HANDLES = 9;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlVariableType value.
    /// </summary>
    public static String getName(/*DmlVariableType*/int dmlVariableType)
    {
    	switch (dmlVariableType)
    	{
    		case NONE: return "NONE";
    		case ANIM_LEVEL: return "ANIM_LEVEL";
    		case ANIM_ONE: return "ANIM_ONE";
    		case BULLET_ENABLED: return "BULLET_ENABLED";
    		case CHILD_MAX: return "CHILD_MAX";
    		case CHILD_PREF: return "CHILD_PREF";
    		case DIRECTION: return "DIRECTION";
    		case HIER_BRANCH_STYLE: return "HIER_BRANCH_STYLE";
    		case ORG_CHART: return "ORG_CHART";
    		case RESIZE_HANDLES: return "RESIZE_HANDLES";
    		default: return "Unknown DmlVariableType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlVariableType value.
    /// </summary>
    public static String toString(/*DmlVariableType*/int dmlVariableType)
    {
    	switch (dmlVariableType)
    	{
    		case NONE: return "None";
    		case ANIM_LEVEL: return "AnimLevel";
    		case ANIM_ONE: return "AnimOne";
    		case BULLET_ENABLED: return "BulletEnabled";
    		case CHILD_MAX: return "ChildMax";
    		case CHILD_PREF: return "ChildPref";
    		case DIRECTION: return "Direction";
    		case HIER_BRANCH_STYLE: return "HierBranchStyle";
    		case ORG_CHART: return "OrgChart";
    		case RESIZE_HANDLES: return "ResizeHandles";
    		default: return "Unknown DmlVariableType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlVariableType name.
    /// </summary>
    public static /*DmlVariableType*/int fromName(String dmlVariableTypeName)
    {
    	if ("NONE".equals(dmlVariableTypeName)) return NONE;
    	if ("ANIM_LEVEL".equals(dmlVariableTypeName)) return ANIM_LEVEL;
    	if ("ANIM_ONE".equals(dmlVariableTypeName)) return ANIM_ONE;
    	if ("BULLET_ENABLED".equals(dmlVariableTypeName)) return BULLET_ENABLED;
    	if ("CHILD_MAX".equals(dmlVariableTypeName)) return CHILD_MAX;
    	if ("CHILD_PREF".equals(dmlVariableTypeName)) return CHILD_PREF;
    	if ("DIRECTION".equals(dmlVariableTypeName)) return DIRECTION;
    	if ("HIER_BRANCH_STYLE".equals(dmlVariableTypeName)) return HIER_BRANCH_STYLE;
    	if ("ORG_CHART".equals(dmlVariableTypeName)) return ORG_CHART;
    	if ("RESIZE_HANDLES".equals(dmlVariableTypeName)) return RESIZE_HANDLES;
    	throw new IllegalArgumentException("Unknown DmlVariableType name.");
    }
    
    /// <summary>
    /// Returns array of DmlVariableType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		ANIM_LEVEL,
    		ANIM_ONE,
    		BULLET_ENABLED,
    		CHILD_MAX,
    		CHILD_PREF,
    		DIRECTION,
    		HIER_BRANCH_STYLE,
    		ORG_CHART,
    		RESIZE_HANDLES,
    	};
    }

    public static final int length = 10;
}

