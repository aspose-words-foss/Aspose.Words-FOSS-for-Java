// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2020 by Mikhail Nepreteamov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Compression level for OOXML files. 
/// <para>
/// (DOCX and DOTX files are internally a ZIP-archive, this property controls the compression level of the archive.
/// </para>
/// <para>
/// Note, that FlatOpc file is not a ZIP-archive, therefore, this property does not affect the FlatOpc files.)
/// </para>
/// </summary>
public /*enum*/ final class CompressionLevel
{
    private CompressionLevel(){}
    
    /// <summary>
    /// Normal compression level. Default compression level used by Aspose.Words.
    /// </summary>
    public static final int NORMAL = 0;

    /// <summary>
    /// Maximum compression level.
    /// </summary>
    public static final int MAXIMUM = 1;

    /// <summary>
    /// Fast compression level.
    /// </summary>
    public static final int FAST = 2;

    /// <summary>
    /// Super Fast compression level. Microsoft Word uses this compression level.
    /// </summary>
    public static final int SUPER_FAST = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CompressionLevel value.
    /// </summary>
    public static String getName(/*CompressionLevel*/int compressionLevel)
    {
    	switch (compressionLevel)
    	{
    		case NORMAL: return "NORMAL";
    		case MAXIMUM: return "MAXIMUM";
    		case FAST: return "FAST";
    		case SUPER_FAST: return "SUPER_FAST";
    		default: return "Unknown CompressionLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CompressionLevel value.
    /// </summary>
    public static String toString(/*CompressionLevel*/int compressionLevel)
    {
    	switch (compressionLevel)
    	{
    		case NORMAL: return "Normal";
    		case MAXIMUM: return "Maximum";
    		case FAST: return "Fast";
    		case SUPER_FAST: return "SuperFast";
    		default: return "Unknown CompressionLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CompressionLevel name.
    /// </summary>
    public static /*CompressionLevel*/int fromName(String compressionLevelName)
    {
    	if ("NORMAL".equals(compressionLevelName)) return NORMAL;
    	if ("MAXIMUM".equals(compressionLevelName)) return MAXIMUM;
    	if ("FAST".equals(compressionLevelName)) return FAST;
    	if ("SUPER_FAST".equals(compressionLevelName)) return SUPER_FAST;
    	throw new IllegalArgumentException("Unknown CompressionLevel name.");
    }
    
    /// <summary>
    /// Returns array of CompressionLevel values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NORMAL,
    		MAXIMUM,
    		FAST,
    		SUPER_FAST,
    	};
    }

    public static final int length = 4;
}

