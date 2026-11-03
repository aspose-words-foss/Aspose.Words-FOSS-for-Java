// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/05/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Type of document nodes that represent &lt;input&gt; and &lt;select&gt; elements imported from HTML.
/// </summary>
public /*enum*/ final class HtmlControlType
{
    private HtmlControlType(){}
    
    /// <summary>
    /// <para>A form field.</para>
    /// </summary>
    public static final int FORM_FIELD = 0;

    /// <summary>
    /// <para>A structured document tag</para>
    /// </summary>
    public static final int STRUCTURED_DOCUMENT_TAG = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HtmlControlType value.
    /// </summary>
    public static String getName(/*HtmlControlType*/int htmlControlType)
    {
    	switch (htmlControlType)
    	{
    		case FORM_FIELD: return "FORM_FIELD";
    		case STRUCTURED_DOCUMENT_TAG: return "STRUCTURED_DOCUMENT_TAG";
    		default: return "Unknown HtmlControlType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HtmlControlType value.
    /// </summary>
    public static String toString(/*HtmlControlType*/int htmlControlType)
    {
    	switch (htmlControlType)
    	{
    		case FORM_FIELD: return "FormField";
    		case STRUCTURED_DOCUMENT_TAG: return "StructuredDocumentTag";
    		default: return "Unknown HtmlControlType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HtmlControlType name.
    /// </summary>
    public static /*HtmlControlType*/int fromName(String htmlControlTypeName)
    {
    	if ("FORM_FIELD".equals(htmlControlTypeName)) return FORM_FIELD;
    	if ("STRUCTURED_DOCUMENT_TAG".equals(htmlControlTypeName)) return STRUCTURED_DOCUMENT_TAG;
    	throw new IllegalArgumentException("Unknown HtmlControlType name.");
    }
    
    /// <summary>
    /// Returns array of HtmlControlType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FORM_FIELD,
    		STRUCTURED_DOCUMENT_TAG,
    	};
    }

    public static final int length = 2;
}

