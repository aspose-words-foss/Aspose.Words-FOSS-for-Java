// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/01/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies properties of an embedded control on a form.
/// </summary>
/// <dev>
/// [MS-OFORMS] 2.5.4.1 SITE_FLAG.
/// </dev>
/*enum*/ final class SiteFlag /*: uint*/
{
    private SiteFlag(){}
    
    public static final /*uint*/int NONE = 0x00000000;
    public static final /*uint*/int TAB_STOP = 0x00000001;
    public static final /*uint*/int VISIBLE = 0x00000002;
    public static final /*uint*/int DEFAULT = 0x00000004;
    public static final /*uint*/int CANCEL = 0x00000008;
    public static final /*uint*/int STREAMED = 0x00000010;
    public static final /*uint*/int AUTO_SIZE = 0x00000020;
    public static final /*uint*/int UNUSED_1 = 0x000000C0;
    public static final /*uint*/int PRESERVE_HEIGHT = 0x00000100;
    public static final /*uint*/int FIT_TO_PARENT = 0x00000200;
    public static final /*uint*/int RESERVED_1 = 0x00001C00;
    public static final /*uint*/int SELECT_CHILD = 0x00002000;
    public static final /*uint*/int UNUSED_2 = 0x0003C000;
    public static final /*uint*/int PROMOTE_CONTROLS = 0x00040000;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SiteFlag value.
    /// </summary>
    public static String getName(/*SiteFlag*//*uint*/int siteFlag)
    {
    	if (NONE == siteFlag) return "NONE";
    	if (TAB_STOP == siteFlag) return "TAB_STOP";
    	if (VISIBLE == siteFlag) return "VISIBLE";
    	if (DEFAULT == siteFlag) return "DEFAULT";
    	if (CANCEL == siteFlag) return "CANCEL";
    	if (STREAMED == siteFlag) return "STREAMED";
    	if (AUTO_SIZE == siteFlag) return "AUTO_SIZE";
    	if (UNUSED_1 == siteFlag) return "UNUSED_1";
    	if (PRESERVE_HEIGHT == siteFlag) return "PRESERVE_HEIGHT";
    	if (FIT_TO_PARENT == siteFlag) return "FIT_TO_PARENT";
    	if (RESERVED_1 == siteFlag) return "RESERVED_1";
    	if (SELECT_CHILD == siteFlag) return "SELECT_CHILD";
    	if (UNUSED_2 == siteFlag) return "UNUSED_2";
    	if (PROMOTE_CONTROLS == siteFlag) return "PROMOTE_CONTROLS";
    	return "Unknown SiteFlag value.";
    }
    
    /// <summary>
    /// Returns string representation of integer SiteFlag value.
    /// </summary>
    public static Set<String> getNames(/*SiteFlag*//*uint*/int siteFlag)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & siteFlag) == NONE) names.add("NONE");
    	if ((TAB_STOP & siteFlag) == TAB_STOP) names.add("TAB_STOP");
    	if ((VISIBLE & siteFlag) == VISIBLE) names.add("VISIBLE");
    	if ((DEFAULT & siteFlag) == DEFAULT) names.add("DEFAULT");
    	if ((CANCEL & siteFlag) == CANCEL) names.add("CANCEL");
    	if ((STREAMED & siteFlag) == STREAMED) names.add("STREAMED");
    	if ((AUTO_SIZE & siteFlag) == AUTO_SIZE) names.add("AUTO_SIZE");
    	if ((UNUSED_1 & siteFlag) == UNUSED_1) names.add("UNUSED_1");
    	if ((PRESERVE_HEIGHT & siteFlag) == PRESERVE_HEIGHT) names.add("PRESERVE_HEIGHT");
    	if ((FIT_TO_PARENT & siteFlag) == FIT_TO_PARENT) names.add("FIT_TO_PARENT");
    	if ((RESERVED_1 & siteFlag) == RESERVED_1) names.add("RESERVED_1");
    	if ((SELECT_CHILD & siteFlag) == SELECT_CHILD) names.add("SELECT_CHILD");
    	if ((UNUSED_2 & siteFlag) == UNUSED_2) names.add("UNUSED_2");
    	if ((PROMOTE_CONTROLS & siteFlag) == PROMOTE_CONTROLS) names.add("PROMOTE_CONTROLS");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SiteFlag value.
    /// </summary>
    public static String toString(/*SiteFlag*//*uint*/int siteFlag)
    {
    	if (NONE == siteFlag) return "None";
    	if (TAB_STOP == siteFlag) return "TabStop";
    	if (VISIBLE == siteFlag) return "Visible";
    	if (DEFAULT == siteFlag) return "Default";
    	if (CANCEL == siteFlag) return "Cancel";
    	if (STREAMED == siteFlag) return "Streamed";
    	if (AUTO_SIZE == siteFlag) return "AutoSize";
    	if (UNUSED_1 == siteFlag) return "Unused1";
    	if (PRESERVE_HEIGHT == siteFlag) return "PreserveHeight";
    	if (FIT_TO_PARENT == siteFlag) return "FitToParent";
    	if (RESERVED_1 == siteFlag) return "Reserved1";
    	if (SELECT_CHILD == siteFlag) return "SelectChild";
    	if (UNUSED_2 == siteFlag) return "Unused2";
    	if (PROMOTE_CONTROLS == siteFlag) return "PromoteControls";
    	return toStringSet(siteFlag);
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SiteFlag value.
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
    /// Returns integer representation by SiteFlag name.
    /// </summary>
    public static /*SiteFlag*//*uint*/int fromName(String siteFlagName)
    {
    	if ("NONE".equals(siteFlagName)) return NONE;
    	if ("TAB_STOP".equals(siteFlagName)) return TAB_STOP;
    	if ("VISIBLE".equals(siteFlagName)) return VISIBLE;
    	if ("DEFAULT".equals(siteFlagName)) return DEFAULT;
    	if ("CANCEL".equals(siteFlagName)) return CANCEL;
    	if ("STREAMED".equals(siteFlagName)) return STREAMED;
    	if ("AUTO_SIZE".equals(siteFlagName)) return AUTO_SIZE;
    	if ("UNUSED_1".equals(siteFlagName)) return UNUSED_1;
    	if ("PRESERVE_HEIGHT".equals(siteFlagName)) return PRESERVE_HEIGHT;
    	if ("FIT_TO_PARENT".equals(siteFlagName)) return FIT_TO_PARENT;
    	if ("RESERVED_1".equals(siteFlagName)) return RESERVED_1;
    	if ("SELECT_CHILD".equals(siteFlagName)) return SELECT_CHILD;
    	if ("UNUSED_2".equals(siteFlagName)) return UNUSED_2;
    	if ("PROMOTE_CONTROLS".equals(siteFlagName)) return PROMOTE_CONTROLS;
    	throw new IllegalArgumentException("Unknown SiteFlag name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by SiteFlag names.
    /// </summary>
    public static /*SiteFlag*//*uint*/int fromNames(Set<String> siteFlagNames)
    {
    	/*SiteFlag*//*uint*/int result = 0;
    	for (String name : siteFlagNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of SiteFlag values.
    /// </summary>
    public static /*uint*/int[] getValues()
    {
    	return new /*uint*/int[]
    	{
    		NONE,
    		TAB_STOP,
    		VISIBLE,
    		DEFAULT,
    		CANCEL,
    		STREAMED,
    		AUTO_SIZE,
    		UNUSED_1,
    		PRESERVE_HEIGHT,
    		FIT_TO_PARENT,
    		RESERVED_1,
    		SELECT_CHILD,
    		UNUSED_2,
    		PROMOTE_CONTROLS,
    	};
    }

    public static final int length = 14;
}

