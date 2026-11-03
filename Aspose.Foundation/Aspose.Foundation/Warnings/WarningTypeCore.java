// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/10/2011 by Alexey Noskov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enum has the same values as Aspose.Words.WarningType excepting categories. It is used for renders.
/// </summary>
public /*enum*/ final class WarningTypeCore
{
    private WarningTypeCore(){}
    
    public static final int DATA_LOSS = 0;
    public static final int MAJOR_FORMATTING_LOSS = 1;
    public static final int MINOR_FORMATTING_LOSS = 2;
    public static final int FONT_SUBSTITUTION = 3;
    public static final int UNEXPECTED_CONTENT = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer WarningTypeCore value.
    /// </summary>
    public static String getName(/*WarningTypeCore*/int warningTypeCore)
    {
    	switch (warningTypeCore)
    	{
    		case DATA_LOSS: return "DATA_LOSS";
    		case MAJOR_FORMATTING_LOSS: return "MAJOR_FORMATTING_LOSS";
    		case MINOR_FORMATTING_LOSS: return "MINOR_FORMATTING_LOSS";
    		case FONT_SUBSTITUTION: return "FONT_SUBSTITUTION";
    		case UNEXPECTED_CONTENT: return "UNEXPECTED_CONTENT";
    		default: return "Unknown WarningTypeCore value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer WarningTypeCore value.
    /// </summary>
    public static String toString(/*WarningTypeCore*/int warningTypeCore)
    {
    	switch (warningTypeCore)
    	{
    		case DATA_LOSS: return "DataLoss";
    		case MAJOR_FORMATTING_LOSS: return "MajorFormattingLoss";
    		case MINOR_FORMATTING_LOSS: return "MinorFormattingLoss";
    		case FONT_SUBSTITUTION: return "FontSubstitution";
    		case UNEXPECTED_CONTENT: return "UnexpectedContent";
    		default: return "Unknown WarningTypeCore value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by WarningTypeCore name.
    /// </summary>
    public static /*WarningTypeCore*/int fromName(String warningTypeCoreName)
    {
    	if ("DATA_LOSS".equals(warningTypeCoreName)) return DATA_LOSS;
    	if ("MAJOR_FORMATTING_LOSS".equals(warningTypeCoreName)) return MAJOR_FORMATTING_LOSS;
    	if ("MINOR_FORMATTING_LOSS".equals(warningTypeCoreName)) return MINOR_FORMATTING_LOSS;
    	if ("FONT_SUBSTITUTION".equals(warningTypeCoreName)) return FONT_SUBSTITUTION;
    	if ("UNEXPECTED_CONTENT".equals(warningTypeCoreName)) return UNEXPECTED_CONTENT;
    	throw new IllegalArgumentException("Unknown WarningTypeCore name.");
    }
    
    /// <summary>
    /// Returns array of WarningTypeCore values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DATA_LOSS,
    		MAJOR_FORMATTING_LOSS,
    		MINOR_FORMATTING_LOSS,
    		FONT_SUBSTITUTION,
    		UNEXPECTED_CONTENT,
    	};
    }

    public static final int length = 5;
}

