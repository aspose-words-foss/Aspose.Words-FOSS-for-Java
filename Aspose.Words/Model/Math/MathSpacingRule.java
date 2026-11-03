// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/02/2011 by Denis Darkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of vertical spacing between rows in a <see cref="MathObjectMatrix"/> and
/// rows in a <see cref="MathObjectArray"/>.
/// </summary>
/*enum*/ final class MathSpacingRule
{
    private MathSpacingRule(){}
    
    /// <summary>
    /// Single spacing gap (1 em)
    /// </summary>
    public static final int SINGLE = 0;

    /// <summary>
    /// 1.5 spacing gap (1.5 ems)
    /// </summary>
    public static final int ONE_AND_HALF = 1;
    
    /// <summary>
    /// 2 spacing gap (2 ems)
    /// </summary>
    public static final int DOUBLE = 2;
    
    /// <summary>
    /// Exactly 
    /// (for <see cref="MathObjectMatrix"/>, rely on value of <see cref="MathObjectMatrix.ColumnGap"/>, measured in twips)
    /// (for <see cref="MathObjectArray"/>, rely on value of <see cref="MathObjectArray.RowSpacing"/>, measured in twips)
    /// </summary>
    public static final int EXACTLY = 3;
    
    /// <summary>
    /// Multiple.
    /// (for  <see cref="MathObjectMatrix"/>,  rely on value of <see cref="MathObjectMatrix.ColumnGap"/>, 
    /// measured in 0.5 em increments) 
    /// (for <see cref="MathObjectArray"/>, rely on value of <see cref="MathObjectArray.RowSpacing"/>, 
    /// measured in 0.5 line increments)
    /// </summary>
    public static final int MULTIPLE = 4;

    /// <summary>
    /// Default = <see cref="Single"/>
    /// </summary>
    public static final int DEFAULT = SINGLE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MathSpacingRule value.
    /// </summary>
    public static String getName(/*MathSpacingRule*/int mathSpacingRule)
    {
    	switch (mathSpacingRule)
    	{
    		case SINGLE:
    		/*case DEFAULT:*/
    			return "SINGLE" + " | " + "DEFAULT";
    		case ONE_AND_HALF: return "ONE_AND_HALF";
    		case DOUBLE: return "DOUBLE";
    		case EXACTLY: return "EXACTLY";
    		case MULTIPLE: return "MULTIPLE";
    		default: return "Unknown MathSpacingRule value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MathSpacingRule value.
    /// </summary>
    public static String toString(/*MathSpacingRule*/int mathSpacingRule)
    {
    	switch (mathSpacingRule)
    	{
    		case SINGLE:
    		/*case DEFAULT:*/
    			return "Single" + " | " + "Default";
    		case ONE_AND_HALF: return "OneAndHalf";
    		case DOUBLE: return "Double";
    		case EXACTLY: return "Exactly";
    		case MULTIPLE: return "Multiple";
    		default: return "Unknown MathSpacingRule value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MathSpacingRule name.
    /// </summary>
    public static /*MathSpacingRule*/int fromName(String mathSpacingRuleName)
    {
    	if ("SINGLE".equals(mathSpacingRuleName)) return SINGLE;
    	if ("ONE_AND_HALF".equals(mathSpacingRuleName)) return ONE_AND_HALF;
    	if ("DOUBLE".equals(mathSpacingRuleName)) return DOUBLE;
    	if ("EXACTLY".equals(mathSpacingRuleName)) return EXACTLY;
    	if ("MULTIPLE".equals(mathSpacingRuleName)) return MULTIPLE;
    	if ("DEFAULT".equals(mathSpacingRuleName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown MathSpacingRule name.");
    }
    
    /// <summary>
    /// Returns array of MathSpacingRule values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SINGLE,
    		ONE_AND_HALF,
    		DOUBLE,
    		EXACTLY,
    		MULTIPLE,
    		DEFAULT,
    	};
    }

    public static final int length = 6;
}
