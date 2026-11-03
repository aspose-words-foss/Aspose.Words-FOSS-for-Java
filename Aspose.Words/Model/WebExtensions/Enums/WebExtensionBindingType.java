// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates available types of binding between a web extension and the data in the document.
/// </summary>
/// <dev>https://docs.microsoft.com/en-us/javascript/api/office/office.bindingtype?view=office-js</dev>
public /*enum*/ final class WebExtensionBindingType
{
    private WebExtensionBindingType(){}
    
    /// <summary>
    /// Tabular data without a header row.
    /// </summary>
    public static final int MATRIX = 0;

    /// <summary>
    /// Tabular data with a header row.
    /// </summary>
    public static final int TABLE = 1;

    /// <summary>
    /// Plain text.
    /// </summary>
    public static final int TEXT = 2;

    /// <summary>
    /// Matrix used by default.
    /// </summary>
    public static final int DEFAULT = MATRIX; // Default for a while.

    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer WebExtensionBindingType value.
    /// </summary>
    public static String getName(/*WebExtensionBindingType*/int webExtensionBindingType)
    {
    	switch (webExtensionBindingType)
    	{
    		case MATRIX:
    		/*case DEFAULT:*/
    			return "MATRIX" + " | " + "DEFAULT";
    		case TABLE: return "TABLE";
    		case TEXT: return "TEXT";
    		default: return "Unknown WebExtensionBindingType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer WebExtensionBindingType value.
    /// </summary>
    public static String toString(/*WebExtensionBindingType*/int webExtensionBindingType)
    {
    	switch (webExtensionBindingType)
    	{
    		case MATRIX:
    		/*case DEFAULT:*/
    			return "Matrix" + " | " + "Default";
    		case TABLE: return "Table";
    		case TEXT: return "Text";
    		default: return "Unknown WebExtensionBindingType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by WebExtensionBindingType name.
    /// </summary>
    public static /*WebExtensionBindingType*/int fromName(String webExtensionBindingTypeName)
    {
    	if ("MATRIX".equals(webExtensionBindingTypeName)) return MATRIX;
    	if ("TABLE".equals(webExtensionBindingTypeName)) return TABLE;
    	if ("TEXT".equals(webExtensionBindingTypeName)) return TEXT;
    	if ("DEFAULT".equals(webExtensionBindingTypeName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown WebExtensionBindingType name.");
    }
    
    /// <summary>
    /// Returns array of WebExtensionBindingType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		MATRIX,
    		TABLE,
    		TEXT,
    		DEFAULT,
    	};
    }

    public static final int length = 4;
}

