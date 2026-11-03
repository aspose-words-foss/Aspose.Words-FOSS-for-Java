// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/04/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies types of ligatures are available for the run of text.
/// </summary>
/*enum*/ final class Ligature
{
    private Ligature(){}
    
    /// <summary>
    /// Specifies that the text is not displayed using ligatures.
    /// </summary>
    public static final int NONE = 0x00;

    /// <summary>
    /// Specifies that the text is displayed using standard ligatures if they are supported by the font.
    /// </summary>
    public static final int STANDARD = 0x01;

    /// <summary>
    /// Specifies that the text is displayed using contextual ligatures if they are supported by the font.
    /// </summary>
    public static final int CONTEXTUAL = 0x02;

    /// <summary>
    /// Specifies that the text is displayed using historical ligatures if they are supported by the font.
    /// </summary>
    public static final int HISTORICAL = 0x04;

    /// <summary>
    /// Specifies that the text is displayed using discretional ligatures if they are supported by the font.
    /// </summary>
    public static final int DISCRETIONAL = 0x08;

    /// <summary>
    /// Specifies that the text is displayed using standard, historical, discretional, and contextual ligatures if they are supported by the font.
    /// </summary>
    public static final int ALL = STANDARD | HISTORICAL | DISCRETIONAL | CONTEXTUAL;

    /// <summary>
    /// Default value.
    /// </summary>
    public static final int DEFAULT = NONE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer Ligature value.
    /// </summary>
    public static String getName(/*Ligature*/int ligature)
    {
    	switch (ligature)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "NONE" + " | " + "DEFAULT";
    		case STANDARD: return "STANDARD";
    		case CONTEXTUAL: return "CONTEXTUAL";
    		case HISTORICAL: return "HISTORICAL";
    		case DISCRETIONAL: return "DISCRETIONAL";
    		case ALL: return "ALL";
    		default: return "Unknown Ligature value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer Ligature value.
    /// </summary>
    public static Set<String> getNames(/*Ligature*/int ligature)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & ligature) == NONE) names.add("NONE");
    	if ((STANDARD & ligature) == STANDARD) names.add("STANDARD");
    	if ((CONTEXTUAL & ligature) == CONTEXTUAL) names.add("CONTEXTUAL");
    	if ((HISTORICAL & ligature) == HISTORICAL) names.add("HISTORICAL");
    	if ((DISCRETIONAL & ligature) == DISCRETIONAL) names.add("DISCRETIONAL");
    	if ((ALL & ligature) == ALL) names.add("ALL");
    	if ((DEFAULT & ligature) == DEFAULT) names.add("DEFAULT");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer Ligature value.
    /// </summary>
    public static String toString(/*Ligature*/int ligature)
    {
    	switch (ligature)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "None" + " | " + "Default";
    		case STANDARD: return "Standard";
    		case CONTEXTUAL: return "Contextual";
    		case HISTORICAL: return "Historical";
    		case DISCRETIONAL: return "Discretional";
    		case ALL: return "All";
    		default: return toStringSet(ligature);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer Ligature value.
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
    /// Returns integer representation by Ligature name.
    /// </summary>
    public static /*Ligature*/int fromName(String ligatureName)
    {
    	if ("NONE".equals(ligatureName)) return NONE;
    	if ("STANDARD".equals(ligatureName)) return STANDARD;
    	if ("CONTEXTUAL".equals(ligatureName)) return CONTEXTUAL;
    	if ("HISTORICAL".equals(ligatureName)) return HISTORICAL;
    	if ("DISCRETIONAL".equals(ligatureName)) return DISCRETIONAL;
    	if ("ALL".equals(ligatureName)) return ALL;
    	if ("DEFAULT".equals(ligatureName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown Ligature name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by Ligature names.
    /// </summary>
    public static /*Ligature*/int fromNames(Set<String> ligatureNames)
    {
    	/*Ligature*/int result = 0;
    	for (String name : ligatureNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of Ligature values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		STANDARD,
    		CONTEXTUAL,
    		HISTORICAL,
    		DISCRETIONAL,
    		ALL,
    		DEFAULT,
    	};
    }

    public static final int length = 7;
}

