// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of icon displayed as the mouse pointer for the control.
/// </summary>
/*enum*/ final class MousePointer
{
    private MousePointer(){}
    
    public static final int DEFAULT = 0x00;
    public static final int ARROW = 0x01;
    public static final int CROSS = 0x02;
    public static final int I_BEAM = 0x03;
    public static final int SIZE_NESW = 0x06;
    public static final int SIZE_NS = 0x07;
    public static final int SIZE_NWSE = 0x08;
    public static final int SIZE_WE = 0x09;
    public static final int UP_ARROW = 0x0a;
    public static final int HOUR_GLASS = 0x0b;
    public static final int NO_DROP = 0x0c;
    public static final int APP_STARTING = 0x0d;
    public static final int HELP = 0x0e;
    public static final int SIZE_ALL = 0x0f;
    public static final int CUSTOM = 0x63;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MousePointer value.
    /// </summary>
    public static String getName(/*MousePointer*/int mousePointer)
    {
    	switch (mousePointer)
    	{
    		case DEFAULT: return "DEFAULT";
    		case ARROW: return "ARROW";
    		case CROSS: return "CROSS";
    		case I_BEAM: return "I_BEAM";
    		case SIZE_NESW: return "SIZE_NESW";
    		case SIZE_NS: return "SIZE_NS";
    		case SIZE_NWSE: return "SIZE_NWSE";
    		case SIZE_WE: return "SIZE_WE";
    		case UP_ARROW: return "UP_ARROW";
    		case HOUR_GLASS: return "HOUR_GLASS";
    		case NO_DROP: return "NO_DROP";
    		case APP_STARTING: return "APP_STARTING";
    		case HELP: return "HELP";
    		case SIZE_ALL: return "SIZE_ALL";
    		case CUSTOM: return "CUSTOM";
    		default: return "Unknown MousePointer value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MousePointer value.
    /// </summary>
    public static String toString(/*MousePointer*/int mousePointer)
    {
    	switch (mousePointer)
    	{
    		case DEFAULT: return "Default";
    		case ARROW: return "Arrow";
    		case CROSS: return "Cross";
    		case I_BEAM: return "IBeam";
    		case SIZE_NESW: return "SizeNESW";
    		case SIZE_NS: return "SizeNS";
    		case SIZE_NWSE: return "SizeNWSE";
    		case SIZE_WE: return "SizeWE";
    		case UP_ARROW: return "UpArrow";
    		case HOUR_GLASS: return "HourGlass";
    		case NO_DROP: return "NoDrop";
    		case APP_STARTING: return "AppStarting";
    		case HELP: return "Help";
    		case SIZE_ALL: return "SizeAll";
    		case CUSTOM: return "Custom";
    		default: return "Unknown MousePointer value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MousePointer name.
    /// </summary>
    public static /*MousePointer*/int fromName(String mousePointerName)
    {
    	if ("DEFAULT".equals(mousePointerName)) return DEFAULT;
    	if ("ARROW".equals(mousePointerName)) return ARROW;
    	if ("CROSS".equals(mousePointerName)) return CROSS;
    	if ("I_BEAM".equals(mousePointerName)) return I_BEAM;
    	if ("SIZE_NESW".equals(mousePointerName)) return SIZE_NESW;
    	if ("SIZE_NS".equals(mousePointerName)) return SIZE_NS;
    	if ("SIZE_NWSE".equals(mousePointerName)) return SIZE_NWSE;
    	if ("SIZE_WE".equals(mousePointerName)) return SIZE_WE;
    	if ("UP_ARROW".equals(mousePointerName)) return UP_ARROW;
    	if ("HOUR_GLASS".equals(mousePointerName)) return HOUR_GLASS;
    	if ("NO_DROP".equals(mousePointerName)) return NO_DROP;
    	if ("APP_STARTING".equals(mousePointerName)) return APP_STARTING;
    	if ("HELP".equals(mousePointerName)) return HELP;
    	if ("SIZE_ALL".equals(mousePointerName)) return SIZE_ALL;
    	if ("CUSTOM".equals(mousePointerName)) return CUSTOM;
    	throw new IllegalArgumentException("Unknown MousePointer name.");
    }
    
    /// <summary>
    /// Returns array of MousePointer values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFAULT,
    		ARROW,
    		CROSS,
    		I_BEAM,
    		SIZE_NESW,
    		SIZE_NS,
    		SIZE_NWSE,
    		SIZE_WE,
    		UP_ARROW,
    		HOUR_GLASS,
    		NO_DROP,
    		APP_STARTING,
    		HELP,
    		SIZE_ALL,
    		CUSTOM,
    	};
    }

    public static final int length = 15;
}

