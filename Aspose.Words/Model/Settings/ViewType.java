// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Possible values for the view mode in Microsoft Word.
/// </summary>
/// <seealso cref="ViewOptions"/>
/// <seealso cref="ViewOptions.ViewType"/>
public /*enum*/ final class ViewType
{
    private ViewType(){}
    
    /// <summary>
    /// The document shall be rendered in the default view of the application.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// The document shall be rendered in the default view of the application.
    /// </summary>
    public static final int READING = 0;
    /// <summary>
    /// The document shall be opened in a view that displays the document as it will print.
    /// </summary>
    public static final int PAGE_LAYOUT = 1;
    /// <summary>
    /// The document shall be rendered in a view optimized for outlining or creating long documents.
    /// </summary>
    public static final int OUTLINE = 3;
    /// <summary>
    /// The document shall be rendered in a view optimized for outlining or creating long documents.
    /// </summary>
    public static final int NORMAL = 4;            
    /// <summary>
    /// The document shall be rendered in a view mimicking the way this document would be displayed 
    /// in a web page.
    /// </summary>
    public static final int WEB = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ViewType value.
    /// </summary>
    public static String getName(/*ViewType*/int viewType)
    {
    	switch (viewType)
    	{
    		case NONE:
    		/*case READING:*/
    			return "NONE" + " | " + "READING";
    		case PAGE_LAYOUT: return "PAGE_LAYOUT";
    		case OUTLINE: return "OUTLINE";
    		case NORMAL: return "NORMAL";
    		case WEB: return "WEB";
    		default: return "Unknown ViewType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ViewType value.
    /// </summary>
    public static String toString(/*ViewType*/int viewType)
    {
    	switch (viewType)
    	{
    		case NONE:
    		/*case READING:*/
    			return "None" + " | " + "Reading";
    		case PAGE_LAYOUT: return "PageLayout";
    		case OUTLINE: return "Outline";
    		case NORMAL: return "Normal";
    		case WEB: return "Web";
    		default: return "Unknown ViewType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ViewType name.
    /// </summary>
    public static /*ViewType*/int fromName(String viewTypeName)
    {
    	if ("NONE".equals(viewTypeName)) return NONE;
    	if ("READING".equals(viewTypeName)) return READING;
    	if ("PAGE_LAYOUT".equals(viewTypeName)) return PAGE_LAYOUT;
    	if ("OUTLINE".equals(viewTypeName)) return OUTLINE;
    	if ("NORMAL".equals(viewTypeName)) return NORMAL;
    	if ("WEB".equals(viewTypeName)) return WEB;
    	throw new IllegalArgumentException("Unknown ViewType name.");
    }
    
    /// <summary>
    /// Returns array of ViewType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		READING,
    		PAGE_LAYOUT,
    		OUTLINE,
    		NORMAL,
    		WEB,
    	};
    }

    public static final int length = 6;
}

