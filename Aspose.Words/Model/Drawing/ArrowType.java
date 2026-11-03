// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of an arrow at a line end.
/// </summary>
/// <seealso cref="Stroke.StartArrowType"/>
/// <seealso cref="Stroke.EndArrowType"/>
public /*enum*/ final class ArrowType
{
    private ArrowType(){}
    
    /// <summary>
    /// The line does not have an arrow at the end.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// The arrow is a solid triangle.
    /// </summary>
    public static final int ARROW = 1;
    /// <summary>
    /// The arrow is a "stealth" arrow.
    /// </summary>
    public static final int STEALTH = 2;
    /// <summary>
    /// The line end is a solid diamond.
    /// </summary>
    public static final int DIAMOND = 3;
    /// <summary>
    /// The line end is a solid oval.
    /// </summary>
    public static final int OVAL = 4;
    /// <summary>
    /// The arrow is an open arrow.
    /// </summary>
    public static final int OPEN = 5;

    /// <summary>
    /// Same as <see cref="None"/>.
    /// </summary>
    public static final int DEFAULT = NONE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ArrowType value.
    /// </summary>
    public static String getName(/*ArrowType*/int arrowType)
    {
    	switch (arrowType)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "NONE" + " | " + "DEFAULT";
    		case ARROW: return "ARROW";
    		case STEALTH: return "STEALTH";
    		case DIAMOND: return "DIAMOND";
    		case OVAL: return "OVAL";
    		case OPEN: return "OPEN";
    		default: return "Unknown ArrowType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ArrowType value.
    /// </summary>
    public static String toString(/*ArrowType*/int arrowType)
    {
    	switch (arrowType)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "None" + " | " + "Default";
    		case ARROW: return "Arrow";
    		case STEALTH: return "Stealth";
    		case DIAMOND: return "Diamond";
    		case OVAL: return "Oval";
    		case OPEN: return "Open";
    		default: return "Unknown ArrowType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ArrowType name.
    /// </summary>
    public static /*ArrowType*/int fromName(String arrowTypeName)
    {
    	if ("NONE".equals(arrowTypeName)) return NONE;
    	if ("ARROW".equals(arrowTypeName)) return ARROW;
    	if ("STEALTH".equals(arrowTypeName)) return STEALTH;
    	if ("DIAMOND".equals(arrowTypeName)) return DIAMOND;
    	if ("OVAL".equals(arrowTypeName)) return OVAL;
    	if ("OPEN".equals(arrowTypeName)) return OPEN;
    	if ("DEFAULT".equals(arrowTypeName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown ArrowType name.");
    }
    
    /// <summary>
    /// Returns array of ArrowType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		ARROW,
    		STEALTH,
    		DIAMOND,
    		OVAL,
    		OPEN,
    		DEFAULT,
    	};
    }

    public static final int length = 7;
}

