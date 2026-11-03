// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/15/2012 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the possible extents which can be used to calculate the absolute positioning of this positional tab character.
/// </summary>
/*enum*/ final class AbsolutePositionTabPositioningBase
{
    private AbsolutePositionTabPositioningBase(){}
    
    /// <summary>
    /// Specifies that the absolute positioning of the tab shall be relative to the indents.
    /// </summary>
    public static final int INDENT = 0;

    /// <summary>
    /// Specifies that the absolute positioning of the tab shall be relative to the margins.
    /// </summary>
    public static final int MARGIN = 1;

    /// <summary>
    /// Defaults to <see cref="Indent"/>
    /// </summary>
    /// <remarks>This default is AW specific, it is not defined in OOXML specification.</remarks>
    public static final int DEFAULT = INDENT;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AbsolutePositionTabPositioningBase value.
    /// </summary>
    public static String getName(/*AbsolutePositionTabPositioningBase*/int absolutePositionTabPositioningBase)
    {
    	switch (absolutePositionTabPositioningBase)
    	{
    		case INDENT:
    		/*case DEFAULT:*/
    			return "INDENT" + " | " + "DEFAULT";
    		case MARGIN: return "MARGIN";
    		default: return "Unknown AbsolutePositionTabPositioningBase value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AbsolutePositionTabPositioningBase value.
    /// </summary>
    public static String toString(/*AbsolutePositionTabPositioningBase*/int absolutePositionTabPositioningBase)
    {
    	switch (absolutePositionTabPositioningBase)
    	{
    		case INDENT:
    		/*case DEFAULT:*/
    			return "Indent" + " | " + "Default";
    		case MARGIN: return "Margin";
    		default: return "Unknown AbsolutePositionTabPositioningBase value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AbsolutePositionTabPositioningBase name.
    /// </summary>
    public static /*AbsolutePositionTabPositioningBase*/int fromName(String absolutePositionTabPositioningBaseName)
    {
    	if ("INDENT".equals(absolutePositionTabPositioningBaseName)) return INDENT;
    	if ("MARGIN".equals(absolutePositionTabPositioningBaseName)) return MARGIN;
    	if ("DEFAULT".equals(absolutePositionTabPositioningBaseName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown AbsolutePositionTabPositioningBase name.");
    }
    
    /// <summary>
    /// Returns array of AbsolutePositionTabPositioningBase values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		INDENT,
    		MARGIN,
    		DEFAULT,
    	};
    }

    public static final int length = 3;
}

