// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/12/2006 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the layout mode for a section allowing to define the document grid behavior.
/// </summary>
public /*enum*/ final class SectionLayoutMode
{
    private SectionLayoutMode(){}
    
    /// <summary>
    /// Specifies that no document grid shall be applied to the contents of the corresponding section in the document.
    /// </summary>
    public static final int DEFAULT = 0;

    /// <summary>
    /// Specifies that the corresponding section shall have both the additional line pitch and character pitch
    /// added to each line and character within it in order to maintain a specific number
    /// of lines per page and characters per line.
    /// Characters will not be automatically aligned with gridlines on typing.
    /// </summary>
    public static final int GRID = 1;

    /// <summary>
    /// Specifies that the corresponding section shall have additional line pitch added to each line within it
    /// in order to maintain the specified number of lines per page.
    /// </summary>
    public static final int LINE_GRID = 2;

    /// <summary>
    /// Specifies that the corresponding section shall have both the additional line pitch and character pitch
    /// added to each line and character within it in order to maintain a specific number
    /// of lines per page and characters per line. 
    /// Characters will be automatically aligned with gridlines on typing.
    /// </summary>
    public static final int SNAP_TO_CHARS = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SectionLayoutMode value.
    /// </summary>
    public static String getName(/*SectionLayoutMode*/int sectionLayoutMode)
    {
    	switch (sectionLayoutMode)
    	{
    		case DEFAULT: return "DEFAULT";
    		case GRID: return "GRID";
    		case LINE_GRID: return "LINE_GRID";
    		case SNAP_TO_CHARS: return "SNAP_TO_CHARS";
    		default: return "Unknown SectionLayoutMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SectionLayoutMode value.
    /// </summary>
    public static String toString(/*SectionLayoutMode*/int sectionLayoutMode)
    {
    	switch (sectionLayoutMode)
    	{
    		case DEFAULT: return "Default";
    		case GRID: return "Grid";
    		case LINE_GRID: return "LineGrid";
    		case SNAP_TO_CHARS: return "SnapToChars";
    		default: return "Unknown SectionLayoutMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SectionLayoutMode name.
    /// </summary>
    public static /*SectionLayoutMode*/int fromName(String sectionLayoutModeName)
    {
    	if ("DEFAULT".equals(sectionLayoutModeName)) return DEFAULT;
    	if ("GRID".equals(sectionLayoutModeName)) return GRID;
    	if ("LINE_GRID".equals(sectionLayoutModeName)) return LINE_GRID;
    	if ("SNAP_TO_CHARS".equals(sectionLayoutModeName)) return SNAP_TO_CHARS;
    	throw new IllegalArgumentException("Unknown SectionLayoutMode name.");
    }
    
    /// <summary>
    /// Returns array of SectionLayoutMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFAULT,
    		GRID,
    		LINE_GRID,
    		SNAP_TO_CHARS,
    	};
    }

    public static final int length = 4;
}

