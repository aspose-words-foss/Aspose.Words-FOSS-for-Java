// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/10/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies one of possible movements of <see cref="DocumentPosition"/>.
/// </summary>
/*enum*/ final class DocumentPositionMovement
{
    private DocumentPositionMovement(){}
    
    /// <summary>
    /// No movement done.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Moved inside a node.
    /// </summary>
    public static final int INSIDE = 1;
    /// <summary>
    /// Moved to the start or end of a node.
    /// </summary>
    public static final int START_END = 2;
    /// <summary>
    /// Moved to a sibling of a node.
    /// </summary>
    public static final int SIBLING = 3;
    /// <summary>
    /// Moved to the parent of a node.
    /// </summary>
    public static final int ABOVE = 4;
    /// <summary>
    /// Moved to a child of a node.
    /// </summary>
    public static final int BELOW = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DocumentPositionMovement value.
    /// </summary>
    public static String getName(/*DocumentPositionMovement*/int documentPositionMovement)
    {
    	switch (documentPositionMovement)
    	{
    		case NONE: return "NONE";
    		case INSIDE: return "INSIDE";
    		case START_END: return "START_END";
    		case SIBLING: return "SIBLING";
    		case ABOVE: return "ABOVE";
    		case BELOW: return "BELOW";
    		default: return "Unknown DocumentPositionMovement value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DocumentPositionMovement value.
    /// </summary>
    public static String toString(/*DocumentPositionMovement*/int documentPositionMovement)
    {
    	switch (documentPositionMovement)
    	{
    		case NONE: return "None";
    		case INSIDE: return "Inside";
    		case START_END: return "StartEnd";
    		case SIBLING: return "Sibling";
    		case ABOVE: return "Above";
    		case BELOW: return "Below";
    		default: return "Unknown DocumentPositionMovement value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DocumentPositionMovement name.
    /// </summary>
    public static /*DocumentPositionMovement*/int fromName(String documentPositionMovementName)
    {
    	if ("NONE".equals(documentPositionMovementName)) return NONE;
    	if ("INSIDE".equals(documentPositionMovementName)) return INSIDE;
    	if ("START_END".equals(documentPositionMovementName)) return START_END;
    	if ("SIBLING".equals(documentPositionMovementName)) return SIBLING;
    	if ("ABOVE".equals(documentPositionMovementName)) return ABOVE;
    	if ("BELOW".equals(documentPositionMovementName)) return BELOW;
    	throw new IllegalArgumentException("Unknown DocumentPositionMovement name.");
    }
    
    /// <summary>
    /// Returns array of DocumentPositionMovement values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		INSIDE,
    		START_END,
    		SIBLING,
    		ABOVE,
    		BELOW,
    	};
    }

    public static final int length = 6;
}

