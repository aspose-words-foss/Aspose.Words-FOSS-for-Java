// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/06/2016 by Alexey Morozov


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies modes for document loading.
/// </summary>
/// <remarks>
/// We've decided to add this enum for DOC Raw-mode extraction to be supported along with doc SkipFormatting mode.
/// GroupDocs mentioned they would want different kinds of fast reading with gradually degrading text fidelity.
/// for <see cref="PlainTextDocument"/> construction.
/// </remarks>
/*enum*/ final class LoadMode
{
    private LoadMode(){}
    
    /// <summary>
    /// Normal document loading. Slowest loading speed.
    /// </summary>
    public static final int NORMAL = 0;

    /// <summary>
    /// No formatting but all text is read and parsed. Average loading speed.
    /// </summary>
    public static final int SKIP_FORMATTING = 1;

    /// <summary>
    /// Raw unparsed text chunks is read. Best loading speed.
    /// </summary>
    /// <remarks>Supported by DOC only.</remarks>
    public static final int RAW_TEXT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer LoadMode value.
    /// </summary>
    public static String getName(/*LoadMode*/int loadMode)
    {
    	switch (loadMode)
    	{
    		case NORMAL: return "NORMAL";
    		case SKIP_FORMATTING: return "SKIP_FORMATTING";
    		case RAW_TEXT: return "RAW_TEXT";
    		default: return "Unknown LoadMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer LoadMode value.
    /// </summary>
    public static String toString(/*LoadMode*/int loadMode)
    {
    	switch (loadMode)
    	{
    		case NORMAL: return "Normal";
    		case SKIP_FORMATTING: return "SkipFormatting";
    		case RAW_TEXT: return "RawText";
    		default: return "Unknown LoadMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by LoadMode name.
    /// </summary>
    public static /*LoadMode*/int fromName(String loadModeName)
    {
    	if ("NORMAL".equals(loadModeName)) return NORMAL;
    	if ("SKIP_FORMATTING".equals(loadModeName)) return SKIP_FORMATTING;
    	if ("RAW_TEXT".equals(loadModeName)) return RAW_TEXT;
    	throw new IllegalArgumentException("Unknown LoadMode name.");
    }
    
    /// <summary>
    /// Returns array of LoadMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NORMAL,
    		SKIP_FORMATTING,
    		RAW_TEXT,
    	};
    }

    public static final int length = 3;
}

