// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/12/2022 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies relatively to what the height of a shape or a text frame is calculated vertically.
/// </summary>
/// <seealso cref="ShapeBase.RelativeVerticalSize"/>
/// <dev>
/// Do not renumber. The values are taken from the RTF specification.
/// </dev>
public /*enum*/ final class RelativeVerticalSize
{
    private RelativeVerticalSize(){}
    
    /// <summary>
    /// Specifies that the height is calculated relatively to the space between the top and the bottom margins.
    /// </summary>
    public static final int MARGIN = 0;
    /// <summary>
    /// Specifies that the height is calculated relatively to the page height.
    /// </summary>
    public static final int PAGE = 1;
    /// <summary>
    /// Specifies that the height is calculated relatively to the top margin area size.
    /// </summary>
    public static final int TOP_MARGIN = 2;
    /// <summary>
    /// Specifies that the height is calculated relatively to the bottom margin area size.
    /// </summary>
    public static final int BOTTOM_MARGIN = 3;
    /// <summary>
    /// Specifies that the height is calculated relatively to the inside margin area size,
    /// to the top margin area size for odd pages and to the bottom margin area size for even pages.
    /// </summary>
    public static final int INNER_MARGIN = 4;
    /// <summary>
    /// Specifies that the height is calculated relatively to the outside margin area size,
    /// to the bottom margin area size for odd pages and to the top margin area size for even pages.
    /// </summary>
    public static final int OUTER_MARGIN = 5;
    /// <summary>
    /// Default value is <see cref="Margin"/>.
    /// </summary>
    public static final int DEFAULT = PAGE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer RelativeVerticalSize value.
    /// </summary>
    public static String getName(/*RelativeVerticalSize*/int relativeVerticalSize)
    {
    	switch (relativeVerticalSize)
    	{
    		case MARGIN: return "MARGIN";
    		case PAGE:
    		/*case DEFAULT:*/
    			return "PAGE" + " | " + "DEFAULT";
    		case TOP_MARGIN: return "TOP_MARGIN";
    		case BOTTOM_MARGIN: return "BOTTOM_MARGIN";
    		case INNER_MARGIN: return "INNER_MARGIN";
    		case OUTER_MARGIN: return "OUTER_MARGIN";
    		default: return "Unknown RelativeVerticalSize value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer RelativeVerticalSize value.
    /// </summary>
    public static String toString(/*RelativeVerticalSize*/int relativeVerticalSize)
    {
    	switch (relativeVerticalSize)
    	{
    		case MARGIN: return "Margin";
    		case PAGE:
    		/*case DEFAULT:*/
    			return "Page" + " | " + "Default";
    		case TOP_MARGIN: return "TopMargin";
    		case BOTTOM_MARGIN: return "BottomMargin";
    		case INNER_MARGIN: return "InnerMargin";
    		case OUTER_MARGIN: return "OuterMargin";
    		default: return "Unknown RelativeVerticalSize value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by RelativeVerticalSize name.
    /// </summary>
    public static /*RelativeVerticalSize*/int fromName(String relativeVerticalSizeName)
    {
    	if ("MARGIN".equals(relativeVerticalSizeName)) return MARGIN;
    	if ("PAGE".equals(relativeVerticalSizeName)) return PAGE;
    	if ("TOP_MARGIN".equals(relativeVerticalSizeName)) return TOP_MARGIN;
    	if ("BOTTOM_MARGIN".equals(relativeVerticalSizeName)) return BOTTOM_MARGIN;
    	if ("INNER_MARGIN".equals(relativeVerticalSizeName)) return INNER_MARGIN;
    	if ("OUTER_MARGIN".equals(relativeVerticalSizeName)) return OUTER_MARGIN;
    	if ("DEFAULT".equals(relativeVerticalSizeName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown RelativeVerticalSize name.");
    }
    
    /// <summary>
    /// Returns array of RelativeVerticalSize values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		MARGIN,
    		PAGE,
    		TOP_MARGIN,
    		BOTTOM_MARGIN,
    		INNER_MARGIN,
    		OUTER_MARGIN,
    		DEFAULT,
    	};
    }

    public static final int length = 7;
}

