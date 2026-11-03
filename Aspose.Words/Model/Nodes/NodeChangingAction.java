// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of node change.
/// </summary>
/// <seealso cref="NodeChangingArgs"/>
/// <seealso cref="NodeChangingArgs.Action"/>
public /*enum*/ final class NodeChangingAction
{
    private NodeChangingAction(){}
    
    /// <summary>
    /// A node is being inserted in the tree.
    /// </summary>
    public static final int INSERT = 0;
    /// <summary>
    /// A node is being removed from the tree.
    /// </summary>
    public static final int REMOVE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer NodeChangingAction value.
    /// </summary>
    public static String getName(/*NodeChangingAction*/int nodeChangingAction)
    {
    	switch (nodeChangingAction)
    	{
    		case INSERT: return "INSERT";
    		case REMOVE: return "REMOVE";
    		default: return "Unknown NodeChangingAction value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NodeChangingAction value.
    /// </summary>
    public static String toString(/*NodeChangingAction*/int nodeChangingAction)
    {
    	switch (nodeChangingAction)
    	{
    		case INSERT: return "Insert";
    		case REMOVE: return "Remove";
    		default: return "Unknown NodeChangingAction value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by NodeChangingAction name.
    /// </summary>
    public static /*NodeChangingAction*/int fromName(String nodeChangingActionName)
    {
    	if ("INSERT".equals(nodeChangingActionName)) return INSERT;
    	if ("REMOVE".equals(nodeChangingActionName)) return REMOVE;
    	throw new IllegalArgumentException("Unknown NodeChangingAction name.");
    }
    
    /// <summary>
    /// Returns array of NodeChangingAction values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		INSERT,
    		REMOVE,
    	};
    }

    public static final int length = 2;
}

