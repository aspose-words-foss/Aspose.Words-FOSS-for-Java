// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/01/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Attributes of <see cref="DmlLayoutVariablePropertySet"/>.
/// </summary>
/*enum*/ final class DmlLayoutVariableAttr
{
    private DmlLayoutVariableAttr(){}
    
    public static final int ORG_CHART = 0;
    public static final int CHILD_MAX = 1;
    public static final int CHILD_PREF = 2;
    public static final int BULLET_ENABLED = 3;
    public static final int DIRECTION = 4;
    public static final int HIER_BRANCH_STYLE = 5;
    public static final int ANIM_ONE = 6;
    public static final int ANIM_LEVEL = 7;
    public static final int RESIZE_HANDLES = 8;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlLayoutVariableAttr value.
    /// </summary>
    public static String getName(/*DmlLayoutVariableAttr*/int dmlLayoutVariableAttr)
    {
    	switch (dmlLayoutVariableAttr)
    	{
    		case ORG_CHART: return "ORG_CHART";
    		case CHILD_MAX: return "CHILD_MAX";
    		case CHILD_PREF: return "CHILD_PREF";
    		case BULLET_ENABLED: return "BULLET_ENABLED";
    		case DIRECTION: return "DIRECTION";
    		case HIER_BRANCH_STYLE: return "HIER_BRANCH_STYLE";
    		case ANIM_ONE: return "ANIM_ONE";
    		case ANIM_LEVEL: return "ANIM_LEVEL";
    		case RESIZE_HANDLES: return "RESIZE_HANDLES";
    		default: return "Unknown DmlLayoutVariableAttr value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlLayoutVariableAttr value.
    /// </summary>
    public static String toString(/*DmlLayoutVariableAttr*/int dmlLayoutVariableAttr)
    {
    	switch (dmlLayoutVariableAttr)
    	{
    		case ORG_CHART: return "OrgChart";
    		case CHILD_MAX: return "ChildMax";
    		case CHILD_PREF: return "ChildPref";
    		case BULLET_ENABLED: return "BulletEnabled";
    		case DIRECTION: return "Direction";
    		case HIER_BRANCH_STYLE: return "HierBranchStyle";
    		case ANIM_ONE: return "AnimOne";
    		case ANIM_LEVEL: return "AnimLevel";
    		case RESIZE_HANDLES: return "ResizeHandles";
    		default: return "Unknown DmlLayoutVariableAttr value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlLayoutVariableAttr name.
    /// </summary>
    public static /*DmlLayoutVariableAttr*/int fromName(String dmlLayoutVariableAttrName)
    {
    	if ("ORG_CHART".equals(dmlLayoutVariableAttrName)) return ORG_CHART;
    	if ("CHILD_MAX".equals(dmlLayoutVariableAttrName)) return CHILD_MAX;
    	if ("CHILD_PREF".equals(dmlLayoutVariableAttrName)) return CHILD_PREF;
    	if ("BULLET_ENABLED".equals(dmlLayoutVariableAttrName)) return BULLET_ENABLED;
    	if ("DIRECTION".equals(dmlLayoutVariableAttrName)) return DIRECTION;
    	if ("HIER_BRANCH_STYLE".equals(dmlLayoutVariableAttrName)) return HIER_BRANCH_STYLE;
    	if ("ANIM_ONE".equals(dmlLayoutVariableAttrName)) return ANIM_ONE;
    	if ("ANIM_LEVEL".equals(dmlLayoutVariableAttrName)) return ANIM_LEVEL;
    	if ("RESIZE_HANDLES".equals(dmlLayoutVariableAttrName)) return RESIZE_HANDLES;
    	throw new IllegalArgumentException("Unknown DmlLayoutVariableAttr name.");
    }
    
    /// <summary>
    /// Returns array of DmlLayoutVariableAttr values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ORG_CHART,
    		CHILD_MAX,
    		CHILD_PREF,
    		BULLET_ENABLED,
    		DIRECTION,
    		HIER_BRANCH_STYLE,
    		ANIM_ONE,
    		ANIM_LEVEL,
    		RESIZE_HANDLES,
    	};
    }

    public static final int length = 9;
}

