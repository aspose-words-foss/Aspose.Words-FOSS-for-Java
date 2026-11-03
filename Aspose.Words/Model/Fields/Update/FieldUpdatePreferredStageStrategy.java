// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies whether a certain field update should be performed on main stage, deferred stage or skipped.
/// </summary>
/*enum*/ final class FieldUpdatePreferredStageStrategy
{
    private FieldUpdatePreferredStageStrategy(){}
    
    /// <summary>
    /// Specifies that a certain field update should be performed on main stage.
    /// </summary>
    public static final int MAIN_LOOP = 0;
    /// <summary>
    /// Specifies that a certain field update should be skipped.
    /// </summary>
    public static final int SKIP = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldUpdatePreferredStageStrategy value.
    /// </summary>
    public static String getName(/*FieldUpdatePreferredStageStrategy*/int fieldUpdatePreferredStageStrategy)
    {
    	switch (fieldUpdatePreferredStageStrategy)
    	{
    		case MAIN_LOOP: return "MAIN_LOOP";
    		case SKIP: return "SKIP";
    		default: return "Unknown FieldUpdatePreferredStageStrategy value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldUpdatePreferredStageStrategy value.
    /// </summary>
    public static String toString(/*FieldUpdatePreferredStageStrategy*/int fieldUpdatePreferredStageStrategy)
    {
    	switch (fieldUpdatePreferredStageStrategy)
    	{
    		case MAIN_LOOP: return "MainLoop";
    		case SKIP: return "Skip";
    		default: return "Unknown FieldUpdatePreferredStageStrategy value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FieldUpdatePreferredStageStrategy name.
    /// </summary>
    public static /*FieldUpdatePreferredStageStrategy*/int fromName(String fieldUpdatePreferredStageStrategyName)
    {
    	if ("MAIN_LOOP".equals(fieldUpdatePreferredStageStrategyName)) return MAIN_LOOP;
    	if ("SKIP".equals(fieldUpdatePreferredStageStrategyName)) return SKIP;
    	throw new IllegalArgumentException("Unknown FieldUpdatePreferredStageStrategy name.");
    }
    
    /// <summary>
    /// Returns array of FieldUpdatePreferredStageStrategy values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		MAIN_LOOP,
    		SKIP,
    	};
    }

    public static final int length = 2;
}

