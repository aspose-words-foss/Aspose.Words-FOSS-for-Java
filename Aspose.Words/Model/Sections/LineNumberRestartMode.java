// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Determines when automatic line numbering restarts.
/// </summary>
/// <seealso cref="PageSetup"/>
/// <seealso cref="PageSetup.LineNumberRestartMode"/>
public /*enum*/ final class LineNumberRestartMode
{
    private LineNumberRestartMode(){}
    
    /// <summary>
    /// Line numbering restarts at the start of every page.
    /// </summary>
    public static final int RESTART_PAGE = 0;
    /// <summary>
    /// Line numbering restarts at the section start.
    /// </summary>
    public static final int RESTART_SECTION = 1;
    /// <summary>
    /// Line numbering continuous from the previous section.
    /// </summary>
    public static final int CONTINUOUS = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer LineNumberRestartMode value.
    /// </summary>
    public static String getName(/*LineNumberRestartMode*/int lineNumberRestartMode)
    {
    	switch (lineNumberRestartMode)
    	{
    		case RESTART_PAGE: return "RESTART_PAGE";
    		case RESTART_SECTION: return "RESTART_SECTION";
    		case CONTINUOUS: return "CONTINUOUS";
    		default: return "Unknown LineNumberRestartMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer LineNumberRestartMode value.
    /// </summary>
    public static String toString(/*LineNumberRestartMode*/int lineNumberRestartMode)
    {
    	switch (lineNumberRestartMode)
    	{
    		case RESTART_PAGE: return "RestartPage";
    		case RESTART_SECTION: return "RestartSection";
    		case CONTINUOUS: return "Continuous";
    		default: return "Unknown LineNumberRestartMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by LineNumberRestartMode name.
    /// </summary>
    public static /*LineNumberRestartMode*/int fromName(String lineNumberRestartModeName)
    {
    	if ("RESTART_PAGE".equals(lineNumberRestartModeName)) return RESTART_PAGE;
    	if ("RESTART_SECTION".equals(lineNumberRestartModeName)) return RESTART_SECTION;
    	if ("CONTINUOUS".equals(lineNumberRestartModeName)) return CONTINUOUS;
    	throw new IllegalArgumentException("Unknown LineNumberRestartMode name.");
    }
    
    /// <summary>
    /// Returns array of LineNumberRestartMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		RESTART_PAGE,
    		RESTART_SECTION,
    		CONTINUOUS,
    	};
    }

    public static final int length = 3;
}

