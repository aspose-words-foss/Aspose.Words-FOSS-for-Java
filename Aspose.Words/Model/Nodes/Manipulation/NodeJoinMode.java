// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/07/2012 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class NodeJoinMode
{
    private NodeJoinMode(){}
    
    public static final int DONT_JOIN = 0;
    public static final int JOIN_TO_PREVIOUS_SIBLING = 1;
    public static final int JOIN_TO_NEXT_SIBLING = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer NodeJoinMode value.
    /// </summary>
    public static String getName(/*NodeJoinMode*/int nodeJoinMode)
    {
    	switch (nodeJoinMode)
    	{
    		case DONT_JOIN: return "DONT_JOIN";
    		case JOIN_TO_PREVIOUS_SIBLING: return "JOIN_TO_PREVIOUS_SIBLING";
    		case JOIN_TO_NEXT_SIBLING: return "JOIN_TO_NEXT_SIBLING";
    		default: return "Unknown NodeJoinMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NodeJoinMode value.
    /// </summary>
    public static String toString(/*NodeJoinMode*/int nodeJoinMode)
    {
    	switch (nodeJoinMode)
    	{
    		case DONT_JOIN: return "DontJoin";
    		case JOIN_TO_PREVIOUS_SIBLING: return "JoinToPreviousSibling";
    		case JOIN_TO_NEXT_SIBLING: return "JoinToNextSibling";
    		default: return "Unknown NodeJoinMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by NodeJoinMode name.
    /// </summary>
    public static /*NodeJoinMode*/int fromName(String nodeJoinModeName)
    {
    	if ("DONT_JOIN".equals(nodeJoinModeName)) return DONT_JOIN;
    	if ("JOIN_TO_PREVIOUS_SIBLING".equals(nodeJoinModeName)) return JOIN_TO_PREVIOUS_SIBLING;
    	if ("JOIN_TO_NEXT_SIBLING".equals(nodeJoinModeName)) return JOIN_TO_NEXT_SIBLING;
    	throw new IllegalArgumentException("Unknown NodeJoinMode name.");
    }
    
    /// <summary>
    /// Returns array of NodeJoinMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DONT_JOIN,
    		JOIN_TO_PREVIOUS_SIBLING,
    		JOIN_TO_NEXT_SIBLING,
    	};
    }

    public static final int length = 3;
}

