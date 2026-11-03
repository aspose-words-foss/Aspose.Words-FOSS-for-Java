// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/07/2006 by Roman Korchagin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// WordArt alignment.
/// </summary>
/// <seealso cref="TextPath.TextPathAlignment"/>
public /*enum*/ final class TextPathAlignment
{
    private TextPathAlignment(){}
    
    /// <summary>
    /// Stretch each line of text to fit width.
    /// </summary>
    public static final int STRETCH = 0;      
    /// <summary>
    /// Center text on width.
    /// </summary>
    public static final int CENTER = 1;       
    /// <summary>
    /// Left justify.
    /// </summary>
    public static final int LEFT = 2;         
    /// <summary>
    /// Right justify.
    /// </summary>
    public static final int RIGHT = 3;        
    /// <summary>
    /// Spread letters out to fit width.
    /// </summary>
    public static final int LETTER_JUSTIFY = 4;   
    /// <summary>
    /// Spread words out to fit width.
    /// </summary>
    public static final int WORD_JUSTIFY = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TextPathAlignment value.
    /// </summary>
    public static String getName(/*TextPathAlignment*/int textPathAlignment)
    {
    	switch (textPathAlignment)
    	{
    		case STRETCH: return "STRETCH";
    		case CENTER: return "CENTER";
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		case LETTER_JUSTIFY: return "LETTER_JUSTIFY";
    		case WORD_JUSTIFY: return "WORD_JUSTIFY";
    		default: return "Unknown TextPathAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TextPathAlignment value.
    /// </summary>
    public static String toString(/*TextPathAlignment*/int textPathAlignment)
    {
    	switch (textPathAlignment)
    	{
    		case STRETCH: return "Stretch";
    		case CENTER: return "Center";
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		case LETTER_JUSTIFY: return "LetterJustify";
    		case WORD_JUSTIFY: return "WordJustify";
    		default: return "Unknown TextPathAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TextPathAlignment name.
    /// </summary>
    public static /*TextPathAlignment*/int fromName(String textPathAlignmentName)
    {
    	if ("STRETCH".equals(textPathAlignmentName)) return STRETCH;
    	if ("CENTER".equals(textPathAlignmentName)) return CENTER;
    	if ("LEFT".equals(textPathAlignmentName)) return LEFT;
    	if ("RIGHT".equals(textPathAlignmentName)) return RIGHT;
    	if ("LETTER_JUSTIFY".equals(textPathAlignmentName)) return LETTER_JUSTIFY;
    	if ("WORD_JUSTIFY".equals(textPathAlignmentName)) return WORD_JUSTIFY;
    	throw new IllegalArgumentException("Unknown TextPathAlignment name.");
    }
    
    /// <summary>
    /// Returns array of TextPathAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		STRETCH,
    		CENTER,
    		LEFT,
    		RIGHT,
    		LETTER_JUSTIFY,
    		WORD_JUSTIFY,
    	};
    }

    public static final int length = 6;
}

