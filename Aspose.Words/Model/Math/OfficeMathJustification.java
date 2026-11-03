// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies the justification of the equation.
/// </summary>
public /*enum*/ final class OfficeMathJustification
{
    private OfficeMathJustification(){}
    
    /// <summary>
    /// Justifies instances of mathematical text to the left with respect to each other, and centers the group of mathematical
    /// text (the Math Paragraph) with respect to the page.
    /// </summary>
    public static final int CENTER_GROUP = 1;

    /// <summary>
    /// Centers each instance of mathematical text individually with respect to margins.
    /// </summary>
    public static final int CENTER = 2;
    
    /// <summary>
    /// Left justification of Math Paragraph.
    /// </summary>
    public static final int LEFT = 3;
    
    /// <summary>
    /// Right Justification of Math Paragraph.
    /// </summary>
    public static final int RIGHT = 4;

    /// <summary>
    /// Inline position of Math.
    /// </summary>
    public static final int INLINE = 7;

    /// <summary>
    /// Default value <see cref="CenterGroup"/>.
    /// </summary>
    public static final int DEFAULT = CENTER_GROUP;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer OfficeMathJustification value.
    /// </summary>
    public static String getName(/*OfficeMathJustification*/int officeMathJustification)
    {
    	switch (officeMathJustification)
    	{
    		case CENTER_GROUP:
    		/*case DEFAULT:*/
    			return "CENTER_GROUP" + " | " + "DEFAULT";
    		case CENTER: return "CENTER";
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		case INLINE: return "INLINE";
    		default: return "Unknown OfficeMathJustification value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer OfficeMathJustification value.
    /// </summary>
    public static String toString(/*OfficeMathJustification*/int officeMathJustification)
    {
    	switch (officeMathJustification)
    	{
    		case CENTER_GROUP:
    		/*case DEFAULT:*/
    			return "CenterGroup" + " | " + "Default";
    		case CENTER: return "Center";
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		case INLINE: return "Inline";
    		default: return "Unknown OfficeMathJustification value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by OfficeMathJustification name.
    /// </summary>
    public static /*OfficeMathJustification*/int fromName(String officeMathJustificationName)
    {
    	if ("CENTER_GROUP".equals(officeMathJustificationName)) return CENTER_GROUP;
    	if ("CENTER".equals(officeMathJustificationName)) return CENTER;
    	if ("LEFT".equals(officeMathJustificationName)) return LEFT;
    	if ("RIGHT".equals(officeMathJustificationName)) return RIGHT;
    	if ("INLINE".equals(officeMathJustificationName)) return INLINE;
    	if ("DEFAULT".equals(officeMathJustificationName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown OfficeMathJustification name.");
    }
    
    /// <summary>
    /// Returns array of OfficeMathJustification values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CENTER_GROUP,
    		CENTER,
    		LEFT,
    		RIGHT,
    		INLINE,
    		DEFAULT,
    	};
    }

    public static final int length = 6;
}

