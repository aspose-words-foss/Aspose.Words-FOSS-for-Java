// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/03/2011 by Denis Darkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies how to represent subtraction on both sides of a line-wrapping break, 
/// when the <see cref="MathProperties.BreakOnBinary"/> option is set to <see cref="MathBreakOnBinary.Repeat"/>. 
/// The first character represents the sign at the end of the line with the
/// break; the second represents the sign at the start of the wrapped line
/// </summary>
/*enum*/ final class MathBreakOnBinarySubtraction
{
    private MathBreakOnBinarySubtraction(){}
    
    /// <summary>
    /// Repetition of subtraction sign after a line-wrapping break is minus on the first and second lines.
    /// </summary>
    public static final int MINUS_MINUS = 0;

    /// <summary>
    /// Repetition of subtraction sign after a line-wrapping break is plus on the first line and minus on the second
    /// line.
    /// </summary>
    public static final int PLUS_MINUS = 1;

    /// <summary>
    /// Repetition of subtraction sign after a line-wrapping break is minus on the first line and plus on the second line.
    /// </summary>
    public static final int MINUS_PLUS = 2;
    
    /// <summary>
    /// Default os <see cref="MinusMinus"/>
    /// </summary>
    public static final int DEFAULT = MINUS_MINUS;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MathBreakOnBinarySubtraction value.
    /// </summary>
    public static String getName(/*MathBreakOnBinarySubtraction*/int mathBreakOnBinarySubtraction)
    {
    	switch (mathBreakOnBinarySubtraction)
    	{
    		case MINUS_MINUS:
    		/*case DEFAULT:*/
    			return "MINUS_MINUS" + " | " + "DEFAULT";
    		case PLUS_MINUS: return "PLUS_MINUS";
    		case MINUS_PLUS: return "MINUS_PLUS";
    		default: return "Unknown MathBreakOnBinarySubtraction value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MathBreakOnBinarySubtraction value.
    /// </summary>
    public static String toString(/*MathBreakOnBinarySubtraction*/int mathBreakOnBinarySubtraction)
    {
    	switch (mathBreakOnBinarySubtraction)
    	{
    		case MINUS_MINUS:
    		/*case DEFAULT:*/
    			return "MinusMinus" + " | " + "Default";
    		case PLUS_MINUS: return "PlusMinus";
    		case MINUS_PLUS: return "MinusPlus";
    		default: return "Unknown MathBreakOnBinarySubtraction value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MathBreakOnBinarySubtraction name.
    /// </summary>
    public static /*MathBreakOnBinarySubtraction*/int fromName(String mathBreakOnBinarySubtractionName)
    {
    	if ("MINUS_MINUS".equals(mathBreakOnBinarySubtractionName)) return MINUS_MINUS;
    	if ("PLUS_MINUS".equals(mathBreakOnBinarySubtractionName)) return PLUS_MINUS;
    	if ("MINUS_PLUS".equals(mathBreakOnBinarySubtractionName)) return MINUS_PLUS;
    	if ("DEFAULT".equals(mathBreakOnBinarySubtractionName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown MathBreakOnBinarySubtraction name.");
    }
    
    /// <summary>
    /// Returns array of MathBreakOnBinarySubtraction values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		MINUS_MINUS,
    		PLUS_MINUS,
    		MINUS_PLUS,
    		DEFAULT,
    	};
    }

    public static final int length = 4;
}

