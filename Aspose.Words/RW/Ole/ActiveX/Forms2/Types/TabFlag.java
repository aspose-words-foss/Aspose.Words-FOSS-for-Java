// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/01/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies whether a tab is visible and whether it is enabled.
/// </summary>
/// <dev>
/// [MS-OFORMS], 2.2.9.7 TabStripTabFlag.
/// </dev>
/*enum*/ final class TabFlag
{
    private TabFlag(){}
    
    public static final int NONE = 0x00000000;
    public static final int VISIBLE = 0x00000001;
    public static final int ENABLED = 0x00000002;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TabFlag value.
    /// </summary>
    public static String getName(/*TabFlag*/int tabFlag)
    {
    	switch (tabFlag)
    	{
    		case NONE: return "NONE";
    		case VISIBLE: return "VISIBLE";
    		case ENABLED: return "ENABLED";
    		default: return "Unknown TabFlag value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer TabFlag value.
    /// </summary>
    public static Set<String> getNames(/*TabFlag*/int tabFlag)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & tabFlag) == NONE) names.add("NONE");
    	if ((VISIBLE & tabFlag) == VISIBLE) names.add("VISIBLE");
    	if ((ENABLED & tabFlag) == ENABLED) names.add("ENABLED");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TabFlag value.
    /// </summary>
    public static String toString(/*TabFlag*/int tabFlag)
    {
    	switch (tabFlag)
    	{
    		case NONE: return "None";
    		case VISIBLE: return "Visible";
    		case ENABLED: return "Enabled";
    		default: return toStringSet(tabFlag);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TabFlag value.
    /// </summary>
    public static String toStringSet(int attr)
    {
    	String result = "";
    	String separator = "";
    	for (int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by TabFlag name.
    /// </summary>
    public static /*TabFlag*/int fromName(String tabFlagName)
    {
    	if ("NONE".equals(tabFlagName)) return NONE;
    	if ("VISIBLE".equals(tabFlagName)) return VISIBLE;
    	if ("ENABLED".equals(tabFlagName)) return ENABLED;
    	throw new IllegalArgumentException("Unknown TabFlag name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by TabFlag names.
    /// </summary>
    public static /*TabFlag*/int fromNames(Set<String> tabFlagNames)
    {
    	/*TabFlag*/int result = 0;
    	for (String name : tabFlagNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of TabFlag values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		VISIBLE,
    		ENABLED,
    	};
    }

    public static final int length = 3;
}

