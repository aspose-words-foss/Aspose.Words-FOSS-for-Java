// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies alignment for the list number or bullet.
/// </summary>
/// <remarks>
/// <p>Used as a value for the <see cref="ListLevel.Alignment"/> property.</p>
/// </remarks>
public /*enum*/ final class ListLevelAlignment
{
    private ListLevelAlignment(){}
    
    /// <summary>
    /// The list label is aligned to the left of the number position.
    /// </summary>
    public static final int LEFT = 0;
    /// <summary>
    /// The list label is centered at the number position.
    /// </summary>
    public static final int CENTER = 1;
    /// <summary>
    /// This list label is aligned to the right of the number position.
    /// </summary>
    public static final int RIGHT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ListLevelAlignment value.
    /// </summary>
    public static String getName(/*ListLevelAlignment*/int listLevelAlignment)
    {
    	switch (listLevelAlignment)
    	{
    		case LEFT: return "LEFT";
    		case CENTER: return "CENTER";
    		case RIGHT: return "RIGHT";
    		default: return "Unknown ListLevelAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ListLevelAlignment value.
    /// </summary>
    public static String toString(/*ListLevelAlignment*/int listLevelAlignment)
    {
    	switch (listLevelAlignment)
    	{
    		case LEFT: return "Left";
    		case CENTER: return "Center";
    		case RIGHT: return "Right";
    		default: return "Unknown ListLevelAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ListLevelAlignment name.
    /// </summary>
    public static /*ListLevelAlignment*/int fromName(String listLevelAlignmentName)
    {
    	if ("LEFT".equals(listLevelAlignmentName)) return LEFT;
    	if ("CENTER".equals(listLevelAlignmentName)) return CENTER;
    	if ("RIGHT".equals(listLevelAlignmentName)) return RIGHT;
    	throw new IllegalArgumentException("Unknown ListLevelAlignment name.");
    }
    
    /// <summary>
    /// Returns array of ListLevelAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEFT,
    		CENTER,
    		RIGHT,
    	};
    }

    public static final int length = 3;
}

