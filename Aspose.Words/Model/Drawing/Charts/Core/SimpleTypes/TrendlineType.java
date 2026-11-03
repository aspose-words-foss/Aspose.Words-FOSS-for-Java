// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies all types of trendline which are available for series in a chart.
/// Corresponds ST_TrendlineType simple type (5.7.3.51).
/// </summary>
/*enum*/ final class TrendlineType
{
    private TrendlineType(){}
    
    /// <summary>
    /// Specifies the trendline shall be an exponential curve in the form y=ab^x.
    /// </summary>
    public static final int EXPONENTIAL = 0;
    /// <summary>
    /// Specifies the trendline shall be a line in the form y=mx+b.
    /// </summary>
    public static final int LINEAR = 1;
    /// <summary>
    /// Specifies the trendline shall be a logarithmic curve in the form y=a*log(x)+b, where log is the natural logarithm.
    /// </summary>
    public static final int LOGARITHMIC = 2;
    /// <summary>
    /// Specifies the trendline shall be a moving average of period Period.
    /// </summary>
    public static final int MOVING_AVERAGE = 3;
    /// <summary>
    /// Specifies the trendline shall be a polynomial curve of order Order in the form y=ax^6+bx^5+cx^4+dx^3+ex^2+fe+g.
    /// </summary>
    public static final int POLYNOMIAL = 4;
    /// <summary>
    /// Specifies the trendline shall be a power curve in the form y=ax^b.
    /// </summary>
    public static final int POWER = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TrendlineType value.
    /// </summary>
    public static String getName(/*TrendlineType*/int trendlineType)
    {
    	switch (trendlineType)
    	{
    		case EXPONENTIAL: return "EXPONENTIAL";
    		case LINEAR: return "LINEAR";
    		case LOGARITHMIC: return "LOGARITHMIC";
    		case MOVING_AVERAGE: return "MOVING_AVERAGE";
    		case POLYNOMIAL: return "POLYNOMIAL";
    		case POWER: return "POWER";
    		default: return "Unknown TrendlineType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TrendlineType value.
    /// </summary>
    public static String toString(/*TrendlineType*/int trendlineType)
    {
    	switch (trendlineType)
    	{
    		case EXPONENTIAL: return "Exponential";
    		case LINEAR: return "Linear";
    		case LOGARITHMIC: return "Logarithmic";
    		case MOVING_AVERAGE: return "MovingAverage";
    		case POLYNOMIAL: return "Polynomial";
    		case POWER: return "Power";
    		default: return "Unknown TrendlineType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TrendlineType name.
    /// </summary>
    public static /*TrendlineType*/int fromName(String trendlineTypeName)
    {
    	if ("EXPONENTIAL".equals(trendlineTypeName)) return EXPONENTIAL;
    	if ("LINEAR".equals(trendlineTypeName)) return LINEAR;
    	if ("LOGARITHMIC".equals(trendlineTypeName)) return LOGARITHMIC;
    	if ("MOVING_AVERAGE".equals(trendlineTypeName)) return MOVING_AVERAGE;
    	if ("POLYNOMIAL".equals(trendlineTypeName)) return POLYNOMIAL;
    	if ("POWER".equals(trendlineTypeName)) return POWER;
    	throw new IllegalArgumentException("Unknown TrendlineType name.");
    }
    
    /// <summary>
    /// Returns array of TrendlineType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		EXPONENTIAL,
    		LINEAR,
    		LOGARITHMIC,
    		MOVING_AVERAGE,
    		POLYNOMIAL,
    		POWER,
    	};
    }

    public static final int length = 6;
}

