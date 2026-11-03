// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/09/2020 by Dmitry Burov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the parity-based page selection rule.
/// </summary>
/*enum*/ final class PageParity
{
    private PageParity(){}
    
    /// <summary>
    /// Select pages with any parity.
    /// </summary>
    public static final int ANY = 0;

    /// <summary>
    /// Select all even pages.
    /// </summary>
    public static final int EVEN = 1;

    /// <summary>
    /// Select all odd pages.
    /// </summary>
    public static final int ODD = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PageParity value.
    /// </summary>
    public static String getName(/*PageParity*/int pageParity)
    {
    	switch (pageParity)
    	{
    		case ANY: return "ANY";
    		case EVEN: return "EVEN";
    		case ODD: return "ODD";
    		default: return "Unknown PageParity value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PageParity value.
    /// </summary>
    public static String toString(/*PageParity*/int pageParity)
    {
    	switch (pageParity)
    	{
    		case ANY: return "Any";
    		case EVEN: return "Even";
    		case ODD: return "Odd";
    		default: return "Unknown PageParity value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PageParity name.
    /// </summary>
    public static /*PageParity*/int fromName(String pageParityName)
    {
    	if ("ANY".equals(pageParityName)) return ANY;
    	if ("EVEN".equals(pageParityName)) return EVEN;
    	if ("ODD".equals(pageParityName)) return ODD;
    	throw new IllegalArgumentException("Unknown PageParity name.");
    }
    
    /// <summary>
    /// Returns array of PageParity values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ANY,
    		EVEN,
    		ODD,
    	};
    }

    public static final int length = 3;
}

