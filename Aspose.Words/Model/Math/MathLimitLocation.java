// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/03/2011 by Denis Darkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This element specifies the location of limits in n-ary operators. Limits can be either centered above and below
/// the n-ary operator, or positioned just to the right of the operator.
/// </summary>
/*enum*/ final class MathLimitLocation
{
    private MathLimitLocation(){}
    
    /// <summary>
    /// intLim (for integral n-ary operator objects) and naryLim (for all other n-ary operator objects) 
    /// options are looked to for the limit placement.
    /// </summary>
    public static final int UNDEFINED = -1;
    
    /// <summary>
    /// Limits placed to the side of the base.
    /// </summary>
    public static final int SUBSCRIPT_SUPERSCRIPT = 0;
    
    /// <summary>
    /// Limits placed to the directly above and below the base.
    /// </summary>
    public static final int UNDER_OVER = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MathLimitLocation value.
    /// </summary>
    public static String getName(/*MathLimitLocation*/int mathLimitLocation)
    {
    	switch (mathLimitLocation)
    	{
    		case UNDEFINED: return "UNDEFINED";
    		case SUBSCRIPT_SUPERSCRIPT: return "SUBSCRIPT_SUPERSCRIPT";
    		case UNDER_OVER: return "UNDER_OVER";
    		default: return "Unknown MathLimitLocation value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MathLimitLocation value.
    /// </summary>
    public static String toString(/*MathLimitLocation*/int mathLimitLocation)
    {
    	switch (mathLimitLocation)
    	{
    		case UNDEFINED: return "Undefined";
    		case SUBSCRIPT_SUPERSCRIPT: return "SubscriptSuperscript";
    		case UNDER_OVER: return "UnderOver";
    		default: return "Unknown MathLimitLocation value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MathLimitLocation name.
    /// </summary>
    public static /*MathLimitLocation*/int fromName(String mathLimitLocationName)
    {
    	if ("UNDEFINED".equals(mathLimitLocationName)) return UNDEFINED;
    	if ("SUBSCRIPT_SUPERSCRIPT".equals(mathLimitLocationName)) return SUBSCRIPT_SUPERSCRIPT;
    	if ("UNDER_OVER".equals(mathLimitLocationName)) return UNDER_OVER;
    	throw new IllegalArgumentException("Unknown MathLimitLocation name.");
    }
    
    /// <summary>
    /// Returns array of MathLimitLocation values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNDEFINED,
    		SUBSCRIPT_SUPERSCRIPT,
    		UNDER_OVER,
    	};
    }

    public static final int length = 3;
}
