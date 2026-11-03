// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/08/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows the user to specify what happens to the current match during a replace operation.
/// </summary>
/// <seealso cref="IReplacingCallback"/>
/// <seealso cref="Range"/>
/// <seealso cref="Range.Replace(string, string, FindReplaceOptions)"/>
public /*enum*/ final class ReplaceAction
{
    private ReplaceAction(){}
    
    /// <summary>
    /// Replace the current match.
    /// </summary>
    public static final int REPLACE = 0;
    /// <summary>
    /// Skip the current match.
    /// </summary>
    public static final int SKIP = 1;
    /// <summary>
    /// Terminate the replace operation.
    /// </summary>
    public static final int STOP = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ReplaceAction value.
    /// </summary>
    public static String getName(/*ReplaceAction*/int replaceAction)
    {
    	switch (replaceAction)
    	{
    		case REPLACE: return "REPLACE";
    		case SKIP: return "SKIP";
    		case STOP: return "STOP";
    		default: return "Unknown ReplaceAction value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ReplaceAction value.
    /// </summary>
    public static String toString(/*ReplaceAction*/int replaceAction)
    {
    	switch (replaceAction)
    	{
    		case REPLACE: return "Replace";
    		case SKIP: return "Skip";
    		case STOP: return "Stop";
    		default: return "Unknown ReplaceAction value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ReplaceAction name.
    /// </summary>
    public static /*ReplaceAction*/int fromName(String replaceActionName)
    {
    	if ("REPLACE".equals(replaceActionName)) return REPLACE;
    	if ("SKIP".equals(replaceActionName)) return SKIP;
    	if ("STOP".equals(replaceActionName)) return STOP;
    	throw new IllegalArgumentException("Unknown ReplaceAction name.");
    }
    
    /// <summary>
    /// Returns array of ReplaceAction values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		REPLACE,
    		SKIP,
    		STOP,
    	};
    }

    public static final int length = 3;
}

