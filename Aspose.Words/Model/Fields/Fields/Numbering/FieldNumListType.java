// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/12/2012 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a predefined LISTNUM or AUTONUM field's list type.
/// </summary>
/*enum*/ final class FieldNumListType
{
    private FieldNumListType(){}
    
    /// <summary>
    /// Specifies a non-predefined LISTNUM or AUTONUM field's list. Default value.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Specifies a list used to maintain LISTNUM field's NumberDefault list functionality
    /// and AUTONUM field functionality.
    /// </summary>
    public static final int NUMBER = 1;
    /// <summary>
    /// Specifies a list used to maintain LISTNUM field's OutlineDefault list functionality
    /// and AUTONUMOUT field functionality.
    /// </summary>
    public static final int OUTLINE = 2;
    /// <summary>
    /// Specifies a list used to maintain LISTNUM field's LegalDefault list functionality
    /// and AUTONUMLGL field functionality.
    /// </summary>
    public static final int LEGAL = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldNumListType value.
    /// </summary>
    public static String getName(/*FieldNumListType*/int fieldNumListType)
    {
    	switch (fieldNumListType)
    	{
    		case NONE: return "NONE";
    		case NUMBER: return "NUMBER";
    		case OUTLINE: return "OUTLINE";
    		case LEGAL: return "LEGAL";
    		default: return "Unknown FieldNumListType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldNumListType value.
    /// </summary>
    public static String toString(/*FieldNumListType*/int fieldNumListType)
    {
    	switch (fieldNumListType)
    	{
    		case NONE: return "None";
    		case NUMBER: return "Number";
    		case OUTLINE: return "Outline";
    		case LEGAL: return "Legal";
    		default: return "Unknown FieldNumListType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FieldNumListType name.
    /// </summary>
    public static /*FieldNumListType*/int fromName(String fieldNumListTypeName)
    {
    	if ("NONE".equals(fieldNumListTypeName)) return NONE;
    	if ("NUMBER".equals(fieldNumListTypeName)) return NUMBER;
    	if ("OUTLINE".equals(fieldNumListTypeName)) return OUTLINE;
    	if ("LEGAL".equals(fieldNumListTypeName)) return LEGAL;
    	throw new IllegalArgumentException("Unknown FieldNumListType name.");
    }
    
    /// <summary>
    /// Returns array of FieldNumListType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		NUMBER,
    		OUTLINE,
    		LEGAL,
    	};
    }

    public static final int length = 4;
}

