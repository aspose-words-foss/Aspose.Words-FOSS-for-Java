// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Width of the arrow at the end of a line.
/// </summary>
/// <seealso cref="Stroke.StartArrowWidth"/>
/// <seealso cref="Stroke.EndArrowWidth"/>
public /*enum*/ final class ArrowWidth
{
    private ArrowWidth(){}
    
    /// <summary>
    /// </summary>
    public static final int NARROW = 0;
    /// <summary>
    /// </summary>
    public static final int MEDIUM = 1;
    /// <summary>
    /// </summary>
    public static final int WIDE = 2;

    /// <summary>
    /// Same as <see cref="Narrow"/>.
    /// </summary>
    public static final int DEFAULT = NARROW;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ArrowWidth value.
    /// </summary>
    public static String getName(/*ArrowWidth*/int arrowWidth)
    {
    	switch (arrowWidth)
    	{
    		case NARROW:
    		/*case DEFAULT:*/
    			return "NARROW" + " | " + "DEFAULT";
    		case MEDIUM: return "MEDIUM";
    		case WIDE: return "WIDE";
    		default: return "Unknown ArrowWidth value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ArrowWidth value.
    /// </summary>
    public static String toString(/*ArrowWidth*/int arrowWidth)
    {
    	switch (arrowWidth)
    	{
    		case NARROW:
    		/*case DEFAULT:*/
    			return "Narrow" + " | " + "Default";
    		case MEDIUM: return "Medium";
    		case WIDE: return "Wide";
    		default: return "Unknown ArrowWidth value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ArrowWidth name.
    /// </summary>
    public static /*ArrowWidth*/int fromName(String arrowWidthName)
    {
    	if ("NARROW".equals(arrowWidthName)) return NARROW;
    	if ("MEDIUM".equals(arrowWidthName)) return MEDIUM;
    	if ("WIDE".equals(arrowWidthName)) return WIDE;
    	if ("DEFAULT".equals(arrowWidthName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown ArrowWidth name.");
    }
    
    /// <summary>
    /// Returns array of ArrowWidth values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NARROW,
    		MEDIUM,
    		WIDE,
    		DEFAULT,
    	};
    }

    public static final int length = 4;
}

