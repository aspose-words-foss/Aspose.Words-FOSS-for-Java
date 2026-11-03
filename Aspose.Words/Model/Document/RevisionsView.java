// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/04/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows to specify whether to work with the original or revised version of a document.
/// </summary>
public /*enum*/ final class RevisionsView
{
    private RevisionsView(){}
    
    /// <summary>
    /// Specifies original version of a document.
    /// </summary>
    public static final int ORIGINAL = 0;

    /// <summary>
    /// Specifies revised version of a document.
    /// </summary>
    public static final int FINAL = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer RevisionsView value.
    /// </summary>
    public static String getName(/*RevisionsView*/int revisionsView)
    {
    	switch (revisionsView)
    	{
    		case ORIGINAL: return "ORIGINAL";
    		case FINAL: return "FINAL";
    		default: return "Unknown RevisionsView value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer RevisionsView value.
    /// </summary>
    public static String toString(/*RevisionsView*/int revisionsView)
    {
    	switch (revisionsView)
    	{
    		case ORIGINAL: return "Original";
    		case FINAL: return "Final";
    		default: return "Unknown RevisionsView value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by RevisionsView name.
    /// </summary>
    public static /*RevisionsView*/int fromName(String revisionsViewName)
    {
    	if ("ORIGINAL".equals(revisionsViewName)) return ORIGINAL;
    	if ("FINAL".equals(revisionsViewName)) return FINAL;
    	throw new IllegalArgumentException("Unknown RevisionsView name.");
    }
    
    /// <summary>
    /// Returns array of RevisionsView values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ORIGINAL,
    		FINAL,
    	};
    }

    public static final int length = 2;
}

