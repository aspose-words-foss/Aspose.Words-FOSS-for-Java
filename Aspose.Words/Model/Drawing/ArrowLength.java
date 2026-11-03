// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Length of the arrow at the end of a line.
/// </summary>
/// <seealso cref="Stroke.StartArrowLength"/>
/// <seealso cref="Stroke.EndArrowLength"/>
public /*enum*/ final class ArrowLength
{
    private ArrowLength(){}
    
    /// <summary>
    /// </summary>
    public static final int SHORT = 0;
    /// <summary>
    /// </summary>
    public static final int MEDIUM = 1;
    /// <summary>
    /// </summary>
    public static final int LONG = 2;

    /// <summary>
    /// Same as <see cref="Short"/>.
    /// </summary>
    public static final int DEFAULT = SHORT;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ArrowLength value.
    /// </summary>
    public static String getName(/*ArrowLength*/int arrowLength)
    {
    	switch (arrowLength)
    	{
    		case SHORT:
    		/*case DEFAULT:*/
    			return "SHORT" + " | " + "DEFAULT";
    		case MEDIUM: return "MEDIUM";
    		case LONG: return "LONG";
    		default: return "Unknown ArrowLength value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ArrowLength value.
    /// </summary>
    public static String toString(/*ArrowLength*/int arrowLength)
    {
    	switch (arrowLength)
    	{
    		case SHORT:
    		/*case DEFAULT:*/
    			return "Short" + " | " + "Default";
    		case MEDIUM: return "Medium";
    		case LONG: return "Long";
    		default: return "Unknown ArrowLength value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ArrowLength name.
    /// </summary>
    public static /*ArrowLength*/int fromName(String arrowLengthName)
    {
    	if ("SHORT".equals(arrowLengthName)) return SHORT;
    	if ("MEDIUM".equals(arrowLengthName)) return MEDIUM;
    	if ("LONG".equals(arrowLengthName)) return LONG;
    	if ("DEFAULT".equals(arrowLengthName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown ArrowLength name.");
    }
    
    /// <summary>
    /// Returns array of ArrowLength values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SHORT,
    		MEDIUM,
    		LONG,
    		DEFAULT,
    	};
    }

    public static final int length = 4;
}

