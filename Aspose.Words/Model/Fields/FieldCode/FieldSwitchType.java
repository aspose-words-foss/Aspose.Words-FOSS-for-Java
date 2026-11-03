// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/08/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a field switch type.
/// </summary>
/*enum*/ final class FieldSwitchType
{
    private FieldSwitchType(){}
    
    /// <summary>
    /// The switch is not recognized as a valid field specific switch for the field being parsed.
    /// </summary>
    public static final int UNKNOWN = 0;
    /// <summary>
    /// The switch is a simple flag that has no argument.
    /// </summary>
    public static final int FLAG = 1;
    /// <summary>
    /// A switch argument is expected to follow the switch.
    /// </summary>
    public static final int HAS_ARGUMENT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldSwitchType value.
    /// </summary>
    public static String getName(/*FieldSwitchType*/int fieldSwitchType)
    {
    	switch (fieldSwitchType)
    	{
    		case UNKNOWN: return "UNKNOWN";
    		case FLAG: return "FLAG";
    		case HAS_ARGUMENT: return "HAS_ARGUMENT";
    		default: return "Unknown FieldSwitchType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldSwitchType value.
    /// </summary>
    public static String toString(/*FieldSwitchType*/int fieldSwitchType)
    {
    	switch (fieldSwitchType)
    	{
    		case UNKNOWN: return "Unknown";
    		case FLAG: return "Flag";
    		case HAS_ARGUMENT: return "HasArgument";
    		default: return "Unknown FieldSwitchType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FieldSwitchType name.
    /// </summary>
    public static /*FieldSwitchType*/int fromName(String fieldSwitchTypeName)
    {
    	if ("UNKNOWN".equals(fieldSwitchTypeName)) return UNKNOWN;
    	if ("FLAG".equals(fieldSwitchTypeName)) return FLAG;
    	if ("HAS_ARGUMENT".equals(fieldSwitchTypeName)) return HAS_ARGUMENT;
    	throw new IllegalArgumentException("Unknown FieldSwitchType name.");
    }
    
    /// <summary>
    /// Returns array of FieldSwitchType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNKNOWN,
    		FLAG,
    		HAS_ARGUMENT,
    	};
    }

    public static final int length = 3;
}

