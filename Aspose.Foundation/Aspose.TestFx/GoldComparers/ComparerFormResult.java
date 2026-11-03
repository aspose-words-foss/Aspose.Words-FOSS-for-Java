// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/07/2010 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// An exit code returned from the comparer form external process.
/// </summary>
public /*enum*/ final class ComparerFormResult
{
    private ComparerFormResult(){}
    
    /// <summary>
    /// Invalid value. To satisfy FxCop.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// The user has pressed the Accept button.
    /// The new gold needs to be accepted.
    /// </summary>
    public static final int ACCEPT = 101;
    /// <summary>
    /// The user has pressed the Skip button or canceled the dialog box.
    /// The difference exception needs to be thrown to indicate the test has failed.
    /// </summary>
    public static final int SKIP_AND_THROW = 102;
    /// <summary>
    /// The user has pressed the Ignore button.
    /// The difference exception should not be thrown, but a warning needs to be logged.
    /// </summary>
    public static final int SKIP_AND_LOG = 103;
    public static final int UNKNOWN_ERROR = 999;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ComparerFormResult value.
    /// </summary>
    public static String getName(/*ComparerFormResult*/int comparerFormResult)
    {
    	switch (comparerFormResult)
    	{
    		case NONE: return "NONE";
    		case ACCEPT: return "ACCEPT";
    		case SKIP_AND_THROW: return "SKIP_AND_THROW";
    		case SKIP_AND_LOG: return "SKIP_AND_LOG";
    		case UNKNOWN_ERROR: return "UNKNOWN_ERROR";
    		default: return "Unknown ComparerFormResult value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ComparerFormResult value.
    /// </summary>
    public static String toString(/*ComparerFormResult*/int comparerFormResult)
    {
    	switch (comparerFormResult)
    	{
    		case NONE: return "None";
    		case ACCEPT: return "Accept";
    		case SKIP_AND_THROW: return "SkipAndThrow";
    		case SKIP_AND_LOG: return "SkipAndLog";
    		case UNKNOWN_ERROR: return "UnknownError";
    		default: return "Unknown ComparerFormResult value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ComparerFormResult name.
    /// </summary>
    public static /*ComparerFormResult*/int fromName(String comparerFormResultName)
    {
    	if ("NONE".equals(comparerFormResultName)) return NONE;
    	if ("ACCEPT".equals(comparerFormResultName)) return ACCEPT;
    	if ("SKIP_AND_THROW".equals(comparerFormResultName)) return SKIP_AND_THROW;
    	if ("SKIP_AND_LOG".equals(comparerFormResultName)) return SKIP_AND_LOG;
    	if ("UNKNOWN_ERROR".equals(comparerFormResultName)) return UNKNOWN_ERROR;
    	throw new IllegalArgumentException("Unknown ComparerFormResult name.");
    }
    
    /// <summary>
    /// Returns array of ComparerFormResult values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		ACCEPT,
    		SKIP_AND_THROW,
    		SKIP_AND_LOG,
    		UNKNOWN_ERROR,
    	};
    }

    public static final int length = 5;
}

