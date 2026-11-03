// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/06/2022 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies preset margins.
/// </summary>
public /*enum*/ final class Margins
{
    private Margins(){}
    
    /// <summary>
    /// Normal margins.
    /// </summary>
    public static final int NORMAL = 0;

    /// <summary>
    /// Narrow margins.
    /// </summary>
    public static final int NARROW = 1;

    /// <summary>
    /// Moderate margins.
    /// </summary>
    public static final int MODERATE = 2;

    /// <summary>
    /// Wide margins.
    /// </summary>
    public static final int WIDE = 3;

    /// <summary>
    /// Mirrored margins.
    /// </summary>
    /// <remarks>
    /// Setting margins to Mirrored will set the appropriate value for <see cref="Aspose.Words.PageSetup.MultiplePages"/> property.
    /// This will affect the whole document, not just the current section.
    /// </remarks>
    public static final int MIRRORED = 4;

    /// <summary>
    /// Custom margins.
    /// </summary>
    public static final int CUSTOM = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer Margins value.
    /// </summary>
    public static String getName(/*Margins*/int margins)
    {
    	switch (margins)
    	{
    		case NORMAL: return "NORMAL";
    		case NARROW: return "NARROW";
    		case MODERATE: return "MODERATE";
    		case WIDE: return "WIDE";
    		case MIRRORED: return "MIRRORED";
    		case CUSTOM: return "CUSTOM";
    		default: return "Unknown Margins value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer Margins value.
    /// </summary>
    public static String toString(/*Margins*/int margins)
    {
    	switch (margins)
    	{
    		case NORMAL: return "Normal";
    		case NARROW: return "Narrow";
    		case MODERATE: return "Moderate";
    		case WIDE: return "Wide";
    		case MIRRORED: return "Mirrored";
    		case CUSTOM: return "Custom";
    		default: return "Unknown Margins value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by Margins name.
    /// </summary>
    public static /*Margins*/int fromName(String marginsName)
    {
    	if ("NORMAL".equals(marginsName)) return NORMAL;
    	if ("NARROW".equals(marginsName)) return NARROW;
    	if ("MODERATE".equals(marginsName)) return MODERATE;
    	if ("WIDE".equals(marginsName)) return WIDE;
    	if ("MIRRORED".equals(marginsName)) return MIRRORED;
    	if ("CUSTOM".equals(marginsName)) return CUSTOM;
    	throw new IllegalArgumentException("Unknown Margins name.");
    }
    
    /// <summary>
    /// Returns array of Margins values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NORMAL,
    		NARROW,
    		MODERATE,
    		WIDE,
    		MIRRORED,
    		CUSTOM,
    	};
    }

    public static final int length = 6;
}

