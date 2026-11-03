// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/10/2022 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


public /*enum*/ final class NumberFormattingOptions
{
    private NumberFormattingOptions(){}
    
    public static final int DEFAULT = 0x0;

    /// <summary>
    /// DO NOT replace format digit placeholder '#" with space if there is no corresponding digit in value.
    /// </summary>
    /// <example>
    /// value=5 format="###"
    /// w/o option => result="  5"
    /// w/  option => result="5"
    /// </example>
    public static final int IGNORE_UNMATCHED_DIGIT_PLACEHOLDER = 0x1;

    /// <summary>
    /// DO multiply value by 100 if format includes percent % character.
    /// w/o option: value=5 format="#%" => result="5%"
    /// w/  option: value=5 format="#%" => result="500%"
    /// </summary>
    public static final int IS_MULTIPLY_PERCENT = 0x2;

    /// <summary>
    /// Use <see cref="double.ToString(string,IFormatProvider)"/> method which does not fit MS Word behaviour.
    /// w/o option: value=12345 format="x##" => result="345"
    /// w/  option: value=12345 format="x##" => result="x12345"
    /// </summary>
    public static final int LEGACY_NUMBER_FORMAT = 0x4;

    /// <summary>
    /// DO NOT convert format to invariant culture.
    /// w/o option: value=12345 format="0,000" culture=ru-RU => result="12345,000"
    /// w/  option: value=12345 format="0,000" culture=ru-RU => result="12 345"
    /// </summary>
    public static final int FORMAT_IS_IN_INVARIANT_CULTURE = 0x8;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer NumberFormattingOptions value.
    /// </summary>
    public static String getName(/*NumberFormattingOptions*/int numberFormattingOptions)
    {
    	switch (numberFormattingOptions)
    	{
    		case DEFAULT: return "DEFAULT";
    		case IGNORE_UNMATCHED_DIGIT_PLACEHOLDER: return "IGNORE_UNMATCHED_DIGIT_PLACEHOLDER";
    		case IS_MULTIPLY_PERCENT: return "IS_MULTIPLY_PERCENT";
    		case LEGACY_NUMBER_FORMAT: return "LEGACY_NUMBER_FORMAT";
    		case FORMAT_IS_IN_INVARIANT_CULTURE: return "FORMAT_IS_IN_INVARIANT_CULTURE";
    		default: return "Unknown NumberFormattingOptions value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer NumberFormattingOptions value.
    /// </summary>
    public static Set<String> getNames(/*NumberFormattingOptions*/int numberFormattingOptions)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((DEFAULT & numberFormattingOptions) == DEFAULT) names.add("DEFAULT");
    	if ((IGNORE_UNMATCHED_DIGIT_PLACEHOLDER & numberFormattingOptions) == IGNORE_UNMATCHED_DIGIT_PLACEHOLDER) names.add("IGNORE_UNMATCHED_DIGIT_PLACEHOLDER");
    	if ((IS_MULTIPLY_PERCENT & numberFormattingOptions) == IS_MULTIPLY_PERCENT) names.add("IS_MULTIPLY_PERCENT");
    	if ((LEGACY_NUMBER_FORMAT & numberFormattingOptions) == LEGACY_NUMBER_FORMAT) names.add("LEGACY_NUMBER_FORMAT");
    	if ((FORMAT_IS_IN_INVARIANT_CULTURE & numberFormattingOptions) == FORMAT_IS_IN_INVARIANT_CULTURE) names.add("FORMAT_IS_IN_INVARIANT_CULTURE");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NumberFormattingOptions value.
    /// </summary>
    public static String toString(/*NumberFormattingOptions*/int numberFormattingOptions)
    {
    	switch (numberFormattingOptions)
    	{
    		case DEFAULT: return "Default";
    		case IGNORE_UNMATCHED_DIGIT_PLACEHOLDER: return "IgnoreUnmatchedDigitPlaceholder";
    		case IS_MULTIPLY_PERCENT: return "IsMultiplyPercent";
    		case LEGACY_NUMBER_FORMAT: return "LegacyNumberFormat";
    		case FORMAT_IS_IN_INVARIANT_CULTURE: return "FormatIsInInvariantCulture";
    		default: return toStringSet(numberFormattingOptions);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NumberFormattingOptions value.
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
    /// Returns integer representation by NumberFormattingOptions name.
    /// </summary>
    public static /*NumberFormattingOptions*/int fromName(String numberFormattingOptionsName)
    {
    	if ("DEFAULT".equals(numberFormattingOptionsName)) return DEFAULT;
    	if ("IGNORE_UNMATCHED_DIGIT_PLACEHOLDER".equals(numberFormattingOptionsName)) return IGNORE_UNMATCHED_DIGIT_PLACEHOLDER;
    	if ("IS_MULTIPLY_PERCENT".equals(numberFormattingOptionsName)) return IS_MULTIPLY_PERCENT;
    	if ("LEGACY_NUMBER_FORMAT".equals(numberFormattingOptionsName)) return LEGACY_NUMBER_FORMAT;
    	if ("FORMAT_IS_IN_INVARIANT_CULTURE".equals(numberFormattingOptionsName)) return FORMAT_IS_IN_INVARIANT_CULTURE;
    	throw new IllegalArgumentException("Unknown NumberFormattingOptions name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by NumberFormattingOptions names.
    /// </summary>
    public static /*NumberFormattingOptions*/int fromNames(Set<String> numberFormattingOptionsNames)
    {
    	/*NumberFormattingOptions*/int result = 0;
    	for (String name : numberFormattingOptionsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of NumberFormattingOptions values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFAULT,
    		IGNORE_UNMATCHED_DIGIT_PLACEHOLDER,
    		IS_MULTIPLY_PERCENT,
    		LEGACY_NUMBER_FORMAT,
    		FORMAT_IS_IN_INVARIANT_CULTURE,
    	};
    }

    public static final int length = 5;
}

