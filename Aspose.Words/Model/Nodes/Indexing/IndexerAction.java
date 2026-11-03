// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/07/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies values to customize node indexing.
/// </summary>
/*enum*/ final class IndexerAction
{
    private IndexerAction(){}
    
    /// <summary>
    /// No additional processing required.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// Node and its child should be completely ignored.
    /// </summary>
    public static final int IGNORE = 1;

    /// <summary>
    /// Node should be ignored but child should be processed.
    /// </summary>
    public static final int SKIP = 2;

    /// <summary>
    /// Node and its child should be collapsed node into single char.
    /// </summary>
    public static final int COLLAPSE = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer IndexerAction value.
    /// </summary>
    public static String getName(/*IndexerAction*/int indexerAction)
    {
    	switch (indexerAction)
    	{
    		case NONE: return "NONE";
    		case IGNORE: return "IGNORE";
    		case SKIP: return "SKIP";
    		case COLLAPSE: return "COLLAPSE";
    		default: return "Unknown IndexerAction value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer IndexerAction value.
    /// </summary>
    public static String toString(/*IndexerAction*/int indexerAction)
    {
    	switch (indexerAction)
    	{
    		case NONE: return "None";
    		case IGNORE: return "Ignore";
    		case SKIP: return "Skip";
    		case COLLAPSE: return "Collapse";
    		default: return "Unknown IndexerAction value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by IndexerAction name.
    /// </summary>
    public static /*IndexerAction*/int fromName(String indexerActionName)
    {
    	if ("NONE".equals(indexerActionName)) return NONE;
    	if ("IGNORE".equals(indexerActionName)) return IGNORE;
    	if ("SKIP".equals(indexerActionName)) return SKIP;
    	if ("COLLAPSE".equals(indexerActionName)) return COLLAPSE;
    	throw new IllegalArgumentException("Unknown IndexerAction name.");
    }
    
    /// <summary>
    /// Returns array of IndexerAction values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		IGNORE,
    		SKIP,
    		COLLAPSE,
    	};
    }

    public static final int length = 4;
}

