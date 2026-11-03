// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies boolean design-time properties of a UserForm.
/// </summary>
/// <dev>
/// [MS-OFORMS] 2.5.5.1 DX_MODE.
/// </dev>
/*enum*/ final class DesignExtenderFlag /*: uint*/
{
    private DesignExtenderFlag(){}
    
    public static final /*uint*/int NONE = 0x00000000;
    public static final /*uint*/int INHERIT_DESIGN = 0x00000001;
    public static final /*uint*/int DESIGN = 0x00000002;
    public static final /*uint*/int INHERIT_SHOW_TOOLBOX = 0x00000004;
    public static final /*uint*/int SHOW_TOOLBOX = 0x00000008;
    public static final /*uint*/int INHERIT_SHOW_GRID = 0x00000010;
    public static final /*uint*/int SHOW_GRID = 0x00000020;
    public static final /*uint*/int INHERIT_SNAP_TO_GRID = 0x00000040;
    public static final /*uint*/int SNAP_TO_GRID = 0x00000080;
    public static final /*uint*/int INHERIT_GRID_X = 0x00000100;
    public static final /*uint*/int INHERIT_GRID_Y = 0x00000200;
    public static final /*uint*/int INHERIT_CLICK_CONTROL = 0x00000400;
    public static final /*uint*/int INHERIT_DBL_CLICK_CONTROL = 0x00000800;
    public static final /*uint*/int INHERIT_SHOW_INVISIBLE = 0x00001000;
    public static final /*uint*/int SHOW_INVISIBLE = 0x00002000;
    public static final /*uint*/int INHERIT_SHOW_TOOLTIPS = 0x00004000;
    public static final /*uint*/int SHOW_TOOLTIPS = 0x00008000;
    public static final /*uint*/int INHERIT_LAYOUT_IMMEDIATE = 0x00010000;
    public static final /*uint*/int LAYOUT_IMMEDIATE = 0x00020000;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DesignExtenderFlag value.
    /// </summary>
    public static String getName(/*DesignExtenderFlag*//*uint*/int designExtenderFlag)
    {
    	if (NONE == designExtenderFlag) return "NONE";
    	if (INHERIT_DESIGN == designExtenderFlag) return "INHERIT_DESIGN";
    	if (DESIGN == designExtenderFlag) return "DESIGN";
    	if (INHERIT_SHOW_TOOLBOX == designExtenderFlag) return "INHERIT_SHOW_TOOLBOX";
    	if (SHOW_TOOLBOX == designExtenderFlag) return "SHOW_TOOLBOX";
    	if (INHERIT_SHOW_GRID == designExtenderFlag) return "INHERIT_SHOW_GRID";
    	if (SHOW_GRID == designExtenderFlag) return "SHOW_GRID";
    	if (INHERIT_SNAP_TO_GRID == designExtenderFlag) return "INHERIT_SNAP_TO_GRID";
    	if (SNAP_TO_GRID == designExtenderFlag) return "SNAP_TO_GRID";
    	if (INHERIT_GRID_X == designExtenderFlag) return "INHERIT_GRID_X";
    	if (INHERIT_GRID_Y == designExtenderFlag) return "INHERIT_GRID_Y";
    	if (INHERIT_CLICK_CONTROL == designExtenderFlag) return "INHERIT_CLICK_CONTROL";
    	if (INHERIT_DBL_CLICK_CONTROL == designExtenderFlag) return "INHERIT_DBL_CLICK_CONTROL";
    	if (INHERIT_SHOW_INVISIBLE == designExtenderFlag) return "INHERIT_SHOW_INVISIBLE";
    	if (SHOW_INVISIBLE == designExtenderFlag) return "SHOW_INVISIBLE";
    	if (INHERIT_SHOW_TOOLTIPS == designExtenderFlag) return "INHERIT_SHOW_TOOLTIPS";
    	if (SHOW_TOOLTIPS == designExtenderFlag) return "SHOW_TOOLTIPS";
    	if (INHERIT_LAYOUT_IMMEDIATE == designExtenderFlag) return "INHERIT_LAYOUT_IMMEDIATE";
    	if (LAYOUT_IMMEDIATE == designExtenderFlag) return "LAYOUT_IMMEDIATE";
    	return "Unknown DesignExtenderFlag value.";
    }
    
    /// <summary>
    /// Returns string representation of integer DesignExtenderFlag value.
    /// </summary>
    public static Set<String> getNames(/*DesignExtenderFlag*//*uint*/int designExtenderFlag)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & designExtenderFlag) == NONE) names.add("NONE");
    	if ((INHERIT_DESIGN & designExtenderFlag) == INHERIT_DESIGN) names.add("INHERIT_DESIGN");
    	if ((DESIGN & designExtenderFlag) == DESIGN) names.add("DESIGN");
    	if ((INHERIT_SHOW_TOOLBOX & designExtenderFlag) == INHERIT_SHOW_TOOLBOX) names.add("INHERIT_SHOW_TOOLBOX");
    	if ((SHOW_TOOLBOX & designExtenderFlag) == SHOW_TOOLBOX) names.add("SHOW_TOOLBOX");
    	if ((INHERIT_SHOW_GRID & designExtenderFlag) == INHERIT_SHOW_GRID) names.add("INHERIT_SHOW_GRID");
    	if ((SHOW_GRID & designExtenderFlag) == SHOW_GRID) names.add("SHOW_GRID");
    	if ((INHERIT_SNAP_TO_GRID & designExtenderFlag) == INHERIT_SNAP_TO_GRID) names.add("INHERIT_SNAP_TO_GRID");
    	if ((SNAP_TO_GRID & designExtenderFlag) == SNAP_TO_GRID) names.add("SNAP_TO_GRID");
    	if ((INHERIT_GRID_X & designExtenderFlag) == INHERIT_GRID_X) names.add("INHERIT_GRID_X");
    	if ((INHERIT_GRID_Y & designExtenderFlag) == INHERIT_GRID_Y) names.add("INHERIT_GRID_Y");
    	if ((INHERIT_CLICK_CONTROL & designExtenderFlag) == INHERIT_CLICK_CONTROL) names.add("INHERIT_CLICK_CONTROL");
    	if ((INHERIT_DBL_CLICK_CONTROL & designExtenderFlag) == INHERIT_DBL_CLICK_CONTROL) names.add("INHERIT_DBL_CLICK_CONTROL");
    	if ((INHERIT_SHOW_INVISIBLE & designExtenderFlag) == INHERIT_SHOW_INVISIBLE) names.add("INHERIT_SHOW_INVISIBLE");
    	if ((SHOW_INVISIBLE & designExtenderFlag) == SHOW_INVISIBLE) names.add("SHOW_INVISIBLE");
    	if ((INHERIT_SHOW_TOOLTIPS & designExtenderFlag) == INHERIT_SHOW_TOOLTIPS) names.add("INHERIT_SHOW_TOOLTIPS");
    	if ((SHOW_TOOLTIPS & designExtenderFlag) == SHOW_TOOLTIPS) names.add("SHOW_TOOLTIPS");
    	if ((INHERIT_LAYOUT_IMMEDIATE & designExtenderFlag) == INHERIT_LAYOUT_IMMEDIATE) names.add("INHERIT_LAYOUT_IMMEDIATE");
    	if ((LAYOUT_IMMEDIATE & designExtenderFlag) == LAYOUT_IMMEDIATE) names.add("LAYOUT_IMMEDIATE");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DesignExtenderFlag value.
    /// </summary>
    public static String toString(/*DesignExtenderFlag*//*uint*/int designExtenderFlag)
    {
    	if (NONE == designExtenderFlag) return "None";
    	if (INHERIT_DESIGN == designExtenderFlag) return "InheritDesign";
    	if (DESIGN == designExtenderFlag) return "Design";
    	if (INHERIT_SHOW_TOOLBOX == designExtenderFlag) return "InheritShowToolbox";
    	if (SHOW_TOOLBOX == designExtenderFlag) return "ShowToolbox";
    	if (INHERIT_SHOW_GRID == designExtenderFlag) return "InheritShowGrid";
    	if (SHOW_GRID == designExtenderFlag) return "ShowGrid";
    	if (INHERIT_SNAP_TO_GRID == designExtenderFlag) return "InheritSnapToGrid";
    	if (SNAP_TO_GRID == designExtenderFlag) return "SnapToGrid";
    	if (INHERIT_GRID_X == designExtenderFlag) return "InheritGridX";
    	if (INHERIT_GRID_Y == designExtenderFlag) return "InheritGridY";
    	if (INHERIT_CLICK_CONTROL == designExtenderFlag) return "InheritClickControl";
    	if (INHERIT_DBL_CLICK_CONTROL == designExtenderFlag) return "InheritDblClickControl";
    	if (INHERIT_SHOW_INVISIBLE == designExtenderFlag) return "InheritShowInvisible";
    	if (SHOW_INVISIBLE == designExtenderFlag) return "ShowInvisible";
    	if (INHERIT_SHOW_TOOLTIPS == designExtenderFlag) return "InheritShowTooltips";
    	if (SHOW_TOOLTIPS == designExtenderFlag) return "ShowTooltips";
    	if (INHERIT_LAYOUT_IMMEDIATE == designExtenderFlag) return "InheritLayoutImmediate";
    	if (LAYOUT_IMMEDIATE == designExtenderFlag) return "LayoutImmediate";
    	return toStringSet(designExtenderFlag);
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DesignExtenderFlag value.
    /// </summary>
    public static String toStringSet(/*uint*/int attr)
    {
    	String result = "";
    	String separator = "";
    	for (/*uint*/int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by DesignExtenderFlag name.
    /// </summary>
    public static /*DesignExtenderFlag*//*uint*/int fromName(String designExtenderFlagName)
    {
    	if ("NONE".equals(designExtenderFlagName)) return NONE;
    	if ("INHERIT_DESIGN".equals(designExtenderFlagName)) return INHERIT_DESIGN;
    	if ("DESIGN".equals(designExtenderFlagName)) return DESIGN;
    	if ("INHERIT_SHOW_TOOLBOX".equals(designExtenderFlagName)) return INHERIT_SHOW_TOOLBOX;
    	if ("SHOW_TOOLBOX".equals(designExtenderFlagName)) return SHOW_TOOLBOX;
    	if ("INHERIT_SHOW_GRID".equals(designExtenderFlagName)) return INHERIT_SHOW_GRID;
    	if ("SHOW_GRID".equals(designExtenderFlagName)) return SHOW_GRID;
    	if ("INHERIT_SNAP_TO_GRID".equals(designExtenderFlagName)) return INHERIT_SNAP_TO_GRID;
    	if ("SNAP_TO_GRID".equals(designExtenderFlagName)) return SNAP_TO_GRID;
    	if ("INHERIT_GRID_X".equals(designExtenderFlagName)) return INHERIT_GRID_X;
    	if ("INHERIT_GRID_Y".equals(designExtenderFlagName)) return INHERIT_GRID_Y;
    	if ("INHERIT_CLICK_CONTROL".equals(designExtenderFlagName)) return INHERIT_CLICK_CONTROL;
    	if ("INHERIT_DBL_CLICK_CONTROL".equals(designExtenderFlagName)) return INHERIT_DBL_CLICK_CONTROL;
    	if ("INHERIT_SHOW_INVISIBLE".equals(designExtenderFlagName)) return INHERIT_SHOW_INVISIBLE;
    	if ("SHOW_INVISIBLE".equals(designExtenderFlagName)) return SHOW_INVISIBLE;
    	if ("INHERIT_SHOW_TOOLTIPS".equals(designExtenderFlagName)) return INHERIT_SHOW_TOOLTIPS;
    	if ("SHOW_TOOLTIPS".equals(designExtenderFlagName)) return SHOW_TOOLTIPS;
    	if ("INHERIT_LAYOUT_IMMEDIATE".equals(designExtenderFlagName)) return INHERIT_LAYOUT_IMMEDIATE;
    	if ("LAYOUT_IMMEDIATE".equals(designExtenderFlagName)) return LAYOUT_IMMEDIATE;
    	throw new IllegalArgumentException("Unknown DesignExtenderFlag name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by DesignExtenderFlag names.
    /// </summary>
    public static /*DesignExtenderFlag*//*uint*/int fromNames(Set<String> designExtenderFlagNames)
    {
    	/*DesignExtenderFlag*//*uint*/int result = 0;
    	for (String name : designExtenderFlagNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of DesignExtenderFlag values.
    /// </summary>
    public static /*uint*/int[] getValues()
    {
    	return new /*uint*/int[]
    	{
    		NONE,
    		INHERIT_DESIGN,
    		DESIGN,
    		INHERIT_SHOW_TOOLBOX,
    		SHOW_TOOLBOX,
    		INHERIT_SHOW_GRID,
    		SHOW_GRID,
    		INHERIT_SNAP_TO_GRID,
    		SNAP_TO_GRID,
    		INHERIT_GRID_X,
    		INHERIT_GRID_Y,
    		INHERIT_CLICK_CONTROL,
    		INHERIT_DBL_CLICK_CONTROL,
    		INHERIT_SHOW_INVISIBLE,
    		SHOW_INVISIBLE,
    		INHERIT_SHOW_TOOLTIPS,
    		SHOW_TOOLTIPS,
    		INHERIT_LAYOUT_IMMEDIATE,
    		LAYOUT_IMMEDIATE,
    	};
    }

    public static final int length = 19;
}

