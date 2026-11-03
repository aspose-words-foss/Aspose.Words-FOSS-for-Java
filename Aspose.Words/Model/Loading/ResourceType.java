// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/08/2011 by Kamimura Andrey

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Type of loaded resource.
/// </summary>
public /*enum*/ final class ResourceType
{
    private ResourceType(){}
    
    /// <summary>
    /// Image.
    /// </summary>
    public static final int IMAGE = 0;

    /// <summary>
    /// Font.
    /// </summary>
    public static final int FONT = 1;

    /// <summary>
    /// CSS style sheet.
    /// </summary>
    public static final int CSS_STYLE_SHEET = 2;

    /// <summary>
    /// Document.
    /// </summary>
    public static final int DOCUMENT = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ResourceType value.
    /// </summary>
    public static String getName(/*ResourceType*/int resourceType)
    {
    	switch (resourceType)
    	{
    		case IMAGE: return "IMAGE";
    		case FONT: return "FONT";
    		case CSS_STYLE_SHEET: return "CSS_STYLE_SHEET";
    		case DOCUMENT: return "DOCUMENT";
    		default: return "Unknown ResourceType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ResourceType value.
    /// </summary>
    public static String toString(/*ResourceType*/int resourceType)
    {
    	switch (resourceType)
    	{
    		case IMAGE: return "Image";
    		case FONT: return "Font";
    		case CSS_STYLE_SHEET: return "CssStyleSheet";
    		case DOCUMENT: return "Document";
    		default: return "Unknown ResourceType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ResourceType name.
    /// </summary>
    public static /*ResourceType*/int fromName(String resourceTypeName)
    {
    	if ("IMAGE".equals(resourceTypeName)) return IMAGE;
    	if ("FONT".equals(resourceTypeName)) return FONT;
    	if ("CSS_STYLE_SHEET".equals(resourceTypeName)) return CSS_STYLE_SHEET;
    	if ("DOCUMENT".equals(resourceTypeName)) return DOCUMENT;
    	throw new IllegalArgumentException("Unknown ResourceType name.");
    }
    
    /// <summary>
    /// Returns array of ResourceType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		IMAGE,
    		FONT,
    		CSS_STYLE_SHEET,
    		DOCUMENT,
    	};
    }

    public static final int length = 4;
}

