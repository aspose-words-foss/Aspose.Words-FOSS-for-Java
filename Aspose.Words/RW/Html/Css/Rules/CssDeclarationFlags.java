// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/05/2016 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Flags that are used to mark declarations in a collection. For example, to indicate the source of each declaration.
/// </summary>
/*enum*/ final class CssDeclarationFlags
{
    private CssDeclarationFlags(){}
    
    /// <summary>
    /// A declaration isn't marked with any flags.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// A declaration comes from our User Agent stylesheet.
    /// </summary>
    public static final int USER_AGENT = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssDeclarationFlags value.
    /// </summary>
    public static String getName(/*CssDeclarationFlags*/int cssDeclarationFlags)
    {
    	switch (cssDeclarationFlags)
    	{
    		case NONE: return "NONE";
    		case USER_AGENT: return "USER_AGENT";
    		default: return "Unknown CssDeclarationFlags value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer CssDeclarationFlags value.
    /// </summary>
    public static Set<String> getNames(/*CssDeclarationFlags*/int cssDeclarationFlags)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & cssDeclarationFlags) == NONE) names.add("NONE");
    	if ((USER_AGENT & cssDeclarationFlags) == USER_AGENT) names.add("USER_AGENT");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssDeclarationFlags value.
    /// </summary>
    public static String toString(/*CssDeclarationFlags*/int cssDeclarationFlags)
    {
    	switch (cssDeclarationFlags)
    	{
    		case NONE: return "None";
    		case USER_AGENT: return "UserAgent";
    		default: return toStringSet(cssDeclarationFlags);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssDeclarationFlags value.
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
    /// Returns integer representation by CssDeclarationFlags name.
    /// </summary>
    public static /*CssDeclarationFlags*/int fromName(String cssDeclarationFlagsName)
    {
    	if ("NONE".equals(cssDeclarationFlagsName)) return NONE;
    	if ("USER_AGENT".equals(cssDeclarationFlagsName)) return USER_AGENT;
    	throw new IllegalArgumentException("Unknown CssDeclarationFlags name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by CssDeclarationFlags names.
    /// </summary>
    public static /*CssDeclarationFlags*/int fromNames(Set<String> cssDeclarationFlagsNames)
    {
    	/*CssDeclarationFlags*/int result = 0;
    	for (String name : cssDeclarationFlagsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of CssDeclarationFlags values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		USER_AGENT,
    	};
    }

    public static final int length = 2;
}

