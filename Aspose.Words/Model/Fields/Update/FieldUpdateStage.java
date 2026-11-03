// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/01/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a field update stage.
/// </summary>
/// <remarks>
/// The order of items in this enum should correspond to the order of update stages. Change it carefully.
/// </remarks>
/*enum*/ final class FieldUpdateStage
{
    private FieldUpdateStage(){}
    
    /// <summary>
    /// All fields are updated one by one on this stage.
    /// </summary>
    public static final int MAIN_LOOP = 0;
    /// <summary>
    /// REF-like postponed fields are updated on this stage.
    /// </summary>
    public static final int DEFERRED_UPDATE_REF = 1;
    /// <summary>
    /// FCL postponed fields are updated on this stage.
    /// </summary>
    public static final int DEFERRED_UPDATE_LAYOUT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldUpdateStage value.
    /// </summary>
    public static String getName(/*FieldUpdateStage*/int fieldUpdateStage)
    {
    	switch (fieldUpdateStage)
    	{
    		case MAIN_LOOP: return "MAIN_LOOP";
    		case DEFERRED_UPDATE_REF: return "DEFERRED_UPDATE_REF";
    		case DEFERRED_UPDATE_LAYOUT: return "DEFERRED_UPDATE_LAYOUT";
    		default: return "Unknown FieldUpdateStage value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldUpdateStage value.
    /// </summary>
    public static String toString(/*FieldUpdateStage*/int fieldUpdateStage)
    {
    	switch (fieldUpdateStage)
    	{
    		case MAIN_LOOP: return "MainLoop";
    		case DEFERRED_UPDATE_REF: return "DeferredUpdateRef";
    		case DEFERRED_UPDATE_LAYOUT: return "DeferredUpdateLayout";
    		default: return "Unknown FieldUpdateStage value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FieldUpdateStage name.
    /// </summary>
    public static /*FieldUpdateStage*/int fromName(String fieldUpdateStageName)
    {
    	if ("MAIN_LOOP".equals(fieldUpdateStageName)) return MAIN_LOOP;
    	if ("DEFERRED_UPDATE_REF".equals(fieldUpdateStageName)) return DEFERRED_UPDATE_REF;
    	if ("DEFERRED_UPDATE_LAYOUT".equals(fieldUpdateStageName)) return DEFERRED_UPDATE_LAYOUT;
    	throw new IllegalArgumentException("Unknown FieldUpdateStage name.");
    }
    
    /// <summary>
    /// Returns array of FieldUpdateStage values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		MAIN_LOOP,
    		DEFERRED_UPDATE_REF,
    		DEFERRED_UPDATE_LAYOUT,
    	};
    }

    public static final int length = 3;
}

