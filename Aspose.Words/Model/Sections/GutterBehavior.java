// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2018 by Dmitry Matveenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents gutter behavior for page margin calculation from the client point of view.
/// </summary>
/// <remarks>
/// There is a tricky condition in MS Word with a side gutter.
/// MS Word counts it as present in some contexts but disregards in other contexts.
/// The behavior depends on compatibility mode.
/// Rather than having multiple conditions to choose the in several places where page margins are used,
/// the logic is handled by <see cref="PageMarginCalculator"/>. 
/// This type is used by the caller to specify the required behavior.
/// </remarks>
/*enum*/ final class GutterBehavior
{
    private GutterBehavior(){}
    
    /// <summary>
    /// Side gutter is counted regardless of the top gutter presence.
    /// </summary>
    /// <remarks>
    /// It is used for page borders, floating table and frames positioning and page metrics.
    /// </remarks>
    public static final int NORMAL = 0;

    /// <summary>
    /// Side gutter is not counted if top gutter is also present.
    /// </summary>
    /// <remarks>
    /// It is used for text width calculation in a section with evenly spaced columns.
    /// </remarks>
    public static final int COLUMN = 1;

    /// <summary>
    /// Side gutter is not counted if top gutter is also present in compatibility mode.
    /// </summary>
    /// <remarks>
    /// It is used for header and footer width calculation.
    /// </remarks>
    public static final int HEADER_FOOTER = 2;

    /// <summary>
    /// Side gutter is not counted if top gutter is also present in 2013 mode.
    /// </summary>
    /// <remarks>
    /// It is used for floating shape positioning.
    /// </remarks>
    public static final int FLOATING_SHAPE_POSITIONING = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer GutterBehavior value.
    /// </summary>
    public static String getName(/*GutterBehavior*/int gutterBehavior)
    {
    	switch (gutterBehavior)
    	{
    		case NORMAL: return "NORMAL";
    		case COLUMN: return "COLUMN";
    		case HEADER_FOOTER: return "HEADER_FOOTER";
    		case FLOATING_SHAPE_POSITIONING: return "FLOATING_SHAPE_POSITIONING";
    		default: return "Unknown GutterBehavior value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer GutterBehavior value.
    /// </summary>
    public static String toString(/*GutterBehavior*/int gutterBehavior)
    {
    	switch (gutterBehavior)
    	{
    		case NORMAL: return "Normal";
    		case COLUMN: return "Column";
    		case HEADER_FOOTER: return "HeaderFooter";
    		case FLOATING_SHAPE_POSITIONING: return "FloatingShapePositioning";
    		default: return "Unknown GutterBehavior value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by GutterBehavior name.
    /// </summary>
    public static /*GutterBehavior*/int fromName(String gutterBehaviorName)
    {
    	if ("NORMAL".equals(gutterBehaviorName)) return NORMAL;
    	if ("COLUMN".equals(gutterBehaviorName)) return COLUMN;
    	if ("HEADER_FOOTER".equals(gutterBehaviorName)) return HEADER_FOOTER;
    	if ("FLOATING_SHAPE_POSITIONING".equals(gutterBehaviorName)) return FLOATING_SHAPE_POSITIONING;
    	throw new IllegalArgumentException("Unknown GutterBehavior name.");
    }
    
    /// <summary>
    /// Returns array of GutterBehavior values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NORMAL,
    		COLUMN,
    		HEADER_FOOTER,
    		FLOATING_SHAPE_POSITIONING,
    	};
    }

    public static final int length = 4;
}

