// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the character spacing adjustment for a document.
/// The default value is <c>Expand</c>.
/// </summary>
/// <dev>
/// In HTML, this is equivalent to setting text-justify-trim on the BODY element.
/// </dev>
public /*enum*/ final class JustificationMode
{
    private JustificationMode(){}
    
    /// <summary>
    /// Do not compress character spacing.
    /// </summary>
    public static final int EXPAND = 0;

    /// <summary>
    /// Compress character spacing.
    /// </summary>
    public static final int COMPRESS = 1;

    /// <summary>
    /// Compress, using rules of the kana syllabaries, Hiragana and Katakana.
    /// </summary>
    public static final int COMPRESS_KANA = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer JustificationMode value.
    /// </summary>
    public static String getName(/*JustificationMode*/int justificationMode)
    {
    	switch (justificationMode)
    	{
    		case EXPAND: return "EXPAND";
    		case COMPRESS: return "COMPRESS";
    		case COMPRESS_KANA: return "COMPRESS_KANA";
    		default: return "Unknown JustificationMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer JustificationMode value.
    /// </summary>
    public static String toString(/*JustificationMode*/int justificationMode)
    {
    	switch (justificationMode)
    	{
    		case EXPAND: return "Expand";
    		case COMPRESS: return "Compress";
    		case COMPRESS_KANA: return "CompressKana";
    		default: return "Unknown JustificationMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by JustificationMode name.
    /// </summary>
    public static /*JustificationMode*/int fromName(String justificationModeName)
    {
    	if ("EXPAND".equals(justificationModeName)) return EXPAND;
    	if ("COMPRESS".equals(justificationModeName)) return COMPRESS;
    	if ("COMPRESS_KANA".equals(justificationModeName)) return COMPRESS_KANA;
    	throw new IllegalArgumentException("Unknown JustificationMode name.");
    }
    
    /// <summary>
    /// Returns array of JustificationMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		EXPAND,
    		COMPRESS,
    		COMPRESS_KANA,
    	};
    }

    public static final int length = 3;
}

