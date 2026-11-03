// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/07/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies whether a certain field update should be performed, postponed, cancelled or rejected.
/// </summary>
/*enum*/ final class FieldUpdateStrategy
{
    private FieldUpdateStrategy(){}
    
    /// <summary>
    /// Specifies that a certain field update should be performed immediately.
    /// </summary>
    public static final int UPDATE = 0;
    /// <summary>
    /// Specifies that a certain field update should be postponed on current stage.
    /// </summary>
    public static final int DEFER = 1;
    /// <summary>
    /// Specifies that a certain field update should be cancelled on current stage.
    /// </summary>
    public static final int SKIP = 2;
    /// <summary>
    /// Specifies that a certain field update should be rejected.
    /// </summary>
    public static final int REJECT = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldUpdateStrategy value.
    /// </summary>
    public static String getName(/*FieldUpdateStrategy*/int fieldUpdateStrategy)
    {
    	switch (fieldUpdateStrategy)
    	{
    		case UPDATE: return "UPDATE";
    		case DEFER: return "DEFER";
    		case SKIP: return "SKIP";
    		case REJECT: return "REJECT";
    		default: return "Unknown FieldUpdateStrategy value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldUpdateStrategy value.
    /// </summary>
    public static String toString(/*FieldUpdateStrategy*/int fieldUpdateStrategy)
    {
    	switch (fieldUpdateStrategy)
    	{
    		case UPDATE: return "Update";
    		case DEFER: return "Defer";
    		case SKIP: return "Skip";
    		case REJECT: return "Reject";
    		default: return "Unknown FieldUpdateStrategy value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FieldUpdateStrategy name.
    /// </summary>
    public static /*FieldUpdateStrategy*/int fromName(String fieldUpdateStrategyName)
    {
    	if ("UPDATE".equals(fieldUpdateStrategyName)) return UPDATE;
    	if ("DEFER".equals(fieldUpdateStrategyName)) return DEFER;
    	if ("SKIP".equals(fieldUpdateStrategyName)) return SKIP;
    	if ("REJECT".equals(fieldUpdateStrategyName)) return REJECT;
    	throw new IllegalArgumentException("Unknown FieldUpdateStrategy name.");
    }
    
    /// <summary>
    /// Returns array of FieldUpdateStrategy values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UPDATE,
    		DEFER,
    		SKIP,
    		REJECT,
    	};
    }

    public static final int length = 4;
}

