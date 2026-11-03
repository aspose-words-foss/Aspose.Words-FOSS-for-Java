// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/05/2011 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Conditional formatting property collection type. 
/// </summary>
/// <remarks>
/// Table style may contain conditional formatting for certain cell/row set. This enumerates types of conditional formatting property collections.
/// </remarks>
/*enum*/ final class AttrCollectionType
{
    private AttrCollectionType(){}
    
    /// <summary>
    /// Character format collection.
    /// </summary>
    public static final int RUN_PR = 0;

    /// <summary>
    /// Paragraph format collection.
    /// </summary>
    public static final int PARA_PR = 1;

    /// <summary>
    /// Cell format collection.
    /// </summary>
    public static final int CELL_PR = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AttrCollectionType value.
    /// </summary>
    public static String getName(/*AttrCollectionType*/int attrCollectionType)
    {
    	switch (attrCollectionType)
    	{
    		case RUN_PR: return "RUN_PR";
    		case PARA_PR: return "PARA_PR";
    		case CELL_PR: return "CELL_PR";
    		default: return "Unknown AttrCollectionType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AttrCollectionType value.
    /// </summary>
    public static String toString(/*AttrCollectionType*/int attrCollectionType)
    {
    	switch (attrCollectionType)
    	{
    		case RUN_PR: return "RunPr";
    		case PARA_PR: return "ParaPr";
    		case CELL_PR: return "CellPr";
    		default: return "Unknown AttrCollectionType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AttrCollectionType name.
    /// </summary>
    public static /*AttrCollectionType*/int fromName(String attrCollectionTypeName)
    {
    	if ("RUN_PR".equals(attrCollectionTypeName)) return RUN_PR;
    	if ("PARA_PR".equals(attrCollectionTypeName)) return PARA_PR;
    	if ("CELL_PR".equals(attrCollectionTypeName)) return CELL_PR;
    	throw new IllegalArgumentException("Unknown AttrCollectionType name.");
    }
    
    /// <summary>
    /// Returns array of AttrCollectionType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		RUN_PR,
    		PARA_PR,
    		CELL_PR,
    	};
    }

    public static final int length = 3;
}

