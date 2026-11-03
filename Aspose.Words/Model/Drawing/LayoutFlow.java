// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Determines the flow of the text layout in a textbox. 
/// </summary>
/// <seealso cref="TextBox.LayoutFlow"/>
/// <dev>
/// Do not renumber. The values are from the RTF spec.
/// </dev>
public /*enum*/ final class LayoutFlow
{
    private LayoutFlow(){}
    
    /// <summary>
    /// Text is displayed horizontally.
    /// </summary>
    public static final int HORIZONTAL = 0;
    /// <summary>
    /// Ideographic text is displayed vertically.
    /// </summary>
    public static final int TOP_TO_BOTTOM_IDEOGRAPHIC = 1;
    /// <summary>
    /// Text is displayed vertically.
    /// </summary>
    public static final int BOTTOM_TO_TOP = 2;
    /// <summary>
    /// Text is displayed vertically.
    /// </summary>
    public static final int TOP_TO_BOTTOM = 3;
    /// <summary>
    /// Ideographic text is displayed horizontally.
    /// </summary>
    public static final int HORIZONTAL_IDEOGRAPHIC = 4;
    /// <summary>
    /// Text is displayed vertically.
    /// </summary>
    public static final int VERTICAL = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer LayoutFlow value.
    /// </summary>
    public static String getName(/*LayoutFlow*/int layoutFlow)
    {
    	switch (layoutFlow)
    	{
    		case HORIZONTAL: return "HORIZONTAL";
    		case TOP_TO_BOTTOM_IDEOGRAPHIC: return "TOP_TO_BOTTOM_IDEOGRAPHIC";
    		case BOTTOM_TO_TOP: return "BOTTOM_TO_TOP";
    		case TOP_TO_BOTTOM: return "TOP_TO_BOTTOM";
    		case HORIZONTAL_IDEOGRAPHIC: return "HORIZONTAL_IDEOGRAPHIC";
    		case VERTICAL: return "VERTICAL";
    		default: return "Unknown LayoutFlow value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer LayoutFlow value.
    /// </summary>
    public static String toString(/*LayoutFlow*/int layoutFlow)
    {
    	switch (layoutFlow)
    	{
    		case HORIZONTAL: return "Horizontal";
    		case TOP_TO_BOTTOM_IDEOGRAPHIC: return "TopToBottomIdeographic";
    		case BOTTOM_TO_TOP: return "BottomToTop";
    		case TOP_TO_BOTTOM: return "TopToBottom";
    		case HORIZONTAL_IDEOGRAPHIC: return "HorizontalIdeographic";
    		case VERTICAL: return "Vertical";
    		default: return "Unknown LayoutFlow value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by LayoutFlow name.
    /// </summary>
    public static /*LayoutFlow*/int fromName(String layoutFlowName)
    {
    	if ("HORIZONTAL".equals(layoutFlowName)) return HORIZONTAL;
    	if ("TOP_TO_BOTTOM_IDEOGRAPHIC".equals(layoutFlowName)) return TOP_TO_BOTTOM_IDEOGRAPHIC;
    	if ("BOTTOM_TO_TOP".equals(layoutFlowName)) return BOTTOM_TO_TOP;
    	if ("TOP_TO_BOTTOM".equals(layoutFlowName)) return TOP_TO_BOTTOM;
    	if ("HORIZONTAL_IDEOGRAPHIC".equals(layoutFlowName)) return HORIZONTAL_IDEOGRAPHIC;
    	if ("VERTICAL".equals(layoutFlowName)) return VERTICAL;
    	throw new IllegalArgumentException("Unknown LayoutFlow name.");
    }
    
    /// <summary>
    /// Returns array of LayoutFlow values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		HORIZONTAL,
    		TOP_TO_BOTTOM_IDEOGRAPHIC,
    		BOTTOM_TO_TOP,
    		TOP_TO_BOTTOM,
    		HORIZONTAL_IDEOGRAPHIC,
    		VERTICAL,
    	};
    }

    public static final int length = 6;
}

