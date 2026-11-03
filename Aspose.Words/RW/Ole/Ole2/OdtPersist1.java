// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Implements 2.9.166 ODTPersist1. See http://msdn.microsoft.com/en-us/library/gg132458(v=office.12).aspx
/// </summary>
/*enum*/ final class OdtPersist1
{
    private OdtPersist1(){}
    
    public static final int NONE = 0x0000;
    public static final int RESERVED_1 = 0x0001;
    /// <summary>
    ///  If set application MUST assume that this OLE object’s class identifier (CLSID) is {00020907-0000-0000-C000-000000000046}.
    /// </summary>
    public static final int DEF_HANDLER = 0x0002;
    public static final int RESERVED_2 = 0x0004;
    public static final int RESERVED_3 = 0x0008;
    /// <summary>
    /// Specifies whether this OLE object is a link.
    /// </summary>
    public static final int LINK = 0x0010;
    public static final int RESERVED_4 = 0x0020;
    /// <summary>
    /// Specifies whether this OLE object is being represented by an icon.
    /// </summary>
    public static final int ICON = 0x0040;
    /// <summary>
    /// Specifies whether this OLE object is only compatible with OLE 1.
    /// </summary>
    public static final int OLE_1 = 0x0080;
    /// <summary>
    /// Specifies whether the user has requested that this OLE object only be updated in response to a user action.
    ///  If Link is zero, then Manual is undefined and MUST be ignored.
    /// </summary>
    public static final int MANUAL = 0x0100;
    /// <summary>
    /// Specifies whether this OLE object has requested to be notified when it is resized by its container.
    /// </summary>
    public static final int RECOMPOSE_ON_RESIZE = 0x0200;
    public static final int RESERVED_5 = 0x0400;
    public static final int RESERVED_6 = 0x0800;
    /// <summary>
    /// Specifies whether this object is an OLE control.
    /// </summary>
    public static final int OCX = 0x1000;
    /// <summary>
    /// If OCX is zero, then this bit MUST be zero. 
    /// If OCX is set, then Stream specifies whether this OLE control stores its data in a single stream instead of a storage. 
    /// If Stream is set, then the data for the OLE control is in a stream called "\003OCXDATA".
    /// </summary>
    public static final int STREAM = 0x2000;
    public static final int RESERVED_7 = 0x4000;
    /// <summary>
    /// Specifies whether this OLE object supports the IViewObject interface.
    /// </summary>
    public static final int SUPPORT_I_VIEW_OBJECT = 0x8000;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer OdtPersist1 value.
    /// </summary>
    public static String getName(/*OdtPersist1*/int odtPersist1)
    {
    	switch (odtPersist1)
    	{
    		case NONE: return "NONE";
    		case RESERVED_1: return "RESERVED_1";
    		case DEF_HANDLER: return "DEF_HANDLER";
    		case RESERVED_2: return "RESERVED_2";
    		case RESERVED_3: return "RESERVED_3";
    		case LINK: return "LINK";
    		case RESERVED_4: return "RESERVED_4";
    		case ICON: return "ICON";
    		case OLE_1: return "OLE_1";
    		case MANUAL: return "MANUAL";
    		case RECOMPOSE_ON_RESIZE: return "RECOMPOSE_ON_RESIZE";
    		case RESERVED_5: return "RESERVED_5";
    		case RESERVED_6: return "RESERVED_6";
    		case OCX: return "OCX";
    		case STREAM: return "STREAM";
    		case RESERVED_7: return "RESERVED_7";
    		case SUPPORT_I_VIEW_OBJECT: return "SUPPORT_I_VIEW_OBJECT";
    		default: return "Unknown OdtPersist1 value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer OdtPersist1 value.
    /// </summary>
    public static Set<String> getNames(/*OdtPersist1*/int odtPersist1)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & odtPersist1) == NONE) names.add("NONE");
    	if ((RESERVED_1 & odtPersist1) == RESERVED_1) names.add("RESERVED_1");
    	if ((DEF_HANDLER & odtPersist1) == DEF_HANDLER) names.add("DEF_HANDLER");
    	if ((RESERVED_2 & odtPersist1) == RESERVED_2) names.add("RESERVED_2");
    	if ((RESERVED_3 & odtPersist1) == RESERVED_3) names.add("RESERVED_3");
    	if ((LINK & odtPersist1) == LINK) names.add("LINK");
    	if ((RESERVED_4 & odtPersist1) == RESERVED_4) names.add("RESERVED_4");
    	if ((ICON & odtPersist1) == ICON) names.add("ICON");
    	if ((OLE_1 & odtPersist1) == OLE_1) names.add("OLE_1");
    	if ((MANUAL & odtPersist1) == MANUAL) names.add("MANUAL");
    	if ((RECOMPOSE_ON_RESIZE & odtPersist1) == RECOMPOSE_ON_RESIZE) names.add("RECOMPOSE_ON_RESIZE");
    	if ((RESERVED_5 & odtPersist1) == RESERVED_5) names.add("RESERVED_5");
    	if ((RESERVED_6 & odtPersist1) == RESERVED_6) names.add("RESERVED_6");
    	if ((OCX & odtPersist1) == OCX) names.add("OCX");
    	if ((STREAM & odtPersist1) == STREAM) names.add("STREAM");
    	if ((RESERVED_7 & odtPersist1) == RESERVED_7) names.add("RESERVED_7");
    	if ((SUPPORT_I_VIEW_OBJECT & odtPersist1) == SUPPORT_I_VIEW_OBJECT) names.add("SUPPORT_I_VIEW_OBJECT");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer OdtPersist1 value.
    /// </summary>
    public static String toString(/*OdtPersist1*/int odtPersist1)
    {
    	switch (odtPersist1)
    	{
    		case NONE: return "None";
    		case RESERVED_1: return "Reserved1";
    		case DEF_HANDLER: return "DefHandler";
    		case RESERVED_2: return "Reserved2";
    		case RESERVED_3: return "Reserved3";
    		case LINK: return "Link";
    		case RESERVED_4: return "Reserved4";
    		case ICON: return "Icon";
    		case OLE_1: return "Ole1";
    		case MANUAL: return "Manual";
    		case RECOMPOSE_ON_RESIZE: return "RecomposeOnResize";
    		case RESERVED_5: return "Reserved5";
    		case RESERVED_6: return "Reserved6";
    		case OCX: return "Ocx";
    		case STREAM: return "Stream";
    		case RESERVED_7: return "Reserved7";
    		case SUPPORT_I_VIEW_OBJECT: return "SupportIViewObject";
    		default: return toStringSet(odtPersist1);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer OdtPersist1 value.
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
    /// Returns integer representation by OdtPersist1 name.
    /// </summary>
    public static /*OdtPersist1*/int fromName(String odtPersist1Name)
    {
    	if ("NONE".equals(odtPersist1Name)) return NONE;
    	if ("RESERVED_1".equals(odtPersist1Name)) return RESERVED_1;
    	if ("DEF_HANDLER".equals(odtPersist1Name)) return DEF_HANDLER;
    	if ("RESERVED_2".equals(odtPersist1Name)) return RESERVED_2;
    	if ("RESERVED_3".equals(odtPersist1Name)) return RESERVED_3;
    	if ("LINK".equals(odtPersist1Name)) return LINK;
    	if ("RESERVED_4".equals(odtPersist1Name)) return RESERVED_4;
    	if ("ICON".equals(odtPersist1Name)) return ICON;
    	if ("OLE_1".equals(odtPersist1Name)) return OLE_1;
    	if ("MANUAL".equals(odtPersist1Name)) return MANUAL;
    	if ("RECOMPOSE_ON_RESIZE".equals(odtPersist1Name)) return RECOMPOSE_ON_RESIZE;
    	if ("RESERVED_5".equals(odtPersist1Name)) return RESERVED_5;
    	if ("RESERVED_6".equals(odtPersist1Name)) return RESERVED_6;
    	if ("OCX".equals(odtPersist1Name)) return OCX;
    	if ("STREAM".equals(odtPersist1Name)) return STREAM;
    	if ("RESERVED_7".equals(odtPersist1Name)) return RESERVED_7;
    	if ("SUPPORT_I_VIEW_OBJECT".equals(odtPersist1Name)) return SUPPORT_I_VIEW_OBJECT;
    	throw new IllegalArgumentException("Unknown OdtPersist1 name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by OdtPersist1 names.
    /// </summary>
    public static /*OdtPersist1*/int fromNames(Set<String> odtPersist1Names)
    {
    	/*OdtPersist1*/int result = 0;
    	for (String name : odtPersist1Names)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of OdtPersist1 values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		RESERVED_1,
    		DEF_HANDLER,
    		RESERVED_2,
    		RESERVED_3,
    		LINK,
    		RESERVED_4,
    		ICON,
    		OLE_1,
    		MANUAL,
    		RECOMPOSE_ON_RESIZE,
    		RESERVED_5,
    		RESERVED_6,
    		OCX,
    		STREAM,
    		RESERVED_7,
    		SUPPORT_I_VIEW_OBJECT,
    	};
    }

    public static final int length = 17;
}

