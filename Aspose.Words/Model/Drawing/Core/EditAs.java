// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies how a group shape should be edited in Microsoft Word.
/// </summary>
/*enum*/ final class EditAs
{
    private EditAs(){}
    
    /// <summary>
    /// The group shape is a normal group of shapes.
    /// Not in DOC files, just our own value.
    /// </summary>
    public static final int GROUP = -1;
    /// <summary>
    /// The group shape is a canvas.
    /// </summary>
    public static final int CANVAS = 0;
    /// <summary>
    /// The group shape is an organizational chart.
    /// </summary>
    public static final int ORGANIZATIONAL_CHART = 1;
    /// <summary>
    /// The group shape is a Radial diagram.
    /// </summary>
    public static final int RADIAL = 2;
    /// <summary>
    /// The group shape is a Cycle diagram.
    /// </summary>
    public static final int CYCLE = 3;
    /// <summary>
    /// The group shape is stacked pyramid diagram.
    /// </summary>
    public static final int PYRAMID = 4;
    /// <summary>
    /// The group shape is a Venn diagram.
    /// </summary>
    public static final int VENN = 5;
    /// <summary>
    /// The group shape is a target diagram.
    /// </summary>
    public static final int TARGET = 6;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer EditAs value.
    /// </summary>
    public static String getName(/*EditAs*/int editAs)
    {
    	switch (editAs)
    	{
    		case GROUP: return "GROUP";
    		case CANVAS: return "CANVAS";
    		case ORGANIZATIONAL_CHART: return "ORGANIZATIONAL_CHART";
    		case RADIAL: return "RADIAL";
    		case CYCLE: return "CYCLE";
    		case PYRAMID: return "PYRAMID";
    		case VENN: return "VENN";
    		case TARGET: return "TARGET";
    		default: return "Unknown EditAs value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer EditAs value.
    /// </summary>
    public static String toString(/*EditAs*/int editAs)
    {
    	switch (editAs)
    	{
    		case GROUP: return "Group";
    		case CANVAS: return "Canvas";
    		case ORGANIZATIONAL_CHART: return "OrganizationalChart";
    		case RADIAL: return "Radial";
    		case CYCLE: return "Cycle";
    		case PYRAMID: return "Pyramid";
    		case VENN: return "Venn";
    		case TARGET: return "Target";
    		default: return "Unknown EditAs value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by EditAs name.
    /// </summary>
    public static /*EditAs*/int fromName(String editAsName)
    {
    	if ("GROUP".equals(editAsName)) return GROUP;
    	if ("CANVAS".equals(editAsName)) return CANVAS;
    	if ("ORGANIZATIONAL_CHART".equals(editAsName)) return ORGANIZATIONAL_CHART;
    	if ("RADIAL".equals(editAsName)) return RADIAL;
    	if ("CYCLE".equals(editAsName)) return CYCLE;
    	if ("PYRAMID".equals(editAsName)) return PYRAMID;
    	if ("VENN".equals(editAsName)) return VENN;
    	if ("TARGET".equals(editAsName)) return TARGET;
    	throw new IllegalArgumentException("Unknown EditAs name.");
    }
    
    /// <summary>
    /// Returns array of EditAs values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		GROUP,
    		CANVAS,
    		ORGANIZATIONAL_CHART,
    		RADIAL,
    		CYCLE,
    		PYRAMID,
    		VENN,
    		TARGET,
    	};
    }

    public static final int length = 8;
}

