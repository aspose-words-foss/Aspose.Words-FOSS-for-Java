// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/11/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// The type of move revision mark.
/// </summary>
/*enum*/ final class MoveRevisionType
{
    private MoveRevisionType(){}
    
    public static final int MOVE_FROM = 0;
    public static final int MOVE_TO = 1;
    public static final int NONE = 2;    // TODO Consider using this as default and move to 1st position in the list

    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MoveRevisionType value.
    /// </summary>
    public static String getName(/*MoveRevisionType*/int moveRevisionType)
    {
    	switch (moveRevisionType)
    	{
    		case MOVE_FROM: return "MOVE_FROM";
    		case MOVE_TO: return "MOVE_TO";
    		case NONE: return "NONE";
    		default: return "Unknown MoveRevisionType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MoveRevisionType value.
    /// </summary>
    public static String toString(/*MoveRevisionType*/int moveRevisionType)
    {
    	switch (moveRevisionType)
    	{
    		case MOVE_FROM: return "MoveFrom";
    		case MOVE_TO: return "MoveTo";
    		case NONE: return "None";
    		default: return "Unknown MoveRevisionType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MoveRevisionType name.
    /// </summary>
    public static /*MoveRevisionType*/int fromName(String moveRevisionTypeName)
    {
    	if ("MOVE_FROM".equals(moveRevisionTypeName)) return MOVE_FROM;
    	if ("MOVE_TO".equals(moveRevisionTypeName)) return MOVE_TO;
    	if ("NONE".equals(moveRevisionTypeName)) return NONE;
    	throw new IllegalArgumentException("Unknown MoveRevisionType name.");
    }
    
    /// <summary>
    /// Returns array of MoveRevisionType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		MOVE_FROM,
    		MOVE_TO,
    		NONE,
    	};
    }

    public static final int length = 3;
}

