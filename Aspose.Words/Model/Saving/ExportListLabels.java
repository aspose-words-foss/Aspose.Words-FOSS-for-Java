// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/11/2011 by Andrey Kamimura

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies how list labels are exported to HTML, MHTML and EPUB.
/// </summary>
/// <seealso cref="HtmlSaveOptions.ExportListLabels"/>
public /*enum*/ final class ExportListLabels
{
    private ExportListLabels(){}
    
    /// <summary>
    /// Outputs list labels in auto mode. Uses HTML native elements when possible.
    /// </summary>
    /// <remarks>
    /// HTML &lt;ul&gt; and &lt;ol&gt; tags are used for list label representation if it doesn't cause formatting loss, otherwise the HTML &lt;p&gt; tag is used.
    /// </remarks>
    public static final int AUTO = 0;

    /// <summary>
    /// Outputs all list labels as inline text.
    /// </summary>
    /// <remarks>
    /// HTML &lt;p&gt; tag is used for any list label representation.
    /// </remarks>
    public static final int AS_INLINE_TEXT = 1;

    /// <summary>
    /// Outputs all list labels as HTML native elements.
    /// </summary>
    /// <remarks>
    /// HTML &lt;ul&gt; and &lt;ol&gt; tags are used for list label representation. Some formatting loss is possible.
    /// </remarks>
    public static final int BY_HTML_TAGS = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ExportListLabels value.
    /// </summary>
    public static String getName(/*ExportListLabels*/int exportListLabels)
    {
    	switch (exportListLabels)
    	{
    		case AUTO: return "AUTO";
    		case AS_INLINE_TEXT: return "AS_INLINE_TEXT";
    		case BY_HTML_TAGS: return "BY_HTML_TAGS";
    		default: return "Unknown ExportListLabels value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ExportListLabels value.
    /// </summary>
    public static String toString(/*ExportListLabels*/int exportListLabels)
    {
    	switch (exportListLabels)
    	{
    		case AUTO: return "Auto";
    		case AS_INLINE_TEXT: return "AsInlineText";
    		case BY_HTML_TAGS: return "ByHtmlTags";
    		default: return "Unknown ExportListLabels value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ExportListLabels name.
    /// </summary>
    public static /*ExportListLabels*/int fromName(String exportListLabelsName)
    {
    	if ("AUTO".equals(exportListLabelsName)) return AUTO;
    	if ("AS_INLINE_TEXT".equals(exportListLabelsName)) return AS_INLINE_TEXT;
    	if ("BY_HTML_TAGS".equals(exportListLabelsName)) return BY_HTML_TAGS;
    	throw new IllegalArgumentException("Unknown ExportListLabels name.");
    }
    
    /// <summary>
    /// Returns array of ExportListLabels values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AUTO,
    		AS_INLINE_TEXT,
    		BY_HTML_TAGS,
    	};
    }

    public static final int length = 3;
}

