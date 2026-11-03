// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/02/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies to what the vertical position of a shape or text frame is relative.
/// </summary>
/// <seealso cref="ShapeBase.RelativeVerticalPosition"/>
/// <dev>
/// Do not renumber. The values are taken from the RTF specification.
/// </dev>
public /*enum*/ final class RelativeVerticalPosition
{
    private RelativeVerticalPosition(){}
    
    /// <summary>
    /// Specifies that the vertical positioning shall be relative to the page margins.
    /// </summary>
    public static final int MARGIN = 0;
    /// <summary>
    /// The object is positioned relative to the top edge of the page.
    /// </summary>
    public static final int PAGE = 1;
    /// <summary>
    /// The object is positioned relative to the top of the paragraph that contains the anchor.
    /// </summary>
    public static final int PARAGRAPH = 2;
    /// <summary>
    /// Undocumented.
    /// </summary>
    public static final int LINE = 3;
    /// <summary>
    /// Specifies that the vertical positioning shall be relative to the top margin of the current page.
    /// </summary>
    public static final int TOP_MARGIN = 4;  // The value taken from a test RTF file.
    /// <summary>
    /// Specifies that the vertical positioning shall be relative to the bottom margin of the current page.
    /// </summary>
    public static final int BOTTOM_MARGIN = 5;   // The value taken from a test RTF file.
    /// <summary>
    /// Specifies that the vertical positioning shall be relative to the inside margin of the current page.
    /// </summary>
    public static final int INSIDE_MARGIN = 6;   // The value taken from a test RTF file.
    /// <summary>
    /// Specifies that the vertical positioning shall be relative to the outside margin of the current page.
    /// </summary>
    public static final int OUTSIDE_MARGIN = 7;  // The value taken from a test RTF file.
    /// <summary>
    /// Default value is <see cref="Margin"/>. 
    /// </summary>
    public static final int TABLE_DEFAULT = MARGIN;          // AM. Better naming for this value should be Default and it's applied to both table and paragraph.
                                    // We decided to don't change this for a while because it's public and we're not 100% sure yet.
    /// <summary>
    /// Default value is <see cref="Paragraph"/>. 
    /// </summary>
    public static final int TEXT_FRAME_DEFAULT = PARAGRAPH;    // AM. Better naming for this value should be ShapeDefault and it is drawing objects default.
                                    // We decided to don't change this for a while because it's public and we're not 100% sure yet.

    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer RelativeVerticalPosition value.
    /// </summary>
    public static String getName(/*RelativeVerticalPosition*/int relativeVerticalPosition)
    {
    	switch (relativeVerticalPosition)
    	{
    		case MARGIN:
    		/*case TABLE_DEFAULT:*/
    			return "MARGIN" + " | " + "TABLE_DEFAULT";
    		case PAGE: return "PAGE";
    		case PARAGRAPH:
    		/*case TEXT_FRAME_DEFAULT:*/
    			return "PARAGRAPH" + " | " + "TEXT_FRAME_DEFAULT";
    		case LINE: return "LINE";
    		case TOP_MARGIN: return "TOP_MARGIN";
    		case BOTTOM_MARGIN: return "BOTTOM_MARGIN";
    		case INSIDE_MARGIN: return "INSIDE_MARGIN";
    		case OUTSIDE_MARGIN: return "OUTSIDE_MARGIN";
    		default: return "Unknown RelativeVerticalPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer RelativeVerticalPosition value.
    /// </summary>
    public static String toString(/*RelativeVerticalPosition*/int relativeVerticalPosition)
    {
    	switch (relativeVerticalPosition)
    	{
    		case MARGIN:
    		/*case TABLE_DEFAULT:*/
    			return "Margin" + " | " + "TableDefault";
    		case PAGE: return "Page";
    		case PARAGRAPH:
    		/*case TEXT_FRAME_DEFAULT:*/
    			return "Paragraph" + " | " + "TextFrameDefault";
    		case LINE: return "Line";
    		case TOP_MARGIN: return "TopMargin";
    		case BOTTOM_MARGIN: return "BottomMargin";
    		case INSIDE_MARGIN: return "InsideMargin";
    		case OUTSIDE_MARGIN: return "OutsideMargin";
    		default: return "Unknown RelativeVerticalPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by RelativeVerticalPosition name.
    /// </summary>
    public static /*RelativeVerticalPosition*/int fromName(String relativeVerticalPositionName)
    {
    	if ("MARGIN".equals(relativeVerticalPositionName)) return MARGIN;
    	if ("PAGE".equals(relativeVerticalPositionName)) return PAGE;
    	if ("PARAGRAPH".equals(relativeVerticalPositionName)) return PARAGRAPH;
    	if ("LINE".equals(relativeVerticalPositionName)) return LINE;
    	if ("TOP_MARGIN".equals(relativeVerticalPositionName)) return TOP_MARGIN;
    	if ("BOTTOM_MARGIN".equals(relativeVerticalPositionName)) return BOTTOM_MARGIN;
    	if ("INSIDE_MARGIN".equals(relativeVerticalPositionName)) return INSIDE_MARGIN;
    	if ("OUTSIDE_MARGIN".equals(relativeVerticalPositionName)) return OUTSIDE_MARGIN;
    	if ("TABLE_DEFAULT".equals(relativeVerticalPositionName)) return TABLE_DEFAULT;
    	if ("TEXT_FRAME_DEFAULT".equals(relativeVerticalPositionName)) return TEXT_FRAME_DEFAULT;
    	throw new IllegalArgumentException("Unknown RelativeVerticalPosition name.");
    }
    
    /// <summary>
    /// Returns array of RelativeVerticalPosition values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		MARGIN,
    		PAGE,
    		PARAGRAPH,
    		LINE,
    		TOP_MARGIN,
    		BOTTOM_MARGIN,
    		INSIDE_MARGIN,
    		OUTSIDE_MARGIN,
    		TABLE_DEFAULT,
    		TEXT_FRAME_DEFAULT,
    	};
    }

    public static final int length = 10;
}

