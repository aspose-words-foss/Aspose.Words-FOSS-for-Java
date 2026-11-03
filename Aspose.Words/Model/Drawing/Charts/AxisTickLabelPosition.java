// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the possible positions for tick labels.
/// </summary>
public /*enum*/ final class AxisTickLabelPosition
{
    private AxisTickLabelPosition(){}
    
    // Corresponds ST_TickLblPos simple type (5.7.3.48).

    /// <summary>
    /// Specifies the axis labels shall be at the high end of the perpendicular axis.
    /// </summary>
    public static final int HIGH = 0;

    /// <summary>
    /// Specifies the axis labels shall be at the low end of the perpendicular axis.
    /// </summary>
    public static final int LOW = 1;

    /// <summary>
    /// Specifies the axis labels shall be next to the axis.
    /// </summary>
    public static final int NEXT_TO_AXIS = 2;

    /// <summary>
    /// Specifies the axis labels are not drawn.
    /// </summary>
    public static final int NONE = 3;

    /// <summary>
    /// Specifies default value of tick labels position.
    /// </summary>
    public static final int DEFAULT = NEXT_TO_AXIS;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AxisTickLabelPosition value.
    /// </summary>
    public static String getName(/*AxisTickLabelPosition*/int axisTickLabelPosition)
    {
    	switch (axisTickLabelPosition)
    	{
    		case HIGH: return "HIGH";
    		case LOW: return "LOW";
    		case NEXT_TO_AXIS:
    		/*case DEFAULT:*/
    			return "NEXT_TO_AXIS" + " | " + "DEFAULT";
    		case NONE: return "NONE";
    		default: return "Unknown AxisTickLabelPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AxisTickLabelPosition value.
    /// </summary>
    public static String toString(/*AxisTickLabelPosition*/int axisTickLabelPosition)
    {
    	switch (axisTickLabelPosition)
    	{
    		case HIGH: return "High";
    		case LOW: return "Low";
    		case NEXT_TO_AXIS:
    		/*case DEFAULT:*/
    			return "NextToAxis" + " | " + "Default";
    		case NONE: return "None";
    		default: return "Unknown AxisTickLabelPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AxisTickLabelPosition name.
    /// </summary>
    public static /*AxisTickLabelPosition*/int fromName(String axisTickLabelPositionName)
    {
    	if ("HIGH".equals(axisTickLabelPositionName)) return HIGH;
    	if ("LOW".equals(axisTickLabelPositionName)) return LOW;
    	if ("NEXT_TO_AXIS".equals(axisTickLabelPositionName)) return NEXT_TO_AXIS;
    	if ("NONE".equals(axisTickLabelPositionName)) return NONE;
    	if ("DEFAULT".equals(axisTickLabelPositionName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown AxisTickLabelPosition name.");
    }
    
    /// <summary>
    /// Returns array of AxisTickLabelPosition values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		HIGH,
    		LOW,
    		NEXT_TO_AXIS,
    		NONE,
    		DEFAULT,
    	};
    }

    public static final int length = 5;
}

