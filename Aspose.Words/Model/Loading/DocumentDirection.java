// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/08/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows to specify the direction to flow the text in a document.
/// </summary>
public /*enum*/ final class DocumentDirection
{
    private DocumentDirection(){}
    
    /// <summary>
    /// Left to right direction.  
    /// </summary>
    public static final int LEFT_TO_RIGHT = 0;

    /// <summary>
    /// Right to left direction.
    /// </summary>
    public static final int RIGHT_TO_LEFT = 1;
    
    /// <summary>
    /// Auto-detect direction.
    /// </summary>
    /// <remarks>
    /// When this option is selected and text contains characters belonging to RTL scripts,
    /// the document direction will be set automatically to RTL.
    /// </remarks>
    public static final int AUTO = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DocumentDirection value.
    /// </summary>
    public static String getName(/*DocumentDirection*/int documentDirection)
    {
    	switch (documentDirection)
    	{
    		case LEFT_TO_RIGHT: return "LEFT_TO_RIGHT";
    		case RIGHT_TO_LEFT: return "RIGHT_TO_LEFT";
    		case AUTO: return "AUTO";
    		default: return "Unknown DocumentDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DocumentDirection value.
    /// </summary>
    public static String toString(/*DocumentDirection*/int documentDirection)
    {
    	switch (documentDirection)
    	{
    		case LEFT_TO_RIGHT: return "LeftToRight";
    		case RIGHT_TO_LEFT: return "RightToLeft";
    		case AUTO: return "Auto";
    		default: return "Unknown DocumentDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DocumentDirection name.
    /// </summary>
    public static /*DocumentDirection*/int fromName(String documentDirectionName)
    {
    	if ("LEFT_TO_RIGHT".equals(documentDirectionName)) return LEFT_TO_RIGHT;
    	if ("RIGHT_TO_LEFT".equals(documentDirectionName)) return RIGHT_TO_LEFT;
    	if ("AUTO".equals(documentDirectionName)) return AUTO;
    	throw new IllegalArgumentException("Unknown DocumentDirection name.");
    }
    
    /// <summary>
    /// Returns array of DocumentDirection values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEFT_TO_RIGHT,
    		RIGHT_TO_LEFT,
    		AUTO,
    	};
    }

    public static final int length = 3;
}

