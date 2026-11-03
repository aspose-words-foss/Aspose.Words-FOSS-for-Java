// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies text alignment in a paragraph.
/// </summary>
public /*enum*/ final class ParagraphAlignment
{
    private ParagraphAlignment(){}
    
    /// <summary>
    /// Text is aligned to the left.
    /// </summary>
    public static final int LEFT = 0;

    /// <summary>
    /// Text is centered horizontally.
    /// </summary>
    public static final int CENTER = 1;

    /// <summary>
    /// Text is aligned to the right.
    /// </summary>
    public static final int RIGHT = 2;

    /// <summary>
    /// Text is aligned to both left and right.
    /// </summary>
    public static final int JUSTIFY = 3;

    /// <summary>
    /// Text is evenly distributed.
    /// </summary>
    public static final int DISTRIBUTED = 4;

    /// <summary>
    /// Arabic only. Kashida length for text is extended to a medium length determined by the consumer.
    /// </summary>
    public static final int ARABIC_MEDIUM_KASHIDA = 5;

    /// <summary>
    /// Arabic only. Kashida length for text is extended to its widest possible length.
    /// </summary>
    public static final int ARABIC_HIGH_KASHIDA = 7;

    /// <summary>
    /// Arabic only. Kashida length for text is extended to a slightly longer length.
    /// </summary>
    public static final int ARABIC_LOW_KASHIDA = 8;

    /// <summary>
    /// Thai only. Text is justified with an optimization for Thai.
    /// </summary>
    public static final int THAI_DISTRIBUTED = 9;

    /// <summary>
    /// The only Math element in a line, aligned as 'Centered As Group'.
    /// </summary>
    public static final int MATH_ELEMENT_CENTER_AS_GROUP = 10;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ParagraphAlignment value.
    /// </summary>
    public static String getName(/*ParagraphAlignment*/int paragraphAlignment)
    {
    	switch (paragraphAlignment)
    	{
    		case LEFT: return "LEFT";
    		case CENTER: return "CENTER";
    		case RIGHT: return "RIGHT";
    		case JUSTIFY: return "JUSTIFY";
    		case DISTRIBUTED: return "DISTRIBUTED";
    		case ARABIC_MEDIUM_KASHIDA: return "ARABIC_MEDIUM_KASHIDA";
    		case ARABIC_HIGH_KASHIDA: return "ARABIC_HIGH_KASHIDA";
    		case ARABIC_LOW_KASHIDA: return "ARABIC_LOW_KASHIDA";
    		case THAI_DISTRIBUTED: return "THAI_DISTRIBUTED";
    		case MATH_ELEMENT_CENTER_AS_GROUP: return "MATH_ELEMENT_CENTER_AS_GROUP";
    		default: return "Unknown ParagraphAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ParagraphAlignment value.
    /// </summary>
    public static String toString(/*ParagraphAlignment*/int paragraphAlignment)
    {
    	switch (paragraphAlignment)
    	{
    		case LEFT: return "Left";
    		case CENTER: return "Center";
    		case RIGHT: return "Right";
    		case JUSTIFY: return "Justify";
    		case DISTRIBUTED: return "Distributed";
    		case ARABIC_MEDIUM_KASHIDA: return "ArabicMediumKashida";
    		case ARABIC_HIGH_KASHIDA: return "ArabicHighKashida";
    		case ARABIC_LOW_KASHIDA: return "ArabicLowKashida";
    		case THAI_DISTRIBUTED: return "ThaiDistributed";
    		case MATH_ELEMENT_CENTER_AS_GROUP: return "MathElementCenterAsGroup";
    		default: return "Unknown ParagraphAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ParagraphAlignment name.
    /// </summary>
    public static /*ParagraphAlignment*/int fromName(String paragraphAlignmentName)
    {
    	if ("LEFT".equals(paragraphAlignmentName)) return LEFT;
    	if ("CENTER".equals(paragraphAlignmentName)) return CENTER;
    	if ("RIGHT".equals(paragraphAlignmentName)) return RIGHT;
    	if ("JUSTIFY".equals(paragraphAlignmentName)) return JUSTIFY;
    	if ("DISTRIBUTED".equals(paragraphAlignmentName)) return DISTRIBUTED;
    	if ("ARABIC_MEDIUM_KASHIDA".equals(paragraphAlignmentName)) return ARABIC_MEDIUM_KASHIDA;
    	if ("ARABIC_HIGH_KASHIDA".equals(paragraphAlignmentName)) return ARABIC_HIGH_KASHIDA;
    	if ("ARABIC_LOW_KASHIDA".equals(paragraphAlignmentName)) return ARABIC_LOW_KASHIDA;
    	if ("THAI_DISTRIBUTED".equals(paragraphAlignmentName)) return THAI_DISTRIBUTED;
    	if ("MATH_ELEMENT_CENTER_AS_GROUP".equals(paragraphAlignmentName)) return MATH_ELEMENT_CENTER_AS_GROUP;
    	throw new IllegalArgumentException("Unknown ParagraphAlignment name.");
    }
    
    /// <summary>
    /// Returns array of ParagraphAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEFT,
    		CENTER,
    		RIGHT,
    		JUSTIFY,
    		DISTRIBUTED,
    		ARABIC_MEDIUM_KASHIDA,
    		ARABIC_HIGH_KASHIDA,
    		ARABIC_LOW_KASHIDA,
    		THAI_DISTRIBUTED,
    		MATH_ELEMENT_CENTER_AS_GROUP,
    	};
    }

    public static final int length = 10;
}

