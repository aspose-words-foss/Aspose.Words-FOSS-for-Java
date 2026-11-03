// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/03/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Defines how to represent binary math operators with respect to a line-wrapping break. The line can wrap before 
/// the operator or after the operator; alternately, the operator can appear both at the end of the first line and the
/// beginning of the second.
/// </summary>
/*enum*/ final class MathBreakOnBinary
{
    private MathBreakOnBinary(){}
    
    /// <summary>
    /// When line-wrapping breaks occur on binary operators, 
    /// the binary operator appears before the break (at the end of the first line).
    /// </summary>
    public static final int BEFORE = 0x00;
    
    /// <summary>
    /// When line-wrapping breaks occur on binary operators, 
    /// the binary operator appears after the break (at the start of the next line).
    /// </summary>
    public static final int AFTER = 0x01;
    
    /// <summary>
    /// When line-wrapping breaks occur on binary operators, the binary operator appears on both sides of the break 
    /// (at the end of the first line and the start of the next line).
    /// </summary>
    public static final int REPEAT = 0x02;

    /// <summary>
    /// Default is <see cref="Before"/>
    /// </summary>
    public static final int DEFAULT = BEFORE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MathBreakOnBinary value.
    /// </summary>
    public static String getName(/*MathBreakOnBinary*/int mathBreakOnBinary)
    {
    	switch (mathBreakOnBinary)
    	{
    		case BEFORE:
    		/*case DEFAULT:*/
    			return "BEFORE" + " | " + "DEFAULT";
    		case AFTER: return "AFTER";
    		case REPEAT: return "REPEAT";
    		default: return "Unknown MathBreakOnBinary value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MathBreakOnBinary value.
    /// </summary>
    public static String toString(/*MathBreakOnBinary*/int mathBreakOnBinary)
    {
    	switch (mathBreakOnBinary)
    	{
    		case BEFORE:
    		/*case DEFAULT:*/
    			return "Before" + " | " + "Default";
    		case AFTER: return "After";
    		case REPEAT: return "Repeat";
    		default: return "Unknown MathBreakOnBinary value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MathBreakOnBinary name.
    /// </summary>
    public static /*MathBreakOnBinary*/int fromName(String mathBreakOnBinaryName)
    {
    	if ("BEFORE".equals(mathBreakOnBinaryName)) return BEFORE;
    	if ("AFTER".equals(mathBreakOnBinaryName)) return AFTER;
    	if ("REPEAT".equals(mathBreakOnBinaryName)) return REPEAT;
    	if ("DEFAULT".equals(mathBreakOnBinaryName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown MathBreakOnBinary name.");
    }
    
    /// <summary>
    /// Returns array of MathBreakOnBinary values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BEFORE,
    		AFTER,
    		REPEAT,
    		DEFAULT,
    	};
    }

    public static final int length = 4;
}

