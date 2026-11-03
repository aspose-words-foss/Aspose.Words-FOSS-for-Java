// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/12/2019 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.18 ST_ConnectorPoint (Connector Point)
/// </summary>
/*enum*/ final class DmlConnectorPoint
{
    private DmlConnectorPoint(){}
    
    public static final int AUTO = 0;
    public static final int BOTTOM_CENTER = 1;
    public static final int BOTTOM_LEFT = 2;
    public static final int BOTTOM_RIGHT = 3;
    public static final int CENTER = 4;
    public static final int MIDDLE_LEFT = 5;
    public static final int MIDDLE_RIGHT = 6;
    public static final int RADIAL = 7;
    public static final int TOP_CENTER = 8;
    public static final int TOP_LEFT = 9;
    public static final int TOP_RIGHT = 10;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlConnectorPoint value.
    /// </summary>
    public static String getName(/*DmlConnectorPoint*/int dmlConnectorPoint)
    {
    	switch (dmlConnectorPoint)
    	{
    		case AUTO: return "AUTO";
    		case BOTTOM_CENTER: return "BOTTOM_CENTER";
    		case BOTTOM_LEFT: return "BOTTOM_LEFT";
    		case BOTTOM_RIGHT: return "BOTTOM_RIGHT";
    		case CENTER: return "CENTER";
    		case MIDDLE_LEFT: return "MIDDLE_LEFT";
    		case MIDDLE_RIGHT: return "MIDDLE_RIGHT";
    		case RADIAL: return "RADIAL";
    		case TOP_CENTER: return "TOP_CENTER";
    		case TOP_LEFT: return "TOP_LEFT";
    		case TOP_RIGHT: return "TOP_RIGHT";
    		default: return "Unknown DmlConnectorPoint value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlConnectorPoint value.
    /// </summary>
    public static String toString(/*DmlConnectorPoint*/int dmlConnectorPoint)
    {
    	switch (dmlConnectorPoint)
    	{
    		case AUTO: return "Auto";
    		case BOTTOM_CENTER: return "BottomCenter";
    		case BOTTOM_LEFT: return "BottomLeft";
    		case BOTTOM_RIGHT: return "BottomRight";
    		case CENTER: return "Center";
    		case MIDDLE_LEFT: return "MiddleLeft";
    		case MIDDLE_RIGHT: return "MiddleRight";
    		case RADIAL: return "Radial";
    		case TOP_CENTER: return "TopCenter";
    		case TOP_LEFT: return "TopLeft";
    		case TOP_RIGHT: return "TopRight";
    		default: return "Unknown DmlConnectorPoint value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlConnectorPoint name.
    /// </summary>
    public static /*DmlConnectorPoint*/int fromName(String dmlConnectorPointName)
    {
    	if ("AUTO".equals(dmlConnectorPointName)) return AUTO;
    	if ("BOTTOM_CENTER".equals(dmlConnectorPointName)) return BOTTOM_CENTER;
    	if ("BOTTOM_LEFT".equals(dmlConnectorPointName)) return BOTTOM_LEFT;
    	if ("BOTTOM_RIGHT".equals(dmlConnectorPointName)) return BOTTOM_RIGHT;
    	if ("CENTER".equals(dmlConnectorPointName)) return CENTER;
    	if ("MIDDLE_LEFT".equals(dmlConnectorPointName)) return MIDDLE_LEFT;
    	if ("MIDDLE_RIGHT".equals(dmlConnectorPointName)) return MIDDLE_RIGHT;
    	if ("RADIAL".equals(dmlConnectorPointName)) return RADIAL;
    	if ("TOP_CENTER".equals(dmlConnectorPointName)) return TOP_CENTER;
    	if ("TOP_LEFT".equals(dmlConnectorPointName)) return TOP_LEFT;
    	if ("TOP_RIGHT".equals(dmlConnectorPointName)) return TOP_RIGHT;
    	throw new IllegalArgumentException("Unknown DmlConnectorPoint name.");
    }
    
    /// <summary>
    /// Returns array of DmlConnectorPoint values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AUTO,
    		BOTTOM_CENTER,
    		BOTTOM_LEFT,
    		BOTTOM_RIGHT,
    		CENTER,
    		MIDDLE_LEFT,
    		MIDDLE_RIGHT,
    		RADIAL,
    		TOP_CENTER,
    		TOP_LEFT,
    		TOP_RIGHT,
    	};
    }

    public static final int length = 11;
}

