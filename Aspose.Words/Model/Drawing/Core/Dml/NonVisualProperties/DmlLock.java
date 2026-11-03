// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class DmlLock
{
    private DmlLock(){}
    
    /// <summary>
    /// Disallow Shape Grouping
    /// </summary>
    public static final int NO_GROUP = 0;

    /// <summary>
    /// Disallow Shape Selection
    /// </summary>
    public static final int NO_SELECT = 1;

    /// <summary>
    /// Disallow Shape Rotation
    /// </summary>
    public static final int NO_ROTATION = 2;

    /// <summary>
    /// Disallow Aspect Ratio Change
    /// </summary>
    public static final int NO_CHANGE_ASPECT = 3;

    /// <summary>
    /// Disallow Shape Movement
    /// </summary>
    public static final int NO_MOVE = 4;

    /// <summary>
    /// Disallow Shape Resize
    /// </summary>
    public static final int NO_RESIZE = 5;

    /// <summary>
    /// Disallow Shape Point Editing
    /// </summary>
    public static final int NO_EDIT_POINTS = 6;

    /// <summary>
    /// Disallow Showing Adjust Handles
    /// </summary>
    public static final int NO_ADJUST_HANDLES = 7;

    /// <summary>
    /// Disallow Arrowhead Changes
    /// </summary>
    public static final int NO_CHANGE_ARROWHEADS = 8;

    /// <summary>
    /// Disallow Shape Type Change
    /// </summary>
    public static final int NO_CHANGE_SHAPE_TYPE = 9;

    /// <summary>
    /// Disallow Graphic Frame Drilldown
    /// </summary>
    public static final int NO_DRILLDOWN = 10;

    /// <summary>
    /// Disallow Goup Shape Ungroup
    /// </summary>
    public static final int NO_UNGROUP = 11;

    /// <summary>
    /// Disallow Picture Cropping
    /// </summary>
    public static final int NO_CROP = 12;

    /// <summary>
    /// Disallow Text Editing
    /// </summary>
    public static final int NO_TEXT_EDIT = 13;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlLock value.
    /// </summary>
    public static String getName(/*DmlLock*/int dmlLock)
    {
    	switch (dmlLock)
    	{
    		case NO_GROUP: return "NO_GROUP";
    		case NO_SELECT: return "NO_SELECT";
    		case NO_ROTATION: return "NO_ROTATION";
    		case NO_CHANGE_ASPECT: return "NO_CHANGE_ASPECT";
    		case NO_MOVE: return "NO_MOVE";
    		case NO_RESIZE: return "NO_RESIZE";
    		case NO_EDIT_POINTS: return "NO_EDIT_POINTS";
    		case NO_ADJUST_HANDLES: return "NO_ADJUST_HANDLES";
    		case NO_CHANGE_ARROWHEADS: return "NO_CHANGE_ARROWHEADS";
    		case NO_CHANGE_SHAPE_TYPE: return "NO_CHANGE_SHAPE_TYPE";
    		case NO_DRILLDOWN: return "NO_DRILLDOWN";
    		case NO_UNGROUP: return "NO_UNGROUP";
    		case NO_CROP: return "NO_CROP";
    		case NO_TEXT_EDIT: return "NO_TEXT_EDIT";
    		default: return "Unknown DmlLock value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlLock value.
    /// </summary>
    public static String toString(/*DmlLock*/int dmlLock)
    {
    	switch (dmlLock)
    	{
    		case NO_GROUP: return "NoGroup";
    		case NO_SELECT: return "NoSelect";
    		case NO_ROTATION: return "NoRotation";
    		case NO_CHANGE_ASPECT: return "NoChangeAspect";
    		case NO_MOVE: return "NoMove";
    		case NO_RESIZE: return "NoResize";
    		case NO_EDIT_POINTS: return "NoEditPoints";
    		case NO_ADJUST_HANDLES: return "NoAdjustHandles";
    		case NO_CHANGE_ARROWHEADS: return "NoChangeArrowheads";
    		case NO_CHANGE_SHAPE_TYPE: return "NoChangeShapeType";
    		case NO_DRILLDOWN: return "NoDrilldown";
    		case NO_UNGROUP: return "NoUngroup";
    		case NO_CROP: return "NoCrop";
    		case NO_TEXT_EDIT: return "NoTextEdit";
    		default: return "Unknown DmlLock value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlLock name.
    /// </summary>
    public static /*DmlLock*/int fromName(String dmlLockName)
    {
    	if ("NO_GROUP".equals(dmlLockName)) return NO_GROUP;
    	if ("NO_SELECT".equals(dmlLockName)) return NO_SELECT;
    	if ("NO_ROTATION".equals(dmlLockName)) return NO_ROTATION;
    	if ("NO_CHANGE_ASPECT".equals(dmlLockName)) return NO_CHANGE_ASPECT;
    	if ("NO_MOVE".equals(dmlLockName)) return NO_MOVE;
    	if ("NO_RESIZE".equals(dmlLockName)) return NO_RESIZE;
    	if ("NO_EDIT_POINTS".equals(dmlLockName)) return NO_EDIT_POINTS;
    	if ("NO_ADJUST_HANDLES".equals(dmlLockName)) return NO_ADJUST_HANDLES;
    	if ("NO_CHANGE_ARROWHEADS".equals(dmlLockName)) return NO_CHANGE_ARROWHEADS;
    	if ("NO_CHANGE_SHAPE_TYPE".equals(dmlLockName)) return NO_CHANGE_SHAPE_TYPE;
    	if ("NO_DRILLDOWN".equals(dmlLockName)) return NO_DRILLDOWN;
    	if ("NO_UNGROUP".equals(dmlLockName)) return NO_UNGROUP;
    	if ("NO_CROP".equals(dmlLockName)) return NO_CROP;
    	if ("NO_TEXT_EDIT".equals(dmlLockName)) return NO_TEXT_EDIT;
    	throw new IllegalArgumentException("Unknown DmlLock name.");
    }
    
    /// <summary>
    /// Returns array of DmlLock values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NO_GROUP,
    		NO_SELECT,
    		NO_ROTATION,
    		NO_CHANGE_ASPECT,
    		NO_MOVE,
    		NO_RESIZE,
    		NO_EDIT_POINTS,
    		NO_ADJUST_HANDLES,
    		NO_CHANGE_ARROWHEADS,
    		NO_CHANGE_SHAPE_TYPE,
    		NO_DRILLDOWN,
    		NO_UNGROUP,
    		NO_CROP,
    		NO_TEXT_EDIT,
    	};
    }

    public static final int length = 14;
}

