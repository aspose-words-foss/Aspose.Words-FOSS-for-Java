// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies direction for replace operations.
/// </summary>
public /*enum*/ final class FindReplaceDirection
{
    private FindReplaceDirection(){}
    
    /// <summary>
    /// Matched items are replaced from first to last.
    /// </summary>
    public static final int FORWARD = 0;

    /// <summary>
    /// Matched items are replaced from last back to first.
    /// </summary>
    public static final int BACKWARD = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FindReplaceDirection value.
    /// </summary>
    public static String getName(/*FindReplaceDirection*/int findReplaceDirection)
    {
    	switch (findReplaceDirection)
    	{
    		case FORWARD: return "FORWARD";
    		case BACKWARD: return "BACKWARD";
    		default: return "Unknown FindReplaceDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FindReplaceDirection value.
    /// </summary>
    public static String toString(/*FindReplaceDirection*/int findReplaceDirection)
    {
    	switch (findReplaceDirection)
    	{
    		case FORWARD: return "Forward";
    		case BACKWARD: return "Backward";
    		default: return "Unknown FindReplaceDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FindReplaceDirection name.
    /// </summary>
    public static /*FindReplaceDirection*/int fromName(String findReplaceDirectionName)
    {
    	if ("FORWARD".equals(findReplaceDirectionName)) return FORWARD;
    	if ("BACKWARD".equals(findReplaceDirectionName)) return BACKWARD;
    	throw new IllegalArgumentException("Unknown FindReplaceDirection name.");
    }
    
    /// <summary>
    /// Returns array of FindReplaceDirection values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FORWARD,
    		BACKWARD,
    	};
    }

    public static final int length = 2;
}

