// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/08/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of alignment to be applied to the phonetic guide text.
/// </summary>
/*enum*/ final class RubyAlignment
{
    private RubyAlignment(){}
    
    /// <summary>
    /// Phonetic guide text should be centered.
    /// </summary>
    public static final int CENTER = 0;

    /// <summary>
    /// Phonetic guide text should be distributed.
    /// </summary>
    public static final int DISTRIBUTE_LETTER = 1;

    /// <summary>
    /// Phonetic guide text should be distributed with additional space added.
    /// </summary>
    public static final int DISTRIBUTE_SPACE = 2;

    /// <summary>
    /// Phonetic guide text should be left aligned.
    /// </summary>
    public static final int LEFT = 3;

    /// <summary>
    /// Phonetic guide text shall be right aligned.
    /// </summary>
    public static final int RIGHT = 4;

    /// <summary>
    /// Phonetic guide text should be right aligned and displayed vertically.
    /// </summary>
    public static final int RIGHT_VERTICAL = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer RubyAlignment value.
    /// </summary>
    public static String getName(/*RubyAlignment*/int rubyAlignment)
    {
    	switch (rubyAlignment)
    	{
    		case CENTER: return "CENTER";
    		case DISTRIBUTE_LETTER: return "DISTRIBUTE_LETTER";
    		case DISTRIBUTE_SPACE: return "DISTRIBUTE_SPACE";
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		case RIGHT_VERTICAL: return "RIGHT_VERTICAL";
    		default: return "Unknown RubyAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer RubyAlignment value.
    /// </summary>
    public static String toString(/*RubyAlignment*/int rubyAlignment)
    {
    	switch (rubyAlignment)
    	{
    		case CENTER: return "Center";
    		case DISTRIBUTE_LETTER: return "DistributeLetter";
    		case DISTRIBUTE_SPACE: return "DistributeSpace";
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		case RIGHT_VERTICAL: return "RightVertical";
    		default: return "Unknown RubyAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by RubyAlignment name.
    /// </summary>
    public static /*RubyAlignment*/int fromName(String rubyAlignmentName)
    {
    	if ("CENTER".equals(rubyAlignmentName)) return CENTER;
    	if ("DISTRIBUTE_LETTER".equals(rubyAlignmentName)) return DISTRIBUTE_LETTER;
    	if ("DISTRIBUTE_SPACE".equals(rubyAlignmentName)) return DISTRIBUTE_SPACE;
    	if ("LEFT".equals(rubyAlignmentName)) return LEFT;
    	if ("RIGHT".equals(rubyAlignmentName)) return RIGHT;
    	if ("RIGHT_VERTICAL".equals(rubyAlignmentName)) return RIGHT_VERTICAL;
    	throw new IllegalArgumentException("Unknown RubyAlignment name.");
    }
    
    /// <summary>
    /// Returns array of RubyAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CENTER,
    		DISTRIBUTE_LETTER,
    		DISTRIBUTE_SPACE,
    		LEFT,
    		RIGHT,
    		RIGHT_VERTICAL,
    	};
    }

    public static final int length = 6;
}

