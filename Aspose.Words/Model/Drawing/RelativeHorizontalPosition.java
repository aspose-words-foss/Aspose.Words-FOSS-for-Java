// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/02/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies to what the horizontal position of a shape or text frame is relative.
/// </summary>
/// <seealso cref="ShapeBase.RelativeHorizontalPosition"/>
/// <dev>
/// Do not renumber. The values are taken from the RTF specification.
/// </dev>
public /*enum*/ final class RelativeHorizontalPosition
{
    private RelativeHorizontalPosition(){}
    
    /// <summary>
    /// Specifies that the horizontal positioning shall be relative to the page margins.
    /// </summary>
    public static final int MARGIN = 0;
    /// <summary>
    /// The object is positioned relative to the left edge of the page.
    /// </summary>
    public static final int PAGE = 1;
    /// <summary>
    /// The object is positioned relative to the left side of the column.
    /// </summary>
    public static final int COLUMN = 2;
    /// <summary>
    /// The object is positioned relative to the left side of the paragraph.
    /// </summary>
    public static final int CHARACTER = 3;
    /// <summary>
    /// Specifies that the horizontal positioning shall be relative to the left margin of the page.
    /// </summary>
    public static final int LEFT_MARGIN = 4; // The value taken from a test RTF file.
    /// <summary>
    /// Specifies that the horizontal positioning shall be relative to the right margin of the page.
    /// </summary>
    public static final int RIGHT_MARGIN = 5;    // The value taken from a test RTF file.
    /// <summary>
    /// Specifies that the horizontal positioning shall be relative to the inside margin of the 
    /// current page (the left margin on odd pages, right on even pages).
    /// </summary>
    public static final int INSIDE_MARGIN = 6;   // The value taken from a test RTF file.
    /// <summary>
    /// Specifies that the horizontal positioning shall be relative to the outside margin of the 
    /// current page (the right margin on odd pages, left on even pages).
    /// </summary>
    public static final int OUTSIDE_MARGIN = 7;  // The value taken from a test RTF file.
    /// <summary>
    /// Default value is <see cref="Column"/>.
    /// </summary>
    public static final int DEFAULT = COLUMN;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer RelativeHorizontalPosition value.
    /// </summary>
    public static String getName(/*RelativeHorizontalPosition*/int relativeHorizontalPosition)
    {
    	switch (relativeHorizontalPosition)
    	{
    		case MARGIN: return "MARGIN";
    		case PAGE: return "PAGE";
    		case COLUMN:
    		/*case DEFAULT:*/
    			return "COLUMN" + " | " + "DEFAULT";
    		case CHARACTER: return "CHARACTER";
    		case LEFT_MARGIN: return "LEFT_MARGIN";
    		case RIGHT_MARGIN: return "RIGHT_MARGIN";
    		case INSIDE_MARGIN: return "INSIDE_MARGIN";
    		case OUTSIDE_MARGIN: return "OUTSIDE_MARGIN";
    		default: return "Unknown RelativeHorizontalPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer RelativeHorizontalPosition value.
    /// </summary>
    public static String toString(/*RelativeHorizontalPosition*/int relativeHorizontalPosition)
    {
    	switch (relativeHorizontalPosition)
    	{
    		case MARGIN: return "Margin";
    		case PAGE: return "Page";
    		case COLUMN:
    		/*case DEFAULT:*/
    			return "Column" + " | " + "Default";
    		case CHARACTER: return "Character";
    		case LEFT_MARGIN: return "LeftMargin";
    		case RIGHT_MARGIN: return "RightMargin";
    		case INSIDE_MARGIN: return "InsideMargin";
    		case OUTSIDE_MARGIN: return "OutsideMargin";
    		default: return "Unknown RelativeHorizontalPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by RelativeHorizontalPosition name.
    /// </summary>
    public static /*RelativeHorizontalPosition*/int fromName(String relativeHorizontalPositionName)
    {
    	if ("MARGIN".equals(relativeHorizontalPositionName)) return MARGIN;
    	if ("PAGE".equals(relativeHorizontalPositionName)) return PAGE;
    	if ("COLUMN".equals(relativeHorizontalPositionName)) return COLUMN;
    	if ("CHARACTER".equals(relativeHorizontalPositionName)) return CHARACTER;
    	if ("LEFT_MARGIN".equals(relativeHorizontalPositionName)) return LEFT_MARGIN;
    	if ("RIGHT_MARGIN".equals(relativeHorizontalPositionName)) return RIGHT_MARGIN;
    	if ("INSIDE_MARGIN".equals(relativeHorizontalPositionName)) return INSIDE_MARGIN;
    	if ("OUTSIDE_MARGIN".equals(relativeHorizontalPositionName)) return OUTSIDE_MARGIN;
    	if ("DEFAULT".equals(relativeHorizontalPositionName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown RelativeHorizontalPosition name.");
    }
    
    /// <summary>
    /// Returns array of RelativeHorizontalPosition values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		MARGIN,
    		PAGE,
    		COLUMN,
    		CHARACTER,
    		LEFT_MARGIN,
    		RIGHT_MARGIN,
    		INSIDE_MARGIN,
    		OUTSIDE_MARGIN,
    		DEFAULT,
    	};
    }

    public static final int length = 9;
}

