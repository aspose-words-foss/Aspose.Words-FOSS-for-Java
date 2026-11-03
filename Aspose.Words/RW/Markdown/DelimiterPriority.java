// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates all possible priorities of the <see cref="Delimiter"/> objects.
/// </summary>
/*enum*/ final class DelimiterPriority
{
    private DelimiterPriority(){}
    
    public static final int LOWEST = Integer.MIN_VALUE;
    public static final int EMPHASES = -2147483647;
    public static final int LINK_TEXT = -2147483646;
    public static final int LINK_DESTINATION = -2147483645;
    public static final int AUTO_LINK = -2147483644;
    public static final int INLINE_CODE = AUTO_LINK;
    public static final int HIGHEST = Integer.MAX_VALUE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DelimiterPriority value.
    /// </summary>
    public static String getName(/*DelimiterPriority*/int delimiterPriority)
    {
    	switch (delimiterPriority)
    	{
    		case LOWEST: return "LOWEST";
    		case EMPHASES: return "EMPHASES";
    		case LINK_TEXT: return "LINK_TEXT";
    		case LINK_DESTINATION: return "LINK_DESTINATION";
    		case AUTO_LINK:
    		/*case INLINE_CODE:*/
    			return "AUTO_LINK" + " | " + "INLINE_CODE";
    		case HIGHEST: return "HIGHEST";
    		default: return "Unknown DelimiterPriority value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DelimiterPriority value.
    /// </summary>
    public static String toString(/*DelimiterPriority*/int delimiterPriority)
    {
    	switch (delimiterPriority)
    	{
    		case LOWEST: return "Lowest";
    		case EMPHASES: return "Emphases";
    		case LINK_TEXT: return "LinkText";
    		case LINK_DESTINATION: return "LinkDestination";
    		case AUTO_LINK:
    		/*case INLINE_CODE:*/
    			return "AutoLink" + " | " + "InlineCode";
    		case HIGHEST: return "Highest";
    		default: return "Unknown DelimiterPriority value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DelimiterPriority name.
    /// </summary>
    public static /*DelimiterPriority*/int fromName(String delimiterPriorityName)
    {
    	if ("LOWEST".equals(delimiterPriorityName)) return LOWEST;
    	if ("EMPHASES".equals(delimiterPriorityName)) return EMPHASES;
    	if ("LINK_TEXT".equals(delimiterPriorityName)) return LINK_TEXT;
    	if ("LINK_DESTINATION".equals(delimiterPriorityName)) return LINK_DESTINATION;
    	if ("AUTO_LINK".equals(delimiterPriorityName)) return AUTO_LINK;
    	if ("INLINE_CODE".equals(delimiterPriorityName)) return INLINE_CODE;
    	if ("HIGHEST".equals(delimiterPriorityName)) return HIGHEST;
    	throw new IllegalArgumentException("Unknown DelimiterPriority name.");
    }
    
    /// <summary>
    /// Returns array of DelimiterPriority values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LOWEST,
    		EMPHASES,
    		LINK_TEXT,
    		LINK_DESTINATION,
    		AUTO_LINK,
    		INLINE_CODE,
    		HIGHEST,
    	};
    }

    public static final int length = 7;
}

