// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/03/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of fraction sign that should be drawn between numerator and denominator.
/// </summary>
/*enum*/ final class MathFractionType
{
    private MathFractionType(){}
    
    /// <summary>
    /// Bar Fraction
    /// </summary>
    public static final int BAR = 0;
    
    /// <summary>
    /// Linear fraction
    /// </summary>
    public static final int LINEAR = 1;
    
    /// <summary>
    /// Stack object, no bar drawn
    /// </summary>
    public static final int NO_BAR = 2;
    
    /// <summary>
    /// Skewed fraction
    /// </summary>
    public static final int SKEWED = 3;

    /// <summary>
    /// Default is <see cref="Bar"/>
    /// </summary>
    public static final int DEFAULT = BAR;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MathFractionType value.
    /// </summary>
    public static String getName(/*MathFractionType*/int mathFractionType)
    {
    	switch (mathFractionType)
    	{
    		case BAR:
    		/*case DEFAULT:*/
    			return "BAR" + " | " + "DEFAULT";
    		case LINEAR: return "LINEAR";
    		case NO_BAR: return "NO_BAR";
    		case SKEWED: return "SKEWED";
    		default: return "Unknown MathFractionType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MathFractionType value.
    /// </summary>
    public static String toString(/*MathFractionType*/int mathFractionType)
    {
    	switch (mathFractionType)
    	{
    		case BAR:
    		/*case DEFAULT:*/
    			return "Bar" + " | " + "Default";
    		case LINEAR: return "Linear";
    		case NO_BAR: return "NoBar";
    		case SKEWED: return "Skewed";
    		default: return "Unknown MathFractionType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MathFractionType name.
    /// </summary>
    public static /*MathFractionType*/int fromName(String mathFractionTypeName)
    {
    	if ("BAR".equals(mathFractionTypeName)) return BAR;
    	if ("LINEAR".equals(mathFractionTypeName)) return LINEAR;
    	if ("NO_BAR".equals(mathFractionTypeName)) return NO_BAR;
    	if ("SKEWED".equals(mathFractionTypeName)) return SKEWED;
    	if ("DEFAULT".equals(mathFractionTypeName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown MathFractionType name.");
    }
    
    /// <summary>
    /// Returns array of MathFractionType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BAR,
    		LINEAR,
    		NO_BAR,
    		SKEWED,
    		DEFAULT,
    	};
    }

    public static final int length = 5;
}

