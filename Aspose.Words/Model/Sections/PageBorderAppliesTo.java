// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies which pages the page border is printed on.
/// </summary>
/// <seealso cref="PageSetup"/>
/// <seealso cref="PageSetup.BorderAppliesTo"/>
public /*enum*/ final class PageBorderAppliesTo
{
    private PageBorderAppliesTo(){}
    
    /// <summary>
    /// Page border is shown on all pages of the section.
    /// </summary>
    public static final int ALL_PAGES = 0;
    /// <summary>
    /// Page border is shown on the first page of the section only.
    /// </summary>
    public static final int FIRST_PAGE = 1;
    /// <summary>
    /// Page border is shown on all pages except the first page of the section.
    /// </summary>
    public static final int OTHER_PAGES = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PageBorderAppliesTo value.
    /// </summary>
    public static String getName(/*PageBorderAppliesTo*/int pageBorderAppliesTo)
    {
    	switch (pageBorderAppliesTo)
    	{
    		case ALL_PAGES: return "ALL_PAGES";
    		case FIRST_PAGE: return "FIRST_PAGE";
    		case OTHER_PAGES: return "OTHER_PAGES";
    		default: return "Unknown PageBorderAppliesTo value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PageBorderAppliesTo value.
    /// </summary>
    public static String toString(/*PageBorderAppliesTo*/int pageBorderAppliesTo)
    {
    	switch (pageBorderAppliesTo)
    	{
    		case ALL_PAGES: return "AllPages";
    		case FIRST_PAGE: return "FirstPage";
    		case OTHER_PAGES: return "OtherPages";
    		default: return "Unknown PageBorderAppliesTo value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PageBorderAppliesTo name.
    /// </summary>
    public static /*PageBorderAppliesTo*/int fromName(String pageBorderAppliesToName)
    {
    	if ("ALL_PAGES".equals(pageBorderAppliesToName)) return ALL_PAGES;
    	if ("FIRST_PAGE".equals(pageBorderAppliesToName)) return FIRST_PAGE;
    	if ("OTHER_PAGES".equals(pageBorderAppliesToName)) return OTHER_PAGES;
    	throw new IllegalArgumentException("Unknown PageBorderAppliesTo name.");
    }
    
    /// <summary>
    /// Returns array of PageBorderAppliesTo values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ALL_PAGES,
    		FIRST_PAGE,
    		OTHER_PAGES,
    	};
    }

    public static final int length = 3;
}

