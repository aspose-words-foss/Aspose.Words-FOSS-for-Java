// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/09/2024 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Allows to specify the elements to be exported to Markdown as raw HTML.
/// </summary>
public /*enum*/ final class MarkdownExportAsHtml
{
    private MarkdownExportAsHtml(){}
    
    /// <summary>
    /// Export all elements using Markdown syntax without any raw HTML.
    /// </summary>
    public static final int NONE = 0x0000;

    /// <summary>
    /// Export tables as raw HTML.
    /// </summary>
    /// <remarks>
    /// <para>When this option is enabled, every table will be exported as raw HTML.
    /// Aspose.Words will try to preserve all formatting of the tables in this case.</para>
    /// <para>If this flag is set, then related <see cref="NonCompatibleTables"/> flag will be ignored.</para>
    /// </remarks>
    public static final int TABLES = 0x0001;

    /// <summary>
    /// Export tables that cannot be correctly represented in pure Markdown as raw HTML.
    /// </summary>
    /// <remarks>
    /// <para> When this option is enabled, Aspose.Words will only export tables that have merged cells
    /// or nested tables as raw HTML. And all other tables will be exported in Markdown format.
    /// Also note, this option will not preserve all formatting of the table, but only preserves
    /// corresponding spans of the cells.</para>
    /// <para>If related <see cref="Tables"/> flag is set, then this flag will be ignored.</para>
    /// </remarks>
    public static final int NON_COMPATIBLE_TABLES = 0x0002;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MarkdownExportAsHtml value.
    /// </summary>
    public static String getName(/*MarkdownExportAsHtml*/int markdownExportAsHtml)
    {
    	switch (markdownExportAsHtml)
    	{
    		case NONE: return "NONE";
    		case TABLES: return "TABLES";
    		case NON_COMPATIBLE_TABLES: return "NON_COMPATIBLE_TABLES";
    		default: return "Unknown MarkdownExportAsHtml value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer MarkdownExportAsHtml value.
    /// </summary>
    public static Set<String> getNames(/*MarkdownExportAsHtml*/int markdownExportAsHtml)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & markdownExportAsHtml) == NONE) names.add("NONE");
    	if ((TABLES & markdownExportAsHtml) == TABLES) names.add("TABLES");
    	if ((NON_COMPATIBLE_TABLES & markdownExportAsHtml) == NON_COMPATIBLE_TABLES) names.add("NON_COMPATIBLE_TABLES");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MarkdownExportAsHtml value.
    /// </summary>
    public static String toString(/*MarkdownExportAsHtml*/int markdownExportAsHtml)
    {
    	switch (markdownExportAsHtml)
    	{
    		case NONE: return "None";
    		case TABLES: return "Tables";
    		case NON_COMPATIBLE_TABLES: return "NonCompatibleTables";
    		default: return toStringSet(markdownExportAsHtml);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MarkdownExportAsHtml value.
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
    /// Returns integer representation by MarkdownExportAsHtml name.
    /// </summary>
    public static /*MarkdownExportAsHtml*/int fromName(String markdownExportAsHtmlName)
    {
    	if ("NONE".equals(markdownExportAsHtmlName)) return NONE;
    	if ("TABLES".equals(markdownExportAsHtmlName)) return TABLES;
    	if ("NON_COMPATIBLE_TABLES".equals(markdownExportAsHtmlName)) return NON_COMPATIBLE_TABLES;
    	throw new IllegalArgumentException("Unknown MarkdownExportAsHtml name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by MarkdownExportAsHtml names.
    /// </summary>
    public static /*MarkdownExportAsHtml*/int fromNames(Set<String> markdownExportAsHtmlNames)
    {
    	/*MarkdownExportAsHtml*/int result = 0;
    	for (String name : markdownExportAsHtmlNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of MarkdownExportAsHtml values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		TABLES,
    		NON_COMPATIBLE_TABLES,
    	};
    }

    public static final int length = 3;
}

