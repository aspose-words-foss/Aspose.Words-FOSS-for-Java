// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/15/2012 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the alignment of an absolutely positioned tab character in a document. 
/// This alignment value determines the position on the line to which this absolute tab shall advance, 
/// as well as the alignment of the text after the alignment tab character position.
/// </summary>
/*enum*/ final class AbsolutePositionTabAlignment
{
    private AbsolutePositionTabAlignment(){}
    
    /// <summary>
    /// Specifies that the positional tab should be left aligned on the line relative to the specified base
    /// and that the text at that location shall be left aligned.
    /// </summary>
    public static final int LEFT = 0;

    /// <summary>
    /// Specifies that the positional tab should be center aligned on the line relative to the specified base
    /// and that the text at that location shall be center aligned.
    /// </summary>
    public static final int CENTER = 1;
    /// <summary>
    /// Specifies that the positional tab should be right aligned on the line relative to the specified base
    /// and that the text at that location shall be right aligned.
    /// </summary>
    public static final int RIGHT = 2;

    /// <summary>
    /// Defaults to <see cref="Left"/>
    /// </summary>
    /// <remarks>This default is AW specific, it is not defined in OOXML specification.</remarks>
    public static final int DEFAULT = LEFT;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AbsolutePositionTabAlignment value.
    /// </summary>
    public static String getName(/*AbsolutePositionTabAlignment*/int absolutePositionTabAlignment)
    {
    	switch (absolutePositionTabAlignment)
    	{
    		case LEFT:
    		/*case DEFAULT:*/
    			return "LEFT" + " | " + "DEFAULT";
    		case CENTER: return "CENTER";
    		case RIGHT: return "RIGHT";
    		default: return "Unknown AbsolutePositionTabAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AbsolutePositionTabAlignment value.
    /// </summary>
    public static String toString(/*AbsolutePositionTabAlignment*/int absolutePositionTabAlignment)
    {
    	switch (absolutePositionTabAlignment)
    	{
    		case LEFT:
    		/*case DEFAULT:*/
    			return "Left" + " | " + "Default";
    		case CENTER: return "Center";
    		case RIGHT: return "Right";
    		default: return "Unknown AbsolutePositionTabAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AbsolutePositionTabAlignment name.
    /// </summary>
    public static /*AbsolutePositionTabAlignment*/int fromName(String absolutePositionTabAlignmentName)
    {
    	if ("LEFT".equals(absolutePositionTabAlignmentName)) return LEFT;
    	if ("CENTER".equals(absolutePositionTabAlignmentName)) return CENTER;
    	if ("RIGHT".equals(absolutePositionTabAlignmentName)) return RIGHT;
    	if ("DEFAULT".equals(absolutePositionTabAlignmentName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown AbsolutePositionTabAlignment name.");
    }
    
    /// <summary>
    /// Returns array of AbsolutePositionTabAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEFT,
    		CENTER,
    		RIGHT,
    		DEFAULT,
    	};
    }

    public static final int length = 4;
}

