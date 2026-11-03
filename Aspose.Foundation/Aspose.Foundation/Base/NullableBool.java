// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/01/2020 by Ivan Lyagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a Boolean value that can be undefined.
/// </summary>
public /*enum*/ final class NullableBool /*: sbyte*/
{
    private NullableBool(){}
    
    /// <summary>
    /// Specifies an undefined value.
    /// </summary>
    public static final byte NOT_DEFINED = -1;

    /// <summary>
    /// Specifies the value of false.
    /// </summary>
    public static final byte FALSE = 0;

    /// <summary>
    /// Specifies the value of true.
    /// </summary>
    public static final byte TRUE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer NullableBool value.
    /// </summary>
    public static String getName(/*NullableBool*/byte nullableBool)
    {
    	if (NOT_DEFINED == nullableBool) return "NOT_DEFINED";
    	if (FALSE == nullableBool) return "FALSE";
    	if (TRUE == nullableBool) return "TRUE";
    	return "Unknown NullableBool value.";
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NullableBool value.
    /// </summary>
    public static String toString(/*NullableBool*/byte nullableBool)
    {
    	if (NOT_DEFINED == nullableBool) return "NotDefined";
    	if (FALSE == nullableBool) return "False";
    	if (TRUE == nullableBool) return "True";
    	return "Unknown NullableBool value.";
    }
    
    /// <summary>
    /// Returns integer representation by NullableBool name.
    /// </summary>
    public static /*NullableBool*/byte fromName(String nullableBoolName)
    {
    	if ("NOT_DEFINED".equals(nullableBoolName)) return NOT_DEFINED;
    	if ("FALSE".equals(nullableBoolName)) return FALSE;
    	if ("TRUE".equals(nullableBoolName)) return TRUE;
    	throw new IllegalArgumentException("Unknown NullableBool name.");
    }
    
    /// <summary>
    /// Returns array of NullableBool values.
    /// </summary>
    public static byte[] getValues()
    {
    	return new byte[]
    	{
    		NOT_DEFINED,
    		FALSE,
    		TRUE,
    	};
    }

    public static final int length = 3;
}

