// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/08/2010 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies (in an autoportable way) how text, ignorable and significant whitespace 
/// will be returned by <see cref="AnyXmlReader"/>.
/// </summary>
public /*enum*/ final class AnyXmlTextHandling
{
    private AnyXmlTextHandling(){}
    
    /// <summary>
    /// No text and whitespace is returned to the caller. Only elements are returned.
    /// </summary>
    public static final int NONE = 0x0000;
    public static final int TEXT = 0x0001;
    public static final int SIGNIFICANT_WHITESPACE = 0x0002;
    public static final int WHITESPACE = 0x0004;
    public static final int PROCESSING_INSTRUCTION = 0x0008;
    public static final int ENTITY_REFERENCE = 0x0010;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AnyXmlTextHandling value.
    /// </summary>
    public static String getName(/*AnyXmlTextHandling*/int anyXmlTextHandling)
    {
    	switch (anyXmlTextHandling)
    	{
    		case NONE: return "NONE";
    		case TEXT: return "TEXT";
    		case SIGNIFICANT_WHITESPACE: return "SIGNIFICANT_WHITESPACE";
    		case WHITESPACE: return "WHITESPACE";
    		case PROCESSING_INSTRUCTION: return "PROCESSING_INSTRUCTION";
    		case ENTITY_REFERENCE: return "ENTITY_REFERENCE";
    		default: return "Unknown AnyXmlTextHandling value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer AnyXmlTextHandling value.
    /// </summary>
    public static Set<String> getNames(/*AnyXmlTextHandling*/int anyXmlTextHandling)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & anyXmlTextHandling) == NONE) names.add("NONE");
    	if ((TEXT & anyXmlTextHandling) == TEXT) names.add("TEXT");
    	if ((SIGNIFICANT_WHITESPACE & anyXmlTextHandling) == SIGNIFICANT_WHITESPACE) names.add("SIGNIFICANT_WHITESPACE");
    	if ((WHITESPACE & anyXmlTextHandling) == WHITESPACE) names.add("WHITESPACE");
    	if ((PROCESSING_INSTRUCTION & anyXmlTextHandling) == PROCESSING_INSTRUCTION) names.add("PROCESSING_INSTRUCTION");
    	if ((ENTITY_REFERENCE & anyXmlTextHandling) == ENTITY_REFERENCE) names.add("ENTITY_REFERENCE");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AnyXmlTextHandling value.
    /// </summary>
    public static String toString(/*AnyXmlTextHandling*/int anyXmlTextHandling)
    {
    	switch (anyXmlTextHandling)
    	{
    		case NONE: return "None";
    		case TEXT: return "Text";
    		case SIGNIFICANT_WHITESPACE: return "SignificantWhitespace";
    		case WHITESPACE: return "Whitespace";
    		case PROCESSING_INSTRUCTION: return "ProcessingInstruction";
    		case ENTITY_REFERENCE: return "EntityReference";
    		default: return toStringSet(anyXmlTextHandling);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AnyXmlTextHandling value.
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
    /// Returns integer representation by AnyXmlTextHandling name.
    /// </summary>
    public static /*AnyXmlTextHandling*/int fromName(String anyXmlTextHandlingName)
    {
    	if ("NONE".equals(anyXmlTextHandlingName)) return NONE;
    	if ("TEXT".equals(anyXmlTextHandlingName)) return TEXT;
    	if ("SIGNIFICANT_WHITESPACE".equals(anyXmlTextHandlingName)) return SIGNIFICANT_WHITESPACE;
    	if ("WHITESPACE".equals(anyXmlTextHandlingName)) return WHITESPACE;
    	if ("PROCESSING_INSTRUCTION".equals(anyXmlTextHandlingName)) return PROCESSING_INSTRUCTION;
    	if ("ENTITY_REFERENCE".equals(anyXmlTextHandlingName)) return ENTITY_REFERENCE;
    	throw new IllegalArgumentException("Unknown AnyXmlTextHandling name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by AnyXmlTextHandling names.
    /// </summary>
    public static /*AnyXmlTextHandling*/int fromNames(Set<String> anyXmlTextHandlingNames)
    {
    	/*AnyXmlTextHandling*/int result = 0;
    	for (String name : anyXmlTextHandlingNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of AnyXmlTextHandling values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		TEXT,
    		SIGNIFICANT_WHITESPACE,
    		WHITESPACE,
    		PROCESSING_INSTRUCTION,
    		ENTITY_REFERENCE,
    	};
    }

    public static final int length = 6;
}

