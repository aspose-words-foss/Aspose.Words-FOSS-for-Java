// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/12/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Document saving stage types.
/// </summary>
/*enum*/ final class SavingStageType
{
    private SavingStageType(){}
    
    /// <summary>
    /// Processing before saving.
    /// </summary>
    public static final int PROCESSING = 0x400;

    /// <summary>
    /// Document content saving.
    /// </summary>
    public static final int SAVING = 0x40000;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SavingStageType value.
    /// </summary>
    public static String getName(/*SavingStageType*/int savingStageType)
    {
    	switch (savingStageType)
    	{
    		case PROCESSING: return "PROCESSING";
    		case SAVING: return "SAVING";
    		default: return "Unknown SavingStageType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SavingStageType value.
    /// </summary>
    public static String toString(/*SavingStageType*/int savingStageType)
    {
    	switch (savingStageType)
    	{
    		case PROCESSING: return "Processing";
    		case SAVING: return "Saving";
    		default: return "Unknown SavingStageType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SavingStageType name.
    /// </summary>
    public static /*SavingStageType*/int fromName(String savingStageTypeName)
    {
    	if ("PROCESSING".equals(savingStageTypeName)) return PROCESSING;
    	if ("SAVING".equals(savingStageTypeName)) return SAVING;
    	throw new IllegalArgumentException("Unknown SavingStageType name.");
    }
    
    /// <summary>
    /// Returns array of SavingStageType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		PROCESSING,
    		SAVING,
    	};
    }

    public static final int length = 2;
}

