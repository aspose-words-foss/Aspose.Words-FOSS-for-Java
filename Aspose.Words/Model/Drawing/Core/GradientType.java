// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Defines the method used to generate a gradient fill.
/// </summary>
/*enum*/ final class GradientType
{
    private GradientType(){}
    
    /// <summary>
    /// Interpolate without correction between RGBs.
    /// </summary>
    public static final int NONE  = 0;        
    /// <summary>
    /// Apply gamma correction to colors.
    /// </summary>
    public static final int GAMMA = 1;        
    /// <summary>
    /// Apply a sigma transfer function to position.
    /// </summary>
    public static final int SIGMA = 2;        
    /// <summary>
    /// Add a flat band at the start of the shade.
    /// </summary>
    public static final int BAND  = 4;        
    /// <summary>
    /// This is a one color shade.
    /// </summary>
    public static final int ONE_COLOR = 8;     

    // TODO shape
//        /* A parameter for the band or sigma function can be stored in the top
//           16 bits of the value - this is a proportion of *each* band of the
//           shade to make flat (or the approximate equal value for a sigma
//           function).  NOTE: the parameter is not used for the sigma function,
//           instead a built in value is used.  This value should not be changed
//           from the default! */
//        ParameterShift = 16,
//        ParameterMask  = 0xffff0000,
//
//        Default = (Gamma|Sigma|
//            (16384<<ParameterShift))

    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer GradientType value.
    /// </summary>
    public static String getName(/*GradientType*/int gradientType)
    {
    	switch (gradientType)
    	{
    		case NONE: return "NONE";
    		case GAMMA: return "GAMMA";
    		case SIGMA: return "SIGMA";
    		case BAND: return "BAND";
    		case ONE_COLOR: return "ONE_COLOR";
    		default: return "Unknown GradientType value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer GradientType value.
    /// </summary>
    public static Set<String> getNames(/*GradientType*/int gradientType)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & gradientType) == NONE) names.add("NONE");
    	if ((GAMMA & gradientType) == GAMMA) names.add("GAMMA");
    	if ((SIGMA & gradientType) == SIGMA) names.add("SIGMA");
    	if ((BAND & gradientType) == BAND) names.add("BAND");
    	if ((ONE_COLOR & gradientType) == ONE_COLOR) names.add("ONE_COLOR");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer GradientType value.
    /// </summary>
    public static String toString(/*GradientType*/int gradientType)
    {
    	switch (gradientType)
    	{
    		case NONE: return "None";
    		case GAMMA: return "Gamma";
    		case SIGMA: return "Sigma";
    		case BAND: return "Band";
    		case ONE_COLOR: return "OneColor";
    		default: return toStringSet(gradientType);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer GradientType value.
    /// </summary>
    public static String toStringSet(int attr)
    {
    	String result = "";
    	String separator = "";
    	for (int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by GradientType name.
    /// </summary>
    public static /*GradientType*/int fromName(String gradientTypeName)
    {
    	if ("NONE".equals(gradientTypeName)) return NONE;
    	if ("GAMMA".equals(gradientTypeName)) return GAMMA;
    	if ("SIGMA".equals(gradientTypeName)) return SIGMA;
    	if ("BAND".equals(gradientTypeName)) return BAND;
    	if ("ONE_COLOR".equals(gradientTypeName)) return ONE_COLOR;
    	throw new IllegalArgumentException("Unknown GradientType name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by GradientType names.
    /// </summary>
    public static /*GradientType*/int fromNames(Set<String> gradientTypeNames)
    {
    	/*GradientType*/int result = 0;
    	for (String name : gradientTypeNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of GradientType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		GAMMA,
    		SIGMA,
    		BAND,
    		ONE_COLOR,
    	};
    }

    public static final int length = 5;
}

