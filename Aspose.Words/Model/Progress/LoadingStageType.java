// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/09/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Document loading stage types.
/// </summary>
/*enum*/ final class LoadingStageType
{
    private LoadingStageType(){}
    
    /// <summary>
    /// Document content reading.
    /// </summary>
    public static final int READING = 0x400;

    /// <summary>
    /// Post processing after reading.
    /// </summary>
    public static final int PROCESSING = 0x40000;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer LoadingStageType value.
    /// </summary>
    public static String getName(/*LoadingStageType*/int loadingStageType)
    {
    	switch (loadingStageType)
    	{
    		case READING: return "READING";
    		case PROCESSING: return "PROCESSING";
    		default: return "Unknown LoadingStageType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer LoadingStageType value.
    /// </summary>
    public static String toString(/*LoadingStageType*/int loadingStageType)
    {
    	switch (loadingStageType)
    	{
    		case READING: return "Reading";
    		case PROCESSING: return "Processing";
    		default: return "Unknown LoadingStageType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by LoadingStageType name.
    /// </summary>
    public static /*LoadingStageType*/int fromName(String loadingStageTypeName)
    {
    	if ("READING".equals(loadingStageTypeName)) return READING;
    	if ("PROCESSING".equals(loadingStageTypeName)) return PROCESSING;
    	throw new IllegalArgumentException("Unknown LoadingStageType name.");
    }
    
    /// <summary>
    /// Returns array of LoadingStageType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		READING,
    		PROCESSING,
    	};
    }

    public static final int length = 2;
}

