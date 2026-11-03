// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/01/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows the visitor to control the enumeration of nodes.
/// </summary>
public /*enum*/ final class VisitorAction
{
    private VisitorAction(){}
    
    /// <summary>
    /// The visitor requests the enumeration to continue.
    /// </summary>
    public static final int CONTINUE = 0;
    /// <summary>
    /// The visitor requests to skip the current node and continue enumeration.
    /// </summary>
    public static final int SKIP_THIS_NODE = 1;
    /// <summary>
    /// The visitor requests the enumeration of nodes to stop.
    /// </summary>
    public static final int STOP = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer VisitorAction value.
    /// </summary>
    public static String getName(/*VisitorAction*/int visitorAction)
    {
    	switch (visitorAction)
    	{
    		case CONTINUE: return "CONTINUE";
    		case SKIP_THIS_NODE: return "SKIP_THIS_NODE";
    		case STOP: return "STOP";
    		default: return "Unknown VisitorAction value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer VisitorAction value.
    /// </summary>
    public static String toString(/*VisitorAction*/int visitorAction)
    {
    	switch (visitorAction)
    	{
    		case CONTINUE: return "Continue";
    		case SKIP_THIS_NODE: return "SkipThisNode";
    		case STOP: return "Stop";
    		default: return "Unknown VisitorAction value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by VisitorAction name.
    /// </summary>
    public static /*VisitorAction*/int fromName(String visitorActionName)
    {
    	if ("CONTINUE".equals(visitorActionName)) return CONTINUE;
    	if ("SKIP_THIS_NODE".equals(visitorActionName)) return SKIP_THIS_NODE;
    	if ("STOP".equals(visitorActionName)) return STOP;
    	throw new IllegalArgumentException("Unknown VisitorAction name.");
    }
    
    /// <summary>
    /// Returns array of VisitorAction values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CONTINUE,
    		SKIP_THIS_NODE,
    		STOP,
    	};
    }

    public static final int length = 3;
}

