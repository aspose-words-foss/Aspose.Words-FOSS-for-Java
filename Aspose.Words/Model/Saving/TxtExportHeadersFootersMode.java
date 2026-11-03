// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2018 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the way headers and footers are exported to plain text format.
/// </summary>
public /*enum*/ final class TxtExportHeadersFootersMode
{
    private TxtExportHeadersFootersMode(){}
    
    /// <summary>
    /// No headers and footers are exported.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Only primary headers and footers are exported at the beginning and end of each section.
    /// </summary>
    /// <remarks>
    /// <para>It is hard to meaningfully output headers and footers to plain text because it is not paginated.</para> 
    /// <para>When this mode is used, only primary headers and footers are exported at the beginning and end of each section.</para>
    /// </remarks>
    public static final int PRIMARY_ONLY = 1;
    /// <summary>
    /// All headers and footers are placed after all section bodies at the very end of a document.
    /// </summary>
    /// <remarks>
    /// This mode is similar to Word.
    /// </remarks>
    public static final int ALL_AT_END = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TxtExportHeadersFootersMode value.
    /// </summary>
    public static String getName(/*TxtExportHeadersFootersMode*/int txtExportHeadersFootersMode)
    {
    	switch (txtExportHeadersFootersMode)
    	{
    		case NONE: return "NONE";
    		case PRIMARY_ONLY: return "PRIMARY_ONLY";
    		case ALL_AT_END: return "ALL_AT_END";
    		default: return "Unknown TxtExportHeadersFootersMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TxtExportHeadersFootersMode value.
    /// </summary>
    public static String toString(/*TxtExportHeadersFootersMode*/int txtExportHeadersFootersMode)
    {
    	switch (txtExportHeadersFootersMode)
    	{
    		case NONE: return "None";
    		case PRIMARY_ONLY: return "PrimaryOnly";
    		case ALL_AT_END: return "AllAtEnd";
    		default: return "Unknown TxtExportHeadersFootersMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TxtExportHeadersFootersMode name.
    /// </summary>
    public static /*TxtExportHeadersFootersMode*/int fromName(String txtExportHeadersFootersModeName)
    {
    	if ("NONE".equals(txtExportHeadersFootersModeName)) return NONE;
    	if ("PRIMARY_ONLY".equals(txtExportHeadersFootersModeName)) return PRIMARY_ONLY;
    	if ("ALL_AT_END".equals(txtExportHeadersFootersModeName)) return ALL_AT_END;
    	throw new IllegalArgumentException("Unknown TxtExportHeadersFootersMode name.");
    }
    
    /// <summary>
    /// Returns array of TxtExportHeadersFootersMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		PRIMARY_ONLY,
    		ALL_AT_END,
    	};
    }

    public static final int length = 3;
}

