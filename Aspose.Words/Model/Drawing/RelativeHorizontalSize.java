// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/12/2022 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies relatively to what the width of a shape or a text frame is calculated horizontally.
/// </summary>
/// <seealso cref="ShapeBase.RelativeHorizontalSize"/>
/// <dev>
/// Do not renumber. The values are taken from the RTF specification.
/// </dev>
public /*enum*/ final class RelativeHorizontalSize
{
    private RelativeHorizontalSize(){}
    
    /// <summary>
    /// Specifies that the width is calculated relatively to the space between the left and the right margins.
    /// </summary>
    public static final int MARGIN = 0;
    /// <summary>
    /// Specifies that the width is calculated relatively to the page width.
    /// </summary>
    public static final int PAGE = 1;
    /// <summary>
    /// Specifies that the width is calculated relatively to the left margin area size.
    /// </summary>
    public static final int LEFT_MARGIN = 2;
    /// <summary>
    /// Specifies that the width is calculated relatively to the right margin area size.
    /// </summary>
    public static final int RIGHT_MARGIN = 3;
    /// <summary>
    /// Specifies that the width is calculated relatively to the inside margin area size,
    /// to the left margin area size for odd pages and to the right margin area size for even pages.
    /// </summary>
    public static final int INNER_MARGIN = 4;
    /// <summary>
    /// Specifies that the width is calculated relatively to the outside margin area size,
    /// to the right margin area size for odd pages and to the left margin area size for even pages.
    /// </summary>
    public static final int OUTER_MARGIN = 5;
    /// <summary>
    /// Default value is <see cref="Margin"/>.
    /// </summary>
    public static final int DEFAULT = PAGE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer RelativeHorizontalSize value.
    /// </summary>
    public static String getName(/*RelativeHorizontalSize*/int relativeHorizontalSize)
    {
    	switch (relativeHorizontalSize)
    	{
    		case MARGIN: return "MARGIN";
    		case PAGE:
    		/*case DEFAULT:*/
    			return "PAGE" + " | " + "DEFAULT";
    		case LEFT_MARGIN: return "LEFT_MARGIN";
    		case RIGHT_MARGIN: return "RIGHT_MARGIN";
    		case INNER_MARGIN: return "INNER_MARGIN";
    		case OUTER_MARGIN: return "OUTER_MARGIN";
    		default: return "Unknown RelativeHorizontalSize value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer RelativeHorizontalSize value.
    /// </summary>
    public static String toString(/*RelativeHorizontalSize*/int relativeHorizontalSize)
    {
    	switch (relativeHorizontalSize)
    	{
    		case MARGIN: return "Margin";
    		case PAGE:
    		/*case DEFAULT:*/
    			return "Page" + " | " + "Default";
    		case LEFT_MARGIN: return "LeftMargin";
    		case RIGHT_MARGIN: return "RightMargin";
    		case INNER_MARGIN: return "InnerMargin";
    		case OUTER_MARGIN: return "OuterMargin";
    		default: return "Unknown RelativeHorizontalSize value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by RelativeHorizontalSize name.
    /// </summary>
    public static /*RelativeHorizontalSize*/int fromName(String relativeHorizontalSizeName)
    {
    	if ("MARGIN".equals(relativeHorizontalSizeName)) return MARGIN;
    	if ("PAGE".equals(relativeHorizontalSizeName)) return PAGE;
    	if ("LEFT_MARGIN".equals(relativeHorizontalSizeName)) return LEFT_MARGIN;
    	if ("RIGHT_MARGIN".equals(relativeHorizontalSizeName)) return RIGHT_MARGIN;
    	if ("INNER_MARGIN".equals(relativeHorizontalSizeName)) return INNER_MARGIN;
    	if ("OUTER_MARGIN".equals(relativeHorizontalSizeName)) return OUTER_MARGIN;
    	if ("DEFAULT".equals(relativeHorizontalSizeName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown RelativeHorizontalSize name.");
    }
    
    /// <summary>
    /// Returns array of RelativeHorizontalSize values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		MARGIN,
    		PAGE,
    		LEFT_MARGIN,
    		RIGHT_MARGIN,
    		INNER_MARGIN,
    		OUTER_MARGIN,
    		DEFAULT,
    	};
    }

    public static final int length = 7;
}

