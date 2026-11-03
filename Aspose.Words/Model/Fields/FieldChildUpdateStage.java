// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/07/2012 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies field's child fields update process stage.
/// </summary>
/*enum*/ final class FieldChildUpdateStage
{
    private FieldChildUpdateStage(){}
    
    /// <summary>
    /// Child fields which must be updated regardless to any conditions are being updated.
    /// </summary>
    public static final int PERMANENT = 0;
    /// <summary>
    /// Child fields which must be updated due to some conditions are being updated.
    /// </summary>
    public static final int CONDITIONAL = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldChildUpdateStage value.
    /// </summary>
    public static String getName(/*FieldChildUpdateStage*/int fieldChildUpdateStage)
    {
    	switch (fieldChildUpdateStage)
    	{
    		case PERMANENT: return "PERMANENT";
    		case CONDITIONAL: return "CONDITIONAL";
    		default: return "Unknown FieldChildUpdateStage value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldChildUpdateStage value.
    /// </summary>
    public static String toString(/*FieldChildUpdateStage*/int fieldChildUpdateStage)
    {
    	switch (fieldChildUpdateStage)
    	{
    		case PERMANENT: return "Permanent";
    		case CONDITIONAL: return "Conditional";
    		default: return "Unknown FieldChildUpdateStage value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FieldChildUpdateStage name.
    /// </summary>
    public static /*FieldChildUpdateStage*/int fromName(String fieldChildUpdateStageName)
    {
    	if ("PERMANENT".equals(fieldChildUpdateStageName)) return PERMANENT;
    	if ("CONDITIONAL".equals(fieldChildUpdateStageName)) return CONDITIONAL;
    	throw new IllegalArgumentException("Unknown FieldChildUpdateStage name.");
    }
    
    /// <summary>
    /// Returns array of FieldChildUpdateStage values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		PERMANENT,
    		CONDITIONAL,
    	};
    }

    public static final int length = 2;
}

