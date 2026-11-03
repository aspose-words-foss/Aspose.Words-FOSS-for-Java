// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/05/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the list type.
/// </summary>
/*enum*/ final class ListType
{
    private ListType(){}
    
    /// <summary>
    /// Specifies that this numbering definition defines a numbering format consisting of a
    /// single level only.
    ///
    /// Word 97 emitted simple or multilevel (not simple) lists.
    /// </summary>
    public static final int SINGLE_LEVEL = 0;
    /// <summary>
    /// Word 97 emitted simple or multilevel (not simple) lists.
    ///
    /// Specifies that this numbering definition defines a numbering format consisting of a multiple levels,
    /// each of the same type (bullets vs. level text).
    /// </summary>
    public static final int MULTI_LEVEL = 1;
    /// <summary>
    /// Specifies that this numbering definition defines a numbering format consisting of a multiple levels,
    /// each of a potentially different type (bullets vs. level text).
    ///
    /// Word 2000, Word 2002, and Word 2003 emit hybrid lists, which are essentially collections of simple
    /// lists. The main difference between Word 2000, Word 2002, and Word 2003 hybrid lists and Word 97
    /// multilevel lists is that each level of a hybrid list has a unique identifier.
    /// </summary>
    public static final int HYBRID_MULTI_LEVEL = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ListType value.
    /// </summary>
    public static String getName(/*ListType*/int listType)
    {
    	switch (listType)
    	{
    		case SINGLE_LEVEL: return "SINGLE_LEVEL";
    		case MULTI_LEVEL: return "MULTI_LEVEL";
    		case HYBRID_MULTI_LEVEL: return "HYBRID_MULTI_LEVEL";
    		default: return "Unknown ListType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ListType value.
    /// </summary>
    public static String toString(/*ListType*/int listType)
    {
    	switch (listType)
    	{
    		case SINGLE_LEVEL: return "SingleLevel";
    		case MULTI_LEVEL: return "MultiLevel";
    		case HYBRID_MULTI_LEVEL: return "HybridMultiLevel";
    		default: return "Unknown ListType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ListType name.
    /// </summary>
    public static /*ListType*/int fromName(String listTypeName)
    {
    	if ("SINGLE_LEVEL".equals(listTypeName)) return SINGLE_LEVEL;
    	if ("MULTI_LEVEL".equals(listTypeName)) return MULTI_LEVEL;
    	if ("HYBRID_MULTI_LEVEL".equals(listTypeName)) return HYBRID_MULTI_LEVEL;
    	throw new IllegalArgumentException("Unknown ListType name.");
    }
    
    /// <summary>
    /// Returns array of ListType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SINGLE_LEVEL,
    		MULTI_LEVEL,
    		HYBRID_MULTI_LEVEL,
    	};
    }

    public static final int length = 3;
}

