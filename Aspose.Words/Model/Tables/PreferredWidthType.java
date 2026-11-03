// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/09/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies the unit of measurement for the preferred width of a table or cell.
/// </summary>
/// <seealso cref="PreferredWidth"/>
public /*enum*/ final class PreferredWidthType
{
    private PreferredWidthType(){}
    
    /// <summary>
    /// The preferred width is not specified. The actual width of the table or cell is either specified using the explicit width or 
    /// will be determined automatically by the table layout algorithm when the table is displayed, depending on the table auto fit setting.
    /// </summary>
    public static final int AUTO = 1;
    /// <summary>
    /// Measure the current item width using a specified percentage.
    /// </summary>
    public static final int PERCENT = 2;
    /// <summary>
    /// Measure the current item width using a specified number of points (1/72 inch).
    /// </summary>
    public static final int POINTS = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PreferredWidthType value.
    /// </summary>
    public static String getName(/*PreferredWidthType*/int preferredWidthType)
    {
    	switch (preferredWidthType)
    	{
    		case AUTO: return "AUTO";
    		case PERCENT: return "PERCENT";
    		case POINTS: return "POINTS";
    		default: return "Unknown PreferredWidthType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PreferredWidthType value.
    /// </summary>
    public static String toString(/*PreferredWidthType*/int preferredWidthType)
    {
    	switch (preferredWidthType)
    	{
    		case AUTO: return "Auto";
    		case PERCENT: return "Percent";
    		case POINTS: return "Points";
    		default: return "Unknown PreferredWidthType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PreferredWidthType name.
    /// </summary>
    public static /*PreferredWidthType*/int fromName(String preferredWidthTypeName)
    {
    	if ("AUTO".equals(preferredWidthTypeName)) return AUTO;
    	if ("PERCENT".equals(preferredWidthTypeName)) return PERCENT;
    	if ("POINTS".equals(preferredWidthTypeName)) return POINTS;
    	throw new IllegalArgumentException("Unknown PreferredWidthType name.");
    }
    
    /// <summary>
    /// Returns array of PreferredWidthType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AUTO,
    		PERCENT,
    		POINTS,
    	};
    }

    public static final int length = 3;
}

