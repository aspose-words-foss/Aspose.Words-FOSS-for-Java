// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/12/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies modes for attribute collection states.
/// </summary>
/*enum*/ final class InternState
{
    private InternState(){}
    
    /// <summary>
    /// Collection is in normal state.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// Collection is being referred by <see cref="InternState.Interned" /> collections for actual attribute values.
    /// </summary>
    public static final int POOLED = 1;

    /// <summary>
    /// Collection is empty itself and refers to <see cref="InternState.Pooled" /> collection for actual attribute values.
    /// </summary>
    public static final int INTERNED = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer InternState value.
    /// </summary>
    public static String getName(/*InternState*/int internState)
    {
    	switch (internState)
    	{
    		case NONE: return "NONE";
    		case POOLED: return "POOLED";
    		case INTERNED: return "INTERNED";
    		default: return "Unknown InternState value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer InternState value.
    /// </summary>
    public static String toString(/*InternState*/int internState)
    {
    	switch (internState)
    	{
    		case NONE: return "None";
    		case POOLED: return "Pooled";
    		case INTERNED: return "Interned";
    		default: return "Unknown InternState value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by InternState name.
    /// </summary>
    public static /*InternState*/int fromName(String internStateName)
    {
    	if ("NONE".equals(internStateName)) return NONE;
    	if ("POOLED".equals(internStateName)) return POOLED;
    	if ("INTERNED".equals(internStateName)) return INTERNED;
    	throw new IllegalArgumentException("Unknown InternState name.");
    }
    
    /// <summary>
    /// Returns array of InternState values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		POOLED,
    		INTERNED,
    	};
    }

    public static final int length = 3;
}

