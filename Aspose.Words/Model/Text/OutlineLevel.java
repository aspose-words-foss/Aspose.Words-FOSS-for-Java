// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies the outline level of a paragraph in the document.
/// </summary>
public /*enum*/ final class OutlineLevel
{
    private OutlineLevel(){}
    
    /// <summary>
    /// The paragraph is at the outline level 1 (topmost level).
    /// </summary>
    public static final int LEVEL_1 = 0;
    /// <summary>
    /// The paragraph is at the outline level 2.
    /// </summary>
    public static final int LEVEL_2 = 1;
    /// <summary>
    /// The paragraph is at the outline level 3.
    /// </summary>
    public static final int LEVEL_3 = 2;
    /// <summary>
    /// The paragraph is at the outline level 4.
    /// </summary>
    public static final int LEVEL_4 = 3;
    /// <summary>
    /// The paragraph is at the outline level 5.
    /// </summary>
    public static final int LEVEL_5 = 4;
    /// <summary>
    /// The paragraph is at the outline level 6.
    /// </summary>
    public static final int LEVEL_6 = 5;
    /// <summary>
    /// The paragraph is at the outline level 7.
    /// </summary>
    public static final int LEVEL_7 = 6;
    /// <summary>
    /// The paragraph is at the outline level 8.
    /// </summary>
    public static final int LEVEL_8 = 7;
    /// <summary>
    /// The paragraph is at the outline level 9.
    /// </summary>
    public static final int LEVEL_9 = 8;
    /// <summary>
    /// The paragraph is at the level of the main text.
    /// </summary>
    public static final int BODY_TEXT = 9;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer OutlineLevel value.
    /// </summary>
    public static String getName(/*OutlineLevel*/int outlineLevel)
    {
    	switch (outlineLevel)
    	{
    		case LEVEL_1: return "LEVEL_1";
    		case LEVEL_2: return "LEVEL_2";
    		case LEVEL_3: return "LEVEL_3";
    		case LEVEL_4: return "LEVEL_4";
    		case LEVEL_5: return "LEVEL_5";
    		case LEVEL_6: return "LEVEL_6";
    		case LEVEL_7: return "LEVEL_7";
    		case LEVEL_8: return "LEVEL_8";
    		case LEVEL_9: return "LEVEL_9";
    		case BODY_TEXT: return "BODY_TEXT";
    		default: return "Unknown OutlineLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer OutlineLevel value.
    /// </summary>
    public static String toString(/*OutlineLevel*/int outlineLevel)
    {
    	switch (outlineLevel)
    	{
    		case LEVEL_1: return "Level1";
    		case LEVEL_2: return "Level2";
    		case LEVEL_3: return "Level3";
    		case LEVEL_4: return "Level4";
    		case LEVEL_5: return "Level5";
    		case LEVEL_6: return "Level6";
    		case LEVEL_7: return "Level7";
    		case LEVEL_8: return "Level8";
    		case LEVEL_9: return "Level9";
    		case BODY_TEXT: return "BodyText";
    		default: return "Unknown OutlineLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by OutlineLevel name.
    /// </summary>
    public static /*OutlineLevel*/int fromName(String outlineLevelName)
    {
    	if ("LEVEL_1".equals(outlineLevelName)) return LEVEL_1;
    	if ("LEVEL_2".equals(outlineLevelName)) return LEVEL_2;
    	if ("LEVEL_3".equals(outlineLevelName)) return LEVEL_3;
    	if ("LEVEL_4".equals(outlineLevelName)) return LEVEL_4;
    	if ("LEVEL_5".equals(outlineLevelName)) return LEVEL_5;
    	if ("LEVEL_6".equals(outlineLevelName)) return LEVEL_6;
    	if ("LEVEL_7".equals(outlineLevelName)) return LEVEL_7;
    	if ("LEVEL_8".equals(outlineLevelName)) return LEVEL_8;
    	if ("LEVEL_9".equals(outlineLevelName)) return LEVEL_9;
    	if ("BODY_TEXT".equals(outlineLevelName)) return BODY_TEXT;
    	throw new IllegalArgumentException("Unknown OutlineLevel name.");
    }
    
    /// <summary>
    /// Returns array of OutlineLevel values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEVEL_1,
    		LEVEL_2,
    		LEVEL_3,
    		LEVEL_4,
    		LEVEL_5,
    		LEVEL_6,
    		LEVEL_7,
    		LEVEL_8,
    		LEVEL_9,
    		BODY_TEXT,
    	};
    }

    public static final int length = 10;
}

