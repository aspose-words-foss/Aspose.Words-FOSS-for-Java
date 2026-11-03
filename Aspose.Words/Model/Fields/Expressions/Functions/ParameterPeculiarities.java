// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/03/2012 by Daria

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Enumerates the parameter properties.
/// </summary>
/*enum*/ final class ParameterPeculiarities
{
    private ParameterPeculiarities(){}
    
    /// <summary>
    /// Specifies that parameter value doesn't have specific flags.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// Indicates that the parameter is a radix-10 number: in additional to digits
    /// can contain only negative sign, currency and/or fraction chars.
    /// </summary>
    public static final int NUMBER = 1;

    /// <summary>
    /// Indicates that the parameter is a fractional number.
    /// </summary>
    public static final int FRACTIONAL = 2;

    /// <summary>
    /// Indicates that the parameter value is a negative number.
    /// </summary>
    public static final int NEGATIVE = 4;

    /// <summary>
    /// Indicates that a currency sign appears at the beginning of parameter.
    /// </summary>
    public static final int CURRENCY_AT_START = 8;

    /// <summary>
    /// Indicates that a currency sign appears at the end of parameter.
    /// </summary>
    public static final int CURRENCY_AT_END = 16;

    /// <summary>
    /// Indicates that the parameter is preceded by a space.
    /// </summary>
    public static final int SPACE_BEFORE = 32;

    /// <summary>
    /// Indicates that the parameter is followed by a space.
    /// </summary>
    public static final int SPACE_AFTER = 64;

    /// <summary>
    /// Indicates that the parameter contains one of the operators supported by Word.
    /// </summary>
    public static final int CONTAINS_OPERATOR = 128;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ParameterPeculiarities value.
    /// </summary>
    public static String getName(/*ParameterPeculiarities*/int parameterPeculiarities)
    {
    	switch (parameterPeculiarities)
    	{
    		case NONE: return "NONE";
    		case NUMBER: return "NUMBER";
    		case FRACTIONAL: return "FRACTIONAL";
    		case NEGATIVE: return "NEGATIVE";
    		case CURRENCY_AT_START: return "CURRENCY_AT_START";
    		case CURRENCY_AT_END: return "CURRENCY_AT_END";
    		case SPACE_BEFORE: return "SPACE_BEFORE";
    		case SPACE_AFTER: return "SPACE_AFTER";
    		case CONTAINS_OPERATOR: return "CONTAINS_OPERATOR";
    		default: return "Unknown ParameterPeculiarities value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer ParameterPeculiarities value.
    /// </summary>
    public static Set<String> getNames(/*ParameterPeculiarities*/int parameterPeculiarities)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & parameterPeculiarities) == NONE) names.add("NONE");
    	if ((NUMBER & parameterPeculiarities) == NUMBER) names.add("NUMBER");
    	if ((FRACTIONAL & parameterPeculiarities) == FRACTIONAL) names.add("FRACTIONAL");
    	if ((NEGATIVE & parameterPeculiarities) == NEGATIVE) names.add("NEGATIVE");
    	if ((CURRENCY_AT_START & parameterPeculiarities) == CURRENCY_AT_START) names.add("CURRENCY_AT_START");
    	if ((CURRENCY_AT_END & parameterPeculiarities) == CURRENCY_AT_END) names.add("CURRENCY_AT_END");
    	if ((SPACE_BEFORE & parameterPeculiarities) == SPACE_BEFORE) names.add("SPACE_BEFORE");
    	if ((SPACE_AFTER & parameterPeculiarities) == SPACE_AFTER) names.add("SPACE_AFTER");
    	if ((CONTAINS_OPERATOR & parameterPeculiarities) == CONTAINS_OPERATOR) names.add("CONTAINS_OPERATOR");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ParameterPeculiarities value.
    /// </summary>
    public static String toString(/*ParameterPeculiarities*/int parameterPeculiarities)
    {
    	switch (parameterPeculiarities)
    	{
    		case NONE: return "None";
    		case NUMBER: return "Number";
    		case FRACTIONAL: return "Fractional";
    		case NEGATIVE: return "Negative";
    		case CURRENCY_AT_START: return "CurrencyAtStart";
    		case CURRENCY_AT_END: return "CurrencyAtEnd";
    		case SPACE_BEFORE: return "SpaceBefore";
    		case SPACE_AFTER: return "SpaceAfter";
    		case CONTAINS_OPERATOR: return "ContainsOperator";
    		default: return toStringSet(parameterPeculiarities);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ParameterPeculiarities value.
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
    /// Returns integer representation by ParameterPeculiarities name.
    /// </summary>
    public static /*ParameterPeculiarities*/int fromName(String parameterPeculiaritiesName)
    {
    	if ("NONE".equals(parameterPeculiaritiesName)) return NONE;
    	if ("NUMBER".equals(parameterPeculiaritiesName)) return NUMBER;
    	if ("FRACTIONAL".equals(parameterPeculiaritiesName)) return FRACTIONAL;
    	if ("NEGATIVE".equals(parameterPeculiaritiesName)) return NEGATIVE;
    	if ("CURRENCY_AT_START".equals(parameterPeculiaritiesName)) return CURRENCY_AT_START;
    	if ("CURRENCY_AT_END".equals(parameterPeculiaritiesName)) return CURRENCY_AT_END;
    	if ("SPACE_BEFORE".equals(parameterPeculiaritiesName)) return SPACE_BEFORE;
    	if ("SPACE_AFTER".equals(parameterPeculiaritiesName)) return SPACE_AFTER;
    	if ("CONTAINS_OPERATOR".equals(parameterPeculiaritiesName)) return CONTAINS_OPERATOR;
    	throw new IllegalArgumentException("Unknown ParameterPeculiarities name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by ParameterPeculiarities names.
    /// </summary>
    public static /*ParameterPeculiarities*/int fromNames(Set<String> parameterPeculiaritiesNames)
    {
    	/*ParameterPeculiarities*/int result = 0;
    	for (String name : parameterPeculiaritiesNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of ParameterPeculiarities values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		NUMBER,
    		FRACTIONAL,
    		NEGATIVE,
    		CURRENCY_AT_START,
    		CURRENCY_AT_END,
    		SPACE_BEFORE,
    		SPACE_AFTER,
    		CONTAINS_OPERATOR,
    	};
    }

    public static final int length = 9;
}

