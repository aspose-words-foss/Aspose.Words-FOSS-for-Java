// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/08/2006 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the plane that is at right angles to the extrusion.
/// </summary>
/*enum*/ final class PlaneType
{
    private PlaneType(){}
    
    /// <summary>
    /// Extrusion is at right angles to the xy-plane. Default.
    /// </summary>
    public static final int XY = 0;      
    /// <summary>
    /// Extrusion is at right angles to the zx-plane.
    /// </summary>
    public static final int ZX = 1;       
    /// <summary>
    /// Extrusion is at right angles to the yz-plane.
    /// </summary>
    public static final int YZ = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PlaneType value.
    /// </summary>
    public static String getName(/*PlaneType*/int planeType)
    {
    	switch (planeType)
    	{
    		case XY: return "XY";
    		case ZX: return "ZX";
    		case YZ: return "YZ";
    		default: return "Unknown PlaneType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PlaneType value.
    /// </summary>
    public static String toString(/*PlaneType*/int planeType)
    {
    	switch (planeType)
    	{
    		case XY: return "XY";
    		case ZX: return "ZX";
    		case YZ: return "YZ";
    		default: return "Unknown PlaneType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PlaneType name.
    /// </summary>
    public static /*PlaneType*/int fromName(String planeTypeName)
    {
    	if ("XY".equals(planeTypeName)) return XY;
    	if ("ZX".equals(planeTypeName)) return ZX;
    	if ("YZ".equals(planeTypeName)) return YZ;
    	throw new IllegalArgumentException("Unknown PlaneType name.");
    }
    
    /// <summary>
    /// Returns array of PlaneType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		XY,
    		ZX,
    		YZ,
    	};
    }

    public static final int length = 3;
}

